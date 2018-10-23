Ext.onReady(function() {

	var title_result = null;
	var sum_flag = false;
	// 计算评级得分
		function sum() {
			sum_flag = false;
			var rdName = null;
			var title = null;
			var rs = null;
			var adjustNumber = 0;
			title_result = '';
			var rsCount = 0;
			title_count = title_store.getCount();
			for ( var j = 0; j < title_count; j++) {
				title = title_store.getAt(j);
				if (Ext.getCmp('rg' + j).getValue() != null) {
					rdName = Ext.getCmp('rg' + j).getValue().inputValue;
					for ( var k = 0; k < title.json.titleIdL.length; k++) {
						rs = title.json.titleIdL[k];
						if (rs.resultId == rdName) {
							rsCount = parseFloat(rsCount)
									+ parseFloat(rs.resultScoring);
						}
					}
				}
			}
			for ( var i = 0; i < title_count; i++) {
				if (Ext.getCmp('rg' + i).getValue() != null) {
					var titleId = Ext.getCmp('rg' + i).name;
					rsId = Ext.getCmp('rg' + i).getValue().inputValue;
					var rsScore;
					if (rsId != '') {
						for ( var j = 0; j < title_count; j++) {
							title = title_store.getAt(j);
							for ( var k = 0; k < title.json.titleIdL.length; k++) {
								rs = title.json.titleIdL[k];
								if (rs.resultId == rsId) {
									rsScore = parseFloat(rs.resultScoring);
								}
							}
						}
						title_result += titleId + ':' + rsId + ':' + rsScore;
						if (i != title_count - 1) {
							title_result += ',';
						}
					}
				} else {
					Ext.Msg.alert("提示", "您还有:"
							+ Ext.getCmp('rg' + i).fieldLabel + " 问题没回答！");
					Ext.getCmp('rg' + i).focus();
					return;
				}
			}

			sum_flag = true;
			Ext.getCmp('indageteQaScoring').setValue(rsCount);

			var typeCode;
			var i = 0;
			riskParamStore.each(function(item) {
				var initScore = item.data.initScore;
				var endScore = item.data.endScore;
				var nameParam = item.data.nameParam;
				if (parseFloat(rsCount) >= item.data.initScore
						&& parseFloat(rsCount) <= item.data.endScore) {
					typeCode = item.data.nameParam;
				}

				i += 1;
			});
			if (typeCode == '' || typeCode == null) {
				typeCode = '5';
			}
			Ext.getCmp('riskCharactTypeCode').setValue(typeCode);
			var index = riskCharactStore.find('key', typeCode);
			var resultTypeStr = riskCharactStore.getAt(index).get('value');
			Ext.getCmp('riskCharactType').setValue(resultTypeStr);

			return "sucess";
		}

		// 保存评估结果
		function save() {

			var lmsdf = new Ext.LoadMask(Ext.get(resultWin.getEl()), {// 定义遮屏到body节点上
						msg : '正在加载表格数据,请稍等...',
						removeMask : true
					});
			lmsdf.show();

			Ext.Ajax.request( {
				url : basepath + '/RiskEvaluation!addCustRiskEvaluation.json',
				form : resultForm.form.id,
				method : 'POST',
				params : {
					title_result : title_result
				},
				failure : function(form, action) {
					Ext.MessageBox.alert('在线评估', '评估失败！');
					sum_flag = false;
					lmsdf.hide();
				},
				success : function(response) {
					lmsdf.hide();
					Ext.MessageBox.alert('在线评估', '评估成功！');
					sum_flag = false;
					Ext.getCmp('score_count2').hide();
					//Ext.getCmp('score_count3').show();
					store.load( {
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
				}
			});
		}

		

		// 问卷
		var title_record = Ext.data.Record.create( [ {
			name : 'titleId',
			mapping : 'TITLE_ID'
		}, {
			name : 'titleName',
			mapping : 'TITLE_NAME'
		}, {
			name : 'titleRemark',
			mapping : 'TITLE_REMARK'
		}, {
			name : 'titleIdL',
			mapping : 'titleId'
		}, {
			name : 'qaId',
			mapping : 'QA_ID'
		} ]);

		var riskParamStore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/RiskQuession!findRiskParam.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [ {
				name : 'nameParam',
				mapping : 'PARAM_NAME'
			}, {
				name : 'initScore',
				mapping : 'INIT_SCORE'
			}, {
				name : 'endScore',
				mapping : 'END_SCORE'
			} ])
		});
		riskParamStore.load();

		var title_store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/TitleQuery!loadTitleRs.json'
			}),
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',

				messageProperty : 'message',
				root : 'data',
				totalProperty : 'count'
			}, title_record)
		});
		title_store.load();

		var custQuessionStore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/RiskEvaluation!loadCustRiskQa.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data'
			}, [ {
				name : 'qaTitle',
				mapping : 'qaTitle'
			}, {
				name : 'titleRemark',
				mapping : 'titleRemark'
			}, {
				name : 'custSelectContent',
				mapping : 'custSelectContent'
			}, {
				name : 'custqtId',
				mapping : 'custqtId'
			}, {
				name : 'custqId',
				mapping : 'custqId'
			}, {
				name : 'scoring',
				mapping : 'scoring'
			} ])
		});

		// 证件类型
		var certTypStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000040'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		certTypStore.load();

		// 客户风险特性
		var riskCharactStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=RISK_CHARACT'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		riskCharactStore.load();

		// 客户风险信息查询条件
		var simple = new Ext.FormPanel( {
			frame : true,
			region : 'north',
			title : "贵宾理财->风险评估",
			height : 130,
			width : '85%',
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
							anchor : '90%',
							labelSeparator:''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							labelStyle : 'text-align:right;',
							name : 'CUST_ZH_NAME',
							anchor : '90%',
							labelSeparator:''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : certTypStore,
							xtype : 'combo',
							labelStyle : 'text-align:right;',
							resizable : true,
							fieldLabel : '证件类型',
							name : 'CERT_TYPE',
							hiddenName : 'CERT_TYPE',
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
							xtype : 'textfield',
							fieldLabel : '证件号码',
							labelStyle : 'text-align:right;',
							name : 'CERT_NUM',
							anchor : '90%',
							labelSeparator:''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : riskCharactStore,
							labelStyle : 'text-align:right;',
							xtype : 'combo',
							resizable : true,
							fieldLabel : '客户风险特性',
							name : 'CUST_RISK_CHARACT',
							hiddenName : 'CUST_RISK_CHARACT',
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
							fieldLabel : '是否评估',
							name : 'IS_EVALUATE',
							hiddenName : 'IS_EVALUATE',
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
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'datefield',
							id : 'startDate',
							fieldLabel : '开始日期',
							labelStyle : 'text-align:right;',
							format : 'Y-m-d', // 日期格式化
							name : 'EVALUATE_DATE_S',
							anchor : '90%',
							labelSeparator:''
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'datefield',
							fieldLabel : '结束日期',
							id : 'endDate',
							labelStyle : 'text-align:right;',
							format : 'Y-m-d', // 日期格式化
							name : 'EVALUATE_DATE_E',
							anchor : '90%',
							labelSeparator:''
						} ]
					} ]
				} ]
			} ],
			buttonAlign : 'center',
			keys : [ {
				key : 13,
				fn : function() {
					Ext.getCmp('risk_serch').focus(true);
				},
				scope : this
			} ],
			buttons : [
					{
						text : '查询',
						id : 'risk_serch',
						handler : function() {
							var startDate = Ext.getCmp('startDate').getValue();
							var endDate = Ext.getCmp('endDate').getValue();
							if (startDate > endDate && startDate != ''
									&& endDate != '') {
								Ext.Msg.alert("系统提示信息", "开始日期不能大于结束日期！");
								return;
							} else {
								store.load( {
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
							}
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
		var cm = new Ext.grid.ColumnModel( [ rownum, {
			header : '核心客户号',
			dataIndex : 'sourceCustId',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 5
		}, {
			header : '客户名称',
			dataIndex : 'custZhName',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 5
		}, {
			header : '客户风险特性',
			dataIndex : 'custRiskCharact',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 110) / 5,
			renderer : function(value) {
				if (value != '') {
					var index = riskCharactStore.find('key', value);
					//return riskCharactStore.getAt(index).get('value');
					if(index != -1)
						return riskCharactStore.getAt(index).get('value');
					else
						return value;
				}

			}
		}, {
			header : '评估人',
			dataIndex : 'evaluateName',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 5
		},{
			header : '评估日期',
			dataIndex : 'evaluateDate',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 5
		}, {
			header : '有效期截至日期',
			dataIndex : 'limitDate',
			sortable : true,
			menuDisabled : true,
			width : (document.body.scrollWidth - 50) / 5
		}, {
			header : 'crm_id',
			dataIndex : 'custId',
			menuDisabled : true,
			hidden : true
		}, {
			header : 'cust_q_id',
			dataIndex : 'custQId',
			menuDisabled : true,
			hidden : true

		}, {
			header : 'indageteQaScoring',
			dataIndex : 'indageteQaScoring',
			menuDisabled : true,
			hidden : true

		} ]);
		/**
		 * 数据存储
		 */

		var store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/riskassess.json'
			}),
			listeners : {
				'load' : function() {
					lm.hide();
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
				name : 'custRiskCharact',
				mapping : 'CUST_RISK_CHARACT'
			}, {
				name : 'evaluateName',
				mapping : 'EVALUATE_NAME'
			}, {
				name : 'evaluateDate',
				mapping : 'EVALUATE_DATE'
			},{
				name: 'limitDate',
				mapping : 'LIMIT_DATE'
			}, {
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'custQId',
				mapping : 'CUST_Q_ID'
			}, {
				name : 'indageteQaScoring',
				mapping : 'INDAGETE_QA_SCORING'
			} ])
		});
		
		// 遮挡
		var lm = new Ext.LoadMask(document.body, {// 定义遮屏到body节点上
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

		var rd_set = new Ext.form.FieldSet( {
			xtype : 'fieldset',
			title : '问卷调查',
			labelWidth : 250,
			labelAlign : 'right',
			collapsible : false,
			itemCls: 'x-check-group-alt',
			items : []
		});

		// 评估结果
		var resultForm = new Ext.FormPanel( {
			frame : true,
			id : 'resultForm',
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
							xtype : 'textfield',
							fieldLabel : '核心客户号',
							labelStyle : 'text-align:right;',
							name : 'sourceCustId',
							anchor : '90%',
							id : 'sourceCustIdP',
							disabled : true,
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							labelStyle : 'text-align:right;',
							name : 'custZhName',
							anchor : '90%',
							disabled : true,
							id : 'custZhNameP',
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'riskCharactType',
							xtype : 'textfield',
							fieldLabel : '风险特性',
							labelStyle : 'text-align:right;',
							name : 'riskCharactType',
							anchor : '90%',
							disabled : true,
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'indageteQaScoring',
							xtype : 'textfield',
							fieldLabel : '评估得分',
							labelStyle : 'text-align:right;',
							name : 'indageteQaScoring',
							anchor : '90%',
							disabled : true,
							labelSeparator:''
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'source',
							xtype : 'textfield',
							fieldLabel : '系统来源',
							labelStyle : 'text-align:right;',
							anchor : '90%',
							value : "CRM系统",
							disabled : true,
							labelSeparator:''
						} ]
					}, {
						xtype : 'hidden',
						name : 'custNo',
						id : 'custNo'
					}, {
						xtype : 'hidden',
						name : 'custRiskCharact',
						id : 'riskCharactTypeCode'
					} ]
				} ]
			} ],

			buttons : [ {
				text : '提交',
				id : 'score_count2',
				handler : function() {
					Ext.getCmp('indageteQaScoring').enable();
					save();
					Ext.getCmp('indageteQaScoring').disable();
					store.load( {
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
				}
			}, 
//			{
//				text : '打印评估报告',
//				id : 'score_count3',
//				hidden:'true',
//				handler : function() {
//					print();
//				}
//			}, 
			{
				text : '重新评估',
				id : 'reset',
				handler : function() {
					opWin.show();
					resultWin.hide();

				}
			}, {
				text : '关闭',
				id : 'reset',
				handler : function() {
					resultWin.hide();

				}
			} ]
		});

		var opForm = new Ext.Panel( {
			id : 'opForm',
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
			buttonAlign : "center",
			items : [ rd_set ],
			listeners : {
				beforerender : function() {
					var title_count = null;
					var title = null;
					var title_rs = null;
					var rs = null;
					title_count = title_store.getCount();
					for ( var i = 0; i < title_count; i++) {
						title = title_store.getAt(i);
						title_rs = new Array();
						for ( var b = 0; b < title.json.titleIdL.length; b++) {
							rs = title.json.titleIdL[b];
							title_rs.push(new Ext.form.Radio( {
								boxLabel : rs.result,
								name : 'result' + i,
								inputValue : rs.resultId
							}));
						}

						new Ext.form.RadioGroup( {
							id : 'rg' + i,
							fieldLabel : title.json.titleName,
							name : title.json.titleId,
							items : [ title_rs ]
						});
						rd_set.add(Ext.getCmp('rg' + i));
						rd_set.doLayout();
					}

				}

			}

		});

		var resultWin = new Ext.Window( {
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
			id : 'resultWin',
			layout : 'fit',
			width : 600,
			height : 200,
			title : '评估结果',
			items : [ resultForm ]
		});

		

		// 表格工具栏
		var tbar = new Ext.Toolbar( {
			items : [
					{
						text : '在线评估',
						iconCls : 'copyCss',
						handler : function() {

							var records = riskGrid.getSelectionModel()
									.getSelections();
							var recordsLen = records.length;
							if (recordsLen != 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行评估！");
								return;
							} else {
								var opWin = new Ext.Window( {
									width : 900,
									height : 450,
									title : '在线评估',
									items : [ opForm ],
									layout : 'fit',
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
									buttons : [ {
										text : '提交',
										id : 'score_count',
										handler : function() {
											var flag = sum();

											if (flag == "sucess") {
												resultWin.show();
												opWin.hide();
											}
										}
									}, {
										text : '重置',
										id : 'reset_info',
										handler : function() {
											title_count = title_store.getCount();
											for ( var j = 0; j < title_count; j++) {
												Ext.getCmp('rg' + j).reset();
											}

										}
									}, {
										text : '关闭',
										handler : function() {
											title_count = title_store.getCount();
											for ( var j = 0; j < title_count; j++) {
												Ext.getCmp('rg' + j).reset();
											}

											opWin.hide();

										}
									} ]
								});

								opWin.on('hide', function() {
									title_count = title_store.getCount();
									for ( var j = 0; j < title_count; j++) {
										Ext.getCmp('rg' + j).setReadOnly(false);
										Ext.getCmp('rg' + j).reset();
									}
									sum_flag = false;
									store.reload( {
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
								});
								var record = riskGrid.getSelectionModel()
										.getSelected();
								resultForm.getForm().loadRecord(record);
								Ext.getCmp('custNo').setValue(
										record.get('custId'));

								Ext.getCmp('riskCharactType').setValue(
										record.get('custRiskCharact'));

								Ext.getCmp('score_count2').show();
								//Ext.getCmp('score_count3').hide();
								opWin.show();
							}
						}
					},
					{
						text : '查看评级记录',
						iconCls : 'dailyDetailIconCss',
						handler : function() {

							var records = riskGrid.getSelectionModel()
									.getSelections();
							var recordsLen = records.length;
							var record = riskGrid.getSelectionModel()
									.getSelected();
							if (recordsLen != 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行查看！");
								return;
							} else if (record.get('custRiskCharact') == null
									|| record.get('custRiskCharact') == '') {
								Ext.Msg.alert("系统提示信息", "该客户未评估，请先对该客户评估！");
								return;
							} else {
								resultWin.show();

								resultForm.getForm().loadRecord(record);
								Ext.getCmp('custNo').setValue(
										record.get('custId'));
								Ext.getCmp('score_count2').hide();
								//Ext.getCmp('score_count3').show();
								var index = riskCharactStore.find('key', record
										.get('custRiskCharact'));
								var resultTypeStr = riskCharactStore.getAt(
										index).get('value');
								Ext.getCmp('riskCharactType').setValue(
										resultTypeStr);
							}
							// Ext.getCmp('save_info').hide();
							// Ext.getCmp('score_count').show();
							// Ext.getCmp('reset_info').hide();

						}
					} ]
		});

		// create the Grid
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
		// ,loadMask : {
				// msg : '正在加载表格数据,请稍等...'
				// }
				});

		riskGrid.on("celldblclick", function() {

			var records = riskGrid.getSelectionModel().getSelections();
			var recordsLen = records.length;
			var record = riskGrid.getSelectionModel().getSelected();
			if (recordsLen != 1) {
				Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行查看！");
				return;
			} else if (record.get('custRiskCharact') == null
					|| record.get('custRiskCharact') == '') {
				Ext.Msg.alert("系统提示信息", "该客户未评估，请先对该客户评估！");
				return;
			} else {
				resultWin.show();

				resultForm.getForm().loadRecord(record);
				Ext.getCmp('custNo').setValue(record.get('custId'));
				Ext.getCmp('score_count2').hide();
				//Ext.getCmp('score_count3').show();
				var index = riskCharactStore.find('key', record
						.get('custRiskCharact'));
				var resultTypeStr = riskCharactStore.getAt(index).get('value');
				Ext.getCmp('riskCharactType').setValue(resultTypeStr);
			}
			// Ext.getCmp('save_info').hide();
				// Ext.getCmp('score_count').show();
				// Ext.getCmp('reset_info').hide();

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
			var cust_code = Ext.getCmp('sourceCustIdP').getValue();
			var risk_level = Ext.getCmp('riskCharactTypeCode').getValue();
			var risk_score = Ext.getCmp('indageteQaScoring').getValue();
			var cust_id = Ext.getCmp('custNo').getValue();
			var param = '&cust_code=' + cust_code + '&risk_level=' + risk_level
					+ '&risk_score=' + risk_score + '&cust_id=' + cust_id;
			var win = new Ext.Window(

					{
						title : '客户敏感信息,导出有风险',
						layout : 'fit',
						width : 700,
						height : 450,
						draggable : true,// 是否可以拖动
						closable : true,// 是否可关闭
						modal : true,
						closeAction : 'hide',
						// iconCls : 'page_addIcon',
						// maximizable: true,
						// maximized:true,
						collapsible : true,// 是否可收缩
						titleCollapse : true,
						// buttonAlign : 'right',
						border : false,
						animCollapse : true,
						pageY : 20,
						// pageX : document.body.clientWidth / 2 - 420 / 2,
						animateTarget : Ext.getBody(),
						buttonAlign : 'center',
						constrain : true,

						items : [ {
							header : false,
							html : '<iframe src="'
									+ basepath
									+ '/reportJsp/print/showReport.jsp?raq=/riskAssess.raq'
									+ param
									+ '\" frameborder="0" scrolling="yes" id="setframe" name="setframe" width="100%" height="100%"></iframe>',
							border : false
						} ]

					});
			win.show();

		}

	});