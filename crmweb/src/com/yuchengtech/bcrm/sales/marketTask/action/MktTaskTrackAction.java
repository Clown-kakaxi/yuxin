package com.yuchengtech.bcrm.sales.marketTask.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.service.MktTaskTrackService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.IAuser;


@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="/MktTaskTrack", results={
    @Result(name="success", type="json"),
})
public class MktTaskTrackAction  extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;  
	IAuser auser = (IAuser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	private HttpServletRequest request;
	@Autowired
	private MktTaskTrackService tService;
    @SuppressWarnings("unchecked")
	public void prepare () {
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	
	        Map<String, Object> valuesMap = new HashMap<String, Object>();
	        StringBuilder sb =new StringBuilder();
	        String taskId=null,targetCode=null;
	        for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("TASK_ID")) {
						taskId = this.getJson().get(key).toString();
					} else if (key.equals("TARGET_CODE")) {
						targetCode = this.getJson().get(key).toString();
					}}}
			   
			   valuesMap.put("taskId", taskId);
				valuesMap.put("targetCode", targetCode);
				
			//查询对应的指标号指标名称
			Map indexInfoMap  = (Map)tService.queryIndexInfo(valuesMap);
			List indexList=(List)indexInfoMap.get("data"); 
			StringBuilder empSql = new StringBuilder(" select M1.TASK_ID,                             "+
													"        M1.TASK_NAME,                           "+
													"        M1.TASK_PARENT_ID,                      "+
													"        M1.OPER_OBJ_ID,                        "+
													"        M1.OPER_OBJ_NAME,                        "+
													"        M1.DIST_TASK_TYPE,                      "+
													"        M1.TASK_STAT,                           "+
													"        M1.TASK_TYPE,                           "+
													"        M1.TASK_BEGIN_DATE,                     "+
													"        M1.TASK_END_DATE,                       ");
			if(indexList != null && indexList.size() > 0){
				for(int i = 0 ; i < indexList.size() ; i++){
					String sqlAp  = "        SUM(CASE                                "+
									"             WHEN M2.TARGET_CODE = '"+((Map)(indexList).get(i)).get("targetCode").toString()+"' THEN "+
									"              M2.ORIGINAL_VALUE                 "+
									"             ELSE                               "+
									"              0                                 "+
									"           END) AS ORIGINAL_VALUE_"+i+",              "+
									"        SUM(CASE                                "+
									"             WHEN M2.TARGET_CODE = '"+((Map)(indexList).get(i)).get("targetCode").toString()+"' THEN "+
									"              M2.TARGET_VALUE                   "+
									"             ELSE                               "+
									"              0                                 "+
									"           END) AS TARGET_VALUE_"+i+",                "+
									"           SUM(CASE                             "+
									"             WHEN M2.TARGET_CODE = '"+((Map)(indexList).get(i)).get("targetCode").toString()+"' THEN "+
									"				CASE WHEN M2.ACHIEVE_VALUE>0	"+
					                "					THEN M2.ACHIEVE_VALUE"+
					                "  						ELSE"+
					                "					GET_TASK_ACHIEVE_VALUE(M1.TASK_ID)	"+
					                "					END		"+
									"             ELSE                               "+
									"              0                                 "+
									"           END) AS ACHIEVE_VALUE_"+i+",                "+
									"           SUM(CASE                             "+
									"             WHEN M2.TARGET_CODE = '"+((Map)(indexList).get(i)).get("targetCode").toString()+"' THEN "+
									" 				CASE WHEN M2.ACHIEVE_VALUE>0"+
						            " 				THEN "
						            + "				(trunc(M2.Achieve_Value/M2.TARGET_VALUE,4))*100"+
						            "   			 ELSE"+
						            " 				(trunc(GET_TASK_ACHIEVE_VALUE(M1.TASK_ID)/M2.TARGET_VALUE,4))*100"+
						            " 			  END  "
						            + "        ELSE"+
									"              0                                 "+
									"           END) AS ACHIEVE_PERCENT_"+i+ ",       ";
					empSql.append(sqlAp);
				}
			}
					empSql.append(	"  M1.TASK_DIST_DATE    from mkt_task_view M1   "+
									"  LEFT JOIN OCRM_F_MM_TASK_TARGET M2           "+
									"     ON M1.TASK_ID = M2.TASK_ID  AND M2.ACHIEVE_VALUE_STATE='2'   where m1.TASKSEQ like '%"+taskId+"%'   "+
									"     GROUP BY M1.TASK_ID,                       "+
									"        M1.TASK_NAME,                           "+
									"        M1.TASK_PARENT_ID,                      "+
									"        M1.OPER_OBJ_ID,                        "+
									"        M1.OPER_OBJ_NAME,                        "+
									"        M1.DIST_TASK_TYPE,                      "+
									"        M1.TASK_STAT,                           "+
									"        M1.TASK_TYPE,                           "+
									"        M1.TASK_BEGIN_DATE,                     "+
									"        M1.TASK_END_DATE,                       "+
									"        M1.TASK_DIST_DATE                       ");
			sb = empSql;
			setPrimaryKey("M1.TASK_ID ");
			SQL = sb.toString();
			datasource = ds;
			addOracleLookup("DIST_TASK_TYPE", "MTASK_OPER_TYPE");//执行对象类型
			addOracleLookup("TASK_STAT", "MTASK_STAT");//营销任务状态
			addOracleLookup("TASK_TYPE", "MTASK_TYPE");//营销任务类型
		}catch(Exception e){
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	   }
	}
    
    /**
     * @author sujm
     * @return String
     * 对表头做去重复处理，返回单一的列标题
     * */
    public String getDistinct(String num) {
    	String numArr[] = num.split(",");
    	String tt ="";
    	StringBuffer aimStr = new StringBuffer();
		for (int i = 0; i < numArr.length; i++) {
			if(!numArr[i].equals(tt)){
				tt = numArr[i];
				aimStr.append(",");
				aimStr.append(numArr[i]);
			}
		}
		return aimStr.toString();
    }
    
    /**
     * @author sujm
     * @return json
     * 根据前台传入的营销任务和关联指标信息，动态的查询任务相关信息，
     * 并最终生成指标的营销任务名称,执行对象类型,执行对象,营销任务状态,营销任务类型,初始值,目标值,达成值,增长率等信息
     * 由json提交给前台界面
     * */
    @SuppressWarnings("unchecked")
	public void getColumn() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		   Map<String, Object> valuesMap = new HashMap<String, Object>();
		   String taskId = request.getParameter("taskId");// 营销任务ID
		   String targetCode = request.getParameter("targetCode");// 指标ID
		   valuesMap.put("taskId", taskId);
			valuesMap.put("targetCode", targetCode);
			
			Map indexInfoMap  = (Map)tService.queryIndexInfo(valuesMap);
			List indexList=(List)indexInfoMap.get("data"); 
			String targetType = null;
			StringBuilder sbIndexName = new StringBuilder();
			StringBuilder targetName = new StringBuilder();
			StringBuilder sbIndexId = new StringBuilder();
			StringBuilder cycleName = new StringBuilder();
			sbIndexName.append("营销任务名称,执行对象类型,执行对象,营销任务状态,营销任务类型");
			sbIndexId.append("TASK_NAME,DIST_TASK_TYPE,OPER_OBJ_NAME,TASK_STAT,TASK_TYPE");
			if(indexList != null && indexList.size() > 0){
				targetType = ((Map)(indexList).get(0)).get("targetType").toString();
				for(int i = 0 ; i < indexList.size() ; i++){
//					sbIndexName.append(',');
//					sbIndexName.append("初始值");
					
//					sbIndexId.append(',');
//					sbIndexId.append("ORIGINAL_VALUE_"+i);
					
					sbIndexName.append(',');
					sbIndexName.append("目标值");
					
					sbIndexId.append(',');
					sbIndexId.append("TARGET_VALUE_"+i);
					
					sbIndexName.append(',');
					sbIndexName.append("达成值");
					
					sbIndexId.append(',');
					sbIndexId.append("ACHIEVE_VALUE_"+i);
					
					sbIndexName.append(',');
					sbIndexName.append("达成率(%)");
					
					sbIndexId.append(',');
					sbIndexId.append("ACHIEVE_PERCENT_"+i);
					
					targetName.append(',');
					targetName.append(((Map)(indexList).get(i)).get("targetName"));
					
					cycleName.append(',');
					cycleName.append(((Map)(indexList).get(i)).get("cycleName"));
				}
			}
			
		sqlMap.put("sbIndexName", sbIndexName.toString());
		sqlMap.put("sbIndexId", sbIndexId.toString());
		sqlMap.put("targetName", targetName.toString());
		sqlMap.put("targetType", targetType.toString());
		sqlMap.put("cycleName", getDistinct(cycleName.toString()));
		json = sqlMap;
    }
    
}
