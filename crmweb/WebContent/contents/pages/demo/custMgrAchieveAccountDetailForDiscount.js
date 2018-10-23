var record2 = Ext.data.Record.create( [ {
			name : 'id',
			mapping : 'ID'
		}, {
			name : 'accountNo',
			mapping : 'ACCOUNT_NO'
		}, {
			name : 'accountName',
			mapping : 'ACCOUNT_NAME'
		}, {
			name : 'openOrgName',
			mapping : 'OPEN_ORG_NAME'
		}, {
			name : 'accountXcName',
			mapping : 'ACCOUNT_XC_NAME'
		}, {
			name : 'xcNo',
			mapping : 'XC_NO'
		}, {
			name : 'atSubOrgName',
			mapping : 'AT_SUB_ORG_NAME'
		}, {
			name : 'atOrgName',
			mapping : 'AT_ORG_NAME'
		}, {
			name : 'discountDate',
			mapping : 'DISCOUNT_DATE'
		}, {
			name : 'discountEndDate',
			mapping : 'DISCOUNT_END_DATE'
		}, {
			name : 'discountAmount',
			mapping : 'DISCOUNT_AMOUNT'
		}, {
			name : 'profitScale',
			mapping : 'PROFIT_SCALE'
		}, {
			name : 'localMonthDayAvg',
			mapping : 'LOCAL_MONTH_DAY_AVG'
		}, {
			name : 'localseasonDayAvg',
			mapping : 'LOCAL_SEASON_DAY_AVG'
		}, {
			name : 'localYearDayAvg',
			mapping : 'LOCAL_YEAR_DAY_AVG'
		}, {
			name : 'localDayAmount',
			mapping : 'LOCAL_DAY_AMOUNT'
		}, {
			name : 'profitIncome',
			mapping : 'PROFIT_INCOME'
		} ]);
// 定义列模型
var cm2 = new Ext.grid.ColumnModel( [ rownum, {
			header : 'ID',
			width : 50,
			align : 'center',
			dataIndex : 'id',
			hidden : true
		}, {
			header : '账号',
			width : 150,
			align : 'center',
			dataIndex : 'accountNo',
			sortable : true
		}, {
			header : '账户名称',
			width : 150,
			align : 'center',
			dataIndex : 'accountName',
			sortable : true
		}, {
			header : '开立网点',
			width : 150,
			align : 'center',
			dataIndex : 'openOrgName',
			sortable : true
		},// 
		{
			header : '吸存人姓名',
			width : 150,
			align : 'center',
			dataIndex : 'accountXcName',
			sortable : true
		}, {
			header : '吸存号',
			width : 150,
			align : 'center',
			dataIndex : 'xcNo',
			sortable : true
		}, {
			header : '所属分行',
			width : 150,
			align : 'center',
			dataIndex : 'atSubOrgName',
			sortable : true
		}, {
			header : '所属机构',
			width : 150,
			align : 'center',
			dataIndex : 'atOrgName',
			sortable : true
		}, {
			header : '贴现日期',
			width : 150,
			align : 'center',
			dataIndex : 'discountDate',
			sortable : true
		}, {
			header : '到期日期',
			width : 150,
			align : 'center',
			dataIndex : 'discountEndDate',
			sortable : true
		}, {
			header : '贴现面值',
			width : 150,
			align : 'right',
			dataIndex : 'discountAmount',
			sortable : true
		}, {
			header : '贴现利率%',
			width : 150,
			align : 'center',
			dataIndex : 'profitScale',
			sortable : true
		}, {
			header : '本月日均',
			width : 150,
			align : 'right',
			dataIndex : 'localMonthDayAvg',
			sortable : true
		}, {
			header : '本季日均',
			width : 150,
			align : 'right',
			dataIndex : 'localseasonDayAvg',
			sortable : true
		}, {
			header : '本年日均',
			width : 150,
			align : 'right',
			dataIndex : 'localYearDayAvg',
			sortable : true
		}, {
			header : '本日余额',
			width : 150,
			align : 'right',
			dataIndex : 'localDayAmount',
			sortable : true
		}, {
			header : '利息收入',
			width : 150,
			align : 'right',
			dataIndex : 'profitIncome',
			sortable : true
		} ]);

/**
 * 数据存储
 */
var store2 = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/mktModelManage.json'// custMgrGroupCount
		}),
	reader : new Ext.data.JsonReader( {
		// successProperty : 'success',
		// idProperty : 'ID',
		// messageProperty : 'message',
		// root : 'json.data',
		// totalProperty : 'json.count'
		totalProperty : 'num',// 记录总数
		root : 'rows'// Json中的列表数据根节点
	}, record2)
});

