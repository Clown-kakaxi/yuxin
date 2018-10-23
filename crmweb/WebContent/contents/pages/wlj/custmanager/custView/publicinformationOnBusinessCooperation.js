/**
*@description 存款账号信息
*/ 
	imports( [
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);
	
var _custId;
var accountNo;
var lookupTypes=[
		'XD000226',//币种代码
		'XD000189',//基本账户状态
		'DM0014',//交易类型
		'ACCOUNT_TYPE'//账户类别
];
var needCondition=false;
var createView=false;
var editView=false;
var url=basepath+'/accountQuery.json?CustId='+_custId;

var fields=[
			
			{name:'OPEN_ACCOUNT_DATE',text:'开户日期',resutlWidth:100,resutlFloat:'right'},
			{name:'ACCT_NO',text:'账户',resutlWidth:80}, 
            {name:'PROD_NAME',text:'账户名称',resutlWidth:80}, 
            {name:'ORG_NAME',text:'开户网点名称',resutlWidth:120},
            {name:'CUR_TYPE',text:'币种',translateType:'XD000226',resutlWidth:60},
            {name:'AMOUNT_ORG_MONEY',text:'余额',viewFn:money('0,000.00'),resutlWidth:100},
            {name:'DEPOSITE_AVG_Y',text:'年平均余额',xtype:'textfield',viewFn:money('0,000.00'),resutlFloat:'right',resutlWidth:80},
            {name:'FREEZE_AMOUNT',text:'unavailable amount',xtype:'textfield',viewFn:money('0,000.00'),resutlFloat:'right',resutlWidth:140},
            {name:'ACCT_TYPE',text:'账户性质',translateType:'ACCOUNT_TYPE',resutlWidth:100},
          //  {name:'ACCT_TYPE',text:'账户性质',translateType:'ACCT_TYPE',resutlWidth:100},
			{name:'DEPOSITE_AVG_Q',text:'季日均',xtype:'textfield',resutlFloat:'right',gridField:false,resutlWidth:80},
		 	{name:'DEPOSITE_AVG_M',text:'月日均',xtype:'textfield',resutlFloat:'right',gridField:false,resutlWidth:80},
			{name:'SUBJECTS',text:'科目',gridField:false,resutlWidth:120},	
			{name:'ACCOUNT_STAT',text:'账户状态',gridField:false,translateType:'XD000189',resutlWidth:120},
			{name:'LOGOUT_ACCOUNT_DATE',text:'销户日期',gridField:false,resutlWidth:120},
			{name:'START_INTER_DATE',text:'起息日',gridField:false,resutlWidth:120},
			{name:'MATURE_DATE',text:'到期日',gridField:false,resutlWidth:120},
			{name:'RATE',text:'利率(%)',xtype:'textfield',gridField:true,resutlWidth:60},
			{name:'TRANS_TIMES',text:'转存次数',xtype:'textfield',gridField:false,resutlWidth:60},	  
            {name:'ACCT_NO1',text:'账号1',xtype:'textfield',gridField:false,resutlWidth:60,hidden:false}
            
            ];

            
var customerView = [{
	/**
	 * 存款账号详情
	 */
	title:'详情',
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : ['ACCT_NO','OPEN_ACCOUNT_DATE','ACCT_NAME','ORG_NAME','CUR_TYPE','AMOUNT_ORG_MONEY','FREEZE_AMOUNT','ACCT_TYPE','DEPOSITE_AVG_Q','DEPOSITE_AVG_M','DEPOSITE_AVG_Y',
					'SUBJECTS','ACCOUNT_STAT','LOGOUT_ACCOUNT_DATE','START_INTER_DATE','MATURE_DATE','RATE','TRANS_TIMES'
			],
		/**
		 *存款账号信息
		 */
		fn : function(ACCT_NO,OPEN_ACCOUNT_DATE,ACCT_NAME,ORG_NAME,CUR_TYPE,AMOUNT_ORG_MONEY,FREEZE_AMOUNT,ACCT_TYPE,DEPOSITE_AVG_Q,DEPOSITE_AVG_M,DEPOSITE_AVG_Y,SUBJECTS,
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
			FREEZE_AMOUNT.readOnly= true;
			FREEZE_AMOUNT.cls='x-readOnly';
			ACCT_TYPE.readOnly= true;
			ACCT_TYPE.cls='x-readOnly';
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
			return [ACCT_NO,OPEN_ACCOUNT_DATE,ACCT_NAME,ORG_NAME,CUR_TYPE,AMOUNT_ORG_MONEY,FREEZE_AMOUNT,ACCT_TYPE,DEPOSITE_AVG_Q,DEPOSITE_AVG_Y,DEPOSITE_AVG_M,
					SUBJECTS,ACCOUNT_STAT,START_INTER_DATE,MATURE_DATE,RATE,TRANS_TIMES];
					  }
		}]


},


