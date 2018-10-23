Ext.onReady(function() {
    var h = document.body.clientHeight;
    var qForm = new Ext.form.FormPanel( {
        labelWidth : 90, // 标签宽度
        region : 'north',
        title : "报表管理->零售报表-> 消费习惯分析表",
        frame : true, // 是否渲染表单面板背景色
        labelAlign : 'middle', // 标签对齐方式
        buttonAlign : 'center',
        height : 120,
            layout : 'column',
            items : [ {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    fieldLabel : '客户号',
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
                    editable:false,
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

    /*
     * continentGroupRow = [ {header: '', colspan: 8, align: 'center'}, {header:
     * '月统计', colspan: 4, align: 'center'}, {header: '年统计', colspan: 4, align:
     * 'center'} ]; var group = new Ext.ux.grid.ColumnHeaderGroup({ rows:
     * [continentGroupRow] });
     */

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
    }
    /*
     * {name:'a17'}, {name:'a18'}, {name:'a19'}, {name:'a20'}, {name:'a21'},
     * {name:'a22'}, {name:'a23'}, {name:'a24'}, {name:'a25'}, {name:'a26'},
     * {name:'a27'}, {name:'a28'}, {name:'a29'}, {name:'a30'}, {name:'a31'},
     * {name:'a32'}, {name:'a33'}, {name:'a34'}, {name:'a35'}, {name:'a36'},
     * {name:'a37'}, {name:'a38'}, {name:'a39'}, {name:'a40'}
     */
    ];

    columns = [ {
        dataIndex : 'a1',
        header : '日期',
        sortable : true
    }, {
        dataIndex : 'a2',
        header : '客户号',
        sortable : true
    }, {
        dataIndex : 'a3',
        header : '交易类型',
        sortable : true
    }, {
        dataIndex : 'a4',
        header : '币种',
        sortable : true
    }, {
        dataIndex : 'a5',
        header : '交易渠道',
        sortable : true
    }, {
        dataIndex : 'a6',
        header : '本月累计金额',
        sortable : true
    }, {
        dataIndex : 'a7',
        header : '本月累计笔数',
        sortable : true
    }, {
        dataIndex : 'a8',
        header : '本季累计金额',
        sortable : true
    }, {
        dataIndex : 'a9',
        header : '本季累计笔数',
        sortable : true
    }, {
        dataIndex : 'a10',
        header : '本年累计金额',
        sortable : true
    }, {
        dataIndex : 'a11',
        header : '本年累计笔数',
        sortable : true
    } ];
    data = [
            [ '2012-06-28', '00234', '', '人民币', '', '30,000.00', 12,
                    '303,000.00', 34, '4,5334,533.00', 345 ],
            [ '2012-06-28', '02342', '', '人民币', '', '60,000.00', 23,
                    '620,000.00', 69, '6,533,454.00', 149 ],
            [ '2012-06-28', '03453', '', '人民币', '', '45,646.00', 4,
                    '6,783,666.00', 14, '23,234,152,234', 254 ],
            [ '2012-06-28', '03452', '', '人民币', '', '345,765.00', 17,
                    '453,453,534', 56, '6,867,658.00', 167 ],
            [ '2012-06-28', '05674', '', '人民币', '', '67,847.00', 6, '384,502',
                    23, '6,784,456.00', 467 ],
            [ '2012-06-28', '03455', '', '人民币', '', '4,353.00', 9,
                    '36,546,687', 21, '4,456,464.00', 384 ] ];

    var grid = new Ext.grid.GridPanel( {
        region : 'center',
        store : new Ext.data.ArrayStore( {
            fields : fields,
            data : data
        }),
        stripeRows : true,
        columns : columns,
        viewConfig : {
            forceFit : true
        }
    // plugins: group
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