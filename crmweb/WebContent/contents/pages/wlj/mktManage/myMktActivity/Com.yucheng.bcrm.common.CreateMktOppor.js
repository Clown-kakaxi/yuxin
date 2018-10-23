Ext.ns('Com.yucheng.bcrm.common');
Ext.QuickTips.init();
/**
 * 
 * @author wangmk1
 * @description 创建商机组件
 * @version 1.000
 * @since 2014-9-28
 * @param {} config  
 * 可接受参数: 
 * 商机名称 opporName,
 * 商机类型 opporType,
 * 商机来源 opporSource,
 * 商机客户来源 custSource,
 * 商机开始日期 opporStartDate,
 * 商机完成日期 opporEndDate,
 * 商机有效期 opporDueDate,
 * 营销活动名称 mktActivName,
 * 营销活动ID mktActivId,
 * 营销任务指标名称 mktTargetName,
 * 营销任务指标ID mktTargetId,
 * 商机内容 opporContent,
 * 商机备注 memo
 * 
 * 客户编号custId
 * 客户名称custName
 * 客户类型custCategory     个人和企业
 * 客户状态custType   潜在和正式
 * 客户联系人custContactName
 * 商机执行人ID executeUserId
 * 商机执行人 executeUserName
 * 商机执行机构ID executeOrgId
 * 商机执行机构 executeOrgName
 *  
 * 产品编号prodId
 * 产品名称prodName
 * 预计金额planAmount
 * 费用预算planCost
 */
