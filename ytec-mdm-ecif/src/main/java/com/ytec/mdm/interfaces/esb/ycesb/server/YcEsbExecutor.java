/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.esb.ycesb.server
 * @文件名：YcEsbExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:39:47
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.esb.ycesb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spc.webos.endpoint.ESB2;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：YcEsbExecutor
 * @类描述：YC ESB 执行程序
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:39:48   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:39:48
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class YcEsbExecutor extends TxAdapterExcutor{
	private static Logger log = LoggerFactory.getLogger(YcEsbExecutor.class);
	/**
	 * @属性名称:esbCharsetName
	 * @属性描述:YCESB字符集
	 * @since 1.0.0
	 */
	protected static String esbCharsetName="UTF-8";
	/**
	 * @属性名称:esb2
	 * @属性描述:ESB2
	 * @since 1.0.0
	 */
	protected ESB2 esb2;
	
	/**
	 *@构造函数 
	 */
	public YcEsbExecutor() {
		super();
	}

	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@param reqBuf
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void init(byte[] reqBuf) throws Exception {
		this.recvmsg=new String(reqBuf,esbCharsetName);
		log.info(">>>[RECV-XML]");
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		interFaceType="YCESB2";
		esb2=ESB2.getInstance();
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.TxAdapterExcutor#execute()
	 */
	public void execute() {
		try {
			/** 集成层前置处理 **/
			beforeExecutor();
			/****解析XML报文****/
			resolvingXml();
			if (doc != null) {
				/** 设置交易信息参数 **/
				data.setCharsetName(esbCharsetName);
				getEcifData();
			} else {
				log.error("解析到错误的ESB请求");
				throw new RequestIOException("解析到错误的ESB请求");
			}
			/** 调用服务 */
			atp.process(data);
			/** 组装响应报文 **/
			resXml = createOutputDocument();

			/** 集成层后置处理 **/
			afterExecutor();
			esb2.sendResponse(getResXml());
		} catch (RequestIOException eie) {
			try{
				log.error("请求非法:{}", eie.getMessage());
				resXml = createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(), eie.getMessage());
				afterExecutor();
				esb2.sendResponse(getResXml());
			}catch(Exception e1){
				log.info("!> Thread[" + this.sid + "] Exception[1] loop.");
			}
			
		} catch (Exception e) {
			try{
				log.error("服务器内部错误:", e);
				resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), e.getMessage());
				afterExecutor();
				esb2.sendResponse(getResXml());
			}catch(Exception e2){
				log.info("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			
		}catch(Throwable ex){
			try{
				log.error("服务器严重错误:", ex);
				resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), ex.getMessage());
				afterExecutor();
				esb2.sendResponse(getResXml());
			}catch (Exception e){
				return;
			}catch(Throwable ex1){
				return;
			}
		}
	}
	
	/**
	 * @函数名称:getResXml
	 * @函数描述:获取响应报文
	 * @参数与返回说明:
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	protected byte[] getResXml() throws Exception{
		log.info("<<<[SEND]");
		log.info(SensitHelper.getInstance().doInforFilter(this.resXml,null));
		if(this.resXml==null){
			return "".getBytes(esbCharsetName);
		}
		return this.resXml.getBytes(esbCharsetName);
	}
	
	/**
	 * 接到报文后前置处理
	 */
	protected abstract void beforeExecutor();
	/**
	 * 响应报文后置处理
	 */
	protected abstract void afterExecutor();

	/**
	 * 解析报文，封装ecif内部对象
	 */
	protected abstract void getEcifData() throws Exception;

	/**
	 * 封装响应报文
	 */
	protected abstract String createOutputDocument() throws Exception;

	/**
	 * 默认响应错误报文
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg);


}
