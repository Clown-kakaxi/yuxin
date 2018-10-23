<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/customerimasset/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '入帐价值',
				name : 'accountvalue',
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
				display : '统计截止日期',
				name : 'uptodate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			} , {
				display : '认证单位',
				name : 'authorg',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '认证日期',
				name : 'authdate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '证书编号',
				name : 'authno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '评估价值',
				name : 'evaluatevalue',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '评估方法',
				name : 'evaluatemethod',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '资产名称',
				name : 'assetname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '资产简述',
				name : 'assetdescribe',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '资产类型',
				name : 'assettype',
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