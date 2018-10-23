package com.yuchengtech.bcrm.serviceManage.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeCustSatisfyList;
import com.yuchengtech.bcrm.serviceManage.service.OcrmFSeCustSatisfyListService;
import com.yuchengtech.bob.common.CommonAction;
/***
 * 客户满意度调查处理的action
 * @author luyueyue
 *
 */

@SuppressWarnings("serial")
@Action("/ocrmFSeCustSatisfyList")
public class OcrmFSeCustSatisfyListAction  extends CommonAction{

    private  String  title_result;
	@Autowired
    private OcrmFSeCustSatisfyListService service ;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性
	
    @Autowired
	public void init(){
	  	model = new OcrmFSeCustSatisfyList(); 
		setCommonService(service);
	}
    
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String cust_id = request.getParameter("cust_id");
		StringBuilder sb = new StringBuilder("");
			sb.append("select l.*,p.PAPER_NAME from OCRM_F_SE_CUST_SATISFY_LIST l,OCRM_F_SM_PAPERS p where l.PAPERS_ID = p.id and l.cust_id='"+cust_id+"' ");
		SQL = sb.toString();
		datasource = ds;
	}
    
    public String addSatisfy() throws Exception{
    	OcrmFSeCustSatisfyList crl = (OcrmFSeCustSatisfyList)model;
    	service.addSatisfy(crl, title_result);
    	return "success";
    }
    
 

	public void setTitle_result(String titleResult) {
		title_result = titleResult;
	}
}