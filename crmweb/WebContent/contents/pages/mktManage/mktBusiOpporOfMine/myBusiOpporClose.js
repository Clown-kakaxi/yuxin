/**
 * 营销管理->商机管理->我的商机：关闭商机 入口JS文件 wzy，2013-03-01
 */

// 定义“商机关闭”窗口From表单
var busiOpportCloseForm = new Ext.FormPanel({
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
				fieldLabel : '商机ID',
				name : 'opporId',
				hidden : true,
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
			}) ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ new Com.yucheng.bcrm.common.MktActivityCommonQuery({
				xtype : 'activityQuery',
				fieldLabel : '营销活动名称',
				labelStyle : 'text-align:right;',
				name : 'mktActivName',
				hiddenName : 'mktActivId',
				singleSelect : false,
				anchor : '90%'
			}), new Com.yucheng.crm.common.ProductManage({
				xtype : 'productChoose',
				fieldLabel : '签约/销售产品',
				labelStyle : 'text-align:right;',
				name : 'prodName',
				hiddenName : 'relTrad',
				singleSelect : false,
				anchor : '90%',
				onTrigger2Click : function() {
					busOpportCloseProView();
				}
			}) ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'numberfield',
				fieldLabel : '达成金额',
				name : 'reachAmount',
				value : '0',
				anchor : '90%'
			} ]
		} ]
	}, {
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '失败关闭理由',
			name : 'opporContent',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '备注',
			name : 'memo',
			anchor : '95%'
		} ]
	} ],
	buttons : [ {
		text : '成功关闭',
		handler : successClose
	}, {
		text : '失败关闭',
		handler : failClose
	}, {
		text : '取消',
		handler : function() {
			busiOpportCloseWindow.hide();
		}
	} ]
});

// 成功关闭
function successClose() {
	doClose(0);
}

// 失败关闭
function failClose() {
	// 判断“失败关闭理由”是否填写
	var opr_content = busiOpportCloseForm.form.findField('opporContent')
			.getValue();
	if (opr_content == null || opr_content == "") {
		Ext.Msg.alert('提示', '请填写“失败关闭理由”！');
		return false;
	}
	doClose(1);
}

// 关闭操作
function doClose(type) {
	if (!busiOpportCloseForm.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
		return false;
	}
	var saveUrl = basepath + '/mktMyBusiOpporOperationAction!'
			+ 'closeBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : busiOpportCloseForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		params : {
			'closeType' : type
		},
		success : function(response) {
			Ext.Msg.alert('提示', '关闭成功！');
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '关闭失败！');
		}
	});
	busiOpportCloseWindow.hide();
}

// 定义“商机关闭”窗口
var busiOpportCloseWindow = new Ext.Window({
	title : '关闭商机',
	plain : true,
	layout : 'fit',
	width : 700,
	height : 300,
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
	items : [ busiOpportCloseForm ]
});

// 打开“商机关闭”窗口
function busiOpportCloseWindowInit() {
	var record = grid.getSelectionModel().getSelected();
	var selectLength = grid.getSelectionModel().getSelections().length;
	if (selectLength == 0) {
		Ext.Msg.alert('提示', '请先选择要关闭的商机！');
		return false;
	} else if (selectLength > 1) {
		Ext.Msg.alert('提示', '只能选择一个商机进行关闭！');
		return false;
	} else {
		var checkedNodes = grid.getSelectionModel().selections.items;
		var oppor_stat = checkedNodes[0].data.opporStat;
		if (oppor_stat != "4") {
			Ext.Msg.alert('提示', '只能关闭“执行中”状态的商机！');
			return false;
		}
		busiOpportCloseForm.getForm().reset();
		busiOpportCloseForm.getForm().loadRecord(record);
		busiOpportCloseForm.form.findField('opporContent').setValue("");// 清空失败关闭理由字段值
		busiOpportCloseWindow.show();
		var checkedNodes = grid.getSelectionModel().selections.items;
		var mktActivId = checkedNodes[0].data.mktActivId;// 营销活动ID
		var mktTargetId = checkedNodes[0].data.mktTargetId;// 营销任务指标ID
		busiOpportCloseForm.form.findField('mktActivId').setValue(mktActivId);// 设置营销活动ID
		busiOpportCloseForm.form.findField('mktTargetId').setValue(mktTargetId);// 营销任务指标ID
	}
}

