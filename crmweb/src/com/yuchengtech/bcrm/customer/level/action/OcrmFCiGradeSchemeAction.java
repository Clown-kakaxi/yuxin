package com.yuchengtech.bcrm.customer.level.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiGradeLevel;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiGradeScheme;
import com.yuchengtech.bcrm.customer.level.service.OcrmFCiGradeLevelService;
import com.yuchengtech.bcrm.customer.level.service.OcrmFCiGradeSchemeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 评级方案处理类  luyy  2014-07-16
 */

@SuppressWarnings("serial")
@Action("/ocrmFCiGradeSchemeManage")
public class OcrmFCiGradeSchemeAction  extends CommonAction{
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFCiGradeSchemeService ocrmFCiGradeSchemeService ;
    
    @Autowired
    private OcrmFCiGradeLevelService lservice;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @Autowired
	public void init(){
    	model = new OcrmFCiGradeScheme();  
    	setCommonService(ocrmFCiGradeSchemeService);
	}
    
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = " select o.*, to_char(CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') as TIME,to_char(LAST_UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss') as TIME_UPDATE" +
    			" from OCRM_F_CI_GRADE_SCHEME o  where 1=1";
    	
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	
    	for(String key:this.getJson().keySet()){
    	     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    	         if(key.equals("GRADE_USEAGE")||key.equals("GRADE_TYPE")||key.equals("IS_USED")||key.equals("GRADE_FREQUENCY"))
    	             sb.append(" and o."+key+"= '"+this.getJson().get(key)+"'");
    	         else if(key.equals("GRADE_BEGIN_DATE")||"GRADE_END_DATE".equals(key))
    	             sb.append(" and o."+key+"= to_date('"+this.getJson().get(key).toString()+"','YYYY-MM-dd')");
    	         else
    	        	 sb.append(" and o."+key+" like '%"+this.getJson().get(key)+"%'");
    	     }
        }
   		setPrimaryKey("o.SCHEME_ID desc ");

    	SQL=sb.toString();
    	datasource = ds;
	}

    
    
    public DefaultHttpHeaders saveData(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	if(((OcrmFCiGradeScheme)model).getSchemeId() == null){//新增
    		((OcrmFCiGradeScheme)model).setCreateDate(new Timestamp(System.currentTimeMillis()));
    		((OcrmFCiGradeScheme)model).setCreateUserId(auth.getUserId());
    		((OcrmFCiGradeScheme)model).setCreateUserName(auth.getUsername());
    		((OcrmFCiGradeScheme)model).setCreateOrgId(auth.getUnitId());
    		((OcrmFCiGradeScheme)model).setCreateOrgName(auth.getUnitName());
    		((OcrmFCiGradeScheme)model).setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
    		((OcrmFCiGradeScheme)model).setLastUpdateUserId(auth.getUserId());
    		((OcrmFCiGradeScheme)model).setLastUpdateUserName(auth.getUsername());
    		((OcrmFCiGradeScheme)model).setLastUpdateOrgId(auth.getUnitId());
    		((OcrmFCiGradeScheme)model).setLastUpdateOrgName(auth.getUnitName());
    		
    		ocrmFCiGradeSchemeService.save(model);
    	}else{
    		OcrmFCiGradeScheme old = (OcrmFCiGradeScheme)ocrmFCiGradeSchemeService.find(((OcrmFCiGradeScheme)model).getSchemeId());
    		((OcrmFCiGradeScheme)model).setCreateDate(old.getCreateDate());
    		((OcrmFCiGradeScheme)model).setCreateUserId(old.getCreateUserId());
    		((OcrmFCiGradeScheme)model).setCreateUserName(old.getCreateUserName());
    		((OcrmFCiGradeScheme)model).setCreateOrgId(old.getCreateOrgId());
    		((OcrmFCiGradeScheme)model).setCreateOrgName(old.getCreateOrgName());
    		((OcrmFCiGradeScheme)model).setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
    		((OcrmFCiGradeScheme)model).setLastUpdateUserId(auth.getUserId());
    		((OcrmFCiGradeScheme)model).setLastUpdateUserName(auth.getUsername());
    		((OcrmFCiGradeScheme)model).setLastUpdateOrgId(auth.getUnitId());
    		((OcrmFCiGradeScheme)model).setLastUpdateOrgName(auth.getUnitName());
    		
    		ocrmFCiGradeSchemeService.save(model);
    	}
    	long id = ((OcrmFCiGradeScheme)model).getSchemeId();
    	//保存等级信息
    	//1.先删除原等级信息
    	ocrmFCiGradeSchemeService.batchUpdateByName(" delete from OcrmFCiGradeLevel l where l.schemeId="+id+"", new HashMap());
    	//2.保存新的等级信息
    	String levelName = request.getParameter("levelName");
		String levelLower = request.getParameter("levelLower");
		String levelUpper = request.getParameter("levelUpper");
		String cardLevel = request.getParameter("cardLevel");
		
		JSONObject jsonObject1 =JSONObject.fromObject(levelName);
		JSONObject jsonObject2 =JSONObject.fromObject(levelLower);
		JSONObject jsonObject3 =JSONObject.fromObject(levelUpper);
		JSONObject jsonObject4 =JSONObject.fromObject(cardLevel);
		
		JSONArray jarray1 =  jsonObject1.getJSONArray("levelName");
		JSONArray jarray2 =  jsonObject2.getJSONArray("levelLower");
		JSONArray jarray3 =  jsonObject3.getJSONArray("levelUpper");
		JSONArray jarray4 =  jsonObject4.getJSONArray("cardLevel");
		for(int i = 0;i<jarray1.size();i++){
			OcrmFCiGradeLevel level = new OcrmFCiGradeLevel();
			level.setSchemeId(id);
			level.setLevelName(jarray1.getString(i));
			level.setCardLevel(jarray4.getString(i));
			String lower = jarray2.getString(i);
			BigDecimal lowerValue = (lower == null||"".equals(lower))?BigDecimal.ZERO:BigDecimal.valueOf(Double.parseDouble(lower));
			String upper = jarray3.getString(i);
			BigDecimal upperValue = (upper == null||"".equals(upper))?null:BigDecimal.valueOf(Double.parseDouble(upper));
			level.setLevelLower(lowerValue);
			level.setLevelUpper(upperValue);
			lservice.save(level);
		}
    	return new DefaultHttpHeaders("success");
    }
    
    
    public void getLevels() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String usege = request.getParameter("GRADE_USEAGE");
    	String id = request.getParameter("SCHEME_ID");
    	String lookUp = "";
    	StringBuffer sb = new StringBuffer();
    	if("1".equals(usege))//零售等级
    		lookUp = "PRE_CUST_LEVEL";
    	else//反洗钱等级
    		lookUp = "FXQ_RISK_LEVEL";
    	if(id == null || "".equals(id)){//新增时的查询    id为空
    			sb.append(" select i.f_value as level_show, '' as level_id, '' as scheme_id, i.f_code as level_name, " +
    					"'' as level_lower, '' as level_upper,'' as card_level from OCRM_SYS_LOOKUP_ITEM i where i.f_lookup_id = '"+lookUp+"'");
    	}else{
    		sb.append("select i.f_value as level_show,l.level_id,l.scheme_id,l.level_name,l.level_lower,l.level_upper,l.card_level  " +
    				"from OCRM_F_CI_GRADE_LEVEL l left join OCRM_SYS_LOOKUP_ITEM i " +
    				"on i.f_code = l.level_name where i.f_lookup_id='"+lookUp+"' and l.scheme_id='"+id+"'");
    	}
    	
    	this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
    }
    
    
    public String destroyBatch() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		
		String jql="DELETE FROM OcrmFCiGradeScheme C WHERE C.schemeId IN ("+idStr+")";
		Map<String,Object> values=new HashMap<String,Object>();
		ocrmFCiGradeSchemeService.batchUpdateByName(jql, values);
		
		//删除关联等级设置信息
		String jql1="DELETE FROM OcrmFCiGradeLevel C WHERE C.schemeId IN ("+idStr+")";
		Map<String,Object> values1=new HashMap<String,Object>();
		ocrmFCiGradeSchemeService.batchUpdateByName(jql1, values1);
		
		addActionMessage(" lookupMapping removed successfully");
		return "success";
	}
    
    //启用或者禁用方法
    public DefaultHttpHeaders batchUse(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFCiGradeSchemeService.batchUse(request);
    	return new DefaultHttpHeaders("success");
    }
    
	//根据指标编码，查询指标编码及对应的指标名称，将查询结果（Map对象（key：指标编码；value：指标名称））放到对象json中
  	public HttpHeaders getIndexMap() {
  		ActionContext ctx = ActionContext.getContext();
  		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
  		if (this.json != null) {
  			this.json.clear();
  		} else {
  			this.json = new HashMap<String, Object>();
  		}
  		ocrmFCiGradeSchemeService.getIndexMap(request, this.json);
  		return new DefaultHttpHeaders("success").disableCaching();
  	}
}