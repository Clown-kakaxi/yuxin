/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common
 * @文件名：TxAdapterExcutor.java
 * @版本信息：1.0.0
 * @日期：2014-5-29-17:58:38
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 *
 */

package com.ytec.mdm.interfaces.common;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.integration.adapter.message.xml.IntegrationLayer;
import com.ytec.mdm.integration.adapter.message.xml.XmlIntegrationLayer;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.common.even.EvenSubject;
import com.ytec.mdm.interfaces.common.even.Subject;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：TxAdapterExcutor
 * @类描述：接口执行基类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-29 下午5:56:27
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-29 下午5:56:27
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 *
 */
public abstract class TxAdapterExcutor implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TxAdapterExcutor.class);
	/**
	 * @属性名称:interFaceType
	 * @属性描述:接口类型
	 * @since 1.0.0
	 */
	protected String interFaceType;
	/**
	 * @属性名称:atp
	 * @属性描述:自定义适配器
	 * @since 1.0.0
	 */
	protected IntegrationLayer atp;
	/**
	 * @属性名称:sid
	 * @属性描述:线程号
	 * @since 1.0.0
	 */
	protected String sid;
	/**
	 * @属性名称:doc
	 * @属性描述:请求报文对象
	 * @since 1.0.0
	 */
	protected Document doc;
	/**
	 * @属性名称:recvmsg
	 * @属性描述:请求报文
	 * @since 1.0.0
	 */
	protected String recvmsg;
	/**
	 * @属性名称:resXml
	 * @属性描述:响应报文
	 * @since 1.0.0
	 */
	protected String resXml;
	protected EcifData data;
	/**
	 * @属性名称:evenSubject
	 * @属性描述:事件通知
	 * @since 1.0.0
	 */
	protected Subject evenSubject = EvenSubject.getInstance();
	/**
	 * @属性名称:charSet
	 * @属性描述:字符集
	 * @since 1.0.0
	 */
	protected String charSet;

	/**
	 *@构造函数
	 */
	public TxAdapterExcutor() {
		/***
		 * 为每个服务工作线程分配一个ID号
		 */
		long id = (long) (Math.random() * 10000000)
				+ System.currentTimeMillis() + this.hashCode();
		this.sid = this.getClass().getSimpleName() + "-" + id;
		/**
		 * 设置以XML为报文的集成层处理
		 */
		atp = new XmlIntegrationLayer();
		/***
		 * 设置默认的字符集编码
		 */
		charSet = MdmConstants.TX_XML_ENCODING;
	}

	/**
	 * @函数名称:beginAdapter
	 * @函数描述:接口执行开始
	 * @参数与返回说明:
	 * @算法描述:
	 */
	protected void beginAdapter() {
		log.info("!> @ {}  Executor.run() START.", this.sid);
		/***
		 * 分配ECIF内部对象
		 */
		data = new EcifData();
		data.setInterFaceType(interFaceType);
		/***
		 * 开始计时
		 */
		data.getStopWatch().start();
	}

	/**
	 * @函数名称:endAdapter
	 * @函数描述:执行结束处理
	 * @参数与返回说明:
	 * @算法描述:
	 */
	protected void endAdapter() {
		/** 计时结束 **/
		data.getStopWatch().stop();
		/**请求不为空，不是退出操作***/
		if (this.recvmsg != null) {
			if (data.getTxCode() != null) {
				log.info("交易码[{}],请求系统[{}],交易返回[{}],处理耗时[{}]ms", data
						.getTxCode(), data.getOpChnlNo(), data.getDetailDes(),
						data.getStopWatch().getElapsedTime());
			}
			if (!data.isSuccess()) {
				log.info("交易成功状态[FALSE],设置返回信息位置:{}", data.getFullTraceInfo());
			}
			/** 记录日志 **/
			atp.setTxLog(resXml);
			/** 事件通知 */
			evenSubject.eventNotify(data);
		}
		data = null;
		log.info("!> @ {} Executor.run() DONE.", this.sid);
	}

	/**
	 * @函数名称:resolvingXml
	 * @函数描述:解析XML
	 * @参数与返回说明:
	 * 		@throws RequestIOException
	 * @算法描述:
	 */
	protected void resolvingXml() throws RequestIOException {
		try {
			/****
			 * 解析XML报文
			 ****/
			doc = XMLUtils.stringToXml(recvmsg);
			/***
			 * 保存原始报文供以后客户化开发使用
			 */
			data.setPrimalMsg(recvmsg);
			/**
			 * 设置XML对象
			 */
			data.setPrimalDoc(doc);
		} catch (Exception e) {
			log.error("解析报文出错:{}",e.getMessage() );
			throw new RequestIOException("XML报文非法");
		}
	}

	/**
	 * 处理线程
	 */
	public void run() {
		/** 前置处理 **/
		beginAdapter();
		/** 适配器处理 ***/
		execute();
		/** 后置处理 **/
		endAdapter();
	}

	/**
	 * @函数名称:execute
	 * @函数描述:执行
	 * @参数与返回说明:
	 * @算法描述:
	 */
	protected abstract void execute();

}
