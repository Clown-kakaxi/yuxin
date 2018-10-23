/**
*@description 个人贷款账号信息（对私）
*/ 
	imports( [
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);
	
var _custId;
var lookupTypes=[
		'XD000226',//币种代码
		'XD000189',//账户状态
		'DM0014',//交易类型
		'DM0013',//钞汇标志
		'DM0009'//五级分类
		
];
var needCondition=false;
var createView=false;
var editView=false;
var url=basepath+'/LoanAccount1Query.json?CustId='+_custId;

var fields=[
	    {name:'ACCOUNT',text:'账户',resutlWidth:80}, 
	    {name:'PROD_NAME',text:'账户名称',resutlWidth:100},
	    {name:'ORG_NAME',text:'开户网点名称',resutlWidth:60},
	    {name:'MONEY_TYPE',text:'币种',translateType:'XD000226',resutlWidth:60},
	    {name:'CURR_FORMERLY',text:'余额（原币种）',xtype:'numberfield',viewFn:money('0,000.00'),gridField:false,resutlWidth:100},
	    {name:'AMOUNT',text:'余额（折人民币）',xtype:'numberfield',viewFn:money('0,000.00'),resutlWidth:100},
	    {name:'SUBJECTS',text:'科目',resutlWidth:60,gridField:false},
	    {name:'CURRE_FIRM_INTEREST',text:'本年实收利息',xtype:'numberfield',resutlFloat:'right',resutlWidth:80,gridField:false,viewFn:money('0,000.00'),minValue: 0},
	    {name:'CURRE_MUST_INTEREST',text:'本年应收利息',xtype:'numberfield',resutlWidth:80,gridField:false,viewFn:money('0,000.00'),minValue: 0},
	    {name:'ACCOUNT_STAT',text:'账户状态',translateType:'XD000189',gridField:false,xtype:'textfield',resutlWidth:60},
	    {name:'LOAN_AVG_Y',text:'年日均',viewFn:money('0,000.00'),minValue: 0,xtype:'numberfield',resutlWidth:60},
	    {name:'FIVE_LEVEL_TYPE',text:'五级分类',xtype:'textfield',translateType:'DM0009',resutlWidth:60},
	    {name:'OPEN_ACCOUNT_DATE',text:'开户日期',dataType:'date',format:'y-m-d',resutlWidth:100,gridField:false},
	    {name:'LOGOUT_ACCOUNT_DATE',text:'销户日期',dataType:'date',format:'y-m-d',resutlWidth:60,gridField:false},
	    {name:'START_INTER_DATE',text:'起息日',dataType:'date',format:'y-m-d',resutlWidth:60,gridField:false},
	    {name:'MATURE_DATE',text:'到期日',dataType:'date',format:'y-m-d',resutlWidth:60,gridField:false},
	    {name:'LOAN_AVG_M',text:'月日均',xtype:'numberfield',resutlWidth:60,gridField:false},
	    {name:'LOAN_AVG_Q',text:'季日均',xtype:'numberfield',resutlWidth:60,gridField:false},
	    
	    {name:'RATE',text:'利率(%)',xtype:'numberfield',resutlWidth:60},
	    {name:'BEF_DEGREE_CONTRI',text:'贡献度（模拟利润）',xtype:'textfield',resutlWidth:60,gridField:false},
	    {name:'ETL_DATE',text:'ETL日期',dataType:'date',format:'y-m-d',resutlWidth:60,gridField:false}
    ];

            
var customerView = [{
	/**
	 * 贷款账号详情
	 */
	title:'详情',
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : ['ACCOUNT','ACCOUNT_NAME','ORG_NAME','MONEY_TYPE','CURR_FORMERLY','RATE','AMOUNT','SUBJECTS','CURRE_FIRM_INTEREST',
					'CURRE_MUST_INTEREST','ACCOUNT_STAT','FIVE_LEVEL_TYPE','OPEN_ACCOUNT_DATE','LOGOUT_ACCOUNT_DATE','START_INTER_DATE',
						'MATURE_DATE','LOAN_AVG_M','LOAN_AVG_Q','LOAN_AVG_Y','BEF_DEGREE_CONTRI'
			],
		/**
		 *存款账号信息
		 */
		fn : function(ACCOUNT,ACCOUNT_NAME,ORG_NAME,MONEY_TYPE,CURR_FORMERLY,RATE,AMOUNT,SUBJECTS,CURRE_FIRM_INTEREST,CURRE_MUST_INTEREST,
				ACCOUNT_STAT,FIVE_LEVEL_TYPE,OPEN_ACCOUNT_DATE,LOGOUT_ACCOUNT_DATE,START_INTER_DATE,MATURE_DATE,LOAN_AVG_M,LOAN_AVG_Q,LOAN_AVG_Y,
				BEF_DEGREE_CONTRI	){
				ACCOUNT.readOnly= true;
				ACCOUNT.cls='x-readOnly';
				ACCOUNT_NAME.readOnly= true;
				ACCOUNT_NAME.cls='x-readOnly';
				ORG_NAME.readOnly= true;
				ORG_NAME.cls='x-readOnly';
				MONEY_TYPE.readOnly= true;
				MONEY_TYPE.cls='x-readOnly';
				CURR_FORMERLY.readOnly= true;
				CURR_FORMERLY.cls='x-readOnly';
				RATE.readOnly= true;
				RATE.cls='x-readOnly';
				AMOUNT.readOnly= true;
				AMOUNT.cls='x-readOnly';
				SUBJECTS.readOnly= true;
				SUBJECTS.cls='x-readOnly';
				CURRE_FIRM_INTEREST.readOnly= true;
				CURRE_FIRM_INTEREST.cls='x-readOnly';
				CURRE_MUST_INTEREST.readOnly= true;
				CURRE_MUST_INTEREST.cls='x-readOnly';
				ACCOUNT_STAT.readOnly= true;
				ACCOUNT_STAT.cls='x-readOnly';
				FIVE_LEVEL_TYPE.readOnly= true;
				FIVE_LEVEL_TYPE.cls='x-readOnly';
				OPEN_ACCOUNT_DATE.readOnly= true;
				OPEN_ACCOUNT_DATE.cls='x-readOnly';
				LOGOUT_ACCOUNT_DATE.readOnly= true;
				LOGOUT_ACCOUNT_DATE.cls='x-readOnly';
				START_INTER_DATE.readOnly= true;
				START_INTER_DATE.cls='x-readOnly';
				MATURE_DATE.readOnly= true;
				MATURE_DATE.cls='x-readOnly';
				LOAN_AVG_M.readOnly= true;
				LOAN_AVG_M.cls='x-readOnly';
				LOAN_AVG_Q.readOnly= true;
				LOAN_AVG_Q.cls='x-readOnly';
				LOAN_AVG_Y.readOnly= true;
				LOAN_AVG_Y.cls='x-readOnly';
				BEF_DEGREE_CONTRI.readOnly= true;
				BEF_DEGREE_CONTRI.cls='x-readOnly';
				return [ACCOUNT,ACCOUNT_NAME,ORG_NAME,MONEY_TYPE,CURR_FORMERLY,RATE,AMOUNT,SUBJECTS,CURRE_FIRM_INTEREST,CURRE_MUST_INTEREST,
				ACCOUNT_STAT,FIVE_LEVEL_TYPE,OPEN_ACCOUNT_DATE,LOGOUT_ACCOUNT_DATE,START_INTER_DATE,MATURE_DATE,LOAN_AVG_M,LOAN_AVG_Q,LOAN_AVG_Y,
				BEF_DEGREE_CONTRI	];
					  }
		}]


},{  
	/*
	TANS_NO流水号
	ORG_NO机构编号
	CUST_ID客户编号
	ACCT账号
	ADVS_ACCT对手账号
	ADVS_ACCT_NAME对手账号名称
	LOAN_FLAG借贷标志
	CURR_TRAN_FLAG现转标志
	TRAD_CHN交易渠道
	CURR币种
	TRAD_MONEY交易金额
	ACCT_BAL账户余额
	TRAD_DT交易日期
	TRAD_TIME交易时间
	TRAD_TELLER交易柜员
	TRAD_ABS交易摘要
	ETL_DATE数据日期
	IDID
	HANDLER经办人
	COST费用
	ACCOUNTIN_DATE记账日
	REVIEW复核人
	TRAD_TYPE交易类型
	CONTACT_TYPE往来类型
	CASH_FLAG钞汇标志
    */	
	title:'个人历史交易流水',
	type:'grid',
	url : basepath + '/evtsavetradtansQuery.json',//evtsavetradtansQuery
	fields : {
		fields:[
			{name: 'CUST_ID', text : '客户编号'},
			{name: 'ACCT', text : '账号'},
			{name: 'CURR', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
			}}, 
			{name: 'TANS_NO', text : '流水号'},
			{name: 'TRAD_CHN', text : '交易渠道'},
			{name: 'TRAD_MONEY', text : '交易金额',renderer:money('0,0.00')},
			{name: 'COST', text : '费用',xtype:'textfield',renderer:money('0,0.00')},
			{name: 'ACCOUNTIN_DATE', text : '记账日'},
			{name: 'TRAD_TIME', text : '交易时间'},
			{name: 'HANDLER', text : '经办人'},
			{name: 'REVIEW', text : '复核人'},
			{name: 'TRAD_ABS', text : '交易摘要'},
			{name: 'TRAD_DT', text : '交易日期'},  
			{name: 'TRAD_TYPE', text : '交易类型',renderer:function(value){
				var val = translateLookupByKey("DM0014",value);
				return val?val:"";
				}},
			{name: 'CONTACT_TYPE', text : '往来类型'}
			
			
		]
	}
},

	{
	title:'实时交易流水',
	type:'grid',
	url : basepath + '/accountQuery!pertrans.json?CustId='+_custId,
	fields : {
		fields:[
				{name: 'custCd', text : '客户号'},
				{name: 'acno', text : '账号'},
				{name: 'ccyc', text : '币种',renderer:function(value){
					var val = translateLookupByKey("XD000226",value);
					return val?val:"";
				}}, 
				{name: 'vern', text : '流水号'},
				{name: 'func', text : '交易渠道'},
				{name: 'txam', text : '交易金额',renderer:function(value){
					if(value != null && value != ''){
						if(typeof value === 'object' && value.length>0){
							var tempVal = value[0];
							tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
							tempVal = tempVal/100;
							return Ext.util.Format.number(tempVal, '0,0.00');
						}else{
							var tempVal = value;
							tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
							tempVal = tempVal/100;
							return Ext.util.Format.number(tempVal, '0,0.00');
						}
					}else{
						return value;
					}
				}},
				{name: 'tam1', text : '手续费',xtype:'textfield',renderer:money('0,0.00')},
				{name: 'adat', text : '记账日'},
				{name: 'atim', text : '时间'},
				{name: 'iusr', text : '经办人'},
				{name: 'ausr', text : '复核人'},
				{name: 'scrm', text : '交易摘要'},
				{name: 'uldt', text : '交易日'},  
				{name: 'txty', text : '交易类型',renderer:function(value){
					var val = translateLookupByKey("DM0014",value);
					return val?val:"";
					}},
				{name: 'rmrk', text : '往来类型'},
				{name: 'batp', text : '钞汇标志',renderer:function(value){
						var val = translateLookupByKey("DM0013",value);
						return val?val:"";
				 }}
		]
	}
},{
	title:'实时余额', //贷款账号
	type:'grid',
	url:basepath + '/accountQuery!perloan.json?CustId='+_custId,
	fields : {
		fields:[
			{name: 'custCd', text : '核心系统客户号'},
			{name: 'accName', text : '账户名称'},  
			{name: 'accNo', text : '账号'}, 
			{name: 'accCcy', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
				}}, 
			{name: 'accAval', text : '余额'},
			{name: 'sysDate', text : '系统日期'}, 
			{name: 'sysTime', text : '系统时间'} 
		]
	}
   }
];

