<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perevaluate/personpoint/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '可用积分',
				name : 'usablePoint',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "累计积分",
			name : "sumPoint",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "总积分",
			name : "totalPoint",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "积分有效期",
			name : "pointPeriod",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "开始计分日期",
			name : "startDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "累计贡献度",
			name : "profitSum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "累计基本积分",
			name : "basePointSum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "累计奖励积分",
			name : "awardPointSum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "累计已使用积分",
			name : "usedPointSum",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "当月已使用积分",
			name : "monthUsedPointSum",
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

