       var tarColumns = new Ext.grid.ColumnModel({
                                columns : [{
                                                        header : '指标',
                                                        width : 100,
                                                        align : 'center',
                                                        dataIndex : 'tarName',
                                                        sortable : true
                                                }, {
                                                        header : '合计',
                                                        width : 120,
                                                        align : 'center',
                                                        dataIndex : 'totalValue',
                                                        sortable : true
                                                }, {
                                                        header : '一季度',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'ji1',
                                                        sortable : true
                                                }, {
                                                        header : '1月',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'm1',
                                                        sortable : true
                                                }, {
                                                        header : '2月',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'm2',
                                                        sortable : true
                                                }, {
                                                        header : '3月',
                                                        width : 50,
                                                        align : 'center',
                                                        dataIndex : 'm3',
                                                        sortable : true
                                                }, {
                                                    header : '二季度',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'ji2',
                                                    sortable : true
                                            }, {
                                                    header : '4月',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'm4',
                                                    sortable : true
                                            }, {
                                                    header : '5月',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'm5',
                                                    sortable : true
                                            }, {
                                                    header : '6月',
                                                    width : 50,
                                                    align : 'center',
                                                    dataIndex : 'm6',
                                                    sortable : true
                                            }, {
                                                header : '三季度',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'ji3',
                                                sortable : true
                                        }, {
                                                header : '7月',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'm7',
                                                sortable : true
                                        }, {
                                                header : '8月',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'm8',
                                                sortable : true
                                        }, {
                                                header : '9月',
                                                width : 50,
                                                align : 'center',
                                                dataIndex : 'm9',
                                                sortable : true
                                        }, {
                                            header : '四季度',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'ji4',
                                            sortable : true
                                    }, {
                                            header : '10月',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'm10',
                                            sortable : true
                                    }, {
                                            header : '11月',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'm11',
                                            sortable : true
                                    }, {
                                            header : '12月',
                                            width : 50,
                                            align : 'center',
                                            dataIndex : 'm12',
                                            sortable : true
                                    }]
                        });

        var record = Ext.data.Record.create([{
                                name : 'tarName'
                        }, {
                                name : 'totalValue'
                        }, {
                                name : 'ji1'
                        }, {
                                name : 'm1'
                        }, {
                        }, {
                                name : 'm2'
                        }, {
                                name : 'm3'
                        }, {
                                name : 'ji2'
                        }, {
                                name : 'm4'
                        }, {
                        }, {
                                name : 'm5'
                        }, {
                                name : 'm6'
                        }, {
                                name : 'ji3'
                        }, {
                                name : 'm7'
                        }, {
                        }, {
                                name : 'm8'
                        }, {
                                name : 'm9'
                        }, {
                                name : 'ji4'
                        }, {
                                name : 'm10'
                        }, {
                        }, {
                                name : 'm11'
                        }, {
                                name : 'm12'
                        }]);

        var tarData = {
                num : 3,
                rows : [{
                                        "tarName" : "存款时点余额",
                                        "totalValue" : "10,000,000,000.00",
                                        "ji1" : "",
                                        "m1" : "",
                                        "m2" : "",
                                        "m3" : "",
                                        "ji2" : "",
                                        "m4" : "",
                                        "m5" : "",
                                        "m6" : "",
                                        "ji3" : "",
                                        "m7" : "",
                                        "m8" : "",
                                        "m9" : "",
                                        "ji4" : "",
                                        "m10" : "",
                                        "m11" : "",
                                        "m12" : ""
                                }, {
                                    "tarName" : "存款日均余额",
                                    "totalValue" : "10,000,000,000.00",
                                    "ji1" : "",
                                    "m1" : "",
                                    "m2" : "",
                                    "m3" : "",
                                    "ji2" : "",
                                    "m4" : "",
                                    "m5" : "",
                                    "m6" : "",
                                    "ji3" : "",
                                    "m7" : "",
                                    "m8" : "",
                                    "m9" : "",
                                    "ji4" : "",
                                    "m10" : "",
                                    "m11" : "",
                                    "m12" : ""
                                }, {
                                    "tarName" : "贷款日均余额",
                                    "totalValue" : "10,000,000,000.00",
                                    "ji1" : "",
                                    "m1" : "",
                                    "m2" : "",
                                    "m3" : "",
                                    "ji2" : "",
                                    "m4" : "",
                                    "m5" : "",
                                    "m6" : "",
                                    "ji3" : "",
                                    "m7" : "",
                                    "m8" : "",
                                    "m9" : "",
                                    "ji4" : "",
                                    "m10" : "",
                                    "m11" : "",
                                    "m12" : ""
                                }]

        };
        
        var tarData1 = {
                num : 3,
                rows : [{
                                        "tarName" : "存款时点余额",
                                        "totalValue" : "10,000,000,000.00",
                                        "ji1" : "4,000,000,000.00",
                                        "m1" : "1,600,000,000.00",
                                        "m2" : "1,200,000,000.00",
                                        "m3" : "1,200,000,000.00",
                                        "ji2" : "3,000,000,000.00",
                                        "m4" : "1,200,000,000.00",
                                        "m5" : "900,000,000.00",
                                        "m6" : "900,000,000.00",
                                        "ji3" : "2,000,000,000.00",
                                        "m7" : "800,000,000.00",
                                        "m8" : "600,000,000.00",
                                        "m9" : "600,000,000.00",
                                        "ji4" : "1,000,000,000.00",
                                        "m10" : "400,000,000.00",
                                        "m11" : "300,000,000.00",
                                        "m12" : "300,000,000.00"
                                }, {
                                    "tarName" : "存款日均余额",
                                    "totalValue" : "10,000,000,000.00",
                                    "ji1" : "4,000,000,000.00",
                                    "m1" : "1,600,000,000.00",
                                    "m2" : "1,200,000,000.00",
                                    "m3" : "1,200,000,000.00",
                                    "ji2" : "3,000,000,000.00",
                                    "m4" : "1,200,000,000.00",
                                    "m5" : "900,000,000.00",
                                    "m6" : "900,000,000.00",
                                    "ji3" : "2,000,000,000.00",
                                    "m7" : "800,000,000.00",
                                    "m8" : "600,000,000.00",
                                    "m9" : "600,000,000.00",
                                    "ji4" : "1,000,000,000.00",
                                    "m10" : "400,000,000.00",
                                    "m11" : "300,000,000.00",
                                    "m12" : "300,000,000.00"
                                }, {
                                    "tarName" : "贷款日均余额",
                                    "totalValue" : "10,000,000,000.00",
                                    "ji1" : "4,000,000,000.00",
                                    "m1" : "1,600,000,000.00",
                                    "m2" : "1,200,000,000.00",
                                    "m3" : "1,200,000,000.00",
                                    "ji2" : "3,000,000,000.00",
                                    "m4" : "1,200,000,000.00",
                                    "m5" : "900,000,000.00",
                                    "m6" : "900,000,000.00",
                                    "ji3" : "2,000,000,000.00",
                                    "m7" : "800,000,000.00",
                                    "m8" : "600,000,000.00",
                                    "m9" : "600,000,000.00",
                                    "ji4" : "1,000,000,000.00",
                                    "m10" : "400,000,000.00",
                                    "m11" : "300,000,000.00",
                                    "m12" : "300,000,000.00"
                                }]

        };
        
        var tarReader = new Ext.data.JsonReader({
                                totalProperty : 'num',
                                idProperty : 'tarName',
                                root : 'rows'
                        }, record);
        var tarStore = new Ext.data.Store({
                                reader : tarReader
                        });

        tarStore.loadData(tarData);

        var tarEditListPanel = new Ext.grid.GridPanel({
        					    height:300,
                                store : tarStore,
                                frame : true,
                                cm : tarColumns,
                                stripeRows : true,
                                tbar : [{
                                    text : '分配',
                                    iconCls : 'page_reIcon',
                                    handler : function() {
                                    	  tarStore.loadData(tarData1);
                                    }

                            }
                          ],
                                frame : true
                        });