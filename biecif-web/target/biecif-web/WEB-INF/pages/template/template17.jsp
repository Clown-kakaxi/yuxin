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
	href="${ctx}/css/classics/template/template3.css" />
<script type="text/javascript">
	$(function() {
		templateshow();
	});
	function templateshow() {
		var $content = $(document);
		var height = $content.height() - 10;
		$("#left").height(height);
		$("#right").height(height);
		$("#mainformtd").height(height - 2);
		$("#mainformdiv").height(height - 2);
	}
</script>
<sitemesh:write property='head' />
<script type="text/javascript">
	$(function() {
		templateshow();
	});
</script>
</head>
<body>
	<div id="left" style="background-color: #FFFFFF">
		<sitemesh:write property='div.template.left' />
	</div>
	<div id="right">
		<table width="100%">
			<tr>
				<td id="mainformtd">
					<div id="mainformdiv" style="overflow: auto; font-size: 12px;">
						<sitemesh:write property='div.template.right' />
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
