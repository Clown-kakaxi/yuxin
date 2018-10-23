/**
 * @description 活期储蓄存款计算器
 * @author Fan zheming
 * @since 2014-07-07
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews = false; // 自定义面板是否浮动

// 引用数据字典
var lookupTypes = ['XD000226'];

var fields = [{
			name : 'TEST',
			text : '此文件fields必须要有一个无用字段',
			resutlWidth : 80
		}];

/**
 * 工具说明form
 */
var notesForm = new Ext.Panel({
	title : '工具说明',
	// height:160,
	collapsed : false,
	collapsible : true,
	autoHeight : true,
	padding : '10px 20px 0',
	html : '实得/应得利息＝∑第N段利息＝(N－1)段本息和×N段计息天数×计息日挂牌活期日利率 <br>应付利息税＝∑第N段利息×利息税率 <br>本息合计＝本金＋实得利息<br>注意:利息税为零,折算日利率为0.00096%'
});

/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
			title : '活期储蓄存款计算器',
			height : 250,
			collapsible : true,
			padding : '10px 0 0',
			labelWidth : 120,
			items : [{
						xtype : 'combo',
						name : 'CURRENCY',
						fieldLabel : '币种<font color=red>*</font>',
						width : 180,
						mode : 'local',
						store : new Ext.data.Store(),// 数据读取、类型转换、搜索
						triggerAction : 'all',
						displayField : 'value',
						valueField : 'key',
						editable : true,
						allowBlank : false
					}, {
						xtype : 'datefield',
						name : 'START_DATE',
						fieldLabel : '起存日期<font color=red>*</font>',
						width : 180,
						format : 'Y-m-d',
						editable : false,
						allowBlank : false
					}, {
						xtype : 'datefield',
						name : 'END_DATE',
						fieldLabel : '终止日期<font color=red>*</font>',
						width : 180,
						format : 'Y-m-d',
						editable : false,
						allowBlank : false
					}, {
						xtype : 'numberfield',
						name : 'CAPITAL',
						fieldLabel : '存入本金<font color=red>*</font>',
						width : 180,
						allowBlank : false,
						allowNegative : false
					}, {
						xtype : 'numberfield',
						name : 'RATE',
						fieldLabel : '年利率(%)<font color=red>*</font>',
						width : 180,
						allowBlank : false,
						allowNegative : false
					}, {
						columnWidth : 1,
						layout : 'column',
						padding : '5px 115px 0',
						items : [{
									xtype : 'button',
									text : '计算',
									width : 80,
									style : {
										marginRight : '10px'
									},
									handler : function() {
										calc();
									}
								}, {
									xtype : 'button',
									text : '重置',
									width : 80,
									handler : function() {
										inputForm.getForm().reset();
										outputForm.getForm().reset();
									}
								}]
					}]
		});

/**
 * 计算器输出项说明
 */
var outputForm = new Ext.FormPanel({
			title : '计算结果(仅供参考)',
			height : 200,
			collapsible : true,
			padding : '10px 0 0',
			labelWidth : 120,
			items : [{
						xtype : 'textfield',
						name : 'RESULT_MONEY1',
						fieldLabel : '应得利息',
						width : 180,
						disabled : true
					}, {
						xtype : 'textfield',
						name : 'RESULT_MONEY2',
						fieldLabel : '应付利息税',
						width : 180,
						disabled : true
					}, {
						xtype : 'textfield',
						name : 'RESULT_MONEY3',
						fieldLabel : '实得利息',
						width : 180,
						disabled : true
					}, {
						xtype : 'textfield',
						name : 'RESULT_MONEY4',
						fieldLabel : '本息合计',
						width : 180,
						disabled : true
					}]
		});

