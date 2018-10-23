/**
 * @description 贷款计算器
 * @author Fan zheming
 * @since 2014-07-10
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

//本地数据字典定义
var localLookup = {
	//贷款种类组
	'LOAN_TYPE' : [
		{key : '1',value : '住房商业贷款'},
		{key : '2',value : '住房公积金贷款'},
		{key : '3',value : '个人消费贷款'}
	],
	//还款方式组
	'REPAY_TYPE' : [
   		{key : '1',value : '到期还本付息'},
   		{key : '2',value : '等额本息还款'},
   		{key : '3',value : '等额本金还款'}
   	],
	//偿还周期组
	'CYCLE_TYPE' : [
		{key : '1',value : '双周'},
		{key : '2',value : '月'},
		{key : '3',value : '季'},
		{key : '4',value : '半年'},
		{key : '5',value : '年'}
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
	html:'1.一次还本付息：借款人需在贷款到期日还清贷款本息，利随本清; <br>2.等额本息：借款人每期以相等的金额(分期还款额)偿还贷款，其中每期归还的金额包括每期应还利息、本金，按还款周期逐期归还，在贷款截止日期前全部还清本息; <br>3.等额本金：借款人每期须偿还等额本金，同时付清本期应付的贷款利息，而每期归还的本金等于贷款总额除以贷款期数。'
});


/**
 * 计算器输入项
 */
var inputForm = new Ext.FormPanel({
	title: '贷款计算器',
	height:350,
	collapsible:true,
	padding:'10px 0 0',
	labelWidth:120,
	items:[
	{xtype: 'combo',name: 'LOAN',fieldLabel: '贷款种类<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false,
		listeners:{
			select : function(combo,record){
				if(record.data.key == '1'){
					inputForm.getForm().findField('RATE').setValue(6.0);
				}else if(record.data.key == '2'){
					inputForm.getForm().findField('RATE').setValue(4.0);
				}else if(record.data.key == '3'){
					inputForm.getForm().findField('RATE').setValue(7.8);
				}
			}
		}
	
	},
	{xtype: 'datefield',name: 'START_DATE',fieldLabel: '起贷日期<font color=red>*</font>',width: 180,format: 'Y-m-d',editable: false,allowBlank:false},
	{xtype: 'datefield',name: 'REPAY_DATE',fieldLabel: '首次还贷日<font color=red>*</font>',width: 180,format: 'Y-m-d',editable: false,allowBlank:false},
	{xtype: 'numberfield',name: 'TERM',fieldLabel: '贷款期限(年)<font color=red>*</font>',width: 180,allowBlank:false,allowNegative:false,allowDecimals:false,maxValue:30,
		listeners:{
			blur : function(){
				if(inputForm.getForm().findField('LOAN').getValue() == 1){
					if(this.value <= 1){
						inputForm.getForm().findField('RATE').setValue(6);
					}else if(this.value > 1 && this.value <= 3){
						inputForm.getForm().findField('RATE').setValue(6.15);
					}else if(this.value > 3 && this.value <= 5){
						inputForm.getForm().findField('RATE').setValue(6.4);
					}else{
						inputForm.getForm().findField('RATE').setValue(6.55);
					}
				}else if(inputForm.getForm().findField('LOAN').getValue() == 2){
					if(this.value <= 5){
						inputForm.getForm().findField('RATE').setValue(4);
					}else{
						inputForm.getForm().findField('RATE').setValue(4.5);
					}
				}else if(inputForm.getForm().findField('LOAN').getValue() == 3){
					if(this.value <= 1){
						inputForm.getForm().findField('RATE').setValue(7.8);
					}else if(this.value > 1 && this.value <= 3){
						inputForm.getForm().findField('RATE').setValue(7.99);
					}else if(this.value > 3 && this.value <= 5){
						inputForm.getForm().findField('RATE').setValue(8.32);
					}else{
						inputForm.getForm().findField('RATE').setValue(8.51);
					}
				}
			}
		}
	},
	{xtype: 'numberfield',name: 'APPLY',fieldLabel: '贷款金额<font color=red>*</font>',width: 180,allowBlank:false,allowNegative:false},
	{xtype: 'combo',name: 'REPAY',fieldLabel: '还款方式<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,allowBlank:false,
		listeners:{
			select : function(combo,record){
				if(record.data.key == '1'){
					inputForm.getForm().findField('CYCLE').setVisible(false);
					certBaseInfoGrid.setVisible(!record.data.key == '1');
				}else{
					inputForm.getForm().findField('CYCLE').setVisible(true);
					certBaseInfoGrid.setVisible(true);
				}
			}
		}
	},
	{xtype: 'combo',name: 'CYCLE',fieldLabel: '偿还频率<font color=red>*</font>',width: 180,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false},
	{xtype: 'numberfield',name: 'RATE',fieldLabel: '年利率(%)<font color=red>*</font>',width: 180,allowBlank:false},
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
				repayStore.removeAll();
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
	height:190,
	collapsible:true,
	padding:'10px 0 0',
	labelWidth:120,
	items:[
		{xtype: 'textfield',name: 'RESULT_MONEY1',fieldLabel: '基本还款金额',width: 180,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY2',fieldLabel: '累计付息',width: 180,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY3',fieldLabel: '累计还款',width: 180,disabled:true},
		{xtype: 'textfield',name: 'RESULT_MONEY4',fieldLabel: '贷款总额',width: 180,disabled:true}
	]
});

//var sm = new Ext.grid.CheckboxSelectionModel();//复选框(选择模型)
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 38
});
var repay_cm = new Ext.grid.ColumnModel([//gridtable中的列定义
 rownum,
// {header : 'ID',dataIndex : 'id',width : 100,sortable : true,hidden : true},
 {header : '还贷日期',dataIndex : 'date',width : 120,sortable : true},
 {header : '各期还款额',dataIndex : 'eRTotal',width : 120,sortable : true, renderer : function(value){if(value != ''){return Ext.util.Format.number(value, '0,000.00');}else{return '0.00'}}},
 {header : '各期还本额',dataIndex : 'eRApply',id : "cretType",width : 150,sortable : true, renderer : function(value){if(value != ''){return Ext.util.Format.number(value, '0,000.00');}else{return '0.00'}}},
 {header : '各期还息额',dataIndex : 'eRIntes',width : 150,sortable : true, renderer : function(value){if(value != ''){return Ext.util.Format.number(value, '0,000.00');}else{return '0.00'}}},
 {header : '剩余本金',dataIndex : 'eBalance',width : 150,sortable : true, renderer : function(value){if(value != ''){return Ext.util.Format.number(value, '0,000.00');}else{return '0.00'}}},
 {header : '累计还息',dataIndex : 'tIntes',width : 150,sortable : true, renderer : function(value){if(value != ''){return Ext.util.Format.number(value, '0,000.00');}else{return '0.00'}}},
 {header : '累计还款',dataIndex : 'tTotal',width : 150 ,sortable : true, renderer : function(value){if(value != ''){return Ext.util.Format.number(value, '0,000.00');}else{return '0.00'}}}
 ]);

