/**
 * 营销管理->商机管理->商机池：查询条件定义JS文件；wzy；2013-02-22
 */

// ========================变量定义======================开始======================//
// 查询条件panel
var searchPanel = null;
// ========================变量定义======================结束======================//

// ========================下拉框对象定义======================开始======================//
// "商机类型"下拉框
var chanceStatStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// "商机来源"下拉框
var chanceSourceStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_SOURCE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// "商机状态"下拉框
var chanceStateStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_STATUS'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// "达成概率"下拉框
var chanceProbStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=REACH_PROB'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// "商机阶段"下拉框
var chanceStageStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_STAGE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// "客户状态"下拉框
var chanceTypeStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=CUSTOMER_STATUS'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// "客户类别"下拉框
var chanceCategoryStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000080'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// ========================下拉框对象定义======================结束======================//

searchPanel = new Ext.form.FormPanel({
	labelWidth : 100,
	labelAlign : 'right',
	height : 300,
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [ {
		columnWidth : .25,
		layout : 'form',
		items : [ new Ext.form.ComboBox({
			hiddenName : 'OPPOR_TYPE',
			fieldLabel : '商机类型',
			labelStyle : 'text-align:right;',
			triggerAction : 'all',
			store : chanceStatStore,
			displayField : 'value',
			valueField : 'key',
			mode : 'local',
			forceSelection : true,
			emptyText : '请选择 ',
			resizable : true,
			anchor : '90%'
		}), {
			store : chanceSourceStore,
			xtype : 'combo',
			resizable : true,
			resizable : true,
			name : 'OPPOR_SOURCE',
			hiddenName : 'OPPOR_SOURCE',
			fieldLabel : '商机来源',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			typeAhead : true,
			forceSelection : true,
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'

		}, {
			fieldLabel : '商机开始日期',
			format : 'Y-m-d',
			xtype : 'datefield',
			editable : false,
			name : 'OPPOR_START_DATE',
			id : 'OPPOR_START_DATE',
			anchor : '90%'
		} ]

	}, {
		columnWidth : .25,
		layout : 'form',
		items : [ {
			xtype : 'textfield',
			fieldLabel : '商机名称',
			name : 'OPPOR_NAME',
			anchor : '90%'
		}, new Com.yucheng.crm.common.ProductManage({
			xtype : 'productChoose',
			fieldLabel : '商机产品',
			name : 'PROD_NAME',
			hiddenName : 'PROD_ID',
			singleSelect : false,
			anchor : '90%'
		}), {
			fieldLabel : '商机完成日期',
			format : 'Y-m-d',
			xtype : 'datefield',
			editable : false,
			name : 'OPPOR_END_DATE',
			id : 'OPPOR_END_DATE',
			anchor : '90%'
		} ]
	}, {
		columnWidth : .25,
		layout : 'form',
		items : [ {
			store : chanceStateStore,
			xtype : 'combo',
			resizable : true,
			resizable : true,
			name : 'OPPOR_STAT',
			hiddenName : 'OPPOR_STAT',
			fieldLabel : '商机状态',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			typeAhead : true,
			forceSelection : true,
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'

		}, {
			store : chanceProbStore,
			xtype : 'combo',
			resizable : true,
			resizable : true,
			name : 'REACH_PROB',
			hiddenName : 'REACH_PROB',
			fieldLabel : '达成概率',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			typeAhead : true,
			forceSelection : true,
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'
		} ]

	}, {
		columnWidth : .25,
		layout : 'form',
		items : [ {
			store : chanceStageStore,
			xtype : 'combo',
			resizable : true,
			resizable : true,
			name : 'OPPOR_STAGE',
			hiddenName : 'OPPOR_STAGE',
			fieldLabel : '商机阶段',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			typeAhead : true,
			forceSelection : true,
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'
		}, {
			fieldLabel : '商机有效期',
			format : 'Y-m-d',
			xtype : 'datefield',
			editable : false,
			name : 'OPPOR_DUE_DATE',
			id : 'OPPOR_DUE_DATE',
			anchor : '90%'
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			var conditionStr = searchPanel.getForm().getValues(false);
			store.on('beforeLoad', function() {
				this.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
			});
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		}
	}, {
		text : '重置',
		handler : function() {
			searchPanel.form.reset();
		}
	} ]
});