Ext.onReady(function() {
    var qForm = new Ext.form.FormPanel( {
        labelWidth : 90, // 标签宽度
        region : 'north',
        title : "报表管理->管理报表->系统应用模块使用情况统计",
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
                    xtype : 'datefield',
                    fieldLabel : '统计截止日期', // 标签
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
    var fields = [], columns = [], data = [];

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
    } ];

    columns = [ {
        dataIndex : 'a1',
        header : '序号',
        sortable : true,
        width : 50
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
        header : '模块',
        sortable : true
    }, {
        dataIndex : 'a5',
        header : '一级功能',
        sortable : true
    }, {
        dataIndex : 'a6',
        header : '二级功能',
        sortable : true
    }, {
        dataIndex : 'a7',
        header : '三级功能',
        sortable : true
    }, {
        dataIndex : 'a8',
        header : '使用次数',
        sortable : true
    } ];
    data = [ [ 1, '101', '某某银行1', 'XXXX', 'XXXX', 'XXXX', 'XXXX', 234 ],
            [ 2, '102', '某某银行2', 'XXXX', 'XXXX', 'XXXX', 'XXXX', 15 ],
            [ 3, '103', '某某银行3', 'XXXX', 'XXXX', 'XXXX', 'XXXX', 28 ] ];

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