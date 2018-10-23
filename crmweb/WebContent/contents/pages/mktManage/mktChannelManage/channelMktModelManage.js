Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var modelTypeStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		sortInfo: {
		    field: 'key',
		    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=MODEL_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	
	var qForm = new Ext.form.FormPanel({
		title : "营销模板查询",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 90,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .3,
				layout : 'form',
				items : [ {
					fieldLabel : '模板编号',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'MODEL_ID',
					anchor : '90%'
				} ]
			},{
				columnWidth : .3,
				layout : 'form',
				items : [ {
					fieldLabel : '模板名称',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'MODEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			}, {
				columnWidth : .3,
				layout : 'form',
				items : [ {
					store : modelTypeStore,
					xtype : 'combo',
					resizable : true,
					fieldLabel : '模板类型',
					name : 'MODEL_TYPE',
					hiddenName : 'MODEL_TYPE',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					width : '100',
					anchor : '90%'
				} ]
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {debugger;
				var conditionStr = qForm.getForm().getValues(false);
				modelStore.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				modelStore.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
				
					
			}
		}, {
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
			}
		} ]
	});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var record = Ext.data.Record.create([ {
		name : 'id',
		mapping : 'ID'
	}, {
		name : 'modelId',
		mapping : 'MODEL_ID'
	}, {
		name : 'modelName',
		mapping : 'MODEL_NAME'
	}, {
		name : 'modelContent',
		mapping : 'MODEL_CONTENT'
	}, {
		name : 'modelType',
		mapping : 'MODEL_TYPE'
	}, {
		name : 'modelTitle',
		mapping : 'MODEL_TITLE'
	},
    {name: 'MODEL_TYPE_ORA'}
]);
	
	// 定义列模型
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([ rownum,sm,
	    {
		header : 'ID',
		width : 100,
		align : 'center',
		dataIndex : 'id',
		hidden:true
	},
        {
		header : '模板编号',
		width : 100,
		align : 'center',
		dataIndex : 'modelId',
		sortable : true
	}, {
		header : '模板名称',
		width : 150,
		align : 'center',
		dataIndex : 'modelName',
		sortable : true
	}, {
		header : '模板类型',
		width : 100,
		align : 'center',
		dataIndex : 'MODEL_TYPE_ORA',
		sortable : true
	}, {
		header : '模板内容',
		width : 600,
		align : 'center',
		dataIndex : 'modelContent',
		sortable : true
	}, {
		header : '模板标题',
		width : 300,
		align : 'center',
		dataIndex : 'modelTitle',
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var modelStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/mktModelManage.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record)
	});

	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
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
	modelStore.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), modelStore
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : modelStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	var tbar = new Ext.Toolbar(
			{
				items : [{
						text : '新增',
						iconCls:'addIconCss',
						handler : function() {
							addModelInit();
						}
					},'-',{
						text : '修改',
						iconCls:'resetIconCss',
						handler : function() {
							var selectLength = grid.getSelectionModel().getSelections().length;
							var selectRe = grid.getSelectionModel().getSelections()[0];
							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
							}else {
								editModelForm.getForm().loadRecord(selectRe);
								
								if ('2' == selectRe.data.modelType) {
									editModelForm.findById('modelTitleEdit').show();
									editModelForm.doLayout();
								} else {
									editModelForm.findById('modelTitleEdit').hide();
									editModelForm.doLayout();
								}
								editModelInit();
							}
						}
					},'-',{
						text : '删除',
						iconCls:'deleteIconCss',
						handler : function() 
						{
							 var selectLength = grid.getSelectionModel().getSelections().length;
							 var selectRe;
							 var tempId;
							 var idStr = '';
							if(selectLength < 1){
								Ext.Msg.alert('提示','请选择需要删除的记录!');
							} else {
								for(var i = 0; i<selectLength;i++)
								{
									selectRe = grid.getSelectionModel().getSelections()[i];
									tempId = selectRe.data.id;
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
													+ '/channelModelManage!batchDestroy.json?idStr='+ idStr,//
													//method : 'DELETE',
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : function() {
														Ext.Msg.alert('提示', '操作成功');
														modelStore.reload();
													},
													failure : function(response) {
														var resultArray = Ext.util.JSON.decode(response.status);
														if(resultArray == 403) {
													           Ext.Msg.alert('提示', response.responseText);
													  } else {

														Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
														modelStore.reload();
													  }
													}
												});
									});
							}
						}
					}]
			});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
		title : '营销模板列表',
		width:700,
		height:315,
		frame : true,
		autoScroll : true,
		region : 'center',
		store : modelStore,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm,
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	// 新增渠道展示的from
	var addModelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
