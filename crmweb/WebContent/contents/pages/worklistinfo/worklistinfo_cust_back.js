/**
 * @description 客户退回工作流审批调用界面js
 * @author
 * @since ?
 * @update helin3,20140926,退回流程审批展示界面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
    var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({
			url:basepath+'/custBack.json'
		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, [{name:'CUST_ID'},
            {name:'CUST_NAME'},
            {name:'CUST_TYPE'},
            {name:'MGR_NAME'},
            {name:'MAIN_TYPE'},
            {name:'ORG_NAME'},
            {name:'BACK_REASON'}
        ])
	});
    var infoForm = new Ext.FormPanel( {
		frame : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth:100,
				items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				          {name : 'MGR_NAME',xtype : 'textfield',fieldLabel : '归属经理',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
				          ]
			},{
				layout : 'form',columnWidth : .5,labelWidth:100,
				items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				         {name : 'ORG_NAME',xtype : 'textfield',fieldLabel : '机构',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
			}]
		},{
			layout : 'form',labelWidth:100,
			items:[{xtype : 'textarea',name : 'BACK_REASON',fieldLabel : '退回原因',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
		}]
	});
    var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[infoForm]
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
    	if(store.getCount()!=0){
    		loadFormData();
    	}
	}});
	function loadFormData(){
		infoForm.getForm().loadRecord(store.getAt(0));
	}
});
