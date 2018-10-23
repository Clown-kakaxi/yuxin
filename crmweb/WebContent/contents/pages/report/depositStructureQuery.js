Ext.onReady(function() {
    var custStateStore  = new Ext.data.Store({
		restful:true,
		autoLoad:true,
		proxy:new Ext.data.HttpProxy({
			url: basepath+'/lookup.json?name=ABC0100020'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

    // 存款余额
        var depositStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '>0', '大于0' ], [ '=0', '等于0' ] ]
        });

        // 存款日均
        var depositAverageStore = new Ext.data.ArrayStore( {
            fields : [ 'key', 'value' ],
            data : [ [ '>0', '大于0' ], [ '=0', '等于0' ] ]
        });

        /** ******************************************************* */
        var qForm = new Ext.form.FormPanel( {
            id : "qfrom",
            labelWidth : 90, // 标签宽度
            frame : true, // 是否渲染表单面板背景色
            title : "客户管理->客户信息查询->存款业务信息检索->客户存款结构检索",
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
                    name : 'etldate',
                    fieldLabel : '数据日期<font color="red">*</font>',
                    xtype : 'datefield',
                    value : ' ',
                    allowBlank : false,
                    format : 'Y-m-d',
                    labelStyle : 'text-align:right;',
                    anchor : '99%'
                }, {
                    xtype : 'combo',
                    store:custStateStore,
					displayField:'value',
					valueField:'key',
					forceSelection : true,//值为true时将限定选中的值为列表中的值，值为false则允许用户将任意文本设置到字段（默认为 false）。
                    fieldLabel : '客户状态',
                    hiddenName : 'cust_state',
                    id : 'custState',
                    triggerAction : 'all',
                    valueField : 'key',
                    displayField : 'value',
                    editable : false,
                    mode : 'local',
                    allowBlank : false,
                    value : '',
                    emptyText : '请选择',
                    mode : 'local',
                    labelStyle : 'text-align:right;',
                    // readOnly : true,
                    anchor : '99%'
                } ]
            }, {
                layout : 'form',
                columnWidth : .25,
                labelWidth : 120,
                items : [ {
                    xtype : 'combo',
                    store : depositStore,
                    fieldLabel : '存款余额',
                    id : 'depositBalance',
                    hiddenName : 'deposit_balance',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '99%'
                }, {
                    xtype : 'combo',
                    store : depositAverageStore,
                    fieldLabel : '存款日均',
                    id : 'depositAverage',
                    hiddenName : 'deposit_average',
                    valueField : 'key',
                    displayField : 'value',
                    triggerAction : 'all',
                    mode : 'local',
                    editable : false,
                    emptyText : '请选择',
                    labelStyle : 'text-align:right;',
                    anchor : '99%'
                } ]
            }, {
                layout : 'form',
                columnWidth : .25,
                labelWidth : 120,
                items : [ new Com.yucheng.bcrm.common.OrgField( {
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
                    anchor : '99%',
                    checkBox : true
                // 复选标志
                        }) ]
            } ]

            ,
            buttonAlign : 'center',
            buttons : [ {
                text : '查询',
                handler : function() {

                    if (!qForm.getForm().isValid()) {
                        Ext.Msg.alert("提醒", "请填写必填项");
                        return false;
                    }
                    select();
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
            var start = qForm.getForm().findField('etldate').getValue();
            var customerStatus = Ext.getCmp("custState").getValue();
            var depositBacustomerStatuslance = Ext.getCmp("depositBalance").getValue();
            var depositAverage = Ext.getCmp("depositAverage").getValue();
            var org_diString = Ext.getCmp("CUST_ORG").getValue();// 机构

            var bDate = Ext.util.Format.date(start, 'Y-m-d');
//            if (start == '')
//                bDate = '2012-08-26';
            if (customerStatus == '')
                customerStatus = '00';
            if (org_diString == '')
                org_diString = '211111';

            var winWidth = screen.width - 10;
            var winHeight = screen.height - 60;
            var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
            winFeatures += "top=0,left=0,height=" + winHeight + ",width="
                    + winWidth;
          if(depositBacustomerStatuslance == '' || typeof depositBacustomerStatuslance == "undefined" ) 
        	  	depositBacustomerStatuslance='>=0';
          if(depositAverage == '' || typeof depositAverage == "undefined" ) 
        	  depositAverage='>=0';
          
          var url = basepath
                        + '/reportJsp/showReport.jsp?raq=/A1.raq&etldate='
                        + bDate + '&uid=' + __units + '&org_id=' + org_diString
                        + '&cust_state='+customerStatus+'&bal='+depositBacustomerStatuslance+'&avg_bal='+depositAverage;
            var winOpen = window.open(url, 'chat' + new Date().getTime(),
                    winFeatures);
        }

        // 布局模型
        var viewport = new Ext.Viewport( {
        	layout:'fit',
            items : [ qForm]

            });

    });