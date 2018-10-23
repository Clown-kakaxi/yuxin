<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<title>报表信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/report/reportMenuInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/report/MonthPickerPlugin.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/commonjs/scriptLoader.js"></script>

<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>


</head>

<body>
<div style="width:101%;height:100%">
	<div style="position:absolute; left:0px; top:0px;  height:100%;width:210px; float:left;" id='sena_tree'></div>
	<div style="position:absolute; left:350px; top:70px;" id='viewport_center'>
	</div>
</div>
</body>
</html>