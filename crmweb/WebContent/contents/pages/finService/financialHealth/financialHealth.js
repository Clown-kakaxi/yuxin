Ext.onReady(function() {
	function sum() {
		var otherAssetSum = 0;
		var otherDebtSum = 0;
		var otherNetAssetSum = 0;
		
		var records = teamgrid.getStore().data.items;
		for ( var r = 0; r < records.length; r++) {
			var a = records[r].data.otherAssetAmountValue;
			if (a == '' || a == null)
				a = 0;
			otherAssetSum += parseInt(a);
		}
		
		var records2 = teamgrid2.getStore().data.items;
		for ( var r = 0; r < records2.length; r++) {
			var a = records2[r].data.otherDebtAmountValue;
			if (a == '' || a == null)
				a = 0;
			otherDebtSum += parseInt(a);
		}
		otherNetAssetSum = otherAssetSum - otherDebtSum;
		Ext.getCmp('otherAssetSum').setValue(otherAssetSum);
		Ext.getCmp('otherDebtSum').setValue(otherDebtSum);
		Ext.getCmp('otherNetAssetSum').setValue(otherNetAssetSum);
		
	}

	// 遮挡
		var lm = new Ext.LoadMask(document.body, {// 定义遮屏到body节点上
					msg : '正在加载表格数据,请稍等...',
					removeMask : true
				});

		// /财务指标
		var finIndexStore = new Ext.data.Store( {

			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=FIN_INDEX'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		finIndexStore.load();

		// 客户等级
		var custLevStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=CDE0100016'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		custLevStore.load();

		// 收入类别
		var detialInTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=IN_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		detialInTypeStore.load();

		// 支出类别
		var detialOutTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=OUT_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		detialOutTypeStore.load();

		// 他行资产类型
		var obAssetsTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=OB_ASSETS_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		obAssetsTypeStore.load();

		// 他行负债类型
		var obLaibTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=OB_LAIB_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		obLaibTypeStore.load();

		// 其他资产类型
		var oAssetsTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=O_ASSETS_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		oAssetsTypeStore.load();

		// 其他负债类型
		var oLaibTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=O_LAIB_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		oLaibTypeStore.load();

		// 证件类型
		var certTypStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=PAPERS_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		certTypStore.load();

		// 客户财务信息查询条件
		var simple = new Ext.FormPanel( {
			frame : true,
			region : 'north',
			title : "贵宾理财->财务健康诊断",
			height : 100,
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
							fieldLabel : '核心客户号',
							labelStyle : 'text-align:right;',
							name : 'SOURCE_CUST_ID',
							anchor : '85%',
							labelSeparator : ''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							labelStyle : 'text-align:right;',
							name : 'CUST_ZH_NAME',
							anchor : '85%',
							labelSeparator : ''
						} ]
					}
					// , {
							// columnWidth : .25,
							// layout : 'form',
							// items : [ {
							// store : certTypStore,
							// xtype : 'combo',
							// labelStyle : 'text-align:right;',
							// resizable : true,
							// fieldLabel : '证件类型',
							// name : 'CERT_TYPE',
							// hiddenName : 'CERT_TYPE',
							// valueField : 'key',
							// displayField : 'value',
							// mode : 'local',
							// typeAhead : true,
							// forceSelection : true,
							// triggerAction : 'all',
							// emptyText : '请选择',
							// selectOnFocus : true,
							// width : '100',
							// anchor : '90%'
							// } ]
							// }, {
							// columnWidth : .25,
							// layout : 'form',
							// items : [ {
							// xtype : 'textfield',
							// fieldLabel : '证件号码',
							// labelStyle : 'text-align:right;',
							// name : 'CERT_NUM',
							// anchor : '90%'
							// } ]
							// }
							, {
								columnWidth : .25,
								layout : 'form',
								items : [ {
									store : custLevStore,
									labelStyle : 'text-align:right;',
									xtype : 'combo',
									resizable : true,
									fieldLabel : '客户级别',
									name : 'CUST_LEV',
									hiddenName : 'CUST_LEV',
									valueField : 'key',
									displayField : 'value',
									mode : 'local',
									typeAhead : true,
									forceSelection : true,
									triggerAction : 'all',
									emptyText : '请选择',
									selectOnFocus : true,
									width : '100',
									anchor : '85%',
									labelSeparator : ''
								} ]
							} ]
				} ]
			} ],
			buttonAlign : 'center',
			keys : [ {
				key : 13,
				fn : function() {
					Ext.getCmp('financial_serch').focus(true);
				},
				scope : this
			} ],
			buttons : [ {
				text : '查询',
				id : 'financial_serch',
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

		// 客户财务信息表格面板
		var sm = new Ext.grid.CheckboxSelectionModel( {
			singleSelect : true
		});

		var rownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cm = new Ext.grid.ColumnModel( [ rownum, {
			header : '核心客户号',
			dataIndex : 'sourceCustId',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 6
		}, {
			header : '客户名称',
			dataIndex : 'custZhName',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 6
		}, {
			header : '客户级别',
			dataIndex : 'custLev',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 6,
			renderer : function(value) {
				if (value != '') {
					var index = custLevStore.find('key', value);
					return custLevStore.getAt(index).get('value');
				}

			}
		}, {
			header : '本行资产(元)',
			dataIndex : 'assetSum',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 80) / 6,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		}, {
			header : '他行资产(元)',
			dataIndex : 'otherBank',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 80) / 6,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		}, {
			header : '其他资产(元)',
			dataIndex : 'otherType',
			menuDisabled : true,
			width : (document.body.scrollWidth - 80) / 6,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		}, {
			header : 'crm_id',
			dataIndex : 'custId',
			menuDisabled : true,
			hidden : true
		} ]);
		/**
		 * 财务信息数据存储
		 */

		var store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialHealth.json'
			}),
			listeners : {
				'load' : function() {
				}
			},
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'sourceCustId',
				mapping : 'SOURCE_CUST_ID'
			}, {
				name : 'custZhName',
				mapping : 'CUST_ZH_NAME'
			}, {
				name : 'custLev',
				mapping : 'CUST_LEV'
			}, {
				name : 'assetSum',
				mapping : 'ASSET_SUM'
			}, {
				name : 'otherBank',
				mapping : 'OTHER_BANK'
			}, {
				name : 'otherType',
				mapping : 'OTHER_TYPE'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			} ])
		});
		// 遮挡
		var store_lm = new Ext.LoadMask(document.body, {// 定义遮屏到body节点上
					store : store,
					msg : '正在加载表格数据,请稍等...',
					removeMask : true
				});
		store.load( {
			params : {
				start : 0,
				limit : 20
			}
		});
		store.on('beforeload', function() {
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

		// 客户非本行资产信息行号
		var teamrownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var teamsm = new Ext.grid.CheckboxSelectionModel();
		// 客户非本行资产信息定义列模型
		var teamcm = new Ext.grid.ColumnModel( [ teamrownum, {
			header : '资产类型',
			dataIndex : 'otherAssetType',
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = obAssetsTypeStore.find('key', value);
					return obAssetsTypeStore.getAt(index).get('value');
				}

			}
		}, {
			header : '金额(元)',
			dataIndex : 'otherAssetAmountValue',
			editor : new Ext.form.TextField( {
				// regex:/^[+-]?\d*\.?\d{1,2}$/
					// maskRe : '-'
					regex : /^\d*[.]?(\d{0,2})?$/
				}),
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '' && value != null) {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		} ]);

		// 客户非本行资产信息数据存储
		var teamstore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!amoutValue.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'otherAssetType',
				mapping : 'ASSETS_TYPE'
			}, {
				name : 'otherAssetAmountValue',
				mapping : 'AMOUNT_VALUE'
			}, {
				name : 'flag',
				mapping : 'flag'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'infoId',
				mapping : 'INFO_ID'
			} ])
		});

		// 客户非本行资产信息
		var teamgrid = new Ext.grid.EditorGridPanel( {
			title : '资产信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm, // 列模型
			sm : teamsm,
			bbar : bbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		// 客户非本行负债信息行号
		var teamrownum2 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var teamsm2 = new Ext.grid.CheckboxSelectionModel();
		// 客户非本行负债信息定义列模型
		var teamcm2 = new Ext.grid.ColumnModel( [ teamrownum2, {
			header : '负债类型',
			dataIndex : 'otherDebtType',
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = obLaibTypeStore.find('key', value);
					return obLaibTypeStore.getAt(index).get('value');
				}

			}
		}, {
			header : '金额(元)',
			dataIndex : 'otherDebtAmountValue',
			editor : new Ext.form.TextField( {
				// regex:/^[+-]?\d*\.?\d{1,2}$/
					// maskRe : '-'
					regex : /^\d*[.]?(\d{0,2})?$/
				}),
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		} ]);

		// 客户非本行资产信息数据存储
		var teamstore2 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!amoutValue.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'otherDebtType',
				mapping : 'ASSETS_TYPE'
			}, {
				name : 'otherDebtAmountValue',
				mapping : 'AMOUNT_VALUE'
			}, {
				name : 'flag',
				mapping : 'flag'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'infoId',
				mapping : 'INFO_ID'
			} ])
		});

		// 客户非本行负债信息
		var teamgrid2 = new Ext.grid.EditorGridPanel( {
			title : '负债信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore2, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm2, // 列模型
			sm : teamsm2,
			bbar : bbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		// 客户他行资产负债信息计算表单
		var teamForm = new Ext.form.FormPanel(
				{
					labelWidth : 90, // 标签宽度
					frame : true, // 是否渲染表单面板背景色
					labelAlign : 'middle', // 标签对齐方式
					// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
					buttonAlign : 'center',
					height : 93,
					items : [ {
						layout : 'column',
						border : false,
						items : [ {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '资产合计',
								id : 'otherAssetSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '负债合计',
								id : 'otherDebtSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '净资产',
								id : 'otherNetAssetSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						} ]
					} ],
					buttons : [
							{
								text : '资产统计',
								id : 'other_sum',
								handler : function() {
									Ext.getCmp('other_sum').focus();
									sum();
									Ext.MessageBox.alert('他行资产负债信息', '统计成功！');
								}

							},
							{
								text : '保存',
								id : 'other_save',
								handler : function() {
									Ext.getCmp('other_save').focus();
									sum();
									var assetInfo = new Array();
									var debtInfo = new Array();
									var i = 0;
									teamstore2.each(function(item) {
												debtInfo[i] = item.data.custId
														+ ":"
														+ item.data.flag
														+ ":"
														+ item.data.otherDebtType
														+ ":"
														+ item.data.otherDebtAmountValue
														+ ":"
														+ item.data.infoId;
												i += 1;
											});
									i = 0;
									teamstore.each(function(item) {
												assetInfo[i] = item.data.custId
														+ ":"
														+ item.data.flag
														+ ":"
														+ item.data.otherAssetType
														+ ":"
														+ item.data.otherAssetAmountValue
														+ ":"
														+ item.data.infoId;
												i += 1;
											});
									debugger;
									Ext.Ajax.request( {
												url : basepath + '/FinancialAnalysis!finInfoSaveOrUpdate.json',
												form : teamForm.form.id,
												method : 'POST',
												params : {
													assetInfo : assetInfo,
													debtInfo : debtInfo,
													belongType : '1'

												},
												failure : function(form, action) {
													Ext.MessageBox
															.alert('他行资产负债信息',
																	'保存失败！');

												},
												success : function(response) {
													Ext.MessageBox
															.alert('他行资产负债信息',
																	'保存成功！');

												}
											});
								}
							} ]
				});

		// 客户非本行资产负债信息面板
		var tab_1 = new Ext.Panel( {
			layout : 'form',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					items : [ teamgrid ]
				}, {
					columnWidth : .5,
					items : [ teamgrid2 ]
				} ]
			}, teamForm ]
		});

		function sum2() {
			var anotherAssetSum = 0;
			var anotherDebtSum = 0;
			var anotherNetAssetSum = 0;
			var records = teamgrid3.getStore().data.items;
			for ( var r = 0; r < records.length; r++) {
				var a = records[r].data.otherAssetAmountValue;
				if (a == '' || a == null)
					a = 0;
				anotherAssetSum += parseInt(a);
			}

			var records2 = teamgrid4.getStore().data.items;
			for ( var r = 0; r < records2.length; r++) {
				var a = records2[r].data.otherDebtAmountValue;
				if (a == '' || a == null)
					a = 0;
				anotherDebtSum += parseInt(a);
			}
			anotherNetAssetSum = anotherAssetSum - anotherDebtSum;
			Ext.getCmp('anotherAssetSum').setValue(anotherAssetSum);
			Ext.getCmp('anotherDebtSum').setValue(anotherDebtSum);
			Ext.getCmp('anotherNetAssetSum').setValue(anotherNetAssetSum);
		}

		// 客户其他资产信息行号
		var teamrownum3 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var teamsm3 = new Ext.grid.CheckboxSelectionModel();
		// 客户其他资产信息定义列模型
		var teamcm3 = new Ext.grid.ColumnModel( [ teamrownum3, {
			header : '资产类型',
			dataIndex : 'otherAssetType',
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = oAssetsTypeStore.find('key', value);
					return oAssetsTypeStore.getAt(index).get('value');
				}

			}
		}, {
			header : '金额(元)',
			dataIndex : 'otherAssetAmountValue',
			editor : new Ext.form.TextField( {
				// regex:/^[+-]?\d*\.?\d{1,2}$/
					// maskRe : '-'
					regex : /^\d*[.]?(\d{0,2})?$/
				}),
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		} ]);

		// 客户其他资产信息数据存储
		var teamstore3 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!amoutValue.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'otherAssetType',
				mapping : 'ASSETS_TYPE'
			}, {
				name : 'otherAssetAmountValue',
				mapping : 'AMOUNT_VALUE'
			}, {
				name : 'flag',
				mapping : 'flag'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'infoId',
				mapping : 'INFO_ID'
			} ])
		});

		// 客户其他资产信息
		var teamgrid3 = new Ext.grid.EditorGridPanel( {
			title : '资产信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore3, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm3, // 列模型
			sm : teamsm3,
			bbar : bbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		// 客户其他负债信息行号
		var teamrownum4 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var teamsm4 = new Ext.grid.CheckboxSelectionModel();
		// 客户其他负债信息定义列模型
		var teamcm4 = new Ext.grid.ColumnModel( [ teamrownum4, {
			header : '负债类型',
			dataIndex : 'otherDebtType',
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = oLaibTypeStore.find('key', value);
					return oLaibTypeStore.getAt(index).get('value');
				}

			}
		}, {
			header : '金额(元)',
			dataIndex : 'otherDebtAmountValue',
			editor : new Ext.form.TextField( {
				// regex:/^[+-]?\d*\.?\d{1,2}$/
					// maskRe : '-'
					regex : /^\d*[.]?(\d{0,2})?$/
				}),
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		} ]);

		// 客户其他资产信息数据存储
		var teamstore4 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!amoutValue.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'otherDebtType',
				mapping : 'ASSETS_TYPE'
			}, {
				name : 'otherDebtAmountValue',
				mapping : 'AMOUNT_VALUE'
			}, {
				name : 'flag',
				mapping : 'flag'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'infoId',
				mapping : 'INFO_ID'
			} ])
		});

		// 客户其他负债信息
		var teamgrid4 = new Ext.grid.EditorGridPanel( {
			title : '负债信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore4, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm4, // 列模型
			sm : teamsm4,
			bbar : bbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		// 客户其他资产负债信息计算表单
		var teamForm2 = new Ext.form.FormPanel(
				{
					labelWidth : 90, // 标签宽度
					frame : true, // 是否渲染表单面板背景色
					labelAlign : 'middle', // 标签对齐方式
					// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
					buttonAlign : 'center',
					height : 93,
					items : [ {
						layout : 'column',
						border : false,
						items : [ {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '资产合计',
								id : 'anotherAssetSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '负债合计',
								id : 'anotherDebtSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '净资产',
								id : 'anotherNetAssetSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						} ]
					} ],
					buttons : [
							{
								text : '资产统计',
								id : 'another_sum',
								handler : function() {
									Ext.getCmp('another_sum').focus();
									sum2();
									Ext.MessageBox.alert('其他资产负债信息', '统计成功！');
								}

							},
							{
								text : '保存',
								id : 'another_save',
								handler : function() {
									Ext.getCmp('another_save').focus();
									sum2();
									var assetInfo = new Array();
									var debtInfo = new Array();
									var i = 0;
									teamstore4
											.each(function(item) {
												debtInfo[i] = item.data.custId
														+ ":"
														+ item.data.flag
														+ ":"
														+ item.data.otherDebtType
														+ ":"
														+ item.data.otherDebtAmountValue
														+ ":"
														+ item.data.infoId;
												i += 1;
											});
									i = 0;
									teamstore3
											.each(function(item) {
												assetInfo[i] = item.data.custId
														+ ":"
														+ item.data.flag
														+ ":"
														+ item.data.otherAssetType
														+ ":"
														+ item.data.otherAssetAmountValue
														+ ":"
														+ item.data.infoId;
												i += 1;
											});

									Ext.Ajax
											.request( {
												url : basepath + '/FinancialAnalysis!finInfoSaveOrUpdate.json',
												form : teamForm2.form.id,
												method : 'POST',
												params : {
													assetInfo : assetInfo,
													debtInfo : debtInfo,
													belongType : '2'

												},
												failure : function(form, action) {
													Ext.MessageBox
															.alert('其他资产负债信息',
																	'保存失败！');

												},
												success : function(response) {
													Ext.MessageBox
															.alert('其他资产负债信息',
																	'保存成功！');

												}
											});
								}
							} ]
				});

		// 客户非本行资产负债信息面板
		var tab_2 = new Ext.Panel( {
			layout : 'form',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					items : [ teamgrid3 ]
				}, {
					columnWidth : .5,
					items : [ teamgrid4 ]
				} ]
			}, teamForm2 ]
		});

		var custId;
		var i = 1;
		function load(custId) {
			var chart1 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
					"chartId" + i, "90%", "320", "0", "0");
			i += 1;
			var chart2 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
					"chartId" + i, "90%", "320", "0", "0");
			i += 1;
			var chart3 = new FusionCharts(basepath
					+ "/FusionCharts/Column3D.swf", "chartId" + i, "90%",
					"320", "0", "0");
			i += 1;
			var chart4 = new FusionCharts(basepath
					+ "/FusionCharts/Column3D.swf", "chartId" + i, "90%",
					"320", "0", "0");
			i += 1;
			Ext.Ajax
					.request( {
						url : basepath
								+ '/FinancialAnalysis!assetXml.json?customerId='
								+ custId,
						method : 'GET',
						success : function(response) {
							var dataXml1 = Ext.util.JSON
									.decode(response.responseText).dataXml1;
							var dataXml2 = Ext.util.JSON
									.decode(response.responseText).dataXml2;
							var dataXml3 = Ext.util.JSON
									.decode(response.responseText).dataXml3;
							var dataXml4 = Ext.util.JSON
									.decode(response.responseText).dataXml4;
							var r = Ext.util.JSON.decode(response.responseText).valueMap;
							var r2 = new Ext.data.Record(r);
							tab_3.getForm().loadRecord(r2);
							chart1.setDataXML(dataXml1);
							chart2.setDataXML(dataXml2);
							chart3.setDataXML(dataXml3);
							chart4.setDataXML(dataXml4);
							chart1.render("chartdiv1");
							chart2.render("chartdiv2");
							chart3.render("chartdiv3");
							chart4.render("chartdiv4");
							// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位
							var newAsset11 = Ext.getCmp('newAsset1').getValue();
							if (newAsset11 == '') {
								Ext.getCmp('newAsset1').setValue('');
							} else {
								var money = Mis.Ext.FormatCnMoney(newAsset11);
								Ext.getCmp('newAsset1').setValue(money);
							}
							;
							var bankAssetSum11 = Ext.getCmp('bankAssetSum1')
									.getValue();
							if (bankAssetSum11 == '') {
								Ext.getCmp('bankAssetSum1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(bankAssetSum11);
								Ext.getCmp('bankAssetSum1').setValue(money);
							}
							;
							var bankDebtSum11 = Ext.getCmp('bankDebtSum1')
									.getValue();
							if (bankDebtSum11 == '') {
								Ext.getCmp('bankDebtSum1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(bankDebtSum11);
								Ext.getCmp('bankDebtSum1').setValue(money);
							}
							;
							var bankNetAsset11 = Ext.getCmp('bankNetAsset1')
									.getValue();
							if (bankNetAsset11 == '') {
								Ext.getCmp('bankNetAsset1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(bankNetAsset11);
								Ext.getCmp('bankNetAsset1').setValue(money);
							}
							;
							var otherAssetSum11 = Ext.getCmp('otherAssetSum1')
									.getValue();
							if (otherAssetSum11 == '') {
								Ext.getCmp('otherAssetSum1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(otherAssetSum11);
								Ext.getCmp('otherAssetSum1').setValue(money);
							}
							;
							var otherDebtSum11 = Ext.getCmp('otherDebtSum1')
									.getValue();
							if (otherAssetSum11 == '') {
								Ext.getCmp('otherDebtSum1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(otherAssetSum11);
								Ext.getCmp('otherDebtSum1').setValue(money);
							}
							;
							var otherNetAsset11 = Ext.getCmp('otherNetAsset1')
									.getValue();
							if (otherNetAsset11 == '') {
								Ext.getCmp('otherNetAsset1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(otherNetAsset11);
								Ext.getCmp('otherNetAsset1').setValue(money);
							}
							;
							var anotherAssetSum11 = Ext.getCmp(
									'anotherAssetSum1').getValue();
							if (anotherAssetSum11 == '') {
								Ext.getCmp('anotherAssetSum1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(anotherAssetSum11);
								Ext.getCmp('anotherAssetSum1').setValue(money);
							}
							;
							var anotherDebtSum11 = Ext
									.getCmp('anotherDebtSum1').getValue();
							if (anotherDebtSum11 == '') {
								Ext.getCmp('anotherDebtSum1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(anotherDebtSum11);
								Ext.getCmp('anotherDebtSum1').setValue(money);
							}
							;
							var anotherNetAsset11 = Ext.getCmp(
									'anotherNetAsset1').getValue();
							if (anotherNetAsset11 == '') {
								Ext.getCmp('anotherNetAsset1').setValue('');
							} else {
								var money = Mis.Ext
										.FormatCnMoney(anotherNetAsset11);
								Ext.getCmp('anotherNetAsset1').setValue(money);
							}
							;
							var monthIn11 = Ext.getCmp('monthIn1').getValue();
							if (monthIn11 == '') {
								Ext.getCmp('monthIn1').setValue('');
							} else {
								var money = Mis.Ext.FormatCnMoney(monthIn11);
								Ext.getCmp('monthIn1').setValue(money);
							}
							;
							var monthOut11 = Ext.getCmp('monthOut1').getValue();
							if (monthOut11 == '') {
								Ext.getCmp('monthOut1').setValue('');
							} else {
								var money = Mis.Ext.FormatCnMoney(monthOut11);
								Ext.getCmp('monthOut1').setValue(money);
							}
							;
							var monthNet11 = Ext.getCmp('monthNet1').getValue();
							if (monthNet11 == '') {
								Ext.getCmp('monthNet1').setValue('');
							} else {
								var money = Mis.Ext.FormatCnMoney(monthNet11);
								Ext.getCmp('monthNet1').setValue(money);
							}
							;
							var debtSum11 = Ext.getCmp('debtSum1').getValue();
							if (debtSum11 == '') {
								Ext.getCmp('debtSum1').setValue('');
							} else {
								var money = Mis.Ext.FormatCnMoney(debtSum11);
								Ext.getCmp('debtSum1').setValue(money);
							}
							;
							var assetSum11 = Ext.getCmp('assetSum1').getValue();
							if (assetSum11 == '') {
								Ext.getCmp('assetSum1').setValue('');
							} else {
								var money = Mis.Ext.FormatCnMoney(assetSum11);
								Ext.getCmp('assetSum1').setValue(money);
							}
							;
							// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位

						},
						failure : function(response) {
						}
					});

		}

		var char_set_1 = new Ext.form.FieldSet( {
			height : 350,
			title : '客户本行资产负债信息',
			items : {
				region : 'center',
				id : 'center-panel',
				layout : 'fit',
				html : '<div id="chartdiv1"></div>'

			}
		});

		var char_set_2 = new Ext.form.FieldSet( {
			height : 350,
			title : '客户他行资产负债信息',
			items : {
				region : 'center',
				id : 'center-panel2',
				layout : 'fit',
				html : '<div id="chartdiv2"></div>'
			}
		});

		var char_set_3 = new Ext.form.FieldSet( {
			title : '客户其他资产负债信息',
			height : 350,
			items : {
				region : 'center',
				id : 'center-panel3',
				layout : 'fit',
				html : '<div id="chartdiv3"></div>'
			}
		});

		// 折线
		var char_set_4 = new Ext.form.FieldSet( {
			title : '客户家庭月度收支信息',

			height : 350,
			items : {
				region : 'center',
				id : 'center-panel4',
				layout : 'fit',
				html : '<div id="chartdiv4"></div>'
			}
		});

		var char_set_5 = new Ext.form.FieldSet( {
			title : '客户本行资产负债（元）',
			labelWidth : 80,
			anchor : '90%',
			items : [ {
				width : 100,
				fieldLabel : '本行资产合计',
				name : 'bankAssetSum',
				id : 'bankAssetSum1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '30%',
				readOnly : true
			}, {
				width : 100,
				fieldLabel : '本行负债合计',
				name : 'bankDebtSum',
				id : 'bankDebtSum1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '30%',
				disabled : true
			}, {
				width : 100,
				fieldLabel : '本行净资产',
				name : 'bankNetAsset',
				id : 'bankNetAsset1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '30%',
				disabled : true
			} ]
		});
		var char_set_6 = new Ext.form.FieldSet( {
			title : '客户他行资产负债（元）',
			labelWidth : 80,
			anchor : '90%',
			items : [ {
				width : 100,
				fieldLabel : '他行资产合计',
				name : 'otherAssetSum',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				id : 'otherAssetSum1',
				disabled : true
			}, {
				width : 100,
				fieldLabel : '他行负债合计',
				name : 'otherDebtSum',
				id : 'otherDebtSum1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			}, {
				width : 100,
				fieldLabel : '他行净资产',
				name : 'otherNetAsset',
				id : 'otherNetAsset1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			} ]
		});
		var char_set_7 = new Ext.form.FieldSet( {
			title : '客户其他资产负债（元）',
			labelWidth : 80,
			anchor : '90%',
			items : [ {
				width : 100,
				fieldLabel : '其他资产合计',
				name : 'anotherAssetSum',
				id : 'anotherAssetSum1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			}, {
				width : 100,
				fieldLabel : '其他负债合计',
				name : 'anotherDebtSum',
				id : 'anotherDebtSum1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			}, {
				width : 100,
				fieldLabel : '其他净资产',
				name : 'anotherNetAsset',
				id : 'anotherNetAsset1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			} ]
		});
		var char_set_8 = new Ext.form.FieldSet( {
			title : '月度收支信息（元）',
			labelWidth : 80,
			items : [ {
				width : 100,
				fieldLabel : '月度总收入',
				name : 'monthIn',
				id : 'monthIn1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				readOnly : true
			}, {
				width : 100,
				fieldLabel : '月度总支出',
				name : 'monthOut',
				id : 'monthOut1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			}, {
				width : 100,
				fieldLabel : '盈余',
				name : 'monthNet',
				id : 'monthNet1',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle : 'text-align:right;',
				anchor : '90%',
				disabled : true
			} ]
		});
		var char_set_9 = new Ext.form.FieldSet( {
			title : '客户资产汇总（元）',
			layout : 'column',
			anchor : '99%',
			items : [ {
				columnWidth : 1 / 3,
				layout : 'form',
				items : [ {
					fieldLabel : '客户总资产',
					name : 'assetSum',
					id : 'assetSum1',
					xtype : 'textfield', // 设置为数字输入框类型
					labelStyle : 'text-align:right;',
					anchor : '90%',
					disabled : true
				} ]

			}, {
				columnWidth : 1 / 3,
				layout : 'form',
				items : [ {
					fieldLabel : '客户总负债资产',
					name : 'debtSum',
					id : 'debtSum1',
					xtype : 'textfield', // 设置为数字输入框类型
					labelStyle : 'text-align:right;',
					anchor : '90%',
					disabled : true
				} ]
			}, {
				columnWidth : 1 / 3,
				layout : 'form',
				items : [ {
					fieldLabel : '净资产',
					name : 'newAsset',
					id : 'newAsset1',
					xtype : 'textfield', // 设置为数字输入框类型
					labelStyle : 'text-align:right;',
					anchor : '90%',
					disabled : true
				} ]
			} ],
			listeners : {
				'statesave' : function() {
				}
			}
		});
		var tab_3 = new Ext.form.FormPanel( {
			title : '客户资产负债信息（单位：元）',
			overflow : 'auto',
			items : [ {
				layout : 'column',
				anchor : '99%',
				items : [ {
					layout : 'form',
					columnWidth : 0.5,
					items : [ char_set_1, char_set_3, {
						layout : 'column',
						items : [ {
							columnWidth : 0.5,
							items : [ char_set_5 ]
						}, {
							columnWidth : 0.5,
							items : [ char_set_6 ]
						} ]

					} ]
				}, {
					layout : 'form',
					columnWidth : 0.5,
					items : [ char_set_2, char_set_4, {
						layout : 'column',
						items : [ {
							columnWidth : 0.5,
							items : [ char_set_7 ]
						}, {
							columnWidth : 0.5,
							items : [ char_set_8 ]
						} ]

					} ]
				} ]
			}, char_set_9 ]
		});

		// 家庭月度收支统计
		function sum3() {
			var monthIn = 0;
			var monthOut = 0;
			var monthNet = 0;

			var records = teamgrid5.getStore().data.items;
			for ( var r = 0; r < records.length; r++) {
				var a = records[r].data.money;
				if (a == '' || a == null)
					a = 0;
				monthIn += parseInt(a);
			}
			var records2 = teamgrid6.getStore().data.items;
			for ( var r = 0; r < records2.length; r++) {
				var a = records2[r].data.money;
				if (a == '' || a == null)
					a = 0;
				monthOut += parseInt(a);
			}
			monthNet = monthIn - monthOut;
			Ext.getCmp('monthInSum').setValue(monthIn);
			Ext.getCmp('monthOutSum').setValue(monthOut);
			Ext.getCmp('monthNetSum').setValue(monthNet);
		}

		// 客户家庭月度收入信息行号
		var teamrownum5 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var teamsm5 = new Ext.grid.CheckboxSelectionModel();
		// 客户家庭月度收入信息定义列模型
		var teamcm5 = new Ext.grid.ColumnModel( [ teamrownum5, {
			header : '收入类型',
			dataIndex : 'detialType',
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = detialInTypeStore.find('key', value);
					return detialInTypeStore.getAt(index).get('value');
				}

			}
		}, {
			header : '金额(元)',
			dataIndex : 'money',
			editor : new Ext.form.TextField( {
				// regex:/^[+-]?\d*\.?\d{1,2}$/
					// maskRe : '-'
					regex : /^\d*[.]?(\d{0,2})?$/
				}),
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}

		} ]);

		// 客户家庭月度收入信息数据存储
		var teamstore5 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!monthValue.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'detialType',
				mapping : 'DETIAL_TYPE'
			}, {
				name : 'money',
				mapping : 'MONEY'
			}, {
				name : 'flag',
				mapping : 'flag'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'infoId',
				mapping : 'INFO_ID'
			} ])
		});

		// 客户家庭月度收入信息
		var teamgrid5 = new Ext.grid.EditorGridPanel( {
			title : '收入信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore5, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm5, // 列模型
			sm : teamsm5,
			bbar : bbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		// 客户家庭月度支出信息行号
		var teamrownum6 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var teamsm6 = new Ext.grid.CheckboxSelectionModel();
		// 客户家庭月度支出信息定义列模型
		var teamcm6 = new Ext.grid.ColumnModel( [ teamrownum6, {
			header : '支出类型',
			dataIndex : 'detialType',
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = detialOutTypeStore.find('key', value);
					return detialOutTypeStore.getAt(index).get('value');
				}

			}
		}, {
			header : '金额(元)',
			dataIndex : 'money',
			editor : new Ext.form.TextField( {
				// regex:/^[+-]?\d*\.?\d{1,2}$/
					// maskRe : '-'
					regex : /^\d*[.]?(\d{0,2})?$/
				}),
			width : 180,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					return Ext.util.Format.number(value, '0,000.00');
				}
			}
		} ]);

		// 客户家庭月度支出信息数据存储
		var teamstore6 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!monthValue.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'detialType',
				mapping : 'DETIAL_TYPE'
			}, {
				name : 'money',
				mapping : 'MONEY'
			}, {
				name : 'flag',
				mapping : 'flag'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'infoId',
				mapping : 'INFO_ID'
			} ])
		});

		// 客户家庭月度支出信息
		var teamgrid6 = new Ext.grid.EditorGridPanel( {
			title : '支出信息',
			height : 300,
			frame : true,
			overflow : 'auto',
			autoScroll : true,
			store : teamstore6, // 数据存储
			stripeRows : true, // 斑马线
			cm : teamcm6, // 列模型
			sm : teamsm6,
			bbar : bbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		// 客户家庭支出信息计算表单
		var teamForm3 = new Ext.form.FormPanel(
				{
					labelWidth : 90, // 标签宽度
					frame : true, // 是否渲染表单面板背景色
					labelAlign : 'middle', // 标签对齐方式
					// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
					buttonAlign : 'center',
					height : 93,
					items : [ {
						layout : 'column',
						border : false,
						items : [ {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '收入合计',
								id : 'monthInSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '支出合计',
								id : 'monthOutSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [ {
								fieldLabel : '盈余',
								id : 'monthNetSum',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle : 'text-align:right;',
								anchor : '90%',
								readOnly : true
							} ]
						} ]
					} ],
					buttons : [
							{
								text : '资产统计',
								id : 'home_sum',
								handler : function() {
									Ext.getCmp('home_sum').focus();
									sum3();
									Ext.MessageBox.alert('家庭月度收支', '统计成功！');
								}

							},
							{
								text : '保存',
								id : 'home_save',
								handler : function() {
									Ext.getCmp('home_save').focus();
									sum3();
									var inInfo = new Array();
									var outInfo = new Array();
									var i = 0;
									teamstore5.each(function(item) {
										inInfo[i] = item.data.custId + ":"
												+ item.data.flag + ":"
												+ item.data.detialType + ":"
												+ item.data.money + ":"
												+ item.data.infoId;
										i += 1;
									});
									i = 0;
									teamstore6.each(function(item) {
										outInfo[i] = item.data.custId + ":"
												+ item.data.flag + ":"
												+ item.data.detialType + ":"
												+ item.data.money + ":"
												+ item.data.infoId;
										i += 1;
									});

									Ext.Ajax
											.request( {
												url : basepath + '/FinancialAnalysis!custIoSaveOrUpdate.json',
												form : teamForm2.form.id,
												method : 'POST',
												params : {
													assetInfo : inInfo,
													debtInfo : outInfo

												},
												failure : function(form, action) {
													Ext.MessageBox
															.alert('家庭月度收支信息',
																	'保存失败！');

												},
												success : function(response) {
													Ext.MessageBox
															.alert('家庭月度收支信息',
																	'保存成功！');

												}
											});
								}
							} ]
				});

		// 客户家庭月度收支信息面板
		var tab_4 = new Ext.Panel( {
			layout : 'form',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					items : [ teamgrid5 ]
				}, {
					columnWidth : .5,
					items : [ teamgrid6 ]
				} ]
			}, teamForm3 ]
		});

		function load2() {
			if (tabs.getActiveTab().title == '客户他行资产负债信息') {
				teamForm.getForm().reset();
				teamstore.load( {
					params : {
						BELONG_TYPE : 1,
						ASSET_DEBT_TYPE : 1,
						CUST_ID : custId,
						ASSETS_TYPE : 'OB_ASSETS_TYPE'
					}
				});

				teamstore2.load( {
					params : {
						BELONG_TYPE : 1,
						ASSET_DEBT_TYPE : 2,
						CUST_ID : custId,
						ASSETS_TYPE : 'OB_LAIB_TYPE'
					},
					callback : function(){
						Ext.getCmp('other_sum').focus();
						sum();
					}
				});
			} else if (tabs.getActiveTab().title == '客户其他资产负债信息') {
				teamForm2.getForm().reset();
				teamstore3.load( {
					params : {
						BELONG_TYPE : 2,
						ASSET_DEBT_TYPE : 1,
						CUST_ID : custId,
						ASSETS_TYPE : 'O_ASSETS_TYPE'
					}
				});

				teamstore4.load( {
					params : {
						BELONG_TYPE : 2,
						ASSET_DEBT_TYPE : 2,
						CUST_ID : custId,
						ASSETS_TYPE : 'O_LAIB_TYPE'
					},
					callback : function(){
						Ext.getCmp('another_sum').focus();
						sum2();
					}
				});
			} else if (tabs.getActiveTab().title == '客户家庭月度收支信息') {
				teamForm3.getForm().reset();
				teamstore5.load( {
					params : {
						IO_TYPE : '1',
						CUST_ID : custId
					}
				});

				teamstore6.load( {
					params : {
						IO_TYPE : '2',
						CUST_ID : custId
					},
					callback : function(){
						Ext.getCmp('home_sum').focus();
						sum3();
					}
				});
			} else if (tabs.getActiveTab().title == '财务分析') {
				load(custId);
			} else if (tabs.getActiveTab().title == '财务指标分析') {

				store_form_set_1.load( {
					params : {
						CUST_ID : custId
					}
				});
			}
		}
		var sm_form_set_1 = new Ext.grid.CheckboxSelectionModel();
		var cm_form_set_1 = new Ext.grid.ColumnModel( [ {
			header : '指标名称',
			dataIndex : 'code',
			width : 200,
			sortable : true,
			renderer : function(value) {
				if (value != '') {
					var index = finIndexStore.find('key', value);
					return finIndexStore.getAt(index).get('value');
				}
			}
		}, {
			header : '现值',
			width : 150,
			dataIndex : 'value',
			align : 'right',
			sortable : true,
			renderer : function(value) {

				// 增加财务指标页面的总资产自有权益比例和总资产负债比的%比格式 修改人兰超 2012-07-26

			if (value != '') {
			if (value.substring(value.length - 1) == '%') {
				value = value.substring(0, value.length - 1);

				return Ext.util.Format.number(value, '00.00%');
			} else if (value == 'Infinity' || value == 'NaN') {
				return "无";
			} else if (value == '#1#') {
				return "无";
			} else if (value == '#2#') {
				return "无负债";
			} else if (value == '#3#') {
				return "无收入";
			} else {

				return Ext.util.Format.number(value, '0,000.00');
			}
		}

	}
		},
		/*
		 * { header : '建议', width : 280, dataIndex : 'proposal', editor : new
		 * Ext.form.Field(), sortable : true },
		 */{
			header : '说明',
			width : 500,
			dataIndex : 'meaning',
			renderer : function(value, meta, record) {
				meta.attr = 'style="white-space:normal;"';
				return value;
			}
		} ]);

		// 客户财务指标信息 数据存储
		var store_form_set_1 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/FinancialAnalysis!findFinIndex.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'code',
				mapping : 'CODE'
			}, {
				name : 'meaning',
				mapping : 'MEANING'
			}, {
				name : 'proposal',
				mapping : 'PROPOSAL'
			}, {
				name : 'value',
				mapping : 'VALUE'
			} ])
		});

		// 表格实例
		var grid_form_set_1 = new Ext.grid.EditorGridPanel( {
			height : 300,
			frame : true,
			border : true,
			overflow : 'auto',
			autoScroll : true,
			store : store_form_set_1, // 数据存储
			// stripeRows : true, // 斑马线
			cm : cm_form_set_1, // 列模型
			sm : sm_form_set_1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		var form_set_1 = new Ext.form.FieldSet( {
			title : '客户指标信息',
			items : [ grid_form_set_1 ]
		});
		var tab_5 = new Ext.form.FormPanel(
				{
					height : 400,
					buttonAlign : "center",
					frame : true,
					items : [ form_set_1 ],
					buttons : [
							{
								text : '生成诊断书',
								id : 'sczds_Id',
								disabled : true,
								handler : function() {

									print();
								}
							},
							{
								text : '保存',
								handler : function() {

									sum();
									var finIndex = new Array();
									var i = 0;
									store_form_set_1.each(function(item) {
										finIndex[i] = item.data.code + ":"
												+ item.data.proposal;
										i += 1;
									});
									Ext.Ajax
											.request( {
												url : basepath + '/FinancialAnalysis!finIndexSaveOrUpdate.json',
												form : teamForm.form.id,
												method : 'POST',
												params : {
													finIndex : finIndex,
													CUST_ID : custId
												},
												failure : function(form, action) {
													Ext.MessageBox.alert(
															'财务指标分析', '提交失败！');

												},
												success : function(response) {
													Ext.MessageBox.alert(
															'财务指标分析', '提交成功！');
													Ext.getCmp('sczds_Id')
															.enable();
												}
											});

								}
							} ]
				});

		var tabs = new Ext.TabPanel( {
			defaults : {
				overflow : 'auto',
				autoScroll : true
			},
			activeTab : 0,
			items : [ {
				title : '客户他行资产负债信息',
				items : [ tab_1 ]

			}, {
				title : '客户其他资产负债信息',
				items : [ tab_2 ]
			}, {
				title : '客户家庭月度收支信息',
				items : [ tab_4 ]
			}, {
				title : '财务分析',
				items : [ tab_3 ]
			}, {
				title : '财务指标分析',
				items : [ tab_5 ]
			} ],
			listeners : {
				'tabchange' : function() {
					load2();
				}
			}
		});

		var infoWin = new Ext.Window( {
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
			layout : 'fit',
			width : 980,
			height : 460,
			buttonAlign : "center",
			title : '信息详情',
			items : [ tabs ]
		});

		// 表格工具栏
		var tbar = new Ext.Toolbar(
				{
					items : [ {
						text : '财务健康诊断',
						iconCls : 'taskDistrIconCss',
						handler : function() {
							teamForm.form.reset();
							teamForm2.form.reset();
							teamForm3.form.reset();
							var records = riskGrid.getSelectionModel()
									.getSelections();
							var recordsLen = records.length;
							if (recordsLen != 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行财务健康诊断！");
								return;
							} else {
								var record = riskGrid.getSelectionModel()
										.getSelected();
								custId = record.get('custId');

								infoWin.show();
								Ext.getCmp('sczds_Id').disable();
								load2();
							}
						}
					} ]
				});

		var riskGrid = new Ext.grid.GridPanel( {
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
		riskGrid.on("celldblclick", function() {

			teamForm.form.reset();
			teamForm2.form.reset();
			teamForm3.form.reset();
			var records = riskGrid.getSelectionModel().getSelections();
			var recordsLen = records.length;
			if (recordsLen != 1) {
				Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行财务健康诊断！");
				return;
			} else {
				var record = riskGrid.getSelectionModel().getSelected();
				custId = record.get('custId');

				infoWin.show();
				Ext.getCmp('sczds_Id').disable();
				load2();
			}

		});

		/*******************整体显示布局******************/
		var viewport = new Ext.Viewport({
			layout : 'fit',
			frame : true,
			items : [{
				layout:'border',
				items:[simple,riskGrid]
			}]
		});
		
		function print() {

			Ext.Ajax
					.request( {
						url : basepath
								+ '/FinancialAnalysis!assetXml.json?customerId='
								+ custId,
						method : 'GET',
						success : function(response) {
							var param = '';
							var r = Ext.util.JSON.decode(response.responseText).valueMap;
							var health_cust_id = custId;
							param += '&health_cust_id=' + health_cust_id;
							var asset_sum = r.assetSum;
							param += '&asset_sum=' + asset_sum;
							var debt_sum = r.debtSum;
							param += '&debt_sum=' + debt_sum;
							var net_asset = r.newAsset;
							param += '&net_asset=' + net_asset;
							var month_in = r.monthIn;
							param += '&month_in=' + month_in;
							var month_out = r.monthOut;
							param += '&month_out=' + month_out;
							// 增加盈余参数 修改人兰超 2012-07-26
							var month_net = r.monthNet;
							param += '&month_net=' + month_net;
							var win = new Ext.Window(

									{
										title : '客户敏感信息,导出有风险',
										layout : 'fit',
										width : 700,
										height : 450,
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
										buttonAlign : 'center',
										items : [ {

											header : false,
											html : '<iframe src="'
													+ basepath
													+ '/reportJsp/print/showReport.jsp?raq=/financialHealth.raq'
													+ param
													+ '\" frameborder="0" scrolling="yes" id="setframe" name="setframe" width="100%" height="100%"></iframe>',
											border : false
										} ]

									});
							win.show();

						},
						failure : function(response) {
						}
					});
		}
		Mis.Ext.FormatCnMoney = function(v) {
			return Ext.util.Format.number(v, '0,000.00');
		};
	});