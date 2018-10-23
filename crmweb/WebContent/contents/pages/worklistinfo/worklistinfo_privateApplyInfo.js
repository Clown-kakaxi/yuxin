/**
 * @description 个人账户变更申请书 提交审核-待办工作审批
 * @author denghj
 * @since 2015-12-17
 */
Ext.onReady(function(){
	var instanceid = curNodeObj.instanceid;
	var custId = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
/**
 * 定义所需要的Store
 */	
	
	//国籍
	var citizenshipStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000025'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	}); 
	
	//性别
	var sexStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000016'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	}); 

	


//-----------------------------------------------------客户信息Panel Begin---------------------------------------
	//B 客户信息变更
	var custChangedInfoPanel = new Ext.form.FormPanel({
//		reader : custChangedInfoReader,
		title : '<font color=black><h1>B 客户信息变更</h1></font>',
		headerStyle:'background-color:transparent;',
		id : 'custChangedInfo',
		frame : false,
		autoHeight:true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'姓名拼音',name:'PINYIN_NAME',id:'PINYIN_NAME',anchor:'90%',disabled:true},
				       {xtype:'datefield',fieldLabel:'出生日期',name:'BIRTHDAY',id:'BIRTHDAY',format:'Y-m-d',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype : 'combo',fieldLabel:'性别',name:'GENDER',id:'GENDER',anchor:'90%',
						store:sexStore,valueField : 'key',displayField : 'value',mode : 'local',
						typeAhead : true,forceSelection : true,triggerAction : 'all',
						labelStyle:'text-align:right;',selectOnFocus : true,disabled:true},
				       {xtype:'combo',fieldLabel:'国籍',name:'CITIZENSHIP',id:'CITIZENSHIP',anchor:'90%',
						store:citizenshipStore,valueField : 'key',displayField : 'value',mode : 'local',
						typeAhead : true,forceSelection : true,triggerAction : 'all',
						labelStyle:'text-align:right;',selectOnFocus : true,disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'datefield',fieldLabel:'证件有效期',name:'IDENT_EXPIRED_DATE',id:'IDENT_EXPIRED_DATE',format:'Y-m-d',anchor:'90%',disabled:true},
				       {xtype:'textfield',fieldLabel:'出生地',name:'BIRTHLOCALE',id:'BIRTHLOCALE',anchor:'90%',disabled:true}]
			}]
		},{
			layout:'column',
			items : [{ 
					layout:'form',
 				    columnWidth : .25,
		            items:[{xtype:'textfield',fieldLabel:'移动电话',name:'CONTMETH_INFO_SJ',id:'CONTMETH_INFO_SJ',anchor:'90%',disabled:true}]
			},{
				layout:'fit',
				columnWidth : .5,
				items:[{xtype:'displayfield',value:'（此电话将用于接受短信交易认证码和账务变动通知，仅限大陆和台湾手机）'}]
			}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .1,
				items:[{xtype:'displayfield',fieldLabel:'电话1',anchor:'90%'},
				       {xtype:'displayfield',fieldLabel:'电话2',anchor:'90%'}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'combo',fieldLabel:'电话类型',name:'CONTMETH_TYPE_1',id:'CONTMETH_TYPE_1',value:'联系手机号码',anchor:'90%',disabled:true},
				       {xtype:'combo',fieldLabel:'电话类型',name:'CONTMETH_TYPE_2',id:'CONTMETH_TYPE_2',value:'家庭电话',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .418,
				items:[{xtype:'textfield',fieldLabel:'电话号码',name:'CONTMETH_INFO_YD',id:'CONTMETH_INFO_YD',anchor:'90%',disabled:true},
				       {xtype:'textfield',fieldLabel:'电话号码',name:'CONTMETH_INFO_JT',id:'CONTMETH_INFO_JT',anchor:'90%',disabled:true}]
			}]
		},{
			layout:'column',
			items:[{ 
				layout:'form',
				columnWidth : .43,
	            items:[{xtype:'textfield',fieldLabel:'居住地址',name:'HOME_ADDR',id:'HOME_ADDR',anchor:'90%',disabled:true}]
		},{ 
			layout:'fit',
			columnWidth : .07,
			items:[{xtype:'radiogroup',name:'HOME_STYLE',id:'HOME_STYLE',anchor:'90%',disabled:true,items:[{boxLabel:'租赁',name:'HOME_STYLE',inputValue:1},{boxLabel:'自有',name:'HOME_STYLE',inputValue:2}]}]
		},{ 
			layout:'form',
			columnWidth : .25,
            items:[{xtype:'textfield',fieldLabel:'居住地邮编',name:'HOME_ZIPCODE',id:'HOME_ZIPCODE',anchor:'90%',disabled:true}]
		}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .5,
				items:[{xtype:'textfield',fieldLabel:'邮寄地址',name:'POST_ADDR',id:'POST_ADDR',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'邮寄邮编',name:'POST_ZIPCODE',id:'POST_ZIPCODE',anchor:'90%',disabled:true}]				    
			}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .58,
				items:[{xtype:'textfield',fieldLabel:'电子邮件E-mail',name:'EMAIL',id:'EMAIL',anchor:'90%',disabled:true}]
			},{
				layout:'fit',
				columnWidth : .25,
				items:[{xtype:'checkbox',width:200,boxLabel:'同步更新为电子对账单接收E-mail',name:'IF_UPDATE_MAIL',id:'IF_UPDATE_MAIL',anchor:'90%',disabled:true}]				    
			}]
		},{
			layout:'column',
			items : [{ 
					layout:'form',
 				    columnWidth : .25,
		            items:[{xtype:'displayfield',fieldLabel:'职业资料',anchor:'90%',disabled:true}]
			}]
		},{
			layout:'column',
			items:[{xtype:'displayfield',width:50},
			       {xtype:'radiogroup',name:'PROFESSION_STYLE',id:'PROFESSION_STYLE',width:520,disabled:true,columns:4,
						items:[{boxLabel:'全员制雇员',name:'PROFESSION_STYLE',inputValue:1},
 	                           {boxLabel:'自雇',name:'PROFESSION_STYLE',inputValue:2},
 	                           {boxLabel:'退休',name:'PROFESSION_STYLE',inputValue:3},
 	                           {boxLabel:'其他（请具体注明）',name:'PROFESSION_STYLE',inputValue:4}]},
			       {xtype:'textfield',name:'PROFESSION_STYLE_REMARK',id:'PROFESSION_STYLE_REMARK',width:200,anchor:'90%',disabled:true}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'单位名称',name:'UNIT_NAME',id:'UNIT_NAME',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'职位',name:'DUTY',id:'DUTY',anchor:'90%',disabled:true}]
			}]
		},{
			layout:'column',
			items:[
					{xtype: 'displayfield',width:20 },			       
					{xtype:'displayfield',value:'是否为美国纳税人：'},
					{xtype:'radiogroup',name:'USA_TAX_FLAG',id:'USA_TAX_FLAG',width:100,disabled:true,columns:2,items:[
					         {boxLabel:'否',name:'USA_TAX_FLAG',inputValue:'N'},
					         {boxLabel:'是',name:'USA_TAX_FLAG',inputValue:'Y'}]},
					{xtype:'displayfield',value:'（US TIN/SSN：'},
					{xtype:'textfield',name:'USA_TAX_IDEN_NO',id:'USA_TAX_IDEN_NO',width:200,anchor:'90%',disabled:true},
					{xtype: 'displayfield',value: '）'}
			]
		},{
			layout:'column',
			items:[
					{xtype: 'displayfield',width:20 },			       
					{xtype:'displayfield',value:'是否在我行有关联人：'},
					{xtype:'radiogroup',name:'IS_RELATED_BANK',id:'IS_RELATED_BANK',width:100,disabled:true,columns:2,items:[{boxLabel:'否',name:'IS_RELATED_BANK',inputValue:'N'},{boxLabel:'是',name:'IS_RELATED_BANK',inputValue:'Y'}]}
			]
		}]
	});
	
	/**
	 * 将账户展示由form改为grid**************************开始******************************
	 */
	var accountReader = new Ext.data.JsonReader({
		root : 'json',
		totalProperty : 'json.count'
	},[{name:'CUST_ID',mapping:'CUST_ID'},
	   {name:'ACCOUNT_CONTENTS',mapping:'ACCOUNT_CONTENTS'},//账户内容
	   {name:'ACCOUNT_NUMBERS',mapping:'ACCOUNT_NUMBERS'},//账户账号
	   ]);
	
	//境内_一般综合账户
	var jnYbzhCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jnYbzhStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnYbzhStore.load({
		params: {
			instanceid: instanceid,
			contentType: '1'
		}
	});
	
	var jnYbzhGrid = new Ext.grid.GridPanel({
		title : '<font color=black>一般综合账户</font>',
		headerStyle: 'background-color:transparent;',
		store: jnYbzhStore,
		cm: jnYbzhCm,
		frame : true,
		autoHeight: true,
//		autoWidth: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境内_外汇结算户
	var jnWhjsCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jnWhjsStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnWhjsStore.load({
		params: {
			instanceid: instanceid,
			contentType: '2'
		}
	});
	
	var jnWhjsGrid = new Ext.grid.GridPanel({
		title : '<font color=black>外汇结算户</font>',
		headerStyle:'background-color:transparent;',
		store: jnWhjsStore,
		cm: jnWhjsCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境内_外汇资本金户
	var jnWhzbCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jnWhzbStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnWhzbStore.load({
		params: {
			instanceid: instanceid,
			contentType: '3'
		}
	});
	
	var jnWhzbGrid = new Ext.grid.GridPanel({
		title : '<font color=black>外汇资本金户</font>',
		headerStyle:'background-color:transparent;',
		store: jnWhzbStore,
		cm: jnWhzbCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境内_外汇境内资产变现账户
	var jnWhbxCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jnWhbxStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnWhbxStore.load({
		params: {
			instanceid: instanceid,
			contentType: '4'
		}
	});
	
	var jnWhbxGrid = new Ext.grid.GridPanel({
		title : '<font color=black>外汇境内资产变现账户</font>',
		headerStyle:'background-color:transparent;',
		store: jnWhbxStore,
		cm: jnWhbxCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境外_一般综合账户
	var jwYbzhCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jwYbzhStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwYbzhStore.load({
		params: {
			instanceid: instanceid,
			contentType: '5'
		}
	});
	
	var jwYbzhGrid = new Ext.grid.GridPanel({
		title : '<font color=black>一般综合账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwYbzhStore,
		cm: jwYbzhCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境外_外汇结算户
	var jwWhjsCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jwWhjsStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwWhjsStore.load({
		params: {
			instanceid: instanceid,
			contentType: '6'
		}
	});
	
	var jwWhjsGrid = new Ext.grid.GridPanel({
		title : '<font color=black>外汇结算户</font>',
		headerStyle:'background-color:transparent;',
		store: jwWhjsStore,
		cm: jwWhjsCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境外_外汇资本金户
	var jwWhzbCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jwWhzbStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwWhzbStore.load({
		params: {
			instanceid: instanceid,
			contentType: '7'
		}
	});
	
	var jwWhzbGrid = new Ext.grid.GridPanel({
		title : '<font color=black>外汇资本金户</font>',
		headerStyle:'background-color:transparent;',
		store: jwWhzbStore,
		cm: jwWhzbCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境外_港澳居民人民币存储账户
	var jwGarmbCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jwGarmbStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwGarmbStore.load({
		params: {
			instanceid: instanceid,
			contentType: '8'
		}
	});
	
	var jwGarmbGrid = new Ext.grid.GridPanel({
		title : '<font color=black>港澳居民人民币存储账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwGarmbStore,
		cm: jwGarmbCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境外_人民币前期费用专用结算账户
	var jwRmbqqfyCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jwRmbqqfyStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwRmbqqfyStore.load({
		params: {
			instanceid: instanceid,
			contentType: '9'
		}
	});
	
	var jwRmbqqfyGrid = new Ext.grid.GridPanel({
		title : '<font color=black>人民币前期费用专用结算账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwRmbqqfyStore,
		cm: jwRmbqqfyCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	//境外_人民币再投资专用结算账户
	var jwRmbztzCm = new Ext.grid.ColumnModel(
			{columns:[{ header : '账号',dataIndex:'ACCOUNT_NUMBERS', sortable : true, width : 600}]}
	);
	
	var jwRmbztzStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyChecked!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwRmbztzStore.load({
		params: {
			instanceid: instanceid,
			contentType: '10'
		}
	});
	
	var jwRmbztzGrid = new Ext.grid.GridPanel({
		title : '<font color=black>人民币再投资专用结算账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwRmbztzStore,
		cm: jwRmbztzCm,
		frame : true,
		autoHeight: true,
		autoScroll : true,
		region : 'center', 
		stripeRows : true, // 斑马线
	});
	
	/**
	 * 将账户展示由form改为grid**************************结束******************************
	 */
	
	//C 账户变更（开通和关闭个别账户）
	var accountChangedPanel = new Ext.form.FormPanel({
		title : '<font color=black><h1>C 账户变更（开通和关闭个别账户）</h1></font>',
		headerStyle:'background-color:transparent;',
		frame : false,
		autoHeight:true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[{xtype:'displayfield',width:20},
			       {xtype:'displayfield',value:'<h1>境内客户：</h1>'},
			       {xtype:'displayfield',width:5},
			       {xtype:'checkbox',id:'JN',name:'JN',disabled:true}]
		},{
			layout:'column',
			items:[jnYbzhGrid]
		},{
			layout:'column',
			items:[jnWhjsGrid]
		},{
			layout:'column',
			items:[jnWhzbGrid]
		},{
			layout:'column',
			items:[jnWhbxGrid]
		},{
			layout:'column',
			items:[{xtype:'displayfield',width:20},
			       {xtype:'displayfield',value:'<h1>境外客户：</h1>'},
			       {xtype:'displayfield',width:5},
			       {xtype:'checkbox',id:'JW',name:'JW',disabled:true}]
		},
		{
			layout:'column',
			items:[jwYbzhGrid]
		},
		{
			layout:'column',
			items:[jwWhjsGrid]
		},
		{
			layout:'column',
			items:[jwWhzbGrid]
		},
		{
			layout:'column',
			items:[jwGarmbGrid]
		},
		{
			layout:'column',
			items:[jwRmbqqfyGrid]
		},
		{
			layout:'column',
			items:[jwRmbztzGrid]
		}]
	});
	
//	D 服务信息变更
	var serviceInfoChangedPanel = new Ext.form.FormPanel({
		title : '<font color=black><h1>D 服务信息变更</h1></font>',
		headerStyle:'background-color:transparent;',
		frame : false,
		autoHeight:true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[{
				layout : 'form',
				labelWidth : 150,
				labelSeparator : '',
				columnWidth : .43,
				items:[{xtype:'displayfield',fieldLabel:'<b>1.电子银行服务-网络银行</b>'}]
			}]
		},{
			layout:'column',
			items:[{
					layout:'form',
					labelWidth:220,
					labelSeparator:'',
					columnWidth : .43,
					items:[{xtype:'displayfield',fieldLabel:'U-key认证'}]
				},{
					layout:'form',
					columnWidth : .15,
					items:[{xtype:'radiogroup',id:'UKEY',name:'UKEY',disabled:true,columns:2,items:[{boxLabel:'开通',name:'UKEY',inputValue:32},{boxLabel:'关闭',name:'UKEY',inputValue:'-1'}]}]
				}
			]
		},{
			layout:'column',
			items:[{
					layout:'form',
					labelWidth:220,
					labelSeparator:'',
					columnWidth : .43,
					items:[{xtype:'displayfield',fieldLabel:'短信认证码（汇款限额RMB50,000元）'}]
				},{
					layout:'form',
					columnWidth : .15,
					items:[{xtype:'radiogroup',id:'MESSAGE_CODE',name:'MESSAGE_CODE',disabled:true,columns:2,items:[{boxLabel:'开通',name:'MESSAGE_CODE',inputValue:34},{boxLabel:'关闭',name:'MESSAGE_CODE',inputValue:'-1'}]}]
				}
			]
		},{
			layout:'column',
			items:[{
					layout:'form',
					labelWidth:220,
					labelSeparator:'',
					columnWidth : .43,
					items:[{xtype:'displayfield',fieldLabel:'U-key挂失'}]
				},{
					layout:'form',
					columnWidth : .15,
					items:[{xtype:'radiogroup',id:'UKEY_LOST',name:'UKEY_LOST',disabled:true,columns:2,items:[{boxLabel:'开通',name:'UKEY_LOST',inputValue:30},{boxLabel:'关闭',name:'UKEY_LOST',inputValue:'-1'}]}]
				}
			]
		},{
			layout:'column',
			items:[{
				layout:'form',
				labelWidth:150,
				labelSeparator:'',
				columnWidth : .43,
				items:[{xtype:'displayfield',fieldLabel:'<b>2.电子银行服务-手机银行</b>'}]
			}]
		},{
			layout:'column',
			items:[{
					layout:'form',
					labelWidth:220,
					labelSeparator:'',
					columnWidth : .43,
					items:[{xtype:'displayfield',fieldLabel:'查询'}]
				},{
					layout:'form',
					columnWidth : .15,
					items:[{xtype:'radiogroup',id:'MOBILE_BANKING_QUERY',name:'MOBILE_BANKING_QUERY',disabled:true,columns:2,items:[{boxLabel:'开通',name:'MOBILE_BANKING_QUERY',inputValue:42},{boxLabel:'关闭',name:'MOBILE_BANKING_QUERY',inputValue:'-1'}]}]
				}
			]
		},{
			layout:'column',
			items:[{
					layout:'form',
					labelWidth:220,
					labelSeparator:'',
					columnWidth : .43,
					items:[{xtype:'displayfield',fieldLabel:'交易'}]
				},{
					layout:'form',
					columnWidth : .15,
					items:[{xtype:'radiogroup',id:'MOBILE_BANKING_TRADE',name:'MOBILE_BANKING_TRADE',disabled:true,columns:2,items:[{boxLabel:'开通',name:'MOBILE_BANKING_TRADE',inputValue:44},{boxLabel:'关闭',name:'MOBILE_BANKING_TRADE',inputValue:'-1'}]}]
				}
			]
		},{
			layout:'column',
			items:[{
				layout:'form',
				labelWidth:150,
				labelSeparator:'',
				columnWidth : .43,
				items:[{xtype:'displayfield',fieldLabel:'<b>3.电子银行服务-电话银行</b>'}]
			},{
				layout:'form',
				columnWidth : .15,
				items:[{xtype:'radiogroup',id:'PHONE_BANKING',name:'PHONE_BANKING',disabled:true,columns:2,items:[{boxLabel:'开通',name:'PHONE_BANKING',inputValue:38},{boxLabel:'关闭',name:'PHONE_BANKING',inputValue:'-1'}]}]
			}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				labelWidth:420,
				labelSeparator:'',
				columnWidth : .43,
				items:[{xtype:'displayfield',fieldLabel:'<b>4.传真交易服务 （*同时默认开通电子对账单服务，请填写电子对账单服务）</b>'}]
			}]
		},{
			layout:'column',
			items:[{xtype:'displayfield',width:50},
			       {xtype:'displayfield',value:'传真机号:'},
			       {xtype:'displayfield',width:10},
			       {xtype:'textfield',width:220,id:'FAX_NUMBER',name:'FAX_NUMBER',anchor:'90%',disabled:true},
			       {xtype:'displayfield',width:348},
			       {xtype:'radiogroup',id:'TRANSACTION_SERVICE',name:'TRANSACTION_SERVICE',width:100,disabled:true,columns:2,items:[{boxLabel:'开通',name:'TRANSACTION_SERVICE',inputValue:60},{boxLabel:'关闭',name:'TRANSACTION_SERVICE',inputValue:'-1'}]}
			]
		},{
			layout:'column',
			items:[{
				layout:'form',
				labelWidth:90,
				labelSeparator:'',
				columnWidth : .43,
				items:[{xtype:'displayfield',fieldLabel:'<b>5.电子对账单</b>'}]
			}]
		},{
			layout:'column',
			items:[
			       {xtype:'displayfield',width:50},
			       {xtype:'displayfield',value:'E-mail:'},
			       {xtype:'displayfield',width:8},
			       {xtype:'hidden',name:'MAIL_ADDRESS'},
			       {xtype:'displayfield',value:'（'},
			       {xtype:'checkbox',width:80,boxLabel:'同电子邮件',id:'MAIL_ADDRESS',name:'MAIL_ADDRESS',anchor:'90%',disabled:true},
			       {xtype:'displayfield',value:'）'},
			       {xtype:'textfield',width:220,id:'MAIL',name:'MAIL',vtype:'email',anchor:'90%',disabled:true},
			       {xtype:'displayfield',width:260},
			       {xtype:'radiogroup',id:'STATEMENTS',name:'STATEMENTS',width:100,disabled:true,columns:2,items:[{boxLabel:'开通',name:'STATEMENTS',inputValue:50},{boxLabel:'关闭',name:'STATEMENTS',inputValue:'-1'}]}
			]
		},{
			layout:'column',
			items:[{
				layout:'form',
				labelWidth:100,
				labelSeparator:'',
				columnWidth : .43,
				items:[{xtype:'displayfield',fieldLabel:'<b>6.账务变动通知</b>'}]
			},{
				layout:'form',
				columnWidth : .15,
				items:[{xtype:'radiogroup',id:'CHANGE_NOTICE',name:'CHANGE_NOTICE',disabled:true,columns:2,items:[{boxLabel:'开通',name:'CHANGE_NOTICE',inputValue:65},{boxLabel:'关闭',name:'CHANGE_NOTICE',inputValue:'-1'}]}]
			}]
		}]
	});
		
	//F 户名变更
	var nameChangedPanel = new Ext.form.FormPanel({
		title : '<font color=black><h1>F 户名变更</h1></font>',
		headerStyle:'background-color:transparent;',
		frame : false,
		autoHeight:true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[
		       {xtype:'displayfield',width:50},
		       {xtype:'checkbox',width:400,boxLabel:'兹因变更姓名，本人拟将在贵行所开立之户名称予以以下更改',name:'CHANGE_NAME',id:'CHANGE_NAME',anchor:'90%',disabled:true}
			]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'旧户名',name:'PERSONAL_OLD_NAME',id:'PERSONAL_OLD_NAME',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'新户名',name:'PERSONAL_NEW_NAME',id:'PERSONAL_NEW_NAME',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'新户名拼音',name:'PINYIN_NEW_NAME',id:'PINYIN_NEW_NAME',anchor:'90%',disabled:true}]
			}]
		}]
	});
//-----------------------------------------------------客户信息Panel End---------------------------------------	

	
//-----------------------------------------------------加载所有Store并进行数据处理   Begin-------------------------------------
	
	/**
	 * 为各Panel定义JsonReader 
	 */
	//客户信息JsonReader
	var custChangedInfoReader = new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'custId',
		totalProperty : 'json.count',
		root : 'json.data',
	}, [{name:'CUST_ID'},
	    {name:'PERSONAL_NAME'},//客户姓名
	    {name:'PINYIN_NAME'},//姓名拼音
	    {name:'GENDER'},//性别
	    {name:'IDENT_EXPIRED_DATE'},//证件有效期
	    {name:'BIRTHDAY'},//出身日期
	    {name:'CITIZENSHIP'},//国籍
	    {name:'BIRTHLOCALE'},//出生地
//	    {name:'MOBILE_PHONE'},//
//	    {name:'CONTMETH_TYPE_1'},//
	    {name:'CONTMETH_INFO_SJ'},//
//	    {name:'CONTMETH_TYPE_2'},
	    {name:'CONTMETH_INFO_YD'},
	    {name:'CONTMETH_INFO_JT'},
	    {name:'HOME_ADDR'},//居住地址
	    {name:'HOME_STYLE'},//居住类型--租赁or自有
	    {name:'HOME_ZIPCODE'},//居住地邮编
	    {name:'POST_ADDR'},//邮寄地址
	    {name:'POST_ZIPCODE'},//邮寄邮编
	    {name:'EMAIL'},//电子邮件E-mail
	    {name:'IF_UPDATE_MAIL'},//同步更新为电子对账单接收E-mail
	    {name:'PROFESSION_STYLE'},//职业资料 
	    {name:'PROFESSION_STYLE_REMARK'},//职业资料其他说明
	    {name:'UNIT_NAME'},//单位名称
	    {name:'DUTY'},//职位
	    {name:'USA_TAX_FLAG'},//是否为美国纳税人
	    {name:'USA_TAX_IDEN_NO'},//US TIN/SSN
	    {name:'IS_RELATED_BANK'}//是否在我行有关联人
	    ]);
	
	//账号JsonReader
	var accountChangedReader = new Ext.data.JsonReader({
		root : 'json',
		idProperty : 'custId',
		totalProperty : 'json.count'
	},[{name:'CUST_ID'},
	   {name:'IS_DOMESTIC_CUST'},//客户范围（境内/境外）0:境内    1:境外
	   {name:'ACCOUNT_CONTENTS'},//账户内容
	   {name:'ACCOUNT_NUMBERS'},//账户账号
	   {name:'STATE'}//复核标识
	   ]);
	
	//服务信息JsonReader
	var serviceInfoChangedReader = new Ext.data.JsonReader({
		root : 'json.data',
		idProperty : 'custId',
		totalProperty : 'json.count'
	}, [{name:'CUST_ID'},
	    {name:'IS_NT_BANK'},//网络银行
	    {name:'UKEY'},		//U-key认证
	    {name:'MESSAGE_CODE'},//短信认证码
	    {name:'UKEY_LOST'},//U-key挂失
	    {name:'MOBILE_BANKING'},//手机银行
	    {name:'MOBILE_BANKING_QUERY'},//手机银行_查询
	    {name:'MOBILE_BANKING_TRADE'},//手机银行_交易
	    {name:'PHONE_BANKING'},//电话银行
	    {name:'TRANSACTION_SERVICE'},//传真交易服务
	    {name:'FAX_NUMBER'},//传真机号
	    {name:'STATEMENTS'},//电子对账单 
	    {name:'MAIL'},//E-mail
	    {name:'MAIL_ADDRESS'},//是否同电邮地址
	    {name:'CHANGE_NOTICE'}//账务变动通知
	]);
	
	//已变更JsonReader
	var custInfoUpHisReader = new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'custId',
		totalProperty : 'json.count',
		root : 'json.data',
	},[{name:'CUST_ID'},//客户编号
	   {name:'CORE_NO'},//核心客户号
	   {name:'CUST_NAME'},//客户名称
	   {name:'UP_ID'},//主键ID
	   {name:'UPDATE_DATE'},//修改日期
	   {name:'UPDATE_ITEM'},//修改项目
	   {name:'UPDATE_BE_CONT'},//修改前内容
	   {name:'UPDATE_USER'},//修改人
	   {name:'UPDATE_FLAG'},//修改标识
	   {name:'UPDATE_ITEM_EN'},//修改项目字段名
	   {name:'UPDATE_PAGE_ITEM_EN'},//修改项目页面中字段
	   {name:'UPDATE_TABLE'},//修改表名
	   {name:'UPDATE_TABLE_ID'},//修改表ID
	   {name:'UPDATE_AF_CONT'},//修改后内容
	   {name:'UPDATE_AF_CONT_VIEW'},//修改后列表展示内容
	   {name:'USER_NAME'},//当前用户
	   {name:'APPR_FLAG'}//是否通过审批（标识为1时标志为通过审批）
	]);
	
	
	/**
	 * 定义Store 加载各自JsonReader
	 */
	//客户变更信息Store
	var custInfoHisStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/privateApplyChecked.json'
		}),
		method : 'get',
		reader : custInfoUpHisReader
	});
	custInfoHisStore.load({
		params : {instanceid : instanceid}
	});
	

	//客户信息Store
	var custChangedInfoStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/privateApplyChecked!queryCustInfo.json'
		}),
		method : 'get',
		reader : custChangedInfoReader
	});
	custChangedInfoStore.load({
		params : {custId : custId},
		callback : function(){
			window.__setCustChangedInfoValue();
		}
	});
	
	//账户信息Store
	var accountChangedStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/privateApplyChecked!queryAccountInfo.json'
		}),
		method : 'get',
		reader : accountChangedReader
	});
	accountChangedStore.load({
		params : {custId : custId},
		callback : function(){
			window.__setAccountChangedValue();
		}
	});
	
	//服务信息Store
	var serviceInfoChangedStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/privateApplyChecked!queryServiceInfo.json'
		}),
		method : 'get',
		reader : serviceInfoChangedReader
	});
	serviceInfoChangedStore.load({
		params : {custId : custId},
		callback : function(){
			window.__setServiceInfoChangedValue();
		}
	});
