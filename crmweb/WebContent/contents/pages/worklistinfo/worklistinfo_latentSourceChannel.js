/***
 * 营销活动审批流程展示页面js
 * mams
 * 2014-03-17
 */	
 Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
			

		var mktWay = new Ext.data.Store( {//费用来源
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=MKT_COST_SOURCES'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		mktWay.load();
		var mktState = new Ext.data.Store( {//目标客户群
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=MKT_TARGET_CUSTOMER'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		mktState.load();
		var mktType = new Ext.data.Store( {//营销活动目的
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=ACTI_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		mktType.load();
	
		
		
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/market-activity.json'
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{name:'MKT_ACTI_ID'},
				            {name:'MKT_ACTI_NAME'},
				        		{name:'PSTART_DATE'},
				        		{name:'PEND_DATE'},
				        		{name:'MKT_ACTI_COST'},
								{name:'MKT_ACTI_TYPE'},
				        		{name:'MKT_ACTI_MODE'},
				        		{name:'MKT_ACTI_STAT'},
				        		{name:'MKT_ACTI_ADDR'},
				        		{name:'MKT_ACTI_CONT'},
				        		{name:'ACTI_CUST_DESC'},
				        		{name:'ACTI_OPER_DESC'},
				        		{name:'ACTI_PROD_DESC'},
				        		{name:'ACTI_REMARK'},
				        		{name:'CREATE_DATE'},
				        		{name:'USERNAME'},
				        		{name:'CREATE_USER'}]
					)
	});
	    var createAnna = createAnnGrid(true,false,true,'');
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [ {
				layout : 'form',
				columnWidth : .5,
				labelWidth : 100,
				items : [ {
					name : 'MKT_ACTI_ID',
					xtype : 'textfield',
					fieldLabel : '营销活动ID',
					labelStyle : 'text-align:right;',
					disabled : true,
					hidden:true,
					anchor : '95%'
				},{
					name : 'MKT_ACTI_NAME',
					xtype : 'textfield',
					fieldLabel : '营销活动名称',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				}, {
					xtype : 'combo',
					fieldLabel : '目标客群',
					disabled : true,
					name : 'MKT_ACTI_STAT',
					labelStyle : 'text-align:right;',
					store : mktState,
					valueField : 'key',
					displayField : 'value',
					anchor : '95%'
				},  {
					xtype : 'combo',
					fieldLabel : '费用来源',
					disabled : true,
					name : 'MKT_ACTI_MODE',
					labelStyle : 'text-align:right;',
					store : mktWay,
					valueField : 'key',
					displayField : 'value',
					anchor : '95%'
				}, {
					name : 'PSTART_DATE',
					xtype : 'datefield',
					fieldLabel : '计划开始日期',
					labelStyle : 'text-align:right;',
					disabled : true,
					format:'Y-m-d',
					anchor : '95%'
				}, {
					xtype : 'datefield',
					name : 'CREATE_DATE',
					fieldLabel : '创建日期',
					format : 'Y-m-d',
					disabled : true,
					anchor : '95%'
				} ]
			}, {
				layout : 'form',
				columnWidth : .5,
				labelWidth : 100,
				items : [ {
					xtype : 'combo',
					fieldLabel : '营销活动目的',
					disabled : true,
					name : 'MKT_ACTI_TYPE',
					labelStyle : 'text-align:right;',
					store : mktType,
					valueField : 'key',
					displayField : 'value',
					anchor : '95%'
				}, {
					name : 'MKT_ACTI_COST',
					xtype : 'textfield',
					fieldLabel : '预算费用',
					labelStyle : 'text-align:right;',
					renderer:money('0,000.00'),
					disabled : true,
					anchor : '95%'
				}, {
					xtype : 'datefield',
					name : 'PEND_DATE',
					fieldLabel : '计划结束日期',
					format : 'Y-m-d',
					disabled : true,
					anchor : '95%'
				} , {
					name : 'USERNAME',
					xtype : 'textfield',
					fieldLabel : '创建人',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				}]
			},{
				layout : 'form',
				columnWidth : 1,
				labelWidth : 100,
				items:[{
					name:'MKT_ACTI_ADDR',
					xtype:'textarea',
					fieldLabel:'活动地点',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				},{
					name:'MKT_ACTI_CONT',
					xtype:'textarea',
					fieldLabel:'营销活动内容',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				},{
					name:'ACTI_CUST_DESC',
					xtype:'textarea',
					fieldLabel:'宣传品类型',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				},{
					name:'ACTI_OPER_DESC',
					xtype:'textarea',
					fieldLabel:'预计参与人数',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				},{
					name:'ACTI_PROD_DESC',
					xtype:'textarea',
					fieldLabel:'涉及产品描述',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				},{
					name:'ACTI_REMARK',
					xtype:'textarea',
					fieldLabel:'备注',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				}]
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
