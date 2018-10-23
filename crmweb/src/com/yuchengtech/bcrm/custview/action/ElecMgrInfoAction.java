package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiCustElecMgrInfo;
import com.yuchengtech.bcrm.custview.service.ElecMgrInfoService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/ElecMgrInfo")
public class ElecMgrInfoAction  extends CommonAction{
    @Autowired
    private ElecMgrInfoService elecMgrInfoService ;
    @Autowired
	public void init(){
	  	model = new AcrmFCiCustElecMgrInfo(); 
		setCommonService(elecMgrInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
    
    //（自定义）批量删除
    public String batchDestroy(){
 
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql="delete from AcrmFCiCustElecMgrInfo c where c.id in ("+idStr+")";
			Map<String,Object> values=new HashMap<String,Object>();
			elecMgrInfoService.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
	        return "success";

    }
    //分页查询
    public HttpHeaders indexPage() throws Exception{
      try{	
    	StringBuilder sb=new StringBuilder("select c from AcrmFCiCustElecMgrInfo c where 1=1 ");
    	Map<String,Object> values=new HashMap<String,Object>();
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("start")!=null)
        	start = new Integer(request.getParameter("start")).intValue();
        if(request.getParameter("limit")!=null)
        	limit = new Integer(request.getParameter("limit")).intValue();
		this.setJson(request.getParameter("condition"));
//		if(request.getParameter("cust_id") !=null){
//			StringBuffer aa =new StringBuffer("'");
//			aa.append(request.getParameter("cust_id")).append("'");
//			sb.append(" and c.custId ='"+request.getParameter("cust_id")+"'");
////			sb.append(" and c.custId = "+aa  );
//		}
		if(condition!=null||!"".equals(condition)){
			sb.append("and c.custId = :custId");
			values.put("custId",request.getParameter("cust_id"));
		}
		
    	for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("custType")){
                    sb.append("and c.custType like :custType");
                    values.put("custType", "%"+(String)this.getJson().get(key)+"%");
                } else if(key.equals("fileType")){
                    sb.append(" and c.fileType like :fileType");
                    values.put("fileType", "%"+(String)this.getJson().get(key)+"%");
                } else if(key.equals("fileName")){
                    sb.append(" and c.fileName like :fileName");
                    values.put("fileName", "%"+(String)this.getJson().get(key)+"%");
                } else if(key.equals("id")){
                    sb.append(" and c.id = :id");
                    values.put("id", Long.parseLong((String) this.getJson().get(key)));
                } else{
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
}