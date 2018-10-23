/**
 * @description 贷款新拨及还款审批页面js
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
//	//客户类别
//	var lucustTypeStore = new Ext.data.Store({
//		restful:true,   
//		autoLoad :true,
//		proxy : new Ext.data.HttpProxy({
//			url :basepath+'/lookup.json?name=CUST_TYPE'
//		}),
//		reader : new Ext.data.JsonReader({
//			root : 'JSON'
//		}, [ 'key', 'value' ])
//	});
	//拨款/还款
	var lupayOrRepayStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=PAY_OR_REPAY'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//行业投向
	var lucusOwnbusiStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=CUS_OWNBUSI',
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
	//贷款类型
	var luloanTypeStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=LOAN_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//币种
	var lucurrencyStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=CURRENCY'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//还款原因
	var lurepayReasonStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=REPAY_REASON'
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
			url :basepath+'/lookup.json?name=PROGRESS'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//当月动拨概率
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
	//固定利率&浮动利率
	var lufloatStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=FLOAT_OR_FIXED_IR'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//银监口径企业大中小
	var lugroupStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000019'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//分支行
	var lookupTypes=[{
		TYPE : 'AREA',//区域中心数据字典
		url : '/ocrmFMkNewRepayAction!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
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
	lucurrencyStore.load();
	lucusOwnbusiStore.load();
//	lucustTypeStore.load();
	lufloatStore.load();
	lugroupStore.load();
	lugroupStore.load();
	luifFlagStore.load();
	luifReStore.load();
	luloanTypeStore.load();
	lupayOrRepayStore.load();
	luprobabilityStore.load();
	luprobabilityStore.load();
	luprogressStore.load();
	lurepayReasonStore.load();
	

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
//				            {fieldLabel:'客户类别',name:'CUST_TYPE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
//				            	,xtype: 'combo',store : lucustTypeStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'客户类别',name:'F_VALUE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'是否RE客户',name:'IF_RE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store :luifReStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'拨款/还款',name:'PAY_OR_REPAY',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store : lupayOrRepayStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'还款原因',name:'REPAY_REASON',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : lurepayReasonStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'是否核批通过',name:'IF_PASS',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store : luifFlagStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'贷款类型',name:'LOAN_TYPE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store : luloanTypeStore,resizable : true,valueField : 'key',displayField : 'value'},
					        {fieldLabel:'固定&浮动利率',name:'FLOAT_OR_FIXED_IR',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : lufloatStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'币种',name:'CURRENCY',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
				            	,xtype: 'combo',store : lucurrencyStore,resizable : true,valueField : 'key',displayField : 'value'},
				            {fieldLabel:'当月动拨/还款概率(%)',name:'PROBABILITY',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : luprobabilityStore,resizable : true,valueField : 'key',displayField : 'value'},
					        {fieldLabel:'创建人',name:'CREATE_USER',hidden:true},
						    {fieldLabel:'创建人',name:'CREATE_USERNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'创建时间',name:'CREATE_TM',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				            {fieldLabel:'创建机构',name:'CREATE_ORG',hidden:true},
				            {fieldLabel:'创建机构',name:'CREATE_ORGNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},          
				            {fieldLabel:'备注',name:'REMARK',xtype : 'textarea',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				        ]
			   },{
				   layout : 'form',columnWidth : .5,labelWidth:100,
				   items : [    
				                {fieldLabel:'客户名称',name:'CUST_NAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
				                {fieldLabel:'分支行',name:'ORG_NAME1',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'是否台商',name:'IF_TAIWANBUSINESS',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : luifFlagStore,resizable : true,valueField : 'key',displayField : 'value'},
				            	{fieldLabel:'进度',name:'PROGRESS',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					        	,xtype: 'combo',store : luprogressStore,resizable : true,valueField : 'key',displayField : 'value'},
					            {fieldLabel:'拨款投向',name:'INDUST_TYPE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : lucusOwnbusiStore,resizable : true,valueField : 'key',displayField : 'value'},
					            {fieldLabel:'预计拨款日期/还款日期(YYYY/MM/DD)',name:'ESTIMATE_DATE',xtype : 'datefield',format : 'Y-m-d',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					        	{fieldLabel:'是否实转',name:'IF_REAL',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'
					            	,xtype: 'combo',store : luifFlagStore,resizable : true,valueField : 'key',displayField : 'value'},
							    {fieldLabel:'利率(%)',name:'INTEREST_RATE',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						        {fieldLabel:'金额(RMB千元)',name:'AMOUNT',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'折后动拨金额(RMB千元)',name:'DISCOUNT_OCCUR_AMT',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'审批状态',name:'APPROVE_STATE',hidden:true},
					            {fieldLabel:'最近修订机构',name:'LAST_UPDATE_ORG',hidden:true},
					            {fieldLabel:'最近修订机构',name:'LAST_UPDATE_ORGNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'最近修订人',name:'LAST_UPDATE_USERNAME',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {fieldLabel:'最近修订时间',name:'LAST_UPDATE_TM',xtype : 'textfield',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'} ]
			   }]
		   }]
	   });
	
    var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/workListNewRepayAction.json?applyNO='+instanceid.split('_')[1],
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
            {name:'PAY_OR_REPAY',maping:'PAY_OR_REPAY'},
            {name:'INDUST_TYPE',maping:'INDUST_TYPE'},
            {name:'IF_PASS',maping:'IF_PASS'},
            {name:'LOAN_TYPE',maping:'LOAN_TYPE'},
            {name:'CURRENCY',maping:'CURRENCY'},
            {name:'AMOUNT',maping:'AMOUNT'},
            {name:'ESTIMATE_DATE',maping:'ESTIMATE_DATE'},
            {name:'REPAY_REASON',maping:'REPAY_REASON'},
            {name:'PROGRESS',maping:'PROGRESS'},
            {name:'IF_REAL',maping:'IF_REAL'},
            {name:'PROBABILITY',maping:'PROBABILITY'},
            {name:'FLOAT_OR_FIXED_IR',maping:'FLOAT_OR_FIXED_IR'},
            {name:'DISCOUNT_OCCUR_AMT',maping:'DISCOUNT_OCCUR_AMT'},
            {name:'IF_TAIWANBUSINESS',maping:'IF_TAIWANBUSINESS'},
            {name:'CUS_NATURE',maping:'CUS_NATURE'},
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
            {name:'INTEREST_RATE',maping:'INTEREST_RATE'},
            {name:'REMARK',maping:'REMARK'}]
		)
	});
/*	var cm=new Ext.grid.ColumnModel([{header:'主键',dataIndex:'ID',sortable:true,width:120,hidden:true},
	                                 {header:'区域1',dataIndex:'REGION',sortable:true,width:120,hidden:true},
	                                 {header:'区域',dataIndex:'ORG_NAME',sortable:true,width:120,hidden:false},
	                                 {header:'分支行1',dataIndex:'ORG_ID',sortable:true,width:120,hidden:true},
	                                 {header:'分支行',dataIndex:'ORG_NAME1',sortable:true,width:120,hidden:false},
	                                 {header:'客户类别',dataIndex:'CUST_TYPE',sortable:true,width:120,hidden:false,renderer:function(value){
                       					 for(var i=0;i< lucustTypeStore.data.length;i++){
	                     						if(lucustTypeStore.data.items[i].data.key==value){
	                     						   return lucustTypeStore.data.items[i].data.value
	                     						}
	                     					}
	                     					}},
	                                 {header:'是否RE客户',dataIndex:'IF_RE',sortable:true,width:120,hidden:false},
	                                 {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120,hidden:false},
	                                 {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120,hidden:false},
	                                 {header:'拨款/还款',dataIndex:'PAY_OR_REPAY',sortable:true,width:120,hidden:false},
	                                 {header:'行业投向',dataIndex:'INDUST_TYPE',sortable:true,width:120,hidden:false},
	                                 {header:'是否核批通过',dataIndex:'IF_PASS',sortable:true,width:120,hidden:false},
	                                 {header:'贷款类型',dataIndex:'LOAN_TYPE',sortable:true,width:120,hidden:false},
	                                 {header:'币种',dataIndex:'CURRENCY',sortable:true,width:120,hidden:false},
	                                 {header:'金额（RMB百万）（拨款+/还款-）',dataIndex:'AMOUNT',sortable:true,width:120,hidden:false},
	                                 {header:'预计拨款日期/还款日期（YYYY/MM/DD）',dataIndex:'ESTIMATE_DATE',sortable:true,width:120,hidden:false},
	                                 {header:'还款原因',dataIndex:'REPAY_REASON',sortable:true,width:120,hidden:false},
	                                 {header:'进度',dataIndex:'PROGRESS',sortable:true,width:120,hidden:false},
	                                 {header:'是否实转',dataIndex:'IF_REAL',sortable:true,width:120,hidden:false},
	                                 {header:'当月动拨概率',dataIndex:'PROBABILITY',sortable:true,width:120,hidden:false},
	                                 {header:'固定利率&浮动利率',dataIndex:'FLOAT_OR_FIXED_IR',sortable:true,width:120,hidden:false},
	                                 {header:'折后动拨金额',dataIndex:'DISCOUNT_OCCUR_AMT',sortable:true,width:120,hidden:false},
	                                 {header:'是否台商',dataIndex:'IF_TAIWANBUSINESS',sortable:true,width:120,hidden:false},
	                                 {header:'银监口径企业大中小',dataIndex:'CUS_NATURE',sortable:true,width:120,hidden:false},
	                                 {header:'审批状态',dataIndex:'APPROVE_STATE',sortable:true,width:120,hidden:false},
	                                 {header:'创建人',dataIndex:'CREATE_USER',sortable:true,width:120,hidden:false},
	                                 {header:'创建机构',dataIndex:'CREATE_ORG',sortable:true,width:120,hidden:false},
	                                 {header:'创建时间',dataIndex:'CREATE_TM',sortable:true,width:120,hidden:false},
	                                 {header:'最近修订人',dataIndex:'LAST_UPDATE_USER',sortable:true,width:120,hidden:false},
	                                 {header:'最近修订机构',dataIndex:'LAST_UPDATE_ORG',sortable:true,width:120,hidden:false},
	                                 {header:'最近修订时间',dataIndex:'LAST_UPDATE_TM',sortable:true,width:120,hidden:false},
	                                 {header:'利率',dataIndex:'INTEREST_RATE',sortable:true,width:120,hidden:false}]);
	var infoGrid=new Ext.grid.GridPanel ({
		autoScroll:true,
		height:200,
		store:store,
		frame:true,
		cm:cm,
		loadMask:{
			msg : '正在加载表格数据,请稍等...'
		}
		
	});*/
	
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
		var v = store.getAt(0).data.PAY_OR_REPAY;//拨款或还款
		
		   if(v == '001' ){//拨款
			   infoForm1.getForm().findField('INDUST_TYPE').setVisible(true);
				 infoForm1.getForm().findField('INDUST_TYPE').allowBlank=false;
				 infoForm1.getForm().findField('REPAY_REASON').setVisible(false); 
				 infoForm1.getForm().findField('REPAY_REASON').setValue(""); 
				 infoForm1.getForm().findField('REPAY_REASON').allowBlank=true;
			}
			else{//还款
				 infoForm1.getForm().findField('INDUST_TYPE').setVisible(false);
				 infoForm1.getForm().findField('INDUST_TYPE').setValue(""); 
				 infoForm1.getForm().findField('INDUST_TYPE').allowBlank=true; 
	    			//还款原因
				 infoForm1.getForm().findField('REPAY_REASON').setVisible(true); 
				 infoForm1.getForm().findField('REPAY_REASON').allowBlank=false;
			}  
		infoForm1.getForm().loadRecord(store.getAt(0));
	}
});
