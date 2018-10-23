<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/billinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '票据号码',
				name : 'billNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '票据面值',
				name : 'billFaceValue',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '票据币种',
				name : 'billCurrency',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '出票人',
				name : 'drawer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '付款人',
				name : 'payer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '收款人',
				name : 'payee',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '保证人',
				name : 'guarantor',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '被背书人',
				name : 'endorsee',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '追索权标志',
				name : 'recourseflag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '承兑类型',
				name : 'acceptType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '承兑人',
				name : 'acceptor',
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