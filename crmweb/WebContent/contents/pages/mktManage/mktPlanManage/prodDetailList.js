// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

/**
* 数据存储
*/
var prostore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
	url : basepath + '/marketPlanProductAddQuery.json',
//    success : function(response) {
//    var resultArray = Ext.util.JSON.decode(response.responseText);
//  	Ext.Msg.alert('提示', response.responseText);
//    },
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
	}, [ 'CUST_ID', 'PRODUCT_ID', 'PROD_NAME', 'CUST_TYP', 'CUST_LEV' ])
});

//改变每页显示条数reload数据
var propagesize_combo = new Ext.form.ComboBox({
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

propagesize_combo.on("select", function(comboBox) {
	probbar.pageSize = parseInt(propagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	prostore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('planIdStr').value,
			limit : probbar.pageSize
		}
	});
});

//分页工具栏
var probbar = new Ext.PagingToolbar({
	pageSize : parseInt(propagesize_combo.getValue()),
	store : prostore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', propagesize_combo ]
});

//新增名单关联客户的表格面板
var custbar = new Ext.Toolbar({
	items : [ {
		text : '添加产品',
		handler : function() {
			batchProAdd();
		}
	} ]
});

var proGroupcombo = new Ext.form.ComboBox({
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

prostore.on('beforeload', function() {
	   var conditionStr =  addProduct.getForm().getValues(false);
  this.baseParams = {
         "condition":Ext.encode(conditionStr)
 };});


var batchProAdd = function() {
	var selectLength = proGrid.getSelectionModel().getSelections().length;

	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要加入计划的 产品!');
	} else {
		var selectRe;
		var tempId;
		var idStr = '';
		for ( var i = 0; i < selectLength; i++) {
			selectRe = proGrid.getSelectionModel().getSelections()[i];
			tempId = selectRe.data.PRODUCT_ID;
			idStr += "'", idStr += tempId;
			idStr += "'";
			if (i != selectLength - 1)
				idStr += ",";
		}
		
		var rollId = document.getElementById("planIdStr").value;
		
		memberForm.getForm().findField("idStr").setValue(idStr);
		
		memberForm.getForm().findField("rollIdTemp").setValue(rollId);
		
		Ext.Ajax.request({
			url : basepath + '/plan-product-batch!batchCreate.json',
			params:{
				idStr:idStr,
				rollId:rollId
			},			
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			method:'POST',
			success : function() {

				Ext.Msg.alert('提示', '操作成功');
				prostore.reload({
					params : {
						planId : document.getElementById('planIdStr').value
					}
				});
				prodStore.load({
					params : {
						planId : document.getElementById('planIdStr').value
					}
				});
			},
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
							limit : parseInt(cuspagesize_combo.getValue()),
							planId : document.getElementById('planIdStr').value
						}
					});
				}
			}

		});
	}
};

//产品列模式
var prosm = new Ext.grid.CheckboxSelectionModel();
var prorownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
var procm = new Ext.grid.ColumnModel([ prorownum, prosm, {
	header : 'cust_id',
	dataIndex : 'CUST_ID',
	sortable : true,
	width : 150,
	hidden : true
}, {
	header : '产品编号',
	dataIndex : 'PRODUCT_ID',
	sortable : true,
	width : 150
}, {
	header : '产品名称',
	dataIndex : 'PROD_NAME',
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

var addProduct = new Ext.FormPanel({
	// layout:'fit',
	title : '产品查询',
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
				fieldLabel : '产品名称',
				name : 'PROD_NAME',
				anchor : '95%'
			} ]
		}, {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '产品编号',
				name : 'PROD_ID',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			var planId=Ext.getCmp('listPanel').getSelectionModel().getSelected().get('planId');
			prostore.reload({
				params : {
					start : 0,
					rollId : document.getElementById('planIdStr').value,
					limit : probbar.pageSize,
					planId : planId
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			addProduct.getForm().reset();
		}

	} ]
});



//产品查询 


var prodColumns = new Ext.grid.ColumnModel([ rownum, sm, {
	header : '产品编号',
	width : 120,
	align : 'left',
	dataIndex : 'productId',
	sortable : true
}, {
	header : '产品名称',
	width : 120,
	align : 'left',
	dataIndex : 'productName',
	sortable : true
},{
    header : '产品类型',
    width : 120,
    align : 'left',
    dataIndex : 'productType',
    sortable : true
}
//, {
//	header : '创建人',
//	width : 120,
//	align : 'left',
//	dataIndex : 'createUser',
//	hidden:true,
//	sortable : true
//}, {
//	header : '创建日期',
//	width : 120,
//	align : 'left',
//	dataIndex : 'createDate',
//	hidden:true,
//	sortable : true
//} 
]);

var prodRecord = Ext.data.Record.create([ {
    name : 'productDetailId',mapping : 'PPDE_ID'
},{
    name : 'productId',mapping : 'PRODUCT_ID'
},{
    name : 'productName',mapping : 'PRODUCT_NAME'
},{
    name : 'productType',mapping : 'PRODUCT_TYPE'    	
}, {
    name : 'createUser',mapping : 'USERNAME'
}, {
    name : 'createDate',mapping : 'CREATE_DATE'
},{
    name : 'planId',mapping : 'PLAN_ID'
}]);

var prodStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planProductQuery.json',
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
	}, prodRecord)
});

