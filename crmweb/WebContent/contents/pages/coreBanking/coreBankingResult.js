Ext.onReady(function() {
	
    Ext.QuickTips.init(); 
	
	var currencyStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CCY'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
			var searchPanel = new Ext.form.FormPanel({
				labelWidth : 105,
				labelAlign : 'right',
				height : 100,
				frame : true,
				title : "实时活期余额查询",
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .3,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '账号',
									id : 'ACCOUNT',
									name : 'ACCOUNT',
									vtype: 'number',
									maxLength : '23',
									minLength : '23',
									anchor : '90%'
								} ]
							},{
								columnWidth : .3,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户名称',
									id : 'CLIENT_NAME',
									name : 'CLIENT_NAME',
									anchor : '90%'
								} ]
							},{
								columnWidth : .3,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '网点号',
									id : 'BRANCH_ID',
									name : 'BRANCH_ID',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .2,
								layout : 'form',
								items : [
									{
									store: currencyStore,
									xtype : 'combo',
									name : 'CURRENCY_TYPE',
									id : 'CURRENCY_TYPE',
									fieldLabel : '币种',
									valueField:'key',
									displayField:'value',
									mode : 'local',
									typeAhead: true,
									hidden : true,
									forceSelection: true,
									triggerAction: 'all',
									emptyText:'请选择',
									selectOnFocus:true,
									width : '100',
									anchor : '90%'
									                 }

								    ]
							},
							{
								columnWidth : .2,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '科目号',
									id : 'SUBJECT_ID',
									hidden : true,
									name : 'SUBJECT_ID',
									anchor : '90%'
								} ]
							}]
				}],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = searchPanel.getForm().getFieldValues();
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
					},{
					text : '添加实时查询',
						handler : function() {
							debugger;
//						window.location.href = 'coreBanking.jsp';
							queryChanceWindow.show();
						}
					}]

			});

			// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			//列模型
			var columns = new Ext.grid.ColumnModel([ rownum,sm,/*{
				header : 'ID', 
				dataIndex : 'ID', 
				sortable : true,
				width : 110
			},*/{
				header : '用户ID',
				dataIndex : 'USER_ID', 
				sortable : true,
				hidden : true,
				width : 110
			},{
				header : '账号', 
				dataIndex : 'ACCOUNT', 
				sortable : true,
				width : 110
			},{
				header : '客户名称', 
				dataIndex : 'CLIENT_NAME', 
				sortable : true,
				width : 110
			},{
				header : '网点号', 
				dataIndex : 'BRANCH_ID', 
				sortable : true,
				width : 110
			},{
				header : '查询时间',
				dataIndex : 'INVOKED', 
				sortable : true,
				width : 150
			},{
				header : '完成时间',
				dataIndex : 'FINISHED', 
				sortable : true,
				width : 150
			},{
				header : '币种', 
				dataIndex : 'CURRENCY_TYPE', 
				hidden : true,
				sortable : true,
				width : 110
			},{
				header : '科目号', 
				dataIndex : 'SUBJECT_ID', 
				hidden : true,
				sortable : true,
				width : 110
			},{
				header : '科目信息', 
				dataIndex : 'SUBJECT_INFO', 
				hidden : true,
				sortable : true,
				width : 110
			},{
				header : '借贷标记', 
				dataIndex : 'INDICATE', 
				hidden : true,
				sortable : true,
				width : 110
			},{
				header : '时点余额', 
				dataIndex : 'BALANCE', 
				sortable : true,
				align : 'right',
				renderer: money('0,000.00'),
				width : 110
			},{
				header : '上日余额', 
				dataIndex : 'LAST_BALANCE', 
				sortable : true,
				align : 'right',
				hidden : true,
				renderer: money('0,000.00'),
				width : 110
			},{
				header : '变动金额', 
				dataIndex : 'CHANGED', 
				align : 'right',
				renderer: money('0,000.00'),
				sortable : true,
				width : 110
			},{
				header : '备注', 
				dataIndex : 'COMMENTS', 
				sortable : true,
				width : 350
			}]);

			var record = Ext.data.Record.create([
             {name: 'ID', mapping: 'ID'},                
             {name: 'CURRENCY_TYPE', mapping: 'CURRENCY_TYPE_GP'},
             {name: 'CLIENT_NAME', mapping: 'CLIENT_NAME'},
             {name: 'SUBJECT_ID', mapping: 'SUBJECT_ID'},
             {name: 'SUBJECT_INFO', mapping: 'SUBJECT_INFO'},
             {name: 'ACCOUNT', mapping: 'ACCOUNT'},
             {name: 'CURRENCT_TIME', mapping: 'CURRENCT_TIME'},
             {name: 'INDICATE', mapping: 'INDICATE'},
             {name: 'BALANCE', mapping: 'BALANCE'},
             {name: 'COMMENTS', mapping: 'COMMENTS'},
             {name: 'CHANGED', mapping: 'CHANGED'},
             {name: 'INVOKED', mapping: 'INVOKED'},
             {name: 'FINISHED', mapping: 'FINISHED'},
             {name: 'BRANCH_ID', mapping: 'BRANCH_ID'},
             {name: 'USER_ID', mapping: 'USER_ID'}
			]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/tempCoreBankingQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
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
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
							[ 1000, '1000条/页' ]]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
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
						title : "实时活期余额查询-->结果列表",
						stripeRows : true,
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

			
			
			//******************************
//			
//			//时时查询
			var queryChanceForm = new Ext.form.FormPanel({
			labelWidth : 100,
			height : 150,
			frame : true,
			region : 'center',
			autoScroll : true,
			buttonAlign : "center",
			items : [{
				layout : 'column',
				items : [{
							columnWidth : .8,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 400,
									id:'queryAccount',
									fieldLabel : '请输入查询账户',
									name : 'queryAccount',
									allowBlank : false,
									vtype: 'number',
									maxLength : '23',
									minLength : '23',
									anchor : '90%'
								}
							    ]
						}]}],buttons : [{
									text : '查询',
									handler : function() {
										var qAccount = Ext.getCmp('queryAccount').getValue();
										if(qAccount==null||qAccount==""||qAccount.length!=23){
											Ext.Msg.alert("输入格式错误","请输入23位用户账户！");
											queryChanceForm.getForm().reset();
										}else{
											Ext.Ajax.request({
												url : basepath+ '/coreBankingQueue',
												method : 'GET',
												params:{
													account: qAccount
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function(response) {
													var message = Ext.util.JSON.decode(response.responseText);
													var queSize = message.queueSize;
													queryChanceForm.getForm().reset();
													queryChanceWindow.hide();
													Ext.Msg.alert('插入成功',"当前队列中有"+queSize+"位用户正在等待查询");
													store.reload();
													window.setInterval("refreshNotification()", 10000);
													//window.location.href = 'coreBankingResult.jsp';
												},
												failure : function() {
													Ext.Msg.alert("插入失败");
												}
											});
								}
									}
								}]
		});

			
			//账户实时查询
			var queryChanceWindow = new Ext.Window({
				title : '实时查询',
				plain : true,
				layout : 'fit',
				width : 400,
				height : 200,
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
				items : [ queryChanceForm ]
			});
			//******************************

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
					height : 125,
					layout : 'fit',
					items : [ searchPanel ]
				}

				]
			});

		})