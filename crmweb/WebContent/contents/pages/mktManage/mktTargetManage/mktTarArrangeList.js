Ext.onReady(function() {

	var searchPanel = new Ext.form.FormPanel({
		labelWidth : 100,
		height : 95,
		frame : true,
		region : 'north',
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [
			         
					{
						columnWidth : .4,
						layout : 'form',
						labelWidth:120,
						items : [
								{
									xtype : 'textfield',
									labelStyle : {
										width : '120px'
									},
									Width : '60',
									id : 'taskName',
									name : 'taskName',
									fieldLabel : '任务名称',
									anchor : '90%'
								},
								{
									columnWidth : .25,
									layout : 'form',
									items : [ {

										xtype : 'combo', resizable : true,
										id : 'taskType',
										name : 'taskType',
										fieldLabel : '任务类型',
										labelStyle : {
											width : '120px'
										},
										width : '100',
										mode : 'local',
										store : new Ext.data.ArrayStore({
											fields : [ 'myId', 'displayText' ],
											data : [ [ 1, '全年综合经营任务' ],
													[ 2, '季度综合经营任务' ],
													[ 3, '全月综合经营任务' ],
													[ 4, '阶段性经营任务' ],
													[ 5, '专项任务' ],
													[ 6, '产品营销任务' ],
													[ 7, '其他营销任务' ] ]
										}),
										valueField : 'myId',
										displayField : 'displayText',
										anchor : '90%'
									} ]
								}
								]
					},
					{
						columnWidth : .4,
						layout : 'form',
						labelWidth:120,
						items : [
								{
									xtype : 'combo', resizable : true,
									id : 'taskStatus',
									name : 'taskStatus',
									fieldLabel : '任务状态',

									mode : 'local',
									store : new Ext.data.ArrayStore({
										fields : [ 'myId', 'displayText' ],
										data : [ [ 1, '暂存' ], [ 2, '执行中' ],
												[ 3, '已关闭' ] ]
									}),
									valueField : 'myId',
									displayField : 'displayText',
									anchor : '90%'
								}, 
								{
									
									layout : 'column',
									xtype : 'panel',
									items : [

									{
										columnWidth : .55,
										layout : 'form',
										labelWidth:120,
										items : {
											fieldLabel : '任务下达日期',
											xtype : 'datefield',
											id : 'createDateS',
											name : 'createDateS',
											anchor : '100%'
										}
									},

									{
										columnWidth : .45,
										layout : 'form',
										labelStyle : 'text-align:center',
										labelAlign : 'right',
										labelSeparator : '',
										labelWidth : 20,
										items : {
											xtype : 'datefield',
											fieldLabel : '至',
											// xtype:'textfield',
											name : 'createDateE',
											id : 'createDateE',
											anchor : '78%'
										}
									}

									]
								} ]
					},
					{
						layout : 'form',
						columnWidth : .1,
						items :
							[
						         {xtype:'label',text:''},
						         {
									xtype : 'button',
									text : '查 询',
									width:80,
									handler : function() {
									}
									
								}]
					}					
					]
		} ]
	});

	var columns = new Ext.grid.ColumnModel({
		columns : [ {
			header : '任务状态',
			width : 180,
			align : 'center',
			dataIndex : 'taskStatus',
			sortable : true
		}, {
			header : '任务名称',
			width : 220,
			align : 'center',
			dataIndex : 'taskName',
			sortable : true
		}, {
			header : '任务类型',
			width : 200,
			align : 'center',
			dataIndex : 'taskType',
			sortable : true
		}, {
			header : '任务下达日期',
			width : 150,
			align : 'center',
			dataIndex : 'arrangeDate',
			sortable : true
		}, {
			header : '创建人',
			width : 150,
			align : 'center',
			dataIndex : 'creatUser',
			sortable : true
		}, {
			header : '创建日期',
			width : 150,
			align : 'center',
			dataIndex : 'creatDate',
			sortable : true
		} ]
	});

	var record = Ext.data.Record.create([ {
		name : 'taskStatus'
	}, {
		name : 'taskName'
	}, {
		name : 'taskType'
	}, {
		name : 'arrangeDate'
	}, {
		name : 'creatUser'
	}, {
		name : 'creatDate'
	} ]);

	var data = {
		num : 6,
		rows : [ {
			"taskStatus" : "执行中",
			"taskName" : "2011年全行年任务",
			"taskType" : "全年综合经营任务",
			"arrangeDate" : "2011-04-01",
			"creatUser" : "客户经理主管",
			"creatDate" : "2011-04-01"
		}, {
			"taskStatus" : "已关闭",
			"taskName" : "2010年全行年任务",
			"taskType" : "全年综合经营任务",
			"arrangeDate" : "2010-04-01",
			"creatUser" : "客户经理主管",
			"creatDate" : "2011-04-01"
		} ]

	};
	var reader = new Ext.data.JsonReader({
		totalProperty : 'num',
		idProperty : 'taskName',
		root : 'rows'
	}, record);
	var store = new Ext.data.Store({
		reader : reader
	});

	store.loadData(data);

	var listPanel = new Ext.grid.GridPanel({
		store : store,
		frame : true,
		cm : columns,
		stripeRows : true,
		tbar : [ {
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
		},'-', {
			text : '指标调整',
			iconCls : 'tar_editIcon',
			handler : function() {
				tarEditInit();
			}}, '-', {
			text : '关闭',
			iconCls : 'page_cloIcon',
			handler : function() {
				confirm("确定关闭吗?");
			}}, '-',{
				text : '任务下达',
				iconCls : 'page_desIcon',
				handler : function() {
					confirm("确认下达吗?");
					alert("下达成功");
				}}
		 ],
		region : 'center',
		store : store,
		frame : true,
		bbar : {
			xtype : 'paging',
			pageSize : 10,
			store : store,
			displayInfo : true,
			displayMsg : '显示1条到10条,共2条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', {
				xtype : 'textfield',
				value : '10'
			} ]
		}
	});

	// 新增窗口展示的from
	var addTaskForm = new Ext.form.FormPanel({
		labelWidth : 150,
		height : 300,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [
				{
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									id : 'taskAddName',
									name : 'taskAddName',
									xtype : 'textfield',
									fieldLabel : '任务名称',
									labelStyle : {
										width : '120px'
									},
									width : '100',
									anchor : '90%'
								}, {
									id : 'taskAddExcType',
									name : 'taskAddExcType',
									xtype : 'combo', resizable : true,
									labelStyle : {
										width : '120px'
									},
									width : '100',
									fieldLabel : '任务执行人类型',
									mode : 'local',
									store : new Ext.data.ArrayStore({
										id : 0,
										fields : [ 'myId',
												'displayText' ],
										data : [ [ 1, '机构' ],
												[ 2, '客户经理' ]]
									}),
									valueField : 'myId',
									displayField : 'displayText',
									triggerAction : 'all',
									anchor : '90%'
								}, {
									xtype : 'datefield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									fieldLabel : '任务结束日期',
									id : 'endDate',
									name : 'endDate',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .5,
								layout : 'form',
								items : [
										{
											fieldLabel : '任务类型',
											labelStyle : {
												width : '120px'
											},
											width : '100',
											name : 'taskAddType',
											id : 'taskAddType',
											xtype : 'combo', resizable : true,
											mode : 'local',
											store : new Ext.data.ArrayStore({
												id : 0,
												fields : [ 'myId',
														'displayText' ],
												data : [ [ 1, '全年综合经营任务' ],
														[ 2, '季度综合经营任务' ],
														[ 3, '全月综合经营任务' ],
														[ 4, '阶段性经营任务' ],
														[ 5, '专项任务' ],
														[ 6, '产品营销任务' ],
														[ 7, '其他营销任务' ] ]
											}),
											valueField : 'myId',
											displayField : 'displayText',
											triggerAction : 'all',
											anchor : '90%'
										}, {
											id : 'beginTime',
											name : 'beginTime',
											xtype : 'datefield',
											fieldLabel : '任务开始日期',
											labelStyle : {
												width : '120px'
											},
											width : 100,
											anchor : '90%'

										} ]
							}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '任务描述',
						id : 'taskDesc',
						name : 'taskDesc',
						anchor : '90%'
					},tarNewListPanel ],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							alert("新增成功");
							addTaskWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							addTaskWindow.hide();
						}
					} ]
				}

		]

	});

	// 修改基本信息展示的from
	var editTaskForm = new Ext.form.FormPanel({
		labelWidth : 150,
		height : 445,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [
				{
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									id : 'taskEditName',
									name : 'taskEditName',
									xtype : 'textfield',
									fieldLabel : '任务名称',
									labelStyle : {
										width : '120px'
									},
									width : '100',
									anchor : '90%'
								}, {
									id : 'taskExcTypeE',
									name : 'taskExcTypeE',
									xtype : 'combo', resizable : true,
									labelStyle : {
										width : '120px'
									},
									width : '100',
									fieldLabel : '任务执行人类型',
									mode : 'local',
									store : new Ext.data.ArrayStore({
										id : 0,
										fields : [ 'myId',
												'displayText' ],
										data : [ [ 1, '机构' ],
												[ 2, '客户经理' ]]
									}),
									valueField : 'myId',
									displayField : 'displayText',
									triggerAction : 'all',
									anchor : '90%'
								}, {
									xtype : 'datefield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									fieldLabel : '任务结束日期',
									id : 'endDateE',
									name : 'endDateE',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .5,
								layout : 'form',
								items : [
										{
											fieldLabel : '任务类型',

											labelStyle : {
												width : '120px'
											},
											width : '100',
											name : 'taskEditType',
											id : 'taskEditType',
											xtype : 'combo', resizable : true,
											mode : 'local',
											store : new Ext.data.ArrayStore({
												id : 0,
												fields : [ 'myId',
														'displayText' ],
												data : [ [ 1, '全年综合经营任务' ],
														[ 2, '季度综合经营任务' ],
														[ 3, '全月综合经营任务' ],
														[ 4, '阶段性经营任务' ],
														[ 5, '专项任务' ],
														[ 6, '产品营销任务' ],
														[ 7, '其他营销任务' ] ]
											}),
											valueField : 'myId',
											displayField : 'displayText',
											triggerAction : 'all',
											anchor : '90%'
										}, {
											id : 'beginTimeE',
											name : 'beginTimeE',
											xtype : 'datefield',
											fieldLabel : '任务开始日期',
											labelStyle : {
												width : '120px'
											},
											width : 100,
											anchor : '90%'

										} ]
							}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
						xtype : 'textarea',
						labelStyle : {
							width : '120px'
						},
						width : 200,
						fieldLabel : '任务描述',
						id : 'taskDescE',
						name : 'taskDescE',
						anchor : '90%'
					}, tarEditListPanel],

					buttons : [

					{

						text : '保  存',
						handler : function() {
							alert("修改成功");
							editTaskWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							editTaskWindow.hide();
						}
					} ]
				}

		]

	});

	// 指标调整展示的from
	var editTargetForm = new Ext.form.FormPanel({
		labelWidth : 150,
		height : 445,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [tarRedListPanel],
					buttons : [

					{

						text : '保  存',
						handler : function() {
							alert("调整成功");
							editTargetWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							editTargetWindow.hide();
						}
					} ]


	});
	
	// 修改窗口展示的from
	var editTaskPanel = new Ext.Panel({
		labelWidth : 150,
		// height : 480,
		// frame : true,
		layout : 'fit',
		autoScroll : true,
		buttonAlign : "center",
		items : [ editTaskForm ]
	});

	// 定义新增窗口
	var addTaskWindow = new Ext.Window({
		title : '营销任务新增',
		plain : true,
		layout : 'fit',
		width : 800,
		height : 450,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ addTaskForm ]
	});

	// 定义修改窗口
	var editTaskWindow = new Ext.Window({
		title : '营销任务修改',
		plain : true,
		layout : 'fit',
		width : 800,
		height : 450,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ editTaskForm ]
	});

	// 定义指标调整窗口
	
	var editTargetWindow = new Ext.Window({
		title : '指标调整',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 280,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ editTargetForm ]
	});
	
	// 展示新增窗口
	function addInit() {
		addTaskWindow.show();

	}
	// 展示修改窗口
	function editInit() {
		editTaskWindow.show();
	}

	// 展示指标调整窗口
	function tarEditInit() {
		editTargetWindow.show();
	}
	
	var view = new Ext.Viewport({

		layout : 'border',
		items : [
		
		 {
		 region : 'south',
		 id : 'south-panel',
		 collapsible : true,
		 title : "流程提示",
		 split : true,
		 height : 90,
		 minSize : 80,
		 maxSize : 200,
		 collapsible : true,
		 hidden : false,
		 margins : '0 0 0 0',
		 contentEl : 's1'
		 },

		{
			region : 'center',
			id : 'center-panel',
			title : "营销任务列表",
			layout : 'fit',
			items : [ listPanel ]
		},

		{
			region : 'north',
			id : 'north-panel',
			title : "营销任务查询",
			height : 95,
			layout : 'fit',
			items : [ searchPanel ]
		}

		]
	});

})