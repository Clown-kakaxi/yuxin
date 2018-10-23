/**
 * @description 个人所得税计算器
 * @author likai
 * @since 2014-07-10
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动



//本地数据字典定义
var localLookup = {
	'INCOME_TYPE' : [
		{key : '1',value : '工资、薪金所得'},
		{key : '2',value : '全年一次性奖金'},  
		{key : '3',value : '个体工商户的生产、经营所得'},  
		{key : '4',value : '对企事业单位的承包经营、承租经营所得'},    
		{key : '5',value : '劳务报酬所得'},  
		{key : '6',value : '稿酬所得'},      
		{key : '7',value : '特许权使用费所得'},      
		{key : '8',value : '利息、股息、红利所得'},      
		{key : '9',value : '财产租赁所得'},      
		{key : '10',value : '财产转让所得'},    
		{key : '11',value : '偶然所得'}
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
	padding:'10px 20px 0px',
	html:'个人所得税是依据中华人民共和国税法最新税率表求得'
		+'<br><font color=red>*</font>为必填项'
});

/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '个人所得税计算器（仅供参考）',
	height:160,
	collapsible:true,
	labelWidth:120,
	items:[
	{xtype: 'combo',name: 'INCOME_TYPE',fieldLabel: '收入类型<font color=red>*</font>',width: 200,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false,
		listeners:{
			select : function(combo,record){
				if(record.data.key == '2'){
					inputForm.getForm().findField('SALARY').setVisible(true);
					inputForm.getForm().findField('SALARY').allowBlank = false;
				}else{
					inputForm.getForm().findField('SALARY').setVisible(false);
					inputForm.getForm().findField('SALARY').allowBlank = true;
				}
			}
		}
	},
	{xtype: 'numberfield',name: 'INCOME_MONEY',fieldLabel: '收入金额<font color=red>*</font>',viewFn:money('0,000.00'),minValue: 0,width: 200,allowBlank:false},
	{xtype: 'numberfield',name: 'SALARY',fieldLabel: '当月工资<font color=red>*</font>',viewFn:money('0,000.00'),minValue: 0,width: 200,allowBlank:true,hidden:true},
	{
	    columnWidth:1,
	    layout:'column',
	    padding:'5px 140px 0px',
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
	height:160,
	collapsible:true,
	padding:'10px 0px 0px',
	labelWidth:120,
	items:[
		{xtype: 'textfield',name: 'RESULT_MONEY1',fieldLabel: '免税金额(元)',viewFn:money('0,000.00'),width: 200,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY2',fieldLabel: '个人所得税(元)',viewFn:money('0,000.00'),width: 200,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY3',fieldLabel: '实际收入(元)',viewFn:money('0,000.00'),width: 200,disabled:true}
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
		inputForm.getForm().findField('INCOME_TYPE').bindStore(findLookupByType('INCOME_TYPE'));
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
	var formValue = inputForm.getForm().getFieldValues();
	var incomeType = Number(formValue.INCOME_TYPE);
	switch(incomeType){
		case 1:
			calcPersonTax(formValue);//工资、薪金所得
			break;
		case 2:
			calcOneTimeBonusTax(formValue);//全年一次性奖金
			break;
		case 3:
			calcPersonalBussinessTax(formValue);//个体工商户的生产、经营所得
			break;
		case 4:
			calcRunBussinessTax(formValue);//对企事业单位承包经营、承租经营所得
			break;
		case 5:
			calcWorkPaidTax(formValue);//劳务报酬所得
			break;
		case 6:
			calcBookPaidTax(formValue);//稿酬所得
			break;
		case 7:
			calcSpecialUsingMoneyTax(formValue);//特许权使用费所得
			break;
		case 8:
			calcStockProfitTax(formValue);//利息、股息、红利所得
			break;
		case 9:
			calcPropertyRentTax(formValue);//财产租赁所得
			break;
		case 10:
			calcPropertyTransferTax(formValue);//财产转让所得
			break;
		case 11:
			calcAccidentalIncomeTax(formValue);//偶然所得
			break;
		default:
			;
	}
};

/**
 * 1、工资薪资所得
 * @param formValue 
 * @returns
 */
