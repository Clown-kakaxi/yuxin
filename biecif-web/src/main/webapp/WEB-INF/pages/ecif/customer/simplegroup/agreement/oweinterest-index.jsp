<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/oweinterest/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '借据号',
				name : 'duebillno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '序号',
				name : 'flow',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '机构代码',
				name : 'instcode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '客户代码',
				name : 'cstcode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同编号',
				name : 'contractno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账号代号',
				name : 'acnt',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '货币种类',
				name : 'curkind',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '欠息金额',
				name : 'owesum',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '利息种类',
				name : 'intetype',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '欠息日期',
				name : 'owedate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '操作员代号',
				name : 'opcode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '操作日期',
				name : 'opdate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '传输标志',
				name : 'trflag',
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