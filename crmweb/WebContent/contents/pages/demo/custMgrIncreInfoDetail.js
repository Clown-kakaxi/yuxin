//合并单元格
var continentGroupRow2 = [ {
			header : '',
			colspan : 7,
			align : 'center'
		}, {
			header : '一般性存款年累计日均增量',
			colspan : 11,
			align : 'center'
		}, {
			header : '一般性存款余额增量',
			colspan : 11,
			align : 'center'
		}, {
			header : '同业存款增量',
			colspan : 6,
			align : 'center'
		}, {
			header : '非贸易融资单位贷款纯贷款日均增量',
			colspan : 7,
			align : 'center'
		}, {
			header : '单位贸易融资纯贷款日均增量',
			colspan : 7,
			align : 'center'
		}, {
			header : '个人贷款纯贷款日均增量',
			colspan : 7,
			align : 'center'
		}, {
			header : '非贸易融资单位贷款纯贷款余额增量',
			colspan : 7,
			align : 'center'
		}, {
			header : '单位贸易融资纯贷款余额增量',
			colspan : 7,
			align : 'center'
		}, {
			header : '个人贷款纯贷款余额增量',
			colspan : 7,
			align : 'center'
		}, {
			header : '贴现业务',
			colspan : 3,
			align : 'center'
		}, {
			header : '银行承兑汇票余额担保方式',
			colspan : 5,
			align : 'center'
		}, {
			header : '累计签发银行承兑汇票担保方式',
			colspan : 5,
			align : 'center'
		}, {
			header : '信用证',
			colspan : 2,
			align : 'center'
		}, {
			header : '不良贷款率',
			colspan : 3,
			align : 'center'
		}, {
			header : '利息回收率',
			colspan : 4,
			align : 'center'
		}, {
			header : '国际业务量',
			colspan : 3,
			align : 'center'
		}, {
			header : '中间业务收入',
			colspan : 2,
			align : 'center'
		}, {
			header : '对公客户数量',
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
			name : 'joinBankYears',
			mapping : 'JION_BANK_YEARS'
		}, {
			name : 'positionYears',
			mapping : 'POSITION_YEARS'
		}, {
			name : 'huoqiSave',
			mapping : 'HUOQI_SAVE'
		}, {
			name : 'dingqiSave',
			mapping : 'DINGQI_SAVE'
		}, {
			name : 'forPubSaveDayAvg',
			mapping : 'FOR_PUB_SAVE_DAY_AVG'
		}, {
			name : 'unitHuoSave',
			mapping : 'UNIT_HUO_SAVE'
		}, {
			name : 'unitDingSave',
			mapping : 'UNIT_DING_SAVE'
		}, {
			name : 'saveBoAmount',
			mapping : 'SAVE_BO_AMOUNT'
		}, {
			name : 'huoqi',
			mapping : 'HUO_QI'
		}, {
			name : 'dingqi',
			mapping : 'DING_QI'
		}, {
			name : 'disSaveDayAvg',
			mapping : 'DIS_SAVE_DAY_AVG'
		}, {
			name : 'disHuoSave',
			mapping : 'DIS_HUO_SAVE'
		}, {
			name : 'disDingSave',
			mapping : 'DIS_DING_SAVE'
		}, {
			name : 'dayAvg',
			mapping : 'DAY_AVG'
		}, {
			name : 'amount',
			mapping : 'AMOUNT'
		}, {
			name : 'upfloat',
			mapping : 'UP_FLOAT'
		}, {
			name : 'standa',
			mapping : 'STANDA'
		}, {
			name : 'downFloat',
			mapping : 'DOWN_FLOAT'
		}, {
			name : 'diYa',
			mapping : 'DI_YA'
		}, {
			name : 'zhiYa',
			mapping : 'ZHI_YA'
		}, {
			name : 'NotDiZhiYa',
			mapping : 'NOT_DI_ZHI_YA'
		}, {
			name : 'txAmount',
			mapping : 'TX_AMOUNT'
		}, {
			name : 'txDayAvg',
			mapping : 'TX_DAY_AVG'
		}, {
			name : 'addTx',
			mapping : 'ADD_TX'
		}, {
			name : 'quaneBo',
			mapping : 'QUANE_BO'
		}, {
			name : 'chaeBo',
			mapping : 'CHAE_BO'
		}, {
			name : 'unitZhiYa',
			mapping : 'UNIT_ZHI_YA'
		}, {
			name : 'perZhiYa',
			mapping : 'PER_ZHI_YA'
		}, {
			name : 'yinChengZhiYa',
			mapping : 'YIN_CHENG_ZHI_YA'
		}, {
			name : 'creditCardAmount',
			mapping : 'CREDIT_CARD_AMOUNT'
		}, {
			name : 'disCreditCard',
			mapping : 'DIS_CREDIT_CARD'
		}, {
			name : 'tuoLoanAmount',
			mapping : 'TUO_LOAN_AMOUNT'
		}, {
			name : 'fiveClassThreeAmount',
			mapping : 'FIVE_CLASS_THREE_AMOUNT'
		}, {
			name : 'fiveClassNotGoodRate',
			mapping : 'FIVE_CLASS_NOT_GOOD_RATE'
		}, {
			name : 'LxIncome',
			mapping : 'LX_INCOME'
		}, {
			name : 'tableInQianx',
			mapping : 'TABLE_IN_QIANX'
		}, {
			name : 'tableOutQianx',
			mapping : 'TABLE_OUT_QIANX'
		}, {
			name : 'LxRecRate',
			mapping : 'LX_REC_RATE'
		}, {
			name : 'addBecome',
			mapping : 'ADD_BECOME'
		}, {
			name : 'glbNum',
			mapping : 'GLB_NUM'
		}, {
			name : 'jieShouHui',
			mapping : 'JIE_SHOU_HUI'
		}, {
			name : 'addBecome',
			mapping : 'ADD_BECOME'
		}, {
			name : 'glbIncome',
			mapping : 'GLB_INCOME'
		}, {
			name : 'total',
			mapping : 'TOTAL'
		}, {
			name : 'addDayAvgUpFif',
			mapping : 'ADD_DAY_AVG_UP_FIF'
		}, {
			name : 'addDayAvgUpHun',
			mapping : 'ADD_DAY_AVG_UP_HUN'
		} ]);

