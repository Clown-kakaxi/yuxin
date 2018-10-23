var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});

var investForm = new Ext.form.FormPanel( {
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
			fieldLabel : '当前一次性投入',
			name : 'q1',
			xtype : 'textfield', 
			value:'10,000',
			anchor : '90%'
		},{
			fieldLabel : '计划投资时间(年)',
			name : 'q1',
			xtype : 'textfield', 
			value:'10',
			anchor : '90%'
		}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '每年投入',
			name : 'q3',
			value:'5，000',
			xtype : 'textfield', 
			anchor : '90%'
		}]
	},{
		columnWidth : 1,
		layout : 'form',
		items : [{
			fieldLabel : '备注',
			name : 'q3',
			xtype : 'textarea', 
			anchor : '95%'
		}]
	}]
});