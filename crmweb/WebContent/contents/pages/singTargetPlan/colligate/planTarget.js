var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});

var targetForm = new Ext.form.FormPanel( {
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
			fieldLabel : '固定资产',
			name : 'q1',
			xtype : 'textfield', 
			value:'50,000',
			anchor : '90%'
		},{
			fieldLabel : '年度可支配收入',
			name : 'q1',
			xtype : 'textfield', 
			value:'30,000',
			anchor : '90%'
		},{
		fieldLabel : '持续时间(年)',
		name : 'q4',
		xtype : 'textfield', 
		value:'15',
		anchor : '90%'
	},{
		fieldLabel : '<font color="red">总需求金额</font>',
		name : 'q4',
		xtype : 'textfield', 
		value:'500,000',
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '流动资产',
			name : 'q3',
			value:'40,000',
			xtype : 'textfield', 
			anchor : '90%'
		},{
			fieldLabel : '距离目标实现(年)',
			name : 'q4',
			xtype : 'textfield', 
			value:'20',
			anchor : '90%'
		}]
	}]
});