var chanceStatStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var chanceSourceStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=BUSI_CHANCE_SOURCE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var chanceStateStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=BUSI_CHANCE_STATUS'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var chanceProbStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=BUSI_CHANCE_PROB'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var chanceStageStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=BUSI_CHANCE_STAGE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var chanceTypeStore = new Ext.data.ArrayStore({
	fields : ['key', 'value'],
	data : [[2, '潜在客户'], [1, '正式客户']]
});
var chanceCategoryStore = new Ext.data.ArrayStore({
	fields : ['key', 'value'],
	data : [/*[1, '对私客户'], */[2, '对公客户']]
});

// 新增商机窗口From表单
var addChanceForm02 = new Ext.FormPanel({
	labelWidth : 100,
	width  : 750,
	height : 375,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [
	{
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'OPPOR_NAME',
				anchor : '90%'
			},
			new Ext.form.ComboBox({
    			hiddenName : 'opporStat',
    			fieldLabel : '商机状态',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceStateStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
//    			id : 'opporStat_add',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),new Ext.form.ComboBox({
    			hiddenName : 'opporSource',
    			fieldLabel : '商机来源',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceSourceStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
//    			id : 'opporSource_add',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),{
				xtype : 'datefield',
				fieldLabel : '商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporEndDate',
//				id : 'opporEndDate',
				anchor : '90%'
			},{
				xtype : 'datefield',
				fieldLabel : '商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporDueDate',
//				id : 'opporDueDate',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '商机产品', 
//				id:'productSelect_add',
				labelStyle: 'text-align:right;',
				name : 'prodName',
				hiddenName:'prodId',
				singleSelect:false,
				anchor : '90%'
			}),new Ext.form.ComboBox({
    			hiddenName : 'custType',
    			fieldLabel : '客户类型',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceTypeStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),{
				xtype : 'numberfield',
				fieldLabel : '预计金额（元）',
				name : 'planAmount',
//				id : 'planAmount',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}
    
    		]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ 
			new Ext.form.ComboBox({
    			hiddenName : 'opporType',
    			fieldLabel : '商机类型',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceStatStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),new Ext.form.ComboBox({
    			hiddenName : 'opporStage',
    			fieldLabel : '商机阶段',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceStageStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
    			editable : false,
    			anchor : '90%'
    		}),{
				xtype : 'datefield',
				fieldLabel : '商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporStartDate',
//				id : 'opporStartDate',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '营销任务指标', 
//				id:'productSelect_close_quota_01',
				labelStyle: 'text-align:right;',
				name : 'MKT_TARGET_NAME',
				hiddenName:'MKT_TARGET_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseQuotaView();
				}
			}),new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '营销活动名称', 
//				id:'productSelect_close_activ_01',
				labelStyle: 'text-align:right;',
				name : 'MKT_ACTIV_NAME',
				hiddenName:'MKT_ACTIV_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseActivView();
				}
			}),new Ext.form.ComboBox({
    			hiddenName : 'custCategoty',
    			fieldLabel : '客户类别',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceCategoryStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}), new Ext.form.ComboBox({
    			hiddenName : 'reachProb',
    			fieldLabel : '达成概率',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store :chanceProbStore ,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),{
				xtype : 'numberfield',
				fieldLabel : '费用预算（元）',
				name : 'planCost',
//				id : 'planCost',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		}]
	},
	{
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'opporContent',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'memo',
			anchor : '95%'
		}]
	}]
});

var fields      = [],
columns     = [],
data        = [],
grid02        = null,
viewport    = null;

//列表字段
fields = [
    {name:'a1'},
    {name:'a2'},
    {name:'a3'},
    {name:'a4'},
    {name:'a5'},
    {name:'a6'}
];

//列表表头
columns = [
    {dataIndex:'a1',header:'客户号',sortable:true,width:120},
    {dataIndex:'a2',header:'客户名称',sortable:true,width:200},
    {dataIndex:'a3',header:'证件类型',sortable:true,width:120},
    {dataIndex:'a4',header:'证件号',sortable:true},
    {dataIndex:'a5',header:'客户状态',sortable:true},
    {dataIndex:'a6',header:'客户级别',sortable:true}
];

//列表数据
data = [
    ['CNJ2013021800007','南京华强电子有限公司经营一部','组织机构代码证','10168598-X','正式','A客户'],
    ['CNJ2013021800008','南京瑜惠保健品有限公司','组织机构代码证','10168524-X','正式','A客户'],
    ['CNJ2013021800020',' 南京豪迈照明电器有限公司     ','组织机构代码证','10168135-X','正式','A客户'],
    ['CNJ2013021800149',' 南京惠百氏装饰工程有限公司                       ','组织机构代码证','10168204-X','正式','A客户'],
    ['CNJ2013021800029','深圳方卡实业南京分公司','组织机构代码证','10161277-X','正式','A客户']
];

//列表表格
grid02 = new Ext.grid.GridPanel({
	title : '客户列表',
    height: 170,
	width : 750,
    store: new Ext.data.ArrayStore({
        fields: fields,
        data: data
    }),
    stripeRows:true,
    columns: columns
});
 
// 定义新增窗口
var addChanceWindow02 = new Ext.Window( {
	title : '商机新增',
	plain : true,
	layout : 'fit',
	width : 800,
	height : 480,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	buttonAlign : "center",
	items : [{
		layout : 'border',
		items: [{
			region:'center',
			autoScroll:true,
		    id: 'center-panel',
		    margins: '0 0 0 0',
		    items : [addChanceForm02,grid02]
		}] 
	}],
	listeners:{
  		"hide":function(){
  			addChanceForm02.getForm().reset();
		},
		"show":function(){//窗体显示时间，进行一些数据设置初始化操作
//			Ext.getCmp('opporStat_add').setValue('0');//商机状态：0，暂存
//			Ext.getCmp('opporSource_add').setValue('0');//商机来源：0，手工创建
//			Ext.getCmp('opporSource_add').readOnly = true;//商机来源只读
		}
	},
	buttons:[{
  		text:'保存',
  		handler:function(){
	  		Ext.Msg.alert('提示', '保存成功！');
	  		addChanceWindow02.hide();
	  	}
  	},{
  		text: '关闭',
	  	handler:function(){
	  		addChanceWindow02.hide();
	  	}
  	}]
});

// 打开 新增商机 窗口
function addMyBusOpportInit01() {
	addChanceWindow02.show();
}

//清空新增商机Form表单
function resetAddForm(){
	addChanceForm02.getForm().reset();
}