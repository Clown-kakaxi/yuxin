Ext.onReady(function() {
			
	var xlStore1 = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data : [['1','新增'], ['2','删除' ]]
	});
	var prodStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/queryprodinfo1.json'
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data',
			totalProperty: 'json.count'
		}, [ 'PRODUCT_ID', 'PROD_NAME' ])
	});
			var searchPanel = new Ext.form.FormPanel({
				title : "查询条件",
				labelWidth : 105,
				labelAlign : 'right',
				height : 125,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'CUST_NAME',
									align : 'right',
									fieldLabel : '客户名称',
									anchor : '90%'
								} ]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'CUST_ID',
									align : 'right',
									fieldLabel : '客户编号',
									anchor : '90%'
								} ]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'CERT_NUM',
									align : 'right',
									fieldLabel : '证件号码',
									anchor : '90%'
								} ]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'PRODUCT_NO',
									align : 'right',
									fieldLabel : '产品编号',
									anchor : '90%'
								} ]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									Width : '100',
									name : 'PRODUCT_NAME',
									align : 'right',
									fieldLabel : '产品名称',
									anchor : '90%'
								} ]
							}
								,{
								columnWidth : .33,
								layout : 'form',
								items : [ 
								    new Ext.ux.form.LovCombo({
								    	fieldLabel: '下拉复选框',
								    	id:'tablePkField',
								    	name: 'tablePkField',
								    	displayField:'value',
								    	valueField:'key',
								    	hideOnSelect:false,
//								    	maxHeight:200,
//								    	store:new Ext.data.SimpleStore({
//								    		fields: ['key','value'],
//								    		data:[
//								    		      [100,"选项A"],[200,"选项B"],[300,"选项C"],[400,"选项D"]
//								    		]
//								    	}),
								    	store : new Ext.data.ArrayStore({
											fields : [ 'key', 'value' ],
											data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
													[ 100, '100条/页' ], [ 250, '250条/页' ],
													[ 455, '500条/页' ] ]
										}),
								    	triggerAction:'all',
								    	mode:'local',
								    	allowBlank:false,
								    	editable:true
								    })
//								          {
//									fieldLabel: '状态',
//		                             name : 'OPP_TYPE',
//		                             id:'OPP_TYPE',
//		                             forceSelection : true,
//		                             xtype:'combo',
//		                             labelStyle: 'text-align:right;',
//		                             triggerAction:'all',
//		                             mode:'local',
//		                             store:xlStore1,
//		                             valueField:'myId',
//		                             displayField:'displayText',
//		                             emptyText:'',
//		                             anchor : '90%'
//								} 
								          ]
							}
							]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = searchPanel.getForm().getValues(false);
						store.baseParams = {"condition" : Ext.encode(conditionStr)};
						store.load({params : {start : 0,limit : parseInt(pagesize_combo.getValue())}});
					}
				}, {
					text : '重置',
					handler : function() {
						searchPanel.getForm().reset();
					}
				}  ]

			});

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			
			var sm = new Ext.grid.CheckboxSelectionModel();
			var columns = new Ext.grid.ColumnModel([ rownum,sm,{
				header : '客户编号',
				width : 100,
				align : 'left',
				dataIndex : 'custId',
				sortable : true
			}, {
				header : '客户名称',
				width : 130,
				align : 'left',
				dataIndex : 'custName',
				sortable : true
			}, {
				header : '证件类型',
				width : 170,
				align : 'left',
				dataIndex : 'certType',
				sortable : true
			}, {
				header : '证件号码',
				width : 170,
				align : 'left',
				dataIndex : 'certNum',
				sortable : true
			}, {
				header : '产品编号',
				width : 170,
				align : 'left',
				dataIndex : 'productNo',
				sortable : true
			}, {
				header : '产品名称',
				width : 170,
				align : 'left',
				dataIndex : 'productName',
				sortable : true
			}, {
				header : '购买金额（元）',
				width : 150,
				align : 'left',
				dataIndex : 'buyAmt',
				sortable : true
			}
//			,{
//			
//				width : 150,
//				align : 'left',
//				dataIndex : 'id',
//				sortable : true
//			},{
//				
//				width : 150,
//				align : 'left',
//				dataIndex : 'custId',
//				sortable : true
//			},{
//				
//				width : 150,
//				align : 'left',
//				dataIndex : 'oppUser',
//				sortable : true
//			},{
//			
//				width : 150,
//				align : 'left',
//				dataIndex : 'oppDate',
//				sortable : true
//			},{
//				
//				width : 150,
//				align : 'left',
//				dataIndex : 'oppReason',
//				sortable : true
//			},{
//			
//				width : 150,
//				align : 'left',
//				dataIndex : 'oppType',
//				sortable : true
//			}
			]);

			var record = Ext.data.Record.create([ {
				name : 'custName',
				mapping : 'CUST_NAME'
			}, {
				name : 'certType',
				mapping : 'CERT_TYPE'
			},{
				name : 'certNum',
				mapping : 'CERT_NUM'
			},{
				name : 'productNo',
				mapping : 'PRODUCT_NO'
			}, {
				name : 'productName',
				mapping : 'PRODUCT_NAME'
			}, {
				name : 'buyAmt',
				mapping : 'BUY_AMT'
			}
			,{
				name : 'id',
				mapping : 'ID'
			},{
				name : 'custId',
				mapping : 'CUST_ID'
			},{
				name : 'oppUser',
				mapping : 'OPP_USER'
			},{
				name : 'oppDate',
				mapping : 'OPP_DATE'
			},{
				name : 'oppReason',
				mapping : 'OPP_REASON'
			}, {
				name : 'oppType',
				mapping : 'OPP_TYPE'
			}
			]);

			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/probuyInfoQuery.json',
					failure : function(response) {
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

			// 默认加载数据
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

			var listPanel = new Ext.grid.GridPanel(
					{
						id : 'listPanel',
						title : "查询结果",
						region : 'center',
						store : store,
						frame : true,
						stripeRows : true,
						height : 125,
						cm : columns,
						sm : sm,
						bbar : bbar,// 分页工具栏
						tbar : [
								{
									text : '新增',
									hidden:true,
									iconCls:'addIconCss',
									handler : function() {
										addInit();
									}
								},
								//'-',
								{
									text : '修改',
									hidden:true,
									iconCls:'editIconCss',
									handler : function() {

										var selectLength = listPanel
												.getSelectionModel()
												.getSelections().length;

										var selectRe = listPanel
												.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											Ext.Msg.alert('提示','请选择一条记录!');
										} else {
											editBasePlanForm.getForm().loadRecord(selectRe);
											editInit();
										}
									}

								},
								//'-',
								{
									text : '删除',
									hidden:true,
									iconCls : 'deleteIconCss',
									handler : function() {
										var selectLength = listPanel
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
												var idStr = '';
												debugger;
												for ( var i = 0; i < selectLength; i++) {
													selectRe = listPanel
															.getSelectionModel()
															.getSelections()[i];
													tempId = selectRe.data.id;
													idStr += tempId;
													if (i != selectLength - 1)
														idStr += ',';
												}
												Ext.Ajax.request({
															url : basepath
																	+ '/probuy-info!batchDestroy.json?idStr='
																	+ idStr,
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															success : function() {
																Ext.Msg.alert('提示', '操作成功');
																store.reload();
															},
															failure : function(response) {
																var resultArray = Ext.util.JSON.decode(response.status);
																if(resultArray == 403) {
																window.location = basepath + '/403.jsp';
																} else {
																Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
																}
																store.reload();
															}
														});

											})
											;
										}
									}
								},
								//'-',
								{
									id:'importTemple',
									text : '新增模板下载',
									iconCls:'addIconCss',
									disabled:false,
									handler : function(){
//						                window.location.href = basepath+"/AnnexeDownload?filename=attachment1326456224567.xls";
						                window.open( basepath+'/TempDownload?filename=attachment1326456224567.xls','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
						            }
								},
								'-',
								{
									text : '批量新增导入',
									iconCls:'importIconCss',
									id:'importbt',
									disabled:false,
									handler:function(){
									importForm.tradecode='MktIndexImportTemp';
						                importWindow.show();
						            }
								},
								//'-',
								{
									id:'importTemple1',
									text : '删除模板下载',
									hidden:true,
									iconCls : 'deleteIconCss',
									disabled:false,
									handler : function(){
					                window.open( basepath+'/TempDownload?filename=attachment.xls','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
						            }
								},
								//'-',
								{
									text : '批量删除导入',
									hidden:true,
									iconCls : 'deleteIconCss',
									id:'importbt1',
									disabled:false,
									handler:function(){
									importForm.tradecode='MktIndexImportTemp1';
						            importWindow.show();
						            }
								}
								],
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			var search_cust_add = new Ext.ux.form.CustomerQueryField({ 
				fieldLabel : '客户名称', 
				labelStyle: 'text-align:right;',
				labelWidth : 100,
				name : 'custName',
				id:'add_aimCustomerName',
				 editable : false,
				 allowBlank:false,//不允许为空
                 blankText:"不能为空，请填写",
				singleSelected:true,
 				anchor : '90%',
 				callback :function(){
 						var cust_id = null;
								var cust_name = null;
								var certType ='';
								var certNum = '';
								cust_name = Ext.getCmp('add_aimCustomerName').getValue();
								if (cust_name != null && cust_name != '') {
									cust_id = Ext.getCmp('add_aimCustomerName').customerId.aId[0];
									certType = Ext.getCmp('add_aimCustomerName').certType.certtyp[0];
									certNum = Ext.getCmp('add_aimCustomerName').certNum.certnum[0];
									addPlanForm.getForm().findField('custId').setValue(cust_id);
									addPlanForm.getForm().findField('certType').setValue(certType);
									addPlanForm.getForm().findField('certNum').setValue(certNum);
								}
 				}
			});
			// 新增窗口展示的from
			var addPlanForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 500,
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
						items : [search_cust_add,{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '客户编号',
							readOnly:true,
							name : 'custId',
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							name : 'certType',
							xtype : 'textfield',
							fieldLabel : '证件类型',
							readOnly:true,
							width : '100',
							anchor : '90%',
							allowBlank : false
						}, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '证件号码',
							readOnly:true,
							name : 'certNum',
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ /*{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '产品名称',
							name : 'productName',
							anchor : '90%'
						}, */{
							store : prodStore,
							id:'prodname2',
							xtype : 'combo', 
							resizable : true,
							name : 'productName',
							hiddenName : 'productName',
							fieldLabel : '产品名称',
							valueField : 'PROD_NAME',
							displayField : 'PROD_NAME',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							editable:true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							anchor : '90%'
					},{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '购买金额（元）',
							name : 'buyAmt',
							anchor : '90%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '产品编号',
							id:'prodid2',
							name : 'productNo',
							anchor : '90%'
						} ,{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '操作类型',
							name : 'oppType',
							anchor : '90%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '操作人',
							name : 'oppUser',
							anchor : '90%'
						}, {
							xtype : 'datefield',
							width : 200,
							fieldLabel : '操作日期',
							name : 'oppDate',
							anchor : '90%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '操作原因',
							name : 'oppReason',
							anchor : '90%'
						}, {
							xtype : 'hidden',
							width : 200,
							fieldLabel : 'ID',
							name : 'id',
							anchor : '90%'
								
						} ]
					}
					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!addPlanForm.getForm().isValid()) { 
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/probuy-info.json',
								method : 'POST',
								params : addPlanForm.getForm().getFieldValues(),
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									 if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
									 }else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								}
								}
							});
							addPlanWindow.hide();
							addPlanForm.getForm().reset();
						}

					}, {
						text : '取  消',
						handler : function() {
							addPlanWindow.hide();
						}
					} ]
				}

				]

			});

			var search_cust_add1 = new Ext.ux.form.CustomerQueryField({ 
				fieldLabel : '客户名称', 
				labelStyle: 'text-align:right;',
				labelWidth : 100,
				name : 'custName',
				id:'add_aimCustomerName1',
				 editable : false,
				 allowBlank:false,//不允许为空
                 blankText:"不能为空，请填写",
				singleSelected:true,
 				anchor : '90%',
 				callback :function(){
 						var cust_id = null;
								var cust_name = null;
								var certType ='';
								var certNum = '';
								cust_name = Ext.getCmp('add_aimCustomerName1').getValue();
								if (cust_name != null && cust_name != '') {
									cust_id = Ext.getCmp('add_aimCustomerName1').customerId.aId[0];
									certType = Ext.getCmp('add_aimCustomerName1').certType.certtyp[0];
									certNum = Ext.getCmp('add_aimCustomerName1').certNum.certnum[0];
									editBasePlanForm.getForm().findField('custId').setValue(cust_id);
									editBasePlanForm.getForm().findField('certType').setValue(certType);
									editBasePlanForm.getForm().findField('certNum').setValue(certNum);
								}
 				}
			});
			// 修改基本信息展示的form
			var editBasePlanForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 480,
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
						items : [ search_cust_add1, {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '客户编号',
							readOnly:true,
							name : 'custId',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [  {
							name : 'certType',
							xtype : 'textfield',
							fieldLabel : '证件类型',
							readOnly:true,
							width : '100',
							anchor : '90%',
							allowBlank : false
						},{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '证件号码',
							readOnly:true,
							name : 'certNum',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ /*{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '产品名称',
							name : 'productName',
							anchor : '90%'
						}, */{
							store : prodStore,
							id:'prodname1',
							xtype : 'combo', 
							resizable : true,
							name : 'productName',
							hiddenName : 'productName',
							fieldLabel : '产品名称',
							valueField : 'PROD_NAME',
							displayField : 'PROD_NAME',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							editable:true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							anchor : '90%'
					},{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '购买金额（元）',
							name : 'buyAmt',
							anchor : '90%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '产品编号',
							name : 'productNo',
							id:'prodid1',
							readOnly:true,
							anchor : '90%'
						},{
							xtype : 'textfield',
							width : 200,
							fieldLabel : '操作类型',
							name : 'oppType',
							anchor : '90%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '操作人',
							name : 'oppUser',
							anchor : '90%'
						}, {
							xtype : 'datefield',
							width : 200,
							fieldLabel : '操作日期',
							name : 'oppDate',
							format : 'Y-m-d',
							anchor : '90%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							width : 200,
							fieldLabel : '操作原因',
							name : 'oppReason',
							anchor : '90%'
						},{
							xtype : 'hidden',
							width : 200,
							fieldLabel : 'ID',
							name : 'id',
							anchor : '90%'
								
						} ]
					}
					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					buttons : [

					{

						text : '保  存',
						handler : function() {
							if(!editBasePlanForm.getForm().isValid()) { 
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/probuy-info.json',
								method : 'POST',
								params : editBasePlanForm.getForm().getFieldValues(),
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									store.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
									} else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
									}
							});
							editPlanWindow.hide();
						}

					}, {
						text : '取  消',
						handler : function() {
							editPlanWindow.hide();
						}
					} ]
				}

				]

			});

			Ext.getCmp('prodname1').addListener('select',function(){
//				alert(prodStore.find('PROD_NAME',Ext.getCmp('prodname1').getValue()));
				var i = prodStore.find('PROD_NAME',Ext.getCmp('prodname1').getValue());
				if(i!=-1){
//					alert(prodStore.getAt(i).get('PRODUCT_ID'));
					Ext.getCmp('prodid1').setValue(prodStore.getAt(i).get('PRODUCT_ID'));
				}
			});
			

			Ext.getCmp('prodname2').addListener('select',function(){
				var i = prodStore.find('PROD_NAME',Ext.getCmp('prodname2').getValue());
				if(i!=-1){
					Ext.getCmp('prodid2').setValue(prodStore.getAt(i).get('PRODUCT_ID'));
				}
			});
			
			// 定义新增窗口
			var addPlanWindow = new Ext.Window({
				title : '零售客户产品新增',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 450,
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
				title : '零售客户产品修改',
				plain : true,
				layout : 'fit',
				width : 880,
				height : 470,
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
				items : [ editBasePlanForm ]
			});
			
//			if(__roles.indexOf('1')>=0){
//		        Ext.getCmp('importTemple').setDisabled(false);
//				Ext.getCmp('importbt').setDisabled(false);
//		    }
			
			// 展示新增窗口
			function addInit() {
				addPlanForm.getForm().reset();
				addPlanWindow.show();

			}
			// 展示修改窗口
			function editInit() {
				editPlanWindow.show();
			}
			
			
			
			var view = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [ {
					layout : 'border',
					items : [searchPanel,listPanel]
				} ]
			});
			store.load({params : {start : 0,limit : parseInt(pagesize_combo.getValue())}});
		})