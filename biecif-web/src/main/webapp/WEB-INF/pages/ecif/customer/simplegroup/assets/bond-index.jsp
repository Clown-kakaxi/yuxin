<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/bond/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '上市交易所名称',
				name : 'exchangeName',
				align : 'center',
				width : 140,
				minWidth : 120
			}, {
				display : '债券名称',
				name : 'bondName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '债券种类',
				name : 'bondKind',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '利率',
				name : 'rate',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '到期日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '卖出(兑付)日期',
				name : 'saleDate',
				align : 'center',
				width : 140,
				minWidth : 120,
				type : "date"
			}, {
				display : '币种',
				name : 'currency',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开始时间',
				name : 'startDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '数量',
				name : 'num',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '金额',
				name : 'amt',
				align : 'center',
				width : 100,
				minWidth : 80
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