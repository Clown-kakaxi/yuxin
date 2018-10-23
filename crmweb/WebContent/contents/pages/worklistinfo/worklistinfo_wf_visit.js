	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var type = instanceid.split('_')[2];
		var nodeid = curNodeObj.nodeid;
		
		var store;
		var infoForm;
		if(type == '1'){
		   store = new Ext.data.Store({
							restful:true,	
					        proxy : new Ext.data.HttpProxy(
					        		{
					        			url:basepath+'/mktVisitP.json'
					        		}),
					        reader: new Ext.data.JsonReader({
					        	root : 'json.data'
					        }, [{name:'CUST_ID'},
					            {name:'CUST_NAME'},
					            {name:'JOB_TYPE_ORA'},
					            {name:'IF_NEW_ORA'},
					            {name:'USER_NAME'},
					            {name:'VISIT_DATE'},
					            {name:'VISIT_RESULT'},
					            {name:'VISIT_INFO'}]
						)
		    });
		    infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'JOB_TYPE_ORA',xtype : 'textfield',fieldLabel : '职业类别',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_NEW_ORA',xtype : 'textfield',fieldLabel : '是否新客户拜访',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'USER_NAME',xtype : 'textfield',fieldLabel : '拜访人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'VISIT_INFO',fieldLabel : '拜访内容',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'VISIT_RESULT',fieldLabel : '拜访结果',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
		}
		if(type == '2'){//对公
			   store = new Ext.data.Store({
								restful:true,	
						        proxy : new Ext.data.HttpProxy(
						        		{
						        			url:basepath+'/mktVisitC.json'
						        		}),
						        reader: new Ext.data.JsonReader({
						        	root : 'json.data'
						        }, [{name:'CUST_ID'},
						            {name:'CUST_NAME'},
						            {name:'VISIT_RESULT'},
						            {name:'VISIT_RESULT_ORA'},
						            {name:'INDUST_TYPE_ORA'},
						            {name:'USER_NAME'},
						            {name:'VISIT_DATE'},
						            {name:'OWN_VISIT_MAN'},
						            {name:'VISIT_MAN'},
						            {name:'CUST_SOURCE'},
						            {name:'CUST_SOURCE_ORA'},
						            {name:'CUST_SOURCE_MAN'},
						            {name:'CUST_SOURCE_TEL'},
						            {name:'REFUSE_REASON'},
						            {name:'VISIT_INFO'}]
							)
			    });
			    infoForm = new Ext.FormPanel( {
					frame : true,
					items : [ {
						layout : 'column',
						items : [{
							layout : 'form',columnWidth : .5,labelWidth:100,
							items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'INDUST_TYPE_ORA',xtype : 'textfield',fieldLabel : '职业类别',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'CUST_SOURCE_ORA',xtype : 'textfield',fieldLabel : '客户来源',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'CUST_SOURCE_MAN',xtype : 'textfield',fieldLabel : '客户转介人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {xtype : 'textfield',name : 'VISIT_RESULT_ORA',fieldLabel : '拜访结果',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
						},{
							layout : 'form',columnWidth : .5,labelWidth:100,
							items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							         {name : 'VISIT_MAN',xtype : 'textfield',fieldLabel : '拜访人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							         {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							         {name : 'CUST_SOURCE_TEL',xtype : 'textfield',fieldLabel : '客户转介人电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						}]
					},{
						layout : 'form',labelWidth:100,
						items:[{xtype : 'textfield',name : 'OWN_VISIT_MAN',fieldLabel : '本行参加人员',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
						       {xtype : 'textarea',name : 'VISIT_INFO',fieldLabel : '拜访内容',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
						       {xtype : 'textarea',name : 'REFUSE_REASON',fieldLabel : '拒绝理由',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
					}]
				});
			}
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
    		if(type == '2'){
    			if(store.getAt(0).data.VISIT_RESULT =='2'){
        			infoForm.form.findField("REFUSE_REASON").show();
        		}else{
					infoForm.form.findField("REFUSE_REASON").hide();
				}
        		if(store.getAt(0).data.CUST_SOURCE =='11'){
        			infoForm.form.findField("CUST_SOURCE_MAN").show();
        			infoForm.form.findField("CUST_SOURCE_TEL").show();
        		}else{
        			infoForm.form.findField("CUST_SOURCE_MAN").hide();
        			infoForm.form.findField("CUST_SOURCE_TEL").hide();
        		}
    		}
		}
		
		
	});