//-----------------------------------------------------加载所有Store并进行数据处理   End---------------------------------------

//-----------------------------------------------------客户信息Gird Begin----------------------------------------
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 35
	});
	
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, 
     	{ header : '客户编号',dataIndex:'CUST_ID', sortable : true, width : 100},
     	{ header : '核心客户号',dataIndex:'CORE_NO', sortable : true, width : 100},
     	{ header : '变更项目', dataIndex:'UPDATE_ITEM', sortable : true, width : 120},
        { header : '变更前内容',dataIndex:'UPDATE_BE_CONT',sortable : true, width : 360},
        { header : '变更后内容',dataIndex:'UPDATE_AF_CONT_VIEW',sortable : true, width : 360},
        { header : '修改字段',dataIndex:'UPDATE_ITEM_EN',id:'updateItemEn',hidden:true},
        { header : '修改人',dataIndex:'USER_NAME',sortable:true, width : 100 },
        { header : '修改时间',dataIndex:'UPDATE_DATE',sortable:true, width : 120},
//        { header : '区域',dataIndex : 'UPDATE_TABLE',id:'updateTable',sortable:true,width : 120,renderer:function(val){
//        	var updateTable = val;
//			switch(updateTable){
//				case 'ACRM_F_CI_PERSON':
//				case 'ACRM_F_CI_PERSON_ADDITIONAL':
//				case 'ACRM_F_CI_CUST_IDENTIFIER':
//				case 'ACRM_F_CI_CONTMETH':
//				case 'ACRM_F_CI_ADDRESS':
//				case 'ACRM_F_CI_PER_KEYFLAG':
//					return 'B客户信息变更';
//					break;
//				case 'ACRM_F_CI_ACCOUNT_INFO':
//					return 'C账户变更';
//					break;
//				case 'ACRM_F_CI_BANK_SERVICE':
//					return 'D服务信息变更';
//					break;
//				default:
//					break;
//			}
//        }},
        { header : '状态',dataIndex:'APPR_FLAG',sortable:true,width:100,hidden:!curNodeObj.approvalHistoryFlag,renderer:function(val){
        	if(val == '1'){
        		return '同意';
        	}else if(val == '2'){
        		return '否决/撤办';
        	}else{
        		return '复核中';
        	}
        }}
	]);
	// 表格实例
