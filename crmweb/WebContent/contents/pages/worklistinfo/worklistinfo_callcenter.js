/**
 * @description
 * @since ?
 * @update 20140923
 */
Ext
		.onReady(function() {
			Ext.QuickTips.init();
			var instanceid = curNodeObj.instanceid;
			var nodeid = curNodeObj.nodeid;
			var custId = curNodeObj.custId;
			var id = '';
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/perSxInfoReview.json',
					method : 'GET'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'json.count',
					root : 'json.data'
				}, [ {
					name : 'custId',
					mapping : 'CUST_ID'
				}, {
					name : 'CORE_NO'
				}, {
					name : 'updateItem',
					mapping : 'UPDATE_ITEM'
				}, {
					name : 'updateBeCont',
					mapping : 'UPDATE_BE_CONT'
				}, {
					name : 'updateAfCont',
					mapping : 'UPDATE_AF_CONT'
				}, {
					name : 'updateUser',
					mapping : 'UPDATE_USER'
				}, {
					name : 'userName',
					mapping : 'USER_NAME'
				}, {
					name : 'updateDate',
					mapping : 'UPDATE_DATE'
				}, {
					name : 'UPDATE_FLAG'
				}, {
					name : 'APPR_FLAG'
				} ])
			});
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 35
			});

			// 定义列模型
			var cm = new Ext.grid.ColumnModel([ rownum, {
				header : '客户编号',
				dataIndex : 'custId',
				sortable : 100,
				width : 100
			}, {
				header : '核心客户号',
				dataIndex : 'CORE_NO',
				sortable : 100,
				width : 100
			}, {
				header : '变更项目',
				dataIndex : 'updateItem',
				sortable : true,
				width : 120
			}, {
				header : '变更前内容',
				dataIndex : 'updateBeCont',
				sortable : true,
				width : 300
			}, {
				header : '变更后内容',
				dataIndex : 'updateAfCont',
				sortable : true,
				width : 300
			}, {
				header : '修改人',
				dataIndex : 'userName',
				sortable : true,
				width : 100
			}, {
				header : '修改时间',
				dataIndex : 'updateDate',
				sortable : true,
				width : 135
			}, {
				header : '状态',
				dataIndex : 'APPR_FLAG',
				sortable : true,
				width : 100,
				hidden : !curNodeObj.approvalHistoryFlag,
				renderer : function(val) {
					if (val == '1') {
						return '同意';
					} else if (val == '2') {
						return '否决/撤办';
					} else {
						return '复核中';
					}
				}
			} ]);
			// 表格实例
			var rowClassFlag = false;
			var rowHisFlag = "";
			var grid = new Ext.grid.GridPanel(
					{
						id : 'viewgrid',
						frame : true,
						height : 400,
						autoScroll : true,
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						store : store, // 数据存储
						stripeRows : true, // 斑马线
						cm : cm, // 列模型
						viewConfig : {
							getRowClass : function(record, rowIndex, rowParams,
									store) {
								var tempflag = record.data.UPDATE_FLAG ? record.data.UPDATE_FLAG
										.substr(
												record.data.UPDATE_FLAG.length - 4,
												4)
										: '0000';
								if (rowHisFlag != tempflag) {
									rowClassFlag = !rowClassFlag;
									rowHisFlag = tempflag;
								}
								// 根据是否修改状态修改背景颜色
								if (rowClassFlag) {
									return 'my_row_set_blue';
								} else {
									return 'my_row_set_blue';
								}
							},
							forceFit : false,
							autoScroll : true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			var bussFieldSetGrid = new Ext.form.FieldSet({
				animCollapse : true,
				collapsible : true,
				title : '流程业务信息',
				items : [ grid ]
			});
			var opinionFlagStore = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/callCenter.json?flag=opinion',
					method : 'get'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'json.count',
					root : 'json.data'
				}, [ 'ID', 'COMMENTCONTENT' ])
			});
			var opinionFlag = new Ext.form.FormPanel({
				frame : true, // 是否渲染表单面板背景色
				height : 200,
				labelAlign : 'right', // 标签对齐方式
				buttonAlign : 'center',
				items : [ {
					layout : 'column',
					border : false,
					items : [ {
						layout : 'form',
						columnWidth : 0.7,
						labelWidth : 80, // 标签宽度
						items : [ {
							xtype : 'textarea',
							fieldLabel : '流程意见内容',
							height : 90,
							maxLength : 200,
							name : 'COMMENTCONTENT',
							anchor : '90%'
						} ]
					} ]
				} ]

			});
			opinionFlagStore.load({
				params : {
					custId : custId
				},
				callback : function() {
					if (opinionFlagStore.getCount() != 0) {
						opinionFlag.getForm().loadRecord(
								opinionFlagStore.getAt(0));
						id = opinionFlagStore.getAt(0).data.ID;
					}
				}
			})

			var checkOpinionSetGrid = new Ext.form.FieldSet({
				animCollapse : true,
				collapsible : true,
				title : '流程审批意见',
				items : [ opinionFlag ]
			});

			var view = new Ext.Panel(
					{
						renderTo : 'viewEChian',
						frame : true,
						width : document.body.scrollWidth,
						height : document.body.scrollHeight - 40,
						autoScroll : true,
						layout : 'form',
						buttonAlign : 'center',
						items : [ bussFieldSetGrid, checkOpinionSetGrid ],
						buttons : [
								{
									text : '保存',
									handler : function() {
										Ext.Ajax
												.request({
													url : basepath
															+ '/callCenter!saveOpinion.json',
													method : 'GET',
													params : {
														id : id,
														commentContent : opinionFlag
																.getForm()
																.findField(
																		'COMMENTCONTENT')
																.getValue()
													},
													success : function(response) {
														opinionFlagStore
																.load({
																	params : {
																		custId : custId
																	},
																	callback : function() {
																		if (opinionFlagStore
																				.getCount() != 0) {
																			debugger;
																			opinionFlag
																					.getForm()
																					.loadRecord(
																							opinionFlagStore
																									.getAt(0));
																			id = opinionFlagStore
																					.getAt(0).data.ID;
																		}
																	}
																})
														opinionFlag.getForm()
																.reset();
														// Ext.Msg.alert('系统提示信息',
														// '保存意见成功!' );
													}
												});
									}
								},
								{
									text : '提交',
									handler : function() {
										Ext.Msg.show({
													title : "系统提示",
													icon:Ext.MessageBox.QUESTION,
													msg : "是否同意修改客户信息",
													buttons:{yes:"同意",no:"否决",cancel:true},
													//buttons : Ext.Msg.YESNOCANCEL,
													closable:false,
													fn : function(e) {
														if ('yes' == e) {
															Ext.Ajax
																	.request({
																		url : basepath
																				+ '/callCenter!endY.json',
																		method : 'GET',
																		params : {
																			instanceid : instanceid,
																		},
																		success : function(
																				response) {
																			Ext.Ajax
																					.request({
																						url : basepath
																								+ '/callCenter!saveOpinion.json',
																						method : 'GET',
																						params : {
																							id : id,
																							commentContent : opinionFlag
																									.getForm()
																									.findField(
																											'COMMENTCONTENT')
																									.getValue()
																						},
																						success : function(
																								response) {
																							// _this.flowStore.reload();
																							// _this.opinionFlag.getForm().reset();
																							// Ext.Msg.alert('系统提示信息',
																							// '保存意见成功!'
																							// );
																						}
																					});
																			if (Ext
																					.getCmp('viewWindow') != null) {
																				Ext
																						.getCmp(
																								'viewWindow')
																						.close();
																			}
																			Ext.Msg
																					.alert(
																							'系统提示信息',
																							'流程办理成功!');
																		}
																	});
														} else if ('no' == e) {
															Ext.Ajax
																	.request({
																		url : basepath
																				+ '/callCenter!endN.json',
																		method : 'GET',
																		params : {
																			nodeid : nodeid,
																			custId : custId
																		},
																		success : function(
																				response) {
																			debugger;
																			Ext.Ajax
																					.request({
																						url : basepath
																								+ '/callCenter!saveOpinion.json',
																						method : 'GET',
																						params : {
																							id : id,
																							commentContent : opinionFlag
																									.getForm()
																									.findField(
																											'COMMENTCONTENT')
																									.getValue()
																						},
																						success : function(
																								response) {
																							// _this.flowStore.reload();
																							// _this.opinionFlag.getForm().reset();
																							// Ext.Msg.alert('系统提示信息',
																							// '保存意见成功!'
																							// );
																						}
																					});
																			if (Ext
																					.getCmp('viewWindow') != null) {
																				Ext
																						.getCmp(
																								'viewWindow')
																						.close();
																			}
																			Ext.Msg
																					.alert(
																							'系统提示信息',
																							'流程办理成功!');
																		}
																	});
														}
													}
												});

									}
								} ]
					});
			// 加载业务数据
			store.load({
				params : {
					'instanceId' : instanceid
				}
			});
		});
