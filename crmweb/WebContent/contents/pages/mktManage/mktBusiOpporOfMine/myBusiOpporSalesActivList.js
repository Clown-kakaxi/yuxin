/**
 * 营销管理->商机管理->我的商机：销售活动管理JS文件；wzy；2013-02-28
 */
// 销售阶段
var sales_stage_store = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=SALES_STAGE'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value']),
			sortInfo : {
				field : "key",
				direction : "ASC"
			}
		});

// 活动执行方式
var exec_way_store = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=EXEC_WAY'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

var salesActivStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath
								+ '/mktBusiOpporSalesActivQueryAction.json'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'json.count',
						root : 'json.data'
					}, [{
								name : 'salesActivId',
								mapping : 'SALES_ACTIV_ID'
							}, {
								name : 'opporId',
								mapping : 'OPPOR_ID'
							}, {
								name : 'salesActivName',
								mapping : 'SALES_ACTIV_NAME'
							}, {
								name : 'salesStage',
								mapping : 'SALES_STAGE'
							}, {
								name : 'SALES_STAGE_ORA'
							}, {
								name : 'execDate',
								mapping : 'EXEC_DATE'
							}, {
								name : 'execWay',
								mapping : 'EXEC_WAY'
							}, {
								name : 'EXEC_WAY_ORA'
							}, {
								name : 'activContent',
								mapping : 'ACTIV_CONTENT'
							}, {
								name : 'execUserId',
								mapping : 'EXEC_USER_ID'
							}, {
								name : 'execUserName',
								mapping : 'EXEC_USER_NAME'
							}, {
								name : 'execOrgId',
								mapping : 'EXEC_ORG_ID'
							}, {
								name : 'execOrgName',
								mapping : 'EXEC_ORG_NAME'
							}, {
								name : 'nextContactTime',
								mapping : 'NEXT_CONTACT_TIME'
							}, {
								name : 'nextExecWay',
								mapping : 'NEXT_EXEC_WAY'
							}, {
								name : 'NEXT_EXEC_WAY_ORA'
							}, {
								name : 'nextExecContent',
								mapping : 'NEXT_EXEC_CONTENT'
							}, {
								name : 'activMemo',
								mapping : 'ACTIV_MEMO'
							}])
		});

var sm_sales = new Ext.grid.CheckboxSelectionModel();
var rownum_sales = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

var cm_salse = new Ext.grid.ColumnModel([rownum_sales,
		sm_sales, // 定义列模型
		{
			header : '销售活动编号',
			dataIndex : 'salesActivId',
			hidden : true
		}, {
			header : '销售阶段',
			dataIndex : 'salesStage',
			hidden : true
		}, {
			header : '活动执行方式',
			dataIndex : 'execWay',
			hidden : true
		}, {
			header : '下次执行方式',
			dataIndex : 'nextExecWay',
			hidden : true
		}, {
			header : '活动执行人ID',
			dataIndex : 'execUserId',
			hidden : true
		}, {
			header : '活动执行人所在机构ID',
			dataIndex : 'execOrgId',
			hidden : true
		}, {
			header : '商机ID',
			dataIndex : 'opporId',
			hidden : true
		}, {
			header : '销售活动名称',
			dataIndex : 'salesActivName',
			sortable : true,
			width : 120
		}, {
			header : '销售阶段',
			dataIndex : 'SALES_STAGE_ORA',
			sortable : true,
			width : 100
		}, {
			header : '活动执行日期',
			dataIndex : 'execDate',
			sortable : true,
			width : 90
		}, {
			header : '活动执行方式',
			dataIndex : 'EXEC_WAY_ORA',
			sortable : true,
			width : 90
		}, {
			header : '活动执行人',
			dataIndex : 'execUserName',
			sortable : true,
			width : 80
		}, {
			header : '活动执行机构',
			dataIndex : 'execOrgName',
			sortable : true,
			width : 120
		}, {
			header : '下次联系时间',
			dataIndex : 'nextContactTime',
			sortable : true,
			width : 90
		}, {
			header : '下次执行方式',
			dataIndex : 'NEXT_EXEC_WAY_ORA',
			sortable : true,
			width : 90
		}]);