// 结果域扩展功能面板
var customerView = [{
			/**
			 * 自定义计算器面板
			 */
			title : '计算器面板',
			hideTitle : true,
			layout : 'form',
			items : [inputForm, outputForm, notesForm]
		}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * 
 * @param {}
 *            view
 * @return {Boolean}
 */
var beforeviewshow = function(view) {
	if (view._defaultTitle == '计算器面板') {
		inputForm.getForm().findField('CURRENCY')
				.bindStore(findLookupByType('XD000226'));
	}
};

/**
 * 计算方法
 */
var calc = function() {
	// 空缺检测
	if (!inputForm.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入格式有误或存在漏输入项，请重新输入');
		return false;
	}
	/*
	 * 开始定义/引入变量
	 */
	// 每段开始日期
	var dateA = inputForm.getForm().findField('START_DATE').getValue();
	// 每段结束日期
	var dateB = calcNextDate(dateA);
	// 终止日期(由用户输入)
	var endD = inputForm.getForm().findField('END_DATE').getValue();
	var currency = inputForm.getForm().findField('CURRENCY').getValue();
	// 利息总和，结果需要值
	var intes = 0;
	// 本利和，结果需要值
	var total = 0;
	// 利息税总和，结果需要值
	var tax = 0;
	// 利息税率, 暂定为0, 可更改
	var taxRate = 0;
	// 日利率, 由年利率得到 (年利率由用户输入)
	var rate = inputForm.getForm().findField('RATE').getValue();
	// 本金(由用户输入)
	var putin = inputForm.getForm().findField('CAPITAL').getValue();
	/*
	 * 校验起存日期和终止日期是否符合规范
	 */
	if (dateA > endD) {
		Ext.Msg.alert('提示', '起存日期必须小于终止日期，请重新输入');
		return false;
	}
	/*
	 * 第一次比较，若本段结束日期超出终止日期则没有利息(税),只有本金 （即：用户输入日期区间没有达到结息日）
	 */
	// 否则, 开始计算利息
	// 利息=本金*日利率*(本段日期区间)
	var date_day = "";
	switch (currency) {
		case '978' :
			date_day = parseInt(rate) / 36000;
			break;
		case '826' :
			date_day = parseInt(rate) / 36500;
			break;
		case '344' :
			date_day = parseInt(rate) / 36500;
			break;
		case '392' :
			date_day = parseInt(rate) / 36000;
			break;
		case '840' :
			date_day = parseInt(rate) / 36000;
			break;
		case '156' :
			date_day = parseInt(rate) / 36000;
			break;
		case '036' :
			date_day = parseInt(rate) / 36500;
			break;
		case '756' :
			date_day = parseInt(rate) / 36000;
			break;
		case '901' :
			date_day = parseInt(rate) / 36500;
			break;
		case '124' :
			date_day = parseInt(rate) / 36000;
			break;
		case '702' :
			date_day = parseInt(rate) / 36500;
			break;
		case '554' :
			date_day = parseInt(rate) / 36500;
			break;
		default :
			date_day = parseInt(rate) / 36000;
	}
	intes = putin * date_day * ((endD - dateA) / 86400000);
	tax = intes * taxRate;
	total = putin + intes;

	var resultMoney1 = intes;
	var resultMoney2 = tax;
	var resultMoney3 = intes - tax;
	var resultMoney4 = intes + putin;
	outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1
			.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2
			.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3
			.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY4').setValue(resultMoney4
			.toFixed(4));
};

/**
 * 输入date计算下一个结息节点日的方法
 * 
 * @param date
 *            传入date return d 返回相应节点(3/6/9/12月20日)
 */
var calcNextDate = function(date) {
	var d1 = new Date(date.getFullYear(), 2, 20);
	var d2 = new Date(date.getFullYear(), 5, 20);
	var d3 = new Date(date.getFullYear(), 8, 20);
	var d4 = new Date(date.getFullYear(), 11, 20);
	if (date < d1) {
		return d1;
	}
	if (date < d2) {
		return d2;
	}
	if (date < d3) {
		return d3;
	}
	if (date < d4) {
		return d4;
	}
	return new Date(date.getFullYear() + 1, 2, 20);
};