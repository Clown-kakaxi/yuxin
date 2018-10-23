<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/seller/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '主要销售商名称',
				name : 'mainsellername',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '经营项目名称',
				name : 'dealprojname',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '所在地',
				name : 'location',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '应收账款余额',
				name : 'receamt',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '支付日期',
				name : 'paydate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '联系电话',
				name : 'tel',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '简介',
				name : 'note',
				align : 'center',
				width : 180,
				minWidth : 150
			} ],
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