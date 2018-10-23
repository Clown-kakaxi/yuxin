<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/entinventory/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '价值币种',
				name : 'valuecurrency',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '价值金额',
				name : 'valuesum',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '入库日期',
				name : 'indepotdate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '减值准备总额',
				name : 'reservesum',
				align : 'center',
				width : 130,
				minWidth : 80
			} , {
				display : '存放地点位置',
				name : 'depotadd',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '存货单位',
				name : 'inventoryunit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '存货名称',
				name : 'inventoryname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '存货市场单价',
				name : 'marketunitprice',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '存货帐面单价',
				name : 'bookunitprice',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '存货数量',
				name : 'inventoryamount',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '存货类型',
				name : 'inventorytype',
				align : 'center',
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
				display : '比例',
				name : 'rate',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 130,
				minWidth : 80,
				type : "date"
			}, {
				display : '评估值',
				name : 'evalvalue',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '评估日期',
				name : 'evaldate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '评估机构',
				name : 'evalorg',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '质量状况',
				name : 'qualitystatus',
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