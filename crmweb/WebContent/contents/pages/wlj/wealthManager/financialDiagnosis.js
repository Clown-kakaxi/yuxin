/**
 * 财务健康诊断Action
 * @author YOYOGI
 * 2014-7-16
 */
imports([
	'/contents/pages/fusionChart/tool/json2.js',
	'/contents/pages/fusionChart/tool/prettify.js',
	'/contents/pages/fusionChart/tool/FusionCharts.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
//初始化提示框
Ext.QuickTips.init();
//引入自定义函数
Ext.ns('Mis.Ext');
Mis.Ext.FormatCnMoney = function(v) {
	return '￥'+Ext.util.Format.number(v,'0,000.00');
};
WLJUTIL.alwaysLockCurrentView = true;//本功能页面锁定悬浮面板滑出
//查询url与提交
var url=basepath+"/financialDiagnosis.json";
//var comitUrl = basepath+'/FwModule-action.json';

var lookupTypes=[
];

var fields=[
	  {name:'ID', text:'报告编号', resutlWidth:150, hidden:true},
      {name:'CUST_ID', text:'客户编号', resutlWidth:150, searchField: true},
      {name:'CUST_NAME', text:'客户名称', resutlWidth:150, searchField: true},
      {name:'BAL', text:'本行资产(元)', resutlWidth:130, dataType:'number'},
      {name:'OTHER_BANK', text:'他行资产(元)', resutlWidth:130, dataType:'number'},
      {name:'OTHER_TYPE', text:'其他资产(元)', resutlWidth:130, dataType:'number'}
];

/**
 * tbar自定义工具栏-含"详情"
 * @type 
 */
var tbar =[{
	text:'下载报告',
	hidden:JsContext.checkGrant('financialD_download'),
	iconCls:'detailIconCss',
	handler:function() {
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择要操作的一行记录！');
			return false;
		}
		var reportId = getSelectedData().data.ID;
		if(reportId == null || reportId == undefined || reportId == ""){
			Ext.Msg.alert('提示','请先进行规划，并生成报告！');
			return false;
		}
		//路径
		window.open( basepath+'/TempDownload?filename=REPORT_'+reportId+'.pdf','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	}
},{
	text:'维护',
	hidden:JsContext.checkGrant('financialD_maintain'),
	iconCls:'detailIconCss',
	handler:function() {
		if(!getSelectedData()){//用于grid(自定义, 用getSelectedData())
			Ext.Msg.alert('提示','请选择要查看的记录！');
			return false;
		}
		editOrViewFn(false);
//		showCustomerViewByTitle('财务健康诊断');
		showCustomerViewByIndex(0);
	}
},{
	text:'详情',
	hidden:JsContext.checkGrant('financialD_detail'),
	iconCls:'detailIconCss',
	handler:function() {
		if(!getSelectedData()){//用于grid(自定义, 用getSelectedData())
			Ext.Msg.alert('提示','请选择要查看的记录！');
			return false;
		}
		editOrViewFn(true);
//		showCustomerViewByTitle('财务健康诊断');
		showCustomerViewByIndex(0);
}
}
	,new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('financialD_excel'),
        formPanel : 'searchCondition',
        url :  basepath+'/financialDiagnosis.json'
    })
]

var editOrViewFn = function(flag){
	if(!flag){
		Ext.getCmp('other_sum').show();
		Ext.getCmp('another_sum').show();
		Ext.getCmp('home_sum').show();
		Ext.getCmp('other_save').show();
		Ext.getCmp('another_save').show();
		Ext.getCmp('home_save').show();
		Ext.getCmp('sczds_save').show();
		Ext.getCmp('sczds_Id').hide();
	}else{
		Ext.getCmp('other_sum').hide();
		Ext.getCmp('another_sum').hide();
		Ext.getCmp('home_sum').hide();
		Ext.getCmp('other_save').hide();
		Ext.getCmp('another_save').hide();
		Ext.getCmp('home_save').hide();
		Ext.getCmp('sczds_save').hide();
		Ext.getCmp('sczds_Id').hide();
	}
//	Ext.getCmp('other_sum').setVisible(!hidden);
//	Ext.getCmp('another_sum').setVisible(!hidden);
//	Ext.getCmp('home_sum').setVisible(!hidden);
//	Ext.getCmp('other_save').setVisible(!hidden);
//	Ext.getCmp('another_save').setVisible(!hidden);
//	Ext.getCmp('home_save').setVisible(!hidden);
//	Ext.getCmp('sczds_Id').setVisible(!hidden);
	
};

/**
 * 财务信息数据存储
 */

var custId = "";
var reportId = "";
var i = 1;

//诸TypeStore注入
//财务指标
var finIndexStore = new Ext.data.Store( {

	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=FIN_INDEX'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])//拿到的是F_VALUE和F_CODE(显示出中文)
});
finIndexStore.load();

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

// 其他资产类型
var oAssetsTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=O_ASSETS_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
oAssetsTypeStore.load();

// 其他负债类型
var oLaibTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=O_LAIB_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
oLaibTypeStore.load();

// 证件类型
var certTypStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=PAPERS_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
certTypStore.load();

//客户他行资产信息行号
var teamrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var teamsm = new Ext.grid.CheckboxSelectionModel();
// 客户他行资产信息定义列模型
var teamcm = new Ext.grid.ColumnModel( [ teamrownum, {
	header : '资产类型',
	dataIndex : 'otherAssetType',
	width : 180,
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
	editor : new Ext.form.TextField( {
		// regex:/^[+-]?\d*\.?\d{1,2}$/
			// maskRe : '-'
			regex : /^\d*[.]?(\d{0,2})?$/
		}),
	width : 180,
	sortable : true,
	renderer : function(value) {//和[主移动表]出现时的view一样
		if (value != '' && value != null) {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);
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
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});


//客户他行负债信息行号
var teamrownum2 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var teamsm2 = new Ext.grid.CheckboxSelectionModel();
// 客户他行负债信息定义列模型
var teamcm2 = new Ext.grid.ColumnModel( [ teamrownum2, {
	header : '负债类型',
	dataIndex : 'otherDebtType',
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = obLaibTypeStore.find('key', value);
			return obLaibTypeStore.getAt(index).get('value');
		}
	}
}, {
	header : '金额(元)',
	dataIndex : 'otherDebtAmountValue',
	editor : new Ext.form.TextField( {
		// regex:/^[+-]?\d*\.?\d{1,2}$/
			// maskRe : '-'
			regex : /^\d*[.]?(\d{0,2})?$/
		}),
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
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});


//客户其他资产信息行号
var teamrownum3 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var teamsm3 = new Ext.grid.CheckboxSelectionModel();
// 客户其他资产信息定义列模型
var teamcm3 = new Ext.grid.ColumnModel( [ teamrownum3, {
	header : '资产类型',
	dataIndex : 'otherAssetType',
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = oAssetsTypeStore.find('key', value);
			return oAssetsTypeStore.getAt(index).get('value');
		}

	}
}, {
	header : '金额(元)',
	dataIndex : 'otherAssetAmountValue',
	editor : new Ext.form.TextField( {
		// regex:/^[+-]?\d*\.?\d{1,2}$/
			// maskRe : '-'
			regex : /^\d*[.]?(\d{0,2})?$/
		}),
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);
//客户其他资产信息数据存储
var teamstore3 = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/FinancialAnalysis!amoutValue.json'
	}),
	reader : new Ext.data.JsonReader( {
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'otherAssetType',
		mapping : 'ASSETS_TYPE'
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
//客户其他资产信息
var teamgrid3 = new Ext.grid.EditorGridPanel( {
	title : '资产信息',
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore3, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm3, // 列模型
	sm : teamsm3,
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});


//客户其他负债信息行号
var teamrownum4 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var teamsm4 = new Ext.grid.CheckboxSelectionModel();
// 客户其他负债信息定义列模型
var teamcm4 = new Ext.grid.ColumnModel( [ teamrownum4, {
	header : '负债类型',
	dataIndex : 'otherDebtType',
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = oLaibTypeStore.find('key', value);
			return oLaibTypeStore.getAt(index).get('value');
		}

	}
}, {
	header : '金额(元)',
	dataIndex : 'otherDebtAmountValue',
	editor : new Ext.form.TextField( {
		// regex:/^[+-]?\d*\.?\d{1,2}$/
			// maskRe : '-'
			regex : /^\d*[.]?(\d{0,2})?$/
		}),
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);
//客户其他资产信息数据存储
var teamstore4 = new Ext.data.Store( {
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
//客户其他负债信息
var teamgrid4 = new Ext.grid.EditorGridPanel( {
	title : '负债信息',
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore4,// 数据存储
	stripeRows : true,// 斑马线
	cm : teamcm4,// 列模型
	sm : teamsm4,
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});


//客户家庭月度收入信息行号
var teamrownum5 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var teamsm5 = new Ext.grid.CheckboxSelectionModel();
// 客户家庭月度收入信息定义列模型
var teamcm5 = new Ext.grid.ColumnModel( [ teamrownum5, {
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
	editor : new Ext.form.TextField( {
		// regex:/^[+-]?\d*\.?\d{1,2}$/
			// maskRe : '-'
			regex : /^\d*[.]?(\d{0,2})?$/
		}),
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);
//客户家庭月度收入信息数据存储
var teamstore5 = new Ext.data.Store( {
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
//客户家庭月度收入信息
var teamgrid5 = new Ext.grid.EditorGridPanel( {
	title : '收入信息',
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore5, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm5, // 列模型
	sm : teamsm5,
//	bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});



//客户家庭月度支出信息行号
var teamrownum6 = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var teamsm6 = new Ext.grid.CheckboxSelectionModel();
// 客户家庭月度支出信息定义列模型
var teamcm6 = new Ext.grid.ColumnModel( [ teamrownum6, {
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
	editor : new Ext.form.TextField( {
		// regex:/^[+-]?\d*\.?\d{1,2}$/
			// maskRe : '-'
			regex : /^\d*[.]?(\d{0,2})?$/
		}),
	width : 180,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			return Ext.util.Format.number(value, '0,000.00');
		}
	}
} ]);
//客户家庭月度支出信息数据存储
var teamstore6 = new Ext.data.Store( {
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
//客户家庭月度支出信息
var teamgrid6 = new Ext.grid.EditorGridPanel( {
	title : '支出信息',
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore6, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm6, // 列模型
	sm : teamsm6,
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});


//客户他行资产负债信息计算表单
var teamForm = new Ext.form.FormPanel(
		{
			labelWidth : 90, // 标签宽度
//			renderer : function(value) {//和[主移动表]出现时的view一样
//		if (value != '' && value != null) {
//			return Ext.util.Format.number(value, '0,000.00');
//		}
//	},
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
						name : 'otherAssetSum',
						id : 'otherAssetSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
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
						name : 'otherDebtSum',
						id : 'otherDebtSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
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
						name : 'otherNetAssetSum',
						id : 'otherNetAssetSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						readOnly : true
					} ]
				} ]
			} ],
			buttons : [
					{
						text : '资产统计',
						id : 'other_sum',
						handler : function() {
							Ext.getCmp('other_sum').focus();
							sum();
							Ext.MessageBox.alert('他行资产负债信息', '统计成功！');
						}

					},
					{
						text : '保存',
						id : 'other_save',
						handler : function() {
							Ext.getCmp('other_save').focus();
							sum();
							var assetInfo = new Array();
							var debtInfo = new Array();
							var i = 0;
							teamstore2.each(function(item) {
										debtInfo[i] = item.data.custId//这里的data是store的属性
												+ ":"//暂时可以认为是store中的约定俗成
												+ item.data.flag
												+ ":"
												+ item.data.otherDebtType
												+ ":"
												+ item.data.otherDebtAmountValue
												+ ":"
												+ item.data.infoId;
										i += 1;
									});
							i = 0;//i清0，进入下一个迭代
							teamstore.each(function(item) {
										assetInfo[i] = item.data.custId
												+ ":"
												+ item.data.flag//改动标记
												+ ":"
												+ item.data.otherAssetType
												+ ":"
												+ item.data.otherAssetAmountValue
												+ ":"
												+ item.data.infoId;
										i += 1;
									});//到这里,数据仍在页面, 下一步是：把东西通过AJAX扔回去！
							Ext.Ajax.request( {
										url : basepath + '/FinancialAnalysis!finInfoSaveOrUpdate.json',
										form : teamForm.form.id,
										mothed : 'POST',
										params : {
											assetInfo : assetInfo,//规定：左是KEY，右是VALUE
											debtInfo : debtInfo,
											belongType : '1'

										},
										failure : function(form, action) {
											Ext.MessageBox
													.alert('他行资产负债信息',
															'保存失败！');

										},
										success : function(response) {
											Ext.MessageBox
													.alert('他行资产负债信息',
															'保存成功！');
										}
									});
						}
					} ]
		});
		
//客户其他资产负债信息计算表单
var teamForm2 = new Ext.form.FormPanel(
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
						id : 'anotherAssetSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
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
						id : 'anotherDebtSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
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
						id : 'anotherNetAssetSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						readOnly : true
					} ]
				} ]
			} ],
			buttons : [
					{
						text : '资产统计',
						id : 'another_sum',
						handler : function() {
							Ext.getCmp('another_sum').focus();
							sum2();
							Ext.MessageBox.alert('其他资产负债信息', '统计成功！');
						}

					},
					{
						text : '保存',
						id : 'another_save',
						handler : function() {
							Ext.getCmp('another_save').focus();
							sum2();
							var assetInfo = new Array();
							var debtInfo = new Array();
							var i = 0;
							teamstore4
									.each(function(item) {
										debtInfo[i] = item.data.custId
												+ ":"
												+ item.data.flag
												+ ":"
												+ item.data.otherDebtType
												+ ":"
												+ item.data.otherDebtAmountValue
												+ ":"
												+ item.data.infoId;
										i += 1;
									});
							i = 0;
							teamstore3
									.each(function(item) {
										assetInfo[i] = item.data.custId
												+ ":"
												+ item.data.flag
												+ ":"
												+ item.data.otherAssetType
												+ ":"
												+ item.data.otherAssetAmountValue
												+ ":"
												+ item.data.infoId;
										i += 1;
									});

							Ext.Ajax
									.request( {
										url : basepath + '/FinancialAnalysis!finInfoSaveOrUpdate.json',
										form : teamForm2.form.id,
										mothed : 'POST',
										params : {
											assetInfo : assetInfo,
											debtInfo : debtInfo,
											belongType : '2'

										},
										failure : function(form, action) {
											Ext.MessageBox
													.alert('其他资产负债信息',
															'保存失败！');

										},
										success : function(response) {
											Ext.MessageBox
													.alert('其他资产负债信息',
															'保存成功！');

										}
									});
						}
					} ]
		});

