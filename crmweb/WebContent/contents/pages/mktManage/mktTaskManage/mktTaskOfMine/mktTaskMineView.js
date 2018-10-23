/**
 * 营销管理->营销任务管理->我的营销任务：营销任务详情form表单定义JS文件；wzy；2013-07-01
 */
// 定义营销任务详情form表单
var mktTaskAssignViewForm = new Ext.form.FormPanel({
			title : '基本信息',
			labelWidth : 100,
			height : 240,
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
												fieldLabel : '营销任务Id',
												hidden : true,
												anchor : '100%'
											}, {
												name : 'taskName',
												xtype : 'textfield',
												fieldLabel : '营销任务名称',
												anchor : '100%',
												disabled : true,
												allowBlank : false
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
												anchor : '100%',
												allowBlank : false,
												disabled : true
											}, {
												xtype : 'textfield',
												fieldLabel : '下达人',
												name : 'distUserName',
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '创建人',
												name : 'createUserName',
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '最近更新人',
												name : 'recentlyUpdateName',
												disabled : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												fieldLabel : '任务开始时间',
												format : 'Y-m-d',
												allowBlank : false,
												name : 'taskBeginDate',
												disabled : true,
												anchor : '100%'
											}, {
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
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '下达时间',
												name : 'taskDistDate',
												allowBlank : false,
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '创建时间',
												name : 'createDate',
												allowBlank : false,
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '最近更新时间',
												name : 'recentlyUpdateDate',
												allowBlank : false,
												disabled : true,
												anchor : '100%'
											}]
								}, {
									columnWidth : .34,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '任务结束时间',
												name : 'taskEndDate',
												allowBlank : false,
												disabled : true,
												anchor : '100%'
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
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '下达机构',
												name : 'distOrgName',
												disabled : true,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '创建机构',
												name : 'createOrgName',
												disabled : true,
												anchor : '100%'
											}]
								}]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [{
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'memo',
									disabled : true,
									anchor : '100%'
								}]

					}]
		});

// =====================本任务指标信息表格处理========================
// 列表字段映射
var mktTaskViewTargetRecord = Ext.data.Record.create([]);
// 读取jsonReader
var mktTaskViewTargetReader = new Ext.data.JsonReader();
// 数据查询store
var mktTaskViewTargetStore = new Ext.data.Store();
// 定义自动当前页行号
var mktTaskViewTargetRownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
// 列模型
var mktTaskViewTargetColumns = new Ext.grid.ColumnModel([]);
// 指标个数
var targetViewCount = 0;

// 指标表格
var mktTaskViewTargetGridPanel = new Ext.grid.GridPanel({
			store : mktTaskViewTargetStore,
			frame : true,
			height : 310,
			cm : mktTaskViewTargetColumns,
			region : 'center',
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 根据选择的执行对象和任务指标，组合成一个二维表格，填写指标的目标值
function makeViewTargetGridOld() {
	var queryUrl = basepath + '/marketTaskAssignAction!'
			+ 'getTargetDataHeader.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					taskId : mktTaskAssignViewForm.form.findField('taskId')
							.getValue(),
					queryType : 'ownTask'
				},
				success : function(response) {
					var header = response.responseText;
					doViewMakeHeaderOld(header);
					doViewSonMakeHeaderOld(header);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
};

function doViewMakeHeaderOld(header) {
	if (header != null && header != "") {
		var propColumn = [mktTaskViewTargetRownum, {
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
		targetViewCount = targetArr.length;
		for (var i = 0; i < targetArr.length; i++) {
			var tempTargetObj = {
				header : targetArr[i],
				dataIndex : 'target_value_' + i,
				sortable : true,
				align : 'right',
				width : 120
			};
			propColumn.push(tempTargetObj);
			var tempTargetField = {
				name : 'target_value_' + i,
				mapping : 'TARGET_VALUE_' + i
			};
			propFieldName.push(tempTargetField);
		}
		mktTaskViewTargetRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		mktTaskViewTargetReader = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, mktTaskViewTargetRecord);

		// 查询store
		mktTaskViewTargetStore = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketTaskAssignAction.json',
								method : 'get'
							}),
					reader : mktTaskViewTargetReader
				});
		// 重新设置列表（包括表头、字段映射等）
		mktTaskViewTargetGridPanel.reconfigure(mktTaskViewTargetStore,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		mktTaskViewTargetStore.on('beforeload', function() {
					this.baseParams = {
						querySign : 'queryHadTargetData',
						taskId : mktTaskAssignViewForm.form.findField('taskId')
								.getValue(),
						queryType : 'ownTask'
					};
				});
		// 查询列表数据
		mktTaskViewTargetStore.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	} else {
		mktTaskViewTargetGridPanel.reconfigure(mktTaskViewTargetStore,
				new Ext.grid.ColumnModel([]));
	}
}