var calcPersonTax = function(formValue){
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	resultMoney1 = 3500;
	//定义应纳税所得额、税率和速算扣除数的数组
	var personalTaxMoney = [80000,55000,35000,9000,4500,1500,0];
	var rate = [0.45,0.35,0.30,0.25,0.20,0.10,0.03];
	var num = [13505,5505,2755,1005,555,105,0];
    var moneyForTax = Number(formValue.INCOME_MONEY)-resultMoney1;
    for ( var i = 0; i < personalTaxMoney.length; i++) {
    	if(moneyForTax > personalTaxMoney[i]){
    		resultMoney2 = moneyForTax*rate[i]-num[i];
    		break;
    	}
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};
/**
 * 2、全年一次性奖金
 * @param formValue
 * @returns
 */
var calcOneTimeBonusTax = function(formValue){
	var salary = Number(formValue.SALARY);//当月工资薪资所得
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;

	//定义应纳税所得额、税率和速算扣除数的数组
	var bonusTaxMoney = [80000,55000,35000,9000,4500,1500,0];
	var rate = [0.45,0.35,0.30,0.25,0.20,0.10,0.03];
	var num = [13505,5505,2755,1005,555,105,0];
    if(salary > 3500){
    	resultMoney1 = 0;
    	for ( var i = 0; i < bonusTaxMoney.length; i++) {
			if((Number(formValue.INCOME_MONEY)/12)>bonusTaxMoney[i]){
				resultMoney2 = Number(formValue.INCOME_MONEY)*rate[i]-num[i];
				break;
			}
		}
    }else if(salary <= 3500){
    	resultMoney1 = 3500-salary;
    	for ( var i = 0; i < bonusTaxMoney.length; i++) {
			if(((Number(formValue.INCOME_MONEY)-resultMoney1)/12)>bonusTaxMoney[i]){
				resultMoney2 = (Number(formValue.INCOME_MONEY)-resultMoney1)*rate[i]-num[i];
				break;
			}
		}
    }
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};

/**
 * 3、个体工商户的生产、经营所得
 * @param formValue
 * @returns
 */
var calcPersonalBussinessTax = function(formValue){
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	//定义应纳税所得额、税率和速算扣除数的数组
	var personalBussinessMoney = [100000,60000,30000,15000,0];
	var rate = [0.35,0.30,0.20,0.10,0.05];
	var num = [14750,9750,3750,750,0];
    resultMoney1 = 0;
    for ( var i = 0; i < personalBussinessMoney.length; i++) {
    	if(Number(formValue.INCOME_MONEY) > personalBussinessMoney[i]){
    		resultMoney2 = Number(formValue.INCOME_MONEY)*rate[i]-num[i];
    		break;
    	}
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 4、对企事业单位的承包经营、承租经营所得
 * @param formValue
 * @returns
 */
var calcRunBussinessTax = function(formValue){
	outputForm.getForm().reset();
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	//定义应纳税所得额、税率和速算扣除数的数组
	var runBussinessMoney = [100000,60000,30000,15000,0];
	var rate = [0.35,0.30,0.20,0.10,0.05];
	var num = [14750,9750,3750,750,0];
    resultMoney1 = 0;
    for ( var i = 0; i < runBussinessMoney.length; i++) {
    	if(Number(formValue.INCOME_MONEY) > runBussinessMoney[i]){
    		resultMoney2 = Number(formValue.INCOME_MONEY)*rate[i]-num[i];
    		break;
    	}
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 5、劳务报酬所得
 * @param formValue
 * @returns
 */
var calcWorkPaidTax = function(formValue){
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	//定义应纳税所得额、税率和速算扣除数的数组
	var workPaidMoney = [50000,20000,0];
	var rate = [0.40,0.30,0.20];
	var num = [7000,2000,0];
	if(Number(formValue.INCOME_MONEY)<4000){
		resultMoney1=800;
		resultMoney2=(Number(formValue.INCOME_MONEY)-800)*0.2;
	}else if(Number(formValue.INCOME_MONEY)>=4000){
        resultMoney1 = Number(formValue.INCOME_MONEY)*0.20;
        for ( var i = 0; i < workPaidMoney.length; i++) {
    	    if(Number(formValue.INCOME_MONEY) > workPaidMoney[i]){
    		    resultMoney2 = Number(formValue.INCOME_MONEY)*0.80*rate[i]-num[i];
    		    break;
      	    }
	    }
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 6、稿酬所得
 * @param formValue
 * @returns
 */
var calcBookPaidTax = function(formValue){
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	if(Number(formValue.INCOME_MONEY)<4000){
		resultMoney1=800;
		resultMoney2=(Number(formValue.INCOME_MONEY)-800)*0.20*(1-0.30);
	}else if(Number(formValue.INCOME_MONEY)>=4000){
        resultMoney1 = Number(formValue.INCOME_MONEY)*0.20;
        resultMoney2 = Number(formValue.INCOME_MONEY)*(1-0.20)*0.20*(1-0.30);
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 7、特许权使用费所得
 * @param formValue
 * @returns
 */
var calcSpecialUsingMoneyTax = function(formValue){
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	if(Number(formValue.INCOME_MONEY)<4000){
		resultMoney1=800;
		resultMoney2=(Number(formValue.INCOME_MONEY)-800)*0.20;
	}else if(Number(formValue.INCOME_MONEY)>=4000){
        resultMoney1 = Number(formValue.INCOME_MONEY)*0.20;
        resultMoney2 = Number(formValue.INCOME_MONEY)*(1-0.20)*0.20;
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 8、利息、股息、红利所得
 * @param formValue
 * @returns
 */
var calcStockProfitTax = function(formValue){
	var resultMoney1,resultMoney2,resultMoney3;
	
	resultMoney1 = 0;
	resultMoney2 = Number(formValue.INCOME_MONEY)*0.20;
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 9、财产租赁所得
 * @param formValue
 * @returns
 */
var calcPropertyRentTax = function(formValue){
	var resultMoney1 = 0;
	var resultMoney2 = 0;
	var resultMoney3 = 0;
	
	if(Number(formValue.INCOME_MONEY)<4000){
		resultMoney1=800;
		resultMoney2=(Number(formValue.INCOME_MONEY)-800)*0.20;
	}else if(Number(formValue.INCOME_MONEY)>=4000){
        resultMoney1 = Number(formValue.INCOME_MONEY)*0.20;
        resultMoney2 = Number(formValue.INCOME_MONEY)*(1-0.20)*0.20;
	}
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};


/**
 * 10、财产转让所得
 * @param formValue
 * @returns
 */
var calcPropertyTransferTax = function(formValue){
	var resultMoney1,resultMoney2,resultMoney3;
	
	resultMoney1 = 0;
	resultMoney2 = Number(formValue.INCOME_MONEY)*0.20;
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};

/**
 * 11、偶然所得
 * @param formValue
 * @returns
 */
var calcAccidentalIncomeTax = function(formValue){
	var resultMoney1,resultMoney2,resultMoney3;
	
	resultMoney1 = 0;
	resultMoney2 = Number(formValue.INCOME_MONEY)*0.20;
    resultMoney3 = Number(formValue.INCOME_MONEY)-resultMoney2;

    outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
    outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
};
