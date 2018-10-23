package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.service.OrgSearchService;
import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 客户经理认定类
 * @author geyu
 * 2014-7-2
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="customerManagerAffirmQuery",results= {@Result(name = "success", type = "json")})
public class CustomerManagerAffirmQueryAction extends CommonAction{
	@Autowired
    private  CustomerManagerAffirmService  customerManagerAffirmService;
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	OrgSearchService oss;
	    
	 @Autowired
		public void init(){
		  	model = new OcrmFCmCustMgrInfo(); 
			setCommonService(customerManagerAffirmService);
			//新增修改删除记录是否记录日志,默认为false，不记录日志
			needLog=true;
		}
	
	public void prepare() {
        
         StringBuilder sb = new StringBuilder("SELECT A.*,B.ORG_NAME,C.DPT_NAME,F.SUB_BRANCH_ID,F.BRANCH_ID FROM SYS_USERS A LEFT JOIN OCRM_F_CM_CUST_MGR_INFO F ON A.USERID = F.CUST_MANAGER_ID"+
        		" LEFT JOIN SYS_UNITS B ON B.unitid= A.unitid LEFT JOIN ADMIN_AUTH_DPT C ON C.DPT_ID=A.dir_id where (select count(1) from OCRM_F_CM_CUST_MGR_INFO H where H.cust_manager_id = A.userid) = 0 ");
        
         for(String key :this.getJson().keySet()){
        	if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
        		if(key.equals("USERID")){
        			sb.append(" AND A.USERID LIKE '%"+this.getJson().get("USERID")+"%'");
        		}else if(key.equals("USERNAME")){
        			sb.append(" AND A.USERNAME LIKE '%"+this.getJson().get("USERNAME")+"%'");
        		}if(key.equals("ORG_ID")){
        			sb.append(" AND A.UNITID  in (select org_id from admin_auth_org o start with org_id='"+this.getJson().get("ORG_ID")+"' connect by o.up_org_id = prior org_id) ");
        		}
        	}
        }
        SQL = sb.toString();
        setPrimaryKey("A.USERID");
        setBranchFileldName("a.unitid");
        datasource = ds;
    }
	/**
	 * 将用户认定为客户经理
	 */
	public DefaultHttpHeaders confirm(){
		ActionContext ac=ActionContext.getContext();
		request=(HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		String USERID=  request.getParameter("USERID");
		String USERNAME=  request.getParameter("USERNAME");
		String ORG_NAME=  request.getParameter("ORG_NAME");
		String UNITID=request.getParameter("UNITID");
		String DIR_ID=request.getParameter("DIR_ID");
		String DPT_NAME=  request.getParameter("DPT_NAME");
		String CERTIFICATE=  request.getParameter("CERTIFICATE");
		String EDUCATION=  request.getParameter("EDUCATION");
		String BIRTHDAY=  request.getParameter("BIRTHDAY");
		String ENTRANTS_DATE=  request.getParameter("ENTRANTS_DATE");
		String POSITION_TIME=  request.getParameter("POSITION_TIME");
		String FINANCIAL_JOB_TIME=  request.getParameter("FINANCIAL_JOB_TIME");
		String POSITION_DEGREE=  request.getParameter("POSITION_DEGREE");
		String BELONG_BUSI_LINE=  request.getParameter("BELONG_BUSI_LINE");
		String BELONG_TEAM_HEAD=  request.getParameter("BELONG_TEAM_HEAD");
		String SUB_BRANCH_ID=  request.getParameter("SUB_BRANCH_ID");
		String BRANCH_ID=  request.getParameter("BRANCH_ID");
		((OcrmFCmCustMgrInfo)model).setCustManagerId(USERID);
		((OcrmFCmCustMgrInfo)model).setCustManagerName(USERNAME);
		((OcrmFCmCustMgrInfo)model).setAffiInstId(UNITID);
		((OcrmFCmCustMgrInfo)model).setDptId(DIR_ID);
		//((OcrmFCmCustMgrInfo)model).setEntrantsDate();
		((OcrmFCmCustMgrInfo)model).setEducation(EDUCATION);
		((OcrmFCmCustMgrInfo)model).setBelongBusiLine(BELONG_BUSI_LINE);
		((OcrmFCmCustMgrInfo)model).setBelongTeamHead(BELONG_TEAM_HEAD);
		((OcrmFCmCustMgrInfo)model).setFinancialJobTime(FINANCIAL_JOB_TIME);
		((OcrmFCmCustMgrInfo)model).setSubBranchId(SUB_BRANCH_ID);
		((OcrmFCmCustMgrInfo)model).setBranchId(BRANCH_ID);
		customerManagerAffirmService.save(model);
		return new DefaultHttpHeaders("success");
	}
 
}
