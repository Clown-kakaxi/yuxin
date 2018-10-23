<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" >
<%
	request.setCharacterEncoding("utf-8");
	String groupNo = request.getParameter("groupNo");
	String groupName = request.getParameter("groupName");
	String treeRootID = request.getParameter("id")+"";
	
	out.println(" var treeRootID = \""+treeRootID+"\" ;");
	out.println(" var groupNo = \""+groupNo+"\" ;");
	out.println(" var groupName = \""+groupName+"\" ;");	

%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/memberMaintenance.js">
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/blocMemberShowList2.js">
</script>


</head>
<body>

<div id='west'></div>
<div id='center'></div>
	<input id='idStr' type="hidden"/>
		<input id="treeRootID" type="hidden" value="<%= treeRootID %>" >
		<input id="groupNo" type="hidden" value="<%= groupNo %>" >
	<input id="groupName" type="hidden" value="<%= groupName %>" >
</body>

</html>




