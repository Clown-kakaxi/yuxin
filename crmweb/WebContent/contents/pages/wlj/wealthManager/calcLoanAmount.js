/**
 * @description 贷款金额计算器
 * @author Fan zheming
 * @since 2014-07-11
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

//本地数据字典定义
var localLookup = {
	'REPAY_TYPE' : [
	    {key : '1',value : '等额本息'},
	    {key : '2',value : '等额本金'}
	],
	'CYCLE_TYPE' : [
	    {key : '1',value : '按月还款'},
	    {key : '2',value : '按季还款'},  
	    {key : '3',value : '半年还款'},  
	    {key : '4',value : '按年还款'}
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
	height:150,
	collapsed:false,
	collapsible:true,
	autoHeight:true,
	padding:'10px 20px 0',
	html:'设贷款期限为n(月)，基本还款金额为y，月利率为i。<br> 等额本息还款可贷金额：y*(((1+i)^n-1)/((1+i)^n*i)) <br>等额本金还款可贷金额：n*y/(1+n*i)'
});


/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '贷款金额计算器',
	height:316,
	collapsible:true,
	padding:'10px 0 0',
	labelWidth:120,
	items:[{
		fieldLabel:'贷款种类',
		xtype:'radiogroup',
		width: 360,
		labelStyle: 'padding-top:7px;',
		defaultType: 'radio', 
		items:[{
			checked: true,
			boxLabel: '住房商业贷款',
			name: 'DKZL',
			inputValue: '1',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
				}
			}
		},{  
			boxLabel: '住房公积金贷款',
			name: 'DKZL',
			inputValue:'2',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
				}
			}
		},{  
			boxLabel: '个人消费贷款',
			name: 'DKZL',
			inputValue:'3',
			listeners : {
				"check" : function(checkbox,checked) {
					if(!checked){
						return;
					}
				}
			}
		}
		]
	},
	{xtype: 'datefield',name: 'START_DATE',fieldLabel: '起贷日期<font color=red>*</font>',width: 180,format: 'Y-m-d',editable: false,allowBlank:false},
	{xtype: 'numberfield',name: 'TERM',fieldLabel: '贷款期限(月)<font color=red>*</font>',width: 180,allowBlank:false,allowNegative:false,allowDecimals:false},
	{xtype: 'combo',name: 'REPAY',fieldLabel: '还款方式<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false},
	{xtype: 'combo',name: 'CYCLE',fieldLabel: '还款周期<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false},
	{xtype: 'numberfield',name: 'BASE_REPAY',fieldLabel: '基本还款金额(元)<font color=red>*</font>',width: 180,allowBlank:false,allowNegative:false},
	{xtype: 'numberfield',name: 'RATE',fieldLabel: '年利率(%)<font color=red>*</font>',width: 180,allowBlank:false,allowNegative:false},
	{
	    columnWidth:1,
	    layout:'column',
	    padding:'5px 115px 0',
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
	title: '计算结果(仅供参考)',
	height:100,
	collapsible:true,
	padding:'10px 0 0',
	labelWidth:120,
	items:[
		{xtype: 'textfield',name: 'RESULT_APPLY',fieldLabel: '可贷款金额 ',width: 180,disabled:true}
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
		inputForm.getForm().findField('REPAY').bindStore(findLookupByType('REPAY_TYPE'));
		inputForm.getForm().findField('CYCLE').bindStore(findLookupByType('CYCLE_TYPE'));
	}
};

/**
 * 计算方法
 */
var calc = function(){
	//空缺检测
	if(!inputForm.getForm().isValid()){
		Ext.Msg.alert('提示','输入格式有误或存在漏输入项，请重新输入');
		return false;
	}
	
	/*
	 * 开始定义/引入变量
	 */
	//起贷日期(由用户输入)
	var startD=inputForm.getForm().findField('START_DATE').getValue();
	//还款方式(由用户输入)
	var repayType=inputForm.getForm().findField('REPAY').getValue();
	//基本还款金额
	var baseRepay=inputForm.getForm().findField('BASE_REPAY').getValue();
	//还款周期(由用户输入)
	var cycle=inputForm.getForm().findField('CYCLE').getValue();
	//贷款金额(结果需要值)
	var permitApply=0;
	
	/*
	 * 分两种情况讨论(等额本息/等额本金)
	 */
	if(repayType==1){
		//本情况为：等额本息
		if(cycle==1){
			//贷款期限(由用户输入), 单位:月【月】
			var term=inputForm.getForm().findField('TERM').getValue();
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/1200;
		}else if(cycle==2){
			//贷款期限(由用户输入), 单位:月【季】
			var term=inputForm.getForm().findField('TERM').getValue()/3;
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/400;
		}else if(cycle==3){
			//贷款期限(由用户输入), 单位:月【半年】
			var term=inputForm.getForm().findField('TERM').getValue()/6;
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/200;
		}else{
			//贷款期限(由用户输入), 单位:月【年】
			var term=inputForm.getForm().findField('TERM').getValue()/12;
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/100;
		}
		permitApply=baseRepay*((pow1(rate,term))-1)/((pow1(rate,term))*rate);
	}else{
		//本情况为：等额本金
		if(cycle==1){
			//贷款期限(由用户输入), 单位:月【月】
			var term=inputForm.getForm().findField('TERM').getValue();
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/1200;
		}else if(cycle==2){
			//贷款期限(由用户输入), 单位:月【季】
			var term=inputForm.getForm().findField('TERM').getValue()/3;
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/400;
		}else if(cycle==3){
			//贷款期限(由用户输入), 单位:月【半年】
			var term=inputForm.getForm().findField('TERM').getValue()/6;
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/200;
		}else{
			//贷款期限(由用户输入), 单位:月【年】
			var term=inputForm.getForm().findField('TERM').getValue()/12;
			//月利率(由用户输入年利率获得)
			var rate=(inputForm.getForm().findField('RATE').getValue())/100;
		}
//		permitApply=baseRepay*term/(1+term*rate);
		permitApply=baseRepay*term;
	}
	outputForm.getForm().findField('RESULT_APPLY').setValue(permitApply.toFixed(4));
};

/**
 * 计算（1+i）^n次方的方法
 * @param i 导入被乘方括号内的值
 * @param n 导入乘方数
 * @returns
 */
var pow1 = function(i,n){
	var pow=1+i;
	for(var j=0;j<n-1;j++){
		pow=pow*(1+i);
	}
	return pow;
};