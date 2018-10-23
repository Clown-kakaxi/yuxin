/**
 * 财富管理-业务需求：财务诊断-财务指标分析tab页面-雷达图（静态页面） wzy，2013-09-10
 */
var demoData = {
	"chart" : {
		"caption" : "财务指标分析雷达图",
		"bgcolor" : "FFFFFF",
		"radarfillcolor" : "FFFFFF",
		"plotfillalpha" : "5",
		"plotborderthickness" : "2",
		// "anchoralpha" : "100",
		// "numberprefix" : "$",
		"numdivlines" : "2",
		"legendposition" : "RIGHT"
	},
	"categories" : [{
				"font" : "Arial",
				"fontsize" : "11",
				"category" : [{
							"label" : "偿付比率"
						}, {
							"label" : "流动性比率"
						}, {
							"label" : "投资与净资产比率"
						}, {
							"label" : "消费率"
						}, {
							"label" : "储蓄比率"
						}]
			}],
	"dataset" : [{
				"seriesname" : "Products",
				"color" : "CD6AC0",
				"anchorsides" : "6",
				"anchorradius" : "2",
				"anchorbordercolor" : "CD6AC0",
				"anchorbgalpha" : "0",
				"data" : [{
							"value" : "24"
						}, {
							"value" : "35"
						}, {
							"value" : "66"
						}, {
							"value" : "44"
						}, {
							"value" : "78"
						}]
			}, {
				"seriesname" : "Services",
				"color" : "0099FF",
				"anchorsides" : "10",
				"anchorbordercolor" : "0099FF",
				"anchorbgalpha" : "0",
				"anchorradius" : "2",
				"data" : [{
							"value" : "11"
						}, {
							"value" : "56"
						}, {
							"value" : "27"
						}, {
							"value" : "75"
						}, {
							"value" : "28"
						}]
			}],
	"styles" : {
		"definition" : [{
					"name" : "myCaptionFont",
					"type" : "font",
					"font" : "Arial",
					"size" : "14",
					"color" : "666666",
					"bold" : "1",
					"underline" : "1"
				}],
		"application" : [{
					"toobject" : "Caption",
					"styles" : "myCaptionFont"
				}]
	}
};

// 雷达图图形
var radarPanel_01 = new Ext.form.FormPanel({
			width : '65%',
			height : '100%',
			frame : true,
			autoScroll : true,
			region : 'west',
			split : true,
			text : '正在加载数据,请稍等...',
			html : '<div id="chartdiv" style="width:100%;height:100%"></div>'
		});

// 显示雷达图形
function displayRader() {
	var swfUrl = basepath + "/FusionCharts/Radar.swf";
	var chart = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0", "0");
	chart.setJSONData(demoData);
	chart.render("chartdiv");
}

var radarColumns = new Ext.grid.ColumnModel([{
			header : '指标名称',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 120
		}, {
			header : '指标值',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 100
		}]);

var radarRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}]);

var radarStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/planProductQuery.json',
						failure : function(response) {
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'num',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, radarRecord)
		});

var radarData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "偿付比率",
				"f2" : "37.50%"
			}, {
				"f1" : "流动性比率",
				"f2" : "24.15%"
			}, {
				"f1" : "投资与净资产比率",
				"f2" : "87.21%"
			}, {
				"f1" : "消费率",
				"f2" : "44.15%"
			}, {
				"f1" : "储蓄比率",
				"f2" : "22.15%"
			}]
};

radarStore.loadData(radarData);

var radarPanel_02 = new Ext.grid.GridPanel({
			height : 90,
			clicksToEdit : 1,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : radarStore,
			frame : true,
			cm : radarColumns,
			stripeRows : true
		});

var formgrid = new Ext.FormPanel({
			layout : 'fit',
			labelAlign : 'right',
			height : 300,
			frame : true,
			html : '<p style="line-height:20px;font-size:13px;" align="center">'
					+ '<input type="button" value="更多指标" style="cursor:hand"></p>'
					+ '<p style="line-height:20px;font-size:13px;">'
					+ '&nbsp;&nbsp;&nbsp;&nbsp;'
					+ '雷达图从流动性、偿付能力、投资资产和消费情况反映客户的资产配置情况，'
					+ '除这些指标外，可点击更多指标按钮，查看客户的更多财务指标，对其进行评估。</p>'
		});

var raderWin = new Ext.Window({
			closeAction : 'hide',
			title : '雷达图',
			height : 430,
			width : 750,
			buttonAlign : 'center',
			layout : 'fit',
			modal : true,
			items : [{
						region : 'center',
						layout : 'border',
						items : [radarPanel_01, {
									region : 'center',
									layout : 'fit',
									items : [{
												layout : 'border',
												split : true,
												height : 250,
												minSize : 100,
												maxSize : 300,
												items : [{
															id : 'center-panel',
															region : 'center',
															layout : 'fit',
															items : [radarPanel_02]
														}, {
															id : 'south-panel',
															region : 'south',
															title : '雷达图描述',
															collapsible : true,
															split : true,
															height : 200,
															minSize : 100,
															maxSize : 300,
															items : [formgrid]
														}]
											}]
								}]
					}],
			listeners : {
				'show' : function() {
					displayRader();
				}
			},
			buttons : [{
						text : '关闭',
						handler : function() {
							raderWin.hide();
						}
					}]
		});