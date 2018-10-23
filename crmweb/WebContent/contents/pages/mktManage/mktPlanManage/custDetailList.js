var msFlag=0;

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

var exesm = new Ext.grid.CheckboxSelectionModel();
var exerownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

//add by huangyan begin
var cusGroupMemeberGrid = new Ext.grid.GridPanel({
	title : '名单客户列表',
	frame : true,
	height : 357,
	autoScroll : true,
	store : custStore,
	stripeRows : true, // 斑马线
	cm : memberColumns,
	sm : sm,
	tbar : [ {
		text : '移出名单',
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
						url : basepath + '/roll-member-batch!batchDestroy',
						method:'POST',
						params:{
							idStr:idStr
						},
//						url : basepath+'/roll-member/'
//								+tempId+'/batchDestroy.json?idStr='+idStr,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失

						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							custStore.reload({
							    params : {
							        planId : document.getElementById('planIdStr').value
							    }
							});
							cusstore.reload({
								params : {
									start : 0,
									rollId : document.getElementById('planIdStr').value,
									planId : document.getElementById('planIdStr').value,
									limit : parseInt(cuspagesize_combo.getValue())
								}
							});
						},
//						failure : function(response) {
//							var resultArray = Ext.util.JSON.decode(response.status);
//							if(resultArray == 403) {
//							window.location = basepath + '/403.jsp';
//							} else {
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//							}
//							//Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//							custStore.reload();
//							cusstore.reload({
//								params : {
//									start : 0,
//									rollId : document.getElementById('planIdStr').value,
//									limit : parseInt(cuspagesize_combo.getValue())
//								}
//							});
//						}
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							}else{
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								custStore.reload();
								cusstore.reload({
									params : {
										start : 0,
										rollId : document.getElementById('planIdStr').value,
										planId : document.getElementById('planIdStr').value,
										limit : parseInt(cuspagesize_combo.getValue())
									}
								});
							}
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
			items : [{
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
				fieldLabel : '客户编号',
				name : 'cust_id',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			//var planId=Ext.getCmp('listPanel').getSelectionModel().getSelected().get('planId');
		    var conditionStr = addCustomer.getForm().getFieldValues();
		    cusstore.baseParams = {
                    "condition" : Ext.encode(conditionStr)
                };
			cusstore.reload({
				params : {
					start : 0,
					//rollId : document.getElementById('planIdStr').value,
					limit : cusbbar.pageSize
					//,
					//planId : planId
					
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			addCustomer.getForm().reset();
			if(msFlag!=0){
                Ext.getCmp('msFlag').setValue('1');
                Ext.getCmp('msFlag').setReadOnly(true);
            }
		}

	} ]
});
/****************************************/
if(window.location.href.split("msFlag=")[1]!=undefined){
    msFlag=1;
}
if(msFlag!=0){
    Ext.getCmp('msFlag').setValue('1');
    Ext.getCmp('msFlag').setReadOnly(true);
}

//指定执行人
var addExecutor = new Ext.FormPanel({
	// layout:'fit',
	title : '营销团队查询',
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
				fieldLabel : '营销团队名称',
				name : 'MKT_TEAM_NAME',
				anchor : '95%'
			} ]
		}, {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '营销团队号',
				name : 'MKT_TEAM_ID',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			var conditionStr = addExecutor.getForm().getValues(false);
			exestore.baseParams = {
				"condition" : Ext.encode(conditionStr)
			};
			exestore.reload({
				params : {
					start : 0,
					rollId : document.getElementById('planIdStr').value,
					limit : cusbbar.pageSize
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			addExecutor.getForm().reset();
		}

	} ]
});

/**
* 数据存储  增加执行人
*/  //huangyan
var exestore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
	url : basepath + '/planCustomerExecutoraddQuery.json',
	failure : function(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		if(resultArray == 403) {
			Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ 'CUST_ID', 'MKT_TEAM_NAME', 'MKT_TEAM_ID', 'CUST_TYP', 'CUST_LEV' ])
});

var cussm = new Ext.grid.CheckboxSelectionModel();
var cusrownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

