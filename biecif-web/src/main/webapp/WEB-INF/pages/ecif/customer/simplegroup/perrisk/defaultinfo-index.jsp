<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrisk/defaultinfo/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '不良记录编号',
				name : 'badRecNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "不良记录类型",
			name : "badRecType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良记录描述",
			name : "badRecDesc",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良记录级别",
			name : "badRecLevel",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良记录日期",
			name : "badRecDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良账户账号",
			name : "badAcctNo",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良账户户名",
			name : "badAcctName",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良账户状态",
			name : "badAcctStat",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良账户币种",
			name : "badAcctCurr",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良账户金额",
			name : "badAcctAmt",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "不良账户余额",
			name : "badAcctBal",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "所在地区",
			name : "occurAreaCode",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "发生机构",
			name : "occurBranchNo",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "处理情况",
			name : "dealInfo",
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

