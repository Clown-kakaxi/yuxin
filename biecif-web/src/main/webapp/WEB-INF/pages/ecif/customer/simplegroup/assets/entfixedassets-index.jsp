<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/entfixedassets/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '使用方式',
				name : 'usemethod',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '原值',
				name : 'formervalue',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '固定资产名称',
				name : 'fixedassetsname',
				align : 'center',
				width : 130,
				minWidth : 80
			}, {
				display : '固定资产类型',
				name : 'fixedassetstype',
				align : 'center',
				width : 130,
				minWidth : 80
			} , {
				display : '处所位置',
				name : 'location',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '币种',
				name : 'currency',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '折旧方法',
				name : 'depreciation',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '更新日期',
				name : 'updatedate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			} , {
				display : '权利证书号',
				name : 'certificateno',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 130,
				minWidth : 100,
				type : "date"
			} , {
				display : '综合成新率',
				name : 'rate',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '评估值',
				name : 'evalvalue',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '评估日期',
				name : 'evaldate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			} , {
				display : '评估机构',
				name : 'evalorg',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '资产状态',
				name : 'fixedassetsstatus',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '面积（平方米）',
				name : 'area',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '折旧率',
				name : 'discountrate',
				align : 'center',
				width : 100,
				minWidth : 100
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