/**
 * 营销管理-->营销任务管理-->营销任务下达：任务指标调整功能JS，wzy，2013-06-20
 */
// ======================================调整任务指标======================================
var taskId = null;// 任务ID
function adjustTask(listPanel_in, store_in) {
	var checkedNodes = listPanel_in.getSelectionModel().selections.items;
	var selectLength = listPanel_in.getSelectionModel().getSelections().length;
	// 1、判断是否选择了要调整指标的任务
	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要调整的记录！');
		return false;
	} else if (selectLength > 1) {
		Ext.Msg.alert('提示', '只能选择一条记录！');
		return false;
	}
	// 2、判断选中的任务是否全是“执行中”状态
	if (checkedNodes[0].data.taskStat != "已下达") {
		Ext.Msg.alert('提示', '只能调整【已下达】状态的营销任务！');
		return false;
	}
	// 3、判断创建人是否是当前用户
	if (checkedNodes[0].data.createUser != __userId) {
		Ext.Msg.alert('提示', '您不是任务的创建人，不能调整！');
		return false;
	}
	taskId = checkedNodes[0].data.taskId;// 选中的任务ID
	// 4、打开调整任务指标窗体
	taskTargetAdjustWindow.show();
	// 5、查询任务指标列表数据
	makeAdjustTargetGridOld(taskId);
}

// 列表字段映射
var mktTaskAdjustTargetRecord = Ext.data.Record.create([]);
// 读取jsonReader
var mktTaskAdjustTargetReader = new Ext.data.JsonReader();
// 数据查询store
var mktTaskAdjustTargetStore = new Ext.data.Store();
// 定义自动当前页行号
var mktTaskAdjustTargetRownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
// 列模型
var mktTaskAdjustTargetColumns = new Ext.grid.ColumnModel([]);
// 指标个数
var targetAdjustCount = 0;

// 指标表格
var mktTaskAdjustTargetGridPanel = new Ext.grid.EditorGridPanel({
			title : '指标信息',
			store : mktTaskAdjustTargetStore,
			frame : true,
			height : 200,
			cm : mktTaskAdjustTargetColumns,
			region : 'center',
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 根据选择的执行对象和任务指标，组合成一个二维表格，填写指标的目标值
function makeAdjustTargetGridOld(taskId) {
	mktTaskAdjustTargetStore.removeAll();
	var queryUrl = basepath + '/marketTaskAssignAction!'
			+ 'getTargetDataHeader.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					taskId : taskId
				},
				success : function(response) {
					var header = response.responseText;
					doAdjustMakeHeaderOld(header, taskId);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
};

// 构造列表表头并查询列表数据
function doAdjustMakeHeaderOld(header, taskId) {
	if (header != null && header != "") {
		var propColumn = [mktTaskAdjustTargetRownum, {
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
		targetAdjustCount = targetArr.length;
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
		mktTaskAdjustTargetRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		mktTaskAdjustTargetReader = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, mktTaskAdjustTargetRecord);

		// 查询store
		mktTaskAdjustTargetStore = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketTaskAssignAction.json',
								method : 'get'
							}),
					reader : mktTaskAdjustTargetReader
				});
		// 重新设置列表（包括表头、字段映射等）
		mktTaskAdjustTargetGridPanel.reconfigure(mktTaskAdjustTargetStore,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		mktTaskAdjustTargetStore.on('beforeload', function() {
					this.baseParams = {
						querySign : 'queryHadTargetData',
						taskId : taskId
					};
				});
		// 查询列表数据
		mktTaskAdjustTargetStore.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	} else {
		mktTaskViewTargetGridPanel.reconfigure(mktTaskAdjustTargetStore,
				new Ext.grid.ColumnModel([]));
	}
}

// 布局
var targetAdjustPanel = new Ext.Panel({
			layout : 'fit',
			items : [{
						layout : 'column',
						border : false,
						items : [{
									columnWidth : 1,
									layout : 'form',
									border : false,
									items : [{
												region : 'center',
												layout : 'fit',
												height : 380,
												items : [mktTaskAdjustTargetGridPanel]
											}]
								}]
					}],
			buttonAlign : 'center',
			buttons : [{
						text : '保存',
						handler : function() {
							saveTargetAdjustData();
						}
					}, {
						text : '重置',
						handler : function() {
							mktTaskAdjustTargetStore.reload({
										params : {
											start : 0,
											limit : 1000
										}
									});
						}
					}, {
						text : '关闭',
						handler : function() {
							taskTargetAdjustWindow.hide();
						}
					}]
		});

// 保存填写的指标（目标值）
function saveTargetAdjustData() {
	var temp = null;
	var targetDataValue = "";
	var tempValue = null;
	for (var i = 0; i < mktTaskAdjustTargetStore.getCount(); i++) {
		temp = mktTaskAdjustTargetStore.getAt(i);
		for (var j = 0; j < targetAdjustCount; j++) {
			tempValue = eval("temp.data.target_value_" + j);
			if (tempValue == null || tempValue == "") {
				tempValue = "0";
			}
			targetDataValue += tempValue;
			if (j != targetAdjustCount - 1) {
				targetDataValue += ",";
			}
		}
		if (i != mktTaskAdjustTargetStore.getCount() - 1) {
			targetDataValue += ";";
		}
	}
	Ext.Msg.wait('正在保存，请稍后......', '提示');
	Ext.Ajax.request({
				url : basepath
						+ '/marketTaskAssignAction!adjustTaskTargetData.json',
				method : 'POST',
				waitMsg : '正在保存数据,请等待...',
				params : {
					'targetDataValue' : Ext.encode(targetDataValue),
					'taskId' : taskId
				},
				success : function() {
					Ext.Msg.alert('提示', '操作成功！');
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '操作失败，失败原因：' + response.responseText);
				}
			});
}

// 定义任务指标调整窗口
var taskTargetAdjustWindow = new Ext.Window({
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
			constrain : true,
			items : [targetAdjustPanel]
		});
