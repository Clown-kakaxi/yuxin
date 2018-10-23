/**
 * 营销管理->商机管理->商机池->商机认领 入口JS文件 wzy，2013-02-26
 */

// 定义 认领商机 窗口 From表单
var busiOpportClaimForm = new Ext.FormPanel(
		{
			labelWidth : 100,
			height : 150,
			frame : true,
			autoScroll : true,
			labelAlign : 'right',
			buttonAlign : "center",
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .95,
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
								fieldLabel : '客户经理认领',
								name : 'claim_user',
								anchor : '90%',
								listeners : {
									"focus" : function() {
										busiOpportClaimForm.form.findField(
												'claim_org').setValue(false);
									}
								}
							},
							{
								xtype : 'radio',
								fieldLabel : '机构认领',
								name : 'claim_org',
								anchor : '90%',
								listeners : {
									"focus" : function() {
										busiOpportClaimForm.form.findField(
												'claim_user').setValue(false);
									}
								}
							} ]
				} ]
			} ],
			buttons : [
					{
						text : '认领',
						handler : function() {
							if (!busiOpportClaimForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
								return false;
							}
							var claimUser = false;
							var claimOrg = false;
							var claimType = 0;// 认领方式
							var opporIdS = busiOpportClaimForm.form.findField(
									'opporId').getValue();
							if (busiOpportClaimForm.form
									.findField('claim_user').checked) {
								claimUser = true;
								claimType = 0;
							}
							if (busiOpportClaimForm.form.findField('claim_org').checked) {
								claimUser = true;
								claimType = 1;
							}
							// 不能同时选择2中认领方式
							if (claimUser && claimOrg) {
								Ext.Msg.alert('提示', '只能选择一种认领方式！');
								return false;
							}
							// 必须选择1种认领方式
							if (!claimUser && !claimOrg) {
								Ext.Msg.alert('提示', '请至少选择一种认领方式！');
								return false;
							}
							var saveUrl = basepath
									+ '/mktBusiOpporOperationAction!'
									+ 'claimBusiOppor.json';
							Ext.Ajax.request({
								url : saveUrl,
								method : 'POST',
								params : {
									'opporIdS' : opporIdS,
									'claimType' : claimType
								},
								waitMsg : '正在保存数据,请等待...',
								success : function(response) {
									Ext.Msg.alert('提示', '认领成功！');
									store.load({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '认领失败！');
								}
							});
							busiOpportClaimWindow.hide();
						}
					}, {
						text : '关闭',
						handler : function() {
							busiOpportClaimWindow.hide();
						}
					} ]
		});

// 定义 认领商机 窗口
var busiOpportClaimWindow = new Ext.Window({
	title : '选择认领方式',
	plain : true,
	layout : 'fit',
	width : 300,
	height : 150,
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
	items : [ busiOpportClaimForm ],
	listeners : {
		"hide" : function() {
			busiOpportClaimForm.getForm().reset();
		},
		"show" : function() {// 窗体显示时间，进行一些数据设置初始化操作，默认设置成“客户经理认领”
			busiOpportClaimForm.form.findField('claim_user').setValue(true);// “客户经理认领”单选按钮选中
			busiOpportClaimForm.form.findField('claim_org').setValue(false);// “机构认领”单选按钮不选中
		}
	}
});

