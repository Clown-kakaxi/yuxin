Ext
		.onReady(function() {
			Ext.QuickTips.init(); 
			
			var lookupIdStore = new Ext.data.JsonStore({
				id : lookupIdStore,
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup-mapping.json'
				}),
				fields : [ 'name' ],
				reader : new Ext.data.JsonReader({
					totalProperty : 'list'
				}, [ {
					name : 'name',
					mapping : 'name'
					} ])
				});

			var qForm = new Ext.form.FormPanel({
				title : "字典属性查询",
				labelWidth : 90, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'center',
				region:'north',
				split:true,
				height : 100,
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .25,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'F_VALUE',
									fieldLabel : '属性值',
									anchor : '90%'
								} ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'F_CODE',
									fieldLabel : '属性编码',
									anchor : '90%'
								} ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [ {
									store : lookupIdStore,
									xtype : 'combo',
									resizable:true,
									fieldLabel : '字典种类ID',
									name : 'F_LOOKUP_ID',
									hiddenName : 'F_LOOKUP_ID',
									valueField : 'name',
									displayField : 'name',
									mode : 'local',
									typeAhead : true,
									forceSelection : true,
									emptyText : '请选择',
									triggerAction : 'all',
									selectOnFocus : true,
									width : '200',
									anchor : '90%'} ]
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
				name : 'ID',
				mapping : 'F_ID'
			}, {
				name : 'value',
				mapping : 'F_VALUE'
			},{
				name : 'code',
				mapping : 'F_CODE'
			},{
				name : 'comment',
				mapping : 'F_COMMENT'
			}, {
				name : 'lookup',
				mapping : 'F_LOOKUP_ID'
			} ]);

			// 定义列模型

			var cm = new Ext.grid.ColumnModel([ rownum, sm, {
				header : '属性ID',
				width : 130,
				dataIndex : 'ID',
				sortable : true
			},  {
				header : '属性值',
				width : 130,
				dataIndex : 'value',
				sortable : true
			}, {
				header : '属性编码',
				width : 130,
				dataIndex : 'code',
				sortable : true
			},  {
				header : '属性说明',
				width : 170,
				dataIndex : 'comment',
				sortable : true
			}, {
				header : '字典种类ID',
				width : 170,
				dataIndex : 'lookup',
				sortable : true
			}]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookupMappingItemQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'F_ID',
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
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								[ 100, '100条/页' ], [ 250, '250条/页' ],
								[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
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
										text : '新增',
										iconCls : 'addIconCss',
										handler : function() {
											addLookupMappingForm.getForm().reset();
											addInit();
										}
									},
									'-',
									{
										text : '修改',
										iconCls : 'editIconCss',
										handler : function() {

											var selectLength = grid
											.getSelectionModel()
											.getSelections().length;

											var selectRe = grid.getSelectionModel()
											.getSelections()[0];

											if (selectLength != 1) {
												Ext.Msg.alert('提示','请选择一条记录!');
											} else {
												editLookupMappingForm.getForm()
														.loadRecord(selectRe);
												editInit();

											}
										}

									},
									'-',
									{
										text : '删除',
										iconCls : 'deleteIconCss',
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
												tempId = selectRe.data.ID;
												idStr += tempId;
												if (i != selectLength - 1)
													idStr += ',';
											}
											Ext.Ajax
													.request({
														url : basepath
																+ '/lookupMapping-item/'
																+ tempId
																+ '/batchDestroy.json?idStr='
																+ idStr,
														waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
														success : function() {
															Ext.Msg.alert('提示', '操作成功!');
															store.reload();
														},
														failure : function(response) {
															Ext.Msg.alert('提示', '操作失败!' );
														}
													});

										})
										;
									}
								}
									},
									'-',
									{
										text : '详情',
										iconCls : 'detailIconCss',
										handler : function() {
											var selectLength = grid
											.getSelectionModel()
											.getSelections().length;

											var selectRe = grid.getSelectionModel()
											.getSelections()[0];


											if (selectLength != 1) {
												Ext.Msg.alert('提示','请选择一条记录!');
											} else {
												lookupMappingDetailForm.getForm()
														.loadRecord(selectRe);
												detailInit();
											}
										}
									}]
					});

			// 新增窗口展示的from
			var addLookupMappingForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 150,
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
							name : 'value',
							xtype : 'textfield',
							vtype : 'trim',
							fieldLabel : '*属性值',
							allowBlank : false,
							blankText : '属性值不能为空',
							maxLength:100,
							minLength:1,
							width : '100',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							vtype : 'trim',
							width : 200,
							fieldLabel : '*属性编码',
							allowBlank : false,
							blankText : '属性编码不能为空',
							maxLength:20,
							minLength:1,
							name : 'code',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : lookupIdStore,
							xtype : 'combo',
							resizable:true,
							fieldLabel : '*字典种类ID',
							name : 'lookup',
							hiddenName : 'lookup',
							valueField : 'name',
							displayField : 'name',
							mode : 'local',
							emptyText : '请选择',
							allowBlank : false,
							blankText : '字典种类ID不能为空',
