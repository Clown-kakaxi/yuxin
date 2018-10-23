/**
 * 财富管理-业务需求：财务诊断-财务现状tab页面（静态页面） wzy，2013-09-05
 */

// 定义一个样式单，改变table中某些单元格的背景色
document.createStyleSheet().cssText = ".x-grid-back-color {background: #f1f2f4;}";

var dateForm = new Ext.form.FormPanel({
			frame : true,
			width : 770,
			height : 35,
			// layout : 'fit',
			border : false,
			labelAlign : 'right',
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .4,
									labelWidth : 80, // 标签宽度
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '报告日期',
												name : 'reportDate',
												anchor : '95%',
												value : '2013-09-06',
												disabled : true
											}]
								}]
					}]
		});

var cwxzColumns = new Ext.grid.ColumnModel([{
			header : '总资产',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 180,
			renderer : function(v, m) {// 改变单元格背景色
				m.css = 'x-grid-back-color';
				return v;
			}
		}, {
			header : '金额',
			dataIndex : 'f2',
			align : 'right',
			sortable : true,
			width : 170
		}, {
			header : '总负债',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 180,
			renderer : function(v, m) {// 改变单元格背景色
				m.css = 'x-grid-back-color';
				return v;
			}
		}, {
			header : '金额',
			dataIndex : 'f4',
			align : 'right',
			sortable : true,
			width : 170
		}]);

var cwxzRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}]);

var cwxzStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, cwxzRecord)
		});

var cwxzData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "活期及通知存款",
				"f2" : "80,000.00",
				"f3" : "贷款",
				"f4" : "160,000.00"
			}, {
				"f1" : "定期存款",
				"f2" : "60,000.00",
				"f3" : "信用卡",
				"f4" : "2,000.00"
			}, {
				"f1" : "投资资产",
				"f2" : "50,525.00",
				"f3" : "",
				"f4" : ""
			}]
};

cwxzStore.loadData(cwxzData);

var cwxzPanel = new Ext.grid.EditorGridPanel({
	height : 110,
	clicksToEdit : 1,
	layout : 'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
	store : cwxzStore,
	frame : true,
	cm : cwxzColumns,
	stripeRows : true,
	listeners : {
		"beforeedit" : function(iEventobj) {
			// 设置单元格背景色
			cwxzPanel.getView().getCell(2, 2).style.backgroundColor = '#f1f2f4';
			cwxzPanel.getView().getCell(2, 3).style.backgroundColor = '#f1f2f4';
			var col = iEventobj.row;// 获取行
			// 设置某些单元格不能编辑
			if ((col == 2)
					&& (iEventobj.field == 'f3' || iEventobj.field == 'f4')) {
				return false;
			}
		}
	}
});

// 活期存款定义
var hqColumns = new Ext.grid.ColumnModel([{
			header : '名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 100
		}, {
			header : '账号',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 90
		}, {
			header : '币种',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 80
		}, {
			header : '开户日期',
			align : 'left',
			dataIndex : 'f4',
			sortable : true,
			width : 90
		}, {
			header : '利率',
			align : 'right',
			dataIndex : 'f5',
			sortable : true,
			width : 90
		}, {
			header : '余额',
			align : 'right',
			dataIndex : 'f6',
			sortable : true,
			width : 90
		}, {
			header : '期限(天)',
			align : 'right',
			dataIndex : 'f7',
			sortable : true,
			width : 80
		}, {
			header : '人民币余额',
			align : 'right',
			dataIndex : 'f8',
			sortable : true,
			width : 100
		}]);

var hqRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}, {
			name : 'f5'
		}, {
			name : 'f6'
		}, {
			name : 'f7'
		}, {
			name : 'f8'
		}]);

var hqStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, hqRecord)
		});

var hqData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "活期存款",
				"f2" : "HQ2013001",
				"f3" : "人民币",
				"f4" : "2013-01-01",
				"f5" : "0.05",
				"f6" : "50,000.00",
				"f7" : "180",
				"f8" : "50,000.00"
			}, {
				"f1" : "通知存款",
				"f2" : "TZ2013029",
				"f3" : "人民币",
				"f4" : "2013-01-02",
				"f5" : "0.04",
				"f6" : "10,000.00",
				"f7" : "360",
				"f8" : "10,000.00"

			}, {
				"f1" : "活期存款",
				"f2" : "HQ2013002",
				"f3" : "人民币",
				"f4" : "2013-01-03",
				"f5" : "0.05",
				"f6" : "20,000.00",
				"f7" : "180",
				"f8" : "20,000.00"
			}]
};

