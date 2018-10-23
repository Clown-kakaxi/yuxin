Ext.onReady(function() {
			Ext.QuickTips.init(); 
			var channelTypeStore = new Ext.data.JsonStore({
				id : channelTypeStore,
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/channel-type.json'
				}),
				fields : [ 'channelTypeId', 'channelTypeName' ],
				reader : new Ext.data.JsonReader({
					totalProperty : 'list'
				}, [ {
					name : 'channelTypeId',
					mapping : 'channelTypeId'
				}, {
					name : 'channelTypeName',
					mapping : 'channelTypeName'
				} ])
			});

			var qForm = new Ext.form.FormPanel({
				title : "渠道查询dd",
				labelWidth : 90, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'center',
				region:'north',
				split:true,
				height : 100,
				items : [ {
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [ {
							name : 'CHANNEL_NAME',
							xtype : 'textfield',
							fieldLabel : '渠道名称',
							
							//width : '100',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [ {
							store : channelTypeStore,
							xtype : 'combo', resizable : true,
							fieldLabel : '渠道类型',
							name : 'CHANNEL_TYPE_ID',
							hiddenName : 'CHANNEL_TYPE_ID',
							valueField : 'channelTypeId',
							displayField : 'channelTypeName',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							//width : '100',
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
							labelWidth : 80,
							items : {
								fieldLabel : '渠道建立日期',
								xtype : 'datefield',
								format : 'Y-m-d',
								editable : false,
								name : 'createDateS',
								anchor : '100%'
							}
						},

						{
							columnWidth : .5,
							layout : 'form',
							labelStyle : 'text-align:center',
							labelAlign : 'right',
							labelSeparator : '',
							labelWidth : 40,
							items : {
								xtype : 'datefield',
								 resizable : true,
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
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = qForm.getForm().getValues(false);
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

				},{
					text : '重置',
					handler : function() {
	                	qForm.getForm().reset();
					}

				} ]
			});
			// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record = Ext.data.Record.create([ {
				name : 'channelId',
				mapping : 'CHANNEL_ID'
			}, {
				name : 'accessCondition',
				mapping : 'ACCESS_CONDITION'
			}, {
				name : 'channelFeature',
				mapping : 'CHANNEL_FEATURE'
			}, {
				name : 'channelPolicy',
				mapping : 'CHANNEL_POLICY'
			}, {
				name : 'channelCode',
				mapping : 'CHANNEL_CODE'
			}, {
				name : 'channelName',
				mapping : 'CHANNEL_NAME'
			}, {
				name : 'createDate',
				mapping : 'CREATE_DATE'
			}, {
				name : 'createOrganization',
				mapping : 'CREATE_ORG'
			}, {
				name : 'createOrganizationName',
				mapping : 'UNITNAME'
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
				name : 'guarantee',
				mapping : 'GUARANTEE'
			}, {
				name : 'remark',
				mapping : 'REMARK'
			}, {
				name : 'channelTypeIdOra',
				mapping : 'CHANNEL_TYPE_ID_ORA'
			}, {
				name : 'createUserName',
				mapping : 'USERNAME'
			}, {
				name : 'channelTypeName',
				mapping : 'CHANNEL_TYPE_NAME'
			}, {
				name : 'channelTypeId',
				mapping : 'CHANNEL_TYPE_ID'
			}]);

			// 定义列模型

			var cm = new Ext.grid.ColumnModel([ rownum, sm, {
				header : '渠道名称',
				dataIndex : 'channelName',
				sortable : true,
				width : 150
			}, {
				header : '渠道类型',
				dataIndex : 'channelTypeName',
				sortable : true,
				width : 150
			}, {
				header : '渠道建立人',
				dataIndex : 'createUserName',
				sortable : true,
				width : 150
			}, {
				header : '渠道建立机构',
				dataIndex : 'createOrganizationName',
				sortable : true,
				width : 150
			}, {
				header : '渠道建立日期',
				dataIndex : 'createDate',
				sortable : true,
				width : 150
			} ]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/channelInfoQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'CHANNEL_ID',
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

			// 默认加载数据
			store.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
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
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			// 表格工具栏
			var tbar = new Ext.Toolbar(
					{
						items : [
								{
									text : '渠道建立',
									handler : function() {
										addChannelInit();
									}
								},
								'-',
								{
									text : '渠道修改',
									handler : function() {

										var selectLength = grid
												.getSelectionModel()
												.getSelections().length;

										var selectRe = grid.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示','请选择一条记录!');
										} else {
											editChannelForm.getForm()
													.loadRecord(selectRe);
											editChannelInit();
										}
									}
								},
								'-',
								{
									text : '渠道删除',
									handler : function() {
										var selectLength = grid
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
													selectRe = grid
															.getSelectionModel()
															.getSelections()[i];
													tempId = selectRe.data.channelId;
													idStr += tempId;
													if (i != selectLength - 1)
														idStr += ',';
												}
												Ext.Ajax
														.request({
															url : basepath
																	+ '/channel-info/'
																	+ tempId
																	+ '/batchDestroy.json?idStr='
																	+ idStr,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert('提示', '操作成功');
																store.reload();
															},
															failure : function(response) {
																var resultArray = Ext.util.JSON.decode(response.status);
																if(resultArray == 403) {
																window.location = basepath + '/403.jsp';
																} else {
																Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
																}
																//Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
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
									text : '渠道类型维护',
									handler : function() {
										channelTypeInit();
									}
								},
								'-',
								{
									text : '渠道详情',
									handler : function() {

										var selectLength = grid
												.getSelectionModel()
												.getSelections().length;

										var selectRe = grid.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示','请选择一条记录!');
										} else {
											detailChannelForm.getForm()
													.loadRecord(selectRe);
											detailChannelWindow.show();
										}
									}
								} ]
					});

			// 新增窗口展示的from
			var addChannelForm = new Ext.form.FormPanel({
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
						defaultType : 'textfield',
						items : [ {
							name : 'channelName',
							xtype : 'textfield',
							allowBlank : false,
							blankText : '渠道名称不能为空',
							maxLength:200,
							fieldLabel : '*渠道名称',
							width : '100',
							anchor : '90%'
						} ]

					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : channelTypeStore,
							xtype : 'combo', 
							fieldLabel : '*渠道类型',
							allowBlank : false,
							blankText : '渠道类型不能为空',
							name : 'channelTypeId',
							valueField : 'channelTypeId',
							hiddenName : 'channelTypeId',
							displayField : 'channelTypeName',
							mode : 'local',
							typeAhead : true,
							resizable : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]

					} ]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '准入条件',
						maxLength:400,
						name : 'accessCondition',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '渠道特点',
						name : 'channelFeature',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '渠道政策',
						name : 'channelPolicy',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '担保',
						name : 'guarantee',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '备注',
						name : 'remark',
						anchor : '90%'
					} ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!addChannelForm.getForm().isValid())
								{ 
									Ext.Msg.alert('提示', '输入不合法，请重新输入');
									return false;
								}
							Ext.Ajax.request({
								url : basepath + '/channel-info.json',
								method : 'POST',
								form : addChannelForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
									window.location = basepath + '/403.jsp';
									} else {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
									//Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									store.reload();
								}
							});
							addChannelWindow.hide();
								}

					}, {
						text : '取  消',
						handler : function() {
							addChannelWindow.hide();
						}
					} ]

				} ]
			});

			// 修改窗口展示的from
			var editChannelForm = new Ext.form.FormPanel({
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
						defaultType : 'textfield',
						items : [ {
							name : 'channelName',
							xtype : 'textfield',
							allowBlank : false,
							blankText : '渠道名称不能为空',
							maxLength:200,
							fieldLabel : '*渠道名称',
							width : '100',
							anchor : '90%'
						} ]

					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : channelTypeStore,
							xtype : 'combo', resizable : true,
							fieldLabel : '*渠道类型',
							allowBlank : false,
							blankText : '渠道类型不能为空',
							name : 'channelTypeId',
							hiddenName : 'channelTypeId',
							valueField : 'channelTypeId',
							hiddenName : 'channelTypeId',
							displayField : 'channelTypeName',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]

					} ]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '准入条件',
						name : 'accessCondition',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '渠道特点',
						name : 'channelFeature',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '渠道政策',
						name : 'channelPolicy',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '担保',
						name : 'guarantee',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						maxLength:400,
						fieldLabel : '备注',
						name : 'remark',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						width : 200,
						fieldLabel : '创建人',
						name : 'createUser',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						width : 200,
						fieldLabel : '创建日期',
						name : 'createDate',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						width : 200,
						fieldLabel : '创建机构',
						name : 'createOrganization',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						width : 200,
						fieldLabel : '渠道ID',
						name : 'channelId',
						anchor : '90%'
					} ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!editChannelForm.getForm().isValid())
								{ 
									Ext.Msg.alert('提示', '输入不合法，请重新输入');
									return false;
								}

							Ext.Ajax.request({
								url : basepath + '/channel-info.json',
								method : 'POST',
								form : editChannelForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
									window.location = basepath + '/403.jsp';
									} else {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
									//Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									store.reload();
								}
							});
							editChannelWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							editChannelWindow.hide();
						}
					} ]

				} ]
			});

			// 详情展示的from
			var detailChannelForm = new Ext.form.FormPanel({
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
							name : 'channelName',
							xtype : 'textfield',
							fieldLabel : '渠道名称',
							width : '100',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '渠道建立人',
							name : 'createUserName',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '渠道建立机构',
							name : 'createOrganizationName',
							anchor : '90%'
						} ]

					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'channelTypeName',
							xtype : 'textfield',
							fieldLabel : '渠道类型',
							width : '100',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '渠道建立日期',
							name : 'createDate',
							anchor : '90%'
						} ]

					} ]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '准入条件',
						name : 'accessCondition',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '渠道特点',
						name : 'channelFeature',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '渠道政策',
						name : 'channelPolicy',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '担保',
						name : 'guarantee',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						width : 200,
						fieldLabel : '备注',
						name : 'remark',
						anchor : '90%'
					},{
						xtype : 'hidden',
						width : 200,
						fieldLabel : '渠道ID',
						name : 'channelId',
						anchor : '90%'
					} ]

				} ],

					buttons : [ {
						text : '关  闭',
						handler : function() {
							detailChannelWindow.hide();
						}
					} ]
			});

			// 定义新增窗口
			var addChannelWindow = new Ext.Window({
				title : '渠道建立',
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
				items : [ addChannelForm ]
			});

			// 定义修改窗口
			var editChannelWindow = new Ext.Window({
				title : '渠道修改',
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
				items : [ editChannelForm ]
			});
			
			// 定义详情窗口
			var detailChannelWindow = new Ext.Window({
				title : '渠道详情',
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
				items : [ detailChannelForm ]
			});

			// 定义类型维护的窗口
			var channelTypeWindow = new Ext.Window({
				title : '渠道类型维护',
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
				border : false,
				listeners:{
				'hide':function(component){
					channelTypeStore.reload();
				}	
				},
				items : [ typeGrid ]
				
			});

			function addChannelInit() {
				addChannelWindow.show();
			}
			;

			function editChannelInit() {
				editChannelWindow.show();
			}
			;

			function channelTypeInit() {
				typeStore.load({
					params : {
						start : 0,
						limit : parseInt(type_pagesize_combo.getValue())
					}
				});
				channelTypeWindow.show();
			}

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				title : '渠道列表',
				frame : true,
				autoScroll : true,
				region : 'center',
				store : store,
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

			// 布局模型
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ {
					layout:'border',
					items : [ qForm ,grid]
				}]
			});

		});