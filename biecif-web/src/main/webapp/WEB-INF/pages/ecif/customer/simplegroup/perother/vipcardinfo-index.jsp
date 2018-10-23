<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var testId;
	var custId = "${custId}";
	$(init);

	function init() {
		url = "${ctx}/ecif/perother/vipcardinfo/list.json?custId="
				+ custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '贵宾卡类型',
				name : 'vipCardType',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '贵宾卡卡号',
				name : 'vipCardNo',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '贵宾卡级别',
				name : 'vipCardLevel',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '贵宾卡发卡机构',
				name : 'vipCardBrccode',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '开始日期',
				name : 'startDate',
				align : 'left',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '结束日期',
				name : 'endDate',
				align : 'left',
				width : 100,
				minWidth : 100,
				type : "date"
			} ],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'vipCardId', //第一次默认排序的字段
			sortOrder : 'asc',
			toolbar : {}
		});
	}
</script>
</head>
<body>
</body>
</html>