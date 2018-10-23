//合并单元格
var continentGroupRow2 = [ {
	header : '',
	colspan : 5,
	align : 'center'
}, {
	header : '客户基本信息',
	colspan : 8,
	align : 'center'
}, {
	header : '',
	colspan : 2,
	align : 'center'
}, {
	header : '单位定期存款日均期限结构',
	colspan : 7,
	align : 'center'
}, {
	header : '',
	colspan : 1,
	align : 'center'
}, {
	header : '',
	colspan : 2,
	align : 'center'
}, {
	header : '定期保证金日均期限结构',
	colspan : 3,
	align : 'center'
}, {
	header : '',
	colspan : 2,
	align : 'center'
}, {
	header : '单位定期存款余额期限结构',
	colspan : 7,
	align : 'center'
}, {
	header : '',
	colspan : 1,
	align : 'center'
}, {
	header : '定期保证金余额期限结构',
	colspan : 5,
	align : 'center'
}, {
	header : '人民币贷款日均结构分布',
	colspan : 16,
	align : 'center'
}, {
	header : '人民币贷款余额结构分布',
	colspan : 16,
	align : 'center'
}, {
	header : '人民币贸易融资日均结构分布',
	colspan : 8,
	align : 'center'
}, {
	header : '人民币贸易融资余额结构分布',
	colspan : 8,
	align : 'center'
}, {
	header : '银行承兑汇票余额',
	colspan : 5,
	align : 'center'
}, {
	header : '（当月/当季/当年）累计签发银行承兑汇票',
	colspan : 5,
	align : 'center'
}, {
	header : '信用证',
	colspan : 2,
	align : 'center'
}, {
	header : '贴现业务',
	colspan : 3,
	align : 'center'
}, {
	header : '（当月/当季/当年）累计实现国际业务量(',
	colspan : 2,
	align : 'center'
}, {
	header : '当月/当季/当年）累计实现中间业务收入',
	colspan : 1,
	align : 'center'
}, {
	header : '（当季/当年）实现贷款利息收入',
	colspan : 1,
	align : 'center'
}, {
	header : '（当季/当年）累计存款利息支出',
	colspan : 1,
	align : 'center'
}, {
	header : '不良余额',
	colspan : 3,
	align : 'center'
} ];

var group = new Ext.ux.grid.ColumnHeaderGroup( {
	rows : [ continentGroupRow2 ]
// continentGroupRow,
		});

var record = Ext.data.Record.create( [ {
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
}, {
	name : 'openAccountDate',
	mapping : 'OPEN_ACCOUNT_DATE'
}, {
	name : 'hangYe',
	mapping : 'HANG_YE'
}, {
	name : 'orgName',
	mapping : 'ORG_NAME'
}, {
	name : 'ownZhi',
	mapping : 'OWN_ZHI'
}, {
	name : 'scaleClass',
	mapping : 'SCALE_CLASS'
}, {
	name : 'perOfAllScale',
	mapping : 'PER_OF_ALL_SCALE'
}, {
	name : 'accordSaveCustNum',
	mapping : 'ACCORD_SAVE_CUST_NUM'
}, {
	name : 'UnitHuoSave',
	mapping : 'UNIT_HUO_SAVE'
}, {
	name : 'UnitDingSave',
	mapping : 'UNIT_DING_SAVE'
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
	name : 'SaveBoAmount',
	mapping : 'SAVE_BO_AMOUNT'
}, {
	name : 'Huoqi',
	mapping : 'HUO_QI'
}, {
	name : 'Dingqi',
	mapping : 'DING_QI'
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
	name : 'quaneBo',
	mapping : 'QUANE_BO'
}, {
	name : 'chaeBo',
	mapping : 'CHAE_BO'
}, {
	name : 'UnitZhiya',
	mapping : 'UNIT_ZHI_YA'
}, {
	name : 'PerZhiya',
	mapping : 'PER_ZHI_YA'
}, {
	name : 'yinChengZhiya',
	mapping : 'YIN_CHENG_ZHI_YA'
}, {
	name : 'creditCardAmount',
	mapping : 'CREDIT_CARD_AMOUNT'
}, {
	name : 'AddDisCreditCard',
	mapping : 'ADD_DIS_CREDIT_CARD'
}, {
	name : 'discountAmount',
	mapping : 'DISCOUNT_AMOUNT'
}, {
	name : 'discountDayAvg',
	mapping : 'DISCOUNT_DAY_AVG'
}, {
	name : 'AddDisDiscount',
	mapping : 'ADD_DIS_DISCOUNT'
}, {
	name : 'glbNum',
	mapping : 'GLB_NUM'
}, {
	name : 'jieShouHui',
	mapping : 'JIE_SHOU_HUI'
}, {
	name : 'glbIncome',
	mapping : 'GLB_INCOME'
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
} ]);

