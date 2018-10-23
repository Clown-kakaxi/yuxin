/**
 * 营销管理->商机管理->销售漏斗和销售漏斗列表 入口JS文件 wzy，2013-02-21
 */

var fields = [];
var columns = [];
var grid = null;
var store = null;
var funnelPicPanel = null;
var proxyUrl = basepath + '/mktBusiOpporFunnelQueryAction.json';

// 列表字段
fields = [{
			name : 'F_CODE'
		}, {
			name : 'F_VALUE'
		}, {
			name : 'F_C_V'
		}, {
			name : 'COUNT_STAGE'
		}, {
			name : 'COUNT_PERCENT'
		}, {
			name : 'COUNT_PERCENT_NUM'
		}, {
			name : 'COUNT_NUMBER'
		}, {
			name : 'COUNT_AMOUNT'
		}, {
			name : 'COUNT_WEIGHT'
		}];

store = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : proxyUrl
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'json.count',
						root : 'json.data'
					}, fields)
		});

// 列表表头
columns = new Ext.grid.ColumnModel([{
			dataIndex : 'F_CODE',
			header : '商机阶段编码',
			sortable : true,
			hidden : true
		}, {
			dataIndex : 'F_C_V',
			header : '商机阶段',
			sortable : true,
			width : 130
		}, {
			dataIndex : 'COUNT_STAGE',
			header : '销售阶段',
			sortable : true,
			width : 200
		}, {
			dataIndex : 'COUNT_PERCENT',
			header : '达成概率',
			sortable : true,
			width : 120
		}, {
			dataIndex : 'COUNT_NUMBER',
			header : '商机数量',
			sortable : true,
			width : 120,
			renderer : function(val) {
				// 增加超链接（有下划线）
				var htmlStr = val;
				if (val != 0) {
					htmlStr = '<span style="color:red;cursor:hand"><u>' + val
							+ '</u></span>';
				}
				return htmlStr;
			}
		}, {
			dataIndex : 'COUNT_AMOUNT',
			header : '预计金额',
			sortable : true,
			renderer : money('0,000.00'),
			align : 'right',
			width : 120
		}, {
			dataIndex : 'COUNT_WEIGHT',
			header : '权重金额',
			sortable : true,
			renderer : money('0,000.00'),
			align : 'right',
			width : 120
		}]);

// 列表表格
grid = new Ext.grid.GridPanel({
			title : '销售漏斗列表',
			layout : 'fit',
			frame : true,
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : store,
			stripeRows : true, // 斑马线
			cm : columns,
			height : 350,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

// 给“商机数量”超链接增加单击事件处理
grid.on('cellclick', function(gridPara, row, col) {// 获取编辑的行数，从0开始
			if (col == 4) {
				var checkedNodes = gridPara.getSelectionModel().selections.items;
				var COUNT_NUMBER = checkedNodes[0].data.COUNT_NUMBER;
				if (COUNT_NUMBER != 0) {
					opporStage = checkedNodes[0].data.F_CODE;
					mktBusiOpporListWindow.show();
				}
			}
		});

// 销售漏斗图形
funnelPicPanel = new Ext.form.FormPanel({
			title : '销售漏斗图形',
			width : '27%',
			height : '100%',
			frame : true,
			autoScroll : true,
			region : 'west',
			split : true,
			text : '正在加载漏斗数据,请稍等...',
			html : '<div id="chartdiv" style="width:100%;height:100%"></div>'
		});
