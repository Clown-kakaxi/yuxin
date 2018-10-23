<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/ebankcontract/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '账户',
				name : 'iabAccno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '户名',
				name : 'iabAccname',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '协议类型',
				name : 'iabAgtype',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '协议号',
				name : 'iabAgno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '协议状态',
				name : 'iabStt',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '对公对私标识',
				name : 'iabPcMark',
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