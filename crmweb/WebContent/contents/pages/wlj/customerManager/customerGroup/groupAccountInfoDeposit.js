/**
*@description 存款账号信息
*/ 
var groupId=_busiId;//获取客户群ID
//视图类型，0表示客户视图，1表示客户群视图，2表示集团视图，3表示客户经理视图
var tempViewType = JsContext._resId.split('$-$');
if(tempViewType.length > 2 ){
	viewType = tempViewType[1];
}
var accountNo;
var lookupTypes=[
		'XD000226'//币种代码
];

var createView=false;
var editView=false;
var needCondition=false;
var url=basepath+'/groupAccount.json?type=1&groupId='+groupId+'&viewType='+viewType;




var fields=[
            {name:'OPEN_ACCOUNT_DATE',text:'开户日期',resutlFloat:'right'},
            {name:'ACCT_NAME',text:'账户名称',resutlWidth:80}, 
            {name:'ORG_NAME',text:'开户网点',resutlWidth:120},
            {name:'CUR_TYPE',text:'币种',translateType:'XD000226',resutlWidth:60},
            {name:'AMOUNT_ORG_MONEY',text:'余额',viewFn:money('0,000.00')},
            {name:'DEPOSITE_AVG_Y',text:'年日均余额',xtype:'textfield',resutlFloat:'right',resutlWidth:80},
            {name:'TEMP2',text:'时间（取数时间）',xtype:'textfield',gridField:false,resutlWidth:60},
            {name:'ACCT_NO',text:'账号',xtype:'textfield',gridField:false,resutlWidth:60}
            ];

            
var customerView = [{
	/**
	 * 存款账号详情
	 */
	title:'详情',
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : ['OPEN_ACCOUNT_DATE','ACCT_NAME','ORG_NAME','CUR_TYPE','AMOUNT_ORG_MONEY','DEPOSITE_AVG_Y'
		
			],
		/**
		 *存款账号信息
		 */
		fn : function(OPEN_ACCOUNT_DATE,ACCT_NAME,ORG_NAME,CUR_TYPE,AMOUNT_ORG_MONEY,DEPOSITE_AVG_Y
					 ){
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
			
			
			return [OPEN_ACCOUNT_DATE,ACCT_NAME,ORG_NAME,CUR_TYPE,AMOUNT_ORG_MONEY,DEPOSITE_AVG_Y];
					  }
		}]


},{
	title:'交易流水',
	type:'grid',
	url : basepath + '/evtsavetradtansQuery.json',
	fields : {
		fields:[
			{name: 'TANS_NO', text : '账户名称'},
			{name: 'ORG_NO', text : '机构编号'},  
			{name: 'CUST_ID', text : '客户编号'}, 
			{name: 'ACCT', text : '账号'},
			{name: 'ADVS_ACCT', text : '对手账号'},  
			{name: 'ADVS_ACCT_NAME', text : '对手账号名称'},  
			{name: 'LOAN_FLAG', text : '借贷标志'},  
			{name: 'CURR_TRAN_FLAG', text : '现转标志'},  
			{name: 'TRAD_CHN', text : '交易渠道'}, 
			{name: 'CURR', text : '币种',renderer:function(value){
				var val = translateLookupByKey("CUR_TYPE",value);
				return val?val:"";
				}}, 
			{name: 'TRAD_MONEY', text : '交易金额'}, 
			{name: 'ACCT_BAL', text : '账户余额'}, 
			{name: 'TRAD_DT', text : '交易日期'}, 
			{name: 'TRAD_TIME', text : '交易时间'}, 
			{name: 'TRAD_TELLER', text : '交易柜员'}, 
			{name: 'TRAD_ABS', text : '交易摘要'}, 
			{name: 'ETL_DATE', text : '数据日期'},
			{name: 'HANDLER', text : '经办人'},
			{name: 'COST', text : '费用'},
			{name: 'ACCOUNTIN_DATE', text : '记账日'},
			{name: 'TRAD_TYPE', text : '交易类型'},
			{name: 'CONTACT_TYPE', text : '往来类型'},
			{name: 'CASH_FLAG', text : '钞汇标志'}
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
				{xtype:'textfield',name:'TEMP1',fieldLabel:'余额（实时）'}
			]
		}
		]
	}]
});


beforeviewshow = function(theView){
	if(theView._defaultTitle == '详情'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		theView.contentPanel.getForm().loadRecord(getSelectedData());
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
	
};