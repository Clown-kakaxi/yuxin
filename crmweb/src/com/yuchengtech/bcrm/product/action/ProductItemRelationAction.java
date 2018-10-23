/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.product.model.ProductItemRelation;
import com.yuchengtech.bcrm.product.service.ProductItemRelationService;
/**
 * @author yaoliang 2011-08-24 产品与业务数据对于关系action
 */
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "product-item-relation" }) })
public class ProductItemRelationAction extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	private static final long serialVersionUID = 1L;
	private ProductItemRelation ProductItemRelation = new ProductItemRelation();
	private Collection<ProductItemRelation> list;
	private String id; 
	@Autowired
	private ProductItemRelationService productItemRelationService;

	public void validate() {

	}
	public HttpHeaders show() {

		return new DefaultHttpHeaders("show");
	}
	// 查询特征列表

	public HttpHeaders index() {
		list = productItemRelationService.getProductItemRelationList();

		return new DefaultHttpHeaders("index").disableCaching();

	}

	public String edit() {
		return "edit";
	}

	// 新增前,先创建一个产品对应数据关系
	public String editNew() {
		ProductItemRelation = new ProductItemRelation();
		return "editNew";
	}

	// 删除一个关系
	public String destroy() {

	try {
		productItemRelationService.deleteProductItemRelatione(id);
			addActionMessage("remove productFeatureId successful");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
	}


	public Date getCurrentDate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		String date = format.format(new java.util.Date()).toString();
		return (Date) format.parse(date);
	}

	
	public HttpHeaders create() {
		try {
			productItemRelationService.saveProductFeature(ProductItemRelation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new DefaultHttpHeaders("success").setLocationId(ProductItemRelation.getId()
				);
	}

	
	public String update() {
		productItemRelationService.saveProductFeature(ProductItemRelation);
		addActionMessage("product update successful");
		return "success";
	}

	public void setId(String id) {
		if (id != null) {
			ProductItemRelation = productItemRelationService.getProductItemRelation(id+"");
		}
		this.id = id;
	}
	
	public Object getModel() {
//		
//		HttpServletRequest req = ServletActionContext.getRequest();
//		
//		String id = req.getParameter("id");
//		
//		if(productTreeJson!=null && productTreeJson.size()>=0){
//			return productTreeJson;
//		}
		return (list != null ? list : ProductItemRelation);
	}

}
