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
				fieldLabel : '客户经理编号',
				name : 'q1',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '客户经理名称',
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
	}, {
		name : 'a6'
	}, {
		name : 'a7'
	}, {
		name : 'a8'
	}];

	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	
	var columns = new Ext.grid.ColumnModel([num, sm,{
		dataIndex : 'a0',
		header : '客户经理编号',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '客户经理姓名',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '投资总额',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a3',
		header : '本月余额',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a4',
		header : '本月赢利投资总额',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a5',
		header : '本月赢利额度',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a6',
		header : '本月亏损投资总额',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a7',
		header : '本月亏损额度',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a8',
		header : '本月总计赢亏额度',
		sortable : true,
		align:'right',
		width : 140
	}]);
	var data = [
	            
	            ['khjl001','周一','25,000','25,400','20,000','400','500','0','400'],
	            ['khjl101','宋盈盈','55,000','55,000','35,000','400','20,000','400','0'],
	            ['kejl102','赵晓秀','20,000','20,200','10,000','600','10,000','400','200'],
	            ['khjl103','刘佳妮','10,000','11,000','10,000','1,000','0','0','1,000'],
	            ['khjl104','宋潇潇','25,000','25,400','20,000','400','500','0','400']
	           
	           
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
				title : "客户经理净值分析",
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
	
	
});