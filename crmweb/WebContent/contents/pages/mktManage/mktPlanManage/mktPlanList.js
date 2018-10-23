Ext.onReady(function() {
			
			var planStatStore = new Ext.data.Store({  
		    	restful:true,   
		    	autoLoad :true,
		    	proxy : new Ext.data.HttpProxy({
					url : basepath+'/lookup.json?name=PLAN_STATUS'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

			var searchPanel = new Ext.form.FormPanel({
				title : "营销计划查询",
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
				        name : 'PLAN_NAME',
				        fieldLabel : '营销计划名称',
				        anchor : '90%'
				    }]
					},{
					    columnWidth : .25,
						layout : 'form',
						items : [{
							store : planStatStore,
							xtype : 'combo', 
							resizable : true,
							name : 'MKT_PLAN_STAT',
							hiddenName : 'MKT_PLAN_STAT',
							fieldLabel : '营销计划状态',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							anchor : '90%'
						}]
					}, {
        				columnWidth : .25,
        				layout : 'form',
        				items : [{
        				    fieldLabel : '创建日期',
        				    xtype : 'datefield',
        				    name : 'createDateS',
        					id : 'createDateS',
        					format : 'Y-m-d',
        					editable : false,
        					anchor : '90%'
        				}]
        			},{
        				columnWidth : .25,
        				labelWidth : 20,
        				layout : 'form',
        				items : [{
        					xtype : 'datefield',
        					fieldLabel : '至',
        					id : 'createDateE',
        					name : 'createDateE',
        					format : 'Y-m-d',
        					editable : false,
        					anchor : '65%'
        				}]
				}/*,{
					layout : 'column',
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'HEADOFFICE',
							fieldLabel : '总行负责人',
							anchor : '90%'
						} ]
					},
					{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'ADMINISTEROFFICE',
							fieldLabel : '分行负责人',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'BRANCHOFFICE',
							fieldLabel : '管辖行负责人',
							anchor : '90%'
						} ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'SUBBRANCHOFFICE',
							fieldLabel : '支行负责人',
							anchor : '90%'
						} ]
					} ]
				}*/ ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
								var start = Ext.getCmp('createDateS').getValue();
								var end = Ext.getCmp('createDateE').getValue();
								if(start==''&&end!=''){
										Ext.Msg.alert('消息框','请先选择开始时间！');
										Ext.getCmp('createDateE').reset();
										return false;
								}else if(end!=''&&start>end){
										Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
										Ext.getCmp('createDateE').reset();
										return false;
								}						
						var conditionStr = searchPanel.getForm().getValues(
								false);
						store.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						store.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});

					}
				}, {
					text : '重置',
					handler : function() {
						searchPanel.getForm().reset();
					}
				}  ]

			});

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			var sm = new Ext.grid.CheckboxSelectionModel();
			var columns = new Ext.grid.ColumnModel([ rownum,sm,{
				header : '营销计划状态',
				width : 130,
				align : 'left',
				dataIndex : 'marketPlanStatementCont',
				sortable : true
			}, {
				header : '营销计划名称',
				width : 210,
				align : 'left',
				dataIndex : 'planName',
				sortable : true
			}, {
				header : '计划开始日期',
				width : 170,
				align : 'left',
				dataIndex : 'planStartDate',
				sortable : true
			}, {
				header : '预计结束日期',
				width : 170,
				align : 'left',
				dataIndex : 'planEndDate',
				sortable : true
			}, {
				header : '实际完成日期',
				width : 170,
				align : 'left',
				dataIndex : 'actualEndDate',
				sortable : true
			}, {
				header : '创建人',
				width : 150,
				align : 'left',
				dataIndex : 'UserName',
				sortable : true
			}, {
				header : '创建日期',
				width : 150,
				align : 'left',
				dataIndex : 'createDate',
				sortable : true
			},{
				header :'id',
				width : 150,
				align : 'left',
				dataIndex :'planId',
				sortable :true
			}/*, {
				header : '总行负责人',
				width : 150,
				align : 'left',
				dataIndex : 'headoffice',
				sortable : true
			}, {
				header : '分行负责人',
				width : 150,
				align : 'left',
				dataIndex : 'administeroffice',
				sortable : true
			}, {
				header : '管辖行负责人',
				width : 150,
				align : 'left',
				dataIndex : 'branchoffice',
				sortable : true
			}, {
				header : '支行负责人',
				width : 150,
				align : 'left',
				dataIndex : 'subbranchoffice',
				sortable : true
			}*/ ]);

			var record = Ext.data.Record.create([ {
				name : 'planId',
				mapping : 'PLAN_ID'
			}, {
				name : 'marketPlanStatement',
				mapping : 'MKT_PLAN_STAT'
			},{
				name : 'marketPlanStatement',
				mapping : 'MKT_PLAN_STAT'
			},{
				name : 'marketPlanStatementCont',
				mapping : 'MKT_PLAN_STAT_ORA'
			}, {
				name : 'marketPlanAim',
				mapping : 'MKT_PLAN_AIM'
			}, {
				name : 'marketPlanCharge',
				mapping : 'MKT_PLAN_CHARGE'
			}, {
				name : 'marketPlanContent',
				mapping : 'MKT_PLAN_CONT'
			}, {
				name : 'planCustomerDescribe',
				mapping : 'PLAN_CUST_DESC'
			}, {
				name : 'planProductDescribe',
				mapping : 'PLAN_PROD_DESC'
			}, {
				name : 'updateUser',
				mapping : 'UPDATEUSER'
			}, {
				name : 'updateDate',
				mapping : 'UPDATE_DATE'
			}, {
				name : 'planName',
				mapping : 'PLAN_NAME'
			}, {
				name : 'planStartDate',
				mapping : 'PLAN_START_DATE'
			}, {
				name : 'planEndDate',
				mapping : 'PLAN_END_DATE'
			}, {
				name : 'actualEndDate',
				mapping : 'ACTUAL_END_DATE'
			},{
				name : 'headoffice',
				mapping : 'HEADOFFICE'
			},{
				name : 'administeroffice',
				mapping : 'ADMINISTEROFFICE'
			},{
				name : 'branchoffice',
				mapping : 'BRANCHOFFICE'
			},{
				name : 'subbranchoffice',
				mapping : 'SUBBRANCHOFFICE'
			},{
				name : 'UserName',
 				mapping : 'USERNAME'
			}, {
				name : 'createDate',
				mapping : 'CREATE_DATE'
			}, {
				name : 'createUser',
				mapping : 'CREATE_USER'
			}, {
				name : 'headoffice',
				mapping : 'HEADOFFICE'
			}, {
				name : 'administeroffice',
				mapping : 'ADMINISTEROFFICE'
			}, {
				name : 'branchoffice',
				mapping : 'BRANCHOFFICE'
			}, {
				name : 'subbranchoffice',
				mapping : 'SUBBRANCHOFFICE'
			} ]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/planQuery.json',
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'PLAN_ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record)
			});

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								[ 100, '100条/页' ], [ 250, '250条/页' ],
								[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				resizable : true,
				width : 85
			});

			// 默认加载数据
