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
out.println(" var orgId = \""+unitId+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%>
<%--
var orgId = "<%=request.getSession().getAttribute("unitId")%>" ;
var orgName = "<%=request.getSession().getAttribute("unitName")%>" ;
--%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/repository/informationRetrieval.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js"></script>
</head>
<body>
<input id='messageId'/>
<div id='north'></div>
<div id='center'></div>
</body>
</html>