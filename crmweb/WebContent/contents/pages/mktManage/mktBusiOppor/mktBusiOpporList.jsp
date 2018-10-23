<%--商机池-列表页面--%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" language="java"%>
<%@ page import="com.yuchengtech.bob.vo.AuthUser" language="java"%>
<%@ page import="java.util.List" language="java" %>
<%@ page import="java.util.HashMap" language="java" %>

<script type="text/javascript">
<%
//本页面中需要用到当前用户所在组织机构的上级组织机构ID，在此获取并声明成JS变量
AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String mktBusiOppor_currUserOrg_parent = "";//当前用户所在机构的父机构编码
if(auth != null){
	List upOrglist = auth.getUpOrgList();
	if(upOrglist != null && upOrglist.size() > 0){
		HashMap map = (HashMap)upOrglist.get(0);
		mktBusiOppor_currUserOrg_parent = (String)map.get("UNITID");
	}
}
%>
var mktBusiOppor_currUserOrg_parent = "<%=mktBusiOppor_currUserOrg_parent%>";
</script>

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

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporQueryCondition.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporQueryResult.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporAddForm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporSalesActivQuery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporModifyForm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporViewForm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporAllocat.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporReturn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporHisList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporFollow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporClaim.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporClaimAudit.js"></script>
</head>
<body>
</body>
</html>