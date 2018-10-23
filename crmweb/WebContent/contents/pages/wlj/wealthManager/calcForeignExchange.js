/**
 * @description 外汇兑换计算器
 * @author helin
 * @since 2014-07-02
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

//本地数据字典定义
var localLookup = {
	'CURRENCY_TYPE' : [
		{key : 'AUD',value : '澳大利亚元'},
		{key : 'CAD',value : '加拿大元'},  
		{key : 'CHF',value : '瑞士法郎'},  
		{key : 'CNY',value : '人民币'},    
		{key : 'DKK',value : '丹麦克朗'},  
		{key : 'EUR',value : '欧元'},      
		{key : 'GBP',value : '英镑'},      
		{key : 'HKD',value : '港币'},      
		{key : 'JPY',value : '日元'},      
		{key : 'MOP',value : '澳门币'},    
		{key : 'NOK',value : '挪威克朗'},  
		{key : 'NZD',value : '新西兰元'},  
		{key : 'SEK',value : '瑞典克朗'},  
		{key : 'SGD',value : '新加坡元'},  
		{key : 'USD',value : '美元'}
	]
};

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
	html: '用1个单位或100个单位的外国货币作为标准，折算为一定数额的本国货币，叫做直接标价法(DirectQuotation)。'
		+'用1个单位或100个单位的本国货币作为标准，折算为一定数量的外国货币，叫做间接标价法(IndirectQuotation)。'
		+'<br>1、现钞兑入：客户应得人民币=外汇现钞金额×当日公布的现钞买入价（直接标价汇率）'
		+' 或 客户应得人民币=外汇现钞金额/当日公布的现钞买入价 （间接标价汇率）'
		+'<br>2、现汇兑入：客户应得人民币=外汇现汇金额*当日公布的现汇买入价（直接标价汇率）'
		+' 或 客户应得人民币=外汇现汇金额／当日公布的现汇买入价 （间接标价汇率）'
		+'<br>3、兑出：客户应得人民币=人民币现钞金额／当日公布的外汇或现钞卖出价'
});

/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '外币兑换计算器（仅供参考）',
	height:260,
	collapsible:true,
	items:[{
		fieldLabel:'汇率标志',
		xtype:'radiogroup',
		width: 200,
		labelStyle: 'padding-top:7px;',
		defaultType: 'radio', 
		items:[{  
			checked: true,
			boxLabel: '直接标价汇率',
			name: 'HLBZ',
			inputValue: '1',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
				}
			}
		},{  
			boxLabel: '间接标价汇率',
			name: 'HLBZ',
			inputValue:'2',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
				}
			}
		}] 
	},
	{
		fieldLabel:'买卖标志',
		xtype:'radiogroup',
		width: 200,
		labelStyle: 'padding-top:7px;',
		defaultType: 'radio', 
		items:[{  
			checked: true,
			boxLabel: '兑入',
			name: 'MMBZ',
			inputValue: '1',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
					Ext.query('label[for=RATE]')[0].innerHTML = '汇率<font color=red>*</font>:';
				}
			}
		},{  
			boxLabel: '兑出',
			name: 'MMBZ',
			inputValue:'2',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
					Ext.query('label[for=RATE]')[0].innerHTML = '现钞卖出价<font color=red>*</font>:';
				}
			}
		}] 
	},
	{xtype: 'combo',name: 'CURRENCY',fieldLabel: '外币币种<font color=red>*</font>',width: 200,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false},
	{xtype: 'numberfield',name: 'DEP_MONEY',fieldLabel: '外币金额<font color=red>*</font>',decimalPrecision:4,width: 200,allowBlank:false},
	{xtype: 'numberfield',id:'RATE',name: 'RATE',fieldLabel: '汇率<font color=red>*</font>',decimalPrecision:4,width: 200,allowBlank:false},
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
	items:[
		{xtype: 'textfield',name: 'RESULT_MONEY',fieldLabel: '人民币金额(元)',width: 200,disabled:true}
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
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view._defaultTitle == '计算器面板'){
		inputForm.getForm().findField('CURRENCY').bindStore(findLookupByType('CURRENCY_TYPE'));
	}
};

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
	//兑入
	if(formValue.MMBZ == '1'){
		if(formValue.HLBZ == '1'){
			resultMoney = formValue.DEP_MONEY * formValue.RATE;
		}
		if(formValue.HLBZ == '2'){
			resultMoney = formValue.DEP_MONEY / formValue.RATE;
		}
		outputForm.getForm().findField('RESULT_MONEY').setValue(resultMoney.toFixed(4));
	}
	//兑出
	if(formValue.MMBZ == '2'){
		resultMoney = formValue.DEP_MONEY / formValue.RATE;
		outputForm.getForm().findField('RESULT_MONEY').setValue(resultMoney.toFixed(4));
	}
}