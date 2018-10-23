Ext.onReady(function() {
	Ext.QuickTips.init();
	var editFlag;
	var leavingNum;
	var stateStore = new Ext.data.ArrayStore({
        fields:['mktMaterialStat','mktMaterialStatText'],
        data:[['1','可用'],['2','不可用']]
    }); 

	// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
//			title : "营销物资管理",
			stUrl : basepath + '/mktMaterialManage-info!indexPage.json',
			// 新增URL，如果不定义则不出现新增按钮
			addUrl : basepath + '/mktMaterialManage-info.json',
			updateUrl : basepath + '/mktMaterialManage-info.json',
			deUrl : basepath + '/mktMaterialManage-info!batchDestroy.json',
			buts : [ {
				text : '出入库管理',
				tooltip : '出入库管理',
				iconCls:'realDaXiaoIconCss',
				handler : function() {
					editinit();
				},
				scope : this
			} ],
			createFun : function(){
					Ext.getCmp("creat_er").setVisible(false);
					Ext.getCmp("update_er").setVisible(false);
					Ext.getCmp("createDate").setVisible(false);
					Ext.getCmp("latelyUpdateDate").setVisible(false);
			},
			editFun :function(){
					Ext.getCmp("creat_er").setVisible(false);
					Ext.getCmp("update_er").setVisible(false);
					Ext.getCmp("createDate").setVisible(false);
					Ext.getCmp("latelyUpdateDate").setVisible(false);
			},
			detailFun:function(){
					Ext.getCmp("creat_er").setVisible(true);
					Ext.getCmp("update_er").setVisible(true);
					Ext.getCmp("createDate").setVisible(true);
					Ext.getCmp("latelyUpdateDate").setVisible(true);
			},
			primary : "mktMaterialId",
			checkbox : true,
			// 定义查询条件Form的高度
//			seFormHeight : 380,
			// 定义增删详情页面弹出窗口高度
//			gridHeight:document.body.scrollHeight-85,
			winHeight : 200,
			// 宽度
			winWidth : 800,
			// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
				// debugger;
			Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
			Ext.getCmp('latelyUpdateDate').setValue(new Date(b.latelyUpdateDate.time));
		},
		// 查询字段定义，若不定义则不出现查询条件From
			selectItems : {
				layout : 'column',
				items : [ {
					columnWidth : .25,
					layout : 'form',
					defaultType : 'textfield',
					border : false,
					items : [ {
						name : 'mktMaterialName',
						xtype : 'textfield',
						fieldLabel : '营销物资',
						anchor : '90%'
					} ]
				} ]
			},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {
				name : 'mktMaterialId',
				header : '营销物资编号'
			}, {
				name : 'mktMaterialName',
				header : '营销物资名称'
			}, {
				name : 'mktMaterialPrice',
				header : '营销物资单价'
			}, {
				name : 'mktMaterialLeavingsNum',
				header : '营销物资剩余数量'
			}, {
				name : 'mktMaterialStat',
				type:'mapping',
				store:stateStore,
				mappingkey : 'mktMaterialStat',
				mappingvalue : 'mktMaterialStatText',
				header : '营销物资状态'
			}, {	
				name : 'remark',
				header : '备注'
			}, {
				name : 'createDate',
				header : '创建日期',
				type : 'date'
			}, {
				id : 'asfasfs',
				name : 'latelyUpdateDate',
				header : '最近更新日期',
				type : 'date'
			}, {
				name : 'creater',
				header : '创建人'
			}, {
				name : 'latelyUpdater',
				header : '最近更新人'
			} ],
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,

			// 新增、修改、详情的form的字段
			fclms : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						name : 'mktMaterialName',
						fieldLabel : '营销物资名称',
						xtype : 'textfield',
						width : 100,
						allowBlank : false,
						maxLength : 200,
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						name : 'mktMaterialPrice',
						fieldLabel : '营销物资单价',
						xtype : 'numberfield',
						width : 100,
						maxLength : 24,
						value :0,
						maxLengthText : '数字长度为24',
						allowBlank:false,//不允许为空
						blankText:"不能为空，请填写",
						anchor : '90%'
					} ]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [ {
						name : 'mktMaterialLeavingsNum',
						fieldLabel : '物资剩余数量',
						xtype : 'numberfield',
						width : 100,
						allowBlank : false,
						maxLength : 200,
						anchor : '90%'
					} ]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{			name : 'mktMaterialStat',
											fieldLabel:'营销物资状态',
											xtype : 'combo',
											store : stateStore,
											width:100,
											allowBlank : false,
											valueField : 'mktMaterialStat',
											hiddenName : 'mktMaterialStat',
											displayField : 'mktMaterialStatText',
											mode : 'local',
											typeAhead : true,
											resizable : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											anchor : '90%'
												}
					         	]
				} ]
			}, {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						name : 'creater',
						id :'creat_er',
						fieldLabel : '创建人',
						width : 100,
						readOnly:true,
						xtype : 'textfield',
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						name : 'latelyUpdater',
						id :'update_er',
						fieldLabel : '最近更新人',
						width : 100,
						readOnly:true,
						xtype : 'textfield',
						anchor : '90%'
					} ]
				} ]
			}, {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						id : 'createDate',
						name : 'createDate',
						fieldLabel : '创建日期',
						width : 100,
						xtype : 'datefield',
						format : 'Y-m-d',
						readOnly : true,
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [ {
						id : 'latelyUpdateDate',
						name : 'latelyUpdateDate',
						fieldLabel : '最近更新日期',
						width : 100,
						xtype : 'datefield',
						format : 'Y-m-d',
						readOnly : true,
						anchor : '90%'
					} ]
				},
				{
					// 特别注意：
					// 必须放置隐藏域的主键
					name : 'mktMaterialId',
					xtype : 'hidden'
				} ]
			} ]
		});

		var matMovePanel = new Ext.FormPanel(
				{	
					closable:true,
					frame : true,
					formId : 'matMovePanel',
					bodyStyle : 'padding:5px 5px 0',
					title : '<span style="font-weight:normal">出入库管理</span>',
					width : '100%',
					height : 300,
					items : [ {
						autoHeight : true,
						items : [ {
							layout : 'column',
							buttonAlign : 'center',
							items : [
									{
										columnWidth : .25,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '营销物资编号',
											maxLength : 100,
											allowBlank : false,
											readOnly : true,
											labelStyle : 'text-align:right;',
											name : 'mktMaterialId',
											id : 'mktMaterialId',
											anchor : '95%'
										},  {
											xtype : 'numberfield',
											fieldLabel : '物资剩余数量',
											name : 'mktMaterialLeavingsNum',
											id : 'mktMaterialLeavingsNum',
											readOnly : true,
											labelStyle : 'text-align:right;',
											anchor : '95%'
										},  {
											xtype : 'textfield',
											fieldLabel : '物资表备注',
											name : 'remark',
											id : 'remark',
											readOnly : false,
											labelStyle : 'text-align:right;',
											anchor : '95%',
												hidden:true
										} ]
									},
									{
										columnWidth : .25,
										layout : 'form',
										items : [{
											xtype : 'textfield',
											fieldLabel : '营销物资名称',
											name : 'mktMaterialName',
											id : 'mktMaterialName',
											readOnly : true,
											labelStyle : 'text-align:right;',
											anchor : '95%'
										},{
											xtype : 'numberfield',
											fieldLabel : '出入库数量',
											name : 'articleNum',
											id : 'articleNum',
											readOnly : false,
											allowBlank:false,//不允许为空
											blankText:"不能为空，请填写",
											labelStyle : 'text-align:right;',
											anchor : '95%'
										}, {
											xtype : 'textfield',
											fieldLabel : '创建人',
											name : 'creater',
											id : 'creater',
											readOnly : false,
											labelStyle : 'text-align:right;',
											anchor : '95%',
											hidden:true
										} ]
									},
									{
										columnWidth : .25,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '营销物资单价',
											name : 'mktMaterialPrice',
											id : 'mktMaterialPrice',
											readOnly : true,
											labelStyle : 'text-align:right;',
											anchor : '95%'
										}, {
											fieldLabel : '出入库方向', 
											allowBlank : false,
											labelStyle : 'text-align:right;',
											xtype : 'combo',
											selectOnFocus :true,
											forceSelection :true,
											triggerAction : 'all',
											mode : 'local',
											store : new Ext.data.ArrayStore(
													{
														id : 0,
														fields : ['movementsKey','movementsValue' ],
														data : [[ 0,'出库' ],[ 1,'入库' ] ]
													}),
											valueField : 'movementsKey',
											displayField : 'movementsValue',
											name : 'outinWay',
											id : 'outinWay',
											anchor : '95%'// 宽度百分比
										} ]

									},
									{
										columnWidth : .25,
										layout : 'form',
										items : [
											{
													fieldLabel : '出入库时间', // 标签
													xtype : 'datefield',
													name : 'outinTime', // name:后台根据此name属性取值
													id : 'outinTime',
													allowBlank : true, // 是否允许为空
													labelStyle : 'text-align:right;',
													anchor : '95%' ,// 宽度百分比
														hidden:true
												}, {
													columnWidth : .25,
													layout : 'form',
													items : [ {
														fieldLabel : '备注', // 标签
														xtype : 'textfield',
														name : 'remarkIo', // name:后台根据此name属性取值
														id : 'remarkIo',
														maxLength :200,
														maxLengthText :'最大长度200字节',
														allowBlank : true, // 是否允许为空
														labelStyle : 'text-align:right;',
														anchor : '95%' // 宽度百分比
													} ]
									}]
									} ],
							buttons : [ {
								text : '保存',
								handler : function() {
									if (!matMovePanel.form.isValid()) {
										Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
										return;
										}
									if (Ext.getCmp("outinWay").getValue() == '0'
											&& Ext.getCmp("articleNum")
													.getValue() > Ext.getCmp(
													"mktMaterialLeavingsNum")
													.getValue()) {
										Ext.Msg.alert('提示', '出库数量多于库存，请重新输入。');
										return;
									} else {
										calculateLeavingNum();
										request();
										addMatMovement.hide();
										matMovePanel.getForm().reset();
									}
								}
							} ]
						} ]
					} ]
				});
		var addMatMovement = new Ext.Window( {
			layout : 'fit',
			width : 800,
			height : 200,
			draggable : true,// 是否可以拖动
			closable : true,// 是否可关闭
			modal : true,
			closeAction : 'hide',
			// iconCls : 'page_addIcon',
			// maximizable: true,
			// maximized:true,
			collapsible : true,// 是否可收缩
			titleCollapse : true,
			buttonAlign : 'right',
			border : false,
			animCollapse : true,
			pageY : 20,
			// pageX : document.body.clientWidth / 2 - 420 / 2,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [ matMovePanel ]
		});

		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			frame : true,
			items : [ listPanel ]
		});
		function showDetails() {
			addMatMovement.show();
		}
		;

		// 打开出入库管理小窗口
		function editinit() {
			var infoRecord = listPanel.grid.selModel.getSelections();
			var record = listPanel.grid.getSelectionModel().getSelected();
			if (infoRecord == null || infoRecord == '') {
				Ext.Msg.alert('提示', '请选择一行数据');
			} else {
				matMovePanel.getForm().loadRecord(record);
				addMatMovement.show();
			}
		}

		listPanel.grid.on('rowclick', function(grid, rowIndex, event) {
			var record = listPanel.grid.getSelectionModel().getSelected();
			editFlag = 1;
			});

		function request() {
			var record = listPanel.grid.getSelectionModel().getSelected();
//			if (editFlag == 1) {
//				var infoRecord = listPanel.grid.getSelectionModel()
//						.getSelected();
//			}
			Ext.Ajax.request( {
				url : basepath + '/mktMaterialIoManage-info!batchUpdate.json',
				method : 'POST',
				params : {
					'id' : Ext.getCmp("mktMaterialId").getValue(),
					'articleNum' : Ext.getCmp("articleNum").getValue(),
					'leavingNum':leavingNum,
					'outinWay' : Ext.getCmp("outinWay").getValue(),
					'remarkIo' : Ext.getCmp("remarkIo").getValue()
				},
				waitMsg : '正在保存数据,请等待...' ,
				 success : function(){
				 Ext.MessageBox.alert('保存操作', '保存成功！');
				 listPanel.grid.getStore().reload();
				 }
				// failure : checkResult
			}
				);
		};

		function calculateLeavingNum() {
			if (Ext.getCmp("outinWay").getValue() == '1') {
				leavingNum = Ext.getCmp("mktMaterialLeavingsNum").getValue()
						+ Ext.getCmp("articleNum").getValue();
			} else if (Ext.getCmp("outinWay").getValue() == '0') {
				leavingNum = Ext.getCmp("mktMaterialLeavingsNum").getValue()
						- Ext.getCmp("articleNum").getValue();
			}
		}

		function outinCheck() {
			if (Ext.getCmp("outinWay").getValue() == '0'
					&& Ext.getCmp("articleNum").getValue() > Ext.getCmp(
							"mktMaterialLeavingsNum").getValue()) {
				Ext.Msg.alert('提示', '出库数量多于库存，请重新输入。');
				return;
			}
		}

	});