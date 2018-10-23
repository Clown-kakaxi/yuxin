Ext
		.onReady(function() {
			Ext.QuickTips.init(); 
			var rollTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=ROLL_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

			var rollKindStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=ROLL_KIND'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])

			var searchPanel = new Ext.form.FormPanel({
				title : "名单查询",
				labelWidth : 100,
				labelAlign : 'right',
				height : 100,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'ROLL_NAME',
							fieldLabel : '名单名称',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : rollTypeStore,
							xtype : 'combo', 
							resizable : true,
							name : 'rollType',
							hiddenName : 'ROLL_TYPE',
							fieldLabel : '名单类型',
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
						} ]
					}, {
						columnWidth : .4,
						layout : 'column',
						xtype : 'panel',

						items : [

						{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 60,
							items : {
								fieldLabel : '创建日期',
								xtype : 'datefield',
								name : 'createDateS',
								format : 'Y-m-d',
								editable : false,
								anchor : '100%'
							}
						},

						{
							columnWidth : .5,
							layout : 'form',
							labelStyle : 'text-align:center',
							labelAlign : 'right',
							labelSeparator : '',
							labelWidth : 30,
							items : {
								xtype : 'datefield',
								fieldLabel : '至',
								name : 'createDateE',
								format : 'Y-m-d',
								editable : false,
								anchor : '90%'
							}
						}

						]
					} ]
				} ],
				buttonAlign : 'center',
				buttons : [
						{
							text : '查询',
							handler : function() {
								var conditionStr = searchPanel.getForm()
										.getValues(false);
								store.baseParams = {
									"condition" : Ext.encode(conditionStr)
								};
								store.load({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});

							}
						}, {
							text : '重置',
							handler : function() {
								searchPanel.getForm().reset();
							}
						} ]

			});

			var sm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var columns = new Ext.grid.ColumnModel([ rownum, sm, {
				header : '名单名称',
				dataIndex : 'rollName',
				sortable : true,
				width : 120
			}, {
				header : '名单有效期',
				dataIndex : 'rollExpectDate',
				sortable : true,
				width : 120
			}, {
				header : '名单类型',
				dataIndex : 'ROLL_TYPE_ORA',
				sortable : true,
				width : 150
			}, 
//			{
//				header : '名单分类',
//				dataIndex : 'ROLL_KIND_ORA',
//				sortable : true,
//				width : 120
//			}, 
			{
				header : '名单说明',
				dataIndex : 'rollDescribe',
				sortable : true,
				width : 200
			}, {
				header : '创建人',
				dataIndex : 'createUserName',
				sortable : true,
				width : 120
			}, {
				header : '创建日期',
				dataIndex : 'createDate',
				sortable : true,
				width : 120
			}, {
				header : '更新人',
				dataIndex : 'updateUserName',
				width : 120
			}, {
				header : '更新日期',
				dataIndex : 'updateDate',
				sortable : true,
				width : 120
			} ]);

			var record = Ext.data.Record.create([ {
				name : 'rollId',
				mapping : 'ROLL_ID'
			}, {
				name : 'rollKind',
				mapping : 'ROLL_KIND'
			}, {
				name : 'rollType',
				mapping : 'ROLL_TYPE'
			}, {
				name : 'rollDescribe',
				mapping : 'ROLL_DESC'
			}, {
				name : 'rollExpectDate',
				mapping : 'ROLL_EXP_DATE'
			}, {
				name : 'rollName',
				mapping : 'ROLL_NAME'
			}, {
				name : 'updateUser',
				mapping : 'UPDATE_USER'
			}, {
				name : 'updateUserName',
				mapping : 'UPDATE_USER_NAME'
			}, {
				name : 'updateDate',
				mapping : 'UPDATE_DATE'
			}, {
				name : 'createUser',
				mapping : 'UPDATE_USER'
			}, {
				name : 'createUserName',
				mapping : 'CREATE_USER_NAME'
			}, {
				name : 'createDate',
				mapping : 'CREATE_DATE'
			}, {
				name : 'ROLL_KIND_ORA'
			}, {
				name : 'ROLL_TYPE_ORA'
			} ]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/rollQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ROLL_ID',
					messageProperty : 'message',
					root : 'json.data',
					totalProperty : 'json.count'
				}, record)
			});

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
							[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				resizable : true,
				width : 85
			});
			
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

			// 默认加载数据
			store.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
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
						title : "名单列表",
						frame : true,
						cm : columns,
						sm : sm,
						stripeRows : true,
						tbar : [
								{
									text : '新增',
									handler : function() {
										addInit();
									}
								},
								'-',
								{
									text : '修改',
									handler : function() {

										var selectLength = listPanel
										.getSelectionModel()
										.getSelections().length;

								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];
								
								if (selectLength != 1) {
									Ext.Msg.alert('提示','请选择一条记录!');
								}else {
											editBaseRollForm.getForm()
													.loadRecord(selectRe);
											document
													.getElementById('rollIdStr').value = selectRe.data.rollId;
											editInit();
										}
									}

								},
								'-',
								{
									text : '删除',
									handler : function() {
										var selectLength = listPanel
												.getSelectionModel()
												.getSelections().length;

										if (selectLength < 1) {
											Ext.Msg.alert('提示','请选择需要删除的记录!');
										}

										else {
											Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
												if(buttonId.toLowerCase() == "no"){
														return;
													}  
												var selectRe;
												var tempId;
												var idStr = '';
												for ( var i = 0; i < selectLength; i++) {
													selectRe = listPanel
															.getSelectionModel()
															.getSelections()[i];
													tempId = selectRe.data.rollId;
													idStr += tempId;
													if (i != selectLength - 1)
														idStr += ',';
												}
												Ext.Ajax
														.request({
															url : basepath
																	+ '/roll-customer!batchDestroy.json?idStr='
																	+ idStr,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert('提示', '操作成功');
																store.reload();
															},
															failure : function(response) {
																Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
																store.reload();
															}
														});

											})
											;
										}
									}
								},
								'-',
								{
									text : '名单关联客户维护',
									handler : function() {
										var selectLength = listPanel
										.getSelectionModel()
										.getSelections().length;

								var selectRe = listPanel.getSelectionModel()
										.getSelections()[0];
								
								if (selectLength != 1) {
									Ext.Msg.alert('提示','请选择一条记录!');
								} else {
											document
								.getElementById('rollIdStr').value = selectRe.data.rollId;
											editBaseRollForm.getForm()
													.loadRecord(selectRe);
											custStore
													.reload({
														params : {
															rollId : document
																	.getElementById('rollIdStr').value,
															start : 0,
															limit : parseInt(cuspagesize_combo.getValue())
														}
													});
											custOperate();
										}

									}
								} ],
						store : store,
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
			var addRollForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 200,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'rollName',
							xtype : 'textfield',
							fieldLabel : '*名单名称',
							defaultType : 'textfield',
							allowBlank : false,
							maxLength:100,
							blankText : '名单名称不能为空 ',
							width : '100',
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'rollExpectDate',
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '名单有效期',
							width : 100,
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : rollTypeStore,
							xtype : 'combo', resizable : true,
							resizable : true,
							name : 'rollType',
							hiddenName : 'rollType',
							fieldLabel : '名单类型',
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
						} ]
					}
