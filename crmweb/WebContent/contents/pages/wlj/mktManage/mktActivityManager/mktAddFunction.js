/**
 * 营销活动新增的公共处理页面
 * luyy
 * 2014-07-31
 */

/**
 * custIdStrs ： 客户id字符串   ,号分割，末尾不要标点 如：100,200,300
 * prodIdStrs ：产品id字符串    ,号分割，末尾不要标点 如：100,200,300
 * groupId : 客户群id  ifGroup 为true时必须
 * ifGroup ：是否客户群处新增    布尔型  必须
 * ifProd ：是否产品目标客户处新增  布尔型  默认为false
 * ifAddCust ：是否需要新增客户按钮  布尔型   默认为true
 * ifDelCust ：是否需要删除客户按钮  布尔型   默认为true
 * ifAddProd ：是否需要新增产品按钮  布尔型   默认为true
 * ifDelProd ：是否需要删除产品按钮  布尔型   默认为true
 * 
 */

function getActiveAddWindowShow(custIdStrs,prodIdStrs,groupId,ifGroup,ifProd,ifAddCust,ifDelCust,ifAddProd,ifDelProd){
	var ifappl = true;
	Ext.Ajax.request({
		url : basepath + '/market-activity!getIfAppl.json',
		success : function(response) {
			var ret = Ext.decode(response.responseText);
			var parma = ret.mktAppType;
			ifappl = parma == '00'?true:false;
		}
	});
	
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
	  //营销活动状态
	    var chanelStore = new Ext.data.Store( {
	        restful : true,
	        sortInfo : {
	            field : 'key',
	            direction : 'ASC'
	        },
	        autoLoad : true,
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=MKT_CHANEL'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    chanelStore.load();
	    
    // 新增信息窗口展示的from
    var editBasePlanForm = new Ext.form.FormPanel({
            frame : true,
            region : 'center',
            autoScroll : true,
            items : [ {
                layout : 'column',
                items : [ {
                    columnWidth : .33,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 134,
                        fieldLabel : '<span style="color:red">*</span>营销活动名称',
                        allowBlank : false,
                        name : 'mktActiName',
                        anchor : '95%'
                    },{
                        xtype : 'datefield',
                        labelStyle : 'text-align:right;',
                        width : 134,
                        editable:false,
                        allowBlank : false,
                        format : 'Y-m-d',
                        fieldLabel : '<span style="color:red">*</span>计划开始时间',
                        name : 'pstartDate',
                        anchor : '95%'
                    },new Ext.ux.form.LovCombo({
    					fieldLabel : '营销渠道',
    					labelStyle : 'text-align:right;',
    					name : 'mktChanel',
    					hiddenName : 'mktChanel',
    					triggerAction : 'all',
    					store : chanelStore ,
    					displayField : 'value',
    					valueField : 'key',
    					mode : 'local',
    					emptyText : '请选择 ',
    					resizable : true,
    					hideOnSelect : false,
    					triggerAction : 'all',
    					allowBlank : false,
    					editable : true,
    					anchor : '90%'
    				}),{
						store : mactiStatusStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销活动状态',
						hiddenName : 'mktActiStat',
						name : 'mktActiStat',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						hidden:true,
						width : 100,
						readOnly : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '95%'
					},{
						xtype:'hidden',
						name:'mktAppState',
						value:ifappl?'1':'3'
					} ]
                },{
                    columnWidth : .33,
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
						width : 100,
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '95%'
					},{
						fieldLabel : '<span style="color:red">*</span>计划结束时间',
						xtype : 'datefield',
						width : 134,
						labelStyle : 'text-align:right;',
						format : 'Y-m-d',
						editable : false,
						allowBlank : false,
						name : 'pendDate',
						anchor : '95%'
					} ]
                },{
                    columnWidth : .34,
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
						width : 134,
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '95%'
					},{
                        labelStyle : 'text-align:right;',
                        width : 134,
                        fieldLabel : '<span style="color:red">*</span>费用预算',
                        xtype : 'numberfield', // 设置为数字输入框类型
						decimalPrecision:2,
						maxValue:99999999,
                        allowBlank : false,
                        name : 'mktActiCost',
                        anchor : '95%'
                    } ,{
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
                    },{
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 134,
                        hidden:true,
                        fieldLabel : '营销活动ID',
                        name : 'mktActiId',
                        anchor : '95%'
                    }  ]
                }]},
              {
                layout : 'column',
                items : [{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
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
                        width : 400,
                        fieldLabel : '营销活动内容',
                        name : 'mktActiCont',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '涉及客户群描述',
                        name : 'actiCustDesc',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '涉及执行人描述',
                        name : 'actiOperDesc',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '涉及产品描述',
                        name : 'actiProdDesc',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '营销活动目的',
                        name : 'mktActiAim',
                        anchor : '95%'
                    } ]
                },{
                    columnWidth : 50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '备注',
                        name : 'actiRemark',
                        anchor : '90%'
                    } ]
                } ]}]
        });
	
	//产品放大镜
	var prodEditCode = new Com.yucheng.crm.common.ProductManage({
		xtype : 'productChoose',
		fieldLabel : '目标营销产品',
		name : 'productName',
		hiddenName : 'aimProd',
		singleSelect : false,
		anchor : '90%',
		callback :function(){
			var prodCode = productContrastForm.form.findField('aimProd').getValue();
			productContrastForm.form.findField('prodCode').setValue(prodCode);
		}
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
			cm:productContrastColumns,
			region:'center',
			sm:prod_sm,
			tbar:[
			      { text:'新增',
			    	hidden:!ifAddProd,
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
			    	hidden:!ifDelProd,
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
							Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
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
												'_SOURCE_PORD':'true'
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
										'_SOURCE_PORD':false,
										'_IS_SAVE_CUST':false
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
											'_SOURCE_PORD':false,
											'_IS_SAVE_CUST':false
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
	
	var custSourceStore = new Ext.data.ArrayStore( {
        fields : [ 'myId', 'displayText' ],
        data : [ [ '1', '自定义筛选' ]]
    });
	
	var search_cust_edit = new Com.yucheng.bcrm.common.CustomerQueryField({ 
		fieldLabel : '目标客户', 
		labelStyle: 'text-align:right;',
		name : 'custNameStr',
	    singleSelected:false,//单选复选标志
		editable : false,
		blankText:"请填写",
		anchor : '90%',
		hiddenName:'abcd',
		callback :function(){
	}
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
			    	iconCls:'addIconCss',
			    	hidden:!ifAddCust,
			       handler:function(){
			    	  	addcustContrastWind.show();
			    	  	addcustContrastWind.setTitle('关联客户维护');
			    	  	custContrastForm.getForm().getEl().dom.reset();
			    	  	custContrastStore.reload();
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	hidden:!ifDelCust,
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
	                    store : custSourceStore,
	                    valueField : 'myId',
	                    displayField : 'displayText',
	                    emptyText : '请选择',
	                    anchor : '95%'} ]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [search_cust_edit,{
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
					}]
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
		        	 selectCustId = custContrastForm.form.findField('abcd').getValue().split(',');
	        		 selectCustName = custContrastForm.form.findField('custNameStr').getValue().split(',');
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
							'custStr':'',
							'custStr1':''
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
									'custStr':'',
									'custStr1':''
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
									}
								});
		        		 }
		        	 addcustContrastWind.hide();
		         }
		         }, {
		        	 text:'重置',
		        	 handler:function(){
		        	 	custContrastForm.getForm().reset();
		         	}
		         }]
	});

