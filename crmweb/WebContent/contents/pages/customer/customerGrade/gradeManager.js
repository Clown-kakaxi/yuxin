/** ******************客户类型******************** */
var nlStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=TYPE_RANGE'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});
/** ******************评级类型******************** */
var typeStore1 = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=GRADE_TYPE'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

/** ******************是否启用******************** */
var irStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=IF_FLAG'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

/** ******************评级频率******************** */
var pjplStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=SCHEME_TIME'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

var pagesize_combo1 = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[100, '100条/页'], [200, '200条/页'],
								[500, '500条/页'], [1000, '1000条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : 100,
			editable : false,
			width : 85
		});

/** *************指标选择窗口************** */
var FormulaWindow = new Ext.Window({
			plain : true,
			defaults : {
				overflow : 'auto',
				autoScroll : true
			},
			layout : 'fit',
			frame : true,
			resizable : true,
			draggable : true,
			closable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			shadow : true,
			loadMask : true,
			maximizable : true,
			collapsible : true,
			titleCollapse : true,
			border : false,
			width : 450,
			height : 420,
			buttonAlign : "center",
			title : '公式管理',
			buttons : [
					// {
					// text : '公式校验',
					// handler:function()
					// {
					// alert("公式校验通过。");
					// }
					// },
					// '-',
					{
				text : '确定',
				handler : function() {
					// 校验公式正确性
					if (!checkFormula()) {
						Ext.Msg.alert('提示', '评级公式配置有误，请检查！');
						return false;
					}
					addaffiche.getForm().findField("gradeFormula").setValue(Ext
							.getCmp('FORMULAWINDOWT').getValue());
					addaffiche.getForm().findField("gradeFormulaExplain")
							.setValue(Ext.getCmp('FORMULAWINDOWW').getValue());

					FormulaWindow.hide();
				}
			}, '-', {
				text : '返回',
				handler : function() {
					FormulaWindow.hide();
				}
			}]
		});

/**
 * form panel ,used for create,update,and show the records.
 */
