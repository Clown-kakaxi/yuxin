//合并单元格
var continentGroupRow22 = [ {
	header : '',
	colspan : 6,
	align : 'center'
}, {
	header : '（当月/当季/当年）储蓄存款日均',
	colspan : 9,
	align : 'center'
}, {
	header : '储蓄存款余额',
	colspan : 9,
	align : 'center'
}, {
	header : '个人贷款（月/季/年)日均',
	colspan : 34,
	align : 'center'
}, {
	header : '个人贷款余额',
	colspan : 34,
	align : 'center'
}, {
	header : '累计发卡量（张）',
	colspan : 2,
	align : 'center'
}, {
	header : '',
	colspan : 1,
	align : 'center'
}, {
	header : '',
	colspan : 1,
	align : 'center'
}, {
	header : '不良余额',
	colspan : 3,
	align : 'center'
} ];

var group2 = new Ext.ux.grid.ColumnHeaderGroup( {
	rows : [ continentGroupRow22 ]
// continentGroupRow,
		});

var record2 = Ext.data.Record.create( [ {
	name : 'id',
	mapping : 'ID'
}, {
	name : 'custMgrName',
	mapping : 'CUST_MGR_NAME'
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
	name : 'custName',
	mapping : 'CUST_NAME'
},{
	name : 'huoqiSave',
	mapping : 'HUOQI_SAVE'
}, {
	name : 'dingqiSave',
	mapping : 'DINGQI_SAVE'
}, {
	name : 'AskedSave',
	mapping : 'ASKED_SAVE'
}, {
	name : 'threeMonthBelow',
	mapping : 'THREE_MONTH_BELOW'
}, {
	name : 'sixMonth',
	mapping : 'SIXMONTH'
}, {
	name : 'oneYear',
	mapping : 'ONE_YEAR'
}, {
	name : 'twoYear',
	mapping : 'TWO_YEAR'
}, {
	name : 'threeYear',
	mapping : 'THREE_YEAR'
}, {
	name : 'fiveYearUp',
	mapping : 'FIVE_YEAR_UP'
}, {
	name : 'sixMonthBlow',
	mapping : 'SIX_MONTH_BLOW'
}, {
	name : 'upFloat',
	mapping : 'UP_FLOAT'
}, {
	name : 'stand',
	mapping : 'SATAND'
}, {
	name : 'downFloat',
	mapping : 'DOWN_FLOAT'
}, {
	name : 'sixMonthAndOneYear',
	mapping : 'SIX_MONTH_AND_ONE_YEAR'
}, {
	name : 'oneOrThreeYear',
	mapping : 'ONE_OR_THREE_YEAR'
}, {
	name : 'threeOrFiveYear',
	mapping : 'THREE_OR_FIVE_YEAR'
}, {
	name : 'loanLxIncome',
	mapping : 'LOAN_LX_INCOME'
}, {
	name : 'addSaveLxOut',
	mapping : 'ADD_SAVE_LX_OUT'
}, {
	name : 'tuoLoanAmount',
	mapping : 'TUO_LOAN_AMOUNT'
}, {
	name : 'fiveClassThreeAmount',
	mapping : 'FIVE_CLASS_THREE_AMOUNT'
}, {
	name : 'tabInOutQianLx',
	mapping : 'TAB_IN_OUT_QIAN_LX'
}, {
	name : 'perHouseLoanAmount',
	mapping : 'PER_HOUSE_LOAN_AMOUNT'
}, {
	name : 'perNotHouseLoanAmount',
	mapping : 'PER_NOT_HOUSE_LOAN_AMOUNT'
}, {
	name : 'perHouseLoanDayAvg',
	mapping : 'PER_HOUSE_LOAN_DAY_AVG'
}, {
	name : 'perNotHouseLoanDayAvg',
	mapping : 'PER_NOT_HOUSE_LOAN_DAY_AVG'
}, {
	name : 'BorrowCard',
	mapping : 'BORROW_CARD'
}, {
	name : 'creditCard',
	mapping : 'CREDIT_CARD'
}]);

