// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
//record
var record = Ext.data.Record.create([{
		name : 'countId',
		mapping : 'COUNT_ID'
	},{
		name : 'custName',
		mapping : 'CUST_NAME'
	},{
		name : 'subOrgName',
		mapping : 'SUB_ORG_NAME'
	},{
		name : 'openaccountBank',
		mapping : 'OPEN_ACCOUNT_BANK'
	},{
		name : 'saveAmount',
		mapping : 'SAVE_AMOUNT'
	},{
		name : 'saveDayAvg',
		mapping : 'SAVE_DAY_AVG'
	},{
		name : 'loanAmount',
		mapping : 'LOAN_AMOUNT'
	},{
		name : 'loanDayAvg',
		mapping : 'LOAN_DAY_AVG'
	},{
		name : 'acceptAmount',
		mapping : 'ACCEPT_AMOUNT'
	},{
		name : 'addAccept',
		mapping : 'ADD_ACCEPT'
	},{
		name : 'discountAmount',
		mapping : 'DISCOUNT_AMOUNT'
	},{
		name : 'addDiscount',
		mapping : 'ADD_DISCOUNT'
	},{
		name : 'addSettingNum',
		mapping : 'ADD_SETTIN_NUM'
	},{
		name : 'validCreditLine',
		mapping : 'VALID_CREDIT_LINE'
}]);

// 定义列模型
var cm = new Ext.grid.ColumnModel([ rownum,{
	header : 'ID',
	width : 190,
	align : 'center',
	hidden:true,
	dataIndex : 'countId',
	sortable : true
},{
	header : '客户名称',
	width : 190,
	align : 'left',
	dataIndex : 'custName',
	sortable : true
}, {
	header : '分行',
	width : 190,
	align : 'left',
	dataIndex : 'subOrgName',
	sortable : true
}, {
	header : '开户网点',
	width : 180,
	align : 'left',
	dataIndex : 'openaccountBank',
	sortable : true
},{
	header : '存款余额',
	width : 180,
	align : 'right',
	dataIndex : 'saveAmount',
	sortable : true
}, {
	header : '存款日均',
	width : 180,
	align : 'right',
	dataIndex : 'saveDayAvg',
	sortable : true
},{
	header : '贷款余额',
	width : 180,
	align : 'right',
	dataIndex : 'loanAmount',
	sortable : true
}, {
	header : '贷款日均',
	width : 180,
	align : 'right',
	dataIndex : 'loanDayAvg',
	sortable : true
},{
	header : '承兑金额',
	width : 180,
	align : 'right',
	dataIndex : 'acceptAmount',
	sortable : true
}, {
	header : '承兑累计',
	width : 180,
	align : 'right',
	dataIndex : 'addAccept',
	sortable : true
},{
	header : '贴现余额',
	width : 180,
	align : 'right',
	dataIndex : 'discountAmount',
	sortable : true
}, {
	header : '贴现累计',
	width : 180,
	align : 'right',
	dataIndex : 'addDiscount',
	sortable : true
},{
	header : '累计结算量',
	width : 180,
	align : 'right',
	dataIndex : 'addSettingNum',
	sortable : true
}, {
	header : '有效授信额度 ',
	width : 180,
	align : 'right',
	dataIndex : 'validCreditLine',
	sortable : true
}]);

/**
 * 数据存储
 */
var store = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/channelCust.json'
	}),
	reader : new Ext.data.JsonReader({
//		successProperty : 'success',
//		idProperty : 'COUNT_ID',
//		messageProperty : 'message',
//		root : 'json.data',
//		totalProperty : 'json.count'
		totalProperty:'num',// 记录总数
		root:'rows'// Json中的列表数据根节点
	}, record)
});

var memberData= {
		TOTALCOUNT:3,
		rows:[
		{"rownum":"1","CUST_NAME":"广东宏泽集团有限公司","SUB_ORG_NAME":"北京分行","OPEN_ACCOUNT_BANK":"北京朝阳支行","SAVE_AMOUNT":"1,500.00","SAVE_DAY_AVG":"1,200.00","LOAN_AMOUNT":"1,000.00",
			"LOAN_DAY_AVG":"800.00","ACCEPT_AMOUNT":"500.00","ADD_ACCEPT":"300.00","DISCOUNT_AMOUNT":"100.00","ADD_DISCOUNT":"250.00","ADD_SETTIN_NUM":"2,700.00","VALID_CREDIT_LINE":"5,000.00"}
		]
	};
store.loadData(memberData);
		
// 每页显示条数下拉选择框
var pagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
				[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	editable : false,
	width : 85
});

// 默认加载数据
store.load({
	params : {
		start : 0,
		limit : parseInt(pagesize_combo.getValue())
	}
});

// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo.getValue()), store
			.reload({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});
});
// 分页工具栏
var bbar = new Ext.PagingToolbar({
	pageSize : parseInt(pagesize_combo.getValue()),
	store : store,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
});

// 表格实例
var grid = new Ext.grid.GridPanel({
	//title : '跨分行开户客户明细列表',
	frame : true,
	width : document.body.scrollWidth,
	height:document.body.scrollHeight-120,
	region : 'center',
	store : store,
	stripeRows : true, // 斑马线
	cm : cm, // 列模型
	tbar :  new Ext.Toolbar({
        items  : ['数据日期：　　　　年　　月','->','单位：万元']
    }), // 表格工具栏
	bbar : bbar,// 分页工具栏
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});