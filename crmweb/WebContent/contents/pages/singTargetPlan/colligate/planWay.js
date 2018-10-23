var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});

var wayForm = new Ext.form.FormPanel( {
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
			fieldLabel : '客户当前资产',
			name : 'q1',
			xtype : 'textfield', 
			value:'50,000',
			anchor : '90%'
		},{
			fieldLabel : '每年投入',
			name : 'q1',
			xtype : 'textfield', 
			value:'30,000',
			anchor : '90%'
		},{
		fieldLabel : '<font color="red">缺口资金</font>',
		name : 'q4',
		xtype : 'textfield', 
		value:'500,000',
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '当前一次性投入',
			name : 'q3',
			value:'40,000',
			xtype : 'textfield', 
			anchor : '90%'
		},{
			fieldLabel : '计划投资时间(年)',
			name : 'q4',
			xtype : 'textfield', 
			value:'20',
			anchor : '90%'
		}]
	},{
		columnWidth : 1,
		layout : 'form',
		items : [{
            xtype : 'fieldset',
            title : '投资方案',
            isFormField : true,
            autoHeight : true,
            layout : 'column',
            items : [{
						columnWidth : .60,
						xtype : 'radiogroup',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '一次性投入',
							inputValue:'1',
							name : 'cb-auto-5'
						}, {
							boxLabel : '每年投入',
							inputValue:'2',
							name : 'cb-auto-5'
						} ]
            }, {
				columnWidth : .40,
				layout : 'form',
				labelWidth : 35,
				items : [ {
					xtype : 'textfield',
					value:' ',
					fieldLabel : '资金（元）',
					anchor : '60%'
				} ]
			}
					]
        },{
			fieldLabel : '备注',
			name : 'q4',
			xtype : 'textarea', 
			value:'XXXXXXXXXXX',
			anchor : '95%'
		}]
	}]
});