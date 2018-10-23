/**
 * 营销管理->商机管理->我的商机 查询结果Table对象定义 wzy，2013-02-17
 */

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

/** ***************************定义列模型*********开始**************** */
var cm = new Ext.grid.ColumnModel([ rownum, sm, {
	header : '商机编号',
	dataIndex : 'opporId',
	hidden : true
}, {
	header : '商机备注',
	dataIndex : 'memo',
	hidden : true
}, {
	header : '商机状态',
	dataIndex : 'opporStat',
	hidden : true
}, {
	header : '客户状态',
	dataIndex : 'custType',
	hidden : true
}, {
	header : '客户类型',
	dataIndex : 'custCategory',
	hidden : true
}, {
	header : '商机阶段',
	dataIndex : 'opporStage',
	hidden : true
}, {
	header : '商机类型',
	dataIndex : 'opporType',
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
	header : '产品ID',
	dataIndex : 'prodId',
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
	header : '达成金额',
	dataIndex : 'reachAmount',
	hidden : true
}, {
	header : '商机名称',
	dataIndex : 'opporName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '商机状态',
	dataIndex : 'OPPOR_STAT_ORA',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '商机阶段',
	dataIndex : 'OPPOR_STAGE_ORA',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '商机来源',
	dataIndex : 'OPPOR_SOURCE_ORA',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '商机类型',
	dataIndex : 'OPPOR_TYPE_ORA',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '商机开始日期',
	dataIndex : 'opporStartDate',
	sortable : true,
	align : 'left',
	width : 90
}, {
	header : '商机完成日期',
	dataIndex : 'opporEndDate',
	sortable : true,
	align : 'left',
	width : 90
}, {
	header : '商机有效期',
	dataIndex : 'opporDueDate',
	sortable : true,
	align : 'left',
	width : 90
}, {
	header : '执行人',
	dataIndex : 'executeUserName',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '执行机构',
	dataIndex : 'executeOrgName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '商机内容',
	dataIndex : 'opporContent',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '客户名称',
	dataIndex : 'custName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '商机产品',
	dataIndex : 'prodName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '客户联系人',
	dataIndex : 'custContactName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '营销活动名称',
	dataIndex : 'mktActivName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '营销任务指标名称',
	dataIndex : 'mktTargetName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '客户状态',
	dataIndex : 'CUST_TYPE_ORA',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '客户类型',
	dataIndex : 'CUST_CATEGORY_ORA',
	sortable : true,
	align : 'left'
}, {
	header : '预计金额',
	dataIndex : 'planAmount',
	sortable : true,
	renderer:money('0,000.00'),
	align : 'right',
	width : 80
}, {
	header : '达成概率',
	dataIndex : 'reachProb',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '费用预算',
	dataIndex : 'planCost',
	sortable : true,
	renderer:money('0,000.00'),
	align : 'right',
	width : 80
}, {
	header : '创建人',
	dataIndex : 'createrName',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '创建机构',
	dataIndex : 'createOrgName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '创建时间',
	dataIndex : 'createDateTime',
	sortable : true,
	align : 'left',
	width : 130
}, {
	header : '最近更新人',
	dataIndex : 'updateUserName',
	sortable : true,
	align : 'left',
	width : 70
}, {
	header : '最近更新机构',
	dataIndex : 'updateOrgName',
	sortable : true,
	align : 'left',
	width : 120
}, {
	header : '最近更新时间',
	dataIndex : 'updateDateTime',
	sortable : true,
	align : 'left',
	width : 130
}, {
	header : '待分配机构',
	dataIndex : 'assignOrgName',
	hidden : true
}, {
	header : '认领人',
	dataIndex : 'claimUserName',
	hidden : true
}, {
	header : '认领机构',
	dataIndex : 'claimOrgName',
	hidden : true
} ]);
/** ***************************定义列模型*********结束**************** */

