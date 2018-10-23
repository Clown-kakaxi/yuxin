Ext.onReady(function() {
	var fields1 = [ {
		name : 'b1'
	},{
		name : 'b2'
	}, {
		name : 'b3'
	}, {
		name : 'b4'
	}];

	//定义自动当前页行号
	var num1 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm1 = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	
	var columns1 = new Ext.grid.ColumnModel([num1,  {
		dataIndex : 'b1',
		header : '客户经理名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b2',
		header : '客户经理类型',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b3',
		header : '机构',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b4',
		header : '所辖客户',
		sortable : true,
		width : 350
	}]);
	var data1 = [
	             ['苏芮','主办客户经理','河北银行','河北金星日化用品厂，河北金星日化采购中心，河北金星日化产品原料厂'],
	             ['张强','协办客户经理','河北银行','河北金星日化分销公司，河北金星日化零售公司']
	];

	//每页显示条数下拉选择框
	var combo1 = new Ext.form.ComboBox({
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

	var store1 = new Ext.data.ArrayStore({
		fields : fields1,
		data : data1
	});

	var number1 = parseInt(combo1.getValue());

	//分页工具栏
	var bbar1 = new Ext.PagingToolbar({
		pageSize : number1,
		store : store1,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', combo1]
	});
	
	var memberList = new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		layout:'fit',
		store : store1,
		stripeRows : true,
//		sm : sm1,
		cm : columns1,
		buttonAlign : "center",
		bbar : bbar1
	});



var view=new Ext.Panel({
	 renderTo:oCustInfo.view_source,		 
	 height:document.body.scrollHeight-30,
	 width:document.body.scrollWidth-200,
	 layout : 'fit',
		frame : true,
		items : [{
			layout : 'border',
			items : [{
				region : 'center',
				id : 'center-panel',
				title : "授信信息",
				layout : 'fit',
				items : [ memberList ]
			}]
		}]
});

});