//客户家庭支出信息计算表单
var teamForm3 = new Ext.form.FormPanel(
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
						id : 'monthInSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
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
						id : 'monthOutSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
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
						id : 'monthNetSum2',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle : 'text-align:right;',
						anchor : '90%',
						readOnly : true
					} ]
				} ]
			} ],
			buttons : [
					{
						text : '资产统计',
						id : 'home_sum',
						handler : function() {
							Ext.getCmp('home_sum').focus();
							sum3();
							Ext.MessageBox.alert('家庭月度收支', '统计成功！');
						}

					},
					{
						text : '保存',
						id : 'home_save',
						handler : function() {
							Ext.getCmp('home_save').focus();
							sum3();
							var inInfo = new Array();
							var outInfo = new Array();
							var i = 0;
							teamstore5.each(function(item) {
								inInfo[i] = item.data.custId + ":"
										+ item.data.flag + ":"
										+ item.data.detialType + ":"
										+ item.data.money + ":"
										+ item.data.infoId;
								i += 1;
							});
							i = 0;
							teamstore6.each(function(item) {
								outInfo[i] = item.data.custId + ":"
										+ item.data.flag + ":"
										+ item.data.detialType + ":"
										+ item.data.money + ":"
										+ item.data.infoId;
								i += 1;
							});

							Ext.Ajax
									.request( {
										url : basepath + '/FinancialAnalysis!custIoSaveOrUpdate.json',
										form : teamForm2.form.id,
										mothed : 'POST',
										params : {
											assetInfo : inInfo,
											debtInfo : outInfo

										},
										failure : function(form, action) {
											Ext.MessageBox
													.alert('家庭月度收支信息',
															'保存失败！');

										},
										success : function(response) {
											Ext.MessageBox
													.alert('家庭月度收支信息',
															'保存成功！');

										}
									});
						}
					} ]
		});

