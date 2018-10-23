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
var addChanceForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
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
    			//id : 'opporStat_add',
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
    			//id : 'opporSource_add',
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
				//id : 'opporEndDate',
				anchor : '90%'
			},{
				xtype : 'datefield',
				fieldLabel : '商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporDueDate',
				//id : 'opporDueDate',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '商机产品', 
				//id:'productSelect_add',
				labelStyle: 'text-align:right;',
				name : 'prodName',
				hiddenName:'prodId',
				singleSelect:false,
				anchor : '90%'
			}),{
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'custContactName',
				anchor : '90%'
			},new Ext.form.ComboBox({
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
				//id : 'planAmount',
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
				//id : 'opporStartDate',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '营销任务指标', 
				//id:'productSelect_close_quota_01',
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
				//id:'productSelect_close_activ_01',
				labelStyle: 'text-align:right;',
				name : 'MKT_ACTIV_NAME',
				hiddenName:'MKT_ACTIV_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseActivView();
				}
			}),new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称', 
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
				//id : 'planCost',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		}]
	},
	{
		layout : 'form',
//							buttonAlign : 'center',
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
	}],
	buttons:[{
  		text:'保存',
  		handler:function(){
	  		if(!addChanceForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		/*
	  		Ext.Ajax.request({
	  			url:basepath+'/VipCardHandoutAuditOperationAction!savePitchTwoAudit.json',
	  			method: 'POST',
	  			form:auditVipCardPitchTwoForm.getForm().id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				params : {
	  				'chkRecords':auditVipCardPitchTwoForm.form.findField('chkRecord').getValue()
	  			},
	  			success : function(response) {
		  			Ext.Msg.alert('提示', '保存成功！');
		  			store.load({
		  				params : {
		  					start : 0,
		  					limit : bbar.pageSize
		  				}
		  			});
	  			},
		  		failure : function(response) {
		  			Ext.Msg.alert('提示','保存失败！');
		  		}
	  		});
	  		*/
	  		Ext.Msg.alert('提示', '保存成功！');
	  		addChanceWindow.hide();
	  	}
  	},{
  		text: '关闭',
	  	handler:function(){
	  		addChanceWindow.hide();
	  	}
  	}]
});

// 商机维护窗口From表单
var editChanceForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
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
			items : [ {
				xtype : 'textfield',
				fieldLabel : '商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'OPPOR_NAME',
				anchor : '90%'
			} ,
			new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_STAT_ORA',
    			fieldLabel : '商机状态',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceStateStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_SOURCE_ORA',
    			fieldLabel : '商机来源',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceSourceStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			//id : 'opporSource_modify',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		}),{
				xtype : 'datefield',
				fieldLabel : '商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'OPPOR_START_DATE',
				anchor : '90%'
			},{
				xtype : 'datefield',
				fieldLabel : '商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'OPPOR_DUE_DATE',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '商机产品', 
				//id:'productSelect_modify',
				labelStyle: 'text-align:right;',
				name : 'PROD_NAME',
				hiddenName:'PROD_ID',
				singleSelect:false,
				anchor : '90%'
			}),{
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'CUST_CONTACT_NAME',
				anchor : '90%'
			},new Ext.form.ComboBox({
    			hiddenName : 'CUST_TYPE_ORA',
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
				name : 'PLAN_AMOUNT',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}
    
    		]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ 
			new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_TYPE_ORA',
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
    			hiddenName : 'OPPOR_STAGE_ORA',
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
				fieldLabel : '商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'OPPOR_END_DATE',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '营销任务指标', 
				//id:'productSelect_close_quota_02',
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
				//id:'productSelect_close_activ_02',
				labelStyle: 'text-align:right;',
				name : 'MKT_ACTIV_NAME',
				hiddenName:'MKT_ACTIV_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseActivView();
				}
			}),new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称', 
				labelWidth : 100,
				name : 'CUST_NAME',
				//custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
				//custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
			    singleSelected:true,//单选复选标志
				editable : false,
				allowBlank:false,//不允许为空
				blankText:"不能为空，请填写",
				anchor : '90%',
				hiddenName:'CUST_ID',
				callback :function(){					
				}
			}),new Ext.form.ComboBox({
    			hiddenName : 'CUST_CATEGORY_ORA',
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
    			hiddenName : 'REACH_PROB',
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
				name : 'PLAN_COST',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}
			]
		} ]
	},
	{
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'OPPOR_CONTENT',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'MEMO',
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text:'保存',
  		handler:function(){
	  		if(!auditVipCardPitchTwoForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		Ext.Ajax.request({
	  			url:basepath+'/VipCardHandoutAuditOperationAction!savePitchTwoAudit.json',
	  			method: 'POST',
	  			form:auditVipCardPitchTwoForm.getForm().id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				params : {
	  				'chkRecords':auditVipCardPitchTwoForm.form.findField('chkRecord').getValue()
	  			},
	  			success : function(response) {
		  			Ext.Msg.alert('提示', '保存成功！');
		  			store.load({
		  				params : {
		  					start : 0,
		  					limit : bbar.pageSize
		  				}
		  			});
	  			},
		  		failure : function(response) {
		  			Ext.Msg.alert('提示','保存失败！');
		  		}
	  		});
	  		editChanceWindow.hide();
	  	}
  	},{
  		text: '关闭',
	  	handler:function(){
	  		editChanceWindow.hide();
	  	}
  	}]
});

