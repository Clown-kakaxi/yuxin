Ext.onReady(function() {

	var searchPanel = new Ext.form.FormPanel({
				labelWidth : 100,
				labelAlign:'right',
				height : 160,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .3,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											labelStyle : {
												width : '120px'
											},
											Width : '100',
											id : 'messageSymbol',
											name : 'messageSymbol',
											fieldLabel : '报文文号',
											anchor : '90%'
										},{
											xtype:"textfield",
											id:'taskType',
											name:'taskType',
											fieldLabel:'方案类型',
											labelStyle:{
												width:'120px'
											},
											width:'100',
											anchor : '90%'
										},{
											fieldLabel : '开始时间',
											xtype : 'datefield',
											id : 'createDateS',
											name : 'createDateS',
											anchor : '90%',
											labelStyle : {
												width : '120px'
											},
											Width : '100'
										}]
							}, {
								columnWidth : .3,
								layout : 'form',
								items : [{

									xtype:'textfield',
									id:'taskIds',
									name:'taskIds',
									fieldLabel:'方案ID',
									labelStyle:{
										width:'120px'
									},
									width:'100',
									anchor:'90%'
								},{

									xtype:'textfield',
									id:'taskRelation',
									name:'taskRelation',
									fieldLabel:'方案关联',
									labelStyle:{
										width:'120px'
									},
									width:'100',
									anchor:'90%'
								},{
									xtype : 'datefield',
									fieldLabel : '结束时间',
									name : 'createDateE',
									id : 'createDateE',
									anchor : '90%',
									labelStyle : {
										width : '120px'
									},
									width : '100'
								}]
							}, {
								columnWidth : .4,
								layout : 'form',
								items:[{
											xtype : 'textfield',
											labelStyle : {
												width : '120px'
											},
											Width : '100',
											id : 'planName',
											name : 'planName',
											fieldLabel : '方案名称',
											anchor : '90%'
										},{
											xtype : 'textfield',
											labelStyle : {
												width : '120px'
											},
											Width : '100',
											id : 'proName',
											name : 'proName',
											fieldLabel : '产品名称',
											anchor : '90%'
										},{
											xtype:"textfield",
											id:'createPerson2',
											name:'createPerson2',
											fieldLabel:'创&nbsp;建&nbsp;人',
											labelStyle:{
												width:'120px'
											},
											width:'100',
											anchor:'90%'
								}]
							}
							]
				}],
				buttonAlign:'center',
				buttons:[
				{
					text:'查询',
					handler:function()
					{
						
					}
				}
				]
				

			});
	var growthColumn = new Ext.ux.grid.ProgressColumn({
		header : "方案进度",
		dataIndex : 'progress',
		width : 100,
		textPst : '%',
		colored : true
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var columns = new Ext.grid.ColumnModel([sm,{
							header:'报文文号',
							width:100,
							align:'center',
							dataIndex:'reportId',
							sortable:true
						},{
							header : '方案ID',
							width : 100,
							align : 'center',
							dataIndex : 'taskId',
							sortable : true
						}, {
							header : '方案名称',
							width : 150,
							align : 'center',
							dataIndex : 'taskName',
							sortable : true
						}, {
							header : '方案类型',
							width : 100,
							align : 'center',
							dataIndex : 'taskType',
							sortable : true
						}, {
							header : '方案关联',
							width : 100,
							align : 'center',
							dataIndex : 'taskRelation',
							sortable : true
						}, {
							header : '产品名称',
							width : 200,
							align : 'center',
							dataIndex : 'proName',
							sortable : true
						}, {
							header : '创建时间',
							width : 100,
							align : 'center',
							dataIndex : 'beginDate',
							sortable : true
						}, {
							header : '方案状态',
							width : 100,
							align : 'center',
							dataIndex : 'taskState',
							sortable : true
						}, {
							header : '发行机构',
							width : 190,
							align : 'center',
							dataIndex : 'taskOrg',
							sortable : true
						},growthColumn
//						,{
//							header:'传播名称',
//							width:150,
//							align:'center',
//							dataIndex:'spreadName',
//							sortable:true
//						}
						]);

	var record = Ext.data.Record.create([{
				name:'reportId'
			},{
				name : 'taskId'
			}, {
				name : 'taskName'
			}, {
				name : 'taskType'
			}, {
				name : 'taskRelation'
			}, {
				name : 'proName'
			}, {
				name : 'beginDate'
			}, {
				name : 'taskState'
			}, {
				name : 'taskOrg'
			},{
				name:'progress'
			}]);

	var data = {
		rows : [{	
					"reportId":"2011年1620号",
					"taskId" : "1",
					"taskName" : "营销方案一",
					"taskType" : "类型1",
					"taskRelation":"方案关联活动",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "线束",
					"taskOrg" : "一号分公司",
					"progress":56
				},{	
					"reportId":"2011年1621号",
					"taskId" : "2",
					"taskName" : "营销方案二",
					"taskType" : "类型3",
					"taskRelation":"方案关联传播",
					"proName":"产品二",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":92
				},{	
					"reportId":"2011年1622号",
					"taskId" : "3",
					"taskName" : "营销方案三",
					"taskType" : "类型2",
					"taskRelation":"方案关联活动",
					"proName":"产品三",
					"beginDate" : "2011-09-01",
					"taskState" : "运行",
					"taskOrg" : "一号分公司",
					"progress":17
				},{	
					"reportId":"2011年1623号",
					"taskId" : "4",
					"taskName" : "营销方案四",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":100
				},{	
					"reportId":"2011年1624号",
					"taskId" : "5",
					"taskName" : "营销方案五",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":80
				},{	
					"reportId":"2011年1625号",
					"taskId" : "6",
					"taskName" : "营销方案六",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":70
				},{	
					"reportId":"2011年1123号",
					"taskId" : "7",
					"taskName" : "营销方案七",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":20
				},{	
					"reportId":"2011年1323号",
					"taskId" : "8",
					"taskName" : "营销方案八",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":90
				},{	
					"reportId":"2011年1653号",
					"taskId" : "9",
					"taskName" : "营销方案九",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":50
				},{	
					"reportId":"2011年1723号",
					"taskId" : "10",
					"taskName" : "营销方案十",
					"taskType" : "类型4",
					"taskRelation":"方案关联传播",
					"proName":"产品一",
					"beginDate" : "2011-09-01",
					"taskState" : "计划",
					"taskOrg" : "一号分公司",
					"progress":30
				}]

	};
	var reader = new Ext.data.JsonReader({
				totalProperty : 'num',
				idProperty : 'taskId',
				root : 'rows'
			}, record);
	var store = new Ext.data.Store({
				reader : reader
			});

	store.loadData(data);
	var listPanel = new Ext.grid.GridPanel({
				store : store,
				frame : true,
				cm : columns,
				sm:sm,
				stripeRows : true,
				plugins : [ growthColumn ],
				tbar : [{
							text : '查看',
							iconCls : 'page_addIcon',
							handler : function() {
//								addInit();
							}
						},'-',{
							text : '创建',
							iconCls : 'page_addIcon',
							handler : function() {
								addInit();
							}
						}, '-', {
							text : '修改',
							iconCls : 'page_editIcon',
							handler : function() {
								addInit();
							}

						}, '-', {
							text : '删除',
							iconCls : 'page_delIcon',
							handler : function() {
								confirm("确定删除吗?");
							}
//						}, '-', {
//							text : '跟踪',
//							iconCls : 'page_cloIcon',
//							handler : function() {
//								followInit();
////								confirm("执行后将为每个客户生成营销活动，确定执行吗?");
//							}
//						}, '-', {
//							text : '总结',
//							iconCls : 'page_cloIcon',
//							handler : function() {
//								sumUpInit();
//							}
						},'-',{
							text : '关联活动',
							iconCls : 'page_cloIcon',
							handler : function() {
//								spreadWindow.show();
							}
						},'-',{
							text : '关联传播',
							iconCls : 'page_cloIcon',
							handler : function() {
								spreadWindow.show();
							}
						},'-',{
							text:'营销内容',
							handler:function(){
//								win.show();
								document.location='mktcontent.html';
							}
						},'-',{
							text:'设计',
							handler:function(){
//								win.show();
								document.location='mktdesign/mktdesign.html';
							}
						},'-',{
							text:'物流',
							handler:function(){
//								win.show();
								document.location='mktLogistics/mktLogistics.html';
							}
						}],
				region : 'center',
				bbar : {
					xtype : 'paging',
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					emptyMsg : "没有符合条件的记录",
					items : ['-', '&nbsp;&nbsp;', {
								xtype : 'textfield',
								value : '10'
							}]
				}
			});

	StepGrid=Ext.extend(Ext.grid.EditorGridPanel, {
		frame: true,
	    title: '步骤',
	    height: '180',
	    anchor:"100%",
	    initComponent : function() {
        this.viewConfig = {
            forceFit: true
        };
        this.tbar = this.buildTopToolbar();
        StepGrid.superclass.initComponent.call(this);
	    },
	    buildTopToolbar : function() {
	        return [{
	            text: '添加进程',
	            iconCls: 'silk-add',
	            handler: this.onAdd,
	            scope: this
	        }, '-', {
	            text: '删除进程',
	            iconCls: 'silk-delete',
	            handler: this.onDelete,
	            scope: this
	        }, '-'];
	    },
	    onAdd : function(btn, ev) {
	        var u = new this.store.recordType({
	            id:"",
				steps:"",
				content:"",
				joinPerson:"",
				aditionPerson:"",
				completion:"",
				isMind:"",
				budget:""
	        });
	        this.stopEditing();
	        this.store.insert(0, u);
	        this.startEditing(0, 1);
	    },

  	  onDelete : function(btn, ev) {
	        var index = this.getSelectionModel().getSelectedCell();
	        if (!index) {
	            return false;
	        }
	        var rec = this.store.getAt(index[0]);
	        this.store.remove(rec);
	    }
	});
	var stepData={rows:[{
			"id":"1",
			"steps":"进程一",
			"content":"印刷",
			"joinPerson":"张大",
			"aditionPerson":"张三",
			"completion":"20%",
			"isMind":"是",
			"budget":"10,000,000"
		},{
			"id":"2",
			"steps":"进程二",
			"content":"制作",
			"joinPerson":"孟九",
			"aditionPerson":"李四",
			"completion":"30%",
			"isMind":"否",
			"budget":"200,000"
		},{
			"id":"3",
			"steps":"进程三",
			"content":"物流",
			"joinPerson":"陈二",
			"aditionPerson":"王五",
			"completion":"100%",
			"isMind":"是",
			"budget":"10,000"
		}]};
	var stepRecord = Ext.data.Record.create([{
				name : 'id'
			}, {
				name : 'steps'
			},{
				name:'content'
			},{
				name:'joinPerson'
			},{
				name:'aditionPerson'
			},{
				name:'completion'
			},{
				name:'isMind'
			},{
				name:'budget'
			}]);
	var stepReader = new Ext.data.JsonReader({
				idProperty : 'id',
				root : 'rows'
			}, stepRecord);		
	var stepStore=new Ext.data.Store({
				reader : stepReader
			});

	stepStore.loadData(stepData);
	var textField =  new Ext.form.TextField();
	var combBox=new Ext.form.ComboBox({
		mode:'local',
		store : new Ext.data.ArrayStore({
			fields : ['myId', 'displayText'],
			triggerAction: 'all',
			typeAhead: true,
			lazyRender: true,
			data : [["是", '是'], 
			        ["否", '否']]}),
		valueField : 'myId',
		displayField : 'displayText'
	});
	var cmm=[
		{header: "ID", sortable: false, dataIndex: 'id'},
		{header: "进程", sortable: true, dataIndex: 'steps',editor: textField},
		{header: "工作内容", sortable: true, dataIndex: 'content',editor: textField},
		{header: "参与人", sortable: true, dataIndex: 'joinPerson',editor: textField},
		{header: "责任人", sortable: true, dataIndex: 'aditionPerson',editor: textField},
		{header: "完成度", sortable: true, dataIndex: 'completion',editor: textField},
		{header: "是否设置提醒", sortable: true, dataIndex: 'isMind',editor: combBox},
		{header: "预算", sortable: true, dataIndex: 'budget',editor: textField}
		
	];
	var stepGrid=new StepGrid({
		store:stepStore,
		width:200,
		columns:cmm,
		 listeners: {
            rowclick: function(g, index, ev) {
                var rec = g.store.getAt(index);
//                userForm.loadRecord(rec);
            },
            destroy : function() {
//                userForm.getForm().reset();
            }
        }
	});
	
	var sumUpForm=new Ext.form.FormPanel({
		labelWidth:150,
		height:300,
		frame:'center',
		autoScroll:true,
		buttonAlign:'center',
		items:[{
			layout:'column',
			items:[{
				columnWidth : .5,
				layout : 'form',
				items:[{
					id:'sumUpPerson',
					name:'sumUpPerson',
					fieldLabel:'总结人',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				},{
					id:'sumUpTime',
					name:'sumUpTime',
					fieldLabel:'总结时间',
					xtype:'datefield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				},{
					id:'sumUpUploadPerson',
					name:'sumUpUploadPerson',
					fieldLabel:'上传人',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				},{
					id:'sumUpResult',
					name:'sumUpResult',
					fieldLabel:'各个工作总结汇总',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					id:'sumUpDataSave',
					name:'sumUpDataSave',
					fieldLabel:'资料保管',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				},{
					id:'sumUpDataName',
					name:'sumUpDataName',
					fieldLabel:'资料名称',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				},{
					id:'sumUpDataType',
					name:'sumUpDataType',
					fieldLabel:'资料类型',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				},{
					id:'sumUpUploadTime',
					name:'sumUpUploadTime',
					fieldLabel:'上传时间',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					anchor : '90%'
				}]
			}]
		},{
			layout:'form',
			buttonAlign:'center',
			items:[{
				xtype : 'textarea',
				labelStyle : {
					width : '120px'
				},
				width : 200,
				fieldLabel : '总结内容',
				id : 'followRemark',
				name : 'followRemark',
				anchor : '90%'
			}],
			buttons : [{
//							text : '完  成',
//							handler : function() {
//								alert("所有进程已全部完成");
//								followTaskWindow.hide();
//							}
//						},{
							text : '保  存',
							handler : function() {
								alert("新增成功");
								sumUpWindow.hide();
//								followTaskWindow.hide();
	//							editPlanWindow.show();
	//							addPlanWindow.hide();
							}
						}, {
							text : '取  消',
							handler : function() {
								sumUpWindow.hide();
//								followTaskWindow.hide();
//								addPlanWindow.hide();
							}
						}]
		}]
	});
	
	var followForm=new Ext.form.FormPanel({
		labelWidth:150,
		height:300,
		frame:'center',
		autoScroll:true,
		buttonAlign:'center',
		items:[{
			layout:'column',
			items:[{
				columnWidth : .5,
				layout : 'form',
				items:[{
					id:'followStepsId',
					name:'followStepsId',
					fieldLabel:'步骤ID',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					width : 200,
					value:'步骤1',
					anchor : '90%'
				},{
					id:'followStepsContent',
					name:'followStepsContent',
					fieldLabel:'内容',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'这是一个好方案',
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsChargePerson',
					name:'followStepsChargePerson',
					fieldLabel:'负责人',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:"张三",
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsReplyResult',
					name:'followStepsReplyResult',
					fieldLabel:'反馈结果',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:"好方案",
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsBudgets',
					name:'followStepsBudgets',
					fieldLabel:'分配预算',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'200,000',
					width : 200,
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					id:'followStepsTitle',
					name:'followStepsTitle',
					fieldLabel:'标题',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'组织竞赛',
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsJoinPerson',
					name:'followStepsJoinPerson',
					fieldLabel:'参与人',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'李四,王五',
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsStates',
					name:'followStepsStates',
					fieldLabel:'当前执行状态',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'执行完成',
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsWorkSum',
					name:'followStepsWorkSum',
					fieldLabel:'工作总结',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'干得好',
					width : 200,
					anchor : '90%'
				},{
					id:'followStepsBudgetSpend',
					name:'followStepsBudgetSpend',
					fieldLabel:'使用预算',
					xtype:'textfield',
					labelStyle:{
						width:'120px'
					},
					value:'150,000',
					width : 200,
					anchor : '90%'
				}]
			}]
		},{
			layout:'form',
			buttonAlign:'center',
			items:[{
				xtype : 'textarea',
				labelStyle : {
					width : '120px'
				},
				width : 200,
				fieldLabel : '备注',
				id : 'followRemark',
				name : 'followRemark',
				anchor : '90%'
			}],
			buttons : [{
							text : '完  成',
							handler : function() {
								alert("所有进程已全部完成");
								followTaskWindow.hide();
							}
						},{
							text : '保  存',
							handler : function() {
								alert("新增成功");
								followTaskWindow.hide();
	//							editPlanWindow.show();
	//							addPlanWindow.hide();
							}
						}, {
							text : '取  消',
							handler : function() {
								followTaskWindow.hide();
//								addPlanWindow.hide();
							}
						}]
		}]
	});
	var spreadSm = new Ext.grid.CheckboxSelectionModel();
	var spreadColumns = new Ext.grid.ColumnModel([sm,{
							header : '传播ID',
							width : 100,
							align : 'center',
							dataIndex : 'spreadId',
							sortable : true
						}, {
							header : '传播名称',
							width : 150,
							align : 'center',
							dataIndex : 'spreadName',
							sortable : true
						}, {
							header : '传播类型',
							width : 150,
							align : 'center',
							dataIndex :'spreadType',
							sortable : true
						}, {
							header : '传播主题',
							width : 150,
							align : 'center',
							dataIndex : 'spreadTopic',
							sortable : true
						}, {
							header : '传播方式',
							width : 150,
							align : 'center',
							dataIndex : 'spreadMode',
							sortable : true
						}, {
							header : '发行时间',
							width : 150,
							align : 'center',
							dataIndex : 'publishTime',
							sortable : true
						}, {
							header : '发行状态',
							width : 150,
							align : 'center',
							dataIndex : 'publishState',
							sortable : true
						}, {
							header : '发行单位',
							width : 150,
							align : 'center',
							dataIndex : 'publishUnion',
							sortable : true
						}]);

	var spreadRecord = Ext.data.Record.create([{
				name : 'spreadId'
			}, {
				name : 'spreadName'
			}, {
				name : 'spreadType'
			}, {
				name : 'spreadTopic'
			}, {
				name : 'spreadMode'
			}, {
				name : 'publishTime'
			},{
				name:'publishState'
			},{
				name:'publishUnion'
			}]);

	var spreadData = {
		num : 1,
		rows : [{
					"spreadId" : "1",
					"spreadName" : "营销传播一",
					"spreadType" : "传播类型一",
					"spreadTopic" : "传播类型",
					"spreadMode" : "网络",
					"publishTime" : "2011-09-12",
					"publishState":"正在执行",
					"publishUnion":"一号公司"
				}]

	};
	var spreadReader = new Ext.data.JsonReader({
				totalProperty : 'num',
				idProperty : 'activityId',
				root : 'rows'
			}, spreadRecord);
	var spreadStroe = new Ext.data.Store({
				reader : spreadReader
			});

	spreadStroe.loadData(spreadData);
	var spreadGrid= new Ext.grid.GridPanel({
		store:spreadStroe,
		frame : true,
		cm : spreadColumns,
		sm:spreadSm
	});
	var spreadForm=new Ext.form.FormPanel({
		region:'center',
		buttonAlign:'center',
		layout:"fit",
		items:[spreadGrid],
		buttons : [{
						text : '传播',
						handler : function() {
							alert("传播成功");
							spreadWindow.hide();
						}
					}, {
						text : '取  消',
						handler : function() {
							spreadWindow.hide();
						}
					}]
	});
	// 新增窗口展示的from
	var addPlanForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 300,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .5,
								layout : 'form',
								items : [{
											id : 'planAddName',
											name : 'planAddName',
											xtype : 'textfield',
											fieldLabel : '营销方案名称',
											labelStyle : {
												width : '120px'
											},
											width : 200,
											anchor : '90%'
										},{
											id : 'planAddProductType',
											name : 'planAddProductType',
											xtype : 'combo',
											fieldLabel : '产品类型',
											labelStyle : {
												width : '120px'
											},
											width : 200,
											anchor : '90%',
											mode : 'local',
											store : new Ext.data.ArrayStore({
														fields : ['myId', 'displayText'],
														data : [[1, '理财'], 
															    [2, '基金'],
																[3, '债券']]
													}),
											valueField : 'myId',
											displayField : 'displayText'
										},{
											id:'planAddCreateName',
											name:'planAddCreateName',
											fieldLabel:'发起人姓名',
											xtype:'textfield',
											width : 200,
											anchor : '90%',
											labelStyle : {
												width : '120px'
											}
										},{
											id:'planAddSalesTotal',
											name:'planAddSalesTotal',
											fieldLabel:'产品总销售额度',
											labelStyle:{
												width:'120px'
											},
											xtype:'textfield',
											width:200,
											anchor : '90%'
										},{
											id:'planAddCreateOrg',
											name:'planAddCreateOrg',
											fieldLabel:'营销发起机构',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'textfield'
										},{
											id:'planAddTotalBudget',
											name:'planAddTotalBudget',
											fieldLabel:'总预算值',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'textfield'
										},{
											id:'planAddBudgetLeft',
											name:'planAddBudgetLeft',
											fieldLabel:'剩余预算',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'textfield'
										},{
											id:'planAddCreateTime',
											name:'planAddCreateTime',
											fieldLabel:'发起时间',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'datefield'
										},{
											id:'planAddResearchType',
											name:'planAddResearchType',
											fieldLabel:'调研类别',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'combo',
											mode : 'local',
											store : new Ext.data.ArrayStore({
														fields : ['myId', 'displayText'],
														data : [[1, '行业'], 
															    [2, '产品'],
																[3, '同业'],
																[4, '市场']]
													}),
											valueField : 'myId',
											displayField : 'displayText'
										},{
											id:'planAddDataUpload',
											name:'planAddDataUpload',
											fieldLabel:'文件上传',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'textfield'
										},{
											id:'planAddSalesContent',
											name:'planAddSalesContent',
											fieldLabel:'卖点内容',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'textfield'
										},{
											id:'planAddStepsRemind',
											name:'planAddStepsRemind',
											fieldLabel:'进度提醒',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'combo',
											mode : 'local',
											store : new Ext.data.ArrayStore({
														fields : ['myId', 'displayText'],
														data : [[1, '是'], 
															    [2, '否']]
													}),
											valueField : 'myId',
											displayField : 'displayText'
										}]
							}, 
							{
								columnWidth : .5,
								layout : 'form',
								items : [{
									id:'planAddProductName',
									name:'planAddProductName',
									fieldLabel : '产品名称',
									anchor : '90%',
									xtype : 'textfield',
									labelStyle : {
										width : '120px'
									},
									Width : 200
								},{
									xtype : 'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									fieldLabel : '产品发起单位',
									id : 'createTime',
									name : 'createTime',
									anchor : '90%'
								},{
									id:'planAddProductSaleTime',
									name:'planAddProductSaleTime',
									fieldLabel:'产品营销日期',
									xtype:'datefield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddSaleMethod',
									name:'planAddSaleMethod',
									fieldLabel:'营销渠道',
									xtype:'combo',
									store:new Ext.data.ArrayStore({
												fields:['id','value'],
												data:[[1,'直销'],
													  [2,'代销']]
											}),
									valueField:'id',
									displayField:'value',
									mode:'local',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddJoinOrg',
									name:'planAddJoinOrg',
									fieldLabel:'参与机构',
									xtype:'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddBudgetSpend',
									name:'planAddBudgetSpend',
									fieldLabel:'已使用预算',
									xtype:'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddResearchContent',
									name:'planAddResearchContent',
									fieldLabel:'调研报告内容',
									xtype:'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddJoinOrg',
									name:'planAddJoinOrg',
									fieldLabel:'方案卖点',
									xtype:'combo',
									store:new Ext.data.ArrayStore({
												fields:['id','value'],
												data:[[1,'卖点'],
													  [2,'主题']]
											}),
									valueField:'id',
									displayField:'value',
									mode:'local',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddDataName',
									name:'planAddDataName',
									fieldLabel:'资料名称',
									xtype:'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddDatasUpload',
									name:'planAddDatasUpload',
									fieldLabel:'资料上传',
									xtype:'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
									id:'planAddDesignContent',
									name:'planAddDesignContent',
									fieldLabel:'设计内容',
									xtype:'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									anchor : '90%'
								},{
											id:'planAddDataType',
											name:'planAddDataType',
											fieldLabel:'资料类型',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'combo',
											store:new Ext.data.ArrayStore({
												fields:['id','value'],
												data:[[1,'文档'],
													  [2,'视频'],
													  [3,'PPT']]
											}),
											valueField:'id',
											displayField:'value',
											mode:'local'
										},{
											id:'planAddImageDesign',
											name:'planAddImageDesign',
											fieldLabel:'形象设计',
											labelStyle:{
												width:'120px'
											},
											width:200,
											anchor : '90%',
											xtype:'combo',
											store:new Ext.data.ArrayStore({
												fields:['id','value'],
												data:[[1,'主视角'],
													  [2,'VI']]
											}),
											valueField:'id',
											displayField:'value',
											mode:'local'
										}]
							}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [{
								xtype : 'textarea',
								labelStyle : {
									width : '120px'
								},
								width : 200,
								fieldLabel : '参与人员',
								id : 'planAim',
								name : 'planAim',
								anchor : '90%'
//							},{
//								fieldLabel:'进程',
//								layout:'form',
//								items:[stepGrid],
//								labelStyle : {
//									width : '120px'
//								},
//								width : 200,
//								anchor:'90%'
							}],

					buttons : [

					{

								text : '保  存',
								handler : function() {
									alert("新增成功");
									addPlanWindow.hide();
								}

							}, {
								text : '取  消',
								handler : function() {
									addPlanWindow.hide();
								}
							}]
				}

				]

			});
var addForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 300,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				buttons:[{
					text:'保存'
				},{
					text:"取消",
					handler:function(){
						addPlanWindow.hide();
					}
				}
				]
});
	// 修改基本信息展示的from
	var editBasePlanForm = new Ext.form.FormPanel({
				labelWidth : 150,
				height : 400,
				frame : true,
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .5,
								layout : 'form',
								items : [{
											id : 'planEditName',
											name : 'planEditName',
											xtype : 'textfield',
											fieldLabel : '营销计划名称',
											labelStyle : {
												width : '120px'
											},
											width : '100',
											anchor : '90%'
										},{
											id : 'beginTimeE',
											name : 'beginTimeE',
											xtype : 'datefield',
											fieldLabel : '计划开始日期',
											labelStyle : {
												width : '120px'
											},
											width : 100,
											anchor : '90%'

										}]
							},
							{
								columnWidth : .5,
								layout : 'form',
								items : [
//								{
//									id : 'custNameE',
//									name : 'custNameE',
//									xtype : 'textfield',
//									fieldLabel : '客户群名称',
//									labelStyle : {
//										width : '120px'
//									},
//									width : '100',
//									anchor : '90%'
//								},
								{
									xtype : 'textfield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									fieldLabel : '费用预算',
									id : 'budgetE',
									name : 'budgetE',
									anchor : '90%'
								},
								{
									xtype : 'datefield',
									labelStyle : {
										width : '120px'
									},
									width : 200,
									fieldLabel : '预计结束日期',
									id : 'endDateE',
									name : 'endDateE',
									anchor : '90%'
								}
								]
							}

					]
				}, {
					layout : 'form',
					buttonAlign : 'center',
					items : [{
								xtype : 'textarea',
								labelStyle : {
									width : '120px'
								},
								width : 200,
								fieldLabel : '营销计划目的',
								id : 'planAimE',
								name : 'planAimE',
								anchor : '90%'
							}, {
								xtype : 'textarea',
								labelStyle : {
									width : '120px'
								},
								width : 200,

								fieldLabel : '营销计划内容',
								id : 'planContE',
								name : 'planContE',
								anchor : '90%'
							}],

					buttons : [

					{

								text : '保  存',
								handler : function() {
									alert("新增成功");
									editPlanWindow.hide();
								}

							}, {
								text : '提  交',
								handler : function() {
									alert("提交成功");
									editPlanWindow.hide();
								}
							},{
								text : '取  消',
								handler : function() {
									editPlanWindow.hide();
								}
							}]
				}

				]

			});
	// 定义修改窗口的tabPanel
	var tokenDelimiter = ':';
	var followTp=new Ext.TabPanel({
		id:'editFollowTabs',
		activeTab:0,
		tabPosition : 'bottom',
		items:[{
			title:'跟踪进度一',
			id:'followTab1',
			items:[followForm]
		},{
			title:'跟踪进度二',
			id:'followTab2'
//			items:[followForm]
		}],
		listeners : {
					'tabchange' : function(tabPanel, tab) {
						if (tab.id != 'editTab') {
							Ext.History.add(tabPanel.id + tokenDelimiter
									+ tab.id);
						}
					}
				}
	});
	Ext.History.on('change', function(token) {
				if (token) {
					var parts = token.split(tokenDelimiter);
					var tabPanel = Ext.getCmp(parts[0]);
					var tabId = parts[1];

					tabPanel.show();
					tabPanel.setActiveTab(tabId);
				} else {

					tp.setActiveTab(0);
					tp.getItem(0).setActiveTab(0);
				}

			});


	// 定义新增窗口
	var addPlanWindow = new Ext.Window({
		title : '营销策划新增',
		plain : true,
		layout : 'fit',
		width:1000,
		height:500,
//		maximized:true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		buttonAlign : 'right',
		border : false,
		items : [addPlanForm]
//		items:[addForm]
		});

