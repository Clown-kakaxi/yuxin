var activityId;
Ext.onReady(function() {
			//审批状态
			 var _TEMAPPStore = new Ext.data.ArrayStore( {
		         fields : [ 'key', 'value' ],
		         data : [ [ '2', '待审批' ], [ '3', '审批通过' ]]
		     });
			//活动状态
			var actiStatusStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=MACTI_STATUS'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			//活动类型
			var mktActiTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=ACTI_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
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
			
			var chanceTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=OPPOR_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
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
			,{name:'appReason',mapping:'APP_REASON'}
			,{name:'aendDate',mapping:'AEND_DATE'}
			,{name:'astartDate',mapping:'ASTART_DATE'}
			,{name:'createDate',mapping:'CREATE_DATE'}
			,{name:'createUser',mapping:'CREATE_USER'}
			,{name:'username',mapping:'USERNAME'}
			,{name:'checkUser',mapping:'CHECK_USER'}
			,{name:'actiCheckId',mapping:'ACTI_CHECK_ID'}
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
						//Width : '100',
						name : 'MKT_ACTI_NAME',
						fieldLabel : '营销活动名称',
						anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						store : _TEMAPPStore,
						xtype : 'combo', resizable : true,
						fieldLabel : '审批状态',
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
						//width : '100',
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
						var conditionStr = searchPanel.getForm().getValues(
								false);
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
			}/*,{
				header : '审批状态',
				width : 150,
				align : 'left',
				dataIndex : 'MKT_ACTI_STAT_ORA',
				sortable : true
			}*/,{
				header : '审批状态',
				width : 150,
				align : 'left',
				dataIndex : 'mktActiStat',
				sortable : true,
				renderer:function(value, p, r){
				if(value == '2')
					return '待审批';
				if(value == '3')
					return '审批通过';
					
				else
					return '未知状态';
				
			}
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
//				hidden : true,
				dataIndex : 'pendDate',
				sortable : true
			}, {
				header : '实际开始日期',
				width : 150,
				align : 'left',
//				hidden :true,
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
				align : 'left',
				dataIndex : 'mktActiCost',
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
			}, {
				header : '申请理由',
				width : 150,
				align : 'left',
				hidden:true,
				dataIndex : 'appReason',
				sortable : true
			}, {
				header : '审批单编号',
				width : 150,
				align : 'left',
				hidden:true,
				dataIndex : 'actiCheckId',
				sortable : true
			}]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/market-activity.json?appSign='+'appSign',
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

			var listPanel = new Ext.grid.GridPanel(
					{
						store : store,
						frame : true,
						cm : columns,
						sm : sm,
						stripeRows : true,
						tbar : [
								
								{
									text : '活动详情',
									iconCls:'detailIconCss ',
									handler : function() {
										var selectLength = listPanel.getSelectionModel().getSelections().length;
										
										var selectRe = listPanel.getSelectionModel().getSelections()[0];
			
										if (selectLength != 1) {
											Ext.Msg.alert("提示", "请选择一条记录!");
											return false;
										}
										
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
									text : '活动审批',
									iconCls:'shenpiIconCss ',
									handler : function() {
									var selectLength = listPanel.getSelectionModel().getSelections().length;
									var selectRe = listPanel.getSelectionModel().getSelections()[0];
									if (selectLength != 1) {
										Ext.Msg.alert("提示", "请选择一条记录!");
										return false;
									}if (selectRe.data.mktActiStat != 2) {
										Ext.Msg.alert("提示", "只能选择状态为[待审批]的记录!");
										return false;
									}if (selectRe.data.checkUser != __userId) {
										Ext.Msg.alert("提示", "该申请的审批人不是您，不能执行此操作!");
										return false;
									}else{
										approveActivityForm.getForm().loadRecord(selectRe);
										approveActivity();
									}	
									}
								},'-',{
									text : '审批历史',
									iconCls:'shenpiIconCss ',
									handler : function() {
									var selectLength = listPanel.getSelectionModel().getSelections().length;
									
									var selectRe = listPanel.getSelectionModel().getSelections()[0];

									if (selectLength != 1) {
										Ext.Msg.alert("提示", "请选择一条记录!");
										return false;
									}
									approveHistoryStore.proxy.setUrl(basepath+'/mktapphistoryqueryaction.json?mktId='+selectRe.data.mktActiId);
									debugger;
									approveHistoryStore.load();
									approveHistory();
									}
								}],
						region : 'center',
						frame : true,
						bbar : bbar
					});

			// 对营销活动审批的form
			var approveActivityForm = new Ext.form.FormPanel(
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
									anchor : '99%'
								},{
									xtype : 'textfield',
									labelStyle : 'text-align:right;',
									fieldLabel : '审批单编号',
									readOnly:true,
									hidden:true,
									name : 'actiCheckId',
									anchor : '99%'
								} ]
							},{
								columnWidth : .99,
								layout : 'form',
								items : [ {
									xtype : 'textarea',
									labelStyle : 'text-align:right;',
									fieldLabel : '申请理由',
									name : 'appReason',
									readOnly:true,
									anchor : '99%'
								} ]
							},{
								columnWidth : .99,
								layout : 'form',
								items : [ {
									xtype : 'textarea',
									labelStyle : 'text-align:right;',
									fieldLabel : '<span style="color:red">*</span>审批意见',
									allowBlank : false,
									name : 'checkIdea',
									anchor : '99%'
								} ]
							}]
						} ]

					});