// =====================子任务指标信息表格处理========================
function doViewSonMakeHeaderOld(header) {
	// 列表字段映射
	var mktTaskViewSonTargetRecord = Ext.data.Record.create([]);
	// 读取jsonReader
	var mktTaskViewSonTargetReader = new Ext.data.JsonReader();
	// 数据查询store
	var mktTaskViewSonTargetStore = new Ext.data.Store();
	// 定义自动当前页行号
	var mktTaskViewSonTargetRownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	// 第一层表头（指标名称）
	var topRow = [{
				header : '',
				colspan : 4,
				align : 'center'
			}];
	var targetGroup = null;
	var mktTaskViewSonTargetGridPanel = ({});

	if (header != null && header != "") {
		var propColumn = [mktTaskViewSonTargetRownum, {
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
				}, {
					header : '状态',
					dataIndex : 'task_stat',
					sortable : true,
					width : 80
				}];
		var propFieldName = [{
					name : 'id',
					mapping : 'ID'
				}, {
					name : 'oper_obj_name',
					mapping : 'OPER_OBJ_NAME'
				}, {
					name : 'task_stat',
					mapping : 'TASK_STAT_ORA'
				}];
		var targetArr = header.split(",");
		targetSonViewCount = targetArr.length;
		for (var i = 0; i < targetArr.length; i++) {
			var tempTargetObj = {
				header : '目标值',
				dataIndex : 'target_value_' + i,
				sortable : true,
				align : 'right',
				width : 80
			};
			propColumn.push(tempTargetObj);
			tempTargetObj = {
				header : '达成值',
				dataIndex : 'achieve_value_' + i,
				sortable : true,
				align : 'right',
				width : 80
			};
			propColumn.push(tempTargetObj);
			tempTargetObj = {
				header : '达成率(%)',
				dataIndex : 'achper_value_' + i,
				sortable : true,
				align : 'right',
				renderer : money('0000.00'),
				width : 80
			};
			propColumn.push(tempTargetObj);
			var tempTargetField = {
				name : 'target_value_' + i,
				mapping : 'TARGET_VALUE_' + i
			};
			propFieldName.push(tempTargetField);
			tempTargetField = {
				name : 'achieve_value_' + i,
				mapping : 'ACHIEVE_VALUE_' + i
			};
			propFieldName.push(tempTargetField);
			propFieldName.push(tempTargetField);
			tempTargetField = {
				name : 'achper_value_' + i,
				mapping : 'ACHPER_VALUE_' + i
			};
			propFieldName.push(tempTargetField);
			var tempTopRow = {
				header : targetArr[i],
				colspan : 3,
				align : 'center'
			};
			topRow.push(tempTopRow);
		}
		targetGroup = new Ext.ux.grid.ColumnHeaderGroup({
					rows : [topRow]
				})
		mktTaskViewSonTargetRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		mktTaskViewSonTargetReader = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, mktTaskViewSonTargetRecord);

		// 查询store
		mktTaskViewSonTargetStore = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketTaskAssignAction.json',
								method : 'get'
							}),
					reader : mktTaskViewSonTargetReader
				});
		// 重新构造子任务表头
		mktTaskViewSonTargetGridPanel = new Ext.grid.GridPanel({
					autoScroll : true,
					store : mktTaskViewSonTargetStore,
					frame : true,
					height : 310,
					columns : propColumn,
					region : 'center',
					plugins : targetGroup,
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
		// 如果有子任务列表了，先删除
		if (targetViewTap.get(1)) {
			targetViewTap.remove(1);
		}
		// 增加子任务列表
		targetViewTap.add([{
					title : '子任务指标',
					items : [mktTaskViewSonTargetGridPanel]
				}]);
		// 指标信息store查询前设置查询条件
		mktTaskViewSonTargetStore.on('beforeload', function() {
					this.baseParams = {
						querySign : 'queryHadTargetDataDetail',
						taskId : mktTaskAssignViewForm.form.findField('taskId')
								.getValue()
					};
				});
		// 查询数据
		mktTaskViewSonTargetStore.reload();
	} else {
		mktTaskViewSonTargetGridPanel = new Ext.grid.GridPanel({
					autoScroll : true,
					store : mktTaskViewSonTargetStore,
					frame : true,
					height : 310,
					columns : propColumn,
					region : 'center',
					plugins : targetGroup,
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
		mktTaskViewSonTargetGridPanel.reconfigure(mktTaskViewSonTargetStore,
				new Ext.grid.ColumnModel([]));
	}
}

// 本任务指标和子任务指标页签定义
var targetViewTap = new Ext.TabPanel({
			activeTab : 0,
			tabPosition : 'top',// 控制tab页签显示的位置（顶部：top；底部：bottom）
			height : 350,
			buttonAlign : "center",
			autoScroll : true,
			items : [{
						title : '本任务指标',
						items : [mktTaskViewTargetGridPanel]
					}]
		});