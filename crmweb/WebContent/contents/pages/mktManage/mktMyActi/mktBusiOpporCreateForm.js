/**
 * 营销管理->商机管理->商机池：新增商机表单及窗体定义JS文件；wzy；2013-02-22
 */

	var StageStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=STAGE_LEAVL'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
		// "客户类别"下拉框
	var chanceCategoryStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
//	// "客户类型"下拉框
//	var custTypeStore = new Ext.data.Store({
//		restful : true,
//		autoLoad : true,
//		proxy : new Ext.data.HttpProxy({
//			url : basepath + '/lookup.json?name=CDE0100018'
//		}),
//		reader : new Ext.data.JsonReader({
//			root : 'JSON'
//		}, [ 'key', 'value' ])
//	});
//	
	
	var chanceStatStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var custStatStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=CUSTOMER_STATUS'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
// 新增商机窗口From表单
var addBusiOpporForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 200,
	frame : true,
	title : '新增->商机基本信息',
	labelAlign : 'right',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '活动编号',
				readOnly:true,
				name : 'myActiId',
				anchor : '90%'
			},{
				xtype : 'textfield',
				fieldLabel : '*商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'opporName',
				anchor : '90%'
			}/*, new Com.yucheng.crm.common.ProductManage({
				xtype : 'productChoose',
				fieldLabel : '*商机产品',
				labelStyle : 'text-align:right;',
				name : 'prodName',
				hiddenName : 'prodId',
				singleSelect : false,
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			})*/, {
				xtype : 'textfield',
				fieldLabel : '商机产品',
				name : 'prodId',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '产品名称',
				name : 'prodName',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'datefield',
				fieldLabel : '*商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporStartDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '营销活动名称',
				name : 'activityQuery',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			} , {
				xtype : 'textfield',
				fieldLabel : '客户名称',
				name : 'custName',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '客户ID',
				name : 'custId',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, new Ext.form.ComboBox({
				hiddenName : 'custType',
				fieldLabel : '客户类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : custStatStore,
				readOnly:true,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'numberfield',
				fieldLabel : '预计金额(元)',
				name : 'planAmount',
				labelStyle : 'text-align:right;',
				value : '0',
				anchor : '90%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ new Ext.form.ComboBox({
				hiddenName : 'opporType',
				fieldLabel : '商机类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceStatStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				anchor : '90%'
			}), {
				xtype : 'datefield',
				fieldLabel : '*商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporDueDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, {
				xtype : 'datefield',
				fieldLabel : '*商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporEndDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			},new Ext.form.ComboBox({
				hiddenName : 'custCategory',
				fieldLabel : '客户类别',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceCategoryStore,
				displayField : 'value',
				valueField : 'key',
				readOnly:true,
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'custContactName',
				readonly : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '费用预算(元)',
				name : 'planCost',
				value : '0',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			} ]
		} ]
	}, {
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'opporContent',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'memo',
			anchor : '95%'
		} ]
	} ]
});

//我的营销活动详情-基本信息
var myMktDetailForm = new Ext.FormPanel({
	labelWidth : 120,
	height : 170,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '活动编号',
				readOnly:true,
				name : 'myActiId',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '活动执行人员编号',
				name : 'executorId',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '计划开始时间',
				name : 'PSTART_DATE',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '实际开始时间',
				name : 'ASTART_DATE',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '活动类型',
				readOnly:true,
				name : 'MKT_ACTI_TYPE_ORA',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, new Ext.form.ComboBox({
				hiddenName : 'progressStage',
				fieldLabel : '进展阶段',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : StageStore,
				readOnly:true,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '客户编号',
				name : 'custId',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, new Ext.form.ComboBox({
				hiddenName : 'custCategory',
				fieldLabel : '客户类别',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceCategoryStore,
				readOnly:true,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '费用预算',
				readOnly:true,
				name : 'MKT_ACTI_COST',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textarea',
				fieldLabel : '涉及客户描述',
				readOnly:true,
				name : 'ACTI_CUST_DESC',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '活动名称',
				name : 'activityQuery',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '活动执行人名称',
				name : 'executorName',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '计划结束时间',
				name : 'PEND_DATE',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '实际结束时间',
				name : 'AEND_DATE',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '活动状态',
				name : 'MKT_ACTI_STAT_ORA',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			},new Ext.form.ComboBox({
				hiddenName : 'isCreChance',
				fieldLabel : '是否已创建商机',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				readOnly:true,
				store : chanceStatStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '*客户名称',
				editable : true,
				readOnly:true,
				name : 'custName',
				anchor : '90%'
			} ,new Ext.form.ComboBox({
				hiddenName : 'custType',
				fieldLabel : '客户类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : custStatStore,
				displayField : 'value',
				valueField : 'key',
				readOnly:true,
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				readOnly:true,
				name : 'custContactName',
				readonly : true,
				anchor : '90%'
			}, {
				xtype : 'textarea',
				fieldLabel : '备注',
				name : 'ACTI_REMARK',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		} ]
	} ]
});

var custContrastRecord = Ext.data.Record.create(
		[
		 {name:'prodId',mapping:'PRODUCT_ID'},
		 {name:'prodName',mapping:'PRODUCT_NAME'}
		 ]
);
var custContrastReader = new Ext.data.JsonReader(//读取jsonReader
		{
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root:'json.data'
		},custContrastRecord
);
var custContrastStore = new Ext.data.Store({//产品对照关系store
        restful : true, 
        proxy : new Ext.data.HttpProxy({ 
        	url:basepath+'/mktactivityrelateinfoaction.json',
        	method:'get'
        }),
		reader:custContrastReader	
});

// 每页显示条数下拉选择框
var pagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
		         [ 50, '50条/页' ],[ 100, '100条/页' ]  ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	resizable : true,
	width : 85
});

