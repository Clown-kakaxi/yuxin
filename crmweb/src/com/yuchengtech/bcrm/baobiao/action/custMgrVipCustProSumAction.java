package com.yuchengtech.bcrm.baobiao.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/custMgrVipCustProSum", results={
    @Result(name="success", type="json"),
})
public class custMgrVipCustProSumAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
		// TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder("SELECT A.MONTH,A.ORG_NAME2,A.ORG_ID2,A.ORG_NAME4,A.ORG_ID4,A.CUST_MANAGER_ID,A.CUST_MANAGER_NAME," +
        		"A.ZJ_STANDARD_NUM,A.ZJ_STANDARD_RATE," +
        		"A.ZJ_DXT_NUM/A.ZJ_VIP_NUM AS ZJ_DXT,A.ZJ_WY_NUM/A.ZJ_VIP_NUM AS ZJ_WY," +
        		"A.ZJ_XYK_NUM/A.ZJ_VIP_NUM AS ZJ_XYK,A.ZJ_LC_NUM/A.ZJ_VIP_NUM AS ZJ_LC," +
        		"A.ZJ_SJYH_NUM/A.ZJ_VIP_NUM AS ZJ_SJYH,A.ZJ_YZKX_NUM/A.ZJ_VIP_NUM AS ZJ_YZKX," +
        		"A.ZJ_ZFZHT_NUM/A.ZJ_VIP_NUM AS ZJ_ZFZHT," +
        		"A.ZJ_TYHF_NUM/A.ZJ_VIP_NUM AS ZJ_TYHF,A.ZJ_CDSY_NUM/A.ZJ_VIP_NUM AS ZJ_CDSY, " +
        		"A.ZJ_JJ_NUM/A.ZJ_VIP_NUM AS ZJ_JJ,A.ZJ_GJS_NUM/A.ZJ_VIP_NUM AS ZJ_GJS," +
        		"A.ZJ_YBT_NUM/A.ZJ_VIP_NUM AS ZJ_YBT,A.ZJ_CFYW_NUM/A.ZJ_VIP_NUM AS ZJ_CFYW," +
        		"A.HJ_STANDARD_NUM,A.HJ_STANDARD_RATE," +
        		"A.HJ_DXT_NUM/A.HJ_VIP_NUM AS HJ_DXT,A.HJ_WY_NUM/A.HJ_VIP_NUM AS HJ_WY," +
        		"A.HJ_XYK_NUM/A.HJ_VIP_NUM AS HJ_XYK,A.HJ_LC_NUM/A.HJ_VIP_NUM AS HJ_LC," +
        		"A.HJ_SJYH_NUM/A.HJ_VIP_NUM AS HJ_SJYH,A.HJ_YZKX_NUM/A.HJ_VIP_NUM AS HJ_YZKX," +
        		"A.HJ_ZFZHT_NUM/A.HJ_VIP_NUM AS HJ_ZFZHT," +
        		"A.HJ_TYHF_NUM/A.HJ_VIP_NUM AS HJ_TYHF,A.HJ_CDSY_NUM/A.HJ_VIP_NUM AS HJ_CDSY, " +
        		"A.HJ_JJ_NUM/A.HJ_VIP_NUM AS HJ_JJ,A.HJ_GJS_NUM/A.HJ_VIP_NUM AS HJ_GJS," +
        		"A.HJ_YBT_NUM/A.HJ_VIP_NUM AS HJ_YBT,A.HJ_CFYW_NUM/A.HJ_VIP_NUM AS HJ_CFYW," +
        		"A.BJ_STANDARD_NUM,A.BJ_STANDARD_RATE," +
        		"A.BJ_DXT_NUM/A.BJ_VIP_NUM AS BJ_DXT,A.BJ_WY_NUM/A.BJ_VIP_NUM AS BJ_WY," +
        		"A.BJ_XYK_NUM/A.BJ_VIP_NUM AS BJ_XYK,A.BJ_LC_NUM/A.BJ_VIP_NUM AS BJ_LC," +
        		"A.BJ_SJYH_NUM/A.BJ_VIP_NUM AS BJ_SJYH,A.BJ_YZKX_NUM/A.BJ_VIP_NUM AS BJ_YZKX," +
        		"A.BJ_ZFZHT_NUM/A.BJ_VIP_NUM AS BJ_ZFZHT," +
        		"A.BJ_TYHF_NUM/A.BJ_VIP_NUM AS BJ_TYHF,A.BJ_CDSY_NUM/A.BJ_VIP_NUM AS BJ_CDSY, " +
        		"A.BJ_JJ_NUM/A.BJ_VIP_NUM AS BJ_JJ,A.BJ_GJS_NUM/A.BJ_VIP_NUM AS BJ_GJS," +
        		"A.BJ_YBT_NUM/A.BJ_VIP_NUM AS BJ_YBT,A.BJ_CFYW_NUM/A.BJ_VIP_NUM AS BJ_CFYW" +
        		" FROM ACRM_M_VIP_PROD_HAVING_RATE A WHERE A.CUST_MANAGER_ID IS not NULL"  );
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("MONTH"))
                    sb.append(" and A."+key+" = '"+this.getJson().get(key)+"'");
                else if(key.equals("CUST_MANAGER"))
                    sb.append(" and A.CUST_MANAGER_ID = '"+this.getJson().get(key)+"'");
//                else if(key.equals("ORG_NAME2"))
//                	sb.append(" and (A.ORG_ID2 = '"+this.getJson().get(key)+"'or A.ORG_ID4 = '"+this.getJson().get(key)+"')");
              
            }
        }
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();        
		String role = "";
		for(GrantedAuthority ga : auth.getAuthorities() ){
		    role += ga.getAuthority()+"$";
		}
//		if(role.indexOf("2")>=0){
//			sb.append(" and  ci.create_user = '"+userId+"'");  
//		}
        
        setPrimaryKey("A.CUST_MANAGER_ID");
//        setBranchFileldName("u.unitid");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}
