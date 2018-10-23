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
	href="${ctx}/css/classics/template/template11.css" />
<script type="text/javascript">
	var grid = null;
	var rightHeight;
	$(function() {
		templateshow();
		templateinit();
	});
	function templateshow() {
		var $content = $(document);
		$("#right").height($content.height() - 10);
		$("#left").height($content.height() - 10);
		if (grid) {
			rightHeight = $("#right").height();
			grid.setHeight(rightHeight - $("#mainsearch").height()-4);
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
		$(".searchtitle .togglebtn").live(
				'click',
				function() {
					var searchbox = $(this).parent().nextAll(
							"div.searchbox:first");
					if ($(this).hasClass("togglebtn-down")) {
						$(this).removeClass("togglebtn-down");
						searchbox.slideToggle('fast', function() {

							if (grid) {
								grid.setHeight(rightHeight
										- $("#mainsearch").height()-4);
							}
						});
					} else {
						$(this).addClass("togglebtn-down");
						searchbox.slideToggle('fast', function() {

							if (grid) {
								grid.setHeight(rightHeight
										- $("#mainsearch").height()+1);
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
		<sitemesh:write property='div.template.left' />
	</div>
	<div id="right">
		<div id="mainsearch" >
			<div class="searchtitle" >
			<div>
				<img src="${ctx}/images/classics/icons/find.png" /> <span>搜索</span>
			</div>
				<div class="togglebtn"></div>
			</div>
			<!-- <div class="navline" style="margin-bottom: 4px;"></div> -->
			<div id="searchbox" class="searchbox" >
				<form id="formsearch">
					<div id="search"></div>
					<div class="l-clear"></div>
				</form>
				<div id="searchbtn" class="searchbtn"></div>
			</div>
		</div>
		<div class="content" >
			<div id="maingrid" class="maingrid"></div>
		</div>
	</div>
</body>
</html>
