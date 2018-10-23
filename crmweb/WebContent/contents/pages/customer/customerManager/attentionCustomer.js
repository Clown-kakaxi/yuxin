/**
 * 功能：我关注的客户
 */

Ext.onReady(function() {
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
	var boxstore3 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=KHQYGM'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var boxstore18 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_LEVEL1'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore19 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_LEVEL2'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	/*var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中型', '0002'], ['小型', '0003']]
			});*/
	var boxstore3 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});
	var boxstore4 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var boxstore5 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['客户群1', '0001'], ['客户群2', '0002'], ['客户群3', '0003'], ['客户群4', '0004']]
			});
	var boxstore6 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['客户名单1', '0001'], ['客户名单2', '0002'], ['客户名单3', '0003'], ['客户名单4', '0004']]
			});
	var boxstore7 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	/*var boxstore8= new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['非关注客户', '0001'], ['关注客户', '0002']]
			});*/
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
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		region:'north',
		split:true,
		buttonAlign : 'center',
		height :100,
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
							fieldLabel : '客户规模',
							 hiddenName : 'CUST_SCOPE',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore2,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							resizable : true,
                            emptyText:'请选择',
							anchor : '90%'
					})/*,
			new Ext.form.ComboBox({							
						fieldLabel : '考核口径客户规模',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : boxstore3,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						name:'crm_scope',										
						anchor : '90%'
								})	*/				
					]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
							fieldLabel : '组织机构代码', // 标签
							name : 'cust_zzdm', // name:后台根据此name属性取值
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
                    })
						]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [
					new Ext.form.ComboBox({
						id : 'c2',
						fieldLabel : '客户状态',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : boxstore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						name:'STS',
						forceSelection : true,
						typeAhead : true,
						resizable : true,
						anchor : '90%'
					}),new Ext.form.ComboBox({
						hiddenName : 'CUST_BIG_LEV',
						fieldLabel : '大客户级别',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : boxstore18,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
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
				items : [
						new Ext.form.ComboBox({
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
										hiddenName : 'CUST_SMALL_LEV',
										fieldLabel : '中小客户级别',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : boxstore19,
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '90%'
									})					
					]
			}]
		}],
	buttons : [{
				text : '查询',
				handler : function() {
					var params = qForm.getForm().getValues(false);
					
					store.removeAll();
					store.load({
						  params : {
                             start : 0,
                             limit : bbar.pageSize ,
                             'condition':Ext.util.JSON.encode(params)
                             }
                             } );
				}
			}, {
				text : '重置',
				handler : function() {
					qForm.getForm().reset(); 
				}
				}]
	});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	        {header : 'id',dataIndex : 'ID',sortable : true,width : 150,hidden :true},
	        {header : '客户id',dataIndex : 'CUST_ID',sortable : true,width : 150,hidden :true},
            {header : '组织机构代码',dataIndex : 'CUST_ZZDM',sortable : true,width : 150},
	        {header : '客户名称',dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150},
            {header : '客户状态',dataIndex : 'CUST_TYP',sortable : true,width : 150},
            {header : '行业',dataIndex : 'HY_CLASS_GP',sortable : true,width : 150},
            {header : '客户规模',dataIndex : 'CUST_SCOPE_GP',sortable : true,width : 150},
            {header : '客户评级',dataIndex : 'CUST_LEV_ORA',sortable : true,width : 150},
            {header : '大客户级别',dataIndex : 'CUST_BIG_LEV_ORA',sortable : true,width : 150},
            {header : '中小客户级别',dataIndex : 'CUST_SMALL_LEV_ORA',sortable : true,width : 150},
            {header : '设置日期',dataIndex : 'CREATE_DT',sortable : true,width : 150}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/queryattentioncustomer.json'}),
        reader: new Ext.data.JsonReader({
        totalProperty : 'json.count',
        root:'json.data'
        }, [ 'ID','CUST_ID','CUST_ZH_NAME','CUST_ZZDM','CUST_TYP','CUST_LEV_ORA','HY_CLASS_GP','CUST_SCOPE_GP','CUST_BIG_LEV_ORA','CUST_SMALL_LEV_ORA','CREATE_DT'])
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
	         data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
	     }),
	     valueField : 'value',
	     displayField : 'text',
	     value : '100',
	     editable : false,
	     width : 85
	 });
	var number = parseInt(pagesize_combo.getValue());
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
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
					text : '取消关注',
					handler : function() {
					/*	var records = grid.getSelectionModel().getSelections();
						var idStr="";
						if(records.length==0){
							return;
						}
						for(var i=0;i<records.length;i++)
						{
							idStr = isStr+records.get("ID")+",";
						}
						idStr = idStr.substring(0,idStr.length()-1);						
						
						Ext.Ajax.request({
							url:basepath+'/customerAttention/12/batchDestroy?idStr='+idStr	,
							success:function(){
								Ext.MessageBox.alert('提示', "操作成功!");
								store.removeAll();
								store.load(
								{
									params:{
										start:0,
										limit:10
									}
								}
								);
							},
							failure:function(){
								Ext.MessageBox.alert('提示', "操作失败!");
								store.removeAll();
								store.load(
								{
									params:{
										start:0,
										limit:10
									}
								}
								);
							}							
						});*/
						
						 var checkedNodes = grid.getSelectionModel().selections.items;
							if(checkedNodes.length==0)
							{
								Ext.Msg.alert('提示', '未选择任何客户');
								return ;
							}
						    var json={'id':[]};
								for(var i=0;i<checkedNodes.length;i++)
							{	json.id.push(checkedNodes[i].data.ID);
							}
						    Ext.Ajax.request({url: basepath+'/customer-attention.json',
							method: 'POST',
							success : function(response) {
								Ext.Msg.alert('提示', '取消关注成功');
								store.load();
							},
							failure : function(response) {
								Ext.Msg.alert('提示','取消关注失败' );
							},
							params : {
								'caid':Ext.encode(json),
								'operate': 'delete'
							}
						    });
						    
						
					}
				}
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
//				height : 340,
//				width : 1362,
				frame : true,
				autoScroll : true,
				region : 'center', // 返回给页面的div
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items: 
				[
					{   
						layout: 'border',
					    id: 'north-panel',
					    title: "客户管理->我关注的客户", 
						items:[qForm,grid]
				     }
	//			     ,{   
	//			    	region:'center',
	//				    id: 'center-panel',
	//				    margins: '0 0 0 0',
	//				    items : [grid]
	//			    }
		     ] 

			});
	store.load();
}) ;