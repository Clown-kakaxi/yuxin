<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/customerrealty/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '产权证号',
				name : 'certificateno',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '买入日期',
				name : 'purchasedate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '卖出日期',
				name : 'saledate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '建构价格',
				name : 'buildprice',
				align : 'right',
				width : 100,
				minWidth : 80
			} , {
				display : '房产抵押情况',
				name : 'mortagage',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '房屋名称',
				name : 'realtyname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '房屋地址',
				name : 'realtyadd',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '房屋性质',
				name : 'realtyattribute',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '房屋面积',
				name : 'realtyarea',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '所占份额',
				name : 'shareprop',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '更新日期',
				name : 'updatedate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 130,
				minWidth : 80,
				type : "date"
			}, {
				display : '评估价格',
				name : 'evaluateprice',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '房屋楼层',
				name : 'realtyfloor',
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
			sortName : 'holdingId',//第一次默认排序的字段
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