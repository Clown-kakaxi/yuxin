
Ext.onReady(function() {

	    var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		
		var rsRecord = new Ext.data.Record.create([
		                                           {name:'custId',mapping:'CUST_ID'},
	                                               {name:'atmLimit',mapping:'ATM_LIMIT'},
	                                               {name:'atmHigh',mapping:'ATM_HIGH'},
	                                               {name:'posLimit',mapping:'POS_LIMIT'},
	                                               {name:'posHigh',mapping:'POS_HIGH'},
	                                               {name:'ukey',mapping:'UKEY'},
	                                               {name:'messageCode',mapping:'MESSAGE_CODE'},
	                                               {name:'phoneBanking',mapping:'PHONE_BANKING'},
	                                               {name:'mobileBanking',mapping:'MOBILE_BANKING'},
	                                               {name:'microBanking',mapping:'MICRO_BANKING'},
	                                               {name:'statements',mapping:'STATEMENTS'},
	                                               {name:'changeNotice',mapping:'CHANGE_NOTICE'},
	                                               {name:'accept',mapping:'ACCEPT'},
	                                               {name:'mailAddress',mapping:'MAIL_ADDRESS'},
	                                               {name:'transactionService',mapping:'TRANSACTION_SERVICE'},
	                                               {name:'mail',mapping:'MAIL'},
	                                               {name:'isCardApply',mapping:'IS_CARD_APPLY'},
	                                               {name:'isNtBank',mapping:'IS_NT_BANK'},
	                                               {name:'isElebankSer',mapping:'IS_ELEBANK_SER'},
	                                               {name:'isAtmHigh',mapping:'IS_ATM_HIGH'},
	                                               {name:'isPosHigh',mapping:'IS_POS_HIGH'},
	                                               
	                                               {name:'atmLimitOld',mapping:'ATM_LIMIT_OLD'},
	                                               {name:'atmHighOld',mapping:'ATM_HIGH_OLD'},
	                                               {name:'posLimitOld',mapping:'POS_LIMIT_OLD'},
	                                               {name:'posHighOld',mapping:'POS_HIGH_OLD'},
	                                               {name:'ukeyOld',mapping:'UKEY_OLD'},
	                                               {name:'messageCodeOld',mapping:'MESSAGE_CODE_OLD'},
	                                               {name:'phoneBankingOld',mapping:'PHONE_BANKING_OLD'},
	                                               {name:'mobileBankingOld',mapping:'MOBILE_BANKING_OLD'},
	                                               {name:'microBankingOld',mapping:'MICRO_BANKING_OLD'},
	                                               {name:'statementsOld',mapping:'STATEMENTS_OLD'},
	                                               {name:'changeNoticeOld',mapping:'CHANGE_NOTICE_OLD'},
	                                               {name:'acceptOld',mapping:'ACCEPT_OLD'},
	                                               {name:'mailAddressOld',mapping:'MAIL_ADDRESS_OLD'},
	                                               {name:'transactionServiceOld',mapping:'TRANSACTION_SERVICE_OLD'},
	                                               {name:'mailOld',mapping:'MAIL_OLD'},
	                                               {name:'isCardApplyOld',mapping:'IS_CARD_APPLY_OLD'},
	                                               {name:'isNtBankOld',mapping:'IS_NT_BANK_OLD'},
	                                               {name:'isElebankSerOld',mapping:'IS_ELEBANK_SER_OLD'},
	                                               {name:'isAtmHighOld',mapping:'IS_ATM_HIGH_OLD'},
	                                               {name:'isPosHighOld',mapping:'IS_POS_HIGH_OLD'}
		                                       ]);
		var rsreader = new Ext.data.JsonReader( {
			root : 'json.data',
			totalProperty : 'json.count'
		}, rsRecord);

		var opForm = new Ext.form.FormPanel({
			id : 'opForm',
			layout : 'form',
			labelAlign : 'left',
			autoScroll : true,
			frame : true,
			buttonAlign : "center",
			items:[{
				xtype:'fieldset',
			   	title:'银行服务信息[修改后]',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
//				anchor : '95%',
				items : [{
				    title: '',
				    xtype: 'fieldset',
				    checkboxToggle: false,
				    animCollapse :true,
				    items:[{boxLabel: '借记卡申请',
					    id:'jiejika',
					    xtype: 'checkbox',
					    checkboxToggle: true,
					    animCollapse :true,
					    hideParent : false,
					    inputValue:'1'
			          },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{
				            xtype: 'displayfield',
				            value: 'ATM转账限额设置'
						},{ xtype: 'checkbox',
						    id:'ATM_LIMIT',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'默认每日累计限额（RMB50,000元）',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_atm2',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'每日累计转账最高限额RMB',
						    inputValue:'1'
						},{//每日累计转账最高限额RMB
					        name : 'atmHigh',
					        id:'id_atm3',
					        xtype: 'numberfield',
					        width: 80,
		                    allowNegative:false,//正数
		                    decimalPrecision:2,//整数
					        allowBlank: true
					    },{xtype: 'displayfield',
					       value: '元'
					    }]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{
				            xtype: 'displayfield',
				            value: 'POS 消费限额设置 '
						},{ xtype: 'checkbox',
						    id:'POS_LIMIT',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'默认单笔限额（RMB500,000元）',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_pos2',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'单笔消费限额RMB',
						    inputValue:'1'
						},{//单笔消费限额RMB
					        name : 'posHigh',
					        id:'id_pos3',
					        xtype: 'numberfield',
					        width: 80,
		                    allowNegative:false,//正数
		                    decimalPrecision:2,//整数
					        allowBlank: true
					    },{xtype: 'displayfield',
					       value: '元'
					    }]
				    }]
				},{
					title: '',
				    xtype: 'fieldset',
				    checkboxToggle: false,
				    animCollapse :true,
				    items:[{boxLabel: '电子银行服务',
					    id:'dzyh',
					    xtype: 'checkbox',
					    checkboxToggle: true,
					    animCollapse :true,
					    hideParent : false,
					    inputValue:'1'
			          },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{boxLabel: '网络银行',
						    id:'wlyh',
						    xtype: 'checkbox',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    inputValue:'1'
				          },{ xtype: 'displayfield',
				            width:20
				          },{
				        	 xtype: 'checkbox',
						     id:'id_ukey',
						     checkboxToggle: true,
						     animCollapse :true,
						     hideParent : false,
						     boxLabel:'U-key认证',
						     inputValue:'1'
				        }]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            width:176
						  },{
				        	xtype: 'checkbox',
						    id:'id_dx',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'短信认证码（汇款限额RMB50,000元）',
						    inputValue:'1'
				        }]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{ xtype: 'checkbox',
						    id:'id_dh',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'电话银行',
						    inputValue:'1'
						}]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{ xtype: 'checkbox',
						    id:'id_sj',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'手机银行',
						    inputValue:'1'
						}]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{ xtype: 'checkbox',
						    id:'id_wx',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'微信银行',
						    inputValue:'1'
						}]
				    }]
				},{
					title: '',
				    id:'dianzifuwu33',
				    xtype: 'fieldset',
				    checkboxToggle: false,
				    animCollapse :true,
				    align:'left', 
				    items:[{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items :[{ xtype: 'checkbox',
							    id:'id_dz',
							    checkboxToggle: true,
							    animCollapse :true,
							    hideParent : false,
							    boxLabel:'电子对账单',
							    inputValue:'1'
						    },{ xtype: 'displayfield',
					            width: 50
					        },{
					            xtype: 'displayfield',
					            value: 'E-mail:' 
							},{//单笔消费限额RMB
						        name : 'mail',
						        id:'TacticsDetail122',
						        xtype: 'textfield',
						        width: 180,
				                allowNegative:false,//正数
				                decimalPrecision:0,//整数
						        allowBlank: true
				             },{
						            xtype: 'displayfield',
						            value: '（' 
							 },{ xtype: 'checkbox',
									    id:'id_dy',
									    checkboxToggle: true,
									    animCollapse :true,
									    hideParent : false,
									    boxLabel:'同电邮地址）',
									    inputValue:'1'
							}]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{ xtype: 'checkbox',
						    id:'id_cz',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'传真交易服务',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_zw',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'账务变动通知',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_yy',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'若符合我行的审核条件，愿意接受我行的信贷额度',
						    inputValue:'1'
						}]
				    }]
				}]
			}]
});
		
		var opFormOld = new Ext.form.FormPanel({
			id : 'opFormOld',
			layout : 'form',
			labelAlign : 'left',
			autoScroll : true,
			frame : true,
			buttonAlign : "center",
			items:[{
				xtype:'fieldset',
			   	title:'银行服务信息[修改前]',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
//				anchor : '95%',
				items : [{
				    title: '',
				    xtype: 'fieldset',
				    checkboxToggle: false,
				    animCollapse :true,
				    autoHeight : true,
				    items:[{boxLabel: '借记卡申请',
					    id:'jiejikaOld',
					    xtype: 'checkbox',
					    checkboxToggle: true,
					    animCollapse :true,
					    hideParent : false,
					    inputValue:'1'
			          },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{
				            xtype: 'displayfield',
				            align : 'left',
				            value: 'ATM转账限额设置'
						},{ xtype: 'checkbox',
						    id:'ATM_LIMIT_OLD',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'默认每日累计限额（RMB50,000元）',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_atm2_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'每日累计转账最高限额RMB',
						    inputValue:'1'
						},{//每日累计转账最高限额RMB
					        name : 'atmHighOld',
					        id:'id_atm3_old',
					        xtype: 'numberfield',
					        width: 80,
		                    allowNegative:false,//正数
		                    decimalPrecision:0,//整数
					        allowBlank: true
					    },{xtype: 'displayfield',
					       value: '元'
					    }]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{
				            xtype: 'displayfield',
				            value: 'POS 消费限额设置 '
						},{ xtype: 'checkbox',
						    id:'POS_LIMIT_OLD',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'默认单笔限额（RMB500,000元）',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_pos2_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'单笔消费限额RMB',
						    inputValue:'1'
						},{//单笔消费限额RMB
					        name : 'posHighOld',
					        id:'id_pos3_old',
					        xtype: 'numberfield',
					        width: 80,
		                    allowNegative:false,//正数
		                    decimalPrecision:0,//整数
					        allowBlank: true
					    },{xtype: 'displayfield',
					       value: '元'
					    }]
				    }]
				},{
					title: '',
				    xtype: 'fieldset',
				    checkboxToggle: false,
				    animCollapse :true,
				    items:[{boxLabel: '电子银行服务',
					    id:'dzyh_old',
					    xtype: 'checkbox',
					    checkboxToggle: false,
					    animCollapse :true,
					    hideParent : false,
					    inputValue:'1'
			          },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
					            xtype: 'displayfield',
					            value: '',
					            width:80
						      },{boxLabel: '网络银行',
							    id:'wlyhOld',
							    xtype: 'checkbox',
							    checkboxToggle: true,
							    animCollapse :true,
							    hideParent : false,
							    inputValue:'1'
					          },{ xtype: 'displayfield',
						          width:20
							  },{ xtype: 'checkbox',
								     id:'id_ukey_old',
								     checkboxToggle: true,
								     animCollapse :true,
								     hideParent : false,
								     boxLabel:'U-key认证',
								     inputValue:'1'
							   }]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            width:176
						  },{
				        	xtype: 'checkbox',
						    id:'id_dx_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'短信认证码（汇款限额RMB50,000元）',
						    inputValue:'1'
				        }]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{ xtype: 'checkbox',
						    id:'id_dh_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'电话银行',
						    inputValue:'1'
						}]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{ xtype: 'checkbox',
						    id:'id_sj_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'手机银行',
						    inputValue:'1'
						}]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{
				            xtype: 'displayfield',
				            value: '',
				            width:80
						},{ xtype: 'checkbox',
						    id:'id_wx_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'微信银行',
						    inputValue:'1'
						}]
				    }]
				},{
					title: '',
				    id:'dianzifuwu33_old',
				    xtype: 'fieldset',
				    checkboxToggle: false,
				    animCollapse :true,
				    items:[{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items :[{ xtype: 'checkbox',
						    id:'id_dz_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'电子对账单',
						    inputValue:'1'
						},{ xtype: 'displayfield',
				            width: 50
					        },{
				            xtype: 'displayfield',
				            value: 'E-mail:' 
							},{//单笔消费限额RMB
						        name : 'mailOld',
						        id:'TacticsDetail122_old',
						        xtype: 'textfield',
						        width: 180,
				                allowNegative:false,//正数
				                decimalPrecision:0,//整数
						        allowBlank: true
				             },{
						            xtype: 'displayfield',
						            value: '（' 
							 },{ xtype: 'checkbox',
									    id:'id_dy_old',
									    checkboxToggle: true,
									    animCollapse :true,
									    hideParent : false,
									    boxLabel:'同电邮地址）',
									    inputValue:'1'
							}]
				    },{
				        xtype: 'compositefield',
				        fieldLabel: '',
				        combineErrors: false,
				        items: [{ xtype: 'checkbox',
						    id:'id_cz_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'传真交易服务',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_zw_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'账务变动通知',
						    inputValue:'1'
						},{ xtype: 'checkbox',
						    id:'id_yy_old',
						    checkboxToggle: true,
						    animCollapse :true,
						    hideParent : false,
						    boxLabel:'若符合我行的审核条件，愿意接受我行的信贷额度',
						    inputValue:'1'
						}]
				    }]
				}]
			}]
});	
		var store = new Ext.data.Store({
			restful:true,
		    proxy : new Ext.data.HttpProxy({
				url:basepath + '/acrmFCiBankService.json'
			}),
		    reader: rsreader
		});
		
