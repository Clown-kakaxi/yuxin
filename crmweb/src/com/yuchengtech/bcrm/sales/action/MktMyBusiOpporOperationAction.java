package com.yuchengtech.bcrm.sales.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

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
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOppor;
import com.yuchengtech.bcrm.sales.service.MktMyBusiOpporOperationService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：营销管理->商机管理->我的商机功能操作Action
 * @author wzy
 * @date:2013-02-28
 */
@Action("/mktMyBusiOpporOperationAction")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "mktBusiOpporOperationAction" }) })
public class MktMyBusiOpporOperationAction extends ValidationAwareSupport
		implements ModelDriven<Object>, Validateable {

	private HttpServletRequest request;
	private static final long serialVersionUID = 8550661616118279889L;
	private OcrmFMmMktBusiOppor model = new OcrmFMmMktBusiOppor();

	private Collection<OcrmFMmMktBusiOppor> list;
	@Autowired
	private MktMyBusiOpporOperationService mktMyBusiOpporOperationService;

	public void validate() {

	}

	public Object getModel() {
		return (list != null ? list : model);
	}

	// 关闭商机
	public void closeBusiOppor() {
		AuthUser auth = null;
		request = this.getRequest();
		String closeType = request.getParameter("closeType");
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String failure_reason=request.getParameter("failure_reason");
		mktMyBusiOpporOperationService.closeBusiOppor(model, closeType,failure_reason, auth);
	}

	// 获取HttpServletRequest对象
	private HttpServletRequest getRequest() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	}

}