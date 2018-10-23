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
		url = "${ctx}/ecif/perevaluate/perpreference/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '风险偏好',
				name : 'riskPrefer',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '语言偏好',
				name : 'langPrefer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '联系时间偏好',
				name : 'contactTimePrefer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '联络频率偏好',
				name : 'contactFrequencyPrefer',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '赠品礼物偏好',
				name : 'giftPrefer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '称谓偏好',
				name : 'titlePrefer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '出行交通工具',
				name : 'vehiclePrefer',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '消费习惯',
				name : 'consumHabit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '保险倾向',
				name : 'insurancePrefer',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '投资经验',
				name : 'investExpr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '感兴趣的投资信息',
				name : 'interestInvestment',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '客户经理了解的客户风险承受能力',
				name : 'investStyle',
				align : 'center',
				width : 190,
				minWidth : 190
			}, {
				display : '客户已使用的我行产品',
				name : 'selfBankProd',
				align : 'center',
				width : 140,
				minWidth : '140'
			}, {
				display : '客户已使用的他行产品',
				name : 'otherBankProd',
				align : 'center',
				width : 140,
				minWidth : '140'
			}, {
				display : '是否接受我行寄发的资料',
				name : 'postDataFlag',
				align : 'center',
				width : 140,
				minWidth : '140'
			}, {
				display : '是否愿意参加我行组织的联谊活动',
				name : 'joinCampFlag',
				align : 'center',
				width : 190,
				minWidth : 190
			}, {
				display : '喜好的宠物',
				name : 'pet',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '喜好的媒体',
				name : 'media',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '喜好的休闲方式',
				name : 'recWay',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '喜好的影视节目类型',
				name : 'filmTvType',
				align : 'center',
				width : 130,
				minWidth : 130
			}, {
				display : '喜好的运动',
				name : 'sports',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '喜好的杂志',
				name : 'magazine',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '主要的投资目标',
				name : 'investTarget',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '主要的投资渠道',
				name : 'investChannel',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '最佳联络方式',
				name : 'contactType',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '最佳联络时间',
				name : 'contactTime',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date",
				format : 'yyyy-MM-dd hh:mm:ss'
			}, {
				display : '个人欢迎文字',
				name : 'welcomeText',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '兴趣爱好',
				name : 'hobby',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '不良嗜好',
				name : 'abuse',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '忌讳',
				name : 'taboo',
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