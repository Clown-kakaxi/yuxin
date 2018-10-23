var tarRedColumns = new Ext.grid.ColumnModel({
			columns : [{
				header : '指标名称',
				width : 120,
				align : 'center',
				dataIndex : 'targetName',
				sortable : true,
				editor : new Ext.form.ComboBox({
							typeAhead : true,
							triggerAction : 'all',
							lazyRender : true,
							listClass : 'x-combo-list-small',
							mode : 'local',
							valueField : 'myId',
							displayField : 'displayText',
							store : new Ext.data.ArrayStore({
										id : 0,
										fields : ['myId', 'displayText'],
										data : [['存款余额增量', '存款余额增量'],
												['存款日均', '存款日均']]
									})
						})
			}, {
				header : '指标值',
				width : 120,
				align : 'center',
				dataIndex : 'targetValue',
				sortable : true,
				editor : new Ext.form.Field()
			}, {
				header : '执行对象',
				width : 120,
				align : 'center',
				dataIndex : 'excObj',
				sortable : true,
				editor : new Ext.form.Field()
			}, {
				header : '创建人',
				width : 120,
				align : 'center',
				dataIndex : 'tarCreatUser',
				sortable : true
			}, {
				header : '创建日期',
				width : 120,
				align : 'center',
				dataIndex : 'tarCreatDate',
				sortable : true
			}]
		});

var tarRedRecord = Ext.data.Record.create([{
			name : 'targetName'
		}, {
			name : 'targetValue'
		}, {
			name : 'excObj'
		}, {
			name : 'tarCreatUser'
		}, {
			name : 'tarCreatDate'
		}]);

var tarRedData = {
	num : 8,
	rows : [{
				"targetName" : "存款余额增量",
				"targetValue" : "10,000,000.00",
				"excObj" : "北京分行营业部",
				"tarCreatUser" : "客户经理主管",
				"tarCreatDate" : "2011-04-01"
			}]

};
var tarRedReader = new Ext.data.JsonReader({
			totalProperty : 'num',
			idProperty : 'targetName',
			root : 'rows'
		}, tarRedRecord);
var tarRedStore = new Ext.data.Store({
			autoDestroy : true,
			reader : tarRedReader
		});

tarRedStore.loadData(tarRedData);

var tarRedListPanel = new Ext.grid.EditorGridPanel({
			height : 200,
			store : tarRedStore,
			frame : true,
			cm : tarRedColumns,
			stripeRows : true,
			clicksToEdit : 1,
			buttonAlign : 'center',
			tbar : [{
						text : '新增',
						iconCls : 'page_addIcon',
						handler : function() {
							tarRedaddInit();
						}
					}, {
						text : '删除',
						iconCls : 'page_addIcon121',
						handler : function() {
							confirm('确定删除吗?');
							alert('删除成功');
						}
					}]
		});
// 展示新增行
function tarRedaddInit() {
	var Plant = tarRedListPanel.getStore().recordType;
	var p = new Plant({
				targetName : "",
				targetValue : "",
				excObj : "",
				tarCreatUser : "客户经理主管",
				tarCreatDate : "2011-04-01"
			});
	tarRedListPanel.stopEditing();
	tarRedStore.insert(0, p);
	tarRedListPanel.startEditing(0, 0);

}
