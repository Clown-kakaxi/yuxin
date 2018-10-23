<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/template/template14.css" />
<script type="text/javascript">
	$(function() {
		templateshow();
	});
	function templateshow(){
		var $content = $(document);
		var height = $content.height();
		$("#center").height(height - 2);
	}
</script>
<sitemesh:write property='head' />
</head>
<body style="padding-right: 0px;
	padding-left: 0px;
	padding-top: 0px;">
	<div id="center" style="width: 100%; overflow: auto; clear: both;">
		<sitemesh:write property='div.template.center' />
	</div>
</body>
</html>
