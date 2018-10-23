<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/creditcontract/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '授信协议号',
				name : 'creditContrId',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '授信合同号',
				name : 'creditContrNo',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '业务品种',
				name : 'businessType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '授信币种',
				name : 'creditCurr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '额度类型',
				name : 'creditType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '额度种类',
				name : 'creditKind',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '是否循环',
				name : 'isLoop',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '额度状态',
				name : 'creditStat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '授信额度',
				name : 'creditLimit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '额度余额',
				name : 'creditBal',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '已占用额度',
				name : 'usedCredit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '可用额度',
				name : 'usableCredit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经办行',
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
				display : '协议起始日',
				name : 'contrStartDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '协议到期日',
				name : 'contrEndDate',
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