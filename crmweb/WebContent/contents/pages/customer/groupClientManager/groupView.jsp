<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../../resource/ext3/resources/css/ext-all.css">   
<script type="text/javascript" src="../../../resource/ext3/adapter/ext/ext-base.js"></script>   
<script type="text/javascript" src="../../../resource/ext3/ext-all.js"></script>

<script type="text/javascript" src="groupView.js"></script>

</head>
<body>

</body>
</html>

************************************************************ -->
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<%
	String groupNo = request.getParameter("groupNo");
	String groupName = request.getParameter("groupName");
	String hostOrgNo = request.getParameter("hostOrgNo");
%>

<html>
<head>
<script type="text/javascript">
function gotoBlocView(itemValue){
	
	if("集团组织架构图"==itemValue)
	{
			document.getElementById("blocViewFrame").src="customerOrgChart/groupCustomerChart.jsp";
			return;
	}
	else if("集团成员列表"==itemValue)
	{
		document.getElementById("blocViewFrame").src="blocMemberShowList.jsp";
		return;
	}	
	else if("授信信息"==itemValue)
	{
		document.getElementById("blocViewFrame").src="groupCreditValueView.jsp";
		return;
	}
	else if("集团基本信息"==itemValue)
	{
		document.getElementById("blocViewFrame").src="blocBaseInfo.jsp";
		return;
	}	
	else if("产品信息"==itemValue)
	{
		document.getElementById("blocViewFrame").src="blocProductInfoList.jsp";
		return;
	}
	else if("存贷款信息"==itemValue)
	{
		document.getElementById("blocViewFrame").src="blocLoanAndDepositList.jsp";
		return;
	}	
}
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/groupView.js"></script>
</head>
<body>
	<input id="groupNo" type="hidden" value="<%= groupNo %>" >
	<input id="groupName" type="hidden" value="<%= groupName %>" >
	<input id="hostOrgNo" type="hidden" value="<%= hostOrgNo %>" >
</body>
</html>




