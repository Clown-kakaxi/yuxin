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
	href="${ctx}/css/classics/template/template12.css" />
<sitemesh:write property='head' />
<script type="text/javascript">
	$(function() {
		var height = $(document).height();
		$("#center").height(height - 40 - 35);
		$("#content").height(height - 40 - 35);
		$("#treeContainer").height(height - 40 - 35);
	});
</script>

</head>
<body>
	<div id="center" >
		<div class="content" style="border: 1px solid #D6D6D6; ">
			<div id="treeContainer"
				style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
				<ul id="tree"
					style="font-size: 12; background-color: F1F1F1; width: 90%"
					class="ztree"></ul>
			</div>
		</div>
	</div>
	<div id="bottom">
		<div class="form-bar">
			<div class="form-bar-inner" style="padding-top: 5px"></div>
		</div>
	</div>
</body>

</html>