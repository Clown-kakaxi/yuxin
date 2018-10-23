Ext.onReady(function() {
    // 客户状态
        var statusStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '正常户' ], [ '2', '已销户' ], [ '3', '久悬户' ] ]
        });
        // 存款规模
        var scaleStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '1', '大于下限规模小于上限规模' ], [ '2', '大于下限规模小于等于上限规模' ],
                    [ '3', '大于等于下限规模小于上限规模' ], [ '4', '大于等于下限规模小于等于上限规模' ] ]
        });

        /** ******************************************************* */

        var qForm = new Ext.form.FormPanel( {
            id : "qfrom",
            labelWidth : 90, // 标签宽度
            title : "客户管理->客户信息检索->存款业务信息检索->存款规模检索",
             frame : true, //是否渲染表单面板背景色
            labelAlign : 'middle', // 标签对齐方式
            buttonAlign : 'center',
            layout : 'column',
            border : false,
            items : [ {
                columnWidth : .25,
                layout : 'form',
                border : false,
                labelWidth : 120,
                items : [ {
                    name : 'startDate',
                    fieldLabel : '数据日期',
                    xtype : 'datefield',
                    value : '',
                    labelSeparator : ':<br/><br/>',
                    format : 'Y-m-d',
                    allowBlank : false,
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
                }, {
                    xtype : 'combo',
                    store : statusStore,
                    fieldLabel : '客户状态',
                    id : 'customerStatus',
                    value : '',
                    hiddenName : 'cust_type',
                    triggerAction : 'all',
                    labelSeparator : ':<br/><br/>',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    allowBlank : false,
                    mode : 'local',
                    emptyText : '请选择',
                    mode : 'local',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
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
                    anchor : '90%',
                    checkBox : true
                // 复选标志
                        }) ]
            }, {
                layout : 'form',
                columnWidth : .25,
                labelWidth : 120,
                items : [ {
                    xtype : 'numberfield',
                    name : 'upperLimit',
                    fieldLabel : '存款余额上限<font color=red>(万元)</font>',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
                }, {
                    xtype : 'numberfield',
                    name : 'lowerLimit',
                    fieldLabel : '存款余额下限<font color=red>(万元)</font>',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
                }, {
                    xtype : 'combo',
                    store : scaleStore,
                    fieldLabel : '存款余额规模',
                    name : 'acctType',
                    id : 'depositScale',
                    // hidden:true,
                    hiddenName : 'acct_type',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
                }

                ]
            }, {
                layout : 'form',
                columnWidth : .25,
                labelWidth : 120,
                items : [ {
                    xtype : 'numberfield',
                    name : 'upperLimitDay',
                    fieldLabel : '存款日均上限<font color=red>(万元)</font>',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
                }, {
                    xtype : 'numberfield',
                    name : 'lowerLimitDay',
                    fieldLabel : '存款日均下限<font color=red>(万元)</font>',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
                }, {
                    xtype : 'combo',
                    store : scaleStore,
                    fieldLabel : '存款日均规模',
                    name : 'custType',
                    id : 'depositDayScale',
                    hiddenName : 'cust_type',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',

                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '90%'
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
            var upperLimit = qForm.getForm().findField("upperLimit").getValue(); // 余额上限
            var lowerLimit = qForm.getForm().findField("lowerLimit").getValue(); // 余额下限
            var depositScale = Ext.getCmp("depositScale").getValue(); // 余额规模

            var upperLimitDay = qForm.getForm().findField("upperLimitDay")
                    .getValue(); // 日均上限
            var lowerLimitDay = qForm.getForm().findField("lowerLimitDay")
                    .getValue(); // 日均下限
            var depositDayScale = Ext.getCmp("depositDayScale").getValue(); // 日均规模
            var org_diString = Ext.getCmp("CUST_ORG").getValue();// 机构

            var bDate = Ext.util.Format.date(start, 'Y-m-d');

            if (start == '')
                bDate = '2012-08-26';
            if (customerStatus == '')
                customerStatus = 1;
            if (org_diString == '')
                org_diString = '211111';

            if (upperLimit != '' && lowerLimit != '') {
                if (upperLimit < lowerLimit) {
                    alert('存款余额上限必须大于等于下限！');
                    return;
                }
            }
            if (upperLimitDay != '' && lowerLimitDay != '') {
                if (upperLimitDay < lowerLimitDay) {
                    alert('存款日均上限必须大于等于下限！');
                    return;
                }
            }
            var searchType = '0,11,21,31';
            if (lowerLimit > 60000)
                searchType = 31;

            var winWidth = screen.width - 10;
            var winHeight = screen.height - 60;
            var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
            winFeatures += "top=0,left=0,height=" + winHeight + ",width="
                    + winWidth;
            if (customerStatus == 1) {
                var url = basepath
                        + '/reportJsp/showReport.jsp?raq=/A1.raq&etldate='
                        + bDate + '&uid=' + __units + '&org_id=' + org_diString
                        + '&cust_state=0';
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
            items : [qForm ]

            });

    });