/**
*@description 存款账号信息（对公）
*/ 
	imports( [
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);
Ext.ns('Mis.Ext');
Mis.Ext.FormatCnMoney = function(v) {
	return Ext.util.Format.number(v,'0,000.00');
};	


var _custId;
var accountNo;
var lookupTypes=[
		'XD000226',//币种代码
		'XD000189',//基本账户状态
		'DM0014',//交易类型
		'DM0013'//钞汇标志
];

var createView=false;
var editView=false;
var needCondition=false;
var url=basepath+'/accountQuery.json?CustId='+_custId;

var fields=[
            
            {name:'ACCT_NAME',text:'账户名称',resutlWidth:80}, 
            {name:'ORG_NAME',text:'开户网点',resutlWidth:120},
            {name:'CUR_TYPE',text:'币种',translateType:'XD000226',resutlWidth:60},
            {name:'AMOUNT_ORG_MONEY',text:'余额',viewFn:money('0,000.00')},
            {name:'DEPOSITE_AVG_Y',text:'年日均',xtype:'textfield',viewFn:money('0,000.00'),resutlFloat:'right',resutlWidth:80},
			 {name:'DEPOSITE_AVG_Q',text:'季日均',xtype:'textfield',resutlFloat:'right',gridField:false,resutlWidth:80},
		 	 {name:'DEPOSITE_AVG_M',text:'月日均',xtype:'textfield',resutlFloat:'right',gridField:false,resutlWidth:80},
			{name:'SUBJECTS',text:'科目',gridField:false,resutlWidth:120},	
			{name:'ACCOUNT_STAT',text:'账户状态',gridField:false,translateType:'XD000189',resutlWidth:120},
			{name:'LOGOUT_ACCOUNT_DATE',text:'销户日期',gridField:false,resutlWidth:120},
			{name:'START_INTER_DATE',text:'起息日',gridField:false,resutlWidth:120},
			{name:'MATURE_DATE',text:'到期日',gridField:false,resutlWidth:120},
			{name:'RATE',text:'利率(%)',xtype:'textfield',gridField:false,resutlWidth:60},
			{name:'TRANS_TIMES',text:'转存次数',xtype:'textfield',gridField:false,resutlWidth:60},	  
            {name:'ACCT_NO',text:'账号',xtype:'textfield',gridField:false,resutlWidth:60},
            {name:'OPEN_ACCOUNT_DATE',text:'开户日期',resutlFloat:'right'}
            ];

            
var customerView = [{
	/**
	 * 存款账号详情
	 */
	title:'详情',
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : ['ACCT_NO','OPEN_ACCOUNT_DATE','ACCT_NAME','ORG_NAME','CUR_TYPE','AMOUNT_ORG_MONEY','DEPOSITE_AVG_Q','DEPOSITE_AVG_M','DEPOSITE_AVG_Y',
					'SUBJECTS','ACCOUNT_STAT','LOGOUT_ACCOUNT_DATE','START_INTER_DATE','MATURE_DATE','RATE','TRANS_TIMES'
			],
		/**
		 *存款账号信息
		 */
		fn : function(ACCT_NO,OPEN_ACCOUNT_DATE,ACCT_NAME,ORG_NAME,CUR_TYPE,AMOUNT_ORG_MONEY,DEPOSITE_AVG_Q,DEPOSITE_AVG_M,DEPOSITE_AVG_Y,SUBJECTS,
						ACCOUNT_STAT,LOGOUT_ACCOUNT_DATE,START_INTER_DATE,MATURE_DATE,RATE,TRANS_TIMES
					 ){
			ACCT_NO.readOnly= true;
			ACCT_NO.cls='x-readOnly';		 	
			OPEN_ACCOUNT_DATE.readOnly= true;
			OPEN_ACCOUNT_DATE.cls='x-readOnly';
			ACCT_NAME.readOnly= true;
			ACCT_NAME.cls='x-readOnly';
			ORG_NAME.readOnly= true;
			ORG_NAME.cls='x-readOnly';
			CUR_TYPE.readOnly= true;
			CUR_TYPE.cls='x-readOnly';
			AMOUNT_ORG_MONEY.readOnly= true;
			AMOUNT_ORG_MONEY.cls='x-readOnly';
			DEPOSITE_AVG_Y.readOnly= true;
			DEPOSITE_AVG_Y.cls='x-readOnly';
			DEPOSITE_AVG_Q.readOnly= true;
			DEPOSITE_AVG_Q.cls='x-readOnly';
			DEPOSITE_AVG_M.readOnly= true;
			DEPOSITE_AVG_M.cls='x-readOnly';
			SUBJECTS.readOnly= true;
			SUBJECTS.cls='x-readOnly';
			ACCOUNT_STAT.readOnly= true;
			ACCOUNT_STAT.cls='x-readOnly';
			START_INTER_DATE.readOnly= true;
			START_INTER_DATE.cls='x-readOnly';
			MATURE_DATE.readOnly= true;
			MATURE_DATE.cls='x-readOnly';
			RATE.readOnly= true;
			RATE.cls='x-readOnly';
			TRANS_TIMES.readOnly= true;
			TRANS_TIMES.cls='x-readOnly';
			
			return [ACCT_NO,OPEN_ACCOUNT_DATE,ACCT_NAME,ORG_NAME,CUR_TYPE,AMOUNT_ORG_MONEY,DEPOSITE_AVG_Q,DEPOSITE_AVG_Y,DEPOSITE_AVG_M,
					SUBJECTS,ACCOUNT_STAT,START_INTER_DATE,MATURE_DATE,RATE,TRANS_TIMES];
					  }
		}]


},{
	title:'交易流水',
	type:'grid',
	url : basepath + '/evtsavetradtansQuery.json',
	fields : {
		fields:[
			{name: 'TANS_NO', text : '流水号'},
			{name: 'ORG_NO', text : '机构编号'},  
			{name: 'ADVS_ACCT_NAME', text : '对手账号名称'},  
			{name: 'LOAN_FLAG', text : '借贷标志'},  
			{name: 'CURR_TRAN_FLAG', text : '现转标志'},  
			{name: 'TRAD_CHN', text : '交易渠道'}, 
			{name: 'CURR', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
				}}, 
			{name: 'TRAD_MONEY', text : '交易金额',renderer:money('0,0.00')}, 
			{name: 'ACCT_BAL', text : '账户余额',xtype:'textfield',renderer:money('0,0.00')}, 
			{name: 'TRAD_DT', text : '交易日期'}, 
			{name: 'TRAD_TIME', text : '交易时间'}, 
			{name: 'TRAD_TELLER', text : '交易柜员'}, 
			{name: 'TRAD_ABS', text : '交易摘要'}, 
			{name: 'ETL_DATE', text : '数据日期'},
			{name: 'HANDLER', text : '经办人'},
			{name: 'COST', text : '费用',xtype:'textfield',renderer:money('0,0.00')},
			{name: 'ACCOUNTIN_DATE', text : '记账日'},
			{name: 'TRAD_TYPE', text : '交易类型',renderer:function(value){
				var val = translateLookupByKey("DM0014",value);
				return val?val:"";
				}},
			{name: 'CONTACT_TYPE', text : '往来类型'},
			{name: 'CASH_FLAG', text : '钞汇标志',renderer:function(value){
				var val = translateLookupByKey("DM0013",value);
				return val?val:"";
				}}
		]
	}
},{
	title:'实时余额',
	type:'grid',
	url:basepath + '/evtsavetradtansQuery.json',//
	fields : {
		fields:[
			{name: 'TANS_NO', text : '账户名称'},
			{name: 'ORG_NO', text : '机构编号'},  
			{name: 'CUST_ID', text : '客户编号'}, 
			{name: 'ADVS_ACCT_NAME', text : '对手账号名称'},  
			{name: 'LOAN_FLAG', text : '借贷标志'},  
			{name: 'CURR_TRAN_FLAG', text : '现转标志'},  
			{name: 'TRAD_CHN', text : '交易渠道'}, 
			{name: 'CURR', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
				}}, 
			{name: 'TRAD_MONEY', text : '交易金额',renderer:money('0,0.00')}, 
			{name: 'ACCT_BAL', text : '账户余额',renderer:money('0,0.00')}, 
			{name: 'TRAD_DT', text : '交易日期'}, 
			{name: 'TRAD_TIME', text : '交易时间'}, 
			{name: 'TRAD_TELLER', text : '交易柜员'}, 
			{name: 'TRAD_ABS', text : '交易摘要'}, 
			{name: 'ETL_DATE', text : '数据日期'},
			{name: 'HANDLER', text : '经办人'},
			{name: 'COST', text : '费用',renderer:money('0,0.00')},
			{name: 'ACCOUNTIN_DATE', text : '记账日'},
			{name: 'TRAD_TYPE', text : '交易类型',renderer:function(value){
				var val = translateLookupByKey("DM0014",value);
				return val?val:"";
				}},
			{name: 'CONTACT_TYPE', text : '往来类型'},
			{name: 'CASH_FLAG', text : '钞汇标志',renderer:function(value){
				var val = translateLookupByKey("DM0013",value);
				return val?val:"";
				}}
		]
	}
   }
];


