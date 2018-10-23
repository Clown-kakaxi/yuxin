// 默认swf路径
Ext.chart.Chart.CHART_URL = '../../../resource/ext3/resources/charts.swf';

// 饼图数据
var store = new Ext.data.JsonStore({
			fields : ['season', 'total'],
			data : [{
						season : '理财资产',
						total : 150
					}, {
						season : '其他资产',
						total : 245
					}]
		});
// 资产占比 饼图
var charPanel = new Ext.Panel({
			width : 350,
			height : 250,
			title : '管理资产占比',
			items : {
				store : store,
				xtype : 'piechart',
				dataField : 'total',
				categoryField : 'season',
				extraStyle : {
					legend : {
						display : 'bottom',
						padding : 5,
						font : {
							family : 'Tahoma',
							size : 13
						}
					}
				}
			}
		});

// 投资规模总揽数据
var store2 = new Ext.data.JsonStore({
			fields : ['name', 'visits', 'views'],
			data : [{
						name : '投资总额',
						visits : 245,
						views : 300
					}, {
						name : '投资净额',
						visits : 240,
						views : 350
					}, {
						name : '收到年息',
						visits : 355,
						views : 300
					}, {
						name : '实现的收益',
						visits : 375,
						views : 320
					}]
		});

// 投资规模 柱状图
var linePanel = new Ext.Panel({
			iconCls : 'chart',
			title : '投资规模',
			frame : true,
			width : 400,
			height : 250,
			layout : 'fit',
			items : {
				xtype : 'columnchart',
				store : store2,
				url : '../../../resource/ext3/resources/charts.swf',
				xField : 'name',
				fieldLabel : 'name',
				yAxis : new Ext.chart.NumericAxis({
							displayName : 'Visits',
							labelRenderer : Ext.util.Format
									.numberRenderer('0,0')
						}),
				tipRenderer : function(chart, record, index, series) {
					if (series.yField == 'visits') {
						return record.data.name
								+ " "
								+ Ext.util.Format.number(record.data.visits,
										'0,0');
					} else {
						return record.data.name
								+ " "
								+ Ext.util.Format.number(record.data.views,
										'0,0');
					}
				},
				series : [{
							type : 'column',
							displayName : 'Page Views',
							yField : 'views',
							style : {
								image : 'bar.gif',
								mode : 'stretch',
								color : 0x99BBE8
							}
						}, {
							type : 'line',
							displayName : 'Visits',
							yField : 'visits',
							style : {
								color : 0x15428B
							}
						}]
			}
		});

// 指标信息
var radarData = {
	TOTALCOUNT : 3,
	rows : [{
				"f1" : "投资资产对管理资产占比",
				"f2" : "37.50%"
			}, {
				"f1" : "单日赢利率",
				"f2" : "24.15%"
			}, {
				"f1" : "风险投资占比",
				"f2" : "87.21%"
			}, {
				"f1" : "投资客户数",
				"f2" : "44"
			}, {
				"f1" : "投资产品数",
				"f2" : "22"
			}]
};

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

radarStore.loadData(radarData);

var radarPanel_02 = new Ext.grid.GridPanel({
			title : '指标信息',
			width : 350,
			height : 250,
			clicksToEdit : 1,
			layout : 'fit',
			store : radarStore,
			frame : true,
			cm : radarColumns,
			stripeRows : true
		});

// 雷达图
var demoData = {
	"chart" : {
		"caption" : "",
		"bgcolor" : "FFFFFF",
		"radarfillcolor" : "FFFFFF",
		"plotfillalpha" : "5",
		"plotborderthickness" : "2",
		// "anchoralpha" : "100",
		// "numberprefix" : "$",
		"numdivlines" : "2",
		"legendposition" : "botton"
	},
	"categories" : [{
				"font" : "Arial",
				"fontsize" : "11",
				"category" : [{
							"label" : "单日赢利率"
						}, {
							"label" : "风险投资占比"
						}, {
							"label" : "投资产品数"
						}, {
							"label" : "投资客户数"
						}, {
							"label" : "投资资产对管理资产占比"
						}]
			}],
	"dataset" : [{
				"seriesname" : "实际值",
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
				"seriesname" : "平均值",
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

// 显示雷达图形
function displayRader2() {
	var swfUrl = basepath + "/FusionCharts/Radar.swf";
	var chart = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0", "0");
	chart.setJSONData(demoData);
	chart.render("chartdiv2");
}


//显示管理资产
function showCharDive1(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv1.xml");
	myChart.render("charDiv1");
}

//投资规模
function showCharLine1(){
	var myChart = new FusionCharts("../../../../FusionCharts/MSColumn3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("lineDiv1.xml");
	myChart.render("charLine1");
}

// -----------------------------------------  资产结构
//显示管理资产
function showCharDive3(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv1.xml");
	myChart.render("charDiv3");
}
//投资风险占比
function showCharDive4(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv2.xml");
	myChart.render("charDiv4");
}
//投资风险占比
function showCharDive5(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv3.xml");
	myChart.render("charDiv5");
}


// -----------------------------------------  产品结构
function showCharDive6(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv1.xml");
	myChart.render("charDiv6");
}
//产品占比
function showCharDive7(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv4.xml");
	myChart.render("charDiv7");
}
//资产分类
function showCharDive8(){
	var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
	myChart.setDataURL("charDiv3.xml");
	myChart.render("charDiv8");
}

