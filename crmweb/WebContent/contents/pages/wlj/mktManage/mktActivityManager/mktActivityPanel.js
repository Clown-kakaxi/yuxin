
	var ifGroup = false;//是否从客户群处创建
	var ifProd = false;//是否从适合产品处创建
	var groupId = '';
	var prodId = '';
	var prodName = '';
	var custId = '' ;
	var custName = '';
	
	//营销方式
	var mactiWayeStore = new Ext.data.Store( {
        restful : true,
        sortInfo : {
            field : 'key',
            direction : 'ASC'
        },
        autoLoad : true,
        proxy : new Ext.data.HttpProxy( {
            url : basepath + '/lookup.json?name=MKT_WAY'
        }),
        reader : new Ext.data.JsonReader( {
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
	//营销活动类型
	var mactiTypeStore = new Ext.data.Store( {
        restful : true,
        sortInfo : {
            field : 'key',
            direction : 'ASC'
        },
        autoLoad : true,
        proxy : new Ext.data.HttpProxy( {
            url : basepath + '/lookup.json?name=ACTI_TYPE'
        }),
        reader : new Ext.data.JsonReader( {
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
	   //营销活动状态
	    var mactiStatusStore = new Ext.data.Store( {
	        restful : true,
	        sortInfo : {
	            field : 'key',
	            direction : 'ASC'
	        },
	        autoLoad : true,
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=MACTI_STATUS'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    mactiStatusStore.load();
	//产品放大镜
	var prodAddCode = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '目标营销产品',
		name : 'productName',
		hiddenName : 'aimProd',
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		var prodCode = addActivityProdForm.form.findField('aimProd').getValue();
		addActivityProdForm.form.findField('prodCode').setValue(prodCode);
	}
		});
	var search_cust_add = new Com.yucheng.bcrm.common.CustomerQueryField({ 
		fieldLabel : '目标客户', 
		labelStyle: 'text-align:right;',
		name : 'custNameStr',
		custtype :'2',//客户类型：  2：对私, 1:对公,  不设默认全部
	    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
	    singleSelected:false,//单选复选标志
		editable : false,
		blankText:"请填写",
		anchor : '90%',
		hiddenName:'abcd',
		callback :function(){
		var cust_name = null;
		var linkNum = '';
	}
	});
  	var pid='';//定义productId传递参数
  	var _SOURCE_CUST=true;
	
	var _SOURCE_GROUP=true;
	var _SOURCE_PORD=true;
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

	 var productContrastGrid = new Ext.grid.EditorGridPanel({			
			store:productContrastStore, 
			frame:true,
			height : 200,
//			width : 200,
			cm:productContrastColumns,
			region:'center',
			sm:prod_sm,
			tbar:[
			      { text:'新增',
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
  	var pid='';//定义custId传递参数
	var _buttonVisible = false; 
	var _sheetVisible =false;//页签展示项是否可见
	var _layOutSet = true; 
	
	 var custSourceStore = new Ext.data.ArrayStore( {
         fields : [ 'myId', 'displayText' ],
         data : [ //[ '1', '自定义筛选' ],
                  [ '2', '客户群导入' ]]
     });
     
	 //根据目标客户来源不同，下拉框显示不同的客户来源
	 if(_SOURCE_CUST){
		 custSourceStore.data.removeAt(0);
	 }if(_SOURCE_GROUP){
		 custSourceStore.data.removeAt(1);
	 }
  	/*
	 * 灵活查询弹出窗口
	 */
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 1000,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				
				// iconCls : 'page_addIcon',
				//maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{html:' <div style="width:'+document.clientWidth+'px;height:'+document.clientHeight+'px;"><div style="position:absolute; left:0px; top:0px; " id=\'view\'></div></div>'
			}],
				buttons : [
						/*{
							text : '查询',
							handler : function() {
								var conditionStr1 =  simple.getForm().getValues(false);
								store.baseParams={
																	
										'condition':Ext.encode(conditionStr1)								
						};
								store.reload({
									  params : {
		                                   start : 0,
		                                   limit : bbar.pageSize
									  
									  }}); 
								addRoleWindow.hide();
								 Ext.getCmp('exportbatten').formPanel=simple;
								//Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							handler : function() {
								simple.getForm().reset();
							}
						},*/ {
							text : '关闭',
							handler : function() {
								addRoleWindow.hide();
								//document.getElementById('view').innerHTML = "";
							}
						} ]
			});
		addRoleWindow.on('hide', function() {
			document.getElementById('view').innerHTML = "";
			addSolutionWindow.destroy();
		});
  	
  	
	var search_cust_group = new Com.yucheng.bcrm.common.CustGroup({ 
		fieldLabel : '关联客户群', 
		labelStyle: 'text-align:right;',
		name : 'custGroup',
	    singleSelected:true,//单选复选标志
		editable : false,
		blankText:"请填写",
		hidden:_SOURCE_GROUP,
		anchor : '90%',
		hiddenName:'groupStr'
	});

    var custContrastRecord = Ext.data.Record.create(
    		[
    		 {name:'aimCustId',mapping:'AIM_CUST_ID'},
    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'},
    		 {name:'custId',mapping:'CUST_ID'},
    		 {name:'createDate',mapping:'CREATE_DATE'},
    		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
    		 {name:'custName',mapping:'CUST_NAME'},
    		 {name:'aimCustSource',mapping:'AIM_CUST_SOURCE'},
    		 {name:'AIM_CUST_SOURCE_ORA'},
    		 {name:'progressStep',mapping:'PROGRESS_STEP'},
    		 {name:'PROGRESS_STEP_ORA'},
    		 {name:'mktActiStat',mapping:'MKT_ACTI_STAT'},
    		 {name:'mgrName',mapping:'MGR_NAME'},
    		 {name:'institutionName',mapping:'INSTITUTION_NAME'}
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
		value : '100',
		editable : false,
		width : 85
	});
	var cust_bbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(cust_pagesize_combo.getValue()),
		store : custContrastStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', cust_pagesize_combo]
	});
	
	cust_pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  cust_bbar.pageSize = parseInt(cust_pagesize_combo.getValue()),
		  custContrastStore.reload({
			  params : {
			  start : 0,
			  limit : parseInt(cust_pagesize_combo.getValue())
		  }
		  });
	  });
 //********************************
	
 	var cust_sm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	var cust_rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	    });
	 var custContrastColumns = new Ext.grid.ColumnModel(
				{
					columns:[ cust_rownum,cust_sm,
					{ header:'aimProdId',dataIndex:'aimProdId',sortable:true,hidden:true},
					{ header:'客户编号',dataIndex:'custId',sortable:true,hidden:true},
					{ header:'客户名称',dataIndex:'custName',sortable:true,width:150},
					{ header:'主办客户经理',dataIndex:'mgrName',sortable:true},
					{ header:'主办机构',dataIndex:'institutionName',sortable:true,width:150},
					{ header:'目标客户来源',dataIndex:'AIM_CUST_SOURCE_ORA',sortable:true,width:150},
					{ header:'进展阶段',dataIndex:'PROGRESS_STEP_ORA',sortable:true,width:150},
					{ header:'创建人',dataIndex:'createUserName',sortable:true,width:160},
					{ header:'创建日期',dataIndex:'createDate',width:160,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/
	 var custContrastGrid = new Ext.grid.EditorGridPanel({			
			store:custContrastStore, 
			frame:true,
			height : 200,
//			width : 200,
			cm:custContrastColumns,
			region:'center',
			sm:cust_sm,
			tbar:[
			      { text:'新增',
			    	  id:'__aimCustAdd',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	addcustContrastWind.show();
			    	  	addcustContrastWind.setTitle('关联客户维护');
			    	  	custContrastForm.getForm().getEl().dom.reset();
			    	  	custContrastStore.reload();
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = custContrastGrid.getSelectionModel().getSelections().length;
						 var selectRe;
						 var tempId;
						 var idStr = '';
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
							for(var i = 0; i<selectLength;i++)
							{
								selectRe = custContrastGrid.getSelectionModel().getSelections()[i];
								tempId = selectRe.data.aimCustId;
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
													'delSgin':'cust'
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													custContrastStore.reload();
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
													if(resultArray == 403) {
												           Ext.Msg.alert('提示', response.responseText);
												  } else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
													custContrastStore.reload();
												  }}
											});
								});
						}}
			      }],
			      bbar:cust_bbar,
			      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	 			  },
	 			  loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			  }
	 });
	 
	 /****************************************产品对照关系信息*************************************************/
	 var custContrastForm = new Ext.form.FormPanel({
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
	    		 items : [ {
	                    fieldLabel : '客户来源',
	                    name : 'custSource',
	                    forceSelection : true,
	                    resizable : true,
	                    xtype : 'combo',
	                    labelStyle : 'text-align:right;',
	                    triggerAction : 'all',
	                    mode : 'local',
	                    value:'2',
	                    store : custSourceStore,
	                    valueField : 'myId',
	                    displayField : 'displayText',
	                    emptyText : '请选择',
	                    anchor : '95%',
	                    listeners:{
	    			 select :function(){
	    			 if(custContrastForm.form.findField('custSource').getValue()=='2'){
	    				 custContrastForm.form.findField('custNameStr').setVisible(false);
		    			 custContrastForm.form.findField('custGroup').setVisible(true);
		    			 custContrastForm.form.findField('custNameStr').reset();
		    			 custContrastForm.form.findField('abcd').reset();
	    			 }
	    			
	    		 }
	    		 }
	                } ]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
//	    		 hidden:_SOURCE_GROUP,
	    		 items : [/*search_cust_edit*/{
						xtype : 'textarea',
						name : 'custNameStr',
						fieldLabel : '客户名称',
						hidden:true,
						anchor : '90%'
					},{
						xtype : 'textfield',
						name : 'abcd',
						hidden:true,
						fieldLabel : '客户编号1',
						anchor : '90%'
					},search_cust_group]
	    	 }]
		 }]
	 });
	var addcustContrastWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'600',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[custContrastForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 if (!custContrastForm.getForm().isValid()) {
		        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		        		 return false;
		        	 }
		        	 var countNum = 0;
		        	 var countName = '';
		        	 var selectStr = custContrastStore.data.items;//当前关联客户数据
		        	 var selectCustId = '';
		        	 var selectCustName = '';
		        	 if(custContrastForm.form.findField('abcd').getValue().length>0){
		        	 selectCustId = custContrastForm.form.findField('abcd').getValue().split(',');//所筛选的关联产品ID 
	        		 selectCustName = custContrastForm.form.findField('custNameStr').getValue().split(',');//所筛选的关联产品名称
		        	 }
		        	 var selectCustId1 = '';
		        	 var selectCustName1 = '';
		        	 if(!_SOURCE_GROUP){
		        		 if(search_cust_group.custStr!=undefined)
		        		 if(search_cust_group.custStr.length>0){
			        		  selectCustId1 = search_cust_group.custStr.split(','); 
				        	  selectCustName1 = search_cust_group.custStr1.split(',');
		        		 }
		        	 }
		        	 for(var i = 0;i<custContrastStore.data.length;i++)
		        	 {
		        		 //判断客户放大镜
		        		 for(var j = 0;j<selectCustId.length;j++){
		        			 if(selectCustId[j]==selectStr[i].data.custId){
		        				 countNum++;
		        				 countName=countName+',<br>'+selectCustId[j]+':'+selectCustName[j];
		        			 }
		        		 }
		        		 //判断客户群
		        		 for(var j = 0;j<selectCustId1.length;j++){
		        			 if(selectCustId1[j]==selectStr[i].data.custId){
		        				 countNum++;
		        				 countName=countName+',<br>'+selectCustId[j]+':'+selectCustName[j];
		        			 }
		        		 }
		        	 }
		        	 if(countNum>0){
		        		 Ext.MessageBox.confirm('提示','您所选客户中有'+countNum+'位已经建立关联关系，分别为：'+countName+'。 <br>保存结果将不包含该部分客户，<br>确定执行此操作吗?',
		        				 function(buttonId){
		     				if(buttonId.toLowerCase() == "no"){
		     					 return false;
		     					}
		     			Ext.Msg.wait('正在保存，请稍后......','系统提示');
		        	 Ext.Ajax.request({
							url : basepath + '/addmarketprodaction!saveData.json?sign=customer',
							params : {
							'custIdStr':custContrastForm.form.findField('abcd').getValue(),
							'custNameStr':custContrastForm.form.findField('custNameStr').getValue(),
							'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
							'custStr':search_cust_group.custStr,
							'custStr1':search_cust_group.custStr1
							},
							method : 'POST',
							form : custContrastForm.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功!');
								custContrastForm.form.reset();
								custContrastStore.reload( {
                                     params : {
                                         start : 0,
                                         limit : cust_bbar.pageSize
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
//								store.reload();
							}
						});
		        		 });}else if(countNum==0){
		        		 	Ext.Msg.wait('正在保存，请稍后......','系统提示');
		        			 Ext.Ajax.request({
									url : basepath + '/addmarketprodaction!saveData.json?sign=customer',
									params : {
									'custIdStr':custContrastForm.form.findField('abcd').getValue(),
									'custNameStr':custContrastForm.form.findField('custNameStr').getValue(),
									'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
									'custStr':search_cust_group.custStr,
									'custStr1':search_cust_group.custStr1
									},
									method : 'POST',
									form : custContrastForm.getForm().id,
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function() {
										Ext.Msg.alert('提示', '操作成功!');
										custContrastStore.reload( {
		                                     params : {
		                                         start : 0,
		                                         limit : cust_bbar.pageSize
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
//										store.reload();
									}
								});
		        		 }
		        	 addcustContrastWind.hide();
		         }
		         }, {
		        	 text:'重置',
		        	 handler:function(){
		        	 	custContrastForm.getForm().reset();
		        	 	search_cust_group.custStr='';
		         	}
		         }]
	});
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
		labelWidth : 100,
		height : 270,
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


    // 新增、修改、详情信息窗口展示的from
    var editBasePlanForm = new Ext.form.FormPanel({
            frame : true,
            region : 'center',
            autoScroll : true,
            items : [ {
                layout : 'column',
                items : [ {
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                       // width : 134,
                        fieldLabel : '<span style="color:red">*</span>营销活动名称',
                        allowBlank : false,
                        name : 'mktActiName',
                        anchor : '95%'
                    },{
						store : mactiStatusStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销活动状态',
						hiddenName : 'mktActiStat',
						name : 'mktActiStat',
						hidden:true,
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
					//	width : 100,
						readOnly : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '95%'
					},{
                        xtype : 'datefield',
                        labelStyle : 'text-align:right;',
                       // width : 134,
                        editable:false,
                        allowBlank : false,
                        format : 'Y-m-d',
                        fieldLabel : '<span style="color:red">*</span>计划开始时间',
                        name : 'pstartDate',
                        anchor : '95%'
                    }  ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
						store : mactiTypeStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销活动类型',
						hiddenName : 'mktActiType',
						name : 'mktActiType',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						allowBlank : false,
						mode : 'local',
						//width : 100,
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '95%'
					},{
						fieldLabel : '<span style="color:red">*</span>计划结束时间',
						xtype : 'datefield',
						//width : 134,
						labelStyle : 'text-align:right;',
						format : 'Y-m-d',
						editable : false,
						allowBlank : false,
						name : 'pendDate',
						anchor : '95%'
					}]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [{
						store : mactiWayeStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销方式',
						hiddenName : 'mktActiMode',
						name : 'mktActiMode',
						valueField : 'key',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						//width : 134,
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '95%'
					},{
                        xtype : 'textfield',
                        fieldLabel : '创建人',
                        hidden:true,
                        name : 'createUser',
                        anchor : '95%'
                    },{
                        xtype : 'datefield',
                        fieldLabel : '创建时间',
                        format : 'Y-m-d',
                        hidden:true,
                        name : 'createDate',
                        anchor : '95%'
                    },{
                        xtype : 'textfield',
                        fieldLabel : '更新人',
                        hidden:true,
                        name : 'updateUser',
                        anchor : '95%'
                    },{
                        xtype : 'datefield',
                        fieldLabel : '更新时间',
                        format : 'Y-m-d',
                        hidden:true,
                        name : 'updateDate',
                        anchor : '95%'
                    },{
                        xtype : 'textfield',
                        fieldLabel : '创建机构',
                        hidden:true,
                        name : 'createOrg',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [{
                        labelStyle : 'text-align:right;',
                       // width : 134,
                        fieldLabel : '<span style="color:red">*</span>费用预算',
                        xtype : 'numberfield', // 设置为数字输入框类型
						decimalPrecision:2,
						maxValue:99999999,
                        allowBlank : false,
                        name : 'mktActiCost',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                       // width : 134,
                        hidden:true,
                        fieldLabel : '营销活动ID',
                        name : 'mktActiId',
                        anchor : '95%'
                    } ]
                } ]
            },{
            	layout:'column',
            	items:[{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        //width : 400,
                        fieldLabel : '活动地点',
                        name : 'mktActiAddr',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        //width : 400,
                        fieldLabel : '营销活动内容',
                        // readOnly : true,
                        name : 'mktActiCont',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        //width : 400,
                        fieldLabel : '涉及客户群描述',
                        // readOnly : true,
                        name : 'actiCustDesc',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        //width : 400,
                        fieldLabel : '涉及执行人描述',
                        // readOnly : true,
                        name : 'actiOperDesc',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        //width : 400,
                        fieldLabel : '涉及产品描述',
                        // readOnly : true,
                        name : 'actiProdDesc',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        //width : 400,
                        fieldLabel : '营销活动目的',
                        // readOnly : true,
                        name : 'mktActiAim',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth :1,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                       // width : 400,
                        fieldLabel : '备注',
                        // readOnly : true,
                        name : 'actiRemark',
                        anchor : '97.5%'
                    } ]
                }]
            }]
        });
	
    // 修改窗口展示的from
    var editPlanPanel = new Ext.Panel( {
        layout : 'fit',
        autoScroll : true,
        buttonAlign : "center",
        items : [ editBasePlanForm ]
    });

    // 定义修改窗口
    var editPlanWindow = new Ext.Window( {
        title : '营销活动修改',
        plain : true,
        layout : 'fit',
        autoScroll : true,
        maximized : true,
        draggable : true,
        closable : true,
        closeAction : 'hide',
        modal : true,
        titleCollapse : true,
        loadMask : true,
        border : false,
        items : [ {
            title : '基本信息',
            buttonAlign : "center",
			id:'jbxx',
            layout : 'fit',
            items : [editBasePlanForm],
            buttons : [ {
    			text : '保存',
    			handler : function() {
    			if (!editBasePlanForm.getForm().isValid()) {
                    Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                    return false;
                };
                var _date = new Date();
                var _pstartDate = editBasePlanForm.form.findField('pstartDate').getValue();
                var _pendDate   = editBasePlanForm.form.findField('pendDate').getValue();
                if(_pstartDate.format('Y-m-d')<_date.format('Y-m-d')){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于今天');
                	return false;
                }if(_pendDate<_pstartDate){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于计划结束时间');
                	return false;
                }
    			Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行吗?',function(buttonId){
    				if(buttonId.toLowerCase() == "no"){
    				return;
    				}
    				Ext.Msg.wait('正在保存，请稍后......','系统提示');
    			Ext.Ajax.request({
    				url : basepath + '/market-activity.json',
    				params : {
    				},
    				method : 'POST',
    				form : editBasePlanForm.getForm().id,
    				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
    				success : function() {
    					 Ext.Ajax.request({
    				         url: basepath +'/market-activity!getPid.json',
    					         success:function(response){
    							 var mktActStr = Ext.util.JSON.decode(response.responseText).pid;
    							 editBasePlanForm.form.findField('mktActiId').setValue(mktActStr);
    							 if(ifGroup){//客户群生成营销活动，保存客户到目标客户表中
    								 Ext.Ajax.request({
    		    				         url: basepath +'/market-activity!saveCust.json',
    		    				         	params : {
    		    				        	 groupId:groupId,
    		    				        	 id:mktActStr
    		    		    				},
    		    					         success:function(response){
    		    					        	 Ext.Msg.alert('提示', '操作成功');
    		    						 	},
    		    						 	failure:function(){
    		    						 		Ext.Msg.alert('提示', '添加目标客户失败');
    		    						 	}
    		    						 });
    							 }else if(ifProd){//推荐产品生成营销活动，保存产品到目标产品表中  添加当前客户
    								 Ext.Ajax.request({
    		    				         url: basepath +'/market-activity!saveProd.json',
    		    				         	params : {
    		    				         	prodId:prodId,
    		    				         	prodName:prodName,
    		    				         	custId:custId,
    		    				         	custName:custName,
    		    				        	id:mktActStr
    		    		    				},
    		    					         success:function(response){
    		    					        	 Ext.Msg.alert('提示', '操作成功');
    		    						 	},
    		    						 	failure:function(){
    		    						 		Ext.Msg.alert('提示', '添加目标产品失败');
    		    						 	}
    		    						 });
    							 }else{
    								 Ext.Msg.alert('提示', '操作成功'); 
    							 }
    					         }
    						 });
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    				       if(resultArray == 403) {
    				           Ext.Msg.alert('系统提示', response.responseText);
    				  } else{

    					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    				}
    				}
    			});
    		});
    		}
    		}, {
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
    				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
    				 return false;
    			}
    			Ext.getCmp('jbxx').collapse();
    			Ext.getCmp('glcpxx').expand();
    			if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('jbxx').hide();
    			Ext.getCmp('glcpxx').show();
    			}
    			productContrastStore.load(
    			{
    				params : {
                         start : 0,
                         limit : prod_bbar.pageSize
                             }
                 });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联产品信息',
            collapsed:true,
			collapsible : true,
			id:'glcpxx',
//			autoHeight : true,
            layout : 'fit',
            buttonAlign : "center",
            items : [productContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glcpxx').collapse();
    			Ext.getCmp('jbxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glcpxx').hide();
    			Ext.getCmp('jbxx').show();
    				}
    			}
    		},{
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
   				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
   				 return false;
   			}
    			Ext.getCmp('glcpxx').collapse();
    			Ext.getCmp('glkkxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glcpxx').hide();
    			Ext.getCmp('glkkxx').show();
    				}
    			custContrastStore.load({
					  params : {
					  start : 0,
					  limit : cust_bbar.pageSize
		  }
		  });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联客户信息',
            collapsed:true,
        	id:'glkkxx',
			collapsible : true,
