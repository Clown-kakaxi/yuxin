<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/common/includes.jsp"%>
<script type="text/javascript">
	<%
		String custId = request.getParameter("custId");
		String busiId = request.getParameter("busiId");
		String viewResId = request.getParameter("viewResId");
		out.print("var _custId = '"+custId+"';");
		out.print("var _busiId = '"+busiId+"';");
		
		
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//1、重新将菜单下的控制点写入公共变量,用于数据权限控制
			if(viewResId!=null && !"-1".equals(viewResId) && !"".equals(viewResId)){
				List<String> grants = auth.findGrantByRes(viewResId);
				if(grants!=null){
					for(int i=0;i<grants.size();i++){
						out.print("__grants.push('"+grants.get(i)+"');");
					}
				}
			}
		}
	%>
	JsContext.initContext();
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.FusionChartPanel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/wlj/customerManager/publicCustView/publicCustInfoHomePage.js"></script>


</head>
<body>
</body>
</html>