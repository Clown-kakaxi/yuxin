/**
 * @description 个人账户新增/变更/销户申请书
 * @author denghj
 * @since 2015-11-24
 */
	Ext.QuickTips.init();
	//自定义全局变量
	var _this = this;
    _this.resId = JsContext._resId;
    var resIdArr = this.resId.split('-');
	var _custId = resIdArr[2].split('$')[1];
	var _sysCurrDate = new Date().format('Y-m-d');
/**
 * 定义所需要的Store或JsonReader
 */	
	//证件类型
	var cardTypeStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
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
	
	//客户基本信息JsonReader
	var privateBaseInfoReader = new Ext.data.JsonReader( {
		root : 'json.data',
		totalProperty : 'json.count'
	}, [{name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'IDENT_TYPE'},
	    {name:'IDENT_ID'},
	    {name:'IDENT_NO'}]);
	
	//客户信息变更JsonReader
	var custChangedInfoReader = new Ext.data.JsonReader({
		root : 'json.data',
		totalProperty : 'json.count'
	}, [{name:'CUST_ID'},
	    {name:'PERSONAL_NAME'},//客户名称
	    {name:'PINYIN_NAME'},//姓名拼音
	    {name:'GENDER'},//性别
	    {name:'IDENT_EXPIRED_DATE'},//证件有效期
	    {name:'BIRTHDAY'},//出身日期
	    {name:'CITIZENSHIP'},//国籍
	    {name:'BIRTHLOCALE'},//出生地
//	    {name:'MOBILE_PHONE'},//移动电话
	    {name:'CONTMETH_INFO_SJ'},//移动电话 
	    {name:'CONTMETH_INFO_SJ_ID'},//移动电话 ID
	    {name:'CONTMETH_INFO_YD'},//联系手机号码
	    {name:'CONTMETH_INFO_YD_ID'},//联系手机号码 ID
	    {name:'CONTMETH_INFO_JT'},//家庭手机号码
	    {name:'CONTMETH_INFO_JT_ID'},//家庭手机号码
	    {name:'HOME_ADDR'},//居住地址
	    {name:'HOME_STYLE'},//居住类型--租赁or自有
	    {name:'HOME_ZIPCODE'},//居住地邮编
	    {name:'HOME_ADDR_ID'},//居住地id
	    {name:'POST_ADDR'},//邮寄地址
	    {name:'POST_ZIPCODE'},//邮寄邮编
	    {name:'POST_ADDR_ID'},//邮寄地id
	    {name:'EMAIL'},//电子邮件E-mail
	    {name:'EMAIL_ID'},//电子邮件E-mailID
	    {name:'IF_UPDATE_MAIL'},//同步更新为电子对账单接收E-mail
	    {name:'PROFESSION_STYLE'},//职业资料 
	    {name:'PROFESSION_STYLE_REMARK'},//职业资料其他说明
	    {name:'UNIT_NAME'},//单位名称
	    {name:'DUTY'},//职位
	    {name:'USA_TAX_FLAG'},//是否为美国纳税人
	    {name:'USA_TAX_IDEN_NO'},//US TIN/SSN
	    {name:'IS_RELATED_BANK'}//是否在我行有关联人IS_RELATED_BANK
	    ]);
	
	//账号变更JsonReader
	var accountChangedReader = new Ext.data.JsonReader({
		root : 'json',
		totalProperty : 'json.count'
	},[{name:'CUST_ID'},
	   {name:'IS_DOMESTIC_CUST'},//客户范围（境内/境外）0:境内    1:境外
	   {name:'ACCOUNT_CONTENTS'},//账户内容
	   {name:'ACCOUNT_NUMBERS'},//账户账号
	   {name:'SERIALNO'},
	   {name:'STATE'}//复核标识
	   ]);
	
	//服务信息变更JsonReader
	var serviceInfoChangedReader = new Ext.data.JsonReader({
		root : 'json.data',
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
	    {name:'CHANGE_NOTICE'},//账务变动通知
	    {name:'SERIALNO'}
	]);
	
