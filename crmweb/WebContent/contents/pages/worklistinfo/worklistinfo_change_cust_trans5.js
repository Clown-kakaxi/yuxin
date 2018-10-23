/**
 * @description 客户移交-待办工作审批
 * @author luyy
 * @since 2014-07-09
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
		
	var handStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=HAND_KIND'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	handStore.load();
	
	var transReasonStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=TRANS_REASON_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	transReasonStore.load();
	var transContentStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=TRANS_CONTENT_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	transContentStore.load();
		
	var store1 = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/transApply.json?type=1'
		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, [
			{name:'tMgrName',mapping:'T_MGR_NAME'},
    		{name:'tOrgName',mapping:'T_ORG_NAME'},
    		{name:'handKind',mapping:'HAND_KIND'},
    		{name:'handOverReason',mapping:'HAND_OVER_REASON'},
    		{name:'workInterfixDt',mapping:'WORK_INTERFIX_DT'},
    		{name:'applyType',mapping:'APPLY_TYPE'},
            {name: 'oldAum',mapping : 'OLD_AUM'},
            {name: 'oldCredit',mapping : 'OLD_CREDIT'},
            {name: 'transContent',mapping : 'TRANS_CONTENT'},
            {name: 'newAum',mapping:'NEW_AUM'},
            {name: 'newCredit',mapping:'NEW_CREDIT'},
            {name: 'transOther',mapping:'TRANS_OTHER'}
		])
	});
	    
	var transedStore = new Ext.data.Store( {
		restful : true,
	    proxy : new Ext.data.HttpProxy( {
	    	url : basepath + '/transApply.json?type=2'
	    }),
    	reader : new Ext.data.JsonReader( {
    		totalProperty : 'json.count',
    		root : 'json.data'
    	}, [ 
	    	{name: 'recordId',mapping : 'RECORD_ID'},
            {name: 'custId',mapping : 'CUST_ID'},
            {name:'custName',mapping:'CUST_NAME'},
            {name: 'mgrId',mapping : 'MGR_ID'},
            {name: 'mgrName',mapping : 'MGR_NAME'},
            {name: 'mainType',mapping : 'MAIN_TYPE'},
            {name: 'institution',mapping:'INSTITUTION'},
            {name: 'institutionName',mapping:'INSTITUTION_NAME'},
            {name: 'mainTypeNew',mapping:'MAIN_TYPE_NEW'}
        ])
	});
	    
	    
	var num1 = new Ext.grid.RowNumberer({
		header : 'No.',
	  	width : 35
	});
	                                           
	var cm1 = new Ext.grid.ColumnModel([
		num1,	// 定义列模型
	    {header : '', dataIndex : 'recordId',hidden : true}, 
	    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
	  	{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
		{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
		{header:'原客户经理',dataIndex:'mgrName',sortable : true,width : 100},
		{header:'原主协办关系',dataIndex:'mainType',hidden : true,sortable : true,width : 100,renderer:function(v){
			if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
		}},
		{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
		{header:'原归属机构',dataIndex:'institutionName',sortable : true,width : 100},
		{header:'新主协办关系',dataIndex:'mainTypeNew',hidden : true,width:100,renderer:function(v){
			if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
		}}
	]);
	var custGrid1 = new Ext.grid.EditorGridPanel({
		title : '移交客户',
	  	autoScroll : true,
	  	height:150,
	  	region : 'center',
	    store: transedStore,
	  	stripeRows : true, // 斑马线
	  	cm : cm1,
	  	viewConfig : {}
	});
	  
	var num = new Ext.grid.RowNumberer({
	  	header : 'No.',
	  	width : 35
	});
	                                           
	var cm = new Ext.grid.ColumnModel([
	   	num,	// 定义列模型
        {header : '', dataIndex : 'recordId',hidden : true}, 
        {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
		{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
		{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
		{header:'原客户经理',dataIndex:'mgrName',sortable : true,width : 100},
		{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
		{header:'原归属机构',dataIndex:'institutionName',sortable : true,width : 100}
	]);
	var custGrid = new Ext.grid.EditorGridPanel({
	  	title : '移交客户',
	  	autoScroll : true,
	  	height:150,
	  	region : 'center',
	    store: transedStore,
	  	stripeRows : true, // 斑马线
	  	cm : cm,
	  	viewConfig : {}
	});
	  
	var transForm1 = new Ext.FormPanel({
		frame : true,
	    height: 240,
	    region : 'north',
	    items : [{
	    	layout : 'column',
	    	items : [{
	    		layout : 'form',
	    		columnWidth : .5,
	    		labelWidth:100,
	    		items : [ 
	    			{xtype:'textfield',name : 'tMgrName',fieldLabel : '接收客户经理',labelStyle: 'text-align:right;',readOnly:true,anchor : '95%'},
	    			{xtype:'numberfield',name:'oldAum',fieldLabel : '原AUM金额',labelStyle : 'text-align:right;',anchor : '95%',readOnly:true},
					{xtype:'numberfield',name:'oldCredit',fieldLabel : '原授信金额',labelStyle : 'text-align:right;',anchor : '95%',readOnly:true},
					{xtype:'lovcombo',name : 'transContent',hiddenName:'transContent',fieldLabel: '转移内容',labelStyle: 'text-align:right;',
             			forceSelection : true,triggerAction:'all',mode:'local',store:transContentStore,valueField:'key',displayField:'value',emptyText:'请选择',readOnly:true,anchor : '95%',resizable:true},
	    			{xtype:'combo',name : 'handKind',hiddenName:'handKind',fieldLabel: '客户移交类别',forceSelection : true,
	                 	labelStyle: 'text-align:right;',triggerAction:'all',mode:'local',store:handStore,valueField:'key',displayField:'value',emptyText:'请选择',readOnly:true,anchor : '95%'}
	    			
	    		]
	    	},{
	    		columnWidth : .5,
	    		layout : 'form',
	    		labelWidth:100,
	    		items : [
	    			{xtype:'textfield',name:'tOrgName',fieldLabel : '接收机构',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
	    			{xtype:'hidden',name:'tOrgId',fieldLabel : '接收机构',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
	    			{xtype:'numberfield',name:'newAum',fieldLabel : '新AUM金额',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
					{xtype:'numberfield',name:'newCredit',fieldLabel : '新授信金额',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
					{xtype:'textfield',name:'transOther',fieldLabel : '转移内容(其它)',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
					{xtype : 'datefield',name : 'workInterfixDt',format : 'Y-m-d',fieldLabel : '客户移交生效日',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'}
	           	]
	    	},{
	    		layout : 'form',
	    		columnWidth : 1,
	    		labelWidth:100,
	    		items : [ 
	    			{name : 'handOverReason',xtype : 'textarea',fieldLabel : '客户移交原因',labelStyle : 'text-align:right;',anchor : '97.5%'}
	    		]
	    	}]
	    }]
	});
    var cTrans = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[transForm1,custGrid,custGrid1]
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
		items : [cTrans,EchainPanel]

	});
			
	store1.load({
		params : {
			applyNo:id
    	},
        callback:function(){
        	if(store1.getCount()!=0){
        		loadFormData();
        	}
		}
	});
	function loadFormData(){
		if(store1.getAt(0).data.applyType =='1'||store1.getAt(0).data.applyType =='2'){
			custGrid.show();
			custGrid1.hide();
			setFieldVisible(true);
			transForm1.getForm().findField('handKind').bindStore(transReasonStore);
		}else{
			custGrid.hide();
			custGrid1.show();
			setFieldVisible(false);
			transForm1.getForm().findField('handKind').bindStore(handStore);
		}
		transForm1.getForm().loadRecord(store1.getAt(0));
	}
	
	function setFieldVisible(bool){
		transForm1.getForm().findField('oldAum').setVisible(bool);
		transForm1.getForm().findField('newAum').setVisible(bool);
		transForm1.getForm().findField('oldCredit').setVisible(bool);
		transForm1.getForm().findField('newCredit').setVisible(bool);
		transForm1.getForm().findField('transContent').setVisible(bool);
		transForm1.getForm().findField('transOther').setVisible(bool);
	}
	
	transedStore.load({
		 params : {
			'applyNo':id
		 }
	});
});
