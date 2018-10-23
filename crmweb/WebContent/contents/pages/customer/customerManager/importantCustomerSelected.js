Ext.onReady(function() {
    Ext.QuickTips.init();

    // 重点客户类型
        var imptCustTypStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=IMPT_CUST_TYPE'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ {
                name : 'key',
                type : 'int'
            }, 'value' ])
        });
        imptCustTypStore.load();
        // 证件类型
        var certTypStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=XD000040'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        certTypStore.load();
        // 客户类型
        var custTypStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,

            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=XD000080'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        custTypStore.load();
        // 客户级别
        var custLevStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=CDE0100016'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        custLevStore.load();

        var sId;
        var mainType;
        // 复选框
        var sm = new Ext.grid.CheckboxSelectionModel();
        // 行号
        var rownum = new Ext.grid.RowNumberer( {
            header : 'No.',
            width : 28
        });

        // **分配start**********************************************************
        var panelType = new Ext.FormPanel( {
            id : 'add',
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            items : [ {
                autoHeight : true,
                items : [ {
                    layout : 'column',
                    items : [ {
                        columnWidth : .95,
                        layout : 'form',
                        items : [ {
                            xtype : 'fieldset',
                            title : '重点户类型',
                            // name:'actiCustRefScore',
                            autoHeight : true,
                            defaultType : 'radio',
                            hideLabels : true,
                            layout : 'column',
                            items : [ {
                                columnWidth : .5,
                                boxLabel : '重点户类型一',
                                id : 'custRef1',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '1'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型二',
                                id : 'custRef2',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '2'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型三',
                                id : 'custRef3',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '3'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型四',
                                id : 'custRef4',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '4'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型五',
                                id : 'custRef5',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '5'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型六',
                                id : 'custRef6',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '6'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型七',
                                id : 'custRef7',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '7'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型八',
                                id : 'custRef8',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '8'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型九',
                                id : 'custRef9',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '9'
                            }, {
                                columnWidth : .5,
                                boxLabel : '重点户类型十',
                                id : 'custRef10',
                                name : 'IMPORT_CUST_TYPE',
                                inputValue : '10'
                            } ]
                        } ]
                    } ]
                } ]

            } ]
        });

        // 重点户模态窗口
        var orgWin = new Ext.Window( {
            plain : true,
            layout : 'fit',
            resizable : true,
            draggable : true,
            closable : true,
            autoScroll : true,
            closeAction : 'hide',
            modal : true, // 模态窗口
            shadow : true,
            loadMask : true,
            maximizable : true,
            collapsible : true,
            titleCollapse : true,
            border : false,
            width : 405,
            height : 260,
            buttonAlign : "center",
            title : '选择重点户类型',
            items : panelType,
            buttons : [ {
                text : '保存',
                handler : function() {
                    if (listPanel.grid.selModel.hasSelection()) {
                        var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
                var selectLength = records.length;// 得到行数组的长度

                var custStr = '';
//                Ext.Ajax.request( {
//                    // url : basepath + '/importantCustomer!assignCust.json',
//                    params : {
//                        custStr : sId
//                    },
//                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//                    method : 'POST',
//                    scope : orgWin,
//                    success : function(a, b) {
//                        Ext.Msg.alert('提示', '操作成功');
//                        listPanel.loadCurrData();
//                    },
//                    failure : function(a, b) {
//                        Ext.Msg.alert('提示', '操作失败');
//                        listPanel.loadCurrData();
//                    }
//                });
                Ext.Msg.alert('提示', '操作成功');
                orgWin.hide();
            }
        }
            }, {
                text : '关闭',
                handler : function() {
                    orgWin.hide();
                }
            } ]
        });
        // **重点户分配end**********************************************************
        // 最终展现的panel
        var listPanel = new Mis.Ext.CrudPanel( {
            id : "listPanel",
            title : "客户管理->重点户管理->重点户设置",
            stUrl : basepath + '/importantCustomer.json',
            primary : "CUST_ID",
            checkbox : true,
            // 定义查询条件Form的高度
            seFormHeight : 80,
            // 定义增删详情页面弹出窗口高度
            winHeight : 250,
            // 宽度
            winWidth : 600,
            dbclick : false,
            // 新增
            // addUrl : basepath + '/customer_assign.json',
            buts : [{
                        id : 'assignImptCust',
                        xtype : 'button',
                        tooltip : '设置为重点户',
                        text : '设置为重点户',
                        iconCls : 'editIconCss',
                        // disabled:JsContext.checkGrant('assignOrg'),
                        listeners : {
                            click : function(n) {
                                if (listPanel.grid.selModel.hasSelection()) {
                                    var records = listPanel.grid.selModel
                                            .getSelections();// 得到被选择的行的数组
                                    var recordsLen = records.length;// 得到行数组的长度
                                    if (recordsLen > 1) {
                                        Ext.Msg.alert("系统提示信息",
                                                "请选择其中一条记录进行分配！");
                                    } else {
                                        sId = listPanel.grid
                                                .getSelectionModel()
                                                .getSelected().get('CUST_ID');
                                        orgWin.show();
                                    }
                                } else {
                                    Ext.Msg.alert("提示", "请先选择要分配的记录!");
                                }
                            }
                        }
                    }, '-', new Ext.Button( {
                        text : '批量导入',
                        iconCls : 'importIconCss',
                        handler : function() {
                            importForm.tradecode = 'importantCustSelected';
                            importWindow.show();
                        }
                    }) ],
            // 查询字段定义，若不定义则不出现查询条件From
            selectItems : {
                layout : 'column',
                items : [ {
                    columnWidth : .25,
                    layout : 'form',
                    labelWidth : 90,
                    defaultType : 'textfield',
                    border : true,
                    items : [ {
                        name : '',
                        xtype : 'textfield',
                        fieldLabel : '客户中文名称',
                        anchor : '95%'
                    } ]
                }, {
                    columnWidth : .25,
                    layout : 'form',
                    border : true,
                    items : [ {
                        store : imptCustTypStore,
                        xtype : 'combo',
                        resizable : true,
                        fieldLabel : '重点户类型',
                        name : '',
                        hiddenName : '',
                        valueField : 'key',
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '95%'
                    } ]
                }, {
                    columnWidth : .25,
                    layout : 'form',
                    labelWidth : 100,
                    defaultType : 'textfield',
                    border : true,
                    items : [ new Com.yucheng.bcrm.common.OrgField( {
                        searchType : 'SUBTREE',/*
                                                 * 指定查询机构范围属性
                                                 * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
                                                 * （所有父、祖机构）ALLORG（所有机构）
                                                 */
                        fieldLabel : '所属机构',
                        labelStyle : 'text-align:right;',
                        id : 'jigouhao', // 放大镜组件ID，用于在重置清空时获取句柄
                        name : 'CUST_ORG',
                        hiddenName : 'instncode', // 后台获取的参数名称
                        anchor : '95%',
                        checkBox : true
                    // 复选标志
                            }) ]
                } ]
            },
            gclms : [ {
                name : 'org_id'
            }, {
                name : 'org_name'
            }, {
                name : 'CUST_ID',
                header : '客户编号',
                width : 100,
                sortable : true
            }, {
                header : '客户名称',
                name : 'CUST_ZH_NAME',
                width : 150,
                sortable : true
            }, {
                header : '客户维护人',
                name : 'MGR_NAME',
                width : 100,
                sortable : true
            }, {
                header : '一级分行',
                name : 'SUPBRID',
                width : 100,
                sortable : true
            }, {
                header : '二级分行',
                width : 100,
                sortable : true
            }, {
                header : '所属机构',
                name : 'INSTITUTION_NAME',
                width : 100,
                sortable : true
            }, {
                header : '客户类型',
                name : 'CUST_TYP',
                width : 100,
                type : 'mapping',
                store : custTypStore,
                mappingkey : 'key',
                mappingvalue : 'value'
            }, {
                header : '客户级别',
                name : 'CUST_LEV',
                width : 100,
                sortable : true,
                type : 'mapping',
                store : custLevStore,
                mappingkey : 'key',
                mappingvalue : 'value'
            }, {
                header : '**客户状态',
                name : 'CUST_LEV',
                width : 100,
                sortable : true,
                type : 'mapping',
                store : custLevStore,
                mappingkey : 'key',
                mappingvalue : 'value'
            }, {
                header : '客户网银状态',
                width : 100,
                sortable : true
            }, {
                header : '证件类型',
                name : 'CERT_TYPE',
                width : 100,
                type : 'mapping',
                store : certTypStore,
                mappingkey : 'key',
                mappingvalue : 'value'
            }, {
                header : '证件号码',
                name : 'CERT_NUM',
                width : 100,
                sortable : true
            }, {
                header : '是否重点户',
                name : 'IF_IMPORTANT_CUST',
                width : 100,
                sortable : true,
                renderer : function(value) {
                    if (value == 1)
                        return '是';
                    else if (value == 0)
                        return '否';
                    else
                        return value;
                }
            }, {
                header : '重点客户类型',
                name : 'IMPORT_CUST_TYPE',
                width : 100,
                sortable : true,
                type : 'mapping',
                store : imptCustTypStore,
                mappingkey : 'key',
                mappingvalue : 'value'
            }, {
                header : '有效授信额度',
                name : 'q',
                width : 100,
                sortable : true
            }, {
                header : '用信余额',
                name : 'q',
                width : 100,
                sortable : true
            }, {
                header : '行业分类',
                name : 'q',
                width : 100,
                sortable : true
            }, {
                header : '组织类别',
                name : 'q',
                width : 100,
                sortable : true
            }, {
                header : '所有制',
                name : 'q',
                width : 100,
                sortable : true
            }, {
                header : '客户规模',
                name : 'q',
                width : 100,
                sortable : true
            }, {
                header : '利润贡献度',
                name : 'ROTECB',
                width : 100,
                sortable : true
            }, {
                header : '存款余额',
                name : 'CKBAL',
                width : 100,
                sortable : true
            }, {
                header : '存款日均',
                name : 'CKBALAVG',
                width : 100,
                sortable : true
            }, {
                header : '贷款余额',
                name : 'LOANBAL',
                width : 100,
                sortable : true
            }, {
                header : '贷款日均',
                name : 'LOANBALAVG',
                width : 100,
                sortable : true
            }, {
                header : '承兑余额',
                name : 'CDBAL',
                width : 100,
                sortable : true
            }, {
                header : '其中:电票承兑余额',
                name : 'DPCDBAL',
                width : 100,
                sortable : true
            }, {
                header : '承兑累计',
                name : 'CDSUM',
                width : 100,
                sortable : true
            }, {
                header : '其中：电票承兑累计',
                name : 'DPCDSUM',
                width : 100,
                sortable : true
            }, {
                header : '贴现累计',
                name : 'TIEXBAL',
                width : 100,
                sortable : true
            }, {
                header : '其中：电票贴现累计',
                name : 'DPTIEXBAL',
                width : 100,
                sortable : true
            }, {
                header : '客户累计结算量',
                name : 'CUSTSUMBAL',
                width : 100,
                sortable : true
            }, {
                header : '中间业务收入',
                name : 'MIDBAL',
                width : 100,
                sortable : true
            }, {
                header : '国际结算量',
                name : 'NASSUMBAL',
                width : 100,
                sortable : true
            }, {
                header : '电子银行结算量',
                name : 'EBANKSUM',
                width : 100,
                sortable : true
            } ],
            pagesize : 20
        });

        // 布局模型
        var viewport = new Ext.Viewport( {
            layout : 'fit',
            items : [ listPanel ]
        });
    });