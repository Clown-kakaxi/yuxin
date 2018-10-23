/**
 * 财富管理-业务需求：快速规划（静态页面） hujun，2013-09-11
 */
Ext.onReady(function() {
	Ext.QuickTips.init();

	// 客户选择组件
	var custSelectPartAdd = new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称',
				labelWidth : 100,
				name : 'custName',
				custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
				custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				anchor : '95%',
				hiddenName : 'custId',
				callback : function() {// 回调方法，给其它字段设置相关属性值
				}
			});

	// 查询条件Form对象
	var sendForm = new Ext.form.FormPanel({
				id : 'sendForm',
				title : '快速规划查询',
				frame : true,
				region : 'north',
				height : 100,
				border : false,
				labelAlign : 'right',
				items : [{
							layout : 'column',
							items : [{
										columnWidth : .25,
										labelWidth : 100,
										layout : 'form',
										items : [custSelectPartAdd]
									}, {
										columnWidth : .25,
										labelWidth : 100,
										layout : 'form',
										items : [{
													xtype : 'datefield',
													fieldLabel : '规划日期',
													name : 'startTime',
													anchor : '95%'
												}]
									}, {
										columnWidth : .25,
										labelWidth : 100,
										layout : 'form',
										items : [{
													xtype:'datefield',
													fieldLabel:'至',
													name:'endTime',
													anchor:'95%'
										}]
									}, {
										columnWidth : .25,
										labelWidth : 100,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '规划名称',
													name : 'planName',
													anchor : '95%'
												}]
									}]
						}],
				buttonAlign : 'center',
				buttons : [{
							text : '查询',
							handler : function() {
							}
						}, {
							text : '重置',
							handler : function() {
								sendForm.getForm().reset();
							}
						}]
			});

	// 列表中的复选框
	var prosm = new Ext.grid.CheckboxSelectionModel();

	// 列表记录序号
	var prorownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 列表数据定义
	var data = [
			['教育规划', '王一平', '330000', '2013-08-12',],
			['家庭理财规划', '王小贱', '120000', '2013-08-14'],
			['养老规划', '张平', '23000000', '2013-08-23'],
			['养老规划', '冯文德', '120000', '2013-08-11'],
			['家庭理财规划', '甘文丽', '24000000', '2013-08-07'],
			['教育规划', '韩刚', '2000000', '2013-08-05'],
			['住房规划', '何静', '1230000', '2013-08-18']];

	// 列表字段名称
	var prodRecord = Ext.data.Record.create([{
				name : 'f1'
			}, {
				name : 'f2'
			}, {
				name : 'f3'
			}, {
				name : 'f4'
			}]);

	// 列表数据存储对象
	var prodStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.ArrayReader({}, prodRecord)
			});

	// 列表表头定义
	var procm = new Ext.grid.ColumnModel([prorownum, prosm, {
				header : '规划名称',
				dataIndex : 'f1',
				sortable : true,
				width : 170
			}, {
				header : '客户名称',
				dataIndex : 'f2',
				sortable : true,
				width : 160
			}, {
				header : '目标需求金额',
				dataIndex : 'f3',
				sortable : true,
				align:'right',
				renderer: money('0,000.00'),
				width : 160
			}, {
				header : '创建日期',
				dataIndex : 'f4',
				align:'right',
				sortable : true,
				width : 160
			}]);

	// 列表定义
	var Grid = new Ext.grid.GridPanel({
		title : '快速规划列表',
		layout : 'fit',
		region : 'center',
		store : prodStore,
		sm : prosm,
		cm : procm,
		tbar : [{
					text : '新增',
					iconCls : 'addIconCss',
					handler : function() {
						addWin.setTitle('新增快速规划');
						addWin.show();
					}
				}, '-', {
					text : '修改',
					iconCls : 'editIconCss',
					handler : function() {
						var selectRe = Grid.getSelectionModel().getSelected();
						if (selectRe == null || selectRe == "undefined") {
							Ext.Msg.alert('提示', '请选择一条记录！');
							return;
						}
						var checkedNodes = Grid.getSelectionModel().selections.items;
						if (checkedNodes.length > 1) {
							Ext.Msg.alert('提示', '只能选择一条记录！');
							return;
						}
						addWin.setTitle('修改快速规划');
						addWin.show();
					}
				}, '-', {
					text : '删除',
					iconCls : 'deleteIconCss',
					handler : function() {
						var selectRe = Grid.getSelectionModel().getSelected();
						if (selectRe == null || selectRe == "undefined") {
							Ext.Msg.alert('提示', '请选择一条记录！');
							return;
						}
//						var checkedNodes = Grid.getSelectionModel().selections.items;
//						if (checkedNodes.length > 1) {
//							Ext.Msg.alert('提示', '只能选择一条记录！');
//							return;
//						}
						Ext.MessageBox.confirm('提示', '确定删除吗？', function(
										buttonId) {
									if (buttonId.toLowerCase() == "no") {
										return;
									}
									var Nodes = Grid.getSelectionModel().getSelections().length;
									for(var i=Nodes-1;i>=0;i--){
										var record=Grid.getSelectionModel().getSelections()[i];
										prodStore.remove(record);
									};
				                 Ext.Msg.alert('提示',"删除成功");
									Ext.Msg.alert('提示', '删除成功！');
								});
					}
				}, '-', {
					text : '详情',
					iconCls : 'detailIconCss',
					handler : function() {
						var selectRe = Grid.getSelectionModel().getSelected();
						if (selectRe == null || selectRe == "undefined") {
							Ext.Msg.alert('提示', '请选择一条记录！');
							return;
						}
						var checkedNodes = Grid.getSelectionModel().selections.items;
						if (checkedNodes.length > 1) {
							Ext.Msg.alert('提示', '只能选择一条记录！');
							return;
						}
						addWin.setTitle('查看快速规划报告');
						addWin.show();
					}
				}],
		bbar : new Ext.PagingToolbar({
					pageSize : '10',
					store : prodStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					emptyMsg : "没有符合条件的记录"
				})
	});

	// 列表数据装载
	prodStore.load(data);

	// 定义新增财务诊断报告窗口
	var addWin = new Ext.Window({
				closeAction : 'hide',
				title : '新增快速规划',
				width : 840,
				height : 450,
				buttonAlign : 'center',
				resizable : true,
				titleCollapse : true,
				maximizable : true,
				collapsible : true,
				layout : 'fit',
				modal : true,
				items : [addTapPanel],
				buttons : [{
							id : 'save_b_id',
							text : '下一步',
							handler : function() {
								bgWin.show(),
								addWin.hide();
							}
						}, {
							text : '关闭',
							handler : function() {
								addWin.hide();
							}
						}]
			});

	// 页面布局
	var view = new Ext.Viewport({
				layout : 'fit',
				items : [{
							layout : 'border',
							items : [sendForm, Grid]
						}]
			});

});