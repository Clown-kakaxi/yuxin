/**
 * 营销管理->营销任务管理->营销任务：任务详情界面JS文件；wzy；2013-06-09
 */

// 任务详细展示-基本信息form表单定义
var detailPlanForm = new Ext.form.FormPanel({
			labelWidth : 140,
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
												fieldLabel : '营销任务ID',
												hidden : true,
												anchor : '90%'
											}, {
												name : 'taskName',
												xtype : 'textfield',
												fieldLabel : '营销任务名称',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '任务开始时间',
												name : 'taskBeginDate',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '任务下达时间',
												name : 'taskDistDate',
												anchor : '99%',
												disabled : true
											}, {
												store : operTypeStatStore,
												xtype : 'combo',
												resizable : true,
												name : 'distTaskType',
												hiddenName : 'distTaskType',
												fieldLabel : '执行对象类型',
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												emptyText : '请选择',
												selectOnFocus : true,
												anchor : '99%',
												disabled : true
											}, {
												name : 'createOrgName',
												xtype : 'textfield',
												fieldLabel : '创建机构',
												anchor : '99%',
												disabled : true
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												name : 'taskParentName',
												xtype : 'textfield',
												fieldLabel : '上级任务名称',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '任务结束时间',
												name : 'taskEndDate',
												anchor : '99%',
												disabled : true
											}, {
												name : 'distUserName',
												xtype : 'textfield',
												fieldLabel : '任务下达人',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '创建人',
												name : 'createUserName',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '最近维护时间',
												name : 'recentlyUpdateDate',
												anchor : '99%',
												disabled : true
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												store : taskTypeStatStore,
												xtype : 'combo',
												resizable : true,
												name : 'taskType',
												hiddenName : 'taskType',
												fieldLabel : '营销任务类型',
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												emptyText : '请选择',
												selectOnFocus : true,
												anchor : '99%',
												disabled : true
											}, {
												store : assuStatStore,
												xtype : 'combo',
												resizable : true,
												name : 'taskStat',
												hiddenName : 'taskStat',
												fieldLabel : '营销任务状态',
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												emptyText : '请选择',
												selectOnFocus : true,
												anchor : '99%',
												disabled : true
											}, {
												name : 'distOrgName',
												xtype : 'textfield',
												fieldLabel : '下达机构',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '创建时间',
												name : 'createDate',
												anchor : '99%',
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '最新维护人',
												name : 'recentlyUpdateName',
												anchor : '99%',
												disabled : true
											}]
								}

						]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [{
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'memo',
									anchor : '99%',
									disabled : true
								}]
					}]

		});

// =====执行对象定义
// 列表字段映射
var executorRecord = Ext.data.Record.create([{
			name : 'id',
			mapping : 'ID'
		}, {
			name : 'createDate',
			mapping : 'CREATE_DATE'
		}, {
			name : 'createUserId',
			mapping : 'CREATE_USER_ID'
		}, {
			name : 'createUserName',
			mapping : 'CREATE_USER_NAME'
		}, {
			name : 'operObjId',
			mapping : 'OPER_OBJ_ID'
		}, {
			name : 'operObjName',
			mapping : 'OPER_OBJ_NAME'
		}, {
			name : 'taskId',
			mapping : 'TASK_ID'
		}, {
			name : 'distTaskType',
			mapping : 'DIST_TASK_TYPE'
		}, {
			name : 'distTaskTypeOra',
			mapping : 'DIST_TASK_TYPE_ORA'
		}]);

// 读取jsonReader
var executorReader = new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root : 'json.data'
		}, executorRecord);

// 数据查询store
var executorStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/marketassudetailinfo.json',
						method : 'get'
					}),
			reader : executorReader
		});

// 查询前，设置查询条件
executorStore.on('beforeload', function() {
			this.baseParams = {
				taskId : detailPlanForm.form.findField('taskId').getValue(),
				querysign : 'oper_obj'
			};
		});

// 每页显示条数下拉选择框
var executor_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'],
								[100, '100条/页'], [250, '250条/页'],
								[500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '100',
			resizable : true,
			width : 85
		});

// 改变每页显示条数reload数据
executor_combo.on("select", function(comboBox) {
			executorBbar.pageSize = parseInt(executor_combo.getValue()), executorStore
					.reload({
								params : {
									start : 0,
									limit : parseInt(executor_combo.getValue())
								}
							});
		});

// gridTable 底部工具栏
var executorBbar = new Ext.PagingToolbar({
			pageSize : parseInt(executor_combo.getValue()),
			store : executorStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', executor_combo]
		});

// 定义自动当前页行号
var prod_rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 列表表头
var executorColumns = new Ext.grid.ColumnModel({
			columns : [prod_rownum, {
						header : 'ID',
						dataIndex : 'id',
						sortable : true,
						hidden : true
					}, {
						header : '执行对象ID',
						dataIndex : 'operObjId',
						sortable : true,
						hidden : true
					}, {
						header : '执行对象',
						dataIndex : 'operObjName',
						sortable : true,
						width : 300
					}, {
						header : '任务ID',
						dataIndex : 'taskId',
						sortable : true,
						width : 100,
						hidden : true
					}, {
						header : '执行对象类型',
						dataIndex : 'distTaskType',
						width : 200,
						sortable : true,
						hidden : true
					}, {
						header : '执行对象类型',
						dataIndex : 'distTaskTypeOra',
						width : 200,
						sortable : true
					}, {
						header : '创建人',
						dataIndex : 'createUserName',
						width : 200,
						sortable : true
					}, {
						header : '创建时间',
						dataIndex : 'createDate',
						width : 200,
						sortable : true
					}, {
						header : '创建人ID',
						dataIndex : 'createUserId',
						width : 100,
						sortable : true,
						hidden : true
					}]
		});

