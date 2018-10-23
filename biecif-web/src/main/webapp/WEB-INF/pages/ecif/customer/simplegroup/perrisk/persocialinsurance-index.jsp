<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perrisk/persocialinsurance/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '源系统流水号',
				name : 'serialNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '社会保险种类',
				name : 'sitype',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '社会保险号码',
				name : 'siaccount',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '余额',
				name : 'sibalance',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
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
			sortName : 'custId',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			toolbar : {}
		});

	});
</script>
</head>
<body>
</body>
</html>