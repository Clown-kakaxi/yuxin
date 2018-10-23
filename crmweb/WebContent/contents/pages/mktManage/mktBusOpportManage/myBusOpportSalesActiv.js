
//销售阶段
var sales_stage_store = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=SALES_STAGE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, 
	[ 'key', 'value' ]
	)
});

//活动执行方式
var exec_way_store = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=EXEC_WAY'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, 
	[ 'key', 'value' ]
	)
});

var salesActivStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/mktBusiOpporSalesActivQueryAction.json'
	}),
	reader: new Ext.data.JsonReader(
		{
			totalProperty : 'json.count',
			root:'json.data'
		},
		[
		 	{name: 'SALES_ACTIV_ID'},
		 	{name: 'OPPOR_ID'},
		 	{name: 'SALES_ACTIV_NAME'},
		 	{name: 'SALES_STAGE_ORA'},
		 	{name: 'EXEC_DATE'},
		 	{name: 'EXEC_WAY_ORA'},
		 	{name: 'ACTIV_CONTENT'},
		 	{name: 'EXEC_USER_ID'},
		 	{name: 'EXEC_USER_NAME'},
		 	{name: 'EXEC_ORG_ID'},
		 	{name: 'EXEC_ORG_NAME'},
		 	{name: 'NEXT_CONTACT_TIME'},
		 	{name: 'NEXT_EXEC_WAY_ORA'},
		 	{name: 'NEXT_EXEC_CONTENT'},
		 	{name: 'ACTIV_MEMO'}
		 ]
	)
});
	
var sm_sales = new Ext.grid.CheckboxSelectionModel();
var rownum_sales = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var cm_salse = new Ext.grid.ColumnModel([
	rownum_sales,
	sm_sales,	// 定义列模型
	{header : '销售活动编号', dataIndex : 'SALES_ACTIV_ID',sortable : true,width : 120,hidden : true},
	{header : '销售阶段', dataIndex : 'SALES_STAGE',sortable : true,width : 120,hidden : true},
	{header : '活动执行方式', dataIndex : 'EXEC_WAY',sortable : true,width : 120,hidden : true},
	{header : '下次执行方式', dataIndex : 'NEXT_EXEC_WAY',sortable : true,width : 120,hidden : true},
	{header : '销售活动名称', dataIndex : 'SALES_ACTIV_NAME',sortable : true,width : 120}, 
	{header : '销售阶段', dataIndex : 'SALES_STAGE_ORA',sortable : true,width : 120}, 
	{header : '活动执行日期', dataIndex : 'EXEC_DATE',sortable : true,width : 120},
	{header : '活动执行方式', dataIndex : 'EXEC_WAY_ORA',sortable : true,width : 120},
	{header : '活动执行人', dataIndex : 'EXEC_USER_NAME',sortable : true,width : 120},
	{header : '活动执行机构', dataIndex : 'EXEC_ORG_NAME',sortable : true,width : 120},
	{header : '下次联系时间', dataIndex : 'NEXT_CONTACT_TIME',sortable : true,width : 120},
	{header : '下次执行方式', dataIndex : 'NEXT_EXEC_WAY_ORA',sortable : true,width : 120}
]);
	
