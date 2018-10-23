package com.yuchengtech.bcrm.callReport.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreport;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;

@Action("/callreportTotalBusiDetail")
public class CallreportTotalBusiDetailAction extends CommonAction {
	/**
	 * 
	 */
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
//	private HttpServletRequest request;
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String callId=request.getParameter("call_id");
		
		System.out.print("========="+callId+"+++++++++++");
		 
		StringBuffer sb = new StringBuffer("select t.* from OCRM_F_SE_CALLREPORT_BUSI t");
		sb.append(" where t.call_id =" +callId);
		
		SQL = sb.toString();
		datasource = ds;
	}

}
