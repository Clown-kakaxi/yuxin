/**
 * 财务健康诊断
 */
Ext.onReady(function() {

    function reset(form) {
        var item = form.getForm().items.items;
        for (var i = 0; i < item.length; i++) {
            item[i].setValue('');
        }
    }

    // 遮罩层
    var lm = new Ext.LoadMask(document.body, { // 定义遮屏到body节点上
        msg: '正在加载表格数据,请稍等...',
        removeMask: true
    });
    var riskLevel;
    var saveAllDemand = false;
    var custConfirm = false;
    // 客户风险特性
    var riskCharactStore = new Ext.data.Store({
        restful: true,
        autoLoad: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/lookup.json?name=RISK_CHARACT'
        }),
        reader: new Ext.data.JsonReader({
            root: 'JSON'
        },
        ['key', 'value'])
    });
    riskCharactStore.load();

    // 产品风险等级
    var prodRiskLevelStore = new Ext.data.Store({
        restful: true,
        autoLoad: true,
    	sortInfo : {
			field : 'key',
			direction : 'asc'
		},
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/lookup.json?name=PROD_RISK_LEVEL'
        }),
        reader: new Ext.data.JsonReader({
            root: 'JSON'
        },
        ['key', 'value'])
		,
		listeners:{//码值过滤用监听
			'load':function(store,records){
				store.filterBy(function(record,id){
				if(record != undefined){
					if(record.get('key') != '0'){//要保留的码值key
					return true;
					}else{//要过滤的码值key
						store.removeAt(record.get('key'));
						return false;
					}
				}
				});
			}
		}
    });
    prodRiskLevelStore.load();

    // 理财产品大类
    var licaiProductTypeStore = new Ext.data.Store({
        restful: true,
        autoLoad: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/lookup.json?name=FIN_PRD_TYPE'
        }),
        reader: new Ext.data.JsonReader({
            root: 'JSON'
        },
        ['key', 'value'])
    });
    licaiProductTypeStore.load();

    // 需求类型
    var demandTypeStore = new Ext.data.Store({
        restful: true,
        autoLoad: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/lookup.json?name=DEMAND_TYPE'
        }),
        reader: new Ext.data.JsonReader({
            root: 'JSON'
        },
        ['key', 'value'])
    });
    demandTypeStore.load();

    var qForm = new Ext.form.FormPanel({
        labelWidth: 90,
        region: 'north',
        title: "贵宾理财->顾问式理财服务",
        frame: true,
        // 是否渲染表单面板背景色
        labelAlign: 'middle',
        buttonAlign: 'center',
        height: 100,
        items: [{
            layout: 'column',
            border: false,
            items: [{
                columnWidth: .25,
                layout: 'form',
                labelWidth: 100,
                // 标签宽度
                defaultType: 'textfield',
                border: false,
                items: [{
                    fieldLabel: '核心客户号',
                    name: 'SOURCE_CUST_ID',
                    xtype: 'textfield',
                    // 设置为数字输入框类型
                    labelStyle: 'text-align:right;',
                    anchor: '85%',
                    labelSeparator: ''
                }]
            },{
                columnWidth: .25,
                layout: 'form',
                labelWidth: 100,
                // 标签宽度
                defaultType: 'textfield',
                border: false,
                items: [{
                    fieldLabel: '客户名称',
                    name: 'CUST_NAME',
                    xtype: 'textfield',
                    // 设置为数字输入框类型
                    labelStyle: 'text-align:right;',
                    anchor: '85%',
                    labelSeparator: ''
                }]
            },{
                columnWidth: .25,
                layout: 'form',
                labelWidth: 100,
                // 标签宽度
                defaultType: 'textfield',
                border: false,
                items: [{
                    xtype: 'combo',
                    labelStyle: 'text-align:right;',
                    resizable: true,
                    fieldLabel: '风险等级',
                    name: 'CUST_RISK_CHARACT',
                    hiddenName: 'CUST_RISK_CHARACT',
                    valueField: 'key',
                    displayField: 'value',
                    mode: 'local',
                    typeAhead: true,
                    forceSelection: true,
                    triggerAction: 'all',
                    emptyText: '请选择',
                    selectOnFocus: true,
                    width: '100',
                    anchor: '85%',
                    store: riskCharactStore,
                    labelSeparator: ''
                }]
            },{
                columnWidth: .25,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    labelStyle: 'text-align:right;',
                    resizable: true,
                    fieldLabel: '是否理财规划',
                    name: 'IS_HAVE',
                    hiddenName: 'IS_HAVE',
                    valueField: 'key',
                    displayField: 'value',
                    mode: 'local',
                    typeAhead: true,
                    forceSelection: true,
                    triggerAction: 'all',
                    emptyText: '请选择',
                    selectOnFocus: true,
                    width: '100',
                    anchor: '85%',
                    store: new Ext.data.SimpleStore({
                        fields: ['key', 'value'],
                        data: [['1', '是'], ['2', '否']]
                    }),
                    labelSeparator: ''
                }]
            }]
        }],
        keys: [{
            key: 13,
            fn: function() {
                Ext.getCmp('consultant_search').focus(true);
            },
            scope: this
        }],
        buttons: [{
            text: '查询',
            id: 'consultant_search',
            handler: function() {
                tb_store.load({
                    params: {
                        start: 0,
                        limit: bbar.pageSize
                    }
                });
            }
        },{
            text: '重置',
            handler: function() {
                qForm.form.reset();
            }
        }]
    });
    // 每页显示条数下拉选择框
    var pagesize_combo = new Ext.form.ComboBox({
        name: 'pagesize',
        triggerAction: 'all',
        mode: 'local',
        store: new Ext.data.ArrayStore({
            fields: ['value', 'text'],
            data: [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
        }),
        valueField: 'value',
        displayField: 'text',
        value: '20',
        editable: false,
        width: 85
    });
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
    pagesize_combo.on("select",function(comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
        tb_store.reload({
            params: {
                start: 0,
                limit: parseInt(pagesize_combo.getValue())
            }
        });
    });

    /**
	 * 财务查询信息
	 */
    var tb_sm = new Ext.grid.CheckboxSelectionModel({
        singleSelect: true
    });
    var tb_rownum = new Ext.grid.RowNumberer({
        header: 'No.',
        width: 28
    });
    var tb_cm = new Ext.grid.ColumnModel([tb_rownum, {
        header: '核心客户号',
        dataIndex: 'sourceCustId',
        width: 200,
        sortable: true
    },{
        header: '客户名称',
        dataIndex: 'custZhName',
        width: 200,
        align: 'center',
        sortable: true
    },{
        header: '客户风险等级',
        dataIndex: 'custRiskCharact',
        width: 200,
        sortable: true,
        renderer: function(value) {
            if (value != '') {
                var index = riskCharactStore.find('key', value);
                if (index != -1) return riskCharactStore.getAt(index).get('value');
                else return value;
            }
        }
    },{
    	header: '行内资产',
        dataIndex: 'assetSum',
        width: 200,
        align: 'right',
        sortable: true,
        renderer: function(value) {
            if (value != '') {
                return Ext.util.Format.number(value, '0,000.00');
            }
        }
    },{
        header: '行外资产',
        dataIndex: 'otherBank',
        width: 200,
        align: 'right',
        sortable: true,
        renderer: function(value) {
            if (value != '') {
                return Ext.util.Format.number(value, '0,000.00');
            }
        }
    }]);

    var tb_store = new Ext.data.Store({
        restful: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/ConsultantFinancial.json'
        }),
        reader: new Ext.data.JsonReader({
            totalProperty: 'json.count',
            root: 'json.data'
        },
        [{
            name: 'sourceCustId',
            mapping: 'SOURCE_CUST_ID'
        },{
            name: 'custZhName',
            mapping: 'CUST_ZH_NAME'
        },{
            name: 'custLev',
            mapping: 'CUST_LEV'
        },{
            name: 'custRiskCharact',
            mapping: 'CUST_RISK_CHARACT'
        },{
            name: 'assetSum',
            mapping: 'ASSET_SUM'
        },{
            name: 'otherBank',
            mapping: 'OTHER_BANK'
        },{
            name: 'custId',
            mapping: 'CUST_ID'
        }])
    });
    var tb_store_lm = new Ext.LoadMask(document.body, { // 定义遮屏到body节点上
        store: tb_store,
        msg: '正在加载表格数据,请稍等...',
        removeMask: true
    });
    tb_store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    tb_store.on('beforeload',function() {
        var conditionStr = qForm.getForm().getValues(false);
        this.baseParams = {
            "condition": Ext.encode(conditionStr)
        };
    });

    var bbar = new Ext.PagingToolbar({
        pageSize: number,
        store: tb_store,
        displayInfo: true,
        displayMsg: '显示{0}条到{1}条,共{2}条',
        emptyMsg: "没有符合条件的记录",
        items: ['-', '&nbsp;&nbsp;', pagesize_combo]
    });

    var tb_grid = new Ext.grid.GridPanel({
        frame: true,
        overflow: 'auto',
        autoScroll: true,
        region: 'center',
        // 和VIEWPORT布局模型对应，充当center区域布局
        store: tb_store,
        // 数据存储
        stripeRows: true,
        // 斑马线
        cm: tb_cm,
        // 列模型
        sm: tb_sm,
        // 复选框
        bbar: bbar,
        tbar: [{
            text: '规划向导',
            iconCls: 'shenpiIconCss',
            handler: function() {
        	Ext.getCmp('peizhixuqiu')
			.setDisabled(false);
        	Ext.getCmp('kehuqueren')
			.setDisabled(false);
                Ext.getCmp('sczds_Id').setDisabled(true);
                Ext.getCmp('xiayibu').setDisabled(true);
                form_set.getForm().reset();
                if (nullRecordCheckout(tb_grid) == true) {
                    var record = tb_grid.getSelectionModel().getSelected();
                    form_set.getForm().loadRecord(record);
                    product_cm.getColumnById('prodScale').editor = new Ext.form.TextField({
                        //regex:/^[+-]?\d*\.?\d{1,2}$/
                        //maskRe : '-'
                        regex: /^\d*[.]?(\d{0,2})?$/
                    });
                    // form_set_win.maximize();
                    riskLevel = record.get('custRiskCharact');
                    Ext.getCmp('addTarget').show();
                    Ext.getCmp('deleteTarget').show();
                    Ext.getCmp('addProd').show();
                    Ext.getCmp('deleteProd').show();
                    Ext.getCmp('saveProd').show();
                    Ext.getCmp('saveAll').show();
                    Ext.getCmp('planRiskLevS').hide();
                    form_set_win.show();
                }
            }
        },
        {
            text: '查看详情',
            iconCls: 'dailyDetailIconCss',
            handler: function() {
                Ext.getCmp('sczds_Id').enable();
                custConfirm = false;
                if (nullRecordCheckout(tb_grid) == true) {
                    var record = tb_grid.getSelectionModel().getSelected();
                    next_forms.getForm().loadRecord(record);
                    riskLevel = record.get('custRiskCharact');
                    var sum2 = Ext.getCmp('assetSum').getValue() + Ext.getCmp('otherSum').getValue();
                    // Ext.getCmp('prodScale').readOnly=true;
                    product_cm.getColumnById('prodScale').editor = new Ext.form.NumberField({
                        decimalPrecision: 2,
                        allowNegative: false,
                        readOnly: true
                    });
                    Ext.getCmp('sum2').setValue(sum2);
                    Ext.getCmp('addTarget').hide();
                    Ext.getCmp('deleteTarget').hide();
                    Ext.getCmp('addProd').hide();
                    Ext.getCmp('deleteProd').hide();
                    Ext.getCmp('saveProd').hide();
                    Ext.getCmp('saveAll').hide();
                    Ext.getCmp('planRiskLevS').show();
                    Ext.getCmp('custIdP').setValue(record.get('custId'));
                    saveAllDemand = true;
                    Ext.Ajax.request({
                        url: basepath + '/ConsultantFinancial!findDemand.json',
                        method: 'GET',
                        params: {
                            custId: record.get('custId')
                        },
                        failure: function(form, action) {
                            Ext.MessageBox.alert('顾问式理财服务', '请先为该客户进行理财规划！');
                        },
                        success: function(response) {
                            var respText = Ext.util.JSON.decode(response.responseText);
                            Ext.getCmp('S_EXTRA_PERFORMACE').setValue(respText.json.EXTRA_PERFORMACE);
                            Ext.getCmp('S_INVESTMENT').setValue(respText.json.INVESTMENT);
                            Ext.getCmp('S_PENSION').setValue(respText.json.PENSION);
                            Ext.getCmp('S_RESERVE').setValue(respText.json.RESERVE);
                            Ext.getCmp('S_LIQUIDITY').setValue(respText.json.LIQUIDITY);
                            Ext.getCmp('S_PROTECTION').setValue(respText.json.PROTECTION);
                            Ext.getCmp('planRiskLevS').setValue(respText.json.PLAN_RISK_LEV);

                            title_store.load({
                                params: {
                                    DEMAND_ID: respText.json.DEMAND_ID
                                },
                                callback: function() {
                                    var scale = 0;
                                    title_store.each(function(item) {
                                        if (item.data.targetScale != undefined) scale += item.data.targetScale;
                                        Ext.getCmp('sum1').setValue(scale);
                                    });
                                }
                            });
                            form_set_next_win.show();
                        }
                    });
                }
            }
        }]
    });

    tb_grid.on("celldblclick",
    function() {
        Ext.getCmp('sczds_Id').enable();
        custConfirm = false;
        if (nullRecordCheckout(tb_grid) == true) {
            var record = tb_grid.getSelectionModel().getSelected();
            next_forms.getForm().loadRecord(record);
            riskLevel = record.get('custRiskCharact');
            var sum2 = Ext.getCmp('assetSum').getValue() + Ext.getCmp('otherSum').getValue();
            // Ext.getCmp('prodScale').readOnly=true;
            product_cm.getColumnById('prodScale').editor = new Ext.form.NumberField({
                decimalPrecision: 2,
                allowNegative: false,
                readOnly: true
            });
            Ext.getCmp('sum2').setValue(sum2);
            Ext.getCmp('addTarget').hide();
            Ext.getCmp('deleteTarget').hide();
            Ext.getCmp('addProd').hide();
            Ext.getCmp('deleteProd').hide();
            Ext.getCmp('saveProd').hide();
            Ext.getCmp('saveAll').hide();
            Ext.getCmp('planRiskLevS').show();
            Ext.getCmp('custIdP').setValue(record.get('custId'));
            saveAllDemand = true;
            Ext.Ajax.request({
                url: basepath + '/ConsultantFinancial!findDemand.json',
                method: 'GET',
                params: {
                    custId: record.get('custId')
                },
                failure: function(form, action) {
                    Ext.MessageBox.alert('顾问式理财服务', '请先为该客户进行理财规划！');
                },
                success: function(response) {
                    var respText = Ext.util.JSON.decode(response.responseText);
                    Ext.getCmp('S_EXTRA_PERFORMACE').setValue(respText.json.EXTRA_PERFORMACE);
                    Ext.getCmp('S_INVESTMENT').setValue(respText.json.INVESTMENT);
                    Ext.getCmp('S_PENSION').setValue(respText.json.PENSION);
                    Ext.getCmp('S_RESERVE').setValue(respText.json.RESERVE);
                    Ext.getCmp('S_LIQUIDITY').setValue(respText.json.LIQUIDITY);
                    Ext.getCmp('S_PROTECTION').setValue(respText.json.PROTECTION);
                    Ext.getCmp('planRiskLevS').setValue(respText.json.PLAN_RISK_LEV);

                    title_store.load({
                        params: {
                            DEMAND_ID: respText.json.DEMAND_ID
                        },
                        callback: function() {
                            var scale = 0;
                            title_store.each(function(item) {
                                if (item.data.targetScale != undefined) scale += item.data.targetScale;
                                Ext.getCmp('sum1').setValue(scale);
                            });
                        }
                    });
                    form_set_next_win.show();
                }
            });
        }
    });

    var form_set_1 = new Ext.form.FieldSet({
        title: '客户信息',
        height: 80,
        layout: 'column',
        labelAlign: 'right',
        items: [{
            columnWidth: 0.3,
            layout: 'form',
            items: [{
                fieldLabel: '客户名称',
                name: 'custZhName',
                xtype: 'textfield',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            },
            {
                fieldLabel: '行内资产',
                name: 'assetSum',
                xtype: 'textfield',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            }]
        },
        {
            columnWidth: 0.3,
            layout: 'form',
            items: [{
                fieldLabel: '核心客户号',
                name: 'sourceCustId',
                xtype: 'textfield',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            },{
                fieldLabel: '行外资产',
                name: 'otherBank',
                xtype: 'textfield',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            }]
        },{
            columnWidth: 0.3,
            layout: 'form',
            items: [{
                fieldLabel: '风险等级',
                typeAhead: true,
                triggerAction: 'all',
                lazyRender: true,
                listClass: 'x-combo-list-small',
                mode: 'local',
                name: 'custRiskCharact',
                xtype: 'combo',
                // 设置为数字输入框类型
                store: riskCharactStore,
                valueField: 'key',
                displayField: 'value',
                editable: false,
                labelStyle: 'text-align:right;',
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            }]
        }]
    });

    var form_set_2 = new Ext.form.FieldSet({
        title: '配置需求',
        height: 300,
        labelAlign: 'right',
        labelSeparator: ':',
        id:'peizhixuqiu',
        disabled : true,
        labelWidth: 160,
        items: [{
            fieldLabel: 'CRM客户号',
            name: 'custId',
            id: 'C_CUST_ID',
            xtype: 'hidden',
            anchor: '90%',
            height: 0,
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>投机',
            id: 'C_EXTRA_PERFORMACE',
            name: 'EXTRA_PERFORMACE',
            height: 38,
            xtype: 'textarea',
            anchor: '90%',
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>投资',
            id: 'C_INVESTMENT',
            name: 'INVESTMENT',
            xtype: 'textarea',
            anchor: '90%',
            height: 38,
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>退休金',
            id: 'C_PENSION',
            name: 'PENSION',
            xtype: 'textarea',
            anchor: '90%',
            height: 38,
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>储备',
            id: 'C_RESERVE',
            name: 'RESERVE',
            xtype: 'textarea',
            anchor: '90%',
            height: 38,
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>流动资金',
            id: 'C_LIQUIDITY',
            name: 'LIQUIDITY',
            xtype: 'textarea',
            anchor: '90%',
            height: 38,
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>保护',
            name: 'PROTECTION',
            id: 'C_PROTECTION',
            xtype: 'textarea',
            anchor: '90%',
            height: 38,
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        }]
    });

    var form_set = new Ext.form.FormPanel({
        buttonAlign: 'right',
        frame: true,
        layout: 'form',
        items: [form_set_1, form_set_2],
        buttons: [{
            text: '取消',
            handler: function() {
                form_set.form.reset();
                form_set_win.hide();
            }
        },{
            text: '客户确认',
            id:'kehuqueren',
            disabled : true,
            handler: function() {
        	Ext.getCmp('kehuqueren').setDisabled(true);
        		if (!form_set.getForm().isValid()) {
                    Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                    Ext.getCmp('kehuqueren').setDisabled(false);
                    return false;
                } else {
                    Ext.MessageBox.confirm("顾问式理财服务", "客户是否已经确认？",
                    function(a) {
                        if (a == 'yes') {
                            custConfirm = true;
                            Ext.getCmp('xiayibu').setDisabled(false);
                            Ext.getCmp('peizhixuqiu').setDisabled(true);
//                        	form_set.doLayout(false);
                        } else{
                        	Ext.getCmp('kehuqueren').setDisabled(false);
                        	custConfirm = false;
                        } 
                    });
                }
            }
        },{
            text: '下一步',
            id:'xiayibu',     //按钮至灰，修改人兰超 2012-07-29
            disabled : true,
            handler: function() {
                if (!form_set.getForm().isValid()) {
                    Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                    return false;
                } else if (!custConfirm) {
                    Ext.MessageBox.alert('顾问式理财服务', '请先进行客户确认！');
                } else {
                    title_store.removeAll();
                    saveAllDemand = false;
                    var record = tb_grid.getSelectionModel().getSelected();
                    reset(next_forms);
                    //next_forms.getForm().reset();
                    next_forms.getForm().loadRecord(record);
                    Ext.getCmp('S_EXTRA_PERFORMACE').setValue(Ext.getCmp('C_EXTRA_PERFORMACE').getValue());
                    Ext.getCmp('S_INVESTMENT').setValue(Ext.getCmp('C_INVESTMENT').getValue());
                    Ext.getCmp('S_PENSION').setValue(Ext.getCmp('C_PENSION').getValue());
                    Ext.getCmp('S_RESERVE').setValue(Ext.getCmp('C_RESERVE').getValue());
                    Ext.getCmp('S_LIQUIDITY').setValue(Ext.getCmp('C_LIQUIDITY').getValue());
                    Ext.getCmp('S_PROTECTION').setValue(Ext.getCmp('C_PROTECTION').getValue());
                    var sum2 = Ext.getCmp('assetSum').getValue() + Ext.getCmp('otherSum').getValue();

                    Ext.getCmp('sum2').setValue(sum2);

                    var conditionStr = form_set.getForm().getValues(false);
                    var a = Ext.encode(conditionStr);
                    debugger;
                    Ext.Ajax.request({
                        url: basepath + '/ConsultantFinancial!saveDemand.json',
                        method: 'GET',
                        params: {
                            condition: Ext.encode(conditionStr)
                        },
                        failure: function(form, action) {
                            Ext.MessageBox.alert('顾问式理财服务', '操作失败！');
                        },
                        success: function(response, a, b, c) {
                            var respText = Ext.util.JSON.decode(response.responseText);
                            Ext.getCmp('S_DEMAND_ID').setValue(respText.json.id);
                            debugger;
                            title_store.load({
                                params: {
                                    DEMAND_ID: respText.json.id
                                }
                            });
                            form_set_next_win.show();
                            form_set_win.hide();
                        }
                    });
                }
            }
        }]
    });

    var form_set_win = new Ext.Window({
        resizable: false,
        collapsible: false,
        draggable: true,
        closeAction: 'hide',
        modal: true,
        // 模态窗口
        animCollapse: false,
        border: false,
        loadMask: true,
        closable: true,
        constrain: true,
        width: 800,
        height: 500,
        buttonAlign: "center",
        layout: 'fit',
        title: '规划向导',
        items: [form_set]
    });

    var form_set_next_1 = new Ext.form.FieldSet({
        title: '客户信息',
        height: 120,
        layout: 'column',
        labelAlign: 'right',
        items: [{
            columnWidth: 0.3,
            layout: 'form',
            items: [{
                fieldLabel: '客户名称',
                name: 'custZhName',
                xtype: 'textfield',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                id: 'custZhNameP',
                labelSeparator: ''
            },{
                fieldLabel: '目标总资产',
                id: 'sum2',
                name: 'c1',
                xtype: 'numberfield',
                // 设置为数字输入框类型
                decimalPrecision: '2',
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            },{
                fieldLabel: '方案资产',
                id: 'sum1',
                name: 'sum1',
                xtype: 'numberfield',
                // 设置为数字输入框类型
                anchor: '90%',
                decimalPrecision: '2',
                disabled: true,
                labelSeparator: ''
            }]
        },{
            columnWidth: 0.38,
            layout: 'form',
            items: [{
                fieldLabel: '核心客户号',
                name: 'sourceCustId',
                xtype: 'textfield',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                id: 'sourceCustIdP',
                labelSeparator: ''
            },{
                fieldLabel: '行内资产',
                id: 'assetSum',
                name: 'assetSum',
                xtype: 'numberfield',
                // 设置为数字输入框类型
                anchor: '90%',
                decimalPrecision: '2',
                disabled: true,
                labelSeparator: ''
            },{
                id: 'custIdP',
                name: 'custIdP',
                xtype: 'hidden',
                // 设置为数字输入框类型
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            },{
                fieldLabel: '方案风险等级',
                typeAhead: true,
                triggerAction: 'all',
                lazyRender: true,
                listClass: 'x-combo-list-small',
                mode: 'local',
                name: 'planRiskLev',
                id: 'planRiskLevS',
                xtype: 'combo',
                // 设置为数字输入框类型
                store: riskCharactStore,
                valueField: 'key',
                displayField: 'value',
                editable: false,
                labelStyle: 'text-align:right;',
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            }]
        },{
            columnWidth: 0.3,
            layout: 'form',
            items: [{
                fieldLabel: '客户风险等级',
                typeAhead: true,
                triggerAction: 'all',
                lazyRender: true,
                listClass: 'x-combo-list-small',
                mode: 'local',
                name: 'custRiskCharact',
                xtype: 'combo',
                // 设置为数字输入框类型
                store: riskCharactStore,
                valueField: 'key',
                displayField: 'value',
                editable: false,
                labelStyle: 'text-align:right;',
                anchor: '90%',
                disabled: true,
                labelSeparator: ''
            },{
                fieldLabel: '行外资产',
                id: 'otherSum',
                name: 'otherBank',
                xtype: 'numberfield',
                // 设置为数字输入框类型
                anchor: '90%',
                decimalPrecision: '2',
                disabled: true,
                labelSeparator: ''
            }]
        }]
    });

    var type_form = new Ext.Panel({
        height: 280,
        frame: true,
        labelWidth: 60,
        labelAlign: 'right',
        layout: 'form',
        items: [{
            fieldLabel: '投机',
            id: 'S_EXTRA_PERFORMACE',
            name: 'EXTRA_PERFORMACE',
            xtype: 'textarea',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        },{
            fieldLabel: '投资',
            id: 'S_INVESTMENT',
            name: ' INVESTMENT',
            xtype: 'textarea',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        },{
            fieldLabel: '退休金',
            id: 'S_PENSION',
            name: 'PENSION',
            xtype: 'textarea',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        },{
            fieldLabel: '储备',
            id: 'S_RESERVE',
            name: 'RESERVE',
            xtype: 'textarea',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        },{
            fieldLabel: '流动资金',
            id: 'S_LIQUIDITY',
            name: 'LIQUIDITY',
            xtype: 'textarea',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        },{
            fieldLabel: '保护',
            name: 'PROTECTION',
            id: 'S_PROTECTION',
            xtype: 'textarea',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        },{
            fieldLabel: '',
            name: 'DEMAND_ID',
            id: 'S_DEMAND_ID',
            xtype: 'hidden',
            anchor: '90%',
            disabled: false,
            readOnly: true,
            height: 40,
            labelSeparator: ''
        }]
    });

    var title_sm = new Ext.grid.CheckboxSelectionModel({
        singleSelect: true
    });
    var title_rownum = new Ext.grid.RowNumberer({
        header: 'No.',
        width: 28
    });
    var title_cm = new Ext.grid.ColumnModel([title_rownum, {
        header: '需求类型',
        dataIndex: 'demandType',
        width: 80,
        sortable: true,
        renderer: function(value) {
            if (value != '') {
                var index = demandTypeStore.find('key', value);
                return demandTypeStore.getAt(index).get('value');
            }
        }
    },{
        header: '目标名称',
        dataIndex: 'targetName',
        width: 80,
        sortable: true,
        menuDisabled: true
    },{
        header: '目标规模',
        dataIndex: 'targetScale',
        width: 80,
        sortable: true,
        menuDisabled: true,
        renderer: function(value) {
            if (value != '') {
                return Ext.util.Format.number(value, '0,000.00');
            }
        }
    },{
        header: '目标介绍',
        dataIndex: 'taegetDesc',
        width: 180,
        sortable: true,
        menuDisabled: true
    },{
        header: '目标id',
        dataIndex: 'demandId',
        menuDisabled: true,
        hidden: true
    }]);

    var title_store = new Ext.data.Store({
        restful: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/ConsultantFinancial!findTarget.json'
        }),
        reader: new Ext.data.JsonReader({
            totalProperty: 'json.count',
            root: 'json.data'
        },
        [{
            name: 'demandType',
            mapping: 'DEMAND_TYPE'
        },{
            name: 'targetName',
            mapping: 'TARGET_NAME'
        },{
            name: 'targetScale',
            mapping: 'TARGET_SCALE'
        },{
            name: 'taegetDesc',
            mapping: 'TAEGET_DESC'
        },{
            name: 'demandId',
            mapping: 'DEMAND_ID'
        },{
            name: 'targetId',
            mapping: 'TARGET_ID'
        }])
    });

    var title_grid = new Ext.grid.GridPanel({
        height: 280,
        frame: true,
        overflow: 'auto',
        autoScroll: true,
        store: title_store,
        // 数据存储
        stripeRows: true,
        // 斑马线
        cm: title_cm,
        // 列模型
        sm: title_sm,
        // 复选框
        tbar: [{
            text: '新增目标',
            id: 'addTarget',
            handler: function() {
                save_win.show();
                Ext.getCmp('C_DEMOND_ID').setValue(Ext.getCmp('S_DEMAND_ID').getValue());
            }
        },{
            text: '产品组合',
            handler: function() {

                var records = title_grid.getSelectionModel().getSelections();
                var recordsLen = records.length;
                var record = title_grid.getSelectionModel().getSelected();

                if (recordsLen != 1) {
                    Ext.Msg.alert("系统提示信息", "请选择一条记录！");
                    return;
                } else {
                    products_win.show();
                    var record = title_grid.getSelectionModel().getSelected();
                    product_store.load({
                        params: {
                            targetId: record.data.targetId
                        }
                    });
                }
            }
        },{
            text: '删除',
            id: 'deleteTarget',
            handler: function() {
                var records = title_grid.getSelectionModel().getSelections();
                var recordsLen = records.length;

                if (recordsLen < 1) {
                    Ext.Msg.alert("系统提示信息", "请选择记录后进行删除！");
                    return;
                } else {
                    Ext.MessageBox.confirm("营销活动", "是否确认？",
                    function(a) {
                        if (a == 'yes') {
                            var record = title_grid.getSelectionModel().getSelected();
                            Ext.Ajax.request({
                                url: basepath + '/ConsultantFinancial!deleteTarget.json',
                                method: 'POST',
                                params: {
                                    id: record.data.targetId
                                },
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('顾问式理财服务', '删除目标失败！');
                                },
                                success: function(response) {
                                    title_store.load({
                                        params: {
                                            DEMAND_ID: Ext.getCmp('S_DEMAND_ID').getValue()
                                        }
                                    });
                                    Ext.MessageBox.alert('顾问式理财服务', '删除目标成功！');
                                }
                            });
                        }
                    });
                }
            }
        }],
        loadMask: {
            msg: '正在加载表格数据,请稍等...'
        }
    });

    var save_form = new Ext.form.FormPanel({
        labelWidth: 100,
        // 标签宽度
        frame: true,
        // 是否渲染表单面板背景色
        labelAlign: 'middle',
        // 标签对齐方式
        buttonAlign: 'center',
        height: 200,
        items: [{
            xtype: 'combo',
            labelStyle: 'text-align:right;',
            resizable: true,
            fieldLabel: '<font color=red>*</font>需求类型',
            name: 'C_DEMAND_TYPE',
            id: 'C_DEMAND_TYPE1',
            hiddenName: 'C_DEMAND_TYPE',
            valueField: 'key',
            displayField: 'value',
            mode: 'local',
            typeAhead: true,
            forceSelection: true,
            triggerAction: 'all',
            emptyText: '请选择',
            selectOnFocus: true,
            width: '100',
            anchor: '90%',
            store: demandTypeStore,
            allowBlank: false,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>目标名称',
            name: 'C_TARGET_NAME',
            id: 'C_TARGET_NAME',
            xtype: 'textfield',
            // 设置为数字输入框类型
            labelStyle: 'text-align:right;',
            anchor: '90%',
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '<font color=red>*</font>目标介绍',
            name: 'C_TARGET_DESC',
            id: 'C_TARGET_DESC',
            xtype: 'textarea',
            // 设置为数字输入框类型
            labelStyle: 'text-align:right;',
            anchor: '90%',
            allowBlank: false,
            validator: trim,
            labelSeparator: ''
        },{
            fieldLabel: '需求ID',
            name: 'C_DEMOND_ID',
            id: 'C_DEMOND_ID',
            xtype: 'hidden',
            // 设置为数字输入框类型
            labelStyle: 'text-align:right;',
            anchor: '90%',
            labelSeparator: ''
        }],
        buttons: [{
            text: '保存',
            handler: function() {
                if (Ext.getCmp('C_DEMAND_TYPE1').getValue() == '') {
                    Ext.MessageBox.alert('顾问式理财服务', '需求类型不能为空！');
                    return;
                } else if (Ext.getCmp('C_TARGET_NAME').getValue() == '') {
                    Ext.MessageBox.alert('顾问式理财服务', '目标名称不能为空！');
                    return;
                } else if (Ext.getCmp('C_TARGET_DESC').getValue() == '') {
                    Ext.MessageBox.alert('顾问式理财服务', '目标介绍不能为空！');
                    return;
                } else {
                    var conditionStr = save_form.getForm().getValues(false);
                    Ext.Ajax.request({
                        url: basepath + '/ConsultantFinancial!saveTarget.json',
                        form: save_form.form.id,
                        method: 'POST',
                        params: {
                            condition: Ext.encode(conditionStr)
                        },
                        failure: function(form, action) {
                            Ext.MessageBox.alert('顾问式理财服务', '添加目标失败！');
                        },
                        success: function(response) {
                            save_form.form.reset();
                            save_win.hide();
                            title_store.load({
                                params: {
                                    DEMAND_ID: Ext.getCmp('S_DEMAND_ID').getValue()
                                }
                            });
                            Ext.MessageBox.alert('顾问式理财服务', '添加目标成功！');
                        }
                    });
                }
            }
        },{
            text: '重置',
            handler: function() {
                save_form.form.reset();
            }
        },{
            text: '返回',
            handler: function() {
                save_form.form.reset();
                save_win.hide();
            }
        }]
    });

    var save_win = new Ext.Window({
        resizable: false,
        collapsible: false,
        draggable: true,
        closeAction: 'hide',
        modal: true,
        // 模态窗口
        animCollapse: false,
        border: false,
        loadMask: true,
        closable: true,
        constrain: true,
        layout: 'fit',
        width: 400,
        height: 260,
        buttonAlign: "center",
        title: '新增目标',
        items: [save_form]
    });
    
    var form_set_next_2 = new Ext.form.FieldSet({
        frame: true,
        height: 330,
        title: '目标配置',
        layout: 'column',
        items: [{
            columnWidth: .35,
            items: [type_form]
        },
        {
            columnWidth: .65,
            items: [title_grid]
        }]
    });

    var next_forms = new Ext.form.FormPanel({
        region: 'center',
        margins: '3 3 3 0',
        autoScroll: true,
        frame: true,
        items: [form_set_next_1, form_set_next_2]
    });

    var form_set_next_win = new Ext.Window({
        resizable: false,
        collapsible: false,
        draggable: false,
        closeAction: 'hide',
        modal: true,
        // 模态窗口
        animCollapse: false,
        border: false,
        loadMask: true,
        closable: true,
        constrain: true,
        //layout : 'fit',
        autoScroll: true,
        layout: 'border',
        width: 850,
        height: 450,
        buttonAlign: "right",
        title: '规划向导',
        items: [next_forms],
        buttons: [{
            text: '保存',
            id: 'saveAll',
            handler: function() {
                var record = tb_grid.getSelectionModel().getSelected();
                debugger;
                Ext.Ajax.request({
                    url: basepath + '/ConsultantFinancial!saveAll.json',
                    method: 'POST',
                    params: {
                        custId: record.data.custId,
                        demandId: Ext.getCmp('S_DEMAND_ID').getValue(),
                        riskLev: record.data.custRiskCharact
                    },
                    failure: function(resp, action, a, b) {
                    	debugger;
                        var respText = Ext.util.JSON.decode(resp.responseText);
                        if (respText.message == 99999) Ext.MessageBox.alert('顾问式理财服务', '当前方案风险等级大于客户风险等级，请修改！');
                        else Ext.MessageBox.alert('顾问式理财服务',
                        //		'方案保存失败！'
                        '当前方案风险等级大于客户风险等级，请修改！');
                    },
                    success: function(response) {
                    	debugger;
                        Ext.MessageBox.alert('顾问式理财服务', '方案保存成功！');
                        saveAllDemand = true;
                        Ext.getCmp('sczds_Id').enable();
                    }
                });
            }
        },{
            text: '生成报告',
            id: 'sczds_Id',
            disabled: true,
            handler: function() {
                print();
            }
        },{
            text: '关闭',
            handler: function() {
                form_set_next_win.hide();
            }
        }]
    });

    var product_store = new Ext.data.Store({
        restful: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/ConsultantFinancial!findTargetProd.json'
        }),
        reader: new Ext.data.JsonReader({
            totalProperty: 'json.count',
            root: 'json.data'
        },
        [{
            name: 'productId' // Json中的属性Key值
        },{
            name: 'prodBusId'
        },{
            name: 'prodName'
        },{
            name: 'riskLevel'
        },{
            name: 'prodScale'
        }])
    });

    var product_sm = new Ext.grid.CheckboxSelectionModel();
    
    var product_rownum = new Ext.grid.RowNumberer({
        header: 'No.',
        width: 28
    });
    
    var product_cm = new Ext.grid.ColumnModel([product_rownum, {
        header: '产品编码',
        dataIndex: 'prodBusId',
        width: 150,
        sortable: true
    },{
        header: '产品名称',
        dataIndex: 'prodName',
        width: 150,
        sortable: true
    },{
        header: '产品风险等级',
        dataIndex: 'riskLevel',
        width: 110,
        // align : 'right',
        sortable: true,
        renderer: function(value) {
            if (value != '') {
                var index = prodRiskLevelStore.find('key', value);
                return prodRiskLevelStore.getAt(index).get('value');
            }
        }
    },{
        header: '<font color=red>*</font>产品规模（元）',
        id: 'prodScale',
        dataIndex: 'prodScale',
        width: 110,
        sortable: true,
        allowBlank: false,
        renderer: function(value) {
            if (value != '') {
                return Ext.util.Format.number(value, '0,000.00');
            }
        }
    }]);
    var product_grid = new Ext.grid.EditorGridPanel({
        height: 260,
        frame: true,
        overflow: 'auto',
        autoScroll: true,
        store: product_store,
        // 数据存储
        stripeRows: true,
        // 斑马线
        cm: product_cm,
        // 列模型
        sm: product_sm,
        // 复选框
        tbar: [{
            text: '添加',
            id: 'addProd',
            handler: function() {
                sub_product_form.form.reset();
                sub_products_win.show();

                var lms = new Ext.LoadMask(Ext.get(sub_products_win.getEl()), { // 定义遮屏到body节点上
                    store: sub_product_store,
                    msg: '正在加载表格数据,请稍等...',
                    removeMask: true
                });

            }
        },{
            text: '移除',
            id: 'deleteProd',
            handler: function() {
                var records = product_grid.getSelectionModel().getSelections();
                product_store.remove(records);
            }
        }],
        loadMask: {
            msg: '正在加载表格数据,请稍等...'
        }
    });

    var products_win = new Ext.Window({
        resizable: false,
        collapsible: false,
        draggable: true,
        closeAction: 'hide',
        modal: true,
        // 模态窗口
        animCollapse: false,
        border: false,
        loadMask: true,
        closable: true,
        constrain: true,
        layout: 'fit',
        width: 600,
        height: 400,
        buttonAlign: "center",
        title: '产品组合',
        items: [product_grid],
        buttons: [{
            text: '保存',
            id: 'saveProd',
            handler: function() {
                var record = title_grid.getSelectionModel().getSelected();
                var prodInfo = new Array();
                var i = 0;
                var flag = true;
                var name = '';
                product_store.each(function(item) {
                    if (item.data.prodScale == undefined || item.data.prodScale == 0) {
                        name += item.data.prodName + ',';
                        flag = false;
                    }
                    prodInfo[i] = record.data.targetId + ":" + item.data.productId + ":" + item.data.prodScale;
                    i++;
                });
                if (product_store.data.length == 0) {
                    Ext.MessageBox.alert('顾问式理财服务', '请至少添加一条产品信息进行保存！');
                } else if (!flag) {
                    Ext.MessageBox.alert('顾问式理财服务', '产品:' + name + '产品规模不能为空！');
                } else {
                    Ext.Ajax.request({
                        url: basepath + '/ConsultantFinancial!saveProd.json',
                        method: 'POST',
                        params: {
                            prodInfo: prodInfo
                        },
                        failure: function(form, action) {
                            Ext.MessageBox.alert('顾问式理财服务', '产品新增失败！');
                        },
                        success: function(response) {
                            title_store.load({
                                params: {
                                    DEMAND_ID: Ext.getCmp('S_DEMAND_ID').getValue()
                                },
                                callback: function() {
                                    var scale = 0;
                                    title_store.each(function(item) {
                                        if (item.data.targetScale != undefined) scale += item.data.targetScale;
                                        Ext.getCmp('sum1').setValue(scale);
                                        Ext.MessageBox.alert('顾问式理财服务', '产品组合保存成功！');
                                        products_win.hide();
                                        sub_product_form.getForm().reset();
                                    });
                                }
                            });
                        }
                    });
                }
            }
        },{
            text: '关闭',
            handler: function() {
                products_win.hide();
            }
        }]
    });
    products_win.on('hide',function() {
        product_store.removeAll();
    });
    var sub_product_form = new Ext.form.FormPanel({
	    labelWidth: 100,
        // 标签宽度
        frame: true,
        // 是否渲染表单面板背景色
        labelAlign: 'right',
        // 标签对齐方式
        // bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
        buttonAlign: 'center',
        height: 100,
        items: [{
            layout: 'column',
            border: false,
            items: [{
                columnWidth: .25,
                layout: 'form',
                items: [{
                    fieldLabel: '产品编号',
                    name: 'PROD_BUS_ID',
                    xtype: 'textfield',
                    // 设置为数字输入框类型
                    width: 110,
                    anchor: '95%',
                    labelSeparator: ''
                }]
            },{
                columnWidth: .25,
                layout: 'form',
                border: false,
                items: [{
                    fieldLabel: '产品名称',
                    name: 'PROD_NAME',
                    xtype: 'textfield',
                    // 设置为数字输入框类型
                    width: 110,
                    anchor: '95%',
                    labelSeparator: ''
                }]
            },{
                columnWidth: .25,
                layout: 'form',
                width: 110,
                items: [{
                    xtype: 'combo',
                    labelStyle: 'text-align:right;',
                    resizable: true,
                    fieldLabel: '产品风险等级',
                    name: 'PROD_RISK',
                    hiddenName: 'PROD_RISK',
                    valueField: 'key',
                    displayField: 'value',
                    mode: 'local',
                    typeAhead: true,
                    forceSelection: true,
                    triggerAction: 'all',
                    emptyText: '请选择',
                    selectOnFocus: true,
                    width: '110',
                    anchor: '95%',
                    store: prodRiskLevelStore,
                    labelSeparator: ''
                }]
            },{
                columnWidth: .25,
                layout: 'form',
                width: 110,
                items: [{
                    xtype: 'combo',
                    labelStyle: 'text-align:right;',
                    resizable: true,
                    fieldLabel: '产品大类',
                    name: 'FIN_PRD',
                    hiddenName: 'FIN_PROD',
                    valueField: 'key',
                    displayField: 'value',
                    mode: 'local',
                    typeAhead: true,
                    forceSelection: true,
                    triggerAction: 'all',
                    emptyText: '请选择',
                    selectOnFocus: true,
                    width: '110',
                    anchor: '95%',
                    store: licaiProductTypeStore,
                    labelSeparator: ''
                }]
            }]
        }],
        keys: [{
            key: 13,
            fn: function() {
                Ext.getCmp('product_search').focus(true);
            },
            scope: this
        }],
        buttons: [{
            text: '查询',
            id: 'product_search',
            handler: function() {
                sub_product_store.load({
                    params: {
                        start: 0,
                        limit: bbar.pageSize
                    }
                });
            }
        },{
            text: '重置',
            handler: function() {
                sub_product_form.form.reset();
            }
        }]
    });

    var sub_product_rownum = new Ext.grid.RowNumberer({
        header: 'No.',
        width: 28
    });
    var sub_product_sm = new Ext.grid.CheckboxSelectionModel();
    
    var sub_product_cm = new Ext.grid.ColumnModel([sub_product_rownum, sub_product_sm, {
        header: '产品编码',
        dataIndex: 'prodBusId',
        width: 170,
        sortable: true
    },{
        header: '产品名称',
        dataIndex: 'prodName',
        width: 200,
        sortable: true
    },{
        header: '产品风险等级',
        dataIndex: 'riskLevel',
        width: 170,
        // align : 'right',
        sortable: true,
        renderer: function(value) {
            if (value != '') {
                var index = prodRiskLevelStore.find('key', value);
                return prodRiskLevelStore.getAt(index).get('value');
            }
        }
    },{
        header: '产品大类',
        dataIndex: 'catlName',
        width: 170,
        sortable: true
    },{
        header: '产品标识',
        dataIndex: 'productId',
        menuDisabled: true,
        hidden: true
    }]);

    var sub_product_store = new Ext.data.Store({
        restful: true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/ConsultantFinancial!findProd.json'
        }),
        reader: new Ext.data.JsonReader({
            totalProperty: 'json.count',
            root: 'json.data'
        },
        [{
            name: 'productId',
            mapping: 'PRODUCT_ID'
        },{
            name: 'prodBusId',
            mapping: 'PROD_BUS_ID'
        },{
            name: 'prodName',
            mapping: 'PROD_NAME'
        },{
            name: 'riskLevel',
            mapping: 'RISK_LEVEL'
        },{
            name: 'catlName',
            mapping: 'CATL_NAME'
        }])
    });
    sub_product_store.load();
    
    sub_product_store.on('beforeload',function() {
        var conditionStr = sub_product_form.getForm().getValues(false);
        this.baseParams = {
            "condition": Ext.encode(conditionStr)
        };
    });

    var sub_product_grid = new Ext.grid.GridPanel({
        height: 210,
        frame: true,
        overflow: 'auto',
        autoScroll: true,
        store: sub_product_store,
        // 数据存储
        stripeRows: true,
        // 斑马线
        cm: sub_product_cm,
        // 列模型
        sm: sub_product_sm
        // , // 复选框
        // // tbar : [ ],
        // loadMask : {
        // msg : '正在加载表格数据,请稍等...'
        // }
    });

    var sub_products_win = new Ext.Window({
        resizable: false,
        collapsible: false,
        draggable: true,
        closeAction: 'hide',
        modal: true,
        // 模态窗口
        animCollapse: false,
        border: false,
        loadMask: true,
        //			closable : false,
        constrain: true,
        layout: 'fit',
        width: 900,
        height: 375,
        buttonAlign: "center",
        title: '产品列表',
        items: [{
            layout: 'form',
            items: [sub_product_form, sub_product_grid]
        }],
        buttons: [{
            text: '确定',
            handler: function() {
                var records = sub_product_grid.getSelectionModel().getSelections();
                var recordsLen = records.length;
                if (recordsLen < 1) {
                    Ext.Msg.alert("系统提示信息", "请选择记录后进行添加！");
                    return;
                } else {
                    sub_products_win.hide();
                    //								sub_products_win.close();
                    var name = '';
                    var r_name = '';
                    
                    for (var i = 0; i < recordsLen; i++) {
                    	var flag = 3;
                        var record = records[i];
                        record.data.prodScale = ''; //清空历史产品规模
                        if (record.get('riskLevel') > riskLevel) {
                            name += "<br/>【"+record.get('prodName')+"】";
                             flag = 1;
                        }
                        for (var j = 0; j < product_store.data.length; j++) {
                            var data = product_store.data.items[j].data;
                            var productId = data.productId;
                            if (productId == record.get('productId')) {
                                flag = 2;
                                r_name += "<br/>【"+data.prodName+"】";
                            }
                        }

                        if (flag != 2 && flag != 1) product_store.add(record);
                    }
                    var content = '';
                    if (name != '') {
                        content += "" + name + "<br/>风险等级大于客户风险等级";
                        if (r_name != '') {
                            content += "" + r_name + "<br/>已经存在！";
                        }
                        Ext.Msg.alert("系统提示信息", content);
                    } else {
                        if (r_name != '') {
                            content += "" + r_name + "<br/>已经存在！";
                            Ext.MessageBox.alert("系统提示信息", content);
                        }
                    }
                }
                //							var records = new Array();
                //							sub_product_grid.selModel.selectRows(records, true); // 选择行
                sub_product_grid.selModel.clearSelections();
            }
        },{
            text: '取消',
            handler: function() {
                sub_products_win.hide();
            }
        }]
    });

    //		sub_products_win.on('hide', function() {
    //			sub_product_store.load();
    //		});

    /*******************整体显示布局******************/
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [{
			layout:'border',
			items:[qForm,tb_grid]
		}]
	});
    
    /**
     * 输入项校验空格
     */
    function trim(_v) {         
       if( _v != _v.trim()) {
       	return  false;
   	}
       	return true;
    };
    //'trimText' : '输入项项首项尾有空格'
    
    var nullRecordCheckout = function(grid) {
        var _record = grid.getSelectionModel().getSelections();
        if (_record.length != 1) {
            Ext.MessageBox.alert('提醒消息', '请选择要操作的一行记录！');
            return false;
        } else {
            return true;
        }
    };
    // 拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function() {
        tb_grid.setHeight(document.body.scrollHeight - 120);
        tb_grid.setWidth(document.body.scrollWidth);
        tb_grid.getView().refresh();
    });

    function print() {

        var cust_id = Ext.getCmp('custIdP').getValue();
        //新增   修改人兰超 2012-07-17
        if (cust_id == '') {
            cust_id = Ext.getCmp('C_CUST_ID').getValue();
        };
        //新增   修改人兰超 2012-07-17
        var cust_code = Ext.getCmp('sourceCustIdP').getValue();
        var param = '&cust_id=' + cust_id + '&cust_code=' + cust_code;
        var win = new Ext.Window({
            title: '客户敏感信息,导出有风险',
            layout: 'fit',
            width: 700,
            height: 450,
            animateTarget: Ext.getBody(),
            buttonAlign: 'center',
            resizable: false,
            collapsible: false,
            draggable: true,
            closeAction: 'hide',
            modal: true,
            // 模态窗口
            animCollapse: false,
            border: false,
            loadMask: true,
            closable: true,
            constrain: true,
            items: [{
                header: false,
                html: '<iframe src="' + basepath + '/reportJsp/print/showReport.jsp?raq=/consultantFinancial.raq' + param + '\" frameborder="0" scrolling="yes" id="setframe" name="setframe" width="100%" height="100%"></iframe>',
                border: false
            }]
        });
        win.show();
    }
});