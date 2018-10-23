<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/trustCust/customerGroup/custGroupInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/trustCust/customerGroup/custGroupActInfo.js"></script>
</head>
<body>
<input type='hidden' id='sqlD' value="<%=request.getAttribute("sql")%>"/>
</body>
</html>