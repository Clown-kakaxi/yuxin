/**
 * 营销管理->商机管理->商机池：维护商机表单及窗体定义JS文件；wzy；2013-02-22
 */

// 客户选择组件
var custSelectPartModify = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '*客户名称',
	labelWidth : 100,
	name : 'custName',
	custtype : '',// 客户类型:1:对私,2:对公,不设默认全部
	custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
	singleSelected : true,// 单选复选标志
	editable : false,
	allowBlank : false,
	blankText : '此项为必填项，请检查！',
	anchor : '90%',
	hiddenName : 'custId',
	callback : function() {// 回调方法，给其它字段设置相关属性值
		// 客户类型
		editBusiOpporForm.form.findField('custCategory').setValue(
				custSelectPartModify.custtype);
		// 客户状态
		editBusiOpporForm.form.findField('custType').setValue(
				custSelectPartModify.custStat);
		// 客户联系人
		if (custSelectPartModify.custtype == '1') {// 如果是对私客户，设置联系人
			editBusiOpporForm.form.findField('custContactName').setValue(
					custSelectPartModify.linkUser);
		} else if (custSelectPartModify.custtype == '2') {// 如果是对公客户，弹出联系人选择框，供用户选择
			editBusiOpporForm.form.findField('custContactName').setValue(
					custSelectPartModify.linkUser);
		}
		// 主办客户经理
		editBusiOpporForm.form.findField('mainCustManager').setValue(
				custSelectPartModify.mgrName);
		// 主办机构
		editBusiOpporForm.form.findField('mainCustOrgname').setValue(
				custSelectPartModify.instName);
	}
});

// 商机维护窗口From表单
var editBusiOpporForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '*商机ID',
				name : 'opporId',
				hidden : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '*商机状态',
				name : 'opporStat',
				hidden : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '*商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'opporName',
				anchor : '90%'
			}, new Com.yucheng.crm.common.ProductManage({
				xtype : 'productChoose',
				fieldLabel : '*商机产品',
				labelStyle : 'text-align:right;',
				name : 'prodName',
				hiddenName : 'prodId',
				singleSelect : false,
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}), {
				xtype : 'datefield',
				fieldLabel : '*商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporStartDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, new Com.yucheng.bcrm.common.MktActivityCommonQuery({
				xtype : 'activityQuery',
				fieldLabel : '营销活动名称',
				labelStyle : 'text-align:right;',
				name : 'mktActivName',
				hiddenName : 'mktActivId',
				singleSelect : false,
				anchor : '90%'
			}), custSelectPartModify, new Ext.form.ComboBox({
				hiddenName : 'custType',
				fieldLabel : '客户状态',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceTypeStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '主办客户经理',
				name : 'mainCustManager',
				readonly : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '预计金额(元)',
				name : 'planAmount',
				labelStyle : 'text-align:right;',
				value : '0',
				anchor : '90%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ new Ext.form.ComboBox({
				hiddenName : 'opporType',
				fieldLabel : '商机类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceStatStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				anchor : '90%'
			}), {
				xtype : 'datefield',
				fieldLabel : '*商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporDueDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, {
				xtype : 'datefield',
				fieldLabel : '*商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporEndDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, new Com.yucheng.bcrm.common.MktTaskTargetCommonQuery({
				xtype : 'taskTargetQuery',
				fieldLabel : '营销任务指标',
				labelStyle : 'text-align:right;',
				name : 'mktTargetName',
				hiddenName : 'mktTargetId',
				singleSelect : true,
				anchor : '90%'
			}), new Ext.form.ComboBox({
				hiddenName : 'custCategory',
				fieldLabel : '客户类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceCategoryStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'custContactName',
				readonly : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '主办机构',
				name : 'mainCustOrgname',
				readonly : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '费用预算(元)',
				name : 'planCost',
				value : '0',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			} ]
		} ]
	}, {
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'opporContent',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'memo',
			anchor : '95%'
		} ]
	} ],
	buttons : [ {
		text : '保存',
		handler : saveModifyBusiOppor
	}, {
		text : '提交',
		handler : submitModifyBusiOppor
	}, {
		text : '关闭',
		handler : function() {
			editBusiOpporWindow.hide();
		}
	} ]
});

// 定义维护商机窗口
var editBusiOpporWindow = new Ext.Window({
	title : '商机维护',
	plain : true,
	layout : 'fit',
	width : 750,
	height : 440,
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
	items : [ editBusiOpporForm ]
});

// 保存商机
// 对商机数据做临时存储，只控制必须输入“商机名称”，在提交时，判断必填项是否完全填写
function saveModifyBusiOppor() {
	// 控制“商机名称”不能为空
	var opporName = editBusiOpporForm.form.findField('opporName').getValue();
	if (opporName == null || opporName == "") {
		Ext.Msg.alert('提示', '商机名称不能为空！');
		return false;
	}
	// 控制“商机有效期”，如果商机状态为“到期退回”，控制“商机有效期”必须晚于当天日期
	var opporStat = editBusiOpporForm.form.findField('opporStat').getValue();
	if (opporStat >= new Date()) {
		Ext.Msg.alert('提示', '商机有效期不能早于今天！');
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'saveOrUpdateBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : editBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			Ext.Msg.alert('提示', '保存成功！');
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '保存失败！');
		}
	});
	editBusiOpporWindow.hide();
}

