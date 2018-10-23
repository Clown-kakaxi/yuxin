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
		url = "${ctx}/ecif/orgmanage/belongbranch/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '客户归属机构编码',
				name : 'brccode1',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '客户归属机构名称',
				name : 'brcname1',
				align : 'left',
				width : 180,
				minWidth : 150
			}, {
				display : '客户管户机构编码',
				name : 'brccode2',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '客户管户机构名称',
				name : 'brcname2',
				align : 'left',
				width : 180,
				minWidth : 150
			}, {
				display : '主协办类型',
				name : 'mainType',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '归属类型',
				name : 'belongType',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '客户来源（介绍人）',
				name : 'custSource',
				align : 'center',
				width : 150,
				minWidth : 150
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