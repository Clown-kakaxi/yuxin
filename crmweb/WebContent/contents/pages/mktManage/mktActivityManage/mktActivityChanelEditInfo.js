  	var pid='';//定义custId传递参数
  	
    // 德阳POC DEMO 使用  yuyz
	var modelTypeStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		sortInfo: {
		    field: 'key',
		    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=MODEL_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
  	
 // 修改基本信息展示的from
	var editModelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
//		id:'allForms',
		labelAlign : 'right',
		//region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'id',
					xtype : 'textfield',
					fieldLabel : 'ID',
					hidden:true
				},{
					name : 'modelId',
					xtype : 'textfield',
					fieldLabel : '*模板编号',
					value : '6',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelName',
					xtype : 'textfield',
					fieldLabel : '*模板名称',
					value : '活动宣传模板',
					maxLength:100,
					allowBlank : false,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					store : modelTypeStore,
					xtype : 'combo',
					resizable : true,
					fieldLabel : '*模板类型',
					name : 'modelType',
					hiddenName : 'modelType',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					value :'2',
					typeAhead : true,
					forceSelection : true,
					allowBlank : false,
					triggerAction : 'all',
					emptyText : '请选择',
//					selectOnFocus : true,
					width : '100',
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelTitle',
					id : 'modelTitleEdit',
					xtype : 'textfield',
					fieldLabel : '模板标题',
					value : '大型宣讲会',
					maxLength:200,
					anchor : '90%',
					hidden : true
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ {
					name : 'modelContent',
					xtype : 'textarea',
					fieldLabel : '*模板内容',
					value : '尊敬的 XX先生：我行将进行大型宣讲会，有多种产品供选购，当场有各种优惠，日期XXXX年XX月XX日 【德阳银行】',
					maxLength:512,
					allowBlank : false,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [
			 {
				text : '取  消',
				handler : function() {
					editModelWindow.hide();
				}
			} ]
	});
	
	// 修改渠道的from
	var editModelPanel = new Ext.Panel({
		labelWidth : 150,
		height : 300,
		layout : 'fit',
		buttonAlign : "center",
		items : [ editModelForm ]
	});
  	
 // 定义修改窗口
	var editModelWindow = new Ext.Window({
		title : '营销模板修改',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 350,
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
		items : [ editModelPanel ]
	});
	
  	
  	
	function editModelInit() {
		editModelWindow.show();
	}
  	
  	var boxstore8 = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=P_CUST_GRADE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
  	
	var editChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		labelAlign : 'right',
		id : 'editAllForms',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelName',
					xtype : 'textfield',
					fieldLabel : '*渠道名称',
					allowBlank : false,
					blankText : '此项不能为空',
					value : '邮件',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelId',
					xtype : 'textfield',
					fieldLabel : '*渠道编号',
					value : '4576',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					hiddenName : 'channelFitCustLevel',
					fieldLabel : '客户级别',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore8,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					name : 'channelId1',
					hiddenName : 'channelId1',
					xtype : 'combo',
					fieldLabel : '营销模板名称',
					store : new Ext.data.ArrayStore({
						fields : [ 'value', 'text' ],
						data : [ [ 100, '产品推荐模板' ], [ 200, '活动宣传模板' ]]
					}),
					value : 200,
					valueField : 'value',
					displayField : 'text',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : 1,
				items : [ {
					name : 'channelContent',
					xtype : 'textarea',
					fieldLabel : '渠道描述',
					maxLength:1000,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [
			{
				text : '关  闭',
				handler : function() {
					editChannelWindow.hide();
				}
			} ]
	});
	
	// 修改渠道的from
	var editChannelPanel = new Ext.Panel({
		labelWidth : 150,
		height : 250,
		layout : 'fit',
		buttonAlign : "center",
		items : [ editChannelForm ]
	});
	
	// 定义修改窗口
	var editChannelWindow = new Ext.Window({
		title : '渠道修改',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 250,
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
		items : [ editChannelPanel ]
	});
	
  	// 展示模板修改窗口
	function editChannelInit() {
		editChannelWindow.show();
	}
	
	//END
	
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
    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'},
    		 {name:'templetName',mapping:'TEMPLETNAME'}
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
	
