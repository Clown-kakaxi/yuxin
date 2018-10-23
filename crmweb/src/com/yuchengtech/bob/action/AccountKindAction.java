package com.yuchengtech.bob.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bob.model.AccountKind;
import com.yuchengtech.bob.service.AccountKindService;

@Action("/account-kind")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "account-kind" }) })
public class AccountKindAction extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	private static final long serialVersionUID = 8556373616118279765L;
	private AccountKind model = new AccountKind();
	private HttpServletRequest request;
	private Long ID;
	private Collection<AccountKind> list;
	@Autowired
	private AccountKindService accountKindService;

	public HttpHeaders show() {
		return new DefaultHttpHeaders("show");
	}

	public HttpHeaders index() {
		list = accountKindService.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String edit() {
		return "edit";
	}

	public String editNew() {
		model = new AccountKind();
		return "editNew";
	}

	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public String destroy() {
		accountKindService.remove(ID);
		addActionMessage("AccountKind removed successfully");
		return "success";
	}

	public DefaultHttpHeaders create() throws Exception {
		accountKindService.save(model);
		addActionMessage("New AccountKind created successfully");
		return new DefaultHttpHeaders("success").setLocationId(model
				.getKindId());

	}

	public String update() throws Exception {
		accountKindService.save(model);
		addActionMessage("AccountKind updated successfully");
		return "success";
	}

	public String batchDestroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		accountKindService.batchRemove(idStr);
		addActionMessage("AccountKind removed successfully");
		return "success";
	}

	public void validate() {

	}

	public void setId(Long ID) {
		if (ID != null) {
			this.model = accountKindService.find(ID);
		}
		this.ID = ID;
	}

	public Object getModel() {
		return (list != null ? list : model);
	}

}