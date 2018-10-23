/**
 *  指标管理
 * @author yxl
 * @since 2013-2-19
 */
Ext.onReady
(
	function() 
	{
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
						text : '公式校验',
						handler:function()
						{
							alert("公式校验通过。");
//							Ext.getCmp('FORMULA').setValue(Ext.getCmp('TEMP').getValue());
//							Ext.getCmp('FORMULA_CONTENT').setValue(Ext.getCmp('TEMPCONTENT').getValue());
//							
//							Ext.getCmp('FORMULA_SHOW').setValue(Ext.getCmp('FORMULAWINDOWT').getValue());
//							Ext.getCmp('FORMULA_CONTENT_SHOW').setValue(Ext.getCmp('FORMULAWINDOWW').getValue());
//							
//							FormulaWindow.hide();
						}
					},
					'-',
					{
						text : '确定',
						handler:function()
						{
//							Ext.Ajax.request
//							(
//								{
//									url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
//									method:'GET',
//									success:function(response)
//									{
//										nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
//										loader.nodeArray = nodeArra;
//										var children = loader.loadAll();
//										Ext.getCmp('indexTreePanel').appendChild(children);
//										filter=new Ext.tree.TreeFilter
//										(
//											this.orgTreeForShow,
//											{
//												clearBlank:true,
//												autoclear:true,
//												ignoreFolder:true
//											}
//										);
//									},
//									failure:function(a,b,c){}
//								}
//							);
							
							Ext.getCmp('FORMULA').setValue(Ext.getCmp('TEMP').getValue());
			    			Ext.getCmp('FORMULA_CONTENT').setValue(Ext.getCmp('TEMPCONTENT').getValue());
			    			
			    			Ext.getCmp('FORMULA_SHOW').setValue(Ext.getCmp('FORMULAWINDOWT').getValue());
			    			Ext.getCmp('FORMULA_CONTENT_SHOW').setValue(Ext.getCmp('FORMULAWINDOWW').getValue());
							
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
		//定义指标类型树查询码
		var condition = {searchType:'SUBINDEXTREE'};
		
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
		
		//指标类型树树panel
		var indexTreeType = new Com.yucheng.bcrm.TreePanel
		(
			{
				tbar :
				[
					{
					    text : '新建',
					    iconCls : 'addIconCss',
					    handler : function() 
					    {
	//				    	var selectLength = listPanel.getSelectionModel().getSelections().length;
	//				
	//				        var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//				
	//				        if (selectLength != 1) 
	//				        {
	//				            Ext.Msg.alert("提示", "请选择一条记录!");
	//				        } 
	//				        else
	//				        {
	//				            editBasePlanForm.getForm().loadRecord(selectRe);
	//				            document.getElementById('idStr').value = selectRe.data.id;
	//				            editInit();
	//				        }
					    }
					
					},
					'-',
					{
					    text : '修改',
					    iconCls : 'editIconCss',
					    handler : function() 
					    {
	//				        // 得到选中记录
	//					    var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//					
	//					    var selectLength = listPanel.getSelectionModel().getSelections().length;
	//					    if (selectLength != 1) 
	//					    {
	//					        // alert('请选择一条记录');
	//					        Ext.Msg.alert("提示", "请选择一条记录!");
	//					    } 
	//					    else
	//					    {
	//					        showChanceForm.getForm().loadRecord(selectRe);
	//					        showInit();
	//					    }
						}
					},
					'-',
					{
					    text : '删除',
					    iconCls : 'deleteIconCss',
					    handler : function() 
					    {
	//				        // 得到选中记录
	//					    var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//					
	//					    var selectLength = listPanel.getSelectionModel().getSelections().length;
	//					    if (selectLength != 1) 
	//					    {
	//					        // alert('请选择一条记录');
	//					        Ext.Msg.alert("提示", "请选择一条记录!");
	//					    } 
	//					    else
	//					    {
	//					        showChanceForm.getForm().loadRecord(selectRe);
	//					        showInit();
	//					    }
						}
					}
				],
				id:'indexTreePanel',
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
					indexTreeListstore.load
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
		
		//指标列表
		
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
		
		var indexTreeListstore = new Ext.data.Store
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
            forceSelection : true,
            width : 85
        });
		
		// 分页工具栏
        var bbar = new Ext.PagingToolbar( {
            pageSize : parseInt(pagesize_combo.getValue()),
            store : indexTreeListstore,
            displayInfo : true,
            displayMsg : '显示{0}条到{1}条,共{2}条',
            emptyMsg : "没有符合条件的记录",
            items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
        });
		
		indexTreeListstore.load
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
	//debugger;
		var indexTreeList = new Ext.grid.GridPanel
		(
			{
				bbar : bbar,
				tbar :
				[
					{
					    text : '新建基础指标',
					    iconCls : 'addIconCss',
					    handler : function() 
					    {
					    	indexTreeEdit.removeAll(true);
					    	indexTreeEdit.add
							(
								{
									columnWidth : .99,
									layout : 'form',
									labelWidth : 70, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : 
									[
									 	{
											fieldLabel : '指标名称',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '指标编号',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '指标类型',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '标识类'],
															['2', '数量类'],
															['3', '金额类'],
															['4', '费率类'],
															['5', '比值类']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										{
											fieldLabel : '指标描述',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '指标对应数据集',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '客户数据集'],
															['2', '产品数据集']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '指标对应字段',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', 'AUM'],
															['2', '贡献度']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										{
											fieldLabel : '维护人',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '维护日期',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										}
//										
									]
								}
							);
							
					    	indexTreeEdit.doLayout();
	//				    	var selectLength = listPanel.getSelectionModel().getSelections().length;
	//				
	//				        var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//				
	//				        if (selectLength != 1) 
	//				        {
	//				            Ext.Msg.alert("提示", "请选择一条记录!");
	//				        } 
	//				        else
	//				        {
	//				            editBasePlanForm.getForm().loadRecord(selectRe);
	//				            document.getElementById('idStr').value = selectRe.data.id;
	//				            editInit();
	//				        }
					    }
					
					},
					'-',
					{
					    text : '新建复合指标',
					    iconCls : 'addIconCss',
					    handler : function() 
					    {
					    	indexTreeEdit.removeAll(true);
					    	indexTreeEdit.add
							(
								{
									columnWidth : .99,
									layout : 'form',
									labelWidth : 70, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : 
									[
									 	{
											fieldLabel : '指标名称',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '指标编号',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '指标类型',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '标识类'],
															['2', '数量类'],
															['3', '金额类'],
															['4', '费率类'],
															['5', '比值类']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										{
											fieldLabel : '指标公式',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '指标描述',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '维护人',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '维护日期',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										}
//										
									]
								}
							);
							
					    	indexTreeEdit.doLayout();
	//				    	var selectLength = listPanel.getSelectionModel().getSelections().length;
	//				
	//				        var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//				
	//				        if (selectLength != 1) 
	//				        {
	//				            Ext.Msg.alert("提示", "请选择一条记录!");
	//				        } 
	//				        else
	//				        {
	//				            editBasePlanForm.getForm().loadRecord(selectRe);
	//				            document.getElementById('idStr').value = selectRe.data.id;
	//				            editInit();
	//				        }
					    }
					
					},
					'-',
					{
					    text : '新建派生指标',
					    iconCls : 'addIconCss',
					    handler : function() 
					    {
					    	indexTreeEdit.removeAll(true);
					    	indexTreeEdit.add
							(
								{
									columnWidth : .99,
									layout : 'form',
									labelWidth : 70, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : 
									[
									 	{
											fieldLabel : '指标名称',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '指标编号',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '指标类型',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '标识类'],
															['2', '数量类'],
															['3', '金额类'],
															['4', '费率类'],
															['5', '比值类']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										{
											fieldLabel : '指标描述',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '关联指标类型',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '基础指标'],
															['2', '复合指标']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '关联指标名称',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '存款时点余额']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '属性维度',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '日'],
														 	['2', '月'],
														 	['3', '季'],
														 	['4', '年']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '统计属性',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '日'],
														 	['2', '月'],
														 	['3', '季'],
														 	['4', '年']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '属性信息项',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '日'],
														 	['2', '月'],
														 	['3', '季'],
														 	['4', '年']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										{
											fieldLabel : '属性值',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										new Ext.form.ComboBox
										(
											{
												hiddenName : 'CUST_TYP',
												fieldLabel : '单位',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												name:'CUST_TYP',
												store : new Ext.data.ArrayStore
												(
													{
														id : 'tarName',
														fields : ['key', 'value'],
														data : 
														[
														 	['1', '元'],
														 	['2', '个'],
														 	['3', '份额'],
														 	['4', '率']
														]
													}
												),
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
												anchor : '90%'
											}
										),
										{
											fieldLabel : '维护人',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										},
										{
											fieldLabel : '维护日期',
											name : 'CUST_ID',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										}
//										
									]
								}
							);
							
					    	indexTreeEdit.doLayout();
	//				    	var selectLength = listPanel.getSelectionModel().getSelections().length;
	//				
	//				        var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//				
	//				        if (selectLength != 1) 
	//				        {
	//				            Ext.Msg.alert("提示", "请选择一条记录!");
	//				        } 
	//				        else
	//				        {
	//				            editBasePlanForm.getForm().loadRecord(selectRe);
	//				            document.getElementById('idStr').value = selectRe.data.id;
	//				            editInit();
	//				        }
					    }
					
					},
					'-',
					{
					    text : '修改',
					    iconCls : 'editIconCss',
					    handler : function() 
					    {
	//				        // 得到选中记录
	//					    var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//					
	//					    var selectLength = listPanel.getSelectionModel().getSelections().length;
	//					    if (selectLength != 1) 
	//					    {
	//					        // alert('请选择一条记录');
	//					        Ext.Msg.alert("提示", "请选择一条记录!");
	//					    } 
	//					    else
	//					    {
	//					        showChanceForm.getForm().loadRecord(selectRe);
	//					        showInit();
	//					    }
						}
					},
					'-',
					{
					    text : '删除',
					    iconCls : 'deleteIconCss',
					    handler : function() 
					    {
	//				        // 得到选中记录
	//					    var selectRe = listPanel.getSelectionModel().getSelections()[0];
	//					
	//					    var selectLength = listPanel.getSelectionModel().getSelections().length;
	//					    if (selectLength != 1) 
	//					    {
	//					        // alert('请选择一条记录');
	//					        Ext.Msg.alert("提示", "请选择一条记录!");
	//					    } 
	//					    else
	//					    {
	//					        showChanceForm.getForm().loadRecord(selectRe);
	//					        showInit();
	//					    }
						}
					}
				],
				store: indexTreeListstore,
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
		
		indexTreeList.on
		(
			"rowdblclick",
			function(listPanel, rowIndex, event) 
			{
				//debugger;
				var selectLength = listPanel.getSelectionModel().getSelections().length;

				var selectRe = listPanel.getSelectionModel().getSelections()[0];
				
				var CLASS = selectRe.data.CLASS;
				
				if(CLASS == '1')
				{
					indexTreeEdit.removeAll(true);
			    	indexTreeEdit.add
					(
						{
							columnWidth : .99,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : 
							[
								{
									fieldLabel : 'INDEX_ID',
									name : 'INDEX_ID',
									id : 'INDEX_ID',
									//xtype : 'textfield', // 设置为数字输入框类型
									xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
							 	{
									fieldLabel : '指标名称',
									name : 'INDEX_NAME',
									id: 'INDEX_NAME',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标编号',
									name : 'INDEX_CODE',
									id : 'INDEX_CODE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '指标类型',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'INDEX_TYPE_CLASS',
										id:'INDEX_TYPE_CLASS',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '标识类'],
													['2', '数量类'],
													['3', '金额类'],
													['4', '费率类'],
													['5', '比值类']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								{
									fieldLabel : '指标描述',
									name : 'INDEX_CONTEN',
									id : 'INDEX_CONTEN',
									xtype : 'textfield', // 设置为数字输入框类型
									//xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '指标对应数据集',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'DS',
										id:'DS',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '客户数据集'],
													['2', '产品数据集']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '指标对应字段',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'FIELD',
										id:'FIELD',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', 'AUM'],
													['2', '贡献度']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								{
									fieldLabel : '维护人',
									name : 'CREATE_AUTER',
									id : 'CREATE_AUTER',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '维护日期',
									name : 'CREATE_DATE',
									id : 'CREATE_DATE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								}
//								
							]
						}
					);
					
			    	indexTreeEdit.doLayout();
			    	Ext.Ajax.request
					(
						{
							url : basepath + '/IndexBaseQueryInfoAction.json',
							method:'GET',
							params : 
							{
								ID:selectRe.data.ID
							},
							success:function(response)
							{
								//alert(response.responseText);
								nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
								//alert(nodeArra[0].INDEX_ID);
								Ext.getCmp('INDEX_NAME').setValue(nodeArra[0].INDEX_NAME);
								Ext.getCmp('INDEX_CODE').setValue(nodeArra[0].INDEX_CODE);
								Ext.getCmp('INDEX_CONTEN').setValue(nodeArra[0].INDEX_CONTEN);
								Ext.getCmp('CREATE_AUTER').setValue(nodeArra[0].CREATE_AUTER);
								Ext.getCmp('CREATE_DATE').setValue(nodeArra[0].CREATE_DATE);
								Ext.getCmp('INDEX_ID').setValue(nodeArra[0].INDEX_ID);
								
								
								Ext.getCmp('FIELD').setValue(nodeArra[0].FIELD);
								Ext.getCmp('INDEX_TYPE_CLASS').setValue(nodeArra[0].INDEX_TYPE_CLASS);
								Ext.getCmp('DS').setValue(nodeArra[0].DS);
							},
							failure:function(a,b,c){}
						}
					);
				}
				else if(CLASS == '2')
				{
					indexTreeEdit.removeAll(true);
			    	indexTreeEdit.add
					(
						{
							columnWidth : .99,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : 
							[
								{
									fieldLabel : 'INDEX_ID',
									name : 'INDEX_ID',
									id : 'INDEX_ID',
									//xtype : 'textfield', // 设置为数字输入框类型
									xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
							 	{
									fieldLabel : '指标名称',
									name : 'INDEX_NAME',
									id : 'INDEX_NAME',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标编号',
									name : 'INDEX_CODE',
									id : 'INDEX_CODE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '指标类型',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'INDEX_CLASS',
										id:'INDEX_CLASS',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '标识类'],
													['2', '数量类'],
													['3', '金额类'],
													['4', '费率类'],
													['5', '比值类']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								{
									fieldLabel : '指标公式',
									name : 'FORMULA_CONTENT_SHOW',
									id : 'FORMULA_CONTENT_SHOW',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标描述',
									name : 'INDEX_CONTENT',
									id : 'INDEX_CONTENT',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '维护人',
									name : 'CREATE_AUTER',
									id : 'CREATE_AUTER',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '维护日期',
									name : 'CREATE_DATE',
									id : 'CREATE_DATE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标公式',
									name : 'FORMULA_SHOW',
									id : 'FORMULA_SHOW',
									//xtype : 'textfield', // 设置为数字输入框类型
									xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标公式',
									name : 'FORMULA_CONTENT',
									id : 'FORMULA_CONTENT',
									//xtype : 'textfield', // 设置为数字输入框类型
									xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标公式',
									name : 'FORMULA',
									id : 'FORMULA',
									//xtype : 'textfield', // 设置为数字输入框类型
									xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								}
//								
							]
						}
					);
			    	
					
			    	Ext.getCmp('FORMULA_CONTENT_SHOW').on
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
//												            					{
//												            						text : '确定',
//												            						handler:function()
//												            						{
////												            							Ext.Ajax.request
////												            							(
////												            								{
////												            									url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
////												            									method:'GET',
////												            									success:function(response)
////												            									{
////												            										nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
////												            										loader.nodeArray = nodeArra;
////												            										var children = loader.loadAll();
////												            										Ext.getCmp('indexTreePanel').appendChild(children);
////												            										filter=new Ext.tree.TreeFilter
////												            										(
////												            											this.orgTreeForShow,
////												            											{
////												            												clearBlank:true,
////												            												autoclear:true,
////												            												ignoreFolder:true
////												            											}
////												            										);
////												            									},
////												            									failure:function(a,b,c){}
////												            								}
////												            							);
//												            							
////												            							Ext.getCmp('FORMULA').setValue(Ext.getCmp('TEMP').getValue());
////												            			    			Ext.getCmp('FORMULA_CONTENT').setValue(Ext.getCmp('TEMPCONTENT').getValue());
////												            			    			
////												            			    			Ext.getCmp('FORMULA_SHOW').setValue(Ext.getCmp('FORMULAWINDOWT').getValue());
////												            			    			Ext.getCmp('FORMULA_CONTENT_SHOW').setValue(Ext.getCmp('FORMULAWINDOWW').getValue());
////												            							
////												            							FormulaWindow.hide();
//												            						}
//												            					},
//												            					'-',
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
			    			
			    			Ext.getCmp('WINDOW_INDEX_ID').setValue(Ext.getCmp('INDEX_ID').getValue());
			    			
			    			Ext.getCmp('TEMP').setValue(Ext.getCmp('FORMULA').getValue());
			    			Ext.getCmp('TEMPCONTENT').setValue(Ext.getCmp('FORMULA_CONTENT').getValue());
			    			
			    			Ext.getCmp('FORMULAWINDOWT').setValue(Ext.getCmp('FORMULA_SHOW').getValue());
			    			Ext.getCmp('FORMULAWINDOWW').setValue(Ext.getCmp('FORMULA_CONTENT_SHOW').getValue());
			    			FormulaWindow.show();
	    				}
			    	);
			    	
			    	indexTreeEdit.doLayout();
			    	Ext.Ajax.request
					(
						{
							url : basepath + '/IndexComplexQueryInfoAction.json',
							method:'GET',
							params : 
							{
								ID:selectRe.data.ID
							},
							success:function(response)
							{
								//alert(response.responseText);
								nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
								//alert(nodeArra[0].INDEX_ID);
								Ext.getCmp('INDEX_ID').setValue(nodeArra[0].INDEX_ID);
								Ext.getCmp('INDEX_NAME').setValue(nodeArra[0].INDEX_NAME);
								Ext.getCmp('INDEX_CODE').setValue(nodeArra[0].INDEX_CODE);
								Ext.getCmp('INDEX_CONTENT').setValue(nodeArra[0].INDEX_CONTENT);
								Ext.getCmp('FORMULA').setValue(nodeArra[0].FORMULA);
								Ext.getCmp('FORMULA_CONTENT').setValue(nodeArra[0].FORMULA_CONTENT);
								
								Ext.getCmp('CREATE_AUTER').setValue(nodeArra[0].CREATE_AUTER);
								Ext.getCmp('CREATE_DATE').setValue(nodeArra[0].CREATE_DATE);
								
								var newTEMPValues = nodeArra[0].FORMULA.split('|');
                            	var newTEMPCONTENTValues = nodeArra[0].FORMULA_CONTENT.split('|');
                            	
                            	var newTEMPValue = '';
                            	var newTEMPCONTENTValue = '';
                            	
                            	for(var i = 0;i<newTEMPValues.length;i++)
                            	{
                            		newTEMPValue = newTEMPValue + newTEMPValues[i];
                            		newTEMPCONTENTValue = newTEMPCONTENTValue + newTEMPCONTENTValues[i];
                            	}
                            	
                            	Ext.getCmp('FORMULA_SHOW').setValue(newTEMPValue);
                            	Ext.getCmp('FORMULA_CONTENT_SHOW').setValue(newTEMPCONTENTValue);
								
								Ext.getCmp('INDEX_CLASS').setValue(nodeArra[0].INDEX_CLASS);
								
								
							},
							failure:function(a,b,c){}
						}
					);
				}
				else if(CLASS == '3')
				{
					indexTreeEdit.removeAll(true);
			    	indexTreeEdit.add
					(
						{
							columnWidth : .99,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : 
							[
								{
									fieldLabel : 'INDEX_ID',
									name : 'INDEX_ID',
									id : 'INDEX_ID',
									//xtype : 'textfield', // 设置为数字输入框类型
									xtype : 'hidden',
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
							 	{
									fieldLabel : '指标名称',
									name : 'INDEX_NAME',
									id : 'INDEX_NAME',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '指标编号',
									name : 'INDEX_CODE',
									id : 'INDEX_CODE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '指标类型',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'INDEX_CLASS',
										id:'INDEX_CLASS',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '标识类'],
													['2', '数量类'],
													['3', '金额类'],
													['4', '费率类'],
													['5', '比值类']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								{
									fieldLabel : '指标描述',
									name : 'INDEX_CONTENT',
									id : 'INDEX_CONTENT',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '关联指标类型',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'RELATED_INDEX_STYLE',
										id:'RELATED_INDEX_STYLE',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '基础指标'],
													['2', '复合指标']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '关联指标名称',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'RELATED_INDEX_ID',
										id:'RELATED_INDEX_ID',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '存款时点余额']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '属性维度',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'DIMENSION',
										id:'DIMENSION',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '全行'],
												 	['2', '辖内'],
												 	['3', '客户'],
												 	['4', '员工']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '统计属性',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'COUNT',
										id:'COUNT',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '完成值'],
												 	['2', '计划值'],
												 	['3', '累计值'],
												 	['4', '均值'],
												 	['5', '同比'],
												 	['6', '环比'],
												 	['7', '占比']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '属性信息项',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'ATTR_INFO_ITEM',
										id:'ATTR_INFO_ITEM',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '日'],
												 	['2', '月'],
												 	['3', '季'],
												 	['4', '年']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								{
									fieldLabel : '属性值',
									name : 'ATTR_VALUE',
									id : 'ATTR_VALUE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								new Ext.form.ComboBox
								(
									{
										hiddenName : 'CUST_TYP',
										fieldLabel : '单位',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										name:'UNIT',
										id:'UNIT',
										store : new Ext.data.ArrayStore
										(
											{
												id : 'tarName',
												fields : ['key', 'value'],
												data : 
												[
												 	['1', '元'],
												 	['2', '个'],
												 	['3', '份额'],
												 	['4', '率']
												]
											}
										),
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									}
								),
								{
									fieldLabel : '维护人',
									name : 'CREATE_AUTER',
									id : 'CREATE_AUTER',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},
								{
									fieldLabel : '维护日期',
									name : 'CREATE_DATE',
									id : 'CREATE_DATE',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								}
//								
							]
						}
					);
					
			    	indexTreeEdit.doLayout();
			    	
			    	Ext.Ajax.request
					(
						{
							url : basepath + '/IndexDeriveQueryInfoAction.json',
							method:'GET',
							params : 
							{
								ID:selectRe.data.ID
							},
							success:function(response)
							{
								nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
								//alert(nodeArra[0].INDEX_ID);
								Ext.getCmp('INDEX_ID').setValue(nodeArra[0].INDEX_ID);
								Ext.getCmp('INDEX_NAME').setValue(nodeArra[0].INDEX_NAME);
								Ext.getCmp('INDEX_CODE').setValue(nodeArra[0].INDEX_CODE);
								Ext.getCmp('INDEX_CONTENT').setValue(nodeArra[0].INDEX_CONTENT);
								Ext.getCmp('ATTR_VALUE').setValue(nodeArra[0].ATTR_VALUE);
								Ext.getCmp('CREATE_AUTER').setValue(nodeArra[0].CREATE_AUTER);
								Ext.getCmp('CREATE_DATE').setValue(nodeArra[0].CREATE_DATE);
								
								Ext.getCmp('INDEX_CLASS').setValue(nodeArra[0].INDEX_CLASS);
								Ext.getCmp('RELATED_INDEX_STYLE').setValue(nodeArra[0].RELATED_INDEX_STYLE);
								Ext.getCmp('RELATED_INDEX_ID').setValue(nodeArra[0].RELATED_INDEX_ID);
								Ext.getCmp('DIMENSION').setValue(nodeArra[0].DIMENSION);
								Ext.getCmp('COUNT').setValue(nodeArra[0].COUNT);
								Ext.getCmp('ATTR_INFO_ITEM').setValue(nodeArra[0].ATTR_INFO_ITEM);
								Ext.getCmp('UNIT').setValue(nodeArra[0].UNIT);
							},
							failure:function(a,b,c){}
						}
					);
				}

//				if (selectLength != 1) {
//					Ext.Msg.alert("提示", "请选择一条记录!");
//				} else {
//						var activityId = selectRe.data.marketActivityId;
//						document.getElementById('marketActivityIdStr').value = activityId;
//						activityDetailForm.getForm().loadRecord(selectRe);
//						detailInit();
//					}
			}
		);
		
		//指标属性
		var indexTreeEditRecord = Ext.data.Record.create
		(
				[
				 	{
				 		name : 'VALUE',
				 		mapping : 'VALUE'
				 	},
				 	{
				 		name : 'CODE',
				 		mapping : 'CODE'
				 	},
				 	{
				 		name : 'NAME',
				 		mapping : 'NAME'
				 	}
				]
			);
		
//		var indexTreeEditStore = new Ext.data.Store
//		(
//			{
//				restful : true,
//				proxy : new Ext.data.HttpProxy
//				(
//					{
//						url : basepath + '/IndexInfoQueryAction.json',
//						method : 'POST'//,
//						/*
//                        success : function(response) 
//                        {
//                        	Ext.Msg.alert('提示', response.responseText); 
//                        },
//            			failure : function(response) 
//            			{
//                     		Ext.Msg.alert('提示','加入失败' );
//                    	}*/
//					}
//				),
//				reader : new Ext.data.JsonReader
//				(
//					{
//						successProperty : 'success',
//						// idProperty: 'ID',
//						// messageProperty: 'message',
//						root : 'json.data',
//						totalProperty : 'json.count'
//					}, 
//					indexTreeEditRecord
//				)
//			}
//		);
		
		
		
		var indexTreeEditStore = new Ext.data.ArrayStore
		(
			{
				fields: ['NAME', 'VALUE','CODE'],
				idIndex: 0 // 每条记录的id将会是第一个元素
			}
		);
		
		


		var indexTreeEdit = new Ext.form.FormPanel
		(
			{
				height : document.body.clientHeight,
				width : 280,
				id:'indexTreeEdit',
				region:'east',
				title:"指标属性编辑",
				tbar :
				[
					{
					    text : '保存',
					    iconCls : 'addIconCss',
					    handler : function() 
					    {
					    	
					    }
					
					}
				],
				layout : 'column'
			}
		);
		
		
		
//		var indexTreeEdit = new Ext.grid.EditorGridPanel
//		(
//			{
//				
//				height : document.body.clientHeight,
//				width : 210,
//				store: indexTreeEditStore,//indexTreeEditStore,
//				colModel: new Ext.grid.ColumnModel
//				(
//					{
//						defaults: 
//						{
//							width: 120,
//							sortable: true
//						},
//						columns: 
//						[
//						 	{
//						 		id: 'NAME', 
//						 		header: '名称', 
//						 		dataIndex: 'NAME'
//						 	},
////						 	{
////						 		header: '值',  
////						 		dataIndex: 'VALUE'
////						 	},
////						 	{
////							    header : '右括号',
////							    width : 100,
////							    align : 'center',
////							    dataIndex : 'right',
////							    editor : new Ext.form.ComboBox({
////										typeAhead : true,
////										triggerAction : 'all',
////										lazyRender : true,
////										listClass : 'x-combo-list-small',
////										mode : 'local',
////										valueField : 'myId1',
////										displayField : 'displayText1',
////										store : new Ext.data.ArrayStore({
////													id : 'tarName',
////													fields : ['myId1', 'displayText1'],
////													data : [[')', ')'],
////													       ['))', '))'],
////													       [')))', ')))']
////															]
////												})
////									}),
////							    sortable : true
////							},
//						 	{
//							    header : '值',
//							    width : 100,
//							    align : 'center',
//							    dataIndex : 'VALUE',
//							    sortable : true,
//							    editor : new Ext.form.Field()
//							},
//						 	{
//						 		header: 'CODE', 
//						 		hidden : true,
//						 		dataIndex: 'CODE'
//						 	}
//						],
//					}
//				),
//				viewConfig: 
//				{
//					forceFit: true,
//
////		      		Return CSS class to apply to rows depending upon data values
//					getRowClass: function(record, index) 
//					{
//						var c = record.get('change');
//						if (c < 0) 
//						{
//							return 'price-fall';
//						} 
//						else if (c > 0) 
//						{
//							return 'price-rise';
//						}
//					}
//				},
//				sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
//				id:'indexTreeEdit',
//				region:'east',
//				title:"指标属性编辑"
//			}
//		);
		
		//布局模型 页面展示

		
		
		var view = new Ext.Viewport
		(
			{
				
				layout : 'border',
				frame : true ,
				items : 
				[
				 	indexTreeType,
				 	indexTreeList,
				 	indexTreeEdit
				]
		});
		
		Ext.Ajax.request
		(
			{
				url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
				method:'GET',
				success:function(response)
				{
					nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
					loader.nodeArray = nodeArra;
					var children = loader.loadAll();
					Ext.getCmp('indexTreePanel').appendChild(children);
					filter=new Ext.tree.TreeFilter
					(
						this.orgTreeForShow,
						{
							clearBlank:true,
							autoclear:true,
							ignoreFolder:true
						}
					);
				},
				failure:function(a,b,c){}
			}
		);
	}
);