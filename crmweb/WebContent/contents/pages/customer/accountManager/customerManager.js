var viewWindow;
Ext.onReady(function() {
    Ext.QuickTips.init();
    var msFlag = 0;
    // *******************************

        var xlStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=DEM0100007'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        var isHavingCardStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=IS_HAVING_CODE'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

        var custTypeStore = new Ext.data.Store( {
            restful : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=MANAGER_TYPE'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

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

        var levelStore = new Ext.data.Store( {
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
        
        var sexStore = new Ext.data.Store( {
            restful : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=DEM0100005'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        
        var polStore = new Ext.data.Store( {
            restful : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=POL_LANDSCAPE'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        
        
        var statStore = new Ext.data.Store( {
            restful : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=MANAGER_STATUS'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        var eduStore = new Ext.data.Store( {
            restful : true,
            sortInfo : {
                field : 'key',
                direction : 'ASC'
            },
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=DEM0100007'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
        
        var jigou = new Com.yucheng.bcrm.common.OrgField( {
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
            anchor : '95%',
            checkBox : true		// 复选标志
                });
        // *****************************************
        var searchPanel = new Ext.form.FormPanel( {

            title : "客户经理信息查询",
            labelWidth : 105,
            labelAlign : 'right',
            frame : true,
            region : 'north',
            // autoScroll : true,
            layout : 'column',
            items : [ {
                columnWidth : .25,
                layout : 'form',
                items : [ jigou, {
                    xtype : 'textfield',
                    fieldLabel : '客户经理姓名',
                    id : 'CUST_MANAGER_NAME',
                    name : 'CUST_MANAGER_NAME',
                    anchor : '95%'
                }, {
                    xtype : 'textfield',
                    fieldLabel : '经济工作年限',
                    id : 'ECONOMY_WORK_YEAR',
                    name : 'ECONOMY_WORK_YEAR',
                    anchor : '95%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    fieldLabel : '客户经理ID',
                    id : 'CUST_MANAGER_ID',
                    name : 'CUST_MANAGER_ID',
                    anchor : '95%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    store : custTypeStore,
                    xtype : 'combo',
                    name : 'CUST_MANAGER_TYPE',
                    id : 'CUST_MANAGER_TYPE',
                    fieldLabel : '客户经理类型',
                    valueField : 'key',
                    resizable : true,
                    displayField : 'value',
                    mode : 'local',
                    typeAhead : true,
                    forceSelection : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    // width : '100',
                    anchor : '95%'
                }

                ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    store : custLevelStore,
                    xtype : 'combo',
                    name : 'CUST_MANAGER_LEVEL',
                    id : 'CUST_MANAGER_LEVEL',
                    fieldLabel : '客户经理等级',
                    valueField : 'key',
                    displayField : 'value',
                    mode : 'local',
                    typeAhead : true,
                    forceSelection : true,
                    resizable : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    // width : '100',
                    anchor : '95%'
                }

                ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    fieldLabel : '学历',
                    name : 'EDUCATION',
                    id : 'EDUCATION',
                    forceSelection : true,
                    resizable : true,
                    xtype : 'combo',
                    labelStyle : 'text-align:right;',
                    triggerAction : 'all',
                    mode : 'local',
                    store : xlStore,
                    valueField : 'key',
                    displayField : 'value',
                    emptyText : '请选择',
                    anchor : '95%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    xtype : 'textfield',
                    fieldLabel : '年龄大于',
                    vtype : 'number',
                    id : 'AGE',
                    name : 'AGE',
                    maxLength : '3',
                    minLength : '0',
                    anchor : '95%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    xtype : 'datefield',
                    fieldLabel : '入行日期',
                    format : 'Y-m-d',
                    id : 'ENTRANTS_DATE',
                    name : 'ENTRANTS_DATE',
                    selectOnFocus : true,
                    anchor : '95%'
                } ]
            }, {
                columnWidth : .25,
                layout : 'form',
                items : [ {
                    store : isHavingCardStore,
                    xtype : 'combo',
                    name : 'IF_CREDIT',
                    id : 'IF_CREDIT',
                    fieldLabel : '是否有信贷证',
                    valueField : 'key',
                    displayField : 'value',
                    mode : 'local',
                    typeAhead : true,
                    forceSelection : true,
                    resizable : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    anchor : '95%'
                } ]
            } ],
            buttonAlign : 'center',
            buttons : [ {
                text : '查询',
                handler : function() {
                if (!searchPanel.getForm().isValid()) {
                    Ext.Msg.alert("提醒", "请填写必填项");
                    return false;
                }
                    var conditionStr = searchPanel.getForm().getFieldValues();
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
                    searchPanel.getForm().reset();
                    if (msFlag != 0) {
                        Ext.getCmp('MANAGER_TYPE').setValue('2');
                        Ext.getCmp('MANAGER_TYPE').setReadOnly(true);
                    }
                    Ext.getCmp("jigouhao").setValue('');

                }
            } ]
        });

        // 复选框
        var sm = new Ext.grid.CheckboxSelectionModel();

        // 定义自动当前页行号
        var rownum = new Ext.grid.RowNumberer( {
            header : 'No.',
            width : 28
        });

        // 列模型
        var columns = new Ext.grid.ColumnModel( [ rownum, sm, {
            header : '隐藏id',
            dataIndex : 'userId',
            sortable : true,
            hidden : true,
            width : 120
        }, {
            header : '机构号',
            dataIndex : 'affiInstId',
            sortable : true,
            width : 120
        }, {
            header : '机构名称',
            dataIndex : 'unitName',
            sortable : true,
            width : 120
        }, {
            header : '客户经理ID',
            dataIndex : 'custManagerId',
            sortable : true,
            width : 120
        }, {
            header : '客户经理姓名',
            dataIndex : 'custManagerName'
        }, {
            header : '性别',
            dataIndex : 'GENDER_ORA',
            width : 80
        },{
            header : '性别',
            dataIndex : 'gender',
            sortable : true,
            hidden:true,
            width : 120
        }, {
            header : '政治面貌',
            dataIndex : 'POLITICAL_STAT_ORA'
        },{
            header : '政治面貌',
            dataIndex : 'politicalStat',
            sortable : true,
            hidden:true,
            width : 120
        }, {
            header : '学历',
            dataIndex : 'EDUCATION_ORA'
        }, {
            header : '状态',
            dataIndex : 'STATE_ORA'
        },{
            header : '状态',
            dataIndex : 'state',
            sortable : true,
            hidden:true,
            width : 120
        },
        // {header : '年龄',dataIndex :'age',align:'right'},
                {
                    header : '是否有信贷证',
                    dataIndex : 'IS_HAVING_CARD_ORA'
                }, {
                    header : '职务',
                    dataIndex : 'duty'
                }, {
                    header : '岗位',
                    dataIndex : 'station'
                }, {
                    header : '职种',
                    dataIndex : 'businessType'
                }, {
                    header : '经济工作年限',
                    dataIndex : 'economyWorkYear',
                    align : 'right'
                }, {
                    header : '入行日期',
                    dataIndex : 'entrantsDate'
                },
                // {header : 'CRM系统所属机构',dataIndex : 'unitName'},
                {
                    header : '客户经理类型id',
                    dataIndex : 'custManagerType',
                    sortable : true,
                    hidden:true,
                    width : 120
                },{
                    header : '客户经理类型',
                    dataIndex : 'CUST_MANAGER_TYPE_ORA'
                }, {
                    header : '客户经理等级id',
                    dataIndex : 'custManagerLevel',
                    sortable : true,
                    hidden:true,
                    width : 120
                }, {
                    header : '客户经理等级',
                    dataIndex : 'CUST_MANAGER_LEVEL_ORA'
                }, {
                    header : '手机号码',
                    dataIndex : 'telephone',
                    sortable : true,
                    width : 120
                } /*
                     * ,{ header : 'CRM系统短息对象职位', dataIndex : 'CRMMailPosition',
                     * sortable : true, width : 120 },{ header : '等级档次',
                     * dataIndex : 'LEVL_ORA', sortable : true, width : 120 }
                     */]);

        var record = Ext.data.Record.create( [ {
            name : 'userId',
            mapping : 'USER_ID'
        }, {
            name : 'custManagerName',
            mapping : 'CUST_MANAGER_NAME'
        }, {
            name : 'affiInstId',
            mapping : 'UNITID'
        }, {
            name : 'custManagerId',
            mapping : 'CUST_MANAGER_ID'
        }, {
            name : 'sex',
            mapping : 'GENDER'
        }, {
            name : 'GENDER_ORA'
        }, {
            name : 'politicalStat',
            mapping : 'POLITICAL_STAT'
        },{
            name : 'POLITICAL_STAT_ORA'
        },
        // {name: 'age', mapping: 'AGE'},
                {
                    name : 'unitName',
                    mapping : 'UNITNAME'
                }, {
                    name : 'education',
                    mapping : 'EDUCATION'
                }, {
                    name : 'EDUCATION_ORA'
                },{
                    name : 'economyWorkYear',
                    mapping : 'ECONOMY_WORK_YEAR'
                }, {
                    name : 'state',
                    mapping : 'STATE'
                },{
                    name : 'STATE_ORA'
                },
                // {name: 'ACCESSIONSTATE_ORA'},
                {
                    name : 'duty',
                    mapping : 'DUTY'
                }, {
                    name : 'station',
                    mapping : 'STATION'
                }, {
                    name : 'businessType',
                    mapping : 'BUSINESS_TYPE'
                }, {
                    name : 'entrantsDate',
                    mapping : 'ENTRANTS_DATE'
                }, {
                    name : 'custManagerType',
                    mapping : 'CUST_MANAGER_TYPE'
                },{
                    name : 'CUST_MANAGER_TYPE_ORA'
                }, {
                    name : 'isHavingCard',
                    mapping : 'IS_HAVING_CARD'
                }, {
                    name : 'IS_HAVING_CARD_ORA'
                }, {
                    name : 'custManagerLevel',
                    mapping : 'CUST_MANAGER_LEVEL'
                },{
                    name : 'CUST_MANAGER_LEVEL_ORA'
                },
                // {name: 'managerLevel', mapping: 'MANAGER_LEVEL'},
                // {name: 'MANAGER_LEVEL_ORA'},
                // {name: 'CRMMailPosition', mapping: 'CRM_MAIL_POSITION'},
                // // {name: 'level', mapping: 'LEVL'},
                // {name: 'LEVL_ORA'},
                // {name: 'managerType', mapping: 'MANAGER_TYPE'},
                // {name: 'MANAGER_TYPE_ORA'},
                {
                    name : 'telephone',
                    mapping : 'TELEPHONE'
                } ]);

        var store = new Ext.data.Store( {
            restful : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/CustomerManagerInfoAction1.json',
                method : 'POST'/*
                                 * , success : function(response) {
                                 * Ext.Msg.alert('提示', response.responseText); }
                                 */
            // failure : function(response) {
                    // Ext.Msg.alert('提示','加入失败' );
                    // }

                    }),
            reader : new Ext.data.JsonReader( {
                successProperty : 'success',
                // idProperty: 'ID',
                // messageProperty: 'message',
                root : 'json.data',
                totalProperty : 'json.count'
            }, record)
        });

        // 每页显示条数下拉选择框
        var pagesize_combo = new Ext.form.ComboBox( {
            name : 'pagesize',
            triggerAction : 'all',
            mode : 'local',
            store : new Ext.data.ArrayStore(
                    {
                        fields : [ 'value', 'text' ],
                        data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
                                [ 50, '50条/页' ], [ 100, '100条/页' ],
                                [ 250, '250条/页' ], [ 500, '500条/页' ] ]
                    }),
            valueField : 'value',
            displayField : 'text',
            value : '20',
            forceSelection : true,
            width : 85
        });

        custTypeStore.load( {
            callback : function() {
                // 
                if (window.location.href.split("msFlag=")[1] != undefined) {
                    msFlag = 1;
                }
                if (msFlag != 0) {
                    Ext.getCmp('MANAGER_TYPE').setValue('2');
                    Ext.getCmp('MANAGER_TYPE').setReadOnly(true);
                    Ext.getCmp('msmgtyp').setReadOnly(true);
                }
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

        var listPanel = new Ext.grid.GridPanel({
	        title : "客户经理信息表",
	        store : store,
	        frame : true,
	        sm : sm,
	        cm : columns,
	        stripeRows : true,
            region : 'center',
            frame : true,
            bbar : bbar,// 分页工具栏
            viewConfig : {
        		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		    },
		    loadMask : {
		    	msg : '正在加载表格数据,请稍等...'
		    },
	        tbar : [{
                text : '客户经理维护',
                iconCls : 'editIconCss',
                handler : function() {
	        		var selectLength = listPanel.getSelectionModel().getSelections().length;
                    var selectRe = listPanel.getSelectionModel().getSelections()[0];
                    if (selectLength != 1) {
                        // alert('请选择一条记录');
                        Ext.Msg.alert("提示", "请选择一条记录!");
                    } else {
                        editBasePlanForm.getForm().loadRecord(selectRe);
                        document.getElementById('idStr').value = selectRe.data.id;
                        editInit();
                    }
                }
            },'-',{
                text : '查看详细信息',
                iconCls : 'detailIconCss',
                handler : function() {
            		// 得到选中记录
	                var selectRe = listPanel.getSelectionModel().getSelections()[0];
	                var selectLength = listPanel.getSelectionModel().getSelections().length;
	                if (selectLength != 1) {
	                    // alert('请选择一条记录');
	                    Ext.Msg.alert("提示", "请选择一条记录!");
	                } else {
	                    showChanceForm.getForm().loadRecord(selectRe);
	                    showInit();
	                }
	            }
            },'-',{
            	text:'客户经理业务全景视图',
            	iconCls:'custGroupMemIconCss',
            	handler : function(){
            		var selectRe = listPanel.getSelectionModel().getSelections()[0];
            		var checkedNodes = listPanel.getSelectionModel().selections.items;
            		var selectLength = listPanel.getSelectionModel().getSelections().length;
            		if (selectLength != 1) {
            			// alert('请选择一条记录');
            			Ext.Msg.alert("提示", "请选择一条记录!");
            		} else {
            			viewWindow = new Com.yucheng.crm.cust.ViewWindow({
            				id:'viewWindow',
            				initComponent : function() {
            					Com.yucheng.crm.cust.ViewWindow.superclass.initComponent.call(this);
            					if(this.custId!=''&&this.custName!=''&&this.custTyp!=''){
            						this.title='您所浏览的客户经理为：'+checkedNodes[0].data.custManagerName;
            					}
            				}
            			});
            			viewWindow.removeListener('beforerender',viewWindow.listeners.beforerender);
            			viewWindow.on('beforerender',function(){
            				var h = document.body.clientHeight;
            				var w = document.body.clientWidth;
            				if ( Ext.isIE9 ){
            	                this.add({items:[{html:' <div style="width: 100%;height:'+h+'px;"><div style="position:absolute; width:220px" id=\'cust_tree\'></div><div style="margin-left:220px;" id=\'viewport_centers\'></div></div>'}]});
            	            } else {
            	    			this.add({items:[{html:' <div style="width:'+w+'px;height:'+h+'px;"><div style="position:absolute; left:0px; top:0px; width:220px" id=\'cust_tree\'></div><div style="position:absolute; left:220px; top:0px; " id=\'viewport_centers\'></div></div>'}]});
            				}
            			});
            			viewWindow.removeListener('afterrender',viewWindow.listeners.afterrender);
            			viewWindow.on('afterrender',function(){
            				viewWindow.mrgIds= checkedNodes[0].data.custManagerId;
            				Ext.ScriptLoader.loadScript({        
            					scripts: [
            					    basepath + '/contents/commonjs/wljMemories/Wlj-Ext-Application-Contance-1.000-v1.0.js',
            					    basepath + '/contents/commonjs/wljMemories/Wlj-Ext-Patch-1.000-v1.0.js',
            					    basepath + '/contents/pages/customer/accountManager/menuOfCorporateCustManager.js'
            					],        
            					finalCallback: function(response) {  
	            					/**
	            					 * TODO do something
	            					 */
            					}
            				});
            			});
            			viewWindow.show();
            		}
            	}
            }]
        });

        var rollTypeStore = new Ext.data.Store( {
            restful : true,
            autoLoad : true,
            proxy : new Ext.data.HttpProxy( {
                url : basepath + '/lookup.json?name=ROLL_TYPE'
            }),
            reader : new Ext.data.JsonReader( {
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

        // 修改基本信息展示的form
        var editBasePlanForm = new Ext.form.FormPanel({
            labelWidth : 140,
            height : 300,
            frame : true,
            region : 'center',
            autoScroll : true,
            buttonAlign : "center",
            items : [ {
                xtype : 'fieldset',
                title : '客户经理基本信息',
                layout : 'column',
                items : [ {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        // readOnly : true,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '客户经理ID',
                        name : 'custManagerId',
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
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                    	store : sexStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%',
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '性别',
                        name : 'sex'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '政治面貌',
                        name : 'politicalStat',
                        store : polStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
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
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '学历',
                        labelStyle : 'text-align:right;',
                        // readOnly : true,
                        name : 'education',
                        store : eduStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
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
                        name : 'entrantsDate',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '状态',
                        labelStyle : 'text-align:right;',
                        name : 'state',
                        store : statStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                } ]
            }, {
                xtype : 'fieldset',
                title : '手工补录项',
                layout : 'column',
                items : [ {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '手机号码',
                        name : 'telephone',
                        vtype : 'mobile',
                        anchor : '90%'
                    } ]
                },/*{ columnWidth : .3, layout : 'form', items : [ {
                     store: levelStore, xtype : 'combo', name :
                      'level', hiddenName : 'level', fieldLabel :
                      '等级档次', valueField:'key', displayField:'value',
                      labelStyle: 'text-align:right;', mode : 'local',
                      typeAhead: true, forceSelection : true,
                      triggerAction: 'all', resizable:true,
                      emptyText:'请选择', selectOnFocus:true, width :
                      '100', anchor : '90%' }
                       ] },*/
                {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        store : custLevelStore,
                        xtype : 'combo',
                        name : 'custManagerLevel',
                        // hiddenName : 'custManagerLevel',
                        fieldLabel : '客户经理等级',
                        valueField : 'key',
                        displayField : 'value',
                        labelStyle : 'text-align:right;',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        resizable : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        width : '100',
                        anchor : '90%'
                    }]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        store : custTypeStore,
                        xtype : 'combo',
                        name : 'custManagerType',
                        id : 'msmgtyp',
                        // hiddenName : 'custManagerType',
                        fieldLabel : '客户经理类型',
                        labelStyle : 'text-align:right;',
                        valueField : 'key',
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        resizable : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        width : '100',
                        anchor : '90%'
                    }]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        store : isHavingCardStore,
                        xtype : 'combo',
                        name : 'isHavingCard',
                        hiddenName : 'isHavingCard',
                        fieldLabel : '是否有信贷证',
                        valueField : 'key',
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        labelStyle : 'text-align:right;',
                        resizable : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        width : '100',
                        anchor : '90%'
                    }]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        hidden:true,
                        width : 200,
                        fieldLabel : '隐藏字段',
                        labelStyle : 'text-align:right;',
                        name : 'userId',
                        anchor : '90%'
                    } ]
                } ]
            } ],
            buttons : [{
                text : '保  存',
                handler : function() {
                    Ext.Ajax.request( {
                        url : basepath + '/CustomerManagerInfoAction1.json',
                        method : 'POST',
                        params : editBasePlanForm
                                .getForm()
                                .getFieldValues(),
                        // form :
                        // editBasePlanForm.getForm().id,
                        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                        success : checkResult,
                        failure : checkResult
                    });
                    editPlanWindow.hide();
                    function checkResult(response) {
                        var resultArray = Ext.util.JSON
                                .decode(response.status);
                        var resultError = response.responseText;
                        if ((resultArray == 200 || resultArray == 201)&& resultError == '') {
                            Ext.Msg.alert('提示', '操作成功');
                            store.reload( {
                                params : {
                                    start : 0,
                                    limit : bbar.pageSize
                                }
                            });
                        } else {
                            if (resultArray == 403) {
                                Ext.Msg.alert('提示',
                                        response.responseText);
                            } else {
                                Ext.Msg.alert('提示','操作失败,失败原因:' + resultError);
                                store.reload( {
                                    params : {
                                        start : 0,
                                        limit : bbar.pageSize
                                    }
                                });
                            }
                        };
                    }
                }
            }, {
                text : '取  消',
                handler : function() {
                    editPlanWindow.hide();
                }
            } ]
        });

        // 展示详细信息窗口展示的from
        var showChanceForm = new Ext.form.FormPanel( {
            labelWidth : 140,
            height : 300,
            frame : true,
            region : 'center',
            autoScroll : true,
            buttonAlign : "center",
            items : [ {
                layout : 'column',
                items : [ {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        readOnly : true,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '机构号',
                        name : 'affiInstId',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        readOnly : true,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '客户经理ID',
                        name : 'custManagerId',
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
                        readOnly : true,
                        name : 'custManagerName',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '性别',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'sex',
                        store : sexStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        labelStyle : 'text-align:right;',
                        width : 200,
                        fieldLabel : '政治面貌',
                        readOnly : true,
                        name : 'politicalStat',
                        store : polStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                }/*
                     * ,{ columnWidth : .3, layout : 'form', items : [ { xtype :
                     * 'textfield', width : 200, labelStyle:
                     * 'text-align:right;', fieldLabel : '毕业学校', readOnly :
                     * true, name : 'graduateschool', anchor : '90%' } ] },{
                     * columnWidth : .3, layout : 'form', items : [ { xtype :
                     * 'textfield', width : 200, fieldLabel : '年龄', readOnly :
                     * true, labelStyle: 'text-align:right;', name : 'age',
                     * anchor : '90%' } ] }
                     */, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '职种',
                        readOnly : true,
                        labelStyle : 'text-align:right;',
                        name : 'businessType',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '岗位',
                        readOnly : true,
                        name : 'station',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        readOnly : true,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '经济工作年限',
                        name : 'economyWorkYear',
                        anchor : '90%'
                    } ]
                }/*
                     * ,{ columnWidth : .3, layout : 'form', items : [ { xtype :
                     * 'textfield', width : 200, readOnly : true, labelStyle:
                     * 'text-align:right;', hidden:true, fieldLabel :
                     * 'CRM系统所属机构', name : 'unitName', anchor : '90%' } ] }
                     */, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '学历',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'education',
                        store : eduStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '入行日期',
                        readOnly : true,
                        labelStyle : 'text-align:right;',
                        format : 'Y-m-d',
                        name : 'entrantsDate',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '状态',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'state',
                        store : statStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        readOnly : true,
                        labelStyle : 'text-align:right;',
                        fieldLabel : '手机号码',
                        name : 'telephone',
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        width : 200,
                        fieldLabel : '客户经理等级',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'custManagerLevel',
                        store : levelStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                }/*
                     * ,{ columnWidth : .3, layout : 'form', items : [ { xtype :
                     * 'textfield', width : 200, fieldLabel : 'CRM系统短信对象职位',
                     * labelStyle: 'text-align:right;', readOnly : true, name :
                     * 'CRMMailPosition', anchor : '90%' } ] }
                     */, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '客户经理类型',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'custManagerType',
                        store : custTypeStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                }, {
                    columnWidth : .3,
                    layout : 'form',
                    items : [ {
                        width : 200,
                        fieldLabel : '是否有信贷证',
                        labelStyle : 'text-align:right;',
                        readOnly : true,
                        name : 'isHavingCard',
                        store : isHavingCardStore,
                        xtype : 'combo',
                        valueField : 'key',
                        resizable : true,
                        displayField : 'value',
                        mode : 'local',
                        typeAhead : true,
                        forceSelection : true,
                        triggerAction : 'all',
                        emptyText : '请选择',
                        selectOnFocus : true,
                        anchor : '90%'
                    } ]
                } ]
            } ]
        });

        // 修改窗口展示的from
        var editPlanPanel = new Ext.Panel( {
            labelWidth : 140,
            height : 300,
            layout : 'fit',
            autoScroll : true,
            buttonAlign : "center",
            items : [ editBasePlanForm ]
        });

        // 定义修改窗口
        var editPlanWindow = new Ext.Window( {
            title : '客户经理信息修改',
            plain : true,
            layout : 'fit',
            width : 1000,
            height : 350,
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
            items : [ editPlanPanel ]
        });

        // 定义详细信息窗口
        var showChanceWindow = new Ext.Window( {
            title : '客户经理详细信息',
            plain : true,
            layout : 'fit',
            width : 1000,
            height : 350,
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
            constrain : true,
            animCollapse : true,
            buttonAlign : 'center',
            items : [ showChanceForm ],
            buttons : [ {
                text : '关闭',

                handler : function() {
                    showChanceWindow.hide();
                }
            } ]
        });

        // 展示修改窗口
        function editInit() {
            editPlanWindow.show();
        }

        // 展示详细信息窗口
        function showInit() {
            showChanceWindow.show();
        }

        var view = new Ext.Viewport( {

            layout : 'border',
            items : [ {
                region : 'center',
                id : 'center-panel',
                layout : 'fit',
                items : [ listPanel ]
            },

            {
                region : 'north',
                id : 'north-panel',
                height : 150,
                layout : 'fit',
                items : [ searchPanel ]
            }

            ]
        });
    });