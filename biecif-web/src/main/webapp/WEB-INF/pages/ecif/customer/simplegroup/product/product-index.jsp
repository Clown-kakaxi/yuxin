<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/product/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '产品代码',
				name : 'prodCode',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '产品名称',
				name : 'prodName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品类型',
				name : 'prodType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品简介',
				name : 'prodSummary',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '品牌名称',
				name : 'brandName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '业务性质',
				name : 'busiChar',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品分类',
				name : 'prodClass',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品形态',
				name : 'prodForm',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品状态',
				name : 'prodStat',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '产品专利',
				name : 'prodPatent',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '上线日期',
				name : 'startDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '下线日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '自主营销标识',
				name : 'ownSaleFlag',
				align : 'center',
				width : 100,
				minWidth : 100
			}],
			checkbox : false,
			usePager : true,
			isScroll : true,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'prodCode',//第一次默认排序的字段
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