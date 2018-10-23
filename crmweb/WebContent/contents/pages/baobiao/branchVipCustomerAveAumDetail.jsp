<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<!-- 树形Grid -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/index.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/css/TreeGrid.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/TreeGrid.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/resource/ext3/ux/CustomerQueryMagnifier.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonExtPanel.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/commonjs/scriptLoader.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonCustView.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/globalVariable.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/baobiao/branchVipCustomerAveAumDetail.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/assistInput.js"></script>
<!-- 关系网络图 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxclient-ie1.8.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxGrapth-Crm-locale-ext-v1.000.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/crm-mxGraph-api-v1.000.js"></script>
<!-- 公共选择放大镜 -->
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>


<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerGroup/baseGlobalVariable.js"></script>
<style type="text/css"><!--
.input_Assist
{
	background-color:#DDDDDD;
}
.ul_background
{
	background-color:#FFFFFF;
	list-style-type:none;
	font-size:12px;
 	border-style: solid;
	border-width:3px;
  	border-color:#DDDDDD;
}
</style>
</head>                                                        
<body>
<div id='north'></div>
<div id='center'></div>
<div id="custNameInputDiv" style="position: absolute; bottom: 1in;right: 1in; top: 1in;width:200;overflow-x:auto;display:none;"></div> 
</body>
</html>