/**
 * 定义所需Panel
 */	
  //客户基本信息Panel
	var privateBaseInfoPanel = new Ext.form.FormPanel({
//		reader : privateBaseInfoReader,//加载store
		frame:false,
		autoHeight:true,
		autoWidth:true,
	  	items:[{
	  			layout:'column',
	  			items:[{
	  				layout:'form',
	  				columnWidth : .25,
	  				items:[{xtype:'textfield',fieldLabel:'户名',name:'CUST_NAME',anchor:'90%',cls:'x-readOnly',readOnly:true}]
	  			},{
	  				layout:'form',
	  				columnWidth : .25,
	  				items:[{xtype:'combo',fieldLabel:'证件类型',name:'IDENT_TYPE',anchor:'90%',id:'IDENT_TYPE',cls:'x-readOnly',readOnly:true,
			               store:cardTypeStore,valueField : 'key',displayField : 'value',mode : 'local',
				              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
				              labelStyle:'text-align:right;',selectOnFocus : true}]
	  			},{
	  				layout:'form',
	  				columnWidth : .25,
	  				items:[{xtype:'textfield',fieldLabel:'证件号',name:'IDENT_NO',width:250,anchor:'90%',cls:'x-readOnly',readOnly:true}]
	  			}]
	  	}]
  });

  //A快速关户
	var quickClosePanel = new Ext.form.FormPanel({
		title : '<font color=black><h1>A 快速关户</h1></font>',
		headerStyle:'background-color:transparent;',
		frame : false,
		autoHeight:true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[{xtype: 'displayfield',width:50 },
				   {xtype:'checkbox',boxLabel:'关闭本人所有账户并销户，同时关闭所有已开通服务',name:'QUICK_CLOSED',width:500}]
		}]
	});
  
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
				items:[{xtype:'textfield',fieldLabel:'姓名拼音',name:'PINYIN_NAME',anchor:'90%'},
				       {xtype:'datefield',fieldLabel:'出生日期',name:'BIRTHDAY',format:'Y-m-d',anchor:'90%'}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype : 'combo',fieldLabel:'性别',name:'GENDER',anchor:'90%',
						store:sexStore,valueField : 'key',displayField : 'value',mode : 'local',
						typeAhead : true,forceSelection : true,triggerAction : 'all',
						labelStyle:'text-align:right;',selectOnFocus : true},
				       {xtype:'combo',fieldLabel:'国籍',name:'CITIZENSHIP',anchor:'90%',
						store:citizenshipStore,valueField : 'key',displayField : 'value',mode : 'local',
						typeAhead : true,forceSelection : true,triggerAction : 'all',
						labelStyle:'text-align:right;',selectOnFocus : true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'datefield',fieldLabel:'证件有效期',name:'IDENT_EXPIRED_DATE',format:'Y-m-d',anchor:'90%'},
				       {xtype:'textfield',fieldLabel:'出生地',name:'BIRTHLOCALE',anchor:'90%'}]
			}]
		},{
			layout:'column',
			items : [{ 
					layout:'form',
 				    columnWidth : .25,
		            items:[{xtype:'textfield',fieldLabel:'移动电话',name:'CONTMETH_INFO_SJ',anchor:'90%'}]
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
				items:[{xtype:'combo',fieldLabel:'电话类型',name:'CONTMETH_TYPE_1',value:'联系手机号码',anchor:'90%'},
				       {xtype:'combo',fieldLabel:'电话类型',name:'CONTMETH_TYPE_2',value:'家庭电话',anchor:'90%'}]
			},{
				layout:'form',
				columnWidth : .418,
				items:[{xtype:'textfield',fieldLabel:'电话号码',name:'CONTMETH_INFO_YD',anchor:'90%'},
				       {xtype:'textfield',fieldLabel:'电话号码',name:'CONTMETH_INFO_JT',anchor:'90%'}]
			}]
		},{
			layout:'column',
			items:[{ 
				layout:'form',
				columnWidth : .43,
	            items:[{xtype:'textfield',fieldLabel:'居住地址',name:'HOME_ADDR',anchor:'90%'}]
		},{ 
			layout:'fit',
			columnWidth : .07,
			items:[{xtype:'radiogroup',name:'HOME_STYLE',anchor:'90%',items:[{boxLabel:'租赁',name:'HOME_STYLE',inputValue:1},{boxLabel:'自有',name:'HOME_STYLE',inputValue:2}]}]
		},{ 
			layout:'form',
			columnWidth : .25,
            items:[{xtype:'textfield',fieldLabel:'居住地邮编',name:'HOME_ZIPCODE',anchor:'90%'}]
		}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .5,
				items:[{xtype:'textfield',fieldLabel:'邮寄地址',name:'POST_ADDR',anchor:'90%'}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'邮寄邮编',name:'POST_ZIPCODE',anchor:'90%'}]				    
			}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .58,
				items:[{xtype:'textfield',fieldLabel:'电子邮件E-mail',name:'EMAIL',anchor:'90%'}]
			},{
				layout:'fit',
				columnWidth : .25,
				items:[{xtype:'checkbox',width:200,boxLabel:'同步更新为电子对账单接收E-mail',name:'IF_UPDATE_MAIL',id:'IF_UPDATE_MAIL',anchor:'90%',inputValue:'0',
					 listeners:{
			    		   'blur' : function(){
			    			   var flag1 = custChangedInfoPanel.getForm().getValues()['IF_UPDATE_MAIL'];
			    			   var flag2 = serviceInfoChangedPanel.getForm().getValues()['MAIL'];
			    			   if(flag1 == 'on' && flag2 != ""){
			    				   Ext.getCmp("EMAIL").setValue(flag2);
			    				   Ext.getCmp("MAIL_ADDRESS").setValue(true);
			    				   Ext.getCmp('STATEMENTS').setValue(50);
			    			   }
			    			   if(flag1 != 'on'){
			    				   Ext.getCmp("MAIL_ADDRESS").setValue(false);
			    			   }
			    		   }
			    	   }}]				    
			}]
		},{
			layout:'column',
			items : [{ 
					layout:'form',
 				    columnWidth : .25,
		            items:[{xtype:'displayfield',fieldLabel:'职业资料',anchor:'90%'}]
			}]
		},{
			layout:'column',
			items:[{xtype:'displayfield',width:50},
			       {xtype:'radiogroup',name:'PROFESSION_STYLE',id:'PROFESSION_STYLE',width:520,columns:4,
						items:[{boxLabel:'全员制雇员',name:'PROFESSION_STYLE',inputValue:1},
 	                           {boxLabel:'自雇',name:'PROFESSION_STYLE',inputValue:2},
 	                           {boxLabel:'退休',name:'PROFESSION_STYLE',inputValue:3},
 	                           {boxLabel:'其他（请具体注明）',name:'PROFESSION_STYLE',inputValue:4}],
 	                           listeners:{
 	                        	   'change' : function(){
 	                        		   var flag = custChangedInfoPanel.getForm().getValues()['PROFESSION_STYLE'];
 	                        		   if(flag == '4'){
 	                        			   setFormDisabled(custChangedInfoPanel.getForm(),false,['PROFESSION_STYLE_REMARK']);
 	                        		   }else{
 	                        			   setFormValuenull(custChangedInfoPanel.getForm(),['PROFESSION_STYLE_REMARK']);
 	                        			   setFormDisabled(custChangedInfoPanel.getForm(),true,['PROFESSION_STYLE_REMARK']);
 	                        		   }
 	                        	   }
 	                           }},
			       {xtype:'textfield',name:'PROFESSION_STYLE_REMARK',id:'PROFESSION_STYLE_REMARK',width:200,anchor:'90%'}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'单位名称',name:'UNIT_NAME',anchor:'90%'}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'职位',name:'DUTY',anchor:'90%'}]
			}]
		},{
			layout:'column',
			items:[
					{xtype: 'displayfield',width:20 },			       
					{xtype:'displayfield',value:'是否为美国纳税人：'},
					{xtype:'radiogroup',name:'USA_TAX_FLAG',width:100,columns:2,items:[
					         {boxLabel:'否',name:'USA_TAX_FLAG',inputValue:'N'},
					         {boxLabel:'是',name:'USA_TAX_FLAG',inputValue:'Y'}],
					         listeners:{
					        	 'change' : function(){
					        		 var flag = custChangedInfoPanel.getForm().getValues()['USA_TAX_FLAG'];
					        		 if(flag == 'Y'){
					        			 setFormDisabled(custChangedInfoPanel.getForm(),false,['USA_TAX_IDEN_NO']);
					        		 }else{
					        			 setFormValuenull(custChangedInfoPanel.getForm(),['USA_TAX_IDEN_NO']);
	                        			 setFormDisabled(custChangedInfoPanel.getForm(),true,['USA_TAX_IDEN_NO']);
					        		 }
					        	 }
					         }},
					{xtype:'displayfield',value:'（US TIN/SSN：'},
					{xtype:'textfield',name:'USA_TAX_IDEN_NO',width:200,anchor:'90%'},
					{xtype: 'displayfield',value: '）'}
			]
		},{
			layout:'column',
			items:[
					{xtype: 'displayfield',width:20 },			       
					{xtype:'displayfield',value:'是否在我行有关联人：'},
					{xtype:'radiogroup',name:'IS_RELATED_BANK',width:100,columns:2,items:[{boxLabel:'否',name:'IS_RELATED_BANK',inputValue:'N'},{boxLabel:'是',name:'IS_RELATED_BANK',inputValue:'Y'}]}
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
	   {name:'ACCOUNT_FLAG',mapping:'ACCOUNT_FLAG'},
	   {name:'ACCOUNT_SIGN',mapping:'ACCOUNT_SIGN'}
	   ]);
	
	//境内_一般综合账户
	var jnYbzhCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jnYbzhStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnYbzhStore.load({
		params: {
			custId: _custId,
			contentType: '1'
		}
	});
	
	var jnYbzhGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>一般综合账户</font>',
		headerStyle: 'background-color:transparent;',
		store: jnYbzhStore,
		cm: jnYbzhCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jnYbzhZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jnYbzhGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '1'
				});
				jnYbzhGrid.stopEditing();
				jnYbzhStore.insert(jnYbzhGrid.getStore().data.length,acc);
				jnYbzhGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jnYbzhGrid.getView();
				var index = jnYbzhGrid.getSelectionModel().getSelectedCell();
				var store = jnYbzhGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}
	        }  
		}
	});
	
	//境内_外汇结算户
	var jnWhjsCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jnWhjsStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnWhjsStore.load({
		params: {
			custId: _custId,
			contentType: '2'
		}
	});
	
	var jnWhjsGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>外汇结算户</font>',
		headerStyle:'background-color:transparent;',
		store: jnWhjsStore,
		cm: jnWhjsCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jnWhjsZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jnYbzhGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '2'
				});
				jnWhjsGrid.stopEditing();
				jnWhjsStore.insert(jnWhjsGrid.getStore().data.length,acc);
				jnWhjsGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jnWhjsGrid.getView();
				var index = jnWhjsGrid.getSelectionModel().getSelectedCell();
				var store = jnWhjsGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}
	        }  
		}
	});
	
	//境内_外汇资本金户
	var jnWhzbCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jnWhzbStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnWhzbStore.load({
		params: {
			custId: _custId,
			contentType: '3'
		}
	});
	
	var jnWhzbGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>外汇资本金户</font>',
		headerStyle:'background-color:transparent;',
		store: jnWhzbStore,
		cm: jnWhzbCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jnWhzbZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jnWhzbGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '3'
				});
				jnWhzbGrid.stopEditing();
				jnWhzbStore.insert(jnWhzbGrid.getStore().data.length,acc);
				jnWhzbGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jnWhzbGrid.getView();
				var index = jnWhzbGrid.getSelectionModel().getSelectedCell();
				var store = jnWhzbGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}
	        }  
		}
	});
	
	//境内_外汇境内资产变现账户
	var jnWhbxCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jnWhbxStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jnWhbxStore.load({
		params: {
			custId: _custId,
			contentType: '4'
		}
	});
	
	var jnWhbxGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>外汇境内资产变现账户</font>',
		headerStyle:'background-color:transparent;',
		store: jnWhbxStore,
		cm: jnWhbxCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jnWhbxZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jnWhbxGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '4'
				});
				jnWhbxGrid.stopEditing();
				jnWhbxStore.insert(jnWhbxGrid.getStore().data.length,acc);
				jnWhbxGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jnWhbxGrid.getView();
				var index = jnWhbxGrid.getSelectionModel().getSelectedCell();
				var store = jnWhbxGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}  
	        }  
		}
	});
	
	//境外_一般综合账户
	var jwYbzhCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jwYbzhStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwYbzhStore.load({
		params: {
			custId: _custId,
			contentType: '5'
		}
	});
	
	var jwYbzhGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>一般综合账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwYbzhStore,
		cm: jwYbzhCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jwYbzhZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jwYbzhGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '5'
				});
				jwYbzhGrid.stopEditing();
				jwYbzhStore.insert(jwYbzhGrid.getStore().data.length,acc);
				jwYbzhGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jwYbzhGrid.getView();
				var index = jwYbzhGrid.getSelectionModel().getSelectedCell();
				var store = jwYbzhGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}   
	        }  
		}
	});
	
	//境外_外汇结算户
	var jwWhjsCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jwWhjsStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwWhjsStore.load({
		params: {
			custId: _custId,
			contentType: '6'
		}
	});
	
	var jwWhjsGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>外汇结算户</font>',
		headerStyle:'background-color:transparent;',
		store: jwWhjsStore,
		cm: jwWhjsCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jwWhjsZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jwWhjsGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '6'
				});
				jwWhjsGrid.stopEditing();
				jwWhjsStore.insert(jwWhjsGrid.getStore().data.length,acc);
				jwWhjsGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jwWhjsGrid.getView();
				var index = jwWhjsGrid.getSelectionModel().getSelectedCell();
				var store = jwWhjsGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}   
	        }  
		}
	});
	
	//境外_外汇资本金户
	var jwWhzbCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jwWhzbStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwWhzbStore.load({
		params: {
			custId: _custId,
			contentType: '7'
		}
	});
	
	var jwWhzbGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>外汇资本金户</font>',
		headerStyle:'background-color:transparent;',
		store: jwWhzbStore,
		cm: jwWhzbCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jwWhzbZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jwWhzbGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '7'
				});
				jwWhzbGrid.stopEditing();
				jwWhzbStore.insert(jwWhzbGrid.getStore().data.length,acc);
				jwWhzbGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jwWhzbGrid.getView();
				var index = jwWhzbGrid.getSelectionModel().getSelectedCell();
				var store = jwWhzbGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}  
	        }  
		}
	});
	
	//境外_港澳居民人民币存储账户
	var jwGarmbCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jwGarmbStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwGarmbStore.load({
		params: {
			custId: _custId,
			contentType: '8'
		}
	});
	
	var jwGarmbGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>港澳居民人民币存储账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwGarmbStore,
		cm: jwGarmbCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jwGarmbZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jwGarmbGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '8'
				});
				jwGarmbGrid.stopEditing();
				jwGarmbStore.insert(jwGarmbGrid.getStore().data.length,acc);
				jwGarmbGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jwGarmbGrid.getView();
				var index = jwGarmbGrid.getSelectionModel().getSelectedCell();
				var store = jwGarmbGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}  
	        }  
		}
	});
	
	//境外_人民币前期费用专用结算账户
	var jwRmbqqfyCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jwRmbqqfyStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwRmbqqfyStore.load({
		params: {
			custId: _custId,
			contentType: '9'
		}
	});
	
	var jwRmbqqfyGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>人民币前期费用专用结算账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwRmbqqfyStore,
		cm: jwRmbqqfyCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jwRmbqqfyZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jwRmbqqfyGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '9'
				});
				jwRmbqqfyGrid.stopEditing();
				jwRmbqqfyStore.insert(jwRmbqqfyGrid.getStore().data.length,acc);
				jwRmbqqfyGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jwRmbqqfyGrid.getView();
				var index = jwRmbqqfyGrid.getSelectionModel().getSelectedCell();
				var store = jwRmbqqfyGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				} 
	        }  
		}
	});
	
	//境外_人民币再投资专用结算账户
	var jwRmbztzCm = new Ext.grid.ColumnModel(
			{columns:[{header: '账号',dataIndex: 'ACCOUNT_NUMBERS',width: 500,editor: new Ext.form.TextField({allowBlank: false})},
			          {header: '关闭',dataIndex:'ACCOUNT_FLAG',width: 50,xtype: 'checkcolumn'},
			          {header:'账户',dataIndex:'ACCOUNT_CONTENTS',hidden:true},
			          {header:'标识',dataIndex:'ACCOUNT_SIGN',hidden:true}]}
	);
	
	var jwRmbztzStore = new Ext.data.Store({
		restful: true,
		method : 'get',
		proxy : new Ext.data.HttpProxy({
			url: basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
		reader: accountReader
	});
	
	jwRmbztzStore.load({
		params: {
			custId: _custId,
			contentType: '10'
		}
	});
	
	var jwRmbztzGrid = new Ext.grid.EditorGridPanel({
		title : '<font color=black>人民币再投资专用结算账户</font>',
		headerStyle:'background-color:transparent;',
		store: jwRmbztzStore,
		cm: jwRmbztzCm,
		renderTo: accountChangedPanel,
		width: 550,
		autoHeight: true,
//		autoExpandColumn: 'jwRmbztzZh',
		frame: false,
		tbar: [{
			text: '新增',
			handler: function(){
				var Account = jwRmbztzGrid.getStore().recordType;
				var acc = new Account({
					CUST_ID: _custId,
					ACCOUNT_CONTENTS: '10'
				});
				jwRmbztzGrid.stopEditing();
				jwRmbztzStore.insert(jwRmbztzGrid.getStore().data.length,acc);
				jwRmbztzGrid.startEditing(0,0);
			}
		},{
			text: '删除',
			handler: function(){
				var view = jwRmbztzGrid.getView();
				var index = jwRmbztzGrid.getSelectionModel().getSelectedCell();
				var store = jwRmbztzGrid.getStore();
				if(!index){
					return false;
			    }
				store.remove(store.getAt(index[0]));
				view.refresh();
			}
		}],
		listeners:{
			'beforeedit': function(e){  
				var currRecord = e.record;
				if(e.field == 'ACCOUNT_NUMBERS'){
			         if (currRecord.get("ACCOUNT_SIGN") == "1")  
			            e.cancel = true;  
				}   
	        }  
		}
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
			       {xtype:'checkbox',id:'JN',name:'JN'}]
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
			       {xtype:'checkbox',id:'JW',name:'JW'}]
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
					items:[{xtype:'radiogroup',id:'UKEY',name:'UKEY',columns:2,items:[{boxLabel:'开通',name:'UKEY',inputValue:32},{boxLabel:'关闭',name:'UKEY',inputValue:'-1'}]}]
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
					items:[{xtype:'radiogroup',id:'MESSAGE_CODE',name:'MESSAGE_CODE',columns:2,items:[{boxLabel:'开通',name:'MESSAGE_CODE',inputValue:34},{boxLabel:'关闭',name:'MESSAGE_CODE',inputValue:'-1'}]}]
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
					items:[{xtype:'radiogroup',id:'UKEY_LOST',name:'UKEY_LOST',columns:2,items:[{boxLabel:'开通',name:'UKEY_LOST',inputValue:30},{boxLabel:'关闭',name:'UKEY_LOST',inputValue:'-1'}]}]
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
					items:[{xtype:'radiogroup',id:'MOBILE_BANKING_QUERY',name:'MOBILE_BANKING_QUERY',columns:2,items:[{boxLabel:'开通',name:'MOBILE_BANKING_QUERY',inputValue:42},{boxLabel:'关闭',name:'MOBILE_BANKING_QUERY',inputValue:'-1'}]}]
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
					items:[{xtype:'radiogroup',id:'MOBILE_BANKING_TRADE',name:'MOBILE_BANKING_TRADE',columns:2,items:[{boxLabel:'开通',name:'MOBILE_BANKING_TRADE',inputValue:44},{boxLabel:'关闭',name:'MOBILE_BANKING_TRADE',inputValue:'-1'}]}]
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
				items:[{xtype:'radiogroup',id:'PHONE_BANKING',name:'PHONE_BANKING',columns:2,items:[{boxLabel:'开通',name:'PHONE_BANKING',inputValue:38},{boxLabel:'关闭',name:'PHONE_BANKING',inputValue:'-1'}]}]
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
			       {xtype:'textfield',width:220,id:'FAX_NUMBER',name:'FAX_NUMBER',anchor:'90%'},
			       {xtype:'displayfield',width:359},
			       {xtype:'radiogroup',id:'TRANSACTION_SERVICE',name:'TRANSACTION_SERVICE',width:100,columns:2,items:[{boxLabel:'开通',name:'TRANSACTION_SERVICE',inputValue:60},{boxLabel:'关闭',name:'TRANSACTION_SERVICE',inputValue:'-1'}],
			    	   listeners:{
			    		   'change' : function(){
			    			   var flag = serviceInfoChangedPanel.getForm().getValues()['TRANSACTION_SERVICE'];
			    			   if(flag == '60'){
			    				   setFormDisabled(serviceInfoChangedPanel.getForm(),false,['FAX_NUMBER']);
			    				   //默认开通电子对账单服务
			    				   serviceInfoChangedPanel.getForm().findField('STATEMENTS').setValue('50');
			    			   }else{
			    				   setFormDisabled(serviceInfoChangedPanel.getForm(),true,['FAX_NUMBER']);
			    				   setFormValuenull(serviceInfoChangedPanel.getForm(),['FAX_NUMBER']);
			    			   }
			    		   }
			    	   }}
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
			       {xtype:'checkbox',width:80,boxLabel:'同电子邮件',id:'MAIL_ADDRESS',name:'MAIL_ADDRESS',anchor:'90%'},
			       {xtype:'displayfield',value:'）'},
			       {xtype:'textfield',width:220,id:'MAIL',name:'MAIL',vtype:'email',anchor:'90%'},
			       {xtype:'displayfield',width:269},
			       {xtype:'radiogroup',id:'STATEMENTS',name:'STATEMENTS',width:100,columns:2,items:[{boxLabel:'开通',name:'STATEMENTS',inputValue:50},{boxLabel:'关闭',name:'STATEMENTS',inputValue:'-1'}],
			    	   listeners:{
			    		   'change' : function(){
			    			   var flag = serviceInfoChangedPanel.getForm().getValues()['STATEMENTS'];
			    			   if(flag == '50'){
			    				   setFormDisabled(serviceInfoChangedPanel.getForm(),false,['MAIL_ADDRESS']);
			    				   setFormDisabled(serviceInfoChangedPanel.getForm(),false,['MAIL']);
			    			   }else{
			    				   setFormDisabled(serviceInfoChangedPanel.getForm(),true,['MAIL_ADDRESS']);
			    				   setFormDisabled(serviceInfoChangedPanel.getForm(),true,['MAIL']);
			    				   setFormValuenull(serviceInfoChangedPanel.getForm(),['MAIL_ADDRESS']);
			    				   setFormValuenull(serviceInfoChangedPanel.getForm(),['MAIL']);
			    			   }
			    		   }
			    	   }}
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
				items:[{xtype:'radiogroup',id:'CHANGE_NOTICE',name:'CHANGE_NOTICE',columns:2,items:[{boxLabel:'开通',name:'CHANGE_NOTICE',inputValue:65},{boxLabel:'关闭',name:'CHANGE_NOTICE',inputValue:'-1'}]}]
			}]
		}]
	});
	
	//E 密码重置
	var passwordResetPanel = new Ext.form.FormPanel({
		title : '<font color=black><h1>E 密码重置</h1></font>',
		headerStyle:'background-color:transparent;',
		frame : false,
		autoHeight:true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[
			       {xtype:'displayfield',width:50},
			       {xtype:'checkboxgroup',width:450,name:'PASS_RESET',columns:3,items:[{boxLabel:'账户密码',name:'PASS_RESET',inputValue:1},{boxLabel:'网络/手机银行密码',name:'PASS_RESET',inputValue:2},{boxLabel:'电话银行密码',name:'PASS_RESET',inputValue:3}]}
			]
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
		       {xtype:'checkbox',width:400,boxLabel:'兹因变更姓名，本人拟将在贵行所开立之户名称予以以下更改',name:'CHANGE_NAME',id:'CHANGE_NAME',anchor:'90%',
		    	   listeners:{
		    		   'change' : function(){
		    			   var flag = nameChangedPanel.getForm().getValues()['CHANGE_NAME'];
		    			   if(flag != 'on'){
		    				   setFormDisabled(nameChangedPanel.getForm(),true,['PERSONAL_NEW_NAME']);
		    				   setFormDisabled(nameChangedPanel.getForm(),true,['PINYIN_NEW_NAME']);
		    				   setFormValuenull(nameChangedPanel.getForm(),['PERSONAL_NEW_NAME']);
		    				   setFormValuenull(nameChangedPanel.getForm(),['PINYIN_NEW_NAME']);
		    			   }
		    		   }
		    	   }}
			]
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'旧户名',name:'PERSONAL_OLD_NAME',anchor:'90%',cls:'x-readOnly',readOnly:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'新户名',name:'PERSONAL_NEW_NAME',anchor:'90%',disabled:true}]
			},{
				layout:'form',
				columnWidth : .25,
				items:[{xtype:'textfield',fieldLabel:'新户名拼音',name:'PINYIN_NEW_NAME',anchor:'90%',disabled:true}]
			}]
		}]
	});
	
	
	//加载所有 FormPanel
	var basicPanel = new Ext.Panel({
	  	title:'<h1>富邦华一银行个人账户新增/变更/销户申请书</h1>',
		layout : 'form',
		autoScroll : true,
//		autoHeight: true,
		items : [privateBaseInfoPanel,quickClosePanel,custChangedInfoPanel,accountChangedPanel,serviceInfoChangedPanel,passwordResetPanel,nameChangedPanel],
		buttonAlign:'center',
		buttons:[{
			text:'打印预览',
			id:'sumbit_print',
			handler:function(){
				Ext.Ajax.request({
					url : basepath + '/privateApplyInfo!judgeExistIn.json',
					method : 'post',
					params : {
						'custId' : _custId
					},
					success : function(response){
						
						printPreview();//调用打印预览方法，跳转至打印预览页面
					},
					failure : function(response){
						 Ext.Msg.alert('提示', '操作失败!');
					}
				});
			}
		},{
			text:'提交',
			id:'sumbit_apply',
			handler:function(){
				Ext.Msg.wait('正在提交，请稍后......','系统提示');
				//提交前判断客户信息是否发生改变，若无变动则禁止提交
				var custChangedInfo = getCustPerModel();
				var accountChangedInfo = getAccountPerModel();
				var serviceChangedInfo = getServicePerModel();
				var addrChangedInfo = getAddrPerModel();
				var contChangedInfo = getContPerModel();
				var identChangedInfo = getIdentPerModel();
				var keyChangedInfo = getKeyPerModel();
				var custAddInfo = getCustAddPerModel();
				
				if(custChangedInfo.length < 1 && accountChangedInfo.length < 1 && serviceChangedInfo.length < 1 && addrChangedInfo.length < 1 
					&& contChangedInfo.length < 1 && identChangedInfo.length < 1 && keyChangedInfo.length < 1 && custAddInfo.length < 1){
					Ext.Msg.alert('提示','信息未作任何修改,不允许提交!');
					return false;
				}
				var custData = custChangedInfoPanel.getForm().getValues();
//				var MOBILE_PHONE = custData.MOBILE_PHONE;
				var CONTMETH_INFO_SJ = custData.CONTMETH_INFO_SJ;
				var CONTMETH_INFO_YD = custData.CONTMETH_INFO_YD;
				var HOME_ZIPCODE = custData.HOME_ZIPCODE;
				var POST_ZIPCODE = custData.POST_ZIPCODE;
				var FAX_NUMBER = serviceInfoChangedPanel.getForm().findField('FAX_NUMBER').getValue();
//				if(!noChinaPer(MOBILE_PHONE,'MOBILE_PHONE')){
//					return false;
//				}
				if(!noChinaPer(CONTMETH_INFO_SJ,'CONTMETH_INFO_SJ')){
					return false;
				}
				if(!noChinaPer(CONTMETH_INFO_YD,'CONTMETH_INFO_YD')){
					return false;
				}
				if(!noChinaPer(HOME_ZIPCODE,'HOME_ZIPCODE')){
					return false;
				}
				if(!noChinaPer(POST_ZIPCODE,'POST_ZIPCODE')){
					return false;
				}
				if(!noChinaPer(FAX_NUMBER,'FAX_NUMBER')){
					return false;
				}
				if(custData.USA_TAX_FLAG == 'Y' && custData.USA_TAX_IDEN_NO == ''){
					Ext.Msg.alert('提示','是否为美国纳税人为“是”，则USTN为必输项！');
					return false;
				}
				if(custData.PROFESSION_STYLE == '4' && custData.PROFESSION_STYLE_REMARK == ''){
					Ext.Msg.alert('提示','职业资料为其他时，请具体注明！');
					return false;
				}
				var _custName = privateBaseInfoPanel.getForm().findField('CUST_NAME').getValue();
				var _identType = Ext.getCmp('IDENT_TYPE').getValue();
				Ext.Ajax.request({
					url : basepath + '/privateApplyInfo!initApplyInfo.json',
					method : 'post',
					params : {
						'custChangedInfo' : Ext.encode(custChangedInfo),
						'accountChangedInfo' : Ext.encode(accountChangedInfo),
						'serviceChangedInfo' : Ext.encode(serviceChangedInfo),
						'addrChangedInfo' : Ext.encode(addrChangedInfo),
						'contChangedInfo' : Ext.encode(contChangedInfo),
						'identChangedInfo' : Ext.encode(identChangedInfo),
						'keyChangedInfo' : Ext.encode(keyChangedInfo),
						'custAddInfo' : Ext.encode(custAddInfo),
						'custId' : _custId,
						'custName' : _custName,
						'identType' : _identType
					},
					success : function(response){
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						window.selectUserListNew(ret,instanceid,currNode,nextNode,function(){
							window.location.reload();
						});//选择下一步办理人
//						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					},
					failure : function(response){
						 Ext.Msg.alert('提示', '操作失败!');
					}
				});
			}
		}]
	});

//----------------------------------------------各Panle做数据读取处理 Begin-----------------------------
	/**
	 * Form,Store加载数据 部分做特殊处理
	 */
	//客户基本信息Panel加载数据
	var privateBaseInfoStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath + '/privateApplyInfo!queryBasicInfo.json',
		}),
		method : 'get',
		reader : privateBaseInfoReader
	});