var memberData2 = {
	TOTALCOUNT : 3,
		rows : [ {
			"rownum" : "1",
			"ACCOUNT_NO" : '81222222',
			"ACCOUNT_NAME" : "广东宏泽集团有限公司",
			"OPEN_ORG_NAME" : "华夏广州分行",
			"ACCOUNT_XC_NAME" : "周梦琪",
			"XC_NO" : "016901",
			"AT_SUB_ORG_NAME" : "华夏广州支行",
			"AT_ORG_NAME" : "华夏广州分行",
			"DISCOUNT_DATE" : "2012-08-31",
			"DISCOUNT_END_DATE" : "2012-09-30",
			"DISCOUNT_AMOUNT" : "500",
			"PROFIT_SCALE" : "0.05",
			"LOCAL_MONTH_DAY_AVG" : "30",
			"LOCAL_SEASON_DAY_AVG" : "40",
			"LOCAL_YEAR_DAY_AVG" : "20",
			"LOCAL_DAY_AMOUNT" : "500",
			"PROFIT_INCOME" : "7.5"
		},{ 
		"rownum" : "2",
		"ACCOUNT_NO" : '8122230',
		"ACCOUNT_NAME" : "嘉康利天然营养品保健公司",
		"OPEN_ORG_NAME" : "华夏北京银行",
		"ACCOUNT_XC_NAME" : "周梦琪",
		"XC_NO" : "016901",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "华夏北京分行",
		"DISCOUNT_DATE" : "2009-10-01",
		"DISCOUNT_END_DATE" : "2012-09-30",
		"DISCOUNT_AMOUNT" : "80",
		"PROFIT_SCALE" : "0.06",
		"LOCAL_MONTH_DAY_AVG" : "20",
		"LOCAL_SEASON_DAY_AVG" : "20",
		"LOCAL_YEAR_DAY_AVG" : "30",
		"LOCAL_DAY_AMOUNT" : "300",
		"PROFIT_INCOME" : "1.44"
	}, { 
		"rownum" : "3",
		"ACCOUNT_NO" : '8122233',
		"ACCOUNT_NAME" : "北京汇智华威贸易有限公司",
		"OPEN_ORG_NAME" : "华夏北京银行",
		"ACCOUNT_XC_NAME" : "梁瀚宇",
		"XC_NO" : "016907",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "华夏北京分行",
		"ACCOUNT_CLASS" : "总分类账",
		"ACCOUNT_STATUS" : "正常",
		"DISCOUNT_DATE" : "2010-08-15",
		"DISCOUNT_END_DATE" : "2013-08-14",
		"DISCOUNT_AMOUNT" : "30",
		"PROFIT_SCALE" : "0.08",
		"LOCAL_MONTH_DAY_AVG" : "20",
		"LOCAL_SEASON_DAY_AVG" : "20",
		"LOCAL_YEAR_DAY_AVG" : "20",
		"LOCAL_DAY_AMOUNT" : "100",
		"PROFIT_INCOME" : "0.72"
	}, { 
		"rownum" : "4",
		"ACCOUNT_NO" : '8122249',
		"ACCOUNT_NAME" : "北京东方情缘广告有限公司",
		"OPEN_ORG_NAME" : "华夏北京银行",
		"ACCOUNT_XC_NAME" : "郝红婵",
		"XC_NO" : "016912",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "华夏北京分行",
		"DISCOUNT_DATE" : "2008-05-15",
		"DISCOUNT_END_DATE" : "2016-07-13",
		"DISCOUNT_AMOUNT" : "50",
		"PROFIT_SCALE" : "0.04",
		"LOCAL_MONTH_DAY_AVG" : "10",
		"LOCAL_SEASON_DAY_AVG" : "20",
		"LOCAL_YEAR_DAY_AVG" : "15",
		"LOCAL_DAY_AMOUNT" : "150",
		"PROFIT_INCOME" : "1.2"
	}, { 
		"rownum" : "5",
		"ACCOUNT_NO" : '81222322',
		"ACCOUNT_NAME" : "北京逐日文化传媒有限公司",
		"OPEN_ORG_NAME" : "华夏北京银行",
		"ACCOUNT_XC_NAME" : "韩冰",
		"XC_NO" : "016912",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "华夏北京分行",
		"DISCOUNT_DATE" : "2012-01-20",
		"DISCOUNT_END_DATE" : "2012-02-18",
		"DISCOUNT_AMOUNT" : "60",
		"PROFIT_SCALE" : "0.06",
		"LOCAL_MONTH_DAY_AVG" : "20",
		"LOCAL_SEASON_DAY_AVG" : "25",
		"LOCAL_YEAR_DAY_AVG" : "25",
		"LOCAL_DAY_AMOUNT" : "200",
		"PROFIT_INCOME" : "1.08"
	} ]
};
store2.loadData(memberData2);

// 每页显示条数下拉选择框
var pagesize_combo2 = new Ext.form.ComboBox( {
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore( {
		fields : [ 'value', 'text' ],
		data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
				[ 1000, '1000条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	editable : false,
	width : 85
});

// 默认加载数据
store2.load( {
	params : {
		start : 0,
		limit : parseInt(pagesize_combo2.getValue())
	}
});

// 改变每页显示条数reload数据
pagesize_combo2.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo2.getValue()), store2.reload( {
		params : {
			start : 0,
			limit : parseInt(pagesize_combo2.getValue())
		}
	});
});
// 分页工具栏
var bbar2 = new Ext.PagingToolbar( {
	pageSize : parseInt(pagesize_combo2.getValue()),
	store : store2,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', pagesize_combo2 ]
});

// 表格实例
var grid2 = new Ext.grid.GridPanel( {
	title : '贴现账户明细列表',
	// loyout:'fit',
	width : document.body.scrollWidth,
	height : 360,
	frame : true,
	autoScroll : true,
	store : store2,
	stripeRows : true, // 斑马线
	cm : cm2, // 列模型
	tbar :  new Ext.Toolbar({
        items  : ['数据日期：　　　　年　　月　　日','　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　币种：',
                  '->','单位：万元']
    }), // 表格工具栏
	bbar : bbar2,// 分页工具栏
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});