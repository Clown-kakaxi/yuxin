Ext.onReady(function() {

	// ========================下拉框定义=================================
	// 产品状态
	var prodStatStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=PROD_STATE'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});

	// 是否标志
	var IFStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'DESC'
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=IF_FLAG'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});

	// 风险级别
	var riskTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/lookup.json?name=PROD_RISK_LEVEL'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});

	// 流动性
	var flowAbilityStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=FLOW_ABILITY'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});

	// 市场方向
	var marketDirectionStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/lookup.json?name=MARKET_DIRECTION'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});

	// 5级分类
	var fiveClassifyStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=PRODUCT_TYPE'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});

	// 产品信息formpanel
	var productEditPanel = new Ext.FormPanel({
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : '99%',
		height : 355,
		autoScroll : true,
		split : true,
		labelWidth : 120,
		items : [{
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '产品编码',
												labelStyle : 'text-align:right;',
												disabled : true,
												name : 'productId',
												id : 'productIdId',
												allowBlank : false,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '产品类别编码',
												labelStyle : 'text-align:right;',
												disabled : true,
												name : 'catlCode',
												readOnly : true,
												anchor : '100%'
											}, {
												name : 'productState',
												hiddenName : 'productState',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '产品状态',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : prodStatStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value',
												value : '1'
											}, {
												xtype : 'datefield',
												fieldLabel : '产品发布日期',
												disabled : true,
												labelStyle : 'text-align:right;',
												name : 'productStartDate',
												id : 'productStartDate1',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												xtype : 'datefield',
												fieldLabel : '产品有效截止日期',
												disabled : true,
												labelStyle : 'text-align:right;',
												name : 'productEndDate',
												id : 'productEndDate1',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												xtype : 'datefield',
												name : 'addedDate',
												disabled : true,
												fieldLabel : '上架日期',
												labelStyle : 'text-align:right;',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												xtype : 'datefield',
												name : 'soldoutDate',
												disabled : true,
												fieldLabel : '下架日期',
												labelStyle : 'text-align:right;',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												name : 'fxjb',
												hiddenName : 'parentClassify',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '上级分类',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : fiveClassifyStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												name : 'ifInternalSale',
												hiddenName : 'ifInternalSale',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '是否行内销售',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : IFStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												xtype : 'textfield',
												labelStyle : 'text-align:right;',
												disabled : true,
												fieldLabel : '销售范围',
												name : 'saleOrg',
												anchor : '100%'
											}, {
												xtype : 'numberfield',
												labelStyle : 'text-align:right;',
												disabled : true,
												fieldLabel : '收益率(%)',
												name : 'incomeProfit',
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '产品描述',
												disabled : true,
												name : 'productDescription',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '产品特点',
												disabled : true,
												name : 'prod_charact',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												labelStyle : 'text-align:right;',
												fieldLabel : '产品名称',
												disabled : true,
												name : 'productName',
												allowBlank : false,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												labelStyle : 'text-align:right;',
												fieldLabel : '产品类别',
												disabled : true,
												name : 'catlName',
												readOnly : true,
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '费率(%)',
												disabled : true,
												labelStyle : 'text-align:right;',
												name : 'cost_rate',
												anchor : '100%'
											}, {
												xtype : 'textfield',
												labelStyle : 'text-align:right;',
												fieldLabel : '期限',
												disabled : true,
												name : 'limit_time',
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '利率(%)',
												disabled : true,
												labelStyle : 'text-align:right;',
												name : 'rate',
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '客户准入规则',
												disabled : true,
												labelStyle : 'text-align:right;',
												hidden : true,
												name : 'custTarRule',
												anchor : '100%'
											}, {
												name : 'ifRecommend',
												hiddenName : 'ifRecommend',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '是否推荐',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : IFStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												xtype : 'datefield',
												name : 'recommendEndDate',
												disabled : true,
												fieldLabel : '推荐结束日期',
												labelStyle : 'text-align:right;',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												xtype : 'textarea',
												name : 'recommentReason',
												disabled : true,
												fieldLabel : '推荐理由',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '目标客户描述',
												disabled : true,
												name : 'obj_cust_disc',
												height : 74,
												maxLength : 150,
												labelStyle : 'text-align:right;',
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '担保要求描述',
												disabled : true,
												name : 'assure_disc',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												name : 'riskLevel',
												hiddenName : 'riskLevel',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '风险级别',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : riskTypeStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												name : 'flowAbility',
												hiddenName : 'flowAbility',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '流动性',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : flowAbilityStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												name : 'marketDirection',
												disabled : true,
												hiddenName : 'marketDirection',
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '市场方向',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : marketDirectionStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												name : 'ifNew',
												hiddenName : 'ifNew',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '是否新品',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : IFStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												xtype : 'datefield',
												name : 'newEndDate',
												disabled : true,
												fieldLabel : '新品结束日期',
												labelStyle : 'text-align:right;',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												name : 'ifDiscount',
												hiddenName : 'ifDiscount',
												disabled : true,
												xtype : 'combo',
												anchor : '100%',
												fieldLabel : '是否优惠',
												labelStyle : 'text-align:right;',
												triggerAction : 'all',
												mode : 'local',
												store : IFStore,
												resizable : true,
												forceSelection : true,
												valueField : 'key',
												displayField : 'value'
											}, {
												xtype : 'datefield',
												name : 'discountEndDate',
												disabled : true,
												fieldLabel : '优惠结束日期',
												labelStyle : 'text-align:right;',
												format : 'Y-m-d',
												anchor : '100%'
											}, {
												xtype : 'textarea',
												name : 'discountInfo',
												disabled : true,
												fieldLabel : '优惠信息',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '风险提示描述',
												disabled : true,
												name : 'danger_disc',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}, {
												xtype : 'textarea',
												fieldLabel : '营销渠道描述',
												disabled : true,
												name : 'channel_disc',
												labelStyle : 'text-align:right;',
												height : 74,
												maxLength : 150,
												anchor : '100%'
											}]
								}]
					}]
		}, {
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '所属法人号',
												labelStyle : 'text-align:right;',
												hidden : true,
												name : 'frId',
												anchor : '100%'
											}, {
												xtype : 'textfield',
												fieldLabel : '创建人',
												labelStyle : 'text-align:right;',
												hidden : true,
												name : 'productCreator',
												anchor : '100%'
											}]
								}]
					}]
		}]
	});

	var productInfoRecord = new Ext.data.Record.create([{
				name : 'productId',
				mapping : 'PRODUCT_ID'
			}, {
				name : 'productName',
				mapping : 'PROD_NAME'
			}, {
				name : 'catlCode',
				mapping : 'CATL_CODE'
			}, {
				name : 'catlName',
				mapping : 'CATL_NAME'
			}, {
				name : 'productState',
				mapping : 'PROD_STATE'
			}, {
				name : 'prodState',
				mapping : 'PROD_STATE'
			}, {
				name : 'productCreator',
				mapping : 'PROD_CREATOR'
			}, {
				name : 'productStartDate',
				mapping : 'PROD_START_DATE'
			}, {
				name : 'productEndDate',
				mapping : 'PROD_END_DATE'
			}, {
				name : 'productDepartment',
				mapping : 'PROD_DEPT'
			}, {
				name : 'productDescription',
				mapping : 'PROD_DESC'
			}, {
				name : 'rate',
				mapping : 'RATE',
				type : 'float'
			}, {
				name : 'cost_rate',
				mapping : 'COST_RATE',
				type : 'float'
			}, {
				name : 'limit_time',
				mapping : 'LIMIT_TIME'
			}, {
				name : 'prod_charact',
				mapping : 'PROD_CHARACT'
			}, {
				name : 'obj_cust_disc',
				mapping : 'OBJ_CUST_DISC'
			}, {
				name : 'danger_disc',
				mapping : 'DANGER_DISC'
			}, {
				name : 'channel_disc',
				mapping : 'CHANNEL_DISC'
			}, {
				name : 'assure_disc',
				mapping : 'ASSURE_DISC'
			}, {
				name : 'targetCustNum',
				mapping : 'TARGET_CUST_NUM'
			}, {
				name : 'incomeProfit',
				mapping : 'INCOME_PROFIT'
			}, {
				name : 'flowAbility',
				mapping : 'FLOW_ABILITY'
			}, {
				name : 'flowAbility',
				mapping : 'FLOW_ABILITY'
			}, {
				name : 'marketDirection',
				mapping : 'MARKET_DIRECTION'
			}, {
				name : 'ifNew',
				mapping : 'IF_NEW'
			}, {
				name : 'newEndDate',
				mapping : 'NEW_END_DATE'
			}, {
				name : 'ifDiscount',
				mapping : 'IF_DISCOUNT'
			}, {
				name : 'discountEndDate',
				mapping : 'DISCOUNT_END_DATE'
			}, {
				name : 'discountInfo',
				mapping : 'DISCOUNT_INFO'
			}, {
				name : 'ifRecommend',
				mapping : 'IF_RECOMMEND'
			}, {
				name : 'recommendEndDate',
				mapping : 'RECOMMEND_END_DATE'
			}, {
				name : 'recommentReason',
				mapping : 'RECOMMENT_REASON'
			}, {
				name : 'addedDate',
				mapping : 'ADDED_DATE'
			}, {
				name : 'soldoutDate',
				mapping : 'SOLDOUT_DATE'
			}, {
				name : 'parentClassify',
				mapping : 'PARENT_CLASSIFY'
			}, {
				name : 'PARENT_CLASSIFY_ORA'
			}, {
				name : 'ifInternalSale',
				mapping : 'IF_INTERNAL_SALE'
			}, {
				name : 'IF_INTERNAL_SALE_ORA'
			}, {
				name : 'saleOrg',
				mapping : 'SALE_ORG'
			}, {
				name : 'riskLevel',
				mapping : 'RISK_LEVEL'
			}, {
				name : 'RISK_LEVEL_ORA'
			}, {
				name : 'frId',
				mapping : 'FR_ID'
			}]);

	// 读取json数据的panel
	var productInfoReader = new Ext.data.JsonReader({
				totalProperty : 'json.count',
				root : 'json.data'
			}, productInfoRecord);

	var productInfoStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/custFitProdDetailsQueryAction.json',
							method : 'GET'
						}),
				reader : productInfoReader
			});

	var prodTabs = new Ext.TabPanel({
		width : '100%',
		heignt : '100%',
		activeTab : 0,
		frame : true,
		defaults : {
			autoHeight : true
		},
		resizeTabs : true, // turn on tab resizing
		preferredTabWidth : 150,
		items : [{
					title : '<span style=\'text-align:center;\'>产品信息</span>',
					items : [productEditPanel]
				}, {
					title : '<span style=\'text-align:center;\' >产品介绍</span>',
					html : '',
					listeners : {
						'activate' : function(o) {
							var nodeId = Ext.getCmp('productIdId').getValue();
							o.el.dom.innerHTML = '<iframe id="content3" name="content3" style="width:100%;height:450px;" frameborder="no"" src=\"'
									+ basepath
									+ '/contents/pages/demo/docs/doc1.jsp?nodeId='
									+ nodeId + '\" "/> scrolling="auto"> ';
						}
					}
				}]
	});

	var productInfo = new Ext.Window({// 产品推荐window
		title : '产品详情',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height : 420,
		width : 1100,
		draggable : false,
		closeAction : 'hide',
		modal : true, // 模态窗口
		border : false,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [prodTabs]
	});

	var productInfoRecord = new Ext.data.Record.create([{
				name : 'productId',
				mapping : 'PRODUCT_ID'
			}, {
				name : 'productName',
				mapping : 'PROD_NAME'
			}, {
				name : 'catlCode',
				mapping : 'CATL_CODE'
			}, {
				name : 'catlName',
				mapping : 'CATL_NAME'
			}, {
				name : 'productState',
				mapping : 'PROD_STATE'
			}, {
				name : 'prodState',
				mapping : 'PROD_STATE'
			}, {
				name : 'productCreator',
				mapping : 'PROD_CREATOR'
			}, {
				name : 'productStartDate',
				mapping : 'PROD_START_DATE'
			}, {
				name : 'productEndDate',
				mapping : 'PROD_END_DATE'
			}, {
				name : 'productDepartment',
				mapping : 'PROD_DEPT'
			}, {
				name : 'productDescription',
				mapping : 'PROD_DESC'
			}, {
				name : 'rate',
				mapping : 'RATE',
				type : 'float'
			}, {
				name : 'cost_rate',
				mapping : 'COST_RATE',
				type : 'float'
			}, {
				name : 'limit_time',
				mapping : 'LIMIT_TIME'
			}, {
				name : 'prod_charact',
				mapping : 'PROD_CHARACT'
			}, {
				name : 'obj_cust_disc',
				mapping : 'OBJ_CUST_DISC'
			}, {
				name : 'danger_disc',
				mapping : 'DANGER_DISC'
			}, {
				name : 'channel_disc',
				mapping : 'CHANNEL_DISC'
			}, {
				name : 'assure_disc',
				mapping : 'ASSURE_DISC'
			}, {
				name : 'targetCustNum',
				mapping : 'TARGET_CUST_NUM'
			}, {
				name : 'incomeProfit',
				mapping : 'INCOME_PROFIT'
			}, {
				name : 'flowAbility',
				mapping : 'FLOW_ABILITY'
			}, {
				name : 'flowAbility',
				mapping : 'FLOW_ABILITY'
			}, {
				name : 'marketDirection',
				mapping : 'MARKET_DIRECTION'
			}, {
				name : 'ifNew',
				mapping : 'IF_NEW'
			}, {
				name : 'newEndDate',
				mapping : 'NEW_END_DATE'
			}, {
				name : 'ifDiscount',
				mapping : 'IF_DISCOUNT'
			}, {
				name : 'discountEndDate',
				mapping : 'DISCOUNT_END_DATE'
			}, {
				name : 'discountInfo',
				mapping : 'DISCOUNT_INFO'
			}, {
				name : 'ifRecommend',
				mapping : 'IF_RECOMMEND'
			}, {
				name : 'recommendEndDate',
				mapping : 'RECOMMEND_END_DATE'
			}, {
				name : 'recommentReason',
				mapping : 'RECOMMENT_REASON'
			}, {
				name : 'addedDate',
				mapping : 'ADDED_DATE'
			}, {
				name : 'soldoutDate',
				mapping : 'SOLDOUT_DATE'
			}, {
				name : 'parentClassify',
				mapping : 'PARENT_CLASSIFY'
			}, {
				name : 'PARENT_CLASSIFY_ORA'
			}, {
				name : 'ifInternalSale',
				mapping : 'IF_INTERNAL_SALE'
			}, {
				name : 'IF_INTERNAL_SALE_ORA'
			}, {
				name : 'saleOrg',
				mapping : 'SALE_ORG'
			}, {
				name : 'riskLevel',
				mapping : 'RISK_LEVEL'
			}, {
				name : 'RISK_LEVEL_ORA'
			}, {
				name : 'frId',
				mapping : 'FR_ID'
			}]);

	// 读取json数据的panel
	var productInfoReader = new Ext.data.JsonReader({
				totalProperty : 'json.count',
				root : 'json.data'
			}, productInfoRecord);

	var productInfoStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/custFitProdDetailsQueryAction.json',
							method : 'GET'
						}),
				reader : productInfoReader
			});

	// ********************************************短信部分代码start========================/
	var messageRemark = '';// 短信内容
	// 短信模板
	var modelstore = new Ext.data.Store({
				sortInfo : {
					field : 'key',
					direction : 'ASC'
				},
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/ocrmFMmSysMsg!searchMktDic.json',
							success : function(response) {
							}
						}),
				reader : new Ext.data.JsonReader({
							root : 'data'
						}, [{
									name : 'key',
									mapping : 'KEY'
								}, {
									name : 'value',
									mapping : 'VALUE'
								}])
			});

	// 短信表单
	var panel = new Ext.form.FormPanel({
		frame : true,
		region : 'center',
		labelWidth : 80,
		items : [{
					xtype : 'textfield',
					fieldLabel : '客户编号',
					name : 'custId',
					labelStyle : 'text-align:right;',
					anchor : '100%',
					allowBlank : false
				}, {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'custName',
					labelStyle : 'text-align:right;',
					anchor : '100%',
					allowBlank : false
				}, {
					xtype : 'textfield',
					fieldLabel : '客户电话',
					name : 'cellNumber',
					labelStyle : 'text-align:right;',
					anchor : '100%',
					allowBlank : false
				}, {
					xtype : 'textfield',
					fieldLabel : '产品id',
					name : 'prodId',
					labelStyle : 'text-align:right;',
					anchor : '100%',
					allowBlank : false,
					hidden : true
				}, {
					xtype : 'textfield',
					fieldLabel : '产品名称',
					name : 'prodName',
					labelStyle : 'text-align:right;',
					anchor : '100%',
					allowBlank : false
				}, {
					fieldLabel : '短信模板<font color="red">*</font>',
					hiddenName : 'modelId',
					name : 'modelId',
					xtype : 'combo',
					labelStyle : 'text-align:right;',
					triggerAction : 'all',
					store : modelstore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText : '请选择',
					resizable : true,
					anchor : '100%',
					hidden : true,
					listeners : {
						// 根据选择的模板生成短信内容
						'select' : function() {
							var model = panel.form.findField('modelId')
									.getValue();
							// Ext.Msg.wait('正在生成短信内容......','系统提示');
							Ext.Ajax.request({
								url : basepath
										+ '/ocrmFMmSysMsg!getMessage.json',
								method : 'POST',
								params : {
									'modelId' : panel.form.findField('modelId')
											.getValue(),
									'custId' : panel.form.findField('custId')
											.getValue(),
									'custName' : panel.form
											.findField('custName').getValue(),
									'prodId' : panel.form.findField('prodId')
											.getValue(),
									'prodName' : panel.form
											.findField('prodName').getValue()
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function(response) {
									messageRemark = response.responseText;
									panel.form.findField('messageRemark')
											.setValue(messageRemark);
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON
											.decode(response.status);
									if (resultArray == 403) {
										Ext.Msg.alert('提示',
												response.responseText);
									} else {
										Ext.Msg
												.alert(
														'提示',
														'操作失败,失败原因:'
																+ response.responseText);
									}
								}
							});
						}
					}
				}, {
					xtype : 'textarea',
					fieldLabel : '短信内容',
					name : 'messageRemark',
					labelStyle : 'text-align:right;',
					anchor : '100%',
					allowBlank : false,
					disabled : false
				}]
	});

	// 短信窗口
	var dxWin = new Ext.Window({
		title : '短信营销',
		height : 300,
		width : 450,
		buttonAlign : 'center',
		draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll : true,
		closeAction : 'hide',
		border : false,
		items : [panel],
		buttons : [{
			text : '确定',
			handler : function() {
				if (!panel.getForm().isValid()) {
					Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
					return false;
				} else {
					if (messageRemark == '') {// 没有模板的
						needApprove = true;
					} else if (messageRemark != panel.form
							.findField('messageRemark').getValue()) {// 做了修改
						needApprove = true;
					} else {
						needApprove = false;
					}
					Ext.Msg.wait('正在保存，请稍后......', '系统提示');
					Ext.Ajax.request({
								url : basepath + '/ocrmFMmSysMsg!saveData.json',
								method : 'POST',
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								form : panel.getForm().id,
								params : {
									'needApprove' : needApprove
								},
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									dxWin.hide();
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '操作失败,失败原因:'
													+ response.responseText);
								}
							});
				}
			}
		}, {
			text : '关 闭',
			handler : function() {
				dxWin.hide();
			}
		}]
	});
	/** ********************************************短信部分代码end******************** */

	// 展示新增营销活动的窗口
	function addActivityInit() {
		var startData = new Date();
		_buttonVisible = true;
		_sheetVisible = false;
		editBasePlanForm.form.reset();

		editBasePlanForm.form.findField('mktActiName').setDisabled(false);
		editBasePlanForm.form.findField('mktActiCost').setDisabled(false);
		editBasePlanForm.form.findField('mktActiType').setDisabled(false);
		editBasePlanForm.form.findField('pstartDate').setDisabled(false);
		editBasePlanForm.form.findField('mktActiMode').setDisabled(false);
		editBasePlanForm.form.findField('pendDate').setDisabled(false);
		editBasePlanForm.form.findField('mktActiStat').setDisabled(false);
		editBasePlanForm.form.findField('mktActiAddr').setDisabled(false);
		editBasePlanForm.form.findField('mktActiCont').setDisabled(false);
		editBasePlanForm.form.findField('actiCustDesc').setDisabled(false);
		editBasePlanForm.form.findField('actiOperDesc').setDisabled(false);
		editBasePlanForm.form.findField('actiProdDesc').setDisabled(false);
		editBasePlanForm.form.findField('mktActiAim').setDisabled(false);
		editBasePlanForm.form.findField('actiRemark').setDisabled(false);

		editPlanWindow.setTitle('营销活动新增');
		editBasePlanForm.form.findField('createUser').setValue(__userId);
		editBasePlanForm.form.findField('createDate').setValue(startData);
		editBasePlanForm.form.findField('updateUser').setValue(__userId);
		editBasePlanForm.form.findField('updateDate').setValue(startData);
		editBasePlanForm.form.findField('mktActiStat').setValue('1');
		editBasePlanForm.form.findField('createOrg').setValue(__units);

		Ext.getCmp('jbxx').show();
		Ext.getCmp('glcpxx').hide();
		Ext.getCmp('glkkxx').hide();
		Ext.getCmp('glqdxx').hide();
		Ext.getCmp('fjxx').hide();
		Ext.getCmp('spxx').hide();
		editPlanWindow.show();
	};

	var tbar = new Ext.Toolbar({
		items : [{
			text : '产品详情',
			iconCls : 'detailIconCss',
			handler : function() {
				var selectLength = productGrid.getSelectionModel()
						.getSelections().length;

				if (selectLength > 1) {
					alert('请选择一条记录!');
				} else {
					var infoRecord = productGrid.getSelectionModel()
							.getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {

						productInfoStore.load({
									params : {
										'prodId' : infoRecord.data.prodId
									},
									callback : function() {
										productEditPanel.getForm()
												.loadRecord(productInfoStore
														.getAt(0));
									}
								});
						productInfo.show();
					}
				}
			}
		}, {
			text : '电话营销',
			disabled : parent.MType == 0 ? true : false,// 根据接入设备控制按钮是否可用
			iconCls : 'editIconCss',
			handler : function() {
				var selectLength = productGrid.getSelectionModel()
						.getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = productGrid.getSelectionModel()
							.getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						var data = productGrid.getSelectionModel().selections.items[0].data;
						if (data.telephoneNum == '' && data.officePhone == ''
								&& data.linkPhone == '') {// 没有电话信息
							Ext.Msg.alert('提示', '没有联系电话信息！');
							return;
						} else {
							newWindow(data, store);
						}
					}
				}
			}
		}, {
			text : '短信营销',
			iconCls : 'editIconCss',
			handler : function() {
				var selectLength = productGrid.getSelectionModel()
						.getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = productGrid.getSelectionModel()
							.getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						var data = productGrid.getSelectionModel().selections.items[0].data;
						if (data.telephoneNum == '') {// 没有电话信息
							Ext.Msg.alert('提示', '没有手机信息！');
							return;
						} else {
							dxWin.show();
							panel.getForm().reset();
							panel.getForm().findField('custId')
									.setValue(data.custId);
							panel.getForm().findField('custName')
									.setValue(data.custZhName);
							panel.getForm().findField('cellNumber')
									.setValue(data.telephoneNum.substring(
											data.telephoneNum.length - 11,
											data.telephoneNum.length));
							panel.getForm().findField('prodId')
									.setValue(data.prodId);
							panel.getForm().findField('prodName')
									.setValue(data.prodName);
							panel.form.findField('modelId').setVisible(false);
							modelstore.load({
										params : {
											'code' : data.prodId
										},
										callback : function() {
											if (modelstore.getCount() != 0) {
												panel.form.findField('modelId')
														.setVisible(true);
											} else {
												Ext.Msg
														.alert("提示",
																"本产品没有相关的短信模板,您需要自行编辑内容!");
												messageRemark = '';
												panel.form.findField('modelId')
														.setVisible(false);
											}

										}
									});
						}
					}
				}
			}
		}, {
			text : '创建营销活动',
			iconCls : 'addIconCss',
			handler : function() {
				var checkedNodes = productGrid.getSelectionModel().selections.items;
				if (checkedNodes.length == 0) {
					Ext.Msg.alert('提示', '未选择任何产品');
					return;
				} else if (checkedNodes.length > 1) {
					Ext.Msg.alert('提示', '您只能选中一个产品');
					return;
				}
				ifGroup = false;
				ifProd = true;
				prodId = checkedNodes[0].data.prodId;
				prodName = checkedNodes[0].data.prodName;
				custId = checkedNodes[0].data.custId;
				custName = checkedNodes[0].data.custZhName;
				addActivityInit();
			}
		}, {
			text : '创建商机',
			iconCls : 'addIconCss',
			handler : function() {
				var checkedNodes = productGrid.getSelectionModel().selections.items;
				if (checkedNodes.length == 0) {
					Ext.Msg.alert('提示', '未选择任何产品');
					return;
				} else if (checkedNodes.length > 1) {
					Ext.Msg.alert('提示', '您只能选中一个产品');
					return;
				}
				var data = checkedNodes[0].data;
				ifGroup = false;
				ifProd = true;
				addBusiOpporWindow.show();
				addBusiOpporForm.form.findField("custType")
						.setValue(data.custTyp);
				addBusiOpporForm.form.findField("custCategory")
						.setValue(data.custStat);
				addBusiOpporForm.form.findField("custName")
						.setValue(data.custZhName);
				addBusiOpporForm.form.findField("custContactName")
						.setValue(data.linkUser);
				addBusiOpporForm.form.findField("prodId").setValue(data.prodId);
				addBusiOpporForm.form.findField("prodName")
						.setValue(data.prodName);

				addBusiOpporForm.buttons[1].hide();
			}
		}]
	});

	// 列选择模型
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});

	var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/productFitOfCustGroupAction.json?groupId='
									+ oCustInfo.groupId + '&groupType='
									+ oCustInfo.custFrom + '&groupMemberType='
									+ oCustInfo.groupMemberType
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'json.count',
							root : 'json.data'
						}, [{
									name : 'id',
									mapping : 'ID'
								}, {
									name : 'prodId',
									mapping : 'PROD_ID'
								}, {
									name : 'prodName',
									mapping : 'PROD_NAME'
								}, {
									name : 'custId',
									mapping : 'CUST_ID'
								}, {
									name : 'custZhName',
									mapping : 'CUST_ZH_NAME'
								}, {
									name : 'custTyp',
									mapping : 'CUST_TYP'
								}, {
									name : 'custStat',
									mapping : 'CUST_STAT'
								}, {
									name : 'linkUser',
									mapping : 'LINK_USER'
								}, {
									name : 'FR_ID_ORA'
								}, {
									name : 'PROD_FR_ID_ORA'
								}, {
									name : 'telephoneNum',
									mapping : 'TELEPHONE_NUM'
								}, {
									name : 'officePhone',
									mapping : 'OFFICE_PHONE'
								}, {
									name : 'linkPhone',
									mapping : 'LINK_PHONE'
								}])
			});

	// 查询产品列表数据
	store.load();

	// gridtable中的列定义
	var productInfoColumns = new Ext.grid.ColumnModel([sm, rownum, {
				header : 'ID',
				dataIndex : 'id',
				id : "id",
				width : 150,
				sortable : true,
				hidden : true
			}, {
				header : '客户编号',
				dataIndex : 'custId',
				id : 'custId',
				width : 150,
				sortable : true
			}, {
				header : '客户名称',
				dataIndex : 'custZhName',
				id : 'custZhName',
				width : 150,
				sortable : true
			}, {
				header : '产品编号',
				dataIndex : 'prodId',
				id : "prodId",
				width : 150,
				sortable : true
			}, {
				header : '产品名称',
				dataIndex : 'prodName',
				id : 'prodName',
				width : 200,
				sortable : true
			}]);

	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'],
									[50, '50条/页'], [100, '100条/页'],
									[250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				forceSelection : true,
				width : 85
			});

	var number = parseInt(pagesize_combo.getValue());

	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()), store
						.load({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
			});

	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	var productGrid = new Ext.grid.GridPanel({// 用户列表数据grid
		frame : true,
		width : '100%',
		height : 600,
		id : 'productGrid',
		autoScroll : true,
		tbar : tbar,
		bbar : bbar,
		stripeRows : true, // 斑马线
		store : store,
		loadMask : true,
		cm : productInfoColumns,
		sm : sm,
		viewConfig : {
			forceFit : false,
			autoScroll : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	var view = new Ext.Panel({
				renderTo : oCustInfo.view_source,
				height : document.body.scrollHeight - 30,
				layout : 'fit',
				autoScroll : true,
				items : [productGrid]
			});
});