Ext.onReady(function() {
	Ext.QuickTips.init();
	// 渠道类型下拉框的数据查询
		var typeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=IS_READ_FLAG'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
	
			
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "邮件平台",
			stUrl : basepath + '/NoteManagerQuery.json',
			primary : "messageId",
			checkbox : true,
			winHeight : 450,
			winWidth : 800,
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
				name : 'TITLE',
				header :'标题',
				mapping : 'title'
			}, {
				name : 'content',
				header :'内容',
				mapping : 'CONTENT'
			}, {
				name : 'SEND_TIME',
				mapping : 'sendTime'
			}, {
				name : 'READ_TIME',
				mapping : 'readTime'
			}, {
				name : 'STATE',
				mapping : 'state'
			}, {
				name : 'IS_READ_FLAG',
				mapping : 'isReadFlag'
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
						name : 'SEND_USER_NAME',
						xtype : 'textfield',
						fieldLabel : '发送人',
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
						fieldLabel : '接收人',
						width : '100',
						anchor : '90%'
					} ]
				},{
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
							width : '100',
							anchor : '90%'
						} ]
					} ]
			},
			fclms : [ {
							layout : 'form',
							columnWidth : .5,
						items : [{
							name : 'sendUserName',
							fieldLabel : '发送人',
							xtype : 'textfield',
							width : 100,
							allowBlank : false,
							maxLength : 100,
							anchor : '90%'
						} ,{
							name : 'receiveUserName',
							fieldLabel : '收件人',
							xtype : 'textfield',
							width : 100,
							allowBlank : true,
							maxLength : 200,
							anchor : '90%'
						},{
							name : 'content',
							fieldLabel : '内容',
							xtype : 'textarea',
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
					text :'新增',
					handler :function(){
					
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
					var textfield1 = Ext.getCmp('textfield1').disable();
					var textfield2 = Ext.getCmp('textfield2').disable();
					var textfield3 = Ext.getCmp('textfield3').disable();
		});
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
	});