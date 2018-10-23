<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.yuchengtech.bcrm.callReport.action.CustCallReportInfoAction"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 	
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jQuery/css/default.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jQuery/css/component.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/js/modernizr.custom.js"></script>
</head>
<body>
	<%
	    String custid=request.getParameter("custId");
	    CustCallReportInfoAction ctba = new CustCallReportInfoAction();
	    List<HashMap<String, Object>> callreport = ctba.sourchCallReport(custid);
	    request.setAttribute("callreport", callreport);
	%>

		<div class="container">
		  <h5>---近5笔拜访记录---</h5>
			<div class="main">
				<ul class="cbp_tmtimeline">
				<c:forEach var="stu" items="${callreport}"  varStatus="status">
					<li>
						<time class="cbp_tmtime">
							<span>${stu.VISIT_DATE }</span>
					    </time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>拜访时间：${stu.BEGIN_DATE }-${stu.END_DATE }</h2>
							<p>拜访形式：${stu.VISIT_WAY }</p>
							<p>拜访内容：${stu.MKT_BAK_NOTE }</p>
							<p>商机阶段：${stu.SALES_STAGE }</p>
							<p>下次拜访时间：${stu.NEXT_VISIT_DATE }</p>
						</div>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>

</body>
</html>