var repayRecord = new Ext.data.Record.create([
// {name : 'id'},
 {name : 'date'},	                                                 
 {name : 'eRTotal'},
 {name : 'eRApply'},
 {name : 'eRIntes'},
 {name : 'eBalance'},
 {name : 'tIntes'},
 {name : 'tTotal'}
 ]);

var repayReader = new Ext.data.JsonReader({//读取json数据的panel//rows.num来自于rpdata
    root:'rows',//计算方法中传输数据时要对应
    totalProperty: 'num'//计算方法中传输数据时要对应
},repayRecord);

var repayStore = new Ext.data.Store({
	proxy:new Ext.data.HttpProxy({
	}),
	reader:repayReader
});
	
var certBaseInfoGrid =  new Ext.grid.GridPanel({//主要证件信息列表数据grid
	name :'REPAY',
	title : '还款表',
	height: 350,
	frame : true,
	store : repayStore,
	loadMask : true,
	cm : repay_cm,
    loadMask : {
		msg : '正在加载表格数据,请稍等...'
	} 
});

//rpdata移出,全局变量
var rpdata ={
		num: 0,
		rows:[]
};

//结果域扩展功能面板
var customerView = [{
	/**
	 * 自定义计算器面板
	 */
	title:'计算器面板',
	hideTitle: true,
	layout: 'form',
	items: [inputForm,outputForm,certBaseInfoGrid,notesForm]//【新增改动】
}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * 注意：各组写在一个beforeviewshow下面
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view._defaultTitle == '计算器面板'){
		inputForm.getForm().findField('LOAN').bindStore(findLookupByType('LOAN_TYPE'));
		inputForm.getForm().findField('REPAY').bindStore(findLookupByType('REPAY_TYPE'));
		inputForm.getForm().findField('CYCLE').bindStore(findLookupByType('CYCLE_TYPE'));
	}
};

