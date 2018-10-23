var activityId;

Ext.onReady(function() {
	debugger;
	 var myData = [
['信用卡推销','暂存','2013-2-19','2013-2-27','','','南京江宁经济开发区 ','10000','秦青','2013-2-19'],	
['助贷业务推广','暂存','2013-2-19','2013-2-27','','','南京市溧水县柘塘工业园 ','20000','秦青','2013-2-19'],
['信用卡推销','到期关闭','2013-2-19','2013-2-28','2013-2-20','2013-2-28','南京江宁经济开发区 ','30000','秦青','2013-2-19'],
['个贷业务','执行中','2013-2-17','2013-2-27','2013-2-17','','南京市雨花台区','10000','秦青','2013-2-19'],
['小企业扶持贷款','正常关闭','2013-2-19','2013-2-27','2013-2-19','2013-2-28','南京市雨花台区','20000','秦青','2013-2-19'],
['个贷推广','已提交','2013-2-19','2013-2-28','2013-2-19','','南京市 大光路','30000','秦青','2013-2-19'],
['助贷业务推广','已退回','2013-2-16','2013-2-27','2013-2-16','','南京市溧水县柘塘工业园 ','10000','秦青','2013-2-19']
];
			var startData = new Date();
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
				header : 'NO',
				width : 28
			});
			
			
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
						store : actiStatusStore,
						xtype : 'combo', resizable : true,
						fieldLabel : '营销活动状态',
						name : 'mktActiType',
						hiddenName : 'mktActiType',
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

					}
				},{
					text : '重置',
					handler : function() {
						searchPanel.getForm().reset();
					}}
				]

			});
			
			var store = new Ext.data.ArrayStore({
	               fields: [
			                  {name: 'mktActiName'},
			                  {name: 'mktActiStat'},
			                  {name: 'pstartDate'},
			                  {name: 'pendDate'},
			                  {name: 'astartDate'},
			                  {name: 'aendDate'},
			                  {name: 'mktActiAddr'},
			                  {name: 'mktActiCost'},
			                  {name: 'createUser'},
			                  {name: 'createDate'}
			               ]
			           });
			
			
			store.loadData(myData);
			

			
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

			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			
//			store.load({
//				params : {
//					start : 0,
//					limit : bbar.pageSize
//				}
//			});
			var listPanel = new Ext.grid.GridPanel(
					{
						store : store,
						frame : true,
						columns : [ {
							header : '营销活动名称',
							width : 150,
							dataIndex : 'mktActiName'
						},{
							header : '营销活动状态',
							width : 150,
							dataIndex : 'mktActiStat'
						}, {
							header : '计划开始时间',
							width : 150,
							dataIndex : 'pstartDate'
						}, {
							header : '计划结束时间',
							width : 150,
							dataIndex : 'pendDate'
						}, {
							header : '实际开始日期',
							width : 150,
							dataIndex : 'astartDate'
						},{
							header : '实际结束日期',
							width : 150,
							dataIndex : 'aendDate'
						}, {
							header : '活动地点',
							width : 150,
							dataIndex : 'mktActiAddr'
						}, {
							header : '费用预算',
							width : 150,
							dataIndex : 'mktActiCost'
						}, {
							header : '创建人',
							width : 150,
							dataIndex : 'createUser'
						}, {
							header : '创建日期',
							width : 150,
							dataIndex : 'createDate'
						}],
						stripeRows : true,
						tbar : [
								{
									text : '新增',
									iconCls:'addIconCss',
									handler : function() {
										addActivityInit();
									}
								},
								'-',
								{
									text : '修改',
									iconCls:'editIconCss',
									handler : function() {
									var selectLength = listPanel.getSelectionModel().getSelections().length;
									if (selectLength != 1) {
										Ext.Msg.alert("提示", "请选择一条记录!");
										return false;
									}
											editInit();
									}
								},
								'-',
								{
									text : '删除',
									iconCls:'deleteIconCss',
									handler : function() 
									{
										 var selectLength = listPanel.getSelectionModel().getSelections().length;
										if(selectLength < 1){
											Ext.Msg.alert('提示','请选择需要删除的记录!');
										} else {
												Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
													if(buttonId.toLowerCase() == "no"){
   												return;
													} else{
														Ext.Msg.alert('提示','删除成功!');
													}

												})
												;
											
										}
									}
									
								},
								'-',
								{
									text : '详情',
									iconCls:'detailIconCss ',
									handler : function() {
										var selectLength = listPanel.getSelectionModel().getSelections().length;
										
										var selectRe = listPanel.getSelectionModel().getSelections()[0];
			
										if (selectLength != 1) {
											Ext.Msg.alert("提示", "请选择一条记录!");
											return false;
										}
											var actiStatus = selectRe.data.mktActiStat;
//											if (actiStatus != '1') {
											if (1<0) {
												Ext.Msg.alert('提示','只能修改创建状态的营销活动!');
											} else {
												editBasePlanForm.getForm().loadRecord(selectRe);
												document.getElementById('marketActivityIdStr').value = selectRe.data.marketActivityId;
												detailInit();
											}
//										}
										}
								},'-',{
									text : '执行',
									iconCls:'maintainIconCss',
									handler : function() {
									var selectLength = listPanel.getSelectionModel().getSelections().length;
										if (selectLength < 1) {
											Ext.Msg.alert('提示','请选择要执行的记录!');
										} else {
												Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
													if(buttonId.toLowerCase() == "no"){
	      												return;
	      											}  else{
	      												Ext.Msg.alert('提示','操作成功!');
	      											}
										});
									}}
								},'-',{

									text : '审批',
									iconCls:'shenpiIconCss ',
									handler : function() {
									var selectLength = listPanel.getSelectionModel().getSelections().length;
									var selectRe = listPanel.getSelectionModel().getSelections()[0];
									if (selectLength != 1) {
										Ext.Msg.alert("提示", "请选择一条记录!");
										return false;
									}else{
										approveActivity();
									}
										
									}
								
								},
								'-',
								{
									text : '关闭',
									iconCls:'closeIconCss',
									handler : function() {
										closeActivity();
									}
								}],
					    width: '150%',
						bbar : bbar
					});
			var closeForm = new Ext.form.FormPanel({
				 labelWidth : 80,
				 height : 200,
				 frame : true,
				 labelAlign : 'right',
				 region : 'center',
				 autoScroll : true,
				 buttonAlign : "center",
				 items : [ {
			    	 layout : 'column',
			    	 items : [ {
			    		 columnWidth : .5,
			    		 layout : 'form',
			    		 items : [  {
			    			 id : 'productId',
			    			 name : 'productId',
			    			 xtype : 'textfield',
			    			 readOnly:true,
			    			 value:'679',
			    			 fieldLabel : '活动编号'
			    		 }]
			    	 }, {
			    		 columnWidth : .5,
			    		 layout : 'form',
			    		 items : [{
			    			 name:'productName',
			    			 xtype:'textfield',
			    			 readOnly:true,
			    			 value:'个贷业务',
			    			 fieldLabel : '活动名称',
			    			 allowBlank : false,
			    			 anchor : '90%'
			    		 }]
			    	 }
			    	 ]
				 },{
					 layout : 'form',
					 buttonAlign : 'center',
					 items : [ {
						 name : 'relDesc',
						 xtype : 'textarea',
						 fieldLabel : '说明',
						 anchor : '95%'
					 }]
					}]
			 });
