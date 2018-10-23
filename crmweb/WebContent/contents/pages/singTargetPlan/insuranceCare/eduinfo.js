	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ '幼儿园', '幼儿园' ], [ '小学', '小学' ],['初中','初中'], ['高中','高中'],['大学','大学'],['研究生','研究生']]
	});
	
	var ifStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ '是', '是' ], [ '否', '否' ]]
	});
	
	var typeStore1 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ '美国', '美国' ], [ '澳大利亚', '澳大利亚' ],['日本','日本'], ['香港','香港'],['英国','英国']]
	});
//表格部分
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
	}, {
		name : 'a4'
	}, {
		name : 'a5'
	}];



	var columns = new Ext.grid.ColumnModel([{
		dataIndex : 'a',
		header : '教育程度',
		sortable : true,
		width : 100
	},{
		dataIndex : 'a0',
		header : '国内',
		sortable : true,
		width : 100
	}, {
		dataIndex : 'a1',
		header : '美国',
		sortable : true,
		width : 100
	}, {
		dataIndex : 'a2',
		header : '澳大利亚',
		sortable : true,
		width : 100
	}, {
		dataIndex : 'a3',
		header : '日本',
		sortable : true,
		width : 100
	}, {
		dataIndex : 'a4',
		header : '香港',
		sortable : true,
		width : 100
	}, {
		dataIndex : 'a5',
		header : '英国',
		sortable : true,
		width : 100
	}]);

	var data = [
	            ['本科费用','30,000.00 ','30,000.00','30,000.00','30,000.00','30,000.00','30,000.00'],
	            ['研究生','30,000.00','30,000.00','30,000.00','30,000.00','30,000.00','30,000.00']
	];



	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});

		var eduGrid = new Ext.grid.GridPanel({
			title:'各国学费介绍',
			region : 'center',
			frame : true,
			store : store,
			stripeRows : true,
			cm : columns
			
	});
		
		var eduInfo = new Ext.form.FormPanel( {
			labelWidth : 100,
			title:'教育保障需求',
			labelAlign : 'right',
			frame : true,
			region : 'north',
			autoScroll : true,
			layout : 'column',
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '通货膨胀率',
					name : 'q1',
					xtype : 'textfield', 
					value:'25%',
					anchor : '90%'
				},{
					store : typeStore ,
					xtype : 'combo',
					resizable : true,
					name : 'DBTABLE_ID',
					fieldLabel : '您计划子女在国内接受教育程度',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					value:'大学',
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					anchor : '90%'
				
			},{
				store : ifStore ,
				xtype : 'combo',
				resizable : true,
				name : 'DBTABLE_ID',
				fieldLabel : '是否考虑留学',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				value:'是',
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%'
			
		},{
			fieldLabel : '留学时间(年)',
			name : 'q1',
			xtype : 'textfield', 
			value:'4',
			anchor : '90%'
		},{
			fieldLabel : '<font color="red">教育保障目标需求缺口</font>',
					name : 'q1',
					xtype : 'textfield', 
					value:'350,000',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '子女的年龄',
					name : 'q3',
					value:'10',
					xtype : 'textfield', 
					anchor : '90%'
				},{
					fieldLabel : '教育费用',
					name : 'q3',
					value:'50，000',
					xtype : 'textfield', 
					anchor : '90%'
				},{
					store : ifStore ,
					xtype : 'combo',
					resizable : true,
					name : 'DBTABLE_ID',
					fieldLabel : '留学国家',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					value:'英国',
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					anchor : '90%'
				
			},{
				fieldLabel : '留学费用',
				name : 'q3',
				value:'500，000',
				xtype : 'textfield', 
				anchor : '90%'
			}]
			}]
		});	