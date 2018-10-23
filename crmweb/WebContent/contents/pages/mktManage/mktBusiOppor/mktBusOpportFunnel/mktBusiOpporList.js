/**
 * 营销管理->商机管理->商机漏斗：从查询结果列表的超链接，打开商机列表页面，JS文件；wzy；2013-03-27
 */
// 列表数据存储对象
var store_list = null;
// 分页工具栏
var bbar = null;

store_list = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath
								+ '/mktBusiOpporListSuperLinkQueryAction.json'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'json.count',
						root : 'json.data'
					}, [{
								name : 'opporId',
								mapping : 'OPPOR_ID'
							}, {
								name : 'opporName',
								mapping : 'OPPOR_NAME'
							}, {
								name : 'opporStat',
								mapping : 'OPPOR_STAT'
							}, {
								name : 'opporStatOra',
								mapping : 'OPPOR_STAT_ORA'
							}, {
								name : 'opporStage',
								mapping : 'OPPOR_STAGE'
							}, {
								name : 'opporStageOra',
								mapping : 'OPPOR_STAGE_ORA'
							}, {
								name : 'opporSource',
								mapping : 'OPPOR_SOURCE'
							}, {
								name : 'opporSourceOra',
								mapping : 'OPPOR_SOURCE_ORA'
							}, {
								name : 'opporType',
								mapping : 'OPPOR_TYPE'
							}, {
								name : 'opporTypeOra',
								mapping : 'OPPOR_TYPE_ORA'
							}, {
								name : 'opporStartDate',
								mapping : 'OPPOR_START_DATE'
							}, {
								name : 'opporEndDate',
								mapping : 'OPPOR_END_DATE'
							}, {
								name : 'opporDueDate',
								mapping : 'OPPOR_DUE_DATE'
							}, {
								name : 'mktTargetId',
								mapping : 'MKT_TARGET_ID'
							}, {
								name : 'mktActivId',
								mapping : 'MKT_ACTIV_ID'
							}, {
								name : 'mktTargetName',
								mapping : 'MKT_TARGET_NAME'
							}, {
								name : 'mktActivName',
								mapping : 'MKT_ACTIV_NAME'
							}, {
								name : 'opporContent',
								mapping : 'OPPOR_CONTENT'
							}, {
								name : 'custId',
								mapping : 'CUST_ID'
							}, {
								name : 'custName',
								mapping : 'CUST_NAME'
							}, {
								name : 'custContactName',
								mapping : 'CUST_CONTACT_NAME'
							}, {
								name : 'prodId',
								mapping : 'PROD_ID'
							}, {
								name : 'prodName',
								mapping : 'PROD_NAME'
							}, {
								name : 'custType',
								mapping : 'CUST_TYPE'
							}, {
								name : 'custTypeOra',
								mapping : 'CUST_TYPE_ORA'
							}, {
								name : 'custCategory',
								mapping : 'CUST_CATEGORY'
							}, {
								name : 'custCategoryOra',
								mapping : 'CUST_CATEGORY_ORA'
							}, {
								name : 'planAmount',
								mapping : 'PLAN_AMOUNT'
							}, {
								name : 'reachProb',
								mapping : 'REACH_PROB_ORA'
							}, {
								name : 'planCost',
								mapping : 'PLAN_COST'
							}, {
								name : 'createrName',
								mapping : 'CREATER_NAME'
							}, {
								name : 'createrId',
								mapping : 'CREATER_ID'
							}, {
								name : 'createOrgName',
								mapping : 'CREATE_ORG_NAME'
							}, {
								name : 'createDateTime',
								mapping : 'CREATE_DATE_TIME'
							}, {
								name : 'updateUserName',
								mapping : 'UPDATE_USER_NAME'
							}, {
								name : 'updateOrgName',
								mapping : 'UPDATE_ORG_NAME'
							}, {
								name : 'updateDateTime',
								mapping : 'UPDATE_DATE_TIME'
							}, {
								name : 'executeUserName',
								mapping : 'EXECUTE_USER_NAME'
							}, {
								name : 'executeUserId',
								mapping : 'EXECUTE_USER_ID'
							}, {
								name : 'executeOrgName',
								mapping : 'EXECUTE_ORG_NAME'
							}, {
								name : 'assignOrgId',
								mapping : 'ASSIGN_OGR_ID'
							}, {
								name : 'assingOrgName',
								mapping : 'ASSIGN_ORG_NAME'
							}, {
								name : 'claimUserName',
								mapping : 'CLAIM_USER_NAME'
							}, {
								name : 'claimOrgName',
								mapping : 'CLAIM_ORG_NAME'
							}, {
								name : 'OPPOR_STAT'
							}, {
								name : 'reachAmount',
								mapping : 'REACH_AMOUNT'
							}, {
								name : 'assignOrgName',
								mapping : 'ASSIGN_ORG_NAME'
							}, {
								name : 'memo',
								mapping : 'MEMO'
							}, {
								name : 'mainCustManager',
								mapping : 'MGR_NAME'
							}, {
								name : 'mainCustOrgname',
								mapping : 'INSTITUTION_NAME'
							}])
		});

