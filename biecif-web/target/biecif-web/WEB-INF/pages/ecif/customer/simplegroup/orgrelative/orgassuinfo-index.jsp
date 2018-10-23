<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/orgassuinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '被担保人名称',
				name : 'assucustname',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '被担保人证件类型',
				name : 'assuidenttype',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '被担保人证件号码',
				name : 'assuidentid',
				align : 'center',
				width : 150,
				minWidth : 120
			} , {
				display : '被担保人类型',
				name : 'assucusttype',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '是否最高额担保',
				name : 'maxassuflag',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '债权银行',
				name : 'debtbank',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担保金额',
				name : 'assuamt',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '担保余额',
				name : 'assubal',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '担保业务种类',
				name : 'assukindname',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '保证笔数',
				name : 'assunum',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '业务形态',
				name : 'assuprod',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担保起始日期',
				name : 'begindate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '担保关系',
				name : 'eachassuflag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担保到期日期',
				name : 'enddate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '是否对外提供担保',
				name : 'assuflag',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '是否有本行贷款',
				name : 'hasLoan',
				align : 'center',
				width : 120,
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