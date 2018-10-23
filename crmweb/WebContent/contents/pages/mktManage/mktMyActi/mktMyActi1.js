var StageStore1 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '执行中' ], [ 2, '成功完成' ],
			[ 3, '失败完成' ] ]
});




var editQForm = new Ext.form.FormPanel({
	labelWidth : 90, // 标签宽度
	frame : true, // 是否渲染表单面板背景色
	labelAlign : 'middle', // 标签对齐方式
	buttonAlign : 'center',
	border : false,
	height : 100,
	items :[ {
		layout : 'column',
		items : [ {
			columnWidth : .50,
			layout : 'form',
			items : [ {
				fieldLabel : '活动日期',
				format : 'Y-m-d',
				xtype : 'datefield',
				editable:false,
				name : 'ACTI_DATE',
				anchor : '90%'
				
			} ]
		},{
			columnWidth : .50,
			layout : 'form',
			items : [ new Ext.form.ComboBox({
    			hiddenName : 'PROGRESS_STAGE',
    			fieldLabel : '进展阶段',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : StageStore1,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		})
			]
		}]
	}],
	buttons : [ {
		text : '查询',
		handler : function() {
			var conditionStr = editQForm.getForm().getValues(false);
			editStore.baseParams = {
				"condition" : Ext.encode(conditionStr)
			};
			editStore.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo1.getValue())
				}
			});
		}
	}, {
		text : '重置',
		handler : function() {
			editQForm.getForm().reset();
		}
	} ]
});
//定义自动当前页行号
var rownum1 = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var record = Ext.data.Record.create([ {
	name : 'recordId',
	mapping : 'RECORD_ID'
}, 
 {
	name : 'mktActiId',
	mapping : 'MKT_ACTI_ID'
}, {
	name : 'actiDate',
	mapping : 'ACTI_DATE'
}, {
	name : 'actiCont',
	mapping : 'ACTI_CONT'
}, {
	name : 'executorName',
	mapping : 'EXECUTOR_NAME'
},{
	name : 'executorId',
	mapping : 'EXECUTOR_ID'
}, {
	name : 'custName',
	mapping : 'CUST_NAME'
}, {
	name : 'actiResult',
	mapping : 'ACTI_RESULT'
},{
	name : 'followEvent',
	mapping:'FOLLOW_EVENT'
},{
	name : 'PROGRESS_STAGE_ORA'
},{
	name : 'progressStage',
	mapping:'PROGRESS_STAGE'
},{
	name : 'createUser',
	mapping:'CREATE_USER'
},{
	name : 'createUserName',
	mapping:'USER_NAME'
},{
	name : 'createDate',
	mapping:'CREATE_DATE'
}]);


var sm1 = new Ext.grid.CheckboxSelectionModel();
var rownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

	
// 定义列模型

var cm1 = new Ext.grid.ColumnModel([ rownum1,sm1 ,{
	header : 'id',
	width : 100,
	align : 'center',
	hidden:true,
	dataIndex : 'recordId',
	sortable : true
},{
	header : '营销活动id',
	width : 100,
	align : 'center',
	hidden:true,
	dataIndex : 'mktActiId',
	sortable : true
},{
	header : '活动日期',
	width : 100,
	align : 'center',
	dataIndex : 'actiDate',
	sortable : true
}, {
	header : '活动内容',
	width : 150,
	align : 'center',
	dataIndex : 'actiCont',
	sortable : true
},{
	header : '活动执行人',
	width : 150,
	align : 'center',
	dataIndex : 'executorName',
	sortable : true
} ,{
	header : '活动执行人',
	width : 150,
	align : 'center',
	hidden:true,
	dataIndex : 'executorId',
	sortable : true
} ,{
	header : '客户名称',
	width : 150,
	align : 'center',
	dataIndex : 'custName',
	sortable : true
},{
	header : '活动结果',
	width : 150,
	align : 'center',
	dataIndex : 'actiResult',
	sortable : true
},{
	header : '待跟进事项',
	width : 150,
	align : 'center',
	dataIndex : 'followEvent',
	sortable : true
},{
	header : '进展阶段',
	width : 150,
	align : 'center',
	dataIndex : 'PROGRESS_STAGE_ORA',
	sortable : true
	
},{
	header : '进展阶段',
	width : 150,
	align : 'center',
	hidden:true,
	dataIndex : 'progressStage',
	sortable : true
	
},{
	header : '创建人Id',
	width : 150,
	align : 'center',
	dataIndex : 'createUser',
	hidden:true,
	sortable : true
	
},{
	header : '创建人',
	width : 150,
	align : 'center',
	dataIndex : 'createUserName',
	sortable : true
	
},{
	header : '创建日期',
	width : 150,
	align : 'center',
	dataIndex : 'createDate',
	sortable : true
	
}]);
/**
 * 数据存储
 */

var editStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/mktMyActiDetailManage.json'
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'ID',
		totalProperty : 'json.count',
		root:'json.data'
	}, record)
});

// 每页显示条数下拉选择框
var pagesize_combo1 = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
				[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	editable : false,
	width : 85
});


// 默认加载数据
editStore.load({
	params : {
		start : 0,
		limit : parseInt(pagesize_combo1.getValue())
	}
});

// 改变每页显示条数reload数据
pagesize_combo1.on("select", function(comboBox) {
	bbar1.pageSize = parseInt(pagesize_combo1.getValue()), editStore
			.reload({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo1.getValue())
				}
			});
});
// 分页工具栏
var bbar1 = new Ext.PagingToolbar({
	pageSize : parseInt(pagesize_combo1.getValue()),
	store : editStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', pagesize_combo1 ]
});

var ActiDetailForm = new Ext.form.FormPanel({
		 height : 430,
		 region : 'center',
		 labelWidth : 100,
		 frame : true,
		 autoScroll : true,
		 labelAlign : 'right',
		 buttonAlign : "center",
		 items : [{
				layout : 'column',
				items : [{
						columnWidth : .5,
						layout : 'form',
						items : [ {
						xtype : 'datefield',
						fieldLabel : '<font color=red>*</font>活动时间',
						format : 'Y-m-d',
						editable : true,
						allowBlank:false,
						name : 'actiDate',
						anchor : '90%'
					},{
						xtype : 'textfield',
						fieldLabel : '活动执行人员编号',
						name : 'executorId',
						readOnly:true,
						editable : false,
						anchor : '90%'
					}, new Ext.form.ComboBox({
		    			hiddenName : 'progressStage',
		    			fieldLabel : '<font color=red>*</font>进展阶段',
		    			labelStyle: 'text-align:right;',
		    			triggerAction : 'all',
		    			store : StageStore1,
		    			displayField : 'value',
		    			valueField : 'key',
		    			mode : 'local',
		    			forceSelection : true,
		    			allowBlank:false,
		    			emptyText:'请选择 ',
		    			resizable : true,
		    			anchor : '90%'
		    		})
					]},
					{
						columnWidth : .5,
						layout : 'form',
						items : [{ 
						xtype : 'textfield',
						fieldLabel : '客户名称',
						readOnly:true,
						editable : false,
						name : 'custName',
						anchor : '90%'
					},{
						xtype : 'textfield',
						fieldLabel : '活动执行人员名称',
						name : 'executorName',
						readOnly:true,
						editable : false,
						anchor : '90%'
					},{ 
						xtype : 'textfield',
						fieldLabel : '客户Id',
						name : 'custId',
						hidden:'true',
						readOnly:true,
						editable : false,
						anchor : '90%'
					}
					]
				}]
			},
			{
				layout : 'form',
				buttonAlign : 'center',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '活动内容',
					name : 'actiCont',
					anchor : '90%'
				}, {
						xtype : 'textarea',
						fieldLabel : '活动结果',
						name : 'actiResult',
						anchor : '90%'
					},
					{
							xtype : 'textarea',
							fieldLabel : '待跟进事项',
							name : 'followEvent',
							anchor : '90%'
					}]
			} ]

});


