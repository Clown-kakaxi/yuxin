<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder,com.yuchengtech.bob.vo.AuthUser" %>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/groupClientMaintenance1.js"></script>
<script type="text/javascript">
<%
AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth.getUserId();
String unitId = auth.getUnitId();
String unitName = auth.getUnitName();
String unitlevel = (String)auth.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/groupCustMember.js"></script>
</head>
<body>
<input id='idStr' type="hidden"/>
</body>
</html>