//	 var chanelTypeStore = new Ext.data.Store({//渠道类型的store
//			restful:true,   
//			autoLoad :true,
//			proxy : new Ext.data.HttpProxy({
//				url :basepath+'/chaneltypeinfo.json?tableName='+'OCRM_F_MM_CHANNEL_INFO'
//			}),
//			reader : new Ext.data.JsonReader({
//				root : 'json.data'
//			}, [ 'CHANNEL_ID', 'CHANNEL_NAME' ])
//		});   

//	    var chanelContrastRecord = Ext.data.Record.create(
//	    		[
//	    		 {name:'actiChannelId',mapping:'ACTI_CHANNEL_ID'},
//	    		 {name:'appCustLever',mapping:'APP_CUST_LEVER'},
//	    		 {name:'cahnTemCont',mapping:'CAHN_TEM_CONT'},
//	    		 {name:'cahnTemName',mapping:'CAHN_TEM_NAME'},
//	    		 {name:'createDate',mapping:'CREATE_DATE'},
//	    		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
//	    		 {name:'productId',mapping:'PRODUCT_ID'},
//	    		 {name:'channelNames',mapping:'CHANNEL_NAMES'},
//	    		 {name:'createUser',mapping:'CREATE_USER'},
//	    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'},
//	    		 {name:'templetName',mapping:'TEMPLETNAME'}
//	    		 ]
//	    );
//	    var chanelContrastReader = new Ext.data.JsonReader(//读取jsonReader
//	    		{
//	    			successProperty : 'success',
//	    			idProperty : 'ID',
//	    			totalProperty : 'json.count',
//	    			root:'json.data'
//	    		},chanelContrastRecord
//		);
//		var chanelContrastStore = new Ext.data.Store({//产品对照关系store
//		        restful : true, 
//		        proxy : new Ext.data.HttpProxy({ 
//		        	url:basepath+'/mktactivityrelateinfoaction.json',
//		        	method:'get'
//		        }),
//				reader:chanelContrastReader
//				
//		});
		
		// 每页显示条数下拉选择框
