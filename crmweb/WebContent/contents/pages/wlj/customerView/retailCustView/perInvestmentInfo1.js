/**
*@description 360客户视图对私 投资信息 -- 客户他行资产负债信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
var url =  basepath + '/lookup.json?name=OB_LAIB_TYPE';
var fields = [{name:'CUST_ID',text:'隐藏字段',hidden:true}];

Ext.onReady(function() {
// 他行负债类型
var obLaibTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=OB_LAIB_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
obLaibTypeStore.load();
// 他行资产类型
var obAssetsTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=OB_ASSETS_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
obAssetsTypeStore.load();//一调load就是调用AJAX了！
//客户他行资产负债信息计算表单
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
						fieldLabel : '资产合计',
						id : 'otherAssetSum',
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
						fieldLabel : '负债合计',
						id : 'otherDebtSum',
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
						fieldLabel : '净资产',
						id : 'otherNetAssetSum',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						viewFn: money('0,000.00'),
						readOnly : true
					} ]
				} ]
			} ]
});
//客户他行资产信息行号
var teamrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 32
});
var teamsm = new Ext.grid.CheckboxSelectionModel();
//客户他行资产信息定义列模型
var teamcm = new Ext.grid.ColumnModel( [ teamrownum, {
	header : '资产类型',
	dataIndex : 'otherAssetType',
	width : 210,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = obAssetsTypeStore.find('key', value);//KV，对应的数字序号
			return obAssetsTypeStore.getAt(index).get('value');//结果就是得到中文
		}
	}
}, {
	header : '金额(元)',
	dataIndex : 'otherAssetAmountValue',//金额, 在刷的时候瞄准dataIndex进来
//	editor : new Ext.form.TextField( {
//		// regex:/^[+-]?\d*\.?\d{1,2}$/
//			// maskRe : '-'
//			regex : /^\d*[.]?(\d{0,2})?$/
//		}),
	width : 210,
	sortable : true,
	renderer : function(value) {//和[主移动表]出现时的view一样
		if (value != '' && value != null) {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
}
]);
//客户他行资产信息数据存储
var teamstore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/FinancialAnalysis!amoutValue.json'
	}),
	reader : new Ext.data.JsonReader( {
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'otherAssetType',
		mapping : 'ASSETS_TYPE'//json回传的对应数据库中的，字段名(原装)
	}, {
		name : 'otherAssetAmountValue',
		mapping : 'AMOUNT_VALUE'
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
		BELONG_TYPE : 1,
		ASSET_DEBT_TYPE : 1,
		CUST_ID : _custId,//有了,外表点击传入
		ASSETS_TYPE : 'OB_ASSETS_TYPE'
	}
});

//客户他行负债信息行号
var teamrownum2 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 32
});
var teamsm2 = new Ext.grid.CheckboxSelectionModel();
//客户他行负债信息定义列模型
var teamcm2 = new Ext.grid.ColumnModel( [ teamrownum2, {
	header : '负债类型',
	dataIndex : 'otherDebtType',
	width : 180,
	sortable : false,
	renderer : function(value) {
		if (value != '') {
			var index = obLaibTypeStore.find('key', value);
			return obLaibTypeStore.getAt(index).get('value');
		}

	}
}, {
	header : '金额(元)',
	dataIndex : 'otherDebtAmountValue',
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
//客户他行资产信息数据存储
var teamstore2 = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/FinancialAnalysis!amoutValue.json'
	}),
	reader : new Ext.data.JsonReader( {
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'otherDebtType',
		mapping : 'ASSETS_TYPE'
	}, {
		name : 'otherDebtAmountValue',
		mapping : 'AMOUNT_VALUE'
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
		BELONG_TYPE : 1,
		ASSET_DEBT_TYPE : 2,
		CUST_ID : _custId,
		ASSETS_TYPE : 'OB_LAIB_TYPE'
	},
	callback : function(){
        var task = new Ext.util.DelayedTask(sum); 
        task.delay(50); 
	}
});

function sum() {
	var otherAssetSum = 0;
	var otherDebtSum = 0;
	var otherNetAssetSum = 0;
	var records = teamgrid.getStore().data.items;
	for ( var r = 0; r < records.length; r++) {
		var a = records[r].data.otherAssetAmountValue;
		if (a == '' || a == null)
			a = 0;
		otherAssetSum += parseInt(a);
	}
	var records2 = teamgrid2.getStore().data.items;
	for ( var r = 0; r < records2.length; r++) {
		var a = records2[r].data.otherDebtAmountValue;
		if (a == '' || a == null)
			a = 0;
		otherDebtSum += parseInt(a);
	}
	otherNetAssetSum = otherAssetSum - otherDebtSum;
	Ext.getCmp('otherAssetSum').setValue(otherAssetSum);
	Ext.getCmp('otherDebtSum').setValue(otherDebtSum);
	Ext.getCmp('otherNetAssetSum').setValue(otherNetAssetSum);
	
}
//客户他行资产信息
var teamgrid = new Ext.grid.EditorGridPanel( {
	title : '资产信息',
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
	title : '负债信息',
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
var tabs = new Ext.Panel( {
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
		items:[tabs]
	} ]
});
});