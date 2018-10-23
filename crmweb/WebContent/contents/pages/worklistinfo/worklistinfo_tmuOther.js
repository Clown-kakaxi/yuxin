/**
 * @description TMU、投行及其他收费审批页面js
 * @author sunjing5
 * @since ?
 * @update 
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	//是否RE客户
	var luifReStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=IF_RE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//收取/返回
	var lugatherOrBackStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=GATHER_OR_BACK'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	//收费科目
	var lugatherTypeStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=GATHER_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	//是否实转，是否核批通过，是否台商
	var luifFlagStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	//进度
	var luprogressStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=SQPROGRESS'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//当月概率
	var luprobabilityStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=PROBABILITY'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	//分支行
	var lookupTypes=[{
		TYPE : 'AREA',//区域中心数据字典
		url : '/OcrmFMkDepositDrawingAction!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
		key : 'KEY',
		value : 'VALUE',
		root : 'data'},
		{
			TYPE : 'BRANCH',//自定义数据权限选项数据字典
			url : '/branchSearchAction!searchBranch.json',
			key : 'KEY',
			value : 'VALUE',
			jsonRoot : 'json.data'			
		}];
//	lucurrencyStore.load();
//	lucustTypeStore.load();
	lugatherOrBackStore.load();
	luifReStore.load();
	lugatherTypeStore.load();
	luifFlagStore.load();
	luprobabilityStore.load();
	luprogressStore.load();
//	happenReasonStore.load();
	

	//详情
	var infoForm1;
	/**
	 * 历史意见列表
	 */
	var viewContent_store1 = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({
        	url:basepath+'/wfComment.json?taskNumber='+instanceid.split('_')[1],
        	method:'GET'
        }),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
            {name:'username',maping:'USERNAME'},
            {name:'commentcontent',maping:'COMMENTCONTENT'},
            {name:'commenttime',maping:'COMMENTTIME'} 
		])
	});
	viewContent_store1.load();
	// 定义自动当前页行号
	var rownum1 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 35
	});
	
	// 定义列模型
	var viewContent_cm1 = new Ext.grid.ColumnModel([rownum1, 
     	{ header : '审批人',dataIndex : 'username', sortable : true, width : 100},
     	{ header : '审批意见',dataIndex : 'commentcontent', sortable : true, width : 120},
     	{ header : '审批日期', dataIndex : 'commenttime', sortable : true, width : 200 }
	]);
	// 表格实例
	var viewContent_grid1 = new Ext.grid.GridPanel({
		id:'viewgrid',
		frame : true,
		height:180,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : viewContent_store1, // 数据存储
		stripeRows : true, // 斑马线
		cm : viewContent_cm1, // 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var viewContent_form = new Ext.form.FieldSet({
		xtype:'fieldset',
		title:'历史意见列表',
		titleCollapse : true,
		collapsed :false,
		hidden:true,
		anchor : '98%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : 1,
				items:[viewContent_grid1]
			}]
		}]
	});
	//流程业务信息：form表单：
	   infoForm1 = new Ext.FormPanel( {
		   frame : true,
		   items : [ 
		   	{
			   layout : 'column',
			   items : [{
				   layout : 'form',columnWidth : .5,labelWidth:100,
				   items : [
				            {fieldLabel:'主键',name:'ID',hidden:true},
				            {fieldLabel:'客户编号',name:'CUST_ID',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'区域',name:'ORG_NAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'客户类别',name:'F_VALUE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
//				            	,xtype: 'combo',store : lucustTypeStore,resizable : true,valueField : 'key',displayField : 'value'
				            },
				            {fieldLabel:'是否RE客户',name:'IF_RE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store :luifReStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'收取/返回',name:'GATHER_OR_BACK',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store : lugatherOrBackStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'金额(RMB千元)',name:'AMOUNT',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'折后金额(RMB千元)',name:'DISCOUNT_OCCUR_AMT',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'最近修订机构',name:'LAST_UPDATE_ORG',hidden:true},
				            {fieldLabel:'最近修订机构',name:'LAST_UPDATE_ORGNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'最近修订人',name:'LAST_UPDATE_USER',hidden:true},
				            {fieldLabel:'最近修订人',name:'LAST_UPDATE_USERNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'最近修订时间',name:'LAST_UPDATE_TM',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
				           ]
			   },{
				   layout : 'form',columnWidth : .5,labelWidth:100,
				   items : [    {fieldLabel:'客户名称',name:'CUST_NAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				                {fieldLabel:'分支行',name:'ORG_NAME1',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'是否台商',name:'IF_TAIWAN',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : luifFlagStore,resizable : true,valueField : 'key',displayField : 'value'},
					            {fieldLabel:'收费科目',name:'GATHER_TYPE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
							        ,xtype: 'combo',store : lugatherTypeStore,resizable : true,valueField : 'key',displayField : 'value'},
					            {fieldLabel:'当月收取/返还概率(%)',name:'PROBABILITY',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : luprobabilityStore,resizable : true,valueField : 'key',displayField : 'value'},
				                {fieldLabel:'进度',name:'PROGRESS',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
						        	,xtype: 'combo',store : luprogressStore,resizable : true,valueField : 'key',displayField : 'value'},
					            {fieldLabel:'复核状态',name:'APPROVE_STATE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%',hidden:true},
					            {fieldLabel:'创建人',name:'CREATE_USER',hidden:true},
					            {fieldLabel:'创建人',name:'CREATE_USERNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'创建时间',name:'CREATE_TM',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'创建机构',name:'CREATE_ORG',hidden:true},
					            {fieldLabel:'创建机构',name:'CREATE_ORGNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},  
					            {fieldLabel:'备注',name:'REMARK',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					         ]
			   }]
		   }]
	   });
	
    var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/workListTmuOtherAction.json?applyNO='+instanceid.split('_')[1],
        		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, [{name:'ID',maping:'ID'},
            {name:'APPLY_NO',maping:'APPLY_NO'},
            {name:'REGION',maping:'REGION'},
            {name:'ORG_NAME',maping:'ORG_NAME'},
            {name:'ORG_ID',maping:'ORG_ID'},
            {name:'ORG_NAME1',maping:'ORG_NAME1'},
            {name:'F_VALUE',maping:'F_VALUE'},
            {name:'IF_RE',maping:'IF_RE'},
            {name:'CUST_ID',maping:'CUST_ID'},
            {name:'CUST_NAME',maping:'CUST_NAME'},
            {name:'GATHER_OR_BACK',maping:'GATHER_OR_BACK'},
          
            {name:'GATHER_TYPE',maping:'GATHER_TYPE'},
            {name:'AMOUNT',maping:'AMOUNT'},
            {name:'PROGRESS',maping:'PROGRESS'},           
            {name:'PROBABILITY',maping:'PROBABILITY'},           
            {name:'DISCOUNT_OCCUR_AMT',maping:'DISCOUNT_OCCUR_AMT'},
            {name:'IF_TAIWAN',maping:'IF_TAIWAN'},          
            {name:'APPROVE_STATE',maping:'APPROVE_STATE'},
            {name:'CREATE_USER',maping:'CREATE_USER'},
            {name:'CREATE_USERNAME',maping:'CREATE_USERNAME'},
            {name:'CREATE_ORG',maping:'CREATE_ORG'},
            {name:'CREATE_ORGNAME',maping:'CREATE_ORGNAME'},
            {name:'CREATE_TM',maping:'CREATE_TM'},
            {name:'LAST_UPDATE_USER',maping:'LAST_UPDATE_USER'},
            {name:'LAST_UPDATE_USERNAME',maping:'LAST_UPDATE_USERNAME'},
            {name:'LAST_UPDATE_ORG',maping:'LAST_UPDATE_ORG'},
            {name:'LAST_UPDATE_ORGNAME',maping:'LAST_UPDATE_ORGNAME'},
            {name:'LAST_UPDATE_TM',maping:'LAST_UPDATE_TM'},
            {name:'REMARK',maping:'REMARK'}
            ]
		)
	});

	
	//公共部分
    var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[infoForm1,viewContent_form]
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
		infoForm1.getForm().loadRecord(store.getAt(0));
	}
});
