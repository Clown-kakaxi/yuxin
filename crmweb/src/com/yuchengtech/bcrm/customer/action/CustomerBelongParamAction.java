package com.yuchengtech.bcrm.customer.action;

import org.apache.struts2.convention.annotation.Action;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.SysPublicParamManager;
/**
 * 客户归属参数action
 * @author CHANGZH
 * @since 2013-01-10
 */

@SuppressWarnings("serial")
@Action("/customerBelongParamAction")
public class CustomerBelongParamAction  extends CommonAction{
	
	//private static Logger log = Logger.getLogger(CustomerBelongParamAction.class);
	
	 /***客户归属管理模式*/
	public String index()  {		
		String custManagerType = SysPublicParamManager.getInstance().findParamValueByName(SysPublicParamManager.CUST_MANAGER_TYPE);
		this.json.put("json",custManagerType);
        return "success";
    }
}