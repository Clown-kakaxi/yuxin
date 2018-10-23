package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.core.QueryHelper;
/**
 * 贡献度参数树
 * @author weijl
 * @since 2012-09-24
 */
@ParentPackage("json-default")
@Action(value="/ContributionParamTree", results={@Result(name="success", type="json")})
public class ContributionParamTreeAction {

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle; //声明数据源
	
	private Map<String, Object> JSON; //声明JSON数据
	
	/**
	 * 获取JSON数据
	 * @return JSON数据
	 */
    public Map<String, Object> getJSON() {
		return JSON;
	}

    /**
     * 设置JSON数据
     * @param jSON
     */
	public void setJSON(Map<String, Object> jSON) {
		JSON = jSON;
	}
	
	private StringBuilder queryStr; //声明查询SQL
	
	/**
	 * 设置查询SQL语句
	 */
	public void prepare() {
		queryStr = new StringBuilder(" SELECT P.* FROM OCRM_F_CI_CONTRI_PARAM P WHERE 1=1 ");
	}
	
	/**
	 * 查询贡献度参数数据
	 * @return
	 */
	public String index() {
		prepare(); 
		try {
			setJSON(new QueryHelper(queryStr.toString(), dsOracle.getConnection()).getJSON()); //获取贡献度参数并为JSON赋值
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