var char_set_1 = new Ext.form.FieldSet( {
	height : 350,
	title : '客户本行资产负债信息',
	items : {
		region : 'center',
		id : 'center-panel',
		layout : 'fit',
		html : '<div id="chartdiv1"></div>'

	}
});

var char_set_2 = new Ext.form.FieldSet( {
	height : 350,
	title : '客户他行资产负债信息',
	items : {
		region : 'center',
		id : 'center-panel2',
		layout : 'fit',
		html : '<div id="chartdiv2"></div>'
	}
});

var char_set_3 = new Ext.form.FieldSet( {
	title : '客户其他资产负债信息',
	height : 350,
	items : {
		region : 'center',
		id : 'center-panel3',
		layout : 'fit',
		html : '<div id="chartdiv3"></div>'
	}
});

// 折线
var char_set_4 = new Ext.form.FieldSet( {
	title : '客户家庭月度收支信息',

	height : 350,
	items : {
		region : 'center',
		id : 'center-panel4',
		layout : 'fit',
		html : '<div id="chartdiv4"></div>'
	}
});

var char_set_5 = new Ext.form.FieldSet( {
	title : '客户本行资产负债（元）',
	labelWidth : 80,
	anchor : '90%',
	items : [ {
		width : 100,
		fieldLabel : '本行资产合计',
		name : 'bankAssetSum',
		id : 'bankAssetSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '95%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '本行负债合计',
		name : 'bankDebtSum',
		id : 'bankDebtSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '95%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '本行净资产',
		name : 'bankNetAsset',
		id : 'bankNetAsset1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '95%',
		disabled : true
	} ]
});

