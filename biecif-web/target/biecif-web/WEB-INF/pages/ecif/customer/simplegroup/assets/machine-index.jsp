<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/machine/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '规格型号',
				name : 'model',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '生产厂家',
				name : 'provider',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '购置日期',
				name : 'buyDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '价格',
				name : 'price',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '启用日期',
				name : 'usedate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '使用状况',
				name : 'machineStat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '最近一次大修年月',
				name : 'lastMaintainTime',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '出厂日期',
				name : 'produceDate',
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
			sortName : 'holdingId',//第一次默认排序的字段
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