// 商机详情窗口From表单
var viewChanceForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 470,
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
				fieldLabel : '商机ID',
				hidden : true,
				name : 'OPPOR_ID',
				//id : 'opporId_view',
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'OPPOR_NAME',
				disabled : true,
				anchor : '90%'
			},
			new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_STAT_ORA',
    			fieldLabel : '商机状态',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceStateStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
				disabled : true,
    			anchor : '90%'
    		}),new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_SOURCE_ORA',
    			fieldLabel : '商机来源',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceSourceStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			//id : 'opporSource_view',
    			forceSelection : true,
    			emptyText:'请选择 ',
    			resizable : true,
				disabled : true,
    			anchor : '90%'
    		}),{
				xtype : 'datefield',
				fieldLabel : '商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'OPPOR_START_DATE',
				disabled : true,
				anchor : '90%'
			},{
				xtype : 'datefield',
				fieldLabel : '商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'OPPOR_DUE_DATE',
				disabled : true,
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '商机产品', 
				//id:'productSelect_view',
				labelStyle: 'text-align:right;',
				name : 'PROD_NAME',
				hiddenName:'PROD_ID',
				singleSelect:false,
				disabled : true,
				anchor : '90%'
			}),{
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'CUST_CONTACT_NAME',
				disabled : true,
				anchor : '90%'
			},new Ext.form.ComboBox({
    			hiddenName : 'CUST_TYPE_ORA',
    			fieldLabel : '客户类型',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : chanceTypeStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
				disabled : true,
    			anchor : '90%'
    		}),{
				xtype : 'numberfield',
				fieldLabel : '预计金额（元）',
				name : 'PLAN_AMOUNT',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}
    		]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ 
			new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_TYPE_ORA',
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
				disabled : true,
    			anchor : '90%'
    		}),new Ext.form.ComboBox({
    			hiddenName : 'OPPOR_STAGE_ORA',
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
				disabled : true,
    			anchor : '90%'
    		}),{
				xtype : 'datefield',
				fieldLabel : '商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'OPPOR_END_DATE',
				disabled : true,
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '营销任务指标',
				name : 'MKT_TARGET_NAME',
				disabled : true,
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '营销活动名称',
				name : 'MKT_ACTIV_NAME',
				disabled : true,
				anchor : '90%'
			},new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称', 
				labelWidth : 100,
				name : 'CUST_NAME',
				//custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
				//custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
			    singleSelected:true,//单选复选标志
				editable : false,
				allowBlank:false,//不允许为空
				blankText:"不能为空，请填写",
				anchor : '90%',
				hiddenName:'CUST_ID',
				disabled : true,
				callback :function(){					
				}
			}),new Ext.form.ComboBox({
    			hiddenName : 'CUST_CATEGORY_ORA',
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
				disabled : true,
    			anchor : '90%'
    		}), new Ext.form.ComboBox({
    			hiddenName : 'REACH_PROB',
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
				disabled : true,
    			anchor : '90%'
    		}),{
				xtype : 'numberfield',
				fieldLabel : '费用预算（元）',
				name : 'PLAN_COST',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}
			]
		} ]
	},
	{
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'OPPOR_CONTENT',
			disabled : true,
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'MEMO',
			disabled : true,
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text: '关闭',
	  	handler:function(){
	  		viewChanceWindow.hide();
	  	}
  	}]
});