// 操作执行对象
var executorGrid = new Ext.grid.GridPanel({
			store : executorStore,
			frame : true,
			height : 300,
			cm : executorColumns,
			region : 'center',
			bbar : executorBbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// =====任务指标定义
// 字段映射
var targetRecord = Ext.data.Record.create([{
			name : 'taskId',
			mapping : 'TASK_ID'
		}, {
			name : 'indexId',
			mapping : 'INDEX_ID'
		}, {
			name : 'targetBh',
			mapping : 'TARGET_BH'
		}, {
			name : 'targetName',
			mapping : 'TARGET_NAME'
		}, {
			name : 'targetMark',
			mapping : 'TARGET_MARK'
		}, {
			name : 'targetClassname',
			mapping : 'TARGET_CLASSNAME'
		}]);

// 读取jsonReader
var targetReader = new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root : 'json.data'
		}, targetRecord);

// 查询store
var targetStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/marketassudetailinfo.json',
						method : 'get'
					}),
			reader : targetReader

		});

// 每页显示条数下拉选择框
var target_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'],
								[100, '100条/页'], [250, '250条/页'],
								[500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '100',
			resizable : true,
			width : 85
		});

// 指标信息store
targetStore.on('beforeload', function() {
			this.baseParams = {
				querysign : 'target',
				taskId : detailPlanForm.form.findField('taskId').getValue()
			};
		});

// 改变每页显示条数reload数据
target_combo.on("select", function(comboBox) {
			targetBbar.pageSize = parseInt(target_combo.getValue()), targetStore
					.reload({
								params : {
									start : 0,
									limit : parseInt(target_combo.getValue())
								}
							});
		});

// gridTable 底部工具栏
var targetBbar = new Ext.PagingToolbar({
			pageSize : parseInt(target_combo.getValue()),
			store : targetStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', target_combo]
		});

// 定义自动当前页行号
var target_rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 列表表头
var targetColumns = new Ext.grid.ColumnModel({
			columns : [target_rownum, {
						header : '任务ID',
						width : 150,
						align : 'left',
						hidden : true,
						dataIndex : 'taskId'
					}, {
						header : '指标ID',
						width : 150,
						align : 'left',
						hidden : true,
						dataIndex : 'indexId'
					}, {
						header : '指标编号',
						width : 150,
						align : 'left',
						dataIndex : 'targetBh',
						sortable : true
					}, {
						header : '指标名称',
						width : 250,
						align : 'left',
						dataIndex : 'targetName',
						sortable : true
					}, {
						header : '指标描述',
						width : 300,
						align : 'left',
						dataIndex : 'targetMark',
						sortable : true
					}, {
						header : '指标类别',
						width : 200,
						align : 'left',
						dataIndex : 'targetClassname',
						sortable : true
					}]
		});

var targetGrid = new Ext.grid.GridPanel({
			store : targetStore,
			frame : true,
			height : 300,
			cm : targetColumns,
			bbar : targetBbar
		});

// =====指标值定义
// 列表表头定义
var cm_targetMendData_view = new Ext.grid.ColumnModel([]);
// 字段映射定义
var targetMendDataRecord_view = Ext.data.Record.create([]);
// 读取jsonReader
var reader_targetMendData_view = new Ext.data.JsonReader();
// 列表记录序号
var rownum_targetMendData_view = new Ext.grid.RowNumberer({
			header : 'NO',
			width : 28
		});
// 查询store
var restfulStore_targetMendData_view = new Ext.data.Store();
// 指标个数
var targetCount_view = 0;

function makeHeader_view() {
	restfulStore_targetMendData_view.removeAll();
	var queryUrl = basepath + '/marketassudetailinfo!'
			+ 'getTargetDataHeader.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					taskId : detailPlanForm.form.findField('taskId').getValue()
				},
				success : function(response) {
					var header = response.responseText;
					doMakeHeader_view(header);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
}

function doMakeHeader_view(header) {
	if (header != null && header != "") {
		var propColumn = [rownum_targetMendData_view, {
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
		targetCount_view = targetArr.length;
		for (var i = 0; i < targetArr.length; i++) {
			var tempTargetObj = {
				header : targetArr[i],
				dataIndex : 'target_value_' + i,
				sortable : true,
				width : 120
			};
			propColumn.push(tempTargetObj);
			var tempTargetField = {
				name : 'target_value_' + i,
				mapping : 'TARGET_VALUE_' + i
			};
			propFieldName.push(tempTargetField);
		}
		targetMendDataRecord_view = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		reader_targetMendData_view = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, targetMendDataRecord_view);

		// 查询store
		restfulStore_targetMendData_view = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketassudetailinfo.json',
								method : 'get'
							}),
					reader : reader_targetMendData_view
				});
		// 重新设置列表（包括表头、字段映射等）
		targetMendDataGrid_view.reconfigure(restfulStore_targetMendData_view,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		restfulStore_targetMendData_view.on('beforeload', function() {
					this.baseParams = {
						querysign : 'targetData',
						taskId : detailPlanForm.form.findField('taskId')
								.getValue()
					};
				});
		// 查询列表数据
		restfulStore_targetMendData_view.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	}
}

// 指标列表
var targetMendDataGrid_view = new Ext.grid.EditorGridPanel({
			store : restfulStore_targetMendData_view,
			frame : true,
			height : 340,
			cm : cm_targetMendData_view,
			region : 'center',
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
