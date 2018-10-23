<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<!-- 首页例子2 ->  -->
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/mainpage/example.css" />
<script type="text/javascript">

	$(function(){
		var contentHeight = $(document).height();
		$("#all").height(contentHeight);
	});
	
</script>
</head>
<body>
	<div id="all">

	</div>
</body>
</html>