//					, {
//						columnWidth : .5,
//						layout : 'form',
//						items : [ {
//							store : rollKindStore,
//							xtype : 'combo', resizable : true,
//							name : 'rollKind',
//							hiddenName : 'rollKind',
//							fieldLabel : '名单分类',
//							valueField : 'key',
//							displayField : 'value',
//							mode : 'local',
//							typeAhead : true,
//							forceSelection : true,
//							triggerAction : 'all',
//							emptyText : '请选择',
//							selectOnFocus : true,
//							width : '100',
//							anchor : '90%'
//						} ]
//					} 
					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						maxLength:300,
						fieldLabel : '名单说明',
						name : 'rollDescribe',
						anchor : '90%'
					} ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!addRollForm.getForm().isValid())
							{ 
								Ext.Msg.alert('提示', '输入不合法，请重新输入');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/roll-customer.json',
								method : 'POST',
								form : addRollForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									store.reload();
								}
							})
							addRollWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							addRollWindow.hide();
						}
					} ]
				}

				]

			});

			// 修改基本信息展示的from
			var editBaseRollForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 320,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'rollName',
							xtype : 'textfield',
							defaultType : 'textfield',
							fieldLabel : '*名单名称',
							allowBlank : false,
							blankText : '名单名称不能为空',
							maxLength:100,
							width : '100',
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'rollExpectDate',
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '名单有效期',
							width : 100,
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : rollTypeStore,
							xtype : 'combo', resizable : true,
							name : 'rollType',
							hiddenName : 'rollType',
							fieldLabel : '名单类型',
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
						} ]
					}