//	privateBaseInfoPanel.load();
	privateBaseInfoStore.load({
		params : {custId : _custId},
		callback : function(){
	    	if(privateBaseInfoStore.getCount() != 0){
	    		for(var i=0;i<privateBaseInfoStore.getCount();i++){
	    			privateBaseInfoPanel.getForm().loadRecord(privateBaseInfoStore.getAt(i));
	    		}
	    	}
		}
	});
	
	//客户信息变更Panel加载数据
	var custChangedInfoStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath + '/privateApplyInfo!queryCustChangedInfo.json',
		}),
		method : 'get',
		reader : custChangedInfoReader
	});
	custChangedInfoStore.load({
		params : {custId : _custId},
		callback : function(){
	    	if(custChangedInfoStore.getCount() != 0){
	    		for(var i=0;i<custChangedInfoStore.getCount();i++){
	    			custChangedInfoPanel.getForm().loadRecord(custChangedInfoStore.getAt(i));
	    			var data = custChangedInfoStore.getAt(i).data;
	    			nameChangedPanel.getForm().findField('PERSONAL_OLD_NAME').setValue(data.PERSONAL_NAME);
	    		}
	    	}
		}
	});

	//账户信息变更Panel加载数据
	var accountChangedStore = new Ext.data.Store({
		restful:true,
	    proxy : new Ext.data.HttpProxy({
			url:basepath + '/privateApplyInfo!queryAccountChangedInfo.json'
		}),
	    reader: accountChangedReader
	});
	accountChangedStore.load({
		params : {
			custId : _custId
		},
		method : 'GET',
	    callback:function(){
	    	if(accountChangedStore.getCount() != 0){
				for(var i=0;i<accountChangedStore.getCount();i++){
					var data = accountChangedStore.getAt(i).data;
					accountChangedPanel.getForm().loadRecord(accountChangedStore.getAt(i));
					var flag = data.IS_DOMESTIC_CUST;//客户范围（境内/境外）
					if(flag == 0){//境内     将境外设为只读
						Ext.getCmp("JN").setValue(true);
						Ext.getCmp("JW").setValue(false);
					}else if(flag == 1){//境外     将境内设为只读
						Ext.getCmp("JW").setValue(true);
						Ext.getCmp("JN").setValue(false);
					}
		    	}
		    }
		}
	});
	
	//服务信息变更Store加载数据
	var serviceInfoChangedStore = new Ext.data.Store({
		restful:true,
	    proxy : new Ext.data.HttpProxy({
			url:basepath + '/privateApplyInfo!queryServiceChangedInfo.json'
		}),
	    reader: serviceInfoChangedReader
	});
	serviceInfoChangedStore.load({
		params : {
			custId : _custId
		},
		method : 'GET',
	    callback:function(){
	    	if(serviceInfoChangedStore.getCount() != 0){
				for(var i=0;i<serviceInfoChangedStore.getCount();i++){
					var data = serviceInfoChangedStore.getAt(i).data;
					serviceInfoChangedPanel.getForm().loadRecord(serviceInfoChangedStore.getAt(i));
					var flag = data.MAIL_ADDRESS;
					if(flag == '1'){
						Ext.getCmp('MAIL_ADDRESS').setValue(true);
					}
		    	}
			}
		}
	});
	
//----------------------------------------------各Panle做数据读取处理 End-----------------------------	
	
	
//----------------------------------------------对各Panel进行业务逻辑处理  Begin------------------------------------------
	/**
	 * 对账户Panel进行业务逻辑设置
	 * 1.当选择境内客户时，境外客户设为只读，反之亦然
	 */
	
	//选择境内客户时
	Ext.getCmp("JN").on("focus",function(){
		accountChangedPanel.getForm().findField("JW").setValue(false);
	});
	//选择境外客户时
	Ext.getCmp("JW").on("focus",function(){
		accountChangedPanel.getForm().findField("JN").setValue(false);
	});
	
	/**
	 * 监听户名变更，若勾选则允许新户名及新户名拼音
	 */
	Ext.getCmp('CHANGE_NAME').on("focus",function(){
		setFormDisabled(nameChangedPanel.getForm(),false,['PERSONAL_NEW_NAME']);
		setFormDisabled(nameChangedPanel.getForm(),false,['PINYIN_NEW_NAME']);
	});
//	Ext.getCmp('CHANGE_NAME').on("blur",function(){
//		setFormDisabled(nameChangedPanel.getForm(),true,['PERSONAL_NEW_NAME']);
//		setFormDisabled(nameChangedPanel.getForm(),true,['PINYIN_NEW_NAME']);
//	});
	
