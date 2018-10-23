/**
 * 营销账户归属界面
 * @author songxs
 * @since 2012-12-18
 * 
 */

Ext.onReady(function() {
	
	var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var loanInfoColumns = new Ext.grid.ColumnModel([rownum,
	                                                 {header :'客户编号',dataIndex:'custId',sortable : true,hidden:true},
	                                                 {header :'统计日期',dataIndex:'statDate',sortable:true},
	                                                 {header :'贷款余额',dataIndex:'loanAmount',sortable:true,renderer: money('0,000.00')},
	                                                 {header :'贷款日均',dataIndex:'yearLoanAmount',sortable:true,renderer: money('0,000.00')},
	                                                 {header :'派生存款余额',dataIndex:'saveAmount',sortabel:true,renderer: money('0,000.00')},
	                                                 {header :'派生存款日均',dataIndex:'yearSaveAmount',sortable:true,renderer: money('0,000.00')},
	                                                 {header :'平均结算资金归行率',dataIndex:'avgRate',sortabel:true,width:180}
	                                                 ]);
	var loanInfoRecord =  new Ext.data.Record.create([                                               
	                                                           {name:'custId',mapping:'CUST_ID'},
	                                                           {name:'statDate',mapping:'STAT_DATE'},
	                                                           {name:'loanAmount',mapping:'LOAN_AMOUNT'},
	                                                           {name:'yearLoanAmount',mapping:'YEAR_LOAN_AMOUNT'},
	                                                           {name:'saveAmount',mapping:'SAVE_AMOUNT'},
	                                                           {name:'yearSaveAmount',mapping:'YEAR_SAVE_AMOUNT'},
	                                                           {name:'avgRate',mapping:'AVG_RATE'}	                                                           
	                                                   ]);
	
	var loanInfoReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
		},loanInfoRecord);
	
	var loanInfoStore = new Ext.data.Store({
		
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/loanInfoQuery-Action.json',
			method:'GET'
		}),
		reader:loanInfoReader
		});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});