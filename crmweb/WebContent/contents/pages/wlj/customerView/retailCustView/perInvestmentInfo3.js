/**
*@description 360客户视图对私 投资信息 -- 客户家庭月度收支信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
var url =  basepath + '/lookup.json?name=IN_TYPE';
var fields = [{name:'CUST_ID',text:'',hidden:true}];
Ext.onReady(function() {
// 收入类别
var detialInTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=IN_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
detialInTypeStore.load();

// 支出类别
var detialOutTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=OUT_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
detialOutTypeStore.load();
//客户家庭支出信息计算表单
var teamForm = new Ext.form.FormPanel(
		{
			labelWidth : 90, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 93,
			items : [ {
				layout : 'column',
				border : false,
				items : [ {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 100, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [ {
						fieldLabel : '收入合计',
						id : 'monthInSum',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						viewFn: money('0,000.00'),
						readOnly : true
					} ]
				}, {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 100, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [ {
						fieldLabel : '支出合计',
						id : 'monthOutSum',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						viewFn: money('0,000.00'),
						readOnly : true
					} ]
				}, {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 100, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [ {
						fieldLabel : '盈余',
						id : 'monthNetSum',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						viewFn: money('0,000.00'),
						readOnly : true
					} ]
				} ]
			} ]
});
//客户家庭月度收入信息行号
var teamrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 32
});
var teamsm = new Ext.grid.CheckboxSelectionModel();
// 客户家庭月度收入信息定义列模型
var teamcm = new Ext.grid.ColumnModel( [ teamrownum, {
	header : '收入类型',
	dataIndex : 'detialType',
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = detialInTypeStore.find('key', value);
			return detialInTypeStore.getAt(index).get('value');
		}

	}
}, {
	header : '金额(元)',
	dataIndex : 'money',
//	editor : new Ext.form.TextField( {
//		// regex:/^[+-]?\d*\.?\d{1,2}$/
//			// maskRe : '-'
//			regex : /^\d*[.]?(\d{0,2})?$/
//		}),
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);
//客户其他资产信息数据存储
var teamstore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/FinancialAnalysis!monthValue.json'
	}),
	reader : new Ext.data.JsonReader( {
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'detialType',
		mapping : 'DETIAL_TYPE'
	}, {
		name : 'money',
		mapping : 'MONEY'
	}, {
		name : 'flag',
		mapping : 'flag'
	}, {
		name : 'custId',
		mapping : 'CUST_ID'
	}, {
		name : 'infoId',
		mapping : 'INFO_ID'
	} ])
});
teamstore.load( {
	params : {
		IO_TYPE : '1',
		CUST_ID : _custId
	}
});
//客户家庭月度支出信息行号
var teamrownum2 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 28
});
var teamsm2 = new Ext.grid.CheckboxSelectionModel();
// 客户家庭月度支出信息定义列模型
var teamcm2 = new Ext.grid.ColumnModel( [ teamrownum2, {
	header : '支出类型',
	dataIndex : 'detialType',
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = detialOutTypeStore.find('key', value);
			return detialOutTypeStore.getAt(index).get('value');
		}
	}
}, {
	header : '金额(元)',
	dataIndex : 'money',
//	editor : new Ext.form.TextField( {
//		// regex:/^[+-]?\d*\.?\d{1,2}$/
//			// maskRe : '-'
//			regex : /^\d*[.]?(\d{0,2})?$/
//		}),
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);

var teamstore2 = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/FinancialAnalysis!monthValue.json'
	}),
	reader : new Ext.data.JsonReader( {
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'detialType',
		mapping : 'DETIAL_TYPE'
	}, {
		name : 'money',
		mapping : 'MONEY'
	}, {
		name : 'flag',
		mapping : 'flag'
	}, {
		name : 'custId',
		mapping : 'CUST_ID'
	}, {
		name : 'infoId',
		mapping : 'INFO_ID'
	} ])
});
teamstore2.load( {
	params : {
		IO_TYPE : '2',
		CUST_ID : _custId
	},
	callback : function(){
		sum();
	}
});

function sum() {
	var monthIn = 0;
	var monthOut = 0;
	var monthNet = 0;

	var records = teamgrid.getStore().data.items;
	for ( var r = 0; r < records.length; r++) {
		var a = records[r].data.money;
		if (a == '' || a == null)
			a = 0;
		monthIn += parseInt(a);
	}
	var records2 = teamgrid2.getStore().data.items;
	for ( var r = 0; r < records2.length; r++) {
		var a = records2[r].data.money;
		if (a == '' || a == null)
			a = 0;
		monthOut += parseInt(a);
	}
	monthNet = monthIn - monthOut;
	Ext.getCmp('monthInSum').setValue(monthIn);
	Ext.getCmp('monthOutSum').setValue(monthOut);
	Ext.getCmp('monthNetSum').setValue(monthNet);
}
//客户他行资产信息
var teamgrid = new Ext.grid.EditorGridPanel( {
	title : '收入信息',
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm, // 列模型
	sm : teamsm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//客户他行负债信息
var teamgrid2 = new Ext.grid.EditorGridPanel( {
	title : '支出信息',
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore2, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm2, // 列模型
	sm : teamsm2,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//客户他行资产负债信息面板
var tab_1 = new Ext.Panel( {
	layout : 'form',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			items : [ teamgrid ]
		},{
			columnWidth : .5,
			items : [ teamgrid2 ]
			}]
	}, teamForm ]
});

// 页面视图
var viewport = new Ext.Viewport({
	layout : 'fit',
	items : [{
		items:[tab_1]
	} ]
});
});