/** ***************************定义数据存储对象*********开始**************** */
var store = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/mktMyBusiOpporQueryAction.json',
		method : 'POST',
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		root : 'json.data',
		totalProperty : 'json.count'
	}, [ {
		name : 'opporId',
		mapping : 'OPPOR_ID'
	}, {
		name : 'memo',
		mapping : 'MEMO'
	}, {
		name : 'opporName',
		mapping : 'OPPOR_NAME'
	}, {
		name : 'opporStat',
		mapping : 'OPPOR_STAT'
	}, {
		name : 'opporType',
		mapping : 'OPPOR_TYPE'
	}, {
		name : 'opporStage',
		mapping : 'OPPOR_STAGE'
	}, {
		name : 'OPPOR_STAT_ORA'
	}, {
		name : 'OPPOR_STAGE_ORA'
	}, {
		name : 'OPPOR_SOURCE_ORA'
	}, {
		name : 'OPPOR_TYPE_ORA'
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
		name : 'mktTargetName',
		mapping : 'MKT_TARGET_NAME'
	}, {
		name : 'mktActivId',
		mapping : 'MKT_ACTIV_ID'
	}, {
		name : 'mktActivName',
		mapping : 'MKT_ACTIV_NAME'
	}, {
		name : 'opporContent',
		mapping : 'OPPOR_CONTENT'
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
		name : 'custName',
		mapping : 'CUST_NAME'
	}, {
		name : 'custContactName',
		mapping : 'CUST_CONTACT_NAME'
	}, {
		name : 'CUST_TYPE_ORA'
	}, {
		name : 'CUST_CATEGORY_ORA'
	}, {
		name : 'planAmount',
		mapping : 'PLAN_AMOUNT'
	}, {
		name : 'reachProb',
		mapping : 'REACH_PROB'
	}, {
		name : 'planCost',
		mapping : 'PLAN_COST'
	}, {
		name : 'createrId',
		mapping : 'CREATER_ID'
	}, {
		name : 'createrName',
		mapping : 'CREATER_NAME'
	}, {
		name : 'createOrgId',
		mapping : 'CREATE_ORG_ID'
	}, {
		name : 'createOrgName',
		mapping : 'CREATE_ORG_NAME'
	}, {
		name : 'createDateTime',
		mapping : 'CREATE_DATE_TIME'
	}, {
		name : 'updateUserId',
		mapping : 'UPDATE_USER_ID'
	}, {
		name : 'updateUserName',
		mapping : 'UPDATE_USER_NAME'
	}, {
		name : 'updateOrgId',
		mapping : 'UPDATE_ORG_ID'
	}, {
		name : 'updateOrgName',
		mapping : 'UPDATE_ORG_NAME'
	}, {
		name : 'updateDateTime',
		mapping : 'UPDATE_DATE_TIME'
	}, {
		name : 'executeUserId',
		mapping : 'EXECUTE_USER_ID'
	}, {
		name : 'executeUserName',
		mapping : 'EXECUTE_USER_NAME'
	}, {
		name : 'executeOrgId',
		mapping : 'EXECUTE_ORG_ID'
	}, {
		name : 'executeOrgName',
		mapping : 'EXECUTE_ORG_NAME'
	}, {
		name : 'assignOrgId',
		mapping : 'ASSIGN_OGR_ID'
	}, {
		name : 'assignOrgName',
		mapping : 'ASSIGN_ORG_NAME'
	}, {
		name : 'claimUserId',
		mapping : 'CLAIM_USER_ID'
	}, {
		name : 'claimUserName',
		mapping : 'CLAIM_USER_NAME'
	}, {
		name : 'claimOrgId',
		mapping : 'CLAIM_ORG_ID'
	}, {
		name : 'claimOrgName',
		mapping : 'CLAIM_ORG_NAME'
	}, {
		name : 'custType',
		mapping : 'CUST_TYPE'
	}, {
		name : 'custCategory',
		mapping : 'CUST_CATEGORY'
	}, {
		name : 'reachAmount',
		mapping : 'REACH_AMOUNT'
	}, {
		name : 'mainCustManager',
		mapping : 'MGR_NAME'
	}, {
		name : 'mainCustOrgname',
		mapping : 'INSTITUTION_NAME'
	} ])
});
/** ***************************定义数据存储对象*********结束**************** */
