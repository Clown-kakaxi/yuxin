package com.yuchengtech.bcrm.sales.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOppor;
import com.yuchengtech.bcrm.sales.service.AddMarketProdService;
import com.yuchengtech.bcrm.sales.service.MktBusiOpporOperationService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @描述：营销管理->商机管理->商机池功能操作Action
 * @author wzy
 * @date:2013-02-22
 */
@Action("/mktBusiOpporOperationAction")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "mktBusiOpporOperationAction" }) })
public class MktBusiOpporOperationAction extends ValidationAwareSupport
		implements ModelDriven<Object>, Validateable {

	private HttpServletRequest request;
	private static final long serialVersionUID = 8550661616118279889L;
	private OcrmFMmMktBusiOppor model = new OcrmFMmMktBusiOppor();

	private Collection<OcrmFMmMktBusiOppor> list;
	@Autowired
	private MktBusiOpporOperationService mktBusiOpporOperationService;
	  @Autowired
	    private AddMarketProdService addmarketprodservice ;
	    @Autowired
		private TargetCusSearchService targetCusSearchService;
	public void validate() {

	}

	public Object getModel() {
		return (list != null ? list : model);
	}

	// 保存/更新商机信息
	public void saveOrUpdateBusiOppor() {
		request = this.getRequest();
		String id = request.getParameter("groupId");
		String ifgroup=request.getParameter("ifgroup");
		String custStr=request.getParameter("custStr");
		String flag=request.getParameter("flag");//判断是否是组件创建商机
		String executeUserName=request.getParameter("executeUserName");
		String executeOrgName=request.getParameter("executeOrgName");
		if("true".equals(flag)){
			model.setExecuteOrgName(executeOrgName);
			model.setExecuteUserName(executeUserName);
		}
		AuthUser auth = null;
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (model != null) {
			if (model.getOpporId() != null && !"".equals(model.getOpporId())) {// 更新
				mktBusiOpporOperationService.updateBusiOppor(model, auth);
			} else {// 新增
				if("true".equals(ifgroup))
					saveBusiOppor(model, auth,custStr,id);//客户群部分新增
				else
					mktBusiOpporOperationService.saveBusiOppor(model, auth);
			}
		}
	}

	public void saveBusiOppor(OcrmFMmMktBusiOppor model,AuthUser auth,String custStr,String id
			){
		
		StringBuffer buffer=new StringBuffer();
		String[] ids=custStr.split(",");
		for(int i=0;i<ids.length;i++){
			buffer.append("'"+ids[i]+"',");
		}
		String ids_r=buffer.substring(0,buffer.length()-1);
		String sql="";
		//获取客户群信息
		List<Object[]> listall =new ArrayList<Object[]>();
			if(!"".equals(custStr)){
				sql="select t.cust_id,t.cust_type,t.cust_name,t.cust_stat,t.linkman_name,t1.institution_code,t1.institution_name,t2.mgr_id,t2.mgr_name "+
				" from ACRM_F_CI_CUSTOMER t "+
				" left join OCRM_F_CI_BELONG_ORG t1 on t.cust_id = t1.cust_id "+
				" left join OCRM_F_CI_BELONG_CUSTMGR t2 on t.cust_id=t2.cust_id "+
				" where t.cust_id in("+ids_r+")";
				listall=addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(sql);
				
			}else if("".equals(custStr)){
				sql="select t.cust_id,t.cust_type,t.cust_name,t.cust_stat,t.linkman_name,t1.institution_code,t1.institution_name,t2.mgr_id,t2.mgr_name "+
				" from ACRM_F_CI_CUSTOMER t "+
				" left join OCRM_F_CI_BELONG_ORG t1 on t.cust_id = t1.cust_id "+
				" left join OCRM_F_CI_BELONG_CUSTMGR t2 on t.cust_id=t2.cust_id "+
				" where t.cust_id in (select cust_id from OCRM_F_CI_RELATE_CUST_BASE where cust_base_id = '"+ id +"')";
				listall=addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(sql);
			}
		
//		//获取客户群信息
//		List<Object[]> list = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select  id,CUST_FROM from " +
//				"OCRM_F_CI_BASE where id='"+groupId+"'");
//		
//		List<Object[]>  listall = null;
//		if(list!=null&&list.size()>0){
//			Object[] o = list.get(0);
//			String custForm = (o[1]==null)?"":o[1].toString();
//			if("2".equals(custForm)){//动态客户群，需要通过客户群筛选条件查询客户
//				List<Object[]> list1 = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select ss_col_item,ss_col_op,ss_col_value,ss_col_join " +
//						"from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+groupId+"')");
//				if(list1!=null&&list1.size()>0){
//					JSONArray jaCondition = new JSONArray();
//					String	radio = "true";
//					for(int i=0;i<list1.size();i++){
//						Object[] oo = list1.get(i);
//						radio = (oo[3]==null)?"":oo[3].toString();
//						Map map = new HashMap<String,Object>();
//		    			map.put("ss_col_item", (oo[0]==null)?"":oo[0].toString());
//		    			map.put("ss_col_op", (oo[1]==null)?"":oo[1].toString());
//		    			map.put("ss_col_value", (oo[2]==null)?"":oo[2].toString());
//						jaCondition.add(map);
//					}
//					//客户筛选
//					String res = targetCusSearchService.generatorSql(jaCondition,radio);	
//					listall = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
//							"cust_id,cust_name as cust_zh_name,cust_Type as cust_typ,cust_stat,linkman_name as link_user  from  ACRM_F_CI_CUSTOMER where cust_id in ( select t.cust_id from ("+res+") t)");
//	    			
//				}
//			}else {
//				//静态客户群，直接关联客户查询
//				listall = addmarketprodservice.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
//						"a.cust_id,a.cust_zh_name,b.cust_type as cust_typ,b.cust_stat,b.linkman_name as link_user  from ocrm_f_ci_relate_cust_base a left join ACRM_F_CI_CUSTOMER b on " +
//							"a.cust_id=b.cust_id where cust_base_id='"+groupId+"'");
//			}
//		
//		}
		mktBusiOpporOperationService.saveBusiOppor(model, auth,listall);
		
	}
	// 提交商机信息
	public void submitBusiOppor() {
		AuthUser auth = null;
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		request = this.getRequest();
		if (model != null) {
			String flag=request.getParameter("flag");//判断是否是组件创建商机
			String executeUserName=request.getParameter("executeUserName");
			String executeOrgName=request.getParameter("executeOrgName");
			if("true".equals(flag)){
				model.setExecuteOrgName(executeOrgName);
				model.setExecuteUserName(executeUserName);
			}
			mktBusiOpporOperationService.submitBusiOppor(model, auth,
					this.getResponse());
		}
	}

	// 分配商机信息
	public void allocatBusiOppor() {
		AuthUser auth = null;
		if (model != null) {
			auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			mktBusiOpporOperationService.allocatBusiOppor(model, auth);
		}
	}

	// 退回商机
	public void backBusiOppor() {
		int result = 1;
		AuthUser auth = null;
		if (model != null) {
			auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			request = this.getRequest();
			String reason=request.getParameter("reason");
			result = mktBusiOpporOperationService.backBusiOppor(model, reason,auth);
			if (result == 1) {
				throw new BizException(1, 0, "100010", "您不符合退回当前商机的条件！");
			}
		}
	}

	// 根据商机ID，删除对应的商机记录
	public void delBusiOpporById() {
		String result = null;
		request = this.getRequest();
		AuthUser auth = null;
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String opporIdS = request.getParameter("opporIdS");
		result = mktBusiOpporOperationService.delBusiOpporByIdS(opporIdS, auth);
		if (result != null && !"".equals(result)) {
			throw new BizException(1, 0, "100010", result);
		}
	}

	// 认领商机
	public void claimBusiOppor() {
		AuthUser auth = null;
		request = this.getRequest();
		String opporIdS = null;// 商机记录ID集合
		String claimType = null;// 认领方式，0：客户经理认领；1：机构认领
		opporIdS = request.getParameter("opporIdS");
		claimType = request.getParameter("claimType");
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		mktBusiOpporOperationService.claimBusiOppor(opporIdS, claimType, auth);
	}

	// 认领商机审批
	public void claimAuditBusiOppor() {
		AuthUser auth = null;
		request = this.getRequest();
		String opporIdS = null;// 商机记录ID集合
		String auditResult = null;// 审批结果，0：通过；1：不通过
		opporIdS = request.getParameter("opporIdS");
		auditResult = request.getParameter("auditResult");
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		mktBusiOpporOperationService.claimAuditBusiOppor(opporIdS, auditResult,
				auth, model);
	}

	// 根据机构ID，查询对应的机构主管
	// 查询逻辑：
	// 1、在用户表中，根据机构ID，查询出该机构下的所有用户
	// 2、在用户和角色关联表中，根据用户和角色关联关系，找出角色为“主管”的用户
	// 其中，“主管角色”编码，是在代码中写死的，在不同的行实施时，需要根据具体情况进行修改
	public void getOrgManager() {
		String ogrId = null;
		String orgManager = null;
		request = this.getRequest();
		ogrId = request.getParameter("orgId");
		orgManager = mktBusiOpporOperationService.getOrgManager(ogrId);
		try {
			this.getResponse().getWriter()
					.write(orgManager == null ? "" : orgManager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 判断当前用户是否能退回选中的商机
	// 判断逻辑：
	// 1、客户经理只能退回分配给自己的商机，客户只有当前一个归属客户经理时不能退回
	// 2、客户主管可以退回分配给本机构的商机，并且只能退回客户没有归属机构的商机
	public void canReturn() {
		String userType = null;
		String opporId = null;
		AuthUser auth = null;
		String canReturnResult = "true";
		request = this.getRequest();
		userType = request.getParameter("userType");
		opporId = request.getParameter("opporId");
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		canReturnResult = mktBusiOpporOperationService.canReturn(userType,
				opporId, auth);
		try {
			this.getResponse().getWriter().write(canReturnResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取HttpServletRequest对象
	private HttpServletRequest getRequest() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	}

	// 获取HttpServletResponse对象
	private HttpServletResponse getResponse() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletResponse) ctx
				.get(ServletActionContext.HTTP_RESPONSE);
	}

	// 从客户群组功能点批量创建商机(新增保存)
	public void pitchCreateBusiOpporFromCustGroup() {
		AuthUser auth = null;
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (model != null) {
			mktBusiOpporOperationService.pitchCreateBusiOpporFromCustGroup(
					model, auth);
		}
	}

	// 从客户群组功能点批量创建商机(新增提交)
	public void pitchCreateSubmitBusiOpporFromCustGroup() {
		AuthUser auth = null;
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (model != null) {
			mktBusiOpporOperationService
					.pitchCreateSubmitBusiOpporFromCustGroup(model, auth);
		}
	}

}