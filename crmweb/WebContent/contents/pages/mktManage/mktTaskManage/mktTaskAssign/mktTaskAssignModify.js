/**
 * 营销管理->营销任务管理->营销任务：修改营销任务form表单定义JS文件；wzy；2013-06-16
 */

// 执行对象--机构
var mktTaskAssignModifyOperOrg = new Com.yucheng.bcrm.common.OrgField({
			searchType : 'SUBORGS',
			fieldLabel : '*机构',
			labelStyle : 'text-align:right;',
			id : 'mktTaskAssignModifyOperOrg', // 放大镜组件ID，用于在重置清空时获取句柄
			name : 'sysOrgName',
			hiddenName : 'sysOrgId', // 后台获取的参数名称
			anchor : '100%',
			checkBox : true, // 复选标志
			callback : function() {
				makeModifyTargetGridNew();
			}
		});

// 执行对象--客户经理
var mktTaskAssignModifyOperUser = new Com.yucheng.crm.common.OrgUserManage({
			xtype : 'userchoose',
			fieldLabel : '*客户经理',
			labelStyle : 'text-align:right;',
			id : 'mktTaskAssignModifyOperUser',
			name : 'custManagerName',
			hiddenName : 'custManagerId',
			// searchRoleType:('127,47'), //指定查询角色属性
			searchType : 'SUBTREE',
			singleSelect : false,
			anchor : '100%',
			callback : function() {
				makeModifyTargetGridNew();
			}
		});

// 指标放大镜对象定义
var mktTaskAssignModifyTargetSelect = new Com.yucheng.crm.common.IndexField({
			xtype : 'userchoose',
			fieldLabel : '*任务指标',
			id : 'targetModifySelect',
			name : 'targetSelect',
			hiddenName : 'targetSelectId',
			labelStyle : 'text-align:right;',
			singleSelect : false,
			anchor : '100%',
			callback : function() {
				makeModifyTargetGridNew();
			}
		});

// 定义修改营销任务form表单
var mktTaskAssignModifyForm = new Ext.form.FormPanel({
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
												fieldLabel : '营销任务Id',
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
												name : 'taskName',
												xtype : 'textfield',
												fieldLabel : '*营销任务名称',
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
														distTaskTypeModifyChange();
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
												name : 'taskBeginDate',
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
												anchor : '100%'
											}]
								}, {
									columnWidth : .34,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												format : 'Y-m-d',
												fieldLabel : '*任务结束时间',
												name : 'taskEndDate',
												allowBlank : false,
												anchor : '100%'
											}]
								}]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [mktTaskAssignModifyOperOrg,
								mktTaskAssignModifyOperUser]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [mktTaskAssignModifyTargetSelect]
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
function distTaskTypeModifyChange() {
	var distTaskType = mktTaskAssignModifyForm.form.findField('distTaskType')
			.getValue();
	if (distTaskType == "1") {
		// 执行对象为“机构”
		Ext.getCmp('mktTaskAssignModifyOperUser').setVisible(false);
		Ext.getCmp('mktTaskAssignModifyOperOrg').setVisible(true);
	} else if (distTaskType == "2") {
		// 执行对象为“客户经理”
		Ext.getCmp('mktTaskAssignModifyOperUser').setVisible(true);
		Ext.getCmp('mktTaskAssignModifyOperOrg').setVisible(false);
	}
}

// 列表字段映射
var mktTaskModifyTargetRecord = Ext.data.Record.create([]);
// 读取jsonReader
var mktTaskModifyTargetReader = new Ext.data.JsonReader();
// 数据查询store
var mktTaskModifyTargetStore = new Ext.data.Store();
// 定义自动当前页行号
var mktTaskModifyTargetRownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
// 列模型
var mktTaskModifyTargetColumns = new Ext.grid.ColumnModel([]);
// 指标个数
var targetModifyCount = 0;

