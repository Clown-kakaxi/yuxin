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
		url = "${ctx}/ecif/perother/consinfo/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '会计月份',
				name : 'accountMonth',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '缴费类型',
				name : 'resourceType',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '缴费',
				name : 'consumeAmount',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '缴费总金额',
				name : 'consumeSum',
				align : 'center',
				width : 130,
				minWidth : 120
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