//方案跟踪窗口
var followTaskWindow=new Ext.Window({
	title : '营销方案跟踪',
	plain : true,
	layout : 'fit',
	width : 880,
	height : 300,
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
	items : [followTp]
});
//
var sumUpWindow=new Ext.Window({
	title : '营销方案总结',
	plain : true,
	layout : 'fit',
	width : 880,
	height : 300,
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
	items : [sumUpForm]
});
			
var spreadWindow=new Ext.Window({
	title : '营销方案传播',
	plain : true,
	layout : 'fit',
	width : 880,
	height : 300,
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
	items : [spreadForm]
});
	// 展示新增窗口
	function addInit() {
		addPlanWindow.show();
	}
	// 展示修改窗口
	function editInit() {
		editPlanWindow.show();
	}
	function sumUpInit(){
		sumUpWindow.show();
	}
	function followInit(){
		followTaskWindow.show();
	}
	
	var view = new Ext.Viewport({

				layout : 'border',
				items : [{
					region : 'center',
					id : 'center-panel',
					title : "营销策划列表",
					layout : 'fit',
					items : [listPanel]
				},
				{
					region : 'north',
					id : 'north-panel',
					title : "营销策划查询",
					height : 160,
					layout : 'fit',
					items : [searchPanel]
				}
				]
			});

})