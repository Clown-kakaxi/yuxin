Ext.onReady(function() {
    var h = document.body.clientHeight;
    var qForm = new Ext.form.FormPanel( {
        labelWidth : 90, // 标签宽度
        frame : true, // 是否渲染表单面板背景色
        labelAlign : 'middle', // 标签对齐方式
        region : 'north',
        title : "报表管理->零售报表->消费群体分析表",
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
                    id : 'e4',
                    format : 'Y-m-d',
                    editable:false,
                    name : 'e4', // name:后台根据此name属性取值
                    allowBlank : true, // 是否允许为空
                    labelStyle : 'text-align:right;',
                    // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                    anchor : '80%' // 宽度百分比
            }]
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
        header : '客户群编号',
        sortable : true
    }, {
        dataIndex : 'a3',
        header : '客户群类型',
        sortable : true
    }, {
        dataIndex : 'a4',
        header : '产品编号',
        sortable : true
    }, {
        dataIndex : 'a5',
        header : '产品名称',
        sortable : true
    }, {
        dataIndex : 'a6',
        header : '产品类型',
        sortable : true
    }, {
        dataIndex : 'a7',
        header : '交易类型',
        sortable : true
    }, {
        dataIndex : 'a8',
        header : '币种',
        sortable : true
    }, {
        dataIndex : 'a9',
        header : '交易渠道',
        sortable : true
    }, {
        dataIndex : 'a10',
        header : '客户数',
        sortable : true
    }, {
        dataIndex : 'a11',
        header : '交易金额',
        sortable : true
    }, {
        dataIndex : 'a11',
        header : '交易笔数',
        sortable : true
    } ];
    data = [
            [ '2012-06-28', '00234', '', '345', '活期储蓄存款', '存款', '', '人民币', '',
                    12, '303,000.00', 345 ],
            [ '2012-06-28', '02342', '', '456', '其他按揭贷款', '贷款', '', '人民币', '',
                    23, '620,000.00', 149 ],
            [ '2012-06-28', '03453', '', '457', '出口质押', '出口', '', '人民币', '', 4,
                    '6,783,666.00', 254 ],
            [ '2012-06-28', '03452', '', '565', '银行承兑汇票贴现', '贴现', '', '人民币',
                    '', 17, '453,453,534', 167 ],
            [ '2012-06-28', '05674', '', '287', '提货担保', '担保', '', '人民币', '', 6,
                    '384,502', 467 ],
            [ '2012-06-28', '03455', '', '754', '进口代收', '进口', '', '人民币', '', 9,
                    '36,546,687', 384 ] ];

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