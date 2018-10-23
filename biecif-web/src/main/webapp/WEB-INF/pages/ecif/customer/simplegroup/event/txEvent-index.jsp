<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/event/txevent/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '事件类型',
				name : 'eventType',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '事件子类型',
				name : 'subEventType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '事件描述',
				name : 'eventDesc',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '事件发生日期',
				name : 'eventOccurDate',
				align : 'center',
				width : 100,
				minWidth :80
			}, {
				display : '事件登记日期',
				name : 'eventRegDate',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '事件登记人员编号',
				name : 'eventRegTellerNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '操作机构编号',
				name : 'eventRegBrc',
				align : 'center',
				width : 100,
				minWidth : 80
			}],
			checkbox : false,
			usePager : true,
			isScroll : true,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'eventId',//第一次默认排序的字段
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