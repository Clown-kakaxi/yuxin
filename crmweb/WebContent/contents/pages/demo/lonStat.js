Ext.onReady(function() {
    var qForm = new Ext.form.FormPanel( {
        labelWidth : 90, // 标签宽度
        region : 'north',
        title : "报表管理->经营报表->经营单位贷款统计报表",
        frame : true, // 是否渲染表单面板背景色
        labelAlign : 'middle', // 标签对齐方式
        buttonAlign : 'center',
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
                    xtype : 'datefield',
                    fieldLabel : '统计起始日期', // 标签
                    name : 'e5', // name:后台根据此name属性取值
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
                items : [ {
                    xtype : 'datefield',
                    fieldLabel : '统计截止日期', // 标签
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
    var fields = [], columns = [], data = [], continentGroupRow = [],

    continentGroupRow = [ {
        header : '',
        colspan : 3,
        align : 'center'
    }, {
        header : '对公',
        colspan : 8,
        align : 'center'
    }, {
        header : '个人',
        colspan : 6,
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
    } ];

    columns = [ {
        dataIndex : 'a1',
        header : '机构号',
        sortable : true
    }, {
        dataIndex : 'a2',
        header : '机构名称',
        sortable : true
    }, {
        dataIndex : 'a3',
        header : '各项贷款合计',
        sortable : true
    }, {
        dataIndex : 'a4',
        header : '短期贷款时点',
        sortable : true
    }, {
        dataIndex : 'a5',
        header : '短期贷款日均',
        sortable : true
    }, {
        dataIndex : 'a6',
        header : '中长期贷款时点',
        sortable : true
    }, {
        dataIndex : 'a7',
        header : '中长期贷款日均',
        sortable : true
    }, {
        dataIndex : 'a8',
        header : '一般贷款时点合计',
        sortable : true
    }, {
        dataIndex : 'a9',
        header : '一般贷款日均合计',
        sortable : true
    }, {
        dataIndex : 'a10',
        header : '贴现时点',
        sortable : true
    }, {
        dataIndex : 'a11',
        header : '贴现日均',
        sortable : true
    }, {
        dataIndex : 'a12',
        header : '消费贷款时点',
        sortable : true
    }, {
        dataIndex : 'a13',
        header : '消费贷款日均',
        sortable : true
    }, {
        dataIndex : 'a14',
        header : '个人经营贷款时点',
        sortable : true
    }, {
        dataIndex : 'a15',
        header : '个人经营贷款日均',
        sortable : true
    }, {
        dataIndex : 'a16',
        header : '个人贷款时点合计',
        sortable : true
    }, {
        dataIndex : 'a17',
        header : '个人贷款日均合计',
        sortable : true
    } ];
    data = [
            [ '101', '某某银行1', 30, 2040, 30, 35, 30, 40, 33, 24, 20, 59, 40, 45,
                    34, 65, 234 ],
            [ '102', '某某银行2', 60, 4080, 60, 34, 65, 12, 3, 55, 12, 559, 324,
                    45, 34, 23, 523 ],
            [ '103', '某某银行3', 90, 6060, 45, 23, 55, 56, 54, 12, 32, 86, 54, 45,
                    34, 12, 634 ],
            [ '104', '某某银行4', 15, 1020, 15, 75, 23, 23, 12, 73, 54, 65, 65, 45,
                    34, 54, 54 ],
            [ '105', '某某银行5', 30, 20400, 300, 66, 43, 53, 35, 35, 20, 12, 23,
                    45, 34, 23, 231 ],
            [ '106', '某某银行6', 30, 20200, 150, 87, 23, 12, 8, 23, 20, 345, 254,
                    45, 34, 67, 123 ] ];

    var grid = new Ext.grid.GridPanel( {
        region:'center',
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