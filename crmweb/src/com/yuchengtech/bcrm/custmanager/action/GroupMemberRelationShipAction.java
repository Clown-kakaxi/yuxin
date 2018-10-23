package com.yuchengtech.bcrm.custmanager.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.service.GroupMemberRelationShipService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.service.CommonQueryService;
@ParentPackage("json-default")
@Action(value="/groupMemberRelationShip", results={
	    @Result(name="success", type="json"),
	})
/**
 * 
* @ClassName: GroupMemberRelationShipAction 
* @Description: 集团组织架构图基本信息查询。
* @author wangmk1 
* @date 2014-8-14 上午9:59:22 
*
 */
public class GroupMemberRelationShipAction extends CommonAction {
	@Autowired
	private CommonQueryService cqs;
	@Autowired
	private GroupMemberRelationShipService gco;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	@Override
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String groupId=request.getParameter("groupId");
//		String condition=request.getParameter("condition");
		StringBuffer sb=new StringBuffer("SELECT t.ID,t.GROUP_NO,t.CUST_NAME,t.CUST_ID FROM OCRM_F_CI_GROUP_MEMBER t " +
				" left join OCRM_F_CI_GROUP_INFO t1 ON  t.GROUP_NO=t1.GROUP_NO " +
				" where t1.id " +
				" ='"+groupId+"' ");
		 String s="";
	   for(String key:this.getJson().keySet()){
		   if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
			   if(key.equals("CUST_ID")||key.equals("CUST_NAME"))
	   	             sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
		   }
	   }
		setPrimaryKey("ID desc ");
		SQL=sb.toString();
		datasource=ds;
	}	
	public void serchGroup(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String groupId=request.getParameter("groupId");
		StringBuffer sb=new StringBuffer("SELECT t.ID,t.GROUP_NO,t.GROUP_NAME FROM OCRM_F_CI_GROUP_INFO t WHERE 1=1 and t.id ='"+groupId+"' ");
		
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
		//对应的root为data
		try {
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  public String update(){
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			String id=request.getParameter("id");
			String infos=request.getParameter("infos");
			gco.updateGraph(id, infos);
	    	return "success";
	    }
	  public HttpHeaders show(){
		  ActionContext ctx = ActionContext.getContext();
		  HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		  String groupId=request.getParameter("groupId");
		  this.json=gco.show(groupId);
		  return new DefaultHttpHeaders("show");
	  }
}
