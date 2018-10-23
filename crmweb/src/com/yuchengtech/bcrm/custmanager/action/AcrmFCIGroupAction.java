package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;


@SuppressWarnings("serial")
@Action("AcrmFCiGroup")
public class AcrmFCIGroupAction extends CommonAction  {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public void prepare(){
		
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

		String group_no = request.getParameter("group_no");
		String group_name = request.getParameter("group_name");
		// updated by liuyx 20171025  主表使用错误，由OCRM_F_CI_GROUP_INFO更换为OCRM_F_CI_GROUP_INFO_NEW
		/*StringBuffer sb =new StringBuffer(" SELECT M.*, A.USER_NAME, O.ORG_NAME        "+
				"   FROM OCRM_F_CI_GROUP_INFO_BAK M        "+
				"   LEFT JOIN ADMIN_AUTH_ACCOUNT A ON M.GAO = A.ACCOUNT_NAME   "+
				"   LEFT JOIN ADMIN_AUTH_ORG O ON M.GAO_ORG = O.ORG_ID   "+
				"  WHERE 1 = 1             ");*/
		StringBuffer sb =new StringBuffer(
				" SELECT M.GRP_NO GROUP_NO,M.GRP_NAME GROUP_NAME, M.CUS_MANAGER_NAME USER_NAME, M.INPUT_BR_ID GAO_ORG, O.ORG_NAME  "+
				"   FROM OCRM_F_CI_GROUP_INFO_NEW M"+
				"   LEFT JOIN ADMIN_AUTH_ORG O ON M.INPUT_BR_ID = O.ORG_ID "+
				"  WHERE 1 = 1             ");
		
    		if(null!=group_name&&!group_name.equals("")){	
					sb.append("  AND (m.GRP_NAME like '%"+group_name+"%' OR m.GRP_NAME = '"+group_name+"')");
				}
    		if (null!=group_no&&!group_no.equals("")){
					sb.append("  AND (m.GRP_NO ='"+group_no+"')");	
    		}
		
		SQL=sb.toString();
		datasource=ds;
	}

}