package com.yuchengtech.bcrm.customer.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.trans.client.Dom4jReadXml;
import com.yuchengtech.trans.client.TransClient;
import com.yuchengtech.trans.client.XmlhelperUtil;

@Action("/ocrmFCiGroupGeneralbusi")
public class OcrmFCiGroupGeneralbusiAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String grpNo=request.getParameter("GRP_NO");
		StringBuffer sb=new StringBuffer("select g.* from OCRM_F_CI_GROUP_GENERALBUSI g " +
				"where 1 = 1 and g.GRP_NO = '"+grpNo+"'");
				
		SQL=sb.toString();
		datasource=ds;
	}
	
	public String Loan() throws Exception{
		Map obj = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String grpno = request.getParameter("GRP_NO");		
	    String resXml=TransClient.process1(grpno);
		Dom4jReadXml dom=new Dom4jReadXml();
		List list=dom.getParseXmlList(resXml);
		Map a=(Map) list.get(0);
		List list1=(List) a.get("LmtAppDetails");
		Map a2=new HashMap();
		a2.put("count", list1.size());
		a2.put("data", list1);
		this.json = new HashMap<String, Object>();
		this.json.put("json", a2);
		return null;

	}

	public String Loan2() throws Exception{
		Map obj = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String grpno = request.getParameter("GRP_NO");
		String resXml=TransClient.process1(grpno);
		Dom4jReadXml dom=new Dom4jReadXml();
		List list=dom.getParseXmlList(resXml);
		Map a=(Map) list.get(0);
		List list1=(List) a.get("PledgeMssJournalAcc");
		Map a2=new HashMap();
		a2.put("count", list1.size());
		a2.put("data", list1);
		this.json = new HashMap<String, Object>();
		this.json.put("json", a2);
		return null;

	}
	
	public String Loan3() throws Exception{
		Map obj = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String grpno = request.getParameter("GRP_NO");
		String resXml=TransClient.process1(grpno);
		Dom4jReadXml dom=new Dom4jReadXml();
		List list=dom.getParseXmlList(resXml);
		Map a=(Map) list.get(0);
		List list1=(List) a.get("DisAppDetails");
		Map a2=new HashMap();
		a2.put("count", list1.size());
		a2.put("data", list1);
		this.json = new HashMap<String, Object>();
		this.json.put("json", a2);
		return null;

	}
	
	public String Loan4() throws Exception{
		Map obj = new HashMap();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String grpno = request.getParameter("GRP_NO");
		String resXml=TransClient.process1(grpno);
		Dom4jReadXml dom=new Dom4jReadXml();
		List list=dom.getParseXmlList(resXml);
		Map a=(Map) list.get(0);
		List list1=(List) a.get("balance");
		Map a2=new HashMap();
		a2.put("count", list1.size());
		a2.put("data", list1);
		this.json = new HashMap<String, Object>();
		this.json.put("json", a2.get("data"));
		return null;

	}

	
}
