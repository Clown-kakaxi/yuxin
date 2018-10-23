<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/guarantycontract/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '产品标识',
				name : 'prodCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '合同编号',
				name : 'contrNo',
				align : 'center',
				width : 130,
				minWidth : 100
			},{
				display : '合同类型',
				name : 'contrType',
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
				display : '签订日期',
				name : 'signDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '生效日期',
				name : 'effectiveDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '到期日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '担保类型',
				name : 'guarantyType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '保证方式',
				name : 'assureType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '保证形式',
				name : 'assureForm',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担保币种',
				name : 'guarantyCurr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担保人编号',
				name : 'guarantorNo',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '担保人名称',
				name : 'guarantorName',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '担保人证件类型',
				name : 'guarantorIdentType',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '担保人证件号码',
				name : 'guarantorIdentNo',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '被担保人编号',
				name : 'warranteeNo',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '被担保人名称',
				name : 'warranteeName',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '被担保人证件类型',
				name : 'warranteeIdentType',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '被担保人证件号码',
				name : 'warranteeIdentNo',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '担保总金额',
				name : 'totalGuaranteeAmt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '担保金额',
				name : 'guaranteeAmt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '已用担保金额',
				name : 'usedGuaranteeAmt',
				align : 'right',
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