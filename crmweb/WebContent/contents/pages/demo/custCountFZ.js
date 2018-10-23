Ext
        .onReady(function() {
            var h = document.body.clientHeight;
            var qForm = new Ext.form.FormPanel( {
                labelWidth : 90, // 标签宽度
                region : 'north',
                title : "报表管理->零售报表->客户数量统计表（按负债业务）",
                frame : true, // 是否渲染表单面板背景色
                labelAlign : 'middle', // 标签对齐方式
                buttonAlign : 'center',
                height : 120,
                    layout : 'column',
                    items : [ {
                        columnWidth : .25,
                        layout : 'form',
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
                        items : [ {
                            xtype : 'datefield',
                            fieldLabel : '统计日期', // 标签
                            id : 'e4',
                            format : 'Y-m-d',
                            editable : false,
                            name : 'e4', // name:后台根据此name属性取值
                            allowBlank : true, // 是否允许为空
                            labelStyle : 'text-align:right;',
                            // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                            anchor : '80%' // 宽度百分比
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
            var fields = [], columns = [], data = [], continentGroupRow = [],

            continentGroupRow = [ {
                header : '',
                colspan : 3,
                align : 'center'
            }, {
                header : '零余额客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '普通客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '潜力客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '黄金客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '白金客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '钻石客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '私人银行客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '',
                colspan : 1,
                align : 'center'
            } ];
            var group = new Ext.ux.grid.ColumnHeaderGroup( {
                rows : [ continentGroupRow ]
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
            }, {
                name : 'a14'
            }, {
                name : 'a15'
            }, {
                name : 'a16'
            }, {
                name : 'a17'
            }, {
                name : 'a18'
            }, {
                name : 'a19'
            }, {
                name : 'a20'
            }, {
                name : 'a21'
            }, {
                name : 'a22'
            }, {
                name : 'a23'
            }, {
                name : 'a24'
            }, {
                name : 'a25'
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
                sortable : true,
                width : 100
            }, {
                dataIndex : 'a4',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a5',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a6',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a7',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a8',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a9',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a10',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a11',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a12',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a13',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a14',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a15',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a16',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a17',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a18',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a19',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a20',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a21',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a22',
                header : '存量',
                sortable : true
            }, {
                dataIndex : 'a23',
                header : '较月初',
                sortable : true
            }, {
                dataIndex : 'a24',
                header : '较年初',
                sortable : true
            }, {
                dataIndex : 'a25',
                header : '统计日期',
                sortable : true,
                width : 100
            } ];
            data = [
                    [ 1, '101', '某某银行1', 30, 20, 0.66, 40, 30, 0.75, 35, 30,
                            40, 33, 24, 20, 59, 40, 45, 34, 65, 23, 56, 32, 64,
                            '2012-06-28' ],
                    [ 2, '102', '某某银行2', 60, 40, 0.66, 80, 60, 0.75, 34, 65,
                            12, 3, 55, 12, 559, 324, 45, 34, 65, 23, 56, 32,
                            64, '2012-06-28' ],
                    [ 3, '103', '某某银行3', 90, 60, 0.66, 60, 45, 0.75, 23, 55,
                            56, 54, 12, 32, 86, 54, 45, 34, 65, 23, 56, 32, 64,
                            '2012-06-28' ],
                    [ 4, '104', '某某银行4', 15, 10, 0.66, 20, 15, 0.75, 75, 23,
                            23, 12, 73, 54, 65, 65, 45, 34, 65, 23, 56, 32, 64,
                            '2012-06-28' ],
                    [ 5, '105', '某某银行5', 30, 20, 0.66, 400, 300, 0.75, 66, 43,
                            53, 35, 35, 20, 12, 23, 45, 34, 65, 23, 56, 32, 64,
                            '2012-06-28' ],
                    [ 6, '106', '某某银行6', 30, 20, 0.66, 200, 150, 0.75, 87, 23,
                            12, 8, 23, 20, 345, 254, 45, 34, 65, 23, 56, 32,
                            64, '2012-06-28' ] ];

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