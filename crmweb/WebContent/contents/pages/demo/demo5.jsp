<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" >

<%
AuthUser auth2=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth2.getUserId();
String unitId = auth2.getUnitId();
String unitName = auth2.getUnitName();
String unitlevel = (String)auth2.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%>

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<title>My Test File</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/css/LovCombo.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/LovCombo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/demo/demo5.js"></script>
	
</head>
<body>
</body>
</html>