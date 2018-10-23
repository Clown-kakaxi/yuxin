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
	
	var  continentGroupRow = [ 
	                          {
	        header : '',
	        colspan : 2,
	        align : 'center'
	    }, {
	        header : '本年初至今日',
	        colspan : 4,
	        align : 'center'
	    }, {
	        header : '本季初至今日',
	        colspan : 4,
	        align : 'center'
	    }, {
	        header : '本月初至今日',
	        colspan : 4,
	        align : 'center'
	    }];
	    var group = new Ext.ux.grid.ColumnHeaderGroup( {
	        rows : [ continentGroupRow ]
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
	}, {
		name : 'a6'
	}, {
		name : 'a7'
	}, {
		name : 'a8'
	}, {
		name : 'a9'
	}, {
		name : 'a10'
	}, {
		name : 'a11'
	}, {
		name : 'a12'
	}, {
		name : 'a13'
	}];

//	总买单、总卖单、总配息、总损益
	var columns = [{
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
		header : '总买单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a3',
		header : '总卖单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a4',
		header : '总配息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a5',
		header : '总损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a6',
		header : '总买单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a7',
		header : '总卖单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a8',
		header : '总配息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a9',
		header : '总损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a10',
		header : '总买单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a11',
		header : '总卖单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a12',
		header : '总配息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a13',
		header : '总损益',
		sortable : true,
		align:'right',
		width : 140
	}];
	var data = [
	            ['100','王一平','25,000','35,000', '5,000','5,500' ,'15,000','25,000', '4,000','4,500' ,'5,000','3,000', '500','600' ],
	            ['101','李晓','35,000','45,000', '6,000','6,500' ,'25,000','35,000', '5,000','5,500' ,'6,000','4,000', '600','700' ],
	            ['102','张贺','25,500','35,080', '5,040','5,520' ,'15,900','25,600', '4,700','4,580' ,'5,030','3,500', '400','690' ],
	            ['103','张晓璇','25,000','35,000', '5,000','5,500' ,'15,000','25,000', '4,000','4,500' ,'5,000','3,000', '500','600' ],
	            ['104','刘佳','25,000','35,000', '5,000','5,500' ,'15,000','25,000', '4,000','4,500' ,'5,000','3,000', '500','600' ]
	           
	           
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
		columns : columns,
        plugins : group,
        bbar : bbar// 分页工具栏
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
				title : "交易金额分析",
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
	
	var  continentGroupRow1 = [ 
	                          {
	        header : '',
	        colspan : 1,
	        align : 'center'
	    }, {
	        header : '本年初至今日',
	        colspan : 4,
	        align : 'center'
	    }, {
	        header : '本季初至今日',
	        colspan : 4,
	        align : 'center'
	    }, {
	        header : '本月初至今日',
	        colspan : 4,
	        align : 'center'
	    },{
	        header : '',
	        colspan : 2,
	        align : 'center'
	    }];
	    var datagroup = new Ext.ux.grid.ColumnHeaderGroup( {
	        rows : [ continentGroupRow1 ]
	    });
	
	var datafields = [{
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}, {
		name : 'a4'
	}, {
		name : 'a5'
	}, {
		name : 'a6'
	}, {
		name : 'a7'
	}, {
		name : 'a8'
	}, {
		name : 'a9'
	}, {
		name : 'a10'
	}, {
		name : 'a11'
	}, {
		name : 'a12'
	}, {
		name : 'a13'
	}, {
		name : 'a14'
	}, {
		name : 'a15'
	}];

//	总买单、总卖单、总配息、总损益
	var datacolumns = [{
		dataIndex : 'a1',
		header : '产品',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '总买单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a3',
		header : '总卖单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a4',
		header : '总配息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a5',
		header : '总损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a6',
		header : '总买单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a7',
		header : '总卖单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a8',
		header : '总配息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a9',
		header : '总损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a10',
		header : '总买单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a11',
		header : '总卖单',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a12',
		header : '总配息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a13',
		header : '总损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a14',
		header : '小计',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a15',
		header : '总计',
		sortable : true,
		align:'right',
		width : 140
	}];
	var datadata = [
	            [' 得利宝天添利B款','25,000','35,000', '5,000','5,500' ,'15,000','25,000', '4,000','4,500' ,'5,000','3,000', '500','600' ,'25,000','35,000'],
	            [' 得利宝天添利C款','35,000','45,000', '6,000','6,500' ,'25,000','35,000', '5,000','5,500' ,'6,000','4,000', '600','700','35,000','45,000' ],
	            [' 天添利A款至尊版','25,500','35,080', '5,040','5,520' ,'15,900','25,600', '4,700','4,580' ,'5,030','3,500', '400','690','25,500','35,080' ],
	            [' 天添利B款至尊版','25,000','35,000', '5,000','5,500' ,'15,000','25,000', '4,000','4,500' ,'5,000','3,000', '500','600' ,'25,000','35,000'],
	            [' 天添利B款','25,000','35,000', '5,000','5,500' ,'15,000','25,000', '4,000','4,500' ,'5,000','3,000', '500','600' ,'25,000','35,000']
	           
	           
	];



	var datastore = new Ext.data.ArrayStore({
		fields : datafields,
		data : datadata
	});

		 var dataInfo = new Ext.grid.GridPanel( {
			 region : 'center',
				frame : true,
				store : datastore,
				stripeRows : true,
				columns : datacolumns,
		        plugins : datagroup
	     });
		
	
	var chartInfo  = new Ext.Panel({
		frame : true,
		autoScroll : true,
		items:[{xtype:'portal',
			 items:[
						     {
						       	 columnWidth:1,
						         border:false,
						         autoHeight:true,
						         items:[{
						             collapsible:true,
						             layout:'fit',
						             title:'并展示单月总金额图',
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
					             title:'各月交易金额累计图',
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
						height : 200,
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