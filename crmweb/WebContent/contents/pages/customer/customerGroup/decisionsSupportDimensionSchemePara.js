/**
 * 客户管理->决策支持维度方案管理：指标参数列表定义JS文件；wzy；2013-05-16
 */
var datas_result = [];

var rowNo1 = -1;

// 指标信息
var paraSelect = new Com.yucheng.crm.common.IndexField({
			xtype : 'userchoose',
			fieldLabel : '指标列表',
			id : 'paraSelect',
			name : 'paraSelect',
			hiddenName : 'paraSelect',
			labelStyle : 'text-align:right;',
			singleSelect : false,
			anchor : '90%',
			callback : function(a, b, c, d) {
				var mgr_namess = Ext.getCmp('paraSelect').getValue();
				if (mgr_namess != null && mgr_namess != '') {
					teamstore_result.getAt(rowNo1).data.f1 = this.CODE;
					teamstore_result.getAt(rowNo1).data.f2 = this.CLASSNAME;
					teamstore_result.getAt(rowNo1).data.f3 = this.NAME;
					grid_express_result.getView().refresh(true);
				}
			}
		});

var datas_result_view = [[1, "1", "基础指标", "数量"], [2, "2", "基础指标", "百分比"],
		[3, "3", "基础指标", "完成率"]];

var person_result = Ext.data.Record.create([{
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
		}]);

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
			title : "指标参数",
			width : 850,
			height : 150,
			frame : true,
			tbar : [{
						text : '新增',
						handler : function() {
							teamstore_result.add(new Ext.data.Record);
						}
					}, '-', {
						text : '删除',
						handler : function() {
							var records = grid_express_result
									.getSelectionModel().getSelections();
							var recordsLen = records.length;
							if (recordsLen < 1) {
								Ext.Msg.alert("系统提示信息", "请选择记录后进行删除！");
								return;
							} else {
								teamstore_result.remove(records);
							}
						}
					}],
			store : teamstore_result,
			columns : [checkboxSM_result, {
						header : "指标编号",
						width : 300,
						dataIndex : "f1",
						editor : paraSelect
					}, {
						header : "指标类型",
						width : 220,
						dataIndex : "f2",
						editor : new Ext.form.TextField({})
					}, {
						header : "指标名称",
						width : 220,
						dataIndex : "f3",
						editor : new Ext.form.TextField({})
					}],
			stripeRows : true,
			clicksToEdit : 1,
			sm : checkboxSM_result
		});

grid_express_result.on('cellclick', function(grid, row, col) {// 获取编辑的行数，从0开始，
			rowNo1 = row;
		});