//----------------------------------------------对各Panel进行业务逻辑处理  End------------------------------------------	

	
//----------------------------------------------对比各Panel变更前后数据变化  Begin--------------------------------------	
	/**
	 * 单独获取地址变更信息
	 * @return allUpdateModelArr
	 */
	var getAddrPerModel = function(){
		var custJson1 = new HashMap();
		if(custChangedInfoStore.getCount() == 0){
			custJson1.add("HOME_ADDR","");custJson1.add("HOME_ZIPCODE","");custJson1.add("HOME_ADDR_ID","");
			custJson1.add("POST_ADDR","");custJson1.add("POST_ZIPCODE","");custJson1.add("POST_ADDR_ID","");
		}else{
			custJsonTemp = custChangedInfoStore.getAt(0).json;
			custJson1.add("HOME_ADDR",custJsonTemp["HOME_ADDR"]);custJson1.add("HOME_ZIPCODE",custJsonTemp["HOME_ZIPCODE"]);custJson1.add("HOME_ADDR_ID",custJsonTemp["HOME_ADDR_ID"]);
			custJson1.add("POST_ADDR",custJsonTemp["POST_ADDR"]);custJson1.add("POST_ZIPCODE",custJsonTemp["POST_ZIPCODE"]);custJson1.add("POST_ADDR_ID",custJsonTemp["POST_ADDR_ID"]);
		}
		var custJson2 = custChangedInfoPanel.getForm().getValues(false);//客户信息变更后
		var tempCustId = _custId;
		
		var allUpdateModelArr = [];
		if(!((custJson1.get("HOME_ADDR") == custJson2["HOME_ADDR"]) || ("" == custJson1.get("HOME_ADDR") && "" == custJson2["HOME_ADDR"]))
			|| !((custJson1.get("HOME_ZIPCODE") == custJson2["HOME_ZIPCODE"]) || ("" == custJson1.get("HOME_ZIPCODE") && "" == custJson2["HOME_ZIPCODE"]))){
			if(custJson1.get("HOME_ADDR_ID") == null){//若原表中无ID则为新增
				var perModel = [];
				addFieldFn(perModel,tempCustId,"","","HOME_ADDR_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"HOME_ADDR_CUST_ID","","1");
				addFieldViewFn(perModel,tempCustId,"","04","HOME_TYPE","","1","04","地址类型");
				if(custJson2["HOME_ZIPCODE"] != "")	
					addFieldViewFn(perModel,tempCustId,"",custJson2["HOME_ZIPCODE"],"HOME_ZIPCODE","","1",custJson2["HOME_ZIPCODE"],"居住地邮编");
				if(custJson2["HOME_ADDR"] != "")	
					addFieldViewFn(perModel,tempCustId,"",custJson2["HOME_ADDR"],"HOME_ADDR","","1",custJson2["HOME_ADDR"],"居住地址地址");
				addFieldFn(perModel,tempCustId,"","CRM","HOME_ADDR_LAST_UPDATE_SYS","","1");
				addFieldFn(perModel,tempCustId,"",JsContext._userId,"HOME_ADDR_LAST_UPDATE_USER","","1");
				addFieldFn(perModel,tempCustId,"",_sysCurrDate,"HOME_ADDR_LAST_UPDATE_TM","","2");
				allUpdateModelArr.push({
					IS_ADD_FLAG : "1",//新增
					permodel : perModel
				});
			}else if(custJson1.get("HOME_ADDR_ID") != null){//若原表中有ID则为更新
				var isActUpdateFlag = false;
				var perModel = [];
				addFieldFn(perModel,tempCustId,custJson1.get("HOME_ADDR_ID"),custJson1.get("HOME_ADDR_ID"),"HOME_ADDR_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"HOME_ADDR_CUST_ID","","1");
					addFieldViewFn(perModel,tempCustId,"04","04","HOME_TYPE","","1","04","地址类型");
				if(custJson2["HOME_ZIPCODE"] != custJson1.get("HOME_ZIPCODE")){
					isActUpdateFlag = true;
					addFieldViewFn(perModel,tempCustId,custJson1.get("HOME_ZIPCODE"),custJson2["HOME_ZIPCODE"],"HOME_ZIPCODE","","1",custJson2["HOME_ZIPCODE"],"居住地邮编");
				}if(custJson2["HOME_ADDR"] != custJson1.get("HOME_ADDR")){
					isActUpdateFlag = true;
					addFieldViewFn(perModel,tempCustId,custJson1.get("HOME_ADDR"),custJson2["HOME_ADDR"],"HOME_ADDR","","1",custJson2["HOME_ADDR"],"居住地址");
				}
				addFieldFn(perModel,tempCustId,"","CRM","HOME_ADDR_LAST_UPDATE_SYS","","1");
				addFieldFn(perModel,tempCustId,"",JsContext._userId,"HOME_ADDR_LAST_UPDATE_USER","","1");
				addFieldFn(perModel,tempCustId,"",_sysCurrDate,"HOME_ADDR_LAST_UPDATE_TM","","2");
				if(isActUpdateFlag){
					allUpdateModelArr.push({
						IS_ADD_FLAG : "0",
						permodel : perModel
					});
				}
			}
		}
			if(!((custJson1.get("POST_ADDR") == custJson2["POST_ADDR"]) || ("" == custJson1.get("POST_ADDR") && "" == custJson2["POST_ADDR"]))
					|| !((custJson1.get("POST_ZIPCODE") == custJson2["POST_ZIPCODE"]) || ("" == custJson1.get("POST_ZIPCODE") && "" == custJson2["POST_ZIPCODE"]))){
				if(custJson1.get("POST_ADDR_ID") == null){//若原表中无ID则为新增
					var perModel = [];
					addFieldFn(perModel,tempCustId,"","","POST_ADDR_ID","1","1");//主键字段
					addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"POST_ADDR_CUST_ID","","1");
					addFieldViewFn(perModel,tempCustId,"","01","POST_TYPE","","1","01","地址类型");
					if(custJson2["POST_ZIPCODE"] != "")	
						addFieldViewFn(perModel,tempCustId,"",custJson2["POST_ZIPCODE"],"POST_ZIPCODE","","1",custJson2["POST_ZIPCODE"],"邮寄邮编");
					if(custJson2["POST_ADDR"] != "")	
						addFieldViewFn(perModel,tempCustId,"",custJson2["POST_ADDR"],"POST_ADDR","","1",custJson2["POST_ADDR"],"邮寄地地址");
					addFieldFn(perModel,tempCustId,"","CRM","POST_ADDR_LAST_UPDATE_SYS","","1");
					addFieldFn(perModel,tempCustId,"",JsContext._userId,"POST_ADDR_LAST_UPDATE_USER","","1");
					addFieldFn(perModel,tempCustId,"",_sysCurrDate,"POST_ADDR_LAST_UPDATE_TM","","2");
					allUpdateModelArr.push({
						IS_ADD_FLAG : "1",//新增
						permodel : perModel
					});
				}else if(custJson1.get("POST_ADDR_ID") != null){//若原表中有ID则为更新
					var isActUpdateFlag = false;
					var perModel = [];
					addFieldFn(perModel,tempCustId,custJson1.get("POST_ADDR_ID"),custJson1.get("POST_ADDR_ID"),"POST_ADDR_ID","1","1");//主键字段
					addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"POST_ADDR_CUST_ID","","1");
					addFieldViewFn(perModel,tempCustId,"01","01","POST_TYPE","","1","01","地址类型");
					if(custJson2["POST_ZIPCODE"] != custJson1.get("POST_ZIPCODE")){
						isActUpdateFlag = true;
						addFieldViewFn(perModel,tempCustId,custJson1.get("POST_ZIPCODE"),custJson2["POST_ZIPCODE"],"POST_ZIPCODE","","1",custJson2["POST_ZIPCODE"],"邮寄邮编");
					}if(custJson2["POST_ADDR"] != custJson1.get("POST_ADDR")){
						isActUpdateFlag = true;
						addFieldViewFn(perModel,tempCustId,custJson1.get("POST_ADDR"),custJson2["POST_ADDR"],"POST_ADDR","","1",custJson2["POST_ADDR"],"邮寄地地址");
					}
					addFieldFn(perModel,tempCustId,"","CRM","POST_ADDR_LAST_UPDATE_SYS","","1");
					addFieldFn(perModel,tempCustId,"",JsContext._userId,"POST_ADDR_LAST_UPDATE_USER","","1");
					addFieldFn(perModel,tempCustId,"",_sysCurrDate,"POST_ADDR_LAST_UPDATE_TM","","2");
					if(isActUpdateFlag){
						allUpdateModelArr.push({
							IS_ADD_FLAG : "0",
							permodel : perModel
						});
					}
				}
			}
			return allUpdateModelArr;
		};
	
	/**
	 * 单独获取联系变更信息
	 * @return allUpdateModelArr
	 */
	var getContPerModel = function(){
		var custJson1 = new HashMap();
		if(custChangedInfoStore.getCount() == 0){
			custJson1.add("EMAIL","");custJson1.add("EMAIL_ID","");
			custJson1.add("CONTMETH_INFO_SJ","");custJson1.add("CONTMETH_INFO_SJ_ID","");
			custJson1.add("CONTMETH_INFO_YD","");custJson1.add("CONTMETH_INFO_YD_ID","");
			custJson1.add("CONTMETH_INFO_JT","");custJson1.add("CONTMETH_INFO_JT_ID","");
		}else{
			custJsonTemp = custChangedInfoStore.getAt(0).json;//客户信息变更前
			custJson1.add("EMAIL",custJsonTemp["EMAIL"]);custJson1.add("EMAIL_ID",custJsonTemp["EMAIL_ID"]);
			custJson1.add("CONTMETH_INFO_SJ",custJsonTemp["CONTMETH_INFO_SJ"]);custJson1.add("CONTMETH_INFO_YD",custJsonTemp["CONTMETH_INFO_YD"]);
			custJson1.add("CONTMETH_INFO_SJ_ID",custJsonTemp["CONTMETH_INFO_SJ_ID"]);custJson1.add("CONTMETH_INFO_YD_ID",custJsonTemp["CONTMETH_INFO_YD_ID"]);
			custJson1.add("CONTMETH_INFO_JT",custJsonTemp["CONTMETH_INFO_JT"]);custJson1.add("CONTMETH_INFO_JT_ID",custJsonTemp["CONTMETH_INFO_JT_ID"]);
		}
		var custJson2 = custChangedInfoPanel.getForm().getValues(false);//客户信息变更后
		var tempCustId = _custId;
		
		var allUpdateModelArr = [];
		if(!((custJson1.get("EMAIL") == custJson2["EMAIL"]) || ("" == custJson1.get("EMAIL") && "" == custJson2["EMAIL"]))){
			if(custJson1.get("EMAIL_ID") == null || custJson1.get("EMAIL_ID") == ""){//若原表中无ID则为新增
				var perModel = [];
				addFieldFn(perModel,tempCustId,"","","EMAIL_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"EMAIL_CUST_ID","","1");
//				addFieldFn(perModel,tempCustId,"","1","STAT","","1");
				addFieldViewFn(perModel,tempCustId,"","500","EMAIL_TYPE","","1","500","联系方式类型");
				if(custJson2["EMAIL"] != "")
					addFieldViewFn(perModel,tempCustId,"",custJson2["EMAIL"],"EMAIL","","1",custJson2["EMAIL"],"电子邮件E-mail");
				addFieldFn(perModel,tempCustId,"","9","EMAIL_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"","1","EMAIL_STATE","","1");
				addFieldFn(perModel,tempCustId,"","CRM","EMAIL_LAST_UPDATE_SYS","","1");
				addFieldFn(perModel,tempCustId,"",JsContext._userId,"EMAIL_LAST_UPDATE_USER","","1");
				addFieldFn(perModel,tempCustId,"",_sysCurrDate,"EMAIL_LAST_UPDATE_TM","","2");
				allUpdateModelArr.push({
					IS_ADD_FLAG : "1",//新增
					permodel : perModel
				});
			}else if(custJson1.get("EMAIL_ID") != null && custJson1.get("EMAIL_ID") != ""){//若原表中有ID则为更新
				var isActUpdateFlag = false;
				var perModel = [];
				addFieldFn(perModel,tempCustId,custJson1.get("EMAIL_ID"),custJson1.get("EMAIL_ID"),"EMAIL_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"EMAIL_CUST_ID","","1");
//				addFieldFn(perModel,tempCustId,"","1","STAT","","1");
				addFieldViewFn(perModel,tempCustId,"500","500","EMAIL_TYPE","","1","500","联系方式类型");
				if(custJson2["EMAIL"] != custJson1.get("EMAIL")){
					isActUpdateFlag = true;
					addFieldViewFn(perModel,tempCustId,custJson1.get("EMAIL"),custJson2["EMAIL"],"EMAIL","","1",custJson2["EMAIL"],"电子邮件E-mail");
				}
				addFieldFn(perModel,tempCustId,"9","9","EMAIL_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"1","1","EMAIL_STATE","","1");
				addFieldFn(perModel,tempCustId,"","CRM","EMAIL_LAST_UPDATE_SYS","","1");
				addFieldFn(perModel,tempCustId,"",JsContext._userId,"EMAIL_LAST_UPDATE_USER","","1");
				addFieldFn(perModel,tempCustId,"",_sysCurrDate,"EMAIL_LAST_UPDATE_TM","","2");
				if(isActUpdateFlag){
					allUpdateModelArr.push({
						IS_ADD_FLAG : "0",//修改
						permodel : perModel
					});
				}
			}
		}
		
		if(!((custJson1.get("CONTMETH_INFO_SJ") == custJson2["CONTMETH_INFO_SJ"]) || ("" == custJson1.get("CONTMETH_INFO_SJ") && "" == custJson2["CONTMETH_INFO_SJ"]))){
			if(custJson1.get("CONTMETH_INFO_SJ_ID") == null || custJson1.get("CONTMETH_INFO_SJ_ID") == ""){//若原表中无ID则为新增
				var perModel = [];
				addFieldFn(perModel,tempCustId,"","","CONTMETH_INFO_SJ_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"CONTMETH_SJ_CUST_ID","","1");
//				addFieldFn(perModel,tempCustId,"","1","STAT","","1");
				addFieldViewFn(perModel,tempCustId,"","102","CONTMETH_INFO_SJ_TYPE","","1","102","联系方式类型");
				if(custJson2["CONTMETH_INFO_SJ"] != "")
					addFieldViewFn(perModel,tempCustId,"",custJson2["CONTMETH_INFO_SJ"],"CONTMETH_INFO_SJ","","1",custJson2["CONTMETH_INFO_SJ"],"移动电话");
				addFieldFn(perModel,tempCustId,"","9","CONTMETH_SJ_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"","1","CONTMETH_SJ_STATE","","1");
				addFieldFn(perModel,tempCustId,"","CRM","CONTMETH_SJ_LAST_UPDATE_SYS","","1");
				addFieldFn(perModel,tempCustId,"",JsContext._userId,"CONTMETH_SJ_LAST_UPDATE_USER","","1");
				addFieldFn(perModel,tempCustId,"",_sysCurrDate,"CONTMETH_SJ_LAST_UPDATE_TM","","2");
				allUpdateModelArr.push({
					IS_ADD_FLAG : "1",//新增
					permodel : perModel
				});
			}else if(custJson1.get("CONTMETH_INFO_SJ_ID") != null && custJson1.get("CONTMETH_INFO_SJ_ID") != ""){//若原表中有ID则为更新
				var isActUpdateFlag = false;
				var perModel = [];
				addFieldFn(perModel,tempCustId,custJson1.get("CONTMETH_INFO_SJ_ID"),custJson1.get("CONTMETH_INFO_SJ_ID"),"CONTMETH_INFO_SJ_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"CONTMETH_SJ_CUST_ID","","1");
//				addFieldFn(perModel,tempCustId,"","1","STAT","","1");
				addFieldViewFn(perModel,tempCustId,"102","102","CONTMETH_INFO_SJ_TYPE","","1","102","联系方式类型");
				if(custJson2["CONTMETH_INFO_SJ"] != custJson1.get("CONTMETH_INFO_SJ")){
					isActUpdateFlag = true;
					addFieldViewFn(perModel,tempCustId,custJson1.get("CONTMETH_INFO_SJ"),custJson2["CONTMETH_INFO_SJ"],"CONTMETH_INFO_SJ","","1",custJson2["CONTMETH_INFO_SJ"],"移动电话");
				}
				addFieldFn(perModel,tempCustId,"9","9","CONTMETH_SJ_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"1","1","CONTMETH_SJ_STATE","","1");
				addFieldFn(perModel,tempCustId,"","CRM","CONTMETH_SJ_LAST_UPDATE_SYS","","1");
				addFieldFn(perModel,tempCustId,"",JsContext._userId,"CONTMETH_SJ_LAST_UPDATE_USER","","1");
				addFieldFn(perModel,tempCustId,"",_sysCurrDate,"CONTMETH_SJ_LAST_UPDATE_TM","","2");
				if(isActUpdateFlag){
					allUpdateModelArr.push({
						IS_ADD_FLAG : "0",
						permodel : perModel
					});
				}
			}
		}
		
		if(!((custJson1.get("CONTMETH_INFO_YD") == custJson2["CONTMETH_INFO_YD"]) || ("" == custJson1.get("CONTMETH_INFO_YD") && "" == custJson2["CONTMETH_INFO_YD"]))){
			if(custJson1.get("CONTMETH_INFO_YD_ID") == null || custJson1.get('CONTMETH_INFO_YD_ID') == ""){//若原表中无ID则为新增
				var perModel = [];
				addFieldFn(perModel,tempCustId,"","","CONTMETH_INFO_YD_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"CONTMETH_YD_CUST_ID","","1");
//				addFieldFn(perModel,tempCustId,"","1","STAT","","1");
				addFieldViewFn(perModel,tempCustId,'','209','CONTMETH_INFO_YD_TYPE','','1','209','联系方式类型');
				if(custJson2['CONTMETH_INFO_YD'] != "")
					addFieldViewFn(perModel,tempCustId,'',custJson2['CONTMETH_INFO_YD'],'CONTMETH_INFO_YD','','1',custJson2['CONTMETH_INFO_YD'],'联系手机号码');
				addFieldFn(perModel,tempCustId,"","9","CONTMETH_YD_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"","1","CONTMETH_YD_STATE","","1");
				addFieldFn(perModel,tempCustId,'','CRM','CONTMETH_YD_LAST_UPDATE_SYS','','1');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'CONTMETH_YD_LAST_UPDATE_USER','','1');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'CONTMETH_YD_LAST_UPDATE_TM','','2');
				allUpdateModelArr.push({
					IS_ADD_FLAG : '1',//新增
					permodel : perModel
				});
			}else if(custJson1.get('CONTMETH_INFO_YD_ID') != null && custJson1.get('CONTMETH_INFO_YD_ID') != ""){//若原表中有ID则为更新
				var isActUpdateFlag = false;
				var perModel = [];
				addFieldFn(perModel,tempCustId,custJson1.get('CONTMETH_INFO_YD_ID'),custJson1.get('CONTMETH_INFO_YD_ID'),'CONTMETH_INFO_YD_ID','1','1');//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'CONTMETH_YD_CUST_ID','','1');
//				addFieldFn(perModel,tempCustId,'','1','STAT','','1');
				addFieldViewFn(perModel,tempCustId,'209','209','CONTMETH_INFO_YD_TYPE','','1','209','联系方式类型');
				if(custJson2['CONTMETH_INFO_YD'] != custJson1.get('CONTMETH_INFO_YD')){
					isActUpdateFlag = true;
					addFieldViewFn(perModel,tempCustId,custJson1.get('CONTMETH_INFO_YD'),custJson2['CONTMETH_INFO_YD'],'CONTMETH_INFO_YD','','1',custJson2['CONTMETH_INFO_YD'],'联系手机号码');
				}
				addFieldFn(perModel,tempCustId,"9","9","CONTMETH_YD_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"1","1","CONTMETH_YD_STATE","","1");
				addFieldFn(perModel,tempCustId,'','CRM','CONTMETH_YD_LAST_UPDATE_SYS','','1');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'CONTMETH_YD_LAST_UPDATE_USER','','1');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'CONTMETH_YD_LAST_UPDATE_TM','','2');
				if(isActUpdateFlag){
					allUpdateModelArr.push({
						IS_ADD_FLAG : '0',
						permodel : perModel
					});
				}
			}
		}
		
		if(!((custJson1.get("CONTMETH_INFO_JT") == custJson2["CONTMETH_INFO_JT"]) || ("" == custJson1.get("CONTMETH_INFO_JT") && "" == custJson2["CONTMETH_INFO_JT"]))){
			if(custJson1.get("CONTMETH_INFO_JT_ID") == null || custJson1.get("CONTMETH_INFO_JT_ID") == ""){//若原表中无ID则为新增
				var perModel = [];
				addFieldFn(perModel,tempCustId,"","","CONTMETH_INFO_JT_ID","1","1");//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"CONTMETH_JT_CUST_ID","","1");
//				addFieldFn(perModel,tempCustId,"","1","STAT","","1");
				addFieldViewFn(perModel,tempCustId,'','2031','CONTMETH_INFO_JT_TYPE','','1','2031','联系方式类型');
				if(custJson2['CONTMETH_INFO_JT'] != "")
					addFieldViewFn(perModel,tempCustId,'',custJson2['CONTMETH_INFO_JT'],'CONTMETH_INFO_JT','','1',custJson2['CONTMETH_INFO_JT'],'家庭电话');
				addFieldFn(perModel,tempCustId,"","9","CONTMETH_JT_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"","1","CONTMETH_JT_STATE","","1");
				addFieldFn(perModel,tempCustId,'','CRM','CONTMETH_JT_LAST_UPDATE_SYS','','1');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'CONTMETH_JT_LAST_UPDATE_USER','','1');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'CONTMETH_JT_LAST_UPDATE_TM','','2');
				allUpdateModelArr.push({
					IS_ADD_FLAG : '1',//新增
					permodel : perModel
				});
			}else if(custJson1.get('CONTMETH_INFO_JT_ID') != null && custJson1.get("CONTMETH_INFO_JT_ID") != ""){//若原表中有ID则为更新
				var isActUpdateFlag = false;
				var perModel = [];
				addFieldFn(perModel,tempCustId,custJson1.get('CONTMETH_INFO_JT_ID'),custJson1.get('CONTMETH_INFO_JT_ID'),'CONTMETH_INFO_JT_ID','1','1');//主键字段
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'CONTMETH_JT_CUST_ID','','1');
//				addFieldFn(perModel,tempCustId,'','1','STAT','','1');
				addFieldViewFn(perModel,tempCustId,'2031','2031','CONTMETH_INFO_JT_TYPE','','1','2031','联系方式类型');
				if(custJson2['CONTMETH_INFO_JT'] != custJson1.get('CONTMETH_INFO_JT')){
					isActUpdateFlag = true;
					addFieldViewFn(perModel,tempCustId,custJson1.get('CONTMETH_INFO_JT'),custJson2['CONTMETH_INFO_JT'],'CONTMETH_INFO_JT','','1',custJson2['CONTMETH_INFO_JT'],'家庭电话');
				}
				addFieldFn(perModel,tempCustId,"9","9","CONTMETH_JT_IS_PRIORI","","1");
				addFieldFn(perModel,tempCustId,"1","1","CONTMETH_JT_STATE","","1");
				addFieldFn(perModel,tempCustId,'','CRM','CONTMETH_JT_LAST_UPDATE_SYS','','1');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'CONTMETH_JT_LAST_UPDATE_USER','','1');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'CONTMETH_JT_LAST_UPDATE_TM','','2');
				if(isActUpdateFlag){
					allUpdateModelArr.push({
						IS_ADD_FLAG : '0',
						permodel : perModel
					});
				}
			}
		}
		
		return allUpdateModelArr;
	};
	
	/**
	 * 单独获取关键标识变更信息
	 * @return perModel
	 */
	var getKeyPerModel = function(){
		if(custChangedInfoStore.getCount() == 0){
			var custJson1 = [];
			custJson1.USA_TAX_FLAG = '';
			custJson1.IS_RELATED_BANK = '';
		}else{
			custJson1 = custChangedInfoStore.getAt(0).json;//客户信息变更前
		}
		var custJson2 = custChangedInfoPanel.getForm().getValues(false);//客户信息变更后
		var tempCustId = _custId;
		var perModel = [];
		
		if(!(custJson1['USA_TAX_FLAG'] == custJson2['USA_TAX_FLAG'] || (custJson1['USA_TAX_FLAG'] == "" && custJson2['USA_TAX_FLAG'] == null))
		|| !(custJson1['IS_RELATED_BANK'] == custJson2['IS_RELATED_BANK'] || (custJson1['IS_RELATED_BANK'] == "" && custJson2['IS_RELATED_BANK'] == null))){
			
			addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'KEYFLAG_CUST_ID','1','1');//主键
			if(!(custJson1['USA_TAX_FLAG'] == custJson2['USA_TAX_FLAG'] || (custJson1['USA_TAX_FLAG'] == '' && custJson2['USA_TAX_FLAG'] == null)))	
				addFieldViewFn(perModel,tempCustId,custJson1['USA_TAX_FLAG'],custJson2['USA_TAX_FLAG'],'USA_TAX_FLAG','','1',custJson2['USA_TAX_FLAG'],'是否为美国纳税人');
			if(!(custJson1['IS_RELATED_BANK'] == custJson2['IS_RELATED_BANK'] || (custJson1['IS_RELATED_BANK'] == '' && custJson2['IS_RELATED_BANK'] == null)))	
				addFieldViewFn(perModel,tempCustId,custJson1['IS_RELATED_BANK'],custJson2['IS_RELATED_BANK'],'IS_RELATED_BANK','','1',custJson2['IS_RELATED_BANK'],'是否在我行有关联人');
			addFieldFn(perModel,tempCustId,'','CRM','KEYFLAG_LAST_UPDATE_SYS','','1');
			addFieldFn(perModel,tempCustId,'',JsContext._userId,'KEYFLAG_LAST_UPDATE_USER','','1');
			addFieldFn(perModel,tempCustId,'',_sysCurrDate,'KEYFLAG_LAST_UPDATE_TM','','2');
		}
		return perModel;
	};
	
	/**
	 * 单独获取证件变更信息
	 * @return perModel
	 */
	var getIdentPerModel = function(){
		if(custChangedInfoStore.getCount() == 0){
			var custJson1 = [];
			custJson1.IDENT_EXPIRED_DATE = '';
		}else{
			custJson1 = custChangedInfoStore.getAt(0).data;//客户信息变更前
		}
		var privateJson = privateBaseInfoStore.getAt(0).data;//客户基本信息
		var custJson2 = custChangedInfoPanel.getForm().getValues(false);//客户信息变更后
		var tempCustId = _custId;
		var perModel = [];
		if(!((custJson1['IDENT_EXPIRED_DATE']==custJson2['IDENT_EXPIRED_DATE']) || (""==custJson1['IDENT_EXPIRED_DATE']&&""==custJson2['IDENT_EXPIRED_DATE']))){
			addKeyFn(perModel,tempCustId,privateJson['IDNET_ID'],"IDENT_ID","证件ID");
//			addFieldFn(perModel,tempCustId,privateJson['IDNET_ID'],privateJson['IDNET_ID'],"IDENT_ID","1","1");//主键
			addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"IDNET_CUST_ID","","1");
			addFieldFn(perModel,tempCustId,privateJson['IDENT_TYPE'],privateJson['IDENT_TYPE'],"IDENT_TYPE","","1");
			addFieldViewFn(perModel,tempCustId,custJson1['IDENT_EXPIRED_DATE'],custJson2['IDENT_EXPIRED_DATE'],"IDENT_EXPIRED_DATE","","2",custJson2['IDENT_EXPIRED_DATE'],'证件有效期');
			addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS','','1');
			addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER','','1');
			addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM','','2');
		};
		return perModel;
	};
	
	/**
	 * 获取客户补充信息变更信息
	 * @return perModel
	 */
	var getCustAddPerModel = function(){
		if(custChangedInfoStore.getCount() == 0){
			var custJson1 = [];
			custJson1.HOME_STYLE = '';
			custJson1.IF_UPDATE_MAIL = '';
			custJson1.PROFESSION_STYLE = '';
			custJson1.PROFESSION_STYLE_REMARK = '';
		}else{
			custJson1 = custChangedInfoStore.getAt(0).data;//客户信息变更前
		}
		
		var custJson2 = custChangedInfoPanel.getForm().getValues();//客户信息变更后
		var tempCustId = _custId;
		var perModel = [];
		var personFlag = true;
		
		if(!(custJson1['HOME_STYLE'] == custJson2['HOME_STYLE'] || (custJson1['HOME_STYLE'] == "" && custJson2['HOME_STYLE'] == null))
			||!(custJson1['IF_UPDATE_MAIL'] == custJson2['IF_UPDATE_MAIL'] || (custJson1['IF_UPDATE_MAIL'] == "" && custJson2['IF_UPDATE_MAIL'] == null))
			||!(custJson1['PROFESSION_STYLE'] == custJson2['PROFESSION_STYLE'] || (custJson1['PROFESSION_STYLE'] == "" && custJson2['PROFESSION_STYLE'] == null))
			||!(custJson1['PROFESSION_STYLE_REMARK'] == custJson2['PROFESSION_STYLE_REMARK'] || (custJson1['PROFESSION_STYLE_REMARK'] == "" && custJson2['PROFESSION_STYLE_REMARK'] == null))){
			
			addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'ADDITIONAL_CUST_ID','1','1');
			if(!(custJson1['HOME_STYLE'] == custJson2['HOME_STYLE'] || (custJson1['HOME_STYLE'] == '' && custJson2['HOME_STYLE'] == null)))	
				addFieldViewFn(perModel,tempCustId,custJson1['HOME_STYLE'],custJson2['HOME_STYLE'],'HOME_STYLE','','1',custJson2['HOME_STYLE'],'居住方式');
			if(!(custJson1['IF_UPDATE_MAIL'] == custJson2['IF_UPDATE_MAIL'] || (custJson1['IF_UPDATE_MAIL'] == '' && custJson2['IF_UPDATE_MAIL'] == null)))	
				addFieldViewFn(perModel,tempCustId,custJson1['IF_UPDATE_MAIL'],custJson2['IF_UPDATE_MAIL'],'IF_UPDATE_MAIL','','1',custJson2['IF_UPDATE_MAIL'],'同步更新为电子对账单接受E-mail');
			if(!(custJson1['PROFESSION_STYLE'] == custJson2['PROFESSION_STYLE'] || (custJson1['PROFESSION_STYLE'] == '' && custJson2['PROFESSION_STYLE'] == null)))	
				addFieldViewFn(perModel,tempCustId,custJson1['PROFESSION_STYLE'],custJson2['PROFESSION_STYLE'],'PROFESSION_STYLE','','1',custJson2['PROFESSION_STYLE'],'职业资料');
			if(!(custJson1['PROFESSION_STYLE_REMARK'] == custJson2['PROFESSION_STYLE_REMARK'] || (custJson1['PROFESSION_STYLE_REMARK'] == '' && custJson2['PROFESSION_STYLE_REMARK'] == null)))	
				addFieldViewFn(perModel,tempCustId,custJson1['PROFESSION_STYLE_REMARK'],custJson2['PROFESSION_STYLE_REMARK'],'PROFESSION_STYLE_REMARK','','1',custJson2['PROFESSION_STYLE_REMARK'],'职业资料补充说明');
			addFieldFn(perModel,tempCustId,'','CRM','ADDITIONAL_LAST_UPDATE_SYS','','1');
			addFieldFn(perModel,tempCustId,'',JsContext._userId,'ADDITIONAL_LAST_UPDATE_USER','','1');
			addFieldFn(perModel,tempCustId,'',_sysCurrDate,'ADDITIONAL_LAST_UPDATE_TM','','2');
		}
		
		return perModel;
	};
	
	/**
	 * 获取客户信息Panel变更信息
	 * @return perModel
	 */
	var getCustPerModel = function(){
		if(custChangedInfoStore.getCount() == 0){
			var custJson1 = [];
			custJson1.PERSONAL_NAME = '';
			custJson1.PINYIN_NAME = '';
			custJson1.GENDER = '';
			custJson1.BIRTHDAY = '';
			custJson1.CITIZENSHIP = '';
			custJson1.BIRTHLOCALE = '';
//			custJson1.MOBILE_PHONE = '';
			custJson1.POST_ZIPCODE = '';
			custJson1.UNIT_NAME = '';
			custJson1.DUTY = '';
			custJson1.USA_TAX_IDEN_NO = '';
		}else{
			var custJson1 = custChangedInfoStore.getAt(0).data;//客户信息变更前
		}
		var custJson2 = custChangedInfoPanel.getForm().getValues();//客户信息变更后
		var nameJson = nameChangedPanel.getForm().getValues();//户名信息变更后
		var tempCustId = _custId;
		var perModel = [];
		var personFlag = false;
		
		for(var key in custJson2){//对客户信息变更前后遍历 找出变更信息
			var custModel = {};
			var custField = custChangedInfoPanel.getForm().findField(key);
			if(key == 'CONTMETH_TYPE_1' || key == 'CONTMETH_TYPE_2' || key == 'HOME_ADDR' || key == 'HOME_ZIPCODE' 
				|| key == 'POST_ADDR' || key == 'POST_ZIPCODE' || key == 'HOME_STYLE' || key == 'IF_UPDATE_MAIL'
				|| key == 'PROFESSION_STYLE' || key == 'PROFESSION_STYLE_REMARK'
				|| key == 'EMAIL' || key == 'CONTMETH_INFO_SJ' || key == 'CONTMETH_INFO_YD' || key == 'CONTMETH_INFO_JT'
				|| key == 'USA_TAX_FLAG' || key == 'IS_RELATED_BANK' || key == 'IDENT_EXPIRED_DATE'){
				continue;
			}
			if(key == 'GENDER' || key == 'CITIZENSHIP'){
				var cust = custField.getValue();
				if(custJson1[key] != cust){
					custModel.custId = tempCustId;
					custModel.updateBeCont = getStoreFieldValue(custField.store,'key',custJson1[key],'value');
					custModel.updateAfCont = cust;
					custModel.updateAfContView = custField.getRawValue();
					custModel.updateItem = custField.fieldLabel;
					custModel.updatePageItemEn = custField.name;
					custModel.fieldType = '1';
					perModel.push(custModel);
					personFlag = true;
				};
			}
//			else if(key == 'PINYIN_NAME'){//判断客户拼音是否发生变化
//				if(nameJson['CHANGE_NAME'] == 'on'){//确定变更户名
//					if(custJson1[key] != nameJson['PINYIN_NEW_NAME']){
//						custModel.custId = tempCustId;
//						custModel.updateBeCont = custJson1[key];
//						custModel.updateAfCont = nameJson['PINYIN_NEW_NAME'];
//						custModel.updateAfContView = nameJson['PINYIN_NEW_NAME'];
//						custModel.updateItem = '新户名拼音';
//						custModel.updatePageItemEn = 'PINYIN_NEW_NAME';
//						custModel.fieldType = '1';
//						perModel.push(custModel);
//						personFlag = true;
//					}
//				}
//			}
			else{
				if(!((custJson1[key]==custJson2[key]) || (""==custJson1[key]&&""==custJson2[key]))){
					var custModel = {};
					custModel.custId = tempCustId;
					custModel.updateBeCont = custJson1[key];
					custModel.updateAfCont = custJson2[key];
					custModel.updateAfContView = custJson2[key];
					custModel.updateItem = custField.fieldLabel;
					//对updateItem作补充
//					if(key == 'MOBILE_PHONE'){//移动电话
//						custModel.updateItem = '移动电话';
//					}
					custModel.updatePageItemEn = custField.name;
					custModel.fieldType = custField.getXType() == 'datefield'?'2':'1';
					perModel.push(custModel);
					personFlag = true;
				};
			};
		};
		
		if(nameJson['CHANGE_NAME'] == 'on'){//确定变更户名
			if(custJson1['PERSONAL_NAME'] != nameJson['PERSONAL_NEW_NAME']){
				custModel.custId = tempCustId;
				custModel.updateBeCont = custJson1['PERSONAL_NAME'];
				custModel.updateAfCont = nameJson['PERSONAL_NEW_NAME'];
				custModel.updateAfContView = nameJson['PERSONAL_NEW_NAME'];
				custModel.updateItem = '新户名';
				custModel.updatePageItemEn = 'PERSONAL_NEW_NAME';
				custModel.fieldType = '1';
				perModel.push(custModel);
				personFlag = true;
			}
		}
		if(personFlag){//添加主键ID，只添加一次
			personFlag = false;
			addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'PERSON_CUST_ID','1','1');
			addFieldFn(perModel,tempCustId,'','CRM','PERSON_LAST_UPDATE_SYS','','1');
			addFieldFn(perModel,tempCustId,'',JsContext._userId,'PERSON_LAST_UPDATE_USER','','1');
			addFieldFn(perModel,tempCustId,'',_sysCurrDate,'PERSON_LAST_UPDATE_TM','','2');
		}
		return perModel;
	};
	
	/**
	 * 获取账户信息Panel变更信息
	 * @return perModel
	 */
	var getAccountPerModel = function(){
		if(accountChangedStore.getCount() != 0){//变更前信息
			var accountJson1 = accountChangedStore.getAt(0).data;
		}else{
			var accountJson1 = [];
			accountJson1.IS_DOMESTIC_CUST = '';
			accountJson1.ACCOUNT_CONTENTS = '';
			accountJson1.ACCOUNT_NUMBERS = '';
			accountJson1.SERIALNO = '';
			accountJson1.STATE = '';
		}
		var accountJson2 = accountChangedPanel.getForm().getValues(false);//账户变更后 针对客户范围
		var jnYbzhGridJson = jnYbzhGrid.getStore();//境内_一般综合客户Json
		var jnWhjsGridJson = jnWhjsGrid.getStore();//境内_外汇结算户
		var jnWhzbGridJson = jnWhzbGrid.getStore();//境内_外汇资本金户
		var jnWhbxGridJson = jnWhbxGrid.getStore();//境内_外汇境内资产变现账户
		var jwYbzhGridJson = jwYbzhGrid.getStore();//境外_一般综合账户
		var jwWhjsGridJson = jwWhjsGrid.getStore();//境外_外汇结算户
		var jwWhzbGridJson = jwWhzbGrid.getStore();//境外_外汇资本金户
		var jwGarmbGridJson = jwGarmbGrid.getStore();//境外_港澳居民人民币存储账户
		var jwRmbqqfyGridJson = jwRmbqqfyGrid.getStore();//境外_人民币前期费用专用结算账户
		var jwRmbztzGridJson = jwRmbztzGrid.getStore();//境外_人民币再投资专用结算账户

		var tempCustId = _custId;
		var perModel = [];
		var accountFlag = false;
		var tempDomestic = ""; // 暂时存放客户范围(境内/外)
		var tempContents = ""; // 暂时存放账户内容
		var tempNumbers = ""; // 暂时存放账户账号
		
		//存放变更后的客户范围
		if(accountJson2['JN']!= undefined && accountJson2['JN'] == 'on'){//境内
			tempDomestic = '0';
		}
		if(accountJson2['JW']!= undefined && accountJson2['JW'] == 'on'){//境外
			tempDomestic = '1';
		}
		
		//定义函数，分解存放账户
		var getAccountContent = function(tempJson){
			if(tempJson.getCount() != 0){
				var data = tempJson.getAt(0).data;//相同账户只存取一条
				if(data.ACCOUNT_FLAG != true && data.ACCOUNT_CONTENTS != ""){//存放账户内容
					if(tempContents == ""){
						tempContents = data.ACCOUNT_CONTENTS;
					}else{
						tempContents += "," + data.ACCOUNT_CONTENTS;
					}
				}			
			}
		}
		//定义函数，分解存放账号
		var getAccountNumber = function(tempJson){
			if(tempJson.getCount() != 0){
				for(var i=0;i<tempJson.getCount();i++){
					var data = tempJson.getAt(i).data;
					if(data.ACCOUNT_FLAG != true && data.ACCOUNT_NUMBERS != ""){//存放账户账号
						if(tempNumbers == ""){
							tempNumbers = data.ACCOUNT_CONTENTS + "-" + data.ACCOUNT_NUMBERS;
						}else{
							tempNumbers += "," + data.ACCOUNT_CONTENTS + "-" + data.ACCOUNT_NUMBERS;
						}
					}
				}
			}
		}
		//依次存放变更后的账户内容
		getAccountContent(jnYbzhGridJson);
		getAccountContent(jnWhjsGridJson);
		getAccountContent(jnWhzbGridJson);
		getAccountContent(jnWhbxGridJson);
		getAccountContent(jwYbzhGridJson);
		getAccountContent(jwWhjsGridJson);
		getAccountContent(jwWhzbGridJson);
		getAccountContent(jwGarmbGridJson);
		getAccountContent(jwRmbqqfyGridJson);
		getAccountContent(jwRmbztzGridJson);
		//依次存放变更后的账户账号
		getAccountNumber(jnYbzhGridJson);
		getAccountNumber(jnWhjsGridJson);
		getAccountNumber(jnWhzbGridJson);
		getAccountNumber(jnWhbxGridJson);
		getAccountNumber(jwYbzhGridJson);
		getAccountNumber(jwWhjsGridJson);
		getAccountNumber(jwWhzbGridJson);
		getAccountNumber(jwGarmbGridJson);
		getAccountNumber(jwRmbqqfyGridJson);
		getAccountNumber(jwRmbztzGridJson);
		
		if(!(accountJson1.IS_DOMESTIC_CUST == tempDomestic || (accountJson1.IS_DOMESTIC_CUST == null && tempDomestic == ""))){
			accountFlag = true;
			addFieldViewFn(perModel,tempCustId,accountJson1.IS_DOMESTIC_CUST,tempDomestic,"IS_DOMESTIC_CUST","","1",tempDomestic,"客户范围");
		}
		if(!(accountJson1.ACCOUNT_CONTENTS == tempContents || (accountJson1.ACCOUNT_CONTENTS == null && tempContents == ""))){
			accountFlag = true;
			addFieldViewFn(perModel,tempCustId,accountJson1.ACCOUNT_CONTENTS,tempContents,"ACCOUNT_CONTENTS","","1",tempContents,"账户内容");
		}	
		if(!(accountJson1.ACCOUNT_NUMBERS == tempNumbers || (accountJson1.ACCOUNT_NUMBERS == null && tempNumbers == ""))){
			accountFlag = true;
			addFieldViewFn(perModel,tempCustId,accountJson1.ACCOUNT_NUMBERS,tempNumbers,"ACCOUNT_NUMBERS","","1",tempNumbers,"账户账号");
		}
		if(accountFlag){//添加主键ID，只添加一次
			accountFlag = false;
			addKeyFn(perModel,tempCustId,accountJson1.SERIALNO,"ACCOUNT_SERIALNO","");
			addFieldFn(perModel,tempCustId,accountJson1.STATE,"0","ACCOUNT_STATE","","1");
			addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"ACCOUNT_CUST_ID","","1");
			addFieldFn(perModel,tempCustId,"",JsContext._userId,"ACCOUNT_LAST_UPDATE_USER","","1");
			addFieldFn(perModel,tempCustId,"",_sysCurrDate,"ACCOUNT_LAST_UPDATE_TM","","2");
		}
		return perModel;
	};
	
	/**
	 * 获取服务信息Panel变更信息
	 * 另：根据变更信息，决定重新设置
	 * @return perModel
	 */
	var getServicePerModel = function(){
		if(serviceInfoChangedStore.getCount() != 0){//服务信息变更前信息
			var serviceJson1 = serviceInfoChangedStore.getAt(0).data;
		}else{
			var serviceJson1 = [];
			serviceJson1.UKEY = '';						//U-key认证
			serviceJson1.MESSAGE_CODE = '';				//短信认证码
			serviceJson1.UKEY_LOST = '';				//U-key挂失			
			serviceJson1.MOBILE_BANKING_QUERY = '';		//手机银行_查询
			serviceJson1.MOBILE_BANKING_TRADE = '';		//手机银行_交易
			serviceJson1.PHONE_BANKING = '';			//电话银行
			serviceJson1.TRANSACTION_SERVICE = '';		//传真交易服务
			serviceJson1.FAX_NUMBER = '';				//传真机号
			serviceJson1.STATEMENTS = '';				//电子对账单
			serviceJson1.MAIL = '';						//E-mail
			serviceJson1.MAIL_ADDRESS = '';				//是否同电邮地址
			serviceJson1.CHANGE_NOTICE = '';			//账务变动通知
			
		}
	
		var serviceJson2 = serviceInfoChangedPanel.getForm().getValues(false);//服务信息变更后
		var tempCustId = _custId;
		var perModel = [];
		var serviceFlag = false;
		
		for(var key in serviceJson2){//对服务信息变更前后遍历   找出变更信息
			if(!((serviceJson1[key]==serviceJson2[key]) || (null==serviceJson1[key]&&""==serviceJson2[key]))){
				
				var serviceModel = {};
				serviceModel.custId = tempCustId;
				serviceModel.updateBeCont = serviceJson1[key];//变更前值
				serviceModel.updateAfCont = serviceJson2[key];//变更后值
				if(key == 'FAX_NUMBER' || key == 'MAIL'){//变更后展示值
					serviceModel.updateAfContView = serviceJson2[key];
				}else if(key == 'MAIL_ADDRESS'){
					serviceModel.updateAfContView = serviceJson2[key] = 'on' ? '同步' : '取消同步';
				}else{
					serviceModel.updateAfContView = serviceJson2[key] != '-1' ? '开通' : '关闭';
				}
				
				//updateItem作补充
				if(key == "UKEY"){//U-key认证
					serviceModel.updateItem = "U-key认证";
				}
				if(key == "MESSAGE_CODE"){//短信认证码
					serviceModel.updateItem = "短信认证码";
				}
				if(key == "UKEY_LOST"){//U-key挂失
					serviceModel.updateItem = "U-key挂失";
				}
				if(key == "MOBILE_BANKING_QUERY"){//查询
					serviceModel.updateItem = "查询";
				}
				if(key == "MOBILE_BANKING_TRADE"){//交易
					serviceModel.updateItem = "交易";
				}
				if(key == "PHONE_BANKING"){//电子银行服务-电话银行
					serviceModel.updateItem = "电子银行服务-电话银行";
				}
				if(key == "TRANSACTION_SERVICE"){//传真交易服务
					serviceModel.updateItem = "传真交易服务";
				}
				if(key == "FAX_NUMBER"){//传真机号
					serviceModel.updateItem = "传真机号";
				}
				if(key == "STATEMENTS"){//电子对账单
					serviceModel.updateItem = "电子对账单";
				}
				if(key == "MAIL"){//E-mail
					serviceModel.updateItem = "E-mail";
				}
				if(key == "MAIL_ADDRESS"){//同电邮地址
					serviceModel.updateItem = "同电邮地址";
					if(serviceJson2[key] == 'on'){
						serviceModel.updateAfCont = '1';
					}
				}
				if(key == "CHANGE_NOTICE"){//账务变动通知
					serviceModel.updateItem = "账务变动通知";
				}
				serviceModel.updatePageItemEn = key;
				serviceModel.fieldType = "1";
				perModel.push(serviceModel);
				serviceFlag = true;
				
			}; 
		}
		//判断网络银行是否发生变化
//		if(serviceJson2['UKEY'] == '32' || serviceJson2['MESSAGE_CODE'] == '34' || serviceJson2['UKEY_LOST'] == '30'){
//			var IS_NT_BANK = '1';
//			if(IS_NT_BANK != serviceJson1['IS_NT_BANK']){
//				serviceFlag = true;
//				addFieldViewFn(perModel,tempCustId,serviceJson1['IS_NT_BANK'],IS_NT_BANK,"IS_NT_BANK","","1","开通","网络银行");
//			}
//		}else{
//			var IS_NT_BANK = '';
//			if(IS_NT_BANK != serviceJson1['IS_NT_BANK']){
//				serviceFlag = true;
//				addFieldViewFn(perModel,tempCustId,serviceJson1['IS_NT_BANK'],IS_NT_BANK,"IS_NT_BANK","","1","关闭","网络银行");
//			}
//		}
//		//判断手机银行手机银行是否发生变化
//		if(serviceJson2['MOBILE_BANKING_QUERY'] == '42' || serviceJson2['MOBILE_BANKING_TRADE'] == '44'){
//			var MOBILE_BANKING = '40';
//			if(MOBILE_BANKING != serviceJson1['MOBILE_BANKING']){
//				serviceFlag = true;
//				addFieldViewFn(perModel,tempCustId,serviceJson1['MOBILE_BANKING'],MOBILE_BANKING,"MOBILE_BANKING","","1","开通","手机银行");
//			}
//		}else{
//			var MOBILE_BANKING = '';
//			if(MOBILE_BANKING != serviceJson1['MOBILE_BANKING']){
//				serviceFlag = true;
//				addFieldViewFn(perModel,tempCustId,serviceJson1['MOBILE_BANKING'],MOBILE_BANKING,"MOBILE_BANKING","","1","关闭","手机银行");
//			}
//		}
		
		if(serviceFlag){//添加主键ID，只添加一次
			serviceFlag = false;
			addKeyFn(perModel,tempCustId,serviceJson1['SERIALNO'],"SERVICE_SERIALNO","");
			addFieldFn(perModel,tempCustId,tempCustId,tempCustId,"SERVICE_CUST_ID","","1");
			addFieldFn(perModel,tempCustId,"",JsContext._userId,"SERVICE_LAST_UPDATE_USER","","1");
			addFieldFn(perModel,tempCustId,"",_sysCurrDate,"SERVICE_LAST_UPDATE_TM","","2");
		}
		
		return perModel;
	};