//		var chanel_pagesize_combo = new Ext.form.ComboBox({
//			name : 'pagesize',
//			triggerAction : 'all',
//			mode : 'local',
//			store : new Ext.data.ArrayStore({
//									fields : ['value', 'text'],
//									data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
//									         [ 100, '100条/页' ], [ 250, '250条/页' ],
//									         [ 500, '500条/页' ] ]
//			}),
//			valueField : 'value',
//			displayField : 'text',
//			value : '100',
//			editable : false,
//			width : 85
//		});
//		var chanel_bbar = new Ext.PagingToolbar({// 分页工具栏
//			pageSize : parseInt(chanel_pagesize_combo.getValue()),
//			store : chanelContrastStore,
//			displayInfo : true,
//			displayMsg : '显示{0}条到{1}条,共{2}条',
//			emptyMsg : "没有符合条件的记录",
//			items : ['-', '&nbsp;&nbsp;', chanel_pagesize_combo]
//		});
		
//		chanel_pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
//			  chanel_bbar.pageSize = parseInt(chanel_pagesize_combo.getValue()),
//			  chanelContrastStore.reload({
//				  params : {
//				  start : 0,
//				  limit : parseInt(chanel_pagesize_combo.getValue())
//			  }
//			  });
//		  });
	 //********************************

//		var chanel_sm = new Ext.grid.CheckboxSelectionModel();
//		// 定义自动当前页行号
//		var chanel_rownum = new Ext.grid.RowNumberer({
//			header : 'No.',
//			width : 28
//		    });
//		 var chanelContrastColumns = new Ext.grid.ColumnModel(
//					{
//						columns:[chanel_rownum,chanel_sm,
//						{ header:'ID',dataIndex:'actiChannelId',sortable:true,hidden:true},
//						{ header:'营销活动编号',dataIndex:'mktActiId',sortable:true,hidden:true},
//						{ header:'渠道编号',dataIndex:'productId',sortable:true,width:150,hidden:true},
//						{ header:'渠道名称',dataIndex:'channelNames',sortable:true,width:160},
//						{ header:'模板名称',dataIndex:'templetName',sortable:true,width:160},
//						{ header:'创建人编号',dataIndex:'createUser',width:160,sortable:true,hidden:true},
//						{ header:'创建人',dataIndex:'createUserName',width:160,sortable:true},
//						{ header:'创建时间',dataIndex:'createDate',width:160,sortable:true}
//						]
//					}
//		 );
//		 /*************************************列模型***********************************************/
//		 var sm = new Ext.grid.CheckboxSelectionModel();
//		 var chanelContrastGrid = new Ext.grid.EditorGridPanel({			
//				store:chanelContrastStore, 
//				frame:true,
//				height : 200,
////				width : 200,
//				cm:chanelContrastColumns,
//				region:'center',
//				sm:chanel_sm,
//				tbar:[
//				      { text:'新增',
//				    	iconCls:'addIconCss',
//				       handler:function(){
//				    	  	chanelContrastForm.form.reset();
//				    	  	addchanelContrastWind.show();
//				    	  	addchanelContrastWind.setTitle('关联渠道信息新增');
//				    	  	chanelContrastForm.getForm().getEl().dom.reset();
//				    	  	chanelContrastStore.reload();
//				      }
//				      },{
//				    	text:'删除',
//				    	iconCls:'deleteIconCss',
//				    	handler:function(){
//							 var selectLength = chanelContrastGrid.getSelectionModel().getSelections().length;
//							 var selectRe;
//							 var tempId;
//							 var idStr = '';
//							if(selectLength < 1){
//								Ext.Msg.alert('提示','请选择需要删除的记录!');
//							} else {
//								for(var i = 0; i<selectLength;i++)
//								{
//									selectRe = chanelContrastGrid.getSelectionModel().getSelections()[i];
//									tempId = selectRe.data.actiChannelId;
//									idStr += tempId;
//									if( i != selectLength-1)
//										idStr += ',';
//								}
//									Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
//										if(buttonId.toLowerCase() == "no"){
//										return;
//										} 
//										Ext.Ajax.request({
//													url : basepath
//													+ '/addmarketprodaction!batchDestroy.json',
//													params : {
//														'idStr' : idStr,
//														'mktActiId' : editBasePlanForm.form.findField('mktActiId').getValue(),
//														'delSgin':'chanel'
//													},
//													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//													success : function() {
//														Ext.Msg.alert('提示', '操作成功');
//														chanelContrastStore.reload();
//													},
//													failure : function(response) {
//														var resultArray = Ext.util.JSON.decode(response.status);
//														if(resultArray == 403) {
//													           Ext.Msg.alert('提示', response.responseText);
//													  } else {
//														Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//														chanelContrastStore.reload();
//													  }}
//												});
//									});
//							}}
//				      }],
//				      bbar:chanel_bbar,
//				      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
//		 			  },
//		 			  loadMask : {
//		 				  msg : '正在加载表格数据,请稍等...'
//		 			  }
//		 });
		 
		 /****************************************产品对照关系信息*************************************************/
