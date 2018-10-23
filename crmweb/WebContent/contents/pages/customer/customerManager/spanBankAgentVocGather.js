//合并单元格
var continentGroupRow = [ {
	header : '',
	colspan : 2,
	align : 'center'
}, {
	header : '推荐存款业务',
	colspan : 5,
	align : 'center'
}, {
	header : '经办存款业务',
	colspan : 5,
	align : 'center'
} ];

var group = new Ext.ux.grid.ColumnHeaderGroup( {
	rows : [ continentGroupRow ]
// continentGroupRow,
		});
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
//record
var record = Ext.data.Record.create([{
	name : 'Id',
	mapping : 'ID'
},{
	name : 'subOrgName',
	mapping : 'SUB_ORG_NAME'
},{
	name : 'custNum',
	mapping : 'CUST_NUM'
},{
	name : 'saveAmount',
	mapping : 'SAVE_AMOUNT'
},{
	name : 'saveDayAvgYear',
	mapping : 'SAVE_DAY_AVG_YEAR'
},{
	name : 'saveDayAvgMonth',
	mapping : 'SAVE_DAY_AVG_MONTH'
},{
	name : 'saveDayAvgSeason',
	mapping : 'SAVE_DAY_AVG_SEASON'
},{
	name : 'custNum1',
	mapping : 'CUST_NUM1'
},{
	name : 'saveAmount1',
	mapping : 'SAVE_AMOUNT1'
},{
	name : 'saveDayAvgYear1',
	mapping : 'SAVE_DAY_AVG_YEAR1'
},{
	name : 'saveDayAvgMonth1',
	mapping : 'SAVE_DAY_AVG_MONTH1'
},{
	name : 'saveDayAvgSeason1',
	mapping : 'SAVE_DAY_AVG_SEASON1'
}]);

// 定义列模型
var cm = new Ext.grid.ColumnModel([// rownum,
    {
	header : 'No.',
	width : 50,
	align : 'center',
	dataIndex : 'Id',
	sortable : true
},{
	header : '分行名称',
	width : 150,
	align : 'left',
	dataIndex : 'subOrgName',
	sortable : true
}, {
	header : '客户数量',
	width : 150,
	align : 'left',
	dataIndex : 'custNum',
	sortable : true
},{
	header : '存款余额',
	width : 150,
	align : 'right',
	dataIndex : 'saveAmount',
	sortable : true
}, {
	header : '存款日均（本年）',
	width : 150,
	align : 'right',
	dataIndex : 'saveDayAvgYear',
	sortable : true
}, {
	header : '存款日均（本季）',
	width : 150,
	align : 'right',
	dataIndex : 'saveDayAvgSeason',
	sortable : true
}, {
	header : '存款日均（本月）',
	width : 150,
	align : 'right',
	dataIndex : 'saveDayAvgMonth',
	sortable : true
}, {
	header : '客户数量',
	width : 150,
	align : 'left',
	dataIndex : 'custNum1',
	sortable : true
},{
	header : '存款余额',
	width : 150,
	align : 'right',
	dataIndex : 'saveAmount1',
	sortable : true
}, {
	header : '存款日均（本年）',
	width : 150,
	align : 'right',
	dataIndex : 'saveDayAvgYear1',
	sortable : true
}, {
	header : '存款日均（本季）',
	width : 150,
	align : 'right',
	dataIndex : 'saveDayAvgSeason1',
	sortable : true
}, {
	header : '存款日均（本月）',
	width : 150,
	align : 'right',
	dataIndex : 'saveDayAvgMonth1',
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
		{"rownum":"1","ID":'1',"SUB_ORG_NAME":"北京分行","CUST_NUM":"100","SAVE_AMOUNT":"50,000.00","SAVE_DAY_AVG_YEAR":"200.00","SAVE_DAY_AVG_SEASON":"260.00","SAVE_DAY_AVG_MONTH":"300.00",
			"CUST_NUM1":"200","SAVE_AMOUNT1":"100,000.00","SAVE_DAY_AVG_YEAR1":"400.00","SAVE_DAY_AVG_SEASON1":"500.00","SAVE_DAY_AVG_MONTH1":"540.00"}
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
	//title : '跨行代理业务汇总列表',
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
	},
	plugins : group
});