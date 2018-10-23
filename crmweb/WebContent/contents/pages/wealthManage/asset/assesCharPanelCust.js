// 默认swf路径
Ext.chart.Chart.CHART_URL = '../../../resource/ext3/resources/charts.swf';

// 饼图数据
var store4 = new Ext.data.JsonStore({
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
var charPanel1 = new Ext.Panel({
			width : 350,
			height : 250,
			title : '管理资产占比',
			items : {
				store : store4,
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

// 投资风险占比
var store5 = new Ext.data.JsonStore({
			fields : ['season', 'total'],
			data : [{
						season : '普通风险',
						total : 45
					}, {
						season : '高风险',
						total : 20
					}, {
						season : '低风险',
						total : 15
					}, {
						season : '中高风险',
						total : 12
					}, {
						season : '中低风险',
						total : 8
					}]
		});
// 资产占比 饼图
var charPanel2 = new Ext.Panel({
			width : 350,
			height : 250,
			title : '投资风险占比',
			items : {
				store : store5,
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

// 管理资产分类
var store6 = new Ext.data.JsonStore({
			fields : ['season', 'total'],
			data : [{
						season : '保险理财类',
						total : 25
					}, {
						season : '风险类理财',
						total : 20
					}, {
						season : '存款类理财',
						total : 75
					}]
		});
// 资产占比 饼图
var charPanel3 = new Ext.Panel({
			width : 350,
			height : 250,
			title : '资产分类',
			items : {
				store : store6,
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