//							maxLength:0,
//							minLength:0,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							width : '200',
							anchor : '90%'} ]
					}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [{
					name : 'comment',
					xtype : 'textarea',
					fieldLabel : '*属性说明',
					allowBlank : false,
					blankText : '属性说明不能为空',
					maxLength:200,
					minLength:1,
					width : '200',
					anchor : '90%'
						}],
					buttons : [
					{
						text : '保  存',
						handler : function() {
							if(!addLookupMappingForm.getForm().isValid())
							{ 
								Ext.Msg.alert('提示','输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/lookupMapping-item.json',
								method : 'POST',
								form : addLookupMappingForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!' );
								}
							})
							addLookupMappingWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							addLookupMappingWindow.hide();
						}
					} ]
				}
				]
			});

			// 修改窗口展示的from
			var editLookupMappingForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 150,
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
							name : 'value',
							xtype : 'textfield',
							vtype : 'trim',
							fieldLabel : '*属性值',
							allowBlank : false,
							blankText : '属性值不能为空',
							maxLength:100,
							minLength:1,
							width : '100',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							vtype : 'trim',
							width : 200,
							fieldLabel : '*属性编码',
							allowBlank : false,
							blankText : '属性编码属性值不能为空',
							maxLength:20,
							minLength:1,
							name : 'code',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : lookupIdStore,
							xtype : 'combo',
							resizable:true,
							fieldLabel : '*字典种类ID',
							name : 'lookup',
							hiddenName : 'lookup',
							valueField : 'name',
							displayField : 'name',
							emptyText : '请选择',
							allowBlank : false,
							blankText : '字典种类ID不能为空',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							width : '200',
							anchor : '90%'} ]
					}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [{
						name : 'comment',
						xtype : 'textarea',
						fieldLabel : '*属性说明',
						allowBlank : false,
						maxLength:200,
						minLength:1,
						blankText : '属性说明不能为空',
						width : '200',
						anchor : '90%'
							},{
								// 隐藏的ID
								xtype : 'hidden',
								width : 200,
								fieldLabel : '条目ID',
								name : 'ID',
								anchor : '90%'
							}],
					buttons : [
					{
						text : '保  存',
						handler : function() {
							if(!editLookupMappingForm.getForm().isValid())
							{ 
								Ext.Msg.alert('提示','输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/lookupMapping-item.json',
								method : 'POST',
								form : editLookupMappingForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!' );
								}
							})
							editLookupMappingWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							editLookupMappingWindow.hide();
						}
					} ]
				}
				]
			});

			// 详情展示的from
			var lookupMappingDetailForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 150,
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
							width : 200,
							fieldLabel : '条目ID',
							name : 'ID',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '属性编码',
							name : 'code',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'value',
							xtype : 'textfield',
							fieldLabel : '属性值',
							width : '100',
							anchor : '90%'
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '字典种类ID',
							name : 'lookup',
							anchor : '90%'
						} ]
					}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [  {
						name : 'comment',
						xtype : 'textarea',
						fieldLabel : '属性说明',
						width : '200',
						anchor : '90%'
					} ],
					buttons : [
					    {
						text : '返  回',
						handler : function() {
							lookupMappingDetailWindow.hide();
						}
					} ]
				}
				]
			});

			// 定义新增窗口
			var addLookupMappingWindow = new Ext.Window({
				title : '字典属性新增',
				plain : true,
				layout : 'fit',
				width : 800,
				height :200,
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
				items : [ addLookupMappingForm ]
			});

			// 定义修改窗口
			var editLookupMappingWindow = new Ext.Window({
				title : '字典属性修改',
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
				items : [ editLookupMappingForm ]
			});
			
			// 定义详情窗口
			var lookupMappingDetailWindow = new Ext.Window({
				title : '字典属性详情',
				plain : true,
				layout : 'fit',
				width : 880,
				height :200,
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
				items : [ lookupMappingDetailForm ]
			});

			// 展示新增窗口
			function addInit() {
				addLookupMappingWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editLookupMappingWindow.show();
			}
			
			//展示字典种类窗口
			function detailInit() {
				lookupMappingDetailWindow.show();
			}

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				title : '字典属性列表',
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