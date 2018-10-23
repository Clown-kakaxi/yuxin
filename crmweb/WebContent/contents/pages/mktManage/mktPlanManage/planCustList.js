
// 定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

var planCustColumns = new Ext.grid.ColumnModel([planRownum,
    {
		header : '客户名称 ',
		width : 150,
		align : 'left',
		dataIndex : 'customerName',
		sortable : true
	},{
		header : '客户编号 ',
		width : 100,
		align : 'left',
		dataIndex : 'customerId',
		sortable : true
	}, {
		header : '主办机构名称',
		dataIndex : 'institutionName',
		sortable : true,
		width : 150
	},{
		header : '主办机构号',
		dataIndex : 'orgId',
		sortable : true,
		width : 100
	},{
		header : '主办客户经理名称',
		dataIndex : 'mgrName',
		width : 150,
		sortable : true
	},{
		header : '主办客户经理号',
		dataIndex : 'mgrId',
		width : 100,
		sortable : true
	},{
		header : '创建人',
		width : 120,
		align : 'left',
		dataIndex : 'createUser',
		sortable : true
	}, {
		header : '创建日期',
		width : 120,
		align : 'left',
		dataIndex : 'createDate',
		sortable : true
	} ]
);

var planCustRecord = Ext.data.Record.create([ {
	name : 'planCustomerdetailId',mapping : 'PCDE_ID'
},{
	name : 'customerId',mapping : 'CUST_ID'
}, {
	name : 'customerName',mapping : 'CUST_NAME'
},{
	name: 'orgId',mapping : 'ORG_ID'
},{
	name: 'institutionName',mapping : 'INSTITUTION_NAME'
},{
	name: 'mgrName',mapping : 'MGR_NAME'
},{
	name: 'mgrId',mapping : 'MGR_ID'
}, {
	name : 'createUserName',
	mapping : 'CREATE_USER_NAME'
}, {
	name : 'createDate',
	mapping : 'CREATE_DATE'
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

var planCustStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/planCustomerQuery.json',
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			}
		}),
		reader : new Ext.data.JsonReader({
			totalProperty:'json.count',
			root : 'json.data'
		}, planCustRecord)
	});


var addPlan1Customer = new Ext.FormPanel({
	// layout:'fit',
	title : '客户查询',
	frame : true,
	border : false,
	labelAlign : 'right',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '客户名称',
				name : 'CUST_NAME',
				anchor : '95%'
			} ]
		}, {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '客户编号',
				name : 'CUST_ID',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			var planId=Ext.getCmp('listPanel').getSelectionModel().getSelected().get('planId');
		    var conditionStr = addPlan1Customer.getForm().getFieldValues();
	//	    alert(conditionStr);
		    planCustStore.baseParams = {
                    "condition" : Ext.encode(conditionStr)
                };
		    planCustStore.reload({
				params : {
					start : 0,
					rollId : document.getElementById('planIdStr').value,
					limit : plancus1bbar.pageSize,
					planId : planId
					
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			addPlan1Customer.getForm().reset();
		}

	} ]
});


var plancus1pagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
				[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	resizable : true,
	width : 85
});

plancus1pagesize_combo.on("select", function(comboBox) {
	plancus1bbar.pageSize = parseInt(plancus1pagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	planCustStore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('planIdStr').value,
			planId : document.getElementById('planIdStr').value,
			limit : plancus1bbar.pageSize
		}
	});
});

var plancus1number = parseInt(plancus1pagesize_combo.getValue());

planCustStore.on('beforeload', function() {
	var conditionStr =  addPlan1Customer.getForm().getValues(false);
	this.baseParams = {
			"condition":Ext.encode(conditionStr),
			planId : document.getElementById('planIdStr').value
	};});

var plancus1bbar = new Ext.PagingToolbar({
	pageSize : plancus1number,
	store : planCustStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', plancus1pagesize_combo ]
});

var planCustListPanel = new Ext.grid.GridPanel({
	height : 315,
	store : planCustStore,
	autoScroll:true,
	frame : true,
	cm : planCustColumns,
	stripeRows : true,
	bbar : plancus1bbar
});
