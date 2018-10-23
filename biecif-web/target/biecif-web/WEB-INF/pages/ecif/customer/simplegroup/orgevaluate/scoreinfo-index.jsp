<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgevaluate/scoreinfo/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '评分机构编码',
				name : 'scoreBranchCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "评分机构名称",
			name : "scoreBranchName",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "评分类型",
			name : "scoreType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "评分结果",
			name : "scoreResult",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "评分日期",
			name : "scoreDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "评分有效期",
			name : "scorePeriod",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "评分生效日期",
			name : "effectiveDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "评分失效日期",
			name : "expiredDate",
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

