/**
 * @description 理财规划
 * @author likai
 * @since 2014-07-10
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
    padding:'10px 20px 0px',
    html:'以月份作为复利的期次'
    	+'<br>届时累计金额=目标所需金额*(1+预计通货膨胀率)的预计达成目标的年数次方'
    	+'<br>每月投资金额=(届时累计金额-(当前已有现金*(1+预计年报酬率)的预计达成目标的年数次方))*(预计年报酬率/12)*0.01/(1+((预计年报酬率/12)*0.01)的预计达成目标累计的月数次方)-1'
    	+'<br><font color=red>*</font>为必填项'
});

/**
* 计算器输入项
*/
var inputForm = new Ext.FormPanel({
    title: '理财规划（仅供参考）',
    height:240,
    collapsible:true,
    padding:'10px 0px 0px',
    labelWidth:170,
    items:[
        {xtype: 'numberfield',name: 'TIME',fieldLabel: '预计在几年内达成目标(年)<font color=red>*</font>',allowDecimals: false,minValue: 0,width: 200,allowBlank:false},
        {xtype: 'numberfield',name: 'NEED_MONEY',fieldLabel: '目标所需金额(目前货币值)(元)<font color=red>*</font>',viewFn:money('0,000.00'),minValue: 0,width: 200,allowBlank:false},
        {xtype: 'numberfield',name: 'INFLATION_RATE',fieldLabel: '预计通货膨胀率(%)<font color=red>*</font>',minValue: 0,width: 200,allowBlank:false},
        {xtype: 'numberfield',name: 'OWN_MONEY',fieldLabel: '目前已有现金(元)<font color=red>*</font>',viewFn:money('0,000.00'),minValue: 0,width: 200,allowBlank:false},
        {xtype: 'numberfield',name: 'PROFIT_RATE',fieldLabel: '预计年报酬率(%)<font color=red>*</font>',minValue: 0,width: 200,allowBlank:false},
        {
          	columnWidth:1,
          	layout:'column',
          	padding:'5px 210px 0px',
          	items:[
          		{xtype:'button',text:'计算',width:60,style: {marginRight:'10px'},handler:function(){
          			calc();
          		}},
          		{xtype:'button',text:'重置',width:60,handler:function(){
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
    height:155,
    collapsible:true,
    padding:'10px 0px 0px',
    labelWidth:170,
    items:[
         {xtype: 'textarea',name: 'RESULT',fieldLabel: '试算结果',height:70,width: 200,disabled:true}
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
    };
    
    var formValue = inputForm.getForm().getValues();
    var resultMoney1 = 0;
    var resultMoney2 = 0;
    if(Number(formValue.NEED_MONEY) < Number(formValue.OWN_MONEY)){
    	 Ext.Msg.alert('提示','当前已有现金能达到目标所需，请重新输入');
         return false;
    }else{
	    var t = Number(formValue.TIME);
	    var nm = Number(formValue.NEED_MONEY);
	    var ir = Number(formValue.INFLATION_RATE);
	    var om = Number(formValue.OWN_MONEY);
	    var pr = Number(formValue.PROFIT_RATE);
	    
	    resultMoney1 = nm * Math.pow(1 + (ir * 0.01), t);
	    resultMoney2 = ((resultMoney1 - om * (Math.pow(1 + pr * 0.01, t))) * pr * 0.01 / 12) / ((Math.pow(1 + (pr * 0.01) / 12, t * 12)) - 1);
	    
	    outputForm.getForm().findField('RESULT').setValue('为了达到您的目标，每月需投资：'+resultMoney2.toFixed(4)+'元， '
	  	       +'届时累计的金额为：'+resultMoney1.toFixed(4)+'元');
    }
}