var AmountNowForm = new Ext.form.FormPanel({
	height:80,
	labelWidth:100,//label的宽度
	labelAlign:'right',
	frame:true,
	autoScroll : true,
	region:'north',
	split:true,
	buttonAlign:'center',
	items:[{
		layout:'form',
		items:[{
			layout:'form',
			items:[
				{xtype:'textfield',name:'TEMP1',fieldLabel:'实时余额'}
			]
		}
		]
	}]
})

var firstlayout=-1;
beforeviewshow = function(theView){
	if(theView._defaultTitle == '详情'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		var tempdata1=getSelectedData().data.AMOUNT_ORG_MONEY;
		var tempdata2=getSelectedData().data.DEPOSITE_AVG_Q;
		var tempdata3=getSelectedData().data.DEPOSITE_AVG_Y;
		var tempdata4=getSelectedData().data.DEPOSITE_AVG_M;
		theView.contentPanel.getForm().loadRecord(getSelectedData());
		if (tempdata1 != '' && tempdata1 != null) {
			theView.contentPanel.getForm().findField('AMOUNT_ORG_MONEY').setValue(Ext.util.Format.number(tempdata1, '0,000.00'));
		}
		if (tempdata2 != '' && tempdata2 != null) {
			theView.contentPanel.getForm().findField('DEPOSITE_AVG_Q').setValue(Ext.util.Format.number(tempdata2, '0,000.00'));
		}
		if (tempdata3 != '' && tempdata3 != null) {
			theView.contentPanel.getForm().findField('DEPOSITE_AVG_Y').setValue(Ext.util.Format.number(tempdata3, '0,000.00'));
		}
		if (tempdata4!= '' && tempdata4 != null) {
			theView.contentPanel.getForm().findField('DEPOSITE_AVG_M').setValue(Ext.util.Format.number(tempdata4, '0,000.00'));
		}
	} 
	if(theView._defaultTitle == '交易流水'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		var k ;
		var ids=getAllSelects()[0].data.ACCT_NO;
		for(k=1;k<getAllSelects().length;k++){
			ids=ids+','+getAllSelects()[k].data.ACCT_NO;
		}
//		accountNo=getSelectedData().data.ACCT_NO;
		theView.setParameters({
			accountNo:ids
			});
		}
	if(theView._defaultTitle == '实时余额'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
			}
			AmountNowForm.getForm().findField('TEMP1').setValue('20.00');
			if(firstlayout<0){
				firstlayout++;
				theView.remove(theView.grid,false);
				theView.doLayout();
				theView.grid.setHeight(theView.grid.getHeight() - 90);
				theView.add({
					xtype:'panel',
					layout:'form',
					items:[AmountNowForm,theView.grid]
					});
					theView.doLayout();
			}
			var k ;
			var ids=getAllSelects()[0].data.ACCT_NO;
			for(k=1;k<getAllSelects().length;k++){
				ids=ids+','+getAllSelects()[k].data.ACCT_NO;
			}
//			accountNo=getSelectedData().data.ACCT_NO;
		theView.setParameters({
			accountNo:ids//暂时设置查询条件为账号
			});
	}
	
}