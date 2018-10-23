/**
 * @description 顾问式理财服务
 * @author likai
 * @since 2014-07-16
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js' // 产品放大jin
	,'/FusionCharts/FusionCharts.js'
	,'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
Ext.QuickTips.init();//提示信息

var url = basepath+'/consultantFinancialNew.json';//查询路径

//本地数据字典定义
var lookupTypes = [
	'CUST_RISK_CHARACT',	//客户风险等级
	'PROD_RISK_LEVEL',		//产品风险等级
	'DEMAND_TYPE'		//需求类型
];

var fields = [
	{name: 'CUST_ID', text : '客户号', searchField: true},
	{name: 'CUST_NAME', text : '客户名称', searchField: true},
	{name: 'CUST_RISK_CHARACT', text : '风险等级', searchField: true,translateType : 'CUST_RISK_CHARACT'},
	{name: 'ASSET_SUM',text:'行内资产', resutlWidth:200,dataType:'number'},
    {name: 'OTHER_BANK',text:'行外资产', resutlWidth:200,dataType:'number'},
    {name: 'CREATE_DT',text:'创建日期',dataType:'date'},
    {name: 'DEMAND_ID',text:'DEMAND_ID',gridField:false}
];

var tbar =[{
	text:'规划',
	hidden: JsContext.checkGrant('consultantFinancial_plan'),
	iconCls:'detailIconCss',
	handler:function() {
		showCustomerViewByIndex(0);
		editOrViewFn(false);
	}
},{
	text:'详情',
	hidden: JsContext.checkGrant('consultantFinancial_detail'),
	iconCls:'detailIconCss',
	handler:function() {
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择要查看的记录！');
			return false;
		}
		var demandId = getSelectedData().data.DEMAND_ID;
		if(demandId == null || demandId == undefined || demandId == ""){
			Ext.Msg.alert('提示','请先进行规划再查看详情！');
			return false;
		}
		editOrViewFn(true);
		showCustomerViewByIndex(0);
	}
},{
	text:'下载报告',
	hidden: JsContext.checkGrant('consultantFinancial_download'),
	iconCls:'detailIconCss',
	handler:function() {
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择要操作的一行记录！');
			return false;
		}
		var demandId = getSelectedData().data.DEMAND_ID;
		if(demandId == null || demandId == undefined || demandId == ""){
			Ext.Msg.alert('提示','请先进行规划，并生成报告！');
			return false;
		}
		window.open( basepath+'/TempDownload?filename=LCGH_'+demandId+'.pdf','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	}
},
	//导出按钮
	new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('consulFinancial_excel'),
        formPanel : 'searchCondition',
        url :  basepath + '/consultantFinancialNew.json'
    })
];

/**
 * @param {} hidden boolean  true表示查看/false表示编辑
 */
var editOrViewFn = function(hidden){
	form_set.getForm().findField('EXTRA_PERFORMACE').setDisabled(hidden);
	form_set.getForm().findField('INVESTMENT').setDisabled(hidden);
	form_set.getForm().findField('PENSION').setDisabled(hidden);
	form_set.getForm().findField('RESERVE').setDisabled(hidden);
	form_set.getForm().findField('LIQUIDITY').setDisabled(hidden);
	form_set.getForm().findField('PROTECTION').setDisabled(hidden);
	
	Ext.getCmp('kehuqueren').setVisible(!hidden);
	Ext.getCmp('kehuqueren').setDisabled(hidden);
	Ext.getCmp('xiayibu').setDisabled(!hidden);
	
	Ext.getCmp('addTarget').setVisible(!hidden);
	Ext.getCmp('addTargetForm').setVisible(!hidden);
	Ext.getCmp('deleteTargetForm').setVisible(!hidden);
	Ext.getCmp('saveTarget').setVisible(!hidden);
	Ext.getCmp('deleteTarget').setVisible(!hidden);
	
	Ext.getCmp('downloadReport').setDisabled(!hidden);
};