var char_set_6 = new Ext.form.FieldSet( {
	title : '客户他行资产负债（元）',
	labelWidth : 80,
	anchor : '90%',
	items : [ {
		width : 100,
		fieldLabel : '他行资产合计',
		name : 'otherAssetSum',//只是招牌
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		id : 'otherAssetSum1',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '他行负债合计',
		name : 'otherDebtSum',
		id : 'otherDebtSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '他行净资产',
		name : 'otherNetAsset',
		id : 'otherNetAsset1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	} ]
});

var char_set_7 = new Ext.form.FieldSet( {
	title : '客户其他资产负债（元）',
	labelWidth : 80,
	anchor : '90%',
	items : [ {
		width : 100,
		fieldLabel : '其他资产合计',
		name : 'anotherAssetSum',
		id : 'anotherAssetSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '其他负债合计',
		name : 'anotherDebtSum',
		id : 'anotherDebtSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '其他净资产',
		name : 'anotherNetAsset',
		id : 'anotherNetAsset1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	} ]
});

var char_set_8 = new Ext.form.FieldSet( {
	title : '月度收支信息（元）',
	labelWidth : 80,
	items : [ {
		width : 100,
		fieldLabel : '月度总收入',
		name : 'monthIn',
		id : 'monthIn1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '月度总支出',
		name : 'monthOut',
		id : 'monthOut1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	}, {
		width : 100,
		fieldLabel : '盈余',
		name : 'monthNet',
		id : 'monthNet1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		disabled : true
	} ]
});

var char_set_9 = new Ext.form.FieldSet( {
	title : '客户资产汇总（元）',
	layout : 'column',
	anchor : '99%',
	items : [ {
		columnWidth : 1 / 3,
		layout : 'form',
		items : [ {
			fieldLabel : '客户总资产',
			name : 'assetSum',
			id : 'assetSum1',
			xtype : 'textfield', // 设置为数字输入框类型
			labelStyle : 'text-align:right;',
			anchor : '90%',
			disabled : true
		} ]
	}, {
		columnWidth : 1 / 3,
		layout : 'form',
		items : [ {
			fieldLabel : '客户总负债资产',
			name : 'debtSum',
			id : 'debtSum1',
			xtype : 'textfield', // 设置为数字输入框类型
			labelStyle : 'text-align:right;',
			anchor : '90%',
			disabled : true
		} ]
	}, {
		columnWidth : 1 / 3,
		layout : 'form',
		items : [ {
			fieldLabel : '净资产',
			name : 'newAsset',
			id : 'newAsset1',
			xtype : 'textfield', // 设置为数字输入框类型
			labelStyle : 'text-align:right;',
			anchor : '90%',
			disabled : true
		} ]
	} ],
	listeners : {
		'statesave' : function() {
		}
	}
});


//客户财务指标信息 数据存储//这里开始【5号表】
var store_form_set_1 = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/FinancialAnalysis!findFinIndex.json'
	}),
	reader : new Ext.data.JsonReader( {//json返回finindex-proposal-value
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'code',//就是那个target（1-7）
		mapping : 'CODE'
	}, {
		name : 'meaning',//说明
		mapping : 'MEANING'
	}, {
		name : 'proposal',//就是给的建议..
		mapping : 'PROPOSAL'
	}, {
		name : 'value',//现值
		mapping : 'VALUE'
	} ])
});	

