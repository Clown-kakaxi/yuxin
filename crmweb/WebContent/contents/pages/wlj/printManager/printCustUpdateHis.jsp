<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/wlj/printManager/common/printInclude.jsp"%>
	<version:frameLink  type="text/css" rel="stylesheet" href="/contents/pages/wlj/printManager/common/printCss.css" />
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/common/printParam.js"/>
	<script type="text/javascript">
		var custId = '<%=request.getParameter("custId")%>';
		var custName = '<%=request.getParameter("custName")%>';
		var UPDATE_ITEM = '<%=request.getParameter("UPDATE_ITEM")%>';
		var UPDATE_USER = '<%=request.getParameter("UPDATE_USER")%>';
		var START_DATE = '<%=request.getParameter("START_DATE")%>';
		var END_DATE = '<%=request.getParameter("END_DATE")%>';
	</script>
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/printCustUpdateHis.js"/>
</head>
</html>