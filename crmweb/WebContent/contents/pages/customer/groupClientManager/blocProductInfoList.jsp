<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/blocProductInfoList.js"></script>
<script type="text/javascript">
<%
AuthUser auth6=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth6.getUserId();
String unitId = auth6.getUnitId();
String unitName = auth6.getUnitName();
String unitlevel = (String)auth6.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%></script>
</head>
<body>

</body>
</html>