/**
*@description 贷款账号信息
*/ 
var groupId=_busiId;//获取客户群ID
var lookupTypes=[
		'CUR_TYPE',//币种代码
		'ACC0100043'
];
//视图类型，0表示客户视图，1表示客户群视图，2表示集团视图，3表示客户经理视图
var tempViewType = JsContext._resId.split('$-$');
if(tempViewType.length > 2 ){
	viewType = tempViewType[1];
}
var needCondition=false;
var createView=false;
var editView=false;
var url=basepath+'/groupAccount.json?type=2&groupId='+groupId+'&viewType='+viewType;

var fields=[
            {name:'STAT_DATE',text:'统计日期',resutlFloat:'right'},
            {name:'ACCOUNT',text:'账户',resutlWidth:80}, 
            {name:'ACCOUNT_NAME',text:'账户名称',resutlWidth:120},
            {name:'WEB_POSIT_NAME',text:'开户网点名称',resutlWidth:60},
            {name:'CUR_TYPE',text:'币种',translateType:'CUR_TYPE',resutlWidth:60},
            {name:'TEMP1',text:'余额（实时）',xtype:'textfield',resutlWidth:60},
            {name:'YEAR_AVG_AMOUNT',text:'年均余额',xtype:'textfield',resutlWidth:100},
            {name:'CURRE_FIRM_INTEREST',text:'本年实收利息',xtype:'textfield',resutlFloat:'right',resutlWidth:80},
            {name:'CURRE_MUST_INTEREST',text:'本年应收利息',xtype:'textfield',resutlWidth:80},
            {name:'ACCOUNT_STAT',text:'账户状态',translateType:'ACC0100043',xtype:'textfield',resutlWidth:60},
            {name:'FIVE_LEVEL_TYPE',text:'五级分类',xtype:'textfield',resutlWidth:60},
            {name:'TEMP2',text:'余额（实时）',xtype:'textfield',gridField:false,resutlWidth:60}
            ];

            
var customerView = [{
	/**
	 * 贷款账号详情
	 */
	title:'详情',
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : ['ACCOUNT','ACCOUNT_NAME','CUR_TYPE','TEMP1','TEMP2'
			],
		/**
		 *存款账号信息
		 */
		fn : function(ACCOUNT,ACCOUNT_NAME,CUR_TYPE,TEMP1,TEMP2
					 ){
			ACCOUNT.readOnly= true;
			ACCOUNT.cls='x-readOnly';
			ACCOUNT_NAME.readOnly= true;
			ACCOUNT_NAME.cls='x-readOnly';
			CUR_TYPE.readOnly= true;
			CUR_TYPE.cls='x-readOnly';
			TEMP1.readOnly= true;
			TEMP1.cls='x-readOnly';
			TEMP2.readOnly= true;
			TEMP2.cls='x-readOnly';
			return [ACCOUNT,ACCOUNT_NAME,IDENT_TYPE,CUR_TYPE,TEMP1,TEMP2];
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
		var ids=getAllSelects()[0].data.ACCOUNT;
		for(k=1;k<getAllSelects().length;k++){
		ids=ids+','+getAllSelects()[k].data.ACCOUNT;
		}
		theView.setParameters({
			accountNo:ids
			});
	}
	
};