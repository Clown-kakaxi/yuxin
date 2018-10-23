/**
 * 营销管理->营销任务管理->营销任务：填写指标值界面JS文件；wzy；2013-06-07
 */
// 列表表头定义
var cm_targetMendData = new Ext.grid.ColumnModel([]);
// 字段映射定义
var targetMendDataRecord = Ext.data.Record.create([]);
// 读取jsonReader
var reader_targetMendData = new Ext.data.JsonReader();
// 列表记录序号
var rownum_targetMendData = new Ext.grid.RowNumberer({
			header : 'NO.',
			width : 28
		});
// 查询store
var restfulStore_targetMendData = new Ext.data.Store();
// 指标个数
var targetCount = 0;

function makeHeader() {
	restfulStore_targetMendData.removeAll();
	var queryUrl = basepath + '/marketassudetailinfo!'
			+ 'getTargetDataHeader.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					taskId : mktAssuInfoForm.form.findField('taskId')
							.getValue()
				},
				success : function(response) {
					var header = response.responseText;
					doMakeHeader(header);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询数据失败！');
				}
			});
}

function doMakeHeader(header) {
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
		targetCount = targetArr.length;
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
		targetMendDataRecord = Ext.data.Record.create(propFieldName);
		// 读取jsonReader
		reader_targetMendData = new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					totalProperty : 'json.count',
					root : 'json.data'
				}, targetMendDataRecord);

		// 查询store
		restfulStore_targetMendData = new Ext.data.Store({
					restful : true,
					proxy : new Ext.data.HttpProxy({
								url : basepath + '/marketassudetailinfo.json',
								method : 'get'
							}),
					reader : reader_targetMendData
				});
		// 重新设置列表（包括表头、字段映射等）
		targetMendDataGrid.reconfigure(restfulStore_targetMendData,
				new Ext.grid.ColumnModel(propColumn));
		// 指标信息store查询前设置查询条件
		restfulStore_targetMendData.on('beforeload', function() {
					this.baseParams = {
						querysign : 'targetData',
						taskId : mktAssuInfoForm.form.findField('taskId')
								.getValue()
					};
				});
		// 查询列表数据
		restfulStore_targetMendData.reload({
					params : {
						start : 0,
						limit : 1000
					}
				});
	}
}

// 指标列表
var targetMendDataGrid = new Ext.grid.EditorGridPanel({
			store : restfulStore_targetMendData,
			frame : true,
			height : 340,
			cm : cm_targetMendData,
			region : 'center',
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 布局
var step4Panel = new Ext.Panel({
			id : 'info4',
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
												height : 340,
												items : [targetMendDataGrid]
											}]
								}]
					}],
			buttonAlign : 'center',
			buttons : [{
						text : '保存',
						handler : function() {
							saveTargetData();
						}
					}, {
						text : '重置',
						handler : function() {
							restfulStore_targetMendData.reload({
										params : {
											start : 0,
											limit : 1000
										}
									});
						}
					}]
		});

// 保存填写的指标（目标值）
function saveTargetData() {
	var temp = null;
	var idValue = "";
	var targetDataValue = "";
	var tempValue = null;
	for (var i = 0; i < restfulStore_targetMendData.getCount(); i++) {
		temp = restfulStore_targetMendData.getAt(i);
		for (var j = 0; j < targetCount; j++) {
			tempValue = eval("temp.data.target_value_" + j);
			if (tempValue == null || tempValue == "") {
				tempValue = "0";
			}
			targetDataValue += tempValue;
			if (j != targetCount - 1) {
				targetDataValue += ",";
			}
		}
		idValue += temp.data.id;
		if (i != restfulStore_targetMendData.getCount() - 1) {
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
					'taskId' : mktAssuInfoForm.form.findField('taskId')
							.getValue()
				},
				success : function() {
					Ext.Msg.alert('提示', '操作成功！');
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '操作失败，失败原因：' + response.responseText);
				}
			});
}