var addaffiche = new Ext.FormPanel({
	formId : 'newNotice',
	frame : true,
	border : false,
	labelAlign : 'right',
	standardSubmit : false,
	layout : 'form',
	width : 800,
	items : [{
				layout : 'column',
				items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>方案名称',
										name : 'schemeName',
										allowBlank : false,
										anchor : '100%'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										store : typeStore1,
										xtype : 'combo',
										resizable : true,
										hiddenName : 'gradeUseage',
										name : 'gradeUseage',
										fieldLabel : '<font color=red>*</font>方案类型',
										valueField : 'key',
										displayField : 'value',
										allowBlank : false,
										mode : 'local',
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '100%'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										store : nlStore,
										xtype : 'combo',
										resizable : true,
										hiddenName : 'gradeType',
										name : 'gradeType',
										fieldLabel : '<font color=red>*</font>客户类型',
										valueField : 'key',
										displayField : 'value',
										allowBlank : false,
										mode : 'local',
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '100%'
									}]
						}]
			}, {
				layout : 'column',
				items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
										store : irStore,
										xtype : 'combo',
										resizable : true,
										hiddenName : 'isUsed',
										fieldLabel : '<font color=red>*</font>是否启用',
										valueField : 'key',
										displayField : 'value',
										allowBlank : false,
										mode : 'local',
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '100%'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								columnWidth : .33,
								layout : 'form',
								items : [{
									store : pjplStore,
									xtype : 'combo',
									resizable : true,
									hiddenName : 'gradeFrequency',
									fieldLabel : '<font color=red>*</font>评级频率',
									valueField : 'key',
									allowBlank : false,
									displayField : 'value',
									mode : 'local',
									triggerAction : 'all',
									emptyText : '请选择',
									selectOnFocus : true,
									anchor : '100%'
								}]
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'datefield',
										fieldLabel : '<font color=red>*</font>评级起始日期',
										format : 'Y-m-d',
										name : 'gradeBeginDate',
										allowBlank : false,
										selectOnFocus : true,
										anchor : '100%'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'datefield',
										fieldLabel : '<font color=red>*</font>评级结束日期',
										format : 'Y-m-d',
										name : 'gradeEndDate',
										allowBlank : false,
										selectOnFocus : true,
										anchor : '100%'
									}]
						}]
			}, {
				layout : 'form',
				items : [{
							xtype : 'textarea',
							fieldLabel : '<font color=red>*</font>评级公式',
							name : 'gradeFormula',
							id : 'gradeFormula',
							allowBlank : false,
							readOnly : true,
							anchor : '100%'
						}, {
							xtype : 'textarea',
							fieldLabel : '<font color=red>*</font>评级公式解释',
							name : 'gradeFormulaExplain',
							allowBlank : false,
							readOnly : true,
							anchor : '100%'
						}, {
							xtype : 'textarea',
							fieldLabel : '备注',
							name : 'memo',
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : '方案编号',
							name : 'schemeId',
							hidden : true,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : 'createUserId',
							name : 'createUserId',
							hidden : true,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : 'createOrgId',
							name : 'createOrgId',
							hidden : true,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : 'createUserName',
							name : 'createUserName',
							hidden : true,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : 'createOrgName',
							name : 'createOrgName',
							hidden : true,
							anchor : '100%'
						}]
			}],
	buttonAlign : 'center',
	buttons : [{
		text : '保存',
		id : 'save',
		xtype : 'button',
		handler : function() {
			if (!addaffiche.getForm().isValid()) {
				Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
				return false;
			}
			var _date = new Date();
			var _pstartDate = addaffiche.form.findField('gradeBeginDate')
					.getValue();
			var _pendDate = addaffiche.form.findField('gradeEndDate')
					.getValue();
			if (operate != 'update') {// 修改时不判断此条件
				if (_pstartDate.format('Y-m-d') < _date.format('Y-m-d')) {
					Ext.MessageBox.alert('提示', '评级起始日期不能小于今天');
					return false;
				}
			}
			if (_pendDate < _pstartDate) {
				Ext.MessageBox.alert('提示', '评级结束日期不能小于评级起始日期');
				return false;
			}

			if (operate == 'add') {
				if (addaffiche.getForm().findField("schemeId").getValue() == null
						|| addaffiche.getForm().findField("schemeId")
								.getValue() == '')
					operate = 'add';
				else
					operate = 'add2';// 新增窗口，但是为多次点击保存
			}
			// 需要判断时间不重复（本条规则启用时才判断）
			if (addaffiche.getForm().findField("isUsed").getValue() == '1') {
				var gradeType = addaffiche.getForm().findField("gradeType")
						.getValue();
				var schemeId = addaffiche.getForm().findField("schemeId")
						.getValue();
				var gradeUseage = addaffiche.getForm().findField("gradeUseage")
						.getValue();
				Ext.Ajax.request({
							url : basepath
									+ '/ocrmFCiGradeSchemeManage!ifExitSunTask.json',
							params : {
								'gradeType' : gradeType,
								'gradeUseage' : gradeUseage,
								'schemeId' : schemeId,
								'pstartDate' : _pstartDate,
								'pendDate' : _pendDate
							},
							method : 'GET',
							waitMsg : '正在查询数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : checkResult,
							failure : checkResult
						});
				function checkResult(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if (resultArray == 200 || resultArray == 201) {
						var ifExit = Ext.util.JSON
								.decode(response.responseText).ifExit;// 是否存时间段重复
						var time = Ext.util.JSON.decode(response.responseText).time;// 重复时间设置
						var name = Ext.util.JSON.decode(response.responseText).names;// 规则名称
						debugger;
						var gradeType1 = '';
						var gradeUseage1 = '';
						if (gradeType == '0')
							gradeType1 = '所有';
						if (gradeType == '1')
							gradeType1 = '零售';
						if (gradeType == '2')
							gradeType1 = '对公';

						if (gradeUseage == '3')
							gradeUseage1 = '忠诚度评级';
						if (gradeUseage == '4')
							gradeUseage1 = '流失预警评级';
						if (gradeUseage == '5')
							gradeUseage1 = '综合评级';

						if (ifExit == 'yes') {
							Ext.Msg.alert('提示', '本法人机构下存在已启用的针对' + gradeType1
											+ '客户的' + gradeUseage1
											+ '评级规则:<br>' + name
											+ ' 与本规则时间设置存在交叉，该规则时间配置如下：<br>'
											+ time + '<br>请调整本规则的时间配置');
							return false;
						} else {
							Ext.Msg.wait('正在保存，请稍后......', '系统提示');
							Ext.Ajax.request({
								url : basepath
										+ '/ocrmFCiGradeSchemeManage!saveData.json',
								params : {
									'operate' : operate
								},
								method : 'POST',
								form : addaffiche.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									if (operate = 'add') {
										Ext.Ajax.request({
											url : basepath
													+ '/ocrmFCiGradeSchemeManage!getPid.json',
											success : function(response) {// 获取当前保存记录的主键
												var id = Ext.util.JSON
														.decode(response.responseText).pid;
												addaffiche.getForm()
														.findField("schemeId")
														.setDisabled(false);
												addaffiche.getForm()
														.findField("schemeId")
														.setValue(id);
												Ext.Msg.alert('提示', '操作成功');
											}
										});
									}
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON
											.decode(response.status);
									if (resultArray == 403) {
										Ext.Msg.alert('提示',
												response.responseText);
									} else {

										Ext.Msg
												.alert(
														'提示',
														'操作失败,失败原因:'
																+ response.responseText);
									}
								}
							});
						}
					}
				};

			} else {
				Ext.Msg.wait('正在保存，请稍后......', '系统提示');
				Ext.Ajax.request({
					url : basepath + '/ocrmFCiGradeSchemeManage!saveData.json',
					params : {
						'operate' : operate
					},
					method : 'POST',
					form : addaffiche.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						if (operate = 'add') {
							Ext.Ajax.request({
								url : basepath
										+ '/ocrmFCiGradeSchemeManage!getPid.json',
								success : function(response) {// 获取当前保存记录的主键
									var id = Ext.util.JSON
											.decode(response.responseText).pid;
									addaffiche.getForm().findField("schemeId")
											.setDisabled(false);
									addaffiche.getForm().findField("schemeId")
											.setValue(id);
									Ext.Msg.alert('提示', '操作成功');
								}
							});
						}
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {

							Ext.Msg.alert('提示', '操作失败,失败原因:'
											+ response.responseText);
						}
					}
				});
			}

		}
	}]
});