//		id:'allForms',
		labelAlign : 'right',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelId',
					xtype : 'textfield',
					fieldLabel : '*模板编号',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelName',
					xtype : 'textfield',
					fieldLabel : '*模板名称',
					allowBlank : false,
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [{
				layout : 'form',
				columnWidth : .9,
				items : [{
					store : modelTypeStore,
					xtype : 'combo',
					resizable : true,
					fieldLabel : '*模板类型',
					name : 'modelType',
					hiddenName : 'modelType',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,
//					selectOnFocus : true,
					width : '100',
					anchor : '90%',
					listeners : {
						'select' : function(combo, record, index) {
							if (record.get('key') == '2') {
								addModelForm.findById('modelTitleNew').show();
								addModelForm.doLayout();
							} else {
								addModelForm.findById('modelTitleNew').hide();
								addModelForm.doLayout();
							}
						}
					}
				}]
			}]
		},{
			layout : 'column',
			items : [{
				layout : 'form',
				columnWidth : .9,
				items : [ {
					name : 'modelTitle',
					id : 'modelTitleNew',
					xtype : 'textfield',
					fieldLabel : '模板标题',
					maxLength:200,
					anchor : '90%',
					hidden : true
				}]
			}]
		},{
			layout : 'column',
			items : [{
				layout : 'form',
				columnWidth : .9,
				items : [ {
					name : 'modelContent',
					xtype : 'textarea',
					fieldLabel : '*模板内容',
					maxLength: 512,
					allowBlank : false,
					anchor : '90%'
				}]
			}]
		}],
		
			buttons : [{
				text : '保存',
				id:'baocun_id',
				//disabled:JsContext.checkGrant('baocun_id'),
				handler : function() {
            		if(!addModelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}
					Ext.Ajax.request({
						url : basepath + '/channelModelManage!create.json',
						method : 'POST',
						form : addModelForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							modelStore.reload();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
							modelStore.reload();
						}
					});
					addModelWindow.hide();
					addModelForm.getForm().reset();
				}
			}, {
				text : '取  消',
				handler : function() {
					addModelWindow.hide();
				}
			}
		]
	});
	
	// 修改基本信息展示的from
	var editModelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
//		id:'allForms',
		labelAlign : 'right',
		//region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'id',
					xtype : 'textfield',
					fieldLabel : 'ID',
					hidden:true
				},{
					name : 'modelId',
					xtype : 'textfield',
					fieldLabel : '*模板编号',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelName',
					xtype : 'textfield',
					fieldLabel : '*模板名称',
					maxLength:100,
					allowBlank : false,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					store : modelTypeStore,
					xtype : 'combo',
					resizable : true,
					fieldLabel : '*模板类型',
					name : 'modelType',
					hiddenName : 'modelType',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					allowBlank : false,
					triggerAction : 'all',
					emptyText : '请选择',
//					selectOnFocus : true,
					width : '100',
					anchor : '90%',
					listeners : {
						'select' : function(combo, record, index) {
							if (record.get('key') == '2') {
								editModelForm.findById('modelTitleEdit').show();
								editModelForm.doLayout();
							} else {
								editModelForm.findById('modelTitleEdit').hide();
								editModelForm.doLayout();
							}
						}
					}
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelTitle',
					id : 'modelTitleEdit',
					xtype : 'textfield',
					fieldLabel : '模板标题',
					maxLength:200,
					anchor : '90%',
					hidden : true
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelContent',
					xtype : 'textarea',
					fieldLabel : '*模板内容',
					maxLength:512,
					allowBlank : false,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [
			{
				text : '保  存',
				handler : function() {
					if(!editModelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}	
					Ext.Ajax.request({
						url : basepath + '/channelModelManage!create.json',
						method : 'POST',
						form : editModelForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							modelStore.reload();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						  }
							modelStore.reload();
						}
					});
					editModelWindow.hide();
				}
			}, {
				text : '取  消',
				handler : function() {
					editModelWindow.hide();
				}
			} ]
	});
	
	// 新增渠道的from
	var addModelPanel = new Ext.Panel({
		width : 600,
		height : 300,
		layout : 'fit',
		buttonAlign : "center",
		items : [ addModelForm]
	});
	
	// 修改渠道的from
	var editModelPanel = new Ext.Panel({
		labelWidth : 150,
		height : 300,
		layout : 'fit',
		buttonAlign : "center",
		items : [ editModelForm ]
	});

	// 展示新增的窗口
	function addModelInit() {
		addModelForm.getForm().reset();
		addModelWindow.show();
	}
	
	// 展示修改窗口
	function editModelInit() {
		
		editModelWindow.show();
	}
	
	// 定义新增窗口
	var addModelWindow = new Ext.Window({
		title : '营销模板新增',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 350,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ addModelPanel ]
	});
	
	// 定义修改窗口
	var editModelWindow = new Ext.Window({
		title : '营销模板修改',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 350,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ editModelPanel ]
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [ {
			layout : 'border',
			items : [ qForm, grid ]
		} ]
	});
});