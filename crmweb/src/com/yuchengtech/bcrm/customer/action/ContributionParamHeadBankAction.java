package com.yuchengtech.bcrm.customer.action;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

/**
 * 贡献度参数 总行 查询
 * @author weijl
 * @since 2012-09-15
 */
@Action("/ContributionParamHeadBank")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "ContributionParamHeadBank"})
})
public class ContributionParamHeadBankAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dateSource; //声明数据源

	/**
	 * 设置查询SQL并为父类属性赋值
	 */
	public void prepare() {
		StringBuilder queryStr = new StringBuilder("SELECT OP.PARM_NAME,"+
				" OP.PARM_NUM "+
				" FROM OCRM_F_CI_CONTRIBUTION_PARM OP "+
				" WHERE 1>0 AND OP.BRID = '100000' "+
				" AND OP.PARM_LEVEL = '1'");

		SQL = queryStr.toString();
		setPrimaryKey("OP.PARM_NAME DESC");
		datasource = dateSource;

	}

	/**
	 * 获取JSON数据
	 */
	public Map<String, Object> getJson() {
		return super.getJson();
	}
}
