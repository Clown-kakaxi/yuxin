<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<!-- 产品放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<!-- 客户放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<!-- 用户放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<!-- 组织机构放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<!-- 营销活动放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js"></script>
<!-- 营销任务指标放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktTaskTargetCommonQuery.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktMyActi/mktBusiOpporCreateForm.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/mktManage/mktMyActi/mktMyActi1.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/mktManage/mktMyActi/mktMyActi.js"></script>
<%@ page import="com.yuchengtech.bob.core.SysPublicParamManager"  language = "java"%>
<script type="text/javascript">
	var a="<%=request.getContextPath()%>";
	var basepath = "/" + a.substring(1, a.length);	
	<%
	String aimCustSource =   SysPublicParamManager.getInstance().findParamValueByName(SysPublicParamManager.AIM_CUST_SOURCE);
	
	out.print("var __aimCustSource = '"+aimCustSource+"';");//客户来源渠道参数
	%>

</script>
</head>
<body>
<input id='myActiIdStr' type='hidden'/>
<input id='custNameStr' type='hidden'/>
<input id='custIdStr' type='hidden'/>
<input id='executorIdStr' type='hidden'/>
<input id='executorNameStr' type='hidden'/>
</body>
</html>