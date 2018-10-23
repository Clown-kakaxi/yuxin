<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/wlj/printManager/common/printInclude.jsp"%>
	<version:frameLink  type="text/css" rel="stylesheet" href="/contents/pages/wlj/printManager/common/printPriceCss.css" />
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/common/printParam.js"/>
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/common/printPriceFormat.js"/>
	
	<script type="text/javascript" >		
						
	var LOAN_PRICE = '<%=request.getParameter("LOAN_PRICE")==null?"":request.getParameter("LOAN_PRICE")%>';
	var PLAN_APPLY = '<%=request.getParameter("PLAN_APPLY")==null?"":request.getParameter("PLAN_APPLY")%>';
	var LOAN = '<%=request.getParameter("LOAN")==null?"":request.getParameter("LOAN")%>';
	var LOAN_PERCENT = '<%=request.getParameter("LOAN_PERCENT")==null?"":request.getParameter("LOAN_PERCENT")%>';
	var PRICE = '<%=request.getParameter("PRICE")==null?"":request.getParameter("PRICE")%>';
	var LOANABLE_PERCENT = '<%=request.getParameter("LOANABLE_PERCENT")==null?"":request.getParameter("LOANABLE_PERCENT")%>';
	var LIMIT1 = '<%=request.getParameter("LIMIT1")==null?"":request.getParameter("LIMIT1")%>';
	var REPAY1 = '<%=request.getParameter("REPAY1")==null?"":request.getParameter("REPAY1")%>';
	var LIMIT1_TIME = '<%=request.getParameter("LIMIT1_TIME")==null?"":request.getParameter("LIMIT1_TIME")%>';
	var LIMIT1_RATE = '<%=request.getParameter("LIMIT1_RATE")==null?"":request.getParameter("LIMIT1_RATE")%>';
	var LIMIT1_REPAY = '<%=request.getParameter("LIMIT1_REPAY")==null?"":request.getParameter("LIMIT1_REPAY")%>';
	var LIMIT2 = '<%=request.getParameter("LIMIT2")==null?"":request.getParameter("LIMIT2")%>';
	var REPAY2 = '<%=request.getParameter("REPAY2")==null?"":request.getParameter("REPAY2")%>';
	var LIMIT2_TIME = '<%=request.getParameter("LIMIT2_TIME")==null?"":request.getParameter("LIMIT2_TIME")%>';
	var LIMIT2_RATE = '<%=request.getParameter("LIMIT2_RATE")==null?"":request.getParameter("LIMIT2_RATE")%>';
	var LIMIT2_REPAY = '<%=request.getParameter("LIMIT2_REPAY")==null?"":request.getParameter("LIMIT2_REPAY")%>';
	var LIMIT1_LEVEL = '<%=request.getParameter("LIMIT1_LEVEL")==null?"":request.getParameter("LIMIT1_LEVEL")%>';
	var LIMIT2_LEVEL = '<%=request.getParameter("LIMIT2_LEVEL")==null?"":request.getParameter("LIMIT2_LEVEL")%>';
	var EMPOWER1_RATE = '<%=request.getParameter("EMPOWER1_RATE")==null?"":request.getParameter("EMPOWER1_RATE")%>';
	var EMPOWER2_RATE = '<%=request.getParameter("EMPOWER2_RATE")==null?"":request.getParameter("EMPOWER2_RATE")%>';
	var EMPOWER1_FTPRATE = '<%=request.getParameter("EMPOWER1_FTPRATE")==null?"":request.getParameter("EMPOWER1_FTPRATE")%>';
	var EMPOWER2_FTPRATE = '<%=request.getParameter("EMPOWER2_FTPRATE")==null?"":request.getParameter("EMPOWER2_FTPRATE")%>';
	var EMPOWER1_RMBRATE = '<%=request.getParameter("EMPOWER1_RMBRATE")==null?"":request.getParameter("EMPOWER1_RMBRATE")%>';
	var EMPOWER2_RMBRATE = '<%=request.getParameter("EMPOWER2_RMBRATE")==null?"":request.getParameter("EMPOWER2_RMBRATE")%>';
	var AMOUNT_RATE = '<%=request.getParameter("AMOUNT_RATE")==null?"":request.getParameter("AMOUNT_RATE")%>';
	var FINAL_LEVEL = '<%=request.getParameter("FINAL_LEVEL")==null?"":request.getParameter("FINAL_LEVEL")%>';
			  
	</script>
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/printPreviewCalcPrice.js"/>
</head>

</html>

