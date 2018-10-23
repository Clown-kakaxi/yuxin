var canSee = false;
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

	var mktActiAssignUser = new Com.yucheng.crm.common.OrgUserManage({ 
		xtype:'userchoose',
		fieldLabel : '执行人',
		labelStyle: 'text-align:right;',
		name : 'MGR_NAME',
		allowBlank:false,
		hiddenName:'PUBLISHER',
		searchRoleType:('127,47'),  //指定查询角色属性
		searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
		singleSelect:true,
		anchor : '90%'
		});

	var store = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/MktMyActiListAction.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'myActiId', mapping: 'MY_ACTI_ID'},
		      {name: 'activityQuery', mapping: 'MY_ACTI_NAME'},
		      {name: 'mktActiId', mapping: 'MKT_ACTI_ID'},
		      
		      {name: 'mktActiName', mapping: 'MKT_ACTI_NAME'},
		      {name: 'MKT_ACTI_TYPE_ORA'},
		      {name: 'MKT_ACTI_STAT_ORA'},
		      {name: 'MKT_ACTI_AIM', mapping: 'MKT_ACTI_AIM'},
		      {name: 'MKT_ACTI_CONT', mapping: 'MKT_ACTI_CONT'},
		      {name: 'PSTART_DATE', mapping: 'PSTART_DATE'},
		      {name: 'PEND_DATE', mapping: 'PEND_DATE'},
		      {name: 'MKT_ACTI_COST', mapping: 'MKT_ACTI_COST'},
		      {name: 'AEND_DATE', mapping: 'AEND_DATE'},
		      {name: 'ACTI_REMARK', mapping: 'ACTI_REMARK'},
		      {name: 'ASTART_DATE', mapping: 'ASTART_DATE'},
		      {name: 'ACTI_CUST_DESC', mapping: 'ACTI_CUST_DESC'},
		      
		      {name: 'custId', mapping: 'CUST_ID'},
		      {name: 'custName', mapping: 'CUST_NAME'},
		      {name: 'executorId', mapping: 'EXECUTOR_ID'},
		      {name: 'executorName', mapping: 'EXECUTOR_NAME'},
		      {name: 'custCategory', mapping: 'CUST_CATEGORY'},
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
	  
	  var cm = new Ext.grid.ColumnModel([rownum,sm,	// 定义列模型
	                                     {header : '活动编号', dataIndex : 'myActiId',sortable : true,width : 120,hidden : true}, 
	                                     {header : '活动名称', dataIndex : 'activityQuery',sortable : true,width : 120},
	                                     {header : '所属主营销活动编号', dataIndex : 'mktActiId',hideable:false,sortable : true,width : 120,hidden : true},
	                                     {header : '所属主营销活动名称', dataIndex : 'mktActiName',sortable : true,width : 120},
	                                     {header : '客户Id', dataIndex : 'custId',hideable:false,sortable : true,width : 120,hidden : true}, 
	                                     {header : '客户名称', dataIndex : 'custName',sortable : true,width : 120}, 
	                                     {header : '活动执行人员编号', dataIndex : 'executorId',sortable : true,width : 120}, 
	                                     {header : '活动执行人名称', dataIndex : 'executorName',sortable : true,width : 120}, 
	                                     {header : '进展阶段', dataIndex : 'progressStage',hideable:false,sortable : true,width : 120,hidden : true},
	                                     {header : '客户类别', dataIndex : 'custCategory',hideable:false,sortable : true,width : 120,hidden : true},
	                                     {header : '客户类型', dataIndex : 'custType',hideable:false,sortable : true,width : 120,hidden : true},
	                                     {header : '联系人', dataIndex : 'custContactName',sortable : true,width : 120,hidden : true},
	                                     {header : '进展阶段', dataIndex : 'PROGRESS_STAGE_ORA',sortable : true,width : 120}, 
	                                     {header : '是否已创建商机', dataIndex : 'isCreChance',sortable : true,width : 120}, 
	                                     {header : '创建人Id', dataIndex : 'createUser',hideable:false,sortable : true,width : 120,hidden : true},
	                                     {header : '创建人', dataIndex : 'createUserName',sortable : true,width : 120},
	                                     {header : '活动类型', dataIndex : 'MKT_ACTI_TYPE_ORA',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '活动状态', dataIndex : 'MKT_ACTI_STAT_ORA',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '活动目的', dataIndex : 'MKT_ACTI_AIM',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '活动内容', dataIndex : 'MKT_ACTI_CONT',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '计划开始时间', dataIndex : 'PSTART_DATE',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '计划结束时间', dataIndex : 'PEND_DATE',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '费用预算', dataIndex : 'MKT_ACTI_COST',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '实际结束日期', dataIndex : 'AEND_DATE',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '实际开始时间', dataIndex : 'ASTART_DATE',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '备注', dataIndex : 'ACTI_REMARK',hideable:false,hidden:true,sortable : true,width : 120},
	                                     {header : '涉及客户描述', dataIndex : 'ACTI_CUST_DESC',hideable:false,hidden:true,sortable : true,width : 120}
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
			columnWidth : .25,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '活动名称',
				name : 'MY_ACTI_NAME',
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [
			         new Ext.form.ComboBox({
			    			hiddenName : 'PROGRESS_STAGE',
			    			fieldLabel : '进展阶段',
			    			labelStyle: 'text-align:right;',
			    			triggerAction : 'all',
			    			store : StageStore,
			    			displayField : 'value',
			    			valueField : 'key',
			    			mode : 'local',
			    			emptyText:'请选择 ',
			    			resizable : true,
			    			anchor : '90%'
			    		})
				]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
					store : ifStore ,
					xtype : 'combo',
					resizable : true,
					name : 'IS_CRE_CHANCE',
					hiddenName : 'IS_CRE_CHANCE',
					fieldLabel : '是否已创建商机',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					anchor : '90%'
			}]
			
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '创建日期',
				format : 'Y-m-d',
				xtype : 'datefield',
				editable:false,
				name : 'CREATE_DATE',
				id : 'CREATE_DATE',
				anchor : '90%'
			}]
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
			items : [/*
					{
						region : 'north',
						title : "活动明细查询",
						height : 100,
						layout : 'fit',
						items : [ editQForm ]
					},*/
					{
						region : 'center',
						layout : 'fit',
						items : [ editGrid ]
					}
			]
		} ],
	listeners : {
		beforeshow : function(){
		editGrid.tbar.setDisplayed(canSee);
		editStore.on('beforeload', function() {
    		this.baseParams = {
    				myActiId:document.getElementById('myActiIdStr').value
    		};
    	});
		},
		hide : function(){
			store.reload();
			}
		,
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
	
	 var mktActiAssignForm = new Ext.form.FormPanel({
		 labelWidth : 80,
		 height : 200,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items : [ {
	    	 layout : 'column',
	    	 items : [ {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [{
	    			 name : 'prodCode',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 hidden:true,
	    			 fieldLabel : '产品编号'
	    		 },mktActiAssignUser]
	    	 }]
		 }]
	 });
	//营销活动分配窗口
	var mktActiAssignWind = new Ext.Window({//展示详情的window
		title:'营销活动执行人查询',
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[mktActiAssignForm],
		buttons:[{
			text:'确定',
			handler:function(){
			if (!mktActiAssignForm.getForm().isValid()) {
       		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
       		 return false;
       	 }
			var selectStr= listPanel.getSelectionModel().getSelections();
			var tempLength = listPanel.getSelectionModel().getSelections().length;
			var myActiId = '';
			if(tempLength>0){
				myActiId=selectStr[0].data.myActiId;
			}
			for(var i = 1;i<tempLength;i++){
				myActiId = myActiId+','+selectStr[i].data.myActiId;
				}
			 Ext.Ajax.request({
					url : basepath + '/MktMyActiListAction!activityExecute.json',
					params : {
				 	'myActiId':myActiId,
					'executorId':mktActiAssignForm.form.findField('PUBLISHER').getValue(),
					'executorName':mktActiAssignForm.form.findField('MGR_NAME').getValue()
					},
					method : 'POST',
					form : mktActiAssignForm.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功!');
						mktActiAssignWind.hide();
						store.reload({
                          params : {
                              start : 0,
                              limit : bbar.pageSize
                          }
                      });
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
					       if(resultArray == 403) {
					           Ext.Msg.alert('提示', response.responseText);
					  } else{
						  mktActiAssignWind.hide();
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
					}
					}
				});
			
				}
		},{
			text:'返回',
			handler:function(){
			mktActiAssignWind.hide();
			}
			}]
	});
	
	// 展示修改窗口
	function editInit() {
		editActivityWindow.show();
	}
	// 新增商机
	function addInit() {
		busiOpportAddWindowInit();
	}
	// 营销活动详情
	function detailInit() {
		busiOpportDetailWindowInit();
	}
	
	// 活动分配
	function mktActiAssignInit() {
		mktActiAssignForm.form.reset();
		mktActiAssignWind.show();
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
							text : '活动明细',
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
									if(selectRe.data.progressStage == 1)
										canSee = true;
									else
										canSee = false;
									
										document.getElementById('myActiIdStr').value = selectRe.data.myActiId;
										document.getElementById('custNameStr').value = selectRe.data.custName;
										document.getElementById('custIdStr').value = selectRe.data.custId;
										document.getElementById('executorIdStr').value = selectRe.data.executorId;
										document.getElementById('executorNameStr').value = selectRe.data.executorName;
										editInit();
								}
							}
						}, '-', {
							text : '查看详情',
							iconCls : 'detailIconCss',
							handler : function() {
							var selectLength = listPanel.getSelectionModel().getSelections().length;
							
							var selectRe = listPanel.getSelectionModel().getSelections()[0];
							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
								return false;
							}
							myMktDetailForm.getForm().loadRecord(selectRe);
							//渠道信息store
					    	chanelContrastStore.on('beforeload', function() {
					    		this.baseParams = {
					    				mktActiId:selectRe.data.mktActiId,
					    				querysign:'chanel'
					    		};
					    	});
					    	chanelContrastStore.load();
					    	//客户-关联产品信息
							custContrastStore1.on('beforeload', function() {
								this.baseParams = {
										mktActiId:selectRe.data.mktActiId,				   //为后台提供查询的营销活动编号
										__custStr:selectRe.data.custId+','+__aimCustSource,//为后台提供客户编号和参数类型
										querysign:'prod'
								};
							});
							custContrastStore1.load();
								detailInit();
							}
						}/*, '-', {
							text : '创建商机',
							iconCls : 'addIconCss',
							handler : function() {
							var selectLength = listPanel.getSelectionModel().getSelections().length;
							
							var selectRe = listPanel.getSelectionModel().getSelections()[0];
							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
								return false;
							}
							if (selectRe.data.isCreChance=='是') {
								Ext.Msg.alert("提示", "已经创建商机，不能重复操作!");
								return false;
							}
							addBusiOpporForm.getForm().loadRecord(selectRe);
							custContrastStore.on('beforeload', function() {
								this.baseParams = {
										mktActiId:selectRe.data.mktActiId,
										querysign:'prod'
								};
							});
							custContrastStore.load();
								addInit();
							}
						}*/,'-',{
							text : '分配活动',
							iconCls : 'detailIconCss',
							handler : function() {
								var selectLength = listPanel.getSelectionModel().getSelections().length;
	
								var selectRe = listPanel.getSelectionModel().getSelections()[0];
	
								if (selectLength != 1) {
									Ext.Msg.alert("提示", "请选择一条记录!");
								}else {
									mktActiAssignInit();	
								}
							}
						}],
		  bbar : bbar,
		  viewConfig : {},
		  loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  }
	  });
	 
	  addBusiOpporWindow.addListener('hide',function(){
			store.reload();
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
						title : "我的营销活动查询",
						height : 100,
						layout : 'fit',
						items : [ searchPanel ]
					},{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [ listPanel ]
					}
			]
		} ]
	});
	
});