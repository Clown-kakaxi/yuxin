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

/**
 * 贡献度参数查询
 * @author weijl
 * @since 2012-09-20
 */
@Action("/ContributionParam")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "ContributionParam"})
})
public class ContributionParamAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源

	/**
	 * 设置查询SQL，并为父类属性赋值
	 */
	public void prepare() {

		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); //获取当前登陆用户

		String orgId = auth.getUnitId(); //获取当前登陆用户机构号
		String unitLevel = auth.getUnitlevel(); //获取当前登陆用户机构级别

		StringBuilder queryStr = new StringBuilder("SELECT OP.PARM_NAME,"+
				" OP.PARM_NUM "+
				" FROM OCRM_F_CI_CONTRIBUTION_PARM OP "+
				" WHERE 1>0 AND OP.BRID = '"+orgId+"'"+
				" AND OP.PARM_LEVEL = '"+unitLevel+"'");

		SQL = queryStr.toString();
		setPrimaryKey("OP.PARM_NAME DESC");
		datasource = ds;

	}

	/**
	 * 获取JSON数据
	 */
	public Map<String, Object> getJson() {
		return super.getJson();
	}
}