var listPanel_sales = new Ext.grid.GridPanel({
	layout:'fit',
	frame : true,
	autoScroll : true,
	region : 'center', // 返回给页面的div
	store: salesActivStore,
	stripeRows : true, // 斑马线
	sm : sm_sales,
	cm : cm_salse,
	height : 470,
	tbar : [
        {
        	text : '活动详情',
			iconCls : 'detailIconCss',
			handler : function() {
				viewSalesActivInit();
			}
		},{
        	text : '新增活动',
			iconCls : 'addIconCss',
			handler : function() {
				addSalesActivInit();
				addSalesActivForm.getForm().reset();
			}
		},{
        	text : '维护活动',
			iconCls : 'editIconCss',
			handler : function() {
				editSalesActivInit();
			}
		}
	],
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
	
// 新增销售活动窗口From表单
var addSalesActivForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '销售活动名称',
				allowBlank : true,
				name : 'SALES_ACTIV_NAME',
				anchor : '90%'
			},{
				xtype : 'datefield',
				fieldLabel : '活动执行日期',
				format : 'Y-m-d',
				editable : true,
				name : 'EXEC_DATE',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				store : sales_stage_store,
				xtype : 'combo',
				resizable : true,
				fieldLabel : '销售阶段',
				name : 'SALES_STAGE',
				hiddenName : 'SALES_STAGE',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			},{
				store : exec_way_store,
				xtype : 'combo',
				resizable : true,
				fieldLabel : '活动执行方式',
				name : 'EXEC_WAY',
				hiddenName : 'EXEC_WAY',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [new Com.yucheng.crm.common.OrgUserManage({ 
				xtype        : 'userchoose',
				fieldLabel   : '活动执行人',
                allowBlank   : false,
				labelStyle   : 'text-align:right;',
				name         : 'EXEC_USER_NAME',
				hiddenName   : 'EXEC_USER_ID',
				//searchRoleType:('127,47'),  //指定查询角色属性
				searchType   : 'SUBORGS',//*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）
				singleSelect : true,//控制是否只能单选
				anchor       : '90%'
			}),new Com.yucheng.bcrm.common.OrgField({
				searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				fieldLabel : '活动执行机构',
				labelStyle : 'text-align:right;',
				name : 'EXEC_ORG_NAME', 
				hiddenName: 'EXEC_ORG_ID',   //后台获取的参数名称
				anchor : '90%',
				checkBox:false //复选标志
			})]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'datefield',
				fieldLabel : '下次联系时间',
				format : 'Y-m-d',
				editable : true,
				name : 'NEXT_CONTACT_TIME',
				anchor : '90%'
			},{
				store : exec_way_store,
				xtype : 'combo',
				resizable : true,
				fieldLabel : '下次执行方式',
				name : 'NEXT_EXEC_WAY',
				hiddenName : 'NEXT_EXEC_WAY',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			}]
		}]
	},
	{
		layout : 'form',
		items : [{
			xtype : 'textarea',
			fieldLabel : '活动内容',
			name : 'ACTIV_CONTENT',
			anchor : '95%'
		},{
			xtype : 'textarea',
			fieldLabel : '下次执行内容',
			name : 'NEXT_EXEC_CONTENT',
			anchor : '95%'
		},{
			xtype : 'textarea',
			fieldLabel : '备注',
			name : 'ACTIV_MEMO',
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text:'保存',
  		handler:function(){
	  		if(!addSalesActivForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		/*Ext.Ajax.request({
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
		  					limit : bbar_sales.pageSize
		  				}
		  			});
	  			},
		  		failure : function(response) {
		  			Ext.Msg.alert('提示','保存失败！');
		  		}
	  		});*/
	  		Ext.Msg.alert('提示', '保存成功！');
	  		addSalesActivWindow.hide();
	  	}
  	},{
  		text: '关闭',
	  	handler:function(){
	  		addSalesActivWindow.hide();
	  	}
  	}]
});
	
// 活动维护窗口From表单
var editSalesActivForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '销售活动名称',
				allowBlank : true,
				name : 'SALES_ACTIV_NAME',
				anchor : '90%'
			},{
				xtype : 'datefield',
				fieldLabel : '活动执行日期',
				format : 'Y-m-d',
				editable : true,
				name : 'EXEC_DATE',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				store : sales_stage_store,
				xtype : 'combo',
				resizable : true,
				fieldLabel : '销售阶段',
				name : 'SALES_STAGE_ORA',
				hiddenName : 'SALES_STAGE_ORA',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			},{
				store : exec_way_store,
				xtype : 'combo',
				resizable : true,
				fieldLabel : '活动执行方式',
				name : 'EXEC_WAY_ORA',
				hiddenName : 'EXEC_WAY_ORA',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [new Com.yucheng.crm.common.OrgUserManage({ 
				xtype        : 'userchoose',
				fieldLabel   : '活动执行人',
                allowBlank   : false,
				labelStyle   : 'text-align:right;',
				name         : 'EXEC_USER_NAME',
				hiddenName   : 'EXEC_USER_ID',
				//searchRoleType:('127,47'),  //指定查询角色属性
				searchType   : 'SUBORGS',//*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）
				singleSelect : true,//控制是否只能单选
				anchor       : '90%'
			}),new Com.yucheng.bcrm.common.OrgField({
				searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				fieldLabel : '活动执行机构',
				labelStyle : 'text-align:right;',
				name : 'EXEC_ORG_NAME', 
				hiddenName: 'EXEC_ORG_ID',   //后台获取的参数名称
				anchor : '90%',
				checkBox:false //复选标志
			})]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'datefield',
				fieldLabel : '下次联系时间',
				format : 'Y-m-d',
				editable : true,
				name : 'NEXT_CONTACT_TIME',
				anchor : '90%'
			},{
				store : exec_way_store,
				xtype : 'combo',
				resizable : true,
				fieldLabel : '下次执行方式',
				name : 'NEXT_EXEC_WAY_ORA',
				hiddenName : 'NEXT_EXEC_WAY_ORA',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			}]
		}]
	},
	{
		layout : 'form',
		items : [{
			xtype : 'textarea',
			fieldLabel : '活动内容',
			name : 'ACTIV_CONTENT',
			anchor : '95%'
		},{
			xtype : 'textarea',
			fieldLabel : '下次执行内容',
			name : 'NEXT_EXEC_CONTENT',
			anchor : '95%'
		},{
			xtype : 'textarea',
			fieldLabel : '备注',
			name : 'ACTIV_MEMO',
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text:'保存',
  		handler:function(){
	  		if(!editSalesActivForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		/*Ext.Ajax.request({
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
		  					limit : bbar_sales.pageSize
		  				}
		  			});
	  			},
		  		failure : function(response) {
		  			Ext.Msg.alert('提示','保存失败！');
		  		}
	  		});*/
	  		Ext.Msg.alert('提示', '保存成功！');
	  		editSalesActivWindow.hide();
	  	}
  	},{
  		text: '关闭',
	  	handler:function(){
	  		editSalesActivWindow.hide();
	  	}
  	}]
});
	
