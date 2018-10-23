Ext.onReady(function() {

	var activityIdTemp = '';
	var activityStore = new Ext.data.JsonStore( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/market-activity.json'
		}),
		fields : [ 'marketActivityId', 'marketActivityName' ],
		reader : new Ext.data.JsonReader( {
			totalProperty : 'list'
		}, [ {
			name : 'marketActivityId',
			mapping : 'marketActivityId'
		}, {
			name : 'marketActivityName',
			mapping : 'marketActivityName'
		} ])
	});

	var chanceStatStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CHANCE_STATUS'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var chanceTypeStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=OPPOR_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var searchPanel = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		height : 100,
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .25,
			layout : 'form',
			items : [{
				xtype : 'textfield',
//				labelStyle : {
//					width : '120px'
//				},
				name : 'MKT_OPPOR_NAME',
				fieldLabel : '商机名称',
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [ {
				store : chanceStatStore,
				xtype : 'combo',
				resizable : true,
				resizable : true,
				name : 'MKT_OPPOR_STAT',
				hiddenName : 'MKT_OPPOR_STAT',
				fieldLabel : '商机状态',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%'
			} ]
		},{
		    columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '创建日期',
				format : 'Y-m-d',
				xtype : 'datefield',
				editable:false,
				name : 'createDateS',
				id : 'createDateS',
				anchor : '90%'
			}]
		},{
		    columnWidth : .25,
            layout : 'form',
            labelWidth : 20,
			items : [{
				xtype : 'datefield',
				fieldLabel : '至',
				format : 'Y-m-d',
				name : 'createDateE',
				id : 'createDateE',
				editable:false,
				anchor : '65%',
				listeners : {
					'blur' : function() {
						var start = Ext.getCmp('createDateS').getValue();
						var end = Ext.getCmp('createDateE').getValue();
						if (start == '' && end != '') {
							Ext.Msg.alert('消息框','请先选择开始时间！');
							Ext.getCmp('createDateE').reset();
						} else if (end != ''&& start > end) {
							Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
							Ext.getCmp('createDateE').reset();
						}
					}
				}
			}]
		} ],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = searchPanel.getForm().getValues(false);
				store.on('beforeLoad', function() {
					this.baseParams = {
						"condition" : Ext.encode(conditionStr)
					};
				});
				store.load( {
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});

			}
		}, {
			text : '重置',
			handler : function() {
				searchPanel.form.reset();
			}
		}]
	});

	// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		var columns = new Ext.grid.ColumnModel( [ rownum, {
			header : '商机状态',
			width : 60,
			align : 'center',
			dataIndex : 'marketOpportunityStatementOra',
			sortable : true
		}, {
			header : '商机名称',
			width : 175,
			align : 'center',
			dataIndex : 'marketOpportunityName',
			sortable : true
		}, {
			header : '客户名称',
			width : 200,
			align : 'center',
			dataIndex : 'aimCustomerName',
			sortable : true
		}, {
			header : '执行人',
			width : 150,
			align : 'center',
			dataIndex : 'operUserId',
			sortable : true
		}, {
			header : '计划开始日期',
			width : 150,
			align : 'center',
			dataIndex : 'opportunityStartDate',
			sortable : true
		}, {
			header : '预计结束日期',
			width : 150,
			align : 'center',
			dataIndex : 'opportunityPlanEndDate',
			sortable : true
		}, {
			header : '实际完成日期',
			width : 150,
			align : 'center',
			dataIndex : 'opportunityEndDate',
			sortable : true
		} ]);

		var record = Ext.data.Record.create( [ {
			name : 'marketOpporId',
			mapping : 'MKT_OPPOR_ID'
		}, {
			name : 'aimCustomerId',
			mapping : 'AIM_CUST_ID'
		}, {
			name : 'aimCustomerName',
			mapping : 'AIM_CUST_NAME'
		}, {
			name : 'createDate',
			mapping : 'CREATE_DATE'
		}, {
			name : 'createUser',
			mapping : 'CREATE_USER'
		}, {
			name : 'updateDate',
			mapping : 'UPDATE_DATE'
		}, {
			name : 'updateUser',
			mapping : 'UPDATE_USER'
		}, {
			name : 'marketOpportunityName',
			mapping : 'MKT_OPPOR_NAME'
		}, {
			name : 'marketOpportunityStatement',
			mapping : 'MKT_OPPOR_STAT'
		}, {
			name : 'marketOpportunityStatementOra',
			mapping : 'MKT_OPPOR_STAT_ORA'
		}, {
			name : 'marketOpportunityType',
			mapping : 'MKT_OPPOR_TYPE'
		}, {
			name : 'marketOpportunityTypeOra',
			mapping : 'MKT_OPPOR_TYPE_ORA'
		}, {
			name : 'operUserId',
			mapping : 'OPER_USER_ID'
		}, {
			name : 'opportunityAnalysis',
			mapping : 'OPPOR_ANALYSIS'
		}, {
			name : 'opportunityContent',
			mapping : 'OPPOR_CONTENT'
		}, {
			name : 'opportunityEndDate',
			mapping : 'OPPOR_END_DATE'
		}, {
			name : 'opportunityPlanEndDate',
			mapping : 'OPPOR_PLANEND_DATE'
		}, {
			name : 'opportunityStartDate',
			mapping : 'OPPOR_START_DATE'
		}, {
			name : 'marketActivityId',
			mapping : 'MKT_ACTI_ID'
		} ]);

		var store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/marketOpportunityQuery.json'
			}),
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',
				idProperty : 'MKT_OPPOR_ID',
				messageProperty : 'message',
				root : 'json.data',
				totalProperty : 'json.count'
			}, record)
		});

		// 每页显示条数下拉选择框
		var pagesize_combo = new Ext.form.ComboBox( {
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore(
					{
						fields : [ 'value', 'text' ],
						data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
								[ 50, '50条/页' ], [ 100, '100条/页' ],
								[ 250, '250条/页' ], [ 500, '500条/页' ] ]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			resizable : true,
			width : 85
		});

		// 默认加载数据
		store.load( {
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});

		// 改变每页显示条数reload数据
		pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});
		});
		// 分页工具栏
		var bbar = new Ext.PagingToolbar( {
			pageSize : parseInt(pagesize_combo.getValue()),
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
		});

		var listPanel = new Ext.grid.GridPanel(
				{
					store : store,
					frame : true,
					cm : columns,
					stripeRows : true,
					tbar : [
							{
								text : '新增',
								iconCls : 'addIconCss',
								// iconCls : 'page_addIcon',
								handler : function() {
									addInit();
									addChanceForm.getForm().reset();
								}
							},
							'-',
							{
								text : '修改',

								iconCls : 'editIconCss',
								handler : function() {
									// 得到选中记录
								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];

								if (selectRe == null || selectRe == "undefined") {
									Ext.Msg.alert('提示', '请选择一条记录!');
								} else {

									var marketOpportunityStatement = selectRe.data.marketOpportunityStatement;
									if (marketOpportunityStatement != '1') {
										Ext.Msg.alert('提示', '只能修改暂存的商机!');
									} else {
										editInit();
										editChanceForm.getForm().loadRecord(
												selectRe);
									}
								}
							}
							},
							'-',
							{
								text : '删除',
								iconCls : 'deleteIconCss',
								handler : function() {
									// 得到选中记录
								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];
								var marketOpportunityStatement = selectRe.data.marketOpportunityStatement;
								if (selectRe == null || selectRe == 'undefined') {
									Ext.Msg.alert('提示', '请选择一条记录!');
								} else {
									if (marketOpportunityStatement != '1') {
										Ext.Msg.alert('提示', '只能删除暂存的营销计划!');
									} else {
										Ext.MessageBox
												.confirm(
														'提示',
														'确定删除吗?',
														function(buttonId) {
															if (buttonId
																	.toLowerCase() == "no") {
																return;
															}
															Ext.Ajax
																	.request( {
																		url : basepath
																				+ '/market-opportunity!destroy.json?marketOpportunityId='
																				+ selectRe
																						.get('marketOpporId'),
																		method : 'GET',
																		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																		success : function() {
																			Ext.Msg
																					.alert(
																							'提示',
																							'操作成功');
																			store
																					.reload();
																		},
																		failure : function(
																				response) {
																			Ext.Msg
																					.alert(
																							'提示',
																							'操作失败,失败原因:' + response.responseText);
																			store
																					.reload();
																		}
																	});

														});
									}
								}
							}
							},
							'-',
							{
								text : '商机分配',
								iconCls : 'taskDistrIconCss',
								handler : function() {
									// 得到选中记录
								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];
								var marketOpportunityStatement = selectRe.data.marketOpportunityStatement;
								if (selectRe == null || selectRe == "undefined") {
									Ext.Msg.alert('提示', '请选择一条记录!');
								} else {
									if (marketOpportunityStatement != '1') {
										Ext.Msg.alert('提示', '只能分配暂存的营销计划!');
									} else {
										activityIdTemp = selectRe.data.marketOpporId;
										// alert(id);
										editChanceForm.getForm().loadRecord(
												selectRe);
										mgrAdjustWin.show();
									}
								}
							}
							},
							'-',
							{
								text : '商机跟踪',
								iconCls : 'dailyIconCss',
								handler : function() {
									// 得到选中记录
								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];
								var marketOpportunityStatement = selectRe.data.marketOpportunityStatement;
								if (selectRe == null || selectRe == 'undefined') {
									Ext.Msg.alert('提示', '请选择一条记录!');
								} else {
									document.getElementById('marketOpporIdStr').value = selectRe.data.marketOpporId;

									// 默认加载数据
									traceStore
											.load( {
												params : {
													start : 0,
													limit : 9999999,
													marketOpporId : document
															.getElementById('marketOpporIdStr').value
												}
											});

									followInit();
								}
							}
							}, '-', {
								text : '销售漏斗',
								iconCls : 'fitDaXiaoIconCss',
								handler : function() {
									xiaoShouLouDou();
								}
							}, '-', {
								text : '关闭商机',
								iconCls : 'closeIconCss',
								handler : function() {
									closeChance();
								}
							} ],
					region : 'center',
					store : store,
					frame : true,
					bbar : bbar
				});

		// 分配商机Start************************************************************

		// 分配商机列表的数据查询
		var mgrAdjustStore = new Ext.data.Store(
				{
					restful : true,
					autoLoad : true,
					proxy : new Ext.data.HttpProxy(
							{
								url : basepath + '/cust_bel_custmgr_adjust!indexPage.json?cust_id=100'
							}),
					reader : new Ext.data.JsonReader( {
						root : 'json.data'
					}, [ {
						name : 'cId'
					}, {
						name : 'custId'
					}, {
						name : 'mgrId'
					}, {
						name : 'mgrName'
					}, {
						name : 'institutionCode'
					}, {
						name : 'institutionName'
					}, {
						name : 'mainType'
					} ])
				});
		// 定义自动当前页行号
		var mgrAdjustRownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		// 分配商机列表的模型
		var mgrAdjustCm = new Ext.grid.ColumnModel( [ mgrAdjustRownum, {
			dataIndex : 'cId',
			hidden : true
		}, {
			dataIndex : 'custId',
			hidden : true
		}, {
			header : '客户经理编号',
			dataIndex : 'mgrId',
			sortable : true,
			width : 100
		}, {
			header : '客户经理名称',
			dataIndex : 'mgrName',
			sortable : true,
			width : 150
		}, {
			header : '所属机构号',
			dataIndex : 'institutionCode',
			sortable : true,
			width : 100,
			hidden : true
		}, {
			dataIndex : 'institutionName',
			header : '所属机构名称',
			sortable : true,
			width : 200
		} ]);
		// 分配商机调整grid
		var mgrAdjustGrid = new Ext.grid.GridPanel( {
			// tbar: ['->','主协办类型为空时，取消该分配'],
			autoScroll : true,
			// clicksToEdit : 1,
			store : mgrAdjustStore, // 数据存储
			cm : mgrAdjustCm, // 列模型
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		// 分配商机列表模态窗口
		var mgrAdjustWin = new Ext.Window(
				{
					plain : true,
					layout : 'fit',
					resizable : true,
					draggable : true,
					closable : true,
					autoScroll : true,
					closeAction : 'hide',
					modal : true, // 模态窗口
					shadow : true,
					loadMask : true,
					maximizable : true,
					collapsible : true,
					titleCollapse : true,
					border : false,
					width : 580,
					height : 400,
					buttonAlign : "center",
					title : '客户经理列表',
					items : mgrAdjustGrid,
					buttons : [
							{
								text : '分配',
								handler : function() {
									var selectRe1 = mgrAdjustGrid
											.getSelectionModel()
											.getSelections()[0];
									if (selectRe1 == null
											|| selectRe1 == "undefined") {
										Ext.Msg.alert('提示', '请选择一条记录!');
									} else {
										if (activityIdTemp != '') {
											var mgrId = selectRe1.data.mgrId;
											// Ext.getCmp('operUserId').setValue(mgrId);
											Ext.Ajax
													.request( {
														url : basepath + '/market-opportunity!assignMgr.json',
														params : {
															activityIdTemp : activityIdTemp,
															mgrId : mgrId
														},
														method : 'POST',
														// form :
														// editChanceForm.getForm().id,
														waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
														success : function() {
															Ext.Msg.alert('提示',
																	'操作成功');
															store.reload();
														},
														failure : function(
																response) {
															Ext.Msg
																	.alert(
																			'提示',
																			'操作失败,失败原因:' + response.responseText);
															store.reload();
														}
													});
										}
									}

								}
							}, {
								text : '关闭',
								handler : function() {
									mgrAdjustWin.hide();
								}
							} ]
				});

		// 分配商机end************************************************************
		// -------------------------------------------------------------------------------------------
		var search_cust_add = new Ext.ux.form.CustomerQueryField(
				{
					fieldLabel : '客户名称',
					labelStyle : 'text-align:right;',
					labelWidth : 100,
					name : 'aimCustomerName',
					id : 'add_aimCustomerName',
					editable : false,
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写",
					singleSelected : true,
					anchor : '90%',
					callback : function() {
						var cust_id = null;
						var cust_name = null;
						cust_name = Ext.getCmp('add_aimCustomerName')
								.getValue();
						if (cust_name != null && cust_name != '') {
							cust_id = Ext.getCmp('add_aimCustomerName').customerId.aId[0];
							addChanceForm.getForm().findField('aimCustomerId')
									.setValue(cust_id);
						}
					}
				});
		// 新增窗口展示的from
		var addChanceForm = new Ext.FormPanel({
					// formId:'addform',
					labelWidth : 100,
					height : 250,
					frame : true,
					autoScroll : true,
					labelAlign : 'right',
					// labelStyle: 'text-align:right;',
					buttonAlign : "center",
					items : [
							{
								layout : 'column',
								items : [ {
									columnWidth : .5,
									layout : 'form',
									items : [ {
										store : activityStore,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '营销活动',
										name : 'marketActivityId',
										hiddenName : 'marketActivityId',
										valueField : 'marketActivityId',
										displayField : 'marketActivityName',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}, {
										store : chanceTypeStore,
										xtype : 'combo',
										resizable : true,
										resizable : true,
										name : 'marketOpportunityType',
										hiddenName : 'marketOpportunityType',
										fieldLabel : '商机类型',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}, new Ext.form.DateField( {
										name : 'opportunityStartDate',
										id : 'opportunityStartDate',
										format : 'Y-m-d',
										value : '',
										editable : false,
										fieldLabel : '开始日期',
										anchor : '90%'
									}), new Ext.form.DateField( {
										fieldLabel : '预计结束日期',
										value : '',
										format : 'Y-m-d',
										editable : false,
										name : 'opportunityPlanEndDate',
										id : 'opportunityPlanEndDate',
										anchor : '90%'
									}) ]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [ {
										xtype : 'textfield',
										fieldLabel : '商机名称',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										name : 'marketOpportunityName',
										anchor : '90%'
									}, {
										xtype : 'textfield',
										fieldLabel : '执行人',
										name : 'operUserId',
										anchor : '90%'
									}, search_cust_add, {
										xtype : 'hidden',
										name : 'aimCustomerId'
									} ]
								} ]
							},
							{
								layout : 'form',
								buttonAlign : 'center',
								items : [ {
									xtype : 'textarea',
									fieldLabel : '商机内容',
									name : 'opportunityContent',
									anchor : '95%'
								}, {
									xtype : 'textarea',
									fieldLabel : '商机分析说明',
									name : 'opportunityAnalysis',
									anchor : '95%'
								}, {
									xtype : 'hidden',
									fieldLabel : '商机状态',
									value : '1',
									id : 'marketOpportunityStatementNew',
									name : 'marketOpportunityStatement',
									anchor : '95%'
								} ],
								buttons : [

										{

											text : '保  存',
											handler : function() {
												if (!addChanceForm.form
														.isValid()) {
													Ext.Msg.alert('提示',
															'输入格式不合法，请重新输入');
													return;
												}
												var start = Ext.getCmp(
														'opportunityStartDate')
														.getValue();
												var end = Ext
														.getCmp(
																'opportunityPlanEndDate')
														.getValue();
												if (start == '' && end != '') {
													Ext.Msg.alert('消息框',
															'请先选择开始时间！');
													Ext
															.getCmp(
																	'opportunityPlanEndDate')
															.reset();
													return;
												} else if (end != ''
														&& start > end) {
													Ext.Msg.alert('消息框',
															'开始时间大于结束时间，请检查！');
													Ext
															.getCmp(
																	'opportunityPlanEndDate')
															.reset();
													return;
												}
												Ext.Ajax
														.request( {
															url : basepath + '/market-opportunity.json',
															method : 'POST',
															form : addChanceForm
																	.getForm().id,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert(
																		'提示',
																		'操作成功');
																store.reload();
															},
															failure : function(
																	response) {
																Ext.Msg
																		.alert(
																				'提示',
																				'操作失败,失败原因:' + response.responseText);
																store.reload();
															}
														});
												addChanceWindow.hide();
											}

										},
										{

											text : '提  交',
											handler : function() {
												if (!addChanceForm.form
														.isValid()) {
													Ext.Msg.alert('提示',
															'输入格式不合法，请重新输入');
													return;
												}
												document
														.getElementById('marketOpportunityStatementNew').value = '2';
												Ext.Ajax
														.request( {
															url : basepath + '/market-opportunity.json',
															method : 'POST',
															form : addChanceForm
																	.getForm().id,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert(
																		'提示',
																		'操作成功');
																store.reload();
															},
															failure : function(
																	response) {
																Ext.Msg
																		.alert(
																				'提示',
																				'操作失败,失败原因:' + response.responseText);
																store.reload();
															}
														});
												addChanceWindow.hide();
											}

										}, {
											text : '取  消',
											handler : function() {
												addChanceWindow.hide();
											}
										} ]
							} ]

				});

		var search_cust_update = new Ext.ux.form.CustomerQueryField(
				{
					fieldLabel : '客户名称',
					labelWidth : 100,
					labelStyle : 'text-align:right;',
					name : 'aimCustomerName',
					id : 'update_aimCustomerName',
					editable : false,
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写",
					singleSelected : true,
					anchor : '90%',
					callback : function() {
						var cust_id = null;
						var cust_name = null;
						cust_name = Ext.getCmp('update_aimCustomerName')
								.getValue();
						if (cust_name != null && cust_name != '') {
							cust_id = Ext.getCmp('update_aimCustomerName').customerId.aId[0];
							editChanceForm.getForm().findField('aimCustomerId')
									.setValue(cust_id);
						}
					}
				});
	// 修改窗口展示的from
	var editChanceForm = new Ext.FormPanel({
					// formId:'editform',
					labelWidth : 100,
					labelAlign : 'right',
					height : 250,
					frame : true,
					region : 'center',
					autoScroll : true,
					buttonAlign : "center",
					items : [
							{
								layout : 'column',
								items : [ {
									columnWidth : .5,
									layout : 'form',
									items : [ {
										store : activityStore,
										xtype : 'combo',
										resizable : true,
										fieldLabel : '营销活动',
										name : 'marketActivityId',
										hiddenName : 'marketActivityId',
										valueField : 'marketActivityId',
										displayField : 'marketActivityName',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}, {
										store : chanceTypeStore,
										xtype : 'combo',
										resizable : true,
										resizable : true,
										name : 'marketOpportunityType',
										hiddenName : 'marketOpportunityType',
										fieldLabel : '商机类型',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}, {
										name : 'opportunityStartDate',
										id : 'opportunityStartDate_update',
										xtype : 'datefield',
										format : 'Y-m-d',
										editable : false,
										fieldLabel : '开始日期',
										anchor : '90%'
									}, {
										xtype : 'datefield',
										fieldLabel : '预计结束日期',
										format : 'Y-m-d',
										editable : false,
										name : 'opportunityPlanEndDate',
										id : 'opportunityPlanEndDate_update',
										anchor : '90%'
									} ]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [ {
										xtype : 'textfield',
										fieldLabel : '商机名称',
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										name : 'marketOpportunityName',
										anchor : '90%'
									}, {
										id : 'operUserId',
										xtype : 'textfield',
										fieldLabel : '执行人',
										name : 'operUserId',
										anchor : '90%'
									}, search_cust_update, {
										xtype : 'hidden',
										name : 'aimCustomerId'
									} ]
								} ]
							},
							{
								layout : 'form',
								buttonAlign : 'center',
								items : [ {
									xtype : 'textarea',
									fieldLabel : '商机内容',
									name : 'opportunityContent',
									anchor : '95%'
								}, {
									xtype : 'textarea',
									fieldLabel : '商机分析说明',
									name : 'opportunityAnalysis',
									anchor : '95%'
								}, {
									xtype : 'hidden',
									fieldLabel : '商机状态',
									value : '1',
									id : 'marketOpportunityStatementEdit',
									name : 'marketOpportunityStatement',
									anchor : '95%'
								}, {
									xtype : 'hidden',
									fieldLabel : '创建人',
									name : 'createUser',
									anchor : '90%'
								}, {
									xtype : 'hidden',
									fieldLabel : '创建日期',
									name : 'createDate',
									anchor : '90%'
								}, {
									xtype : 'hidden',
									fieldLabel : '更新人',
									name : 'updateUser',
									anchor : '90%'
								}, {
									xtype : 'hidden',
									fieldLabel : '更新日期',
									name : 'updateDate',
									anchor : '90%'
								}, {
									xtype : 'hidden',
									fieldLabel : '商机ID',
									name : 'marketOpporId',
									anchor : '90%'
								}, {
									xtype : 'hidden',
									fieldLabel : '实际完成日期',
									name : 'opportunityEndDate',
									anchor : '90%'
								} ],
								buttons : [

										{

											text : '保  存',
											handler : function() {
												if (!editChanceForm.form
														.isValid()) {
													Ext.Msg.alert('提示',
															'输入格式不合法，请重新输入');
													return;
												}
												var start = Ext
														.getCmp(
																'opportunityStartDate_update')
														.getValue();
												var end = Ext
														.getCmp(
																'opportunityPlanEndDate_update')
														.getValue();
												if (start == '' && end != '') {
													Ext.Msg.alert('消息框',
															'请先选择开始时间！');
													Ext
															.getCmp(
																	'opportunityPlanEndDate_update')
															.reset();
													return false;
												} else if (end != ''
														&& start > end) {
													Ext.Msg.alert('消息框',
															'开始时间大于结束时间，请检查！');
													Ext
															.getCmp(
																	'opportunityPlanEndDate_update')
															.reset();
													return false;
												}
												Ext.Ajax
														.request( {
															url : basepath + '/market-opportunity!update.json',
															method : 'POST',
															form : editChanceForm
																	.getForm().id,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert(
																		'提示',
																		'操作成功');
																store.reload();
															},
															failure : function(
																	response) {
																Ext.Msg
																		.alert(
																				'提示',
																				'操作失败,失败原因:' + response.responseText);
																store.reload();
															}
														});
												editChanceWindow.hide();
											}

										},
										{

											text : '提  交',
											handler : function() {
												if (!editChanceForm.form
														.isValid()) {
													Ext.Msg.alert('提示',
															'输入格式不合法，请重新输入');
													return;
												}
												document
														.getElementById('marketOpportunityStatementEdit').value = '2';
												Ext.Ajax
														.request( {
															url : basepath + '/market-opportunity!update.json',
															method : 'POST',
															form : editChanceForm
																	.getForm().id,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert(
																		'提示',
																		'操作成功');
																store.reload();
															},
															failure : function(
																	response) {
																Ext.Msg
																		.alert(
																				'提示',
																				'操作失败,失败原因:' + response.responseText);
																store.reload();
															}
														});
												editChanceWindow.hide();
											}

										}, {
											text : '取  消',
											handler : function() {
												editChanceWindow.hide();
											}
										} ]
							} ]

				});
		//
		// // 定义修改窗口的tabPanel
		// var tokenDelimiter = ':';
		// var editTp = new Ext.TabPanel({
		// id : 'editChanceTabs',
		// activeTab : 0,
		// tabPosition : 'bottom',
		//
		// // height: 450,
		// // width: '100%',
		// items : [
		// // {
		// // xtype: 'tabpanel',
		// // id: 'editTab',
		// // activeTab: 0,
		// // tabPosition: 'bottom',
		// // items: [
		// {
		// title : '基本信息',
		// id : 'baseTab',
		// items : [editChanceForm]
		// }, {
		// title : '目标明细',
		// id : 'aimTab',
		// items : [aimListPanel]
		// }, {
		// title : '客户明细',
		// id : 'custTab',
		// items : [p]
		// }, {
		// title : '执行人明细',
		// id : 'executorListTab',
		// items : [executorListPanel]
		//
		// }, {
		// title : '费用明细',
		// id : 'chargeListTab',
		// items : [chargeListPanel]
		// }, {
		// title : '附件明细',
		// id : 'annexListTab',
		// items : [annexListPanel]
		// }, {
		// title : '商机跟踪',
		// id : 'followListTab',
		// items : [followListPanel]
		// }],
		//
		// listeners : {
		// 'tabchange' : function(tabPanel, tab) {
		// if (tab.id != 'editTab') {
		// Ext.History.add(tabPanel.id + tokenDelimiter
		// + tab.id);
		// }
		// }
		// }
		// });
		//
		// Ext.History.on('change', function(token) {
		// if (token) {
		// var parts = token.split(tokenDelimiter);
		// var tabPanel = Ext.getCmp(parts[0]);
		// var tabId = parts[1];
		//
		// tabPanel.show();
		// tabPanel.setActiveTab(tabId);
		// } else {
		//
		// tp.setActiveTab(0);
		// tp.getItem(0).setActiveTab(0);
		// }
		//
		// });
		//
		// // 修改窗口展示的from
		// var editChancePanel = new Ext.Panel({
		// labelWidth : 150,
		// // height : 480,
		// // frame : true,
		// layout : 'fit',
		// autoScroll : true,
		// buttonAlign : "center",
		// items : [editTp]
		// });

		// 定义新增窗口
		var addChanceWindow = new Ext.Window( {
			title : '商机新增',
			plain : true,
			layout : 'fit',
			width : 800,
			height : 350,
			resizable : true,
			draggable : true,
			closable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			loadMask : true,
			maximizable : true,
			collapsible : true,
			titleCollapse : true,
			buttonAlign : 'right',
			border : false,
			constrain : true,
			items : [ addChanceForm ]
		});

		// 定义修改窗口
		var editChanceWindow = new Ext.Window( {
			title : '商机修改',
			plain : true,
			layout : 'fit',
			width : 880,
			height : 350,
			resizable : true,
			draggable : true,
			closable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			loadMask : true,
			maximizable : true,
			collapsible : true,
			titleCollapse : true,
			border : false,
			items : [ editChanceForm ]
		});

		// 展示新增窗口
		function addInit() {
			addChanceWindow.show();
			Ext.getCmp('opportunityStartDate').setValue('');
			Ext.getCmp('opportunityPlanEndDate').setValue('');
			debugger;

		}
		// 展示修改窗口
		function editInit() {
			editChanceWindow.show();
			debugger;
		}

		// 展示商机跟踪窗口
		function followInit() {
			traceWindow.show();
		}

		// 关闭商机的操作
		function closeChance() {
			// 得到选中记录
			var selectRe = listPanel.getSelectionModel().getSelections()[0];
			var marketOpportunityStatement = selectRe.data.marketOpportunityStatement;
			if (selectRe == null || selectRe == 'undefined') {
				Ext.Msg.alert('提示', '请选择一条记录!');
			} else {
				if (marketOpportunityStatement != '2') {
					Ext.Msg.alert('提示', '只能关闭执行中的商机!');
				} else {
					Ext.MessageBox
							.confirm('提示',
									'确定关闭吗?',
									function(buttonId) {
										if (buttonId.toLowerCase() == "no") {
											return;
										}
										// var jsonArray = [];
									selectRe.data.marketOpportunityStatement = '3'; // 将状态设置为关闭
									var sysDate = new Date();
									selectRe.data.opportunityEndDate = sysDate; // 取当前日期设置为实际完成日期
									// selectRe.data.updateDate = sysDate;
									// //取当前日期设置为最近更新日期
									// jsonArray.push(selectRe.data);
									// //将数据存放在jsonArray数组中
									// var tempJson;
									// if (jsonArray.length>0)
									// tempJson = Ext.encode(jsonArray);
									// //把数组转化成字符串
									editChanceWindow.show();
									editChanceForm.getForm().loadRecord(
											selectRe);
									Ext.Ajax
											.request( {
												url : basepath + '/market-opportunity!update.json',
												method : 'POST',
												form : editChanceForm.getForm().id,
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													store.reload();
												},
												failure : function(response) {
													Ext.Msg
															.alert(
																	'提示',
																	'操作失败,失败原因:' + response.responseText);
													store.reload();
												}
											});
									editChanceWindow.hide();
								});
				}
			}
		}
		var view = new Ext.Viewport( {
			layout : "fit",
			frame : true,
			items : [ {
				layout : 'border',
				items : [

				// {
						// region : 'south',
						// id : 'south-panel',
						// collapsible : true,
						// title : "流程提示",
						// split : true,
						// height : 90,
						// minSize : 80,
						// maxSize : 200,
						// collapsible : true,
						// hidden : false,
						// margins : '0 0 0 0',
						// contentEl : 's1'
						// },

						{
							region : 'center',
							id : 'center-panel',
							title : "商机列表",
							layout : 'fit',
							items : [ listPanel ]
						},

						{
							region : 'north',
							id : 'north-panel',
							title : "商机查询",
							height : 105,
							layout : 'fit',
							items : [ searchPanel ]
						}

				]
			} ]
		});

		function xiaoShouLouDou() {

			var viewWindow = new Ext.Window(
					{
						layout : 'fit',
						id : 'viewWindow',
						width : 800,
						height : 400,
						draggable : true,// 是否可以拖动
						closable : true,// 是否可关闭
						modal : true,
						closeAction : 'close',
						titleCollapse : true,
						buttonAlign : 'center',
						border : false,
						animCollapse : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [ {
							html : ' <div style="width:100%;"><div style="position:absolute; left:0px; top:0px; overflow:auto" id=\'viewport_center\'><iframe id="content" name="content2" style="width:100%; height:180%; "  scrolling="no" frameborder="yes" src="'
									+ basepath
									+ '/contents/pages/demo/chanceStage.html"  scrolling="auto"> </iframe></div><div style="position:absolute; left:0px; top:260px; " id=\'viewport_center4\'><iframe id="content4" name="content2" style="width:100%;height:130%;" frameborder="no" src="'
									+ basepath
									+ '/contents/pages/demo/chanceStage.jsp"  scrolling="no"> </iframe></div></div>'
						}

						]
					});
			viewWindow.title = '销售漏斗';
			viewWindow.show();

		}

	});