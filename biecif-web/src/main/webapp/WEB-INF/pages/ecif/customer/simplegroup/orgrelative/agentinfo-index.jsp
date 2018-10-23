<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/agentinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '代理人姓名',
				name : 'agentName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '代理人证件类型',
				name : 'agentIdentType',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '代理人证件号码',
				name : 'agentIdentNo',
				align : 'center',
				width : 150,
				minWidth : 120
			} , {
				display : '代理 人证件失效日期',
				name : 'agentIdentExpiredDate',
				align : 'center',
				width : 120,
				minWidth : 120,
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