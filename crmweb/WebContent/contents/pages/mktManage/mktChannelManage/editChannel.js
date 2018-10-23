//Ext.onReady(function() {
	Ext.QuickTips.init();
	
	//服务渠道
	var fuqdStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FWQD'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	//渠道宣传模板名称
	
	var channelModelNameStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['1', '手机宣传模板'], ['2', '网上宣传模板'], ['3', '支行宣传模板'],['4', '客户经理宣传模板'],['5', '业务员宣传模板']]
	});
	
	var channelQForm = new Ext.form.FormPanel({
		title : "目标渠道",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		border : false,
		//region : 'north',
		split : true,
		//height : 120,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .50,
				layout : 'form',
				items : [ {
					fieldLabel : '渠道名称',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CHANNEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {debugger;
				var conditionStr = channelQForm.getForm().getValues(false);
//				store.baseParams = {
//					"condition" : Ext.encode(conditionStr)
//				};
//				store.load({
//					params : {
//						start : 0,
//						limit : parseInt(pagesize_combo.getValue())
//					}
//				});
				editChannelStore.loadData(channelData);
			}
		}, {
			text : '重置',
			handler : function() {
			channelQForm.getForm().reset();
			}
		} ]
	});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var record = Ext.data.Record.create([ {
		name : 'channelId',
		mapping : 'CHANNEL_ID'
	}, {
		name : 'channelName',
		mapping : 'CHANNEL_NAME'
	}, {
		name : 'channelDesc',
		mapping : 'CHANNEL_DESC'
	}, {
		name : 'channelFitCustLevel',
		mapping : 'CHANNEL_FIT_CUST_LEVEL'
	}, {
		name : 'channelModelId',
		mapping : 'CHANNEL_MODE_ID'
	}, {
		name : 'channelModelName',
		mapping : 'CHANNEL_MODE_NAME'
	}, {
		name : 'channelModelContent',
		mapping : 'CHANNEL_MODE_CONTENT'
	}, {
		name : 'createUserName',
		mapping : 'CREATE_USER_NAME'
	}, {
		name : 'createDate',
		mapping : 'CREATE_DATE'
	}]);
	
	var channelData = {"json":{"count":3,"data":[
		{"CHANNEL_ID":"00001","CHANNEL_NAME":"邮件","CHANNEL_DESC":"邮件","CHANNEL_FIT_CUST_LEVEL":"1","CHANNEL_MODE_ID":"1","CHANNEL_MODE_NAME":"网上宣传模板","CHANNEL_MODE_CONTENT":"营销内容1","CREATE_USER_NAME":"分行行长","CREATE_DATE":"2012-12-20"},
		{"CHANNEL_ID":"00002","CHANNEL_NAME":"ATM","CHANNEL_DESC":"ATM","CHANNEL_FIT_CUST_LEVEL":"2","CHANNEL_MODE_ID":"1","CHANNEL_MODE_NAME":"手机宣传模板","CHANNEL_MODE_CONTENT":"营销内容2","CREATE_USER_NAME":"行长","CREATE_DATE":"2012-12-10"},
		{"CHANNEL_ID":"00003","CHANNEL_NAME":"短信","CHANNEL_DESC":"短信","CHANNEL_FIT_CUST_LEVEL":"3","CHANNEL_MODE_ID":"1","CHANNEL_MODE_NAME":"网上宣传模板","CHANNEL_MODE_CONTENT":"营销内容3","CREATE_USER_NAME":"分行行长","CREATE_DATE":"2012-12-20"}
		]}};

	// 定义列模型

	var cm = new Ext.grid.ColumnModel([ rownum, {
		header : '渠道编号',
		width : 100,
		align : 'center',
		dataIndex : 'channelId',
		sortable : true
	}, {
		header : '渠道名称',
		width : 150,
		align : 'center',
		dataIndex : 'channelName',
		sortable : true
	}, {
		header : '渠道适用客户级别',
		width : 150,
		align : 'center',
		dataIndex : 'channelFitCustLevel',
		sortable : true
	}, {
		header : '渠道宣传模版编号',
		width : 150,
		align : 'center',
		dataIndex : 'channelModelId',
		sortable : true
	}, {
		header : '渠道宣传模版名称',
		width : 150,
		align : 'center',
		dataIndex : 'channelModelName',
		sortable : true
	}, {
		header : '渠道宣传模版内容',
		width : 150,
		align : 'center',
		dataIndex : 'channelModelContent',
		sortable : true
	}, {
		header : '创建人',
		width : 100,
		align : 'left',
		dataIndex : 'createUserName',
		sortable : true
	}, {
		header : '创建日期',
		width : 100,
		align : 'left',
		dataIndex : 'createDate',
		sortable : true
}]);

	/**
	 * 数据存储
	 */
	var editChannelStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'CHANNEL_ID',
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


	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), editChannelStore
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
		store : editChannelStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	var tbar = new Ext.Toolbar(
			{
				items : [{
						text : '新增',
						handler : function() {
							add2ChannelInit();
						}
					},'-',{
						text : '修改',
						handler : function() {
//						var selectLength = editGrid.getSelectionModel().getSelections().length;
//						var selectRe = editGrid.getSelectionModel().getSelections()[0];
//						if (selectLength != 1) {
//							Ext.Msg.alert("提示", "请选择一条记录!");
//						}else {
//							var actiStatus = selectRe.data.actiStatus;
//								edit2ChannelForm.getForm().loadRecord(selectRe);
//								document.getElementById('channelIdStr').value = selectRe.data.channelId;
								edit2ChannelInit();
//						}
						}
					},'-',{
						text : '删除',
						handler : function() 
						{
						Ext.Msg.alert('提示', '操作成功');
						/*	 
						var selectLength = editChannelGrid.getSelectionModel().getSelections().length;
							 var selectRe;
							 var tempId;
							 var idStr = '';
							 var actiStatus;
							if(selectLength < 1){
								Ext.Msg.alert('提示','请选择需要删除的记录!');
							} else {
								for(var i = 0; i<selectLength;i++)
								{
									selectRe = editChannelGrid.getSelectionModel().getSelections()[i];
									actiStatus = selectRe.data.actiStatus;
									if(actiStatus != '1'){
										Ext.Msg.alert('提示','只能删除创建状态的营销活动!');
										return;
									}
									tempId = selectRe.data.marketActivityId;
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
													+ '/market-activity!batchDestroy.json?idStr='+ idStr,
													//method : 'DELETE',
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : function() {
														Ext.Msg.alert('提示', '操作成功');
														editChannelStore.reload();
													}
												});
									})
									;
							}
							*/
						}
					}]
			});

	// 表格实例
	var editChannelGrid = new Ext.grid.GridPanel({
		title : '目标渠道',
		width:700,
		height:295,
		frame : true,
		autoScroll : true,
		//region : 'center',
		store : editChannelStore,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	// 新增渠道展示的from
	var add2ChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		id:'editNewForms',
		labelAlign : 'right',
		//region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : fuqdStore,
					resizable : true,
					name : 'channelName',
					hiddenName : 'channelName',
					xtype : 'combo',
					fieldLabel : '*渠道名称',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : channelModelNameStore,
					resizable : true,
					name : 'modelName',
					hiddenName : 'modelName',
					xtype : 'combo',
					fieldLabel : '*渠道宣传模版名称',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelName',
					xtype : 'textfield',
					editable:false,
					fieldLabel : '渠道适用客户级别',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'createUserName',
					xtype : 'hidden',
					fieldLabel : '创建人',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'createDate',
					xtype : 'hidden',
					format:'Y-m-d',
					fieldLabel : '创建日期',
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				//columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelContent',
					xtype : 'textarea',
					editable:false,
					fieldLabel : '渠道宣传模版内容',
					maxLength:1000,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [{
				text : '保存',
				id:'baocun_id',
				//disabled:JsContext.checkGrant('baocun_id'),
				handler : function() {
            		if(!add2ChannelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}
//					Ext.Ajax.request({
//						url : basepath + '/editChannelManage.json',
//						method : 'POST',
//						form : add2ChannelForm.getForm().id,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//						success : function() {
//							Ext.Msg.alert('提示', '操作成功');
//							editChannelStore.reload();
//						}
//					});
            		Ext.Msg.alert('提示', '操作成功');
					add2ChannelWindow.hide();
					add2ChannelForm.getForm().reset();
				}
			}, {
				text : '取  消',
				handler : function() {
					add2ChannelWindow.hide();
				}
			}
		]
	});
	
	// 修改基本信息展示的from
	var edit2ChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		labelAlign : 'right',
		id : 'editUpdateForms',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : fuqdStore,
					resizable : true,
					name : 'channelName',
					hiddenName : 'channelName',
					xtype : 'combo',
					fieldLabel : '*渠道名称',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : channelModelNameStore,
					resizable : true,
					name : 'modelName',
					hiddenName : 'modelName',
					xtype : 'combo',
					fieldLabel : '*渠道宣传模版名称',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelId',
					xtype : 'textfield',
					editable:false,
					fieldLabel : '*渠道编号',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelName',
					xtype : 'textfield',
					editable:false,
					fieldLabel : '渠道适用客户级别',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'createUserName',
					xtype : 'hidden',
					fieldLabel : '创建人',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'createDate',
					xtype : 'hidden',
					format:'Y-m-d',
					fieldLabel : '创建日期',
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'modelId',
					xtype : 'textfield',
					editable:false,
					fieldLabel : '渠道宣传模版编号',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				//columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelContent',
					xtype : 'textarea',
					editable:false,
					fieldLabel : '渠道宣传模版内容',
					maxLength:1000,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [
			{
				text : '保  存',
				handler : function() {
					if(!edit2ChannelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}	
//					Ext.Ajax.request({
//						url : basepath + '/editChannelManage.json',
//						method : 'POST',
//						form : edit2ChannelForm.getForm().id,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//						success : function() {
//							Ext.Msg.alert('提示', '操作成功');
//							editChannelStore.reload();
//						}
//					});
					Ext.Msg.alert('提示', '操作成功');
					edit2ChannelWindow.hide();
				}
			}, {
				text : '取  消',
				handler : function() {
					edit2ChannelWindow.hide();
				}
			} ]
	});
	
	// 新增渠道的from
	var add2ChannelPanel = new Ext.Panel({
		width : 700,
		height : 300,
		layout : 'fit',
		buttonAlign : "center",
		items : [ add2ChannelForm]
	});
	
	// 修改渠道的from
	var edit2ChannelPanel = new Ext.Panel({
		labelWidth : 150,
		height : 300,
		layout : 'fit',
		//autoScroll : true,
		buttonAlign : "center",
		items : [ edit2ChannelForm ]
	});

	// 展示新增的窗口
	function add2ChannelInit() {
		add2ChannelForm.getForm().reset();
		add2ChannelWindow.show();
	}
	
	// 展示修改窗口
	function edit2ChannelInit() {
		edit2ChannelWindow.show();
	}
	
	// 定义新增窗口
	var add2ChannelWindow = new Ext.Window({
		title : '渠道新增',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 300,
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
		items : [ add2ChannelPanel ]
	});
	
	// 定义修改窗口
	var edit2ChannelWindow = new Ext.Window({
		title : '渠道修改',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 315,
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
		items : [ edit2ChannelPanel ]
	});
