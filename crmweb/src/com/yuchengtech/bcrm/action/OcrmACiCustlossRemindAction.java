package com.yuchengtech.bcrm.action;

/**
 * 大额客户流失预警处理
 * luyy
 * 2014-07-19
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmACiCustlossRemind;
import com.yuchengtech.bcrm.customer.service.OcrmACiCustlossRemindService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.constance.SystemConstance;

@ParentPackage("json-default")
@Action(value = "/custLost")
public class OcrmACiCustlossRemindAction  extends CommonAction{

@Autowired
@Qualifier("dsOracle")
private DataSource ds;

@Autowired
private OcrmACiCustlossRemindService service;

@Autowired
public void init(){
	model = new OcrmACiCustlossRemind();
	setCommonService(service);
}

AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

public void prepare(){
	 ActionContext ctx = ActionContext.getContext();
	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	 StringBuilder sb=new StringBuilder();
	sb.append(" select c.*,o.org_name as branch_name from OCRM_A_CI_CUSTLOSS_REMIND c " +
			"left join admin_auth_org o on c.branch_no = o.org_id where 1=1");
	
	//根据用户控制权限
	String searchLevel = "mgr";
	//根据角色限制查询
	List list = auth.getRolesInfo();
	for(Object m:list){
		Map map = (Map)m;
		if("1".equals(map.get("ROLE_LEVEL"))){//总行
			searchLevel = "zh";
			break ;
		}else if("R201".equals(map.get("ROLE_CODE"))){
			searchLevel = "jg";//区域中心行长
			continue ;
		}else{
			continue ;
		}
	}
	
	if("mgr".equals(searchLevel)){//客户经理主管,支行行长    本分行
		sb.append(" and  c.BRANCH_NO='"+auth.getUnitId()+"'");
	}
	if("jg".equals(searchLevel)){//查询本机构
		if("DB2".equals(SystemConstance.DB_TYPE)) {
			StringBuilder withSb = new StringBuilder();
			withSb.append("with rpl (org_id,up_org_id) as " +
					" ( " +
					" select org_id,up_org_id   from admin_auth_org  " +
					" where org_id ='"+auth.getUnitId()+"' " +
					" union all  select  child.org_id,child.up_org_id from rpl parent, admin_auth_org child where child.up_org_id=parent.org_id " +
			" ) " );
			withSQL = withSb.toString();
			sb.append(" and c.BRANCH_NO in (select org_id from rpl)");
		} else {
			sb.append(" and c.BRANCH_NO in (select org_id from admin_auth_org o start with org_id='"+auth.getUnitId()+"' connect by o.up_org_id = prior org_id)");
		}
	}
	
	 for(String key : this.getJson().keySet()){
  		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(key.equals("DECLARE_CUR_TYPE")||"BRANCH_NO".equals(key)||"TRAD_AMT".equals(key)){
					sb.append("  and c."+key+" like '%"+this.getJson().get(key)+"%'  ");
				}else if("TRAD_AMT_S".equals(key)){
					sb.append("  and c.TRAD_AMT  >"+this.getJson().get(key)+"  ");
				}else if("TRAD_AMT_E".equals(key)){
					sb.append("  and c.TRAD_AMT <"+this.getJson().get(key)+"  ");
				}else if(key.equals("TRAD_DATE")){
					sb.append("  and c."+key+" =to_date( '"+this.getJson().get(key)+"' ,'YYYY-MM-dd') ");
				}else if("BRANCH_NAME".equals(key)){
					sb.append("  and  o.org_name like '%"+this.getJson().get(key)+"%' ");
				}else {
					sb.append("  and  c."+key+" like '%"+this.getJson().get(key)+"%' ");

              }
  		}
		}
	
	SQL = sb.toString();
	datasource = ds;
}

@SuppressWarnings("unchecked")
public void save() throws SQLException{
	//此处将修改为通过接口获取信息再保存
	service.save(model);
	//此时需要根据金额查询大额信息提醒规则，然后生成提醒信息
	BigDecimal money = ((OcrmACiCustlossRemind)model).getTradAmt();//交易金额
	String custName = ((OcrmACiCustlossRemind)model).getCustName();
	String custId = ((OcrmACiCustlossRemind)model).getCustId();//客户id
	String barchNo =  ((OcrmACiCustlossRemind)model).getBranchNo();//所在支行
	String areaCenter =  ((OcrmACiCustlossRemind)model).getAreaCenter();//所在区域中心/异地分行
	
	Connection conn=null;
	Statement stat=null;
	
	//查询预警阀值设置
	List<Object[]> list = (List<Object[]>)service.getBaseDAO().findByNativeSQLWithIndexParam(" " +
			"select AMOUNT,REMIND_ROLES from OCRM_A_CI_CUSTLOSS_RULE order by amount desc");
	if(list != null && list.size()>0){
		for(Object[] o : list){
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(o[0].toString())*10000);
			if(money.compareTo(amount) == 1){//大于   生成信息提醒
				String role = o[1].toString();
				String roles[] = role.split(",");
				for (String temprole : roles){//循环角色，生成提醒信息
					List<Object[]> list1 = (List<Object[]>)service.getBaseDAO().findByNativeSQLWithIndexParam(" " +
					"select ID,ROLE_CODE,ROLE_LEVEL from ADMIN_AUTH_ROLE where ROLE_CODE='"+temprole+"'");
					for(Object[] oo : list1){
						String level = oo[2].toString();
						String roleId = oo[0].toString();
						String insert = "";
						if("1".equals(level)){//总行角色
							insert = " insert into OCRM_F_WP_REMIND (INFO_ID,CUST_ID,CUST_NAME,USER_ID,REMIND_REMARK,'RULE_CODE') " +
									"select ID_SEQUENCE.NEXTVAL,'"+custId+"','"+custName+"',a.account_name,'大额客户流失预警提醒：客户"+custName+"发生金额为"+money+"的账户变动。','1001' " +
											"from admin_auth_account a where a.id in (select account_id from admin_auth_account_role where role_id='"+roleId+"') ";
						}else if("2".equals(level)){//区域中心/异地分行角色
							insert = " insert into OCRM_F_WP_REMIND (INFO_ID,CUST_ID,CUST_NAME,USER_ID,REMIND_REMARK,'RULE_CODE') " +
							"select ID_SEQUENCE.NEXTVAL,'"+custId+"','"+custName+"',a.account_name,'大额客户流失预警提醒：客户"+custName+"发生金额为"+money+"的账户变动。','1001'  " +
									"from admin_auth_account a where a.id in (select account_id from admin_auth_account_role where role_id='"+roleId+"') " +
											"and a.org_id = '"+areaCenter+"' ";
						}else if("3".equals(level)){//支行角色
							insert = " insert into OCRM_F_WP_REMIND (INFO_ID,CUST_ID,CUST_NAME,USER_ID,REMIND_REMARK,'RULE_CODE') " +
							"select ID_SEQUENCE.NEXTVAL,'"+custId+"','"+custName+"',a.account_name,'大额客户流失预警提醒：客户"+custName+"发生金额为"+money+"的账户变动。','1001'  " +
									"from admin_auth_account a where a.id in (select account_id from admin_auth_account_role where role_id='"+roleId+"') " +
											"and a.org_id = '"+barchNo+"' ";
						}
						if(!"".equals(insert)){//执行插入
							try{
								conn = ds.getConnection();
								stat = conn.createStatement();
								stat.executeUpdate(insert);
							}finally{
								JdbcUtil.close(null, stat, conn);
							}
							
						}
					}
				}
				break;
			}
		}
	}
	
	
}
}