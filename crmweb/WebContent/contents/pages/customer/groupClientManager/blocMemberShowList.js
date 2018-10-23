Ext.onReady(function() {
	
			var relationIdStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=RELATION_ID'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
			var memberTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=MEMBER_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
	
			var hyflStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=HYFL'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
			var qygmStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=QYGM'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
	
			var khqygmStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=KHQYGM'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
			var isNormalCustStore = new Ext.data.SimpleStore({
				fields : ['name', 'code'],
				data : [['正式客户', '正式客户'],['非正式客户', '非正式客户']]
			});
			
			var searchPanel = new Ext.form.FormPanel({
								labelWidth : 120,
								labelAlign : 'right',
								height : 100,
								frame : true,
								id : 'searchPanel',
								region : 'north',
								title : "成员查询",
								autoScroll : true,
								items : [
									    	{
											    layout : 'column',
											    items : [
											    	    	{
													   		    columnWidth : .3,
															    layout : 'form',
																items : [ 
																		   {
																		 	   xtype : 'textfield',
																			   Width : '100',
																			   name : 'CUST_ZH_NAME',
																			   fieldLabel : '企业名称',
																			   anchor : '100%'
																	       }
																         ]
														    },
														    {
													   		    columnWidth : .3,
															    layout : 'form',
																items : [ 
																			{
																		 	   xtype : 'textfield',
																			   Width : '100',
																			   name : 'CUST_ZZDM',
																			   fieldLabel : '组织机构代码',
																			   anchor : '100%'
																	         }
																        ]
														    },
														    {
														    	columnWidth : .3,
																layout : 'form',
																items : [
																			{
																				store : isNormalCustStore,
																				xtype : 'combo',
																				vtype : 'trim',
																				fieldLabel : '客户状态',
																				name : 'IS_NORMAL_CUST',
																				hiddenName : 'IS_NORMAL_CUST',
																				valueField : 'name',
																				displayField : 'code',
																				mode : 'local',
																				typeAhead : true,
																				forceSelection : true,
																				triggerAction : 'all',
																				emptyText : '请选择',
																				selectOnFocus : true,
																				width : '100',
																				anchor : '100%',
																				editable: false
																		     }
																		]
														    }
														]
											}
										],
								buttonAlign : 'center',
								buttons : [ 
										    {
												text : '查询',
												handler : function() {
													var conditionStr = searchPanel.getForm().getValues(false);
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
											 },
											 {
												text : '重置',
												handler : function() {
								                	searchPanel.getForm().reset();
												}
											 }
								 		  ]
							});
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
							header : 'No.',
							width : 28
						 });
			// 列模型
			var columns = new Ext.grid.ColumnModel([ 
										                rownum, 
										                {
															header : '客户名称',
															width : 150,
															dataIndex : 'custZhName',
															sortable : true
														}, 
														{
															header : '组织机构代码',
															width : 95,
															dataIndex : 'custZzdm',
															sortable : true
														},
														{
															header : '上级企业名称',
															width : 150,
															dataIndex : 'parentCustZhName',
															sortable : true
														},
														{
															header : '客户状态',
															width : 100,
															dataIndex : 'isNormalCust',
															sortable : true
														},
														{
															header : '行业',
															width : 160,
															dataIndex : 'HY_CLASS_GP',
															sortable : true
														},
														{
															header : '客户规模',
															width : 90,
															dataIndex : 'CUST_SCOPE_GP',
															sortable : true
														},
														{
															header : '考核口径客户规模',
															width : 130,
															dataIndex : 'CRM_SCOPE_GP',
															sortable : true
														},
														{
															header : '税务证号',
															width : 100,
															dataIndex : 'taxCard',
															sortable : true
														},
														{
															header : '营业执照号',
															width : 100,
															dataIndex : 'wkLinceseNo',
															sortable : true
														},
														{
															header : '与上级企业关系',
															width : 100,
															dataIndex : 'RELATION_ID_ORA',
															sortable : true
														},
														{
															header : '成员类型',
															width : 100,
															dataIndex : 'MEMBER_TYPE_ORA',
															sortable : true
														}
												  ]
						  );

			var record = Ext.data.Record.create([
			                                     {name : 'id'},
			                                     {name : 'custId'},
			                                     {name : 'custZhName'},
			                                     {name : 'custZzdm'},
			                                     {name : 'parentCustZhName'},
			                                     {name : 'hyClass'},
			                                     {name : 'HY_CLASS_GP' },
			                                     {name : 'isNormalCust'},
			                                     {name : 'custScope'},
			                                     {name : 'CUST_SCOPE_GP' },
			                                     {name : 'crmScope'},
			                                     {name : 'CRM_SCOPE_GP' },
			                                     {name : 'taxCard'},
			                                     {name : 'wkLinceseNo'},
			                                     {name : 'relationId'},
			                                     {name : 'RELATION_ID_ORA' },
			                                     {name : 'memberType'},
			                                     {name : 'MEMBER_TYPE_ORA' }
												]
						 );
			
			
			// 数据
			var store = new Ext.data.Store({
//				restful : true,
//				proxy : new Ext.data.HttpProxy({
//					url : basepath + '/blocMemberShowList.json?groupNo=' + parent.document.getElementById("groupNo").value
//				}),
				reader : new Ext.data.JsonReader({
//													successProperty : 'success',
//													idProperty : 'id',
//													messageProperty : 'message',
													root : 'rows',
													totalProperty : 'num'
												 }, 
												 record
				)
			});
			var memberData= {
					TOTALCOUNT:3,
					rows:[
{"id":"G005511",
	"custId":"34521",
	"custZhName":"集团对外担保用户",
	"custZzdm":"DG09865",
	"parentCustZhName":"CRM产品研发中心",
	"hyClass":"IT",
	"HY_CLASS_GP":"金融IT",
	"isNormalCust":"是",
	"custScope":"全口径",
	"CUST_SCOPE_GP":"全口径",
	"crmScope":"对公对私",
	"CRM_SCOPE_GP":"对公对私",
	"taxCard":"无",
	"wkLinceseNo":"897756556",
	"relationId":"1234",
	"RELATION_ID_ORA":"无",
	"memberType":"产品组",
	"MEMBER_TYPE_ORA":"对外担保客户"}	,
	{"id":"G005512",
		"custId":"34521",
		"custZhName":"擎宇科技有限公司",
		"custZzdm":"DG09865",
		"parentCustZhName":"CRM产品研发中心",
		"hyClass":"IT",
		"HY_CLASS_GP":"金融IT",
		"isNormalCust":"是",
		"custScope":"全口径",
		"CUST_SCOPE_GP":"全口径",
		"crmScope":"对公对私",
		"CRM_SCOPE_GP":"对公对私",
		"taxCard":"无",
		"wkLinceseNo":"897756556",
		"relationId":"1234",
		"RELATION_ID_ORA":"无",
		"memberType":"产品组",
		"MEMBER_TYPE_ORA":"正式客户"}		,
		{"id":"G005513",
			"custId":"34521",
			"custZhName":"圣杰科技有限公司",
			"custZzdm":"DG09865",
			"parentCustZhName":"CRM产品研发中心",
			"hyClass":"IT",
			"HY_CLASS_GP":"金融IT",
			"isNormalCust":"是",
			"custScope":"全口径",
			"CUST_SCOPE_GP":"全口径",
			"crmScope":"对公对私",
			"CRM_SCOPE_GP":"对公对私",
			"taxCard":"无",
			"wkLinceseNo":"897756556",
			"relationId":"1234",
			"RELATION_ID_ORA":"无",
			"memberType":"产品组",
			"MEMBER_TYPE_ORA":"正式客户"}		,
			{"id":"G005514",
				"custId":"34521",
				"custZhName":"俊材商贸有限公司",
				"custZzdm":"DG09865",
				"parentCustZhName":"CRM产品研发中心",
				"hyClass":"IT",
				"HY_CLASS_GP":"金融IT",
				"isNormalCust":"是",
				"custScope":"全口径",
				"CUST_SCOPE_GP":"全口径",
				"crmScope":"对公对私",
				"CRM_SCOPE_GP":"对公对私",
				"taxCard":"无",
				"wkLinceseNo":"897756556",
				"relationId":"1234",
				"RELATION_ID_ORA":"无",
				"memberType":"产品组",
				"MEMBER_TYPE_ORA":"正式客户"}		,
				{"id":"G005515",
					"custId":"34521",
					"custZhName":"建辉科技有限公司",
					"custZzdm":"DG09865",
					"parentCustZhName":"CRM产品研发中心",
					"hyClass":"IT",
					"HY_CLASS_GP":"金融IT",
					"isNormalCust":"是",
					"custScope":"全口径",
					"CUST_SCOPE_GP":"全口径",
					"crmScope":"对公对私",
					"CRM_SCOPE_GP":"对公对私",
					"taxCard":"无",
					"wkLinceseNo":"897756556",
					"relationId":"1234",
					"RELATION_ID_ORA":"无",
					"memberType":"产品组",
					"MEMBER_TYPE_ORA":"正式客户"}	
					]
				};
			store.loadData(memberData);
			
			
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
			
//			// 默认加载数据
//			store.load({
//				params : {
//					start : 0,
//					limit : parseInt(pagesize_combo.getValue())
//				}
//			});

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

			// 列表
			var listPanel = new Ext.grid.GridPanel({
								store : store,
								frame : true,
								cm : columns,
								title : "集团成员列表",
								stripeRows : true,
								region : 'center',
								frame : true,
								bbar : bbar,// 分页工具栏
								tbar:[new Com.yucheng.bob.ExpButton({
									formPanel:'searchPanel',
									url:basepath + '/blocMemberShowList.json?groupNo=' + parent.document.getElementById("groupNo").value
								    })],
								viewConfig : {
								// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
								},
								loadMask : {
									msg : '正在加载表格数据,请稍等...'
								}
							});
			
			listPanel.on('rowdblclick', function(grid, rowIndex, event) {
				var checkedNodes = listPanel.getSelectionModel().selections.items;
				if(checkedNodes.length==0)
					{
						Ext.Msg.alert('提示', '未选择任何客户');
						return ;
					}
				  var custId = grid.getSelectionModel().selections.items[0].data.custId;
				  window.location.href = '../customerManager/customerBaseInformation.jsp?customerId=' + custId;

			});
			
			
			var view = new Ext.Viewport({
				layout : 'border',
				items : [ {
							region : 'center',
							id : 'center-panel',
							layout : 'fit',
							items : [ listPanel ]
						  },
						  {
							region : 'north',
							id : 'north-panel',
							height : 105,
							layout : 'fit',
							items : [ searchPanel ]
					      }
						]
			});

		})