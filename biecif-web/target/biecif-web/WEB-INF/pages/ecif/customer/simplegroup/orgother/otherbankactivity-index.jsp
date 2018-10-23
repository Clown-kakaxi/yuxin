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
		url = "${ctx}/ecif/orgother/otherbankactivity/list.json?custId="
				+ custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '协议内容',
				name : 'info',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '在他行不良记录及原因',
				name : 'otherBadRecord',
				align : 'center',
				width : 150,
				minWidth : 150
			}, {
				display : '发生行',
				name : 'occurOrg',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '业务类型',
				name : 'businessType',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '币种',
				name : 'currency',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '金额',
				name : 'businessSum',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '五级分类',
				name : 'classifyResult',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '起始日期',
				name : 'beginDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '到期日',
				name : 'maturity',
				align : 'center',
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