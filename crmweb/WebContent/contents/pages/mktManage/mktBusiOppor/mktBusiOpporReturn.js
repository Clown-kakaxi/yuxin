/**
 * 营销管理->商机管理->商机池->商机退回 入口JS文件 wzy，2013-02-25
 */

// 定义 商机退回窗口 From表单
var busiOpportReturnForm = new Ext.FormPanel({
			labelWidth : 100,
			height : 250,
			frame : true,
			autoScroll : true,
			labelAlign : 'right',
			buttonAlign : "center",
			items : [{
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '商机ID',
									name : 'opporId',
									hidden : true,
									anchor : '90%'
								}, {
									xtype : 'textarea',
									fieldLabel : '退回原因',
									name : 'memo',
									allowBlank : false,
									blankText : '此项为必填项，请检查！',
									maxLength : 800,
									anchor : '95%'
								}]
					}],
			buttons : [{
				text : '退回',
				handler : function() {
					if (!busiOpportReturnForm.getForm().isValid()) {
						Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
						return false;
					}
					var saveUrl = basepath + '/mktBusiOpporOperationAction!'
							+ 'backBusiOppor.json';
					Ext.Ajax.request({
								url : saveUrl,
								method : 'POST',
								form : busiOpportReturnForm.getForm().id,
								waitMsg : '正在保存数据,请等待...',
								success : function(response) {
									Ext.Msg.alert('提示', '退回成功！');
									store.load({
												params : {
													start : 0,
													limit : bbar.pageSize
												}
											});
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '退回失败！');
								}
							});
					busiOpportReturnWindow.hide();
				}
			}, {
				text : '取消',
				handler : function() {
					busiOpportReturnWindow.hide();
				}
			}]
		});

// 定义 商机退回 窗口
var busiOpportReturnWindow = new Ext.Window({
			title : '商机退回',
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
			items : [busiOpportReturnForm]
		});

// 打开“商机退回”窗口，支持批量退回
// 1、客户经理只能退回分配给自己的商机，客户只有当前一个归属客户经理时不能退回
// 2、客户主管可以退回分配给本机构的商机，并且只能退回客户没有归属机构的商机。
// 3、在提醒规则中增加商机到期提醒提前天生配置，ETL在跑批时根据设置进行提醒。
// 4、只能退回“执行中”和“待分配”状态的商机
// 备注：由于权限的判断逻辑比较复杂，而且在前台不好获取判断逻辑所需数据，所以，判断逻辑放到后台，向前台返回判断结果
function busiOpportReturnWindowInit() {
	var record = listPanel.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要退回的商机！');
		return;
	}
	// 备注：由于在此判断逻辑中，角色编码是“写死”的，而在不同的实施现场，角色编码可能不同，
	// 所以，此处需要根据具体情况修改角色编码，
	// 在产品中，客户经理角色编码：zhhcm；总行业务经理：zhbm；分行业务经理：fhbm
	var roleCodes = __roleCodes;
	var roleArrs = null;
	var isCustManager = false;// 是否是客户经理
	var isOrgManager = false;// 是否是机构主管
	var userType = null;// 用户角色类型，0：客户经理；1：机构主管；2：客户经理+机构主管
	var opporId = null;
	var opporIdS = "";
	if (roleCodes != null && roleCodes != "") {
		roleArrs = roleCodes.split('$');
		if (roleArrs != null && roleArrs.length > 0) {
			for (var i = 0; i < roleArrs.length; i++) {
				if (roleArrs[i] == 'zhhcm') {// 客户经理
					isCustManager = true;
					userType = 0;
				} else if (roleArrs[i] == 'zhbm' || roleArrs[i] == 'fhbm') {// 机构主管
					isOrgManager = true;
					userType = 1;
				}
			}
		}
		if (isCustManager && isOrgManager) {
			userType = 2;
		}
	}
	// 只能退回“执行中”和“待分配”状态的商机
	var checkedNodes = listPanel.getSelectionModel().selections.items;
	for (var i = 0; i < checkedNodes.length; i++) {
		// 1、控制只能分配“待分配(1)、执行中(4)”的商机
		oppor_stat = checkedNodes[i].data.OPPOR_STAT;
		if (oppor_stat != 1 && oppor_stat != 4) {
			// Ext.Msg.alert('提示', '只能退回“待分配、执行中”状态的商机！');
			// wzy，20130422，modify：“商机池”功能中，不能查询出“执行中”状态的商机，所以，此处提示中，状态只有“待分配”
			Ext.Msg.alert('提示', '只能退回“待分配”状态的商机！');
			return false;
		}
		// 2、获取选择的商机
		opporId = checkedNodes[i].data.opporId;
		opporIdS += opporId;
		if (i < checkedNodes.length - 1) {
			opporIdS += ",";
		}
	}
	// 3、权限判断
	var queryUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'canReturn.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					'userType' : userType,
					'opporId' : opporIdS
				},
				success : function(response) {
					var canReturn = response.responseText;
					if (canReturn == "true") {
						// 4、展示窗口
						busiOpportReturnForm.getForm().reset();
						busiOpportReturnForm.getForm().loadRecord(record);
						busiOpportClaimForm.form.findField('opporId')
								.setValue(opporIdS);
						busiOpportReturnForm.form.findField('memo')
								.setValue('');
						busiOpportReturnWindow.show();
					} else {
						Ext.Msg.alert('提示', '您没有权限退回选中的商机！');
						return false;
					}
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '您没有权限退回选中的商机！');
					return false;
				}
			});
}