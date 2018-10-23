/**
 * 财富管理-业务需求：财务诊断-收支表tab页面（静态页面） wzy，2013-09-06
 */

// 定义一个样式单，改变table中某些单元格的背景色
document.createStyleSheet().cssText = ".x-grid-back-color {background: #f1f2f4;}";
// “周期”下拉框数据定义
var boxTypeStore = new Ext.data.SimpleStore({
			fields : ['key', 'value'],
			data : [['1', '月'], ['2', '季'], ['3', '半年'], ['4', '年']]
		});
function rendererCombobox(value, p, r) { 
	var index = boxTypeStore.find(Ext.getCmp('boxTypeStore').valueField, value); 
	var  record= boxTypeStore.getAt(index); 
	var displayText = ""; 
	if (record == null) { 
	return value; 
	} else { 
	return record.data.value; // 获取record中的数据集中的display字段的值 
	
	} 
	} 

var szbColumns = new Ext.grid.ColumnModel([{
			header : '分类',
			align : 'left',
			dataIndex : 'f1',
			sortable : true,
			width : 120,
			renderer : function(v, m) {// 改变单元格背景色
				m.css = 'x-grid-back-color';
				return v;
			}
		}, {
			header : '明细',
			align : 'left',
			dataIndex : 'f2',
			sortable : true,
			width : 150,
			renderer : function(v, m) {// 改变单元格背景色
				m.css = 'x-grid-back-color';
				return v;
			}
		}, {
			header : '周期',
			align : 'left',
			dataIndex : 'f3',
			sortable : true,
			width : 110,
			renderer: rendererCombobox,
			editor : new Ext.form.ComboBox({
						store : boxTypeStore,
						id:'boxTypeStore',
						valueField : 'key',
						displayField : 'value',
						editable : false,
						forceSelection : true,
						mode : 'local',
						blankText : '请选择',
						emptyText : '请选择',
						triggerAction : 'all'
					})
		}, {
			header : '金额',
			align : 'left',
			dataIndex : 'f4',
			sortable : true,
			align : 'right',
			editor : new Ext.form.NumberField(),
			width : 120
		}, {
			header : '年金额',
			align : 'left',
			dataIndex : 'f5',
			align : 'right',
			sortable : true,
			editor : new Ext.form.NumberField(),
			width : 120
		}, {
			header : '年汇总合计',
			align : 'left',
			dataIndex : 'f6',
			align : 'right',
			sortable : true,
			editor : new Ext.form.NumberField(),
			width : 120
		}]);

var szbRecord = Ext.data.Record.create([{
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
		}]);

var szbStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/'
					}),
			reader : new Ext.data.JsonReader({
						root : 'rows'// Json中的列表数据根节点
					}, szbRecord)
		});

var memberData = {
	rows : [{
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "工资薪金",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "个体经营收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "企事业经营收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "劳务报酬",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "稿酬",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "特许权收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "奖金收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常收入(税后)</b></html>",
				"f2" : "其他收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常支出</b></html>",
				"f2" : "基本生活支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常支出</b></html>",
				"f2" : "交通支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常支出</b></html>",
				"f2" : "休闲支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常支出</b></html>",
				"f2" : "租金支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常支出</b></html>",
				"f2" : "教育进修",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>日常支出</b></html>",
				"f2" : "其他支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>财富收入(税后)</b></html>",
				"f2" : "投资收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>财富收入(税后)</b></html>",
				"f2" : "租金收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>财富收入(税后)</b></html>",
				"f2" : "保险收入",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>财富支出</b></html>",
				"f2" : "投资支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>财富支出</b></html>",
				"f2" : "还款支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>财富支出</b></html>",
				"f2" : "保险支出",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}, {
				"f1" : "<html><b>年净收入</b></html>",
				"f2" : "",
				"f3" : "",
				"f4" : "",
				"f5" : "",
				"f6" : ""
			}]
};

szbStore.loadData(memberData);

var szbPanel = new Ext.grid.EditorGridPanel({
			height : 355,
			clicksToEdit : 1,
			layout : 'fit',
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : szbStore,
			frame : true,
			cm : szbColumns,
			stripeRows : true,
			listeners : {}
		});

