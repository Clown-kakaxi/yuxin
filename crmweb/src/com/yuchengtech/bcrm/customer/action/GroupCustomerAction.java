package com.yuchengtech.bcrm.customer.action;

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
@Action("groupCustomer")
public class GroupCustomerAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare(){

		StringBuffer sb=new StringBuffer("select GRP_NO,GRP_NAME,PARENT_CUS_ID,PARENT_CUS_NAME,PARENT_LOAN_CARD,GRP_FINANCE_TYPE,GRP_DETAIL," +
				"MAIN_BR_ID,MAIN_BR_NAME,CUS_MANAGER,CUS_MANAGER_NAME,INPUT_USER_ID,INPUT_USER_NAME,INPUT_DATE,INPUT_BR_ID,INPUT_BR_NAME from Ocrm_f_Ci_Group_INFO_NEW  where 1=1");
		
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("GRP_NO".equals(key)){
					sb.append(" and GRP_NO ='"+this.json.get(key)+"'");
				}
				else if("GRP_NAME".equals(key)){ 
					sb.append(" and GRP_NAME like '%"+this.json.get(key)+"%'");
				}
				else	if("PARENT_CUS_NAME".equals(key)){ 
					sb.append(" and PARENT_CUS_NAME like '%"+this.json.get(key)+"%'");
				}
				else if("GRP_FINANCE_TYPE".equals(key)){
					sb.append(" and  GRP_FINANCE_TYPE='"+this.json.get(key)+"'");
				}
			}
		}
//		//添加证件类型与证件号码查询,取证件表上的数据进行查询
//		if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))
//			&& null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
//			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND I.IDENT_NO = '"+this.json.get("IDENT_NO")+"')");
//		}else if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))){
//			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"')");
//		}else if(null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
//			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO = '"+this.json.get("IDENT_NO")+"')");
//		}
			
		SQL=sb.toString();
		datasource=ds;
		
		setPrimaryKey(" GRP_NO desc ");
//		configCondition("GRP_NO","=","GRP_NO",DataType.String);
//		configCondition("GRP_NAME","like","GRP_NAME",DataType.String);
//		configCondition("PARENT_CUS_NAME","like","PARENT_CUS_NAME",DataType.String);
//		configCondition("GRP_FINANCE_TYPE","=","GRP_FINANCE_TYPE",DataType.String);
	}
	
}