/**
 * 计算方法
 */
var calc = function(){
	//输入格式错误以及存在漏输入项时提示重新输入...
	if(!inputForm.getForm().isValid()){
		Ext.Msg.alert('提示','输入格式有误或存在漏输入项，请重新输入');
		return false;
	}
	
	/*
	 * 开始定义/引入变量
	 */
	//起贷日期(由用户输入)
	var startD=inputForm.getForm().findField('START_DATE').getValue();
	//还贷日(由用户输入)
	var repayD=inputForm.getForm().findField('REPAY_DATE').getValue();
	//贷款期限,折算成日(由用户输入的年数获得)
	var term=(inputForm.getForm().findField('TERM').getValue())*365;
	//贷款金额(由用户输入)
	var apply=inputForm.getForm().findField('APPLY').getValue();
	//还款方式(由用户输入)
	var repayType=inputForm.getForm().findField('REPAY').getValue();
	//偿还周期(由用户输入)
	var cycle=inputForm.getForm().findField('CYCLE').getValue();
	//日利率, 由年利率得到 (年利率由用户输入)
	var rate=(inputForm.getForm().findField('RATE').getValue())/36500;
	
	//基本还款金额, 结果需要值
	var baseRepay=0;
	//累计付息, 结果需要值
	var sumIntes=0;
	//累计还款, 结果需要值
	var sumRepay=0;
	
	//制表数据
	//各期还款额
	var eveRepayTotal=0;
	//各期还本额
	var eveRepayApply=0;
	//各期还息额
	var eveRepayIntes=0;
	//各期余额
	var eveBalance=0;
	//各还贷日
	var eveRepayD=repayD;
	//支付利息总额(累计还息)
	var totalIntes=0;
	//总还款额(累计还款)
	var totalTotal=0;

	/*
	 * 校验起贷日期和首次还贷日是否符合规范
	 */
	if(startD>repayD){
		Ext.Msg.alert('提示','起贷日期必须小于首次还贷日，请重新输入');
		return false;
	}
	
	/*
	 * 以下开始进入计算方法:
	 */
	if(repayType==1){//到期还本付息。注意: 到期一次还本付息采用单利
		//累计付息=借金*年利率*年份
		sumIntes=apply*((inputForm.getForm().findField('RATE').getValue())/100)*(inputForm.getForm().findField('TERM').getValue());
		//累计还款=累计付息+借金
		sumRepay=sumIntes+apply;
		//基本还款, 只还一次、即：累计还款额
		baseRepay=sumRepay;
		//注意:到期一次还本付息是不需要表格的, 【贷款总额】..就是total, 就是apply!
		//先将之前的rpdata的rows数据数组残留清空
		rpdata.rows.length=0;
		//即使是一次还本付息,也要显示于表格
		var obj={};
		obj.date=(new Date((repayD/1000+term*86400)*1000)).getFullYear()+"年"+((new Date((repayD/1000+term*86400)*1000)).getMonth()+1)+"月"+(new Date((repayD/1000+term*86400)*1000)).getDate()+"日";
		obj.eRTotal=baseRepay;
		obj.eRApply=apply;
		obj.eRIntes=sumIntes;
		obj.eBalance=0;
		obj.tIntes=sumIntes;
		obj.tTotal=sumRepay;
		rpdata.rows.push(obj);
		rpdata.num=1;
		repayStore.loadData(rpdata);
		
	}else{
		//开始进入switch-case对应5种case(还款周期)
		switch(cycle){
		case '1':if(repayType==2){
					//周期：双周, 类型：等额本息
					//首先,规范用户的输入还款日
					if((repayD-startD)>1209600000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始两周内，请重新输入');
						return false;
					}

					//总期数(利用：round(对象值,小数位))
					var duringTerm = Ext.util.Format.round(((term/14)+1),0);
					//双周利率
					var rateDBW=14*rate;
					
					//获得元素1：(1+利率)^(期数)
					var element1=pow1(rateDBW,duringTerm);//1.132(1年12%)
					
					//计算各期还款额【！】
					eveRepayTotal=apply*rateDBW*element1/(element1-1);
					
					/*
					 * 第1期,不参与循环计算
					 */
					//第1期还本额
					eveRepayApply=apply*(pow1(rateDBW,1)-1)/(element1-1);
					//第1期还息额
					eveRepayIntes=eveRepayTotal-eveRepayApply;
					//第1期余额
					eveBalance=apply*(element1-pow1(rateDBW,1))/(element1-1);
					//第1期支付利息总额(已累计还息)
					totalIntes=apply*(((1*rateDBW*element1)/(element1-1))-((pow1(rateDBW,1)-1)/(element1-1)));
					//第1期总还款额(已累计还款)
					totalTotal=apply*1*rateDBW*((element1)/(element1-1));
					//第1个还贷日repayD--待输出

					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					//把制表变量推入表格
					var obj={};
					obj.date=repayD.getFullYear()+"年"+(repayD.getMonth()+1)+"月"+repayD.getDate()+"日";
					obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
					obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
					obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
					obj.eBalance=Ext.util.Format.round(eveBalance,2);
					obj.tIntes=Ext.util.Format.round(totalIntes,2);
					obj.tTotal=Ext.util.Format.round(totalTotal,2);
					rpdata.rows.push(obj);
					
					/*
					 * 第2期开始,用循环计算
					 */
					for(var i=1;i<duringTerm;i++){
						//至K期还本额
						eveRepayApply=apply*(pow1(rateDBW,i+1)-pow1(rateDBW,i))/(element1-1);
						//至K期还息额
						eveRepayIntes=eveRepayTotal-eveRepayApply;
						//至K期余额
						eveBalance=apply*(element1-pow1(rateDBW,i+1))/(element1-1);
						//至K期支付利息总额(已累计还息)-循环结束此为总累计值
						totalIntes=apply*((((i+1)*rateDBW*element1)/(element1-1))-((pow1(rateDBW,i+1)-1)/(element1-1)));
						//至K期总还款额(已累计还款)-循环结束此为总累计值
						totalTotal=apply*(i+1)*rateDBW*((element1)/(element1-1));
						//第K个还贷日
						eveRepayD=new Date((repayD/1000+i*14*86400)*1000);

						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额
					baseRepay=eveRepayTotal;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}else{
					//周期：双周, 类型：等额本【金】
					//首先,规范用户的输入还款日
					if((repayD-startD)>1209600000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始两周内，请重新输入');
						return false;
					}

					//总期数(利用：round(对象值,小数位))
					var duringTerm = Ext.util.Format.round(((term/14)+1),0);
					//双周利率
					var rateDBW=14*rate;
					/*
					 * 用循环逐期计算
					 */
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					for(var i=0;i<duringTerm;i++){
						//至K期还款额
						eveRepayTotal=apply/duringTerm+(1-((i+1)-1)/duringTerm)*apply*rateDBW;
						//至K期还本额
						eveRepayApply=apply/duringTerm;
						//至K期还息额
						eveRepayIntes=(1-((i+1)-1)/duringTerm)*apply*rateDBW;
						//至K期余额
						eveBalance=(1-(i+1)/duringTerm)*apply;
						//至K期支付利息总额(已累计还息)
						totalIntes=((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateDBW;
						//至K期总还款额(已累计还款)
						totalTotal=(i+1)*(apply/duringTerm)+((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateDBW;
						//至K个还贷日
						eveRepayD=new Date((repayD/1000+i*14*86400)*1000);
												
						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额(本金)
					baseRepay=eveRepayApply;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					//【贷款总额】..就是total, 就是apply!
					//【还款表指标元素已推入各元素数组】
				};
				break;
		case '2':if(repayType==2){
					//周期：月, 类型：等额本息
					//首先,规范用户的输入还款日
					if((repayD-startD)>2592000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始一个月内，请重新输入');
						return false;
					}

					//总期数
					var duringTerm = (inputForm.getForm().findField('TERM').getValue())*12;
					//月利率
					var rateMON=((inputForm.getForm().findField('RATE').getValue())/1200);
					
					//获得元素1：(1+利率)^(期数)
					var element1=pow1(rateMON,duringTerm);
					
					//计算各期还款额【！】
					eveRepayTotal=apply*rateMON*element1/(element1-1);
					
					/*
					 * 第1期,不参与循环计算
					 */
					//第1期还本额
					eveRepayApply=apply*(pow1(rateMON,1)-1)/(element1-1);
					//第1期还息额
					eveRepayIntes=eveRepayTotal-eveRepayApply;
					//第1期余额
					eveBalance=apply*(element1-pow1(rateMON,1))/(element1-1);
					//第1期支付利息总额(已累计还息)
					totalIntes=apply*(((1*rateMON*element1)/(element1-1))-((pow1(rateMON,1)-1)/(element1-1)));
					//第1期总还款额(已累计还款)
					totalTotal=apply*1*rateMON*((element1)/(element1-1));
					//第1个还贷日repayD--待输出【！】
					
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					//把制表变量推入表格
					var obj={};
					obj.date=repayD.getFullYear()+"年"+(repayD.getMonth()+1)+"月"+repayD.getDate()+"日";
					obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
					obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
					obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
					obj.eBalance=Ext.util.Format.round(eveBalance,2);
					obj.tIntes=Ext.util.Format.round(totalIntes,2);
					obj.tTotal=Ext.util.Format.round(totalTotal,2);
					rpdata.rows.push(obj);
					
					/*
					 * 第2期开始,用循环计算
					 */
					for(var i=1;i<duringTerm;i++){
						//至K期还本额
						eveRepayApply=apply*(pow1(rateMON,i+1)-pow1(rateMON,i))/(element1-1);
						//至K期还息额
						eveRepayIntes=eveRepayTotal-eveRepayApply;
						//至K期余额
						eveBalance=apply*(element1-pow1(rateMON,i+1))/(element1-1);
						//至K期支付利息总额(已累计还息)-循环结束此为总累计值
						totalIntes=apply*((((i+1)*rateMON*element1)/(element1-1))-((pow1(rateMON,i+1)-1)/(element1-1)));
						//至K期总还款额(已累计还款)-循环结束此为总累计值
						totalTotal=apply*(i+1)*rateMON*((element1)/(element1-1));
						//第K个还贷日
						eveRepayD=new Date((repayD/1000+i*30*86400)*1000);
						
						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额
					baseRepay=eveRepayTotal;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}else{
					//周期：月, 类型：等额本【金】
					//首先,规范用户的输入还款日
					if((repayD-startD)>2592000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始一个月内，请重新输入');
						return false;
					}

					//总期数
					var duringTerm = (inputForm.getForm().findField('TERM').getValue())*12;
					//月利率
					var rateMON=((inputForm.getForm().findField('RATE').getValue())/1200);

					/*
					 * 用循环逐期计算
					 */
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					for(var i=0;i<duringTerm;i++){
						//至K期还款额
						eveRepayTotal=apply/duringTerm+(1-((i+1)-1)/duringTerm)*apply*rateMON;
						//至K期还本额
						eveRepayApply=apply/duringTerm;
						//至K期还息额
						eveRepayIntes=(1-((i+1)-1)/duringTerm)*apply*rateMON;
						//至K期余额
						eveBalance=(1-(i+1)/duringTerm)*apply;
						//至K期支付利息总额(已累计还息)
						totalIntes=((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateMON;
						//至K期总还款额(已累计还款)
						totalTotal=(i+1)*(apply/duringTerm)+((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateMON;
						//至K个还贷日
						eveRepayD=new Date((repayD/1000+i*30*86400)*1000);

						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额(本金)
					baseRepay=eveRepayApply;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}
		break;
		case '3':if(repayType==2){
					//周期：季, 类型：等额本息
					//首先,规范用户的输入还款日
					if((repayD-startD)>7776000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始一个季度内，请重新输入');
						return false;
					}

					//总期数
					var duringTerm = (inputForm.getForm().findField('TERM').getValue())*4;
					//季利率
					var rateSEA = ((inputForm.getForm().findField('RATE').getValue())/400);
					
					//获得元素1：(1+利率)^(期数)
					var element1=pow1(rateSEA,duringTerm);
					
					//计算各期还款额【！】
					eveRepayTotal=apply*rateSEA*element1/(element1-1);
					
					/*
					 * 第1期,不参与循环计算
					 */
					//第1期还本额
					eveRepayApply=apply*(pow1(rateSEA,1)-1)/(element1-1);
					//第1期还息额
					eveRepayIntes=eveRepayTotal-eveRepayApply;
					//第1期余额
					eveBalance=apply*(element1-pow1(rateSEA,1))/(element1-1);
					//第1期支付利息总额(已累计还息)
					totalIntes=apply*(((1*rateSEA*element1)/(element1-1))-((pow1(rateSEA,1)-1)/(element1-1)));
					//第1期总还款额(已累计还款)
					totalTotal=apply*1*rateSEA*((element1)/(element1-1));
					//第1个还贷日repayD--待输出

					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					//把制表变量推入表格
					var obj={};
					obj.date=repayD.getFullYear()+"年"+(repayD.getMonth()+1)+"月"+repayD.getDate()+"日";
					obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
					obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
					obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
					obj.eBalance=Ext.util.Format.round(eveBalance,2);
					obj.tIntes=Ext.util.Format.round(totalIntes,2);
					obj.tTotal=Ext.util.Format.round(totalTotal,2);
					rpdata.rows.push(obj);
					
					/*
					 * 第2期开始,用循环计算
					 */
					for(var i=1;i<duringTerm;i++){
						//至K期还本额
						eveRepayApply=apply*(pow1(rateSEA,i+1)-pow1(rateSEA,i))/(element1-1);
						//至K期还息额
						eveRepayIntes=eveRepayTotal-eveRepayApply;
						//至K期余额
						eveBalance=apply*(element1-pow1(rateSEA,i+1))/(element1-1);
						//至K期支付利息总额(已累计还息)-循环结束此为总累计值
						totalIntes=apply*((((i+1)*rateSEA*element1)/(element1-1))-((pow1(rateSEA,i+1)-1)/(element1-1)));
						//至K期总还款额(已累计还款)-循环结束此为总累计值
						totalTotal=apply*(i+1)*rateSEA*((element1)/(element1-1));
						//第K个还贷日
						eveRepayD=new Date((repayD/1000+i*90*86400)*1000);

						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额
					baseRepay=eveRepayTotal;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}else{
					//周期：季, 类型：等额本【金】
					//首先,规范用户的输入还款日
					if((repayD-startD)>7776000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始一个季度内，请重新输入');
						return false;
					}

					//总期数
					var duringTerm = (inputForm.getForm().findField('TERM').getValue())*4;
					//季利率
					var rateSEA = ((inputForm.getForm().findField('RATE').getValue())/400);

					/*
					 * 用循环逐期计算
					 */
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					for(var i=0;i<duringTerm;i++){
						//至K期还款额
						eveRepayTotal=apply/duringTerm+(1-((i+1)-1)/duringTerm)*apply*rateSEA;
						//至K期还本额
						eveRepayApply=apply/duringTerm;
						//至K期还息额
						eveRepayIntes=(1-((i+1)-1)/duringTerm)*apply*rateSEA;
						//至K期余额
						eveBalance=(1-(i+1)/duringTerm)*apply;
						//至K期支付利息总额(已累计还息)
						totalIntes=((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateSEA;
						//至K期总还款额(已累计还款)
						totalTotal=(i+1)*(apply/duringTerm)+((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateSEA;
						//至K个还贷日
						eveRepayD=new Date((repayD/1000+i*90*86400)*1000);
						
						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额(本金)
					baseRepay=eveRepayApply;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}
		break;
		case '4':if(repayType==2){
					//周期：半年, 类型：等额本息
					//首先,规范用户的输入还款日
					if((repayD-startD)>15552000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始半年内，请重新输入');
						return false;
					}

					//总期数
					var duringTerm = (inputForm.getForm().findField('TERM').getValue())*2;
					//半年利率
					var rateHFY = ((inputForm.getForm().findField('RATE').getValue())/200);
					
					//获得元素1：(1+利率)^(期数)
					var element1=pow1(rateHFY,duringTerm);
					
					//计算各期还款额【！】
					eveRepayTotal=apply*rateHFY*element1/(element1-1);
					
					/*
					 * 第1期,不参与循环计算
					 */
					//第1期还本额
					eveRepayApply=apply*(pow1(rateHFY,1)-1)/(element1-1);
					//第1期还息额
					eveRepayIntes=eveRepayTotal-eveRepayApply;
					//第1期余额
					eveBalance=apply*(element1-pow1(rateHFY,1))/(element1-1);
					//第1期支付利息总额(已累计还息)
					totalIntes=apply*(((1*rateHFY*element1)/(element1-1))-((pow1(rateHFY,1)-1)/(element1-1)));
					//第1期总还款额(已累计还款)
					totalTotal=apply*1*rateHFY*((element1)/(element1-1));
					//第1个还贷日repayD--待输出
					
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					//把制表变量推入表格
					var obj={};
					obj.date=repayD.getFullYear()+"年"+(repayD.getMonth()+1)+"月"+repayD.getDate()+"日";
					obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
					obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
					obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
					obj.eBalance=Ext.util.Format.round(eveBalance,2);
					obj.tIntes=Ext.util.Format.round(totalIntes,2);
					obj.tTotal=Ext.util.Format.round(totalTotal,2);
					rpdata.rows.push(obj);
					
					/*
					 * 第2期开始,用循环计算
					 */
					for(var i=1;i<duringTerm;i++){
						//至K期还本额
						eveRepayApply=apply*(pow1(rateHFY,i+1)-pow1(rateHFY,i))/(element1-1);
						//至K期还息额
						eveRepayIntes=eveRepayTotal-eveRepayApply;
						//至K期余额
						eveBalance=apply*(element1-pow1(rateHFY,i+1))/(element1-1);
						//至K期支付利息总额(已累计还息)-循环结束此为总累计值
						totalIntes=apply*((((i+1)*rateHFY*element1)/(element1-1))-((pow1(rateHFY,i+1)-1)/(element1-1)));
						//至K期总还款额(已累计还款)-循环结束此为总累计值
						totalTotal=apply*(i+1)*rateHFY*((element1)/(element1-1));
						//第K个还贷日
						eveRepayD=new Date((repayD/1000+i*180*86400)*1000);

						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额
					baseRepay=eveRepayTotal;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}else{
					//周期：半年, 类型：等额本【金】
					//首先,规范用户的输入还款日
					if((repayD-startD)>15552000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始半年内，请重新输入');
						return false;
					}
					
					//总期数
					var duringTerm = (inputForm.getForm().findField('TERM').getValue())*2;
					//半年利率
					var rateHFY = ((inputForm.getForm().findField('RATE').getValue())/200);

					/*
					 * 用循环逐期计算
					 */
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					for(var i=0;i<duringTerm;i++){
						//至K期还款额
						eveRepayTotal=apply/duringTerm+(1-((i+1)-1)/duringTerm)*apply*rateHFY;
						//至K期还本额
						eveRepayApply=apply/duringTerm;
						//至K期还息额
						eveRepayIntes=(1-((i+1)-1)/duringTerm)*apply*rateHFY;
						//至K期余额
						eveBalance=(1-(i+1)/duringTerm)*apply;
						//至K期支付利息总额(已累计还息)
						totalIntes=((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateHFY;
						//至K期总还款额(已累计还款)
						totalTotal=(i+1)*(apply/duringTerm)+((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateHFY;
						//至K个还贷日
						eveRepayD=new Date((repayD/1000+i*180*86400)*1000);
						
						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额(本金)
					baseRepay=eveRepayApply;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}
		break;
		case '5':if(repayType==2){
					//周期：年, 类型：等额本息
					//首先,规范用户的输入还款日
					if((repayD-startD)>31536000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始一年内，请重新输入');
						return false;
					}

					//总期数
					var duringTerm = inputForm.getForm().findField('TERM').getValue();
					//年利率
					var rateNEN = (inputForm.getForm().findField('RATE').getValue())/100;
					
					//获得元素1：(1+利率)^(期数)
					var element1=pow1(rateNEN,duringTerm);
					
					//计算各期还款额【！】
					eveRepayTotal=apply*rateNEN*element1/(element1-1);
					
					/*
					 * 第1期,不参与循环计算
					 */
					//第1期还本额
					eveRepayApply=apply*(pow1(rateNEN,1)-1)/(element1-1);
					//第1期还息额
					eveRepayIntes=eveRepayTotal-eveRepayApply;
					//第1期余额
					eveBalance=apply*(element1-pow1(rateNEN,1))/(element1-1);
					//第1期支付利息总额(已累计还息)
					totalIntes=apply*(((1*rateNEN*element1)/(element1-1))-((pow1(rateNEN,1)-1)/(element1-1)));
					//第1期总还款额(已累计还款)
					totalTotal=apply*1*rateNEN*((element1)/(element1-1));
					//第1个还贷日repayD--待输出

					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					//把制表变量推入表格
					var obj={};
					obj.date=repayD.getFullYear()+"年"+(repayD.getMonth()+1)+"月"+repayD.getDate()+"日";
					obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
					obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
					obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
					obj.eBalance=Ext.util.Format.round(eveBalance,2);
					obj.tIntes=Ext.util.Format.round(totalIntes,2);
					obj.tTotal=Ext.util.Format.round(totalTotal,2);
					rpdata.rows.push(obj);
					
					/*
					 * 第2期开始,用循环计算
					 */
					for(var i=1;i<duringTerm;i++){
						//至K期还本额
						eveRepayApply=apply*(pow1(rateNEN,i+1)-pow1(rateNEN,i))/(element1-1);
						//至K期还息额
						eveRepayIntes=eveRepayTotal-eveRepayApply;
						//至K期余额
						eveBalance=apply*(element1-pow1(rateNEN,i+1))/(element1-1);
						//至K期支付利息总额(已累计还息)-循环结束此为总累计值
						totalIntes=apply*((((i+1)*rateNEN*element1)/(element1-1))-((pow1(rateNEN,i+1)-1)/(element1-1)));
						//至K期总还款额(已累计还款)-循环结束此为总累计值
						totalTotal=apply*(i+1)*rateNEN*((element1)/(element1-1));
						//第K个还贷日
						eveRepayD=new Date((repayD/1000+i*365*86400)*1000);

						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						rpdata.rows.push(obj);
						
					}
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额
					baseRepay=eveRepayTotal;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
					
				}else{
					//周期：年, 类型：等额本【金】
					//首先,规范用户的输入还款日
					if((repayD-startD)>31536000000){
						Ext.Msg.alert('提示','还贷日请设在放款日开始一年内，请重新输入');
						return false;
					}
					//各期还款额
					var eveRepayTotal=0;
					//各期还本额
					var eveRepayApply=0;
					//各期还息额
					var eveRepayIntes=0;
					//各期余额
					var eveBalance=0;
					//各还贷日
					var eveRepayD=repayD;
					//支付利息总额(累计还息)
					var totalIntes=0;
					//总还款额(累计还款)
					var totalTotal=0;
					//总期数
					var duringTerm = inputForm.getForm().findField('TERM').getValue();
					//年利率
					var rateNEN = (inputForm.getForm().findField('RATE').getValue())/100;

					
					/*
					 * 用循环逐期计算
					 */
					//先将之前的rpdata的rows数据数组残留清空
					rpdata.rows.length=0;
					for(var i=0;i<duringTerm;i++){
						//至K期还款额
						eveRepayTotal=apply/duringTerm+(1-((i+1)-1)/duringTerm)*apply*rateNEN;
						//至K期还本额
						eveRepayApply=apply/duringTerm;
						//至K期还息额
						eveRepayIntes=(1-((i+1)-1)/duringTerm)*apply*rateNEN;
						//至K期余额
						eveBalance=(1-(i+1)/duringTerm)*apply;
						//至K期支付利息总额(已累计还息)
						totalIntes=((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateNEN;
						//至K期总还款额(已累计还款)
						totalTotal=(i+1)*(apply/duringTerm)+((i+1)-((i+1)-1)*(i+1)/(2*duringTerm))*apply*rateNEN;
						//至K个还贷日
						eveRepayD=new Date((repayD/1000+i *365*86400)*1000);
						
						//把制表变量推入表格
						var obj={};
						obj.date=eveRepayD.getFullYear()+"年"+(eveRepayD.getMonth()+1)+"月"+eveRepayD.getDate()+"日";
						obj.eRTotal=Ext.util.Format.round(eveRepayTotal,2);
						obj.eRApply=Ext.util.Format.round(eveRepayApply,2);
						obj.eRIntes=Ext.util.Format.round(eveRepayIntes,2);
						obj.eBalance=Ext.util.Format.round(eveBalance,2);
						obj.tIntes=Ext.util.Format.round(totalIntes,2);
						obj.tTotal=Ext.util.Format.round(totalTotal,2);
						
						rpdata.rows.push(obj);

					}
					
					//装载rpdata
					rpdata.num = rpdata.rows.length;
					//repayStore.loadData();
					repayStore.loadData(rpdata);
					/*
					 * 最后,赋值给baseRepay/sumIntes/sumRepay
					 */
					//基本还款额(本金)
					baseRepay=eveRepayApply;
					//累计付息
					sumIntes=totalIntes;
					//累计还款
					sumRepay=totalTotal;
//					pageDVD();
				}
		break;
		default:
			;
		}
	}
	
	//结果小计
	var resultMoney1 = baseRepay;
	var resultMoney2 = sumIntes;
	var resultMoney3 = sumRepay;
	var resultMoney4 = apply;
	//结果反馈(保留4位小数)
	outputForm.getForm().findField('RESULT_MONEY1').setValue(resultMoney1.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY2').setValue(resultMoney2.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY3').setValue(resultMoney3.toFixed(4));
	outputForm.getForm().findField('RESULT_MONEY4').setValue(resultMoney4.toFixed(4));
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