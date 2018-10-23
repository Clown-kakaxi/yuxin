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
	var rightHeight = $("#right").height();
	var leftHeight = $("#left").height();
	var $treeContainer = $("#treeContainer");
	$treeContainer.height(leftHeight - 58);
	$("#mainformtd").height(height - 35);
	$("#mainformdiv").height(height - 35)
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
		<div id="lefttable" width="100%" border="0">
			<div width="100%"
				style="height:30px;background-image: url(${ctx}/css/classics/ligerUI/Gray/images/ui/gridbar.jpg);border-bottom:1px solid #D6D6D6;">
				<div width="8%"
					style="padding-left: 10px; float: left; position: relative; height: 20p; margin-top: 8px">
					<sitemesh:write property='div.template.left.up.icon' />
				</div>
				<div width="90%">
					<span
						style="font-size: 12; float: left; position: relative; line-height: 30px; padding-left: 2px">
						<sitemesh:write property='div.template.left.up' />
					</span>
				</div>
			</div>
		</div>
		<div id="treeToolbar"
			style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #D6D6D6;"></div>
		<div id="treeContainer"
			style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
			<ul id="tree"
				style="font-size: 12; background-color: #FFFFFF; width: 92%"
				class="ztree"></ul>
		</div>
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
			<tr>
				<td height="33"
					style="font-size: 12px; border-top: 1px solid #D6D6D6;"><div
						class="form-bar">
						<div class="form-bar-inner"></div>
					</div></td>
			</tr>
		</table>
	</div>
</body>
</html>
