var operIds = null;// 执行对象ID集合
var operNames = null;// 执行对象名称集合
// 执行对象--客户经理
var mktTaskAssignResolveOperUser = new Com.yucheng.crm.common.OrgUserManage({
			xtype : 'userchoose',
			fieldLabel : '*客户经理',
			labelStyle : 'text-align:right;',
			name : 'CUST_MANAGER',
			id : 'mktTaskAssignResolveOperUser',
			hiddenName : 'OPER_USER',
			// searchRoleType:('127,47'), //指定查询角色属性
			searchType : 'SUBTREE',
			singleSelect : false,
			anchor : '100%',
			callback : function() {
				makeResolveTargetGridNew();
			}
		});

// 执行对象--机构
var mktTaskAssignResolveOperOrg = new Com.yucheng.bcrm.common.OrgField({
			searchType : 'SUBORGS',
			fieldLabel : '*机构',
			labelStyle : 'text-align:right;',
			id : 'mktTaskAssignResolveOperOrg', // 放大镜组件ID，用于在重置清空时获取句柄
			name : 'operObjName1',
			hiddenName : 'operObjId1', // 后台获取的参数名称
			anchor : '100%',
			checkBox : true, // 复选标志
			callback : function() {
				makeResolveTargetGridNew();
			}
		});

// 指标放大镜对象定义
var mktTaskAssignResolveTargetSelect = new Com.yucheng.crm.common.IndexField({
			xtype : 'userchoose',
			fieldLabel : '*任务指标',
			id : 'targetResolveSelect',
			name : 'targetSelect',
			hiddenName : 'targetSelectId',
			labelStyle : 'text-align:right;',
			singleSelect : false,
			anchor : '100%',
			callback : function() {
				makeResolveTargetGridNew();
			}
		});

// 定义分解营销任务from表单
var mktTaskAssignResolveForm = new Ext.form.FormPanel({
			title : '基本信息',
			labelWidth : 100,
			height : 215,
			frame : true,
			labelAlign : 'right',
			region : 'center',
			autoScroll : true,
			buttonAlign : "center",
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												name : 'taskId',
												xtype : 'textfield',
												fieldLabel : '*营销任务Id',
												hidden : true,
												anchor : '100%'
											}, {
												name : 'taskName',
												xtype : 'textfield',
												fieldLabel : '*营销任务名称',
												disabled : true,
												anchor : '100%',
												allowBlank : false
											}, {
												store : operTypeStatStore,
												xtype : 'combo',
												resizable : true,
												name : 'distTaskType',
												hiddenName : 'distTaskType',
												fieldLabel : '*执行对象类型',
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												emptyText : '请选择',
												selectOnFocus : true,
												anchor : '100%',
												allowBlank : false,
												listeners : {
													'select' : function(combo) {
														distTaskTypeResolveChange();
													}
												}
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												fieldLabel : '*任务开始时间',
												format : 'Y-m-d',
												allowBlank : false,
												disabled : true,
												name : 'taskBeginDate',
												anchor : '100%'
											}, {
												store : taskTypeStatStore,
												xtype : 'combo',
												resizable : true,
												name : 'taskType',
												hiddenName : 'taskType',
												fieldLabel : '营销任务类型',
												disabled : true,
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												emptyText : '请选择',
												selectOnFocus : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .34,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '*任务结束时间',
												disabled : true,
												name : 'taskEndDate',
												allowBlank : false,
												anchor : '100%'
											}]
								}]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [mktTaskAssignResolveOperOrg,
								mktTaskAssignResolveOperUser]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						// items : [mktTaskAssignResolveTargetSelect]
						items : [{
									name : 'targetSelectId',
									xtype : 'textfield',
									fieldLabel : '任务指标ID',
									hidden : true,
									anchor : '100%'
								}, {
									name : 'operObjId',
									xtype : 'textfield',
									fieldLabel : '执行对象Id',
									hidden : true,
									anchor : '100%'
								}, {
									name : 'operObjName',
									xtype : 'textfield',
									fieldLabel : '执行对象名称',
									hidden : true,
									anchor : '100%'
								}, {
									name : 'targetSelect',
									xtype : 'textfield',
									fieldLabel : '任务指标',
									disabled : true,
									anchor : '100%',
									allowBlank : false
								}]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [{
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'memo',
									anchor : '100%'
								}]

					}]
		});

