Ext
		.onReady(function() {
			Ext.QuickTips.init();
			var appointStatStore = new Ext.data.Store( {
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=APPOINT_STAT'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			appointStatStore.load();
			
			var appointSourceStore =  new Ext.data.ArrayStore({
				fields : [ 'key', 'value'  ],
				data : [ [ 1, 'cc' ], 
				         [ 2, '柜面' ],
						[ 3, '大堂' ],
						[ 4, '网银' ],
						[ 5, '手机银行' ],
						[ 6, '门户' ],
						[ 7, '自助设备' ],
						[ 8, '填单机' ],
						[ 9, '录入' ]
						]
			});
			var custAppointmentPanel = new Mis.Ext.CrudPanel(
					{
						id : "custAppointmentPanel",
						title : "预约网点处理",
						stUrl : basepath + '/appointmentManage.json',
						detailUrl : basepath + '/appointmentManage.json',
						winHeight : 380,
						conditionStr : {
							'APPOINT_ORG' : __units
					    },
						buts : [
								{
									text : '预约成功',
									iconCls : 'editIconCss',
									id : 'suBut',
									handler : function() {
										if (custAppointmentPanel.grid.selModel
												.hasSelection()) {// 判断是否选择记录
											var records = custAppointmentPanel.grid.selModel
													.getSelections();// 得到被选择的行的数组
											var selectLength = records.length;// 得到行数组的长度
											if (selectLength != 1) {// 判断是否只选择一条记录
												Ext.Msg.alert('提示信息',
														'请选择一条记录！');
											} else {
												var selectRe = records[0];
												var appointId = selectRe.data.appointId;// 获得选中记录的id
												var appointStat = selectRe.data.appointStat;
												if (appointStat != '01') {
													Ext.Msg.alert('提示',
															'只能处理状态为创建的预约信息!');
												} else if (appointId != '') {
													handleWindow.show();
													handleForm.getForm()
															.loadRecord(
																	selectRe);
													handleForm.getForm().findField('appointStat').setValue('02');
												}
											}
										} else {
											Ext.Msg.alert('提示信息', '请选择一条记录！');
										}
									}
								},
								{
									text : '预约失败',
									iconCls : 'editIconCss',
									id : 'faBut',
									handler : function() {
										if (custAppointmentPanel.grid.selModel
												.hasSelection()) {// 判断是否选择记录
											var records = custAppointmentPanel.grid.selModel
													.getSelections();// 得到被选择的行的数组
											var selectLength = records.length;// 得到行数组的长度
											if (selectLength != 1) {// 判断是否只选择一条记录
												Ext.Msg.alert('提示信息',
														'请选择一条记录！');
											} else {
												var selectRe = records[0];
												var appointId = selectRe.data.appointId;// 获得选中记录的id
												var appointStat = selectRe.data.appointStat;
												if (appointStat != '01') {
													Ext.Msg.alert('提示',
															'只能处理状态为创建的预约信息!');
												} else if (appointId != '') {
													handleWindow.show();
													handleForm.getForm()
															.loadRecord(
																	selectRe);
													handleForm.getForm().findField('appointStat').setValue('03');
												}
											}
										} else {
											Ext.Msg.alert('提示信息', '请选择一条记录！');
										}
									}
								} ],
						primary : "appointId",
						checkbox : true,
						// 定义查询条件Form的高度
						seFormHeight : 80,
						// 查询字段定义，若不定义则不出现查询条件Form
						selectItems : {
							layout : 'column',
							items : [
									{
										columnWidth : .3,
										layout : 'form',
										items : [ new Com.yucheng.bcrm.common.CustomerQueryField(
												{
													fieldLabel : '客户名称',
													labelStyle : 'text-align:right;',
													labelWidth : 100,
													id : 'CUST_NAME',
													name : 'CUST_NAME',
													custtype : '1',// 客户类型：
																	// 1：对私,
																	// 2:对公,
																	// 不设默认全部
													singleSelected : true,// 单选复选标志
													editable : false,
													anchor : '95%',
													hiddenName : 'CUST_ID'
												}) ]
									}, {
										columnWidth : .3,
										layout : 'form',
										border : false,
										items : [ {
											xtype : 'textfield',
											name : 'CERT_NO',
											triggerAction : 'all',
											anchor : '90%',
											fieldLabel : '证件号码'
										} ]
									}, {
										columnWidth : .3,
										layout : 'form',
										labelWidth : 110,
										defaultType : 'textfield',
										border : false,
										items : [ {
											store : appointStatStore,
											xtype : 'combo',
											resizable : true,
											editable : false,
											fieldLabel : '预约状态',
											name : 'APPOINT_STAT',
											hiddenName : 'APPOINT_STAT',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											width : '150',
											anchor : '90%'
										} ]
									} ]
						},

						gclms : [ {
							name : 'appointId',
							mapping : 'APPOINT_ID',
							xtype : 'hidden'
						}, {
							name : 'appointStat',
							mapping : 'APPOINT_STAT',
							header : '预约状态',
							type : 'mapping',
							store : appointStatStore,
							mappingkey : 'key',
							mappingvalue : 'value'
						},{
							name : 'appointSource',
							mapping : 'APPOINT_SOURCE',
							header : '预约来源',
							type : 'mapping',
							store : appointSourceStore,
							mappingkey : 'key',
							mappingvalue : 'value'
						}, {
							name : 'custId',
							header : '客户编号',
							mapping : 'CUST_ID'
						}, {
							name : 'custName',
							header : '客户名称',
							mapping : 'CUST_NAME'
						}, {
							name : 'certNo',
							header : '证件号码',
							mapping : 'CERT_NO'
						}, {
							name : 'MGR_NAME',
							header : '所属客户经理'
						}, {
							name : 'telephone',
							mapping : 'TELEPHONE',
							header : '客户联系电话'
						}, {
							name : 'appointEvent',
							header : '预约事项',
							mapping : 'APPOINT_EVENT'
						}, {
							name : 'appointTime',
							header : '预约时间',
							mapping : 'APPOINT_TIME'
						}, {
							name : 'APPOINT_ORG_NAME',
							header : '预约网点'
						}, {
							name : 'appointResult',
							mapping : 'APPOINT_RESULT',
							xtype : 'hidden'
						}, {
							name : 'handleUser',
							mapping : 'HANDLE_USER',
							xtype : 'hidden'
						}, {
							name : 'HANDLE_USER_NAME',
							header : '处理人'
						}, {
							name : 'mgrId',
							mapping : 'MGR_ID',
							xtype : 'hidden'
						}, {
							name : 'appointOrg',
							mapping : 'APPOINT_ORG',
							xtype : 'hidden'
						}, {
							name : 'appointResult',
							mapping : 'APPOINT_RESULT',
							xtype : 'hidden'
						}, {
							name : 'createUser',
							mapping : 'CREATE_USER',
							xtype : 'hidden'
						},{
							name : 'pOrC',
							mapping : 'P_OR_C',
							xtype : 'hidden'
						},{
							name : 'createOrg',
							mapping : 'CREATE_ORG',
							xtype : 'hidden'
						}, {
							name : 'USER_NAME',
							header : '创建人'
						}, {
							name : 'createDate',
							mapping : 'CREATE_DATE',
							header : '创建日期'
						}, {
							name : 'updateUser',
							mapping : 'UPDATE_USER',
							xtype : 'hidden'
						}, {
							name : 'UPDATEUSER_NAME',
							xtype : 'hidden'
						}, {
							name : 'updateDate',
							mapping : 'UPDATE_DATE',
							xtype : 'hidden'
						} ],
						pagesize : 20,
						// 新增、修改、详情的form的字段
						fclms : [
								{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : appointStatStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '预约状态',
											name : 'appointStat',
											editable : false,
											hiddenName : 'appointStat',
											value : '01',
											readOnly : true,
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
										}, {
											name : 'appointId',
											xtype : 'hidden'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [  {
											name : 'custId',
											fieldLabel : '客户编号',
											readOnly : true,
											width : 100,
											xtype : 'textfield',
											anchor : '90%'
										} ]
									} ]
								},
								{
									layout : 'column',
									items : [
											{
												columnWidth : .5,
												layout : 'form',
												items : [  new Com.yucheng.bcrm.common.CustomerQueryField(
														{
															fieldLabel : '*客户名称',
															labelWidth : 100,
															allowBlank : false,
															blankText : '此项不能为空',
															id : 'CUST_NAMEIDFS',
															name : 'custName',
															custtype : '1',// 客户类型：
																			// 1：对私,
																			// 2:对公,
																			// 不设默认全部
															singleSelected : true,// 单选复选标志
															editable : false,
															anchor : '95%',
															hiddenName : 'CUST_ID',
															callback : function() {
																var cust_name = null;
																cust_name = Ext.getCmp('CUST_NAMEIDFS').getValue();
																if (cust_name != null
																		&& cust_name != '') {
																	var cust_id = Ext.getCmp('CUST_NAMEIDFS').customerId;
																	var cert_no = Ext.getCmp('CUST_NAMEIDFS').certNum;
																	var telephone = Ext.getCmp('CUST_NAMEIDFS').mobileNum;
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('custId').setValue(cust_id);
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('certNo').setValue(cert_no);
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('telephone').setValue(telephone);
																}
															}
														}) ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												items : [  {
													xtype : 'textfield',
													allowBlank : false,
													blankText : '此项不能为空',
													fieldLabel : '*证件号码',
													name : 'certNo',
													anchor : '90%'
												} ]
											} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											name : 'telephone',
											allowBlank : false,
											blankText : '此项不能为空',
											fieldLabel : '*客户联系电话',
											width : 100,
											xtype : 'textfield',
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ 
										       {
										    	    id : 'MGR_NAME',
										    	   	name : 'MGR_NAME',
										    	   	fieldLabel : '所属客户经理',
										    	   	readOnly : true,
										    	   	width : 100,
										    	   	xtype : 'textfield',
										    	   	anchor : '90%'
										       }, 
										       {
										    	    id : 'MGR_ID',
										    	   	name : 'mgrId',
										    	   	width : 100,
										    	   	xtype : 'hidden',
										    	   	anchor : '90%'
										       } 
										         ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ new Ext.form.DateField( {
											name : 'appointTime',
											format : 'Y-m-d',
											editable : false,
											fieldLabel : '*预约时间',
											allowBlank : false,
											blankText : '此项不能为空',
											anchor : '90%'
										}) ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ 
								    	   	new Com.yucheng.bcrm.common.OrgField({
												searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
												fieldLabel : '*预约网点',
												allowBlank : false,
												blankText : '此项不能为空',
												id : 'appointOrgName', //放大镜组件ID，用于在重置清空时获取句柄
												name : 'APPOINT_ORG_NAME', 
												hiddenName: 'appointOrg',   //后台获取的参数名称
												anchor : '90%',
												checkBox:false //复选标志
											}) ]
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'appointEvent',
										xtype : 'textarea',
										fieldLabel : '*预约事项',
										allowBlank : false,
										blankText : '此项不能为空',
										maxLength : 1000,
										anchor : '90%'
									}]
								}, {
									layout : 'form',
									items : [ {
										name : 'appointResult',
										xtype : 'textarea',
										disabled :true,
										fieldLabel : '预约结果',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '处理人ID',
											disabled :true,
											name : 'handleUser',
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '处理人',
											disabled :true,
											name : 'HANDLE_USER_NAME',
											anchor : '90%'
										} ]
									} ]
								},
								{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'hidden',
											fieldLabel : '创建人ID',
											name : 'createUser',
											anchor : '90%'
										}, {
											xtype : 'textfield',
											fieldLabel : '创建人',
											readOnly : true,
											name : 'USER_NAME',
											anchor : '90%'
										} ,{
											xtype : 'hidden',
											name : 'createOrg',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'pOrC',
											anchor : '90%'
										}]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '创建日期',
											readOnly : true,
											name : 'createDate',
											anchor : '90%'
										} ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'hidden',
											fieldLabel : '最近更新人ID',
											name : 'updateUser',
											anchor : '90%'
										}, {
											xtype : 'textfield',
											fieldLabel : '最近更新人',
											name : 'UPDATEUSER_NAME',
											readOnly : true,
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '最近更新日期',
											readOnly : true,
											name : 'updateDate',
											anchor : '90%'
										} ]
									} ]
								} ]
					});

			var handleForm = new Ext.form.FormPanel(
					{
						labelWidth : 120,
						frame : true,
						labelAlign : 'right',
						region : 'center',
						autoScroll : true,
						buttonAlign : "center",
						items : [ {
							layout : 'form',
							items : [ {
								name : 'appointResult',
								xtype : 'textarea',
								fieldLabel : '*预约结果',
								allowBlank : false,
								blankText : '此项不能为空',
								maxLength : 1000,
								anchor : '90%'
							}, {
								name : 'appointStat',
								xtype : 'hidden'
							},{
								name : 'appointId',
								xtype : 'hidden'
							}, {
								name : 'custId',
								xtype : 'hidden'
							}, {
								name : 'custName',
								xtype : 'hidden'
							}, {
								name : 'certNo',
								xtype : 'hidden'
							}, {
								name : 'telephone',
								xtype : 'hidden'
							}, {
								name : 'appointEvent',
								xtype : 'hidden'
							}, {
								name : 'appointTime',
								xtype : 'hidden'
							}, {
								name : 'appointResult',
								xtype : 'hidden'
							}, {
								name : 'mgrId',
								xtype : 'hidden'
							}, {
								name : 'appointOrg',
								xtype : 'hidden'
							},  {
								name : 'createUser',
								xtype : 'hidden'
							},  {
								name : 'createDate',
								xtype : 'hidden'
							},{
								xtype : 'hidden',
								name : 'createOrg',
								anchor : '90%'
							},{
								xtype : 'hidden',
								name : 'pOrC',
								anchor : '90%'
							}]
						} ],
						buttons : [
								{
									text : '保  存',
									handler : function() {
										if (!handleForm.getForm().isValid()) {
											Ext.Msg.alert('提示', '输入不合法，请重新输入');
											return false;
										}
										Ext.Ajax
												.request( {
													url : basepath + '/appointmentManage.json',
													method : 'POST',
													form : handleForm.getForm().id,
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : function() {
														Ext.Msg.alert('提示',
																'操作成功');
														custAppointmentPanel.grid.store
																.load();
													},
													failure : function(response) {
														var resultArray = Ext.util.JSON
																.decode(response.status);
														if (resultArray == 403) {
															Ext.Msg
																	.alert(
																			'提示',
																			response.responseText);
														} else {
															Ext.Msg
																	.alert(
																			'提示',
																			'操作失败,失败原因:' + response.responseText);
														}
														custAppointmentPanel.grid.store
																.load();
													}
												});
										handleWindow.hide();
									}
								}, {
									text : '取  消',
									handler : function() {
										handleWindow.hide();
									}
								} ]
					});

			var handlePanel = new Ext.Panel( {
				labelWidth : 600,
				height : 180,
				layout : 'fit',
				buttonAlign : "center",
				items : [ handleForm ]
			});

			var handleWindow = new Ext.Window( {
				title : '预约处理',
				plain : true,
				layout : 'fit',
				width : 600,
				height : 180,
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
				items : [ handlePanel ]
			});

			// 布局模型
			var viewport = new Ext.Viewport( {
				layout : 'fit',
				frame : true,
				items : [ custAppointmentPanel ]
			});

		})