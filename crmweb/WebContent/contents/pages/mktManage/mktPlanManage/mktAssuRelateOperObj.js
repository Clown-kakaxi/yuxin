/**
 * 营销管理->营销任务管理->营销任务：新增、修改营销任务时，执行对象操作界面JS文件；wzy；2013-06-06
 */
// 执行对象--个人
var operateUser = new Com.yucheng.crm.common.OrgUserManage({
	xtype : 'userchoose',
	fieldLabel : '执行人',
	labelStyle : 'text-align:right;',
	name : 'CUST_MANAGER',
	id : 'operateUser',
	hiddenName : 'OPER_USER',
	// searchRoleType:('127,47'), //指定查询角色属性
	searchType : 'SUBTREE',
	singleSelect : false,
	anchor : '98%',
	callback : function() {
		Ext.Ajax.request({
					url : basepath + '/marketassudetailinfo!saveOper.json',
					params : {
						'operUserId' : searchmktRelateOperObj.form
								.findField('OPER_USER').getValue(),
						'operUserName' : searchmktRelateOperObj.form
								.findField('CUST_MANAGER').getValue(),
						'taskId' : mktAssuInfoForm.form.findField('taskId')
								.getValue(),
						'distTaskType' : mktAssuInfoForm.form
								.findField('distTaskType').getValue()
					},
					method : 'POST',
					form : searchmktRelateOperObj.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						mktRelateOperObjStore.reload({
									params : {
										start : 0,
										limit : parseInt(mktRelateOperObj_combo
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
						mktRelateOperObjStore.reload({
									params : {
										start : 0,
										limit : parseInt(mktRelateOperObj_combo
												.getValue())
									}
								});
					}
				});
	}
});

// 执行对象--机构
var operateOrg = new Com.yucheng.bcrm.common.OrgField({
	searchType : 'SUBTREE',
	fieldLabel : '执行对象',
	labelStyle : 'text-align:right;',
	id : 'operateOrg', // 放大镜组件ID，用于在重置清空时获取句柄
	name : 'CUST_ORG',
	hiddenName : 'instncode', // 后台获取的参数名称
	anchor : '98%',
	checkBox : true, // 复选标志
	callback : function() {
		Ext.Ajax.request({
					url : basepath + '/marketassudetailinfo!saveOper.json',
					params : {
						'operUserId' : searchmktRelateOperObj.form
								.findField('instncode').getValue(),
						'operUserName' : searchmktRelateOperObj.form
								.findField('CUST_ORG').getValue(),
						'taskId' : mktAssuInfoForm.form.findField('taskId')
								.getValue(),
						'distTaskType' : mktAssuInfoForm.form
								.findField('distTaskType').getValue()
					},
					method : 'POST',
					form : searchmktRelateOperObj.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						mktRelateOperObjStore.reload({
									params : {
										start : 0,
										limit : parseInt(mktRelateOperObj_combo
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
						mktRelateOperObjStore.reload({
									params : {
										start : 0,
										limit : parseInt(mktRelateOperObj_combo
												.getValue())
									}
								});
					}
				});
	}
});

// 列表字段映射
var mktRelateOperObjRecord = Ext.data.Record.create([{
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
var mktRelateOperObjReader = new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root : 'json.data'
		}, mktRelateOperObjRecord);

// 数据查询store
var mktRelateOperObjStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/marketassudetailinfo.json',
						method : 'get'
					}),
			reader : mktRelateOperObjReader
		});

// 查询前设置查询条件
mktRelateOperObjStore.on('beforeload', function() {
			this.baseParams = {
				taskId : mktAssuInfoForm.form.findField('taskId').getValue(),
				querysign : 'oper_obj'
			};
		});

// 每页显示条数下拉选择框
var mktRelateOperObj_combo = new Ext.form.ComboBox({
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
			value : '20',
			resizable : true,
			width : 85
		});

// 改变每页显示条数reload数据
mktRelateOperObj_combo.on("select", function(comboBox) {
	mktRelateOperObjBbar.pageSize = parseInt(mktRelateOperObj_combo.getValue()), mktRelateOperObjStore
			.reload({
						params : {
							start : 0,
							limit : parseInt(mktRelateOperObj_combo.getValue())
						}
					});
});

// gridTable 底部工具栏
var mktRelateOperObjBbar = new Ext.PagingToolbar({
			pageSize : parseInt(mktRelateOperObj_combo.getValue()),
			store : mktRelateOperObjStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', mktRelateOperObj_combo]
		});

// 复选框
var mktRelateOperObjSm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var prod_rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 列表表头
var mktRelateOperObjColumns = new Ext.grid.ColumnModel({
			columns : [prod_rownum, mktRelateOperObjSm, {
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

// 执行对象选择（放大镜）
var searchmktRelateOperObj = new Ext.form.FormPanel({
			labelWidth : 80,
			labelAlign : 'right',
			frame : true,
			region : 'north',
			autoScroll : true,
			layout : 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [operateOrg, operateUser]
					}, {
						columnWidth : .1,
						layout : 'column',
						items : [{
									xtype : 'button',
									text : '删除执行对象',
									handler : function() {
										deleteExeObj();
									}
								}]
					}]
		});

// 删除执行对象
function deleteExeObj() {
	var checkedNodes = mktRelateOperObjGrid.getSelectionModel().selections.items;
	var selectLength = mktRelateOperObjGrid.getSelectionModel().getSelections().length;
	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请先选择需要删除的记录！');
		return false;
	} else {
		var tempSign = mktRelateOperObjGrid.getSelectionModel().selections.items[0].data.taskStat;
		var json = {
			'id' : []
		};
		for (var i = 0; i < checkedNodes.length; i++) {
			json.id.push(checkedNodes[i].data.id);
		}
		Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return;
					}
					Ext.Ajax.request({
								url : basepath
										+ '/marketassudetailinfo!deleteOper.json',
								method : 'POST',
								params : {
									cbid : Ext.encode(json),
									'operate' : 'delete'
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功！');
									mktRelateOperObjStore.reload();
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
									}
									mktRelateOperObjStore.reload();
								}
							});
				});
	}
}

// 执行对象列表
var mktRelateOperObjGrid = new Ext.grid.EditorGridPanel({
			store : mktRelateOperObjStore,
			frame : true,
			height : 300,
			cm : mktRelateOperObjColumns,
			region : 'center',
			sm : mktRelateOperObjSm,
			bbar : mktRelateOperObjBbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 页面布局
var mktRelateOperObjInfo = new Ext.Panel({
			autoScroll : true,
			id : 'info2',
			layout : 'fit',
			items : [{
						layout : 'form',
						border : false,
						items : [{
									region : 'north',
									height : 50,
									layout : 'fit',
									items : [searchmktRelateOperObj]
								}, {
									region : 'center',
									height : 340,
									layout : 'fit',
									items : [mktRelateOperObjGrid]
								}]
					}]
		});
