<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder,com.yuchengtech.bob.vo.AuthUser" %>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/groupMemberManageList.js"></script>
<script type="text/javascript">
<%
AuthUser auth3=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth3.getUserId();
String unitId = auth3.getUnitId();
String unitName = auth3.getUnitName();
String unitlevel = (String)auth3.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%></script>
</head>
<body>
<input id='idStr' type="hidden"/>

</head>

<body >

</body>
</html>