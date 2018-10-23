/**
 * 模块管理
 * @author GUOCHI
 * @since 2012-10-09
 */
Ext.onReady(function() {
    var RIDEStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [[1, 'RIDE用户登录'], [2, '配置用户登录']]
    });
    var isOuterStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [[1, '是'], [0, '否']]
    });
    var mouduleQueryForm = new Ext.form.FormPanel({//查询Form
        labelWidth : 90,
        height : 80,
        frame : true,
        labelAlign : 'middle',
        buttonAlign : 'center',
        items : [{
            layout : 'column',
			border : false,
			items : [{
			    columnWidth : .25,
				defaultType : 'textfield',
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [{
					fieldLabel : '模块名称',
					name : 'MDUL_NAME',
					id : 'MDUL_NAME',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					anchor : '90%'
				}]
			}]
		}],
		buttons : [{
			text : '查询',
			handler : function() {
	         	var conditionStr = mouduleQueryForm.getForm().getFieldValues();
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
			handler : function() {// 重置方法，查询表单重置
				mouduleQueryForm.getForm().reset();
			}
		}]
	});
	var pagesize_combo = new Ext.form.ComboBox({//每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : ['value', 'text'],
			data : [[10, '10条/页'], [20, '20条/页'],[50, '50条/页'], [100, '100条/页'],[250, '250条/页'], [500, '500条/页']]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});

	pagesize_combo.on("select", function(comboBox) {//改变每页显示条数reload数据
		pageBar.pageSize = parseInt(pagesize_combo.getValue()), store.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var multiSm = new Ext.grid.CheckboxSelectionModel();//复选框
	var rownum = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	var mouduleColumns = new Ext.grid.ColumnModel([rownum, multiSm,//列模型 
		{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 120},
		{header : '模块名称',dataIndex : 'mdulName',sortable : true,width : 100}, 
		{header : '模块描述',dataIndex : 'mdulDesc',sortable : true,width : 200}, 
		{header : '功能点数量',dataIndex : 'amount',sortable : true,width : 100}, 
		{header : '创建日期',dataIndex : 'crtDate' ,sortable : true,width : 220}]);
	var mouduleQueryRecord = Ext.data.Record.create([
		{name : 'id',       mapping : 'ID'},
		{name : 'mdulName',	mapping : 'MDUL_NAME'},
		{name : 'mdulDesc',	mapping : 'MDUL_DESC'},
		{name : 'crtDate',	mapping : 'CRT_DATE'},
		{name : 'isOuter',	mapping : 'IS_OUTER'},
		{name : 'userName',	mapping : 'USER_NAME'},
		{name : 'userKey',  mapping : 'USER_KEY'},
		{name : 'url',      mapping : 'URL'},
		{name : 'pwdKey',   mapping : 'PWD_KEY'	},
		{name : 'password', mapping : 'PASSWORD'}, 
		{name : 'isRide',   mapping : 'IS_RIDE'	},
		{name : 'amount',   mapping : 'AMOUNT'}]);
	var store = new Ext.data.Store({//查询数据源
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/moduleManagerQuery.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, mouduleQueryRecord)
	});
	var pageBar = new Ext.PagingToolbar({//分页工具栏
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});
	store.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
    	}
	});
	var mouduleGrid = new Ext.grid.GridPanel({//显示查询结果Grid
		store : store,
		frame : true,
		sm : multiSm,
		cm : mouduleColumns,
		stripeRows : true,
		region : 'center',
		frame : true,
		tbar : [{
			text : '新增',
			iconCls : 'addIconCss',
			handler : function() {// 调用新增方法
				addPlanForm.getForm().reset();
				Ext.getCmp('com0').setValue(0);
				 for (var i = 1; i < 7; i++) {
				     Ext.getCmp('com' + i).hide();
				 }
				addPlanWindow.show();
			}
		}, '-', {
    		text : '修改',
			iconCls : 'editIconCss',
			handler : function() { // 调用修改方法
				var _record = mouduleGrid.getSelectionModel().getSelected();
				if (!_record) {
					Ext.MessageBox.alert('系统提示信息', '请选择要操作的记录！');
					return false;
				} else {
					var checkedNodes = mouduleGrid.getSelectionModel().selections.items;
					if (checkedNodes.length > 1) {
						Ext.MessageBox.alert('系统提示信息', '您选择的记录过多！');
						return false;
					}
    				editBasePlanForm.getForm().loadRecord(_record);
					if (Ext.getCmp('com00').getValue() == 1) {
						Ext.getCmp('com01').show();
						Ext.getCmp('com02').show();
						Ext.getCmp('com03').show();
						Ext.getCmp('com06').show();
					} else {
						Ext.getCmp('com01').hide();
						Ext.getCmp('com02').hide();
						Ext.getCmp('com03').hide();
						Ext.getCmp('com06').hide();
					}
    				if (Ext.getCmp('com06').getValue() == 2) {
						Ext.getCmp('com04').show();
						Ext.getCmp('com05').show();
					} else {
						Ext.getCmp('com04').hide();
						Ext.getCmp('com05').hide();
					}
					editPlanWindow.show();
				}
			}
		}, '-', {
			text : '删除',
			iconCls : 'deleteIconCss',
			handler : function() { // 调用删除方法
				var _record = mouduleGrid.getSelectionModel().getSelected();
				if (!_record) {
					Ext.MessageBox.alert('系统提示信息', '请选择要删除的的模块！');
					return false;
    			} else {
					var checkedNodes = mouduleGrid.getSelectionModel().selections.items;
        			if (checkedNodes.length > 1) {
						Ext.MessageBox.alert('系统提示信息', '您选择的记录过多！');
						return false;
					}
					if (confirm("确定删除吗?")) {
						var idStr = mouduleGrid.getSelectionModel().getSelections()[0].data.id;
						Ext.Ajax.request({
							url : basepath	+ '/FwModule-action!destroy.json?idStr=' + idStr,
							waitMsg : '正在删除数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('系统提示信息', '操作成功');
								store.reload();
							},
							failure : function() {
								Ext.Msg.alert('系统提示信息', '操作失败');
							}
						});
					}
				}
			}
		}, '-', {
			text : '功能点维护', // 展示functionManager.js中的功能点维护窗口
			iconCls : 'detailIconCss',
			handler : function() { 
				var _record = mouduleGrid.getSelectionModel().getSelected();
				if (!_record) {
	   				Ext.MessageBox.alert('系统提示信息', '请选择要操作的记录！');
					return false;
				} else {
					var checkedNodes = mouduleGrid.getSelectionModel().selections.items;
					if (checkedNodes.length > 1) {
        				Ext.MessageBox.alert('系统提示信息', '您选择的记录过多！');
						return false;
					}
					moduleId = checkedNodes[0].data.id;
					Ext.Ajax.request({// 左侧模块功能树的Ajax请求事件
    					url : basepath	+ '/fwFunctionTree-action.json?mdulId='	+ moduleId,
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
							loader.nodeArray = nodeArra;// 拿到从后台返回的数据
							loader.refreshCache();// 刷新缓存
							var children = loader.loadAll();// 得到相应的树数据
							leftTreeForShows.root.removeAll(true); // 清掉以前的数据
							leftTreeForShows.appendChild(children);// 把数据重新填充
						}
					});
					functionManage.getForm().reset();
					Ext.getCmp('fwFunId').setValue('');
					Ext.getCmp('funcManager').setDisabled(true);
					viewWindow.setTitle("系统设置-->模块管理-->"+checkedNodes[0].data.mdulName);
//					functionManage.setWidth(functionManage.getInnerWidth());
					viewWindow.show();
					controllerStore.load();
				}
			}
		}],
    	bbar : pageBar, // 分页工具栏
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var addPlanForm = new Ext.form.FormPanel({// 新增窗口展示的from
		frame : true,
		labelAlign : 'right',
		region : 'center',
		buttonAlign : "center",
		items : [{
				layout : 'form',
				items : [{
				    name : 'mdulName',
				    xtype : 'textfield',
				    fieldLabel : '<font color=red>*</font>模块名称',
				    allowBlank : false,
				    anchor : '90%'
				}, {
				    xtype : 'textarea',
				    fieldLabel : '模块描述',
				    maxLength : 120,
				    name : 'mdulDesc',
				    anchor : '90%'
				}, {
				    id : 'com0',
				    fieldLabel : '<font color=red>*</font>是否外部系统',
				    name : 'isOuter',
				    xtype : 'combo',
				    triggerAction : 'all',
				    mode : 'local',
				    editable : false,
				    store : isOuterStore,
				    valueField : 'key',
				    displayField : 'value',
				    emptyText : '请选择',
				    anchor : '90%'
				}, {
				    id : 'com6',
				    fieldLabel : '<font color=red>*</font>登录方式',
				    name : 'isRide',
				    xtype : 'combo',
				    triggerAction : 'all',
				    mode : 'local',
				    editable : false,
				    store : RIDEStore,
				    valueField : 'key',
				    displayField : 'value',
				    emptyText : '请选择',
				    anchor : '90%'
				}, {
				    id : 'com1',
				    xtype : 'textfield',
				    fieldLabel : '<font color=red>*</font>系统URL',
				    name : 'url',
				    anchor : '90%'
				}, {
				    id : 'com2',
				    xtype : 'textfield',
				    fieldLabel : '<font color=red>*</font>用户变量名称',
				    name : 'userKey',
				    anchor : '90%'
				}, {
				    id : 'com3',
				    xtype : 'textfield',
				    fieldLabel : '<font color=red>*</font>密码变量名称',
				    name : 'pwdKey',
				    anchor : '90%'
				}, {
				    id : 'com4',
				    xtype : 'textfield',
				    fieldLabel : '<font color=red>*</font>用户名',
				    name : 'userName',
				    anchor : '90%'
				}, {
				    id : 'com5',
				    xtype : 'textfield',
				    width : 200,
				    fieldLabel : '<font color=red>*</font>密码',
				    name : 'password',
				    anchor : '90%'
				}, {
				    xtype : 'textfield',
				    width : 200,
				    fieldLabel : '创建日期',
				    hidden : true,
				    id : 'crtDate',
				    name : 'crtDate',
				    anchor : '90%'
				}]
		}, {
		    layout : 'form',
			buttonAlign : 'center',
			buttons : [{
			    text : '保  存',
				handler : function() {
    			    if(!addPlanForm.getForm().isValid()){
                        Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                        return false;
                    }
					Ext.Ajax.request({
					    url : basepath + '/FwModule-action.json',
					    method : 'POST',
					    params : addPlanForm.getForm().getFieldValues(),
					    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					    success : function() {
					        Ext.Msg.alert('提示', '操作成功！');
					        store.reload();
					    },
					    failure : function(response) {
					        var resultArray = Ext.util.JSON.decode(response.status);
					        if (resultArray == 403) {
					            Ext.Msg.alert('提示',response.responseText);
					        } else {
					            Ext.Msg.alert('提示','操作失败,失败原因:' + response.responseText);
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
			}]
		}]
	});
	var editBasePlanForm = new Ext.form.FormPanel({// 修改基本信息展示的form
		frame : true,
		labelAlign : 'right',
		region : 'center',
		buttonAlign : "center",
		items : [{
		    layout : 'form',
		    items : [{
		        xtype : 'numberfield',
		        hidden : true,
		        id : 'id0',
		        name : 'id'
		    }, {
		        xtype : 'textfield',
		        fieldLabel : '<font color=red>*</font>模块名称',
		        allowBlank : false,
		        name : 'mdulName',
		        anchor : '90%'
		    }, {
		        xtype : 'textarea',
		        fieldLabel : '模块描述',
		        maxLength : 120,
		        name : 'mdulDesc',
		        anchor : '90%'
		    }, {
		        id : 'com00',
		        fieldLabel : '<font color=red>*</font>是否外部系统',
		        name : 'isOuter',
		        xtype : 'combo',
		        triggerAction : 'all',
		        mode : 'local',
		        editable : false,
		        store : isOuterStore,
		        valueField : 'key',
		        displayField : 'value',
		        emptyText : '请选择',
		        anchor : '90%'
		    }, {
		        id : 'com06',
		        fieldLabel : '<font color=red>*</font>登录方式',
		        name : 'isRide',
		        xtype : 'combo',
		        triggerAction : 'all',
		        mode : 'local',
		        editable : false,
		        store : RIDEStore,
		        valueField : 'key',
		        displayField : 'value',
		        emptyText : '请选择',
		        anchor : '90%'
		    }, {
		        id : 'com01',
		        xtype : 'textfield',
		        fieldLabel : '<font color=red>*</font>系统URL',
		        name : 'url',
		        anchor : '90%'
		    }, {
		        id : 'com02',
		        xtype : 'textfield',
		        fieldLabel : '<font color=red>*</font>用户变量名称',
		        name : 'userKey',
		        anchor : '90%'
		    }, {
		        id : 'com03',
		        xtype : 'textfield',
		        fieldLabel : '<font color=red>*</font>密码变量名称',
		        name : 'pwdKey',
		        anchor : '90%'
		    }, {
		        id : 'com04',
		        xtype : 'textfield',
		        fieldLabel : '<font color=red>*</font>用户名',
		        name : 'userName',
		        hidden : true,
		        anchor : '90%'
		    }, {
		        id : 'com05',
		        xtype : 'textfield',
		        fieldLabel : '<font color=red>*</font>密码',
		        name : 'password',
		        hidden : true,
		        anchor : '90%'
		    }, {//创建日期
		        xtype : 'textfield',
		        hidden : true,
		        name : 'crtDate',
		        anchor : '90%'
		    }]
		}, {
			layout : 'form',
			buttonAlign : 'center',
			buttons : [{
				text : '保  存',
				handler : function() {
					if (!editBasePlanForm.getForm().isValid()) {
						Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
						return false;
					}
					Ext.Ajax.request({
					    url : basepath + '/FwModule-action.json',
					    method : 'POST',
					    params : editBasePlanForm.getForm().getFieldValues(),
					    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					    success : function() {
					        Ext.Msg.alert('提示', '操作成功');
					        store.reload();
					    },
					    failure : function(response) {
					        var resultArray = Ext.util.JSON.decode(response.status);
					        if (resultArray == 403) {
					            Ext.Msg.alert('提示',response.responseText);
					        } else {
					            Ext.Msg.alert('提示','操作失败,失败原因:'+ response.responseText);
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
			}]
		}]
	});
	var addPlanWindow = new Ext.Window({// 定义新增窗口
		title : '模块新增',
		width : 600,
		draggable : true,  //可拖动，默认为false
		closable : true,
		closeAction : 'hide',
		modal : true,     //对其后面的一切内容进行灰显
		border : false,   //隐藏面板body元素的边框
		items : [addPlanForm]
	});
	var editPlanWindow = new Ext.Window({// 定义修改窗口
	    title : '模块修改',
	    width : 600,
	    draggable : true,//可拖动，默认为false
	    closable : true,
	    closeAction : 'hide',
	    modal : true, // 对其后面的一切内容进行灰显
	    border : false,//隐藏面板body元素的边框
	    items : [editBasePlanForm]
	});
	/** *####################################新增单选按钮联动###########################################** */
	var cb = Ext.getCmp('com0');// 是否外部系统
	var RIDE = Ext.getCmp('com6');// 是否RIDE方式登录
	cb.on('select', function() {
		for (var i = 1; i < 7; i++) {
			Ext.getCmp('com' + i).setValue('');
			Ext.getCmp('com' + i).hide();
		}
		if (cb.getValue() == 1) {
			Ext.getCmp('com1').show();
			Ext.getCmp('com2').show();
			Ext.getCmp('com3').show();
			Ext.getCmp('com6').show();
		} else if (cb.getValue() == 0) {
		    Ext.getCmp('com6').hide();
		    Ext.getCmp('com3').hide();
		    Ext.getCmp('com2').hide();
		    Ext.getCmp('com1').hide();
		}
	});
	RIDE.on('select', function() {
	    Ext.getCmp('com4').setValue('');
	    Ext.getCmp('com5').setValue('');
	    if (RIDE.getValue() == 2) {
	        Ext.getCmp('com4').show();
	        Ext.getCmp('com5').show();
	    } else if (RIDE.getValue() == 1) {
	        Ext.getCmp('com5').hide();
	        Ext.getCmp('com4').hide();
	    }
	});
	Ext.getCmp('com6').hide();
	Ext.getCmp('com5').hide();
	Ext.getCmp('com4').hide();
	Ext.getCmp('com3').hide();
	Ext.getCmp('com2').hide();
	Ext.getCmp('com1').hide();
	/** *####################################修改单选按钮联动###########################################** */
	var cb0 = Ext.getCmp('com00');// 是否外部系统
	var RIDE0 = Ext.getCmp('com06');// 是否RIDE方式登录
	cb0.on('select', function() {
	    for (var i = 1; i < 7; i++) {
	        Ext.getCmp('com0' + i).setValue('');
	        Ext.getCmp('com0' + i).hide();
	    }
	    if (cb0.getValue() == 1) {
	        Ext.getCmp('com01').show();
	        Ext.getCmp('com02').show();
	        Ext.getCmp('com03').show();
	        Ext.getCmp('com06').show();
	    } else if (cb0.getValue() == 0) {
	        Ext.getCmp('com06').hide();
	        Ext.getCmp('com03').hide();
	        Ext.getCmp('com02').hide();
	        Ext.getCmp('com01').hide();
	    }
	});
	RIDE0.on('select', function() {
	    Ext.getCmp('com04').setValue('');
	    Ext.getCmp('com05').setValue('');
	    if (RIDE0.getValue() == 2) {
	        Ext.getCmp('com04').show();
	        Ext.getCmp('com05').show();
	    } else if (RIDE0.getValue() == 1) {
	        Ext.getCmp('com05').hide();
	        Ext.getCmp('com04').hide();
	    }
	});
	Ext.getCmp('com06').hide();
	Ext.getCmp('com05').hide();
	Ext.getCmp('com04').hide();
	Ext.getCmp('com03').hide();
	Ext.getCmp('com02').hide();
	Ext.getCmp('com01').hide();
	var viewport = new Ext.Viewport({//整体显示布局
	    layout : 'fit',
	    items:[{
	        layout : 'border',
    	    items : [{
    	        region : 'north',
    	        title : "模块管理",
    	        height : 106,
    	        margins : '0 0 0 0',
    	        items : [mouduleQueryForm]
    	    }, {
    	        region : 'center',
    	        layout:'fit',
    	        hight : document.body.scrollHight - 106,
    	        autoScroll : true,
    	        margins : '0 0 0 0',
    	        items : [mouduleGrid]
    	    }]
	    }]
	});
	/**#######################################################################*/
	viewWindow.on('hide',function(){
	    store.reload();
	});
});