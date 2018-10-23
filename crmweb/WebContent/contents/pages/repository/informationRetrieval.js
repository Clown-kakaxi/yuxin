Ext.onReady(function() {
			Ext.QuickTips.init();
			var selectScopeStore = new Ext.data.SimpleStore({
					fields : ['name', 'code'],
					data : [['全部', '全部'],['标题', '标题'],['内容', '内容']]
				});
			
			var recordco = Ext.data.Record.create([
			                                       {
			                                    	   name:'sectionId',mapping:'sectionId'
			                                       },{name:'sectionName',mapping:'sectionName'}]);
			var messageTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/workplatforminfosection!indexAll.json'
				}),
				reader : new Ext.data.JsonReader({
					idProperties:'sectionId',
					root : ''
				}, recordco)
			});
			var qForm = new Ext.form.FormPanel({
				title : "资讯检索",
				labelWidth : 90, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'center',
				region : 'north',
				split : true,
				height : 120,
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							fieldLabel : '检索信息',
							labelStyle: 'text-align:right;',
							xtype : 'textfield',
							Width : '100',
							name : 'RETRIEVAL_INFORMATION',
							anchor : '90%'
							//disabled:true   编辑区不能输入显灰色
						    //readOnly:true   只读
						} ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : messageTypeStore,
							xtype : 'combo',
							name : 'MESSAGE_TYPE',
							hiddenName : 'MESSAGE_TYPE',
							fieldLabel : '归属模块',
							labelStyle: 'text-align:right;',
							valueField : 'sectionId',
							displayField : 'sectionName',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							editable:false,
							width : '100',
							anchor : '90%'
						} ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							store : selectScopeStore,
							xtype : 'combo',
							id : 'selectScope',
							name : 'SELECT_SCOPE',
							hiddenName : 'SELECT_SCOPE',
							fieldLabel : '检索范围',
							emptyText : '请选择',
							labelStyle: 'text-align:right;',
							valueField : 'name',
							displayField : 'code',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							editable:false,
							width : '100',
							anchor : '90%'
						} ]
						
					},{
						columnWidth : .25,
						layout : 'form',
						items : [new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBORGS',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '发布机构',
							labelStyle : 'text-align:right;',
							id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'PUBLISH_ORG_NAME', 
							hiddenName: 'PUBLISH_ORG',   //后台获取的参数名称
							anchor : '90%',
							checkBox:false //复选标志
						})]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							fieldLabel : '发布者',
							labelStyle: 'text-align:right;',
							xtype : 'textfield',
							Width : '100',
							name : 'PUBLISH_USER',
							anchor : '90%'
						} ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							fieldLabel : '发布时间',
							xtype : 'datefield',
							labelStyle: 'text-align:right;',
							Width : '100',
							name : 'PUBLISH_DATES',
							format : 'Y-m-d',
							editable : false,
							anchor : '90%'
						} ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'datefield',
							fieldLabel : '至',
							labelStyle: 'text-align:right;',
							name : 'PUBLISH_DATEE',
							Width : '100',
							format : 'Y-m-d',
							editable : false,
							anchor : '90%'
						} ]
					} ]
				} ],
				buttons : [ {
					text : '查询',
					handler : function() {debugger;
						var conditionStr = qForm.getForm().getValues(false);
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

				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
						Ext.getCmp("jigouhao").setValue('');
					}

				} ]
			});

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record = Ext.data.Record.create([ {
				name : 'messageId',
				mapping : 'MESSAGE_ID'
			}, {
				name : 'messageType',
				mapping : 'MESSAGE_TYPE'
			}, {
				name : 'messageTitle',
				mapping : 'MESSAGE_TITLE'
			}, {
				name : 'messageSummary',
				mapping : 'MESSAGE_SUMMARY'
			}, {
				name : 'messageIntroduce',
				mapping : 'MESSAGE_INTRODUCE'
			}, {
				name : 'publishDate',
				mapping : 'PUBLISH_DATE'
			}, {
				name : 'publishUser',
				mapping : 'PUBLISH_USER'
			}, {
				name : 'publishOrg',
				mapping : 'PUBLISH_ORG'
			}, {
				name : 'unitName',
				mapping : 'UNITNAME'
			}, {
				name : 'MESSAGE_TYPE_ORA'
			}]);

			// 定义列模型

			var cm = new Ext.grid.ColumnModel([ rownum, {
				header : '文档标题',
				width : 190,
				align : 'center',
				dataIndex : 'messageTitle',
				sortable : true
			}, {
				header : '文档摘要',
				width : 190,
				align : 'center',
				dataIndex : 'messageSummary',
				sortable : true
			}, {
				header : '归属模块',
				width : 180,
				align : 'center',
				dataIndex : 'MESSAGE_TYPE_ORA',
				sortable : true
			}, {
				header : '发布机构',
				width : 180,
				align : 'center',
				dataIndex : 'unitName',
				sortable : true
			}, {
				header : '发布者',
				width : 180,
				align : 'center',
				dataIndex : 'publishUser',
				sortable : true
			}, {
				header : '发布时间',
				width : 180,
				align : 'center',
				dataIndex : 'publishDate',
				sortable : true
			}]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/workingplatformInfoQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'MESSAGE_ID',
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
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
							[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
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
				bbar.pageSize = parseInt(pagesize_combo.getValue()), store
						.reload({
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
			// 修改基本信息展示的from
			var edit2ChannelForm = new Ext.form.FormPanel({
				labelWidth : 120,
				height : 130,
				width : 870,
				frame : true,
				labelAlign : 'right',
				id : 'editUpdateForms',
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [{
							name : 'messageTitle',
							xtype : 'textfield',
							fieldLabel : '文档标题',
							maxLength:100,
							anchor : '90%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'messageSummary',
							xtype : 'textfield',
							fieldLabel : '文档摘要',
							maxLength:100,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'MESSAGE_TYPE_ORA',
							xtype : 'textfield',
							fieldLabel : '归属模块',
							maxLength:100,
							anchor : '90%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'selectScope',
							xtype : 'textfield',
							fieldLabel : '检索范围',
							maxLength:100,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'unitName',
							xtype : 'textfield',
							fieldLabel : '发布机构',
							anchor : '90%'
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'publishDate',
							xtype : 'datefield',
							format:'Y-m-d',
							fieldLabel : '发布时间',
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							name : 'publishUser',
							xtype : 'textfield',
							fieldLabel : '发布者',
							anchor : '90%'
						}]
					}]
				}] 
			});
			var edit2ChannelWindow = new Ext.Window({
				title : '资讯详情',
				plain : true,
//				layout : 'fit',
				width : 900,
				height : 160,
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
				autoScroll : true,
				items : [ edit2ChannelForm,
				          {
  				   xtype : 'fieldset',
  				   title: '附件',
  				   id : 'detailApp',
  				   autoHeight : true,
  				   layout : 'form',
  				   collapsed: true,
  			       collapsible: true,
  		           items : [appendixGridPanel2],
  		           listeners:{
  						'collapse':function(){
							edit2ChannelWindow.setHeight(200);
							edit2ChannelWindow.setPosition(120,10);
							edit2ChannelWindow.doLayout();
  						},
  					    'expand':function(){
  							edit2ChannelWindow.setHeight(350);
  							edit2ChannelWindow.setPosition(120,10);
  							edit2ChannelWindow.doLayout();
  						}
  				   }
  		          }],
                  listeners : {
              		'beforeshow' : function(){
              			Ext.getCmp('_downId').setDisabled(false);
              			Ext.getCmp('_upload').setDisabled(true);
              			Ext.getCmp('_delload').setDisabled(true);
              		}
              	}
			});
			var tbar = new Ext.Toolbar(
					{
						items : [
								{
						            text:'附件信息',
						            iconCls:'dailyIconCss',
						            handler:function()
						            {
						                var record = grid.getSelectionModel().getSelected(); 
						                if (!record) {
						                    Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
						                    return false;
						                }
						                var checkedNodes = grid.getSelectionModel().selections.items;
						                if(checkedNodes.length>1){
						                    Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
						                    return false;
						                }
						                var messageIdStr = record.get('messageId');
						                
						                uploadForm.relaId = messageIdStr;
						                uploadForm.modinfo = 'infomation';
						                var condi = {};
						                condi['relationInfo'] = messageIdStr;
						                condi['relationMod'] = 'infomation';
						                Ext.Ajax.request({
						                    url:basepath+'/queryanna.json',
						                    method : 'GET',
						                    params : {
						                        "condition":Ext.encode(condi)
						                    },
						                    failure : function(a,b,c){
						                        Ext.MessageBox.alert('查询异常', '查询失败！');
						                    },
						                    success : function(response){
						                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
						                        appendixStore.loadData(anaExeArray.json.data);
						                        appendixGridPanel.getView().refresh();
						                    }
						                });
						                appendixWindow.show();
						            }
						        },{
						            text:'详细信息',
						            iconCls:'dailyDetailIconCss',
						            handler:function()
						            {
						        	var selectLength = grid.getSelectionModel().getSelections().length;
									var selectRe = grid.getSelectionModel().getSelections()[0];
									if (selectLength != 1) {
										Ext.Msg.alert("提示", "请选择一条记录!");
									}else {
										var record = grid.getSelectionModel().getSelected();
						                /* 附件列表  */	
										var messageIdStr = record.get('messageId');
										uploadForm.relaId = messageIdStr;
										uploadForm.modinfo = 'infomation';
										var condi = {};
										condi['relationInfo'] = messageIdStr;
										condi['relationMod'] = 'infomation';
										Ext.Ajax.request( {
											url : basepath + '/queryanna.json',
											method : 'GET',
											params : {
												"condition" : Ext.encode(condi)
											},
											failure : function(a, b, c) {
												Ext.MessageBox.alert('查询异常', '查询失败！');
											},
											success : function(response) {
												var anaExeArray = Ext.util.JSON.decode(response.responseText);
												appendixStore.loadData(anaExeArray.json.data);
												appendixGridPanel2.getView().refresh();
											}
										});
										
										edit2ChannelForm.getForm().loadRecord(selectRe);
										edit2ChannelWindow.show();
						            }}} ]
					});

			// 表格实例
			var grid = new Ext.grid.GridPanel({
				title : '资讯检索列表',
				frame : true,
				autoScroll : true,
				region : 'center',
				store : store,
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

			// 布局模型
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ {
					layout : 'border',
					items : [ qForm, grid ]
				} ]
			});

		});