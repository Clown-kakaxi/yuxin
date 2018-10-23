Ext.onReady(function() {
	//客户状态
	  var custStateStore  = new Ext.data.Store({
			restful:true,
			autoLoad:true,
			proxy:new Ext.data.HttpProxy({
				url: basepath+'/lookup.json?name=ABC0100020'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	//存款余额类型1
	var depositType1Store = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','单位活期存款'],['2','单位定期存款'],['3','单位保证金存款']]
	});
	//存款余额类型2A
	var depositType2AStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','单位定期通知存款'],['2','单位定期3个月及以下存款'],['3','单位定期6个月存款'],['4','单位定期1年存款'],
	          ['5','单位定期2年存款'],['6','单位定期3年存款'],['7','单位定期5年及以上存款']]
	});
	//存款余额类型2B
	var depositType2BStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','单位活期保证金存款'],['2','单位定期保证金存款']]
	});
	//存款余额类型3
	var depositType3Store = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','单位定期3个月及以下保证金存款'],['2','单位定期6个月保证金存款'],['3','单位定期1年保证金存款'],['4','单位定期2年保证金存款'],
	          ['5','单位定期3年保证金存款'],['6','单位定期5年及以上保证金存款']]
	});

/**********************************************************/

	var qForm = new Ext.form.FormPanel({
		id : "qfrom",
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		 title: "客户管理->客户信息检索->存款业务信息检索->存款类型检索", 
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
   				layout : 'column',
   				border : false,
   	           items :[{
   	        	   columnWidth : .25,
   	        	   layout : 'form',
   	        	   border : false,
//   	        	   labelWidth : 120,
					items : [ {
						name:'startDate',
						fieldLabel:'数据日期',
						xtype:'datefield',
						value:' ',
						format:'Y-m-d',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						anchor:'95%'
					},
					{
						xtype : 'combo',
						store : depositType1Store,
						fieldLabel : '存款余额类型一',
						name : 'custType',
						id:'depositType1',
						hiddenName : 'cust_type',
						valueField : 'key',
						displayField : 'value',
						triggerAction : 'all',
						mode:'local',
						editable : false,
						emptyText : '请选择',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					  } ,
					  {	
							xtype : 'combo',
							store : depositType2AStore, 
							fieldLabel : '存款余额类型二',
							name : 'acctType',
							id:'depositType2a',
							hidden:true,
							hiddenName : 'acct_type',
							valueField : 'key',
							displayField : 'value',
							triggerAction : 'all',
							mode:'local',
							editable : false,
							emptyText : '请选择',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						  },
						  {	
								xtype : 'combo',
								store : depositType2BStore, 
								fieldLabel : '存款余额类型二',
								name : 'acctType',
								id:'depositType2b',
								hidden:true,
								hiddenName : 'acct_type',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								mode:'local',
								editable : false,
								emptyText : '请选择',
								labelStyle : 'text-align:right;',
								anchor : '95%'
							  },
					  {	
							xtype : 'combo',
							store : depositType3Store, 
							fieldLabel : '存款余额类型三',
							name : 'acctType',
							id:'depositType3',
							hidden:true,
							hiddenName : 'acct_type',
							valueField : 'key',
							displayField : 'value',
							triggerAction : 'all',
							mode:'local',
							editable : false,
							emptyText : '请选择',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						  }	  
					]
				   },  {
					layout : 'form',
					columnWidth : .25,
//					labelWidth : 120,
					items : [
					{
							xtype : 'combo',
							store : custStateStore,
							fieldLabel : '客户状态',
							id : 'custState',
							hiddenName : 'cust_type',
							triggerAction : 'all',
							valueField : 'key',
							displayField : 'value',
							editable : false,
							allowBlank:false,
							value:'',
							mode:'local',
							emptyText : '请选择',
							mode : 'local',
							forceSelection : true,
							labelStyle : 'text-align:right;',
					//		readOnly : true,
							anchor : '95%'
						},
						{
							xtype : 'combo',
							store : depositType1Store,
							fieldLabel : '存款日均类型一',
							name : 'custType',
							id:'depositDayType1',
							hiddenName : 'cust_type',
							valueField : 'key',
							displayField : 'value',
							triggerAction : 'all',
							mode:'local',
							editable : false,
							emptyText : '请选择',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						  } ,
						  {	
								xtype : 'combo',
								store : depositType2AStore, 
								fieldLabel : '存款日均类型二',
								name : 'acctType',
								id:'depositDayType2a',
								hidden:true,
								hiddenName : 'acct_type',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								mode:'local',
								editable : false,
								emptyText : '请选择',
								labelStyle : 'text-align:right;',
								anchor : '95%'
							  },
							  {	
									xtype : 'combo',
									store : depositType2BStore, 
									fieldLabel : '存款日均类型二',
									name : 'acctType',
									id:'depositDayType2b',
									hidden:true,
									hiddenName : 'acct_type',
									valueField : 'key',
									displayField : 'value',
									triggerAction : 'all',
									mode:'local',
									editable : false,
									emptyText : '请选择',
									labelStyle : 'text-align:right;',
									anchor : '95%'
								  },
						  {	
								xtype : 'combo',
								store : depositType3Store, 
								fieldLabel : '存款日均类型三',
								name : 'acctType',
								id:'depositDayType3',
								hidden:true,
								hiddenName : 'acct_type',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								mode:'local',
								editable : false,
								emptyText : '请选择',
								labelStyle : 'text-align:right;',
								anchor : '95%'
							  }	
					]
					},{
						layout : 'form',
						columnWidth : .25,
//						labelWidth : 120,
						items : [
					         new Com.yucheng.bcrm.common.OrgField({
									searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
									fieldLabel : '所属机构',
									labelStyle : 'text-align:right;',
									id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
									name : 'CUST_ORG', 
									hiddenName: 'instncode',   //后台获取的参数名称
									anchor : '95%',
									checkBox:true //复选标志
								}) 
//							{
//								xtype : 'textfield',
//								fieldLabel : '所属机构',
//								hidden : true,
//								name:'instncode',
//								value:' '
//							},instnCombo6

						]
	           }],
        buttonAlign : 'center',
		buttons : [
				{
					text : '查询',
					handler : function() {
				
					if(!qForm.getForm().isValid()){
						Ext.Msg.alert("提醒","请填写必填项");
						return false;
					}
					select();
//						report4ShowWindow.show();
						var parameters = qForm.getForm()
								.getValues(false);
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
						deposit2a.hide();
						deposit2b.hide();
						deposit3.hide();
						Ext.getCmp("CUST_ORG").setValue('');
					}
				} ] 	     
	});			
		function select(){
			var start = qForm.getForm().findField('startDate').getValue();  //时间
			var customerStatus = Ext.getCmp("custState").getValue();   //客户状态
			var org_diString = Ext.getCmp("CUST_ORG").getValue();//机构
			//存款余额类型
			var deposit1 = Ext.getCmp("depositType1").getValue();
			var deposit2a = Ext.getCmp("depositType2a").getValue();
			var deposit2b = Ext.getCmp("depositType2b").getValue();
			var deposit3 = Ext.getCmp("depositType3").getValue();
			//日均
			var dayDeposit1  = Ext.getCmp("depositDayType1").getValue();
			var dayDeposit2a = Ext.getCmp("depositDayType2a").getValue();
			var dayDeposit2b = Ext.getCmp("depositDayType2b").getValue();
			var dayDeposit3  = Ext.getCmp("depositDayType3").getValue();
		    
		    var bDate=Ext.util.Format.date(start,'Y-m-d');
		    if(start=='') bDate='2012-08-26';
		    if(customerStatus=='') customerStatus=1;
		    if(org_diString=='') org_diString='211111';
		    
		    
		    var searchType=0;
		    
		    if(deposit1==1 || dayDeposit1==1)  searchType=11;
		    	
				var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				
				if(customerStatus==1){
					var url=basepath+'/reportJsp/showReport.jsp?raq=/A1.raq&etldate='+bDate+'&uid='+__units+'&org_id='+org_diString+'&cust_state=0';	
				}
				else if(customerStatus==2){
					var url=basepath+'/reportJsp/showReport.jsp?raq=/A1.raq&etldate='+bDate+'&uid='+__units+'&org_id='+org_diString+'&cust_state=1';
				}
				else
				{
					var url=basepath+'/reportJsp/showReport.jsp?raq=/A1.raq&etldate='+bDate+'&uid='+__units+'&org_id='+org_diString+'&cust_state=2';
				}
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}

	// 布局模型
	var viewport = new Ext.Viewport({
		layout:'fit',
				items: [qForm] 

			});
	
	 /***####################################下拉框联动###########################################***/
	var deposit1 = Ext.getCmp('depositType1');
	var deposit2a = Ext.getCmp('depositType2a');
	var deposit2b = Ext.getCmp('depositType2b');
	var deposit3 = Ext.getCmp('depositType3');
	
	deposit1.on('select',function(){
		if(deposit1.getValue()==2){
			deposit2a.show();
			deposit2b.hide();
			deposit3.hide();
		}
		else if(deposit1.getValue()==3){
			deposit2a.hide();
			deposit2b.show();
		}
		else
		{
			deposit2a.hide();
			deposit2b.hide();
		}
		
		deposit1.triggerBlur();
		
		deposit1.on('change',function(){//1改变或清空时的操作
			deposit2a.setValue('');
			deposit2b.setValue('');
			deposit3.setValue('');
		   	if(deposit1.getValue()==''||deposit1.getValue()==1){
		   		deposit2a.hide();
		   		deposit2b.hide();
		   		deposit3.hide();
		   	}
		   });
	});
	
	deposit2b.on('select',function(){
		if(deposit2b.getValue()==2){
			deposit3.show();
		}
		else
		{
			deposit3.hide();
		}
		
		deposit2b.triggerBlur();
		
		deposit2b.on('change',function(){//1改变或清空时的操作
			deposit3.setValue('');
		   	if(deposit2b.getValue()==''||deposit2b.getValue()==null){
		   		deposit3.hide();
		   	}
		   });
	});
	
	//日均存款类型下拉级联
	var depositDay1 = Ext.getCmp('depositDayType1');
	var depositDay2a = Ext.getCmp('depositDayType2a');
	var depositDay2b = Ext.getCmp('depositDayType2b');
	var depositDay3 = Ext.getCmp('depositDayType3');
	
	depositDay1.on('select',function(){
		if(depositDay1.getValue()==2){
			depositDay2a.show();
			depositDay2b.hide();
			depositDay3.hide();
		}
		else if(depositDay1.getValue()==3){
			depositDay2a.hide();
			depositDay2b.show();
		}
		else
		{
			depositDay2a.hide();
			depositDay2b.hide();
			depositDay3.hide();
		}
		
		depositDay1.triggerBlur();
		
		depositDay1.on('change',function(){//1改变或清空时的操作
			depositDay2a.setValue('');
			depositDay2b.setValue('');
			depositDay3.setValue('');
		   	if(depositDay1.getValue()==''||depositDay1.getValue()==1){
		   		depositDay2a.hide();
		   		depositDay2b.hide();
		   		depositDay3.hide();
		   	}
		   });
	});
	
	depositDay2b.on('select',function(){
		if(depositDay2b.getValue()==2){
			depositDay3.show();
		}
		
		else
		{
			depositDay3.hide();
		}
		
		depositDay2b.triggerBlur();
		
		depositDay2b.on('change',function(){//1改变或清空时的操作
			depositDay3.setValue('');
		   	if(depositDay2b.getValue()==''||depositDay2b.getValue()==null){
		   		depositDay3.hide();
		   	}
		   });
	});
   /****#############################################################################****/
	
	
}); 