// “执行对象”下拉框选择值时，处理逻辑
function distTaskTypeResolveChange() {
	var distTaskType = mktTaskAssignResolveForm.form.findField('distTaskType')
			.getValue();
	if (distTaskType == "1") {
		// 执行对象为“机构”
		Ext.getCmp('mktTaskAssignResolveOperUser').setVisible(false);
		Ext.getCmp('mktTaskAssignResolveOperOrg').setVisible(true);
	} else if (distTaskType == "2") {
		// 执行对象为“客户经理”
		Ext.getCmp('mktTaskAssignResolveOperUser').setVisible(true);
		Ext.getCmp('mktTaskAssignResolveOperOrg').setVisible(false);
	}
}

var mktTaskResolveMainColumns = new Ext.grid.ColumnModel([{
			header : '编号',
			width : 130,
			align : 'left',
			hidden : true,
			dataIndex : 'targetNo',
			sortable : true
		}, {
			header : '指标编号',
			width : 200,
			align : 'left',
			dataIndex : 'targetCode',
			sortable : true
		}, {
			header : '指标名称',
			width : 300,
			align : 'left',
			dataIndex : 'indexName',
			sortable : true
		}, {
			header : '目标值',
			width : 200,
			align : 'left',
			dataIndex : 'targetValue',
			align : 'right',
			sortable : true
		}]);

var mktTaskResolveMainStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/marketMainAssuAction.json'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'json.count',
						root : 'json.data'
					}, [{
								name : 'targetNo',
								mapping : 'TARGET_NO'
							}, {
								name : 'targetCode',
								mapping : 'TARGET_CODE'
							}, {
								name : 'indexName',
								mapping : 'INDEX_NAME'
							}, {
								name : 'targetValue',
								mapping : 'TARGET_VALUE'
							}])
		});
mktTaskResolveMainStore.on('beforeload', function() {
			this.baseParams = {
				querySign : 'searchTarget',
				taskId : mktTaskAssignResolveForm.form.findField('taskId')
						.getValue()
			};
		});

// 本任务指标表格
var mktTaskResolveMainGrid = new Ext.grid.GridPanel({
			title : '本任务指标信息',
			store : mktTaskResolveMainStore,
			autoScroll : true,
			frame : true,
			cm : mktTaskResolveMainColumns,
			height : 120,
			region : 'center',
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 列表字段映射
var mktTaskResolveTargetRecord = Ext.data.Record.create([]);
// 读取jsonReader
var mktTaskResolveTargetReader = new Ext.data.JsonReader();
// 数据查询store
var mktTaskResolveTargetStore = new Ext.data.Store();
// 定义自动当前页行号
var mktTaskResolveTargetRownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
// 列模型
var mktTaskResolveTargetColumns = new Ext.grid.ColumnModel([]);
// 指标个数
var targetResolveCount = 0;

// 指标表格
var mktTaskResolveTargetGridPanel = new Ext.grid.EditorGridPanel({
			title : '子任务指标分解信息',
			store : mktTaskResolveTargetStore,
			autoScroll : true,
			frame : true,
			height : 200,
			cm : mktTaskResolveTargetColumns,
			region : 'center',
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 根据选择的执行对象和任务指标，组合成一个二维表格，填写指标的目标值
function makeResolveTargetGridOld() {
	mktTaskResolveTargetStore.removeAll();
	var queryUrl = basepath + '/marketTaskAssignAction!'
			+ 'getTargetDataHeader.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					taskId : mktTaskAssignResolveForm.form.findField('taskId')
							.getValue()
				},
				success : function(response) {
					var header = response.responseText;
					doMakeHeaderOld(header);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
};
var rownum_targetMendData = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

function doMakeHeaderOld(header) {
	if (header != null && header != "") {
		var propColumn = [rownum_targetMendData, {
					header : '执行对象记录ID',
					dataIndex : 'id',
					sortable : true,
					hidden : true,
					width : 200
				}, {
					header : '执行对象',
					dataIndex : 'oper_obj_name',
					sortable : true,
					width : 200
				}];
		var propFieldName = [{
					name : 'id',
					mapping : 'ID'
				}, {
					name : 'oper_obj_name',
					mapping : 'OPER_OBJ_NAME'
				}];
		var targetArr = header.split(",");
		targetResolveCount = targetArr.length;
		for (var i = 0; i < targetArr.length; i++) {
			var tempTargetObj = {
				header : targetArr[i],
				dataIndex : 'target_value_' + i,
				sortable : true,
				align : 'right',
				width : 120,
				editor : new Ext.form.Field()
			};
			propColumn.push(tempTargetObj);
			var tempTargetField = {
				name : 'target_value_' + i,
				mapping : 'TARGET_VALUE_' + i
			};
			propFieldName.push(tempTargetField);
		}
		mktTaskResolveTargetRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		mktTaskResolveTargetReader = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, mktTaskResolveTargetRecord);

		// 查询store
		mktTaskResolveTargetStore = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketTaskAssignAction.json',
								method : 'get'
							}),
					reader : mktTaskResolveTargetReader
				});
		// 重新设置列表（包括表头、字段映射等）
		mktTaskResolveTargetGridPanel.reconfigure(mktTaskResolveTargetStore,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		mktTaskResolveTargetStore.on('beforeload', function() {
					this.baseParams = {
						querySign : 'queryHadTargetData',
						taskId : mktTaskAssignResolveForm.form
								.findField('taskId').getValue()
					};
				});
		// 查询列表数据
		mktTaskResolveTargetStore.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	}
}

