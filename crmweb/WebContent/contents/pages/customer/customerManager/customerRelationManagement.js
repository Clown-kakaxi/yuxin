/**
 * 客户关系管理
 */
Ext.onReady(function() {
	var boxstore13 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=KHQYGM'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore8 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=HYFL'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore5 = new Ext.data.Store({  
		
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querycustomerbase.json'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'ID', 'CUST_BASE_NAME' ])
		});
	var boxstore6 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/queryrollcustomer.json'
				}),
				reader : new Ext.data.JsonReader({
					root : 'json.data'
				}, [ 'ROLL_ID', 'ROLL_NAME' ])
			});
	var boxstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CIFKHZT'
			/*	,
				success : function(response) {
					Ext.Msg.alert('提示', response.responseText);
				},
				failure : function(response) {
					Ext.Msg.alert('提示','加入失败' );
				}*/
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore16 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=CUST_BIG_LEV'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
var boxstore2 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=QYGM'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore17 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=CUST_SMALL_LEV'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
	 var boxstore20= new Ext.data.Store({  
	     	sortInfo: {
	     	    field: 'key',
	     	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	     	},

		            restful:true,   
		            autoLoad :true,
		            proxy : new Ext.data.HttpProxy({
		                    url :basepath+'/lookup.json?name=CUST_LEVEL4'
		                }),
		                reader : new Ext.data.JsonReader({
		                    root : 'JSON'
		                }, [ 'key', 'value' ])
		            });
	 var boxstore3 = new Ext.data.Store({  
			sortInfo: {
        	    field: 'key',
        	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
        	},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=CUST_RELATION'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
/*	var boxstore3 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0'],['子公司', '1'], ['分公司', '2'], ['供应商', '3'], ['经销商', '4'], ['持股关系', '5'],['竞争对手', '6'], ['合作关系', '7']]
	});*/
/*	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['一级子公司', '0001'], ['二级子公司', '0002'], ['三级子公司', '0003'], ['四级子公司', '0004'], ['五级子公司', '0005'], ['六级子公司', '0006'], ['七级子公司', '0007'], ['八级子公司', '0008'], ['九级子公司', '0009'], ['十级子公司', '0010']]
	});
	var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0'],['<--上下级(游)', '1'], ['<-->合作关系', '2']]
	});

	var boxstore4 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['正式', '0001'], ['注销', '0002'], ['全部', '0003']]
			});
	var boxstore8 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中型', '0002'], ['小型', '0003']]
			});
	var boxstore6 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});
	var boxstore7 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var boxstore5 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0'],['客户群1', '1'], ['客户群2', '2'], ['客户群3', '3'], ['客户群4', '4']]
			});
		var boxstore9 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
		var boxstore10 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});*/
	var windowsm= new Ext.grid.CheckboxSelectionModel(); 

	// 定义自动当前页行号
	var windowrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var windowcm = new Ext.grid.ColumnModel([windowrownum,windowsm,
	         {
				header : '关联客户ID', // 列标题
				dataIndex : 'CUST_ID', // 数据索引:和Store模型对应
				hidden : true,// 是否可排序
				width:200
            },{
				header : '关联客户组织机构代码',
				dataIndex : 'CUST_ZZDM',
				sortable : true,
				width : 150
			},{
				header : '关联客户名称', // 列标题
				dataIndex : 'CUST_ZH_NAME', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width:200
		    }
			]);

	/**
	 * 数据存储
	 */
	var windowstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerdescription.json'}),
        reader: new Ext.data.JsonReader({
        totalProperty : 'json.count',
        root:'json.data'
        }, [
			{name: 'CUST_ID'},
			{name: 'CUST_ZH_NAME'},
			{name: 'CUST_ZZDM'}
		])
	});
	windowstore.on('beforeload', function() {
	    	var conditionStr =  form.getForm().getValues(false);
	        this.baseParams = {
	                "condition":Ext.encode(conditionStr)
	        };
	    });
	 var windowpagesize_combo = new Ext.form.ComboBox({
	        name : 'pagesize',
	        triggerAction : 'all',
	        mode : 'local',
	        store : new Ext.data.ArrayStore({
	            fields : ['value', 'text'],
	            data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
	        }),
	        valueField : 'value',
	        displayField : 'text',
	        value : '100',
	        editable : false,
	        width : 85
	    });
	var windownumber = parseInt(windowpagesize_combo.getValue());
	var windowbbar = new Ext.PagingToolbar({
	       pageSize : windownumber,
	       store : windowstore,
	       displayInfo : true,
	       displayMsg : '显示{0}条到{1}条,共{2}条',
	       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	       emptyMsg : "没有符合条件的记录",
	       items : ['-', '&nbsp;&nbsp;', windowpagesize_combo
	                ]
	   });
		

	   // 改变每页显示条数reload数据
	windowpagesize_combo.on("select", function(comboBox) {
		//var size = windowpagesize_combo.getValue();
		windowbbar.pageSize = parseInt(windowpagesize_combo.getValue()),
		windowstore.reload({
           params : {
               start : 0,
               limit : parseInt(windowpagesize_combo.getValue())
           }
       });
	});
	var windowgrid = new Ext.grid.GridPanel({
				height : document.body.scrollHeight-248,
				frame : true,
				autoScroll : true,
				store : windowstore, // 数据存储
				stripeRows : true, // 斑马线
				sm : windowsm,
				bbar:windowbbar,
				cm : windowcm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	// 定义自动当前页行号
	var windowrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var sm2= new Ext.grid.CheckboxSelectionModel(); 

	// 定义列模型
	var cm2 = new Ext.grid.ColumnModel([windowrownum,sm2,
	     {header : 'ID', dataIndex : 'ID',sortable : true,width : 150,hidden:true},
         {header : '目标客户ID', dataIndex : 'DEST_CUST_ID',sortable : true,width : 150,hidden:true},
         {header : '目标客户组织机构代码',dataIndex : 'DEST_CUST_ZZDM',sortable : true,width : 150}, 
         {header : '目标客户名称', dataIndex : 'DEST_CUST_NAME',sortable : true,width : 150},
	     {header : '关联客户ID',dataIndex : 'RELA_CUST_ID',sortable : true,width : 150,hidden:true},
	     {header : '关联客户组织机构代码',dataIndex : 'RELA_CUST_ZZDM'},
         {header : '关联客户名称',dataIndex : 'RELA_CUST_NAME'}, 
		 {header : '关系名称',dataIndex : 'RELA_NAME_ORA'},
		 {header : '关系描述',dataIndex : 'RELA_DESC'},
		 {header : '持股比例 ',dataIndex : 'SH_PCT',renderer:function(value){
	            if(value=='0'){
	                return "";
	            }else{
	                return value+"%";
	            }
	        }},
		 {header : '创建人ID',dataIndex : 'CREATOR',hidden:true},
		 {header : '创建人姓名',dataIndex : 'CREATOR_NAME',hidden:true},
		 {header : '创建时间',dataIndex : 'CREAT_DATE',hidden:true}
		]);

	/**
	 * 数据存储
	 */
	var store2 = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/queryviewandgrid2.json'

	  }
	  ),
       reader: new Ext.data.JsonReader({
        totalProperty : 'json.count',
        root:'json.data'
        }, [
            {name: 'ID'},
			{name: 'DEST_CUST_ID'},
			{name: 'DEST_CUST_NAME'},
			{name: 'DEST_CUST_ZZDM'},
			{name: 'RELA_CUST_ID'},
			{name: 'RELA_CUST_NAME'},
			{name: 'RELA_CUST_ZZDM'},
			{name: 'RELA_NAME_ORA'},
			{name: 'RELA_DESC'},
			{name: 'SH_PCT'}
			//{name: 'CREATOR'},
			//{name: 'CREATOR_NAME'},
			//{name: 'CREAT_DATE'}
		])
	});
	  
    store2.on('beforeload', function() {
    	this.baseParams = {
    	 customerId: Ext.getCmp('CUST_ID').getValue()
    	 };
    //alert(Ext.getCmp('CUST_ID').getValue());
        
});

	var tbar2 = new Ext.Toolbar({
		items : [{
			text : '客户间关系新增',
			
			handler : function() {
				batchAdd();
			}
		}
		/*, '-', {
			text : '客户间关系修改',
			
			handler : function() {
				editInit();
			}
		}*/
		, '-', {
			text : '客户间关系删除',
			
			handler : function() {
				batchDelete();
			}
		}
		]
	});
