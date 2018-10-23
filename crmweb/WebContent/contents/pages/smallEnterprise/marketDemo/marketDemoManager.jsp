<%@ page import="org.springframework.security.core.context.SecurityContextHolder,com.yuchengtech.bob.vo.AuthUser" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/css/LovCombo.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/LovCombo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/frameControllers/WljAPPBooter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/smallEnterprise/marketDemo/marketAdd.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/smallEnterprise/marketDemo/marketDemoManager.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/CustomerQueryMagnifier.js"></script>
<!-- 用户放大镜 -->
<!-- 客户放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<!-- 产品放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityProdEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityCustEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityChanelEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityList1.js"></script>
<script type="text/javascript">
<%
AuthUser auth6=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String currenUserId = auth6.getUserId();
String unitId = auth6.getUnitId();
String userName = auth6.getUsername();
String unitName = auth6.getUnitName();
String unitlevel = (String)auth6.getUnitlevel()+"";
out.println(" var orgId = \""+unitId+unitlevel+"\" ;");
out.println(" var orgName = \""+unitName+"\" ;");
out.println(" var userNameSu = \""+userName+"\" ;");
%></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/smallEnterprise/SMEO/SMEOjm.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/smallEnterprise/SMEO/SMEOcy.js"></script> 
<title>专营支行管理</title>
</head>
<body>
<input id='marketTeamIdStr' type="hidden"/>
<div id='view'></div>
</body>
</html>