//			store.load({
//				params : {
//					start : 0,
//					limit : parseInt(pagesize_combo.getValue())
//				}
//			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var listPanel = new Ext.grid.GridPanel(
					{
						title : "营销计划列表",
						store : store,
						frame : true,
						id : 'listPanel',
						cm : columns,
						sm : sm,
						stripeRows : true,
						tbar : [
								{
									text : '新增',
									iconCls:'addIconCss',
									handler : function() {
										addInit();
									}
								},
								'-',
								{
									text : '修改',
									iconCls:'editIconCss',
									handler : function() {

										var selectLength = listPanel
												.getSelectionModel()
												.getSelections().length;

										var selectRe = listPanel
												.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示','请选择一条记录!');
										} else {
											var marketPlanStatement = selectRe.data.marketPlanStatement;
											if (marketPlanStatement != '1') {
												Ext.Msg.alert('提示','只能修改暂存的营销计划!');
											} else {
												editBasePlanForm.getForm()
														.loadRecord(selectRe);
												document.getElementById('planIdStr').value = selectRe.data.planId;
												editInit();
											}
										}
									}

								},
								'-',
								{
									text : '删除',
									iconCls:'deleteIconCss',
									handler : function() {
										 var selectLength = listPanel.getSelectionModel()
											.getSelections().length;
										
//										var selectRe = listPanel
//												.getSelectionModel()
//												.getSelections()[0];
										//var marketPlanStatement = selectRe.data.marketPlanStatement;
//										if (selectRe == null
//												|| selectRe == 'undefined') {
//											Ext.Msg.alert('提示','请选择一条记录!');
										 var selectRe;
										 var tempId;
										 var idStr = '';
										 var marketPlanStatement;
										if(selectLength < 1){
											Ext.Msg.alert('提示','请选择需要删除的记录!');
										} else {
											for(var i = 0; i<selectLength;i++)
											{
												selectRe = listPanel.getSelectionModel()
												.getSelections()[i];
												marketPlanStatement = selectRe.data.marketPlanStatement;
												if(marketPlanStatement != '1'){
													Ext.Msg.alert('提示','只能删除暂存的营销计划!');
													return;
												}
												tempId = selectRe.data.planId;
												idStr += tempId;
												if( i != selectLength-1)
													idStr += ',';
											}
												Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
													if(buttonId.toLowerCase() == "no"){
      												return;
													} 
													Ext.Ajax.request({
														url : basepath
														+ '/market-plan!batchDestroy.json?idStr='+ idStr,
																//method : 'DELETE',
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success : function() {
																	Ext.Msg.alert('提示', '操作成功');
																	store.reload();
																},
																failure : function(response) {
																	var resultArray = Ext.util.JSON.decode(response.status);
																	if(resultArray == 403) {
																           Ext.Msg.alert('提示', response.responseText);
																  } else {

																	Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
																	store.reload();
																  }
																}
															});

												})
												;
											
										}
									}
								},
								'-',
								{
									text : '执行',
									iconCls:'maintainIconCss',
									handler : function() {
										var selectRe = listPanel
												.getSelectionModel()
												.getSelections()[0];
										if (selectRe == null
												|| selectRe == 'undefined') {
											Ext.Msg.alert('提示','请选择一条记录!');
										} else {
											var marketPlanStatement = selectRe.data.marketPlanStatement;
											if (marketPlanStatement != '1') {
												Ext.Msg.alert('提示', '该计划已经在执行中或是已关闭状态');
											} else {
												Ext.MessageBox.confirm('提示','执行后将为每个客户生成营销活动，确定执行吗?',function(buttonId){
													if(buttonId.toLowerCase() == "no"){
	      												return;
	      											} 
													Ext.Ajax
															.request({
																url : basepath
																		+ '/market-plan/'
																		+ selectRe.get('planId')
																		+ '/planExecute.json',
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success : function() {
																	Ext.Msg.alert('提示', '操作成功');
																	store.reload();
																},
																failure : function(response) {
																	var resultArray = Ext.util.JSON.decode(response.status);
																	 if(resultArray == 403) {
																           Ext.Msg.alert('提示', response.responseText);
																  } else {

																	Ext.Msg.alert('提示', '操作失败:请查看该计划中是否有客户');
																	store.reload();
																  }
																}
															});
												})
												;
											}
										}
									}
								},
								'-',
								{
									text : '关闭',
									iconCls:'closeIconCss',
									handler : function() {
										closePlan();
									}
								},
								'-',
								{
									text : '详情',
									iconCls:'detailIconCss',
									handler : function() {

										var selectLength = listPanel
												.getSelectionModel()
												.getSelections().length;

										var selectRe = listPanel
												.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示','请选择一条记录!');
										} else {
											planDetailForm.getForm()
													.loadRecord(selectRe);
											document
											.getElementById('planIdStr').value = selectRe.data.planId;
											planDetailWindow.show();
										}
									}
								} ],
						region : 'center',
						frame : true,
						bbar : bbar,// 分页工具栏
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});

			// 新增窗口展示的from
			var addPlanForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 500,
				frame : true,
				labelAlign : 'right',
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'planName',
							xtype : 'textfield',
							fieldLabel : '*营销计划名称',
							width : '100',
							anchor : '90%',
							allowBlank : false
						}, {
							name : 'planStartDate',
							xtype : 'datefield',
							fieldLabel : '计划开始日期',
							format : 'Y-m-d',
							editable : false,
							allowBlank : false,
							blankText :'此项为必填项，请检查！',
							width : 100,
							anchor : '90%'

						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'marketPlanCharge',
							id:'marketPlanCharge',
							xtype : 'numberfield',
							fieldLabel : '费用预算',
							value :0,
							allowBlank : false,
							blankText :'此项为必填项，请检查！',
							width : 200,
							anchor : '90%'
						}, {
							xtype : 'datefield',
							labelStyle : {
								width : '120px'
							},
							width : 200,
							fieldLabel : '预计结束日期',
							format : 'Y-m-d',
							editable : false,
							name : 'planEndDate',
							allowBlank : false,
							blankText :'此项为必填项，请检查！',
							anchor : '90%'
						} ]
					}/*, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '总行负责人',
							name : 'headoffice',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '管辖行负责人',
							name : 'administeroffice',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '分行负责人',
							name : 'branchoffice',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '支行负责人',
							name : 'subbranchoffice',
							anchor : '90%'
						} ]
					}*/

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '营销计划目的',
						name : 'marketPlanAim',
						allowBlank : false,
						blankText :'此项为必填项，请检查！',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,

						fieldLabel : '营销计划内容',
						name : 'marketPlanContent',
						allowBlank : false,
						blankText :'此项为必填项，请检查！',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '涉及客户群描述 ',
						name : 'planCustomerDescribe',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,

						fieldLabel : '涉及产品描述',
						name : 'planProductDescribe',
						anchor : '90%'
					},
