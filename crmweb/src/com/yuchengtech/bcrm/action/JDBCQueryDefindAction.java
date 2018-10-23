package com.yuchengtech.bcrm.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

//import com.yuchengtech.crm.service.custumer.CrmFciRelativesService;



/**
 * @author sujm 自定义查询
 */
@SuppressWarnings("serial")
@Action("/jdbcquerydefindaction")
public class JDBCQueryDefindAction  extends CommonAction{
	@Autowired
	private DataSource dataSource;

  
    @SuppressWarnings("finally")
	public DefaultHttpHeaders create(String idStr) {// 验证营销活动是否关联了客户
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String ss = idStr;
		boolean flag = true;
		try {
			String[] strarray = ss.split(",");
			for (int i = 0; i < strarray.length; i++) {
				String sss = strarray[i];

				conn = dataSource.getConnection();
				statement = conn.createStatement();

				String sql = "select * from ocrm_f_mk_acti_customer t where t.mkt_acti_id = '"
						+ sss + "' ";
				rs = statement.executeQuery(sql);

				if (!rs.next()) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, statement, conn);
			if (flag == false) {
				throw new BizException(1, 0, "100010", "对不起，您须先关联目标客户，才能执行此操作!");
			}
			return new DefaultHttpHeaders("success");
		}
    }
}