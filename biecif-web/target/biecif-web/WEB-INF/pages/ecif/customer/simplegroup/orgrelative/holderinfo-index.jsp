<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/holderinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '股东名称',
				name : 'holdcustname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '股东类型',
				name : 'holdcusttype',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '行业任职',
				name : 'industryposition',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '是否存在违规违纪现象',
				name : 'isoffenceflag',
				align : 'center',
				width : 150,
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