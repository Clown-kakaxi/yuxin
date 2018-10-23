	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var flag = instanceid.split('_')[2];//标示是删除还是新增
		var nodeid = curNodeObj.nodeid;
		var jsonListRecord = Ext.data.Record.create([  
		                                             {name:'CUST_MANAGER_NAME'},
		                                             {name:'CUST_MANAGER_ORG'},
		                                             {name:'JOIN_DATE'},
		                                             {name:'TEAM_NAME'},
		                                             {name:'BELONG_ORG'},
		                                             {name:'TEAM_TYPE'}
		                                             ]);  
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy(
	        		{
	        			url:basepath+'/customerMktTeamMembers.json'
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
		 //复选框
//		var sm = new Ext.grid.CheckboxSelectionModel();

		// 定义自动当前页行号
//		var rownum = new Ext.grid.RowNumberer({
//			header : 'No.',
//			width : 28
//		});
	   var cm = new Ext.grid.ColumnModel([
          {dataIndex:'CUST_MANAGER_NAME',header:'客户经理名称',width:200,sortable : true},
          {dataIndex:'CUST_MANAGER_ORG',header:'客户经理归属机构',width:200,sortable : true},
          {dataIndex:'JOIN_DATE',header:'加入团队时间',width:200,sortable : true}
      ]);
  	  var grid = new Ext.grid.GridPanel({
  		  height:100,
  		  autoScroll: true,
          stripeRows: true,
          store: store,
          cm : cm,
//          sm : sm, // 复选框
          loadMask: {
              msg: '正在加载表格数据,请稍等...'
          }
      });
  	 var fieldSet = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '团队成员明细',
	    items:[grid]
     }); 
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ 
							   {name : 'TEAM_NAME',xtype : 'textfield',fieldLabel : '团队名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							   {name : 'BELONG_ORG',xtype : 'textfield',fieldLabel : '归属机构',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
                         	]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ 
					          {name : 'TEAM_TYPE',xtype : 'combo',fieldLabel : '团队类型',labelStyle : 'text-align:right;',store: teamType,valueField : 'key',displayField : 'value',disabled:true,anchor : '95%'}
                         	]
				}]
			}]
		});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[infoForm,fieldSet]
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
		}
		
				
	});
