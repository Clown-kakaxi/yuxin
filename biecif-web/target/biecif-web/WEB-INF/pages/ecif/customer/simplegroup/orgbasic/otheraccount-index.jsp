<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgbasic/otheraccount/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '普通结算账户账号',
				name : 'acctNo',
				align : 'left',
				width : 120,
				minWidth : 120
			}, {
				display : '普通结算账户户名',
				name : 'acctName',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '普通结算账户开户时间',
				name : 'acctOpenDate',
				align : 'center',
				width : 150,
				minWidth : 130,
				type : "date"
			}, {
				display : '普通结算账户开户机构',
				name : 'acctOpenBank',
				align : 'center',
				width : 150,
				minWidth : 130
			} , {
				display : '他行账户行',
				name : 'otherBank',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '他行账户',
				name : 'otherAcctNo',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '他行账户户名',
				name : 'otherAcctName',
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