//----------------------------------------------对比各Panel变更前后数据变化  End--------------------------------------	
	
	
	/**
	 * 实现打印预览跳转并将传送数据
	 */
	var printPreview = function(){
		//获取变更后信息
		var privateBaseInfo = privateBaseInfoPanel.getForm().getValues();      //基本信息
		var custInfo = custChangedInfoPanel.getForm().getValues();             //B 客户信息变更
		var accountInfo = accountChangedPanel.getForm().getValues();           //C 账户变更
		var serviceInfo = serviceInfoChangedPanel.getForm().getValues();   	   //D 服务信息变更
		var passwordInfo =passwordResetPanel.getForm().getValues();		   	   //E 密码重置
		var nameInfo = nameChangedPanel.getForm().getValues();		           //F 户名变更

//		//对边变更前后信息  得出发生变更的字段   暂时未确定如何确定发生变更字段！！！！！！！！！
		var custChangedAll = getCustPerModel();
		var accountChangedAll = getAccountPerModel();
		var serviceChangedAll = getServicePerModel();
		var addrChangedAll = getAddrPerModel();
		var contChangedAll = getContPerModel();
		var identChangedAll = getIdentPerModel();
		var keyChangedAll = getKeyPerModel();
		var custAddAll = getCustAddPerModel();
		
		//------------------------------------------------------获取变更字段开始--------------------------------------------------
		var changedKeys = [];
		for(var i=0;i<custChangedAll.length;i++){
			if(custChangedAll[i].updatePageItemEn != 'PERSON_CUST_ID' && custChangedAll[i].updatePageItemEn != 'PERSON_LAST_UPDATE_SYS' && custChangedAll[i].updatePageItemEn != undefined &&
			custChangedAll[i].updatePageItemEn != 'PERSON_LAST_UPDATE_USER' && custChangedAll[i].updatePageItemEn != 'PERSON_LAST_UPDATE_TM'){
				changedKeys.push(custChangedAll[i].updatePageItemEn);
			}
		}
		
//		for(var i=0;i<accountChangedAll.length;i++){
//			if(accountChangedAll[i].updatePageItemEn != 'ACCOUNT_SERIALNO' && accountChangedAll[i].updatePageItemEn != 'ACCOUNT_STATE' &&
//			accountChangedAll[i].updatePageItemEn != 'ACCOUNT_CUST_ID' && accountChangedAll[i].updatePageItemEn != 'ACCOUNT_LAST_UPDATE_USER' && 
//			accountChangedAll[i].updatePageItemEn != 'ACCOUNT_LAST_UPDATE_TM')
//				changedKeys.push(accountChangedAll[i].updatePageItemEn);
//		}
		
		
		for(var i=0;i<serviceChangedAll.length;i++){
			if(serviceChangedAll[i].updatePageItemEn != 'SERVICE_SERIALNO' && serviceChangedAll[i].updatePageItemEn != 'SERVICE_CUST_ID' && 
			serviceChangedAll[i].updatePageItemEn != 'SERVICE_LAST_UPDATE_USER' && serviceChangedAll[i].updatePageItemEn != 'SERVICE_LAST_UPDATE_TM'){
				changedKeys.push(serviceChangedAll[i].updatePageItemEn);
			}
		}
	
		for(var i=0;i<addrChangedAll.length;i++){
			for(var j=0;j<addrChangedAll[i].permodel.length;j++){
				if(addrChangedAll[i].permodel[j].updatePageItemEn == 'HOME_ADDR' || addrChangedAll[i].permodel[j].updatePageItemEn == 'HOME_ZIPCODE'  
					|| addrChangedAll[i].permodel[j].updatePageItemEn == 'POST_ADDR' || addrChangedAll[i].permodel[j].updatePageItemEn == 'POST_ZIPCODE'){
					changedKeys.push(addrChangedAll[i].permodel[j].updatePageItemEn);
				}
			}
		}
		
		for(var i=0;i<contChangedAll.length;i++){
			for(var j=0;j<contChangedAll[i].permodel.length;j++){
				if(contChangedAll[i].permodel[j].updatePageItemEn == 'EMAIL' || contChangedAll[i].permodel[j].updatePageItemEn == 'CONTMETH_INFO_SJ' 
					|| contChangedAll[i].permodel[j].updatePageItemEn == 'CONTMETH_INFO_YD' || contChangedAll[i].permodel[j].updatePageItemEn == 'CONTMETH_INFO_JT'){
					changedKeys.push(contChangedAll[i].permodel[j].updatePageItemEn);
				}	
			}
		}
		
		for(var i=0;i<identChangedAll.length;i++){
			if(identChangedAll[i].updatePageItemEn == 'IDENT_EXPIRED_DATE'){
				changedKeys.push(identChangedAll[i].updatePageItemEn);
			}
		}
		
		for(var i=0;i<keyChangedAll.length;i++){
			if(keyChangedAll[i].updatePageItemEn == 'USA_TAX_FLAG' || keyChangedAll[i].updatePageItemEn == 'IS_RELATED_BANK'){
				changedKeys.push(keyChangedAll[i].updatePageItemEn);
			}
		}
		
		for(var i=0;i<custAddAll.length;i++){
			if(custAddAll[i].updatePageItemEn == 'HOME_STYLE' || custAddAll[i].updatePageItemEn == 'IF_UPDATE_MAIL' 
				|| custAddAll[i].updatePageItemEn == 'PROFESSION_STYLE' || custAddAll[i].updatePageItemEn == 'PROFESSION_STYLE_REMARK'){
				changedKeys.push(custAddAll[i].updatePageItemEn);
			}
		}
		
		if(nameInfo.PERSONAL_NEW_NAME != undefined){
			changedKeys.push(nameInfo.PERSONAL_NEW_NAME);
		}
		if(nameInfo.PINYIN_NEW_NAME != undefined){
			changedKeys.push(nameInfo.PINYIN_NEW_NAME);
		}
		//------------------------------------------------------获取变更字段结束--------------------------------------------------
		
		//拼接打印页面url
		var turl = '?CUST_NAME='+privateBaseInfo.CUST_NAME+
				   '&IDENT_TYPE='+privateBaseInfo.IDENT_TYPE+
				   '&IDENT_NO='+privateBaseInfo.IDENT_NO+
				   '&PINYIN_NAME='+custInfo.PINYIN_NAME+
				   '&BIRTHDAY='+custInfo.BIRTHDAY+
				   '&GENDER='+custInfo.GENDER+
				   '&BIRTHLOCALE='+custInfo.BIRTHLOCALE+
				   '&CITIZENSHIP='+custInfo.CITIZENSHIP+
				   '&IDENT_EXPIRED_DATE='+custInfo.IDENT_EXPIRED_DATE+
//				   '&MOBILE_PHONE='+custInfo.MOBILE_PHONE+
				   '&CONTMETH_TYPE_1='+custInfo.CONTMETH_TYPE_1+
				   '&CONTMETH_TYPE_2='+custInfo.CONTMETH_TYPE_2+
				   '&CONTMETH_INFO_SJ='+custInfo.CONTMETH_INFO_SJ+
				   '&CONTMETH_INFO_YD='+custInfo.CONTMETH_INFO_YD+
				   '&CONTMETH_INFO_JT='+custInfo.CONTMETH_INFO_JT+
				   '&HOME_ADDR='+custInfo.HOME_ADDR+
				   '&HOME_STYLE='+custInfo.HOME_STYLE+
				   '&HOME_ZIPCODE='+custInfo.HOME_ZIPCODE+
				   '&POST_ADDR='+custInfo.POST_ADDR+
				   '&EMAIL='+custInfo.EMAIL+
				   '&POST_ZIPCODE='+custInfo.POST_ZIPCODE+
				   '&IF_UPDATE_MAIL='+custInfo.IF_UPDATE_MAIL+
				   '&PROFESSION_STYLE='+custInfo.PROFESSION_STYLE+
				   '&PROFESSION_STYLE_REMARK='+(custInfo.PROFESSION_STYLE_REMARK == undefined ? "" : custInfo.PROFESSION_STYLE_REMARK)+
				   '&UNIT_NAME='+(custInfo.UNIT_NAME == undefined ? "" : custInfo.UNIT_NAME)+
				   '&DUTY='+(custInfo.DUTY == undefined ? "" : custInfo.DUTY)+
				   '&USA_TAX_FLAG='+custInfo.USA_TAX_FLAG+
				   '&USA_TAX_IDEN_NO='+(custInfo.USA_TAX_IDEN_NO == undefined ? "" : custInfo.USA_TAX_IDEN_NO)+
				   '&IS_RELATED_BANK='+custInfo.IS_RELATED_BANK+
				   '&JN='+accountInfo.JN+
				   '&JW='+accountInfo.JW+
				   '&UKEY='+serviceInfo.UKEY+
				   '&MESSAGE_CODE='+serviceInfo.MESSAGE_CODE+
				   '&UKEY_LOST='+serviceInfo.UKEY_LOST+
				   '&MOBILE_BANKING_QUERY='+serviceInfo.MOBILE_BANKING_QUERY+
				   '&MOBILE_BANKING_TRADE='+serviceInfo.MOBILE_BANKING_TRADE+
				   '&PHONE_BANKING='+serviceInfo.PHONE_BANKING+
				   '&TRANSACTION_SERVICE='+serviceInfo.TRANSACTION_SERVICE+
				   '&FAX_NUMBER='+serviceInfo.FAX_NUMBER+
				   '&STATEMENTS='+serviceInfo.STATEMENTS+
				   '&MAIL='+serviceInfo.MAIL+
				   '&MAIL_ADDRESS='+serviceInfo.MAIL_ADDRESS+
				   '&CHANGE_NOTICE='+serviceInfo.CHANGE_NOTICE+
				   '&PASS_RESET='+passwordInfo.PASS_RESET+
				   '&CHANGE_NAME='+nameInfo.CHANGE_NAME+
				   '&PERSONAL_OLD_NAME='+(nameInfo.PERSONAL_OLD_NAME == undefined ? "" : nameInfo.PERSONAL_OLD_NAME)+
				   '&PERSONAL_NEW_NAME='+(nameInfo.PERSONAL_NEW_NAME == undefined ? "" : nameInfo.PERSONAL_NEW_NAME)+
				   '&PINYIN_NEW_NAME='+(nameInfo.PINYIN_NEW_NAME == undefined ? "" : nameInfo.PINYIN_NEW_NAME);
				   

		var tempApp = parent._APP ? parent._APP : parent.parent._APP;
		
		Ext.Ajax.request({
			url : basepath + '/privateApplyInfo!sessionForward.json',
			method : 'post',
			params : {
				'changedKeys' : Ext.encode(changedKeys),
			},
			success : function(response){
				tempApp.openWindow({
					name : '打印预览',
					action : basepath
							+ '/contents/pages/wlj/printManager/printCustApplyview.jsp'
							+ turl,
					resId : 'task_print_3',
					id : 'task_print_3',
					serviceObject : false
				});
			},
			failure : function(response){
				Ext.Message.alert("提示","打印页面跳转失败，请重新尝试！");
			}
		});
		
		
	};
	
	/**
	 * 
	 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		id:'viewport',
		layout : 'fit',
		activeItem : 0,
	    items:[basicPanel]
	});
});

/**
 * 页面加载完成后 加载beforeSetAllCustIsUpdate
 * 1.页面数据加载完成后，验证该case是否已经提交审核，若是：则信息锁定不再允许提交修改
 * 2.当职业资料为'其他'时，PROFESSION_STYLE_REMARK可写；否则设为只读
 */
