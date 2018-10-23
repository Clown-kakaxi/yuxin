<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/oldcare/productWindow.js"></script>
			
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/planCreate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/planInvest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/planAdjust.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/insuranceCare/productinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/investPlan.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/investPlan/investPlanSearch.js"></script>
<script type="text/javascript">	
function gotoBlocView(itemValue){
		if("规划创建"==itemValue)
		{
				document.getElementById("blocViewFrame").src="planCreate.jsp";
				return;
		}
		else if("投资方式"==itemValue)
		{
			document.getElementById("blocViewFrame").src="planInvest.jsp";
			return;
		}	
		else if("资产配置"==itemValue)
		{
			document.getElementById("blocViewFrame").src="planAdjust.jsp";
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