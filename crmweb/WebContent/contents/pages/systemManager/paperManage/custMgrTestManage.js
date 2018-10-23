Ext.onReady(function() {
	//遮挡
	var lm = new Ext.LoadMask(document.body, {// 定义遮屏到body节点上
		msg : '正在加载表格数据,请稍等...',
		removeMask : true
	});
	// 题目类型
		var titleTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=TITLE_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		titleTypeStore.load();

		// 客户风险信息查询条件
		var simple = new Ext.FormPanel( {
			frame : true,
			id : 'queryGroup',
			bodyStyle : 'padding:5px 5px 0',
			width : '100%',
			labelAlign : 'center',
			items : [ {
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '试题标题',
							labelStyle : 'text-align:right;',
							name : 'TITLE_NAME',
							anchor : '90%',
							labelSeparator:''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : titleTypeStore,
							xtype : 'combo',
							labelStyle : 'text-align:right;',
							resizable : true,
							fieldLabel : '试题分类',
							name : 'TITLE_TYPE',
							hiddenName : 'TITLE_TYPE',
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
							labelSeparator:''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'combo',
							labelStyle : 'text-align:right;',
							resizable : true,
							fieldLabel : '是否有效',
							name : 'AVAILABLE',
							hiddenName : 'AVAILABLE',
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
							store : new Ext.data.SimpleStore( {
								fields : [ 'key', 'value' ],
								data : [ [ '1', '是' ], [ '2', '否' ] ]
							}),
							labelSeparator:''
						} ]
					} ]
				} ]
			} ],
			buttonAlign : 'center',
			keys : [ {
				key : 13,
				fn : function() {
					Ext.getCmp('quession_serch').focus(true);
				},
				scope : this
			} ],
			buttons : [ {
				text : '查询',
				id:'quession_serch',
				handler : function() {
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
					simple.getForm().reset();
				}

			} ]
		});
		// 客户风险信息表格面板
		var sm = new Ext.grid.CheckboxSelectionModel( {
			singleSelect : true
		});
		var rownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cm = new Ext.grid.ColumnModel( [ rownum,{
			header : '试题标题',
			dataIndex : 'titleName',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 4,
			renderer : function(value, meta, record) {
				meta.attr = 'style="white-space:normaddl;"';
				return value;
			}
		}, {
			header : '试题分类',
			dataIndex : 'titleType',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
//			renderer : function(value) {
//				if (value != '') {
//					var index = titleTypeStore.find('key', value);
////					return titleTypeStore.getAt(index).get('value');
//				}
//
//			}
		}, {
			header : '显示顺序',
			dataIndex : 'titleSort',
			sortable : true,
			//xtype : new Ext.form.NumberField(),
			menuDisabled : true,
			width : document.body.scrollWidth / 12

		}, {
			header : '有效标志',
			dataIndex : 'available',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 12,
			renderer : function(value) {
				if (value != '') {
					if (value == '1')
						return '是';
					else if (value == '2')
						return '否';
					else
						return '';
				}
			}

		}, {
			header : '更新人',
			dataIndex : 'updator',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
		}, {
			header : '更新时间',
			dataIndex : 'updateDate',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
		}, {
			header : 'title_id',
			dataIndex : 'titleId',
			menuDisabled : true,
			hidden : true
		} ]);

		var store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/RiskQuession.json'
			}),listeners :{
				'load':function(){lm.hide();}
			},
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'titleName',
				mapping : 'TITLE_NAME'
			}, {
				name : 'titleType',
				mapping : 'TITLE_TYPE'
			}, {
				name : 'titleSort',
				type:'number',
				mapping : 'TITLE_SORT'
			}, {
				name : 'available',
				mapping : 'AVAILABLE'
			}, {
				name : 'updator',
				mapping : 'USER_NAME'
			}, {
				name : 'updateDate',
				mapping : 'UPDATE_DATE'
			}, {
				name : 'titleId',
				mapping : 'TITLE_ID'
			} ])
		});
		store.load( {
			params : {
				start : 0,
				limit : 20
			}
		});
		store.on('beforeload', function() {
			lm.show();
			var conditionStr = simple.getForm().getValues(false);
			this.baseParams = {
				"condition" : Ext.encode(conditionStr)
			};
		});

		
		
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
			editable : false,
			width : 85
		});
		var number = parseInt(pagesize_combo.getValue());
		// 改变每页显示条数reload数据
		pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});
		});
		var bbar = new Ext.PagingToolbar( {
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
		});
		var createForm = new Ext.FormPanel( {
			frame : true,
			id : 'createForm',
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : "center",
			width : '100%',
			labelAlign : 'center',
			items : [ {
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'CREATE_TITLE_NAME',
							xtype : 'textfield',
							fieldLabel : '试题标题',
							labelStyle : 'text-align:right;',
							name : 'CREATE_TITLE_NAME',
							anchor : '90%',
							allowBlank : false,
							validator : trim,
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'CREATE_TITLE_TYPE2',
							store : titleTypeStore,
							xtype : 'combo',
							labelStyle : 'text-align:right;',
							resizable : true,
							fieldLabel : '试题分类',
							name : 'CREATE_TITLE_TYPE',
							hiddenName : 'CREATE_TITLE_TYPE',
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
							allowBlank : false,
							validator : trim,
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'CREATE_TITLE_SORT',
							xtype : 'numberfield',
							fieldLabel : '显示顺序',
							labelStyle : 'text-align:right;',
							name : 'CREATE_TITLE_SORT',
							anchor : '90%',
							decimalPrecision : 0,
							maxValue : 100,
							allowNegative : false,
							maxLength : 3,
							allowBlank : false,
							validator : trim,
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'CREATE_AVAILABLE',
							xtype : 'combo',
							labelStyle : 'text-align:right;',
							resizable : true,
							fieldLabel : '是否有效',
							name : 'AVAILABLE',
							hiddenName : 'AVAILABLE',
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
							allowBlank : false,
							validator : trim,
							store : new Ext.data.SimpleStore( {
								fields : [ 'key', 'value' ],
								data : [ [ '1', '是' ], [ '2', '否' ] ]
							}),
							labelSeparator:''
						} ]
					} ]
				} ]
			} ]
		});
		// 客户非本行资产信息行号
		var teamrownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		var teamsm = new Ext.grid.CheckboxSelectionModel();
		// 客户非本行资产信息定义列模型
		var teamcm = new Ext.grid.ColumnModel( [ teamrownum, {

			header : '选项内容',
			dataIndex : 'result',
			width : 300,
			editor : new Ext.form.TextField( {
				id : 'CREATE_RESULT'
			}),
			sortable : true,
			renderer : function(value, meta, record) {
				meta.attr = 'style="white-space:normal;"';
				return value;
			}
		}, {
			header : '选项分值',
			hidden:true,
			dataIndex : 'resultScoring',
			//可以输入负数 修改 2012-07-22 兰超
			editor : new Ext.form.TextField( {
				id : 'CREATE_RESULT_SCORING',
				regex :/^[-]?\d*[.]?(\d{0})?$/,

			//	decimalPrecision : 0,
				
			//	allowNegative : true,  
//				minLength : 1,
				//可以输入负数 修改 2012-07-22 兰超	
				maxLength : 3,			
				minLengthText : '该字段不可为空'
			}),
			width : 165,
			sortable : true
		}, {
			header : '选项顺序',
			dataIndex : 'resultSort',
			editor : new Ext.form.NumberField( {
				id : 'CREATE_RESULT_SORT',
				decimalPrecision : 0,
				allowNegative : false,
				maxLength : 3,
				minLength : 1,
				minLengthText : '该字段不可为空'
			}),
			width : 165,
			sortable : true
		} ]);

		// 客户非本行资产信息数据存储
		var teamstore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/RiskQuession!findResult.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'result',
				mapping : 'RESULT'
			}, {
				name : 'resultScoring',
				mapping : 'RESULT_SCORING'
			}, {
				name : 'resultSort',
				mapping : 'RESULT_SORT'
			} ])
		});
		var tbar2 = new Ext.Toolbar( {
			items : [ {
				id : 'createResult',
				text : '新增',
				handler : function() {
					teamstore.add(new Ext.data.Record);
				}
			}, {
				id : 'deleteResult',
				text : '删除',
				handler : function() {
					var records = teamgrid.getSelectionModel().getSelections();
					var recordsLen = records.length;
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择记录后进行删除！");
						return;
					} else {
						teamstore.remove(records);
					}
				}
			} ]
		});
		// 答案
		var teamgrid = new Ext.grid.EditorGridPanel( {
			// title : '资产信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm, // 列模型
			sm : teamsm,
			// bbar : bbar,
			tbar : tbar2,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		var opWin = new Ext.Window(
				{
					id : 'opWin',
					resizable : false,
					collapsible : false,
					draggable : true,
					closeAction : 'hide',
					modal : true, // 模态窗口
					animCollapse : false,
					border : false,
					loadMask : true,
					closable : true,
					constrain : true,
					width : 700,
					height : 450,
					title : '新增题目',
					items : [ createForm, teamgrid ],

					buttons : [
							{
								text : '保存',
								id : 'score_count2',
								handler : function() {
								opWin.hide();
							}
							}, {
								text : '重置',
								id : 'reset',
								handler : function() {
									teamstore.removeAll();
									createForm.getForm().reset();
								}
							}, {
								text : '关闭',
								id : 'close',
								handler : function() {
									opWin.hide();
								}
							} ]
				});

		opWin.on('hide', function() {
			teamstore.removeAll();
			createForm.getForm().reset();
			store.load( {
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		});

		// 表格工具栏
		var tbar = new Ext.Toolbar(
				{
					items : [
							{
								text : '新增',
								iconCls : 'addIconCss',
								handler : function() {
									Ext.getCmp('reset').show();
									Ext.getCmp('score_count2').show();
									Ext.getCmp('createResult').show();
									Ext.getCmp('deleteResult').show();
									Ext.getCmp('CREATE_TITLE_NAME').enable();
									Ext.getCmp('CREATE_TITLE_TYPE2').enable();
									Ext.getCmp('CREATE_TITLE_SORT').enable();
									Ext.getCmp('CREATE_AVAILABLE').enable();
									Ext.getCmp('CREATE_RESULT').setReadOnly(false);
									Ext.getCmp('CREATE_RESULT_SORT').setReadOnly(false);
									Ext.getCmp('CREATE_RESULT_SCORING').setReadOnly(false);
									opWin.setTitle('新增试题');
									opWin.show();

								}
							},
							{
								text : '查看',
								iconCls : 'detailIconCss',
								handler : function() {

									var records = riskGrid.getSelectionModel()
											.getSelections();
									var recordsLen = records.length;
									if (recordsLen != 1) {
										Ext.Msg.alert("系统提示信息",
												"请选择其中一条记录进行查看！");
										return;
									} else {
										opWin.setTitle('查看试题');
										Ext.getCmp('reset').hide();
										Ext.getCmp('score_count2').hide();
										Ext.getCmp('createResult').hide();
										Ext.getCmp('deleteResult').hide();
										Ext.getCmp('CREATE_TITLE_NAME')
												.disable();
										Ext.getCmp('CREATE_TITLE_TYPE2')
												.disable();
										Ext.getCmp('CREATE_TITLE_SORT')
												.disable();
										Ext.getCmp('CREATE_AVAILABLE')
												.disable();
										Ext.getCmp('CREATE_RESULT').setReadOnly(true);
										Ext.getCmp('CREATE_RESULT_SORT').setReadOnly(true);
										Ext.getCmp('CREATE_RESULT_SCORING').setReadOnly(true);

										opWin.show();

										var record = riskGrid
												.getSelectionModel()
												.getSelected();
										Ext
												.getCmp('CREATE_TITLE_NAME')
												.setValue(
														record.get('titleName'));
										Ext
												.getCmp('CREATE_TITLE_TYPE2')
												.setValue(
														record.get('titleType'));
										Ext
												.getCmp('CREATE_TITLE_SORT')
												.setValue(
														record.get('titleSort'));
										Ext
												.getCmp('CREATE_AVAILABLE')
												.setValue(
														record.get('available'));
										teamstore.load( {
											params : {
												titleId : record.get('titleId')
											}
										});
									}

								}
							},
							{
								text : '开启',
								iconCls : 'completeIconCss',
								handler : function() {

									var records = riskGrid.getSelectionModel()
											.getSelections();
									var recordsLen = records.length;
									var record = riskGrid.getSelectionModel()
											.getSelected();
									if (recordsLen != 1) {
										Ext.Msg.alert("系统提示信息",
												"请选择其中一条记录进行查看！");
										return;
									} else if (record.get('available') == '1') {
										Ext.MessageBox.alert('试题维护', '试题已经开启！');
										return;
									} else {

										Ext.Ajax
												.request( {
													url : basepath + '/RiskQuession!openOrCloseQuession.json',
													method : 'POST',
													params : {
														titleId : record
																.get('titleId'),
														available : '1'
													},
													failure : function(form,
															action) {
														Ext.MessageBox
																.alert('试题维护',
																		'开启失败！');

													},
													success : function(response) {
														store
																.load( {
																	params : {
																		start : 0,
																		limit : bbar.pageSize
																	}
																});
														Ext.MessageBox
																.alert('试题维护',
																		'开启成功！');

													}
												});
									}

								}
							},
							{
								text : '关闭',
								iconCls : 'closeIconCss',
								handler : function() {
									var records = riskGrid.getSelectionModel()
											.getSelections();
									var recordsLen = records.length;
									var record = riskGrid.getSelectionModel()
											.getSelected();
									if (recordsLen != 1) {
										Ext.Msg.alert("系统提示信息",
												"请选择其中一条记录进行查看！");
										return;
									} else if (record.get('available') == '2') {
										Ext.MessageBox.alert('试题维护', '试题已经关闭！');
										return;
									} else {

										Ext.Ajax
												.request( {
													url : basepath + '/RiskQuession!openOrCloseQuession.json',
													method : 'POST',
													params : {
														titleId : record
																.get('titleId'),
														available : '2'
													},
													failure : function(form,
															action) {
														Ext.MessageBox
																.alert('试题维护',
																		'关闭失败！');

													},
													success : function(response) {
														store
																.load( {
																	params : {
																		start : 0,
																		limit : bbar.pageSize
																	}
																});
														Ext.MessageBox
																.alert('试题维护',
																		'关闭成功！');

													}
												});
									}

								}
							} ]
				});
		// create the Grid
		var riskGrid = new Ext.grid.GridPanel( {
			height : document.body.scrollHeight - 97,
			width : document.body.scrollWidth,
			frame : true,
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : store,
			stripeRows : true, // 斑马线
			cm : cm,
			sm : sm,
			tbar : tbar,
			bbar : bbar,
			viewConfig : {}
		});
		
		
		riskGrid.on("celldblclick", function(){


			var records = riskGrid.getSelectionModel()
					.getSelections();
			var recordsLen = records.length;
			if (recordsLen != 1) {
				Ext.Msg.alert("系统提示信息",
						"请选择其中一条记录进行查看！");
				return;
			} else {
				opWin.setTitle('查看试题');
				Ext.getCmp('reset').hide();
				Ext.getCmp('score_count2').hide();
				Ext.getCmp('createResult').hide();
				Ext.getCmp('deleteResult').hide();
				Ext.getCmp('CREATE_TITLE_NAME')
						.disable();
				Ext.getCmp('CREATE_TITLE_TYPE2')
						.disable();
				Ext.getCmp('CREATE_TITLE_SORT')
						.disable();
				Ext.getCmp('CREATE_AVAILABLE')
						.disable();
				Ext.getCmp('CREATE_RESULT').setReadOnly(true);
				Ext.getCmp('CREATE_RESULT_SORT').setReadOnly(true);
				Ext.getCmp('CREATE_RESULT_SCORING').setReadOnly(true);

				opWin.show();

				var record = riskGrid
						.getSelectionModel()
						.getSelected();
				Ext
						.getCmp('CREATE_TITLE_NAME')
						.setValue(
								record.get('titleName'));
				Ext
						.getCmp('CREATE_TITLE_TYPE2')
						.setValue(
								record.get('titleType'));
				Ext
						.getCmp('CREATE_TITLE_SORT')
						.setValue(
								record.get('titleSort'));
				Ext
						.getCmp('CREATE_AVAILABLE')
						.setValue(
								record.get('available'));
				teamstore.load( {
					params : {
						titleId : record.get('titleId')
					}
				});
			}

		
		});

		/**
	      * 输入项校验空格
	      */
	     function trim(_v) {         
	        if( _v != _v.trim()) {
	        	return  false;
	    	}
	        	return true;
	     };
	     //'trimText' : '输入项项首项尾有空格'
		
		/*******************整体显示布局******************/
		var viewport = new Ext.Viewport({
			layout : 'fit',
			frame : true,
			items : [{
				layout:'border',
				items:[{
					region : 'north',
					id : 'north-panel',
					title : "客户经理管理->客户经理问卷试题维护",
					height : 100,
					hidden : false,
					margins : '0 0 0 0',
					items : [ simple ]
				},{
					region : 'center',
					id : 'center-panel',
					margins : '0 0 0 0',
					items : [ riskGrid ]
				}]
			}]
		});

	});