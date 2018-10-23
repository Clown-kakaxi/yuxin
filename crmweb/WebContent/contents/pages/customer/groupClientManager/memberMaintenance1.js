/**
 *集团成员维护
 */
Ext.onReady(function() {

	var memberRelation = new Ext.form.FormPanel({//成员关系维护表单
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		//	   		collapsible : true,// 是否可收缩
		region : 'center',
		title : '成员维护信息',
		buttonAlign : 'center',
		height : 150,
		items : [
				{
					layout : 'column',
					border : false,
					items : [
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [ {
									fieldLabel : '上级企业名称',
									xtype : 'textfield', // 设置为数字输入框类型
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [ {
									fieldLabel : '成员关系',
									xtype : 'combo', // 设置为数字输入框类型
									anchor : '100%',
									triggerAction : 'all',
									mode : 'local',
									store : new Ext.data.ArrayStore({
										fields : [ 'myId', 'displayText' ],
										data : [ [ 2, '全部' ], [ 0, '标准集团成员' ],
												[ 1, '对外担保' ] ]
									}),
									valueField : 'myId',
									displayField : 'displayText'
								} ]
							},
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [ {
									fieldLabel : '与上级企业关系',
									xtype : 'combo', // 设置为数字输入框类型
									anchor : '100%',
									triggerAction : 'all',
									mode : 'local',
									store : new Ext.data.ArrayStore({
										fields : [ 'myId', 'displayText' ],
										data : [ [ 3, '全部' ], [ 0, '母子公司' ],
												[ 1, '分公司' ], [ 2, '持股公司' ] ]
									}),
									valueField : 'myId',
									displayField : 'displayText'
								} ]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [ {
									fieldLabel : '持股',
									xtype : 'textfield', // 设置为数字输入框类型
									anchor : '100%'
								} ]
							} ]
				}, {

					layout : 'form',
					labelWidth : 90, // 标签宽度
					height : 60,
					defaultType : 'textfield',
					border : false,
					items : [ {
						fieldLabel : '备注',
						xtype : 'textarea', // 设置为数字输入框类型
						anchor : '100%'

					} ]
				} ]

	});
	var windowFormSearchMember = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		//	   		collapsible : true,// 是否可收缩
		region : 'north',
		buttonAlign : 'center',
		height : 80,
		items : [ {
			layout : 'column',
			border : false,
			items : [ {
				columnWidth : .50,
				layout : 'form',
				labelWidth : 90, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [ {
					fieldLabel : '客户名称',
					name : 'wincn',
					xtype : 'textfield', // 设置为数字输入框类型
					anchor : '90%'
				} ]
			}, {
				columnWidth : .50,
				layout : 'form',
				labelWidth : 90, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [ {
					fieldLabel : '组织机构代码',
					name : 'wincn',
					xtype : 'textfield', // 设置为数字输入框类型
					anchor : '90%'
				} ]
			}

			]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {

			}
		}, {
			text : '返回',
			handler : function() {
				window.location = "groupClientMaintenance1.jsp";
			}
		} ]

	});
	//复选框
	var windowsm = new Ext.grid.CheckboxSelectionModel();

	//	var blocMemberSearchCheck = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var windowrownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var windowcm = new Ext.grid.ColumnModel([ windowrownum, windowsm, {
		header : '客户名称', // 列标题
		dataIndex : 'a1', // 数据索引:和Store模型对应
		sortable : true,
		width : 250
	// 是否可排序
	}, {
		header : '组织机构代码',
		dataIndex : 'a3',
		width : 150
	} ]);

	/**
	 * 数据存储
	 */
	var windowstore = new Ext.data.Store({
		// 数据读取器
		reader : new Ext.data.JsonReader({
			totalProperty : 'num',// 记录总数
			//idIndex:'blocName', 
			root : 'rows'// Json中的列表数据根节点
		}, [ {
			name : 'a1' // Json中的属性Key值
		}, {
			name : 'a2'
		}, {
			name : 'a3'
		}, {
			name : 'a4'
		}, {
			name : 'a5'
		}, {
			name : 'a6'
		}, {
			name : 'a7'
		}, {
			name : 'a8'
		}, {
			name : 'a9'
		} ])
	});

	var windowmemberData = {
		TOTALCOUNT : 3,
		rows : [ {
			"rownum" : "1",
			"a1" : "北京国安信息科技股份有限公司",
			"a2" : "国有企业",
			"a3" : "000273",
			"a4" : "09",
			"a5" : "通信业",
			"a6" : "是",
			"a7" : "北京银行上海分行XX支行客户群"
		}, {
			"rownum" : "2",
			"a1" : "北京鸿联九五信息产业有限公司",
			"a2" : "国有企业",
			"a3" : "100382",
			"a4" : "06",
			"a5" : "通信业",
			"a6" : "是",
			"a7" : "北京银行上地支行客户群"
		}, {
			"rownum" : "3",
			"a1" : "北京国安通信有限公司",
			"a2" : "国有企业",
			"a3" : "200123",
			"a4" : "03",
			"a5" : "通信业",
			"a6" : "是",
			"a7" : "北京银行上地支行客户群"
		}, {
			"rownum" : "4",
			"a1" : "华夏基金管理有限公司",
			"a2" : "国有企业",
			"a3" : "200483",
			"a4" : "06",
			"a5" : "金融业",
			"a6" : "是",
			"a7" : "北京银行上地支行客户群"
		}

		]
	};

	windowstore.loadData(windowmemberData);
	// 表格实例
	var windowgrid2 = new Ext.grid.GridPanel({
		height : 250,
		frame : true,
		autoScroll : true,
		ddGroup : 'gridDDGroup',
		enableDragDrop : true,
		tbar : [ {
			text : '加入集团',
			handler : function() {
				addMemberWindowForGroup.hide();
				Ext.MessageBox.alert('提示', "保存成功!");
			}
		} ],
		store : windowstore, // 数据存储
		stripeRows : true, // 斑马线
		cm : windowcm, // 列模型
		sm : windowsm,// 复选框
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}

	});

	var tempTreeNode;

	var blocMemberTree = new Ext.tree.TreePanel({
		autoScroll : true,
		enableDD : true,
		id : 'blocMemberTree',
		useArrows : false,
		listeners : {
			'click' : function(node) {
				Ext.getCmp("parentCom").setValue(node.text);
				tempTreeNode = node;
			},
			render : function() {
				var treePanelDropTar = new Ext.dd.DropTarget(blocMemberTree
						.getEl(), {
					ddGroup : 'gridDDGroup',
					notifyEnter : function(ddSource, e, data) {
						//						blocMemberTree.getEl().stopFx();
						//						blocMemberTree.getEl().hightlight();
					},
					notifyDrop : function(ddSource, e, data) {
						var numRecord = ddSource.dragData.selections.length;
						for ( var i = 0; i < numRecord; i++) {
							var comName = ddSource.dragData.selections[i]
									.get("a1");
							if (tempTreeNode == null || tempTreeNode.isLeaf()) {
								alert("请选择一个非叶子节点企业");
								return;
							}

							tempTreeNode.appendChild(new Ext.tree.TreeNode({
								text : comName,
								leaf : true
							}));
						}

					}
				});
			}
		},
		root : new Ext.tree.AsyncTreeNode({
			id : 'root',
			expanded : true,
			text : '中国北京集团有限公司',
			children : [ {
				text : '中国北京集团有限公司',
				id : 'pekingBloc',
				leaf : true
			}, {
				text : '北京银行股份有限公司',
				id : 'pekingBank',
				leaf : true
			}, {
				text : '北京国安信息产业股份有限公司',
				expanded : true,
				children : [ {
					text : '北京国安信息产业股份有限公司',
					leaf : true
				}, {
					text : '北京国安信息科技股份有限公司',
					leaf : true
				}, {
					text : '北京国安通信有限公司',
					leaf : true
				} ]
			}, {
				text : '北京证券',
				expanded : true,
				id : 'pekingBond',
				children : [ {
					text : '北京证券',
					leaf : true
				}, {
					text : '华夏基金管理有限公司',
					leaf : true
				}, {
					text : '北京金通证券有限责任公司',
					leaf : true
				} ]
			}, {
				text : '集团对外担保成员',
				expanded : true,
				children : [ {
					text : '中国移动北京分公司',
					leaf : true
				}, {
					text : '中石油北京分公司',
					leaf : true
				} ]
			} ]
		})
	});

	var contextMenu = new Ext.menu.Menu({
		id : 'deptTreeContextMenu',
		items : [ {
			text : '移除节点',
			iconCls : 'page_delIcon',
			handler : function() {
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				deleteDeptItems('2', selectNode.attributes.id);
			}
		}, {
			text : '刷新节点',
			iconCls : 'page_refreshIcon',
			handler : function() {
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				if (selectNode.attributes.leaf) {
					selectNode.parentNode.reload();
				} else {
					selectNode.reload();
				}
			}
		} ]
	});

	blocMemberTree.on('contextmenu', function(node, e) {
		e.preventDefault();
		contextMenu.showAt(e.getXY());
	});

	var memberManage = new Ext.Viewport({
		layout : 'border',
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		title : '<span style="font-weight:normal">集团成员维护</span>',
		collapsible : true,// 是否可收缩
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [
				{
					region : 'center',
					autoScroll : true,
					items : [ windowFormSearchMember, windowgrid2,
							memberRelation, memberGrid ]
				}, {
					region : 'west',
					width : 250,
					layout : 'fit',
					//items:windowgridBloc,
					items : blocMemberTree,
					tbar : [ {
						text : '移除节点',
						handler : function() {

						}
					} ]

				} ],
		buttons : [ {
			text : '返   回',

			handler : function() {
				//addMemberWindowForGroup.hide();
			}
		} ]
	});
});