/* var pageCombo = new Ext.form.ComboBox({
							        name : 'pagesize',
							        triggerAction : 'all',
							        mode : 'local',
							        store : new Ext.data.ArrayStore({
							            fields : ['value', 'text'],
							            data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
							        }),
							        valueField : 'value1',
							        displayField : 'text',
							        value : '10',
							        editable : false,
							        width : 85,
							        listeners:{
							        	'select':function(combo){							        	
							        		store2.removeAll();
							        		store2.load({
							        			params:{
							        				start:0,
							        				limit:combo.value
							        			}
							        		});
							        	}
							        }
							    });*/
    var pagesize_combo2 = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '100',
        editable : false,
        width : 85
    });
    var number2 = parseInt(pagesize_combo2.getValue());
 // 改变每页显示条数reload数据
    pagesize_combo2.on("select", function(pagesize_combo2) {
    	bbar2.pageSize = parseInt(pagesize_combo2.getValue()),
        store2.reload({
            params : {
                start : 0,
                limit : parseInt(pagesize_combo2.getValue())
            }
        });
    });
	var bbar2 = new Ext.PagingToolbar({
       pageSize : number2,
       store : store2,
       displayInfo : true,
       displayMsg : '显示{0}条到{1}条,共{2}条',
       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
       emptyMsg : "没有符合条件的记录",
       items : ['-', '&nbsp;&nbsp;', pagesize_combo2
                ]
   });
	var grid2 = new Ext.grid.GridPanel({
				height :document.body.scrollHeight-58,
				frame : true,
				autoScroll : true,
				store : store2, // 数据存储
				stripeRows : true, // 斑马线
				sm : sm2,
				cm : cm2, // 列模型
				tbar:tbar2,
				bbar:bbar2,
					/*new Ext.PagingToolbar({
			       pageSize : pageCombo.getValue(),
			       store : store2,
			       displayInfo : true,
			       displayMsg : '显示{0}条到{1}条,共{2}条',
			       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			       emptyMsg : "没有符合条件的记录",
			       items : ['-', '&nbsp;&nbsp;', pageCombo
			                ]
			   	}),*/
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	
	var windowForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'left', // 标签对齐方式
		//collapsible : true,// 是否可收缩
		buttonAlign : 'center',
		height : 110,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '目标客户名称',
											name : 'CUST_ZH_NAME',
											id : 'CUST_ZH_NAME',
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '100%'
										},{ 
											xtype : 'textfield',
											hidden :true,
											fieldLabel : 'CUST_ID',
											Width:'100',
											id : 'CUST_ID',
											name : 'CUST_ID',
											anchor : '90%'
										}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [new Ext.form.ComboBox({
									hiddenName : 'RELA_NAME',
									id:'RELANAME',
									fieldLabel : '关系名称',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore3,
									displayField : 'value',
									valueField : 'key',
									 allowBlank : false,
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '100%'
								}),{ 
									xtype : 'textfield',
									hidden :true,
									fieldLabel : 'CUST_ZZDM',
									Width:'100',
									id : 'CUST_ZZDM',
									name : 'CUST_ZZDM',
									anchor : '90%'
								}]
							},{
								columnWidth : .34,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
			                        xtype: 'compositefield',
			                        fieldLabel: '持股比例',
			                        id:'cgbl',
			                        hidden:true,
			                        labelStyle: 'text-align:right;',
			                        combineErrors: false,
			                        items: [
			                           {
			                               name : 'SH_PCT',
			                               id : 'SH_PCT',
			                               xtype: 'numberfield',
			                               maxValue:100,
			                            	anchor : '99%'
			                           }/*,
			                           {
			                               xtype: 'displayfield',
			                               value: '%'
			                           }*/
			                        ]
			                    }]
							}
					]
				},
				{
					layout:'form',
					items:{
						labelStyle: 'text-align:right;',
						name : 'rela_desc',
						id : 'RELA_DESC',
						anchor:'80%',
						xtype:'textarea',
						fieldLabel : '关系描述'
					}
				}]
	});
	Ext.getCmp('RELANAME').on('select', function() {
		//alert('sdfsdfsdf');
		if(
		Ext.getCmp('RELANAME').value=="5"){
		Ext.getCmp('cgbl').setVisible(true);}
		else Ext.getCmp('cgbl').setVisible(false);
		//alert('sdfsdfds');
			//debugger;
			//Ext.getCmp('cgbl').setVisible(false);
			//
		//debugger;
		
	});
	var form = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'left', // 标签对齐方式
		//collapsible : true,// 是否可收缩
		buttonAlign : 'center',
		height : 80,
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .33,
				layout : 'form',
				labelWidth : 80, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
							fieldLabel : '客户名称',
							name : 'cust_zh_name',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '98%'
						}
					]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : 80, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
							fieldLabel : '组织机构代码', // 标签
							name : 'cust_zzdm', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '98%' // 宽度百分比
						}]
			}]
		}],
				buttons : [{
					text : '查询',
					handler : function() {
					
					
						windowstore.reload({
							  params : {
                                 start : 0,
                                 limit : windowbbar.pageSize }} );}
				}, {
					text : '重置',
						handler : function() {
						form.getForm().reset(); }
				}]
		
	});


	var addRoleWindow = new Ext.Window(
			{
				//layout : 'fit',
				width : 1020,
				height : 420,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal">客户间关系维护</span>',
				// iconCls : 'page_addIcon',
				maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .53,
						layout : 'form',
						border : false,
						items : [windowForm,form,windowgrid]}, {
							columnWidth : .47,
							layout : 'form',
							border : false,
							items : [grid2]
						}]
				}],
				//listeners: {'hide':{fn: makesure}},
				buttons : [
						{
							text : '关闭',
						
							handler : function() {
								addRoleWindow.hide();
								windowForm.getForm().reset();
								Ext.getCmp('cgbl').setVisible(false);
							}
						} ]
			});

	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 125,
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
							fieldLabel : '客户名称',
							name : 'CUST_ZH_NAME',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
			          	},new Ext.form.ComboBox({
							hiddenName : 'HY_CLASS',
							fieldLabel : '行业',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore8,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
						}),new Ext.form.ComboBox({
							hiddenName : 'ROLL_NAME',
							fieldLabel : '客户名单',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore6,
							displayField : 'ROLL_NAME',
							valueField : 'ROLL_ID',
							mode : 'local',
							forceSelection : true,
							emptyText:'请选择',
							typeAhead : true,
							resizable : true,
							anchor : '90%'
						})
					]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '组织机构代码', // 标签
					name : 'CUST_ZZDM', // name:后台根据此name属性取值
					allowBlank : true, // 是否允许为空
					labelStyle: 'text-align:right;',
					anchor : '90%' // 宽度百分比
				},
				new Ext.form.ComboBox({
                    hiddenName : 'CUST_LEV',
                    fieldLabel : '客户评级',
                    labelStyle: 'text-align:right;',
                    triggerAction : 'all',
                    store : boxstore20,
                    displayField : 'value',
                    valueField : 'key',
                    mode : 'local',
                    forceSelection : true,
                    typeAhead : true,
                    emptyText:'请选择',
                    resizable : true,
                    anchor : '90%'
            })]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [new Ext.form.ComboBox({
					hiddenName : 'STS',
					fieldLabel : '客户状态',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				}),new Ext.form.ComboBox({
					hiddenName : 'CRM_SCOPE',
					fieldLabel : '考核口径客户规模',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore13,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [new Ext.form.ComboBox({
					hiddenName : 'CUST_SCOPE',
					fieldLabel : '客户规模',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore2,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
			}),new Ext.form.ComboBox({
				hiddenName : 'CUST_BASE_NAME',
				fieldLabel : '客户群组',
				labelStyle: 'text-align:right;',
				triggerAction : 'all',
				store : boxstore5,
				displayField : 'CUST_BASE_NAME',
				valueField : 'ID',
				mode : 'local',
				forceSelection : true,
				typeAhead : true,
				emptyText:'请选择',
				resizable : true,
				anchor : '90%'
			})]
			}]
		}],
	buttons : [{
				text : '查询',
				handler : function() {
				 		store.reload({
							  params : {
                                   start : 0,
                                   limit : bbar.pageSize }} );
				}
			}, {
				text : '重置',
				handler : function() {
					qForm.getForm().reset(); 
				}
				}]
	});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true}); 

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,
	         {header : 'id',dataIndex : 'CUST_ID',sortable : true,width : 200,hidden :true},
	         {header : '组织机构代码',dataIndex : 'CUST_ZZDM',sortable : true},
	         {header : '客户名称',dataIndex : 'CUST_ZH_NAME',sortable : true,width : 200},
	         {header : '行业',dataIndex : 'HY_CLASS_GP',sortable : true},
	         {header : '客户状态',dataIndex : 'STS',sortable : true},
	         {header : '客户规模',dataIndex : 'CUST_SCOPE_GP',sortable : true},
	         {header : '考核口径客户规模',dataIndex : 'CRM_SCOPE_GP',sortable : true},
	         {header : '客户评级',dataIndex : 'CUST_LEV_ORA',sortable : true}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerquery2.json'}),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        root:'json.data'
        }, [
			{name: 'CUST_ID'},
			{name: 'CUST_ZH_NAME'},
			{name: 'CUST_ZZDM'},
			{name: 'HY_CLASS_GP'},
			{name: 'STS'},
			{name: 'CRM_SCOPE_GP'},
			{name: 'CUST_SCOPE_GP'},
			
			{name: 'CUST_LEV_ORA'}
		])
	});
    store.on('beforeload', function() {
    	var conditionStr =  qForm.getForm().getValues(false);
        this.baseParams = {
                "condition":Ext.encode(conditionStr)
        };
    });
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '100',
        editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
 // 改变每页显示条数reload数据
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
        store.reload({
            params : {
                start : 0,
                limit : parseInt(pagesize_combo.getValue())
            }
        });
    });
	var bbar = new Ext.PagingToolbar({
       pageSize : number,
       store : store,
       displayInfo : true,
       displayMsg : '显示{0}条到{1}条,共{2}条',
       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
       emptyMsg : "没有符合条件的记录",
       items : ['-', '&nbsp;&nbsp;', pagesize_combo
                ]
   });
	

   
	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '客户间关系维护',
					
					handler : function() {
						editInit();
						//root.expand(true);
					}
				}/*
				, '-', {
					text : '客户间关系修改',
					
					handler : function() {
						editInit();
						root.expand(true);
					}
				}
				, '-', {
					text : '客户间关系删除',
					
					handler : function() {
						deleteRoleItems();
					}
				}*/
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height :document.body.scrollHeight-150,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				//sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户管理->客户间关系", 
				    height: 150,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 

			});
