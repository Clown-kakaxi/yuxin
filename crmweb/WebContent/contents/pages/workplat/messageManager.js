Ext.onReady(function() {
	Ext.QuickTips.init();
	// 渠道类型下拉框的数据查询
	var pageSize = 20;
	var u_idStr = '';	
	var u_nameStr = '';
	var arr = new Array();
		var typeStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['0', '未读'],['1', '已读']]
			});
		var userTypeStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['0', '用户'],['1', '客户'],['2','群组']]
			});
		var boxTypeStore = new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['0', '收件箱'],['1', '发件箱']]
			});	
		var sendRecord = Ext.data.Record.create([{
								name : 'sendUserId',
								mapping : 'SENDUSERID'
							},{
								name : 'sendUserType',
								mapping : 'SENDUSERTYPE'
							}, {
								name : 'sendUserName',
								mapping : 'SENDUSERNAME'
							},{
								name : 'sendUserTel',
								mapping : 'SENDUSERTEL'
							}]);
			var sendReader = new Ext.data.JsonReader({
           		successProperty : 'success',
           		idProperty : 'SENDUSERID',
           		messageProperty : 'message',
           		root : 'json.data',
           		totalProperty : 'json.count'
           	}, sendRecord);	
           	
           	var sendStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/SendManageQuery.json',
					method: 'GET'
				}),
				reader : sendReader
			});
			
			var sm = new Ext.grid.CheckboxSelectionModel();
			var rownum = new Ext.grid.RowNumberer({
						header : '编号',
						width : 40
					});
			// 定义列模型
			var sendCm = new Ext.grid.ColumnModel([rownum,sm, 
			           {
						dataIndex : 'sendUserId', // 数据索引:和Store模型对应
						sortable : true,
						hidden : true,
						width : 80
				    } ,{
						header : '名称',
						dataIndex : 'sendUserName',
						sortable : true,
						width : 150
					},{
						header : '手机号码',
						dataIndex : 'sendUserTel',
						sortable : true,
						width : 150
					},{
						dataIndex : 'sendUserType', 
						sortable : true,
						header :'类别',
						width : 150,
						renderer:function(value){
							if(value =='0'){
								return value ='用户';
							}else if(value =='1'){
								return value = '客户';
							}else{
							  return value = '群组';
							}
						}
				    }]);
			
			
			var noteForm = new Ext.form.FormPanel({
				id : 'noteForm',
				width : 600,
				height : 400,
				frame : true,
				labelWidth : 60,
				layout : 'form',
				labelAlign : 'right',
				items : [{
							layout : 'column',
							items : [{
										layout : 'form',
										columnWidth : 0.8,
										items : [{
													xtype : 'textfield',
													fieldLabel : '收件人',
													disabled  : true,
													name : 'RECEIVE_USER_NAME',
													width : 160,
													anchor : '95%'
												}]
									},{
										layout : 'form',
										columnWidth : 0.08,
										items : [{
													xtype : 'button',
													text : '添加',
													width :80,
													anchor : '95%',
													handler :function(){
														sendStore.reload({
																params : {
																	start : 0,
																	limit : pageSize
																}
														});
														userWin.show();
													}
												}]
									},{
										layout : 'form',
										columnWidth : 0.08,
										items : [{
													xtype : 'button',
													text : '清空',
													width :80,
													anchor : '95%',
													handler : function() {
										u_idStr = '';
										u_nameStr = '';
										title_s = '';
										content_s = ''
										for (var i = 0; i < arr.length; i++) {
											arr.pop(i);
										}
										noteForm.form
												.findField('RECEIVE_USER_NAME')
												.setValue('');
									}
												}]
									}]}, {
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '标题',
													name : 'TITLE',
													width  : 120,
													anchor : '76%'
												}, {
													
													xtype : 'textarea',
													hideLabel: true,
													height : 260,
													name : 'CONTENT',
													anchor : '95%'
															
												}]
									}]
						});
