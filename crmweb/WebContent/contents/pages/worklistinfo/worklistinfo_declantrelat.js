Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var main_id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	//数据字典
var lookup1Store = new Ext.data.Store({//关系人属性
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000306'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var lookup2Store = new Ext.data.Store({//关联方属性
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=RELATE_PARTY_ATTR'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
}); 
var lookup3Store = new Ext.data.Store({//证件类型
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var lookup4Store = new Ext.data.Store({//主申报人与银行的关联关系
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=REL_WITH_BANK'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var lookup5Store = new Ext.data.Store({//关系人与银行的关联关系
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=REL_WITH_BANK1'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var lookup6Store = new Ext.data.Store({//关联人与主申报人关系
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=RELATE_DECLARANT_REL1'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var lookup7Store = new Ext.data.Store({//是否为商业银行
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000346'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
lookup1Store.load();
lookup2Store.load();
lookup3Store.load();
lookup4Store.load();
lookup5Store.load();
lookup6Store.load();
lookup7Store.load();

	var relaterownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
			header : 'No.',
			width : 28
		});
	var relatecolumnmodel = new Ext.grid.ColumnModel([
	    relaterownum,
//	    {header:'关系人编号',dataIndex:'RELATE_ID',width:100,sortable : true,hidden:true},
	    {header:'关系人名称',dataIndex:'PRIVY_NAME',width:100,sortable : true},
	    {header:'关系人属性',dataIndex:'PRIVY_ATTRIBUTE',width:100,renderer:function(value){
	    	       for(var i=0;i< lookup1Store.data.length;i++){
						if(lookup1Store.data.items[i].data.key==value){
						   return lookup1Store.data.items[i].data.value
						}
					}
					},sortable : true},
	    {header:'证件类型',dataIndex:'IDENT_TYPE',width:150,renderer:function(value){
					 for(var i=0;i< lookup3Store.data.length;i++){
						if(lookup3Store.data.items[i].data.key==value){
						   return lookup3Store.data.items[i].data.value
						}
					}
					},sortable : true},
		{header:'证件号码',dataIndex:'IDENT_NO',width:100,sortable : true},
	    {header:'电话号码',dataIndex:'TEL',width:100,sortable : true},
	    {header:'邮箱',dataIndex:'EMAIL',width:100,sortable : true},
	    {header:'联系地址',dataIndex:'CONTACT_ADDR',width:100,sortable : true},
	    {header:'与申报人关系',dataIndex:'RELATE_DECLARANT_REL',width:100,renderer:function(value){
					for(var i=0;i< lookup6Store.data.length;i++){
						if(lookup6Store.data.items[i].data.key==value){
						   return lookup6Store.data.items[i].data.value
						}
					}
					},sortable : true},
	    {header:'与银行关系',dataIndex:'DECLARANT_BANK_REL',width:100,renderer:function(value){
	    			for(var i=0;i< lookup5Store.data.length;i++){
						if(lookup5Store.data.items[i].data.key==value){
						   return lookup5Store.data.items[i].data.value
						}
					}
					},sortable : true},
	    {header:'是否为商业银行',dataIndex:'lookup7Store',width:100,hidden:true,renderer:function(value){
					for(var i=0;i< lookup7Store.data.length;i++){
						if(lookup7Store.data.items[i].data.key==value){
						   return lookup7Store.data.items[i].data.value
						}
					}
					},sortable : true},
	    {header:'关联人编号',dataIndex:'MAIN_ID',width:100,hidden:true,sortable : true},
	    {header:'生效日',dataIndex:'EFFECT_DATE',width:100,hidden:true,sortable : true}
]);
	var relateRecord = new Ext.data.Record.create([
//			{name:'RELATE_ID'},
		    {name:'PRIVY_NAME'},
		    {name:'PRIVY_ATTRIBUTE'},
		    {name:'IDENT_TYPE'},
		    {name:'IDENT_NO'},
		    {name:'TEL'},
		    {name:'EMAIL'},
		    {name:'CONTACT_ADDR'},
		    {name:'RELATE_DECLARANT_REL'},
		    {name:'DECLARANT_BANK_REL'},
		    {name:'IS_COMMECIAL_BANK'},
		    {name:'MAIN_ID'},
		    {name:'EFFECT_DATE'}
		]);
	var relateInfoReader = new Ext.data.JsonReader({
			totalProperty:'json.count',
			root:'json.data'
		},relateRecord);
	var relateInfoProxy = new Ext.data.HttpProxy({
				url:basepath+'/AcrmFCrRelatePrivyInfoTemp.json'
			});	
	var	relateInfoStore = new Ext.data.Store({
			restful : true,
			proxy : relateInfoProxy,
			reader :relateInfoReader,
			recordType:relateRecord
		});	
	var	relatepagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				         [ 100, '100条/页' ], [ 250, '250条/页' ],
				         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			forceSelection : true,
			width : 85	
		});
	var searchrelateData = function(grid,cobo){//根据不同的分页加载数据
				grid.store.removeAll();
				grid.store.load({
					params:{
					    'MAIN_ID':main_id,
						start:0,
						limit: parseInt(cobo.getValue())
					}
				});
		};	
	var relatenumber = parseInt(relatepagesize_combo.getValue());
			/**
			 * 监听分页下拉框选择事件
			 */
			relatepagesize_combo.on("select", function(comboBox) {
				relatebbar.pageSize = parseInt(relatepagesize_combo.getValue()),
				searchrelateData(relategrid,relatepagesize_combo);//不同分页加载数据
			});
			//分页工具条定义
	var	relatebbar = new Ext.PagingToolbar({
				pageSize : relatenumber,
				store : relateInfoStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', relatepagesize_combo]
			});		
			
	var relategrid =new Ext.grid.GridPanel({//
				title:'关系人信息',
				frame:true,
				height:200,
				autoScroll : true,
				bbar:relatebbar,
				stripeRows : true, // 斑马线
				store:relateInfoStore,
				loadMask:true,
				cm :relatecolumnmodel,
				region:'center',
				viewConfig:{
					forceFit:false,
					autoScroll:true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
	})
		
	var fieldSet = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '关系人信息',
	    items:[relategrid]
     });
	 var jsonListRecord = Ext.data.Record.create([  
		                                             {name:'DECLARANT_ATTR'},
		                                             {name:'IDENT_TYPE'},
		                                             {name:'DECLARANT_BANK_REL'},
		                                             {name:'IS_COMMECIAL_BANK'},
		                                             {name:'TEL'},
		                                             {name:'TEAM_TYPE'},
		                                             {name:'DECLARANT_NAME'},
		                                             {name:'IDENT_NO'},
		                                             {name:'STOCK_RATIO'},
		                                             {name:'EMAIL'},
		                                             {name:'CONTACT_ADDR'},
		                                             {name:'START_DATE'},
		                                             {name:'CANCLE_CAUSE'}
		                                             ]);  
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy(
	        		{
	        			url:basepath+'/acrmfcrdeclarantinfoTemp.json'
	        		}),
	        reader: new Ext.data.JsonReader({
	        	root : 'json.data'
	        }, jsonListRecord
		)});
		
	
     var MainDeclantInfoForm = new Ext.form.FormPanel({

		title : '主申报人信息',
		frame : true,
		height:200,
		autoScroll : true,
		split : true,
		items : [{
	    layout : 'column',
	    items : [
	    	{
	    		layout:'form',
	    		columnWidth:.5,
    			items:[
//    				{name:'MAIN_ID',xtype:'textfield',hidden:true},
			    	{name : 'DECLARANT_ATTR',xtype : 'combo',fieldLabel:'关联方属性',readOnly:true,cls:'x-readOnly',mode : 'local',
		    			store:lookup4Store ,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'
		    			},
			    	{name : 'IDENT_TYPE',xtype : 'combo',fieldLabel:'证件类型',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store:lookup3Store,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
//			    	{name : 'IDENT_TYPE2',xtype : 'combo',fieldLabel:'证件类型',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
			    	{name : 'DECLARANT_BANK_REL',xtype : 'combo',fieldLabel:'与银行关联关系',readOnly:true,cls:'x-readOnly',mode : 'local',store: lookup4Store,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
			    	{name : 'IS_COMMECIAL_BANK',xtype : 'combo',fieldLabel:'是否为商业银行',width: 280,valueField:'key',readOnly:true,mode : 'local',store:lookup7Store,triggerAction : 'all',displayField:'value',cls:'x-readOnly',hidden:true,anchor : '60%'},
	    			{name : 'TEL',xtype : 'textfield',fieldLabel:'电话号码',vtype:'alphanum',width: 280,readOnly:true,cls:'x-readOnly',anchor : '60%'}
	    		]
	    	},{
	    		layout:'form',
	    		columnWidth:.5,
	    		items:[
	    		 {name : 'DECLARANT_NAME',xtype : 'textfield',fieldLabel:'申报人名字',readOnly:true,cls:'x-readOnly',allowBlank:false,anchor : '60%'},
	    		 {name : 'IDENT_NO',xtype : 'textfield',fieldLabel:'证件号码',readOnly:true,cls:'x-readOnly',allowBlank:false,anchor : '60%'},
	    		 {name : 'STOCK_RATIO',xtype : 'textfield',fieldLabel:'持股比例',readOnly:true,cls:'x-readOnly',hidden:true,anchor : '60%'},
	    		 {name : 'EMAIL',xtype : 'textfield',vtype:'email',fieldLabel:'邮件地址',readOnly:true,cls:'x-readOnly',anchor : '60%'},
	    		 {name : 'CONTACT_ADDR',xtype : 'textfield',fieldLabel:'联系地址',readOnly:true,cls:'x-readOnly',anchor : '60%'},
	    		 {name : 'START_DATE', xtype: 'datefield',fieldLabel : '关联起始日期',readOnly:true,cls:'x-readOnly',format:'Y-m-d',allowBlank:true,anchor : '60%'}
	    		 ]
	    	},{
				layout:'form',
				columnWidth : 1,
				items:[
					{name :'CANCLE_CAUSE',xtype:'textarea',fieldLabel:'变更说明',readOnly:true,cls:'x-readOnly',width: 480,anchor:'80%'}
					]
				}	
			]
		}
		]
   });
   
	var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[MainDeclantInfoForm,fieldSet]
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
	store.load({params:{
						'MAIN_ID':main_id},
        callback:function(){
        	if(store.getCount()!=0){
        		loadFormData();
        	}
		}});
		function loadFormData(){
    		MainDeclantInfoForm.getForm().loadRecord(store.getAt(0));
		}
	relateInfoStore.load({
					params:{
						'MAIN_ID':main_id,
						start:0,
						limit: parseInt(relatepagesize_combo.getValue())
					}
			});
})