<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/customercontact/contmeth/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '联系信息类型',
				name : 'contmethType',
				align : 'left',
				width : 200,
				minWidth : 80
			}, {
				display : '联系信息内容',
				name : 'contmethInfo',
				align : 'center',
				width : 300,
				minWidth : 200
			}, /* {
				display : '联系顺序号',
				name : 'contmethSeq',
				align : 'center',
				width : 150,
				minWidth : 80
			},  */{
				display : '更新时间',
				name : 'lastUpdateTm',
				align : 'center',
				width : 135,
				minWidth : 120,
				type : "date",
				format : 'yyyy-MM-dd hh:mm:ss'
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
			sortName : 'contmethSeq',//第一次默认排序的字段
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