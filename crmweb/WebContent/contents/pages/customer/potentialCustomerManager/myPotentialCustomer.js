Ext.onReady(function() {
	var boxstore12 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中型', '0002'], ['小型', '0003']]
			});
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['正式', '0001'], ['注销', '0002'], ['全部', '0003']]
			});
	var isSmall = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['是', '0001'], ['否', '0002']]
			});
	
	//是否中小企业客户
	var boxstore11 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=YN'
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
	var boxstore3 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});
	var boxstore4 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var boxstore9= new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_DISTRIBUTE'
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
	/*var boxstore8 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000']]
			});*/
	var boxstore9 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['潜在', '0001'], ['已转正', '0002']]
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
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 125,
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
					valueField : 'CHANNEL_ID',
					mode : 'local',
					forceSelection : true,
					emptyText:'请选择',
					typeAhead : true,
					resizable : true,
					anchor : '90%'
				})]
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
	  {header : '客户ID',dataIndex : 'ID',hidden:true},
	  {header : '客户名称',dataIndex : 'CUST_ZH_NAME',sortable : true},
	  {header : '组织机构代码',dataIndex : 'CUST_ZZDM',sortable : true},
	  {header : '归属机构号',dataIndex : 'UNITCODE',sortable : true},
	  {header : '归属机构名称',dataIndex : 'UNITNAME',sortable : true},
	  {header : '行业',dataIndex : 'HY_CLASS',hidden:true},
	  {header : '行业',dataIndex : 'HY_CLASS_GP',sortable : true},
	  {header : '潜在客户状态',dataIndex : 'IS_POTENTIAL_ORA',sortable : true},
	  {header : '潜在客户状态',dataIndex : 'IS_POTENTIAL',hidden:true},
	  {header : '客户分配状态',dataIndex : 'ASSIGN_STS_ORA',sortable : true},
	  {header : '客户规模',dataIndex : 'CUST_SCOPE_GP',sortable : true},
	  {header : '客户规模',dataIndex : 'CUST_SCOPE',hidden:true},
	  {header : '客户来源渠道',dataIndex : 'CHANNEL',sortable : true},
	  {header : '联系人',dataIndex : 'CIF5LXR1'},
	  {header : '联系电话',dataIndex : 'CIF5TEL1'},
	  {header : '创建人',dataIndex : 'USERNAME',sortable : true},
	  {header : '统计日期',dataIndex : 'CRM_DT',sortable : true,width : 150},
	  {header : '是否中小企业客户',dataIndex : 'CRE_MS_FLG',sortable : true,hidden:true},
	  {header : '是否中小企业客户',dataIndex : 'CRE_MS_FLG_GP',sortable : true}
			]);

	 var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/querymypotentialcustomer.json'
