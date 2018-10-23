package com.yuchengtech.bob.action;

import java.util.Collection;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bob.model.ClientMemberProduct;
import com.yuchengtech.bob.service.ClientMemberProductService;

@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "client-member-product" }) })
public class ClientMemberProductAction extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	private static final long serialVersionUID = 1569168866929141867L;
	private ClientMemberProduct groupMemberProduct;
	@SuppressWarnings("unused")
	private long id;
	private Collection<ClientMemberProduct> list;
	@Autowired
	private ClientMemberProductService clientMemberProductService;

	// 取得集团客户产品信息列表
	public HttpHeaders index() {
		String groupId = "010101022";
		list = clientMemberProductService.memberProductList(groupId);
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void validate() {

	}

	public Object getModel() {
		return (list != null ? list : groupMemberProduct);
	}
}
