/**
 * @description 整存整取计算器
 * @author Fan zheming
 * @since 2014-07-09
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

//远程数据字典
var lookupTypes = ['XD000226'];
//本地数据字典
var localLookup = {
	//存期
	'TIME_TYPE' : [
   		{key : '1',value : '一个月(外币)'},
   		{key : '2',value : '三个月'},
   		{key : '3',value : '六个月'},
   		{key : '4',value : '1年'},
   		{key : '5',value : '2年'},
   		{key : '6',value : '3年(人民币)'},
   		{key : '7',value : '5年(人民币)'}
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
	padding:'10px 20px 0',
	html:'1.存期按一个月(外币/2.25)、三个月(2.75)、六个月(3.05)、一年(3.25)、二年(3.75)、三年(人民币/4.25)、五年(人民币/4.75)。整存整取储蓄存款允许部分提前支取一次, 提前支取部分按活期计息，未取部分仍按原定存期、利率、原起息日计息。 <br>2.应付利息=本金×存期×利率 <br>3.实付利息=应付利息-应税利息×税率（设置为零）<br>4.约定到期自动转存，按复息计算; 第二次应付利息＝(本金+第一次实付利息)×存期×第二次利率.'
});


/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '整存整取计算器',
	height:250,
	collapsible:true,
	padding:'10px 0 0',
	labelWidth:120,
	items:[
	{xtype: 'combo',name: 'CURRENCY',fieldLabel: '币种<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: true,allowBlank:false},
	{xtype: 'datefield',name: 'START_DATE',fieldLabel: '起存日期<font color=red>*</font>',width: 180,format: 'Y-m-d',editable: false,allowBlank:false},
	{xtype: 'combo',name: 'TIME',fieldLabel: '存期<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false, allowBlank:false,
		listeners:{
			select : function(combo,record){
				if(record.data.key == '1'){
					inputForm.getForm().findField('RATE').setValue(0.25);
				}else if(record.data.key == '2'){
					inputForm.getForm().findField('RATE').setValue(2.60);
				}else if(record.data.key == '3'){
					inputForm.getForm().findField('RATE').setValue(2.80);
				}else if(record.data.key == '4'){
					inputForm.getForm().findField('RATE').setValue(3.00);
				}else if(record.data.key == '5'){
					inputForm.getForm().findField('RATE').setValue(3.75);
				}else if(record.data.key == '6'){
					inputForm.getForm().findField('RATE').setValue(4.25);
				}else if(record.data.key == '7'){
					inputForm.getForm().findField('RATE').setValue(4.75);
				}
			}
		}
	},
	{xtype: 'numberfield',name: 'CAPITAL',fieldLabel: '存入本金<font color=red>*</font>',width: 180,allowBlank:false,allowNegative:false},
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
	height:200,
	collapsible:true,
	padding:'10px 0 0',
	labelWidth:120,
	items:[
		{xtype: 'textfield',name: 'RESULT_MONEY1',fieldLabel: '应得利息',width: 180,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY2',fieldLabel: '应付利息税',width: 180,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY3',fieldLabel: '实得利息',width: 180,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY4',fieldLabel: '本息合计',width: 180,disabled:true}
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
 * 结果域面板滑入前触发,系统提供listener事件方法-货币+存期
 * 注意：两组写在一个beforeviewshow下面
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view._defaultTitle == '计算器面板'){
		inputForm.getForm().findField('CURRENCY').bindStore(findLookupByType('XD000226'));
		inputForm.getForm().findField('TIME').bindStore(findLookupByType('TIME_TYPE'));
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
	//起始日期(由用户输入)
	var startD=inputForm.getForm().findField('START_DATE').getValue();
	//日利率, 由年利率得到 (年利率由用户输入)
	var rate=(inputForm.getForm().findField('RATE').getValue())/36500;
	//本金(由用户输入)
	var putin=inputForm.getForm().findField('CAPITAL').getValue();
	//利息总和，结果需要值
	var intes=0;
	//本利和，结果需要值
	var total=0;
	//利息税总和，结果需要值
	var tax=0;
	//利息税率, 暂定为0, 可更改 
	var taxRate=0;
	
	/*
	 * if语句分七种利率情况（分别对应7个时间跨度），进行计算
	 * 其中：利率在if语句中用年利率（单位：%）
	 *       利率在计算过程中折算为日利率
	 * 另外，用startD起存日期计算出对应时间跨度的endD终止日期（更精确）
	 *      用endD-startD经过这算可以得到精确天数
	 * 公式：
	 *       利息=本金*日利率*天数
	 *       利息税=利息*利息税率
	 *       本利和=本金+利息
	 *                           【下同】
	 */
	if(inputForm.getForm().findField('TIME').getValue()==1){
		//计算endD终止日期
		var endD=new Date(startD.getFullYear(),startD.getMonth()+1,startD.getDate());
		//计算interv跨度精确天数
		var interv=((endD-startD)/86400000);
		//计算：利息，利息税，本利和
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	if(inputForm.getForm().findField('TIME').getValue()==2){
		var endD=new Date(startD.getFullYear(),startD.getMonth()+3,startD.getDate());
		var interv=((endD-startD)/86400000);
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	if(inputForm.getForm().findField('TIME').getValue()==3){
		var endD=new Date(startD.getFullYear(),startD.getMonth()+6,startD.getDate());
		var interv=((endD-startD)/86400000);
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	if(inputForm.getForm().findField('TIME').getValue()==4){
		var endD=new Date(startD.getFullYear()+1,startD.getMonth(),startD.getDate());
		var interv=((endD-startD)/86400000);
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	if(inputForm.getForm().findField('TIME').getValue()==5){
		var endD=new Date(startD.getFullYear()+2,startD.getMonth(),startD.getDate());
		var interv=((endD-startD)/86400000);
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	if(inputForm.getForm().findField('TIME').getValue()==6){
		var endD=new Date(startD.getFullYear()+3,startD.getMonth(),startD.getDate());
		var interv=((endD-startD)/86400000);
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	if(inputForm.getForm().findField('TIME').getValue()==7){
		var endD=new Date(startD.getFullYear()+5,startD.getMonth(),startD.getDate());
		var interv=((endD-startD)/86400000);
		intes=putin*rate*interv;
		tax=intes*taxRate;
		total=putin+intes;
	}
	//结果小计
	var resultMoney1 = intes;
	var resultMoney2 = tax;
	var resultMoney3 = intes-tax;
	var resultMoney4 = intes+putin;
	//结果反馈(保留4位小数)
	outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY4').setValue(resultMoney4.toFixed(4));
};