/*	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});*/
	function editInit(){
	     var record = grid.getSelectionModel().getSelected();
	     var checkedNodes = grid.getSelectionModel().selections.items;
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
	     windowForm.getForm().loadRecord(record);
	     
		 addRoleWindow.show();
		 store2.load(
		 		  {	
						params : {
							start : 0,
							limit : parseInt(pagesize_combo.getValue())
						}
		 		  }
		       );
		 windowstore.load({
			  params : {
                start : 0,
                limit : windowbbar.pageSize }} );
	}
	var batchAdd= function(){
		 if(!windowForm.getForm().isValid())
			{ 
				Ext.Msg.alert('提示','输入有误！');
				return false;
			}
		var custid= Ext.getCmp('CUST_ID').getValue();
		var custzhname= Ext.getCmp('CUST_ZH_NAME').getValue();
		var custzzdm= Ext.getCmp('CUST_ZZDM').getValue();
		var shpct= Ext.getCmp('SH_PCT').getValue();
		var reladesc= Ext.getCmp('RELA_DESC').getValue();
		var relaname= Ext.getCmp('RELANAME').getValue();
	 	var checkedNodes = windowgrid.getSelectionModel().selections.items;
		var json={'id':[]};
		var json2={'cust_zh_name':[]};
	 	var json3={'cust_zzdm':[]};
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.id.push(checkedNodes[i].data.CUST_ID);
				json2.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
				json3.cust_zzdm.push(checkedNodes[i].data.CUST_ZZDM);
			}
			Ext.Ajax.request({
						url:basepath+'/customer-relation.json',
                        method: 'POST',
						success : function(response) {
							Ext.Msg.alert('提示', '添加关系成功');
							 store2.load(
							 		  {
							 			 params : {
												start : 0,
												limit : parseInt(pagesize_combo2.getValue())
											}
							 		  }
							       );
						},
						failure : function(response) {
							Ext.Msg.alert('提示','添加关系失败' );
						},
						params : {
							'custid':custid,
							'custzhname': custzhname,
							'custzzdm': custzzdm,
							'shpct': shpct,
							'reladesc': reladesc,
							'relaname': relaname,
							'cid':Ext.encode(json),
							'cust_zh_name': Ext.encode(json2),
							'cust_zzdm': Ext.encode(json3),
							'operate':'add'
						}});
	
	};
	var batchDelete=function(){
		   var checkedNodes = grid2.getSelectionModel().selections.items;
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何关系');
				return ;
			}
		    var json={'id':[]};
				for(var i=0;i<checkedNodes.length;i++)
			{	json.id.push(checkedNodes[i].data.ID);
			}
		    Ext.Ajax.request({url: basepath+'/customer-relation.json',
			method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '删除成功');
				 store2.load(
				 		  {
				 			 params : {
									start : 0,
									limit : parseInt(pagesize_combo2.getValue()),
									customerId: Ext.getCmp('CUST_ID').getValue()
								}
				 		  }
				       );
				 
			},
			failure : function(response) {
				Ext.Msg.alert('提示','删除失败' );
			},
			params : {
				'gxid':Ext.encode(json),
				'operate': 'delete'
			}
		    });
		    
				    
			

	};


});