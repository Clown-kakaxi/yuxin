Ext
		.onReady(function() {
			Ext.QuickTips.init();
			
			var productTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=PRODUCT_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

			var qForm = new Ext.form.FormPanel({
				title : "资讯信息查询",
				labelWidth : 90, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'center',
				region : 'north',
				split : true,
				height : 100,
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							labelStyle: 'text-align:right;',
							Width : '100',
							name : 'MESSAGE_TITLE',
							fieldLabel : '资讯名称',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : productTypeStore,
							xtype : 'combo',
							name : 'PRODUCT_TYPE',
							hiddenName : 'PRODUCT_TYPE',
							labelStyle: 'text-align:right;',
							fieldLabel : '资讯分类',
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
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							fieldLabel : '发布时间',
							xtype : 'datefield',
							labelStyle: 'text-align:right;',
							Width : '100',
							name : 'PUBLISH_DATES',
							format : 'Y-m-d',
							editable : false,
							anchor : '90%'
						} ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'datefield',
							fieldLabel : '至',
							labelStyle: 'text-align:right;',
							name : 'PUBLISH_DATEE',
							Width : '100',
							format : 'Y-m-d',
							editable : false,
							anchor : '90%'
						} ]
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

				}, {
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
				name : 'messageId',
				mapping : 'MESSAGE_ID'
			}, {
				name : 'messageType',
				mapping : 'MESSAGE_TYPE'
			}, {
				name : 'messageTitle',
				mapping : 'MESSAGE_TITLE'
			}, {
				name : 'messageSummary',
				mapping : 'MESSAGE_SUMMARY'
			}, {
				name : 'messageIntroduce',
				mapping : 'MESSAGE_INTRODUCE'
			}, {
				name : 'productType',
				mapping : 'PRODUCT_TYPE'
			}, {
				name : 'publishDate',
				mapping : 'PUBLISH_DATE'
			}, {
				name : 'publishUser',
				mapping : 'PUBLISH_USER'
			}, {
				name : 'publishOrg',
				mapping : 'PUBLISH_ORG'
			}, {
				name : 'PRODUCT_TYPE_ORA'
			} ]);

			// 定义列模型

			var cm = new Ext.grid.ColumnModel([ rownum, sm,{
				header : '资讯名称',
				width : 216,
				align : 'center',
				dataIndex : 'messageTitle',
				sortable : true
			}, {
				header : '资讯摘要说明',
				width : 220,
				align : 'center',
				dataIndex : 'messageSummary',
				sortable : true
			}, {
				header : '资讯分类',
				width : 220,
				align : 'center',
				dataIndex : 'PRODUCT_TYPE_ORA',
				sortable : true
			}, {
				header : '发布时间',
				width : 220,
				align : 'center',
				dataIndex : 'publishDate',
				sortable : true
			}]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/newsManageQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'MESSAGE_ID',
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
							[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				editable : false,
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
				bbar.pageSize = parseInt(pagesize_combo.getValue()), store
						.reload({
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
									text : '新增',
									handler : function() {
										addForm.getForm().reset();
										addInit();
									}
								},
								'-',
								{
									text : '修改',
									handler : function() {

										var selectLength = grid
												.getSelectionModel()
												.getSelections().length;

										var selectRe = grid.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示', '请选择一条记录!');
										} else {
											editForm.getForm()
													.loadRecord(selectRe);
											editInit();

										}
									}

								},
								'-',
								{
									text : '删除',
									handler : function() {
										var selectLength = grid
												.getSelectionModel()
												.getSelections().length;

										if (selectLength < 1) {
											Ext.Msg.alert('提示', '请选择需要删除的记录!');
										}

										else {
											Ext.MessageBox
													.confirm(
															'提示',
															'确定删除吗?',
															function(buttonId) {
																if (buttonId
																		.toLowerCase() == "no") {
																	return;
																}
																var selectRe;
																var tempId;
																var idStr = '';
																for ( var i = 0; i < selectLength; i++) {
																	selectRe = grid
																			.getSelectionModel()
																			.getSelections()[i];
																	tempId = selectRe.data.messageId;
																	idStr += tempId;
																	if (i != selectLength - 1)
																		idStr += ',';
																}
																Ext.Ajax
																		.request({
																			url : basepath
																					+ '/news-manage/'
																					+ tempId
																					+ '/batchDestroy.json?idStr='
																					+ idStr,
																			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																			success : function() {
																				Ext.Msg.alert(
																								'提示',
																								'操作成功!');
																				store.reload();
																			},
																			failure : function(
																					response) {
																				Ext.Msg
																						.alert(
																								'提示',
																								'操作失败!');
																			}
																		});

															});
										}
									}
								},
								'-',
								{
									text : '详情',
									handler : function() {
										var selectLength = grid
												.getSelectionModel()
												.getSelections().length;

										var selectRe = grid.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示', '请选择一条记录!');
										} else {
											DetailForm.getForm()
													.loadRecord(selectRe);
											detailInit();
										}
									}
								},
								'-',
								{
						            text:'附件信息',
						            handler:function()
						            {
						                var record = grid.getSelectionModel().getSelected(); 
						                if (!record) {
						                    Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
						                    return false;
						                }
						                var checkedNodes = grid.getSelectionModel().selections.items;
						                if(checkedNodes.length>1){
						                    Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
						                    return false;
						                }
						                var messageIdStr = record.get('messageId');
						                
						                uploadForm.relaId = messageIdStr;
						                uploadForm.modinfo = 'infomation';
						                var condi = {};
						                condi['relationInfo'] = messageIdStr;
						                condi['relationMod'] = 'infomation';
						                Ext.Ajax.request({
						                    url:basepath+'/queryanna.json',
						                    method : 'GET',
						                    params : {
						                        "condition":Ext.encode(condi)
						                    },
						                    failure : function(a,b,c){
						                        Ext.MessageBox.alert('查询异常', '查询失败！');
						                    },
						                    success : function(response){
						                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
						                        appendixStore.loadData(anaExeArray.json.data);
						                        appendixGridPanel.getView().refresh();
						                    }
						                });
						                appendixWindow.show();
						            }
						        } ]
					});

			// 新增窗口展示的from
			var addForm = new Ext.form.FormPanel({
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
							xtype : 'textfield',
							vtype : 'trim',
							Width : '100',
							name : 'messageTitle',
							fieldLabel : '*资讯名称',
							allowBlank : false,
							blankText : '资讯名称不能为空',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : productTypeStore,
							xtype : 'combo',
							name : 'productType',
							hiddenName : 'productType',
							fieldLabel : '*资讯分类',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							allowBlank : false,
							blankText : '资讯类型不能为空',
							width : '100',
							anchor : '90%'
						} ]
					} , {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'messageSummary',
							fieldLabel : '资讯摘要',
							anchor : '90%'
						} ]
					}]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '资讯介绍 ',
						name : 'messageIntroduce',
						anchor : '90%'
					} ],
					buttons : [ {
						text : '保  存',
						handler : function() {
							if (!addForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/news-manage.json',
								method : 'POST',
								form : addForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!');
								}
							})
							addWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							addWindow.hide();
						}
					} ]
				} ]
			});

			// 修改窗口展示的from
			var editForm = new Ext.form.FormPanel({
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
							xtype : 'textfield',
							vtype : 'trim',
							Width : '100',
							name : 'messageTitle',
							fieldLabel : '*资讯名称',
							allowBlank : false,
							blankText : '资讯名称不能为空',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : productTypeStore,
							xtype : 'combo',
							name : 'productType',
							hiddenName : 'productType',
							fieldLabel : '*资讯分类',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							allowBlank : false,
							blankText : '资讯类型不能为空',
							width : '100',
							anchor : '90%'
						} ]
					} , {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'messageSummary',
							fieldLabel : '资讯摘要',
							anchor : '90%'
						} ]
					}]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '资讯介绍 ',
						name : 'messageIntroduce',
						anchor : '90%'
					}, {
						// 隐藏的ID
						xtype : 'hidden',
						width : 200,
						fieldLabel : '序号',
						name : 'messageId',
						anchor : '90%'
					} ],
					buttons : [ {
						text : '保  存',
						handler : function() {
							if (!editForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/news-manage.json',
								method : 'POST',
								form : editForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!');
								}
							})
							editWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							editWindow.hide();
						}
					} ]
				} ]
			});

			// 详情展示的from
			var DetailForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 250,
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
							xtype : 'textfield',
							vtype : 'trim',
							Width : '100',
							name : 'messageTitle',
							fieldLabel : '资讯名称',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : productTypeStore,
							xtype : 'combo',
							name : 'productType',
							hiddenName : 'productType',
							fieldLabel : '新资讯分类',
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
					} , {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'messageSummary',
							fieldLabel : '新资讯摘要',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'publishDate',
							fieldLabel : '发布时间',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'publishUser',
							fieldLabel : '发布者',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'unitName',
							fieldLabel : '发布机构',
							anchor : '90%'
						} ]
					}]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '资讯介绍 ',
						name : 'messageIntroduce',
						anchor : '90%'
					} ],
					buttons : [ {
						text : '返  回',
						handler : function() {
							DetailWindow.hide();
						}
					} ]
				} ]
			});

			// 定义新增窗口
			var addWindow = new Ext.Window({
				title : '资讯新增',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 200,
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
				items : [ addForm ]
			});

			// 定义修改窗口
			var editWindow = new Ext.Window({
				title : '资讯修改',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 200,
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
				items : [ editForm ]
			});

			// 定义详情窗口
			var DetailWindow = new Ext.Window({
				title : '资讯详情',
				plain : true,
				layout : 'fit',
				width : 880,
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
				items : [ DetailForm ]
			});

			// 展示新增窗口
			function addInit() {
				addWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editWindow.show();
			}

			// 展示详情窗口
			function detailInit() {
				DetailWindow.show();
			}

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				title : '资讯信息列表',
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
					layout : 'border',
					items : [ qForm, grid ]
				} ]
			});

		});