//	    var applyPanel = new Ext.Panel({
//	    	autoScroll : true,
//	    	layout : 'column',
//	    	items : [ {columnWidth : .5,
//			            items :[opFormOld]
//			           },{columnWidth : .5,
//			            items :[opForm]
//			           } ]
//	    	});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[opForm,opFormOld]
	   }); 
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			 frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
		
		store.load({
			params : {
				custId : id,
				old:'1'
			},
			method : 'GET',
			callback:function(){
				window.__setFormValue();
			}
		});
		window.__setFormValue = function(){
				if(store.getCount() != 0){
					for(var i=0;i<store.getCount();i++){
						var data = store.getAt(i).data;
						//修改后
			    		opForm.getForm().loadRecord(store.getAt(i));
			    		opForm.getForm().findField('mail').setValue(data.mail);
			    		if(data.isAtmHigh != 0){//ATM每日累计转账最高限额
			    			Ext.getCmp("id_atm2").setValue(true);
			    		}
			    		if(data.isPosHigh != 0){//POS单笔消费限额
			    			Ext.getCmp("id_pos2").setValue(true);
			    		}
			    		if(data.isCardApply != ""){//借记卡申请
			    			Ext.getCmp("jiejika").setValue(true);
			    		}
			    		if(data.isElebankSer != ""){//电子银行服务
			    			Ext.getCmp("dzyh").setValue(true);
			    		}
			    		if(data.isNtBank != ""){//网络银行
			    			Ext.getCmp("wlyh").setValue(true);
			    		}
			    		if(data.atmLimit != ""){//ATM默认每日累计限额
			    			Ext.getCmp("ATM_LIMIT").setValue(true);
			    		}
			    		if(data.posLimit != ""){//POS默认单笔限额
			    			Ext.getCmp("POS_LIMIT").setValue(true);
			    		}
			    		if(data.ukey != ""){//u-key
			    			Ext.getCmp("id_ukey").setValue(true);
			    		}
			    		if(data.messageCode != ""){//短信认证码
			    			Ext.getCmp("id_dx").setValue(true);
			    		}
			    		if(data.phoneBanking != ""){//电话银行
			    			Ext.getCmp("id_dh").setValue(true);
			    		}
			    		if(data.mobileBanking != ""){//手机银行
			    			Ext.getCmp("id_sj").setValue(true);
			    		}
			    		if(data.microBanking != ""){//微信银行
			    			Ext.getCmp("id_wx").setValue(true);
			    		}
			    		if(data.statements != ""){//电子对账单
			    			Ext.getCmp("id_dz").setValue(true);
			    		}
			    		if(data.transactionService != ""){//传真交易服务
			    			Ext.getCmp("id_cz").setValue(true);
			    		}
			    		if(data.changeNotice != ""){//账务变动通知
			    			Ext.getCmp("id_zw").setValue(true);
			    		}
			    		if(data.accept != ""){//若符合我行的审核条件，愿意接受我行的信贷额度
			    			Ext.getCmp("id_yy").setValue(true);
			    		}
			    		if(data.mailAddress != ""){//同电邮地址
			    			Ext.getCmp("id_dy").setValue(true);
			    		}
			    		//修改前
			    		opFormOld.getForm().findField('mailOld').setValue(data.mailOld);
			    		if(data.isAtmHighOld != 0){//ATM每日累计转账最高限额
			    			Ext.getCmp("id_atm2_old").setValue(true);
			    		}
			    		if(data.isPosHighOld != 0){//POS单笔消费限额
			    			Ext.getCmp("id_pos2_old").setValue(true);
			    		}
			    		if(data.isCardApplyOld != ""){//借记卡申请
			    			Ext.getCmp("jiejika_old").setValue(true);
			    		}
			    		if(data.isElebankSerOld != ""){//电子银行服务
			    			Ext.getCmp("dzyh_old").setValue(true);
			    		}
			    		if(data.isNtBankOld != ""){//网络银行
			    			Ext.getCmp("wlyh_old").setValue(true);
			    		}
			    		if(data.atmLimitOld != ""){//ATM默认每日累计限额
			    			Ext.getCmp("ATM_LIMIT_OLD").setValue(true);
			    		}
			    		if(data.posLimitOld != ""){//POS默认单笔限额
			    			Ext.getCmp("POS_LIMIT_OLD").setValue(true);
			    		}
			    		if(data.ukeyOld != ""){//u-key
			    			Ext.getCmp("id_ukey_old").setValue(true);
			    		}
			    		if(data.messageCodeOld != ""){//短信认证码
			    			Ext.getCmp("id_dx_old").setValue(true);
			    		}
			    		if(data.phoneBankingOld != ""){//电话银行
			    			Ext.getCmp("id_dh_old").setValue(true);
			    		}
			    		if(data.mobileBankingOld != ""){//手机银行
			    			Ext.getCmp("id_sj_old").setValue(true);
			    		}
			    		if(data.microBankingOld != ""){//微信银行
			    			Ext.getCmp("id_wx_old").setValue(true);
			    		}
			    		if(data.statementsOld != ""){//电子对账单
			    			Ext.getCmp("id_dz_old").setValue(true);
			    		}
			    		if(data.transactionServiceOld != ""){//传真交易服务
			    			Ext.getCmp("id_cz_old").setValue(true);
			    		}
			    		if(data.changeNoticeOld != ""){//账务变动通知
			    			Ext.getCmp("id_zw_old").setValue(true);
			    		}
			    		if(data.acceptOld != ""){//若符合我行的审核条件，愿意接受我行的信贷额度
			    			Ext.getCmp("id_yy_old").setValue(true);
			    		}
			    		if(data.mailAddressOld != ""){//同电邮地址
			    			Ext.getCmp("id_dy_old").setValue(true);
			    		}
			    	}
				}
		};
	});
