var ifStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ '是', '是' ], [ '否', '否' ]]
	});

var healthform  = new Ext.form.FormPanel( {
	labelWidth : 100,
	title:'医疗保障需求',
	labelAlign : 'right',
//	height:200,
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [{
		columnWidth : .5,
		layout : 'form',
		items : [{
			store : ifStore ,
			xtype : 'combo',
			resizable : true,
			name : 'DBTABLE_ID',
			fieldLabel : '你是否有医保',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			value:'是',
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'
		
	},{
		fieldLabel : '发生重大疾病时，您希望的保险保障是',
		name : 'q3',
		value:'30,000',
		xtype : 'textfield', 
		anchor : '90%'
	},{
		fieldLabel : '<font color="red">医疗保障目标需求缺口</font>',
		name : 'q3',
		value:'20,000',
		xtype : 'textfield', 
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '因病住院时,你希望获得医疗补助',
			name : 'q3',
			value:'3000',
			xtype : 'textfield', 
			anchor : '90%'
		}]
	}]
});