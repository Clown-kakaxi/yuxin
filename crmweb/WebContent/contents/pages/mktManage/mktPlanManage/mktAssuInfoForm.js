/**
 * 营销管理->营销任务管理->营销任务：新增营销任务form表单定义JS文件；wzy；2013-06-06
 */
// 定义新增营销任务from表单
var mktAssuInfoForm = new Ext.form.FormPanel({
			labelWidth : 100,
			height : 450,
			id : 'info1',
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
												width : '100',
												anchor : '90%'
											}, {
												name : 'taskName',
												xtype : 'textfield',
												fieldLabel : '*营销任务名称',
												width : '100',
												anchor : '90%',
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
												width : '100',
												anchor : '90%',
												allowBlank : false
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												width : 200,
												fieldLabel : '*任务开始时间',
												format : 'Y-m-d',
												allowBlank : false,
												name : 'taskBeginDate',
												anchor : '90%'
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
												width : '100',
												anchor : '90%'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'datefield',
												width : 200,
												format : 'Y-m-d',
												fieldLabel : '*任务结束时间',
												name : 'taskEndDate',
												allowBlank : false,
												anchor : '90%'
											}]
								}]
					}, {
						layout : 'form',
						buttonAlign : 'center',
						items : [{
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'memo',
									anchor : '95%'
								}]

					}],
			buttons : [{
						text : '保存',
						handler : function() {
							saveAssuData();
						}
					}, {
						text : '重置',
						handler : function() {
							mktAssuInfoForm.form.reset();
						}
					}]
		});

// 保存
function saveAssuData() {
	if (!mktAssuInfoForm.getForm().isValid()) {
		Ext.MessageBox.alert('提示', '输入有误，请检查输入项！');
		return false;
	};
	Ext.Msg.wait('正在保存，请稍后......', '提示');
	Ext.Ajax.request({
		url : basepath + '/marketassuinfo.json',
		params : {
			operate : 'add'
		},
		method : 'POST',
		form : mktAssuInfoForm.getForm().id,
		success : function() {
			Ext.Ajax.request({
				url : basepath + '/marketassuinfo!getPid.json',
				success : function(response) {
					var taskId = Ext.util.JSON.decode(response.responseText).pid;
					mktAssuInfoForm.form.findField('taskId').setValue(taskId);
					Ext.Msg.alert('提示', '操作成功！');
				}
			});
		},
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			} else {
				Ext.Msg.alert('提示', '操作失败，失败原因：' + response.responseText);
			}
		}
	});
}