<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/orginvestinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '投资客户编号',
				name : 'investCustNo',
				align : 'left',
				width : 120,
				minWidth : 100
			}, {
				display : '被投资客户编号',
				name : 'investedCustNo',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '被投资企业名称',
				name : 'investedCustName',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '被投资企业组织机构代码',
				name : 'investedCustOrgCode',
				align : 'center',
				width : 160,
				minWidth : '140'
			} , {
				display : '被投资人贷款卡编码',
				name : 'investedLoanCardNo',
				align : 'center',
				width : 150,
				minWidth : 130
			}, {
				display : '被投资人注册登记编号',
				name : 'investedCustRegNo',
				align : 'center',
				width : 150,
				minWidth : 130
			}, {
				display : '被投资人法定代表人',
				name : 'investedCustLegalRepr',
				align : 'center',
				width : 150,
				minWidth : 130
			}, {
				display : '被投资人组织机构代码',
				name : 'investedCustOrgCode',
				align : 'center',
				width : 150,
				minWidth : 130
			}, {
				display : '被投资人注册地址',
				name : 'investedCustRegAddr',
				align : 'center',
				width : 180,
				minWidth : 150
			}, {
				display : '投向企业贷款卡状态',
				name : 'investedLoanCardStat',
				align : 'center',
				width : 150,
				minWidth : 110
			}, {
				display : '对外投资币种',
				name : 'currsign',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '对外投资金额',
				name : 'inveamt',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '投资情况记录编号',
				name : 'keyno',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '投资占比',
				name : 'stockPerc',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '投资方式',
				name : 'invekind',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '第一年投资收益',
				name : 'firstYearInvestIncome',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '投资日期',
				name : 'invedate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '是否第一大股东',
				name : 'isLargestSh',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '实际金额',
				name : 'oughtsum',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '应投实际金额',
				name : 'investmentsum',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '股权证号',
				name : 'stockcertno',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '投资收益',
				name : 'investyield',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '担任职务',
				name : 'duty',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担任该职务时间',
				name : 'holddate',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '相关行业从业年限',
				name : 'engageterm',
				align : 'center',
				width : 120,
				minWidth : 110
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