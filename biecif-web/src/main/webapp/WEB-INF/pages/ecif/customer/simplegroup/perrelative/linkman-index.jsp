<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perrelative/linkman/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '联系人姓名',
				name : 'linkmanName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '联系电话',
				name : 'tel',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '手机号码',
				name : 'mobile',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '联系人证件类型',
				name : 'linkmanIdentType',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '联系人证件号码',
				name : 'linkmanIdentNo',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '联系地址',
				name : 'addr',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '电子邮件地址',
				name : 'email',
				align : 'center',
				width : 120,
				minWidth : 100
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