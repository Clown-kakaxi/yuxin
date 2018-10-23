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
		url = "${ctx}/ecif/perother/otherinfo/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '开户银行数',
				name : 'usedBankNum',
				align : 'center',
				width : 200,
				minWidth : 200
			},{
				display : '使用银行名称',
				name : 'usedBankName',
				align : 'center',
				width : 200,
				minWidth : 200
			},{
				display : '公交卡号',
				name : 'busCardNo',
				align : 'center',
				width : 200,
				minWidth : 200
			},{
				display : '网银注册号',
				name : 'ebankRegNo',
				align : 'center',
				width : 200,
				minWidth : 200
			},{
				display : '公共事业付费',
				name : 'publicUtilityPay',
				align : 'center',
				width : 200,
				minWidth : 200
			},{
				display : '主要流动资金',
				name : 'liquidassets',
				align : 'center',
				width : 200,
				minWidth : 200
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