//	var rowClassFlag = false;
//	var rowHisFlag = "";
	//客户信息变更列表
	var custGrid = new Ext.grid.GridPanel({
		id:'viewgrid',
		frame : true,
		height:180,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : custInfoHisStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		viewConfig : {
			getRowClass : function(record,rowIndex,rowParams,custInfoHisStore){
				  	return 'my_row_set_red';
			},
			forceFit:false,
			autoScroll:true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
   var custFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '客户信息变更列表',
	    items:[custGrid]
   }); 
//-----------------------------------------------------客户信息Gird End------------------------------------------
	
	
//-----------------------------------------------------对比custInfoHisStore与其他store 找出变更信息    Begin-----------------------
	/**
	 * custInfoHisStore与custChangedInfoStore对比
	 * 并将对比结果对custChangedInfoPanel赋值
	 */
	window.__setCustChangedInfoValue = function(){
		if(custChangedInfoStore.getCount() != 0){//custChangedInfoPanel加载原信息
			for(var i=0;i<custChangedInfoStore.getCount();i++){
				var custChangedJson = custChangedInfoStore.getAt(i).json;
				for(key in custChangedJson){//遍历custChangedJson
					if(key == 'CUST_ID' || key == 'PERSONAL_NAME'){
						continue;
					}
					Ext.getCmp(key).setValue(custChangedJson[key]);
				}
				Ext.getCmp('PERSONAL_OLD_NAME').setValue(custChangedJson.PERSONAL_NAME);
			}
		}

		if(custInfoHisStore.getCount() != 0){//已变信息替换原信息
			for(var i=0;i<custInfoHisStore.getCount();i++){
				var data = custInfoHisStore.getAt(i).data;  //取出每条custInfoHisStore信息
				if(data.UPDATE_TABLE != 'ACRM_F_CI_ACCOUNT_INFO' && data.UPDATE_TABLE != 'ACRM_F_CI_BANK_SERVICE'){//并且修改表字段与key一致，将对应field值改为UPDATE_AF_CONT
					Ext.getCmp(data.UPDATE_PAGE_ITEM_EN).setValue(data.UPDATE_AF_CONT);
				}
			}
		}
		
	};
	
	/**
	 * custInfoHisStore与accountChangedStore对比
	 * 并将对比结果对accountChangedPanel赋值
	 */
	window.__setAccountChangedValue = function(){
		if(accountChangedStore.getCount() != 0){//accountChangedPanel加载原信息  只加载客户范围  其余信息已由变更信息覆盖
			for(var i=0;i<accountChangedStore.getCount();i++){
				var data = accountChangedStore.getAt(i).data;
				var flag = data.IS_DOMESTIC_CUST;//客户范围（境内/境外）
				if(flag == 0){//境内     将境外设为只读
					Ext.getCmp("JN").setValue(true);
				}else if(flag == 1){//境外     将境内设为只读
					Ext.getCmp("JW").setValue(true);
				}
	    	}
		}
		
		if(custInfoHisStore.getCount() != 0){//已变信息替换原信息
			var accountContentArr = "";
			var accountNumberArr = "";
			var flag = "";//客户范围（境内/境外）
			for(var i=0;i<custInfoHisStore.getCount();i++){
				var data = custInfoHisStore.getAt(i).data;  //取出每条custInfoHisStore信息
				if(data.UPDATE_TABLE == 'ACRM_F_CI_ACCOUNT_INFO' && data.UPDATE_PAGE_ITEM_EN == 'IS_DOMESTIC_CUST'){//修改表字段与key一致，将对应field值改为UPDATE_AF_CONT
					flag = data.UPDATE_AF_CONT;
				}
			}
			if(flag == 0){//境内     将境外设为只读
				Ext.getCmp("JN").setValue(true);
			}else if(flag == 1){//境外     将境内设为只读
				Ext.getCmp("JW").setValue(true);
			}
		}
		
	};
	
	/**
	 * custInfoHisStore与serviceInfoChangedStore对比
	 * 并将对比结果对serviceInfoChangedPanel赋值
	 */
	window.__setServiceInfoChangedValue = function(){

		if(serviceInfoChangedStore.getCount() != 0){//serviceInfoChangedPanel加载原信息
			for(var i=0;i<serviceInfoChangedStore.getCount();i++){
				var serviceJson = serviceInfoChangedStore.getAt(i).json;
				for(key in serviceJson){
					if(key == 'CUST_ID' || key == 'MOBILE_BANKING' || key == 'IS_NT_BANK'){
						continue;
					}else if(key == 'MAIL_ADDRESS' && serviceJson[key] == '1'){
						Ext.getCmp(key).setValue(true);
					}else{
						Ext.getCmp(key).setValue(serviceJson[key]);
					}
				}
	    	}
		}
		
		if(custInfoHisStore.getCount() != 0){//已变信息替换原信息
			for(var i=0;i<custInfoHisStore.getCount();i++){
				var data = custInfoHisStore.getAt(i).data;  //取出每条custInfoHisStore信息
				if(data.UPDATE_TABLE == 'ACRM_F_CI_BANK_SERVICE' && data.UPDATE_PAGE_ITEM_EN != ''){//并且修改表字段与key一致，将对应field值改为UPDATE_AF_CONT
					
					if(data.UPDATE_PAGE_ITEM_EN == 'MOBILE_BANKING' || data.UPDATE_PAGE_ITEM_EN == 'IS_NT_BANK'){
						continue;
					}else if(data.UPDATE_PAGE_ITEM_EN == 'MAIL_ADDRESS'){
						Ext.getCmp(data.UPDATE_PAGE_ITEM_EN).setValue(data.UPDATE_AF_CONT);
					}else{
						Ext.getCmp(data.UPDATE_PAGE_ITEM_EN).setValue(data.UPDATE_AF_CONT);
					}
				}
			}
		}
	};
	
//-----------------------------------------------------对比custInfoHisStore与其他store 找出变更信息    End--------------------------

	var custSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: 'B客户信息变更列表',
	    items:[custChangedInfoPanel]
   }); 
	
	var accountSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: 'C账户信息变更列表',
	    items:[accountChangedPanel]
   }); 
	
	var serviceSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: 'D服务信息变更列表',
	    items:[serviceInfoChangedPanel]
   }); 
	
	var nameSetGrid = new Ext.form.FieldSet({
		 animCollapse :true,
		 collapsible:true,
		 title: 'F户名变更',
		 items:[nameChangedPanel]
	});
	
	//加载所有 FormPanel
	var basicPanel = new Ext.Panel({
		layout : 'form',
		autoScroll : true,
		items : [custSetGrid,accountSetGrid,serviceSetGrid,nameSetGrid]
	});

	var EchainPanel = new Mis.Echain.EchainPanel({
		instanceID : instanceid,
		nodeId : nodeid,
		nodeName : curNodeObj.nodeName,
		fOpinionFlag : curNodeObj.fOpinionFlag,
		approvalHistoryFlag : curNodeObj.approvalHistoryFlag,
		WindowIdclode : curNodeObj.windowid,
		callbackCustomFun : '3_a10##1'
	});
	var view = new Ext.Panel( {
		renderTo : 'viewEChian',
		frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [custFieldSetGrid,basicPanel,EchainPanel]

	});
});