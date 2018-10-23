var activityId;
Ext.onReady(function() {
			var startData = new Date();
			var __appButton = true;
			if(__mktAppType=='01'){
				__appButton = false;
			}
			
			// 审批人信息
			var title_record = Ext.data.Record.create( [ {
				name : 'accountName',
				mapping : 'accountName'
			}, {
				name : 'userName',
				mapping : 'userName'
			}, {
				name : 'userState',
				mapping : 'userState'
			} ]);
			
			//营销活动审批审批人（可配置）
			var title_store = new Ext.data.Store( {
				restful : true,
				proxy : new Ext.data.HttpProxy( {
					url : basepath + '/watingappuserinfoaction!loadTitleRs.json?role='+'1234,admin'
				}),
				reader : new Ext.data.JsonReader( {
					successProperty : 'success',
		
					messageProperty : 'message',
					root : 'data',
					totalProperty : 'count'
				}, title_record)
			});
			title_store.load();
			var startData = new Date();
			
			//审批状态
			var approveStatStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=MACTI_APPROVE_STAT'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
			//营销活动类型
			var activityStore = new Ext.data.JsonStore({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/market-activity.json'
				}),
				fields : [ 'marketActivityId', 'marketActivityName' ],
				reader : new Ext.data.JsonReader({
					totalProperty : 'list'
				}, [ {
					name : 'marketActivityId',
					mapping : 'marketActivityId'
				}, {
					name : 'marketActivityName',
					mapping : 'marketActivityName'
				} ])
			});

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			//营销活动record
			var record = Ext.data.Record.create([  
			 {name:'mktActiId',mapping:'MKT_ACTI_ID'}	
			,{name:'actiCustDesc',mapping:'ACTI_CUST_DESC'}		
			,{name:'actiOperDesc',mapping:'ACTI_OPER_DESC'}		
			,{name:'actiProdDesc',mapping:'ACTI_PROD_DESC'}
			,{name:'actiRemark',mapping:'ACTI_REMARK'}
			,{name:'aendDate',mapping:'AEND_DATE'}
			,{name:'astartDate',mapping:'ASTART_DATE'}
			,{name:'createDate',mapping:'CREATE_DATE'}
			,{name:'createUser',mapping:'CREATE_USER'}
			,{name:'username',mapping:'USERNAME'}
			,{name:'checkUser',mapping:'CHECK_USER'}
			,{name:'mktActiAddr',mapping:'MKT_ACTI_ADDR'}
			,{name:'mktActiAim',mapping:'MKT_ACTI_AIM'}
			,{name:'mktActiCont',mapping:'MKT_ACTI_CONT'}
			,{name:'mktActiCost',mapping:'MKT_ACTI_COST'}
			,{name:'mktActiMode',mapping:'MKT_ACTI_MODE'}
			,{name:'mktActiName',mapping:'MKT_ACTI_NAME'}
			,{name:'mktActiStat',mapping:'MKT_ACTI_STAT'}
			,{name:'mktActiTeam',mapping:'MKT_ACTI_TEAM'}
			,{name:'mktActiType',mapping:'MKT_ACTI_TYPE'}
			,{name:'MKT_ACTI_STAT_ORA'}
			,{name:'pendDate',mapping:'PEND_DATE'}
			,{name:'pstartDate',mapping:'PSTART_DATE'}
			,{name:'updateDate',mapping:'UPDATE_DATE'}
			,{name:'updateUser',mapping:'UPDATE_USER'}]);
			
			//营销活动查询panel
			var searchPanel = new Ext.form.FormPanel({
				labelWidth : 100,
				labelAlign : 'right',
				height : 130,
				frame : true,
				region : 'north',
				autoScroll : true,
					layout : 'column',
					items : [   {
							columnWidth : .25,
							layout : 'form',
							items : [{
								fieldLabel : '计划开始时间从',
								xtype : 'datefield',
								id : 'PSTART_DATE_S',
								format : 'Y-m-d',
								editable : false,
								name : 'PSTART_DATE_S',
								anchor : '90%'
							}]
						},{
						    columnWidth : .25,
                            layout : 'form',
                            labelWidth: 20,
							items : [{
								xtype : 'datefield',
								format : 'Y-m-d',
								editable : false,
								fieldLabel : '至',
								name : 'PSTART_DATE_E',
								id : 'PSTART_DATE_E',
								anchor : '65%'
							}]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						name : 'MKT_ACTI_NAME',
						fieldLabel : '营销活动名称',
						anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						store : mactiStatusStore,
						xtype : 'combo', resizable : true,
						fieldLabel : '营销活动状态',
						name : 'MKT_ACTI_STAT',
						hiddenName : 'MKT_ACTI_STAT',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						editable :false,
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
                        selectOnFocus : true,
						anchor : '90%'
					} ]
				},  {
					columnWidth : .25,
					layout : 'form',
					items : [{
						fieldLabel : '计划结束时间从',
						xtype : 'datefield',
						id : 'PEND_DATE_S',
						format : 'Y-m-d',
						editable : false,
						name : 'PEND_DATE_S',
						anchor : '90%'
					}]
				},{
				    columnWidth : .25,
                    layout : 'form',
                    labelWidth: 20,
					items : [{
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						fieldLabel : '至',
						name : 'PEND_DATE_E',
						id : 'PEND_DATE_E',
						anchor : '65%'
					}]}],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
										var start = Ext.getCmp('PSTART_DATE_S').getValue();
										var end = Ext.getCmp('PSTART_DATE_E').getValue();
										var start1 = Ext.getCmp('PEND_DATE_S').getValue();
										var end1 = Ext.getCmp('PEND_DATE_E').getValue();
										if(start==''&&end!=''){
											Ext.Msg.alert('消息框','请先选择开始时间！');
											Ext.getCmp('PSTART_DATE_E').reset();
											return;
										}else if(end!=''&&start>end){
											Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
											Ext.getCmp('PSTART_DATE_E').reset();
											return;
										}
										if(start1==''&&end1!=''){
											Ext.Msg.alert('消息框','请先选择开始时间！');
											Ext.getCmp('PEND_DATE_E').reset();
											return;
										}else if(end1!=''&&start1>end1){
											Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
											Ext.getCmp('PEND_DATE_E').reset();
											return;
										}
						var conditionStr = searchPanel.getForm().getValues(false);
						store.on('beforeLoad', function() {
							this.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						});
						store.load({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
					}
				},{
					text : '重置',
					handler : function() {
						searchPanel.getForm().reset();
					}}
				]
			});
			
			var sm = new Ext.grid.CheckboxSelectionModel();
			var columns = new Ext.grid.ColumnModel([ rownum,sm,{
				header : '营销活动ID',
				hidden:true,
				width : 150,
				align : 'left',
				dataIndex : 'mktActiId',
				sortable : true
			}, {
				header : '营销活动名称',
				width : 150,
				align : 'left',
				dataIndex : 'mktActiName',
				sortable : true
			},{
				header : '营销活动状态',
				width : 150,
				align : 'left',
				dataIndex : 'MKT_ACTI_STAT_ORA',
				sortable : true
			},{
				header : '营销活动状态',
				width : 150,
				align : 'left',
				hidden:true,
				dataIndex : 'mktActiStat',
				sortable : true
			}, {
				header : '计划开始时间',
				width : 150,
				align : 'left',
				dataIndex : 'pstartDate',
				sortable : true
			}, {
				header : '计划结束时间',
				width : 150,
				align : 'left',
				dataIndex : 'pendDate',
				sortable : true
			}, {
				header : '实际开始日期',
				width : 150,
				align : 'left',
				dataIndex : 'astartDate',
				sortable : true
			},{
				header : '实际结束日期',
				width : 150,
				align : 'left',
				dataIndex : 'aendDate',
				sortable : true
			}, {
				header : '活动地点',
				width : 150,
				align : 'left',
				dataIndex : 'mktActiAddr',
				sortable : true
			}, {
				header : '费用预算',
				width : 150,
				align : 'right',
				dataIndex : 'mktActiCost',
				renderer: money('0,000.00'),
				sortable : true
			}, {
				header : '创建人',
				width : 150,
				align : 'left',
				dataIndex : 'username',
				sortable : true
			}, {
				header : '创建人编号',
				hidden:true,
				align : 'left',
				dataIndex : 'createUser',
				sortable : true
			}, {
				header : '创建日期',
				width : 150,
				align : 'left',
				dataIndex : 'createDate',
				sortable : true
			}, {
				header : '待审批人',
				width : 150,
				align : 'left',
				hidden:true,
				dataIndex : 'checkUser',
				sortable : true
			}]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/market-activity.json',
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'MKT_ACTI_ID',
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
				value : '20',
				resizable : true,
				width : 85
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
			
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
			
			//营销活动展示列表
			var listPanel = new Ext.grid.GridPanel(
					{
						store : store,
						frame : true,
						cm : columns,
						sm : sm,
						stripeRows : true,
						tbar : [
								{
									text : '创建活动',
									iconCls:'addIconCss',
									handler : function() {
										ifGroup = false;	
										ifProd = false;
									addActivityInit();
									}
								},
								'-',
								{
									text : '修改活动',
									iconCls:'editIconCss',
									handler : function() {
										
									var selectLength = listPanel.getSelectionModel().getSelections().length;
									var selectRe = listPanel.getSelectionModel().getSelections()[0];
									if (selectLength != 1) {
										Ext.Msg.alert("提示", "请选择一条记录!");
										return false;
									}
										var actiStatus = selectRe.data.mktActiStat;
										if (!((selectRe.data.mktActiStat =='1')||(selectRe.data.mktActiStat =='6'))) {
											Ext.Msg.alert("提示", "只能修改状态为[暂存]或[已退回]的营销活动!");
											return false;
										}else {
											editBasePlanForm.form.findField('mktActiName').setDisabled(false);
											editBasePlanForm.form.findField('mktActiCost').setDisabled(false);
											editBasePlanForm.form.findField('mktActiType').setDisabled(false);
											editBasePlanForm.form.findField('pstartDate').setDisabled(false);
											editBasePlanForm.form.findField('mktActiMode').setDisabled(false);
											editBasePlanForm.form.findField('pendDate').setDisabled(false);
											editBasePlanForm.form.findField('mktActiStat').setDisabled(false);
											editBasePlanForm.form.findField('mktActiAddr').setDisabled(false);
											editBasePlanForm.form.findField('mktActiCont').setDisabled(false);
											editBasePlanForm.form.findField('actiCustDesc').setDisabled(false);
											editBasePlanForm.form.findField('actiOperDesc').setDisabled(false);
											editBasePlanForm.form.findField('actiProdDesc').setDisabled(false);
											editBasePlanForm.form.findField('mktActiAim').setDisabled(false);
											editBasePlanForm.form.findField('actiRemark').setDisabled(false);
											
											editPlanWindow.show();
											editPlanWindow.hide();
											editBasePlanForm.getForm().loadRecord(selectRe);
											document.getElementById('marketActivityIdStr').value = selectRe.data.marketActivityId;
											editInit();
										}
									}
								},
								'-',
								{
									text : '删除活动',
									iconCls:'deleteIconCss',
									handler : function() 
									{
										 var selectLength = listPanel.getSelectionModel().getSelections().length;
										 var selectRe;
										 var tempId;
										 var idStr = '';
										 var actiStatus;
										if(selectLength < 1){
											Ext.Msg.alert('提示','请选择需要删除的记录!');
										} else {
											for(var i = 0; i<selectLength;i++)
											{
												selectRe = listPanel.getSelectionModel().getSelections()[i];
												actiStatus = selectRe.data.mktActiStat;
												if(actiStatus != '1'){
													Ext.Msg.alert('提示','只能删除[暂存]状态的营销活动!');
													return;
												}
												if(selectRe.data.createUser != __userId){
													Ext.Msg.alert('提示','只能删除自己创建的营销活动!');
													return;
												}
												tempId = selectRe.data.mktActiId;
												idStr += tempId;
												if( i != selectLength-1)
													idStr += ',';
											}
												Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
													if(buttonId.toLowerCase() == "no"){
   												return;
													} 
													Ext.Ajax.request({
																url : basepath
																+ '/market-activity!batchDestroy.json?idStr='+ idStr,
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success : function() {
																	Ext.Msg.alert('提示', '操作成功');
																	store.reload();
																},
																failure : function(response) {
																	var resultArray = Ext.util.JSON.decode(response.status);
																	if(resultArray == 403) {
																           Ext.Msg.alert('提示', response.responseText);
																  } else {
																	Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
																	store.reload();
																  }
																}
															});
												});
										}}
								},
								'-',
								{
									text : '活动详情',
									iconCls:'detailIconCss ',
									handler : function() {
										var selectLength = listPanel.getSelectionModel().getSelections().length;
										var selectRe = listPanel.getSelectionModel().getSelections()[0];
										if (selectLength != 1) {
											Ext.Msg.alert("提示", "请选择一条记录!");
											return false;
										};
										editBasePlanForm.form.findField('mktActiName').setDisabled(true);
										editBasePlanForm.form.findField('mktActiCost').setDisabled(true);
										editBasePlanForm.form.findField('mktActiType').setDisabled(true);
										editBasePlanForm.form.findField('pstartDate').setDisabled(true);
										editBasePlanForm.form.findField('mktActiMode').setDisabled(true);
										editBasePlanForm.form.findField('pendDate').setDisabled(true);
										editBasePlanForm.form.findField('mktActiStat').setDisabled(true);
										editBasePlanForm.form.findField('mktActiAddr').setDisabled(true);
										editBasePlanForm.form.findField('mktActiCont').setDisabled(true);
										editBasePlanForm.form.findField('actiCustDesc').setDisabled(true);
										editBasePlanForm.form.findField('actiOperDesc').setDisabled(true);
										editBasePlanForm.form.findField('actiProdDesc').setDisabled(true);
										editBasePlanForm.form.findField('mktActiAim').setDisabled(true);
										editBasePlanForm.form.findField('actiRemark').setDisabled(true);
										
											var actiStatus = selectRe.data.mktActiStat;
												editPlanWindow.show();
												editPlanWindow.hide();
												editBasePlanForm.getForm().loadRecord(selectRe);
												document.getElementById('marketActivityIdStr').value = selectRe.data.marketActivityId;
												detailInit();
										}
								},'-',{
									text : '执行活动',
									iconCls:'maintainIconCss',
									hidden:__appButton,
									handler : function() {
									zhixingFucntion();
								}
								},{

									text : '活动执行申请',
									hidden:!__appButton,
									iconCls:'shenpiIconCss ',
									handler : function() {
									if(__roles.substring(0,__roles.length-1)=='126'){//在判定如果操作人为 126角色，则在执行提交申请时，视作执行操作，无需申请
										zhixingFucntion();
									}else{
									tijiaoshenpingFunction();
									}
								}
								},'-',
								//YUYZ 德阳POC DEMO
								{
									text : '通过渠道批量执行',
									iconCls:'maintainIconCss',
									handler : function() {
									var selectLength = listPanel.getSelectionModel().getSelections().length;
									 var selectRe;
									 var tempId;
									 var idStr = '';
									 var actiStatus;
									if(selectLength < 1){
										Ext.Msg.alert('提示','请选择需要执行的记录!');
									} 
									for(var i = 0; i<selectLength;i++)
									{
										selectRe = listPanel.getSelectionModel().getSelections()[i];
										actiStatus = selectRe.data.mktActiStat;
										if(actiStatus != '1'){
											Ext.Msg.alert('提示','只能执行[暂存]状态的营销活动!');
											return;
										}
										if(selectRe.data.createUser != __userId){
											Ext.Msg.alert('提示','只能执行自己创建的营销活动!');
											return;
										}
										tempId = selectRe.data.mktActiId;
										idStr += tempId;
										if( i != selectLength-1)
											idStr += ',';
									}
										  Ext.Msg.show({
										  title: '按钮操作',
										  msg: '您确定要执行该操作吗?',
										  buttons: {
										  yes: true,
										  no: true,
										  cancel: true
										  },
										  icon: 'kakaxiIconCss',
										  fn: function(btn) {
										  	if(btn=='yes'){
										  		Ext.Msg.alert('系统提示','您执行了该操作!');
										  	}
										  	if(btn=='no'){
										  		Ext.Msg.alert('系统提示','您终止了该操作!');
										  	}
										  	if(btn=='cancel'){
										  		Ext.Msg.alert('系统提示','您取消了该操作!');
										  	}
										  }
										  });
									
//									Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
//										if(buttonId.toLowerCase() == "no"){
//										return;
//										} 
//										alert("执行成功，已经通过渠道向所有客户发送消息 ");
//									})
									;
								}
								},
								//END
								{
									text : '关闭',
									iconCls:'closeIconCss',
									handler : function() {
										closeActivity();
									}
								}],
						region : 'center',
						frame : true,
						bbar : bbar
					});
			
				//执行营销活动
				function zhixingFucntion(){
				var selectLength = listPanel.getSelectionModel().getSelections().length;
				 var selectRe;
				 var tempId;
				 var idStr = '';
					if (selectLength < 1) {
						Ext.Msg.alert('提示','请选择要执行的记录!');
					} else {
							for(var i = 0; i<selectLength;i++)
							{
								selectRe = listPanel.getSelectionModel().getSelections()[i];
								var actiStatus = selectRe.data.mktActiStat;
								if(selectRe.data.createUser != __userId){
									Ext.Msg.alert('提示','只能执行自己创建的营销活动!');
									return;
								}
								if (!((actiStatus=='1')||(actiStatus=='6'))) {
									Ext.Msg.alert('提示', '该活动已经在执行中或是关闭状态!');
									return false;
								}
								tempId = selectRe.data.mktActiId;
								idStr += tempId;
								if( i != selectLength-1)
									idStr += ',';
							}
							Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
								if(buttonId.toLowerCase() == "no"){
										return;
									} 
								Ext.Ajax.request({
								url : basepath+ '/market-activity!activityExecute.json',
								params : {
								'idStr':idStr,
								'sign':'execute'
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									 if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
								  } else {
									Ext.Msg.alert('提示', '操作失败');
								  }
								}
							});
							});
						}
						};
						//营销活动提交申请
						function tijiaoshenpingFunction(){
						var selectLength = listPanel.getSelectionModel().getSelections().length;
						var selectRe = listPanel.getSelectionModel().getSelections()[0];
						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
							return false;
						}if (!((selectRe.data.mktActiStat == '6')||(selectRe.data.mktActiStat == '1'))) {
							Ext.Msg.alert("提示", "只能选择状态为[暂存]或[已退回]的记录!");
							return false;
						}else{
							applyActivityForm.getForm().loadRecord(selectRe);
							applyActivity();
						}	
					};
			
