/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.interfaces
 * @文件名：YcEsbMsgCd.java
 * @版本信息：1.0.0
 * @日期：2014-2-19-下午4:55:20
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：YcEsbMsgCd
 * @类描述：YC ESB 响应报文报文编号加工函数
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-19 下午4:55:20   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-19 下午4:55:20
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class YcEsbMsgCd implements IResponseXmlFun {
	private Logger log = LoggerFactory.getLogger(YcEsbMsgCd.class);

	
	/**
	 *@构造函数 
	 */
	public YcEsbMsgCd() {
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==1){
			String msgCd = arg[0].toString();
			if (msgCd != null && msgCd != "") {
				msgCd = msgCd.trim();
				return msgCd.substring(0, msgCd.length() - 1).concat("1");
			} else {
				log.warn("响应报文报文编号加工函数参数值为空");
				return "";
			}
		}else{
			log.warn("响应报文报文编号加工函数参数为空");
			return "";
		}
	}

}
