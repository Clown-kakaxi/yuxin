package com.yuchengtech.bcrm.serviceManage.action;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeGoods;
import com.yuchengtech.bcrm.serviceManage.service.OcrmFSeGoodsService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 礼品管理Action
 * 
 * @author luyy
 * @since 2014-06-10
 */

@SuppressWarnings("serial")
@Action("/ocrmFSeGoods")
public class OcrmFSeGoodsAction extends CommonAction {

	@Autowired
	private OcrmFSeGoodsService service;
	

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	@Autowired
	public void init() {
		model = new OcrmFSeGoods();
		setCommonService(service);
		needLog = true;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		StringBuilder sb = new StringBuilder("");
		if(!"".equals(id)&&id != null){
			sb.append(" select s.*,a.user_name as create_name from OCRM_F_SE_GOODS s ,admin_auth_account a where s.create_id = a.account_name and s.id='"+id+"'");
		}else{
			sb.append("select s.*,a.user_name as create_name from OCRM_F_SE_GOODS s ,admin_auth_account a where s.create_id = a.account_name   ");
			String searchLevel = "jg";
			//根据角色限制查询
			List list = auth.getRolesInfo();
			for(Object m:list){
				Map map = (Map)m;
				if("1".equals(map.get("ROLE_LEVEL"))){//总行
					searchLevel = "zh";
					break ;
				}else{
					continue ;
				}
			}
			if("jg".equals(searchLevel)){
				sb.append(" and s.ORG_ID='"+auth.getUnitId()+"' ");
			}
			
			for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("CREATE_DATE")) {
						sb.append(" AND s.CREATE_DATE =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
					} else if (key.equals("CREATE_NAME")) {
						sb.append(" AND a.user_name like '%" + this.getJson().get(key)+ "%'");
					} else if (key.equals("ORG_ID")) {
						sb.append(" AND s.ORG_ID ='" + this.getJson().get(key)+ "'");
					}else if(key.equals("CUST_LEVEL")){
						String levels[] = this.getJson().get(key).toString().split(",");
						if(levels.length>0){
							for(int i=0;i<levels.length;i++){
								if(i == 0){
									sb.append(" AND (s.CUST_LEVEL  like '%" + levels[0]+ "%'");
								}else{
									sb.append(" or s.CUST_LEVEL   like '%" + levels[i]+ "%'");
								}
							}
							
						}
						sb.append(" )");
					}
					else{ 
						sb.append(" AND s." + key + " like '%" + this.getJson().get(key)+ "%'");
					}
				}
			}
		}

		SQL = sb.toString();
		datasource = ds;
	}
	
	
	 /**
	 * 发起工作流
	 * */
	public void initFlow() throws Exception{
	  	ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String requestId =  request.getParameter("instanceid");
		String name =  request.getParameter("name");
		String instanceid = "LP_"+requestId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		  String jobName = "礼品复核_"+name;//自定义流程名称
		  service.initWorkflowByWfidAndInstanceid("14", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
	
		    String nextNode = "14_a4";
			List list = auth.getRolesInfo();
			for(Object m:list){
				Map map = (Map)m;
				if("R105".equals(map.get("ROLE_CODE"))||"R104".equals(map.get("ROLE_CODE"))||"R106".equals(map.get("ROLE_CODE"))||"R107".equals(map.get("ROLE_CODE"))){//总行
					nextNode = "14_a7";
					break ;
				}else if("R201".equals(map.get("ROLE_CODE"))){//区域行长
					nextNode = "14_a6";
					continue ;
				}else if("R301".equals(map.get("ROLE_CODE"))){//支行行长
					nextNode = "14_a5";
					continue ;
				}else if("R303".equals(map.get("ROLE_CODE"))){//经理主管
					nextNode = "14_a4";
					continue ;
				}else{
					continue ;
				}
			}	
		  Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("instanceid", instanceid);
		    map1.put("currNode", "14_a3");
		    map1.put("nextNode",  nextNode);
		    this.setJson(map1);
	}
	
	   /**
	 * 流程提交
	 * */
	public void initFlowJob() throws Exception{
//	  	ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		String instanceid = "LP_"+request.getParameter("instanceid");
//		//流程参数，判断节点走向
//		Map<String, String> mapParma = new HashMap<String, String>();//参数map
//		List list = auth.getRolesInfo();
//		for(Object m:list){
//			Map map = (Map)m;
//			if("R105".equals(map.get("ROLE_CODE"))||"R104".equals(map.get("ROLE_CODE"))||"R106".equals(map.get("ROLE_CODE"))||"R107".equals(map.get("ROLE_CODE"))){//总行
//				mapParma.put("roleLevel", "zhzy");
//				break ;
//			}else if("R201".equals(map.get("ROLE_CODE"))){//区域行长
//				mapParma.put("roleLevel", "qyhz");
//				break ;
//			}else if("R301".equals(map.get("ROLE_CODE"))){//支行行长
//				mapParma.put("roleLevel", "zhhz");
//				break ;
//			}else if("R303".equals(map.get("ROLE_CODE"))){//经理主管
//				mapParma.put("roleLevel", "jlzg");
//				break ;
//			}else{
//				continue ;
//			}
//		}
//		  
//		service.wfCompleteJob(instanceid, "14_a3", "", null, mapParma);
	}

	 //查询机构字典
    public String searchOrg()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select org_name as value,org_id as key from admin_auth_org ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
    
  //查询活动字典
    public String searchActi()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String type = request.getParameter("type");
		try {
			StringBuilder sb = new StringBuilder("");
			if("search".equals(type))
				sb.append("select MKT_ACTI_NAME as value,MKT_ACTI_ID as key from OCRM_F_MK_MKT_ACTIVITY where MKT_ACTI_STAT in ('3','4','5') ");
			if("new".equals(type))
				sb.append("select MKT_ACTI_NAME as value,MKT_ACTI_ID as key from OCRM_F_MK_MKT_ACTIVITY where MKT_ACTI_STAT in ('3') ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id : ids){
    		service.batchUpdateByName(" delete from OcrmFSeGoods g where g.id='"+Long.parseLong(id)+"'", new HashMap());
    	}
    }
}
