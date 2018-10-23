var insuranceform  = new Ext.form.FormPanel( {
	labelWidth : 100,
	labelAlign : 'right',
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [{
		columnWidth : .5,
		layout : 'form',
		items : [{
		fieldLabel : '养老保障',
		name : 'q3',
		value:'30,000',
		xtype : 'textfield', 
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
		fieldLabel : '教育保障',
		name : 'q3',
		value:'30,000',
		xtype : 'textfield', 
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
		fieldLabel : '医疗保障',
		name : 'q3',
		value:'30,000',
		xtype : 'textfield', 
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '意外保障',
			name : 'q3',
			value:'30,000',
			xtype : 'textfield', 
			anchor : '90%'
		}]
	}]
});
var fields = [{
	name : 'a'
}, {
	name : 'a0'
}, {
	name : 'a1'
}, {
	name : 'a2'
}, {
	name : 'a3'
}];



var columns = new Ext.grid.ColumnModel([{
	dataIndex : 'a',
	header : '保险类型',
	sortable : true,
	width : 80
},{
	dataIndex : 'a0',
	header : '合理性',
	sortable : true,
	width : 70
}, {
	dataIndex : 'a1',
	header : '已有保险',
	sortable : true,
	width : 70
}, {
	dataIndex : 'a2',
	header : '系统建议保额',
	sortable : true,
	width : 70
}, {
	dataIndex : 'a3',
	header : '需求缺口',
	sortable : true,
	width : 70
}]);

var data = [
            ['养老保障','欠缺','10000','20000','10000'],
            ['教育保障','合理','500','500','0'],
            ['医疗保障','充足','2000','1500','0'],
            ['意外保障','充足','2000','1500','0']
];



var store = new Ext.data.ArrayStore({
	fields : fields,
	data : data
});

	var insuranceGrid = new Ext.grid.GridPanel({
		region : 'center',
//		title:"已有保障情况",
		heigth:200,
		frame : true,
		store : store,
		stripeRows : true,
		cm : columns
		
});
	