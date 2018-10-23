Ext.onReady(function() {
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
	var boxstore7 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_STATUS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore8 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/channelInfoQuery.json'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'CHANNEL_ID', 'CHANNEL_NAME' ])
		});
	var boxstore9 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_DISTRIBUTE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore10 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=HYFL'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	Ext.override(Ext.form.ComboBox, {
	      onViewClick : function(doFocus) {
	        var index = this.view.getSelectedIndexes()[0], s = this.store, r = s.getAt(index);
	        if (r) {
	          this.onSelect(r, index);
	        } else if (s.getCount() === 0) {
	          this.collapse();
	              
	        }
	        if (doFocus !== false) {
	          this.el.focus();
	        }
	      }
	    });	    
	 
	 //递归收起子节点
	 function childCollapse(node){
	 	 node.eachChild(function(currNode)
	 	 {
	 	 	if(!currNode.isLeaf())
	 	 	{
		 	 	currNode.collapse();		 	 	         
		 	 	childCollapse(currNode);
	 	 	}
	 	 }
	 	 );
	 }		
	 var orgTreePanel = new Ext.tree.TreePanel(
			 	{	 		
			 	autoScroll:true,
			 	height:350,
			 	width:200,
				listeners:{
					'click':function(node)
					{
						tempCombo = addorganization.getForm().findField("HOST_ORG_NAME");
						addorganization.getForm().findField("UNITCODE").setValue(node.id);
						addCustomer.getForm().reset();
						cusstore.load(
			    				{ params : {
			                    start : 0,
			                    limit : cusbbar.pageSize }});
						tempCombo.setRawValue(node.text);
						tempCombo.collapse();						
					}
				},
				root:new Ext.tree.AsyncTreeNode({
					id:orgId,
					text:orgName,			
					autoScroll:true,
					expanded:true,
					leaf:false,
					loader:new Ext.tree.TreeLoader({
						url:basepath+'/system-unit-recursive-plain.json',
						requestMethod:'GET',
						listeners:{
							'load':function(){
								var rootNode = orgTreePanel.root;
								rootNode.eachChild(function(node){
									if(!node.isLeaf()){
										node.collapse();
										childCollapse(node);
									}
								});						
							}
						}
					})
				}),
				animate : false,
				useArrows : false,
				border : false
			 }
			 );	
	 var orgTreePanel2 = new Ext.tree.TreePanel(
			 	{	 		
			 	autoScroll:true,
			 	height:350,
			 	width:200,
				listeners:{
					'click':function(node)
					{
						tempCombo = simple1.getForm().findField("HOST_ORG_NAME2");
						simple1.getForm().findField("UNITCODE2").setValue(node.id);
						tempCombo.setRawValue(node.text);
						tempCombo.collapse();						
					}
				},
				root:new Ext.tree.AsyncTreeNode({
					id:orgId,
					text:orgName,			
					autoScroll:true,
					expanded:true,
					leaf:false,
					loader:new Ext.tree.TreeLoader({
						url:basepath+'/system-unit-recursive-plain.json',
						requestMethod:'GET',
						listeners:{
							'load':function(){
								var rootNode = orgTreePanel2.root;
								rootNode.eachChild(function(node){
									if(!node.isLeaf()){
										node.collapse();
										childCollapse(node);
									}
								});						
							}
						}
					})
				}),
				animate : false,
				useArrows : false,
				border : false
			 }
			 );	

	 var simple1 = new Ext.FormPanel({
	        frame:true,
	        id:'queryGroup',
	        bodyStyle:'padding:5px 5px 0',
	        split:true,
			height:100,
	        items: [{
	            items :[{
					layout:'column',
					items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
							xtype:'combo',					
							store : new Ext.data.SimpleStore( {
								fields : [],
								data : [ [] ]
							}),
							name:'HOST_ORG_NAME2',
							emptyText : '请选择',
							fieldLabel : '归属机构',
							editable:false,
							resizable:true,
							labelStyle: 'text-align:right;',
							anchor : '95%',
							mode : 'local',
							triggerAction : 'all',
							maxHeight : 390,
							tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd2'></div></div></tpl>",
							onSelect : Ext.emptyFn,
							listeners:{
								'expand':function(combo){			
									orgTreePanel2.render('addOrgTreeDivForAdd2');
								}
							}
						}]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '客户经理名称',
									name : 'USER_NAME',
									labelStyle: 'text-align:right;',
									xtype : 'textfield', // 设置为数字输入框类型
									anchor : '95%'
								}]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '客户经理编号',
									name : 'USER_ID',
									labelStyle: 'text-align:right;',
									xtype : 'textfield', // 设置为数字输入框类型
									anchor : '95%'
								}]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : 'id',
									id:'UNITCODE2',
									name : 'UNITCODE',
									hidden:true,
									labelStyle: 'text-align:right;',
									xtype : 'textfield', // 设置为数字输入框类型
									anchor : '95%'
								}]
					}
	            ]}
	            ]}],
			buttonAlign:'center',
	        buttons: [{
	            text: '查询',
	            handler : function() {
	            	cusGroupstore.load(
	        			/*	{ params : {
                               start : 0,
                               limit : cusGroupbbar.pageSize }}*/ );
           
			}
	        },{
	            text: '重置',
	            handler : function() {
	            	simple1.getForm().reset();   
			}
	        }]
	    });
		var cusGrouprownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
	   	var cusGroupcm = new Ext.grid.ColumnModel([cusGrouprownum,
            {header : 'id', dataIndex : 'ID',sortable : true,width : 150,hidden:true}, 
            {header : '潜在客户组织机构代码',dataIndex : 'CUST_ZZDM',sortable : true,width : 100}, 
            {header : '潜在客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 100 }, 
            {header : '归属机构',dataIndex:'UNITNAME',sortable : true},
            {header : '归属客户经理编号',dataIndex:'USER_ID',sortable : true},
            {header : '归属客户经理',dataIndex:'USERNAME',sortable : true}
			]);

		var cusGroupstore = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/querypotentialcustomerallocation1.json'}
	        ),
	        reader: new Ext.data.JsonReader({
	        root:'json.data',
            totalProperty : 'json.count'
	        }, [ 'ID','CUST_ZH_NAME','CUST_ZZDM','UNITNAME','USER_ID','USERNAME'])
		});
		cusGroupstore.on('beforeload', function() {
	    	var conditionStr =  simple1.getForm().getValues(false);
	        this.baseParams = {
	                "condition":Ext.encode(conditionStr)
	        };
	     });


	 var cusGrouppagesize_combo = new Ext.form.ComboBox({
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

    var cusGroupnumber = parseInt(cusGrouppagesize_combo.getValue());
    cusGrouppagesize_combo.on("select", function(comboBox) {
    	cusGroupbbar.pageSize = parseInt(cusGrouppagesize_combo.getValue()),
    	cusGroupstore.load({
					params : {
						start : 0,
						limit : parseInt(cusGrouppagesize_combo.getValue())
					}
				});
	});
	var cusGroupbbar = new Ext.PagingToolbar({
        pageSize : cusGroupnumber,
        store : cusGroupstore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', cusGrouppagesize_combo
                 ]
    });
	    var cusGroupGrid = new Ext.grid.GridPanel({
	    	height :285,
			width : 886,
	    	//title:'客户群列表',
	        store: cusGroupstore,
			cm : cusGroupcm,
			//autoExpandColumn:'affichename',
			//sm:cusGroupsm,
			bbar : cusGroupbbar,
			selModel:new Ext.grid.RowSelectionModel({
					singleSelect:true
					})
	       
	    });
    var cusGroupQueryWind = new Ext.Window({
       	height:'450',
       	width:'900',
       	closable:true,
       	closeAction:'hide',
       	frame:true,
       	maximizable:true,
    	items:[
    			   simple1,
    			   cusGroupGrid
    			 ],

       	buttonAlign:'center',
       	buttons:[   	
       	{
       		text:'返 回',
       		handler:function()
       		{
       			cusGroupQueryWind.hide();
       		}
       	}
       	]
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
							fieldLabel : '客户名称', // 标签
							name : 'CUST_ZH_NAME', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
						},new Ext.form.ComboBox({
							hiddenName : 'IS_POTENTIAL',
							fieldLabel : '潜在客户状态',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore9,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true, 
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
					})/*,{
							fieldLabel : '归属机构号', // 标签
							name : 'BELONG_ORG_NUM', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
					    }*/,{
							fieldLabel : '统计日期从', // 标签
							 xtype:'datefield',
							 format:'Y-m-d', //日期格式化
							name : 'CRM_DT_START', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
						}]
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
						},{
							fieldLabel : '归属机构名称', // 标签
							name : 'BELONG_ORG_NAME', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
				    	},{
							fieldLabel : '统计日期至', // 标签
							 xtype:'datefield',
							 format:'Y-m-d', //日期格式化
							name : 'CRM_DT_END', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
						}]
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
						hiddenName : 'HY_CLASS',
						fieldLabel : '行业',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : boxstore10,
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
							hiddenName : 'ASSIGN_STS',
							fieldLabel : '客户分配状态',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore7,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true, 
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
					}),new Ext.form.ComboBox({
						hiddenName : 'CHANNEL',
						fieldLabel : '来源渠道',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : boxstore8,
						displayField : 'CHANNEL_NAME',
						valueField : 'CHANNEL_NAME',
						mode : 'local',
						forceSelection : true,
						emptyText:'请选择',
						typeAhead : true,
						resizable : true,
						anchor : '90%'
					})]
					}]
		}],
	buttons : [{
				text : '查询',
				handler : function() {
					store.load({
						  params : {
                             start : 0,
                             limit : bbar.pageSize}}
							);
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
	    {header : 'ID',dataIndex : 'ID',hidden:true,sortable : true},
		{header : '客户ID',dataIndex : 'CUST_ID',hidden:true,sortable : true},
		{header : '客户名称',dataIndex : 'CUST_ZH_NAME',sortable : true},
		{header : '组织机构代码',dataIndex : 'CUST_ZZDM',sortable : true},
		{header : '归属机构号',dataIndex : 'UNITCODE',sortable : true},
		{header : '归属机构名称',dataIndex : 'UNITNAME',sortable : true},
		{header : '行业',dataIndex : 'HY_CLASS',hidden:true},
		{header : '行业',dataIndex : 'HY_CLASS_GP',sortable : true},
		{header : '潜在客户状态',dataIndex : 'IS_POTENTIAL_ORA',sortable : true},
		{header : '客户分配状态',dataIndex : 'ASSIGN_STS_ORA',sortable : true},
		{header : '客户规模',dataIndex : 'CUST_SCOPE_GP',sortable : true},
		{header : '客户规模',dataIndex : 'CUST_SCOPE',hidden:true},
		{header : '客户来源渠道',dataIndex : 'CHANNEL',sortable : true},
		{header : '联系人',dataIndex : 'CIF5LXR1'},
		{header : '联系电话',dataIndex : 'CIF5TEL1'},
		{header : '创建人',dataIndex : 'USERNAME',sortable : true},
		{header : '统计日期',dataIndex : 'CRM_DT',sortable : true,width : 150},
		  {header : '是否中小企业客户',dataIndex : 'CRE_MS_FLG_GP',sortable : true}
			]);

	 var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/querymypotentialcustomer.json'
	       /* 	,
	        	success : function(response) {
					Ext.Msg.alert('提示', response.responseText);
				}*/
	        }),
	        reader: new Ext.data.JsonReader({
	 	       totalProperty : 'json.count',
	 	        root:'json.data'
	 	        }, [
	 	           {name: 'ID'},
	                 {name: 'CUST_ID'},
	 				{name: 'CUST_ZH_NAME'},
	 				{name: 'CUST_ZZDM'},
	 				{name: 'UNITCODE'},
	 				{name: 'UNITNAME'},
	 				{name: 'HY_CLASS'},
	 				{name: 'HY_CLASS_GP'},
	 				{name: 'IS_POTENTIAL_ORA'},
	 				{name: 'ASSIGN_STS_ORA'},
	 				{name: 'CUST_SCOPE_GP'},
	 				{name: 'CUST_SCOPE'},
	 				{name: 'CHANNEL'},
	 				{name: 'CIF5LXR1'},
	 				{name: 'CIF5TEL1'},
	 				{name: 'USERNAME'},
	 				{name: 'CRE_MS_FLG_GP'},
	 				{name: 'CRM_DT'}
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
	
		var addorganization=new Ext.FormPanel({
		title:'选择归属机构',
		frame:true,
		border:false,
		labelAlign:'right',
		items : [{
					layout:'column',
					items : [{
						columnWidth : .50,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
							xtype:'combo',					
							store : new Ext.data.SimpleStore( {
								fields : [],
								data : [ [] ]
							}),
							name:'HOST_ORG_NAME',
							emptyText : '请选择',
							fieldLabel : '归属机构',
							editable:false,
							resizable:true,
							labelStyle: 'text-align:right;',
							anchor : '95%',
							mode : 'local',
							triggerAction : 'all',
							maxHeight : 390,
							tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
							onSelect : Ext.emptyFn,
							listeners:{
								'expand':function(combo){			
									orgTreePanel.render('addOrgTreeDivForAdd');
								}
							}
						}]
					},{
								columnWidth : .50,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : 'id',
											id:'UNITCODE',
											name : 'UNITCODE',
											hidden:true,
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '95%'
										}]
							}]
				}]
	});
	var addCustomer=new Ext.FormPanel({
	title:'选择归属经理',
	frame:true,
	border:false,
	labelAlign:'right',
	items : [{
				layout:'column',
				items : [{
						columnWidth : .50,
							layout : 'form',
							labelWidth : 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户经理名称',
										name : 'USER_NAME',
										labelStyle: 'text-align:right;',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '95%'
									}]
						},{
							columnWidth : .50,
							layout : 'form',
							labelWidth : 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户经理编号',
										name : 'USER_ID',
										labelStyle: 'text-align:right;',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '95%'
									}]
						}]
			}],
			buttonAlign:'center',
			buttons:[{
				text:'查询',
				handler:function()
				{
			cusstore.load(
    				{ params : {
                    start : 0,
                    limit : cusbbar.pageSize }} );
		   
				},
				width:80
			},{
				text:'重置',
					handler : function() {
						addCustomer.getForm().reset();
					}
					
			}]
});
	var cussm = new Ext.grid.CheckboxSelectionModel();
	var cusrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cuscm = new Ext.grid.ColumnModel([cusrownum,cussm,
           {header : '客户经理编号',dataIndex : 'USER_ID',sortable : true,width : 150}, 
           {header : '客户经理名称', dataIndex : 'USER_NAME',sortable : true,width : 150 }
			]);
	var custbar = new Ext.Toolbar({
		items:[
		{
			text:'分配潜在客户',
			handler:function()
			{
				batchAdd();
			}
		}
		]
	});

	/**
	 * 数据存储
	 */
	var cusstore = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomermanager.json'}),
			        reader: new Ext.data.JsonReader({
                    totalProperty : 'json.count',
			        root:'json.data'
			        }, [ 'USER_ID','USER_NAME'])
				});
	 
	  cusstore.on('beforeload', function() {
	   var conditionStr =  addCustomer.getForm().getValues(false);
		var unitcode= Ext.getCmp('UNITCODE').getValue();
	   //UNITCODE
       this.baseParams = {
              "condition":Ext.encode(conditionStr),
              unitcode:unitcode
      };});
	// 每页显示条数下拉选择框
		var cuspagesize_combo = new Ext.form.ComboBox({
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
		var cusnumber = parseInt(cuspagesize_combo.getValue());
				// 改变每页显示条数reload数据
				cuspagesize_combo.on("select", function(comboBox) {
							cusbbar.pageSize = parseInt(cuspagesize_combo.getValue());
							//number = parseInt(comboBox.getValue());
							cusstore.reload({
										params : {
											start : 0,
											limit : parseInt(cuspagesize_combo.getValue())
										}
									});
						});
		// 分页工具栏
		var cusbbar = new Ext.PagingToolbar({
							pageSize : cusnumber,
							store : cusstore,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
							emptyMsg : "没有符合条件的记录",
							items : ['-', '&nbsp;&nbsp;', cuspagesize_combo]
						});
	    var cussmGroupMemberCheck = new Ext.grid.CheckboxSelectionModel();
		
		var cusGrouprownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});			
				
		var groupMemberCol = new Ext.grid.ColumnModel(
				[
			  cusGrouprownum,cussmGroupMemberCheck,
			  {header : 'id', dataIndex : 'ID',sortable : true,width : 150,hidden:true}, 
              {header : '潜在客户组织机构代码',dataIndex : 'CUST_ZZDM',sortable : true,width : 100}, 
              {header : '潜在客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 100 }, 
              {header : '归属机构',dataIndex:'UNITNAME',sortable : true},
              {header : '归属客户经理编号',dataIndex:'USER_ID',sortable : true},
              {header : '归属客户经理',dataIndex:'USERNAME',sortable : true}
				]);
		var custGroupStore = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/querypotentialcustomerallocation.json'}
	        ),
	        reader: new Ext.data.JsonReader({
	        root:'json.data',
            totalProperty : 'json.count'
	        }, [ 'ID','CUST_ZH_NAME','CUST_ZZDM','UNITNAME','USER_ID','USERNAME'])
		});
		custGroupStore.on('beforeload', function() {
			var checkedNodes = grid.getSelectionModel().selections.items;
			var json={'bid':[]};
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.bid.push(checkedNodes[i].data.CUST_ID);
			}
	       this.baseParams = {
	    		   //cbid: Ext.getCmp('cbid').getValue(),
	    		   'bid':Ext.encode(json)
		      };});
		var cusGroupcombo = new Ext.form.ComboBox({
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
	    var cusGroupnumber = parseInt(cusGroupcombo.getValue());
	    // 改变每页显示条数reload数据
	    cusGroupcombo.on("select", function(comboBox) {
	    	cusGroupBbar.pageSize = parseInt(cusGroupcombo.getValue()),
	    	custGroupStore.reload({
	            params : {
	                start : 0,
	                limit : parseInt(cusGroupcombo.getValue())
	            }
	        });
	    });
		var cusGroupBbar = new Ext.PagingToolbar({
	       pageSize : cusGroupnumber,
	       store : custGroupStore,
	       displayInfo : true,
	       displayMsg : '显示{0}条到{1}条,共{2}条',
	       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	       emptyMsg : "没有符合条件的记录",
	       items : ['-', '&nbsp;&nbsp;', cusGroupcombo
	                ]
	   });
		var cusGroupMemeberGrid = new Ext.grid.GridPanel({
			title:'潜在客户归属关系列表',
			frame : true,
			height: document.body.scrollHeight-58,
			autoScroll : true,
			store: custGroupStore,
			stripeRows : true, // 斑马线
			cm : groupMemberCol,
			sm:cussmGroupMemberCheck,
		/*	tbar:[
			      
			      {'text':'移除客户群',handler:function(){
			     //batchDelete();
			}}
			      ],*/
			bbar : cusGroupBbar,
			viewConfig : {
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		var cusGrid = new Ext.grid.GridPanel({
			height: document.body.scrollHeight-218,
			frame : true,
			autoScroll : true,
			store : cusstore, // 数据存储
			stripeRows : true, // 斑马线
			cm : cuscm, // 列模型
			sm : cussm, // 复选框
			bbar : cusbbar,
	        tbar:custbar,
			viewConfig : {
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	var newmember=new Ext.Window(
			{
				   layout : 'fit',
					width:1000,
					height :420,
					closable : true,
					resizable : false,
					collapsible : false,
					maximizable: true,
					maximized:true,
					draggable : true,
					closeAction : 'hide',
					title : '潜在客户分配',
					buttonAlign:'center',
					modal : true, // 模态窗口 
					//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
					animCollapse : false,
					border : false,
					closable : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [
				         {
						layout : 'column',
						border : false,
						items : [
						        {
							columnWidth : .53,
							layout : 'form',
							border : false,
							items : [addorganization,addCustomer,cusGrid]}, {
								columnWidth : .47,
								layout : 'form',
								border : false,
								items : [cusGroupMemeberGrid]
							}
							]
					}
					],
					
					buttonAlign:'center',
					
					buttons:[{
				  			text: '关闭',
				  			handler:function(){
				  			newmember.hide();
						}
		 				}]	
	});

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '分配潜在客户',
					handler : function() {
						editInit();
					}},'-',{
						text : '潜在客户分配查询',
						handler : function() {
							cusGroupQueryWind.show();
						}
				}
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
		        height :document.body.scrollHeight-142,
		        width : document.body.scrollWidth,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
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
				    title: "潜在客户管理->潜在客户分配", 
				    height: 145,
				    hidden:false,
				    margins: '0 0 0 0',
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
		var checkedNodes = grid.getSelectionModel().selections.items;
		if(checkedNodes.length==0)
		{
			Ext.Msg.alert('提示', '未选择任何客户');
			return ;
		}
		
		newmember.show();
		custGroupStore.load();
		//addRoleWindow.show();
	};
	var batchAdd= function(){
		var unitcode= Ext.getCmp('UNITCODE').getValue();
		if(unitcode=="")
		{
			Ext.Msg.alert('提示', '未选择机构');
			return ;
		}
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	var checkedNodes2 = cusGrid.getSelectionModel().selections.items;
	 	var json3={'cid':[]};
		var json={'bid':[]};
		var json2={'userid':[]};
		/*	if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}*/
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.bid.push(checkedNodes[i].data.CUST_ID);
				json3.cid.push(checkedNodes[i].data.ID);
			}
			for(var i=0;i<checkedNodes2.length;i++)
			{
				json2.userid.push(checkedNodes2[i].data.USER_ID);
			}
			Ext.Ajax.request({
						url:basepath+'/potential-customer-allocation.json',
                        method: 'POST',
						success : function(response) {
							Ext.Msg.alert('提示', '分配成功');
							custGroupStore.load(
									
										/*	params : {
												cbid: Ext.getCmp('cbid').getValue()
											}*/
					        		  
									);
						},
						failure : function(response) {
							  var resultArray = Ext.util.JSON.decode(response.status);
							   if(resultArray == 403) {
							      Ext.Msg.alert('提示','您没有此权限!');
							   } else {
								  Ext.Msg.alert('提示','分配失败!');
							   }
						},
						params : {
							'bid':Ext.encode(json),
							'userid': Ext.encode(json2),
							'unitcode': unitcode,
							'cid':Ext.encode(json3),
							'operate': 'add'
							
						}});
	
	};
    /*
	// 获取选择行
	function getCheckboxValues() {
		// 返回一个行集合JS数组
		var rows = grid.getSelectionModel().getSelections();
		if (Ext.isEmpty(rows)) {
			Ext.MessageBox.alert('提示', '您没有选中任何数据!');
			return;
		}
		// 将JS数组中的行级主键，生成以,分隔的字符串
		var strChecked = jsArray2JsString(rows, 'xmid');
		Ext.MessageBox.alert('提示', strChecked);
		// 获得选中数据后则可以传入后台继续处理
	}*/


}); 