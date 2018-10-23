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
	href="${ctx}/css/classics/template/template20.css" />
<script type="text/javascript">
    var grid = null;
    $(function() {
	templateshow();
	templateinit();
    });

    function templateshow() {
	$("#right").height($(document).height() - 6);
	$("#left").height($(document).height() - 10);
	$("#treeContainer").height($("#left").height() - 80);
	if (grid) {
	    var rightHeight = $("#right").height();
	    grid.setHeight(rightHeight - $("#mainsearch").height() - 3);
	}
    }
    function templateinit() {
	$(".l-dialog-btn").live('mouseover', function() {
	    $(this).addClass("l-dialog-btn-over");
	}).live('mouseout', function() {
	    $(this).removeClass("l-dialog-btn-over");
	});
	$(".l-dialog-tc .l-dialog-close").live('mouseover', function() {
	    $(this).addClass("l-dialog-close-over");
	}).live('mouseout', function() {
	    $(this).removeClass("l-dialog-close-over");
	});
	$(".searchtitle .templatebtn").live(
		'click',
		function() {
		    var searchbox = $(this).parent().nextAll(
			    "div.searchbox:first");

		    var rightHeight = $("#right").height();
		    if ($(this).hasClass("togglebtn-down")) {
			$(this).removeClass("togglebtn-down");
			searchbox.slideToggle('fast', function() {
			    if (grid) {
				grid.setHeight(rightHeight
					- $("#mainsearch").height() - 8);
			    }
			});
		    } else {
			$(this).addClass("togglebtn-down");
			searchbox.slideToggle('fast', function() {
			    if (grid) {
				grid.setHeight(rightHeight
					- $("#mainsearch").height() - 3);
			    }
			});
		    }
		});
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
	<div id="left">
		<div id="mainsearch">
			<div class="searchtitle">
				<img src="${ctx}/images/classics/icons/find.png" /> <span>搜索</span>
				<div class="templatebtn">&nbsp;</div>
			</div>
			<!-- <div class="navline" style="margin-bottom: 4px;"></div> -->
			<div id="searchbox" class="searchbox" style="width: 99%;">
				<form id="formsearch">
					<div id="search"></div>
					<div class="l-clear"></div>
				</form>
				<div id="searchbtn" class="searchbtn"></div>
			</div>
		</div>
		<div id="maingrup" class="content" style="width: 99%;">
			<div id="maingrid" class="maingrid"></div>
		</div>
	</div>
	<div id="right" style="height:99%; background-color: #FFFFFF;border: 1px solid #D6D6D6;">
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
		<div id="treeToolbar" class="l-panel-topbar l-toolbar" style="width: 99%;height: 22px; padding:1px;"></div>
		<div id="treeContainer"
			style="width: 99%; overflow: auto; clear: both; background-color: #FFFFFF;">
			<ul id="tree"
				style="font-size: 12; background-color: #FFFFFF; width: 92%" 
				class="ztree"></ul>
		</div>
		<div id="treeSetup" class="l-panel-topbar l-toolbar" style="height:30px;border-top: 1px solid #D6D6D6;border-bottom: 1px solid #D6D6D6;" align="center">
			<sitemesh:write property='div.template.treeSetup.down' />
		</div>
		 
	</div>
</body>
</html>
