<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/right/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '使用权年限',
				name : 'rightYearLimit',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '已使用年限',
				name : 'usedYears',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '准入标准',
				name : 'admittanceStd',
				align : 'center',
				width : 110,
				minWidth : 80
			}, {
				display : '经营权批准号码',
				name : 'rightApproNo',
				align : 'center',
				width : 160,
				minWidth : 120
			} , {
				display : '经营权信息描述',
				name : 'rightDesc',
				align : 'center',
				width : 180,
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