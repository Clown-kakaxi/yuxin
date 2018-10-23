
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

var salesActivStore_view = new Ext.data.Store({
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
	
var listPanel_sales_view = new Ext.grid.GridPanel({
	layout:'fit',
	frame : true,
	autoScroll : true,
	region : 'center', // 返回给页面的div
	store: salesActivStore_view,
	stripeRows : true, // 斑马线
	sm : sm_sales,
	cm : cm_salse,
	height : 470,
	tbar : [
        {
        	text : '活动详情',
			iconCls : 'detailIconCss',
			handler : function() {
				viewSalesActivInitView();
			}
		}
	],
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//活动详情窗口From表单
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
	
// 打开 销售活动详情 窗口
function viewSalesActivInitView() {
	var record = listPanel_sales_view.getSelectionModel().getSelected();
	if(record == null){
		Ext.Msg.alert('提示', '请先选择要查看的活动！');
		return;
	}
	viewSalesActivForm.getForm().reset();
	viewSalesActivForm.getForm().loadRecord(record);
	viewSalesActivWindow.show();
}