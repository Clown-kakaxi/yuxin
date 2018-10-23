<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perfinance/personbusiinfo/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '经营项目名称',
				name : 'busiItemName',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "经营项目描述",
			name : "busiItemDesc",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "经营业务类型",
			name : "busiItemType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "经营项目地址",
			name : "busiItemAddress",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "是否共有所有权",
			name : "isCorpOwnership",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "合伙人数",
			name : "partnerNum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "所有权百分比（%）",
			name : "stockPercent",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "资产总额",
			name : "assetsAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "负债总额",
			name : "debtAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "应收账款",
			name : "inAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "应付账款",
			name : "outAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "年销售收入",
			name : "yearSaleIncome",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "年支出",
			name : "yearPay",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "年利润",
			name : "yearProfit",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "月销售收入",
			name : "monthSaleIncome",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "月支出",
			name : "monthPay",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "月结余情况",
			name : "monthProfit",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "上年销售额",
			name : "lastYearSaleAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "上月销售额",
			name : "lastMonthSaleAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "日最高销售额",
			name : "dayMaxSaleAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "日最低销售额",
			name : "dayMinSaleAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最后更新系统",
			name : "lastUpdateSys",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最后更新人",
			name : "lastUpdateUser",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最后更新时间",
			name : "lastUpdateTm",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "交易流水号",
			name : "txSeqNo",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	],
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

