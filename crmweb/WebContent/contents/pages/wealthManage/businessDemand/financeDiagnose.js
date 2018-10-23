/**
 * 财富管理-业务需求：财务诊断（静态页面） wzy，2013-09-05
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

	// 证件类型下拉框的数据查询
	var certTypStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=XD000040'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	certTypStore.load();

	// 查询条件Form对象
	var sendForm = new Ext.form.FormPanel({
				id : 'sendForm',
				title : '财务诊断查询',
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
													xtype : 'textfield',
													fieldLabel : '客户号',
													name : 'custNo',
													anchor : '95%'
												}]
									}, {
										columnWidth : .25,
										labelWidth : 100,
										layout : 'form',
										items : [{
													xtype : 'combo',
													fieldLabel : '证件类型',
													name : 'certType',
													anchor : '95%',
													editable : false,
													emptyText : '请选择',
													mode : 'local',
													triggerAction : 'all',
													store : certTypStore,
													valueField : 'key',
													displayField : 'value',
													anchor : '95%'
												}]
									}, {
										columnWidth : .25,
										labelWidth : 100,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '证件号码',
													name : 'crtyNo',
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
			['100', '王一平', '已生成', '2013-08-12', '张生保', '紫金农商行方山支行',
					'2013-08-12'],
			['101', '李小平', '未生成', '2013-08-14', '灵中秋', '紫金农商行滨江支行',
					'2013-08-14'],
			['s101', '张云', '未生成', '2013-08-23', '生注天', '常熟农商行叠石桥支行',
					'2013-08-23'],
			['PCD20121232', '冯文德', '未生成', '2013-08-11', '张生保', '紫金农商行滨江支行',
					'2013-08-11'],
			['PCD20121248', '甘文丽', '已生成', '2013-08-07', '生注天', '紫金农商行方山支行',
					'2013-08-07'],
			['PCD20121249', '韩刚', '未生成', '2013-08-05', '灵中秋', '常熟农商行叠石桥支行',
					'2013-08-05'],
			['PCD20121250', '何静', '未生成', '2013-08-18', '生注天', '紫金农商行滨江支行',
					'2013-08-18']];

	// 列表字段名称
	var prodRecord = Ext.data.Record.create([{
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
			}, {
				name : 'f7'
			}]);

	// 列表数据存储对象
	var prodStore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.ArrayReader({}, prodRecord)
			});

	// 列表表头定义
	var procm = new Ext.grid.ColumnModel([prorownum, prosm, {
				header : '客户号',
				dataIndex : 'f1',
				sortable : true,
				width : 120
			}, {
				header : '客户名称',
				dataIndex : 'f2',
				sortable : true,
				width : 150
			}, {
				header : '报告状态',
				dataIndex : 'f3',
				sortable : true,
				width : 150
			}, {
				header : '创建日期',
				dataIndex : 'f4',
				sortable : true,
				width : 150
			}, {
				header : '创建人',
				dataIndex : 'f5',
				sortable : true,
				width : 150
			}, {
				header : '创建机构',
				dataIndex : 'f6',
				sortable : true,
				width : 200
			}, {
				header : '最近维护日期',
				dataIndex : 'f7',
				sortable : true,
				width : 150
			}]);

	// 列表定义
	var Grid = new Ext.grid.GridPanel({
		title : '财务诊断列表',
		layout : 'fit',
		region : 'center',
		store : prodStore,
		sm : prosm,
		cm : procm,
		tbar : [{
					text : '新增',
					iconCls : 'addIconCss',
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
						var f3 = checkedNodes[0].data.f3;
						if (f3 != '未生成' && f3 != '6') {
							Ext.Msg.alert('提示', '只能选择【未生成】财务诊断报告的客户！');
							return false;
						}
						addWin.setTitle('新增财务诊断报告');
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
						var f3 = checkedNodes[0].data.f3;
						if (f3 != '已生成' && f3 != '6') {
							Ext.Msg.alert('提示', '只能选择【已生成】财务诊断报告的客户！');
							return false;
						}
						addWin.setTitle('修改财务诊断报告');
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
						var checkedNodes = Grid.getSelectionModel().selections.items;
						if (checkedNodes.length > 1) {
							Ext.Msg.alert('提示', '只能选择一条记录！');
							return;
						}
						var f3 = checkedNodes[0].data.f3;
						if (f3 != '已生成' && f3 != '6') {
							Ext.Msg.alert('提示', '只能选择【已生成】财务诊断报告的客户！');
							return false;
						}
						Ext.MessageBox.confirm('提示', '确定删除吗？', function(
										buttonId) {
									if (buttonId.toLowerCase() == "no") {
										return;
									}
									Ext.Msg.alert('提示', '删除成功！');
								});
					}
				}, '-', {
					text : '查看报告',
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
						var f3 = checkedNodes[0].data.f3;
						if (f3 != '已生成' && f3 != '6') {
							Ext.Msg.alert('提示', '只能选择【已生成】财务诊断报告的客户！');
							return false;
						}
						addWin.setTitle('查看财务诊断报告');
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

	var addTapPanel = new Ext.TabPanel({
				activeTab : 0,
				tabPosition : 'top',// 控制tab页签显示的位置（顶部：top；底部：bottom）
				height : 430,
				buttonAlign : "center",
				items : [{
							title : '资产负债表',
							items : [zcfzPanel],
							listeners : {
								'activate' : function() {
									Ext.getCmp('save_b_id').show();// 显示“保存”按钮
								}
							}
						}, {
							title : '收支表',
							items : [szbPanel],
							listeners : {
								'activate' : function() {
									Ext.getCmp('save_b_id').show();// 显示“保存”按钮
									gridSpan(szbPanel, "row", "[f1]","");
								}
							}
						}, {
							title : '财务现状',
							items : [layoutPanel],
							listeners : {
								'activate' : function() {
									Ext.getCmp('save_b_id').hide();// 隐藏“保存”按钮
								}
							}
						}, {
							title : '财务指标分析',
							items : [cwzbfxPanel],
							listeners : {
								'activate' : function() {
									Ext.getCmp('save_b_id').show();// 显示“保存”按钮
								}
							}
						}]
			});

	// 定义新增财务诊断报告窗口
	var addWin = new Ext.Window({
				closeAction : 'hide',
				title : '新增财务诊断报告',
				width : 820,
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
							text : '保存',
							handler : function() {

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