// 根据选择的执行对象和任务指标，组合成一个二维表格，填写指标的目标值
function makeResolveTargetGridNew() {
	var targetIds = '';// 指标ID集合
	var targetNames = '';// 指标名称集合

	var oForm = mktTaskAssignResolveForm.form;// 本页面（新增页面）新增表单Form对象
	var distTaskType = oForm.findField('distTaskType').getValue();// 执行对象类型

	// 用待分配的指标store来拼接指标信息
	targetAddCount = mktTaskResolveMainStore.getCount();
	for (var i = 0; i < mktTaskResolveMainStore.getCount(); i++) {
		temp = mktTaskResolveMainStore.getAt(i);
		targetIds += temp.data.targetCode;
		targetNames += temp.data.indexName;
		if (i != mktTaskResolveMainStore.getCount() - 1) {
			targetIds += ",";
			targetNames += ",";
		}
	}
	mktTaskAssignResolveForm.form.findField('targetSelectId')
			.setValue(targetIds);
	if (distTaskType == "1") {
		operIds = oForm.findField('operObjId1').getValue();
		operNames = oForm.findField('operObjName1').getValue();
	} else {
		operIds = oForm.findField('OPER_USER').getValue();
		operNames = oForm.findField('CUST_MANAGER').getValue();
	}
	mktTaskAssignResolveForm.form.findField('operObjId').setValue(operIds);
	mktTaskAssignResolveForm.form.findField('operObjName').setValue(operNames);
	// 如果执行对象或者任务指标有为空的，不生成任务指标表格
	if (operIds == null || operIds == "" || targetIds == null
			|| targetIds == "") {
		return false;
	}
	var propColumn = [mktTaskResolveTargetRownum, {
				header : '执行对象ID',
				dataIndex : 'obj_id',
				sortable : true,
				hidden : true,
				width : 200
			}, {
				header : '执行对象',
				dataIndex : 'oper_obj_name',
				sortable : true,
				width : 200
			}];
	var propFieldName = [{
				name : 'obj_id'
			}, {
				name : 'oper_obj_name'
			}];
	var targetNameArr = targetNames.split(",");
	targetResolveCount = targetNameArr.length;
	for (var i = 0; i < targetResolveCount; i++) {
		var tempTargetObj = {
			header : targetNameArr[i],
			dataIndex : 'target_value_' + i,
			sortable : true,
			width : 120,
			editor : new Ext.form.Field()
		};
		propColumn.push(tempTargetObj);
		var tempTargetField = {
			name : 'target_value_' + i
		};
		propFieldName.push(tempTargetField);
	}
	mktTaskResolveTargetRecord = Ext.data.Record.create(propFieldName);
	// 读取jsonReader
	mktTaskResolveTargetReader = new Ext.data.JsonReader({
				root : 'rows',
				totalProperty : 'num'
			}, mktTaskResolveTargetRecord);
	// 查询store
	mktTaskResolveTargetStore = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/marketassudetailinfo.json',
							method : 'get'
						}),
				reader : mktTaskResolveTargetReader
			});
	// 重新设置列表（包括表头、字段映射等）
	mktTaskResolveTargetGridPanel.reconfigure(mktTaskResolveTargetStore,
			new Ext.grid.ColumnModel(propColumn));
	mktTaskResolveTargetStore.loadData(makeResolveTargetData(operIds,
			operNames, targetIds));
}

// 构造数据对象
function makeResolveTargetData(operIds, operNames, targetIds) {
	var tempRowData = [];
	var tempColData = null;
	var operIdArr = operIds.split(",");
	var operNameArr = operNames.split(",");
	var targetIdArr = targetIds.split(",");
	for (var i = 0; i < operIdArr.length; i++) {
		tempColData = {
			"obj_id" : operIdArr[i],
			"oper_obj_name" : operNameArr[i]
		};
		for (var j = 0; j < targetIdArr.length; j++) {
			tempColData["target_value_" + j] = "";
		}
		tempRowData.push(tempColData);
	}
	var targetData = {
		rows : tempRowData
	};
	return targetData;
}