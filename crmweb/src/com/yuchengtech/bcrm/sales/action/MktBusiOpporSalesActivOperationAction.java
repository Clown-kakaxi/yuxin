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
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktSalesActiv;
import com.yuchengtech.bcrm.sales.service.MktMyBusiOpporOperationService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：营销管理->商机管理->我的商机->销售活动功能操作Action
 * @author wzy
 * @date:2013-03-01
 */
@Action("/mktBusiOpporSalesActivOperationAction")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "MktBusiOpporSalesActivOperationAction" }) })
public class MktBusiOpporSalesActivOperationAction extends
		ValidationAwareSupport implements ModelDriven<Object>, Validateable {

	private HttpServletRequest request;
	private static final long serialVersionUID = 8550661616118279889L;
	private OcrmFMmMktSalesActiv model = new OcrmFMmMktSalesActiv();

	private Collection<OcrmFMmMktBusiOppor> list;
	@Autowired
	private MktMyBusiOpporOperationService mktMyBusiOpporOperationService;

	public void validate() {

	}

	public Object getModel() {
		return (list != null ? list : model);
	}

	// 保存/更新销售活动信息
	public void saveOrUpdateBusiOpporActiv() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		mktMyBusiOpporOperationService.saveOrUpdateBusiOpporActiv(model, auth);
	}

	// 根据商机ID集合，删除对应的销售活动记录
	public void delBusiOpporActivByIds() {
		request = this.getRequest();
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String salesActivIds = request.getParameter("salesActivIds");
		mktMyBusiOpporOperationService.delBusiOpporActivByIds(salesActivIds,
				auth);
	}

	// 获取HttpServletRequest对象
	private HttpServletRequest getRequest() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	}

}