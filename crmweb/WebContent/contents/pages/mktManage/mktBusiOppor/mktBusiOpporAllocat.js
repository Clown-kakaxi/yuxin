/**
 * 营销管理->商机管理->商机池->商机分配 入口JS文件 wzy，2013-02-19
 */

// 定义 商机分配窗口 From表单
var busiOpportAllocatForm = new Ext.FormPanel(
		{
			labelWidth : 100,
			height : 250,
			frame : true,
			autoScroll : true,
			labelAlign : 'right',
			buttonAlign : "center",
			items : [ {
				layout : 'column',
				items : [
						{
							columnWidth : .3,
							layout : 'form',
							items : [
									{
										xtype : 'textfield',
										fieldLabel : '商机ID',
										name : 'opporId',
										hidden : true,
										anchor : '90%'
									},
									{
										xtype : 'radio',
										fieldLabel : '分配到机构',
										name : 'org_id_allocat',
										anchor : '90%',
										listeners : {
											"focus" : function() {
												busiOpportAllocatForm.form
														.findField(
																'assignOrgName')
														.setDisabled(false);
												busiOpportAllocatForm.form
														.findField(
																'executeUserName')
														.setDisabled(true);
												busiOpportAllocatForm.form
														.findField(
																'executeUserName')
														.setValue("");
												busiOpportAllocatForm.form
														.findField(
																'manager_id_allocat')
														.setValue(false);
											}
										}
									},
									{
										xtype : 'radio',
										fieldLabel : '分配到客户经理',
										name : 'manager_id_allocat',
										anchor : '90%',
										listeners : {
											"focus" : function() {
												busiOpportAllocatForm.form
														.findField(
																'assignOrgName')
														.setDisabled(true);
												busiOpportAllocatForm.form
														.findField(
																'executeUserName')
														.setDisabled(false);
												busiOpportAllocatForm.form
														.findField(
																'assignOrgName')
														.setValue("");
												busiOpportAllocatForm.form
														.findField(
																'org_id_allocat')
														.setValue(false);
											}
										}
									} ]
						},
						{
							columnWidth : .7,
							layout : 'form',
							items : [
									new Com.yucheng.bcrm.common.OrgField(
											{
												searchType : 'SUBTREE',// 指定查询机构范围属性，SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH（所有父、祖机构）ALLORG（所有机构）
												fieldLabel : '待分配机构',
												labelStyle : 'text-align:right;',
												name : 'assignOrgName',
												hiddenName : 'assignOgrId', // 后台获取的参数名称
												anchor : '90%',
												checkBox : false, // 复选标志,
												onTrigger2Click : function() {
													if (busiOpportAllocatForm.form
															.findField('assignOrgName').disabled) {
														return false;
													} else {
														Com.yucheng.bcrm.common.OrgField.prototype.onTrigger2Click
																.apply(this);// 调用父类的同名方法
													}
												}
											}),
									new Com.yucheng.crm.common.OrgUserManage(
											{
												xtype : 'userchoose',
												fieldLabel : '待分配客户经理',
												allowBlank : true,
												labelStyle : 'text-align:right;',
												name : 'executeUserName',
												hiddenName : 'executeUserId',
												searchType : 'SUBORGS',// 指定查询机构范围属性，SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH（所有父、祖机构）ALLORG（所有机构）
												singleSelect : true,// 控制是否只能单选
												anchor : '90%',
												onTrigger2Click : function() {
													if (busiOpportAllocatForm.form
															.findField('executeUserName').disabled) {
														return false;
													} else {
														Com.yucheng.crm.common.OrgUserManage.prototype.onTrigger2Click
																.apply(this);// 调用父类的同名方法
													}
												}
											}), {
										xtype : 'datefield',
										fieldLabel : '商机有效期',
										format : 'Y-m-d',
										editable : true,
										hidden : true,
										name : 'opporDueDate',
										anchor : '90%'
									} ]
						} ]
			} ],
			buttons : [
					{
						text : '分配',
						handler : function() {
							if (!busiOpportAllocatForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
								return false;
							}
							// 如果选择“分配到机构”，判断是否选择了“待分配机构”
							if (busiOpportAllocatForm.form
									.findField('org_id_allocat').checked) {
								var value = busiOpportAllocatForm.form
										.findField('assignOrgName').getValue();
								if (value == undefined || value == null
										|| value == "") {
									Ext.Msg.alert('提示', '请选择待分配机构！');
									return false;
								}
							}
							// 如果选择“分配到客户经理”，判断是否选择了“待分配客户经理”
							if (busiOpportAllocatForm.form
									.findField('manager_id_allocat').checked) {
								var value = busiOpportAllocatForm.form
										.findField('executeUserName')
										.getValue();
								if (value == undefined || value == null
										|| value == "") {
									Ext.Msg.alert('提示', '请选择待分配客户经理！');
									return false;
								}
							}
							// 如果“商机有效期”可见，那么不能早于当前日期
							if (!busiOpportAllocatForm.form
									.findField('opporDueDate').hidden) {
								var opporDueDate = busiOpportAllocatForm.form
										.findField('opporDueDate').getValue();
								if (opporDueDate != null && opporDueDate != "") {
									if (opporDueDate < new Date()) {
										Ext.Msg.alert('提示', '商机有效期不能早于今天！');
										return false;
									}
								} else {
									Ext.Msg.alert('提示', '商机有效期不能为空！');
									return false;
								}
							}
							var saveUrl = basepath
									+ '/mktBusiOpporOperationAction!'
									+ 'allocatBusiOppor.json';
							Ext.Ajax.request({
								url : saveUrl,
								method : 'POST',
								form : busiOpportAllocatForm.getForm().id,
								waitMsg : '正在保存数据,请等待...',
								success : function(response) {
									Ext.Msg.alert('提示', '分配成功！');
									store.load({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '分配失败！');
								}
							});
							busiOpportAllocatWindow.hide();
						}
					}, {
						text : '关闭',
						handler : function() {
							busiOpportAllocatWindow.hide();
						}
					} ]
		});

// 定义 商机分配 窗口
var busiOpportAllocatWindow = new Ext.Window({
	title : '商机分配',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 200,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ busiOpportAllocatForm ],
	listeners : {
		"hide" : function() {
			busiOpportAllocatForm.getForm().reset();
		},
		"show" : function() {// 窗体显示时间，进行一些数据设置初始化操作，默认设置成“分配到机构”
			busiOpportAllocatForm.form.findField('org_id_allocat').setValue(
					true);// “分配到机构”单选按钮选中
			busiOpportAllocatForm.form.findField('manager_id_allocat')
					.setValue(false);// “分配到客户经理”单选按钮不选中
			busiOpportAllocatForm.form.findField('assignOrgName').setDisabled(
					false);// “待分配机构”放大镜可以使用
			busiOpportAllocatForm.form.findField('executeUserName')
					.setDisabled(true);// “待分配客户经理”放大镜不能使用
			busiOpportAllocatForm.form.findField('assignOrgName').setValue("");// 设置待“分配机构”为空
			busiOpportAllocatForm.form.findField('executeUserName')
					.setValue("");// 设置“待分配客户经理”为空
		}
	}
});

