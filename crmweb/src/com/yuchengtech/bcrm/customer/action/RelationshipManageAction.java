package com.yuchengtech.bcrm.customer.action;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustRelate;
import com.yuchengtech.bcrm.customer.service.RelationshipManageService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
/**
 * 
* @ClassName: RelationshipManageAction 
* @Description: 客户视图：客户关联关系
* @author wangmk1 
* @date 2014-7-31 
*
 */
@Action("/relationshipManage")
public class RelationshipManageAction extends CommonAction {
	@Autowired
	private RelationshipManageService relationshipManageService;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds ;
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	@Autowired
	public void init(){
		model = new AcrmFCiCustRelate();
		setCommonService(relationshipManageService);
	}
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId =request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select c.*,tt.id f_id,tt.TEL,tt.MOBILE,tt.membername,tt.CORE_NO from ACRM_F_CI_CUST_RELATE c " +
				" left join acrm_f_ci_per_families tt " +
				" on tt.cust_id=c.cust_id " +
				" where (c.cust_id='"+custId+"' " +
				" or c.cust_no_r='"+custId+"') " +
				" and c.relationship=tt.familyrela  and  c.cust_name_r=tt.membername ");
		SQL = sb.toString();
		datasource = ds ;
		setPrimaryKey("c.ID desc");
		configCondition("c.CUST_NAME", "=", "CUST_NAME", DataType.String);
		configCondition("c.CUST_ID", "=", "CUST_ID", DataType.String);
		configCondition("c.CUST_NAME_R", "=", "CUST_NAME_R", DataType.String);
		configCondition("c.CUST_NO_R", "=", "CUST_NO_R", DataType.String);
	}
	/**
	 * 批量删除 
	 * @return
	 */
	public String batchDestory(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String ids = request.getParameter("idStr");
		//客户关联关系
		String relations = request.getParameter("realtion");
		String custids = request.getParameter("custids");
		ids=ids.substring(0, ids.length()-1);
		relations=relations.substring(0, relations.length()-1);
		custids=custids.substring(0, custids.length()-1);
		//增加对关联关系为配偶时（104）的删除逻辑 
		batchDestroy(custids,relations);

/*	if (relations.equals("104")) {
		batchDestroy(custids,relations);
	}*/
		relationshipManageService.batchRemove(ids);
		return "success";
	}
	
	
	  public String batchDestroy(String custids,String relations){
//   	ActionContext ctx = ActionContext.getContext();
//      request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		String idStr = request.getParameter("idStr");
		String jql="delete from AcrmFCiPerFamilys c where c.custId in ('"+custids+"') and c.familyrela in('"+relations+"') ";
		Map<String,Object> values=new HashMap<String,Object>();
		relationshipManageService.batchUpdateByName(jql, values);
		addActionMessage("batch removed successfully");
        return "success";
}
	
}
