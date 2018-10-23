Ext.onReady(function() {
			var searchPanel = new Ext.form.FormPanel({
				labelWidth : 105,
				labelAlign : 'right',
				height : 100,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .2,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '机构号',
									id : 'INSTN_NO',
									name : 'INSTN_NO',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .2,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户经理ID',
									id : 'USER_ID',
									name : 'USER_ID',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .2,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户经理姓名',
									id : 'USER_NAME',
									name : 'USER_NAME',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .2,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户名称',
									id : 'CUST_NAME',
									name : 'MANAGER_LEVEL',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .2,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '组织机构代码',
									id : 'CUST_ZZDM',
									name : 'CUST_ZZDM',
									anchor : '90%'
								} ]
							}
							 ]
				}],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = searchPanel.getForm().getValues(
								false);
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
							searchPanel.getForm().reset();
						}
					} ]

			});

			// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			//列模型
			var columns = new Ext.grid.ColumnModel([ rownum,sm,{
				header : '机构号',
				dataIndex : 'INSTN_NO', 
				sortable : true,
				width : 120
			},{
				header : '客户经理姓名', 
				dataIndex : 'USER_NAME', 
				sortable : true,
				width : 120
			},{
				header : '客户经理编号',
				dataIndex : 'USER_ID', 
				sortable : true,
				width : 120
			},{
				header : '统计日期',
				dataIndex : 'CRM_DT', 
				sortable : true,
				width : 120
			},{
				header : '业务类型', // 列标题
				dataIndex : 'STYP', 
				sortable : true,
				width : 120
			},{
				header : '组织机构代码', 
				dataIndex : 'CUST_ZZDM', 
				sortable : true,
				width : 120
			},{
				header : '关联账号', 
				dataIndex : 'RELA_ACCT', 
				sortable : true,
				width : 120
			},{
				header : '关联占比', 
				dataIndex : 'RELA_PCT', 
				sortable : true,
				width : 120,
				align : 'right',
				renderer: percent(false)
			},{
				header : '关联日期', 
				dataIndex : 'RELA_DT', 
				sortable : true,
				width : 120
			},{
				header : '时点余额', 
				dataIndex : 'BAL', 
				sortable : true,
				width : 120,
				align : 'right',
				renderer: money('0,000' )
			},{
				header : '月均余额', 
				dataIndex : 'MON_AVG', 
				sortable : true,
				width : 120,
				align : 'right',
				renderer: money('0,000' )
			},{
				header : '季均余额', 
				dataIndex : 'QUAR_AVG', 
				sortable : true,
				width : 120,
				align : 'right',
				renderer: money('0,000' )
			},{
				header : '年均余额', 
				dataIndex : 'YEAR_AVG', 
				sortable : true,
				width : 120,
				align : 'right',
				renderer: money('0,000' )
			},{
				header : '上年末11天余额', 
				dataIndex : 'YEAR_DAY11', 
				sortable : true,
				width : 120,
				align : 'right',
				renderer: money('0,000' )
			}]);

			var record = Ext.data.Record.create([
	     {name: 'ID', mapping: 'id'},
         {name: 'INSTN_NO', mapping: 'instn_no'},
         {name: 'CRM_DT', mapping: 'crm_dt'},                              
         {name: 'USER_ID', mapping: 'user_id'},  
         {name: 'USER_NAME', mapping: 'user_name'},
         {name: 'STYP', mapping: 'styp'},
         {name: 'CUST_NAME', mapping: 'cust_name'},
         {name: 'CUST_ZZDM', mapping: 'cust_zzdm'},
         {name: 'RELA_ACCT', mapping: 'rela_acct'},
         {name: 'RELA_PCT', mapping: 'rela_pct'},
         {name: 'RELA_DT', mapping: 'rela_dt'},
         {name: 'BAL', mapping: 'bal'},
         {name: 'MON_AVG', mapping: 'mon_avg'},
         {name: 'QUAR_AVG', mapping: 'quar_avg'},
         {name: 'YEAR_AVG', mapping: 'year_avg'},
         {name: 'YEAR_DAY11', mapping: 'year_day11'}
		 ]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/performquery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'INSTN_NO',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
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
				value : '10',
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

			var listPanel = new Ext.grid.GridPanel(
					{
						store : store,
						frame : true,
						sm : sm,
						cm : columns,
						stripeRows : true,/*,
						tbar : [
								{
									text : '新增',
									iconCls : 'page_addIcon',
									handler : function() {
										addInit();
									}
								},
								'-',
								{
									text : '修改',
									iconCls : 'page_editIcon',
									handler : function() {

										var selectLength = listPanel
												.getSelectionModel()
												.getSelections().length;

										var selectRe = listPanel
												.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											alert('请选择一条记录');
										} else {
												editBasePlanForm.getForm().loadRecord(selectRe);
												document.getElementById('idStr').value = selectRe.data.id;
												editInit();
											
										}
									}

								},
								'-',
								{
									text : '删除',
									iconCls : 'page_delIcon',
									handler : function() {
										 var selectLength = listPanel.getSelectionModel()
											.getSelections().length;
										
										if (selectLength < 1) {
											alert('请选择一条记录');
										} else {
												if (confirm("确定删除吗?")) 
												{
													var selectRe;
													var tempId;
													var idStr = '';
													for(var i = 0;i<selectLength;i++){
													 selectRe = listPanel.getSelectionModel().getSelections()[i];
													 tempId = selectRe.data.id;
													 idStr += tempId;
													 if( i != selectLength-1)
															idStr += ',';
													 }
													Ext.Ajax
															.request({
																url : basepath
																		+ '/CustomerManagerInfoAction/'
										                                +tempId+'?idStr='+idStr,
																method : 'DELETE',
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success : function() {
																	alert("操作成功");
																	store
																			.reload();
																},
																failure : function() {
																	alert("操作成功");
																	store
																			.reload();
																}
															});

												};
											
										}
									}
								},
								'-',
								{
									text : '查看详细信息',
									iconCls : 'page_editIcon',
									handler : function() {
										// 得到选中记录
										var selectRe = listPanel.getSelectionModel()
												.getSelections()[0];
								        
										if (selectRe == null || selectRe == "undefined") {
											alert('请选择一条记录');
										} else {					
												showChanceForm.getForm().loadRecord(selectRe);
												showInit();
											}
										}							
								}],*/
						region : 'center',
						frame : true,
						bbar : bbar,// 分页工具栏
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});

			// 新增窗口展示的from
			var addPlanForm = new Ext.form.FormPanel({
				labelWidth : 80,
				height : 420,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理姓名',
										name : 'managerName',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理类型',
										name : 'managerType',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '职种',
										name : 'occupation',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '归属机构ID',
										name : 'orgnizationId',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '政治面貌',
										name : 'politicalLandscape',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '职务',
										name : 'position',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'combo',
										width : 200,
										fieldLabel : '性别',
										forceSelection:true,
										triggerAction:'all',
										mode:'local',
										store:new Ext.data.ArrayStore({
											fields:['myId','displayText'],
											data:[[0,'请选择'],[1,'男'],[2,'女']]
										}),
	                                    valueField:'myId',
	                                    displayField:'displayText',
	                                    name : 'sex',
	                                    anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '联系方式',
										name : 'contactWay',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'combo',
										width : 200,
										fieldLabel : '是否客户经理',
										forceSelection:true,
										triggerAction:'all',
										mode:'local',
										store:new Ext.data.ArrayStore({
											fields:['myId','displayText'],
											data:[[0,'请选择'],[1,'是'],[2,'否']]
										}),
	                                    valueField:'myId',
	                                    displayField:'displayText',
	                                    name : 'isSmeManager',
	                                    anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '经济工作年限',
										name : 'ecoWorkLife',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '学历',
										name : 'enducation',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'datefield',
										width : 200,
										fieldLabel : '入行日期',
										format : 'Y-m-d',
										name : 'entryTime',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'datefield',
										width : 200,
										fieldLabel : '毕业时间',
										format : 'Y-m-d',
										name : 'graduationTime',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'combo',
										width : 200,
										fieldLabel : '是否有信贷证',
										forceSelection:true,
										triggerAction:'all',
										mode:'local',
										store:new Ext.data.ArrayStore({
											fields:['myId','displayText'],
											data:[[0,'请选择'],[1,'有'],[2,'无']]
										}),
	                                    valueField:'myId',
	                                    displayField:'displayText',
	                                    name : 'isHavingCreditCard',
	                                    anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '岗位',
										name : 'job',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理岗位年限',
										name : 'jobLife',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '等级档次',
										name : 'level',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理编号',
										name : 'managerCode',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理等级',
										name : 'managerLevel',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '状态',
										name : 'status',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '工作单位',
										name : 'workUnit',
										anchor : '90%'
									}
								    ]
							}]}],buttons : [

											{

												text : '保  存',
												handler : function() {
													Ext.Ajax.request({

														url : basepath + '/CustomerManagerInfoAction.json',
														method : 'POST',
														form : addPlanForm.getForm().id,
														waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
														success : function(response) {
															var status = response.responseText;
															if ( status == null || status == '')
															{
															alert("操作成功");
															store.reload();
															}else{
																alert("操作失败"+"["+status+"]");
															}
														},
														failure : function() {
															alert("操作失败");
														}
													})
													addPlanWindow.hide();
												}

											}, {
												text : '取  消',
												handler : function() {
													addPlanWindow.hide();
												}
											} ]

			});

			// 修改基本信息展示的form
			var editBasePlanForm = new Ext.form.FormPanel({
				labelWidth : 80,
				height : 300,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理姓名',
										name : 'managerName',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理类型',
										name : 'managerType',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '职种',
										name : 'occupation',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '归属机构ID',
										name : 'orgnizationId',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '政治面貌',
										name : 'politicalLandscape',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '职务',
										name : 'position',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'combo',
										width : 200,
										fieldLabel : '性别',
										forceSelection:true,
										triggerAction:'all',
										mode:'local',
										store:new Ext.data.ArrayStore({
											fields:['myId','displayText'],
											data:[[0,'请选择'],[1,'男'],[2,'女']]
										}),
	                                    valueField:'myId',
	                                    displayField:'displayText',
	                                    name : 'sex',
	                                    anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '联系方式',
										name : 'contactWay',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'combo',
										width : 200,
										fieldLabel : '是否客户经理',
										forceSelection:true,
										triggerAction:'all',
										mode:'local',
										store:new Ext.data.ArrayStore({
											fields:['myId','displayText'],
											data:[[0,'请选择'],[1,'是'],[2,'否']]
										}),
	                                    valueField:'myId',
	                                    displayField:'displayText',
	                                    name : 'isSmeManager',
	                                    anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '经济工作年限',
										name : 'ecoWorkLife',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '学历',
										name : 'enducation',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'datefield',
										width : 200,
										fieldLabel : '入行日期',
										format : 'Y-m-d',
										name : 'entryTime',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'datefield',
										width : 200,
										fieldLabel : '毕业时间',
										format : 'Y-m-d',
										name : 'graduationTime',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'combo',
										width : 200,
										fieldLabel : '是否有信贷证',
										forceSelection:true,
										triggerAction:'all',
										mode:'local',
										store:new Ext.data.ArrayStore({
											fields:['myId','displayText'],
											data:[[0,'请选择'],[1,'有'],[2,'无']]
										}),
	                                    valueField:'myId',
	                                    displayField:'displayText',
	                                    name : 'isHavingCreditCard',
	                                    anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '岗位',
										name : 'job',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理岗位年限',
										name : 'jobLife',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '等级档次',
										name : 'level',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理编号',
										name : 'managerCode',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '客户经理等级',
										name : 'managerLevel',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '状态',
										name : 'status',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										fieldLabel : '工作单位',
										name : 'workUnit',
										anchor : '90%'
									}
								    ]
							},{
								//隐藏字段ID
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'hidden',
										width : 200,
										fieldLabel : 'ID',
										name : 'id',
										anchor : '90%'
									}
								    ]
							}]}],buttons : [

											{

												text : '保  存',
												handler : function() {
													debugger;
													Ext.Ajax.request({

														url : basepath + '/CustomerManagerInfoAction.json',
														method : 'POST',
														form : editBasePlanForm.getForm().id,
														waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
														success : function() {
															alert("操作成功");
															store.reload();
														},
														failure : function() {
															alert("操作失败");
														}
													})
													editPlanWindow.hide();
												}

											}, {
												text : '取  消',
												handler : function() {
													editPlanWindow.hide();
												}
											} ]

			});

