<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<title>集团客户存贷信息</title>
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/treegrid/treegrid.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/index.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/css/TreeGrid.css" />

<style>
	.blocLoanAndDepositNodeClass table
	{
		/*text-align:'center'*/
		color:red;
	}
</style>
       
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/TreeGrid.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/groupClientManager/groupCreditValueView.js"></script>
</head>
<body>
<div id="blocCreditValue">
</div>
</body>
</html>