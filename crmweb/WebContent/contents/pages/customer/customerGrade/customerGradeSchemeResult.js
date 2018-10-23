var datas_result = [];

var datas_result_view = [ [ 1, "黄金客户", "100", "" ], [ 2, "重点客户", "90", "100" ],
		[ 3, "优质客户", "80", "90" ], [ 4, "一般客户", "70", "80" ],
		[ 5, "限制客户", "60", "70" ], [ 6, "淘汰客户", "", "60" ] ];

var person_result = Ext.data.Record.create([ {
	name : "f0",
	mapping : 0
}, {
	name : "f1",
	mapping : 1
}, {
	name : "f2",
	mapping : 2
}, {
	name : "f3",
	mapping : 3
} ]);

// 复选框选择模式
var checkboxSM_result = new Ext.grid.CheckboxSelectionModel({
	checkOnly : false,
	singleSelect : false
});

var cellSM_result = new Ext.grid.CellSelectionModel();

var teamstore_result = new Ext.data.Store({
	reader : new Ext.data.ArrayReader({
		id : 0
	}, person_result),
	data : datas_result
});

var grid_express_result = new Ext.grid.EditorGridPanel({
	title : "评级结果细项",
	width : 800,
	height : 230,
	frame : true,
	tbar : [
			{
				text : '新增',
				handler : function() {
					teamstore_result.add(new Ext.data.Record);
				}
			},
			'-',
			{
				text : '删除',
				handler : function() {
					var records = grid_express_result.getSelectionModel()
							.getSelections();
					var recordsLen = records.length;
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择记录后进行删除！");
						return;
					} else {
						teamstore_result.remove(records);
					}
				}
			} ],
	store : teamstore_result,
	columns : [ checkboxSM_result, {
		header : "客户等级名称",
		width : 300,
		dataIndex : "f1",
		editor : new Ext.form.TextField({})
	}, {
		header : "指标总分下限(包含)",
		width : 220,
		dataIndex : "f2",
		editor : new Ext.form.TextField({})
	}, {
		header : "指标总分上限(不包含)",
		width : 220,
		dataIndex : "f3",
		editor : new Ext.form.TextField({})
	} ],
	stripeRows : true,
	sm : checkboxSM_result
});