//		 var chanelContrastForm = new Ext.form.FormPanel({
//				region:'center',
//				margins: '0 0 0 0',
//				autoScroll:true,
//				labelWidth : 120, // 标签宽度
//				frame : true, //是否渲染表单面板背景色
//				labelAlign : 'middle', // 标签对齐方式
//				buttonAlign : 'center',
//				height : 120,
//				items : [{
//					layout : 'column',
//					border : false,
//					items : [{
//						columnWidth : .8,
//						layout : 'form',
//						labelWidth : 120, // 标签宽度
//						defaultType : 'textfield',
//						border : false,
//						items : [new Ext.ux.form.LovCombo({
//					    	fieldLabel : '<font color=red>*</font>渠道名称',
//					    	name : 'chanelType',
//					    	labelStyle: 'text-align:right;',
//					    	displayField : 'CHANNEL_NAME',
//					    	valueField : 'CHANNEL_ID',
//					    	hideOnSelect:false,
//					    	store :chanelTypeStore,
//					    	triggerAction:'all',
//					    	mode:'local',
//					    	allowBlank:false,
//					    	editable:true,
//					    	anchor : '95%'
//					    })/*,{
//							 name : 'relDesc',
//							 labelStyle: 'text-align:right;',
//							 xtype : 'textarea',
//							 fieldLabel : '描述',
//							 anchor : '95%'
//						 }*/
//					    ]}
//					]
//				}]
//			});
//		var addchanelContrastWind = new Ext.Window({//新增和修改的window
//			closeAction:'hide',
//			height:'200',
//			width:'500',
//			modal : true,//遮罩
//			buttonAlign:'center',
//			layout:'fit',
//			items:[chanelContrastForm],
//			buttons:[
//			         {
//			        	 text:'保存',
//			        	 handler: function(){
//				        	 if (!chanelContrastForm.getForm().isValid()) {
//				        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
//				        		 return false;
//				        	 }
//				        	//判断所选渠道中是否存在重复数据
//				        	 var countNum = 0;
//				        	 var countName = '';
//				        	 var selectStr = chanelContrastStore.data.items;//当前关联产品数据
//				        	 var selectChanelId = chanelContrastForm.form.findField('chanelType').getValue().split(',');//所筛选的关联产品ID
//				        	 for(var i = 0;i<chanelContrastStore.data.length;i++)
//				        	 {
//				        		 for(var j = 0;j<selectChanelId.length;j++){
//				        			 if(selectChanelId[j]==selectStr[i].data.productId){
//				        				 countNum++;
//				        				 countName=countName+',<br>'+selectChanelId[j];
//				        			 }
//				        		 }
//				        	 }
//				        	 if(countNum>0){
//				        		 Ext.MessageBox.confirm('提示','您所选渠道中有【 '+countNum+' 】种渠道方式已经建立关联关系，分别为：'+countName+'。<br>保存结果将不包含该类渠道，<br>确定执行此操作吗?',
//				        				 function(buttonId){
//				     				if(buttonId.toLowerCase() == "no"){
//				     					 return false;
//				     					} 
//				        	 Ext.Ajax.request({
//									url : basepath + '/addmarketprodaction!saveData.json?sign=chanel',
//									params : {
//									'chanelId':chanelContrastForm.form.findField('chanelType').getValue(),
//									'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue()
//									},
//									method : 'POST',
//									form : chanelContrastForm.getForm().id,
//									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//									success : function() {
//										Ext.Msg.alert('提示', '操作成功!');
//										chanelContrastStore.reload( {
//		                                     params : {
//		                                         start : 0,
//		                                         limit : chanel_bbar.pageSize
//		                                     }
//		                                 });
//									},
//									failure : function(response) {
//										var resultArray = Ext.util.JSON.decode(response.status);
//									       if(resultArray == 403) {
//									           Ext.Msg.alert('提示', response.responseText);
//									  } else{
//
//										Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//									}
//									}
//								});
//				        		 });}else if(countNum==0){
//				        			 Ext.Ajax.request({
//											url : basepath + '/addmarketprodaction!saveData.json?sign=chanel',
//											params : {
//											'chanelId':chanelContrastForm.form.findField('chanelType').getValue(),
//											'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue()
//											},
//											method : 'POST',
//											form : chanelContrastForm.getForm().id,
//											waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//											success : function() {
//												Ext.Msg.alert('提示', '操作成功!');
//												chanelContrastStore.reload( {
//				                                     params : {
//				                                         start : 0,
//				                                         limit : chanel_bbar.pageSize
//				                                     }
//				                                 });
//											},
//											failure : function(response) {
//												var resultArray = Ext.util.JSON.decode(response.status);
//											       if(resultArray == 403) {
//											           Ext.Msg.alert('提示', response.responseText);
//											  } else{
//
//												Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//											}
//											}
//										});	 
//				        		 }
//				        	 addchanelContrastWind.hide();
//				         }
//			         },
//			         {
//			        	 text:'重置',
//			        	 handler:function(){
//			        	 	chanelContrastForm.getForm().reset();
//			         	}
//			         }
//			        ]
//		});
		
		
	var _editPlanWindow = new Ext.Window({
        title : '营销活动新增',
        layout : 'fit',
    	closeAction : 'hide',
    	modal : true, // 模态窗口
    	shadow : true,
    	loadMask : true,
    	maximizable : true,
        draggable : true,
        closable : true,
        height:500,
        width:1000,
        closeAction : 'hide',
        modal : true,
        titleCollapse : true,
        loadMask : true,
        border : false,
        items : [ {
            xtype : 'fieldset',
            title : '基本信息',
            titleCollapse : true,
			collapsible : true,
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
    							 Ext.Ajax.request({
		    				         url: basepath +'/market-activity!saveCustAndProd.json',
		    				         	params : {
		    				        	 custIdStrs:custIdStrs,
		    				        	 prodIdStrs:prodIdStrs,
		    				        	 groupId:groupId,
		    				        	 ifGroup:ifGroup,
		    				        	 ifProd:ifProd?ifProd:false,
		    				        	 id:mktActStr
		    		    				},
		    					         success:function(response){
		    					        	 Ext.Msg.alert('提示', '操作成功');
		    						 	},
		    						 	failure:function(){
		    						 		Ext.Msg.alert('提示', '操作失败');
		    						 	}
		    						 });
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
    			Ext.getCmp('jbxx').hide();
    			Ext.getCmp('glcpxx').show();
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
    			_editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联产品信息',
            collapsed:true,
			collapsible : true,
			id:'glcpxx',
            layout : 'fit',
            buttonAlign : "center",
            items : [productContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glcpxx').collapse();
    			Ext.getCmp('jbxx').expand();
	    		Ext.getCmp('glcpxx').hide();
	    		Ext.getCmp('jbxx').show();
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
    			Ext.getCmp('glcpxx').hide();
    			Ext.getCmp('glkkxx').show();
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
    			_editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联客户信息',
            collapsed:true,
        	id:'glkkxx',
			collapsible : true,
            layout : 'fit',
            buttonAlign : "center",
            items : [custContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glkkxx').collapse();
    			Ext.getCmp('glcpxx').expand();
    			Ext.getCmp('glkkxx').hide();
    			Ext.getCmp('glcpxx').show();
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
    			Ext.getCmp('fjxx').expand();
    			Ext.getCmp('glkkxx').hide();
    			Ext.getCmp('fjxx').show();

    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			_editPlanWindow.hide();
    			}
    		} ]
        }
//        , {
//            xtype : 'fieldset',
//            title : '关联渠道信息',
//            collapsed:true,
//			collapsible : true,
//			id:'glqdxx',
//			 buttonAlign : "center",
//            layout : 'fit',
//            items : [chanelContrastGrid],
//            buttons : [{
//    			text : '上一步',
//    			handler : function() {
//    			Ext.getCmp('glqdxx').collapse();
//    			Ext.getCmp('glkkxx').expand();
//    			Ext.getCmp('glqdxx').hide();
//    			Ext.getCmp('glkkxx').show();
//    			custContrastStore.load({
//				  params : {
//				  start : 0,
//				  limit : cust_bbar.pageSize
//			  }
//			  });
//    			}
//    		},{
//    			text : '下一步',
//    			handler : function() {
//    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
//   				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
//   				 return false;
//   			}
//    	    	var messageIdStr = editBasePlanForm.form.findField('mktActiId').getValue();
//    	        uploadForm.relaId = messageIdStr;
//    	        uploadForm.modinfo = 'mktActive';
//    	        var condi = {};
//    	        condi['relationInfo'] = messageIdStr;
//    	        condi['relationMod'] = 'mktActive';
//    	        Ext.Ajax.request({
//    	            url:basepath+'/queryanna.json',
//    	            method : 'GET',
//    	            params : {
//    	                "condition":Ext.encode(condi)
//    	            },
//    	            failure : function(a,b,c){
//    	                Ext.MessageBox.alert('查询异常', '查询失败！');
//    	            },
//    	            success : function(response){
//    	                var anaExeArray = Ext.util.JSON.decode(response.responseText);
//    	                appendixStore.loadData(anaExeArray.json.data);
//    	            }
//    	        });
//    			Ext.getCmp('glqdxx').collapse();
//    			Ext.getCmp('fjxx').expand();
//    			Ext.getCmp('glqdxx').hide();
//    			Ext.getCmp('fjxx').show();
//    			}
//    		}, {
//    			text : '关  闭',
//    			handler : function() {
//    			_editPlanWindow.hide();
//    			}
//    		} ]
//        }
        , {
            xtype : 'fieldset',
            title : '附件信息',
            collapsed:true,
			collapsible : true,
			id:'fjxx',
			buttonAlign : "center",
            layout : 'fit',
            items : [appendixGridPanel2],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('fjxx').collapse();
    			Ext.getCmp('glkkxx').expand();
    			Ext.getCmp('fjxx').hide();
    			Ext.getCmp('glkkxx').show();
    			custContrastStore.load({ 
    			params : 
    				{
                     start : 0,
                     limit : cust_bbar.pageSize
                 }
             });
    			}
    		}, {
    			text : '完  成',
    			handler : function() {
    			_editPlanWindow.hide();
    			}
    		} ]
        }],
        listeners : {
		beforeshow : function(){
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
//    	//渠道信息store
//    	chanelContrastStore.on('beforeload', function() {
//    		this.baseParams = {
//    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
//    				querysign:'chanel'
//    		};
//    	});
//    	chanelContrastStore.load({
//    		params : {
//    			start : 0,
//    			limit : chanel_bbar.pageSize
//    		}
//    	});
    	//附件信息
    	var messageIdStr = editBasePlanForm.form.findField('mktActiId').getValue();
    	        uploadForm.relaId = messageIdStr;
    	        uploadForm.modinfo = 'mktActive';
    	        var condi = {};
    	        condi['relationInfo'] = messageIdStr;
    	        condi['relationMod'] = 'mktActive';
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
    	
		}}
    
	});
	
	editBasePlanForm.form.findField('createUser').setValue(__userId);
	editBasePlanForm.form.findField('createDate').setValue(new Date());
	editBasePlanForm.form.findField('updateUser').setValue(__userId);
	editBasePlanForm.form.findField('updateDate').setValue(new Date());
	editBasePlanForm.form.findField('mktActiStat').setValue('1');
	editBasePlanForm.form.findField('createOrg').setValue(__units);
	
	Ext.getCmp('jbxx').show();
	Ext.getCmp('glcpxx').hide();
	Ext.getCmp('glkkxx').hide();
//	Ext.getCmp('glqdxx').hide();
	Ext.getCmp('fjxx').hide();
	_editPlanWindow.show();
	
}