Ext.onReady(function() {
	Ext.QuickTips.init();
	// 渠道类型下拉框的数据查询
	var pageSize = 20;
	var u_cust_idArr =[];
	var u_manager_idArr =[];
		var typeStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['0', '未读'],['1', '已读']]
			});
		var userTypeStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['0', '用户'],['1', '客户']]
			});
		var boxTypeStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['0', '收信箱'],['1', '发信箱']]
			});	
			var search_cust = new Ext.ux.form.CustomerQueryField({ 
				fieldLabel : '客户名称', 
				labelStyle: 'text-align:right;',
				name : 'RECEIVE_USER_NAME',
				id:'search_cust',
				 editable : false,
				singleSelected:false,
				width : 150,
 				anchor : '90%',
 				callback :function(){
 					u_cust_idArr =[];
 					 u_cust_idArr = search_cust.customerId.aId;
 				}
			});
			var search_view = new Ext.form.FieldSet({
				title : '选择收信人',
//				collapsed : true,
//				width  : 580,
				collapsible : true,
				items:[search_cust,
				       new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '客户经理', 
					id:'search_manager',
					labelStyle: 'text-align:right;',
					name : 'RECEIVE_USER_NAME',
					hiddenName:'manager',
					//searchRoleType:('127,47'),  //指定查询角色属性
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:false,
					anchor : '90%',
					callback: function() {
					u_manager_idArr=[];
					u_manager_idArr=Ext.getCmp("search_manager").hiddenField.getValue().split(",");
					
					} 
					})
				]
			}); 
			var noteForm = new Ext.form.FormPanel({
				id : 'noteForm',
				width : 600,
				height : 400,
				frame : true,
				labelWidth : 100,
				layout : 'form',
				labelAlign : 'right',
				items : [{
							layout : 'column',
							items : [{
										layout : 'form',
										columnWidth : 0.99,
										items : [search_view]
									}]}, {
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '手动添加号码(例:11,22)',
													name : 'RECEIVE_USER_NAME_HAND',
													regex:/^(\d+,?)*$/,
													regexText:'格式有误,例:11,22,33',
//													width  : 180,
													anchor : '90%'
												},{
													xtype : 'textarea',
													hideLabel: true,
													height : 160,
													maxLength : 255,
													maxLengthText : '内容长度过长',
													allowBlank:false,//不允许为空
													blankText:"不能为空，请填写",
													name : 'CONTENT',
													anchor : '90%'
															
												}]
									}]
						});
			
			var addWin = new Ext.Window({
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
							width : 600,
							height : 400,
							buttonAlign : "center",
							title : '编辑新短信',
							items : [noteForm],
							buttons : [{
							text : "立即发送",
							handler :function(){
								var receiveUserName_s='';
								var handTel_s='';
								var	content_s='';
							handTel_s = noteForm.getForm().findField('RECEIVE_USER_NAME_HAND').getValue();
							receiveUserName_s =Ext.getCmp('search_cust').getValue();
							receiveUserName_s += Ext.getCmp('search_manager').getValue();
							content_s = noteForm.getForm().findField('CONTENT').getValue();
							if (!noteForm.form.isValid()) {
								Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
								return;
							}
							if(handTel_s==""&&receiveUserName_s==""){
								Ext.Msg.alert('提醒框','请输入收信人！');
							}else{
								var cust_json ={'custIdArr':[]};
								var manager_json ={'managerIdArr':[]};
									cust_json.custIdArr = u_cust_idArr;
									debugger;
									manager_json.managerIdArr = u_manager_idArr;
									debugger;
					Ext.Ajax.request({
						url : basepath+'/NoteManage!sendNoteNow.json',
						method : 'POST',
						params : {
							"cust_json":Ext.encode(cust_json),
							"manager_json":Ext.encode(manager_json),
							"handTel_s":handTel_s,
							"content_s":content_s
							},
						scope : this,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							content_s ='';
							handTel_s='';
							u_cust_idArr=[];
							u_manager_idArr=[];
							addWin.hide();
							listPanel.grid.getStore().reload();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					});
							}}
						}, {
							text : "定时发送",
							handler : function() {
								var receiveUserName_s = '';
								var handTel_s = '';
								var content_s = '';
								if (!noteForm.form.isValid()) {
									Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
									return;
								}
								var plandate = Ext.getCmp('plandate').getValue();
								var plantime = Ext.getCmp('plantime').getValue();
								var datetime = plandate.format('Y-m-d') + " " + plantime;
								var dt = new Date();
//								dt.format('Y-m-d H:m');
								handTel_s = noteForm.getForm().findField('RECEIVE_USER_NAME_HAND').getValue();
								receiveUserName_s = Ext.getCmp('search_cust').getValue();
								receiveUserName_s += Ext.getCmp('search_manager').getValue();
								content_s = noteForm.getForm().findField('CONTENT').getValue();
								if (handTel_s == "" && receiveUserName_s == "") {
									Ext.Msg.alert('提醒框', '请输入收信人！');
								} else if(plandate==''||plantime==''){
									Ext.Msg.alert('提醒框', '请输入正确的日期和时间！');
								}else {
									var cust_json = {
										'custIdArr' : []
									};
									var manager_json = {
										'managerIdArr' : []
									};
									cust_json.custIdArr = u_cust_idArr;
									manager_json.managerIdArr = u_manager_idArr;
									Ext.Ajax.request({
												url : basepath + '/NoteManage!sendByTime.json',
												method : 'POST',
												params : {
													"cust_json" : Ext.encode(cust_json),
													"manager_json" : Ext.encode(manager_json),
													"handTel_s" : handTel_s,
													"content_s" : content_s,
													"datetime" : datetime
												},
												scope : this,
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													content_s = '';
													handTel_s = '';
													u_cust_idArr=[];
													u_manager_idArr=[];
													addWin.hide();
													listPanel.grid.getStore().reload();
												},
												failure : function(response) {
													Ext.Msg.alert('提示', '操作失败,失败原因:'
																	+ response.responseText);
												}
											});
						}}}, { 
					layout : 'column',
					xtype :'form',
					width : 220,
					height :23,
					items : [ {
						columnWidth : .6,
						layout : 'form',
						labelWidth : 20,
						items : [{
							labelStyle: 'text-align:right;',
							fieldLabel : '日期',
							id: 'plandate',
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							name : 'plandate',
							width :100,
							anchor : '90%'
						}]
					}, {
						columnWidth : .4,
						layout : 'form',
						labelStyle : 'text-align:center',
						labelAlign : 'right',
						labelSeparator : '',
						labelWidth : 20,
						items : [{
							xtype : 'timefield',
							id :'plantime',
							minValue: '00:00',
    						maxValue: '23:59',
							resizable : true,
							fieldLabel : '时间',
							width :68,
							name : 'plantime',
							format : 'H:i',
							editable : false,
							anchor : '85%'
						}]
					} ]
						},{
							text : "重置",
							handler : function(){
							content_s ='';
							handTel_s='';
								noteForm.getForm().reset();
								Ext.getCmp('plandate').setValue('');
								Ext.getCmp('plantime').setValue('');
							},
							scope : this
						}]
						});
						
