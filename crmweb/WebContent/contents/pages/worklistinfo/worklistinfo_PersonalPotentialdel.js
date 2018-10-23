/**
 * 个金潜在客户删除复核
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var cusId = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	var record = Ext.data.Record.create([
  		{name : 'CUS_ID'},
  		{name : 'CUS_NAME'},
  		{name : 'CUST_MGR_NAME'},
  		{name : 'MAIN_BR_NAME'},
  		{name : 'MKT_ACTIVITIE'},
  		{name : 'MKT_ACTIVITIE_V'},
  		{name : 'SOURCE_CHANNEL'},
  		{name : 'MOVER_USER'},
  		{name : 'STATE'},
  		{name : 'BACK_STATE'},
  		{name : 'CALL_NO'},
  		{name : 'DELETE_REASON'},
  		{name : 'DELETE_NOTE'}
  		
  	]);
    var store = new Ext.data.Store({
        restful:true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/myPotentialCustomer!queryLatentById.json'
        }),
        reader: new Ext.data.JsonReader({
            root:'json.data',
            totalProperty:'json.count'
        },record)
    });
    var cm = new Ext.grid.ColumnModel([
        {dataIndex:'CUS_ID',header:'客户号',width : 120,sortable : true},
        {dataIndex:'CUS_NAME',header:'客户名称',width : 120,sortable : true},
        {dataIndex:'CUST_MGR_NAME',header:'所属客户经理',width : 120,sortable : true},
        {dataIndex:'MAIN_BR_NAME',header:'所属机构',width : 120,sortable : true},
        {dataIndex:'MKT_ACTIVITIE_V',header:'营销活动',width : 300,sortable : true},
        {dataIndex:'SOURCE_CHANNEL',header:'来源渠道',width : 120,sortable : true},
        {dataIndex:'MOVER_USER',header:'分配回收人',width : 120,sortable : true},
        {dataIndex:'STATE',header:'潜在客户状态',width : 120,sortable : true},
        {dataIndex:'BACK_STATE',header:'分派状态',width : 120,sortable : true},
        {dataIndex:'CALL_NO',header:'手机号码',width : 120,sortable : true},
        {dataIndex:'DELETE_REASON',header:'删除原因',width : 120,sortable : true},
        {dataIndex:'DELETE_NOTE',header:'备注',width : 120,sortable : true}
    ]);
	var grid = new Ext.grid.GridPanel({
		height:160,
        autoScroll: true,
        stripeRows: true,
        store: store,
        cm : cm,
        viewConfig:{
        	
        },
        loadMask: {
            msg: '正在加载表格数据,请稍等...'
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
	var view = new Ext.Panel({
		renderTo : 'viewEChian',
		frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [bussFieldSetGrid,EchainPanel]
	});
	//加载参数调整数据
	store.load({
		params:{
			cusId : cusId
		}
	});
});