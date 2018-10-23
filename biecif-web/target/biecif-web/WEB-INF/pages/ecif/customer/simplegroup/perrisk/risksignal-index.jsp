<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrisk/risksignal/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '对象类型',
				name : 'objectType',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "对象编号",
			name : "objectNo",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "风险类型",
			name : "signalType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "风险信号状态",
			name : "signalStatus",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "风险信号编号",
			name : "signalNo",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "风险信号名称",
			name : "signalName",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "预警信息来源",
			name : "messageOrigin",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "预警信息详情",
			name : "messageContent",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "是否需要紧急行动",
			name : "actionFlag",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "紧急行动措施",
			name : "actionType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "建议解除预警原因",
			name : "freeReason",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "预警信息产生方式",
			name : "signalChannel",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "完成预警检查日期",
			name : "finishDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "源系统流水号",
			name : "srcSerialNo",
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

