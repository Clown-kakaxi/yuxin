package com.yuchengtech.bcrm.custmanager.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 
 * @ClassName: CustomerAntMoneyQueryAction
 * @Description: 客户反洗钱查询Action
 *    客户查询去除数据权限的代码控制，改为由后台数据过滤器配置 by :sujm 20140821
 * @author luyy
 * @date 2014-7-17  
 *
 */
@SuppressWarnings("serial")
@Action("customerAntiMoneyQuery")
public class CustomerAntiMoneyQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
 
	public void prepare(){
		StringBuffer sb=new StringBuffer("SELECT DISTINCT  C.CUST_ID,C.CUST_NAME,C.CORE_NO,C.IDENT_TYPE,C.IDENT_NO,C.CUST_TYPE,C.CUST_LEVEL,C.CURRENT_AUM,C.TOTAL_DEBT,C.RISK_LEVEL," +
			" linkman.linkman_name, linkman.mobile LINKMAN_TEL, O.org_biz_cust_type as BELONG_LINE_NO,C.CUST_STAT,M.INSTITUTION_NAME AS ORG_NAME,M.MGR_NAME,GD.CUST_GRADE_TYPE, " +
			" C.CREDIT_LEVEL,C.FAXTRADE_NOREC_NUM,T.BELONG_TEAM_HEAD_NAME,L.BL_NAME," +
			"	GD.CUST_GRADE as FXQ_RISK_LEVEL," +
			"GD.CUST_GRADE as OLD_FXQ_RISK_LEVEL," +
			"GD.LAST_UPDATE_USER," +
			" t9.ident_value IDENT_TYPE1, "+   // --证件类型1  对公对私
			" t9.ident_no INDENT_NO1, "+   //  --证件号1 对公对私对公对私
			" t9.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1, "+   //   --证件1到期日 对公对私
			" t10.ident_value IDENT_TYPE2, "+   // --证件类型2 对公对私
			" t10.ident_no INDENT_NO2, "+   //  --证件号2 对公对私
			" t10.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2, "+   //  --证件2到期日 对公对私
			
			"O.IF_ORG_SUB_TYPE AS if_org_sub_type_ORG," +//--是否自贸区(对公)
			" n.if_org_sub_type as if_org_sub_type_per, " +//--是否自贸区(对私)
			//是否为代理开户更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
			//" IX.FXQ006, "+//客户是否为代理开户
			"OCT.FLAG_AGENT," +//客户是否为代理开户
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
			" IX.FXQ026, "+//客户所在行政区域是否存在严重犯罪
			
			
			" IX.FXQ021, "+//与客户建立业务关系的渠道
			" IX.FXQ022, "+//是否在规范证券市场上市
			" IX.FXQ023, "+//客户的股权或控制权结构
			" IX.FXQ024, "+//客户是否存在隐名股东或匿名股东
			" IX.FXQ025,  "+//客户办理的业务(对公)
			" C.CREATE_DATE, " +
			" fc.FLAG, " +
			" FC.FLAG_FP, "+ //复评状态
			" s.SPECIAL_LIST_TYPE, " + //特殊名单类型
			" s.SPECIAL_LIST_KIND, " + //特殊名单类别
			" s.SPECIAL_LIST_FLAG, " + //特殊名单标志
			" s.ORIGIN, " + //数据来源
			" s.STAT_FLAG, " + //状态标志
			" s.APPROVAL_FLAG, " + //审核标志
			" s.START_DATE, " + //起始日期
			" s.END_DATE, " + //结束日期
			" s.ENTER_REASON, " + //列入原因
			" n.email " +
			
			" FROM ACRM_F_CI_CUSTOMER C LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID " +
			" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T ON M.MGR_ID = T.CUST_MANAGER_ID " +
			" left join acrm_f_ci_org o on o.CUST_ID = C.CUST_ID " +
			" LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type = to_char(L.BL_NO) " +
			" left join OCRM_F_CI_ANTI_CUST_LIST risk on risk.cust_id = c.cust_id " +
			" LEFT JOIN ACRM_F_SYS_CUST_FXQ_INDEX IX ON IX.CUST_ID = C.CUST_ID " + 
			" left join ACRM_F_CI_SPECIALLIST s on s.CUST_ID = C.CUST_ID " +
			" INNER join acrm_a_fact_fxq_customer fc on fc.CUST_ID = C.CUST_ID " +// 反洗钱客户表 保存客户复评
			"left join ( select exe.org_cust_id cust_id,exe.linkman_name,exe.mobile  from   ACRM_F_CI_ORG_EXECUTIVEINFO exe where exe.linkman_type='21' "+
			" union all "+
			" select  plink.cust_id,plink.linkman_name,plink.mobile from  ACRM_F_CI_PER_LINKMAN plink where plink.linkman_type='21') linkman on linkman.cust_id=c.cust_id "+
			" LEFT JOIN (SELECT ee.CUST_ID, ee.CUST_GRADE_TYPE,ee.CUST_GRADE," +
			"   (case when ee.LAST_UPDATE_USER <> 'ETL' then tt.user_name else ee.LAST_UPDATE_USER end ) as LAST_UPDATE_USER FROM ACRM_F_CI_GRADE  ee " +
			"  left join admin_auth_account tt on tt.account_name = ee.LAST_UPDATE_USER " +
			"  WHERE CUST_GRADE_TYPE = '01') GD ON GD.CUST_ID = C.CUST_ID " +
			" left join ACRM_F_CI_PERSON n on c.cust_id = n.cust_id" +
            
			" left join (select t.cust_id, " +//
			" t.IDENT_no, " +//
			" t.IDENT_EXPIRED_DATE, " +//
			" t1.f_value ident_value " +//
			" from acrm_f_ci_cust_identifier t " +//   --证件表
			" left join (select * " +//
			" from ocrm_sys_lookup_item " +//  --字典表
			" where f_lookup_id = 'XD000040') t1 " +//
			" on t.IDENT_TYPE = t1.F_CODE " +//
			" where IS_OPEN_ACC_IDENT = 'Y') t9 " +//
			" on C.cust_id = t9.cust_id " +//
			" left join (select t.cust_id, " +//
			" t.IDENT_no, " +//
			" t.IDENT_EXPIRED_DATE, " +//
			" t1.f_value ident_value " +//
			" from acrm_f_ci_cust_identifier t " +//  --证件表
			" left join (select * " +//
			" from ocrm_sys_lookup_item " +//  --字典表
			" where f_lookup_id = 'XD000040') t1 " +//
			" on t.IDENT_TYPE = t1.F_CODE " +//
			" where (IS_OPEN_ACC_IDENT <> 'Y' OR " +//
			" IS_OPEN_ACC_IDENT IS NULL) " +//
			" AND ident_type NOT IN ('V', '15X', 'W', 'Y')) t10 " +//
			" on C.cust_id = t10.cust_id " +
			"    LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID "+
     
			" WHERE 1=1  ");
		//2016/1/22 关联所属客户表 
		
		
		
		
		
			/*String role="";
			List<?> list101 = auth.getRolesInfo();
			for (Object m : list101) {
				Map<?, ?> map = (Map<?, ?>) m;// map自m引自list，ROLE_CODE为键, R000为值
				String role1= (String) map.get("ROLE_CODE");
				if("R115".equals(role1)){
					role="R115";
					break;
				}
				if("R116".equals(role1)){
					role="R116";
				}
			}
			
			if(!role.equals("R116")&&!role.equals("R115")){
				sb.append(
						" AND ((M.MGR_ID IN "+
					       " (SELECT A1.ACCOUNT_NAME "+
					           " FROM ADMIN_AUTH_ACCOUNT A1 "+
					         "  WHERE A1.ACCOUNT_NAME = '"+auth.getUserId()+"' "+
					             " OR A1.BELONG_TEAM_HEAD = '"+auth.getUserId()+"'))) "
						);
			}
				*/
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("ACCT_NO".equals(key)){
					sb.append(" and c.cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
				}
				if("ORG_NAME".equals(key)){ 
					sb.append(" and (m.INSTITUTION in (SELECT unitid FROM SYS_UNITS  WHERE UNITSEQ LIKE '%"+this.json.get(key)+"%'))");
				}
				if("BL_NAME".equals(key)&&!"归属业务条线".equals(this.json.get(key))){
					sb.append(" and  (o.org_biz_cust_type in (select distinct bl_ID from ACRM_F_CI_BUSI_LINE t START   WITH bl_ID='"+this.json.get(key)+"' CONNECT BY PRIOR BL_ID=PARENT_ID))");
				}
			}
		}
		//添加证件类型与证件号码查询,取证件表上的数据进行查询
		if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))
			&& null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}else if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"')");
		}else if(null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}
		
		SQL=sb.toString();
		datasource=ds;
		
		setPrimaryKey("c.CUST_ID desc ");
		configCondition("c.CUST_ID","like","CUST_ID",DataType.String);
		configCondition("c.CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("c.CUST_TYPE","=","CUST_TYPE",DataType.String);
		configCondition("c.CUST_STAT","=","CUST_STAT",DataType.String);
		configCondition("c.LINKMAN_NAME","like","LINKMAN_NAME",DataType.String);
		configCondition("c.LINKMAN_TEL","like","LINKMAN_TEL",DataType.String);
		configCondition("c.CUST_LEVEL","=","CUST_LEVEL",DataType.String);
		configCondition("c.RISK_LEVEL","=","RISK_LEVEL",DataType.String);
		configCondition("c.CREDIT_LEVEL","=","CREDIT_LEVEL",DataType.String);
		configCondition("c.TOTAL_DEBT",">=","TOTAL_DEBT",DataType.Number);
		configCondition("m.MGR_NAME","=","MGR_NAME",DataType.String);
		configCondition("c.FAXTRADE_NOREC_NUM","=","FAXTRADE_NOREC_NUM",DataType.Number);
		configCondition("c.CURRENT_AUM",">=","CURRENT_AUM",DataType.Number);
		configCondition("t.BELONG_TEAM_HEAD","=","MGR_ID1",DataType.String);
		configCondition("t.BELONG_TEAM_HEAD_NAME","=","BELONG_TEAM_HEAD_NAME",DataType.String);
		configCondition("C.CORE_NO","=","CORE_NO",DataType.String);
		configCondition("GD.CUST_GRADE","=","FXQ_RISK_LEVEL",DataType.String);
	

	}
	
	
	
	/**
	 * 用于反洗钱指标打印
	 * @return
	 */
	public String queryCustFXQIndex(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

	        
	        String custId = request.getParameter("custId");

	        StringBuffer sb=new StringBuffer("SELECT DISTINCT  " +
	                "ai.instruction_content," +  //fxq010 指标说明内容
	                "ai.instr_time," +			//时间	
	                "ai.instr_user," +			//用户
	                "ai.KYJYBG_SBSJ," +			//指标编号
	                "aaa.user_name,"+ //用户名称 
	        		"C.CUST_ID,C.CUST_NAME,C.CORE_NO," +
	        		" grzj.F_VALUE IDENT_TYPE,"+// 证件类型
	        		//"C.IDENT_TYPE," +
	        		"C.IDENT_NO," +
	        		" QY.F_VALUE CUST_TYPE1,"+ //
	
	        		"C.CUST_TYPE," +
	        		"C.CUST_LEVEL,C.CURRENT_AUM,C.TOTAL_DEBT,C.RISK_LEVEL," +
	    			" linkman.linkman_name, linkman.mobile LINKMAN_TEL, O.org_biz_cust_type as BELONG_LINE_NO,C.CUST_STAT,M.INSTITUTION_NAME AS ORG_NAME,M.MGR_NAME,GD.CUST_GRADE_TYPE, " +
	    			" C.CREDIT_LEVEL,C.FAXTRADE_NOREC_NUM,T.BELONG_TEAM_HEAD_NAME,L.BL_NAME," +
	    			"GD.CUST_GRADE as FXQ_RISK_LEVEL," +
	    			"GD.CUST_GRADE as OLD_FXQ_RISK_LEVEL," +
	    			
	    			"GD.LAST_UPDATE_USER," +
	    			"OCT.FLAG_AGENT," +//客户是否为代理开户
	    			//是否为代理开户更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
	    			//" IX.FXQ006, "+//客户是否为代理开户
	    			" IX.FXQ007, "+//客户办理的业务(对私)
	    			" IX.FXQ008, "+//是否涉及风险提示信息或权威媒体报道信息
	    			" IX.FXQ009, "+//客户或其亲属、关系密切人等是否属于外国政要
	    			" FXQ10.F_VALUE FXQ010,"+
	    			//" IX.FXQ010, "+//反洗钱交易监测记录
	    			" IX.FXQ011, "+//是否被列入中国发布或承认的应实施反洗钱监控措施的名单
	    			" IX.FXQ012, "+//是否发生具有异常特征的大额现金交易
	    			" IX.FXQ013, "+//是否发生具有异常特征的非面对面交易
	    			" IX.FXQ014, "+//是否存在多次涉及跨境异常交易报告
	    			" IX.FXQ015, "+//代办业务是否存在异常情况
	    			" IX.FXQ016, "+//是否频繁进行异常交易
	    			" IX.FXQ026, "+//客户所在行政区域是否存在严重犯罪
	    			
	    			" IX.FXQ021, "+//与客户建立业务关系的渠道
	    			" IX.FXQ022, "+//是否在规范证券市场上市
	    			" IX.FXQ023, "+//客户的股权或控制权结构
	    			" IX.FXQ024, "+//客户是否存在隐名股东或匿名股东
	    			" IX.FXQ025,  "+//客户办理的业务(对公)
	    			" C.CREATE_DATE, " +
	    			" fc.FLAG, " +//
	    			" FC.FLAG_FP, "+ //复评状态
	    			
	    			
	    			" s.SPECIAL_LIST_TYPE, " + //特殊名单类型
	    			" s.SPECIAL_LIST_KIND, " + //特殊名单类别
	    			" s.SPECIAL_LIST_FLAG, " + //特殊名单标志
	    			" s.ORIGIN, " + //数据来源
	    			" s.STAT_FLAG, " + //状态标志
	    			" s.APPROVAL_FLAG, " + //审核标志
	    			" s.START_DATE, " + //起始日期
	    			" s.END_DATE, " + //结束日期
	    			" s.ENTER_REASON, " + //列入原因
	    			" n.email " +
	    			" FROM ACRM_F_CI_CUSTOMER C LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID " +
	    			" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T ON M.MGR_ID = T.CUST_MANAGER_ID " +
	    			" left join acrm_f_ci_org o on o.CUST_ID = C.CUST_ID " +
	    			" LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type = to_char(L.BL_NO) " +
	    			" left join OCRM_F_CI_ANTI_CUST_LIST risk on risk.cust_id = c.cust_id " +
	    			" LEFT JOIN ACRM_F_SYS_CUST_FXQ_INDEX IX ON IX.CUST_ID = C.CUST_ID " + 
	    			" left join ACRM_F_CI_SPECIALLIST s on s.CUST_ID = C.CUST_ID " +
	    			" inner join acrm_a_fact_fxq_customer fc on fc.CUST_ID = C.CUST_ID " +//判断客户是否是新老客户
	    			"left join ( select exe.org_cust_id cust_id,exe.linkman_name,exe.mobile  from   ACRM_F_CI_ORG_EXECUTIVEINFO exe where exe.linkman_type='21' "+
	    			" union all "+
	    			" select  plink.cust_id,plink.linkman_name,plink.mobile from  ACRM_F_CI_PER_LINKMAN plink where plink.linkman_type='21') linkman on linkman.cust_id=c.cust_id "+
	    			" LEFT JOIN (SELECT ee.CUST_ID, ee.CUST_GRADE_TYPE,ee.CUST_GRADE," +
	    			"   (case when ee.LAST_UPDATE_USER <> 'ETL' then tt.user_name else ee.LAST_UPDATE_USER end ) as LAST_UPDATE_USER FROM ACRM_F_CI_GRADE  ee " +
	    			"  left join admin_auth_account tt on tt.account_name = ee.LAST_UPDATE_USER " +
	    			"  WHERE CUST_GRADE_TYPE = '01') GD ON GD.CUST_ID = C.CUST_ID " +
	    			" left join ACRM_F_CI_PERSON n on c.cust_id = n.cust_id" +
	    			" left join ACRM_ANTI_INDEX_INSTRUCTION ai"+
	    			"  on c.cust_id=ai.cust_id"+
	    			" left join admin_auth_account aaa on  aaa.account_name = ai.instr_user" +
	    			" left join (select *From ocrm_sys_lookup_item where f_lookup_id ='XD000080') QY on C.CUST_TYPE=QY.F_CODE"+// 客户类型
	    			" left join (select *From ocrm_sys_lookup_item where f_lookup_id ='XD000078') grzj on grzj.F_CODE=C.IDENT_TYPE"+//证件类型
	    			" left join (select *From ocrm_sys_lookup_item where f_lookup_id ='FXQ010') FXQ10 ON FXQ10.F_CODE=IX.FXQ010"+//反洗钱交易监测记录
	    			"    LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID "+//客户是否为代理开户
	        		" WHERE 1=1  ");
	    		sb.append(" AND C.CUST_ID='"+custId+"'");
	   
	    		
	    		
	    		
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection(), null);//查询
			Map<String, Object> result = query.getJSON();
			
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
	
	
	/**
	 * 查看客户反洗钱指标信息
	 * @return
	 */
	public String getFXQIndexInfo(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

	        String custId=request.getParameter("CUST_ID");

	        StringBuffer sb=new StringBuffer(
	        		" SELECT T.CUST_ID,"+
	        		" T.CUST_TYPE ,"+ //客户类型		
	        		" T.CUST_NAME,"+
	        		" T6.MGR_NAME MGR_ID, "+ 		// --客户归属客户经理
	        		" T1.NATION_CODE,"+ 	//   --对公 客户注册地 对公
	        		" T2.CITIZENSHIP,"+ 	//   --对私 国籍  对私
	        		" T2.BIRTHDAY ,"+ 		// --出生日期  对私
	        		" T3.INDUST_TYPE,"+ 	// --行业分类 对公
	        		" T2.CAREER_TYPE,"+ 	// --客户职业 对私
	        		" T3.CREATE_BRANCH_NO ,"+ 	//--开户行
	        		" CASE WHEN T1.ENT_SCALE_CK IS NULL THEN T1.ENT_SCALE_RH"+ 	// 
	        		" ELSE T1.ENT_SCALE_CK END  AS ENT_SCALE_CK,"+ 	//   --企业规模 对公

       				" CASE WHEN T.CUST_TYPE = '1' THEN T3.IDENT_TYPE END IDENT_TYPE_DG,"+  //--客户证件类型/对私
       				" CASE WHEN T.CUST_TYPE = '2' THEN T3.IDENT_TYPE END IDENT_TYPE_DS,"+//--身份证明文件种类 对公
       				"OCT.FLAG_AGENT," +//客户是否为代理开户
       				//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
       				
					//" T4.FXQ006 ,"+ //--客户是否为代理开户  对私
	        		" T4.FXQ007 ,"+ //--客户办理的业务   对私
	        		" T4.FXQ008 ,"+ //--是否涉及风险提示信息或权威媒体报道信息
	        		" T4.FXQ009 ,"+ //--客户或其亲属、关系密切人等是否属于外国政要  对私
	        		" "+ //--客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要  对公
	        		" T4.FXQ010 ,"+ //--反洗钱交易监测记录
	        		" T4.FXQ011 ,"+ //--是否被列入中国发布或承认的应实施反洗钱监控措施的名单
	        		" T4.FXQ012 ,"+ //--是否发生具有异常特征的大额现金交易
	        		" T4.FXQ013 ,"+ //--是否发生具有异常特征的非面对面交易
	        		" T4.FXQ014 ,"+ //--是否存在多次涉及跨境异常交易报告
	        		" T4.FXQ015 ,"+ //--代办业务是否存在异常情况
	        		" T4.FXQ016 ,"+ //--是否频繁进行异常交易
	        		" T4.FXQ026 ,"+ //--客户所在行政区域是否存在严重犯罪
	        		" T4.FXQ021 ,"+ //--与客户建立业务关系的渠道
	        		" T4.FXQ022 ,"+ //--是否在规范证券市场上市
	        		" T4.FXQ023 ,"+ //--客户的股权或控制权结构
	        		" T4.FXQ024 ,"+ //--客户是否存在隐名股东或匿名股东
	        		" T4.FXQ025,"+ //  --客户办理的业务   对公
	        		" DQSH001,"+ //   --证件是否过期
	        		" DQSH002,"+ //  --客户是否可取得联系
	        		" DQSH003,"+ //  --联系时间
	        		" DQSH004,"+ //  --联系人与帐户持有人的关系-对私
	        		" DQSH005,"+ //	--预计证件更新时间
	        		" DQSH006,"+ //	--未及时更新证件的理由
	        		" DQSH007,"+ //	--客户是否无正当理由拒绝更新证件
	        		" DQSH008,"+ //	--客户留存的证件及信息是否存在疑点或矛盾
	        		" DQSH009,"+ //	--账户是否频繁发生大额现金交易
	        		" DQSH010,"+ //	--账户是否频繁发生外币现钞存取业务
	        		" DQSH011,"+ //	--账户现金交易是否与客户职业特性相符
	        		" DQSH012,"+ //	--账户是否频繁发生大额的网上银行交易
	        		" DQSH013,"+ //	--账户是否与公司账户之间发生频繁或大额的交易
	        		" DQSH014,"+ //	--账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符
	        		" DQSH015,"+ //	--账户资金是否快进快出，不留余额或少留余额
	        		" DQSH016,"+ //	--账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
	        		" DQSH017,"+ //	--账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
	        		" DQSH018,"+ //	--账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
	        		" DQSH019,"+ //	--账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
	        		" DQSH020,"+ //	--账户是否频繁发生跨境交易，且金额大于1万美元
	        		" DQSH021,"+ //	--账户是否经常由他人代为办理业务
	        		" DQSH022,"+ //	--客户是否提前偿还贷款，且与其财务状况明显不符
	        		" DQSH023,"+ //	--当前账户状态是否正常
	        		
	        		" T.CURRENT_AUM,"+//AUM(人民币)(20160318新增)
	        		" DQSH024,"+ //	--AUM(人民币)          改为  客户是否涉及反洗钱黑名单
	        		" DQSH025,"+ //	--企业证件是否过期
	        		" DQSH026,"+ //	--法定代表人证件是否过期
	        		" DQSH027,"+ //	--联系人证件是否过期
	        		" DQSH028,"+ //	--联系人的身份-对公
	        		" DQSH029,"+ //	--账户是否与自然人账户之间发生频繁或大额的交易
	        		" DQSH030,"+ //	--账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符
	        		" DQSH031,"+ //	--账户是否频繁收取与其经营业务明显无关的汇款
	        		" DQSH032,"+ //	--账户资金交易频度、金额是否与其经营背景相符
	        		" DQSH033,"+ //	--账户交易对手及资金用途是否与其经营背景相符
	        		" DQSH034,"+ //	--账户是否与关联企业之间频繁发生大额交易
	        		" DQSH035,"+ //	--客户行为是否存在异常
	        		" DQSH036,"+ //	--账户交易是否存在异常
	        		" DQSH037,"+ //	--联系人与帐户持有人的关系说明
	        		" DQSH038"+ //	--联系人的身份说明
	        		" FROM ACRM_F_CI_CUSTOMER T"+ //
	        		" LEFT JOIN ACRM_F_CI_ORG T1 ON T.CUST_ID = T1.CUST_ID"+ //
	        		" LEFT JOIN ACRM_F_CI_PERSON T2 ON T.CUST_ID = T2.CUST_ID"+ // 
	        		" INNER JOIN ACRM_A_FACT_FXQ_CUSTOMER T3 ON T.CUST_ID = T3.CUST_ID"+ //
	        		" LEFT JOIN ACRM_F_SYS_CUST_FXQ_INDEX T4 ON T.CUST_ID = T4.CUST_ID"+ //
	        		" LEFT JOIN ACRM_A_ANTI_DQSH_INFO T5 ON T.CUST_ID = T5.CUST_ID "+ //
	        		" LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID "+    //客户是否为代理开户 
	        		" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR T6 ON T.CUST_ID = T6.CUST_ID " //
	        		);
	        
	        
	        
	        
	    	sb.append(" where 1=1  AND T.CUST_ID='"+custId+"'");
	    	
	    	
	    	//sb1 暂时没用
			StringBuffer sb1=new StringBuffer(//83项
					" SELECT DISTINCT "+                 
							" C.CUST_ID, "+   //   --客户号  对公对私
							" C.CORE_NO, "+   //   --核心客户号 对公对私
							" C.CUST_NAME, "+   //  --客户名称 对公对私
							" C.CUST_TYPE, "+   //   --客户类型                               
							" P.CITIZENSHIP, "+   //  --国籍  对私
							" P.CAREER_TYPE, "+   //  --职业  对私
							" P.BIRTHDAY, "+   //     --出生日期  对私
							" t9.ident_value IDENT_TYPE1, "+   // --证件类型1  对公对私
							" t9.ident_no INDENT_NO1, "+   //  --证件号1 对公对私对公对私
							" t9.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1, "+   //   --证件1到期日 对公对私
							" t10.ident_value IDENT_TYPE2, "+   // --证件类型2 对公对私
							" t10.ident_no INDENT_NO2, "+   //  --证件号2 对公对私
							" t10.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2, "+   //  --证件2到期日 对公对私
							" p.if_org_sub_type as if_org_sub_type_per, "+   //  --是否自贸区(对私) 
							" O.IF_ORG_SUB_TYPE AS if_org_sub_type_ORG, "+   //   --是否自贸区(对私) 
							" O.BUILD_DATE, "+   //   --成立日期  --对公
							" O.NATION_CODE, "+   //  --国家或地区代码 --注册地 对公
							"OCT.FLAG_AGENT," +//客户是否为代理开户
							//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
							//" FXQ.FXQ006, "+   //   --客户是否为代理开户  对公对私
							" AGE.AGENT_NAME, "+   //  --代理人姓名  对公对私
							" AGE.AGENT_NATION_CODE, "+   //  --代理人国籍  对公对私
							" AGE.IDENT_TYPE AGE_IDENT_TYPE, "+   //  --代理人证件类型   对公对私
							" AGE.IDENT_NO AGE_IDENT_NO, "+   //   --代理人证件号码  对公对私
							" AGE.TEL, "+   //     --代理人联系电话  对公对私                                                                                              
							"  "+   //--保留客户是否涉及反洗钱黑名单   对公对私
							" FXQ.FXQ007, "+   //   --客户办理的业务(对私) 对公对私
							" FXQ.FXQ008, "+   //   --是否涉及风险提示信息或权威媒体报道信息  对公对私
							" FXQ.FXQ009, "+   //   --客户或其亲属、关系密切人等是否属于外国政要 对公对私
							//* 合规处调整指标
							" FXQ.FXQ010,"+ //--反洗钱交易监测记录
							" FXQ.FXQ012,"+ //--是否发生具有异常特征的大额现金交易
							" FXQ.FXQ013,"+ //--是否发生具有异常特征的非面对面交易
							" FXQ.FXQ014,"+//--是否存在多次涉及跨境异常交易报告
							" FXQ.FXQ015,"+ //--代办业务是否存在异常情况
							" FXQ.FXQ016,"+ //--是否频繁进行异常交易
							" FXQ.FXQ026,"+ //客户所在行政区域是否存在严重犯罪
							" FXQ. FXQ.FXQ021, "+   //   --与客户建立业务关系的渠道  对公
							" FXQ.FXQ022, "+   //   --是否在规范证券市场上市  对公 
							" FXQ.FXQ023, "+   //   --客户的股权或控制权结构  对公 
							" FXQ.FXQ024, "+   //   --客户是否存在隐名股东或匿名股东 对公 
							" FXQ.FXQ025, "+   //   --客户办理的业务(对公)  对公 
							" FUN_CHANGE_CODE(o.In_Cll_Type,'FXQ020') In_Cll_Type, "+   // --行业分类  对公
							" CASE "+   //
							" WHEN O.ENT_SCALE_CK IS NULL THEN "+   //
							" nvl(trim(O.ENT_SCALE_RH), 'CS04') "+   // --企业规模（人行） 
							" ELSE "+   //
							" NVL(trim(O.ENT_SCALE_CK), 'CS04') "+   // --企业规模（存款）  
							" END AS ENT_SCALE_CK, "+   //  --企业规模  对公
							" DQSH.DQSH001, "+   //  --证件是否过期   对私
							" DQSH.DQSH002, "+   //  --客户是否可取得联系   对私
							" DQSH.DQSH003, "+   //  --联系时间   对私
							" DQSH.DQSH004, "+   //  --联系人与帐户持有人的关系 对公对私 
							" DQSH.DQSH005, "+   //  --预计证件更新时间 对公对私
							" DQSH.DQSH006, "+   //  --未及时更新证件的理由  对公对私
							" DQSH.DQSH007, "+   //  --客户是否无正当理由拒绝更新证件  对公对私
							" DQSH.DQSH008, "+   //  --客户留存的证件及信息是否存在疑点或矛盾 对公对私 
							" DQSH.DQSH009, "+   //  --账户是否频繁发生大额现金交易  对公对私
							" DQSH.DQSH010, "+   //  --账户是否频繁发生外币现钞存取业务  对公对私
							" DQSH.DQSH011, "+   //  --账户现金交易是否与客户职业特性相符  对公对私
							" DQSH.DQSH012, "+   //  --账户是否频繁发生大额的网上银行交易  对公对私
							" DQSH.DQSH013, "+   //  --账户是否与公司账户之间发生频繁或大额的交易  对公对私
							" DQSH.DQSH014, "+   //  --账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符  对公对私
							" DQSH.DQSH015, "+   //  --账户资金是否快进快出，不留余额或少留余额  对公对私
							" DQSH.DQSH016, "+   //  --账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准 对公对私 
							" DQSH.DQSH017, "+   //  --账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付 对公对私 
							" DQSH.DQSH018, "+   //  --账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付  对公对私
							" DQSH.DQSH019, "+   //  --账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心  对公对私
							" DQSH.DQSH020, "+   //  --账户是否频繁发生跨境交易，且金额大于1万美元 对公对私
							" DQSH.DQSH021, "+   //  --账户是否经常由他人代为办理业务  对公对私
							" DQSH.DQSH022, "+   //  --客户是否提前偿还贷款，且与其财务状况明显不符  对公对私
							" DQSH.DQSH023, "+   //  --当前账户状态是否正常  对公对私
							
				            " C.CURRENT_AUM,"+//AUM(人民币)(20160318新增)
							" DQSH.DQSH024, "+   //  --AUM(人民币) 对公对私 改为  客户是否涉及反洗钱黑名单
							" DQSH.DQSH025, "+   //  --企业证件是否过期  对公 
							" DQSH.DQSH026, "+   //  --法定代表人证件是否过期  对公 
							" DQSH.DQSH027, "+   //  --联系人证件是否过期  对公 
							" DQSH.DQSH028, "+   //  --联系人的身份  对公 
							" DQSH.DQSH029, "+   //  --账户是否与自然人账户之间发生频繁或大额的交易  对公 
							" DQSH.DQSH030, "+   //  --账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符  对公 
							" DQSH.DQSH031, "+   //  --账户是否频繁收取与其经营业务明显无关的汇款  对公
							" DQSH.DQSH032, "+   //  --账户资金交易频度、金额是否与其经营背景相符  对公
							" DQSH.DQSH033, "+   //  --账户交易对手及资金用途是否与其经营背景相符  对公
							" DQSH.DQSH034, "+   //  --账户是否与关联企业之间频繁发生大额交易   对公
							" DQSH.DQSH035 DQSH0351, "+   //  --客户行为是否存在异常   对公 低风险
							" DQSH.DQSH036 DQSH0361, "+   //  --账户交易是否存在异常    对公 低风险  
							" DQSH.DQSH035 DQSH0352, "+   //  --客户行为是否存在异常 对私 低风险
							" DQSH.DQSH036 DQSH0362, "+   //  --账户交易是否存在异常   对私 低风险         
							" DQSH.DQSH037,"+ //	--联系人与帐户持有人的关系说明
			        		" DQSH.DQSH038,"+ //	--联系人的身份说明
							" G.CUST_GRADE, "+   //  --客户反洗钱等级                                
							" to_char(LAST_DAY(sysdate), 'yyyy-mm-dd') AUDIT_END_DATE "+   // --审核截止日期
							
						/*	
						 * " GD.CUST_GRADE as FXQ_RISK_LEVEL," + 
							" GD.CUST_GRADE as OLD_FXQ_RISK_LEVEL," +//更新前风险等级
							" GD.LAST_UPDATE_USER," + //最后更新人
							" C.CREATE_DATE, " +
							" fc.FLAG, " +	
							" FC.FLAG_FP, "+ //复评状态
						 */							
							" from ACRM_F_CI_CUSTOMER C "+   //   ----客户主表
							" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M "+   //  --客户经理归属表
							" ON M.CUST_ID = C.CUST_ID "+   //
							" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T "+   //   --客户经理信息表
							" ON T.CUST_MANAGER_ID = M.MGR_ID "+   //  
							" LEFT JOIN ACRM_F_CI_PERSON P "+   //   --对私个人主表
							" ON P.CUST_ID = C.CUST_ID "+   //
							" LEFT JOIN ACRM_F_CI_ORG O "+   //      --对公企业主表
							" ON O.CUST_ID = C.CUST_ID "+   //
							" left join (select t.cust_id, "+   //
							" t.IDENT_no, "+   //
							" t.IDENT_EXPIRED_DATE, "+   //
							" t1.f_value ident_value "+   //
							" from acrm_f_ci_cust_identifier t "+   //   --证件表
							" left join (select * "+   //
							" from ocrm_sys_lookup_item "+   //  --字典表
							" where f_lookup_id = 'XD000040') t1 "+   //
							" on t.IDENT_TYPE = t1.F_CODE "+   //
							" where IS_OPEN_ACC_IDENT = 'Y') t9 "+   //
							" on C.cust_id = t9.cust_id "+   //
							" left join (select t.cust_id, "+   //
							" t.IDENT_no, "+   //
							" t.IDENT_EXPIRED_DATE, "+   //
							" t1.f_value ident_value "+   //
							" from acrm_f_ci_cust_identifier t "+   //  --证件表
							" left join (select * "+   //
							" from ocrm_sys_lookup_item "+   //  --字典表
							" where f_lookup_id = 'XD000040') t1 "+   //
							" on t.IDENT_TYPE = t1.F_CODE "+   //
							" where (IS_OPEN_ACC_IDENT <> 'Y' OR "+   //
							" IS_OPEN_ACC_IDENT IS NULL) "+   //
							" AND ident_type NOT IN ('V', '15X', 'W', 'Y')) t10 "+   //
							" on C.cust_id = t10.cust_id "+   //
							" left join ACRM_F_SYS_CUST_FXQ_INDEX FXQ "+   //  --反洗钱前台录入指标表
							" on FXQ.CUST_ID = C.CUST_ID "+   //
							" LEFT JOIN ACRM_F_CI_AGENTINFO AGE "+   //     --代理人信息表
							" ON AGE.CUST_ID = C.CUST_ID "+   //
							" LEFT JOIN ACRM_A_ANTI_DQSH_INFO DQSH ON C.CUST_ID = DQSH.CUST_ID "+   //
							" LEFT JOIN ocrm_f_ci_agent_tmp OCT ON OCT.CUST_ID=C.CUST_ID "+    //客户是否为代理开户
							" LEFT JOIN (SELECT * FROM  ACRM_F_CI_GRADE WHERE  cust_grade_type = '01' ) G "+   //--客户等级表
							" ON G.CUST_ID = C.CUST_ID "
							
					);
	    	
			sb1.append(" where 1=1  AND C.CUST_ID='"+custId+"'");
			QueryHelper query;
			query = new QueryHelper(sb1.toString(), ds.getConnection(), null);//查询
			Map<String, Object> result = query.getJSON();
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
	
	
   
}
