package com.yuchengtech.bcrm.customer.action;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/analogprofparamquery")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "analogprofparamquery"})
})
public class AnalogProfParamQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		String orgId = auth.getUnitId();

		StringBuilder sb = new StringBuilder("SELECT op.PARM_ID,"+
				" cp.PARAM_NAME as PARM_TYPE, "+
				" ccp.PARAM_NAME as PARM_NAME,"+
				" op.PARM_NAME as PARM_NAME_NUM,"+
				" op.PARM_TYPE as PARM_TYPE_NUM,"+
				" op.PARM_NUM,"+
				" op.PARM_LEVEL, "+
				" org.UNITNAME, "+
				" uss.USERNAME, "+
				" op.DATEDT "+
				" FROM OCRM_F_CI_CONTRIBUTION_PARM op "+
				" left join (SELECT c.PARAM_NAME, c.PARAM_ID FROM OCRM_F_CI_CONTRI_PARAM c where c.PARAM_KIND=0 ) cp "+
				" on op.PARM_TYPE = cp.PARAM_ID "+
				" left join OCRM_F_CI_CONTRI_PARAM ccp "+
				" on op.PARM_NAME = ccp.PARAM_ID "+
				" left join SYS_UNITS org "+
				" on op.BRID = org.UNITID "+
				" left join SYS_USERS uss"+
				" on op.OWENERID = uss.USERID "+
				" where 1>0 and org.UNITID = '"+orgId+"'");

		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("parmType"))
					sb.append(" and op.PARM_TYPE = '" + this.getJson().get(key)
							+ "'");
				
			}
		}

		SQL = sb.toString();
		setPrimaryKey("op.PARM_ID desc");
		datasource = ds;

	}

	public Map<String, Object> getJson() {
		return super.getJson();
	}
}
