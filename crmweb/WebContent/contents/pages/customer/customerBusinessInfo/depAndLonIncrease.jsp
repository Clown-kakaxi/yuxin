<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder,com.yuchengtech.bob.vo.AuthUser" %>
<%@ include file="/contents/pages/common/includes.jsp"%>
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

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerBusinessInfo/depAndLonIncrease.js">

</script> 

</head>

<body>
<!-- 
<div id= "addOrgTreeDivForTempDiv"></div>
 -->
</body>
</html>