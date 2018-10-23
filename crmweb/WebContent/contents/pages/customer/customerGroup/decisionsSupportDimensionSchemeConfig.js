/**
 * 客户管理->决策支持维度方案管理：主页面定义JS文件；wzy；2013-05-16
 */

/** ******************方案类型******************** */
var nlStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['供应链', '1'], ['商圈', '2'], ['公私联动', '3'], ['客户生命周期', '4'],
					['集团客户', '5'], ['中小企业群', '6'], ['联保企业群', '7']]
		});
/** ******************启用状态******************** */
var irStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['启用', '1'], ['停用', '0']]
		});
/** ******************方案适用范围**************** */
var fafwStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['贷方', 'C'], ['借方', 'D']]

		});
/** ******************评级频率******************** */
var pjplStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['AC', 'AC'], ['AT', 'AT'], ['BB', 'BB'], ['BP', 'BP'],
					['CC', 'CC'], ['CH', 'CH'], ['FA', 'FA'], ['FT', 'FT']]
		});
/** ******************行业逻辑关系符******************** */
var aoStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['与', '1'], ['或', '0']]
		});
/** ******************商圈成员协会******************** */
var xhStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['软件协会', '1'], ['硬件协会', '0']]
		});
/** ******************零售成员类型******************** */
var lscyStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['高管', '0'], ['中层管理者', '1'], ['普通员工', '2']]
		});
