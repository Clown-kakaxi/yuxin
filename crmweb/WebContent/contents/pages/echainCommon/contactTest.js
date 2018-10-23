Ext.onReady(function() {
			Ext.QuickTips.init(); 

			//查询form
			var qForm = new Ext.form.FormPanel({
				title : "联系人信息",
				labelWidth : 90, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'right', // 标签对齐方式
				buttonAlign : 'center',
				region:'north',
				split:true,
				height : 100,
				items : [ {
					layout : 'column',
					items : [  {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							Width : '100',
							name : 'NAME',
							fieldLabel : '联系人姓名',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : []
					} ]
				} ],
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = qForm.getForm().getValues(false);
						store.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
						store.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});

					}

				},{
					text : '重置',
					handler : function() {
	                	qForm.getForm().reset();
					}

				} ]
			});
			// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});

			var record = Ext.data.Record.create([ {name : 'id',mapping:'ID'},
			                                      {name : 'NAME'},
			                                      {name : 'TEL'},
			                                      {name : 'SEX'}]);

			// 定义列模型

			var cm = new Ext.grid.ColumnModel([ rownum, sm, {
				header : 'ID',
				hidden:true,
				dataIndex : 'id',
				sortable : true
			}, {header : '联系人名称',width : 150,dataIndex : 'NAME',sortable : true}, 
			{header : '联系人电话',width : 170,dataIndex : 'TEL',sortable : true}, 
			{header : '性别',width : 100,dataIndex : 'SEX',sortable : true}
			]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/querycontacttest.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'ID',
					messageProperty : 'message',
					root : 'json.data',
					totalProperty : 'json.count'
				}, record)
			});

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								[ 100, '100条/页' ], [ 250, '250条/页' ],
								[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});

			// 默认加载数据
			store.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			
			// 表格工具栏
			var tbar = new Ext.Toolbar(
					{
						items : [
									{
										text : '新增',
										handler : function() {
											addContactForm.getForm().reset();
											addInit();
										}
									},
									'-',
									{
										text : '修改',
										handler : function() {
											var selectLength = grid.getSelectionModel()
											.getSelections().length;

											var selectRe = grid.getSelectionModel()
											.getSelections()[0];

											if (selectLength != 1) {
												Ext.Msg.alert('提示','请选择一条记录!');
											} else {
												editContactForm.getForm()
														.loadRecord(selectRe);
												editInit();

											}
										}

									},
									'-',
									{
										text : '删除',
										handler : function() {
											var selectLength = grid
											.getSelectionModel()
											.getSelections().length;

									if (selectLength < 1) {
										Ext.Msg.alert('提示','请选择需要删除的记录!');
									}

									else {
										Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
											if(buttonId.toLowerCase() == "no"){
													return;
												}  
											var selectRe;
											var tempId;
											var tempCount;
											var idStr = '';
											for ( var i = 0; i < selectLength; i++) {
												selectRe = grid.getSelectionModel()
														.getSelections()[i];
												tempId = selectRe.data.id;
												idStr += tempId;
												if (i != selectLength - 1)
													idStr += ',';
											}
											Ext.Ajax.request({
														url : basepath
																+ '/contactTest!batchDestroy.json?idStr='
																+ idStr,
														method:'POST',
														waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
														success : function() {
														Ext.Msg.alert('提示', '操作成功!' );
															store.reload();
														},
														failure : function() {
														
															Ext.Msg.alert('提示', '操作失败!' );
														}
													});
										});
									}
								}
									},
									'-',
									{
										text : '详情',
										handler : function() {
											var selectLength = grid.getSelectionModel()
											.getSelections().length;

											var selectRe = grid.getSelectionModel()
											.getSelections()[0];

											if (selectLength != 1) {
												Ext.Msg.alert('提示','请选择一条记录!');
											} else {
												viewContactForm.getForm().loadRecord(selectRe);
												detailInit();
											}
										}
									},'-',{
										text:'发起申请',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/contactTest!testWorkflow.json',
											method : 'GET',
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											var instanceId = '';
											if(a.responseText.length>0){
												instanceId = Ext.decode(a.responseText).instanceId;
											}
												Ext.Msg.alert('提示', '操作成功!,流程实例号:'+instanceId);
												store.reload();
											},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'查询待办列表',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!getUserAllTodoWorkList.json',
											method : 'GET',
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'提交节点',
										handler:function(){
										var obj = {a:'a'};
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!wfCompleteJob.json',
											method : 'GET',
											params : {
											instanceID:'F7F51C759F6012AE',
											nodeID : '1_a12',
											paramMap1 : Ext.encode(obj)
										},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'保存流程',
										handler:function(){
										var obj = {a:'a'};
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!wfSaveJob.json',
											method : 'GET',
											params : {
											InstanceID:'F7F54847D1D39956',
											nodeID : '1_a12',
											commentContent : '哈哈哈哈哈',
											commentType : '1',
											paramMap1 : Ext.encode(obj)
										},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'获取下一节点列表',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!getNextNodeList.json',
											method : 'GET',
											params : {
											InstanceID:'F7F54847D1D39956',
											nodeID : '1_a12'
										},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'获取节点办理用户列表',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!getNodeUserList.json',
											method : 'GET',
											params : {
											InstanceID:'F7F51C759F6012AE',
											nodeID : '1_a9'
										},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'查询意见',
										handler:function(){
										var obj = {a:'a'};
										Ext.Ajax.request({
//											url : basepath + '/EchainCommon!getUserComment.json',
											url : basepath + '/EchainCommon!getAllComments.json',
											method : 'GET',
											params : {
											InstanceID:'F7F54847D1D39956'/*,
											nodeID : '1_a12',
											commentType : '1'*/
										},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'提交意见',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!setComment.json',
											method : 'GET',
											params : {
											InstanceID:'F7F54847D1D39956',
											nodeID : '1_a12',
											commentContent : '哦啦啦啦',
											commentType : '1'
										},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'查询办理信息',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!getWorkFlowHistory.json',
											method : 'GET',
											params : {
												InstanceID:'F7F54847D1D39956'
											},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									},'-',{
										text:'查询实例信息',
										handler:function(){
										Ext.Ajax.request({
											url : basepath + '/EchainCommon!getInstanceInfo.json',
											method : 'GET',
											params : {
												InstanceID:'F7F54847D1D39956',
												nodeID : '1_a12'
											},
											waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function(a) {
											debugger;
										},
											failure : function() {
												Ext.Msg.alert('提示', '操作失败!' );
											}
										});
									}
									}]
					});

			// 新增窗口展示的from
			var addContactForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 150,
				frame : true,
				region : 'center',
				autoScroll : true,
				labelAlign:'right',
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									name : 'NAME',
									fieldLabel : '*联系人名称',
									allowBlank : false,
									maxLength:30,
									minLength:1,
									anchor : '90%'
								} ]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'numberfield',
									name : 'TEL',
									fieldLabel : '*电话',
									allowBlank : false,
									allowNegative:false,
									maxLength:20,
									minLength:1,
									anchor : '90%'
								} ]
							} ,{
								columnWidth : .5,
								layout : 'form',
								items : [ {
			         					xtype : 'combo',
			        					fieldLabel : '性别',
			        					name : 'SEX', 
			        					editable:false,
										allowBlank : false,
			                            emptyText:'请选择',
			                            mode : 'local',
			                            triggerAction:'all',
			                            store:new Ext.data.ArrayStore({
			             			       fields : ['value', 'key'],
			            			       data : [['男','1'],['女','0']]
			            			   }),
			                            valueField:'key',
			                            displayField:'value',
			                            anchor : '90%',labelStyle:'text-align:right;'
			                        }]
							}]
				}, {
					layout : 'form',
					buttonAlign : 'center',

					buttons : [ {
						text : '保  存',
						handler : function() {
							if(!addContactForm.getForm().isValid())
							{ 
								Ext.Msg.alert('提示','输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/contactTest.json',
								method : 'POST',
								form : addContactForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function(response) {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function(response) {
									Ext.Msg.alert("提示",'操作失败!原因：'+response.responseText);
								}
							});
							addContactWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							addContactWindow.hide();
						}
					} ]
				} ]
			});

			// 修改窗口展示的from
			var editContactForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 150,
				frame : true,
				region : 'center',
				autoScroll : true,
				labelAlign:'right',
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [
								{
									columnWidth : .5,
									layout : 'form',
									items : [ {
										// 隐藏的ID
										xtype : 'hidden',
										name : 'id',
										anchor : '90%'
									},{
										xtype : 'textfield',
										name : 'NAME',
										fieldLabel : '*联系人名称',
										allowBlank : false,
										maxLength:30,
										minLength:1,
										vtype:'alphanum',
										anchor : '90%'
									} ]
								},{
									columnWidth : .5,
									layout : 'form',
									items : [ {
										xtype : 'numberfield',
										name : 'TEL',
										fieldLabel : '*电话',
										allowBlank : false,
										allowNegative:false,
										maxLength:20,
										minLength:1,
										anchor : '90%'
									} ]
								} ,{
									columnWidth : .5,
									layout : 'form',
									items : [ {
				         					xtype : 'combo',
				        					fieldLabel : '性别',
				        					name : 'SEX', 
				        					editable:false,
											allowBlank : false,
				                            emptyText:'请选择',
				                            mode : 'local',
				                            triggerAction:'all',
				                            store:new Ext.data.ArrayStore({
				             			       fields : ['value', 'key'],
				            			       data : [['男','1'],['女','0']]
				            			   }),
				                            valueField:'key',
				                            displayField:'value',
				                            anchor : '90%',labelStyle:'text-align:right;'
				                        }]
								}]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					buttons : [
					{
						text : '保  存',
						handler : function() {
							if(!editContactForm.getForm().isValid())
							{ 
								Ext.Msg.alert('提示','输入格式有误，请重新输入!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/contactTest.json',
								method : 'POST',
								form : editContactForm.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功!');
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('提示', '操作失败!' );
								}
							});
							editContactWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							editContactWindow.hide();
						}
					} ]
				}
				]
			});

			// 详情展示的from
			var viewContactForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 150,
				frame : true,
				region : 'center',
				autoScroll : true,
				labelAlign:'right',
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [
								{
									columnWidth : .5,
									layout : 'form',
									items : [ {
										// 隐藏的ID
										xtype : 'hidden',
										name : 'id',
										anchor : '90%'
									},{
										xtype : 'textfield',
										name : 'NAME',
										fieldLabel : '*联系人名称',
										allowBlank : false,
										maxLength:30,
										minLength:1,
										anchor : '90%'
									} ]
								},{
									columnWidth : .5,
									layout : 'form',
									items : [ {
										xtype : 'numberfield',
										name : 'TEL',
										fieldLabel : '*电话',
										allowBlank : false,
										allowNegative:false,
										maxLength:20,
										minLength:1,
										anchor : '90%'
									} ]
								} ,{
									columnWidth : .5,
									layout : 'form',
									items : [ {
				         					xtype : 'combo',
				        					fieldLabel : '性别',
				        					name : 'SEX', 
				        					editable:false,
											allowBlank : false,
				                            emptyText:'请选择',
				                            mode : 'local',
				                            triggerAction:'all',
				                            store:new Ext.data.ArrayStore({
				             			       fields : ['value', 'key'],
				            			       data : [['男','1'],['女','0']]
				            			   }),
				                            valueField:'key',
				                            displayField:'value',
				                            anchor : '90%',labelStyle:'text-align:right;'
				                        }]
								}]
				}, {
					layout : 'form',
					buttonAlign : 'center',

					buttons : [
					    {
						text : '返  回',
						handler : function() {
							viewContactWindow.hide();
						}
					} ]
				}
				]
			});

			// 定义新增窗口
			var addContactWindow = new Ext.Window({
				title : '新增联系人',
				plain : true,
				layout : 'fit',
				width : 800,
				height :150,
				resizable : true,
				draggable : true,
				closable : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				loadMask : true,
				maximizable : true,
				collapsible : true,
				titleCollapse : true,
				buttonAlign : 'right',
				border : false,
				items : [ addContactForm ]
			});

			// 定义修改窗口
			var editContactWindow = new Ext.Window({
				title : '修改联系人',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 150,
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
				items : [ editContactForm ]
			});
			
			// 定义详情窗口
			var viewContactWindow = new Ext.Window({
				title : '联系人详情',
				plain : true,
				layout : 'fit',
				width : 880,
				height :150,
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
				items : [ viewContactForm ]
			});

			// 展示新增窗口
			function addInit() {
				addContactWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editContactWindow.show();
			}
			
			//展示字典种类窗口
			function detailInit() {
				viewContactWindow.show();
			}

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				frame : true,
				autoScroll : true,
				region : 'center',
				store : store,
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

			// 布局模型
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ {
					layout:'border',
					items : [ qForm ,grid]
				}]
			});

		});