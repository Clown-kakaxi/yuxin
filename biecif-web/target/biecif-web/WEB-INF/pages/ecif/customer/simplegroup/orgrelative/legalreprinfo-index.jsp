<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/legalreprinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '法定代表人姓名',
				name : 'legalReprName',
				align : 'center',
				width : 120,
				minWidth : 100
			},{
				display : '法定代表人证件类型',
				name : 'legalReprIdentType',
				align : 'center',
				width : 150,
				minWidth : 120
			} , {
				display : '法定代表人证件号码',
				name : 'legalReprIdentNo',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '法定代表人证件失效日期',
				name : 'legalReprIdentExpiredDate',
				align : 'center',
				width : 150,
				minWidth : 140,
				type : "date"
			}, {
				display : '法定代表人联系方式',
				name : 'legalReprContmeth',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '法定代表人移动电话',
				name : 'legalReprMobile',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '法定代表人固定电话',
				name : 'legalReprTel',
				align : 'center',
				width : 150,
				minWidth : 120
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