//	        	,
//	        	success : function(response) {
//					Ext.Msg.alert('提示', response.responseText);
//				}
	        }),
	       reader: new Ext.data.JsonReader({
	       totalProperty : 'json.count',
	        root:'json.data'
	        }, [
                {name: 'ID'},
				{name: 'CUST_ZH_NAME'},
				{name: 'CUST_ZZDM'},
				{name: 'UNITCODE'},
				{name: 'UNITNAME'},
				{name: 'HY_CLASS'},
				{name: 'HY_CLASS_GP'},
				{name: 'IS_POTENTIAL_ORA'},
				{name: 'IS_POTENTIAL'},
				{name: 'ASSIGN_STS_ORA'},
				{name: 'CUST_SCOPE_GP'},
				{name: 'CUST_SCOPE'},
				{name: 'CHANNEL'},
				{name: 'CIF5LXR1'},
				{name: 'CIF5TEL1'},
				{name: 'USERNAME'},
				{name: 'CRM_DT'},
				{name: 'CRE_MS_FLG'},
				{name: 'CRE_MS_FLG_GP'}
				
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
	 var simple = new Ext.FormPanel({
		  frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        items: [{
	           autoHeight:true,
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*客户名称',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
//	                             allowBlank : false,
	                             id: 'CUST_ZH_NAME1',
	                             name: 'CUST_ZH_NAME',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	 							hiddenName : 'CUST_SCOPE',
	 							id : 'CUST_SCOPE1',
								fieldLabel : '*客户规模',
								
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
//								allowBlank : false,
								store:boxstore12,
								displayField : 'name',
								valueField : 'code',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
						})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '组织机构代码',
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             id: 'CUST_ZZDM1',
	                             name: 'CUST_ZZDM',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'HY_CLASS',
									id : 'HY_CLASS1',
									fieldLabel : '*行业',
//									allowBlank : false,
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
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*客户联系人',
	                             allowBlank : false,
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             id: 'CIF5LXR11',
	                             name: 'CIF5LXR1',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	                        	 	id : 'CHANNEL1',
		 							hiddenName : 'CHANNEL',
									fieldLabel : '来源渠道',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore8,
									displayField : 'CHANNEL_NAME',
									valueField : 'CHANNEL_ID',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*联系电话',
	                             maxLength:20,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             name: 'CIF5TEL1',
	                             id: 'CIF5TEL11',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'CRE_MS_FLG',
									id : 'CRE_MS_FLG1',
									fieldLabel : '是否中小企业客户',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : isSmall,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								})]
	                     }
	            ]}
	            ]}]
	    });
	 var simple2 = new Ext.FormPanel({
	        frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        items: [{
	           autoHeight:true,
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*客户名称',
	                             allowBlank : false,
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             name: 'CUST_ZH_NAME',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
		 							hiddenName : 'CUST_SCOPE',
									fieldLabel : '*客户规模',
									allowBlank : false,
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore12,
									displayField : 'code',
									valueField : 'name',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
							}),new Ext.form.ComboBox({
								hiddenName : 'CRE_MS_FLG',
								fieldLabel : '是否中小企业客户',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : isSmall,
								displayField : 'name',
								valueField : 'code',
								mode : 'local',
								forceSelection : true,
								emptyText:'请选择',
								typeAhead : true,
								resizable : true,
								anchor : '95%'
							})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '组织机构代码',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             name: 'CUST_ZZDM',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'HY_CLASS',
									fieldLabel : '*行业',
									allowBlank : false,
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
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*客户联系人',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             name: 'CIF5LXR1',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
		 							hiddenName : 'CHANNEL',
									fieldLabel : '来源渠道',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore8,
									displayField : 'CHANNEL_NAME',
									valueField : 'CHANNEL_ID',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*联系电话',
	                             labelStyle: 'text-align:right;',
	                             maxLength:20,
	                             allowBlank : false,
	                             name: 'CIF5TEL1',
	                             anchor:'95%'
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
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								})]
	                     }
	            ]}
	            ]}]
	    });
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 900,
				height : 200,
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [simple],
				buttons : [
						{
							text : '保存',
							handler : function() {
								  if(!simple.getForm().isValid())
									{ 
										Ext.Msg.alert('提示','输入信息有误!');
										return false;
									}
								Ext.Ajax.request({
								    url:basepath+'/myPotentialCustomer.json',
								    method: 'POST',
								    form:simple.getForm().id,
									params : {
										'CUST_ZH_NAME':Ext.getCmp('CUST_ZH_NAME1').getValue(),
										'CUST_SCOPE':Ext.getCmp('CUST_SCOPE1').getValue(),
										'CIF5TEL1':Ext.getCmp('CIF5TEL11').getValue(),
										'CIF5LXR1':Ext.getCmp('CIF5LXR11').getValue(),
										'CUST_ZZDM':Ext.getCmp('CUST_ZZDM1').getValue(),
										'CRE_MS_FLG':Ext.getCmp('CRE_MS_FLG1').getValue(),
										'operate':'add'
									},
									success : function(response) {
		    							Ext.Msg.alert('提示', '成功');
										store.load();
		    						},
		    						failure : function(response) {
		    							Ext.Msg.alert('提示', response.responseText);
		    						}
								});
								addRoleWindow.hide();
								//Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							id : 'btnReset',
							handler : function() {
								simple.getForm().reset();   
								//clearForm(addRoleFormPanel.getForm());
							}
						}, {
							text : '关闭',
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	var addRoleWindow2 = new Ext.Window(
			{
				layout : 'fit',
				width : 900,
				height : 200,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [simple2],
				buttons : [
						{
							text : '保存',
							handler : function() {
								if(!simple2.getForm().isValid())
								{ 
									Ext.Msg.alert('提示','输入信息有误!');
									return false;
								}
								var checkedNodes = grid.getSelectionModel().selections.items;
									Ext.Ajax.request({
									    url:basepath+'/my-potential-customer.json?a=2',
									    method: 'POST',
									    form:simple2.getForm().id,
										success : function(response) {
			    							Ext.Msg.alert('提示', '成功');
			    							store.load({
			    								  params : {
			    		                             start : 0,
			    		                             limit : bbar.pageSize}}
			    									);
			    						},
			    						failure : function(response, option) {
			    							
			    							var json = response.responseText;   
			    							var exception= json.split("#sena#")[1];
			    							  //var data = Ext.decode(json);   

			    							// var result=Ext.util.JSON.decode(eval(response.responseText)); 
			    							Ext.Msg.alert('提示', exception);
			    						},
											params : {
												cid:checkedNodes[0].data.ID,
												//'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
												'operate':'update'
											}
									});
								addRoleWindow2.hide();
								//Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							handler : function() {
								simple2.getForm().reset();   
								//clearForm(addRoleFormPanel.getForm());
							}
						}, {
							text : '关闭',
							handler : function() {
								addRoleWindow2.hide();
							}
						} ]
			});

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [/*{
					text : '客户视图',
					handler : function() {
						  window.location.href = '../customerManager/customerBaseInformation.html' ;
					}
				},'-',*/{
					text : '潜在客户新增',
					iconCls:'addIconCss',
					handler : function() {
						simple.getForm().reset();   
						addRoleWindow.show();
						  					}
				},'-',{
					text : '潜在客户修改',
					iconCls:'resetIconCss',
					handler : function() {
						update();
						//addRoleWindow2.show();
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
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [{
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "潜在客户管理->潜在客户维护", 
				    height: 145,
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
				}]
			});
	
	var update = function() {
        var _record = grid.getSelectionModel().getSelected();
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	
        if (!_record||checkedNodes.length>1) {
        	Ext.MessageBox.alert('提示', '请选择要操作的一列！');
        } else {
          var record = grid.getSelectionModel().getSelected();

			
          addRoleWindow2.show();
          simple2.getForm().getEl().dom.reset();
			simple2.getForm().loadRecord(record);
        }
    };

}); 