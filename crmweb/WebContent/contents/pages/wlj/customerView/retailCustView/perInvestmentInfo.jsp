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
		out.print("var _custId = '"+custId+"';");
		out.print("var _busiId = '"+busiId+"';");
	%>
	//注：此tabs变量必须按此格式定义,且此文件只能更改tabs变量里面的内容
	var tabs =[{
		title:'客户他行资产负债信息',
		url:'/contents/pages/wlj/customerView/retailCustView/perInvestmentInfo1.js'
	},
	{
		title:'客户其他资产负债信息',
		url:'/contents/pages/wlj/customerView/retailCustView/perInvestmentInfo2.js'
	},
	{
		title:'客户家庭月度收支信息',
		url:'/contents/pages/wlj/customerView/retailCustView/perInvestmentInfo3.js'
	},
	{
		title:'财务分析',
		url:'/contents/pages/wlj/customerView/retailCustView/perInvestmentInfo4.js'
	},
	{
		title:'财务指标分析',
		url:'/contents/pages/wlj/customerView/retailCustView/perInvestmentInfo5.js'
	}
	];
	</script>
	<version:frameScript type="text/javascript" src="/contents/pages/common/Com.yucheng.bcrm.common.tabpanel.js"/>
</head>
<body>
</body>
</html>