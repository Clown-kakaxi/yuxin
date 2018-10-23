<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<%@ include file="/common/wijmo.jsp"%>

<script type="text/javascript">
	var grid = null;
	var msg = "该时间范围内有没数据！";

	$(function() {
		 $("#navtab1").ligerTab();
		templateshow();
		templateinit();

	});
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
					var centerHeight = $("#center").height();
					if ($(this).hasClass("togglebtn-down")) {
						$(this).removeClass("togglebtn-down");
						searchbox.slideToggle('fast', function() {
							$("#analysis").height(centerHeight - $("#mainsearch").height() - 10);
							$("#showPic").height(centerHeight - $("#mainsearch").height() - 20);
							if (grid) {
								grid.setHeight(centerHeight
										- $("#mainsearch").height()
										- 10);
							}
						});
					} else {
						$(this).addClass("togglebtn-down");
						searchbox.slideToggle('fast', function() {
							$("#analysis").height(centerHeight - $("#mainsearch").height() - 5);
							$("#showPic").height(centerHeight - $("#mainsearch").height() - 10);	
							if (grid) {
								grid.setHeight(centerHeight
										- $("#mainsearch").height()
										- 5);
							}
						});
					}
				});
	}
	function templateshow() {
		$("#center").height($(document).height() - 30);
		if (grid) {
			var centerHeight = $("#center").height();
			grid.setHeight(centerHeight - $("#mainsearch").height() - 10);
			$("#analysis").height(centerHeight - $("#mainsearch").height() - 10);
			$("#showPic").height(centerHeight - $("#mainsearch").height() - 20);	
		}
	}

	addSearchButtonsInAnalysise = function(form, grid, btnContainer, fn) {
		if (!form)
			return;
		form = $(form);
		if (btnContainer) {
			BIONE.createButton({
				appendTo : btnContainer,
				text : '搜索',
				icon : 'search3',
				width : '50px',
				click : function() {
					var mainform = $("form:first");
					if(mainform.valid()){
						fn();
					}
				}
			});
			BIONE.createButton({
						appendTo : btnContainer,
						text : '重置',
						icon : 'refresh2',
						width : '50px',
						click : function() {
							$(":input", form)
									.not(
											":submit, :reset,:hidden,:image,:button, [disabled]")
									.each(function() {
										$(this).val("");
									});
							$(":input[ltype=combobox]", form).each(function() {
								$(this).val("");
							});
						}
					});
		}
	};

	function loadData(url, data, fn) {
		$.ajax({
			cache : false,
			async : true,
			url : url,
			dataType : 'json',
			data : data,
			type : "post",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success : function(result) {
				var mainform = $("form:first");
				if(mainform.valid()){
					fn(result);
				}
				
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
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
	<div id="center">
		<div id="mainsearch">
			<div class="searchtitle">
				<img src="${ctx}/images/classics/icons/find.png" /> <span>搜索</span>
				<div class="togglebtn">&nbsp;</div>
			</div>
			<!-- <div class="navline" style="margin-bottom: 4px;"></div> -->
			<div id="searchbox" class="searchbox">
				<div id="formsearch">
					<form id="search"></form>
					<div class="l-clear"></div>
				</div>
				<div id="searchbtn" class="searchbtn"></div>
			</div>
		</div>


		<div class="content">
			<div id="navtab1">
				<div tabid="home" title="图表展示" lselected="true"  >
					<div id="analysis"style="border:solid 1px; border-color: #D6D6D6; margin-bottom: 2px; background-color: white;">
						<sitemesh:write property='div.template.analysis' />
					</div>
				</div>
				<div tabid="tab1" title="数据展示">
					<div id="maingrid" class="maingrid"></div>
				</div>
			</div>


		</div>
	</div>
</body>
</html>