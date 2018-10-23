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
		
		var level = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=GOODS_CUST_LEVEL'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		level.load();
		
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
				        			url:basepath+'/ocrmFSeGoods.json'
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{name:'ID'},
				            {name:'GOODS_NAME'},
				        		{name:'GOODS_NUMBER'},
				        		{name:'ORG_ID'},
				        		{name:'CREATE_ID'},
								{name:'CREATE_NAME'},
				        		{name:'CREATE_DATE'},
				        		{name:'COMP_ACTI'},
				        		{name:'CUST_LEVEL'},
				        		{name:'GOODS_PRICE'},
				        		{name:'GOODS_SCORE'}]
					)
	});
	    
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'GOODS_NAME',xtype : 'textfield',fieldLabel : '礼品名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {xtype:'combo',fieldLabel: '配合活动',disabled:true,name: 'COMP_ACTI',labelStyle: 'text-align:right;',
                         	store:actiStore ,valueField : 'key',displayField : 'value',anchor:'95%'},
                         	{xtype:'combo',fieldLabel: '赠送客户级别',disabled:true,name: 'CUST_LEVEL',labelStyle: 'text-align:right;',
                             	store: level,valueField : 'key',displayField : 'value',anchor:'95%'},
                         	{xtype:'combo',fieldLabel: '单位名称',disabled:true,name: 'ORG_ID',labelStyle: 'text-align:right;',
                             	store: orgStore,valueField : 'key',displayField : 'value',anchor:'95%'}
                         	]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'GOODS_NUMBER',xtype : 'textfield',fieldLabel : '库存数量',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'CREATE_NAME',xtype : 'textfield',fieldLabel : '创建人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'GOODS_PRICE',xtype : 'textfield',fieldLabel : '礼品单价',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'GOODS_SCORE',xtype : 'textfield',fieldLabel : '礼品积分单价',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
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
