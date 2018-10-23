Ext.onReady(function() {
	Ext.QuickTips.init();
		// 审批状态下拉框的数据查询
		var appStatusStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=APPROVEL_STATUS'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		appStatusStore.load();
		//证件类型
		var certTypStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000040'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		certTypStore.load();
		//客户类型
		var custTypStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000080'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		custTypStore.load();
		//客户级别
		var custLevStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=CDE0100016'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		custLevStore.load();
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户管理->待认领客户查询",
			closable:false,	//在选项卡上，不显示关闭按钮
			stUrl : basepath + '/latentApplyQuery.json',
			applyUrl : basepath + '/ocrm_f_ci_latent_apply_info.json',
			primary : "CUST_ID",
			dbclick : false,	
			checkbox : true,
			// 定义查询条件Form的高度
			seFormHeight : 100,
			// 定义增删详情页面弹出窗口高度
			winHeight : 250,
			//宽度
			winWidth : 600,
		
			buts : [
			{
				id : 'renling',
				xtype : 'button',
				tooltip : '认领',
				text : '认领',
				iconCls:'publishIconCss',
				listeners : {
					click : function(n) {
						if (listPanel.grid.selModel.hasSelection()) {
							var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
							var recordsLen = records.length;// 得到行数组的长度
							if (recordsLen > 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行认领！");
							} else {
								
								var record = listPanel.grid.getSelectionModel()
										.getSelected();
								debugger;
								var id = record.get(listPanel.primary);
								listPanel.opUrl = listPanel.applyUrl;
								var winButsArray = [];
								winButsArray.push({text : "保存",handler : listPanel.save, scope : listPanel});
								winButsArray.push({text : "关闭",handler : listPanel.closeWin,scope : listPanel});
								listPanel.winButs = winButsArray;
								listPanel.showWin();
					    		if(listPanel.editFun)
					    			listPanel.editFun();
					    		if(listPanel.stUrl)
					    			listPanel.seOneRecord(id);
					    		else if(listPanel.demoData)
					    			listPanel.fp.getForm().loadRecord(record);
							}
						} else {
							Ext.Msg.alert("提示", "请先选择要认领的记录!");
						}
					}
			}
			}],
		
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems : {
				layout : 'column',
				items : [ {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',width : '100',anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ {name : 'CUST_ZH_NAME',xtype : 'textfield',fieldLabel : '客户中文名称',width : '100',anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					items : [ {store : certTypStore,xtype : 'combo',resizable : true,fieldLabel : '证件类型',name : 'CERT_TYPE',hiddenName : 'CERT_TYPE',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',
						emptyText : '请选择',selectOnFocus : true,width : '100',anchor : '90%'} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					items : [ {name : 'CERT_NUM',xtype : 'textfield',fieldLabel : '证件号码',width : '100',anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					items : [ {store : custTypStore,xtype : 'combo',resizable : true,fieldLabel : '客户类型',name : 'CUST_TYP',hiddenName : 'CUST_TYP',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',
						emptyText : '请选择',selectOnFocus : true,width : '100',anchor : '90%'} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ {store : custLevStore,xtype : 'combo',resizable : true,fieldLabel : '客户级别',name : 'CUST_LEV',hiddenName : 'CUST_LEV',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',
						emptyText : '请选择',selectOnFocus : true,width : '100',anchor : '90%'} ]
				} ]
			},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {
				name : 'custId',
				header : '客户编号',
				mapping :'CUST_ID'
			}, {
				name : 'CUST_ZH_NAME',
				header : '客户中文名称'
			},  {
				name : 'CERT_TYPE',
				header : '证件类型',
				type : 'mapping',
				store : certTypStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				name : 'CERT_NUM',
				sortable : true,
				header : '证件号码'
			}, {
				name : 'CUST_TYP',
				sortable : true,
				header : '客户类型',
				type : 'mapping',
				store : custTypStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				name : 'CUST_LEV',
				sortable : true,
				header : '客户级别',
				type : 'mapping',
				store : custLevStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				name : 'AFFI_CUST_MANAGER',
				header : '客户经理'
			}],
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			
			// 新增、修改、详情的form的字段
			fclms : [{
				layout : 'form',
				items : [{
								layout : 'column',
								items:[{columnWidth : .5,
									layout : 'form',
									items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',readOnly :true,width : '100',anchor : '90%'}]
								},{
									columnWidth : .5,
									layout : 'form',
									items : [ {name : 'CUST_ZH_NAME',xtype : 'textfield',fieldLabel : '客户中文名称',readOnly :true,width : '100',anchor : '90%'
										}]
								}]
			             },
		             {
			            	 layout:'column',
			            	 items:[{columnWidth : .5,
			            		     layout : 'form',
			            		     items : [ {name : 'applyPeriod',xtype : 'numberfield',fieldLabel : '认领期限',width : '100',anchor : '90%'
									}]},{
										columnWidth : .5,
										layout : 'form',
										items : [ {name : 'createDate',xtype : 'datefield',format : 'Y-m-d',editable : false,fieldLabel : '创建日期',width : '100',anchor : '90%',value : new Date(),hidden : true
										},{name : 'approvelStatus',xtype : 'textfield',fieldLabel : '审批状态', width : '100',anchor : '90%',hidden : true,value : '1'}
									]}
									]},
			             	{
								columnWidth : .5,
								layout : 'form',
								border : false,
								items : [ {name : 'applyReason',xtype : 'textarea',fieldLabel : '认领理由',width : '100',anchor : '90%'
								}, {name : 'applyUsername',xtype : 'textfield',fieldLabel : '认领人名称',width : '100',anchor : '90%',hidden : true,value : __userName
								}, {name : 'applyUser',xtype : 'textfield',fieldLabel : '认领人',width : '100',anchor : '90%',value : __userId,hidden : true
								},{name : 'applyInit',xtype : 'textfield',fieldLabel : '认领机构',width : '100',anchor : '90%',value : __units,hidden : true
								},{name : 'applyInitname',xtype : 'textfield',fieldLabel : '认领机构名称',width : '100',anchor : '90%',value:__unitname,hidden : true}
								
								
								]
			             }
			]
			}]
		});
		
		// 审批最终展现的panel
		var spListPanel = new Mis.Ext.CrudPanel( {
			id : "spListPanel",
			title : "客户管理->待审批客户查询",
			closable:false,	//在选项卡上，不显示关闭按钮
			stUrl : basepath + '/ocrm_f_ci_latent_apply_info!indexPage.json',
			approvelURl : basepath + '/ocrm_f_ci_latent_apply_info!approvelBack.json',
			detailUrl : basepath + '/ocrm_f_ci_latent_apply_info!indexPage.json',
			primary : "claimtagNo",
			checkbox : true,
			// 定义查询条件Form的高度
			seFormHeight : 80,
			// 定义增删详情页面弹出窗口高度
			winHeight : 300,
			//宽度
			winWidth : 600,
		
			spIdStr : '',
			
		buts : [
		{
			id : 'shenpi',
			xtype : 'button',
			tooltip : '审批',
			text : '审批',
			iconCls:'shenpiIconCss ',
			listeners : {
				click : function(n) {
					if (spListPanel.grid.selModel.hasSelection()) {
						var records = spListPanel.grid.selModel.getSelections();// 得到被选择的行的数组
						var recordsLen = records.length;// 得到行数组的长度
						if (recordsLen > 1) {
							Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行审批！");
						} else {
							var record = spListPanel.grid.getSelectionModel()
									.getSelected();
							var id = record.get(spListPanel.primary);
							spListPanel.opUrl = spListPanel.approvelURl;
							spListPanel.spIdStr = records[0].get(spListPanel.primary);
							var approvelStatus = records[0].get('approvelStatus');
							var winButsArray = [];
							//审批状态中2为已审批
							if (approvelStatus == '1') {
								winButsArray.push({text : "通过",handler : spListPanel.approvel , scope : spListPanel});
								winButsArray.push({text : "不通过",handler : spListPanel.approvelBack, scope : spListPanel});
							}
							winButsArray.push({text : "关闭",handler : spListPanel.closeWin,scope : spListPanel});
							spListPanel.winButs = winButsArray;
							spListPanel.showWin();
				    		if(spListPanel.editFun)
				    			spListPanel.editFun();
				    		if(spListPanel.stUrl)
				    			spListPanel.seOneRecord(id);
				    		else if(spListPanel.demoData)
				    			spListPanel.fp.getForm().loadRecord(record);
						}
					} else {
						Ext.Msg.alert("提示", "请先选择要审批的记录!");
					}
				}
		}
		}],
		
		//审批通过
		approvel : function() {
			Ext.Ajax
			.request({
				url : basepath + '/ocrm_f_ci_latent_apply_info!approvel.json',
				params : {
					idStr : spListPanel.spIdStr
				},
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				method : 'POST',
				scope : spListPanel,
				success : function() {
					Ext.Msg.alert('提示', '操作成功');
					spListPanel.loadCurrData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
					spListPanel.loadCurrData();
				}
			});
			spListPanel.closeWin();
		},
			
		//审批不通过
		approvelBack : function() {
				Ext.Ajax
				.request({
					url : spListPanel.approvelURl,
					params : {
						idStr : spListPanel.spIdStr
					},
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					method : 'POST',
					scope : spListPanel,
					success : function() {
						Ext.Msg.alert('提示', '操作成功');
						spListPanel.loadCurrData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						spListPanel.loadCurrData();
					}
				});
				spListPanel.closeWin();
		},	
		
		// 查询字段定义，若不定义则不出现查询条件From
			selectItems : {
				layout : 'column',
				items : [ {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 80,
					defaultType : 'textfield',
					border : false,
					items : [ {
						store : appStatusStore,xtype : 'combo',resizable : true,fieldLabel : '审批状态',name : 'approvelStatus',hiddenName : 'approvelStatus',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,
						width : '100',anchor : '90%'
					} ]
				}, {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',width : '100',anchor : '90%'
					} ]
				}, {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ {name : 'applyUsername',xtype : 'textfield',fieldLabel : '认领人名称',width : '100',anchor : '90%'
					} ]
				} ]
			},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {
				name : 'claimtagNo'
			}, {
				name : 'approvelStatus',
				header : '审批状态',
				type : 'mapping',
				store : appStatusStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				name : 'custId',
				header : '客户编号'
			}, {
				name : 'applyUser',
				header : '认领人'
			}, {
				name : 'applyUsername',
				header : '认领人名称'
			}, {
				name : 'applyInit',
				header : '认领机构'
			}, {
				name : 'applyInitname',
				header : '认领机构名称'
			}, {
				name : 'applyPeriod',
				header : '认领期限'
			}, {
				name : 'applyReason',
				header : '认领理由'
			}, {
				name : 'createDate',
				header : '创建日期',
				type : 'date'
			}],
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
				//debugger;
				Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
			},
			// 新增、修改、详情的form的字段
			fclms : [{
				layout : 'form',
				items : [{
								layout : 'column',
								items:[{columnWidth : .5,
									layout : 'form',
									items : [ {name : 'custId',xtype : 'textfield',readOnly :true,disabled:true,fieldLabel : '客户编号',width : '100',anchor : '90%'}]
								},{
									columnWidth : .5,
									layout : 'form',
									items : [ {
										store : appStatusStore,xtype : 'combo',resizable : true,fieldLabel : '审批状态',name : 'approvelStatus',hiddenName : 'approvelStatus',
										valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,
										width : '100',anchor : '90%'
									}]
								}]
			             },
			             {
			            	 layout:'column',
			            	 items:[{columnWidth : .5,
			            		     layout : 'form',
			            		     items : [ {name : 'applyPeriod',xtype : 'textfield',fieldLabel : '认领期限',disabled:true,width : '100',anchor : '90%',readOnly :true
									}]},{
										columnWidth : .5,
										layout : 'form',
										items : [ {id : 'createDate', name : 'createDate',xtype : 'datefield',format : 'Y-m-d',readOnly :true,disabled:true,fieldLabel : '创建日期',width : '100',anchor : '90%'
										}]
									}]
			             },{
								columnWidth : .5,
								layout : 'form',
								border : false,
								items : [ {name : 'applyReason',xtype : 'textarea',readOnly :true,disabled:true,fieldLabel : '认领理由',width : '100',anchor : '90%'
								}]
			             }
			]
			}]
			
		});
		
		
		var tabs = new Ext.TabPanel({
			xtype:"tabpanel",			   			   
			region:"center",
			activeTab: 0,
		    items: [listPanel,spListPanel]
		});
		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ tabs ]
		});

	});