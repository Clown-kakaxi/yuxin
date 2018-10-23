/**
 * @description 债券认购收益率计算器
 * @author helin
 * @since 2014-07-02
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

var fields = [
	{name: 'TEST',text:'此文件fields必须要有一个无用字段', resutlWidth:80}
];

/**
 * 工具说明form
 */
var notesForm = new Ext.Panel({
	title: '工具说明',
	//height:160,
	collapsed:false,
	collapsible:true,
	autoHeight:true,
	padding:'10 20 0',
	html:'债券认购收益率 ＝ ((债券单位面值+债券单位面值*债券票面年利率*债券偿还期限)/债券单位发行价格)*100% - 100%'
});

/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '债券认购收益率计算器（仅供参考）',
	height:210,
	collapsible:true,
	padding:'10 0 0',
	labelWidth:120,
	items:[
	{xtype: 'numberfield',name: 'BOND_MONEY',fieldLabel: '债券面值(元)<font color=red>*</font>',width: 180,allowBlank:false},
	{xtype: 'numberfield',name: 'UNIT_PRICE',fieldLabel: '单位发行价格(元)<font color=red>*</font>',width: 180,allowBlank:false},
	{xtype: 'numberfield',name: 'RATE',fieldLabel: '票面利率(%)<font color=red>*</font>',width: 180,allowBlank:false},
	{xtype: 'numberfield',name: 'YEAR',fieldLabel: '还款期限(年)<font color=red>*</font>',width: 180,allowBlank:false},
	{
	    columnWidth:1,
	    layout:'column',
	    padding:'5 115 0',
	    items:[
			{xtype:'button',text:'计算',width:80,style: {marginRight:'10px'},handler:function(){
				calc();
			}},
			{xtype:'button',text:'重置',width:80,handler:function(){
				inputForm.getForm().reset();
				outputForm.getForm().reset();
			}}
		]
	}
	]
});

/**
 * 计算器输出项说明
 */
var outputForm = new Ext.FormPanel({
	title: '计算结果',
	height:100,
	collapsible:true,
	padding:'10 0 0',
	labelWidth:120,
	items:[
		{xtype: 'textfield',name: 'RESULT_MONEY',fieldLabel: '债券认购收益率(%)',width: 180,disabled:true}
	]
});


//结果域扩展功能面板
var customerView = [{
	/**
	 * 自定义计算器面板
	 */
	title:'计算器面板',
	hideTitle: true,
	layout: 'form',
	items: [inputForm,outputForm,notesForm]
}];

/**
 * 计算方法
 */
var calc = function(){
	if(!inputForm.getForm().isValid()){
		Ext.Msg.alert('提示','输入格式有误或存在漏输入项，请重新输入');
		return false;
	}
	var formValue = inputForm.getForm().getValues();
	var resultMoney = 0;
	//债券认购收益率 ＝ ((债券单位面值+债券单位面值*债券票面年利率*债券偿还期限)/债券单位发行价格)*100% - 100%
	resultMoney = (Number(formValue.BOND_MONEY) + Number(formValue.BOND_MONEY) * Number(formValue.RATE) * Number(formValue.YEAR) * 0.01)/Number(formValue.UNIT_PRICE) * 100 - 100;
	outputForm.getForm().findField('RESULT_MONEY').setValue(resultMoney.toFixed(2));
}