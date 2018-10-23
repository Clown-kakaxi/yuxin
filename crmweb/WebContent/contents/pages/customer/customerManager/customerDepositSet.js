/*******************************************************************************
 * menu:客户管理》客户托管》客户托管设置 auth:sujm in chengdu
 */
var custStr = "", custNameStr = "";

Ext.onReady(function() {
	Ext.QuickTips.init();
	var afterMgrName = new Ext.ux.form.CustMgrField({
				fieldLabel : '托管客户经理名称',
				labelStyle : 'text-align:right;',
				name : 'afterMgrName',
				id : 'afterMgrName',
				width : 200,
				editable : false,
				allowBlank : false,
				// 不允许为空
				blankText : "不能为空，请填写",
				singleSelected : true
			});

	function Todate(num) {
		// Fri Oct 31 18:00:00 UTC+0800 2008
		num = num + ""; // 给字符串后就一个空格
		var date = "";
		var month = new Array();
		month["Jan"] = 1;
		month["Feb"] = 2;
		month["Mar"] = 3;
		month["Apr"] = 4;

		month["May"] = 5;
		month["Jan"] = 6;
		month["Jul"] = 7;
		month["Aug"] = 8;

		month["Sep"] = 9;
		month["Oct"] = 10;
		month["Nov"] = 11;
		month["Dec"] = 12;
		var week = new Array();
		week["Mon"] = "一";
		week["Tue"] = "二";
		week["Wed"] = "三";
		week["Thu"] = "四";

		week["Fri"] = "五";
		week["Sat"] = "六";
		week["Sun"] = "日";
		str = num.split(" "); // 根据空格组成数组
		date = str[5] + "-"; // 就是在2008的后面加一个“-”

		// 通过修改这里可以得到你想要的格式
		date = date + month[str[1]] + "-" + str[2] + " " + str[3];

		// date=date+" 周"+week[str[0]];
		return date;
	}

	var depositPanel = new Ext.FormPanel({
		frame : true,
		region : 'center',
		autoScroll : true,
		items : [{
					xtype : 'fieldset',
					title : '托管基本信息',
					layout : 'column',
					items : [{
								columnWidth : .5,
								layout : 'form',
								items : [{
											id : 'cust_num',
											name : 'cust_num',
											fieldLabel : '选中客户数量',
											readOnly : true,
											xtype : 'textfield',
											labelStyle : 'text-align:right;',
											anchor : '95%'
										}, afterMgrName, {
											id : 'bcId_zh',
											name : 'ID',
											xtype : 'textfield',
											hidden : true
										}]
							},/*
								 * { columnWidth : .5, layout : 'form', items : [ ] },
								 */{
								columnWidth : .5,
								layout : 'form',
								items : [{
											id : 'begDate',
											name : 'begDate',
											xtype : 'datefield',
											fieldLabel : '托管开始时间',
											allowBlank : false,
											labelStyle : 'text-align:right;',
											endDateField : 'endDate',
											anchor : '95%'

										}, {
											id : 'endDate',
											name : 'endDate',
											xtype : 'datefield',
											fieldLabel : '托管结束时间',
											allowBlank : false,
											labelStyle : 'text-align:right;',
											startDateField : 'begDate',
											anchor : '95%'
										}]
							}]
				}, {
					xtype : 'fieldset',
					title : '托管模块',
					layout : 'column',
					items : [{
						items : [{
									layout : 'form',
									columnWidth : .5,
									items : [{
												fieldLabel : '客户概要信息',
												xtype : 'checkboxgroup',
												id : 'managedModule',
												columns : [200],
												items : [{
															boxLabel : '客户概要信息',
															id : '1'
														}]
											}, {
												fieldLabel : '客户基本信息',
												xtype : 'checkboxgroup',
												id : 'managedModule2',
												columns : [200],
												items : [{
															id : '21',
															boxLabel : '基本信息'
														}, {
															id : '22',
															boxLabel : '联系信息'
														}, {
															id : '23',
															boxLabel : '家庭信息'
														}, {
															id : '24',
															boxLabel : '理财信息'
														}, {
															id : '25',
															boxLabel : '兴趣爱好信息'
														}, {
															id : '26',
															boxLabel : '信用卡信息'
														}, {
															id : '27',
															boxLabel : '电子渠道签约信息'
														}]
											}, {
												fieldLabel : '客户财务信息',
												xtype : 'checkboxgroup',
												id : 'managedModule3',
												columns : [200],
												items : [{
															id : '31',
															boxLabel : '客户全行资产负债信息'
														}, {
															id : '32',
															boxLabel : '录入他行资产负债'
														}, {
															id : '33',
															boxLabel : '录入其他资产负债'
														}, {
															id : '34',
															boxLabel : '录入家庭阅读收支'
														}]
											}]
								}, {
									layout : 'form',
									columnWidth : .5,
									items : [{
												fieldLabel : '客户业务信息',
												xtype : 'checkboxgroup',
												id : 'managedModule4',
												columns : [200],
												items : [{
															id : '41',
															boxLabel : '存款业务'
														}, {
															id : '42',
															boxLabel : '贷款业务'
														}, {
															id : '43',
															boxLabel : '国债业务'
														}, {
															id : '44',
															boxLabel : '理财业务'
														}, {
															id : '45',
															boxLabel : '基金业务'
														}, {
															id : '46',
															boxLabel : '信用卡业务'
														}]
											}, {
												fieldLabel : '客户营销信息',
												xtype : 'checkboxgroup',
												id : 'managedModule5',
												columns : [200],
												items : [{
															id : '51',
															boxLabel : '客户接触历史'
														}, {
															id : '52',
															boxLabel : '客户提醒历史'
														}]
											}]
								}

						]

					}]
				}]
	});

	// 机构分配模态窗口
	var depositWin = new Ext.Window({
		plain : true,
		id : 'depositWin',
		layout : 'anchor',
		resizable : true,
		draggable : true,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true,
		// 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : false,// 是否展示最大化 按钮
		collapsible : false,// 是否可收缩
		maximized : false,// 是否最大化打开窗口
		titleCollapse : true,
		border : false,
		width : 700,
		height : 400,
		buttonAlign : "center",
		title : '客户托管',
		items : [depositPanel],
		buttons : [{
			text : '保存',
			handler : function() {
				var managedModuleStr = "1";// 组织托管模块的码值串，默认托管（‘1’ 客户概要信息）

				for (var j = 2; j < 6; j++) {
					if (depositPanel.form.findField("managedModule" + j)
							.getValue().length > 0) {
						for (var i = 0; i < depositPanel.form
								.findField("managedModule" + j).getValue().length; i++) {
							managedModuleStr = managedModuleStr
									+ ","
									+ depositPanel.form
											.findField("managedModule" + j)
											.getValue()[i].id;
						}
					}
				}

				if (!depositPanel.form.isValid()) {
					return false;
				}
				var result = '';
				var mgrId = afterMgrName.userId.aId[0]; // 托管客户经理编号
				var mgrName = Ext.getCmp('afterMgrName').getValue(); // 托管客户经理名称
				var begDate1 = Ext.getCmp('begDate').getValue();
				var begDate = Todate(begDate1);
				var endDate1 = Ext.getCmp('endDate').getValue();
				var endDate = Todate(endDate1);
				var records = listPanel.grid.selModel.getSelections();
				// debugger;
				result += custStr + ',&' + custNameStr + ',&' + mgrId + ','
						+ mgrName + ',' + begDate + ',' + endDate + ',&'
						+ managedModuleStr;
				// debugger;
				// return false;
				// result += belMgrId + ',&';
				//			

				Ext.Ajax.request({
							url : basepath
									+ '/customer_deposit!batchDeposit.json',
							params : {
								data : result
							},
							waitMsg : '正在保存数据,请等待...',
							// 显示读盘的动画效果，执行完成后效果消失
							method : 'POST',
							scope : depositWin,
							success : function(a, b) {
								Ext.Msg.alert('提示', '操作成功');
								// 通过以下代码，可以实现操作成功后加载操作后的数据

								listPanel.loadCurrData();
							},
							failure : function(a, b) {
								Ext.Msg.alert('提示', '操作失败');

								listPanel.loadCurrData();
							}
						});
				depositWin.hide();
				// debugger;
			}
		}, {
			text : '关闭',
			handler : function() {
				depositWin.hide();
				// afterMgrName.userId.aId[0] = '';
				// //调整后客户经理编号

				// depositWin.hide();
			}
		}]
	});

	var mainTypeStore = new Ext.data.ArrayStore({
				fields : ['myId', 'displayText'],
				data : [['1', '主办'], ['2', '协办']]
			});
	// 证件类型
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
	// 客户类型
	var custTypStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=XD000080'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	custTypStore.load();
	// 客户级别
	var custLevStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name='
									+ 'C_CUST_LEV'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	custLevStore.load();

	// **客户经理分配end**********************************************************
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
				id : "listPanel",
				title : "客户管理 -> 客户托管 -> 客户托管设置",
				stUrl : basepath + '/customer_deposit.json',// basepath +
				primary : "CUST_ID",
				checkbox : true,
				// 定义查询条件Form的高度
				seFormHeight : 60,
				// 定义增删详情页面弹出窗口高度
				winHeight : 250,
				// 宽度
				winWidth : 600,
				dbclick : false,
				selectItems : {
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90,
								defaultType : 'textfield',
								border : false,
								items : [{
											name : 'CUST_ZH_NAME',
											xtype : 'textfield',
											fieldLabel : '客户中文名称',
											width : '100',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								border : false,
								items : [{
											store : certTypStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '证件类型',
											name : 'CERT_TYPE',
											hiddenName : 'CERT_TYPE',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											width : '100',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90,
								defaultType : 'textfield',
								border : false,
								items : [{
											name : 'CERT_NUM',
											xtype : 'textfield',
											fieldLabel : '证件号码',
											width : '100',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90,
								defaultType : 'textfield',
								border : false,
								items : [{
											store : custLevStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '客户级别',
											name : 'CUST_LEV',
											hiddenName : 'CUST_LEV',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											width : '100',
											anchor : '90%'
										}]
							}]
				},
				buts : [{
					id : 'assignOrg',
					xtype : 'button',
					tooltip : '客户托管',
					text : '客户托管',
					iconCls : 'completeIconCss',
					listeners : {
						click : function(n) {
							// debugger;
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel
										.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度

								var isApproveStr = "";// 记录已托管状态的客户
								var beforeAppStr = "";// 记录已提交待审批客户
								var countNum = 0, countNum_dsp = 0;
								// debugger;
								for (var i = 0; i < records.length; i++) {
									// 获得选择托管设置的客户ID串 和 客户NAME串
									custStr = custStr + records[i].json.CUST_ID
											+ ",";
									custNameStr = custNameStr
											+ records[i].json.CUST_ZH_NAME
											+ ",";
									if (records[i].json.APPROVE_STAT == '2') {
										isApproveStr += records[i].json.CUST_ZH_NAME
												+ "</br>";
										countNum++;
									} else if (records[i].json.APPROVE_STAT == '1') {
										beforeAppStr += records[i].json.CUST_ZH_NAME
												+ "</br>";
										countNum_dsp++;
									}
								}
								var msg = "";
								if (countNum > 0) {
									Ext.Msg.alert("提示",
											"托管失败，如下客户已通过托管审批：</br>"
													+ isApproveStr);
									return false;
								}
								if (countNum_dsp > 0) {
									Ext.Msg.alert("提示",
											"托管失败，如下客户已提交设置待审批：</br>"
													+ beforeAppStr);
									return false;
								}

								sId = listPanel.grid.getSelectionModel()
										.getSelected().get('CUST_ID');
								depositWin.setAnimateTarget('depositWin');
								depositWin.setTitle("客户托管(" + recordsLen + ")");
								depositWin.show();
								depositPanel.form.findField('managedModule')
										.setValue([1]);
								depositPanel.form.findField('managedModule')
										.disable();
								depositPanel.form.findField("cust_num")
										.setValue(recordsLen);
								depositPanel.form.findField('afterMgrName')
										.setValue('');
								depositPanel.form.findField('begDate')
										.setValue('');
								depositPanel.form.findField('endDate')
										.setValue('');
								// }
							} else {
								Ext.Msg.alert("提示", "请先选择要分配的记录!");
							}
						}
					}
				}],
				// 查询字段定义，若不定义则不出现查询条件From

				gclms : [{
							name : 'org_id'
						}, {
							name : 'org_name'
						}, {
							name : 'CUST_ID',
							sortable : true,
							header : '客户编号',
							width : 200
						}, {
							name : 'CUST_ZH_NAME',
							sortable : true,
							header : '客户中文名称',
							width : 200
						}, {
							name : 'CERT_TYPE',
							header : '证件类型',
							type : 'mapping',
							store : certTypStore,
							mappingkey : 'key',
							mappingvalue : 'value',
							width : 180
						}, {
							name : 'CERT_NUM',
							sortable : true,
							header : '证件号码',
							width : 200
						}, {
							name : 'CUST_LEV',
							sortable : true,
							header : '客户级别',
							type : 'mapping',
							store : custLevStore,
							mappingkey : 'key',
							mappingvalue : 'value',
							width : 150
						}, {
							name : 'APPROVE_STAT',
							// sortable : true,
							header : '托管状态',
							renderer : function(value, p, r) {
								if (value == "2")
									return "<span style='color:red;'>已托管</span>";
								else if (value == "1")
									return "<span style='color:blue;'>待审批</span>";
								else
									return "<span style='color:green;'>未托管</span>";
							},
							width : 150
						}],
				pagesize : 20
			});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [listPanel]
			});
});