//	// 每页显示条数下拉选择框
//	var pagesize_combo = new Ext.form.ComboBox({
//		name : 'pagesize',
//		triggerAction : 'all',
//		mode : 'local',
//		store : new Ext.data.ArrayStore({
//			fields : [ 'value', 'text' ],
//			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
//			         [ 50, '50条/页' ],[ 100, '100条/页' ]  ]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		value : '100',
//		resizable : true,
//		width : 85
//	});
//
//	chanelContrastStore.reload({
//		params : {
//			start : 0,
//			limit : parseInt(pagesize_combo.getValue())
//		}
//	});
//	// 改变每页显示条数reload数据
//	pagesize_combo.on("select", function(comboBox) {
//		bbar.pageSize = parseInt(pagesize_combo.getValue()),
//		chanelContrastStore.reload({
//			params : {
//				start : 0,
//				limit : parseInt(pagesize_combo.getValue())
//			}
//		});
//	});
//
//	var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
//			pageSize : parseInt(pagesize_combo.getValue()),
//			store : chanelContrastStore,
//			displayInfo : true,
//			displayMsg : '显示{0}条到{1}条,共{2}条',
//			emptyMsg : "没有符合条件的记录",
//			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
//	});
	
		 //*********************************
	// 每页显示条数下拉选择框
	var chanel_pagesize_combo = new Ext.form.ComboBox({
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
		value : '100',
		editable : false,
		width : 85
	});
	var chanel_bbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(chanel_pagesize_combo.getValue()),
		store : chanelContrastStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', chanel_pagesize_combo]
	});
	
	chanel_pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  chanel_bbar.pageSize = parseInt(chanel_pagesize_combo.getValue()),
		  chanelContrastStore.reload({
			  params : {
			  start : 0,
			  limit : parseInt(chanel_pagesize_combo.getValue())
		  }
		  });
	  });
 //********************************

	var chanel_sm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	var chanel_rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	    });
	 var chanelContrastColumns = new Ext.grid.ColumnModel(
				{
					columns:[chanel_rownum,chanel_sm,
					{ header:'ID',dataIndex:'actiChannelId',sortable:true,hidden:true},
					{ header:'营销活动编号',dataIndex:'mktActiId',sortable:true,hidden:true},
					{ header:'渠道编号',dataIndex:'productId',sortable:true,width:150},
					{ header:'渠道名称',dataIndex:'productName',sortable:true,width:160,renderer:function(val){
                  	  return '<span><u><A onclick=editChannelInit()>' + val + '<A></u></span>';
                    }},
					{ header:'模板名称',dataIndex:'templetName',sortable:true,width:160,renderer:function(val){
                  	  return '<span><u><A onclick=editModelInit() >' + val + '<A></u></span>';
                    }},
					{ header:'创建人编号',dataIndex:'createUser',width:160,sortable:true,hidden:true},
					{ header:'创建人',dataIndex:'createUserName',width:160,sortable:true},
					{ header:'创建时间',dataIndex:'createDate',width:160,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/
	 var sm = new Ext.grid.CheckboxSelectionModel();
	 var chanelContrastGrid = new Ext.grid.EditorGridPanel({			
			store:chanelContrastStore, 
			frame:true,
			height : 200,
//			width : 200,
			cm:chanelContrastColumns,
			region:'center',
			sm:chanel_sm,
			tbar:[
			      { text:'新增',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	chanelContrastForm.form.reset();
			    	  	addchanelContrastWind.show();
			    	  	addchanelContrastWind.setTitle('关联渠道信息新增');
			    	  	chanelContrastForm.getForm().getEl().dom.reset();
			    	  	chanelContrastStore.reload();
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = chanelContrastGrid.getSelectionModel().getSelections().length;
						 var selectRe;
						 var tempId;
						 var idStr = '';
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
							for(var i = 0; i<selectLength;i++)
							{
								selectRe = chanelContrastGrid.getSelectionModel().getSelections()[i];
								tempId = selectRe.data.actiChannelId;
								idStr += tempId;
								if( i != selectLength-1)
									idStr += ',';
							}
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return;
									} 
									Ext.Ajax.request({
												url : basepath
												+ '/addmarketprodaction!batchDestroy.json',
												params : {
													'idStr' : idStr,
													'mktActiId' : editBasePlanForm.form.findField('mktActiId').getValue(),
													'delSgin':'chanel'
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													chanelContrastStore.reload();
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
													if(resultArray == 403) {
												           Ext.Msg.alert('提示', response.responseText);
												  } else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
													chanelContrastStore.reload();
												  }}
											});
								});
						}}
			      }],
			      bbar:chanel_bbar,
			      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	 			  },
	 			  loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			  }
	 });
	 
	 /****************************************产品对照关系信息*************************************************/
	 var chanelContrastForm = new Ext.form.FormPanel({
			region:'center',
			margins: '0 0 0 0',
			autoScroll:true,
			labelWidth : 120, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			height : 120,
			items : [{
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .8,
					layout : 'form',
					labelWidth : 120, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [new Ext.ux.form.LovCombo({
				    	fieldLabel : '<font color=red>*</font>渠道名称',
				    	name : 'chanelType',
				    	labelStyle: 'text-align:right;',
				    	displayField : 'CHANNEL_NAME',
				    	valueField : 'CHANNEL_ID',
				    	hideOnSelect:false,
				    	store :chanelTypeStore,
				    	triggerAction:'all',
				    	mode:'local',
				    	allowBlank:false,
				    	editable:true,
				    	anchor : '95%'
				    })/*,{
						 name : 'relDesc',
						 labelStyle: 'text-align:right;',
						 xtype : 'textarea',
						 fieldLabel : '描述',
						 anchor : '95%'
					 }*/
				    ]}
				]
			}]
		});
	var addchanelContrastWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[chanelContrastForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
			        	 if (!chanelContrastForm.getForm().isValid()) {
			        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
			        		 return false;
			        	 }
			        	//判断所选渠道中是否存在重复数据
			        	 var countNum = 0;
			        	 var countName = '';
			        	 var selectStr = chanelContrastStore.data.items;//当前关联产品数据
			        	 var selectChanelId = chanelContrastForm.form.findField('chanelType').getValue().split(',');//所筛选的关联产品ID
//			        	 var selectChanelName = productContrastForm.form.findField('productName').getValue().split(',');//所筛选的关联产品名称
			        	 for(var i = 0;i<chanelContrastStore.data.length;i++)
			        	 {
			        		 for(var j = 0;j<selectChanelId.length;j++){
			        			 if(selectChanelId[j]==selectStr[i].data.productId){
			        				 countNum++;
			        				 countName=countName+',<br>'+selectChanelId[j];
			        			 }
			        		 }
			        	 }
			        	 if(countNum>0){
			        		 Ext.MessageBox.confirm('提示','您所选渠道中有【 '+countNum+' 】种渠道方式已经建立关联关系，分别为：'+countName+'。<br>保存结果将不包含该类渠道，<br>确定执行此操作吗?',
			        				 function(buttonId){
			     				if(buttonId.toLowerCase() == "no"){
			     					 return false;
			     					} 
			        	 Ext.Ajax.request({
								url : basepath + '/addmarketprodaction!saveData.json?sign=chanel',
								params : {
								'chanelId':chanelContrastForm.form.findField('chanelType').getValue(),
								'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue()
								},
								method : 'POST',
								form : chanelContrastForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									chanelContrastStore.reload( {
	                                     params : {
	                                         start : 0,
	                                         limit : chanel_bbar.pageSize
	                                     }
	                                 });
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
								       if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
								  } else{

									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								}
//									store.reload();
								}
							});
			        		 });}else if(countNum==0){
			        			 Ext.Ajax.request({
										url : basepath + '/addmarketprodaction!saveData.json?sign=chanel',
										params : {
										'chanelId':chanelContrastForm.form.findField('chanelType').getValue(),
										'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue()
										},
										method : 'POST',
										form : chanelContrastForm.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											Ext.Msg.alert('提示', '操作成功!');
											chanelContrastStore.reload( {
			                                     params : {
			                                         start : 0,
			                                         limit : chanel_bbar.pageSize
			                                     }
			                                 });
										},
										failure : function(response) {
											var resultArray = Ext.util.JSON.decode(response.status);
										       if(resultArray == 403) {
										           Ext.Msg.alert('提示', response.responseText);
										  } else{

											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
										}
//											store.reload();
										}
									});	 
			        		 }
			        	 addchanelContrastWind.hide();
			         }
		         },
		         {
		        	 text:'重置',
		        	 handler:function(){
		        	 	chanelContrastForm.getForm().reset();
		         	}
		         }
		        ]
	});
	
	 /****************************修改方法*************************************/

	var update = function() {
		debugger;
		var record = chanelContrastGrid.getSelectionModel().getSelected();
		if(!record){
			Ext.MessageBox.alert('提示', '请选择要修改的一列！');
		}
		else{
			addchanelContrastWind.show();
			addchanelContrastWind.setTitle('产品对照关系修改');
			chanelContrastForm.getForm().loadRecord(record);
			var selectedRow1 = chanelContrastGrid.selModel.getSelections();
			chanelId = selectedRow1[0].data.chanelId;
			chanelContrastStore.load({
				params : {
					'productId':chanelId,
					'querysign':'chanelomer'
				}
			});
		}
	};

