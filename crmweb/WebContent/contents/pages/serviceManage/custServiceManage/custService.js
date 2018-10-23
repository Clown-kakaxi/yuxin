Ext
		.onReady(function() {
			debugger;
			Ext.QuickTips.init();
			var serviceExecute = function(selectRe,infoStatus)
			{
				var serviceId = selectRe.data.serviceId;
				var custId = selectRe.data.custId;
				var custName = selectRe.data.custName;
				document.getElementById('mktId').value = serviceId;
				document.getElementById('custIdExe').value = custId;
				document.getElementById('custNameExe').value = custName;
				serviceExecuteWindow.show();
				serviceExeInit(serviceId);	
				if(infoStatus == '03')
					serviceExecutePanel.grid.tbar.hide();
				else
					serviceExecutePanel.grid.tbar.show();
				Ext.Ajax.request({
					url : basepath + '/custServiceManage!updateStat.json',
					params : {
						stat : infoStatus?infoStatus:'02',
						idStr : serviceId
					},
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					method : 'GET',
					scope : this,
					success : function() {
						custServicePanel.loadCurrData();
					}
				});
			};
			
			var serviceStatStore = new Ext.data.Store( {
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=SERVICE_STAT'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			serviceStatStore.load();
			var serviceKindStore = new Ext.data.Store( {
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=SERVICE_KIND'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			serviceKindStore.load();
			var planChannelStore = new Ext.data.Store( {
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=SERVICE_CHANNEL'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			planChannelStore.load();
			var custServicePanel = new Mis.Ext.CrudPanel(
					{
						id : "custServicePanel",
						title : "客户服务管理",
						winHeight : 400,
						stUrl : basepath + '/custServiceManage.json',
						addUrl : basepath + '/custServiceManage.json',
						updateUrl : basepath + '/custServiceManage.json',
//						detailUrl : basepath + '/custServiceManage.json', 
						createFun:function()
						{
								Ext.getCmp('userName').hide();
								Ext.getCmp('createDate').hide();
								Ext.getCmp('updateUserName').hide();
								Ext.getCmp('updateDate').hide();
								
						},
						editFun:function()
						{
								var infoStatus = custServicePanel.grid.getSelectionModel().getSelected().get('serviceStat');
								var createUser = custServicePanel.grid.getSelectionModel().getSelected().get('createUser');
								if (infoStatus != '01') {
									Ext.Msg.alert('提示','只能修改状态为创建的服务信息!');
									custServicePanel.win.hide();
								} 
								if(createUser != __userId)
								{
									Ext.Msg.alert('提示','只能修改自己创建的服务信息!');
									custServicePanel.win.hide();
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
						deleteFun:function()
						{
							var records = custServicePanel.grid.selModel.getSelections();// 得到被选择的行的数组
							var selectLength = records.length;// 得到行数组的长度
							var infoStatus,createUser;
							for ( var i = 0; i < selectLength; i++) {
								selectRe = records[i];
								infoStatus = selectRe.get('serviceStat');
								createUser = selectRe.get('createUser');
								if (infoStatus != '01') {
									Ext.Msg.alert('提示','只能删除状态为创建的服务信息!');
									return false;
								} 
								if (createUser != __userId) {
									Ext.Msg.alert('提示','只能删除自己创建的服务信息!');
									return false;
								} 
							};
							return true;
						},
						saveSubmitFun : function()
						{
							var dateS = this.fp.form.items.items[7].value;
							var dateE = this.fp.form.items.items[8].value;
							if(dateS > dateE)
							{
								Ext.Msg.alert('提示','计划开始日期应在预计结束日期之前!');
								return false;
							}
							return true;
							
						},
						deUrl : basepath + '/custServiceManage!batchDestroy.json',
						buts : [{
							text : '客户服务执行',
							iconCls : 'editIconCss',
							id : 'serviceExecuteBut',
							handler : function() {
								if (custServicePanel.grid.selModel
										.hasSelection()) {// 判断是否选择记录
									var records = custServicePanel.grid.selModel
											.getSelections();// 得到被选择的行的数组
									var selectLength = records.length;// 得到行数组的长度
									if (selectLength != 1) {// 判断是否只选择一条记录
										Ext.Msg.alert('提示信息', '请选择一条记录！');
									} else {
										var selectRe = records[0];
										var serviceId = selectRe.data.serviceId;// 获得选中记录的id
										var infoStatus = selectRe.data.serviceStat;
										var createUser = selectRe.data.createUser;
										if(createUser != __userId)
										{
											Ext.Msg.alert('提示','只能对自己创建的服务信息进行此项操作!');
											custServicePanel.win.hide();
										}
										else if (serviceId != '' && infoStatus == '01') {
											Ext.MessageBox.confirm('系统提示信息','确认进行执行操作吗？',
											function(buttonobj) {
												if (buttonobj == 'yes'){
													serviceExecute(selectRe);
												}
										 }, this);
										}
										else if(serviceId != '') {
											serviceExecute(selectRe,infoStatus);
										}
									}
								}
								else{
									Ext.Msg.alert('提示信息', '请选择一条记录！');
								}
							}
						}, {
							text : '服务补录',
							iconCls : 'editIconCss',
							id : 'additionalBut',
							handler : function() {
											infoAddWindow.show();
									}
						},{
							text : '服务结束',
							iconCls : 'editIconCss',
							id : 'serviceEndBut',
							handler : function() {
								if (custServicePanel.grid.selModel
										.hasSelection()) {// 判断是否选择记录
									var records = custServicePanel.grid.selModel
											.getSelections();// 得到被选择的行的数组
									var selectLength = records.length;// 得到行数组的长度
									if (selectLength < 1) {
										Ext.Msg.alert('提示信息', '请至少选择一条记录！');
									} else {
										var serviceId,infoStatus,createUser;
										var idStr = '';
										for ( var i = 0; i < selectLength; i++) {
											selectRe = records[i];
											serviceId = selectRe.data.serviceId;// 获得选中记录的id
											infoStatus = selectRe.data.serviceStat;
											createUser = selectRe.data.createUser;
											if (infoStatus != '02') {
												Ext.Msg.alert('提示','只能结束状态为执行的服务信息!');
												return false;
											};
											if(createUser != __userId)
											{
												Ext.Msg.alert('提示','只能结束自己创建的服务信息!');
												return false;
											};
											idStr += serviceId;
											if (i != selectLength - 1)
												idStr += ',';
										};
										if (idStr != '') {
											Ext.MessageBox.confirm('系统提示信息','确认进行结束操作吗？',
											function(buttonobj) {
												if (buttonobj == 'yes'){
												Ext.Ajax.request({
													url : basepath + '/custServiceManage!updateStat.json',
													params : {
														stat : '03',
														idStr : idStr
													},
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													method : 'GET',
													scope : this,
													success : function() {
														Ext.Msg.alert('提示信息', '操作成功！');
														custServicePanel.loadCurrData();
													}
												});}
										 }, this);
										}
									}
								}
								else{
									Ext.Msg.alert('提示信息', '请选择一条记录！');
								}
							}
						} ],
						primary : "serviceId",
						checkbox : true,
						// 定义查询条件Form的高度
						seFormHeight : 80,
						// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
						// 查询字段定义，若不定义则不出现查询条件Form
						selectItems : {
							layout : 'column',
							items : [
									{
										columnWidth : .3,
										layout : 'form',
										defaultType : 'textfield',
										border : false,
										items : [
												new Com.yucheng.bcrm.common.CustomerQueryField(
												{
													fieldLabel : '客户名称',
													labelStyle : 'text-align:right;',
													labelWidth : 100,
													id : 'CUST_NAME',
													name : 'CUST_NAME',
													singleSelected : true,// 单选复选标志
													editable : false,
													anchor : '95%',
													hiddenName : 'CUST_ID'
												}) ]
									}, {
										columnWidth : .3,
										layout : 'form',
										labelWidth : 110,
										defaultType : 'textfield',
										border : false,
										items : [ {
											store : serviceStatStore,
											xtype : 'combo',
											resizable : true,
											editable : false,
											fieldLabel : '服务状态',
											name : 'SERVICE_STAT',
											hiddenName : 'SERVICE_STAT',
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
									}, {
										columnWidth : .3,
										layout : 'form',
										border : false,
										items : [ {
											store : serviceKindStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '服务类别',
											editable : false,
											name : 'SERVICE_KIND',
											hiddenName : 'SERVICE_KIND',
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
							name : 'serviceId',
							mapping : 'SERVICE_ID',
							xtype : 'hidden'
						}, {
							name : 'serviceStat',
							mapping : 'SERVICE_STAT',
							header : '服务状态',
							type : 'mapping',
							store : serviceStatStore,
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
							name : 'serviceKind',
							mapping : 'SERVICE_KIND',
							header : '服务类别',
							type : 'mapping',
							store : serviceKindStore,
							mappingkey : 'key',
							mappingvalue : 'value'
						}, {
							name : 'serviceCont',
							header : '服务内容',
							mapping : 'SERVICE_CONT'
						}, {
							name : 'pStartDate',
							header : '计划开始日期',
							mapping : 'PSTART_DATE'

						}, {
							name : 'pEndDate',
							header : '计划结束日期',
							mapping : 'PEND_DATE'
						},{
							name : 'actualDate',
							mapping : 'ACTUAL_DATE',
							xtype : 'hidden'
						}, {
							name : 'planChannel',
							mapping : 'PLAN_CHANNEL',
							xtype : 'hidden'
						}, {
							name : 'cantactChannel',
							mapping : 'CANTACT_CHANNEL',
							xtype : 'hidden'
						},{
							name : 'serviceResult',
							mapping : 'SERVICE_RESULT',
							xtype : 'hidden'
						},{
							name : 'aimProd',
							mapping : 'AIM_PROD',
							xtype : 'hidden'
						},{
							name : 'needEvent',
							mapping : 'NEED_EVENT',
							xtype : 'hidden'
						},{
							name : 'createUser',
							mapping : 'CREATE_USER',
							xtype : 'hidden'
						},{
							name : 'createOrg',
							mapping : 'CREATE_ORG',
							xtype : 'hidden'
						}, {
							name : 'needResource',
							mapping : 'NEED_RESOURCE',
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
							header : '最近更新人'
						}, {
							name : 'updateDate',
							mapping : 'UPDATE_DATE',
							header : '最近更新日期'
						},{
							name : 'pOrC',
							mapping : 'P_OR_C',
							xtype : 'hidden'
						}],
						pagesize : 20,

						// 新增、修改、详情的form的字段
						fclms : [
								{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : serviceStatStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '服务状态',
											name : 'serviceStat',
											editable : false,
											hiddenName : 'serviceStat',
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
											name : 'serviceId',
											xtype : 'hidden'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : serviceKindStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '服务类别',
											editable : false,
											name : 'serviceKind',
											hiddenName : 'serviceKind',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											value : '01',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											selectOnFocus : true,
											width : '100',
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
												items : [ {
													id : 'custId',
													name : 'custId',
													fieldLabel : '客户编号',
													readOnly : true,
													width : 100,
													xtype : 'textfield',
													anchor : '90%'
												} ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												items : [ new Com.yucheng.bcrm.common.CustomerQueryField(
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
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('custId').setValue(cust_id);
																}
															}
														}) ]
											} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ new Com.yucheng.crm.common.ProductManage({ 
											 xtype:'productChoose',
												fieldLabel : '目标营销产品',
										 name : 'productName',
										  hiddenName:'aimProd',
										        singleSelect:false,
										        anchor : '90%'
										       })]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : planChannelStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '服务预计渠道',
											editable : false,
											name : 'planChannel',
											hiddenName : 'planChannel',
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
										} ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ new Ext.form.DateField( {
											name : 'pStartDate',
											format : 'Y-m-d',
											editable : false,
											fieldLabel : '*计划开始日期',
											allowBlank : false,
											blankText : '此项不能为空',
											anchor : '90%'
										}) ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											columnWidth : .5,
											layout : 'form',
											items : [ new Ext.form.DateField( {
												name : 'pEndDate',
												format : 'Y-m-d',
												editable : false,
												fieldLabel : '*预计结束日期',
												allowBlank : false,
												blankText : '此项不能为空',
												anchor : '90%'
											}) ]
										} ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											columnWidth : .5,
											layout : 'form',
											items : [ new Ext.form.DateField( {
												name : 'actualDate',
												disabled : true,
												format : 'Y-m-d',
												fieldLabel : '实际服务日期',
												anchor : '90%'
											}) ]
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : planChannelStore,
											disabled : true,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '实际接触渠道',
											name : 'cantactChannel',
											hiddenName : 'cantactChannel',
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
										} ]
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'serviceCont',
										xtype : 'textarea',
										fieldLabel : '*服务内容',
										allowBlank : false,
										blankText : '此项不能为空',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'needResource',
										xtype : 'textarea',
										fieldLabel : '所需资源',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'serviceResult',
										xtype : 'textarea',
										fieldLabel : '服务结果',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'needEvent',
										xtype : 'textarea',
										fieldLabel : '待跟进事项',
										maxLength : 2000,
										anchor : '90%'
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'hidden',
											fieldLabel : '创建人ID',
											name : 'createUser',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'createOrg',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'pOrC',
											anchor : '90%'
										},  {
											id : 'userName',
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
											id : 'createDate',
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
								} ]
					});
			
			var infoAddForm = new Ext.form.FormPanel(
					{
						labelWidth : 120,
						frame : true,
						labelAlign : 'right',
						id : 'infoAddi',
						region : 'center',
						autoScroll : true,
						buttonAlign : "center",
						items : [
								{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											id :'serviceStatForAddi',
											store : serviceStatStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '服务状态',
											value : '03',
											editable : false,
											name : 'serviceStat',
											hiddenName : 'serviceStat',
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
											name : 'serviceId',
											xtype : 'hidden'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : serviceKindStore,
											xtype : 'combo',
											resizable : true,
											editable : false,
											fieldLabel : '服务类别',
											name : 'serviceKind',
											hiddenName : 'serviceKind',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											value : '01',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											selectOnFocus : true,
											width : '100',
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
												items : [ {
													id : 'custId1',
													name : 'custId',
													fieldLabel : '客户编号',
													readOnly : true,
													width : 100,
													xtype : 'textfield',
													anchor : '90%'
												} ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												items : [ new Com.yucheng.bcrm.common.CustomerQueryField(
														{
															fieldLabel : '*客户名称',
															labelWidth : 100,
															allowBlank : false,
															blankText : '此项不能为空',
															id : 'CUST_NAMEIDFS1',
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
																cust_name = Ext.getCmp('CUST_NAMEIDFS1').getValue();
																if (cust_name != null
																		&& cust_name != '') {
																	var cust_id = Ext.getCmp('CUST_NAMEIDFS1').customerId;
																	this.ownerCt.ownerCt.ownerCt.getForm().findField('custId1').setValue(cust_id);
																}
															}
														}) ]
											} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [  {
											columnWidth : .5,
											layout : 'form',
											items : [ new Com.yucheng.crm.common.ProductManage({ 
												 xtype:'productChoose',
											        fieldLabel : '目标营销产品', 
											 name : 'productName',
											  hiddenName:'aimProd',
											        singleSelect:false,
											        anchor : '90%'
											       })]
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : planChannelStore,
											xtype : 'combo',
											resizable : true,
											editable : false,
											fieldLabel : '服务预计渠道',
											name : 'planChannel',
											hiddenName : 'planChannel',
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
										} ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ new Ext.form.DateField( {
											name : 'pStartDate',
											format : 'Y-m-d',
											editable : false,
											fieldLabel : '计划开始日期',
											anchor : '90%'
										}) ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											columnWidth : .5,
											layout : 'form',
											items : [ new Ext.form.DateField( {
												name : 'pEndDate',
												format : 'Y-m-d',
												editable : false,
												fieldLabel : '计划结束日期',
												anchor : '90%'
											}) ]
										} ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											columnWidth : .5,
											layout : 'form',
											items : [ new Ext.form.DateField( {
												name : 'actualDate',
												format : 'Y-m-d',
												editable : false,
												fieldLabel : '*实际服务日期',
												allowBlank : false,
												blankText : '此项不能为空',
												anchor : '90%'
											}) ]
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : planChannelStore,
											xtype : 'combo',
											resizable : true,
											editable : false,
											fieldLabel : '实际接触渠道',
											name : 'cantactChannel',
											hiddenName : 'cantactChannel',
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
										} ]
										
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'serviceCont',
										xtype : 'textarea',
										fieldLabel : '*服务内容',
										allowBlank : false,
										blankText : '此项不能为空',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'needResource',
										xtype : 'textarea',
										fieldLabel : '所需资源',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'serviceResult',
										xtype : 'textarea',
										fieldLabel : '*服务结果',
										allowBlank : false,
										blankText : '此项不能为空',
										maxLength : 1000,
										anchor : '90%'
									} ]
								}, {
									layout : 'form',
									items : [ {
										name : 'needEvent',
										xtype : 'textarea',
										fieldLabel : '待跟进事项',
										maxLength : 2000,
										anchor : '90%'
									}, {
										xtype : 'hidden',
										name : 'createOrg',
										anchor : '90%'
									},{
										xtype : 'hidden',
										name : 'pOrC',
										anchor : '90%'
									}]
								}],
						buttons : [
								{
									text : '保  存',
									handler : function() {
										if (!infoAddForm.getForm()
												.isValid()) {
											Ext.Msg.alert('提示', '输入不合法，请重新输入');
											return false;
										}
										Ext.Ajax
												.request( {
													url : basepath + '/custServiceManage.json',
													method : 'POST',
													form : infoAddForm.getForm().id,
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : function() {
														Ext.Msg.alert('提示',
																'操作成功');
														custServicePanel.grid.store.load();
													},
													failure : function(response) {
														var resultArray = Ext.util.JSON
																.decode(response.status);
														if (resultArray == 403) {
															Ext.Msg
																	.alert('提示',response.responseText);
														} else {
															Ext.Msg
																	.alert(
																			'提示',
																			'操作失败,失败原因:' + response.responseText);
														}
														custServicePanel.grid.store.load();
													}
												});
										infoAddWindow.hide();
									}
								}, {
									text : '取  消',
									handler : function() {
									infoAddWindow.hide();
									}
								} ]
					});
			
			
			
			var infoAddPanel = new Ext.Panel({
				labelWidth : 800,
				height : 380,
				layout : 'fit',
				buttonAlign : "center",
				items : [ infoAddForm ]
			});
			
			var infoAddWindow = new Ext.Window({
				title : '服务信息补录',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 380,
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
				items : [ infoAddPanel ]
			});
			
			var serviceExecuteWindow = new Ext.Window({
				title : '客户服务执行',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 380,
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
				items : [ serviceExecutePanel ]
			});
			
			// 布局模型
			var viewport = new Ext.Viewport( {
				layout : 'fit',
				frame : true,
				items : [ custServicePanel ]
			});

		})