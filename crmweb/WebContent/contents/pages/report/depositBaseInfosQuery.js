Ext.onReady(function() {
    // 客户状态
        var statusStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '正常户' ], [ '2', '已销户' ], [ '3', '久悬户' ] ]
        });

        // 开户性质
        var openNatureStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '基本户' ], [ '2', '一般结算户' ], [ '3', '专用存款户' ],
                    [ '4', '临时存款户' ] ]
        });
        // 组织类别
        var organizationStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '即独资公司' ], [ '2', '合伙组织' ], [ '3', '股份合作' ],
                    [ '4', '无限责任公司' ], [ '5', '有限责任公司' ],
                    [ '6', '有限责任公司（联营）' ], [ '7', '股份有限公司（未上市）' ],
                    [ '8', '股份有限公司（已上市）' ], [ '9', '个人合伙' ], [ '10', '其他' ] ]
        });
        // 所有制
        var owerShipStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '国有控股' ], [ '2', '集体控股' ], [ '3', '私人控股' ],
                    [ '4', '港澳台控股' ], [ '5', '外商控股' ] ]
        });
        // 行业门类
        var hyStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '机构组织' ], [ '2', '农林牧渔' ], [ '3', '医药卫生' ],
                    [ '4', '建筑建材' ], [ '5', '信息产业' ] ]
        });
        // 行业大类
        var hyBigStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '其他电信服务' ], [ '2', '移动电信服务' ], [ '3', '互联网信息服务' ],
                    [ '4', '有线广播电视传输服务' ], [ '5', '计算机服务业' ] ]
        });
        // 行业中类
        var hyMiddleStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '证券业' ], [ '2', '证券市场管理' ], [ '3', '证券投资' ],
                    [ '4', '保险业' ], [ '5', '保险辅助服务' ] ]
        });
        // 客户规模
        var customerScaleStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '大型企业' ], [ '2', '中型企业' ], [ '3', '小型企业' ],
                    [ '4', '微型企业' ] ]
        });

        /** ******************************************************* */

        var qForm = new Ext.form.FormPanel( {
            id : "qfrom",
            labelWidth : 90, // 标签宽度
            title : "客户管理->客户信息检索->存款业务信息检索->基础信息检索",
             frame : true, //是否渲染表单面板背景色
            labelAlign : 'right', // 标签对齐方式
            buttonAlign : 'center',
            layout : 'column',
            border : false,
            items : [ {
                columnWidth : .25,
                layout : 'form',
                border : false,
                items : [ {
                    name : 'startDate',
                    fieldLabel : '数据日期',
                    xtype : 'datefield',
                    value : ' ',
                    format : 'Y-m-d',
                    allowBlank : false,
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }, {
                    xtype : 'combo',
                    store : statusStore,
                    fieldLabel : '客户状态',
                    id : 'customerStatus',
                    hiddenName : 'cust_type',
                    triggerAction : 'all',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    allowBlank : false,
                    value : '',
                    mode : 'local',
                    emptyText : '请选择',
                    mode : 'local',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }, new Com.yucheng.bcrm.common.OrgField( {
                    searchType : 'SUBTREE',/*
                                             * 指定查询机构范围属性
                                             * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
                                             * （所有父、祖机构）ALLORG（所有机构）
                                             */
                    fieldLabel : '所属机构',
                    labelStyle : 'text-align:right;',
                    id : 'CUST_ORG', // 放大镜组件ID，用于在重置清空时获取句柄
                    name : 'CUST_ORG',
                    hiddenName : 'instncode', // 后台获取的参数名称
                    anchor : '95%',
                    checkBox : true
                // 复选标志
                        }), {
                    xtype : 'combo',
                    store : customerScaleStore,
                    fieldLabel : '客户规模',
                    id : 'customerScale',
                    hiddenName : 'cust_type',
                    triggerAction : 'all',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    allowBlank : false,
                    value : '',
                    mode : 'local',
                    emptyText : '请选择',
                    mode : 'local',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }

                ]
            }, {
                layout : 'form',
                columnWidth : .25,
                items : [ {
                    xtype : 'combo',
                    store : hyStore,
                    fieldLabel : '行业门类',
                    id : 'hyType',
                    hiddenName : 'cust_type',
                    triggerAction : 'all',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    mode : 'local',
                    emptyText : '请选择',
                    mode : 'local',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }, {
                    xtype : 'combo',
                    store : hyBigStore,
                    fieldLabel : '行业大类',
                    id : 'hybig',
                    hiddenName : 'cust_type',
                    triggerAction : 'all',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    mode : 'local',
                    emptyText : '请选择',
                    mode : 'local',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }, {
                    xtype : 'combo',
                    store : hyMiddleStore,
                    fieldLabel : '行业中类',
                    name : 'acctType',
                    id : 'hymiddle',
                    // hidden:true,
                    hiddenName : 'acct_type',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }

                ]
            }, {
                layout : 'form',
                columnWidth : .25,
                items : [ {
                    xtype : 'combo',
                    store : owerShipStore,
                    fieldLabel : '所有制',
                    id : 'owerShip',
                    hiddenName : 'cust_type',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }, {
                    xtype : 'combo',
                    store : organizationStore,
                    fieldLabel : '组织类别',
                    id : 'organization',
                    hiddenName : 'cust_type',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                }, {
                    xtype : 'combo',
                    store : openNatureStore,
                    fieldLabel : '开户性质',
                    id : 'openNature',
                    hiddenName : 'cust_type',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '95%'
                } ]
            } ],
            buttonAlign : 'center',
            buttons : [ {
                text : '查询',
                handler : function() {

                    if (!qForm.getForm().isValid()) {
                        Ext.Msg.alert("提醒", "请填写必填项");
                        return false;
                    }
                    select();
                    // report4ShowWindow.show();
                var parameters = qForm.getForm().getValues(false);
            }
            }, {
                text : '重置',
                handler : function() {
                    qForm.getForm().reset();
                    Ext.getCmp("CUST_ORG").setValue('');
                }
            } ]
        });
        function select() {
            var start = qForm.getForm().findField('startDate').getValue(); // 时间
            var customerStatus = Ext.getCmp("customerStatus").getValue(); // 客户状态
            var customerScale = Ext.getCmp("customerScale").getValue(); // 客户规模
            var org_diString = Ext.getCmp("CUST_ORG").getValue();// 机构

            var hyType = Ext.getCmp("hyType").getValue(); // 行业门类
            var hymiddle = Ext.getCmp("hymiddle").getValue(); // 行业中类
            var hybig = Ext.getCmp("hybig").getValue(); // 行业大类
            var owerShip = Ext.getCmp("owerShip").getValue(); // 所有制
            var openNature = Ext.getCmp("openNature").getValue(); // 开户性质
            var organization = Ext.getCmp("organization").getValue(); // 组织类别
            var bDate = Ext.util.Format.date(start, 'Y-m-d');

            if (start == '')
                bDate = '2012-08-26';
            if (customerStatus == '')
                customerStatus = 1;
            if (org_diString == '')
                org_diString = '211111';
            var searchType = '0';
            if (customerScale != '')
                searchType = '0,11,31';

            var winWidth = screen.width - 10;
            var winHeight = screen.height - 60;
            var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
            winFeatures += "top=0,left=0,height=" + winHeight + ",width="
                    + winWidth;
            if (customerStatus == 1) {
                var url = basepath
                        + '/reportJsp/showReport.jsp?raq=/A1.raq&uid='
                        + __units + '&etldate=' + bDate + '&org_id='
                        + org_diString + '&cust_state=0';
            } else if (customerStatus == 2) {
                var url = basepath
                        + '/reportJsp/showReport.jsp?raq=/A1.raq&etldate='
                        + bDate + '&uid=' + __units + '&org_id=' + org_diString
                        + '&cust_state=1';
            } else {
                var url = basepath
                        + '/reportJsp/showReport.jsp?raq=/A1.raq&etldate='
                        + bDate + '&uid=' + __units + '&org_id=' + org_diString
                        + '&cust_state=2';
            }
            var winOpen = window.open(url, 'chat' + new Date().getTime(),
                    winFeatures);
        }
        // 布局模型
        var viewport = new Ext.Viewport( {
        	layout:'fit',
            items : [ qForm ]

            });

    });