//			// 关闭营销活动
			function closeActivity() {
				var selectLength = listPanel.getSelectionModel().getSelections().length;
				 var selectRe;
				 var tempId;
				 var idStr = '';
					if (selectLength < 1) {
						Ext.Msg.alert('提示','请选择要执行的记录!');
					} else {
							for(var i = 0; i<selectLength;i++)
							{
								selectRe = listPanel.getSelectionModel().getSelections()[i];
								actiStatus = selectRe.data.mktActiStat;
								if(actiStatus != '3'){
									Ext.Msg.alert('提示','只能关闭状态为执行中的营销活动!');
									return;
								}
								if(selectRe.data.createUser != __userId){
									Ext.Msg.alert('提示','只能关闭自己创建的营销活动!');
									return;
								}
								selectRe = listPanel.getSelectionModel().getSelections()[i];
								var actiStatus = selectRe.data.mktActiStat;
								tempId = selectRe.data.mktActiId;
								idStr += tempId;
								if( i != selectLength-1)
									idStr += ',';
							}
							Ext.MessageBox.confirm('提示','确定要关闭吗?',function(buttonId){
								if(buttonId.toLowerCase() == "no"){
										return;
									} 
								Ext.Ajax.request({
											url : basepath+ '/market-activity!activityExecute.json',
											params : {
											'idStr':idStr,
											'sign':'close'
											},
											waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : function() {
												Ext.Msg.alert('提示', '操作成功');
												store.reload();
											},
											failure : function(response) {
												var resultArray = Ext.util.JSON.decode(response.status);
												 if(resultArray == 403) {
											           Ext.Msg.alert('提示', response.responseText);
											  } else {
												Ext.Msg.alert('提示', '操作失败');
											  }
											}
										});
							});
					}
				};
			
			var rd_set = new Ext.form.FieldSet( {
				xtype : 'fieldset',
				title : '审批人',
				columnWidth : .99,
				layout : 'form',
				labelAlign : 'right',
				collapsible : false,
				itemCls: 'x-check-group-alt',
				buttonAlign : "left",
				items : []
				
			});
			// 营销活动申请展示的form
			var applyActivityForm = new Ext.form.FormPanel(
					{
						labelWidth : 80,
						height : 100,
						frame : true,
						region : 'center',
						autoScroll : true,
						buttonAlign : "center",
						items : [{
							layout : 'column',
							items : [ {
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									labelStyle : 'text-align:right;',
									fieldLabel : '活动编号',
									allowBlank : false,
									readOnly:true,
									name : 'mktActiId',
									anchor : '99%'
								} ]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									labelStyle : 'text-align:right;',
									fieldLabel : '活动名称',
									readOnly:true,
									name : 'mktActiName',
									anchor : '90%'
								} ]
							},{
								columnWidth : .99,
								layout : 'form',
								items : [ {
									xtype : 'textarea',
									labelStyle : 'text-align:right;',
									fieldLabel : '<span style="color:red">*</span>申请理由',
									allowBlank : false,
									name : 'appReason',
									anchor : '95%'
								} ]
							},rd_set]
						} ],
						listeners : {
							beforerender : function() {
								var title_count = null;
								var title = null;
								var title_rs = null;
								var rs = null;
								title_count = title_store.getCount();
									for ( var b = 0; b < title_count; b++) {
										title = title_store.getAt(b);
										title_rs = new Array();
										rs = title.json.accountName;
										title_rs.push({
											name:'para1',
											boxLabel: title.json.userName, 
											inputValue :title.json.accountName
												});
										new Ext.form.RadioGroup({
											id : 'user' + b,
											name : title.json.accountName,
											items : [ title_rs ]
										});								
										rd_set.add(Ext.getCmp('user' + b));
										rd_set.doLayout();
									}
								}
						}
					});
			
