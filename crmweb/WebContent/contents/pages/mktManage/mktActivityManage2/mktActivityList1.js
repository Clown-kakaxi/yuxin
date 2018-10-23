   
	var custLevelStore = new Ext.data.Store( {
        restful : true,
        sortInfo : {
            field : 'key',
            direction : 'ASC'
        },
        autoLoad : true,
        proxy : new Ext.data.HttpProxy( {
            url : basepath + '/lookup.json?name=MANAGER_LEVEL'
        }),
        reader : new Ext.data.JsonReader( {
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
	   //营销活动状态
	    var mactiStatusStore = new Ext.data.Store( {
	        restful : true,
	        sortInfo : {
	            field : 'key',
	            direction : 'ASC'
	        },
	        autoLoad : true,
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=MACTI_STATUS'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	   //渠道类型
	    var custLevelStore1 = new Ext.data.Store( {
	        restful : true,
	        sortInfo : {
	            field : 'key',
	            direction : 'ASC'
	        },
	        autoLoad : true,
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=MANAGER_LEVEL'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	//产品放大镜
	var prodAddCode = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '目标营销产品',
		name : 'productName',
		hiddenName : 'aimProd',
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		var prodCode = addActivityProdForm.form.findField('aimProd').getValue();
		addActivityProdForm.form.findField('prodCode').setValue(prodCode);
	}
		});
	var search_cust_add = new Com.yucheng.bcrm.common.CustomerQueryField({ 
		fieldLabel : '目标客户', 
		labelStyle: 'text-align:right;',
		name : 'custNameStr',
//		id:'groupRootCustName1',
		custtype :'2',//客户类型：  1：对私, 2:对公,  不设默认全部
	    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
	    singleSelected:false,//单选复选标志
		editable : false,
//		allowBlank:false,//不允许为空
		blankText:"请填写",
		anchor : '90%',
		hiddenName:'abcd',
		callback :function(){
		var cust_name = null;
		var linkNum = '';
//		cust_name = Ext.getCmp('add_aimCustomerName').getValue();
//		if (cust_name != null && cust_name != '') {
//			linkNum = Ext.getCmp('add_aimCustomerName').mobileNum.mobilenum[0];
////			dailyForm.getForm().findField('newcusttel').setValue(linkNum);
//		}
	}
	});
	// 新增活动展示的from 营销活动基本信息
	var addActivityForm = new Ext.form.FormPanel( {
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			xtype : 'fieldset',
			title : '营销活动基本信息',
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					fieldLabel : '<span style="color:red">*</span>营销活动名称',
					allowBlank : false,
					name : 'mktActiName',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					fieldLabel : '营销活动类型',
					name : 'mktActiType',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					fieldLabel : '营销方式',
					name : 'mktActiMode',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '营销团队',
					labelStyle : 'text-align:right;',
					name : 'mktActiTeam',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '<span style="color:red">*</span>费用预算',
					labelStyle : 'text-align:right;',
					format : 'Y-m-d',
					allowBlank : false,
					name : 'mktActiCost',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '计划开始时间',
					labelStyle : 'text-align:right;',
//					allowBlank : false,
					format : 'Y-m-d',
					name : 'pstartDate',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					labelStyle : 'text-align:right;',
					fieldLabel : '计划结束时间',
//					allowBlank : false,
					format : 'Y-m-d',
					name : 'pendDate',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '创建人',
					readOnly:true,
					labelStyle : 'text-align:right;',
					name : 'test',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '创建人ID',
					hidden:true,
					labelStyle : 'text-align:right;',
					name : 'createUser',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '创建日期',
					format : 'Y-m-d',
					labelStyle : 'text-align:right;',
					name : 'createDate',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [{
				store : mactiStatusStore,
				xtype : 'combo',
				fieldLabel : '营销活动状态',
				name : 'mktActiStat',
				hiddenName : 'mktActiStat',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				readOnly:true,
				labelStyle : 'text-align:right;',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '99%'
			}]}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '活动地点',
					labelStyle : 'text-align:right;',
					name : 'mktActiAddr',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					labelStyle : 'text-align:right;',
					fieldLabel : '营销活动内容',
					name : 'mktActiCont',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '涉及客户群描述',
					labelStyle : 'text-align:right;',
					name : 'actiCustDesc',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '涉及执行人描述',
					labelStyle : 'text-align:right;',
					name : 'actiOperDesc',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '涉及产品描述',
					labelStyle : 'text-align:right;',
					name : 'actiProdDesc',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					labelStyle : 'text-align:right;',
					fieldLabel : '营销活动目的',
					name : 'mktActiAim',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .99,
				layout : 'form',
				items : [ {
					xtype : 'textarea',
					fieldLabel : '备注',
					labelStyle : 'text-align:right;',
					name : 'actiRemark',
					anchor : '99%'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '下一步',
			handler : function() {
			if (!addActivityForm.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            };
			Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行[下一步]吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return;
				} 
				
//				addActivityProdForm.form.findField('prodCode').setValue('黄金20g');
//				addActivityProdForm.form.findField('prodCode1').setValue('9975');
//				addActivityProdForm.form.findField('mktActStr').setValue('957');
//				addActivityProdForm.form.findField('state').setValue('客户经理1');
//				addActivityProdForm.form.findField('state1').setValue(new Date());
				
					addActivityProdWindow.show();
					addActivityWindow.hide();
		});
		}
		}, {
			text : '取  消',
			handler : function() {
				addActivityWindow.hide();
			}
		} ]
	
	});
	//新增营销活动，营销活动产品信息
	var addActivityProdForm = new Ext.form.FormPanel( {
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			xtype : 'fieldset',
			title : '营销活动关联产品信息',
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '关联产品',
					labelStyle : 'text-align:right;',
					name : 'prodCode',
					anchor : '99%'
				}]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '关联产品编号',
					labelStyle : 'text-align:right;',
					name : 'prodCode1',
					anchor : '99%'
				} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '营销活动编号',
					labelStyle : 'text-align:right;',
					name : 'mktActStr',
					anchor : '99%'
				} ]
			}]
		}, {
			xtype : 'fieldset',
			title : '操作人信息',
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '创建人',
					labelStyle : 'text-align:right;',
					name : 'state',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '创建日期',
					labelStyle : 'text-align:right;',
					value:'2013-2-19',
					format : 'Y-m-d',
					name : 'state1',
					anchor : '99%'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '上一步',
			handler : function() {
				addActivityWindow.show();
				addActivityProdWindow.hide();
			}
		}, {
			text : '下一步',
			handler : function() {
			{
				Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行[下一步]吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return;
					} 
					addActivityCustForm.form.findField('custIdStr1').setValue('王二');
					addActivityCustForm.form.findField('custIdStr').setValue('CD19726');
					addActivityCustForm.form.findField('mktActStr').setValue('957');
					addActivityCustForm.form.findField('state').setValue('客户经理1');
					addActivityCustForm.form.findField('state1').setValue(new Date());
						addActivityCustWindow.show();
						addActivityProdWindow.hide();
			});
			}
			}
		}, {
			text : '取  消',
			handler : function() {
				addActivityProdWindow.hide();
			}
		} ]
	
	});
	//新增营销活动，关联客户信息
	var addActivityCustForm = new Ext.form.FormPanel( {
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			xtype : 'fieldset',
			title : '营销活动目标客户信息',
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '目标客户',
					labelStyle : 'text-align:right;',
					name : 'custIdStr1',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户编号',
					labelStyle : 'text-align:right;',
					name : 'custIdStr',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '营销活动编号',
					labelStyle : 'text-align:right;',
					name : 'mktActStr',
					anchor : '99%'
				} ]
			}]
		}, {
			xtype : 'fieldset',
			title : '操作人信息',
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '创建人',
					labelStyle : 'text-align:right;',
					name : 'state',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '创建日期',
					labelStyle : 'text-align:right;',
					name : 'state1',
					format : 'Y-m-d',
					anchor : '99%'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '上一步',
			handler : function() {
				addActivityProdWindow.show();
				addActivityCustWindow.hide();
			}
		}, {
			text : '下一步',
			handler : function() {
			{
				Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行[下一步]吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return;
					} 
					addActivityChanelForm.form.findField('productId').setValue('XX渠道');
					addActivityChanelForm.form.findField('mktActStr').setValue('957');
					addActivityChanelForm.form.findField('state').setValue('客户经理1');
					addActivityChanelForm.form.findField('state1').setValue(new Date());
						addActivityChanelWindow.show();
						addActivityCustWindow.hide();
			});
			}
			}
		}, {
			text : '取  消',
			handler : function() {
			addActivityCustWindow.hide();
			}
		} ]
	
	});
	//新增营销活动，关联渠道信息
	var addActivityChanelForm = new Ext.form.FormPanel({
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			xtype : 'fieldset',
			title : '渠道信息',
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				items : [{
				store : custLevelStore1,
				xtype : 'combo', resizable : true,
				fieldLabel : '渠道类型',
				name : 'productId',
				hiddenName : 'productId',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				editable :false,
				labelStyle : 'text-align:right;',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
                selectOnFocus : true,
				anchor : '90%'
			}]}/*, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户编号',
					labelStyle : 'text-align:right;',
					name : 'custIdStr',
					anchor : '99%'
				} ]
			}*/, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '营销活动编号',
					labelStyle : 'text-align:right;',
					name : 'mktActStr',
					anchor : '99%'
				} ]
			}]
		}, {
			xtype : 'fieldset',
			title : '操作人信息',
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '创建人',
					labelStyle : 'text-align:right;',
					name : 'state',
					anchor : '99%'
				} ]
			}, {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '创建日期',
					labelStyle : 'text-align:right;',
					name : 'state1',
					format : 'Y-m-d',
					anchor : '99%'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '上一步',
			handler : function() {
				addActivityCustWindow.show();
				addActivityChanelWindow.hide();
			}
		}, {
			text : '下一步',
			handler : function() {
			{
				Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行[下一步]吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return;
					} 
						Ext.MessageBox.confirm('提示','渠道信息已经建立，需要添加附件信息吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
								addActivityChanelWindow.hide();
							}if(buttonId.toLowerCase() == "yes"){
				                appendixWindow.show();
				            
							} 
						});
			});
			}
			}
		}, {
			text : '取  消',
			handler : function() {
			addActivityChanelWindow.hide();
			}
		} ]
	
	});
	
    // 修改信息窗口展示的from
    var editBasePlanForm1 = new Ext.form.FormPanel({
            labelWidth : 140,
            frame : true,
            height:130,
            width:870,
            region : 'center',
            autoScroll : true,
            buttonAlign : "center",
            items : [ {
//                xtype : 'fieldset',
//                title : '基本信息',
//                collapsed:true,
//    			collapsible : true,
//    			autoHeight : true,
                layout : 'column',
                items : [ {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        // readOnly : true,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '营销活动ID',
                        name : 'mktActiId',
                        value:'679',
                        hidden:true,
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '客户经理姓名',
                        // readOnly : true,
                        name : 'custManagerName',
                        value:'秦青',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '性别',
                        // readOnly : true,
                        name : 'sex',
                        value:'女',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '政治面貌',
                        // readOnly : true,
                        name : 'politicalStat',
                        value:'党员',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '职种',
                        // readOnly : true,
                        name : 'businessType',
                        value:'会计',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '岗位',
                        labelStyle : 'text-align:right;',
                        // readOnly : true,
                        name : 'station',
                        value:'客户经理',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 200,
                        // readOnly : true,
                        fieldLabel : '经济工作年限',
                        name : 'economyWorkYear',
                        value:'4',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '学历',
                        labelStyle : 'text-align:right;',
                        // readOnly : true,
                        name : 'education',
                        value:'本科',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '入行日期',
                        labelStyle : 'text-align:right;',
                        // readOnly : true,
                        format : 'Y-m-d',
                        value:'2013-3-5',
                        name : 'entrantsDate',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '状态',
                        labelStyle : 'text-align:right;',
                        name : 'state',
                        value:'正常',
                        anchor : '90%'
                    } ]
                } ]
            }]
        });
    var editBasePlanForm1 = new Ext.form.FormPanel({
        labelWidth : 140,
        frame : true,
        height:130,
        width:870,
        region : 'center',
        autoScroll : true,
        buttonAlign : "center",
        items : [ {
//            xtype : 'fieldset',
//            title : '基本信息',
//            collapsed:true,
//			collapsible : true,
//			autoHeight : true,
            layout : 'column',
            items : [ {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    width : 200,
                    // readOnly : true,
                    labelStyle : 'text-align:right;',
                    fieldLabel : '营销活动ID',
                    value:'679',
                    name : 'mktActiId',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    labelStyle : 'text-align:right;',
                    width : 200,
                    fieldLabel : '客户经理姓名',
                    // readOnly : true,
                    name : 'custManagerName',
                    value:'张三',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    labelStyle : 'text-align:right;',
                    width : 200,
                    fieldLabel : '性别',
                    // readOnly : true,
                    name : 'sex',
                    value:'女',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    labelStyle : 'text-align:right;',
                    width : 200,
                    fieldLabel : '政治面貌',
                    // readOnly : true,
                    name : 'politicalStat',
                    value:'党员',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    labelStyle : 'text-align:right;',
                    width : 200,
                    fieldLabel : '职种',
                    // readOnly : true,
                    name : 'businessType',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    width : 200,
                    fieldLabel : '岗位',
                    labelStyle : 'text-align:right;',
                    // readOnly : true,
                    name : 'station',
                    value:'客户经理',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    labelStyle : 'text-align:right;',
                    width : 200,
                    // readOnly : true,
                    fieldLabel : '经济工作年限',
                    name : 'economyWorkYear',
                    value:'4',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    width : 200,
                    fieldLabel : '学历',
                    labelStyle : 'text-align:right;',
                    // readOnly : true,
                    name : 'education',
                    value:'本科',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    width : 200,
                    fieldLabel : '入行日期',
                    labelStyle : 'text-align:right;',
                    // readOnly : true,
                    format : 'Y-m-d',
                    value:'2013-3-5',
                    name : 'entrantsDate',
                    anchor : '90%'
                } ]
            }, {
                columnWidth : .3,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    width : 200,
                    fieldLabel : '状态',
                    labelStyle : 'text-align:right;',
                    name : 'state',
                    value:'正常',
                    anchor : '90%'
                } ]
            } ]
        }]
    });

	// 新增活动窗口展示的form
	var addPlanPanel = new Ext.Panel( {
		layout : 'fit',
		buttonAlign : "center",
		items : [ addActivityForm ]
	});
	
	// 新增活动窗口展示的form
	var addPlanProdPanel = new Ext.Panel( {
		layout : 'fit',
		buttonAlign : "center",
		items : [ addActivityProdForm ]
	});
	
	// 新增活动窗口展示的form addCustPanel
	var addCustPanel = new Ext.Panel( {
		layout : 'fit',
		buttonAlign : "center",
		items : [ addActivityCustForm ]
	});
	
	// 新增活动窗口展示的form addChanelPanel
	var addChanelPanel = new Ext.Panel( {
		layout : 'fit',
		buttonAlign : "center",
		items : [ addActivityChanelForm ]
	});
	
    // 修改窗口展示的from
    var editPlanPanel = new Ext.Panel( {
        layout : 'fit',
        autoScroll : true,
        buttonAlign : "center",
        items : [ editBasePlanForm1 ]
    });
    var editPlanPanel1 = new Ext.Panel( {
        layout : 'fit',
        autoScroll : true,
        buttonAlign : "center",
        items : [ editBasePlanForm1]
    });
	
	// 定义新增窗口
	var addActivityWindow = new Ext.Window( {
		title : '营销活动新增',
		plain : true,
		layout : 'fit',
		maximized : false,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		width : 900,
		height : 400,
		resizable : true,
		draggable : true,
		border : false,
		items : [ addPlanPanel ]
	});
	// 定义新增窗口
	var addActivityProdWindow = new Ext.Window( {
		title : '营销活动新增',
		plain : true,
		layout : 'fit',
		maximized : false,
		width : 900,
		height : 400,
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
		autoScroll : true,
		items : [ {
            xtype : 'fieldset',
            title : '关联产品信息',
            collapsed:false,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [productContrastGrid2]
        }, {
            xtype : 'fieldset',
            title : '关联客户信息',
            collapsed:true,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [custContrastGrid2]
        }, {
            xtype : 'fieldset',
            title : '关联渠道信息',
            collapsed:true,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [chanelContrastGrid2]
        } ] ,
        buttonAlign : "center",
		buttons : [ {
			text : '上一步',
			handler : function() {
				addActivityWindow.show();
				addActivityProdWindow.hide();
			}
		}, {
			text : '下一步',
			handler : function() {
			{
				Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行[下一步]吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return;
					} 
						Ext.MessageBox.confirm('提示','相关信息已经建立，需要添加附件信息吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
								addActivityProdWindow.hide();
							}if(buttonId.toLowerCase() == "yes"){
				                appendixWindow.show();
				            
							} 
						});
			});
			}
			}
		}, {
			text : '取  消',
			handler : function() {
				addActivityProdWindow.hide();
			}
		} ]
	
	});
	
	
	// 定义新增窗口addActivityCustWindow
	var addActivityCustWindow = new Ext.Window( {
		title : '营销活动新增',
		plain : true,
		layout : 'fit',
		maximized : false,
		width : 900,
		height : 400,
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
		items : [ addCustPanel ]
	});
	
	// 定义新增窗口addActivityChanelWindow
	var addActivityChanelWindow = new Ext.Window( {
		title : '营销活动新增',
		plain : true,
		layout : 'fit',
		maximized : false,
		width : 900,
		height : 400,
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
		items : [ addChanelPanel ]
	});
    var appendixGridPanel3 = new Ext.grid.GridPanel({//附件table
        title:'附件列表',
        cm:columnAppendix,
        height:200,
        width:840,
        store:appendixStore
    });
    var editPlanWindow1 = new Ext.Window( {
        title : '营销活动详情',
        plain : true,
        layout : 'fit',
        autoScroll : true,
        maximized : false,
		width : 900,
		height : 400,
        closable : true,
        closeAction : 'hide',
        modal : true,
        loadMask : true,
        border : false,
        items : [ {
            xtype : 'fieldset',
            title : '基本信息',
			collapsible : true,
			collapsed: false,
			autoHeight : true,
            layout : 'column',
            items : [editPlanPanel1]
        }, {
            xtype : 'fieldset',
            title : '关联产品信息',
            collapsed:true,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [productContrastGrid1]
        }, {
            xtype : 'fieldset',
            title : '关联客户信息',
            collapsed:true,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [custContrastGrid1]
        }, {
            xtype : 'fieldset',
            title : '关联渠道信息',
            collapsed:true,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [chanelContrastGrid1]
        }, {
            xtype : 'fieldset',
            title : '附件信息',
            collapsed:true,
			collapsible : true,
			autoHeight : true,
            layout : 'fit',
            items : [appendixGridPanel3]
        }
        ]
    });
