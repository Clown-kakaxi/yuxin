// 定义关闭营销任务from表单
var mktTaskAssignCloseForm = new Ext.form.FormPanel({
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

var mktTaskCloseMainColumns = new Ext.grid.ColumnModel([{
			header : '编号',
			width : 130,
			align : 'left',
			hidden : true,
			dataIndex : 'targetNo',
			sortable : true
		}, {
			header : '指标编号',
			width : 150,
			align : 'left',
			dataIndex : 'targetCode',
			sortable : true
		}, {
			header : '指标名称',
			width : 250,
			align : 'left',
			dataIndex : 'indexName',
			sortable : true
		}, {
			header : '目标值',
			width : 180,
			align : 'right',
			dataIndex : 'targetValue',
			sortable : true
		}, {
			header : '达成值',
			width : 180,
			align : 'right',
			dataIndex : 'achieveValue',
			editor : new Ext.form.Field(),
			sortable : true
		}, {
			header : '达成率(%)',
			width : 180,
			align : 'right',
			dataIndex : 'achievePercent',
			sortable : true
		}]);

var mktTaskCloseMainStore = new Ext.data.Store({
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
							}, {
								name : 'achieveValue',
								mapping : 'ACHIEVE_VALUE'
							}, {
								name : 'achievePercent',
								mapping : 'ACHIEVE_PERCENT'
							}])
		});

mktTaskCloseMainStore.on('beforeload', function() {
			this.baseParams = {
				querySign : 'searchTarget',
				taskId : mktTaskAssignCloseForm.form.findField('taskId')
						.getValue()
			};
		});

