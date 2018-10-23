Ext.onReady(function() {
	var fields1 = [ {
		name : 'b1'
	},{
		name : 'b2'
	}, {
		name : 'b3'
	}, {
		name : 'b4'
	}, {
		name : 'b5'
	}, {
		name : 'b6'
	}, {
		name : 'b7'
	}, {
		name : 'b8'
	}, {
		name : 'b9'
	}, {
		name : 'b10'
	}, {
		name : 'b11'
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
		header : '客户名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b2',
		header : '授信批复文号',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b3',
		header : '币种',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b4',
		header : '授信总敞口',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b5',
		header : '额度项下合同最长期限',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b6',
		header : '额度有效期',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b7',
		header : '额度生效日期',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b8',
		header : '剩余额度',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b9',
		header : '额度到期日',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b10',
		header : '保证金比例',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b11',
		header : '主要担保方式',
		sortable : true,
		width : 140
	}]);
	var data1 = [
	            [ '河北金星日化用品厂','PFX000198','人民币' ,'1000000','5','6','2012-09-18','950000','2018-09-18','30%','抵押'],
	            [ '河北金星日化采购中心','PFX000198','人民币' ,'1000000','5','6','2012-09-18','950000','2018-09-18','30%','抵押'],
	            [ '河北金星日化产品原料厂','PFX000198','人民币' ,'1000000','5','6','2012-09-18','950000','2018-09-18','30%','抵押'],
	            [ '河北金星日化分销公司','PFX000198','人民币' ,'1000000','5','6','2012-09-18','950000','2018-09-18','30%','抵押'],
	            [ '河北金星日化零售公司','PFX000198','人民币' ,'1000000','5','6','2012-09-18','950000','2018-09-18','30%','抵押']
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
