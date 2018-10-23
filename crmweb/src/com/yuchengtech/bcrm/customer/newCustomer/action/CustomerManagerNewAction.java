package com.yuchengtech.bcrm.customer.newCustomer.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bcrm.customer.newCustomer.service.CustomerManagerNewService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/*
 * @description 客户管理
 * @author sunjing5
 * @since 2017-05-01
 */

@Action("/customerManagerNew")
public class CustomerManagerNewAction extends CommonAction {
	private static Logger log = Logger.getLogger(CustomerManagerNewAction.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  CustomerManagerNewService  CustomerManagerNewService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmFCiPotCusCom(); 
		setCommonService(CustomerManagerNewService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		StringBuffer sb=new StringBuffer("SELECT * FROM (SELECT C.LOAN_CUST_ID,C.LOAN_CUST_STAT,C.POTENTIAL_FLAG,C.CUST_ID,C.CUST_NAME,C.CORE_NO,C.IDENT_TYPE,C.IDENT_NO,C.CUST_TYPE,C.CUST_LEVEL,C.CURRENT_AUM,C.TOTAL_DEBT,C.RISK_LEVEL," +
				" NVL(EXE.LINKMAN_NAME, PLINK.LINKMAN_NAME) LINKMAN_NAME, NVL(EXE.MOBILE, PLINK.MOBILE) LINKMAN_TEL,O.org_biz_cust_type as BELONG_LINE_NO,C.CUST_STAT,M.INSTITUTION,M.INSTITUTION_NAME AS ORG_NAME, EE.CUST_GRADE_TYPE, " +
		        "	 CASE WHEN M.MGR_NAME IS NULL THEN (SELECT A.USER_NAME FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME='511N1456')"+
                " ELSE M.MGR_NAME END AS  MGR_NAME,"+
				" C.CREDIT_LEVEL,C.FAXTRADE_NOREC_NUM,AA.ACCOUNT_NAME MGR_ID1 ,AA.USER_NAME BELONG_TEAM_HEAD_NAME,L.BL_NAME, EE.CUST_GRADE AS FXQ_RISK_LEVEL, "+
				" CASE  WHEN EE.LAST_UPDATE_USER <> 'ETL' THEN TT.USER_NAME ELSE EE.LAST_UPDATE_USER  END LAST_UPDATE_USER," +
				" IX.FXQ006, "+//客户是否为代理开户
				" IX.FXQ007, "+//客户办理的业务(对私)
				" IX.FXQ008, "+//是否涉及风险提示信息或权威媒体报道信息
				" IX.FXQ009, "+//客户或其亲属、关系密切人等是否属于外国政要
				
				" IX.FXQ010, "+//反洗钱交易监测记录
				" IX.FXQ011, "+//是否被列入中国发布或承认的应实施反洗钱监控措施的名单
				" IX.FXQ012, "+//是否发生具有异常特征的大额现金交易
				" IX.FXQ013, "+//是否发生具有异常特征的非面对面交易
				" IX.FXQ014, "+//是否存在多次涉及跨境异常交易报告
				" IX.FXQ015, "+//代办业务是否存在异常情况
				" IX.FXQ016, "+//是否频繁进行异常交易
				
				" IX.FXQ021, "+//与客户建立业务关系的渠道
				" IX.FXQ022, "+//是否在规范证券市场上市
				" IX.FXQ023, "+//客户的股权或控制权结构
				" IX.FXQ024, "+//客户是否存在隐名股东或匿名股东
				" IX.FXQ025,  "+//客户办理的业务(对公)
				" C.CREATE_DATE, " +
				" s.SPECIAL_LIST_TYPE, " + //特殊名单类型
				" s.SPECIAL_LIST_KIND, " + //特殊名单类别
				" s.SPECIAL_LIST_FLAG, " + //特殊名单标志
				" s.ORIGIN, " + //数据来源
				" s.STAT_FLAG, " + //状态标志
				" s.APPROVAL_FLAG, " + //审核标志
				" s.START_DATE, " + //起始日期
				" s.END_DATE, " + //结束日期
				" s.ENTER_REASON, " + //列入原因
				" n.email ," +
				" M.MGR_ID,O1.ORG_ID"+
				" FROM ACRM_F_CI_CUSTOMER C " +
				" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID " +
				" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON M.MGR_ID = A.ACCOUNT_NAME"+
				" LEFT JOIN  ADMIN_AUTH_ACCOUNT_ORG  O1 ON A.ACCOUNT_ID=O1.ACCOUNT_ID"+//添加ORG_ID
				" LEFT JOIN ADMIN_AUTH_ACCOUNT AA ON A.BELONG_TEAM_HEAD = AA.ACCOUNT_NAME"+
				" left join acrm_f_ci_org o on o.CUST_ID = C.CUST_ID " +

				" LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type =  L.BL_NO  " +
				" left join OCRM_F_CI_ANTI_CUST_LIST risk on risk.cust_id = c.cust_id " +
				" LEFT JOIN ACRM_F_SYS_CUST_FXQ_INDEX IX ON IX.CUST_ID = C.CUST_ID " + 
				" left join ACRM_F_CI_SPECIALLIST s on s.CUST_ID = C.CUST_ID " +
				" LEFT JOIN ACRM_F_CI_ORG_EXECUTIVEINFO EXE  ON EXE.ORG_CUST_ID = C.CUST_ID  AND EXE.LINKMAN_TYPE = '21'"+//--机构干系人
				" LEFT JOIN ACRM_F_CI_PER_LINKMAN PLINK  ON PLINK.CUST_ID = C.CUST_ID  AND PLINK.LINKMAN_TYPE = '21'"+//--个人联系人信息
				" LEFT JOIN ACRM_F_CI_GRADE EE ON EE.CUST_ID = C.CUST_ID AND EE.CUST_GRADE_TYPE = '01'"+
				" LEFT JOIN ADMIN_AUTH_ACCOUNT TT ON TT.ACCOUNT_NAME = EE.LAST_UPDATE_USER"+
				" left join ACRM_F_CI_PERSON n on c.cust_id = n.cust_id)M" +
				" WHERE 1=1  ");
			
			for(String key:this.getJson().keySet()){
				if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
					if("ACCT_NO".equals(key)){
						sb.append(" and M.cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
					}
					if("ORG_NAME".equals(key)){ 
						sb.append(" and (m.INSTITUTION in (SELECT unitid FROM SYS_UNITS  WHERE UNITSEQ LIKE '%"+this.json.get(key)+"%'))");
					}
					if("BL_NAME".equals(key)&&!"归属业务条线".equals(this.json.get(key))){
						sb.append(" and  (M.org_biz_cust_type in (select distinct bl_ID from ACRM_F_CI_BUSI_LINE t START   WITH bl_ID='"+this.json.get(key)+"' CONNECT BY PRIOR BL_ID=PARENT_ID))");
					}
					if("QUERY_AUTH".equals(key)&&!this.getJson().get(key).equals("")&&("2").equals(this.json.get(key))){
						sb.append(" and  m.MGR_ID='"+auth.getUserId()+"'");
					}
				}
			}
			//添加证件类型与证件号码查询,取证件表上的数据进行查询
			if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))
				&& null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
				sb.append(" and M.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
			}else if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))){
				sb.append(" and EXISTS  (SELECT 1 FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND M.CUST_ID =I.CUST_ID)");
			}else if(null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
				sb.append(" and M.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
			}
			 addOracleLookup("LOAN_CUST_STAT", "XD000075");
			SQL=sb.toString();
			datasource=ds;
			
			//setPrimaryKey("c.CUST_ID desc ");
			configCondition("M.CUST_ID","like","CUST_ID",DataType.String);
			configCondition("M.CUST_NAME","like","CUST_NAME",DataType.String);
			configCondition("M.CUST_TYPE","=","CUST_TYPE",DataType.String);
			configCondition("M.CUST_STAT","=","CUST_STAT",DataType.String);
			configCondition("M.LINKMAN_NAME","like","LINKMAN_NAME",DataType.String);
			configCondition("M.LINKMAN_TEL","like","LINKMAN_TEL",DataType.String);
			configCondition("M.CUST_LEVEL","=","CUST_LEVEL",DataType.String);
			configCondition("M.RISK_LEVEL","=","RISK_LEVEL",DataType.String);
			configCondition("M.CREDIT_LEVEL","=","CREDIT_LEVEL",DataType.String);
			configCondition("M.TOTAL_DEBT",">=","TOTAL_DEBT",DataType.Number);
			configCondition("m.MGR_NAME","=","MGR_NAME",DataType.String);
			configCondition("M.FAXTRADE_NOREC_NUM","=","FAXTRADE_NOREC_NUM",DataType.Number);
			configCondition("M.CURRENT_AUM",">=","CURRENT_AUM",DataType.Number);
			configCondition("M.MGR_ID1","=","MGR_ID1",DataType.String);
			configCondition("M.BELONG_TEAM_HEAD_NAME","=","BELONG_TEAM_HEAD_NAME",DataType.String);
			configCondition("M.MGR_NAME","=","MGR_NAME",DataType.String);
			configCondition("M.CORE_NO","=","CORE_NO",DataType.String);
			configCondition("M.FXQ_RISK_LEVEL","=","FXQ_RISK_LEVEL",DataType.String);
			configCondition("M.POTENTIAL_FLAG","=","POTENTIAL_FLAG",DataType.String);//是否潜在客户
			configCondition("M.LOAN_CUST_STAT","=","LOAN_CUST_STAT",DataType.String);//信贷客户状态
			configCondition("M.LOAN_CUST_ID","like","LOAN_CUST_ID",DataType.String);//信贷客户号
	}
	
	
	