var editActiDetailForm = new Ext.form.FormPanel({
	 height : 430,
	 region : 'center',
	 labelWidth : 100,
	 frame : true,
	 autoScroll : true,
	 labelAlign : 'right',
	 buttonAlign : "center",
	 items : [{
			layout : 'column',
			items : [{
					columnWidth : .5,
					layout : 'form',
					items : [
					 {xtype : 'textfield',
						fieldLabel : '明细编号',
						readOnly:true,
						editable : false,
						name : 'recordId',
						anchor : '90%'
					} ,{
					xtype : 'datefield',
					fieldLabel : '活动时间',
					format : 'Y-m-d',
					readOnly:true,
					editable : false,
					name : 'actiDate',
					anchor : '90%'
				},{
					xtype : 'textfield',
					fieldLabel : '活动执行人员编号',
					name : 'executorId',
					readOnly:true,
					editable : false,
					anchor : '90%'
				}, new Ext.form.ComboBox({
	    			hiddenName : 'progressStage',
	    			fieldLabel : '进展阶段',
	    			labelStyle: 'text-align:right;',
	    			triggerAction : 'all',
	    			store : StageStore1,
	    			displayField : 'value',
	    			valueField : 'key',
	    			mode : 'local',
	    			forceSelection : true,
	    			emptyText:'请选择 ',
	    			resizable : true,
	    			anchor : '90%'
				})
				,{
					xtype : 'textfield',
					fieldLabel : '创建人',
					name : 'createUser',
					hidden:'true',
					anchor : '90%'
				}
				]},
				{
					columnWidth : .5,
					layout : 'form',
					items : [
					{xtype : 'textfield',
						fieldLabel : '营销活动编号',
						readOnly:true,
						editable : false,
						name : 'mktActiId',
						anchor : '90%'
					} ,{ 
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'custName',
					readOnly:true,
					editable : false,
					anchor : '90%'
				},{
					xtype : 'textfield',
					fieldLabel : '活动执行人员名称',
					name : 'executorName',
					readOnly:true,
					editable : false,
					anchor : '90%'
				}
				,{
					xtype : 'datefield',
					fieldLabel : '创建日期',
					format : 'Y-m-d',
					readOnly:true,
					editable : false,
					name : 'createDate',
					anchor : '90%'
				},{ 
					xtype : 'textfield',
					fieldLabel : '客户Id',
					name : 'custId',
					hidden:'true',
					readOnly:true,
					editable : false,
					anchor : '90%'
				}
				
				]
				}]
			},
			{
				layout : 'form',
			buttonAlign : 'center',
			items : [ {
				xtype : 'textarea',
				fieldLabel : '活动内容',
				name : 'actiCont',
				anchor : '90%'
			}, {
					xtype : 'textarea',
					fieldLabel : '活动结果',
					name : 'actiResult',
					anchor : '90%'
				},
				{
						xtype : 'textarea',
						fieldLabel : '待跟进事项',
						name : 'followEvent',
						anchor : '90%'
				}]
			} ]

});
var addActiDetailWind = new Ext.Window({//新增的window
	closeAction:'hide',
	height:'430',
	width:'700',
	modal : true,//遮罩
	buttonAlign:'center',
	layout:'fit',
	items:[ActiDetailForm],
	buttons:[
	         {
	        	 text:'保存',
	        	 handler: function(){
	        	 if (!ActiDetailForm.getForm().isValid()) {
	        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	        		 return false;
	        	 }
	        	
	        	 Ext.Ajax.request({
						url : basepath + '/mktMyActiDetailManage!saveData.json',
						params : {
						'operate':'add',
						'myActiId':document.getElementById('myActiIdStr').value
						},
						method : 'POST',
						form : ActiDetailForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功!');
							editStore.reload( {
                                 params : {
                                     start : 0,
                                     limit : bbar1.pageSize
                                 }
                             });
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{

							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
						}
					});
	        	 addActiDetailWind.hide();
	         }
	         },
	         {
	        	 text:'重置',
	        	 handler:function(){
	        		 ActiDetailForm.getForm().reset();
	         	}
	         }
	        ]
});

