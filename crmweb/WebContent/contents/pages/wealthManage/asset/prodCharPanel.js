// 默认swf路径
Ext.chart.Chart.CHART_URL = '../../../resource/ext3/resources/charts.swf';

// 饼图数据
var store8 = new Ext.data.JsonStore({
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
var charPanel8 = new Ext.Panel({
			width : 350,
			height : 250,
			title : '管理资产占比',
			items : {
				store : store8,
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

// 产品占比饼图数据
var store9 = new Ext.data.JsonStore({
			fields : ['season', 'total'],
			data : [{
						season : '理财产品',
						total : 150
					}, {
						season : '存款产品',
						total : 245
					}, {
						season : '基金产品',
						total : 245
					}, {
						season : '保险产品',
						total : 50
					}, {
						season : '国债产品',
						total : 60
					}, {
						season : '其他产品',
						total : 130
					}]
		});
// 产品占比 饼图
var charPanel9 = new Ext.Panel({
			width : 350,
			height : 250,
			title : '产品占比',
			items : {
				store : store9,
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
var store10 = new Ext.data.JsonStore({
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
var charPanel10 = new Ext.Panel({
			width : 350,
			height : 250,
			title : '资产分类',
			items : {
				store : store10,
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