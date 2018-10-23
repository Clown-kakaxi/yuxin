<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perrelative/perforeassu/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '被担保人编号',
				name : 'assucustNo',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '被担保人姓名',
				name : 'assucustName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '被担保人证件类型',
				name : 'paperkind',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '被担保人证件号码',
				name : 'assupaperId',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '被担保人类型',
				name : 'assucustType',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '债权银行',
				name : 'bankName',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '担保关系',
				name : 'eachassuFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '与担保人关系',
				name : 'relasign',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担保内容/业务形态',
				name : 'assuprod',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '担保金额',
				name : 'assuamt',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '担保余额',
				name : 'assubal',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '月还额款',
				name : 'mretuamt',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '开始日期',
				name : 'beginDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '截止日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '是否不良',
				name : 'isbadFlag',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '是否最高额担保',
				name : 'mostassuFlag',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '担保业务种类',
				name : 'assukindName',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '总担保金额',
				name : 'totalAssuamt',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '是否对外担保',
				name : 'isExternalGuaranty',
				align : 'center',
				width : 100,
				minWidth : 100
			} ],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'assucustNo',//第一次默认排序的字段
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