//					{
//						xtype : 'textarea',
//						labelStyle : {
//							width : '120px'
//						},
//						width : 200,
//						fieldLabel : '总行负责人',
//						name : 'planProductDescribe',
//						anchor : '90%'
//					},  
					{
						// 隐藏的列 默认为暂存
						xtype : 'hidden',
						width : 200,
						fieldLabel : '营销计划状态',
						name : 'marketPlanStatement',
						value : '1',
						anchor : '90%'
					} ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!addPlanForm.getForm().isValid()) { 
								Ext.Msg.alert('提示框','输入数据不合法，请检查！');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/market-plan.json?a=1',
								method : 'POST',
								form : addPlanForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									 if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
									 }else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								}
								}
							});
							addPlanWindow.hide();
							addPlanForm.getForm().reset();
						}

					}, {
						text : '取  消',
						handler : function() {
							addPlanWindow.hide();
						}
					} ]
				}

				]

			});
			
			

			// 详情展示的form
			var planDetailForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 408,
				frame : true,
				labelAlign : 'right',
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '营销计划ID',
							name : 'planId',
							anchor : '90%'
						}, {
							name : 'planName',
							xtype : 'textfield',
							fieldLabel : '营销计划名称',
							labelStyle : {
								width : '120px'
							},
							width : '100',
							anchor : '90%'
						}, {
							name : 'planStartDate',
							xtype : 'textfield',
							fieldLabel : '计划开始日期',
							width : 100,
							anchor : '90%'

						}, {
							name : 'actualEndDate',
							xtype : 'textfield',
							fieldLabel : '实际完成日期',
							width : 100,
							anchor : '90%'

						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '创建日期',
							name : 'createDate',
							anchor : '90%'
						},{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '最近更新人',
							name : 'updateUser',
							anchor : '90%'
						}/*,{
							columnWidth : .5,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 200,
								fieldLabel : '总行负责人',
								name : 'headoffice',
								anchor : '90%'
							}, {
								xtype : 'textfield',
								width : 200,
								fieldLabel : '管辖行负责人',
								name : 'administeroffice',
								anchor : '90%'
							} ]
						}*/