var indexTreeListRecord = Ext.data.Record.create([{
			name : 'indexId',
			mapping : 'INDEX_ID'
		}, {
			name : 'indexCode',
			mapping : 'INDEX_CODE'
		}, {
			name : 'indexUse',
			mapping : 'INDEX_USE'
		}, {
			name : 'indexName',
			mapping : 'INDEX_NAME'
		}, {
			name : "INDEX_USE_ORA"
		}]);
// 指标类型树树加载属性值
var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
			checkField : 'ASTRUE',
			parentAttr : 'ROOT',// 指向父节点的属性列
			locateAttr : 'F_CODE',// 类型编号
			rootValue : 1000,
			textField : 'F_VALUE',// 类型名称
			idProperties : 'F_CODE'// 主键
		});

Ext.getCmp('gradeFormula').on("focus", function(listPanel, rowIndex, event) {
	if (addaffiche.form.findField('gradeUseage').getValue() == ''
			|| addaffiche.form.findField('gradeUseage').getValue() == null) {
		Ext.Msg.alert('提示', '请先选择评级类型！!');
		return false;
	}

	FormulaWindow.removeAll(true);
	FormulaWindow.add({
		width : 210,
		height : document.body.clientHeight,
		layout : 'border',
		// frame : true ,
		items : [{
					region : 'north',
					id : 'FORMULAWINDOW',
					height : 75,
					title : '公式表达式',
					items : [{
								// fieldLabel : 'INDEX_ID',
								name : 'FORMULAWINDOWT',
								id : 'FORMULAWINDOWT',
								// xtype : 'textfield', // 设置为数字输入框类型
								width : 450,
								xtype : 'textarea',
								labelStyle : 'text-align:right;',
								disabled : true,
								anchor : '90%',
								enableKeyEvents : true,
								listeners : {
									'keypress' : function(field, e) {
										maskKeyEvent(e);
									},
									'specialkey' : function(field, e) {
										maskKeyEvent(e);
									},
									'focus' : function() {
										storeCaretWT(this);
									}
								}
							}]
				}, {
					region : 'center',
					id : 'FORMULACONTENTWINDOW',
					title : '中文表达式',
					items : [{
								// fieldLabel : 'INDEX_ID',
								name : 'FORMULAWINDOWW',
								id : 'FORMULAWINDOWW',
								// xtype : 'textfield', // 设置为数字输入框类型
								width : 450,
								xtype : 'textarea',
								labelStyle : 'text-align:right;',
								disabled : true,
								anchor : '90%'
							}]

				}, {
					region : 'south',
					id : 'COUNTWINDOW',
					height : 200,
					title : '计算器',
					layout : 'border',
					items : [{
								region : 'center',
								id : 'FORMULAWINDOW1',
								width : 310,
								title : '基本输入',
								layout : 'column',
								items : [{
											columnWidth : .16666,
											layout : 'form',
											// labelWidth : 100, // 标签宽度
											// defaultType : 'textfield',
											border : false,
											items : [
													new Ext.Button({
																text : '1',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('1');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '2',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('2');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '3',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('3');
																	transCode2Name();
																}
															})]
										}, {
											columnWidth : .16666,
											layout : 'form',
											// labelWidth : 100, // 标签宽度
											// defaultType : 'textfield',
											border : false,
											items : [
													new Ext.Button({
																text : '4',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('4');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '5',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('5');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '6',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('6');
																	transCode2Name();
																}
															})]
										}, {
											columnWidth : .16666,
											layout : 'form',
											// labelWidth : 100, // 标签宽度
											// defaultType : 'textfield',
											border : false,
											items : [
													new Ext.Button({
																text : '7',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('7');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '8',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('8');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '9',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('9');
																	transCode2Name();
																}
															})]
										}, {
											columnWidth : .16666,
											layout : 'form',
											// labelWidth : 100, // 标签宽度
											// defaultType : 'textfield',
											border : false,
											items : [
													new Ext.Button({
																text : '0',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('0');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '+',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('+');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '-',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('-');
																	transCode2Name();
																}
															})]
										}, {
											columnWidth : .16666,
											layout : 'form',
											// labelWidth : 100, // 标签宽度
											// defaultType : 'textfield',
											border : false,
											items : [
													new Ext.Button({
																text : '*',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('*');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '/',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('/');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '(',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('(');
																	transCode2Name();
																}
															})]
										}, {
											columnWidth : .16666,
											layout : 'form',
											// labelWidth : 100, // 标签宽度
											// defaultType : 'textfield',
											border : false,
											items : [
													new Ext.Button({
																text : ')',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT(')');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '.',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('.');
																	transCode2Name();
																}
															}),
													new Ext.Button({
																text : '%',
																height : 50,
																width : 50,
																handler : function() {
																	insertWT('%');
																	transCode2Name();
																}
															})]
										}

								]
							}, {
								region : 'east',
								id : 'FORMULACONTENTWINDOW3',
								width : 140,
								title : '指标',
								items : [

								new Ext.Button({
									text : '指标选择',
									height : 50,
									width : 140,
									handler : function() {
										var indexTreeListstorex = new Ext.data.Store(
												{
													restful : true,
													proxy : new Ext.data.HttpProxy(
															{
																url : basepath
																		+ '/IndexbaseQueryAction.json',
																method : 'POST'
															}),
													reader : new Ext.data.JsonReader(
															{
																successProperty : 'success',
																root : 'json.data',
																totalProperty : 'json.count'
															},
															indexTreeListRecord)
												});
										rownum_target = new Ext.grid.RowNumberer(
												{
													header : 'No.',
													width : 28
												});

										var windowIndexGrid = new Ext.grid.GridPanel(
												{
													store : indexTreeListstorex,
													colModel : new Ext.grid.ColumnModel([
															rownum_target, {
																id : 'id',
																header : '指标编码',
																hidden : true,
																dataIndex : 'indexId'
															}, {
																header : '指标编号',
																dataIndex : 'indexCode'
															}, {
																header : '指标名称',
																dataIndex : 'indexName',
																width : 220
															}, {
																header : '指标用途',
																dataIndex : 'INDEX_USE_ORA'
															}]),

													id : 'indexTreeList',
													region : 'center'// ,
													// title:"指标列表"
												});

										windowIndexGrid.on("rowdblclick",
												function(listPanel, rowIndex,
														event) {
													var selectRe = listPanel
															.getSelectionModel()
															.getSelections()[0].data;
													insertWT(selectRe.indexCode);
													indexMap[selectRe.indexCode] = selectRe.indexName;// 将选择的指标编码和指标名称放入JSON对象
													transCode2Name();
													IndexWindow.hide();
												});

										var IndexWindow = new Ext.Window({
													plain : true,
													defaults : {
														overflow : 'auto',
														autoScroll : true
													},
													layout : 'border',
													frame : true,
													resizable : true,
													draggable : true,
													closable : true,
													closeAction : 'hide',
													modal : true, // 模态窗口
													shadow : true,
													loadMask : true,
													maximizable : true,
													collapsible : true,
													titleCollapse : true,
													border : false,
													width : 500,
													height : 400,
													buttonAlign : "center",
													title : '指标选择',
													buttons : [{
																text : '返回',
																handler : function() {
																	IndexWindow
																			.hide();
																}
															}],
													items : [
													// windowIndexTree,
													windowIndexGrid]
												});

										indexTreeListstorex.load({
											params : {
												typeid : addaffiche.form
														.findField('gradeUseage')
														.getValue()
											}
										});
										IndexWindow.show();

									}
								}), new Ext.Button({
											text : '<-',
											height : 50,
											width : 140,
											handler : function() {
												delContent();
												transCode2Name();
											}
										}),

								{
									fieldLabel : 'INDEX_ID',
									name : 'WINDOW_INDEX_ID',
									id : 'WINDOW_INDEX_ID',
									xtype : 'hidden',
									labelStyle : 'text-align:right;',
									anchor : '90%'
								}, {
									fieldLabel : 'INDEX_ID',
									name : 'TEMP',
									id : 'TEMP',
									value : '',
									xtype : 'hidden',
									labelStyle : 'text-align:right;',
									anchor : '90%'
								}, {
									fieldLabel : 'INDEX_ID',
									name : 'TEMPCONTENT',
									id : 'TEMPCONTENT',
									value : '',
									xtype : 'hidden',
									labelStyle : 'text-align:right;',
									anchor : '90%'
								}]

							}]
				}]
	});
	Ext.getCmp('FORMULAWINDOWT').setValue(addaffiche.getForm()
			.findField("gradeFormula").getValue());
	Ext.getCmp('FORMULAWINDOWW').setValue(addaffiche.getForm()
			.findField("gradeFormulaExplain").getValue());
	getIndexMap();
	FormulaWindow.show();
});

