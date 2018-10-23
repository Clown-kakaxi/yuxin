/**
 * 营销管理->营销任务管理->营销任务：新增、修改营销任务时，指标操作界面JS文件；wzy；2013-06-06
 */
// 指标放大镜对象定义
var targetSelect = new Com.yucheng.crm.common.IndexField({
	xtype : 'userchoose',
	fieldLabel : '选择指标',
	id : 'targetSelect',
	name : 'targetSelect',
	hiddenName : 'targetSelectId',
	labelStyle : 'text-align:right;',
	singleSelect : false,
	anchor : '98%',
	callback : function() {
		Ext.Ajax.request({
					url : basepath + '/marketassudetailinfo!saveTarget.json',
					params : {
						'targetCode' : targetForm.form
								.findField('targetSelectId').getValue(),
						'taskId' : mktAssuInfoForm.form.findField('taskId')
								.getValue()
					},
					method : 'POST',
					form : searchmktRelateOperObj.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						restfulStore_target.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo_target
												.getValue())
									}
								});
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败，失败原因：'
											+ response.responseText);
						}
						restfulStore_target.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo_target
												.getValue())
									}
								});
					}
				});
	}
});

// 选择指标Form
var targetForm = new Ext.form.FormPanel({
			labelWidth : 80,
			labelAlign : 'right',
			frame : true,
			region : 'north',
			autoScroll : true,
			layout : 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [targetSelect]
					}, {
						columnWidth : .1,
						layout : 'column',
						items : [{
									xtype : 'button',
									text : '删除指标',
									handler : function() {
										deleteTarget();
									}
								}]
					}]
		});

// 删除指标
function deleteTarget() {
	var checkedNodes = targetSelectGrid.getSelectionModel().selections.items;
	var selectLength = targetSelectGrid.getSelectionModel().getSelections().length;
	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请先选择需要删除的记录！');
		return false;
	} else {
		var json = {
			'id' : []
		};
		var taskId = targetSelectGrid.getSelectionModel().selections.items[0].data.taskId;
		for (var i = 0; i < checkedNodes.length; i++) {
			json.id.push(checkedNodes[i].data.indexId);
		}
		Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return;
					}
					Ext.Ajax.request({
								url : basepath
										+ '/marketassudetailinfo!deleteTarget.json',
								method : 'POST',
								params : {
									cbid : Ext.encode(json),
									taskId : taskId,
									'operate' : 'delete'
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功！');
									restfulStore_target.reload();
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
										restfulStore_target.reload();
									}
								}
							});
				});
	}
}

// 列表中的复选框
var sm_target = new Ext.grid.CheckboxSelectionModel();

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
var reader_target = new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root : 'json.data'
		}, targetRecord);

// 查询store
var restfulStore_target = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/marketassudetailinfo.json',
						method : 'get'
					}),
			reader : reader_target
		});

// 列表记录序号
var rownum_target = new Ext.grid.RowNumberer({
			header : 'NO',
			width : 28
		});

// 列表表头
var cm_target = new Ext.grid.ColumnModel([rownum_target, sm_target, {
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
		}]);

// 分页下拉框
var pagesize_combo_target = new Ext.form.ComboBox({
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
			value : 20,
			editable : false,
			width : 85
		});

// 列表底部显示栏
var bbar_target = new Ext.PagingToolbar({
			pageSize : parseInt(pagesize_combo_target.getValue()),
			store : restfulStore_target,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', pagesize_combo_target]
		});

// 分页下拉框值改变事件
pagesize_combo_target.on("select", function(comboBox) {
	bbar_target.pageSize = parseInt(pagesize_combo_target.getValue()), restfulStore_target
			.reload({
						params : {
							start : 0,
							limit : parseInt(pagesize_combo_target.getValue())
						}
					});
});

// 指标信息store查询前设置查询条件
restfulStore_target.on('beforeload', function() {
			this.baseParams = {
				querysign : 'target',
				taskId : mktAssuInfoForm.form.findField('taskId').getValue()
			};
		});

// 指标列表
var targetSelectGrid = new Ext.grid.EditorGridPanel({
			store : restfulStore_target,
			frame : true,
			height : 300,
			cm : cm_target,
			region : 'center',
			sm : sm_target,
			bbar : bbar_target,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 布局
var targetPanel = new Ext.Panel({
			autoScroll : true,
			id : 'info3',
			layout : 'fit',
			items : [{
						layout : 'form',
						border : false,
						items : [{
									region : 'north',
									height : 50,
									layout : 'fit',
									items : [targetForm]
								}, {
									region : 'center',
									height : 340,
									layout : 'fit',
									items : [targetSelectGrid]
								}]
					}]
		});