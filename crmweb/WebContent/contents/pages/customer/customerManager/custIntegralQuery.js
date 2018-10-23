Ext.onReady(function() {
	Ext.QuickTips.init();
	// 客户编号
	var cust_id = oCustInfo.cust_id;

	var tbar = new Ext.Toolbar({
		items : [ {
			text : '积分兑换历史查询',
			iconCls : 'detailIconCss',
			handler : function() {
				showWin.show();
			}
		}, '-', {
			text : '积分明细详情',
			iconCls : 'detailIconCss',
			handler : function() {
				showDetailWin.show();
			}
		} ]
	});
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
		id : "listPanel",
		title : "客户积分兑换情况",
		tbar : tbar,

		// 主键
		primary : "id",
		// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
		dbclick : false,
		// 查询路径设置
		stUrl : basepath + '/acrmFCiCustIntegral-info!indexPage.json?cust_id='
				+ cust_id,
		// 定义查询条件Form的高度
		seFormHeight : 0,
		// 定义增删详情页面弹出窗口高度
		winHeight : 450,
		// 定义增删详情页面弹出窗口宽度
		winWidth : 900,
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		// 定义高度
//		height : document.body.clientHeight,
		// 定义宽度
//		width : document.body.clientWidth - 240,
		// 定义显示结果列表高度
//		gridHeight : document.body.clientHeight - 100,
		frame : true,
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ {
			name : 'id'
		}, {
			name : 'custId'
		}, {
			name : 'custId',
			header : '客户编号',
			width : 100
		}, {
			name : 'custName',
			header : '客户名称',
			width : 100
		}, {
			name : 'custType',
			header : '客户类别',
			width : 100
		}, {
			name : 'countNum',
			header : '客户累计积分',
			type : 'float',
			width : 100
		}, {
			name : 'custCumCount',
			header : '客户当月积分',
			type : 'float',
			width : 100
		}, {
			name : 'custCumCost',
			header : '客户当月消费积分',
			type : 'float',
			width : 100
		}, {
			name : 'custCostSum',
			header : '客户累计消费积分',
			type : 'float',
			width : 100
		}, {
			name : 'custSpareCount',
			header : '客户可用积分',
			type : 'float',
			width : 100
		}, {
			name : 'etlDate',
			header : '平台日期',
			type : 'date',
			width : 100
		} ]

	});

	var searchPanel = new Ext.form.FormPanel({// 查询panel

		title : '积分兑换查询',
		height : 180,
		// buttonAlign:'center',
		labelWidth : 100,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		items : [ {
			autoHeight : true,
			items : [ {
				layout : 'column',
				buttonAlign : 'center',
				items : [
						{
							columnWidth : .50,
							layout : 'form',
							items : [
									{

										xtype : 'textfield',
										name : 'PRODUCT_ID',
										fieldLabel : '商品名称',
										anchor : '95%'

									},
									{
										fieldLabel : '商品分类',
										name : 'EVENT_TYP',
										xtype : 'combo',
										// editable:false,
										// allowBlank : false,
										labelStyle : 'text-align:right;',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										store : new Ext.data.ArrayStore({
											fields : [ 'myId', 'displayText' ],
											data : [ [ '家用电器', '家用电器' ],
													[ '手机数码', '手机数码' ],
													[ '图书期刊', '图书期刊' ],
													[ '音像制品', '音像制品' ],
													[ '汽车用品', '汽车用品' ],
													[ '运动健康', '运动健康' ],
													[ '医疗保健', '医疗保健' ],
													[ '箱包服装', '箱包服装' ],
													[ '食品饮料', '食品饮料' ],
													[ '优惠礼卷', '优惠礼卷' ] ]
										}),
										valueField : 'myId',
										displayField : 'displayText',
										anchor : '95%'
									} ]
						},
						{
							columnWidth : .50,
							layout : 'form',
							items : [ {

								fieldLabel : '商品价格',
								name : 'EVENT_TYP',

								xtype : 'combo',
								// editable:false,
								// allowBlank : false,
								labelStyle : 'text-align:right;',
								valueField : 'key',
								displayField : 'value',
								mode : 'local',
								typeAhead : true,
								forceSelection : true,
								triggerAction : 'all',
								emptyText : '请选择',
								selectOnFocus : true,
								store : new Ext.data.ArrayStore({
									fields : [ 'myId', 'displayText' ],
									data : [ [ '1千以下', '1千以下' ],
											[ ' 1千～5千', ' 1千～5千' ],
											[ '5千～1万', '5千～1万' ],
											[ '1万～5万', '1万～5万' ],
											[ '5万～10万', '5万～10万' ],
											[ ' 10万以上', ' 10万以上' ] ]
								}),
								valueField : 'myId',
								displayField : 'displayText',
								anchor : '95%'
							} ]
						}, {
							columnWidth : .50,
							layout : 'form',
							items : [ {
								xtype : 'datefield',
								editable : false,
								name : 'PROD_START_DATE_FROM',
								id : 'PROD_START_DATE_FROM',
								fieldLabel : '起始日期',
								format : 'Y-m-d', // 日期格式化
								labelStyle : 'text-align:right;',
								name : 'FS_DT',
								anchor : '95%'
							} ]
						}, {
							columnWidth : .50,
							layout : 'form',
							items : [ {
								xtype : 'datefield',
								editable : false,
								name : 'PROD_START_DATE_TO',
								id : 'PROD_START_DATE_TO',
								fieldLabel : '截止日期',
								format : 'Y-m-d', // 日期格式化
								labelStyle : 'text-align:right;',
								name : 'FS_DT',
								anchor : '95%'
							} ]
						}

				]
			} ]
		}

		],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {
				var start = Ext.getCmp('PROD_START_DATE_FROM').getValue();
				var end = Ext.getCmp('PROD_START_DATE_TO').getValue();
				if (start == '' && end != '') {
					Ext.Msg.alert('消息框', '请先选择开始时间！');
					Ext.getCmp('PROD_START_DATE_TO').reset();
					return;
				} else if (end != '' && start > end) {
					Ext.Msg.alert('消息框', '开始时间大于结束时间，请检查！');
					Ext.getCmp('PROD_START_DATE_TO').reset();
					return;
				}

				var parameters = searchPanel.getForm().getValues(false);

				productInfoStore.removeAll();
				productInfoStore.baseParams = {
					'condition' : Ext.util.JSON.encode(parameters)
				};
				productInfoStore.load({
					params : {
						start : 0,
						limit : parseInt(spagesize_combo.getValue())
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				searchPanel.getForm().reset();
				// prodTreePanel
				// .root.attributes.loader.url=basepath+"/queryprodleveltree.json?level=1";
				classflg = 1;
			}
		} ]

	});

	var searchDetailPanel = new Ext.form.FormPanel({// 查询panel
		title : '积分明细详情查询',
		height : 100,
		// buttonAlign:'center',
		labelWidth : 100,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		items : [ {
			autoHeight : true,
			items : [ {
				layout : 'column',
				buttonAlign : 'center',
				items : [ {
					columnWidth : .33,
					layout : 'form',
					items : [ {
						fieldLabel : '积分类型',
						name : 'EVENT_TYP',
						xtype : 'combo',
						labelStyle : 'text-align:right;',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						store : new Ext.data.ArrayStore({
							fields : [ 'myId', 'displayText' ],
							data : [ [ '累积', '累积' ], [ '消费', '消费' ] ]
						}),
						valueField : 'myId',
						displayField : 'displayText',
						anchor : '95%'
					} ]
				}, {
					columnWidth : .33,
					layout : 'form',
					items : [ {
						xtype : 'datefield',
						editable : false,
						name : 'PROD_START_DATE_FROM',
						fieldLabel : '开始日期',
						format : 'Y-m-d', // 日期格式化
						labelStyle : 'text-align:right;',
						name : 'FS_DT',
						anchor : '95%'
					} ]
				}, {
					columnWidth : .33,
					layout : 'form',
					items : [ {
						xtype : 'datefield',
						editable : false,
						name : 'PROD_START_DATE_TO',
						fieldLabel : '结束日期',
						format : 'Y-m-d', // 日期格式化
						labelStyle : 'text-align:right;',
						name : 'FS_DT',
						anchor : '95%'
					} ]
				}

				]
			} ]
		}

		],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {
				productInfoStore_detail.loadData(memberData);
			}
		}, {
			text : '重置',
			handler : function() {
				searchPanel.getForm().reset();
				classflg = 1;
			}
		} ]

	});

	var totalDetailPanel = new Ext.form.FormPanel({// 查询panel
		height : 100,
		// buttonAlign:'center',
		labelWidth : 100,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		items : [ {
			autoHeight : true,
			items : [ {
				layout : 'column',
				buttonAlign : 'center',
				items : [ {
					columnWidth : .33,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '累积总计',
						name : 't1',
						anchor : '100%',
						value : '650',
						style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: 0px solid;'
					} ]
				}, {
					columnWidth : .33,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '消费总计',
						name : 't2',
						anchor : '100%',
						value : '450',
						style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: 0px solid;'
					} ]
				} ]
			} ]
		} ]
	});

	var productInfoColumns = new Ext.grid.ColumnModel([// gridtable中的列定义
	new Ext.grid.RowNumberer(), {
		header : '序号',
		dataIndex : 'id',
		width : 150,
		sortable : true
	}, {
		header : '礼品名称',
		dataIndex : 'giftName',
		id : 'giftName',
		sortable : true,
		width : 150
	}, {
		header : '礼品分类',
		dataIndex : 'gifttype',
		id : 'gifttype',
		sortable : true
	}, {
		header : '兑换积分',
		dataIndex : 'point',
		id : 'productEndDate',
		sortable : true
	}, {
		header : '商品单位',
		dataIndex : 'cost_rate',
		renderer : ratePercent(true),
		align : 'right',
		id : 'cost_rate',
		sortable : true
	}, {
		header : '总消费积分',
		dataIndex : 'totalpoints',
		id : 'totalpoints',
		sortable : true
	}, {
		header : '兑换日期',
		dataIndex : 'publishdate',
		id : 'publishdate',
		sortable : true
	}, {
		header : '发布人',
		dataIndex : 'publisher',
		id : 'publisher',
		sortable : true
	}

	]);

	var productInfoColumns_detail = new Ext.grid.ColumnModel([// gridtable中的列定义
	new Ext.grid.RowNumberer(), {
		header : '执行日期',
		dataIndex : 'f1',
		width : 150,
		sortable : true
	}, {
		header : '累计积分',
		dataIndex : 'f2',
		sortable : true,
		width : 150
	}, {
		header : '消费积分',
		dataIndex : 'f3',
		sortable : true,
		width : 150
	}, {
		header : '所剩积分',
		dataIndex : 'f4',
		sortable : true,
		width : 150
	} ]);

	var productInfoRecord = new Ext.data.Record.create([ {
		name : 'id',
		mapping : 'ID'
	}, {
		name : 'giftName',
		mapping : 'GIFT_NAME'
	},

	{
		name : 'gifttype',
		mapping : 'GIFT_TYPE'
	}, {
		name : 'productState',
		mapping : 'PROD_STATE'
	}, {
		name : 'productCreator',
		mapping : 'PROD_CREATOR'
	}, {
		name : 'pord_creator',
		mapping : 'USER_NAME'
	}, {
		name : 'point',
		mapping : 'POINTS'
	}, {
		name : 'productDepartment',
		mapping : 'PROD_DEPT'
	}, {
		name : 'productDescription',
		mapping : 'PROD_DESC'
	}, {
		name : 'rate',
		mapping : 'RATE',
		type : 'float'
	}, {
		name : 'cost_rate',
		mapping : 'GIFT_UNIT',
		type : 'float'
	}, {
		name : 'limit_time',
		mapping : 'LIMIT_TIME'
	}, {
		name : 'totalpoints',
		mapping : 'EXCHANGE_TOTLE'
	}, {
		name : 'publishdate',
		mapping : 'PUBLISH_DATE'
	}, {
		name : 'publisher',
		mapping : 'PUBLISHER'
	}

	]);

	var productInfoRecord_detail = new Ext.data.Record.create([ {
		name : 'f1'
	}, {
		name : 'f2'
	}, {
		name : 'f3'
	}, {
		name : 'f4'
	} ]);

	var productInfoReader = new Ext.data.JsonReader({// 读取json数据的panel
		totalProperty : 'json.count',
		root : 'json.data'
	}, productInfoRecord);

	var productInfoReader_detail = new Ext.data.JsonReader({// 读取json数据的panel
		totalProperty : 'num',// 记录总数
		root : 'rows'// Json中的列表数据根节点
	}, productInfoRecord_detail);

	var productInfoStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/custintegralquery.json',
			// success : function(response){
			// alert(response.responseText);
			// },
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if (resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			},
			method : 'GET'
		}),
		reader : productInfoReader
	});

	var productInfoStore_detail = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/custintegralquery.json',
			// success : function(response){
			// alert(response.responseText);
			// },
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if (resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			},
			method : 'GET'
		}),
		reader : productInfoReader_detail
	});

	// ***********************

	// 每页显示条数下拉选择框
	var spagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});

	// 改变每页显示条数reload数据
	spagesize_combo.on("select", function(comboBox) {
		sbbar.pageSize = parseInt(spagesize_combo.getValue()), productInfoStore
				.reload({
					params : {
						start : 0,
						limit : parseInt(spagesize_combo.getValue())
					}
				});
	});
	// 分页工具栏
	var sbbar = new Ext.PagingToolbar({
		pageSize : parseInt(spagesize_combo.getValue()),
		store : productInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});

	// ***********************

	productInfoStore.load({
		params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		// 'condition':'{"CATL_CODE":"A1"}'
		}
	});
	var productInfoGrid = new Ext.grid.GridPanel({// 产品列表数据grid
		title : '积分明细详情列表',
		frame : true,
		id : 'productInfoGrid',
		store : productInfoStore,
		loadMask : true,

		cm : productInfoColumns,
		bbar : sbbar,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	var productInfoGrid_detail = new Ext.grid.GridPanel({// 产品列表数据grid
		title : '积分明细详情列表',
		height : 250,
		frame : true,
		store : productInfoStore_detail,
		loadMask : true,
		cm : productInfoColumns_detail,
		bbar : sbbar,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}

	});

	var showWin = new Ext.Window({
		title : '客户积分历史查询',
		plain : true,
		closeAction : 'hide',
		// layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,

		width : 800,
		height : 460,

		layout : 'border',
		items : [ searchPanel, {
			region : 'center',
			layout : 'fit',
			items : [ productInfoGrid ]
		} ]
	});

	var showDetailWin = new Ext.Window({
		title : '积分明细详情',
		plain : true,
		closeAction : 'hide',
		// layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,

		width : 800,
		height : 460,

		layout : 'border',
		items : [ searchDetailPanel, {
			region : 'center',
			// layout : 'fit',
			items : [ productInfoGrid_detail, totalDetailPanel ]
		} ]
	});

	var memberData = {
		TOTALCOUNT : 6,
		rows : [ {
			"rownum" : "1",
			"f1" : "2012-02-01",
			"f2" : "600",
			"f3" : "",
			"f4" : "600"
		}, {
			"rownum" : "2",
			"f1" : "2012-03-02",
			"f2" : "",
			"f3" : "100",
			"f4" : "500"
		}, {
			"rownum" : "3",
			"f1" : "2012-04-03",
			"f2" : "20",
			"f3" : "",
			"f4" : "520"
		}, {
			"rownum" : "4",
			"f1" : "2012-05-04",
			"f2" : "30",
			"f3" : "",
			"f4" : "550"
		}, {
			"rownum" : "5",
			"f1" : "2012-06-05",
			"f2" : "",
			"f3" : "150",
			"f4" : "400"
		}, {
			"rownum" : "6",
			"f1" : "2012-07-06",
			"f2" : "",
			"f3" : "200",
			"f4" : "200"
		} ]
	};
	productInfoStore_detail.loadData(memberData);

	// 布局模型
	var viewport = new Ext.Panel({
		renderTo : 'viewport_center',
		height : document.body.clientHeight - 30,
		layout : 'fit',
		autoScroll : true,
		items : [ listPanel ]
	});

});