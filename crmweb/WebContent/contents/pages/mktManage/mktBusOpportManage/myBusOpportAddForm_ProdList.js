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
var addChanceForm01 = new Ext.FormPanel({
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
				fieldLabel : '*商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'OPPOR_NAME',
				anchor : '90%'
			},
			new Ext.form.ComboBox({
    			hiddenName : 'opporStat',
    			fieldLabel : '*商机状态',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			allowBlank : false,
				blankText : '此项为必填项，请检查！',
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
			},new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '*客户名称', 
				labelWidth : 100,
				name : 'custName',
				//custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
				//custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
			    singleSelected:true,//单选复选标志
				editable : false,
				allowBlank:false,//不允许为空
				blankText:"不能为空，请填写",
				anchor : '90%',
				hiddenName:'custId',
				callback :function(){					
				}
			}),new Ext.form.ComboBox({
    			hiddenName : 'custType',
    			fieldLabel : '*客户类型',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceTypeStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			allowBlank : false,
				blankText : '此项为必填项，请检查！',
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
    			fieldLabel : '*商机类型',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceStatStore,
    			allowBlank : false,
				blankText : '此项为必填项，请检查！',
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
				fieldLabel : '*商机开始日期',
				format : 'Y-m-d',
				editable : true,
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
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
    			fieldLabel : '*客户类别',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceCategoryStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			allowBlank : false,
				blankText : '此项为必填项，请检查！',
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
grid01        = null,
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
    {dataIndex:'a1',header:'产品名称',sortable:true,width:200},
    {dataIndex:'a2',header:'产品分类名称',sortable:true,width:150},
    {dataIndex:'a3',header:'利率',sortable:true,width:120},
    {dataIndex:'a4',header:'费率',sortable:true,width:120},
    {dataIndex:'a5',header:'期限',sortable:true,width:120}
];

//列表数据
data = [
    ['投联贷','贷款类','0.0250%','0.0010%','5年'],
    ['保联贷','贷款类','0.0200%','0.0020%','5年'],
    ['联合贷','贷款类','0.0150%','0.0020%','10年'],
    ['倍增贷','贷款类','0.0230%','0.0010%','15年'],
    ['小企业e助贷','贷款类','0.0120%','0.0011%','5年']
];

//列表表格
grid01 = new Ext.grid.GridPanel({
	title : '产品列表',
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
var addChanceWindow01 = new Ext.Window( {
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
		    items : [addChanceForm01,grid01]
		}] 
	}],
	listeners:{
  		"hide":function(){
  			addChanceForm01.getForm().reset();
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
  			if (!addChanceForm01.getForm().isValid()) {
  	       		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
  	       		 return false;
  			}
	  		Ext.Msg.alert('提示', '保存成功！');
	  		addChanceWindow01.hide();
	  	}
  	},{
  		text: '关闭',
	  	handler:function(){
	  		addChanceWindow01.hide();
	  	}
  	}]
});

// 打开 新增商机 窗口
function addMyBusOpportInit02() {
	addChanceWindow01.show();
}

//清空新增商机Form表单
function resetAddForm(){
	addChanceForm01.getForm().reset();
}