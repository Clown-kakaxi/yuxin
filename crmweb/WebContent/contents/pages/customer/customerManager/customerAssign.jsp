<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonExtPanel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/assignOrgDetail.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/assignCustMgrDetail.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/customerAssignHist.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/customerAssign.js"></script>

<%@ page import="com.yuchengtech.bob.core.SysPublicParamManager"  language = "java"%>
<script type="text/javascript">
	var a="<%=request.getContextPath()%>";
	var basepath = "/" + a.substring(1, a.length);	
	<%
	String custManagerType =   SysPublicParamManager.getInstance().findParamValueByName(SysPublicParamManager.CUST_MANAGER_TYPE);
	out.print("var __custManagerType = '"+custManagerType+"';");//客户归属参数
	%>







</script>


</head>
<body>
</body>
</html>