/** ******************零售客户类型******************** */
var lskhStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['潜在客户', '0'], ['正式客户', '1']]
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
				title : '决策支持维度方案查询',
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
													fieldLabel : '方案编号',
													name : 'NOTICE_NO',
													anchor : '90%'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '方案名称',
													name : 'NOTICE_TITLE',
													anchor : '90%'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										items : [{
													xtype : 'combo',
													fieldLabel : '方案类型',
													editable : false,
													emptyText : '请选择',
													name : 'NOTICE_LEVEL',
													mode : 'local',
													anchor : '90%',
													triggerAction : 'all',
													store : nlStore,
													valueField : 'value',
													displayField : 'key'
												}]
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
				header : '方案编号',
				dataIndex : 'f7',
				sortable : true,
				width : 90
			}, {
				header : '方案名称',
				dataIndex : 'f1',
				sortable : true,
				width : 220
			}, {
				header : '方案类型',
				dataIndex : 'f2',
				sortable : true,
				width : 100
			}, {
				header : '启用状态',
				sortable : true,
				dataIndex : 'f3',
				width : 80
			}, {
				header : '方案客户数',
				dataIndex : 'f6',
				sortable : true,
				width : 90,
				align : 'right'
			}, {
				header : '方案描述',
				sortable : true,
				dataIndex : 'f8',
				width : 280
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
					"f1" : "供应链维度方案",
					"f2" : "供应链",
					"f3" : "启用",
					"f4" : "2013-03-23",
					"f5" : "李小明",
					"f6" : "248",
					"f7" : "FA20130323",
					"f8" : "此方案是针对供应链维度定义的管理方案"
				}, {
					"rownum" : "2",
					"f1" : "商圈维度方案",
					"f2" : "商圈",
					"f3" : "启用",
					"f4" : "2012-12-19",
					"f5" : "司马淘",
					"f6" : "432",
					"f7" : "FA20121219",
					"f8" : "此方案是针对商圈维度定义的管理方案"
				}, {
					"rownum" : "3",
					"f1" : "公私联动维度方案",
					"f2" : "公私联动",
					"f3" : "启用",
					"f4" : "2012-11-06",
					"f5" : "欧阳合一",
					"f6" : "848",
					"f7" : "FA20121106",
					"f8" : "此方案是针对公私联动维度定义的管理方案"
				}, {
					"rownum" : "4",
					"f1" : "客户生命周期维度方案",
					"f2" : "客户生命周期",
					"f3" : "启用",
					"f4" : "2013-03-03",
					"f5" : "吴东",
					"f6" : "654",
					"f7" : "FA20130303",
					"f8" : "此方案是针对客户生命周期维度定义的管理方案"
				}, {
					"rownum" : "5",
					"f1" : "集团客户维度方案",
					"f2" : "集团客户",
					"f3" : "启用",
					"f4" : "2013-03-01",
					"f5" : "单和",
					"f6" : "4242",
					"f7" : "FA20130301",
					"f8" : "此方案是针对集团客户维度定义的管理方案"
				}, {
					"rownum" : "6",
					"f1" : "中小企业群维度方案",
					"f2" : "中小企业群",
					"f3" : "启用",
					"f4" : "2013-03-01",
					"f5" : "邱力",
					"f6" : "8432",
					"f7" : "FA20130301",
					"f8" : "此方案是针对中小企业群维度定义的管理方案"
				}, {
					"rownum" : "7",
					"f1" : "联保企业群维度方案",
					"f2" : "联保企业群",
					"f3" : "启用",
					"f4" : "2012-10-26",
					"f5" : "许劭区",
					"f6" : "5639",
					"f7" : "FA20121026",
					"f8" : "此方案是针对联保企业群维度定义的管理方案"
				}]
	};
	restfulStore.loadData(memberData);

	// 方案信息
	var addaffiche = new Ext.FormPanel({
				title : '方案信息',
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
											fieldLabel : '方案编号',
											name : 'f7',
											anchor : '100%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											fieldLabel : '方案名称',
											name : 'f1',
											anchor : '100%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
									xtype : 'combo',
									fieldLabel : '方案类型',
									editable : false,
									emptyText : '请选择',
									name : 'f2',
									mode : 'local',
									anchor : '100%',
									triggerAction : 'all',
									store : nlStore,
									valueField : 'value',
									displayField : 'key',
									listeners : {
										select : function(combo, record, value) {
											// 当下拉选项的值被改变时，显示对应的“业务信息”Form表单
											// 隐藏“业务信息”form表单
											gyl_yw_form.setVisible(false);
											sq_yw_form.setVisible(false);
											gsld_yw_form.setVisible(false);
											khsmzq_yw_form.setVisible(false);
											var f2 = addaffiche.getForm()
													.findField("f2").getValue();
											// 显示对应的Form表单
											if (f2 == '1') {// 供应链
												gyl_yw_form.setVisible(true);
											} else if (f2 == '2') {// 商圈
												sq_yw_form.setVisible(true);
											} else if (f2 == '3') {// 公私联动
												gsld_yw_form.setVisible(true);
											} else if (f2 == '4') {// 客户生命周期
												khsmzq_yw_form.setVisible(true);
											} else {// ['集团客户', '5'],['中小企业群',
												// '6'],['联保企业群',
												// '7']]，不显示任何“业务信息”Form表单
											}
										}
									}
								}]
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
											name : 'f3',
											anchor : '100%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											fieldLabel : '方案客户数',
											name : 'f6',
											disabled : true,
											anchor : '100%'
										}]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .99,
								layout : 'form',
								items : [{
											xtype : 'textarea',
											fieldLabel : '方案描述',
											maxLengthText : '内容长度过长',
											name : 'f8',
											anchor : '100%'

										}]
							}]
				}]
			});

	// 指标公式
	var zbgs_form = new Ext.FormPanel({
				title : '指标公式',
				frame : true,
				border : false,
				labelAlign : 'right',
				standardSubmit : false,
				layout : 'form',
				width : 850,
				items : [{
							layout : 'column',
							items : [{
										columnWidth : 1,
										layout : 'form',
										items : [{
													xtype : 'textarea',
													fieldLabel : '指标公式',
													name : 'f21',
													anchor : '100%',
													value : '数量*百分比*完成率'
												}]
									}, {
										columnWidth : 1,
										layout : 'form',
										items : [{
													xtype : 'textarea',
													fieldLabel : '公式描述',
													name : 'f22',
													anchor : '100%',
													value : '最后完成的有效数量计算公式'
												}]
									}]
						}]
			});

	var tbar = new Ext.Toolbar({
		items : [{
			id : 'infoNot',
			text : '查看方案',
			iconCls : 'detailIconCss',
			handler : function() {
				var _record = grid.getSelectionModel().getSelected();
				if (!_record) {
					Ext.MessageBox.alert('提示', '请选择要操作的记录！');
					return false;
				} else {
					var checkedNodes = grid.getSelectionModel().selections.items;
					var _item = null;
					var type = checkedNodes[0].data.f2;
					// 根据“方案类型”不同，在编辑页面，展示不同的数据项
					if (type == '供应链') {
						_item = [addaffiche, gyl_yw_form, grid_express_result,
								zbgs_form];
					} else if (type == '商圈') {
						_item = [addaffiche, sq_yw_form, grid_express_result,
								zbgs_form];
					} else if (type == '公私联动') {
						_item = [addaffiche, gsld_yw_form, grid_express_result,
								zbgs_form];
					} else if (type == '客户生命周期') {
						_item = [addaffiche, khsmzq_yw_form,
								grid_express_result, zbgs_form];
					} else if (type == '集团客户' || type == '中小企业群'
							|| type == '联保企业群') {
						_item = [addaffiche, grid_express_result, zbgs_form];
					}
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
								title : '查看方案',
								items : _item,
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
										teamstore_result
												.loadData(datas_result_view);
									}
								}
							});
					win.show();
				}
			}
		}, '-', {
			text : '新增方案',
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
							title : '新增方案',
							items : [addaffiche, gyl_yw_form, sq_yw_form,
									gsld_yw_form, khsmzq_yw_form,
									grid_express_result, zbgs_form],
							buttonAlign : 'center',
							buttons : [{
								text : '保存',
								handler : function() {
									var f2 = addaffiche.getForm()
											.findField("f2").getValue();
									if (f2 == null || f2 == "") {
										Ext.MessageBox.alert('提示', '方案类型不能为空！');
										return false;
									}
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
									// 隐藏“业务信息”form表单
									gyl_yw_form.setVisible(false);
									sq_yw_form.setVisible(false);
									gsld_yw_form.setVisible(false);
									khsmzq_yw_form.setVisible(false);
									teamstore_result.loadData(datas_result);
								}
							}
						});
				gyl_yw_form.getForm().reset();
				sq_yw_form.getForm().reset();
				gsld_yw_form.getForm().reset();
				khsmzq_yw_form.getForm().reset();
				addaffiche.getForm().reset();
				zbgs_form.getForm().reset();
				win.show();
			}
		}, '-', {
			id : '__upNot',
			text : '修改方案',
			iconCls : 'resetIconCss',
			handler : function() {
				var _record = grid.getSelectionModel().getSelected();
				if (!_record) {
					Ext.MessageBox.alert('提示', '请选择要操作的记录！');
					return false;
				} else {
					var checkedNodes = grid.getSelectionModel().selections.items;
					var _item = null;
					var type = checkedNodes[0].data.f2;
					// 根据“方案类型”不同，在编辑页面，展示不同的数据项
					if (type == '供应链') {
						_item = [addaffiche, gyl_yw_form, grid_express_result,
								zbgs_form];
					} else if (type == '商圈') {
						_item = [addaffiche, sq_yw_form, grid_express_result,
								zbgs_form];
					} else if (type == '公私联动') {
						_item = [addaffiche, gsld_yw_form, grid_express_result,
								zbgs_form];
					} else if (type == '客户生命周期') {
						_item = [addaffiche, khsmzq_yw_form,
								grid_express_result, zbgs_form];
					} else if (type == '集团客户' || type == '中小企业群'
							|| type == '联保企业群') {
						_item = [addaffiche, grid_express_result, zbgs_form];
					}
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
								title : '修改方案',
								items : _item,
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
										teamstore_result
												.loadData(datas_result_view);
									}
								}
							});
					win.show();
				}
			}
		}, '-', {
			id : 'delNot',
			text : '方案删除',
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

	var grid = new Ext.grid.GridPanel({
				title : '决策支持维度方案信息',
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