// 打开页面，进行“评级公式”中指标及对应名称的查询，将结果放在一个JSON对象中
var indexMap = null;
function getIndexMap() {
	var indexCode = addaffiche.getForm().findField("gradeFormula").getValue();
	Ext.Ajax.request({
				async : false,
				url : basepath + '/ocrmFCiGradeSchemeManage!getIndexMap.json',
				params : {
					'indexPre' : 'D',
					'indexCodeLength' : 7,
					'indexCode' : indexCode
				},
				method : 'GET',
				waitMsg : '正在查询数据,请等待...',
				success : function(response) {
					indexMap = Ext.util.JSON.decode(response.responseText);
					transCode2Name();
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询指标数据出错！');
				}
			});
}

// 将“评级公式”转换成中文
function transCode2Name() {
	var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
	if (wtValue && wtValue != "") {
		// 将“评级公式”中的指标编码转换成指标名称，其它字符不变
		for (var key in indexMap) {
			// wtValue = wtValue.replace(key,
			// indexMap[key]);//替换单个指标（如果有多个相同指标，只替换第一个）
			wtValue = wtValue.replace(new RegExp(key, "gm"), indexMap[key]);// 替换所有指标（如果有多个相同指标，替换所有相同的指标）
		}
	}
	Ext.getCmp('FORMULAWINDOWW').setValue(wtValue);// 赋值
	Ext.getCmp('FORMULAWINDOWT').setDisabled(false);// 让“评级公式”输入框可编辑
}

