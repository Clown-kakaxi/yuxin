<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/bdshracctinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '债券帐户号',
				name : 'acctNo',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '款项号',
				name : 'subAcct',
				align : 'center',
				width : 150,
				minWidth : 120
			},{
				display : '存款帐号',
				name : 'acctNo1',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '产品名称',
				name : 'prdCode',
				align : 'center',
				width : 180,
				minWidth : 150
			}, {
				display : '帐户状态',
				name : 'accStat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开户日期',
				name : 'openDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '开户机构编号',
				name : 'openBrc',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '开户员工编号',
				name : 'teller1',
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
			sortName : 'contrId',//第一次默认排序的字段
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