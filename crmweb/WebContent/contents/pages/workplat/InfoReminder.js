Ext.onReady(function() {
			Ext.QuickTips.init(); 
			var groupID = null;
			var pageSize = 15;
//			var custGradeStore = new Ext.data.Store({
//				restful : true,
//				autoLoad : true,
//				proxy : new Ext.data.HttpProxy({
//					url : basepath + '/lookup.json?name=CUST_GRADE'
//				}),
//				reader : new Ext.data.JsonReader({
//					root : 'JSON'
//				}, [ 'key', 'value' ])
//			});
			
	var userRecord = Ext.data.Record.create([{
												name : 'ID',
												mapping : 'ID'
											}, {
												name : 'userName',
												mapping : 'USER_NAME'
											}, {
												name : 'appID',
												mapping : 'APP_ID'
											}, {
												name : 'accountName',
												mapping : 'ACCOUNT_NAME'
											}, {
												name : 'userState',
												mapping : 'USER_STATE'
											}, {
												name : 'accountID',
												mapping : 'ACCOUNT_ID'
											}, {
												name : 'userCode',
												mapping : 'USER_CODE'
											},{
												name : 'deadline',
												mapping : 'DEADLINE'
											}, {
												name : 'orgID',
												mapping : 'ORG_ID'
											}]);
			var custRecord = Ext.data.Record.create([ 
				                               	    {
				                               		name : 'custId',
				                               		mapping : 'CUST_ID'
				                               	    },{
				                               		name : 'custName',
				                               		mapping : 'CUST_NAME'
				                               		},{
					                               		name : 'custManagerId',
					                               		mapping : 'CUST_MANAGER_ID'
					                               	},{
				                               		name : 'sex',
				                               		mapping : 'SEX'
				                               		},{
				                               		name : 'email',
				                               		mapping : 'EMAIL'
				                               		},{
				                               		name : 'mobileNo1',
				                               		mapping : 'MOBILE_NO1'
				                               		},{
				                               		name : 'address',
				                               		mapping : 'ADDRESS'
				                               		},{
				                               		name : 'birthday',
				                               		mapping : 'BIRTHDAY'
				                               		},{
				                               		name : 'custSource',
				                               		mapping : 'CUST_SOURCE'
				                               		},{
				                               		name : 'homeTel',
				                               		mapping : 'HOME_TEL'
				                               		},{
				                               		name : 'unitAddr',
				                               		mapping : 'UNIT_ADDR'
				                               		}
				                               	    ]);
			
			var relRecord = Ext.data.Record.create( [ {
													name : 'relId',
													mapping : 'REL_ID'
												},{
													name : 'userName',
													mapping : 'USER_NAME'
												}
												 , {
													name : 'groupId',
													mapping : 'GROUP_ID'
												}, {
													name : 'userId',
													mapping : 'USER_ID'
												}, {
													name : 'userType',
													mapping : 'USER_TYPE'
												},  {
													name : 'infoEmail',
													mapping : 'INFO_EMAIL'
												}, {
													name : 'infoTel',
													mapping : 'INFO_TEL'
												}
												]);
			
			var userReader = new Ext.data.JsonReader({
           		successProperty : 'success',
           		idProperty : 'ID',
           		messageProperty : 'message',
           		root : 'json.data',
           		totalProperty : 'json.count'
           	}, userRecord);
			
			var custReader = new Ext.data.JsonReader({
           		successProperty : 'success',
           		idProperty : 'CUST_ID',
           		messageProperty : 'message',
           		root : 'json.data',
           		totalProperty : 'json.count'
           	}, custRecord);
			
			var relReader = new Ext.data.JsonReader({
           		successProperty : 'success',
           		idProperty : 'REL_ID',
           		messageProperty : 'message',
           		root : 'json.data',
           		totalProperty : 'json.count'
           	}, relRecord);
			
			var userStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/AdminUserQuery.json',
					method: 'GET'
				}),
				reader : userReader
			});
			var custStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/GroupRelCustQuery.json',
					method: 'GET'
				}),
				reader :custReader
			});
			
			var relStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/CrmfwkGroupRelQuery.json',
					method: 'GET'
				}),
				reader :relReader
			});
			
			var sm = new Ext.grid.CheckboxSelectionModel();
			var sm1 = new Ext.grid.CheckboxSelectionModel();
			var sm2 = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
						header : '编号',
						width : 40
					});
			// 定义列模型
			var u_cm = new Ext.grid.ColumnModel([rownum,sm1, 
			           {
						dataIndex : 'ID', // 数据索引:和Store模型对应
						sortable : true,
						hidden : true,
						width : 0
				    }, {
						header : '用户名称',
						dataIndex : 'userName',
						sortable : true,
						width : 150
					},{
						header : '机构编号',
						dataIndex : 'orgID',
						sortable : true,
						width : 150
					},{
						header : '账号名称',
						dataIndex : 'accountName',
						sortable : true,
						width : 150
					}
					]);
			var c_cm = new Ext.grid.ColumnModel([rownum,sm, 
			            			           {
			            						dataIndex : 'custId', // 数据索引:和Store模型对应
			            						sortable : true,
			            						hidden : true,
			            						width : 80
			            				    }, {
			            						header : '客户名称',
			            						dataIndex : 'custName',
			            						sortable : true,
			            						width : 160
			            					},{
				            						header :'邮箱',
				            						dataIndex : 'email',
				            						sortable : true,
				            						width : 155
				            					},{
				            						header : '手机号码',
				            						dataIndex :'mobileNo1',
				            						sortable : true,
				            						width :155
				            					},{
				            						header : '客户生日',
				            						dataIndex :'birthday',
				            						hidden :true,
				            						sortable : true,
				            						width : 80
				            					},{
				            						header :'机构编号',
				            						hidden :true,
				            						dataIndex :'custManagerId',
				            						sortable : true,
				            						width : 80
				            					}
			            					]);
			var grouprel_cm = new Ext.grid.ColumnModel([rownum,sm2, 
				            			           {
				            						dataIndex : 'relId', // 数据索引:和Store模型对应
				            						sortable : true,
				            						hidden : true,
				            						width : 80
				            				    }, {
				            						header : '组员名称',
				            						dataIndex : 'userName',
				            						sortable : true,
				            						width : 100
				            					},{
				            						header : '邮箱',
				            						dataIndex :'infoEmail',
				            						sortable : true,
				            						width : 116
				            					},{
				            						header : '手机号码',
				            						dataIndex : 'infoTel',
				            						sortable : true,
				            						width : 116
				            					},{
				            						hidden :true,
				            						dataIndex : 'groupId'
				            					},{
				            						hidden :true,
				            						dataIndex : 'userId'
				            					},{
				            						hidden :true,
				            						dataIndex : 'userType'
				            					}
				            					]);
			
			    
			    var queryCustForm = new Ext.FormPanel({
				id :'queryUserForm',
				title : '客户查询',
				frame : true,
				width : 530,
				height:100,
				border : false,
				labelAlign : 'right',
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .50,
						labelWidth : 100, // 标签宽度
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'CUST_NAME',
							anchor : '95%'
						} ]
					},{
						columnWidth :.5,
						labelWidth :100,
						layout :'form',
						items :[{
							xtype : 'textfield',
							fieldLabel : '机构编号',
							name : 'CUST_MANAGER_ID',
							anchor : '95%'
						}]
					}]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
					var cust = queryCustForm.getForm()
					.getValues(false);
					custStore.baseParams = {
							"condition" : Ext.encode(cust)
					},
					custStore.reload({
							params : {
								start : 0,
								limit : pageSize
							}
						});

					},
					width : 80
				}, {
					text : '重置',
					handler : function() {
							queryCustForm.getForm().reset();
					}

				} ]
			});
			var adminUserForm = new Ext.FormPanel({
				id :'adminUserForm',
				title : '用户查询',
				frame : true,
				width : 530,
				height:100,
				border : false,
				labelAlign : 'right',
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .50,
						labelWidth : 100, // 标签宽度
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '用户名称',
							name : 'USER_NAME',
							anchor : '95%'
						}]
					},{
					    columnWidth :0.5,
					    labelWidth : 100,
					    layout :'form',
					    items :[{
							xtype : 'textfield',
							fieldLabel : '机构编号',
							name : 'ORG_ID',
							anchor : '95%'
						} ]
					}]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
					var adminUser = adminUserForm.getForm()
					.getValues(false);
					userStore.baseParams = {
							"condition" : Ext.encode(adminUser)
					},
					userStore.reload({
							params : {
								start : 0,
								limit : pageSize
							}
						});

					},
					width : 80
				}, {
					text : '重置',
					handler : function() {
							adminUserForm.getForm().reset();
					}

				} ]
			});
			
			var usergrid = new Ext.grid.GridPanel({
				id :'usergrid',
				width:530,
				height:document.getElementsByTagName("body")[0].clientHeight-100-30,
			    store:userStore,
			    sm: sm1,
			    cm : u_cm,
			    tbar : [{
			    	text : '归入群组',
			    	handler : function(){
			    	 	var usersArray = usergrid.getSelectionModel().getSelections();
			    	 	if(usersArray.length<1){
			    	 		Ext.Msg.alert('提醒框','请选择记录！');
			    	 	}else{
			    	 		var record = null;
			    	 		var idStr='';
			    	 		var userType = 1;
			    	 		var rowRel = null;
			    	 		var rowCount = Ext.getCmp('rightPanel').getStore().getCount();
			    	 		for (var i = 0; i < usersArray.length; i++) {
								record = usersArray[i];
			    	 			var flag = true;
								if (rowCount > 0) {
									for (var k = 0; k < rowCount; k++) {
										rowRel = rightPanel.getStore().getAt(k);
										if (rowRel.data.userId == record.data.ID && rowRel.data.userType == '1') {
											flag = false;
											break;
										}
									}
										if (flag) {
											idStr += record.data.ID;
											if (i != usersArray.length - 1) {
												idStr += ',';
											}
										}
								} else {
									idStr += record.data.ID;
									if (i != usersArray.length - 1) {
										idStr += ',';
									}
								}
							}
							
							if(idStr.charAt(idStr.length-1)==','){
									idStr=idStr.substring(0,idStr.length-1) 
							}
							if(idStr.charAt(0)==','){
									idStr=idStr.substring(1,idStr.length) 
							}
								saveGroupRel(idStr,userType,groupID);
			    	 	}
			    	}
			    }],
			    bbar: new Ext.PagingToolbar({
					pageSize : pageSize,
					store : userStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					emptyMsg : "没有符合条件的记录"
				})
			});
			
				//用户/客户归入群组方法
			saveGroupRel = function(idStr,userType,groupid){
				if(idStr == ''){
					Ext.Msg.alert('提醒框','所选项在群组中已存在，请检查！')
				}else{
						Ext.Ajax.request({
							url : basepath + '/CrmfwkGroupRel/1'+'/batchCreate.json?idStr='+idStr+'&userType='+userType+'&groupid='+groupid,
							scope : this,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								relStore.reload();
								if(userType == '1'){
									userStore.reload();
								}else{
									custStore.reload();
								}
							},
							failure : function() {
								Ext.Msg.alert('提示', '操作失败');
								relStore.reload();
									if(userType == '1'){
									userStore.reload();
								}else{
									custStore.reload();
								}
							}
						});
				}
			    };
			    
			var custgrid = new Ext.grid.GridPanel({
				id :'custgrid',
				width:530,
				height:document.getElementsByTagName("body")[0].clientHeight-100-30,
			    store:custStore,
			    sm: sm,
			    cm : c_cm,
			    tbar : [{
			    	text : '归入群组',
			    	handler : function(){
			    	 	var custArray = custgrid.getSelectionModel().getSelections();
			    	 	if(custArray.length<1){
			    	 		Ext.Msg.alert('提醒框','请选择记录！');
			    	 	}else{
			    	 		var record = null;
			    	 		var idStr='';
			    	 		var userType = 2;
			    	 		var rowRel = null;
			    	 		var rowCount = Ext.getCmp('rightPanel').getStore().getCount();
			    	 		for (var i = 0; i < custArray.length; i++) {
								record = custArray[i];
								var flag = true;
								if(rowCount>0){
								for (var k = 0; k < rowCount; k++) {
									rowRel = rightPanel.getStore().getAt(k);
									if (rowRel.data.userId == record.data.custId && rowRel.data.userType == '2') {
										flag = false;
										break;
									}
								}
								if (flag) {
									idStr += record.data.custId;
									if (i != custArray.length - 1) {
										idStr += ',';
									}
								}
								}else{
									idStr += record.data.custId;
									if (i != custArray.length - 1) {
										idStr += ',';
									}
								}
							}
							
							if(idStr.charAt(idStr.length-1)==','){
									idStr=idStr.substring(0,idStr.length-1) 
							}
							if(idStr.charAt(0)==','){
									idStr=idStr.substring(1,idStr.length) 
							}
			    	 		 saveGroupRel(idStr,userType,groupID);
			    	 	}
			    	
			    	
			    	}
			    }],
			    bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					store : custStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					emptyMsg : "没有符合条件的记录"
				})
			});
			
			
			var tabPanel = new Ext.TabPanel({
				width :540,
	            region: 'west',
	            activeTab: 0,
	            defaults:{autoScroll:true},
	            items:[{
	            	xtype:'panel',
	            	title:'选择用户',
	            	border :false,
	            	items:[{ 
						layout : 'form',
						border : false,
						items : [ adminUserForm, usergrid]
	            	}]
	            }
	            ,{xtype :'panel',
	            	title:'选择客户',
	            	border :false,
	            	items:[{
					layout : 'form',
					border : false,
					items : [ queryCustForm, custgrid]
            	}]
	        }
	            ]});
			
			
			var rightPanel =  new Ext.grid.GridPanel({
				id :'rightPanel',
				width :document.getElementsByTagName("body")[0].clientWidth-540,
				height:document.getElementsByTagName("body")[0].clientHeight-1,
				region :'center',
				autoScroll: true, 
				title:'群组成员列表',
			    store:relStore,
			    sm: sm2,
			    cm : grouprel_cm,
			    tbar:[{
			    	text : '移除群组',
			    	handler : function(){
			 var selectLength = rightPanel.getSelectionModel().getSelections().length;
		        if(selectLength < 1){
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
					for(var i = 0; i<selectLength;i++){
						selectRe = rightPanel.getSelectionModel().getSelections()[i];
						tempId = selectRe.data.relId;
						idStr += tempId;
						if( i != selectLength-1)
							idStr += ',';
					}
					Ext.Ajax.request({
						url : basepath+'/CrmfwkGroupRel/'
								+tempId+'/batchDestroy.json?idStr='+idStr,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
//							rightPanel.getSelectionModel().getSelections().each(function(r){
//							   relStore.remove(r);
//							});
							Ext.Msg.alert('提示', '操作成功');
							relStore.reload();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					});
					
					});
		            }
				
			    		
			    	}
			    }],
			    bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					store : relStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					emptyMsg : "没有符合条件的记录"
				})
			});
			
			var groupPanel = new Ext.Panel( {
			id : 'groupPanel',
			layout :'border',
			region :'fit',
			stripeRows : true,
			width :document.getElementsByTagName("body")[0].clientWidth-1,
			height:document.getElementsByTagName("body")[0].clientHeight-1,
			items : [ tabPanel,rightPanel]
		});

		var group_win = new Ext.Window({
							plain : true,
							layout : 'fit',
							resizable : true,
							draggable : true,
							closable : true,
							closeAction : 'hide',
							modal : true, // 模态窗口
							shadow : true,
							loadMask : true,
							maximizable : true,
							collapsible : true,
							titleCollapse : true,
							border : false,
							width : 950,
							height : 500,
							title : '群组设置',
							items : [groupPanel]
						});
			
			// 最终展现的panel
			var listPanel = new Mis.Ext.CrudPanel( {
				id : "listPanel",
				region :'center',
				title : "群组管理",
			    stUrl : basepath + '/CrmfwkGroupQuery.json',
				addUrl : basepath + '/CrmfwkGroup.json',
				updateUrl : basepath + '/CrmfwkGroup.json',
				deUrl : basepath + '/CrmfwkGroup!batchDestroy.json',
				primary : "groupId",
				checkbox : true,
				winHeight : 400,
				winWidth : 800,
				seFormHeight : 80,
				pageSize : 20,
				gclms : [ {
					name : 'groupName',
					header: '群组名称',
					mapping : 'GROUP_NAME'
				}, {
					name : 'createUser',
					header: '创建用户',
					mapping : 'CREATE_USER'
				}, {
					name : 'createDate',
					header: '创建时间',
					mapping : 'CREATE_DATE'
				},{
					name : 'updateUser',
					header: '修改用户',
					mapping : 'UPDATE_USER'
				},{
					name : 'updateDate',
					header: '修改时间',
					mapping : 'UPDATE_DATE'
				}, {
					name : 'remark',
					header: '备注',
					mapping : 'REMARK'
				},{
					name : 'groupType',
					mapping : 'GROUP_TYPE'
				},{
					name : 'groupId',
					mapping : 'GROUP_ID'
				}],
				pagesize : 20,
				// 查询字段
				selectItems : {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						defaultType : 'textfield',
						border : false,
						items : [ {
							name : 'GROUP_NAME',
							xtype : 'textfield',
							fieldLabel : '群组名称',
							width : '100',
							anchor : '90%'
						} ]
					}]
				},
				// from的字段
				fclms : [ {
							layout : 'form',
							columnWidth : .5,
						items : [{
							name : 'groupName',
							fieldLabel : '群组名称',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 100,
							anchor : '90%'
						} ,{
							name : 'remark',
							fieldLabel : '备注',
							xtype : 'textarea',
							width : 100,
							allowBlank : true,
							maxLength : 200,
							anchor : '90%'
						}, {
							name : 'groupId',
							xtype : 'hidden'
					}]
				}],
				
				//扩展按钮
				buts:[{
					text : '群组设置',
					xtype:'button',
					handler : function(){
					var selectLength = listPanel.grid.getSelectionModel().getSelections().length;
					var selectRe = listPanel.grid.getSelectionModel().getSelections()[0];
					if(selectLength!=1){
						Ext.Msg.alert('提示','请选择一条记录!');
					}else{
						var groupId = selectRe.data.groupId;
						    groupID = groupId;
						var condi = {
								"groupId":groupId
						};
						relStore.reload({
						params:{
							start : 0,
							//"groupId" : groupId,
							limit : pageSize,
							'condition':Ext.encode(condi)
					     }
							
				});
						group_win.show();
						custStore.reload({
							params:{
								start : 0,
								limit : pageSize
					     }
						});
						userStore.reload({
							params:{
								start : 0,
								limit : pageSize
					     }
						});
					}
					
					}
				}]
				
			});

			
			// 页面视图
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [{
					layout : 'border',
					items:[listPanel]
				} ]
			});
		});