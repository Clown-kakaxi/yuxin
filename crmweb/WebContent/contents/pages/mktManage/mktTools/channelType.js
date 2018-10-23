	 // 复选框
	var typeSm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var typeRownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	
	var isSmallStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=IS_SMALL'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	// 定义列模型
	var typeCm = new Ext.grid.ColumnModel([typeRownum,typeSm, 
	            {
				header : '渠道类型名称',
				dataIndex : 'channelTypeName',
				sortable : true,
				width : 100
			}, {
				header : '是否中小企业',
				dataIndex : 'isSmallOra',
				sortable : true,
				width : 100
			}, {
				header : '创建人',
				dataIndex : 'CREATE_USER_NAME',
				sortable : true,
				width : 100
			},{
				header : '创建日期',
				dataIndex : 'createDate',
				sortable : true,
				width : 100
			}, {
				header : '最近更新人',
				dataIndex : 'UPDATE_USER_NAME',
				sortable : true,
				width : 100
			},{
				header : '最近更新日期',
				dataIndex : 'updateDate',
				sortable : true,
				width : 100
			}
			]);
	
	var typeRecord = Ext.data.Record.create([ {
		name : 'channelTypeId',mapping : 'CHANNEL_TYPE_ID'
	}, {
		name : 'channelTypeName',mapping : 'CHANNEL_TYPE_NAME'
	}, {
		name : 'isSmall',mapping : 'IS_SMALL'
	},{
		name : 'isSmallOra',mapping : 'IS_SMALL_ORA'
	},{
		name : 'createDate',mapping : 'CREATE_DATE'
	},{
		name : 'CREATE_USER_NAME'
	},{
		name : 'createUser',mapping : 'CREATE_USER'
	}, {
		name : 'updateDate',mapping : 'UPDATE_DATE'
	}, {
		name : 'updateUser',mapping : 'UPDATE_USER'
	},{
		name : 'UPDATE_USER_NAME'
	}]);

	/**
	 * 数据存储
	 */
	var typeStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
		url : basepath+'/channelTypeQuery.json'
	}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
	        idProperty: 'CHANNEL_TYPE_ID',
	        messageProperty: 'message',
			root : 'json.data',
			totalProperty: 'json.count'
	}, typeRecord)
		});
	
	// 每页显示条数下拉选择框
	var type_pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
	});

	// 改变每页显示条数reload数据
	type_pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(type_pagesize_combo.getValue()),
		typeStore.reload({
			params : {
				start : 0,
				limit : parseInt(type_pagesize_combo.getValue())
			}
		});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(type_pagesize_combo.getValue()),
		store : typeStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', type_pagesize_combo ]
	});

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '新增类型',
					handler : function() {
						addTypeInit();
					}
				},'-',{
					text : '修改类型',
					handler : function() {
						
						var selectLength = typeGrid.getSelectionModel()
						.getSelections().length;
						
						var selectRe = typeGrid.getSelectionModel()
								.getSelections()[0];
						
						if(selectLength != 1){
							Ext.Msg.alert('提示','请选择一条记录!');
						} 
						else {
							editTypeForm.getForm().loadRecord(selectRe);
							editTypeInit();
						}
					}
				},'-',{
					text : '删除类型',
					handler : function() {
						 var selectLength = typeGrid.getSelectionModel()
							.getSelections().length;
							
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
								for(var i = 0; i<selectLength;i++)
								{
									selectRe = typeGrid.getSelectionModel()
									.getSelections()[i];
									tempId = selectRe.data.channelTypeId;
									idStr += tempId;
									if( i != selectLength-1)
										idStr += ',';
								}
								Ext.Ajax.request({
									url : basepath+'/channel-type/'
											+tempId+'/batchDestroy.json?idStr='+idStr,
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function() {
										Ext.Msg.alert('提示', '操作成功');
										typeStore.reload();
									},
									failure : function(response) {
										Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
										typeStore.reload();
									}
								});
					        	});
								}
					            }
							}
				]
			});
	
	
	// 新增窗口展示的from
	var addTypeForm = new Ext.form.FormPanel({
	            labelWidth : 150,
	            height : 300,
	            frame : true,
	            region : 'center',
	            autoScroll : true,
	            buttonAlign : 'center',
	            items : [{
	                layout : 'column',
	                items : [{
	                            columnWidth : .5,
	                            layout : 'form',
	                            items : [{
	                                        name : 'channelTypeName',
	                                        xtype : 'textfield',
	                                        defaultType : 'textfield',
	                                        allowBlank : false,
	                                        maxLength:200,
	            							blankText : '渠道类型名称不能为空',
	                                        fieldLabel : '渠道类型名称',
	                                        width : '100',
	                                        anchor : '90%'
	                                    }]

	                },{
                        columnWidth : .5,
                        layout : 'form',
                        items : [
								{
							store : isSmallStore,
							xtype : 'combo', resizable : true,
							name : 'isSmall',
							allowBlank : false,
							blankText : '是否中小企业不能为空',
							hiddenName : 'isSmall',
							fieldLabel : '是否中小企业',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
								}
                                 ]

            }]},{
	        			layout : 'form',
	        			buttonAlign : 'center',
	            buttons : [

	            {

	                        text : '保  存',
	                        handler : function()  {
	                    		if(!addTypeForm.getForm().isValid())
								{ 
	                    			Ext.Msg.alert('提示', '输入不合法，请重新输入');
									return false;
								}	
	            Ext.Ajax.request({
	                url : basepath+'/channel-type.json',
	                method : 'POST',
	                form : addTypeForm.getForm().id,
	                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                success : function() {
	                    Ext.Msg.alert('提示', '操作成功');
	                    typeStore.reload();
	                },
	                failure : function(response) {
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	                    typeStore.reload();
	                }
	            });
	            addTypeWindow.hide();
	        }

	                    }, {
	                        text : '取  消',
	                        handler : function() {
	                            addTypeWindow.hide();
	                        }
	                    }]

	        }]});
	
	// 修改窗口展示的from
	var editTypeForm = new Ext.form.FormPanel({
	            labelWidth : 150,
	            height : 300,
	            frame : true,
	            region : 'center',
	            autoScroll : true,
	            buttonAlign : 'center',
	            items : [{
	                layout : 'column',
	                items : [{
                        columnWidth : .5,
                        layout : 'form',
                        items : [{
                                    name : 'channelTypeName',
                                    defaultType : 'textfield',
                                    allowBlank : false,
                                    maxLength:200,
        							blankText : '渠道类型名称不能为空',
                                    xtype : 'textfield',
                                    fieldLabel : '渠道类型名称',
                                    width : '100',
                                    anchor : '90%'
                                }]

            },{
                columnWidth : .5,
                layout : 'form',
                items : [{
					store : isSmallStore,
					xtype : 'combo', resizable : true,
					name : 'isSmall',
					allowBlank : false,
					blankText : '是否中小企业不能为空',
					hiddenName : 'isSmall',
					fieldLabel : '是否中小企业',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					selectOnFocus : true,
					width : '100',
					anchor : '90%'
						}]

    }]},{
	        			layout : 'form',
	        			buttonAlign : 'center',
	        			items : [ {
	        				xtype : 'hidden',
	        				width : 200,
	        				fieldLabel : '创建人',
	        				name : 'createUser',
	        				anchor : '90%'
	        			}, {
	        				xtype : 'hidden',
	        				width : 200,
	        				fieldLabel : '创建日期',
	        				name : 'createDate',
	        				anchor : '90%'
	        			}, {
	        				xtype : 'hidden',
	        				width : 200,
	        				fieldLabel : '最近更新人',
	        				name : 'updateUser',
	        				anchor : '90%'
	        			},{
	        				xtype : 'hidden',
	        				width : 200,
	        				fieldLabel : '最近更新日期',
	        				name : 'updateDate',
	        				anchor : '90%'
	        			},{
			        				xtype : 'hidden',
			        				width : 200,
			        				fieldLabel : '渠道类型ID',
			        				name : 'channelTypeId',
			        				anchor : '90%'
			        			}],


	            buttons : [

	            {

	                        text : '保  存',
	                        handler : function()  {
	                        	if(!editTypeForm.getForm().isValid())
								{ 
	                        		Ext.Msg.alert('提示', '渠道类型名称、是否中小企业不能为空');
									return false;
								}	
	            Ext.Ajax.request({
	                url : basepath+'/channel-type.json',
	                method : 'POST',
	                form : editTypeForm.getForm().id,
	                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                success : function() {
	                    Ext.Msg.alert('提示', '操作成功');
	                    typeStore.reload();
	                },
	                failure : function(response) {
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	                    typeStore.reload();
	                }
	            });
	            editTypeWindow.hide();
	        }

	                    }, {
	                        text : '取  消',
	                        handler : function() {
	                            editTypeWindow.hide();
	                        }
	                    }]

	        }]});
	
	
	// 定义新增窗口
	var addTypeWindow = new Ext.Window({
	            title : '渠道类型新增',
	            plain : true,
	            layout : 'fit',
	            width : 800,
	            height : 200,
	            resizable : true,
	            draggable : true,
	            closable : true,
	            closeAction : 'hide',
	            modal : true, // 模态窗口
	            loadMask : true,
	            maximizable : true,
	            collapsible : true,
	            titleCollapse : true,
	            buttonAlign : 'right',
	            border : false,
	            items : [addTypeForm]
	        });
	
	// 定义修改窗口
	var editTypeWindow = new Ext.Window({
	            title : '渠道类型修改',
	            plain : true,
	            layout : 'fit',
	            width : 800,
	            height : 200,
	            resizable : true,
	            draggable : true,
	            closable : true,
	            closeAction : 'hide',
	            modal : true, // 模态窗口
	            loadMask : true,
	            maximizable : true,
	            collapsible : true,
	            titleCollapse : true,
	            buttonAlign : 'right',
	            border : false,
	            items : [editTypeForm]
	        });

    function addTypeInit()
    {
    	addTypeWindow.show();
    };
    
    function editTypeInit()
    {
    	editTypeWindow.show();
    };
	
	// 表格实例
	var typeGrid = new Ext.grid.GridPanel({
				height : 500,
				frame : true,
				autoScroll : true,
				region : 'center', 
				store : typeStore, 
				stripeRows : true, // 斑马线
				cm : typeCm, // 列模型
				sm : typeSm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
