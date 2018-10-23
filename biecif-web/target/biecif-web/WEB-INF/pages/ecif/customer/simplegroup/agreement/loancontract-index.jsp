<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/loancontract/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '产品代码',
				name : 'prodCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同类型',
				name : 'contrType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同编号',
				name : 'contrNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同状态',
				name : 'contrStat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同币种',
				name : 'contrCurr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同金额',
				name : 'contrAmt',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同种类',
				name : 'contrKind',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同期限',
				name : 'contrLimit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同签订日',
				name : 'contrSignDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '合同生效日',
				name : 'contrEffectiveDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '合同到期日',
				name : 'contrEndDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '经办机构',
				name : 'branchNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经办人',
				name : 'tellerNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经办日期',
				name : 'operDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '客户经理',
				name : 'custManager',
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