var Store =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ '飞机', '飞机' ], [ '火车', '火车' ], [ '私家车', '私家车' ], [ '公交车', '公交车' ], [ '轮渡', '轮渡' ]]
	});

var accidentform  = new Ext.form.FormPanel( {
	labelWidth : 100,
	title:'意外保障需求',
	labelAlign : 'right',
//	height:250,
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [{
		columnWidth : .5,
		layout : 'form',
		items : [{
			store : Store ,
			xtype : 'combo',
			resizable : true,
			name : 'DBTABLE_ID',
			fieldLabel : '出行经常选择的交通工具',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			value:'私家车',
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'
		
	},{
		fieldLabel : '家庭持续时间（年）',
		name : 'q3',
		value:'30',
		xtype : 'textfield', 
		anchor : '90%'
	},{
		fieldLabel : '如果发生意外风险，您需要为您的父母准备多少赡养费',
		name : 'q3',
		value:'300,000',
		xtype : 'textfield', 
		anchor : '90%'
	},{
		fieldLabel : '<font color="red">保障目标需求缺口</font>',
		name : 'q3',
		value:'200,000',
		xtype : 'textfield', 
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '家庭日常支出（月）',
			name : 'q3',
			value:'3000',
			xtype : 'textfield', 
			anchor : '90%'
		},{
			fieldLabel : '如果发生意外风险，您需要为您的孩子准备多少教育金',
			name : 'q3',
			value:'30,0000',
			xtype : 'textfield', 
			anchor : '90%'
		},{
			fieldLabel : '你目前的负债',
			name : 'q3',
			value:'0',
			xtype : 'textfield', 
			anchor : '90%'
		}]
	}]
});