Ext.onReady(function() {
	
	var record_newcustlink = Ext.data.Record.create
    (
    	[
    	 	{
				name : 'ncustId',
				mapping : 'NCUSTL_ID'
			},
    	 	{
    	 		name : 'reportId',
    	 		mapping : 'REPORT_ID'
    	 	}, 
    	 	{
    	 		name : 'custId',
    	 		mapping : 'CUST_ID'
    	 	},
    	 	{
    	 		name : 'custName',
    	 		mapping : 'CUST_NAME'
    	 	}, 
    	 	{
    	 		name : 'linkTime',
    	 		mapping : 'LINK_TIME'
    	 	},
    	 	{
    	 		name : 'linkContent',
    	 		mapping : 'LINK_CONTENT'
    	 	}
    	]
    );
    
    var record_oldcustlink = Ext.data.Record.create
    (
    	[
    	 	{
				name : 'ocustId',
				mapping : 'OCUSTL_ID'
			},
    	 	{
    	 		name : 'reportId',
    	 		mapping : 'REPORT_ID'
    	 	}, 
    	 	{
    	 		name : 'custId',
    	 		mapping : 'CUST_ID'
    	 	},
    	 	{
    	 		name : 'custName',
    	 		mapping : 'CUST_NAME'
    	 	}, 
    	 	{
    	 		name : 'linkTime',
    	 		mapping : 'LINK_TIME'
    	 	},
    	 	{
    	 		name : 'linkContent',
    	 		mapping : 'LINK_CONTENT'
    	 	}
    	]
    );
    
    var record_workcon = Ext.data.Record.create
    (
    	[
    	 	{
				name : 'wirkconId',
				mapping : 'WORKCON_ID'
			},
    	 	{
    	 		name : 'reportId',
    	 		mapping : 'REPORT_ID'
    	 	}, 
    	 	{
    	 		name : 'workContent',
    	 		mapping : 'WORK_CONTENT'
    	 	},
    	 	{
    	 		name : 'compStatus',
    	 		mapping : 'COMP_STATUS'
    	 	}
    	]
    );
    
    var record_tran = Ext.data.Record.create
    (
    	[
    	 	{
				name : 'tranId',
				mapping : 'TRAN_ID'
			},
    	 	{
    	 		name : 'reportId',
    	 		mapping : 'REPORT_ID'
    	 	}, 
    	 	{
    	 		name : 'custId',
    	 		mapping : 'CUST_ID'
    	 	},
    	 	{
    	 		name : 'produtId',
    	 		mapping : 'PRODUT_ID'
    	 	},
    	 	{
    	 		name : 'produtValue',
    	 		mapping : 'PRODUT_VALUE'
    	 	},
    	 	{
    	 		name : 'produtDate',
    	 		mapping : 'PRODUT_DATE'
    	 	}
    	]
    );
    
    var record_cloan = Ext.data.Record.create
    (
    	[
    	 	{
				name : 'cloanId',
				mapping : 'CLOAN_ID'
			},
    	 	{
    	 		name : 'reportId',
    	 		mapping : 'REPORT_ID'
    	 	}, 
    	 	{
    	 		name : 'custId',
    	 		mapping : 'CUST_ID'
    	 	},
    	 	{
    	 		name : 'checkContent',
    	 		mapping : 'CHECK_CONTENT'
    	 	},
    	 	{
    	 		name : 'checkDate',
    	 		mapping : 'CHECK_DATE'
    	 	},
    	 	{
    	 		name : 'checkResult',
    	 		mapping : 'CHECK_RESULT'
    	 	}
    	]
    );
    
    var record_sug = Ext.data.Record.create
    (
    	[
    	 	{
				name : 'sugId',
				mapping : 'SUG_ID'
			},
    	 	{
    	 		name : 'reportId',
    	 		mapping : 'REPORT_ID'
    	 	}, 
    	 	{
    	 		name : 'sugContent',
    	 		mapping : 'SUG_CONTENT'
    	 	}
    	]
    );
    
	
	
    Ext.QuickTips.init();
    var msFlag = 0;

        var reportType = new Ext.data.Store
        (
        	{
	            restful : true,
	            sortInfo : 
	            {
	                field : 'key',
	                direction : 'ASC'
	            },
	            autoLoad : true,
	            proxy : new Ext.data.HttpProxy
	            ( 
	            	{
	            		url : basepath + '/lookup.json?name=REPORT_TYPE'
	            	}
	            ),
	            reader : new Ext.data.JsonReader
	            ( 
	            	{
	            		root : 'JSON'
	            	}, 
	            	['key','value']
	            )
        });

        var jigou = new Com.yucheng.bcrm.common.OrgField( {
            searchType : 'ALLORG',/*
                                     * 指定查询机构范围属性
                                     * SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH
                                     * （所有父、祖机构）ALLORG（所有机构）
                                     */
            fieldLabel : '机构',
            roleType:'1',
            labelStyle : 'text-align:right;',
            id : 'jigouhao', // 放大镜组件ID，用于在重置清空时获取句柄
            name : 'AFFI_INST_ID',
            hiddenName : 'checkedNodes', // 后台获取的参数名称
            anchor : '95%',
            checkBox : true
        // 复选标志
                });
        
        // *****************************************
        var searchPanel = new Ext.form.FormPanel
        (
        	{
        		title : "工作报告查询",
        		labelWidth : 105,
        		labelAlign : 'right',
        		frame : true,
        		region : 'north',
        		// autoScroll : true,
        		layout : 'column',
        		items : 
        		[
					{
						columnWidth : .25,
						layout : 'form',
						items : 
						[ 
						  	{
					         	xtype : 'textfield',
					            fieldLabel : '客户经理ID',
					            id : 'CUST_MANAGER_ID',
					            name : 'CUST_MANAGER_ID',
					            anchor : '95%'
						  	},
						  	{
			                    store : reportType,
			                    xtype : 'combo',
			                    name : 'report_type',
			                    id : 'report_type',
			                    fieldLabel : '报告类型',
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
		                 	},
		                 	{
		                 		xtype : 'hidden',
		                 		id:'BIZ_CODE',
		                 		name:'BIZ_CODE',
		                 		value:'1'
		                 	}
						 ]
					}, 
        		 	{
        		 		columnWidth : .25,
        		 		layout : 'form',
        		 		items : 
        		 		[ 
        		 		  	{
        		 		  		xtype : 'textfield',
        		 		  		fieldLabel : '客户经理姓名',
        		 		  		id : 'USER_NAME',
        		 		  		name : 'cust_manager_name',
        		 		  		anchor : '95%'
        		 		  	},
        		 		  	{
    	 						xtype : 'datefield',
    	 						fieldLabel : '报告日期',
    	 						format:'Y-m-d',
    	 						name:'report_date',
    	 						labelStyle: 'text-align:right;',
    	 						anchor : '90%'
    		 				}  
        		 		 ]
        		 	}, 
        		 	{
        		 		columnWidth : .25,
        		 		layout : 'form',
        		 		items : 
        		 		[ 
        		 		  	jigou
        		 		]
        		 	}
        		 
        	],
            buttonAlign : 'center',
            buttons : 
            [ 
             	{
             		text : '查询',
             		handler : function() 
             		{
             			// var flag =
             			// submitValidate(orgTreePanel,searchPanel.getForm());
             			// if(!flag){
             			// return;
             			// }
             			if (!searchPanel.getForm().isValid()) 
             			{
             				Ext.Msg.alert("提醒", "请填写必填项");
             				return false;
             			}
             			//var ageD = Ext.getCmp("AGE").getValue();
             			// alert(ageD);
             			var ageD = 1;
             			if (ageD < 0 || ageD > 200) 
             			{
             				alert("年龄输入不合理，请重新输入！");
             				return;
             			} 
             			else 
             			{
             				var conditionStr = searchPanel.getForm().getFieldValues();
             				//alert(Ext.encode(conditionStr));
             				store.baseParams = 
             				{
             					"condition" : Ext.encode(conditionStr)
             				};
             				store.load
             				( 
             					{
             						params : 
             						{
             							start : 0,
             							limit : parseInt(pagesize_combo.getValue())
             						}
             					}
             				);

             			}
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
            dataIndex : 'reportId',
            sortable : true,
            hidden : true,
            width : 120
        }, {
            header : '客户经理ID',
            dataIndex : 'custManagerId',
            sortable : true,
            width : 120
        }, {
            header : '客户经理姓名',
            dataIndex : 'custManagerName'
        },{
            header : '报告周期',
            dataIndex : 'reportCycle'
        }, {
            header : '报告类型',
            dataIndex : 'reportType'
        },{
            header : '日期',
            dataIndex : 'reportDate'
        }]);
        
        
        var record = Ext.data.Record.create
        (
        	[
        	 	{
        	 		name : 'reportId',
        	 		mapping : 'REPORT_ID'
        	 	}, 
        	 	{
        	 		name : 'custManagerId',
        	 		mapping : 'CUST_MANAGER_ID'
        	 	},
        	 	{
        	 		name : 'custManagerName',
        	 		mapping : 'CUST_MANAGER_NAME'
        	 	}, 
        	 	{
        	 		name : 'reportCycle',
        	 		mapping : 'REPORT_CYCLE'
        	 	},
        	 	{
        	 		name : 'reportType',
        	 		mapping : 'REPORT_TYPE'
        	 	}, 
        	 	{
        	 		name : 'reportDate',
        	 		mapping : 'REPORT_DATE'
        	 	}
        	]
        );

        var store = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				//url : basepath + '/CustomerManagerInfoAction1.json',
        				url : basepath + '/WorkReportListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record
        		)
        	}
        );

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
        
        var store_newcustlink = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				url : basepath + '/ReportNewCustLinkListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record_newcustlink
        		)
        	}
        );
        
        var store_oldcustlink = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				url : basepath + '/ReportOldCustLinkListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record_oldcustlink
        		)
        	}
        );
        
        var store_workcon = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				url : basepath + '/ReportWorkConListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record_workcon
        		)
        	}
        );
        
        var store_tran = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				url : basepath + '/ReportTransactionListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record_tran
        		)
        	}
        );
        
        var store_cloan = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				url : basepath + '/ReportCheckLoanListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record_cloan
        		)
        	}
        );
        
        var store_sug = new Ext.data.Store
        (
        	{
        		restful : true,
        		proxy : new Ext.data.HttpProxy
        		(
        			{
        				url : basepath + '/ReportSugListQueryAction.json',
        				method : 'POST', 
        				success : function(response) 
        				{
        					//Ext.Msg.alert('提示', response.responseText); 
        				},
        				failure : function(response) 
        				{
        					Ext.Msg.alert('提示','加入失败' );
        				}
        			}
        		),
        		reader : new Ext.data.JsonReader
        		( 
        			{
        				successProperty : 'success',
        				// idProperty: 'ID',
        				// messageProperty: 'message',
        				root : 'json.data',
        				totalProperty : 'json.count'
        			}, 
        			record_sug
        		)
        	}
        );
        
        var listPanel = new Ext.grid.GridPanel(
                {

                    title : "工作报告",
                    store : store,
                    frame : true,
                    sm : sm,
                    cm : columns,
                    stripeRows : true,
                    tbar : [
                            {
                            	id:'viewCustomerManager',
                                text : '查看详细信息',
                                iconCls : 'detailIconCss',
                                handler : function() {
                                    // 得到选中记录
                                var selectRe = listPanel.getSelectionModel().getSelections()[0];

                                var selectLength = listPanel.getSelectionModel().getSelections().length;
                                if (selectLength != 1) 
                                {
                                    // alert('请选择一条记录');
                                    Ext.Msg.alert("提示", "请选择一条记录!");
                                } 
                                else 
                                {
                                	store_newcustlink.load
                            		( 
                            			{
                            				params : 
                            				{
                            					start : 0,
                            					limit : parseInt(pagesize_combo.getValue()),
                            					REPORT_ID:selectRe.data.reportId
                            				}
                            			}
                            		);
                                	store_oldcustlink.load
                            		( 
                            			{
                            				params : 
                            				{
                            					start : 0,
                            					limit : parseInt(pagesize_combo.getValue()),
                            					REPORT_ID:selectRe.data.reportId
                            				}
                            			}
                            		);
                                	store_workcon.load
                            		( 
                            			{
                            				params : 
                            				{
                            					start : 0,
                            					limit : parseInt(pagesize_combo.getValue()),
                            					REPORT_ID:selectRe.data.reportId
                            				}
                            			}
                            		);
                                	
                                	store_tran.load
                            		( 
                            			{
                            				params : 
                            				{
                            					start : 0,
                            					limit : parseInt(pagesize_combo.getValue()),
                            					REPORT_ID:selectRe.data.reportId
                            				}
                            			}
                            		);
                                	
                                	store_cloan.load
                            		( 
                            			{
                            				params : 
                            				{
                            					start : 0,
                            					limit : parseInt(pagesize_combo.getValue()),
                            					REPORT_ID:selectRe.data.reportId
                            				}
                            			}
                            		);
                                	
                                	store_sug.load
                            		( 
                            			{
                            				params : 
                            				{
                            					start : 0,
                            					limit : parseInt(pagesize_combo.getValue()),
                            					REPORT_ID:selectRe.data.reportId
                            				}
                            			}
                            		);
                                	
                                    showChanceForm.getForm().loadRecord(selectRe);
                                    showInit();
                                }
                            }
                            }
                            ],
                    region : 'center',
                    frame : true,
                    bbar : bbar,// 分页工具栏
                    viewConfig : {
                    // 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
                    },
                    loadMask : {
                        msg : '正在加载表格数据,请稍等...'
                    }
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
        
        //客户经理编辑中的机构控件
        var AFFI_INST_ID = new Com.yucheng.bcrm.common.OrgField
        (
        	{
        		searchType : 'ALLORG',
        		fieldLabel : '机构',
        		labelStyle : 'text-align:right;',
        		id : 'AFFI_INST_ID_EDIT', // 放大镜组件ID，用于在重置清空时获取句柄
        		name : 'AFFI_INST_ID',
        		hiddenName : 'checkedNodes', // 后台获取的参数名称
        		anchor : '95%',
        		checkBox : true
             }
        );

        // 展示详细信息窗口展示的from
        var showChanceForm = new Ext.form.FormPanel
        (
        	
        	
        	{
	            labelWidth : 140,
	            height : 300,
	            frame : true,
	            region : 'center',
	            autoScroll : true,
	            buttonAlign : "center",
	            items : 
	            [
	             	{
	             		layout : 'column',
	             		items : 
	             		[ 
		             		{
			                    columnWidth : .2,
			                    layout : 'form',
			                    items : 
			                    [
			                     	{
				                        xtype : 'textfield',
				                        width : 100,
				                        readOnly : true,
				                        labelStyle : 'text-align:right;',
				                        fieldLabel : '客户经理ID',
				                        name : 'custManagerId',
				                        anchor : '120%'
				                    }
			                     ]
			                }, 
			                {
			                    columnWidth : .2,
			                    layout : 'form',
			                    items : 
			                    [
			                     	{
				                        xtype : 'textfield',
				                        width : 100,
				                        readOnly : true,
				                        labelStyle : 'text-align:right;',
				                        fieldLabel : '客户经理姓名',
				                        name : 'custManagerName',
				                        anchor : '120%'
				                    } 
			                    ]
			                }, 
			                {
			                    columnWidth : .2,
			                    layout : 'form',
			                    items : 
			                    [ 
				                    {
				                        xtype : 'textfield',
				                        labelStyle : 'text-align:right;',
				                        width : 100,
				                        fieldLabel : '报告周期',
				                        readOnly : true,
				                        name : 'reportCycle',
				                        anchor : '120%'
				                    } 
				                ]
			                }, 
			                {
			                    columnWidth : .2,
			                    layout : 'form',
			                    items : 
			                    [
			                     	{
				                        xtype : 'textfield',
				                        width : 100,
				                        fieldLabel : '报告类型',
				                        labelStyle : 'text-align:right;',
				                        readOnly : true,
				                        name : 'reportType',
				                        anchor : '120%'
				                    } 
			                     ]
			                }, 
			                {
			                    columnWidth : .2,
			                    layout : 'form',
			                    items : 
			                    [ 
				                    {
				                        xtype : 'textfield',
				                        labelStyle : 'text-align:right;',
				                        width : 100,
				                        fieldLabel : '日期',
				                        readOnly : true,
				                        name : 'reportDate',
				                        anchor : '120%'
				                    }
				                ]
			                }
			           ]
	             	} 
	           ]
	        });

        
        
        
        var pagesize_combo_newcustlink = new Ext.form.ComboBox
        (
        	{
	            name : 'pagesize',
	            triggerAction : 'all',
	            mode : 'local',
	            store : new Ext.data.ArrayStore
	            (
                    {
                        fields : [ 'value', 'text' ],
                        data : 
                        [
                         	[ 10, '10条/页' ], 
                         	[ 20, '20条/页' ],
                            [ 50, '50条/页' ], 
                            [ 100, '100条/页' ],
                            [ 250, '250条/页' ], 
                            [ 500, '500条/页' ]
                        ]
                    }
                ),
		        valueField : 'value',
		        displayField : 'text',
		        value : '20',
		        forceSelection : true,
		        width : 85
        	}
        );
        
        var pagesize_combo_oldcustlink = new Ext.form.ComboBox
        (
        	{
	            name : 'pagesize',
	            triggerAction : 'all',
	            mode : 'local',
	            store : new Ext.data.ArrayStore
	            (
                    {
                        fields : [ 'value', 'text' ],
                        data : 
                        [
                         	[ 10, '10条/页' ], 
                         	[ 20, '20条/页' ],
                            [ 50, '50条/页' ], 
                            [ 100, '100条/页' ],
                            [ 250, '250条/页' ], 
                            [ 500, '500条/页' ]
                        ]
                    }
                ),
		        valueField : 'value',
		        displayField : 'text',
		        value : '20',
		        forceSelection : true,
		        width : 85
        	}
        );
        
        var pagesize_combo_workcon = new Ext.form.ComboBox
        (
        	{
	            name : 'pagesize',
	            triggerAction : 'all',
	            mode : 'local',
	            store : new Ext.data.ArrayStore
	            (
                    {
                        fields : [ 'value', 'text' ],
                        data : 
                        [
                         	[ 10, '10条/页' ], 
                         	[ 20, '20条/页' ],
                            [ 50, '50条/页' ], 
                            [ 100, '100条/页' ],
                            [ 250, '250条/页' ], 
                            [ 500, '500条/页' ]
                        ]
                    }
                ),
		        valueField : 'value',
		        displayField : 'text',
		        value : '20',
		        forceSelection : true,
		        width : 85
        	}
        );
        
        var pagesize_combo_tran = new Ext.form.ComboBox
        (
        	{
	            name : 'pagesize',
	            triggerAction : 'all',
	            mode : 'local',
	            store : new Ext.data.ArrayStore
	            (
                    {
                        fields : [ 'value', 'text' ],
                        data : 
                        [
                         	[ 10, '10条/页' ], 
                         	[ 20, '20条/页' ],
                            [ 50, '50条/页' ], 
                            [ 100, '100条/页' ],
                            [ 250, '250条/页' ], 
                            [ 500, '500条/页' ]
                        ]
                    }
                ),
		        valueField : 'value',
		        displayField : 'text',
		        value : '20',
		        forceSelection : true,
		        width : 85
        	}
        );
        
        var pagesize_combo_cloan = new Ext.form.ComboBox
        (
        	{
	            name : 'pagesize',
	            triggerAction : 'all',
	            mode : 'local',
	            store : new Ext.data.ArrayStore
	            (
                    {
                        fields : [ 'value', 'text' ],
                        data : 
                        [
                         	[ 10, '10条/页' ], 
                         	[ 20, '20条/页' ],
                            [ 50, '50条/页' ], 
                            [ 100, '100条/页' ],
                            [ 250, '250条/页' ], 
                            [ 500, '500条/页' ]
                        ]
                    }
                ),
		        valueField : 'value',
		        displayField : 'text',
		        value : '20',
		        forceSelection : true,
		        width : 85
        	}
        );
        
        var pagesize_combo_sug = new Ext.form.ComboBox
        (
        	{
	            name : 'pagesize',
	            triggerAction : 'all',
	            mode : 'local',
	            store : new Ext.data.ArrayStore
	            (
                    {
                        fields : [ 'value', 'text' ],
                        data : 
                        [
                         	[ 10, '10条/页' ], 
                         	[ 20, '20条/页' ],
                            [ 50, '50条/页' ], 
                            [ 100, '100条/页' ],
                            [ 250, '250条/页' ], 
                            [ 500, '500条/页' ]
                        ]
                    }
                ),
		        valueField : 'value',
		        displayField : 'text',
		        value : '20',
		        forceSelection : true,
		        width : 85
        	}
        );
        // 改变每页显示条数reload数据
        pagesize_combo_newcustlink.on
        (
        	"select", 
        	function(comboBox) 
        	{
        		bbar_newcustlink.pageSize = parseInt
        		(
        			pagesize_combo_newcustlink.getValue()
        		), 
        		store_newcustlink.reload
    			(
    				{
    					params : 
    					{
    						start : 0,
    						limit : parseInt(pagesize_combo_newcustlink.getValue())
    					}
    				}
    			)
        	}
        );
        
        pagesize_combo_oldcustlink.on
        (
        	"select", 
        	function(comboBox) 
        	{
        		bbar_oldcustlink.pageSize = parseInt
        		(
        			pagesize_combo_oldcustlink.getValue()
        		), 
        		store_oldcustlink.reload
    			(
    				{
    					params : 
    					{
    						start : 0,
    						limit : parseInt(pagesize_combo_oldcustlink.getValue())
    					}
    				}
    			)
        	}
        );
        
        pagesize_combo_workcon.on
        (
        	"select", 
        	function(comboBox) 
        	{
        		bbar_workcon.pageSize = parseInt
        		(
        			pagesize_combo_workcon.getValue()
        		), 
        		store_workcon.reload
    			(
    				{
    					params : 
    					{
    						start : 0,
    						limit : parseInt(pagesize_combo_workcon.getValue())
    					}
    				}
    			)
        	}
        );
        
        pagesize_combo_tran.on
        (
        	"select", 
        	function(comboBox) 
        	{
        		bbar_tran.pageSize = parseInt
        		(
        			pagesize_combo_tran.getValue()
        		), 
        		store_tran.reload
    			(
    				{
    					params : 
    					{
    						start : 0,
    						limit : parseInt(pagesize_combo_tran.getValue())
    					}
    				}
    			)
        	}
        );
        
        pagesize_combo_cloan.on
        (
        	"select", 
        	function(comboBox) 
        	{
        		bbar_cloan.pageSize = parseInt
        		(
        			pagesize_combo_cloan.getValue()
        		), 
        		store_cloan.reload
    			(
    				{
    					params : 
    					{
    						start : 0,
    						limit : parseInt(pagesize_combo_cloan.getValue())
    					}
    				}
    			)
        	}
        );
        
        pagesize_combo_sug.on
        (
        	"select", 
        	function(comboBox) 
        	{
        		bbar_sug.pageSize = parseInt
        		(
        			pagesize_combo_sug.getValue()
        		), 
        		store_sug.reload
    			(
    				{
    					params : 
    					{
    						start : 0,
    						limit : parseInt(pagesize_combo_sug.getValue())
    					}
    				}
    			)
        	}
        );
        // 分页工具栏
//        var bbar_newcustlink = new Ext.PagingToolbar
//        (
//        	{
//	            pageSize : parseInt(pagesize_combo_newcustlink.getValue()),
//	            store : store_newcustlink,
//	            displayInfo : true,
//	            displayMsg : '显示{0}条到{1}条,共{2}条',
//	            emptyMsg : "没有符合条件的记录",
//	            items : [ '-', '&nbsp;&nbsp;', pagesize_combo_newcustlink ]
//	        }
//        );
//        
//        var bbar_oldcustlink = new Ext.PagingToolbar
//        (
//        	{
//	            pageSize : parseInt(pagesize_combo_oldcustlink.getValue()),
//	            store : store_oldcustlink,
//	            displayInfo : true,
//	            displayMsg : '显示{0}条到{1}条,共{2}条',
//	            emptyMsg : "没有符合条件的记录",
//	            items : [ '-', '&nbsp;&nbsp;', pagesize_combo_oldcustlink ]
//	        }
//        );
//        
//        var bbar_workcon = new Ext.PagingToolbar
//        (
//        	{
//	            pageSize : parseInt(pagesize_combo_workcon.getValue()),
//	            store : store_workcon,
//	            displayInfo : true,
//	            displayMsg : '显示{0}条到{1}条,共{2}条',
//	            emptyMsg : "没有符合条件的记录",
//	            items : [ '-', '&nbsp;&nbsp;', pagesize_combo_workcon ]
//	        }
//        );
//        
//        var bbar_tran = new Ext.PagingToolbar
//        (
//        	{
//	            pageSize : parseInt(pagesize_combo_tran.getValue()),
//	            store : store_tran,
//	            displayInfo : true,
//	            displayMsg : '显示{0}条到{1}条,共{2}条',
//	            emptyMsg : "没有符合条件的记录",
//	            items : [ '-', '&nbsp;&nbsp;', pagesize_combo_tran ]
//	        }
//        );
//        
//        var bbar_cloan = new Ext.PagingToolbar
//        (
//        	{
//	            pageSize : parseInt(pagesize_combo_cloan.getValue()),
//	            store : store_cloan,
//	            displayInfo : true,
//	            displayMsg : '显示{0}条到{1}条,共{2}条',
//	            emptyMsg : "没有符合条件的记录",
//	            items : [ '-', '&nbsp;&nbsp;', pagesize_combo_cloan ]
//	        }
//        );
//        
//        var bbar_sug = new Ext.PagingToolbar
//        (
//        	{
//	            pageSize : parseInt(pagesize_combo_sug.getValue()),
//	            store : store_sug,
//	            displayInfo : true,
//	            displayMsg : '显示{0}条到{1}条,共{2}条',
//	            emptyMsg : "没有符合条件的记录",
//	            items : [ '-', '&nbsp;&nbsp;', pagesize_combo_sug ]
//	        }
//        );
        
        var showChanceWindow = new Ext.Window
        (
        	{
        		layout : 'border',
        		title : '工作报告',
        		plain : true,
        		width : 1150,
        		height : 475,
        		resizable : true,
        		draggable : true,
        		closable : true,
        		closeAction : 'hide',
        		modal : true,
        		maximizable : true,
        		collapsible : true,
        		titleCollapse : true,
        		border : false,
        		constrain : true,
        		animCollapse : true,
	            buttonAlign : 'center',
	            items : 
	            [
					{
					    region : 'north',
					    id : 'north-windows',
					    height : 40,
					    layout : 'fit',
					    items : 
					    [
					     	showChanceForm
					    ]
					},
	             	{
	             		region : 'center',
	             		id : 'center-panel',
	             		layout : 'border',
	             		height : 600,
	             		autoScroll:true,
	             		items : 
	             		[
	             		 	{
	             		 		region : 'north',
	    					    id : 'center-north',
	    					    height : 125,
	    					    layout : 'border',
				                items : 
			                	[
			             		 	{
			             		 		region : 'west',
			    					    id : 'center-north-west',
			    					    width: 530,
			    					    title : '新客户或目标客户联系和拜访',
			    					    xtype: 'grid',
			    					    store: store_newcustlink,//修改
			    					    frame: true,
			    					    //sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			    					    stripeRows : true,
										colModel: new Ext.grid.ColumnModel
										(
											{
												defaults: 
												{
													width: 120,
													sortable: true
												},
												columns: 
												[
												 	rownum,
												 	{
														header : '隐藏id',
														dataIndex : 'ncustId',
														sortable : true,
														hidden : true,
														width : 120
													},
												 	{
														header : '隐藏id',
														dataIndex : 'reportId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
									        	 		header : '客户号',
											            dataIndex : 'custId',
											            sortable : true,
											            width : 120
											        }, 
											        {
											            header : '客户姓名',
											            dataIndex : 'custName'
											        },
											        {
											            header : '联系日期',
											            dataIndex : 'linkTime'
											        }, 
											        {
											            header : '联系内容',
											            dataIndex : 'linkContent'
											        }
												]
											}
										),
										viewConfig: 
										{
											
										},
										iconCls: 'icon-grid',
				             		 	loadMask :
				             		 	{
				                            msg : '正在加载表格数据,请稍等...'
				                        },
				                        //bbar : bbar_newcustlink//修改
			             		 	},
			             		 	{
			             		 		region : 'center',
			    					    id : 'center-north-center',
			    					    title : '老客户日常联系',
			    					    xtype: 'grid',
			    					    store: store_oldcustlink,//修改
			    					    frame: true,
			    					    //sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			    					    stripeRows : true,
										colModel: new Ext.grid.ColumnModel
										(
											{
												defaults: 
												{
													width: 120,
													sortable: true
												},
												columns: 
												[
												 	rownum,
												 	{
														header : '隐藏id',
														dataIndex : 'ocustId',
														sortable : true,
														hidden : true,
														width : 120
													},
												 	{
														header : '隐藏id',
														dataIndex : 'reportId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
									        	 		header : '客户号',
											            dataIndex : 'custId',
											            sortable : true,
											            width : 120
											        }, 
											        {
											            header : '客户姓名',
											            dataIndex : 'custName'
											        },
											        {
											            header : '联系日期',
											            dataIndex : 'linkTime'
											        }, 
											        {
											            header : '联系内容',
											            dataIndex : 'linkContent'
											        }
												]
											}
										),
										viewConfig: 
										{
											
										},
										iconCls: 'icon-grid',
				             		 	loadMask :
				             		 	{
				                            msg : '正在加载表格数据,请稍等...'
				                        }
				                        //bbar : bbar_oldcustlink//修改
			             		 	}
			             		]
	             		 	},
	             		 	{
	             		 		region : 'center',
	    					    id : 'center-center',
	    					    height : 200,
	    					    layout : 'border',
	    					    items : 
			                	[
			             		 	{
			             		 		region : 'west',
			    					    id : 'center-center-west',
			    					    title : '月度、周计划进度和完成情况（截至当日情况）',
			    					    width: 530,
			    					    xtype: 'grid',
			    					    store: store_workcon,//修改
			    					    frame: true,
			    					    //sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			    					    stripeRows : true,
										colModel: new Ext.grid.ColumnModel
										(
											{
												defaults: 
												{
													width: 120,
													sortable: true
												},
												columns: 
												[
												 	rownum,
												 	{
														header : '隐藏id',
														dataIndex : 'wirkconId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
														header : '隐藏id',
														dataIndex : 'reportId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
									        	 		header : '任务内容',
											            dataIndex : 'workContent',
											            sortable : true,
											            width : 120
											        }, 
											        {
											            header : '完成度',
											            dataIndex : 'compStatus'
											        }
												]
											}
										),
										viewConfig: 
										{
											
										},
										iconCls: 'icon-grid',
				             		 	loadMask :
				             		 	{
				                            msg : '正在加载表格数据,请稍等...'
				                        }
				                        //bbar : bbar_workcon//修改
			             		 	},
			             		 	{
			             		 		region : 'center',
			    					    id : 'center-center-center',
			    					    title : '达成交易',
			    					    xtype: 'grid',
			    					    store: store_tran,//修改
			    					    frame: true,
			    					    //sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			    					    stripeRows : true,
										colModel: new Ext.grid.ColumnModel
										(
											{
												defaults: 
												{
													width: 120,
													sortable: true
												},
												columns: 
												[
												 	rownum,
												 	{
														header : '隐藏id',
														dataIndex : 'tranId',
														sortable : true,
														hidden : true,
														width : 120
													},
												 	{
														header : '隐藏id',
														dataIndex : 'reportId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
									        	 		header : '交易客户',
											            dataIndex : 'custId',
											            sortable : true,
											            width : 120
											        }, 
											        {
											            header : '交易产品',
											            dataIndex : 'produtId'
											        },
											        {
											            header : '交易额度',
											            dataIndex : 'produtValue'
											        }, 
											        {
											            header : '交易时间',
											            dataIndex : 'produtDate'
											        }
												]
											}
										),
										viewConfig: 
										{
											
										},
										iconCls: 'icon-grid',
				             		 	loadMask :
				             		 	{
				                            msg : '正在加载表格数据,请稍等...'
				                        }
				                        //bbar : bbar_tran//修改
			             		 	}
			             		]
	             		 	},
	             		 	{
	             		 		region : 'south',
	    					    id : 'center-south',
	    					    height : 125,
	    					    layout : 'border',
	    					    items : 
			                	[
			             		 	{
			             		 		region : 'west',
			    					    id : 'center-south-west',
			    					    width: 530,
			    					    title : '贷后检查工作',
			    					    xtype: 'grid',
			    					    store: store_cloan,//修改
			    					    frame: true,
			    					    //sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			    					    stripeRows : true,
										colModel: new Ext.grid.ColumnModel
										(
											{
												defaults: 
												{
													width: 120,
													sortable: true
												},
												columns: 
												[
												 	rownum,
												 	{
														header : '隐藏id',
														dataIndex : 'cloanId',
														sortable : true,
														hidden : true,
														width : 120
													},
												 	{
														header : '隐藏id',
														dataIndex : 'reportId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
									        	 		header : '贷款客户',
											            dataIndex : 'custId',
											            sortable : true,
											            width : 120
											        }, 
											        {
											            header : '工作检查内容',
											            dataIndex : 'checkContent'
											        },
											        {
											            header : '检查时间',
											            dataIndex : 'checkDate'
											        }, 
											        {
											            header : '检查结果',
											            dataIndex : 'checkResult'
											        }
												]
											}
										),
										viewConfig: 
										{
											
										},
										iconCls: 'icon-grid',
				             		 	loadMask :
				             		 	{
				                            msg : '正在加载表格数据,请稍等...'
				                        }
				                        //bbar : bbar_cloan//修改
			             		 	},
			             		 	{
			             		 		region : 'center',
			    					    id : 'center-south-center',
			    					    title : '关注和需要解决的问题以及对总支行的建议',
			    					    xtype: 'grid',
			    					    store: store_sug,//修改
			    					    frame: true,
			    					    //sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			    					    stripeRows : true,
										colModel: new Ext.grid.ColumnModel
										(
											{
												defaults: 
												{
													width: 120,
													sortable: true
												},
												columns: 
												[
												 	rownum,
												 	{
														header : '隐藏id',
														dataIndex : 'reportId',
														sortable : true,
														hidden : true,
														width : 120
													},
													{
									        	 		header : '建议和问题内容',
											            dataIndex : 'custId',
											            sortable : true,
											            width : 120
											        }
												]
											}
										),
										viewConfig: 
										{
											
										},
										iconCls: 'icon-grid',
				             		 	loadMask :
				             		 	{
				                            msg : '正在加载表格数据,请稍等...'
				                        }
				                        //bbar : bbar_sug//修改
			             		 	}
			             		]
	             		 	}
	             		]
	             	}
	            ],
	            buttons : 
	            [
	             	{
	             		text : '关闭',
	             		handler : function() 
	             		{
	             			showChanceWindow.hide();
	             		}
	             	}
	            ]
        	}	
        );
        

        

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