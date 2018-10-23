/**
 * 营销管理->商机管理->我的商机：新增商机表单及窗体定义JS文件；wzy；2013-03-01
 */
// 客户选择组件
var custSelectPartAdd = new Com.yucheng.bcrm.common.CustomerQueryField({
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
		addBusiOpporForm.form.findField('custCategory').setValue(
				custSelectPartAdd.custtype);
		// 客户状态
		addBusiOpporForm.form.findField('custType').setValue(
				custSelectPartAdd.custStat);
		// 客户联系人
		if (custSelectPartAdd.custtype == '1') {// 如果是对私客户，设置联系人
			addBusiOpporForm.form.findField('custContactName').setValue(
					custSelectPartAdd.linkUser);
		} else if (custSelectPartAdd.custtype == '2') {// 如果是对公客户，弹出联系人选择框，供用户选择
			addBusiOpporForm.form.findField('custContactName').setValue(
					custSelectPartAdd.linkUser);
		}
		// 主办客户经理
		addBusiOpporForm.form.findField('mainCustManager').setValue(
				custSelectPartAdd.mgrName);
		// 主办机构
		addBusiOpporForm.form.findField('mainCustOrgname').setValue(
				custSelectPartAdd.instName);
	}
});

// 新增商机窗口From表单
var addBusiOpporForm = new Ext.FormPanel({
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
			}), custSelectPartAdd, new Ext.form.ComboBox({
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
				editable : false,
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
		handler : saveAddBusiOppor
	}, {
		text : '提交',
		handler : submitAddBusiOppor
	}, {
		text : '关闭',
		handler : function() {
			addBusiOpporWindow.hide();
		}
	} ]
});

// 定义新增窗口
var addBusiOpporWindow = new Ext.Window({
	title : '商机新增',
	plain : true,
	layout : 'fit',
	width : 800,
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
	items : [ addBusiOpporForm ],
	listeners : {
		"hide" : function() {
			addBusiOpporForm.getForm().reset();
		},
		"show" : function() {// 窗体显示时间，进行一些数据设置初始化操作
			addBusiOpporForm.getForm().reset();
		}
	}
});

// 保存商机
// 对商机数据做临时存储，只控制必须输入“商机名称”，在提交时，判断必填项是否完全填写
function saveAddBusiOppor() {
	var opporName = addBusiOpporForm.form.findField('opporName').getValue();
	if (opporName == null || opporName == "") {
		Ext.Msg.alert('提示', '商机名称不能为空！');
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'saveOrUpdateBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : addBusiOpporForm.getForm().id,
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
	addBusiOpporWindow.hide();
}

// 提交商机
function submitAddBusiOppor() {
	if (!addBusiOpporForm.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'submitBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : addBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			Ext.Msg.alert('提示', '提交成功！');
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
	addBusiOpporWindow.hide();
}

// 打开 新增商机 窗口
function busiOpportAddWindowInit() {
	addBusiOpporWindow.show();
}