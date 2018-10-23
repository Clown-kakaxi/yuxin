<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/oldcare/productWindow.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/oldcare/oldCarePlan.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/singTargetPlan/oldcare/oldCarePlanSearch.js"></script>
<script type="text/javascript">	
	function gotoBlocView(itemValue){
		
		if("客户需求采集"==itemValue)
		{
				document.getElementById("blocViewFrame").src="info1.jsp";
				return;
		}
		else if("规划策略"==itemValue)
		{
			document.getElementById("blocViewFrame").src="info2.jsp";
			return;
		}	
		else if("产品信息"==itemValue)
		{
			document.getElementById("blocViewFrame").src="info3.jsp";
			return;
		}
	}
	</script>
</head>
<body>
</body>
</html>