//			// 关闭营销活动
			var closeWind = new Ext.Window({//关闭window
				closeAction:'hide',
				height:'200',
				width:'500',
				modal : true,//遮罩
				buttonAlign:'center',
				layout:'fit',
				items:[closeForm],
				buttons:[
				         {
				        	 text:'成功关闭',
				        	 handler: function(){
				        		 closeWind.hide();
				         }
				         },
				         {
				        	 text:'失败关闭',
				        	 handler: function(){
				        		 closeWind.hide();
				         }
				         },
				         {
				        	 text:'取消',
				        	 handler:function(){
				        		 closeWind.hide();
				         	}
				         }
				        ]
			});
			
			function closeActivity() {
				var selectLength = listPanel.getSelectionModel().getSelections().length;
				 var selectRe;
				 var tempId;
				 var idStr = '';
					if (selectLength < 1) {
						Ext.Msg.alert('提示','请选择要执行的记录!');
					} else {
						closeWind.show();
						closeWind.setTitle('营销活动关闭');
						
						
						
						
						
//							Ext.MessageBox.confirm('提示','确定要关闭吗?',function(buttonId){
//								if(buttonId.toLowerCase() == "no"){
//										return;
//									} else{
//										Ext.Msg.alert('提示','操作成功!');
//									}
//							});
					}
				}

			// 修改基本信息展示的form
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
									value:'679',
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
									value:'营销活动',
									name : 'mktActiName',
									anchor : '99%'
								} ]
							},{
								columnWidth : .99,
								layout : 'form',
								items : [ {
									xtype : 'textarea',
									labelStyle : 'text-align:right;',
									fieldLabel : '审批意见',
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

			// 定义审批窗口
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
		            approveActivityWindow.hide();
				}
				}, {
					text : '审批拒绝',
					handler : function() {
					if (!approveActivityForm.getForm().isValid()) {
		                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		                return false;
		            };
		            approveActivityWindow.hide();
				}
				}, {
					text : '取  消',
					handler : function() {
						approveActivityWindow.hide();
					}
				} ]
			});

			// 展示新增的窗口
			function addActivityInit() {
				addActivityForm.form.reset();
 				addActivityProdForm.form.reset();
 				addActivityCustForm.form.reset();
 				addActivityForm.form.findField('createUser').setValue(__userId);
 				addActivityForm.form.findField('test').setValue(__userName);
 				addActivityForm.form.findField('createDate').setValue(new Date());
 				addActivityForm.form.findField('mktActiStat').setValue(1);
 				addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
 				addActivityForm.form.findField('mktActiType').setValue('推广活动');
 				addActivityForm.form.findField('mktActiMode').setValue('宣传');
 				addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
 				addActivityForm.form.findField('mktActiCost').setValue('1000');
 				addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
 				addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
 				addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
 				addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
 				addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
 				addActivityForm.form.findField('mktActiAim').setValue('推广');
 				addActivityForm.form.findField('actiRemark').setValue('无');
				
				addActivityWindow.show();
			};
			
			// 展示修改窗口
			function editInit() {
				test = true;
				editPlanWindow.show();
			}

			// 展示详情窗口
			function detailInit() {
				test = false;
				editPlanWindow1.show();
			}
			
			// 审批的窗口
			function approveActivity() {
				approveActivityWindow.show();
			}

			var view = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'border',
					items : [
	
					{
						region : 'center',
						id : 'center-panel',
						title : "营销活动列表",
						layout : 'fit',
						height : 105,
						items : [ listPanel ]
					},
	
					{
						region : 'north',
						id : 'north-panel',
						title : "营销活动查询",
						height : 130,
						layout : 'fit',
						items : [ searchPanel ]
					}
	
					]
				}]
			});
		});