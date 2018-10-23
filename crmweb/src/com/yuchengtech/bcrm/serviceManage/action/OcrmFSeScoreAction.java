package com.yuchengtech.bcrm.serviceManage.action;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeAdd;
import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeScore;
import com.yuchengtech.bcrm.serviceManage.service.OcrmFSeAddService;
import com.yuchengtech.bcrm.serviceManage.service.OcrmFSeScoreService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户积分管理Action
 * 
 * @author luyy
 * @since 2014-06-09
 */

@SuppressWarnings("serial")
@Action("/ocrmFSeScore")
public class OcrmFSeScoreAction extends CommonAction {

	@Autowired
	private OcrmFSeScoreService service;
	
	@Autowired
	private OcrmFSeAddService addService;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	@Autowired
	public void init() {
		model = new OcrmFSeScore();
		setCommonService(service);
		needLog = true;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String addId = request.getParameter("addId");
		StringBuilder sb = new StringBuilder("");
		if(!"".equals(addId)&&addId != null){
			sb.append(" select a.score_add,a.add_reson as add_reason,a.add_date,s.cust_id,s.cust_name,s.score_total,s.score_used from OCRM_F_SE_SCORE s,OCRM_F_SE_ADD a where a.score_id=s.id and a.id='"+addId+"'");
		}else{
			sb.append("select c.cust_id as cust_id,c.cust_name,s.id,m.institution_name,m.INSTITUTION ,m.mgr_name," +
					"decode(s.SCORE_TOTAL,null,0,'',0,s.SCORE_TOTAL) as SCORE_TOTAL,s.score_used,s.score_todel,s.score_add," +
					"decode(s.add_state,null,'6','','6',s.add_state) as add_state,s.add_reason,s.add_date,s.add_id,s.COUNT_NUM,s.CUST_CUM_COUNT,s.CUST_CUM_COST,s.CUST_COST_SUM " +
					" from ACRM_F_CI_CUSTOMER c , OCRM_F_SE_SCORE s ,Ocrm_f_Ci_Belong_Custmgr m" +
					" where  c.cust_id=s.cust_id and  c.cust_id = m.cust_id  ");
			
			String right = request.getParameter("right");
			if(right != null &&"no".equals(right)){//无限制查询
			}else{
				String searchLevel = "mgr";
				//根据角色限制查询
				List list = auth.getRolesInfo();
				for(Object m:list){
					Map map = (Map)m;
					if("1".equals(map.get("ROLE_LEVEL"))){//总行
						searchLevel = "zh";
						break ;
					}else if("R201".equals(map.get("ROLE_CODE"))||"R301".equals(map.get("ROLE_CODE"))||"R302".equals(map.get("ROLE_CODE"))||"R308".equals(map.get("ROLE_CODE"))||"R303".equals(map.get("ROLE_CODE"))){
						searchLevel = "jg";//区域中心行长、支行行长、柜面主管、Call Center主管、客户经理主管
						continue ;
					}else{
						continue ;
					}
				}
				
				if("mgr".equals(searchLevel)){
					sb.append(" and m.mgr_id='"+auth.getUserId()+"'");
				}
				if("jg".equals(searchLevel)){
					sb.append(" and m.INSTITUTION='"+auth.getUnitId()+"'");
				}
				
			}
			
			for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("ADD_DATE")) {
						sb.append(" AND s.ADD_DATE =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
					} else if (key.equals("MGR_NAME")) {
						sb.append(" AND m." + key + " like '%" + this.getJson().get(key)+ "%'");
					}else if (key.equals("INSTITUTION_NAME")||key.equals("INSTITUTION")) {
						sb.append(" AND m." + key + " like '%" + this.getJson().get(key)+ "%'");
					} else if (key.equals("SCORE_TOTAL")||key.equals("SCORE_USED")||key.equals("SCORE_ADD")) {
						sb.append(" AND s." + key + " = '" + this.getJson().get(key)+ "'");
					} else{ 
						sb.append(" AND s." + key + " like '%" + this.getJson().get(key)+ "%'");
					}
				}
			}
		}

		SQL = sb.toString();
		datasource = ds;
	}
	
	public void save() throws IOException{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	OcrmFSeAdd add ;
    	if(((OcrmFSeScore)model).getAddId() == null){//新增加分
    		add = new OcrmFSeAdd();
        	add.setScoreId(((OcrmFSeScore)model).getId().toString());
        	add.setAddDate(((OcrmFSeScore)model).getAddDate());
        	add.setAddReson(((OcrmFSeScore)model).getAddReason());
        	add.setState("1");
        	add.setScoreAdd(((OcrmFSeScore)model).getScoreAdd());
        	addService.save(add);
        	
        	Map map = new HashMap();
        	map.put("addid", add.getId().toString());
        	map.put("addScore", add.getScoreAdd());
        	map.put("addState", "1");
        	map.put("addreason", add.getAddReson());
        	map.put("addDate", add.getAddDate());
        	
        	service.batchUpdateByName(" update OcrmFSeScore o set o.scoreAdd=:addScore,o.addState=:addState,o.addReason=:addreason," +
        			"o.addDate=:addDate,o.addId=:addid where o.id='"+((OcrmFSeScore)model).getId()+"' ", map);
        	
    	}else{//修改草稿状态的加分信息
    		add =(OcrmFSeAdd) addService.find(Long.valueOf(((OcrmFSeScore)model).getAddId()));
    		add.setScoreAdd(((OcrmFSeScore)model).getScoreAdd());
        	add.setAddReson(((OcrmFSeScore)model).getAddReason());
        	
        	addService.save(add);
        	
        	Map map = new HashMap();
        	map.put("addScore", add.getScoreAdd());
        	map.put("addreason", add.getAddReson());
        	
        	service.batchUpdateByName(" update OcrmFSeScore o set o.scoreAdd=:addScore,o.addReason=:addreason  where o.id='"+((OcrmFSeScore)model).getId()+"' ", map);
    	}

    	String ids = ((OcrmFSeScore)model).getId()+"#"+add.getId();
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(ids);
		response.getWriter().flush();
    	
	}
	
	 /**
	 * 发起工作流
	 * */
	public void initFlow() throws Exception{
	  	ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String requestId =  request.getParameter("instanceid");
		String name =  request.getParameter("name");
		String instanceid = "SC_"+requestId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "积分复核_"+name;//自定义流程名称
		service.initWorkflowByWfidAndInstanceid("13", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceid", instanceid);
	    map.put("currNode", "13_a3");
	    map.put("nextNode",  "13_a4");
	    this.setJson(map);
	}
	
	   /**
	 * 流程提交
	 * */
	public void initFlowJob() throws Exception{
//	  	ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		String instanceid = "SC_"+request.getParameter("instanceid");
//		service.wfCompleteJob(instanceid, "13_a3", "13_a4", null, null);
	}

}
