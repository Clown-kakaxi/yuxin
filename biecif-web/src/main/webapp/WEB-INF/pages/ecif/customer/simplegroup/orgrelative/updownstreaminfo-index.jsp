<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/updownstreaminfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '上下游企业类型',
				name : 'salesupplytype',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '上下游客户名称',
				name : 'salesupplycustname',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '年平均交易额',
				name : 'yearamt',
				align : 'right',
				width : 120,
				minWidth : 100
			} , {
				display : '负责人电话',
				name : 'finatel',
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