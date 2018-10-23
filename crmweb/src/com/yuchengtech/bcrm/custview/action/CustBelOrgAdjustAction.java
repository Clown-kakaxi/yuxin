package com.yuchengtech.bcrm.custview.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.service.OrgSearchService;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bcrm.custview.service.CustMgrBelongService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户归属机构调整	
 */
@SuppressWarnings("serial")
@Action("/cust_bel_org_adjust")
public class CustBelOrgAdjustAction extends CommonAction {
	
	@Autowired
	private CustMgrBelongService custMgrBelongService;
	@Autowired
	private OrgSearchService orgSearchService;

	@Autowired
	public void init() {
		model = new OcrmFCiBelongOrg();
		setCommonService(custMgrBelongService);
		// 新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog = false;;
	}
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	private HttpServletRequest request;
	public void prepare(){

    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

    	String cust_id = request.getParameter("cust_id");
        StringBuffer sb = new StringBuffer("select t.* ,m.UNITSEQ from OCRM_F_CI_BELONG_ORG t left join sys_units m on t.institution_code=m.UNITID where 1=1 ");
        if(!cust_id.equals("")){
        	sb.append(" and t.cust_id = '"+cust_id+"'");
        }
        	SQL=sb.toString();
			datasource = ds;
    
	}
    public void searchMainOrg(){

    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cust_id = request.getParameter("cust_id");
    	String main_type = request.getParameter("main_type");
        StringBuffer sb = new StringBuffer("select 0 as parent_id ,t.institution_code,t.institution_name from OCRM_F_CI_BELONG_ORG t where 1>0");
        if(!main_type.equals("")){
        	sb.append(" and t.main_type = '"+main_type+"'");
        }
        if(!cust_id.equals("")){
        	sb.append(" and t.cust_id = '"+cust_id+"'");
        }
        	SQL=sb.toString();
			datasource = ds;
    
	}
	
	
	/**
	 * 通过获得ID查询客户归属机构
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public HttpHeaders indexPage() throws Exception {
		try {
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//查找该机构下的下级机构
			Map<String,Object> subOrgsMap = orgSearchService.searchSubOrgs(auth.getUnitId());
			Map<String, Object> belOrgMap = null;
			StringBuilder sb = new StringBuilder(
					"select bo from OcrmFCiBelongOrg bo where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			// 获取传过来的参数
			String custId = request.getParameter("cust_id");
			//sb.append(" and bo.custId = '" + custId+"'");
			sb.append("and bo.custId = :custId");
			values.put("custId",custId);
			super.indexPageByJql(sb.toString(), values);
			belOrgMap = (Map<String, Object>) this.json.get("json");
			//归属机构信息表，获取该客户已经分配了的机构
			List<OcrmFCiBelongOrg> belOrgList = (List<OcrmFCiBelongOrg>) belOrgMap.get("data");
			//获取该登录者所在机构的直接下级机构
			List subOrgsList = (List<OcrmFCiBelongOrg>) subOrgsMap.get("data");
			List<CustBelOrg> resultList = new ArrayList<CustBelOrg>();
			int count = 0;
			//合并归属客户经理信息和机构客户经理表信息到resultList中
			for (int i = 0; i < subOrgsList.size(); i++) {
				CustBelOrg cbo = new CustBelOrg();
				Map res = (Map)subOrgsList.get(i);
				String unitId = (String) res.get("UNITID");
				String unitName = (String) res.get("UNITNAME");
				OcrmFCiBelongOrg obc = null;
				cbo.custId = custId;
				cbo.institutionCode = unitId;
				cbo.institutionName = unitName;
				cbo.mainType = "";
				//该客户经理对同一个客户只能分配一次
				for (int j = 0; j < belOrgList.size(); j++) {
					//如果客户经理已经分配了，则查找相应的记录并赋对应的值
					if (unitId.equals(belOrgList.get(j).getInstitutionCode())) {
						obc = belOrgList.get(j);
						cbo.mainType = obc.getMainType();
						cbo.oId = obc.getId();
					} 
				}
				count = i + 1;
				resultList.add(cbo);
			}
			//设置为JSON格式的map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", resultList);
			map.put("count", count);
			Map <String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("json", map);
			this.setJson(resultMap);
			return new DefaultHttpHeaders("success")
		    .disableCaching();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//客户归属机构
	public class CustBelOrg {
		private Long oId;		//id
		private String custId;	//客户编号
		private String institutionCode;	//归属机构编号
		private String institutionName;	//归属机构名称
		private String mainType;		//主协办类型
		public String getCustId() {
			return custId;
		}
		public Long getoId() {
			return oId;
		}
		public void setoId(Long oId) {
			this.oId = oId;
		}
		public void setCustId(String custId) {
			this.custId = custId;
		}
		public String getInstitutionCode() {
			return institutionCode;
		}
		public void setInstitutionCode(String institutionCode) {
			this.institutionCode = institutionCode;
		}
		public String getInstitutionName() {
			return institutionName;
		}
		public void setInstitutionName(String institutionName) {
			this.institutionName = institutionName;
		}
		public String getMainType() {
			return mainType;
		}
		public void setMainType(String mainType) {
			this.mainType = mainType;
		}
		
	}
}