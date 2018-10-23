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
	}, {
		name : 'a6'
	}, {
		name : 'a7'
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
		header : '实现损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a4',
		header : '未实现损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a5',
		header : '派息',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a6',
		header : '资金增减',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a7',
		header : '市计价AUM',
		sortable : true,
		align:'right',
		width : 140
	}]);
	var data = [
	            ['100','王一平','25,000','25,000','0','400','400','25,400'],
	            ['101','李晓','55,000','50,000','5,000','300','600','55,000'],
	            ['102','张贺','20,000','15,200','5,000','200','500','20,200'],
	            ['103','张晓璇','10,000','8,000','2,000','700','-100','11,000'],
	            ['104','刘佳','25,000','25,000','0','400','200','25,000']
	           
	           
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
				title : "财富增长分析",
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
	var dataInfo  = new Ext.Panel({
		frame : true,
		items:[{
            collapsible:true,
            layout:'fit',
            style:'padding:0px 0px 0px 0px',
            html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src= \"chart/data1.html\"  " scrolling="no"> </iframe>'
        }]
	});
	//图表Window
	var infoWin = new Ext.Window(
			{
				layout : 'fit',
				modal : true,
				 width : 650,
				 height : 400,
				closeAction : 'hide',
				buttonAlign : 'center',
				border : false,
				 closable : true,
				items : [ {
					layout : 'border',
					items : [
							{
								region : 'center',
								id : 'center-panel',
								layout : 'fit',
								items:[dataInfo]
							} ]
				} ]
			});
	grid.on("rowdblclick",
			function(listPanel, rowIndex,
					event) {
				infoWin.show();
			});
	
});