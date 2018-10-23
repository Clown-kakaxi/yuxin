package com.yuchengtech.bcrm.customer.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiLatentApplyInfo;
import com.yuchengtech.bcrm.customer.service.BelongCustmgrService;
import com.yuchengtech.bcrm.customer.service.OcrmFCiLatentApplyInfoService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户审批
 *
 */
@SuppressWarnings("serial")
@Action("/ocrm_f_ci_latent_apply_info")
public class OcrmFCiLatentApplyInfoAction  extends CommonAction{
    @Autowired
    private OcrmFCiLatentApplyInfoService ocrmFCiLatentApplyInfoService ;
    @Autowired
    private BelongCustmgrService belongCustmgrService;
    @Autowired
	public void init(){
	  	model = new OcrmFCiLatentApplyInfo(); 
		setCommonService(ocrmFCiLatentApplyInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
  //批量审批
    public String batchApprove() throws Exception{
    	try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			//调用批量审批方法
			ocrmFCiLatentApplyInfoService.batchApprove(idStr);
	        return "success";
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    }
    
  //审批通过
    public String approvel() throws Exception{
    	try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			//调用审批方法
			ocrmFCiLatentApplyInfoService.approve(idStr);
	        return "success";
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    }
    
    /**
     * 审批不通过
     * @return
     * @throws Exception 
     */
    public String approvelBack() throws Exception {
    	try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql="delete from OcrmFCiLatentApplyInfo c where c.claimtagNo= '"+Long.parseLong(idStr)+"'";
			Map<String,Object> values=new HashMap<String,Object>();
			ocrmFCiLatentApplyInfoService.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
	        return "success";
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    }

    //分页查询
    public HttpHeaders indexPage() throws Exception{
      try{	
    	StringBuilder sb=new StringBuilder("select lai from OcrmFCiLatentApplyInfo lai where 1=1 ");
    	Map<String,Object> values=new HashMap<String,Object>();
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("start")!=null)
        	start = new Integer(request.getParameter("start")).intValue();
        if(request.getParameter("limit")!=null)
        	limit = new Integer(request.getParameter("limit")).intValue();
		this.setJson(request.getParameter("condition"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Set<String> k = this.getJson().keySet();
    	for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("approvelStatus")){
                    sb.append(" and lai.approvelStatus like :approvelStatus");
                    values.put("approvelStatus", "%"+(String)this.getJson().get(key)+"%");
                }
                else if (key.equals("applyUsername")) {
                	 sb.append(" and lai.applyUsername like :applyUsername");
                     values.put("applyUsername", "%"+(String)this.getJson().get(key)+"%");
                }
                else if(key.equals("custId")){
                    sb.append(" and lai.custId = :custId");
                    values.put("custId", (String) this.getJson().get(key));
                } 
                else if(key.equals("claimtagNo")){
                    sb.append(" and lai.claimtagNo = :claimtagNo");
                    values.put("claimtagNo", Long.parseLong((String) this.getJson().get(key)));
                } 
                else if(key.equals("applyPeriod")){
                    sb.append(" and lai.applyPeriod = :applyPeriod");
                    values.put("applyPeriod", Long.parseLong((String) this.getJson().get(key)));
                }
                else{
                	sb.append(" and lai."+key+" = :"+key);
                	values.put(key, this.getJson().get(key));
                }
            }
        }
    	return super.indexPageByJql(sb.toString(), values);
      }catch(Exception e){
    	  e.printStackTrace();
    	  throw e;
      }
	}
    //自定义查询
    public HttpHeaders query() throws Exception{
      try{	
    	StringBuilder sb=new StringBuilder("select lai from OcrmFCiLatentApplyInfo lai where 1=1 ");
    	Map<String,Object> values=new HashMap<String,Object>();
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //获取查询条件，查询条件用condition封装
        this.setJson(request.getParameter("condition"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	if(key.equals("approvelStatus")){
                    sb.append("and lai.approvelStatus like :approvelStatus");
                    values.put("approvelStatus", "%"+(String)this.getJson().get(key)+"%");
                }
                else if (key.equals("applyUsername")) {
                	 sb.append("and lai.applyUsername like :applyUsername");
                     values.put("applyUsername", "%"+(String)this.getJson().get(key)+"%");
                }
                else if(key.equals("claimtagNo")){
                    sb.append(" and lai.claimtagNo = :claimtagNo");
                    values.put("claimtagNo", Long.parseLong((String) this.getJson().get(key)));
                } 
                else{
                	sb.append(" and lai."+key+" = :"+key);
                	values.put(key, this.getJson().get(key));
                	
                }
            }
        }
    	return super.indexByJql(sb.toString(), values);
      }catch(Exception e){
    	  e.printStackTrace();
    	  throw e;
      }
	}
    
}