var sm_form_set_1 = new Ext.grid.CheckboxSelectionModel();
var cm_form_set_1 = new Ext.grid.ColumnModel( [ {
	header : '指标名称',
	dataIndex : 'code',
	width : 150,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = finIndexStore.find('key', value);
			return finIndexStore.getAt(index).get('value');
		}
	}
}, {
	header : '现值',
	width : 100,
	dataIndex : 'value',
	align : 'right',
	sortable : true,
	renderer : function(value) {

		// 增加财务指标页面的总资产自有权益比例和总资产负债比的%比格式 修改人兰超 2012-07-26

	if (value != '') {
	if (value.substring(value.length - 1) == '%') {
		value = value.substring(0, value.length - 1);

		return Ext.util.Format.number(value, '00.00%');
	} else if (value == 'Infinity' || value == 'NaN') {
		return "无";
	} else if (value == '#1#') {
		return "无";
	} else if (value == '#2#') {
		return "无负债";
	} else if (value == '#3#') {
		return "无收入";
	} else {

		return Ext.util.Format.number(value, '0,000.00');
	}
}

}
},
/*
 * { header : '建议', width : 280, dataIndex : 'proposal', editor : new
 * Ext.form.Field(), sortable : true },
 */{
	header : '说明',
	width : 505,
	dataIndex : 'meaning',
	renderer : function(value, meta, record) {
		meta.attr = 'style="white-space:normal;"';
		return value;
	}
} ]);
//表格实例
var grid_form_set_1 = new Ext.grid.EditorGridPanel( {
	height : 300,
	frame : true,
	border : true,
	overflow : 'auto',
	autoScroll : true,
	store : store_form_set_1, // 数据存储
	// stripeRows : true, // 斑马线
	cm : cm_form_set_1, // 列模型
	sm : sm_form_set_1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var form_set_2 = new Ext.form.FieldSet({
    title: '财务指标点评',
    height: 715,
    layout: 'column',
    labelAlign: 'right',
    autoScroll : true,
    labelWidth : 250,
    items: [{
        columnWidth: 1,
        layout: 'form',
        items: [{           
            fieldLabel: '负债总资产比率点评',
            name: 'dp_1',
            id:'fzzzcbldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },
        {
            fieldLabel: '负债收入比率点评',
            name: 'dp_2',
            id:'fzsrbldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '流动性比率点评',
            name: 'dp_3',
            id:'ldxbldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '储蓄比率点评',
            name: 'dp_4',
            id:'cxbldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '投资与净资产比率点评',
            name: 'dp_5',
            id:'tzyjzcbldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '消费率点评',
            name: 'dp_6',
            id:'xfldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '理财成就率点评',
            name: 'dp_7',
            id:'lccjldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '收益率点评',
            name: 'dp_8',
            id:'syldp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        },{
            fieldLabel: '综合点评',
            name: 'dp_9',
            id:'zhdp',
            xtype: 'textarea',
            // 设置为数字输入框类型
            anchor: '80%',
            labelSeparator: ''
        }
        ]
    }]
});

var form_set_1 = new Ext.form.FieldSet( {
	title : '客户指标信息',
	items : [ grid_form_set_1 , form_set_2]
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
	//他行-资产合计
	if(otherAssetSum == ''){
		Ext.getCmp('otherAssetSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(otherAssetSum);
		Ext.getCmp('otherAssetSum2').setValue(money);
	}
	//他行-负债合计
	if(otherDebtSum == ''){
		Ext.getCmp('otherDebtSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(otherDebtSum);
		Ext.getCmp('otherDebtSum2').setValue(money);
	}
	//他行-净资产
	if(otherNetAssetSum == ''){
		Ext.getCmp('otherNetAssetSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(otherNetAssetSum);
		Ext.getCmp('otherNetAssetSum2').setValue(money);
	}
}

function sum2() {
	var anotherAssetSum = 0;
	var anotherDebtSum = 0;
	var anotherNetAssetSum = 0;
	var records = teamgrid3.getStore().data.items;
	for ( var r = 0; r < records.length; r++) {
		var a = records[r].data.otherAssetAmountValue;
		if (a == '' || a == null)
			a = 0;
		anotherAssetSum += parseInt(a);
	}

	var records2 = teamgrid4.getStore().data.items;
	for ( var r = 0; r < records2.length; r++) {
		var a = records2[r].data.otherDebtAmountValue;
		if (a == '' || a == null)
			a = 0;
		anotherDebtSum += parseInt(a);
	}
	anotherNetAssetSum = anotherAssetSum - anotherDebtSum;
	
	//其他-资产合计
	if(anotherAssetSum == ''){
		Ext.getCmp('anotherAssetSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(anotherAssetSum);
		Ext.getCmp('anotherAssetSum2').setValue(money);
	}
	//其他-负债合计
	if(anotherDebtSum == ''){
		Ext.getCmp('anotherDebtSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(anotherDebtSum);
		Ext.getCmp('anotherDebtSum2').setValue(money);
	}
	//其他-净资产
	if(anotherNetAssetSum == ''){
		Ext.getCmp('anotherNetAssetSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(anotherNetAssetSum);
		Ext.getCmp('anotherNetAssetSum2').setValue(money);
	}
}

function sum3() {
	var monthIn = 0;
	var monthOut = 0;
	var monthNet = 0;

	var records = teamgrid5.getStore().data.items;
	for ( var r = 0; r < records.length; r++) {
		var a = records[r].data.money;
		if (a == '' || a == null)
			a = 0;
		monthIn += parseInt(a);
	}
	var records2 = teamgrid6.getStore().data.items;
	for ( var r = 0; r < records2.length; r++) {
		var a = records2[r].data.money;
		if (a == '' || a == null)
			a = 0;
		monthOut += parseInt(a);
	}
	monthNet = monthIn - monthOut;
	
	//家庭-收入合计
	if(monthIn == ''){
		Ext.getCmp('monthInSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(monthIn);
		Ext.getCmp('monthInSum2').setValue(money);
	}
	//家庭-支出合计
	if(monthOut == ''){
		Ext.getCmp('monthOutSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(monthOut);
		Ext.getCmp('monthOutSum2').setValue(money);
	}
	//家庭-盈余	
	if(monthNet == ''){
		Ext.getCmp('monthNetSum2').setValue('￥ 0.00');
	}else{
		var money = Mis.Ext.FormatCnMoney(monthNet);
		Ext.getCmp('monthNetSum2').setValue(money);
	}
}


//客户他行资产负债信息面板
var tab_1 = new Ext.Panel( {
	layout : 'form',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			items : [ teamgrid ]
		}, {
			columnWidth : .5,
			items : [ teamgrid2 ]
		} ]
	}, teamForm ]
});

//客户其他资产负债信息面板
var tab_2 = new Ext.Panel( {
	layout : 'form',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			items : [ teamgrid3 ]
		}, {
			columnWidth : .5,
			items : [ teamgrid4 ]
		} ]
	}, teamForm2 ]
});

var tab_3 = new Ext.form.FormPanel( {
	title : '客户资产负债信息（单位：元）',
	overflow : 'auto',
	items : [ {
		layout : 'column',
		anchor : '99%',
		items : [ {
			layout : 'form',
			columnWidth : 0.5,
			items : [ char_set_1, char_set_3, {
				layout : 'column',
				items : [ {
					columnWidth : 0.5,
					items : [ char_set_5 ]
				}, {
					columnWidth : 0.5,
					items : [ char_set_6 ]
				} ]

			} ]
		}, {
			layout : 'form',
			columnWidth : 0.5,
			items : [ char_set_2, char_set_4, {
				layout : 'column',
				items : [ {
					columnWidth : 0.5,
					items : [ char_set_7 ]
				}, {
					columnWidth : 0.5,
					items : [ char_set_8 ]
				} ]

			} ]
		} ]
	}, char_set_9 ]
});

//客户家庭月度收支信息面板
var tab_4 = new Ext.Panel( {
	layout : 'form',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			items : [ teamgrid5 ]
		}, {
			columnWidth : .5,
			items : [ teamgrid6 ]
		} ]
	}, teamForm3 ]
});

