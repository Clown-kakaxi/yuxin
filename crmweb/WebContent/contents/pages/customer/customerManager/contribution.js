Ext.onReady(function() {
	var custId =oCustInfo.cust_id;
	var custName =oCustInfo.cust_name;
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			//列模型
			var columns1 = new Ext.grid.ColumnModel([ rownum,{
				header : '编号', // 列标题
				dataIndex : 'id', // 数据索引:和Store模型对应
				sortable : true,
				hidden : true,
				width : 130
			},{
				header : '客户编号', // 列标题
				dataIndex : 'customId', // 数据索引:和Store模型对应
				sortable : true,
				width : 130
			// 是否可排序
			}, {
				header : '客户名称', // 列标题
				dataIndex : 'custName', // 数据索引:和Store模型对应
				sortable : true,
				width : 130
			// 是否可排序
			}, {
				header : '机构号',
				dataIndex : 'orgId',
				sortable : true,
				width : 130
				// 是否可排序
			}, {
				header : '存款贡献度（折人民币） ', // 列标题
				dataIndex : 'contriDeposit', // 数据索引:和Store模型对应
				sortable : true,
				align : 'right',
                renderer: money('0,000.00' ),
				width : 140
			// 是否可排序
			}, {
				header : '贷款贡献度（折人民币） ', // 列标题
				dataIndex : 'contributionLoan', // 数据索引:和Store模型对应
				sortable : true,
				align : 'right',
                renderer: money('0,000.00' ),
				width : 140
			// 是否可排序
			}, {
				header : '中间业务贡献度（目前暂无） ', // 列标题
				dataIndex : 'contributionMid', // 数据索引:和Store模型对应
				sortable : true,
				align : 'right',
                renderer: money('0,000.00' ),
				width : 140
			// 是否可排序
			}, {
				header : '客户贡献度（折人民币） ', // 列标题
				dataIndex : 'contributionCust', // 数据索引:和Store模型对应
				sortable : true,
				align : 'right',
                renderer: money('0,000.00' ),
				width : 140
			// 是否可排序
			}, {
				header : 'ETL日期', // 列标题
				dataIndex : 'etlDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 130
			// 是否可排序
			}
			]);

			var record1 = Ext.data.Record.create([
			 {name: 'id', mapping: 'ID'},                                    
             {name: 'customId', mapping: 'CUSTOM_ID'},
             {name: 'custName', mapping: 'CUST_NAME'},                                   
             {name: 'orgId', mapping: 'ORG_ID'},  
             {name: 'contriDeposit', mapping: 'CONTRI_DEPOSIT'},
             {name: 'contributionLoan', mapping: 'CONTRIBUTION_LOAN'},
             {name: 'contributionMid', mapping: 'CONTRIBUTION_MID'},
             {name: 'contributionCust', mapping: 'CONTRIBUTION_CUST'},
             {name: 'etlDate', mapping: 'ETL_DATE'}
             ]);

			var store1 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/contributionInformation-info.json?cust_id='+custId
//					success :function(response){
//					Ext.Msg.alert("123",response.responseText);
//				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
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
				value : '20',
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
						title : '贡献度信息',
						//autoScroll : true,
						//定义高度
						height:document.body.clientHeight,
						//定义宽度
						width : document.body.clientWidth-240,
						//定义显示结果列表高度
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

			
			
			// 定义展示窗口的tabPanel
//			var tokenDelimiter = ':';
//			var listPanel = new Ext.TabPanel({
//				id : 'listPanel',
//				activeTab : 0,
//				tabPosition : 'top',
//				items : [ {
//					title : '贡献度信息',
//					listeners : {
//						'activate' : function() {
//					        store1.load({
//								params : {
//									start : 0
//									//marketTeamId : document.getElementById('marketTeamId').value
//								}
//							});
//							
//						}
//					},
//					items : [ listPanel1 ]
//				}]
//
//			});
			

			var editPlanPanel = new Ext.Panel({
				height:document.body.clientHeight-30,
				layout : 'fit',
				primary : "id",
				buttonAlign : "center",
				items : [ listPanel1 ]
			});
			
					var viewport_center = new Ext.Panel({
						renderTo:'viewport_center',
						height:document.body.clientHeight-30,
						layout : 'fit',
						items: [{   
//			                    region:'center',
						        margins: '0 0 0 0',
						    	items:[editPlanPanel]}
						] 
					});
				});