//						, {
//							xtype : 'textfield',
//							width : 200,
//							fieldLabel : '最近更新日期',
//							name : 'updateDate',
//							anchor : '90%'
//						} 
						]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							store : planStatStore,
							xtype : 'combo', 
							resizable : true,
							name : 'marketPlanStatement',
							hiddenName : 'marketPlanStatement',
							fieldLabel : '营销计划状态',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							readOnly : true,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} , {
							xtype : 'textfield',
							fieldLabel : '费用预算',
							name : 'marketPlanCharge',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '预计结束日期',
							name : 'planEndDate',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '创建人',
							name : 'UserName',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '最近更新日期',
							name : 'updateDate',
							anchor : '90%'
						}/*, {
							columnWidth : .5,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 200,
								fieldLabel : '分行负责人',
								name : 'branchoffice',
								anchor : '90%'
							}, {
								xtype : 'textfield',
								width : 200,
								fieldLabel : '支行负责人',
								name : 'subbranchoffice',
								anchor : '90%'
							} ]
						}*/ ]
					}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '营销计划目的',
						name : 'marketPlanAim',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,

						fieldLabel : '营销计划内容',
						name : 'marketPlanContent',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '涉及客户群描述 ',
						name : 'planCustomerDescribe',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,

						fieldLabel : '涉及产品描述',
						name : 'planProductDescribe',
						anchor : '90%'
					} ],

					buttons : [ {
						text : '返回',
						handler : function() {
							planDetailWindow.hide();
						}
					} ]
				}

				]
			});

			// 修改基本信息展示的form
			var editBasePlanForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 480,
				frame : true,
				labelAlign : 'right',
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'planName',
							xtype : 'textfield',
							fieldLabel : '*营销计划名称',
							labelStyle : {
								width : '120px'
							},
							width : '100',
							anchor : '90%',
							allowBlank : false
						}, {
							name : 'planStartDate',
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '计划开始日期',
							allowBlank : false,
							blankText :'此项为必填项，请检查！',
							width : 100,
							anchor : '90%'

						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'numberfield',
							labelStyle : {
								width : '120px'
							},
							fieldLabel : '费用预算',
							name : 'marketPlanCharge',
							width : 200,
							allowBlank : false,
							blankText :'此项为必填项，请检查！',
							anchor : '90%'
						}, {
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							width : 200,
							fieldLabel : '预计结束日期',
							allowBlank : false,
							blankText :'此项为必填项，请检查！',
							name : 'planEndDate',
							anchor : '90%'
						} ]
					}/*, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '总行负责人',
							name : 'headoffice',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '管辖行负责人',
							name : 'administeroffice',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '分行负责人',
							name : 'branchoffice',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '支行负责人',
							name : 'subbranchoffice',
							anchor : '90%'
						} ]
					}*/

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '营销计划目的',
						name : 'marketPlanAim',
						allowBlank : false,
						blankText :'此项为必填项，请检查！',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,

						fieldLabel : '营销计划内容',
						name : 'marketPlanContent',
						allowBlank : false,
						blankText :'此项为必填项，请检查！',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '涉及客户群描述 ',
						name : 'planCustomerDescribe',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,

						fieldLabel : '涉及产品描述',
						name : 'planProductDescribe',
						anchor : '90%'
					}, {
						// 隐藏的planId
						xtype : 'hidden',
						width : 200,
						fieldLabel : '营销计划ID',
						name : 'planId',
						anchor : '90%'
					}, {
						// 隐藏的营销计划状态
						xtype : 'hidden',
						width : 200,
						fieldLabel : '营销计划状态',
						name : 'marketPlanStatement',
						anchor : '90%'
					}, {
						// 隐藏的创建人
						xtype : 'hidden',
						width : 200,
						fieldLabel : '创建人',
						name : 'createUser',
						anchor : '90%'
					}, {
						// 隐藏的创建日期
						xtype : 'hidden',
						width : 200,
						fieldLabel : '创建日期',
						name : 'createDate',
						anchor : '90%'
					} ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!editBasePlanForm.getForm().isValid()) { 
								alert('请输入合法数据！');
								return false;
							}
							Ext.Ajax.request({

								url : basepath + '/market-plan.json?a=2',
								method : 'POST',
								form : editBasePlanForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
									} else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
									}
							});
							editPlanWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							editPlanWindow.hide();
						}
					} ]
				}

				]

			});

			// 定义修改窗口的tabPanel
			var tokenDelimiter = ':';
			var editTp = new Ext.TabPanel({
				id : 'editPlanTabs',
				activeTab : 0,
				tabPosition : 'bottom',
				items : [ {
					title : '基本信息',
					items : [ editBasePlanForm ]
				},{
					title : '客户明细',
					listeners : {
						'activate' : function() {
							custStore.load({
								params : {
									start : 0,
									planId : document.getElementById('planIdStr').value
								}
							});
							
						}
					},
					items : [ add1Customer,custListPanel ]
				}, {
					title : '产品明细',
					listeners : {
						'activate' : function() {
							prodStore.load({
								params : {
									planId : document.getElementById('planIdStr').value
								}
							});
						}
					},
					items : [ add1Product,prodListPanel ]
				} ]

			});
			
			// 新增窗口展示的from  add by huangyan
			var addCustForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 300,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : 'center',
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : '100',
							fieldLabel : '客户号',
							name : 'customerId',
							anchor : '90%'
						},{
							name : 'customerName',
							xtype : 'textfield',
							fieldLabel : '客户名称 ',
							width : '200',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : '100',
							fieldLabel : '执行团队',
							name : 'executor',
							anchor : '90%'
						}, {
							// 隐藏的planId
							xtype : 'hidden',
							width : 200,
							fieldLabel : '营销计划ID',
							id : 'currPlanId',
							name : 'planId',
							anchor : '90%'
						} ]
					}

					]

				} ],

				buttons : [

				{

					text : '保存',
					handler : function() {
						document.getElementById('currPlanId').value = document.getElementById('planIdStr').value;
						Ext.Ajax.request({

							url : basepath+'/plan-customer.json',
							method : 'POST',
							form : addCustForm.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								custStore.reload();
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.status);
							       if(resultArray == 403) {
							           Ext.Msg.alert('提示', response.responseText);
							  } else{

								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							  }
							}
						});
						addCustWindow.hide();
					}
				}, {
					text : '取  消',
					handler : function() {
						addCustWindow.hide();
					}
				} ]

			});

			// 定义详情窗口的tabPanel
			var planDetailPanel = new Ext.TabPanel({
				activeTab : 0,
				tabPosition : 'bottom',
				items : [ {
					title : '基本信息',
					items : [ planDetailForm ]
				}, {
					title : '客户明细',
					listeners : {
						'activate' : function() {
							planCustStore.load({
								params : {
									planId : document.getElementById('planIdStr').value
								}
							});
							
						}
					},
					items : [ addPlan1Customer,planCustListPanel ]
				}, {
					title : '产品明细',
					listeners : {
						'activate' : function() {
							planProdStore.load({
								params : {
									planId : document.getElementById('planIdStr').value
								}
							});
						}
					},
					items : [ addDetailProduct,planProdListPanel ]
				} ]

			});

			// 修改窗口展示的from
			var editPlanPanel = new Ext.Panel({
				labelWidth : 150,
				height : 480,
				layout : 'fit',
				//autoScroll : true,
				buttonAlign : "center",
				items : [ editTp ]
			});

			// 定义新增窗口
			var addPlanWindow = new Ext.Window({
				title : '营销计划新增',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 450,
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
				items : [ addPlanForm ]
			});

			// 定义修改窗口
			var editPlanWindow = new Ext.Window({
				title : '营销计划修改',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 470,
				resizable : true,
				draggable : true,
				listeners:{
					'beforeshow':function(){
						editTp.setActiveTab(0);						
					}
					
				},
				closable : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				loadMask : true,
				maximizable : true,
				collapsible : true,
				titleCollapse : true,
				border : false,
				items : [ editPlanPanel ]
			});

			// 定义详情的窗口
			var planDetailWindow = new Ext.Window({
				title : '营销计划详情',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 470,
				resizable : true,
				draggable : true,
				listeners:{
					'beforeshow':function(){
						planDetailPanel.setActiveTab(0);						
					}
					
				},
				closable : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				loadMask : true,
				maximizable : true,
				collapsible : true,
				titleCollapse : true,
				border : false,
				items : [ planDetailPanel ]
			});

			// 展示新增窗口
			function addInit() {
				addPlanForm.getForm().reset();
				Ext.getCmp("marketPlanCharge").setValue("0");
				addPlanWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editPlanWindow.show();
			}

			// 关闭营销计划
			function closePlan() {
				// 得到选中记录的planId
				var selectRe = listPanel.getSelectionModel().getSelections()[0];
				if (selectRe == null || selectRe == 'undefined') {
					Ext.Msg.alert('提示','请选择一条记录!');
				} else {
					var marketPlanStatement = selectRe.data.marketPlanStatement;
					if (marketPlanStatement != '2') {
						Ext.Msg.alert('提示','只能关闭执行中的营销计划!');
					} else {
						Ext.MessageBox.confirm('提示','确定关闭吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
									return;
								} 
							Ext.Ajax.request({
								url : basepath + '/market-plan/'
										+ selectRe.data.planId
										+ '/closePlan.json',
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
									} else {
									Ext.Msg.alert('提示', '操作失败');
									}
								}
							});
						});
					}
				}
			}
			
			var view = new Ext.Viewport({

				layout : 'fit',
				items : [ {
					layout : 'border',
					items : [searchPanel,listPanel]
				} ]
			});

		});