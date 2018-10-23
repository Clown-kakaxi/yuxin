//合并单元格
var continentGroupRow2 = [ {
	header : '',
	colspan : 7,
	align : 'center'
}, {
	header : '一般性存款（月/季/年)累计日均',
	colspan : 9,
	align : 'center'
}, {
	header : '一般性存款余额',
	colspan : 9,
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
			name : 'askedSave',
			mapping : 'ASKED_SAVE'
		}, {
			name : 'threeMonthBelow',
			mapping : 'THREE_MONTH_BELOW'
		}, {
			name : 'sixMonth',
			mapping : 'SIX_MONTH'
		}, {
			name : 'oneYear',
			mapping : 'ONE_YEAR'
		}, {
			name : 'twoYears',
			mapping : 'TWO_YEARS'
		}, {
			name : 'threeYears',
			mapping : 'THREE_YEARS'
		}, {
			name : 'fiveYearsAndUp',
			mapping : 'FIVE_YEARS_AND_UP'
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
					header : '活期存款（含保证金活期）',
					width : 150,
					align : 'right',
					dataIndex : 'huoqiSave',
					sortable : true
				}, {
					header : '定期存款（含保证金定期）',
					width : 150,
					align : 'right',
					dataIndex : 'dingqiSave',
					sortable : true
				}, {
					header : '通知存款',
					width : 150,
					align : 'right',
					dataIndex : 'askedSave',
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
					dataIndex : 'twoYears',
					sortable : true
				}, {
					header : '三年',
					width : 150,
					align : 'right',
					dataIndex : 'threeYears',
					sortable : true
				}, {
					header : '5年及以上',
					width : 150,
					align : 'right',
					dataIndex : 'fiveYearsAndUp',
					sortable : true
				}, {
					header : '活期存款（含保证金活期）',
					width : 150,
					align : 'right',
					dataIndex : 'huoqiSave',
					sortable : true
				}, {
					header : '定期存款（含保证金定期）',
					width : 150,
					align : 'right',
					dataIndex : 'dingqiSave',
					sortable : true
				}, {
					header : '通知存款',
					width : 150,
					align : 'right',
					dataIndex : 'askedSave',
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
					dataIndex : 'twoYears',
					sortable : true
				}, {
					header : '三年',
					width : 150,
					align : 'right',
					dataIndex : 'threeYears',
					sortable : true
				}, {
					header : '5年及以上',
					width : 150,
					align : 'right',
					dataIndex : 'fiveYearsAndUp',
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
		"CUST_MGR_NAME" : '赵兰',
		"XC_NO" : "10005",
		"AT_SUB_ORG_NAME" : "华夏华夏分行",
		"AT_ORG_NAME" : "华夏天津营业部",
		"JION_BANK_YEARS" : "2005-02",
		"POSITION_YEARS" : "7",
		"HUOQI_SAVE" : "3,000",
		"DINGQI_SAVE" : "500",
		"ASKED_SAVE" : "500",
		"THREE_MONTH_BELOW" : "1,000",
		"SIX_MONTH" : "500",
		"ONE_YEAR" : "300",
		"TWO_YEARS" : "200",
		"THREE_YEARS" : "300",
		"FIVE_YEARS_AND_UP" : "200",
		"HUOQI_SAVE" : "3,000",
		"DINGQI_SAVE" : "500",
		"ASKED_SAVE" : "500",
		"THREE_MONTH_BELOW" : "1,000",
		"SIX_MONTH" : "500",
		"ONE_YEAR" : "300",
		"TWO_YEARS" : "200",
		"THREE_YEARS" : "300",
		"FIVE_YEARS_AND_UP" : "200"
	}, {
		"ID" : "2",
		"CUST_MGR_NAME" : '王军胜',
		"XC_NO" : "10006",
		"AT_SUB_ORG_NAME" : "华夏北京分行",
		"AT_ORG_NAME" : "公司业务部",
		"JION_BANK_YEARS" : "2008-09",
		"POSITION_YEARS" : "4",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "1,000",
		"ASKED_SAVE" : "700",
		"THREE_MONTH_BELOW" : "2,000",
		"SIX_MONTH" : "1,000",
		"ONE_YEAR" : "1,000",
		"TWO_YEARS" : "300",
		"THREE_YEARS" : "600",
		"FIVE_YEARS_AND_UP" : "400",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "1,000",
		"ASKED_SAVE" : "700",
		"THREE_MONTH_BELOW" : "2,000",
		"SIX_MONTH" : "1,000",
		"ONE_YEAR" : "1,000",
		"TWO_YEARS" : "300",
		"THREE_YEARS" : "600",
		"FIVE_YEARS_AND_UP" : "400"
	}, {
		"ID" : "3",
		"CUST_MGR_NAME" : '张善军',
		"XC_NO" : "10007",
		"AT_SUB_ORG_NAME" : "华夏北京银行",
		"AT_ORG_NAME" : "华夏北京支行",
		"JION_BANK_YEARS" : "2009-09",
		"POSITION_YEARS" : "3",
		"HUOQI_SAVE" : "10,000",
		"DINGQI_SAVE" : "2,000",
		"ASKED_SAVE" : "1,000",
		"THREE_MONTH_BELOW" : "5,000",
		"SIX_MONTH" : "2,000",
		"ONE_YEAR" : "2,000",
		"TWO_YEARS" : "1,000",
		"THREE_YEARS" : "1,000",
		"FIVE_YEARS_AND_UP" : "1,000",
		"HUOQI_SAVE" : "10,000",
		"DINGQI_SAVE" : "2,000",
		"ASKED_SAVE" : "1,000",
		"THREE_MONTH_BELOW" : "5,000",
		"SIX_MONTH" : "2,000",
		"ONE_YEAR" : "2,000",
		"TWO_YEARS" : "1,000",
		"THREE_YEARS" : "1,000",
		"FIVE_YEARS_AND_UP" : "1,000"
	}, {
		"ID" : "4",
		"CUST_MGR_NAME" : '李宪生',
		"XC_NO" : "10008",
		"AT_SUB_ORG_NAME" : "华夏天津分行",
		"AT_ORG_NAME" : "公司业务部",
		"JION_BANK_YEARS" : "2007-09",
		"POSITION_YEARS" : "5",
		"HUOQI_SAVE" : "4,000",
		"DINGQI_SAVE" : "400",
		"ASKED_SAVE" : "300",
		"THREE_MONTH_BELOW" : "2,000",
		"SIX_MONTH" : "1,000",
		"ONE_YEAR" : "500",
		"TWO_YEARS" : "500",
		"THREE_YEARS" : "200",
		"FIVE_YEARS_AND_UP" : "200",
		"HUOQI_SAVE" : "4,000",
		"DINGQI_SAVE" : "400",
		"ASKED_SAVE" : "300",
		"THREE_MONTH_BELOW" : "2,000",
		"SIX_MONTH" : "1,000",
		"ONE_YEAR" : "500",
		"TWO_YEARS" : "500",
		"THREE_YEARS" : "200",
		"FIVE_YEARS_AND_UP" : "200"
	}, {
		"ID" : "5",
		"CUST_MGR_NAME" : '萧红',
		"XC_NO" : "10009",
		"AT_SUB_ORG_NAME" : "华夏北京支行",
		"AT_ORG_NAME" : "营销部",
		"JION_BANK_YEARS" : "2006-09",
		"POSITION_YEARS" : "6",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "500",
		"ASKED_SAVE" : "200",
		"THREE_MONTH_BELOW" : "2,000",
		"SIX_MONTH" : "1,000",
		"ONE_YEAR" : "1,000",
		"TWO_YEARS" : "1,000",
		"THREE_YEARS" : "200",
		"FIVE_YEARS_AND_UP" : "300",
		"HUOQI_SAVE" : "5,000",
		"DINGQI_SAVE" : "500",
		"ASKED_SAVE" : "200",
		"THREE_MONTH_BELOW" : "2,000",
		"SIX_MONTH" : "1,000",
		"ONE_YEAR" : "1,000",
		"TWO_YEARS" : "1,000",
		"THREE_YEARS" : "200",
		"FIVE_YEARS_AND_UP" : "300"
	} ]
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
	title : '客户经理分类业务情况列表',
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