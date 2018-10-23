Ext.onReady(function() {
			Ext.QuickTips.init();
			var rel_cust_id = '';
			var custId = '';
			var store = Ext.data.Store;
			function setCertTypeValue(value) {
//				debugger;
				if (value == undefined) {
					return "";
				} else if (certTypStore.query('key', value, false, true).first() == undefined) {
					return "";
				} else {
					//return certTypStore.query('key', value, false, true).first().get('value');
					Ext.getCmp('certType_1').setValue(certTypStore.query('key', value, false, true).first().get('value'));
				}
			}
			;
			/** ********客户品牌大类******* */
			var p_cust_grade = new Ext.data.Store( {
				restful : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=P_CUST_GRADE'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON',
					totalProperty : 'list'
				}, [ 'key', 'value' ])
			});
			p_cust_grade.load();
			//证件类型
			var certTypStore = new Ext.data.Store( {
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=XD000040'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON'
				},['key','value'])
			});
			certTypStore.load();
			/** ********************阅读标识STORE******************************* */
			var TopicRecord = Ext.data.Record.create( [ {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'applyDate',
				mapping : 'applyDate'
			}, {
				name : 'applyOrg',
				mapping : 'applyOrg'
			}, {
				name : 'applyUser',
				mapping : 'applyUser'
			}, {
				name : 'certCode',
				mapping : 'certCode'
			}, {
				name : 'certType',
				mapping : 'certType'
			}, {
				name : 'currentGrade',
				mapping : 'currentGrade'
			}, {
				name : 'custId',
				mapping : 'custId'
			}, {
				name : 'custName',
				mapping : 'custName'
			}, {
				name : 'position',
				mapping : 'position'
			}, {
				name : 'reason',
				mapping : 'reason'
			}, {
				name : 'salary',
				mapping : 'salary'
			}, {
				name : 'status',
				mapping : 'status'
			}, {
				name : 'telphone',
				mapping : 'telphone'
			}, {
				name : 'toGrade',
				mapping : 'toGrade'
			}, {
				name : 'workUnit',
				mapping : 'workUnit'
			} ]);
			var restfulStore = new Ext.data.Store( {
				restful : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/ocrmFCiGradeApply-info!indexPage.json'
//					 success : function(success) {
//							 Ext.Msg.alert('提示', success.responseText);
//							 alert('dddddddddddddddd');
//							 }
						}),
				reader : new Ext.data.JsonReader( {
					successProperty : 'success',
					root : 'json.data',
					totalProperty : 'json.count'
				}, TopicRecord)
			});

			var writer = new Ext.data.JsonWriter( {
				encode : false
			});
			var sm = new Ext.grid.CheckboxSelectionModel();

			var rownum = new Ext.grid.RowNumberer( {
				header : 'No.',
				width : 28
			});

			var pagesize_combo = new Ext.form.ComboBox( {
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore( {
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								[ 100, '100条/页' ], [ 250, '250条/页' ],
								[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : 20,
				editable : false,
				width : 85
			});

			var bbar = new Ext.PagingToolbar( {
				pageSize : parseInt(pagesize_combo.getValue()),
				store : restfulStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
						restfulStore.reload( {
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});
			});
			restfulStore.load( {
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});
			/**
			 * form panel ,used for create,update,and show the records.
			 */
			var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户姓名', 
				labelStyle: 'text-align:right;',
				labelWidth : 100,
				name : 'custName',
				id:'rel_cust_name',
				custtype : '',// 客户类型:1:对私,2:对公,不设默认全部
				custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				allowBlank:false,//不允许为空
				blankText:"不能为空，请填写",
				anchor : '90%',
				hiddenName : 'custId',
		//		hiddenName:'abcd',
				callback :function(){
//				debugger;
//				var custid = Ext.getCmp('rel_cust_name').customerId.aId[0];
				var custid = search_cust.customerId;
				Ext.Ajax.request( {
							url : basepath + '/perCustQuery.json',
							method : 'GET',
							params : {
								'condition' : Ext.encode( {
									custId : custid
									
								})
							},
							success : function(response) {
								var nodeArra = Ext.util.JSON.decode(response.responseText);
//	                            Ext.Msg.alert('提示', response.responseText);
									store.data = nodeArra;
									Ext.getCmp('currentGrade_1').setDisabled(false);
									Ext.getCmp('currentGrade_1').setReadOnly(true);
									if (store.data.json.count != 0) {
										Ext.getCmp('custId_1').setReadOnly(true);
										try{
										Ext.getCmp('custId_1').setValue(store.data.json.data[0].CUST_ID1);
										setCertTypeValue(store.data.json.data[0].CERT_TYPE);
										Ext.getCmp('currentGrade_1').setValue(store.data.json.data[0].CUST_LEV);
										Ext.getCmp('certCode_1').setValue(store.data.json.data[0].CERT_NUM);
										Ext.getCmp('workUnit_1').setValue(store.data.json.data[0].WRK_UNIT);
										Ext.getCmp('position_1').setValue(store.data.json.data[0].POSITION);
										Ext	.getCmp('salary_1').setValue(store.data.json.data[0].YEAR_INCOME);
										Ext.getCmp('telphone_1').setValue(store.data.json.data[0].LINK_PHONE);
										}catch(e){
//											Ext.Msg.alert('提示', e.message);
										}
//									 if(store.data.json.data[0].CUST_TYPE=='1'){
////										addaffiche.getForm().reset();
//										Ext.getCmp('custId_1').setReadOnly(false);
//										Ext.getCmp('custId_1').setValue('');
//										setCertTypeValue('');
//										Ext.getCmp('currentGrade_1').setValue('');
//										Ext.getCmp('certCode_1').setValue('');
//										Ext.getCmp('workUnit_1').setValue('');
//										Ext.getCmp('position_1').setValue('');
//										Ext	.getCmp('salary_1').setValue('');
//										Ext.getCmp('telphone_1').setValue('');
//										Ext.Msg.alert('提示', '没有此用户的信息,请手动录入');
//								}
									}else{
									Ext.getCmp('currentGrade_1').setDisabled(true);
									Ext.Msg.alert('提示', '请选择私人客户');
									addaffiche.getForm().reset();
								}
							}
						});
			}
			});
			
			
//			var search_cust = new Ext.ux.form.CustomerQueryField(
//					{
//						fieldLabel : '客户姓名',
//						labelStyle : 'text-align:right;',
//						name : 'custName',
//						id : 'rel_cust_name',
//						editable : false,
//						allowBlank : false,// 不允许为空
//						blankText : "不能为空，请填写",
//						singleSelected : true,
//						anchor : '90%',
//						callback : function() {
//							custid = Ext.getCmp('rel_cust_name').customerId.aId[0];
//							Ext.Ajax.request( {
//										url : basepath + '/perCustQuery.json',
//										method : 'GET',
//										params : {
//											'condition' : Ext.encode( {
//												custId : custid
//												
//											})
//										},
//										success : function(response) {
//											var nodeArra = Ext.util.JSON.decode(response.responseText);
////				                            Ext.Msg.alert('提示', response.responseText);
//												store.data = nodeArra;
//												Ext.getCmp('currentGrade_1').setDisabled(false);
//												Ext.getCmp('currentGrade_1').setReadOnly(true);
//												if (store.data.json.count != 0) {
//													Ext.getCmp('custId_1').setReadOnly(true);
//													Ext.getCmp('custId_1').setValue(store.data.json.data[0].CUST_ID1);
//													setCertTypeValue(store.data.json.data[0].CERT_TYPE);
//													Ext.getCmp('currentGrade_1').setValue(store.data.json.data[0].CUST_LEV);
//													Ext.getCmp('certCode_1').setValue(store.data.json.data[0].CERT_NUM);
//													Ext.getCmp('workUnit_1').setValue(store.data.json.data[0].WRK_UNIT);
//													Ext.getCmp('position_1').setValue(store.data.json.data[0].POSITION);
//													Ext	.getCmp('salary_1').setValue(store.data.json.data[0].YEAR_INCOME);
//													Ext.getCmp('telphone_1').setValue(store.data.json.data[0].LINK_PHONE);
////												 if(store.data.json.data[0].CUST_TYPE=='1'){
//////													addaffiche.getForm().reset();
////													Ext.getCmp('custId_1').setReadOnly(false);
////													Ext.getCmp('custId_1').setValue('');
////													setCertTypeValue('');
////													Ext.getCmp('currentGrade_1').setValue('');
////													Ext.getCmp('certCode_1').setValue('');
////													Ext.getCmp('workUnit_1').setValue('');
////													Ext.getCmp('position_1').setValue('');
////													Ext	.getCmp('salary_1').setValue('');
////													Ext.getCmp('telphone_1').setValue('');
////													Ext.Msg.alert('提示', '没有此用户的信息,请手动录入');
////											}
//												}else{
//												Ext.getCmp('currentGrade_1').setDisabled(true);
//												Ext.Msg.alert('提示', '请选择私人客户');
//												addaffiche.getForm().reset();
//											}
//										}
//									});
//						}
//					});
			var addaffiche = new Ext.FormPanel( {
				formId : 'newNotice',
				frame : true,
				border : false,
				labelAlign : 'right',
				standardSubmit : false,
				layout : 'form',
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : [ search_cust ]
					}, {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : [ {
							id : 'custId_1',
							name : 'custId',
							fieldLabel : '客户编号',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 200,
							readOnly : true,
							anchor : '90%'
						} ]
					  	} ]
				},{
					layout : 'column',
					items : [ {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
						id:'certType_1',
						store : certTypStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '证件类型',
						name : 'certType',
						hiddenName : 'certType',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						width : '100',
						anchor : '90%'
					}
					}, {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
							id : 'certCode_1',
							name : 'certCode',
							fieldLabel : '证件号码',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 200,
							anchor : '90%'
				       }
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
							id : 'workUnit_1',
							name : 'workUnit',
							fieldLabel : '工作单位',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 200,
							anchor : '90%'
						}
					}, {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
							id : 'position_1',
							name : 'position',
							fieldLabel : '职务',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 200,
							anchor : '90%'
						}
					}, {// 特别注意：须放置隐藏域的主键
								name : 'id',
								xtype : 'hidden'
							} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
							id : 'salary_1',
							name : 'salary',
							fieldLabel : '年收入',
							xtype : 'numberfield', 
							width : 100,
							allowBlank : false,
							maxLength : 200,
							anchor : '90%'
						}
					}, {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
							id : 'telphone_1',
							name : 'telphone',
							fieldLabel : '联系电话',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 200,
							anchor : '90%'
						}
					} ]
				   }, {
					layout : 'column',
					items : [  {
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
							store : p_cust_grade,
							id:'currentGrade_1',
							xtype : 'combo',
							resizable : true,
							fieldLabel : '现有评级',
							name : 'currentGrade',
							hiddenName : 'currentGrade',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%',
							disabled:true
						}
					},{
						columnWidth : .5,
						labelWidth : 60,
						layout : 'form',
						items : {
						store : p_cust_grade,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '申请评级',
						name : 'toGrade',
						hiddenName : 'toGrade',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						width : '100',
						anchor : '90%'
					}
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 60,
					items : [ {
						id : '1123',
						name : 'reason',
						fieldLabel : '申请原因',
						xtype : 'textarea',
						width : 200,
						allowBlank : false,
						maxLength : 400,
						anchor : '95%'
					} ]
				} ]
			});
			/**
			 * grid toolbar.
			 */
			var tbar = new Ext.Toolbar(
					{
						items : [
								{
									text : '客户评级申请',
									iconCls:'importIconCss',
									handler : function() {
										Ext.each(addaffiche.getForm().items.items,
												function(f) {
													f.originalValue = '';
														});
										// 定义用户的客户id
										var win = new Ext.Window(
												{
													layout : 'fit',
													width : 900,
													height : 350,
													closable : true,
													resizable : false,
													collapsible : false,
													draggable : true,
													closeAction : 'hide',
													title : '客户评级申请',
													modal : true,
													animCollapse : false,
													maximizable : true,
													border : false,
													closable : true,
													animateTarget : Ext.getBody(),
													constrain : true,
													items : [ addaffiche ],
													buttonAlign : 'center',
													buttons : [{
																text : '确认',
																handler : function() {
																	if (!addaffiche.getForm().isValid()) {
																		Ext.MessageBox.alert('新增操作','请正确输入各项必要信息！');
																		return false;
																	}

																	var pars = addaffiche.getForm().getFieldValues();
//																	debugger;
																	Ext.Ajax.request( {
																				url : basepath + '/ocrmFCiGradeApply-info.json',
																				method : 'POST',
																				params : pars,
																				failure : function(	form,action) {
																					Ext.MessageBox.alert('新增操作',	'新增失败！');
																					restfulStore.load( {
																								params : {
																									start : 0,
																									limit : parseInt(pagesize_combo.getValue())
																								}
																							});
																				},
																				success : function(
																						response) {
																					Ext.MessageBox.alert('新增操作','新增成功！');
																					restfulStore.load( {
																								params : {
																									start : 0,
																									limit : parseInt(pagesize_combo.getValue())
																								}
																							});
																					win.hide();
																				}
																			});
																}
															},
															{
																text : '取消',
																handler : function() {
																	win.hide();
																}
															} ]
												});
										win.show();
										addaffiche.getForm().reset();
									}
								},'-',
								{
									id : '__upNot',
									text : '客户评级维护',
									iconCls:'maintainIconCss',
									handler : function() {
									Ext.getCmp('currentGrade_1').setDisabled(false);
										var _record = grid.getSelectionModel().getSelected();
										if (!_record) {
											Ext.MessageBox.alert('修改操作',
													'请选择要操作的记录！');
											return false;
										} else {
											var checkedNodes = grid	.getSelectionModel().selections.items;
											if (checkedNodes.length > 1) {
												Ext.MessageBox.alert('修改操作',	'您选择的记录过多！');
												return false;
											}

											var status = _record.get('status');
											if(status=='等待提交'||status=='审批拒绝'){
												var win = new Ext.Window(
														{
															layout : 'fit',
															width : 900,
															height : 350,
															closable : true,
															resizable : false,
															draggable : true,
															closeAction : 'hide',
															title : '修改公告',
															collapsible : false,
															modal : true,
															animCollapse : false,
															maximizable : true,
															border : false,
															closable : true,
															animateTarget : Ext
															.getBody(),
															constrain : true,
															items : [ addaffiche ],
															buttonAlign : 'center',
															buttons : [
															           {
															        	   text : '确认',
															        	   handler : function() {
															        	   if (!addaffiche.getForm().isValid()) {
															        		   Ext.MessageBox.alert('新增操作','请正确输入各项必要信息！');
															        		   return false;
															        	   }
															        	   var pars = addaffiche.getForm().getFieldValues();
															        	   Ext.Ajax.request( {
															        		   url : basepath + '/ocrmFCiGradeApply-info.json',
															        		   method : 'POST',
															        		   params : pars,
															        		   failure : function(form,	action) {
															        		   Ext.MessageBox.alert('修改操作','修改失败！');
															        		   restfulStore.load( {params : {
															        			   start : 0,
															        			   limit : parseInt(pagesize_combo.getValue())
															        		   }
															        		   });
															        	   },
															        	   success : function(
															        			   response) {
															        		   Ext.MessageBox
															        		   .alert(
															        				   '修改操作',
															        		   '修改成功！');
															        		   restfulStore.load( {
															        			   params : {
															        			   start : 0,
															        			   limit : parseInt(pagesize_combo.getValue())
															        		   }
															        		   });
															        		   win.hide();
															        	   }
															        	   });
															           }
															           },
															           {
															        	   text : '取消',
															        	   handler : function() {
															        	   win.hide();
															           }
															           } ]
														});
												var record = grid.getSelectionModel().getSelected();
												addaffiche.getForm().loadRecord(record);
												win.show();
												
											}else{
												Ext.MessageBox.alert('提示',	'申请已提交，不能修改！');
											}

										}
									}
								},'-',{
									text : '提交申请(审批)',
									iconCls:'shenpiIconCss',
										handler : function() {
											var record_1 = grid.getSelectionModel().getSelected();
											if(!record_1){
												Ext.MessageBox.alert('提示','请选择要操作的记录！');
												return false;
											}else{
												var status = record_1.get('status');
												if(status=='等待提交'||status=='审批拒绝'){
													var instanceid = record_1.get('id');
													Ext.Ajax.request({
														url : basepath + '/ocrmFCiGradeApply-info!initFlow.json',
														method : 'POST',
														params : {
														instanceid:instanceid
														,custname:record_1.get('custName')
													},
													waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : function() {
														Ext.Msg.alert('提示', '操作成功');
														//直接提交到主管评价节点
//														var url = basepath+'/echaincommonservlet?method=echainflowdemo&actionType=submit&instanceid='+instanceid+'&nodeid=13_a3&nextnodeid=13_a4&userid='+__userId+'&orgid='+__units+'';
//														var retObj = window.showModalDialog(url,'result','dialogHeight:300px;dialogWidth:600px;help:no;resizable:no;status:no;');
														restfulStore.load( {
															params : {
															start : 0,
															limit : parseInt(pagesize_combo.getValue())
														}
														});
														
													},
													failure : function() {
														Ext.Msg.alert('提示', '操作失败');
														restfulStore.load( {
															params : {
															start : 0,
															limit : parseInt(pagesize_combo.getValue())
														}
														});
													}
													});
													
												}else{
													Ext.MessageBox.alert('提示','申请已提交，不能重复提交！');
												}
											}
										}
								} ]
					});
			/** 公告信息表格* */
			var grid = new Ext.grid.GridPanel( {
				title : '客户评级申请',
				frame : true,
				autoScroll : true,
				store : restfulStore,
				region : 'center',
				stripeRows : true,
				tbar : tbar,
				sm : sm,
				columns : [ rownum, sm, {
					hidden : true,
					header : 'id',
					dataIndex : 'id',
					sortable : true
				}, {
					header : '客户编号',
					dataIndex : 'custId',
					sortable : true,
					width : 70
				}, {
					header : '客户姓名',
					dataIndex : 'custName',
					sortable : true,
					width : 70
				}, {
					header : '证件类型',
					dataIndex : 'certType',
					width : 100,
					sortable : true,
					renderer : function(value){
						if(value==undefined)
							return value;
						else if(certTypStore.query('key',value,false,true).first()==undefined)
							return value;
						else	
							return certTypStore.query('key',value,false,true).first().get('value');
					}
				}, {
					header : '证件号码',
					width : 100,
					dataIndex : 'certCode'
				}, {
					header : '联系电话',
					dataIndex : 'telphone',
					width : 90
				}, {
					header : '工作单位',
					dataIndex : 'workUnit',
					sortable : true,
					width : 130
				}, {
					header : '职务',
					width : 70,
					sortable : true,
					dataIndex : 'position'
				}, {
					header : '年收入',
					width : 70,
					sortable : true,
					dataIndex : 'salary',
					align:'right;',renderer: money('0,000.00')
				}, {
					width : 70,
					name : 'currentGrade',
					dataIndex : 'currentGrade',
					header : '现有评级',
					sortable : true,
					align:'right;',
					renderer : function(value){
					//debugger;
						if(value==undefined||value=="")
							return "";
						else if(p_cust_grade.query('key',value,false,true).first()==undefined)
							return "";
						else	
							return p_cust_grade.query('key',value,false,true).first().get('value');
						
					}
				}, {
					width : 70,
					dataIndex : 'toGrade',
					name:'toGrade',
					header : '申请评级',
					align:'right;',
					sortable : true,
					renderer : function(value){
						if(value==undefined||value=="")
							return "";
						else if(p_cust_grade.query('key',value,false,true).first()==undefined)
							return "";
						else	
							return p_cust_grade.query('key',value,false,true).first().get('value');
					}
				}, {
					header : '申请人',
					dataIndex : 'applyUser',
					sortable : true,
					width : 70
				}, {
					header : '申请机构',
					dataIndex : 'applyOrg',
					sortable : true,
					width : 70
				}, {
					header : '申请日期',
					width : 70,
					sortable : true,
					dataIndex : 'applyDate',
					renderer:function(value){
						if(value != null){
							return dateTostr(new Date(value.time));//    new Date(value.time).toString();
						}else{
							return value;
						}
					}
				}, {
					header : '状态',
					dataIndex : 'status',
					sortable : true,
					width : 60
//					,renderer : function(value){
//						if(value=='审批拒绝'){
//							return '<font color="red">'+value+'</font>';
//						}else if(value=='审批通过'){
//							return '<font color="green">'+value+'</font>';
//						}else if(value=='等待审核'){
//							return '<font color="blue">'+value+'</font>';
//						}
//						else{
//							return value;
//						}
//					}
				} , {
					header : '申请原因',
					dataIndex : 'reason',
					width : 170
				}],
				selModel : new Ext.grid.RowSelectionModel( {
					singleSelect : true
				}),
				bbar : bbar,
				viewConfig : {},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
			var viewport = new Ext.Viewport( {
				layout : 'fit',
				items : [ {
					layout : 'border',
					items : [ grid ]
				} ]
			});
			
			 function dateTostr(date)
			    {
			      yy=date.getYear();
			      mm=date.getMonth()+1;
			      if(mm<10){
			    	  mm='0'+mm;
			      }
			      dd=date.getDate();
			      if(dd<10){
			    	  dd='0'+dd;
			      }
			      datestr=yy+'-'+mm+'-'+dd;
			      return datestr;
			     }


		});