// 功能按钮定义
var listPanel_sales = new Ext.grid.GridPanel({
			layout : 'fit',
			frame : true,
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : salesActivStore,
			stripeRows : true, // 斑马线
			sm : sm_sales,
			cm : cm_salse,
			height : 470,
			tbar : [{
						text : '活动详情',
						iconCls : 'detailIconCss',
						handler : function() {
							viewSalesActivInit();
						}
					}, '-', {
						text : '新增活动',
						iconCls : 'addIconCss',
						handler : function() {
							addSalesActivInit();
						}
					}, '-', {
						text : '维护活动',
						iconCls : 'editIconCss',
						handler : function() {
							editSalesActivInit();
						}
					}, '-', {
						text : '删除活动',
						iconCls : 'deleteIconCss',
						handler : function() {
							deleteSalesActivInit();
						}
					}],
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// ===============================定义列表、新增、修改、查看Form表单==========================开始===
// 新增销售活动窗口From表单
var addSalesActivForm = new Ext.FormPanel({
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
										fieldLabel : '商机ID',
										name : 'opporId',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '*销售活动名称',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										name : 'salesActivName',
										anchor : '90%'
									}, {
										xtype : 'datefield',
										fieldLabel : '*活动执行日期',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										format : 'Y-m-d',
										editable : true,
										name : 'execDate',
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
										store : sales_stage_store,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '*销售阶段',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										name : 'salesStage',
										hiddenName : 'salesStage',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										editable : false,
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}, {
										store : exec_way_store,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '活动执行方式',
										name : 'execWay',
										hiddenName : 'execWay',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										editable : false,
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
										xtype : 'datefield',
										fieldLabel : '*下次联系时间',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										format : 'Y-m-d',
										editable : true,
										name : 'nextContactTime',
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
										store : exec_way_store,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '下次执行方式',
										name : 'nextExecWay',
										hiddenName : 'nextExecWay',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										editable : false,
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}]
						}]
			}, {
				layout : 'form',
				items : [{
							xtype : 'textarea',
							fieldLabel : '活动内容',
							name : 'activContent',
							anchor : '95%'
						}, {
							xtype : 'textarea',
							fieldLabel : '下次执行内容',
							name : 'nextExecContent',
							anchor : '95%'
						}, {
							xtype : 'textarea',
							fieldLabel : '备注',
							name : 'activMemo',
							anchor : '95%'
						}]
			}],
	buttons : [{
		text : '保存',
		handler : function() {
			if (!addSalesActivForm.getForm().isValid()) {
				Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
				return false;
			}
			var saveUrl = basepath
					+ '/mktBusiOpporSalesActivOperationAction!saveOrUpdateBusiOpporActiv.json';
			var oppor_id = addSalesActivForm.form.findField('opporId')
					.getValue();
			Ext.Ajax.request({
						url : saveUrl,
						method : 'POST',
						form : addSalesActivForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response) {
							Ext.Msg.alert('提示', '保存成功！');
							salesActivStore.load({
										params : {
											start : 0,
											condition : '',
											'oppor_id' : oppor_id
										}
									});
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '保存失败！');
						}
					});
			addSalesActivWindow.hide();
		}
	}, {
		text : '关闭',
		handler : function() {
			addSalesActivWindow.hide();
		}
	}]
});

// 活动维护窗口From表单
var editSalesActivForm = new Ext.FormPanel({
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
										fieldLabel : '销售活动ID',
										name : 'salesActivId',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '商机ID',
										name : 'opporId',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '执行人ID',
										name : 'execUserId',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '执行人名称',
										name : 'execUserName',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '执行人所在机构ID',
										name : 'execOrgId',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '执行人所在机构名称',
										name : 'execOrgName',
										hidden : true
									}, {
										xtype : 'textfield',
										fieldLabel : '*销售活动名称',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										name : 'salesActivName',
										anchor : '90%'
									}, {
										xtype : 'datefield',
										fieldLabel : '*活动执行日期',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										format : 'Y-m-d',
										editable : true,
										name : 'execDate',
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
										store : sales_stage_store,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '*销售阶段',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										name : 'salesStage',
										hiddenName : 'salesStage',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										editable : false,
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}, {
										store : exec_way_store,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '活动执行方式',
										name : 'execWay',
										hiddenName : 'execWay',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										editable : false,
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
										xtype : 'datefield',
										fieldLabel : '*下次联系时间',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										format : 'Y-m-d',
										editable : true,
										name : 'nextContactTime',
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [{
										store : exec_way_store,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '下次执行方式',
										name : 'nextExecWay',
										hiddenName : 'nextExecWay',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										editable : false,
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}]
						}]
			}, {
				layout : 'form',
				items : [{
							xtype : 'textarea',
							fieldLabel : '活动内容',
							name : 'activContent',
							anchor : '95%'
						}, {
							xtype : 'textarea',
							fieldLabel : '下次执行内容',
							name : 'nextExecContent',
							anchor : '95%'
						}, {
							xtype : 'textarea',
							fieldLabel : '备注',
							name : 'activMemo',
							anchor : '95%'
						}]
			}],
	buttons : [{
		text : '保存',
		handler : function() {
			if (!editSalesActivForm.getForm().isValid()) {
				Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
				return false;
			}
			var saveUrl = basepath
					+ '/mktBusiOpporSalesActivOperationAction!saveOrUpdateBusiOpporActiv.json';
			var oppor_id = editSalesActivForm.form.findField('opporId')
					.getValue();
			Ext.Ajax.request({
						url : saveUrl,
						method : 'POST',
						form : editSalesActivForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response) {
							Ext.Msg.alert('提示', '保存成功！');
							salesActivStore.load({
										params : {
											start : 0,
											condition : '',
											'oppor_id' : oppor_id
										}
									});
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '保存失败！');
						}
					});
			editSalesActivWindow.hide();
		}
	}, {
		text : '关闭',
		handler : function() {
			editSalesActivWindow.hide();
		}
	}]
});

