Ext.onReady(function() {
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			//列模型
			var columns1 = new Ext.grid.ColumnModel([ rownum,{
				header : '统计日期', // 列标题
				dataIndex : 'crm_dt', // 数据索引:和Store模型对应
				sortable : true,
				width : 100,
				renderer:function(){return '2012-06-29';}
			// 是否可排序
			}, {
				header : '贷款类型', // 列标题
				dataIndex : 'loanTyp', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},  {
				header : '上年余额 ', // 列标题
				dataIndex : 'lastAcBl', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '本期余额 ', // 列标题
				dataIndex : 'curAcBl', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '上年日均 ', // 列标题
				dataIndex : 'lastYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '本年日均 ', // 列标题
				dataIndex : 'curYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '上年月均', // 列标题
				dataIndex : 'lastMonthAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '本年月均', // 列标题
				dataIndex : 'curMonthAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '上年季均', // 列标题
				dataIndex : 'lastQuarterAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '本年季均', // 列标题
				dataIndex : 'curQuarterAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}
			]);

			var record1 = Ext.data.Record.create([                                 
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'loanTyp', mapping: 'LOAN_TYP'},                                   
             {name: 'custTyp', mapping: 'CUST_TYP'},  
             {name: 'curAcBl', mapping: 'CUR_AC_BL'},
             {name: 'lastAcBl', mapping: 'LAST_AC_BL'},
             {name: 'lastYearAvg', mapping: 'LAST_YEAR_AVG'},
             {name: 'curYearAvg', mapping: 'CUR_YEAR_AVG'},
             {name: 'lastMonthAvg', mapping: 'LAST_MONTH_AVG'},
             {name: 'curMonthAvg', mapping: 'CUR_MONTH_AVG'},
             {name: 'lastQuarterAvg', mapping: 'LAST_QUARTER_AVG'},
             {name: 'curQuarterAvg', mapping: 'CUR_QUARTER_AVG'}
             ]);

			var store1 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/loanInformationquery.json?customerId='+oCustInfo.cust_id
//					success :function(response){
//					Ext.Msg.alert("123",response.responseText);
//				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'CUST_ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record1)
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
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});

			// 默认加载数据
			store1.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar1.pageSize = parseInt(pagesize_combo.getValue()),
				store1.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar1 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store1,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var listPanel1 = new Ext.grid.GridPanel(
					{
						title : '贷款业务概况',
						height : 455,
						height:document.body.clientHeight-64,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store1,
						frame : true,
						cm : columns1,
						stripeRows : true,
						//region : 'center',
						frame : true,
						bbar : bbar1,// 分页工具栏
						viewConfig:{
							   forceFit:false,
							   autoScroll:true
							},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});

			//列模型
			var columns2 = new Ext.grid.ColumnModel([ rownum,{
				header : '统计日期', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100,
				renderer:function(){return '2012-06-29';}
			// 是否可排序
			},{
				header : '本期余额 ', // 列标题
				dataIndex : 'curAcBl', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '上年余额 ', // 列标题
				dataIndex : 'lastAcBl', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '上年日均',
				dataIndex : 'lastYearAvg',
				sortable : true,
				width : 100
				// 是否可排序
			},{
				header : '本年日均', // 列标题
				dataIndex : 'curYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '上年月均', // 列标题
				dataIndex : 'lastMonthAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},  {
				header : '本年月均 ', // 列标题
				dataIndex : 'curMonthAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '上年季均 ', // 列标题
				dataIndex : 'lastQuarterAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '本年季均', // 列标题
				dataIndex : 'curQuarterAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}
			]);

			var record2 = Ext.data.Record.create([                                  
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'custTyp', mapping: 'CUST_TYP'},
             {name: 'curAcBl', mapping: 'CUR_AC_BL'},
             {name: 'lastAcBl', mapping: 'LAST_AC_BL'},
             {name: 'lastYearAvg', mapping: 'LAST_YEAR_AVG'},
             {name: 'curYearAvg', mapping: 'CUR_YEAR_AVG'},
             {name: 'lastMonthAvg', mapping: 'LAST_MONTH_AVG'},
             {name: 'curMonthAvg', mapping: 'CUR_MONTH_AVG'},
             {name: 'lastQuarterAvg', mapping: 'LAST_QUARTER_AVG'},
             {name: 'curQuarterAvg', mapping: 'CUR_QUARTER_AVG'}
             ]);

			var store2 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/saveInformation-info.json?customerId='+oCustInfo.cust_id
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'CUST_ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record2)
			});
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});

			// 默认加载数据
			store2.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar2.pageSize = parseInt(pagesize_combo.getValue()),
				store2.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar2 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store2,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var listPanel2 = new Ext.grid.GridPanel(
					{
						title:'存款业务概况',
//						autoScroll : true,
						height : 455,
						height:document.body.clientHeight-64,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store2,
						frame : true,
						cm : columns2,
						stripeRows : true,
						region : 'center',
						frame : true,
						bbar : bbar2,// 分页工具栏
				        viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			//列模型
			var columns3 = new Ext.grid.ColumnModel([ rownum,{
				header : '统计日期', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100,
				renderer:function(){return '2012-06-29';}
			// 是否可排序
			},{
				header : '业务种类', // 列标题
				dataIndex : 'type', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '业务笔数', // 列标题
				dataIndex : 'count', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '业务发生额',
				dataIndex : 'amount',
				sortable : true,
				width : 100
				// 是否可排序
			}, {
				header : '手续费收入 ', // 列标题
				dataIndex : 'income', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}
			]);

			var record3 = Ext.data.Record.create([
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'custTyp', mapping: 'CUST_TYP'},
             {name: 'type', mapping: 'TYPE'},
             {name: 'count', mapping: 'COUNT'},
             {name: 'amount', mapping: 'AMOUNT'},
             {name: 'income', mapping: 'INCOME'}
             ]);

			var store3 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/betweenBusinessInformation-info.json?customerId='+oCustInfo.cust_id
//					success :function(response){
//					Ext.Msg.alert("123",response.responseText);
//					debugger;
//				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'CUST_ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record3)
			});
            
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});
			// 默认加载数据
			store3.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar3.pageSize = parseInt(pagesize_combo.getValue()),
				store3.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar3 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store3,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var listPanel3 = new Ext.grid.GridPanel(
					{
						title : '中间业务概况',
//						autoScroll : true,
						height : 455,
						height:document.body.clientHeight-64,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store3,
						frame : true,
						cm : columns3,
						stripeRows : true,
						region : 'center',
						frame : true,
						bbar : bbar3,// 分页工具栏
						viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			//列模型
			var columns4 = new Ext.grid.ColumnModel([ rownum,{
				header : '统计日期', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				sortable : true,
				width : 150,
				renderer:function(){return '2012-06-29';}
			// 是否可排序
			},{
				header : '上年度国际结算量', // 列标题
				dataIndex : 'lastYearAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}, {
				header : '本期国际结算量', // 列标题
				dataIndex : 'curAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}, {
				header : '上年度日均外币存款',// 列标题
				dataIndex : 'lastDepWbYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
			}, {
				header : '本期日均外币存款 ', // 列标题
				dataIndex : 'curDepWbYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			},{
				header : '上年末外币存款余额 ', // 列标题
				dataIndex : 'lastDepWbAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			},{
				header : '本期外币存款余额 ', // 列标题
				dataIndex : 'curDepWbAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			},{
				header : '上年末日均外币贷款余额 ', // 列标题
				dataIndex : 'lastLoanWbYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			},{
				header : '本期日均外币贷款余额 ', // 列标题
				dataIndex : 'curLoanWbYearAvg', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			},{
				header : '上年末外币贷款余额 ', // 列标题
				dataIndex : 'lastLoanWbAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			},{
				header : '本期外币贷款余额 ', // 列标题
				dataIndex : 'curLoanWbAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}
			]);

			var record4 = Ext.data.Record.create([
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'custTyp', mapping: 'CUST_TYP'},
             {name: 'lastYearAmt', mapping: 'LAST_YEAR_AMT'},
             {name: 'curAmt', mapping: 'CUR_AMT'},
             {name: 'lastDepWbYearAvg', mapping: 'LAST_DEP_WB_YEAR_AVG'},
             {name: 'curDepWbYearAvg', mapping: 'CUR_DEP_WB_YEAR_AVG'},
             {name: 'lastDepWbAmt', mapping: 'LAST_DEP_WB_AMT'},
             {name: 'curDepWbAmt', mapping: 'CUR_DEP_WB_AMT'},
             {name: 'lastLoanWbYearAvg', mapping: 'LAST_LOAN_WB_YEAR_AVG'},
             {name: 'curLoanWbYearAvg', mapping: 'CUR_LOAN_WB_YEAR_AVG'},
             {name: 'lastLoanWbAmt', mapping: 'LAST_LOAN_WB_AMT'},
             {name: 'curLoanWbAmt', mapping: 'CUR_LOAN_WB_AMT'}
             ]);

			var store4 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/internationalBusinessInformation-info.json?customerId='+oCustInfo.cust_id
//					success :function(response){
//					Ext.Msg.alert("123",response.responseText);
//					debugger;
//				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'CUST_ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record4)
			});
            
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});
			// 默认加载数据
			store4.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar4.pageSize = parseInt(pagesize_combo.getValue()),
				store4.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar4 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store4,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var listPanel4 = new Ext.grid.GridPanel(
					{
						title : '国际业务概况',
						height:document.body.clientHeight-64,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store4,
						frame : true,
						cm : columns4,
						stripeRows : true,
						region : 'center',
						frame : true,
						bbar : bbar4,// 分页工具栏
						viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			
			
			
			
			
			
			// 定义展示窗口的tabPanel
			var tokenDelimiter = ':';
			var listPanel = new Ext.TabPanel({
				id : 'listPanel',
				activeTab : 0,
				tabPosition : 'top',
				items : [ {
					title : '贷款业务概况',
					listeners : {
						'activate' : function() {
					        store1.load({
								params : {
									start : 0
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
							
						}
					},
					items : [ listPanel1 ]
				},{
					title : '存款业务概况',
					listeners : {
						'activate' : function() {
					store2.load({
								params : {
									start : 0
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
							
						}
					},
					items : [ listPanel2 ]
				}, {
					title : '中间业务概况',
					listeners : {
						'activate' : function() {
					store3.load({
								params : {
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
						}
					},
					items : [ listPanel3 ]
				},{
					title : '国际业务概况',
					listeners : {
						'activate' : function() {
					store4.load({
								params : {
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
						}
					},
					items : [ listPanel4 ]
				} ]

			});
			

			var editPlanPanel = new Ext.Panel({
				height:document.body.clientHeight-37,
				layout : 'fit',
				primary : "id",
				autoScroll : true,
				buttonAlign : "center",
				items : [ listPanel ]
			});
			
			var viewport_center = new Ext.Panel({
						autoScroll : true,
						renderTo:'viewport_center',
						height:document.body.clientHeight-33,
						layout : 'fit',
						items: [{   
					            autoScroll:true,
//			                    region:'center',
						        margins: '0 0 0 0',
						    	items:[editPlanPanel]}
						] 
					});
				});