// 定义列模型
var cm = new Ext.grid.ColumnModel( [ // rownum,
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
			header : '入行年月',
			width : 150,
			align : 'center',
			dataIndex : 'joinBankYears',
			sortable : true
		}, {
			header : '上岗年限',
			width : 150,
			align : 'center',
			dataIndex : 'positionYears',
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
			header : '对公存款日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'forPubSaveDayAvg',
			sortable : true
		}, {
			header : '单位活期存款',
			width : 150,
			align : 'right',
			dataIndex : 'unitHuoSave',
			sortable : true
		}, {
			header : '单位定期存款',
			width : 150,
			align : 'right',
			dataIndex : 'unitDingSave',
			sortable : true
		}, {
			header : '存入保证金',
			width : 150,
			align : 'right',
			dataIndex : 'saveBoAmount',
			sortable : true
		}, {
			header : '活期',
			width : 150,
			align : 'right',
			dataIndex : 'huoqi',
			sortable : true
		}, {
			header : '定期',
			width : 150,
			align : 'right',
			dataIndex : 'dingqi',
			sortable : true
		}, {
			header : '储蓄存款日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'disSaveDayAvg',
			sortable : true
		}, {
			header : '储蓄活期存款',
			width : 150,
			align : 'right',
			dataIndex : 'disHuoSave',
			sortable : true
		}, {
			header : '储蓄定期存款',
			width : 150,
			align : 'right',
			dataIndex : 'disDingSave',
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
			header : '对公存款余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'forPubSaveAmount',
			sortable : true
		}, {
			header : '单位活期存款',
			width : 150,
			align : 'right',
			dataIndex : 'unitHuoSave',
			sortable : true
		}, {
			header : '单位定期存款',
			width : 150,
			align : 'right',
			dataIndex : 'unitDingSave',
			sortable : true
		}, {
			header : '存入保证金',
			width : 150,
			align : 'right',
			dataIndex : 'saveBoAmount',
			sortable : true
		}, {
			header : '活期',
			width : 150,
			align : 'right',
			dataIndex : 'huoqi',
			sortable : true
		}, {
			header : '定期',
			width : 150,
			align : 'right',
			dataIndex : 'dingqi',
			sortable : true
		}, {
			header : '储蓄存款余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'disSaveAmount',
			sortable : true
		}, {
			header : '储蓄活期存款',
			width : 150,
			align : 'right',
			dataIndex : 'disHuoSave',
			sortable : true
		}, {
			header : '储蓄定期存款',
			width : 150,
			align : 'right',
			dataIndex : 'disDingSave',
			sortable : true
		}, {
			header : '日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'dayAvg',
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
			header : '余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'amount',
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
			header : '日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'dayAvg',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upfloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'standa',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '抵押',
			width : 150,
			align : 'right',
			dataIndex : 'diYa',
			sortable : true
		}, {
			header : '质押',
			width : 150,
			align : 'right',
			dataIndex : 'zhiYa',
			sortable : true
		}, {
			header : '非抵质押',
			width : 150,
			align : 'right',
			dataIndex : 'NotDiZhiYa',
			sortable : true
		}, {
			header : '日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'dayAvg',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upfloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'standa',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '抵押',
			width : 150,
			align : 'right',
			dataIndex : 'diYa',
			sortable : true
		}, {
			header : '质押',
			width : 150,
			align : 'right',
			dataIndex : 'zhiYa',
			sortable : true
		}, {
			header : '非抵质押',
			width : 150,
			align : 'right',
			dataIndex : 'NotDiZhiYa',
			sortable : true
		}, {
			header : '日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'dayAvg',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upfloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'standa',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '抵押',
			width : 150,
			align : 'right',
			dataIndex : 'diYa',
			sortable : true
		}, {
			header : '质押',
			width : 150,
			align : 'right',
			dataIndex : 'zhiYa',
			sortable : true
		}, {
			header : '非抵质押',
			width : 150,
			align : 'right',
			dataIndex : 'NotDiZhiYa',
			sortable : true
		}, {
			header : '余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'amount',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upfloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'standa',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '抵押',
			width : 150,
			align : 'right',
			dataIndex : 'diYa',
			sortable : true
		}, {
			header : '质押',
			width : 150,
			align : 'right',
			dataIndex : 'zhiYa',
			sortable : true
		}, {
			header : '非抵质押',
			width : 150,
			align : 'right',
			dataIndex : 'NotDiZhiYa',
			sortable : true
		}, {
			header : '余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'amount',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upfloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'standa',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '抵押',
			width : 150,
			align : 'right',
			dataIndex : 'diYa',
			sortable : true
		}, {
			header : '质押',
			width : 150,
			align : 'right',
			dataIndex : 'zhiYa',
			sortable : true
		}, {
			header : '非抵质押',
			width : 150,
			align : 'right',
			dataIndex : 'NotDiZhiYa',
			sortable : true
		}, {
			header : '余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'amount',
			sortable : true
		}, {
			header : '上浮',
			width : 150,
			align : 'right',
			dataIndex : 'upfloat',
			sortable : true
		}, {
			header : '基准',
			width : 150,
			align : 'right',
			dataIndex : 'standa',
			sortable : true
		}, {
			header : '下浮',
			width : 150,
			align : 'right',
			dataIndex : 'downFloat',
			sortable : true
		}, {
			header : '抵押',
			width : 150,
			align : 'right',
			dataIndex : 'diYa',
			sortable : true
		}, {
			header : '质押',
			width : 150,
			align : 'right',
			dataIndex : 'zhiYa',
			sortable : true
		}, {
			header : '非抵质押',
			width : 150,
			align : 'right',
			dataIndex : 'NotDiZhiYa',
			sortable : true
		}, {
			header : '贴现余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'txAmount',
			sortable : true
		}, {
			header : '贴现日均增量',
			width : 150,
			align : 'right',
			dataIndex : 'txDayAvg',
			sortable : true
		}, {
			header : '累计办理贴现增量',
			width : 150,
			align : 'right',
			dataIndex : 'addTx',
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
			dataIndex : 'unitZhiYa',
			sortable : true
		}, {
			header : '个人存贷质押',
			width : 150,
			align : 'right',
			dataIndex : 'perZhiYa',
			sortable : true
		}, {
			header : '银承质押',
			width : 150,
			align : 'right',
			dataIndex : 'yinChengZhiYa',
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
			dataIndex : 'unitZhiYa',
			sortable : true
		}, {
			header : '个人存贷质押',
			width : 150,
			align : 'right',
			dataIndex : 'perZhiYa',
			sortable : true
		}, {
			header : '银承质押',
			width : 150,
			align : 'right',
			dataIndex : 'yinChengZhiYa',
			sortable : true
		}, {
			header : '信用证余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'creditCardAmount',
			sortable : true
		}, {
			header : '开出信用证增量',
			width : 150,
			align : 'right',
			dataIndex : 'disCreditCard',
			sortable : true
		}, {
			header : '拖欠贷款余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'tuoLoanAmount',
			sortable : true
		}, {
			header : '五级分类后三类余额增量',
			width : 150,
			align : 'right',
			dataIndex : 'fiveClassThreeAmount',
			sortable : true
		}, {
			header : '五级分类不良率（%）变动',
			width : 150,
			align : 'center',
			dataIndex : 'fiveClassNotGoodRate',
			sortable : true
		}, {
			header : '利息收入增量',
			width : 150,
			align : 'right',
			dataIndex : 'LxIncome',
			sortable : true
		}, {
			header : '表内欠息增量',
			width : 150,
			align : 'right',
			dataIndex : 'tableInQianx',
			sortable : true
		}, {
			header : '表外欠息增量',
			width : 150,
			align : 'center',
			dataIndex : 'tableOutQianx',
			sortable : true
		}, {
			header : '表外欠息本日余额',
			width : 150,
			align : 'right',
			dataIndex : 'tableOutLocDayAmount',
			sortable : true
		}, {
			header : '利息回收率（%）变动',
			width : 150,
			align : 'center',
			dataIndex : 'LxRecRate',
			sortable : true
		}, {
			header : '累计实现增量',
			width : 150,
			align : 'right',
			dataIndex : 'addBecome',
			sortable : true
		}, {
			header : '国际业务结算量增量',
			width : 150,
			align : 'right',
			dataIndex : 'glbNum',
			sortable : true
		}, {
			header : '结售汇量增量',
			width : 150,
			align : 'right',
			dataIndex : 'jieShouHui',
			sortable : true
		}, {
			header : '累计实现增量',
			width : 150,
			align : 'right',
			dataIndex : 'addBecome',
			sortable : true
		}, {
			header : '其中：国际业务收入增量',
			width : 150,
			align : 'center',
			dataIndex : 'glbIncome',
			sortable : true
		}, {
			header : '总数增量',
			width : 150,
			align : 'right',
			dataIndex : 'total',
			sortable : true
		}, {
			header : '累计日均＞50万',
			width : 150,
			align : 'right',
			dataIndex : 'addDayAvgUpFif',
			sortable : true
		}, {
			header : '累计日均＞100万',
			width : 150,
			align : 'right',
			dataIndex : 'addDayAvgUpHun',
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
		"rownum" : "1",
		"ID" : "1",
		"CUST_MGR_NAME" : '赵兰',
		"XC_NO" : "10005",
		"AT_SUB_ORG_NAME" : "华夏华夏分行",
		"AT_ORG_NAME" : "华夏天津营业部",
		"JION_BANK_YEARS" : "2005-02",
		"POSITION_YEARS" : "7",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "490",
		"FOR_PUB_SAVE_DAY_AVG" : "15",
		"UNIT_HUO_SAVE" : "1,000",
		"UNIT_DING_SAVE" : '5',
		"SAVE_BO_AMOUNT" : "50",
		"HUO_QI" : "3,000",
		"DING_QI" : "500",
		"DIS_SAVE_DAY_AVG" : "15",
		"DIS_HUO_SAVE" : "1,000",
		"DIS_DING_SAVE" : "5",
		"DAY_AVG" : "1.5",
		"AMOUNT" : "2,300",
		"UP_FLOAT" : "50",
		"STANDA" : "3,000",
		"DOWN_FLOAT" : "50",
		"DI_YA" : "300",
		"ZHI_YA" : "500",
		"NOT_DI_ZHI_YA" : "2,300",
		"TX_AMOUNT" : "300",
		"TX_DAY_AVG" : "1",
		"ADD_TX" : "5",
		"QUANE_BO" : '430',
		"CHAE_BO" : "300",
		"UNIT_ZHI_YA" : "100",
		"PER_ZHI_YA" : "300",
		"YIN_CHENG_ZHI_YA" : "300",
		"CREDIT_CARD_AMOUNT" : "1,000",
		"DIS_CREDIT_CARD" : "2,200",
		"TUO_LOAN_AMOUNT" : "820",
		"FIVE_CLASS_THREE_AMOUNT" : "200",
		"FIVE_CLASS_NOT_GOOD_RATE" : "0.2",
		"LX_INCOME" : "100",
		"TABLE_IN_YEAR_AMOUNT" : '2,000',
		"TABLE_IN_LOC_DAY_AMOUNT" : "2,200",
		"TABLE_OUT_YEAR_AMOUNT" : "2,200",
		"TABLE_OUT_LOC_DAY_MOUNT" : "3,500",
		"LX_REC_RATE" : "0.8",
		"ADD_BECOME" : "400",
		"GLB_NUM" : "200",
		"JIE_SHOU_HUI" : "240",
		"ADD_BECOME" : "300",
		"GLB_INCOME" : "250",
		"TOTAL" : "5,000",
		"ADD_DAY_AVG_UP_FIF" : "2",
		"ADD_DAY_AVG_UP_HUN" : "1.2"
	},{
		"ID" : "2",
		"rownum" : "2",
		"CUST_MGR_NAME" : '王军胜',
		"XC_NO" : "10006",
		"AT_SUB_ORG_NAME" : "华夏北京分行",
		"AT_ORG_NAME" : "公司业务部",
		"JION_BANK_YEARS" : "2008-09",
		"POSITION_YEARS" : "4",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "490",
		"FOR_PUB_SAVE_DAY_AVG" : "15",
		"UNIT_HUO_SAVE" : "1,000",
		"UNIT_DING_SAVE" : '5',
		"SAVE_BO_AMOUNT" : "50",
		"HUO_QI" : "3,000",
		"DING_QI" : "500",
		"DIS_SAVE_DAY_AVG" : "15",
		"DIS_HUO_SAVE" : "1,000",
		"DIS_DING_SAVE" : "5",
		"DAY_AVG" : "1.5",
		"AMOUNT" : "3,000",
		"UP_FLOAT" : "50",
		"STANDA" : "3,000",
		"DOWN_FLOAT" : "50",
		"DI_YA" : "300",
		"ZHI_YA" : "500",
		"NOT_DI_ZHI_YA" : "2,000",
		"TX_AMOUNT" : "300",
		"TX_DAY_AVG" : "1",
		"ADD_TX" : "5",
		"QUANE_BO" : '400',
		"CHAE_BO" : "300",
		"UNIT_ZHI_YA" : "100",
		"PER_ZHI_YA" : "200",
		"YIN_CHENG_ZHI_YA" : "300",
		"CREDIT_CARD_AMOUNT" : "1,000",
		"DIS_CREDIT_CARD" : "2,000",
		"TUO_LOAN_AMOUNT" : "800",
		"FIVE_CLASS_THREE_AMOUNT" : "300",
		"FIVE_CLASS_NOT_GOOD_RATE" : "0.1",
		"LX_INCOME" : "100",
		"TABLE_IN_YEAR_AMOUNT" : '2,000',
		"TABLE_IN_LOC_DAY_AMOUNT" : "2,500",
		"TABLE_OUT_YEAR_AMOUNT" : "3,000",
		"TABLE_OUT_LOC_DAY_MOUNT" : "3,500",
		"LX_REC_RATE" : "0.8",
		"ADD_BECOME" : "500",
		"GLB_NUM" : "300",
		"JIE_SHOU_HUI" : "200",
		"ADD_BECOME" : "400",
		"GLB_INCOME" : "200",
		"TOTAL" : "8,000",
		"ADD_DAY_AVG_UP_FIF" : "3",
		"ADD_DAY_AVG_UP_HUN" : "1"
	},{
		"ID" : "3",
		"rownum" : "3",
		"CUST_MGR_NAME" : '张善军',
		"XC_NO" : "10007",
		"AT_SUB_ORG_NAME" : "华夏北京银行",
		"AT_ORG_NAME" : "华夏北京支行",
		"JION_BANK_YEARS" : "2009-09",
		"POSITION_YEARS" : "3",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "490",
		"FOR_PUB_SAVE_DAY_AVG" : "15",
		"UNIT_HUO_SAVE" : "1,000",
		"UNIT_DING_SAVE" : '5',
		"SAVE_BO_AMOUNT" : "50",
		"HUO_QI" : "3,000",
		"DING_QI" : "500",
		"DIS_SAVE_DAY_AVG" : "15",
		"DIS_HUO_SAVE" : "1,000",
		"DIS_DING_SAVE" : "5",
		"DAY_AVG" : "1.5",
		"AMOUNT" : "1,000",
		"UP_FLOAT" : "30",
		"STANDA" : "1,000",
		"DOWN_FLOAT" : "30",
		"DI_YA" : "100",
		"ZHI_YA" : "200",
		"NOT_DI_ZHI_YA" : "1,000",
		"TX_AMOUNT" : "200",
		"TX_DAY_AVG" : "1",
		"ADD_TX" : "3",
		"QUANE_BO" : '200',
		"CHAE_BO" : "200",
		"UNIT_ZHI_YA" : "100",
		"PER_ZHI_YA" : "100",
		"YIN_CHENG_ZHI_YA" : "200",
		"CREDIT_CARD_AMOUNT" : "800",
		"DIS_CREDIT_CARD" : "1,700",
		"TUO_LOAN_AMOUNT" : "600",
		"FIVE_CLASS_THREE_AMOUNT" : "230",
		"FIVE_CLASS_NOT_GOOD_RATE" : "0.3",
		"LX_INCOME" : "100",
		"TABLE_IN_YEAR_AMOUNT" : '1,900',
		"TABLE_IN_LOC_DAY_AMOUNT" : "2,100",
		"TABLE_OUT_YEAR_AMOUNT" : "2,400",
		"TABLE_OUT_LOC_DAY_MOUNT" : "2,700",
		"LX_REC_RATE" : "0.8",
		"ADD_BECOME" : "300",
		"GLB_NUM" : "200",
		"JIE_SHOU_HUI" : "200",
		"ADD_BECOME" : "200",
		"GLB_INCOME" : "150",
		"TOTAL" : "3,000",
		"ADD_DAY_AVG_UP_FIF" : "3",
		"ADD_DAY_AVG_UP_HUN" : "2.6"
	},{
		"ID" : "4",
		"rownum" : "4",
		"CUST_MGR_NAME" : '李宪生',
		"XC_NO" : "10008",
		"AT_SUB_ORG_NAME" : "华夏天津分行",
		"AT_ORG_NAME" : "公司业务部",
		"JION_BANK_YEARS" : "2007-09",
		"POSITION_YEARS" : "5",
		"HUOQI_SAVE" : "4,000",
		"DINGQI_SAVE" : "450",
		"FOR_PUB_SAVE_DAY_AVG" : "14",
		"UNIT_HUO_SAVE" : "1,000",
		"UNIT_DING_SAVE" : '4',
		"SAVE_BO_AMOUNT" : "50",
		"HUO_QI" : "2,000",
		"DING_QI" : "400",
		"DIS_SAVE_DAY_AVG" : "15",
		"DIS_HUO_SAVE" : "1,000",
		"DIS_DING_SAVE" : "50",
		"DAY_AVG" : "1.4",
		"AMOUNT" : "3,400",
		"UP_FLOAT" : "50",
		"STANDA" : "3,400",
		"DOWN_FLOAT" : "50",
		"DI_YA" : "340",
		"ZHI_YA" : "540",
		"NOT_DI_ZHI_YA" : "2,400",
		"TX_AMOUNT" : "340",
		"TX_DAY_AVG" : "1",
		"ADD_TX" : "5",
		"QUANE_BO" : '420',
		"CHAE_BO" : "320",
		"UNIT_ZHI_YA" : "120",
		"PER_ZHI_YA" : "220",
		"YIN_CHENG_ZHI_YA" : "320",
		"CREDIT_CARD_AMOUNT" : "1,300",
		"DIS_CREDIT_CARD" : "2,000",
		"TUO_LOAN_AMOUNT" : "850",
		"FIVE_CLASS_THREE_AMOUNT" : "350",
		"FIVE_CLASS_NOT_GOOD_RATE" : "0.15",
		"LX_INCOME" : "100",
		"TABLE_IN_YEAR_AMOUNT" : '2,500',
		"TABLE_IN_LOC_DAY_AMOUNT" : "2,500",
		"TABLE_OUT_YEAR_AMOUNT" : "3,100",
		"TABLE_OUT_LOC_DAY_MOUNT" : "3,100",
		"LX_REC_RATE" : "0.8",
		"ADD_BECOME" : "400",
		"GLB_NUM" : "200",
		"JIE_SHOU_HUI" : "200",
		"ADD_BECOME" : "300",
		"GLB_INCOME" : "200",
		"TOTAL" : "6,000",
		"ADD_DAY_AVG_UP_FIF" : "2",
		"ADD_DAY_AVG_UP_HUN" : "1"
	},{
		"ID" : "5",
		"rownum" : "5",
		"CUST_MGR_NAME" : '萧红',
		"XC_NO" : "10009",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "营销部",
		"JION_BANK_YEARS" : "2006-09",
		"POSITION_YEARS" : "6",
		"HUOQI_SAVE" : "3,000",
		"DINGQI_SAVE" : "550",
		"FOR_PUB_SAVE_DAY_AVG" : "15",
		"UNIT_HUO_SAVE" : "500",
		"UNIT_DING_SAVE" : '5',
		"SAVE_BO_AMOUNT" : "50",
		"HUO_QI" : "2,000",
		"DING_QI" : "500",
		"DIS_SAVE_DAY_AVG" : "15",
		"DIS_HUO_SAVE" : "500",
		"DIS_DING_SAVE" : "50",
		"DAY_AVG" : "1.5",
		"AMOUNT" : "2,000",
		"UP_FLOAT" : "40",
		"STANDA" : "3,000",
		"DOWN_FLOAT" : "40",
		"DI_YA" : "200",
		"ZHI_YA" : "400",
		"NOT_DI_ZHI_YA" : "3,000",
		"TX_AMOUNT" : "400",
		"TX_DAY_AVG" : "1",
		"ADD_TX" : "7",
		"QUANE_BO" : '400',
		"CHAE_BO" : "200",
		"UNIT_ZHI_YA" : "100",
		"PER_ZHI_YA" : "200",
		"YIN_CHENG_ZHI_YA" : "200",
		"CREDIT_CARD_AMOUNT" : "1,200",
		"DIS_CREDIT_CARD" : "2,100",
		"TUO_LOAN_AMOUNT" : "700",
		"FIVE_CLASS_THREE_AMOUNT" : "300",
		"FIVE_CLASS_NOT_GOOD_RATE" : "0.05",
		"LX_INCOME" : "100",
		"TABLE_IN_QIANX" : '2,000',
		"TABLE_OUT_QIANX" : "3,200",
		"LX_REC_RATE" : "0.7",
		"ADD_BECOME" : "520",
		"GLB_NUM" : "320",
		"JIE_SHOU_HUI" : "200",
		"ADD_BECOME" : "300",
		"GLB_INCOME" : "200",
		"TOTAL" : "8,000",
		"ADD_DAY_AVG_UP_FIF" : "1",
		"ADD_DAY_AVG_UP_HUN" : "1.2"
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
	title : '客户经理增量业务情况列表',
	// loyout:'fit',
	region : 'center',
	width : document.body.scrollWidth,
	height : 370,
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