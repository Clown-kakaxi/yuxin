/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ext
 * @�ļ�����HttpExecutorWhithChannel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:48:30
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * ECIF http �ӿڽ���
 * @author wangzy1@yuchengtech.com
 * 
 */
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�HttpExecutorWhithChannel
 * @��������HTTP������,�Դ���ͨ�����жϽ���ִ�бȽ��������ύ����ͨ������ͨ���������ȴ��¸�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:48:30   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:48:30
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class HttpExecutorWhithChannel extends HttpExecutor implements IWithChannel{
	private static Logger log = LoggerFactory.getLogger(HttpExecutorWhithChannel.class);
	private static int defaultChannelSize=2;
	private static ExecutorService pool=Executors.newFixedThreadPool(ServerConfiger.getIntArg("slowChannelSize")>0 ? 
												ServerConfiger.getIntArg("slowChannelSize"):defaultChannelSize);            //��ͨ���̳߳�
	private boolean isOtherChannel=false;
	/**
	 * ���캯��
	 * 
	 */
	public HttpExecutorWhithChannel() {
		super();
	}
	/**
	 * �����߳�
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
						/***����http����****/
						request = decoder.decode(receivebuffer);
					}catch(RequestTooLargeException e){//����̫��
						log.error("http���Ľ�����",e);
						this.respcode = HttpStatus.REQUEST_ENTITY_TOO_LARGE;
						throw new RequestIOException(e.getMessage());
					}catch(ProtocolException e){
						this.respcode = HttpStatus.PRECONDITION_FAILED;
						log.info("http PRECONDITION_FAILED");
						throw new RequestIOException(e.getMessage());
					}catch(Exception e){//���Ĵ���
						log.error("http���Ľ�����",e);
						this.respcode = HttpStatus.BAD_REQUEST;
						throw new RequestIOException(e.getMessage());
					}
					if (request != null) {
						decoder.reset();
						/**û����������***/
						if(request.contentLength==0){
							log.info("����Ϊ��");
							send(this.packing(createDefauteMsg(ErrorCode.INF_PUBLISH_SUCCESS.getCode(),"�����������У����ύECIF��������"),  HttpStatus.BAD_REQUEST));
							selector.removeOpenSocket(client);
							return;
						}
						this.recvmsg=request.getBodyString();
						log.info(">>>[RECV-XML] Client["+ client.socket().getRemoteSocketAddress() + "]["+ request.contentLength + "]");
						log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
						beforeExecutor();
						/****����XML����****/
						resolvingXml();
						
						if (doc != null) {
							/** ���ý�����Ϣ���� **/
							if(!StringUtil.isEmpty(request.getCharset())){
								charSet=request.getCharset();
							}
							data.setCharsetName(charSet);
							getEcifData();
						}else{
							this.respcode = HttpStatus.UNPROCESSABLE_ENTITY;
							log.info("����������http����");
							throw new RequestIOException("����������http����"+respcode.getReasonPhrase());
						}
						if(asserOtherChannel()){
							isOtherChannel=true;
							pool.execute(new OtherChannelExecutor(this));
							log.info("!> @ {} �ύ������ͨ������ͨ���˳�",this.sid);
							return;
						}
						/** ���ñ�׼���� */
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
							log.error("http���Ľ�����ʱ");
							this.respcode = HttpStatus.BAD_REQUEST;
							throw new RequestIOException("http���Ľ�����ʱ");
						}
						if(httpServerBlock!=read){
							log.warn("����״̬���ã���Ҫ��ȡ��Ρ�����");
							try{
								Thread.sleep(sleepTime);
							}catch(Exception e){
								log.warn(e.getMessage());
							}
						}
						rcount++;
						log.warn("��ȡ����"+read+"Byte,��Ҫ��ȡ��Ρ�����");
						this.receivebuffer.clear(); // �ٶ�
						read = client.read(receivebuffer);
						receivebuffer.flip(); // flip for read
						if(read<0){
							selector.removeOpenSocket(client);
							log.warn("!> @" + this.sid + " TxExecutor.run() exit.");
							return;
						}else if(read==0){
							log.warn("��ȡ0Byte����");
						}
					}
				} while (receivebuffer.hasRemaining()); // consume all
				if(request==null){
					log.error("http���Ĵ���");
					this.respcode = HttpStatus.BAD_REQUEST;
					throw new RequestIOException("http���Ĵ���");
				}
			}else{
				/***ע����¼�****/
				selector.registerChannel(session);
			}
			/********/
		} catch (RequestIOException eie) {
			/***����Ƿ�***/
			log.error("����Ƿ�:{}",eie.getMessage());
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
			/***IO����***/
			log.error("IO����",ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/**����������**/
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
			log.error("!> [FATAL]���������ش���",ex);
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
			log.info("!> @ {} ������ͨ������ʼִ��",this.sid);
			/** ���ñ�׼���� */
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
			log.info("��ͨ��ִ�н���");
		} catch (RequestIOException eie) {
			/***����Ƿ�***/
			log.error("����Ƿ�:{}",eie.getMessage());
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
			/***IO����***/
			log.error("IO����",ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/**����������**/
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
			log.error("!> [FATAL]���������ش���",ex);
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
	 * ��д�����߳�
	 */
	@Override
	public void run() {
		/**ǰ�ô���**/
		beginAdapter();
		/**����������***/
		execute();
		if(!isOtherChannel){
		/**���ô���**/
			endAdapter();
		}
	}
}
