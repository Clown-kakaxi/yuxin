/**
 * @description 理财认购计算器
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
	html:'预期收益额 = (认购金额 × 期限 × 年化收益率 ) / 计息基础（360或365）'
});

/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '理财认购计算器（仅供参考）',
	height:260,
	collapsible:true,
	labelWidth:120,
	items:[{
		fieldLabel:'计息方式',
		xtype:'radiogroup',
		width: 200,
		labelStyle: 'padding-top:7px;',
		defaultType: 'radio', 
		items:[{  
			checked: true,
			boxLabel: '万元',
			name: 'JXFS',
			inputValue: '1'
		},{  
			boxLabel: '千元',
			name: 'JXFS',
			inputValue:'2'
		},{  
			boxLabel: '全额',
			name: 'JXFS',
			inputValue:'3'
		}] 
	},
	{xtype: 'numberfield',name: 'APPLY_MONEY',fieldLabel: '认购金额(元)<font color=red>*</font>',width: 180,allowBlank:false},
	{xtype: 'numberfield',name: 'RATE',fieldLabel: '年化收益率(%)<font color=red>*</font>',width: 180,allowBlank:false},
	{xtype: 'numberfield',name: 'DAY',fieldLabel: '期限(天)<font color=red>*</font>',width: 180,allowBlank:false},
	{
		fieldLabel:'计息基础',
		xtype:'radiogroup',
		width: 200,
		labelStyle: 'padding-top:7px;',
		defaultType: 'radio', 
		items:[{  
			checked: true,
			boxLabel: '365',
			name: 'JXJC',
			inputValue: '365'
		},{  
			boxLabel: '360',
			name: 'JXJC',
			inputValue:'360'
		}] 
	},{
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
		{xtype: 'textfield',name: 'RESULT_MONEY',fieldLabel: '预期收益额(元)',width: 180,disabled:true}
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
	if(formValue.JXFS == '1'){
		//计算实际计算金额
		//formValue.APPLY_MONEY = Number(formValue.APPLY_MONEY) - Number(formValue.APPLY_MONEY)%10000;
		resultMoney = (Number(formValue.APPLY_MONEY) * Number(formValue.DAY) * Number(formValue.RATE) * 0.01) / Number(formValue.JXJC);
	}
	if(formValue.JXFS == '2'){
		//计算实际计算金额
		//formValue.APPLY_MONEY = Number(formValue.APPLY_MONEY) - Number(formValue.APPLY_MONEY)%1000;
		resultMoney = (Number(formValue.APPLY_MONEY) * Number(formValue.DAY) * Number(formValue.RATE) * 0.01) / Number(formValue.JXJC);
	}
	if(formValue.JXFS == '3'){
		resultMoney = (Number(formValue.APPLY_MONEY) * Number(formValue.DAY) * Number(formValue.RATE) * 0.01) / Number(formValue.JXJC);
	}
	outputForm.getForm().findField('RESULT_MONEY').setValue(resultMoney.toFixed(4));
}