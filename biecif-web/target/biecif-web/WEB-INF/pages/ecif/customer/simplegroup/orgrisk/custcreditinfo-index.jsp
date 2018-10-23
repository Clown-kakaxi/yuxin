<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrisk/custcreditinfo/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '客户名称',
				name : 'custName',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "授信编号",
			name : "creditNo",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "授信类型",
			name : "creditType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "测算授信额度",
			name : "measureCreditLimit",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "拟报送授信额度",
			name : "sendCreditLimit",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "最终授信额度",
			name : "lastCreditLimit",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "授信币种",
			name : "creditCurrency",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "授信起始日",
			name : "creditStartDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "授信到期日",
			name : "creditEndDate",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "是否循环",
			name : "isLoop",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "是否生效",
			name : "isValid",
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