var editActiDetailWin = new Ext.Window({//修改的window
	closeAction:'hide',
	height:'430',
	width:'700',
	modal : true,//遮罩
	buttonAlign:'center',
	layout:'fit',
	items:[editActiDetailForm],
	buttons:[
	         {
	        	 text:'保存',
	        	 handler: function(){
	        	 if (!editActiDetailForm.getForm().isValid()) {
	        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	        		 return false;
	        	 }
	        	
	        	 Ext.Ajax.request({
						url : basepath + '/mktMyActiDetailManage!saveData.json',
						params : {
						'operate':'update'
						},
						method : 'POST',
						form : editActiDetailForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功!');
							editStore.reload( {
                                 params : {
                                     start : 0,
                                     limit : bbar1.pageSize
                                 }
                             });
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{

							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
						}
					});
	        	 editActiDetailWin.hide();
	         }
	         },
	         {
	        	 text:'重置',
	        	 handler:function(){
	        		 editActiDetailForm.getForm().reset();
	         	}
	         }
	        ]
});
var tbar1 = new Ext.Toolbar(
		{
			items : [{
					text : '新增',
					iconCls:'addIconCss ',
					handler : function() {
						addActiDetailWind.show();
			    	  	addActiDetailWind.setTitle('活动明细新增');
			    	  	ActiDetailForm.getForm().getEl().dom.reset();
			    	  	ActiDetailForm.form.findField('custName').setValue(document.getElementById('custNameStr').value),
			    	  	ActiDetailForm.form.findField('custId').setValue(document.getElementById('custIdStr').value),
			    	  	ActiDetailForm.form.findField('executorId').setValue(document.getElementById('executorIdStr').value),
			    	  	ActiDetailForm.form.findField('executorName').setValue(document.getElementById('executorNameStr').value),
			    	  	editStore.reload();
					}
				},'-',{
					text : '修改',
					iconCls:'editIconCss',
					handler : function() {
						 var _record = editGrid.getSelectionModel().getSelected();
						  var checkedNodes = editGrid.getSelectionModel().selections.items;
						  if (!_record||checkedNodes.length>1) {
							  Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
						  } else {
							  var record = editGrid.getSelectionModel().getSelected();
							  editActiDetailForm.getForm().loadRecord(record);
							  editActiDetailWin.show();
							  editActiDetailWin.setTitle('活动明细修改');
					    	  editStore.reload();
						  }
					}},'-',{
						text : '删除',
						iconCls:'deleteIconCss',
						handler : function() {
							 var selectLength = editGrid.getSelectionModel().getSelections().length;
							 var selectRe;
							 var tempId;
							 var idStr = '';
							if(selectLength < 1){
								Ext.Msg.alert('提示','请选择需要删除的记录!');
							} else {
								for(var i = 0; i<selectLength;i++)
								{
									selectRe = editGrid.getSelectionModel().getSelections()[i];
									tempId = selectRe.data.recordId;
									idStr += tempId;
									if( i != selectLength-1)
										idStr += ',';
								}
									Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
										if(buttonId.toLowerCase() == "no"){
										return;
										} 
										Ext.Ajax.request({
													url : basepath
													+ '/mktMyActiDetailManage!batchDestroy.json?idStr='+ idStr,
													//method : 'DELETE',
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : function() {
														Ext.Msg.alert('提示', '操作成功');
														editStore.reload();
													},
													failure : function(response) {
														var resultArray = Ext.util.JSON.decode(response.status);
														if(resultArray == 403) {
													           Ext.Msg.alert('提示', response.responseText);
													  } else {

														Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
														 editStore.reload();
													  }
													}
												});

									})
									;
								
							}
						}
						}]
		});
//表格实例
var editGrid = new Ext.grid.GridPanel({
	title : '活动明细信息列表',
	frame : true,
	autoScroll : true,
	store : editStore,
	stripeRows : true, // 斑马线
	sm:sm1,
	cm : cm1, // 列模型
	tbar:tbar1,	
//	bbar : bbar1,// 分页工具栏
	viewConfig : {},
	layout : 'fit',
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