//					, {
//						columnWidth : .5,
//						layout : 'form',
//						items : [ {
//							store : rollKindStore,
//							xtype : 'combo', resizable : true,
//							name : 'rollKind',
//							hiddenName : 'rollKind',
//							fieldLabel : '名单分类',
//							valueField : 'key',
//							displayField : 'value',
//							mode : 'local',
//							typeAhead : true,
//							forceSelection : true,
//							triggerAction : 'all',
//							emptyText : '请选择',
//							selectOnFocus : true,
//							width : '100',
//							anchor : '90%'
//						} ]
//					}
					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '名单说明',
						maxLength:300,
						name : 'rollDescribe',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						fieldLabel : '创建人',
						name : 'createUser',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						fieldLabel : '创建日期',
						name : 'createDate',
						format : 'Y-m-d',
						editable : false,
						anchor : '90%'
					}, {
						xtype : 'hidden',
						fieldLabel : '更新人',
						name : 'updateUser',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						fieldLabel : '更新日期',
						name : 'updateUser',
						format : 'Y-m-d',
						editable : false,
						anchor : '90%'
					}, {
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'rollId',
						anchor : '90%'
					} ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!editBaseRollForm.getForm().isValid())
							{ 
								Ext.Msg.alert('提示', '输入不合法，请重新输入');
								return false;
							}
							Ext.Ajax.request({

								url : basepath + '/roll-customer.json',
								method : 'POST',
								form : editBaseRollForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									store.reload();
								}
							})
							editRollWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							editRollWindow.hide();
						}
					} ]
				}

				]

			});


			// 定义新增窗口
			var addRollWindow = new Ext.Window({
				title : '名单新增',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 300,
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
				items : [ addRollForm ]
			});

			// 定义修改窗口
			var editRollWindow = new Ext.Window({
				title : '名单修改',
				plain : true,
				layout : 'fit',
				width : 900,
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
				items : [ editBaseRollForm ]
			});

			// 定义名单关联客户维护页面
			var custDefendWindow=new Ext.Window(
					{
						   layout : 'fit',
							width:1000,
							height :420,
							closable : true,
							resizable : false,
							collapsible : false,
							draggable : true,
							maximizable:true,
							closeAction : 'hide',
							title : '名单关联客户维护',
							buttonAlign:'center',
							modal : true, // 模态窗口 
							//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
							animCollapse : false,
							border : false,
							closable : true,
							animateTarget : Ext.getBody(),
							constrain : true,
							items : [
						         {
								layout : 'column',
								border : false,
								items : [
								        {
									columnWidth : .45,
									layout : 'form',
									border : false
									,
									items : [addCustomer,cusGrid]}, {
										columnWidth : .55,
										layout : 'form',
										border : false,
										items : [cusGroupMemeberGrid]
									}
									]
							}
							],
							
							buttonAlign:'center',
							
							buttons:[{
	  				  			text: '关闭',
	   				  			handler:function(){
	   				  			custDefendWindow.hide();
	    						}
				 				}]	
			});

			// 展示新增窗口
			function addInit() {
				addRollWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editRollWindow.show();
			}

			//展示名单关联客户窗口
			function custOperate() {
				custDefendWindow.show();
			}

			var view = new Ext.Viewport({

				layout : 'fit',
				items : [ {
					layout : 'border',
					items : [ searchPanel, listPanel ]
				} ]
			});
			
			addRollForm.getForm().clearInvalid();
			
			editBaseRollForm.getForm().clearInvalid();

		})