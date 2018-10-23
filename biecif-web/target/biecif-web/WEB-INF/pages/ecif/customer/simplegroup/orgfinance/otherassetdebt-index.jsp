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
		url = "${ctx}/ecif/orgfinance/otherassetdebt/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '会计科目',
				name : 'financeitem',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '科目内容',
				name : 'adcontent',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '币种',
				name : 'adcurrency',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '金额',
				name : 'adsum',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账龄',
				name : 'adage',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '情况说明',
				name : 'addescribe',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '更新日期',
				name : 'updatedate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 100,
				minWidth : 80,
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