<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/common/includes.jsp"%>
<script type="text/javascript">
	<%
		String custId = request.getParameter("custId");
		String busiId = request.getParameter("busiId");
		out.print("var _custId = '"+custId+"';");
		out.print("var _busiId = '"+busiId+"';");
	%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.FusionChartPanel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/wlj/customerManager/privateCustView/privateCustInfoHomePage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/frameControllers/widgets/app/Wlj-frame-function-app-cfg.js"></script>

</head>
<body>
</body>
</html>