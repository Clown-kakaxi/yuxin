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
	var custSearch = new Com.yucheng.crm.common.OrgUserManage({ 
		xtype:'userchoose',
		fieldLabel : '规则设置人', 
//		id:'roleSeter',
		labelStyle: 'text-align:right;',
		name : 'userName',
		hiddenName:'roleSeter',
//		searchRoleType:('127,47'),  //指定查询角色属性 ,默认全部角色
		searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
		singleSelect:false,
		anchor : '90%'
		});
	var FormulaWindow = new Ext.Window
	(
		{
			plain : true,
			defaults :
			{
				overflow :'auto',
				autoScroll :true
			},
			layout : 'fit',
			frame : true,
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
			width : 1000,
			height : 420,
			buttonAlign : "center",
			title : '公式管理',
			buttons:
			[
				{
					text : '确定',
					handler:function()
					{				
					addChannelForm.form.findField('role').setValue(Ext.getCmp('TEMP').getValue());
//		    			Ext.getCmp('FORMULA_CONTENT').setValue(Ext.getCmp('TEMPCONTENT').getValue());
//		    			
//		    			Ext.getCmp('FORMULA_SHOW').setValue(Ext.getCmp('FORMULAWINDOWT').getValue());
//		    			Ext.getCmp('FORMULA_CONTENT_SHOW').setValue(Ext.getCmp('FORMULAWINDOWW').getValue());
						
						FormulaWindow.hide();
					}
				},
				'-',
			 	{
			 		text : '返回',
			 		handler:function()
			 		{
			 			FormulaWindow.hide();
			 		}
			 	}
			 ]
		}
	);
	
	var qForm = new Ext.form.FormPanel({
		title : "热销产品规则查询",
		labelWidth : 100, // 标签宽度
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
				items : [ custSearch,{
					fieldLabel : '规则名称',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CHANNEL_ID',
					anchor : '90%'
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '规则设置时间',
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
					fieldLabel : '规则开始生效时间',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					Width : '100',
					format:'Y-m-d',
					name : 'CHANNEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '规则结束时间',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					Width : '100',
					format:'Y-m-d',
					name : 'CHANNEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
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
		name : 'roleName',
		mapping : 'roleName'
	}, {
		name : 'setDate',
		mapping : 'setDate'
	}, {
		name : 'setStartDate',
		mapping : 'setStartDate'
	}, {
		name : 'setEndDate',
		mapping : 'setEndDate'
	}, {
		name : 'roleSeter',
		mapping : 'roleSeter'
	}, {
		name : 'remark',
		mapping : 'remark'
	}]);

	// 定义列模型

	var cm = new Ext.grid.ColumnModel([ rownum, {
		header : '规则名称',
		width : 150,
		align : 'left',
		dataIndex : 'roleName',
		sortable : true
	}, {
		header : '规则设置时间',
		width : 100,
		align : 'left',
		dataIndex : 'setDate',
		sortable : true
	}, {
		header : '规则开始生效时间',
		width : 100,
		align : 'left',
		dataIndex : 'setStartDate',
		sortable : true
	}, {
		header : '规则结束时间',
		width : 100,
		align : 'left',
		dataIndex : 'setEndDate',
		sortable : true
	}, {
		header : '规则设置人',
		width : 100,
		align : 'left',
		dataIndex : 'roleSeter',
		sortable : true
	}, {
		header : '备注',
		width : 150,
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
			{"rownum":"1","roleName":"规则1","setDate":"2012-09-09","setStartDate":"2013-01-01","setEndDate":"2013-10-10","roleSeter":"秦生","remark":"备注1"}
			,{"rownum":"2","roleName":"规则2","setDate":"2012-10-09","setStartDate":"2013-01-01","setEndDate":"2013-10-10","roleSeter":"秦生","remark":"备注2"}
			,{"rownum":"3","roleName":"规则3","setDate":"2012-11-09","setStartDate":"2013-01-01","setEndDate":"2013-10-10","roleSeter":"秦生","remark":"备注3"}
			,{"rownum":"4","roleName":"规则4","setDate":"2012-12-09","setStartDate":"2013-01-01","setEndDate":"2013-10-10","roleSeter":"秦生","remark":"备注4"}
			,{"rownum":"5","roleName":"规则5","setDate":"2013-01-09","setStartDate":"2013-01-01","setEndDate":"2013-10-10","roleSeter":"秦生","remark":"备注5"}
			
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

	// 默认加载数据
//	channelStore.load({
//		params : {
//			start : 0,
//			limit : parseInt(pagesize_combo.getValue())
//		}
//	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue())
//		, channelStore
//				.reload({
//					params : {
//						start : 0,
//						limit : parseInt(pagesize_combo.getValue())
//					}
//				});
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
						text : '新增规则',
						iconCls:'addIconCss ',
						handler : function() {
							addChannelInit();
						}
					},'-',{
						text : '修改规则',
						iconCls:'resetIconCss',
						handler : function() {
						var selectLength = grid.getSelectionModel().getSelections().length;
						var selectRe = grid.getSelectionModel().getSelections()[0];
						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
						}else {
							var actiStatus = selectRe.data.actiStatus;
								addChannelForm.getForm().loadRecord(selectRe);
								document.getElementById('channelIdStr').value = selectRe.data.channelId;
								editChannelInit();
						}
						}
					},'-',{
						text : '启用规则',
						iconCls:'completeIconCss',
						handler : function() {
						var selectLength = grid.getSelectionModel().getSelections().length;
						var selectRe = grid.getSelectionModel().getSelections()[0];
						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
						}else{
						Ext.MessageBox.confirm('提示','确定启用吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							return;
							} 
							Ext.Msg.alert('提示', '操作成功');
						});
						}
						}
					},'-',{
						text : '停止规则',
						iconCls:'deleteIconCss ',
						handler : function() {
							var selectLength = grid.getSelectionModel().getSelections().length;
							var selectRe = grid.getSelectionModel().getSelections()[0];
							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
							}else{
							Ext.MessageBox.confirm('提示','确定停止吗?',function(buttonId){
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
		title : '热销产品设置规则信息列表',
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

	// 新增规则展示的from
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
					name : 'roleName',
					xtype : 'textfield',
					fieldLabel : '*规则名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'setStartDate',
					xtype : 'datefield',
					format:'Y-m-d',
					fieldLabel : '*规则开始生效时间',
					maxLength:100,
					anchor : '90%'
				}]
			}]
		},{
			layout : 'column',
			items : [  {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'setEndDate',
					xtype : 'datefield',
					format:'Y-m-d',
					fieldLabel : '*规则结束时间',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [/* {
					name : 'channelId1',
					hiddenName : 'channelId1',
					xtype : 'combo',
					fieldLabel : '营销模板名称',
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
				}*/ {
					name : 'role',
					id:'roleId',
					xtype : 'textfield',
					fieldLabel : '*规则',
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
					fieldLabel : '备注',
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
	
	var indexTreeListRecord = Ext.data.Record.create
	(
		[
		 	{
		 		name : 'ID',
		 		mapping : 'ID'
		 	},
		 	{
		 		name : 'CODE',
		 		mapping : 'CODE'
		 	},
		 	{
		 		name : 'NAME',
		 		mapping : 'NAME'
		 	},
		 	{
		 		name : 'CONTENT',
		 		mapping : 'CONTENT'
		 	},
		 	{
		 		name : 'CLASSNAME',
		 		mapping : 'CLASSNAME'
		 	},
		 	{
		 		name : 'CLASS',
		 		mapping : 'CLASS'
		 	}
		]
	);
	//指标类型树树加载属性值
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader
	(
		{
			checkField : 'ASTRUE',
			parentAttr : 'INDEX_TYPE_SUPERUNIT_ID',//指向父节点的属性列
			locateAttr : 'INDEX_TYPE_ID',//机构编号
			rootValue :1000,
			textField : 'INDEX_TYPE_NAME',//机构名称
			idProperties : 'INDEX_TYPE_ID'//主键
		}
	);
	
	Ext.getCmp('roleId').on
	(
		"focus",
		function(listPanel, rowIndex, event) 
		{
			FormulaWindow.removeAll(true);
			FormulaWindow.add
			(
				{
					width : 210,
					height : document.body.clientHeight,
					layout : 'border',
					//frame : true ,
					items : 
					[
					 	{
					 		region:'north',
					 		id:'FORMULAWINDOW',
					 		height : 75,
					 		title:'公式表达式',
					 		items:
					 		[
					 		 	{
					 		 		//fieldLabel : 'INDEX_ID',
									name : 'FORMULAWINDOWT',
									id : 'FORMULAWINDOWT',
									//xtype : 'textfield', // 设置为数字输入框类型
									width:1000,
									xtype : 'textarea',
									labelStyle: 'text-align:right;',
									disabled:true,
									anchor : '90%'
					 		 	}
					 		]
					 	},
					 	{
					 		region:'center',
					 		id:'FORMULACONTENTWINDOW',
					 		title:'中文表达式',
					 		items:
					 		[
					 		 	{
					 		 		//fieldLabel : 'INDEX_ID',
									name : 'FORMULAWINDOWW',
									id : 'FORMULAWINDOWW',
									//xtype : 'textfield', // 设置为数字输入框类型
									width:1000,
									xtype : 'textarea',
									labelStyle: 'text-align:right;',
									disabled:true,
									anchor : '90%'
					 		 	}
					 		]
					 		
					 	},
					 	{
					 		region:'south',
					 		id:'COUNTWINDOW',
					 		height : 200,
					 		title:'计算器',
					 		layout : 'border',
					 		items:
					 		[
					 		 	{
	    					 		region:'west',
	    					 		id:'FORMULAWINDOW1',
	    					 		width : 300,
	    					 		title:'基本输入',
	    					 		layout : 'column',
	    					 		items:
	    					 		[
	    					 		 	{
	    					 		 		columnWidth : .16666,
	    									layout : 'form',
	    									//labelWidth : 100, // 标签宽度
	    									//defaultType : 'textfield',
	    									border : false,
	    									items : 
	    									[
	    									 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'1',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('1');
	                                                    		TEMPCONTENT.setValue('1');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|1');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|1');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'2',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('2');
	                                                    		TEMPCONTENT.setValue('2');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|2');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|2');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'3',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('3');
	                                                    		TEMPCONTENT.setValue('3');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|3');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|3');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	)
	    									]
	    					 		 	},
	    					 		 	{
	    					 		 		columnWidth : .16666,
	    									layout : 'form',
	    									//labelWidth : 100, // 标签宽度
	    									//defaultType : 'textfield',
	    									border : false,
	    									items : 
	    									[
	    									 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'4',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('4');
	                                                    		TEMPCONTENT.setValue('4');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|4');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|4');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'5',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('5');
	                                                    		TEMPCONTENT.setValue('5');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|5');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|5');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'6',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('6');
	                                                    		TEMPCONTENT.setValue('6');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|6');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|6');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	)
	    									]
	    					 		 	},
	    					 		 	{
	    					 		 		columnWidth : .16666,
	    									layout : 'form',
	    									//labelWidth : 100, // 标签宽度
	    									//defaultType : 'textfield',
	    									border : false,
	    									items : 
	    									[
	    									 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'7',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('7');
	                                                    		TEMPCONTENT.setValue('7');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|7');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|7');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'8',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('8');
	                                                    		TEMPCONTENT.setValue('8');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|8');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|8');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'9',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('9');
	                                                    		TEMPCONTENT.setValue('9');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|9');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|9');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	)
	    									]
	    					 		 	},
	    					 		 	{
	    					 		 		columnWidth : .16666,
	    									layout : 'form',
	    									//labelWidth : 100, // 标签宽度
	    									//defaultType : 'textfield',
	    									border : false,
	    									items : 
	    									[
	    									 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'0',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('0');
	                                                    		TEMPCONTENT.setValue('0');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|0');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|0');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'+',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('+');
	                                                    		TEMPCONTENT.setValue('+');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|+');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|+');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'-',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('-');
	                                                    		TEMPCONTENT.setValue('-');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|-');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|-');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	)
	    									]
	    					 		 	},
	    					 		 	{
	    					 		 		columnWidth : .16666,
	    									layout : 'form',
	    									//labelWidth : 100, // 标签宽度
	    									//defaultType : 'textfield',
	    									border : false,
	    									items : 
	    									[
	    									 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'*',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('*');
	                                                    		TEMPCONTENT.setValue('*');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|*');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|*');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'/',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('/');
	                                                    		TEMPCONTENT.setValue('/');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|/');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|/');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'(',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('(');
	                                                    		TEMPCONTENT.setValue('(');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|(');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|(');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	)
	    									]
	    					 		 	},
	    					 		 	{
	    					 		 		columnWidth : .16666,
	    									layout : 'form',
	    									//labelWidth : 100, // 标签宽度
	    									//defaultType : 'textfield',
	    									border : false,
	    									items : 
	    									[
	    									 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:')',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue(')');
	                                                    		TEMPCONTENT.setValue(')');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|)');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|)');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'.',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                    	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('.');
	                                                    		TEMPCONTENT.setValue('.');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|.');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|.');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	),
			    					 		 	new Ext.Button
			    					 		 	(
			    					 		 		{
	                                                    text:'%',
	                                                    height : 50,
	                                                    width : 50,
	                                                    handler:function()
	                                                    {
	                                                        //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
	                                                    }
	                                                }
			    					 		 	)
	    									]
	    					 		 	}
	    					 		 	
	    					 		 	
	    					 		]
	    					 	},
	    					 	{
	    					 		region:'center',
	    					 		id:'FORMULACONTENTWINDOW2',
	    					 		title:'经典函数',
	    					 		layout : 'column',
	    					 		items:
		    					 	[
										{
											columnWidth : .333,
											layout : 'form',
											//labelWidth : 100, // 标签宽度
											//defaultType : 'textfield',
											border : false,
											items : 
											[
											 	new Ext.Button
											 	(
											 		{
										                text:'SUM（合计）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('SUM');
	                                                    		TEMPCONTENT.setValue('合计');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|SUM');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|合计');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	),
											 	new Ext.Button
											 	(
											 		{
										                text:'AVERAGE（均值）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('AVERAGE');
	                                                    		TEMPCONTENT.setValue('均值');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|AVERAGE');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|均值');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	),
											 	new Ext.Button
											 	(
											 		{
										                text:'IF（如果）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('IF');
	                                                    		TEMPCONTENT.setValue('如果');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|IF');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|如果');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	)
											]
										},
										{
											columnWidth : .333,
											layout : 'form',
											//labelWidth : 100, // 标签宽度
											//defaultType : 'textfield',
											border : false,
											items : 
											[
											 	new Ext.Button
											 	(
											 		{
										                text:'COUNT（数量）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('COUNT');
	                                                    		TEMPCONTENT.setValue('数量');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|COUNT');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|数量');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	),
											 	new Ext.Button
											 	(
											 		{
										                text:'MAX（最大）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('MAX');
	                                                    		TEMPCONTENT.setValue('最大');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|MAX');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|最大');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	),
											 	new Ext.Button
											 	(
											 		{
										                text:'MIN（最小）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('MIN');
	                                                    		TEMPCONTENT.setValue('最小');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|MIN');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|最小');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	)
											]
										},
										{
											columnWidth : .333,
											layout : 'form',
											//labelWidth : 100, // 标签宽度
											//defaultType : 'textfield',
											border : false,
											items : 
											[
											 	new Ext.Button
											 	(
											 		{
										                text:'ABS（绝对值）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('ABS');
	                                                    		TEMPCONTENT.setValue('绝对值');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|ABS');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|绝对值');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	),
											 	new Ext.Button
											 	(
											 		{
										                text:'MOD（取余）',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	if(TEMP.getValue() == '')
	                                                    	{
	                                                    		TEMP.setValue('MOD');
	                                                    		TEMPCONTENT.setValue('取余');
	                                                    	}
	                                                    	else
	                                                    	{
	                                                    		TEMP.setValue(TEMP.getValue()+'|MOD');
	                                                    		TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|取余');
	                                                    	}
//	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length;i++)
	                                                    	{
	                                                    		newTEMPValue = newTEMPValue + newTEMPValues[i];
	                                                    		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
	                                                    	}
	                                                    	
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newTEMPValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newTEMPCONTENTValue);
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	),
											 	new Ext.Button
											 	(
											 		{
										                text:'<-',
										                height : 50,
										                width : 100,
										                handler:function()
										                {
										                	var TEMP = Ext.getCmp('TEMP');
	                                                    	var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
	                                                    	
	                                                    	var newTEMPValues = TEMP.getValue().split('|');
	                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
	                                                    	
	                                                    	var newTEMPValue = '';
	                                                    	var newValue = '';
	                                                    	var newTEMPCONTENTValue = '';
	                                                    	var newCONTENTValue = '';
	                                                    	
	                                                    	for(var i = 0;i<newTEMPValues.length-1;i++)
	                                                    	{
	                                                    		if(newTEMPValue == '')
	                                                    		{
	                                                    			newTEMPValue = newTEMPValues[i];
	                                                    			newValue = newTEMPValues[i];
	                                                    			newTEMPCONTENTValue = newTEMPCONTENTValues[i];
	                                                    			newCONTENTValue = newTEMPCONTENTValues[i];
	                                                    		}
	                                                    		else
	                                                    		{
	                                                    			newTEMPValue = newTEMPValue + '|' +newTEMPValues[i];
	                                                    			newValue = newValue + newTEMPValues[i];
	                                                    			newTEMPCONTENTValue = newTEMPCONTENTValue + '|' +newTEMPCONTENTValues[i];
	                                                    			newCONTENTValue = newCONTENTValue + newTEMPCONTENTValues[i];
	                                                    		}
	                                                    	}
	                                                    	TEMP.setValue(newTEMPValue);
	                                                    	TEMPCONTENT.setValue(newTEMPCONTENTValue);
	                                                    	Ext.getCmp('FORMULAWINDOWT').setValue(newValue);
	                                                    	Ext.getCmp('FORMULAWINDOWW').setValue(newCONTENTValue);
	                                                    	
										                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
										                }
										            }
											 	)
											]
										}
		    					 	]
	    					 		
	    					 		
	    					 	},
	    					 	{
	    					 		region:'east',
	    					 		id:'FORMULACONTENTWINDOW3',
	    					 		width :380,
	    					 		title:'指标',
	    					 		items:
	    					 		[
	    					 		 	new Ext.Button
									 	(
									 		{
								                text:'指标选择',
								                height : 50,
								                width : 378,
								                handler:function()
								                {
								                	var indexTreeListstorex = new Ext.data.Store
								            		(
								            			{
								            				restful : true,
								            				proxy : new Ext.data.HttpProxy
								            				(
								            					{
								            						url : basepath + '/IndexInfoQueryAction.json',
								            						method : 'POST'//,
								            						/*
								                                    success : function(response) 
								                                    {
								                                    	Ext.Msg.alert('提示', response.responseText); 
								                                    },
								                        			failure : function(response) 
								                        			{
								                                 		Ext.Msg.alert('提示','加入失败' );
								                                	}*/
								            					}
								            				),
								            				reader : new Ext.data.JsonReader
								            				(
								            					{
								            						successProperty : 'success',
								            						// idProperty: 'ID',
								            						// messageProperty: 'message',
								            						root : 'json.data',
								            						totalProperty : 'json.count'
								            					}, 
								            					indexTreeListRecord
								            				)
								            			}
								            		);
								                	
								                	var windowIndexTree = new Com.yucheng.bcrm.TreePanel
													(
															{
																id:'indexTreePanelx',
																height : document.body.clientHeight,
																width : 210,
																autoScroll:true,
																checkBox : false, //是否现实复选框：
																_hiddens : [],
																resloader:loader,
																region:'west',
																split:true,
																root: new Ext.tree.AsyncTreeNode
																(
																	{
																		id:1000,
																		expanded:true,
																		text:"指标库",
																		autoScroll:true,
																		children:[]
																	}
																),
																//单击机构树的节点，获取机构ID赋值给隐藏域,根据ID查询
																clickFn:function(node)
																{
																	//alert(node.id);
																	indexTreeListstorex.load
																	(
																		{
																			params : 
																			{
																				start : 0,
																				limit : parseInt(pagesize_combo.getValue()),
																				typeid: node.id
																			}
																		}
																	);
																	//Ext.getCmp('selectOrgId').setValue(node.id);
																	//orgPanel.selectForm.buttons[0].handler({'click' : function search(){}});
																}
															}
														);
								                	var bbbar = new Ext.PagingToolbar( {
								                        pageSize : parseInt(pagesize_combo.getValue()),
								                        store : indexTreeListstorex,
								                        displayInfo : true,
								                        displayMsg : '显示{0}条到{1}条,共{2}条',
								                        emptyMsg : "没有符合条件的记录",
								                        items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
								                    });
								                	
								                	var windowIndexGrid = new Ext.grid.GridPanel
													(
															{
																bbar:bbbar,
																store: indexTreeListstorex,
																colModel: new Ext.grid.ColumnModel
																(
																	{
																		defaults: 
																		{
																			width: 120,
																			sortable: true
																		},
																		columns: 
																		[
																		 	{
																		 		id: 'id', 
																		 		header: '指标编码', 
																		 		hidden : true,
																		 		dataIndex: 'ID'
																		 	},
																		 	{
																		 		header: '指标编号',  
																		 		dataIndex: 'CODE'
																		 	},
																		 	{
																		 		header: '指标名称', 
																		 		dataIndex: 'NAME'
																		 	},
																		 	{
																		 		header: '指标描述', 
																		 		dataIndex: 'CONTENT'
																		 	},
																		 	{
																		 		header: '指标分类', 
																		 		dataIndex: 'CLASSNAME'
																		 	},
																		 	{
																		 		header: '指标分类码', 
																		 		hidden : true,
																		 		dataIndex: 'CLASS'
																		 	}
																		]
																	}
																),
																sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
																id:'indexTreeList',
																region :'center',
																title:"指标列表"
															}
														);
								                	
								                	windowIndexGrid.on
								                	(
							                			"rowdblclick",
							                			function(listPanel, rowIndex, event) 
							                			{
							                				var selectLength = listPanel.getSelectionModel().getSelections().length;
							                				var selectRe = listPanel.getSelectionModel().getSelections()[0].data;
							                				
							                				var TEMP = Ext.getCmp('TEMP');
							                				var TEMPCONTENT = Ext.getCmp('TEMPCONTENT');
							                				var FORMULAWINDOWT = Ext.getCmp('FORMULAWINDOWT');
							                				var FORMULAWINDOWW = Ext.getCmp('FORMULAWINDOWW');
							                				
							                				if(TEMP.getValue() == '')
							                				{
							                					TEMP.setValue(selectRe.CODE);
							                					TEMPCONTENT.setValue(selectRe.NAME);
							                					FORMULAWINDOWT.setValue(selectRe.CODE);
							                					FORMULAWINDOWW.setValue(selectRe.NAME);
							                				}
							                				else
							                				{
							                					TEMP.setValue(TEMP.getValue()+'|'+selectRe.CODE);
							                					TEMPCONTENT.setValue(TEMPCONTENT.getValue()+'|'+selectRe.NAME);
							                					
							                					var newTEMPValues = TEMP.getValue().split('|');
		                                                    	var newTEMPCONTENTValues = TEMPCONTENT.getValue().split('|');
		                                                    	
		                                                    	var newValue = '';
		                                                    	var newCONTENTValue = '';
		                                                    	
		                                                    	for(var i = 0;i<newTEMPValues.length;i++)
		                                                    	{
	                                                    			newValue = newValue + newTEMPValues[i];
	                                                    			newCONTENTValue = newCONTENTValue + newTEMPCONTENTValues[i];
		                                                    		
		                                                    	}
		                                                    	FORMULAWINDOWT.setValue(newValue);
		                                                    	FORMULAWINDOWW.setValue(newCONTENTValue);
							                				}
							                				
							                				IndexWindow.hide();
							                			}
								                	);
								                	
								                	var IndexWindow = new Ext.Window
								            		(
								            			{
								            				plain : true,
								            				defaults :
								            				{
								            					overflow :'auto',
								            					autoScroll :true
								            				},
								            				layout : 'border',
								            				//layout : 'fit',
								            				frame : true,
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
								            				width : 800,
								            				height : 400,
								            				buttonAlign : "center",
								            				title : '指标选择',
								            				buttons:
								            				[
//								            					{
//								            						text : '确定',
//								            						handler:function()
//								            						{
////								            							Ext.Ajax.request
////								            							(
////								            								{
////								            									url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
////								            									method:'GET',
////								            									success:function(response)
////								            									{
////								            										nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
////								            										loader.nodeArray = nodeArra;
////								            										var children = loader.loadAll();
////								            										Ext.getCmp('indexTreePanel').appendChild(children);
////								            										filter=new Ext.tree.TreeFilter
////								            										(
////								            											this.orgTreeForShow,
////								            											{
////								            												clearBlank:true,
////								            												autoclear:true,
////								            												ignoreFolder:true
////								            											}
////								            										);
////								            									},
////								            									failure:function(a,b,c){}
////								            								}
////								            							);
//								            							
////								            							Ext.getCmp('FORMULA').setValue(Ext.getCmp('TEMP').getValue());
////								            			    			Ext.getCmp('FORMULA_CONTENT').setValue(Ext.getCmp('TEMPCONTENT').getValue());
////								            			    			
////								            			    			Ext.getCmp('FORMULA_SHOW').setValue(Ext.getCmp('FORMULAWINDOWT').getValue());
////								            			    			Ext.getCmp('FORMULA_CONTENT_SHOW').setValue(Ext.getCmp('FORMULAWINDOWW').getValue());
////								            							
////								            							FormulaWindow.hide();
//								            						}
//								            					},
//								            					'-',
								            				 	{
								            				 		text : '返回',
								            				 		handler:function()
								            				 		{
								            				 			IndexWindow.hide();
								            				 		}
								            				 	}
								            				],
								            				items:
								            				[
																windowIndexTree,
																windowIndexGrid
								            				]
								            			}
								            		);
								                	
								                	
								                	indexTreeListstorex.load
													(
														{
															params : 
															{
																start : 0,
																limit : parseInt(pagesize_combo.getValue()),
																typeid: '1001'
															}
														}
													);
								                	
								                	IndexWindow.show();
								                    //window.location.href = basepath+'/FileDownload?filename='+res.json.filename;
								                }
								            }
									 	),
									 	{
	    					 		 		fieldLabel : 'INDEX_ID',
	    									name : 'WINDOW_INDEX_ID',
	    									id : 'WINDOW_INDEX_ID',
	    									//xtype : 'textfield', // 设置为数字输入框类型
	    									xtype : 'hidden',
	    									labelStyle: 'text-align:right;',
	    									anchor : '90%'
									 	},
									 	{
	    					 		 		fieldLabel : 'INDEX_ID',
	    									name : 'TEMP',
	    									id : 'TEMP',
	    									value:'',
	    									//xtype : 'textfield', // 设置为数字输入框类型
	    									xtype : 'hidden',
	    									labelStyle: 'text-align:right;',
	    									anchor : '90%'
									 	},
									 	{
	    					 		 		fieldLabel : 'INDEX_ID',
	    									name : 'TEMPCONTENT',
	    									id : 'TEMPCONTENT',
	    									value:'',
	    									//xtype : 'textfield', // 设置为数字输入框类型
	    									xtype : 'hidden',
	    									labelStyle: 'text-align:right;',
	    									anchor : '90%'
									 	}
	    					 		]
	    					 		
	    					 	}
					 		]
					 	}
					]
				}
			);
			
