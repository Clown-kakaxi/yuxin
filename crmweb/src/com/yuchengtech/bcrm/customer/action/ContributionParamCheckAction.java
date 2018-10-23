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
 * 贡献度参数查询（用于检测是否存在同名参数）
 * @author weijl
 * @since 2012-09-19
 */
@Action("/ContributionParamCheck")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "ContributionParamCheck"})
})
public class ContributionParamCheckAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源

	/**
	 * 设置查询SQL并为父类属性赋值
	 */
	public void prepare() {
		StringBuilder queryStr = new StringBuilder("SELECT OP.PARAM_NAME,"+
				" OP.PARAM_ID "+
				" FROM OCRM_F_CI_CONTRI_PARAM OP "+
				" WHERE 1>0 AND OP.PARAM_KIND != '0' ");

		SQL = queryStr.toString();
		setPrimaryKey("OP.PARAM_ID DESC");
		datasource = ds;

	}

	/**
	 * 获取JSON数据
	 */
	public Map<String, Object> getJson() {
		return super.getJson();
	}
}
