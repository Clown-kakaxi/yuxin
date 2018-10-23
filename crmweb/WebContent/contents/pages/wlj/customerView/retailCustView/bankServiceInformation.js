/**
*@description 360客户视图 服务信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
		]);

var custId =_custId;

var needCondition = false;
var url = basepath + '/lookup.json?name=LOVE_INVEST_TYPE';
var fields = [{name:'LIKE_INVEST_TYPE',text:'',hidden :true}];


var cardNoStore =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	/*sortInfo : {
            field:'key',
            direction:'ASC'
        },*/
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/acrmFCiBankService!getCardNo.json?custId='+custId
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
 cardNoStore.load({
 	callback	: function(){
 		Ext.getCmp("CARD_NO").setValue(cardNoStore.getAt(0).data.value);
 	}
 });
  
  
var cardTypeStore =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=CARD_CATLG'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
cardTypeStore.load();
var cardType1Store = new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/oneKeyAccountAction!getCardType.json'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		},['key','value'])
	});
cardType1Store.load();
var cardType2Store  = new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/oneKeyAccountAction!getCardType2.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
cardType2Store.load();

Ext.onReady(function() {

//注：store在客户视图里，设置autoLoad : true表示比form的store.load慢，导致赋值后还是显示编码，故手工调用load()方法
//定义总共有多少个checkboxgroup，
_total_ckg = 1;

var rsRecord = new Ext.data.Record.create([
       {name:'custId',mapping:'CUST_ID'},
       {name:'isCardApply',mapping:'IS_CARD_APPLY'},
       {name:'isAtmHigh',mapping:'IS_ATM_HIGH'},
       {name:'atmHigh',mapping:'ATM_HIGH'},
       {name:'isDftcntAtm',mapping:'IS_DFTCNT_ATM'},
       {name:'lmtcntDAtm',mapping:'LMTCNT_D_ATM'},
       {name:'isDftlmtyAtm',mapping:'IS_DFTLMTY_ATM'},
       {name:'lmtamtYAtm',mapping:'LMTAMT_Y_ATM'},
       {name:'isPosHigh',mapping:'IS_POS_HIGH'},
       {name:'posHigh',mapping:'POS_HIGH'},
       {name:'isElebankSer',mapping:'IS_ELEBANK_SER'},
       {name:'isNtBank',mapping:'IS_NT_BANK'},
       {name:'ukey',mapping:'UKEY'},
       {name:'messageCode',mapping:'MESSAGE_CODE'},
       {name:'mobileBanking',mapping:'MOBILE_BANKING'},
       {name:'isMsgPhone',mapping:'IS_MSG_PHONE'},
       {name:'isDftlmtdEb',mapping:'IS_DFTLMTD_EB'},
       {name:'isDftcntEb',mapping:'IS_DFTCNT_EB'},
       {name:'isDftlmtyEb',mapping:'IS_DFTLMTY_EB'},
       {name:'LmtamtdEb',mapping:'LMTAMT_D_EB'},
       {name:'lmtcntDEb',mapping:'LMTCNT_D_EB'},
       {name:'lmtamtYEb',mapping:'LMTAMT_Y_EB'},
       {name:'statements',mapping:'STATEMENTS'},
       {name:'changeNotice',mapping:'CHANGE_NOTICE'},
       {name:'mailAddress',mapping:'MAIL_ADDRESS'},
       {name:'mail',mapping:'MAIL'},
       {name:'cardNo',mapping:'CARD_NO'},
       {name:'card1',mapping:'CARD1'},
       {name:'card2',mapping:'CARD2'},
       {name:'card3',mapping:'CARD3'}
   ]);
var rsreader = new Ext.data.JsonReader({
	root : 'json.data',
	totalProperty : 'json.count'
}, rsRecord);
  
var opForm = new Ext.form.FormPanel({
	id : 'opForm',
	layout : 'form',
	labelAlign : 'right',
	autoScroll : true,
	frame : true,
	buttonAlign : "center",
	items:[{
		xtype:'fieldset',
	   	title:'银行服务信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
//		anchor : '95%',
		items : [{
				   baseCls:"x-plain",
				   style:'margin:0 10px;',
				   layout:"column",
				   id : 'serviceInfoPanel',
				   name : 'serviceInfoPanel',
				   frame:true,
				   autoScroll:true,//有滚动条
				   items:[{
					 		layout : 'form',
				        	style:'margin:0px 20px 0px 10px;',
				        	width:950,
				        	labelWidth:30,
						    items:[{//卡号
									xtype : 'combo',
									hiddenName :'CARD_NO',
									id : 'CARD_NO',
									fieldLabel : '卡号',
									width : 200,
									store : cardNoStore,
									/*store: new Ext.data.ArrayStore({
										id:0,
										fields:['CARD_NO'],
										data:[['6235655588800087221'],['6235655588800087220']]
									}),*/
									emptyText:'请选择',
									valueField : 'key',
									displayField : 'value',
									mode : 'local', 
									editable:false,
									valueNotFoundText :"该客户尚未开卡",
									autoSelect : true,
									triggerAction : 'all' ,
									listeners:{
											'focus': {  
												   fn: function(e){  
														e.expand();  
														this.doQuery(this.allQuery, true);  
													},  
													buffer:200  
											 },
											 'select':function(combo,record,index){
											 	debugger;
											   		if(this.oldValue==this.value){
											   			return false;
											   		}
											   		store.load({
														params : {
															'custId':custId,
															'cardNo':this.value
														},
														method : 'GET',
													    callback:function(){
													    	setFormValue();
														}
													});
											   		
											   		/*Ext.Ajax.request({
														url : basepath + '/acrmFCiBankService.json',
														method : 'GET',
														params : {
															'custId':custId,
															'cardNo':this.value
														},
														success : function(response) {
															var ret = Ext.decode(response.responseText);
															setFormValue(ret);
														},
														failure : function() {
															Ext.Msg.alert('查询结果', '查询失败');
														}
													});*/
											   	}
									}
								}]
  					 },{
						layout : 'column',
				    	xtype:'fieldset',
				    	id : 'jiejikaFieldset',
				    	border : false,
				    	style:'margin:10px 20px 0px 10px;',
				    	width:950,
					    items:[
					   			{
					            	 layout:"table",
									 anchor:'98%',
									 defaultType:"checkbox",
									 layoutConfig:{columns:2,padding : 10},//将父容器分成2列
					            	 items: [{
											  width:150,
											  boxLabel:"借记卡申请",
											  inputValue:'1',
											  rowspan:7,
											  name:'jiejika',
											  id:'jiejika',
											  listeners:{
												afterrender	: function(){
													this.el.up("div").setStyle("margin","5px 0px");
												},
												'disable' : function(){
														if(this.el){
															this.el.up("div").removeClass("x-item-disabled");
										            		this.el.up("div").setStyle({
										            			color : "#555",
										            			cursor: "default",
										            			opacity: 1
										            		});
														}
									            	}
								  }
							},{
								xtype : 'panel',
								layout:'column',
								id : 'cardTypePanel',
								name :  'cardTypePanel',
								width:800,
								items:[{
										columnWidth:.3,
										layout:'form',
										labelWidth:1,
										width:230,
										items:[
												{
												   xtype : 'combo',
												   hiddenName : 'cardType',
												   id : 'cardType',
												   emptyText:'请选择',
												   width:200,
												   editable:false,
												   store : cardTypeStore,
												   //hidden :true,
												   anchor:"80%",
												   valueField : 'key',
												   displayField : 'value',
												   mode : 'local',
												   valueNotFoundText :"",
												   triggerAction : 'all' 
												}
										       ]
									},
									{
										columnWidth:.3,
										layout:'form',
										labelWidth:1,
										width:230,
										items:[
												{
												   xtype : 'combo',
												   hiddenName : 'cardType1_0',
												   id : 'cardType1_0',
												   width:200,
												   emptyText:'请选择',
												   editable:false,
												   store: cardType1Store,
												   anchor:"80%",
												   valueField : 'key',
												   displayField : 'value',
												   mode : 'local',
												   valueNotFoundText :"",
												   triggerAction : 'all'
										}]
									},
									
									{
										columnWidth:.3,
										layout:'form',
										labelWidth:1,
										width:230,
										items:[
												{
												   xtype : 'combo',
												   hiddenName : 'cardType2_0',
												   id : 'cardType2_0',
												   emptyText:'请选择',
												   editable:false,
												  // hidden:true,
												   width:200,
												   store:cardType2Store,
												   anchor:"80%",
												   valueField : 'key',
												   displayField : 'value',
												   mode : 'local',
												   valueNotFoundText :"",
												   triggerAction : 'all' 
												}
										       ]
									}
								]
							},
							 {
								   xtype:'tbtext',
								   width:1000,
								   id : 'ATM',
								   name : 'ATM',
								   //hidden:true,
								   text:'<br/><b><font style="font-size:14px;">ATM转账限额设置</font></b><br/>'
							 },{
								xtype : 'panel',
								layout:'column',
								id : 'ATMDayLimitPanel',
								//hidden:true,
								name : 'ATMDayLimitPanel',
								items:[{
										columnWidth:.35,	
										layout:'form',
										labelWidth:1,
										items:[{
												  anchor:"90%",
												  id : 'ATMDayLimitDefault',
												  name : 'ATMDayLimitDefault',
												  xtype:'radio',
												  boxLabel:"默认每日累计限额（RMB50,000元）",
												  inputValue:1,
												  checked:true,
												  listeners:{
													  'disable' : function(){
														  	if(this.el){
														  		this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
														  	}
										            	}
											 	 }
											}]
										},
										{
											columnWidth:.22,
											layout:'form',
											labelWidth:1,
											items:[{
												anchor:"98%",
												  xtype:'radio',
												  id : 'ATMDayLimitDefine',
												  name : 'ATMDayLimitDefault',
												  boxLabel:"每日累计转账最高限额RMB",
												  inputValue:0,
												  listeners:{	
													    'disable' : function(){
													    	if(this.el){
													    		this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
													    	}
										            	}
												   }
											}]
										},
										{
											columnWidth:.1,
											layout:'form',
											labelWidth:1,
											items:[{
												anchor:"98%",
												 xtype : 'numberfield',
												 width : '50',
												 id : 'ATMDayLimit',
												 name : 'ATMDayLimit',
												 maxValue:50000 
											}]
										},
										{
												columnWidth:.2,
												layout:'form',
												labelWidth:1,
												items:[{
													 anchor:"90%",
													 xtype:'tbtext',
													 style:"font-size:14px;",
													 text:"<br>元（0-50,000）</br>"
												}]
											}]
			       	        },
			       	        {
			       	        	xtype : 'panel',
								layout:'column',
								id : 'ATMDayCountPanel',
								name : 'ATMDayCountPanel',
								//hidden:true,
								items:[{
										columnWidth:.35,
										layout:'form',
										labelWidth:1,
										items:[
												{
													  anchor:"90%",
													  xtype:'radio',
													  boxLabel:"默认每日累计笔数（10笔）",
													  id : 'ATMDayCountDefault',
													  name : 'ATMDayCountDefault',
													  checked:true,
													  inputValue:1,
													  listeners:{
														  'disable' : function(){
														  		if(this.el){
														  			this.el.up("div").removeClass("x-item-disabled");
												            		this.el.up("div").setStyle({
												            			color : "#555",
												            			cursor: "default",
												            			opacity: 1
												            		});
														  		}
											            	}
												     }
												}
										      ]
								},
								{
									columnWidth:.22,
									layout:'form',
									labelWidth:1,
									items:[
											{
												anchor:"98%",
												  xtype:'radio',
												  boxLabel:"每日累计转账笔数",
												  id : 'ATMDayCountDefine',
												  name : 'ATMDayCountDefault',
												  inputValue:0,
												  listeners:{
													   'disable' : function(){
													   		if(this.el){
													   			this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
													   		}
										            	}
												    }
											}
									       ]
								},
								{
									columnWidth:.1,
									layout:'form',
									labelWidth:1,
									items:[{
										anchor:"98%",
										xtype : 'numberfield',
										 width : '50',
										 id : 'ATMDayLimitCount',
										 name : 'ATMDayLimitCount'
									}]
								},
								{
									columnWidth:.2,
									layout:'form',
									labelWidth:1,
									items:[{
										anchor:"90%",
										xtype:'tbtext',
										text:"<br>笔</br>",
										style:"font-size:14px;"
									}]
								}
			       	        ]
			       	   },
			       	   {
		      	        	xtype : 'panel',
							layout:'column',
							id : 'ATMYearLimitPanel',
							name : 'ATMYearLimitPanel',
							//hidden:true,
							items:[{
									columnWidth:.35,
									layout:'form',
									labelWidth:1,
									items:[{
										  anchor:"90%",
										  xtype:'radio',
										  boxLabel:"默认每年累计限额（RMB10,000,000元）",
										  id : 'ATMYearLimitDefault',
										  name : 'ATMYearLimitDefault',
										  checked:true,
										  inputValue:1,
										  listeners:{
											  'disable' : function(){
											  		if(this.el){
											  			this.el.up("div").removeClass("x-item-disabled");
									            		this.el.up("div").setStyle({
									            			color : "#555",
									            			cursor: "default",
									            			opacity: 1
									            		});
											  		}
								            	}
										  }
										  
									}]
							},
							{
								columnWidth:.22,
								layout:'form',
								labelWidth:1,
								items:[{
									anchor:"98%",
									  xtype:'radio',
									  boxLabel:"每年累计转账最高限额RMB",
									  id : 'ATMYearLimitDefine',
									  name : 'ATMYearLimitDefault',
									  inputValue:0,
									  listeners:{
									        'disable' : function(){
									        		if(this.el){
									        			this.el.up("div").removeClass("x-item-disabled");
									            		this.el.up("div").setStyle({
									            			color : "#555",
									            			cursor: "default",
									            			opacity: 1
									            		});
									        		}
								            	}
							               }
								}]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
									anchor:"98%",
									xtype : 'numberfield',
									 width : '50',
									 id : 'ATMYearLimit',
									 name : 'ATMYearLimit'
								}]
							},
							{
								columnWidth:.2,
								layout:'form',
								labelWidth:1,
								items:[{
									 anchor:"90%",
									 xtype:'tbtext',
									 text:"<br>元</br>",
									 style:"font-size:14px;"
								}]
							
							}]
			       	   },
			       	   	{
						   xtype:'tbtext',
						   width:1000,
						   id : 'POS',
						   name : 'POS',
						   //hidden:true,
		        	       text:"<b><font style='font-size:14px;'>POS消费限额设置</font></b>"
			       	   	},
			       	   	{
		       	        	xtype : 'panel',
							layout:'column',
							id : 'POSPanel',
						    name : 'POSPanel',
						    //hidden:true,
							items:[{
									columnWidth:.35,
									layout:'form',
									labelWidth:1,
									items:[{
										  anchor:"90%",
										  xtype:'radio',
										  checked:true,
										  boxLabel:"默认单笔限额（RMB500,000元）",
										  id : 'POSDefault',
									      name : 'POSDefault',
										  inputValue:1,
										  listeners:{
											  'disable' : function(){
											  		if(this.el){
											  			this.el.up("div").removeClass("x-item-disabled");
									            		this.el.up("div").setStyle({
									            			color : "#555",
									            			cursor: "default",
									            			opacity: 1
									            		});
											  		}
								            	}
										  }
									}]
							},
							{
								columnWidth:.22,
								layout:'form',
								labelWidth:1,
								items:[{
									  anchor:"98%",
									  xtype:'radio',
									  boxLabel:"单笔消费限额RMB",
									  id : 'POSDefine',
									  name : 'POSDefault',
									  inputValue:0,
									  listeners:{
								            'disable' : function(){
								            	if(this.el){
								            		this.el.up("div").removeClass("x-item-disabled");
								            		this.el.up("div").setStyle({
								            			color : "#555",
								            			cursor: "default",
								            			opacity: 1
								            		});
								            	}
							            	}
						               }
								}]
							},{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
									anchor:"98%",
									xtype : 'numberfield',
									 width : '50',
									 id : 'eachCustemLimit',
									 name : 'eachCustemLimit'
								}]
							},
							{
								columnWidth:.2,
								layout:'form',
								labelWidth:1,
								items:[{
										anchor:"90%",
										xtype:'tbtext',
										text:"<br>元</br>",
										style:"font-size:14px;"
									}]
							}]
				       	  }
		            	 ]
		             }
	       	   ]
						
	   },{
	   		layout : 'column',
        	xtype:'fieldset',
        	border:false,
        	id : 'dianziBankFieldset',
        	style:'margin:5px 20px 0px 10px;',
        	width:950,
		    items:[{
	            	 layout:"table",
					 anchor:'98%',
					 defaultType:"checkbox",
					 layoutConfig:{columns:2,padding : 10},//将父容器分成2列
					 items:[{
						  width:150,
						  boxLabel:"电子银行服务",
						  inputValue:'1',
						  rowspan:8,
						  id : 'dianziBank',
						  name:'dianziBank',
						  listeners:{
					  		afterrender	: function(){
								this.el.up("div").setStyle("margin","5px 0px");
							},
							'disable' : function(){
								if(this.el){
									this.el.up("div").removeClass("x-item-disabled");
				            		this.el.up("div").setStyle({
				            			color : "#555",
				            			cursor: "default",
				            			opacity: 1
				            		});
								}
			            	}
					}
				},{
					anchor:"98%",
					xtype:'checkbox',
					style : 'padding-left:0px',
					name : 'netBank',
					//hidden:true,
					id : 'netBank',
					width:800,
					boxLabel:"网络银行（若不选汇款认证方式，则默认只有查询功能）",
					inputValue:'1',
					listeners:{
						   'check':function(obj,isChecked){
							   if(isChecked == true){
								   Ext.getCmp("ukeyPanel").setVisible(true);
								   Ext.getCmp("shortMessagePanel").setVisible(true);
							   }else if(isChecked == false){
							   		Ext.getCmp("ukey").setValue(false);
								   Ext.getCmp("shortMessage").setValue(false);
								   Ext.getCmp("ukeyPanel").setVisible(false);
								   Ext.getCmp("shortMessagePanel").setVisible(false);
							   }
						   },
						   'disable' : function(){
						   	 	if(this.el){
						   	 		this.el.up("div").removeClass("x-item-disabled");
				            		this.el.up("div").setStyle({
				            			color : "#555",
				            			cursor: "default",
				            			opacity: 1
				            		});
						   	 	}
			            	}
					}
				},
				{
					xtype : 'panel',
					id:'ukeyPanel',
					name:'ukeyPanel',
					layout:'column',
					//hidden:true,
					items:[{
							  columnWidth:1,
								layout:'form',
								labelWidth:1,
								style:'padding-left:30px',
								items:[{
										xtype:'checkbox',
										id : 'ukey',
										name : 'ukey',
										anchor:"50%",
										boxLabel:"U-key汇款认证",
										inputValue:'1',
										listeners:{
											'disable' : function(){
												if(this.el){
													this.el.up("div").removeClass("x-item-disabled");
								            		this.el.up("div").setStyle({
								            			color : "#555",
								            			cursor: "default",
								            			opacity: 1
								            		});
												}
							            	}
										}
									  }]
						  }]
				},
				{
					xtype : 'panel',
					layout:'column',
					id : 'shortMessagePanel',
					name : 'shortMessagePanel',
					//hidden:true,
					items:[{
							  columnWidth:1,
								layout:'form',
								style:'padding-left:30px',
								labelWidth:1,
								items:[{
								    	   xtype:'checkbox',
											id : 'shortMessage',
											name : 'shortMessage',
											anchor:"50%",
											boxLabel:"短信认证",
											inputValue:'1',
											listeners:{
												'disable' : function(){
													if(this.el){
														this.el.up("div").removeClass("x-item-disabled");
									            		this.el.up("div").setStyle({
									            			color : "#555",
									            			cursor: "default",
									            			opacity: 1
									            		});
													}
								            	}
											}
									  }]
						  }]
				},{
				      anchor:"98%",
					  xtype:'checkbox',
					  id : 'mobileBank',
					  name : 'mobileBank',
					  //hidden:true,
					  boxLabel:"手机银行",
					  inputValue:'1',
					  listeners:{
						  'check':function(obj,isChecked){
							  if(isChecked == true){
								  Ext.getCmp("shortMessage2Panel").setVisible(true);
								  Ext.getCmp("shortMessage2").setValue(true);
							  }else if(isChecked == false){
							  	  Ext.getCmp("shortMessage2").setValue(false);
								  Ext.getCmp("shortMessage2Panel").setVisible(false);
							  }
						  },
						  'disable' : function(){
						  		if(this.el){
						  			this.el.up("div").removeClass("x-item-disabled");
				            		this.el.up("div").setStyle({
				            			color : "#555",
				            			cursor: "default",
				            			opacity: 1
				            		});
						  		}
			            	}
					  }
				},{
					xtype : 'panel',
					layout:'column',
					id : 'shortMessage2Panel',
					name:'shortMessage2Panel',
					//hidden:true,
					items:[{
							columnWidth:1,
							layout:'form',
							labelWidth:1,
							style:'padding-left:30px',
							items:[{
										  anchor:"50%",
										  xtype:'checkbox',
										  id:'shortMessage2',
										  name:'shortMessage2',
										  checked :true,
										  boxLabel:"短信验证",
										  inputValue:'1',
										  listeners:{
												'disable' : function(){
													if(this.el){
														this.el.up("div").removeClass("x-item-disabled");
									            		this.el.up("div").setStyle({
									            			color : "#555",
									            			cursor: "default",
									            			opacity: 1
									            		});
													}
								            	}
											}
									}]
					       }]
				},{
       	        	xtype : 'panel',
					layout:'column',
					id : 'dayAccLimitPanel',
					name : 'dayAccLimitPanel',
					//hidden:true,
					items:[{
								 columnWidth:.18,
									layout:'form',
									labelWidth:1,
									items:[{
												xtype:'tbtext',
											    width:800,
											    height:37.81,
											    style:'padding-top:5px',
											    text:"日累计转账限额"
											}]
								      
							 },{
									columnWidth:.2,
									layout:'form',
									labelWidth:1,
									items:[{
												  anchor:"90%",
												  xtype:'radio',
												  boxLabel:"默认无限制",
												  id : 'dayAccLimitDefault',
											  	  name : 'dayAccLimitDefault',
												  checked:true,
												  inputValue:1,
												  listeners:{
													  'check':function(obj,isChecked){
														   if(isChecked == true){
														  	  Ext.getCmp("dayAccSelfDefine").reset();
															  Ext.getCmp("dayAccSelfDefine").setDisabled(true);
														  }
													  },
													  'disable' : function(){
													  		if(this.el){
													  			this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
													  		}
										            	}
												  }
										}]
							},{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
											anchor:"98%",
											  xtype:'radio',
											  boxLabel:"自定义",
											  inputValue:0,
											  id : 'dayAccLimitDefine',
											  name : 'dayAccLimitDefault',
											  listeners:{
													  'check':function(obj,isChecked){
														  if(isChecked == true){
															  Ext.getCmp("dayAccSelfDefine").allowBlank = false;
															  Ext.getCmp("dayAccSelfDefine").setDisabled(false);
															  Ext.getCmp("dayAccSelfDefine").focus();
														  }
													  },
													  'disable' : function(){
													  		if(this.el){
													  			this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
													  		}
										            	}
												  }
										}]
							},{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[
										{
											anchor:"98%",
											xtype : 'numberfield',
											 width : '50',
											 id : 'dayAccSelfDefine',
											 name : 'dayAccSelfDefine'
										}
								       ]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
											anchor:"90%",
											 xtype:'tbtext',
											 text:"<br>元</br>",
											 style:"font-size:14px;"
										}]
							}]
				},
				{
       	        	xtype : 'panel',
					layout:'column',
					id : 'dayAccCountPanel',
					name : 'dayAccCountPanel',
					//hidden:true,
					items:[
							 {
								 columnWidth:0.18,
									layout:'form',
									labelWidth:1,
									items:[
											{
												xtype:'tbtext',
											    width:800,
											    style:'padding-top:5px',
											    text:"日累计转账笔数"
											   	}	
									       ]
								      
							 },
							 {
									columnWidth:0.2,
									layout:'form',
									labelWidth:1,
									items:[
											{
												  anchor:"90%",
												  xtype:'radio',
												  boxLabel:"默认200笔",
												  id : 'dayAccCountDefault',
												  name : 'dayAccCountDefault',
												  checked:true,
												  inputValue:1,
												  listeners:{
													  'check':function(obj,isChecked){
														   if(isChecked == true){
														  	  Ext.getCmp("dayCountSelfDefine").reset();
															  Ext.getCmp("dayCountSelfDefine").setDisabled(true);
														  }
													  },
													  'disable' : function(){
													  		if(this.el){
													  			this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
													  		}
										            	}
												  }
											}
									      ]
							},
							{
								columnWidth:0.1,
								layout:'form',
								labelWidth:1,
								items:[
										{
											anchor:"98%",
											  xtype:'radio',
											  boxLabel:"自定义",
											  id : 'dayAccCountDefine',
											  name : 'dayAccCountDefault',
											  inputValue:0,
											  listeners:{
													  'check':function(obj,isChecked){
														  if(isChecked == true){
															  Ext.getCmp("dayCountSelfDefine").allowBlank = false;
															  Ext.getCmp("dayCountSelfDefine").setDisabled(false);
															  Ext.getCmp("dayCountSelfDefine").focus();
														  }
													  },
													  'disable' : function(){
													  		if(this.el){
													  			this.el.up("div").removeClass("x-item-disabled");
											            		this.el.up("div").setStyle({
											            			color : "#555",
											            			cursor: "default",
											            			opacity: 1
											            		});
													  		}
										            	}
												  }
										}
								       ]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[
										{
											anchor:"98%",
											xtype : 'numberfield',
											 width : '50',
											 id : 'dayCountSelfDefine',
											  name : 'dayCountSelfDefine',
											 titleCollapse : true,
											 collapsible : true
										}
								       ]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[
										{
											anchor:"90%",
											 xtype:'tbtext',
											 text:"<br>笔</br>",
											 style:"font-size:14px;"
										}
								       ]
							}]
				},
				{
       	        	xtype : 'panel',
					layout:'column',
					id : 'yearAccLimitPanel',
					name : 'yearAccLimitPanel',
					//hidden:true,
					items:[{
								 columnWidth:.18,
									layout:'form',
									labelWidth:1,
									items:[{
												xtype:'tbtext',
											    width:800,
											     style:'padding-top:5px',
											    text:"年累计转账限额"
											}]
							 },
							 {
									columnWidth:.20,
									layout:'form',
									labelWidth:1,
									items:[{
											  anchor:"90%",
											  xtype:'radio',
											  boxLabel:"默认无限制",
											  id : 'yearAccLimitDefault',
										  	  name : 'yearAccLimitDefault',
											  checked:true,
											  inputValue:1,
											  listeners:{
												  'check':function(obj,isChecked){
													   if(isChecked == true){
													  	  Ext.getCmp("yearAccSelfDefine").reset();
														  Ext.getCmp("yearAccSelfDefine").setDisabled(true);
													  }
												  },
												  'disable' : function(){
												  		if(this.el){
												  			this.el.up("div").removeClass("x-item-disabled");
										            		this.el.up("div").setStyle({
										            			color : "#555",
										            			cursor: "default",
										            			opacity: 1
										            		});
												  		}
									            	}
											  }
										}]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
										anchor:"98%",
										  xtype:'radio',
										  boxLabel:"自定义",
										  inputValue:0,
										  id : 'yearAccLimitDefine',
										  name : 'yearAccLimitDefault',
										  listeners:{
												  'check':function(obj,isChecked){
													  if(isChecked == true){
														  Ext.getCmp("yearAccSelfDefine").allowBlank = false;
														  Ext.getCmp("yearAccSelfDefine").setDisabled(false);
														  Ext.getCmp("yearAccSelfDefine").focus();
													  }
												  },
												  'disable' : function(){
												  		if(this.el){
												  			this.el.up("div").removeClass("x-item-disabled");
										            		this.el.up("div").setStyle({
										            			color : "#555",
										            			cursor: "default",
										            			opacity: 1
										            		});
												  		}
									            	}
											  }
									}]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
											anchor:"98%",
											xtype : 'numberfield',
											 width : '50',
											 titleCollapse : true,
											 id : 'yearAccSelfDefine',
											  name :'yearAccSelfDefine',
											 collapsible : true
										}]
							},
							{
								columnWidth:.1,
								layout:'form',
								labelWidth:1,
								items:[{
										anchor:"90%",
										 xtype:'tbtext',
										 text:"<br>元</br>",
										 style:"font-size:14px;"
									}]
							}]
						}]
		   			}]
	  	},{
	 		layout : 'column',
        	style:'margin:0px 20px 0px 10px;',
        	width:950,
		    items:[{
            	 layout:"table",
				 anchor:'98%',
				 defaultType:"checkbox",
				 layoutConfig:{columns:2,padding : 10},//将父容器分成2列
				 items:[
				 	{
						  anchor:"90%",
						  xtype:'checkbox',
						  boxLabel:"电子对账单",
						  id:'elecState',
						  name:'elecState',
						  inputValue:'1',
						  width:150,
						  listeners:{
								afterrender	: function(){
									this.el.up("div").setStyle("margin","5px 0px");
								},
							  'disable' : function(){
							  		if(this.el){
							  			this.el.up("div").removeClass("x-item-disabled");
					            		this.el.up("div").setStyle({
					            			color : "#555",
					            			cursor: "default",
					            			opacity: 1
					            		});
							  		}
				            	}
						  }
						  
					},
					{
						xtype : 'panel',
						layout:'column',
						id:'elecStatePanel',
						name:'elecStatePanel',
						//hidden:true,
						colspan:2,
						width:800,
						items:[{
							columnWidth:.35,
							layout:'form',
							labelWidth:50,
							items:[{
								xtype : 'textfield',
								fieldLabel : "E-mail",
								name : 'email',
								id : 'email',
								width:'200',
								vtype:'email',
								listeners : {
					            	'afterrender'	: function(){
					            		Ext.Ajax.request({
											url : basepath + '/acrmFCiBankService!getEmail.json?custId='+custId+"&type=0",
											method : 'GET',
											success : function(response) {
											var ret = Ext.decode(response.responseText);
											this.setValue(ret.email);
											}
										})
									}
					            }
							}]
						},{
							columnWidth:.01,
							layout:'form',
							labelWidth:1,
							items:[{
								xtype:'tbtext',
								style :'padding-top : 8px;',
							   text:'<b>(</b>'
							}]
						 },{
						    columnWidth:.13,
							layout:'form',
							labelWidth:1,
							items:[{
								  anchor:"98%",
								  xtype:'checkbox',
								  id : 'isEquEmail',
								  boxLabel:"同电邮地址",
								  inputValue:'1',
								  listeners:{
									  
									  'disable' : function(){
									  		if(this.el){
									  			this.el.up("div").removeClass("x-item-disabled");
							            		this.el.up("div").setStyle({
							            			color : "#555",
							            			cursor: "default",
							            			opacity: 1
							            		});
									  		}
						            	}
								  }
							}]
						 },
						{
						 columnWidth:.01,
							layout:'form',
							labelWidth:1,
							items:[{
								xtype:'tbtext',
								style :'padding-top : 8px;',
							  	text:'<b>)</b>'
							}]
					   }]
					}]
	   		 }]
  		 },{
	 		layout : 'column',
        	style:'margin:0px 20px 0px 10px;',
        	width:950,
		    items:[{
            	 layout:"table",
				 anchor:'98%',
				 defaultType:"checkbox",
				 layoutConfig:{columns:2,padding : 10},//将父容器分成2列
				 items:[{
					anchor:"90%",
					xtype:'checkbox',
					boxLabel:"财务变动通知",
					id:'chgNotice',
					width : 150,
					inputValue:'1',
					listeners:{
						afterrender	: function(){
							this.el.up("div").setStyle("margin","5px 0px");
						},
						'disable' : function(){
								if(this.el){
									this.el.up("div").removeClass("x-item-disabled");
				            		this.el.up("div").setStyle({
				            			color : "#555",
				            			cursor: "default",
				            			opacity: 1
				            		});
								}
			            	}
					}
				}]
	   		 }]
  		 }]
	}]
		}]/*,buttons:[{
		id : '_saveHobby',
		text :'保存',
		hidden:JsContext.checkGrant('bankServiceInfo_save'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在保存数据,请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/acrmFCiBankService.json?custId='+custId,
				method : 'POST',
				form : opForm.form.id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Ajax.request({
						url : basepath+'/session-info!getPid.json',
						method : 'GET',
						success : function(a,b,v) {
						    var idStr = Ext.decode(a.responseText).pid;
						    Ext.Msg.alert('提示', '操作成功');
							store.reload();
						}
					});
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
	},{
		text :'提交',
		hidden:JsContext.checkGrant('bankServiceInfo_commit'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在提交审核的数据,请稍等...','提示');
		    Ext.Ajax.request({
				url : basepath + '/acrmFCiBankService!initFlow.json?custId='+custId,
				method : 'GET',
				form : opForm.form.id,
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
	}]*/
}
	
	
	);