hqStore.loadData(hqData);

var hqPanel = new Ext.grid.GridPanel({
			title : '活期及通知存款',
			height : 130,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : hqStore,
			frame : true,
			cm : hqColumns,
			stripeRows : true
		});

// 定期存款
var dqColumns = new Ext.grid.ColumnModel([{
			header : '名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 100
		}, {
			header : '账号',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 90
		}, {
			header : '币种',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 80
		}, {
			header : '开户日期',
			align : 'left',
			dataIndex : 'f4',
			sortable : true,
			width : 90
		}, {
			header : '利率',
			align : 'right',
			dataIndex : 'f5',
			sortable : true,
			width : 90
		}, {
			header : '余额',
			align : 'right',
			dataIndex : 'f6',
			sortable : true,
			width : 90
		}, {
			header : '期限(天)',
			align : 'right',
			dataIndex : 'f7',
			sortable : true,
			width : 80
		}, {
			header : '人民币余额',
			align : 'right',
			dataIndex : 'f8',
			sortable : true,
			width : 100
		}]);

var dqRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}, {
			name : 'f5'
		}, {
			name : 'f6'
		}, {
			name : 'f7'
		}, {
			name : 'f8'
		}]);

var dqStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, dqRecord)
		});

var dqData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "定期存款",
				"f2" : "HQ2013001",
				"f3" : "人民币",
				"f4" : "2013-01-01",
				"f5" : "0.05",
				"f6" : "50,000.00",
				"f7" : "180",
				"f8" : "50,000.00"
			}, {
				"f1" : "定期存款",
				"f2" : "TZ2013029",
				"f3" : "人民币",
				"f4" : "2013-01-02",
				"f5" : "0.04",
				"f6" : "10,000.00",
				"f7" : "360",
				"f8" : "10,000.00"

			}]
};

dqStore.loadData(dqData);

var dqPanel = new Ext.grid.GridPanel({
			title : '定期存款',
			height : 110,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : dqStore,
			frame : true,
			cm : dqColumns,
			stripeRows : true
		});

// 投资资产（基金和证券理财计划）定义
var tzjzColumns = new Ext.grid.ColumnModel([{
			header : '名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 120
		}, {
			header : '基金代码',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 120
		}, {
			header : '当前净值',
			align : 'right',
			dataIndex : 'f3',
			sortable : true,
			width : 120
		}, {
			header : '份额',
			align : 'right',
			dataIndex : 'f4',
			sortable : true,
			width : 120
		}, {
			header : '现值',
			align : 'right',
			dataIndex : 'f5',
			sortable : true,
			width : 120
		}]);

var tzjzRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}, {
			name : 'f5'
		}]);

var tzjzStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, tzjzRecord)
		});

var tzjzData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "XXXX成长计划",
				"f2" : "CZJJ2013001",
				"f3" : "10.50",
				"f4" : "50",
				"f5" : "525.00"
			}]
};

tzjzStore.loadData(tzjzData);

var tzjzPanel = new Ext.grid.GridPanel({
			title : '投资资产(基金和证券理财计划)',
			height : 90,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : tzjzStore,
			frame : true,
			cm : tzjzColumns,
			stripeRows : true
		});

// 投资资产（国债）定义
var tzgzColumns = new Ext.grid.ColumnModel([{
			header : '名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 120
		}, {
			header : '账号',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 120
		}, {
			header : '票面利率',
			align : 'right',
			dataIndex : 'f3',
			sortable : true,
			width : 90
		}, {
			header : '起息日',
			align : 'left',
			dataIndex : 'f4',
			sortable : true,
			width : 90
		}, {
			header : '到期日',
			align : 'left',
			dataIndex : 'f5',
			sortable : true,
			width : 90
		}, {
			header : '存期(月)',
			align : 'right',
			dataIndex : 'f6',
			sortable : true,
			width : 100
		}, {
			header : '本金',
			align : 'right',
			dataIndex : 'f7',
			sortable : true,
			width : 100
		}]);

var tzgzRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}, {
			name : 'f5'
		}, {
			name : 'f6'
		}, {
			name : 'f7'
		}]);

var tzgzStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, tzgzRecord)
		});

var tzgzData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "XXXX国债",
				"f2" : "GZZH2013001",
				"f3" : "0.15",
				"f4" : "2012-11-04",
				"f5" : "2013-11-04",
				"f6" : "12",
				"f7" : "50,000.00"
			}]
};

tzgzStore.loadData(tzgzData);

