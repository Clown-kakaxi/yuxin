Ext.onReady(function() {
	
	var qfrom = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '客户编号',
				name : 'q1',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '客户名称',
				name : 'q1',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '查询',
			handler : function() {}},
			{
				text : '重置',
			     handler : function() {
			    	 qfrom.form.reset();
						}
			}]
});
	
	
	
	
	//账户详情
	var accountfields = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}];

	var accountnum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	
	var accountColumns = new Ext.grid.ColumnModel([accountnum, {
		dataIndex : 'a0',
		header : '产品种类',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '目前余额市值',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a2',
		header : '未实现损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a3',
		header : '未实现报酬率',
		sortable : true,
		align:'right',
		width : 140
	}]);
	var accountdata = [
	            ['得利宝天添利B款','25,000','2,000','13%' ],
	            ['得利宝天添利C款','35,000','5,000','10%' ],
	            ['天添利A款至尊版','25,700','4,000','11%' ],
	            ['天添利B款至尊版','45,000','2,300','13%' ],
	            ['天添利A款','25,060','1,500','15%' ]
	           
	           
	];



	var accountStore = new Ext.data.ArrayStore({
		fields : accountfields,
		data : accountdata
	});
	var accountInfo =  new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		store : accountStore,
		stripeRows : true,
		cm : accountColumns
});
	var detailWin = new Ext.Window(
			{
				layout : 'fit',
				modal : true,
				title : "账户详情",
				 width : 650,
				 height : 400,
				closeAction : 'hide',
				buttonAlign : 'center',
				border : false,
				 closable : true,
				items : [ {
					layout : 'border',
					items : [
							{
								region : 'center',
								id : 'center-panel',
								layout : 'fit',
								items:[accountInfo]
							} ]
				} ]
			});
	
	//按时间统计
	debugger;
	var  continentGroupRow = [ 
                          {
        header : '',
        colspan : 1,
        align : 'center'
    }, {
        header : '本季',
        colspan : 3,
        align : 'center'
    }, {
        header : '年初至今',
        colspan : 3,
        align : 'center'
    }, {
        header : '去年整年',
        colspan : 3,
        align : 'center'
    }];
    var group = new Ext.ux.grid.ColumnHeaderGroup( {
        rows : [ continentGroupRow ]
    });
    

    var timefields = [ {
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
	}, {
		name : 'a6'
	}, {
		name : 'a7'
	}, {
		name : 'a8'
	}, {
		name : 'a9'
	}];

	var timenum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});


//  已实现损益、卖出金额及已实现报酬率
	var timecolumns = [{
		dataIndex : 'a0',
		header : '产品',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '已实现损益',
		sortable : true,
		width : 70
	}, {
		dataIndex : 'a2',
		header : '卖出金额',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a3',
		header : '未实现损益',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a4',
		header : '已实现损益',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a5',
		header : '卖出金额',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a6',
		header : '未实现损益',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a7',
		header : '已实现损益',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a8',
		header : '卖出金额',
		sortable : true,
		align:'right',
		width : 70
	}, {
		dataIndex : 'a9',
		header : '未实现损益',
		sortable : true,
		align:'right',
		width :70
	}];
	
	var timedata = [
	            ['得利宝天添利B款','25,000','2,000','13%','25,000','2,000','13%','25,000','2,000','13%' ],
	            ['得利宝天添利C款','35,000','5,000','10%','35,000','5,000','10%','35,000','5,000','10%' ],
	            ['天添利A款至尊版','25,700','4,000','11%' ,'25,700','4,000','11%','25,700','4,000','11%'],
	            ['天添利B款至尊版','45,000','2,300','13%' ,'45,000','2,300','13%','45,000','2,300','13%'],
	            ['天添利A款','25,060','1,500','15%' ,'25,060','1,500','15%','25,060','1,500','15%']
	           
	           
	];
	var timestore = new Ext.data.ArrayStore({
		fields : timefields,
		data : timedata
	});
	 var timeGrid = new Ext.grid.GridPanel( {
         store : timestore,
         stripeRows : true,
         columns : timecolumns,
         plugins : group
     });
	
	 var timeWin = new Ext.Window(
				{
					layout : 'fit',
					modal : true,
					title : "时间统计",
					 width : 700,
					 height : 400,
					closeAction : 'hide',
					buttonAlign : 'center',
					border : false,
					 closable : true,
					items : [ {
						layout : 'border',
						items : [
								{
									region : 'center',
									layout : 'fit',
									items:[timeGrid]
								} ]
					} ]
				});
	//主列表
	var fields = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}, {
		name : 'a4'
	}];

	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	
	var columns = new Ext.grid.ColumnModel([num, sm,{
		dataIndex : 'a0',
		header : '客户编号',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '客户姓名',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '目前余额市值',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a3',
		header : '未实现损益',
		sortable : true,
		align:'right',
		width : 140
	}, {
		dataIndex : 'a4',
		header : '未实现报酬率',
		sortable : true,
		align:'right',
		width : 140
	}]);
	var data = [
	            ['100','王一平','25,000','2,000','13%' ],
	            ['101','李晓','55,000','2,100','12%' ],
	            ['102','张贺','29,000','5,000','7%' ],
	            ['103','张晓璇','25,070','6,200','9%' ],
	            ['104','刘佳','28,000','2,000','18%' ]
	           
	           
	];



	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});
	var combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['value', 'text'],
					data : [[10, '10条/页'], [20, '20条/页'],
							[50, '50条/页'], [100, '100条/页'],
							[250, '250条/页'], [500, '500条/页']]
				}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var number = parseInt(combo.getValue());

	//分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', combo]
	});
	
	var grid = new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		store : store,
		stripeRows : true,
		sm:sm,
		cm : columns,
		tbar:[ {
            text : '察看账户详情',
            iconCls:'detailIconCss',
            handler:function() {
            	var records = grid.selModel.getSelections();// 得到被选择的行的数组
            	var selectLength = records.length;
                 if (selectLength!=1) {
                 	alert("请选择一条记录");
                     return false;
                 }
                 detailWin.show();
                 
        }},{
        	text : '时间区间统计',
            iconCls:'detailIconCss',
            handler:function() {
            	var records = grid.selModel.getSelections();// 得到被选择的行的数组
            	var selectLength = records.length;
                 if (selectLength!=1) {
                 	alert("请选择一条记录");
                     return false;
                 }
                timeWin.show();
                 
        }
        }],
		bbar:bbar
});
	
	//页面布局
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [{
				region : 'north',
				id : 'north-panel',
				title : "损益分析",
				height : 100,
				layout : 'fit',
				items : [qfrom]
			},{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [grid]
					}
			]
		} ]
	});
	
	
});