// 合并单元格
// 备注：从网上找到的一个方法，代码逻辑处理有些Bug，基本实现了单元格合并，但是没有实现合并后的样式调整
function gridSpan(grid, rowOrCol, cols, sepCol) {
	var array1 = new Array();
	var arraySep = new Array();
	var count1 = 0;
	var count2 = 0;
	var index1 = 0;
	var index2 = 0;
	var aRow = undefined;
	var preValue = undefined;
	var firstSameCell = 0;
	var allRecs = grid.getStore().getRange();
	if (rowOrCol == "row") {
		count1 = grid.getColumnModel().getColumnCount();
		count2 = grid.getStore().getCount();
	} else {
		count1 = grid.getStore().getCount();
		count2 = grid.getColumnModel().getColumnCount();
	}
	for (i = 0; i < count1; i++) {
		if (rowOrCol == "row") {
			var curColName = grid.getColumnModel().getDataIndex(i);
			var curCol = "[" + curColName + "]";
			if (cols.indexOf(curCol) < 0)
				continue;
		}
		preValue = undefined;
		firstSameCell = 0;
		array1[i] = new Array();
		for (j = 0; j < count2; j++) {
			if (rowOrCol == "row") {
				index1 = j;
				index2 = i;
			} else {
				index1 = i;
				index2 = j;
			}
			var colName = grid.getColumnModel().getDataIndex(index2);
			if (sepCol && colName == sepCol)
				arraySep[index1] = allRecs[index1].get(sepCol);
			var seqOldValue = seqCurValue = "1";
			if (sepCol && index1 > 0) {
				seqOldValue = arraySep[index1 - 1];
				seqCurValue = arraySep[index1];
			}
			if (allRecs[index1].get(colName) == preValue
					&& (colName == sepCol || seqOldValue == seqCurValue)) {
				try {
					allRecs[index1].set(colName, "");
				} catch (e) {
					// alert("=4==" + e.message);
				}
				array1[i].push(j);
				if (j == count2 - 1) {
					var index = firstSameCell
							+ Math.round((j + 1 - firstSameCell) / 2 - 1);
					try {
						if (rowOrCol == "row") {
							allRecs[index].set(colName, preValue);
						} else {
							allRecs[index1].set(grid.getColumnModel()
											.getColumnId(index), preValue);
						}
					} catch (e) {
						// alert("=1==" + e.message);
					}
				}
			} else {
				if (j != 0) {
					var index = firstSameCell
							+ Math.round((j + 1 - firstSameCell) / 2 - 1);
					try {
						if (rowOrCol == "row") {
							allRecs[index].set(colName, preValue);
						} else {
							allRecs[index1].set(grid.getColumnModel()
											.getColumnId(index), preValue);
						}
					} catch (e) {
						// alert("=2==" + e.message);
					}
				}
				firstSameCell = j;
				preValue = allRecs[index1].get(colName);
				if (preValue == '<html><b>年净收入</b></html>') {
					break;
				}
				try {
					allRecs[index1].set(colName, "");
					if (j == count2 - 1) {
						allRecs[index1].set(colName, preValue);
					}
				} catch (e) {
					// alert("=3==" + e.message);
				}
			}
		}
	}
	try {
		grid.getStore().commitChanges();
	} catch (e) {
		// alert("=5==" + e.message);
	}
	// 添加所有分隔线
	var rCount = grid.getStore().getCount();
	for (i = 0; i < rCount; i++) {
		for (j = 0; j < grid.getColumnModel().getColumnCount(); j++) {
			try {
				aRow = grid.getView().getCell(i, j);
			} catch (e) {
				// alert("=6==" + e.message);
			}
			if (aRow) {
				if (i == 0) {
					aRow.style.borderTop = "none";
					aRow.style.borderLeft = "1px solid #ccc";
				} else if (i == rCount - 1) {
					aRow.style.borderTop = "1px solid #ccc";
					aRow.style.borderLeft = "1px solid #ccc";
					aRow.style.borderBottom = "1px solid #ccc";
				} else {
					aRow.style.borderTop = "1px solid #ccc";
					aRow.style.borderLeft = "1px solid #ccc";
				}
				if (j == grid.getColumnModel().getColumnCount() - 1)
					aRow.style.borderRight = "1px solid #ccc";
				if (i == rCount - 1)
					aRow.style.borderBottom = "1px solid #ccc";
			}
		}
	}
	// 去除合并的单元格的分隔线
	for (i = 0; i < array1.length; i++) {
		if (!Ext.isEmpty(array1[i])) {
			for (j = 0; j < array1[i].length; j++) {
				try {
					if (rowOrCol == "row") {
						aRow = grid.getView().getCell(array1[i][j], i);
						aRow.style.borderTop = "none";
					} else {
						aRow = grid.getView().getCell(i, array1[i][j]);
						aRow.style.borderLeft = "none";
					}
				} catch (e) {
					// alert("=7==" + e.message);
				}
			}
		}
	}
}