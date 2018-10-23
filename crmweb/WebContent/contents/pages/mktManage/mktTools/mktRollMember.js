
//新增名单成员
var addCustomer = new Ext.FormPanel({
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
				name : 'cust_zh_name',
				anchor : '95%'
			} ]
		}, {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '组织机构代码',
				name : 'cust_zzdm',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			cusstore.reload({
				params : {
					start : 0,
					rollId : document.getElementById('rollIdStr').value,
					limit : cusbbar.pageSize
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			addCustomer.getForm().reset();
		}

	} ]
});

var cussm = new Ext.grid.CheckboxSelectionModel();
var cusrownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

// 定义列模型
var cuscm = new Ext.grid.ColumnModel([ cusrownum, cussm, {
	header : 'cust_id',
	dataIndex : 'CUST_ID',
	sortable : true,
	width : 150,
	hidden : true
}, {
	header : '组织机构代码',
	dataIndex : 'CUST_ZZDM',
	sortable : true,
	width : 150
}, {
	header : '客户名称',
	dataIndex : 'CUST_ZH_NAME',
	sortable : true,
	width : 250
}, {
	header : '客户类型',
	dataIndex : 'CUST_TYP',
	sortable : true,
	width : 150,
	hidden : true
}, {
	header : '客户级别',
	dataIndex : 'CUST_LEV',
	sortable : true,
	width : 150,
	hidden : true
} ]);
/**
 * 数据存储
 */
var cusstore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/rollCustCanAddQuery.json'
	}),
	reader : new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ 'CUST_ID', 'CUST_ZH_NAME', 'CUST_ZZDM', 'CUST_TYP', 'CUST_LEV' ])
});

cusstore.on('beforeload', function() {
	   var conditionStr =  addCustomer.getForm().getValues(false);
    this.baseParams = {
           "condition":Ext.encode(conditionStr)
   };});



var cuspagesize_combo = new Ext.form.ComboBox({
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

var cusnumber = parseInt(cuspagesize_combo.getValue());
// 改变每页显示条数reload数据
cuspagesize_combo.on("select", function(comboBox) {
	cusbbar.pageSize = parseInt(cuspagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	cusstore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('rollIdStr').value,
			limit : parseInt(cuspagesize_combo.getValue())
		}
	});
});

var cusGroupcombo = new Ext.form.ComboBox({
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

// 分页工具栏
var cusbbar = new Ext.PagingToolbar({
	pageSize : cusnumber,
	store : cusstore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', cuspagesize_combo ]
});

// 新增名单关联客户的表格面板
var custbar = new Ext.Toolbar({
	items : [ {
		text : '归入名单',
		handler : function() {
			batchAdd();
		}
	} ]
});

var cusGrid = new Ext.grid.GridPanel({
	height : 260,
	frame : true,
	autoScroll : true,
	store : cusstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : cuscm, // 列模型
	sm : cussm, // 复选框
	bbar : cusbbar,
	tbar : custbar,
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var custRecord = Ext.data.Record.create([ {
	name : 'id',
	mapping : 'ID'
}, {
	name : 'customerId',
	mapping : 'CUST_ID'
}, {
	name : 'customerName',
	mapping : 'CUST_NAME'
}, {
	name : 'zzdm',
	mapping : 'ZZDM'
}, {
	name : 'updateUser',
	mapping : 'UPDATE_USER'
}, {
	name : 'updateDate',
	mapping : 'UPDATE_DATE'
}, {
	name : 'createUserName',
	mapping : 'CREATE_USER_NAME'
}, {
	name : 'createDate',
	mapping : 'CREATE_DATE'
}, {
	name : 'rollId',
	mapping : 'ROLL_ID'
} ]);

var custStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/rollMemberQuery.json'
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, custRecord)
});

custStore.on('beforeload', function() {
    this.baseParams = {
 		   rollId: document.getElementById('rollIdStr').value
   };});

// 改变每页显示条数reload数据
cusGroupcombo.on("select", function(comboBox) {
	cusGroupBbar.pageSize = parseInt(cusGroupcombo.getValue()), custStore
			.reload({
				params : {
					start : 0,
					rollId : document.getElementById('rollIdStr').value,
					limit : parseInt(cusGroupcombo.getValue())
				}
			});
});

var cusGroupBbar = new Ext.PagingToolbar({
	pageSize : parseInt(cusGroupcombo.getValue()),
	store : custStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', cusGroupcombo ]
});

var memberColumns = new Ext.grid.ColumnModel([ rownum, sm, {
	header : '客户编号 ',
	width : 100,
	align : 'center',
	dataIndex : 'customerId',
	sortable : true
}, {
	header : '客户名称 ',
	width : 170,
	align : 'center',
	dataIndex : 'customerName',
	sortable : true
}, {
	header : '组织机构代码',
	width : 100,
	align : 'center',
	dataIndex : 'zzdm',
	sortable : true
}, {
	header : '创建人',
	width : 100,
	align : 'center',
	dataIndex : 'createUserName',
	sortable : true
}, {
	header : '创建日期',
	width : 100,
	align : 'center',
	dataIndex : 'createDate',
	sortable : true
} ]);

var cusGroupMemeberGrid = new Ext.grid.GridPanel({
	title : '名单关联客户列表',
	frame : true,
	height : 357,
	autoScroll : true,
	store : custStore,
	stripeRows : true, // 斑马线
	cm : memberColumns,
	sm : sm,
	tbar : [ {
		text : '移除名单',
		iconCls : 'page_delIcon',
		handler : function() {
			 var selectLength = cusGroupMemeberGrid.getSelectionModel()
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
						selectRe = cusGroupMemeberGrid.getSelectionModel()
						.getSelections()[i];
						tempId = selectRe.data.id;
						idStr += tempId;
						if( i != selectLength-1)
							idStr += ',';
					}
					Ext.Ajax.request({
						url : basepath+'/roll-member/'
								+tempId+'/batchDestroy.json?idStr='+idStr,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							custStore.reload();
							cusstore.reload({
								params : {
									start : 0,
									rollId : document.getElementById('rollIdStr').value,
									limit : parseInt(cuspagesize_combo.getValue())
								}
							});
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							custStore.reload();
							cusstore.reload({
								params : {
									start : 0,
									rollId : document.getElementById('rollIdStr').value,
									limit : parseInt(cuspagesize_combo.getValue())
								}
							});
						}
					});
					
					});
		            }
				}
	} ],
	bbar : cusGroupBbar,
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var batchAdd = function() {
	var selectLength = cusGrid.getSelectionModel().getSelections().length;

	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要加入名单的客户!');
	} else {
		var selectRe;
		var tempId;
		var idStr = '';
		for ( var i = 0; i < selectLength; i++) {
			selectRe = cusGrid.getSelectionModel().getSelections()[i];
			tempId = selectRe.data.CUST_ID;
			idStr += "'", idStr += tempId;
			idStr += "'";
			if (i != selectLength - 1)
				idStr += ",";
		}
		;
		Ext.Ajax.request({
			url : basepath + '/roll-member/1' + '/batchCreate.json?idStr='
					+ idStr + '&rollId='
					+ document.getElementById("rollIdStr").value,
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			success : function() {

				Ext.Msg.alert('提示', '操作成功');
				custStore.reload({
					params : {
						rollId : document.getElementById('rollIdStr').value,
						start : 0,
						limit : parseInt(cusGroupcombo.getValue())
					}
				});
				cusstore.reload({
					params : {
						start : 0,
						rollId : document.getElementById('rollIdStr').value,
						limit : parseInt(cuspagesize_combo.getValue())
					}
				});
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
			}
		});
	}
};