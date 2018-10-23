<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/executiveinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '姓名',
				name : 'name',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '证件类型',
				name : 'identType',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '证件号码',
				name : 'identNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '性别',
				name : 'gender',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '出生日期',
				name : 'birthday',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '最高学历',
				name : 'highestSchooling',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '婚姻状况',
				name : 'marriage',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '任职部门',
				name : 'dept',
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