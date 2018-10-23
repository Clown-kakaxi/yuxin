/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.interfaces
 * @文件名：TxnStat.java
 * @版本信息：1.0.0
 * @日期：2013-12-19-上午11:05:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：TxnStat
 * @类描述：状态返回判断
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-19 上午11:05:28   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-19 上午11:05:28
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxnStat implements IResponseXmlFun {
	private Logger log = LoggerFactory.getLogger(TxnStat.class);

	/**
	 *@构造函数 
	 */
	public TxnStat() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==1){
			if("true".equals(arg[0].toString())){
				return "SUCCESS";
			}else{
				return "ERROR";
			}
		}else{
			log.warn("状态返回函数参数为空");
			return "ERROR";
		}
	}

}
