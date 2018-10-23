package com.yuchengtech.bcrm.callReport.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.callReport.model.OcrmFCiCallreportInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class CallReportQueryService extends CommonService{

	public CallReportQueryService(){
		JPABaseDAO<OcrmFCiCallreportInfo,String> baseDao = new JPABaseDAO<OcrmFCiCallreportInfo,String>(OcrmFCiCallreportInfo.class);
		super.setBaseDAO(baseDao);
	}
	
	public void saveRemind(OcrmFCiCallreportInfo model,Map parameterMap) throws ParseException{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = (String) parameterMap.get("id");
		String custId = (String)parameterMap.get("custId");
		String custName = (String)parameterMap.get("custName");
		String remindDt = (String)parameterMap.get("remindDt");
		String callreport = (String)parameterMap.get("callreport");
		String remindType = (String)parameterMap.get("remindType");
//		String createTime = (String)parameterMap.get("createTime");
//		String createUsername = (String)parameterMap.get("createUsername");
//		String createUser = (String)parameterMap.get("createUser");
//		String createOrg = (String)parameterMap.get("createOrg");
		// String remindInfo = parameterMap.get("remindInfo");
		Date now = new Date();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String sql = " ";
		if ("".equals(id) || id == null) {
			if (callreport != null && !"".equals(callreport)) {
				model.setId(custId+ dateFormat.format(now) + "01");
				model.setCallreportInfo(callreport);
				model.setCustId(custId);
				model.setCreateUser(auth.getUserId());
				model.setCreateTm(ts);
				model.setCreateOrg(auth.getUnitId());
				model.setCreateUsername(auth.getUsername());
				model.setCustName(custName);
				model.setUpdateUser(auth.getUserId());
				model.setUpdateUsername(auth.getUsername());
				model.setUpdateOrg(auth.getUnitId());
				model.setUpdateTm(ts);
				this.getBaseDAO().save(model);
			}
		} else {
			model = (OcrmFCiCallreportInfo) find(id);
			model.setCallreportInfo(callreport);
			model.setUpdateOrg(auth.getUnitId());
			model.setUpdateTm(ts);
			model.setUpdateUser(auth.getUserId());
			model.setUpdateUsername(auth.getUsername());
			this.getBaseDAO().save(model);
		}
		String ruleCode = " ";
		if (remindType.equals("理财")) {
			ruleCode = "306";
		} else if (remindType.equals("贷款")) {
			ruleCode = "307";
		} else if (remindType.equals("代发")) {
			ruleCode = "308";
		} else if (remindType.equals("保险")) {
			ruleCode = "309";
		} else if (remindType.equals("存款")) {
			ruleCode = "310";
		} else if (remindType.equals("其他")) {
			ruleCode = "311";
		}
		if (remindType != null && !"".equals(remindType)) {
    	  sql="insert into OCRM_F_WP_REMIND (INFO_ID,OPERATE_MGR,USER_ID,CUST_ID,CUST_NAME,RULE_CODE,MSG_CRT_DATE,MSG_START_DATE) values "
		      	+ "(SEQ_ID.NEXTVAL,'"+auth.getUsername()+"','"+auth.getUserId()+"','"+custId+"','"+custName+"','"+ruleCode+"',to_date('"+dateFormat2.format(new Date())+"','yyyy-mm-dd'),to_date('"+remindDt+"','yyyy-mm-dd'))";
			this.getBaseDAO().createNativeQueryWithIndexParam(sql, null).executeUpdate();
		}
	}
}
