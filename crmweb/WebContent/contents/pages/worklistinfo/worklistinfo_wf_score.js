	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		
		var state = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=ADD_STATE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		state.load();
		
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/ocrmFSeScore.json?addId='+id
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{name:'CUST_ID'},
				        		{name:'SCORE_ADD'},
				        		{name:'SCORE_TOTAL'},
				        		{name:'CUST_NAME'},
				        		{name:'ADD_DATE'},
				        		{name:'SCORE_USED'},
				        		{name:'ADD_REASON'}]
					)
	});
	    var createAnna = createAnnGrid(true,false,true,'');
	    
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'SCORE_ADD',xtype : 'textfield',fieldLabel : '拟加积分',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'SCORE_TOTAL',xtype : 'textfield',fieldLabel : '可用积分',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'ADD_DATE',xtype : 'textfield',fieldLabel : '申请日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'SCORE_USED',xtype : 'textfield',fieldLabel : '已用积分',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
				}]
			},{
				layout : 'form',labelWidth:100,
				items:[{xtype : 'textarea',name : 'ADD_REASON',fieldLabel : '加分理由',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
			}]
		});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[infoForm,createAnna]
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
		
		uploadForm.relaId = id;
		uploadForm.modinfo = 'scoreAdd';
		var condi = {};
        condi['relationInfo'] = id;
        condi['relationMod'] = 'scoreAdd';
        Ext.Ajax.request({
            url:basepath+'/queryanna.json',
            method : 'GET',
            params : {
                "condition":Ext.encode(condi)
            },
            failure : function(a,b,c){
                Ext.MessageBox.alert('查询异常', '查询失败！');
            },
            success : function(response){
                var anaExeArray = Ext.util.JSON.decode(response.responseText);
                	createAnna.store.loadData(anaExeArray.json.data);
                	createAnna.getView().refresh();
            }
        });		
	});