//			 新增审批form
			var approvePanel = new Ext.Panel({
				width : 500,
				height : 240,
				layout : 'fit',
				items : [ approveActivityForm]
			});
			
			var approveActivityWindow = new Ext.Window({
				title : '活动审批',
				plain : true,
				layout : 'fit',
				width : 500,
				height : 250,
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
				items : [ approvePanel ],
				buttonAlign : "center",
				buttons : [ {
					text : '审批通过',
					handler : function() {
					if (!approveActivityForm.getForm().isValid()) {
		                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		                return false;
		            };
		            Ext.Ajax.request({
						url : basepath + '/addmarketprodaction!approve.json',
						params : {
						'mktActStr':approveActivityForm.form.findField('mktActiId').getValue(),
						'checkIdea':approveActivityForm.form.findField('checkIdea').getValue(),
						'sign':'approve',
						'actiCheckId':approveActivityForm.form.findField('actiCheckId').getValue()
						},
						method : 'POST',
						form : approveActivityForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							approveActivityWindow.hide();
							store.reload();
							Ext.Msg.alert('提示', '操作成功');
							approveActivityForm.form.reset();
							
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{
							  approve.hide();
							approveActivityForm.form.reset();
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
						}
					});
				}
				}, {
					text : '审批拒绝',
					handler : function() {
					if (!approveActivityForm.getForm().isValid()) {
		                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		                return false;
		            };
		            Ext.Ajax.request({
						url : basepath + '/addmarketprodaction!approve.json',
						params : {
						'mktActStr':approveActivityForm.form.findField('mktActiId').getValue(),
						'checkIdea':approveActivityForm.form.findField('checkIdea').getValue(),
						'sign':'refuse'
						},
						method : 'POST',
						form : approveActivityForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							approveActivityWindow.hide();
							store.reload();
							Ext.Msg.alert('提示', '操作成功');
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{
							approveActivityWindow.hide();
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
						}
					});
//				});
				}
				}, {
					text : '取  消',
					handler : function() {
					approveActivityWindow.hide();
					}
				} ]
			});
			 var approveHistoryRecord = Ext.data.Record.create(
			    		[
			    		 {name:'actiCheckId',mapping:'ACTI_CHECK_ID'},
			    		 {name:'checkDate',mapping:'CHECK_DATE'},
			    		 {name:'checkStatus',mapping:'CHECK_STATUS'},
			    		 {name:'checkIdea',mapping:'CHECK_IDEA'},
			    		 {name:'checkUserName',mapping:'CHECK_USER_NAME'},
			    		 {name:'appUserName',mapping:'APP_USER_NAME'},
			    		 {name:'appReason',mapping:'APP_REASON'},
			    		 {name:'checkUser',mapping:'CHECK_USER'},
			    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'}
			    		 ]
			    );
		   var approveHistoryReader = new Ext.data.JsonReader(//读取jsonReader
		    		{
		    			successProperty : 'success',
		    			idProperty : 'ID',
		    			totalProperty : 'json.count',
		    			root:'json.data'
		    		},approveHistoryRecord
			);
			var approveHistoryStore = new Ext.data.Store({//产品对照关系store
		        restful : true, 
		        proxy : new Ext.data.HttpProxy({ 
		        	url:basepath+'/mktapphistoryqueryaction.json'
		        }),
				reader:approveHistoryReader
				
		});
			 var approveHistoryColumns = new Ext.grid.ColumnModel(
						{
							columns:[
							{ header:'审批单编号',dataIndex:'actiCheckId',sortable:true,hidden:true,width:100},
							{ header:'申请人',dataIndex:'appUserName',sortable:true,width:100},
							{ header:'申请理由',dataIndex:'appReason',sortable:true,width:150},
							{ header:'审批人',dataIndex:'checkUserName',sortable:true,width:100},
							{ header:'审批状态',dataIndex:'checkStatus',width:80,sortable:true,renderer : function(value, p, r) {
								if (value == "2")
									return "<span style='color:blue;'>待审批</span>";
								else if (value == "1")
									return "<span style='color:green;'>审批通过</span>";
								else if (value == "0")
									return "<span style='color:red;'>审批拒绝</span>";
							}},
							{ header:'审批意见',dataIndex:'checkIdea',width:150,sortable:true},
							{ header:'审批时间',dataIndex:'checkDate',width:80,sortable:true}
							]
						}
			 );
			 var approveHistoryGrid = new Ext.grid.EditorGridPanel({			
					store:approveHistoryStore, 
					frame:true,
					height : 200,
					cm:approveHistoryColumns,
					region:'center',
					viewConfig : {
	 			   	},
	 			   	loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			   	}
			 });
			
			var approveHistoryWindow = new Ext.Window({
				title : '活动审批',
				plain : true,
				layout : 'fit',
				width : 700,
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
				items : [ approveHistoryGrid ],
				buttonAlign : "center",
				buttons : [{
					text : '关  闭',
					handler : function() {
					approveHistoryWindow.hide();
					}
				} ]
			});

			// 展示详情窗口
			function detailInit() {
				_buttonVisible = false;
				
				Ext.getCmp('jbxx').show();
				Ext.getCmp('glcpxx').show();
				Ext.getCmp('glkkxx').show();
				Ext.getCmp('glqdxx').show();
				Ext.getCmp('fjxx').show();
				Ext.getCmp('spxx').show();
				editPlanWindow.setTitle('营销活动详情');
				editPlanWindow.show();
			}
			
			// 审批的窗口
			function approveActivity() {
				approveActivityWindow.show();
			}
			// 审批的窗口
			function approveHistory() {
				approveHistoryWindow.show();
			}

			var view = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'border',
					items : [{
						region : 'center',
						id : 'center-panel',
						title : "营销活动审批列表",
						layout : 'fit',
						height : 105,
						items : [ listPanel ]
					},{
						region : 'north',
						id : 'north-panel',
						title : "营销活动审批查询",
						height : 130,
						layout : 'fit',
						items : [ searchPanel ]
					}	
					]
				}]
			});
		});