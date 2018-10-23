Ext.onReady(function() {
	
	//表格部分
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
	}];



	var columns = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '日期',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '2011',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '2012',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a3',
		header : '2013',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a4',
		header : '2014',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a5',
		header : '2015',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a6',
		header : '2016',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a7',
		header : '......',
		sortable : true,
		width : 140
	}]);

	var data = [
	            ['期末资产余额','10,900.00','11,881.00','12,950.29','14,115.82','15,386.24','16,771.00','......'],
	            ['投入资金','10,000.00','0','0','0','0','0','......'],
	            ['教育支出','10,000.00','0','0','0','0','0','......'],
	            ['生活支出','0','0','0','0','0','0','......'],
	            ['投资收益','900','981','1,069.29','1,165.53','1,270.42','1,384.76','1,384.76']
	];



	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});

		var infoGrid = new Ext.grid.GridPanel({
			region : 'center',
			frame : true,
			store : store,
			stripeRows : true,
			cm : columns
			
	});
		
	//页面布局
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [{
				xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	           	 columnWidth:.5,
	             border:false,
	             autoHeight:true,
	             items:[{
	                 title: '养老金缺口',
	                 collapsible:true,
	                 layout:'fit',
	                 style:'padding:0px 0px 0px 0px',
	                 html:'<iframe id="contentFrame" name="content" height="240" frameborder="no" width="100%" src=\"chart/a.html\"  scrolling="no"> </iframe>'
	             }]
	     },{
	     	 columnWidth:.5,
	             border:false,
	             autoHeight:true,
	             items:[{
	                 title: '现金流图',
	                 collapsible:true,
	                 layout:'fit',
	                 style:'padding:0px 0px 0px 0px',
	                 html:'<iframe id="contentFrame" name="content" height="240" frameborder="no" width="100%" src=\"chart/b.html\" scrolling="no"> </iframe>'
	       }]},
	       {
	            	 columnWidth:1,
		                border:false,
		              height:200,
		              layout:'fit',
		                items:[{
		                    collapsible:true,
		                    layout:'fit',
		                    style:'padding:0px 0px 0px 0px',
		                    items:[infoGrid]
		    }]}]
			}
		]
		} ]
	});
});