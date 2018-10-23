package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value = "/custMgrAffirmQuery", results = { @Result(name = "success", type = "json") })
public class CustMgrAffirmQueryAction extends CommonAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public void prepare() {
        StringBuilder sb = new StringBuilder(" SELECT A.*,C.UNITNAME SUPER_UNIT_NAME,E.ROLE_CODE,E.ROLE_NAME,B.UNITNAME,F.USER_ID,(select count(1) from OCRM_F_CM_CUST_MGR_INFO H where H.cust_manager_id=A.userid ) IS_CUST_MANAGER FROM SYS_USERS A " +
        		" INNER JOIN SYS_UNITS B ON A.UNITID=B.UNITID"+
        		" INNER JOIN SYS_UNITS C ON B.SUPERUNITID=C.UNITID" +
        		" LEFT JOIN ADMIN_AUTH_ACCOUNT_ROLE D ON A.ID=D.ACCOUNT_ID" +
        		" LEFT JOIN ADMIN_AUTH_ROLE E ON D.ROLE_ID=E.ID" +
        		" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO F ON A.USERID=F.CUST_MANAGER_ID where 1= 1"
        		);

        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("cust_Mgr_Flag")){
                	if(this.getJson().get(key).equals("0")){
                		sb.append(" and (select count(1) from OCRM_F_CM_CUST_MGR_INFO H where H.cust_manager_id=A.userid )=0");
                	}else if(this.getJson().get(key).equals("1")){
                		sb.append(" and (select count(1) from OCRM_F_CM_CUST_MGR_INFO H where H.cust_manager_id=A.userid )>0");
                	}
                	
                }
                if (key.equals("checkedNodes")){
                    String orgStr = this.getJson().get(key).toString();
                    String newStr = orgStr.replace(",", "','");
                    sb.append(" and A.UNITID in  ('" + newStr
                            + "')");}
            }
        }

        SQL = sb.toString();
        setPrimaryKey("A.USERID");
        setBranchFileldName("a.unitid");
//        addGreenplumLookup("CUR_COD", "CCY");
//        addOracleLookup("LNCRD_STS", "LNCRD_STS");
        datasource = ds;
    }

}
