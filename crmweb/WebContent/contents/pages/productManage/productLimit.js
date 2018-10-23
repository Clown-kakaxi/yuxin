Ext.onReady(function(){
	
	//查询模块
 var search =new Ext.form.FormPanel({
	 title:'条件查询',
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
			 columnWidth:.3,
			 items:[{
				 xtype:'textfield',
				 fieldLabel:'产品编号',
				 name:'prodcut_Id',
				 id:'product_Id',
				 anchor:'90%'
			 }]
		 },{
			 layout:'form',
			 columnWidth:.3,
			 items:[{
				 xtype:'textfield',
				 fieldLabel:'产品名',
				 name:'prodcut_name',
				 id:'product_name',
				 anchor:'90%'
			 }]
		 },{
			 layout:'form',
			 columnWidth:.3,
			 items:[{
				 columnWidth:.25,
					xtype:'combo',
					id:'balance',
					name:'balance',
					triggerAction:'all',
					anchor:'90%',
				//	lazyRender:true,
					fieldLabel:'余额',
					mode:'local',
					store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: ['value','displayText'],
			        data: [[1, '大于等于5万'], [2, '小于5万']]
			               }),
			       valueField:'value',
			       displayField:'displayText'
			 }]
		 }]
	 }],
	 buttons:[{
		 text:'查询',
		 handler:function(){
		 
	 }
	 },{
		 text:'重置',
		 handler:function(){
		 search.getForm().reset();
	 }
	 }]
 });
	var productLimitColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                new Ext.grid.RowNumberer(),
	                                {header :'产品编号',dataIndex:'productId',id:"productId",sortable : true,width:60},
	                                {header :'产品类型 ',dataIndex:'productType',id:"productType",sortable : true,width:100},
	                                {header :'产品名',dataIndex:'productName',id:"productName",sortable : true,width:100},
	                                {header :'产品发布时间',dataIndex:'productStarttime',id:"productStarttime",sortable : true,width:100},
	                                {header :'产品截止时间',dataIndex:'productEndtime',id:"productEndtime",sortable : true,width:100},
	                                {header :'规划理财金额',dataIndex:'planMoney',id:"planMoney",align:'right',renderer: money('0,000.00'),sortable : true,width:120},
	                                {header :'余额',dataIndex:'productBalance',id:"marketChannle",align:'right',renderer: money('0,000.00'),sortable : true,width:120},                                
	                                {header :'客户数',dataIndex:'customerNumber',id:"customerNumber",align:'right',sortable : true,width:100},
	                                {header :'客户购买渠道',dataIndex:'marketChannle',id:"marketChannle",sortable : true,width:150},
	                                {header :'产品状态 ',dataIndex:'productstate',id:"productstate",sortable : true,width:160},
	                                {header :'产品描述',dataIndex:'productdetail',id:"productdetail",sortable : true,width:180}
	                                               	]);
	var store=new Ext.data.Store({
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.ArrayReader({},[{name:'productId'},{name:'productType'},{name:'productName'},
		                                    {name:'productStarttime'},{name:'productEndtime'},{name:'planMoney'},
		                                    {name:'productBalance'},{name:'customerNumber'},
		                                    {name:'marketChannle'},{name:'productstate'},{name:'productdetail'}])
		
		
	});
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

	// 改变每页显示条数reload数据
	spagesize_combo.on("select", function(comboBox) {
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		store.reload({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
	});
	// 分页工具栏
	var sbbar = new Ext.PagingToolbar({
		pageSize : parseInt(spagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});

	//***********************
	store.load();
//	store.load({params:{		
//		start:0,
//		limit: parseInt(spagesize_combo.getValue())
////			'condition':'{"CATL_CODE":"A1"}'
//	}});
	var detailsearch=new Ext.form.FormPanel({
		 title:'查询模块',
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
				 columnWidth:.25,
				 layout:'form',
				 items:[{
					 xtype:'combo',
						id:'budyPeriod1',
						name:'budyPeriod1',
						triggerAction:'all',
						anchor:'99%',
					//	lazyRender:true,
						fieldLabel:'购买渠道',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 0,
				        fields: ['value','displayText'],
				        data: [[1, '柜台'], [2, '网银']]
				               }),
				       valueField:'value',
				       displayField:'displayText'
				 }]
			 },{  columnWidth:.25,
				 layout:'form',
				 items:[{
					 xtype:'combo',
						id:'moneySource1',
						name:'moneySource1',
						triggerAction:'all',
						anchor:'99%',
					//	lazyRender:true,
						fieldLabel:'资金来源',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 0,
				        fields: ['value','displayText'],
				        data: [[1, '行内储蓄'], [2, '跨行转账'],[3,'现金']]
				               }),
				       valueField:'value',
				       displayField:'displayText'
				 }] 
			 },{
				 columnWidth:.25,
				 layout:'form',
				 items:[{
				 xtype:'datefield',
				 fieldLabel:'购买日期从',
				 name:'budyTimeStart',
				 id:'budyTimeStart',
				 anchor:'99%'
				 }] 
			 },{
				 columnWidth:.25,
				 layout:'form',
				 items:[{
				 xtype:'datefield',
				 fieldLabel:'到',
				 name:'budyTimeEnd',
				 id:'budyTimeEnd',
				 anchor:'99%'
				 }] 
			 }]
		 }],
		 buttons:[{
			 text:'查询',
			 handler:function(){
			 
		 }
		 },{
			 text:'重置',
			 handler:function(){
			 detailsearch.getForm().reset();
		 }
		 }]
		
	});
	var detailColumns = new Ext.grid.ColumnModel([
	              	                            new Ext.grid.RowNumberer(), 
	              	                            {header :'客户经理ID',dataIndex:'managerId',id:"managerId",sortable : true},
	              	                            {header :'客户经理名',dataIndex:'managerName',id:"managerName",sortable : true},
	              	                            {header :'产品编号',dataIndex:'productId',id:"productId",sortable : true},
	              	                            {header :'产品类型 ',dataIndex:'productType',id:"productType",sortable : true},
	              	                 	        {header :'产品名',dataIndex:'productName',id:"productName",sortable : true},
	              	                 	        {header :'产品期数',dataIndex:'productPeriod',id:"productPeriod",sortable : true},
	              	                 	        {header :'产品发布时间',dataIndex:'productStarttime',id:"productStarttime",sortable : true},
	              	                 	        {header :'产品截止时间',dataIndex:'productEndtime',id:"productEndtime",sortable : true},
	              	                 	        {header :'客户名',dataIndex:'customerName',id:"customerName",sortable : true},
	              	                 	        {header :'购买时间',dataIndex:'buyTime',id:"buyTime",sortable : true},
	              	                 	        {header :'投资金额',dataIndex:'investMoney',id:"investMoney",align:'right',renderer: money('0,000.00'),sortable : true},
	              	                 	        {header :'资金来源',dataIndex:'moneySource',id:"moneySource",sortable : true},
	              	                 	        {header :'购买渠道',dataIndex:'buyPanel',id:"buyPanel",sortable : true},	
	              	                 	        {header :'客户证件类型',dataIndex:'customerPaper',id:"customerPaper",sortable : true},
	              	                 	        {header :'证件号',dataIndex:'paperNumber',id:"paperNumber",sortable : true},
	              	                 	        {header :'产品描述',dataIndex:'productdetail',id:"productdetail",sortable : true}
	              	                 	                                ]);
	              	                 	var detailstore=new Ext.data.Store({
	              	                 		proxy:new Ext.data.MemoryProxy(detaildata),
	              	                 		reader:new Ext.data.ArrayReader({},[{name:'managerId'},{name:'managerName'},{name:'productId'},
	              	                 		                                    {name:'productType'},{name:'productName'},{name:'productPeriod'},
	              	                 		                                    {name:'productStarttime'},{name:'productEndtime'},{name:'customerName'},
	              	                 		                                    {name:'buyTime'},{name:'investMoney'},{name:'moneySource'},
	              	                 		                                    {name:'buyPanel'},{name:'customerPaper'},{name:'paperNumber'},{name:'productdetail'}])
	              	                 		
	              	                 		
	              	                 	});
	              	                 	detailstore.load();
	 	var detailgrid=new Ext.grid.GridPanel({
	              		title:'明细列表',
	              		frame:true,
	              		layout:'fit',
	              		region:'center',
	              		id:'detailgrid',
	              		store:detailstore,
	              		loadMask:true,
	              		tbar:[ 
	       			       new Com.yucheng.bob.ExpButton({
	       				  formPanel : 'gridcorp',
	       				  iconCls:'exportIconCss',
	       				  id:'exportbtcorp',
	       				  url : basepath+'/productlimit.json?'
	       			  	})	
	       			       ],
	              		cm :detailColumns,
	              		bbar:sbbar,
	              		loadMask : {
	                      msg : '正在加载表格数据,请稍等...'
	                  }

	              	});
	              	//产品明细window
	  var productdetailwind=new Ext.Window({
	              		title:'产品明细列表',
	              		width:880,
	              		height:420,
	              		closeAction:'hide',
	              		closable:true,
	              		maximizable:true,
	              		buttonAlign:'center',
	              		border:false,
	              		layout:'border',
	              		draggable:true,
	              		collapsible:true,
	              		titleCollapse:true,
	              		items:[detailgrid,detailsearch],
	              		buttons:[{
	              			text:'返回',
	              			handler:function(){
	              			productdetailwind.hide();
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
	                    		    html:'<iframe id="contentFrame1" name="content1" height="350" frameborder="no" width="100%" src=\"marketView.html\"/> scrolling="no"> </iframe>'}
	                            ],
	                            buttonAlign:'center',
	                            buttons:[{text:'返回',
	                                    handler: function(){
	                            	customerviewwind.hide();
	                            }}
	                            ]
	            });
	var grid=new Ext.grid.GridPanel({
		title:'额度管理列表',
		frame:true,
		region:'center',
		id:'productInfoGrid',
		store:store,
		loadMask:true,
		cm :productLimitColumns,
		tbar:[{text:'产品明细',
			iconCls:'detailIconCss',
			handler:function(){
			var selectRe = grid.getSelectionModel().getSelections()[0];

			if (selectRe == null
					|| selectRe == "undefined") {
				Ext.Msg.alert('提示','请选择一条记录!');
				return;
			} 
				productdetailwind.show();
			}},{
				text:'销售视图',
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
	//布局
	var view = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items:[{
		layout:'border',
		items:[search,grid]
		       }]
	});
});