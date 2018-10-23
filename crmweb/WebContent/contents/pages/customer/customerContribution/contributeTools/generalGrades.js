Ext
		.onReady(function() {
			Ext.QuickTips.init();

			var cardUseStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=CARD_USE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

			var qForm = new Ext.form.FormPanel({
				title : "打分卡查询",
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
							store : cardUseStore,
							xtype : 'combo',
							name : 'CARD_USE',
							hiddenName : 'CARD_USE',
							fieldLabel : '打分卡用途',
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
						items : [ {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 60,
							items : {
								fieldLabel : '申请日期',
								xtype : 'datefield',
								name : 'APPLY_DATES',
								format : 'Y-m-d',
								editable : false,
								anchor : '100%'
							}
						}, {
							columnWidth : .5,
							layout : 'form',
							labelStyle : 'text-align:center',
							labelAlign : 'right',
							labelSeparator : '',
							labelWidth : 30,
							items : {
								xtype : 'datefield',
								fieldLabel : '至',
								name : 'APPLY_DATEE',
								format : 'Y-m-d',
								editable : false,
								anchor : '90%'
							}
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
				name : 'ID'
			}, {
				name : 'APPLY_DATE'
			}, {
				name : 'ORG_NAME'
			}, {
				name : 'BELONG_INSTN'
			}, {
				name : 'LOCAL_ORG_NAME'
			}, {
				name : 'CARD_USE'
			}, {
				name : 'APPLY_INTRODUCTION'
			}, {
				name : 'FILLER_NAME'
			}, {
				name : 'APPLY_COMMENT'
			}, {
				name : 'LOCAL_ORG_OPINION'
			}, {
				name : 'CENTRAL_ORG_OPINION'
			}, {
				name : 'CUST_ZH_NAME'
			}, {
				name : 'CUST_ZZDM'
			}, {
				name : 'CARD_USE_ORA'
			} ]);

			// 定义列模型
			var cm = new Ext.grid.ColumnModel([ rownum, sm, {
				header : '序号',
				width : 50,
				align : 'center',
				dataIndex : 'ID',
				sortable : true
			}, {
				header : '客户名称',
				width : 100,
				align : 'center',
				dataIndex : 'CUST_ZH_NAME',
				sortable : true
			}, {
				header : '组织机构代码',
				width : 100,
				align : 'center',
				dataIndex : 'CUST_ZZDM',
				sortable : true
			}, {
				header : '所属机构名称',
				width : 150,
				align : 'center',
				dataIndex : 'ORG_NAME',
				sortable : true
			}, {
				header : '所属分行管理部',
				width : 150,
				align : 'center',
				dataIndex : 'LOCAL_ORG_NAME',
				sortable : true
			}, {
				header : '打分卡用途',
				width : 100,
				align : 'center',
				dataIndex : 'CARD_USE_ORA',
				sortable : true
			}, {
				header : '填表人',
				width : 100,
				align : 'center',
				dataIndex : 'FILLER_NAME',
				sortable : true
			}, {
				header : '申请简介',
				width : 200,
				align : 'center',
				dataIndex : 'APPLY_INTRODUCTION',
				sortable : true
			}, {
				header : '申请备注',
				width : 200,
				align : 'center',
				dataIndex : 'APPLY_COMMENT',
				sortable : true
			}, {
				header : '申请日期',
				width : 100,
				align : 'center',
				dataIndex : 'APPLY_DATE',
				sortable : true
			}, {
				header : '分行管理部意见',
				width : 200,
				align : 'center',
				dataIndex : 'LOCAL_ORG_OPINION',
				sortable : true
			}, {
				header : '总行意见',
				width : 200,
				align : 'center',
				dataIndex : 'CENTRAL_ORG_OPINION',
				sortable : true
			} ]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/gradeQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
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
			var cusrownumDetail = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			var cuscmDetail = new Ext.grid.ColumnModel([ cusrownumDetail, {
				header : 'id',
				dataIndex : 'ID',
				sortable : true,
				width : 150,
				hidden : true
			}, {
				header : '客户名称',
				dataIndex : 'CUST_ZH_NAME',
				sortable : true,
				width : 100
			}, {
				header : '组织机构代码',
				dataIndex : 'CUST_ZZDM',
				sortable : true,
				width : 100
			}, {
				header : '客户所属机构号',
				dataIndex : 'BELONG_INSTN',
				sortable : true,
				width : 100
			} ]);

			var cusstoreDetail = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/queryCustomerBaseGrades.json'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'json.count',
					root : 'json.data'
				}, [ 'ID', 'CUST_ZH_NAME', 'CUST_ZZDM', 'BELONG_INSTN' ])
			});
			// 每页显示条数下拉选择框
			var cuspagesize_comboDetail = new Ext.form.ComboBox({
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

			var cusrownumDetail = parseInt(cuspagesize_comboDetail.getValue());
			// 改变每页显示条数reload数据
			cuspagesize_comboDetail.on("select", function(comboBox) {
				cusbbarDetail.pageSize = parseInt(cuspagesize_comboDetail
						.getValue()), cusstoreDetail.reload({
					params : {
						start : 0,
						limit : parseInt(cuspagesize_comboDetail.getValue())
					}
				});
			});
			// 分页工具栏
			var cusbbarDetail = new Ext.PagingToolbar({
				pageSize : cusrownumDetail,
				store : cusstoreDetail,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', cuspagesize_comboDetail ]
			});

			var cusGridDetail = new Ext.grid.GridPanel({
				store : cusstoreDetail,
				cm : cuscmDetail,
				height : 250,
				bbar : cusbbarDetail,
				width : '100%'
			});

			var memberSearch = new Ext.FormPanel({
				title : '成员企业信息',
				frame : true,
				border : false,
				buttonAlign : 'center',
				labelAlign : 'right',
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'CUST_ZH_NAME',
							labelStyle : {
								width : '120px'
							},
							anchor : '90%'
						} ]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '组织机构代码',
							name : 'CUST_ZZDM',
							labelStyle : {
								width : '120px'
							},
							anchor : '90%'
						} ]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户所属机构号',
							name : 'BELONG_INSTN',
							labelStyle : {
								width : '120px'
							},
							anchor : '90%'
						} ]
					} ]
				} ],
				// buttonAlign:'center',
				buttons : [
						{
							text : '查询',
							handler : function() {
								var conditionStr = memberSearch.getForm()
										.getValues(false);
								cusstoreDetail.baseParams = {
									"condition" : Ext.encode(conditionStr)
								};
								cusstoreDetail.load({
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
								memberSearch.getForm().reset();
							}
						} ]
			});
			// 表格工具栏
			var tbar = new Ext.Toolbar(
					{
						items : [
								{
									text : '新增',
									handler : function() {
										addGeneralGradesForm.getForm().reset();
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
											editGeneralGradesForm.getForm()
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
																	tempId = selectRe.data.ID;
																	idStr += tempId;
																	if (i != selectLength - 1)
																		idStr += ',';
																}
																Ext.Ajax
																		.request({
																			url : basepath
																					+ '/general-grades/'
																					+ tempId
																					+ '/batchDestroy.json?idStr='
																					+ idStr,
																			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																			success : function() {
																				Ext.Msg
																						.alert(
																								'提示',
																								'操作成功!');
																				store
																						.reload();
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
											GeneralGradesDetailForm.getForm()
													.loadRecord(selectRe);
											detailInit();
										}
									}
								} ]
					});

			// 新增窗口展示的from
			var addGeneralGradesForm = new Ext.form.FormPanel({
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
							store : cardUseStore,
							xtype : 'combo',
							name : 'CARD_USE',
							hiddenName : 'CARD_USE',
							fieldLabel : '*打分卡用途',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							allowBlank : false,
							blankText : '打分卡用途不能为空',
							width : '100',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'CUST_ZH_NAME',
							fieldLabel : '*客户名称',
							readOnly : true,
							allowBlank : false,
							blankText : '客户名称不能为空',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'CUST_ZZDM',
							fieldLabel : '*组织机构代码',
							readOnly : true,
							allowBlank : false,
							blankText : '组织机构代码不能为空',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						Width : '200',
						name : 'APPLY_INTRODUCTION',
						fieldLabel : '申请简介',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'APPLY_COMMENT',
						fieldLabel : '申请备注',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'LOCAL_ORG_OPINION',
						fieldLabel : '分行管理部意见',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'CENTRAL_ORG_OPINION',
						fieldLabel : '总行意见',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						Width : '200',
						name : 'BELONG_INSTN',
						fieldLabel : '所属机构ID',
						anchor : '90%'
					} ],
					buttons : [ {
						text : '选取客户',
						handler : function() {

							GeneralGradesChooseWindow.show();
							cusstoreDetail.load();
						}
					}, {
						text : '保  存',
						handler : function() {
							if (!addGeneralGradesForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/general-grades.json',
								method : 'POST',
								form : addGeneralGradesForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!');
								}
							})
							addGeneralGradesWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							addGeneralGradesWindow.hide();
						}
					} ]
				} ]
			});

			// 修改窗口展示的from
			var editGeneralGradesForm = new Ext.form.FormPanel({
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
							store : cardUseStore,
							xtype : 'combo',
							name : 'CARD_USE',
							hiddenName : 'CARD_USE',
							fieldLabel : '*打分卡用途',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							allowBlank : false,
							blankText : '打分卡用途不能为空',
							width : '100',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							vtype : 'trim',
							Width : '100',
							name : 'FILLER_NAME',
							fieldLabel : '*填表人',
							allowBlank : false,
							blankText : '填表人不能为空',
							readOnly : true,
							maxLength : 100,
							minLength : 1,
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'CUST_ZH_NAME',
							fieldLabel : '*客户名称',
							readOnly : true,
							allowBlank : false,
							blankText : '客户名称不能为空',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'ORG_NAME',
							fieldLabel : '*所属机构名称',
							readOnly : true,
							allowBlank : false,
							blankText : '所属机构名称不能为空',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'BELONG_INSTN',
							fieldLabel : '*所属分行管理部',
							readOnly : true,
							allowBlank : false,
							blankText : 'LOCAL_ORG_NAME',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'CUST_ZZDM',
							fieldLabel : '*组织机构代码',
							readOnly : true,
							allowBlank : false,
							blankText : '组织机构代码不能为空',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'form',
					buttonAlign : 'center',

					items : [ {
						xtype : 'textarea',
						Width : '200',
						name : 'APPLY_INTRODUCTION',
						fieldLabel : '申请简介',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'APPLY_COMMENT',
						fieldLabel : '申请备注',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'LOCAL_ORG_OPINION',
						fieldLabel : '分行管理部意见',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'CENTRAL_ORG_OPINION',
						fieldLabel : '总行意见',
						anchor : '90%'
					}, {
						// 隐藏的ID
						xtype : 'hidden',
						width : 200,
						fieldLabel : '序号',
						name : 'ID',
						anchor : '90%'
					} ],
					buttons : [ {
						text : '保  存',
						handler : function() {
							if (!editGeneralGradesForm.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/general-grades.json',
								method : 'POST',
								form : editGeneralGradesForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!');
								}
							})
							editGeneralGradesWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							editGeneralGradesWindow.hide();
						}
					} ]
				} ]
			});

			// 详情展示的from
			var GeneralGradesDetailForm = new Ext.form.FormPanel({
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
							Width : '100',
							name : 'ID',
							fieldLabel : '序号',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : cardUseStore,
							xtype : 'combo',
							name : 'CARD_USE',
							hiddenName : 'CARD_USE',
							fieldLabel : '打分卡用途',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							vtype : 'trim',
							Width : '100',
							name : 'FILLER_NAME',
							fieldLabel : '填表人',
							maxLength : 100,
							minLength : 1,
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'CUST_ZH_NAME',
							fieldLabel : '客户名称',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'ORG_NAME',
							fieldLabel : '所属机构名称',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'BELONG_INSTN',
							fieldLabel : '所属分行管理部',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'CUST_ZZDM',
							fieldLabel : '组织机构代码',
							anchor : '90%'
						} ]
					} , {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'APPLY_DATE',
							fieldLabel : '申请日期',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						Width : '200',
						name : 'APPLY_INTRODUCTION',
						fieldLabel : '申请简介',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'APPLY_COMMENT',
						fieldLabel : '申请备注',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'LOCAL_ORG_OPINION',
						fieldLabel : '分行管理部意见',
						anchor : '90%'
					}, {
						xtype : 'textarea',
						Width : '200',
						name : 'CENTRAL_ORG_OPINION',
						fieldLabel : '总行意见',
						anchor : '90%'
					} ],
					buttons : [ {
						text : '返  回',
						handler : function() {
							GeneralGradesDetailWindow.hide();
						}
					} ]
				} ]
			});

			// 定义新增窗口
			var addGeneralGradesWindow = new Ext.Window({
				title : '打分卡新增',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 420,
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
				items : [ addGeneralGradesForm ]
			});

			// 定义修改窗口
			var editGeneralGradesWindow = new Ext.Window({
				title : '打分卡修改',
				plain : true,
				layout : 'fit',
				width : 880,
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
				items : [ editGeneralGradesForm ]
			});

			// 定义详情窗口
			var GeneralGradesDetailWindow = new Ext.Window({
				title : '打分卡详情',
				plain : true,
				layout : 'fit',
				width : 880,
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
				items : [ GeneralGradesDetailForm ]
			});

			// 展示选取客户窗口
			var GeneralGradesChooseWindow = new Ext.Window(
					{
						width : 800,
						height : 450,
						closable : true,
						resizable : false,
						collapsible : false,
						draggable : true,
						closeAction : 'hide',
						modal : true, // 模态窗口
						animCollapse : false,
						border : false,
						closable : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [ memberSearch, cusGridDetail ],
						buttonAlign : 'center',
						buttons : [
								{
									text : '确定',
									handler : function() {
										var selectLength = cusGridDetail
												.getSelectionModel()
												.getSelections().length;

										var selectRe = cusGridDetail
												.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示', '请选择一条记录!');
										} else {
											addGeneralGradesForm.getForm()
													.loadRecord(selectRe);
											GeneralGradesChooseWindow.hide();
										}
									}
								}, {
									text : '取消',
									handler : function() {
										GeneralGradesChooseWindow.hide();
									}
								} ]
					});

			// 展示新增窗口
			function addInit() {
				addGeneralGradesWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editGeneralGradesWindow.show();
			}

			// 展示详情窗口
			function detailInit() {
				GeneralGradesDetailWindow.show();
			}

			// 展示选取客户窗口
			function chooseInit() {
				GeneralGradesChooseWindow.show();
			}

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				title : '打分卡列表',
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