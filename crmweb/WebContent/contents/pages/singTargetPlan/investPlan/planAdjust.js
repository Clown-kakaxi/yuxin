var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});

var adjustForm = new Ext.form.FormPanel( {
	labelWidth : 100,
	labelAlign : 'right',
	height:160,
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '股票市场方向',
			name : 'q1',
			xtype : 'textfield', 
			value:'25%',
			anchor : '90%'
		},{
			fieldLabel : '货币市场方向',
			name : 'q1',
			xtype : 'textfield', 
			value:'25%',
			anchor : '90%'
		}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '债券市场方向',
			name : 'q3',
			value:'25%',
			xtype : 'textfield', 
			anchor : '90%'
		},{
			fieldLabel : '其他市场方向',
			name : 'q3',
			value:'25%',
			xtype : 'textfield', 
			anchor : '90%'
		}]
	},{
		columnWidth : 1,
		layout : 'form',
		items : [{
			fieldLabel : '调整原因',
			name : 'q3',
			value:'XXXXXXXXXXXXXXXX',
			xtype : 'textarea', 
			anchor : '95%'
		}]
	}]
});