//			var planTime = new Ext.form.DateTimeField({
//					id :'planTime',
//					fieldLabel: '时间',
//					width : 140,
//					name: 'planTime',
//					format:'YmdH:i:s'
//				});
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
							title : '编辑新站内信',
							items : [noteForm],
							buttons : [{
							text : "立即发送",
							handler :function(){
							var receiveUserName_s = noteForm.getForm().findField('RECEIVE_USER_NAME').getValue();
							var title_s =noteForm.getForm().findField('TITLE').getValue();
							var	content_s = noteForm.getForm().findField('CONTENT').getValue();
							title_s = encodeURIComponent(title_s);
							content_s = encodeURIComponent(content_s);
							if(receiveUserName_s==""){
								Ext.Msg.alert('提醒框','请输入收信人！');
							}else{
					Ext.Ajax.request({
						url : basepath+'/NoteManage!sendMessageNow.json?title_s='+title_s
						+"&content_s="+content_s+"&u_idStr="+u_idStr,
						scope : this,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							u_idStr ='';
							u_nameStr = '';
							title_s ='';
							content_s =''
							for(var i=0;i<arr.length;i++){
							   arr.pop(i);
							}
							addWin.hide();
							listPanel.grid.getStore().reload();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					});
							}
							},
							scope : this
						}, {
							text : "定时发送",
							handler : function(){
							var plandate = Ext.getCmp('plandate').getValue();
							var plantime = Ext.getCmp('plantime').getValue();
							var datetime = plandate +" "+ plantime;
							
							var receiveUserName_s = noteForm.getForm().findField('RECEIVE_USER_NAME').getValue();
							var title_s =noteForm.getForm().findField('TITLE').getValue();
							var	content_s = noteForm.getForm().findField('CONTENT').getValue();
							title_s = encodeURIComponent(title_s);
							content_s = encodeURIComponent(content_s);
							if(receiveUserName_s==""){
								Ext.Msg.alert('提醒框','请输入收信人！');
							}else{
					Ext.Ajax.request({
						url : basepath+'/NoteManage!sendByTime.json?title_s='+title_s
						+"&content_s="+content_s+"&u_idStr="+u_idStr+'&datetime='+datetime+'&sendType=0',
						scope : this,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							u_idStr ='';
							u_nameStr = '';
							title_s ='';
							content_s =''
							for(var i=0;i<arr.length;i++){
							   arr.pop(i);
							}
							addWin.hide();
							listPanel.grid.getStore().reload();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					});
							}
							},
							scope : this
						},{ 
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
					}, {
							text : "重置",
							handler : function(){
								noteForm.getForm().reset();
								u_idStr ='';
							u_nameStr = '';
							title_s ='';
							content_s =''
							for(var i=0;i<arr.length;i++){
							   arr.pop(i);
							}
							},
							scope : this
						}]
						});
						
			var sendGrid = new Ext.grid.GridPanel({
				id : 'sendGrid',
				width : 585,
				height : 280,
				store : sendStore,
				sm : sm,
				cm : sendCm,
				tbar : [{
					text : '添加',
					handler : function() {
						u_nameStr ='';
						var usersArray = sendGrid.getSelectionModel().getSelections();
						var record = null;
						var flag = true;
						for (var i = 0; i < usersArray.length; i++) {
							flag = true;
							record = usersArray[i];
							for(var k=0;k<arr.length;k++){
							  if((arr[k].data.sendUserId == record.data.sendUserId)&&(arr[k].data.sendUserType == record.data.sendUserType)){
							  		flag = false;
							  		break;
							  }
							}
							if(flag){
							arr.push(record);
							u_nameStr += record.data.sendUserName;
								u_nameStr +=';';
							u_idStr += record.data.sendUserId;
							u_idStr += ',';
							u_idStr += record.data.sendUserType;
							u_idStr += ';';
							}
						}
						var nameStr = noteForm.getForm().findField('RECEIVE_USER_NAME').getValue();
								if(nameStr ==''){
									nameStr += u_nameStr;
								}else{
									var ch = nameStr.charAt(nameStr.length-1);
									if(ch ==';'){
										nameStr += u_nameStr;
									}else{
										nameStr +=';';
										nameStr +=u_nameStr;
									}
								}
						noteForm.getForm().findField('RECEIVE_USER_NAME').setValue(nameStr);
						userWin.hide();
					}
				}],
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : sendStore,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							emptyMsg : "没有符合条件的记录"
						})
			});
			 
			var sendForm = new Ext.form.FormPanel({
				id :'sendForm',
				title : '成员查询',
				frame : true,
				width : 600,
				height:90,
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
							fieldLabel : '成员名称',
							name : 'SENDUSERNAME',
							anchor : '95%'
						}]
					},{
						columnWidth : .50,
						labelWidth : 100, // 标签宽度
						layout : 'form',
						items : [ {
						store : userTypeStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '成员类型',
						name : 'SENDUSERTYPE',
						hiddenName : 'SENDUSERTYPE',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '--请选择--',
						selectOnFocus : true,
						width : '100',
						anchor : '90%'
					} ]
					}]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
					var v_send = sendForm.getForm().getValues(false);
					sendStore.baseParams = {
							"condition" : Ext.encode(v_send)
					},
					sendStore.reload({
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
						var userWin = new Ext.Window({
							plain : true,
							layout : 'fit',
							resizable : false,
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
							title : '添加收件人',
							items : [{
								layout : 'form',
								items:[sendForm,sendGrid]
							}]
						});
						
						userWin.on('hide',function(){
							sm.clearSelections();
						});
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "站内信平台",
			stUrl : basepath + '/NoteManagerQuery.json?sendType=0',
			primary : "messageId",
			checkbox : true,
			winHeight : 450,
			winWidth : 800,
			seFormHeight: 100,
			pageSize :20,
			gclms : [ {
				name : 'messageId',
				mapping : 'MESSAGE_ID'
			}, {
				name : 'sendUserName',
				header : '发送人',
				mapping : 'SEND_USER_NAME'
			}, {
				name : 'receiveUserName',
				header : '接收人',
				mapping : 'RECEIVE_USER_NAME'
			}, {
				name : 'isReadFlag',
				header :'读取状态',
				mapping : 'IS_READ_FLAG',
				renderer : function(v){
					if(v=='0'){
						return v = '未读';
					}else{
						return v = '已读';
					}
				}
			}, {
				name : 'title',
				header :'标题',
				mapping : 'TITLE'
			}, {
				name : 'content',
				header :'内容',
				mapping : 'CONTENT'
			}, {
				name : 'sendTime',
				mapping : 'SEND_TIME'
			}, {
				name : 'readTime',
				mapping : 'READ_TIME'
			}, {
				name : 'state',
				mapping : 'STATE'
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
							width :80,
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
							lableWidth : 60,
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
					} ,{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : boxTypeStore,
							xtype : 'combo',
							name : 'BOX',
							hiddenName : 'BOX',
							labelStyle: 'text-align:right;',
							fieldLabel : '收/发信箱',
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
					}]
				} ]
			},fclms : [ {
							layout : 'form',
							columnWidth : .5,
						items : [{
							id :'textfield1',
							name : 'sendUserName',
							fieldLabel : '发送人',
							xtype : 'textfield',
							disabled :true,
							width : 100,
							allowBlank : false,
							maxLength : 100,
							anchor : '90%'
						} ,{
							id :'textfield2',
							name : 'receiveUserName',
							fieldLabel : '收件人',
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
					handler :function(){
					noteForm.form.reset();
					addWin.show();
					}
			},{
				text : '查看',
				handler : function(){
					if (listPanel.grid.selModel.hasSelection()) {
							var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
							var recordsLen = records.length;// 得到行数组的长度
							if (recordsLen > 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
							} else {
								if (listPanel.grid.selModel.hasSelection()) {
									 listPanel.detail();
									var messageId = listPanel.grid
											.getSelectionModel().getSelected()
											.get('messageId');
									var readFlag = listPanel.grid
											.getSelectionModel().getSelected()
											.get('isReadFlag');
									Ext.Ajax.request({
										url : basepath
												+ '/NoteManage!lopkDetail.json?messageId='
												+ messageId + '&readFlag='
												+ readFlag,
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
				handler : function(){
			 var selectLength = listPanel.grid.getSelectionModel().getSelections().length;
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
		
		listPanel.grid.on('celldblclick',function(){
						if (listPanel.grid.selModel.hasSelection()) {
								var messageId = listPanel.grid.getSelectionModel().getSelected().get('messageId');
								var readFlag = listPanel.grid.getSelectionModel().getSelected().get('isReadFlag');
								Ext.Ajax.request({
								url : basepath
										+ '/NoteManage!lopkDetail.json?messageId='
										+ messageId + '&readFlag=' + readFlag,
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