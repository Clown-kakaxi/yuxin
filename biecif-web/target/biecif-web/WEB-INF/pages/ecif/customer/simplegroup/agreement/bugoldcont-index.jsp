<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/bugoldcont/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '签约状态',
				name : 'contstat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '黄金账号',
				name : 'goldacctno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '账号',
				name : 'acctno',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '账户名称',
				name : 'custname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件类型',
				name : 'idtype',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件号码',
				name : 'idno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '手机号码',
				name : 'mobphone',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '联系电话',
				name : 'tel',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '邮政编码',
				name : 'zipcode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '地址',
				name : 'addr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '币种代码',
				name : 'ccy',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '客户类型',
				name : 'custtype',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开户机构码',
				name : 'opnacctbrc',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '电子邮件地址',
				name : 'email',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '传真',
				name : 'faxno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '中心代码',
				name : 'centercode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '黄金账户密码',
				name : 'goldacctpwd',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '品种名称',
				name : 'kindname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '上金所服务电话',
				name : 'srvtel',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '上金所网址',
				name : 'website',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '前置流水号',
				name : 'baspstanno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '前置交易日期',
				name : 'baspdate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '前置交易时间',
				name : 'basptime',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '机构码',
				name : 'bnkinstid',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '柜员',
				name : 'bnkuserid',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '解约前置流水号',
				name : 'ccbaspstanno',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '解约前置交易日期',
				name : 'ccbaspdate',
				align : 'center',
				width : 120,
				minWidth : 120,
				type : "date"
			}, {
				display : '解约前置交易时间',
				name : 'ccbasptime',
				align : 'center',
				width : 120,
				minWidth : 120,
				type : "date"
			}, {
				display : '解约机构码',
				name : 'ccbnkinstid',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '解约柜员',
				name : 'ccbnkuserid',
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