//			var sendGrid = new Ext.grid.GridPanel({
//				id : 'sendGrid',
//				width : 585,
//				height : 280,
//				store : sendStore,
//				sm : sm,
//				cm : sendCm,
//				tbar : [{
//					text : '添加',
//					handler : function() {
//						u_nameStr ='';
//						var usersArray = sendGrid.getSelectionModel().getSelections();
//						var record = null;
//						var flag =true;
//						for (var i = 0; i < usersArray.length; i++) {
//							flag = true;
//							record = usersArray[i];
//							for(var k=0;k<arr.length;k++){
//							  if((arr[k].data.sendUserId == record.data.sendUserId)&&(arr[k].data.sendUserType == record.data.sendUserType)){
//							  		flag = false;
//							  		break;
//							  }
//							}
//							if(flag){
//							arr.push(record);
//							u_nameStr += record.data.sendUserName;
//								u_nameStr +=';';
//							u_idStr += record.data.sendUserId;
//							u_idStr += ',';
//							u_idStr += record.data.sendUserType;
//							u_idStr += ';';
//							}
//						}
//						var nameStr = noteForm.getForm().findField('RECEIVE_USER_NAME').getValue();
//								if(nameStr ==''){
//									nameStr += u_nameStr;
//								}else{
//									var ch = nameStr.charAt(nameStr.length-1);
//									if(ch ==';'){
//										nameStr += u_nameStr;
//									}else{
//										nameStr +=';';
//										nameStr +=u_nameStr;
//									}
//								}
//						noteForm.getForm().findField('RECEIVE_USER_NAME').setValue(nameStr);
//						userWin.hide();
//					}
//				}],
//				bbar : new Ext.PagingToolbar({
//							pageSize : pageSize,
//							store : sendStore,
//							displayInfo : true,
//							displayMsg : '显示{0}条到{1}条,共{2}条',
//							emptyMsg : "没有符合条件的记录"
//						})
//			});
			 
