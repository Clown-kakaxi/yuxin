package com.yuchengtech.bcrm.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiGrade;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktIntentC;
import com.yuchengtech.bcrm.service.AcrmFCiGradeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 客户等级信息
 * @author likai
 * @since 2014-08-25
 *
 */
@Action("/acrmFCiGrade")
public class AcrmFCiGradeAction extends CommonAction {
	
	@Autowired
	private AcrmFCiGradeService service;
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性
	
	@Autowired
	public void init() {
		model = new AcrmFCiGrade();
		setCommonService(service);
		needLog=true;
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("CUST_ID_P");
		StringBuilder sb = new StringBuilder("select c.CUST_ID,c.CUST_NAME,'反洗钱风险等级' as CUST_GRADE_TYPE,t.f_value as  CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " left join ocrm_sys_lookup_item t on t.f_code = g.cust_grade "+
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type = '01' and t.f_lookup_id = 'FXQ_RISK_LEVEL' " +
											 " 	union all " +
											 "select c.CUST_ID,c.CUST_NAME,'信用等级' as CUST_GRADE_TYPE,t.f_value as CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " left join ocrm_sys_lookup_item t on t.f_code = g.cust_grade "+
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type in ('03','04') and t.f_lookup_id = 'XD000082' " +
											 " union all " +
									 		 "select c.CUST_ID,c.CUST_NAME,'五级分类' as CUST_GRADE_TYPE,t.f_value as CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " left join ocrm_sys_lookup_item t on t.f_code = g.cust_grade "+
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type ='02' and t.f_lookup_id = 'DM0009' " +
											 " union all " +
									 		 "select c.CUST_ID,c.CUST_NAME,'其他' as CUST_GRADE_TYPE,'其他' as CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type ='99'" +
											 " union all "+
											 "select c.CUST_ID,c.CUST_NAME,'商金企业类型' as CUST_GRADE_TYPE,g.CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type ='06' " +
											 " union all "+
											 "select c.CUST_ID,c.CUST_NAME,'商金企业评级' as CUST_GRADE_TYPE,g.CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type ='07' " +
											 " union all "+
											 "select c.CUST_ID,c.CUST_NAME,'法金信用等级' as CUST_GRADE_TYPE,g.CUST_GRADE,g.EVALUATE_DATE,g.LAST_UPDATE_USER "+
											 " from ACRM_F_CI_GRADE g "+
											 " left join  ACRM_F_CI_CUSTOMER c  on c.CUST_ID = g.CUST_ID " +
											 " where c.CUST_ID = '"+custId+"' and g.cust_grade_type ='10' ");
		SQL=sb.toString();
		datasource = ds;
	}
	
	
	public void  searchGrade(){
		try {
			ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
			String custId=request.getParameter("custId");
			StringBuilder sb = new StringBuilder("");
				sb.append("select t.cust_grade_id,t.cust_id  from ACRM_F_CI_GRADE t  where t.cust_id='"+custId+"' and t.CUST_GRADE_TYPE='01' ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save() throws Exception{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
    	String custId = request.getParameter("custId");
    	String custName = request.getParameter("custName");
    	String riskLevel = request.getParameter("riskLevel");
    	String lastUpdateUser = request.getParameter("lastUpdateUser");
    	String CUST_GRADE_ID=request.getParameter("");
//    	//update等级信息表对应记录
//    	service.batchUpdateByName(" update AcrmFCiGrade set custGradeId='"+custId+2014091701+"' ", new HashMap());
    	//删除客户原有等级信息
    	//service.batchUpdateByName(" delete from AcrmFCiGrade g where g.custId='"+custId+"' and g.custGradeType='01'", new HashMap());
    	//添加新的等级信息
    	Date now = new Date();
    	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
    	String CustGradeId =((AcrmFCiGrade) model).getCustGradeId();
    	if("".equals(CustGradeId) || CustGradeId==null){
    		((AcrmFCiGrade) model).setCustGradeId(custId+dateFormat.format(now)+"01");
    	}
    	((AcrmFCiGrade) model).setCustId(custId);
    	((AcrmFCiGrade) model).setOrgCode(auth.getUnitId());
    	((AcrmFCiGrade) model).setOrgName(auth.getUnitName());
    	((AcrmFCiGrade) model).setCustGradeType("01");
    	((AcrmFCiGrade) model).setCustGrade(riskLevel);
    	((AcrmFCiGrade) model).setEvaluateDate(now);
    	((AcrmFCiGrade) model).setEffectiveDate(now);
    	((AcrmFCiGrade) model).setLastUpdateSys("CRM");
    	((AcrmFCiGrade) model).setLastUpdateUser(lastUpdateUser);
    	((AcrmFCiGrade) model).setLastUpdateTm(new Timestamp(System.currentTimeMillis()));
    	
    	service.save(model);
	}
}