//定义列模型
var cuscm = new Ext.grid.ColumnModel([ cusrownum, cussm,     {
	header : '客户名称 ',
	width : 150,
	align : 'left',
	dataIndex : 'CUST_ZH_NAME',
	sortable : true
},{
	header : '客户编号 ',
	width : 100,
	align : 'left',
	dataIndex : 'CUST_ID',
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
},{
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

//定义列模型 --执行人
var execm = new Ext.grid.ColumnModel([ exerownum, exesm, {
	header : 'cust_id',
	dataIndex : 'CUST_ID',
	sortable : true,
	width : 150,
	hidden : true
}, {
	header : '营销团队号',
	dataIndex : 'MKT_TEAM_ID',
	sortable : true,
	width : 150
}, {
	header : '营销团队名称',
	dataIndex : 'MKT_TEAM_NAME',
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
	url : basepath + '/rollCustCanAddQuery1.json',
	failure : function(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		if(resultArray == 403) {
			Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ 'CUST_ID', 'CUST_ZH_NAME',  'CUST_TYP', 'CUST_LEV' ])
});

cusstore.on('beforeload', function() {
	   var conditionStr =  addCustomer.getForm().getValues(false);
  this.baseParams = {
         "condition":Ext.encode(conditionStr)
         //,
         //planId : document.getElementById('planIdStr').value
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
//执行人
var exepagesize_combo = new Ext.form.ComboBox({
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
//执行人
var exenumber = parseInt(cuspagesize_combo.getValue());
//改变每页显示条数reload数据
cuspagesize_combo.on("select", function(comboBox) {
	cusbbar.pageSize = parseInt(cuspagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	cusstore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('planIdStr').value,
			planId : document.getElementById('planIdStr').value,
			limit : cusbbar.pageSize
		}
	});
});

//改变每页显示条数reload数据
exepagesize_combo.on("select", function(comboBox) {
	exebbar.pageSize = parseInt(exepagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	exestore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('planIdStr').value,
			planId : document.getElementById('planIdStr').value,
			limit : cusbbar.pageSize
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

var exeGroupcombo = new Ext.form.ComboBox({
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

//分页工具栏
var cusbbar = new Ext.PagingToolbar({
	pageSize : cusnumber,
	store : cusstore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', cuspagesize_combo ]
});

//分页工具栏-- 执行人
var exebbar = new Ext.PagingToolbar({
	pageSize : exenumber,
	store : exestore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', exepagesize_combo ]
});

//新增名单关联客户的表格面板
var custbar = new Ext.Toolbar({
	items : [ {
		text : '增加客户',
		handler : function() {
		Ext.Msg.alert('提示','新增成功!');
		//	batchAdd();
		}
	} ]
});

//增加执行人 
var exetbar = new Ext.Toolbar({
	items : [ {
		text : '指定执行团队',
		handler : function() {
			pointExecutorAdd();
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

var ExeGrid = new Ext.grid.GridPanel({
	height : 260,
	frame : true,
	autoScroll : true,
	store : exestore, // 数据存储
	stripeRows : true, // 斑马线
	cm : execm, // 列模型
	sm : exesm, // 复选框
	bbar : exebbar,
	tbar : exetbar,
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//复选框
var sm = new Ext.grid.CheckboxSelectionModel();

//定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var custRecord = Ext.data.Record.create([ {
	name : 'id',
	mapping : 'PCDE_ID'
}, {
	name : 'customerId',
	mapping : 'CUST_ID'
}, {
	name : 'customerName',
	mapping : 'CUST_NAME'
},{
	name: 'institutionName',
	mapping : 'INSTITUTION_NAME'
},{
	name: 'mgrName',
	mapping : 'MGR_NAME'
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

var custsstore = new Ext.data.Store({
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

custsstore.on('beforeload', function() {
  this.baseParams = {
		   rollId: document.getElementById('planIdStr').value
 };});

//改变每页显示条数reload数据
cusGroupcombo.on("select", function(comboBox) {
	cusGroupBbar.pageSize = parseInt(cusGroupcombo.getValue()), custStore
			.reload({
				params : {
					start : 0,
					rollId : document.getElementById('planIdStr').value,
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

var memberColumns = new Ext.grid.ColumnModel([ rownum, sm, 
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
} 
]);

var cusGroupMemeberGrid = new Ext.grid.GridPanel({
	title : '名单客户列表',
	frame : true,
	height : 357,
	autoScroll : true,
	store : custStore,
	stripeRows : true, // 斑马线
	cm : memberColumns,
	sm : sm,
	tbar : [ {
		text : '移出名单',
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
						url : basepath + '/roll-member-batch!batchDestroy',
						method:'POST',
						params:{
							idStr:idStr
						},
//						url : basepath+'/roll-member/'
//								+tempId+'/batchDestroy.json?idStr='+idStr,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失

						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							custStore.reload();
							cusstore.reload({
								params : {
									start : 0,
									rollId : document.getElementById('planIdStr').value,
									planId : document.getElementById('planIdStr').value,
									limit : parseInt(cuspagesize_combo.getValue())
								}
							});
						},
//						failure : function(response) {
//							var resultArray = Ext.util.JSON.decode(response.status);
//							if(resultArray == 403) {
//							window.location = basepath + '/403.jsp';
//							} else {
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//							}
//							//Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//							custStore.reload();
//							cusstore.reload({
//								params : {
//									start : 0,
//									rollId : document.getElementById('planIdStr').value,
//									limit : parseInt(cuspagesize_combo.getValue())
//								}
//							});
//						}
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							}else{
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								custStore.reload();
								cusstore.reload({
									params : {
										start : 0,
										rollId : document.getElementById('planIdStr').value,
										planId : document.getElementById('planIdStr').value,
										limit : parseInt(cuspagesize_combo.getValue())
									}
								});
							}
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

var memberForm = new Ext.form.FormPanel({
	layout:'form',
	items:[
	{
		name:'idStr',
		hidden:true,
		xtype:'textfield'
	},
	{
		name:'rollIdTemp',
		hidden:true,
		xtype:'textfield'
	}
	]
});

var batchAdd = function() {
	var selectLength = cusGrid.getSelectionModel().getSelections().length;

	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要加入计划的客户!');
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
		
		var rollId = document.getElementById("marketActivityIdStr").value;
		
		memberForm.getForm().findField("idStr").setValue(idStr);
		
		memberForm.getForm().findField("rollIdTemp").setValue(rollId);
		
		Ext.Ajax.request({
//			url : basepath + '/roll-member/1' + '/batchCreate.json?idStr='
//					+ idStr + '&rollId='
//					+ document.getElementById("planIdStr").value,

			url : basepath + '/plancustomerbatch!batchCreate.json',
			params:{
				idStr:idStr,
				rollId:rollId
			},			
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			method:'POST',
			success : function() {

				Ext.Msg.alert('提示', '操作成功');
				cusstore.reload({
					params : {
						start : 0,
						rollId : document.getElementById('marketActivityIdStr').value,
						planId : document.getElementById('marketActivityIdStr').value,
						limit : parseInt(cuspagesize_combo.getValue())
					}
				});
				custStore.reload({
					params : {
						start : 0,
						planId : document.getElementById('marketActivityIdStr').value
					}
				});
							},
//			failure : function(response) {
//				var resultArray = Ext.util.JSON.decode(response.status);
//				if(resultArray == 403) {
//				window.location = basepath + '/403.jsp';
//				} else {
//				Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//				}
//			}
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}else{
					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
					cusstore.reload({
						params : {
							start : 0,
							rollId : document.getElementById('marketActivityIdStr').value,
							planId : document.getElementById('marketActivityIdStr').value,
							limit : parseInt(cuspagesize_combo.getValue())
						}
					});
				}
			}

		});
		
	}
};

var pointExecutorAdd = function() {
	var selectLength = ExeGrid.getSelectionModel().getSelections().length;
	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要指定执行团队的客户!');
	}else if(selectLength >1){
		Ext.Msg.alert('提示', '只能指定一个执行团队!');
		return false;
	}
	else {
		var selectRe;
		var tempId;
		var idStr = '';
		for ( var i = 0; i < selectLength; i++) {
			selectRe = ExeGrid.getSelectionModel().getSelections()[i];
			tempId = selectRe.data.MKT_TEAM_ID;
			idStr += tempId;
			if (i != selectLength - 1)
				idStr += ",";
		}
		
		var rollId = document.getElementById("executeIdStr").value;
//		memberForm.getForm().findField("idStr").setValue(idStr);
//		
//		memberForm.getForm().findField("rollIdTemp").setValue(rollId);
		Ext.Ajax.request({
//			url : basepath + '/roll-member/1' + '/batchCreate.json?idStr='
//					+ idStr + '&rollId='
//					+ document.getElementById("planIdStr").value,

			url : basepath + '/plan-customer-batch/1/updateExecutor.json?idStr=' + idStr + '&rollId=' + rollId,
//			params:{
//				idStr:idStr,
//				rollId:rollId
//			},			
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			method:'POST',
			success : function() {

				Ext.Msg.alert('提示', '操作成功');
				custStore.reload({
					params : {
						start : 0,
						planId : document.getElementById('planIdStr').value
					}
				});
			},
//			failure : function(response) {
//				var resultArray = Ext.util.JSON.decode(response.status);
//				if(resultArray == 403) {
//				window.location = basepath + '/403.jsp';
//				} else {
//				Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//				}
//			}
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}else{
					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
					cusstore.reload({
						params : {
							start : 0,
							rollId : document.getElementById('planIdStr').value,
							planId : document.getElementById('planIdStr').value,
							limit : parseInt(cuspagesize_combo.getValue())
						}
					});
				}
			}

		});
		
	}
};

//定义名单关联客户维护页面
var custDefendWindow=new Ext.Window(
		{
			   layout : 'fit',
				width:700,
				height :420,
				closable : true,
				resizable : false,
				collapsible : false,
				draggable : true,
				maximizable:true,
				closeAction : 'hide',
				title : '添加客户',
				buttonAlign:'center',
				modal : true, // 模态窗口 
				//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
				animCollapse : false,
				border : false,
				closable : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [
			         {
					layout : 'column',
					border : false,
					items : [
					        {
						columnWidth : .99,
						layout : 'form',
						border : false
						,
						items : [addCustomer,cusGrid]}//, 
//						{
//							columnWidth : .55,
//							layout : 'form',
//							border : false,
//							items : [cusGroupMemeberGrid]
//						}
						]
				}
				],
				
				buttonAlign:'center',
				
				buttons:[{
			  			text: '关闭',
			  			handler:function(){
			  			custDefendWindow.hide();
					}
	 				}]	
});
//定义名单关联客户维护页面
var insertExecuteWindow = new Ext.Window(
		{
			   layout : 'fit',
				width:500,
				height :420,
				closable : true,
				resizable : false,
				collapsible : false,
				draggable : true,
				maximizable:true,
				closeAction : 'hide',
				title : '指定执行团队',
				buttonAlign:'center',
				modal : true, // 模态窗口 
				//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
				animCollapse : false,
				border : false,
				closable : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [
			         {
					layout : 'column',
					border : false,
					items : [
					        {
						columnWidth : .99,
						layout : 'form',
						border : false
						,
						items : [addExecutor,ExeGrid]}
						//items : [addCustomer,cusGrid]}//, 
//						{
//							columnWidth : .55,
//							layout : 'form',
//							border : false,
//							items : [cusGroupMemeberGrid]
//						}
						]
				}
				],
				
				buttonAlign:'center',
				
				buttons:[{
			  			text: '关闭',
			  			handler:function(){
			  			insertExecuteWindow.hide();
					}
	 				}]	
});
//add by huangyan end


var custColumns = new Ext.grid.ColumnModel([rownum,sm,
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
}]
);
var custRecord2 = Ext.data.Record.create([ {
	name : 'id',
	mapping : 'PCDE_ID'
}, {
	name : 'customerId',
	mapping : 'CUST_ID'
}, {
	name : 'customerName',
	mapping : 'CUST_ZH_NAME'
},{
	name: 'institutionName',
	mapping : 'INSTITUTION_NAME'
},{
	name: 'mgrName',
	mapping : 'MGR_NAME'
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
		url : basepath + '/rollCustCanAddQuery1.json',
		success : function(response) {
		}
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'CUST_ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, custRecord2)
});

var add1Customer = new Ext.FormPanel({
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
			items : [ {
				xtype : 'textfield',
				fieldLabel : '客户编号',
				name : 'cust_id',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
		    var conditionStr = add1Customer.getForm().getValues(false);
		    custStore.baseParams = {
                    "condition" : Ext.encode(conditionStr)
                  //  planId : document.getElementById('marketActivityIdStr').value
                };
			custStore.reload({
				params : {
					start : 0,
					limit : cus1bbar.pageSize
				}
			});
		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			add1Customer.getForm().reset();
		}

	} ]
});

var cus1pagesize_combo = new Ext.form.ComboBox({
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

var cus1number = parseInt(cus1pagesize_combo.getValue());

/*custStore.on('beforeload', function() {
	   var conditionStr =  add1Customer.getForm().getValues(false);
this.baseParams = {
      "condition":Ext.encode(conditionStr),
      planId : document.getElementById('marketActivityIdStr').value
};});*/


cus1pagesize_combo.on("select", function(comboBox) {
	cus1bbar.pageSize = parseInt(cus1pagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	custStore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('marketActivityIdStr').value,
			planId : document.getElementById('marketActivityIdStr').value,
			limit : cus1bbar.pageSize
		}
	});
});


var cus1bbar = new Ext.PagingToolbar({
	pageSize : cus1number,
	store : custStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', cus1pagesize_combo ]
});
var custListPanel = new Ext.grid.GridPanel({
	height : 315,
	store : custStore,
	frame : true,
	stripeRows : true,
	autoScroll : true,
	sm : sm, // 复选框
	cm : custColumns,
	stripeRows : true,
	bbar : cus1bbar,
	tbar : [ {
		text : '新增',
		handler : function() {
			addCustInit(); //新增时弹出的窗口
		}
	},  '-', {
		text : '删除',
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
							debugger;
							tempId = selectRe.data.id;
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
								var resultArray = Ext.util.JSON.decode(response.status);
								if(resultArray == 403) {
							           Ext.Msg.alert('提示', response.responseText);
							  } else{

								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								custStore.reload();
							  }
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
				fieldLabel : '客户编号',
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
				fieldLabel : '执行团队',
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
	//items : [ addCustForm ] huangyan
	items : [ cusGroupMemeberGrid ]
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
	custDefendWindow.show();

}

function insertExecute(idStr){
	insertExecuteWindow.show();
}