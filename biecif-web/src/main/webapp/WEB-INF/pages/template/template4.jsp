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
	href="${ctx}/css/classics/template/template4.css" />
<script type="text/javascript">
    $(function() {
	templateshow();
    });
    function templateshow() {
	var $content = $(document);
	$("#all").height($content.height() - 60);
	$("#left").height($content.height() - 60);
	$("#center").height($content.height() - 60);
	$("#right").height($content.height() - 60);
	$("#bottom").height(40);
	var rightHeight = $("#right").height();
	var centerHeight = $("#center").height();
	var leftHeight = $("#left").height();
	var $leftTreeContainer = $("#leftTreeContainer");
	//26px是.l-treetoolbar中height
	$leftTreeContainer.height(leftHeight - 32 - 26);
	var $centerTreeContainer = $("#centerTreeContainer");
	$centerTreeContainer.height(centerHeight - 32);
	var $rightTreeContainer = $("#rightTreeContainer");
	$rightTreeContainer.height(rightHeight - 32);
    }
</script>
<sitemesh:write property='head' />
</head>
<body style="text-align: center;">
	<div id="all">
		<div id="left" style="background-color: #FFFFFF">
			<div id="lefttable" width="100%" border="0">
				<div width="100%"
					style="height:30px;background-image: url(${ctx}/css/classics/ligerUI/Gray/images/ui/gridbar.jpg);border-bottom:1px solid #D6D6D6;">
					<div width="8%"
						style="padding-left: 10px; float: left; position: relative; height: 20p; margin-top: 8px">
						<sitemesh:write property='div.template.left.up.icon' />
					</div>
					<div width="30%">
						<span
							style="font-size: 12; float: left; position: relative; line-height: 30px; padding-left: 2px">
							<sitemesh:write property='div.template.left.up' />
						</span>
					</div>
					<div width="60%"> 
						<div
							style="float: right; position: relative; padding-right: 3px; padding-top:4px;">
							<sitemesh:write property='div.template.left.up.right' />
						</div>
					</div>
				</div>
			</div>
			<div id="treeToolbar"
				style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #D6D6D6;"></div>
			<div id="leftTreeContainer"
				style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
				<ul id="leftTree"
					style="font-size: 12; background-color: #FFFFFF; width: 92%"
					class="ztree"></ul>
			</div>
		</div>
		<div id="center" style="background-color: #FFFFFF">
			<sitemesh:write property='div.template.center' />
		</div>
		<div id="right" style="background-color: #FFFFFF">
			<sitemesh:write property='div.template.right' />
		</div>
	</div>
	<div id="bottom">
		<sitemesh:write property='div.template.bottom' />
	</div>
</body>
</html>
