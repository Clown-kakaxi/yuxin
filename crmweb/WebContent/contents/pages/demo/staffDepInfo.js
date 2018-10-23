Ext.onReady(function() {
    var qForm = new Ext.form.FormPanel( {
        labelWidth : 90, // 标签宽度
        region : 'north',
        title : "报表管理->零售报表->各经营机构员工储蓄存款明细日报表",
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
    var fields = [], columns = [], data = [], continentGroupRow = [],

    continentGroupRow = [ {
        header : '',
        colspan : 5,
        align : 'center'
    }, {
        header : '储蓄月日均',
        colspan : 3,
        align : 'center'
    }, {
        header : '储蓄余额',
        colspan : 3,
        align : 'center'
    } ];
    var group = new Ext.ux.grid.ColumnHeaderGroup( {
        rows : [ continentGroupRow ]
    });

    fields = [ {
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
    } ];

    columns = [ {
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
        header : '员工姓名',
        sortable : true
    }, {
        dataIndex : 'a5',
        header : '员工号',
        sortable : true
    }, {
        dataIndex : 'a6',
        header : '协存号',
        sortable : true
    }, {
        dataIndex : 'a7',
        header : '活期',
        sortable : true
    }, {
        dataIndex : 'a8',
        header : '定期',
        sortable : true
    }, {
        dataIndex : 'a9',
        header : '合计',
        sortable : true
    }, {
        dataIndex : 'a10',
        header : '活期',
        sortable : true
    }, {
        dataIndex : 'a11',
        header : '定期',
        sortable : true
    }, {
        dataIndex : 'a12',
        header : '合计',
        sortable : true
    } ];
    data = [ [ '101', '某某银行1', '甲1', '2201', 40, 30, 35, 30, 40, 33, 24 ],
            [ '102', '某某银行2', '甲2', '3301', 80, 60, 34, 65, 12, 3, 55 ],
            [ '103', '某某银行3', '甲3', '4401', 60, 45, 23, 55, 56, 54, 12 ],
            [ '104', '某某银行4', '甲4', '5501', 20, 15, 75, 23, 23, 12, 73 ],
            [ '105', '某某银行5', '甲5', '6601', 400, 300, 66, 43, 53, 35, 35 ],
            [ '106', '某某银行6', '甲6', '7701', 200, 150, 87, 23, 12, 8, 23 ] ];

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