//为德阳银行POC 稍作了一些改动
var msFlag=0;
var kehuSearch = new Ext.form.FormPanel({
				labelWidth : 105,
				title : "客户信息查询",
				labelAlign : 'right',
				height : 100,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户名称',
									name : 'CUST_ZH_NAME',
									width : '100',
									anchor : '100%'
								} ]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '证件号码',
									name : 'CERT_NUM',
									width : '100',
									anchor : '100%'
								} ]
							},{
								columnWidth : .3,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : 'sdf',
									hidden :true,
									id : 'MKT_TEAM_ID2',
									name : 'MKT_TEAM_ID2',
									width : '100',
									anchor : '100%'
								} ]
							}]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = kehuSearch.getForm().getValues(
								false);
						kehuStore.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						kehuStore.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});

					}
				},{
					text : '重置',
					handler : function() {
						kehuSearch.getForm().reset();
						var ssss = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId;
						Ext.getCmp("MKT_TEAM_ID2").setValue(ssss);
						
					}
				} ]

			});


			
			 // 复选框
			var mtmsm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var mtmrownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});
			var sujmStoreRecord = Ext.data.Record.create([
			          {name: 'id', mapping: 'ID'},
                      {name: 'custId', mapping: 'CUST_ID'},
                      {name: 'custZzdm', mapping: 'CERT_NUM'},
                      {name: 'custZhName', mapping : 'CUST_ZH_NAME'}
//                      {name: 'belongInsn', mapping: 'BELONG_INSTN'}
			        ]);
			// 定义列模型
			var mtmcm = new Ext.grid.ColumnModel([mtmrownum,mtmsm,
			        {header : 'id',dataIndex : 'id',width : 250,hidden:true},
			        {header : '证件号码',dataIndex : 'custZzdm',sortable : true,width : 250},
			        {header : '客户名称',dataIndex : 'custZhName',sortable : true,width : 250},
			        {header : '归属行',dataIndex : 'belongInsn',width : 200,sortable : true,hidden :true}
					]);
			
			 var sujmStore = new Ext.data.Store({
				 restful : true,
					proxy : new Ext.data.HttpProxy({
						url : basepath + '/addTeamCustomerInfoQueryQuery.json' 
//						success : function(response) {
//							Ext.Msg.alert('提示', response.responseText);
//							debugger;
//						}
//						failure : function(response) {
//							Ext.Msg.alert('提示','加入失败' );
//						}

					}),
					reader : new Ext.data.JsonReader({
						successProperty: 'success',
				        idProperty: 'ID',
				        messageProperty: 'message',
						root : 'json.data',
						totalProperty: 'json.count'
					},sujmStoreRecord)
				});
			 
				
			
			var queryMarketTeamCustomerForm = new Ext.form.FormPanel({
				labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'right', // 标签对齐方式
				region:'north',
				// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
				buttonAlign : 'center',
				height : 80,
				items : [{
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [ {
                            hidden:true,
                            xtype:'textfield',
                            name:'msFlag',
                            value:'0',
                            id:'msFlag'
                            },{
							xtype : 'textfield',
							fieldLabel : '证件号码',
							id : 'CUST_ZZDM1',
							name : 'CUST_ZZDM1',
							width : '100',
							anchor : '100%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							id : 'CUST_ZH_NAME11',
							name : 'CUST_ZH_NAME11',
							width : '100',
							anchor : '100%'
						} ]
					}]
				}],
			buttons : [{
						text : '查询',
						handler : function() {
							var conditionStr = queryMarketTeamCustomerForm.getForm().getFieldValues();
//							alert(conditionStr);
							sujmStore.baseParams = {
									"condition" : Ext.encode(conditionStr)
								};
							sujmStore.reload({
								  params : {
	                                   start : 0,
	                                   limit : bbar.pageSize }} );
					
					   }},{
						text : '重置',
							handler : function() {
								queryMarketTeamCustomerForm.getForm().reset();
								if(msFlag!=0){
		                            Ext.getCmp('msFlag').setValue('1');
		                            Ext.getCmp('msFlag').setReadOnly(true);
		                        }
							}
						}]
			});

			/****************************************/
			if(window.location.href.split("msFlag=")[1]!=undefined){
			    msFlag=1;
			}
			if(msFlag!=0){
			    Ext.getCmp('msFlag').setValue('1');
			    Ext.getCmp('msFlag').setReadOnly(true);
			}
			//***********************

            // 每页显示条数下拉选择框
            var spagesize_combo = new Ext.form.ComboBox({
                name : 'pagesize',
                triggerAction : 'all',
                mode : 'local',
                store : new Ext.data.ArrayStore({
                    fields : [ 'value', 'text' ],
                    data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
                            [ 1000, '1000条/页' ] ]
                }),
                valueField : 'value',
                displayField : 'text',
                value : '100',
                forceSelection : true,
                width : 85
            });

            // 改变每页显示条数reload数据
            spagesize_combo.on("select", function(comboBox) {
                sbbar.pageSize = parseInt(spagesize_combo.getValue()),
                sujmStore.reload({
                    params : {
                        start : 0,
                        limit : parseInt(spagesize_combo.getValue())
                    }
                });
            });
            // 分页工具栏
            var sbbar = new Ext.PagingToolbar({
                pageSize : parseInt(spagesize_combo.getValue()),
                store : sujmStore,
                displayInfo : true,
                displayMsg : '显示{0}条到{1}条,共{2}条',
                emptyMsg : "没有符合条件的记录",
                items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
            });

            //***********************
			// 表格实例
			var MarketTeamCustomerGrid = new Ext.grid.GridPanel({
						height :310,
						width : 200,
						frame : true,
						autoScroll : true,
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						store : sujmStore, // 数据存储
						stripeRows : true, // 斑马线
						cm : mtmcm, // 列模型
						sm : mtmsm, // 复选框
						tbar :  [
									{
										text : '加入团队',
										iconCls : 'addIconCss',
										handler : function()  {
											
											 var selectLength = MarketTeamCustomerGrid.getSelectionModel().getSelections().length;
												if(selectLength<1){
													Ext.Msg.alert("系统提醒","请选择至少一条记录");
													return false;
												}
												var checkedNodes = MarketTeamCustomerGrid.getSelectionModel().selections.items;
												var sujm = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId;
												var json = {'custId':[]};
												var json1 = {'marketTeamId':[]};
												for(var i=0;i<checkedNodes.length;i++){
													json.custId.push(checkedNodes[i].data.custId);
													checkedNodes[i].data.marketTeamId=sujm;
													json1.marketTeamId.push(checkedNodes[i].data.marketTeamId);
												}
											Ext.Ajax.request({
												url : basepath + '/addTeamCustomerInfoAction.json?a=1',
												method : 'POST',
												params : {
													'custId' :Ext.encode(json),
													'marketTeamId':Ext.encode(json1),
													'operate': 'add'
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success :checkResult,
												failure :checkResult
											});
											
											function checkResult(response) {
												var resultArray = Ext.util.JSON.decode(response.status);
												var resultError = response.responseText;
												if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
													Ext.Msg.alert('提示', '操作成功');
													sujmStore.reload({
														params : {
															start : 0,
															limit :bbar.pageSize
															                    }
															                });
													addMarketTeamCustomerWindow.hide();
													
													kehuStore.reload({
											params : {
											'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}else{
													if(resultArray == 403){
														Ext.Msg.alert('提示', response.responseText);
														}
													else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
													kehuStore.reload({
											params : {
											'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}
											}
											};
//											}
										}
									}], // 表格工具栏
						bbar:sbbar,
						viewConfig:{
							   forceFit:false,
							   autoScroll:true
							},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			
			
			var mtmsm1 = new Ext.grid.CheckboxSelectionModel();
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});
			//列模型 
			var columns = new Ext.grid.ColumnModel([ rownum,mtmsm1,{
				header : 'ID', // 列标题
				dataIndex : 'id', // 数据索引:和Store模型对应
				sortable : true,
				hidden:true,
				width : 150
			// 是否可排序
			},{
				header : '团队号', // 列标题
				dataIndex : 'mktTeamId', // 数据索引:和Store模型对应
				sortable : true,
				hidden :true,
				width : 150
			// 是否可排序
			},{
				header : '客户ID', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				sortable : true,
				hidden:true,
				width : 150
			// 是否可排序
			},{
				header : '客户名称', // 列标题
				dataIndex : 'custZhName', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}, {
				header : '证件号码', // 列标题
				dataIndex : 'custZzdm', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}/*, {
				header : '归属行',
				dataIndex : 'belongInsn',
				sortable : true,
				width : 150
			}*/, {
				header : '加入时间 ', // 列标题
				dataIndex : 'joinDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}]);
			
			//营销团队客户信息
			var record = Ext.data.Record.create([
			     {name: 'id', mapping: 'ID'},                 
			     {name: 'custId', mapping: 'CUST_ID'},
			     {name :'custZhName', mapping :'CUST_ZH_NAME'},
			     {name :'custZzdm', mapping :'CERT_NUM'},
                 {name: 'joinDate', mapping: 'JOIN_DATE'},        
                 {name:'mktTeamId',mapping :'MKT_TEAM_ID'},
                 {name:'belongInsn',mapping :'BELONG_INSN'}
                 ]);
			
			var kehuStore = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/marketTeamCustomerInfo.json',
					failure : function(response){
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				},record)
			});
			
			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
							[ 1000, '1000条/页' ]]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				editable : false,
				width : 85
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				kehuStore.reload({
					params : {
						'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : kehuStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			
			
			var SMEOPanel = new Ext.grid.GridPanel(
					{
						store : kehuStore,
						title : "客户信息表",
						frame : true,
						height:300,
						cm : columns,
						sm : mtmsm1,
						stripeRows : true,
						tbar : [
								{
									text : '新增',
									iconCls : 'addIconCss',
									handler : function() {
										var tempId = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.teamLeaderId;
										if(tempId!=__userId){
											Ext.Msg.alert("系统提醒","您不是该团队负责人，无权维护该项！");
											return false;
										}
										addMarketTeamCustomerInit();
									}
								},
								'-',
								{
									text : '删除',
									iconCls : 'deleteIconCss',
									handler : function() {
										var tempId = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.teamLeaderId;
										if(tempId!=__userId){
											Ext.Msg.alert("系统提醒","您不是该团队负责人，无权维护该项！");
											return false;
										}
										
										var  _record = SMEOPanel.getSelectionModel().getSelected();
										
//										
//										
//										var selectLength = SMEOPanel.getSelectionModel().getSelections().length;
//										var selectRe = SMEOPanel.getSelectionModel().getSelections()[0];
										if (!_record) {
											Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
										} else {
												if (confirm("确定删除吗?")) {
													var checkedNodes = SMEOPanel.getSelectionModel().selections.items;
													var json={'id':[]};
													for(var i=0;i<checkedNodes.length;i++)
									    			{
									    				json.id.push(checkedNodes[i].data.id);
									    			}
													
													
													Ext.Ajax.request({
																url:basepath+'/addTeamCustomerInfoAction.json?a=2',
									                            method: 'POST',
									                            params : {
									    							cbid:Ext.encode(json),
									    							'operate':'delete'
									    						},
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success :checkResult,
																failure :checkResult
															});
													function checkResult(response) {
														var resultArray = Ext.util.JSON.decode(response.status);
														var resultError = response.responseText;
														debugger;
														if ((resultArray == 200 ||resultArray == 201||resultArray == 404)) {
															Ext.Msg.alert('提示', '操作成功');
															kehuStore.reload({
													params : {
													'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
													start : 0,
													limit :bbar.pageSize
													                    }
													                });
														}else{
															if(resultArray == 403){
																Ext.Msg.alert('提示', response.responseText);
																}
															else {
															Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
															kehuStore.reload({
													params : {
													'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
													start : 0,
													limit :bbar.pageSize
													                    }
													                });
														}}};
												};
											}
									}
								}],
						bbar : bbar,// 分页工具栏
						region : 'center',
						frame : true,
					
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			
			// 定义展示员工基本信息窗口
			var addMarketTeamCustomerWindow = new Ext.Window({
				title : '客户信息查询',
				plain : true,
				layout : 'border',
				width : 800,
				height : 400,
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
				items: [queryMarketTeamCustomerForm,MarketTeamCustomerGrid]
			});
			
			function addMarketTeamCustomerInit() {
				addMarketTeamCustomerWindow.show();
			}	
			
			