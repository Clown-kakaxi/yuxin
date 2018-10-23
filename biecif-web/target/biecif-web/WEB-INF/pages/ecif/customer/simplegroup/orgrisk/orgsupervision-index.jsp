<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrisk/orgsupervision/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '银监会风险预警信号',
				name : 'cbrcRiskSignal',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '反洗钱风险等级',
				name : 'amlRiskLevel',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '作假行为描述',
				name : 'menddesc',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '处理情况',
				name : 'dealinfo',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '数据来源',
				name : 'datasrcdesc',
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