// 在textarea的光标处增加字符
function insertWT(value) {
	var FORMULAWINDOWT = Ext.getCmp("FORMULAWINDOWT");
	if (Ext.isIE) {
		insertAtCaretWT(FORMULAWINDOWT.el.dom, value);
	} else {
		var startPos = FORMULAWINDOWT.el.dom.selectionStart;
		var endPos = FORMULAWINDOWT.el.dom.selectionEnd;
		FORMULAWINDOWT.el.dom.value = FORMULAWINDOWT.el.dom.value.substring(0,
				startPos)
				+ value
				+ FORMULAWINDOWT.el.dom.value.substring(endPos,
						FORMULAWINDOWT.el.dom.value.length);

		FORMULAWINDOWT.el.focus();
		FORMULAWINDOWT.el.dom.setSelectionRange(endPos + value.length, endPos
						+ value.length);
	}
}

function storeCaretWT() {
	if (Ext.getCmp("FORMULAWINDOWT").el.dom.createTextRange) {
		Ext.getCmp("FORMULAWINDOWT").el.dom.curRange = document.selection
				.createRange().duplicate();
	}
}

// 给textarea定义单击事件
Ext.getDoc().on('click', function handleDocClick(e) {
	if (Ext.getCmp("FORMULAWINDOWT")
			&& document.activeElement.id == "FORMULAWINDOWT") {
		if (Ext.getCmp("FORMULAWINDOWT").el.dom.createTextRange) {
			Ext.getCmp("FORMULAWINDOWT").el.dom.curRange = document.selection
					.createRange().duplicate();
		}
	}
});