//			 新增审批form
			var applyPanel = new Ext.Panel({
				width : 690,
				height : 349,
				layout : 'fit',
				items : [ applyActivityForm]
			});

			// 定义审批窗口
			var applyActivityWindow = new Ext.Window({
				title : '活动申请',
				plain : true,
				layout : 'fit',
				width : 700,
				height : 350,
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
				items : [ applyPanel ],
				buttonAlign : "center",
				buttons : [ {
					text : '提交申请',
					handler : function() {
					if (!applyActivityForm.getForm().isValid()) {
		                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		                return false;
		            };
		            var title_count = title_store.getCount();
		            var user_id=null;
		            for(var b = 0;b<title_count;b++){
		            if(Ext.getCmp('user'+b).getValue()!=null){
		            	user_id=Ext.getCmp('user'+b).getValue().inputValue;
		            }	
		            }
		            if(user_id==null){
		            	 Ext.MessageBox.alert('提示','请指定审批人');
		            	 return false;
		            }
		            Ext.Ajax.request({
						url : basepath + '/addmarketprodaction!approve.json',
						params : {
						'mktActStr':applyActivityForm.form.findField('mktActiId').getValue(),
						'appReason':applyActivityForm.form.findField('appReason').getValue(),
						'approveUser':user_id,
						'sign':'apply'
						},
						method : 'POST',
						form : applyActivityForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							applyActivityWindow.hide();
							store.reload();
							Ext.Msg.alert('提示', '操作成功');
							applyActivityForm.form.reset();
							
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{
							  applyActivityWindow.hide();
							applyActivityForm.form.reset();
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
						}
					});
				}
				}, {
					text : '取  消',
					handler : function() {
					applyActivityWindow.hide();
					}
				} ]
			});
			
			// 展示新增的窗口
			function addActivityInit() {
				_buttonVisible = true;
				_sheetVisible = false;
				editBasePlanForm.form.reset();
				
				editBasePlanForm.form.findField('mktActiName').setDisabled(false);
				editBasePlanForm.form.findField('mktActiCost').setDisabled(false);
				editBasePlanForm.form.findField('mktActiType').setDisabled(false);
				editBasePlanForm.form.findField('pstartDate').setDisabled(false);
				editBasePlanForm.form.findField('mktActiMode').setDisabled(false);
				editBasePlanForm.form.findField('pendDate').setDisabled(false);
				editBasePlanForm.form.findField('mktActiStat').setDisabled(false);
				editBasePlanForm.form.findField('mktActiAddr').setDisabled(false);
				editBasePlanForm.form.findField('mktActiCont').setDisabled(false);
				editBasePlanForm.form.findField('actiCustDesc').setDisabled(false);
				editBasePlanForm.form.findField('actiOperDesc').setDisabled(false);
				editBasePlanForm.form.findField('actiProdDesc').setDisabled(false);
				editBasePlanForm.form.findField('mktActiAim').setDisabled(false);
				editBasePlanForm.form.findField('actiRemark').setDisabled(false);
				
				editPlanWindow.setTitle('营销活动新增');
				editBasePlanForm.form.findField('createUser').setValue(__userId);
				editBasePlanForm.form.findField('createDate').setValue(startData);
				editBasePlanForm.form.findField('updateUser').setValue(__userId);
				editBasePlanForm.form.findField('updateDate').setValue(startData);
				editBasePlanForm.form.findField('mktActiStat').setValue('1');
				editBasePlanForm.form.findField('createOrg').setValue(__units);
				
				Ext.getCmp('jbxx').show();
				Ext.getCmp('glcpxx').hide();
				Ext.getCmp('glkkxx').hide();
				Ext.getCmp('glqdxx').hide();
				Ext.getCmp('fjxx').hide();
				Ext.getCmp('spxx').hide();
				editPlanWindow.show();
			};
			
			// 展示修改窗口
			function editInit() {
				_buttonVisible = true;
				_sheetVisible = true;
				Ext.getCmp('jbxx').show();
				Ext.getCmp('glcpxx').show();
				Ext.getCmp('glkkxx').show();
				Ext.getCmp('glqdxx').show();
				Ext.getCmp('fjxx').show();
				Ext.getCmp('spxx').hide();
				editPlanWindow.setTitle('营销活动修改');
				editBasePlanForm.form.findField('updateUser').setValue(__userId);
				editBasePlanForm.form.findField('updateDate').setValue(startData);
				editPlanWindow.show();
			}

			// 展示详情窗口
			function detailInit() {
				_buttonVisible = false;
				_sheetVisible = true;
				Ext.getCmp('jbxx').show();
				Ext.getCmp('glcpxx').show();
				Ext.getCmp('glkkxx').show();
				Ext.getCmp('glqdxx').show();
				Ext.getCmp('fjxx').show();
				if(__appButton){
					Ext.getCmp('spxx').show();	
				}else{
					Ext.getCmp('spxx').hide();
				}
				editPlanWindow.setTitle('营销活动详情');
				editPlanWindow.show();
			}
			// 申请的窗口
			function applyActivity() {
				applyActivityWindow.show();
			}
			editPlanWindow.addListener('hide',function(){
				store.reload();
			});

			var view = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'border',
					items : [{
						region : 'center',
						id : 'center-panel',
						title : "营销活动列表",
						layout : 'fit',
						height : 105,
						items : [ listPanel ]
					},{
						region : 'north',
						id : 'north-panel',
						title : "营销活动查询",
						height : 130,
						layout : 'fit',
						items : [ searchPanel ]
					}]
				}]
			});
		});