/**
 * 营销管理->营销活动管理->营销活动模板管理：主页面定义JS文件；wzy；2013-05-22
 */

/** ******************启用状态******************** */
var irStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['启用', '1'], ['停用', '0']]
		});

/** ******************营销活动状态******************** */
var mactiStatusStore = new Ext.data.Store({
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=MACTI_STATUS'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

/** ******************营销活动类型******************** */
var mactiTypeStore = new Ext.data.Store({
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=ACTI_TYPE'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

/** ******************营销方式******************** */
var mactiWayeStore = new Ext.data.Store({
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=MKT_WAY'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

// 产品放大镜(查询条件用)
var prodEditCode_query = new Com.yucheng.crm.common.ProductManage({
			xtype : 'productChoose',
			fieldLabel : '对应产品',
			name : 'productName',
			hiddenName : 'aimProd',
			singleSelect : false,
			anchor : '90%',
			callback : function() {
			}
		});

// 产品放大镜(新增、修改、查看界面用)
var prodEditCode_add = new Com.yucheng.crm.common.ProductManage({
			xtype : 'productChoose',
			fieldLabel : '对应产品',
			name : 'f2',
			hiddenName : 'f1',
			singleSelect : false,
			anchor : '99%',
			callback : function() {
			}
		});

Ext.onReady(function() {
	Ext.QuickTips.init();

	/** *************用于修复ie下datefield显示不完整的bug,ie9测试ok************** */
	Ext.isIE8 = Ext.isIE && navigator.userAgent.indexOf('MSIE 8') != -1;
	Ext.override(Ext.menu.Menu, {
				autoWidth : function() {
					var el = this.el, ul = this.ul;
					if (!el) {
						return;
					}
					var w = this.width;
					if (w) {
						el.setWidth(w);
					} else if (Ext.isIE && !Ext.isIE8) { // Ext2.2 支持
						// Ext.isIE8 属性
						el.setWidth(this.minWidth);
						var t = el.dom.offsetWidth;
						el.setWidth(ul.getWidth() + el.getFrameWidth("lr"));
					}
				}
			});

	/** **********************公告查询FORM*************************** */
	var centerApply = new Ext.form.FormPanel({
				id : "searchCondition",
				labelWidth : 100,
				frame : true,
				autoScroll : true,
				region : 'north',
				title : '营销活动模板查询',
				buttonAlign : "center",
				height : 122,
				width : '100%',
				labelAlign : 'right',
				items : [{
							layout : 'column',
							items : [{
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '模板编号',
													name : 'NOTICE_NO',
													anchor : '90%'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '模板名称',
													name : 'NOTICE_TITLE',
													anchor : '90%'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										items : [prodEditCode_query]
									}]
						}, {
							layout : 'column',
							items : [{
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'combo',
													fieldLabel : '启用状态',
													mode : 'local',
													emptyText : '请选择',
													store : irStore,
													triggerAction : 'all',
													valueField : 'value',
													editable : false,
													displayField : 'key',
													name : 'isRead',
													anchor : '90%'
												}]
									}]
						}],
				buttons : [{
							text : '查询',
							xtype : 'button',
							handler : function() {
								restfulStore.loadData(memberData);
							}
						}, {
							text : '重置',
							xtype : 'button',
							handler : function() {
								centerApply.getForm().reset();
							}
						}]
			});

	var proxyIndex = new Ext.data.HttpProxy({
				url : basepath + '/noticequery.json?noticeTitle=asas（）'
			});

	var TopicRecord = Ext.data.Record.create([{
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
			}, {
				name : 'f8'
			}]);

	var reader = new Ext.data.JsonReader({
				totalProperty : 'num',// 记录总数
				root : 'rows'// Json中的列表数据根节点
			}, TopicRecord/** data record */
	);

	var writer = new Ext.data.JsonWriter({
				encode : false
			});

	var sm = new Ext.grid.CheckboxSelectionModel();

	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});

	var cm1 = new Ext.grid.ColumnModel([rownum, sm, {
				header : '模板编号',
				dataIndex : 'f7',
				sortable : true,
				width : 120
			}, {
				header : '模板名称',
				dataIndex : 'f1',
				sortable : true,
				width : 220
			}, {
				header : '对应产品',
				dataIndex : 'f2',
				sortable : true,
				width : 100
			}, {
				header : '启用状态',
				sortable : true,
				dataIndex : 'f3',
				width : 80
			}, {
				header : '模板描述',
				sortable : true,
				dataIndex : 'f8',
				width : 320
			}, {
				header : '创建时间',
				width : 100,
				sortable : true,
				dataIndex : 'f4'
			}, {
				header : '创建人',
				width : 100,
				sortable : true,
				dataIndex : 'f5'
			}]);

	var restfulStore = new Ext.data.Store({
				id : 'notice',
				restful : true,
				proxy : proxyIndex,
				reader : reader,
				writer : writer,
				recordType : TopicRecord
			});

	var pagesize_combo = new Ext.form.ComboBox({
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
				value : 20,
				editable : false,
				width : 85
			});

	var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : restfulStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()), restfulStore
						.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
			});

	// 河北银行对公POC，使用数据：和河北银行相关
	var memberData = {
		TOTALCOUNT : 6,
		rows : [{
					"rownum" : "1",
					"f1" : "2013营销贷产品营销活动模板",
					"f2" : "营销贷",
					"f3" : "启用",
					"f4" : "2013-03-23",
					"f5" : "李小明",
					"f6" : "248",
					"f7" : "YXHDMB20130323",
					"f8" : "此模板是针对营销贷产品营销活动的模板"
				}, {
					"rownum" : "2",
					"f1" : "2012商圈贷产品营销活动模板",
					"f2" : "商圈贷",
					"f3" : "启用",
					"f4" : "2012-12-19",
					"f5" : "司马淘",
					"f6" : "432",
					"f7" : "YXHDMB20121219",
					"f8" : "此模板是针对商圈贷产品营销活动的模板"
				}, {
					"rownum" : "3",
					"f1" : "2012订单贷产品营销活动模板",
					"f2" : "订单贷",
					"f3" : "启用",
					"f4" : "2012-11-06",
					"f5" : "欧阳合一",
					"f6" : "848",
					"f7" : "YXHDMB20121106",
					"f8" : "此模板是针对订单贷产品营销活动的模板"
				}, {
					"rownum" : "4",
					"f1" : "2013订单贷产品营销活动模板",
					"f2" : "订单贷",
					"f3" : "启用",
					"f4" : "2013-03-03",
					"f5" : "吴东",
					"f6" : "654",
					"f7" : "YXHDMB20130303",
					"f8" : "此模板是针对订单贷产品营销活动的模板"
				}, {
					"rownum" : "5",
					"f1" : "2013商圈贷产品营销活动模板",
					"f2" : "商圈贷",
					"f3" : "启用",
					"f4" : "2013-03-01",
					"f5" : "单和",
					"f6" : "4242",
					"f7" : "YXHDMB20130301",
					"f8" : "此模板是针对商圈贷产品营销活动的模板"
				}, {
					"rownum" : "6",
					"f1" : "2013营销贷产品营销活动模板",
					"f2" : "营销贷",
					"f3" : "启用",
					"f4" : "2013-03-01",
					"f5" : "邱力",
					"f6" : "8432",
					"f7" : "YXHDMB20130301",
					"f8" : "此模板是针对营销贷产品营销活动的模板"
				}, {
					"rownum" : "7",
					"f1" : "2012商圈贷产品营销活动模板",
					"f2" : "商圈贷",
					"f3" : "启用",
					"f4" : "2012-10-26",
					"f5" : "许劭区",
					"f6" : "5639",
					"f7" : "YXHDMB20121026",
					"f8" : "此模板是针对商圈贷产品营销活动的模板"
				}]
	};
	restfulStore.loadData(memberData);

	// 模板信息
	var addaffiche = new Ext.FormPanel({
				title : '模板信息',
				formId : 'newNotice',
				frame : true,
				border : false,
				labelAlign : 'right',
				standardSubmit : false,
				layout : 'form',
				width : 850,
				items : [{
							layout : 'column',
							items : [{
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '模板编号',
													name : 'f7',
													anchor : '100%'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '模板名称',
													name : 'f1',
													anchor : '100%'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'combo',
													fieldLabel : '启用状态',
													mode : 'local',
													emptyText : '请选择',
													store : irStore,
													triggerAction : 'all',
													valueField : 'value',
													editable : false,
													displayField : 'key',
													name : 'f3',
													anchor : '100%'
												}]
									}]
						}, {
							layout : 'column',
							items : [{
										columnWidth : 1,
										layout : 'form',
										items : [prodEditCode_add]
									}]
						}, {
							layout : 'column',
							items : [{
										columnWidth : .99,
										layout : 'form',
										items : [{
													xtype : 'textarea',
													fieldLabel : '模板描述',
													name : 'f8',
													anchor : '100%'

												}]
									}]
						}]
			});

	// 活动信息
	var zbgs_form = new Ext.FormPanel({
		title : '活动信息',
		frame : true,
		border : false,
		labelAlign : 'right',
		standardSubmit : false,
		layout : 'form',
		width : 850,
		items : [{
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				items : [{
							xtype : 'textfield',
							labelStyle : 'text-align:right;',
							width : 134,
							fieldLabel : '<span style="color:red">*</span>营销活动名称',
							allowBlank : false,
							name : 'mktActiName',
							anchor : '99%'
						}, {
							store : mactiStatusStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '<span style="color:red">*</span>营销活动状态',
							hiddenName : 'mktActiStat',
							name : 'mktActiStat',
							valueField : 'key',
							labelStyle : 'text-align:right;',
							displayField : 'value',
							mode : 'local',
							width : 100,
							readOnly : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							anchor : '99%'
						}]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [{
							xtype : 'textfield',
							labelStyle : 'text-align:right;',
							width : 134,
							hidden : true,
							fieldLabel : '营销活动ID',
							name : 'mktActiId',
							anchor : '99%'
						}]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [{
							store : mactiTypeStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '<span style="color:red">*</span>营销活动类型',
							hiddenName : 'mktActiType',
							name : 'mktActiType',
							valueField : 'key',
							labelStyle : 'text-align:right;',
							displayField : 'value',
							allowBlank : false,
							mode : 'local',
							width : 100,
							editable : false,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							anchor : '99%'
						}, {
							xtype : 'datefield',
							labelStyle : 'text-align:right;',
							width : 134,
							editable : false,
							allowBlank : false,
							format : 'Y-m-d',
							fieldLabel : '<span style="color:red">*</span>计划开始时间',
							name : 'pstartDate',
							anchor : '99%'
						}]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [{
							store : mactiWayeStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '<span style="color:red">*</span>营销方式',
							hiddenName : 'mktActiMode',
							name : 'mktActiMode',
							valueField : 'key',
							allowBlank : false,
							labelStyle : 'text-align:right;',
							displayField : 'value',
							mode : 'local',
							width : 134,
							editable : false,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							anchor : '99%'
						}, {
							xtype : 'textfield',
							fieldLabel : '创建人',
							hidden : true,
							name : 'createUser',
							anchor : '99%'
						}, {
							xtype : 'datefield',
							fieldLabel : '创建时间',
							format : 'Y-m-d',
							hidden : true,
							name : 'createDate',
							anchor : '99%'
						}, {
							xtype : 'textfield',
							fieldLabel : '更新人',
							hidden : true,
							name : 'updateUser',
							anchor : '99%'
						}, {
							xtype : 'datefield',
							fieldLabel : '更新时间',
							format : 'Y-m-d',
							hidden : true,
							name : 'updateDate',
							anchor : '99%'
						}, {
							xtype : 'textfield',
							fieldLabel : '创建机构',
							hidden : true,
							name : 'createOrg',
							anchor : '99%'
						}, {
							fieldLabel : '<span style="color:red">*</span>计划结束时间',
							xtype : 'datefield',
							width : 134,
							labelStyle : 'text-align:right;',
							format : 'Y-m-d',
							editable : false,
							allowBlank : false,
							name : 'pendDate',
							anchor : '99%'
						}]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [{
							labelStyle : 'text-align:right;',
							width : 134,
							fieldLabel : '<span style="color:red">*</span>费用预算',
							xtype : 'numberfield', // 设置为数字输入框类型
							decimalPrecision : 2,
							maxValue : 99999999,
							allowBlank : false,
							name : 'mktActiCost',
							anchor : '99%'
						}]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '活动地点',
							name : 'mktActiAddr',
							anchor : '99%'
						}]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '营销活动内容',
							name : 'mktActiCont',
							anchor : '99%'
						}]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '涉及客户群描述',
							name : 'actiCustDesc',
							anchor : '99%'
						}]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '涉及执行人描述',
							name : 'actiOperDesc',
							anchor : '99%'
						}]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '涉及产品描述',
							name : 'actiProdDesc',
							anchor : '99%'
						}]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '营销活动目的',
							name : 'mktActiAim',
							anchor : '99%'
						}]
			}, {
				columnWidth : 50,
				layout : 'form',
				items : [{
							xtype : 'textarea',
							labelStyle : 'text-align:right;',
							width : 400,
							fieldLabel : '备注',
							name : 'actiRemark',
							anchor : '90%'
						}]
			}]
		}]
	});

	var tbar = new Ext.Toolbar({
		items : [{
			id : 'infoNot',
			text : '查看模板',
			iconCls : 'detailIconCss',
			handler : function() {
				var _record = grid.getSelectionModel().getSelected();
				if (!_record) {
					Ext.MessageBox.alert('提示', '请选择要操作的记录！');
					return false;
				} else {
					var win = new Ext.Window({
								autoScroll : true,
								resizable : true,
								collapsible : true,
								maximizable : true,
								draggable : true,
								closeAction : 'hide',
								modal : true, // 模态窗口
								animCollapse : true,
								border : false,
								loadMask : true,
								closable : true,
								constrain : true,
								width : 880,
								height : 470,
								title : '查看模板',
								items : [addaffiche, zbgs_form],
								buttonAlign : 'center',
								buttons : [{
											text : '关闭',
											handler : function() {
												win.hide();
											}
										}],
								listeners : {
									'show' : function() {
										var record = grid.getSelectionModel()
												.getSelected();
										addaffiche.getForm().loadRecord(record);
										setActivityInfo();
									}
								}
							});
					win.show();
				}
			}
		}, '-', {
			text : '新增模板',
			iconCls : 'addIconCss',
			handler : function() {
				var win = new Ext.Window({
							autoScroll : true,
							resizable : true,
							collapsible : true,
							maximizable : true,
							draggable : true,
							closeAction : 'hide',
							modal : true, // 模态窗口
							animCollapse : true,
							border : false,
							loadMask : true,
							closable : true,
							constrain : true,
							width : 880,
							height : 470,
							title : '新增模板',
							items : [addaffiche, zbgs_form],
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										handler : function() {
											Ext.MessageBox.alert('提示', '保存成功！');
											win.hide();
										}
									}, {
										text : '关闭',
										handler : function() {
											win.hide();
										}
									}],
							listeners : {
								'show' : function() {
									zbgs_form.getForm()
											.findField('mktActiStat')
											.setValue('暂存');
								}
							}
						});
				addaffiche.getForm().reset();
				zbgs_form.getForm().reset();
				win.show();
			}
		}, '-', {
			id : '__upNot',
			text : '修改模板',
			iconCls : 'resetIconCss',
			handler : function() {
				var _record = grid.getSelectionModel().getSelected();
				if (!_record) {
					Ext.MessageBox.alert('提示', '请选择要操作的记录！');
					return false;
				} else {
					var win = new Ext.Window({
								autoScroll : true,
								resizable : true,
								collapsible : true,
								maximizable : true,
								draggable : true,
								closeAction : 'hide',
								modal : true, // 模态窗口
								animCollapse : true,
								border : false,
								loadMask : true,
								closable : true,
								constrain : true,
								width : 880,
								height : 470,
								title : '修改模板',
								items : [addaffiche, zbgs_form],
								buttonAlign : 'center',
								buttons : [{
											text : '保存',
											handler : function() {
												Ext.MessageBox.alert('提示',
														'保存成功！');
												win.hide();
											}
										}, {
											text : '关闭',
											handler : function() {
												win.hide();
											}
										}],
								listeners : {
									'show' : function() {
										var record = grid.getSelectionModel()
												.getSelected();
										addaffiche.getForm().loadRecord(record);
										setActivityInfo();
									}
								}
							});
					win.show();
				}
			}
		}, '-', {
			id : 'delNot',
			text : '删除模板',
			iconCls : 'deleteIconCss',
			handler : function() {
				var checkedNodes = grid.getSelectionModel().selections.items;
				if (checkedNodes.length == 0) {
					Ext.Msg.alert('提示', '未选择任何记录');
					return;
				} else {
					Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
								if (buttonId.toLowerCase() == "no") {
									return;
								}
								Ext.Msg.alert('提示', '操作成功！');
							})
				}
			}
		}]
	});

	// 查看、修改页面：设置活动的相关信息
	function setActivityInfo() {
		zbgs_form.getForm().reset();
		zbgs_form.getForm().findField('mktActiName').setValue('营销贷产品推荐活动');
		zbgs_form.getForm().findField('mktActiType').setValue('5');
		zbgs_form.getForm().findField('mktActiMode').setValue('1');
		zbgs_form.getForm().findField('mktActiStat').setValue('暂存');
		zbgs_form.getForm().findField('mktActiCost').setValue('35000');
		zbgs_form.getForm().findField('pstartDate').setValue('2012-02-01');
		zbgs_form.getForm().findField('pendDate').setValue('2014-12-31');
		zbgs_form.getForm().findField('mktActiAddr')
				.setValue('河北省邯郸市光明南大街199号');
		zbgs_form.getForm().findField('mktActiCont')
				.setValue('在河北省邯郸市光明南大街199号进行营销贷产品');
		zbgs_form.getForm().findField('actiCustDesc')
				.setValue('相关客户群为营销贷产品的客户群');
		zbgs_form.getForm().findField('actiOperDesc')
				.setValue('相关执行人为营销贷产品的客户经理');
		zbgs_form.getForm().findField('actiProdDesc').setValue('主要是和营销贷产品相关的');
		zbgs_form.getForm().findField('mktActiAim').setValue('向客户推荐营销贷产品');
		zbgs_form.getForm().findField('actiRemark').setValue('营销贷产品的营销活动模板定义');
	}

	var grid = new Ext.grid.GridPanel({
				title : '营销活动模板信息',
				frame : true,
				store : restfulStore,
				region : 'center',
				stripeRows : true,
				tbar : tbar,
				cm : cm1,
				sm : sm,
				bbar : bbar,
				viewConfig : {},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [{
							layout : 'border',
							items : [centerApply, grid]
						}]
			});
});