//	public void  doNameCheck(){
//		ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		String custName = request.getParameter("cusName").trim();
//		String type ="";
//		boolean flag = checkCustName(custName);//返回true,代表该客户已存在		
//		if(flag){
//			boolean PotenFlag = checkPotentialCust(custName);//判断是否是潜在客户 ,返回true代表是潜在客户
//			if(PotenFlag){
//				type="2";
//			}else{
//				type="1";}	
//		}else{
//			type="3";//客户不存在，可以新增
//		}
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("type", type);
//		this.setJson(map);
//	}
//	/**
//	 * 与正式客户做匹配
//	 * @param custName
//	 * @return
//	 */	
//	public boolean checkCustName(String custName){
//		boolean flag = false;
//		try{
//			if(custName!=null && !"".equals(custName)){
//				String sql ="SELECT CUST_ID FROM ACRM_F_CI_CUSTOMER WHERE CUST_NAME='"+custName+"'";
//				Connection  connection = ds.getConnection();
//				Statement stmt = connection.createStatement();
//				ResultSet result = stmt.executeQuery(sql);
//				List<String> List = new ArrayList<String>();
//				String custId="";
//			    while (result.next()){
//			    	custId = result.getString(1);
//			    	List.add(custId);
//			    }
//			    if(List!= null && List.size()>0){
//			    	flag = true;
//			    }
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	/**
//	 * 与潜在客户做匹配
//	 * @param custName
//	 * @return
//	 */	
//	public boolean checkPotentialCust(String custName){
//		boolean flag = false;
//		try{
//			if(custName!=null && !"".equals(custName)){
//				if(custName.length()>=6){
//					custName=custName.substring(0, 6);
//					String sql="SELECT POTENTIAL_FLAG FROM ACRM_F_CI_CUSTOMER WHERE CUS_NAME ='"+custName+"'";
//					Connection  connection = ds.getConnection();
//					Statement stmt = connection.createStatement();
//					ResultSet result = stmt.executeQuery(sql);
//					String potentialFlag="";
//				    while (result.next()){
//				    	potentialFlag = result.getString(1);	
//				    }
//				    if( potentialFlag.equals("1")){
//				    	flag = true;
//				    }
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		
//		return flag;
//	}

	public List<Object> validationNewLatentCustomer(String sql, int clom) {
		log.info("" + sql);
		List<Object> List = new ArrayList<Object>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			result = stmt.executeQuery(sql);
			String custId = "";
			String potentialFlag = "";// 潜在客户标识
			String coreNo = "";// 核心客户号
			while (result.next()) {
				if (clom == 1 || clom == 2) {
					custId = result.getString(1);
					potentialFlag = result.getString(2);
					coreNo = result.getString(3);
					List.add(custId + ";" + potentialFlag + ";" + coreNo);
				}
				if (clom == 3) {
					custId = result.getString(1);
					List.add(custId);
				} else {
					custId = result.getString(1);
					List.add(custId);
					if (clom == 8) {
						List.add(result.getString(2));
						List.add(result.getString(3));
						List.add(result.getString(4));
					}
				}
			}
			log.info("validationNewLatentCustomer: " + List.toString());
			return List;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(result, stmt, connection);
		}
		return null;
	}
	/**
	 * 验证是否已存在该客户:
	 * 验证两个条件：1、是否重名
	 * 2、该证件类型与号码是否已存在（必须保证一个企业只能用一个证件开户）
	 * 
	 */
	public void doParameterCheck(){
	   	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String type = "",type1 = "";
		try {
			log.info("保存客户信息之前验证是否已存在该客户");
			String custName = request.getParameter("custName")==null?"":request.getParameter("custName");
			String identType = request.getParameter("identType")==null?"":request.getParameter("identType");
			String identNo = request.getParameter("identNo")==null?"":request.getParameter("identNo");
			String custid = request.getParameter("custid")==null?"":request.getParameter("custid");
			
			List<Object> list = null,list1,list2,list4=null;
			boolean  flag1 =false;  //名称相同时为潜在客户
			boolean	 flag2 =false;  //证件相同时为潜在客户
			boolean  flag3=false;    //修改时名称是否已有其他相同客户名的客户，是为true
			boolean  flag5=false;	//修改时证件是否已有其他相同证件号的客户，是为true
			boolean flag4=false;//已有潜在客户，提示客户经理标志
			if(custName!=null&&!"".equals(custName)&&identType!=null&&!"".equals(identType)&&identNo!=null&&!"".equals(identNo)){
				log.info("开始检查同名客户......");
				// 1、取同名的客户编号、潜在客户标识、核心客户号
				String sql1 = "SELECT C.CUST_ID,C.POTENTIAL_FLAG,C.CORE_NO FROM ACRM_F_CI_CUSTOMER  C WHERE C.CUST_NAME='" + custName + "' AND C.CUST_TYPE='1'";
				list1 = validationNewLatentCustomer(sql1, 1);
				if (list1 != null && list1.size() > 0) {// 客户名称已存在 true
					log.info(String.format("发现同名客户[%d]条,继续校验客户号和潜在客户标志", list1.size()));
					for (int i = 0; i < list1.size(); i++) {
						String id[] = list1.get(0).toString().split(";");
						if (!custid.equals(id[0])) {
							flag3 = true; // 不同的客户号
							if (id[1].equals("0")) {
								type = "1";// 既有客户
								log.info("该同名客户为既有客户，且客户号与当前客户不相同");
							}
							if (id[1].equals("1") && (id[2].equals("null") || id[2].equals(""))) {
								log.info("该同名客户为潜在客户，且客户号与当前客户不相同");
								flag1 = true; // 已有潜在客户
								type = "2";
							}
						}
					}
				}
				log.info("开始检查证件类型和证件号相同的客户......");
				// 2、取同证件的客户编号、潜在客户标识、核心客户号
				String  sql2="SELECT C.CUST_ID ,C.POTENTIAL_FLAG,C.CORE_NO FROM ACRM_F_CI_CUSTOMER  C WHERE C.IDENT_TYPE='"+identType+"' AND C.IDENT_NO='"+identNo+"'";
				list2=validationNewLatentCustomer(sql2,2);//存在与法金客户匹配证件类型和号码相同 true
				if (list2 != null && list2.size() > 0) {
					log.info(String.format("发现同证件类型和证件号客户[%d]条,继续校验客户号和潜在客户标志", list1.size()));
					for (int i = 0; i < list2.size(); i++) {
						String id[] = list2.get(0).toString().split(";");
						if (!custid.equals(id[0])) {
							flag5 = true;
							if (id[1].equals("0")) {
								type = "1";// 既有客户
								log.info("该同证件客户为既有客户，且客户号与当前客户不相同");
							}
							if (id[1].equals("1") && (id[2].equals("null") || id[2].equals(""))) {
								log.info("该同证件客户为潜在客户，且客户号与当前客户不相同");
								flag2 = true; // 已有潜在客户
								type = "2";
							}
						}
					}
				}
			}
			if (!(flag3 || flag5)) {// 可以修改或新增
				type = "4";
			}
            //如果潜在客户已存在，查出其客户经理，返回前台
			if(type!="4"){
				String  sql4="SELECT AC.CUST_ID,AC.CUST_NAME,OC.MGR_ID,OC.MGR_NAME FROM ACRM_F_CI_CUSTOMER  AC LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR OC ON AC.CUST_ID=OC.CUST_ID WHERE 1=1";
				if (custid != null && !custid.equals("")) {
					sql4 = sql4 + " AND AC.CUST_ID NOT IN('" + custid + "')";
				}
				if (flag3 && !flag5) {
					sql4 = sql4 + " AND AC.CUST_NAME='" + custName + "' ";
					type1 = "5";
				}
				if (flag5 && !flag3) {
					sql4 = sql4 + " AND AC.IDENT_TYPE='" + identType + "' AND AC.IDENT_NO='" + identNo + "'";
					type1 = "6";
				}
				if (flag3 && flag5) {
					sql4 = sql4 + " AND AC.CUST_NAME='" + custName + "'";// 当证件与名称都有重复的且不为当前客户时，报客户名称重复
					type1 = "5";
					if (!flag1) {
						type = "1";
					} else {
						type = "2";
					}
				}
				
				list4=validationNewLatentCustomer(sql4,8);
				if(list4!=null&&list4.size()>0){
					flag4=true;
				}
			}				
			
			if(flag4==true){
				list=list4;
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("type1", type1);
			if(list!=null&&list.size()>0){
				map.put("custId", (list).get(0));   //潜在客户id
				map.put("userName",(list).get(1));
				map.put("mgrId",list.get(2));
				map.put("mgrName",list.get(3));
			}
			log.info("doParameterCheck  type: "+type);
			this.setJson(map);
		} catch (Exception e) {
			throw new BizException(1,0,"10001",":"+e.getMessage());
			//e.printStackTrace();
		}
	}	
	/**
	 * 中征码校验是否重复
	 */
	public void doCodeCheck() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String code = request.getParameter("code").trim();
		String custId = request.getParameter("custId").trim();
		List<Object> list = null;
		String allow = "1";// 不通过
		try {
			// StringBuilder sb = new StringBuilder(" SELECT COUNT(CUST_ID) AS COUNTCODE  FROM ACRM_F_CI_ORG T "+
			// " WHERE T.LOAN_CARD_NO='"+code+"' ");
			// sb.append( "--填写sql");
			// String querySql = sb.toString();
			// this.json = new QueryHelper(querySql, ds.getConnection()).getJSON();
			if (code != null && !"".equals(code)) {
				String sb1 = " SELECT CUST_ID  FROM ACRM_F_CI_ORG T WHERE T.LOAN_CARD_NO='" + code + "' ";
				list = validationNewLatentCustomer(sb1, 3);
				if (list != null && list.size() > 0) {// 中证码已存在 true
					if (custId.equals(list.get(0))) {// 为同一客户编号
						allow = "2";// 通过校验
					} else {
						allow = "1";// 不通过
					}
				} else {// 中证码不存在
					allow = "2";// 通过校验
				}
			} else {
				allow = "2";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("allow", allow);
			log.info("doCodeCheck  allow: " + allow);
			this.setJson(map);
		} catch (Exception e) {
			throw new BizException(1, 0, "10001", ":" + e.getMessage());
		}
	}
	/**
	 * 新增潜在客户
	 */
	public void create1() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		log.info("新增潜在客户信息");
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");// 客户名称
		String identCustName = request.getParameter("custName") == null ? "" : request.getParameter("custName");// 客户名称
		String identType = request.getParameter("identType") == null ? "" : request.getParameter("identType");// 证件类型
		String identNo = request.getParameter("identNo") == null ? "" : request.getParameter("identNo");// 证件号码
		String custType = request.getParameter("custType") == null ? "" : request.getParameter("custType");// 客户类型
		String firstjson = request.getParameter("firstjson") == null ? "" : request.getParameter("firstjson");// 第一页json
		String secondjson = request.getParameter("secondjson") == null ? "" : request.getParameter("secondjson");// 第二页json
		String checkStr = CustomerManagerNewService.save(custId, identCustName, identType, identNo, custType, firstjson, secondjson);
		log.info("checkStr:" + checkStr);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custId", checkStr); // 潜在客户id
		this.setJson(map);
	}
    
