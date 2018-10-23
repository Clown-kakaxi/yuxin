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
	// 切换子面板
	function changePage(btn) {
		// 判断是否进行了基本信息的保存
		var tempId = mktAssuInfoForm.form.findField('taskId').getValue();
		if ('' == tempId || undefined == tempId) {
			Ext.Msg.alert('提示', '请先保存基本信息！');
			return false;
		}
		// mktAssuInfoPanel.layout.activeItem.id//获取当前步骤对象的ID，如info1
		var index = Number(mktAssuInfoPanel.layout.activeItem.id.substring(4));// 获取步骤序号
		if (btn.text == '上一步') {
			// 点击“上一步”按钮的处理
			index -= 1;
			if (index < 1) {
				index = 1;
			}
			mktAssuInfoWindow.setTitle('营销任务' + type + '-->第' + index + '步，共'
					+ totalStep + '步');
		} else {
			// 点击“下一步”按钮的处理
			index += 1;
			if (index == '2') {
				// 如果是第二步（选取执行对象），根据执行对象类型不同，显示不同的放大镜
				var distTaskType = mktAssuInfoForm.form
						.findField('distTaskType').getValue();
				if ('1' == distTaskType) {
					Ext.getCmp('operateUser').setVisible(false);
					Ext.getCmp('operateOrg').setVisible(true);
				} else if ('2' == distTaskType) {
					Ext.getCmp('operateUser').setVisible(true);
					Ext.getCmp('operateOrg').setVisible(false);
				}
				// 查询已有的执行对象列表数据
				mktRelateOperObjStore.reload({
							params : {
								start : 0,
								limit : parseInt(mktRelateOperObj_combo
										.getValue())
							}
						});
			} else if (index == '3') {
				// 判断是否选择了“执行对象”，如果没有，不能到下一步界面
				var selectLength = mktRelateOperObjGrid.getStore()
						.getTotalCount();
				if (selectLength < 1) {
					Ext.Msg.alert('提示', '请先选择执行对象！');
					return false;
				}
				// 如果是第三步，查询已有的指标列表数据
				restfulStore_target.reload({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo_target
										.getValue())
							}
						});
			} else if (index == '4') {
				// 判断是否选择了“任务指标”，如果没有，不能到下一步界面
				var selectLength = targetSelectGrid.getStore().getTotalCount();
				if (selectLength < 1) {
					Ext.Msg.alert('提示', '请先选择任务指标！');
					return false;
				}
				// 如果是第四步，查询指标初始值列表数据
				makeHeader();
			}
			mktAssuInfoWindow.setTitle('营销任务' + type + '-->第' + index + '步，共'
					+ totalStep + '步');
		}
		// 如果是第一步，那么“上一步”按钮不可用；否则，“上一步”按钮可用
		if (index == 1) {
			mktAssuInfoPanel.buttons[0].setDisabled(true);
		} else {
			mktAssuInfoPanel.buttons[0].setDisabled(false);
		}
		// 如果是第四步，那么“下一步”按钮不可用；否则，“下一步”按钮可用
		if (index == 4) {
			mktAssuInfoPanel.buttons[1].setDisabled(true);
		} else {
			mktAssuInfoPanel.buttons[1].setDisabled(false);
		}
		// 设置当前要显示的步骤对应的页面
		mktAssuInfoPanel.layout.setActiveItem('info' + index);
	};

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
					url : basepath + '/marketassuinfo.json',
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

	// 切换详情面板
	function changeDetailPage(btn) {
		if (btn.text == '上一步') {
			if (num != 0) {
				num -= 1;
			}
		}
		if (btn.text == '下一步') {
			if (num != 3) {
				num += 1;
			}
		}
		if (num == 0) {
			detailPlanWindow.setTitle("营销任务详情-->基本信息");
			detailPlanPanel.buttons[0].setDisabled(true);
			detailPlanPanel.buttons[1].setDisabled(false);
		}
		if (num == 1) {
			detailPlanWindow.setTitle("营销任务详情-->执行对象");
			executorStore.reload({
						params : {
							start : 0,
							limit : parseInt(executor_combo.getValue())
						}
					});
			detailPlanPanel.buttons[0].setDisabled(false);
			detailPlanPanel.buttons[1].setDisabled(false);

		}
		if (num == 2) {
			detailPlanWindow.setTitle("营销任务详情-->任务指标");
			targetStore.reload({
						params : {
							start : 0,
							limit : parseInt(target_combo.getValue())
						}
					});
			detailPlanPanel.buttons[0].setDisabled(false);
			detailPlanPanel.buttons[1].setDisabled(false);
		}
		if (num == 3) {
			detailPlanWindow.setTitle("营销任务详情-->指标值");
			makeHeader_view();
			detailPlanPanel.buttons[0].setDisabled(false);
			detailPlanPanel.buttons[1].setDisabled(true);
		}
		detailPlanPanel.getLayout().setActiveItem(num);

	};

	// 详细信息窗口展示的from
	var detailPlanPanel = new Ext.Panel({
				layout : "card",
				activeItem : 0,
				height : 450,
				autoScroll : true,
				layoutConfig : {
					animate : true
				},
				items : [detailPlanForm, executorGrid, targetGrid,
						targetMendDataGrid_view],
				buttonAlign : "center",
				buttons : [{
							text : '上一步',
							handler : changeDetailPage
						}, {
							text : '下一步',
							handler : changeDetailPage
						}, {
							text : '关闭',
							handler : function() {
								detailPlanWindow.hide();
							}
						}]
			});

	// 营销任务新增、修改窗口展示的from
	var mktAssuInfoPanel = new Ext.Panel({
		layout : 'card',
		activeItem : 0,
		autoScroll : true,
		buttonAlign : "center",
		items : [mktAssuInfoForm, mktRelateOperObjInfo, targetPanel, step4Panel],
		buttons : [{
					text : '上一步',
					handler : changePage
				}, {
					text : '下一步',
					handler : changePage
				}, {
					text : '关闭',
					handler : function() {
						mktAssuInfoWindow.hide();
					}
				}]
	});

	// 定义营销任务新增、修改窗口
	var mktAssuInfoWindow = new Ext.Window({
				title : '营销任务新增',
				plain : true,
				layout : 'fit',
				width : 1000,
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
				items : [mktAssuInfoPanel],
				listeners : {
					beforehide : function() {
						store.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
						restfulStore_target.removeAll();
					}
				}
			});

	// 定义详细信息窗口
	var detailPlanWindow = new Ext.Window({
				title : '营销任务详情-->基本信息',
				plain : true,
				layout : 'fit',
				width : 1000,
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
				border : false,
				items : [detailPlanPanel]
			});

	// 展示新增窗口
	function addInit() {
		type = '新增';
		mktAssuInfoWindow
				.setTitle('营销任务' + type + '-->第1步，共' + totalStep + '步');
		resetForm();
		mktAssuInfoWindow.show();
		// “上一步”按钮不可用
		mktAssuInfoPanel.buttons[0].setDisabled(true);
		// “下一步”按钮可用（修改操作，下一步到最后一步的界面时，“下一步”按钮被disabled，所以此处要重新启用）
		mktAssuInfoPanel.buttons[1].setDisabled(false);
		// 展示第一个tab页
		mktAssuInfoPanel.layout.setActiveItem('info1');
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
				}];
		mktAssuInfoForm.getForm().setValues(emptyObj);
	}

	// 展示修改窗口
	function editInit() {
		type = '修改';
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
			mktAssuInfoForm.getForm().loadRecord(selectRe);
			mktAssuInfoWindow.setTitle('营销任务' + type + '-->第1步，共' + totalStep
					+ '步');
		}
		mktAssuInfoWindow.show();
		mktAssuInfoPanel.buttons[0].setDisabled(true);// “上一步”按钮不可用
		mktAssuInfoPanel.buttons[1].setDisabled(false);// “下一步”按钮可用（修改操作，下一步到最后一步的界面时，“下一步”按钮被disabled，所以此处要重新启用）
		mktAssuInfoPanel.layout.setActiveItem('info1');
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
								url : basepath + '/marketassuinfo.json',
								method : 'POST',
								params : {
									cbid : Ext.encode(json),
									'operate' : 'delete'
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
	function closeInit(listPanel, store, pagesize_combo) {
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe = listPanel.getSelectionModel().getSelections()[0];
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
		} else {
			var tempOper = listPanel.getSelectionModel().selections.items[0].data.operUser;
			var tempStat = listPanel.getSelectionModel().selections.items[0].data.taskStat;
			// if (tempOper != __userId || tempStat != "执行中") {
			if (tempStat != "执行中") {
				// Ext.Msg.alert('提示',
				// '操作失败，可能原因：<br> 1:执行人不为操作者本人！<br> 2:该记录不是【执行中】状态！');
				Ext.Msg.alert('提示', '只能关闭【执行中】状态的任务！');
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
							// 打开填写指标完成值窗口，需要讨论是否填写指标达成值
							// taskCloseWindow.show();
							// 执行关闭任务的逻辑
							closeTask(listPanel, store, pagesize_combo);
						}
					}
				};
			}
		}
	}

	// 任务详情
	function detailInit() {
		var selectLength = listPanel.getSelectionModel().getSelections().length;
		var selectRe = listPanel.getSelectionModel().getSelections()[0];
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
		} else {
			num = 0;
			taskId = listPanel.getSelectionModel().selections.items[0].data.taskId;// 任务ID
			detailPlanForm.getForm().loadRecord(selectRe);// 加载数据
			detailPlanPanel.buttons[0].setDisabled(true);// “上一步”按钮不可用
			detailPlanPanel.buttons[1].setDisabled(false);// “下一步”按钮可用
			detailPlanWindow.show();
			detailPlanPanel.getLayout().setActiveItem(num);// 第一个步骤页面展示
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