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
		url = "${ctx}/ecif/perother/inhabinfo/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '居住地址',
				name : 'inhabAddr',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '居住地址邮编',
				name : 'inhabPost',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '居住状况',
				name : 'inhabStatsign',
				align : 'center',
				width : 130,
				minWidth : 130
			}, {
				display : '居住起始年月',
				name : 'inhabMonth',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '居住时间',
				name : 'inhabTime',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '是否现地址',
				name : 'currInhabFlag',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '按揭银行',
				name : 'loanBank',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '借款人编号',
				name : 'loanCustId',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '借款人',
				name : 'loanCustName',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '所有人编号',
				name : 'ownCustId',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '所有人',
				name : 'ownCustName',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '面积（平方米）',
				name : 'inhabArea',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '贷款金额',
				name : 'loanCapi',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '起始日期',
				name : 'beginDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '到期日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '还款频率',
				name : 'retufreqsign',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '每期还款额',
				name : 'mamt',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '当前余额',
				name : 'loanBal',
				align : 'center',
				width : 100,
				minWidth : 90
			} , {
				display : '最高预期数',
				name : 'highOvernum',
				align : 'center',
				width : 100,
				minWidth : 90
			} , {
				display : '是否存在拖欠',
				name : 'overFlag',
				align : 'center',
				width : 100,
				minWidth : 90
			} , {
				display : '所有权证号码',
				name : 'ownerPaperNo',
				align : 'center',
				width : 100,
				minWidth : 90
			} , {
				display : '位置',
				name : 'loacation',
				align : 'center',
				width : 100,
				minWidth : 90
			} , {
				display : '建成时间',
				name : 'finishTime',
				align : 'center',
				width : 100,
				minWidth : 90,
				type : "date"
			} , {
				display : '完税价',
				name : 'taxprice',
				align : 'center',
				width : 100,
				minWidth : 90
			} , {
				display : '是否抵押',
				name : 'mortFlag',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '住房性质',
				name : 'houseAttr',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '租用期限',
				name : 'term',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '每年租金',
				name : 'annualRent',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '每月租金',
				name : 'monthRent',
				align : 'center',
				width : 100,
				minWidth : 90
			} ],
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