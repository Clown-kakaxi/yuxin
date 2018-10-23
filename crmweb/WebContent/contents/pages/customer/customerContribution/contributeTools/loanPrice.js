Ext
		.onReady(function() {
			Ext.QuickTips.init();

			var ftpNo;

			var ftp;

			var crmCode;

			var referenceRateValue;

			xsProxy = new Ext.data.HttpProxy({
				url : basepath + '/querylookupxs.json?crmCode=' + crmCode + '',
				method : 'GET'
			});

			var xsStore = new Ext.data.Store({
				restful : true,
				proxy : xsProxy,
				fields : [ 'code_name_1' ],
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					root : 'json.data'
				}, [ 'code_name_1' ])
			});

			var boxstore1 = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				root : 'root',
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=CCY'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			// 动态下拉框默认值
			boxstore1.on('load', function() {
				Ext.getCmp('MoneyType').setValue('CRM_CCY_001');
			});

			var boxstore2 = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=DKQX'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			// 动态下拉框默认值
			boxstore2.on('load', function() {
				Ext.getCmp('LoanLimitTime').setValue('CRM_DKQX_002');

				boxstore3.load({
					params : {
						name : Ext.getCmp('LoanLimitTime').getValue()
					}
				});
			});

			var boxstore3 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/referenceRateQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					root : 'json.data'
				}, [ 'code_name_1', 'crm_code' ])
			});

			var boxstore4 = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/economiccapitalcalculation.json'
				}),
				fields : [ 'ECONOMIC_CAPITAL_OCCUPIED', 'FA_NAME', 'FTP',
						'LOAN_ACCOUNT', 'CURRENCY', 'REFERENCE_RATE',
						'LOAN_LIMIT_TIME' ],
				reader : new Ext.data.JsonReader({
					root : 'json.data'
				}, [ {
					name : 'ECONOMIC_CAPITAL_OCCUPIED'
				}, {
					name : 'FA_NAME'
				}, {
					name : 'FTP'
				}, {
					name : 'LOAN_ACCOUNT'
				}, {
					name : 'CURRENCY'
				}, {
					name : 'REFERENCE_RATE'
				}, {
					name : 'LOAN_LIMIT_TIME'
				} ])
			});

			var boxstore5 = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/queryftp.json'
				}),
				fields : [ 'SYSTEM_FTP_NAME', 'SYSTEM_FTP_NO' ],
				reader : new Ext.data.JsonReader({
					// totalProperty : 'list',
					root : 'json.data'
				}, [ {
					name : 'SYSTEM_FTP_NAME'
				}, {
					name : 'SYSTEM_FTP_NO'
				} ])
			});

			boxstore6Proxy = new Ext.data.HttpProxy({
				url : basepath + '/queryftp1.json?ftpNo=' + ftpNo + '',
				method : 'GET'
			});

			var boxstore6 = new Ext.data.Store({
				restful : true,
				proxy : boxstore6Proxy,
				fields : [ 'SYSTEM_FTP_RMBPRICE', 'SYSTEM_FTP_OTHERPRICE' ],
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					root : 'json.data'
				}, [ 'SYSTEM_FTP_RMBPRICE', 'SYSTEM_FTP_OTHERPRICE' ])
			});

			ftp1Proxy = new Ext.data.HttpProxy({
				url : basepath + '/queryftp1.json?ftpNo=' + ftpNo + '',
				method : 'GET'
			});

			var ftp1Store = new Ext.data.Store({
				restful : true,
				// autoLoad:false,
				proxy : ftp1Proxy,
				fields : [ 'SYSTEM_FTP_RMBPRICE', 'SYSTEM_FTP_OTHERPRICE' ],
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					root : 'json.data'
				}, [ 'SYSTEM_FTP_RMBPRICE', 'SYSTEM_FTP_OTHERPRICE' ])
			});

			var qForm = new Ext.form.FormPanel(
					{
						title : '贷款定价器',
						labelWidth : 130, // 标签宽度
						frame : true, // 是否渲染表单面板背景色
						labelAlign : 'middle', // 标签对齐方式
						buttonAlign : 'center',
						region : 'north',
						split : true,
						height : 210,
						items : [
								{
									layout : 'column',
									border : false,
									items : [
											{
												columnWidth : .25,
												layout : 'form',
												defaultType : 'textfield',
												border : false,
												items : [
														{
															id : 'ID',
															fieldLabel : '序号',
															name : 'ID',
															hiddenName : 'ID',
															xtype : 'numberfield',
															hidden : true,
															readOnly : true,
															labelStyle : 'text-align:right;',
															anchor : '90%'
														},
														{
															id : 'economicCapitalOccupied',
															fieldLabel : '*经济资本占用方案名称',
															hiddenName : 'ECONOMIC_CAPITAL_OCCUPIED',
															forceSelection : true,
															xtype : 'combo',
															labelStyle : 'text-align:right;',
															triggerAction : 'all',
															mode : 'local',
															store : boxstore4,
															valueField : 'ECONOMIC_CAPITAL_OCCUPIED',
															displayField : 'FA_NAME',
															allowBlank : false,
															emptyText : '请选择',
															anchor : '90%'
														},
														{
															fieldLabel : '*经济资本成本率(%)', // 标签
															id : 'ECONOMIC_CAPITAL_COST_RATE',
															name : 'ECONOMIC_CAPITAL_COST_RATE', // name:后台根据此name属性取值
															hiddenName : 'ECONOMIC_CAPITAL_COST_RATE',
															xtype : 'numberfield', // 设置为数字输入框类型
															value : 15, // 2011年为0.15
															readOnly : true,
															allowBlank : false,
															labelStyle : 'text-align:right',
															anchor : '90%' // 宽度百分比
														},
														{
															fieldLabel : '*营销费用(万元)',
															id : 'MARKET_COST',
															name : 'MARKET_COST',
															hiddenName : 'MARKET_COST',
															xtype : 'numberfield', // 设置为数字输入框类型
															allowBlank : false,
															maxLength : 10,
															minLength : 1,
															labelStyle : 'text-align:right;',
															anchor : '90%'
														} ]
											},
											{
												columnWidth : .25,
												layout : 'form',
												defaultType : 'textfield',
												border : false,
												items : [
														{
															fieldLabel : '*贷款金额(万元)',
															id : 'LOAN_ACCOUNT',
															hiddenName : 'LOAN_ACCOUNT',
															name : 'LOAN_ACCOUNT',
															xtype : 'numberfield', // 设置为数字输入框类型
															allowBlank : false,
															readOnly : true,
															maxLength : 10,
															minLength : 1,
															labelStyle : 'text-align:right;',
															anchor : '90%'
														},
														{
															store : boxstore1,
															xtype : 'combo',
															labelStyle : 'text-align:right;',
															id : 'MoneyType',
															name : 'CURRENCY',
															hiddenName : 'CURRENCY',
															fieldLabel : '*币种',
															valueField : 'key',
															displayField : 'value',
															mode : 'local',
															typeAhead : true,
															forceSelection : true,
															triggerAction : 'all',
															selectOnFocus : true,
															readOnly : true,
															allowBlank : false,
															width : '100',
															anchor : '90%'
														},
														{
															fieldLabel : '*其他费用(万元)',
															id : 'OTHER_COST',
															name : 'OTHER_COST',
															hiddenName : 'OTHER_COST',
															xtype : 'numberfield', // 设置为数字输入框类型
															allowBlank : false,
															maxLength : 10,
															minLength : 1,
															labelStyle : 'text-align:right;',
															anchor : '90%'
														} ]
											},
											{
												columnWidth : .25,
												layout : 'form',
												defaultType : 'textfield',
												border : false,
												items : [
														{
															store : boxstore2,
															xtype : 'combo',
															labelStyle : 'text-align:right;',
															id : 'LoanLimitTime',
															name : 'LOAN_LIMIT_TIME',
															hiddenName : 'LOAN_LIMIT_TIME',
															fieldLabel : '*贷款期限',
															value : 'CRM_DKQX_002',
															valueField : 'key',
															displayField : 'value',
															mode : 'local',
															typeAhead : true,
															forceSelection : true,
															triggerAction : 'all',
															selectOnFocus : true,
															readOnly : true,
															allowBlank : false,
															width : '100',
															anchor : '90%'
														},
														{
															store : boxstore5,
															xtype : 'combo',
															labelStyle : 'text-align:right;',
															id : 'ftpinfo',
															name : 'FTP',
															hiddenName : 'FTP',
															fieldLabel : '*FTP',
															valueField : 'SYSTEM_FTP_NO',
															displayField : 'SYSTEM_FTP_NAME',
															mode : 'local',
															typeAhead : true,
															forceSelection : true,
															emptyText : '请选择',
															triggerAction : 'all',
															selectOnFocus : true,
															allowBlank : false,
															readOnly : true,
															width : '100',
															anchor : '90%'
														} ]
											},
											{
												columnWidth : .25,
												layout : 'form',
												defaultType : 'textfield',
												border : false,
												items : [
														{
															xtype : 'numberfield',
															decimalPrecision : 4,
															labelStyle : 'text-align:right;',
															id : 'referenceRate',
															name : 'REFERENCE_RATE',
															hiddenName : 'REFERENCE_RATE',
															readOnly : true,
															fieldLabel : '*基准利率(%)',
															allowBlank : false,
															anchor : '90%'
														},
														{
															fieldLabel : '*FTP值(%)', // 标签
															id : 'ftpvalue',
															name : 'FTP_VALUE', // name:后台根据此name属性取值
															hiddenName : 'FTP_VALUE',
															xtype : 'numberfield', // 设置为数字输入框类型
															allowBlank : false,
															readOnly : true,
															labelStyle : 'text-align:right;',
															anchor : '90%' // 宽度百分比
														} ]
											} ]
								},
								{
									xtype : 'fieldset',
									title : '测算结果',
									items : [ new Ext.Panel(
											{
												layout : 'column',
												border : false,
												items : [
														{
															columnWidth : .25,
															layout : 'form',
															items : [ {
																fieldLabel : '目标利润(万元)', // 标签
																id : 'TARGET_PROFIT',
																name : 'TARGET_PROFIT', // name:后台根据此name属性取值
																hiddenName : 'TARGET_PROFIT',
																xtype : 'numberfield', // 设置为数字输入框类型
																labelStyle : 'text-align:right;',
																anchor : '90%' // 宽度百分比
															} ]
														},
														{
															columnWidth : .25,
															layout : 'form',
															items : [ {
																fieldLabel : '目标收益率(%)', // 标签
																id : 'TARGET_PROFIT_RATE',
																name : 'TARGET_PROFIT_RATE', // name:后台根据此name属性取值
																hiddenName : 'TARGET_PROFIT_RATE',
																xtype : 'numberfield', // 设置为数字输入框类型
																labelStyle : 'text-align:right;',
																anchor : '90%' // 宽度百分比
															} ]
														},
														{
															columnWidth : .25,
															layout : 'form',
															items : [ {
																id : 'ESTIMATE_PRICE',
																fieldLabel : '<span style="color:red" >估算价格(%)</span>',
																name : 'ESTIMATE_PRICE',
																hiddenName : 'ESTIMATE_PRICE',
																xtype : 'numberfield', // 设置为数字输入框类型
																readOnly : true,
																labelStyle : 'text-align:right;',
																anchor : '90%'
															} ]
														},
														{
															columnWidth : .25,
															layout : 'form',
															items : [ {
																id : 'INTEREST_FLOAT_RATE',
																fieldLabel : '<span style="color:red" >贷款利率浮动比例(%)</span>',
																name : 'INTEREST_FLOAT_RATE',
																hiddenName : 'INTEREST_FLOAT_RATE',
																xtype : 'numberfield', // 设置为数字输入框类型
																readOnly : true,
																labelStyle : 'text-align:right;',
																anchor : '90%'
															} ]
														} ]
											}) ]
								} ],
						buttons : [
								{
									text : '计算',
									handler : function() {

										if (!qForm.getForm().isValid()) {
											Ext.Msg.alert('提示', '输入有误，请重新输入!');
											return false;
										}

										if (Ext.getCmp('TARGET_PROFIT')
												.getValue() != ''
												&& Ext.getCmp(
														'TARGET_PROFIT_RATE')
														.getValue() != '') {
											Ext.Msg
													.alert('提示',
															'利润利润和目标收益率必须且只可以输入一项，请重新输入!');
											return false;
										}

										if (Ext.getCmp('TARGET_PROFIT')
												.getValue() == ''
												&& Ext.getCmp(
														'TARGET_PROFIT_RATE')
														.getValue() == '') {
											Ext.Msg
													.alert('提示',
															'利润利润和目标收益率必须且只可以输入一项，请重新输入!');
											return false;
										}

										// 贷款金额
										var LOAN_ACCOUNT = parseFloat(Ext
												.getCmp('LOAN_ACCOUNT')
												.getValue());

										// 目标利润
										var TARGET_PROFIT;

										if (Ext.getCmp('TARGET_PROFIT')
												.getValue() != "") {
											TARGET_PROFIT = parseFloat(Ext
													.getCmp('TARGET_PROFIT')
													.getValue());
										} else {
											TARGET_PROFIT = parseFloat(Ext
													.getCmp(
															'TARGET_PROFIT_RATE')
													.getValue())
													* parseFloat(Ext.getCmp(
															'LOAN_ACCOUNT')
															.getValue() / 100);
											Ext.getCmp('TARGET_PROFIT')
													.setValue(TARGET_PROFIT);
										}

										// 经济资本占用
										var ECONOMIC_CAPITAL_OCCUPIED = parseFloat(Ext
												.getCmp(
														'economicCapitalOccupied')
												.getValue());

										// 经济资本成本率
										var ECONOMIC_CAPITAL_COST_RATE = parseFloat(Ext
												.getCmp(
														'ECONOMIC_CAPITAL_COST_RATE')
												.getValue());

										// 营销费用
										var MARKET_COST = parseFloat(Ext
												.getCmp('MARKET_COST')
												.getValue());

										// 其他费用
										var OTHER_COST = parseFloat(Ext.getCmp(
												'OTHER_COST').getValue());

										// 目标收益率=目标利润÷贷款金额
										var TARGET_PROFIT_RATE;

										if (Ext.getCmp('TARGET_PROFIT_RATE')
												.getValue() != "") {
											TARGET_PROFIT_RATE = parseFloat(Ext
													.getCmp(
															'TARGET_PROFIT_RATE')
													.getValue());
										} else {
											TARGET_PROFIT_RATE = TARGET_PROFIT
													/ LOAN_ACCOUNT * 100;
											Ext.getCmp('TARGET_PROFIT_RATE')
													.setValue(
															TARGET_PROFIT_RATE);
										}
										// ftp
										var FTP = parseFloat(Ext.getCmp(
												'ftpvalue').getValue());

										// 估算价格
										var ESTIMATE_PRICE = ((TARGET_PROFIT
												+ ECONOMIC_CAPITAL_OCCUPIED
												* ECONOMIC_CAPITAL_COST_RATE
												/ 100 + MARKET_COST + OTHER_COST)
												/ LOAN_ACCOUNT + FTP / 100)
												/ (1 - 0.055) * 100;
										Ext.getCmp('ESTIMATE_PRICE').setValue(
												ESTIMATE_PRICE);

										// 基准利率
										var REFERENCE_RATE = parseFloat(Ext
												.getCmp('referenceRate')
												.getRawValue());

										// 贷款利率浮动比例 = 估算价格/基准利率-1
										var INTEREST_FLOAT_RATE = (ESTIMATE_PRICE
												/ REFERENCE_RATE - 1);
										Ext.getCmp('INTEREST_FLOAT_RATE')
												.setValue(INTEREST_FLOAT_RATE);
									}
								},
								{
									text : '保存',
									handler : function() {

										if (!qForm.getForm().isValid()) {
											Ext.Msg.alert('提示', '请填写必填信息!');
											return false;
										}

										if (Ext.getCmp('TARGET_PROFIT')
												.getValue() == ''
												|| Ext.getCmp(
														'TARGET_PROFIT_RATE')
														.getValue() == '') {
											Ext.Msg.alert('提示',
													'利润利润或目标收益率不能为空，请重新输入!');
											return false;
										}

										qForm.getForm().getValues(false);
										referenceRateValue = Ext.getCmp(
												'referenceRate').getValue();
										Ext.getCmp('referenceRate').setValue(
												Ext.getCmp('referenceRate')
														.getRawValue());
										Ext.Ajax
												.request({
													url : basepath
															+ '/customerloanprice.json',
													method : 'POST',
													form : qForm.getForm().id,
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : checkResult,
													failure : checkResult,
													params : {
														'operate' : 'add'
													}
												});
									}
								}, {
									text : '清空',
									handler : function() {
										qForm.getForm().reset();
									}
								} ]
					});

			Ext.getCmp('LoanLimitTime').on('select', function() {

				boxstore3.load({
					params : {
						name : Ext.getCmp('LoanLimitTime').getValue()
					}
				})

			});

			// 设置ftp的值
			Ext
					.getCmp('ftpinfo')
					.addListener(
							"select",
							function() {
								if (Ext.getCmp('ftpinfo').getValue() != '') {
									ftpNo = Ext.getCmp('ftpinfo').getValue();
									boxstore6Proxy.setApi(
											Ext.data.Api.actions.read, basepath
													+ '/queryftp1.json?ftpNo='
													+ ftpNo + '');
									boxstore6
											.load({
												callback : function() {
													if (Ext.getCmp('MoneyType')
															.getValue() == 'CRM_CCY_001') {
														ftp = parseFloat(boxstore6
																.getAt(0).data.SYSTEM_FTP_RMBPRICE);
													} else {
														ftp = parseFloat(boxstore6
																.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
													}
													Ext.getCmp('ftpvalue')
															.setValue(ftp);
												}
											});
								}
							});

			function checkResult(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				Ext.getCmp('referenceRate').setValue(referenceRateValue);
				if ((resultArray == 200 || resultArray == 201)
						&& resultError == '') {
					Ext.Msg.alert('提示', '操作成功');
					store.reload({
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
				} else {
					Ext.Msg.alert('提示', '操作失败');
					store.reload({
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
				}
			}

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
				name : 'LOAN_ACCOUNT'
			}, {
				name : 'CURRENCY'
			}, {
				name : 'LOAN_LIMIT_TIME'
			}, {
				name : 'FTP'
			}, {
				name : 'FTP_ORA'
			}, {
				name : 'FTP_VALUE'
			}, {
				name : 'MARKET_COST'
			}, {
				name : 'OTHER_COST'
			}, {
				name : 'ECONOMIC_CAPITAL_OCCUPIED'
			}, {
				name : 'TARGET_PROFIT'
			}, {
				name : 'TARGET_PROFIT_RATE'
			}, {
				name : 'INTEREST_FLOAT_RATE'
			}, {
				name : 'REFERENCE_RATE'
			}, {
				name : 'CALCULATE_TIME'
			}, {
				name : 'CUST_ID'
			}, {
				name : 'ORG_ID'
			}, {
				name : 'ESTIMATE_PRICE'
			}, {
				name : 'CURRENCY_GP'
			}, {
				name : 'LOAN_LIMIT_TIME_GP'
			} ]);

			// 定义列模型

			var cm = new Ext.grid.ColumnModel(
					[
							rownum,
							sm,
							{
								header : '序号',
								dataIndex : 'ID',
								hidden : true
							},
							{
								header : '贷款金额(万元)',
								width : 100,
								dataIndex : 'LOAN_ACCOUNT',
								align : 'right',
								renderer : money('0,000.00')
							},
							{
								header : '币种',
								width : 130,
								dataIndex : 'CURRENCY_GP',
								align : 'center'
							},
							{
								header : '贷款期限',
								width : 130,
								dataIndex : 'LOAN_LIMIT_TIME_GP',
								align : 'center'
							},
							{
								header : 'FTP',
								width : 250,
								dataIndex : 'FTP_ORA',
								renderer : function(value) {
									if (boxstore5.getCount() == 0) {
										boxstore5
												.load({
													callback : function() {
														var index = boxstore5
																.find(
																		'SYSTEM_FTP_NO',
																		value);
														if (index != -1) {
															value = boxstore5
																	.getAt(index).data.SYSTEM_FTP_NAME;
														}
														store
																.reload({
																	params : {
																		start : 0,
																		limit : bbar.pageSize
																	}
																});
														return value;
													}
												});
									} else {
										var index = boxstore5.find(
												'SYSTEM_FTP_NO', value);
										if (index != -1) {
											value = boxstore5.getAt(index).data.SYSTEM_FTP_NAME;
										}
										return value;
									}
								},
								align : 'center'
							}, {
								header : 'FTP值(%)',
								width : 60,
								dataIndex : 'FTP_VALUE',
								align : 'right',
								renderer : ratePercent(false)
							}, {
								header : '营销费用(万元)',
								width : 100,
								dataIndex : 'MARKET_COST',
								align : 'right',
								renderer : money('0,000.00')
							}, {
								header : '其他费用(万元)',
								width : 100,
								dataIndex : 'OTHER_COST',
								align : 'right',
								renderer : money('0,000.00')
							}, {
								header : '经济资本占用(万元)',
								width : 130,
								dataIndex : 'ECONOMIC_CAPITAL_OCCUPIED',
								align : 'right',
								renderer : money('0,000.00')
							}, {
								header : '目标收益率(%)',
								width : 100,
								dataIndex : 'TARGET_PROFIT_RATE',
								align : 'right',
								renderer : ratePercent(false)
							}, {
								header : '目标利润(万元)',
								width : 100,
								dataIndex : 'TARGET_PROFIT',
								align : 'right',
								renderer : money('0,000.00')
							}, {
								header : '基准利率(%)',
								width : 100,
								dataIndex : 'REFERENCE_RATE',
								align : 'right',
								renderer : ratePercent(false)
							}, {
								header : '估算价格(%)',
								width : 100,
								dataIndex : 'ESTIMATE_PRICE',
								align : 'right',
								renderer : ratePercent(false)
							}, {
								header : '贷款利率浮动比例(%)',
								width : 130,
								dataIndex : 'INTEREST_FLOAT_RATE',
								align : 'right',
								renderer : ratePercent(false)
							}, {
								header : '测算日期时间',
								width : 100,
								dataIndex : 'CALCULATE_TIME',
								align : 'center'
							}, {
								header : '用户ID',
								width : 60,
								dataIndex : 'CUST_ID',
								align : 'center'
							}, {
								header : '机构号',
								width : 60,
								dataIndex : 'ORG_ID',
								align : 'center'
							} ]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/querycustomerloanprice.json'
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

			// 表格工具栏
			var tbar = new Ext.Toolbar({
				items : [ {
					text : '修改',
					handler : function() {
						editInit();
					}
				}, '-', {
					text : '删除',
					handler : function() {
						deleteInit();
					}
				} ]
			});

			function editInit() {

				var selectLength = grid.getSelectionModel().getSelections().length;

				var infoRecord = grid.getSelectionModel().getSelected();
				if (selectLength != 1) {
					Ext.Msg.alert('提示', '请选择一条记录!');
				} else {
					qForm.getForm().loadRecord(infoRecord);
				}
			}

			function deleteInit() {

				var selectLength = grid.getSelectionModel().getSelections().length;

				if (selectLength < 1) {
					Ext.Msg.alert('提示', '请选择需要删除的记录!');
				}

				else {
					Ext.MessageBox.confirm('提示', '确定删除吗?',
							function(buttonId) {
								if (buttonId.toLowerCase() == "no") {
									return;
								}
								var selectRe;
								var tempId;
								var idStr = '';
								for ( var i = 0; i < selectLength; i++) {
									selectRe = grid.getSelectionModel()
											.getSelections()[i];
									tempId = selectRe.data.ID;
									idStr += tempId;
									if (i != selectLength - 1)
										idStr += ',';
								}
								Ext.Ajax.request({
									url : basepath + '/customerloanprice/'
											+ tempId + '.json?idStr=' + idStr,
									method : 'DELETE',
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : checkResult,
									failure : checkResult
								});

							});
				}
			}

			/** ***************************************************************** */
			// 基准利率获取
			var cb5 = Ext.getCmp('LoanLimitTime');
			cb5
					.addListener(
							"select",
							function() {
								if (cb5.getValue() != '') {
									crmCode = cb5.getValue();
									xsProxy
											.setApi(
													Ext.data.Api.actions.read,
													basepath
															+ '/querylookupxs.json?crmCode='
															+ crmCode + '');
									xsStore
											.load({
												callback : function() {
													if (!xsStore.getCount() == 0) {
														Ext
																.getCmp(
																		'referenceRate')
																.setValue(
																		parseFloat(xsStore
																				.getAt(0).data.code_name_1));
													}
												}
											});
								}
							});

			if (cb5.getValue() != '') {
				crmCode = cb5.getValue();
				xsProxy.setApi(Ext.data.Api.actions.read, basepath
						+ '/querylookupxs.json?crmCode=' + crmCode + '');
				xsStore
						.load({
							callback : function() {
								if (!xsStore.getCount() == 0) {
									Ext
											.getCmp('referenceRate')
											.setValue(
													parseFloat(xsStore.getAt(0).data.code_name_1));
								}
							}
						});
			}

			var cb6 = Ext.getCmp('ftpinfo');
			cb6
					.addListener(
							"select",
							function() {
								if (cb6.getValue() != '') {
									ftpNo = cb6.getValue();
									ftp1Proxy.setApi(Ext.data.Api.actions.read,
											basepath + '/queryftp1.json?ftpNo='
													+ ftpNo + '');
									ftp1Store
											.load({
												callback : function() {
													if (Ext.getCmp('MoneyType')
															.getValue() == 'CRM_CCY_001') {
														if (ftp1Store
																.getCount() != 0) {
															ftp = parseFloat(ftp1Store
																	.getAt(0).data.SYSTEM_FTP_RMBPRICE);
														}
													} else {
														if (ftp1Store
																.getCount() != 0) {
															ftp = parseFloat(ftp1Store
																	.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
														}
													}
													Ext.getCmp('ftpvalue')
															.setValue(ftp);
												}
											});
								}
							});

			/** ****************************************************************** */
			var cb7 = Ext.getCmp('economicCapitalOccupied');
			cb7
					.addListener(
							"select",
							function() {
								if (cb7.getValue() != '') {
									economicCapitalOccupied = cb7.getValue();
									var index = boxstore4.find('FA_NAME', cb7
											.getRawValue());
									if (index != -1) {
										Ext
												.getCmp('LOAN_ACCOUNT')
												.setValue(
														boxstore4.getAt(index).data.LOAN_ACCOUNT);
										Ext
												.getCmp('referenceRate')
												.setValue(
														boxstore4.getAt(index).data.REFERENCE_RATE);
										Ext
												.getCmp('MoneyType')
												.setValue(
														boxstore4.getAt(index).data.CURRENCY);
										Ext
												.getCmp('LoanLimitTime')
												.setValue(
														boxstore4.getAt(index).data.LOAN_LIMIT_TIME);
										Ext
												.getCmp('ftpinfo')
												.setValue(
														boxstore4.getAt(index).data.FTP);

										if (cb6.getValue() != '') {
											ftpNo = cb6.getValue();
											ftp1Proxy
													.setApi(
															Ext.data.Api.actions.read,
															basepath
																	+ '/queryftp1.json?ftpNo='
																	+ ftpNo
																	+ '');
											ftp1Store
													.load({
														callback : function() {
															if (Ext
																	.getCmp(
																			'MoneyType')
																	.getValue() == 'CRM_CCY_001') {
																if (ftp1Store
																		.getCount() != 0) {
																	ftp = parseFloat(ftp1Store
																			.getAt(0).data.SYSTEM_FTP_RMBPRICE);
																}
															} else {
																if (ftp1Store
																		.getCount() != 0) {
																	ftp = parseFloat(ftp1Store
																			.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
																}
															}
															Ext
																	.getCmp(
																			'ftpvalue')
																	.setValue(
																			ftp);
														}
													});
										}
									}
								} else {
									Ext.Msg.alert('提示', '无对应计算结果!');
								}
							});

			/** ****************************************************************** */

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				title : '贷款定价器列表',
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