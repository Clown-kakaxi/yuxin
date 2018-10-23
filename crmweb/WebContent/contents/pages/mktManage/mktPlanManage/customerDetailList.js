// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

var custColumns = new Ext.grid.ColumnModel([rownum,sm,
	{
		header : '客户编号 ',
		width : 150,
		align : 'center',
		dataIndex : 'customerId',
		sortable : true
	}, {
		header : '客户名称 ',
		width : 180,
		align : 'center',
		dataIndex : 'customerName',
		sortable : true
	},{
		header : '执行人 ',
		width : 180,
		align : 'center',
		dataIndex : 'executor',
		sortable : true
	}, {
		header : '创建人',
		width : 120,
		align : 'center',
		dataIndex : 'createUser',
		sortable : true
	}, {
		header : '创建日期',
		width : 120,
		align : 'center',
		dataIndex : 'createDate',
		sortable : true
	} ]
);

var custRecord = Ext.data.Record.create([ {
	name : 'planCustomerdetailId',mapping : 'PCDE_ID'
},{
	name : 'customerId',mapping : 'CUST_ID'
}, {
	name : 'customerName',mapping : 'CUST_NAME'
},{
	name : 'executor',mapping : 'EXECUTOR_NAME'
} ,{
	name : 'createUser',mapping : 'CREATE_USER_NAME'
}, {
	name : 'createDate',mapping : 'CREATE_DATE'
},{
	name : 'planId',mapping : 'PLAN_ID'
}
]);

var custStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planCustomerQuery.json'
	}),
	reader : new Ext.data.JsonReader({
		root : 'json.data'
	}, custRecord)
});

var custListPanel = new Ext.grid.GridPanel({
	height : 445,
	store : custStore,
	frame : true,
	sm : sm, // 复选框
	cm : custColumns,
	stripeRows : true,
	tbar : [ {
		text : '新增',
		iconCls : 'page_addIcon',
		handler : function() {
			addCustInit();
		}
	},  '-', {
		text : '删除',
		iconCls : 'page_delIcon',
		handler : function() {
				 var selectLength = custListPanel.getSelectionModel()
					.getSelections().length;
					
			        if(selectLength < 1){
						Ext.Msg.alert('提示','请选择需要删除的记录!');
					} 
			        
			        else {
			        	Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
												if(buttonId.toLowerCase() == "no"){
      												return;
      				    } 
						var selectRe;
						var tempId;
						var idStr = '';
						for(var i = 0; i<selectLength;i++)
						{
							selectRe = custListPanel.getSelectionModel()
							.getSelections()[i];
							tempId = selectRe.data.planCustomerdetailId;
							idStr += tempId;
							if( i != selectLength-1)
								idStr += ',';
						}
						Ext.Ajax.request({
							url : basepath+'/plan-customer/'
									+tempId+'/batchDestroy.json?idStr='+idStr,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								custStore.reload();
							},
							failure : function(response) {
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								custStore.reload();
							}
						});
						
						});
			            }
					}
		}
	]
});

// 新增窗口展示的from
var addCustForm = new Ext.form.FormPanel({
	labelWidth : 150,
	height : 300,
	frame : true,
	region : 'center',
	autoScroll : true,
	buttonAlign : 'center',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				width : '100',
				fieldLabel : '客户号',
				name : 'customerId',
				anchor : '90%'
			},{
				name : 'customerName',
				xtype : 'textfield',
				fieldLabel : '客户名称 ',
				width : '200',
				anchor : '90%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				width : '100',
				fieldLabel : '执行人',
				name : 'executor',
				anchor : '90%'
			}, {
				// 隐藏的planId
				xtype : 'hidden',
				width : 200,
				fieldLabel : '营销计划ID',
				id : 'currPlanId',
				name : 'planId',
				anchor : '90%'
			} ]
		}

		]

	} ],

	buttons : [

	{

		text : '保  存',
		handler : function() {
			document.getElementById('currPlanId').value = document.getElementById('planIdStr').value;
			Ext.Ajax.request({

				url : basepath+'/plan-customer.json',
				method : 'POST',
				form : addCustForm.getForm().id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Msg.alert('提示', '操作成功');
					custStore.reload();
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				}
			});
			addCustWindow.hide();
		}
	}, {
		text : '取  消',
		handler : function() {
			addCustWindow.hide();
		}
	} ]

});

// 定义新增窗口
var addCustWindow = new Ext.Window({
	title : '客户新增',
	plain : true,
	layout : 'fit',
	width : 800,
	height : 200,
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
	items : [ addCustForm ]
});
//
// //定义修改窗口
// var editPlanWindow = new Ext.Window({
// title : '营销计划修改',
// plain : true,
// layout : 'fit',
// width : 800,
// height : 500,
// resizable : true,
// draggable : true,
// closable : true,
// closeAction : 'hide',
// modal : true, // 模态窗口
// loadMask : true,
// maximizable : true,
// collapsible : true,
// titleCollapse : true,
// border : false,
// items : [editPlanPanel]
//
// });
//
// 展示新增窗口
function addCustInit() {
	addCustWindow.show();

}