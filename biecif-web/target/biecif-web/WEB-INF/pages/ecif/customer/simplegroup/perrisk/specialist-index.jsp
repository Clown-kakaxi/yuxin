<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perrisk/specialist/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '特殊名单类型',
				name : 'specialListType',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '特殊名单标志',
				name : 'specialListFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件类型',
				name : 'identType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件号码',
				name : 'identNo',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '证件户名',
				name : 'identCustName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '列入原因',
				name : 'enterReason',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '状态标志',
				name : 'statFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '起始日期',
				name : 'startDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '结束日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
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