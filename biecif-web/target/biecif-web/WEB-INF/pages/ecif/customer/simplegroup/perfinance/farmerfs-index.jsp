<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perfinance/farmerfs/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '源系统流水号',
				name : 'serialNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '报表月份',
				name : 'recordmonth',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '类型1',
				name : 'type1',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '类型2',
				name : 'type2',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '类型3',
				name : 'type3',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '类型4',
				name : 'type4',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '类型5',
				name : 'type5',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '类型6',
				name : 'type6',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '类型7',
				name : 'type7',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '数量1',
				name : 'num1',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '数量2',
				name : 'num2',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '数量3',
				name : 'num3',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '数量4',
				name : 'num4',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '数量5',
				name : 'num5',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '数量6',
				name : 'num6',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '属性1',
				name : 'attribute1',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '总价',
				name : 'sum',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '单价',
				name : 'sum1',
				align : 'right',
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
			sortName : 'id.serialNo',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			toolbar : {}
		});

	});
</script>

<title>个人资产(农金)</title>
</head>
<body>
</body>
</html>