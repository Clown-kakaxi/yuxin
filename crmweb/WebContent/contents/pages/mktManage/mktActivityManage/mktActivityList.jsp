<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--产品放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<!--客户放大镜 -->	
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js"></script>
<%@ page import="com.yuchengtech.bob.core.SysPublicParamManager"  language = "java"%>
<script type="text/javascript">
	var a="<%=request.getContextPath()%>";
	var basepath = "/" + a.substring(1, a.length);	
	<%
	String aimCustSource =   SysPublicParamManager.getInstance().findParamValueByName(SysPublicParamManager.AIM_CUST_SOURCE);
	String mktAppType =   SysPublicParamManager.getInstance().findParamValueByName(SysPublicParamManager.MKT_APP_TYPE);
	
	out.print("var __aimCustSource = '"+aimCustSource+"';");//客户来源渠道参数
	out.print("var __mktAppType = '"+mktAppType+"';");//营销活动审批方式参数
	%>

</script>
<!-- 复选下拉框 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/css/LovCombo.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/LovCombo.js"></script>

<!-- 营销活动产品修改 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityProdEditInfo.js"></script>
<!-- 营销活动客户修改 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityCustEditInfo.js"></script>
<!-- 营销活动渠道修改 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityChanelEditInfo.js"></script>
<!-- 附件信息 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js"></script>
<!-- 审批信息 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityShenPiInfo.js"></script>

<!-- 活动主文件-->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityList1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityList.js"></script>
</head>
<body>
<input id='executeIdStr' type='hidden'/>
<input id='marketActivityIdStr' type='hidden'/>
</body>
</html>