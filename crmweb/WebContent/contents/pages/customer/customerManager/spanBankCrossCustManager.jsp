<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<input id='idStr' type="hidden"/>
<script type="text/javascript">
<%
AuthUser auth7=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth7.getUserId();
String unitId = auth7.getUnitId();
String unitName = auth7.getUnitName();
String unitlevel = (String)auth7.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
%></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/demo/custMgrGroupCountOrgTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/yucheng/form/month.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/spanBranchAccountCustDetail.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/spanSubbranchAccountCustDetail.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/spanBankCrossCustManager.js"></script>
<div id='north'></div>
<div id='center'></div>
</body>
</html>