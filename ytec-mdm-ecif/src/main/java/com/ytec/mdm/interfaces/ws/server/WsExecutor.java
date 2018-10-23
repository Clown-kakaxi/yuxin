/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.ws.server
 * @文件名：WsExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:49:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.ws.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：WsExecutor
 * @类描述：WEB SERVICE 执行
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:49:28   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:49:28
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class WsExecutor extends TxAdapterExcutor{
	private static Logger log = LoggerFactory.getLogger(WsExecutor.class);
	private static final Pattern ENCODING = Pattern.compile("encoding=('|\")([\\w|-]+)('|\")",
            Pattern.CASE_INSENSITIVE);   //XML字符集匹配正则式
	
	/**
	 *@构造函数 
	 */
	public WsExecutor() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@param req 请求报文
	 * @算法描述:
	 */
	public void init(String req){
		this.recvmsg=req;
		log.info(">>>[RECV-XML]");
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		interFaceType="WS";
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.TxAdapterExcutor#execute()
	 */
	protected void execute() {
		try {
			beforeExecutor();
			
			/****解析XML报文****/
			resolvingXml();
			
			if (doc != null) {
				/** 设置交易信息参数 **/
				if(!StringUtil.isEmpty(doc.getXMLEncoding())){
					charSet=doc.getXMLEncoding();
				}else{
					/*获取字符集**/
					Matcher matcher = ENCODING.matcher(this.recvmsg);
			        if (matcher.find()) {
			            charSet=matcher.group(2);
			        }
				}
				//设置字符集
				data.setCharsetName(charSet);
				getEcifData();
			} else {
				log.error("解析到错误的ESB请求");
				throw new RequestIOException("解析到错误的ESB请求");
			}
			/** 调用服务 */
			atp.process(data);
			resXml = createOutputDocument();
		} catch (RequestIOException eie) {
			log.error("请求非法:{}", eie.getMessage());
			resXml = createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(), eie.getMessage());
		} catch (Exception e) {
			log.error("服务器内部错误:", e);
			resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), e.getMessage());
		}
		afterExecutor();
	}
	
	/**
	 * @函数名称:getResXml
	 * @函数描述:获取响应报文
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public String getResXml(){
		log.info(">>>[SEND-XML]");
		log.info(SensitHelper.getInstance().doInforFilter(this.resXml, null));
		return this.resXml;
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