// 定义列模型

var cm = new Ext.grid.ColumnModel( [ // rownum,
		{
			header : 'NO.',
			width : 50,
			align : 'center',
			dataIndex : 'id'
		},// ,hidden:true
				{
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
					header : '开户日期',
					width : 150,
					align : 'center',
					dataIndex : 'openAccountDate',
					sortable : true
				}, {
					header : '行业',
					width : 150,
					align : 'center',
					dataIndex : 'hangYe',
					sortable : true
				}, {
					header : '组织结构',
					width : 150,
					align : 'center',
					dataIndex : 'orgName',
					sortable : true
				}, {
					header : '所有制',
					width : 150,
					align : 'center',
					dataIndex : 'ownZhi',
					sortable : true
				}, {
					header : '规模分类',
					width : 150,
					align : 'center',
					dataIndex : 'scaleClass',
					sortable : true
				}, {
					header : '按本人存款占该客户所有存款比重折算客户数（户）',
					width : 150,
					align : 'center',
					dataIndex : 'perOfAllScale',
					sortable : true
				}, {
					header : '按存款占比折算有效户客户数（户）',
					width : 150,
					align : 'center',
					dataIndex : 'accordSaveCustNum',
					sortable : true
				}, {
					header : '单位活期存款',
					width : 150,
					align : 'right',
					dataIndex : 'UnitHuoSave',
					sortable : true
				}, {
					header : '单位定期存款',
					width : 150,
					align : 'right',
					dataIndex : 'UnitDingSave',
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
					header : '存入保证金',
					width : 150,
					align : 'right',
					dataIndex : 'SaveBoAmount',
					sortable : true
				}, {
					header : '活期',
					width : 150,
					align : 'right',
					dataIndex : 'Huoqi',
					sortable : true
				}, {
					header : '定期',
					width : 150,
					align : 'right',
					dataIndex : 'Dingqi',
					sortable : true
				}, {
					header : '3个月',
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
					header : '单位活期存款',
					width : 150,
					align : 'right',
					dataIndex : 'UnitHuoSave',
					sortable : true
				}, {
					header : '单位定期存款',
					width : 150,
					align : 'right',
					dataIndex : 'UnitDingSave',
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
					header : '存入保证金',
					width : 150,
					align : 'right',
					dataIndex : 'SaveBoAmount',
					sortable : true
				}, {
					header : '活期',
					width : 150,
					align : 'right',
					dataIndex : 'Huoqi',
					sortable : true
				}, {
					header : '定期',
					width : 150,
					align : 'right',
					dataIndex : 'Dingqi',
					sortable : true
				}, {
					header : '3个月',
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
					header : '全额保证',
					width : 150,
					align : 'right',
					dataIndex : 'quaneBo',
					sortable : true
				}, {
					header : '差额保证',
					width : 150,
					align : 'right',
					dataIndex : 'chaeBo',
					sortable : true
				}, {
					header : '单位存单质押',
					width : 150,
					align : 'right',
					dataIndex : 'UnitZhiya',
					sortable : true
				}, {
					header : '个人存贷质押',
					width : 150,
					align : 'right',
					dataIndex : 'PerZhiya',
					sortable : true
				}, {
					header : '银承质押',
					width : 150,
					align : 'right',
					dataIndex : 'yinChengZhiya',
					sortable : true
				}, {
					header : '全额保证',
					width : 150,
					align : 'right',
					dataIndex : 'quaneBo',
					sortable : true
				}, {
					header : '差额保证',
					width : 150,
					align : 'right',
					dataIndex : 'chaeBo',
					sortable : true
				}, {
					header : '单位存单质押',
					width : 150,
					align : 'right',
					dataIndex : 'UnitZhiya',
					sortable : true
				}, {
					header : '个人存贷质押',
					width : 150,
					align : 'right',
					dataIndex : 'PerZhiya',
					sortable : true
				}, {
					header : '银承质押',
					width : 150,
					align : 'right',
					dataIndex : 'yinChengZhiya',
					sortable : true
				}, {
					header : '信用证余额',
					width : 150,
					align : 'right',
					dataIndex : 'creditCardAmount',
					sortable : true
				}, {
					header : '累计开出信用证',
					width : 150,
					align : 'right',
					dataIndex : 'AddDisCreditCard',
					sortable : true
				}, {
					header : '贴现余额',
					width : 150,
					align : 'right',
					dataIndex : 'discountAmount',
					sortable : true
				}, {
					header : '当月/当季/当年）贴现日均',
					width : 150,
					align : 'right',
					dataIndex : 'discountDayAvg',
					sortable : true
				}, {
					header : '（当月/当季/当年）累计办理贴现',
					width : 150,
					align : 'right',
					dataIndex : 'AddDisDiscount',
					sortable : true
				}, {
					header : '国际业务结算量',
					width : 150,
					align : 'right',
					dataIndex : 'glbNum',
					sortable : true
				}, {
					header : '结售汇',
					width : 150,
					align : 'right',
					dataIndex : 'jieShouHui',
					sortable : true
				}, {
					header : '国际业务收入',
					width : 150,
					align : 'right',
					dataIndex : 'glbIncome',
					sortable : true
				}, {
					header : '（当季/当年）实现贷款利息收入',
					width : 150,
					align : 'right',
					dataIndex : 'loanLxIncome',
					sortable : true
				}, {
					header : '（当季/当年）累计存款利息支出',
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
					align : 'right',
					dataIndex : 'tabInOutQianLx',
					sortable : true
				} ]);

