var operate = '';//标识  第一次新增：add,新增时第二次以上的点击：add2,修改：update
Ext.onReady(function() {
			Ext.QuickTips.init();

			/** *************用于修复ie下datefield显示不完整的bug,ie9测试ok************** */
			Ext.isIE8 = Ext.isIE && navigator.userAgent.indexOf('MSIE 8') != -1;
			Ext.override(Ext.menu.Menu, {
				autoWidth : function() {
					var el = this.el, ul = this.ul;
					if (!el) {
						return;
					}
					var w = this.width;
					if (w) {
						el.setWidth(w);
					} else if (Ext.isIE && !Ext.isIE8) { // Ext2.2 支持
						// Ext.isIE8 属性
						el.setWidth(this.minWidth);
						var t = el.dom.offsetWidth;
						el.setWidth(ul.getWidth() + el.getFrameWidth("lr"));
					}
				}
			});
			
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
				value : 20,
				editable : false,
				width : 85
			});

			/** ******************列表数据******************** */
			var restfulStore = new Ext.data.Store( {
				  restful:true,
				  proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFCiGradeSchemeManage.json'
				  }),
				  reader: new Ext.data.JsonReader({
					  totalProperty : 'json.count',
					  root:'json.data'
				  }, [{name: 'schemeId', mapping: 'SCHEME_ID'},
				      {name: 'schemeName', mapping: 'SCHEME_NAME'},
				      {name: 'IS_USED_ORA'},
				      {name: 'GRADE_TYPE_ORA'},
				      {name: 'GRADE_USEAGE_ORA'},
				      {name: 'isUsed',mapping: 'IS_USED'},
				      {name: 'gradeType',mapping: 'GRADE_TYPE'},
				      {name: 'gradeUseage',mapping: 'GRADE_USEAGE'},
				      {name: 'time', mapping: 'TIME'},
				      {name: 'createUserId', mapping: 'CREATE_USER_ID'},
				      {name: 'createUserName', mapping: 'CREATE_USER_NAME'},
				      {name: 'createOrgId', mapping: 'CREATE_ORG_ID'},
				      {name: 'createOrgName', mapping: 'CREATE_ORG_NAME'},
				      {name: 'lastUpdateUserName', mapping: 'LAST_UPDATE_USER_NAME'},
				      {name: 'timeUpdate', mapping: 'TIME_UPDATE'},
				      {name: 'schemeScope', mapping: 'SCHEME_SCOPE'},
				      {name: 'orgScopeId', mapping: 'ORG_SCOPE_ID'},
				      {name: 'orgScopeName', mapping: 'ORG_SCOPE_NAME'},
				      {name: 'gradeFrequency', mapping: 'GRADE_FREQUENCY'},
				      {name: 'gradeBeginDate', mapping: 'GRADE_BEGIN_DATE'},
				      {name: 'gradeEndDate',mapping: 'GRADE_END_DATE'},
				      {name: 'gradeFormulaExplain', mapping: 'GRADE_FORMULA_EXPLAIN'},
				      {name: 'gradeFormula', mapping: 'GRADE_FORMULA'},
				      {name: 'memo', mapping: 'MEMO'}
				      ])
			  });
			restfulStore.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}});
			

			var sm = new Ext.grid.CheckboxSelectionModel();

			var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});
			var cm1 = new Ext.grid.ColumnModel([ rownum, sm, {
				hidden : true,
				header : '方案ID',
				dataIndex : 'schemeId',
				sortable : true,
				width : 120
			}, {
				header : '方案名称',
				dataIndex : 'schemeName',
				sortable : true,
				width : 200
			}, {
				header : '是否启用',
				sortable : true,
				dataIndex : 'IS_USED_ORA',
				width : 80
			}, {
				header : '方案类型',
				dataIndex : 'GRADE_USEAGE_ORA',
				sortable : true,
				width : 120
			}, {
				header : '客户类型',
				dataIndex : 'GRADE_TYPE_ORA',
				sortable : true,
				width : 80
			}, {
				header : '创建时间',
				width : 120,
				sortable : true,
				dataIndex : 'time'
			}, {
				header : '创建人ID',
				width : 120,
				sortable : true,
				dataIndex : 'createUserId'
			}, {
				header : '创建人',
				width : 120,
				sortable : true,
				dataIndex : 'createUserName'
			}, {
				header : '创建机构ID',
				dataIndex : 'createOrgId',
				sortable : true,
				width : 120
			}, {
				header : '创建机构',
				dataIndex : 'createOrgName',
				sortable : true,
				width : 120
			}, {
				header : '最近维护人',
				sortable : true,
				dataIndex : 'lastUpdateUserName',
				width : 120
			}, {
				header : '最近维护时间',
				sortable : true,
				dataIndex : 'timeUpdate',
				width : '50'
			}, {
				header : '方案适用范围',
				sortable : true,
				hidden:true,
				dataIndex : 'schemeScope',
				width : '50'
			}, {
				header : '方案机构范围Id',
				sortable : true,
				hidden:true,
				dataIndex : 'orgScopeId',
				width : '50'
			}, {
				header : '方案机构范围',
				sortable : true,
				hidden:true,
				dataIndex : 'orgScopeName',
				width : '50'
			}, {
				header : '评级频率',
				sortable : true,
				hidden:true,
				dataIndex : 'gradeFrequency',
				width : '50'
			}, {
				header : '评级起始日期',
				sortable : true,
				hidden:true,
				dataIndex : 'gradeBeginDate',
				width : '50'
			}, {
				header : '评级结束日期',
				sortable : true,
				hidden:true,
				dataIndex : 'gradeEndDate',
				width : '50'
			}, {
				header : '评级公式解释',
				sortable : true,
				hidden:true,
				dataIndex : 'gradeFormulaExplain',
				width : '50'
			}, {
				header : '评级公式',
				sortable : true,
				hidden:true,
				dataIndex : 'gradeFormula',
				width : '50'
			}, {
				header : '备注',
				sortable : true,
				hidden:true,
				dataIndex : 'memo',
				width : '50'
			}]);
	
		

			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : restfulStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
						restfulStore.reload({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});
			});
			
			
			/** **********************客户评级方案查询FORM*************************** */
			var centerApply = new Ext.form.FormPanel({
				id : "searchCondition",
				labelWidth : 100,
				frame : true,
				autoScroll : true,
				region : 'north',
				title : '客户评级方案查询',
				buttonAlign : "center",
				height : 100,
				width : '100%',
				labelAlign : 'right',
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '方案名称',
							name : 'SCHEME_NAME',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							store : typeStore1 ,
							xtype : 'combo',
							resizable : true,
							name : 'GRADE_USEAGE',
							hiddenName : 'GRADE_USEAGE',
							fieldLabel : '方案类型',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							anchor : '90%'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							store : irStore ,
							xtype : 'combo',
							resizable : true,
							name : 'IS_USED',
							hiddenName : 'IS_USED',
							fieldLabel : '是否启用',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							anchor : '90%'
						} ]
					} ]
				} ],
				buttons : [ {
					text : '查询',
					xtype : 'button',
					handler : function() {
						var conditionStr = centerApply.getForm().getValues(false);
						restfulStore.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
						restfulStore.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});
					}
				}, {
					text : '重置',
					xtype : 'button',
					handler : function() {
						centerApply.getForm().reset();
					}
				} ]
			});
			
			
			
			
			

			/**
			 * grid toolbar.
			 */
			var tbar = new Ext.Toolbar(
					{
						items : [
								{
									text : '新增方案',
									iconCls : 'addIconCss',
									handler : function() {
										operate = 'add';
										var win = new Ext.Window(
												{
													autoScroll : true,
													resizable : true,
													collapsible : true,
													maximizable : true,
													draggable : true,
													closeAction : 'hide',
													modal : true, // 模态窗口
													animCollapse : true,
													border : false,
													loadMask : true,
													closable : true,
													constrain : true,
													width : 840,
													height : 470,
													title : '新增方案',
													items : [ addaffiche,
															grid_express_result ],
													buttonAlign : 'center',
													buttons : [
															{
																text : '关闭',
																handler : function() {
																	win.hide();
																}
															} ],
													listeners : {
														'hide' : function() {
															restfulStore.reload();
														}
													}
												});
										addaffiche.getForm().reset();
										teamstore_result.reload();
										win.show();
										grid_express_result.tbar.setDisplayed(true);
										Ext.getCmp('save').setDisabled(false);
										Ext.getCmp('save1').setDisabled(false);
										Ext.getCmp('reset').setDisabled(false);
										
									}
								},
								'-',
								{
									id : '__upNot',
									text : '修改方案',
									iconCls : 'resetIconCss',
									handler : function() {
										operate = 'update';
										var _record = grid.getSelectionModel().getSelected();
										var oper = grid.getSelectionModel().selections.items[0].data.createUserId;
										if (!_record) {
											Ext.MessageBox.alert('修改操作','请选择要操作的记录！');
											return false;
										} if(oper != __userId){
											Ext.MessageBox.alert('修改操作','只能修改本人创建的记录！');
											return false;
										}
										else {
											var win = new Ext.Window(
													{
														autoScroll : true,
														resizable : true,
														collapsible : true,
														maximizable : true,
														draggable : true,
														closeAction : 'hide',
														modal : true, // 模态窗口
														animCollapse : true,
														border : false,
														loadMask : true,
														closable : true,
														constrain : true,
														width : 840,
														height : 470,
														title : '修改方案',
														items : [ addaffiche,
																grid_express_result ],
														buttonAlign : 'center',
														buttons : [{
																	text : '关闭',
																	handler : function() {
																		win.hide();
																	}
																} ],
														listeners : {
															'show' : function() {
																var record = grid.getSelectionModel().getSelected();
																addaffiche.getForm().loadRecord(record);
																teamstore_result.reload();
															},
															'hide' : function() {
																restfulStore.reload();
															}
														}
													});
											win.show();
											grid_express_result.tbar.setDisplayed(true);
											Ext.getCmp('save').setDisabled(false);
											Ext.getCmp('save1').setDisabled(false);
											Ext.getCmp('reset').setDisabled(false);
										}
									}
								},
								'-',
								{
									id : 'infoNot',
									text : '查看方案',
									iconCls : 'detailIconCss',
									handler : function() {
										var _record = grid.getSelectionModel().getSelected();
										if (!_record) {
											Ext.MessageBox.alert('查看操作','请选择要操作的记录！');
											return false;
										} else {
											var win = new Ext.Window(
													{
														autoScroll : true,
														resizable : true,
														collapsible : true,
														maximizable : true,
														draggable : true,
														closeAction : 'hide',
														modal : true, // 模态窗口
														animCollapse : true,
														border : false,
														loadMask : true,
														closable : true,
														constrain : true,
														width : 840,
														height : 470,
														title : '查看方案',
														items : [ addaffiche,
																grid_express_result ],
														buttonAlign : 'center',
														buttons : [ {
															text : '关闭',
															handler : function() {
																win.hide();
															}
														} ],
														listeners : {
															'show' : function() {
																var record = grid.getSelectionModel().getSelected();
																addaffiche.getForm().loadRecord(record);
																teamstore_result.reload();
															},
															'hide' : function() {
																restfulStore.reload();
															}
														}
													});
											win.show();
											grid_express_result.tbar.setDisplayed(false);
											Ext.getCmp('save').setDisabled(true);
											Ext.getCmp('save1').setDisabled(true);
											Ext.getCmp('reset').setDisabled(true);
										}
									}
								},
								'-',
								{
									id : 'delNot',
									text : '方案删除',
									iconCls : 'deleteIconCss',
									handler : function() {
										 var selectLength = grid.getSelectionModel().getSelections().length;
										 var selectRe;
										 var tempId;
										 var idStr = '';
										if(selectLength < 1){
											Ext.Msg.alert('提示','请选择需要删除的记录!');
										} else {
											for(var i = 0; i<selectLength;i++)
											{
												selectRe = grid.getSelectionModel().getSelections()[i];
												tempId = selectRe.data.schemeId;
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
																+ '/ocrmFCiGradeSchemeManage!destroyBatch.json?idStr='+ idStr,
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success : function() {
																	Ext.Msg.alert('提示', '操作成功');
																	restfulStore.reload();
																},
																failure : function(response) {
																	var resultArray = Ext.util.JSON.decode(response.status);
																	if(resultArray == 403) {
																           Ext.Msg.alert('提示', response.responseText);
																  } else {
																	Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
																	restfulStore.reload();
																  }
																}
															});
												});
										}}
								
								} ]
					});
			var grid = new Ext.grid.GridPanel({
//				title : '客户评级方案信息',
				frame : true,
				store : restfulStore,
				region : 'center',
				stripeRows : true,
				tbar : tbar,
				cm : cm1,
				sm : sm,
				bbar : bbar,
				viewConfig : {},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ {
					layout : 'border',
					items : [ centerApply, grid ]
				} ]
			});
		});