var form_set_1 = new Ext.form.FieldSet({
    title: '客户信息',
    height: 90,
    layout: 'column',
    labelAlign: 'right',
    items: [{
        columnWidth: 0.33,
        layout: 'form',
        items: [
        	{name: 'CUST_ID', xtype: 'textfield',fieldLabel: '客户号',disabled: true,cls:'x-readOnly',anchor: '95%'},
        	{name: 'ASSET_SUM',xtype: 'textfield',fieldLabel: '行内资产',disabled: true,cls:'x-readOnly',anchor: '95%'}
        ]
    },{
        columnWidth: 0.33,
        layout: 'form',
        items: [
        	{name: 'CUST_NAME',xtype: 'textfield',fieldLabel: '客户名称',disabled: true,cls:'x-readOnly',anchor: '95%'},
        	{name: 'OTHER_BANK',xtype: 'textfield',fieldLabel: '行外资产',disabled: true,cls:'x-readOnly', anchor: '95%'}
        ]
    },{
        columnWidth: 0.33,
        layout: 'form',
        items: [
        	{name: 'CUST_RISK_CHARACT',xtype: 'combo',fieldLabel: '风险等级', typeAhead: true,triggerAction: 'all',lazyRender: true,
	            listClass: 'x-combo-list-small',mode: 'local', store: new Ext.data.Store(),valueField: 'key',displayField: 'value',editable: false,
	            labelStyle: 'text-align:right;',disabled: true,cls:'x-readOnly',anchor: '95%'}
        ]
    }]
});

var form_set_2 = new Ext.form.FieldSet({
    title: '配置需求',
    height: 255,
    labelAlign: 'right',
    id:'peizhixuqiu',
    labelWidth: 160,
    items: [
    	{xtype:'textfield',name:'DEMAND_ID',hidden:true},
    	{xtype:'textfield',name:'CREATOR_ID',hidden:true},
    	{xtype:'datefield',name:'CREATE_DT',format:'Y-m-d',hidden:true},
    	{xtype: 'textfield',name: 'EXTRA_PERFORMACE',fieldLabel: '投机<font color=red>*</font>',anchor: '95%',allowBlank: false},
        {xtype: 'textfield',name: 'INVESTMENT',fieldLabel: '投资(长期)<font color=red>*</font>',anchor: '95%',allowBlank: false},
        {xtype: 'textfield',name: 'PENSION',fieldLabel: '退休金<font color=red>*</font>',anchor: '95%',allowBlank: false},
        {xtype: 'textfield',name: 'RESERVE',fieldLabel: '储备(短期)<font color=red>*</font>',anchor: '95%',allowBlank: false},
        {xtype: 'textfield',name: 'LIQUIDITY',fieldLabel: '流动资金<font color=red>*</font>',  anchor: '95%',allowBlank: false},
        {xtype: 'textfield',name: 'PROTECTION',fieldLabel: '保护<font color=red>*</font>',anchor: '95%',allowBlank: false}
     ]
});

/**
 * 
 * @param {} d
 */
var disabledFormSet2 = function(d){
	form_set.getForm().findField('EXTRA_PERFORMACE').setDisabled(d);
	form_set.getForm().findField('INVESTMENT').setDisabled(d);
	form_set.getForm().findField('PENSION').setDisabled(d);
	form_set.getForm().findField('RESERVE').setDisabled(d);
	form_set.getForm().findField('LIQUIDITY').setDisabled(d);
	form_set.getForm().findField('PROTECTION').setDisabled(d);
	
	Ext.getCmp('kehuqueren').setDisabled(d);
	Ext.getCmp('xiayibu').setDisabled(!d);
}

/**
 * 客户需求
 */
var demandStore = new Ext.data.Store({
    restful: true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/custDemand.json'
    }),
    reader: new Ext.data.JsonReader({
        totalProperty: 'json.count',
        root: 'json.data'
    },[
    	{name: 'DEMAND_ID'},
    	{name: 'CUST_ID'},
    	{name: 'EXTRA_PERFORMACE'},
    	{name: 'INVESTMENT'},
    	{name: 'PENSION'},
    	{name: 'RESERVE'},
    	{name: 'LIQUIDITY'},
    	{name: 'PROTECTION'},
    	{name: 'PLAN_RISK_LEV'},
    	{name: 'CREATE_DT'},
    	{name: 'CREATOR_ID'},
    	{name: 'AVAILABLE'}
    ])
});