/** *******************定义“签约/销售产品”选择控件***********开始********* */
// 定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var sm_p = new Ext.grid.CheckboxSelectionModel();
var planProdColumns = new Ext.grid.ColumnModel([ planRownum, sm_p, {
	header : '交易日期',
	align : 'left',
	dataIndex : 'productDetailId',
	sortable : true
}, {
	header : '交易时间',
	align : 'left',
	dataIndex : 'productId',
	sortable : true
}, {
	header : '交易金额',
	align : 'left',
	dataIndex : 'createUser',
	sortable : true
}, {
	header : '币种',
	align : 'left',
	dataIndex : 'createDate',
	sortable : true
} ]);

var planProdRecord = Ext.data.Record.create([ {
	name : 'productDetailId',
	mapping : 'PPDE_ID'
}, {
	name : 'productId',
	mapping : 'PRODUCT_ID'
}, {
	name : 'productName',
	mapping : 'PRODUCT_NAME'
}, {
	name : 'createUser',
	mapping : 'USERNAME'
}, {
	name : 'createDate',
	mapping : 'CREATE_DATE'
}, {
	name : 'planId',
	mapping : 'PLAN_ID'
} ]);

var planProdStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planProductQuery.json',
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty : 'num',// 记录总数
		root : 'rows'// Json中的列表数据根节点
	}, planProdRecord)
});

var memberData = {
	TOTALCOUNT : 3,
	rows : [ {
		"rownum" : "1",
		"PPDE_ID" : "2012-03-23",
		"PRODUCT_ID" : "20120308",
		" PRODUCT_NAME" : "002342",
		"USERNAME" : "1,000",
		"CREATE_DATE" : "人民币",
		"PLAN_ID" : "2342342"
	}, {
		"rownum" : "2",
		"PPDE_ID" : "2012-04-21",
		"PRODUCT_ID" : "20120407",
		" PRODUCT_NAME" : "002342",
		"USERNAME" : "25,000",
		"CREATE_DATE" : "人民币",
		"PLAN_ID" : "2342342"
	}, {
		"rownum" : "3",
		"PPDE_ID" : "2012-11-03",
		"PRODUCT_ID" : "20121108",
		" PRODUCT_NAME" : "002342",
		"USERNAME" : "1,329",
		"CREATE_DATE" : "人民币",
		"PLAN_ID" : "2342342"
	}, {
		"rownum" : "4",
		"PPDE_ID" : "2012-12-10",
		"PRODUCT_ID" : "20121208",
		" PRODUCT_NAME" : "002342",
		"USERNAME" : "2,432",
		"CREATE_DATE" : "人民币",
		"PLAN_ID" : "2342342"
	}, {
		"rownum" : "5",
		"PPDE_ID" : "2013-01-20",
		"PRODUCT_ID" : "20130104",
		" PRODUCT_NAME" : "003554",
		"USERNAME" : "99,876",
		"CREATE_DATE" : "人民币",
		"PLAN_ID" : "2342342"
	} ]
};
planProdStore.loadData(memberData);

var mktAssuListPanel = new Ext.grid.GridPanel({
	layout : 'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
	height : 245,
	store : planProdStore,
	frame : true,
	sm : sm_p,
	cm : planProdColumns,
	stripeRows : true,
	buttonAlign : "center",
	buttons : [ {
		text : '选择',
		handler : function() {
			Ext.getCmp('productSelect_close_pro').setValue(
					"2012-11-03 | 1329 | 人民币");
			busOpportCloseWindow.hide();
		}
	} ]
});

// 定义详情查看窗口
var busOpportCloseWindow = new Ext.Window({
	title : '交易流水',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 300,
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
	items : [ mktAssuListPanel ]
});

function busOpportCloseProView() {
	busOpportCloseWindow.show();
}
/** *******************定义“签约/销售产品”选择控件***********开始********* */
