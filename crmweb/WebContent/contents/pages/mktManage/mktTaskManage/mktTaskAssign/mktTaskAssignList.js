// 下拉框定义：任务执行对象类型
var operTypeStatStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=MTASK_OPER_TYPE'
					}),
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});
// 下拉框定义：营销任务类型
var taskTypeStatStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=MTASK_TYPE'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});
// 下拉框定义：营销任务状态
var assuStatStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=MTASK_STAT'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

Ext.onReady(function() {
	// 查询条件Form表单
	var searchPanel = new Ext.form.FormPanel({
				title : "营销任务查询",
				labelWidth : 105,
				labelAlign : 'right',
				height : 100,
				frame : true,
				region : 'north',
				autoScroll : true,
				layout : 'column',
				items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										name : 'TASK_NAME',
										fieldLabel : '任务名称',
										anchor : '90%'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										store : assuStatStore,
										xtype : 'combo',
										resizable : true,
										name : 'TASK_STAT',
										hiddenName : 'TASK_STAT',
										fieldLabel : '任务状态',
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
							columnWidth : .33,
							layout : 'form',
							items : [new Com.yucheng.crm.common.OrgUserManage({
										xtype : 'userchoose',
										fieldLabel : '下达人',
										id : 'dist',
										labelStyle : 'text-align:right;',
										name : 'CUST_MANAGER',
										hiddenName : 'DIST_USER',
										// searchRoleType:('127,47'), //指定查询角色属性
										searchType : 'SUBTREE',/*
																 * 指定查询机构范围属性
																 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
																 * （所有父、祖机构）ALLORG（所有机构）
																 */
										singleSelect : false,
										anchor : '90%'
									})]
						}],
				buttonAlign : 'center',
				buttons : [{
					text : '查询',
					handler : function() {
						var conditionStr = searchPanel.getForm()
								.getValues(false);
						store.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
						store.load({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});

					}
				}, {
					text : '重置',
					handler : function() {
						searchPanel.getForm().reset();
					}
				}]

			});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 查询结果列表表头定义
	var columns = new Ext.grid.ColumnModel([rownum, sm, {
				header : '营销任务编号',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'taskId',
				sortable : true
			}, {
				header : '创建机构',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'createOrgName',
				sortable : true
			}, {
				header : '最近维护时间',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'recentlyUpdateDate',
				sortable : true
			}, {
				header : '最近维护人',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'recentlyUpdateName',
				sortable : true
			}, {
				header : '营销任务名称',
				width : 330,
				align : 'left',
				dataIndex : 'taskName',
				sortable : true
			}, {
				header : '上级任务名称',
				width : 200,
				align : 'left',
				hidden : true,
				dataIndex : 'taskParentName',
				sortable : true
			}, {
				header : '任务状态',
				width : 100,
				align : 'left',
				dataIndex : 'taskStat',
				sortable : true
			}, {
				header : '下达人',
				width : 100,
				align : 'left',
				dataIndex : 'distUserName',
				sortable : true
			}, {
				header : '下达人Id',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'distUser',
				sortable : true
			}, {
				header : '下达时间',
				width : 100,
				align : 'left',
				dataIndex : 'taskDistDate',
				sortable : true
			}, {
				header : '下达机构',
				width : 220,
				align : 'left',
				dataIndex : 'distOrgName',
				sortable : true
			}, {
				header : '执行人机构',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'operOrg',
				sortable : true
			}, {
				header : '执行人',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'operName',
				sortable : true
			}, {
				header : '执行人ID',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'operUser',
				sortable : true
			}, {
				header : '创建人',
				width : 100,
				align : 'left',
				dataIndex : 'createUserName',
				sortable : true
			}, {
				header : '创建人ID',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'createUser',
				sortable : true
			}, {
				header : '创建日期',
				width : 100,
				align : 'left',
				dataIndex : 'createDate',
				sortable : true
			}, {
				header : '任务开始时间',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'taskBeginDate',
				sortable : true
			}, {
				header : '任务结束时间',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'taskEndDate',
				sortable : true
			}]);

	// 字段映射
	var record = Ext.data.Record.create([{
				name : 'taskId',
				mapping : 'TASK_ID'
			}, {
				name : 'distOrg',
				mapping : 'DIST_ORG'
			}, {
				name : 'distOrgName',
				mapping : 'DIST_ORG_NAME'
			}, {
				name : 'distTaskType',
				mapping : 'DIST_TASK_TYPE'
			}, {
				name : 'createDate',
				mapping : 'CREATE_DATE'
			}, {
				name : 'createUser',
				mapping : 'CREATE_USER'
			}, {
				name : 'diskUser',
				mapping : 'DISK_USER'
			}, {
				name : 'distUser',
				mapping : 'DIST_USER'
			}, {
				name : 'distUserName',
				mapping : 'DIST_USER_NAME'
			}, {
				name : 'feeAmt',
				mapping : 'FEE_AMT',
				type : 'float'
			}, {
				name : 'createDate',
				mapping : 'CREATE_DATE'
			}, {
				name : 'memo',
				mapping : 'MEMO'
			}, {
				name : 'operOrg',
				mapping : 'OPER_ORG'
			}, {
				name : 'operUser',
				mapping : 'OPER_USER'
			}, {
				name : 'taskBeginDate',
				mapping : 'TASK_BEGIN_DATE'
			}, {
				name : 'taskDistDate',
				mapping : 'TASK_DIST_DATE'
			}, {
				name : 'taskEndDate',
				mapping : 'TASK_END_DATE'
			}, {
				name : 'taskName',
				mapping : 'TASK_NAME'
			}, {
				name : 'taskParentId',
				mapping : 'TASK_PARENT_ID'
			}, {
				name : 'taskStat',
				mapping : 'TASK_STAT_ORA'
			}, {
				name : 'taskType',
				mapping : 'TASK_TYPE'
			}, {
				name : 'createUserName',
				mapping : 'CREATE_USER_NAME'
			}, {
				name : 'diskName',
				mapping : 'DISK_NAME'
			}, {
				name : 'operName',
				mapping : 'OPER_NAME'
			}, {
				name : 'createOrgName',
				mapping : 'CREATE_ORG_NAME'
			}, {
				name : 'recentlyUpdateDate',
				mapping : 'RECENTLY_UPDATE_DATE'
			}, {
				name : 'recentlyUpdateName',
				mapping : 'RECENTLY_UPDATE_NAME'
			}, {
				name : 'taskParentName',
				mapping : 'TASK_PARENT_NAME'
			}]);

	// 查询结果存储对象
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
					url : basepath + '/marketTaskAssignAction.json',
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
				}),
		reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'PLAN_ID',
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
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'],
									[50, '50条/页'], [100, '100条/页'],
									[250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				resizable : true,
				width : 85
			});

	// 默认加载数据
	store.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()), store
						.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
			});

	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	// 查询结果列表定义
	var listPanel = new Ext.grid.GridPanel({
				title : "营销任务列表",
				store : store,
				frame : true,
				id : 'listPanel',
				cm : columns,
				sm : sm,
				stripeRows : true,
				region : 'center',
				frame : true,
				bbar : bbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				tbar : [{
							text : '新增',
							iconCls : 'addIconCss',
							handler : function() {
								addInit();
							}
						}, '-', {
							text : '修改',
							iconCls : 'editIconCss',
							handler : function() {
								editInit();
							}
						}, '-', {
							text : '删除',
							iconCls : 'deleteIconCss',
							handler : function() {
								deleteInit();
							}
						}, '-', {
							text : '下达',
							iconCls : 'custGroupMemIconCss',
							handler : function() {
								transTask(listPanel, store);
							}
						}, '-', {
							text : '调整',
							iconCls : 'editIconCss',
							handler : function() {
								adjustTask(listPanel, store);
							}
						}, '-', {
							text : '关闭',
							iconCls : 'closeIconCss',
							handler : function() {
								closeInit();
							}
						}, '-', {
							text : '详情',
							iconCls : 'detailIconCss',
							handler : function() {
								detailInit();
							}
						}]
			});

	// 营销任务详情窗口展示的from
	var detailPlanPanel = new Ext.Panel({
				autoScroll : true,
//				items : [mktTaskAssignViewForm, mktTaskViewTargetGridPanel]
				items : [mktTaskAssignViewForm, targetViewTap]
			});

	// 营销任务新增窗口展示的from
	var mktTaskAssignAddPanel = new Ext.Panel({
				autoScroll : true,
				items : [mktTaskAssignAddForm, mktTaskAddTargetGridPanel]
			});

	// 营销任务修改窗口展示的from
	var mktTaskAssignModifyPanel = new Ext.Panel({
				autoScroll : true,
				items : [mktTaskAssignModifyForm, mktTaskModifyTargetGridPanel]
			});

	// 定义营销任务新增窗口
	var mktTaskAssignAddWindow = new Ext.Window({
				title : '营销任务新增',
				layout : 'fit',
				width : 1000,
				height : 480,
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
				items : [mktTaskAssignAddPanel],
				listeners : {
					beforehide : function() {
						store.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
					}
				},
				buttons : [{
							text : '保存',
							handler : function() {
								saveTask();
							}
						}, {
							text : '关闭',
							handler : function() {
								mktTaskAssignAddWindow.hide();
							}
						}]
			});

	// 定义营销任务修改窗口
	var mktTaskAssignModifyWindow = new Ext.Window({
				title : '营销任务修改',
				layout : 'fit',
				width : 1000,
				height : 480,
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
				items : [mktTaskAssignModifyPanel],
				listeners : {
					beforehide : function() {
						store.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
					}
				},
				buttons : [{
							text : '保存',
							handler : function() {
								updateTask();
							}
						}, {
							text : '关闭',
							handler : function() {
								mktTaskAssignModifyWindow.hide();
							}
						}]
			});

	// 定义营销任务详情窗口
	var mktTaskAssignViewWindow = new Ext.Window({
				title : '营销任务详情',
				plain : true,
				layout : 'fit',
				width : 1000,
				height : 480,
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
				items : [detailPlanPanel],
				buttonAlign : 'center',
				buttons : [{
							text : '关闭',
							handler : function() {
								mktTaskAssignViewWindow.hide();
							}
						}]
			});

	// 保存新增的任务
	function saveTask() {
		if (!mktTaskAssignAddForm.getForm().isValid()) {
			Ext.MessageBox.alert('提示', '输入有误，请检查输入项！');
			return false;
		};
		var temp = null;
		var targetDataValue = "";
		var tempValue = null;
		for (var i = 0; i < mktTaskAddTargetStore.getCount(); i++) {
			temp = mktTaskAddTargetStore.getAt(i);
			for (var j = 0; j < targetAddCount; j++) {
				tempValue = eval("temp.data.target_value_" + j);
				if (tempValue == null || tempValue == "") {
					tempValue = "0";
				}
				targetDataValue += tempValue;
				if (j != targetAddCount - 1) {
					targetDataValue += ",";
				}
			}
			if (i != mktTaskAddTargetStore.getCount() - 1) {
				targetDataValue += ";";
			}
		}
		Ext.Msg.wait('正在保存，请稍后......', '提示');
		Ext.Ajax.request({
					url : basepath + '/marketTaskAssignAction!saveTask.json',
					params : {
						targetIds : mktTaskAssignAddForm.form
								.findField('targetSelectId').getValue(),
						targetValueData : targetDataValue
					},
					method : 'POST',
					form : mktTaskAssignAddForm.getForm().id,
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						mktTaskAssignAddWindow.hide();
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败，失败原因：'
											+ response.responseText);
						}
						mktTaskAssignAddWindow.hide();
					}
				});
	}

	// 保存修改的任务
	function updateTask() {
		if (!mktTaskAssignModifyForm.getForm().isValid()) {
			Ext.MessageBox.alert('提示', '输入有误，请检查输入项！');
			return false;
		};
		var temp = null;
		var targetDataValue = "";
		var tempValue = null;
		for (var i = 0; i < mktTaskModifyTargetStore.getCount(); i++) {
			temp = mktTaskModifyTargetStore.getAt(i);
			for (var j = 0; j < targetModifyCount; j++) {
				tempValue = eval("temp.data.target_value_" + j);
				if (tempValue == null || tempValue == "") {
					tempValue = "0";
				}
				targetDataValue += tempValue;
				if (j != targetModifyCount - 1) {
					targetDataValue += ",";
				}
			}
			if (i != mktTaskModifyTargetStore.getCount() - 1) {
				targetDataValue += ";";
			}
		}
		Ext.Msg.wait('正在保存，请稍后......', '提示');
		Ext.Ajax.request({
					url : basepath + '/marketTaskAssignAction!updateTask.json',
					params : {
						targetIds : mktTaskAssignModifyForm.form
								.findField('targetSelectId').getValue(),
						targetValueData : targetDataValue
					},
					method : 'POST',
					form : mktTaskAssignModifyForm.getForm().id,
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						mktTaskAssignModifyWindow.hide();
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败，失败原因：'
											+ response.responseText);
						}
						mktTaskAssignModifyWindow.hide();
					}
				});
	}

	// 展示新增窗口
	function addInit() {
		resetForm();
		Ext.getCmp('mktTaskAssignAddOperUser').setVisible(false);
		Ext.getCmp('mktTaskAssignAddOperOrg').setVisible(false);
		mktTaskAssignAddWindow.show();
	}

	// 清空新增基本信息界面的form表单
	function resetForm() {
		var emptyObj = [{
					id : 'taskId',
					value : ''
				}, {
					id : 'taskName',
					value : ''
				}, {
					id : 'distTaskType',
					value : ''
				}, {
					id : 'taskBeginDate',
					value : ''
				}, {
					id : 'taskType',
					value : ''
				}, {
					id : 'taskEndDate',
					value : ''
				}, {
					id : 'memo',
					value : ''
				}, {
					id : 'OPER_USER',
					value : ''
				}, {
					id : 'CUST_MANAGER',
					value : ''
				}, {
					id : 'operObjId',
					value : ''
				}, {
					id : 'operObjName',
					value : ''
				}, {
					id : 'targetSelectId',
					value : ''
				}, {
					id : 'targetSelect',
					value : ''
				}];
		mktTaskAssignAddForm.getForm().setValues(emptyObj);
		// 清空指标列表
		mktTaskAddTargetGridPanel.reconfigure(new Ext.data.Store(),
				new Ext.grid.ColumnModel([]));
	}

	// 展示修改窗口
	function editInit() {
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe = listPanel.getSelectionModel().getSelections()[0];
		if (selectLength < 1) {
			Ext.Msg.alert('提示', '请先选择一条记录！');
			return false;
		} else if (selectLength > 1) {
			Ext.Msg.alert('提示', '只能选择一条记录！');
			return false;
		} else {
			var tempSign = listPanel.getSelectionModel().selections.items[0].data.taskStat;
			if (tempSign != '暂存') {
				Ext.Msg.alert('提示', '只能修改【暂存】状态的任务！');
				return false;
			}
			mktTaskAssignModifyForm.getForm().loadRecord(selectRe);// 填充Form表单
			// 设置编辑界面的“执行机构”、“客户经理”、“指标”放大镜的值
			Ext.Ajax.request({
				url : basepath
						+ '/marketTaskAssignAction!queryTaskOperAndTarget.json',
				method : 'POST',
				params : {
					taskId : selectRe.data.taskId
				},
				waitMsg : '正在查询数据,请等待...',
				success : function(response) {
					var rt = response.responseText;
					var operIds = Ext.util.JSON.decode(rt).operIds;// 执行对象ID
					var operNames = Ext.util.JSON.decode(rt).operNames;// 执行对象名称
					var targetIds = Ext.util.JSON.decode(rt).targetIds;// 任务指标ID
					var targetNames = Ext.util.JSON.decode(rt).targetNames;// 任务指标名称
					// “执行对象”设置值处理逻辑
					var distTaskType = mktTaskAssignModifyForm.form
							.findField('distTaskType').getValue();
					if (distTaskType == "1") {
						// 执行对象为“机构”
						Ext.getCmp('mktTaskAssignModifyOperUser')
								.setVisible(false);
						Ext.getCmp('mktTaskAssignModifyOperOrg')
								.setVisible(true);
						mktTaskAssignModifyForm.getForm().findField("sysOrgId")
								.setValue(operIds);
						mktTaskAssignModifyForm.getForm()
								.findField("sysOrgName").setValue(operNames);
					} else if (distTaskType == "2") {
						// 执行对象为“客户经理”
						Ext.getCmp('mktTaskAssignModifyOperUser')
								.setVisible(true);
						Ext.getCmp('mktTaskAssignModifyOperOrg')
								.setVisible(false);
						mktTaskAssignModifyForm.getForm()
								.findField("custManagerId").setValue(operIds);
						mktTaskAssignModifyForm.getForm()
								.findField("custManagerName")
								.setValue(operNames);
					}
					// “任务指标”设置值处理逻辑
					mktTaskAssignModifyForm.getForm()
							.findField("targetSelectId").setValue(targetIds);
					mktTaskAssignModifyForm.getForm().findField("targetSelect")
							.setValue(targetNames);
					// 给隐藏的“执行对象ID”、“执行对象名称”赋值
					mktTaskAssignModifyForm.getForm().findField("operObjId")
							.setValue(operIds);
					mktTaskAssignModifyForm.getForm().findField("operObjName")
							.setValue(operNames);
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if (resultArray == 403) {
						Ext.Msg.alert('提示', response.responseText);
					} else {

						Ext.Msg.alert('提示', '操作失败，失败原因：'
										+ response.responseText);
						store_in.reload();
					}
				}
			});
			// 查询指标列表
			makeModifyTargetGridOld();
		}
		mktTaskAssignModifyWindow.show();
	};

	// 删除营销任务
	function deleteInit() {
		var checkedNodes = listPanel.getSelectionModel().selections.items;
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe;
		var tempId;
		var idStr = '';
		var marketPlanStatement;
		if (selectLength < 1) {
			Ext.Msg.alert('提示', '请选择要删除的记录！');
		} else {
			var tempSign = listPanel.getSelectionModel().selections.items[0].data.taskStat;
			if (tempSign != "暂存") {
				Ext.Msg.alert('系统提示', '只能删除【暂存】状态下的营销任务！');
				return false;
			} else {
				var json = {
					'id' : []
				};
				for (var i = 0; i < checkedNodes.length; i++) {
					json.id.push(checkedNodes[i].data.taskId);
				}
				Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return;
					}
					Ext.Ajax.request({
								url : basepath
										+ '/marketTaskAssignAction!deleteTask.json',
								method : 'POST',
								params : {
									cbid : Ext.encode(json)
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功！');
									store.reload();
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
									}
									store.reload();
								}
							});

				});
			}
		}
	}

	// 关闭营销任务
	function closeInit() {
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe = listPanel.getSelectionModel().getSelections()[0];
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
			return false;
		} else {
			var tempStat = listPanel.getSelectionModel().selections.items[0].data.taskStat;
			if (tempStat != "已下达") {
				Ext.Msg.alert('提示', '只能关闭【已下达】状态的任务！');
				return false;
			} else {
				// 判断子任务关闭情况
				var taskId = listPanel.getSelectionModel().selections.items[0].data.taskId;
				Ext.Ajax.request({
							url : basepath
									+ '/marketTaskAssignAction!isExistSonTask.json',
							params : {
								'taskId' : taskId
							},
							method : 'GET',
							waitMsg : '正在查询数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : checkResult,
							failure : checkResult
						});
			}
		}
	}

	// 处理：判断子任务是否关闭
	function checkResult(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		if (resultArray == 200 || resultArray == 201) {
			// 未关闭的子任务名字
			var noCompleteTaskNames = Ext.util.JSON
					.decode(response.responseText).noCompleteTaskNames;// 未关闭的子任务名字
			if (noCompleteTaskNames != null && noCompleteTaskNames != '') {
				Ext.Msg.alert('提示', '完成此操作需要先关闭以下子任务：<br>'
								+ noCompleteTaskNames);
				return false;
			} else {
				// 此处是关闭顶级任务，不需要填写指标值，所以，直接处理关闭逻辑
				// 执行关闭任务的逻辑
				closeTask();
			}
		} else {
			Ext.Msg.alert('提示', '关闭任务出错！');
			return false;
		}
	};

	// 执行任务关闭处理后台逻辑
	function closeTask() {
		Ext.MessageBox.confirm('提示', '确定要关闭选中的任务吗？', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {
				return false;
			}
			Ext.Ajax.request({
				url : basepath + '/marketTaskAssignAction!closeTask.json',
				method : 'POST',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				params : {
					'taskId' : listPanel.getSelectionModel().selections.items[0].data.taskId
				},
				success : function() {
					Ext.Msg.alert('提示', '操作成功！');
					store.reload({
								params : {
									start : 0,
									limit : parseInt(pagesize_combo.getValue())
								}
							});
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '操作失败，失败原因：' + response.responseText);
				}
			});
		});
	}

	// 任务详情
	function detailInit() {
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe = listPanel.getSelectionModel().getSelections()[0];
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
			return false;
		} else {
			mktTaskAssignViewForm.getForm().loadRecord(selectRe);// 加载数据
			makeViewTargetGridOld();
			targetViewTap.setActiveTab(0);
			mktTaskAssignViewWindow.show();
		}
	}

	// 页面布局
	var view = new Ext.Viewport({
				layout : 'fit',
				items : [{
							layout : 'border',
							items : [searchPanel, listPanel]
						}]
			});

});