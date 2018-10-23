/**
 * 营销账户汇总信息
 * @author songxs
 * @since 2012-12-18
 * 
 */


Ext.onReady(function() {
	
	var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var marketAccountInfoColumns = new Ext.grid.ColumnModel([rownum,
	                                                 {header :'客户编号',dataIndex:'custId',sortable : true,hidden:true},
	                                                 {header :'统计日期',dataIndex:'statDate',sortable:true},
	                                                 {header :'贷款余额',dataIndex:'loanAmount',sortable:true,renderer: money('0,000.00')},
	                                                 {header :'贷款日均',dataIndex:'yearLoanAmount',sortable:true,renderer: money('0,000.00')},
	                                                 {header :'派生存款余额',dataIndex:'saveAmount',sortabel:true,renderer: money('0,000.00')},
	                                                 {header :'派生存款日均',dataIndex:'yearSaveAmount',sortable:true,renderer: money('0,000.00')},
	                                                 {header :'平均结算资金归行率(%)',dataIndex:'avgRate',sortabel:true,width:180}
	                                                 ]);
	var marketAccountInfoRecord =  new Ext.data.Record.create([                                               
	                                                           {name:'custId',mapping:'CUST_ID'},
	                                                           {name:'statDate',mapping:'STAT_DATE'},
	                                                           {name:'loanAmount',mapping:'LOAN_AMOUNT'},
	                                                           {name:'yearLoanAmount',mapping:'YEAR_LOAN_AMOUNT'},
	                                                           {name:'saveAmount',mapping:'SAVE_AMOUNT'},
	                                                           {name:'yearSaveAmount',mapping:'YEAR_SAVE_AMOUNT'},
	                                                           {name:'avgRate',mapping:'AVG_RATE'}	                                                           
	                                                   ]);
	
	var marketAccountInfoReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
		},marketAccountInfoRecord);
	
	var marketAccountInfoStore = new Ext.data.Store({
		
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/marketAccountInfoQuery-Action.json',
			method:'GET'
		}),
		reader:marketAccountInfoReader
		});
	
	var spagesize_combo = new Ext.form.ComboBox({// 每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
		});
	
	spagesize_combo.on("select", function(comboBox) {	// 改变每页显示条数reload数据
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		marketAccountInfoStore.reload({
			params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		}
		});
	});	
	marketAccountInfoStore.load({params:{	
		'custid' : oCustInfo.cust_id ,
		start:0,
		limit: parseInt(spagesize_combo.getValue())
		}});
	
	var sbbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : marketAccountInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	var marketAccountInfoGrid =  new Ext.grid.GridPanel({//日程查询列表数据grid
		
		title:'营销账户汇总信息',
		id:'marketAccountInfoGrid',
		store:marketAccountInfoStore,
		layout:'fit',
		frame:true,
		height:document.body.scrollHeight-38,
		loadMask:true,
		cm :marketAccountInfoColumns,
		bbar:sbbar,
			stripeRows : true,
			loadMask : {
			msg : '正在加载表格数据,请稍等...'
	}
	});
	
	var viewport_center = new Ext.Panel({
		renderTo:'viewport_center',
		layout:'fit',
		autoScroll:true,
		items: [marketAccountInfoGrid] 
	});	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});
