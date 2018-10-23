<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/oldcare/productWindow.js"></script>
			
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/productinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/planCreate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/colligate/planTarget.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/colligate/planWay.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/planAdjust.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/colligate/colligatePlan.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/colligate/colligatePlanSearch.js"></script>
<script type="text/javascript">	
function gotoBlocView(itemValue){
		if("规划创建"==itemValue)
		{
				document.getElementById("blocViewFrame").src="../investPlan/planCreate.jsp";
				return;
		}
		if("客户生活目标"==itemValue)
		{
				document.getElementById("blocViewFrame").src="planTarget.jsp";
				return;
		}
		if("策略选择"==itemValue)
		{
				document.getElementById("blocViewFrame").src="planWay.jsp";
				return;
		}
		if("资产配置"==itemValue)
		{
				document.getElementById("blocViewFrame").src="../investPlan/planAdjust.jsp";
				return;
		}
		else if("产品推荐"==itemValue)
		{
			document.getElementById("blocViewFrame").src="../insuranceCare/productinfo.jsp";
			return;
		}
	}
	</script>
</head>
<body>
</body>
</html>