var beforeSetAllCustIsUpdate = function(){
	//判断custId信息是否已提交审核，若是则信息锁定
	Ext.Ajax.request({
    	url : basepath + '/privateApplyInfo!isLocked.json',
        method : 'GET',
        params : {custId: _custId},
        success : function(response){
        	var ret = Ext.decode(response.responseText);
        	if(ret.json.data.length>0 && ret.json.data[0].AUTHOR != ''){
        		showMsgNotification('您暂无修改权限，客户记录已被操作员'+ret.json.data[0].AUTHOR+'锁定！',300000);
        		Ext.getCmp('sumbit_apply').setVisible(false);
        		Ext.getCmp('sumbit_print').setVisible(false);
        	}
        },
        failure : function(response){
        	Ext.getCmp('sumbit_apply').setVisible(false);
        	Ext.getCmp('sumbit_print').setVisible(false);
        }
	});

};
window.onload = beforeSetAllCustIsUpdate;//window加载完成后执行beforeSetAllCustIsUpdate


/**
 * 判断联系类容是否包含中文字符或全角字符
 */
var noChinaPer=function(value,type){
	var flag=true;
	if (/[\u4E00-\u9FA5]/i.test(value)) {
		switch(type){
//			case  'MOBILE_PHONE':
//				Ext.Msg.alert("提示", "移动电话不允许包括中文字符或全角符号");
//				break;
			case  'CONTMETH_INFO_SJ':
				Ext.Msg.alert("提示", "手机电话1不允许包括中文字符或全角符号");
				break;
			case  'CONTMETH_INFO_YD':
				Ext.Msg.alert("提示", "移动电话1不允许包括中文字符或全角符号");
				break;
			case  'HOME_ZIPCODE':
				Ext.Msg.alert("提示", "居住地邮编不允许包括中文字符或全角符号");
				break;
			case  'POST_ZIPCODE':
				Ext.Msg.alert("提示", "邮寄邮编不允许包括中文字符或全角符号");
				break;
			case  'FAX_NUMBER':
				Ext.Msg.alert("提示", "传真机号不允许包括中文字符或全角符号");
				break;
			default:
				break;
		}
		
		flag= false;
	};
	if (value.length > 0) {
		for (var i = value.length - 1; i >= 0; i--) {
			unicode = value.charCodeAt(i);
			if (unicode > 65280 && unicode < 65375) {
				switch(type){
//			        case  'MOBILE_PHONE':
//			        	Ext.Msg.alert("提示", "移动电话不允许包括中文字符或全角符号");
//			        	break;
					case  'CONTMETH_INFO_SJ':
						Ext.Msg.alert("提示", "手机电话1不允许包括中文字符或全角符号");
						break;
					case  'CONTMETH_INFO_YD':
						Ext.Msg.alert("提示", "移动电话1不允许包括中文字符或全角符号");
						break;
					case  'HOME_ZIPCODE':
						Ext.Msg.alert("提示", "居住地邮编不允许包括中文字符或全角符号");
						break;
					case  'POST_ZIPCODE':
						Ext.Msg.alert("提示", "邮寄邮编不允许包括中文字符或全角符号");
						break;
					case  'FAX_NUMBER':
						Ext.Msg.alert("提示", "传真机号不允许包括中文字符或全角符号");
						break;
					default:
						break;
				}
				flag = false;
				break;
			}
		}
	}
	return flag;
};


