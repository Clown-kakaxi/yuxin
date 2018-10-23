/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.integration.transaction
 * @文件名：ExtXmlFun.java
 * @版本信息：1.0.0
 * @日期：2014-3-26-下午2:21:12
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.integration.transaction;

import org.springframework.stereotype.Component;

import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：ExtXmlFun
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-3-26 下午2:21:12   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-3-26 下午2:21:12
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
public class ExtXmlFun implements IResponseXmlFun {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg==null||arg.length==0){
			return "10202";
		}else{
			return arg[0].toString();
		}
	}

}
