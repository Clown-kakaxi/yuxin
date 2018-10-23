Ext.onReady(function() {

            var xlStore = new Ext.data.ArrayStore( {
                fields : [ 'myId', 'displayText' ],
                data : [ [ '1', '一月' ], [ '2', '二月' ], [ '3', '三月' ],
                        [ '4', '四月' ], [ '5', '五月' ], [ '6', '六月' ],
                        [ '7', '七月' ], [ '8', '八月' ], [ '9', '九月' ],
                        [ '10', '十月' ], [ '11', '十一月' ], [ '12', '十二月' ] ]
            });
            var year = parseInt(new Date().getFullYear());
            var yearStore = new Ext.data.ArrayStore( {
                fields : [ 'key', 'value' ],
                data : [ [ year - 10, year - 10 ], [ year - 9, year - 9 ],
                        [ year - 8, year - 8 ], [ year - 7, year - 7 ],
                        [ year - 6, year - 6 ], [ year - 5, year - 5 ],
                        [ year - 4, year - 4 ], [ year - 3, year - 3 ],
                        [ year - 2, year - 2 ], [ year - 1, year - 1 ],
                        [ year, year ], [ year + 1, year + 1 ],
                        [ year + 2, year + 2 ], [ year + 3, year + 3 ],
                        [ year + 4, year + 4 ], [ year + 5, year + 5 ],
                        [ year + 6, year + 6 ], [ year + 7, year + 7 ],
                        [ year + 8, year + 8 ], [ year + 9, year + 9 ] ]
            });
            var xlStore1 = new Ext.data.ArrayStore( {
                fields : [ 'myId', 'displayText' ],
                data : [ [ '1', '放款笔数' ], [ '2', '放款金额' ] ]
            });
            var searchPanel = new Ext.form.FormPanel(
                    {
                        labelWidth : 90,
                        labelAlign : 'right',
                        height : 100,
                        frame : true,
                        region : 'north',
                        title : "小贷客户放款趋势图-->查询",
                        autoScroll : true,
                        layout : 'column',

                        items : [ {
                            columnWidth : .25,
                            layout : 'form',
                            items : [ {
                                xtype : 'datefield',
                                fieldLabel : '开始时间',
                                format : 'Y-m-d',
                                name : 'ATTENDONDATE',
                                selectOnFocus : true,
                                anchor : '95%'
                            } ]
                        }, {
                            columnWidth : .25,
                            layout : 'form',
                            items : [{
                                xtype : 'datefield',
                                fieldLabel : '结束时间',
                                format : 'Y-m-d',
                                name : 'ATTENDONDATE1',
                                selectOnFocus : true,
                                anchor : '95%'
                            }  ]
                        }, {
                            columnWidth : .25,
                            layout : 'form',
                            items : [ {
                                labelStyle : 'text-align:right;',
                                triggerAction : 'all',
                                mode : 'local',
                                xtype : 'combo',
                                store : yearStore,
                                valueField : 'key',
                                displayField : 'value',
                                emptyText : '请选择',
                                fieldLabel : '至',
                                id : 'crmDtEnd',
                                name : 'crmDtEnd',
                                anchor : '90%'
                            } ]
                        }, {
                            columnWidth : .25,
                            layout : 'form',
                            items : [ {
                                fieldLabel : '月份',
                                name : 'startLoanMonth',
                                id : 'startLoanMonth',
                                forceSelection : true,
                                xtype : 'combo',
                                labelStyle : 'text-align:right;',
                                triggerAction : 'all',
                                mode : 'local',
                                store : xlStore,
                                valueField : 'myId',
                                displayField : 'displayText',
                                emptyText : '请选择',
                                anchor : '90%'
                            }

                            ]
                        }

                        ],
                        buttonAlign : 'center',
                        buttons : [
                                {
                                    text : '查询',
                                    handler : function() {
//                                        if (start == '' && end != '') {
//                                            Ext.Msg.alert('消息框', '请先选择开始时间！');
//                                            Ext.getCmp('crmDtEnd').reset();
//                                            return false;
//                                        } else {
//                                            return;
//                                        }
                                    document.getElementById("startLoanMonth1").value = searchPanel.getForm().findField("startLoanMonth").value;
                                    document.getElementById("crmDtEnd1").value = searchPanel.getForm().findField("ATTENDONDATE1").value;

                                        load();
                                    }
                                }, {
                                    text : '重置',
                                    handler : function() {
                                        searchPanel.getForm().reset();
                                    }
                                } ]
                    });

            var view = new Ext.Viewport( {
                layout : 'fit',
                items : [ {
                    layout : 'border',
                    items : [ {
                        region : 'north',
                        id : 'north-panel',
                        height : 105,
                        layout : 'fit',
                        items : [ searchPanel ]
                    }, {
                        region : 'center',
                        id : 'center-panel',
                        layout : 'fit',
                        height : 800,
                        html : '<div id="chartdiv"></div>'
                    } ]
                } ]
            });

            // searchPanel.getForm().findField("startLoanYear").setValue(new
            // Date().getYear() + "-01-01");
            // searchPanel.getForm().findField("startLoanMonth").setValue(new
            // Date().getYear() + "-12-31");
        })