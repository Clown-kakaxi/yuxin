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
		url = "${ctx}/ecif/permanage/belonglinedebt/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '归属部门',
				name : 'belongDept',
				align : 'left',
				width : 250,
				minWidth : 200
			}],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'custId', //第一次默认排序的字段
			sortOrder : 'asc',
			toolbar : {}
		});
	}
</script>
</head>
<body>
</body>
</html>