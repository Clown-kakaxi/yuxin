/**
 * @description 对私授信信息审批展现界面
 * @since ?
 * @update 20140923
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	//var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({
        	url:basepath+'/perSxInfoReview.json',
        	method:'GET'
        }),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
			{name:'custId',mapping:'CUST_ID'},
			{name:'CORE_NO'},
            {name:'updateItem',mapping:'UPDATE_ITEM'},
            {name:'updateBeCont',mapping:'UPDATE_BE_CONT'},
            {name:'updateAfCont',mapping:'UPDATE_AF_CONT'},
            {name:'updateUser',mapping:'UPDATE_USER'},
            {name:'userName',mapping:'USER_NAME'},
            {name:'updateDate',mapping:'UPDATE_DATE'}
		])
	});
	
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
		
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, 
     	{header : '客户编号',dataIndex : 'custId', sortable : 100, width : 100},
     	{header : '核心客户号',dataIndex : 'CORE_NO', sortable : 100, width : 100},
     	{header : '变更项目', dataIndex : 'updateItem', sortable : true, width : 120 },
        {header : '变更前内容', dataIndex : 'updateBeCont', sortable : true, width : 120 },
        {header : '变更后内容', dataIndex : 'updateAfCont', sortable : true, width : 120},
        {header : '修改人', dataIndex : 'userName',sortable : true, width : 100 },
        {header : '修改时间',dataIndex : 'updateDate',sortable : true, width : 135}
	]);
	// 表格实例
	var grid = new Ext.grid.GridPanel({
		id:'viewgrid',
		frame : true,
		height:180,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		viewConfig:{
			   forceFit:false,
			   autoScroll:true
			},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
    var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[grid]
   }); 
	var EchainPanel = new Mis.Echain.EchainPanel({
		instanceID:instanceid,
		nodeId:nodeid,
		nodeName:curNodeObj.nodeName,
		fOpinionFlag:curNodeObj.fOpinionFlag,
		approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
		WindowIdclode:curNodeObj.windowid,
		callbackCustomFun:'3_a10##1'
	});
	var view = new Ext.Panel( {
		renderTo : 'viewEChian',
		  frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [bussFieldSetGrid,EchainPanel]

	});
	store.load({
		params:{
			'instanceId':instanceid
		}
	});
				
});