var tab_5 = new Ext.form.FormPanel(
		{
			buttonAlign : "center",
			frame : true,
			items : [ form_set_1 ],
			buttons : [{
						text : '保存',
						id:'sczds_save',
						handler : function() {
							Ext.getCmp('sczds_Id').show();
							sum();
							var finIndex = new Array();
							var i = 0;
							store_form_set_1.each(function(item) {
								finIndex[i] = item.data.code + ":"//finIndex : code(target1-7)+proposal(建议)
										+ item.data.proposal;
								i += 1;
							});
							var finIndex2 = new Array();
							finIndex2[0]="dp_1:"+Ext.getCmp('fzzzcbldp').getValue();
							finIndex2[1]="dp_2:"+Ext.getCmp('fzsrbldp').getValue();
							finIndex2[2]="dp_3:"+Ext.getCmp('ldxbldp').getValue();
							finIndex2[3]="dp_4:"+Ext.getCmp('cxbldp').getValue();
							finIndex2[4]="dp_5:"+Ext.getCmp('tzyjzcbldp').getValue();
							finIndex2[5]="dp_6:"+Ext.getCmp('xfldp').getValue();
							finIndex2[6]="dp_7:"+Ext.getCmp('lccjldp').getValue();
							finIndex2[7]="dp_8:"+Ext.getCmp('syldp').getValue();
							finIndex2[8]="dp_9:"+Ext.getCmp('zhdp').getValue();
							finIndex2.toString();
							Ext.Msg.wait('请稍等,正在保存数据中...','提示');
							Ext.Ajax.request( {
										url : basepath + '/FinancialAnalysis!finIndexSaveOrUpdate.json',
										form : teamForm.form.id,
										method : 'POST',
										params : {
											finIndex : finIndex,//发送前, Array
											CUST_ID : custId,
											f2Index : finIndex2
										},
										failure : function(form, action) {
											Ext.MessageBox.alert(
													'财务指标分析', '提交失败！');

										},
										success : function(response) {
											Ext.MessageBox.alert(
													'财务指标分析', '提交成功！');
//											Ext.getCmp('sczds_Id')
//													.enable();
										}
									});

						}
					},{
				        text: '生成诊断书',
				        id: 'sczds_Id',
				        handler: function() {
							Ext.Msg.wait('请稍等,正在生成中...','提示');
				            Ext.Ajax.request({
				                url: basepath + '/FinancialAnalysis!createReport.json',
				                method: 'POST',
				                params:{
				                	custId: getSelectedData().data.CUST_ID,
				                	reportId : getSelectedData().data.ID
				                },
				                success: function(response){
				                	Ext.Msg.alert('提示','操作成功！');
				                	reloadCurrentData();
				                },
				                failure:function(){
				                    Ext.Msg.alert('提示','操作失败！');
				                }
				            });
				        }
				    } ]
		});

