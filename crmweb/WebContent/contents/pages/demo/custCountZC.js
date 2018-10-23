Ext
        .onReady(function() {
            var h = document.body.clientHeight;
            var qForm = new Ext.form.FormPanel( {
                labelWidth : 90, // 标签宽度
                frame : true, // 是否渲染表单面板背景色
                labelAlign : 'middle', // 标签对齐方式
                buttonAlign : 'center',
                region : 'north',
                title : "报表管理->零售报表->客户数量统计表（按资产业务）",
                height : 120,
                items : [ {
                    layout : 'column',
                    border : false,
                    items : [ {
                        columnWidth : .25,
                        layout : 'form',
                        labelWidth : 80, // 标签宽度
                        defaultType : 'textfield',
                        border : false,
                        items : [ {
                            fieldLabel : '机构号',
                            name : 'e1',
                            xtype : 'textfield', // 设置为数字输入框类型
                            labelStyle : 'text-align:right;',
                            anchor : '80%'
                        } ]
                    }, {
                        columnWidth : .25,
                        layout : 'form',
                        labelWidth : 80, // 标签宽度
                        defaultType : 'textfield',
                        border : false,
                        items : [ {
                            xtype : 'datefield',
                            fieldLabel : '统计日期', // 标签
                            id : 'e4',
                            name : 'e4', // name:后台根据此name属性取值
                            allowBlank : true, // 是否允许为空
                            labelStyle : 'text-align:right;',
                            // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                            anchor : '80%' // 宽度百分比
                        } ]
                    }, {
                        columnWidth : .25,
                        layout : 'form',
                        labelWidth : 80, // 标签宽度
                        defaultType : 'textfield',
                        border : false,
                        items : []
                    }, {
                        columnWidth : .25,
                        layout : 'form',
                        labelWidth : 80, // 标签宽度
                        defaultType : 'textfield',
                        border : false,
                        items : []
                    } ]
                } ],
                buttons : [ {
                    text : '查询'
                /*
                 * handler : function() { queryBalanceInfo(qForm.getForm()); }
                 */
                }, {
                    text : '重置'
                /*
                 * handler : function() { qForm.getForm().reset(); }
                 */
                } ]
            });
            /** **************************************************************** */
            var fields = [], columns = [], data = [], continentGroupRow = [], subGroupRow = [];

            continentGroupRow = [ {
                header : '',
                colspan : 3,
                align : 'center'
            }, {
                header : '个人经营类客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '个人消费类客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '',
                colspan : 1,
                align : 'center'
            } ];

            subGroupRow = [ {
                header : '',
                colspan : 3,
                align : 'center'
            }, {
                header : '个体工商户',
                colspan : 3,
                align : 'center'
            }, {
                header : '小微企业主',
                colspan : 3,
                align : 'center'
            }, {
                header : '',
                colspan : 3,
                align : 'center'
            }, {
                header : '',
                colspan : 1,
                align : 'center'
            } ];

            var group = new Ext.ux.grid.ColumnHeaderGroup( {
                rows : [ continentGroupRow, subGroupRow ]
            });

            fields = [ {
                name : 'a1'
            }, {
                name : 'a2'
            }, {
                name : 'a3'
            }, {
                name : 'a4'
            }, {
                name : 'a5'
            }, {
                name : 'a6'
            }, {
                name : 'a7'
            }, {
                name : 'a8'
            }, {
                name : 'a9'
            }, {
                name : 'a10'
            }, {
                name : 'a11'
            }, {
                name : 'a12'
            }, {
                name : 'a13'
            } ];

            columns = [ {
                dataIndex : 'a1',
                header : '序号',
                sortable : true
            }, {
                dataIndex : 'a2',
                header : '机构号',
                sortable : true
            }, {
                dataIndex : 'a3',
                header : '机构名称',
                sortable : true
            }, {
                dataIndex : 'a4',
                header : '个数',
                sortable : true
            }, {
                dataIndex : 'a5',
                header : '较上月',
                sortable : true
            }, {
                dataIndex : 'a6',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a7',
                header : '个数',
                sortable : true
            }, {
                dataIndex : 'a8',
                header : '较上月',
                sortable : true
            }, {
                dataIndex : 'a9',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a10',
                header : '个数',
                sortable : true
            }, {
                dataIndex : 'a11',
                header : '较上月',
                sortable : true
            }, {
                dataIndex : 'a12',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a13',
                header : '统计日期',
                sortable : true
            } ];
            data = [
                    [ 1, '101', '某某银行1', 30, 20, 40, 30, 35, 30, 40, 33, 24,
                            '2012-06-28' ],
                    [ 2, '102', '某某银行2', 60, 40, 80, 60, 34, 65, 12, 3, 55,
                            '2012-06-28' ],
                    [ 3, '103', '某某银行3', 90, 60, 60, 45, 23, 55, 56, 54, 12,
                            '2012-06-28' ],
                    [ 4, '104', '某某银行4', 15, 10, 20, 15, 75, 23, 23, 12, 73,
                            '2012-06-28' ],
                    [ 5, '105', '某某银行5', 30, 20, 400, 300, 66, 43, 53, 35, 35,
                            '2012-06-28' ],
                    [ 6, '106', '某某银行6', 30, 20, 200, 150, 87, 23, 12, 8, 23,
                            '2012-06-28' ] ];

            var grid = new Ext.grid.GridPanel( {
            	   region : 'center',
                   autoScroll : true,
                store : new Ext.data.ArrayStore( {
                    fields : fields,
                    data : data
                }),
                stripeRows : true,
                columns : columns,
                viewConfig : {
                    forceFit : true
                },
                plugins : group
            });
            /** **************************************************************** */
            // 布局模型
            var viewport = new Ext.Viewport( {
            	layout:'fit',
            	items:[{
            	layout : 'border',
            	items: [qForm,grid] 
            	}]
});
        });