Ext.onReady(function() {
	
	var qfrom = new Ext.form.FormPanel( {
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
				fieldLabel : '客户编号',
				name : 'q1',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '客户名称',
				name : 'q1',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '查询',
			handler : function() {}},
			{
				text : '重置',
			     handler : function() {
			    	 qfrom.form.reset();
						}
			}]
});
	
	var fields = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}, {
		name : 'a4'
	}, {
		name : 'a5'
	}];

	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var columns = new Ext.grid.ColumnModel([num, sm,{
		dataIndex : 'a0',
		header : '客户编号',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '客户姓名',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '投资金额',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a3',
		header : '投资比例',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a4',
		header : '已实现损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a5',
		header : '报酬率贡献度',
		sortable : true,
		align:'right',
		width : 140
	}]);
	var data = [
	            ['100','王一平','25,000','15%', '25,000','25,000' ],
	            ['101','李晓','55,000','20%', '55,000','55,000' ],
	            ['102','张贺','20,000','50%', '20,000','20,000' ],
	            ['103','张晓璇','10,000','35%', '10,000','10,000' ],
	            ['104','刘佳','25,000','10%', '25,000','25,000' ]
	           
	           
	];



	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});
	var combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['value', 'text'],
					data : [[10, '10条/页'], [20, '20条/页'],
							[50, '50条/页'], [100, '100条/页'],
							[250, '250条/页'], [500, '500条/页']]
				}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var number = parseInt(combo.getValue());

	//分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', combo]
	});
	var grid = new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		store : store,
		stripeRows : true,
		sm:sm,
		cm : columns,
		bbar:bbar
});
	
	//页面布局
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [{
				region : 'north',
				id : 'north-panel',
				title : "投资标的分析",
				height : 100,
				layout : 'fit',
				items : [qfrom]
			},{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [grid]
					}
			]
		} ]
	});
	
	    

	    var datafields = [ {
			name : 'a0'
		}, {
			name : 'a1'
		}, {
			name : 'a2'
		}, {
			name : 'a3'
		}, {
			name : 'a4'
		}];



	//  投资金额、投资比例、已实现损益及报酬率贡献度
		var datacolumns = new Ext.grid.ColumnModel( [{
			dataIndex : 'a0',
			header : '时间',
			sortable : true,
			width : 140
		}, {
			dataIndex : 'a1',
			header : '投资金额',
			sortable : true,
			width : 120
		}, {
			dataIndex : 'a2',
			header : '投资比例',
			sortable : true,
			align:'right',
			width : 120
		}, {
			dataIndex : 'a3',
			header : '已实现损益',
			sortable : true,
			align:'right',
			width :120
		}, {
			dataIndex : 'a4',
			header : '报酬率贡献度',
			sortable : true,
			align:'right',
			width : 120
		}]);
		
		var datadata = [
		                ['本季','25,000','15%', '25,000','25,000' ],
			            ['年初至今','55,000','20%', '55,000','55,000' ],
			            ['去年整年','75,000','50%', '75,000','75,000' ]
		           
		           
		];
		var datastore = new Ext.data.ArrayStore({
			fields : datafields,
			data : datadata
		});
		 var dataInfo = new Ext.grid.GridPanel( {
	         store : datastore,
	         height:120,
	         stripeRows : true,
	         cm : datacolumns
	     });
		
	
	var chartInfo  = new Ext.Panel({
		frame : true,
		autoScroll : true,
		items:[{xtype:'portal',
			 items:[{
			           	 columnWidth:1,
			             border:false,
			             autoHeight:true,
			                 items:[{layout:'column',
			                	 title:"依资产类别分类图",
			                	 items:[{
								       	 columnWidth:.5,
								         border:false,
								         autoHeight:true,
								         items:[{
					                		 collapsible:true,
								             layout:'fit',
								             style:'padding:0px 0px 0px 0px',
								             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src= \"chart/data1.html\"  " scrolling="no"> </iframe>'
								         }]}
								         ,{
									       	 columnWidth:.5,
									         border:false,
									         autoHeight:true,
									         items:[{
					                		 collapsible:true,
								             layout:'fit',
								             style:'padding:0px 0px 0px 0px',
								             html:'<iframe id="contentFrameClo" name="contentClo" height="290" frameborder="no" width="100%" src= \"chart/data2.html\"  " scrolling="no"> </iframe>'
									         }]
								         }]
						     } ]},
						     {
						       	 columnWidth:1,
						         border:false,
						         autoHeight:true,
						         items:[{
						             collapsible:true,
						             layout:'fit',
						             title:'依投资标的图',
						             style:'padding:0px 0px 0px 0px',
						             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src= \"chart/data3.html\"  " scrolling="no"> </iframe>'
						         }]
						 },{
					       	 columnWidth:1,
					         border:false,
					         autoHeight:true,
					         items:[{
					             collapsible:true,
					             layout:'fit',
					             title:'依投资区域图',
					             style:'padding:0px 0px 0px 0px',
					             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src= \"chart/data4.html\"  " scrolling="no"> </iframe>'
					         }]
					 }]
		         }]
		
	});
	
	//图表Window
	var infoWin = new Ext.Window(
			{
				layout : 'fit',
				modal : true,
				autoScroll : true,
				width : 700,
				height : 500,
				closeAction : 'hide',
				buttonAlign : 'center',
				border : false,
				closable : true,
				items : [ {
					layout : 'border',
					items : [
					         {
						region : 'north',
						layout : 'fit',
						height : 120,
						items:[dataInfo]
					},
					{
								region : 'center',
								layout : 'fit',
								items:[chartInfo]
							}
					]
				} ]
			});
	grid.on("rowdblclick",
			function(listPanel, rowIndex,
					event) {
				infoWin.show();
			});
	
});