<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/relativecorp/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '关联企业名称',
				name : 'relativeCorpName',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '组织机构代码',
				name : 'orgCode',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '关联关系类型',
				name : 'relationType',
				align : 'center',
				width : 120,
				minWidth : 100
			} , {
				display : '关联关系说明',
				name : 'relationDesc',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '总资产',
				name : 'totalAssets',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '净资产',
				name : 'netAssets',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '总负债',
				name : 'totalDebt',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '净利润',
				name : 'netProfit',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '主要业务收入',
				name : 'mainBusiness',
				align : 'right',
				width : 120,
				minWidth : 100
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