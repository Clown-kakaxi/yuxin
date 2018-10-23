/**
 * @description 年金计算器
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
    html:'普通年金终值=年金值*(1+收益率)的期数次方/收益率'
    	+'<br>普通年金现值=年金值*(1-(1+收益率)的负的期数次方)/收益率'
    	+'<br><font color=red>*</font>为必填项'
});

/**
* 计算器输入项
*/
var inputForm = new Ext.FormPanel({
    title: '年金计算器（仅供参考）',
    height:250,
    collapsible:true,
    padding:'10px 0px 0px',
    labelWidth:120,
    items:[
		{
			fieldLabel:'年金类型',
			xtype:'radiogroup',
			width: 200,
			labelStyle: 'padding-top:7px;',
			defaultType: 'radio', 
			items:[{
				checked: true,
				boxLabel: '年金终值',
				name: 'PENSION_TYPE',
				inputValue: '1',
				listeners : {
					"check" : function(checkbox,checked) {
						if(!checked){
							return;
						}
						Ext.query('label[for=RESULT_MONEY]')[0].innerHTML = '年金终值(元):';
						Ext.query('label[for=RESULT_MONEY1]')[0].innerHTML = '年金现值(元)<font color=red>*</font>:';
					}
				}
			},{  
				boxLabel: '年金现值',
				name: 'PENSION_TYPE',
				inputValue:'2',
				listeners : {
					"check" : function(checkbox,checked) {
						if(!checked){
							return;
						}
						Ext.query('label[for=RESULT_MONEY]')[0].innerHTML = '年金现值(元):';
						Ext.query('label[for=RESULT_MONEY1]')[0].innerHTML = '年金终值(元)<font color=red>*</font>:';
					}
				}
			}]
		},
        {xtype: 'numberfield',name: 'RATE',fieldLabel: '收益率(%)<font color=red>*</font>',minValue: 0,width: 200,allowBlank:true},
        {xtype: 'numberfield',name: 'TIMES',fieldLabel: '期数(期)<font color=red>*</font>',allowDecimals: false,minValue: 0,width: 200,allowBlank:true},
        {xtype: 'numberfield',name: 'PMT',fieldLabel: '年金值(元)<font color=red>*</font>',viewFn:money('0,000.00'),minValue: 0,width: 200,allowBlank:true},
        {xtype: 'numberfield',id: 'RESULT_MONEY1',name: 'VALUE',fieldLabel: '年金现值(元)<font color=red>*</font>',viewFn:money('0,000.00'),width : 200,allowBlank:true},
        {
          	columnWidth:1,
          	layout:'column',
          	padding:'5px 150px 0px',
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
    height:90,
    collapsible:true,
    padding:'10px 0px 0px',
    labelWidth:120,
    viewFn:money('0,000.00'),
    items:[
         {xtype: 'numberfield',id: 'RESULT_MONEY',name: 'RESULT_MONEY',fieldLabel: '年金终值(元)',width: 200,disabled:true}
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
    var A = Number(formValue.PMT);
    var i = Number(formValue.RATE);
    var n = Number(formValue.TIMES);
    var V = Number(formValue.VALUE);
    
    
    //1、求年金终值
    if(formValue.PENSION_TYPE == '1'){
//    	resultMoney = A * (Math.pow((i * 0.01 + 1), n) - 1)/ (i * 0.01);
    	resultMoney = A * (Math.pow((i * 0.01 + 1), n) - 1)/ (i * 0.01) + V*(Math.pow(1+i*0.01),n);
    	outputForm.getForm().findField('RESULT_MONEY').setValue(resultMoney.toFixed(4));
    }
    
    //2、求年金现值
    if(formValue.PENSION_TYPE == '2'){
//    	resultMoney = A * (Math.pow(1 + (i * 0.01), n) - 1) / (i * 0.01 * Math.pow(1 + (i * 0.01), n));
    	resultMoney = ((V-A*(Math.pow((i*0.01 + 1),n) - 1)/(i*0.01)))/(Math.pow(i*0.01 + 1),n);
    	outputForm.getForm().findField('RESULT_MONEY').setValue(resultMoney.toFixed(4));
    }
    
}
