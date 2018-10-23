<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/ebanksignaccount/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '银行账号',
				name : 'bankAcc',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '银行编号',
				name : 'bankNo',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '客户编号',
				name : 'clientNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '客户类别',
				name : 'clientType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '签约日期',
				name : 'transDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '解约日期',
				name : 'signoffDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '签约机构',
				name : 'branchNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开卡机构',
				name : 'openBranch',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '签约状态',
				name : 'status',
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