//定义商机详情查看页面的tab页面
var detailTap = new Ext.TabPanel({
	activeTab : 0,
	tabPosition : 'top',//控制tab页签显示的位置（顶部：top；底部：bottom）
	height : 470,
	items : [{
		title : '商机详情',
		items : [ viewChanceForm ]
	},{
		title : '销售活动',
		items : [ listPanel_sales_view ],
		listeners : {
			'activate' : function() {
				var oppor_id = viewChanceForm.form.findField('OPPOR_ID').getValue();//选中记录的商机ID 
				salesActivStore_view.load({
					params : {
						start : 0,
						condition : '',
						'oppor_id' : oppor_id
					}
				});
			}
		}
	}]
});
 
// 定义新增窗口
var addChanceWindow = new Ext.Window( {
	title : '商机新增',
	plain : true,
	layout : 'fit',
	width : 800,
	height : 470,
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
	items : [ addChanceForm ],
	listeners:{
  		"hide":function(){
  			addChanceForm.getForm().reset();
		},
		"show":function(){//窗体显示时间，进行一些数据设置初始化操作
			//Ext.getCmp('opporStat_add').setValue('0');//商机状态：0，暂存
			//Ext.getCmp('opporSource_add').setValue('0');//商机来源：0，手工创建
			//Ext.getCmp('opporSource_add').readOnly = true;//商机来源只读
		}
	}
});

// 定义维护商机窗口
var editChanceWindow = new Ext.Window( {
	title : '商机维护',
	plain : true,
	layout : 'fit',
	width : 800,
	height : 470,
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
	items : [ editChanceForm ]
});	

// 定义详情查看窗口
var viewChanceWindow = new Ext.Window( {
	title : '商机详情',
	plain : true,
	layout : 'fit',
	width : 800,
	height : 470,
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
	items : [ detailTap ],
	listeners:{
		'beforeshow':function(){
			detailTap.setActiveTab(0);//显示第一个tab页签内容					
		}
	}
});

// 打开 新增商机 窗口
function addMyBusOpportInit() {
	addChanceForm.form.findField('custName').setValue('南京春晖科技实业有限公司');
	addChanceForm.form.findField('custCategoty').setValue('对公客户');
	addChanceForm.form.findField('custContactName').setValue('王凯');
	addChanceForm.form.findField('custType').setValue('正式客户');
	addChanceWindow.show();
}

//清空新增商机Form表单
function resetAddForm(){
	addChanceForm.getForm().reset();
}

//客户视图中，在“适合的产品”界面新增商机，设置产品信息
function setAddFormPara(proName){
	//Ext.getCmp('productSelect_add').setValue(proName);
}

// 打开 维护商机 窗口
function editMyBusOpportInit() {
	var record = grid.getSelectionModel().getSelected();
	if(record == null){
		Ext.Msg.alert('提示', '请先选择要维护的商机！');
		return false;
	}
	editChanceForm.getForm().reset();
	editChanceForm.getForm().loadRecord(record);
	editChanceWindow.show();
}

// 打开 商机详情 窗口
function viewMyBusOpportInit() {
    var selectLength = grid.getSelectionModel().getSelections().length;
    if(selectLength == 0){
    	Ext.Msg.alert('提示', '请先选择要查看的商机！');
        return false;
    }else if(selectLength > 1){
    	Ext.Msg.alert('提示', '只能选择一个商机进行查看！');
        return false;
    }else{
    	var record = grid.getSelectionModel().getSelected();
    	viewChanceForm.getForm().reset();
    	viewChanceForm.getForm().loadRecord(record);
    	viewChanceWindow.show();
    }
}