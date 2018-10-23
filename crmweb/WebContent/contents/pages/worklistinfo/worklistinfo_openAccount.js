/**
 * @description 一键开户流程展示页面
 * @author cuihw
 * @since 2017-09-15
 */

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var isLianMingHu;

	//对私联系信息类型
	var store_contmethTypes=new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/oneKeyAccountAction!getContmethTypes.json'
		}),
		reader : new Ext.data.JsonReader( {root : 'JSON'},['key','value'])
	});

	//来源渠道
	var store_channels=new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/oneKeyAccountAction!getChannels.json'
		}),
		reader : new Ext.data.JsonReader( {root : 'JSON'},['key','value'])
	});
	//卡种
	var cardTypeStore =  new Ext.data.Store( {
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
	var cardType1Store = new Ext.data.Store( {
			restful : true,
			sortInfo : {
		            field:'key',
		            direction:'ASC'
		        },
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/oneKeyAccountAction!getCardType.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
	var cardType2Store  = new Ext.data.Store( {
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
	
	
    /**
	 * 放大字体
	 */
	function convertFontCss(text) {
		return '<font style="font-size:14px;">' + text + '</font>';
	}

	//国籍
	var conStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		proxy : new Ext.data.HttpProxy({
					//url : basepath + '/lookup.json?name=XD000025'
					url : basepath + '/oneKeyAccountAction!getNationNalityStore.json'
				}),
		reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, ['key', 'value'])
	});
	
	
	//风险国别代码
	var riskStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		proxy : new Ext.data.HttpProxy({
					url : basepath + '/oneKeyAccountAction!getRiskCountryStore.json'
				}),
		reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, ['key', 'value'])
	});

	//证件类型的下拉选项
	var store_ident=new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000368'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});

	//国际区号
	var globalRoamingStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=IDD_CODE'
				}),
		reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, ['key', 'value'])
	});

	// 与我行关联关系
	var staffinStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=XD000306'
				}),
		reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, ['key', 'value'])
	});

	//发证机关所在地
	var dqStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/oneKeyAccountAction!getOrgRegionStore.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	

	var instanceid = curNodeObj.instanceid;
	var custId = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;

	window.taxManager = (function() {
		var count = 1;
		function getId() {
			return count++;
		}
		function getCfg(data) {
			var index = getId();
			var cfg = {
				xtype : 'compositefield',
				id : 'tax_' + index,
				hideLabel : true,
				style:'margin-left:30px;',
				items : [{
							xtype : 'label',
							html : convertFontCss('税收居民国（地区）'),
							width : 140
						}, {
							xtype : 'textfield',
							labelAlign : 'top',
							id : 'juMinGuo' + index,
							cls : 'x-readOnly',
							width : 200,
							readOnly : data ? true : false,
							value : data ? data.area : '',
							name : 'juMinGuo' + index
						}, {
							xtype : 'label',
							html : convertFontCss('纳税人识别号（TIN）'),
							width : 140
						}, {
							xtype : 'textfield',
							labelAlign : 'top',
							width : 200,
							id : 'TIN' + index,
							cls : 'x-readOnly',
							readOnly : data ? true : false,
							value : data ? data.tin : '',
							name : 'TIN' + index
						}]
			}
			return cfg;
		}
		return {
			add : function(data) {
				var exceptionForm = Ext.getCmp("exceptionHand");
				var cfg = getCfg(data);
				exceptionForm.add(cfg);
				exceptionForm.doLayout();
			},
			initSeq : function() {
				count = 1;
			},
			getCurrentSeq : function() {
				return count;
			}
		}
	})();
	
	
	window.taxManager2 = (function() {
		var count = 1;
		function getId() {
			return count++;
		}
		function getCfg2(data) {
			var index = getId();
			var cfg = {
				xtype : 'compositefield',
				id : 'tax2_' + index,
				hideLabel : true,
				style:'margin-left:30px;',
				items : [{
							xtype : 'label',
							html : convertFontCss('税收居民国（地区）'),
							width : 140
						}, {
							xtype : 'textfield',
							labelAlign : 'top',
							id : 'juMinGuo2_' + index,
							cls : 'x-readOnly',
							width : 200,
							readOnly : data ? true : false,
							value : data ? data.area : '',
							name : 'juMinGuo2_' + index
						}, {
							xtype : 'label',
							html : convertFontCss('纳税人识别号（TIN）'),
							width : 140
						}, {
							xtype : 'textfield',
							labelAlign : 'top',
							width : 200,
							id : 'TIN2_' + index,
							cls : 'x-readOnly',
							readOnly : data ? true : false,
							value : data ? data.tin : '',
							name : 'TIN2_' + index
						}]
			}
			return cfg;
		}
		return {
			add : function(data) {
				var exceptionForm = Ext.getCmp("exceptionHand2");
				var cfg = getCfg2(data);
				exceptionForm.add(cfg);
				exceptionForm.doLayout();
			},
			initSeq : function() {
				count = 1;
			},
			getCurrentSeq : function() {
				return count;
			}
		}
	})();
	


	//查询 第一页
	var qzComStore = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/oneKeyAccountAction!queryComfsx.json',
							method : 'get'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'json.count',
							root : 'json.data'
						}, [
								'CUST_ID',
								'PERSONAL_NAME',
								'PINYIN_NAME',
								'GENDER',
								'NATIONALITY',
								'BIRTHDAY',// "2017-09-04"
								'CITIZENSHIP',
								'BIRTHLOCALE',// "ARE"
								'identityType3',
								'identityNo3',
								'LEGAL_EXPIRED_DATE',// 有效日期
								'longterm',// 是否长期有效
								'twIdentNum3',
								'gaIdentNum3',
								'youxiaoqixian',
								'longterm2',
								//'qianfadidian',
								'qianfajiguan',
								'chizhengcishu',
								'HOME_ADDR',
								'HOME_ADDR_INFO',
								'POST_ZIPCODE',
								'isRent',
								'MAIL_ADDR',
								'MAIL_ADDR_INFO',
								'MAIL_ZIPCODE',
								'JOBINFO',
								'JOBNAME',
								'JOB',
								'JOBREMARK',
								'mbPhone',
								'QUYUMA',
								'mbPHONENUM',
								'ORTHERPHONE',// 其他电话1
								'PHONE_CITIZENSHIP',
								'QUYUMA1',
								'QUYUMA2',
								'PHONENUM1',
								'ORTHERPHONE1',// 其他电话2
								'PHONE_CITIZENSHIP1', 'QUYUMA3', 'QUYUMA4',
								'PHONENUM2', 'EMAIL', 'HASRELATED',
								'RELATEDNAME', 'RELATION', 'RELATION1',
								'SOURCECHANNEL','RISK_NATION_CODE', 'customManager',
								'isLianMingHu', 'lianMinPinYin', 'sex',
								'CITIZENSHIP1', 'lianMingIdenType1',
								'lianMingIdenNo1', 'LEGAL_EXPIRED_DATE2',
								'LONGTERM2', 'lianMingTwIdentNum1',
								'LEGAL_EXPIRED_DATE1', 'LONGTERM1',
								'lianMingGaIdentNum1', 'CITIZENSHIP2',
								'radio1', 'isUNtaxpayer', 'USTIN',
								'taxCountrys', 'tins',// 税收居民国
								'REASON', 'REASON2', 'detailReason',
								'radio2', 'isUNtaxpayer2', 'USTIN2',
								'taxCountrys2', 'tins2',// 税收居民国
								'REASON3', 'REASON4', 'detailReason2'
								])
			});
	// 基本信息部分 第一页（客户基础信息）
	var qzCombaseInfo = new Ext.form.FormPanel({
		frame : true,
		autoScroll : true,
		title : '第一页（客户基础信息）',
		labelWidth : 140,
		buttonAlign : "center",
		items : [{
			layout : 'column',
			items : [{
				title : '<font style="font-size:16px;">客户基本信息</font>',
				columnWidth : 1,
				xtype : 'fieldset',
				//style : 'margin:0 10px;',
				 style:'margin:0 180px;',
				items : [{
					layout : 'column',
					items : [{
						 		columnWidth:0.5,
    	    					layout:'form',
    	    					items:[{
        	    					xtype: 'compositefield',
        	    					fieldLabel : '<font color="red">*</font>'+convertFontCss("姓名"),
						    		anchor:'90%',
									items: [{
											 id : "PERSONAL_NAME",
											 xtype : 'textfield',
											 anchor:'92%',
											 width:150,
											 readOnly:true,
											 cls:'x-readOnly',
											 titleCollapse : true,
											 collapsible : true
										 },{
										   xtype:'checkbox',
										   id:'isLianMingHu',
										   boxLabel:convertFontCss("联名户"),
										   name:'isLianMingHu',
										   inputValue:'1',
										   readOnly:true,
										   disabled:true,
										   listeners : {
										   		'afterrender' : function(){
									            		this.el.up("div").removeClass("x-item-disabled");
									            		this.el.up("div").setStyle({
									            			color : "#555",
									            			cursor: "default",
									            			opacity: 1
									            		});
								            	    }
										       }
										    }]
    	    						}]
								 },{//客户姓名拼音
									columnWidth:0.5,
								    layout : 'form',
							        items:[{
								         xtype : 'textfield',
								         id:'PINYIN_NAME',
								         width:200,
										 fieldLabel : '<font color=red>\*</font>'+convertFontCss("姓名拼音"),
										 readOnly : true,
										 cls : 'x-readOnly'
							          }]
			                	},{
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{
									   xtype:'radiogroup',
									   id:'GENDER',
									   hiddenName : 'GENDER',
									   fieldLabel:'<font color=red>\*</font>'+convertFontCss("性别"),
									   columns:2,
									   anchor:'60%',
									   disabled : true,
									   items:[
											  {boxLabel:'男',name:'GENDER',inputValue:'1',
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
											  },
											  {boxLabel:'女',name:'GENDER',inputValue:'2',
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
									}]
								}, {
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{//国籍
										xtype : 'combo',
										hiddenName :'citizenship',
										id : 'citizenship',
										fieldLabel : '<font color="red">*</font>'+convertFontCss("国籍"),
										width : 200,
										store : conStore,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										readOnly : true,
										cls : 'x-readOnly'
									}]
								},{
									 columnWidth:1,
									 layout: 'form',
									 items:[{//国籍
											xtype : 'textfield',
											id : 'BIRTHDAY',
											//anchor : '36%',
											width:200,
											fieldLabel : '<font color=red>\*</font>'+ convertFontCss("个人生日"),
											readOnly : true,
											cls : 'x-readOnly'
									}]
								},{
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{
										xtype : 'combo',
										id : 'birthLocale',
										hiddenName : 'birthLocale',
										fieldLabel : '<font color=red>\*</font>'+convertFontCss("出生地"),
										store : conStore,
										resizable : true,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										triggerAction : 'all',
										width : 200,
										readOnly : true,
										cls : 'x-readOnly'
									 }]
								}, {//签发地点
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{
										xtype : 'combo',
										id : 'qianfajiguan',
										hiddenName : 'qianfajiguan',
										fieldLabel : convertFontCss("发证机关所在地"),
										emptyText:"请选择",
										store : dqStore,
										resizable : true,
										valueField : 'key',
										displayField : 'value',
										readOnly : true,
										cls : 'x-readOnly',
										mode : 'local',
										valueNotFoundText :"",
										triggerAction : 'all',
										width : 200
									 }]
								 },{
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{
										xtype : 'combo',
										id : 'identityType3',
										hiddenName :'identityType3',
										fieldLabel : '<font color="red">*</font>'+convertFontCss("证件类型"),
										emptyText:'请选择',
										store : store_ident,
										resizable : true,
										width : 200,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										readOnly:true,
										cls:'x-readOnly',
										triggerAction : 'all'
									}]
								},{
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{//证件号码
										xtype : 'textfield',
										id : 'identityNo3',
										width : 200,
										fieldLabel : '<font color=red>\*</font>'+convertFontCss("证件号码"),
										readOnly:true,
										cls:'x-readOnly'
									}]
								},{
									columnWidth:1,
									layout:'form',
									items:[{
										xtype: 'compositefield',
										anchor:'90%',
										items: [{//有效日期
													 xtype : 'datefield',
													 id:'LEGAL_EXPIRED_DATE',
													 name : 'LEGAL_EXPIRED_DATE',
													 fieldLabel : '<font color="red">*</font>'+convertFontCss("有效日期"),
													 width:200,
													 readOnly : true,
													 cls : 'x-readOnly',
													 format:'Y-m-d'
											   },{//是否长期有效
												   xtype:'checkbox',
												   id:'longterm',
												   boxLabel:convertFontCss("长期有效"),
												   inputValue:'1',
												   disabled : true,
												   listeners:{
												  		'afterrender' : function(){
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
									 columnWidth:0.5,
									 layout: 'form',
									 items:[{
										 xtype : 'textfield',
										 width : 200,
										 name:'twIdentNum3',
										 id:'twIdentNum3',
										 fieldLabel : '<font color=red>\*</font>'+convertFontCss("台湾身份证"),
										 readOnly:true,
										 cls:'x-readOnly'
									}]
								},{//持证次数
									 columnWidth:0.5,
									 layout:'form',
									 items:[{
									    fieldLabel:convertFontCss("持证次数"),
									    xtype : 'numberfield',
										name : 'chizhengcishu',
										decimalPrecision :0,
										id : 'chizhengcishu',
										readOnly : true,
										cls : 'x-readOnly',
										//hidden :true,
										width : 200
									}]  
								}, {
									 columnWidth:1,
									 layout: 'form',
									 items:[{
										 xtype : 'textfield',
										 width : 200,
										 name:'gaIdentNum3',
										 id:'gaIdentNum3',
										 fieldLabel : '<font color=red>\*</font>'+convertFontCss("港澳身份证"),
										 //hidden:true,
										 readOnly:true,
										 cls:'x-readOnly'
									}]
								},{
										columnWidth:1,
										layout:'form',
										items:[{
											id:'dateCompositefield',
											xtype: 'compositefield',
											//hidden:true,
											anchor:'90%',
											items: [{//有效日期
														 xtype : 'datefield',
														 id:'youxiaoqixian',
														 name : 'youxiaoqixian',
														 fieldLabel : '<font color="red">*</font>'+convertFontCss("有效日期"),
														 width : 200,
														 readOnly : true,
														 cls : 'x-readOnly',
														 format:'Y-m-d'
												   },{//是否长期有效
													   xtype:'checkbox',
													   id:'longterm2',
													   boxLabel:convertFontCss("长期有效"),
													   inputValue:'1',
													   disabled : true,
													   listeners:{
													  		'afterrender' : function(){
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
									}, {//居住地址
									    columnWidth:1,
					        	    	layout:'form',
					        	    	items:[{
					        	    			xtype: 'compositefield',
					        	    			fieldLabel : '<font color="red">*</font>'+convertFontCss("居住地址"),
											    anchor:'99%',
												items: [{
													    xtype : 'combo',
														hiddenName :'HOME_ADDR',
														id : 'HOME_ADDR',
														emptyText:'请选择',
														store : conStore,
														width:200,
														resizable : true,
														valueField : 'key',
														allowBlank:false,
														displayField : 'value',
														mode : 'local',
														readOnly : true,
														cls : 'x-readOnly',
														valueNotFoundText :"",
														triggerAction : 'all'
											     },{//居住地址（详细内容）
												    xtype : 'textfield',
													name : 'HOME_ADDR_INFO',
													emptyText:"请输入详细居住地址",
													id : 'HOME_ADDR_INFO',
													maxLength:200,
													anchor:'90%',
													width:400,
													readOnly : true,
													cls : 'x-readOnly'
						        	   		   },{//居住地状态
										     	   xtype:'radiogroup',
												   id:'isRent',
												   columns:2,
												   width:150,
												   disabled : true,
												   items:[
												       {boxLabel:convertFontCss('租赁'),name:'isRent',inputValue:'2',
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
												       },
											           {boxLabel:convertFontCss('自有'),name:'isRent',inputValue:'1',
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
						        				}]
					        	    		}]
									}, {//居住地邮编
					        	        columnWidth:1,
					        	        layout:'form',
					        	        items:[{
											    xtype : 'numberfield',
												name : 'POST_ZIPCODE',
												id : 'POST_ZIPCODE',
												fieldLabel : convertFontCss("居住地邮编"),
												width : 200,
												readOnly : true,
												cls : 'x-readOnly',
												maxLength:50
											 }]     
					        	    },{
					        	    	columnWidth:1,
					        	    	layout:'form',
					        	    	items:[{
					        	    			xtype: 'compositefield',
					        	    			fieldLabel : '<font color="red">*</font>'+convertFontCss("邮寄地址"),
											    anchor:'90%',
												items: [{//邮寄地址同上
												     	   xtype:'checkbox',
														   id:'same',
														   boxLabel:convertFontCss("同上"),
														   name:'same',
														   anchor:'92%',
														   disabled : true,
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
														},{//邮寄地址国籍
														    xtype : 'combo',
														    hiddenName : 'MAIL_ADDR',
															id : 'MAIL_ADDR',
															store : conStore,
															anchor:'90%',
															width:150,
															resizable : true,
															valueField : 'key',
															displayField : 'value',
															emptyText:'请选择',
															mode : 'local',
															readOnly : true,
															cls : 'x-readOnly',
															valueNotFoundText :"",
															triggerAction : 'all'
														},{//邮寄详细地址
														    xtype : 'textfield',
															name : 'MAIL_ADDR_INFO',
															id : 'MAIL_ADDR_INFO',
															maxLength:200,
															width:400,
															anchor : '90%',
															readOnly : true,
															cls : 'x-readOnly',
															emptyText:"请输入详细邮寄地址"
											            }]
					        	    	}]
	        	    			},{//邮寄地邮编
				        	    	columnWidth:1,
				        	        layout:'form',
				        	        items:[{
										    xtype : 'numberfield',
											name : 'MAIL_ZIPCODE',
											id : 'MAIL_ZIPCODE',
											fieldLabel : convertFontCss("邮寄邮编"),
											readOnly : true,
											cls : 'x-readOnly',
											width : 200,
											maxLength:50
										 }]
				        	    },{
				        	    	columnWidth:1,
				        	    	layout:'form',
				        	    	items:[{
				        	    			xtype: 'compositefield',
										    fieldLabel : '<font color="red">*</font>'+convertFontCss("移动电话"),
										    anchor:'95%',
											items: [{ 
												       xtype : 'combo',
													   hiddenName : 'mbPhone',
													   anchor : '95%',
													   id:'mbPhone',
													   emptyText:'请选择',
													   store:globalRoamingStore,
													   width: 200,
													   resizable : true,
													   valueField : 'value',
													   displayField : 'key',
													   mode : 'local',
													   valueNotFoundText :"",
													   readOnly : true,
													   cls : 'x-readOnly',
													   triggerAction : 'all'
									  			},{
													xtype : 'textfield',
													name : 'QUYUMA',
													id:'QUYUMA',
													anchor : '95%',
													readOnly:true,
													width:50,
													cls:'x-readOnly'
											    },{
											        xtype : 'numberfield',
												    emptyText:"此电话将用于接受短信验证码和账务变通通知",
													name : 'mbPHONENUM',
													id : 'mbPHONENUM',
													anchor : '95%',
													width:250,
													readOnly : true,
													cls : 'x-readOnly',
													maxLength:100
											    }
											]
				        	    		    }]
				        	    	},{
				        	    	   columnWidth:1,
				        	    	   layout:'form',
				        	    	   items:[{
				        	    			   xtype: 'compositefield',
										       fieldLabel : convertFontCss("其他电话"),
										       anchor:'99%',
										       items:[{//其他电话1类型选择
										  				   xtype : 'combo',
														   hiddenName : 'ORTHERPHONE',
														   id:'ORTHERPHONE',
														   anchor:'92%',
														   width:75,
														   emptyText: "请选择",
														   store:store_contmethTypes,
														   valueField : 'key',
														   displayField : 'value',
														   mode : 'local',
														   valueNotFoundText :"",
														   triggerAction : 'all',
														   readOnly : true,
														   cls : 'x-readOnly'
										 			 },{//其他电话1国籍
															xtype : 'combo',
															hiddenName : 'PHONE_CITIZENSHIP',
															anchor : '90%',
															width:120,
															emptyText:'请选择',
															id:'PHONE_CITIZENSHIP',
															store : globalRoamingStore,
															resizable : true,
															valueField : 'value',
															displayField : 'key',
															mode : 'local',
															readOnly : true,
															cls : 'x-readOnly',
															triggerAction : 'all'
										  			 },{
															xtype : 'textfield',
															name : 'QUYUMA1',
															id:'QUYUMA1',
															anchor:'90%',
															width:50,
															readOnly:true,
															cls:'x-readOnly'
										 			 },{
													        xtype : 'label',
													        html : '<font style="font-size:14px;">-区域码</font>'
													 },{//其他电话1区域码
														    xtype : 'textfield',
															name : 'QUYUMA2',
															id : 'QUYUMA2',
															width:50,
															readOnly : true,
															cls : 'x-readOnly',
															anchor:'90%'
													 },{
													        xtype : 'label',
													        html : '<font style="font-size:14px;">-电话</font>'
													 },{//其他电话1电话号
														    xtype : 'numberfield',
															name : 'PHONENUM1',
															id : 'PHONENUM1',
															anchor : '50%',
															readOnly : true,
															cls : 'x-readOnly',
															width:250,
															maxLength:100
													 }]
				        	    	      }]
			    	    		},{
			    	    		    columnWidth:1,
				    	    	   layout:'form',
				    	    	   items:[{
				    	    			   xtype: 'compositefield',
									       anchor:'99%',
									       items:[{//其他电话2类型选择
												   xtype : 'combo',
												   anchor :'92%',
												   hiddenName : 'ORTHERPHONE1',
												   emptyText:"请选择",
												   width:75,
											       editable:false,
												   id:'ORTHERPHONE1',
												   store:store_contmethTypes,
												   resizable : true,
												   valueField : 'key',
												   displayField : 'value',
												   mode : 'local',
												   triggerAction : 'all' ,
												   readOnly : true,
												   cls : 'x-readOnly'
									 			 },{//其他电话2国籍选择
													xtype : 'combo',
													anchor : '90%',
													width:120,
													hiddenName : 'PHONE_CITIZENSHIP1',
													emptyText:"请选择",
													id:'PHONE_CITIZENSHIP1',
													store : globalRoamingStore,
													resizable : true,
													valueField : 'value',
													displayField : 'key',
													mode : 'local',
													triggerAction : 'all',
													readOnly : true,
													cls : 'x-readOnly'
												 },{//其他电话2国际区号
													xtype : 'textfield',
													anchor : '90%',
													width:50,
													name : 'QUYUMA3',
													id:'QUYUMA3',
													readOnly:true,
													cls:'x-readOnly'
												 },{
											        xtype : 'label',
											        html : '<font style="font-size:14px;">-区域码</font>'
												 },{//其他电话2区域码
												    xtype : 'textfield',
													id : 'QUYUMA4',
													anchor : '90%',
													width:50,
													readOnly : true,
													cls : 'x-readOnly'
												 },{
											        xtype : 'label',
											        html : '<font style="font-size:14px;">-电话</font>'
												 },{//其他电话2电话号码
												    xtype : 'numberfield',
													name : 'PHONENUM2',
													id : 'PHONENUM2',
													anchor : '50%',
													width:250,
													maxLength:100,
													readOnly : true,
													cls : 'x-readOnly'
												 }]
										}]
					    	 },{//职业资料
								   columnWidth:0.6,
				        	       layout: 'form',
				        	       items:[{
								     	   xtype:'radiogroup',
										   id:'JOBINFO',
										   fieldLabel:'<font color="red">*</font>'+convertFontCss("职业资料"),
										   columns:4,
										   allowBlank:false,
										   anchor:'90%',
										  autoWidth : true,
										  disabled : true,
										   items:[
										   		{boxLabel:convertFontCss('全日制雇员'),name:'JOBINFO',inputValue:'04',
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
										   		},
									           {boxLabel:convertFontCss('自雇'),name:'JOBINFO',inputValue:'05',
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
									           },
									           {boxLabel:convertFontCss('退休'),name:'JOBINFO',inputValue:'10',
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
									           },
									           {boxLabel:convertFontCss('其他'),name:'JOBINFO',inputValue:'99',
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
					        	        }]
							},{//职业资料其他备注
							  	columnWidth:.40,
								 layout:'form',
								 items:[{
								    xtype : 'textfield',
								    fieldLabel : convertFontCss("（请具体注明）"),
									name : 'JOBREMARK',
									id : 'JOBREMARK',
									width:200,
									maxLength:100,
									readOnly : true,
									cls : 'x-readOnly'
							    }]
						   },{//职业资料全日制单位名称
							  columnWidth:0.5,
								 layout:'form',
								 items:[{
								    xtype : 'textfield',
								    fieldLabel : '<font color=red>*</font>'+convertFontCss("单位名称"),
									name : 'JOBNAME',
									id : 'JOBNAME',
									width : 200,
									readOnly : true,
									cls : 'x-readOnly'
								 }]
						  },{//职业资料全日制职位
							  columnWidth:0.5,
								 layout:'form',
								 items:[{
								    xtype : 'textfield',
								    fieldLabel : '<font color=red>*</font>'+convertFontCss("职位"),
									name : 'JOB',
									id : 'JOB',
									width : 200,
									readOnly : true,
									cls : 'x-readOnly'
								 }]
						  },{//电子邮箱E-mail
							  columnWidth:0.5,
								 layout:'form',
								 items:[{
								    xtype : 'textfield',
								    fieldLabel : convertFontCss("电子邮箱E-mail"),
									name : 'EMAIL',
									id : 'EMAIL',
									width : 200,
								    vtype:'email',
								    readOnly : true,
									cls : 'x-readOnly'
								 }]
						 },{//在我行有无关联人
			        	       columnWidth:0.5,
			        	       layout:'form',
			        	       items:[{
							     	   xtype:'radiogroup',
									   id:'HASRELATED',
									   fieldLabel:'<font color="red">*</font>'+convertFontCss("在我行有无关联人"),
									   columns:2,
									   disabled : true,
									   anchor:'70%',
									   items:[
									       {boxLabel:'有',name:'HASRELATED',inputValue:1,
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
									       },
								           {boxLabel:'无',name:'HASRELATED',inputValue:0,
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
					        	  }]
					     },{//关联人姓名
							  columnWidth:0.5,
								 layout:'form',
								 items:[{
									 fieldLabel:'<font color="red">*</font>'+convertFontCss("关联人姓名"),
								    xtype : 'textfield',
									name : 'RELATEDNAME',
									id : 'RELATEDNAME',
									width:200,
									hidden : true,
									readOnly : true,
									cls : 'x-readOnly'
								 }]
						 },{//与关联人关系
							  columnWidth:0.5,
								 layout:'form',
								 items:[{
									 fieldLabel:convertFontCss("与关联人关系"),
								    xtype : 'textfield',
									name : 'RELATION',
									id : 'RELATION',
									readOnly : true,
									hidden : true,
									cls : 'x-readOnly',
									width:200
								 }]
						 },{ //与我行关联关系
							 columnWidth:0.5,
								 layout:'form',
								 items:[{
									 	 fieldLabel:'<font color="red">*</font>'+convertFontCss("与我行关联关系"),
							        	 xtype : 'combo',
							        	 hiddenName : 'RELATION1',
							        	 id:'RELATION1',
							        	 emptyText:"请选择",
							        	 store : staffinStore,
							        	 resizable : true,
							        	 valueField : 'key',
							        	 displayField : 'value',
										 mode : 'local',
										valueNotFoundText :"",
										 triggerAction : 'all',
										 width : 200 ,
										 readOnly : true,
										 cls : 'x-readOnly'
									 }]
					 	}, {//来源渠道
					    	 columnWidth:0.5,
							 layout:'form',
							 items:[{
									xtype : 'combo',
									hiddenName : 'SOURCECHANNEL',
									id :'SOURCECHANNEL',
									fieldLabel : '<font color="red">*</font>'+convertFontCss("来源渠道"),
									emptyText:"请选择",
								    editable:false,
									store : store_channels,
									resizable : true,
									valueField : 'key',
									displayField : 'value',
									mode : 'local',
									triggerAction : 'all',
									readOnly : true,
									cls : 'x-readOnly',
									width : 200 
							 		}]
							 }, {//风险国别代码
							    	columnWidth:0.5,
									layout:'form',
									items:[{
											xtype:'combo',
											width : 200,
											fieldLabel:'<font color="red">*</font>'+convertFontCss("风险国别代码"),
											name:'RISK_NATION_CODE',
											id:'RISK_NATION_CODE',
											hiddenName:'RISK_NATION_CODE',
											store:riskStore,
											resizable : true,
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											readOnly : true,
											cls : 'x-readOnly',
											triggerAction : 'all'
									 }]
						   }, {//所属客户经理
					  	    	 layout:'form',
					  	    	 columnWidth:0.5,
					  	    	 items:[{
										 xtype : 'textfield',
										 id : 'customManager',
										 hiddenName : 'customManager',
										 width : 200,
										 fieldLabel : '<font color=red>*</font>'+convertFontCss("所属客户经理"),
										readOnly : true,
										cls : 'x-readOnly'			      
						  	         }]
						  }]
					}]
				}]
			},{
			layout : 'column',
			items :[{
				title : '<font style="font-size:16px;">联名户信息</font>',
				columnWidth : 1,
				id : 'lianminghu',
				xtype : 'fieldset',
				style : 'margin:0 180px;',
				items : [{
					layout : 'column',
					items : [{
						columnWidth : 0.5,
						layout : 'form',
						items : [{
									 xtype : 'textfield',
									 id : 'lianMinPinYin',
									 width : 200,
									 fieldLabel : '<font color=red>\*</font>'+convertFontCss("联名户姓名拼音"),
									 readOnly : true,
									 cls : 'x-readOnly'
								}]
						},{
							 columnWidth:0.5,
							 layout: 'form',
							 items:[{
							   xtype:'radiogroup',
							   id:'sex',
							   fieldLabel:'<font color=red>\*</font>'+convertFontCss("联名户性别"),
							   columns:2,
							   disabled : true,
							   anchor:'60%',
							   items:[
									  {boxLabel:'男',name:'sex',inputValue:'1',
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
									  },
									  {boxLabel:'女',name:'sex',inputValue:'2',
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
							}]
						},{
							 columnWidth:0.5,
							 layout: 'form',
							 items:[{
								xtype : 'combo',
								hiddenName : 'CITIZENSHIP1',
								id : 'CITIZENSHIP1',
								fieldLabel : '<font color="red">*</font>'+convertFontCss("联名户国籍"),
								width:200,
								emptyText:"请选择",
								store : conStore,
								resizable : true,
								valueField : 'key',
								displayField : 'value',
								mode : 'local',
								readOnly : true,
								cls : 'x-readOnly',
								triggerAction : 'all'
							 }]
						},{
							 columnWidth:0.5,
							 layout: 'form',
							 items:[{
								xtype : 'combo',
								id : 'CITIZENSHIP2',
								hiddenName : 'CITIZENSHIP2',
								fieldLabel : convertFontCss("发证机关所在地"),
								emptyText:"请选择",
								width:200,
								store : dqStore,
								resizable : true,
								valueField : 'key',
								displayField : 'value',
								mode : 'local',
								readOnly : true,
								cls : 'x-readOnly',
								triggerAction : 'all'
							 }]
						},{
							 columnWidth:0.5,
							 layout: 'form',
							 items:[{
								xtype : 'combo',
								hiddenName : 'lianMingIdenType1',
								id : 'lianMingIdenType1',
								fieldLabel : '<font color="red">*</font>'+convertFontCss("证件类型"),
								width:200,
								emptyText:"请选择",
								store : store_ident,
								resizable : true,
								valueField : 'key',
								displayField : 'value',
								mode : 'local',
								readOnly:true,
								 cls:'x-readOnly',
								triggerAction : 'all'
							 }]
						},{
							 columnWidth:0.5,
							 layout: 'form',
							 items:[{
								 xtype : 'textfield',
								 id : 'lianMingIdenNo1',
								 width : 200,
								 fieldLabel : '<font color=red>\*</font>'+convertFontCss("证件号码"),
								 readOnly:true,
								 cls:'x-readOnly'
							 }]
						},{
							columnWidth:1,
							layout:'form',
							items:[{
								xtype: 'compositefield',
								anchor:'90%',
								items: [{//有效日期
											 xtype : 'datefield',
											 id:'LEGAL_EXPIRED_DATE2',
											 width : 200,
											 name : 'LEGAL_EXPIRED_DATE2',
											 readOnly : true,
											 cls : 'x-readOnly',
											 fieldLabel : '<font color="red">*</font>'+convertFontCss("有效日期"),
											 format:'Y-m-d'
									   },{//是否长期有效
										   xtype:'checkbox',
										   id:'LONGTERM2',
										   disabled : true,
										   boxLabel:convertFontCss("长期有效"),
										   name:'LONGTERM2',
										   inputValue:'1',
										   listeners:{
										  		'afterrender' : function(){
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
					}]},{
						 columnWidth:0.5,
						 layout: 'form',
						 items:[{
							 xtype : 'textfield',
							 width : 200,
							 fieldLabel : '<font color=red>\*</font>'+convertFontCss("台湾身份证"),
							 name:'lianMingTwIdentNum1',
							 id:'lianMingTwIdentNum1',
							 readOnly:true,
							 cls:'x-readOnly'
						 }]
					},{
						 columnWidth:0.5,
						 layout: 'form',
						 items:[{
							 xtype : 'textfield',
							 width : 200,
							 fieldLabel : '<font color=red>\*</font>'+convertFontCss("港澳身份证"),
							 name:'lianMingGaIdentNum1',
							 id:'lianMingGaIdentNum1',
							 readOnly:true,
							 cls:'x-readOnly'
						 }]
					},{
						columnWidth:1,
						layout:'form',
						items:[{
							id:'dateCompositefield2',
							xtype: 'compositefield',
							//hidden:true,
							anchor:'90%',
							items: [{//有效日期
										 xtype : 'datefield',
										 id:'LEGAL_EXPIRED_DATE1',
										 width : 200,
										 name : 'LEGAL_EXPIRED_DATE1',
										 readOnly : true,
										 cls : 'x-readOnly',
										 fieldLabel : '<font color="red">*</font>'+convertFontCss("有效日期"),
										 format:'Y-m-d'
								   },{//是否长期有效
									   xtype:'checkbox',
									   id:'LONGTERM1',
									   boxLabel:convertFontCss("长期有效"),
									   name:'LONGTERM1',
									   disabled : true,
									   inputValue:'1',
									   listeners:{
									  		'afterrender' : function(){
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
						}]}]
				}]
			}]
		},{
			layout : 'column',
			items : [{
				title : '<font style="font-size:16px;">本人声明</font>',
				columnWidth : 1,
				//id : 'shengming',
				xtype : 'fieldset',
				 style:'margin:0px 180px 30px 180px;',
				items : [{
					layout : 'column',
					items : [{
						columnWidth:1,
		  		      	xtype:'fieldset',
		  		      	id : 'shengming',
		  		      	title:'<font style="font-size:16px;">证件1：</font>',
		  		      	style:'margin:10px 20px 20px 10px;',
		  		      	items:[{
		  		      		columnWidth:1,
			        	       layout:'form',
			        	       items:[{
							     	   xtype:'radiogroup',
									   columns:1,
									   id : 'radio1',
									   anchor:'90%',
									   disabled : true,
									   items:[
									       {boxLabel:convertFontCss('1、仅为中国税收居民'),name:'radio1',inputValue:'01',
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
									       },
								           {boxLabel:convertFontCss('2、仅为非居民'),name:'radio1',inputValue:'02',
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
								           },
								           {boxLabel:convertFontCss('3、既是中国税收居民又是其他国税收居民'),name:'radio1',inputValue:'03',
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
				        	        }]
				        	
						   },{
								  layout:'column',
								  columnWidth:1,
								  id:'shengMing',
								  style:'margin-left:80px;',
								  items:[{
				  	        	  layout:'form',
					  		      columnWidth:1,
					  		      labelWidth:100,
					  		      items:[{
						  		    	      xtype:'tbtext',
						  		    	      text:'<br/><b>'+convertFontCss("如您在以上选项中勾选第2项或者第3项，请填写下列信息：")+'</b><br/>'
					  		     		 }]
							  	         },{
							  	         	columnWidth:1,
							  	         	layout:'hbox',
							  	         	items	: [{
							  	         		width	: 240,
							  	         		labelWidth	: 140,
							        	       layout:'form',
							        	       items:[{
											     	   xtype:'radiogroup',
													   id:'isUNtaxpayer',
													    width	: 70,
													   fieldLabel:convertFontCss("1)是否为美国纳税人"),
													   disabled : true,
													   items:[
													       {boxLabel:'是',name:'isUNtaxpayer',inputValue:'1',
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
													       },
												           {boxLabel:'否',name:'isUNtaxpayer',inputValue:'2',
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
							        	        }]
							  	         	},{
										     width	: 300,
							  	         	 labelWidth	: 100,
											 layout:'form',
											 items:[{
												    xtype : 'textfield',
													name : 'USTIN',
													id : 'USTIN',
													fieldLabel:convertFontCss("US TIN/SSN"),
													readOnly : true,
													cls : 'x-readOnly'
												 }]
							  	         }]
						        	       
							           	},{
													columnWidth : 1,
													layout : 'form',
													items : [{
														xtype : 'tbtext',
														style : 'margin-left:10px;',
														text : convertFontCss("2)请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号(TIN)")
													}]
												},{
										  	        id:'exceptionHand',
										  	    	columnWidth:1,
										  	    	layout:'form',
										  	    	xtype: 'panel',
										  	    	items:[]
										  	    },{
													  columnWidth:1,
									  	        	  layout:'form',
										  		      items:[{
										  		    	      xtype:'tbtext',
										  		    	      text:'<br/>'+convertFontCss("如您不能提供居民国（地区）纳税人识别号，请选择原因(TIN)")+'<br/>'
											  		      }]
										  	    },{
										  		      layout:'form',
										  		      columnWidth:1,
										  		      labelWidth:1,
										  		      hideLabel : true,
										  		      style: 'margin-left:30px;',
										  		      items:[{
													     	   xtype:'checkbox',
															   id:'REASON',
															   disabled : true,
															   boxLabel:convertFontCss("居民国（地区）不发放纳税人识别号"),
															   listeners:{
															  		'afterrender' : function(){
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
															  	   xtype:'checkbox',
																   id:'REASON2',
																   disabled : true,
																   boxLabel:convertFontCss("账户持有人未能取得纳税人识别号"),
																   listeners:{
																  		'afterrender' : function(){
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
										  	    	 layout:'form',
										  	    	 columnWidth:1,
										  	    	 items:[{
														 xtype : 'textarea',
														 width : '500',
														 id :'detailReason',
														 fieldLabel : convertFontCss("请解释具体原因"),
														 readOnly : true,
														 cls : 'x-readOnly'
										  	         }]
										  	     }]
								}]
		  		      	},{
						columnWidth:1,
		  		      	xtype:'fieldset',
		  		      	id : 'shengming2',
		  		      	title:'<font style="font-size:16px;">证件2：</font>',
		  		      	style:'margin:0px 20px 20px 10px;',
		  		      	items:[{
		  		      		columnWidth:1,
			        	       layout:'form',
			        	       items:[{
							     	   xtype:'radiogroup',
									   columns:1,
									   id : 'radio2',
									   anchor:'90%',
									   disabled : true,
									   items:[
									       {boxLabel:convertFontCss('1、仅为中国税收居民'),name:'radio2',inputValue:'01',
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
									       },
								           {boxLabel:convertFontCss('2、仅为非居民'),name:'radio2',inputValue:'02',
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
								           },
								           {boxLabel:convertFontCss('3、既是中国税收居民又是其他国税收居民'),name:'radio2',inputValue:'03',
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
				        	        }]
				        	
						   },{
								  layout:'column',
								  columnWidth:1,
								  id:'shengMing2',
								  style:'margin-left:80px;',
								  items:[{
				  	        	  layout:'form',
					  		      columnWidth:1,
					  		      labelWidth:100,
					  		      items:[{
						  		    	      xtype:'tbtext',
						  		    	      text:'<br/><b>'+convertFontCss("如您在以上选项中勾选第2项或者第3项，请填写下列信息：")+'</b><br/>'
					  		     		 }]
							  	         },{
							  	         	columnWidth:1,
							  	         	layout:'hbox',
							  	         	items	: [{
							  	         	   width	: 240,
							  	         	   labelWidth	: 140,
							        	       layout:'form',
							        	       items:[{
											     	   xtype:'radiogroup',
											     	   width	: 70,
													   id:'isUNtaxpayer2',
													   fieldLabel:convertFontCss("1)是否为美国纳税人"),
													   disabled : true,
													   items:[
													       {boxLabel:'是',name:'isUNtaxpayer2',inputValue:'1',
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
													       },
												           {boxLabel:'否',name:'isUNtaxpayer2',inputValue:'2',
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
							        	        }]
							  	         	},{
										     width	: 300,
							  	         	 labelWidth	: 100,
											 layout:'form',
											 items:[{
												    xtype : 'textfield',
													name : 'USTIN2',
													id : 'USTIN2',
													fieldLabel:convertFontCss("US TIN/SSN"),
													readOnly : true,
													cls : 'x-readOnly'
												 }]
							  	         	}]
							           	},{
													columnWidth : 1,
													layout : 'form',
													items : [{
														xtype : 'tbtext',
														style : 'margin-left:10px;',
														text : convertFontCss("2)请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号(TIN)")
													}]
												},{
										  	        id:'exceptionHand2',
										  	    	columnWidth:1,
										  	    	layout:'form',
										  	    	xtype: 'panel',
										  	    	items:[]
										  	    },{
													  columnWidth:1,
									  	        	  layout:'form',
										  		      items:[{
										  		    	      xtype:'tbtext',
										  		    	      text:'<br/>'+convertFontCss("如您不能提供居民国（地区）纳税人识别号，请选择原因(TIN)")+'<br/>'
											  		      }]
										  	    },{
										  		      layout:'form',
										  		      columnWidth:1,
										  		      labelWidth:1,
										  		      hideLabel : true,
										  		      style: 'margin-left:30px;',
										  		      items:[{
													     	   xtype:'checkbox',
															   id:'REASON3',
															   disabled : true,
															   boxLabel:convertFontCss("居民国（地区）不发放纳税人识别号"),
															   listeners:{
															  		'afterrender' : function(){
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
															  	   xtype:'checkbox',
																   id:'REASON4',
																   disabled : true,
																   boxLabel:convertFontCss("账户持有人未能取得纳税人识别号"),
																   listeners:{
																  		'afterrender' : function(){
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
										  	    	 layout:'form',
										  	    	 columnWidth:1,
										  	    	 items:[{
														 xtype : 'textarea',
														 width : '500',
														 id :'detailReason2',
														 fieldLabel : convertFontCss("请解释具体原因"),
														 readOnly : true,
														 cls : 'x-readOnly'
										  	         }]
										  	     }]
								}]
		  		      	}]
				}]
		
			}]
		}],
		buttons : [{
					text : '下一页',
					handler : function(btn) {
						qzComInfo.setActiveTab(1);
					}
				}]
	});
	
	//查询客户信息 第一页
	qzComStore.load({
		params : {
			custId : custId
		},
		callback : function() {
			if (this.getCount() != 0) {
				var record = this.getAt(0);
				
				// 国籍 ||居住地址(国籍)|| 邮寄地址（国籍）|| 风险国别代码
				if (conStore.getCount() == 0) {
					conStore.load({
								callback : function() {
									Ext.getCmp("citizenship").setValue(record.get("CITIZENSHIP"));
									Ext.getCmp("HOME_ADDR").setValue(record.get("HOME_ADDR"));
									Ext.getCmp("MAIL_ADDR").setValue(record.get("MAIL_ADDR"));
									Ext.getCmp("birthLocale").setValue(record.get("MAIL_ADDR"));
								}
							});
				} else {
					Ext.getCmp("citizenship").setValue(record.get("CITIZENSHIP"));
					Ext.getCmp("HOME_ADDR").setValue(record.get("HOME_ADDR"));
					Ext.getCmp("MAIL_ADDR").setValue(record.get("MAIL_ADDR"));
					Ext.getCmp("birthLocale").setValue(record.get("MAIL_ADDR"));
				}
				
				// 风险国别代码
				if (riskStore.getCount() == 0) {
					riskStore.load({
								callback : function() {
									Ext.getCmp("RISK_NATION_CODE").setValue(record.get("RISK_NATION_CODE"));
								}
							});
				} else {
					Ext.getCmp("RISK_NATION_CODE").setValue(record.get("RISK_NATION_CODE"));
				}
				
				// 出生地
				Ext.getCmp("birthLocale").setValue(record.get("BIRTHLOCALE"));
				
				// 证件类型1
				if (store_ident.getCount() == 0) {
					store_ident.load({
								callback : function() {
									Ext.getCmp("identityType3").setValue(record.get("identityType3"));
								}
							});
				} else {
					Ext.getCmp("identityType3").setValue(record.get("identityType3"));
				}
				
				if(record.get("identityType3") == '6'){//台胞证(台湾同胞来往内地通行证)
					Ext.getCmp('chizhengcishu').setVisible(true);
				}
				
				if (record.get("longterm") == '1') {// 证件1是否长期有效
					Ext.getCmp("longterm").checked = true;
				} else {
					Ext.getCmp("longterm").checked = false;
				}

				// 证件类型2
				if (record.get("twIdentNum3") == ""
						&& record.get("gaIdentNum3") == "") {
					Ext.getCmp("dateCompositefield").setVisible(false);
					Ext.getCmp("youxiaoqixian").setVisible(false);
					Ext.getCmp("longterm2").setVisible(false);
				}else{
					if (record.get("longterm2") == '1') {// 证件2是否长期有效
						Ext.getCmp("longterm2").checked = true;
					} else {
						Ext.getCmp("longterm2").checked = false;
					}
				}

				if (record.get("twIdentNum3") == "") {
					Ext.getCmp("twIdentNum3").setVisible(false);
					Ext.getCmp("chizhengcishu").setVisible(false);
				}
				if (record.get("gaIdentNum3") == "") {
					Ext.getCmp("gaIdentNum3").setVisible(false);
				}
				
				
				// 发证机关所在地
				if (dqStore.getCount() == 0) {
					dqStore.load({
								callback : function() {
									Ext.getCmp("qianfajiguan").setValue(record.get("qianfajiguan"));
								}
							});
				} else {
					Ext.getCmp("qianfajiguan").setValue(record.get("qianfajiguan"));
				}
				// 移动电话
				if (globalRoamingStore.getCount() == 0) {
					globalRoamingStore.load({
								callback : function() {
									Ext.getCmp("mbPhone").setValue(record.get("mbPhone"));
									Ext.getCmp("PHONE_CITIZENSHIP").setValue(record.get("PHONE_CITIZENSHIP"));
									Ext.getCmp("PHONE_CITIZENSHIP1").setValue(record.get("PHONE_CITIZENSHIP1"));
								}
							});
				} else {
					Ext.getCmp("mbPhone").setValue(record.get("mbPhone"));
					Ext.getCmp("PHONE_CITIZENSHIP").setValue(record.get("PHONE_CITIZENSHIP"));
					Ext.getCmp("PHONE_CITIZENSHIP1").setValue(record.get("PHONE_CITIZENSHIP1"));
				}
				
				//其他电话
				if (store_contmethTypes.getCount() == 0) {
					store_contmethTypes.load({
								callback : function() {
									Ext.getCmp("ORTHERPHONE").setValue(record.get("ORTHERPHONE"));
									Ext.getCmp("ORTHERPHONE1").setValue(record.get("ORTHERPHONE1"));
								}
							});
				} else {
					Ext.getCmp("ORTHERPHONE").setValue(record.get("ORTHERPHONE"));
					Ext.getCmp("ORTHERPHONE1").setValue(record.get("ORTHERPHONE1"));
				}
				

				// 职业资料
				if (record.get("JOBINFO") != '') {
					var jobInfo = record.get("JOBINFO");
					if (jobInfo == '04') {
						Ext.getCmp('JOBREMARK').setVisible(false);
						Ext.getCmp('JOBNAME').setVisible(true);
						Ext.getCmp('JOB').setVisible(true);
						if (record.get("JOBNAME") != '') {
							Ext.getCmp('JOBNAME').setValue(record.get("JOBNAME"));
						}
						if (record.get("JOB") != '') {
							Ext.getCmp('JOB').setValue(record.get("JOB"));
						}
					} else if (jobInfo == '99') {
						Ext.getCmp('JOBREMARK').setVisible(true);
						Ext.getCmp('JOBNAME').setVisible(false);
						Ext.getCmp('JOB').setVisible(false);
					} else {
						Ext.getCmp('JOBREMARK').setVisible(false);
						Ext.getCmp('JOBNAME').setVisible(false);
						Ext.getCmp('JOB').setVisible(false);
					}
				}

				// 在我行有无关联人
				if (record.get("HASRELATED") != '') {
					var HASRELATED = record.get("HASRELATED");
					if (HASRELATED == 1) {
						Ext.getCmp('RELATEDNAME').setVisible(true);
						if (record.get("RELATEDNAME") != '') {
							Ext.getCmp('RELATEDNAME').setValue(record.get("RELATEDNAME"));
						}
						Ext.getCmp('RELATION').setVisible(true);
						if (record.get("RELATION") != '') {
							Ext.getCmp('RELATION').setValue(record.get("RELATION"));
						}
					} else {
						Ext.getCmp('RELATEDNAME').setVisible(false);
						Ext.getCmp('RELATION').setVisible(false);
					}
				}

				// 与我行关联关系
				if (staffinStore.getCount() == 0) {
					staffinStore.load({
								callback : function() {
									Ext.getCmp("RELATION1").setValue(record.get("RELATION1"));
								}
							});
				} else {
					Ext.getCmp("RELATION1").setValue(record.get("RELATION1"));
				}
				
				//来源渠道
				if(store_channels.getCount() == 0){
					store_channels.load({
						callback : function(){
							Ext.getCmp("SOURCECHANNEL").setValue(record.get("SOURCECHANNEL"));
						}
					})
				}else{
					Ext.getCmp("SOURCECHANNEL").setValue(record.get("SOURCECHANNEL"));
				}
				

				// 是否是联名户
				if (record.get("isLianMingHu") == '0') {
					isLianMingHu = '0';
					Ext.getCmp("isLianMingHu").setValue(false);
					Ext.getCmp('lianminghu').setVisible(false);
					//Ext.getCmp('shengming').setVisible(false);
					Ext.getCmp('shengming2').setVisible(false);
					Ext.getCmp('shengming').el.setStyle({'border':0,title:''});
					Ext.getCmp('shengming').setTitle("");
				} else {
					isLianMingHu = '1';
					Ext.getCmp("isLianMingHu").setValue(true);
					// 联名户国籍
					if (conStore.getCount() == 0) {
						conStore.load({
									callback : function() {
										Ext.getCmp("CITIZENSHIP1").setValue(record.get("CITIZENSHIP1"));
									}
								});
					} else {
						Ext.getCmp("CITIZENSHIP1").setValue(record.get("CITIZENSHIP1"));
					}

					// 证件类型1
					if (store_ident.getCount() == 0) {
						store_ident.load({
									callback : function() {
										Ext.getCmp("lianMingIdenType1").setValue(record.get("lianMingIdenType1"));
									}
								});
					} else {
						Ext.getCmp("lianMingIdenType1").setValue(record.get("lianMingIdenType1"));
					}

					if (record.get("lianMingTwIdentNum1") == ""
							&& record.get("lianMingGaIdentNum1") == "") {
						Ext.getCmp("dateCompositefield2").setVisible(false);
						Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(false);
						Ext.getCmp("LONGTERM1").setVisible(false);
					}

					if (record.get("lianMingTwIdentNum1") == "") {
						Ext.getCmp("lianMingTwIdentNum1").setVisible(false);
					}
					if (record.get("lianMingGaIdentNum1") == "") {
						Ext.getCmp("lianMingGaIdentNum1").setVisible(false);
					}

					if (record.get("LONGTERM2") == '1') {// 证件1是否长期有效
						Ext.getCmp("LONGTERM2").checked = true;
					} else if (record.get("LONGTERM2") == '0') {
						Ext.getCmp("LONGTERM2").checked = false;
					}
					if (record.get("LONGTERM1") == '1') {// 证件2是否长期有效
						Ext.getCmp("LONGTERM1").checked = true;
					} else if (record.get("LONGTERM1") == '0') {
						Ext.getCmp("LONGTERM1").checked = false;
					}

					// 联名户证件发证机关所在地
					if (dqStore.getCount() == 0) {
						dqStore.load({
									callback : function() {
										Ext.getCmp("CITIZENSHIP2").setValue(record.get("CITIZENSHIP2"));
									}
								});
					} else {
						Ext.getCmp("CITIZENSHIP2").setValue(record.get("CITIZENSHIP2"));
					}
					
					//从户本人声明
					var radio2 = record.get("radio2");
					if (radio2 != '') {
						if (radio2 == '02' || radio2 == '03') {
							Ext.getCmp("shengMing2").setVisible(true);
							var isUNtaxpayer2 = record.get("isUNtaxpayer2");
							if (isUNtaxpayer2 != '') {
								if (isUNtaxpayer2 == '1') {
									Ext.getCmp('USTIN2').setVisible(true);
								} else if (isUNtaxpayer2 == '2') {
									Ext.getCmp('USTIN2').setVisible(false);
								}
							}
	
							var juMinGuo2 = record.get("taxCountrys2");
							var tin2 = record.get("tins2");
							if (juMinGuo2) {
								var junMinGuoArr2 = juMinGuo2.split("-");
								var tinArr2 = tin2.split("-");
								taxManager2.initSeq();
								for (var i = 0; i < tinArr2.length - 1; i++) {
									taxManager2.add({
												area : junMinGuoArr2[i],
												tin : tinArr2[i]
											});
								}
							}
	
						} else if (radio2 == '01') {
							Ext.getCmp("shengMing2").setVisible(false);
						}
					}
				}

				// 主户本人声明
				var radio1 = record.get("radio1");
				if (radio1 != '') {
					if (radio1 == '02' || radio1 == '03') {
						Ext.getCmp("shengMing").setVisible(true);
						var isUNtaxpayer = record.get("isUNtaxpayer");
						if (isUNtaxpayer != '') {
							if (isUNtaxpayer == '1') {
								Ext.getCmp('USTIN').setVisible(true);
							} else if (isUNtaxpayer == '2') {
								Ext.getCmp('USTIN').setVisible(false);
							}
						}

						var juMinGuo = record.get("taxCountrys");
						var tin = record.get("tins");
						if (juMinGuo) {
							var junMinGuoArr = juMinGuo.split("-");
							var tinArr = tin.split("-");
							taxManager.initSeq();
							for (var i = 0; i < tinArr.length - 1; i++) {
								taxManager.add({
											area : junMinGuoArr[i],
											tin : tinArr[i]
										});
							}
						}

					} else if (radio1 == '01') {
						Ext.getCmp("shengMing").setVisible(false);
					}
				}
				
				qzCombaseInfo.getForm().loadRecord(record);
			}
		}
	});

	/**
	 * 查询第二页
	 */
	var qzComListsStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
					url : basepath + '/oneKeyAccountAction!queryComsecond.json',
					method : 'get'
				}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'json.count',
					root : 'json.data'
				}, ['cusCategory', 'DFCheckbox', 'isOpenCard', 'cardType',
						'cardType1_0', 'cardType2_0', 'ATMDayLimitDefault',
						'ATMDayLimit', 'ATMDayCountDefault',
						'ATMDayLimitCount', 'ATMYearLimitDefault',
						'ATMYearLimit', 'POSDefault', 'eachCustemLimit',
						'dianziBank', 'netBank', 'ukey', 'shortMessage',
						/*'phoneBank',*/ 'mobileBank', 'shortMessage2',
						'dayAccLimitDefault', 'dayAccSelfDefine',
						'dayAccCountDefault', 'dayCountSelfDefine',
						'yearAccLimitDefault', 'yearAccSelfDefine',
						'elecState', 'isEquEmail', 'email', 'chgNotice','same'])
	});

	// 账号信息
	var accountInfo = new Ext.FormPanel({
				baseCls : "x-plain",
				layout : "column",
				id : 'accountInfoPanel',
				name : 'accountInfoPanel',
				frame : true,
				items : [{
							layout : "table",
							header : false,
							//width : 1000,
							defaultType : "checkbox",
							layoutConfig : {
								columns : 2
							}// 将父容器分成2列
						}]
			});
	// 服务信息
	var serviceInfo = new Ext.FormPanel({
		baseCls : "x-plain",
		style : 'margin:0 10px;',
		layout : "column",
		id : 'serviceInfoPanel',
		name : 'serviceInfoPanel',
		frame : true,
		autoScroll : true,// 有滚动条
		items : [{
					layout : 'column',
		        	xtype:'fieldset',
		        	id : 'jiejikaFieldset',
		        	border : false,
		        	style:'margin:10px 20px 0px 10px;',
		        	width:950,
				    items: [{
							layout : "table",
							anchor : '98%',
							defaultType : "checkbox",
							layoutConfig : {columns : 2,padding : 10},// 将父容器分成2列
							items : [{
										width : 150,
										boxLabel : convertFontCss("借记卡申请"),
										inputValue : '1',
										rowspan : 7,
										name : 'jiejika',
										id : 'jiejika',
										disabled : true,
										listeners : {
											afterrender : function() {
												if(this.el){
													this.el.up("div").removeClass("x-item-disabled");
													this.el.up("div").setStyle({
														color : "#555",
														cursor: "default",
														opacity: 1,
														margin : "5px 0px"
													});
												}
											}
										}
									}, {
										xtype : 'panel',
										layout : 'column',
										id : 'cardTypePanel',
										name : 'cardTypePanel',
										width : 800,
										items : [{
											columnWidth : 0.3,
											layout : 'form',
											labelWidth : 1,
											width : 230,
											items : [{
												xtype : 'combo',
												hiddenName : 'cardType',
												id : 'cardType',
												emptyText : '请选择',
												width : 200,
												editable : false,
												store : cardTypeStore,
												anchor : "80%",
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												readOnly : true,
												cls : 'x-readOnly',
												triggerAction : 'all'
											}]
										}, {
											columnWidth : .3,
											layout : 'form',
											labelWidth : 1,
											width : 230,
											items : [{
												xtype : 'combo',
												hiddenName : 'cardType1_0',
												id : 'cardType1_0',
												width : 200,
												emptyText : '请选择',
												editable : false,
												store: cardType1Store,
												anchor : "80%",
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												readOnly : true,
												cls : 'x-readOnly',
												triggerAction : 'all'
											}]
										},{
											columnWidth : .3,
											layout : 'form',
											labelWidth : 1,
											width : 230,
											items : [{
												xtype : 'combo',
												hiddenName : 'cardType2_0',
												id : 'cardType2_0',
												emptyText : '请选择',
												editable : false,
												width : 200,
												store: cardType2Store,
												anchor : "80%",
												readOnly : true,
												cls : 'x-readOnly',
												valueField : 'key',
												displayField : 'value',
												mode : 'local',
												triggerAction : 'all'
											}]
										}]
									}, {
										xtype : 'tbtext',
										width : 1000,
										id : 'ATM',
										name : 'ATM',
										text : '<br/><b><font style="font-size:14px;">ATM转账限额设置</font></b><br/>'
									}, {
										xtype : 'panel',
										layout : 'column',
										id : 'ATMDayLimitPanel',
										name : 'ATMDayLimitPanel',
										items : [{
											columnWidth : .35,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "90%",
												id : 'ATMDayLimitDefault',
												name : 'ATMDayLimitDefault',
												width :250,
												xtype : 'radio',
												boxLabel : convertFontCss("默认每日累计限额（RMB50,000元）"),
												inputValue : 1,
												disabled : true,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .22,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'radio',
												id : 'ATMDayLimitDefine',
												name : 'ATMDayLimitDefault',
												boxLabel : convertFontCss("每日累计转账最高限额RMB"),
												disabled : true,
												inputValue : 0,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .1,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'numberfield',
												width : 80,
												id : 'ATMDayLimit',
												name : 'ATMDayLimit',
												readOnly : true,
												cls : 'x-readOnly'
											}]
										}, {
											columnWidth : .2,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "90%",
												xtype : 'tbtext',
												style : "font-size:14px;padding-top:4px;",
												text:"元（0-50,000）</br>"
											}]
										}]
									}, {
										xtype : 'panel',
										layout : 'column',
										id : 'ATMDayCountPanel',
										name : 'ATMDayCountPanel',
										items : [{
											columnWidth : .35,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "90%",
												xtype : 'radio',
												width : 250,
												boxLabel : convertFontCss("默认每日累计笔数（10笔）"),
												id : 'ATMDayCountDefault',
												name : 'ATMDayCountDefault',
												disabled : true,
												inputValue : 1,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .22,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'radio',
												boxLabel : convertFontCss("每日累计转账笔数"),
												id : 'ATMDayCountDefine',
												name : 'ATMDayCountDefault',
												disabled : true,
												inputValue : 0,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .1,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'numberfield',
												width : 80,
												id : 'ATMDayLimitCount',
												name : 'ATMDayLimitCount',
												readOnly : true,
												cls : 'x-readOnly'
											}]
										}, {
											columnWidth : .2,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor:"90%",
												xtype:'tbtext',
												text:"笔</br>",
												style:"font-size:14px;padding-top:4px;"
											}]
										}]
									}, {
										xtype : 'panel',
										layout : 'column',
										id : 'ATMYearLimitPanel',
										name : 'ATMYearLimitPanel',
										items : [{
											columnWidth : .35,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "90%",
												xtype : 'radio',
												width : 250,
												boxLabel : convertFontCss("默认每年累计限额（RMB10,000,000元）"),
												id : 'ATMYearLimitDefault',
												name : 'ATMYearLimitDefault',
												disabled : true,
												inputValue : 1,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .22,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'radio',
												boxLabel : convertFontCss("每年累计转账最高限额RMB"),
												id : 'ATMYearLimitDefine',
												name : 'ATMYearLimitDefault',
												disabled : true,
												inputValue : 0,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .1,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'numberfield',
												width : 80,
												id : 'ATMYearLimit',
												name : 'ATMYearLimit',
												readOnly : true,
												cls : 'x-readOnly'
											}]
										}, {
											columnWidth : .2,
											layout : 'form',
											labelWidth : 1,
											items : [{
												 anchor:"90%",
												 xtype:'tbtext',
												 text:"元</br>",
												 style:"font-size:14px;padding-top:4px;"
											}]
				
										}]
									}, {
										xtype : 'tbtext',
										width : 1000,
										id : 'POS',
										name : 'POS',
										text : "<b><font style='font-size:14px;'>POS消费限额设置</font></b>"
									}, {
										xtype : 'panel',
										layout : 'column',
										id : 'POSPanel',
										name : 'POSPanel',
										//width : 1000,
										items : [{
											columnWidth : .35,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "90%",
												xtype : 'radio',
												width : 250,
												boxLabel : convertFontCss("默认单笔限额（RMB500,000元）"),
												id : 'POSDefault',
												name : 'POSDefault',
												disabled : true,
												inputValue : 1,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .22,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'radio',
												boxLabel : convertFontCss("单笔消费限额RMB"),
												id : 'POSDefine',
												name : 'POSDefault',
												disabled : true,
												inputValue : 0,
												listeners:{
													'afterrender' : function(){
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
										}, {
											columnWidth : .1,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor : "98%",
												xtype : 'numberfield',
												width : 80,
												id : 'eachCustemLimit',
												name : 'eachCustemLimit',
												readOnly : true,
												cls : 'x-readOnly'
											}]
										}, {
											columnWidth : .2,
											layout : 'form',
											labelWidth : 1,
											items : [{
												anchor:"90%",
												xtype:'tbtext',
												text:"元</br>",
												style:"font-size:14px;padding-top:4px;"
											}]
										}]
									}]
						}]
			 },{
				 	layout : 'column',
		        	xtype:'fieldset',
		        	border:false,
		        	id : 'dianziBankFieldset',
		        	style:'margin:0px 20px 0px 10px;',
		        	width:950,
				    items:[{
			            	 layout:"table",
							 anchor:'98%',
							 defaultType:"checkbox",
							 layoutConfig:{columns:2,padding : 10},//将父容器分成2列
							 items:[{
									  width:150,
									  boxLabel:convertFontCss("电子银行服务"),
									  inputValue:'1',
									  rowspan:8,
									  id : 'dianziBank',
									  name:'dianziBank',
									  disabled : true,
									  listeners:{
								  		afterrender	: function(){
											if(this.el){
												this.el.up("div").removeClass("x-item-disabled");
												this.el.up("div").setStyle({
													color : "#555",
													cursor: "default",
													opacity: 1,
													margin:"5px 0px"
												});
											}
										}
								      }
				 				},{
									anchor:"98%",
									xtype:'checkbox',
									style : 'padding-left:0px',
									name : 'netBank',
									id : 'netBank',
									width:800,
									disabled : true,
									boxLabel:convertFontCss("网络银行（若不选汇款认证方式，则默认只有查询功能）"),
									inputValue:'1',
									listeners:{
										'afterrender' : function(){
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
									id:'ukeyPanel',
									name:'ukeyPanel',
									layout:'column',
									hidden:true,
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
														disabled : true,
														boxLabel:convertFontCss("U-key汇款认证"),
														inputValue:'1',
														listeners:{
															'afterrender' : function(){
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
									id : 'shortMessagePanel',
									name : 'shortMessagePanel',
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
													disabled : true,
													boxLabel:convertFontCss("短信认证"),
													inputValue:'1',
													listeners:{
														'afterrender' : function(){
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
									  disabled : true,
									  boxLabel:convertFontCss("手机银行"),
									  inputValue:'1',
									  listeners:{
										'afterrender' : function(){
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
												   disabled : true,
												  boxLabel:convertFontCss("短信验证"),
												  inputValue:'1',
												  listeners:{
													'afterrender' : function(){
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
									items:[{
										    columnWidth:.18,
											layout:'form',
											labelWidth:1,
											items:[{
														xtype:'tbtext',
													    width:150,
													    height:37.81,
													    style:'padding-top:5px',
													    text:convertFontCss("日累计转账限额")
													}]
												      
										 },{
												columnWidth:.3,
												layout:'form',
												labelWidth:1,
												items:[{
													  anchor:"90%",
													  xtype:'radio',
													  width : 150,
													  boxLabel:convertFontCss("默认无限制"),
													  id : 'dayAccLimitDefault',
												  	  name : 'dayAccLimitDefault',
												  	  disabled : true,
													  inputValue:1,
													  listeners:{
															'afterrender' : function(){
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
												columnWidth:.3,
												layout:'form',
												labelWidth:1,
												items:[{
													anchor:"98%",
													  xtype:'radio',
													  boxLabel:convertFontCss("自定义"),
													  inputValue:0,
													  id : 'dayAccLimitDefine',
													  name : 'dayAccLimitDefault',
													  disabled : true,
													  listeners:{
															'afterrender' : function(){
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
												columnWidth:.3,
												layout:'form',
												labelWidth:1,
												items:[{
													 anchor:"98%",
													 xtype : 'numberfield',
													 width : 80,
													 id : 'dayAccSelfDefine',
													 name : 'dayAccSelfDefine',
													 readOnly : true,
													 cls : 'x-readOnly'
												}]
											},{
												columnWidth:.1,
												layout:'form',
												labelWidth:1,
												items:[{
													anchor:"90%",
													 xtype:'tbtext',
													 text:"元</br>",
													 style:"font-size:14px;padding-top:4px;"
												}]
											}]
								},{
				       	        	xtype : 'panel',
									layout:'column',
									id : 'dayAccCountPanel',
									name : 'dayAccCountPanel',
									items:[{
										 columnWidth:0.18,
											layout:'form',
											labelWidth:1,
											items:[{
												xtype:'tbtext',
											    width:150,
											    style:'padding-top:5px',
											    text:convertFontCss("日累计转账笔数")
											   	}]
										      
									 },{
										columnWidth:0.2,
										layout:'form',
										labelWidth:1,
										items:[{
											  anchor:"90%",
											  xtype:'radio',
											  boxLabel:convertFontCss("默认200笔"),
											   width : 150,
											  id : 'dayAccCountDefault',
											  name : 'dayAccCountDefault',
											  disabled : true,
											  inputValue:1,
											  listeners:{
													'afterrender' : function(){
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
										columnWidth:0.1,
										layout:'form',
										labelWidth:1,
										items:[{
											anchor:"98%",
											  xtype:'radio',
											  boxLabel:convertFontCss("自定义"),
											  id : 'dayAccCountDefine',
											  name : 'dayAccCountDefault',
											  disabled : true,
											  inputValue:0,
											  listeners:{
													'afterrender' : function(){
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
											width : 80,
											id : 'dayCountSelfDefine',
											name : 'dayCountSelfDefine',
											readOnly : true,
											cls : 'x-readOnly'
										}]
									},{
										columnWidth:.1,
										layout:'form',
										labelWidth:1,
										items:[{
											anchor:"90%",
											 xtype:'tbtext',
											 text:"笔</br>",
											 style:"font-size:14px;padding-top:4px;"
										}]
									}]
								},{
				       	        	xtype : 'panel',
									layout:'column',
									id : 'yearAccLimitPanel',
									name : 'yearAccLimitPanel',
									items:[{
											 columnWidth:.18,
												layout:'form',
												labelWidth:1,
												items:[{
													xtype:'tbtext',
												    width:150,
												    style:'padding-top:5px',
												    text:convertFontCss("年累计转账限额")
												}]
											      
										 },{
											columnWidth:.20,
											layout:'form',
											labelWidth:1,
											items:[{
												  anchor:"90%",
												  xtype:'radio',
												  boxLabel:convertFontCss("默认无限制"),
												   width : 150,
												  id : 'yearAccLimitDefault',
											  	  name : 'yearAccLimitDefault',
											  	  disabled : true,
												  inputValue:1,
												  listeners:{
														'afterrender' : function(){
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
												  boxLabel:convertFontCss("自定义"),
												  inputValue:0,
												  disabled : true,
												  id : 'yearAccLimitDefine',
												  name : 'yearAccLimitDefault',
												  listeners:{
														'afterrender' : function(){
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
												width : 80,
												readOnly : true,
												cls : 'x-readOnly',
												id : 'yearAccSelfDefine',
												name :'yearAccSelfDefine'
											}]
										},{
											columnWidth:.1,
											layout:'form',
											labelWidth:1,
											items:[{
												anchor:"90%",
												 xtype:'tbtext',
												 text:"元</br>",
												style:"font-size:14px;padding-top:4px;"
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
							  boxLabel:convertFontCss("电子对账单"),
							  id:'elecState',
							  name:'elecState',
							  inputValue:'1',
							  disabled : true,
							  width:150,
							  listeners:{
									'afterrender' : function(){
										if(this.el){
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color : "#555",
												cursor: "default",
												opacity: 1,
												margin:"5px 0px"
											});
										}
									}
							  }
						},{
							xtype : 'panel',
							layout:'column',
							id:'elecStatePanel',
							name:'elecStatePanel',
							colspan:2,
							width:800,
							items:[{
								columnWidth:.35,
								layout:'form',
								labelWidth:50,
								items:[{
									xtype : 'textfield',
									fieldLabel : convertFontCss("E-mail"),
									name : 'email',
									id : 'email',
									width:'200',
									readOnly : true,
									cls : 'x-readOnly',
									vtype:'email'
								}]
							},{
								 columnWidth:.01,
									layout:'form',
									labelWidth:1,
									items:[{
										xtype:'tbtext',
										style :'padding-top : 12px;',
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
										  boxLabel:convertFontCss("同电邮地址"),
										  disabled : true,
										  inputValue:'1',
										  listeners:{
										  	'afterrender' : function(){
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
								 columnWidth:.01,
									layout:'form',
									labelWidth:1,
									items:[{
										xtype:'tbtext',
										style :'padding-top : 12px;',
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
						boxLabel:convertFontCss("财务变动通知"),
						id:'chgNotice',
						width : 150,
						disabled : true,
						inputValue:'1',
						listeners:{
							'afterrender' : function(){
									if(this.el){
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color : "#555",
											cursor: "default",
											opacity: 1,
											margin:'5px 0px'
										});
									}
								}
						}
					}]
		   		 }]
	  	  }]
	});

	var qzComLists = new Ext.Panel({
				title : '第二页（银行信息）',
				frame : true,
				autoScroll : true,
				buttonAlign : "center",
				labelWidth : 140,
				items : [{
							layout : 'column',
							xtype : 'fieldset',
							id : 'Tab_accountInfo',
							title : '<b style="font-size:16px;">账户信息</b>',
							 style:'margin:10px 180px 20px 180px;',
							items : [accountInfo]
						}, {
							layout : 'column',
							xtype : 'fieldset',
							id : 'Tab_serviceInfo',
							title : '<font style="font-size:16px;">服务信息</font>',
							 style:'margin:10px 180px 20px 180px;',
							items : [serviceInfo]
						}],
				buttons : [{
							text : '上一页',
							handler : function() {
								qzComInfo.setActiveTab(0);
							}
						}]
			});

	/**
	 * 对公信息面板
	 */
	var qzComInfo = new Ext.TabPanel({
				activeItem : 0,
				deferredRender : false,
				defaults : {
					autoHeight : true
				},
				items : [qzCombaseInfo, qzComLists]
			});
	var panel = new Ext.Panel({
				title : '客户复核信息',
				autoHeight : true,
				autoWeight : true,
				items : [qzComInfo]
			});
	// -------------------------------------------客户复核前信息end----------------------------------
	var bussFieldSetGrid = new Ext.form.FieldSet({
				animCollapse : true,
				collapsible : true,
				title : '流程业务信息',
				items : [panel]
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
	var view = new Ext.Panel({
				renderTo : 'viewEChian',
				frame : true,
				width : document.body.scrollWidth,
				height : document.body.scrollHeight - 40,
				autoScroll : true,
				layout : 'form',
				items : [bussFieldSetGrid, EchainPanel]

			});

	var jnwAccountType = [];
	Ext.Ajax.request({
				url : basepath + '/oneKeyAccountAction!getAccountType.json',
				type : 'POST',
				success : function(response) {
					var info = Ext.decode(response.responseText);
					for (var i in info) {
						var infoItem = info[i];
						if (infoItem.value == '未知') {
							continue;
						} else {
							var value = infoItem.value;
							var code = infoItem.code;
							var items = infoItem.items;
							var radioItems = [];
							if (jnwAccountType.indexOf(code) < 0) {
								jnwAccountType.push(code);
							}
							radioItems.push({
										xtype : 'radio',
										boxLabel : convertFontCss(value),
										inputValue : code,
										name : 'cusCategory',
										id : "radio" + code,
										disabled : true,
										listeners:{
											'afterrender' : function(){
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
									});

							var jnwPanel = new Ext.Panel({
										layout : "column",
										width : 400,
										items : radioItems
									});

							var checkboxItems = [];
							for (var k in items) {
								var checkItem = items[k];
								var checkValue = checkItem.value;
								var checkCode = checkItem.code;

								if (checkValue != undefined) {
									checkboxItems.push({
												xtype : "checkbox",
												inputValue : checkCode,
												id : code + checkCode,
												name : "radio" + code,
												boxLabel : convertFontCss(checkValue),
												style : 'margin-left:30px;',
												disabled : true,
												listeners:{
													'afterrender' : function(){
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
											});
								}
							}

							var jnwCusCheckboxGroup = new Ext.form.CheckboxGroup(
									{
										vertical : true,
										columns : 1,
										width : 400,
										items : checkboxItems,
										id : code + 'Checkbox'
									});
							jnwPanel.add(jnwCusCheckboxGroup);
							accountInfo.add(jnwPanel);
							accountInfo.doLayout();

						}
					}
					accountInfo.doLayout();
				}
			});

	qzComListsStore.load({
		params : {
			custId : custId
		},
		callback : function() {
			if (qzComListsStore.getCount() != 0) {
				var record = qzComListsStore.getAt(0);
				//邮寄地址与居住地址是否一致
				if(record.get("same") != ""){
					Ext.getCmp("same" ).setValue(record.get("same"));
				}
				
				// 境内外
				if (record.get("cusCategory") != "") {
					Ext.getCmp("radio" + record.get("cusCategory")).setValue(true);
					if (record.get("DFCheckbox") != "") {
						var DFCheckbox = record.get("DFCheckbox");
						var dfcount = 0;
						for (var i = 0; i < DFCheckbox.length; i++) {
							var act_type = DFCheckbox[i].ACT_TYPE;
							if (act_type != "") {
								Ext.getCmp(record.get("cusCategory") + act_type).setValue(true);
								if(record.get("cusCategory") + act_type == 'DK' || 
									record.get("cusCategory") + act_type == 'FH'){
									dfcount = 1;
								}
							}
						}
						
						if (isLianMingHu == '1' || dfcount == 0) {
							Ext.getCmp("jiejikaFieldset").setVisible(false);
							Ext.getCmp("dianziBankFieldset").setVisible(false);
							Ext.getCmp("elecState").setVisible(false);
							Ext.getCmp("elecStatePanel").setVisible(false);
						}
					}
				}

				
				// 借记卡申请
				if (record.get("isOpenCard") == "1") {
					Ext.getCmp("jiejika").setValue(true);
					var cardType = record.get("cardType");
					var cardType1_0 = record.get("cardType1_0");
					var cardType2_0 = record.get("cardType2_0");
					Ext.getCmp("cardType").setVisible(true);
					Ext.getCmp("cardType1_0").setVisible(true);
					Ext.getCmp("cardType2_0").setVisible(true);
		  			
		  			//卡类型
					if(cardTypeStore.getCount() == 0){
						cardTypeStore.load({
							callback : function(){
								Ext.getCmp("cardType").setValue(record.get("cardType"));
							}
						})
					}else{
						Ext.getCmp("cardType").setValue(record.get("cardType"));
					}
		  			
		  			Ext.getCmp("cardType").allowBlank = false;
		  			cardType1Store.load({
			   			params	: {
			   				name	: cardType
			   			},
			   			callback	: function(){
			   				Ext.getCmp("cardType1_0").setValue(cardType1_0);
			   			}
			   		});
			   		cardType2Store.load({
			   			params	: {
			   				name	: cardType1_0
			   			},
			   			callback	: function(){
			   				Ext.getCmp("cardType2_0").setValue(cardType2_0);
			   			}
			   		});
			   		
			   		serviceInfo.doLayout();
			   		
		  			Ext.getCmp("cardType1_0").allowBlank = false;
		  			Ext.getCmp("cardType2_0").allowBlank = false;

						// ATM转账限额设置
						// 每日累计限额
						if (record.get("ATMDayLimitDefault") != '') {
							var ATMDayLimitDefault = record.get("ATMDayLimitDefault");
							if (ATMDayLimitDefault == '0') {
								Ext.getCmp("ATMDayLimitDefine").setValue(true);
								if (record.get("ATMDayLimit") != '') {
									Ext.getCmp("ATMDayLimit").setValue(record.get("ATMDayLimit"));
								}
							} else {
								Ext.getCmp("ATMDayLimitDefault").setValue(true);
							}
						}
						// 每日累计笔数
						if (record.get("ATMDayCountDefault") != '') {
							var ATMDayCountDefault = record
									.get("ATMDayCountDefault");
							if (ATMDayCountDefault == '0') {
								Ext.getCmp("ATMDayCountDefine").setValue(true);
								if (record.get("ATMDayLimitCount") != '') {
									Ext.getCmp("ATMDayLimitCount")
											.setValue(record
													.get("ATMDayLimitCount"));
								}
							} else {
								Ext.getCmp("ATMDayCountDefault").setValue(true);
							}
						}

						// 每年累计限额
						if (record.get("ATMYearLimitDefault") != '') {
							var ATMYearLimitDefault = record
									.get("ATMYearLimitDefault");
							if (ATMYearLimitDefault == '0') {
								Ext.getCmp("ATMYearLimitDefine").setValue(true);
								if (record.get("ATMYearLimit") != '') {
									Ext.getCmp("ATMYearLimit").setValue(record
											.get("ATMYearLimit"));
								}
							} else {
								Ext.getCmp("ATMYearLimitDefault")
										.setValue(true);
							}
						}

						// POS消费限额设置
						if (record.get("POSDefault") != '') {
							var POSDefault = record.get("POSDefault");
							if (POSDefault == '0') {
								Ext.getCmp("POSDefine").setValue(true);
								if (record.get("eachCustemLimit") != '') {
									Ext.getCmp("eachCustemLimit")
											.setValue(record
													.get("eachCustemLimit"));
								}
							} else {
								Ext.getCmp("POSDefault").setValue(true);
							}
						}

					} else {
						Ext.getCmp("cardType").setVisible(false);
						Ext.getCmp("cardType1_0").setVisible(false);
						Ext.getCmp("cardType2_0").setVisible(false);
						Ext.getCmp('ATM').setVisible(false);
						Ext.getCmp('ATMDayLimitPanel').setVisible(false);
						Ext.getCmp('ATMDayCountPanel').setVisible(false);
						Ext.getCmp('ATMYearLimitPanel').setVisible(false);
						Ext.getCmp('POS').setVisible(false);
						Ext.getCmp('POSPanel').setVisible(false);
					}
				

				// 电子银行服务
				if (record.get("dianziBank") != '') {
					var dianziBank = record.get("dianziBank");
					if (dianziBank == '1') {
						Ext.getCmp("dianziBank").setValue(true);
						// 网络银行
						if (record.get("netBank") != '') {
							var netBank = record.get("netBank");
							if (netBank == '1') {
								Ext.getCmp("ukeyPanel").setVisible(true);
								Ext.getCmp("shortMessagePanel")
										.setVisible(true);
								Ext.getCmp("netBank").setValue(true);
								// ukey
								if (record.get("ukey") != '') {
									var ukey = record.get("ukey");
									if (ukey == '1') {
										Ext.getCmp("ukey").setValue(true);
									} else {
										Ext.getCmp("ukey").setValue(false);
									}
								}
								// 短信认证
								if (record.get("shortMessage") != '') {
									var shortMessage = record
											.get("shortMessage");
									if (shortMessage == '1') {
										Ext.getCmp("shortMessage")
												.setValue(true);
									} else {
										Ext.getCmp("shortMessage")
												.setValue(false);
									}
								}
							} else {
								Ext.getCmp("netBank").setValue(false);
								Ext.getCmp("ukeyPanel").setVisible(false);
								Ext.getCmp("shortMessagePanel")
										.setVisible(false);
							}
						}

						/*// 电话银行
						if (record.get("phoneBank") != '') {
							var phoneBank = record.get("phoneBank");
							if (phoneBank == '1') {
								Ext.getCmp("phoneBank").setValue(true);
							} else {
								Ext.getCmp("phoneBank").setValue(false);
							}
						}*/

						// 手机银行
						if (record.get("mobileBank") != '') {
							var mobileBank = record.get("mobileBank");
							if (mobileBank == '1') {
								Ext.getCmp("mobileBank").setValue(true);
								Ext.getCmp("shortMessage2Panel")
										.setVisible(true);
								// 短信验证
								if (record.get("shortMessage2") != '') {
									var shortMessage2 = record
											.get("shortMessage2");
									if (shortMessage2 == '1') {
										Ext.getCmp("shortMessage2")
												.setValue(true);

									} else {
										Ext.getCmp("shortMessage2")
												.setValue(false);
									}
								}
							} else {
								Ext.getCmp("mobileBank").setValue(false);
								Ext.getCmp("shortMessage2Panel")
										.setVisible(false);
							}
						}

						// 日累计转账限额
						if (record.get("dayAccLimitDefault") != '') {
							var dayAccLimitDefault = record
									.get("dayAccLimitDefault");
							if (dayAccLimitDefault == '0') {
								Ext.getCmp("dayAccLimitDefine").setValue(true);
								if (record.get("dayAccSelfDefine") != '') {
									Ext.getCmp("dayAccSelfDefine")
											.setValue(record
													.get("dayAccSelfDefine"));
								}
							} else {
								Ext.getCmp("dayAccLimitDefault").setValue(true);
							}
						}

						// 日累计转账笔数
						if (record.get("dayAccCountDefault") != '') {
							var dayAccCountDefault = record
									.get("dayAccCountDefault");
							if (dayAccCountDefault == '0') {
								Ext.getCmp("dayAccCountDefine").setValue(true);
								if (record.get("dayCountSelfDefine") != '') {
									Ext.getCmp("dayCountSelfDefine")
											.setValue(record
													.get("dayCountSelfDefine"));
								}
							} else {
								Ext.getCmp("dayAccCountDefault").setValue(true);
							}
						}

						// 年累计转账限额
						if (record.get("yearAccLimitDefault") != '') {
							var yearAccLimitDefault = record
									.get("yearAccLimitDefault");
							if (yearAccLimitDefault == '0') {
								Ext.getCmp("yearAccLimitDefine").setValue(true);
								if (record.get("yearAccSelfDefine") != '') {
									Ext.getCmp("yearAccSelfDefine")
											.setValue(record
													.get("yearAccSelfDefine"));
								}
							} else {
								Ext.getCmp("yearAccLimitDefault")
										.setValue(true);
							}
						}

					} else {
						Ext.getCmp("dianziBank").setValue(false);
						Ext.getCmp("netBank").setVisible(false);
						Ext.getCmp("ukeyPanel").setVisible(false);
						Ext.getCmp("shortMessagePanel").setVisible(false);
						//Ext.getCmp("phoneBank").setVisible(false);
						Ext.getCmp("mobileBank").setVisible(false);
						Ext.getCmp("shortMessage2Panel").setVisible(false);
						Ext.getCmp("dayAccLimitPanel").setVisible(false);
						Ext.getCmp("dayAccCountPanel").setVisible(false);
						Ext.getCmp("yearAccLimitPanel").setVisible(false);
					}
				}

				// 电子对账单
				if (record.get("elecState") != '') {
					var elecState = record.get("elecState");
					if (elecState == '1') {
						Ext.getCmp("elecState").setValue(true);
						// 对账单是否同email
						if (record.get("isEquEmail") != '') {
							var isEquEmail = record.get("isEquEmail");
							if (isEquEmail == '1') {
								Ext.getCmp("isEquEmail").setValue(true);
							} else {
								Ext.getCmp("isEquEmail").setValue(false);
							}
						}
						// E-mail
						if (record.get("email") != '') {
							Ext.getCmp("email").setValue(record.get("email"));
						}
					} else {
						Ext.getCmp("elecState").setValue(false);
						Ext.getCmp('elecStatePanel').setVisible(false);
					}
				}

				// 财务变动通知
				if (record.get("chgNotice") != '') {
					var chgNotice = record.get("chgNotice");
					if (chgNotice == '1') {
						Ext.getCmp("chgNotice").setValue(true);
					} else {
						Ext.getCmp("chgNotice").setValue(false);
					}
				}

				// accountInfo.getForm().loadRecord(record);
				// serviceInfo.getForm().loadRecord(record);
			}
		}
	});

});
