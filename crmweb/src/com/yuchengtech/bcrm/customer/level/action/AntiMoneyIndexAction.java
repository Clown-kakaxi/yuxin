package com.yuchengtech.bcrm.customer.level.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiIndexApply;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiIndexInfo;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiMoneyIndex;
import com.yuchengtech.bcrm.customer.level.service.OcrmFCiAntiIndexApplyService;
import com.yuchengtech.bcrm.customer.level.service.OcrmFCiAntiIndexInfoService;
import com.yuchengtech.bcrm.customer.level.service.OcrmFCiAntiMoneyIndexService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 反洗钱指标处理
 * @author luyy
 *@since 2014-07-14
 */

@SuppressWarnings("serial")
@Action("/antiMoney")
public class AntiMoneyIndexAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFCiAntiIndexInfoService service;
	
	@Autowired
	private OcrmFCiAntiIndexApplyService aservice;
	
	@Autowired
	private OcrmFCiAntiMoneyIndexService iservice;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
//		model = new OcrmFCiAntiIndexInfo();
		setCommonService(service);
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	StringBuffer sb = new StringBuffer("select p.*,a.user_name as LAST_UPDATE_NAME,a1.user_name as LAST_VERIFIER_NAME " +
    			"from  OCRM_F_CI_ANTI_MONEY_INDEX p left join admin_auth_account a on p.LAST_UPDATE_USER=a.account_name " +
    			"left join admin_auth_account a1 on a1.account_name = p.LAST_VERIFIER  where 1=1 ");
    	//处理页面查询条件
    	 for(String key : this.getJson().keySet()){
     		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
 				if(null!=key&&key.equals("LAST_UPDATE_NAME")){
 					sb.append("  and a."+key+" like '%"+this.getJson().get(key)+"%'  ");
 				}else if(null!=key&&key.equals("LAST_VERIFIER_NAME")){
 					sb.append("  and a1."+key+" like '%"+this.getJson().get(key)+"%'  ");
 				}else if(null!=key&&(key.equals("LAST_UPDATE_TM")||key.equals("LAST_VERIFY_TM"))){
 					sb.append("  and p."+key+" =to_date('"+this.getJson().get(key).toString().substring(0,10)+"'  ,'yyyy-mm-dd')");
 				}else if(null!=key){
 					sb.append("  and  p."+key+" like '%"+this.getJson().get(key)+"%' ");
                }
     		}
 		}
    	 
    	this.setPrimaryKey(" index_code ");
    	SQL = sb.toString();
    	datasource = ds;
    }
	//拼接查询出全部子项
	public void all() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String indexId = request.getParameter("indexId");
    	String indexDic = request.getParameter("indexDic");
    	StringBuffer sb = new StringBuffer();
    	if(indexDic == null || "".equals(indexDic)){//如果字典为空   认为是“开户行”指标
    		sb.append("select (select 1 from ACRM_A_ANTI_HIGH_INDEX i  where i.index_id = v.index_value and i.index_code = v.index_code) as is_flag," +
    				" i.org_name as index_value_name,v.id,v.index_score,v.index_right,i.org_id as index_value,v.high_flag " +
    				" from admin_auth_org i " +
    				" left join OCRM_F_CI_ANTI_MONEY_INDEX_VAR v on i.org_id = v.index_value and v.index_id = '"+indexId+"'");
    		sb.append(" order by i.org_id ");
    	}else{
    		sb.append("select (select 1 from ACRM_A_ANTI_HIGH_INDEX i  where i.index_id = v.index_value and i.index_code = v.index_code) as is_flag, " +
    				" i.f_value as index_value_name,v.id,v.index_score,v.index_right,i.f_code as index_value,v.high_flag " +
    				" from OCRM_SYS_LOOKUP_ITEM i " +
    				" left join OCRM_F_CI_ANTI_MONEY_INDEX_VAR v on i.f_code = v.index_value and v.index_id = '"+indexId+"' " +
    				" where i.f_lookup_id='"+indexDic+"'");
    		sb.append(" order by i.f_code ");
    	}
    	this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
	}
	
	//拼接查询出当前设置的子项
	public void old() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String applyId = request.getParameter("applyId");
    	StringBuffer sb = new StringBuffer();
    	String indexId = "";
    	String indexDic = "";
    	if(applyId != null && !"".equals(applyId)){//流程查询
    		OcrmFCiAntiIndexApply apply = (OcrmFCiAntiIndexApply)aservice.find(Long.parseLong(applyId));
    		indexId = apply.getIndexId();
    		OcrmFCiAntiMoneyIndex index = (OcrmFCiAntiMoneyIndex)iservice.find(Long.parseLong(indexId));
    		indexDic = index.getIndexDic();
    	}else{
    		indexId = request.getParameter("indexId");
        	indexDic = request.getParameter("indexDic");
    	}
    	if(indexDic == null || "".equals(indexDic)){//如果字典为空   认为是“开户行”指标
    		sb.append("select i.org_name as index_value_name,v.id,v.index_score,v.index_right,i.org_id as index_value,v.high_flag " +
    				"from admin_auth_org i ,OCRM_F_CI_ANTI_MONEY_INDEX_VAR v where i.org_id = v.index_value and v.index_id = '"+indexId+"'");
    		sb.append(" order by i.org_id ");
    	}else{
    		sb.append("select i.f_value as index_value_name,v.id,v.index_score,v.index_right,i.f_code as index_value,v.high_flag " +
    				"from OCRM_SYS_LOOKUP_ITEM i " +
    				",OCRM_F_CI_ANTI_MONEY_INDEX_VAR v where i.f_code = v.index_value and v.index_id = '"+indexId+"' " +
    				"and i.f_lookup_id='"+indexDic+"'");
    		sb.append(" order by i.f_code ");
    	}
    	this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
	}
	
	//拼接查询出当前申请中的子项
	public void apply() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String applyId = request.getParameter("applyId");
    	StringBuffer sb = new StringBuffer();
    	String indexId = "";
    	String indexDic = "";
    	if(applyId != null && !"".equals(applyId)){//流程查询
    		OcrmFCiAntiIndexApply apply = (OcrmFCiAntiIndexApply)aservice.find(Long.parseLong(applyId));
    		indexId = apply.getIndexId();
    		OcrmFCiAntiMoneyIndex index = (OcrmFCiAntiMoneyIndex)iservice.find(Long.parseLong(indexId));
    		indexDic = index.getIndexDic();
    	}else{
    		indexId = request.getParameter("indexId");
        	indexDic = request.getParameter("indexDic");
    	}
    	if(indexDic == null || "".equals(indexDic)){//如果字典为空   认为是“开户行”指标
    		sb.append("select i.org_name as index_value_name,v.id,v.index_score,v.index_right,i.org_id as index_value,v.high_flag " +
    				"from admin_auth_org i ,OCRM_F_CI_ANTI_INDEX_INFO v where i.org_id = v.index_value " +
    				"and v.APPLY_ID IN (select ID from OCRM_F_CI_ANTI_INDEX_APPLY where INDEX_ID='"+indexId+"' and APPLY_STATE='1' ) ");
    	}else{
    		sb.append("select i.f_value as index_value_name,v.id,v.index_score,v.index_right,i.f_code as index_value,v.high_flag " +
    				"from OCRM_SYS_LOOKUP_ITEM i " +
    				",OCRM_F_CI_ANTI_INDEX_INFO v where i.f_code = v.index_value " +
    				"and v.APPLY_ID IN (select ID from OCRM_F_CI_ANTI_INDEX_APPLY where INDEX_ID='"+indexId+"' and APPLY_STATE='1' ) " +
    				"and i.f_lookup_id='"+indexDic+"'");
    	}
    	this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		
	}
	
	public void save() throws Exception{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String indexId = request.getParameter("indexId");
    	
    	//获取原指标信息
    	OcrmFCiAntiMoneyIndex index = (OcrmFCiAntiMoneyIndex)iservice.find(Long.parseLong(indexId));
//    	index.setVeriflerStat("1");
//    	index.setLastUpdateTm(new Date());
//    	index.setLastUpdateUser(auth.getUserId());
//    	index.setLastVerifier("");
//    	index.setLastVerifyTm(null);
//    	iservice.save(index);
    	iservice.batchUpdateByName(" update OcrmFCiAntiMoneyIndex p set p.veriflerStat='1'," +
    			" p.lastVerifier = null, " +
    			" p.lastVerifyTm = null, " +
    			" p.lastUpdateUser = '"+auth.getUserId()+"'," +
    		    " p.lastUpdateTm = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' where p.indexId='" + Long.parseLong(indexId) + "'",null);
    	
    	String indexCode = index.getIndexCode();
    	String indexName = index.getIndexName();//INDEX_TYPE
    	
    	//获取指标类型
        String indexValue = iservice.getIndexDic(index.getIndexType());
    	
    	//生成申请信息
    	OcrmFCiAntiIndexApply apply = new OcrmFCiAntiIndexApply();
    	apply.setIndexId(indexId);
    	apply.setUserId(auth.getUsername());
    	apply.setApplyDate(new Date());
    	apply.setApplyState("1");
    	
    	apply = (OcrmFCiAntiIndexApply)aservice.save(apply);
    	
//    	生成申请子项信息
    	Long id = apply.getId();
    	String INDEX_VALUEs = request.getParameter("INDEX_VALUEs");
		String INDEX_SCOREs = request.getParameter("INDEX_SCOREs");
		String INDEX_RIGHTs = request.getParameter("INDEX_RIGHTs");
		
		String HIGH_FLAGs = request.getParameter("HIGH_FLAGs");
		
		JSONObject jsonObject1 =JSONObject.fromObject(INDEX_VALUEs);
		JSONObject jsonObject2 =JSONObject.fromObject(INDEX_SCOREs);
		JSONObject jsonObject3 =JSONObject.fromObject(INDEX_RIGHTs);
		
		JSONObject jsonObject7 =JSONObject.fromObject(HIGH_FLAGs);
		
		JSONArray jarray1 =  jsonObject1.getJSONArray("INDEX_VALUE");
		JSONArray jarray2 =  jsonObject2.getJSONArray("INDEX_SCORE");
		JSONArray jarray3 =  jsonObject3.getJSONArray("INDEX_RIGHT");
		
		JSONArray jarray7 =  jsonObject7.getJSONArray("HIGH_FLAG");
		
		for(int i = 0;i<jarray1.size();i++){
			OcrmFCiAntiIndexInfo info = new OcrmFCiAntiIndexInfo();
			info.setApplyId(id.toString());
			info.setIndexCode(indexCode);
			info.setIndexName(indexName);
			info.setIndexValue(jarray1.getString(i));
			info.setIndexScore(BigDecimal.valueOf(jarray2.getDouble(i)));
			info.setIndexRight(BigDecimal.valueOf(jarray3.getDouble(i)));
			
			info.setHighFlag(jarray7.getString(i));

			service.save(info);
			//记录手工设置高风险选项
//			service.saveHighIndex(info,jarray7.getString(i));
		}
		 //提交流程
		String instanceid = "ZB_"+id;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "反洗钱指标审核_"+indexName+"_"+indexValue;//自定义流程名称
		
		service.initWorkflowByWfidAndInstanceid("31", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("instanceid", instanceid);
	    map1.put("currNode", "31_a3");
	    map1.put("nextNode",  "31_a4");
	    this.setJson(map1);
	}
	
	public void updateStat() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		String type = request.getParameter("type");
		String ids[] = idStr.split(",");
		for (String id : ids) {
			service.batchUpdateByName(" update OcrmFCiAntiMoneyIndex p set p.indexState='" + type+ "' where p.indexId='" + Long.parseLong(id) + "'",new HashMap());
		}
	}
}