//读取点评信息的load
var load0 = function(custId) {//请求【2】
	Ext.Ajax.request({//这是request
		url : basepath+'/FinancialAnalysis!queryReview.json?custId='+custId,
		method : 'GET',
		success : function(response){
			var json = Ext.util.JSON.decode(response.responseText).json;
			if(json.json.data[0] != null || json.json.data[0] != undefined){
				tab_5.getForm().findField('fzzzcbldp').setValue(json.json.data[0].FZZZCBLDP_ADVISE);
				tab_5.getForm().findField('fzsrbldp').setValue(json.json.data[0].FZSRBLDP_ADVISE);
				tab_5.getForm().findField('ldxbldp').setValue(json.json.data[0].LDXBLDP_ADVISE);
				tab_5.getForm().findField('cxbldp').setValue(json.json.data[0].CXBLDP_ADVISE);
				tab_5.getForm().findField('tzyjzcbldp').setValue(json.json.data[0].TZYJZCBLDPL_ADVISE);
				tab_5.getForm().findField('xfldp').setValue(json.json.data[0].XFLDP_ADVISE);
				tab_5.getForm().findField('lccjldp').setValue(json.json.data[0].LCCJLDP_ADVISE);
				tab_5.getForm().findField('syldp').setValue(json.json.data[0].SYLDP_ADVISE);
				tab_5.getForm().findField('zhdp').setValue(json.json.data[0].ZHDP_ADVISE);
			}
		}
	});
}
		 
