/**
 * @description 法金潜在客户管理
 * @author likai
 * @since 2014-12-08
 */

imports([ '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
		, '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js' // 用户放大镜
		, '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js' ]);

Ext.QuickTips.init();// 提示信息
var url = basepath + '/acrmFCiPotCusCom.json';
// var comitUrl = basepath + '/acrmFCiPotCusCom!saveData.json';

var needCondition = true;
var needGrid = true;
var createView = true;
var editView = true;
var detailView = true;
var json = null;// 保存选中的客户

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var lookupTypes = [ 'IF_FLAG', 'CUST_SOURCE', 'MANAGER_TYPE', 'XD000002',
		'XD000007', 'FJQZKH_OWNBUSI', 'POTENTAIL_STATE' ];

var localLookup = {
	'A_FILETYPE' : [ {
		key : '1',
		value : '银行对账单'
	}, {
		key : '2',
		value : '进/销货单据'
	}, {
		key : '3',
		value : '税单'
	}, {
		key : '4',
		value : '工资单'
	} ],
	'B_FILETYPE' : [ {
		key : '1',
		value : '电费单'
	}, {
		key : '2',
		value : '电话费单'
	}, {
		key : '3',
		value : '水费单'
	}, {
		key : '4',
		value : '运输单据'
	}, {
		key : '5',
		value : '气费单'
	}, {
		key : '6',
		value : '租约'
	} ],
	'C_FILETYPE' : [ {
		key : '1',
		value : '非正式账本'
	} ],
	'CONCLUSION' : [ {
		key : '1',
		value : '未处理'
	}, {
		key : '2',
		value : '未定位'
	}, {
		key : '3',
		value : '未通过'
	}, {
		key : '4',
		value : '通过'
	} ],
	'CREDIT_USE' : [ {
		key : '1',
		value : '经营周转'
	}, {
		key : '2',
		value : '科技开发'
	}, {
		key : '3',
		value : '项目投资'
	}, {
		key : '4',
		value : '固定资产配置'
	}, {
		key : '5',
		value : '其他'
	} ],
	'Q_MARKETIN' : [ {
		key : '1',
		value : '年主营业务收入超过20亿元，且为上市企业'
	}, {
		key : '2',
		value : '年主营业务收入超过20亿元，但并非上市企业'
	}, {
		key : '3',
		value : '年主营业务收入为人民币12亿元至20亿元（含）'
	}, {
		key : '4',
		value : '年主营业务收入为人民币6亿元至12亿元（含）'
	}, {
		key : '5',
		value : '年主营业务收入为人民币3亿元至6亿元（含）'
	}, {
		key : '6',
		value : '年主营业务收入为人民币1亿元至3亿元（含）'
	}, {
		key : '7',
		value : '年主营业务收入为人民币0.7亿元（含）至 1亿元（含）'
	} ],
	'CURRENCY' : [ {
		key : '10',
		value : 'RMB'
	}, {
		key : '13',
		value : 'USD'
	}, {
		key : '5',
		value : 'EUR'
	}, {
		key : '8',
		value : 'JPY'
	}, {
		key : '7',
		value : 'HKD'
	}, {
		key : '6',
		value : 'GBP'
	}, {
		key : '1',
		value : 'AUD'
	}, {
		key : '2',
		value : 'CAD'
	}, {
		key : '3',
		value : 'CHF'
	}, {
		key : '9',
		value : 'NZD'
	}, {
		key : '11',
		value : 'SGD'
	}, {
		key : '12',
		value : 'TWD'
	} ]
};

var fields = [ {
	name : 'CUS_ID',
	text : '客户编号',
	searchField : true,
	xtype : 'textfield',
	readOnly : false
}, {
	name : 'CUS_NAME',
	text : '客户名称',
	xtype : 'textfield',
	searchField : true,
	maxLength : 100,
	allowBlank : false
}, {
	name : 'CUS_PHONE',
	text : '客户电话',
	gridField : true
}, {
	name : 'CUS_ADDR',
	text : '客户地址',
	gridField : true,
	xtype : 'textarea',
	maxLength : 200
}, {
	name : 'ATTEN_NAME',
	text : '联系人姓名',
	gridField : true
}, {
	name : 'ATTEN_BUSI',
	text : '联系人职务',
	gridField : true,
	translateType : 'XD000007'
}, {
	name : 'ATTEN_PHONE',
	text : '联系人电话',
	gridField : true
}, {
	name : 'LEGAL_NAME',
	text : '法人代表姓名',
	gridField : true
}, {
	name : 'REQ_CURRENCY',
	text : '币种',
	gridField : true,
	translateType : 'CURRENCY'
}, {
	name : 'REG_CAP_AMT',
	text : '注册资金(万元)',
	gridField : true,
	dataType : 'number'
}, {
	name : 'CUS_RESOURCE',
	text : '客户来源',
	gridField : true,
	translateType : 'CUST_SOURCE'
}, {
	name : 'CUS_RESOURCE_BAK1',
	searchField : true,
	text : '客户来源备注1',
	gridField : true
}, {
	name : 'CUS_RESOURCE_BAK2',
	searchField : true,
	text : '客户来源备注2',
	gridField : true
}, {
	name : 'CUS_RESOURCE_BAK3',
	searchField : true,
	text : '客户来源备注3',
	gridField : true
}, {
	name : 'CUS_RESOURCE_BAK4',
	text : '来源备注',
	gridField : true
}, {
	name : 'ACT_CTL_NAME',
	text : '实际控制人姓名',
	gridField : false
}, {
	name : 'ACT_CTL_PHONE',
	text : '实际控制人电话',
	gridField : false
}, {
	name : 'ACT_CTL_WIFE',
	text : '实际控制人配偶姓名',
	gridField : false
}, {
	name : 'PARTNER_INFO1',
	text : '第一持股人',
	gridField : false
}, {
	name : 'PARTNER_RATE1',
	text : '第一持股人比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'PARTNER_INFO2',
	text : '第二持股人',
	gridField : false
}, {
	name : 'PARTNER_RATE2',
	text : '第二持股人比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'PARTNER_INFO3',
	text : '第三持股人',
	gridField : false
}, {
	name : 'PARTNER_RATE3',
	text : '第三持股人比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'AMOUNT2',
	text : '前年净利润(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'AMOUNT1',
	text : '上年净利润(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'AMOUNT',
	text : '当期总收入(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'PRE_AMOUNT',
	text : '今年预计净利润(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'AVE_BALANCE',
	text : '平均每月银行账户余额(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'TOTAL_ASS',
	text : '企业总资产(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'LICENSE_FLAG',
	text : '能否提供营业执照',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'TAX_REC_FLAG',
	text : '能否提供企业纳税凭证',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'DEBT_AMOUNT',
	text : '企业总负债(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'CAP_AMOUNT',
	text : '企业自有资金(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'LOAN_AMOUNT',
	text : '银行贷款总额(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'FINA_AMOUNT',
	text : '其他渠道融资(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'CREDIT_CARD_FLAG',
	text : '有无企业贷款卡',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'DEBIT_FLAG',
	text : '是否有欠息情况',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'BAD_CREDIT_FLAG',
	text : '是否有未结清的不良贷款信息',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'PER_CARD_FLAG',
	text : '个人是否拥有信用卡',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'CREDIT_CARD_BANK',
	text : '个人信用卡第一发卡行',
	gridField : false
}, {
	name : 'PRE_CREDIT_AMOUNT',
	text : '期望贷款金额(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'CREDIT_USE',
	text : '贷款用途',
	gridField : false,
	translateType : 'CREDIT_USE'
}, {
	name : 'TERM',
	text : '贷款期限(月)',
	gridField : false,
	dataType : 'number',
	allowDecimals : false,
	allowNegative : false
}, {
	name : 'REPAY_SOURCE',
	text : '还款来源',
	gridField : false
}, {
	name : 'SUP_INF',
	text : '第一供货商',
	gridField : false
}, {
	name : 'SUP_INF_RATE',
	text : '第一供货商比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'SUP_INF_S',
	text : '第二供账商',
	gridField : false
}, {
	name : 'SUP_INF_S_RATE',
	text : '第二供账商比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'BUYER_INF',
	text : '第一买售商',
	gridField : false
}, {
	name : 'BUYER_INF_RATE',
	text : '第一买售商比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'BUYER_INF_S',
	text : '第二买售商',
	gridField : false
}, {
	name : 'BUYER_INF_S_RATE',
	text : '第二买售商比例',
	gridField : false,
	dataType : 'number',
	allowNegative : false
}, {
	name : 'RELATION_COM',
	text : '关联企业1',
	gridField : false
}, {
	name : 'RELATION_COM_S',
	text : '关联企业2',
	gridField : false
}, {
	name : 'GUA_MOR_FLAG',
	text : '企业以下信息是否拥有以下担保，是否有抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'CUST_MGR',
	text : '主管客户经理',
	gridField : true
}, {
	name : 'Q_CUSTOMERTYPE',
	text : '客户经理类型',
	gridField : false,
	translateType : 'MANAGER_TYPE'
}, {
	name : 'Q_INTERVIEWEENAME',
	text : '受访人姓名',
	gridField : false
}, {
	name : 'Q_INTERVIEWEEPOST',
	text : '受访人职位',
	gridField : false,
	translateType : 'XD000007'
}, {
	name : 'Q_OPERATEYEARS',
	text : '企业经营年限',
	gridField : false
}, {
	name : 'Q_BUSINESS',
	text : '行业',
	gridField : false,
	translateType : 'FJQZKH_OWNBUSI'
}, {
	name : 'Q_MARKETIN',
	text : '销售收入',
	gridField : false,
	translateType : 'Q_MARKETIN'
}, {
	name : 'Q_ASSTOTAL',
	text : '总资产(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'Q_MAGYEARS',
	text : '核心管理经验',
	gridField : false
}, {
	name : 'Q_WORKYEARS',
	text : '从业年限',
	gridField : false,
	dataType : 'number',
	allowDecimals : false,
	allowNegative : false
}, {
	name : 'Q_FOUNDEDYEARS',
	text : '成立年份',
	gridField : false
}, {
	name : 'Q_CREDITLIMIT',
	text : '申请贷款额度(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'Q_ADDRYEARS',
	text : '现址经营年限',
	gridField : false
}, {
	name : 'Q_PYEARINCOME',
	text : '前年销售收入(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'Q_LYEARINCOME',
	text : '去年销售收入(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'Q_TOTALINCOME',
	text : '今年累计销售收入(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'Q_PLANINCOME',
	text : '今年计划销售收入(折人民币/千元)',
	gridField : false,
	dataType : 'number',
	viewFn : money('0,000.00'),
	allowNegative : false
}, {
	name : 'G_HOUSE',
	text : '房产',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_HOUSEPLEDGE',
	text : '房产抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_LAND',
	text : '土地使用权',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_LANDPLEDGE',
	text : '土地使用权抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_EQUIPMENT',
	text : '设备',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_EQUIPMENTPLEDGE',
	text : '设备抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_FOREST',
	text : '林权',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_FORESTPLEDGE',
	text : '林权抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_MINING',
	text : '采矿权',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_MININGPLEDGE',
	text : '采矿权抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_FLOATING',
	text : '浮劢抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_FLOATPLEDGE',
	text : '浮劢抵押货押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_DEPOSIT',
	text : '存款',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_DEPOSITPLEDGE',
	text : '存款抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_VEHICLE',
	text : '车辆',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_VEHICLEPLEDGE',
	text : '车辆抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_RECEIVABLEMONEY',
	text : '应收败款',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_RECEIVABLEMPLEDGE',
	text : '应收败款抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_STOCK',
	text : '股权',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'G_STOCKPLEDGE',
	text : '股权抵押',
	gridField : false,
	translateType : 'IF_FLAG'
}, {
	name : 'A_FILETYPE',
	text : 'A类文件',
	gridField : false,
	translateType : 'A_FILETYPE',
	multiSelect : true,
	multiSeparator : ','
}, {
	name : 'B_FILETYPE',
	text : 'B类文件',
	gridField : false,
	translateType : 'B_FILETYPE',
	multiSelect : true,
	multiSeparator : ','
}, {
	name : 'C_FILETYPE',
	text : 'C类文件',
	gridField : false,
	translateType : 'C_FILETYPE'
}, {
	name : 'CONCLUSION',
	text : '移动信贷客户筛选状态',
	gridField : false,
	translateType : 'CONCLUSION',
	allowBlank : false
}, {
	name : 'ISNEW',
	text : '是否移动信贷本地自建',
	gridField : true,
	translateType : 'IF_FLAG'
}, {
	name : 'STATE',
	text : '潜在客户状态',
	gridField : true,
	translateType : 'POTENTAIL_STATE'
}, {
	name : 'MOVER_USER',
	text : '移交分配人',
	gridField : true
}, {
	name : 'MOVER_DATE',
	text : '移交分配日期',
	gridField : true,
	xtype : 'textarea',
	maxLength : 200
},{
	name : 'REMARK',
	text : '备注',
	gridField : true,
	xtype : 'textarea',
	maxLength : 200
},{
	name : 'IVTIME',
	text : '拜访次数',
	gridField : true,
	dataType : 'number',
	viewFn : money('0'),
	allowNegative : false
}, {
	name : 'NETIME',
	text : '电访次数',
	gridField : true,
	dataType : 'number',
	viewFn : money('0'),
	allowNegative : false
} ];

var handStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
		field : 'key',
		direction : 'ASC'
	},
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FJMTASK_OPER_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var transForm1 = new Ext.FormPanel({
	frame : true,
	height : 140,
	region : 'north',
	items : [ {
		layout : 'column',
		items : [
				{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 100,
					items : [ {
						fieldLabel : '<font color="red">*</font>执行对象类型',
						name : 'handKind',
						hiddenName : 'handKind',
						forceSelection : true,
						xtype : 'combo',
						labelStyle : 'text-align:right;',
						triggerAction : 'all',
						mode : 'local',
						store : handStore,
						valueField : 'key',
						displayField : 'value',
						allowBlank : false,
						emptyText : '请选择',
						anchor : '95%',
						listeners : {
							select : function() {
								var handKind = transForm1.getForm().findField(
										'handKind').getValue();
								if (handKind != '' && handKind == '1') {// 机构
									transForm1.getForm().findField(
											'custMgrName').setVisible(false);
									transForm1.getForm().findField('orgName')
											.setVisible(true);
								} else if (handKind != '' && handKind == '2') {// 客户
									transForm1.getForm().findField(
											'custMgrName').setVisible(true);
									transForm1.getForm().findField('orgName')
											.setVisible(false);
								}
							}
						}
					} ]
				},
				{
					layout : 'form',
					columnWidth : 1,
					labelWidth : 100,
					items : [ new Com.yucheng.crm.common.OrgUserManage({
						hidden : true,
						xtype : 'userchoose',
						fieldLabel : '<font color="red">*</font>客户执行对象',
						labelStyle : 'text-align:right;',
						name : 'custMgrName',
						hiddenName : 'custMgrId',
						singleSelected : true,// 单选复选标志
						allowBlank : false,
						searchField : true,
						anchor : '47.5%',
						callback : function(b) {
							transForm1.getForm().findField("custMgrName")
									.setValue(b[0].data.userName);
							transForm1.getForm().findField("custMgrId")
									.setValue(b[0].data.userId);
						}
					}) ]
				},
				{
					layout : 'form',
					columnWidth : 1,
					labelWidth : 100,
					items : [ new Com.yucheng.bcrm.common.OrgField({
						hidden : true,
						xtype : 'orgchoose',
						fieldLabel : '<font color="red">*</font>机构执行对象',
						labelStyle : 'text-align:right;',
						name : 'orgName',
						hiddenName : 'orgId',
						singleSelected : true,// 单选复选标志
						allowBlank : false,
						searchField : true,
						anchor : '47.5%',
						callback : function() {
							transForm1.getForm().findField("orgId").setValue(
									transForm1.getForm().findField("orgId")
											.getValue());
						}
					}) ]
				} ]
	} ]
});
var transedRecord = Ext.data.Record.create([ {
	name : 'CUS_ID',
	mapping : 'CUS_ID'
}, {
	name : 'CUS_NAME',
	mapping : 'CUS_NAME'
} ]);

var transedStore = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		root : 'transedData'
	}, transedRecord)
});

var num = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var cm = new Ext.grid.ColumnModel([ num, // 定义列模型
{
	header : '客户编号',
	dataIndex : 'CUS_ID',
	sortable : true,
	width : 100
}, {
	header : '客户名称',
	dataIndex : 'CUS_NAME',
	sortable : true,
	width : 150
}]);

var custGrid = new Ext.grid.GridPanel({
	title : '分配的客户',
	autoScroll : true,
	height : 130,
	region : 'center',
	store : transedStore,
	stripeRows : true, // 斑马线
	cm : cm,
	viewConfig : {}
});

