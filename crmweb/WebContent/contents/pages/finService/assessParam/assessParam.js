Ext.onReady(function() {
	// 客户风险特性
	var riskCharactStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=RISK_CHARACT'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	riskCharactStore.load();

	// 客户风险信息表格面板
	var sm = new Ext.grid.CheckboxSelectionModel( {
		singleSelect : true
	});
	
	var rownum = new Ext.grid.RowNumberer( {
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel( [ rownum, {
		header : '参数名',
		dataIndex : 'paramName',
		sortable : true,
		menuDisabled : true,
		width : document.body.scrollWidth / 4,
		renderer : function(value) {
			if (value != '') {
				var index = riskCharactStore.find('key', value);
				return riskCharactStore.getAt(index).get('value');
			}

		}
		}, {
			header : '起始值',
			dataIndex : 'initScore',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
		}, {
			header : '截止值',
			dataIndex : 'endScore',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
	
		}, {
			header : '维护人',
			dataIndex : 'updaterId',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
	
		}, {
			header : '维护时间',
			dataIndex : 'updaterDate',
			sortable : true,
			menuDisabled : true,
			width : document.body.scrollWidth / 6
	
		} 
	]);
	/**
	 * 数据存储
	 */

	var store = new Ext.data.Store( {
		restful : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/AssessParam.json'
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty : 'json.count',
			root : 'json.data'
			}, [ {
				name : 'initScore',
				mapping : 'INIT_SCORE'
			}, {
				name : 'endScore',
				mapping : 'END_SCORE'
			}, {
				name : 'paramName',
				mapping : 'PARAM_NAME'
			}, {
				name : 'updaterId',
				mapping : 'USER_NAME'
			}, {
				name : 'updaterDate',
				mapping : 'UPDATE_DATE'
			}, {
				name : 'paramId',
				mapping : 'PARAM_ID'
			} 
		])
	});

	var pagesize_combo = new Ext.form.ComboBox( {
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore(
				{
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
							[ 50, '50条/页' ], [ 100, '100条/页' ],
							[ 250, '250条/页' ], [ 500, '500条/页' ] ]
				}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var number = parseInt(pagesize_combo.getValue());
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var bbar = new Ext.PagingToolbar( {
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	var tbar = new Ext.Toolbar({
		items : [ {
			text : '修改',
			iconCls : 'editIconCss',
			handler : function() {
				var records = riskGrid.getSelectionModel().getSelections();
				var recordsLen = records.length;
				if (recordsLen != 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行修改！");
					return;
				} else {
					var record = riskGrid.getSelectionModel().getSelected();

								var paramCode = record.get('paramName');
								var index = riskCharactStore.find('key',paramCode);
								var paramName = riskCharactStore.getAt(index).get('value');

					var paramName = record.get('paramName');
					Ext.getCmp('paramName').setValue(paramName);
								Ext.getCmp('paramCode').setValue(paramCode);
					Ext.getCmp('initScore').setValue(record.get('initScore'));
					Ext.getCmp('endScore').setValue(record.get('endScore'));
					Ext.getCmp('paramId').setValue(record.get('paramId'));
					opWin.show();
				}
			}
		} ]
			});
	store.load( {
		params : {
			start : 0,
			limit : bbar.pageSize
		}
	});

	var opForm = new Ext.form.FormPanel( {
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 80,
		items : [ {
			layout : 'column',
			border : false,
			items : [ {
				columnWidth : 1 / 3,
				layout : 'form',
				labelWidth : 70, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [ {
					fieldLabel : '参数名',
					id : 'paramName',
					xtype : 'textfield', // 设置为数字输入框类型
					labelStyle : 'text-align:right;',
					disabled : true,
					labelSeparator:''
				}
					, {
						xtype : 'hidden',
						name : 'paramCode',
						id : 'paramCode'
					}
				, {
					xtype : 'hidden',
					name : 'paramId',
					id : 'paramId'
				} ]
			}, {
				columnWidth : 1 / 3,
				layout : 'form',
				labelWidth : 70, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [ {
					fieldLabel : '起始值',
					id : 'initScore',
					xtype : 'numberfield', // 设置为数字输入框类型
					labelStyle : 'text-align:right;',
					allowNegative : true,
					labelSeparator:''
				} ]
			}, {
				columnWidth : 1 / 3,
				layout : 'form',
				labelWidth : 70, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [ {
					fieldLabel : '截止值',
					id : 'endScore',
					xtype : 'numberfield', // 设置为数字输入框类型
					labelStyle : 'text-align:right;',
					allowNegative : true,
					labelSeparator:''
				} ]
			} ]
		} ],
		buttons : [{
			text : '保存',
			handler : function() {
				Ext.Ajax.request( {
					url : basepath + '/AssessParam!saveAssessParam.json',
					// form : teamForm.form.id,
					method : 'POST',
					params : {
						paramName : Ext.getCmp('paramName').getValue(),
						paramId : Ext.getCmp('paramId').getValue(),
						initScore : Ext.getCmp('initScore').getValue(),
						endScore : Ext.getCmp('endScore').getValue()
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('评分参数', '保存失败！');
					},
					success : function(response) {
						Ext.MessageBox.alert('评分参数', '保存成功！');
						opWin.hide();
					}
				});
			}
		}, {
			text : '取消',
			handler : function() {
				opWin.hide();
			}
		} ]
	});

	var opWin = new Ext.Window( {
		resizable : false,
		collapsible : false,
		draggable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		animCollapse : false,
		border : false,
		loadMask : true,
		closable : true,
		constrain : true,
		id : 'opWin',
		width : 660,
		height : 120,
		title : '在线评估',
		items : [ opForm ]
	});
	opWin.on('hide', function() {
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});

	// create the Grid
	var riskGrid = new Ext.grid.GridPanel( {
		frame : true,
		autoScroll : true,
		title : "理财规划->风险评估评分标准参数",
		region : 'center', // 返回给页面的div
		store : store,
		stripeRows : true, // 斑马线
		cm : cm,
		sm : sm,
		tbar : tbar,
		bbar : bbar,
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	/*******************整体显示布局******************/
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [riskGrid]
	});

});