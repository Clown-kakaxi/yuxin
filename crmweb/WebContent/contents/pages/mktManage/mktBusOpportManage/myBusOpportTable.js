/**
 * 营销管理->商机管理->我的商机
 * 查询结果Table对象定义
 * wzy，2013-02-17
 */

//复选框
var sm = new Ext.grid.CheckboxSelectionModel();

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'NO',
	width : 28
});

/*****************************定义列模型*********开始*****************/
var cm = new Ext.grid.ColumnModel(
	[rownum, sm,
	 {header:'商机编号',dataIndex:'OPPOR_ID',hidden:true},
	 {header:'商机备注',dataIndex:'MEMO',hidden:true},
	 {header:'商机名称',dataIndex:'OPPOR_NAME',sortable:true,align:'left'},
	 {header:'商机状态',dataIndex:'OPPOR_STAT_ORA',sortable:true,align:'left'},
	 {header:'商机阶段',dataIndex:'OPPOR_STAGE_ORA',sortable:true,align:'left'},
	 {header:'商机来源',dataIndex:'OPPOR_SOURCE_ORA',sortable:true,align:'left'},
	 {header:'商机类型',dataIndex:'OPPOR_TYPE_ORA',sortable:true,align:'left'},
	 {header:'商机开始日期',dataIndex:'OPPOR_START_DATE',sortable:true,align:'left'},
	 {header:'商机完成日期',dataIndex:'OPPOR_END_DATE',sortable:true,align:'left'},
	 {header:'商机有效期',dataIndex:'OPPOR_DUE_DATE',sortable:true,align:'left'},
	 {header:'营销任务指标名称',dataIndex:'MKT_TARGET_NAME',sortable:true,align:'left'},
	 {header:'营销活动名称',dataIndex:'MKT_ACTIV_NAME',sortable:true,align:'left'},
	 {header:'商机内容',dataIndex:'OPPOR_CONTENT',sortable:true,align:'left'},
	 {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,align:'left'},
	 {header:'商机产品',dataIndex:'PROD_NAME',sortable:true,align:'left'},
	 {header:'客户联系人',dataIndex:'CUST_CONTACT_NAME',sortable:true,align:'left'},
	 {header:'客户类型',dataIndex:'CUST_TYPE_ORA',sortable:true,align:'left'},
	 {header:'客户类别',dataIndex:'CUST_CATEGORY_ORA',sortable:true,align:'left'},
	 {header:'预计金额',dataIndex:'PLAN_AMOUNT',sortable:true,align:'left'},
	 {header:'达成概率',dataIndex:'REACH_PROB',sortable:true,align:'left'},
	 {header:'费用预算',dataIndex:'PLAN_COST',sortable:true,align:'left'},
	 {header:'创建人',dataIndex:'CREATER_NAME',sortable:true,align:'left'},
	 {header:'创建机构',dataIndex:'CREATE_ORG_NAME',sortable:true,align:'left'},
	 {header:'创建时间',dataIndex:'CREATE_DATE_TIME',sortable:true,align:'left'},
	 {header:'最近更新人',dataIndex:'UPDATE_USER_NAME',sortable:true,align:'left'},
	 {header:'最近更新机构',dataIndex:'UPDATE_ORG_NAME',sortable:true,align:'left'},
	 {header:'最近更新时间',dataIndex:'UPDATE_DATE_TIME',sortable:true,align:'left'},
	 {header:'执行人',dataIndex:'EXECUTE_USER_NAME',sortable:true,align:'left'},
	 {header:'执行机构',dataIndex:'EXECUTE_ORG_NAME',sortable:true,align:'left'},
	 {header:'待分配机构',dataIndex:'ASSIGN_ORG_NAME',sortable:true,align:'left'},
	 {header:'认领人',dataIndex:'CLAIM_USER_NAME',sortable:true,align:'left'},
	 {header:'认领机构',dataIndex:'CLAIM_ORG_NAME',sortable:true,align:'left'}
	]
);
/*****************************定义列模型*********结束*****************/

/*****************************定义数据存储对象*********开始*****************/
var store = new Ext.data.Store({
	restful:true,	
    proxy : new Ext.data.HttpProxy({
    	url:basepath+'/myBusOpportQueryAction.json',
        method:'POST',
        failure : function(response) {
            var resultArray = Ext.util.JSON.decode(response.status);
            if(resultArray == 403) {
                Ext.Msg.alert('提示', response.responseText);
            }
        }
    }),
    reader: new Ext.data.JsonReader(
    	{
	        successProperty: 'success',
		    root:'json.data',
		    totalProperty: 'json.count'
    	}, 
    	[
		{name: 'OPPOR_ID'},
		{name: 'MEMO'},
        {name: 'OPPOR_NAME'},
        {name: 'OPPOR_STAT_ORA'},
        {name: 'OPPOR_STAGE_ORA'},
        {name: 'OPPOR_SOURCE_ORA'},
        {name: 'OPPOR_TYPE_ORA'},
        {name: 'OPPOR_START_DATE'},
        {name: 'OPPOR_END_DATE'},
        {name: 'OPPOR_DUE_DATE'},
        {name: 'MKT_TARGET_ID'},
        {name: 'MKT_TARGET_NAME'},
        {name: 'MKT_ACTIV_ID'},
        {name: 'MKT_ACTIV_NAME'},
        {name: 'OPPOR_CONTENT'},
        {name: 'PROD_ID'},
        {name: 'PROD_NAME'},
        {name: 'CUST_ID'},
        {name: 'CUST_NAME'},
        {name: 'CUST_CONTACT_NAME'},
        {name: 'CUST_TYPE_ORA'},
        {name: 'CUST_CATEGORY_ORA'},
        {name: 'PLAN_AMOUNT'},
        {name: 'REACH_PROB'},
        {name: 'PLAN_COST'},
        {name: 'CREATER_ID'},
        {name: 'CREATER_NAME'},
        {name: 'CREATE_ORG_ID'},
        {name: 'CREATE_ORG_NAME'},
        {name: 'CREATE_DATE_TIME'},
        {name: 'UPDATE_USER_ID'},
        {name: 'UPDATE_USER_NAME'},
        {name: 'UPDATE_ORG_ID'},
        {name: 'UPDATE_ORG_NAME'},
        {name: 'UPDATE_DATE_TIME'},
        {name: 'EXECUTE_USER_ID'},
        {name: 'EXECUTE_USER_NAME'},
        {name: 'EXECUTE_ORG_ID'},
        {name: 'EXECUTE_ORG_NAME'},
        {name: 'ASSIGN_OGR_ID'},
        {name: 'ASSIGN_ORG_NAME'},
        {name: 'CLAIM_USER_ID'},
        {name: 'CLAIM_USER_NAME'},
        {name: 'CLAIM_ORG_ID'},
        {name: 'CLAIM_ORG_NAME'}
        ]
    )
});
/*****************************定义数据存储对象*********结束*****************/