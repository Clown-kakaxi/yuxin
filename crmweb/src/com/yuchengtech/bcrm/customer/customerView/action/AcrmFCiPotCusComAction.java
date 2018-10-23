package com.yuchengtech.bcrm.customer.customerView.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusHis;
import com.yuchengtech.bcrm.customer.customerView.service.AcrmFCiPotCusComService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/*
 * @description 法金潜在客户管理
 * @author likai
 * @since 2014-12-08
 */

@Action("/acrmFCiPotCusCom")
public class AcrmFCiPotCusComAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmFCiPotCusComService  acrmFCiPotCusComService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmFCiPotCusCom(); 
		setCommonService(acrmFCiPotCusComService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String authId =auth.getUnitId();
    	String userId = auth.getUserId();
//		StringBuilder sb = new StringBuilder(" select P.*, from ACRM_F_CI_POT_CUS_COM P INNER JOIN ADMIN_AUTH_ACCOUNT M ON P.CUST_MGR=M.ACCOUNT_NAME where P.cust_type <> '2' ");
//		sb.append(" left join ocrm_f_interview_record r on p.cus_id= r.cust_id ")
		StringBuffer sb = new StringBuffer("SELECT " +
				" P.CUS_ID," +
				" P.CUS_NAME," +
				" P.CERT_TYPE," +
				" P.CERT_CODE," +
				" P.CUS_PHONE," +
				" P.CUS_ADDR," +
				" P.ATTEN_NAME," +
				" P.ATTEN_BUSI," +
				" P.ATTEN_PHONE," +
				" P.LEGAL_NAME," +
				" P.REG_CAP_AMT," +
				" P.CUS_RESOURCE," +
				" P.ACT_CTL_NAME," +
				" P.ACT_CTL_PHONE," +
				" P.ACT_CTL_WIFE," +
				" P.AMOUNT2," +
				" P.AMOUNT1," +
				" P.AMOUNT," +
				" P.PRE_AMOUNT," +
				" P.AVE_BALANCE," +
				" P.TOTAL_ASS," +
				" P.LICENSE_FLAG," +
				" P.TAX_REC_FLAG," +
				" P.DEBT_AMOUNT," +
				" P.CAP_AMOUNT," +
				" P.LOAN_AMOUNT," +
				" P.FINA_AMOUNT," +
				" P.CREDIT_CARD_FLAG," +
				" P.DEBIT_FLAG," +
				" P.BAD_CREDIT_FLAG," +
				" P.PER_CARD_FLAG," +
				" P.CREDIT_CARD_BANK," +
				" P.PRE_CREDIT_AMOUNT," +
				" P.CREDIT_USE," +
				" P.TERM," +
				" P.REPAY_SOURCE," +
				" P.SUP_INF," +
				" P.BUYER_INF," +
				" P.RELATION_COM," +
				" P.GUA_MOR_FLAG," +
				" CASE WHEN M.USER_NAME  IS NULL THEN (SELECT O.ORG_NAME FROM ADMIN_AUTH_ORG O WHERE O.ORG_ID=P.CUST_MGR) ELSE M.USER_NAME " +
				" END  CUST_MGR," +
				" P.MAIN_BR_ID," +
				" P.INPUT_ID," +
				" P.INPUT_BR_ID," +
				" P.INPUT_DATE," +
				" P.QUE_INFO," +
				" P.REMARK," +
				" P.BUYER_INF_RATE," +
				" P.SUP_INF_RATE," +
				" P.PARTNER_INF1," +
				" P.PARTNER_INF2," +
				" P.PARTNER_INF3," +
				" P.RATE1," +
				" P.RATE2," +
				" P.RATE3," +
				" P.CUS_STATUS," +
				" P.CALL_NO," +
				" P.CONTMETH_INFO," +
				" P.CUST_TYPE," +
				" P.ZIPCODE," +
				" P.EN_NAME," +
				" P.CUST_STAT," +
				" P.JOB_TYPE," +
				" P.INDUST_TYPE," +
				" P.SHORT_NAME," +
				" P.DEL," +
				" P.SUP_INF_S," +
				" P.SUP_INF_S_RATE," +
				" P.BUYER_INF_S," +
				" P.BUYER_INF_S_RATE," +
				" P.RELATION_COM_S," +
				" P.Q_CUSTOMERTYPE," +
				" P.Q_INTERVIEWEENAME," +
				" P.Q_INTERVIEWEEPOST," +
				" P.Q_OPERATEYEARS," +
				" P.Q_BUSINESS," +
				" P.Q_MARKETIN," +
				" P.Q_ASSTOTAL," +
				" P.Q_MAGYEARS," +
				" P.Q_WORKYEARS," +
				" P.Q_FOUNDEDYEARS," +
				" P.Q_CREDITLIMIT," +
				" P.Q_ADDRYEARS," +
				" P.Q_PYEARINCOME," +
				" P.Q_LYEARINCOME," +
				" P.Q_TOTALINCOME," +
				" P.Q_PLANINCOME," +
				" P.G_HOUSE," +
				" P.G_HOUSEPLEDGE," +
				" P.G_LAND," +
				" P.G_LANDPLEDGE," +
				" P.G_EQUIPMENT," +
				" P.G_EQUIPMENTPLEDGE," +
				" P.G_FOREST," +
				" P.G_FORESTPLEDGE," +
				" P.G_MINING," +
				" P.G_MININGPLEDGE," +
				" P.G_FLOATING," +
				" P.G_FLOATPLEDGE," +
				" P.G_DEPOSIT," +
				" P.G_DEPOSITPLEDGE," +
				" P.G_VEHICLE," +
				" P.G_VEHICLEPLEDGE," +
				" P.G_RECEIVABLEMONEY," +
				" P.G_RECEIVABLEMPLEDGE," +
				" P.G_STOCK," +
				" P.G_STOCKPLEDGE," +
				" P.A_FILETYPE," +
				" P.B_FILETYPE," +
				" P.C_FILETYPE," +
				" P.CONCLUSION," +
				" P.ISNEW," +
				" P.PARTNER_INFO1," +
				" P.PARTNER_RATE1," +
				" P.PARTNER_INFO2," +
				" P.PARTNER_RATE2," +
				" P.PARTNER_INFO3," +
				" P.PARTNER_RATE3," +
				" P.IF_TARGETBUSI," +
				" P.REQ_CURRENCY," +
				" P.STATE," +
				" (SELECT M.USER_NAME FROM ADMIN_AUTH_ACCOUNT M WHERE M.ACCOUNT_NAME=P.MOVER_USER) AS MOVER_USER," +
				" P.BACK_STATE," +
				" P.MOVER_DATE," +
				" P.CUS_RESOURCE_BAK1," +
				" P.CUS_RESOURCE_BAK2," +
				" P.CUS_RESOURCE_BAK3," +
				" P.CUS_RESOURCE_BAK4," +
				" R.IVTIME,NR.NETIME, " +
                " CASE WHEN P.CUST_MGR='"+userId+"' THEN 1 ELSE 2 END CLO1 " +
				" FROM ACRM_F_CI_POT_CUS_COM P LEFT JOIN ADMIN_AUTH_ACCOUNT M" +
				" ON P.CUST_MGR = M.ACCOUNT_NAME" +
				" LEFT JOIN (SELECT COUNT(*) AS IVTIME,CUST_ID FROM OCRM_F_INTERVIEW_RECORD  WHERE REVIEW_STATE='3' GROUP BY CUST_ID ) R" +
				" ON P.CUS_ID = R.CUST_ID" +
				" LEFT JOIN (SELECT COUNT(*) AS NETIME,CUST_ID FROM OCRM_F_CALL_NEW_RECORD WHERE CALL_RESULT='1' GROUP BY CUST_ID ) NR" +
				" ON P.CUS_ID = NR.CUST_ID" +
				" WHERE (P.CUST_TYPE <> '2' OR P.CUST_TYPE IS NULL) ");	
		SQL=sb.toString();
		datasource = ds;
		 setPrimaryKey(" CLO1,BACK_STATE,OPERATE_TIME desc");
		configCondition("CUS_ID", "like", "CUS_ID", DataType.String);
		configCondition("CUS_NAME", "like", "CUS_NAME", DataType.String);
		configCondition("CUS_RESOURCE_BAK1", "like", "CUS_RESOURCE_BAK1", DataType.String);
		configCondition("CUS_RESOURCE_BAK2", "like", "CUS_RESOURCE_BAK2", DataType.String);
		configCondition("CUS_RESOURCE_BAK3", "like", "CUS_RESOURCE_BAK3", DataType.String);
	
	}
	
	public DefaultHttpHeaders saveData()  throws Exception{
		try{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		if(((AcrmFCiPotCusCom)model).getCusId() == null){
			Random random1 = new Random();
			int sp=Math.abs(random1.nextInt());
			String result="crm"+sp;
			((AcrmFCiPotCusCom)model).setCusName(((AcrmFCiPotCusCom)model).getCusName().trim());
 			((AcrmFCiPotCusCom)model).setCusId(result);
 			((AcrmFCiPotCusCom)model).setCustMgr(auth.getUserId());
 			((AcrmFCiPotCusCom)model).setCustType("1");
 			((AcrmFCiPotCusCom)model).setInputDate(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
 			((AcrmFCiPotCusCom)model).setState("0");
			acrmFCiPotCusComService.save(model);
		}else if(((AcrmFCiPotCusCom)model).getCusId() != null){
			String cusId = ((AcrmFCiPotCusCom)model).getCusId().toString();
			String jql = "update AcrmFCiPotCusCom c set c.cusName=:cusName,c.cusPhone=:cusPhone,c.cusAddr=:cusAddr,c.attenName=:attenName,c.attenBusi=:attenBusi,c.attenPhone=:attenPhone,c.legalName=:legalName,c.regCapAmt=:regCapAmt,"
					+" c.cusResource=:cusResource,c.actCtlName=:actCtlName,c.actCtlPhone=:actCtlPhone,c.actCtlWife=:actCtlWife,c.partnerInfo1=:partnerInfo1,c.partnerRate1=:partnerRate1,c.partnerInfo2=:partnerInfo2,c.partnerRate2=:partnerRate2,"
					+" c.partnerInfo3=:partnerInfo3,c.partnerRate3=:partnerRate3,c.amount2=:amount2,c.amount1=:amount1,c.amount=:amount,c.preAmount=:preAmount,c.aveBalance=:aveBalance,c.totalAss=:totalAss,"
					+" c.licenseFlag=:licenseFlag,c.taxRecFlag=:taxRecFlag,c.debtAmount=:debtAmount,c.capAmount=:capAmount,c.loanAmount=:loanAmount,c.finaAmount=:finaAmount,c.creditCardFlag=:creditCardFlag,"
					+" c.debitFlag=:debitFlag,c.badCreditFlag=:badCreditFlag,c.perCardFlag=:perCardFlag,c.creditCardBank=:creditCardBank,c.preCreditAmount=:preCreditAmount,c.creditUse=:creditUse,"
					+" c.term=:term,c.repaySource=:repaySource,c.supInf=:supInf,c.supInfRate=:supInfRate,c.supInfS=:supInfS,c.supInfSRate=:supInfSRate,c.buyerInf=:buyerInf,c.buyerInfRate=:buyerInfRate,"
					+" c.buyerInfS=:buyerInfS,c.buyerInfSRate=:buyerInfSRate,c.relationCom=:relationCom,c.relationComS=:relationComS,c.guaMorFlag=:guaMorFlag,c.qCustomertype=:qCustomertype,"
					+" c.qIntervieweename=:qIntervieweename,c.qIntervieweepost=:qIntervieweepost,c.qOperateyears=:qOperateyears,c.qBusiness=:qBusiness,c.qMarketin=:qMarketin,c.qAsstotal=:qAsstotal,"
					+" c.qMagyears=:qMagyears,c.qWorkyears=:qWorkyears,c.qFoundedyears=:qFoundedyears,c.qCreditlimit=:qCreditlimit,c.qAddryears=:qAddryears,c.qPyearincome=:qPyearincome,"
					+" c.qLyearincome=:qLyearincome,c.qTotalincome=:qTotalincome,c.qPlanincome=:qPlanincome,c.gHouse=:gHouse,c.gHousepledge=:gHousepledge,c.gLand=:gLand,c.gLandpledge=:gLandpledge,"
					+" c.gEquipment=:gEquipment,c.gEquipmentpledge=:gEquipmentpledge,c.gForest=:gForest,c.gForestpledge=:gForestpledge,c.gMining=:gMining,c.gMiningpledge=:gMiningpledge,"
					+" c.gFloating=:gFloating,c.gFloatpledge=:gFloatpledge,c.gDeposit=:gDeposit,c.gDepositpledge=:gDepositpledge,c.gVehicle=:gVehicle,c.gVehiclepledge=:gVehiclepledge,"
					+" c.gReceivablemoney=:gReceivablemoney,c.gReceivablempledge=:gReceivablempledge,c.gStock=:gStock,c.gStockpledge=:gStockpledge,c.aFiletype=:aFiletype,c.bFiletype=:bFiletype,"
					+" c.cFiletype=:cFiletype,c.conclusion=:conclusion,c.isnew=:isnew,c.reqCurrency=:reqCurrency,c.remark=:remark ,c.cusResourceBak4=:cusResourceBak4  where c.cusId = '"+cusId+"'";
			Map<String,Object> values = new HashMap<String,Object>();
			values.put("cusName",((AcrmFCiPotCusCom)model).getCusName().trim());
			values.put("cusPhone",((AcrmFCiPotCusCom)model).getCusPhone());
			values.put("cusAddr",((AcrmFCiPotCusCom)model).getCusAddr());
			values.put("attenName",((AcrmFCiPotCusCom)model).getAttenName());
			values.put("attenBusi",((AcrmFCiPotCusCom)model).getAttenBusi());
			values.put("attenPhone",((AcrmFCiPotCusCom)model).getAttenPhone());
			values.put("legalName",((AcrmFCiPotCusCom)model).getLegalName());
			values.put("regCapAmt",((AcrmFCiPotCusCom)model).getRegCapAmt());
			values.put("cusResource",((AcrmFCiPotCusCom)model).getCusResource());
			values.put("actCtlName",((AcrmFCiPotCusCom)model).getActCtlName());
			values.put("actCtlPhone",((AcrmFCiPotCusCom)model).getActCtlPhone());
			values.put("actCtlWife",((AcrmFCiPotCusCom)model).getActCtlWife());
			values.put("partnerInfo1",((AcrmFCiPotCusCom)model).getPartnerInfo1());
			values.put("partnerRate1",((AcrmFCiPotCusCom)model).getPartnerRate1());
			values.put("partnerInfo2",((AcrmFCiPotCusCom)model).getPartnerInfo2());
			values.put("partnerRate2",((AcrmFCiPotCusCom)model).getPartnerRate2());
			values.put("partnerInfo3",((AcrmFCiPotCusCom)model).getPartnerInfo3());
			values.put("partnerRate3",((AcrmFCiPotCusCom)model).getPartnerRate3());
			values.put("amount2",((AcrmFCiPotCusCom)model).getAmount2());
			values.put("amount1",((AcrmFCiPotCusCom)model).getAmount1());
			values.put("amount",((AcrmFCiPotCusCom)model).getAmount());
			values.put("preAmount",((AcrmFCiPotCusCom)model).getPreAmount());
			values.put("aveBalance",((AcrmFCiPotCusCom)model).getAveBalance());
			values.put("totalAss",((AcrmFCiPotCusCom)model).getTotalAss());
			values.put("licenseFlag",((AcrmFCiPotCusCom)model).getLicenseFlag());
			values.put("taxRecFlag",((AcrmFCiPotCusCom)model).getTaxRecFlag());
			values.put("debtAmount",((AcrmFCiPotCusCom)model).getDebtAmount());
			values.put("capAmount",((AcrmFCiPotCusCom)model).getCapAmount());
			values.put("loanAmount",((AcrmFCiPotCusCom)model).getLoanAmount());
			values.put("finaAmount",((AcrmFCiPotCusCom)model).getFinaAmount());
			values.put("creditCardFlag",((AcrmFCiPotCusCom)model).getCreditCardFlag());
			values.put("debitFlag",((AcrmFCiPotCusCom)model).getDebitFlag());
			values.put("badCreditFlag",((AcrmFCiPotCusCom)model).getBadCreditFlag());
			values.put("perCardFlag",((AcrmFCiPotCusCom)model).getPerCardFlag());
			values.put("creditCardBank",((AcrmFCiPotCusCom)model).getCreditCardBank());
			values.put("preCreditAmount",((AcrmFCiPotCusCom)model).getPreCreditAmount());
			values.put("creditUse",((AcrmFCiPotCusCom)model).getCreditUse());
			values.put("term",((AcrmFCiPotCusCom)model).getTerm());
			values.put("repaySource",((AcrmFCiPotCusCom)model).getRepaySource());
			values.put("supInf",((AcrmFCiPotCusCom)model).getSupInf());
			values.put("supInfRate",((AcrmFCiPotCusCom)model).getSupInfRate());
			values.put("supInfS",((AcrmFCiPotCusCom)model).getSupInfS());
			values.put("supInfSRate",((AcrmFCiPotCusCom)model).getSupInfSRate());
			values.put("buyerInf",((AcrmFCiPotCusCom)model).getBuyerInf());
			values.put("buyerInfRate",((AcrmFCiPotCusCom)model).getBuyerInfRate());
			values.put("buyerInfS",((AcrmFCiPotCusCom)model).getBuyerInfS());
			values.put("buyerInfSRate",((AcrmFCiPotCusCom)model).getBuyerInfSRate());
			values.put("relationCom",((AcrmFCiPotCusCom)model).getRelationCom());
			values.put("relationComS",((AcrmFCiPotCusCom)model).getRelationComS());
			values.put("guaMorFlag",((AcrmFCiPotCusCom)model).getGuaMorFlag());
			//values.put("custMgr",auth.getUserId());
			values.put("qCustomertype",((AcrmFCiPotCusCom)model).getQCustomertype());
			values.put("qIntervieweename",((AcrmFCiPotCusCom)model).getQIntervieweename());
			values.put("qIntervieweepost",((AcrmFCiPotCusCom)model).getQIntervieweepost());
			values.put("qOperateyears",((AcrmFCiPotCusCom)model).getQOperateyears());
			values.put("qBusiness",((AcrmFCiPotCusCom)model).getQBusiness());
			values.put("qMarketin",((AcrmFCiPotCusCom)model).getQMarketin());
			values.put("qAsstotal",((AcrmFCiPotCusCom)model).getQAsstotal());
			values.put("qMagyears",((AcrmFCiPotCusCom)model).getQMagyears());
			values.put("qWorkyears",((AcrmFCiPotCusCom)model).getQWorkyears());
			values.put("qFoundedyears",((AcrmFCiPotCusCom)model).getQFoundedyears());
			values.put("qCreditlimit",((AcrmFCiPotCusCom)model).getQCreditlimit());
			values.put("qAddryears",((AcrmFCiPotCusCom)model).getQAddryears());
			values.put("qPyearincome",((AcrmFCiPotCusCom)model).getQPyearincome());
			values.put("qLyearincome",((AcrmFCiPotCusCom)model).getQLyearincome());
			values.put("qTotalincome",((AcrmFCiPotCusCom)model).getQTotalincome());
			values.put("qPlanincome",((AcrmFCiPotCusCom)model).getQPlanincome());
			values.put("gHouse",((AcrmFCiPotCusCom)model).getGHouse());
			values.put("gHousepledge",((AcrmFCiPotCusCom)model).getGHousepledge());
			values.put("gLand",((AcrmFCiPotCusCom)model).getGLand());
			values.put("gLandpledge",((AcrmFCiPotCusCom)model).getGLandpledge());
			values.put("gEquipment",((AcrmFCiPotCusCom)model).getGEquipment());
			values.put("gEquipmentpledge",((AcrmFCiPotCusCom)model).getGEquipmentpledge());
			values.put("gForest",((AcrmFCiPotCusCom)model).getGForest());
			values.put("gForestpledge",((AcrmFCiPotCusCom)model).getGForestpledge());
			values.put("gMining",((AcrmFCiPotCusCom)model).getGMining());
			values.put("gMiningpledge",((AcrmFCiPotCusCom)model).getGMiningpledge());
			values.put("gFloating",((AcrmFCiPotCusCom)model).getGFloating());
			values.put("gFloatpledge",((AcrmFCiPotCusCom)model).getGFloatpledge());
			values.put("gDeposit",((AcrmFCiPotCusCom)model).getGDeposit());
			values.put("gDepositpledge",((AcrmFCiPotCusCom)model).getGDepositpledge());
			values.put("gVehicle",((AcrmFCiPotCusCom)model).getGVehicle());
			values.put("gVehiclepledge",((AcrmFCiPotCusCom)model).getGVehiclepledge());
			values.put("gReceivablemoney",((AcrmFCiPotCusCom)model).getGReceivablemoney());
			values.put("gReceivablempledge",((AcrmFCiPotCusCom)model).getGReceivablempledge());
			values.put("gStock",((AcrmFCiPotCusCom)model).getGStock());
			values.put("gStockpledge",((AcrmFCiPotCusCom)model).getGStockpledge());
			values.put("aFiletype",((AcrmFCiPotCusCom)model).getAFiletype());
			values.put("bFiletype",((AcrmFCiPotCusCom)model).getBFiletype());
			values.put("cFiletype",((AcrmFCiPotCusCom)model).getCFiletype());
			values.put("conclusion",((AcrmFCiPotCusCom)model).getConclusion());
			values.put("isnew",((AcrmFCiPotCusCom)model).getIsnew());
			values.put("reqCurrency",((AcrmFCiPotCusCom)model).getReqCurrency());
			values.put("remark",((AcrmFCiPotCusCom)model).getRemark());
            values.put("cusResourceBak4", ((AcrmFCiPotCusCom)model).getCusResourceBak4()==null?"":((AcrmFCiPotCusCom)model).getCusResourceBak4());
            acrmFCiPotCusComService.batchUpdateByName(jql, values);
			
			updatePotCusInfo(cusId, ((AcrmFCiPotCusCom)model).getCusName());
		}
		}catch(Exception e){
			throw new BizException(1,0,"0000","输入的格式有误请检查"); 
		}
		return new DefaultHttpHeaders("success");
	}
	
	public void updatePotCusInfo(String custId,String custName){
		
		String sql="update acrm_f_ci_pot_cus_com  t set t.OPERATE_TIME=systimestamp where cus_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql);
		String sql1="update ocrm_f_interview_record set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql1);
		String sql2="update ocrm_f_call_new_record set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql2);
		String sql3="update OCRM_F_CI_MKT_PROSPECT_C set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql3);
		String sql4="update OCRM_F_CI_MKT_INTENT_C set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql4);
		String sql5="update OCRM_F_CI_MKT_CA_C set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql5);
		String sql6="update OCRM_F_CI_MKT_CHECK_C set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql6);
		String sql7="update OCRM_F_CI_MKT_APPROVL_C set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql7);
		String sql8="update OCRM_F_CI_MKT_APPROVED_C set cust_name='"+custName+"' where cust_id='"+custId+"'";
		acrmFCiPotCusComService.updatePotCusInfo(sql8);
	}
	
	
	public void  doNameCheck(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custName = request.getParameter("cusName").trim();
		String type ="";
		boolean flag = checkCustName(custName);
		boolean PotenFlag = checkPotentialCust(custName);
		if(flag){
			type="1";
		}else if(PotenFlag){
			type="2";
		}else{
			type="3";
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		this.setJson(map);
	}
	
	
	/**
	 * 与正式客户做匹配
	 * @param custName
	 * @return
	 */
	public boolean checkCustName(String custName){
		boolean flag = false;
		try{
			if(custName!=null && !"".equals(custName)){
				String sql ="select cust_id from acrm_f_ci_customer where cust_name='"+custName+"'";
				Connection  connection = ds.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery(sql);
				List<String> List = new ArrayList<String>();
				String custId="";
			    while (result.next()){
			    	custId = result.getString(1);
			    	List.add(custId);
			    }
			    if(List!= null && List.size()>0){
			    	flag = true;
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean checkPotentialCust(String custName){
		boolean flag = false;
		try{
			if(custName!=null && !"".equals(custName)){
				if(custName.length()>=6){
					custName=custName.substring(0, 6);
					String sql="select cus_id from ACRM_F_CI_POT_CUS_COM where cus_name like '"+custName+"%'";
					Connection  connection = ds.getConnection();
					Statement stmt = connection.createStatement();
					ResultSet result = stmt.executeQuery(sql);
					List<String> List = new ArrayList<String>();
					String custId="";
				    while (result.next()){
				    	custId = result.getString(1);
				    	List.add(custId);
				    }
				    if(List!= null && List.size()>0){
				    	flag = true;
				    }
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmFCiPotCusComService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
    //恢复
    public DefaultHttpHeaders recoverBack(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmFCiPotCusComService.recoverBack(request);
		return new DefaultHttpHeaders("success");
    }
    
    //分配
    
    public DefaultHttpHeaders fbPotCusInfo() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String authId = auth.getUserId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String date = sdf.format(new Date());
    	String cusId = request.getParameter("cusId");
		String type=request.getParameter("type");
		String custMgr = request.getParameter("custMgr");
  
		if(type.equals("1")){
			List list = auth.getRolesInfo();
	    	List<String> roleList = new ArrayList<String>();
	    	for(int i=0;i<list.size();i++){
	    		Map<String,String> map = (Map<String, String>) list.get(i);
	    		roleList.add(map.get("ROLE_CODE"));
	    	}
	    	if(!roleList.contains("R311") && !roleList.contains("R202")){
	    		if(roleList.contains("R106") || roleList.contains("R309")){
	    			throw new BizException(1,0,"0000","您沒有权限将客户分配给机构"); 
	    		}
	    	}
			Map<String,String> map = getOrgInfo(custMgr);
			String upOrgId = map.get("upOgrId")==null?"":map.get("upOgrId");
			String upOrgName = map.get("upOrgName")==null?"":map.get("upOrgName");
			String orgId=map.get("orgId")==null?"":map.get("orgId");
			String orgName = map.get("orgName")==null?"":map.get("orgName");	
			String sql="update acrm_f_ci_pot_cus_com  set cust_mgr='"+orgId+"', main_br_id='"+orgId+"',mover_user='"+authId+"',mover_date=to_date('"+date+"','yyyyMMdd'),OPERATE_TIME=systimestamp where  cus_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql);
			String sql1=" update ocrm_f_interview_record  a set a.mgr_id='"+orgId+"',a.mgr_name='"+orgName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql1);
			String sql2=" update ocrm_f_call_new_record a set a.mgr_id='"+orgId+"',a.mgr_name='"+orgName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql2);
			String sql3="update OCRM_F_CI_MKT_PROSPECT_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+orgName+"',user_id='"+custMgr+"',rm_id='"+custMgr+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql3);
			String sql4="update OCRM_F_CI_MKT_INTENT_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+orgName+"',user_id='"+custMgr+"',rm_id='"+custMgr+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql4);
			String sql5="update OCRM_F_CI_MKT_CA_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+orgName+"',user_id='"+custMgr+"',rm_id='"+custMgr+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql5);
			String sql6="update OCRM_F_CI_MKT_CHECK_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+orgName+"',user_id='"+custMgr+"',rm_id='"+custMgr+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql6);
			String sql7="update OCRM_F_CI_MKT_APPROVL_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+orgName+"',user_id='"+custMgr+"',rm_id='"+custMgr+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql7);
			String sql8="update OCRM_F_CI_MKT_APPROVED_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+orgName+"',user_id='"+custMgr+"',rm_id='"+custMgr+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql8);
		}else if(type.equals("2")){
			Map<String,String> map = getCustMgrInfo(custMgr);
			String upOrgId = map.get("upOgrId")==null?"":map.get("upOgrId");
			String upOrgName = map.get("upOrgName")==null?"":map.get("upOrgName");
			String orgId=map.get("orgId")==null?"":map.get("orgId");
			String orgName = map.get("orgName")==null?"":map.get("orgName");	
			String accountName=map.get("accountName")==null?"":map.get("accountName");
			String userName=map.get("userName")==null?"":map.get("userName");
			String sql="update acrm_f_ci_pot_cus_com  set cust_mgr='"+accountName+"', main_br_id='"+orgId+"',mover_user='"+authId+"',mover_date=to_date('"+date+"','yyyyMMdd'),OPERATE_TIME=systimestamp where  cus_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql);
			String sql1=" update ocrm_f_interview_record  a set a.mgr_id='"+accountName+"',a.mgr_name='"+userName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql1);
			String sql2=" update ocrm_f_call_new_record a set a.mgr_id='"+accountName+"',a.mgr_name='"+userName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql2);
			String sql3="update OCRM_F_CI_MKT_PROSPECT_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+accountName+"',rm_id='"+accountName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql3);
			String sql4="update OCRM_F_CI_MKT_INTENT_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+accountName+"',rm_id='"+accountName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql4);
			String sql5="update OCRM_F_CI_MKT_CA_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+accountName+"',rm_id='"+accountName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql5);
			String sql6="update OCRM_F_CI_MKT_CHECK_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+accountName+"',rm_id='"+accountName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql6);
			String sql7="update OCRM_F_CI_MKT_APPROVL_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+accountName+"',rm_id='"+accountName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql7);
			String sql8="update OCRM_F_CI_MKT_APPROVED_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+accountName+"',rm_id='"+accountName+"' where cust_id in ("+cusId+")";
			acrmFCiPotCusComService.updatePotCusInfo(sql8);

		}
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdfs.format(new Date());
		if(cusId.contains(",")){
		  String[] custIds = cusId.split(",");
		  for(String custId:custIds){
			  String custName = getCustNameByCustId(custId);
			  String sql="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,mover_date) values("+custId+",'"+custName+"','1','"+authId+"','"+time+"')";
				acrmFCiPotCusComService.updatePotCusInfo(sql);
		  }
			
		}else{
			String custName = getCustNameByCustId(cusId);
			String sql="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,mover_date) values("+cusId+",'"+custName+"','1','"+authId+"','"+time+"')";
			acrmFCiPotCusComService.updatePotCusInfo(sql);
		}
		
		
		acrmFCiPotCusComService.fbPotCusInfo(request);
		return new DefaultHttpHeaders("success");
    }
    
    public Map<String,String> getCustMgrInfo(String custMgr){
    	Map<String,String> map = new HashMap<String, String>();
    	try{
    		String upOgrId="";
    		String upOrgName="";
    		String orgId="";
    		String orgName="";
    		String accountName="";
    		String userName="";
	    	String sql="select t.org_id as up_org_id, t.org_name as up_org_name, e.org_id,e.org_name,e.account_name,e.user_name from admin_auth_org t," +
	    			" (select o.up_org_id,o.org_id,o.org_name,a.account_name,a.user_name from admin_auth_org o" +
	    			" left join admin_auth_account a" +
	    			" on o.org_id = a.org_id" +
	    			" where a.account_name='"+custMgr+"'" +
	    			" ) e where t.org_id = e.up_org_id";
	    	Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				 upOgrId = result.getString("up_org_id");
				 upOrgName = result.getString("up_org_name");
				 orgId = result.getString("org_id");
				 orgName= result.getString("org_name");
				 accountName = result.getString("account_name");
				 userName = result.getString("user_name");
			}
			map.put("upOgrId", upOgrId);
			map.put("upOrgName", upOrgName);
			map.put("orgId", orgId);
			map.put("orgName", orgName);
			map.put("accountName", accountName);
			map.put("userName", userName);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return map;
    }
    
    //归属是机构
    public Map<String,String> getOrgInfo(String custMgr){
		Map<String,String> map = new HashMap<String, String>();
		try{
			String upOgrId="";
			String upOrgName="";
			String orgId="";
			String orgName="";
			String sql="select  a.org_id as up_org_id,a.org_name as up_org_name,e.org_id ,e.org_name from admin_auth_org a ," +
					" (select org_id,org_name,up_org_id from admin_auth_org o where org_id='"+custMgr+"') e where a.org_id=e.up_org_id";
			Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				 upOgrId = result.getString("up_org_id");
				 upOrgName = result.getString("up_org_name");
				 orgId = result.getString("org_id");
				 orgName= result.getString("org_name");
			}
			map.put("upOgrId", upOgrId);
			map.put("upOrgName", upOrgName);
			map.put("orgId", orgId);
			map.put("orgName", orgName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
    
    //回收
    public DefaultHttpHeaders moverBack() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cusId = request.getParameter("cusId");
    	String userId=auth.getUserId();//当前用户ID
    	String userName = auth.getUsername();//当前用户名
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String date = sdf.format(new Date());
    	String orgId = auth.getUnitId();//当前用户的机构编号
    	String orgName = auth.getUnitName();//当前用户的机构名称
    	
    	String upOrgId="";//区域的编号
    	String upOrgName="";//区域的名称
    	Map<String,String> map = getArea(userId);
    	upOrgId=map.get("orgId")==null?"":map.get("orgId");
    	upOrgName=map.get("orgName")==null?"":map.get("orgName");
    	String sql="update acrm_f_ci_pot_cus_com  set cust_mgr='"+userId+"', main_br_id='"+orgId+"', back_state ='0',mover_user='"+userId+"',mover_date=to_date('"+date+"','yyyyMMdd'),OPERATE_TIME=systimestamp where  cus_id in ("+cusId+")";
    	acrmFCiPotCusComService.updatePotCusInfo(sql);
    	String sql1=" update ocrm_f_interview_record  a set a.mgr_id='"+userId+"',a.mgr_name='"+userName+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql1);
		String sql2=" update ocrm_f_call_new_record a set a.mgr_id='"+userId+"',a.mgr_name='"+userName+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql2);
		
		String sql3="update OCRM_F_CI_MKT_PROSPECT_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+userId+"',rm_id='"+userId+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql3);
		String sql4="update OCRM_F_CI_MKT_INTENT_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+userId+"',rm_id='"+userId+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql4);
		String sql5="update OCRM_F_CI_MKT_CA_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+userId+"',rm_id='"+userId+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql5);
		String sql6="update OCRM_F_CI_MKT_CHECK_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+userId+"',rm_id='"+userId+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql6);
		String sql7="update OCRM_F_CI_MKT_APPROVL_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+userId+"',rm_id='"+userId+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql7);
		String sql8="update OCRM_F_CI_MKT_APPROVED_C set area_id='"+upOrgId+"',area_name='"+upOrgName+"',dept_id='"+orgId+"',dept_name='"+orgName+"',rm='"+userName+"',user_id='"+userId+"',rm_id='"+userId+"' where cust_id in ("+cusId+")";
		acrmFCiPotCusComService.updatePotCusInfo(sql8);
		
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdfs.format(new Date());
		if(cusId.contains(",")){
			  String[] custIds = cusId.split(",");
			  for(String custId:custIds){
				  String custName = getCustNameByCustId(custId);
				  String sql9="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,mover_date) values("+custId+",'"+custName+"','2','"+userId+"','"+time+"')";
					acrmFCiPotCusComService.updatePotCusInfo(sql9);
			  }
				
			}else{
				String custName = getCustNameByCustId(cusId);
				String sql9="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,mover_date) values("+cusId+",'"+custName+"','2','"+userId+"','"+time+"')";
				acrmFCiPotCusComService.updatePotCusInfo(sql9);
			}
		
		return new DefaultHttpHeaders("success");
    }
    //获取区域的编号和名称
    public Map<String,String> getArea(String custMgr){
    	Map<String,String> map = new HashMap<String, String>();
    	String sql="select org_id,org_name from admin_auth_org where org_id=(" +
    			" select o.up_org_id from admin_auth_org o" +
    			" left join admin_auth_account a" +
    			" on o.org_id = a.org_id" +
    			" where a.account_name='"+custMgr+"')";
    	try{
    		Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			String orgId="";
			String orgName="";
			while(result.next()){
				 orgId = result.getString("org_id");
				 orgName= result.getString("org_name");
			}
			map.put("orgId", orgId);
			map.put("orgName", orgName);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return map;
    }
    
    public void  doCheckRole(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("cusId");
    	List<String> list = new ArrayList<String>();
    	String type="";
    	boolean backFlag = checkCustBackState(custId);
    	if(backFlag){//存在未收回的
    		type="4";
    	}else{
	      	String types="";
	    	if(custId.contains(",")){
	    		String[] strList = custId.split(",");
	    		for(String str:strList){
	    		 	boolean MoverFlag = checkMoverUser(str);//判断是否存在分配移交人
	    	    	boolean roleCheck = checkUserNameRole(str);//判断客户的归属人
	    	    	if(MoverFlag && roleCheck){//已有分配人,归属人角色是RM
			    		types="1";
			    		list.add(types);
			    	}else if((MoverFlag && !roleCheck) || (!MoverFlag && !roleCheck)){//已有分配人,但是归属人角色不是RM或者没有分配人并且归属不是RM的
			    		types="3";
			    		list.add(types);
			    	}else{//如果没有归属人并且归属是RM的是不能分配的
			    		types="2";
			    		list.add(types);
			    	}
	    		}
	    	}else{
	    		boolean MoverFlag = checkMoverUser(custId);
		    	boolean roleCheck = checkUserNameRole(custId);
		    	if(MoverFlag && roleCheck){//已有分配人,归属人角色是RM
		    		types="1";
		    		list.add(types);
		    	}else if((MoverFlag && !roleCheck) || (!MoverFlag && !roleCheck)){//已有分配人,但是归属人角色不是RM或者没有分配人并且归属不是RM的
		    		types="3";
		    		list.add(types);
		    	}else{//如果没有归属人并且归属是RM的是不能分配的
		    		types="2";
		    		list.add(types);
		    	}
	    	}
	    	if(list.contains("1")){//如果存在某一个客户他存在分配移交人并且主管客户经理是客户经理只能先收回
	    		type="1";
	    	}else if(list.contains("3")){
	    		type="2";
	    	}else{
	    		type="3";
	    	} 
        }
    	Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		this.setJson(map);
    }
    //是否有分配人
    public boolean checkMoverUser(String custId){
    	boolean flag = false;
    	try{
    		String sql="select p.mover_user from acrm_f_ci_pot_cus_com p where cus_id IN ("+custId+")";
    	 	Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			String mover_user="";
		    while (result.next()){
		    	mover_user = result.getString(1);
		    	if(mover_user==null || "".equals(mover_user)){
		    		mover_user="1";
		    	}else{
		    		mover_user="2";
		    	}
		    }
		    if(mover_user.equals("2")){
		    	flag = true;
		    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return flag;
    }
    //校验归属人角色
    public boolean checkUserNameRole(String cusId){	
    	boolean flag = false;
    	try{	
    	 String sql="SELECT ROLE_CODE FROM ( SELECT F.*," +
						"(CASE WHEN " +
						" F.IDCHECK IS NULL THEN " +
						" 0 " +
						" ELSE " +
						" 1 " +
						" END) IS_CHECKED " +
						" FROM (SELECT * " +
						" FROM ADMIN_AUTH_ROLE T0 " +
						" LEFT JOIN (SELECT T1.ID AS IDCHECK, T1.ROLE_ID " +
						"  FROM ADMIN_AUTH_ACCOUNT_ROLE T1 " +
						"  LEFT JOIN ADMIN_AUTH_ACCOUNT T2 " +
						"  ON T2.ID = T1.ACCOUNT_ID " +
						"  WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME IN (SELECT  CUST_MGR FROM ACRM_F_CI_POT_CUS_COM  WHERE CUS_ID IN ("+cusId+")))) E " +
						"   ON E.ROLE_ID = T0.ID " +
						"  WHERE T0.ROLE_LEVEL >= '1') F " +
						" WHERE 1 = 1 " +
						" ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'";
	    	Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			List<String> List = new ArrayList<String>();
			String role="";
		    while (result.next()){
		    	role = result.getString(1);
		    	List.add(role);
		    }
		    if(List.size()==1){
		    	if(List.get(0).equals("R305") || List.get(0).equals("R105")){
		    		flag = true;
		    	}
		    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return flag;
    }
    
   
    
//    /**
//     * 检测分配的潜在客户是否发生业务数据
//     * @param custId
//     * @return
//     */
//    public boolean checkInfo(String cusId){
//    	boolean flag=false;
//    	try{
//		    	String type="";
//		    	Connection  connection = ds.getConnection();
//				Statement stmt = connection.createStatement();
//				List<String> list = new ArrayList<String>();
//		    	if(cusId.contains(",")){
//		    		String[] str =cusId.split(",");
//		    		for(int i=0;i<str.length;i++){
//		    			String cusId_Mgr = str[i];
//		    			String[] strs = cusId_Mgr.split("-");
//		        		String custId=strs[0];
//		        		String custMgr = strs[1];
//		        		String sql1="   select cust_id from  ocrm_f_interview_record where cust_id ="+custId+" and mgr_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  ocrm_f_call_new_record where cust_id ="+custId+"and mgr_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  OCRM_F_CI_MKT_PROSPECT_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  OCRM_F_CI_MKT_INTENT_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  OCRM_F_CI_MKT_CA_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  OCRM_F_CI_MKT_CHECK_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  OCRM_F_CI_MKT_APPROVL_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		        				" union all" +
//		        				" select cust_id from  OCRM_F_CI_MKT_APPROVED_C where cust_id ="+custId+"and user_id ="+custMgr+"";
//		        		   ResultSet result = stmt.executeQuery(sql1);
//			       		   if (result.next()){//存在数据
//			       			   type="1";
//			       			   list.add(type);
//			       		   }else{//不存在数据
//			       			   type="2";
//			       			   list.add(type);
//			       		   }        		
//		    		}
//		    	}else{
//		    		String[] str = cusId.split("-");
//		    		String custId=str[0];
//		    		String custMgr = str[1];
//		    		String sql1="   select cust_id from  ocrm_f_interview_record where cust_id ="+custId+" and mgr_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  ocrm_f_call_new_record where cust_id ="+custId+"and mgr_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  OCRM_F_CI_MKT_PROSPECT_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  OCRM_F_CI_MKT_INTENT_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  OCRM_F_CI_MKT_CA_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  OCRM_F_CI_MKT_CHECK_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  OCRM_F_CI_MKT_APPROVL_C where cust_id ="+custId+"and user_id ="+custMgr+""+
//		    				" union all" +
//		    				" select cust_id from  OCRM_F_CI_MKT_APPROVED_C where cust_id ="+custId+"and user_id ="+custMgr+"";
//		    		  ResultSet result = stmt.executeQuery(sql1);
//		    		  if (result.next()){//存在数据
//		      			   type="1";
//		      			   list.add(type);
//		      		   }else{//不存在数据
//		      			   type="2";
//		      			   list.add(type);
//		      		   }        		
//		    	}
//    	 
//		    if(list!=null && list.size()>0){
//		    	if(list.contains("1")){
//		    		flag=true;
//		    	}
//		    }  	
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    	return flag;
//    }
    
    
    public boolean checkCustBackState(String custId){
    	boolean flag=false;
    	try{
    		Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
    		String sql="select back_state from acrm_f_ci_pot_cus_com  where cus_id in ("+custId+") ";
    		ResultSet result = stmt.executeQuery(sql);
    		List<String> list = new ArrayList<String>();
    		String backState="";
    		while(result.next()){
    			backState=result.getString("back_state");
    			list.add(backState);
    		}
    		
    		if(list!=null){
    			if(list.contains("1")){
    				flag=true;
    			}
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return flag;
    }
    
    /**
     * 根据客户编号获取客户名称
     * @param custId
     * @return
     * @throws Exception
     */
    public String getCustNameByCustId(String custId){
    	String custName="";
    	try{
    		Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
    		String sql="select cus_name from acrm_f_ci_pot_cus_com  where cus_id ="+custId+"";
    		ResultSet result = stmt.executeQuery(sql);
    		while(result.next()){
    			custName=result.getString("cus_name");
    		}	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return custName;
    }
}