// 在textarea的光标处插入字符
function insertAtCaretWT(txtobj, txt) {
	if (txtobj.curRange) {
		Ext.getCmp("FORMULAWINDOWT").el.focus();
		txtobj.curRange.text = txt;
		txtobj.curRange.select();
	} else {
		txtobj.focus();
		storeCaretWT(txtobj);
		insertAtCaretWT(txtobj, txt);
	}
}

// 屏蔽键盘事件
function maskKeyEvent(event) {
	event.stopEvent();
}

// 删除textarea中光标前的一个字符
function delContent() {
	var cusorPos = getCusorPostion();// 获取光标位置
	if (cusorPos > 0) {
		// 光标位置大于0（光标前有字符）
		var textareaObj = Ext.getCmp('FORMULAWINDOWT');
		var wtValue = textareaObj.getValue();
		var preValue = wtValue.substring(0, cusorPos - 1);
		var sufValue = wtValue.substring(cusorPos);
		textareaObj.setValue(preValue + sufValue);
		go2Pos(cusorPos - 1);
	}
}

// 获取textarea中光标的位置
function getCusorPostion() {
	var txb = document.getElementById('FORMULAWINDOWT');// 根据ID获得对象
	var pos = 0;// 设置初始位置
	txb.focus();// 输入框获得焦点,这句也不能少,不然后面会出错,血的教训啦.
	var s = txb.scrollTop;// 获得滚动条的位置
	var r = document.selection.createRange();// 创建文档选择对象
	var t = txb.createTextRange();// 创建输入框文本对象
	t.collapse(true);// 将光标移到头
	t.select();// 显示光标,这个不能少,不然的话,光标没有移到头.当时我不知道,搞了十几分钟
	var j = document.selection.createRange();// 为新的光标位置创建文档选择对象
	r.setEndPoint("StartToStart", j);// 在以前的文档选择对象和新的对象之间创建对象,妈的,不好解释,我表达能力不算太好.有兴趣自己去看msdn的资料
	var str = r.text;// 获得对象的文本
	var re = new RegExp("[\\n]", "g");// 过滤掉换行符,不然你的文字会有问题,会比你的文字实际长度要长一些.搞死我了.我说我得到的数字怎么总比我的实际长度要长.
	str = str.replace(re, "");// 过滤
	pos = str.length;// 获得长度.也就是光标的位置
	r.collapse(false);
	r.select();// 把光标恢复到以前的位置
	txb.scrollTop = s;// 把滚动条恢复到以前的位置
	return pos;
}

// 将光标定位到textarea的某个位置
function go2Pos(pos) {
	var ta1 = document.getElementById('FORMULAWINDOWT');// 根据ID获得对象
	ta1.focus();
	var o = ta1.createTextRange();
	o.move("character", pos);
	o.select();
}

// 校验评级公式正确性：正确，返回true；错误，返回false
// 逻辑处理：将评级公式中的指标编码替换成数字20，然后调用js的eval方法执行表达式运算，如果出错，证明评级公式配置有问题，给出提示
function checkFormula() {
	var result = true;
	var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
	if (wtValue && wtValue != null && wtValue != "") {
		for (var key in indexMap) {
			// wtValue = wtValue.replace(key, (20));//
			// 将指标编码转换成数字（转换成(20)），替换单个指标（如果有多个相同指标，只替换第一个）
			wtValue = wtValue.replace(new RegExp(key, "gm"), "(20)");// 将指标编码转换成数字（转换成(20)），替换所有指标（如果有多个相同指标，替换所有相同的指标）
		}
		// 执行公式
		try {
			eval(wtValue);
		} catch (e) {
			result = false;
		}
	}
	return result;
}