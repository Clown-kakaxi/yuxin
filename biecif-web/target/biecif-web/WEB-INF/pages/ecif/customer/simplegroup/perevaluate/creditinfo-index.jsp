<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgevaluate/creditinfo/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '征信报告中过去24个月最大逾期期数',
				name : 'maxOverdue24Mon',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "征信报告中过去12个月最大逾期期数",
			name : "maxOverdue12Mon",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "征信报告中过去24个月内逾期一期以上的次数",
			name : "oneOverdue24Mon",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "征信报告中过去12个月内逾期一期以上的次数",
			name : "oneOverdue12Mon",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "征信报告贷款次数",
			name : "loanNum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "征信报告个人经营贷款次数",
			name : "busiLoanNum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "征信报告信用贷款次数",
			name : "creditLoanNum",
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

