Ext.onReady(function() {

			// 查询panel
			var searchPanel = new Ext.form.FormPanel({
						title : '投资总揽  > 客户资总揽',
						region : 'north',
						height : 100,
						labelWidth : 150,// label的宽度
						labelAlign : 'right',
						frame : true,
						autoScroll : true,
						region : 'north',
						split : true,
						items : [{
									layout : 'column',
									items : [{
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : 'textfield',
															name : 'productName',
															fieldLabel : '客户姓名',
															anchor : '50%'
														}]
											}]
								}],
						buttonAlign : 'center',
						buttons : [{
									text : '查询'
								}, {
									text : '重置',
									handler : function() {
										productSearchPanel.getForm().reset();
									}
								}]
					});

			// 增长率规划 数据源 DEMO
			var eduFields = [{
						name : 'a0'
					}, {
						name : 'a1'
					}, {
						name : 'a2'
					}, {
						name : 'a3'
					}];
			var eduData = [['0001', '张三', '8876.09', ''],
					['0002', '李四', '766544.09', '1223.00'],
					['0003', '五XX', '23445.44', '334.23'],
					['0004', '张XX', '222344.56', '3456.23'],
					['0005', '软三', '256778.21', '2234.12']];
			// 定义自动当前页行号
			var num = new Ext.grid.RowNumberer({
						header : 'No.',
						width : 28
					});

			var sm = new Ext.grid.CheckboxSelectionModel({
						singleSelect : true
					});

			// gridtable中的列定义
			var eduColumns = new Ext.grid.ColumnModel([num, sm, {
						header : '客户编号',
						dataIndex : 'a0',
						id : "productId",
						sortable : true
					}, {
						header : '客户姓名',
						dataIndex : 'a1',
						id : 'productName',
						sortable : true
					}, {
						header : '客户投资总资产',
						dataIndex : 'a2',
						id : 'catlName',
						sortable : true,
						align : 'right'
					}, {
						header : '客户单日赢利率',
						dataIndex : 'a3',
						id : 'productStartDate',
						sortable : true,
						align : 'right'
					}]);

			var eduInfoStore = new Ext.data.ArrayStore({
						fields : eduFields,
						data : eduData
					});

			// ***********************
			// 每页显示条数下拉选择框
			var spagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'],
											[50, '50条/页'], [100, '100条/页'],
											[250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value',
						displayField : 'text',
						value : '20',
						forceSelection : true,
						width : 85
					});

			// 分页工具栏
			var sbbar = new Ext.PagingToolbar({
						pageSize : parseInt(spagesize_combo.getValue()),
						store : eduInfoStore,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', spagesize_combo]
					});
			// ***********************

			// 理财产品查询结果grid
			var resultGridPanel = new Ext.grid.GridPanel({
						frame : true,
						id : 'resultGridPanel',
						store : eduInfoStore,
						region : 'center',
						loadMask : true,
						cm : eduColumns,
						sm : sm,
						bbar : sbbar, // 分页工具栏
						viewConfig : {
							forceFit : true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
					
			//双击事件		
			resultGridPanel.addListener('rowclick', function(){
				showWinBase.show();
				// ----  总揽 
				//显示雷达图
				displayRader2();
				//显示资占比
				showCharDive1();
				showCharLine1();
				
				//-----资产结构
				showCharDive3();
				//投资风险占比
				showCharDive4();
				//投资风险占比
				showCharDive5();
				
				
				//---产品结构
				showCharDive6();
				showCharDive7();
				showCharDive8();
			});   		

			// 页面布局
			new Ext.Viewport({// 页面展示
				layout : 'fit',
				frame : true,
				layout : 'border',
				items : [searchPanel, resultGridPanel]
			});

		});