/**
 * 营销管理->商机管理->商机池->商机认领审核 入口JS文件 wzy，2013-02-26
 */

// 定义 商机认领审核窗口 From表单
var busiOpportClaimAuditForm = new Ext.FormPanel(
		{
			labelWidth : 100,
			height : 250,
			frame : true,
			autoScroll : true,
			labelAlign : 'right',
			buttonAlign : "center",
			items : [
					{
						layout : 'column',
						items : [
								{
									columnWidth : .5,
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
												fieldLabel : '同意',
												name : 'claim_audit_pass',
												anchor : '90%',
												listeners : {
													"focus" : function() {
														busiOpportClaimAuditForm.form
																.findField(
																		'claim_audit_back')
																.setValue(false);
														busiOpportClaimAuditForm.form
																.findField(
																		'memo')
																.hide();
														busiOpportClaimAuditForm.form
																.findField(
																		'memo')
																.setValue("");
													}
												}
											} ]
								},
								{
									columnWidth : .5,
									layout : 'form',
									items : [ {
										xtype : 'radio',
										fieldLabel : '拒绝',
										name : 'claim_audit_back',
										anchor : '90%',
										listeners : {
											"focus" : function() {
												busiOpportClaimAuditForm.form
														.findField(
																'claim_audit_pass')
														.setValue(false);
												busiOpportClaimAuditForm.form
														.findField('memo')
														.show();
											}
										}
									} ]
								} ]
					}, {
						layout : 'column',
						items : [ {
							columnWidth : .99,
							layout : 'form',
							items : [ {
								xtype : 'textarea',
								fieldLabel : '拒绝理由',
								name : 'memo',
								hidden : true,
								anchor : '90%'
							} ]
						} ]
					} ],
			buttons : [
					{
						text : '审批',
						handler : function() {
							if (!busiOpportClaimAuditForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
								return false;
							}
							var pass = false;
							var back = false;
							var auditResult = 0;// 审核结果，0：通过；1：不通过
							var opporIdS = busiOpportClaimAuditForm.form
									.findField('opporId').getValue();
							if (busiOpportClaimAuditForm.form
									.findField('claim_audit_pass').checked) {
								pass = true;
								auditResult = 0;
							}
							if (busiOpportClaimAuditForm.form
									.findField('claim_audit_back').checked) {
								back = true;
								auditResult = 1;
							}
							// 不能同时选择2中认领方式
							if (pass && back) {
								Ext.Msg.alert('提示', '只能选择一个审核结果！');
								return false;
							}
							// 必须选择1种认领方式
							if (!pass && !back) {
								Ext.Msg.alert('提示', '请至少选择一个审核结果！');
								return false;
							}
							if (!busiOpportClaimAuditForm.form
									.findField('memo').hidden) {
								var memo = busiOpportClaimAuditForm.form
										.findField('memo').getValue();
								if (memo == null || memo == "") {
									Ext.Msg.alert('提示', '请填写拒绝理由！');
									return false;
								}
							}
							var saveUrl = basepath
									+ '/mktBusiOpporOperationAction!'
									+ 'claimAuditBusiOppor.json';
							Ext.Ajax.request({
								url : saveUrl,
								method : 'POST',
								form : busiOpportClaimAuditForm.getForm().id,
								params : {
									'opporIdS' : opporIdS,
									'auditResult' : auditResult
								},
								waitMsg : '正在保存数据,请等待...',
								success : function(response) {
									Ext.Msg.alert('提示', '审批成功！');
									store.load({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '审批失败！');
								}
							});
							busiOpportClaimAuditWindow.hide();
						}
					}, {
						text : '关闭',
						handler : function() {
							busiOpportClaimAuditWindow.hide();
						}
					} ]
		});

// 定义 商机审批 窗口
var busiOpportClaimAuditWindow = new Ext.Window({
	title : '商机审批',
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
	items : [ busiOpportClaimAuditForm ],
	listeners : {
		"hide" : function() {
			busiOpportClaimAuditForm.getForm().reset();
		},
		"show" : function() {// 窗体显示时间，进行一些数据设置初始化操作，默认设置成“通过”
			busiOpportClaimAuditForm.form.findField('claim_audit_pass')
					.setValue(true);// “同意”单选按钮选中
			busiOpportClaimAuditForm.form.findField('claim_audit_back')
					.setValue(false);// “拒绝”单选按钮不选中
			busiOpportClaimAuditForm.form.findField('memo').hide();// “拒绝理由”输入框不可见
			busiOpportClaimAuditForm.form.findField('memo').setValue("");// “拒绝理由”值为空
		}
	}
});

// 打开 商机认领审批 窗口
function busiOpportClaimAuditWindowInit() {
	var record = listPanel.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要审批的商机！');
		return;
	}
	var checkedNodes = listPanel.getSelectionModel().selections.items;
	var oppor_stat = null;// 商机状态
	var opporIdS = '';// 选中的商机记录ID的集合
	var opporId = null;// 商机ID
	for ( var i = 0; i < checkedNodes.length; i++) {
		// 1、控制只能分配“待审批(3)”的商机
		oppor_stat = checkedNodes[i].data.OPPOR_STAT;
		if (oppor_stat != 3) {
			Ext.Msg.alert('提示', '只能审批“待审批”状态的商机！');
			return false;
		}
		opporId = checkedNodes[i].data.opporId;
		opporIdS += opporId;
		if (i < checkedNodes.length - 1) {
			opporIdS += ",";
		}
	}
	busiOpportClaimAuditForm.getForm().reset();
	// busiOpportClaimAuditForm.getForm().loadRecord(record);
	busiOpportClaimAuditForm.form.findField('opporId').setValue(opporIdS);// 设置要审批的商机ID集合
	busiOpportClaimAuditWindow.show();
}