custContrastStore.reload({
	params : {
		start : 0,
		limit : parseInt(pagesize_combo.getValue())
	}
});
// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo.getValue()),
	custContrastStore.reload({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
});

var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
		pageSize : parseInt(pagesize_combo.getValue()),
		store : custContrastStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
});



 /*************************************列模型***********************************************/
 // 定义自动当前页行号
 var rownum = new Ext.grid.RowNumberer( {
     header : 'No',
     width : 28
 });
 var sm = new Ext.grid.CheckboxSelectionModel();
 var custContrastColumns = new Ext.grid.ColumnModel([rownum, sm, 
                                     				{ header:'aimProdId',dataIndex:'aimProdId',sortable:true,hidden:true},
                                     				{ header:'产品编号',dataIndex:'prodId',sortable:true,width:150},
                                     				{ header:'产品名称',dataIndex:'prodName',sortable:true,width:150}
                                     				]
                                      );
 var custContrastGrid = new Ext.grid.EditorGridPanel({			
		store:custContrastStore, 
		title : '新增->商机关联产品信息',
		frame:true,
		height : 750,
		cm:custContrastColumns,
		region:'center',
		sm:sm,
        bbar:bbar,
	    loadMask : {
		  msg : '正在加载表格数据,请稍等...'
	    }
 });
 
 
 
 //start我的营销活动-活动客户关联产品列表

 var custContrastRecord1 = Ext.data.Record.create(
 		[
 		 {name:'prodId',mapping:'PRODUCT_ID'},
 		 {name:'prodName',mapping:'PRODUCT_NAME'}
 		 ]
 );
 var custContrastReader1 = new Ext.data.JsonReader(//读取jsonReader
 		{
 			successProperty : 'success',
 			idProperty : 'ID',
 			totalProperty : 'json.count',
 			root:'json.data'
 		},custContrastRecord1
 );
 var custContrastStore1 = new Ext.data.Store({//产品对照关系store
         restful : true, 
         proxy : new Ext.data.HttpProxy({ 
         	url:basepath+'/mktactivityrelateinfoaction.json',
         	method:'get'
         }),
 		reader:custContrastReader1	
 });

 //*********************************
	// 每页显示条数下拉选择框
	var cust_pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var cust_bbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(cust_pagesize_combo.getValue()),
		store : custContrastStore1,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', cust_pagesize_combo]
	});
	
	cust_pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  cust_bbar.pageSize = parseInt(cust_pagesize_combo.getValue()),
		  custContrastStore1.reload({
			  params : {
			  start : 0,
			  limit : parseInt(cust_pagesize_combo.getValue())
		  }
		  });
	  });
 //********************************
 
  // 定义自动当前页行号
  var rownum1 = new Ext.grid.RowNumberer( {
      header : 'No',
      width : 28
  });
  
  var custContrastColumns1 = new Ext.grid.ColumnModel([rownum1, 
                                      				{ header:'aimProdId',dataIndex:'aimProdId',sortable:true,hidden:true},
                                      				{ header:'产品编号',dataIndex:'prodId',sortable:true,width:150},
                                      				{ header:'产品名称',dataIndex:'prodName',sortable:true,width:150}
                                      				]
                                       );
  var custContrastGrid1 = new Ext.grid.EditorGridPanel({			
 		store:custContrastStore1, 
 		frame:true,
 		height : 250,
 		bbar:cust_bbar,
 		cm:custContrastColumns1,
 		region:'center',
         viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
 	    },
 	    loadMask : {
 		  msg : '正在加载表格数据,请稍等...'
 	    }
  });
 
 //end
  
  //start渠道信息
  var chanelTypeStore = new Ext.data.Store({//渠道类型的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/chaneltypeinfo.json?tableName='+'OCRM_F_MM_CHANNEL_INFO'
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [ 'CHANNEL_ID', 'CHANNEL_NAME' ])
	});   

  var chanelContrastRecord = Ext.data.Record.create(
  		[
  		 {name:'actiChannelId',mapping:'ACTI_CHANNEL_ID'},
  		 {name:'appCustLever',mapping:'APP_CUST_LEVER'},
  		 {name:'cahnTemCont',mapping:'CAHN_TEM_CONT'},
  		 {name:'cahnTemName',mapping:'CAHN_TEM_NAME'},
  		 {name:'createDate',mapping:'CREATE_DATE'},
  		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
  		 {name:'productId',mapping:'PRODUCT_ID'},
  		 {name:'productName',mapping:'PRODUCT_NAME'},
  		 {name:'createUser',mapping:'CREATE_USER'},
  		 {name:'mktActiId',mapping:'MKT_ACTI_ID'}
  		 ]
  );
  var chanelContrastReader = new Ext.data.JsonReader(//读取jsonReader
  		{
  			successProperty : 'success',
  			idProperty : 'ID',
  			totalProperty : 'json.count',
  			root:'json.data'
  		},chanelContrastRecord
	);
	var chanelContrastStore = new Ext.data.Store({//产品对照关系store
	        restful : true, 
	        proxy : new Ext.data.HttpProxy({ 
	        	url:basepath+'/mktactivityrelateinfoaction.json',
	        	method:'get'
	        }),
			reader:chanelContrastReader
			
	});

	 var chanelContrastColumns = new Ext.grid.ColumnModel(
				{
					columns:[
					{ header:'ID',dataIndex:'actiChannelId',sortable:true,hidden:true},
					{ header:'营销活动编号',dataIndex:'mktActiId',sortable:true,hidden:true},
					{ header:'渠道编号',dataIndex:'productId',sortable:true,width:150},
					{ header:'渠道名称',dataIndex:'productName',sortable:true,width:160},
					{ header:'创建人编号',dataIndex:'createUser',width:160,sortable:true,hidden:true},
					{ header:'创建人',dataIndex:'createUserName',width:160,sortable:true},
					{ header:'创建时间',dataIndex:'createDate',width:160,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/
	 var chanelContrastGrid = new Ext.grid.EditorGridPanel({			
			store:chanelContrastStore, 
			frame:true,
			height : 250,
//			width : 200,
			cm:chanelContrastColumns,
			region:'center',
			viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		  	},
			loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  	}
	 });
  //end
 
   var addBusiOpporPanel = new Ext.Panel({ 
		   layout : 'accordion', 
		   layoutConfig :{ 
		    activeOnTop : false,             		//设置打开的子面板置顶 
		    fill : true,                     		//子面板充满父面板的剩余空间 
		    hideCollapseTool: false,         		//显示“展开收缩”按钮 
		    titleCollapse : true,     				//允许通过点击子面板的标题来展开或收缩面板 
		    animate:true          					//使用动画效果 
		   }, 
		   title:'我的营销活动->创建商机', 	//面板表一
		   frame:true,                				//渲染面板 
		   height : 750, 							//自定义高度
		   width : 420, 							//自定义宽度
//		   defaults : {                 //设置默认属性 
//		    bodyStyle:'background-color:#FFFFFF;padding:15px' //设置面板体的背景色 
//		   }, 
		   items: [ 
		    addBusiOpporForm, custContrastGrid
		   ] 
		  }); 
	 
// 定义新增窗口
var addBusiOpporWindow = new Ext.Window({
	plain : true,
	layout : 'fit',
	width : 750,
	height : 420,
	resizable : true,
	draggable : true,
	closable : true,
	autoScroll : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : "center",
	border : false,
	constrain : true,
	 items : [addBusiOpporPanel],
	buttons : [ {
		text : '保存',
		handler : saveAddBusiOppor
	}, {
		text : '提交',
		handler : submitAddBusiOppor
	}, {
		text : '关闭',
		handler : function() {
			addBusiOpporWindow.hide();
		}
	} ],
	listeners : {
		"hide" : function() {
			addBusiOpporForm.getForm().reset();
		},
		"beforeshow" : function() {// 窗体显示时间，进行一些数据设置初始化操作
			addBusiOpporForm.getForm().reset();
		}
	}
});

//定义新增窗口
var detailBusiOpporWindow = new Ext.Window({
	title : '我的营销活动详细信息',
	plain : true,
	layout : 'fit',
	width : 750,
	height : 420,
	resizable : true,
	draggable : true,
	closable : true,
	autoScroll : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : "center",
	border : false,
	constrain : true,
	 items : [{
         xtype : 'fieldset',
         title : '我的营销活动基本信息',
         collapsed:false,
		 collapsible : true,
         layout : 'fit',
         id:'myopporBaseInfo',
         buttonAlign : "center",
         items : [myMktDetailForm]
     },{
         xtype : 'fieldset',
         title : '我的营销活动涉及产品列表',
         collapsed:true,
         collapsible : true,
         layout : 'fit',
         id:'myrelateProdInfo',
         buttonAlign : "center",
         items : [custContrastGrid1]
     },{
         xtype : 'fieldset',
         title : '我的营销活动涉及渠道列表',
         collapsed:true,
         collapsible : true,
         layout : 'fit',
         id:'myrelateChanelInfo',
         buttonAlign : "center",
         items : [chanelContrastGrid]
     }],
     
	buttons : [ {
		text : '关闭',
		handler : function() {
		detailBusiOpporWindow.hide();
		}
	} ],
	listeners : {
		"hide" : function() {
	myMktDetailForm.getForm().reset();
		},
		"beforeshow" : function() {// 窗体显示时间，进行一些数据设置初始化操作
			Ext.getCmp('myrelateProdInfo').collapse();
			Ext.getCmp('myrelateChanelInfo').collapse();
    		Ext.getCmp('myopporBaseInfo').expand();
			myMktDetailForm.getForm().reset();
		}
	}
});



// 保存商机
// 对商机数据做临时存储，只控制必须输入“商机名称”，在提交时，判断必填项是否完全填写
function saveAddBusiOppor() {
	var opporName = addBusiOpporForm.form.findField('opporName').getValue();
	if (opporName == null || opporName == "") {
		Ext.Msg.alert('提示', '商机名称不能为空！');
		return false;
	}
	var selectStr= custContrastGrid.getSelectionModel().getSelections();
	var tempLength = custContrastGrid.getSelectionModel().getSelections().length;
	var tempProdId = '';
	var tempProdName = '';
	if(tempLength<=0){
		Ext.Msg.alert('提示','请选择关联产品');
		return false;
	}else{
		tempProdId=selectStr[0].data.prodId;	
		tempProdName=selectStr[0].data.prodName;
		for(var i = 1;i<tempLength;i++){
			tempProdId = tempProdId+','+selectStr[i].data.prodId;
			tempProdName = tempProdName+','+selectStr[i].data.prodName;
		}
			addBusiOpporForm.form.findField('prodId').setValue(tempProdId);
			addBusiOpporForm.form.findField('prodName').setValue(tempProdName);
			
			var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'saveOrUpdateBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : addBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			Ext.Msg.alert('提示', '保存成功！');
//			store.load({
//				params : {
//					start : 0,
//					limit : bbar.pageSize
//				}
//			});
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '保存失败！');
		}
	});
	addBusiOpporWindow.hide();
	}
}

