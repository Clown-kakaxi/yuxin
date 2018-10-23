/**
 * 财富管理-业务需求：财务诊断-资产负债表tab页面（静态页面） wzy，2013-09-05
 */

// 定义一个样式单，改变table中某些单元格的背景色
document.createStyleSheet().cssText = ".x-grid-back-color {background: #f1f2f4;}";
var planProdColumns = new Ext.grid.ColumnModel([{
			header : '资产',
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
			align : 'right',
			dataIndex : 'f2',
			sortable : true,
			editor : new Ext.form.NumberField(),
			width : 170
		}, {
			header : '负债',
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
			align : 'right',
			dataIndex : 'f4',
			sortable : true,
			editor : new Ext.form.NumberField(),
			width : 170
		}]);

var planProdRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		}]);

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
	rows : [{
				"f1" : "<html><b>现金及现金等价物</b></html>",
				"f2" : "",
				"f3" : "<html><b>贷款</b></html>",
				"f4" : ""
			}, {
				"f1" : "现金",
				"f2" : "",
				"f3" : "公积金贷款",
				"f4" : ""
			}, {
				"f1" : "活期存款",
				"f2" : "",
				"f3" : "个人住房贷款",
				"f4" : ""
			}, {
				"f1" : "通知存款",
				"f2" : "",
				"f3" : "个人商铺贷款",
				"f4" : ""
			}, {
				"f1" : "货币基金",
				"f2" : "",
				"f3" : "个人车位贷款",
				"f4" : ""
			}, {
				"f1" : "<html><b>定期存款</b></html>",
				"f2" : "",
				"f3" : "教育贷款",
				"f4" : ""
			}, {
				"f1" : "整存整取",
				"f2" : "",
				"f3" : "汽车贷款",
				"f4" : ""
			}, {
				"f1" : "零存整取",
				"f2" : "",
				"f3" : "质押贷款",
				"f4" : ""
			}, {
				"f1" : "整存零取",
				"f2" : "",
				"f3" : "贷记卡",
				"f4" : ""
			}, {
				"f1" : "存本取息",
				"f2" : "",
				"f3" : "其他贷款",
				"f4" : ""
			}, {
				"f1" : "教育储蓄",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "灵活两便",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "<html><b>投资资产</b></html>",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "基金",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "股票",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "期货",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "债券",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "黄金",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "信托",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "理财产品",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "企业投资",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "<html><b>家居资产</b></html>",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "房屋",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "车辆",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "其他",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "<html><b>限定性资产</b></html>",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "公积金",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "社会统筹养老保险",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "社会统筹医疗保险",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "<html><b>保险</b></html>",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "人寿保险",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "财产保险",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "责任保险",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "养老保险",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}, {
				"f1" : "<html><b>总资产</b></html>",
				"f2" : "",
				"f3" : "<html><b>总负债</b></html>",
				"f4" : ""
			}, {
				"f1" : "<html><b>净资产</b></html>",
				"f2" : "",
				"f3" : "",
				"f4" : ""
			}]
};

planProdStore.loadData(memberData);

var zcfzPanel = new Ext.grid.EditorGridPanel({
	height : 355,
	clicksToEdit : 1,
	layout : 'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
	store : planProdStore,
	frame : true,
	cm : planProdColumns,
	stripeRows : true,
//	tbar : [{
//				text : '资产分析图',
//				iconCls : 'detailIconCss',
//				handler : function() {
//				}
//			}],
	listeners : {
		"beforeedit" : function(iEventobj) {
			// 设置单元格背景色
			zcfzPanel.getView().getRow(0).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(5, 1).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(12, 1).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(21, 1).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(25, 1).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(29, 1).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(10, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(11, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(12, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(13, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(14, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(15, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(16, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(17, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(18, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(19, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(20, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(21, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(22, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(23, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(24, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(25, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(26, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(27, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(28, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(29, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(30, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(31, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(32, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(33, 3).style.backgroundColor = '#f1f2f4';
			zcfzPanel.getView().getCell(35, 3).style.backgroundColor = '#f1f2f4';
			var col = iEventobj.row;// 获取行
			// 设置某些单元格不能编辑
			if (col == 0) {
				return false;
			} else if (col == 5 && iEventobj.field == 'f2') {
				return false;
			} else if ((col == 12 || col == 21 || col == 25 || col == 29)
					&& iEventobj.field == 'f2') {
				return false;
			} else if ((col >= 10 && col != 34) && iEventobj.field == 'f4') {
				return false;
			}
		}
	}
});