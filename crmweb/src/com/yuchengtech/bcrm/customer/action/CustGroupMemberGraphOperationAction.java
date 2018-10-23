package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.customer.service.CustGroupMemberGraphOperationService;
import com.yuchengtech.bcrm.customer.vo.CustGroupGraphDetailVo;

/**
 * @描述：客户管理->客户群组管理->客户群组成员关系图维护功能操作Action
 * @author wzy
 * @date:2013-04-11
 */
@Action("/custGroupMemberGraphOperationAction")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "custGroupMemberGraphOperationAction" }) })
public class CustGroupMemberGraphOperationAction extends ValidationAwareSupport
		implements ModelDriven<Object>, Validateable {

	private HttpServletRequest request;
	private static final long serialVersionUID = 8550661616118279889L;
	private CustGroupGraphDetailVo model = new CustGroupGraphDetailVo();

	@Autowired
	private CustGroupMemberGraphOperationService custGroupMemberGraphOperationService;

	public void validate() {

	}

	public Object getModel() {
		return model;
	}

	// 维护关系图数据时，查询关系图数据详细信息
	public String edit() {
		request = this.getRequest();
		String graphId = request.getParameter("graphId");
		model = custGroupMemberGraphOperationService.getGraphDetail(graphId);
		return "edit";
	}

	// 新增保存客户群组成员关系图数据
	public void saveCustGroupMemberRelationGraph() {
		if (model != null) {
			custGroupMemberGraphOperationService
					.saveCustGroupMemberRelationGraph(model);
		}
	}

	// 获取HttpServletRequest对象
	private HttpServletRequest getRequest() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	}

}