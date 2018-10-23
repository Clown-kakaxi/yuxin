<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.yuchengtech.bob.upload.FileTypeConstance" language="java" %>
<%@ taglib uri="/VersionControlTag" prefix="version" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<!--
	<link rel="shortcut icon" href="favicon.ico" />
	-->
	<%@ include file="/contents/pages/common/includes.jsp"%>
	<!--
	<version:frameScript type="text/javascript" src="/dwr/engine.js"/>
	<version:frameScript type="text/javascript" src="/dwr/util.js"/>
	-->
	<version:frameScript type="text/javascript" src="/contents/frameControllers/WljAPPBooter.js"/>
	<version:frameScript type="text/javascript" src="/contents/wljFrontFrame/searchFace.js"/>
	
	<style type="text/css">	
	.guard_container{
		background:#1b96d1;
		border-color:#1b96d1;
		width : 100px;
		height : 20px;
	}
	
	.reIcon{
		background:#000;
		border-color:#1b96d1;
		position : absolute;
		height : 20px;
		width : 20px;
	}
	.reIcon2{
		background:#FF1493;
		border-color:#FF1493;
		position : absolute;
		height : 20px;
		width : 20px;
	}
	</style>
	<script type="text/javascript"> __condition=''; </script>
</head>
	<body>
	<input id='condition' hidden="true" style="display:none;"/>
	</body>
</html>
