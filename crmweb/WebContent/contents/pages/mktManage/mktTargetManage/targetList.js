var tarColumns = new Ext.grid.ColumnModel({
			columns : [{
						header : '指标名称',
						width : 120,
						align : 'center',
						dataIndex : 'targetName',
						sortable : true
					}, {
						header : '指标值',
						width : 120,
						align : 'center',
						dataIndex : 'targetValue',
						sortable : true
					}, {
						header : '执行对象',
						width : 120,
						align : 'center',
						dataIndex : 'excObj',
						sortable : true
					},{
						header : '创建人',
						width : 120,
						align : 'center',
						dataIndex : 'tarCreatUser',
						sortable : true
					}, {
						header : '创建日期',
						width : 120,
						align : 'center',
						dataIndex : 'taiCreatDate',
						sortable : true
					}]
		});

var tarRecord = Ext.data.Record.create([ {
			name : 'targetName'
		}, {
			name : 'targetValue'
		}, {
			name : 'excObj'
		}, {
			name : 'tarCreatUser'
		}, {
			name : 'taiCreatDate'
		}]);

var tarData = {
	num : 8,
	rows : [{
				"targetName" : "存款余额增量",
				"targetValue" : "10,000,000.00",
				"excObj" : "北京分行营业部",
				"tarCreatUser" : "客户经理主管",
				"taiCreatDate" : "2011-04-01"
			}]

};
var tarReader = new Ext.data.JsonReader({
			totalProperty : 'num',
			idProperty : 'targetName',
			root : 'rows'
		}, tarRecord);
var tarStore = new Ext.data.Store({
			reader : tarReader
		});

tarStore.loadData(tarData);

var tarListPanel = new Ext.grid.GridPanel({
			height : 445,
			store : tarStore,
			frame : true,
			cm : tarColumns,
			stripeRows : true,
			tbar : [{
						text : '新增',
						iconCls : 'page_addIcon',
						handler : function() {
							addInit();
						}
					}, '-', {
						text : '修改',
						iconCls : 'page_editIcon',
						handler : function() {
							editInit();
						}

					}, '-', {
						text : '删除',
						iconCls : 'page_delIcon',
						handler : function() {
							confirm("确定删除吗?");
						}
					}]
		});

// 新增窗口展示的from
var addTarForm = new Ext.form.FormPanel({
			labelWidth : 150,
			height : 300,
			frame : true,
			region : 'center',
			autoScroll : true,
			buttonAlign : 'center',
			items : [{
				layout : 'column',
				items : [{
							columnWidth : .5,
							layout : 'form',
							items : [{
										fieldLabel : '指标名称',

										labelStyle : {
											width : '120px'
										},
										width : '100',
										name : 'targetAddName',
										id : 'targetAddName',
										xtype : 'combo', resizable : true,
										mode : 'local',
										store : new Ext.data.ArrayStore({
													id : 0,
													fields : ['myId',
															'displayText'],
													data : [[1, '存款余额增量'],
															[2, '存款日均']]
												}),
										valueField : 'myId',
										displayField : 'displayText',
										triggerAction : 'all',
										anchor : '90%'
									},
{
										xtype : 'textfield',
										labelStyle : {
											width : '120px'
										},
										width : 200,
										fieldLabel : '执行对象',
										id : 'excObj',
										name : 'excObj',
										anchor : '90%'
									}
									]
						}, {
							columnWidth : .5,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								labelStyle : {
									width : '120px'
								},
								width : 200,
								fieldLabel : '指标值',
								id : 'targetValue',
								name : 'targetValue',
								anchor : '90%'
							}]
						}

				]

			}],

			buttons : [

			{

						text : '保  存',
						handler : function() {
							alert("新增成功");
							addTarWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							addTarWindow.hide();
						}
					}]

		});

// 定义新增窗口
var addTarWindow = new Ext.Window({
			title : '指标新增',
			plain : true,
			layout : 'fit',
			width : 800,
			height : 200,
			resizable : true,
			draggable : true,
			closable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			loadMask : true,
			maximizable : true,
			collapsible : true,
			titleCollapse : true,
			buttonAlign : 'right',
			border : false,
			items : [addTarForm]
		});
//
// //定义修改窗口
// var editPlanWindow = new Ext.Window({
// title : '营销计划修改',
// plain : true,
// layout : 'fit',
// width : 800,
// height : 500,
// resizable : true,
// draggable : true,
// closable : true,
// closeAction : 'hide',
// modal : true, // 模态窗口
// loadMask : true,
// maximizable : true,
// collapsible : true,
// titleCollapse : true,
// border : false,
// items : [editPlanPanel]
//
// });
//
// 展示新增窗口
function addInit() {
	addTarWindow.show();

}
// // 展示修改窗口
// function editInit() {
// editPlanWindow.show();
// }
//
