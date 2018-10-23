/**
 * 营销管理-->营销任务管理：任务下达和任务调整功能JS，wzy，2013-05-06
 */
// ===================================下达任务，支持批量下达==================================
function transTask(listPanel_in, store_in) {
	var checkedNodes = listPanel_in.getSelectionModel().selections.items;
	var selectLength = listPanel_in.getSelectionModel().getSelections().length;
	// 选中的任务ID对象
	var json = {
		'id' : []
	};
	// 1、判断是否选择了要下达的任务
	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要下达的记录！');
		return false;
	}
	// 2、判断选中的任务是否全是“暂存”状态，判断当前用户是否是任务创建人
	for (var i = 0; i < checkedNodes.length; i++) {
		// 2.1、判断状态
		if (checkedNodes[i].data.taskStat != "暂存") {
			Ext.Msg.alert('提示', '只能下达【暂存】状态的营销任务！');
			return false;
		}
		// 2.2、判断创建人是否是当前用户
		if (checkedNodes[i].data.createUser != __userId) {
			Ext.Msg.alert('提示', '您选择的任务中，有不是您创建的，不能下达！');
			return false;
		}
		json.id.push(checkedNodes[i].data.taskId);// 增加选中的任务ID
	}
	Ext.MessageBox.confirm('提示', '您确定要下达选中的任务吗？', function(buttonId) {
		if (buttonId.toLowerCase() == "no") {
			return false;
		}
		Ext.Ajax.request({
					url : basepath
							+ '/marketAssuTransAdjustAction!assuTrans.json',
					method : 'POST',
					params : {
						cbid : Ext.encode(json)
					},
					waitMsg : '正在保存数据,请等待...',
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						store_in.reload();
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

	});
}

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
	if (checkedNodes[0].data.taskStat != "执行中") {
		Ext.Msg.alert('提示', '只能调整【执行中】状态的营销任务！');
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
	makeAdjustHeader();
}

// 列表表头定义
var cm_targetAdjustData = new Ext.grid.ColumnModel([]);
// 字段映射定义
var targetAdjustDataRecord = Ext.data.Record.create([]);
// 读取jsonReader
var reader_targetAdjustData = new Ext.data.JsonReader();
// 列表记录序号
var rownum_targetAdjustData = new Ext.grid.RowNumberer({
			header : 'NO',
			width : 28
		});
// 查询store
var restfulStore_targetAdjustData = new Ext.data.Store();
// 指标个数
var targetAdjustCount = 0;

function makeAdjustHeader() {
	restfulStore_targetAdjustData.removeAll();
	var queryUrl = basepath + '/marketassudetailinfo!'
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
					doMakeAdjustHeader(header);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
}

function doMakeAdjustHeader(header) {
	if (header != null && header != "") {
		var propColumn = [rownum_targetAdjustData, {
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
		targetAdjustDataRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		reader_targetAdjustData = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, targetAdjustDataRecord);

		// 查询store
		restfulStore_targetAdjustData = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketassudetailinfo.json',
								method : 'get'
							}),
					reader : reader_targetAdjustData
				});
		// 重新设置列表（包括表头、字段映射等）
		targetAdjustDataGrid.reconfigure(restfulStore_targetAdjustData,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		restfulStore_targetAdjustData.on('beforeload', function() {
					this.baseParams = {
						querysign : 'targetData',
						taskId : taskId
					};
				});
		// 查询列表数据
		restfulStore_targetAdjustData.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	}
}

// 指标列表
var targetAdjustDataGrid = new Ext.grid.EditorGridPanel({
			store : restfulStore_targetAdjustData,
			frame : true,
			height : 380,
			cm : cm_targetAdjustData,
			region : 'center',
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

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
												items : [targetAdjustDataGrid]
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
							restfulStore_targetAdjustData.reload({
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
	var idValue = "";
	var targetDataValue = "";
	var tempValue = null;
	for (var i = 0; i < restfulStore_targetAdjustData.getCount(); i++) {
		temp = restfulStore_targetAdjustData.getAt(i);
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
		idValue += temp.data.id;
		if (i != restfulStore_targetAdjustData.getCount() - 1) {
			targetDataValue += ";";
			idValue += ",";
		}
	}
	Ext.Msg.wait('正在保存，请稍后......', '提示');
	Ext.Ajax.request({
				url : basepath + '/marketassudetailinfo!saveTargetData.json',
				method : 'POST',
				waitMsg : '正在保存数据,请等待...',
				params : {
					'idValue' : Ext.encode(idValue),
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
			title : '指标调整',
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
