package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
 * @ClassName: CustomerQueryActio
 * @Description: 客户查询Action
 *    客户查询去除数据权限的代码控制，改为由后台数据过滤器配置 by :sujm 20140821
 * @author luyy
 * @date 2014-7-17  
 *
 */
@SuppressWarnings("serial")
@Action("customerQuery")
public class CustomerQueryAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){

		StringBuffer sb=new StringBuffer("SELECT C.CUST_ID,C.CUST_NAME,C.CORE_NO,C.IDENT_TYPE,C.IDENT_NO,C.CUST_TYPE,C.CUST_LEVEL,C.CURRENT_AUM,C.TOTAL_DEBT,C.RISK_LEVEL," +
			" NVL(EXE.LINKMAN_NAME, PLINK.LINKMAN_NAME) LINKMAN_NAME, NVL(EXE.MOBILE, PLINK.MOBILE) LINKMAN_TEL,O.org_biz_cust_type as BELONG_LINE_NO,C.CUST_STAT,M.INSTITUTION_NAME AS ORG_NAME,M.MGR_NAME, EE.CUST_GRADE_TYPE, " +
			" C.CREDIT_LEVEL,C.FAXTRADE_NOREC_NUM,AA.USER_NAME BELONG_TEAM_HEAD_NAME,L.BL_NAME, EE.CUST_GRADE AS FXQ_RISK_LEVEL, "+
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
//			" fc.FLAG, " +
			
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
			" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON M.MGR_ID = A.ACCOUNT_NAME"+
			" LEFT JOIN ADMIN_AUTH_ACCOUNT AA ON A.BELONG_TEAM_HEAD = AA.ACCOUNT_NAME"+
//			" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T ON M.MGR_ID = T.CUST_MANAGER_ID " +
			" left join acrm_f_ci_org o on o.CUST_ID = C.CUST_ID " +

			" LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type =  L.BL_NO  " +
			" left join OCRM_F_CI_ANTI_CUST_LIST risk on risk.cust_id = c.cust_id " +
			" LEFT JOIN ACRM_F_SYS_CUST_FXQ_INDEX IX ON IX.CUST_ID = C.CUST_ID " + 
			" left join ACRM_F_CI_SPECIALLIST s on s.CUST_ID = C.CUST_ID " +
//			" left join acrm_a_fact_fxq_customer fc on fc.CUST_ID = C.CUST_ID " +//判断客户是否是新老客户
			" LEFT JOIN ACRM_F_CI_ORG_EXECUTIVEINFO EXE  ON EXE.ORG_CUST_ID = C.CUST_ID  AND EXE.LINKMAN_TYPE = '21'"+//--机构干系人
			"  LEFT JOIN ACRM_F_CI_PER_LINKMAN PLINK  ON PLINK.CUST_ID = C.CUST_ID  AND PLINK.LINKMAN_TYPE = '21'"+//--个人联系人信息
//			"left join ( select exe.org_cust_id cust_id,exe.linkman_name,exe.mobile  from   ACRM_F_CI_ORG_EXECUTIVEINFO exe where exe.linkman_type='21' "+
//			" union all "+
//			" select  plink.cust_id,plink.linkman_name,plink.mobile from  ACRM_F_CI_PER_LINKMAN plink where plink.linkman_type='21') linkman on linkman.cust_id=c.cust_id "+
//			" LEFT JOIN (SELECT ee.CUST_ID, ee.CUST_GRADE_TYPE,ee.CUST_GRADE," +
//			"   (case when ee.LAST_UPDATE_USER <> 'ETL' then tt.user_name else ee.LAST_UPDATE_USER end ) as LAST_UPDATE_USER FROM ACRM_F_CI_GRADE  ee " +
//			"  left join admin_auth_account tt on tt.account_name = ee.LAST_UPDATE_USER " +
//			"  WHERE CUST_GRADE_TYPE = '01') GD ON GD.CUST_ID = C.CUST_ID " +
			"  LEFT JOIN ACRM_F_CI_GRADE EE ON EE.CUST_ID = C.CUST_ID AND EE.CUST_GRADE_TYPE = '01'"+
			"  LEFT JOIN ADMIN_AUTH_ACCOUNT TT ON TT.ACCOUNT_NAME = EE.LAST_UPDATE_USER"+
			"  left join ACRM_F_CI_PERSON n on c.cust_id = n.cust_id" +
//			" WHERE 1=1  ");
			//add by liuming 20170824,过滤本行员工
		    " WHERE 1=1  and nvl(C.staffin,' ') <> 'S' ");
		
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
			sb.append(" and EXISTS  (SELECT 1 FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND C.CUST_ID =I.CUST_ID)");
		}else if(null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}
			
		SQL=sb.toString();
		datasource=ds;
		
		//setPrimaryKey("c.CUST_ID desc ");
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
		configCondition("A.BELONG_TEAM_HEAD","=","MGR_ID1",DataType.String);
		configCondition("AA.USER_NAME","=","BELONG_TEAM_HEAD_NAME",DataType.String);
		configCondition("C.CORE_NO","=","CORE_NO",DataType.String);
		configCondition("EE.CUST_GRADE","=","FXQ_RISK_LEVEL",DataType.String);
	}
	
}