//			var sendForm = new Ext.form.FormPanel({
//				id :'sendForm',
//				title : '成员查询',
//				frame : true,
//				width : 600,
//				height:90,
//				border : false,
//				labelAlign : 'right',
//				items : [ {
//					layout : 'column',
//					items : [ {
//						columnWidth : .50,
//						labelWidth : 100, // 标签宽度
//						layout : 'form',
//						items : [ {
//							xtype : 'textfield',
//							fieldLabel : '成员名称',
//							name : 'SENDUSERNAME',
//							anchor : '95%'
//						}]
//					},{
//						columnWidth : .50,
//						labelWidth : 100, // 标签宽度
//						layout : 'form',
//						items : [ {
//						store : userTypeStore,
//						xtype : 'combo',
//						resizable : true,
//						fieldLabel : '成员类型',
//						name : 'SENDUSERTYPE',
//						hiddenName : 'SENDUSERTYPE',
//						valueField : 'key',
//						displayField : 'value',
//						mode : 'local',
//						typeAhead : true,
//						forceSelection : true,
//						triggerAction : 'all',
//						emptyText : '--请选择--',
//						selectOnFocus : true,
//						width : '100',
//						anchor : '90%'
//					} ]
//					}]
//				} ],
//				buttonAlign : 'center',
//				buttons : [ {
//					text : '查询',
//					handler : function() {
//					var v_send = sendForm.getForm().getValues(false);
//					sendStore.baseParams = {
//							"condition" : Ext.encode(v_send)
//					},
//					sendStore.reload({
//							params : {
//								start : 0,
//								limit : pageSize
//							}
//						});
//
//					},
//					width : 80
//				}, {
//					text : '重置',
//					handler : function() {
//							queryCustForm.getForm().reset();
//					}
//
//				} ]
//			});
//						var userWin = new Ext.Window({
//							plain : true,
//							layout : 'fit',
//							resizable : false,
//							draggable : true,
//							closable : true,
//							closeAction : 'hide',
//							modal : true, // 模态窗口
//							shadow : true,
//							loadMask : true,
//							maximizable : true,
//							collapsible : true,
//							titleCollapse : true,
//							border : false,
//							width : 600,
//							height : 400,
//							buttonAlign : "center",
//							title : '添加收件人',
//							items : [{
//								layout : 'form',
//								items:[sendForm,sendGrid]
//							}]
//						});
//						
//						userWin.on('hide',function(){
//							sm.clearSelections();
//						});
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "短信平台",
			stUrl : basepath + '/NoteManagerQuery.json?sendType=1',
			primary : "MESSAGE_ID",
			checkbox : true,
			winHeight : 350,
			winWidth : 500,
			seFormHeight: 100,
			
			gclms : [ {
				name : 'MESSAGE_ID',
				mapping : 'MESSAGE_ID'
			}, {
				name : 'messageId',
				mapping : 'MESSAGE_ID'
			}, {
				name : 'sendUserName',
				header : '发信人',
				mapping : 'SEND_USER_NAME'
			}, {
				name : 'receiveUserName',
				header : '收信人',
				mapping : 'RECEIVE_USER_NAME'
			}, {
				name : 'isReadFlag',
				header :'读取状态',
				mapping : 'IS_READ_FLAG',
				renderer : function(v){
					if(v=='0'){
						return v = '未读';
					}else if(v=='1'){
						return v = '已读';
					}
				}
			}, {
				name : 'content',
				header :'内容',
				mapping : 'CONTENT'
			}],
			pagesize : pageSize,
			// 查询字段
			selectItems : {
				layout :'form',
				items :[{
								layout : 'column',
								labelWidth :80,
				items : [ {
					columnWidth : .25,
					layout : 'form',
					defaultType : 'textfield',
					
					border : false,
					items : [ {
						name : 'SEND_USER_NAME',
						xtype : 'textfield',
						labelStyle: 'text-align:right;',
						fieldLabel : '发信人',
						width : '100',
						anchor : '90%'
					} ]
				} ,{
					columnWidth : .25,
					layout : 'form',
					defaultType : 'textfield',
					border : false,
					items : [ {
						name : 'RECEIVE_USER_NAME',
						xtype : 'textfield',
						labelStyle: 'text-align:right;',
						fieldLabel : '收信人',
						width : '100',
						anchor : '90%'
					} ]
				},{ columnWidth : .5,
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 80,
						items : [{
							labelStyle: 'text-align:right;',
							fieldLabel : '收发日期',
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							name : 'sendTimeS',
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						labelStyle : 'text-align:center',
						labelAlign : 'right',
						labelSeparator : '',
						labelWidth : 40,
						items : [{
							xtype : 'datefield',
							resizable : true,
							fieldLabel : '至',
							name : 'sendTimeE',
							format : 'Y-m-d',
							editable : false,
							anchor : '85%'
						}]
					} ]
				
					} ]
				},{
					layout :'column',
					labelWidth : 80,
					items:[{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : typeStore,
							xtype : 'combo',
							name : 'IS_READ_FLAG',
							hiddenName : 'IS_READ_FLAG',
							labelStyle: 'text-align:right;',
							fieldLabel : '是否已读',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
//							width : '100',
							anchor : '90%'
						} ]
					} 
//					,{
//						columnWidth : .25,
//						layout : 'form',
//						items : [ {
//							store : boxTypeStore,
//							xtype : 'combo',
//							name : 'BOX',
//							hiddenName : 'BOX',
//							labelStyle: 'text-align:right;',
//							fieldLabel : '收/发信箱',
//							valueField : 'key',
//							displayField : 'value',
//							mode : 'local',
//							typeAhead : true,
//							forceSelection : true,
//							triggerAction : 'all',
//							emptyText : '请选择',
//							selectOnFocus : true,
////							width : '100',
//							anchor : '90%'
//						} ]
//					}
					]
				} ]
			},fclms : [ {
							layout : 'form',
							columnWidth : .5,
						items : [{
							id :'textfield1',
							name : 'sendUserName',
							fieldLabel : '发信人',
							xtype : 'textfield',
							width : 100,
							disabled :true,
							allowBlank : false,
							maxLength : 100,
							anchor : '90%'
						} ,{
							id :'textfield2',
							name : 'receiveUserName',
							fieldLabel : '收信人',
							xtype : 'textfield',
							disabled :true,
							width : 100,
							allowBlank : true,
							maxLength : 200,
							anchor : '90%'
						},{
							id :'textfield3',
							name : 'content',
							fieldLabel : '内容',
							xtype : 'textarea',
							disabled :true,
							width : 100,
							allowBlank : true,
							maxLength : 200,
							anchor : '90%'
						}, {
							name : 'messageId',
							xtype : 'hidden'
					}]
				}],
			buts :[{
					text :'新建',
					iconCls:'addIconCss',
					handler :function(){
					noteForm.form.reset();
					Ext.getCmp('plandate').setValue('');
					Ext.getCmp('plantime').setValue('');
					addWin.show();
					}
			},{
				text : '查看',
				iconCls:'maintainIconCss',
				handler : function(){
					if (listPanel.grid.selModel.hasSelection()) {
							var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
							var recordsLen = records.length;// 得到行数组的长度
							if (recordsLen > 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
							} else {
								if (listPanel.grid.selModel.hasSelection()) {
									 listPanel.detail();
									var messageId = listPanel.grid.getSelectionModel().getSelected().get('messageId');
									var readFlag = listPanel.grid.getSelectionModel().getSelected().get('isReadFlag');
									Ext.Ajax.request({
										url : basepath+ '/NoteManage!lopkDetail.json',
										method : 'POST',
										params : {
											"messageId":messageId,
											"readFlag":readFlag
										},
										scope : this,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											listPanel.grid.getStore().reload();
										},
										failure : function(response) {
											Ext.Msg
													.alert(
															'提示',
															'操作失败,失败原因:'
																	+ response.responseText);
										}
									});
								}
							}
						} else {
							Ext.Msg.alert("提示", "请先选择要查看的记录!");
						}
						
					
				}
			},{
				text :'删除',
				iconCls:'deleteIconCss',
				handler : function(){
			 var selectLength = listPanel.grid.getSelectionModel().getSelections().length;
		        if(selectLength < 1){
					Ext.Msg.alert('提示','请选择需要删除的记录!');
				} 
		        else {
		        	Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
						if(buttonId.toLowerCase() == "no"){
								return ;
							}
					var selectRe=null;
					var tempId;
					var idStr = '';
					for(var i = 0; i<selectLength;i++){
						selectRe = listPanel.grid.getSelectionModel().getSelections()[i];
						tempId = selectRe.data.messageId;
						idStr += tempId;
						if( i != selectLength-1)
							idStr += ',';
					}
					Ext.Ajax.request({
						url : basepath+'/NoteManage/1'+'/batchDel.json?idStr='+idStr,
						scope : this,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							listPanel.grid.getStore().reload();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					});
					
					});
		            }
			    		
			    	}
			}]
		});
		
		listPanel.on('celldblclick',function(){
						if (listPanel.grid.selModel.hasSelection()) {
								var messageId = listPanel.grid.getSelectionModel().getSelected().get('messageId');
								var readFlag = listPanel.grid.getSelectionModel().getSelected().get('isReadFlag');
								Ext.Ajax.request({
								url : basepath+ '/NoteManage!lopkDetail.json',
								method : 'POST',
								params : {
									"messageId" : messageId,
									"readFlag" : readFlag
								},
								scope : this,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									listPanel.grid.getStore().reload();
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '操作失败,失败原因:'
													+ response.responseText);
								}
							});
						} 
					
		});
		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
	});