// 销售活动详情窗口From表单
var viewMySalesActivForm = new Ext.FormPanel({
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
												disabled : true,
												name : 'salesActivName',
												anchor : '90%'
											}, {
												xtype : 'textfield',
												fieldLabel : '活动执行日期',
												disabled : true,
												name : 'execDate',
												anchor : '90%'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '销售阶段',
												disabled : true,
												name : 'SALES_STAGE_ORA',
												anchor : '90%'
											}, {
												xtype : 'textfield',
												fieldLabel : '活动执行方式',
												disabled : true,
												name : 'EXEC_WAY_ORA',
												anchor : '90%'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '活动执行人',
												disabled : true,
												name : 'execUserName',
												anchor : '90%'
											}, {
												xtype : 'textfield',
												fieldLabel : '活动执行机构',
												disabled : true,
												name : 'execOrgName',
												anchor : '90%'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '下次联系时间',
												disabled : true,
												name : 'nextContactTime',
												anchor : '90%'
											}, {
												xtype : 'textfield',
												fieldLabel : '下次执行方式',
												disabled : true,
												name : 'NEXT_EXEC_WAY_ORA',
												anchor : '90%'
											}]
								}]
					}, {
						layout : 'form',
						items : [{
									layout : 'form',
									items : [{
												xtype : 'textarea',
												fieldLabel : '活动内容',
												name : 'activContent',
												disabled : true,
												anchor : '95%'
											}, {
												xtype : 'textarea',
												fieldLabel : '下次执行内容',
												name : 'nextExecContent',
												disabled : true,
												anchor : '95%'
											}, {
												xtype : 'textarea',
												fieldLabel : '备注',
												disabled : true,
												name : 'activMemo',
												anchor : '95%'
											}]
								}]
					}],
			buttons : [{
						text : '关闭',
						handler : function() {
							viewMySalesActivWindow.hide();
						}
					}]
		});
// ===============================定义列表、新增、修改、查看Form表单==========================结束===

// ===============================定义列表、新增、修改、查看窗体==========================开始===
// 定义新增活动窗口
var addSalesActivWindow = new Ext.Window({
			title : '新增活动',
			plain : true,
			layout : 'fit',
			width : 700,
			height : 380,
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
			items : [addSalesActivForm]
		});

// 定义活动维护窗口
var editSalesActivWindow = new Ext.Window({
			title : '活动维护',
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
			items : [editSalesActivForm]
		});

// 定义详情查看窗口
var viewMySalesActivWindow = new Ext.Window({
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
			items : [viewMySalesActivForm]
		});

// 定义销售活动窗口
var salesActivWindow = new Ext.Window({
			title : '销售活动',
			plain : true,
			layout : 'fit',
			width : 880,
			height : 450,
			resizable : true,
			draggable : true,
			closable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			loadMask : true,
			maximizable : true,
			collapsible : true,
			titleCollapse : true,
			buttonAlign : 'center',
			border : false,
			constrain : true,
			items : [listPanel_sales],
			listeners : {
				'beforehide' : function() {
					queryMyBusiOpporList();
				}
			},
			buttons : [{
						text : '关闭',
						handler : function() {
							salesActivWindow.hide();
						}
					}]
		});