Com.yucheng.bcrm.common.CreateMktOppor=function(config){
	config=config||{};
	var _this=this;
	/********************基本信息***************************************/
	/**
	 * 数据字典
	 */
	//"商机来源"
    var opporSourceStore = new Ext.data.Store({
        	restful : true,
        	autoLoad : true,
        	proxy : new Ext.data.HttpProxy({
        		url : basepath + '/lookup.json?name=BUSI_CHANCE_SOURCE'
        	}),
        	reader : new Ext.data.JsonReader({
        		root : 'JSON'
        	}, [ 'key', 'value' ]),
        	listeners :{
    			load :function(){
					//设置默认客户类型
    				var opporSourceField = baseForm.getForm().findField('opporSource');
    				if(opporSourceField){
    					opporSourceField.setValue(baseForm.getForm().findField('opporSource').getValue());
    				}
    			}
    		}
        });
    // "商机类型"
    var opporTypeStore = new Ext.data.Store({
        	restful : true,
        	autoLoad : true,
        	proxy : new Ext.data.HttpProxy({
        		url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
        	}),
        	reader : new Ext.data.JsonReader({
        		root : 'JSON'
        	}, [ 'key', 'value' ])
        });
	//商机客户来源
    var custSourceStore = new Ext.data.ArrayStore({
    		fields : [ 'key', 'value'  ],
    		data : [ [ '1', '客户' ], [ '2', '客户群' ] ]
    	});
   	/**
	 * 基本信息form
	 */
	var baseForm=new Ext.form.FormPanel({
		autoHeight:true,
		labelWidth:100,
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		layout:'column',
		items:[{
    			columnWidth : .33,
    			layout : 'form',
    			items : [{
							xtype : 'textfield',
							fieldLabel : '<font color=red>*</font>商机名称',
							allowBlank : false,
							name : 'opporName',
							anchor : '90%'
						}, {
							xtype : 'combo',
							fieldLabel : '<font color=red>*</font>商机来源',
							allowBlank : false,
							hiddenName : 'opporSource',
							anchor : '90%', 
							triggerAction : 'all',
							store : opporSourceStore,
							resizable : true,
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							emptyText : '未知',
							selectOnFocus : true,
							forceSelection : true
						}, {
							xtype : 'datefield',
							fieldLabel : '<font color=red>*</font>商机开始日期',
							format : 'Y-m-d',
							editable : true,
							validator:ckDate,
							validateOnBlur:true, 
							name : 'opporStartDate',
							allowBlank : false,
							anchor : '90%'
						}]
		},{
    			columnWidth : .33,
				layout : 'form',
				items : [{
							xtype : 'combo',
							fieldLabel : '<font color=red>*</font>商机类型',
							allowBlank : false,
							hiddenName : 'opporType',
							anchor : '90%',
							triggerAction : 'all',
							store : opporTypeStore,
							resizable : true,
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							emptyText : '未知',
							selectOnFocus : true,
							forceSelection : true
						}, {
							xtype : 'combo',
							fieldLabel : '<font color=red>*</font>商机客户来源',
							allowBlank : false,
							hiddenName : 'custSource',
							anchor : '90%',
							triggerAction : 'all',
							store : custSourceStore,
							resizable : true,
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							emptyText : '未知',
							selectOnFocus : true,
							forceSelection : true
						}, {
							xtype : 'datefield',
							fieldLabel : '<font color=red>*</font>商机完成日期',
							allowBlank : false,
							format : 'Y-m-d',
							validator:ckDate,
							validateOnBlur:true, 
        					editable : true,
        					name : 'opporEndDate',
							anchor : '90%'
						}]	
    	},{
    			columnWidth : .33,
    			layout : 'form',
    			items : [{
							xtype : 'datefield',
							fieldLabel : '<font color=red>*</font>商机有效期',
							allowBlank : false,
							format : 'Y-m-d',
        					editable : true,
        					validator:ckDate,
        					validateOnBlur:true, 
        					name : 'opporDueDate',
							anchor : '90%'
						}, {
							xtype : 'activityQuery',
        					fieldLabel : '营销活动名称',
        					name : 'mktActivName',
        					hiddenName : 'mktActivId',
        					singleSelect : false,
        					anchor : '90%'
						}, {
							xtype:'mktTaskTarget',
	        				fieldLabel : '营销任务指标名称',
	        				labelStyle : 'text-align:right;',
	        				name : 'mktTargetName',
	        				hiddenName : 'mktTargetId',
	        				singleSelect : false,
	        				anchor : '90%'
						}]	
		},{
			columnWidth : 1,
    		layout : 'form',
    		items : [{
						xtype : 'textarea',
						fieldLabel : '<font color=red>*</font>商机内容',
						allowBlank : false,
						name : 'opporContent',
						anchor : '96%'
					}, {
						xtype : 'textarea',
						fieldLabel : '商机备注',
						name : 'memo',
						anchor : '96%'
					}]
		}]
	});
	/********************客户信息**************************************/
	/**
	 * 客户信息grid sm
	 */
	var custsm = new Ext.grid.CheckboxSelectionModel();
	/**
	 * 客户信息grid cm
	 */
    var custcm = new Ext.grid.ColumnModel({
        defaults: {
            sortable: true,
            align:'left'
        },
        columns: [custsm,{
            header: '客户编号',
            dataIndex: 'custId',
            width: 100,
            editor: new Ext.form.TextField({
                allowBlank: false
            })
        },{
        	id:'custName',
            header: '客户名称',
            dataIndex: 'custName',
            width: 100,
            //使用放大镜
            editor: new Com.yucheng.bcrm.common.CustomerQueryField({
            	xtype:'customerquery',
            	hiddenName:'custId',
        		name : 'custName',
        		//处理回显
        		callback:function(a,b){
        			//选择的列的record
        			var row = custgrid.getSelectionModel().getSelected();
        			//为grid赋值。
        			custstore.getById(row.id).data.custName = b[0].data.CUST_NAME;
        			custstore.getById(row.id).data.custId = b[0].data.CUST_ID;
        			custstore.getById(row.id).data.custCategory = b[0].data.CUST_TYPE;
        			custstore.getById(row.id).data.custCategoryOra = b[0].data.CUST_TYPE_ORA;
        			custstore.getById(row.id).data.custType = b[0].data.POTENTIAL_FLAG;
        			custstore.getById(row.id).data.custTypeOra = b[0].data.POTENTIAL_FLAG_ORA;
        			custstore.getById(row.id).data.custContactName = b[0].data.LINKMAN_NAME;
        			custstore.getById(row.id).data.executeOrgId = b[0].data.ORG_ID;
        			custstore.getById(row.id).data.executeOrgName = b[0].data.ORG_NAME;
        			custstore.getById(row.id).data.executeUserId = b[0].data.MGR_ID;
        			custstore.getById(row.id).data.executeUserName = b[0].data.MGR_NAME;
        			custgrid.view.refresh();//刷新列
        		},
                allowBlank: false
            })
        }, {
            header: '客户类型',
            dataIndex: 'custCategoryOra',
            width: 100,
     		editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
            header: '客户类型',
            hidden:true,
            dataIndex: 'custCategory',
            width: 100,
     		editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
            header: '客户状态',
            dataIndex: 'custTypeOra',
            width: 100,
          	editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
            header: '客户状态',
            hidden:true,
            dataIndex: 'custType',
            width: 100,
          	editor: new Ext.form.TextField({
                allowBlank: false
            })            
        }, {
            header: '客户联系人',
            dataIndex: 'custContactName',
            width: 100,
     		editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
        	id:'exUserN',
            header: '商机执行人',
            dataIndex: 'executeUserName',
            width: 100,
            editor: new Ext.form.TextField({
                allowBlank: false
            })
		}, {
            header: '商机执行人ID',
            hidden:true,
            dataIndex: 'executeUserId',
            width: 100,
     		editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
            header: '商机执行机构',
            dataIndex: 'executeOrgName',
            width: 100,
     		editor: new Ext.form.TextField({
                allowBlank: false
            })
        }, {
            header: '商机执行机构ID',
            hidden:true,
            dataIndex: 'executeOrgId',
            width: 100,
     		editor: new Ext.form.TextField({
                allowBlank: false
            })
        }]
    });
	/**
	 * 客户信息grid store
	 */
    var custstore = new Ext.data.ArrayStore({
	    fields: [{name : 'custId'},
				{name : 'custName'},
				{name : 'custCategory'},
				{name : 'custType'},
				{name : 'custContactName'},
				{name : 'executeUserName'},
				{name : 'executeUserId'},
				{name : 'executeOrgName'},
				{name : 'executeOrgId'}
				]
    });
	/**
	 * 客户信息grid
	 */
    var custgrid = new Ext.grid.EditorGridPanel({
        store: custstore,
        autoScroll:true,
        cm: custcm,
        sm:custsm,
        width: 800,
        height: 120,
        frame: true,
        clicksToEdit: 1,
        listeners:{  
        	/**
        	 *      e = {
                    grid: this,
                    record: r,
                    field: field,
                    value: r.data[field],
                    row: row,
                    column: col,
                    cancel:false
                };
        	 */
            'beforeedit':function(e){ 
            	if(e.field!='custName'){
            		e.cancel= true;
        			return false;
            	}
            },
              /**
               * var e = {
                grid: this,
                record: r,
                field: field,
                originalValue: startValue,
                value: value,
                row: ed.row,
                column: ed.col,
                cancel:false
        	    };
               */
              'validateedit':function(e){ 
            },
            	/**
        	 *    e = {
                grid: this,
                record: r,
                field: field,
                originalValue: startValue,
                value: value,
                row: ed.row,
                column: ed.col,
                cancel:false
            };
                };
        	 */
            'afteredit':function(e){ 
            }  
        } ,
        tbar: [{
            text: '新增',
            handler : function(){
                var Plant = custgrid.getStore().recordType;
                var p = new Plant({
                    custId: '',
                    custName: '',
                    custType: '',
                    custContactName:'',
                    executeUserName:'',
                    executeUserId:'',
                    executeOrgName:'',
                    executeOrgId:''
                });
                custgrid.stopEditing();
                custstore.insert(0, p);
                custgrid. getSelectionModel().selectFirstRow();  
                custgrid.startEditing(0,2);
            }
        },{
           text: "删除",
           handler: function() {
               var selModel = custgrid.getSelectionModel();
               if (selModel.hasSelection()) {
                   Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
                       if (button == "yes") {
                           var selections = selModel.getSelections();
                           Ext.each(selections, function(item) {
                               custstore.remove(item);
                           });
                       }
                   });
               }
               else {
                   Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
               }
           }
        }]
    });

    /********************产品信息****************************************/
    //金额格式化
    Ext.apply(Ext.util.Format,{ money : function(v) {
            v = (Math.round((v-0)*100))/100;
            v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
            v = String(v);
            var ps = v.split('.'),
                whole = ps[0],
                sub = ps[1] ? '.'+ ps[1] : '.00',
                r = /(\d+)(\d{3})/;
            while (r.test(whole)) {
                whole = whole.replace(r, '$1' + ',' + '$2');
            }
            v = whole + sub;
            if (v.charAt(0) == '-') {
                return '-' + v.substr(1);
            }
            return "" +  v;
        }});
   
    
	/**
	 * 产品信息grid sm
	 */
	var prodsm = new Ext.grid.CheckboxSelectionModel();
	/**
	 * 产品信息grid cm
	 */
    var prodcm = new Ext.grid.ColumnModel({
        defaults: {
            sortable: true,
            align:'left'
        },
        columns: [prodsm,{
            header: '产品编号',
            dataIndex: 'prodId',
            width: 120,
    		editor: new Ext.form.TextField({
                allowBlank: false
            })
        },{
            header: '产品名称',
            dataIndex: 'prodName',
            width: 120,
            editor: new Com.yucheng.crm.common.ProductManage({
            	xtype:'productChoose',
            	hiddenName:'prodId',
        		name : 'prodName',
        		//处理回显
        		callback:function(a){
        			//选择的列的record
        			var row = prodgrid.getSelectionModel().getSelected();
        			//为grid赋值。
        			prodstore.getById(row.id).data.prodName = a[0].data.productName;
        			prodstore.getById(row.id).data.prodId = a[0].data.productId;
        			prodgrid.view.refresh();//刷新列
        		},
                allowBlank: false
            })
        }, {
            header: '预计金额',
            dataIndex: 'planAmount',
            width: 120,
            align: 'right',
            renderer: 'money',
            editor: new Ext.form.NumberField({
                allowBlank: false,
                allowNegative: false
            })
        }, {
            header: '费用预算',
            dataIndex: 'planCost',
            width: 120,
            align: 'right',
            renderer: 'money',
            editor: new Ext.form.NumberField({
                allowBlank: false,
                allowNegative: false
            })
        }]
    });
	/**
	 * 产品信息grid store
	 */
    var prodstore = new Ext.data.ArrayStore({
    	fields:[{name : 'prodId'},
				{name : 'prodName'},
				{name : 'planAmount'},
				{name : 'planCost'}
				]
    });
	/**
	 * 产品信息grid
	 */
    var prodgrid = new Ext.grid.EditorGridPanel({
        store: prodstore,
        autoScroll:true,
        cm: prodcm,
        sm:prodsm,
        width: 800,
        height: 120,
        frame: true,
        clicksToEdit: 1,
        listeners:{  
        	/**
        	 *      e = {
                    grid: this,
                    record: r,
                    field: field,
                    value: r.data[field],
                    row: row,
                    column: col,
                    cancel:false
                };
        	 */
            'beforeedit':function(e){ 
            	if(e.field=='prodId'){
            		e.cancel= true;
        			return false;
            	}
            },
              /**
               * var e = {
                grid: this,
                record: r,
                field: field,
                originalValue: startValue,
                value: value,
                row: ed.row,
                column: ed.col,
                cancel:false
        	    };
               */
              'validateedit':function(e){ 
            },
            	/**
        	 *    e = {
                grid: this,
                record: r,
                field: field,
                originalValue: startValue,
                value: value,
                row: ed.row,
                column: ed.col,
                cancel:false
            };
                };
        	 */
            'afteredit':function(e){ 
            }  
        } ,
        tbar: [{
            text: '新增',
            handler : function(){
            	//创建record p
                var Plant = prodgrid.getStore().recordType;
                var p = new Plant({
                    prodId: '',
                    prodName: '',
                    planAmount: '',
                    planCost:''
                });
                prodstore.insert(0, p);
              	prodgrid. getSelectionModel().selectFirstRow();  
                prodgrid.startEditing(0,2);
            }
        },{
           text: "删除",
           handler: function() {
               var selModel = prodgrid.getSelectionModel();
               if (selModel.hasSelection()) {
                   Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
                       if (button == "yes") {
                           var selections = selModel.getSelections();
                           Ext.each(selections, function(item) {
                               prodstore.remove(item);
                           });
                       }
                   });
               }
               else {
                   Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
               }
           }
        }]
    });
	
	//baseForm参数接收
	if(config.opporName&&config.opporName!=''){
		baseForm.getForm().findField('opporName').setValue(config.opporName);
	}
	if(config.opporType&&config.opporType!=''){
		baseForm.getForm().findField('opporType').setValue(config.opporType);
	}
	
	//商机来源 暂时设置为 0-手动添加
	baseForm.getForm().findField('opporSource').setValue('0');
	baseForm.getForm().findField('opporSource').readOnly=true;
	if(config.opporSource&&config.opporSource!=''){
//		baseForm.getForm().findField('opporSource').setValue(config.opporSource);
	}
	//商机客户来源    暂时设置为 1-客户
	baseForm.getForm().findField('custSource').setValue('1');
	baseForm.getForm().findField('custSource').readOnly=true;
	if(config.custSource&&config.custSource!=''){
//		baseForm.getForm().findField('custSource').setValue(config.custSource);
	}
	if(config.opporStartDate&&config.opporStartDate!=''){
		baseForm.getForm().findField('opporStartDate').setValue(config.opporStartDate);
	}
	if(config.opporEndDate&&config.opporEndDate!=''){
		baseForm.getForm().findField('opporEndDate').setValue(config.opporEndDate);
	}
	if(config.opporDueDate&&config.opporDueDate!=''){
		baseForm.getForm().findField('opporDueDate').setValue(config.opporDueDate);
	}
	if(config.mktActivName&&config.mktActivName!=''){
		baseForm.getForm().findField('mktActivName').setValue(config.mktActivName);
	}
	if(config.mktActivId&&config.mktActivId!=''){
		baseForm.getForm().findField('mktActivId').setValue(config.mktActivId);
	}
	if(config.mktTargetName&&config.mktTargetName!=''){
		baseForm.getForm().findField('mktTargetName').setValue(config.mktTargetName);
	}
	if(config.mktTargetId&&config.mktTargetId!=''){
		baseForm.getForm().findField('mktTargetId').setValue(config.mktTargetId);
	}
	if(config.opporContent&&config.opporContent!=''){
		baseForm.getForm().findField('opporContent').setValue(config.opporContent);
	}
	if(config.memo&&config.memo!=''){
		baseForm.getForm().findField('memo').setValue(config.memo);
	}	
	/**
	 * 组件布局
	 */
	Ext.apply(config,{
		width: 700,
		height:700,
		autoScroll:true,
		layout:'form',
		items:[{xtype:'fieldset',title:'基本信息',collapsible : true,autoScroll:true,anchor:'99%',layout:'fit',frame:false,items:[baseForm]},
				{xtype:'fieldset',title:'客户信息',collapsible : true,autoScroll:true,anchor:'99%',layout:'fit',frame:false,items:[custgrid]},
				{xtype:'fieldset',title:'产品信息',collapsible : true,autoScroll:true,anchor:'99%',layout:'fit',frame:false,items:[prodgrid]}
		],
		buttonAlign:'center',
		buttons : [	{
						text : '保存',
						handler:function(){
							//获取baseform数据的json字符串
							var basejson = Ext.encode(baseForm.getForm().getValues());
							//获取custgrid数据的json字符串
							var custArray=Ext.pluck(custstore.data.items, 'data');
							var custjson=Ext.encode(custArray);  
							//获取progrid数据的json字符串
							var prodArray=Ext.pluck(prodstore.data.items, 'data');
							var prodjson=Ext.encode(prodArray); 
							var custIdArray=Ext.pluck(custArray, 'custId');
							//验证客户信息是否为空
							for(var i in custIdArray){
								if(custIdArray[i]==''){
									Ext.Msg.alert('提示','客户信息存在漏输项！');
									return false;
								}
							}
							var prodIdArray=Ext.pluck(prodArray, 'prodId');
							//验证产品信息是否为空
							for(var i in prodIdArray){
								if(prodIdArray[i]==''){
									Ext.Msg.alert('提示','产品信息存在漏输项！');
									return false;
								}
							}
							custgrid.getColumnModel().getColumnById('custName').getCellEditor();
							if(baseForm.getForm().isValid()){
								var custSource= baseForm.getForm().findField('custSource').getValue();
								var opporSource= baseForm.getForm().findField('opporSource').getValue();
								if(custSource=='1'&&opporSource=='0'){
									updateData(basejson,custjson,prodjson);
								}
							}else{
								Ext.Msg.alert('提示','输入数据验证失败！');
								return false;
							}
						}
					}, {
						text : '取消',
						handler : function() {
							_this.ownerCt.hide();
						}
					}]
	});
	//日期验证处理
	/**
	 * 日期校验
	 */
	function ckDate(){  
		    var v1 =  baseForm.getForm().findField("opporStartDate").getValue();  
		    var v2 =  baseForm.getForm().findField("opporEndDate").getValue();
		    var v3 =  baseForm.getForm().findField("opporDueDate").getValue();  
		    if(v1=="" || v2==""||v3=="") return true; 
		    
		    if(v2<=v1) {
		    	Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“完成日期”！');
		    	return false;
		    }else if(v3<=v1){
		    	Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“商机有效期”！');
		    	return false;
		    }else if(v3<=v2){
		    	Ext.Msg.alert('提示', '商机“完成日期”不能晚于或等于“商机有效期”！');
		    	return false;
		    }
		    return true;  
		};
	//保存数据处理
	function updateData(basejson,custjson,prodjson){
		if(custjson=='[]'||prodjson=='[]'){
			Ext.Msg.alert('提示', '客户信息或者产品信息不能为空！');
			return false;
		}
		
		Ext.Ajax.request({
			url:basepath+'/createMktOppor!save.json',
			method:'POST',
			params:{
				basejson:basejson,
				custjson:custjson,
				prodjson:prodjson
			},
			success:function(response){
				Ext.Msg.alert('提示', '保存成功！');
				_this.ownerCt.hide();
			},
			failure:function(response){
				Ext.Msg.alert('提示', '保存失败！');
			}
		})
	}
	Com.yucheng.bcrm.common.CreateMktOppor.superclass.constructor.call(this,config);
}
Ext.extend(Com.yucheng.bcrm.common.CreateMktOppor,Ext.Panel);
Ext.reg('createMktOppor',Com.yucheng.bcrm.common.CreateMktOppor);