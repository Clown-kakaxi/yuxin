Ext.onReady(function() {
	//用户群查询条件
		var simple = new Ext.FormPanel( {
			frame : true,
			id : 'queryGroup',
			bodyStyle : 'padding:5px 5px 0',
			width : '100%',
			labelAlign : 'center',
			items : [ {
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '用户群编号',
							labelStyle : 'text-align:right;',
							name : 'CUST_BASE_NUMBER',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '用户群名称',
							labelStyle : 'text-align:right;',
							name : 'CUST_BASE_NAME',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'datefield',
							fieldLabel : '用户群创建日期',
							labelStyle : 'text-align:right;',
							format : 'Y-m-d', //日期格式化
							name : 'CUST_BASE_CREATE_DATE',
							anchor : '90%'
						} ]
					} ]
				} ]
			} ],
			buttonAlign : 'center',
			buttons : [ {
				id : 'search1',
				text : '查询',
				handler : function() {
					store.load( {
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});

				}
			}, {
				text : '重置',
				handler : function() {
					simple.getForm().reset();
				}

			} ],
			keys:[{
				key:13,
				fn:function(){
					Ext.getCmp('search1').focus(true);
				},
				scope:this
		}]
		});

/*		var qForm2 = new Ext.FormPanel( {
			//layout:'fit',
			title : '用户查询',
			frame : true,
			border : false,
			id : 'qForm2',
			labelAlign : 'right',
			hidden : true,
			items : []
		});*/

		//新增用户群成员
		var addCustomer = new Ext.FormPanel( {
			//layout:'fit',
			title : '用户查询',
			frame : true,
			border : false,
			labelAlign : 'right',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .33,
					labelWidth : 80, // 标签宽度
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '用户名称',
						//Width:'100',
						name : 'USERNAME',
						anchor : '95%'
					} ]
				}, {
					columnWidth : .33,
					labelWidth : 80, // 标签宽度
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '用户编号',
						//Width:'100',
						name : 'USERID',
						anchor : '95%'
					} ]
				}, {
					columnWidth : .33,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						hidden : true,
						fieldLabel : 'id',
						labelStyle : {
							width : '120px'
						},
						Width : '100',
						id : 'cbid',
						name : 'id',
						anchor : '90%'
					} ]
				} ]
			} ],
			buttonAlign : 'center',
			buttons : [ {
				id : 'userGId',
				text : '查询',
				handler : function() {
					var conditionStr = addCustomer.getForm().getValues(false);
					cusstore.baseParams = {
						"condition" : Ext.encode(conditionStr)
					};
					cusstore.reload( {
						params : {
							start : 0,
							limit : cusbbar.pageSize
						}
					});

				},
				width : 80
			}, {
				text : '重置',
				handler : function() {
					addCustomer.getForm().reset();
				}

			} ],
			keys:[{
				key:13,
				fn:function(){
					Ext.getCmp('userGId').focus(true);
				},
				scope:this
			}]
		});
		//新增用户群成员的表格面板 

		var custbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'addIconCss',
				text : '归入用户群',
				handler : function() {
					batchAdd();

				}
			}
			//		{
				////			text:'导入查询',
				//			handler:function()
				//			{
				//				importquery();
				//				//batchAdd();
				//			
				//				
				//			}
				//		}
				]
			});
		var cussm = new Ext.grid.CheckboxSelectionModel();
		var cusrownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cuscm = new Ext.grid.ColumnModel( [ cusrownum, cussm,
		   {header : '用户号',dataIndex : 'USERID',sortable : true,width : 150},
		   {header : '用户名称',dataIndex : 'USERNAME',sortable : true,width : 150},
		   {header : '联系电话',dataIndex : 'MOBILEPHONE',sortable : true,width : 150},
		   {header : '性别',dataIndex : 'SEX_ORA',sortable : true,width : 150} ]);

		/**
		 * 数据存储
		 */
		var cusstore = new Ext.data.Store( {
//			autoLoad : true,
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/queryUserDescription.json',
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if (resultArray == 403) {
						Ext.Msg.alert('提示', '您没有此权限!');
					}
				}
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ 'USERID', 'USERNAME', 'MOBILEPHONE', 'SEX', 'SEX_ORA' ])
		});

		/*  cusstore.on('beforeload', function() {
		   var conditionStr =  addCustomer.getForm().getValues(false);
		   this.baseParams = {
		          "condition":Ext.encode(conditionStr)
		  };});*/

		// 每页显示条数下拉选择框
		var cuspagesize_combo = new Ext.form.ComboBox( {
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
			editable : false,
			width : 85
		});
		var cusnumber = parseInt(cuspagesize_combo.getValue());
		// 改变每页显示条数reload数据
		cuspagesize_combo.on("select", function(comboBox) {
			cusbbar.pageSize = parseInt(cuspagesize_combo.getValue());
			//number = parseInt(comboBox.getValue());
				cusstore.reload( {
					params : {
						start : 0,
						limit : parseInt(cuspagesize_combo.getValue())
					}
				});
			});
		// 分页工具栏
		var cusbbar = new Ext.PagingToolbar( {
			pageSize : cusnumber,
			store : cusstore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', cuspagesize_combo ]
		});

		var cusGrid = new Ext.grid.GridPanel( {
			height : document.body.scrollHeight - 155,
			id : 'viewgrid',
			frame : true,
			autoScroll : true,
			store : cusstore, // 数据存储
			stripeRows : true, // 斑马线
			cm : cuscm, // 列模型
			sm : cussm, // 复选框
			bbar : cusbbar,
			tbar : custbar,
			viewConfig : {}
//			,
//			loadMask : {
//				msg : '正在加载表格数据,请稍等...'
//			}
		});
		cusGrid.on(
						'rowdblclick',
						function(cusGrid, rowIndex, event) {
							custwindow(window.Ext.getCmp('viewgrid')
									.getSelectionModel().selections.items[0].data.CUST_ID);

						});

		//新增用户群
		var addCustomerGroup = new Ext.FormPanel( {
			//layout:'fit',
			//title : '新增用户群',
			name : 'addCustomerGroup',
			id : 'addCustomerGroup',
			labelAlign : 'right',
			frame : true,
			border : false,
			style : 'padding:10 10 10 10',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						id : 'custBaseNameId',
						xtype : 'textfield',
						fieldLabel : '<font color=red>*</font>用户群名称',
						labelSeparatro : '',
						allowBlank : false,
						maxLength : 20,
						/*		labelStyle:{
									width:'120px'
								},	*/
						labelStyle : 'text-align:right;',
						Width : '100',
						name : 'custBaseName',
						anchor : '90%',
						listeners : {
							blur : function(text){
								if(this.allowBlank==false && Ext.util.Format.trim(text.getValue()).length==0){
									Ext.Msg.alert('提示','用户群名称不能为空格');
									Ext.getCmp('custBaseNameId').setValue("");
									return false;
								}else{
									return true;
								}
							}
						}
					} ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						hidden : true,
						//disabled:true,
						fieldLabel : '用户群编号',
						labelSeparator : '',
						labelStyle : 'text-align:right;',
						Width : '100',
						name : 'custBaseNumber',
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						hidden : true,
						xtype : 'textfield',
						fieldLabel : 'id',
						labelStyle : {
							width : '120px'
						},
						labelStyle : 'text-align:right;',
						Width : '100',
						name : 'id',
						anchor : '90%'
					} ]
				} ]
			}, {
				layout : 'form',
				items : {
					name : 'custBaseDesc',
					anchor : '80%',
					xtype : 'textarea',
					maxLength : 360,
					fieldLabel : '用户群描述'
				}
			} ]
		});

		//修改用户群
		var updateCustomerGroup = new Ext.FormPanel( {
			//layout:'fit',
			//title : '新增用户群',
			labelAlign : 'right',
			frame : true,
			border : false,
			style : 'padding:10 10 10 10',
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						id : 'updateBaseNameId',
						xtype : 'textfield',
						fieldLabel : '<font color=red>*</font>用户群名称',
						labelSeparator : '',
						allowBlank : false,
						maxLength : 20,
						labelStyle : 'text-align:right;',
						Width : '100',
						name : 'custBaseName',
						anchor : '90%',
						listeners : {
							blur : function(text){
								if(this.allowBlank==false && Ext.util.Format.trim(text.getValue()).length==0){
									Ext.Msg.alert('提示','用户群名称不能为空格');
									Ext.getCmp('updateBaseNameId').setValue("");
									return false;
								}else{
									return true;
								}
							}
						}
					} ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						hidden : true,
						fieldLabel : '用户群编号',
						labelSeparator : '',
						labelStyle : 'text-align:right;',
						Width : '100',
						name : 'custBaseNumber',
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						hidden : true,
						xtype : 'textfield',
						fieldLabel : 'id',
						labelStyle : {
							width : '120px'
						},
						Width : '100',
						name : 'id',
						anchor : '90%'
					} ]
				} ]
			}, {
				layout : 'form',
				items : {
					name : 'custBaseDesc',
					anchor : '80%',
					maxLength : 360,
					xtype : 'textarea',
					fieldLabel : '用户群描述'
				}
			} ]
		});
		//复选框
		var sm4 = new Ext.grid.CheckboxSelectionModel();

		// 定义自动当前页行号
		var rownum4 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cm4 = new Ext.grid.ColumnModel( [ rownum4, {
			header : 'id',
			dataIndex : 'id',
			sortable : true,
			hidden : true
		}, {
			header : '用户名称',
			dataIndex : 'CUST_NAME',
			sortable : true
		} ]);

		/**
		 * 数据存储
		 */

		var store4 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/queryimportquery.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'CUST_ID'
			}, {
				name : 'CUST_ZH_NAME'
			} ])
		});
		var pagesize_combo4 = new Ext.form.ComboBox( {
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
			editable : false,
			width : 85
		});
		var number4 = parseInt(pagesize_combo4.getValue());
		// 改变每页显示条数reload数据
		pagesize_combo4.on("select", function(comboBox) {
			bbar4.pageSize = parseInt(pagesize_combo4.getValue());
			store4.reload( {
				params : {
					start : 0,
					limit : parseInt(pagesize_combo4.getValue())
				}
			});
		});
		// 每页显示条数下拉选择框

		//分页工具栏
		var bbar4 = new Ext.PagingToolbar( {
			pageSize : number4,
			store : store4,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo4 ]
		});

		//	  var importquery= function(){
		//		  importquerywindow.show();
		//		  store4.load();
		//		};

		//		var tbar4 = new Ext.Toolbar({
		//			items:[
		//			       {
		//					    text:'导入查询用户',
		//					    handler:function(){
		//					        importForm.tradecode='ImportQuery';
		//					        importForm.proxyStore=store4;
		//					        importWindow.show();
		//					    }
		//					}
		//			]
		//		});

		//信贷台账
		var grid4 = new Ext.grid.GridPanel( {
			height : 215,
			frame : true,
			autoScroll : true,
			store : store4, // 数据存储
			stripeRows : true, // 斑马线
			//					tbar:tbar4,
			cm : cm4, // 列模型
			bbar : bbar4,// 分页工具栏
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		var updatewin = new Ext.Window( {
			title : "修改用户群信息",
			layout : 'fit',
			width : 750,
			height : 250,
			closable : true,
			resizable : false,
			collapsible : false,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口 
			//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
			animCollapse : false,
			maximizable : true,
			border : false,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [ updateCustomerGroup ],
			buttonAlign : 'center',
			buttons : [
					{
						text : '保存',
						handler : function() {
							if (!updateCustomerGroup.getForm().isValid()) {
								Ext.Msg.alert('提示', '输入信息有误!');
								return false;
							}
							Ext.Ajax.request( {
								url : basepath + '/userBase.json',
								method : 'POST',
								form : updateCustomerGroup.getForm().id,
								success : function(response) {
									Ext.Msg.alert('提示', '修改成功');
									store.load( {
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON
											.decode(response.status);
									if (resultArray == 403) {
										Ext.Msg.alert('提示', '您没有此权限!');
									} else {
										Ext.Msg.alert('提示', '修改失败!');
									}
								},
								params : {
									'operate' : 'update'
								}
							});
							updatewin.hide();
						}
					}, {
						text : '关闭',
						handler : function() {
							updatewin.hide();
						}
					} ]
		});
		/**群权限设置窗口****************************************************************/
		var groupId = '';
		var powerStore = new Ext.data.ArrayStore( {
			fields : [ 'key', 'value' ],
			data : [ [ '1', '报表' ], [ '2', '营销活动' ] ]
		});
		//自己的菜单查看
		var powerSimple = new Ext.FormPanel({
	        frame:true,
	        id:'queryGroup2',
	        bodyStyle:'padding:5px 5px 0',
//	        width: '100%',
	        height : 80,
	        labelAlign:'center',
	        items: [{
	            items :[{ 
	            		layout:'column',
	                     items:[                     
	                     {
	                         columnWidth:.45,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '权限名称',
	                             labelStyle: 'text-align:right;',
	                             name: 'menuName',
	                             anchor:'90%'
	                         }]
	                     }/*,{
	                         columnWidth:.45,
	                         layout: 'form',
	                         items: [{
	                             xtype:'combo',
	                             fieldLabel: '权限类型',
	                             labelStyle: 'text-align:right;',
	                             //name: 'menuType',
	                             hiddenName : 'menuType',
	                             store : powerStore,
	                        	 valueField : 'key',
	                        	 displayField : 'value',
	                        	 mode : 'local',
	     						 triggerAction : 'all',
	                             anchor:'90%'
	                         }]
	                     }*/
	            ]}
	            ]}],
			buttonAlign:'center',
	        buttons: [{
	        	id : 'search2',
	            text: '查询',
	        	handler : function() {
		        	purviewQueryStore.reload( {
						params : {
						start : 0,
						limit : bbar.pageSize
					}
				});
			}
	        },{
	            text: '重置',
	        	handler : function() {
	        		powerSimple.getForm().reset();   
			}
	            
	           
	        }],
	        keys:[{
				key:13,
				fn:function(){
					Ext.getCmp('search2').focus(true);
				},
				scope:this
	        }]
	    });
		
		
		var purviewQueryCheck = new Ext.grid.CheckboxSelectionModel();

		var purviewQueryrownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		var purviewQueryCol = new Ext.grid.ColumnModel( [
                purviewQueryrownum,purviewQueryCheck, 
            {header : '权限ID',dataIndex : 'MENU_ID',sortable : true,width : 150,hidden : true},
            {header : '权限名称',dataIndex : 'MENU_NAME',sortable : true,width : 150},
            {header : '类型',dataIndex : 'MENU_TYPE',sortable : true,width : 100}
            ]);
		var purviewQueryStore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/queryUserBaseMaintain.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data',
				totalProperty : 'json.count'
			}, [ 'MENU_ID', 'MENU_NAME', 'MENU_TYPE' ])
		});
		purviewQueryStore.on('beforeload', function() {
			var conditionStr1 = powerSimple.getForm().getValues(false);
			this.baseParams = {
				'condition' : Ext.encode(conditionStr1),
				'cbid' : groupId
		};
		});
		var purviewQuerycombo = new Ext.form.ComboBox( {
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
			editable : false,
			width : 85
		});

		var purviewQuerynumber = parseInt(purviewQuerycombo.getValue());
		// 改变每页显示条数reload数据
		purviewQuerycombo.on("select", function(comboBox) {
			purviewQueryBbar.pageSize = parseInt(purviewQuerycombo.getValue()),
			purviewQueryStore.reload( {
						params : {
							start : 0,
							limit : parseInt(purviewQuerycombo.getValue())
						}
					});
		});
		var purviewQueryBbar = new Ext.PagingToolbar( {
			pageSize : purviewQuerynumber,
			store : purviewQueryStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', purviewQuerycombo ]
		});
		var purviewQueryGrid = new Ext.grid.GridPanel( {
			title : '权限列表',
			frame : true,
			height : document.body.scrollHeight-140,
			autoScroll : true,
			store : purviewQueryStore,
			stripeRows : true, // 斑马线
			cm : purviewQueryCol,
			sm : purviewQueryCheck,
			tbar : [ {
				iconCls : 'editIconCss',
				'text' : '设置权限',
				handler : function() {
			 	var checkedNodes = purviewQueryGrid.getSelectionModel().selections.items;
			 	var json={'id':[]};
				if(checkedNodes.length==0)
				{
					Ext.Msg.alert('提示', '请选择一条数据！');
					return ;
				}
				for(var i=0;i<checkedNodes.length;i++)
				{
					json.id.push(checkedNodes[i].data.MENU_ID);
				}
			 	//var json3={'cust_zzdm':[]};
				Ext.Ajax.request({
					url : basepath + '/userPower!powerSet.json',
                    method: 'POST',
					success : function(response) {
						Ext.Msg.alert('提示', '设置成功');
						purviewstore.load();
						purviewQueryStore.load();
					},
					failure : function(response) {
						  var resultArray = Ext.util.JSON.decode(response.status);
						   if(resultArray == 403) {
						      Ext.Msg.alert('提示','您没有此权限!');
						   } else {
							  Ext.Msg.alert('提示','加入失败!');
						   }
					},
					params : {
						'pid':Ext.encode(json),
						//'cust_zh_name': Ext.encode(json2),
						//'cust_zzdm': Ext.encode(json3),
						'cid': groupId
						//'operate': 'add'
						
					}});
				
					//batchDelete();
				}
			}					 
			],
			bbar : purviewQueryBbar,
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		/****************************已选权限查询grid**************************************/
	
		var purviewsm = new Ext.grid.CheckboxSelectionModel();

		var purviewrownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

		var purviewcm = new Ext.grid.ColumnModel( [
                purviewrownum,purviewsm, 
                {header : 'ID',dataIndex : 'PRIV_ID',sortable : true,width : 150,hidden : true},
                {header : '权限名称',dataIndex : 'MENU_NAME',sortable : true,width : 150},
                {header : '类型',dataIndex : 'MENU_TYPE',sortable : true,width : 100}
            ]);
		var purviewstore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/userPowerSetAction.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data',
				totalProperty : 'json.count'
			}, [ 'PRIV_ID', 'MENU_NAME', 'MENU_TYPE'])
		});
		purviewstore.on('beforeload', function() {
			this.baseParams = {
				'cid' : groupId
			};
		});

		var purviewcombo = new Ext.form.ComboBox( {
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
			editable : false,
			width : 85
		});

		var purviewnumber = parseInt(purviewcombo.getValue());
		// 改变每页显示条数reload数据
		purviewcombo.on("select", function(comboBox) {
			purviewbbar.pageSize = parseInt(purviewcombo.getValue()),
			purviewstore.reload( {
						params : {
							start : 0,
							limit : parseInt(purviewcombo.getValue())
						}
					});
		});
		var purviewbbar = new Ext.PagingToolbar( {
			pageSize : purviewnumber,
			store : purviewstore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', purviewcombo ]
		});
		var purviewGrid = new Ext.grid.GridPanel( {
			title : '群已拥有的权限',
			frame : true,
			height : document.body.scrollHeight-60,
			autoScroll : true,
			store : purviewstore,
			stripeRows : true, // 斑马线
			cm : purviewcm,
			sm : purviewsm,
			tbar : [ {
				iconCls : 'deleteIconCss',
				'text' : '删除权限',
				handler : function() {
			 	var checkedNodes = purviewGrid.getSelectionModel().selections.items;
			 	var json={'id':[]};
				if(checkedNodes.length==0)
				{
					Ext.Msg.alert('提示', '请选择一条数据！');
					return ;
				}
				for(var i=0;i<checkedNodes.length;i++)
				{
					json.id.push(checkedNodes[i].data.PRIV_ID);
				}
			 	//var json3={'cust_zzdm':[]};
				Ext.Ajax.request({
					url : basepath + '/userPower!deletePower.json',
                    method: 'POST',
					success : function(response) {
						Ext.Msg.alert('提示', '设置成功');
					    purviewstore.load();
					    purviewQueryStore.load();
					},
					failure : function(response) {
						  var resultArray = Ext.util.JSON.decode(response.status);
						   if(resultArray == 403) {
						      Ext.Msg.alert('提示','您没有此权限!');
						   } else {
							  Ext.Msg.alert('提示','加入失败!');
						   }
					},
					params : {
						'priv':Ext.encode(json)
						//'cust_zh_name': Ext.encode(json2),
						//'cust_zzdm': Ext.encode(json3),
						//'cid': groupId
						//'operate': 'add'
						
					}});
				
				
				
				
				}
			}					 
			],
			bbar : purviewbbar,
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		var powerPanelWin = new Ext.Window( {
			//id:'powerPanelWin',
			title : "群权限设置",
			layout : 'form',
			width : 750,
			height : 420,
			closable : true,
			resizable : false,
			collapsible : false,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口 
			//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
			animCollapse : false,
			maximizable : true,
			maximized : true,
			border : false,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			//[ powerSimple, listPanel],
			items : 
			[{
				layout : 'column',
				border : false,
				items : [ {
					columnWidth : .53,
					layout : 'form',
					border : false,
					items : [ 
					          powerSimple
					          	, 
					          
					          purviewQueryGrid 
					          
					          ]
				}, {
					columnWidth : .47,
					layout : 'form',
					border : false,
					items : [
					         purviewGrid
					         ]
				} ]
			}],

			buttonAlign : 'center',

			buttons : [ {
				text : '关闭',
				handler : function() {
					store.load( {
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
					powerPanelWin.hide();
				}
			} ]

		});
		
		//群权限设置
		var powerSet = function() {
			var _record = cusGroupGrid.getSelectionModel().getSelected();
			var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			if (!_record || checkedNodes.length > 1) {
				Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
			} else {
				//var record = cusGroupGrid.getSelectionModel().getSelected();
				groupId = checkedNodes[0].data.id;//获得群ID
				//Ext.getCmp('grpNo').setValue(groupId);
//				listPanel.grid.store.on('beforeload', function() {
//					debugger;
//					this.baseParams = {
//							params:{'groupId':groupId}
//					};
//				});
//				listPanel.grid.store.on('beforeload', function() {
//			        this.baseParams = {
//			        		'groupId':groupId
//			                
//			        };
//				});
			/*	listPanel.grid.store.load({
						params:{'groupId':groupId}
				});*/
				powerPanelWin.show();
				purviewQueryStore.load();
				purviewstore.load();
			}
		};
		/******************************************************************/
		var cussmGroupMemberCheck = new Ext.grid.CheckboxSelectionModel();

		var cusGrouprownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		var groupMemberCol = new Ext.grid.ColumnModel( [
                cusGrouprownum,cussmGroupMemberCheck, 
            {header : 'id',dataIndex : 'ID',sortable : true,width : 150,hidden : true},
            {header : '用户号',dataIndex : 'USERID',sortable : true,width : 150},
            {header : '用户名称',dataIndex : 'USERNAME',sortable : true,width : 100},
            {header : '机构名称',dataIndex : 'UNITNAME',sortable : true,width : 150},
            {header : '联系电话',dataIndex : 'MOBILEPHONE',sortable : true,width : 100},
            {header : '性别',dataIndex : 'SEX_ORA',sortable : true}
            ]);
		var custGroupStore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/queryUserBaseList.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data',
				totalProperty : 'json.count'
			}, [ 'ID', 'USERID', 'USERNAME', 'UNITNAME', 'MOBILEPHONE', 'SEX',
					'SEX_ORA' ])
		});

		custGroupStore.on('beforeload', function() {
			this.baseParams = {
				cbid : Ext.getCmp('cbid').getValue()
			};
		});

		var cusGroupcombo = new Ext.form.ComboBox( {
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
			editable : false,
			width : 85
		});

		var cusGroupnumber = parseInt(cusGroupcombo.getValue());
		// 改变每页显示条数reload数据
		cusGroupcombo.on("select", function(comboBox) {
			cusGroupBbar.pageSize = parseInt(cusGroupcombo.getValue()),
					custGroupStore.reload( {
						params : {
							start : 0,
							limit : parseInt(cusGroupcombo.getValue())
						}
					});
		});
		var cusGroupBbar = new Ext.PagingToolbar( {
			pageSize : cusGroupnumber,
			store : custGroupStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', cusGroupcombo ]
		});

		var cusGroupMemeberGrid = new Ext.grid.GridPanel( {
			title : '用户群成员列表',
			frame : true,
			height : document.body.scrollHeight - 58,
			autoScroll : true,
			store : custGroupStore,
			stripeRows : true, // 斑马线
			cm : groupMemberCol,
			sm : cussmGroupMemberCheck,
			tbar : [ {
				iconCls : 'deleteIconCss',
				'text' : '移出用户群',
				handler : function() {
					batchDelete();
				}
			}
			//				,
			//		         new Com.yucheng.bob.ExpButton({
			//			            formPanel : 'qForm2',
			//			            id:'exportbt',
			//		         		url : basepath+'/querycustomerbase2.json?'
			//			        })					 
			],
			bbar : cusGroupBbar,
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		var newmember = new Ext.Window( {
			layout : 'fit',
			width : 1000,
			height : 420,
			closable : true,
			resizable : false,
			collapsible : false,
//			maximizable : true,
			maximized : true,
			draggable : true,
			closeAction : 'hide',
//			title : '用户群成员维护',
			buttonAlign : 'center',
			modal : true, // 模态窗口 
			//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
			animCollapse : false,
			border : false,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [ {
				layout : 'column',
				border : false,
				items : [ {
					columnWidth : .53,
					layout : 'form',
					border : false,
					items : [ addCustomer, cusGrid ]
				}, {
					columnWidth : .47,
					layout : 'form',
					border : false,
					items : [  cusGroupMemeberGrid ]
				} ]
			} ],

			buttonAlign : 'center',

			buttons : [ {
				text : '关闭',
				handler : function() {
					store.load( {
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
					newmember.hide();
				}
			} ],
			listeners : {
				'show' : function(){
					var lm = new Ext.LoadMask (Ext.get(newmember.id),{
						store:cusstore,
						msg : '正在加载表格数据,请稍等...',
						removeMask: true 
					});
					cusstore.on('beforeload',function(){lm.show();});
				}
			}
		});
		/*	var custbarDetail = new Ext.Toolbar({
				items:[
				{
					text:'查看详细',
					handler:function()
					{custview();	
					}
				}
				]
			});*/

		// 表格工具栏
		var tbar = new Ext.Toolbar(
				{
					items : [
							{
								iconCls : 'addIconCss',
								text : '新增用户群',
								handler : function() {
									var win = new Ext.Window(
											{
												title : "新增用户群",
												layout : 'fit',
												width : 750,
												height : 250,
												closable : true,
												resizable : false,
												collapsible : false,
												draggable : true,
												closeAction : 'hide',
												//titleCollapse : false,
												modal : true, // 模态窗口 
												//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
												animCollapse : false,
												maximizable : true,
												border : false,
												closable : true,
												animateTarget : Ext.getBody(),
												constrain : true,
												items : [ addCustomerGroup ],
												buttonAlign : 'center',
												listeners : {
													"hide" : function() {
														addCustomerGroup
																.getForm()
																.reset();
													}
												},
												buttons : [
														{
															text : '保存',
															handler : function() {
																if (!addCustomerGroup
																		.getForm()
																		.isValid()) {
																	Ext.Msg
																			.alert(
																					'提示',
																					'输入信息有误!');
																	return false;
																}
																Ext.Ajax
																		.request( {
																			url : basepath + '/userBase.json',
																			method : 'POST',
																			form : addCustomerGroup
																					.getForm().id,
																			success : function(
																					response) {
																				Ext.Msg
																						.alert(
																								'提示',
																								'操作成功');
																				store
																						.load( {
																							params : {
																								start : 0,
																								limit : bbar.pageSize
																							}
																						});
																			},
																			failure : function(
																					a,b,c) {debugger;
																				var resultArray = Ext.util.JSON
																						.decode(a.status);
																				var e = Ext.decode(a.responseText);
																				if (resultArray == 403) {
																					Ext.Msg
																							.alert(
																									'提示',
																									'您没有此权限!');
																				} else {
																					if(e.status_code == "500"){
																						if(e.code != null && e.direct != null && e.level != null && e.msg != null){
																							if(e.code == 'groupExist'){//潜在客户新增时，如果输入已存在的 姓名 + 证件类型 + 证件号时抛此异常 
																								Ext.Msg.alert('提示','该用户群名称已存在');
																							}
																						}
																						
																					}else{
																						Ext.Msg
																								.alert(
																										'提示',
																										'新增失败!');
																					}
																				}
																			},
																			params : {
																				//'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
																				'operate' : 'add'
																			}
																		});
																win.hide();
															}
														},
														{
															text : '取消',
															handler : function() {
																win.hide();
															}
														} ]
											});
									/*		Ext.Ajax.request({
													url:basepath+'/querycustomerbasenumber.json',
												    method: 'GET',
												    success:function(response){
												    	var json=Ext.util.JSON.decode(response.responseText);
												    	 Ext.getCmp('customerBaseNumber').setValue(json.json.data);
											    }
												});*/

									/*	addCustomerGroup.getForm().load({
									          //waitMsg: '正在加载数据',
									  	        //waitTitle: '提示',
										restful:true,	
									    url:basepath+'/querycustomerbasenumber.json',
									    method: 'GET',
									   callback : function(form,action) {
										   debugger;
									    	Ext.Msg.alert('提示',action);
									    	 //debugger;
											//var resultArray = Ext.util.JSON.decode(response.responseText);
												//Ext.Msg.alert('提示',response.responseText);
											}
									     }
										);*/
									win.show();

								}
							},
							'-',
							{
								iconCls : 'publishIconCss',
								text : '用户群成员维护',
								handler : function() {
									var _record = cusGroupGrid
											.getSelectionModel().getSelected();
									var checkedNodes = cusGroupGrid
											.getSelectionModel().selections.items;
									if (!_record || checkedNodes.length > 1) {
										Ext.MessageBox.alert('修改操作',
												'请选择要操作的一列！');
									} else {
										addCustomer.getForm().loadRecord(
												_record);
										var temp = checkedNodes[0].data.id;
										var gloGroupName = _record.data.custBaseName;
										newmember.setTitle("用户群群成员维护"+"-"+gloGroupName);
										newmember.show();
										custGroupStore.load( {
											params : {
												cbid : Ext.getCmp('cbid')
														.getValue()
											}
										});
										//					          Ext.getCmp("exportbt").url=basepath+'/querycustomerbase2.json?cbid='+temp;
									}
								}
							}, '-',
							{
								iconCls : 'editIconCss',
								text : '修改用户群',
								handler : function() {

									updateCustomerBase(cusGroupGrid);

								}
							}, '-', {
								iconCls : 'deleteIconCss',
								text : '删除用户群',
								handler : function() {
									deleteCustomerBase(cusGroupGrid);

									store.load( {
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});

								}
							}, '-', {
								iconCls : 'optionIconCss',
								text : '群权限设置',
								handler : function() {
									powerSet();
									//						store.load(
								//		        				{ params : {
								//	                                   start : 0,
								//	                                   limit : bbar.pageSize }} );
							}
							} ]
				});

		//用户群表格面板

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cusGroupsm = new Ext.grid.CheckboxSelectionModel();
		var rownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cm = new Ext.grid.ColumnModel( [ rownum, sm, {
			header : 'id',
			dataIndex : 'id',
			sortable : true,
			width : 150,
			hidden : true
		}, {
			header : '用户群编号',
			hidden : true,
			dataIndex : 'custBaseNumber',
			sortable : true,
			width : 150
		}, {
			header : '用户群名称',
			dataIndex : 'custBaseName',
			sortable : true,
			width : 150
		}, {
			header : '用户群创建日期',
			dataIndex : 'custBaseCreateDate',
			sortable : true,
			width : 150
		}, {
			header : '用户群成员数',
			dataIndex : 'custBaseMemberNum',
			renderer : function(value) {
				if (value == '') {
					return "0";
				} else {
					return value;
				}
			},
			sortable : true,
			width : 150
		}, {
			header : '用户群描述',
			dataIndex : 'custBaseDesc',
			sortable : true,
			width : 150
		} ]);
		/**
		 * 数据存储
		 */

		var store = new Ext.data.Store( {
			autoLoad : true,
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/queryuserbase.json'
			/*   	success : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('提示', response.responseText);
					}*/
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [ {
				name : 'id',
				mapping : 'ID'
			}, {
				name : 'custBaseNumber',
				mapping : 'CUST_BASE_NUMBER'
			}, {
				name : 'custBaseName',
				mapping : 'CUST_BASE_NAME'
			}, {
				name : 'custBaseCreateDate',
				mapping : 'CUST_BASE_CREATE_DATE'
			}, {
				name : 'custBaseMemberNum',
				mapping : 'MEMBERSNUM'
			}, {
				name : 'custBaseDesc',
				mapping : 'CUST_BASE_DESC'
			} ])
		});
		store.on('beforeload', function() {
			var conditionStr = simple.getForm().getValues(false);
			this.baseParams = {
				"condition" : Ext.encode(conditionStr)
			};
		});

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
			editable : false,
			width : 85
		});
		var number = parseInt(pagesize_combo.getValue());
		// 改变每页显示条数reload数据
		pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo.getValue()), store.reload( {
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});
		});
		var bbar = new Ext.PagingToolbar( {
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
		});

		// create the Grid
		var cusGroupGrid = new Ext.grid.GridPanel( {
			frame : true,
			height : document.body.scrollHeight - 100,
			autoScroll : true,
			region : 'center', // 返回给页面的div
			store : store,
			stripeRows : true, // 斑马线
			cm : cm,
			sm : sm,
			tbar : tbar,
			bbar : bbar,
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ {
				region : 'north',
				id : 'north-panel',
				title : "系统管理->用户群管理",
				height : 100,
				hidden : false,
				margins : '0 0 0 0',
				//layout: 'fit',
				items : [ simple ]
			}, {
				region : 'center',
				id : 'center-panel',
				height : document.body.scrollHeight - 100,
				margins : '0 0 0 0',
				items : [ cusGroupGrid ]
			} ]

		});
		var updateCustomerBase = function(cusGroupGrid) {
			var _record = cusGroupGrid.getSelectionModel().getSelected();
			var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			if (!_record || checkedNodes.length > 1) {
				Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
			} else {
				var record = cusGroupGrid.getSelectionModel().getSelected();
				updateCustomerGroup.getForm().loadRecord(record);
				updatewin.show();
			}
		};
		var deleteCustomerBase = function(cusGroupGrid) {
			var _record = cusGroupGrid.getSelectionModel().getSelected();
			if (!_record) {
				Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
			} else {
				var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
				var json = {
					'id' : []
				};
				for ( var i = 0; i < checkedNodes.length; i++) {
					json.id.push(checkedNodes[i].data.id);
				}
				Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return;
					}
					Ext.Ajax.request( {
						url : basepath + '/userBase.json',
						method : 'POST',
						success : function(response) {
							Ext.Msg.alert('提示', '成功');
							store.load();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON
									.decode(response.status);
							if (resultArray == 403) {
								Ext.Msg.alert('提示', '您没有此权限!');
							} else {
								Ext.Msg.alert('提示', '失败!');
							}
						},
						params : {
							cbid : Ext.encode(json),
							'operate' : 'delete'
						}
					});

				});
			}
		};
		var batchAdd = function() {
			var cbid = Ext.getCmp('cbid').getValue();
			var checkedNodes = cusGrid.getSelectionModel().selections.items;
			var json = {
				'cust_id' : []
			};
			var json1 = {
				'cust_zh_name' : []
			};
			if (checkedNodes.length == 0) {
				Ext.Msg.alert('提示', '未选择任何用户');
				return;
			}
			for ( var i = 0; i < checkedNodes.length; i++) {
				debugger;
				json.cust_id.push(checkedNodes[i].data.USERID);
				json1.cust_zh_name.push(checkedNodes[i].data.USERNAME);

			}

			Ext.Ajax.request( {
				url : basepath + '/userRelateUserBase.json',
				method : 'POST',
				success : function(response) {
					Ext.Msg.alert('提示', '加入成功');
					custGroupStore.load( {
						params : {
							cbid : Ext.getCmp('cbid').getValue()
						}
					});
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					var resultArray = Ext.util.JSON.decode(response.status);debugger;
					var e = Ext.util.JSON.decode(response.responseText);
					if(e.status_code == "500"){
						if(e.code != null && e.direct != null && e.level != null && e.msg != null){
							Ext.Msg.alert('提示','该群已存在此客户');
						}
					}else if (resultArray == 403) {
						Ext.Msg.alert('提示', '您没有此权限!');
					} else {
						Ext.Msg.alert('提示', '加入失败!');
					}
				},
				params : {
					'cust_id' : Ext.encode(json),
					'cust_zh_name' : Ext.encode(json1),
					'cbid' : cbid,
					'operate' : 'add'

				}
			});
		};
		var batchDelete = function() {
			var checkedNodes = cusGroupMemeberGrid.getSelectionModel().selections.items;
			if (checkedNodes.length == 0) {
				Ext.Msg.alert('提示', '未选择任何用户');
				return;
			}
			var json = {
				'id' : []
			};
			for ( var i = 0; i < checkedNodes.length; i++) {
				json.id.push(checkedNodes[i].data.ID);
			}
			var id = checkedNodes[0].data.ID;
			Ext.Ajax.request( {
				url : basepath + '/userRelateUserBase.json',
				method : 'POST',
				success : function(response) {
					Ext.Msg.alert('提示', '删除成功');
					custGroupStore.load( {
						params : {
							cbid : Ext.getCmp('cbid').getValue()
						}
					});
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if (resultArray == 403) {
						Ext.Msg.alert('提示', '您没有此权限!');
					} else {
						Ext.Msg.alert('提示', '删除失败!');
					}
				},
				params : {
					'cbid' : Ext.encode(json),
					'operate' : 'delete'
				}
			});
		};

		
		cusGroupGrid.on('rowdblclick', function() {
			if (cusGroupGrid.selModel.hasSelection()) {
				var records = cusGroupGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
				} else {
					var record = cusGroupGrid.getSelectionModel().getSelected();
						updateCustomerGroup.getForm().loadRecord(record);
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要查看的记录!");
			}
			updatewin.show();
		}); 
		
		var custwindow = function(custid) {
			var id = custid;
			var viewpanel = new Ext.Panel(
					{
						layout : 'fit',
						items : [ {
							layout : 'fit',
							margins : '0 0 0 0',
							items : [ {
								html : '<iframe id="content" name="content2" height="100%" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation2.jsp?customerId=' + id + '\" "/> scrolling="auto"> </iframe>'
							} ]
						} ]

					});
			var viewWindow = new Ext.Window( {
				layout : 'fit',
				width : 1000,
				height : 400,
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				maximized : true,
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [ viewpanel ]
			});
			viewWindow.show();

		};
		/*	var custview=function(){
		 var checkedNodes = cusGridDetail.getSelectionModel().selections.items;
		 if(checkedNodes.length==0)
		 {
		 Ext.Msg.alert('提示', '未选择任何用户');
		 return ;
		 }
		 var custId = cusGridDetail.getSelectionModel().selections.items[0].data.CUST_ID;
		 window.location.href = '../customerManager/customerBaseInformation.jsp?customerId='+custId;
		 };*/

	});