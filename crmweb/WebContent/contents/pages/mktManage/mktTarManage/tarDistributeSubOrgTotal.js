var tarDictColumnsSubOrgTotal = new Ext.grid.ColumnModel({
                                columns : [{
                                    header : '年度',
                                    width : 100,
                                    align : 'center',
                                    dataIndex : 'yearNumDict',
                                    sortable : true

                            },{
                                                        header : '指标',
                                                        width : 100,
                                                        align : 'center',
                                                        dataIndex : 'tarName',
                                                        sortable : true

                                                },
                                                {
                                                        header : '上级任务指标值',
                                                        width : 100,
                                                        align : 'center',
                                                        dataIndex : 'tarValue',
                                                        sortable : true,
                                                        align:'right'
                                                       
                                                },
			                                    {
			                                    	header:'任务指标分解值',
				                                    width:100,		
				                                    dataIndex:'tarDistributeTotal'
			                                    }
                                    
                                    ]
                        });

        var tarDictRecordSubOrgTotal = Ext.data.Record.create([{
                          name : 'yearNumDict'
                        }, {
                                name : 'tarName'
                        }, 
                        {
                        	name:'tarValue'
                        },
                        {
                        	name:'tarDistributeTotal'
                        }                       
                        ]);

        var tarDictDataSubOrgTotal = {
                num : 3,
                rows : [
                				{
                	                    "yearNumDict" : '2011',
                                        "tarName" : "存款时点余额",
                                        tarValue:"145,000,000.00",
										tarDistributeTotal:"150,000,000.00",
                                        distributeStatus:'暂存'
                                },
                				{
                	                    "yearNumDict" : '2011',
                                        "tarName" : "存款日均余额",
                                        tarValue:"145,000,000.00",
										tarDistributeTotal:"150,000,000.00",
                                        distributeStatus:'暂存'
                                        
                                },  
	               				{
	                	                    "yearNumDict" : '2011',
	                                        "tarName" : "存款时点增量",
	                                        tarValue:"145,000,000.00",
											tarDistributeTotal:"150,000,000.00",
	                                        distributeStatus:'暂存'
	                              },
	               				{
	                	                    "yearNumDict" : '2011',
	                                        "tarName" : "存款日均增量",
	                                        tarValue:"145,000,000.00",
											tarDistributeTotal:"150,000,000.00",
	                                        distributeStatus:'暂存'
	                              },
                                {
                                	"yearNumDict" : "2011",
                                    "tarName" : "贷款时点余额",
                                    tarValue:"145,000,000.00",
									tarDistributeTotal:"150,000,000.00",
                                    distributeStatus:'暂存'
                                },	                              
                                {
                                	"yearNumDict" : "2011",
                                    "tarName" : "贷款日均余额",
                                    tarValue:"145,000,000.00",
                                    tarDistributeTotal:"150,000,000.00",
                                     distributeStatus:'暂存'
                                }
                                ]

        };
        
        var tarDictReaderSubOrgTotal = new Ext.data.JsonReader({
                                totalProperty : 'num',
                                root : 'rows'
                        }, tarDictRecordSubOrgTotal);
        var tarDictStoreSubOrgTotal = new Ext.data.Store({
        						autoDestroy : true,
                                reader : tarDictReaderSubOrgTotal
                        });

        tarDictStoreSubOrgTotal.loadData(tarDictDataSubOrgTotal);

        var tarDictListPanelSubOrgTotal = new Ext.grid.EditorGridPanel({
			height : 350,
			region:'center',
			store : tarDictStoreSubOrgTotal,
			frame : true,
			cm : tarDictColumnsSubOrgTotal,
			stripeRows : true,
			clicksToEdit : 1,
			buttonAlign : 'center'
		});
     