/**
 * 数据存储
 */
var store = new Ext.data.Store( {
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
	}, record)
});

var memberData = {
	TOTALCOUNT : 3,
	rows : [ {
		"ID" : "1",
		"CUST_MGR_NAME" : '周梦琪',
		"XC_NO" : "016901",
		"AT_SUB_ORG_NAME" : "华夏北京银行",
		"AT_ORG_NAME" : "公司业务部",
		"CUST_NAME" : "广东宏泽集团有限公司",
		"OPEN_ACCOUNT_DATE" : "2011-04-01",
		"HANG_YE" : "高新技术行业",
		"ORG_NAME" : "事业部制",
		"OWN_ZHI" : "股份制",
		"SCALE_CLASS" : "大型",
		"PER_OF_ALL_SCALE" : "0.6",
		"ACCORD_SAVE_CUST_NUM" : "1",
		"UNIT_HUO_SAVE" : "100",
		"UNIT_DING_SAVE" : "30",
		"ASKED_SAVE" : "500",
		"THREE_MONTH_BELOW" : "30",
		"SIXMONTH" : "30",
		"ONE_YEAR" : "20",
		"TWO_YEAR" : "10",
		"THREE_YEAR" : "10",
		"FIVE_YEAR_UP" : "10",
		"SAVE_BO_AMOUNT" : "5",
		"HUO_QI" : "1,000",
		"DING_QI" : "500",
		"SIX_MONTH_BLOW" : "600",
		"UP_FLOAT" : "50",
		"SATAND" : "500",
		"DOWN_FLOAT" : "50",
		"SIX_MONTH_AND_ONE_YEAR" : "200",
		"ONE_OR_THREE_YEAR" : "100",
		"THREE_OR_FIVE_YEAR" : "100",
		"QUANE_BO" : "50",
		"CHAE_BO" : "5",
		"UNIT_ZHI_YA" : "100",
		"PER_ZHI_YA" : "100",
		"YIN_CHENG_ZHI_YA" : "200",
		"CREDIT_CARD_AMOUNT" : "500",
		"ADD_DIS_CREDIT_CARD" : "1,000",
		"DISCOUNT_AMOUNT" : "1,000",
		"DISCOUNT_DAY_AVG" : "10",
		"ADD_DIS_DISCOUNT" : "500",
		"GLB_NUM" : "555",
		"JIE_SHOU_HUI" : "122",
		"GLB_INCOME" : "200",
		"LOAN_LX_INCOME" : "80",
		"ADD_SAVE_LX_OUT" : "50",
		"TUO_LOAN_AMOUNT" : "30",
		"FIVE_CLASS_THREE_AMOUNT" : "20",
		"TAB_IN_OUT_QIAN_LX" : "10"
	},{
		"ID" : "2",
		"CUST_MGR_NAME" : '梁瀚宇',
		"XC_NO" : "016907",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "华夏朝阳分行",
		"CUST_NAME" : "北京汇智华威贸易有限公司",
		"OPEN_ACCOUNT_DATE" : "2010-09-01",
		"HANG_YE" : "高新技术行业",
		"ORG_NAME" : "直线制",
		"OWN_ZHI" : "独资",
		"SCALE_CLASS" : "微型",
		"PER_OF_ALL_SCALE" : "0.6",
		"ACCORD_SAVE_CUST_NUM" : "1",
		"UNIT_HUO_SAVE" : "10",
		"UNIT_DING_SAVE" : "3",
		"ASKED_SAVE" : "50",
		"THREE_MONTH_BELOW" : "3",
		"SIXMONTH" : "3",
		"ONE_YEAR" : "2",
		"TWO_YEAR" : "1",
		"THREE_YEAR" : "1",
		"FIVE_YEAR_UP" : "1",
		"SAVE_BO_AMOUNT" : "5",
		"HUO_QI" : "100",
		"DING_QI" : "50",
		"SIX_MONTH_BLOW" : "60",
		"UP_FLOAT" : "5",
		"SATAND" : "50",
		"DOWN_FLOAT" : "5",
		"SIX_MONTH_AND_ONE_YEAR" : "20",
		"ONE_OR_THREE_YEAR" : "10",
		"THREE_OR_FIVE_YEAR" : "10",
		"QUANE_BO" : "5",
		"CHAE_BO" : "0.5",
		"UNIT_ZHI_YA" : "10",
		"PER_ZHI_YA" : "10",
		"YIN_CHENG_ZHI_YA" : "20",
		"CREDIT_CARD_AMOUNT" : "50",
		"ADD_DIS_CREDIT_CARD" : "100",
		"DISCOUNT_AMOUNT" : "100",
		"DISCOUNT_DAY_AVG" : "1",
		"ADD_DIS_DISCOUNT" : "50",
		"GLB_NUM" : "50",
		"JIE_SHOU_HUI" : "10",
		"GLB_INCOME" : "20",
		"LOAN_LX_INCOME" : "8",
		"ADD_SAVE_LX_OUT" : "5",
		"TUO_LOAN_AMOUNT" : "3",
		"FIVE_CLASS_THREE_AMOUNT" : "2",
		"TAB_IN_OUT_QIAN_LX" : "1"
	},{
		"ID" : "3",
		"CUST_MGR_NAME" : '郝红婵',
		"XC_NO" : "016912",
		"AT_SUB_ORG_NAME" : "华夏北京银行",
		"AT_ORG_NAME" : "华夏上地支行",
		"CUST_NAME" : "北京东方情缘广告有限公司",
		"OPEN_ACCOUNT_DATE" : "2008-08-01",
		"HANG_YE" : "高新技术行业",
		"ORG_NAME" : "直线职能制",
		"OWN_ZHI" : "股份制",
		"SCALE_CLASS" : "中型",
		"PER_OF_ALL_SCALE" : "0.6",
		"ACCORD_SAVE_CUST_NUM" : "1",
		"UNIT_HUO_SAVE" : "50",
		"UNIT_DING_SAVE" : "10",
		"ASKED_SAVE" : "200",
		"THREE_MONTH_BELOW" : "15",
		"SIXMONTH" : "15",
		"ONE_YEAR" : "10",
		"TWO_YEAR" : "5",
		"THREE_YEAR" : "5",
		"FIVE_YEAR_UP" : "5",
		"SAVE_BO_AMOUNT" : "5",
		"HUO_QI" : "500",
		"DING_QI" : "200",
		"SIX_MONTH_BLOW" : "300",
		"UP_FLOAT" : "30",
		"SATAND" : "300",
		"DOWN_FLOAT" : "30",
		"SIX_MONTH_AND_ONE_YEAR" : "100",
		"ONE_OR_THREE_YEAR" : "50",
		"THREE_OR_FIVE_YEAR" : "50",
		"QUANE_BO" : "30",
		"CHAE_BO" : "5",
		"UNIT_ZHI_YA" : "50",
		"PER_ZHI_YA" : "50",
		"YIN_CHENG_ZHI_YA" : "100",
		"CREDIT_CARD_AMOUNT" : "300",
		"ADD_DIS_CREDIT_CARD" : "500",
		"DISCOUNT_AMOUNT" : "500",
		"DISCOUNT_DAY_AVG" : "10",
		"ADD_DIS_DISCOUNT" : "200",
		"GLB_NUM" : "200",
		"JIE_SHOU_HUI" : "50",
		"GLB_INCOME" : "130",
		"LOAN_LX_INCOME" : "40",
		"ADD_SAVE_LX_OUT" : "20",
		"TUO_LOAN_AMOUNT" : "10",
		"FIVE_CLASS_THREE_AMOUNT" : "10",
		"TAB_IN_OUT_QIAN_LX" : "3"
	},{
		"ID" : "4",
		"CUST_MGR_NAME" : '韩冰',
		"XC_NO" : "016913",
		"AT_SUB_ORG_NAME" : "华夏天津银行",
		"AT_ORG_NAME" : "华夏天津分行",
		"CUST_NAME" : "北京逐日文化传媒有限公司",
		"OPEN_ACCOUNT_DATE" : "2012-03-01",
		"HANG_YE" : "高新技术行业",
		"ORG_NAME" : "矩阵制",
		"OWN_ZHI" : "合资",
		"SCALE_CLASS" : "小型",
		"PER_OF_ALL_SCALE" : "0.6",
		"ACCORD_SAVE_CUST_NUM" : "1",
		"UNIT_HUO_SAVE" : "20",
		"UNIT_DING_SAVE" : "3",
		"ASKED_SAVE" : "50",
		"THREE_MONTH_BELOW" : "3",
		"SIXMONTH" : "3",
		"ONE_YEAR" : "2",
		"TWO_YEAR" : "1",
		"THREE_YEAR" : "1",
		"FIVE_YEAR_UP" : "1",
		"SAVE_BO_AMOUNT" : "3",
		"HUO_QI" : "200",
		"DING_QI" : "10",
		"SIX_MONTH_BLOW" : "200",
		"UP_FLOAT" : "10",
		"SATAND" : "100",
		"DOWN_FLOAT" : "15",
		"SIX_MONTH_AND_ONE_YEAR" : "50",
		"ONE_OR_THREE_YEAR" : "30",
		"THREE_OR_FIVE_YEAR" : "10",
		"QUANE_BO" : "5",
		"CHAE_BO" : "1",
		"UNIT_ZHI_YA" : "10",
		"PER_ZHI_YA" : "10",
		"YIN_CHENG_ZHI_YA" : "20",
		"CREDIT_CARD_AMOUNT" : "50",
		"ADD_DIS_CREDIT_CARD" : "100",
		"DISCOUNT_AMOUNT" : "100",
		"DISCOUNT_DAY_AVG" : "3",
		"ADD_DIS_DISCOUNT" : "50",
		"GLB_NUM" : "88",
		"JIE_SHOU_HUI" : "22",
		"GLB_INCOME" : "40",
		"LOAN_LX_INCOME" : "3",
		"ADD_SAVE_LX_OUT" : "5",
		"TUO_LOAN_AMOUNT" : "3",
		"FIVE_CLASS_THREE_AMOUNT" : "2",
		"TAB_IN_OUT_QIAN_LX" : "1"
	},{
		"ID" : "5",
		"CUST_MGR_NAME" : '曹乐',
		"XC_NO" : "016908",
		"AT_SUB_ORG_NAME" : "华夏北京分行",
		"AT_ORG_NAME" : "国贸营业部",
		"CUST_NAME" : "嘉康利天然营养品保健公司",
		"OPEN_ACCOUNT_DATE" : "2011-04-01",
		"HANG_YE" : "农林行业",
		"ORG_NAME" : "子公司和分公司",
		"OWN_ZHI" : "股份制",
		"SCALE_CLASS" : "中型",
		"PER_OF_ALL_SCALE" : "0.6",
		"ACCORD_SAVE_CUST_NUM" : "1",
		"UNIT_HUO_SAVE" : "50",
		"UNIT_DING_SAVE" : "20",
		"ASKED_SAVE" : "50",
		"THREE_MONTH_BELOW" : "15",
		"SIXMONTH" : "18",
		"ONE_YEAR" : "12",
		"TWO_YEAR" : "6",
		"THREE_YEAR" : "6",
		"FIVE_YEAR_UP" : "6",
		"SAVE_BO_AMOUNT" : "3",
		"HUO_QI" : "600",
		"DING_QI" : "260",
		"SIX_MONTH_BLOW" : "300",
		"UP_FLOAT" : "30",
		"SATAND" : "20",
		"DOWN_FLOAT" : "20",
		"SIX_MONTH_AND_ONE_YEAR" : "120",
		"ONE_OR_THREE_YEAR" : "50",
		"THREE_OR_FIVE_YEAR" : "50",
		"QUANE_BO" : "2",
		"CHAE_BO" : "6",
		"UNIT_ZHI_YA" : "40",
		"PER_ZHI_YA" : "40",
		"YIN_CHENG_ZHI_YA" : "70",
		"CREDIT_CARD_AMOUNT" : "300",
		"ADD_DIS_CREDIT_CARD" : "600",
		"DISCOUNT_AMOUNT" : "600",
		"DISCOUNT_DAY_AVG" : "5",
		"ADD_DIS_DISCOUNT" : "280",
		"GLB_NUM" : "200",
		"JIE_SHOU_HUI" : "75",
		"GLB_INCOME" : "100",
		"LOAN_LX_INCOME" : "30",
		"ADD_SAVE_LX_OUT" : "20",
		"TUO_LOAN_AMOUNT" : "10",
		"FIVE_CLASS_THREE_AMOUNT" : "30",
		"TAB_IN_OUT_QIAN_LX" : "10"
	}]
};
store.loadData(memberData);

// 每页显示条数下拉选择框
var pagesize_combo = new Ext.form.ComboBox( {
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
store.load( {
	params : {
		start : 0,
		limit : parseInt(pagesize_combo.getValue())
	}
});

// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
});
// 分页工具栏
var bbar = new Ext.PagingToolbar( {
	pageSize : parseInt(pagesize_combo.getValue()),
	store : store,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
});

// 表格实例
var grid = new Ext.grid.GridPanel( {
	title : '对公客户业务统计明细列表',
	// loyout:'fit',
	width : document.body.scrollWidth,
	height : 360,
	frame : true,
	autoScroll : true,
	store : store,
	stripeRows : true, // 斑马线
	cm : cm, // 列模型
	tbar :  new Ext.Toolbar({
        items  : ['数据日期：　　　　年　　月　　日','　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　币种：',
                  '->','单位：万元']
    }), // 表格工具栏
	bbar : bbar,// 分页工具栏
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	},
	plugins : group
});