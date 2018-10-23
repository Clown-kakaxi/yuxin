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
//--------------------修改或新增前的对应关系人信息表格---------------------------------------
	var relate1rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
			header : 'No.',
			width : 28
		});
	var relate1columnmodel = new Ext.grid.ColumnModel([
	    relate1rownum,
	    {header:'关系人编号',dataIndex:'RELATE_ID',width:100,sortable : true},
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
	var relate1Record = new Ext.data.Record.create([
			{name:'RELATE_ID'},
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
	var relate1InfoReader = new Ext.data.JsonReader({
			totalProperty:'json.count',
			root:'json.data'
		},relate1Record);
	var relate1InfoProxy = new Ext.data.HttpProxy({
				url:basepath+'/AcrmFCrRelatePrivyInfo.json'
			});	
	var	relate1InfoStore = new Ext.data.Store({
			restful : true,
			proxy : relate1InfoProxy,
			reader :relate1InfoReader,
			recordType:relate1Record
		});	
	var	relate1pagesize_combo = new Ext.form.ComboBox({
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
	
	var relate1number = parseInt(relate1pagesize_combo.getValue());
			/**
			 * 监听分页下拉框选择事件
			 */
			relate1pagesize_combo.on("select", function(comboBox) {
				relate1bbar.pageSize = parseInt(relate1pagesize_combo.getValue()),
				searchrelateData(relate1grid,relate1pagesize_combo);//不同分页加载数据
			});
			//分页工具条定义
	var	relate1bbar = new Ext.PagingToolbar({
				pageSize : relate1number,
				store : relate1InfoStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', relate1pagesize_combo]
			});		
			
	var relate1grid =new Ext.grid.GridPanel({//
				title:'关系人修改新增钱信息',
				frame:true,
				height:200,
				autoScroll : true,
				bbar:relate1bbar,
				stripeRows : true, // 斑马线
				store:relate1InfoStore,
				loadMask:true,
				cm :relate1columnmodel,
				region:'center',
				viewConfig:{
					forceFit:false,
					autoScroll:true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
	})
//----------------------新增或修改后的关系人数据信息------------------------------------
		var relate2rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
			header : 'No.',
			width : 28
		});
	var relate2columnmodel = new Ext.grid.ColumnModel([
	    relate2rownum,
	    {header:'关系人编号',dataIndex:'RELATE_ID',width:100,sortable : true,hidden:true},
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
	    {header:'是否为商业银行',dataIndex:'IS_COMMECIAL_BANK',width:100,renderer:function(value){
					for(var i=0;i< lookup7Store.data.length;i++){
						if(lookup7Store.data.items[i].data.key==value){
						   return lookup7Store.data.items[i].data.value
						}
					}
					},sortable : true},
//	    {header:'关联人编号',dataIndex:'MAIN_ID',width:100,sortable : true},
	    {header:'生效日',dataIndex:'EFFECT_DATE',width:100,hidden:true,sortable : true}
]);
	var relate2Record = new Ext.data.Record.create([
			{name:'RELATE_ID'},
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
//		    {name:'MAIN_ID'},
		    {name:'EFFECT_DATE'}
		]);
	var relate2InfoReader = new Ext.data.JsonReader({
			totalProperty:'json.count',
			root:'json.data'
		},relate2Record);
	var relate2InfoProxy = new Ext.data.HttpProxy({
				url:basepath+'/AcrmFCrRelatePrivyInfoTemp.json'
			});	
	var	relate2InfoStore = new Ext.data.Store({
			restful : true,
			proxy : relate2InfoProxy,
			reader :relate2InfoReader,
			recordType:relate2Record
		});	
	var	relate2pagesize_combo = new Ext.form.ComboBox({
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
	var relate2number = parseInt(relate2pagesize_combo.getValue());
			/**
			 * 监听分页下拉框选择事件
			 */
			relate2pagesize_combo.on("select", function(comboBox) {
				relate2bbar.pageSize = parseInt(relate2pagesize_combo.getValue()),
				searchrelateData(relate2grid,relate2pagesize_combo);//不同分页加载数据
			});
			//分页工具条定义
	var	relate2bbar = new Ext.PagingToolbar({
				pageSize : relate2number,
				store : relate2InfoStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', relate2pagesize_combo]
			});		
			
	var relate2grid =new Ext.grid.GridPanel({//
				title:'关系人修改新增信息后',
				frame:true,
				height:200,
				autoScroll : true,
				bbar:relate2bbar,
				stripeRows : true, // 斑马线
				store:relate2InfoStore,
				loadMask:true,
				cm :relate2columnmodel,
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
	    items:[relate1grid,relate2grid]
     });
	
	
   
	var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[fieldSet]
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
	
	relate1InfoStore.load({
					params:{
						'MAIN_ID':main_id,
						start:0,
						limit: parseInt(relate1pagesize_combo.getValue())
					}
			});
	relate2InfoStore.load({
			params:{
				'MAIN_ID':main_id,
				start:0,
				limit: parseInt(relate2pagesize_combo.getValue())
			}
	});
})