//var i=1;
var load1 = function(custId) {//load方法
	var chart1 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart2 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart3 = new FusionCharts(basepath
			+ "/FusionCharts/Column3D.swf", "chartId" + i, "90%",
			"320", "0", "0");
	i += 1;
	var chart4 = new FusionCharts(basepath
			+ "/FusionCharts/Column3D.swf", "chartId" + i, "90%",
			"320", "0", "0");
	i += 1;
	Ext.Ajax
			.request( {
				url : basepath
						+ '/FinancialAnalysis!assetXml.json?customerId='
						+ custId,
				method : 'GET',
				success : function(response) {
					var dataXml1 = Ext.util.JSON
							.decode(response.responseText).dataXml1;//data小毛驴是从这里进来的
					var dataXml2 = Ext.util.JSON
							.decode(response.responseText).dataXml2;
					var dataXml3 = Ext.util.JSON
							.decode(response.responseText).dataXml3;
					var dataXml4 = Ext.util.JSON
							.decode(response.responseText).dataXml4;
					var r = Ext.util.JSON.decode(response.responseText).valueMap;//？可能是json..可以被record接收
					var r2 = new Ext.data.Record(r);
					tab_3.getForm().loadRecord(r2);//r2前往tab_3 视图
					chart1.setDataXML(dataXml1);//设值  设置数据
					chart2.setDataXML(dataXml2);
					chart3.setDataXML(dataXml3);
					chart4.setDataXML(dataXml4);
					chart1.render("chartdiv1");//能图显  在相应位置
					chart2.render("chartdiv2");
					chart3.render("chartdiv3");
					chart4.render("chartdiv4");
					// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位
					var newAsset11 = Ext.getCmp('newAsset1').getValue();
					if (newAsset11 == '') {
						Ext.getCmp('newAsset1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(newAsset11);
						Ext.getCmp('newAsset1').setValue(money);
					}
					var bankAssetSum11 = Ext.getCmp('bankAssetSum1')
							.getValue();
					if (bankAssetSum11 == '') {
						Ext.getCmp('bankAssetSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(bankAssetSum11);
						Ext.getCmp('bankAssetSum1').setValue(money);
					}
					var bankDebtSum11 = Ext.getCmp('bankDebtSum1')
							.getValue();
					if (bankDebtSum11 == '') {
						Ext.getCmp('bankDebtSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(bankDebtSum11);
						Ext.getCmp('bankDebtSum1').setValue(money);
					}
					var bankNetAsset11 = Ext.getCmp('bankNetAsset1')
							.getValue();
					if (bankNetAsset11 == '') {
						Ext.getCmp('bankNetAsset1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(bankNetAsset11);
						Ext.getCmp('bankNetAsset1').setValue(money);
					}
					var otherAssetSum11 = Ext.getCmp('otherAssetSum1')
							.getValue();
					if (otherAssetSum11 == '') {
						Ext.getCmp('otherAssetSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(otherAssetSum11);
						Ext.getCmp('otherAssetSum1').setValue(money);
					}
					var otherDebtSum11 = Ext.getCmp('otherDebtSum1')
							.getValue();
					if (otherAssetSum11 == '') {
						Ext.getCmp('otherDebtSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(otherDebtSum11);
						Ext.getCmp('otherDebtSum1').setValue(money);
					}
					var otherNetAsset11 = Ext.getCmp('otherNetAsset1')
							.getValue();
					if (otherNetAsset11 == '') {
						Ext.getCmp('otherNetAsset1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(otherNetAsset11);
						Ext.getCmp('otherNetAsset1').setValue(money);
					}
					var anotherAssetSum11 = Ext.getCmp(
							'anotherAssetSum1').getValue();
					if (anotherAssetSum11 == '') {
						Ext.getCmp('anotherAssetSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(anotherAssetSum11);
						Ext.getCmp('anotherAssetSum1').setValue(money);
					}
					var anotherDebtSum11 = Ext
							.getCmp('anotherDebtSum1').getValue();
					if (anotherDebtSum11 == '') {
						Ext.getCmp('anotherDebtSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(anotherDebtSum11);
						Ext.getCmp('anotherDebtSum1').setValue(money);
					}
					var anotherNetAsset11 = Ext.getCmp(
							'anotherNetAsset1').getValue();
					if (anotherNetAsset11 == '') {
						Ext.getCmp('anotherNetAsset1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(anotherNetAsset11);
						Ext.getCmp('anotherNetAsset1').setValue(money);
					}
					var monthIn11 = Ext.getCmp('monthIn1').getValue();
					if (monthIn11 == '') {
						Ext.getCmp('monthIn1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthIn11);
						Ext.getCmp('monthIn1').setValue(money);
					}
					var monthOut11 = Ext.getCmp('monthOut1').getValue();
					if (monthOut11 == '') {
						Ext.getCmp('monthOut1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthOut11);
						Ext.getCmp('monthOut1').setValue(money);
					}
					var monthNet11 = Ext.getCmp('monthNet1').getValue();
					if (monthNet11 == '') {
						Ext.getCmp('monthNet1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthNet11);
						Ext.getCmp('monthNet1').setValue(money);
					}
					var debtSum11 = Ext.getCmp('debtSum1').getValue();
					if (debtSum11 == '') {
						Ext.getCmp('debtSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(debtSum11);
						Ext.getCmp('debtSum1').setValue(money);
					}
					var assetSum11 = Ext.getCmp('assetSum1').getValue();
					if (assetSum11 == '') {
						Ext.getCmp('assetSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(assetSum11);
						Ext.getCmp('assetSum1').setValue(money);
					}
					// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位
				},
				failure : function(response) {
				}
			});
};

//读取“他行”、“其他”、“家庭”资产负债信息的load
function load2() {
	if (tabs.getActiveTab().title == '客户他行资产负债信息') {
		teamForm.getForm().reset();
		teamstore.load( {
			params : {
				BELONG_TYPE : 1,
				ASSET_DEBT_TYPE : 1,
				CUST_ID : custId,//有了,外表点击传入
				ASSETS_TYPE : 'OB_ASSETS_TYPE'
			}
		});
		teamstore2.load( {
			params : {
				BELONG_TYPE : 1,
				ASSET_DEBT_TYPE : 2,
				CUST_ID : custId,
				ASSETS_TYPE : 'OB_LAIB_TYPE'
			},
			callback : function(){
				Ext.getCmp('other_sum').focus();
				sum();
			}
		});
	} else if (tabs.getActiveTab().title == '客户其他资产负债信息') {
		teamForm2.getForm().reset();
		teamstore3.load( {
			params : {
				BELONG_TYPE : 2,
				ASSET_DEBT_TYPE : 1,
				CUST_ID : custId,
				ASSETS_TYPE : 'O_ASSETS_TYPE'
			}
		});

		teamstore4.load( {
			params : {
				BELONG_TYPE : 2,
				ASSET_DEBT_TYPE : 2,
				CUST_ID : custId,
				ASSETS_TYPE : 'O_LAIB_TYPE'
			},
			callback : function(){
				Ext.getCmp('another_sum').focus();
				sum2();
			}
		});
	} else if (tabs.getActiveTab().title == '客户家庭月度收支信息') {
		teamForm3.getForm().reset();
		teamstore5.load( {
			params : {
				IO_TYPE : '1',
				CUST_ID : custId
			}
		});

		teamstore6.load( {
			params : {
				IO_TYPE : '2',
				CUST_ID : custId
			},
			callback : function(){
				Ext.getCmp('home_sum').focus();
				sum3();
			}
		});
	} else if (tabs.getActiveTab().title == '财务分析') {//就这里用的是LOAD1
		load1(custId);
	} else if (tabs.getActiveTab().title == '财务指标分析') {
		store_form_set_1.load( {
			params : {
				CUST_ID : custId
			}
		});
		load0(custId);//触发【1】
	}
};

//诊断tabs
var tabs = new Ext.TabPanel( {
	hidden: true,
	defaults : {
		overflow : 'auto',
		autoScroll : true
	},
	activeTab : 0, 
	items : [ {
		title : '客户他行资产负债信息',
		items : [ tab_1 ]

	}, {
		title : '客户其他资产负债信息',
		items : [ tab_2 ]
	}, {
		title : '客户家庭月度收支信息',
		items : [ tab_4 ]
	}, {
		title : '财务分析',
		items : [ tab_3 ]
	}, {
		title : '财务指标分析',
		items : [ tab_5 ]
	} ],
	listeners : {
		'tabchange' : function() {
			load2();
		}
	}
});

//增加beforeviewshow事件
var beforeviewshow = function(view){
	//debugger;
	if(view._defaultTitle == '财务健康诊断'){
		if(getSelectedData() && getAllSelects().length == 1){
			if(tabs.hidden){
				tabs.setVisible(true);
				tabs.hidden=false;
			}
			teamForm2.form.reset();
			teamForm3.form.reset();
			teamForm.getForm().reset();
			tab_5.getForm().reset();
			custId = getSelectedData().data.CUST_ID;
			reportId = getSelectedData().data.ID;
			teamstore2.load( {
				params : {
					BELONG_TYPE : 1,
					ASSET_DEBT_TYPE : 2,
					CUST_ID : custId,
					ASSETS_TYPE : 'OB_LAIB_TYPE'
				}
			});
			teamstore.load( {
				params : {
					BELONG_TYPE : 1,
					ASSET_DEBT_TYPE : 1,
					CUST_ID : custId,
					ASSETS_TYPE : 'OB_ASSETS_TYPE'
				},
				callback : function(){
					Ext.getCmp('other_sum').focus();//回显对准
					sum();
				}
			});
			tabs.setActiveTab(0);
		}else{
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
	}
};

//结果域扩展功能面板
var customerView = [{
	title : '财务健康诊断',
	suspendWidth: 800,
	hideTitle:true,
	items:[tabs]
}];