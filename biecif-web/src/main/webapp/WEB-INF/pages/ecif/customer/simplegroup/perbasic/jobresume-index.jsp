<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perbasic/jobresume/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '单位代码',
				name : 'unitCode',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '单位性质',
				name : 'unitChar',
				align : 'center',
				width : 150,
				minWidth : 80
			}, {
				display : '所在单位',
				name : 'workUnit',
				align : 'left',
				width : 150,
				minWidth : 120
			}, {
				display : '所在部门',
				name : 'workDept',
				align : 'center',
				width : 150,
				minWidth : 120
			} , {
				display : '担任职务',
				name : 'position',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '单位电话',
				name : 'unitTel',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '单位地址',
				name : 'unitAddress',
				align : 'left',
				width : 150,
				minWidth : 120
			}, {
				display : '单位邮编',
				name : 'unitZipcode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开始日期',
				name : 'startDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '结束日期',
				name : 'endDate',
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