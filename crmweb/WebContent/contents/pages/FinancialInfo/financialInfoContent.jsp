<%@page import="com.yuchengtech.crm.constance.JdbcUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>财经资讯内容</title>
</head>
<body>
	<%
		String finInfoId = request.getParameter("finInfoId");
		Long finId = Long.parseLong(finInfoId);
		Connection conn = JdbcUtil.getConnection();
		String sql = "select upload_content from crm_f_sys_fin_info where fin_info_id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, finId);
		ResultSet rs = ps.executeQuery();
		StringBuffer sb=null;
		while (rs.next()) {
			Reader reader = rs.getCharacterStream("UPLOAD_CONTENT");
			BufferedReader bufferedReader = new BufferedReader(reader);
			sb = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			bufferedReader.close();
		}
		String str=sb.toString();
	%>
	<%=str %>
	<form action="financialInfo.jsp" method="post">
		<input type="submit" value="返回"/>
	</form>
</body>
</html>