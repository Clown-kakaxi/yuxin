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
	href="${ctx}/css/classics/template/template5.css" />
<script type="text/javascript">
	$(function() {
		templateshow();
	});
	function templateshow() {
		var height = $(document).height();
		var bottomheight = 40;
		$("#bottom").height(bottomheight);
		$("#center").height(height - bottomheight-3);
	}
</script>
<sitemesh:write property='head' />
</head>
<body>
	<div id="center" style="width: 100%; overflow: auto; clear: both;">
		<sitemesh:write property='div.template.center' />
	</div>
	<div id="bottom">
		<div class="form-bar">
			<div class="form-bar-inner" style="padding-top: 5px"></div>
		</div>
	</div>
</body>
</html>