// 定义列模型
var cm2 = new Ext.grid.ColumnModel( [ // rownum,
                                      {
                              			header : 'NO.',
                              			width : 50,
                              			align : 'center',
                              			dataIndex : 'id'
                              		},{
			header : '客户经理姓名',
			width : 150,
			align : 'center',
			dataIndex : 'custMgrName',
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
			header : '客户名称',
			width : 150,
			align : 'center',
			dataIndex : 'custName',
			sortable : true
		}, {
			header : '活期存款',
			width : 150,
			align : 'right',
			dataIndex : 'huoqiSave',
			sortable : true
		}, {
			header : '定期存款',
			width : 150,
			align : 'right',
			dataIndex : 'dingqiSave',
			sortable : true
		}, {
			header : '通知存款',
			width : 150,
			align : 'right',
			dataIndex : 'AskedSave',
			sortable : true
		}, {
			header : '3个月及以下',
			width : 150,
			align : 'right',
			dataIndex : 'threeMonthBelow',
			sortable : true
		}, {
			header : '6个月',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonth',
			sortable : true
		}, {
			header : '一年',
			width : 150,
			align : 'right',
			dataIndex : 'oneYear',
			sortable : true
		}, {
			header : '二年',
			width : 150,
			align : 'right',
			dataIndex : 'twoYear',
			sortable : true
		}, {
			header : '三年',
			width : 150,
			align : 'right',
			dataIndex : 'threeYear',
			sortable : true
		}, {
			header : '5年及以上',
			width : 150,
			align : 'right',
			dataIndex : 'fiveYearUp',
			sortable : true
		}, {
			header : '活期存款',
			width : 150,
			align : 'right',
			dataIndex : 'huoqiSave',
			sortable : true
		}, {
			header : '定期存款',
			width : 150,
			align : 'right',
			dataIndex : 'dingqiSave',
			sortable : true
		}, {
			header : '通知存款',
			width : 150,
			align : 'right',
			dataIndex : 'AskedSave',
			sortable : true
		}, {
			header : '3个月及以下',
			width : 150,
			align : 'right',
			dataIndex : 'threeMonthBelow',
			sortable : true
		}, {
			header : '6个月',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonth',
			sortable : true
		}, {
			header : '一年',
			width : 150,
			align : 'right',
			dataIndex : 'oneYear',
			sortable : true
		}, {
			header : '二年',
			width : 150,
			align : 'right',
			dataIndex : 'twoYear',
			sortable : true
		}, {
			header : '三年',
			width : 150,
			align : 'right',
			dataIndex : 'threeYear',
			sortable : true
		}, {
			header : '5年及以上',
			width : 150,
			align : 'right',
			dataIndex : 'fiveYearUp',
			sortable : true
		}, {
			header : '个人非住房贷款日均',
			width : 150,
			align : 'right',
			dataIndex : 'perNotHouseLoanDayAvg',
			sortable : true
		}, {
			header : '6个月（含）以内',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthBlow',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '6个月—1年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthAndOneYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '1—3年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'oneOrThreeYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '3—5年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'threeOrFiveYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '个人住房贷款日均',
			width : 150,
			align : 'right',
			dataIndex : 'perHouseLoanDayAvg',
			sortable : true
		},{
			header : '6个月（含）以内',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthBlow',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '6个月—1年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthAndOneYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '1—3年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'oneOrThreeYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '3—5年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'threeOrFiveYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '个人非住房贷款余额',
			width : 150,
			align : 'right',
			dataIndex : 'perNotHouseLoanAmount',
			sortable : true
		},{
			header : '6个月（含）以内',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthBlow',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '6个月—1年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthAndOneYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '1—3年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'oneOrThreeYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '3—5年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'threeOrFiveYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '个人住房贷款余额',
			width : 150,
			align : 'right',
			dataIndex : 'perHouseLoanAmount',
			sortable : true
		}, {
			header : '6个月（含）以内',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthBlow',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '6个月—1年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'sixMonthAndOneYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '1—3年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'oneOrThreeYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '3—5年（含）',
			width : 150,
			align : 'right',
			dataIndex : 'threeOrFiveYear',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upFloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'stand',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '借记卡',
			width : 150,
			align : 'right',
			dataIndex : 'BorrowCard',
			sortable : true
		}, {
			header : '信用卡',
			width : 150,
			align : 'right',
			dataIndex : 'creditCard',
			sortable : true
		}, {
			header : '（当季/当年）实现贷款利息收入',
			width : 150,
			align : 'right',
			dataIndex : 'loanLxIncome',
			sortable : true
		}, {
			header : '（当月/当季/当年）累计存款利息支出',
			width : 150,
			align : 'right',
			dataIndex : 'addSaveLxOut',
			sortable : true
		}, {
			header : '拖欠贷款余额',
			width : 150,
			align : 'right',
			dataIndex : 'tuoLoanAmount',
			sortable : true
		}, {
			header : '五级分类后三类余额',
			width : 150,
			align : 'right',
			dataIndex : 'fiveClassThreeAmount',
			sortable : true
		}, {
			header : '表内外欠息',
			width : 150,
			align : 'center',
			dataIndex : 'tabInOutQianLx',
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
		"ID" : "1",
		"CUST_MGR_NAME" : '周梦琪',
		"XC_NO" : "016901",
		"AT_SUB_ORG_NAME" : "华夏北京银行",
		"AT_ORG_NAME" : "公司业务部",
		"CUST_NAME" : "广东宏泽集团有限公司",
		"HUOQI_SAVE" : "100",
		"DINGQI_SAVE" : "30",
		"ASKED_SAVE" : "5",
		"THREE_MONTH_BELOW" : "30",
		"SIXMONTH" : "30",
		"ONE_YEAR" : "10",
		"TWO_YEAR" : "10",
		"THREE_YEAR" : "10",
		"FIVE_YEAR_UP" : "10",
		"SIX_MONTH_BLOW" : "20",
		"UP_FLOAT" : "0.5",
		"SATAND" : "5",
		"DOWN_FLOAT" : "0.5",
		"SIX_MONTH_AND_ONE_YEAR" : "20",
		"ONE_OR_THREE_YEAR" : "10",
		"THREE_OR_FIVE_YEAR" : "10",
		"CREDIT_CARD" : "8",
		"BORROW_CARD" : "5",
		"PER_NOT_HOUSE_LOAN_DAY_AVG" : "0.3",
		"PER_HOUSE_LOAN_DAY_AVG" : "0.2",
		"PER_NOT_HOUSE_LOAN_AMOUNT" : "30",
		"PER_HOUSE_LOAN_AMOUNT" : "20",
		"LOAN_LX_INCOME" : "8",
		"ADD_SAVE_LX_OUT" : "5",
		"TUO_LOAN_AMOUNT" : "30",
		"FIVE_CLASS_THREE_AMOUNT" : "20",
		"TAB_IN_OUT_QIAN_LX" : "10"
	}, {
		"ID" : "2",
		"CUST_MGR_NAME" : '梁瀚宇',
		"XC_NO" : "016907",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "华夏朝阳分行",
		"CUST_NAME" : "北京汇智华威贸易有限公司",
		"HUOQI_SAVE" : "100",
		"DINGQI_SAVE" : "50",
		"ASKED_SAVE" : "500",
		"THREE_MONTH_BELOW" : "30",
		"SIXMONTH" : "30",
		"ONE_YEAR" : "20",
		"TWO_YEAR" : "10",
		"THREE_YEAR" : "10",
		"FIVE_YEAR_UP" : "10",
		"SIX_MONTH_BLOW" : "600",
		"UP_FLOAT" : "50",
		"SATAND" : "500",
		"DOWN_FLOAT" : "50",
		"SIX_MONTH_AND_ONE_YEAR" : "200",
		"ONE_OR_THREE_YEAR" : "100",
		"THREE_OR_FIVE_YEAR" : "100",
		"CREDIT_CARD" : "80",
		"BORROW_CARD" : "50",
		"PER_NOT_HOUSE_LOAN_DAY_AVG" : "30",
		"PER_HOUSE_LOAN_DAY_AVG" : "20",
		"PER_NOT_HOUSE_LOAN_AMOUNT" : "10",
		"PER_HOUSE_LOAN_AMOUNT" : "10",
		"LOAN_LX_INCOME" : "80",
		"ADD_SAVE_LX_OUT" : "50",
		"TUO_LOAN_AMOUNT" : "30",
		"FIVE_CLASS_THREE_AMOUNT" : "20",
		"TAB_IN_OUT_QIAN_LX" : "10"
	}, {
		"ID" : "3",
		"CUST_MGR_NAME" : '郝红婵',
		"XC_NO" : "016912",
		"AT_SUB_ORG_NAME" : "华夏北京银行",
		"AT_ORG_NAME" : "华夏上地支行",
		"CUST_NAME" : "北京东方情缘广告有限公司",
		"HUOQI_SAVE" : "100",
		"DINGQI_SAVE" : "50",
		"ASKED_SAVE" : "8",
		"THREE_MONTH_BELOW" : "30",
		"SIXMONTH" : "30",
		"ONE_YEAR" : "20",
		"TWO_YEAR" : "10",
		"THREE_YEAR" : "10",
		"FIVE_YEAR_UP" : "10",
		"SIX_MONTH_BLOW" : "60",
		"UP_FLOAT" : "1",
		"SATAND" : "60",
		"DOWN_FLOAT" : "1",
		"SIX_MONTH_AND_ONE_YEAR" : "20",
		"ONE_OR_THREE_YEAR" : "10",
		"THREE_OR_FIVE_YEAR" : "10",
		"CREDIT_CARD" : "30",
		"BORROW_CARD" : "30",
		"PER_NOT_HOUSE_LOAN_DAY_AVG" : "0.3",
		"PER_HOUSE_LOAN_DAY_AVG" : "0.8",
		"PER_NOT_HOUSE_LOAN_AMOUNT" : "100",
		"PER_HOUSE_LOAN_AMOUNT" : "100",
		"LOAN_LX_INCOME" : "28",
		"ADD_SAVE_LX_OUT" : "10",
		"TUO_LOAN_AMOUNT" : "40",
		"FIVE_CLASS_THREE_AMOUNT" : "20",
		"TAB_IN_OUT_QIAN_LX" : "20"
	}, {
		"ID" : "4",
		"CUST_MGR_NAME" : '韩冰',
		"XC_NO" : "016913",
		"AT_SUB_ORG_NAME" : "华夏天津银行",
		"AT_ORG_NAME" : "华夏天津分行",
		"CUST_NAME" : "北京逐日文化传媒有限公司",
		"HUOQI_SAVE" : "1,000",
		"DINGQI_SAVE" : "500",
		"ASKED_SAVE" : "50",
		"THREE_MONTH_BELOW" : "300",
		"SIXMONTH" : "300",
		"ONE_YEAR" : "200",
		"TWO_YEAR" : "100",
		"THREE_YEAR" : "100",
		"FIVE_YEAR_UP" : "100",
		"SIX_MONTH_BLOW" : "600",
		"UP_FLOAT" : "50",
		"SATAND" : "600",
		"DOWN_FLOAT" : "50",
		"SIX_MONTH_AND_ONE_YEAR" : "200",
		"ONE_OR_THREE_YEAR" : "100",
		"THREE_OR_FIVE_YEAR" : "100",
		"CREDIT_CARD" : "80",
		"BORROW_CARD" : "50",
		"PER_NOT_HOUSE_LOAN_DAY_AVG" : "3",
		"PER_HOUSE_LOAN_DAY_AVG" : "2",
		"PER_NOT_HOUSE_LOAN_AMOUNT" : "500",
		"PER_HOUSE_LOAN_AMOUNT" : "300",
		"LOAN_LX_INCOME" : "80",
		"ADD_SAVE_LX_OUT" : "50",
		"TUO_LOAN_AMOUNT" : "300",
		"FIVE_CLASS_THREE_AMOUNT" : "200",
		"TAB_IN_OUT_QIAN_LX" : "100"
	}, {
		"ID" : "5",
		"CUST_MGR_NAME" : '曹乐',
		"XC_NO" : "016908",
		"AT_SUB_ORG_NAME" : "华夏北京分行",
		"AT_ORG_NAME" : "国贸营业部",
		"CUST_NAME" : "嘉康利天然营养品保健公司",
		"HUOQI_SAVE" : "100",
		"DINGQI_SAVE" : "50",
		"ASKED_SAVE" : "500",
		"THREE_MONTH_BELOW" : "30",
		"SIXMONTH" : "30",
		"ONE_YEAR" : "20",
		"TWO_YEAR" : "10",
		"THREE_YEAR" : "10",
		"FIVE_YEAR_UP" : "10",
		"SIX_MONTH_BLOW" : "600",
		"UP_FLOAT" : "50",
		"SATAND" : "500",
		"DOWN_FLOAT" : "50",
		"SIX_MONTH_AND_ONE_YEAR" : "200",
		"ONE_OR_THREE_YEAR" : "100",
		"THREE_OR_FIVE_YEAR" : "100",
		"CREDIT_CARD" : "80",
		"BORROW_CARD" : "50",
		"PER_NOT_HOUSE_LOAN_DAY_AVG" : "3",
		"PER_HOUSE_LOAN_DAY_AVG" : "2",
		"PER_NOT_HOUSE_LOAN_AMOUNT" : "10",
		"PER_HOUSE_LOAN_AMOUNT" : "10",
		"LOAN_LX_INCOME" : "80",
		"ADD_SAVE_LX_OUT" : "50",
		"TUO_LOAN_AMOUNT" : "30",
		"FIVE_CLASS_THREE_AMOUNT" : "20",
		"TAB_IN_OUT_QIAN_LX" : "10"
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
	title : '个人客户主要业务明细列表',
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
	},
	plugins : group2
});