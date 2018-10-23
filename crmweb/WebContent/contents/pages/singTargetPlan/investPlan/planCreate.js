var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});

var infoForm = new Ext.form.FormPanel( {
	labelWidth : 100,
	labelAlign : 'right',
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [{
		columnWidth : .5,
		layout : 'form',
		items : [new Com.yucheng.bcrm.common.CustomerQueryField({
			fieldLabel : '客户名称',
			labelWidth : 100,
			name : 'custName',
			custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
			custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
			singleSelected : true,// 单选复选标志
			editable : false,
			allowBlank : false,
			anchor : '90%',
			hiddenName : 'custId',
			value:'李晓丽'
		}),{
			fieldLabel : '客户性别',
			name : 'q1',
			xtype : 'textfield', 
			value:'女',
			anchor : '90%'
		},{
			store : typeStore ,
			xtype : 'combo',
			resizable : true,
			name : 'DBTABLE_ID',
			hiddenName : 'DBTABLE_ID',
			fieldLabel : '风险偏好',
			valueField : 'key',
			displayField : 'value',
			mode : 'local',
			value:'2',
			triggerAction : 'all',
			emptyText : '请选择',
			selectOnFocus : true,
			anchor : '90%'
		
	},{
		fieldLabel : '投资收益率',
		name : 'q4',
		xtype : 'textfield', 
		value:'20%',
		anchor : '90%'
	}]
	},{
		columnWidth : .5,
		layout : 'form',
		items : [{
			fieldLabel : '规划名称',
			name : 'q3',
			value:'李晓丽_投资规划',
			xtype : 'textfield', 
			anchor : '90%'
		},{
			fieldLabel : '客户联系方式',
			name : 'q4',
			xtype : 'textfield', 
			value:'13061015100',
			anchor : '90%'
		},{
		fieldLabel : '通货膨胀率',
		name : 'q4',
		xtype : 'textfield', 
		value:'20%',
		anchor : '90%'
	}]
	}]
});