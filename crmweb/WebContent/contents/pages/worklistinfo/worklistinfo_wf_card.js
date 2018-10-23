	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/cardApply.json'
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{ name :'CUST_ID'},
				            { name :'CUST_NAME'},
				            { name :'AMT_AVG_30DAYS'},
				            { name :'CUST_GRADE_ORA'},
				            { name :'CARD_LVL_ORA'},
				            { name :'CARD_NUM'},
				            { name :'H_CARD_LEV_ORA'},
				            { name :'CARD_LEV_APP_ORA'},
				            { name :'CARD_APP_VALIDATE'}]
					)
	});
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'AMT_AVG_30DAYS',xtype : 'textfield',fieldLabel : '滚动30天日均',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'CARD_LVL_ORA',xtype : 'textfield',fieldLabel : '可发卡等级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'H_CARD_LEV_ORA',xtype : 'textfield',fieldLabel : '客户持有卡最高等级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					          ]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					         {name : 'CUST_GRADE_ORA',xtype : 'textfield',fieldLabel : '客户等级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					         {name : 'CARD_NUM',xtype : 'textfield',fieldLabel : '客户已持卡数目',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					         {name : 'CARD_LEV_APP_ORA',xtype : 'textfield',fieldLabel : '本次申请发卡等级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
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
