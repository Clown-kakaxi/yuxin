<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/smeinveinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '买入/租赁价格',
				name : 'purchaseprice',
				align : 'left',
				width : 130,
				minWidth : 80
			}, {
				display : '买入/租赁日期',
				name : 'purchasedate',
				align : 'center',
				width : 130,
				minWidth : 80,
				type : "date"
			}, {
				display : '产权证号',
				name : 'prorightno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '卖出日期',
				name : 'saledate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			} , {
				display : '投保公司',
				name : 'insureentname',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '抵押情况',
				name : 'guarantyinfo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '摊位/厂房名称',
				name : 'assetname',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '摊位/厂房地址',
				name : 'assetaddress',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '摊位/厂房性质',
				name : 'assettype',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '摊位/厂房面积',
				name : 'assetarea',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '统计截止日期',
				name : 'statdate',
				align : 'center',
				width : 130,
				minWidth : 80,
				type : "date"
			}, {
				display : '评估价格',
				name : 'evaluateprice',
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