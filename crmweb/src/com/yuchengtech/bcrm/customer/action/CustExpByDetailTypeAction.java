package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.JdbcUtil;


@ParentPackage("json-default")
@Action(value="/smartcustomerquery", results={
    @Result(name="success", type="json"),
})
public class CustExpByDetailTypeAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;

		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		List r_mapList = new ArrayList();// 用于存储需要添加字典转换的字段
		// 获取DBTABLE_ID和pramas
		String DBTABLE_ID = request.getParameter("DBTABLE_ID");
		String pramas = request.getParameter("pramas");

		// 查询语句
		StringBuilder sb = new StringBuilder("");
		sb.append(" select * from ");
		// 拼接查询语句
		// 表名
		String table = " select value from mtool_dbtable where id='"
				+ DBTABLE_ID + "'";
		// 字段
		String colunm = " select col_name_e,col_name_c,notes,decode(col_type,'NUMBER','num','DECIMAL','num','dic') as type"
				+ " from  mtool_dbcol m where dbtable_id='"
				+ DBTABLE_ID
				+ "' and (notes is not null or col_type in ('NUMBER','DECIMAL')) order by id";
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(table);
			while (rs.next()) {
				sb.append(rs.getString("value"));
			}
			sb.append(" where 1=1 ");

			if (pramas != null && !("".equals(pramas))) {// pramas==null用于在点击查询之前(直接在bbar中点击刷新的时候）此时不拼接条件
				String parmaarray[] = pramas.split(":");
				stmt1 = conn.createStatement();
				rs1 = stmt1.executeQuery(colunm);
				int i = 0;
				String type = "";
				while (rs1.next()) {
					type = rs1.getString("type");
					if ("dic".equals(type)) {
						if (!"empty".equals(parmaarray[i])) {
							String parma = parmaarray[i].replace("a", "00|0");// 将用a代替的0或00换回来
							sb.append("and ");
							sb.append(rs1.getString("col_name_e"));
							sb.append(" in ('");
							sb.append(parma.replace("|", "','"));
							sb.append("') ");
							if (rs1.getString("notes") != null
									|| !"".equals(rs1.getString("notes"))) {
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("name", rs1.getString("col_name_e"));
								map.put("note", rs1.getString("notes"));
								r_mapList.add(map);
							}
						}
					} else if ("num".equals(type)) {
						String text[] = parmaarray[i].replace("|", ",").split(
								",");// 直接用|分割有问题
						if (!"empty".equals(text[0])) {
							sb.append("and ");
							sb.append(rs1.getString("col_name_e"));
							sb.append(" >=");
							sb.append(text[0]);
							sb.append(" ");
						}
						if (!"empty".equals(text[1])) {
							sb.append("and ");
							sb.append(rs1.getString("col_name_e"));
							sb.append(" <=");
							sb.append(text[1]);
							sb.append(" ");
						}
					}
					i++;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs1, stmt1, null);
			JdbcUtil.close(rs, stmt, conn);
		}
		setPrimaryKey("cust_id");
		for (int i = 0; i < r_mapList.size(); i++) {
			HashMap<String, Object> map = (HashMap<String, Object>) r_mapList.get(i);
			addOracleLookup(map.get("name").toString(), map.get("note").toString());
		}

		SQL = sb.toString();
		datasource = ds;
	}
}

