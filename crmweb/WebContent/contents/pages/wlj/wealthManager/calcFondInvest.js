/**
 * @description 基金定投
 * @author likai
 * @since 2014-07-10
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

//本地数据字典定义
var localLookup = {
		'CUT_PAYMENT_DAY' : [
			{key : '1',value : '01'},
			{key : '2',value : '02'},
			{key : '3',value : '03'},
			{key : '4',value : '04'},
			{key : '5',value : '05'},
			{key : '6',value : '06'},
			{key : '7',value : '07'},
			{key : '8',value : '08'},
			{key : '9',value : '09'},
			{key : '10',value : '10'},
			{key : '11',value : '11'},
			{key : '12',value : '12'},
			{key : '13',value : '13'},
			{key : '14',value : '14'},
			{key : '15',value : '15'},
			{key : '16',value : '16'},
			{key : '17',value : '17'},
			{key : '18',value : '18'},
			{key : '19',value : '19'},
			{key : '20',value : '20'},
			{key : '21',value : '21'},
			{key : '22',value : '22'},
			{key : '23',value : '23'},
			{key : '24',value : '24'},
			{key : '25',value : '25'},
			{key : '26',value : '26'},
			{key : '27',value : '27'},
			{key : '28',value : '28'},
			{key : '29',value : '29'},
			{key : '30',value : '30'},
			{key : '31',value : '31'}
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
    html:'<br><font color=red>*</font>为必填项'
});

/**
* 计算器输入项
*/
var inputForm = new Ext.FormPanel({
    title: '基金定时定额投资报酬率试算（仅供参考）',
    height:400,
    collapsible:true,
    padding:'10 0 0',
    labelWidth:120,
    items:[
        {xtype: 'textfield',name: 'NAME',fieldLabel: '基金名称<font color=red>*</font>',width: 180,allowBlank:false},
        {xtype: 'datefield',name: 'START_DATE',fieldLabel: '开始扣款日期<font color=red>*</font>',format: 'Y-m-d',width: 180,allowBlank:false},
        {xtype: 'combo',name: 'CUT_PAYMENT_DAY',fieldLabel: '每月扣款日<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false},
        {xtype: 'numberfield',name: 'DEDUCTION_MONEY',fieldLabel: '每次扣款金额<font color=red>*</font>',width: 180,allowBlank:false},
        {
			fieldLabel:'手续费收费方式',
			xtype:'radiogroup',
			width: 180,
			labelStyle: 'padding-top:7px;',
			defaultType: 'radio', 
			items:[{
				checked: true,
				boxLabel: '前收',
				name: 'CHARGE_TYPE',
				inputValue: '1',
				listeners : {
					"check" : function(checkbox,checked) {
						if(!checked){
							return;
						}
						Ext.getCmp('CHARGE_RATE2').setVisible(false);
						inputForm.getForm().findField('CHARGE_RATE').setVisible(true);
						inputForm.getForm().findField('CHARGE_RATE').allowBlank=false;
						inputForm.setHeight(400);
					}
				}
			},{  
				boxLabel: '后收',
				name: 'CHARGE_TYPE',
				inputValue:'2',
				listeners : {
					"check" : function(checkbox,checked) {
						if(!checked){
							return;
						}
						Ext.getCmp('CHARGE_RATE2').setVisible(true);
						inputForm.getForm().findField('CHARGE_RATE').setVisible(false);
						inputForm.getForm().findField('CHARGE_RATE').allowBlank=true;
						inputForm.setHeight(480);
					}
				}
			}]
		},
        {xtype: 'numberfield',id: 'CHARGE_RATE',name: 'CHARGE_RATE',fieldLabel: '手续费率(%)<font color=red>*</font>',width: 180,allowBlank:false,hidden:false},
        {
		    id: 'CHARGE_RATE2',
		    hideParent : false,
		    fieldLabel: '手续费率级距<font color=red>*</font>',
		    width: 280,
		    height: 100,
		    items:[{
                xtype: 'compositefield',
                fieldLabel: '',
                combineErrors: false,
                items: [{
                    xtype: 'displayfield',
                    value: '第'
                },{
                    name : 'YEAR',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false,//正数
                    decimalPrecision:0//整数
                }, {
                    xtype: 'displayfield',
                    value: '年内，手续费'
                },{
                	name : 'CHARGE_RATE2',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false
                },{
                    xtype: 'displayfield',
                    value: '%'
                }]
		    },
		    {
                xtype: 'compositefield',
                fieldLabel: '',
                combineErrors: false,
                items: [{
                    xtype: 'displayfield',
                    value: '第'
                },{
                    name : 'YEAR',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false,//正数
                    decimalPrecision:0
                }, {
                    xtype: 'displayfield',
                    value: '年内，手续费'
                },{
                	name : 'CHARGE_RATE2',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false
                },{
                    xtype: 'displayfield',
                    value: '%'
                }]
		    	
		    },
		    {
                xtype: 'compositefield',
                fieldLabel: '',
                combineErrors: false,
                items: [{
                    xtype: 'displayfield',
                    value: '第'
                },{
                    name : 'YEAR',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false,//正数
                    decimalPrecision:0
                }, {
                    xtype: 'displayfield',
                    value: '年内，手续费'
                },{
                	name : 'CHARGE_RATE2',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false
                },{
                    xtype: 'displayfield',
                    value: '%'
                }]
		    
		    },
		    {
                xtype: 'compositefield',
                fieldLabel: '',
                combineErrors: false,
                items: [{
                    xtype: 'displayfield',
                    value: '第'
                },{
                    name : 'YEAR',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false,//正数
                    decimalPrecision:0
                }, {
                    xtype: 'displayfield',
                    value: '年内，手续费'
                },{
                	name : 'CHARGE_RATE2',
                    xtype: 'numberfield',
                    width: 80,
                    allowNegative:false
                },{
                    xtype: 'displayfield',
                    value: '%'
                }]
		    }]
        },
        {xtype: 'numberfield',name: 'PURCHASE_NUMBER',fieldLabel: '累计申购单位数<font color=red>*</font>',width: 180,allowBlank:false},
        {xtype: 'datefield',name: 'REDEMPTION_DATE',fieldLabel: '赎回日期',format: 'Y-m-d',width: 180,allowBlank:true},
        {xtype: 'numberfield',name: 'REDEMPTION_NUMBER',fieldLabel: '赎回净值<font color=red>*</font>',width: 180,allowBlank:false},
        {
          	columnWidth:1,
          	layout:'column',
          	padding:'5 115 0',
          	items:[
          		{xtype:'button',text:'重新计算',width:80,style: {marginRight:'10px'},handler:function(){
          			calc();
          		}}
          	]
         }
         ]
});

/**
* 计算器输出项说明
*/
var outputForm = new Ext.FormPanel({
    title: '定时定额-计算结果',
    height:210,
    collapsible:true,
    padding:'10 0 0',
    labelWidth:120,
    items:[
         {xtype: 'numberfield',name: 'INVEST_MONEY',fieldLabel: '投资总成本(元)',width: 180,disabled:true,unitText:'元'},
         {xtype: 'numberfield',name: 'DEDUCTION_TIMES',fieldLabel: '累计扣款次数(次)',width: 180,disabled:true},
         {xtype: 'numberfield',name: 'REDEMPTION_MONEY',fieldLabel: '现金/赎回总金额(元)',width: 180,disabled:true},
         {xtype: 'numberfield',name: 'PROFIT_AND_LOSS',fieldLabel: '损益(元)',width: 180,disabled:true},
         {xtype: 'numberfield',name: 'PAY_RATE',fieldLabel: '报酬率(%)',width: 180,disabled:true}
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


var beforeviewshow = function(view){
	if(view._defaultTitle == '计算器面板'){
		inputForm.getForm().findField('CUT_PAYMENT_DAY').bindStore(findLookupByType('CUT_PAYMENT_DAY'));
		Ext.getCmp('CHARGE_RATE2').setVisible(false);
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
    var resultMoney1 = 0;
    var resultMoney2 = 0;
    var resultMoney3 = 0;
    var resultMoney4 = 0;
    var resultMoney5 = 0;
    var deduction = Number(formValue.DEDUCTION_MONEY);
    var rate = Number(formValue.CHARGE_RATE);

	var date1 = formValue.START_DATE;//开始扣款日期
    
    if(formValue.CHARGE_TYPE == '1'){//选择前收时
    	var Months;//定义累积扣款月数
    	date1Arr = date1.split('-');
    	if(formValue.REDEMPTION_DATE != null && formValue.REDEMPTION_DATE != ""){//有赎回日期时
    		var date2 = formValue.REDEMPTION_DATE;
    		date2Arr = date2.split('-');
    		var year = parseInt(date2Arr[0]) - parseInt(date1Arr[0]);
    		var month = parseInt(date2Arr[1]) - parseInt(date1Arr[1]);
    		var day = parseInt(date2Arr[2]) - parseInt(date1Arr[2]);
    		if(day >= 0){
    			Months = 1+ year * 12 + month;
    		}else{
    			Months = year * 12 + month;
    		}
        	resultMoney1 = deduction * (1 + rate * 0.01) * Months;
    	}else if(formValue.REDEMPTION_DATE == null || formValue.REDEMPTION_DATE == ""){//没有赎回日期时
    		var today = new Date();
    		today.format('Y-m-d');
    		var year = today.getFullYear() - parseInt(date1Arr[0]);
    		var month = (today.getMonth() + 1) - parseInt(date1Arr[1]);
    		var day = today.getDay() - parseInt(date1Arr[2]);
    		if(day >= 0){
    			Months = 1+ year * 12 + month;
    		}else{
    			Months = year * 12 + month;
    		}
        	resultMoney1 = deduction * (1 + rate * 0.01) * Months;
    	}
    }else if(formValue.CHARGE_TYPE == '2'){//选择后收时
    	var Year = Number(formValue.YEAR);//定义年数
    	var Months;//定义累积扣款月数
    	date1Arr = date1.split('-');
    	if(formValue.REDEMPTION_DATE != null && formValue.REDEMPTION_DATE != ""){//有赎回日期时
    		var date2 = formValue.REDEMPTION_DATE;
    		date2Arr = date2.split('-');
    		var year = parseInt(date2Arr[0]) - parseInt(date1Arr[0]);
    		var month = parseInt(date2Arr[1]) - parseInt(date1Arr[1]);
    		var day = parseInt(date2Arr[2]) - parseInt(date1Arr[2]);
    		if(day >= 0){
    			Months = 1+ year * 12 + month;
    		}else{
    			Months = year * 12 + month;
    		}
    		if(Year <= Months / 12){
    			resultMoney1 = Months * deduction;
    		}else{
    			resultMoney1 = deduction * (1 + rate * 0.01) * Months;
    		}
    	}else if(formValue.REDEMPTION_DATE == null || formValue.REDEMPTION_DATE == ""){//没有赎回日期时
    		var today = new Date();
    		var year = today.getFullYear() - parseInt(date1Arr[0]);
    		var month = (today.getMonth() + 1) - parseInt(date1Arr[1]);
    		var day = today.getDay() - parseInt(date1Arr[2]);
    		if(day >= 0){
    			Months = 1+ year * 12 + month;
    		}else{
    			Months = year * 12 + month;
    		}
    		if(Year <= Months / 12){
    			resultMoney1 = Months * deduction;
    		}else{
    			resultMoney1 = deduction * (1 + rate * 0.01) * Months;
    		}
    	}
    }
    
	resultMoney2 = Months;
	resultMoney3 = Number(formValue.PURCHASE_NUMBER) * Number(formValue.REDEMPTION_NUMBER);
	resultMoney4 = resultMoney3 - resultMoney1;
	resultMoney5 = resultMoney4 / resultMoney1 *100;
	
    
    outputForm.getForm().findField('INVEST_MONEY').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('DEDUCTION_TIMES').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('REDEMPTION_MONEY').setValue(resultMoney3.toFixed(4));
    outputForm.getForm().findField('PROFIT_AND_LOSS').setValue(resultMoney4.toFixed(4));
    outputForm.getForm().findField('PAY_RATE').setValue(resultMoney5.toFixed(4));
    
}