var add1Product = new Ext.FormPanel({
	// layout:'fit',
	title : '产品查询',
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
				fieldLabel : '产品名称',
				name : 'PRODUCT_NAME',
				anchor : '95%'
			} ]
		}, {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [  {
				xtype : 'textfield',
				fieldLabel : '产品编号',
				name : 'PRODUCT_ID',
				anchor : '95%'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			var planId=Ext.getCmp('listPanel').getSelectionModel().getSelected().get('planId');
			prodStore.reload({
				params : {
					start : 0,
					rollId : document.getElementById('planIdStr').value,
					limit : pro1bbar.pageSize,
					planId : planId
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			add1Product.getForm().reset();
		}
	} ]
});

var pro1pagesize_combo = new Ext.form.ComboBox({
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

var pro1number = parseInt(pro1pagesize_combo.getValue());

prodStore.on('beforeload', function() {
	   var conditionStr =  add1Product.getForm().getValues(false);
this.baseParams = {
      "condition":Ext.encode(conditionStr),
      planId : document.getElementById('planIdStr').value
};});

pro1pagesize_combo.on("select", function(comboBox) {
	pro1bbar.pageSize = parseInt(pro1pagesize_combo.getValue());
	prodStore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('planIdStr').value,
			planId : document.getElementById('planIdStr').value,
			limit : pro1bbar.pageSize
		}
	});
});

var pro1bbar = new Ext.PagingToolbar({
	pageSize : pro1number,
	store : prodStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', pro1pagesize_combo ]
});

var prodListPanel = new Ext.grid.GridPanel({
	height : 315,
	store : prodStore,
	sm : sm,
	frame : true,
	cm : prodColumns,
	stripeRows : true,
	bbar : pro1bbar,
	tbar : [
			{
				text : '新增',
				handler : function() {
					addProdInit();
				}
			},
			'-',
			{
				text : '删除',
				handler : function() {
					var selectLength = prodListPanel.getSelectionModel()
							.getSelections().length;

					if (selectLength < 1) {
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
							for ( var i = 0; i < selectLength; i++) {
								selectRe = prodListPanel.getSelectionModel()
										.getSelections()[i];
								tempId = selectRe.data.productDetailId;
								idStr += tempId;
								if (i != selectLength - 1)
									idStr += ',';
							}
							Ext.Ajax.request({
								url : basepath + '/plan-product/' + tempId
										+ '/batchDestroy.json?idStr=' + idStr,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									prodStore.reload();
								},
								failure : function(response) {
									 if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
								  } else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									prodStore.reload();
								}
								}
							});

						})
						;
					}
				}
			} ]
});

// 新增窗口展示的from
var addProdForm = new Ext.form.FormPanel({
	labelWidth : 150,
	height : 300,
	frame : true,
	region : 'center',
	autoScroll : true,
	buttonAlign : 'center',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .3,
			layout : 'form',
			items : [ {
				name : 'productId',
				xtype : 'textfield',
				fieldLabel : '产品ID',
				width : '100',
				anchor : '90%'
			}, {
				id : 'currPlanId',
				name : 'planId',
				xtype : 'hidden',
				fieldLabel : '营销计划ID',
				width : '100',
				anchor : '90%'
			} ]

		}, {
			columnWidth : .3,
			layout : 'form',
			items : [ {
				name : 'productName',
				xtype : 'textfield',
				fieldLabel : '产品名称',
				width : '100',
				anchor : '90%'
			} ]
		},{
			columnWidth : .3,
			layout : 'form',
			items : [ {
			name : 'productType',
		    fieldLabel : '产品类型',
		    width : 100,
		    anchor : '90%'
			} ]
		} ]

	} ],

	buttons : [

			{

				text : '保  存',
				handler : function() {
					document.getElementById('currPlanId').value = document
							.getElementById('planIdStr').value;
					Ext.Ajax.request({
						url : basepath + '/plan-product.json',
						method : 'POST',
						form : addProdForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							prodStore.reload();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							prodStore.reload();
						}
					});
					addProdWindow.hide();
				}

			}, {
				text : '取  消',
				handler : function() {
					addProdWindow.hide();
				}
			} ]

});

var proGrid = new Ext.grid.GridPanel({
	height : 260,
	frame : true,
	autoScroll : true,
	store : prostore, // 数据存储
	stripeRows : true, // 斑马线
	cm : procm, // 列模型
	sm : prosm, // 复选框
	bbar : probbar,
	tbar : custbar,
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var addProdWindow = new Ext.Window({
	layout : 'fit',
	width:700,
	height :420,
	closable : true,
	resizable : false,
	collapsible : false,
	draggable : true,
	maximizable:true,
	closeAction : 'hide',
	title : '添加产品',
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
		items : [{
			columnWidth : .99,
			layout : 'form',
			border : false,
			items : [addProduct,proGrid]}//, 
//			{
//				columnWidth : .55,addProduct,,proGrid
//				layout : 'form',
//				border : false,
//				items : [cusGroupMemeberGrid]
//			}
			]
	}
	],
	
	buttonAlign:'center',
	
	buttons:[{
			text: '关闭',
			handler:function(){
			addProdWindow.hide();
		}
		}]	
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
function addProdInit() {
	addProdWindow.show();

}
// // 展示修改窗口
// function editInit() {
// editPlanWindow.show();
// }
//
