<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/tbshare/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '客户编号',
				name : 'clientNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品内码',
				name : 'internalCode',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '合作商代码',
				name : 'sellerCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : 'TA代码',
				name : 'taCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '产品代码',
				name : 'prdCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '理财账号',
				name : 'assetAcc',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '份额总数',
				name : 'totVol',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '冻结份额',
				name : 'divMode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '长期冻结份额',
				name : 'longFrozenVol',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '组合投资份额',
				name : 'groupVol',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '分红方式',
				name : 'divMode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '红利比例',
				name : 'divRate',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '昨日总份额',
				name : 'ystdyTotVol',
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
				display : '客户类别',
				name : 'clientType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '追加投资标志',
				name : 'appendFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '其他冻结份额',
				name : 'otherFrozen',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '本期收益',
				name : 'income',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '买入成本',
				name : 'cost',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '累计收入',
				name : 'totIncome',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '最后变动日期',
				name : 'lastDate',
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