var cantactChannelStore = new Ext.data.Store( {
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/lookup.json?name=SERVICE_CHANNEL'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
cantactChannelStore.load();
var serviceExeInit = function(serviceId){
	serviceExecutePanel.conditionStr = {
			'MKT_ID' : serviceId
	};
	serviceExecutePanel.loadCurrData();
};
var serviceExecutePanel = new Mis.Ext.CrudPanel(
					{
						id : "serviceExecutePanel",
						stUrl : basepath + '/MarketTractManage.json',
						addUrl : basepath + '/MarketTractManage.json',
						updateUrl : basepath + '/MarketTractManage.json',
						deUrl : basepath + '/MarketTractManage!batchDestroy.json',
						primary : "recordId",
						checkbox : true,
						needReset : false,
						winHeight : 330,
						gridHeight : 320,
						gridWidth : 780,
						defaultLoad : false,
						// 定义查询条件Form的高度
						seFormHeight : 80,
						// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
						gclms : [ {
							name : 'recordId',
							mapping : 'RECORD_ID',
							xtype : 'hidden'
						},{
							name : 'cantactDate',
							header : '服务时间',
							mapping : 'CANTACT_DATE'
						}, {
							name : 'custId',
							header : '客户编号',
							mapping : 'CUST_ID'
						}, {
							name : 'custName',
							header : '客户名称',
							mapping : 'CUST_NAME'
						},
						{
							name : 'cantactChannel',
							mapping : 'CANTACT_CHANNEL',
							header : '接触渠道',
							type : 'mapping',
							store : cantactChannelStore,
							mappingkey : 'key',
							mappingvalue : 'value'
						}, {
							name : 'marketResult',
							mapping : 'MARKET_RESULT',
							header : '服务结果'
						},{
							name : 'needEvent',
							mapping : 'NEED_EVENT',
							header : '待跟进事项'
						},{
							name : 'createUser',
							mapping : 'CREATE_USER',
							xtype : 'hidden'
						},{
							name : 'USER_NAME',
							header : '执行人'
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
						} ],
						pagesize : 20,
						createFun : function() {
						             	this.fp.getForm().findField('mktId').setValue(document.getElementById('mktId').value);
						             	this.fp.getForm().findField('custName').setValue(document.getElementById('custNameExe').value);
						             	this.fp.getForm().findField('custId').setValue(document.getElementById('custIdExe').value);
					     },

						// 新增、修改、详情的form的字段
						fclms : [
								{
									layout : 'column',
									items : [
											{
												columnWidth : .5,
												layout : 'form',
												items : [ {
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
												items : [ {
													name : 'custName',
													fieldLabel : '客户名称',
													readOnly : true,
													width : 100,
													xtype : 'textfield',
													anchor : '90%'
												}  ]
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
												name : 'cantactDate',
												format : 'Y-m-d',
												fieldLabel : '*服务日期',
												allowBlank : false,
												blankText : '此项不能为空',
												anchor : '90%'
											}) ]
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											store : cantactChannelStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '*接触渠道',
											allowBlank : false,
											blankText : '此项不能为空',
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
										name : 'marketResult',
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
											name : 'createUser',
											anchor : '90%'
										}, {
											xtype : 'textfield',
											fieldLabel : '执行人',
											readOnly : true,
											name : 'USER_NAME',
											anchor : '90%'
										} ]
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
										},{
											xtype : 'hidden',
											name : 'recordId',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'mktId',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'serviceKind',
											value : '01',
											anchor : '90%'
										}]
									} ]
								} ]
					});