// 提交商机
function submitAddBusiOppor() {
	if (!addBusiOpporForm.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
		return false;
	}
	if (!dateCheck()) {
		return false;
	}
	var tempLength = custContrastGrid.getSelectionModel().getSelections().length;
	if(tempLength<=0){
		Ext.Msg.alert('提示','请选择关联产品');
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'submitBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : addBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			 Ext.Ajax.request({
    				         url: basepath +'/MktMyActiListAction!updateIsCreChance.json',
    				         params : {
    				         myMktActiId:addBusiOpporForm.form.findField('myActiId').getValue()
    						 },
					         success:function(response){
							 Ext.Msg.alert('提示', '提交成功');
    						 	}
    						 });
			
//			Ext.Msg.alert('提示', '提交成功！');
			
			
			
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '提交失败！');
		}
	});
	addBusiOpporWindow.hide();
}

// 日期判断
function dateCheck() {
	var opporStartDate = addBusiOpporForm.form.findField('opporStartDate')
			.getValue();
	var opporEndDate = addBusiOpporForm.form.findField('opporEndDate')
			.getValue();
	var opporDueDate = addBusiOpporForm.form.findField('opporDueDate')
			.getValue();
	// 1、商机“开始日期”不能晚于“完成日期”
	if (opporStartDate >= opporEndDate) {
		Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“完成日期”！');
		return false;
	}
	// 2、商机“开始日期”不能晚于“商机有效期”
	if (opporStartDate >= opporDueDate) {
		Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“商机有效期”！');
		return false;
	}
	// 3、商机“完成日期”不能晚于“邮寄有效期”
	if (opporEndDate > opporDueDate) {
		Ext.Msg.alert('提示', '商机“完成日期”不能晚于或等于“商机有效期”！');
		return false;
	}
	return true;
}

// 打开 新增商机 窗口
function busiOpportAddWindowInit() {
	addBusiOpporWindow.show();
}
//营销活动详情 窗口
function busiOpportDetailWindowInit() {
	detailBusiOpporWindow.show();
}