/**
 * 添加隐藏主键字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} value 主键值
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 */
var addKeyFn = function(perModel,_tempCustId,value,key,fieldLabel){
//	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = value;
	pcbhModel.updateAfCont = value;
	pcbhModel.updateAfContView = value;
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updatePageItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};
/**
 * 添加隐藏字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 */
var addFieldFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValue;
	pcbhModel.updateItem = '';
	pcbhModel.updatePageItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
};

/**
 * 添加变更历史字段可显示
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 * @param {} afterValueView 修改后显示值
 * @param {} updateItem 修改项目
 */
var addFieldViewFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType,afterValueView,updateItem){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValueView;
	pcbhModel.updateItem = updateItem;
	pcbhModel.updatePageItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
};

/**
 * @param store 要遍历的store
 * @param keyField 要比较的字段
 * @param keyValue 要比较的字段的值
 * @param valueField 要获取值的字段
 */
var getStoreFieldValue = function(store,keyField,keyValue,valueField){
	for(var i=0;i<store.getCount();i++){
		if(store.getAt(i).data[keyField] == keyValue){
			return store.getAt(i).data[valueField];
		}
	}
	return keyValue;
};

/**
 * @description 
 * 
 * 设置禁用启用起否示例
 * setFormDisabled(xxxPanel.getForm(),true,[
 *	'EN_NAME','CUST_ID','INOUT_FLAG','VIP_FLAG','PER_CUST_TYPE'
 * ]);
 * 设置可编辑字段
 * @param form formPanel.getForm()  
 * @param arr 数组
 * @param disable true禁用,false表示不禁用
 */
