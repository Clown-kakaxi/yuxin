<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html> 
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/productManage/productStatistics/productTradeDetail.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/productManage/productStatistics/productStatistics.js"></script>
<script type="text/javascript">
<%
AuthUser auth7=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth7.getUserId();
String unitId = auth7.getUnitId();
String unitName = auth7.getUnitName();
String unitlevel = (String)auth7.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%></script></head>

<body>
<input id='idStr' type="hidden"/>
</body>
</html>
