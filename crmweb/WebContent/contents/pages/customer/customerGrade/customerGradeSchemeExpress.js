var datas = [];

// var datas_view = [ [ 1, "(", "贷款金额", "<", "200", ")", "&&" ],
// [ 2, "(", "定期存款金额", ">", "300", ")", "&&" ],
// [ 3, "(", "获取存款金额", ">", "400", ")", "" ] ];

var datas_view = [ [ 1, "((", "评级得分", "*", "20%", "", "+" ],
		[ 2, "", "信用等级得分", "*", "80%", ")", "*" ],
		[ 3, "", "权重1", "", "", "", "+" ],
		[ 4, "", "授信额度得分", "*", "权重2", ")", "*" ],
		[ 5, "", "权重3", "", "", "", "+" ],
		[ 6, "", "模拟利润得分", "*", "权重4", "", "+" ],
		[ 7, "(", "合作年限得分", "*", "权重5", "", "+" ],
		[ 8, "", "是否股东得分", "*", "权重6", ")", "*" ],
		[ 9, "", "权重7", "", "", "", "+" ],
		[ 10, "", "年收入得分", "", "", "", "+" ],
		[ 11, "", "五级分类得分", "", "", "", "" ] ];

var person = Ext.data.Record.create([ {
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
}, {
	name : "f4",
	mapping : 4
}, {
	name : "f5",
	mapping : 5
}, {
	name : "f6",
	mapping : 6
} ]);

// 复选框选择模式
var checkboxSM = new Ext.grid.CheckboxSelectionModel({
	checkOnly : false,
	singleSelect : false
});

var cellSM = new Ext.grid.CellSelectionModel();

var teamstore = new Ext.data.Store({
	reader : new Ext.data.ArrayReader({
		id : 0
	}, person),
	data : datas
});

var grid_express = new Ext.grid.EditorGridPanel({
	title : "评级方案细项",
	width : 800,
	height : 330,
	layout : 'fit',
	frame : true,
	tbar : [ {
		text : '新增',
		handler : function() {
			teamstore.add(new Ext.data.Record);
		}
	}, '-', {
		text : '删除',
		handler : function() {
			var records = grid_express.getSelectionModel().getSelections();
			var recordsLen = records.length;
			if (recordsLen < 1) {
				Ext.Msg.alert("系统提示信息", "请选择记录后进行删除！");
				return;
			} else {
				teamstore.remove(records);
			}
		}
	} ],
	store : teamstore,
	columns : [
			checkboxSM,
			{
				header : "左括号",
				width : 80,
				dataIndex : "f1",
				editor : new Ext.form.TextField({})
			},
			{
				header : "指标",
				width : 300,
				dataIndex : "f2",
				editor : new Com.yucheng.crm.common.ProductManage({
					id : 'productSelect_close_quota_01',
					anchor : '100%',
					disabled : true,
					onTrigger2Click : function() {
						busOpportCloseQuotaView();
					}
				})
			},
			{
				header : "条件",
				width : 80,
				dataIndex : "f3",
				editor : new Ext.form.ComboBox({
					editable : false,
					displayField : "sex",
					mode : "local",
					triggerAction : "all",
					store : new Ext.data.SimpleStore(
							{
								fields : [ "sex" ],
								data : [ [ "+" ], [ "-" ], [ "*" ], [ "/" ],
										[ ">" ], [ ">=" ], [ "<" ], [ "<=" ],
										[ "=" ], [ "!=" ] ]
							})
				})
			}, {
				header : "值",
				width : 120,
				dataIndex : "f4",
				editor : new Ext.form.TextField({})
			}, {
				header : "右括号",
				width : 80,
				dataIndex : "f5",
				editor : new Ext.form.TextField({})
			}, {
				header : "连接符",
				width : 80,
				dataIndex : "f6",
				editor : new Ext.form.ComboBox({
					editable : false,
					displayField : "sex",
					mode : "local",
					triggerAction : "all",
					store : new Ext.data.SimpleStore({
						fields : [ "sex" ],
						data : [ [ "+" ], [ "-" ], [ "*" ], [ "/" ] ]
					})
				})
			} ],
	stripeRows : true,
	sm : checkboxSM
});