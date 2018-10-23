/*******************************************************************************
 * menu:客户管理》客户托管》客户托管查询 auther:sujm
 */
var custStr = "", custNameStr = "";
var managedId = "";
var depositLogId = "";
Ext.onReady(function() {
	Ext.QuickTips.init();
	// 主协标识
	var mainTypStore = new Ext.data.Store({
				restful : true,
				// autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=MAINTAIN_TYPE'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	mainTypStore.load();

	var managedStatStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '未开始'], ['2', '托管中'], ['3', '已结束']]
			});

	// 性别
	var sexStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=DEM0100005'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	// 证件类型
	var certTypStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=PAPERS_TYPE'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	certTypStore.load();
	// 客户类型
	var custTypStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=XD000080'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	custTypStore.load();
	// 客户级别
	var custLevStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				sortInfo : {
					field : 'key',
					direction : 'ASC' // or 'DESC' (case sensitive for local
					// sorting)
				},
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name='
									+ 'C_CUST_LEV'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	custLevStore.load();
	// 审批状态
	var appStatusStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath
									+ '/lookup.json?name=APPROVEL_STATUS'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	appStatusStore.load();
	// **客户经理分配end**********************************************************
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
		id : "listPanel",
		autocroll : true,
		title : "客户管理 -> 客户托管 -> 客户托管审批",
		stUrl : basepath + '/customer_deposit_query.json',// basepath +
		// '/customer_assign.json',
		detailUrl : basepath + '/customer_deposit_query.json',
		primary : "ID",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 120,
		// 定义增删详情页面弹出窗口高度
		winHeight : 250,
		// 宽度
		winWidth : 1024,
		dbclick : false,
		defaultLoad : false,
		// 托管审批通过
		approvel : function() {
			Ext.Ajax.request({
						url : basepath
								+ '/customer_deposit_query!approvel.json',
						params : {
							idStr : managedId
						},
						waitMsg : '正在保存数据,请等待...',
						// 显示读盘的动画效果，执行完成后效果消失
						method : 'POST',
						scope : listPanel,
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							listPanel.loadCurrData();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							listPanel.loadCurrData();
						}
					});
			listPanel.closeWin();
		},
		// 托管审批拒绝
		approvelBack : function() {
			Ext.Ajax.request({
						url : basepath
								+ '/customer_deposit_query!approvelBack.json',
						params : {
							idStr : managedId
						},
						waitMsg : '正在保存数据,请等待...',
						// 显示读盘的动画效果，执行完成后效果消失
						method : 'POST',
						scope : listPanel,
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							listPanel.loadCurrData();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							listPanel.loadCurrData();
						}
					});
			listPanel.closeWin();
		},
		buts : [{
			id : 'depositSpBtn',
			xtype : 'button',
			tooltip : '审批',
			text : '审批',
			iconCls : 'ReadIconCss',
			// hidden:JsContext.checkGrant('_approlve'),
			listeners : {
				click : function(n) {
					debugger;
					if (listPanel.grid.selModel.hasSelection()) {
						var records = listPanel.grid.selModel.getSelections(); // 得到被选择的行的数组
						var recordsLen = records.length; // 得到行数组的长度
						if (recordsLen > 1) {
							Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行审批！");
						} else {
							// debugger;
							var record = listPanel.grid.getSelectionModel()
									.getSelected();
							managedId = record.get(listPanel.primary);
							debugger;
							// depositLogId = record.data;
							listPanel.opUrl = listPanel.approvelURl;
							// listPanel.spIdStr =
							// records[0].get(listPanel.primary);
							var approvelStatus = records[0].get('APPROVE_STAT');
							// debugger;
							var winButsArray = [];
							// 审批状态中2为已审批
							if (approvelStatus == '1') {
								winButsArray.push({
											text : "通过",
											handler : listPanel.approvel,
											scope : listPanel
										});
								winButsArray.push({
											text : "不通过",
											handler : listPanel.approvelBack,
											scope : listPanel
										});
							}
							winButsArray.push({
										text : "关闭",
										handler : listPanel.closeWin,
										scope : listPanel
									});
							listPanel.winButs = winButsArray;
							listPanel.showWin();
							if (listPanel.stUrl)
								listPanel.seOneRecord(managedId);
							else if (listPanel.demoData)
								listPanel.fp.getForm().loadRecord(record);
						}
					} else {
						Ext.Msg.alert("提示", "请先选择要审批的记录!");
					}
				}
			}
		}],
		selectItems : {
			layout : 'column',
			items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						defaultType : 'textfield',
						border : false,
						items : [new Com.yucheng.bcrm.common.OrgField({
									searchType : 'ALLORG',/*
															 * 指定查询机构范围属性
															 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
															 * （所有父、祖机构）ALLORG（所有机构）
															 */
									fieldLabel : '所属机构',
									labelStyle : 'text-align:right;',
									// roleType:__P_OR_C,
									id : 'jigouhao', // 放大镜组件ID，用于在重置清空时获取句柄
									name : 'BEFORE_INST_NAME',
									hiddenName : 'instncode', // 后台获取的参数名称
									anchor : '90%',
									checkBox : true
										// 复选标志
									}), new Ext.form.ComboBox({
											hiddenName : 'CUST_LEV',
											fieldLabel : '客户级别',
											labelStyle : 'text-align:right;',
											triggerAction : 'all',
											store : custLevStore,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											typeAhead : true,
											emptyText : '请选择',
											resizable : true,
											anchor : '90%'
										}), {
									name : 'APPLY_DATE_START',
									xtype : 'datefield',
									fieldLabel : '申请时间从',
									format : 'Y-m-d',
									width : '100',
									anchor : '90%'
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						defaultType : 'textfield',
						border : false,
						items : [{
									name : 'CUST_ID',
									xtype : 'textfield',
									fieldLabel : '核心客户号',
									width : '100',
									anchor : '90%'
								}, {
									name : 'CUST_ZH_NAME',
									xtype : 'textfield',
									fieldLabel : '客户名称',
									width : '100',
									anchor : '90%'
								}, {
									name : 'APPLY_DATE_END',
									xtype : 'datefield',
									fieldLabel : '申请时间到',
									format : 'Y-m-d',
									width : '100',
									anchor : '90%'
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						defaultType : 'textfield',
						border : false,
						items : [new Com.yucheng.crm.common.OrgUserManage({
											xtype : 'userchoose',
											fieldLabel : '客户经理',
											id : 'BEFORE_MGR_CODE',
											labelStyle : 'text-align:right;',
											name : 'BEFORE_MGR_CODE',
											hiddenName : 'BEFORE_MGR_CODE1',
											searchRoleType : ('1014,1027'), // 指定查询角色属性
											// ,默认全部角色
											searchType : 'SUBTREE',/*
																	 * 允许空，默认辖内机构用户，指定查询机构范围属性
																	 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
																	 * （所有父、祖机构）ALLORG（所有机构）
																	 */
											singleSelect : false,
											anchor : '90%'
										}),
								new Com.yucheng.crm.common.OrgUserManage({
											xtype : 'userchoose',
											fieldLabel : '托管客户经理',
											id : 'MANAGED_MGR_CODE',
											labelStyle : 'text-align:right;',
											name : 'MANAGED_MGR_CODE',
											hiddenName : 'MANAGED_MGR_CODE1',
											searchRoleType : ('1014,1027'), // 指定查询角色属性
											// ,默认全部角色
											searchType : 'SUBTREE',/*
																	 * 允许空，默认辖内机构用户，指定查询机构范围属性
																	 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
																	 * （所有父、祖机构）ALLORG（所有机构）
																	 */
											singleSelect : false,
											anchor : '90%'
										})]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						defaultType : 'textfield',
						border : false,
						items : [{
									store : mainTypStore,
									xtype : 'combo',
									resizable : true,
									fieldLabel : '主协标志',
									name : 'MAIN_TYPE',
									hiddenName : 'MAIN_TYPE',
									valueField : 'key',
									displayField : 'value',
									mode : 'local',
									editable : false,
									forceSelection : true,
									triggerAction : 'all',
									emptyText : '请选择',
									selectOnFocus : true,
									anchor : '90%'
								}]
					}]
		},
		// 查询字段定义，若不定义则不出现查询条件From

		gclms : [{
					name : 'ID'
				}, {
					name : 'org_name'
				}, {
					name : 'BEFORE_INST_NAME',
					sortable : true,
					header : '所属机构',
					width : 150
				}, {
					name : 'CUST_ZH_NAME',
					sortable : true,
					header : '客户名称',
					width : 180
				}, {
					name : 'CUST_ID',
					header : '核心客户号',
					width : 130
				}, {
					name : 'CUST_LEV',
					header : '客户级别',
					type : 'mapping',
					store : custLevStore,
					mappingkey : 'key',
					mappingvalue : 'value',
					width : 80
				}, {
					name : 'BEFORE_MGR_NAME',
					sortable : true,
					header : '客户经理',
					width : 80
				}, {
					name : 'MANAGED_MGR_NAME',
					sortable : true,
					header : '托管客户经理',
					width : 90
				}, {
					name : 'MAIN_TYPE',
					sortable : true,
					header : '主协标志',
					type : 'mapping',
					store : mainTypStore,
					mappingkey : 'key',
					mappingvalue : 'value',
					width : 60
				}, {
					name : 'APPLY_DATE',
					sortable : true,
					header : '申请时间',
					width : 80
				}, {
					name : 'APPROVE_STAT',
					header : '审批状态',
					renderer : function(value, p, r) {
						if (value == "1")
							return "<span style='color:blue;'>待审批</span>";
						else if (value == "2")
							return "<span style='color:green;'>审批通过</span>";
						else if (value == "3")
							return "<span style='color:red;'>审批拒绝</span>";
					},
					width : 80
				}, {
					name : 'APPROVE_DATE',
					sortable : true,
					header : '审批时间',
					width : 80
				}, {
					name : 'APPROVE_USER_NAME',
					sortable : true,
					header : '审批人',
					width : 80
				}],
		pagesize : 20,
		// 新增、修改、详情的form的字段
		formColums : function() {
			return new Ext.form.FieldSet({
						items : [
								util.layout._tr([util.form._td({
													name : 'CUST_ZH_NAME',
													readOnly : true,
													labelStyle : 'text-align:right;',
													xtype : 'textfield',
													fieldLabel : '客户名称'
												})], [util.form._td({
													name : 'CUST_ID',
													labelStyle : 'text-align:right;',
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '核心客户号'
												})], [util.form._td({
													name : 'CUST_LEV',
													readOnly : true,
													xtype : 'combo',
													fieldLabel : '客户级别',
													valueField : 'key',
													labelStyle : 'text-align:right;',
													displayField : 'value',
													store : custLevStore
												})]),
								util.layout._tr([util.form._td({
													name : 'BEFORE_MGR_NAME',
													readOnly : true,
													xtype : 'textfield',
													labelStyle : 'text-align:right;',
													fieldLabel : '原客户经理'
												})], [util.form._td({
													name : 'MANAGED_MGR_NAME',
													readOnly : true,
													labelStyle : 'text-align:right;',
													xtype : 'textfield',
													fieldLabel : '托管客户经理'
												})]),
								util.layout._tr([util.form._td({
													name : 'MAIN_TYPE',
													readOnly : true,
													xtype : 'combo',
													fieldLabel : '主协标志',
													valueField : 'key',
													labelStyle : 'text-align:right;',
													displayField : 'value',
													store : mainTypStore
												})], [util.form._td({
													name : 'MANAGED_END_DATE',
													readOnly : true,
													labelStyle : 'text-align:right;',
													xtype : 'textfield',
													fieldLabel : '托管结束时间'
												})])]
					})
		}
	});
	// $('managedStatCombo').disable();
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [listPanel]
			});
});
