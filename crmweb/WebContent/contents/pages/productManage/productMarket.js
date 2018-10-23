Ext.onReady(function(){
	var search = new Ext.form.FormPanel({
		title:'销售查询',
		frame:true,
		height:100,
		labelWidth:70,
		region:'north',
		items:[{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth:.25,
				items:[{
					xtype:'textfield',
					fieldLabel:'客户经理ID',
					name:'managerId1',
					id:'managerId1',
					anchor:'90%'
				}]
			},{
				layout:'form',
				columnWidth:.25,
				items:[{
					xtype:'textfield',
					fieldLabel:'客户经理名',
					name:'managerName1',
					id:'managerName1',
					anchor:'90%'
				}]
			},{
				layout:'form',
				columnWidth:.25,
				items:[{
					xtype:'textfield',
					fieldLabel:'产品号',
					name:'productId1',
					id:'productId1',
					anchor:'90%'
				}]
			},{
				layout:'form',
				columnWidth:.25,
				items:[{
					xtype:'textfield',
					fieldLabel:'产品名',
					name:'productName1',
					id:'productName1',
					anchor:'90%'
				}]
			}]
		}],
		buttonAlign:'center',
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
	var productMarketColumns = new Ext.grid.ColumnModel([
                                    new Ext.grid.RowNumberer(), 
                                    {header :'客户经理ID',dataIndex:'managerId',id:"managerId",sortable : true},
                                    {header :'客户经理名',dataIndex:'managerName',id:"managerName",sortable : true},
                                    {header :'产品编号',dataIndex:'productId',id:"productId",sortable : true},
                                    {header :'产品类型 ',dataIndex:'productType',id:"productType",sortable : true},
	                                {header :'产品名',dataIndex:'productName',id:"productName",sortable : true},
	                                {header :'产品发布时间',dataIndex:'productStarttime',id:"productStarttime",sortable : true},
	                                {header :'产品截止时间',dataIndex:'productEndtime',id:"productEndtime",sortable : true},
	                                {header :'月销售户数',dataIndex:'monthSell',id:"monthSell",sortable : true},
	                                {header :'月销售金额',dataIndex:'monthMoney',id:"monthMoney",align:'right',renderer: money('0,000.00'),sortable : true},
	                                {header :'季销售户数',dataIndex:'quarterSell',id:"quarterSell",sortable : true},
	                                {header :'季销售金额',dataIndex:'quarterMoney',id:"quarterMoney",align:'right',renderer: money('0,000.00'),sortable : true},
	                                {header :'年销售户数',dataIndex:'yearSell',id:"yearSell",sortable : true},
	                                {header :'年销售金额',dataIndex:'yearMoney',id:"yearMoney",align:'right',renderer: money('0,000.00'),sortable : true},	                                
	                                {header :'产品状态 ',dataIndex:'productstate',id:"productstate",sortable : true},
	                                {header :'产品描述',dataIndex:'productdetail',id:"productdetail",sortable : true}
	                                                    ]);
	var store=new Ext.data.Store({
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.ArrayReader({},[{name:'managerId'},{name:'managerName'},{name:'productId'},
		                                    {name:'productType'},{name:'productName'},{name:'productStarttime'},
		                                    {name:'productEndtime'},{name:'monthSell'},{name:'monthMoney'},
		                                    {name:'quarterSell'},{name:'quarterMoney'},{name:'yearSell'},
		                                    {name:'yearMoney'},{name:'productstate'},{name:'productdetail'}])
		
		
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
	
	var detailsearch = new Ext.form.FormPanel({
		title:'销售明细查询',
		frame:true,
		height:120,
		labelWidth:90,
		labelAlign:'right',
		region:'north',
		layout:'column',
		items:[{layout:'form',
					columnWidth:.3,
					items:[{
						xtype:'datefield',
						fieldLabel:'销售日期从',
						format:'Y年m月d日',
						name:'marketStart',
						id:'marketStart1',
						anchor:'99%'
					},{
						    xtype:'combo',
							id:'customerOld1',
							name:'customerOld1',
							triggerAction:'all',
							anchor:'99%',
						//	lazyRender:true,
							fieldLabel:'年龄层次',
							mode:'local',
							store: new Ext.data.ArrayStore({
					        id: 0,
					        fields: ['value','displayText'],
					        data: [[1, '25岁以下'],[2, '25岁到30岁'],[3,'30岁到35岁'],[4,'35岁到40岁'],[5,'40岁到45岁'],[6,'45岁以上']]
					               }),
					       valueField:'value',
					       displayField:'displayText'
					}]
		},{
			layout:'form',
			columnWidth:.3,
			items:[{
				xtype:'datefield',
				fieldLabel:'到',
				format:'Y年m月d日',
				name:'marketEnd',
				id:'marketEnd1',
				anchor:'99%'
			},{
				xtype:'combo',
				id:'contractAmount1',
				name:'contractAmount1',
				triggerAction:'all',
				anchor:'99%',
			//	lazyRender:true,
				fieldLabel:'单笔签约金额',
				mode:'local',
				store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: ['value','displayText'],
		        data: [[1, '5万以下'],[2, '5万到10万'],[3,'10万到20万'],[4,'20万到30万'],[5,'30万到40万'],[6,'40万以上']]
		               }),
		       valueField:'value',
		       displayField:'displayText'
			}]
		},{layout:'form',
			columnWidth:.3,
			items:[{
				xtype:'combo',
				id:'marketArea',
				name:'marketArea',
				triggerAction:'all',
				anchor:'99%',
			//	lazyRender:true,
				fieldLabel:'区域',
				mode:'local',
				store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: ['value','displayText'],
		        data: [[1, '北京'],[2, '杭州'],[3,'深圳']]
		               }),
		       valueField:'value',
		       displayField:'displayText'
			}]}],
		buttonAlign:'center',
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
	                 	        {header :'客户年龄',dataIndex:'customerOld',id:"customerOld",sortable : true},
	                 	        {header :'购买时间',dataIndex:'buyTime',id:"buyTime",sortable : true},
	                 	        {header :'购买区域',dataIndex:'buyArea',id:"buyArea",sortable : true},
	                 	        {header :'投资金额',dataIndex:'investMoney',id:"investMoney",align:'right',renderer: money('0,000.00'),sortable : true},
	                 	        {header :'购买渠道',dataIndex:'buyPanel',id:"buyPanel",sortable : true},	
	                 	        {header :'资金来源',dataIndex:'moneySource',id:"moneySource",sortable : true},
	                 	        {header :'客户证件类型',dataIndex:'customerPaper',id:"customerPaper",sortable : true},
	                 	        {header :'证件号',dataIndex:'paperNumber',id:"paperNumber",sortable : true},
	                 	        {header :'产品状态 ',dataIndex:'productstate',id:"productstate",sortable : true},
	                 	        {header :'产品描述',dataIndex:'productdetail',id:"productdetail",sortable : true}
	                 	                                ]);
	                 	var detailstore=new Ext.data.Store({
	                 		proxy:new Ext.data.MemoryProxy(detaildata),
	                 		reader:new Ext.data.ArrayReader({},[{name:'managerId'},{name:'managerName'},{name:'productId'},
	                 		                                    {name:'productType'},{name:'productName'},{name:'productPeriod'},
	                 		                                    {name:'productStarttime'},
	                 		                                    {name:'productEndtime'},{name:'customerName'},{name:'customerOld'},{name:'buyTime'},
	                 		                                    {name:'buyArea'},{name:'investMoney'},{name:'buyPanel'},{name:'moneySource'},
	                 		                                    {name:'customerPaper'},{name:'paperNumber'},{name:'productstate'},{name:'productdetail'}])
	                 		
	                 		
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
		cm :detailColumns,
		tbar:[ 
			       new Com.yucheng.bob.ExpButton({
				  formPanel : 'gridcorp',
				  iconCls:'exportIconCss',
				  id:'exportbtcorp',
				  url : basepath+'/productMarket.json?'
			  	})	
			       ],
		bbar:sbbar,
		loadMask : {
        msg : '正在加载表格数据,请稍等...'
    }

	});
	//产品明细window
	var productdetailwind=new Ext.Window({
		title:'产品明细列表',
		width:800,
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
		items:[detailsearch,detailgrid],
		buttons:[{
			text:'返回',
			handler:function(){
			productdetailwind.hide();
		}
		}]
	});
	
	var grid=new Ext.grid.GridPanel({
		title:'销售列表',
		frame:true,
		region:'center',
		id:'Grid',
		store:store,
		loadMask:true,
		cm :productMarketColumns,
		tbar:[{
			text:'销售明细',
			iconCls:'detailIconCss',
			handler:function(){
			var selectRe = grid.getSelectionModel().getSelections()[0];

			if (selectRe == null
					|| selectRe == "undefined") {
				Ext.Msg.alert('提示','请选择一条记录!');
				return;
			} 
				productdetailwind.show();
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