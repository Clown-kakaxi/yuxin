/**
 * 客户评级查询JS文件
 */
Ext
		.onReady(function() {
			var oldAssistInput = null;
			/** 防止内存控制机制误删Ext.MessageBox内部对象* */
			Ext.Msg.alert('ANTIDEBUG', 'ANTIDEBUG');
			Ext.Msg.hide();
			// 客户类型
			var boxstore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=XD000080'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			// 证件类型数据集
			var certstore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=XD000040'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			// 客户级别数据集
			var boxstore8 = new Ext.data.Store({
				sortInfo : {
					field : 'key',
					direction : 'ASC' // or 'DESC' (case sensitive for local
				// sorting)
				},
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=P_CUST_GRADE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

			// 查询条件Form定义
			var qForm = new Ext.form.FormPanel(
					{
						id : 'qForm',
						labelWidth : 90, // 标签宽度
						frame : true, // 是否渲染表单面板背景色
						labelAlign : 'middle', // 标签对齐方式
						// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
						buttonAlign : 'center',
						layout : 'column',
						border : false,
						items : [
								{
									columnWidth : .25,
									layout : 'form',
									labelWidth : 70, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : [ {
										fieldLabel : '客户号',
										name : 'CUST_ID',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle : 'text-align:right;',
										anchor : '90%'
									}, new Ext.form.ComboBox({
										hiddenName : 'CUST_TYP',
										fieldLabel : '客户类型',
										labelStyle : 'text-align:right;',
										triggerAction : 'all',
										name : 'CUST_TYP',
										store : boxstore,
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText : '请选择',
										resizable : true,
										anchor : '90%'
									}) ]
								},
								{
									columnWidth : .25,
									layout : 'form',
									labelWidth : 90, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : [
											{
												fieldLabel : '客户名称',
												id : 'CUST_ZH_NAME',
												name : 'CUST_ZH_NAME',
												xtype : 'textfield', // 设置为数字输入框类型
												labelStyle : 'text-align:right;',
												anchor : '90%',
												enableKeyEvents : true,
												listeners : {
													/*
													 * 前台只要引入JSP页面的CSS定义、div定义以及修改ID和URL就好，其余不用做改动。后台走相应url定义的action
													 */
													'keydown' : {
														fn : function(o, evt) {
															oldAssistInput = Ext
																	.getCmp(
																			'CUST_ZH_NAME')
																	.getValue();
														}
													},
													'keyup' : {
														fn : function(o, evt) {
															var input = Ext
																	.getCmp('CUST_ZH_NAME');
															var url = basepath
																	+ '/customerGradeQuery!NameFind.json';
															assistInput(input,
																	url,
																	oldAssistInput);
														}
													},
													'change' : {
														fn : function(o) {
															var findDivId = document.activeElement.id;
															if (findDivId == "custNameInputDiv") {
															} else {
																custNameInputDiv.style.display = "none";
															}
														}
													}
												}
											},new Com.yucheng.bcrm.common.OrgField(
													{
														searchType : 'SUBTREE',/*
																				 * 指定查询机构范围属性
																				 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
																				 * （所有父、祖机构）ALLORG（所有机构）
																				 */
														fieldLabel : '评级机构',
														labelStyle : 'text-align:right;',
														id : 'CUST_ORG1', // 放大镜组件ID，用于在重置清空时获取句柄
														name : 'CUST_ORG1',
														hiddenName : 'ORG_ID', // 后台获取的参数名称
														anchor : '90%',
														checkBox : false
													// 复选标志
													})]
								},
								{
									columnWidth : .25,
									layout : 'form',
									labelWidth : 70, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : [ {
										fieldLabel : '证件号码',
										name : 'CERT_NUM',
										id : 'CERT_NUM',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle : 'text-align:right;',
										anchor : '90%'
									} ]
								},
								{
									columnWidth : .25,
									layout : 'form',
									labelWidth : 70, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : [
											 {
												xtype : 'datefield',
												fieldLabel : '评级日期',
												format : 'Y-m-d',
												name : 'ETL_DT',
												anchor : '90%'
											} ]
								} ],
						buttons : [
								{
									text : '查询',
									handler : function() {
										var parameters = qForm.getForm()
												.getValues(false);
										store.baseParams = {
											'condition' : Ext.util.JSON
													.encode(parameters)
										};
										store.load({
											params : {
												start : 0,
												limit : bbar.pageSize
											}
										});

									}
								},
								{
									text : '重置',
									handler : function() {
										qForm.getForm().reset();
									}
								} ]
					});
			/**
			 * 客户查询结果数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/customerGradeQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'json.count',
					root : 'json.data'
				}, [ {
					name : 'custId',
					mapping : 'CUST_ID'
				}, {
					name : 'custZhName',
					mapping : 'CUST_ZH_NAME'
				}, {
					name : 'CERT_TYPE_ORA'
				}, {
					name : 'CUST_STAT_ORA'
				}, {
					name : 'CUST_TYP_ORA'
				}, , {
					name : 'certType',
					mapping : 'CERT_TYPE'
				}, {
					name : 'custStat',
					mapping : 'CUST_STAT'
				}, {
					name : 'custTyp',
					mapping : 'CUST_TYP'
				},
				{
					name : 'certNum',
					mapping : 'CERT_NUM'
				},
				{
					name : 'gradeLevel',
					mapping : 'GRADE_LEVEL'
				},
				{
					name : 'gradeHandLevel',
					mapping : 'GRADE_HAND_LEVEL'
				},{
					name : 'etlDt',
					mapping : 'ETL_DT'
				},{
					name : 'orgName',
					mapping : 'ORG_NAME'
				}
				])
			});
			// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			// 定义列模型
			var cm = new Ext.grid.ColumnModel([ rownum, sm, {
				header : '客户号',
				dataIndex : 'custId',
				sortable : true,
				width : 100
			}, {
				header : '客户名称',
				dataIndex : 'custZhName',
				width : 200,
				sortable : true
			}, {
				header : '证件类型',
				dataIndex : 'CERT_TYPE_ORA',
				width : 150,
				sortable : true
			}, {
				header : '证件号码',
				dataIndex : 'certNum',
				width : 150,
				sortable : true,
				renderer : function(v) {
					if (!JsContext.checkGrant('hideCNum'))
						return "******";
					else
						return v;
				}
			}, {
				header : '客户状态',
				dataIndex : 'CUST_STAT_ORA',
				width : 60,
				sortable : true
			}, {
				header : '客户类型',
				dataIndex : 'custTyp',
				width : 100,
				sortable : true,
				hidden : true
			}, {
				header : '客户类型',
				dataIndex : 'CUST_TYP_ORA',
				width : 60,
				sortable : true
			}, {
				header : '客观评级',
				dataIndex : 'gradeLevel',
				width : 100,
				sortable : true
			}, {
				header : '主观评级',
				dataIndex : 'gradeHandLevel',
				width : 100,
				sortable : true
			},{
				header : '评级日期',
				dataIndex : 'etlDt',
				width : 100,
				sortable : true
			},{
				header : '评级机构',
				dataIndex : 'orgName',
				width : 100,
				sortable : true
			}

			]);

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
			var number = parseInt(pagesize_combo.getValue());
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()), store
						.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});
			});
			var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var checkedNodessd = '';
			// 表格工具栏

			var tbar = new Ext.Toolbar(
					{

						items : [ {
							text : '客户视图',
							iconCls : 'custGroupMemIconCss',
							handler : function() {
								var checkedNodes = grid.getSelectionModel().selections.items;
								if (checkedNodes.length == 0) {
									Ext.Msg.alert('提示', '未选择任何客户');
									return;
								} else if (checkedNodes.length > 1) {
									Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
									return;
								}
								var viewWindow = new Com.yucheng.crm.cust.ViewWindow(
										{
											id : 'viewWindow',
											custId : checkedNodes[0].data.custId,
											custName : checkedNodes[0].data.custZhName,
											custTyp : checkedNodes[0].data.custTyp
										});

								Ext.Ajax
										.request({
											url : basepath
													+ '/commsearch!isMainType.json',
											method : 'GET',
											params : {
												'mgrId' : __userId,
												'custId' : checkedNodes[0].data.custId
											},
											success : function(response) {
												var anaExeArray = Ext.util.JSON
														.decode(response.responseText);
												if (anaExeArray.json != null) {
													if (anaExeArray.json.MAIN_TYPE == '1') {
														oCustInfo.omain_type = true;
													} else {
														oCustInfo.omain_type = false;
													}
												} else {
													oCustInfo.omain_type = false;
												}
												oCustInfo.cust_id = checkedNodes[0].data.custId;
												oCustInfo.cust_name = checkedNodes[0].data.custZhName;
												oCustInfo.cust_type = checkedNodes[0].data.custTyp;
												viewWindow.show();

											},
											failure : function(form, action) {
											}
										});

							}
						} 
						]
					});

			// 表格实例
			var grid = new Ext.grid.GridPanel(
					{
						height : document.body.scrollHeight - 123,
						// width : document.body.scrollWidth,
						id : 'viewgrid',
						frame : true,
						autoScroll : true,
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						store : store, // 数据存储
						stripeRows : true, // 斑马线
						cm : cm, // 列模型
						sm : sm, // 复选框
						tbar : [ tbar ], // 表格工具栏
						bbar : bbar,
						viewConfig : {
							forceFit : false,
							autoScroll : true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						},
						listeners : {
							dblclick : function() {
								var checkedNodes = grid.getSelectionModel().selections.items;
								if (checkedNodes.length > 0) {
									var viewWindow = new Com.yucheng.crm.cust.ViewWindow(
											{
												id : 'viewWindow',
												custId : checkedNodes[0].data.custId,
												custName : checkedNodes[0].data.custZhName,
												custTyp : checkedNodes[0].data.custTyp
											});

									Ext.Ajax
											.request({
												url : basepath
														+ '/commsearch!isMainType.json',
												method : 'GET',
												params : {
													'mgrId' : __userId,
													'custId' : checkedNodes[0].data.custId
												},
												success : function(response) {
													var anaExeArray = Ext.util.JSON
															.decode(response.responseText);
													if (anaExeArray.json != null) {
														if (anaExeArray.json.MAIN_TYPE == '1') {
															oCustInfo.omain_type = true;
														} else {
															oCustInfo.omain_type = false;
														}
													} else {
														oCustInfo.omain_type = false;
													}
													oCustInfo.cust_id = checkedNodes[0].data.custId;
													oCustInfo.cust_name = checkedNodes[0].data.custZhName;
													oCustInfo.cust_type = checkedNodes[0].data.custTyp;
													viewWindow.show();

												},
												failure : function(form, action) {
												}
											});
								}

							}
						}
					});

			// 布局模型

			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ {
					layout : 'border',
					items : [ {
						region : 'north',
						title : "客户评级结果查询",
						height : 130,
						margins : '0 0 0 0',
						layout: 'fit',
						items : [ qForm ]
					}, {
						region : 'center',
						layout : 'fit',
						margins : '0 0 0 0',
						items : [ grid ]
					} ]
				} ]
			});



		});