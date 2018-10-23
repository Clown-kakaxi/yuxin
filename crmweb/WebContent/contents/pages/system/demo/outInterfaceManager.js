Ext.onReady(function() {
	Ext.QuickTips.init();
	var boxstore8 = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=P_CUST_GRADE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	//接口类型
	 var interfaceTypeStore = new Ext.data.ArrayStore( {
         fields : [ 'key', 'value' ],
         data : [ [ '1', 'Socket' ], [ '2', 'THHP' ], [ '3', 'WEBSERVICE' ], [ '4', 'SOAP' ]]
     });
		//收发状态
	 var SFZTStore = new Ext.data.ArrayStore( {
         fields : [ 'key', 'value' ],
         data : [ [ '1', '发送' ], [ '2', '接收' ]]
     });
	 
		//接口状态
	 var interfaceStateStore = new Ext.data.ArrayStore( {
         fields : [ 'key', 'value' ],
         data : [ [ '1', '有效' ], [ '2', '无效' ]]
     });
	
	var qForm = new Ext.form.FormPanel({
		title : "外部接口查询",
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
					fieldLabel : '接口名称',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CHANNEL_ID',
					anchor : '90%'
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [{
					store : interfaceTypeStore,
					xtype : 'combo', resizable : true,
					fieldLabel : '接口类型',
					name : 'INTERFACE_TYPE',
					labelStyle: 'text-align:right;',
					hiddenName : 'INTERFACE_TYPE',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable :false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
                    selectOnFocus : true,
					//width : '100',
					anchor : '90%'
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '接口设置时间从',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					format:'Y-m-d',
					Width : '100',
					name : 'CHANNEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '接口设置时间到',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					format:'Y-m-d',
					Width : '100',
					name : 'CHANNEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					store : SFZTStore,
					xtype : 'combo', resizable : true,
					fieldLabel : '收发状态',
					name : 'SFZT',
					hiddenName : 'SFZT',
					labelStyle: 'text-align:right;',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable :false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
                    selectOnFocus : true,
					//width : '100',
					anchor : '90%'
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					store : interfaceStateStore,
					xtype : 'combo', resizable : true,
					fieldLabel : '接口状态',
					name : 'INTERFACE_STATE',
					hiddenName : 'INTERFACE_STATE',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					labelStyle: 'text-align:right;',
					editable :false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
                    selectOnFocus : true,
					//width : '100',
					anchor : '90%'
				} ]
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = qForm.getForm().getValues(false);
				channelStore.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				channelStore.load({
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
			}
		} ]
	});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var record = Ext.data.Record.create([ {
		name : 'interfaceName',
		mapping : 'interfaceName'
	}, {
		name : 'interfaceType',
		mapping : 'interfaceType'
	}, {
		name : 'ipAddr',
		mapping : 'ipAddr'
	}, {
		name : 'port',
		mapping : 'port'
	}, {
		name : 'setTime',
		mapping : 'setTime'
	}, {
		name : 'interfaceState',
		mapping : 'interfaceState'
	}, {
		name : 'remark',
		mapping : 'remark'
	}]);

	// 定义列模型

	var cm = new Ext.grid.ColumnModel([ rownum, {
		header : '接口名称',
		width : 200,
		align : 'left',
		dataIndex : 'interfaceName',
		sortable : true
	}, {
		header : '接口类型',
		width : 150,
		align : 'left',
		dataIndex : 'interfaceType',
		sortable : true
	}, {
		header : 'IP',
		width : 100,
		align : 'right',
		dataIndex : 'ipAddr',
		sortable : true
	}, {
		header : '端口',
		width : 100,
		align : 'right',
		dataIndex : 'port',
		sortable : true
	}, {
		header : '接口设置时间',
		width : 100,
		align : 'left',
		dataIndex : 'setTime',
		sortable : true
	}, {
		header : '接口状态',
		width : 100,
		align : 'left',
		dataIndex : 'interfaceState',
		sortable : true
	}, {
		header : '备注',
		width : 100,
		align : 'left',
		dataIndex : 'remark',
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var channelStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, record)
	});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","interfaceName":"短息接口","interfaceType":"THHP","ipAddr":"172.167.1.1","port":"3305","setTime":"2013-02-28","interfaceState":"有效","remark":"短息接口"},
			{"rownum":"2","interfaceName":"接收CC处理接口","interfaceType":"THHP","ipAddr":"219.117.23.1","port":"4565","setTime":"2013-01-22","interfaceState":"有效","remark":"短息预约"},
			{"rownum":"3","interfaceName":"接收咨询信息接口","interfaceType":"Socket","ipAddr":"214.227.148.1","port":"3306","setTime":"2013-03-12","interfaceState":"有效","remark":"客户服务短息"},
			{"rownum":"4","interfaceName":"发送CC","interfaceType":"WEBSERVICE","ipAddr":"192.168.1.34","port":"8080","setTime":"2012-08-18","interfaceState":"无效","remark":"呼叫中心信息共享"}
			]
		};
	channelStore.loadData(memberData);

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

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue())
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : channelStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	var tbar = new Ext.Toolbar(
			{
				items : [{
						text : '新增',
						iconCls:'addIconCss ',
						handler : function() {
							addChannelInit();
						}
					},'-',{
						text : '修改',
						iconCls:'resetIconCss',
						handler : function() {
						var selectLength = grid.getSelectionModel().getSelections().length;
						var selectRe = grid.getSelectionModel().getSelections()[0];
						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
						}else {
							var actiStatus = selectRe.data.actiStatus;
								editChannelForm.getForm().loadRecord(selectRe);
								document.getElementById('channelIdStr').value = selectRe.data.channelId;
								editChannelInit();
						}
						}
					},'-',{
						text : '删除',
						iconCls:'deleteIconCss',
						handler : function() 
						{
							 var selectLength = grid.getSelectionModel()
								.getSelections().length;
							 var selectRe;
							 var tempId;
							 var idStr = '';
							 var actiStatus;
							if(selectLength < 1){
								Ext.Msg.alert('提示','请选择需要删除的记录!');
							} else {
								for(var i = 0; i<selectLength;i++)
								{
									selectRe = grid.getSelectionModel()
									.getSelections()[i];
									cId = selectRe.data.channelId;
									if(cId == '3453')
									{
										Ext.Msg.alert('提示','该外部接口已经在营销活动中引用，不能删除!');
										return;
									}
								}
									Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
										if(buttonId.toLowerCase() == "no"){
										return;
										} 
										Ext.Msg.alert('提示', '操作成功');
									});
							}
						}
					}]
			});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
		title : '外部接口基本信息列表',
		width:700,
		height:315,
		frame : true,
		autoScroll : true,
		region : 'center',
		store : channelStore,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	// 新增外部接口展示的from
	var addChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		id:'allForms',
		labelAlign : 'right',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'interfaveName',
					xtype : 'textfield',
					fieldLabel : '*接口名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'interfaveName',
					xtype : 'textfield',
					fieldLabel : '*IP',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : interfaceTypeStore,
					xtype : 'combo', resizable : true,
					fieldLabel : '接口类型',
					name : 'INTERFACE_TYPE',
					labelStyle: 'text-align:right;',
					hiddenName : 'INTERFACE_TYPE',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable :false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
                    selectOnFocus : true,
					//width : '100',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'interfaveName',
					xtype : 'textfield',
					fieldLabel : '*端口',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					hiddenName : 'channelFitCustLevel',
					fieldLabel : '接口状态',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : interfaceStateStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					name : 'bwstype',
					xtype : 'textfield',
					fieldLabel : '*接口报文格式',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : 1,
				items : [ {
					name : 'remark',
					xtype : 'textarea',
					fieldLabel : '外部接口描述',
					maxLength:1000,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [{
				text : '保存',
				id:'baocun_id',
				//disabled:JsContext.checkGrant('baocun_id'),
				handler : function() {
            		if(!addChannelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}
            		Ext.Msg.alert('提示', '操作成功');
					addChannelWindow.hide();
					addChannelForm.getForm().reset();
				}
			}, {
				text : '取  消',
				handler : function() {
					addChannelWindow.hide();
				}
			}
		]
	});
	
	// 修改基本信息展示的from
	var editChannelForm = new Ext.form.FormPanel({
		labelWidth : 120,
		height : 300,
		frame : true,
		labelAlign : 'right',
		id : 'editAllForms',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'interfaceName',
					xtype : 'textfield',
					fieldLabel : '*接口名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'ipAddr',
					xtype : 'textfield',
					fieldLabel : '*IP',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : interfaceTypeStore,
					xtype : 'combo', resizable : true,
					fieldLabel : '接口类型',
					name : 'interfaceType',
					labelStyle: 'text-align:right;',
					hiddenName : 'interfaceType',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					editable :false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
                    selectOnFocus : true,
					//width : '100',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'port',
					xtype : 'textfield',
					fieldLabel : '*端口',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					hiddenName : 'interfaceState',
					fieldLabel : '接口状态',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : interfaceStateStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					name : 'channelId1',
					hiddenName : 'channelId1',
					xtype : 'combo',
					fieldLabel : '接口报文格式',
					store : new Ext.data.ArrayStore({
						fields : [ 'value', 'text' ],
						data : [ [ 100, '产品推荐模板' ], [ 200, '活动宣传模板' ]]
					}),
					valueField : 'value',
					displayField : 'text',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : 1,
				items : [ {
					name : 'remark',
					xtype : 'textarea',
					fieldLabel : '外部接口描述',
					maxLength:1000,
					anchor : '90%'
				}]
			}]
		}],
			buttons : [
			{
				text : '保  存',
				handler : function() {
					if(!editChannelForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}	
					Ext.Msg.alert('提示', '操作成功');
					//end
					editChannelWindow.hide();
				}
			}, {
				text : '取  消',
				handler : function() {
					editChannelWindow.hide();
				}
			} ]
	});
	
	// 新增外部接口的from
	var addChannelPanel = new Ext.Panel({
		width : 600,
		height : 250,
		layout : 'fit',
		buttonAlign : "center",
		items : [ addChannelForm]
	});
	
	// 修改外部接口的from
	var editChannelPanel = new Ext.Panel({
		labelWidth : 150,
		height : 250,
		layout : 'fit',
		buttonAlign : "center",
		items : [ editChannelForm ]
	});

	// 展示新增的窗口
	function addChannelInit() {
		addChannelForm.getForm().reset();
		addChannelWindow.show();
	}
	
	// 展示修改窗口
	function editChannelInit() {
		editChannelWindow.show();
	}
	
	// 定义新增窗口
	var addChannelWindow = new Ext.Window({
		title : '外部接口新增',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 250,
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
		items : [ addChannelPanel ]
	});
	
	// 定义修改窗口
	var editChannelWindow = new Ext.Window({
		title : '外部接口修改',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 250,
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
		items : [ editChannelPanel ]
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