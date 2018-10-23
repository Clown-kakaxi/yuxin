Ext
        .onReady(function() {
            var qForm = new Ext.form.FormPanel( {
                labelWidth : 90, // 标签宽度
                frame : true, // 是否渲染表单面板背景色
                region : 'north',
                title : "报表管理->零售报表-> 客户资产情况统计表",
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
                            format : 'Y-m-d',
                            editable : false,
                            id : 'e4',
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
                header : '普通客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '潜力客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '黄金客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '白金客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '钻石客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '私人银行客户',
                colspan : 6,
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
            }, {
                name : 'a26'
            }, {
                name : 'a27'
            }, {
                name : 'a28'
            }, {
                name : 'a29'
            }, {
                name : 'a30'
            }, {
                name : 'a31'
            }, {
                name : 'a32'
            }, {
                name : 'a33'
            }, {
                name : 'a34'
            }, {
                name : 'a35'
            }, {
                name : 'a36'
            }, {
                name : 'a37'
            }, {
                name : 'a38'
            }, {
                name : 'a39'
            }, {
                name : 'a40'
            } ];

            columns = [ {
                dataIndex : 'a1',
                header : '序号',
                sortable : true
            }, {
                dataIndex : 'a2',
                header : '机构号',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a3',
                header : '机构名称',
                sortable : true,
                width : 250
            }, {
                dataIndex : 'a4',
                header : '存款年日均',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a5',
                header : '存款余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a6',
                header : '理财余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a7',
                header : '保险余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a8',
                header : '贵金属余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a9',
                header : '余额合计',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a10',
                header : '存款年日均',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a11',
                header : '存款余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a12',
                header : '理财余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a13',
                header : '保险余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a14',
                header : '贵金属余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a15',
                header : '余额合计',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a16',
                header : '存款年日均',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a17',
                header : '存款余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a18',
                header : '理财余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a19',
                header : '保险余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a20',
                header : '贵金属余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a21',
                header : '余额合计',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a22',
                header : '存款年日均',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a23',
                header : '存款余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a24',
                header : '理财余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a25',
                header : '保险余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a26',
                header : '贵金属余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a27',
                header : '余额合计',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a28',
                header : '存款年日均',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a29',
                header : '存款余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a30',
                header : '理财余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a31',
                header : '保险余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a32',
                header : '贵金属余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a33',
                header : '余额合计',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a34',
                header : '存款年日均',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a35',
                header : '存款余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a36',
                header : '理财余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a37',
                header : '保险余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a38',
                header : '贵金属余额',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a39',
                header : '余额合计',
                sortable : true,
                width : 200
            }, {
                dataIndex : 'a40',
                header : '统计日期',
                sortable : true,
                width : 250
            } ];
            data = [
                    [ 1, '101', '某某银行1', 30, 20, 0.66, 40, 30, 0.75, 35, 30,
                            40, 33, 24, 20, 59, 40, 45, 34, 65, 23, 56, 32, 64,
                            30, 20, 0.66, 40, 30, 0.75, 35, 30, 40, 33, 24, 20,
                            59, 40, 45, '2012-06-28' ],
                    [ 2, '102', '某某银行2', 60, 40, 0.66, 80, 60, 0.75, 34, 65,
                            12, 3, 55, 12, 559, 324, 45, 34, 65, 23, 56, 32,
                            64, 60, 40, 0.66, 80, 60, 0.75, 34, 65, 12, 3, 55,
                            12, 559, 324, 45, '2012-06-28' ],
                    [ 3, '103', '某某银行3', 90, 60, 0.66, 60, 45, 0.75, 23, 55,
                            56, 54, 12, 32, 86, 54, 45, 34, 65, 23, 56, 32, 64,
                            90, 60, 0.66, 60, 45, 0.75, 23, 55, 56, 54, 12, 32,
                            86, 54, 45, '2012-06-28' ],
                    [ 4, '104', '某某银行4', 15, 10, 0.66, 20, 15, 0.75, 75, 23,
                            23, 12, 73, 54, 65, 65, 45, 34, 65, 23, 56, 32, 64,
                            15, 10, 0.66, 20, 15, 0.75, 75, 23, 23, 12, 73, 54,
                            65, 65, 45, '2012-06-28' ],
                    [ 5, '105', '某某银行5', 30, 20, 0.66, 400, 300, 0.75, 66, 43,
                            53, 35, 35, 20, 12, 23, 45, 34, 65, 23, 56, 32, 64,
                            30, 20, 0.66, 400, 300, 0.75, 66, 43, 53, 35, 35,
                            20, 12, 23, 45, '2012-06-28' ],
                    [ 6, '106', '某某银行6', 30, 20, 0.66, 200, 150, 0.75, 87, 23,
                            12, 8, 23, 20, 345, 254, 45, 34, 65, 23, 56, 32,
                            64, 30, 20, 0.66, 200, 150, 0.75, 87, 23, 12, 8,
                            23, 20, 345, 254, 45, '2012-06-28' ] ];

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