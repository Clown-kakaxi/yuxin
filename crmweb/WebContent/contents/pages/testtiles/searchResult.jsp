<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/frameControllers/WljAPPBooter.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/testtiles/searchResult.js"></script>

<style type="text/css">
	.resultgroup{
		border : 2px solid #000;
	}
	
	.search_cust{
		background-color : #FFDDAA;
	}
	
	.search_info{
		background-color : #00FFFF;
	}
	
	.search_level{
		background-color : #FF0000;
	}
	
	.search_in{
		background-color : #F5DEB3;
	}
	
	.search_or{
		background-color : #F08080;
	}
	
</style>
<script type="text/javascript">
	<%
		out.print("var conditionP='"+request.getParameter("condition")+"';");
	%>
</script>
</head>
<body>
	
</body>
</html>