var setFormDisabled = function(form,disable,arr){
	for(var i=0;i<arr.length;i++){
		var tempfield = form.findField(arr[i]);
		tempfield.setDisabled(disable);
		if(disable){
			tempfield.addClass('x-readOnly');
		}else{
			tempfield.removeClass('x-readOnly');
		}
	}
};

/**
 * 设置value为null
 * @param form formPanel.getForm()  
 * @param arr 数组
 */
var setFormValuenull = function(form,arr){
	for(var i=0;i<arr.length;i++){
		var tempField = form.findField(arr[i]);
		tempField.setValue();
	}
};

/**
 * 实现HashMap
 * map.size                 获取map长度
 * map.isEmpty()  			判断Map是否为空
 * map.containsKey(key) 	判断对象中是否包含给定Key
 * map.containsValue(value) 判断对象中是否包含给定的Value 
 * map.add(key,value)   	map中添加数据
 * map.get(key)    			根据给定的Key获得Value
 * map.remove(key) 			根据给定的Key删除一个值 
 */
 function HashMap(){
	var length = 0;  
	//创建一个对象  
    var obj = new Object();  
  
    /** 
    * 判断Map是否为空 
    */  
    this.isEmpty = function(){  
        return length == 0;  
    };  
  
    /** 
    * 判断对象中是否包含给定Key 
    */  
    this.containsKey=function(key){  
        return (key in obj);  
    };  
  
    /** 
    * 判断对象中是否包含给定的Value 
    */  
    this.containsValue=function(value){  
        for(var key in obj){  
            if(obj[key] == value){  
                return true;  
            }  
        }  
        return false;  
    };  
  
    /** 
    *向map中添加数据 
    */  
    this.add=function(key,value){  
        if(!this.containsKey(key)){  
            length++;  
        }  
        obj[key] = value;  
    };  
  
    /** 
    * 根据给定的Key获得Value 
    */  
    this.get=function(key){  
        return this.containsKey(key)?obj[key]:null;  
    };  
  
    /** 
    * 根据给定的Key删除一个值 
    */  
    this.remove=function(key){  
        if(this.containsKey(key)&&(delete obj[key])){  
            length--;  
        }  
    };  
  
    /** 
    * 获得Map中的所有Value 
    */  
    this.getValues=function(){  
        var _values= new Array();  
        for(var key in obj){  
            _values.push(obj[key]);  
        }  
        return _values;  
    };  
  
    /** 
    * 获得Map中的所有Key 
    */  
    this.getKeys=function(){  
        var _keys = new Array();  
        for(var key in obj){  
            _keys.push(key);  
        }  
        return _keys;  
    };  
  
    /** 
    * 获得Map的长度 
    */  
    this.size = function(){  
        return length;  
    };  
  
    /** 
    * 清空Map 
    */  
    this.clear = function(){  
        length = 0;  
        obj = new Object();  
    };  
};


/**
 * 右下角显示提示信息
 * @param {} msg 提示信息
 * @param {} error
 * @param {} hideDelay
 */
var showMsgNotification = function(msg, hideDelay, error) {
	new Ext.ux.Notification( {
		iconCls : 'ico-g-51',
		title : error ? '<font color=red>错误</font>' : '<font color=red>提示</font>',
		html : "<br><font style='font-weight:bold;' color=red text-align=left>" + msg
				+ '</font><br><br>',
		autoDestroy : true,
		plain : false,
		shadow : false,
		draggable : false,
		hideDelay : hideDelay || (error ? 30000 : 30000),
		width : 400
	}).show(document);
};
