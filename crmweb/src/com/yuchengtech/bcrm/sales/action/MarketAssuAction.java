//package com.yuchengtech.bcrm.sales.action;
//
//import java.util.Collection;
//
//import javax.servlet.http.HttpServletRequest;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Result;
//import org.apache.struts2.convention.annotation.Results;
//import org.apache.struts2.rest.DefaultHttpHeaders;
//import org.apache.struts2.rest.HttpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.opensymphony.xwork2.ActionContext;
//import com.opensymphony.xwork2.ModelDriven;
//import com.opensymphony.xwork2.Validateable;
//import com.opensymphony.xwork2.ValidationAwareSupport;
//import com.yuchengtech.bcrm.sales.model.OcrmFMmTask;
//import com.yuchengtech.bcrm.sales.service.MarketAssuInfoService;
//
//@Action("/market-assu")
//@Results({
//    @Result(name="success", type="redirectAction", params = {"actionName" , "market-assu"})
//})
//public class MarketAssuAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
//
//	private static final long serialVersionUID = 8550661616118279889L;
//	private OcrmFMmTask model = new OcrmFMmTask();
//	private Long planId;
//	private HttpServletRequest request;
//	private Collection<OcrmFMmTask> list;
//	@Autowired
//	private MarketAssuInfoService marketPlanService;
//	
//    public HttpHeaders show() {
//        return new DefaultHttpHeaders("show");
//    }
//
//    public HttpHeaders index() {
//        list = marketPlanService.findAll();
//        return new DefaultHttpHeaders("index")
//            .disableCaching();
//    }
//    
//    public String edit() {
//        return "edit";
//    }
//
//    public String editNew() {
//    	model = new OcrmFMmTask();
//        return "editNew";
//    }
//
//    public String deleteConfirm() {
//        return "deleteConfirm";
//    }
//
//    public String destroy() {
//    	marketPlanService.remove(planId);
//        addActionMessage("marketPlan removed successfully");
//        return "success";
//    }
//
//    public DefaultHttpHeaders create() {
//    	ActionContext ctx = ActionContext.getContext();
//        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//        if (request.getParameter("operate").equals("delete")) {
//			String s = request.getParameter("cbid");
//			JSONObject jsonObject = JSONObject.fromObject(s);
//			JSONArray jarray = jsonObject.getJSONArray("id");
//			  if(jarray.size()>0)
//			     {
//			   for (int i=0;i<jarray.size();i++)
//		   {
//				     Long id=Long.parseLong(jarray.get(i).toString());
//				     marketPlanService.remove(id);
//		       }
//		    }
//			    return new DefaultHttpHeaders("success")
//	            .setLocationId(model.getTaskId());
//		}
//        if(request.getParameter("operate").equals("opr")){
//			String s = request.getParameter("cbid");
//			JSONObject jsonObject = JSONObject.fromObject(s);
//			JSONArray jarray = jsonObject.getJSONArray("id");
//			  if(jarray.size()>0)
//			     {
//			   for (int i=0;i<jarray.size();i++)
//		   {
//				     Long id=Long.parseLong(jarray.get(i).toString());
//				     marketPlanService.oper(id);
//		       }
//		    }
//			    return new DefaultHttpHeaders("success")
//	            .setLocationId(model.getTaskId());
//		}
//        if(request.getParameter("operate").equals("distribut")){
//			String s = request.getParameter("cbid");
//			JSONObject jsonObject = JSONObject.fromObject(s);
//			JSONArray jarray = jsonObject.getJSONArray("id");
//			  if(jarray.size()>0)
//			     {
//			   for (int i=0;i<jarray.size();i++)
//		   {
//				     String id=String.valueOf(jarray.get(i).toString());
//				     model.setTaskId(null);
//				     marketPlanService.save(model,id);
//		       }
//		    }
//			    return new DefaultHttpHeaders("success")
//	            .setLocationId(model.getTaskId());
//		}
//        else{
//    	marketPlanService.save(model,"0");
//        addActionMessage("New marketPlan created successfully");
//        return new DefaultHttpHeaders("success")
//            .setLocationId(model.getTaskId());
//        }
//        
//    }
//
//    public String update() {
//    	marketPlanService.save(model,"0");
//        addActionMessage("marketPlan updated successfully");
//        return "success";
//    }
//    
//    //关闭营销计划
//    public String closePlan() {
//    	marketPlanService.closePlan(model);
//        addActionMessage("marketPlan closed successfully");
//        return "success";
//    }
//    
//    //执行营销计划
//    public String planExecute()
//    {
//    	marketPlanService.saveActivity(model);
//        addActionMessage("marketPlan executed successfully");
//        return "success";
//    }
//
//    public void validate() {
//
//    }
//
//    public void setId(Long planId) {
//        if (planId != null) {
//            this.model = marketPlanService.find(planId);
//        }
//        this.planId = planId;
//    }
//    public Object getModel() {
//        return (list != null ? list : model);
//    }
//
//}