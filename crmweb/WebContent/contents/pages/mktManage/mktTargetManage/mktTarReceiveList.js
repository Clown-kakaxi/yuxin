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
// {
// layout : 'form',
// columnWidth : .1,
// buttonAlign : "center",
// items : [{
// xtype : 'button',
// text : '查询',
// labelStyle : {
// width : '120px'
// },
// handler : function() {
// },
// anchor : '10%'
// }]
// }
//								
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
						items : [
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
			header : '任务开始日期',
			width : 200,
			align : 'center',
			dataIndex : 'beginDate',
			sortable : true
		}, {
			header : '任务结束日期',
			width : 150,
			align : 'center',
			dataIndex : 'endDate',
			sortable : true
		}, {
			header : '任务执行客户 经理',
			width : 150,
			align : 'center',
			dataIndex : 'excCustManager',
			sortable : true
		}, {
			header : '任务执行机构',
			width : 150,
			align : 'center',
			dataIndex : 'excOrg',
			sortable : true
		} ]
	});

	var record = Ext.data.Record.create([ {
		name : 'taskStatus'
	}, {
		name : 'taskName'
	}, {
		name : 'beginDate'
	}, {
		name : 'endDate'
	}, {
		name : 'excCustManager'
	}, {
		name : 'excOrg'
	} ]);

	var data = {
		num : 6,
		rows : [ {
			"taskStatus" : "执行中",
			"taskName" : "2011年全行年任务",
			"beginDate" : "2011-04-01",
			"endDate" : "2011-09-10",
			"excCustManager" : "",
			"excOrg" : "北京分行营业部"
		}, {
			"taskStatus" : "已关闭",
			"taskName" : "2010年全行年任务",
			"beginDate" : "2010-02-01",
			"endDate" : "2011-01-01",
			"excCustManager" : "客户经理主管",
			"excOrg" : "北京分行营业部"
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
					text : '任务分解',
					iconCls : 'red_Icon',
					handler : function() {
						tarRedInit();}}, '-', {
			text : '关闭',
			iconCls : 'page_cloIcon',
			handler : function() {
				confirm("确定关闭吗?");
			}}
//					,'-',  {
//						text : '详细信息',
//						iconCls : 'red_Icon',
//						handler : function() {
//							tarDetInit();
//						}}
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

	// 任务分解展示的from
	var tarRedForm = new Ext.form.FormPanel({
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
									id : 'supTaskAddName',
									name : 'supTaskAddName',
									xtype : 'textfield',
									fieldLabel : '上级任务名称',
									value : '全年营销任务',
									labelStyle : {
										width : '120px'
									},
									disabled : true,
									width : '100',
									anchor : '90%'
								},{
									id : 'taskAddType',
									name : 'taskAddType',
									disabled : true,
									xtype : 'textfield',
									fieldLabel : '任务类型',
									labelStyle : {
										width : '120px'
									},
									value : '全年综合经营任务',
									
									width : '100',
									anchor : '90%'
								}, {
									xtype : 'datefield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									fieldLabel : '任务开始日期',
									id : 'beginAddDate',
									name : 'beginAddDate',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .5,
								layout : 'form',
								items : [{
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
									id : 'taskExcAddType',
									name : 'taskExcAddType',
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
								},
										{
											id : 'endTime',
											name : 'endTime',
											xtype : 'datefield',
											fieldLabel : '任务结束日期',
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
						id : 'taskAddDesc',
						name : 'taskAddDesc',
						anchor : '90%'
					},tarEditListPanel
					],
					
					buttons : [
					{

						text : '保  存',
						handler : function() {
							alert("分解成功,但为暂存状态，确认无误可进行任务下达!");
							tarRedWindow.show();
							tarRedWindow.hide();
						}

					}, {

						text : '提  交',
						handler : function() {
							confirm("提交后不可更改,确定提交吗?");
							alert("分解成功!");
							tarRedWindow.show();
							tarRedWindow.hide();
						}

					},
						{
						text : '取  消',
						handler : function() {
							tarRedWindow.hide();
						}
					} ]
				}

		]

	});



	// 定义任务分解窗口
	var tarRedWindow = new Ext.Window({
		title : '营销任务分解',
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
		buttonAlign : 'right',
		border : false,
		items : [ tarRedForm ]
	});

	// 展示任务分解窗口
	function tarRedInit() {
		tarRedWindow.show();

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