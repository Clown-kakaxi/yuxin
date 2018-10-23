<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template1.jsp">
<head>
<script type="text/javascript">
	var grid;
	
	$(function() {
		BIONE.showLoading();
		// 调整属性
		$("#mainsearch").css("overflow", "hidden").empty();
		$("#center").css("overflow", "auto");
		initLabel();
	});
	// 初始化grid
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			url : "${ctx}/bione/label/apply/exmaple2/obj.json",
			columns : [{
				display : '标识',
				name : 'labelObjNo',
				width : "30%",
				align : 'left'
			}, {
				display : '名称',
				name : 'labelObjName',
				width : "30%",
				align : 'left'
			}, {
			  	display : '备注',
				name : 'remark',
				width : "30%",
				align : 'left'
			}],
			dataAction : 'server', //从后台获取数据
			delayLoad : true, // 延迟加载
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			method : 'get',
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			rownumbers : true
		});
	}
	
	// 点击标签事件
	function onAfterClickLabel(labelArray) {
		var labels = JSON2.stringify(labelArray);
		grid.set("parms", {
			labels: labels
		});
		grid.loadData();
	}
	
	// 初始化标签页面
	function initLabel() {
		window.LABEL = {
			url: "${ctx}/bione/label/apply/labelOfObj.json", // ajax加载标签路径
			data: {
				labelObjId: "${labelObjId}"
			},
			onReady: f_ready, // 标签面板加载完毕回调函数
			onAfterClickLabel: onAfterClickLabel, // 标签点击回调函数
			onClickExt: f_click_ext // ‘收起/展开’按钮点击回调函数
		};
		
		// 添加标签选择页面
		var frame = $('<iframe/>').attr({
			id : "frame",
			src : "${ctx}/bione/label/apply/labelCheck",
			frameborder : "0"
		}).css({
			width : "100%",
			height: $(document).height() - 100
		});
		$('#mainsearch').append(frame);
	}
	
	// label页面加载完成事件
	function f_ready(height) {
		initGrid();
		if (height) {
			grid.setHeight($("#center").height() - height - 2);
			$('#mainsearch').height(height);
			if (height > 140) {
				grid.setHeight($("#center").height() - 140 - 2);
			}
		}
		LABEL.setWidth();// setWidth(width) 重新设置面板宽度
		grid.loadData();
		BIONE.hideLoading();
	}
	
	// ‘收起/展开’按钮点击回调函数
	function f_click_ext(height) {
		grid.setHeight($("#center").height() - height - 2);
		$('#mainsearch').height(height);
		if (height > 140) {
			grid.setHeight($("#center").height() - 140 - 2);
		}
		LABEL.setWidth();
	}
</script>
</head>
<body>
</body>
</html>