// 打开 认领商机 窗口
// 1、客户经理只能认领本机构待分配商机；
// 2、客户主管只能认领上级机构待分配商机；
// 3、客户经理认领审批通过后，商机状态转换为执行中；
// 4、客户主管认领审批通过后，商机状态继续为待分配；
// 5、机构主管在审批认领商机时，拒绝认领的需要输入拒绝理由；
function busiOpportClaimWindowInit() {
	var record = listPanel.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要认领的商机！');
		return;
	}
	var checkedNodes = listPanel.getSelectionModel().selections.items;
	var oppor_stat = null;// 商机状态
	var opporIdS = '';// 选中的商机记录ID的集合
	var opporId = null;// 商机ID
	var claimType = 0;// 认领方式，0：客户经理认领；1：机构认领
	var assingOrgId = null;// 待分配机构ID
	// 根据当前登录用户的角色进行判断：是将商机认领到个人还是认领到机构
	// 如果当前用户是客户经理，那么商机认领到个人（客户经理）
	// 如果当前用户是机构主管，那么商机认领到机构（当前用户所在机构）
	// 如果当前用户既是客户经理又是机构主管，弹出选择窗体，让用户选择是认领到个人还是机构
	var roleType = getRoleType();// 0：客户经理；1：机构主管；2：客户经理+机构主管
	for ( var i = 0; i < checkedNodes.length; i++) {
		// 1、控制只能认领“待分配(1)、退回(5)、到期退回(6)”的商机
		oppor_stat = checkedNodes[i].data.OPPOR_STAT;
		if (oppor_stat != 1 && oppor_stat != 5 && oppor_stat != 6) {
			Ext.Msg.alert('提示', '只能认领“待分配、退回、到期退回”状态的商机！');
			return false;
		}
		// 2、权限控制
		assingOrgId = checkedNodes[i].data.assignOrgId;
		if (roleType == "0") {
			// 当前用户是客户经理
			if (assingOrgId != __units) {
				Ext.Msg.alert('提示', '您没有权限认领选中的商机！');
				return false;
			}
		} else if (roleType == "1") {
			// 当前用户是机构主管
			if (assingOrgId != mktBusiOppor_currUserOrg_parent) {
				Ext.Msg.alert('提示', '您没有权限认领选中的商机！');
				return false;
			}
		} else if (roleType == "2") {
			// 当前用户是客户经理+机构主管
			if (assingOrgId != __units
					&& assingOrgId != mktBusiOppor_currUserOrg_parent) {
				Ext.Msg.alert('提示', '您没有权限认领选中的商机！');
				return false;
			}
		} else {
			Ext.Msg.alert('提示', '您没有权限认领选中的商机！');
			return false;
		}
		// 3、获取选择的商机
		opporId = checkedNodes[i].data.opporId;
		opporIdS += opporId;
		if (i < checkedNodes.length - 1) {
			opporIdS += ",";
		}
	}
	if (roleType == "2") {// 是客户经理+机构主管
		// 选择认领方式：个人认领还是机构认领
		busiOpportClaimForm.getForm().reset();
		busiOpportClaimForm.form.findField('opporId').setValue(opporIdS);
		busiOpportClaimWindow.show();
	} else {// 是客户经理或者机构主管
		// 直接进行认领
		if (roleType == "0") {
			claimType = 0;
		} else if (roleType == "1") {
			claimType = 1;
		}
		var saveUrl = basepath + '/mktBusiOpporOperationAction!'
				+ 'claimBusiOppor.json';
		Ext.Ajax.request({
			url : saveUrl,
			method : 'POST',
			params : {
				'opporIdS' : opporIdS,
				'claimType' : claimType
			},
			waitMsg : '正在保存数据,请等待...',
			success : function(response) {
				Ext.Msg.alert('提示', '认领成功！');
				store.load({
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '认领失败！');
			}
		});
	}
}

var roleCustManagerArr = [];// "客户经理角色编码"数组
var roleOrgManagerArr = [];// "机构主管角色编码"数组
// 初始化"客户经理角色编码"数组
// 备注：不同客户现场定义的角色编码可能不同，此处需要根据客户具体情况进行适当修改
roleCustManagerArr.push("zhhcm");// 支行客户经理
// 初始化"机构主管角色编码"数组
// 备注：不同客户现场定义的角色编码可能不同，此处需要根据客户具体情况进行适当修改
roleOrgManagerArr.push("zhbm");// 总行业务经理
roleOrgManagerArr.push("fhbm");// 分行业务经理
roleOrgManagerArr.push("zhhz");// 支行行长

function getRoleType() {
	var roleType = null;// 返回结果，0：客户经理；1：机构主管；2：客户经理+机构主管
	var isCustManager = false;// 是否是客户经理，是：true；否：false
	var isOrgManager = false;// 是否是机构主管，是：true；否：false
	var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
	var roleArrs = null;
	if (roleCodes != null && roleCodes != "") {
		roleArrs = roleCodes.split('$');
		if (roleArrs != null && roleArrs.length > 0) {
			for ( var i = 0; i < roleArrs.length; i++) {
				// 判断当前用户是否拥有客户经理角色
				if (!isCustManager) {
					for ( var j = 0; j < roleCustManagerArr.length; j++) {
						if (roleArrs[i] == roleCustManagerArr[j]) {
							isCustManager = true;
						}
					}
				}
				// 判断当前用户是否拥有机构主管角色
				if (!isOrgManager) {
					for ( var j = 0; j < roleOrgManagerArr.length; j++) {
						if (roleArrs[i] == roleOrgManagerArr[j]) {
							isOrgManager = true;
						}
					}
				}
			}
		}
	}
	if (isCustManager) {
		roleType = "0";// 客户经理
	}
	if (isOrgManager) {
		roleType = "1";// 机构主管
	}
	if (isCustManager && isOrgManager) {
		roleType = "2";// 客户经理+机构主管
	}
	return roleType;
}