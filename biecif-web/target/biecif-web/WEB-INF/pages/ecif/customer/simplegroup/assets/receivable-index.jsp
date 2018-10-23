<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/receivable/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '商品/服务名称',
				name : 'productServiceName',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '应收账款债务人名称',
				name : 'receivableDebtorName',
				align : 'center',
				width : 140,
				minWidth : 120
			}, {
				display : '应收账款账龄',
				name : 'receivableAging',
				align : 'center',
				width : 120,
				minWidth : 100
			} , {
				display : '应收账款金额',
				name : 'receivableAmt',
				align : 'center',
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