/*
var AmountNowForm = new Ext.form.FormPanel({
	height:80,
	labelWidth:100,//label的宽度
	labelAlign:'right',
	frame:true,
	autoScroll : true,
	region:'center',
	split:true,
	buttonAlign:'center',
	items:[{
		layout:'form',
		items:[{
			layout:'form',
			items:[
				{xtype:'textfield',name:'TEMP1',fieldLabel:'余额（实时）'}
			]
		}
		]
	}]
})
*/
var firstlayout= -1;
beforeviewshow = function(theView){
	if(theView._defaultTitle == '详情'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		theView.contentPanel.getForm().loadRecord(getSelectedData());
		var tempdata1=getSelectedData().data.CURR_FORMERLY;
		var tempdata2=getSelectedData().data.AMOUNT;
		var tempdata3=getSelectedData().data.CURRE_FIRM_INTEREST;
		var tempdata4=getSelectedData().data.CURRE_MUST_INTEREST;
		var tempdata5=getSelectedData().data.LOAN_AVG_M;
		var tempdata6=getSelectedData().data.LOAN_AVG_Q;
		var tempdata7=getSelectedData().data.LOAN_AVG_Y;
		theView.contentPanel.getForm().loadRecord(getSelectedData());
		if (tempdata1 != '' && tempdata1 != null) {
			theView.contentPanel.getForm().findField('CURR_FORMERLY').setValue(Ext.util.Format.number(tempdata1, '0,000.00'));
		}
		if (tempdata2 != '' && tempdata2 != null) {
			theView.contentPanel.getForm().findField('AMOUNT').setValue(Ext.util.Format.number(tempdata2, '0,000.00'));
		}
		if (tempdata3 != '' && tempdata3 != null) {
			theView.contentPanel.getForm().findField('CURRE_FIRM_INTEREST').setValue(Ext.util.Format.number(tempdata3, '0,000.00'));
		}
		if (tempdata4!= '' && tempdata4 != null) {
			theView.contentPanel.getForm().findField('CURRE_MUST_INTEREST').setValue(Ext.util.Format.number(tempdata4, '0,000.00'));
		}
		if (tempdata5!= '' && tempdata5 != null) {
			theView.contentPanel.getForm().findField('LOAN_AVG_M').setValue(Ext.util.Format.number(tempdata5, '0,000.00'));
		}
		if (tempdata6!= '' && tempdata6 != null) {
			theView.contentPanel.getForm().findField('LOAN_AVG_Q').setValue(Ext.util.Format.number(tempdata6, '0,000.00'));
		}
		if (tempdata7!= '' && tempdata7 != null) {
			theView.contentPanel.getForm().findField('LOAN_AVG_Y').setValue(Ext.util.Format.number(tempdata7, '0,000.00'));
		}
	} 
	if(theView._defaultTitle == '个人历史交易流水'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		var k ;
		var ids=getAllSelects()[0].data.ACCOUNT;
		for(k=1;k<getAllSelects().length;k++){
			ids=ids+','+getAllSelects()[k].data.ACCOUNT;
		}
		theView.setParameters({
			accountNo:ids
			});
	}
	if(theView._defaultTitle == '实时交易流水'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		var k ;
		var ids=getAllSelects()[0].data.ACCOUNT;
		for(k=1;k<getAllSelects().length;k++){
			ids=ids+','+getAllSelects()[k].data.ACCOUNT;
		}
		theView.setParameters({
			accountNo:ids
			});
	}
	if(theView._defaultTitle == '实时余额'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
			}
		/*
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
			}*/
		var k ;
		var ids=getAllSelects()[0].data.ACCOUNT;
		for(k=1;k<getAllSelects().length;k++){
			ids=ids+','+getAllSelects()[k].data.ACCOUNT;
		}
		theView.setParameters({
			accountNo:ids//暂时设置查询条件为账号
			});
	}
	
}