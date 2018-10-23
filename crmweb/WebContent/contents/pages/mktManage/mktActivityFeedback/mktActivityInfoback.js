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
	
	//活动状态
	var actiStatusStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=MACTI_STATUS'
		}),
		reader : new Ext.data.JsonReader({
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
		  proxy : new Ext.data.HttpProxy({url:basepath+'/mktActivityInfoback.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'mktActiId', mapping: 'MKT_ACTI_ID'},
		      {name: 'mktActiName', mapping: 'MKT_ACTI_NAME'},
		      {name: 'mktActiStat', mapping: 'MKT_ACTI_STAT_ORA'},
		      {name: 'deliver', mapping: 'DELIVER'},
		      {name: 'sucess', mapping: 'SUCESS'},
		      {name: 'per', mapping: 'PER'},
		      {name: 'aStartDate', mapping: 'ASTART_DATE'},
		      {name: 'aEndDate', mapping: 'AEND_DATE'},
		      {name: 'createUser', mapping: 'CREATE_USER'},
		      {name: 'userName', mapping: 'USER_NAME'},
		      {name: 'createDate', mapping: 'CREATE_DATE'}
		      
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
	  

	  var cm = new Ext.grid.ColumnModel([rownum,	// 定义列模型
	                                     {header : '营销活动ID', dataIndex : 'mktActiId',sortable : true,width : 120,hidden : true}, 
	                                     {header : '营销活动名称', dataIndex : 'mktActiName',sortable : true,width : 120},
	                                     {header : '营销活动状态', dataIndex : 'mktActiStat',sortable : true,width : 120}, 
	                                     {header : '下发客户数', dataIndex : 'deliver',sortable : true,width : 120,align : 'right'}, 
	                                     {header : '营销成功客户数', dataIndex : 'sucess',sortable : true,width : 120,align : 'right'}, 
	                                     {header : '完成率', dataIndex : 'per',sortable : true,width : 120,renderer : percent(false),align : 'right'}, 
	                                     {header : '实际开始时间', dataIndex : 'aStartDate',sortable : true,width : 120},
	                                     {header : '实际结束日期', dataIndex : 'aEndDate',sortable : true,width : 120},
	                                     {header : '创建人Id', dataIndex : 'createUser',sortable : true,width : 120,hidden : true},
	                                     {header : '创建人', dataIndex : 'userName',sortable : true,width : 120},
	                                     {header : '创建日期', dataIndex : 'createDate',sortable : true,width : 120}
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
		height : 130,
		frame : true,
		region : 'north',
		autoScroll : true,
			layout : 'column',
			items : [   {
					columnWidth : .25,
					layout : 'form',
					items : [{
						fieldLabel : '实际开始时间从',
						xtype : 'datefield',
						id : 'ASTART_DATE_S',
						format : 'Y-m-d',
						editable : false,
						name : 'ASTART_DATE_S',
						anchor : '90%'
					}]
				},{
				    columnWidth : .25,
                    layout : 'form',
                    labelWidth: 20,
					items : [{
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						fieldLabel : '至',
						name : 'ASTART_DATE_E',
						id : 'ASTART_DATE_E',
						anchor : '65%'
					}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				//Width : '100',
				name : 'MKT_ACTI_NAME',
				fieldLabel : '营销活动名称',
				anchor : '90%'
			} ]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [ {
				store : actiStatusStore,
				xtype : 'combo', resizable : true,
				fieldLabel : '营销活动状态',
				name : 'MKT_ACTI_STAT',
				hiddenName : 'MKT_ACTI_STAT',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				//width : '100',
				anchor : '90%'
			} ]
		},  {
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '实际结束时间从',
				xtype : 'datefield',
				id : 'AEND_DATE_S',
				format : 'Y-m-d',
				editable : false,
				name : 'AEND_DATE_S',
				anchor : '90%'
			}]
		},{
		    columnWidth : .25,
            layout : 'form',
            labelWidth: 20,
			items : [{
				xtype : 'datefield',
				format : 'Y-m-d',
				editable : false,
				fieldLabel : '至',
				name : 'AEND_DATE_E',
				id : 'AEND_DATE_E',
				anchor : '65%'
			}]}],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {
								var start = Ext.getCmp('ASTART_DATE_S').getValue();
								var end = Ext.getCmp('ASTART_DATE_E').getValue();
								var start1 = Ext.getCmp('AEND_DATE_S').getValue();
								var end1 = Ext.getCmp('AEND_DATE_E').getValue();
								if(start==''&&end!=''){
									Ext.Msg.alert('消息框','请先选择开始时间！');
									Ext.getCmp('ASTART_DATE_E').reset();
									return;
								}else if(end!=''&&start>end){
									Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
									Ext.getCmp('ASTART_DATE_E').reset();
									return;
								}
								if(start1==''&&end1!=''){
									Ext.Msg.alert('消息框','请先选择开始时间！');
									Ext.getCmp('AEND_DATE_E').reset();
									return;
								}else if(end1!=''&&start1>end1){
									Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
									Ext.getCmp('AEND_DATE_E').reset();
									return;
								}
				var conditionStr = searchPanel.getForm().getValues(
						false);
				store.on('beforeLoad', function() {
					this.baseParams = {
						"condition" : Ext.encode(conditionStr)
					};
				});
				store.load({
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});

			}
		},{
			text : '重置',
			handler : function() {
				searchPanel.getForm().reset();
			}}
		]

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
								height : 140,
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