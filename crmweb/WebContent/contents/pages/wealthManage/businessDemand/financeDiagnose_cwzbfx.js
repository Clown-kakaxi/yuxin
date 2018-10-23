/**
 * 财富管理-业务需求：财务诊断-财务指标分析tab页面（静态页面） wzy，2013-09-09
 */

// 定义一个样式单，改变table中某些单元格的背景色
document.createStyleSheet().cssText = ".x-grid-back-color {background: #f1f2f4;}";
// 财务指标分析
var cwzbfxColumns = new Ext.grid.ColumnModel([{
			header : '指标',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 120
		}, {
			header : '说明',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 300
		}, {
			header : '实际值',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 180
		}, {
			header : '参考值',
			align : 'left',
			dataIndex : 'f4',
			sortable : true,
			width : 100
		}]);

var cwzbfxRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}]);

var cwzbfxStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, cwzbfxRecord)
		});

var cwzbfxData = {
	TOTALCOUNT : 3,
	rows : [{
		"f1" : "偿付比率",
		"f2" : "反映客户综合还债的能力",
		"f3" : "<htm><img src='pic/cwzd_001.jpg' width='80%' height='80%'><br>净资产/总资产</html>",
		"f4" : "高于50%"
	}, {
		"f1" : "负债总资产比率",
		"f2" : "减少由于资金的流动性不足而出现财务危机的可能",
		"f3" : "<htm><img src='pic/cwzd_002.jpg' width='80%' height='80%'><br>总负债/总资产</html>",
		"f4" : "低于50%"
	}, {
		"f1" : "负债收入比率",
		"f2" : "反映客户某一时期的还贷能力",
		"f3" : "<htm><img src='pic/cwzd_003.jpg' width='80%' height='80%'><br>月还款支出/月收入</html>",
		"f4" : "低于40%"
	}, {
		"f1" : "流动性比率",
		"f2" : "反映客户资产在未发生价值损失的条件下迅速<br>变现的能力",
		"f3" : "<htm><img src='pic/cwzd_004.jpg' width='80%' height='80%'><br>流动资产/月支出</html>",
		"f4" : "30%左右"
	}, {
		"f1" : "储蓄比率",
		"f2" : "反映客户税后收入中可用于投资或储蓄的比例",
		"f3" : "<htm><img src='pic/cwzd_005.jpg' width='80%' height='80%'><br>年盈余/年收入</html>",
		"f4" : "高于30%"
	}, {
		"f1" : "投资与净资产比率",
		"f2" : "反映客户通过投资增加财富以实现其财务目标的能力，<br>尤其对中年客户。将其它金融资产视为投资资产",
		"f3" : "<htm><img src='pic/cwzd_006.jpg' width='80%' height='80%'><br>投资资产/净资产</html>",
		"f4" : "50%左右"
	}, {
		"f1" : "消费率",
		"f2" : "反应客户支出占总收入的比例",
		"f3" : "<htm><img src='pic/cwzd_006.jpg' width='80%' height='80%'><br>总支出/总收入</html>",
		"f4" : "低于70%"
	}]
};

cwzbfxStore.loadData(cwzbfxData);

var cwzbfxPanel = new Ext.grid.GridPanel({
			height : 410,
			clicksToEdit : 1,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : cwzbfxStore,
			frame : true,
			cm : cwzbfxColumns,
			stripeRows : true,
			tbar : [{
						text : '雷达图',
						iconCls : 'detailIconCss',
						handler : function() {
							raderWin.show();
						}
					}],
			listeners : {
				"beforeedit" : function(iEventobj) {
				}
			}
		});

// 其它财务指标
var qtcwzbColumns = new Ext.grid.ColumnModel([{
			header : '指标',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 200
		}, {
			header : '说明',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 300
		}, {
			header : '实际值',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			editor : new Ext.form.NumberField(),
			width : 200
		}]);

var qtcwzbRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}]);

var qtcwzbStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, qtcwzbRecord)
		});

var qtcwzbData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "理财成就率",
				"f2" : "理财成就率越高说明过去理财成绩越佳",
				"f3" : ""
			}, {
				"f1" : "综合收益率",
				"f2" : "所有财富的综合收益率",
				"f3" : ""
			}, {
				"f1" : "贷款综合利率",
				"f2" : "所有贷款的综合利率",
				"f3" : ""
			}, {
				"f1" : "金融产品综合收益率",
				"f2" : "活期，存款，投资资产等财富的综合收益",
				"f3" : ""
			}]
};

qtcwzbStore.loadData(qtcwzbData);

var qtcwzbPanel = new Ext.grid.EditorGridPanel({
			height : 130,
			clicksToEdit : 1,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : qtcwzbStore,
			frame : true,
			cm : qtcwzbColumns,
			stripeRows : true,
			listeners : {}
		});

// 财务指标点评
var cwzbdpColumns = new Ext.grid.ColumnModel([{
			header : '指标',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 200
		}, {
			header : '点评',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			editor : new Ext.form.TextField(),
			width : 400
		}]);

var cwzbdpRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}]);

var cwzbdpStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, cwzbdpRecord)
		});

var cwzbdpData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "偿付比率点评",
				"f2" : ""
			}, {
				"f1" : "负债总资产比率点评",
				"f2" : ""
			}, {
				"f1" : "负债收入比率点评",
				"f2" : ""
			}, {
				"f1" : "流动性比率点评",
				"f2" : ""
			}, {
				"f1" : "储蓄比率点评",
				"f2" : ""
			}, {
				"f1" : "投资与净资产比率点评",
				"f2" : ""
			}, {
				"f1" : "消费率点评",
				"f2" : ""
			}, {
				"f1" : "理财成就率点评",
				"f2" : ""
			}, {
				"f1" : "收益率点评",
				"f2" : ""
			}, {
				"f1" : "综合点评",
				"f2" : ""
			}]
};

cwzbdpStore.loadData(cwzbdpData);

var cwzbdpPanel = new Ext.grid.EditorGridPanel({
			height : 255,
			clicksToEdit : 1,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : cwzbdpStore,
			frame : true,
			cm : cwzbdpColumns,
			stripeRows : true,
			listeners : {}
		});

var cxzbfx_01 = new Ext.form.FieldSet({
			xtype : 'fieldset',
			title : '财务指标分析',
			width : 770,
			// height : 300,
			autoScroll : true,
			labelAlign : 'right',
			collapsible : true,
			itemCls : 'x-check-group-alt',
			items : [cwzbfxPanel]
		});

var cxzbfx_02 = new Ext.form.FieldSet({
			xtype : 'fieldset',
			title : '其它财务指标',
			width : 770,
			// height : 300,
			autoScroll : true,
			labelAlign : 'right',
			collapsible : true,
			itemCls : 'x-check-group-alt',
			items : [qtcwzbPanel]
		});

var cxzbfx_03 = new Ext.form.FieldSet({
			xtype : 'fieldset',
			title : '财务指标点评',
			width : 770,
			// height : 300,
			autoScroll : true,
			labelAlign : 'right',
			collapsible : true,
			itemCls : 'x-check-group-alt',
			items : [cwzbdpPanel]
		});

var cwzbfxPanel = new Ext.Panel({
			width : 800,
			height : 350,
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
			buttonAlign : "center",
			items : [cxzbfx_01, cxzbfx_02, cxzbfx_03]
		});