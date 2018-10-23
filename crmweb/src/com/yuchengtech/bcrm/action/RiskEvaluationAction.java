package com.yuchengtech.bcrm.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.service.RiskEvaluationService;
import com.yuchengtech.bcrm.system.model.OcrmFSeCustRiskInfoList;
import com.yuchengtech.bob.common.CommonAction;
@SuppressWarnings("serial")
@Action("/RiskEvaluation")
public class RiskEvaluationAction  extends CommonAction{

    private  String  title_result;
	@Autowired
    private RiskEvaluationService riskEvaluationService ;
    @Autowired
	public void init(){
	  	model = new OcrmFSeCustRiskInfoList(); 
		setCommonService(riskEvaluationService);
	}
    
    
    public String addCustRiskEvaluation() throws Exception{
    	OcrmFSeCustRiskInfoList crl = (OcrmFSeCustRiskInfoList)model;
    		riskEvaluationService.addCustRiskEvaluation(crl, title_result);
    	return "success";
    }
    
    public String updateCustRiskEvaluation() throws Exception{
    	OcrmFSeCustRiskInfoList crl = (OcrmFSeCustRiskInfoList)model;
    	riskEvaluationService.save(crl);
    	return "success";
    }
    public String loadCustRiskQa() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custqId = request.getParameter("custqId");
    	if(custqId!=null &&!"".equals(custqId)){
    		json = riskEvaluationService.loadCustRiskQa(new Long(custqId));
    	}
    	return "success";
    }
 
    //分页查询
    public HttpHeaders indexPage() throws Exception{
      try{	
    	StringBuilder sb=new StringBuilder("select c from OcrmFSeCustRiskInfoList c where 1=1 ");
    	Map<String,Object> values=new HashMap<String,Object>();
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("start")!=null)
        	start = new Integer(request.getParameter("start")).intValue();
        if(request.getParameter("limit")!=null)
        	limit = new Integer(request.getParameter("limit")).intValue();
		this.setJson(request.getParameter("condition"));
    	for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("custName")){
                    sb.append(" and c.custName like :custName");
                    values.put("custName", "%"+(String)this.getJson().get(key)+"%");
                }
                else if(key.equals("riskCharactType")){
                    sb.append(" and c.riskCharactType like :riskCharactType" );
                    values.put("riskCharactType", "%"+(String)this.getJson().get(key)+"%");
                }	
//                else if(key.equals("id")){
//                    sb.append(" and c.id = :id");
//                    values.put("id", Long.parseLong((String) this.getJson().get(key)));
//                } 
                else{
                	sb.append(" and c."+key+" = :"+key);
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


	public void setTitle_result(String titleResult) {
		title_result = titleResult;
	}
}