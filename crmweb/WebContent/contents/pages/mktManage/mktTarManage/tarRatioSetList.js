Ext.onReady(function() {
	
	var leftTreeForShowForSet = new Ext.tree.TreePanel({//北京银行机构数

		//width:200,
		autoScroll:true,
		animate : false,
		useArrows : false,
		listeners:{
			click:function(node)
			{
				//comboxWithTree.setValue(node.text);
				var orgTree=Ext.getCmp('orgName').setValue(node.text);
				orgTree.expand();
			}
		},
		root: new Ext.tree.AsyncTreeNode({//树的定义
			id:'root',
			expanded:true,
			text:'北京银行总行',
			autoScroll:true,
			children:[
				{
					text:'北京分行',
					id:'companyProduct',
					checked:false,
					children://子节点
					[
						{
							text:'北京管理部',
							id:'currentDeposit',
							checked:false,
							children:[
							{
								text:'建国支行',
								checked:false,
								leaf:true //标记 是叶子,
							},
							{
								text:'和平里支行',
								checked:false,
								leaf:true
										
							},
							{
								text:'官园支行',
								checked:false,
								leaf:true
							}
							]

						},
						{
							text:'CBD管理部',
							checked:false,
							id:'timeDeposit',
							children:[
							{
								text:'朝外支行',
								checked:false,
								leaf:true
							
							},
							{
								text:'北辰路支行',
								checked:false,
								leaf:true
							}
							]
						},
						{
							text:'中科园管理部',
							checked:false,
							id:'callDeposit',
							children:[
							{
								text:'双秀支行',
								checked:false,
								leaf:true
							
							},
							{
								text:'金运支行',
								checked:false,
								leaf:true
							},
							{
								text:'中科园支行',
								checked:false,
								leaf:true
							}
							]
							
						}
						
					]
					
				},
				{
					text:'天津分行',
					id:'loanProduct',
					checked:false,
					children:
					[
						{
							text:'天津分行',
							checked:false,
							children:[
							{
								text:'天津开发区支行',
								checked:false,
								leaf:true
							},
							{
								text:'天津南开支行',
								checked:false,
								leaf:true
							},
							{
								text:'天津空港支行',
								checked:false,
								leaf:true
							},
							{
								text:'天津梅江支行',
								checked:false,
								leaf:true
							}							
							]
							
						}

					]
				},
				{
					text:'上海分行',
					id:'RMBLetterOfGuarantee',
					checked:false,
					children:
					[
						{
							text:'上海分行',
							checked:false,
							children:[
							{
								text:'上海分行营业部',
								checked:false,
								leaf:true
							},
							{
								text:'上海宝山支行',
								checked:false,
								leaf:true
							},
							{
								text:'上海浦东支行',
								checked:false,
								leaf:true
							},
							{
								text:'上海闵行支行',
								checked:false,
								leaf:true
							}
							]
						}				


					]
				}
			
			]
		 
		}),
		region:'west',
//		collapsible:true,
		loader:new Ext.tree.TreeLoader(),
		split:true
	});
	
        var searchPanel = new Ext.form.FormPanel({
                                labelWidth : 100,
                                height : 100,
                                frame : true,
                                region : 'north',
                                autoScroll : true,
                                items : [{
                                        layout : 'column',
                                        items : [
                                        	{
                                                columnWidth : .25,
                                                layout : 'form',
                                                items : [{

                                                        xtype : 'combo', resizable : true,
                                                        id : 'yearNum',
                                                        name : 'yearNum',
                                                        fieldLabel : '年度',
                                                        labelStyle : {
                                                                width : '120px'
                                                        },
                                                        width : '100',
                                                        mode : 'local',
                                                        store : new Ext.data.ArrayStore({
                                                                                fields : ['myId', 'displayText'],
                                                                                data : [[1, '2007'], [2, '2008'],
                                                                                        [3, '2009'],
                                                                                                [4, '2010'],
                                                                                                [5, '2011']]
                                                                        }),
                                                        valueField : 'myId',
                                                        displayField : 'displayText',
                                                        anchor : '90%'
                                                }]
                                         },
                                         {
                                                columnWidth : .35,
                                                layout : 'form',
                                                items : [
                                                	{
														xtype:'combo',
														triggerAction:'all',
														anchor:'90%',
														id:'orgName',
														name:'orgName',
														fieldLabel:'机构',
														mode:'local',
														store: new Ext.data.ArrayStore({
												        id: 0,
												        fields: [  ],
												        data: [[]]
												 	    }),
												       tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDiv'></div></div></tpl>",
												       allowBlank:false,
												       onSelect:Ext.emptyFn,
												       listeners:{
												        expand:function()
												        {
												        	leftTreeForShowForSet.render('addOrgTreeDiv');
												        }
												       }
                                                	}
                                                ]
                                         }                                         
//                                        
//                                        {
//                                        	  columnWidth : .25,
//                                              layout : 'form',
//                                              items : [{
//                                                xtype : 'textfield',
//                                                labelStyle : {
//                                                        width : '120px'
//                                                },
//                                                Width : '100',
//                                                id : 'orgName',
//                                                name : 'orgName',
//                                                fieldLabel : '机构',
//                                                anchor : '90%'
//                                              }]
//                                        },
//                                        {
//                                      	  columnWidth : .25,
//                                          layout : 'form',
//                                          items : [{
//                                                xtype : 'combo', resizable : true,
//                                                id : 'tarType',
//                                                name : 'tarType',
//                                                fieldLabel : '指标类型',
//                                                labelStyle : {
//                                                        width : '120px'
//                                                },
//                                                width : '100',
//                                                mode : 'local',
//                                                store : new Ext.data.ArrayStore({
//                                                                        fields : ['myId', 'displayText'],
//                                                                        data : [[1, '存款类'], [2, '贷款类'],
//                                                                                [3, '中间业务类'],
//                                                                                        [4, '客户数']]
//                                                                }),
//                                                valueField : 'myId',
//                                                displayField : 'displayText',
//                                                anchor : '90%'
//                                        }]}
                                  ]
                                }],
                                buttonAlign:'center',
                                buttons:[
                                {
                                	text:'查   询',
                                	handler:function()
                                	{
                                	}
                                }
                                ]
                                

                        });

        var columns = new Ext.grid.ColumnModel({
                                columns : [{
                                                        header : '年度',
                                                        width : 170,
                                                        align : 'center',
                                                        dataIndex : 'yearNum',
                                                        sortable : true
                                                }, {
                                                        header : '机构名称',
                                                        width : 175,
                                                        align : 'center',
                                                        dataIndex : 'orgName',
                                                        sortable : true
                                                }, {
                                                        header : '指标类型',
                                                        width : 200,
                                                        align : 'center',
                                                        dataIndex : 'tarType',
                                                        sortable : true
                                                },
                                                {
                                                        header : '指标',
                                                        width : 150,
                                                        align : 'center',
                                                        dataIndex : 'tarName',
                                                        sortable : true
                                                }
//                                                {
//                                                        header : '指标值',
//                                                        width : 150,
//                                                        align : 'center',
//                                                        dataIndex : 'tarValue',
//                                                        sortable : true
//                                                },
//                                                {
//                                                        header : '比年初增量',
//                                                        width : 150,
//                                                        align : 'center',
//                                                        dataIndex : 'tarIncNum',
//                                                        sortable : true
//                                                }
                                                ]
                        });

        var record = Ext.data.Record.create([{
                                name : 'yearNum'
                        }, {
                                name : 'orgName'
                        }, {
                                name : 'tarType'
                        }, {
                                name : 'tarName'
                        }, {
                        }, {
                                name : 'tarValue'
                        }, {
                                name : 'tarIncNum'
                        }]);

        var data = {
                num : 6,
                rows : [  		{
                                        "yearNum" : "2010",
                                        "orgName" : "北京分行",
                                        "tarType" : "存款类",
                                        "tarName" : "存款时点余额",
                                        "tarValue" : "10,000,000,000.00",
                                        "tarIncNum" : "1,000,000,000.00"
                                },
                                {
                                    "yearNum" : "2011",
                                    "orgName" : "广州分行",
                                    "tarType" : "存款类",
                                    "tarName" : "存款日均余额",
                                    "tarValue" : "10,000,000,000.00",
                                    "tarIncNum" : "1,000,000,000.00"
                                }, 
                                {
                                    "yearNum" : "2011",
                                    "orgName" : "北京分行",
                                    "tarType" : "存款类",
                                    "tarName" : "存款时点增量",
                                    "tarValue" : "11,000,000,000.00",
                                    "tarIncNum" : "1,000,000,000.00"
                                },                                
                                {
                                    "yearNum" : "2011",
                                    "orgName" : "北京分行",
                                    "tarType" : "贷款类",
                                    "tarName" : "贷款时点余额",
                                    "tarValue" : "11,000,000,000.00",
                                    "tarIncNum" : "1,000,000,000.00"
                                },
                                {
                                    "yearNum" : "2011",
                                    "orgName" : "北京分行",
                                    "tarType" : "贷款类",
                                    "tarName" : "贷款日均余额",
                                    "tarValue" : "11,000,000,000.00",
                                    "tarIncNum" : "1,000,000,000.00"
                                }
                                 
                                ]

        };
        var reader = new Ext.data.JsonReader({
                                totalProperty : 'num',
//                                idProperty : 'yearNum',
                                root : 'rows'
                        }, record);
        var store = new Ext.data.Store({
                                reader : reader
                        });

        store.loadData(data);

        var listPanel = new Ext.grid.GridPanel({
                                store : store,
                                frame : true,
                                cm : columns,
                                stripeRows : true,
                                tbar : [{
                                                        text : '指标分配',
                                                        iconCls : 'page_etIcon',
                                                        handler : function() {
                                                                editTarInit();
                                                        }
                                                }, '-', {
                                                        text : '指标设置',
                                                        iconCls : 'page_edIcon',
                                                        handler : function() {
                                                                editDictInit();
                                                        }

                                                }
                                              ],
                                store : store,
                                frame : true,
                                bbar : {
                                        xtype : 'paging',
                                        pageSize : 10,
                                        store : store,
                                        displayInfo : true,
                                        displayMsg : '显示1条到10条,共3条',
                                        emptyMsg : "没有符合条件的记录",
                                        items : ['-', '&nbsp;&nbsp;', {
                                                                xtype : 'textfield',
                                                                value : '10'
                                                        }]
                                }
                        });
                        
// 分配指标展示的from
    	var tarRedForm = new Ext.form.FormPanel({
    		labelWidth : 150,
    		height : 300,
    		frame : true,
    		autoScroll : true,
    		buttonAlign : "center",
    		//layout:'border',
    		items : [
    				{
    					height:50,
    					layout : 'column',
    					items : [
    							{
    								columnWidth : .5,
    								layout : 'form',
    								items : [ {

                                        xtype : 'combo', resizable : true,
                                        id : 'yearNumAdd',
                                        name : 'yearNumAdd',
                                        fieldLabel : '年度',
                                        labelStyle : {
                                                width : '120px'
                                        },
                                        width : '100',
                                        mode : 'local',
                                        store : new Ext.data.ArrayStore({
                                                                fields : ['myId', 'displayText'],
                                                                data : [[1, '2007'], [2, '2008'],
                                                                        [3, '2009'],
                                                                                [4, '2010'],
                                                                                [5, '2011']]
                                                        }),
                                        valueField : 'myId',
                                        displayField : 'displayText',
                                        anchor : '90%'
                                } ]
    							},
    							{
    								columnWidth : .5,
    								layout : 'form',
    								items : [{
    									id : 'orgNameRed',
    									name : 'orgNameRed',
    									xtype : 'combo', resizable : true,
    									labelStyle : {
    										width : '120px'
    									},
    									width : '100',
    									fieldLabel : '机构名称',
    									mode : 'local',
    									store : new Ext.data.ArrayStore({
    										id : 0,
    										fields : [ 'myId',
    												'displayText' ],
    										data : [ [ 1, '北京分行' ],
    												[ 2, '广州分行' ]]
    									}),
    									valueField : 'myId',
    									displayField : 'displayText',
    									triggerAction : 'all',
    									anchor : '90%'
    								}]
    							}

    					]
    				},tarEditListPanel
    					],
    					
    					buttons : [
    					{

    						text : '保  存',
    						handler : function() {
    							alert("指标分配成功！");
    							tarRedWindow.hide();
    						}

    					}, {

    						text : '下 达',
    						handler : function() {
    							confirm("指标下达后不可更改,确定下达吗?");
    							alert("下达成功!");
    							tarRedWindow.hide();
    						}

    					},
    						{
    						text : '取  消',
    						handler : function() {
    							tarRedWindow.hide();
    						}
    					} ]

    	});

    	
        // 设置指标展示的from
    	var editDictForm = new Ext.form.FormPanel({
    		labelWidth : 150,
    		height : 300,
    		frame : true,
    		autoScroll : true,
    		buttonAlign : "center",
    		items : [
    				tarDictListPanel
    					],
    					
    					buttons : [
    					{

    						text : '保  存',
    						handler : function() {
    							alert("指标设置成功！");
    							editDictWindow.hide();
    						}

    					},
    						{
    						text : '取  消',
    						handler : function() {
    							editDictWindow.hide();
    						}
    					} ]

    	});
  
//    	// 定义任务分配窗口
    	var tarRedWindow = new Ext.Window({
    		title : '指标分配',
    		plain : true,
    		layout : 'fit',
    		width : 800,
    		height : 450,
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
    		items : [ tarRedForm ]
    	});

    	// 定义指标设置窗口
    	var editDictWindow = new Ext.Window({
    		title : '指标设置',
    		plain : true,
    		layout : 'fit',
    		width : 800,
    		height : 450,
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
    		items : [ editDictForm ]
    	});
  
        // 展示指标分配窗口
        function editTarInit() {
        	tarRedWindow.show();

        }
     
        // 展示指标设置窗口
        function editDictInit() {
        	editDictWindow.show();

        }
        
        var view = new Ext.Viewport({

                                layout : 'border',
                                items : [

                                                {
                                        region : 'center',
                                        id : 'center-panel',
                                        title : "指标列表",
                                        layout : 'fit',
                                        items : [tarDictListPanel]
                                },

                                {
                                        region : 'north',
                                        id : 'north-panel',
                                        title : "指标查询",
                                        height : 100,
                                        layout : 'fit',
                                        items : [searchPanel]
                                }

                                ]
                        });

})


