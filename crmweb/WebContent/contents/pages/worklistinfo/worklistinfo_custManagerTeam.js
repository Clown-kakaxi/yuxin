	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[2];
		var nodeid = curNodeObj.nodeid;
		var jsonListRecord = Ext.data.Record.create([  
		                                             {name:'MKT_TEAM_NAME'},
		                                             {name:'ORG_NAME'},
		                                             {name:'TEAM_TYPE'},
		                                             {name:'TEAM_LEADER_NAME'}
		                                             ]);  
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy(
	        		{
	        			url:basepath+'/customerMktTeamInformationAdd.json'
	        		}),
	        reader: new Ext.data.JsonReader({
	        	root : 'json.data'
	        }, jsonListRecord
		)});
		
		var teamType = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=CUSTMANAGER_TEAM_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		teamType.load();
		
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ 
					          {name : 'MKT_TEAM_NAME',xtype : 'textfield',fieldLabel : '团队名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          {name : 'ORG_NAME',xtype : 'textfield',fieldLabel : '所属机构',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
                         	]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ 
					          {name : 'TEAM_TYPE',xtype : 'combo',fieldLabel : '团队类型',labelStyle : 'text-align:right;',
					        	  store: teamType,valueField : 'key',displayField : 'value',disabled:true,anchor : '95%'},
					          {name : 'TEAM_LEADER_NAME',xtype : 'textfield',fieldLabel : '负责人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
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
		store.load({params:{id:id,operate:'temp'},
	        callback:function(){
	        	if(store.getCount()!=0){
	        		loadFormData();
	        	}
			}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
		}
		
				
	});
