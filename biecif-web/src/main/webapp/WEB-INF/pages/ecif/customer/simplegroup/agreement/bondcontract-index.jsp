<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/bondcontract/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '国债托管账号',
				name : 'gztgzh',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '债券代码',
				name : 'zqdm',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账面余额',
				name : 'zmye',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '可用额度',
				name : 'kyed',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '质押额度',
				name : 'zyed',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '冻结额度',
				name : 'djed',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '债券持有状态',
				name : 'zqcyzt',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '余额变动日期',
				name : 'yebdrq',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '债券期次',
				name : 'zqqc',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '付息日期',
				name : 'fxrq',
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
			sortName : 'contrId',//第一次默认排序的字段
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