// 每页显示条数下拉选择框
var pagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'],
								[100, '100条/页'], [250, '250条/页'],
								[500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});

pagesize_combo.on("select", function(comboBox) { // 改变每页显示条数reload数据
			bbar.pageSize = parseInt(pagesize_combo.getValue()), store_list
					.reload({
								params : {
									start : 0,
									limit : parseInt(pagesize_combo.getValue())
								}
							});
		});

var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 定义列模型
var cm = new Ext.grid.ColumnModel([rownum, {
			header : '商机编号',
			dataIndex : 'opporId',
			hidden : true
		}, {
			header : '商机备注',
			dataIndex : 'memo',
			hidden : true
		}, {
			header : '产品ID',
			dataIndex : 'prodId',
			hidden : true
		}, {
			header : '达成金额',
			dataIndex : 'reachAmount',
			hidden : true
		}, {
			header : '待分配机构',
			dataIndex : 'assignOrgName',
			hidden : true
		}, {
			header : '认领人',
			dataIndex : 'claimUserName',
			hidden : true
		}, {
			header : '产品状态',
			dataIndex : 'OPPOR_STAT',
			hidden : true
		}, {
			header : '执行人',
			dataIndex : 'executeUserId',
			hidden : true
		}, {
			header : '待分配机构ID',
			dataIndex : 'assignOrgId',
			hidden : true
		}, {
			header : '创建人ID',
			dataIndex : 'createrId',
			hidden : true
		}, {
			header : '营销活动ID',
			dataIndex : 'mktActivId',
			hidden : true
		}, {
			header : '营销任务指标ID',
			dataIndex : 'mktTargetId',
			hidden : true
		}, {
			header : '客户ID',
			dataIndex : 'custId',
			hidden : true
		}, {
			header : '主办客户经理名称',
			dataIndex : 'mainCustManager',
			hidden : true
		}, {
			header : '主办机构名称',
			dataIndex : 'mainCustOrgname',
			hidden : true
		}, {
			header : '商机名称',
			dataIndex : 'opporName',
			sortable : true,
			width : 120,
			renderer : function(val) {
				// 增加超链接（有下划线）
				return '<span style="color:red;cursor:hand"><u>' + val
						+ '</u></span>';
			}
		}, {
			header : '商机状态',
			dataIndex : 'opporStatOra',
			sortable : true,
			width : 70
		}, {
			header : '商机阶段',
			dataIndex : 'opporStageOra',
			sortable : true,
			width : 70
		}, {
			header : '商机来源',
			dataIndex : 'opporSourceOra',
			sortable : true,
			width : 70
		}, {
			header : '商机类型',
			dataIndex : 'opporTypeOra',
			sortable : true,
			width : 70
		}, {
			header : '商机开始日期',
			dataIndex : 'opporStartDate',
			sortable : true,
			width : 90
		}, {
			header : '商机完成日期',
			dataIndex : 'opporEndDate',
			sortable : true,
			width : 90
		}, {
			header : '商机有效期',
			dataIndex : 'opporDueDate',
			sortable : true,
			width : 90
		}, {
			header : '执行人',
			dataIndex : 'executeUserName',
			sortable : true,
			width : 70
		}, {
			header : '执行机构',
			dataIndex : 'executeOrgName',
			sortable : true,
			width : 120
		}, {
			header : '商机内容',
			dataIndex : 'opporContent',
			sortable : true,
			width : 120
		}, {
			header : '客户名称',
			dataIndex : 'custName',
			sortable : true,
			width : 120,
			renderer : function(val) {
				// 增加超链接（有下划线）
				return '<span style="color:red;cursor:hand"><u>' + val
						+ '</u></span>';
			}
		}, {
			header : '商机产品',
			dataIndex : 'prodName',
			sortable : true,
			width : 120
		}, {
			header : '客户联系人',
			dataIndex : 'custContactName',
			sortable : true,
			width : 120
		}, {
			header : '营销活动名称',
			dataIndex : 'mktActivName',
			sortable : true,
			width : 120
		}, {
			header : '营销任务指标名称',
			dataIndex : 'mktTargetName',
			sortable : true,
			width : 120
		}, {
			header : '客户状态',
			dataIndex : 'custTypeOra',
			sortable : true,
			width : 70
		}, {
			header : '客户类型',
			dataIndex : 'custCategoryOra',
			sortable : true,
			width : 70
		}, {
			header : '预计金额',
			dataIndex : 'planAmount',
			sortable : true,
			renderer : money('0,000.00'),
			align : 'right',
			width : 80
		}, {
			header : '达成概率',
			dataIndex : 'reachProb',
			sortable : true,
			width : 70
		}, {
			header : '费用预算',
			dataIndex : 'planCost',
			sortable : true,
			renderer : money('0,000.00'),
			align : 'right',
			width : 80
		}, {
			header : '创建人',
			dataIndex : 'createrName',
			sortable : true,
			width : 70
		}, {
			header : '创建机构',
			dataIndex : 'createOrgName',
			sortable : true,
			width : 120
		}, {
			header : '创建时间',
			dataIndex : 'createDateTime',
			sortable : true,
			width : 130
		}, {
			header : '最近更新人',
			dataIndex : 'updateUserName',
			sortable : true,
			width : 70
		}, {
			header : '最近更新机构',
			dataIndex : 'updateOrgName',
			sortable : true,
			width : 120
		}, {
			header : '最近更新时间',
			dataIndex : 'updateDateTime',
			sortable : true,
			width : 130
		}, {
			header : '待分配机构',
			dataIndex : 'assingOrgName',
			sortable : true,
			width : 120
		}, {
			header : '认领人',
			dataIndex : 'claimUserName',
			sortable : true,
			width : 70
		}, {
			header : '认领机构',
			dataIndex : 'claimOrgName',
			sortable : true,
			width : 120
		}]);