// 商机详情窗口From表单
var viewSalesActivForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '销售活动名称',
				allowBlank : true,
				disabled : true,
				name : 'SALES_ACTIV_NAME',
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '活动执行日期',
				allowBlank : true,
				disabled : true,
				name : 'EXEC_DATE',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '销售阶段',
				allowBlank : true,
				disabled : true,
				name : 'SALES_STAGE_ORA',
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '活动执行方式',
				allowBlank : true,
				disabled : true,
				name : 'EXEC_WAY_ORA',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '活动执行人',
				allowBlank : true,
				disabled : true,
				name : 'EXEC_USER_NAME',
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '活动执行机构',
				allowBlank : true,
				disabled : true,
				name : 'EXEC_ORG_NAME',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '下次联系时间',
				allowBlank : true,
				disabled : true,
				name : 'NEXT_CONTACT_TIME',
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '下次执行方式',
				allowBlank : true,
				disabled : true,
				name : 'NEXT_EXEC_WAY_ORA',
				anchor : '90%'
			}]
		}]
	},
	{
		layout : 'form',
		items : [{
			xtype : 'textarea',
			fieldLabel : '活动内容',
			name : 'ACTIV_CONTENT',
			disabled : true,
			anchor : '95%'
		},{
			xtype : 'textarea',
			fieldLabel : '下次执行内容',
			name : 'NEXT_EXEC_CONTENT',
			disabled : true,
			anchor : '95%'
		},{
			xtype : 'textarea',
			fieldLabel : '备注',
			disabled : true,
			name : 'ACTIV_MEMO',
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text: '关闭',
	  	handler:function(){
	  		viewSalesActivWindow.hide();
	  	}
  	}]
});
	 
// 定义新增活动窗口
var addSalesActivWindow = new Ext.Window( {
	title : '新增活动',
	plain : true,
	layout : 'fit',
	width : 700,
	height : 400,
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
	items : [ addSalesActivForm ]
});
	
// 定义活动维护窗口
var editSalesActivWindow = new Ext.Window( {
	title : '活动维护',
	plain : true,
	layout : 'fit',
	width : 700,
	height : 400,
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
	items : [ editSalesActivForm ]
});	

// 定义详情查看窗口
var viewSalesActivWindow = new Ext.Window( {
	title : '活动详情',
	plain : true,
	layout : 'fit',
	width : 700,
	height : 400,
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
	items : [ viewSalesActivForm ]
});

// 打开 新增商机 窗口
function addSalesActivInit() {
	addSalesActivWindow.show();
}

// 打开 维护商机 窗口
function editSalesActivInit() {
	var record = listPanel_sales.getSelectionModel().getSelected();
	if(record == null){
		Ext.Msg.alert('提示', '请先选择要维护的商机！');
		return;
	}
	editSalesActivForm.getForm().reset();
	editSalesActivForm.getForm().loadRecord(record);
	editSalesActivWindow.show();
}
	
// 打开 销售活动详情 窗口
function viewSalesActivInit() {
	var record = listPanel_sales.getSelectionModel().getSelected();
	if(record == null){
		Ext.Msg.alert('提示', '请先选择要查看的活动！');
		return;
	}
	viewSalesActivForm.getForm().reset();
	viewSalesActivForm.getForm().loadRecord(record);
	viewSalesActivWindow.show();
}
	
// 定义销售活动窗口
var salesActivWindow = new Ext.Window( {
	title : '销售活动',
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
	items : [ listPanel_sales ]
});

// 销售活动
function salesActivInit() {
	var record = grid.getSelectionModel().getSelected();
	if(record == null){
		Ext.Msg.alert('提示', '请先选择商机！');
		return;
	}
	var selectRe = grid.getSelectionModel().getSelections()[0];
	var oppor_id = selectRe.data.OPPOR_ID;//选中记录的商机ID
	salesActivStore.load({
		params : {
			start : 0,
			condition : '',
			'oppor_id' : oppor_id
		}
	});
	salesActivWindow.show();
}
