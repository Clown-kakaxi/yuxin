<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/guaranty/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '抵质押物类型',
				name : 'guarantyType',
				align : 'left',
				width : 130,
				minWidth : 80
			}, {
				display : '抵质押物价值',
				name : 'guarantyValue',
				align : 'right',
				width : 130,
				minWidth : 80
			}, {
				display : '抵质押物名称',
				name : 'guarantyName',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '抵质押分类',
				name : 'guarantyCalss',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '抵质押物数量单位',
				name : 'guarantyNumUnit',
				align : 'center',
				width : 150,
				minWidth : 100
			}, {
				display : '抵质押物数量',
				name : 'guarantyNum',
				align : 'right',
				width : 130,
				minWidth : 80
			}, {
				display : '权利证书类型',
				name : 'guarantyCertType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '权利证书号码',
				name : 'guarantyCertNo',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '抵质押物变现能力',
				name : 'guarantyTrunCash',
				align : 'center',
				width : 150,
				minWidth : 100
			}, {
				display : '评估价值',
				name : 'guarantyEvalValue',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '价值确认方式',
				name : 'guarantyEstimKind',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '评估机构名称',
				name : 'evalCorpName',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '抵质押率',
				name : 'guarantyRate',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '是否登记',
				name : 'isRegiFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '是否投保',
				name : 'isInsuFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '是否共有人',
				name : 'isPartOwnerFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '是否公正',
				name : 'isNotaFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经办行',
				name : 'bankId',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经办人',
				name : 'operId',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '抵质押物描述',
				name : 'guarantyDesc',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '抵质押物状态',
				name : 'guarantyStat',
				align : 'center',
				width : 130,
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