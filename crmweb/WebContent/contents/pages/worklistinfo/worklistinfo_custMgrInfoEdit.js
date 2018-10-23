	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split(',')[1];
		var nodeid = curNodeObj.nodeid;
		var jsonListRecord = Ext.data.Record.create([  
		                                             {name:'CUST_MANAGER_ID'},
		                                             {name:'CUST_MANAGER_NAME'},
		                                             {name:'CERTIFICATE'}
		                                             ]);  
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy(
	        		{
	        			url:basepath+'/CustomerManagerInfoReviewAction.json'
	        		}),
	        reader: new Ext.data.JsonReader({
	        	root : 'json.data'
	        }, jsonListRecord
		)});
		
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'CUST_MANAGER_ID',xtype : 'textfield',fieldLabel : '客户经理编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'CUST_MANAGER_NAME',xtype : 'textfield',fieldLabel : '客户经理名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'CERTIFICATE',xtype : 'textfield',fieldLabel : '资格证书',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
                         	]
				}]
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
		store.load({params:{id:id,operate:'review'},
	        callback:function(){
	        	if(store.getCount()!=0){
	        		loadFormData();
	        	}
			}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
		}
		
				
	});