var form_set = new Ext.form.FormPanel({
    autoHeight:true,
    frame: true,
    buttonAlign:'center',
    layout: 'form',
    items: [form_set_1, form_set_2],
    buttons: [{
        text: '客户确认',
        id:'kehuqueren',
        handler: function() {
    		if (!form_set.getForm().isValid()) {
                Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                return false;
            }
            Ext.MessageBox.confirm("顾问式理财服务", "客户是否已经确认？",function(a) {
                if (a == 'no') {
                	return ;
                } 
                var commintData = translateDataKey(form_set.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
                commintData.custId = form_set.getForm().findField('CUST_ID').getValue();
                commintData.planRiskLev = form_set.getForm().findField('CUST_RISK_CHARACT').getValue();
	            Ext.Ajax.request({
	                url: basepath + '/custDemand.json',
	                method: 'POST',
	                params : commintData,
	                success: function(response) {
	                    Ext.Msg.alert('提示', '操作成功！');
	                    disabledFormSet2(true);
	                    demandStore.load({
							params:{
								custId : getSelectedData().data.CUST_ID
							}
						});
	                },
	                failure: function(){
	                	Ext.Msg.alert('提示', '操作失败！');
	                }
	            });
            });
        }
    },{
        text: '下一步',
        id:'xiayibu', 
        disabled : true,
        handler: function() {
            title_store.removeAll();
            var selectRecord = getSelectedData();
            if(next_forms.getForm().getEl()){
            	next_forms.getForm().getEl().dom.reset();
            }
            next_forms.getForm().loadRecord(selectRecord);
            if(demandStore.getCount()>0){
				var rd = demandStore.getAt(0);
				next_forms.getForm().loadRecord(rd);
			}
			showCustomerViewByTitle('规划向导-目标产品配置');
        }
    },{
    	id : 'cancelButt',
        text: '关闭',
        handler: function() {
        	form_set.getForm().getEl().dom.reset();
        	hideCurrentView();
        }
    }]
});


//
var form_set_next_1 = new Ext.form.FieldSet({
    title: '客户信息',
    height: 90,
    layout: 'column',
    labelAlign: 'right',
    items: [{
        columnWidth: 0.33,
        layout: 'form',
        items: [
        	{name: 'CUST_ID', xtype: 'textfield',fieldLabel: '客户号',disabled: true,cls:'x-readOnly',anchor: '95%'},
        	{name: 'ASSET_SUM',xtype: 'textfield',fieldLabel: '行内资产',disabled: true,cls:'x-readOnly',anchor: '95%'}
        ]
    },{
        columnWidth: 0.33,
        layout: 'form',
        items: [
        	{name: 'CUST_NAME',xtype: 'textfield',fieldLabel: '客户名称',disabled: true,cls:'x-readOnly',anchor: '95%'},
        	{name: 'OTHER_BANK',xtype: 'textfield',fieldLabel: '行外资产',disabled: true,cls:'x-readOnly', anchor: '95%'}
        ]
    },{
        columnWidth: 0.33,
        layout: 'form',
        items: [
        	{name: 'CUST_RISK_CHARACT',xtype: 'combo',fieldLabel: '风险等级', typeAhead: true,triggerAction: 'all',lazyRender: true,
	            listClass: 'x-combo-list-small',mode: 'local', store: new Ext.data.Store(),valueField: 'key',displayField: 'value',editable: false,
	            labelStyle: 'text-align:right;',disabled: true,cls:'x-readOnly',anchor: '95%'}
        ]
    }]
});

var type_form = new Ext.Panel({
    height: 230,
    frame: true,
    labelWidth: 70,
    labelAlign: 'right',
    layout: 'form',
    items: [
    	{xtype:'textfield',name:'DEMAND_ID',hidden:true},
    	{xtype:'textfield',name:'CREATOR_ID',hidden:true},
    	{xtype:'datefield',name:'CREATE_DT',format:'Y-m-d',hidden:true},
    	{xtype: 'textfield',name: 'EXTRA_PERFORMACE',fieldLabel: '投机',anchor: '95%',disabled: true,cls:"x-readOnly"},
        {xtype: 'textfield',name: 'INVESTMENT',fieldLabel: '投资(长期)',anchor: '95%',disabled: true,cls:"x-readOnly"},
        {xtype: 'textfield',name: 'PENSION',fieldLabel: '退休金',anchor: '95%',disabled: true,cls:"x-readOnly"},
        {xtype: 'textfield',name: 'RESERVE',fieldLabel: '储备(短期)',anchor: '95%',disabled: true,cls:"x-readOnly"},
        {xtype: 'textfield',name: 'LIQUIDITY',fieldLabel: '流动资金',  anchor: '95%',disabled: true,cls:"x-readOnly"},
        {xtype: 'textfield',name: 'PROTECTION',fieldLabel: '保护',anchor: '95%',disabled: true,cls:"x-readOnly"}
    ]
});

var save_form = new Ext.form.FormPanel({
    labelWidth: 100,	// 标签宽度
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'middle',	// 标签对齐方式
    buttonAlign: 'center',
    height: 200,
    items: [
    	{xtype: 'combo', name: 'DEMAND_TYPE',hiddenName: 'DEMAND_TYPE',labelStyle: 'text-align:right;',resizable: true,fieldLabel: '<font color=red>*</font>需求类型',
        	valueField: 'key',displayField: 'value',mode: 'local',typeAhead: true,forceSelection: true,triggerAction: 'all',emptyText: '请选择',selectOnFocus: true,anchor: '90%',store: new Ext.data.Store(),allowBlank: false},
        {fieldLabel: '<font color=red>*</font>目标名称',name: 'TARGET_NAME',xtype: 'textfield',labelStyle: 'text-align:right;',anchor: '90%',allowBlank: false},
        {fieldLabel: '<font color=red>*</font>目标介绍',name: 'TAEGET_DESC',xtype: 'textarea',labelStyle: 'text-align:right;',anchor: '90%',allowBlank: false},
        {fieldLabel: '需求ID', name: 'DEMAND_ID',xtype: 'hidden', labelStyle: 'text-align:right;',anchor: '90%'}
    ],
    buttons: [{
        text: '保存',
        handler: function() {
        	if (!save_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '存在漏输入项,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(save_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
            Ext.Ajax.request({
                url: basepath + '/ocrmFFinTarget.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '操作成功！');
                     save_form.form.reset();
                     save_win.hide();
                     title_store.load({
                         params: {
                            DEMAND_ID: commintData.demandId
                         }
                     });
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
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
    modal: true,	// 模态窗口
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


var sub_product_form = new Ext.form.FormPanel({
    labelWidth: 100,
    // 标签宽度
    frame: true,
    // 是否渲染表单面板背景色
    labelAlign: 'right',
    buttonAlign: 'center',
    height: 100,
    items: [
    	{name:'DEMAND_ID',xtype:'textfield',hidden:true},
    	{name:'TARGET_ID',xtype:'textfield',hidden:true},
    	{name:'PROD_RISK',xtype:'textfield',hidden:true},
    	{name:'PROD_NAME',hiddenName:'PROD_ID',xtype:'productChoose',fieldLabel:'<font color=red >*</font>产品名称',anchor:'95%',singleSelect:false
    		,allowBlank:false,riskLevel:'',callback:function(checkedNodes){
    			var temp = '';
    			for(var i=0;i<checkedNodes.length;i++){
					if(i == 0){
						temp = temp + checkedNodes[i].data.RISK_LEVEL;
					}else{
						temp = temp +',' +checkedNodes[i].data.RISK_LEVEL;
					}
				}
				sub_product_form.getForm().findField('PROD_RISK').setValue(temp);
    		}},
    	{name:'CONF_SCALE',xtype:'numberfield',fieldLabel:'<font color=red >*</font>产品规模',anchor:'95%',hidden:true}
    ],
    buttons: [{
        text: '保存',
        handler: function() {
        	if (!sub_product_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '存在漏输入项,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(sub_product_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
            Ext.Ajax.request({
                url: basepath + '/ocrmFFinProdConf.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '操作成功！');
                     sub_products_win.hide();
                     product_store.load({
	                    params: {
	                        targetId: commintData.targetId
	                    }
	                });
	                title_store.load({
                         params: {
                            DEMAND_ID: commintData.demandId
                         }
                     });
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
        }
    }]
});



var sub_products_win = new Ext.Window({
    resizable: false,
    collapsible: false,
    draggable: true,
    closeAction: 'hide',
    modal: true,
    animCollapse: false,
    border: false,
    loadMask: true,
    constrain: true,
    layout: 'fit',
    width: 400,
    height: 200,
    buttonAlign: "center",
    title: '添加产品',
    items: [sub_product_form]
});

var product_store = new Ext.data.Store({
    restful: true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/ocrmFFinProdConf.json'
    }),
    reader: new Ext.data.JsonReader({
        totalProperty: 'json.count',
        root: 'json.data'
    },
    [{
        name: 'CONF_ID' 
    },{
        name: 'PROD_ID'
    },{
        name: 'PROD_NAME'
    },{
        name: 'PROD_RISK'
    },{
        name: 'TARGET_ID' 
    },{
        name: 'DEMAND_ID' 
    },{
        name: 'CONF_SCALE'
    }])
});

var product_sm = new Ext.grid.CheckboxSelectionModel();
var product_rownum = new Ext.grid.RowNumberer({
    header: 'No.',
    width: 35
});
    
var product_cm = new Ext.grid.ColumnModel([
	product_rownum, 
	{header: '产品编码',dataIndex: 'PROD_ID',width: 100,sortable: true},
	{header: '产品名称',dataIndex: 'PROD_NAME',width: 150,sortable: true},
	{header: '产品风险等级',dataIndex: 'PROD_RISK',width: 150,sortable: true,
	    renderer: function(value) {
	    	var val = translateLookupByKey("PROD_RISK_LEVEL",value);
			return val?val:"";
	    }
	},
	{header: '<font color=red>*</font>产品规模（元）',dataIndex: 'CONF_SCALE',width: 150,allowBlank: false,
		editor:new Ext.form.NumberField({allowBlank:false}),
		renderer: function(value) {
		    if (value != '') {
	       	 	return Ext.util.Format.number(value, '0,000.00');
	        }
	    }
	}
]);
    
var product_grid = new Ext.grid.EditorGridPanel({
    height: 260,
    frame: true,
    overflow: 'auto',
    autoScroll: true,
    clicksToEdit:1,
    store: product_store,
    // 数据存储
    stripeRows: true,
    // 斑马线
    cm: product_cm,
    // 列模型
    sm: product_sm,
    // 复选框
    tbar: [{
    	id : 'addTargetForm',
        text: '新增',
        handler: function() {
            sub_product_form.form.reset();
            sub_products_win.show();
            var record = title_grid.getSelectionModel().getSelected();
            sub_product_form.getForm().findField('DEMAND_ID').setValue(record.data.DEMAND_ID);
            sub_product_form.getForm().findField('TARGET_ID').setValue(record.data.TARGET_ID);
        }
    },{
    	id : 'deleteTargetForm',
        text: '删除',
        handler: function() {
            var records = product_grid.getSelectionModel().getSelections();
            var recordsLen = records.length;
            if (recordsLen < 1) {
                Ext.Msg.alert("提示", "请选择记录后进行删除！");
                return;
            } else {
            	var idStr = '';
            	for (var i = 0; i < recordsLen; i++) {
                    var selectRe = records[i];
                    var tempId = selectRe.data.CONF_ID;
                    idStr += tempId;
                    if (i != recordsLen - 1)
                    	idStr += ',';
                }
                Ext.MessageBox.confirm("提示", "是否确认？",function(a) {
                    if (a == 'yes') {
                        var record = title_grid.getSelectionModel().getSelected();
                        Ext.Ajax.request({
                            url: basepath + '/ocrmFFinProdConf!batchDestroy.json',
                            mothed: 'POST',
                            params: {
                                ids: idStr
                            },
                            failure: function(form, action) {
                                Ext.MessageBox.alert('提示', '删除失败！');
                            },
                            success: function(response) {
                                product_store.load({
				                    params: {
				                        targetId:  records[0].data.TARGET_ID
				                    }
				                });
                                Ext.MessageBox.alert('提示', '删除成功！');
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

var products_win = new Ext.Window({
    resizable: false,
    collapsible: false,
    draggable: true,
    closeAction: 'hide',
    modal: true,
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
    	id : 'saveTarget',
    	text: '保存',
    	handler: function() {
    		product_grid.stopEditing();//停止编辑
			var ids = "";
			var vals = "";
			var demandId = "";
			for (var i = 0; i < product_store.getCount(); i++) {
				var temp = product_store.getAt(i);
				ids += ","+temp.data.CONF_ID;
				vals += ","+temp.data.CONF_SCALE;
				demandId = product_store.getAt(i).data.DEMAND_ID;
				targetId = product_store.getAt(i).data.TARGET_ID;
			}
			Ext.Msg.wait('正在保存数据...','提示');
			Ext.Ajax.request({
                url: basepath + '/ocrmFFinProdConf.json',
                method: 'POST',
                params : {
                	targetId: targetId,
                	ids: ids,
                	vals: vals
                },
                success: function(response) {
                    Ext.Msg.alert('提示', '操作成功！');
	                title_store.load({
                         params: {
                            DEMAND_ID: demandId
                         }
                     });
                     products_win.hide();
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
    	}
    },{
        text: '关闭',
        handler: function() {
            products_win.hide();
        }
    }]
});
    
//产品目标表
var title_store = new Ext.data.Store({
    restful: true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/ocrmFFinTarget.json'
    }),
    reader: new Ext.data.JsonReader({
        totalProperty: 'json.count',
        root: 'json.data'
    },
    [{
        name: 'DEMAND_TYPE'
    },{
        name: 'TARGET_NAME'
    },{
        name: 'TARGET_SCALE'
    },{
        name: 'TAEGET_DESC'
    },{
        name: 'DEMAND_ID'
    },{
        name: 'TARGET_ID'
    }])
});
var title_sm = new Ext.grid.CheckboxSelectionModel({
    singleSelect: true
});
var title_rownum = new Ext.grid.RowNumberer({
    header: 'No.',
    width: 35
});
var title_cm = new Ext.grid.ColumnModel([title_rownum, 
	{ header: '需求类型',dataIndex: 'DEMAND_TYPE',width: 80,sortable: true
		,renderer: function(value) {
        	var val = translateLookupByKey("DEMAND_TYPE",value);
			return val?val:"";
		}
	},
    {header: '目标名称',dataIndex: 'TARGET_NAME',sortable: true,menuDisabled: true},
    {header: '目标规模',dataIndex: 'TARGET_SCALE',menuDisabled: true,
	    renderer: function(value) {
	        if (value != '') {
	            return Ext.util.Format.number(value, '0,000.00');
	        }
	    }
	},
	{header: '目标介绍',dataIndex: 'TAEGET_DESC',width: 180,sortable: true},
	{header: '目标id',dataIndex: 'TARGET_ID',menuDisabled: true,hidden: true}
]);


var title_grid = new Ext.grid.GridPanel({
    height: 230,
    frame: true,
    overflow: 'auto',
    autoScroll: true,
    store: title_store,
    stripeRows: true,
    cm: title_cm,
    sm: title_sm,
    tbar: [{
        text: '新增目标',
        id: 'addTarget',
        handler: function() {
            save_win.show();
            save_form.getForm().findField('DEMAND_ID').setValue(next_forms.getForm().findField('DEMAND_ID').getValue());
        }
    },{
        text: '产品组合',
        handler: function() {
            var records = title_grid.getSelectionModel().getSelections();
            var recordsLen = records.length;
            var record = title_grid.getSelectionModel().getSelected();

            if (recordsLen != 1) {
                Ext.Msg.alert("提示", "请选择一条记录！");
                return;
            } else {
                products_win.show();
                var record = title_grid.getSelectionModel().getSelected();
                product_store.load({
                    params: {
                        targetId: record.data.TARGET_ID
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
                Ext.Msg.alert("提示", "请选择记录后进行删除！");
                return;
            } else {
            	var idStr = '';
            	for (var i = 0; i < recordsLen; i++) {
                    var selectRe = records[i];
                    var tempId = selectRe.data.TARGET_ID;
                    idStr += tempId;
                    if (i != recordsLen - 1)
                    	idStr += ',';
                }
                Ext.MessageBox.confirm("提示", "是否确认？",function(a) {
                    if (a == 'yes') {
                        var record = title_grid.getSelectionModel().getSelected();
                        Ext.Ajax.request({
                            url: basepath + '/ocrmFFinTarget!batchDestroy.json',
                            mothed: 'POST',
                            params: {
                                ids: idStr
                            },
                            failure: function(form, action) {
                                Ext.MessageBox.alert('提示', '删除失败！');
                            },
                            success: function(response) {
                                title_store.load({
                                    params: {
                                        DEMAND_ID: next_forms.getForm().findField('DEMAND_ID').getValue()
                                    }
                                });
                                Ext.MessageBox.alert('提示', '删除成功！');
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



var form_set_next_2 = new Ext.form.FieldSet({
    frame: true,
    height: 230,
    title: '目标配置',
    layout: 'column',
    items: [{
        columnWidth: .35,
        items: [type_form]
    },{
        columnWidth: .65,
        items: [title_grid]
    }]
});

var next_forms = new Ext.form.FormPanel({
    region: 'center',
    margins: '3 3 3 0',
    autoHeight:true,
    buttonAlign:'center',
    frame: true,
    items: [form_set_next_1, form_set_next_2],
     buttons: [{
        text: '上一步',
        handler: function() {
        	showCustomerViewByIndex(0);
        }
    },{
        text: '下一步',
        handler: function() {
        	showCustomerViewByTitle('规划向导-配置情况');
        }
    },{
        text: '关闭',
        handler: function() {
            hideCurrentView();
        }
    }]
});

var configChart = new Ext.Panel({
	height:180,
	html:'<div id="chartIdconfigDiv" ><div>'
});

var config_store0 = new Ext.data.Store({
    restful: true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/custDemand!queryConfigProdType.json'
    }),
    reader: new Ext.data.JsonReader({
        totalProperty: 'json.count',
        root: 'json.data'
    },
    [{
        name: 'F_VALUE'
    },{
        name: 'PROD_RATE1'
    },{
        name: 'PROD_RATE2'
    }])
});

var config_rownum0 = new Ext.grid.RowNumberer({
    header: 'No.',
    width: 35
});
var config_cm0 = new Ext.grid.ColumnModel([config_rownum0, 
	{ header: '产品大类',dataIndex: 'F_VALUE',width: 	100,sortable: true},
    {header: '模板占比(%)',dataIndex: 'PROD_RATE1',width: 80,menuDisabled: true},
	{header: '实际占比(%)',dataIndex: 'PROD_RATE2',width: 80,sortable: true}
]);
var config_grid0 = new Ext.grid.GridPanel({
    height: 180,
    frame: true,
    overflow: 'auto',
    autoScroll: true,
    store: config_store0,
    stripeRows: true,
    cm: config_cm0
});


var config_store = new Ext.data.Store({
    restful: true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/custDemand!queryConfigProd.json'
    }),
    reader: new Ext.data.JsonReader({
        totalProperty: 'json.count',
        root: 'json.data'
    },
    [{
        name: 'PROD_TYPE_ID'
    },{
        name: 'PROD_NAME'
    },{
        name: 'PROD_SCALE'
    },{
        name: 'PROD_RATE'
    }])
});

var config_rownum = new Ext.grid.RowNumberer({
    header: 'No.',
    width: 35
});
var config_cm = new Ext.grid.ColumnModel([config_rownum, 
	{ header: '产品大类',dataIndex: 'PROD_TYPE_ID',width: 80,sortable: true},
    {header: '产品名称',dataIndex: 'PROD_NAME',width: 200,menuDisabled: true},
    {header: '产品规模',dataIndex: 'PROD_SCALE',width: 100,menuDisabled: true,
	    renderer: function(value) {
	        if (value != '') {
	            return Ext.util.Format.number(value, '0,000.00');
	        }
	    }
	},
	{header: '产品占比(%)',dataIndex: 'PROD_RATE',width: 180,sortable: true}
]);
var config_grid = new Ext.grid.GridPanel({
    height: 150,
    frame: true,
    overflow: 'auto',
    autoScroll: true,
    store: config_store,
    stripeRows: true,
    cm: config_cm
});

//产品配置情况
var config_panel = new Ext.Panel({
    frame: true,
    autoHeight:true,
    buttonAlign:'center',
    layout:'form',
    items: [{
    	layout:'column',
    	items:[{
    		columnWidth:0.5,
    		items:[configChart]
    	},{
    		columnWidth:0.5,
    		items:[config_grid0]
    	}]
    },config_grid],
    buttons:[{
        text: '上一步',
        handler: function() {
            showCustomerViewByIndex(1);
        }
    },{
        text: '生成报告',
        id: 'sczds_Id',
        handler: function() {
          	var demandId = next_forms.getForm().findField('DEMAND_ID').getValue();
			Ext.Msg.wait('请稍等,正在生成中...','提示');
            Ext.Ajax.request({
                url: basepath + '/custDemand!createReport.json',
                method: 'POST',
                params:{
                	riskType: getSelectedData().data.CUST_RISK_CHARACT,
                	demandId : demandId
                },
                success: function(response){
                	Ext.Msg.alert('提示','操作成功！');
                },
                failure:function(){
                    Ext.Msg.alert('提示','操作失败！');
                }
            });
        }
    },{
    	id : 'downloadReport',
        text: '下载报告',
        handler: function() {
        	var demandId = next_forms.getForm().findField('DEMAND_ID').getValue();
			window.open( basepath+'/TempDownload?filename=LCGH_'+demandId+'.pdf','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
        }
    },{
        text: '关闭',
        handler: function() {
            hideCurrentView();
        }
    }]
})


var customerView = [{
	title: '规划向导-配置需求',
	type: 'form',
	hideTitle:true,
	suspendWidth: 900,
	items:[form_set]
},{
	title: '规划向导-目标产品配置',
	type: 'form',
	hideTitle:true,
	suspendWidth: 900,
	items:[next_forms]
},{
	title: '规划向导-配置情况',
	type: 'form',
	hideTitle:true,
	suspendWidth: 900,
	items:[config_panel]
}];

var beforeviewshow = function(theview){
	if(!getSelectedData()){
		Ext.Msg.alert('提示','请选择要操作的一行记录！');
		return false;
	}
	if(theview._defaultTitle == '规划向导-配置需求'){
		if(getSelectedData().data.CUST_RISK_CHARACT == null || getSelectedData().data.CUST_RISK_CHARACT == ""){
			Ext.Msg.alert('提示','请先进行风险评估,再进行理财服务！');
			return false;
		}
		if(form_set.getForm().getEl()){
			form_set.getForm().getEl().dom.reset();
		}
		//绑定数据字典
		form_set.getForm().findField('CUST_RISK_CHARACT').bindStore(findLookupByType('CUST_RISK_CHARACT'));
		next_forms.getForm().findField('CUST_RISK_CHARACT').bindStore(findLookupByType('CUST_RISK_CHARACT'));
		form_set.getForm().loadRecord(getSelectedData());
		demandStore.load({
			params:{
				custId : getSelectedData().data.CUST_ID
			},
			callback:function(){
				if(demandStore.getCount()>0){
					var record = demandStore.getAt(0);
					form_set.getForm().loadRecord(record);
				}
			}
		});
	}
	if(theview._defaultTitle == '规划向导-目标产品配置'){
		save_form.getForm().findField('DEMAND_TYPE').bindStore(findLookupByType('DEMAND_TYPE'));
		var custRisk = next_forms.getForm().findField('CUST_RISK_CHARACT').getValue();
		custRisk = Number(custRisk);
		var tempRisk = '';
		for(var i=1;i<= custRisk;i++){
			if(i==1){
				tempRisk = i;
			}else{
				tempRisk +=","+i;
			}
		}
		sub_product_form.getForm().findField('PROD_NAME').riskLevel = tempRisk;
		title_store.load({
	         params: {
	            DEMAND_ID: next_forms.getForm().findField('DEMAND_ID').getValue()
	         }
	     });
	}
	if(theview._defaultTitle == '规划向导-配置情况'){
		config_store.load({
			 params: {
	            demandId: next_forms.getForm().findField('DEMAND_ID').getValue()
	         }
		});
		config_store0.load({
			 params: {
			 	riskType: next_forms.getForm().findField('CUST_RISK_CHARACT').getValue(),
	            demandId: next_forms.getForm().findField('DEMAND_ID').getValue()
	         }
		});
		
		var chartIdconfig = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf","chartIdconfig", "100%", "160", "0", "0");
		Ext.Ajax.request({
            url: basepath + '/custDemand!queryConfigChart.json',
            method: 'GET',
            params : {
            	demandId: next_forms.getForm().findField('DEMAND_ID').getValue()
            },
            success: function(response) {
            	 var data = Ext.util.JSON.decode(response.responseText).json.data;
            	 var xmlData = '<chart caption="推荐资产配置" baseFontSize="12" formatNumberScale="0" >';
            	 for(var i=0;i<data.length;i++){
            	 	xmlData += '<set label="' + data[i].F_VALUE + '"  value="'+ Number(data[i].CONF_SCALE) + '"/>';
            	 }
            	 xmlData += '</chart>';
            	 chartIdconfig.setDataXML(xmlData);
            	 chartIdconfig.render("chartIdconfigDiv");
            },
            failure: function(){
            }
        });

	}
};

var autoLoadGrid = false;
/*
 * 处理其它页面跳转过来时参数处理
 */
var fnCondisDecide = function() {
	var parms = '';
	if (window.location.search) {
		parms = Ext.urlDecode(window.location.search);
	}
	var condis = parms['?condis']?parms['?condis']:parms['condis'];
	if (typeof condis != "undefined") {
		getConditionField('CUST_ID').setValue(condis);
		setSearchParams({CUST_ID:condis});
	}else{
		setSearchParams({});
	}
};

var afterinit = function(){
	fnCondisDecide();
};


