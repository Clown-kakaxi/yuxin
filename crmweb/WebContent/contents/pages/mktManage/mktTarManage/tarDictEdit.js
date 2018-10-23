	var leftTreeForShow = new Ext.tree.TreePanel({//北京银行机构数

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

					children://子节点
					[
						{
							text:'北京管理部',
							id:'currentDeposit',
							
							children:[
							{
								text:'建国支行',
								leaf:true //标记 是叶子,
							},
							{
								text:'和平里支行',
								leaf:true
										
							},
							{
								text:'官园支行',
								leaf:true
							}
							]

						},
						{
							text:'CBD管理部',
							id:'timeDeposit',
							children:[
							{
								text:'朝外支行',
								leaf:true
							
							},
							{
								text:'北辰路支行',
								leaf:true
							}
							]
						},
						{
							text:'中科园管理部',
							id:'callDeposit',
							children:[
							{
								text:'双秀支行',
								leaf:true
							
							},
							{
								text:'金运支行',
								leaf:true
							},
							{
								text:'中科园支行',
								leaf:true
							}
							]
							
						}
						
					]
					
				},
				{
					text:'天津分行',
					id:'loanProduct',

					children:
					[
						{
							text:'天津分行',

							children:[
							{
								text:'天津开发区支行',
								leaf:true
							},
							{
								text:'天津南开支行',
								leaf:true
							},
							{
								text:'天津空港支行',
								leaf:true
							},
							{
								text:'天津梅江支行',
								leaf:true
							}							
							]
							
						}

					]
				},
				{
					text:'上海分行',
					id:'RMBLetterOfGuarantee',

					children:
					[
						{
							text:'上海分行',

							children:[
							{
								text:'上海分行营业部',
								leaf:true
							},
							{
								text:'上海宝山支行',
								leaf:true
							},
							{
								text:'上海浦东支行',
								leaf:true
							},
							{
								text:'上海闵行支行',
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
	


var tarDictColumns = new Ext.grid.ColumnModel({
                                columns : [{
                                    header : '年度',
                                    width : 100,
                                    align : 'center',
                                    dataIndex : 'yearNumDict',
                                    sortable : true,
                    				editor : new Ext.form.ComboBox({
            							typeAhead : true,
            							triggerAction : 'all',
            							lazyRender : true,
            							listClass : 'x-combo-list-small',
            							mode : 'local',
            							valueField : 'myId',
            							displayField : 'displayText',
            							store : new Ext.data.ArrayStore({
            										id : 'yearNumDict',
            										fields : ['myId', 'displayText'],
            										data : [
            										        ['2011', '2011'],
            												['2012', '2012'],
            												['2013', '2013'],
            												['2014', '2014'],
            												['2015', '2015']
            												]
            									})
            						})
                            },{
                                                        header : '指标',
                                                        width : 100,
                                                        align : 'center',
                                                        dataIndex : 'tarName',
                                                        sortable : true,
                                        				editor : new Ext.form.ComboBox({
                                							typeAhead : true,
                                							triggerAction : 'all',
                                							lazyRender : true,
                                							listClass : 'x-combo-list-small',
                                							mode : 'local',
                                							valueField : 'myId1',
                                							displayField : 'displayText1',
                                							store : new Ext.data.ArrayStore({
                                										id : 'tarName',
                                										fields : ['myId1', 'displayText1'],
                                										data : [['存款时点余额', '存款时点余额'],
                                										        ['存款日均余额', '存款日均余额'],
                                										        ['贷款时点余额', '贷款时点余额'],
                                												['贷款日均余额', '贷款日均余额'],
                                												['存款时点增量', '存款时点增量'],
                                												['存款日均增量', '存款日均增量'],
                                												['大客户数', '大客户数'],
                                												['中小客户数', '中小客户数'],
                                												['网银客户数', '中间业务收入'],
                                												['中间业务收入', '中间业务收入']
                                												]
                                									})
                                						})
                                                },  {
                                                        header : '一季度',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'ji1',
                                                        sortable : true,
                                                        editor : new Ext.form.Field()
                                                }, {
                                                        header : '1月',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'm1',
                                                        sortable : true,
                                                        editor : new Ext.form.Field()
                                                }, {
                                                        header : '2月',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'm2',
                                                        sortable : true,
                                                        editor : new Ext.form.Field()
                                                }, {
                                                        header : '3月',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'm3',
                                                        sortable : true,
                                                        editor : new Ext.form.Field()
                                                }, {
                                                    header : '二季度',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'ji2',
                                                    sortable : true,
                                                    editor : new Ext.form.Field()
                                            }, {
                                                    header : '4月',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'm4',
                                                    sortable : true,
                                                    editor : new Ext.form.Field()
                                            }, {
                                                    header : '5月',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'm5',
                                                    sortable : true,
                                                    editor : new Ext.form.Field()
                                            }, {
                                                    header : '6月',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'm6',
                                                    sortable : true,
                                                    editor : new Ext.form.Field()
                                            }, {
                                                header : '三季度',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'ji3',
                                                sortable : true,
                                                editor : new Ext.form.Field()
                                        }, {
                                                header : '7月',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'm7',
                                                sortable : true,
                                                editor : new Ext.form.Field()
                                        }, {
                                                header : '8月',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'm8',
                                                sortable : true,
                                                editor : new Ext.form.Field()
                                                
                                        }, {
                                                header : '9月',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'm9',
                                                sortable : true,
                                                editor : new Ext.form.Field()
                                        }, {
                                            header : '四季度',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'ji4',
                                            sortable : true,
                                            editor : new Ext.form.Field()
                                    }, {
                                            header : '10月',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'm10',
                                            sortable : true,
                                            editor : new Ext.form.Field()
                                    }, {
                                            header : '11月',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'm11',
                                            sortable : true,
                                            editor : new Ext.form.Field()
                                    }, {
                                            header : '12月',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'm12',
                                            sortable : true,
                                            editor : new Ext.form.Field()
                                    }]
                        });

        var tarDictRecord = Ext.data.Record.create([{
                                name : 'yearNumDict'
                        }, {
                                name : 'tarName'
                        }, {
                                name : 'ji1'
                        }, {
                                name : 'm1'
                        }, {
                                name : 'm2'
                        }, {
                                name : 'm3'
                        }, {
                                name : 'ji2'
                        }, {
                                name : 'm4'
                        }, {
                                name : 'm5'
                        }, {
                                name : 'm6'
                        }, {
                                name : 'ji3'
                        }, {
                                name : 'm7'
                        }, {
                                name : 'm8'
                        }, {
                                name : 'm9'
                        }, {
                                name : 'ji4'
                        }, {
                                name : 'm10'
                        }, {
                                name : 'm11'
                        }, {
                                name : 'm12'
                        }]);

        var tarDictData = {
                num : 3,
                rows : [
                				{
                	                    "yearNumDict" : '2011',
                                        "tarName" : "存款时点余额",
                                        "ji1" : "4",
                                        "m1" : "4",
                                        "m2" : "3",
                                        "m3" : "3",
                                        "ji2" : "3",
                                        "m4" : "4",
                                        "m5" : "4",
                                        "m6" : "3",
                                        "ji3" : "2",
                                        "m7" : "4",
                                        "m8" : "4",
                                        "m9" : "3",
                                        "ji4" : "1",
                                        "m10" : "4",
                                        "m11" : "3",
                                        "m12" : "3"
                                },
                				{
                	                    "yearNumDict" : '2011',
                                        "tarName" : "存款日均余额",
                                        "ji1" : "4",
                                        "m1" : "4",
                                        "m2" : "3",
                                        "m3" : "3",
                                        "ji2" : "3",
                                        "m4" : "4",
                                        "m5" : "4",
                                        "m6" : "3",
                                        "ji3" : "2",
                                        "m7" : "4",
                                        "m8" : "4",
                                        "m9" : "3",
                                        "ji4" : "1",
                                        "m10" : "4",
                                        "m11" : "3",
                                        "m12" : "3"
                                },  
	               				{
	                	                    "yearNumDict" : '2011',
	                                        "tarName" : "存款时点增量",
	                                        "ji1" : "4",
	                                        "m1" : "4",
	                                        "m2" : "3",
	                                        "m3" : "3",
	                                        "ji2" : "3",
	                                        "m4" : "4",
	                                        "m5" : "4",
	                                        "m6" : "3",
	                                        "ji3" : "2",
	                                        "m7" : "4",
	                                        "m8" : "4",
	                                        "m9" : "3",
	                                        "ji4" : "1",
	                                        "m10" : "4",
	                                        "m11" : "3",
	                                        "m12" : "3"
	                              },
	               				{
	                	                    "yearNumDict" : '2011',
	                                        "tarName" : "存款日均增量",
	                                        "ji1" : "4",
	                                        "m1" : "4",
	                                        "m2" : "3",
	                                        "m3" : "3",
	                                        "ji2" : "3",
	                                        "m4" : "4",
	                                        "m5" : "4",
	                                        "m6" : "3",
	                                        "ji3" : "2",
	                                        "m7" : "4",
	                                        "m8" : "4",
	                                        "m9" : "3",
	                                        "ji4" : "1",
	                                        "m10" : "4",
	                                        "m11" : "3",
	                                        "m12" : "3"
	                              },
                                {
                                	"yearNumDict" : "2011",
                                    "tarName" : "贷款时点余额",
                                    "ji1" : "4",
                                    "m1" : "4",
                                    "m2" : "3",
                                    "m3" : "3",
                                    "ji2" : "4",
                                    "m4" : "4",
                                    "m5" : "3",
                                    "m6" : "3",
                                    "ji3" : "1",
                                    "m7" : "4",
                                    "m8" : "3",
                                    "m9" : "3",
                                    "ji4" : "1",
                                    "m10" : "4",
                                    "m11" : "3",
                                    "m12" : "3"
                                },	                              
                                {
                                	"yearNumDict" : "2011",
                                    "tarName" : "贷款日均余额",
                                    "ji1" : "4",
                                    "m1" : "4",
                                    "m2" : "3",
                                    "m3" : "3",
                                    "ji2" : "4",
                                    "m4" : "4",
                                    "m5" : "3",
                                    "m6" : "3",
                                    "ji3" : "1",
                                    "m7" : "4",
                                    "m8" : "3",
                                    "m9" : "3",
                                    "ji4" : "1",
                                    "m10" : "4",
                                    "m11" : "3",
                                    "m12" : "3"
                                }
                                ]

        };
        
        var tarDictReader = new Ext.data.JsonReader({
                                totalProperty : 'num',
                                root : 'rows'
                        }, record);
        var tarDictStore = new Ext.data.Store({
        						autoDestroy : true,
                                reader : tarDictReader
                        });

        tarDictStore.loadData(tarDictData);

        
        var tarDictListPanel = new Ext.grid.EditorGridPanel({
			height : 350,
			store : tarDictStore,
			frame : true,
			cm : tarDictColumns,
			stripeRows : true,
			clicksToEdit : 1,
			buttonAlign : 'center',
			tbar : [{
						text : '保存',
						iconCls : 'page_addIcon',
						handler : function() {
//							tarDictAddInit();
						}
					}
//					,
//					{
//						text : '删除',
//						iconCls : 'page_delIcon',
//						handler : function() {
//							confirm('确定删除吗?');
//							alert('删除成功');
//						}
//					}
					]
		});
        
     // 展示新增行
        function tarDictAddInit() {
        	var editPlant = tarDictListPanel.getStore().recordType;
        	var pe = new editPlant({
        		yearNumDict : "",
                tarName : "",
                ji1 : "",
                m1 : "",
                m2 : "",
                m3 : "",
                ji2 : "",
                m4 : "",
                m5 : "",
                m6 : "",
                ji3 : "",
                m7 : "",
                m8 : "",
                m9 : "",
                ji4 : "",
                m10 : "",
                m11 : "",
                m12 : ""
        			});
        	tarDictListPanel.stopEditing();
        	tarDictStore.insert(0, pe);
        	tarDictListPanel.startEditing(0, 0);

        }        