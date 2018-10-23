<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
	var custId = '<%=request.getParameter("custId") %>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonExtPanel.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Crm-Ext-Extends-1.000-v1.0.js"></script>

<script type="text/javascript"src="<%=request.getContextPath()%>/contents/commonjs/scriptLoader.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/DateComputer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/integralManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/integralAdd.js"></script>

<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>


</head>
<body>
</body>
</html>