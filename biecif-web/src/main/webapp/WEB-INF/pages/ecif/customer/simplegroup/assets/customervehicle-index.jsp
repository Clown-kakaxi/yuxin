<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/customervehicle/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '买入日期',
				name : 'purchasedate',
				align : 'left',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '付款情况',
				name : 'purchasestate',
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
			}, {
				display : '发动机号',
				name : 'engineid',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '更新日期',
				name : 'updatedate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 120,
				minWidth : 100,
				type : "date"
			}, {
				display : '购买金额',
				name : 'purchasesum',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '车架号',
				name : 'underspanid',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '车辆品牌',
				name : 'vehiclebrand',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '车辆概况',
				name : 'vehiclesituation',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '车辆牌号',
				name : 'vehiclelicense',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '车辆合格证',
				name : 'vehiclecert',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '行驶证号码',
				name : 'licenseno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '机动车登记证标志',
				name : 'vehicleregflag',
				align : 'center',
				width : 140,
				minWidth : 100
			}, {
				display : '已行驶公里',
				name : 'runkm',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '每月还款',
				name : 'monthrepayment',
				align : 'right',
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