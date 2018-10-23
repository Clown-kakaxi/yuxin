	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
			
		var actiStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : '/ocrmFSeGoods!searchActi.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data'
			}, [ 'key', 'value' ])
		});
		actiStore.load();
		
		var orgStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : '/ocrmFSeGoods!searchOrg.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data'
			}, [ 'key', 'value' ])
		});
		orgStore.load();
		
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/ocrmFSeGoodsHis.json'
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{name:'ID'},
				            {name:'CUST_ID'},
				            {name:'CUST_NAME'},
				            {name:'GOODS_NAME'},
				        		{name:'GOODS_NUMBER'},
				        		{name:'ORG_ID'},
								{name:'CREATE_NAME'},
				        		{name:'CREATE_DATE'},
				        		{name:'COMP_ACTI'},
				        		{name:'GIVE_NUMBER'},
				        		{name:'GIVE_DATE'},
				        		{name:'GIVE_REASON'},
				        		{name:'NEED_SCORE'},
				        		{name:'REMARK'}]
					)
	});
	    
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {xtype:'combo',fieldLabel: '单位名称',disabled:true,name: 'ORG_ID',labelStyle: 'text-align:right;',
                     			store: orgStore,valueField : 'key',displayField : 'value',anchor:'95%'},
					          {name : 'GOODS_NAME',xtype : 'textfield',fieldLabel : '礼品名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'CREATE_DATE',xtype : 'textfield',fieldLabel : '创建日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'GIVE_DATE',xtype : 'textfield',fieldLabel : '赠送日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
                         	
                         	]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {xtype:'combo',fieldLabel: '配合活动',disabled:true,name: 'COMP_ACTI',labelStyle: 'text-align:right;',
                     			store: actiStore,valueField : 'key',displayField : 'value',anchor:'95%'},
					          {name : 'GOODS_NUMBER',xtype : 'textfield',fieldLabel : '库存数量',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'GIVE_NUMBER',xtype : 'textfield',fieldLabel : '赠送数量',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'NEED_SCORE',xtype : 'textfield',fieldLabel : '所需积分',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					]
				}]
			},{
				layout : 'form',labelWidth:100,
				items:[{xtype : 'textarea',name : 'GIVE_REASON',fieldLabel : '赠送理由',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
				       {xtype : 'textarea',name : 'REMARK',fieldLabel : '备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
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