// 查询我的商机列表
function queryMyBusiOpporList() {
	var conditionStr = qForm.getForm().getFieldValues();
	store.on('beforeload', function() {
				this.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
			});
	store.reload({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
}
// ===============================定义列表、新增、修改、查看窗体==========================结束===

// ===============================定义打开窗体的方法==========================开始===
// 打开“销售活动管理”列表窗口
function openSalesActivInit() {
	var record = grid.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择商机！');
		return false;
	}
	var checkedNodes = grid.getSelectionModel().selections.items;
	if (checkedNodes != null && checkedNodes.length > 1) {
		Ext.Msg.alert('提示', '只能选择一个商机！');
		return false;
	}
	var selectRe = grid.getSelectionModel().getSelections()[0];
	var opporStat = selectRe.data.opporStat;// 商机状态
	if (opporStat != "4") {// 4-执行中
		Ext.Msg.alert('提示', '只有“执行中”状态的商机才能管理销售活动！');
		return false;
	}
	var oppor_id = selectRe.data.opporId;// 选中记录的商机ID
	salesActivStore.load({
				params : {
					start : 0,
					condition : '',
					'oppor_id' : oppor_id
				}
			});
	salesActivWindow.show();
}

// 打开“新增销售活动”窗口
function addSalesActivInit() {
	addSalesActivForm.getForm().reset();
	// 设置商机ID值
	var selectRe = grid.getSelectionModel().getSelections()[0];
	var oppor_id = selectRe.data.opporId;// 选中记录的商机ID
	addSalesActivForm.form.findField('opporId').setValue(oppor_id);
	addSalesActivWindow.show();
}

// 打开“维护销售活动”窗口
function editSalesActivInit() {
	var record = listPanel_sales.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要维护的活动！');
		return false;
	}
	var selectRe = grid.getSelectionModel().getSelections()[0];
	var oppor_id = selectRe.data.OPPOR_ID;// 选中记录的商机ID
	editSalesActivForm.getForm().reset();
	editSalesActivForm.getForm().loadRecord(record);
	// 设置商机ID值
	addSalesActivForm.form.findField('opporId').setValue(oppor_id);
	editSalesActivWindow.show();
}

// 打开“销售活动详情”窗口
function viewSalesActivInit() {
	var record = listPanel_sales.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要查看的活动！');
		return false;
	}
	viewMySalesActivForm.getForm().reset();
	viewMySalesActivForm.getForm().loadRecord(record);
	viewMySalesActivWindow.show();
}

// 删除销售活动
function deleteSalesActivInit() {
	var record = listPanel_sales.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要删除的销售活动！');
		return false;
	}
	var checkedNodes = listPanel_sales.getSelectionModel().selections.items;
	var salesActivIds = '';// 选中的销售活动记录ID的集合
	var salesActivId = null;// 商机ID
	for (var i = 0; i < checkedNodes.length; i++) {
		salesActivId = checkedNodes[i].data.salesActivId;
		salesActivIds += (salesActivId);
		if (i < checkedNodes.length - 1) {
			salesActivIds += ",";
		}
	}
	var delUrl = basepath
			+ '/mktBusiOpporSalesActivOperationAction!delBusiOpporActivByIds.json?';
	var selectRe = grid.getSelectionModel().getSelections()[0];
	var oppor_id = selectRe.data.opporId;// 选中记录的商机ID
	Ext.MessageBox.confirm('提示', '您确定要删除吗？', function(buttonId) {
				if (buttonId.toLowerCase() == "no") {// 不删除
					return false;
				} else {// 要删除
					Ext.Ajax.request({
								url : delUrl,
								params : {
									'salesActivIds' : salesActivIds
								},
								waitMsg : '正在删除数据,请等待...',
								success : function() {
									Ext.Msg.alert('提示', '操作成功！');
									salesActivStore.load({
												params : {
													start : 0,
													condition : '',
													'oppor_id' : oppor_id
												}
											});
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON
											.decode(response.status);
									if (resultArray == 403) {
										Ext.Msg.alert('提示',
												response.responseText);
									} else {
										Ext.Msg
												.alert(
														'提示',
														'操作失败，失败原因：'
																+ response.responseText);
										store.reload();
									}
								}
							});
				}
			});
}
// ===============================定义打开窗体的方法==========================结束===
