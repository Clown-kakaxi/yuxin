package com.yuchengtech.bcrm.custmanager.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;
/**
 * 
 * @description :反洗钱风险等级审核--流程客户显示页面Action
 *
 * @author : zhaolong
 * @date : 2016-1-18 下午2:32:08
 */
@SuppressWarnings("serial")
@Action("/antMoneyTask")
public class AntMoneyTaskAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void prepare() {
		    	ActionContext ctx = ActionContext.getContext();
				request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
				String CUST_GRADE = request.getParameter("CUST_GRADE");
				String instanceid =request.getParameter("instanceid");
				String CUST_ID = request.getParameter("CUST_ID");
				//0是标示，1是用户ID，2是当前风险等级，3是审核风险等级，4 客户类型（流程处理中显示客户信息分辨是个人还是企业） 5.流程发起人ID  6.是审核结束表中的数据总数
				String[] instanceids =instanceid.split("_");
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
						"O.IF_ORG_SUB_TYPE AS if_org_sub_type_ORG," +//--是否自贸区(对私) 
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
				sb.append(" OCT.FLAG_AGENT, ");
			
				sb.append(" FXQ.FXQ007,");//客户办理的业务(对私)
				sb.append(" FXQ.FXQ008,");//是否涉及风险提示信息或权威媒体报道信息
				sb.append(" FXQ.FXQ009,");//客户或其亲属、关系密切人等是否属于外国政要
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
				sb.append(" decode(G.CUST_GRADE,'H','"+instanceids[2]+"','"+instanceids[2]+"') CUST_GRADE,");//客户反洗钱等级 decode(WFID,'H','D','L') 
				sb.append(" GDQ.INSTANCEID,");// 审批流程ID
				sb.append(" GDQ.INSTRUCTION,");// 审核结果说明		
				sb.append(" decode(GDQ.CUST_GRADE_CHECK,'H','"+instanceids[3]+"','"+instanceids[3]+"') CUST_GRADE_CHECK");//定期审核等级	
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
				sb.append(" LEFT JOIN (SELECT * FROM  ACRM_F_CI_GRADE WHERE  cust_grade_type = '01' ) G");//客户等级表
				sb.append(" ON G.CUST_ID = C.CUST_ID");//
				
				
				sb.append(" LEFT JOIN (select * from ACRM_F_CI_GRADE_DQ where instanceid= '"+instanceid+"'  ) GDQ");//客户等级表
				sb.append(" ON GDQ.CUST_ID = C.CUST_ID ");//
				sb.append(" LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID ");   //客户是否为代理开户 
				sb.append(" where 1 = 1");//
				sb.append(" AND C.CUST_ID = '"+CUST_ID+"'");//
				SQL=sb.toString();//赋值给sql
				datasource=  ds;//赋值数据源
				setPrimaryKey("C.CUST_ID desc ");//排序	    
	}

}
