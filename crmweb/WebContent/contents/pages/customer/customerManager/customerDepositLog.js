Ext.onReady(function() {
	Ext.QuickTips.init();

	// 审批状态下拉框的数据查询
	var appStatusStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=APPLY_STATUS'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	appStatusStore.load();
	// 管户类型
	var maintainTypeStore = new Ext.data.Store({
				sortInfo : {
					field : 'key',
					direction : 'ASC' // or 'DESC' (case sensitive for local
					// sorting)
				},
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=MAINTAIN_TYPE'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	// 客户转移审批展现的panel
	var spListPanel = new Mis.Ext.CrudPanel({
		id : "spListPanel",
		title : "客户托管->客户托管日志",
		// closable : false, // 在选项卡上，不显示关闭按钮
		stUrl : basepath
				+ '/customertransferapploginfoaction.json?adjSign=customerDeposit',
		primary : "ID",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 60,
		// 定义增删详情页面弹出窗口高度
		winHeight : 300,
		// 宽度
		winWidth : 600,

		spIdStr : '',
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems : {
			layout : 'column',
			items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						defaultType : 'textfield',
						border : false,
						items : [{
									name : 'custId',
									xtype : 'textfield',
									fieldLabel : '客户号',
									width : '100',
									anchor : '90%'
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100,
						defaultType : 'textfield',
						border : false,
						items : [{
									xtype : 'datefield',
									fieldLabel : '调整日期从',
									format : 'Y-m-d',
									name : 'startTime',
									labelStyle : 'text-align:right;',
									anchor : '90%'
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						defaultType : 'textfield',
						border : false,
						items : [{
									xtype : 'datefield',
									fieldLabel : '调整日期到',
									format : 'Y-m-d',
									name : 'endTime',
									labelStyle : 'text-align:right;',
									anchor : '90%'
								}]
					}]
		},
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [{
					name : 'custId',
					mapping : 'CUST_ID',
					header : '客户号',
					width : 120
				}, {
					name : 'custName',
					mapping : 'CUST_NAME',
					header : '客户名称',
					width : 180
				}, {
					name : 'beforeOrgId',
					mapping : 'BEFORE_ORG_ID',
					header : '发起机构代号',
					width : 90
				}, {
					name : 'applyOrgName',
					mapping : 'APPLY_ORG_NAME',
					header : '发起机构名称'
				}, {
					name : 'applyUserId',
					mapping : 'APPLY_USER_ID'// 发起人编号
				}, {
					name : 'applyDate',
					mapping : 'APPLY_DATE'// 发起日期
				}, {
					name : 'applyUserName',
					mapping : 'APPLY_USER_NAME',
					header : '发起人姓名',
					width : 90
				}, {
					name : 'OPER_TYPE',
					mapping : 'OPER_TYPE',
					header : '调整类型',
					width : 80,
					renderer : function(value) {
						if (value == '1') {
							return '转移';

						}
						if (value == '2') {
							return '托管';

						} else {
							return '未知';
						}
					}
				}, {
					name : 'oldMgrName',
					mapping : 'OLD_MGR_NAME',
					header : '原客户经理姓名'
				}, {
					name : 'applyUserName',
					mapping : 'APPLY_USER_NAME',
					header : '新客户经理姓名'
				}, {
					name : 'appStatus',
					mapping : 'APP_STATUS',
					header : '分配状态',
					width : 90,
					renderer : function(value, p, r) {
						if (value == "1")
							return "<span style='color:blue;'>待审批</span>";
						else if (value == "2")
							return "<span style='color:green;'>审批通过</span>";
						else if (value == "3")
							return "<span style='color:red;'>审批拒绝</span>";
					}
				}, {
					name : 'appDate3',
					mapping : 'APP_DATE3',
					header : '调整完成日期'/*
										 * , type : 'date'
										 */
				}, {
					name : 'rightType',
					mapping : 'RIGHT_TYPE',
					type : 'mapping',
					store : maintainTypeStore,
					mappingkey : 'key',
					mappingvalue : 'value'
				}, {
					name : 'oldMgrId',
					mapping : 'OLD_MGR_ID'// 原客户经理ID
				}/*
					 * , { name : 'beforeOrgId', mapping:'BEFORE_ORG_ID',
					 * hidden:true, header : '客户原所属机构号' }
					 */, {
					name : 'remark',
					mapping : 'REMARK',
					width : '200'
				}],
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			// Ext.getCmp('applyDate').setValue(new Date(b.applyDate.time));
			Ext.getCmp('applyDate').setValue(b.APPLY_DATE);
		},
		// 新增、修改、详情的form的字段
		fclms : [{
					layout : 'form',
					items : [{
								layout : 'column',
								items : [{
											columnWidth : .5,
											layout : 'form',
											items : [{
														name : 'custId',
														xtype : 'textfield',
														readOnly : true,
														fieldLabel : '客户号',
														width : '100',
														anchor : '90%'
													}]
										}, {
											columnWidth : .5,
											layout : 'form',
											items : [{
														store : appStatusStore,
														xtype : 'combo',
														readOnly : true,
														resizable : true,
														fieldLabel : '审批状态',
														name : 'appStatus',
														hiddenName : 'appStatus',
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
													}]
										}]
							}, {
								layout : 'column',
								items : [{
											columnWidth : .5,
											layout : 'form',
											items : [{
														name : 'applyUserName',
														xtype : 'textfield',
														fieldLabel : '发起人',
														width : '100',
														anchor : '90%',
														readOnly : true
													}]
										}, {
											columnWidth : .5,
											layout : 'form',
											items : [{
														id : 'applyDate',
														name : 'applyDate',
														xtype : 'datefield',
														format : 'Y-m-d',
														readOnly : true,
														fieldLabel : '申请时间',
														width : '100',
														anchor : '90%'
													}]
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								border : false,
								items : [{
											name : 'remark',
											xtype : 'textarea',
											readOnly : true,
											fieldLabel : '申请备注',
											width : '100',
											anchor : '95%'
										}]
							}]
				}]

	});

	// var tabs = new Ext.TabPanel({
	// xtype : "tabpanel",
	// region : "center",
	// activeTab : 0,
	// items : [spListPanel]
	// });

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [spListPanel]
			});
});