//    public static void main(String args[]){
//    	String json = "[{'CUST_ID':'','CORE_NO':'','LOAN_CUST_ID':'','LOAN_CUST_STAT':'','POTENTIAL_FLAG':'1','CUST_TYPE':'1','SHORT_NAME':'','CUST_NAME':'sj56','IDENT_TYPE':'20','IDENT_NO2':'','ORG_CUST_TYPE':'','LOAN_ORG_TYPE':'','BUSI_LIC_NO':'','SW_REGIS_CODE':'','AREA_REG_CODE':'','BELONG_GROUP':'','EN_NAME':'','IDENT_NO':'34567876-4','IDENT_END_DATE':'','FLAG_CAP_DTL':'','CREDIT_CODE':'','ACC_OPEN_LICENSE':'','NATION_REG_CODE':'','ORG1_CUST_ID':'','LEGAL_LINKMAN_ID':'','BUSIINFO_CUST_ID':'','REGISTER_CUST_ID':'','ADDR_ID0':'','ADDR_ID1':'','IDENT_ID':'','MEMBER_ID':'','OBU_ID':'','OPEN_ID':'','REGIS_ID':'','NATION_REG_ID':'','AREA_REG_ID':'','MGR_KEY_ID':'','BRANCH_ID':'','NATION_CODE':'CHN','HQ_NATION_CODE':'','IN_CLL_TYPE':'','EMPLOYEE_SCALE':'','RISK_NATION_CODE':'','INVEST_TYPE':'','AREA_CODE':'','STAFFIN':'','ENT_PROPERTY':'','REGISTER_TYPE':'','COM_HOLD_TYPE':'','ENT_SCALE':'','ENT_SCALE_CK':'','LEGAL_REPR_NAME':'','LEGAL_REPR_IDENT_NO':'','REG_CODE_TYPE':'','REGISTER_DATE':'','END_DATE':'','REGISTER_AREA':'','REGISTER_CAPITAL_CURR':'','MAIN_BUSINESS':'','SWIFT':'','LEGAL_REPR_IDENT_TYPE':'','LEGAL_IDENT_EXPIRED_DATE':'','REGISTER_NO':'','BUILD_DATE':'','REGISTER_ADDR':'','ADDR0':'','REGISTER_CAPITAL':'','MINOR_BUSINESS':'','ANNUAL_INCOME':'','SALE_CCY':'','TOTAL_ASSETS':'','SALE_AMT':'','CREATE_DATE':'','MGR_ID':'','LAST_UPDATE_SYS':'','CREATE_BRANCH_NO':'','CREATE_TIME_LN':'','FIRST_LOAN_DATE':'','LAST_UPDATE_TM':'','LOAN_CARD_NO':'','REMARK':'','GROUP_NO':'','IN_CLL_TYPE_ID':'','REGISTER_TYPE_ID':'','REGISTER_AREA_ID':'','MGR_ID1':'','UNITID':''}]";
//    	JSONArray json1 = JSONArray.fromObject(json);
//    	net.sf.json.JSONObject jsonObject = json1.getJSONObject(0);
//    	System.out.println(jsonObject.getString("POTENTIAL_FLAG"));
//    }
}
