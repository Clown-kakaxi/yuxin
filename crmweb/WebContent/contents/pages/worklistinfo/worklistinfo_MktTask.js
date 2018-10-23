/**
 * @description 营销任务反馈审批页面js
 * @author
 * @since ?
 * @update 
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
    var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/mktTaskApro.json'
        		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, [{name:'OPER_OBJ_NAME',maping:'OPER_OBJ_NAME'},
        	{name:'TARGET_NAME',maping:'TARGET_NAME'},
        	{name:'TARGET_VALUE',maping:'TARGET_VALUE'},
        	{name:'ACHIEVE_VALUE',maping:'ACHIEVE_VALUE'},
        	{name:'ACHIEVE_REMARK',maping:'ACHIEVE_REMARK'}
           ]
		)
	});
	var cm=new Ext.grid.ColumnModel([
		{header:'执行对象',dataIndex:'OPER_OBJ_NAME',sortable:true,width:120,hidden:false},
		{header:'营销任务',dataIndex:'TARGET_NAME',sortable:true,width:120,hidden:false},
		{header:'目标值',dataIndex:'TARGET_VALUE',sortable:true,width:120,hidden:false},
		{header:'完成值',dataIndex:'ACHIEVE_VALUE',sortable:true,width:120,hidden:false},
		{header:'反馈说明',dataIndex:'ACHIEVE_REMARK',sortable:true,width:300,hidden:false}
	]);
	var infoGrid=new Ext.grid.GridPanel ({
		autoScroll:true,
		height:200,
		store:store,
		frame:true,
		cm:cm,
		loadMask:{
			msg : '正在加载表格数据,请稍等...'
		}
		
	});
    var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[infoGrid]
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
			
	store.load({params : {
		id:id
    },
    callback:function(){
    	
	}});
});