//			Ext.getCmp('WINDOW_INDEX_ID').setValue(Ext.getCmp('INDEX_ID').getValue());
//			
//			Ext.getCmp('TEMP').setValue(Ext.getCmp('FORMULA').getValue());
//			Ext.getCmp('TEMPCONTENT').setValue(Ext.getCmp('FORMULA_CONTENT').getValue());
//			
//			Ext.getCmp('FORMULAWINDOWT').setValue(Ext.getCmp('FORMULA_SHOW').getValue());
//			Ext.getCmp('FORMULAWINDOWW').setValue(Ext.getCmp('FORMULA_CONTENT_SHOW').getValue());
			FormulaWindow.show();
		}
	);
	
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
					name : 'channelName',
					xtype : 'textfield',
					fieldLabel : '*渠道名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'channelId',
					xtype : 'textfield',
					fieldLabel : '*渠道编号',
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
					fieldLabel : '客户级别',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore8,
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
					fieldLabel : '营销模板名称',
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
					name : 'channelContent',
					xtype : 'textarea',
					fieldLabel : '渠道描述',
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
					//注释掉一下代码 供德阳银行 POC demo使用 YUYZ
//					Ext.Ajax.request({
//						url : basepath + '/channelManage.json',
//						method : 'POST',
//						form : editChannelForm.getForm().id,
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//						success : function() {
//							Ext.Msg.alert('提示', '操作成功');
//							store.reload();
//						},
//						failure : function(response) {
//							var resultArray = Ext.util.JSON.decode(response.status);
//						       if(resultArray == 403) {
//						           Ext.Msg.alert('提示', response.responseText);
//						  } else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//						  }
//							store.reload();
//						}
//					});
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
	
	// 新增渠道的from
	var addChannelPanel = new Ext.Panel({
		width : 600,
		height : 250,
		layout : 'fit',
		buttonAlign : "center",
		items : [ addChannelForm]
	});
	
	// 修改渠道的from
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
		addChannelWindow.show();
	}
	
	// 定义新增窗口
	var addChannelWindow = new Ext.Window({
		title : '渠道新增',
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
		title : '渠道修改',
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