// 提交商机
function submitModifyBusiOppor() {
	if (!editBusiOpporForm.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
		return false;
	}
	// 控制“商机有效期”，如果商机状态为“到期退回”，控制“商机有效期”必须晚于当天日期
	var opporStat = editBusiOpporForm.form.findField('opporStat').getValue();
	if (opporStat >= new Date()) {
		Ext.Msg.alert('提示', '商机有效期不能早于今天！');
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'submitBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : editBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			// Ext.Msg.alert('提示', '提交成功！');
			var msg = response.responseText;
			if (msg != null && msg != "") {
				if (msg.substring(0, 1) == "0") {
					msg = "商机成功分配给客户经理“" + msg.substring(1) + "”。";
				} else if (msg.substring(0, 1) == "1") {
					msg = "商机成功分配给机构“" + msg.substring(1) + "”。";
				}
			}
			Ext.Msg.alert('提示', msg);
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '提交失败！');
		}
	});
	editBusiOpporWindow.hide();
}

// 打开 维护商机 窗口
// 1、只有暂存、退回、到期退回的商机才能修改；
// 2、到期退回的商机如果要提交进入待分配，必须修改商机到期日期；
// 3、有执行人的商机只能由执行人修改，没有执行人的商机只能由待分配机构主管修改；
function busiOpportEditWindowInit() {
	var record = listPanel.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要维护的商机！');
		return false;
	}
	// 1、状态控制：控制“0-暂存、5-退回、6-到期退回”状态的商机才能进行维护
	var checkedNodes = listPanel.getSelectionModel().selections.items;
	var opporStates = checkedNodes[0].data.OPPOR_STAT;
	if (opporStates != '0' && opporStates != '5' && opporStates != '6') {
		Ext.Msg.alert('提示', '只能维护“暂存、退回、到期退回”状态的商机！');
		return false;
	}
	// 2、控制修改权限：有执行人的商机只能有执行人修改，没有执行人的商机只能由待分配机构主管修改；
	var executeUserId = checkedNodes[0].data.executeUserId;// 执行人ID
	var assignOrgId = checkedNodes[0].data.assignOrgId;// 待分配机构ID
	var createrId = checkedNodes[0].data.createrId;// 创建人ID
	if (executeUserId != null && executeUserId != "") {
		// 有执行人的商机，如果当前用户不是执行人，不能修改商机
		if (executeUserId != __userId) {
			Ext.Msg.alert('提示', '您没有权限维护该商机！');
			return false;
		}
		// 3、展示窗口
		openWin();
	} else if (assignOrgId != null && assignOrgId != "") {
		// 无执行人的商机，如果当前用户不是“待分配机构”主管，不能修改商机
		var queryUrl = basepath + '/mktBusiOpporOperationAction!'
				+ 'getOrgManager.json';
		Ext.Ajax.request({
			url : queryUrl,
			method : 'POST',
			sync : true,// 同步
			waitMsg : '正在查询数据,请等待...',
			params : {
				'orgId' : assignOrgId
			},
			success : function(response) {
				var orgManagerId = response.responseText;
				if (orgManagerId == __userId) {
					// 3、展示窗口
					openWin();
				} else {
					Ext.Msg.alert('提示', '您没有权限维护该商机！');
					return false;
				}
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '您没有权限维护该商机！');
				return false;
			}
		});
	} else if (opporStates == '0') {
		// 商机状态为“暂存”，只能由创建人修改
		if (createrId != __userId) {
			Ext.Msg.alert('提示', '您没有权限维护该商机！');
			return false;
		}
		// 3、展示窗口
		openWin();
	}
}

// 打开维护窗口
function openWin() {
	var record = listPanel.getSelectionModel().getSelected();
	var checkedNodes = listPanel.getSelectionModel().selections.items;
	var mktActivId = checkedNodes[0].data.mktActivId;// 营销活动ID
	var mktTargetId = checkedNodes[0].data.mktTargetId;// 营销任务指标ID
	var custId = checkedNodes[0].data.custId;// 客户ID
	var prodId = checkedNodes[0].data.prodId;// 产品ID
	editBusiOpporForm.getForm().reset();
	editBusiOpporForm.getForm().loadRecord(record);
	editBusiOpporWindow.show();
	editBusiOpporForm.form.findField('mktActivId').setValue(mktActivId);// 设置营销活动ID
	editBusiOpporForm.form.findField('mktTargetId').setValue(mktTargetId);// 营销任务指标ID
	editBusiOpporForm.form.findField('custId').setValue(custId);// 客户ID
	editBusiOpporForm.form.findField('prodId').setValue(prodId);// 产品ID
}