Ext.onReady(function() {
	var ifStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	
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
	
	
	
	var store = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/mktActivityOrgback.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'orgId', mapping: 'ORG_ID'},
		      {name: 'orgName', mapping: 'ORG_NAME'},
		      {name: 'num', mapping: 'NUM'},
		      {name: 'deliver', mapping: 'DELIVER'},
		      {name: 'sucess', mapping: 'SUCESS'},
		      {name: 'per', mapping: 'PER'}
		      ])
	  });
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
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
	
	//默认加载数据
	store.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
	
	
	pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		  store.reload({
			  params : {
			  start : 0,
			  limit : parseInt(pagesize_combo.getValue())
		  }
		  });
	  });
	
	
	 var sm = new Ext.grid.CheckboxSelectionModel();
	  var rownum = new Ext.grid.RowNumberer({
		  header : 'No.',
		  width : 28
	  });
						
	  var cm = new Ext.grid.ColumnModel([rownum,// 定义列模型
	                                     {header : '机构Id', dataIndex : 'orgId',sortable : true,width : 120,hidden : true}, 
	                                     {header : '机构名称', dataIndex : 'orgName',sortable : true,width : 120}, 
	                                     {header : '下发营销活动数', dataIndex : 'num',sortable : true,width : 120,align : 'right'}, 
	                                     {header : '下发客户数', dataIndex : 'deliver',sortable : true,width : 120,align : 'right'}, 
	                                     {header : '营销成功客户数', dataIndex : 'sucess',sortable : true,width : 120,align : 'right'}, 
	                                     {header : '完成率', dataIndex : 'per',sortable : true,width : 120,renderer : percent(false),align : 'right'}
	                                     ]);
	 var number = parseInt(pagesize_combo.getValue());
	
var bbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});


	
	var  searchPanel = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .33,
			layout : 'form',
			items : [new Com.yucheng.bcrm.common.OrgField({
				searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				fieldLabel : '所属机构',
				labelStyle : 'text-align:right;',
				id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
				hiddenName: 'ORG_ID',   //后台获取的参数名称
				anchor : '90%',
				checkBox:true //复选标志
			})
			]
			
		}],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = searchPanel.getForm().getValues(false);
				store.on('beforeLoad', function() {
					this.baseParams = {
						"condition" : Ext.encode(conditionStr)
					};
				});
				store.load( {
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});

			}
		}, {
			text : '重置',
			handler : function() {
				searchPanel.form.reset();
			}
		}]
		
	});
	
	store.on('beforeload', function() {
		  var conditionStr =  searchPanel.getForm().getValues(false);
		  this.baseParams = {
				  "condition":Ext.encode(conditionStr)
		  };
	  });
	
	 var listPanel = new Ext.grid.GridPanel({
	      layout:'fit',
		  frame : true,
		  autoScroll : true,
		  region : 'center', // 返回给页面的div
		  store: store,
		  stripeRows : true, // 斑马线
		  sm:sm,
		  cm : cm,
		  bbar : bbar,
		  viewConfig : {},
		  loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  }
	  });

		var view = new Ext.Viewport( {
				layout : "fit",
				frame : true,
				items : [ {
					layout : 'border',
					items : [
							{
								region : 'north',
								id : 'north-panel',
								title : "营销活动反馈查询",
								height : 100,
								layout : 'fit',
								items : [ searchPanel ]
							},
							{
								region : 'center',
								id : 'center-panel',
								layout : 'fit',
								items : [ listPanel ]
							}
					]
				} ]
			});

});