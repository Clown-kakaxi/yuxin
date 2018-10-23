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
		  proxy : new Ext.data.HttpProxy({url:basepath+'/mktActivityManegerback.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'executorName', mapping: 'EXECUTOR_NAME'},
		      {name: 'executorId', mapping: 'EXECUTOR_ID'},
		      {name: 'mktActiId', mapping: 'MKT_ACTI_ID'},
		      {name: 'mktActiName', mapping: 'MKT_ACTI_NAME'},
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
	                                     {header : '客户经理', dataIndex : 'executorName',sortable : true,width : 120},
	                                     {header : '客户经理ID', dataIndex : 'executorId',sortable : true,width : 120,hidden : true},
	                                     {header : '营销活动ID', dataIndex : 'mktActiId',sortable : true,width : 120,hidden : true}, 
	                                     {header : '营销活动名称', dataIndex : 'mktActiName',sortable : true,width : 120},
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
			items : [new Com.yucheng.crm.common.OrgUserManage({ 
				xtype:'userchoose',
				fieldLabel : '所属客户经理', 
				id:'CUST_MANAGER',
				labelStyle: 'text-align:right;',
				hiddenName:'EXECUTOR_ID',
//				searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				singleSelect:false,
				anchor : '90%'
				})]
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
	
	// 定义活动明细窗口
	var editActivityWindow = new Ext.Window({
		title : '活动明细',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 440,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'center',
						layout : 'fit',
						items : [ editGrid ]
					}
			]
		} ],
	listeners : {
		beforeshow : function(){
		editGrid.tbar.setDisplayed(false);
		editStore.on('beforeload', function() {
    		this.baseParams = {
    				myActiId:document.getElementById('myActiIdStr').value
    		};
    	});
		},
		show : function(){
			editStore.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo1.getValue())
				}
			});
			}
	}
	});
	
	// 展示活动明细窗口
	function editInit() {
		editActivityWindow.show();
	}
	
	var store2 = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/MktMyActiListAction.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'myActiId', mapping: 'MY_ACTI_ID'},
		      {name: 'activityQuery', mapping: 'MY_ACTI_NAME'},
		      {name: 'custId', mapping: 'CUST_ID'},
		      {name: 'custName', mapping: 'CUST_NAME'},
		      {name: 'executorId', mapping: 'EXECUTOR_ID'},
		      {name: 'executorName', mapping: 'EXECUTOR_NAME'},
		      {name: 'custCategory', mapping: 'CUST_TYP'},
		      {name: 'custType', mapping: 'CUST_TYP'},
		      {name: 'custContactName', mapping: 'CUST_CONTACT_NAME'},
		      {name: 'progressStage', mapping: 'PROGRESS_STAGE'},
		      {name: 'PROGRESS_STAGE_ORA'},
		      {name: 'isCreChance', mapping: 'IS_CRE_CHANCE_ORA'},
		      {name: 'createUser', mapping: 'CREATE_USER'},
		      {name: 'createUserName', mapping: 'USER_NAME'},
		      {name: 'createDate', mapping: 'CREATE_DATE'}
		      ])
	  });
	// 每页显示条数下拉选择框
	var pagesize_combo2 = new Ext.form.ComboBox({
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
	
	var bbar2 = new Ext.PagingToolbar({// 分页工具栏
		pageSize : number,
		store : store2,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo2]
	});

	
	pagesize_combo2.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  bbar2.pageSize = parseInt(pagesize_combo2.getValue()),
		  store2.reload({
			  params : {
			  start : 0,
			  limit : parseInt(pagesize_combo2.getValue())
		  }
		  });
	  });
	
	 var sm2 = new Ext.grid.CheckboxSelectionModel();
	 var rownum2 = new Ext.grid.RowNumberer({
		  header : 'No.',
		  width : 28
	  });
	 var cm2 = new Ext.grid.ColumnModel([rownum2,sm2,	// 定义列模型
	                                     {header : '活动编号', dataIndex : 'myActiId',sortable : true,width : 120,hidden : true}, 
	                                     {header : '活动名称', dataIndex : 'activityQuery',sortable : true,width : 120},
	                                     {header : '客户Id', dataIndex : 'custId',sortable : true,width : 120,hidden : true}, 
	                                     {header : '客户名称', dataIndex : 'custName',sortable : true,width : 120}, 
	                                     {header : '活动执行人员编号', dataIndex : 'executorId',sortable : true,width : 120}, 
	                                     {header : '活动执行人名称', dataIndex : 'executorName',sortable : true,width : 120}, 
	                                     {header : '进展阶段', dataIndex : 'progressStage',sortable : true,width : 120,hidden : true},
	                                     {header : '客户类别', dataIndex : 'custCategory',sortable : true,width : 120,hidden : true},
	                                     {header : '客户类型', dataIndex : 'custType',sortable : true,width : 120,hidden : true},
	                                     {header : '联系人', dataIndex : 'custContactName',sortable : true,width : 120,hidden : true},
	                                     {header : '进展阶段', dataIndex : 'PROGRESS_STAGE_ORA',sortable : true,width : 120}, 
	                                     {header : '是否已创建商机', dataIndex : 'isCreChance',sortable : true,width : 120}, 
	                                     {header : '创建人Id', dataIndex : 'createUser',sortable : true,width : 120,hidden : true},
	                                     {header : '创建人', dataIndex : 'createUserName',sortable : true,width : 120},
	                                     {header : '创建日期', dataIndex : 'createDate',sortable : true,width : 120}
	                                     ]);
	 var number2 = parseInt(pagesize_combo2.getValue());
	
	var infoGrid = new Ext.grid.GridPanel({
	      layout:'fit',
		  frame : true,
		  autoScroll : true,
		  region : 'center', // 返回给页面的div
		  store: store2,
		  stripeRows : true, // 斑马线
		  sm:sm2,
		  cm : cm2,
		  tbar : [{
							text : '活动明细',
							iconCls : 'detailIconCss',
							handler : function() {
								var selectLength = infoGrid
								.getSelectionModel()
								.getSelections().length;
	
								var selectRe = infoGrid
										.getSelectionModel()
										.getSelections()[0];
	
								if (selectLength != 1) {
									Ext.Msg.alert("提示", "请选择一条记录!");
								}else {
									
										document.getElementById('myActiIdStr').value = selectRe.data.myActiId;
										editInit();
								}
							}
						}
						],
		  bbar : bbar2,
		  viewConfig : {},
		  loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  }
	  });
	//详细信息窗口
	var InfoWindow = new Ext.Window({
		title : '详细信息',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 440,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'center',
						layout : 'fit',
						items : [ infoGrid ]
					}
			]
		} ],
		listeners : {
			beforeshow : function(){
			store2.on('beforeload', function() {
	    		this.baseParams = {
	    				ActiId:document.getElementById('ActiIdStr').value,
	    				exeId:document.getElementById('exeStr').value
	    		};
	    	});
			},
			show : function(){
				store2.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo2.getValue())
					}
				});
				}
		}

	});
	
	
	// 详细信息
	function showInfo() {
		InfoWindow.show();
	}
	
	 var listPanel = new Ext.grid.GridPanel({
	      layout:'fit',
		  frame : true,
		  autoScroll : true,
		  region : 'center', // 返回给页面的div
		  store: store,
		  stripeRows : true, // 斑马线
		  sm:sm,
		  cm : cm,
		  tbar : [{
				text : '详细信息',
				iconCls : 'detailIconCss',
				handler : function() {
					var selectLength = listPanel
					.getSelectionModel()
					.getSelections().length;

					var selectRe = listPanel
							.getSelectionModel()
							.getSelections()[0];

					if (selectLength != 1) {
						Ext.Msg.alert("提示", "请选择一条记录!");
					}else {
						document.getElementById('ActiIdStr').value = selectRe.data.mktActiId;
						document.getElementById('exeStr').value = selectRe.data.executorId;
						showInfo();
					}
				}
			}],
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