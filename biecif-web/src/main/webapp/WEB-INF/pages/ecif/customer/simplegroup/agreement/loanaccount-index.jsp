<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/loanaccount/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '产品代码',
				name : 'prodCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账户类型',
				name : 'acctType',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '账户账号',
				name : 'acctNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账户状态',
				name : 'acctStat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账户币种',
				name : 'acctCurr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账户余额',
				name : 'acctBal',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开户日期',
				name : 'openAcctDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '销户日期',
				name : 'cancelAcctDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '五级分类',
				name : 'level5Classify',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '十二级分类',
				name : 'level12Classify',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开户机构编码',
				name : 'openAcctBranchNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '销户机构编码',
				name : 'cancelAcctBranchNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '借据号',
				name : 'duebillNo',
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