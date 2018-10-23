<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/asset/otherbankdeposit/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '经办机构名称',
				name : 'operBranchName',
				align : 'center',
				width : 140,
				minWidth : 100
			}, {
				display : '存款种类',
				name : 'depsoitType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '存款金额',
				name : 'amt',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '存款币种',
				name : 'currency',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '存入日期',
				name : 'startDate',
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
				display : '利率',
				name : 'interestRate',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '帐户行',
				name : 'acctBank',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '帐号',
				name : 'acctNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '帐户类型',
				name : 'acctType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '户名',
				name : 'acctName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '存款期限（月）',
				name : 'depositLimit',
				align : 'center',
				width : 140,
				minWidth : 100
			}, {
				display : '积数合计',
				name : 'pdtSum',
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
			sortName : 'holdingId',//第一次默认排序的字段
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