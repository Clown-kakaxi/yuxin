/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.product.model.ProductCategory;
import com.yuchengtech.bcrm.product.service.ProductKindsService;
import com.yuchengtech.crm.exception.BizException;

/**
 * @author yaoliang 2011-08-24 产品类别action
 */
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "product-kinds" }) })
public class ProductKindsAction extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	private static final long serialVersionUID = 1L;
	private ProductCategory productCategory = new ProductCategory();
	private Collection<ProductCategory> list;
	private String id;
	private List productTreeJson;
	@Autowired
	private ProductKindsService productKindsService;

	public void validate() {
	}

	public HttpHeaders show() {
		return new DefaultHttpHeaders("show");
	}

	// 查询类列表

	public HttpHeaders index() {
		list = productKindsService.getProductKindsList();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String edit() {
		return "edit";
	}

	// 新增前,先创建一个产品类别
	public String editNew() {
		productCategory = new ProductCategory();
		return "editNew";
	}

	// 删除一个产品类别
	public String destroy() {
		try {
			productKindsService.deleteProductKinds(id);
			addActionMessage("remove productInfo successful");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
	}

	// 获取系统当前时间格式为:YYYY-MM-DD
	public Date getCurrentDate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		String date = format.format(new java.util.Date()).toString();
		return (Date) format.parse(date);
	}

	// 新增产品类
	public HttpHeaders create() {
		try {
			productKindsService.saveProductKinds(productCategory);
		} catch (BizException e) {
			throw e;
		}catch(Exception e){
    		e.printStackTrace();
    		throw new BizException(1,2,"1002",e.getMessage());
    	}
		return new DefaultHttpHeaders("success").setLocationId(productCategory.getCatlCode());
	}

	// 修改一条产品类别信息
	public String update() {
		productKindsService.saveProductKinds(productCategory);
		addActionMessage("product update successful");
		return "success";
	}

	public void setId(String id) {
		if (id != null) {
			productCategory = productKindsService.getProductKinds(id);
		}
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public void getProductTreeJson(){
		productTreeJson = new ArrayList();
		String json1 = "{text:'资产产品',id:'deposits'}";
		JSONObject obj = JSONObject.fromObject(json1);
		JSONObject obj2 = JSONObject.fromObject("{text:'负债产品',id:'loanProduct'}");
		JSONObject obj3 = JSONObject.fromObject("{text:'中间业务产品',id:'midProduct'}");
		productTreeJson.add(obj);
		productTreeJson.add(obj2);
		productTreeJson.add(obj3);
	}

	public Object getModel() {
		return (list != null ? list : productCategory);
	}

}
