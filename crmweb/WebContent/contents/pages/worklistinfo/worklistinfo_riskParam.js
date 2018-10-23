/**
 * 风险参数调整流程
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var apprId = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	var record = Ext.data.Record.create([
  		{name : 'RISK_CHARACT'},
  		{name : 'RISK_CHARACT_ORA'},
  		{name : 'LOWER_VALUE'},
  		{name : 'UPPER_VALUE'},
  		{name : 'IS_BEFORE'}
  	]);
    var store = new Ext.data.Store({
        restful:true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/riskParm!queryRiskHisByApprId.json'
        }),
        reader: new Ext.data.JsonReader({
            root:'json.data',
            totalProperty:'json.count'
        },record)
    });
    var cm = new Ext.grid.ColumnModel([
        {dataIndex:'RISK_CHARACT_ORA',header:'风险类型',width : 120,sortable : true},
        {dataIndex:'LOWER_VALUE',header:'下限值',width : 120,sortable : true},
        {dataIndex:'UPPER_VALUE',header:'上限值',width : 120,sortable : true},
        {dataIndex:'IS_BEFORE',header:'调整标识',width : 120,sortable : true,renderer:function(val){
        	if(val == '01'){
        		return '调整前';
        	}else if(val == '02'){
        		return '调整后';
        	}else{
        		return val;
        	}
        }}
    ]);
	var grid = new Ext.grid.GridPanel({
		height:160,
        autoScroll: true,
        stripeRows: true,
        store: store,
        cm : cm,
        viewConfig:{
        	getRowClass : function(record,rowIndex,rowParams,store){
        	  //根据调整标识修改背景颜色  
        	  if(record.data.IS_BEFORE=="02"){
        		  return 'my_row_setS';  //修改背景颜色
        	  }else{
        	  	  return '';
        	  }
        	}
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
			apprId : apprId
		}
	});
});