Ext.getCmp('isEquEmail').on('check',function(){
	if(Ext.getCmp('isEquEmail').checked == true){
		 Ext.Ajax.request({
				url : basepath + '/acrmFCiBankService!getEmail.json?custId='+custId+"&type=0",
				method : 'GET',
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					opForm.getForm().findField('email').setValue(ret.email);
				}
		});
	}else{
		Ext.Ajax.request({
			url : basepath + '/acrmFCiBankService!getEmail.json?custId='+custId+"&type=1",
			method : 'GET',
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				opForm.getForm().findField('email').setValue(ret.email);
			}
	    });
	}
});


var store = new Ext.data.Store({
	restful:true,
    proxy : new Ext.data.HttpProxy({
		url:basepath + '/acrmFCiBankService.json'
	}),
    reader: rsreader
});
//初始化家庭信息方法
store.load({
	params : {
		custId : custId
	},
	method : 'GET',
    callback:function(){
    	setFormValue();
	}
});
/**
 * 为form表单赋值，主要是解决异步请求时的问题
 * 注：把方法定义到window对象上是因为定时器带参数时，会找不到此方法，故加在对象上调用即可
 */
//window.__setFormValue = function(){
 function setFormValue() {
 
		if(store.getCount() != 0) {
			for(var i = 0;i < store.getCount();i++) {
				var data = store.getAt(i).data;
				if(data){
					//借记卡信息
					debugger;
					if(data.isCardApply == "1") {//借记卡申请
						Ext.getCmp("cardType").setVisible(true);
						Ext.getCmp("cardType1_0").setVisible(true);
						Ext.getCmp("cardType2_0").setVisible(true);
						Ext.getCmp("ATM").setVisible(true);
						Ext.getCmp("ATMDayLimitPanel").setVisible(true);
						Ext.getCmp("ATMDayCountPanel").setVisible(true);
						Ext.getCmp("ATMYearLimitPanel").setVisible(true);
						Ext.getCmp("POS").setVisible(true);
						Ext.getCmp("POSPanel").setVisible(true);
						Ext.getCmp("jiejika").setValue(true);
						if(data.isAtmHigh == "0") {//ATM每日累计转账最高限额
							Ext.getCmp("ATMDayLimitDefine").setValue(true);
							Ext.getCmp("ATMDayLimit").setValue(data.atmHigh);
						}else{
							Ext.getCmp("ATMDayLimitDefault").setValue(true);
							Ext.getCmp("ATMDayLimit").setValue("");
						}
						if(data.isDftcntAtm == "0") {//ATM每日累计转账笔数
							Ext.getCmp("ATMDayCountDefine").setValue(true);
							Ext.getCmp("ATMDayLimitCount").setValue(data.lmtcntDAtm);
						}else{
							Ext.getCmp("ATMDayCountDefault").setValue(true);
							Ext.getCmp("ATMDayLimitCount").setValue("");
						}
						if(data.isDftlmtyAtm == "0") {//ATM每年转账累计限额
							Ext.getCmp("ATMYearLimitDefine").setValue(true);
							Ext.getCmp("ATMYearLimit").setValue(data.lmtamtYAtm);
						}else{
							Ext.getCmp("ATMYearLimitDefault").setValue(true);
							Ext.getCmp("ATMYearLimit").setValue("");
						}
						if(data.isPosHigh == "0") {//POS单笔消费限额
							Ext.getCmp("POSDefine").setValue(true);
							Ext.getCmp("eachCustemLimit").setValue(data.posHigh);
						}else{
							Ext.getCmp("POSDefault").setValue(true);
							Ext.getCmp("eachCustemLimit").setValue("");
						}
						//卡类型
						if(cardTypeStore.getCount() == 0) {
							cardTypeStore.load({
								callback	: function() {
									Ext.getCmp("cardType").setValue(data.card1);
								}
							})
						} else {
							Ext.getCmp("cardType").setValue(data.card1);
						}
		
						cardType1Store.load({
							params		: {
								name	: data.card2
							},
							callback	: function() {
								Ext.getCmp("cardType1_0").setValue(data.card2);
							}
						});
						cardType2Store.load({
							params		: {
								name	: data.card3
							},
							callback	: function() {
								Ext.getCmp("cardType2_0").setValue(data.card3);
							}
						});
					}else{
						  Ext.getCmp("jiejika").setValue(false);
						  Ext.getCmp("cardType").setVisible(false);
						Ext.getCmp("cardType1_0").setVisible(false);
						Ext.getCmp("cardType2_0").setVisible(false);
						Ext.getCmp("ATM").setVisible(false);
						Ext.getCmp("ATMDayLimitPanel").setVisible(false);
						Ext.getCmp("ATMDayCountPanel").setVisible(false);
						Ext.getCmp("ATMYearLimitPanel").setVisible(false);
						Ext.getCmp("POS").setVisible(false);
						Ext.getCmp("POSPanel").setVisible(false);
					} 
					//电子银行信息
					if(data.isElebankSer == "1") {//电子银行服务
						Ext.getCmp("netBank").setVisible(true);
						Ext.getCmp("ukeyPanel").setVisible(true);
						Ext.getCmp("shortMessagePanel").setVisible(true);
						Ext.getCmp("mobileBank").setVisible(true);
						Ext.getCmp("shortMessage2Panel").setVisible(true);
						Ext.getCmp("dayAccLimitPanel").setVisible(true);
						Ext.getCmp("dayAccCountPanel").setVisible(true);
						Ext.getCmp("yearAccLimitPanel").setVisible(true);
						Ext.getCmp("dianziBank").setValue(true);
						if(data.isNtBank == "1") {//网络银行
							Ext.getCmp("netBank").setValue(true);
					    		if(data.ukey == "1") {//u-key
								Ext.getCmp("ukey").setValue(true);
							}
							if(data.messageCode == "1") {//短信认证码
								Ext.getCmp("shortMessage").setValue(true);
							}
						}
						if(data.mobileBanking == "1") {//手机银行 
							Ext.getCmp("mobileBank").setValue(true);
							if(data.isMsgPhone == "1") {//短信验证
								Ext.getCmp("shortMessage2").setValue(true);
							} else {
								Ext.getCmp("shortMessage2").setValue(false);
							};
						}
						if(data.isDftlmtdEb == "0") {//日累计转账限额
							Ext.getCmp("dayAccLimitDefine").setValue(true);
							Ext.getCmp("dayAccSelfDefine").setValue(data.LmtamtdEb);
						}
						if(data.isDftcntEb == "0") {//日累计转账笔数
							Ext.getCmp("dayAccCountDefine").setValue(true);
							Ext.getCmp("dayCountSelfDefine").setValue(data.lmtcntDEb);
						}
						if(data.isDftlmtyEb == "0") {//年累计转账限额
							Ext.getCmp("yearAccLimitDefine").setValue(true);
							Ext.getCmp("yearAccSelfDefine").setValue(data.lmtamtYEb);
						}
					}else{
						Ext.getCmp("dianziBank").setValue(false);
						Ext.getCmp("netBank").setVisible(false);
						Ext.getCmp("ukeyPanel").setVisible(false);
						Ext.getCmp("shortMessagePanel").setVisible(false);
						Ext.getCmp("mobileBank").setVisible(false);
						Ext.getCmp("shortMessage2Panel").setVisible(false);
						Ext.getCmp("dayAccLimitPanel").setVisible(false);
						Ext.getCmp("dayAccCountPanel").setVisible(false);
						Ext.getCmp("yearAccLimitPanel").setVisible(false);
					}
					//电子对账单信息
					if(data.statements == "1") {//电子对账单
						debugger;
						Ext.getCmp("elecStatePanel").setVisible(true);
						Ext.getCmp("elecState").setValue(true);
						//Ext.getCmp("email").setValue(data.mail);
						if(data.mailAddress == "1") {//同电邮地址
							Ext.getCmp("isEquEmail").setValue(true);
						}else{
							Ext.getCmp("isEquEmail").setValue(false);
						}
					}else{
						Ext.getCmp("elecStatePanel").setVisible(false);
						Ext.getCmp("elecState").setValue(false);
						Ext.getCmp("email").setValue("");
					}
					//账务变动通知
					if(data.changeNotice == "1") {//账务变动通知
						Ext.getCmp("chgNotice").setValue(true);
					}else{
						Ext.getCmp("chgNotice").setValue(false);
					}
				}
				
			}

		}else{
			Ext.getCmp("jiejika").setValue(false);
			Ext.getCmp("dianziBank").setValue(false);
			Ext.getCmp("elecState").setValue(false);
			Ext.getCmp("chgNotice").setValue(false);
			Ext.getCmp("cardType").setVisible(false);
			Ext.getCmp("cardType1_0").setVisible(false);
			Ext.getCmp("cardType2_0").setVisible(false);
			Ext.getCmp("ATM").setVisible(false);
			Ext.getCmp("ATMDayLimitPanel").setVisible(false);
			Ext.getCmp("ATMDayCountPanel").setVisible(false);
			Ext.getCmp("ATMYearLimitPanel").setVisible(false);
			Ext.getCmp("POS").setVisible(false);
			Ext.getCmp("POSPanel").setVisible(false);
			Ext.getCmp("netBank").setVisible(false);
			Ext.getCmp("ukeyPanel").setVisible(false);
			Ext.getCmp("shortMessagePanel").setVisible(false);
			Ext.getCmp("mobileBank").setVisible(false);
			Ext.getCmp("shortMessage2Panel").setVisible(false);
			Ext.getCmp("dayAccLimitPanel").setVisible(false);
			Ext.getCmp("dayAccCountPanel").setVisible(false);
			Ext.getCmp("yearAccLimitPanel").setVisible(false);
			Ext.getCmp("elecStatePanel").setVisible(false);
		}
	} 


var tabs = new Ext.Panel( {
	layout : 'form',
	autoScroll : true,
	items : [opForm]
});

//展现页面
var viewport = new Ext.Viewport({
layout : 'fit',
items:[tabs]
});

});
