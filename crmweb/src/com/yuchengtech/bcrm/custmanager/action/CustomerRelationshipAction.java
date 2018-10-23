package com.yuchengtech.bcrm.custmanager.action;
import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
@ParentPackage("json-default")
@Action("/customerRelationship")
public class CustomerRelationshipAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare(){
		StringBuffer sb=new StringBuffer("SELECT t.*, a1.role_name as AUTHOR_NAME FROM OCRM_F_CI_GRAPH t " +
				" left join ADMIN_AUTH_ROLE a1 on t.AUTHOR=a1.ROLE_CODE " +
				" WHERE 1=1 ");
		setPrimaryKey("t.ID desc ");
		SQL=sb.toString();
		datasource=ds;
		configCondition("GRAPH_NAME","like","GRAPH_NAME",DataType.String);
	}	
}
