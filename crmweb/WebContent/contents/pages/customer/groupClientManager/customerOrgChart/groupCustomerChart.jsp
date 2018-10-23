<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/contents/pages/common/includes.jsp"%>
	<script type="text/javascript">
	<%
		String busiId = request.getParameter("busiId");
		out.print("var _busiId = '"+busiId+"';");
	%>
	</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxclient-ie1.8.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxGrapth-Crm-locale-ext-v1.000.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/crm-mxGraph-api-v1.000.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/customerOrgChart/groupCustomerChart.js"></script>
</head>	
<body></body>
</html>