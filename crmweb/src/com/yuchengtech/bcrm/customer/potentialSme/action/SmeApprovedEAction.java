package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovedC;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovedDb;



import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeApprovedDbService;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeApprovedEService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 中小企客户营销流程 -  已批核动拨  
 * @author denghj
 * @since 2015-08-07
 */
@SuppressWarnings("serial")
@Action("/smeApprovedE")
public class SmeApprovedEAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiSmeApprovedEService service;
    
    @Autowired
    private OcrmFCiSmeApprovedDbService dbService;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktApprovedC();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = " select c.ID,c.CALL_ID,c.HP_ID,c.CUST_ID,c.CUST_NAME,c.GROUP_NAME,c.AREA_ID,c.AREA_NAME,c.DEPT_ID," +
				"c.DEPT_NAME,c.RM,c.APPLY_AMT,c.COMP_TYPE,c.GRADE_LEVEL,c.CASE_TYPE,c.IF_ADD,c.ADD_AMT,c.XD_HZ_DATE," +
				"c.IF_ACCEPT,c.USE_DATE_P,c.NOACCEPT_REASON,c.CTR_C_DATE,c.CTR_S_DATE,c.MORTGAGE_DATE,c.FILE_UP_DATE," +
				"c.SX_CTR_DATE,c.CTR_PROBLEM,c.PROBLEM_DATE,c.AMT_USE_DATE,c.ACCOUNT_DATE,c.PAY_DATE,c.USER_ID," +
				"c.RECORD_DATE,c.UPDATE_DATE,c.CHECK_STAT,c.RM_ID,c.PIPELINE_ID,c.UNRECEPT_REASON," +
				"c.REASON_REMARK1,c.UNISSUE_REASON,c.REASON_REMARK2,c.INSURE_AMT,c.INSURE_MONEY,c.XD_CHECK_DATE,c.CUST_TYPE,c.CURRENCY," +
				"DECODE(c.INSURE_CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD','12','TWD','13','USD',c.INSURE_CURRENCY) as INSURE_CURRENCY," +
    			"a.user_name,r.db_amts, '"+auth.getBelongBusiLine()+"' as line  from OCRM_F_CI_MKT_APPROVED_C c "
    			+ " left join  (select t.pipeline_id as pipeline_id_b,sum (t.db_amt) as db_amts  from OCRM_F_CI_MKT_APPROVED_DB   t group by t.pipeline_id) r on c.pipeline_id=r.pipeline_id_b " +
    			" left join admin_auth_account a on c.user_id = a.account_name  where 1=1  and (a.belong_busi_line = '5' or a.belong_busi_line ='0') ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    	}else{
    		sb.append(" and c.LAST_SEND_STEP is null ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.RECORD_DATE asc ");
        	configCondition("CUST_ID","=","CUST_ID",DataType.String);
        	configCondition("PIPELINE_ID","=","PIPELINE_ID",DataType.String);
    		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("AREA_NAME","like","AREA_NAME",DataType.String);
    		configCondition("DEPT_NAME","=","DEPT_NAME",DataType.String);
    		configCondition("RM","like","RM",DataType.String);
    		configCondition("RM_ID","=","RM_ID",DataType.String);
    		configCondition("IF_ACCEPT","=","IF_ACCEPT",DataType.String);
    		configCondition("CASE_TYPE","=","CASE_TYPE",DataType.String);
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	OcrmFCiMktApprovedC approvedC =(OcrmFCiMktApprovedC)model;
    	approvedC.setUserId(auth.getUserId());
    	approvedC.setCheckStat("2");
    	if(approvedC.getId()==null){//新增
    		approvedC.setRecordDate(new Date());//设置记录日期
    		if((approvedC.getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			approvedC.setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(approvedC);
    	}else{//修改
    		//根据字段IfFifthStep判断是否转入IfFifthStep
    		approvedC.setUpdateDate(new Date());
    		service.save(approvedC);
    	
    	}
    	return new DefaultHttpHeaders("success");
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_APPROVED_C WHERE ID='"+Long.parseLong(id)+"'", null);
            pList.add(list);
    	}
    	if(pList!=null && pList.size()>0){
    		for(List aList:pList){
    			for(int i=0;i<aList.size();i++){
    				String pId=aList.get(i)+"";
    				service.batchUpdateByName("UPDATE OcrmFCiMktProspectC  SET ifPipeline='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktIntentC SET ifSecondStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktCaC   SET ifThirdStep='99'  WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktCheckC  SET ifFourthStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktApprovlC  SET ifFifthStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktApprovedC  SET lastSendStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    			}
    			
    		}
    	}
    }
    
    
    public String queryDB() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String pipelineId = request.getParameter("pipelineId");
        //查询统一客户表信息-个人客户表信息
		StringBuffer sb = new StringBuffer("select t.id,t.pipeline_id,t.db_amt,t.db_date, t1.user_name as DB_USER from OCRM_F_CI_MKT_APPROVED_DB t "
				+ " left join admin_auth_account t1 on t.DB_USER=t1.account_name "
				+ " where t.pipeline_id='"+pipelineId+"'");
		QueryHelper query;
		query = new QueryHelper(sb.toString(), ds.getConnection());
		Map<String, Object> result = query.getJSON();
		if(this.json != null)
			this.json.clear();
		else
			this.json = new HashMap<String,Object>(); 
		this.json.put("json",result);
    	return "success";
    }
    
    public void addDB() throws ParseException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String DB_DATE=request.getParameter("DB_DATE");
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = format.parse(DB_DATE); 
        OcrmFCiMktApprovedDb ocrmFCiMktApprovedDb =new OcrmFCiMktApprovedDb();
        ocrmFCiMktApprovedDb.setPipelineId(Long.parseLong(request.getParameter("PIPELINE_ID")));
        ocrmFCiMktApprovedDb.setDbAmt(new BigDecimal(request.getParameter("DB_AMT")));
        ocrmFCiMktApprovedDb.setDbDate(date);
        ocrmFCiMktApprovedDb.setDbUser(auth.getUserId());
    	setCommonService(dbService);
    	dbService.save(ocrmFCiMktApprovedDb);
    	   	
    }
    
    public void updateDB() throws ParseException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String DB_DATE=request.getParameter("DB_DATE");
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = format.parse(DB_DATE); 
        OcrmFCiMktApprovedDb ocrmFCiMktApprovedDb =new OcrmFCiMktApprovedDb();
        ocrmFCiMktApprovedDb.setId(Long.parseLong(request.getParameter("ID")));
        ocrmFCiMktApprovedDb.setPipelineId(Long.parseLong(request.getParameter("PIPELINE_ID")));
        ocrmFCiMktApprovedDb.setDbAmt(new BigDecimal(request.getParameter("DB_AMT")));
        ocrmFCiMktApprovedDb.setDbDate(date);
        ocrmFCiMktApprovedDb.setDbUser(auth.getUserId());
    	setCommonService(dbService);
    	dbService.save(ocrmFCiMktApprovedDb);
    	
    	
    }
    
    public void batchDelDB() throws ParseException{
    	ActionContext ctx = ActionContext.getContext();                                            
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr=request.getParameter("idStr");
    	setCommonService(dbService);
    	dbService.batchRemove(idStr);  	
    }

}