var cTrans = new Ext.Panel({
	autoScroll : true,
	buttonAlign : "center",
	layout : 'border',
	items : [ transForm1, custGrid ],
	buttons : [ {
		text : '提交',
		handler : function() {
			var hindType = transForm1.form.findField('handKind').getValue();
			var custMgrId = transForm1.form.findField('custMgrId').getValue();
			var orgId = transForm1.form.findField('orgId').getValue();
			if (hindType == '') {
				Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
				return false;
			} else {
				if (hindType == '1') {// 机构
					if (orgId == '') {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
				} else if (hindType == '2') {// 客户
					if (custMgrId == '') {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
				}
			}
			var cusId = '';
			for ( var i = 0; i < transedStore.getCount(); i++) {
				var temp = transedStore.getAt(i);
				if (i == 0) {
					cusId = "'" + temp.data.CUS_ID + "'";
				} else {
					cusId += "," + "'" + temp.data.CUS_ID + "'";
				}
			}
			Ext.Msg.wait('正在保存，请稍后......', '系统提示');
			Ext.Ajax.request({
				url : basepath + '/acrmFCiPotCusCom!fbPotCusInfo.json',
				method : 'GET',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				// form:transForm1.getForm().CUS_ID,
				params : {
					cusId : cusId,
					type : transForm1.form.findField('handKind').getValue(),
					custMgr : transForm1.form.findField('custMgrId').getValue()
							+ transForm1.form.findField('orgId').getValue()
				},
				success : function(response) {
					Ext.Msg.alert('提示', '分配成功');
					reloadCurrentData();
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '分配失败');
					reloadCurrentData();
				}
			});
		}
	} ]
});

var customerView = [
		{
			title : '新增',
			//hideTitle:true,
			type : 'form',
			groups : [
					{
						// columnCount : 2 ,
						// fields :
						// ['CUS_ID','CUS_NAME','CUS_PHONE','ATTEN_NAME','ATTEN_BUSI','ATTEN_PHONE','LEGAL_NAME','REQ_CURRENCY','REG_CAP_AMT','CUS_RESOURCE','CUS_RESOURCE_BAK1','CUS_RESOURCE_BAK2','CUS_RESOURCE_BAK3','CUS_RESOURCE_BAK4','ACT_CTL_NAME','ACT_CTL_PHONE','ACT_CTL_WIFE','PARTNER_INFO1'
						// ,'PARTNER_RATE1','PARTNER_INFO2','PARTNER_RATE2','PARTNER_INFO3','PARTNER_RATE3','AMOUNT2','AMOUNT1','AMOUNT','PRE_AMOUNT','AVE_BALANCE','TOTAL_ASS','LICENSE_FLAG','TAX_REC_FLAG','DEBT_AMOUNT'
						// ,'CAP_AMOUNT','LOAN_AMOUNT','FINA_AMOUNT','CREDIT_CARD_FLAG','DEBIT_FLAG','BAD_CREDIT_FLAG','PER_CARD_FLAG','CREDIT_CARD_BANK','PRE_CREDIT_AMOUNT','CREDIT_USE','TERM','REPAY_SOURCE'
						// ,'SUP_INF','SUP_INF_RATE','SUP_INF_S','SUP_INF_S_RATE','BUYER_INF','BUYER_INF_RATE','BUYER_INF_S','BUYER_INF_S_RATE','RELATION_COM','RELATION_COM_S','GUA_MOR_FLAG','CUST_MGR','Q_CUSTOMERTYPE'
						// ,'Q_INTERVIEWEENAME','Q_INTERVIEWEEPOST','Q_OPERATEYEARS','Q_BUSINESS','Q_MARKETIN','Q_ASSTOTAL','Q_MAGYEARS','Q_WORKYEARS','Q_FOUNDEDYEARS','Q_CREDITLIMIT','Q_ADDRYEARS','Q_PYEARINCOME','Q_LYEARINCOME'
						// ,'Q_TOTALINCOME','Q_PLANINCOME','G_HOUSE','G_HOUSEPLEDGE','G_LAND','G_LANDPLEDGE','G_EQUIPMENT','G_EQUIPMENTPLEDGE','G_FOREST','G_FORESTPLEDGE','G_MINING','G_MININGPLEDGE','G_FLOATING'
						// ,'G_FLOATPLEDGE','G_DEPOSIT','G_DEPOSITPLEDGE','G_VEHICLE','G_VEHICLEPLEDGE','G_RECEIVABLEMONEY','G_RECEIVABLEMPLEDGE','G_STOCK','G_STOCKPLEDGE','A_FILETYPE','B_FILETYPE','C_FILETYPE','CONCLUSION','ISNEW','STATE','MOVER_USER'],
						fields : [
								{
									name : 'CUS_ID',
									text : '客户编号',
									searchField : true,
									xtype : 'textfield',
									readOnly : false
								},
								{
									name : 'CUS_NAME',
									text : '客户名称',
									xtype : 'textfield',
									searchField : true,
									maxLength : 80,
									allowBlank : false
								},
								{
									name : 'CUS_PHONE',
									text : '客户电话',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'ATTEN_NAME',
									text : '联系人姓名',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'ATTEN_BUSI',
									text : '联系人职务',
									gridField : true,
									translateType : 'XD000007'
								},
								{
									name : 'ATTEN_PHONE',
									text : '联系人电话',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'LEGAL_NAME',
									text : '法人代表姓名',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'REQ_CURRENCY',
									text : '币种',
									gridField : true,
									translateType : 'CURRENCY'
								},
								{
									name : 'REG_CAP_AMT',
									text : '注册资金(万元)',
									gridField : true,
									dataType : 'number'
								},
								{
									name : 'CUS_RESOURCE',
									text : '客户来源',
									gridField : true,
									translateType : 'CUST_SOURCE',
									editable : true,
									listeners : {
										select : function() {
											var flag = this.value;
											if (flag == 20) {
												getCurrentView().contentPanel.form
														.findField(
																"CUS_RESOURCE_BAK4")
														.show();
												getCurrentView().contentPanel.form
														.findField("CUS_RESOURCE_BAK4").allowBlank = false;
											} else {
												getCurrentView().contentPanel.form
														.findField(
																"CUS_RESOURCE_BAK4")
														.hide();
												getCurrentView().contentPanel.form
														.findField(
																"CUS_RESOURCE_BAK4")
														.setValue('');
												getCurrentView().contentPanel.form
														.findField("CUS_RESOURCE_BAK4").allowBlank = true;
											}
										}
									}
								}, {
									name : 'CUS_RESOURCE_BAK1',
									text : '客户来源备注1',
									gridField : true
								}, {
									name : 'CUS_RESOURCE_BAK2',
									text : '客户来源备注2',
									gridField : true
								}, {
									name : 'CUS_RESOURCE_BAK3',
									text : '客户来源备注3',
									gridField : true
								}, {
									name : 'CUS_RESOURCE_BAK4',
									text : '来源备注',
									maxLength : 80,
									gridField : true
								}, {
									name : 'ACT_CTL_NAME',
									text : '实际控制人姓名',
									maxLength : 20,
									gridField : false
								}, {
									name : 'ACT_CTL_PHONE',
									text : '实际控制人电话',
									maxLength : 20,
									gridField : false
								}, {
									name : 'ACT_CTL_WIFE',
									text : '实际控制人配偶姓名',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_INFO1',
									text : '第一持股人',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_RATE1',
									text : '第一持股人比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'PARTNER_INFO2',
									text : '第二持股人',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_RATE2',
									text : '第二持股人比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'PARTNER_INFO3',
									text : '第三持股人',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_RATE3',
									text : '第三持股人比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'AMOUNT2',
									text : '前年净利润(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'AMOUNT1',
									text : '上年净利润(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'AMOUNT',
									text : '当期总收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'PRE_AMOUNT',
									text : '今年预计净利润(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'AVE_BALANCE',
									text : '平均每月银行账户余额(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'TOTAL_ASS',
									text : '企业总资产(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'LICENSE_FLAG',
									text : '能否提供营业执照',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'TAX_REC_FLAG',
									text : '能否提供企业纳税凭证',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'DEBT_AMOUNT',
									text : '企业总负债(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'CAP_AMOUNT',
									text : '企业自有资金(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'LOAN_AMOUNT',
									text : '银行贷款总额(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'FINA_AMOUNT',
									text : '其他渠道融资(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'CREDIT_CARD_FLAG',
									text : '有无企业贷款卡',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'DEBIT_FLAG',
									text : '是否有欠息情况',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'BAD_CREDIT_FLAG',
									text : '是否有未结清的不良贷款信息',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'PER_CARD_FLAG',
									text : '个人是否拥有信用卡',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'CREDIT_CARD_BANK',
									text : '个人信用卡第一发卡行',
									gridField : false
								}, {
									name : 'PRE_CREDIT_AMOUNT',
									text : '期望贷款金额(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'CREDIT_USE',
									text : '贷款用途',
									gridField : false,
									maxLength : 80,
									translateType : 'CREDIT_USE'
								}, {
									name : 'TERM',
									text : '贷款期限(月)',
									gridField : false,
									dataType : 'number',
									allowDecimals : false,
									allowNegative : false
								}, {
									name : 'REPAY_SOURCE',
									text : '还款来源',
									maxLength : 80,
									gridField : false
								}, {
									name : 'SUP_INF',
									text : '第一供货商',
									maxLength : 80,
									gridField : false
								}, {
									name : 'SUP_INF_RATE',
									text : '第一供货商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'SUP_INF_S',
									text : '第二供账商',
									maxLength : 100,
									gridField : false
								}, {
									name : 'SUP_INF_S_RATE',
									text : '第二供账商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'BUYER_INF',
									text : '第一买售商',
									maxLength : 80,
									gridField : false
								}, {
									name : 'BUYER_INF_RATE',
									text : '第一买售商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'BUYER_INF_S',
									text : '第二买售商',
									maxLength : 100,
									gridField : false
								}, {
									name : 'BUYER_INF_S_RATE',
									text : '第二买售商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'RELATION_COM',
									text : '关联企业1',
									maxLength : 80,
									gridField : false
								}, {
									name : 'RELATION_COM_S',
									text : '关联企业2',
									maxLength : 20,
									gridField : false
								}, {
									name : 'GUA_MOR_FLAG',
									text : '企业以下信息是否拥有以下担保，是否有抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'CUST_MGR',
									text : '主管客户经理',
									gridField : true
								}, {
									name : 'Q_CUSTOMERTYPE',
									text : '客户经理类型',
									gridField : false,
									translateType : 'MANAGER_TYPE'
								}, {
									name : 'Q_INTERVIEWEENAME',
									text : '受访人姓名',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_INTERVIEWEEPOST',
									text : '受访人职位',
									gridField : false,
									translateType : 'XD000007'
								}, {
									name : 'Q_OPERATEYEARS',
									text : '企业经营年限',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_BUSINESS',
									text : '行业',
									gridField : false,
									translateType : 'FJQZKH_OWNBUSI'
								}, {
									name : 'Q_MARKETIN',
									text : '销售收入',
									gridField : false,
									translateType : 'Q_MARKETIN'
								}, {
									name : 'Q_ASSTOTAL',
									text : '总资产(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_MAGYEARS',
									text : '核心管理经验',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_WORKYEARS',
									text : '从业年限',
									gridField : false,
									dataType : 'number',
									allowDecimals : false,
									allowNegative : false
								}, {
									name : 'Q_FOUNDEDYEARS',
									text : '成立年份',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_CREDITLIMIT',
									text : '申请贷款额度(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_ADDRYEARS',
									text : '现址经营年限',
									dataType : 'number',
									gridField : false
								}, {
									name : 'Q_PYEARINCOME',
									text : '前年销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_LYEARINCOME',
									text : '去年销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_TOTALINCOME',
									text : '今年累计销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_PLANINCOME',
									text : '今年计划销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'G_HOUSE',
									text : '房产',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_HOUSEPLEDGE',
									text : '房产抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_LAND',
									text : '土地使用权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_LANDPLEDGE',
									text : '土地使用权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_EQUIPMENT',
									text : '设备',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_EQUIPMENTPLEDGE',
									text : '设备抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FOREST',
									text : '林权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FORESTPLEDGE',
									text : '林权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_MINING',
									text : '采矿权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_MININGPLEDGE',
									text : '采矿权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FLOATING',
									text : '浮劢抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FLOATPLEDGE',
									text : '浮劢抵押货押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_DEPOSIT',
									text : '存款',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_DEPOSITPLEDGE',
									text : '存款抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_VEHICLE',
									text : '车辆',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_VEHICLEPLEDGE',
									text : '车辆抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_RECEIVABLEMONEY',
									text : '应收败款',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_RECEIVABLEMPLEDGE',
									text : '应收败款抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_STOCK',
									text : '股权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_STOCKPLEDGE',
									text : '股权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'A_FILETYPE',
									text : 'A类文件',
									gridField : false,
									translateType : 'A_FILETYPE',
									multiSelect : true,
									multiSeparator : ','
								}, {
									name : 'B_FILETYPE',
									text : 'B类文件',
									gridField : false,
									translateType : 'B_FILETYPE',
									multiSelect : true,
									multiSeparator : ','
								}, {
									name : 'C_FILETYPE',
									text : 'C类文件',
									gridField : false,
									translateType : 'C_FILETYPE'
								}, {
									name : 'CONCLUSION',
									text : '移动信贷客户筛选状态',
									gridField : false,
									translateType : 'CONCLUSION',
									allowBlank : true
								}, {
									name : 'ISNEW',
									text : '是否移动信贷本地自建',
									gridField : true,
									translateType : 'IF_FLAG'
								}, {
									name : 'STATE',
									text : '潜在客户状态',
									gridField : true,
									translateType : 'POTENTAIL_STATE'
								}, {
									name : 'MOVER_USER',
									text : '移交分配人',
									gridField : true
								}, {
									name : 'REMARK',
									text : '备注',
									gridField : true,
									xtype : 'textarea',
									maxLength : 800
								} ],
						fn : function(CUS_ID, CUS_NAME, CUS_PHONE, ATTEN_NAME,
								ATTEN_BUSI, ATTEN_PHONE, LEGAL_NAME,
								REQ_CURRENCY, REG_CAP_AMT, CUS_RESOURCE,
								CUS_RESOURCE_BAK1, CUS_RESOURCE_BAK2,
								CUS_RESOURCE_BAK3, CUS_RESOURCE_BAK4,
								ACT_CTL_NAME, ACT_CTL_PHONE, ACT_CTL_WIFE,
								PARTNER_INFO1, PARTNER_RATE1, PARTNER_INFO2,
								PARTNER_RATE2, PARTNER_INFO3, PARTNER_RATE3,
								AMOUNT2, AMOUNT1, AMOUNT, PRE_AMOUNT,
								AVE_BALANCE, TOTAL_ASS, LICENSE_FLAG,
								TAX_REC_FLAG, DEBT_AMOUNT, CAP_AMOUNT,
								LOAN_AMOUNT, FINA_AMOUNT, CREDIT_CARD_FLAG,
								DEBIT_FLAG, BAD_CREDIT_FLAG, PER_CARD_FLAG,
								CREDIT_CARD_BANK, PRE_CREDIT_AMOUNT,
								CREDIT_USE, TERM, REPAY_SOURCE, SUP_INF,
								SUP_INF_RATE, SUP_INF_S, SUP_INF_S_RATE,
								BUYER_INF, BUYER_INF_RATE, BUYER_INF_S,
								BUYER_INF_S_RATE, RELATION_COM, RELATION_COM_S,
								GUA_MOR_FLAG, CUST_MGR, Q_CUSTOMERTYPE,
								Q_INTERVIEWEENAME, Q_INTERVIEWEEPOST,
								Q_OPERATEYEARS, Q_BUSINESS, Q_MARKETIN,
								Q_ASSTOTAL, Q_MAGYEARS, Q_WORKYEARS,
								Q_FOUNDEDYEARS, Q_CREDITLIMIT, Q_ADDRYEARS,
								Q_PYEARINCOME, Q_LYEARINCOME, Q_TOTALINCOME,
								Q_PLANINCOME, G_HOUSE, G_HOUSEPLEDGE, G_LAND,
								G_LANDPLEDGE, G_EQUIPMENT, G_EQUIPMENTPLEDGE,
								G_FOREST, G_FORESTPLEDGE, G_MINING,
								G_MININGPLEDGE, G_FLOATING, G_FLOATPLEDGE,
								G_DEPOSIT, G_DEPOSITPLEDGE, G_VEHICLE,
								G_VEHICLEPLEDGE, G_RECEIVABLEMONEY,
								G_RECEIVABLEMPLEDGE, G_STOCK, G_STOCKPLEDGE,
								A_FILETYPE, B_FILETYPE, C_FILETYPE, CONCLUSION,
								ISNEW, STATE, MOVER_USER) {
							CONCLUSION.readOnly = true;
							CONCLUSION.cls = "x-readOnly";
							CUS_RESOURCE_BAK1.readOnly = true;
							CUS_RESOURCE_BAK1.cls = "x-readOnly";
							CUS_RESOURCE_BAK2.readOnly = true;
							CUS_RESOURCE_BAK2.cls = "x-readOnly";
							CUS_RESOURCE_BAK3.readOnly = true;
							CUS_RESOURCE_BAK3.cls = "x-readOnly";
							CUS_ID.hidden = true;
							STATE.hidden = true;
							MOVER_USER.hidden = true;
							return [ CUS_ID, CUS_NAME, CUS_PHONE, ATTEN_NAME,
									ATTEN_BUSI, ATTEN_PHONE, LEGAL_NAME,
									REQ_CURRENCY, REG_CAP_AMT, CUS_RESOURCE,
									CUS_RESOURCE_BAK1, CUS_RESOURCE_BAK2,
									CUS_RESOURCE_BAK3, CUS_RESOURCE_BAK4,
									ACT_CTL_NAME, ACT_CTL_PHONE, ACT_CTL_WIFE,
									PARTNER_INFO1, PARTNER_RATE1,
									PARTNER_INFO2, PARTNER_RATE2,
									PARTNER_INFO3, PARTNER_RATE3, AMOUNT2,
									AMOUNT1, AMOUNT, PRE_AMOUNT, AVE_BALANCE,
									TOTAL_ASS, LICENSE_FLAG, TAX_REC_FLAG,
									DEBT_AMOUNT, CAP_AMOUNT, LOAN_AMOUNT,
									FINA_AMOUNT, CREDIT_CARD_FLAG, DEBIT_FLAG,
									BAD_CREDIT_FLAG, PER_CARD_FLAG,
									CREDIT_CARD_BANK, PRE_CREDIT_AMOUNT,
									CREDIT_USE, TERM, REPAY_SOURCE, SUP_INF,
									SUP_INF_RATE, SUP_INF_S, SUP_INF_S_RATE,
									BUYER_INF, BUYER_INF_RATE, BUYER_INF_S,
									BUYER_INF_S_RATE, RELATION_COM,
									RELATION_COM_S, GUA_MOR_FLAG, CUST_MGR,
									Q_CUSTOMERTYPE, Q_INTERVIEWEENAME,
									Q_INTERVIEWEEPOST, Q_OPERATEYEARS,
									Q_BUSINESS, Q_MARKETIN, Q_ASSTOTAL,
									Q_MAGYEARS, Q_WORKYEARS, Q_FOUNDEDYEARS,
									Q_CREDITLIMIT, Q_ADDRYEARS, Q_PYEARINCOME,
									Q_LYEARINCOME, Q_TOTALINCOME, Q_PLANINCOME,
									G_HOUSE, G_HOUSEPLEDGE, G_LAND,
									G_LANDPLEDGE, G_EQUIPMENT,
									G_EQUIPMENTPLEDGE, G_FOREST,
									G_FORESTPLEDGE, G_MINING, G_MININGPLEDGE,
									G_FLOATING, G_FLOATPLEDGE, G_DEPOSIT,
									G_DEPOSITPLEDGE, G_VEHICLE,
									G_VEHICLEPLEDGE, G_RECEIVABLEMONEY,
									G_RECEIVABLEMPLEDGE, G_STOCK,
									G_STOCKPLEDGE, A_FILETYPE, B_FILETYPE,
									C_FILETYPE, CONCLUSION, ISNEW, STATE,
									MOVER_USER ];
						}
					}, {
						columnCount : 1,
						fields : [ {
							name : 'CUS_ADDR',
							text : '客户地址',
							gridField : true,
							xtype : 'textarea',
							maxLength : 80
						} ],
						fn : function(CUS_ADDR) {
							return [ CUS_ADDR ];
						}
					}, {
						columnCount : 1,
						fields : [ 'REMARK' ],
						fn : function(REMARK) {
							return [ REMARK ];
						}
					} ],
			formButtons : [ {
				text : '保存',
				fn : function(formPanel, basicForm) {
					if (!formPanel.getForm().isValid()) {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
					;
					var data = formPanel.getForm().getFieldValues();
					var cusName = data.CUS_NAME;
					Ext.Ajax
							.request({
								url : basepath
										+ '/acrmFCiPotCusCom!doNameCheck.json',
								method : 'GET',
								params : {
									cusName : cusName
								},
								success : function(response) {
									var ret = Ext.decode(response.responseText);
									var nametype = ret.type;
									if (nametype == '1') {
										Ext.MessageBox.alert('提示',
												'客户已是正式客户，不允许新建');
										return false;
									} else if (nametype == '2') {
										Ext.MessageBox
												.confirm(
														'提示',
														'有类似客户，确认是否新建?',
														function(buttonId) {
															if (buttonId
																	.toLowerCase() == "no") {
																return false;
															} else {
																var commintData = translateDataKey(
																		data, 1);
																Ext.Msg
																		.wait(
																				'正在处理，请稍后......',
																				'系统提示');
																Ext.Ajax
																		.request({
																			url : basepath
																					+ '/acrmFCiPotCusCom!saveData.json',
																			method : 'GET',
																			params : commintData,
																			success : function(
																					response) {
																				Ext.Msg
																						.alert(
																								'提示',
																								'保存成功!');
																				reloadCurrentData();
																			},
																			failure : function() {
																				Ext.Msg
																						.alert(
																								'提示',
																								'保存失败!');
																				reloadCurrentData();
																			}
																		});
															}
														});
									} else if (nametype == '3') {
										var commintData = translateDataKey(
												data, 1);
										Ext.Msg.wait('正在处理，请稍后......', '系统提示');
										Ext.Ajax
												.request({
													url : basepath
															+ '/acrmFCiPotCusCom!saveData.json',
													method : 'GET',
													params : commintData,
													success : function(response) {
														Ext.Msg.alert('提示',
																'保存成功!');
														reloadCurrentData();
													},
													failure : function() {
														Ext.Msg.alert('提示',
																'保存失败!');
														reloadCurrentData();
													}
												});
									}
								}
							});

				}
			} ]
		},
		{
			title : '修改',
			type : 'form',
			autoLoadSeleted : true,
			groups : [
					{
						columnCount : 2,
						// fields :
						// ['CUS_ID','CUS_NAME','CUS_PHONE','ATTEN_NAME','ATTEN_BUSI','ATTEN_PHONE','LEGAL_NAME','REQ_CURRENCY','REG_CAP_AMT','CUS_RESOURCE','CUS_RESOURCE_BAK1','CUS_RESOURCE_BAK2','CUS_RESOURCE_BAK3','ACT_CTL_NAME','ACT_CTL_PHONE','ACT_CTL_WIFE','PARTNER_INFO1'
						// ,'PARTNER_RATE1','PARTNER_INFO2','PARTNER_RATE2','PARTNER_INFO3','PARTNER_RATE3','AMOUNT2','AMOUNT1','AMOUNT','PRE_AMOUNT','AVE_BALANCE','TOTAL_ASS','LICENSE_FLAG','TAX_REC_FLAG','DEBT_AMOUNT'
						// ,'CAP_AMOUNT','LOAN_AMOUNT','FINA_AMOUNT','CREDIT_CARD_FLAG','DEBIT_FLAG','BAD_CREDIT_FLAG','PER_CARD_FLAG','CREDIT_CARD_BANK','PRE_CREDIT_AMOUNT','CREDIT_USE','TERM','REPAY_SOURCE'
						// ,'SUP_INF','SUP_INF_RATE','SUP_INF_S','SUP_INF_S_RATE','BUYER_INF','BUYER_INF_RATE','BUYER_INF_S','BUYER_INF_S_RATE','RELATION_COM','RELATION_COM_S','GUA_MOR_FLAG','CUST_MGR','Q_CUSTOMERTYPE'
						// ,'Q_INTERVIEWEENAME','Q_INTERVIEWEEPOST','Q_OPERATEYEARS','Q_BUSINESS','Q_MARKETIN','Q_ASSTOTAL','Q_MAGYEARS','Q_WORKYEARS','Q_FOUNDEDYEARS','Q_CREDITLIMIT','Q_ADDRYEARS','Q_PYEARINCOME','Q_LYEARINCOME'
						// ,'Q_TOTALINCOME','Q_PLANINCOME','G_HOUSE','G_HOUSEPLEDGE','G_LAND','G_LANDPLEDGE','G_EQUIPMENT','G_EQUIPMENTPLEDGE','G_FOREST','G_FORESTPLEDGE','G_MINING','G_MININGPLEDGE','G_FLOATING'
						// ,'G_FLOATPLEDGE','G_DEPOSIT','G_DEPOSITPLEDGE','G_VEHICLE','G_VEHICLEPLEDGE','G_RECEIVABLEMONEY','G_RECEIVABLEMPLEDGE','G_STOCK','G_STOCKPLEDGE','A_FILETYPE','B_FILETYPE','C_FILETYPE','CONCLUSION','ISNEW','STATE','MOVER_USER'],
						fields : [
								{
									name : 'CUS_ID',
									text : '客户编号',
									searchField : true,
									xtype : 'textfield',
									readOnly : false
								},
								{
									name : 'CUS_NAME',
									text : '客户名称',
									xtype : 'textfield',
									searchField : true,
									maxLength : 80,
									allowBlank : false
								},
								{
									name : 'CUS_PHONE',
									text : '客户电话',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'ATTEN_NAME',
									text : '联系人姓名',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'ATTEN_BUSI',
									text : '联系人职务',
									gridField : true,
									translateType : 'XD000007'
								},
								{
									name : 'ATTEN_PHONE',
									text : '联系人电话',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'LEGAL_NAME',
									text : '法人代表姓名',
									maxLength : 20,
									gridField : true
								},
								{
									name : 'REQ_CURRENCY',
									text : '币种',
									gridField : true,
									translateType : 'CURRENCY'
								},
								{
									name : 'REG_CAP_AMT',
									text : '注册资金(万元)',
									gridField : true,
									dataType : 'number'
								},
								{
									name : 'CUS_RESOURCE',
									text : '客户来源',
									gridField : true,
									translateType : 'CUST_SOURCE',
									editable : true,
									listeners : {
										select : function() {
											var flag = this.value;
											if (flag == 20) {
												getCurrentView().contentPanel.form
														.findField(
																"CUS_RESOURCE_BAK4")
														.show();
												getCurrentView().contentPanel.form
														.findField("CUS_RESOURCE_BAK4").allowBlank = false;
											} else {
												getCurrentView().contentPanel.form
														.findField(
																"CUS_RESOURCE_BAK4")
														.hide();
												getCurrentView().contentPanel.form
														.findField(
																"CUS_RESOURCE_BAK4")
														.setValue('');
												getCurrentView().contentPanel.form
														.findField("CUS_RESOURCE_BAK4").allowBlank = true;
											}
										}
									}
								}, {
									name : 'CUS_RESOURCE_BAK1',
									text : '客户来源备注1',
									gridField : true
								}, {
									name : 'CUS_RESOURCE_BAK2',
									text : '客户来源备注2',
									gridField : true
								}, {
									name : 'CUS_RESOURCE_BAK3',
									text : '客户来源备注3',
									gridField : true
								}, {
									name : 'CUS_RESOURCE_BAK4',
									text : '来源备注',
									maxLength : 80,
									gridField : true
								}, {
									name : 'ACT_CTL_NAME',
									text : '实际控制人姓名',
									maxLength : 20,
									gridField : false
								}, {
									name : 'ACT_CTL_PHONE',
									text : '实际控制人电话',
									maxLength : 20,
									gridField : false
								}, {
									name : 'ACT_CTL_WIFE',
									text : '实际控制人配偶姓名',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_INFO1',
									text : '第一持股人',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_RATE1',
									text : '第一持股人比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'PARTNER_INFO2',
									text : '第二持股人',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_RATE2',
									text : '第二持股人比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'PARTNER_INFO3',
									text : '第三持股人',
									maxLength : 20,
									gridField : false
								}, {
									name : 'PARTNER_RATE3',
									text : '第三持股人比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'AMOUNT2',
									text : '前年净利润(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'AMOUNT1',
									text : '上年净利润(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'AMOUNT',
									text : '当期总收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'PRE_AMOUNT',
									text : '今年预计净利润(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'AVE_BALANCE',
									text : '平均每月银行账户余额(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'TOTAL_ASS',
									text : '企业总资产(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'LICENSE_FLAG',
									text : '能否提供营业执照',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'TAX_REC_FLAG',
									text : '能否提供企业纳税凭证',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'DEBT_AMOUNT',
									text : '企业总负债(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'CAP_AMOUNT',
									text : '企业自有资金(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'LOAN_AMOUNT',
									text : '银行贷款总额(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'FINA_AMOUNT',
									text : '其他渠道融资(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'CREDIT_CARD_FLAG',
									text : '有无企业贷款卡',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'DEBIT_FLAG',
									text : '是否有欠息情况',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'BAD_CREDIT_FLAG',
									text : '是否有未结清的不良贷款信息',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'PER_CARD_FLAG',
									text : '个人是否拥有信用卡',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'CREDIT_CARD_BANK',
									text : '个人信用卡第一发卡行',
									gridField : false
								}, {
									name : 'PRE_CREDIT_AMOUNT',
									text : '期望贷款金额(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'CREDIT_USE',
									text : '贷款用途',
									maxLength : 80,
									gridField : false,
									translateType : 'CREDIT_USE'
								}, {
									name : 'TERM',
									text : '贷款期限(月)',
									gridField : false,
									dataType : 'number',
									allowDecimals : false,
									allowNegative : false
								}, {
									name : 'REPAY_SOURCE',
									text : '还款来源',
									maxLength : 80,
									gridField : false
								}, {
									name : 'SUP_INF',
									text : '第一供货商',
									maxLength : 80,
									gridField : false
								}, {
									name : 'SUP_INF_RATE',
									text : '第一供货商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'SUP_INF_S',
									text : '第二供账商',
									maxLength : 100,
									gridField : false
								}, {
									name : 'SUP_INF_S_RATE',
									text : '第二供账商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'BUYER_INF',
									text : '第一买售商',
									maxLength : 80,
									gridField : false
								}, {
									name : 'BUYER_INF_RATE',
									text : '第一买售商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'BUYER_INF_S',
									text : '第二买售商',
									maxLength : 100,
									gridField : false
								}, {
									name : 'BUYER_INF_S_RATE',
									text : '第二买售商比例',
									gridField : false,
									dataType : 'number',
									allowNegative : false
								}, {
									name : 'RELATION_COM',
									text : '关联企业1',
									maxLength : 80,
									gridField : false
								}, {
									name : 'RELATION_COM_S',
									text : '关联企业2',
									maxLength : 20,
									gridField : false
								}, {
									name : 'GUA_MOR_FLAG',
									text : '企业以下信息是否拥有以下担保，是否有抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'CUST_MGR',
									text : '主管客户经理',
									gridField : true
								}, {
									name : 'Q_CUSTOMERTYPE',
									text : '客户经理类型',
									gridField : false,
									translateType : 'MANAGER_TYPE'
								}, {
									name : 'Q_INTERVIEWEENAME',
									text : '受访人姓名',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_INTERVIEWEEPOST',
									text : '受访人职位',
									gridField : false,
									translateType : 'XD000007'
								}, {
									name : 'Q_OPERATEYEARS',
									text : '企业经营年限',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_BUSINESS',
									text : '行业',
									gridField : false,
									translateType : 'FJQZKH_OWNBUSI'
								}, {
									name : 'Q_MARKETIN',
									text : '销售收入',
									gridField : false,
									translateType : 'Q_MARKETIN'
								}, {
									name : 'Q_ASSTOTAL',
									text : '总资产(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_MAGYEARS',
									text : '核心管理经验',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_WORKYEARS',
									text : '从业年限',
									gridField : false,
									dataType : 'number',
									allowDecimals : false,
									allowNegative : false
								}, {
									name : 'Q_FOUNDEDYEARS',
									text : '成立年份',
									maxLength : 20,
									gridField : false
								}, {
									name : 'Q_CREDITLIMIT',
									text : '申请贷款额度(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_ADDRYEARS',
									text : '现址经营年限',
									gridField : false
								}, {
									name : 'Q_PYEARINCOME',
									text : '前年销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_LYEARINCOME',
									text : '去年销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_TOTALINCOME',
									text : '今年累计销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'Q_PLANINCOME',
									text : '今年计划销售收入(折人民币/千元)',
									gridField : false,
									dataType : 'number',
									viewFn : money('0,000.00'),
									allowNegative : false
								}, {
									name : 'G_HOUSE',
									text : '房产',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_HOUSEPLEDGE',
									text : '房产抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_LAND',
									text : '土地使用权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_LANDPLEDGE',
									text : '土地使用权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_EQUIPMENT',
									text : '设备',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_EQUIPMENTPLEDGE',
									text : '设备抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FOREST',
									text : '林权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FORESTPLEDGE',
									text : '林权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_MINING',
									text : '采矿权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_MININGPLEDGE',
									text : '采矿权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FLOATING',
									text : '浮劢抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_FLOATPLEDGE',
									text : '浮劢抵押货押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_DEPOSIT',
									text : '存款',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_DEPOSITPLEDGE',
									text : '存款抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_VEHICLE',
									text : '车辆',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_VEHICLEPLEDGE',
									text : '车辆抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_RECEIVABLEMONEY',
									text : '应收败款',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_RECEIVABLEMPLEDGE',
									text : '应收败款抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_STOCK',
									text : '股权',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'G_STOCKPLEDGE',
									text : '股权抵押',
									gridField : false,
									translateType : 'IF_FLAG'
								}, {
									name : 'A_FILETYPE',
									text : 'A类文件',
									gridField : false,
									translateType : 'A_FILETYPE',
									multiSelect : true,
									multiSeparator : ','
								}, {
									name : 'B_FILETYPE',
									text : 'B类文件',
									gridField : false,
									translateType : 'B_FILETYPE',
									multiSelect : true,
									multiSeparator : ','
								}, {
									name : 'C_FILETYPE',
									text : 'C类文件',
									gridField : false,
									translateType : 'C_FILETYPE'
								}, {
									name : 'CONCLUSION',
									text : '移动信贷客户筛选状态',
									gridField : false,
									translateType : 'CONCLUSION',
									allowBlank : true
								}, {
									name : 'ISNEW',
									text : '是否移动信贷本地自建',
									gridField : true,
									translateType : 'IF_FLAG'
								}, {
									name : 'STATE',
									text : '潜在客户状态',
									gridField : true,
									translateType : 'POTENTAIL_STATE'
								}, {
									name : 'MOVER_USER',
									text : '移交分配人',
									gridField : true
								}, {
									name : 'REMARK',
									text : '备注',
									gridField : true,
									xtype : 'textarea',
									maxLength : 800
								} ],
						fn : function(CUS_ID, CUS_NAME, CUS_PHONE, ATTEN_NAME,
								ATTEN_BUSI, ATTEN_PHONE, LEGAL_NAME,
								REQ_CURRENCY, REG_CAP_AMT, CUS_RESOURCE,
								CUS_RESOURCE_BAK1, CUS_RESOURCE_BAK2,
								CUS_RESOURCE_BAK3, CUS_RESOURCE_BAK4,
								ACT_CTL_NAME, ACT_CTL_PHONE, ACT_CTL_WIFE,
								PARTNER_INFO1, PARTNER_RATE1, PARTNER_INFO2,
								PARTNER_RATE2, PARTNER_INFO3, PARTNER_RATE3,
								AMOUNT2, AMOUNT1, AMOUNT, PRE_AMOUNT,
								AVE_BALANCE, TOTAL_ASS, LICENSE_FLAG,
								TAX_REC_FLAG, DEBT_AMOUNT, CAP_AMOUNT,
								LOAN_AMOUNT, FINA_AMOUNT, CREDIT_CARD_FLAG,
								DEBIT_FLAG, BAD_CREDIT_FLAG, PER_CARD_FLAG,
								CREDIT_CARD_BANK, PRE_CREDIT_AMOUNT,
								CREDIT_USE, TERM, REPAY_SOURCE, SUP_INF,
								SUP_INF_RATE, SUP_INF_S, SUP_INF_S_RATE,
								BUYER_INF, BUYER_INF_RATE, BUYER_INF_S,
								BUYER_INF_S_RATE, RELATION_COM, RELATION_COM_S,
								GUA_MOR_FLAG, CUST_MGR, Q_CUSTOMERTYPE,
								Q_INTERVIEWEENAME, Q_INTERVIEWEEPOST,
								Q_OPERATEYEARS, Q_BUSINESS, Q_MARKETIN,
								Q_ASSTOTAL, Q_MAGYEARS, Q_WORKYEARS,
								Q_FOUNDEDYEARS, Q_CREDITLIMIT, Q_ADDRYEARS,
								Q_PYEARINCOME, Q_LYEARINCOME, Q_TOTALINCOME,
								Q_PLANINCOME, G_HOUSE, G_HOUSEPLEDGE, G_LAND,
								G_LANDPLEDGE, G_EQUIPMENT, G_EQUIPMENTPLEDGE,
								G_FOREST, G_FORESTPLEDGE, G_MINING,
								G_MININGPLEDGE, G_FLOATING, G_FLOATPLEDGE,
								G_DEPOSIT, G_DEPOSITPLEDGE, G_VEHICLE,
								G_VEHICLEPLEDGE, G_RECEIVABLEMONEY,
								G_RECEIVABLEMPLEDGE, G_STOCK, G_STOCKPLEDGE,
								A_FILETYPE, B_FILETYPE, C_FILETYPE, CONCLUSION,
								ISNEW, STATE, MOVER_USER) {
							CUS_ID.hidden = true;
							CUST_MGR.readOnly = true;
							CUST_MGR.cls = "x-readOnly";
							CONCLUSION.readOnly = true;
							CONCLUSION.cls = "x-readOnly";
							CUS_RESOURCE_BAK1.readOnly = true;
							CUS_RESOURCE_BAK1.cls = "x-readOnly";
							CUS_RESOURCE_BAK2.readOnly = true;
							CUS_RESOURCE_BAK2.cls = "x-readOnly";
							CUS_RESOURCE_BAK3.readOnly = true;
							CUS_RESOURCE_BAK3.cls = "x-readOnly";
							STATE.hidden = true;
							MOVER_USER.hidden = true;
							return [ CUS_ID, CUS_NAME, CUS_PHONE, ATTEN_NAME,
									ATTEN_BUSI, ATTEN_PHONE, LEGAL_NAME,
									REQ_CURRENCY, REG_CAP_AMT, CUS_RESOURCE,
									CUS_RESOURCE_BAK1, CUS_RESOURCE_BAK2,
									CUS_RESOURCE_BAK3, CUS_RESOURCE_BAK4,
									ACT_CTL_NAME, ACT_CTL_PHONE, ACT_CTL_WIFE,
									PARTNER_INFO1, PARTNER_RATE1,
									PARTNER_INFO2, PARTNER_RATE2,
									PARTNER_INFO3, PARTNER_RATE3, AMOUNT2,
									AMOUNT1, AMOUNT, PRE_AMOUNT, AVE_BALANCE,
									TOTAL_ASS, LICENSE_FLAG, TAX_REC_FLAG,
									DEBT_AMOUNT, CAP_AMOUNT, LOAN_AMOUNT,
									FINA_AMOUNT, CREDIT_CARD_FLAG, DEBIT_FLAG,
									BAD_CREDIT_FLAG, PER_CARD_FLAG,
									CREDIT_CARD_BANK, PRE_CREDIT_AMOUNT,
									CREDIT_USE, TERM, REPAY_SOURCE, SUP_INF,
									SUP_INF_RATE, SUP_INF_S, SUP_INF_S_RATE,
									BUYER_INF, BUYER_INF_RATE, BUYER_INF_S,
									BUYER_INF_S_RATE, RELATION_COM,
									RELATION_COM_S, GUA_MOR_FLAG, CUST_MGR,
									Q_CUSTOMERTYPE, Q_INTERVIEWEENAME,
									Q_INTERVIEWEEPOST, Q_OPERATEYEARS,
									Q_BUSINESS, Q_MARKETIN, Q_ASSTOTAL,
									Q_MAGYEARS, Q_WORKYEARS, Q_FOUNDEDYEARS,
									Q_CREDITLIMIT, Q_ADDRYEARS, Q_PYEARINCOME,
									Q_LYEARINCOME, Q_TOTALINCOME, Q_PLANINCOME,
									G_HOUSE, G_HOUSEPLEDGE, G_LAND,
									G_LANDPLEDGE, G_EQUIPMENT,
									G_EQUIPMENTPLEDGE, G_FOREST,
									G_FORESTPLEDGE, G_MINING, G_MININGPLEDGE,
									G_FLOATING, G_FLOATPLEDGE, G_DEPOSIT,
									G_DEPOSITPLEDGE, G_VEHICLE,
									G_VEHICLEPLEDGE, G_RECEIVABLEMONEY,
									G_RECEIVABLEMPLEDGE, G_STOCK,
									G_STOCKPLEDGE, A_FILETYPE, B_FILETYPE,
									C_FILETYPE, CONCLUSION, ISNEW, STATE,
									MOVER_USER ];
						}
					}, {
						columnCount : 1,
						fields : [{name : 'CUS_ADDR',
									text : '客户地址',
									maxLength:80,
									xtype : 'textarea'} ],
						fn : function(CUS_ADDR) {
							return [ CUS_ADDR ];
						}
					}, {
						columnCount : 1,
						fields : [ 'REMARK' ],
						fn : function(REMARK) {
							return [ REMARK ];
						}
					} ],
			formButtons : [ {
				text : '保存',
				fn : function(formPanel, basicForm) {
					if (!formPanel.getForm().isValid()) {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
					;
					var data = formPanel.getForm().getFieldValues();
					var cusName = data.CUS_NAME;
					Ext.Msg.wait('正在处理，请稍后......', '系统提示');
					Ext.Ajax
							.request({
								url : basepath
										+ '/acrmFCiPotCusCom!doNameCheck.json',
								method : 'GET',
								params : {
									cusName : cusName
								},
								success : function(response) {
									var ret = Ext.decode(response.responseText);
									var nametype = ret.type;
									if (nametype == '1') {
										Ext.MessageBox.alert('提示',
												'客户已是正式客户，不允许修改');
										return false;
									} else if (nametype == '2') {
										Ext.MessageBox
												.confirm(
														'提示',
														'有类似客户，确认是否修改?',
														function(buttonId) {
															if (buttonId
																	.toLowerCase() == "no") {
																return false;
															} else {
																var commintData = translateDataKey(
																		data, 1);
																Ext.Msg
																		.wait(
																				'正在处理，请稍后......',
																				'系统提示');
																Ext.Ajax
																		.request({
																			url : basepath
																					+ '/acrmFCiPotCusCom!saveData.json',
																			method : 'GET',
																			params : commintData,
																			success : function(
																					response) {
																				Ext.Msg
																						.alert(
																								'提示',
																								'保存成功!');
																				reloadCurrentData();
																			},
																			failure : function() {
																				Ext.Msg
																						.alert(
																								'提示',
																								'保存失败!');
																				reloadCurrentData();
																			}
																		});
															}
														});
									} else if (nametype == '3') {
										var commintData = translateDataKey(
												data, 1);
										Ext.Msg.wait('正在处理，请稍后......', '系统提示');
										Ext.Ajax
												.request({
													url : basepath
															+ '/acrmFCiPotCusCom!saveData.json',
													method : 'GET',
													params : commintData,
													success : function(response) {
														Ext.Msg.alert('提示',
																'保存成功!');
														reloadCurrentData();
													},
													failure : function() {
														Ext.Msg.alert('提示',
																'保存失败!');
														reloadCurrentData();
													}
												});
									}
								}
							});
				}
			} ]

		},
		{
			title : '详情',
			type : 'form',
			autoLoadSeleted : true,
			groups : [
					{
						columnCount : 2,
						fields : [ {
							name : 'CUS_ID',
							text : '客户编号',
							searchField : true,
							xtype : 'textfield',
							readOnly : false
						}, {
							name : 'CUS_NAME',
							text : '客户名称',
							xtype : 'textfield',
							searchField : true,
							maxLength : 100,
							allowBlank : false
						}, {
							name : 'CUS_PHONE',
							text : '客户电话',
							gridField : true
						}, {
							name : 'ATTEN_NAME',
							text : '联系人姓名',
							gridField : true
						}, {
							name : 'ATTEN_BUSI',
							text : '联系人职务',
							gridField : true,
							translateType : 'XD000007'
						}, {
							name : 'ATTEN_PHONE',
							text : '联系人电话',
							gridField : true
						}, {
							name : 'LEGAL_NAME',
							text : '法人代表姓名',
							gridField : true
						}, {
							name : 'REQ_CURRENCY',
							text : '币种',
							gridField : true,
							translateType : 'CURRENCY'
						}, {
							name : 'REG_CAP_AMT',
							text : '注册资金(万元)',
							gridField : true,
							dataType : 'number'
						}, {
							name : 'CUS_RESOURCE',
							text : '客户来源',
							gridField : true,
							translateType : 'CUST_SOURCE'
						}, {
							name : 'CUS_RESOURCE_BAK1',
							text : '客户来源备注1',
							gridField : true
						}, {
							name : 'CUS_RESOURCE_BAK2',
							text : '客户来源备注2',
							gridField : true
						}, {
							name : 'CUS_RESOURCE_BAK3',
							text : '客户来源备注3',
							gridField : true
						}, {
							name : 'CUS_RESOURCE_BAK4',
							text : '来源备注',
							gridField : true
						}, {
							name : 'ACT_CTL_NAME',
							text : '实际控制人姓名',
							gridField : false
						}, {
							name : 'ACT_CTL_PHONE',
							text : '实际控制人电话',
							gridField : false
						}, {
							name : 'ACT_CTL_WIFE',
							text : '实际控制人配偶姓名',
							gridField : false
						}, {
							name : 'PARTNER_INFO1',
							text : '第一持股人',
							gridField : false
						}, {
							name : 'PARTNER_RATE1',
							text : '第一持股人比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'PARTNER_INFO2',
							text : '第二持股人',
							gridField : false
						}, {
							name : 'PARTNER_RATE2',
							text : '第二持股人比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'PARTNER_INFO3',
							text : '第三持股人',
							gridField : false
						}, {
							name : 'PARTNER_RATE3',
							text : '第三持股人比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'AMOUNT2',
							text : '前年净利润(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'AMOUNT1',
							text : '上年净利润(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'AMOUNT',
							text : '当期总收入(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'PRE_AMOUNT',
							text : '今年预计净利润(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'AVE_BALANCE',
							text : '平均每月银行账户余额(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'TOTAL_ASS',
							text : '企业总资产(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'LICENSE_FLAG',
							text : '能否提供营业执照',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'TAX_REC_FLAG',
							text : '能否提供企业纳税凭证',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'DEBT_AMOUNT',
							text : '企业总负债(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'CAP_AMOUNT',
							text : '企业自有资金(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'LOAN_AMOUNT',
							text : '银行贷款总额(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'FINA_AMOUNT',
							text : '其他渠道融资(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'CREDIT_CARD_FLAG',
							text : '有无企业贷款卡',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'DEBIT_FLAG',
							text : '是否有欠息情况',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'BAD_CREDIT_FLAG',
							text : '是否有未结清的不良贷款信息',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'PER_CARD_FLAG',
							text : '个人是否拥有信用卡',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'CREDIT_CARD_BANK',
							text : '个人信用卡第一发卡行',
							gridField : false
						}, {
							name : 'PRE_CREDIT_AMOUNT',
							text : '期望贷款金额(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'CREDIT_USE',
							text : '贷款用途',
							gridField : false,
							translateType : 'CREDIT_USE'
						}, {
							name : 'TERM',
							text : '贷款期限(月)',
							gridField : false,
							dataType : 'number',
							allowDecimals : false,
							allowNegative : false
						}, {
							name : 'REPAY_SOURCE',
							text : '还款来源',
							gridField : false
						}, {
							name : 'SUP_INF',
							text : '第一供货商',
							gridField : false
						}, {
							name : 'SUP_INF_RATE',
							text : '第一供货商比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'SUP_INF_S',
							text : '第二供账商',
							gridField : false
						}, {
							name : 'SUP_INF_S_RATE',
							text : '第二供账商比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'BUYER_INF',
							text : '第一买售商',
							gridField : false
						}, {
							name : 'BUYER_INF_RATE',
							text : '第一买售商比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'BUYER_INF_S',
							text : '第二买售商',
							gridField : false
						}, {
							name : 'BUYER_INF_S_RATE',
							text : '第二买售商比例',
							gridField : false,
							dataType : 'number',
							allowNegative : false
						}, {
							name : 'RELATION_COM',
							text : '关联企业1',
							gridField : false
						}, {
							name : 'RELATION_COM_S',
							text : '关联企业2',
							gridField : false
						}, {
							name : 'GUA_MOR_FLAG',
							text : '企业以下信息是否拥有以下担保，是否有抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'CUST_MGR',
							text : '主管客户经理',
							gridField : true
						}, {
							name : 'Q_CUSTOMERTYPE',
							text : '客户经理类型',
							gridField : false,
							translateType : 'MANAGER_TYPE'
						}, {
							name : 'Q_INTERVIEWEENAME',
							text : '受访人姓名',
							gridField : false
						}, {
							name : 'Q_INTERVIEWEEPOST',
							text : '受访人职位',
							gridField : false,
							translateType : 'XD000007'
						}, {
							name : 'Q_OPERATEYEARS',
							text : '企业经营年限',
							gridField : false
						}, {
							name : 'Q_BUSINESS',
							text : '行业',
							gridField : false,
							translateType : 'FJQZKH_OWNBUSI'
						}, {
							name : 'Q_MARKETIN',
							text : '销售收入',
							gridField : false,
							translateType : 'Q_MARKETIN'
						}, {
							name : 'Q_ASSTOTAL',
							text : '总资产(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'Q_MAGYEARS',
							text : '核心管理经验',
							gridField : false
						}, {
							name : 'Q_WORKYEARS',
							text : '从业年限',
							gridField : false,
							dataType : 'number',
							allowDecimals : false,
							allowNegative : false
						}, {
							name : 'Q_FOUNDEDYEARS',
							text : '成立年份',
							gridField : false
						}, {
							name : 'Q_CREDITLIMIT',
							text : '申请贷款额度(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'Q_ADDRYEARS',
							text : '现址经营年限',
							dataType : 'number',
							gridField : false
						}, {
							name : 'Q_PYEARINCOME',
							text : '前年销售收入(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'Q_LYEARINCOME',
							text : '去年销售收入(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'Q_TOTALINCOME',
							text : '今年累计销售收入(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'Q_PLANINCOME',
							text : '今年计划销售收入(折人民币/千元)',
							gridField : false,
							dataType : 'number',
							viewFn : money('0,000.00'),
							allowNegative : false
						}, {
							name : 'G_HOUSE',
							text : '房产',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_HOUSEPLEDGE',
							text : '房产抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_LAND',
							text : '土地使用权',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_LANDPLEDGE',
							text : '土地使用权抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_EQUIPMENT',
							text : '设备',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_EQUIPMENTPLEDGE',
							text : '设备抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_FOREST',
							text : '林权',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_FORESTPLEDGE',
							text : '林权抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_MINING',
							text : '采矿权',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_MININGPLEDGE',
							text : '采矿权抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_FLOATING',
							text : '浮劢抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_FLOATPLEDGE',
							text : '浮劢抵押货押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_DEPOSIT',
							text : '存款',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_DEPOSITPLEDGE',
							text : '存款抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_VEHICLE',
							text : '车辆',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_VEHICLEPLEDGE',
							text : '车辆抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_RECEIVABLEMONEY',
							text : '应收败款',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_RECEIVABLEMPLEDGE',
							text : '应收败款抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_STOCK',
							text : '股权',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'G_STOCKPLEDGE',
							text : '股权抵押',
							gridField : false,
							translateType : 'IF_FLAG'
						}, {
							name : 'A_FILETYPE',
							text : 'A类文件',
							gridField : false,
							translateType : 'A_FILETYPE',
							multiSelect : true,
							multiSeparator : ','
						}, {
							name : 'B_FILETYPE',
							text : 'B类文件',
							gridField : false,
							translateType : 'B_FILETYPE',
							multiSelect : true,
							multiSeparator : ','
						}, {
							name : 'C_FILETYPE',
							text : 'C类文件',
							gridField : false,
							translateType : 'C_FILETYPE'
						}, {
							name : 'CONCLUSION',
							text : '移动信贷客户筛选状态',
							gridField : false,
							translateType : 'CONCLUSION',
							allowBlank : false
						}, {
							name : 'ISNEW',
							text : '是否移动信贷本地自建',
							gridField : true,
							translateType : 'IF_FLAG'
						}, {
							name : 'STATE',
							text : '潜在客户状态',
							gridField : true,
							translateType : 'POTENTAIL_STATE'
						}, {
							name : 'MOVER_USER',
							text : '移交分配人',
							gridField : true
						}, {
							name : 'REMARK',
							text : '备注',
							gridField : true,
							xtype : 'textarea',
							maxLength : 200
						} ],
						fn : function(CUS_ID, CUS_NAME, CUS_PHONE, ATTEN_NAME,
								ATTEN_BUSI, ATTEN_PHONE, LEGAL_NAME,
								REQ_CURRENCY, REG_CAP_AMT, CUS_RESOURCE,
								CUS_RESOURCE_BAK1, CUS_RESOURCE_BAK2,
								CUS_RESOURCE_BAK3, CUS_RESOURCE_BAK4,
								ACT_CTL_NAME, ACT_CTL_PHONE, ACT_CTL_WIFE,
								PARTNER_INFO1, PARTNER_RATE1, PARTNER_INFO2,
								PARTNER_RATE2, PARTNER_INFO3, PARTNER_RATE3,
								AMOUNT2, AMOUNT1, AMOUNT, PRE_AMOUNT,
								AVE_BALANCE, TOTAL_ASS, LICENSE_FLAG,
								TAX_REC_FLAG, DEBT_AMOUNT, CAP_AMOUNT,
								LOAN_AMOUNT, FINA_AMOUNT, CREDIT_CARD_FLAG,
								DEBIT_FLAG, BAD_CREDIT_FLAG, PER_CARD_FLAG,
								CREDIT_CARD_BANK, PRE_CREDIT_AMOUNT,
								CREDIT_USE, TERM, REPAY_SOURCE, SUP_INF,
								SUP_INF_RATE, SUP_INF_S, SUP_INF_S_RATE,
								BUYER_INF, BUYER_INF_RATE, BUYER_INF_S,
								BUYER_INF_S_RATE, RELATION_COM, RELATION_COM_S,
								GUA_MOR_FLAG, CUST_MGR, Q_CUSTOMERTYPE,
								Q_INTERVIEWEENAME, Q_INTERVIEWEEPOST,
								Q_OPERATEYEARS, Q_BUSINESS, Q_MARKETIN,
								Q_ASSTOTAL, Q_MAGYEARS, Q_WORKYEARS,
								Q_FOUNDEDYEARS, Q_CREDITLIMIT, Q_ADDRYEARS,
								Q_PYEARINCOME, Q_LYEARINCOME, Q_TOTALINCOME,
								Q_PLANINCOME, G_HOUSE, G_HOUSEPLEDGE, G_LAND,
								G_LANDPLEDGE, G_EQUIPMENT, G_EQUIPMENTPLEDGE,
								G_FOREST, G_FORESTPLEDGE, G_MINING,
								G_MININGPLEDGE, G_FLOATING, G_FLOATPLEDGE,
								G_DEPOSIT, G_DEPOSITPLEDGE, G_VEHICLE,
								G_VEHICLEPLEDGE, G_RECEIVABLEMONEY,
								G_RECEIVABLEMPLEDGE, G_STOCK, G_STOCKPLEDGE,
								A_FILETYPE, B_FILETYPE, C_FILETYPE, CONCLUSION,
								ISNEW, STATE, MOVER_USER) {
							CUS_ID.readOnly = true;
							CUS_ID.cls = 'x-readOnly';
							CUS_NAME.readOnly = true;
							CUS_NAME.cls = 'x-readOnly';
							CUS_PHONE.readOnly = true;
							CUS_PHONE.cls = 'x-readOnly';
							ATTEN_NAME.readOnly = true;
							ATTEN_NAME.cls = 'x-readOnly';
							ATTEN_BUSI.readOnly = true;
							ATTEN_BUSI.cls = 'x-readOnly';
							ATTEN_PHONE.readOnly = true;
							ATTEN_PHONE.cls = 'x-readOnly';
							LEGAL_NAME.readOnly = true;
							LEGAL_NAME.cls = 'x-readOnly';
							REQ_CURRENCY.readOnly = true;
							REQ_CURRENCY.cls = 'x-readOnly';
							REG_CAP_AMT.readOnly = true;
							REG_CAP_AMT.cls = 'x-readOnly';
							CUS_RESOURCE.readOnly = true;
							CUS_RESOURCE.cls = 'x-readOnly';
							CUS_RESOURCE_BAK1.readOnly = true;
							CUS_RESOURCE_BAK1.cls = "x-readOnly";
							CUS_RESOURCE_BAK2.readOnly = true;
							CUS_RESOURCE_BAK2.cls = "x-readOnly";
							CUS_RESOURCE_BAK3.readOnly = true;
							CUS_RESOURCE_BAK3.cls = "x-readOnly";
							CUS_RESOURCE_BAK4.readOnly = true;
							CUS_RESOURCE_BAK4.cls = "x-readOnly";
							ACT_CTL_NAME.readOnly = true;
							ACT_CTL_NAME.cls = 'x-readOnly';
							ACT_CTL_PHONE.readOnly = true;
							ACT_CTL_PHONE.cls = 'x-readOnly';
							ACT_CTL_WIFE.readOnly = true;
							ACT_CTL_WIFE.cls = 'x-readOnly';
							PARTNER_INFO1.readOnly = true;
							PARTNER_INFO1.cls = 'x-readOnly';
							PARTNER_RATE1.readOnly = true;
							PARTNER_RATE1.cls = 'x-readOnly';
							PARTNER_INFO2.readOnly = true;
							PARTNER_INFO2.cls = 'x-readOnly';
							PARTNER_RATE2.readOnly = true;
							PARTNER_RATE2.cls = 'x-readOnly';
							PARTNER_INFO3.readOnly = true;
							PARTNER_INFO3.cls = 'x-readOnly';
							PARTNER_RATE3.readOnly = true;
							PARTNER_RATE3.cls = 'x-readOnly';
							AMOUNT2.readOnly = true;
							AMOUNT2.cls = 'x-readOnly';
							AMOUNT1.readOnly = true;
							AMOUNT1.cls = 'x-readOnly';
							AMOUNT.readOnly = true;
							AMOUNT.cls = 'x-readOnly';
							PRE_AMOUNT.readOnly = true;
							PRE_AMOUNT.cls = 'x-readOnly';
							AVE_BALANCE.readOnly = true;
							AVE_BALANCE.cls = 'x-readOnly';
							TOTAL_ASS.readOnly = true;
							TOTAL_ASS.cls = 'x-readOnly';
							LICENSE_FLAG.readOnly = true;
							LICENSE_FLAG.cls = 'x-readOnly';
							TAX_REC_FLAG.readOnly = true;
							TAX_REC_FLAG.cls = 'x-readOnly';
							DEBT_AMOUNT.readOnly = true;
							DEBT_AMOUNT.cls = 'x-readOnly';
							CAP_AMOUNT.readOnly = true;
							CAP_AMOUNT.cls = 'x-readOnly';
							LOAN_AMOUNT.readOnly = true;
							LOAN_AMOUNT.cls = 'x-readOnly';
							FINA_AMOUNT.readOnly = true;
							FINA_AMOUNT.cls = 'x-readOnly';
							CREDIT_CARD_FLAG.readOnly = true;
							CREDIT_CARD_FLAG.cls = 'x-readOnly';
							DEBIT_FLAG.readOnly = true;
							DEBIT_FLAG.cls = 'x-readOnly';
							BAD_CREDIT_FLAG.readOnly = true;
							BAD_CREDIT_FLAG.cls = 'x-readOnly';
							PER_CARD_FLAG.readOnly = true;
							PER_CARD_FLAG.cls = 'x-readOnly';
							CREDIT_CARD_BANK.readOnly = true;
							CREDIT_CARD_BANK.cls = 'x-readOnly';
							PRE_CREDIT_AMOUNT.readOnly = true;
							PRE_CREDIT_AMOUNT.cls = 'x-readOnly';
							CREDIT_USE.readOnly = true;
							CREDIT_USE.cls = 'x-readOnly';
							TERM.readOnly = true;
							TERM.cls = 'x-readOnly';
							REPAY_SOURCE.readOnly = true;
							REPAY_SOURCE.cls = 'x-readOnly';
							SUP_INF.readOnly = true;
							SUP_INF.cls = 'x-readOnly';
							SUP_INF_RATE.readOnly = true;
							SUP_INF_RATE.cls = 'x-readOnly';
							SUP_INF_S.readOnly = true;
							SUP_INF_S.cls = 'x-readOnly';
							SUP_INF_S_RATE.readOnly = true;
							SUP_INF_S_RATE.cls = 'x-readOnly';
							BUYER_INF.readOnly = true;
							BUYER_INF.cls = 'x-readOnly';
							BUYER_INF_RATE.readOnly = true;
							BUYER_INF_RATE.cls = 'x-readOnly';
							BUYER_INF_S.readOnly = true;
							BUYER_INF_S.cls = 'x-readOnly';
							BUYER_INF_S_RATE.readOnly = true;
							BUYER_INF_S_RATE.cls = 'x-readOnly';
							RELATION_COM.readOnly = true;
							RELATION_COM.cls = 'x-readOnly';
							RELATION_COM_S.readOnly = true;
							RELATION_COM_S.cls = 'x-readOnly';
							GUA_MOR_FLAG.readOnly = true;
							GUA_MOR_FLAG.cls = 'x-readOnly';
							CUST_MGR.readOnly = true;
							CUST_MGR.cls = 'x-readOnly';
							Q_CUSTOMERTYPE.readOnly = true;
							Q_CUSTOMERTYPE.cls = 'x-readOnly';
							Q_INTERVIEWEENAME.readOnly = true;
							Q_INTERVIEWEENAME.cls = 'x-readOnly';
							Q_INTERVIEWEEPOST.readOnly = true;
							Q_INTERVIEWEEPOST.cls = 'x-readOnly';
							Q_OPERATEYEARS.readOnly = true;
							Q_OPERATEYEARS.cls = 'x-readOnly';
							Q_BUSINESS.readOnly = true;
							Q_BUSINESS.cls = 'x-readOnly';
							Q_MARKETIN.readOnly = true;
							Q_MARKETIN.cls = 'x-readOnly';
							Q_ASSTOTAL.readOnly = true;
							Q_ASSTOTAL.cls = 'x-readOnly';
							Q_MAGYEARS.readOnly = true;
							Q_MAGYEARS.cls = 'x-readOnly';
							Q_WORKYEARS.readOnly = true;
							Q_WORKYEARS.cls = 'x-readOnly';
							Q_FOUNDEDYEARS.readOnly = true;
							Q_FOUNDEDYEARS.cls = 'x-readOnly';
							Q_CREDITLIMIT.readOnly = true;
							Q_CREDITLIMIT.cls = 'x-readOnly';
							Q_ADDRYEARS.readOnly = true;
							Q_ADDRYEARS.cls = 'x-readOnly';
							Q_PYEARINCOME.readOnly = true;
							Q_PYEARINCOME.cls = 'x-readOnly';
							Q_LYEARINCOME.readOnly = true;
							Q_LYEARINCOME.cls = 'x-readOnly';
							Q_TOTALINCOME.readOnly = true;
							Q_TOTALINCOME.cls = 'x-readOnly';
							Q_PLANINCOME.readOnly = true;
							Q_PLANINCOME.cls = 'x-readOnly';
							G_HOUSE.readOnly = true;
							G_HOUSE.cls = 'x-readOnly';
							G_HOUSEPLEDGE.readOnly = true;
							G_HOUSEPLEDGE.cls = 'x-readOnly';
							G_LAND.readOnly = true;
							G_LAND.cls = 'x-readOnly';
							G_LANDPLEDGE.readOnly = true;
							G_LANDPLEDGE.cls = 'x-readOnly';
							G_EQUIPMENT.readOnly = true;
							G_EQUIPMENT.cls = 'x-readOnly';
							G_EQUIPMENTPLEDGE.readOnly = true;
							G_EQUIPMENTPLEDGE.cls = 'x-readOnly';
							G_FOREST.readOnly = true;
							G_FOREST.cls = 'x-readOnly';
							G_FORESTPLEDGE.readOnly = true;
							G_FORESTPLEDGE.cls = 'x-readOnly';
							G_MINING.readOnly = true;
							G_MINING.cls = 'x-readOnly';
							G_MININGPLEDGE.readOnly = true;
							G_MININGPLEDGE.cls = 'x-readOnly';
							G_FLOATING.readOnly = true;
							G_FLOATING.cls = 'x-readOnly';
							G_FLOATPLEDGE.readOnly = true;
							G_FLOATPLEDGE.cls = 'x-readOnly';
							G_DEPOSIT.readOnly = true;
							G_DEPOSIT.cls = 'x-readOnly';
							G_DEPOSITPLEDGE.readOnly = true;
							G_DEPOSITPLEDGE.cls = 'x-readOnly';
							G_VEHICLE.readOnly = true;
							G_VEHICLE.cls = 'x-readOnly';
							G_VEHICLEPLEDGE.readOnly = true;
							G_VEHICLEPLEDGE.cls = 'x-readOnly';
							G_RECEIVABLEMONEY.readOnly = true;
							G_RECEIVABLEMONEY.cls = 'x-readOnly';
							G_RECEIVABLEMPLEDGE.readOnly = true;
							G_RECEIVABLEMPLEDGE.cls = 'x-readOnly';
							G_STOCK.readOnly = true;
							G_STOCK.cls = 'x-readOnly';
							G_STOCKPLEDGE.readOnly = true;
							G_STOCKPLEDGE.cls = 'x-readOnly';
							A_FILETYPE.readOnly = true;
							A_FILETYPE.cls = 'x-readOnly';
							B_FILETYPE.readOnly = true;
							B_FILETYPE.cls = 'x-readOnly';
							C_FILETYPE.readOnly = true;
							C_FILETYPE.cls = 'x-readOnly';
							CONCLUSION.readOnly = true;
							CONCLUSION.cls = 'x-readOnly';
							ISNEW.readOnly = true;
							ISNEW.cls = 'x-readOnly';
							STATE.readOnly = true;
							STATE.cls = 'x-readOnly';
							MOVER_USER.readOnly = true;
							MOVER_USER.cls = 'x-readOnly';
							return [ CUS_ID, CUS_NAME, CUS_PHONE, ATTEN_NAME,
									ATTEN_BUSI, ATTEN_PHONE, LEGAL_NAME,
									REQ_CURRENCY, REG_CAP_AMT, CUS_RESOURCE,
									CUS_RESOURCE_BAK1, CUS_RESOURCE_BAK2,
									CUS_RESOURCE_BAK3, CUS_RESOURCE_BAK4,
									ACT_CTL_NAME, ACT_CTL_PHONE, ACT_CTL_WIFE,
									PARTNER_INFO1, PARTNER_RATE1,
									PARTNER_INFO2, PARTNER_RATE2,
									PARTNER_INFO3, PARTNER_RATE3, AMOUNT2,
									AMOUNT1, AMOUNT, PRE_AMOUNT, AVE_BALANCE,
									TOTAL_ASS, LICENSE_FLAG, TAX_REC_FLAG,
									DEBT_AMOUNT, CAP_AMOUNT, LOAN_AMOUNT,
									FINA_AMOUNT, CREDIT_CARD_FLAG, DEBIT_FLAG,
									BAD_CREDIT_FLAG, PER_CARD_FLAG,
									CREDIT_CARD_BANK, PRE_CREDIT_AMOUNT,
									CREDIT_USE, TERM, REPAY_SOURCE, SUP_INF,
									SUP_INF_RATE, SUP_INF_S, SUP_INF_S_RATE,
									BUYER_INF, BUYER_INF_RATE, BUYER_INF_S,
									BUYER_INF_S_RATE, RELATION_COM,
									RELATION_COM_S, GUA_MOR_FLAG, CUST_MGR,
									Q_CUSTOMERTYPE, Q_INTERVIEWEENAME,
									Q_INTERVIEWEEPOST, Q_OPERATEYEARS,
									Q_BUSINESS, Q_MARKETIN, Q_ASSTOTAL,
									Q_MAGYEARS, Q_WORKYEARS, Q_FOUNDEDYEARS,
									Q_CREDITLIMIT, Q_ADDRYEARS, Q_PYEARINCOME,
									Q_LYEARINCOME, Q_TOTALINCOME, Q_PLANINCOME,
									G_HOUSE, G_HOUSEPLEDGE, G_LAND,
									G_LANDPLEDGE, G_EQUIPMENT,
									G_EQUIPMENTPLEDGE, G_FOREST,
									G_FORESTPLEDGE, G_MINING, G_MININGPLEDGE,
									G_FLOATING, G_FLOATPLEDGE, G_DEPOSIT,
									G_DEPOSITPLEDGE, G_VEHICLE,
									G_VEHICLEPLEDGE, G_RECEIVABLEMONEY,
									G_RECEIVABLEMPLEDGE, G_STOCK,
									G_STOCKPLEDGE, A_FILETYPE, B_FILETYPE,
									C_FILETYPE, CONCLUSION, ISNEW, STATE,
									MOVER_USER ];
						}
					}, {
						columnCount : 1,
						fields : [ 'CUS_ADDR' ],
						fn : function(CUS_ADDR) {
							CUS_ADDR.readOnly = true;
							CUS_ADDR.cls = 'x-readOnly';
							return [ CUS_ADDR ];
						}
					}, {
						columnCount : 1,
						fields : [ 'REMARK' ],
						fn : function(REMARK) {
							REMARK.readOnly = true;
							REMARK.cls = 'x-readOnly';
							return [ REMARK ];
						}
					} ]
		}, {
			title : '客户分配',
			hideTitle : true,
			type : 'panel',
			items : [ cTrans ]
		}

];

// var createFormViewer = [{
// fields :
// ['CUS_ID','CUS_NAME','CUS_PHONE','ATTEN_NAME','ATTEN_BUSI','ATTEN_PHONE','LEGAL_NAME','REQ_CURRENCY','REG_CAP_AMT','CUS_RESOURCE','ACT_CTL_NAME','ACT_CTL_PHONE','ACT_CTL_WIFE','PARTNER_INFO1'
// ,'PARTNER_RATE1','PARTNER_INFO2','PARTNER_RATE2','PARTNER_INFO3','PARTNER_RATE3','AMOUNT2','AMOUNT1','AMOUNT','PRE_AMOUNT','AVE_BALANCE','TOTAL_ASS','LICENSE_FLAG','TAX_REC_FLAG','DEBT_AMOUNT'
// ,'CAP_AMOUNT','LOAN_AMOUNT','FINA_AMOUNT','CREDIT_CARD_FLAG','DEBIT_FLAG','BAD_CREDIT_FLAG','PER_CARD_FLAG','CREDIT_CARD_BANK','PRE_CREDIT_AMOUNT','CREDIT_USE','TERM','REPAY_SOURCE'
// ,'SUP_INF','SUP_INF_RATE','SUP_INF_S','SUP_INF_S_RATE','BUYER_INF','BUYER_INF_RATE','BUYER_INF_S','BUYER_INF_S_RATE','RELATION_COM','RELATION_COM_S','GUA_MOR_FLAG','CUST_MGR','Q_CUSTOMERTYPE'
// ,'Q_INTERVIEWEENAME','Q_INTERVIEWEEPOST','Q_OPERATEYEARS','Q_BUSINESS','Q_MARKETIN','Q_ASSTOTAL','Q_MAGYEARS','Q_WORKYEARS','Q_FOUNDEDYEARS','Q_CREDITLIMIT','Q_ADDRYEARS','Q_PYEARINCOME','Q_LYEARINCOME'
// ,'Q_TOTALINCOME','Q_PLANINCOME','G_HOUSE','G_HOUSEPLEDGE','G_LAND','G_LANDPLEDGE','G_EQUIPMENT','G_EQUIPMENTPLEDGE','G_FOREST','G_FORESTPLEDGE','G_MINING','G_MININGPLEDGE','G_FLOATING'
// ,'G_FLOATPLEDGE','G_DEPOSIT','G_DEPOSITPLEDGE','G_VEHICLE','G_VEHICLEPLEDGE','G_RECEIVABLEMONEY','G_RECEIVABLEMPLEDGE','G_STOCK','G_STOCKPLEDGE','A_FILETYPE','B_FILETYPE','C_FILETYPE','CONCLUSION','ISNEW'],
//			
// fn :
// function(CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REQ_CURRENCY,REG_CAP_AMT,CUS_RESOURCE,ACT_CTL_NAME,ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1
// ,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,PARTNER_RATE3,AMOUNT2,AMOUNT1,AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT
// ,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,CREDIT_CARD_FLAG,DEBIT_FLAG,BAD_CREDIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,PRE_CREDIT_AMOUNT,CREDIT_USE,TERM,REPAY_SOURCE
// ,SUP_INF,SUP_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_RATE,BUYER_INF_S,BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE
// ,Q_INTERVIEWEENAME,Q_INTERVIEWEEPOST,Q_OPERATEYEARS,Q_BUSINESS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_WORKYEARS,Q_FOUNDEDYEARS,Q_CREDITLIMIT,Q_ADDRYEARS,Q_PYEARINCOME,Q_LYEARINCOME
// ,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING
// ,G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CONCLUSION,ISNEW){
// CONCLUSION.readOnly=true;
// CONCLUSION.cls = "x-readOnly";
// CUS_ID.hidden = true;
//				    	
// return
// [CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REQ_CURRENCY,REG_CAP_AMT,CUS_RESOURCE,ACT_CTL_NAME,ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1
// ,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,PARTNER_RATE3,AMOUNT2,AMOUNT1,AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT
// ,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,CREDIT_CARD_FLAG,DEBIT_FLAG,BAD_CREDIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,PRE_CREDIT_AMOUNT,CREDIT_USE,TERM,REPAY_SOURCE
// ,SUP_INF,SUP_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_RATE,BUYER_INF_S,BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE
// ,Q_INTERVIEWEENAME,Q_INTERVIEWEEPOST,Q_OPERATEYEARS,Q_BUSINESS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_WORKYEARS,Q_FOUNDEDYEARS,Q_CREDITLIMIT,Q_ADDRYEARS,Q_PYEARINCOME,Q_LYEARINCOME
// ,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING
// ,G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CONCLUSION,ISNEW]
// }
// },{
// columnCount : 1 ,
// fields : ['CUS_ADDR'],
// fn : function(CUS_ADDR){
// return [CUS_ADDR];
// }
// }];

// var editFormViewer = [{
// fields :
// ['CUS_ID','CUS_NAME','CUS_PHONE','ATTEN_NAME','ATTEN_BUSI','ATTEN_PHONE','LEGAL_NAME','REQ_CURRENCY','REG_CAP_AMT','CUS_RESOURCE','ACT_CTL_NAME','ACT_CTL_PHONE','ACT_CTL_WIFE','PARTNER_INFO1'
// ,'PARTNER_RATE1','PARTNER_INFO2','PARTNER_RATE2','PARTNER_INFO3','PARTNER_RATE3','AMOUNT2','AMOUNT1','AMOUNT','PRE_AMOUNT','AVE_BALANCE','TOTAL_ASS','LICENSE_FLAG','TAX_REC_FLAG','DEBT_AMOUNT'
// ,'CAP_AMOUNT','LOAN_AMOUNT','FINA_AMOUNT','CREDIT_CARD_FLAG','DEBIT_FLAG','BAD_CREDIT_FLAG','PER_CARD_FLAG','CREDIT_CARD_BANK','PRE_CREDIT_AMOUNT','CREDIT_USE','TERM','REPAY_SOURCE'
// ,'SUP_INF','SUP_INF_RATE','SUP_INF_S','SUP_INF_S_RATE','BUYER_INF','BUYER_INF_RATE','BUYER_INF_S','BUYER_INF_S_RATE','RELATION_COM','RELATION_COM_S','GUA_MOR_FLAG','CUST_MGR','Q_CUSTOMERTYPE'
// ,'Q_INTERVIEWEENAME','Q_INTERVIEWEEPOST','Q_OPERATEYEARS','Q_BUSINESS','Q_MARKETIN','Q_ASSTOTAL','Q_MAGYEARS','Q_WORKYEARS','Q_FOUNDEDYEARS','Q_CREDITLIMIT','Q_ADDRYEARS','Q_PYEARINCOME','Q_LYEARINCOME'
// ,'Q_TOTALINCOME','Q_PLANINCOME','G_HOUSE','G_HOUSEPLEDGE','G_LAND','G_LANDPLEDGE','G_EQUIPMENT','G_EQUIPMENTPLEDGE','G_FOREST','G_FORESTPLEDGE','G_MINING','G_MININGPLEDGE','G_FLOATING'
// ,'G_FLOATPLEDGE','G_DEPOSIT','G_DEPOSITPLEDGE','G_VEHICLE','G_VEHICLEPLEDGE','G_RECEIVABLEMONEY','G_RECEIVABLEMPLEDGE','G_STOCK','G_STOCKPLEDGE','A_FILETYPE','B_FILETYPE','C_FILETYPE','CONCLUSION','ISNEW'],
//			
// fn :
// function(CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REQ_CURRENCY,REG_CAP_AMT,CUS_RESOURCE,ACT_CTL_NAME,ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1
// ,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,PARTNER_RATE3,AMOUNT2,AMOUNT1,AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT
// ,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,CREDIT_CARD_FLAG,DEBIT_FLAG,BAD_CREDIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,PRE_CREDIT_AMOUNT,CREDIT_USE,TERM,REPAY_SOURCE
// ,SUP_INF,SUP_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_RATE,BUYER_INF_S,BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE
// ,Q_INTERVIEWEENAME,Q_INTERVIEWEEPOST,Q_OPERATEYEARS,Q_BUSINESS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_WORKYEARS,Q_FOUNDEDYEARS,Q_CREDITLIMIT,Q_ADDRYEARS,Q_PYEARINCOME,Q_LYEARINCOME
// ,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING
// ,G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CONCLUSION,ISNEW){
//				
// return
// [CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REQ_CURRENCY,REG_CAP_AMT,CUS_RESOURCE,ACT_CTL_NAME,ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1
// ,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,PARTNER_RATE3,AMOUNT2,AMOUNT1,AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT
// ,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,CREDIT_CARD_FLAG,DEBIT_FLAG,BAD_CREDIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,PRE_CREDIT_AMOUNT,CREDIT_USE,TERM,REPAY_SOURCE
// ,SUP_INF,SUP_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_RATE,BUYER_INF_S,BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE
// ,Q_INTERVIEWEENAME,Q_INTERVIEWEEPOST,Q_OPERATEYEARS,Q_BUSINESS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_WORKYEARS,Q_FOUNDEDYEARS,Q_CREDITLIMIT,Q_ADDRYEARS,Q_PYEARINCOME,Q_LYEARINCOME
// ,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING
// ,G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CONCLUSION,ISNEW]
// }
// },{
// columnCount : 1 ,
// fields : ['CUS_ADDR'],
// fn : function(CUS_ADDR){
// return [CUS_ADDR];
// }
// }];

// var detailFormViewer = [{
// fields :
// ['CUS_ID','CUS_NAME','CUS_PHONE','ATTEN_NAME','ATTEN_BUSI','ATTEN_PHONE','LEGAL_NAME','REQ_CURRENCY','REG_CAP_AMT','CUS_RESOURCE','CUS_RESOURCE_BAK1','CUS_RESOURCE_BAK2','CUS_RESOURCE_BAK3','ACT_CTL_NAME','ACT_CTL_PHONE','ACT_CTL_WIFE','PARTNER_INFO1'
// ,'PARTNER_RATE1','PARTNER_INFO2','PARTNER_RATE2','PARTNER_INFO3','PARTNER_RATE3','AMOUNT2','AMOUNT1','AMOUNT','PRE_AMOUNT','AVE_BALANCE','TOTAL_ASS','LICENSE_FLAG','TAX_REC_FLAG','DEBT_AMOUNT'
// ,'CAP_AMOUNT','LOAN_AMOUNT','FINA_AMOUNT','CREDIT_CARD_FLAG','DEBIT_FLAG','BAD_CREDIT_FLAG','PER_CARD_FLAG','CREDIT_CARD_BANK','PRE_CREDIT_AMOUNT','CREDIT_USE','TERM','REPAY_SOURCE'
// ,'SUP_INF','SUP_INF_RATE','SUP_INF_S','SUP_INF_S_RATE','BUYER_INF','BUYER_INF_RATE','BUYER_INF_S','BUYER_INF_S_RATE','RELATION_COM','RELATION_COM_S','GUA_MOR_FLAG','CUST_MGR','Q_CUSTOMERTYPE'
// ,'Q_INTERVIEWEENAME','Q_INTERVIEWEEPOST','Q_OPERATEYEARS','Q_BUSINESS','Q_MARKETIN','Q_ASSTOTAL','Q_MAGYEARS','Q_WORKYEARS','Q_FOUNDEDYEARS','Q_CREDITLIMIT','Q_ADDRYEARS','Q_PYEARINCOME','Q_LYEARINCOME'
// ,'Q_TOTALINCOME','Q_PLANINCOME','G_HOUSE','G_HOUSEPLEDGE','G_LAND','G_LANDPLEDGE','G_EQUIPMENT','G_EQUIPMENTPLEDGE','G_FOREST','G_FORESTPLEDGE','G_MINING','G_MININGPLEDGE','G_FLOATING'
// ,'G_FLOATPLEDGE','G_DEPOSIT','G_DEPOSITPLEDGE','G_VEHICLE','G_VEHICLEPLEDGE','G_RECEIVABLEMONEY','G_RECEIVABLEMPLEDGE','G_STOCK','G_STOCKPLEDGE','A_FILETYPE','B_FILETYPE','C_FILETYPE','CONCLUSION','ISNEW','STATE','MOVER_USER'],
//			
// fn :
// function(CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REQ_CURRENCY,REG_CAP_AMT,CUS_RESOURCE,CUS_RESOURCE_BAK1,CUS_RESOURCE_BAK2,CUS_RESOURCE_BAK3,ACT_CTL_NAME,ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1
// ,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,PARTNER_RATE3,AMOUNT2,AMOUNT1,AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT
// ,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,CREDIT_CARD_FLAG,DEBIT_FLAG,BAD_CREDIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,PRE_CREDIT_AMOUNT,CREDIT_USE,TERM,REPAY_SOURCE
// ,SUP_INF,SUP_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_RATE,BUYER_INF_S,BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE
// ,Q_INTERVIEWEENAME,Q_INTERVIEWEEPOST,Q_OPERATEYEARS,Q_BUSINESS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_WORKYEARS,Q_FOUNDEDYEARS,Q_CREDITLIMIT,Q_ADDRYEARS,Q_PYEARINCOME,Q_LYEARINCOME
// ,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING
// ,G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CONCLUSION,ISNEW,STATE,MOVER_USER){
//				
// CUS_ID.readOnly=true;CUS_ID.cls='x-readOnly';
// CUS_NAME.readOnly=true;CUS_NAME.cls='x-readOnly';
// CUS_PHONE.readOnly=true;CUS_PHONE.cls='x-readOnly';
// ATTEN_NAME.readOnly=true;ATTEN_NAME.cls='x-readOnly';
// ATTEN_BUSI.readOnly=true;ATTEN_BUSI.cls='x-readOnly';
// ATTEN_PHONE.readOnly=true;ATTEN_PHONE.cls='x-readOnly';
// LEGAL_NAME.readOnly=true;LEGAL_NAME.cls='x-readOnly';
// REQ_CURRENCY.readOnly=true;REQ_CURRENCY.cls='x-readOnly';
// REG_CAP_AMT.readOnly=true;REG_CAP_AMT.cls='x-readOnly';
// CUS_RESOURCE.readOnly=true;CUS_RESOURCE.cls='x-readOnly';
// CUS_RESOURCE_BAK1.readOnly=true;
// CUS_RESOURCE_BAK1.cls = "x-readOnly";
// CUS_RESOURCE_BAK2.readOnly=true;
// CUS_RESOURCE_BAK2.cls = "x-readOnly";
// CUS_RESOURCE_BAK3.readOnly=true;
// CUS_RESOURCE_BAK3.cls = "x-readOnly";
// ACT_CTL_NAME.readOnly=true;ACT_CTL_NAME.cls='x-readOnly';
// ACT_CTL_PHONE.readOnly=true;ACT_CTL_PHONE.cls='x-readOnly';
// ACT_CTL_WIFE.readOnly=true;ACT_CTL_WIFE.cls='x-readOnly';
// PARTNER_INFO1.readOnly=true;PARTNER_INFO1.cls='x-readOnly';
// PARTNER_RATE1.readOnly=true;PARTNER_RATE1.cls='x-readOnly';
// PARTNER_INFO2.readOnly=true;PARTNER_INFO2.cls='x-readOnly';
// PARTNER_RATE2.readOnly=true;PARTNER_RATE2.cls='x-readOnly';
// PARTNER_INFO3.readOnly=true;PARTNER_INFO3.cls='x-readOnly';
// PARTNER_RATE3.readOnly=true;PARTNER_RATE3.cls='x-readOnly';
// AMOUNT2.readOnly=true;AMOUNT2.cls='x-readOnly';
// AMOUNT1.readOnly=true;AMOUNT1.cls='x-readOnly';
// AMOUNT.readOnly=true;AMOUNT.cls='x-readOnly';
// PRE_AMOUNT.readOnly=true;PRE_AMOUNT.cls='x-readOnly';
// AVE_BALANCE.readOnly=true;AVE_BALANCE.cls='x-readOnly';
// TOTAL_ASS.readOnly=true;TOTAL_ASS.cls='x-readOnly';
// LICENSE_FLAG.readOnly=true;LICENSE_FLAG.cls='x-readOnly';
// TAX_REC_FLAG.readOnly=true;TAX_REC_FLAG.cls='x-readOnly';
// DEBT_AMOUNT.readOnly=true;DEBT_AMOUNT.cls='x-readOnly';
// CAP_AMOUNT.readOnly=true;CAP_AMOUNT.cls='x-readOnly';
// LOAN_AMOUNT.readOnly=true;LOAN_AMOUNT.cls='x-readOnly';
// FINA_AMOUNT.readOnly=true;FINA_AMOUNT.cls='x-readOnly';
// CREDIT_CARD_FLAG.readOnly=true;CREDIT_CARD_FLAG.cls='x-readOnly';
// DEBIT_FLAG.readOnly=true;DEBIT_FLAG.cls='x-readOnly';
// BAD_CREDIT_FLAG.readOnly=true;BAD_CREDIT_FLAG.cls='x-readOnly';
// PER_CARD_FLAG.readOnly=true;PER_CARD_FLAG.cls='x-readOnly';
// CREDIT_CARD_BANK.readOnly=true;CREDIT_CARD_BANK.cls='x-readOnly';
// PRE_CREDIT_AMOUNT.readOnly=true;PRE_CREDIT_AMOUNT.cls='x-readOnly';
// CREDIT_USE.readOnly=true;CREDIT_USE.cls='x-readOnly';
// TERM.readOnly=true;TERM.cls='x-readOnly';
// REPAY_SOURCE.readOnly=true;REPAY_SOURCE.cls='x-readOnly';
// SUP_INF.readOnly=true;SUP_INF.cls='x-readOnly';
// SUP_INF_RATE.readOnly=true;SUP_INF_RATE.cls='x-readOnly';
// SUP_INF_S.readOnly=true;SUP_INF_S.cls='x-readOnly';
// SUP_INF_S_RATE.readOnly=true;SUP_INF_S_RATE.cls='x-readOnly';
// BUYER_INF.readOnly=true;BUYER_INF.cls='x-readOnly';
// BUYER_INF_RATE.readOnly=true;BUYER_INF_RATE.cls='x-readOnly';
// BUYER_INF_S.readOnly=true;BUYER_INF_S.cls='x-readOnly';
// BUYER_INF_S_RATE.readOnly=true;BUYER_INF_S_RATE.cls='x-readOnly';
// RELATION_COM.readOnly=true;RELATION_COM.cls='x-readOnly';
// RELATION_COM_S.readOnly=true;RELATION_COM_S.cls='x-readOnly';
// GUA_MOR_FLAG.readOnly=true;GUA_MOR_FLAG.cls='x-readOnly';
// CUST_MGR.readOnly=true;CUST_MGR.cls='x-readOnly';
// Q_CUSTOMERTYPE.readOnly=true;Q_CUSTOMERTYPE.cls='x-readOnly';
// Q_INTERVIEWEENAME.readOnly=true;Q_INTERVIEWEENAME.cls='x-readOnly';
// Q_INTERVIEWEEPOST.readOnly=true;Q_INTERVIEWEEPOST.cls='x-readOnly';
// Q_OPERATEYEARS.readOnly=true;Q_OPERATEYEARS.cls='x-readOnly';
// Q_BUSINESS.readOnly=true;Q_BUSINESS.cls='x-readOnly';
// Q_MARKETIN.readOnly=true;Q_MARKETIN.cls='x-readOnly';
// Q_ASSTOTAL.readOnly=true;Q_ASSTOTAL.cls='x-readOnly';
// Q_MAGYEARS.readOnly=true;Q_MAGYEARS.cls='x-readOnly';
// Q_WORKYEARS.readOnly=true;Q_WORKYEARS.cls='x-readOnly';
// Q_FOUNDEDYEARS.readOnly=true;Q_FOUNDEDYEARS.cls='x-readOnly';
// Q_CREDITLIMIT.readOnly=true;Q_CREDITLIMIT.cls='x-readOnly';
// Q_ADDRYEARS.readOnly=true;Q_ADDRYEARS.cls='x-readOnly';
// Q_PYEARINCOME.readOnly=true;Q_PYEARINCOME.cls='x-readOnly';
// Q_LYEARINCOME.readOnly=true;Q_LYEARINCOME.cls='x-readOnly';
// Q_TOTALINCOME.readOnly=true;Q_TOTALINCOME.cls='x-readOnly';
// Q_PLANINCOME.readOnly=true;Q_PLANINCOME.cls='x-readOnly';
// G_HOUSE.readOnly=true;G_HOUSE.cls='x-readOnly';
// G_HOUSEPLEDGE.readOnly=true;G_HOUSEPLEDGE.cls='x-readOnly';
// G_LAND.readOnly=true;G_LAND.cls='x-readOnly';
// G_LANDPLEDGE.readOnly=true;G_LANDPLEDGE.cls='x-readOnly';
// G_EQUIPMENT.readOnly=true;G_EQUIPMENT.cls='x-readOnly';
// G_EQUIPMENTPLEDGE.readOnly=true;G_EQUIPMENTPLEDGE.cls='x-readOnly';
// G_FOREST.readOnly=true;G_FOREST.cls='x-readOnly';
// G_FORESTPLEDGE.readOnly=true;G_FORESTPLEDGE.cls='x-readOnly';
// G_MINING.readOnly=true;G_MINING.cls='x-readOnly';
// G_MININGPLEDGE.readOnly=true;G_MININGPLEDGE.cls='x-readOnly';
// G_FLOATING.readOnly=true;G_FLOATING.cls='x-readOnly';
// G_FLOATPLEDGE.readOnly=true;G_FLOATPLEDGE.cls='x-readOnly';
// G_DEPOSIT.readOnly=true;G_DEPOSIT.cls='x-readOnly';
// G_DEPOSITPLEDGE.readOnly=true;G_DEPOSITPLEDGE.cls='x-readOnly';
// G_VEHICLE.readOnly=true;G_VEHICLE.cls='x-readOnly';
// G_VEHICLEPLEDGE.readOnly=true;G_VEHICLEPLEDGE.cls='x-readOnly';
// G_RECEIVABLEMONEY.readOnly=true;G_RECEIVABLEMONEY.cls='x-readOnly';
// G_RECEIVABLEMPLEDGE.readOnly=true;G_RECEIVABLEMPLEDGE.cls='x-readOnly';
// G_STOCK.readOnly=true;G_STOCK.cls='x-readOnly';
// G_STOCKPLEDGE.readOnly=true;G_STOCKPLEDGE.cls='x-readOnly';
// A_FILETYPE.readOnly=true;A_FILETYPE.cls='x-readOnly';
// B_FILETYPE.readOnly=true;B_FILETYPE.cls='x-readOnly';
// C_FILETYPE.readOnly=true;C_FILETYPE.cls='x-readOnly';
// CONCLUSION.readOnly=true;CONCLUSION.cls='x-readOnly';
// ISNEW.readOnly=true;ISNEW.cls='x-readOnly';
// STATE.readOnly=true;STATE.cls='x-readOnly';
// MOVER_USER.readOnly=true;MOVER_USER.cls='x-readOnly';
//
// return
// [CUS_ID,CUS_NAME,CUS_PHONE,ATTEN_NAME,ATTEN_BUSI,ATTEN_PHONE,LEGAL_NAME,REQ_CURRENCY,REG_CAP_AMT,CUS_RESOURCE,CUS_RESOURCE_BAK1,CUS_RESOURCE_BAK2,CUS_RESOURCE_BAK3,ACT_CTL_NAME,ACT_CTL_PHONE,ACT_CTL_WIFE,PARTNER_INFO1
// ,PARTNER_RATE1,PARTNER_INFO2,PARTNER_RATE2,PARTNER_INFO3,PARTNER_RATE3,AMOUNT2,AMOUNT1,AMOUNT,PRE_AMOUNT,AVE_BALANCE,TOTAL_ASS,LICENSE_FLAG,TAX_REC_FLAG,DEBT_AMOUNT
// ,CAP_AMOUNT,LOAN_AMOUNT,FINA_AMOUNT,CREDIT_CARD_FLAG,DEBIT_FLAG,BAD_CREDIT_FLAG,PER_CARD_FLAG,CREDIT_CARD_BANK,PRE_CREDIT_AMOUNT,CREDIT_USE,TERM,REPAY_SOURCE
// ,SUP_INF,SUP_INF_RATE,SUP_INF_S,SUP_INF_S_RATE,BUYER_INF,BUYER_INF_RATE,BUYER_INF_S,BUYER_INF_S_RATE,RELATION_COM,RELATION_COM_S,GUA_MOR_FLAG,CUST_MGR,Q_CUSTOMERTYPE
// ,Q_INTERVIEWEENAME,Q_INTERVIEWEEPOST,Q_OPERATEYEARS,Q_BUSINESS,Q_MARKETIN,Q_ASSTOTAL,Q_MAGYEARS,Q_WORKYEARS,Q_FOUNDEDYEARS,Q_CREDITLIMIT,Q_ADDRYEARS,Q_PYEARINCOME,Q_LYEARINCOME
// ,Q_TOTALINCOME,Q_PLANINCOME,G_HOUSE,G_HOUSEPLEDGE,G_LAND,G_LANDPLEDGE,G_EQUIPMENT,G_EQUIPMENTPLEDGE,G_FOREST,G_FORESTPLEDGE,G_MINING,G_MININGPLEDGE,G_FLOATING
// ,G_FLOATPLEDGE,G_DEPOSIT,G_DEPOSITPLEDGE,G_VEHICLE,G_VEHICLEPLEDGE,G_RECEIVABLEMONEY,G_RECEIVABLEMPLEDGE,G_STOCK,G_STOCKPLEDGE,A_FILETYPE,B_FILETYPE,C_FILETYPE,CONCLUSION,ISNEW,STATE,MOVER_USER];
// }
// },{
// columnCount : 1 ,
// fields : ['CUS_ADDR'],
// fn : function(CUS_ADDR){
// CUS_ADDR.readOnly = true;
// CUS_ADDR.cls = 'x-readOnly';
// return [CUS_ADDR];
// }
// },{
// columnCount : 1 ,
// fields : ['REMARK'],
// fn : function(REMARK){
// REMARK.readOnly = true;
// REMARK.cls = 'x-readOnly';
// return [REMARK];
// }
// }];

var tbar = [
		{
			text : '删除',
			hidden : JsContext.checkGrant('delete_posCusInfo'),
			handler : function() {
				if (getSelectedData() == false) {
					Ext.Msg.alert('提示', '请选择一条数据！');
					return false;
				} else {
					var selectRecords = getAllSelects();
					var state = '';
					for ( var i = 0; i < selectRecords.length; i++) {
						state += '' + selectRecords[i].data.STATE + ',';
					}
					var flag = state.indexOf('1,');
					if (state != '' && flag > (-1)) {
						Ext.Msg.alert('提示', '存在无效的潜在客户,请选择正式的有效的客户');
						return false;
					}

					Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
						if (buttonId.toLowerCase() == "no") {
							return false;
						}
						var selectRecords = getAllSelects();
						var cusId = '';
						for ( var i = 0; i < selectRecords.length; i++) {
							if (i == 0) {
								cusId = "'" + selectRecords[i].data.CUS_ID
										+ "'";
							} else {
								cusId += "," + "'"
										+ selectRecords[i].data.CUS_ID + "'";
							}
						}
						Ext.Ajax.request({
							url : basepath + '/acrmFCiPotCusCom!batchDel.json',
							params : {
								cusId : cusId
							},
							success : function() {
								Ext.Msg.alert('提示', '删除成功');
								reloadCurrentData();
							},
							failure : function() {
								Ext.Msg.alert('提示', '删除失败');
								reloadCurrentData();
							}
						});
					});
				}
			}
		},
		{
			text : '恢复',
			hidden : JsContext.checkGrant('reback_posCusInfo'),
			handler : function() {
				if (getSelectedData() == false) {
					Ext.Msg.alert('提示', '请选择一条数据！');
					return false;
				} else {
					var selectRecords = getAllSelects();
					var state = '';
					for ( var i = 0; i < selectRecords.length; i++) {
						state += '' + selectRecords[i].data.STATE + ',';
					}
					var flag = state.indexOf('0,');
					if (state != '' && flag > (-1)) {
						Ext.Msg.alert('提示', '只能恢复无效的潜在客户');
						return false;
					}

					Ext.MessageBox.confirm('提示', '确定恢复吗?', function(buttonId) {
						if (buttonId.toLowerCase() == "no") {
							return false;
						}
						var selectRecords = getAllSelects();
						var cusId = '';
						for ( var i = 0; i < selectRecords.length; i++) {
							if (i == 0) {
								cusId = "'" + selectRecords[i].data.CUS_ID
										+ "'";
							} else {
								cusId += "," + "'"
										+ selectRecords[i].data.CUS_ID + "'";
							}
						}
						Ext.Ajax.request({
							url : basepath
									+ '/acrmFCiPotCusCom!recoverBack.json',
							params : {
								cusId : cusId
							},
							success : function() {
								Ext.Msg.alert('提示', '恢复成功');
								reloadCurrentData();
							},
							failure : function() {
								Ext.Msg.alert('提示', '恢复失败');
								reloadCurrentData();
							}
						});
					});
				}
			}
		},
		{
			text : '回收',
			hidden : JsContext.checkGrant('fb_posCusInfo'),
			handler : function() {
				if (getSelectedData() == false) {
					Ext.Msg.alert('提示', '请选择数据！');
					return false;
				}
				Ext.MessageBox.confirm('提示', '确定收回吗?', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return false;
					}
					var selectRecords = getAllSelects();
					var cusId = '';
					for ( var i = 0; i < selectRecords.length; i++) {
						if (i == 0) {
							cusId = "'" + selectRecords[i].data.CUS_ID + "'";
						} else {
							cusId += "," + "'" + selectRecords[i].data.CUS_ID
									+ "'";
						}
					}
					Ext.Ajax.request({
						url : basepath + '/acrmFCiPotCusCom!moverBack.json',
						params : {
							cusId : cusId
						},
						success : function() {
							Ext.Msg.alert('提示', '成功收回潜在客户');
							//reloadCurrentData();
						},
						failure : function() {
							Ext.Msg.alert('提示', '收回潜在客户失败');
							//reloadCurrentData();
						}
					});
				});
			}
		},
		{
			text : '分配',
			hidden : JsContext.checkGrant('hs_posCusInfo'),
			handler : function() {
				if (getSelectedData() == false) {
					Ext.Msg.alert('提示', '请选择数据！');
					return false;
				}
				var selectRecords = getAllSelects();
				var cusId = '';
				for ( var i = 0; i < selectRecords.length; i++) {
					if (i == 0) {
						cusId = "'" + selectRecords[i].data.CUS_ID + "'";
					} else {
						cusId += "," + "'" + selectRecords[i].data.CUS_ID + "'";
					}
				}

				Ext.Ajax
						.request({
							url : basepath
									+ '/acrmFCiPotCusCom!doCheckRole.json',
							method : 'GET',
							params : {
								cusId : cusId
							},
							success : function(response) {
								var ret = Ext.decode(response.responseText);
								var nametype = ret.type;
								if (nametype == '1') {
									Ext.MessageBox
											.alert('提示',
													'所选客户存在已有分配人且归属人为RM的法金潜在客户,只能先进行回收,回收后再分配');
									return false;
								} else if (nametype == '3') {
									Ext.MessageBox
											.alert('提示',
													'只能分配尚且没有分配人且归属人不为RM的潜在客户,所选客户存在不满足条件的');
									return false;
								} else if (nametype == '4') {
									Ext.MessageBox
											.alert('提示',
													'所选潜在客户存在已发生具体业务或者未收回,法金潜在客户不能进行分配,只能先回收再分配');
									return false;
								} else if (nametype == '2') {
									json = {
										'transedData' : []
									};
									for ( var i = 0; i < getAllSelects().length; i++) {
										var records = getAllSelects()[i].data;
										var temp = {};
										temp.CUS_ID = records.CUS_ID;
										temp.CUS_NAME = records.CUS_NAME;
										json.transedData.push(temp);
									}
									transedStore.loadData(json);
									transForm1.form.reset();
									showCustomerViewByIndex(3);
								}
							}
						});

			}
		},
		// 导出按钮
		new Com.yucheng.crm.common.NewExpButton({
			formPanel : 'searchCondition',
			hidden : JsContext.checkGrant('fjqz_export'),
			url : basepath + '/acrmFCiPotCusCom.json'
		}),

		// 新增法金潜在客户批量导入 2015-2-4
		{
			text : '模板下载',
			hidden : JsContext.checkGrant('fjqz_down'),
			handler : function() {
				var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
						+ ' scrollbars=no, resizable=no,location=no, status=no';
				var fileName = 'fjlantentCustonerImportGroup.xlsx';
				var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
				window.open(uploadUrl, '', winPara);
			}
		}, {
			text : '批量导入',
			hidden : JsContext.checkGrant('fjqz_import'),
			handler : function() {
				importForm.tradecode = 'importLantentCustInfoGroup';
				importWindow.show();
			}
		} ];

