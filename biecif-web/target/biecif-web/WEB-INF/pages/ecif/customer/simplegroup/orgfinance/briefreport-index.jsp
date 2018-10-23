<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var testId;
	var custId = "${custId}";
	$(init);

	function init() {
		url = "${ctx}/ecif/orgfinance/briefreport/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '报表编号',
				name : 'reportNo',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '报表种类',
				name : 'reportKind',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '会计准则类型',
				name : 'accountingRule',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '报表类型',
				name : 'reportType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '报表金额单位',
				name : 'reportUnit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '财务信息细项名称',
				name : 'reportDetail',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '财务信息细项金额',
				name : 'reportDetailAmt',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '资产规模情况',
				name : 'assetsCondition',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '资本充足情况',
				name : 'capitalCondition',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '盈利能力情况',
				name : 'profitability',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '利润监管情况',
				name : 'profitIndex',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '信贷资产质量情况',
				name : 'loanQuality',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '风险情况监管指标',
				name : 'riskMonitorIndex',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '流动性情况监管指标',
				name : 'liquidityMonitorIndex',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '资产总额',
				name : 'totalAssets',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '负债总额',
				name : 'totalDebt',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '审计日期',
				name : 'auditdate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			} , {
				display : '审计单位',
				name : 'auditoffice',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '审计意见',
				name : 'auditoinion',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '审计标志',
				name : 'auditflag',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '是否有保留意见',
				name : 'hasReserveOpt',
				align : 'center',
				width : 120,
				minWidth : 110
			}  ],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'custId', //第一次默认排序的字段
			sortOrder : 'asc',
			toolbar : {}
		});
	}
</script>
</head>
<body>
</body>
</html>