var number = parseInt(pagesize_combo.getValue());

// 分页工具栏
bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : store_list,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', pagesize_combo]
		});

var mktBusiOpporListPanel = new Ext.grid.GridPanel({
			layout : 'fit',
			frame : true,
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : store_list,
			stripeRows : true, // 斑马线
			cm : cm,
			bbar : bbar,
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 给“商机名称”、“客户名称”超链接增加单击事件处理
mktBusiOpporListPanel.on('cellclick', function(gridPara, row, col) {// 获取编辑的行数，从0开始
			if (col == 16) {// 点击第16列，弹出商机详情窗体
				var record = gridPara.getSelectionModel().getSelected();
				viewChanceForm.getForm().reset();
				viewChanceForm.getForm().loadRecord(record);
				viewChanceWindow.show();
			} else if (col == 27) {// 点击第16列，弹出客户视图窗体
				var checkedNodes = gridPara.getSelectionModel().selections.items;
				var viewWindows = new Com.yucheng.crm.cust.ViewWindow({
							id : 'viewWindows',
							custId : checkedNodes[0].data.custId,
							custName : checkedNodes[0].data.custName,
							custTyp : checkedNodes[0].data.custType
						});

				Ext.Ajax.request({
							url : basepath + '/commsearch!isMainType.json',
							method : 'GET',
							params : {
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
								oCustInfo.cust_name = checkedNodes[0].data.custName;
								oCustInfo.cust_type = checkedNodes[0].data.custType;
								viewWindows.show();
							},
							failure : function(form, action) {
							}
						});
			}
		});

// 商机阶段
var opporStage = null;

// 展示商机列表的窗体
var mktBusiOpporListWindow = new Ext.Window({
			title : '商机列表',
			plain : true,
			layout : 'fit',
			width : 900,
			height : 450,
			resizable : true,
			draggable : true,
			closable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			loadMask : true,
			maximizable : true,
			collapsible : true,
			titleCollapse : true,
			buttonAlign : 'right',
			border : false,
			constrain : true,
			items : [mktBusiOpporListPanel],
			listeners : {
				'beforeshow' : function() {// 打开页面就进行数据查询
					var conditionStr = qForm.getForm().getFieldValues();
					var paraJson = Ext.encode(conditionStr);
					store_list.on('beforeload', function() {
								this.baseParams = {
									"condition" : paraJson
								};
							});
					store_list.load({
								params : {
									start : 0,
									limit : bbar.pageSize,
									opporStage : opporStage
								}
							});
				}
			}
		});