var tzgzPanel = new Ext.grid.GridPanel({
			title : '投资资产(国债)',
			height : 90,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : tzgzStore,
			frame : true,
			cm : tzgzColumns,
			stripeRows : true
		});

// 贷款定义
var dkColumns = new Ext.grid.ColumnModel([{
			header : '名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 100
		}, {
			header : '账号',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 130
		}, {
			header : '放款日期',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 80
		}, {
			header : '贷款总额',
			align : 'right',
			dataIndex : 'f4',
			sortable : true,
			width : 80
		}, {
			header : '贷款期限(月)',
			align : 'left',
			dataIndex : 'f5',
			sortable : true,
			width : 90
		}, {
			header : '贷款利率',
			align : 'right',
			dataIndex : 'f6',
			sortable : true,
			width : 80
		}, {
			header : '币种',
			align : 'left',
			dataIndex : 'f7',
			sortable : true,
			width : 80
		}, {
			header : '还款方式',
			align : 'left',
			dataIndex : 'f8',
			sortable : true,
			width : 80
		}, {
			header : '每月还款额',
			align : 'right',
			dataIndex : 'f9',
			sortable : true,
			width : 80
		}, {
			header : '余额',
			align : 'right',
			dataIndex : 'f10',
			sortable : true,
			width : 80
		}]);

var dkRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}, {
			name : 'f5'
		}, {
			name : 'f6'
		}, {
			name : 'f7'
		}, {
			name : 'f8'
		}, {
			name : 'f9'
		}, {
			name : 'f10'
		}]);

var dkStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, dkRecord)
		});

var dkData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "房贷",
				"f2" : "6288333322224441111",
				"f3" : "2008-09-10",
				"f4" : "230,000.00",
				"f5" : "36",
				"f6" : "0.35",
				"f7" : "人民币",
				"f8" : "等额本息",
				"f9" : "1,100.00",
				"f10" : "160,000.00"
			}]
};

dkStore.loadData(dkData);

var dkPanel = new Ext.grid.GridPanel({
			title : '贷款',
			height : 105,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : dkStore,
			frame : true,
			cm : dkColumns,
			stripeRows : true
		});

// 信用卡定义
var xykColumns = new Ext.grid.ColumnModel([{
			header : '名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 100
		}, {
			header : '账号',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 130
		}, {
			header : '账单日',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 80
		}, {
			header : '最迟还款日',
			align : 'left',
			dataIndex : 'f4',
			sortable : true,
			width : 80
		}, {
			header : '币种',
			align : 'left',
			dataIndex : 'f5',
			sortable : true,
			width : 90
		}, {
			header : '总额度',
			align : 'right',
			dataIndex : 'f6',
			sortable : true,
			width : 80
		}, {
			header : '可用额度',
			align : 'right',
			dataIndex : 'f7',
			sortable : true,
			width : 80
		}, {
			header : '待还款额度',
			align : 'right',
			dataIndex : 'f8',
			sortable : true,
			width : 80
		}]);

var xykRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}, {
			name : 'f5'
		}, {
			name : 'f6'
		}, {
			name : 'f7'
		}, {
			name : 'f8'
		}]);

var xykStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, xykRecord)
		});

var xykData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "招行信用卡",
				"f2" : "6288111122223333444",
				"f3" : "每月5号",
				"f4" : "每月12号",
				"f5" : "人民币",
				"f6" : "15,000.00",
				"f7" : "13,000.00",
				"f8" : "2,000.00"
			}]
};

xykStore.loadData(xykData);

var xykPanel = new Ext.grid.GridPanel({
			title : '信用卡',
			height : 90,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : xykStore,
			frame : true,
			cm : xykColumns,
			stripeRows : true
		});

var fd_01 = new Ext.form.FieldSet({
			xtype : 'fieldset',
			title : '财务概况',
			width : 770,
			// height : 150,
			autoScroll : true,
			labelAlign : 'right',
			collapsible : true,
			itemCls : 'x-check-group-alt',
			items : [cwxzPanel]
		});

var fd_02 = new Ext.form.FieldSet({
			xtype : 'fieldset',
			title : '账户详情',
			width : 770,
			// height : 300,
			autoScroll : true,
			labelAlign : 'right',
			collapsible : true,
			itemCls : 'x-check-group-alt',
			items : [hqPanel, dqPanel, tzjzPanel, tzgzPanel, dkPanel, xykPanel]
		});

var layoutPanel = new Ext.Panel({
			width : 800,
			height : 350,
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
			buttonAlign : "center",
			items : [dateForm, fd_01, fd_02]
		});