// 打开 商机分配 窗口
function busiOpportAllocatWindowInit() {
	var record = listPanel.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要分配的商机！');
		return;
	}
	var checkedNodes = listPanel.getSelectionModel().selections.items;
	var oppor_stat = null;// 商机状态
	var opporIdS = '';// 选中的商机记录ID的集合
	var opporId = null;// 商机ID
	var had_6 = false;// 是否有选择“到期退回”的商机
	var had_no_6 = false;// 是否有选择非“到期退回”的商机
	var assignOrgName = null;// 执行机构名称
	var had_allocat_org = false;// 是否有“待分配到机构”的商机
	var had_allocat_no_org = false;// 是否有不是“待分配到机构”的商机
	var assignOrgId = null;// 待分配机构ID
	for ( var i = 0; i < checkedNodes.length; i++) {
		// 1、控制只能分配“待分配(1)”、“退回(5)”、“到期退回(6)”的商机
		oppor_stat = checkedNodes[i].data.OPPOR_STAT;
		if (oppor_stat != 1 && oppor_stat != 5 && oppor_stat != 6) {
			Ext.Msg.alert('提示', '只能分配“待分配、退回、到期退回”状态的商机！');
			return false;
		}
		if (oppor_stat == 6) {// 有到期退回的商机，将标志置成true
			had_6 = true;
		} else {
			had_no_6 = true;// 没有到期退回的商机，将标志置成true
		}
		// 2、“到期退回”的商机，不能和“待分配”、“退回”的商机一起分配，因为“到期退回”的商机，需要更改“商机有效期”
		if (had_6 && had_no_6) {
			Ext.Msg.alert('提示', '选择的商机中包含“到期退回”和其它状态的商机，不能分配！');
			return false;
		}
		// 3、“没有归属机构”和“有归属机构”的商机，不能同时分配
		assignOrgName = checkedNodes[i].data.assingOrgName;
		if (assignOrgName == null || assignOrgName != "") {// 有“分配到机构”的商机
			had_allocat_org = true;
		} else {// 有不是“分配到机构”的商机
			had_allocat_no_org = true;
		}
		if (had_allocat_org && had_allocat_no_org) {
			Ext.Msg.alert('提示', '有“待分配机构”的商机不能和其它的商机一起分配！');
			return false;
		}
		// 4、判断当前用户所属机构是否是“待分配机构”
		assignOrgId = checkedNodes[i].data.assignOrgId;
		if (assignOrgId != __units) {
			Ext.Msg.alert('提示', '您没有权限对选中的商机进行分配！');
			return false;
		}
		opporId = checkedNodes[i].data.opporId;
		opporIdS += opporId;
		if (i < checkedNodes.length - 1) {
			opporIdS += ",";
		}
	}
	busiOpportAllocatForm.getForm().reset();
	busiOpportAllocatForm.getForm().loadRecord(record);
	busiOpportAllocatForm.form.findField('opporId').setValue(opporIdS);// 设置要分配的商机ID集合
	// 设置“商机有效期”相关属性
	busiOpportAllocatForm.form.findField('opporDueDate').setValue("");
	if (had_6) {// 有“到期退回”状态的商机，显示“商机有效期”
		busiOpportAllocatForm.form.findField('opporDueDate').show();// 显示
	} else {// 没有“到期退回”状态的商机，不显示“商机有效期”
		busiOpportAllocatForm.form.findField('opporDueDate').hide();// 隐藏;
	}
	busiOpportAllocatWindow.show();
}