{  
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
	title:'机构历史交易流水',
	type:'grid',
	url : basepath + '/evtsavetradtansQuery.json?CustId='+_custId,
	fields : {
		fields:[
		    {name: 'TRAD_DT', text : '交易日期'},  
		    {name: 'TRAD_TIME', text : '交易时间'},
		    {name: 'CURR', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
			}}, 
			{name: 'TRAD_MONEY', text : '交易金额',renderer:money('0,0.00')},
			{name: 'COST', text : '费用',xtype:'textfield',renderer:money('0,0.00')},
			{name: 'TRAD_TYPE', text : '交易类型',renderer:function(value){
				var val = translateLookupByKey("DM0014",value);
				return val?val:"";
				}},
			{name: 'TRAD_ABS', text : '交易摘要'},
			{name: 'TRAD_CHN', text : '交易渠道'},
			{name: 'HANDLER', text : '经办人'},
			{name: 'REVIEW', text : '复核人'},
			{name: 'ACCOUNTIN_DATE', text : '记账日'},
			{name: 'CONTACT_TYPE', text : '往来类型'},
			{name: 'TANS_NO', text : '流水号'},
			{name: 'CUST_ID', text : '客户编号'},
			{name: 'ACCT', text : '账号'}
		]
	}
},
	{
	title:'实时交易流水',
	type:'grid',
	url : basepath + '/accountQuery!trans.json?CustId='+_custId,//evtsavetradtansQuery
	fields : {
		fields:[
		    {name: 'uldt', text : '交易日'},  
		    {name: 'atim', text : '时间'},
		    {name: 'ccyc', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
			}}, 
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
			{name: 'txty', text : '交易类型',renderer:function(value){
				var val = translateLookupByKey("DM0014",value);
				return val?val:"";
				}},
			{name: 'scrm', text : '交易摘要'},
			{name: 'func', text : '交易渠道'},
			{name: 'iusr', text : '经办人'},
			{name: 'ausr', text : '复核人'},
			{name: 'adat', text : '记账日'},
			{name: 'rmrk', text : '往来类型'},
			{name: 'vern', text : '流水号'},
			{name: 'custCd', text : '客户号'},
			{name: 'acno', text : '账号'}
			
			
			
			
			
		
			
			
			
			
			
			
			
			
			
		]
	}
},{
	title:'实时余额',// 存款账号
	type:'grid',
	url:basepath + '/accountQuery!doposit.json?CustId='+_custId,//evtsavetradtansQuery
	fields : {
		fields:[
			{name: 'custCd', text : '核心系统客户号'},
			{name: 'accName', text : '账户名称'},  
			{name: 'accNo', text : '账号'}, 
			{name: 'accCcy', text : '币种',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
				}}, 
			{name: 'accAval', text : '余额',renderer:function(value){
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
			{name: 'sysDate', text : '系统日期'}, 
			{name: 'sysTime', text : '系统时间'} 
		]
	}
   }
];

var firstlayout=-1;
/*
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
*/

beforeviewshow = function(theView){
	if(theView._defaultTitle == '详情'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		theView.contentPanel.getForm().loadRecord(getSelectedData());
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
	if(theView._defaultTitle == '机构历史交易流水'){
		
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		var k ;
		var ids=getAllSelects()[0].data.ACCT_NO1;
		
		for(k=1;k<getAllSelects().length;k++){
			
		ids=ids+','+getAllSelects()[k].data.ACCT_NO1;
		
		
		}
//		accountNo=getSelectedData().data.ACCT_NO;
		theView.setParameters({
			accountNo:getSelectedData().data.ACCT_NO1,
			curr:getSelectedData().data.CUR_TYPE,
			flag:'jgls'
			});
	}
	if(theView._defaultTitle == '实时交易流水'){
		
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
		var k ;
		
		
		var ids=getAllSelects()[0].data.ACCT_NO;
		
		
		
		for(k=1;k<getAllSelects().length;k++){
			ids=ids+','+getAllSelects()[k].data.ACCT_NO;
			}
		/*
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
		AmountNowForm.getForm().findField('TEMP1').setValue('20.00');
		theView.remove(theView.grid,false);
		*/
		theView.setParameters({
			accountNo:ids//暂时设置查询条件为账号
			});
	}
	
}
var beforeconditioninit = function(panel, app){
	app.pageSize = 100;
};