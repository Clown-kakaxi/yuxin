package com.yuchengtech.bcrm.oneKeyAccount.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.oneKeyAccount.service.CheckHXOpenAccountService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/checkHXOpenAccountAction")
public class CheckHXOpenAccountAction extends CommonAction{
	
	@Autowired
	private CheckHXOpenAccountService checkHXOpenAccountServicce;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	 /**
	 *  验证核心是否可以开户
	 * @param certTypeStr
	 * @param certNoStr
	 * @return  
	 */
	public void checkHXAccount(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	//联名户标志，1:联名户;0:非联名户
		String jointCustType = request.getParameter("jointaccount");
		String certType1 = request.getParameter("certtype");//证件类型1
    	String certNo1 = request.getParameter("certid");//证件号码1
    	String certType2 = request.getParameter("certtype2");//证件类型2
    	String certNo2 = request.getParameter("certid2");//证件号码2
		Map<String,Object> modelMap = new HashMap<String, Object>();
		if(jointCustType != null && jointCustType.equals("1")){//联名户
			modelMap = checkHXOpenAccountServicce.checkHXOpenJointAccount(certType1,certNo1,certType2,certNo2);
		}else{//非联名户
			modelMap = checkHXOpenAccountServicce.checkHXOpenAccount(certType1,certNo1);
		}
		this.json = modelMap;
	}

}
