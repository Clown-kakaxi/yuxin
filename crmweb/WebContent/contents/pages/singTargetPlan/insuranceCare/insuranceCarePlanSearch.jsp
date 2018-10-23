<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/oldcare/productWindow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/oldinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/eduinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/healthinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/accidentinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/insuranceinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/productinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/insuranceCarePlan.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/insuranceCarePlanSearch.js"></script>
	<script type="text/javascript">	
function gotoBlocView(itemValue){
		if("养老保障"==itemValue)
		{
				document.getElementById("blocViewFrame").src="oldinfo.jsp";
				return;
		}
		else if("教育保障"==itemValue)
		{
			document.getElementById("blocViewFrame").src="eduinfo.jsp";
			return;
		}	
		else if("医疗保障"==itemValue)
		{
			document.getElementById("blocViewFrame").src="healthinfo.jsp";
			return;
		}
		else if("意外保障"==itemValue)
		{
			document.getElementById("blocViewFrame").src="accidentinfo.jsp";
			return;
		}
		else if("投保回顾"==itemValue)
		{
			document.getElementById("blocViewFrame").src="insuranceinfo.jsp";
			return;
		}
		else if("产品推荐"==itemValue)
		{
			document.getElementById("blocViewFrame").src="productinfo.jsp";
			return;
		}
	}
	</script>
</head>
<body>
</body>
</html>