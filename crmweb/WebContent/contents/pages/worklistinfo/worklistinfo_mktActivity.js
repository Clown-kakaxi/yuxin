/***
 * 营销活动审批流程展示页面js
 * hujun 
 * 2014-06-25
 */	
 Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		debugger;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
			

		var mktWay = new Ext.data.Store( {//营销活动方式
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=MKT_WAY'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		mktWay.load();
		var mktState = new Ext.data.Store( {//营销活动状态
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=MACTI_STATUS'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		mktState.load();
		var mktType = new Ext.data.Store( {//营销活动类型
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
				        		{name:'MKT_ACTI_AIM'},
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
					fieldLabel : '营销活动状态',
					disabled : true,
					name : 'MKT_ACTI_STAT',
					labelStyle : 'text-align:right;',
					store : mktState,
					valueField : 'key',
					displayField : 'value',
					anchor : '95%'
				},  {
					xtype : 'combo',
					fieldLabel : '营销活动方式',
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
					fieldLabel : '营销活动类型',
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
					fieldLabel:'涉及客户群描述',
					labelStyle : 'text-align:right;',
					disabled : true,
					anchor : '95%'
				},{
					name:'ACTI_OPER_DESC',
					xtype:'textarea',
					fieldLabel:'涉及执行人描述',
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
					name:'MKT_ACTI_AIM',
					xtype:'textarea',
					fieldLabel:'营销活动目的',
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
	    /**********************关联产品grid******************************/
	    var prodRowNumber = new Ext.grid.RowNumberer({
	        header:'NO.',
	        width:28
	    });
	    var prodCm = new Ext.grid.ColumnModel([
	        prodRowNumber,
	        {dataIndex:'AIM_PROD_ID',header:'目标产品ID',width : 120,sortable : true, hidden:true},
	        {dataIndex:'MKT_ACTI_ID',header:'营销活动ID',width : 120,sortable : true,hidden:true},
	        {dataIndex:'PRODUCT_ID',header:'产品编号',width : 120,sortable : true},
	        {dataIndex:'PRODUCT_NAME',header:'产品名称',width : 120,sortable : true},
	        {dataIndex:'CREATE_USER_NAME',header:'创建人',width : 120,sortable : true},
	        {dataIndex:'CREATE_DATE',header:'创建日期',format:'Y-m-d',width : 120,sortable : true}
	    ]);
	    // create the data record
	    var prodRecord = new Ext.data.Record.create([
	        {name:'AIM_PROD_ID',mapping:'AIM_PROD_ID'},
	        {name:'MKT_ACTI_ID',mapping:'MKT_ACTI_ID'},
	        {name:'PRODUCT_ID',mapping:'PRODUCT_ID'},
	        {name:'PRODUCT_NAME',mapping:'PRODUCT_NAME'},
	        {name:'CREATE_USER_NAME',mapping:'CREATE_USER_NAME'},
	        {name:'CREATE_DATE',mapping:'CREATE_DATE'}
	    ]);
	    // create the data store
	    var prodStore = new Ext.data.Store({
	        restful:true,
	        proxy: new Ext.data.HttpProxy({
	        	url : basepath + '/mktactivityrelateinfoaction.json?querysign=prod'
	        }),
	        reader: new Ext.data.JsonReader({
	            root:'json.data',
	            totalProperty:'json.count'
	        },prodRecord)
	    });
	    var prodGrid=new Ext.grid.GridPanel({
	    	frame: true,
	        region: 'center',
	        height:200,
	        autoScroll: true,
	        stripeRows: true,
	        store: prodStore,
	        cm : prodCm,
	        viewConfig:{},
	        loadMask: {
	            msg: '正在加载表格数据,请稍等...'
	        }
	    });
	    /**********************关联客户grid******************************/
	    var custRowNumber = new Ext.grid.RowNumberer({
	        header:'NO.',
	        width:28
	    });
	    var custCm = new Ext.grid.ColumnModel([
	        custRowNumber,
	        {dataIndex:'AIM_CUST_ID',header:'目标客户ID',width : 120,sortable : true, hidden:true},
	        {dataIndex:'CUST_ID',header:'客户编号',width : 120,sortable : true,hidden:true},
	        {dataIndex:'CUST_NAME',header:'客户名称',width : 120,sortable : true},
	        {dataIndex:'MGR_NAME',header:'主办客户经理',width : 120,sortable : true},
	        {dataIndex:'INSTITUTION_NAME',header:'主办机构',width : 120,sortable : true},
	        {dataIndex:'AIM_CUST_SOURCE',header:'目标客户来源',renderer:function(value){
				
				if(value=="01"){
					return "自定义筛选";
				}else if(value=="02"){
					return "客户群导入";
				}
				}},
			{dataIndex:'CREATE_USER_NAME',header:'创建人',width : 120,sortable : true},
			{dataIndex:'CREATE_DATE',header:'创建日期',width : 120,sortable : true}
	    ]);
	    // create the data record
	    var custRecord = new Ext.data.Record.create([
	        {name:'AIM_CUST_ID',mapping:'AIM_CUST_ID'},
	        {name:'CUST_ID',mapping:'CUST_ID'},
	        {name:'CUST_NAME',mapping:'CUST_NAME'},
	        {name:'MGR_NAME',mapping:'MGR_NAME'},
	        {name:'INSTITUTION_NAME',mapping:'INSTITUTION_NAME'},
	        {name:'AIM_CUST_SOURCE',mapping:'AIM_CUST_SOURCE'},
	        {name:'PROGRESS_STEP',mapping:'PROGRESS_STEP'},
	        {name:'CREATE_USER_NAME',mapping:'CREATE_USER_NAME'},
	        {name:'CREATE_DATE',mapping:'CREATE_DATE'}
	    ]);
	    // create the data store
	    var custStore = new Ext.data.Store({
	        restful:true,
	        proxy: new Ext.data.HttpProxy({
	        	url : basepath+'/mktactivityrelateinfoaction.json?querysign=customer'
	        }),
	        reader: new Ext.data.JsonReader({
	            root:'json.data',
	            totalProperty:'json.count'
	        },custRecord)
	    });
	    var custGrid=new Ext.grid.GridPanel({
	    	frame: true,
	        region: 'center',
	        height:200,
	        autoScroll: true,
	        stripeRows: true,
	        store: custStore,
	        cm : custCm,
	        viewConfig:{},
	        loadMask: {
	            msg: '正在加载表格数据,请稍等...'
	        }
	    });
	    /**********************关联渠道grid******************************/
	    var channelRowNumber = new Ext.grid.RowNumberer({
	        header:'NO.',
	        width:28
	    });
	    var channelCm = new Ext.grid.ColumnModel([
	        channelRowNumber,
	        {dataIndex:'ACTI_CHANNEL_ID',header:'目标渠道ID',width : 120,sortable : true, hidden:true},
	        {dataIndex:'CHANNEL_ID',header:'渠道编号',width : 120,sortable : true,hidden:true},
	        {dataIndex:'CHANNEL_NAMES',header:'渠道名称',width : 120,sortable : true},
	        {dataIndex:'TEMPLETNAME',header:'模板名称',width : 120,sortable : true},
	        {dataIndex:'MKT_ACTI_ID',header:'营销活动编号',width : 120,hidden:true,sortable : true},
	        {dataIndex:'CREATE_USER',header:'创建人编号',width : 120,hidden:true,sortable : true},
	        {dataIndex:'CREATE_USER_NAME',header:'创建人',width : 120,sortable : true},
	        {dataIndex:'CREATE_DATE',header:'创建日期',format:'Y-m-d',width : 120,sortable : true}
	    ]);
	    // create the data record
	    var  channelRecord = new Ext.data.Record.create([
	        {name:'ACTI_CHANNEL_ID',mapping:'ACTI_CHANNEL_ID'},
	        {name:'CHANNEL_ID',mapping:'CHANNEL_ID'},
	        {name:'CHANNEL_NAMES',mapping:'CHANNEL_NAMES'},
	        {name:'TEMPLETNAME',mapping:'TEMPLETNAME'},
	        {name:'MKT_ACTI_ID',mapping:'MKT_ACTI_ID'},
	        {name:'CREATE_USER',mapping:'CREATE_USER'},
	        {name:'CREATE_USER_NAME',mapping:'CREATE_USER_NAME'},
	        {name:'CREATE_DATE',mapping:'CREATE_DATE'}
	    ]);
	    // create the data store
//	    var  channelStore = new Ext.data.Store({
//	        restful:true,
//	        proxy: new Ext.data.HttpProxy({
//	        	url : basepath + '/mktactivityrelateinfoaction.json?querysign=chanel'
//	        }),
//	        reader: new Ext.data.JsonReader({
//	            root:'json.data',
//	            totalProperty:'json.count'
//	        }, channelRecord)
//	    });
//	    var  channelGrid=new Ext.grid.GridPanel({
//	    	frame: true,
//	        region: 'center',
//	        autoScroll: true,
//	        stripeRows: true,
//	        height:200,
//	        store:  channelStore,
//	        cm :  channelCm,
//	        viewConfig:{},
//	        loadMask: {
//	            msg: '正在加载表格数据,请稍等...'
//	        }
//	    });
	    var tabpanel=new Ext.TabPanel({
	    	 width:'100%',
	         heignt:'100%',
	         activeTab: 0,
	         frame:true,
	         defaults:{autoHeight: true},
	         resizeTabs:true, // turn on tab resizing
	         preferredTabWidth:150,	
	         items:[
		        	{ 
						title: '<span style=\'text-align:center;\'>基础信息</span>',
						items:[infoForm]
					},{
						title: '<span style=\'text-align:center;\'>关联产品信息</span>',
						items:[prodGrid]
					},{
						title: '<span style=\'text-align:center;\'>关联客户信息</span>',
						items:[custGrid],
						listeners:{
							'activate':function(){
								custStore.load({
									params:{
										mktActiId:id
									}
								});
							}
						}
					}
//					,{
//						title: '<span style=\'text-align:center;\'>关联渠道信息</span>',
//						items:[channelGrid]
//					}
					,{
						title: '<span style=\'text-align:center;\'>附件信息信息</span>',
						items:[createAnna]
					}
        ]	
	    	
	    });
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[tabpanel]
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
		prodStore.load({
			params:{
				mktActiId:id
			}
		});
		custStore.load({
			params:{
				mktActiId:id
			}
		});
//		channelStore.load({
//			params:{
//				mktActiId:id
//			}
//		});
		
		
		 var condi = {};
        condi['relationInfo'] = id;
        condi['relationMod'] = 'mktActive';
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
                    try{
                    	createAnna.getView().refresh();
                    	createAnna.tbar.setDisplayed(false);
                    }catch(e){
                    }
               
            }
        });
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
		}			
	});
