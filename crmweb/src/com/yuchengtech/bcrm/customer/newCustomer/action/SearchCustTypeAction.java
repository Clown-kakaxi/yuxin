package com.yuchengtech.bcrm.customer.newCustomer.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.action.BaseQueryAction;
import com.yuchengtech.bob.core.QueryHelper;

/**
 * 数据权限下拉列表查询
 * 
 * @author sunjing
 * @since 2017-05-01
 */

@ParentPackage("json-default")
@Action(value = "/searchCustTypeAction", results = { @Result(name = "success", type = "json") })
public class SearchCustTypeAction extends BaseQueryAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 数据源

	public void prepare() {
	}
/**
 * 特殊监管区：保税内  的码值
 */
	@SuppressWarnings("unchecked")
	public void searchS() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT I.F_VALUE AS VALUE, I.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000304' AND I.F_CODE BETWEEN '1'AND 'I' AND I.F_CODE NOT IN ('99') ORDER BY I.F_CODE");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
/**
 * 特殊监管区：保税区外的码值
 */
	@SuppressWarnings("unchecked")
	public void searchNS() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT I.F_VALUE AS VALUE, I.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000304' AND I.F_CODE in ('L','M') ORDER BY REPLACE(I.F_CODE,99,0)");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
/**
 * 特殊监管区：所有的码值
 */
	public void searchALL() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT I.F_VALUE AS VALUE, I.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000304' AND I.F_CODE NOT IN ('J','K') ORDER BY REPLACE(I.F_CODE,99,0)");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
/**
 * 特殊监管区：是否自贸区为“是”，且为“保税区内”时的码值
 */
	public void searchZM1() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT i.f_value AS VALUE, i.f_code AS KEY FROM OCRM_SYS_LOOKUP_ITEM i WHERE i.f_lookup_id='XD000304' AND I.F_CODE IN ('E','F','G','H','I')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
	/**
	 * 特殊监管区：是否自贸区为“是”，且为“保税区外”时的码值
	 */
		public void searchZM2() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);

			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT i.f_value AS VALUE, i.f_code AS KEY FROM OCRM_SYS_LOOKUP_ITEM i WHERE i.f_lookup_id='XD000304' AND I.F_CODE IN ('M')");

			SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
			try {
				QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
				json = qh.getJSON();
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("KEY", "");
				// map.put("VALUE", "无");
				((List) json.get("data")).add(map);

			} catch (Exception e) {
			}
		}
/**
 * 特殊监管区：是否自贸区为“否”，且为“保税区内”时的码值
 */
	public void searchNZM1() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT i.f_value AS VALUE, i.f_code AS KEY FROM OCRM_SYS_LOOKUP_ITEM i WHERE i.f_lookup_id='XD000304' AND (I.F_CODE BETWEEN '1' AND 'D')   AND I.F_CODE NOT IN('99')   ORDER BY I.F_CODE");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
	/**
	 * 特殊监管区：是否自贸区为“否”，且为“保税区外”时的码值
	 */
		public void searchNZM2() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);

			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT i.f_value AS VALUE, i.f_code AS KEY FROM OCRM_SYS_LOOKUP_ITEM i WHERE i.f_lookup_id='XD000304' AND I.F_CODE IN ('L')");

			SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
			try {
				QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
				json = qh.getJSON();
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("KEY", "");
				// map.put("VALUE", "无");
				((List) json.get("data")).add(map);

			} catch (Exception e) {
			}
		}
		
	public void searchAddr() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ " FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000192' AND I.F_CODE NOT IN('07','08')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}

	public void searchIdent() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ " FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000040' AND I.F_CODE NOT IN('Z','V','W','Y')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}

	public void searchLink() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000339' AND I.F_CODE NOT IN('5')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}

	public void searchOpen() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
//		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
//				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000040' AND I.F_CODE IN('20','2X','H','K')");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000040' AND I.F_CODE IN('20','2X')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
	
	/**
	 * add by liuming 20170816
	 * 查询所有证件类型
	 **/
	
	public void searchAll() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000040'");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
	
	/**
	 * add by liuming 20170816
	 * 查询OP页面的证件类型1、证件类型2的交集(除个人证件类型)
	 **/
	public void searchIdentType() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000040' AND I.F_CODE IN ('H','K','J','C','V','3X','9X','W','Y') ");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
	
	//查询币种
	public void searchIcon() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000027' AND I.F_CODE IN('36', '102', '756', '156', '978', '826', '344', '392', '446', '554', '643', '702', '901', '840')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
	   //查询所有币种
		public void searchIconAll() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);

			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
					+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000027'");

			SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
			try {
				QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
				json = qh.getJSON();
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("KEY", "");
				// map.put("VALUE", "无");
				((List) json.get("data")).add(map);

			} catch (Exception e) {
			}
		}
	//客户类型码值修改，不要个金码值
	public void searchcomType() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  I.F_VALUE AS VALUE, I.F_CODE AS KEY"
				+ "  FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='XD000053' AND I.F_CODE not IN('001','002')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("KEY", "");
			// map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
	}
}
