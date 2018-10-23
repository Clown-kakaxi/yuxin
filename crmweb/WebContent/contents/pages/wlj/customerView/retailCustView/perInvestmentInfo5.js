/**
*@description 360客户视图对私 投资信息 -- 客户财务指标信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/

var url = basepath + '/lookup.json?name=FIN_INDEX';
var fields = [{name:'CODE',text:'',hidden:true}];
Ext.onReady(function() {
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

store_form_set_1.load( {
	params : {
		CUST_ID : _custId
	},
	callback : function(){
        var task = new Ext.util.DelayedTask(load); 
        task.delay(50); 
	}

});
function load() {
	load0(_custId);
}
var load0 = function(custId) {
	Ext.Ajax.request({//这是request
		url : basepath+'/FinancialAnalysis!queryReview.json?custId='+custId,
		method : 'GET',
		success : function(response){
			var json = Ext.util.JSON.decode(response.responseText).json;
			tabs.getForm().findField('fzzzcbldp').setValue(json.json.data[0].FZZZCBLDP_ADVISE);
			tabs.getForm().findField('fzsrbldp').setValue(json.json.data[0].FZSRBLDP_ADVISE);
			tabs.getForm().findField('ldxbldp').setValue(json.json.data[0].LDXBLDP_ADVISE);
			tabs.getForm().findField('cxbldp').setValue(json.json.data[0].CXBLDP_ADVISE);
			tabs.getForm().findField('tzyjzcbldp').setValue(json.json.data[0].TZYJZCBLDPL_ADVISE);
			tabs.getForm().findField('xfldp').setValue(json.json.data[0].XFLDP_ADVISE);
			tabs.getForm().findField('lccjldp').setValue(json.json.data[0].LCCJLDP_ADVISE);
			tabs.getForm().findField('syldp').setValue(json.json.data[0].SYLDP_ADVISE);
			tabs.getForm().findField('zhdp').setValue(json.json.data[0].ZHDP_ADVISE);
		}
	});
}
var sm_form_set_1 = new Ext.grid.CheckboxSelectionModel();
var cm_form_set_1 = new Ext.grid.ColumnModel( [ {
	header : '指标名称',
	dataIndex : 'code',
	width : 200,
	sortable : true,
	renderer : function(value) {
		if (value != '') {
			var index = finIndexStore.find('key', value);
			return finIndexStore.getAt(index).get('value');
		}
	}
}, {
	header : '现值',
	width : 150,
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
	width : 500,
	dataIndex : 'meaning',
	renderer : function(value, meta, record) {
		meta.attr = 'style="white-space:normal;"';
		return value;
	}
} ]);
//表格实例
var grid_form_set_1 = new Ext.grid.EditorGridPanel( {
	height : 250,
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
    height: 200,
    layout: 'column',
    labelAlign: 'right',
    autoScroll : true,
    labelWidth : 250,
    items: [{
        columnWidth: 0.8,
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

var tabs = new Ext.form.FormPanel( {
	buttonAlign : "center",
	frame : true,
	items : [ form_set_1 ]
});

//var tabs = new Ext.TabPanel( {
//	activeTab : 0,
//	tabHeight:0, 
//	items : [ {
//		items : [ tab_3 ]
//	}],
//	listeners : {
//		'tabchange' : function() {
//			load();
//		}
//	}
//});
// 页面视图
var viewport = new Ext.Viewport({
	layout : 'fit',
	defaults : {
		overflow : 'auto',
		autoScroll : true
	},
	items : [{
		items:[tabs]
	} ]
});
});