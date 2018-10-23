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
	href="${ctx}/css/classics/template/template7.css" />
<script type="text/javascript">
    $(function() {
	templateshow();
    });
    function templateshow() {
	var $content = $(document);
	$("#all").height($content.height() - 40);
	$("#right").height($content.height() - 40);
	$("#left").height($content.height() - 40);
	$("#tab").height($content.height() - 40);
	$("#bottom").height(30);
	var leftHeight = $("#left").height();
	var $treeContainer = $("#treeContainer");
	$treeContainer.height(leftHeight - 32);
    }
</script>
<sitemesh:write property='head' />
</head>
<body>
	<div id="all">
	<div id="left" style="background-color: #FFFFFF; overflow: left; clear: both;">
		<div id="lefttable" width="100%" border="0">
			<div width="100%"
				style="height:30px;background-image: url(${ctx}/css/classics/ligerUI/Gray/images/ui/gridbar.jpg);">
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
			style="width: 100%; overflow: auto;  background-color: #FFFFFF;">
			<ul id="tree"
				style="font-size: 12; background-color: #FFFFFF; width: 92%"
				class="ztree"></ul>
		</div>
	</div>
	<div id="right">
		<div id="tab" style="width: 100%; overflow: hidden;"></div>
	</div>
	</div>	
	<div id="bottom">
		<div class="form-bar">
			<div class="form-bar-inner" style="padding-top: 5px;"></div>
		</div>
	</div>
</body>
</html>