//			autoHeight : true,
            layout : 'fit',
            buttonAlign : "center",
            items : [custContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glkkxx').collapse();
    			Ext.getCmp('glcpxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glkkxx').hide();
    			Ext.getCmp('glcpxx').show();
    				}
    			productContrastStore.load( 
    				{
                     params : {
                     start : 0,
                     limit : prod_bbar.pageSize
                     }
                 });
    			}
    		},{
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
   				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
   				 return false;
   			}
    			Ext.getCmp('glkkxx').collapse();
    			Ext.getCmp('glqdxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glkkxx').hide();
    			Ext.getCmp('glqdxx').show();
    				}
    			chanelContrastStore.load(
    				{ 
    				 params : {
                     start : 0,
                     limit : chanel_bbar.pageSize
                 }
             });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联渠道信息',
            collapsed:true,
			collapsible : true,
			id:'glqdxx',
//			autoHeight : true,
			 buttonAlign : "center",
            layout : 'fit',
            items : [chanelContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glqdxx').collapse();
    			Ext.getCmp('glkkxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glqdxx').hide();
    			Ext.getCmp('glkkxx').show();
    				}
    			custContrastStore.load({
				  params : {
				  start : 0,
				  limit : cust_bbar.pageSize
			  }
			  });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }
        ],
        listeners : {
		beforeshow : function(){
    	custContrastGrid.tbar.setDisplayed(_buttonVisible);
    	chanelContrastGrid.tbar.setDisplayed(_buttonVisible);
    	productContrastGrid.tbar.setDisplayed(_buttonVisible);
    	Ext.getCmp('jbxx').buttons[0].setVisible(_buttonVisible);
    	
    	//产品信息store
    	productContrastStore.on('beforeload', function() {
    		this.baseParams = {
    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
    				querysign:'prod'
    		};
    	});
    	productContrastStore.load({
    		params : {
    			start : 0,
    			limit : prod_bbar.pageSize
    		}
    	});
    	//客户信息store
    	custContrastStore.on('beforeload', function() {
    		this.baseParams = {
    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
    				querysign:'customer'
    		};
    	});
    	custContrastStore.load({
    		params : {
    			start : 0,
    			limit : cust_bbar.pageSize
    		}
    	});
    	//渠道信息store
    	chanelContrastStore.on('beforeload', function() {
    		this.baseParams = {
    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
    				querysign:'chanel'
    		};
    	});
    	chanelContrastStore.load({
    		params : {
    			start : 0,
    			limit : chanel_bbar.pageSize
    		}
    	});
    	//附件信息
    	var messageIdStr = editBasePlanForm.form.findField('mktActiId').getValue();
    	        uploadForm.relaId = messageIdStr;
    	        uploadForm.modinfo = 'infomation';
    	        var condi = {};
    	        condi['relationInfo'] = messageIdStr;
    	        condi['relationMod'] = 'infomation';
    	        Ext.Ajax.request({
    	            url:basepath+'/queryanna.json',
    	            method : 'GET',
    	            params : {
    	                "condition":Ext.encode(condi)
    	            },
    	            failure : function(a,b,c){
    	                Ext.MessageBox.alert('查询异常', '查询失败！');
    	            },
    	            success : function(response){
    	                var anaExeArray = Ext.util.JSON.decode(response.responseText);
    	                appendixStore.loadData(anaExeArray.json.data);
    	            }
    	        });
    	
    	//start 调整在点击修改界面时出现的展示问题。
    	Ext.getCmp('jbxx').expand();
		Ext.getCmp('glcpxx').collapse();
		Ext.getCmp('glkkxx').collapse();
		Ext.getCmp('glqdxx').collapse();
    	//end
    	
		}}
    });
    
 // 展示新增的窗口
	function addActivityInit(id,ifgroup,ifprod) {
		ifGroup = ifgroup;	
		ifProd = ifprod;
		groupId=id;
		var startData = new Date();
		_buttonVisible = true;
		_sheetVisible = false;
		editBasePlanForm.form.reset();
		
		editBasePlanForm.form.findField('mktActiName').setDisabled(false);
		editBasePlanForm.form.findField('mktActiCost').setDisabled(false);
		editBasePlanForm.form.findField('mktActiType').setDisabled(false);
		editBasePlanForm.form.findField('pstartDate').setDisabled(false);
		editBasePlanForm.form.findField('mktActiMode').setDisabled(false);
		editBasePlanForm.form.findField('pendDate').setDisabled(false);
		editBasePlanForm.form.findField('mktActiStat').setDisabled(false);
		editBasePlanForm.form.findField('mktActiAddr').setDisabled(false);
		editBasePlanForm.form.findField('mktActiCont').setDisabled(false);
		editBasePlanForm.form.findField('actiCustDesc').setDisabled(false);
		editBasePlanForm.form.findField('actiOperDesc').setDisabled(false);
		editBasePlanForm.form.findField('actiProdDesc').setDisabled(false);
		editBasePlanForm.form.findField('mktActiAim').setDisabled(false);
		editBasePlanForm.form.findField('actiRemark').setDisabled(false);
		
		editPlanWindow.setTitle('营销活动新增');
		editBasePlanForm.form.findField('createUser').setValue(__userId);
		editBasePlanForm.form.findField('createDate').setValue(startData);
		editBasePlanForm.form.findField('updateUser').setValue(__userId);
		editBasePlanForm.form.findField('updateDate').setValue(startData);
		editBasePlanForm.form.findField('mktActiStat').setValue('1');
		editBasePlanForm.form.findField('createOrg').setValue(__units);
		
		Ext.getCmp('jbxx').show();
		Ext.getCmp('glcpxx').hide();
		Ext.getCmp('glkkxx').hide();
		Ext.getCmp('glqdxx').hide();
		editPlanWindow.show();
	};