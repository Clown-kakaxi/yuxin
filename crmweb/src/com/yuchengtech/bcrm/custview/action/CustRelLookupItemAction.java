package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.service.CustRelLookupItemService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.model.LookupMappingItem;

@SuppressWarnings("serial")
@Action("/CustRelLookupItem")
public class CustRelLookupItemAction  extends CommonAction{
    @Autowired
    private CustRelLookupItemService custRelLookupItemService ;
    @Autowired
	public void init(){
	  	model = new LookupMappingItem(); 
		setCommonService(custRelLookupItemService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
    public HttpHeaders queryLookupItems() throws Exception{
        try{	
      	StringBuilder sb=new StringBuilder("select c from LookupMappingItem c where 1=1  and c.lookup ='CUS0100038' ");
      	Map<String,Object> values=new HashMap<String,Object>();
      	ActionContext ctx = ActionContext.getContext();
          request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
          //获取查询条件，查询条件用condition封装
//          this.setJson(request.getParameter("condition"));
          String str = request.getParameter("condition");
          if(str!= null&&!"".equals(str)){
        	  sb.append(" and c.code like '").append(str).append("%'");
          }
          else{
        	  sb.append(" and 1=1");
          }
      	return super.indexByJql(sb.toString(), values);
        }catch(Exception e){
      	  e.printStackTrace();
      	  throw e;
        }
  	}
}