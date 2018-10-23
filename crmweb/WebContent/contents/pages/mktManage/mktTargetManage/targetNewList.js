var tarNewColumns = new Ext.grid.ColumnModel({
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

var tarNewRecord = Ext.data.Record.create([{
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

var tarNewData = {
	num : 8,
	rows : []

};
var tarNewReader = new Ext.data.JsonReader({
			totalProperty : 'num',
			idProperty : 'targetName',
			root : 'rows'
		}, tarNewRecord);
var tarNewStore = new Ext.data.Store({
			autoDestroy : true,
			reader : tarNewReader
		});

tarNewStore.loadData(tarNewData);

var tarNewListPanel = new Ext.grid.EditorGridPanel({
			height : 200,
			store : tarNewStore,
			frame : true,
			cm : tarNewColumns,
			stripeRows : true,
			clicksToEdit : 1,
			buttonAlign : 'center',
			tbar : [{
						text : '新增',
						iconCls : 'page_addIcon',
						handler : function() {
							addInit();
						}
					}, {
						text : '删除',
						iconCls : 'page_addIcon',
						handler : function() {
							confim('确定删除吗?');
							alert('删除成功');
						}
					}]
		});
// 展示新增行
function addInit() {
	var Plant = tarNewListPanel.getStore().recordType;
	var p = new Plant({
				targetName : "",
				targetValue : "",
				excObj : "",
				tarCreatUser : "客户经理主管",
				tarCreatDate : "2011-04-01"
			});
	tarNewListPanel.stopEditing();
	tarNewStore.insert(0, p);
	tarNewListPanel.startEditing(0, 0);

}
