/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ext
 * @文件名：HttpExecutorWhithChannel.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:48:30
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.ext.IWithChannel;
import com.ytec.mdm.interfaces.socket.http.server.HttpExecutor;
import com.ytec.mdm.interfaces.socket.http.tools.HttpStatus;
import com.ytec.mdm.interfaces.socket.http.tools.ProtocolException;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;
import com.ytec.mdm.server.common.ServerConfiger;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.StringUtil;

/**
 * ECIF http 接口解析
 * @author wangzy1@yuchengtech.com
 * 
 */
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：HttpExecutorWhithChannel
 * @类描述：HTTP工作类,自带慢通道，判断交易执行比较慢，就提交到慢通道，主通道结束，等待下个请求
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:48:30   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:48:30
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class HttpExecutorWhithChannel extends HttpExecutor implements IWithChannel{
	private static Logger log = LoggerFactory.getLogger(HttpExecutorWhithChannel.class);
	private static int defaultChannelSize=2;
	private static ExecutorService pool=Executors.newFixedThreadPool(ServerConfiger.getIntArg("slowChannelSize")>0 ? 
												ServerConfiger.getIntArg("slowChannelSize"):defaultChannelSize);            //慢通道线程池
	private boolean isOtherChannel=false;
	/**
	 * 构造函数
	 * 
	 */
	public HttpExecutorWhithChannel() {
		super();
	}
	/**
	 * 处理线程
	 */
	@Override
	public void execute() {
		try {
			this.receivebuffer.clear(); // clear for read
			int read = client.read(receivebuffer);
			if (read <0) {
				selector.removeOpenSocket(client);
				log.info("!> @" + this.sid + " TxExecutor.run() exit.");
				return;
			} else if (read > 0) {
				receivebuffer.flip(); // flip for read
				request=null;
				int rcount=0;
				do {
					try{
						/***解析http报文****/
						request = decoder.decode(receivebuffer);
					}catch(RequestTooLargeException e){//报文太长
						log.error("http报文解析：",e);
						this.respcode = HttpStatus.REQUEST_ENTITY_TOO_LARGE;
						throw new RequestIOException(e.getMessage());
					}catch(ProtocolException e){
						this.respcode = HttpStatus.PRECONDITION_FAILED;
						log.info("http PRECONDITION_FAILED");
						throw new RequestIOException(e.getMessage());
					}catch(Exception e){//报文错误
						log.error("http报文解析：",e);
						this.respcode = HttpStatus.BAD_REQUEST;
						throw new RequestIOException(e.getMessage());
					}
					if (request != null) {
						decoder.reset();
						/**没有请求内容***/
						if(request.contentLength==0){
							log.info("请求为空");
							send(this.packing(createDefauteMsg(ErrorCode.INF_PUBLISH_SUCCESS.getCode(),"服务正在运行，请提交ECIF请求数据"),  HttpStatus.BAD_REQUEST));
							selector.removeOpenSocket(client);
							return;
						}
						this.recvmsg=request.getBodyString();
						log.info(">>>[RECV-XML] Client["+ client.socket().getRemoteSocketAddress() + "]["+ request.contentLength + "]");
						log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
						beforeExecutor();
						/****解析XML报文****/
						resolvingXml();
						
						if (doc != null) {
							/** 设置交易信息参数 **/
							if(!StringUtil.isEmpty(request.getCharset())){
								charSet=request.getCharset();
							}
							data.setCharsetName(charSet);
							getEcifData();
						}else{
							this.respcode = HttpStatus.UNPROCESSABLE_ENTITY;
							log.info("解析到错误http请求");
							throw new RequestIOException("解析到错误http请求"+respcode.getReasonPhrase());
						}
						if(asserOtherChannel()){
							isOtherChannel=true;
							pool.execute(new OtherChannelExecutor(this));
							log.info("!> @ {} 提交任务到慢通道，主通道退出",this.sid);
							return;
						}
						/** 调用标准服务 */
						atp.process(data);
						resXml=createOutputDocument();
						afterExecutor();
						send(this.packing(resXml, HttpStatus.OK));
						if(!isCloseAfterSvc&&request.isKeepAlive){
							if(!receivebuffer.hasRemaining()){
								selector.registerChannel(session);
							}
						}else{
							selector.removeOpenSocket(client);
							break;
						}
					}else{
						if(rcount>readMaxNum){
							log.error("http报文解析超时");
							this.respcode = HttpStatus.BAD_REQUEST;
							throw new RequestIOException("http报文解析超时");
						}
						if(httpServerBlock!=read){
							log.warn("网络状态不好，需要读取多次。。。");
							try{
								Thread.sleep(sleepTime);
							}catch(Exception e){
								log.warn(e.getMessage());
							}
						}
						rcount++;
						log.warn("读取数据"+read+"Byte,需要读取多次。。。");
						this.receivebuffer.clear(); // 再读
						read = client.read(receivebuffer);
						receivebuffer.flip(); // flip for read
						if(read<0){
							selector.removeOpenSocket(client);
							log.warn("!> @" + this.sid + " TxExecutor.run() exit.");
							return;
						}else if(read==0){
							log.warn("读取0Byte数据");
						}
					}
				} while (receivebuffer.hasRemaining()); // consume all
				if(request==null){
					log.error("http报文错误");
					this.respcode = HttpStatus.BAD_REQUEST;
					throw new RequestIOException("http报文错误");
				}
			}else{
				/***注册读事件****/
				selector.registerChannel(session);
			}
			/********/
		} catch (RequestIOException eie) {
			/***请求非法***/
			log.error("请求非法:{}",eie.getMessage());
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(),eie.getMessage());
				afterExecutor();
				if(respcode==null){
					respcode=HttpStatus.BAD_REQUEST;
				}
				send(this.packing(resXml, this.respcode));
			} catch (Exception e1) {
				log.info("!> Thread[" + this.sid + "] Exception[1] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		} catch (IOException ie) {
			/***IO错误***/
			log.error("IO错误",ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/**服务器错误**/
			log.error("!> [FATAL] Exception: " , e);
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),e.getMessage());
				afterExecutor();
				send(this.packing(resXml, HttpStatus.INTERNAL_SERVER_ERROR));
			} catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		}catch(Throwable ex){
			log.error("!> [FATAL]服务器严重错误",ex);
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),ex.getMessage());
				afterExecutor();
				send(this.packing(resXml, HttpStatus.INTERNAL_SERVER_ERROR));
			}catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
			}catch(Throwable ex1){
				log.error("!> Thread[" + this.sid + "] Throwable[3] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		}
	}
	
	public void otherChannelExe(){
		try {
			log.info("!> @ {} 任务到慢通道，开始执行",this.sid);
			/** 调用标准服务 */
			atp.process(data);
			resXml=createOutputDocument();
			afterExecutor();
			send(this.packing(resXml, HttpStatus.OK));
			if(!isCloseAfterSvc&&request.isKeepAlive){
				if(!receivebuffer.hasRemaining()){
					selector.registerChannel(session);
				}
			}else{
				selector.removeOpenSocket(client);
			}
			endAdapter();
			log.info("慢通道执行结束");
		} catch (RequestIOException eie) {
			/***请求非法***/
			log.error("请求非法:{}",eie.getMessage());
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(),eie.getMessage());
				afterExecutor();
				if(respcode==null){
					respcode=HttpStatus.BAD_REQUEST;
				}
				send(this.packing(resXml, this.respcode));
			} catch (Exception e1) {
				log.info("!> Thread[" + this.sid + "] Exception[1] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		} catch (IOException ie) {
			/***IO错误***/
			log.error("IO错误",ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/**服务器错误**/
			log.error("!> [FATAL] Exception: " , e);
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),e.getMessage());
				afterExecutor();
				send(this.packing(resXml, HttpStatus.INTERNAL_SERVER_ERROR));
			} catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		}catch(Throwable ex){
			log.error("!> [FATAL]服务器严重错误",ex);
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),ex.getMessage());
				afterExecutor();
				send(this.packing(resXml, HttpStatus.INTERNAL_SERVER_ERROR));
			}catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
			}catch(Throwable ex1){
				log.error("!> Thread[" + this.sid + "] Throwable[3] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		}
	}
	
	/**
	 * 重写处理线程
	 */
	@Override
	public void run() {
		/**前置处理**/
		beginAdapter();
		/**适配器处理***/
		execute();
		if(!isOtherChannel){
		/**后置处理**/
			endAdapter();
		}
	}
}
