/**
 * 客户信用信息，静态页面 wzy，2013-03-13
 */
var fields = [], columns = [], data = [], grid = null, viewport = null;
// 定义一个样式单，改变table中某些单元格的背景色
document.createStyleSheet().cssText = ".x-grid-back-color {background: #f1f2f4;}";
Ext.onReady(function() {
	var planProdColumns = new Ext.grid.ColumnModel([ {
		header : '币种',
		align : 'left',
		dataIndex : 'f1',
		sortable : true,
		width : 200,
		renderer : function(v, m) {// 改变单元格背景色
			m.css = 'x-grid-back-color';
			return v;
		}
	}, {
		header : '人民币',
		align : 'left',
		dataIndex : 'f2',
		sortable : true,
		width : 200
	}, {
		header : '美元',
		align : 'left',
		dataIndex : 'f3',
		sortable : true,
		width : 200
	} ]);

	var planProdRecord = Ext.data.Record.create([ {
		name : 'f1'
	}, {
		name : 'f2'
	}, {
		name : 'f3'
	} ]);

	var planProdStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/planProductQuery.json',
			failure : function(response) {
			}
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'num',// 记录总数
			root : 'rows'// Json中的列表数据根节点
		}, planProdRecord)
	});

	var memberData = {
		TOTALCOUNT : 3,
		rows : [ {
			"f1" : "信用额度",
			"f2" : "￥15,000.00",
			"f3" : "$260.00"
		}, {
			"f1" : "可用额度",
			"f2" : "￥12,000.00",
			"f3" : "$200.00"
		}, {
			"f1" : "预计现金额度",
			"f2" : "￥9,000.00",
			"f3" : "$150.00"
		}, {
			"f1" : "每月账单日",
			"f2" : "15号",
			"f3" : ""
		}, {
			"f1" : "账单类型",
			"f2" : "电子账单",
			"f3" : ""
		}, {
			"f1" : "是否有逾期",
			"f2" : "否",
			"f3" : ""
		}, {
			"f1" : "逾期次数",
			"f2" : "0",
			"f3" : ""
		} ]
	};
	planProdStore.loadData(memberData);

	var salePanel = new Ext.grid.GridPanel({
		height : document.body.scrollHeight - 40,
		width : document.body.scrollWidth - 10,
		layout : 'fit',
		autoScroll : true,
		region : 'center', // 返回给页面的div
		store : planProdStore,
		frame : true,
		cm : planProdColumns,
		stripeRows : true
	});

	new Ext.Panel({
		title : '客户信用信息',
		renderTo : 'viewport_center',
		labelWidth : 250,
		layout : 'fit',
		primary : "id",
		buttonAlign : "center",
		items : [ salePanel ]
	});
});