/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
import com.yuchengtech.bcrm.product.model.ProductFeature;
import com.yuchengtech.bcrm.product.service.ProductFeatureService;
/**
 * @author yaoliang 2011-08-24 产品特征项action
 */
@Action("/product-feature")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "product-feature" }) })
public class ProductFeatureAction extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	private static final long serialVersionUID = 1L;
	private ProductFeature productFeature = new ProductFeature();
	private Collection<ProductFeature> list;
	private Long id;
	@Autowired
	private ProductFeatureService productFeatureService;
	
	private HttpServletRequest request;

	public void validate() {

	}

	public HttpHeaders show() {

		return new DefaultHttpHeaders("show");
	}
	// 查询特征列表

	public HttpHeaders index() {
		list = productFeatureService.getProductFeatureList();

		return new DefaultHttpHeaders("index").disableCaching();

	}

	public String edit() {
		return "edit";
	}

	// 新增前,先创建一个产品特征
	public String editNew() {
		productFeature = new ProductFeature();
		return "editNew";
	}

	// 删除一个产品特征
	public String destroy() {
		productFeatureService.deleteProductFeature(id);
		addActionMessage("remove productFeatureId successful");
		return "success";
	}

	// 获取系统当前时间格式为:YYYY-MM-DD
	public Date getCurrentDate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		String date = format.format(new java.util.Date()).toString();
		return (Date) format.parse(date);
	}

	// 新增产品特征
	public HttpHeaders create() {
		try {
			productFeatureService.saveProductFeature(productFeature);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new DefaultHttpHeaders("success").setLocationId(productFeature.getFeatureId()
				);
	}

	// 修改一条产品特征信息
	public String update() {
		productFeatureService.saveProductFeature(productFeature);
		addActionMessage("product update successful");
		return "success";
	}
	
    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        //productFeatureService.batchRemove(idStr);
        addActionMessage("product removed successfully");
        return "success";
    }

	public void setId(Long id) {
		if (id != null) {
			productFeature = productFeatureService.getProductFeature(id);
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
		return (list != null ? list : productFeature);
	}

}
