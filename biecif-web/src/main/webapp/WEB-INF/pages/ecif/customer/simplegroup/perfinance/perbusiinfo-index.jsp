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
		url = "${ctx}/ecif/perfinance/perbusiinfo/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '现经营地址经营年限',
				name : 'addrdealyear',
				align : 'left',
				width : 150,
				minWidth : 120
			}, {
				display : '资产总额',
				name : 'assetamt',
				align : 'right',
				width : 120,
				minWidth : 100
			}, {
				display : '基本账户开户账号',
				name : 'basicacno',
				align : 'center',
				width : 180,
				minWidth : 150
			}, {
				display : '基本账户开户行',
				name : 'basicbankname',
				align : 'center',
				width : 150,
				minWidth : 120
			} , {
				display : '日最高销售额',
				name : 'daymaxsaleamt',
				align : 'right',
				width : 100,
				minWidth : 100
			}, {
				display : '日最低销售额',
				name : 'dayminsaleamt',
				align : 'right',
				width : 100,
				minWidth : 100
			}, {
				display : '经营场所',
				name : 'dealaddr',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '企业资金-现金',
				name : 'dealcash',
				align : 'right',
				width : 100,
				minWidth : 90
			}, {
				display : '经营项目',
				name : 'dealproj',
				align : 'left',
				width : 100,
				minWidth : 80
			}, {
				display : '企业资金-存款',
				name : 'dealsavings',
				align : 'right',
				width : 100,
				minWidth : 100
			}, {
				display : '经营场地',
				name : 'dealsitetype',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '到银行存款频率',
				name : 'depofreq',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '总客户数量',
				name : 'downcustnumtype',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '全职人数',
				name : 'fulltimenum',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '收入总额',
				name : 'incomeamt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '共同所有权',
				name : 'incommownership',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经营项目编号',
				name : 'keyno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '上月销售额',
				name : 'lastmonthsaleamt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '负债总额',
				name : 'liabamt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '经营证照是否在有效期',
				name : 'licedate',
				align : 'center',
				width : 130,
				minWidth : 130
			}, {
				display : '经营证照有效期',
				name : 'liceenddate',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '营业执照号',
				name : 'liceid',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '营业执照类型',
				name : 'liceidtype',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '营业证照是否齐全',
				name : 'liceisfull',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '营业执照年限',
				name : 'licelife',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '营业项目月收入',
				name : 'monthincome',
				align : 'right',
				width : 100,
				minWidth : 100
			}, {
				display : '月支出',
				name : 'monthpay',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '月结余情况',
				name : 'monthprofit',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '行业',
				name : 'occuindustry',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '其他收入',
				name : 'otherincome',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '合伙人数',
				name : 'partnernum',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '注册登记日',
				name : 'registdate',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '申请人所有权百分比',
				name : 'stockper',
				align : 'center',
				width : 130,
				minWidth : 130
			}, {
				display : '库存所在地址',
				name : 'storeaddr',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '库存检查日期',
				name : 'storecheckdate',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '库存估算金额',
				name : 'storeestiamt',
				align : 'right',
				width : 100,
				minWidth : 100
			}, {
				display : '现有总应付账款金额',
				name : 'daytotalsaleamt',
				align : 'right',
				width : 120,
				minWidth : 120
			}, {
				display : '账务及交易记录',
				name : 'tranrecord',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '周转资金',
				name : 'turncapi',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '总供货商数量',
				name : 'upcustnumtype',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '从业时间',
				name : 'workingtime',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '年营业额',
				name : 'yearamt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '本年利润',
				name : 'yearprofamt',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '本年销售收入',
				name : 'yearsaleamt',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '负责人',
				name : 'responsibleperson',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '许可证号码',
				name : 'licenseno',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '经营形式',
				name : 'manageform',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '税务登记证号',
				name : 'taxregno',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '注册资金',
				name : 'regcapital',
				align : 'right',
				width : 100,
				minWidth : 80
			}, {
				display : '注册地址',
				name : 'liceaddr',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '主要经营范围',
				name : 'maindealscope',
				align : 'center',
				width : 100,
				minWidth : 90
			}, {
				display : '规模性行业分类',
				name : 'occuindustrytype',
				align : 'center',
				width : 110,
				minWidth : 110
			}, {
				display : '企业内部环境行业分类',
				name : 'innerenvindustrytype',
				align : 'center',
				width : 140,
				minWidth : '140'
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