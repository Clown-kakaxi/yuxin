Ext.onReady(function(){	 
	//查询模块
	 var search =new Ext.form.FormPanel({
		 title:'客户统计查询',
		 frame:true,
		 labelAlign:'right',
		 buttonAlign:'center',
		 split:true,
		 height:100,
		 region:'north',
		 autoScoll:true,
		 items:[{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.25,
				 items:[{
					 xtype:'datefield',
					 fieldLabel:'期间从',
					 name:'customerTimeStart1',
					 format:'Y-m-d',
					 id:'customerTimeStart1',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.25,
				 items:[{
					 xtype:'datefield',
					 fieldLabel:'到',
					 format:'Y-m-d',
					 name:'customerTimeEnd1',
					 id:'customerTimeEnd1',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.25,
				 items:[{
					 
						xtype:'numberfield',
						fieldLabel:'购买次数>=',
						id:'budyTimes1',
						name:'budyTimes1',
						allowDecimals : 0,
						anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.25,
				 items:[{
						xtype:'combo',
						id:'customerState1',
						name:'customerState',
						triggerAction:'all',
						anchor:'90%',
					//	lazyRender:true,
						fieldLabel:'客户状态',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 0,
				        fields: ['value','displayText'],
				        data: [[1, '休眠'], [2, '正常'],[3,'活跃']]
				               }),
				       valueField:'value',
				       displayField:'displayText'
				 }]
			 }]
		 }],
		 buttons:[{
			 text:'查询',
			 handler:function(node){
			 var parameters = search.getForm().getValues(false);
			 customerstore.removeAll();
			 	customerstore.baseParams = {
	             'condition':Ext.util.JSON.encode(parameters)
	            };
			 customerstore.load({
			         params:{
			             start:0,
			             limit: parseInt(spagesize_combo.getValue())
			         }
			 	});
	          }
		 },{
			 text:'重置',
			 handler:function(){
			 search.getForm().reset();
		 }
		 }]
	 });
	
	//********************机构列表
		var productLimitColumns = new Ext.grid.ColumnModel([//grid中的列定义
		                                new Ext.grid.RowNumberer(),
		                                {header :'客户号',dataIndex:'customerId',id:"customerId",sortable : true,width:100},
		                                {header :'客户姓名 ',dataIndex:'customerName',id:"customerName",sortable : true,width:100},
		                                {header :'客户年龄',dataIndex:'customerOld',id:"customerOld",sortable : true,width:80},
		                                {header :'性别',dataIndex:'customerSex',id:"customerSex",sortable : true,width:80},
		                                {header :'资产',dataIndex:'customerIncome',id:"customerIncome",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'购买偏好',dataIndex:'customerLove',id:"customerLove",sortable : true,width:100},
		                                {header :'购买次数',dataIndex:'budyTime',id:"budyTime",sortable : true,width:100},
		                                {header :'每次签约量',dataIndex:'contractAmont',id:"contractAmont",align:'right',renderer: money('0,000.00'),sortable : true,width:80},
		                                {header :'累计签约期数',dataIndex:'contractPeriod',id:"contractPeriod",sortable : true,width:80},
		                                {header :'累计签约量',dataIndex:'totalContract',id:"totalContract",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'最后产品截止日期',dataIndex:'overproductTimeEnd',id:"overproductTimeEnd",sortable : true,width:140},
		                                {header :'客户状态',dataIndex:'customerState',id:"customerState",sortable : true,width:100}
		                                               	]);
		  var productInfoRecord = new Ext.data.Record.create([    
		                                   {name:'customerId',mapping:'CUSTOMER_ID'},
		                                   {name:'customerName',mapping:'CUSTOMER_NAME'},
		                                   {name:'customerOld',mapping:'CUSTOMER_OLD'},
		                                   {name:'customerSex',mapping:'CUSTOMER_SEX'},
		                                   {name:'customerIncome',mapping:'CUST_INCOME'},
		                                   {name:'customerLove',mapping:'CUSTOMER_LOVE'},	                         
		                                   {name:'budyTime',mapping:'BUDY_TIME'},
		                                   {name:'contractAmont',mapping:'CONT_AMONT'},
		                                   {name:'contractPeriod',mapping:'CONT_PERIOD'},
		                                   {name:'totalContract',mapping:'TOTAL_CONTRACT'},
		                                   {name:'overproductTimeEnd',mapping:'OVERPROD_END'},
		                                   {name:'customerState',mapping:'CUST_STATE'}            
		                                                      ]);

		var Reader = new Ext.data.JsonReader({//读取json数据的panel
		    totalProperty:'json.count',
		    root:'json.data'
		    },productInfoRecord);
		var customerstore=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({
		        url:basepath+'/productcustomer.json',
		        method:'GET'
		        }),
			reader:Reader
		});
		//*********************
		
		 var spagesize_combo = new Ext.form.ComboBox({
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
	
		// 机构列表的改变每页显示条数reload数据
		spagesize_combo.on("select", function(comboBox) {
			sbbar.pageSize = parseInt(spagesize_combo.getValue()),
			customerstore.reload({
				params : {
					start : 0,
					limit : parseInt(spagesize_combo.getValue())
				}
			});
		});
		// 机构列表分页工具栏
		var sbbar = new Ext.PagingToolbar({
			pageSize : parseInt(spagesize_combo.getValue()),
			store : customerstore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
		});
		var customerid = '';
		var customergrid=new Ext.grid.GridPanel({
			title:'客户统计列表',
			frame:true,
			region:'center',
			id:'productcustomercollect',
			store:customerstore,
			loadMask:true,
			cm :productLimitColumns,
			tbar:[ 
			       {  text:'休眠客户',
			    	  iconCls:'custSelectIconCss',
			    	  handler:function(){
			    	  dormantwind.show();
			    	  dormantstore.baseParams = {
			    				customerState:'休眠'
			    		};
			    		dormantstore.load({
			    			params : {
			    			start : 0,
			    			limit : parseInt(spagesize_combo.getValue())
			    		}});
			      }},
				{
					text:'详情 ',
					iconCls:'detailIconCss',
					handler:function(){
			    	  var selectRe = customergrid.getSelectionModel().getSelected();//选择一条数据
					if (selectRe == null
							|| selectRe == "undefined") {
						Ext.Msg.alert('提示','请选择一条记录!');
						return;
					} 
					customerid=selectRe.data.customerId;
					productdetailwind.show();
					detailstore.baseParams = {
							'customerid':customerid
					};
					detailstore.load({
						params : {
								start : 0,
								limit : parseInt(spagesize_combo.getValue())
				}});
				}
				},{
					text:'销售趋势图',
					iconCls :'custGroupMemIconCss',
					handler:function(){
					customerviewwind.show();
				}
				}],
	    	bbar:sbbar,
			loadMask : {
	        msg : '正在加载表格数据,请稍等...'
	    }
		});		
	
		//客户明细列表
		var detailColumns = new Ext.grid.ColumnModel([//grid中的列定义
			            	 new Ext.grid.RowNumberer(),
			            	 {header :'客户号',dataIndex:'customerId',id:"customerId",sortable : true,width:100},
			                 {header :'客户姓名 ',dataIndex:'customerName',id:"customerName",sortable : true,width:100},
			            	 {header :'客户年龄',dataIndex:'customerOld',id:"customerOld",sortable : true,width:80},
			            	 {header :'性别',dataIndex:'customerSex',id:"customerSex",sortable : true,width:80},
			            	 {header :'资产',dataIndex:'customerIncome',id:"customerIncome",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
			            	 {header :'产品号',dataIndex:'productId',id:"productId",sortable : true,width:100},
			                 {header :'产品名',dataIndex:'productName',id:"productName",sortable : true,width:100},
			            	 {header :'签约时间',dataIndex:'contractTime',id:"contractTime",sortable : true,width:80},
			            	 {header :'产品期数',dataIndex:'productPeriod',id:"productPeriod",sortable : true,width:80},
			            	 {header :'签约金额',dataIndex:'ContractMoney',id:"ContractMoney",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
			                 {header :'产品截止日期',dataIndex:'productTimeEnd',id:"productTimeEnd",sortable : true,width:100},
			                 {header :'客户状态',dataIndex:'customerState',id:"customerState",sortable : true,width:100}
			            		       	]);
	  var detailRecord = new Ext.data.Record.create([    
			              {name:'customerId',mapping:'CUSTOMER_ID'},
			              {name:'customerName',mapping:'CUSTOMER_NAME'},
			              {name:'customerOld',mapping:'CUSTOMER_OLD'},
			              {name:'customerSex',mapping:'CUSTOMER_SEX'},
			              {name:'customerIncome',mapping:'CUST_INCOME'},
			              {name:'productId',mapping:'PROD_ID'},	                         
			              {name:'productName',mapping:'PROD_NAME'},
			              {name:'contractTime',mapping:'CONT_TIME'},
			              {name:'productPeriod',mapping:'PROD_PERIOD'},
			              {name:'ContractMoney',mapping:'CONT_MONEY'},
			              {name:'productTimeEnd',mapping:'PROD_TIMEEND'},
			              {name:'customerState',mapping:'CUST_STATE'}            
			               ]);
	  
	  var detailReader = new Ext.data.JsonReader({//读取json数据的panel
		    totalProperty:'json.count',
		    root:'json.data'
		    },detailRecord);
		var detailstore=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({
		        url:basepath+'/productcustomerdetail.json',
		        method:'GET'
		        }),
			reader:detailReader
		});
		 var spagesize_combos = new Ext.form.ComboBox({
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
		// 机构列表的改变每页显示条数reload数据
		spagesize_combos.on("select", function(comboBox) {
			detailsbbar.pageSize = parseInt(spagesize_combos.getValue()),
			detailstore.reload({
				params : {
					start : 0,
					limit : parseInt(spagesize_combos.getValue())
				}
			});
		});
		// 机构列表分页工具栏
		var detailsbbar = new Ext.PagingToolbar({
			pageSize : parseInt(spagesize_combo.getValue()),
			store : detailstore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', spagesize_combos ]
		});
		
		//明细列表
	  var detailgrid=new Ext.grid.GridPanel({
			title:'客户明细列表',
			frame:true,
			region:'center',
			id:'customerdetail',
			store:detailstore,
			loadMask:true,
			cm :detailColumns,
			tbar:[ 
			      
			       new Com.yucheng.bob.ExpButton({
			       formPanel : 'dtailgrid',
				  iconCls:'exportIconCss',
				  id:'export',
				  url : basepath+'/productcustomerdetail.json?'
			  	})	
				],
	  	bbar:detailsbbar,
			loadMask : {
	      msg : '正在加载表格数据,请稍等...'
	  }
		});	
	  
	var productdetailwind = new Ext.Window({
		    title:'客户明细',
      		width:880,
      		height:420,
      		closeAction:'hide',
      		closable:true,
      		maximizable:true,
      		buttonAlign:'center',
      		border:false,
      		layout:'fit',
      		draggable:true,
      		collapsible:true,
      		titleCollapse:true,
      		items:[detailgrid],
      		buttons:[{
      			text:'返回',
      			handler:function(){
      			productdetailwind.hide();
      			
      		}
      		}]
	});
	//休眠客户列表
	
	
	var dormantstore=new Ext.data.Store({
	            			proxy:new Ext.data.HttpProxy({
	            		        url:basepath+'/productcustomerdormant.json',
	            		        method:'GET'
	            		        }),
	            			reader:Reader
	            		});
	var dormantgrid=new Ext.grid.GridPanel({
		title:'<span style="font-weight:normal">客户统计列表</span>',
		frame:true,
		region:'center',
		id:'productcustomercollect',
		store:dormantstore,
		loadMask:true,
		cm :productLimitColumns,
		tbar:[ 
		      new Com.yucheng.bob.ExpButton({
			       formPanel : 'dormantgrid',
				   iconCls:'exportIconCss',
				   id:'exportbtcorp',
				   url : basepath+'/productcustomerdormant.json?'
			  	})	
		      ],
    	bbar:sbbar,
		loadMask : {
        msg : '正在加载表格数据,请稍等...'
    }
	});	
	
	var dormantwind = new Ext.Window({
	    title:'休眠客户',
  		width:880,
  		height:420,
  		closeAction:'hide',
  		closable:true,
  		maximizable:true,
  		buttonAlign:'center',
  		border:false,
  		layout:'fit',
  		draggable:true,
  		collapsible:true,
  		titleCollapse:true,
  		items:[dormantgrid],
  		buttons:[{
  			text:'返回',
  			handler:function(){
  			dormantwind.hide();
  		}
  		}]
});
	var customerviewwind = new Ext.Window({
		title:'销售趋势图',
  		width:680,
  		height:420,
  		closeAction:'hide',
  		closable:true,
  		maximizable:true,
  		buttonAlign:'center',
  		border:false,
  		layout:'fit',
  		draggable:true,
  		collapsible:true,
  		titleCollapse:true,
         items:[
                {layout:'fit',
                	style:'padding:8px 0px 0px 0px',
             	//width : document.body.clientWidth-100,
				//height : document.body.clientHeight-50,
            
             	collapsible:true,
                
                 //height:date,
        		    html:'<iframe id="contentFrame1" name="content1" height="350" frameborder="no" width="100%" src=\"customerView.html\"/> scrolling="no"> </iframe>'}
                ],
                buttonAlign:'center',
                buttons:[{text:'返回',
                        handler: function(){
                	customerviewwind.hide();
                }}
                ]
});
	 
	//布局
	 var view = new Ext.Viewport({//页面展示
			layout : 'fit',
			frame : true,
			items : [{
			      layout:'border',
						items:[
						     search,
						{
							region:'center',
							layout:'fit',
							items:[customergrid]
						}
						]				
				
			
			
			}]
		});	

	 customerstore.load({
         params:{
             start:0,
             limit: parseInt(spagesize_combo.getValue())
         }
       });
});