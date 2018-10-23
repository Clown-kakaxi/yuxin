package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/exportact", results={
    @Result(name="success", type="json"),
})
public class ExportAction extends CommonAction{
	
	@Autowired
	private DataSource ds;
	
    @Override
    public void prepare() {
        StringBuilder sb = new StringBuilder().append("Select n.NOTICE_ID,n.NOTICE_TITLE,n.NOTICE_CONTENT,n.NOTICE_LEVEL,(SELECT u2.USERNAME FROM SYS_USERS u2 WHERE u2.USERID=n.PUBLISHER) AS PUBLISHER_NAME,n.PUBLISHER,(SELECT u1.UNITNAME FROM SYS_UNITS u1 WHERE u1.UNITID=n.PUBLISH_ORG) AS PUB_ORG_NAME, n.PUBLISH_ORG,n.IS_TOP,n.PUBLISH_TIME,n.MOD_TYPE,n.PUBLISHED,n.ACTIVE_DATE,n.TOP_ACTIVE_DATE,n.NOTICE_TYPE,(SELECT u2.USERNAME FROM SYS_USERS u2 WHERE u2.USERID=n.CREATOR) AS CREATOR_NAME,n.CREATOR"
                +" from ocrm_f_wp_notice n");
        sb.append(" ORDER BY n.PUBLISH_TIME ,n.ACTIVE_DATE DESC");
        this.SQL = sb.toString();
        this.datasource  = ds;
        this.setPrimaryKey("NOTICE_ID");
        this.addOracleLookup("NOTICE_LEVEL", "NOTICE_LEV");
    }

}
