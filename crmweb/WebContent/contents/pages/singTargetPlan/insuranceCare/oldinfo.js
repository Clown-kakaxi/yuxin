var oldform = new Ext.form.FormPanel( {
		labelWidth : 100,
		title:'养老保障需求',
		labelAlign : 'right',
		frame : true,
//		heigth:270,
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
				fieldLabel : '通货膨胀率',
				name : 'q1',
				xtype : 'textfield', 
				value:'25%',
				anchor : '90%'
			},{
				fieldLabel : '预计退休年龄',
				name : 'q1',
				xtype : 'textfield', 
				value:'60',
				anchor : '90%'
			},{
				fieldLabel : '退休时期望的日常支出（月）',
				name : 'q1',
				xtype : 'textfield', 
				value:'3000',
				anchor : '90%'
			},{
				fieldLabel : '<font color="red">养老保障目标需求缺口</font>',
				name : 'q1',
				xtype : 'textfield', 
				value:'350,000',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '客户年龄',
				name : 'q3',
				value:'35',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '家族平均寿命',
				name : 'q3',
				value:'78',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '退休时期望的旅游支出',
				name : 'q3',
				value:'50,000',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '其他支出',
				name : 'q1',
				xtype : 'textfield', 
				value:'350,000',
				anchor : '90%'
			}]
		}]
	});