var targetAddCount = 0;
var type = "";// resolve：分解 adjust ：调整
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

var taskId = null;// 任务编号
var num = 0;// 详情的card页面序号
var type = '';// 用以区分新增和修改时的窗口标题
var totalStep = '4';// 创建营销任务的步骤数
Ext.onReady(function() {

	// 查询条件Form表单
	var searchPanel = new Ext.form.FormPanel({
				title : "我的营销任务查询",
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
				header : 'TASK_TYPE',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'taskType',
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
				header : '执行对象类型',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'distTaskType',
				sortable : true
			}, {
				header : '营销任务名称',
				width : 200,
				align : 'left',
				dataIndex : 'taskName',
				sortable : true
			}, {
				header : '上级任务名称',
				width : 200,
				align : 'left',
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
				width : 180,
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
				header : '任务内容',
				width : 130,
				align : 'left',
				hidden : true,
				dataIndex : 'taskDetail',
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
				name : 'taskDetail',
				mapping : 'TASK_DETAIL'
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
					url : basepath + '/marketMainAssuAction.json',
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
				title : "我的营销任务列表",
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
							text : '分解',
							iconCls : 'addIconCss',
							handler : function() {
								resolveInit();
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
								adjustTask();
							}
						}, '-', {
							text : '关闭',
							iconCls : 'closeIconCss',
							handler : function() {
								closeInit(listPanel, store, pagesize_combo);
							}
						}, '-', {
							text : '详情',
							iconCls : 'detailIconCss',
							handler : function() {
								detailInit();
							}
						}]
			});

	// 营销任务分解分解窗口展示的from
	var mktTaskAssignResolvePanel = new Ext.Panel({
				autoScroll : true,
				items : [mktTaskAssignResolveForm, mktTaskResolveMainGrid,
						mktTaskResolveTargetGridPanel]
			});
	// 保存分解/调整结果的任务
	function saveTask() {
		if (type == 'resolve') {
			if (mktTaskAssignResolveForm.form.findField('distTaskType')
					.getValue() == '1'
					&& (mktTaskAssignResolveForm.form.findField('operObjName1')
							.getValue() == '' || mktTaskAssignResolveForm.form
							.findField('operObjName1').getValue() == null)) {
				Ext.MessageBox.alert('提示', '请输入机构信息进行分解！');
				return false;
			}
			if ((mktTaskAssignResolveForm.form.findField('CUST_MANAGER')
					.getValue() == '' || mktTaskAssignResolveForm.form
					.findField('CUST_MANAGER').getValue() == null)
					&& mktTaskAssignResolveForm.form.findField('distTaskType')
							.getValue() == '2') {
				Ext.MessageBox.alert('提示', '请输入客户经理信息进行分解！');
				return false;
			}
		}

		var temp = null;
		var targetDataValue = "";
		var tempValue = null;
		var targetIds = '';// 指标ID集合
		// 用待分配的指标store来拼接指标信息
		targetAddCount = mktTaskResolveMainStore.getCount();
		for (var i = 0; i < mktTaskResolveMainStore.getCount(); i++) {
			temp = mktTaskResolveMainStore.getAt(i);
			targetIds += temp.data.targetCode;
			if (i != mktTaskResolveMainStore.getCount() - 1) {
				targetIds += ",";
			}
		}
		mktTaskAssignResolveForm.form.findField('targetSelectId')
				.setValue(targetIds);

		for (var i = 0; i < mktTaskResolveTargetStore.getCount(); i++) {
			temp = mktTaskResolveTargetStore.getAt(i);
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
			if (i != mktTaskResolveTargetStore.getCount() - 1) {
				targetDataValue += ";";
			}
		}
		Ext.Msg.wait('正在保存，请稍后......', '提示');
		if (type == 'resolve') {
			operIds = mktTaskAssignResolveForm.form.findField('operObjId')
					.getValue();
			operNames = mktTaskAssignResolveForm.form.findField('operObjName')
					.getValue();
			Ext.Ajax.request({
						url : basepath + '/marketMainAssuAction!saveTask.json',
						params : {
							targetIds : mktTaskAssignResolveForm.form
									.findField('targetSelectId').getValue(),
							targetValueData : targetDataValue,
							operIds : operIds,
							operNames : operNames
						},
						method : 'POST',
						form : mktTaskAssignResolveForm.getForm().id,
						success : function() {
							Ext.Msg.alert('提示', '操作成功！');
							mktTaskAssignResolveWindow.hide();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON
									.decode(response.status);
							if (resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							} else {
								Ext.Msg.alert('提示', '操作失败，失败原因：'
												+ response.responseText);
							}
							mktTaskAssignResolveWindow.hide();
						}
					});
		}
		if (type == 'adjust') {
			Ext.Ajax.request({
						url : basepath
								+ '/marketMainAssuAction!updateTask.json',
						params : {
							targetIds : mktTaskAssignResolveForm.form
									.findField('targetSelectId').getValue(),
							targetValueData : targetDataValue
						},
						method : 'POST',
						form : mktTaskAssignResolveForm.getForm().id,
						success : function() {
							Ext.Msg.alert('提示', '操作成功！');
							mktTaskAssignResolveWindow.hide();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON
									.decode(response.status);
							if (resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							} else {
								Ext.Msg.alert('提示', '操作失败，失败原因：'
												+ response.responseText);
							}
							mktTaskAssignResolveWindow.hide();
						}
					});
		}

	}

	// 定义营销任务分解窗口
	var mktTaskAssignResolveWindow = new Ext.Window({
		title : '营销任务分解',
		// plain : true,
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
		items : [mktTaskAssignResolvePanel],
		listeners : {
			beforehide : function() {
				store.reload({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});
			},
			beforeshow : function() {
				var selectRe = listPanel.getSelectionModel().getSelections()[0];
				Ext.Ajax.request({
					url : basepath
							+ '/marketTaskAssignAction!queryTaskOperAndTarget.json',
					method : 'POST',
					params : {
						taskId : selectRe.data.taskId,
						queryType : 'ownTask'
					},
					waitMsg : '正在查询数据,请等待...',
					success : function(response) {
						var rt = response.responseText;
						// alert(rt);
						var operIds = Ext.util.JSON.decode(rt).operIds;// 执行对象ID
						var operNames = Ext.util.JSON.decode(rt).operNames;// 执行对象名称
						var targetIds = Ext.util.JSON.decode(rt).targetIds;// 任务指标ID
						var targetNames = Ext.util.JSON.decode(rt).targetNames;// 任务指标名称
						var operObjType = Ext.util.JSON.decode(rt).operObjType;// 子任务执行对象类型

						// 设置“执行对象类型”
						mktTaskAssignResolveForm.form.findField('distTaskType')
								.setValue(operObjType);

						// “执行对象”设置值处理逻辑
						if (operObjType == "1") {
							// 执行对象为“机构”
							Ext.getCmp('mktTaskAssignResolveOperUser')
									.setVisible(false);
							Ext.getCmp('mktTaskAssignResolveOperOrg')
									.setVisible(true);
							mktTaskAssignResolveForm.getForm()
									.findField("operObjId1").setValue(operIds);
							mktTaskAssignResolveForm.getForm()
									.findField("operObjName1")
									.setValue(operNames);
						} else if (operObjType == "2") {
							// 执行对象为“客户经理”
							Ext.getCmp('mktTaskAssignResolveOperUser')
									.setVisible(true);
							Ext.getCmp('mktTaskAssignResolveOperOrg')
									.setVisible(false);
							mktTaskAssignResolveForm.getForm()
									.findField("OPER_USER").setValue(operIds);
							mktTaskAssignResolveForm.getForm()
									.findField("CUST_MANAGER")
									.setValue(operNames);
						}
						// “任务指标”设置值处理逻辑
						mktTaskAssignResolveForm.getForm()
								.findField("targetSelectId")
								.setValue(targetIds);
						mktTaskAssignResolveForm.getForm()
								.findField("targetSelect")
								.setValue(targetNames);
						// 给隐藏的“执行对象ID”、“执行对象名称”赋值
						mktTaskAssignResolveForm.getForm()
								.findField("operObjId").setValue(operIds);
						mktTaskAssignResolveForm.getForm()
								.findField("operObjName").setValue(operNames);
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
				mktTaskResolveMainStore.load();
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
						mktTaskAssignResolveWindow.hide();
					}
				}]
	});

	// 展示分解窗口
	function resolveInit() {
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
			if (tempSign != '执行中') {
				Ext.Msg.alert('提示', '只能分解【执行中】状态的任务！');
				return false;
			}
			mktTaskAssignResolveForm.getForm().loadRecord(selectRe);
			var distTaskType = mktTaskAssignResolveForm.form
					.findField('distTaskType').getValue();
			if (distTaskType == "1") {
				// 执行对象为“机构”
				Ext.getCmp('mktTaskAssignResolveOperUser').setVisible(false);
				Ext.getCmp('mktTaskAssignResolveOperOrg').setVisible(true);
			} else if (distTaskType == "2") {
				Ext.Msg.alert('提示', '该任务已分解到客户经理不能再分解！');
				return false;
				// 执行对象为“客户经理”
				Ext.getCmp('mktTaskAssignResolveOperUser').setVisible(true);
				Ext.getCmp('mktTaskAssignResolveOperOrg').setVisible(false);
			}
			makeResolveTargetGridOld();
		}
		// 将指标和执行对象置空
		mktTaskAssignResolveForm.form.findField('operObjName1').setValue('');
		mktTaskAssignResolveForm.form.findField('CUST_MANAGER').setValue('');
		// Ext.getCmp('targetResolveSelect').setVisible(false);
		mktTaskAssignResolveWindow.show();
		mktTaskAssignResolveWindow.setTitle('营销任务分解');
		mktTaskAssignResolvePanel.get(0).show();//显示基本信息
		mktTaskAssignResolveWindow.setHeight(480);
		mktTaskAssignResolveWindow.setWidth(1000);
		type = "resolve";
	}
	// 任务下达
	function transTask(listPanel_in, store_in) {
		var checkedNodes = listPanel_in.getSelectionModel().selections.items;
		var selectLength = listPanel_in.getSelectionModel().getSelections().length;
		var ids = '';
		// 1、判断是否选择了要下达的任务
		if (selectLength < 1) {
			Ext.Msg.alert('提示', '请选择需要下达的记录！');
			return false;
		}
		// 2、判断选中的任务是否全是“执行中”状态
		for (var i = 0; i < checkedNodes.length; i++) {
			if (checkedNodes[i].data.taskStat != "执行中") {
				Ext.Msg.alert('提示', '只能下达【执行中】状态的营销任务！');
				return false;
			}
			ids += checkedNodes[i].data.taskId;

			if (i != checkedNodes.length - 1)
				ids += ",";

		}
		Ext.MessageBox.confirm('提示', '您确定要下达选中的任务吗？', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {
				return false;
			}
			Ext.Ajax.request({
						url : basepath + '/marketMainAssuAction!assuTrans.json',
						method : 'POST',
						params : {
							cbid : ids
						},
						waitMsg : '正在保存数据,请等待...',
						success : function() {
							Ext.Msg.alert('提示', '操作成功！');
							store_in.reload();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON
									.decode(response.status);
							if (resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							} else {

								Ext.Msg.alert('提示', '操作失败，失败原因：'
												+ response.responseText);
								store_in.reload();
							}
						}
					});

		});
	}
	// 任务调整
	function adjustTask() {
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
			if (tempSign != '已下达') {
				Ext.Msg.alert('提示', '只能调整【已下达】状态的任务！');
				return false;
			}
			mktTaskAssignResolveForm.getForm().loadRecord(selectRe);
			Ext.getCmp('mktTaskAssignResolveOperUser').setVisible(false);
			Ext.getCmp('mktTaskAssignResolveOperOrg').setVisible(false);
			makeResolveTargetGridOld();
		}
		Ext.getCmp('targetResolveSelect').setVisible(false);
		mktTaskAssignResolveWindow.show();
		mktTaskAssignResolvePanel.get(0).hide();//隐藏基本信息
		mktTaskAssignResolveWindow.setHeight(400);
		mktTaskAssignResolveWindow.setTitle('营销任务调整');
		type = "adjust";
	}

	// 关闭窗口的保存方法
	function saveValue() {
		// 任务指标记录ID
		var json0 = {
			'targetNo' : []
		};
		// 指标达成值
		var json1 = {
			'achieveValue' : []
		};
		// 指标达成率
		var json2 = {
			'achievePercent' : []
		};
		for (var i = 0; i < mktTaskCloseMainStore.getCount(); i++) {
			var temp = mktTaskCloseMainStore.getAt(i);
			// if (temp.data.achieveValue == 0) {
			// Ext.Msg.alert('提示', '指标' + temp.data.indexName + '达成值为0，请修改！');
			// return false;
			// } else {
			json0.targetNo.push(temp.data.targetNo);
			json1.achieveValue.push(temp.data.achieveValue);
			json2.achievePercent.push(temp.data.achievePercent);
			// }
		}
		Ext.Msg.wait('正在保存，请稍后......', '提示');
		Ext.Ajax.request({
					url : basepath + '/marketMainAssuAction!saveValues.json',
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					params : {
						'targetNo' : Ext.encode(json0),
						'achieveValue' : Ext.encode(json1),
						'achievePercent' : Ext.encode(json2),
						'taskId' : mktTaskAssignCloseForm.form
								.findField('taskId').getValue()
					},
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						store.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
						mktTaskAssignCloseWindow.hide();

						// Ext.Ajax.request({
						// url : basepath
						// + '/marketMainAssuAction!closeTask.json',
						// method : 'POST',
						// waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						// params : {
						// 'taskId' : mktTaskAssignCloseForm.form
						// .findField('taskId').getValue(),
						// 'type' : mktTaskAssignCloseForm.form
						// .findField('distTaskType')
						// .getValue()
						// },
						// success : function() {
						// Ext.Msg.alert('提示', '操作成功！');
						// store.reload({
						// params : {
						// start : 0,
						// limit : parseInt(pagesize_combo
						// .getValue())
						// }
						// });
						// mktTaskAssignCloseWindow.hide();
						// },
						// failure : function(response) {
						// Ext.Msg
						// .alert(
						// '提示',
						// '操作失败，失败原因：'
						// + response.responseText);
						// }
						// });
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '操作失败,失败原因:'
										+ response.responseText);
					}
				});

	}

	// 营销任务关闭窗口展示的from
	var mktTaskAssignClosePanel = new Ext.Panel({
				// layout : 'fit',
				autoScroll : true,
				items : [mktTaskAssignCloseForm, mktTaskCloseMainGrid]
			});

	// 定义营销任务关闭窗口
	var mktTaskAssignCloseWindow = new Ext.Window({
				title : '营销任务关闭',
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
				buttonAlign : 'center',
				border : false,
				items : [mktTaskAssignClosePanel],
				listeners : {
					beforehide : function() {
						store.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
					},
					beforeshow : function() {
						mktTaskCloseMainStore.load();
					}
				},
				buttons : [{
							text : '保存',
							id : 'save',
							handler : function() {
								saveValue();
							}
						}, {
							text : '关闭',
							handler : function() {
								mktTaskAssignCloseWindow.hide();
							}
						}]
			});

	// 关闭营销任务
	function closeInit(listPanel, store, pagesize_combo) {
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe = listPanel.getSelectionModel().getSelections()[0];
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
		} else {
			var tempStat = listPanel.getSelectionModel().selections.items[0].data.taskStat;
			if (tempStat != "执行中" && tempStat != "已下达") {
				Ext.Msg.alert('提示', '只能关闭【执行中】和【已下达】状态的任务！');
				return false;
			} else {
				// 判断子任务关闭情况
				taskId = listPanel.getSelectionModel().selections.items[0].data.taskId;
				Ext.Ajax.request({
							url : basepath
									+ '/marketassuinfo!ifExitSunTask.json',
							params : {
								'taskId' : taskId
							},
							method : 'GET',
							waitMsg : '正在查询数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : checkResult,
							failure : checkResult
						});
				function checkResult(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if (resultArray == 200 || resultArray == 201) {
						var ifExit = Ext.util.JSON
								.decode(response.responseText).ifExit;// 是否存在未关闭的子任务
						var names = Ext.util.JSON.decode(response.responseText).names;// 未关闭的子任务名字
						if (ifExit == 'yes') {
							Ext.Msg.alert('提示', '完成此操作需要先关闭以下子任务：<br>' + names);
						} else {
							// 根据执行对象类型判断是否打开指标填写窗口
							var distTaskType = listPanel.getSelectionModel().selections.items[0].data.distTaskType;
							if (distTaskType == '1') {// 机构直接关闭
								// 执行关闭任务的逻辑
								closeTask(listPanel, store, pagesize_combo);
							} else if (distTaskType == '2') {// 打开填写指标完成值窗口
								mktTaskCloseMainStore.load();
								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];
								mktTaskAssignCloseForm.getForm()
										.loadRecord(selectRe);
								mktTaskAssignCloseWindow.show();
							}

						}
					}
				};
			}
		}
	}
	// 营销任务详情窗口展示的from
	// var mktTaskAssignDetailPanel = new Ext.Panel({
	// autoScroll : true,
	// items : [mktTaskAssignDetailForm, mktTaskDetailMainGrid,
	// mktTaskDetailTargetGridPanel]
	// });
	// // 定义营销任务详情窗口
	// var mktTaskAssignDetailWindow = new Ext.Window({
	// title : '营销任务详情',
	// // plain : true,
	// layout : 'fit',
	// width : 1000,
	// height : 450,
	// resizable : true,
	// draggable : true,
	// closable : true,
	// DetailAction : 'hide',
	// modal : true, // 模态窗口
	// loadMask : true,
	// maximizable : true,
	// collapsible : true,
	// titleCollapse : true,
	// buttonAlign : 'center',
	// border : false,
	// items : [mktTaskAssignDetailPanel],
	// listeners : {
	// beforehide : function() {
	// store.reload({
	// params : {
	// start : 0,
	// limit : parseInt(pagesize_combo
	// .getValue())
	// }
	// });
	// }
	// },
	// buttons : [ {
	// text : '关闭',
	// handler : function() {
	// mktTaskAssignDetailWindow.hide();
	// }
	// }]
	// });
	// 详情
	function detailInit() {
		// var selectRe = listPanel.getSelectionModel().getSelections()[0];
		// mktTaskAssignDetailForm.getForm().loadRecord(selectRe);
		// mktTaskDetailMainStore.load();
		// //子任务分解信息
		// mktTaskDetailTargetStore.removeAll();
		// var queryUrl = basepath + '/marketTaskAssignAction!'
		// + 'getTargetDataHeader.json';
		// Ext.Ajax.request({
		// url : queryUrl,
		// method : 'POST',
		// waitMsg : '正在查询数据,请等待...',
		// params : {
		// taskId : mktTaskAssignDetailForm.form.findField('taskId')
		// .getValue()
		// },
		// success : function(response) {
		// var header = response.responseText;
		// doMakeHeaderOld1(header);
		// },
		// failure : function(response) {
		// Ext.Msg.alert('提示', '查询数据失败！');
		// }
		// });
		// mktTaskAssignDetailWindow.show();

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

	// 营销任务详情窗口展示的from
	var detailPlanPanel = new Ext.Panel({
				autoScroll : true,
				items : [mktTaskAssignViewForm, targetViewTap]
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

	// 页面布局
	var view = new Ext.Viewport({
				layout : 'fit',
				items : [{
							layout : 'border',
							items : [searchPanel, listPanel]
						}]
			});

});