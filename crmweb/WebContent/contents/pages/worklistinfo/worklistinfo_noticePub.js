
Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var flag = instanceid.split('_')[2];
		var nodeid = curNodeObj.nodeid;
		var jsonListRecord = Ext.data.Record.create([  
		                                             {name:'NOTICE_TITLE'},//公告标题
		                                             {name:'NOTICE_CONTENT'},//内容
		                                             {name:'NOTICE_LEVEL'},
		                                             {name:'IS_TOP'},
		                                             {name:'TOP_ACTIVE_DATE'},
		                                             {name:'RECEIVE_ORG_NAME'},
		                                             {name:'CREATOR_NAME'},//创建人
		                                             {name:'PUB_ORG_NAME'},//发布机构
		                                             {name:'PUBLISHER_NAME'},//发布人
		                                             {name:'ACTIVE_DATE'}//有效期
		                                             ]);  
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy(
	        		{
	        			url:basepath+'/noticequery.json'
	        		}),
	        reader: new Ext.data.JsonReader({
	        	root : 'json.data'
	        }, jsonListRecord
		)});
		
		var isTop = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=IF_FLAG'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		isTop.load();
		var noticeLev = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=NOTICE_LEV'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		noticeLev.load();
		
		var detailAnna = createAnnGrid(true,false,true,false);
		
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .33,labelWidth:100,
					items : [  
                              {name : 'NOTICE_TITLE',xtype : 'textfield',fieldLabel : '公告标题',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
                              {name : 'ACTIVE_DATE',xtype : 'textfield',fieldLabel : '有效期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
                              {name : 'NOTICE_LEVEL',xtype : 'combo',fieldLabel : '重要程度',labelStyle : 'text-align:right;',
                            		  store: noticeLev,valueField : 'key',displayField : 'value',disabled:true,anchor : '95%'}
                         	]
				},{
					layout : 'form',columnWidth : .33,labelWidth:100,
					items : [ 
					          {name : 'RECEIVE_ORG_NAME',xtype : 'textfield',fieldLabel : '接收机构',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'PUB_ORG_NAME',xtype : 'textfield',fieldLabel : '发布机构',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'PUBLISHER_NAME',xtype : 'textfield',fieldLabel : '发布人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					          
                         	]
				},{
					layout : 'form',columnWidth : .33,labelWidth:100,
					items : [ 
					         {name : 'CREATOR_NAME',xtype : 'textfield',fieldLabel : '创建人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					         {name : 'IS_TOP',xtype : 'combo',fieldLabel : '是否置顶',labelStyle : 'text-align:right;',
					        	 store: isTop,valueField : 'key',displayField : 'value',disabled:true,anchor : '95%'},
					         {name : 'TOP_ACTIVE_DATE',xtype : 'textfield',fieldLabel : '置顶时间',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
                         	]
				}]
			}]
		});
	    var prodTabs = new Ext.form.FieldSet({
	        collapsible:true,
	    	title : '公告内容'
	    });
	
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[infoForm,prodTabs,detailAnna]
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
		store.load({params:{id:id,flag:flag,operate:'temp'},
	        callback:function(){
	        	if(store.getCount()!=0){
	        		loadFormData();
	        	}
			}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
    		var top = infoForm.getForm().findField('IS_TOP').getValue();
    		if(top == '0'){
    			infoForm.getForm().findField('TOP_ACTIVE_DATE').setVisible(false);
    		}else {
    			infoForm.getForm().findField('TOP_ACTIVE_DATE').setVisible(true);
			}
    		//===========================公告内容 begin======================================
    		if(flag == '1'){
    			Ext.Ajax.request({
    				url:basepath+'/ocrmSysRicheditInfoTemp!indexPage.json',
    				method:'GET',
    				params:{
    					relId:id
    				},
    				success:function(response){
    					if(Ext.decode(response.responseText).json.data.length>0){
    						var context = Ext.decode(response.responseText).json.data[0].content;
    						if(context == ''){
    							prodTabs.body.update("<p>暂无公告内容</p>");
    						}else{
    							prodTabs.body.update(context);
    						}
    					}else{
    						prodTabs.body.update('<p>暂无公告内容</p>');
    					}
    				},failure:function(){
    				}
    			});
    		}else if(flag == '2'){
    			Ext.Ajax.request({
    				url:basepath+'/ocrmSysRicheditInfoTemp!indexPageTemp.json',
    				method:'GET',
    				params:{
    					relId:id
    				},
    				success:function(response){
    					if(Ext.decode(response.responseText).json.data.length>0){
    						var context = Ext.decode(response.responseText).json.data[0].content;
    						if(context == ''){
    							prodTabs.body.update("<p>暂无公告内容</p>");
    						}else{
    							prodTabs.body.update(context);
    						}
    					}else{
    						prodTabs.body.update('<p>暂无公告内容</p>');
    					}
    				},failure:function(){
    				}
    			});
    		}
    		
		}
		//============================内容end===========================================
		//==============================公告附件=====================================
		if(flag == '1'){
			uploadForm.relaId = id;
	        uploadForm.modinfo = 'notice';
	        var condi = {};
	        condi['relationInfo'] = id;
	        condi['relationMod'] = 'notice';
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
	                	detailAnna.store.loadData(anaExeArray.json.data);
	                    detailAnna.getView().refresh();
	            }
	        });
		}else if(flag == '2'){
			uploadForm.relaId = id;
	        uploadForm.modinfo = 'notice';
	        var condi = {};
	        condi['relationInfo'] = id;
	        condi['relationMod'] = 'notice';
	        Ext.Ajax.request({
	            url:basepath+'/queryannaTemp.json',
	            method : 'GET',
	            params : {
	                "condition":Ext.encode(condi),
	                approval:flag
	            },
	            failure : function(a,b,c){
	                Ext.MessageBox.alert('查询异常', '查询失败！');
	            },
	            success : function(response){
	                var anaExeArray = Ext.util.JSON.decode(response.responseText);
	                	detailAnna.store.loadData(anaExeArray.json.data);
	                    detailAnna.getView().refresh();
	            }
	        });
		}
		//==================================附件end================================
//	    uploadForm.relaId = id;
//        uploadForm.modinfo = 'notice';
//        var condi = {};
//        condi['relationInfo'] = id;
//        condi['relationMod'] = 'notice';
//        condi['approval'] = 'temp';
//        Ext.Ajax.request({
//            url:basepath+'/queryanna.json',
//            method : 'GET',
//            params : {
//                "condition":Ext.encode(condi)
//            },
//            failure : function(a,b,c){
//                Ext.MessageBox.alert('查询异常', '查询失败！');
//            },
//            success : function(response){
//                var anaExeArray = Ext.util.JSON.decode(response.responseText);
//                	detailAnna.store.loadData(anaExeArray.json.data);
//                    detailAnna.getView().refresh();
//            }
//        });
				
	});
