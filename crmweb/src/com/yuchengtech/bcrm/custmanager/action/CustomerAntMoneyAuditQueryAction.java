package com.yuchengtech.bcrm.custmanager.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiDqshInfo;
import com.yuchengtech.bcrm.custmanager.service.CustomerAntMoneyAuditQueryService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 
 * @ClassName: CustomerAntMoneyAuditQueryAction
 * @Description: 高/中/低风险反洗钱客户查询Action
 *    客户查询去除数据权限的代码控制，改为由后台数据过滤器配置 by :sujm 20140821
 * @author luyy
 * @date 2014-7-17  
 *
 */
@SuppressWarnings("serial")
@Action("/customerAntMoneyAuditQuery")
public class CustomerAntMoneyAuditQueryAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
	@Qualifier("customerAntMoneyAuditQueryService")
	private CustomerAntMoneyAuditQueryService customerAntMoneyAuditQueryService;
	
	//private HttpServletRequest request;//接受浏览器发出的请求
	
	private static Logger log = Logger.getLogger(CustomerAntMoneyAuditQueryAction.class);

	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init(){
		model=new AcrmAAntiDqshInfo();
		setCommonService(customerAntMoneyAuditQueryService);
	}
	/**
	 * 低风险反洗钱客户信息查询
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String riskLevel = request.getParameter("riskLevel");
		String check_status = request.getParameter("CHECK_STATUS");
		String permission = request.getParameter("permission");
		//获取审核状态   
		/*ConditionManager conditionManager = new ConditionManager(this.getJson());
    	conditionManager.addItem("dq.check_status", "=", "CHECK_STATUS", DataType.String);
    	if (conditionManager.getWhereSQL().length() > 0) {
    		StringBuffer strb= conditionManager.getWhereSQL(); //拼接出一个SQL 
    		check_status=strb.substring(strb.indexOf("'")+1,strb.indexOf("'")+2); //截取出状态
    	}
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		String month = sdf.format(new Date());
		int year = Integer.valueOf((sdf2.format(new Date())));

		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT  C.CUST_ID,");//客户号
		sb.append(" C.CORE_NO,");//核心客户号
		sb.append(" C.CUST_NAME,");//客户名称
		sb.append(" C.CUST_TYPE,");//客户类型   
		sb.append(" G.CUST_GRADE,");//客户反洗钱等级
		sb.append(" nvl(dq.check_status,0) check_status,");
		//sb.append(" dq.check_status,");
		if(riskLevel != null){
			if("H".equals(riskLevel)){
				sb.append(" to_char(LAST_DAY(sysdate),'yyyy-mm-dd') AUDIT_END_DATE ");					
			}else if("M".equals(riskLevel)){
				sb.append(" to_char(LAST_DAY(sysdate),'yyyy-mm-dd') AUDIT_END_DATE ");				
			}else if("L".equals(riskLevel)){
				sb.append(" add_months(trunc(sysdate,'yyyy'),12)-1 AUDIT_END_DATE ");
			}
		}
	
		//审核截止日期
		sb.append(" from ACRM_F_CI_CUSTOMER C");//客户主表
		sb.append(" LEFT JOIN (SELECT * FROM  ACRM_F_CI_GRADE WHERE  cust_grade_type = '01' ) G");//客户等级表
		sb.append(" ON G.CUST_ID = C.CUST_ID");
		sb.append(" left join (SELECT * FROM ACRM_F_CI_GRADE_dq WHERE cust_grade_type = '01') dq ");
		sb.append(" on dq.CUST_ID = C.CUST_ID");
		if("H".equals(riskLevel)){
			sb.append(" and dq.CHECK_RQ='"+year+month+"01'");
		}else if("M".equals(riskLevel)){
			sb.append(" and dq.CHECK_RQ='"+year+"0901'");
		}else if("L".equals(riskLevel)){
			sb.append(" and dq.CHECK_RQ='"+year+"0901'");
		}
		sb.append(" inner join acrm_a_fact_fxq_customer fc on fc.CUST_ID = C.CUST_ID " );//判断客户是否是新老客户
		sb.append(" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID "); //2016/1/22 关联所属客户表 
		if(null!=riskLevel){
			sb.append(" where 1 = 1 AND G.CUST_GRADE = '"+riskLevel+"'  ");//审核状态不为已审核，或者为空
		}else{
			sb.append(" where 1 = 2 AND G.CUST_GRADE = '"+riskLevel+"'  ");
		}*/
		/*//当前用户的所有客户
		sb.append(
		" AND ((M.MGR_ID IN "+
	       " (SELECT A1.ACCOUNT_NAME "+
	           " FROM ADMIN_AUTH_ACCOUNT A1 "+
	         "  WHERE A1.ACCOUNT_NAME = '"+auth.getUserId()+"' "+
	             " OR A1.BELONG_TEAM_HEAD = '"+auth.getUserId()+"'))) "
		);*/
		/*if("0".equals(check_status)){
			sb.append(" and dq.check_status is null ");
		}else if("1".equals(check_status)){
			sb.append(" and dq.check_status ='1' ");
		}else if("2".equals(check_status))
		{
			sb.append(" and dq.check_status ='2' ");
		}*/
		
		/*	//开启定期审核 上线启用
		 * if(riskLevel != null){
			//高风险，3、9月份执行查询
			if("L".equals(riskLevel)){
				if(month.equals("09") && year%2 == 1){
					sb.append(" and 1=1 ");						
				}else{
					sb.append(" and 1=2  ");					
				}				
			}else if("M".equals(riskLevel)){
				if(month.equals("09")){
					sb.append(" and 1=1 ");					
				}else{
					sb.append(" and 1=2 ");
				}				
			}else if("H".equals(riskLevel)){
				if(month.equals("03") || month.equals("09")){
					sb.append(" and 1=1 ");
				}else{
					sb.append(" and 1=2 ");	
				}
			}
		}
		*/
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT C.CUST_ID, ")
		.append("C.CORE_NO, ")
		.append("C.CUST_NAME, ")
		.append("C.CUST_TYPE, ")
		.append("G.CUST_GRADE, ")
		.append("NVL(DQ.CHECK_STATUS, 0) CHECK_STATUS, ")
		.append("ADD_MONTHS(TRUNC(SYSDATE, 'yyyy'), 12) - 1 AUDIT_END_DATE ")
		.append("FROM ACRM_F_CI_CUSTOMER C ")
		.append("LEFT JOIN ACRM_F_CI_GRADE G ON G.CUST_ID = C.CUST_ID AND G.CUST_GRADE_TYPE = '01' ")
		.append("LEFT JOIN (SELECT ROW_NUMBER() OVER(PARTITION BY T.CUST_ID ORDER BY T.CHECK_START_DATE DESC) RN, ")
		.append("	T.CUST_ID, ")
		.append("	T.CHECK_STATUS ")
		.append(" 	FROM ACRM_F_CI_GRADE_DQ T ")
		.append("	WHERE T.CUST_GRADE_TYPE = '01') DQ ON DQ.CUST_ID = C.CUST_ID AND DQ.RN = 1 ")
		.append("INNER JOIN ACRM_A_FACT_FXQ_CUSTOMER FC ON FC.CUST_ID = C.CUST_ID ")
		.append("LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID ")
		.append("WHERE 1 = 1 ")
		.append("AND G.CUST_GRADE = 'L' ")
		.append("AND NVL(DQ.CHECK_STATUS, '0') <> '2' ")
		.append(" ");
		if(permission!=null&&permission.trim().equals("false")){
			log.info("反洗钱客户审核权限未开放，设置SQL条件1=2，限制查询");
			sb.append(" and 1=2");
		}
		SQL=sb.toString();//赋值给sql
		datasource=  ds;//赋值数据源
		setPrimaryKey("C.CUST_ID desc ");//排序
		configCondition("C.CUST_ID","like","CUST_ID",DataType.String);
		configCondition("C.CORE_NO","=","CORE_NO",DataType.String);
		configCondition("C.CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("C.CUST_TYPE","=","CUST_TYPE",DataType.String);
		configCondition("G.CUST_GRADE","=","FXQ_RISK_LEVEL",DataType.String);
		configCondition("DQ.CHECK_STATUS", "=", "CHECK_STATUS", DataType.String);
		/*for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("ACCT_NO".equals(key)){
					sb.append(" and c.cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
				}
			}
		}*/
		
	}
	/**
	 * 反洗钱获取客户详细信息
	 * @return
	 */
	public String getFXQCustInfo(){
	 //public String getIfAppl(){
	    	ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			
			String CUST_GRADE = request.getParameter("CUST_GRADE");
			String CUST_ID = request.getParameter("CUST_ID");
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
			String month = sdf.format(new Date());
			int year = Integer.valueOf((sdf2.format(new Date())));

			StringBuffer sb = new StringBuffer("");
			
			sb.append("SELECT  C.CUST_ID,");//客户号
			sb.append("C.CORE_NO," +		//--核心客户号
					"C.CUST_NAME, " +		//--客户名称
					"C.CUST_TYPE," +	//--客户类型
					"t9.ident_value IDENT_TYPE1," +// --证件类型1
					"t9.ident_no INDENT_NO1," +// --证件号1
					"t9.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1," +//--证件1到期日
					" t10.ident_value IDENT_TYPE2," +//--证件类型2
					" t10.ident_no INDENT_NO2," +//--证件号2
					"t10.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2," +//--证件2到期日
					" C.CREATE_DATE," +//--客户创建日期(cb) 
					" C.CUST_STAT," +//--客户状态 
					" M.INSTITUTION_NAME AS ORG_NAME," +//--所属机构名称
					" M.MGR_NAME," +//--归属客户经理名称 
					"T.BELONG_TEAM_HEAD_NAME," +//--归属team head名称（法金） 
					"P.CITIZENSHIP," +//--国籍 
					"P.CAREER_TYPE," +//--职业 
					"P.BIRTHDAY," +// --出生日期 
					" p.if_org_sub_type as if_org_sub_type_per, " +//--是否自贸区(对私)
					" O.BUILD_DATE," +//--成立日期 
					"O.NATION_CODE," +//--国家或地区代码
					"O.IF_ORG_SUB_TYPE AS if_org_sub_type_ORG," +//--是否自贸区(对公) 
					"FUN_CHANGE_CODE(o.In_Cll_Type,'FXQ020') In_Cll_Type, " +//--行业分类
					"" +
					"CASE " +
	                "WHEN O.ENT_SCALE_CK IS NULL THEN " +
	                 "nvl(trim(O.ENT_SCALE_RH), 'CS04') " + //--企业规模（人行） 
	                "ELSE "+
	                 "NVL(trim(O.ENT_SCALE_CK), 'CS04') " + //--企业规模（存款） 
	                 "END  AS ENT_SCALE_CK," +//--企业规模
					" AGE.AGENT_NAME," +//--代理人姓名
					"AGE.AGENT_NATION_CODE," +//--代理人国籍 
					" AGE.IDENT_TYPE AGE_IDENT_TYPE," +// --代理人证件类型
					"");//
			sb.append(" AGE.IDENT_NO AGE_IDENT_NO,");//代理人证件号码 
			sb.append(" AGE.TEL, ");//客户是否为黑名单客户
			sb.append(" OCT.FLAG_AGENT,");//客户是否为代理开户
			//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
			//sb.append(" FXQ.FXQ006,");//客户是否为代理开户
			
			sb.append(" FXQ.FXQ007,");//客户办理的业务(对私)
			sb.append(" FXQ.FXQ008,");//是否涉及风险提示信息或权威媒体报道信息
			sb.append(" FXQ.FXQ009,");//客户或其亲属、关系密切人等是否属于外国政要
			
			
			
			sb.append(" FXQ.FXQ010,");//用于保存数据
			sb.append(" FXQ.FXQ011,");//用于保存数据
			sb.append(" FXQ.FXQ012,");//用于保存数据
			sb.append(" FXQ.FXQ013,");//用于保存数据
			sb.append(" FXQ.FXQ014,");//用于保存数据
			sb.append(" FXQ.FXQ015,");//用于保存数据
			sb.append(" FXQ.FXQ016,");//用于保存数据
			sb.append(" FXQ.FXQCODE,");//用于保存数据
			
			
			
			
			
			sb.append(" FXQ.FXQ021,");//与客户建立业务关系的渠道
			sb.append(" FXQ.FXQ022,");//是否在规范证券市场上市
			sb.append(" FXQ.FXQ023,");//客户的股权或控制权结构
			sb.append(" FXQ.FXQ024,");//客户是否存在隐名股东或匿名股东
			sb.append(" FXQ.FXQ025,");//客户办理的业务(对公) 
			sb.append(" DQSH.DQSH001,");//证件是否过期
			sb.append(" DQSH.DQSH002,");//客户是否可取得联系
			sb.append(" DQSH.DQSH003,");//联系时间 
			sb.append(" DQSH.DQSH004,");//联系人与帐户持有人的关系 
			sb.append(" DQSH.DQSH037,");//联系人与帐户持有人的关系说明
			sb.append(" DQSH.DQSH005,");//预计证件更新时间
			sb.append(" DQSH.DQSH006,");//未及时更新证件的理由
			sb.append(" DQSH.DQSH007,");//客户是否无正当理由拒绝更新证件
			sb.append(" DQSH.DQSH008,");//客户留存的证件及信息是否存在疑点或矛盾
			sb.append(" DQSH.DQSH009,");//账户是否频繁发生大额现金交易
			sb.append(" DQSH.DQSH010,");//账户是否频繁发生外币现钞存取业务
			sb.append(" DQSH.DQSH011,");//账户现金交易是否与客户职业特性相符
			sb.append(" DQSH.DQSH012,");//账户是否频繁发生大额的网上银行交易 
			sb.append(" DQSH.DQSH013,");//账户是否与公司账户之间发生频繁或大额的交易
			sb.append(" DQSH.DQSH014,");//账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符 
			sb.append(" DQSH.DQSH015,");//账户资金是否快进快出，不留余额或少留余额
			sb.append(" DQSH.DQSH016,");//账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
			sb.append(" DQSH.DQSH017,");//账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
			sb.append(" DQSH.DQSH018,");//账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付 
			sb.append(" DQSH.DQSH019,");//账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
			sb.append(" DQSH.DQSH020,");//账户是否频繁发生跨境交易，且金额大于1万美元
			sb.append(" DQSH.DQSH021,");//账户是否经常由他人代为办理业务
			sb.append(" DQSH.DQSH022,");//客户是否提前偿还贷款，且与其财务状况明显不符
			sb.append(" DQSH.DQSH023,");//当前账户状态是否正常
			sb.append(" C.CURRENT_AUM,");//AUM(人民币)(20160318新增)
			sb.append(" DQSH.DQSH024,");//AUM(人民币)改为  客户是否涉及反洗钱黑名单
			sb.append(" DQSH.DQSH025,");//企业证件是否过期 
			sb.append(" DQSH.DQSH026,");//法定代表人证件是否过期 
			sb.append(" DQSH.DQSH027,");//联系人证件是否过期 
			sb.append(" DQSH.DQSH028,");//联系人的身份 
			sb.append(" DQSH.DQSH038,");//联系人的身份说明 ----------------新增 
			sb.append(" DQSH.DQSH029,");//账户是否与自然人账户之间发生频繁或大额的交易
			sb.append(" DQSH.DQSH030,");//账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符
			sb.append(" DQSH.DQSH031,");//账户是否频繁收取与其经营业务明显无关的汇款
			sb.append(" DQSH.DQSH032,");//账户资金交易频度、金额是否与其经营背景相符
			sb.append(" DQSH.DQSH033,");//账户交易对手及资金用途是否与其经营背景相符
			sb.append(" DQSH.DQSH034,");//账户是否与关联企业之间频繁发生大额交易
			sb.append(" DQSH.DQSH035,");//客户行为是否存在异常
			sb.append(" DQSH.DQSH036,");//账户交易是否存在异常
			sb.append(" G.CUST_GRADE,");//客户反洗钱等级 
			sb.append(" GDQ.INSTANCEID,");// 审批流程ID
			sb.append(" GDQ.INSTRUCTION,");// 审核结果说明		
			sb.append(" GDQ.CUST_GRADE_CHECK,");//定期审核等级	
			sb.append(" GDQ.GRADE_ID,");//审核结果说明表ID
			sb.append(" fc.FLAG");// 3为新客户
			//sb.append("	to_char(LAST_DAY(sysdate), 'yyyy-mm-dd') AUDIT_END_DATE");//审核截止日期
			/*if(riskLevel != null){
				if(riskLevel.equals("H")){
					sb.append(" add_months(trunc(sysdate,'yyyy'),12)-1 AUDIT_END_DATE ");					
				}else if(riskLevel.equals("M")){
					sb.append(" to_char(LAST_DAY(sysdate),'yyyy-mm-dd') AUDIT_END_DATE ");				
				}else if(riskLevel.equals("H")){
					sb.append(" to_char(LAST_DAY(sysdate),'yyyy-mm-dd') AUDIT_END_DATE ");
				}
			}*/	
			//from
			sb.append(" from ACRM_F_CI_CUSTOMER C");//----客户主表
			sb.append(" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M");//--客户经理归属表
			sb.append(" ON M.CUST_ID = C.CUST_ID");
			sb.append(" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T");//--客户经理信息表
			sb.append(" ON T.CUST_MANAGER_ID = M.MGR_ID");//
			sb.append(" LEFT JOIN ACRM_F_CI_PERSON P ");//对私个人主表
			sb.append(" ON P.CUST_ID = C.CUST_ID");//
			sb.append(" LEFT JOIN ACRM_F_CI_ORG O");//对公企业主表
			sb.append(" ON O.CUST_ID = C.CUST_ID");//
			sb.append(" left join (select t.cust_id,");//
			sb.append(" t.IDENT_no,");//
			sb.append(" t.IDENT_EXPIRED_DATE,");//
			sb.append(" t1.f_value ident_value");//sb.append(" ");//
			sb.append(" from acrm_f_ci_cust_identifier t");//--证件表
			sb.append(" left join (select *");//
			sb.append(" from ocrm_sys_lookup_item");//字典表
			sb.append(" where f_lookup_id = 'XD000040') t1");//
			sb.append(" on t.IDENT_TYPE = t1.F_CODE");//
			sb.append(" where IS_OPEN_ACC_IDENT = 'Y') t9");//
			sb.append(" on C.cust_id = t9.cust_id");//
			sb.append(" left join (select t.cust_id,");//
			sb.append(" t.IDENT_no,");//
			sb.append(" t.IDENT_EXPIRED_DATE,");//
			sb.append(" t1.f_value ident_value");//
			sb.append(" from acrm_f_ci_cust_identifier t");//证件表
			sb.append(" left join (select *");//  
			sb.append(" from ocrm_sys_lookup_item");//--字典表
			sb.append(" where f_lookup_id = 'XD000040') t1");//
			sb.append(" on t.IDENT_TYPE = t1.F_CODE");//
			sb.append(" where (IS_OPEN_ACC_IDENT <> 'Y' OR");//
			sb.append(" IS_OPEN_ACC_IDENT IS NULL)");//
			sb.append(" AND ident_type NOT IN ('V', '15X', 'W', 'Y')) t10");//
			sb.append(" on C.cust_id = t10.cust_id");//
			sb.append(" left join ACRM_F_SYS_CUST_FXQ_INDEX FXQ");//反洗钱前台录入指标表
			sb.append(" on FXQ.CUST_ID = C.CUST_ID");//
			sb.append(" LEFT JOIN ACRM_F_CI_AGENTINFO AGE");//代理人信息表
			sb.append(" ON AGE.CUST_ID = C.CUST_ID");//
			sb.append(" LEFT JOIN ACRM_A_ANTI_DQSH_INFO DQSH ON C.CUST_ID = DQSH.CUST_ID");//
			sb.append(" LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID ");//客户是否为代理开户 
			
			sb.append(" LEFT JOIN (SELECT * FROM  ACRM_F_CI_GRADE WHERE  cust_grade_type = '01' ) G");//客户等级表
			sb.append(" ON G.CUST_ID = C.CUST_ID");//
			sb.append(" inner join acrm_a_fact_fxq_customer fc on fc.CUST_ID = C.CUST_ID " );//判断客户是否是新老客户
			
			
			
			if("H".equals(CUST_GRADE)){
			sb.append(" LEFT JOIN (select * from ACRM_F_CI_GRADE_DQ d2 where d2.CUST_ID= '"+CUST_ID+"' and d2.check_rq='"+year+month+"01'and  d2.CUST_GRADE_OLD='"+CUST_GRADE+"'  ) GDQ");//客户等级表
			sb.append(" ON GDQ.CUST_ID = C.CUST_ID");//
			}else if("M".equals(CUST_GRADE)){
				sb.append(" LEFT JOIN (select * from ACRM_F_CI_GRADE_DQ d2 where d2.CUST_ID= '"+CUST_ID+"' and d2.check_rq='"+year+"0901'and  d2.CUST_GRADE_OLD='"+CUST_GRADE+"'  ) GDQ");//客户等级表
				sb.append(" ON GDQ.CUST_ID = C.CUST_ID");//
				}else if("L".equals(CUST_GRADE)){
					sb.append(" LEFT JOIN (select * from ACRM_F_CI_GRADE_DQ d2 where d2.CUST_ID= '"+CUST_ID+"' and d2.check_rq='"+year+"0901'and  d2.CUST_GRADE_OLD='"+CUST_GRADE+"'  ) GDQ");//客户等级表
					sb.append(" ON GDQ.CUST_ID = C.CUST_ID");//
					}
			
		
			//select GDQ.* from ACRM_F_CI_GRADE_DQ where grade_id = '210000142704'||'20160105'||'H' GDQ
			
			sb.append(" where 1 = 1");//
			sb.append(" AND C.CUST_ID = '"+CUST_ID+"'");//
			SQL=sb.toString();//赋值给sql
			datasource=  ds;//赋值数据源
			setPrimaryKey("C.CUST_ID desc ");//排序
		
		try{
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			//"客户记录被锁定，操作员"+list.get(0)
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	    }
	
	

  
	
   	
   	
   	
	/***
	 * 提交审批 保存按钮上的 提交审批
	 * Uncaught SyntaxError: missing ) after argument list
	 * @throws Exception
	 */
	public void saveRiskLevel() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
   		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);

		AcrmAAntiDqshInfo acrmAAntiDqshInfo=(AcrmAAntiDqshInfo)model;
		int times = 0;
   		
   		//list办理中流程，list2已办结流程
   		List list = customerAntMoneyAuditQueryService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like '%ANTMONEY%"+acrmAAntiDqshInfo.getCustId()+"%'");
   		List list2 = customerAntMoneyAuditQueryService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORDEND where instanceid like '%ANTMONEY_"+acrmAAntiDqshInfo.getCustId()+"%'");
   		Map<String, Object> map1 = new HashMap<String, Object>();
   		if(list!= null && list.size() > 0){
   			//如果有办理中流程，不让再提交
   			//map1.put("existTask", "existTask");
   			//如果有办理中流程，不让再提交
   	    	response.getWriter().write("{\"existTask\":\"existTask\"}");
   	    	response.getWriter().flush();
   		}else{
   			if(list2!= null && list2.size() > 0){
   				times = list.size();
   			}
   				
   				Map<String, Object> paramMap = new HashMap<String, Object>();
   				List<?> list101 = auth.getRolesInfo();
   				for (Object m : list101) {
   					Map<?, ?> map = (Map<?, ?>) m;// map自m引自list，ROLE_CODE为键, R000为值
   					paramMap.put("role", map.get("ROLE_CODE"));
   				}
   	   			//acrmAAntiDqshInfo.getCustGrade() 为原始风险等级
   				//acrmAAntiDqshInfo.getCustGradeCheck() 审核风险等级
	    		//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息  0是标示，1是用户ID，2是当前风险等级，3是审核风险等级，4 客户类型（流程处理中显示客户信息分辨是个人还是企业） 5.流程发起人ID  6.是审核结束表中的数据总数 cust_type 
	    		String instanceid = "ANTMONEY_"+acrmAAntiDqshInfo.getCustId()+"_"+acrmAAntiDqshInfo.getCustGrade()+"_"+acrmAAntiDqshInfo.getCustGradeCheck()+"_"+acrmAAntiDqshInfo.getCustType()+"_"+auth.getUserId()+"_"+times;
	    		acrmAAntiDqshInfo.setInstanceid(instanceid);
	    		customerAntMoneyAuditQueryService.save(acrmAAntiDqshInfo);
	       		String name =  acrmAAntiDqshInfo.getCustName();
	    		String jobName = "反洗钱风险等级审核_"+name;//自定义流程名称
	    		customerAntMoneyAuditQueryService.initWorkflowByWfidAndInstanceid("131", jobName, paramMap,
	    				instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
	    		response.getWriter().write("{\"instanceid\":\""+instanceid+"\"}");
   	    		response.getWriter().flush();
   	   			
   			
   		}

	}
	
	public void save() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		AcrmAAntiDqshInfo acrmAAntiDqshInfo=(AcrmAAntiDqshInfo)model;
		List list = customerAntMoneyAuditQueryService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like '%ANTMONEY%"+acrmAAntiDqshInfo.getCustId()+"%'");
		if(list!= null && list.size() > 0){
   			//如果有办理中流程，不让修改信息
   			//map1.put("existTask", "existTask");
   			//如果有办理中流程，不让修改信息
   	    	response.getWriter().write("{\"existTask\":\"existTask\"}");
   	    	response.getWriter().flush();
   		}else{	
   			customerAntMoneyAuditQueryService.save(acrmAAntiDqshInfo);
   			response.getWriter().write("{\"existTask\":\"no\"}");
   	    	response.getWriter().flush();
   		}
		
	}
}