// 指标表格
var mktTaskModifyTargetGridPanel = new Ext.grid.EditorGridPanel({
			title : '指标信息',
			store : mktTaskModifyTargetStore,
			frame : true,
			height : 200,
			cm : mktTaskModifyTargetColumns,
			region : 'center',
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 根据选择的执行对象和任务指标，组合成一个二维表格，填写指标的目标值
function makeModifyTargetGridOld() {
	var queryUrl = basepath + '/marketTaskAssignAction!'
			+ 'getTargetDataHeader.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					taskId : mktTaskAssignModifyForm.form.findField('taskId')
							.getValue()
				},
				success : function(response) {
					var header = response.responseText;
					doModifyMakeHeaderOld(header);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
};

function doModifyMakeHeaderOld(header) {
	if (header != null && header != "") {
		var propColumn = [mktTaskModifyTargetRownum, {
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
		targetModifyCount = targetArr.length;
		for (var i = 0; i < targetArr.length; i++) {
			var tempTargetObj = {
				header : targetArr[i],
				dataIndex : 'target_value_' + i,
				sortable : true,
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
		mktTaskModifyTargetRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		mktTaskModifyTargetReader = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, mktTaskModifyTargetRecord);

		// 查询store
		mktTaskModifyTargetStore = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketTaskAssignAction.json',
								method : 'get'
							}),
					reader : mktTaskModifyTargetReader
				});
		// 重新设置列表（包括表头、字段映射等）
		mktTaskModifyTargetGridPanel.reconfigure(mktTaskModifyTargetStore,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		mktTaskModifyTargetStore.on('beforeload', function() {
					this.baseParams = {
						querySign : 'queryHadTargetData',
						taskId : mktTaskAssignModifyForm.form
								.findField('taskId').getValue()
					};
				});
		// 查询列表数据
		mktTaskModifyTargetStore.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	} else {
		mktTaskViewTargetGridPanel.reconfigure(mktTaskModifyTargetStore,
				new Ext.grid.ColumnModel([]));
	}
}

// 根据选择的执行对象和任务指标，组合成一个二维表格，填写指标的目标值
function makeModifyTargetGridNew() {
	var targetIds = null;// 指标ID集合
	var targetNames = null;// 指标名称集合
	var operIds = null;// 执行对象ID集合
	var operNames = null;// 执行对象名称集合
	var oForm = mktTaskAssignModifyForm.form;// 本页面（新增页面）新增表单Form对象
	var distTaskType = oForm.findField('distTaskType').getValue();// 执行对象类型
	targetIds = oForm.findField('targetSelectId').getValue();
	targetNames = oForm.findField('targetSelect').getValue();
	if (distTaskType == "1") {
		operIds = oForm.findField('sysOrgId').getValue();
		operNames = oForm.findField('sysOrgName').getValue();
	} else {
		operIds = oForm.findField('custManagerId').getValue();
		operNames = oForm.findField('custManagerName').getValue();
	}
	// 给隐藏的“执行对象ID”、“执行对象名称”赋值
	oForm.findField("operObjId").setValue(operIds);
	oForm.findField("operObjName").setValue(operNames);
	// 如果执行对象或者任务指标有为空的，不生成任务指标表格
	if (operIds == null || operIds == "" || targetIds == null
			|| targetIds == "") {
		return false;
	}
	var propColumn = [mktTaskModifyTargetRownum, {
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
	targetModifyCount = targetNameArr.length;
	for (var i = 0; i < targetModifyCount; i++) {
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
	mktTaskModifyTargetRecord = Ext.data.Record.create(propFieldName);
	// 读取jsonReader
	mktTaskModifyTargetReader = new Ext.data.JsonReader({
				root : 'rows',
				totalProperty : 'num'
			}, mktTaskModifyTargetRecord);
	// 查询store
	mktTaskModifyTargetStore = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/marketassudetailinfo.json',
							method : 'get'
						}),
				reader : mktTaskModifyTargetReader
			});
	// 重新设置列表（包括表头、字段映射等）
	mktTaskModifyTargetGridPanel.reconfigure(mktTaskModifyTargetStore,
			new Ext.grid.ColumnModel(propColumn));
	mktTaskModifyTargetStore.loadData(makeModifyTargetData(operIds, operNames,
			targetIds));
}

// 构造数据对象
function makeModifyTargetData(operIds, operNames, targetIds) {
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