/** ***********导入窗口定义模块*************** */
var _tempImpFileName = "";
var pkHead = "";
/**
 * 导入表单对象，此对象为全局对象，页面直接调用。
 */
var importForm = new Ext.FormPanel(
		{
			height : 200,
			width : '100%',
			title : '文件导入',
			fileUpload : true,
			dataName : 'file',
			frame : true,
			tradecode : "",

			/** 是否显示导入状态 */
			importStateInfo : '',
			importStateMsg : function(state) {
				var titleArray = [ 'excel数据转化为SQL脚本', '执行SQL脚本...',
						'正在将临时表数据导入到业务表中...', '导入成功！' ];
				this.importStateInfo = "当前状态为：[<font color='red'>"
						+ titleArray[state] + "</font>];<br>";
			},
			/** 是否显示 当前excel数据转化为SQL脚本成功记录数 */
			curRecordNumInfo : '',
			curRecordNumMsg : function(o) {
				this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"
						+ o + "</font>];<br>";
			},
			/** 是否显示 当前sheet页签记录数 */
			curSheetRecordNumInfo : '',
			curSheetRecordNumMsg : function(o) {
				this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"
						+ o + "</font>];<br>";
			},
			/** 是否显示 当前sheet页签号 */
			sheetNumInfo : '',
			sheetNumMsg : function(o) {
				this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 是否显示 sheet页签总数 */
			totalSheetNumInfo : '',
			totalSheetNumMsg : function(o) {
				this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 是否显示 已导入完成sheet数 */
			finishSheetNumInfo : '',
			finishSheetNumMsg : function(o) {
				this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 是否显示 已导入完成记录数 */
			finishRecordNumInfo : '',
			finishRecordNumMsg : function(o) {
				this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 当前excel数据转化为SQL脚本成功记录数 */
			curRecordNum : 0,
			/** 导入总数 */
			totalNum : 1,
			/** 进度条信息 */
			progressBarText : '',
			/** 进度条Msg */
			progressBartitle : '',
			proxyStore : undefined,
			items : [],
			/** 进度条 */
			progressBar : null,
			/** *import成功句柄 */
			importSuccessHandler : function(json) {
				if (json != null) {
					if (typeof (json.curRecordNum) != 'undefined'
							&& this.curRecordNumMsg) {
						this.curRecordNumMsg(json.curRecordNum);
						this.curRecordNum = json.curRecordNum;
					}
					if (typeof (json.importState) != 'undefined'
							&& this.importStateMsg) {
						this.importStateMsg(json.importState);
					}
					if (typeof (json.curSheetRecordNum) != 'undefined'
							&& this.curSheetRecordNumMsg) {
						this.curSheetRecordNumMsg(json.curSheetRecordNum);
					}
					if (typeof (json.sheetNum) != 'undefined'
							&& this.sheetNumMsg) {
						this.sheetNumMsg(json.sheetNum);
					}
					if (typeof (json.totalSheetNum) != 'undefined'
							&& this.totalSheetNumMsg) {
						this.totalSheetNumMsg(json.totalSheetNum);
					}
					if (typeof (json.finishSheetNum) != 'undefined'
							&& this.finishSheetNumMsg) {
						this.finishSheetNumMsg(json.finishSheetNum);
					}
					if (typeof (json.finishRecordNum) != 'undefined'
							&& this.finishRecordNumMsg) {
						this.finishRecordNumMsg(json.finishRecordNum);
					}
				} else {
					this.curRecordNumInfo = '';
					this.importStateInfo = '';
					this.curSheetRecordNumInfo = '';
					this.sheetNumInfo = '';
					this.totalSheetNumInfo = '';
					this.finishSheetNumInfo = '';
					this.finishRecordNumInfo = '';
				}

				this.progressBartitle = '';
				/** 进度条Msg信息配置：各信息项目显示内容由各自方法配置 */
				this.progressBartitle += this.curRecordNumMsg ? this.curRecordNumInfo
						: '';
				this.progressBartitle += this.importStateMsg ? this.importStateInfo
						: '';
				this.progressBartitle += this.curSheetRecordNumMsg ? this.curSheetRecordNumInfo
						: '';
				this.progressBartitle += this.sheetNumMsg ? this.sheetNumInfo
						: '';
				this.progressBartitle += this.totalSheetNumMsg ? this.totalSheetNumInfo
						: '';
				this.progressBartitle += this.finishSheetNumMsg ? this.finishSheetNumInfo
						: '';
				this.progressBartitle += this.finishRecordNumMsg ? this.finishRecordNumInfo
						: '';

				showProgressBar(this.totalNum, this.curRecordNum,
						this.progressBarText, this.progressBartitle,
						"上传成功，正在导入数据，请稍候");
			},
			buttons : [ {
				text : '导入文件',
				handler : function() {
					var tradecode = this.ownerCt.ownerCt.tradecode;
					var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
					var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
					if (tradecode == undefined || tradecode == '') {
						Ext.MessageBox
								.alert('Debugging！',
										'You forgot to define the tradecode for the import form!');
						return false;
					}
					var impRefreshHandler = 0;
					if (this.ownerCt.ownerCt.getForm().isValid()) {
						this.ownerCt.ownerCt.ownerCt.hide();
						var fileNamesFull = this.ownerCt.ownerCt.items.items[0]
								.getValue();
						var extPoit = fileNamesFull.substring(fileNamesFull
								.indexOf('.'));
						if (extPoit == '.xls' || extPoit == '.XLS'
								|| extPoit == '.xlsx' || extPoit == '.XLSX') {
						} else {
							Ext.MessageBox.alert("文件错误", "导入文件不是XLS或XLSX文件。");
							return false;
						}
						showProgressBar(1, 0, '', '', '正在上传文件...');

						this.ownerCt.ownerCt
								.getForm()
								.submit(
										{
											url : basepath
													+ '/FileUpload?isImport=true',
											success : function(form, o) {
												_tempImpFileName = Ext.util.JSON
														.decode(o.response.responseText).realFileName;
												var condi = {};
												condi['filename'] = _tempImpFileName;
												condi['tradecode'] = tradecode;
												Ext.Ajax
														.request({
															url : basepath
																	+ "/ImportAction.json",
															method : 'GET',
															params : {
																"condition" : Ext
																		.encode(condi)
															},
															success : function() {
																importForm
																		.importSuccessHandler(null);
																var importFresh = function() {
																	Ext.Ajax
																			.request({
																				url : basepath
																						+ "/ImportAction!refresh.json",
																				method : 'GET',
																				success : function(
																						a) {
																					if (a.status == '200'
																							|| a.status == '201') {
																						var res = Ext.util.JSON
																								.decode(a.responseText);
																						if (res.json.result != undefined
																								&& res.json.result == '200') {
																							window
																									.clearInterval(impRefreshHandler);
																							if (res.json.BACK_IMPORT_ERROR
																									&& res.json.BACK_IMPORT_ERROR == 'FILE_ERROR') {
																								Ext.Msg
																										.alert(
																												"提示",
																												"导出文件格式有误，请下载导入模版。");
																								return;
																							}
																							if (proxyStorePS != undefined) {
																								var condiFormP = {};
																								condiFormP['pkHaed'] = res.json.PK_HEAD;
																								pkHead = res.json.PK_HEAD;
																								proxyStorePS
																										.load({
																											params : {
																												pkHead : pkHead
																											}
																										});
																							} else {
																								importForm
																										.importSuccessHandler(res.json);
																								showSuccessWinImp(res.json.curRecordNum);// 导入数据条数
																							}
																						} else if (res.json.result != undefined
																								&& res.json.result == '900') {

																							window
																									.clearInterval(impRefreshHandler);
																							importState = true;
																							progressWin
																									.hide();// 隐藏导入进度窗口
																							Ext.Msg
																									.alert(
																											"导入失败",
																											"失败原因：\n"
																													+ res.json.BACK_IMPORT_ERROR);
																						} else if (res.json.result != undefined
																								&& res.json.result == '999') {
																							importForm
																									.importSuccessHandler(res.json);
																						}
																					}
																				}
																			});
																};
																impRefreshHandler = window
																		.setInterval(
																				importFresh,
																				1000);
															},
															failure : function() {
															}
														});

											},
											failure : function(form, o) {
												Ext.Msg.show({
													title : 'Result',
													msg : '数据文件上传失败，请稍后重试!',
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.ERROR
												});
											}
										});
					}
				}
			} ]
		});
/**
 * 导入弹出窗口，此对象为全局对象，由各页面直接调用。
 */

var importWindow = new Ext.Window({
	width : 700,
	hideMode : 'offsets',
	modal : true,
	height : 250,
	closeAction : 'hide',
	items : [ importForm ]
});
importWindow.on('show', function(upWindow) {
	if (Ext.getCmp('littleup')) {
		importForm.remove(Ext.getCmp('littleup'));
	}
	importForm.removeAll(true);
	importForm.add(new Ext.form.TextField({
		xtype : 'textfield',
		id : 'littleim',
		name : 'annexeName',
		inputType : 'file',
		fieldLabel : '文件名称',
		anchor : '90%'
	}));
	importForm.doLayout();
});
var progressBar = {};
var importState = false;
var progressWin = new Ext.Window({
	width : 300,
	hideMode : 'offsets',
	closable : true,
	modal : true,
	autoHeight : true,
	closeAction : 'hide',
	items : [],
	listeners : {
		'beforehide' : function() {
			return importState;
		}
	}
});
function showProgressBar(count, curnum, bartext, msg, title) {
	importState = false;
	progressBar = new Ext.ProgressBar({
		width : 285
	});
	progressBar.wait({
		interval : 200, // 每次更新的间隔周期
		duration : 5000, // 进度条运作时候的长度，单位是毫秒
		increment : 5, // 进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
		fn : function() { // 当进度条完成主动更新后履行的回调函数。该函数没有参数。
			progressBar.reset();
		}
	});
	progressWin.removeAll();
	progressWin.setTitle(title);
	if (msg.length == 0) {
		msg = '正在导入...';
	}
	var importContext = new Ext.Panel({
		title : '',
		frame : true,
		region : 'center',
		height : 100,
		width : '100%',
		autoScroll : true,
		html : '<span>' + msg + '</span>'
	});
	progressWin.add(importContext);
	progressWin.add(progressBar);
	progressWin.doLayout();
	progressWin.show();

}
function showSuccessWinImp(curRecordNum) {
	importState = true;
	progressWin.removeAll();
	progressWin.setTitle("成功导入记录数为[" + curRecordNum + "]");
	progressWin.add(new Ext.Panel({
		title : '',
		width : 300,
		layout : 'fit',
		autoHeight : true,
		bodyStyle : 'text-align:center',
		html : '<img src="' + basepath + '/contents/img/UltraMix55.gif" />'
	}));
	progressWin.doLayout();
	progressWin.show();
}

var viewshow = function(view) {
	if (view._defaultTitle == '新增') {
		getCurrentView().contentPanel.getForm().findField('CONCLUSION')
				.setValue('1');
		getCurrentView().contentPanel.form.findField("CUS_RESOURCE_BAK4")
				.hide();
		getCurrentView().contentPanel.form.findField("CUS_RESOURCE_BAK4")
				.setValue('');
	} else if (view._defaultTitle == '客户分配') {
		transForm1.getForm().findField('custMgrName').setVisible(false);
		transForm1.getForm().findField('orgName').setVisible(false);
	}
}

var beforeviewshow = function(view) {
	if (view._defaultTitle == '修改' || view._defaultTitle == '详情') {
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示', '请选择一条数据');
			return false;
		}
		if (getSelectedData().data.CUS_RESOURCE == 20) {
			view.contentPanel.form.findField("CUS_RESOURCE_BAK4").show();
		} else {
			view.contentPanel.form.findField("CUS_RESOURCE_BAK4").hide();
		}
	}
	if (view._defaultTitle == '详情') {
		if (getSelectedData().data.CUS_RESOURCE == 20) {
			view.contentPanel.form.findField("CUS_RESOURCE_BAK4").show();
		} else {
			view.contentPanel.form.findField("CUS_RESOURCE_BAK4").hide();
		}
	}
}