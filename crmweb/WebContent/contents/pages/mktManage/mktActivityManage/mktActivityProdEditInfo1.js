  	var pid='';//定义productId传递参数
  	var _SOURCE_CUST=true;
	if(__aimCustSource.substring(0, 1)=='1'){
		_SOURCE_CUST=false;
	};
	var _SOURCE_GROUP=true;
	if(__aimCustSource.substring(1, 2)=='1'){
		_SOURCE_GROUP=false;
	};
	var _SOURCE_PORD=true;
	if(__aimCustSource.substring(2, 3)=='1'){
		_SOURCE_PORD=false;
	};
	var _IS_SAVE_CUST = false;
	// 审批人信息
			var prodRelateCustomerRecord = Ext.data.Record.create( [ {
				name : 'custName',
				mapping : 'custName'
			}, {
				name : 'custId',
				mapping : 'custId'
			} ]);
			
			//查询待删除产品中，有多少目标客户在关联客户表中的store
			var prodRelateCustomerStore = new Ext.data.Store( {
				restful : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/addmarketprodaction!queryRelateCustomer.json'
				}),
				reader : new Ext.data.JsonReader( {
					successProperty : 'success',
		
					messageProperty : 'message',
					root : 'data',
					totalProperty : 'count'
				}, prodRelateCustomerRecord)
			});
	
	//产品放大镜
	var prodEditCode = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '目标营销产品',
		name : 'productName',
		hiddenName : 'aimProd',
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		var prodCode = productContrastForm.form.findField('aimProd').getValue();
		productContrastForm.form.findField('prodCode').setValue(prodCode);
   	 	if(!_SOURCE_PORD){
   	 	Ext.MessageBox.confirm('提示','是否将您所选择的产品对应的目标客户作为该营销活动的关联客户？',
				 function(buttonId){
			if(buttonId.toLowerCase() == "yes"){
				_IS_SAVE_CUST=true;
			}if(buttonId.toLowerCase() == "no"){
				_IS_SAVE_CUST=false;
			}
				}); 	
   	 	}}
		});  

    var productContrastRecord = Ext.data.Record.create(
    		[
    		 {name:'aimProdId',mapping:'AIM_PROD_ID'},
    		 {name:'createUser',mapping:'CREATE_USER'},
    		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
    		 {name:'createDate',mapping:'CREATE_DATE'},
    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'},
    		 {name:'productId',mapping:'PRODUCT_ID'},
    		 {name:'productName',mapping:'PRODUCT_NAME'}
    		 ]
    );
    var productContrastReader = new Ext.data.JsonReader(//读取jsonReader
    		{
    			successProperty : 'success',
    			idProperty : 'ID',
    			totalProperty : 'json.count',
    			root:'json.data'
    		},productContrastRecord
	);
	var productContrastStore = new Ext.data.Store({//产品对照关系store
	        restful : true, 
	        proxy : new Ext.data.HttpProxy({ 
	        	url:basepath+'/mktactivityrelateinfoaction.json',
	        	method:'get'
	        }),
			reader:productContrastReader
			
	});
	
	// 每页显示条数下拉选择框
	var prod_pagesize_combo = new Ext.form.ComboBox({
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
		value : '100',
		resizable : true,
		width : 85
	});

	productContrastStore.reload({
		params : {
			start : 0,
			limit : parseInt(prod_pagesize_combo.getValue())
		}
	});
	// 改变每页显示条数reload数据
	prod_pagesize_combo.on("select", function(comboBox) {
		prod_bbar.pageSize = parseInt(prod_pagesize_combo.getValue()),
		productContrastStore.reload({
			params : {
				start : 0,
				limit : parseInt(prod_pagesize_combo.getValue())
			}
		});
	});

	var prod_bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
			pageSize : parseInt(prod_pagesize_combo.getValue()),
			store : productContrastStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', prod_pagesize_combo ]
	});
	 var prod_sm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	 var prod_rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	    });
	 var productContrastColumns = new Ext.grid.ColumnModel(
				{
					columns:[prod_rownum,prod_sm,
					{ header:'aimProdId',dataIndex:'aimProdId',sortable:true,hidden:true},
					{ header:'产品编号',dataIndex:'productId',sortable:true,hidden:true},
					{ header:'产品名称',dataIndex:'productName',id:'relType',sortable:true,width:150},
					{ header:'创建人',dataIndex:'createUserName',sortable:true,width:160},
					{ header:'创建日期',dataIndex:'createDate',width:160,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/
	 
	 
	 
	 var sm2 = new Ext.grid.CheckboxSelectionModel();
		var rownum2 = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		var fields2 = [{
			name : 'c1'
		}, {
			name : 'c2'
		}, {
			name : 'c3'
		}, {
			name : 'c4'
		}];
		
		var Columns2 = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                             sm2,
	                                            {header :'产品编号',dataIndex:'c1',width:150,sortable : true},
	                                            {header:'产品名称',dataIndex:'c2',width:150,sortable : true},
	                                            {header:'产品分类',dataIndex:'c3',width:150,sortable : true},
	                                            {header:'适合客户群',dataIndex:'c4',width:150,sortable : true}
	                                            ]);
		
		var data2= [['400','单位活期存款','普通存款','普通客户群,商圈客户群']
		,['403','通知存款','存款类业务','商圈客户群'],
		['404','协议存款','存款类业务','链式客户群,商圈客户群'],
		['406','协定户存款','协议存款','商圈客户群,族群'],
		['407','信用证保证金存款','保证金存款','商圈客户群']];
				

				 var store2 = new Ext.data.ArrayStore({
					   fields : fields2,
						data : data2
					});
	    var grid = new Ext.grid.GridPanel({
			region : 'center',
			frame : true,
			layout:'fit',
			store : store2,
			stripeRows : true,
			cm : Columns2,
			sm:sm2,
			buttonAlign : "center",
			buttons:[{
				text : '确定',
				handler : function() {
					var selectLength = grid.getSelectionModel().getSelections().length;
					if (selectLength < 1) {
						Ext.Msg.alert("提示", "请选择至少一条记录!");
					}else{
						var idstr = '';
						var namestr = '';
						for(var i = 0; i<selectLength;i++)
						{
							selectRe = grid.getSelectionModel().getSelections()[i];
							idstr 	+= ','+ selectRe.data.c1;
							namestr 	+= ','+ selectRe.data.c2;
						};
						idstr 	= idstr.substring(1, idstr.length);
						namestr 	= namestr.substring(1, namestr.length);
						
						//判断所选产品中是否存在重复数据
			        	 var countNum = 0;
			        	 var countName = '';
			        	 debugger;
			        	 var selectStr = productContrastStore.data.items;//当前关联产品数据
			        	 for(var i = 0;i<productContrastStore.data.length;i++)
			        	 {
			        		 for(var j = 0;j<selectLength;j++){
			        			 selectRe = grid.getSelectionModel().getSelections()[j];
			        			 if(selectRe.data.c1 ==selectStr[i].data.productId){
			        				 countNum++;
			        				 countName=countName+',<br>'+selectRe.data.c1+':'+selectRe.data.c2;
			        			 }
			        		 }
			        	 }
			        	 if(countNum>0){
			        		 Ext.MessageBox.confirm('提示','您所选产品中有'+countNum+'种产品已经建立关联关系，分别为：'+countName+'。<br>保存结果将不包含该类产品，<br>确定执行此操作吗?',
			        				 function(buttonId){
			     				if(buttonId.toLowerCase() == "no"){
			     					 return false;
			     					} 
			     					Ext.Msg.wait('正在保存，请稍后......','系统提示');
			        	 Ext.Ajax.request({
								url : basepath + '/addmarketprodaction!saveData.json?sign=prod',
								params : {
								'prodIdStr':idstr,
								'prodNameStr':namestr,
								'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
								'_SOURCE_PORD':_SOURCE_PORD,
								'_IS_SAVE_CUST':_IS_SAVE_CUST
								},
								method : 'POST',
								form : productContrastForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function(response) {
									Ext.Msg.alert('提示', '操作成功!');
									productContrastStore.reload( {
	                                     params : {
	                                         start : 0,
	                                         limit : prod_bbar.pageSize
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
								}
							});
			        		 });
			        		 }else if(countNum==0){
			        		 	Ext.Msg.wait('正在保存，请稍后......','系统提示');
			        			 Ext.Ajax.request({
										url : basepath + '/addmarketprodaction!saveData.json?sign=prod',
										params : {
										'prodIdStr':idstr,
										'prodNameStr':namestr,
										'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
										'_SOURCE_PORD':_SOURCE_PORD,
										'_IS_SAVE_CUST':_IS_SAVE_CUST
										},
										method : 'POST',
										form : productContrastForm.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function(response) {
											Ext.Msg.alert('提示', '操作成功!');
											productContrastStore.reload( {
			                                     params : {
			                                         start : 0,
			                                         limit : prod_bbar.pageSize
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
										}
									});	 
			        		 }
			        	 adviseProductWind.hide();
			         }
						
						}
					}
			]
		});

	    
	    var adviseProductWind = new Ext.Window({
	    	title : '推荐产品',
			plain : true,
			layout : 'fit',
			width : 700,
			height : 450,
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
							items : [ grid ]
						}
				]
			} ]
	    	
	    });

	 var productContrastGrid = new Ext.grid.EditorGridPanel({			
			store:productContrastStore, 
			frame:true,
			height : 200,
//			width : 200,
			cm:productContrastColumns,
			region:'center',
			sm:prod_sm,
			tbar:[
				{ text:'从推荐产品选择',
					iconCls:'addIconCss',
				   handler:function(){
					  	adviseProductWind.show();
					  	adviseProductWind.setTitle('产品对照关系新增');
					  	productContrastStore.reload();
				  }
				  },
			      { text:'从产品库选择',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	addProductContrastWind.show();
			    	  	addProductContrastWind.setTitle('产品对照关系新增');
			    	  	productContrastForm.getForm().getEl().dom.reset();
			    	  	productContrastStore.reload();
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = productContrastGrid.getSelectionModel().getSelections().length;
						 var selectRe;
						 var tempId;
						 var idStr = '';
						 var prodStr = '';
						 var custIdStr = '';
						 var custNameStr = '';
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
							if(selectLength>0){
							idStr = productContrastGrid.getSelectionModel().getSelections()[0].data.aimProdId;
							prodStr = productContrastGrid.getSelectionModel().getSelections()[0].data.productId;
							}
							for(var i = 1; i<selectLength;i++)
							{
								selectRe = productContrastGrid.getSelectionModel().getSelections()[i];
								idStr 	+= ','+ selectRe.data.aimProdId;
								prodStr += ','+ selectRe.data.productId
							};
//							prodRelateCustomerStore.proxy.setUrl(basepath+ '/queryRelateCustomer!queryRelateCustomer.json');
							prodRelateCustomerStore.on('beforeload', function() {
							this.baseParams = {
								'idStr' : prodStr,
								'mktActiId' : editBasePlanForm.form.findField('mktActiId').getValue()
							};
						});
								prodRelateCustomerStore.load({
								 callback : function() {
								 title_count = prodRelateCustomerStore.getCount();
								if(title_count>0){
									title = prodRelateCustomerStore.getAt(0);
								custNameStr = title.json.custId+':'+title.json.custName;
								}
								for ( var b = 1; b < title_count; b++) {
										title = prodRelateCustomerStore.getAt(b);
										custNameStr=custNameStr+',<br>'+title.json.custId+':'+title.json.custName;
									}
									
//									Ext.MessageBox.confirm('提示','您要删除的产品涉及【'+prodRelateCustomerStore.getCount()+'】条关联客户信息，分别为:<br>'+custNameStr+'。<br>删除该产品，系统将同步删除改产品涉及的目标客户信息，确定删除吗?',function(buttonId){
									Ext.MessageBox.confirm('提示','您要删除的产品涉及【'+prodRelateCustomerStore.getCount()+'】条关联客户信息。<br>删除该产品，系统将同步删除该产品涉及的目标客户信息，确定删除吗？',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return;
									} 
									Ext.Ajax.request({
												url : basepath
												+ '/addmarketprodaction!batchDestroy.json',
												params : {
													'idStr' : idStr,
													'prodStr':prodStr,
													'mktActiId' : editBasePlanForm.form.findField('mktActiId').getValue(),
													'delSgin':'prod',
													'_SOURCE_PORD':_SOURCE_PORD,
													'delSgin':'prod',
													'_SOURCE_PORD':_SOURCE_PORD
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													productContrastStore.reload();
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
													if(resultArray == 403) {
												           Ext.Msg.alert('提示', response.responseText);
												  } else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
													productContrastStore.reload();
												  }}
											});
								});
            					}
								});
							
								
						}}
			      }],
			      bbar:prod_bbar,
			      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	 			  },
	 			  loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			  }
	 });
	 
	 /****************************************产品对照关系信息*************************************************/
	 var productContrastForm = new Ext.form.FormPanel({
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
	    		 },prodEditCode]
	    	 }]
		 }]
	 });
	var addProductContrastWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[productContrastForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 if (!productContrastForm.getForm().isValid()) {
		        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		        		 return false;
		        	 }
		        	 //判断所选产品中是否存在重复数据
		        	 var countNum = 0;
		        	 var countName = '';
		        	 var selectStr = productContrastStore.data.items;//当前关联产品数据
		        	 var selectProdId = productContrastForm.form.findField('prodCode').getValue().split(',');//所筛选的关联产品ID
		        	 var selectProdName = productContrastForm.form.findField('productName').getValue().split(',');//所筛选的关联产品名称
		        	 for(var i = 0;i<productContrastStore.data.length;i++)
		        	 {
		        		 for(var j = 0;j<selectProdId.length;j++){
		        			 if(selectProdId[j]==selectStr[i].data.productId){
		        				 countNum++;
		        				 countName=countName+',<br>'+selectProdId[j]+':'+selectProdName[j];
		        			 }
		        		 }
		        	 }
		        	 if(countNum>0){
		        		 Ext.MessageBox.confirm('提示','您所选产品中有'+countNum+'种产品已经建立关联关系，分别为：'+countName+'。<br>保存结果将不包含该类产品，<br>确定执行此操作吗?',
		        				 function(buttonId){
		     				if(buttonId.toLowerCase() == "no"){
		     					 return false;
		     					} 
		     					Ext.Msg.wait('正在保存，请稍后......','系统提示');
		        	 Ext.Ajax.request({
							url : basepath + '/addmarketprodaction!saveData.json?sign=prod',
							params : {
							'prodIdStr':productContrastForm.form.findField('prodCode').getValue(),
							'prodNameStr':productContrastForm.form.findField('productName').getValue(),
							'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
							'_SOURCE_PORD':_SOURCE_PORD,
							'_IS_SAVE_CUST':_IS_SAVE_CUST
							},
							method : 'POST',
							form : productContrastForm.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function(response) {
								Ext.Msg.alert('提示', '操作成功!');
								productContrastStore.reload( {
                                     params : {
                                         start : 0,
                                         limit : prod_bbar.pageSize
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
							}
						});
		        		 });
		        		 }else if(countNum==0){
		        		 	Ext.Msg.wait('正在保存，请稍后......','系统提示');
		        			 Ext.Ajax.request({
									url : basepath + '/addmarketprodaction!saveData.json?sign=prod',
									params : {
									'prodIdStr':productContrastForm.form.findField('prodCode').getValue(),
									'prodNameStr':productContrastForm.form.findField('productName').getValue(),
									'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
									'_SOURCE_PORD':_SOURCE_PORD,
									'_IS_SAVE_CUST':_IS_SAVE_CUST
									},
									method : 'POST',
									form : productContrastForm.getForm().id,
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function(response) {
										Ext.Msg.alert('提示', '操作成功!');
										productContrastStore.reload( {
		                                     params : {
		                                         start : 0,
		                                         limit : prod_bbar.pageSize
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
									}
								});	 
		        		 }
		        	 addProductContrastWind.hide();
		         }
		         },
		         {
		        	 text:'重置',
		        	 handler:function(){
		        	 	productContrastForm.getForm().reset();
		         	}
		         }
		        ]
	});