//			// 定义修改窗口的tabPanel
//			var tokenDelimiter = ':';
//			var editTp = new Ext.TabPanel({
//				id : 'editPlanTabs',
//				activeTab : 0,
//				tabPosition : 'bottom',
//				items : [ {
//					title : '基本信息',
//					items : [ editBasePlanForm ]
//				}]
//
//			});

			// 展示详细信息窗口展示的from
			var showChanceForm = new Ext.form.FormPanel({
				labelWidth : 40,
				height : 250,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '用户ID',
										name : 'id',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '客户经理姓名',
										name : 'managerName',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '客户经理类型',
										name : 'managerType',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '职种',
										name : 'occupation',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '归属机构ID',
										name : 'orgnizationId',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '政治面貌',
										name : 'politicalLandscape',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '职务',
										name : 'position',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '性别',
										name : 'sex',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '联系方式',
										name : 'contactWay',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 100,
										readOnly:true,
										fieldLabel : '是否客户经理',
										name : 'isSmeManager',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '经济工作年限',
										name : 'ecoWorkLife',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '学历',
										name : 'enducation',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '入行日期',
										name : 'entryTime',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '毕业时间',
										name : 'graduationTime',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '是否有信贷证',
										name : 'isHavingCreditCard',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '岗位',
										name : 'job',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '客户经理岗位年限',
										name : 'jobLife',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '等级档次',
										name : 'level',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '客户经理编号',
										name : 'managerCode',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '客户经理等级',
										name : 'managerLevel',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '状态',
										name : 'status',
										anchor : '90%'
									}
								    ]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{
										xtype : 'textfield',
										width : 200,
										readOnly:true,
										fieldLabel : '工作单位',
										name : 'workUnit',
										anchor : '90%'
									}
								    ]
							}]}]
			});
			

			// 修改窗口展示的from
			var editPlanPanel = new Ext.Panel({
				labelWidth : 80,
				height : 300,
				layout : 'fit',
				autoScroll : true,
				buttonAlign : "center",
				items : [ editBasePlanForm ]
			});

			// 定义新增窗口
			var addPlanWindow = new Ext.Window({
				title : '客户经理信息新增',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 300,
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
				items : [ addPlanForm ]
			});

			// 定义修改窗口
			var editPlanWindow = new Ext.Window({
				title : '客户经理信息修改',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 300,
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
				items : [ editPlanPanel ]
			});

			// 定义详细信息窗口
			var showChanceWindow = new Ext.Window({
						title : '客户经理详细信息',
						plain : true,
						layout : 'fit',
						width : 880,
						height : 300,
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
						constrain:true,
						animCollapse:true,
						buttonAlign : 'center',
						items : [showChanceForm],
						buttons : [
									 {
										text : '关闭',
									
										handler : function() {
											showChanceWindow.hide();
										}
									} ]
					});
			
			// 展示新增窗口
			function addInit() {
				addPlanWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editPlanWindow.show();
			}
			
			// 展示详细信息窗口
			function showInit() {
				showChanceWindow.show();
			}
		
			var view = new Ext.Viewport({

				layout : 'border',
				items : [ {
					region : 'center',
					id : 'center-panel',
					title : "客户经理信息表",
					layout : 'fit',
					items : [ listPanel ]
				},

				{
					region : 'north',
					id : 'north-panel',
					title : "客户经理信息查询",
					height : 125,
					layout : 'fit',
					items : [ searchPanel ]
				}

				]
			});

		})