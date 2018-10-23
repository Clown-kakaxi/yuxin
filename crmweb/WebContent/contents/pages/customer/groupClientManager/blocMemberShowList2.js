var cbid1;
//var appStatusStore = new Ext.data.Store({
//	restful : true,
//	autoLoad : true,
//	proxy : new Ext.data.HttpProxy({
//		url : basepath + '/lookup.json?name=APP_STATUS'
//	}),
//	reader : new Ext.data.JsonReader({
//		root : 'JSON'
//	}, [ 'key', 'value' ])
//});

var appStatusStore = new Ext.data.ArrayStore({
    fields:['myId','displayText'],
    data:[['0','退回'],['1','暂存'],['2','待审批'],['3','已审批']]
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

// 定义自动当前页行号
var rownum1 = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

// 复选框
var windowsm1 = new Ext.grid.CheckboxSelectionModel();

// 列模型
var columns = new Ext.grid.ColumnModel([ rownum1,windowsm1, {
	header : 'ID',
	width : 150,
	align : 'center',
	hidden : true,
	dataIndex : 'id',
	sortable : true
}, {
	header : '客户名称',
	width : 80,
	dataIndex : 'custZhName',
	sortable : true
}, {
	header : '组织机构代码',
	width : 95,
	dataIndex : 'custZzdm',
	sortable : true
}, {
	header : '上级单位名称',
	width : 150,
	dataIndex : 'parentCustZhName',
	sortable : true
}, {
	header : '成员类型',
	width : 150,
	dataIndex : 'memberType',
	sortable : true
}, {
	header : '成员关系',
	width : 60,
	dataIndex : 'relationId',
	sortable : true
}, {
	header : '隐藏标识',
	width : 150,
	hidden : true,
	dataIndex : 'cout',
	sortable : true
}, {
	header : '控股比例',
	width : 80,
	dataIndex : 'stockRate',
	align : 'right',
	renderer : percent(false),
	sortable : true
}, {
	header : '备注',
	width : 150,
	dataIndex : 'remark',
	sortable : true
}, {
	header : '审批状态',
	width : 150,
	dataIndex : 'APP_STATUS_ORA',
	sortable : true
}, {
	header : '申请人工号',
	width : 100,
	dataIndex : 'appUserId',
	sortable : true
}, {
	header : '隐藏Id',
	width : 100,
	dataIndex : 'upDateUserId',
	hidden:true,
	sortable : true
}, {
	header : '申请人姓名',
	width : 160,
	dataIndex : 'appUserName',
	sortable : true
}, {
	header : '提交日期',
	width : 90,
	dataIndex : 'submitDate',
	sortable : true
}, {
	header : '审批日期',
	width : 130,
	align : 'center',
	dataIndex : 'appDate',
	sortable : true
}]);

var record = Ext.data.Record.create([ {
	name : 'id',
	mapping : 'ID'
}, {
	name : 'custId',
	mapping : 'CUST_ID'
}, {
	name : 'custZhName',
	mapping : 'CUST_ZH_NAME'
}, {
	name : 'custZzdm',
	mapping : 'CUST_ZZDM'
}, {
	name : 'parentCustZhName',
	mapping : 'PARENT_CUST_ZH_NAME'
}, {
	name : 'hyClass',
	mapping : 'HY_CLASS'
}, {
	name : 'appUserName',
	mapping : 'APP_USER_NAME'
}, {
	name : 'appUserId',
	mapping : 'APP_USER_ID'
}, {
	name : 'isNormalCust',
	mapping : 'IS_NORMAL_CUST'
}, {
	name : 'custScope',
	mapping : 'CUST_SCOPE'
}, {
	name : 'upDateUserId',
	mapping : 'UPDATE_USER_ID'
}, {
	name : 'CUST_SCOPE_GP'
}, {
	name : 'crmScope',
	mapping : 'CRM_SCOPE'
}, {
	name : 'CRM_SCOPE_GP'
}, {
	name : 'taxCard',
	mapping : 'TAX_CARD'
}, {
	name : 'cout',
	mapping : 'COUT'
}, {
	name : 'wkLinceseNo',
	mapping : 'WK_LINCESE_NO'
}, {
	name : 'memberType',
	mapping : 'MEMBER_TYPE_ORA'
}, {
	name : 'relationId',
	mapping : 'RELATION_ID_ORA'
}, {
	name : 'stockRate',
	mapping : 'STOCK_RATE'
}, {
	name : 'remark',
	mapping : 'REMARK'
}, {
	name : 'appStatus',
	mapping : 'APP_STATUS'
},{
	name : 'APP_STATUS_ORA'
	
}, {
	name : 'submitDate',
	mapping : 'SUBMIT_DATE'
}, {
	name : 'appDate',
	mapping : 'APP_DATE'
} ]);

var store = new Ext.data.Store({
	id : 'showclinetInfo',
	restful : true,
	proxy : new Ext.data.HttpProxy({
//		url : basepath + '/groupMemberInfoShowQuery.json?groupNo=' + groupNo&'app_t='+__units,
		url : basepath + '/groupMemberInfoShowQuery.json?groupNo=' + groupNo+__units,
		params : {
			'__units' : __units
		}
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'id',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
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
				[ 1000, '1000条/页' ] ]
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
	bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload({
		params : {
			start : 0,
			limit : bbar.pageSize,
			'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
			'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
			'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
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

// 修改基本信息展示的form
var appGroupInfoForm = new Ext.form.FormPanel(
		{
			labelWidth : 80,
			height : 100,
			frame : true,
			region : 'center',
			autoScroll : true,
			buttonAlign : "center",
			items : [{ 
				layout : 'column',
				items : [{
								columnWidth : .33,
								layout : 'form',
								items : [{
											columnWidth:.25,
											xtype:'textfield',
											readOnly : true,
											name : 'id',
											hidden : true,
											triggerAction:'all',
											anchor:'90%',
											fieldLabel : 'id'
										}]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [{
											columnWidth:.25,
											xtype:'textfield',
											readOnly : true,
											name : 'testt',
											id : 'testt',
											hidden : true,
											triggerAction:'all',
											anchor:'90%',
											fieldLabel : 'id'
										}]
							}
							]
			}],
			buttons : [
					{
						text : '审批通过',
						handler : function() {
									var appStatus = appGroupInfoForm.getForm().getFieldValues().testt;
									var id = appGroupInfoForm.getForm().getFieldValues().id;
//									if ( __units == "00001"){
										Ext.Ajax
												.request({
													url : basepath
															+ '/GroupMemberForAppAction.json',
													method : 'POST',
													params : {
														cbid : cbid1,
														appStatus : appStatus,
														'operate':'app'
													},
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : checkResult,
													failure : checkResult
												});
										appGroupInfoForm.getForm().reset();
										appGroupInfoWindow.hide();

										function checkResult(response) {
											var resultArray = Ext.util.JSON
													.decode(response.status);
											var resultError = response.responseText;
											if ((resultArray == 200 || resultArray == 201)
													&& resultError == '') {
												Ext.Msg.alert('提示', '审批成功');
												Ext.getCmp("blocMemberTree").root.reload();// 在移除时实现树的刷新
												store.reload({
													params : {
														start : 0,
														limit : bbar.pageSize,
														'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
														'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
														'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
													}
												});
											} else{
												if(resultArray == 403){
													Ext.Msg.alert('提示', response.responseText);
											      }
												else {
												Ext.Msg.alert('提示',
														'操作失败,失败原因:'
																+ resultError);
												Ext.getCmp("blocMemberTree").root.reload();// 在移除时实现树的刷新
												store.reload({
													params : {
														start : 0,
														limit : bbar.pageSize,
														'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
														'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
														'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
													}
												});
											}
										}
										}
										;
//									} else {
//										appGroupInfoWindow.hide();
//										Ext.Msg.alert("系统提示","对不起，您不是总行人员，没有操作权限！");
//									}
								
							
						}

					},
					{
						text : '退  回',
						handler : function() {
							var appStatus = appGroupInfoForm.getForm().getFieldValues().appStatus;
							var id = appGroupInfoForm.getForm().getFieldValues().id;
							var remark = appGroupInfoForm.getForm().getFieldValues().remark;
//							if ( __units == "00001"){
								Ext.Ajax.request({
									url : basepath + '/groupMemberRemoveAction.json?a=2',
									method : 'POST',
									params : {	
										cbid : cbid1,
										'operate':'refuse',
										remark : remark,
										id : id
									},
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : checkResult,
									failure : checkResult
								});
								appGroupInfoWindow.hide();

								function checkResult(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									var resultError = response.responseText;
									if ((resultArray == 200 || resultArray == 201)
											&& resultError == '') {
										Ext.Msg.alert('提示', '操作成功');
										Ext.getCmp("blocMemberTree").root.reload();// 在移除时实现树的刷新
										store.reload({
											params : {
												start : 0,
												limit : bbar.pageSize,
												'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
												'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
												'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
											}
										});
									}else{
										if(resultArray == 403)
										{
											Ext.Msg.alert('提示', response.responseText);
									    }
										else {
										Ext.Msg.alert('提示', '操作失败,失败原因:'+ resultError);
										Ext.getCmp("blocMemberTree").root.reload();// 在移除时实现树的刷新
										store.reload({
											params : {
												start : 0,
												limit : bbar.pageSize,
												'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
												'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
												'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
											}
										});
									}
								}
								}
								;
//							}else{
//								appGroupInfoWindow.hide();
//								Ext.Msg.alert("系统提示","对不起，您不是总行人员，没有操作权限！");
//							}
						}
					}, {
						text : '取  消',
						handler : function() {
							appGroupInfoWindow.hide();
						}
					} ]

		});

// 集团客户审批from
var appGroupInfoPanel = new Ext.Panel({
	labelWidth : 80,
	height : 100,
	layout : 'fit',
	autoScroll : true,
	buttonAlign : "center",
	items : [ appGroupInfoForm ]
});

// 定义集团客户审批窗口
var appGroupInfoWindow = new Ext.Window({
	title : '集团客户审批意见',
	plain : true,
	layout : 'fit',
	width : 300,
	height : 100,
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
	items : [ appGroupInfoPanel ]
});

// 展示修改窗口
function editInit() {
	appGroupInfoWindow.show();
}

// 列表
var listPanel = new Ext.grid.GridPanel(
		{
			store : store,
			frame : true,
			cm : columns,
			sm:windowsm1,
			stripeRows : true,
			height : 300,
			width : 1140,
			region : 'center',
			frame : true,
			tbar : [
					{
						text : '移出集团',
						id:'removefromgroup',
						handler : function() {
							var  _record = listPanel.getSelectionModel().getSelected();
							if (!_record) {
							    Ext.Msg.alert("系统提醒",'请选择一条记录');
							} else {
								var checkedNodes = listPanel.getSelectionModel().selections.items;
								for(var i=0;i<checkedNodes.length;i++)
				    			{
									var cout=checkedNodes[i].data.cout;
									if (cout > 0) {
										Ext.Msg.alert("系统提醒", "请先删除该客户下地子节点！");
										cout = 0;
										store.reload({
											params : {
												start : 0,
												limit : bbar.pageSize,
												'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
												'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
												'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
											}
										});
										return false;
									}
				    			}
//								var cout = listPanel.getSelectionModel().selections.items[0].data.cout;
//								alert(cout);
								
								Ext.Msg.confirm('提示','确定移出集团么?',function(buttonId){
                                    if(buttonId.toLowerCase() == "no")
                                    {
                                        return;             
                                    }
									
									var json={'id':[]};
									for(var i=0;i<checkedNodes.length;i++)
					    			{
					    				json.id.push(checkedNodes[i].data.id);
					    			}
									
									var selectRe;
									Ext.Ajax.request({
//										url : basepath+ '/groupMemberRemoveAction/'+ selectRe.get('id'),
//										method : 'DELETE',
										
										url:basepath+'/groupMemberRemoveAction.json',
			                            method: 'POST',
			                            params : {
			    							cbid:Ext.encode(json),
			    							'operate':'delete'
			    						},
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : checkResult,
										failure : checkResult
									});
									function checkResult(response) {
										var resultArray = Ext.util.JSON
												.decode(response.status);
										var resultError = response.responseText;
										if ((resultArray == 200 || resultArray == 201|| resultArray == 404)){
											Ext.Msg.alert('提示', '操作成功');
											Ext.getCmp("blocMemberTree").root
													.reload();// 在移除时实现树的刷新
											store.reload({
												params : {
													start : 0,
													limit : bbar.pageSize,
													'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
													'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
													'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
												}
											});
										} else {
											Ext.Msg.alert('提示', '操作失败,失败原因:'
													+ resultError);
											store.reload({
												params : {
													start : 0,
													limit : bbar.pageSize,
													'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
													'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
													'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
												}
											});
										}
									};
									});
							}
						}
					},
					'-',
					{
						text : '提交申请',
						iconCls:'importIconCss',
						handler : function() {
							var  _record = listPanel.getSelectionModel().getSelected();
							if (!_record) {
							    Ext.Msg.alert("系统提醒",'请选择一条记录');
							} else {
							var checkedNodes = listPanel.getSelectionModel().selections.items;
							for(var i=0;i<checkedNodes.length;i++)
			    			{
								var tempCheck=checkedNodes[i].data.upDateUserId;
								var cout=checkedNodes[i].data.APP_STATUS_ORA;
								if (tempCheck !=__userId) {
									Ext.Msg.alert("系统提醒", "存在不是您暂存的记录！");
									store.reload({
										params : {
											start : 0,
											limit : bbar.pageSize,
											'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
											'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
											'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
										}
									});
									return false;
								}
								if (!(cout == "暂存"||cout=="退回")) {
									Ext.Msg.alert("系统提醒", "存在非暂存或非退回记录！");
									store.reload({
										params : {
											start : 0,
											limit : bbar.pageSize,
											'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
											'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
											'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
										}
									});
									return false;
								}
									if (confirm("确定提交吗?")) {
										
										var json={'id':[]};
										for(var i=0;i<checkedNodes.length;i++)
						    			{
						    				json.id.push(checkedNodes[i].data.id);
						    			}
										Ext.Ajax.request({
													url : basepath+ '/groupMemberRemoveAction.json?a=1',
													method : 'POST',
													// params:editGroupInfoForm.getForm().getFieldValues(),
													 params : {
							    							cbid:Ext.encode(json),
							    							'operate':'post'
							    						},
													// form :
													// editGroupInfoForm.getForm().id,
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success : checkResult,
													failure : checkResult
												});
										function checkResult(response) {
											var resultArray = Ext.util.JSON
													.decode(response.status);
											var resultError = response.responseText;
											debugger;
											if ((resultArray == 200 || resultArray == 201)
													&& resultError == '') {
												Ext.Msg.alert('提示','用户已加入到申请队列，请等待审批！');
												Ext.getCmp("blocMemberTree").root.reload();// 在移除时实现树的刷新
												store.reload({
													params : {
														start : 0,
														limit : bbar.pageSize,
														'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
														'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
														'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
													}
												});
											} else {
												Ext.Msg.alert('提示','操作失败,失败原因:'
																+ resultError);
												store.reload({
													params : {
														start : 0,
														limit : bbar.pageSize,
														'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
														'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
														'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
													}
												});
											}
										};
									}
							}
						}
						}
					},
					'-',
					{
						id :'app_sub',
						text : '审批',
						iconCls:'shenpiIconCss',
						handler : function() {
							var selectLength = listPanel.getSelectionModel()
									.getSelections().length;
							var selectRe = listPanel.getSelectionModel()
									.getSelections()[0];
							if (selectLength < 1) {
								Ext.Msg.alert("系统提醒", "请选择一条记录！");
							} else {
								var checkedNodes = listPanel.getSelectionModel().selections.items;
								for(var i=0;i<checkedNodes.length;i++)
				    			{
									var tempAppStatus=checkedNodes[i].data.appStatus;
//									alert(tempAppStatus);
								if (tempAppStatus >= 3) {
									Ext.Msg.alert("提醒", "存在【通过审批】项，请重新选择！");
									return false;
								}
									if (tempAppStatus <= 0) {
										Ext.Msg.alert("提醒","存在【退回】项，请重新选择！");
										return false;
									}
										if(tempAppStatus==1){
											Ext.Msg.alert("提醒","存在【暂存】项，尚未提交申请！");
											return false;
										}
				    			}
								
								var json={'id':[]};
								for(var i=0;i<checkedNodes.length;i++)
				    			{
				    				json.id.push(checkedNodes[i].data.id);
				    			}
								
								cbid1=Ext.encode(json);
//								alert(cbid1);
								var tempAppStatus = listPanel.getSelectionModel().selections.items[0].data.appStatus;
								Ext.getCmp("testt").setValue(tempAppStatus);
								appGroupInfoForm.getForm().loadRecord(
										selectRe);
								document.getElementById('idStr').value = selectRe.data.id;
								editInit();
							}
						}
					},'->',
					new Ext.form.Label({
						text:'组织机构代码:'
					}),
					{
						xtype:'textfield',
						name:'CUST_ZZDM',
						width:100,
						id:'CUST_ZZDM'
		    		},
					new Ext.form.Label({
						text:'客户名称:'
					}),
					{
						xtype:'textfield',
						name:'CUST_NAME',
						width:100,
						id:'CUST_NAME'
		    		},
					new Ext.form.Label({
						text:'审批状态:'
					}),	{	
                        name: 'APP_STATUS',
                        id : 'APP_STATUS',
                        forceSelection : true,
						resizable:true,
                        xtype:'combo',
                        width:100,
                        labelStyle: 'text-align:right;',
                        triggerAction:'all',
                        mode:'local',
                        store:appStatusStore,
                        valueField:'myId',
                        displayField:'displayText',
                        emptyText:'请选择',
                        anchor : '90%'
                    },		    
				    {
				    	width:80,
						text : '查询',
						handler : function() {
//							alert(Ext.getCmp("APP_STATUS").getValue());
//						var custZzdm = Ext.getCmp("CUST_ZZDM").getValue();
//						var custName = Ext.getCmp("CUST_NAME").getValue();
//						var appStatus = Ext.getCmp("APP_STATUS").getValue();
						
						store.load({
							params:{
								start : 0,
								limit : bbar.pageSize,
								'CUST_ZZDM':Ext.getCmp("CUST_ZZDM").getValue(),
								'CUST_NAME':Ext.getCmp("CUST_NAME").getValue(),
								'APP_STATUS':Ext.getCmp("APP_STATUS").getValue()
							}
						});
				    }
				} ],
			bbar : bbar,// 分页工具栏
			viewConfig : {
			// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
listPanel
		.on(
				'rowdblclick',
				function(grid, rowIndex, event) {
					var checkedNodes = listPanel.getSelectionModel().selections.items;
					if (checkedNodes.length == 0) {
						Ext.Msg.alert('提示', '未选择任何客户');
						return;
					}
					var custId = grid.getSelectionModel().selections.items[0].data.custId;
					window.location.href = '../customerManager/customerBaseInformation.jsp?customerId='+ custId;
				});
//
//if(__units=="10001"||__units=="00001"){
//
//    Ext.getCmp('app_sub').setDisabled(false);
//};
    if(__units!=1){
        
        Ext.getCmp('removefromgroup').hidden=true;
    }   