// 本任务指标表格
var mktTaskCloseMainGrid = new Ext.grid.EditorGridPanel({
			autoScroll : true,
			title : '本任务指标信息',
			store : mktTaskCloseMainStore,
			frame : true,
			id : 'listPanel',
			cm : mktTaskCloseMainColumns,
			height : 500,
			region : 'center',
			stripeRows : true,
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

mktTaskCloseMainGrid.on("afteredit", this.afterEdit, this);

function afterEdit(obj) {
	var r = obj.record;
	var targetValue = r.get("targetValue");
	var achieveValue = r.get("achieveValue");
	if (achieveValue == null || achieveValue == '') {// 如果没有填，都写0
		r.set("achieveValue", 0);
		r.set("achievePercent", 0);
	} else if (targetValue != 0) {// 如果填了值，当目标为0时，达成率为0，否则计算
		var digit = Math.pow(10, 4);
		var p = Math.round((achieveValue / targetValue) * digit) / digit * 100;
		r.set("achievePercent", p);
	} else {
		r.set("achievePercent", 0);
	}
}

// 关闭任务
function closeTask(listPanel_in, store_in, pagesize_combo_in) {
	Ext.MessageBox.confirm('提示', '确定要关闭选中的任务吗？', function(buttonId) {
		if (buttonId.toLowerCase() == "no") {
			return;
		}
		Ext.Ajax.request({
			url : basepath + '/marketMainAssuAction!closeTask.json',
			method : 'POST',
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			params : {
				'taskId' : listPanel_in.getSelectionModel().selections.items[0].data.taskId,
				'type' : listPanel_in.getSelectionModel().selections.items[0].data.distTaskType
			},
			success : function() {
				Ext.Msg.alert('提示', '操作成功！');
				store_in.reload({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo_in.getValue())
							}
						});
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '操作失败，失败原因：' + response.responseText);
			}
		});
	});
}
//
// var mktTaskAssignDetailForm = new Ext.form.FormPanel({
// title : '基本信息',
// labelWidth : 100,
// height : 215,
// frame : true,
// labelAlign : 'right',
// region : 'center',
// autoScroll : true,
// buttonAlign : "center",
// items : [{
// layout : 'column',
// items : [{
// columnWidth : .33,
// layout : 'form',
// items : [{
// name : 'taskId',
// xtype : 'textfield',
// fieldLabel : '*营销任务Id',
// hidden : true,
// anchor : '100%'
// }, {
// name : 'taskName',
// xtype : 'textfield',
// fieldLabel : '*营销任务名称',
// readOnly:true,
// anchor : '100%',
// allowBlank : false
// }, {
// store : operTypeStatStore,
// xtype : 'combo',
// resizable : true,
// name : 'distTaskType',
// hiddenName : 'distTaskType',
// fieldLabel : '*执行对象类型',
// valueField : 'key',
// displayField : 'value',
// mode : 'local',
// editable : false,
// typeAhead : true,
// forceSelection : true,
// triggerAction : 'all',
// emptyText : '请选择',
// selectOnFocus : true,
// anchor : '100%',
// allowBlank : false
//										
// }]
// }, {
// columnWidth : .33,
// layout : 'form',
// items : [{
// xtype : 'datefield',
// fieldLabel : '*任务开始时间',
// format : 'Y-m-d',
// allowBlank : false,
// readOnly:true,
// name : 'taskBeginDate',
// anchor : '100%'
// }, {
// store : taskTypeStatStore,
// xtype : 'combo',
// resizable : true,
// name : 'taskType',
// hiddenName : 'taskType',
// fieldLabel : '营销任务类型',
// readOnly:true,
// valueField : 'key',
// displayField : 'value',
// mode : 'local',
// editable : false,
// typeAhead : true,
// forceSelection : true,
// triggerAction : 'all',
// emptyText : '请选择',
// selectOnFocus : true,
// anchor : '100%'
// }]
// }, {
// columnWidth : .34,
// layout : 'form',
// items : [{
// xtype : 'datefield',
// format : 'Y-m-d',
// fieldLabel : '*任务结束时间',
// readOnly:true,
// name : 'taskEndDate',
// allowBlank : false,
// anchor : '100%'
// }]
// }]
// },{
// layout : 'form',
// buttonAlign : 'center',
// items : [{
// xtype : 'textarea',
// fieldLabel : '备注',
// name : 'memo',
// anchor : '100%'
// }]
//
// }]
// });
// var mktTaskDetailMainColumns = new Ext.grid.ColumnModel([ {
// header : '编号',
// width : 130,
// align : 'left',
// hidden : true,
// dataIndex : 'targetNo',
// sortable : true
// },{
// header : '指标编号',
// width : 130,
// align : 'left',
// dataIndex : 'targetCode',
// sortable : true}
// ,{
// header : '指标名称',
// width : 130,
// align : 'left',
// dataIndex : 'indexName',
// sortable : true}
// ,{
// header : '目标值',
// width : 130,
// align : 'left',
// dataIndex : 'targetValue',
// sortable : true},
// {
// header : '达成值',
// width : 130,
// align : 'left',
// dataIndex : 'achieveValue',
// sortable : true}
// ,{
// header : '达成率',
// width : 130,
// align : 'left',
// dataIndex : 'achievePercent',
// sortable : true}]);
//
// var mktTaskDetailMainStore = new Ext.data.Store( {
// restful:true,
// proxy : new Ext.data.HttpProxy({url:basepath+'/marketMainAssuAction.json'
// }),
// reader: new Ext.data.JsonReader({
// totalProperty : 'json.count',
// root:'json.data'
// }, [{
// name : 'targetNo',
// mapping : 'TARGET_NO'
// },{
// name : 'targetCode',
// mapping : 'TARGET_CODE'
// },{
// name : 'indexName',
// mapping : 'INDEX_NAME'
// },{
// name : 'targetValue',
// mapping : 'TARGET_VALUE'
// },{
// name : 'achieveValue',
// mapping : 'ACHIEVE_VALUE'
// },{
// name : 'achievePercent',
// mapping : 'ACHIEVE_PERCENT'
// }
// ])
// });
//
// mktTaskDetailMainStore.on('beforeload', function() {
// this.baseParams = {
// querySign : 'searchTarget',
// taskId : mktTaskAssignDetailForm.form
// .findField('taskId').getValue()
// };
// });
//
// //本任务指标表格
// var mktTaskDetailMainGrid = new Ext.grid.EditorGridPanel({
// title : '本任务指标信息',
// store : mktTaskDetailMainStore,
// frame : true,
// id : 'listPanel',
// cm : mktTaskDetailMainColumns,
// height : 200,
// region : 'center',
// stripeRows : true,
// loadMask : {
// msg : '正在加载表格数据,请稍等...'
// }
// });
//
//
//
// //列表字段映射
// var mktTaskDetailTargetRecord = Ext.data.Record.create([]);
// // 读取jsonReader
// var mktTaskDetailTargetReader = new Ext.data.JsonReader();
// // 数据查询store
// var mktTaskDetailTargetStore = new Ext.data.Store();
// // 定义自动当前页行号
// var mktTaskDetailTargetRownum = new Ext.grid.RowNumberer({
// header : 'No.',
// width : 28
// });
// // 列模型
// var mktTaskDetailTargetColumns = new Ext.grid.ColumnModel([]);
// // 指标个数
// var targetDetailCount = 0;
//
// // 指标表格
// var mktTaskDetailTargetGridPanel = new Ext.grid.EditorGridPanel({
// title : '子任务指标分解信息',
// store : mktTaskDetailTargetStore,
// autoScroll : true,
// frame : true,
// height : 200,
// cm : mktTaskDetailTargetColumns,
// region : 'center',
// clicksToEdit : 1,
// loadMask : {
// msg : '正在加载表格数据,请稍等...'
// }
// });
// function doMakeHeaderOld1(header) {
// if (header != null && header != "") {
// var propColumn = [rownum_targetMendData, {
// header : '执行对象记录ID',
// dataIndex : 'id',
// sortable : true,
// hidden : true,
// width : 200
// }, {
// header : '执行对象',
// dataIndex : 'oper_obj_name',
// sortable : true,
// width : 200
// }];
// var propFieldName = [{
// name : 'id',
// mapping : 'ID'
// }, {
// name : 'oper_obj_name',
// mapping : 'OPER_OBJ_NAME'
// }];
// var targetArr = header.split(",");
// targetDetailCount = targetArr.length;
// for (var i = 0; i < targetArr.length; i++) {
// var tempTargetObj = {
// header : targetArr[i],
// dataIndex : 'target_value_' + i,
// sortable : true,
// width : 120,
// editor : new Ext.form.Field()
// };
// propColumn.push(tempTargetObj);
// var tempTargetField = {
// name : 'target_value_' + i,
// mapping : 'TARGET_VALUE_' + i
// };
// propFieldName.push(tempTargetField);
// }
// mktTaskDetailTargetRecord = Ext.data.Record.create(propFieldName);
// // 读取jsonReader
// mktTaskDetailTargetReader = new Ext.data.JsonReader({
// successProperty : 'success',
// idProperty : 'ID',
// totalProperty : 'json.count',
// root : 'json.data'
// }, mktTaskDetailTargetRecord);
//
// // 查询store
// mktTaskDetailTargetStore = new Ext.data.Store({
// restful : true,
// proxy : new Ext.data.HttpProxy({
// url : basepath + '/marketTaskAssignAction.json',
// method : 'get'
// }),
// reader : mktTaskDetailTargetReader
// });
// // 重新设置列表（包括表头、字段映射等）
// mktTaskDetailTargetGridPanel.reconfigure(mktTaskDetailTargetStore,
// new Ext.grid.ColumnModel(propColumn));
// // 指标信息store查询前设置查询条件
// mktTaskDetailTargetStore.on('beforeload', function() {
// this.baseParams = {
// querySign : 'queryHadTargetData',
// taskId : mktTaskAssignDetailForm.form
// .findField('taskId').getValue()
// };
// });
// // 查询列表数据
// mktTaskDetailTargetStore.reload({
// params : {
// start : 0,
// limit : 1000
// }
// });
// }
// }

