Ext.onReady(function() {
    Ext.QuickTips.init();

    var qForm = new Ext.form.FormPanel( {
        title : "个人汇总查询",
        labelWidth : 90, // 标签宽度
        frame : true, // 是否渲染表单面板背景色
        labelAlign : 'middle', // 标签对齐方式
        buttonAlign : 'center',
        region : 'north',
        split : true,
        height : 120,
        layout : 'column',
        items : [ {
            columnWidth : .25,
            layout : 'form',
            items : [ new Com.yucheng.bcrm.common.OrgField( {
                searchType : 'ALLORG',/*
                                         * 指定查询机构范围属性
                                         * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
                                         * （所有父、祖机构）ALLORG（所有机构）
                                         */
                fieldLabel : '所属机构',
                labelStyle : 'text-align:right;',
                id : 'jigouhao', // 放大镜组件ID，用于在重置清空时获取句柄
                name : 'CUST_ORG',
                hiddenName : 'checkedNodes', // 后台获取的参数名称
                anchor : '90%',
                checkBox : true
            // 复选标志
                    }) ]
        }, {
            columnWidth : .25,
            layout : 'form',
            items : [ {
                fieldLabel : '截止日期',
                labelStyle : 'text-align:right;',
                xtype : 'datefield',
                format : 'Y-m-d',
                name : 'END_DATE',
                anchor : '90%'
            } ]
        } ],
        buttons : [ {
            text : '查询',
            handler : function() {
                debugger;
                var conditionStr = qForm.getForm().getValues(false);
                store.baseParams = {
                    "condition" : Ext.encode(conditionStr)
                };
                store.load( {
                    params : {
                        start : 0,
                        limit : parseInt(pagesize_combo.getValue())
                    }
                });

            }
        }, {
            text : '重置',
            handler : function() {
                qForm.getForm().reset();
                Ext.getCmp("jigouhao").setValue('');
            }
        } ]
    });

    // 定义自动当前页行号
        var rownum = new Ext.grid.RowNumberer( {
            header : 'No.',
            width : 28
        });

        var continentGroupRow = [ {
            header : '',
            colspan : 7,
            align : 'center'
        }, {
            header : '其中：担任角色',
            colspan : 4,
            align : 'center'
        } ];

        var group = new Ext.ux.grid.ColumnHeaderGroup( {
            rows : [ continentGroupRow ]
        });

        var record = Ext.data.Record.create( [ {
            name : 'id',
            mapping : 'ID'
        }, {
            name : 'name',
            mapping : 'NAME'
        }, {
            name : 'xcNo',
            mapping : 'XC_NO'
        }, {
            name : 'atSubOrgName',
            mapping : 'AT_SUB_ORG_NAME'
        }, {
            name : 'atOrgName',
            mapping : 'AT_ORG_NAME'
        }, {
            name : 'serviceOrCondition',
            mapping : 'SERVICE_OR_CONDITION'
        }, {
            name : 'jionCustMgrGroupNum',
            mapping : 'JION_CUST_MGR_GROUP_NUM'
        }, {
            name : 'groupLeaderNum',
            mapping : 'GROUP_LEADER_NUM'
        }, {
            name : 'mainCustMgrNum',
            mapping : 'MAIN_CUST_MGR_NUM'
        }, {
            name : 'jointlyCustMgrNum',
            mapping : 'JIONTLY_CUST_MGR_NUM'
        }, {
            name : 'profSuportPerson',
            mapping : 'PROF_SUPORT_PERSON'
        } ]);

        // 定义列模型
        var cm = new Ext.grid.ColumnModel( [ rownum, {
            header : 'ID',
            width : 100,
            align : 'center',
            dataIndex : 'id',
            hidden : true
        }, {
            header : '姓名',
            width : 150,
            align : 'center',
            dataIndex : 'name',
            cellclick : true,
            sortable : true
        }, {
            header : '吸存号',
            width : 150,
            align : 'center',
            dataIndex : 'xcNo',
            sortable : true
        }, {
            header : '所在分行',
            width : 150,
            align : 'center',
            dataIndex : 'atSubOrgName',
            sortable : true
        }, {
            header : '所在机构',
            width : 150,
            align : 'center',
            dataIndex : 'atOrgName',
            cellclick : true,
            sortable : true
        }, {
            header : '职务/岗位',
            width : 150,
            align : 'center',
            dataIndex : 'serviceOrCondition',
            sortable : true
        }, {
            header : '参与客户经理小组数',
            width : 150,
            align : 'center',
            dataIndex : 'jionCustMgrGroupNum',
            sortable : true
        }, {
            header : '组长',
            width : 150,
            align : 'center',
            dataIndex : 'groupLeaderNum',
            cellclick : true,
            sortable : true
        }, {
            header : '主办客户经理',
            width : 150,
            align : 'center',
            dataIndex : 'mainCustMgrNum',
            sortable : true
        }, {
            header : '协办客户经理',
            width : 150,
            align : 'center',
            dataIndex : 'jointlyCustMgrNum',
            sortable : true
        }, {
            header : '专业支持人员',
            width : 150,
            align : 'center',
            dataIndex : 'profSuportPerson',
            cellclick : true,
            sortable : true
        } ]);

        /**
         * 数据存储
         */
        var store = new Ext.data.Store( {
            restful : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/mktModelManage.json'// custMgrGroupCount
                }),
            reader : new Ext.data.JsonReader( {
                // successProperty : 'success',
                // idProperty : 'ID',
                // messageProperty : 'message',
                // root : 'json.data',
                // totalProperty : 'json.count'
                totalProperty : 'num',// 记录总数
                root : 'rows'// Json中的列表数据根节点
            }, record)
        });

        var memberData = {
            TOTALCOUNT : 3,
            rows : [ {
                "rownum" : "1",
                "ID" : "1",
                "NAME" : '<u>赵兰</u>',
                "XC_NO" : "10005",
                "AT_SUB_ORG_NAME" : "北京分行",
                "AT_ORG_NAME" : "公司业务部",
                "SERVICE_OR_CONDITION" : "产品经理",
                "JION_CUST_MGR_GROUP_NUM" : "5",
                "GROUP_LEADER_NUM" : "",
                "MAIN_CUST_MGR_NUM" : "",
                "JIONTLY_CUST_MGR_NUM" : "",
                "PROF_SUPORT_PERSON" : "5"
            }, {
                "rownum" : "2",
                "ID" : "2",
                "NAME" : '<u>王金瓯</u>',
                "XC_NO" : "10023",
                "AT_SUB_ORG_NAME" : "海淀分行",
                "AT_ORG_NAME" : "海淀紫竹桥支行",
                "SERVICE_OR_CONDITION" : "行长",
                "JION_CUST_MGR_GROUP_NUM" : "6",
                "GROUP_LEADER_NUM" : "6",
                "MAIN_CUST_MGR_NUM" : "",
                "JIONTLY_CUST_MGR_NUM" : "",
                "PROF_SUPORT_PERSON" : ""
            }, {
                "rownum" : "3",
                "ID" : "3",
                "NAME" : '<u>张善俊</u>',
                "XC_NO" : "10045",
                "AT_SUB_ORG_NAME" : "天津分行",
                "AT_ORG_NAME" : "业务一部",
                "SERVICE_OR_CONDITION" : "客户经理",
                "JION_CUST_MGR_GROUP_NUM" : "12",
                "GROUP_LEADER_NUM" : "",
                "MAIN_CUST_MGR_NUM" : "10",
                "JIONTLY_CUST_MGR_NUM" : "2",
                "PROF_SUPORT_PERSON" : ""
            }, {
                "rownum" : "4",
                "ID" : "4",
                "NAME" : '<u>李显鸥</u>',
                "XC_NO" : "11002",
                "AT_SUB_ORG_NAME" : "天津分行",
                "AT_ORG_NAME" : "业务二部",
                "SERVICE_OR_CONDITION" : "客户经理",
                "JION_CUST_MGR_GROUP_NUM" : "5",
                "GROUP_LEADER_NUM" : "",
                "MAIN_CUST_MGR_NUM" : "5",
                "JIONTLY_CUST_MGR_NUM" : "",
                "PROF_SUPORT_PERSON" : ""
            }, {
                "rownum" : "5",
                "ID" : "5",
                "NAME" : '<u>萧红</u>',
                "XC_NO" : "10034",
                "AT_SUB_ORG_NAME" : "天津分行",
                "AT_ORG_NAME" : "业务三部",
                "SERVICE_OR_CONDITION" : "客户经理",
                "JION_CUST_MGR_GROUP_NUM" : "14",
                "GROUP_LEADER_NUM" : "4",
                "MAIN_CUST_MGR_NUM" : "3",
                "JIONTLY_CUST_MGR_NUM" : "2",
                "PROF_SUPORT_PERSON" : "5"
            } ]
        };
        store.loadData(memberData);

        // 每页显示条数下拉选择框
        var pagesize_combo = new Ext.form.ComboBox( {
            name : 'pagesize',
            triggerAction : 'all',
            mode : 'local',
            store : new Ext.data.ArrayStore( {
                fields : [ 'value', 'text' ],
                data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
                        [ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
            }),
            valueField : 'value',
            displayField : 'text',
            value : '100',
            editable : false,
            width : 85
        });

        // 默认加载数据
        store.load( {
            params : {
                start : 0,
                limit : parseInt(pagesize_combo.getValue())
            }
        });

        // 改变每页显示条数reload数据
        pagesize_combo.on("select", function(comboBox) {
            bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
                params : {
                    start : 0,
                    limit : parseInt(pagesize_combo.getValue())
                }
            });
        });
        // 分页工具栏
        var bbar = new Ext.PagingToolbar( {
            pageSize : parseInt(pagesize_combo.getValue()),
            store : store,
            displayInfo : true,
            displayMsg : '显示{0}条到{1}条,共{2}条',
            emptyMsg : "没有符合条件的记录",
            items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
        });

        // 表格实例
        var grid = new Ext.grid.GridPanel( {
            title : '个人汇总小组信息列表',
            width : 700,
            height : 315,
            frame : true,
            autoScroll : true,
            region : 'center',
            store : store,
            stripeRows : true, // 斑马线
            cm : cm, // 列模型
            // sm : sm,
            // tbar : tbar, // 表格工具栏
            bbar : bbar,// 分页工具栏
            viewConfig : {},
            loadMask : {
                msg : '正在加载表格数据,请稍等...'
            },
            plugins : group
        });

        // //////////////////////////////////////////////////////////////////////////////////////

        var record1 = Ext.data.Record.create( [ {
            name : 'id',
            mapping : 'ID'
        }, {
            name : 'name',
            mapping : 'NAME'
        }, {
            name : 'xcNo',
            mapping : 'XC_NO'
        }, {
            name : 'jionCustMgrGroupName',
            mapping : 'JION_CUST_MGR_GROUP_NAME'
        }, {
            name : 'atSubOrgName',
            mapping : 'AT_SUB_ORG_NAME'
        }, {
            name : 'atOrgName',
            mapping : 'AT_ORG_NAME'
        }, {
            name : 'memberNum',
            mapping : 'MEMEBER_NUM'
        }, {
            name : 'holeRole',
            mapping : 'HOLD_ROLE'
        } ]);

        // 定义列模型
        var cm1 = new Ext.grid.ColumnModel( [ rownum, {
            header : 'ID',
            width : 150,
            align : 'center',
            dataIndex : 'id',
            hidden : true
        }, {
            header : '姓名',
            width : 150,
            align : 'center',
            dataIndex : 'name',
            sortable : true
        }, {
            header : '吸存号',
            width : 150,
            align : 'center',
            dataIndex : 'xcNo',
            sortable : true
        }, {
            header : '参与小组名称',
            width : 150,
            align : 'center',
            dataIndex : 'jionCustMgrGroupName',
            sortable : true
        }, {
            header : '所属分行',
            width : 150,
            align : 'center',
            dataIndex : 'atSubOrgName',
            sortable : true
        }, {
            header : '所属机构',
            width : 150,
            align : 'center',
            dataIndex : 'atOrgName',
            sortable : true
        }, {
            header : '成员人数',
            width : 150,
            align : 'center',
            dataIndex : 'memberNum',
            sortable : true
        }, {
            header : '担任其中角色',
            width : 150,
            align : 'center',
            dataIndex : 'holeRole',
            sortable : true
        } ]);

        /**
         * 数据存储
         */
        var store1 = new Ext.data.Store( {
            restful : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/mktModelManage.json'
            }),
            reader : new Ext.data.JsonReader( {
                // successProperty : 'success',
                // idProperty : 'ID',
                // messageProperty : 'message',
                // root : 'json.data',
                // totalProperty : 'json.count'
                totalProperty : 'num1',// 记录总数
                root : 'rows1'// Json中的列表数据根节点
            }, record1)
        });

        var memberData1 = {
            TOTALCOUNT : 3,
            rows1 : [ {
                "rownum" : "1",
                "ID" : "1",
                "NAME" : '赵兰',
                "XC_NO" : "10005",
                "JION_CUST_MGR_GROUP_NAME" : "中间业务团队",
                "AT_SUB_ORG_NAME" : "北京分行",
                "AT_ORG_NAME" : "公司业务部",
                "MEMEBER_NUM" : "8",
                "HOLD_ROLE" : "专业支持人员"
            }, {
                "rownum" : "2",
                "ID" : "1",
                "NAME" : '赵兰',
                "XC_NO" : "10005",
                "JION_CUST_MGR_GROUP_NAME" : "贷款业务团队",
                "AT_SUB_ORG_NAME" : "北京分行",
                "AT_ORG_NAME" : "公司业务部",
                "MEMEBER_NUM" : "6",
                "HOLD_ROLE" : "专业支持人员"
            }, {
                "rownum" : "3",
                "ID" : "1",
                "NAME" : '赵兰',
                "XC_NO" : "10005",
                "JION_CUST_MGR_GROUP_NAME" : "存款业务团队",
                "AT_SUB_ORG_NAME" : "北京分行",
                "AT_ORG_NAME" : "公司业务部",
                "MEMEBER_NUM" : "3",
                "HOLD_ROLE" : "专业支持人员"
            }, {
                "rownum" : "4",
                "ID" : "1",
                "NAME" : '赵兰',
                "XC_NO" : "10005",
                "JION_CUST_MGR_GROUP_NAME" : "理财业务团队",
                "AT_SUB_ORG_NAME" : "北京分行",
                "AT_ORG_NAME" : "公司业务部",
                "MEMEBER_NUM" : "9",
                "HOLD_ROLE" : "专业支持人员"
            }, {
                "rownum" : "5",
                "ID" : "1",
                "NAME" : '赵兰',
                "XC_NO" : "10005",
                "JION_CUST_MGR_GROUP_NAME" : "综合业务团队",
                "AT_SUB_ORG_NAME" : "北京分行",
                "AT_ORG_NAME" : "公司业务部",
                "MEMEBER_NUM" : "11",
                "HOLD_ROLE" : "专业支持人员"
            } ]
        };
        store1.loadData(memberData1);

        // 每页显示条数下拉选择框
        var pagesize_combo1 = new Ext.form.ComboBox( {
            name : 'pagesize1',
            triggerAction : 'all',
            mode : 'local',
            store : new Ext.data.ArrayStore( {
                fields : [ 'value', 'text' ],
                data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
                        [ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
            }),
            valueField : 'value',
            displayField : 'text',
            value : '100',
            editable : false,
            width : 85
        });

        // 默认加载数据
        store1.load( {
            params : {
                start : 0,
                limit : parseInt(pagesize_combo1.getValue())
            }
        });

        // 改变每页显示条数reload数据
        pagesize_combo1.on("select", function(comboBox) {
            bbar.pageSize = parseInt(pagesize_combo1.getValue()), store1
                    .reload( {
                        params : {
                            start : 0,
                            limit : parseInt(pagesize_combo1.getValue())
                        }
                    });
        });
        // 分页工具栏
        var bbar1 = new Ext.PagingToolbar( {
            pageSize : parseInt(pagesize_combo1.getValue()),
            store : store1,
            displayInfo : true,
            displayMsg : '显示{0}条到{1}条,共{2}条',
            emptyMsg : "没有符合条件的记录",
            items : [ '-', '&nbsp;&nbsp;', pagesize_combo1 ]
        });

        // 表格实例
        var grid1 = new Ext.grid.GridPanel( {
            title : '客户经理小组信息列表',
            width : 800,
            height : 450,
            frame : true,
            autoScroll : true,
            store : store1,
            stripeRows : true, // 斑马线
            cm : cm1, // 列模型
            // sm : sm,
            // tbar : tbar, // 表格工具栏
            bbar : bbar1,// 分页工具栏
            viewConfig : {},
            loadMask : {
                msg : '正在加载表格数据,请稍等...'
            }
        });

        // 定义新增窗口
        var custMgrGroupWindow = new Ext.Window( {
            title : '客户经理小组信息',
            plain : true,
            layout : 'fit',
            width : 800,
            height : 450,
            resizable : true,
            draggable : true,
            closable : true,
            closeAction : 'hide',
            modal : true, // 模态窗口
            loadMask : true,
            maximizable : true,
            collapsible : true,
            titleCollapse : true,
            border : false,
            items : [ grid1 ]
        });

        grid.on('cellclick', function(grid, row, col) {// 获取编辑的行数，从0开始，
                    if (col == 2) {
                        custMgrGroupWindow.show();
                    }
                });
        // 布局模型
        var viewport = new Ext.Viewport( {
            layout : 'fit',
            items : [ {
                layout : 'border',
                items : [ qForm, grid ]
            } ]
        });
    });