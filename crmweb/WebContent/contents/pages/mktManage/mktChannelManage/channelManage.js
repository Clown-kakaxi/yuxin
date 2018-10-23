Ext.onReady(function() {
	Ext.QuickTips.init();
	var boxstore8 = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=P_CUST_GRADE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var qForm = new Ext.form.FormPanel({
		title : "渠道查询",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 120,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '渠道ID',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CHANNEL_ID',
					anchor : '90%'
				} ]
			},{
				columnWidth : .25,
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
			handler : function() {
				var conditionStr = qForm.getForm().getValues(false);
				channelStore.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				channelStore.load({
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
		name : 'channelId',
		mapping : 'CHANNEL_ID'
	}, {
		name : 'channelName',
		mapping : 'CHANNEL_NAME'
	}, {
		name : 'channelModelContent',
		mapping : 'channelModelContent'
	}, {
		name : 'channelFitCustLevel',
		mapping : 'CHANNEL_FIT_CUST_LEVEL'
//	}, {
//		name : 'createUserName',
//		mapping : 'CREATE_USER_NAME'
//	}, {
//		name : 'createDate',
//		mapping : 'CREATE_DATE'
	}]);

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
		header : '模板名称',
		width : 250,
		align : 'center',
		dataIndex : 'channelModelContent',
		sortable : true
//	}, {
//		header : '创建人',
//		width : 100,
//		align : 'left',
//		dataIndex : 'createUserName',
//		sortable : true
//	}, {
//		header : '创建日期',
//		width : 100,
//		align : 'left',
//		dataIndex : 'createDate',
//		sortable : true
}]);

	/**
	 * 数据存储
	 */
	var channelStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, record)
	});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","CHANNEL_ID":"3453","CHANNEL_NAME":"客户经理","CHANNEL_FIT_CUST_LEVEL":"A级","channelModelContent":"产品宣传模板"},
			{"rownum":"2","CHANNEL_ID":"3463","CHANNEL_NAME":"CC","CHANNEL_FIT_CUST_LEVEL":"B级","channelModelContent":"产品宣传模板"},
			{"rownum":"3","CHANNEL_ID":"2342","CHANNEL_NAME":"邮件","CHANNEL_FIT_CUST_LEVEL":"B级","channelModelContent":"产品宣传模板"},			
			{"rownum":"4","CHANNEL_ID":"2347","CHANNEL_NAME":"ATM","CHANNEL_FIT_CUST_LEVEL":"C级","channelModelContent":"产品宣传模板"},		
			{"rownum":"5","CHANNEL_ID":"2347","CHANNEL_NAME":"柜面","CHANNEL_FIT_CUST_LEVEL":"C级","channelModelContent":"产品宣传模板"},	
			{"rownum":"6","CHANNEL_ID":"2347","CHANNEL_NAME":"VTM","CHANNEL_FIT_CUST_LEVEL":"C级","channelModelContent":"产品宣传模板"},	
			{"rownum":"7","CHANNEL_ID":"2347","CHANNEL_NAME":"手机银行","CHANNEL_FIT_CUST_LEVEL":"C级","channelModelContent":"产品宣传模板"},	
			{"rownum":"8","CHANNEL_ID":"2347","CHANNEL_NAME":"网银","CHANNEL_FIT_CUST_LEVEL":"C级","channelModelContent":"产品宣传模板"},	
			{"rownum":"9","CHANNEL_ID":"2347","CHANNEL_NAME":"短信","CHANNEL_FIT_CUST_LEVEL":"C级","channelModelContent":"产品宣传模板"}	
			]
		};
	channelStore.loadData(memberData);

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
//	channelStore.load({
//		params : {
//			start : 0,
//			limit : parseInt(pagesize_combo.getValue())
//		}
//	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue())
//		, channelStore
//				.reload({
//					params : {
//						start : 0,
//						limit : parseInt(pagesize_combo.getValue())
//					}
//				});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : channelStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	var tbar = new Ext.Toolbar(
			{
				items : [{
						text : '新增',
						iconCls:'addIconCss ',
						handler : function() {
							addChannelInit();
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
							var actiStatus = selectRe.data.actiStatus;
								editChannelForm.getForm().loadRecord(selectRe);
								document.getElementById('channelIdStr').value = selectRe.data.channelId;
								editChannelInit();
						}
						}
					},'-',{
						text : '删除',
						iconCls:'deleteIconCss',
						handler : function() 
						{
							 var selectLength = grid.getSelectionModel()
								.getSelections().length;
							 var selectRe;
							 var tempId;
							 var idStr = '';
							 var actiStatus;
							if(selectLength < 1){
								Ext.Msg.alert('提示','请选择需要删除的记录!');
							} else {
								for(var i = 0; i<selectLength;i++)
								{
									selectRe = grid.getSelectionModel()
									.getSelections()[i];
									//注释掉一下代码 供德阳银行 POC demo使用 YUYZ
//									actiStatus = selectRe.data.actiStatus;
//									if(actiStatus != '1'){
//										Ext.Msg.alert('提示','该渠道已经在营销活动中引用，不能删除!');
//										return;
//									}
//									tempId = selectRe.data.marketActivityId;
//									idStr += tempId;
//									if( i != selectLength-1)
//										idStr += ',';
									//end
									cId = selectRe.data.channelId;
									if(cId == '3453')
									{
										Ext.Msg.alert('提示','该渠道已经在营销活动中引用，不能删除!');
										return;
									}
								}
									Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
										if(buttonId.toLowerCase() == "no"){
										return;
										} 
										//注释掉一下代码 供德阳银行 POC demo使用 YUYZ
//										Ext.Ajax.request({
//													url : basepath
//													+ '/market-activity!batchDestroy.json?idStr='+ idStr,
//													//method : 'DELETE',
//													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//													success : function() {
//														Ext.Msg.alert('提示', '操作成功');
//														store.reload();
//													},
//													failure : function(response) {
//														var resultArray = Ext.util.JSON.decode(response.status);
//														if(resultArray == 403) {
//													           Ext.Msg.alert('提示', response.responseText);
//													  } else {
//
//														Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//														store.reload();
//													  }
//													}
//												});
										Ext.Msg.alert('提示', '操作成功');
										//end
									});
							}
						}
					}]
			});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
		title : '渠道基本信息列表',
		width:700,
		height:315,
		frame : true,
		autoScroll : true,
		region : 'center',
		store : channelStore,
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
	var addChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		id:'allForms',
		labelAlign : 'right',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelName',
					xtype : 'textfield',
					fieldLabel : '*渠道名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelId',
					xtype : 'textfield',
					fieldLabel : '*渠道编号',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [  {
				columnWidth : .5,
				layout : 'form',
				items : [ /*{
					name : 'channelName',
					xtype : 'combo',
					fieldLabel : '客户级别',
					anchor : '90%'
				},*/new Ext.form.ComboBox({
					hiddenName : 'channelFitCustLevel',
					fieldLabel : '客户级别',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore8,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelId1',
					hiddenName : 'channelId1',
					xtype : 'combo',
					fieldLabel : '营销模板名称',
					store : new Ext.data.ArrayStore({
						fields : [ 'value', 'text' ],
						data : [ [ 100, '产品推荐模板' ], [ 200, '活动宣传模板' ]]
					}),
					valueField : 'value',
					displayField : 'text',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : 1,
				items : [ {
					name : 'channelContent',
					xtype : 'textarea',
					fieldLabel : '渠道描述',
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
            		if(!addChannelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}
            		Ext.Msg.alert('提示', '操作成功');
            		//注释掉一下代码 供德阳银行 POC demo使用 YUYZ
//					Ext.Ajax.request({
//						url : basepath + '/channelManage.json',
//						method : 'POST',
//						form : addChannelForm.getForm().id,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//						success : function() {
//							Ext.Msg.alert('提示', '操作成功');
//							channelStore.reload();
//						},
//						failure : function(response) {
//							var resultArray = Ext.util.JSON.decode(response.status);
//						       if(resultArray == 403) {
//						           Ext.Msg.alert('提示', response.responseText);
//						  } else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//						}
//						       channelStore.reload();
//						}
//					});
            		//end
					addChannelWindow.hide();
					addChannelForm.getForm().reset();
				}
			}, {
				text : '取  消',
				handler : function() {
					addChannelWindow.hide();
				}
			}
		]
	});
	
	// 修改基本信息展示的from
	var editChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		labelAlign : 'right',
		id : 'editAllForms',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelName',
					xtype : 'textfield',
					fieldLabel : '*渠道名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelId',
					xtype : 'textfield',
					fieldLabel : '*渠道编号',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					hiddenName : 'channelFitCustLevel',
					fieldLabel : '客户级别',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore8,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					name : 'channelId1',
					hiddenName : 'channelId1',
					xtype : 'combo',
					fieldLabel : '营销模板名称',
					store : new Ext.data.ArrayStore({
						fields : [ 'value', 'text' ],
						data : [ [ 100, '产品推荐模板' ], [ 200, '活动宣传模板' ]]
					}),
					valueField : 'value',
					displayField : 'text',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : 1,
				items : [ {
					name : 'channelContent',
					xtype : 'textarea',
					fieldLabel : '渠道描述',
					maxLength:1000,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [
			{
				text : '保  存',
				handler : function() {
					if(!editChannelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}	
					//注释掉一下代码 供德阳银行 POC demo使用 YUYZ
//					Ext.Ajax.request({
//						url : basepath + '/channelManage.json',
//						method : 'POST',
//						form : editChannelForm.getForm().id,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//						success : function() {
//							Ext.Msg.alert('提示', '操作成功');
//							store.reload();
//						},
//						failure : function(response) {
//							var resultArray = Ext.util.JSON.decode(response.status);
//						       if(resultArray == 403) {
//						           Ext.Msg.alert('提示', response.responseText);
//						  } else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//						  }
//							store.reload();
//						}
//					});
					Ext.Msg.alert('提示', '操作成功');
					//end
					editChannelWindow.hide();
				}
			}, {
				text : '取  消',
				handler : function() {
					editChannelWindow.hide();
				}
			} ]
	});
	
	// 新增渠道的from
	var addChannelPanel = new Ext.Panel({
		width : 600,
		height : 250,
		layout : 'fit',
		buttonAlign : "center",
		items : [ addChannelForm]
	});
	
	// 修改渠道的from
	var editChannelPanel = new Ext.Panel({
		labelWidth : 150,
		height : 250,
		layout : 'fit',
		buttonAlign : "center",
		items : [ editChannelForm ]
	});

	// 展示新增的窗口
	function addChannelInit() {
		addChannelForm.getForm().reset();
		addChannelWindow.show();
	}
	
	// 展示修改窗口
	function editChannelInit() {
		editChannelWindow.show();
	}
	
	// 定义新增窗口
	var addChannelWindow = new Ext.Window({
		title : '渠道新增',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 250,
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
		items : [ addChannelPanel ]
	});
	
	// 定义修改窗口
	var editChannelWindow = new Ext.Window({
		title : '渠道修改',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 250,
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
		items : [ editChannelPanel ]
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