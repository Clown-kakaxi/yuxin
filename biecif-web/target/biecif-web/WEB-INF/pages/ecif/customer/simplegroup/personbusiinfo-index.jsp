<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perfinance/personbusiinfo/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '个人负债信息标识',
				name : 'PERSON_DEBT_ID',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "客户标识",
			name : "CUST_ID",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "负债类型",
			name : "DEBT_TYPE",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "负债名称",
			name : "DEBT_NAME",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "负债余额",
			name : "DEBT_BAL",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "负债描述",
			name : "DEBT_DESC",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "源系统流水号",
			name : "SRC_SERIAL_NO",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最后更新系统",
			name : "LAST_UPDATE_SYS",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最后更新人",
			name : "LAST_UPDATE_USER",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最后更新时间",
			name : "LAST_UPDATE_TM",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "交易流水号",
			name : "TX_SEQ_NO",
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

