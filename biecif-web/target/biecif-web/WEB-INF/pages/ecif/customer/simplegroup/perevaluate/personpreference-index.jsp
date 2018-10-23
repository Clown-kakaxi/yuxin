<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perevaluate/personpreference/list.json?custId=" + custId;
	$(function() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '语言偏好',
				name : 'langPrefer',
				align : 'center',
				width : 100,
				minWidth : 80
			}	, {
			display : "称谓偏好",
			name : "titlePrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "联络方式偏好",
			name : "contactType",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "联络频率偏好",
			name : "contactFrequencyPrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "联络时间偏好",
			name : "contactTimePrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "赠品礼物偏好",
			name : "giftPrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "出行交通工具偏好",
			name : "vehiclePrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "消费习惯",
			name : "consumHabit",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "保险倾向",
			name : "insurancePrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "投资经验",
			name : "investExpr",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "投资风险偏好",
			name : "riskPrefer",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "感兴趣的投资信息",
			name : "interestInvestment",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "客户风险承受能力",
			name : "investStyle",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "主要的投资目标",
			name : "investTarget",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "主要的投资渠道",
			name : "investChannel",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "是否接受我行寄发的资料",
			name : "postDataFlag",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "是否愿意参加联谊活动",
			name : "joinCampFlag",
			align : 'center',
			width : 100,
			minWidth : 80
	}
	, {
			display : "个人欢迎文字",
			name : "welcomeText",
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

