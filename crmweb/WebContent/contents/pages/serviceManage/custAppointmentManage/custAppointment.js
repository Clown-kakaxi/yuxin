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
						title : "预约信息维护",
						stUrl : basepath + '/appointmentManage.json',
						addUrl : basepath + '/appointmentManage.json',
						updateUrl : basepath + '/appointmentManage.json',
//						detailUrl : basepath + '/appointmentManage.json',
						winHeight : 420,
						conditionStr : {
							'CREATE_USER' : __userId
					    },
					    createFun:function()
						{
								Ext.getCmp('userName').hide();
								Ext.getCmp('createDate').hide();
								Ext.getCmp('updateUserName').hide();
								Ext.getCmp('updateDate').hide();
								
						},
						editFun : function() {
							var appointStat = custAppointmentPanel.grid
									.getSelectionModel().getSelected().get(
											'appointStat');
							if (appointStat != '01') {
								Ext.Msg.alert('提示', '只能修改预约状态为创建的预约信息!');
								custAppointmentPanel.win.hide();
							}
							Ext.getCmp('userName').show();
							Ext.getCmp('createDate').show();
							Ext.getCmp('updateUserName').show();
							Ext.getCmp('updateDate').show();
						},
						detailFun:function()
						{
							Ext.getCmp('userName').show();
							Ext.getCmp('createDate').show();
							Ext.getCmp('updateUserName').show();
							Ext.getCmp('updateDate').show();
							
					    },
						deleteFun : function() {
							var records = custAppointmentPanel.grid.selModel
									.getSelections();// 得到被选择的行的数组
							var selectLength = records.length;// 得到行数组的长度
							var appointStat;
							for ( var i = 0; i < selectLength; i++) {
								selectRe = records[i];
								appointStat = selectRe.get('appointStat');
								if (appointStat != '01') {
									Ext.Msg.alert('提示', '只能删除预约状态为创建的服务信息!');
									return false;
								}
							};
							return true;
						},
						 saveSubmitFun : function()
						 {
						 var dateTemp = this.fp.form.findField('appointTime').value;
						 var myDate = new Date();
						 myDate = Ext.util.Format.date(myDate, 'Y-m-d');
						 if(dateTemp < myDate)
						 {
						 Ext.Msg.alert('提示','预约时间必须在今天之后!');
						 return false;
						 }
						 return true;
						 },
						deUrl : basepath + '/appointmentManage!batchDestroy.json',
						primary : "appointId",
						checkbox : true,
						// 定义查询条件Form的高度
						seFormHeight : 80,
						createFun:function()
						{
								Ext.getCmp('userName').hide();
								Ext.getCmp('createDate').hide();
								Ext.getCmp('updateUserName').hide();
								Ext.getCmp('updateDate').hide();
								
						},
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
						},{
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
						},{
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
											name : 'APPOINT_STAT',
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
															callback : function(a,b) {
															debugger;
																var cust_name = null;
																cust_name = Ext.getCmp('CUST_NAMEIDFS').getValue();
																if (cust_name != null
																		&& cust_name != '') {
																	var cust_id = a.customerId;
																	var cert_no = a.certNum;
																	var telephone = a.mobileNum;
																	var mgr_id = a.mgrId;
																	var mgr_name = a.mgrName;
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('custId').setValue(cust_id);
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('certNo').setValue(cert_no);
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('telephone').setValue(telephone);
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('mgrId').setValue(mgr_id);
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('MGR_NAME').setValue(mgr_name);
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
										    	   	name : 'MGR_NAME',
										    	   	fieldLabel : '所属客户经理',
										    	   	readOnly : true,
										    	   	width : 100,
										    	   	xtype : 'textfield',
										    	   	anchor : '90%'
										       }, 
										       {
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
												roleType:'1',
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
											xtype : 'hidden',
											name : 'createOrg',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'pOrC',
											anchor : '90%'
										},{
											id :'userName',
											xtype : 'textfield',
											fieldLabel : '创建人',
											readOnly : true,
											name : 'USER_NAME',
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											id :'createDate',
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
											id : 'updateUserName',
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
											id : 'updateDate',
											xtype : 'textfield',
											fieldLabel : '最近更新日期',
											readOnly : true,
											name : 'updateDate',
											anchor : '90%'
										} ]
									} ]
								},{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ 
										{
											store : appointSourceStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '预约渠道',
											name : 'APPOINT_SOURCE',
											editable : false,
											hiddenName : 'appointSource',
											readOnly : true,
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											value:'9',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											width : '100',
											anchor : '90%'
										}
										]
									}]
								} ]
					});

			// 布局模型
			var viewport = new Ext.Viewport( {
				layout : 'fit',
				frame : true,
				items : [ custAppointmentPanel ]
			});

		})