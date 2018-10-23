

/*******************************************************************************
 * menu:客户管理--> 客户托管 --> 客户托管查询 auther by:sujm 2012-12-11
 */

Ext.onReady(function() {
	Ext.QuickTips.init();

	var mainTypeStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '主办'], ['2', '协办']]
			});
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
					direction : 'ASC'
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
		// autocroll: true,
		title : "客户管理 -> 客户托管 -> 客户托管查询",
		stUrl : basepath + '/customer_belong_info_query.json',// basepath +
		// '/customer_assign.json',
		// detailUrl : basepath + '/customer_belong_info_query.json',
		primary : "ID",
		checkbox : true,
		defaultLoad : false,
		// 定义查询条件Form的高度
		seFormHeight : 60,
		// 定义增删详情页面弹出窗口高度
		winHeight : 250,
		// 宽度
		winWidth : 1024,
		// gridHeight : 400,
		dbclick : false,
		selectItems : {
			layout : 'column',
			items : [{
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
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						defaultType : 'textfield',
						border : false,
						items : [{
									name : 'CUST_ZH_NAME',
									xtype : 'textfield',
									fieldLabel : '客户中文名称',
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
									store : custLevStore,
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
									// emptyText : '请选择',
									selectOnFocus : true,
									width : '100',
									anchor : '90%'
								}]
					}]
		},
		// 查询字段定义，若不定义则不出现查询条件From
		buts : [{
					id : 'assignOrg',
					xtype : 'button',
					tooltip : '取消托管',
					text : '取消托管',
					iconCls : 'deleteIconCss',
					listeners : {
						click : function(n) {
							var records = listPanel.grid.selModel
									.getSelections();// 得到被选择的行的数组
							var recordsLen = records.length;// 得到行数组的长度

							// debugger;
							for (var i = 0; i < records.length; i++) {
								if (records[i].json.APPROVE_STAT != '2') {
									Ext.Msg.alert('系统提示', '您所选的客户非托管状态，不能取消托管');
									return false;
								}
							}
							Ext.Msg.alert('系统提示', '取消托管成功');
						}

					}
				}],
		gclms : [{
					name : 'ID'
				}, {
					name : 'org_name'
				}, {
					name : 'CUST_ID',
					sortable : true
					// ,
				// header : '客户编号'
			}	, {
					name : 'CUST_ZH_NAME',
					sortable : true,
					header : '客户名称',
					width : 200
				}, {
					name : 'CUST_ID',
					header : '核心客户号',
					width : 150
					// type : 'mapping',
				// store : certTypStore,
				// mappingkey : 'key',
				// mappingvalue : 'value'
			}	, {
					name : 'BEFORE_MGR_NAME',
					sortable : true,
					header : '原客户经理'
				}, {
					name : 'CUST_LEV',
					width : '20',
					sortable : true,
					header : '客户级别',
					type : 'mapping',
					store : custLevStore,
					mappingkey : 'key',
					mappingvalue : 'value'
				}, {
					name : 'MANAGED_MGR_NAME',
					sortable : true,
					hidden : true,
					header : '托管客户经理'
				}, {
					name : 'CUST_LEV'// ,
					// sortable : true,
				// header : '客户级别',
				// type : 'mapping',
				// store : custLevStore,
				// mappingkey : 'key',
				// mappingvalue : 'value'
			}	, {
					name : 'MAIN_TYPE',
					sortable : true,
					header : '主协标志',
					type : 'mapping',
					store : mainTypeStore,
					mappingkey : 'key',
					mappingvalue : 'value',
					width : 100
				}, {
					name : 'APPROVE_STAT',
					sortable : true,
					header : '审批状态',
					type : 'mapping',
					store : appStatusStore,
					mappingkey : 'key',
					mappingvalue : 'value',
					width : 100
				}, {
					name : 'APPROVE_DATE',
					sortable : true,
					header : '审批时间'
				}, {
					name : 'APPROVE_USER_NAME',
					sortable : true,
					header : '审批人'
				}, {
					name : 'CONT_METH',
					sortable : true
					// ,
				// header : '首选手机号码'
			}	, {
					name : 'MANAGED_END_DATE',
					sortable : true,
					header : '托管结束时间',
					width : 120
				}],
		pagesize : 20
	});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [listPanel]
			});
});