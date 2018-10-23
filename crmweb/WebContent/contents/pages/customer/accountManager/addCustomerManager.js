/*	
	var record = Ext.data.Record.create([
         {name: 'id', mapping: 'ID'},
         {name: 'age', mapping: 'AGE'},   
         {name: 'userName', mapping: 'USER_NAME'},  
         {name: 'political', mapping: 'POLITICAL'},
         {name : 'gender', mapping :'GENDER'},
         {name: 'graduateschool', mapping: 'GRADUATESCHOOL'},
         {name: 'gwNames', mapping: 'GWNAMES'},
         {name: 'educationlevel', mapping: 'EDUCATIONLEVEL'},
         {name: 'economicyear', mapping: 'ECONOMICYEAR'},
         {name: 'accessionstate', mapping: 'ACCESSIONSTATE_ORA'},
         {name: 'attendondate', mapping: 'ATTENDONDATE'},
         {name: 'unitName', mapping: 'UNITNAME'},
         {name: 'defaultProperty',mapping :'DEFAULTPROPERTY'},
         {name: 'userId', mapping: 'USER_ID'}
	     ]);
	
	
	 var xlStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['研究生','研究生'],['在职本科','在职本科'],['在职研究生','在职研究生'],['大专','大专'],['在职博研','在职博研'],['高中','高中'],
	              ['中专','中专'],['职高','职高'],['初中','初中'],['在职研究生','在职研究生'],['在职大专','在职大专'],['在职中专','在职中专'],['小学','小学'],
	              ['本科','本科'],['博士研究生','博士研究生'],['技校','技校'],['高职','高职']]
	    });
	
	 var zzmmStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['中共党员','中共党员'],['团员','团员'],['群众','群众']]
	    });
	 
	 var crmPoStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['总行行长','总行行长'],['总行部门负责人','总行部门负责人'],['分行管理部行长','分行管理部行长'],
	              ['分行管理部部门经理','分行管理部部门经理'],['支行行长','支行行长'],['支行公司部经理','支行公司部经理']]
	    });
	 
	var accessionstateStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=MANAGER_STATUS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var isHavingCardStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=IS_HAVING_CODE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var custTypeStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=MANAGER_TYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var pliticalTypeStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=POL_LANDSCAPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var custLevelStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=MANAGER_LEVEL'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var levelStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=MANAGER_GRADE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});

	
	
	 var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/customerManagerBaseQuery.json'
	      
			 * , success : function(response) { Ext.Msg.alert('提示',
			 * response.responseText); }
			 
	        }),
	       reader: new Ext.data.JsonReader({
	       totalProperty : 'json.count',
	        root:'json.data'
	        }, record)
		});

	 //***************************************
	
	 // 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
		// 定义列模型
		var cm = new Ext.grid.ColumnModel([rownum,sm, 
		        {header : '员工ID',dataIndex : 'userId'},
		        {header : '员工姓名',dataIndex : 'userName'},
		        {header : 'id', dataIndex : 'id',sortable : true,width : 150,hidden :true}, 
		        {header : '年龄',dataIndex : 'age',width : 80},
			    {header : '性别',dataIndex : 'gender',width :80},
			    {header : '政治面貌',dataIndex : 'political'},
			    {header : '岗位',dataIndex : 'gwNames'},
			    {header : '毕业学校',dataIndex : 'graduateschool'},
			    {header : '学历',dataIndex : 'educationlevel'},
			    {header : '状态',dataIndex :'accessionstate'},
			    {header : '职种',dataIndex : 'defaultProperty'},
			    {header : '经济工作年限',dataIndex : 'economicyear'},
			    {header : '入行日期',dataIndex : 'attendondate'},
			    {header : '所属机构',dataIndex : 'unitName'}
			    
				]);
		
		
	     var pagesize_combo = new Ext.form.ComboBox({
	         name : 'pagesize',
	         triggerAction : 'all',
	         mode : 'local',
	         store : new Ext.data.ArrayStore({
	             fields : ['value', 'text'],
	             data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
	         }),
	         valueField : 'value',
	         displayField : 'text',
	         value : '10',
	         forceSelection : true,
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
	        // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	        emptyMsg : "没有符合条件的记录",
	        items : ['-', '&nbsp;&nbsp;', pagesize_combo
	                 ]
	    });
		// 表格工具栏****************************************************************************************
		
		var tbar = new Ext.Toolbar({
					items : [
					{
						text : '设置为客户经理',
						handler : function() {
							var selectLength = grid.getSelectionModel()
							.getSelections().length;
							
					if (selectLength < 1) {
						alert('请选择需要加入名单的客户');
					} else 
					 {

						var selectLength = grid
								.getSelectionModel()
								.getSelections().length;

						var selectRe = grid
								.getSelectionModel()
								.getSelections()[0];

						if (selectLength != 1) {
							alert('请选择一条记录');
						} else {
								editBasePlanForm.getForm().loadRecord(selectRe);
								document.getElementById('idStr').value = selectRe.data.id;
								addCustWindow.hide();
								addInit();
							
						}
					}
						
				}
					}			
					
					]
				});


	 //***************************************
		// 新增基本信息展示的form
		var editBasePlanForm = new Ext.form.FormPanel({
			labelWidth : 80,
			height : 300,
			frame : true,
			region : 'center',
			autoScroll : true,
			buttonAlign : "center",
			items : [{
				layout : 'column',
				items : [{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									readOnly : true,
									fieldLabel : '客户经理ID',
									name : 'userId',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '客户经理姓名',
									readOnly : true,
									name : 'userName',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '性别',
									readOnly : true,
									name : 'gender',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{	
                             fieldLabel: '政治面貌',
                             name: 'political',
                             forceSelection : true,
                             xtype:'combo',
                             triggerAction:'all',
                             mode:'local',
                             readOnly : true,
                             store:zzmmStore,
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         }

							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '毕业学校',
									readOnly : true,
									name : 'graduateschool',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '年龄',
									readOnly : true,
									name : 'age',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '职种',
									readOnly : true,
									name : 'defaultProperty',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '岗位',
									readOnly : true,
									name : 'gwNames',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									readOnly : true,
									fieldLabel : '经济工作年限',
									name : 'economicyear',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{	
                             fieldLabel: '学历',
                             name: 'educationlevel',
                             forceSelection : true,
                             xtype:'combo',
                             triggerAction:'all',
                             mode:'local',
                             readOnly : true,
                             store:xlStore,
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         }

							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '入行日期',
									readOnly : true,
									format : 'Y-m-d',
									name : 'attendondate',
									anchor : '90%'
								}
							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
								store: accessionstateStore,
								xtype : 'combo',
								name : 'accessionstate',
								fieldLabel : '状态',
								valueField:'key',
								displayField:'value',
								mode : 'local',
								typeAhead: true,
								readOnly : true,
								forceSelection : true,
								triggerAction: 'all',
								emptyText:'请选择',
								selectOnFocus:true,
								width : '100',
								anchor : '90%'
								                 }

							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									store: custTypeStore,
									xtype : 'combo',
															name : 'managerType',
															fieldLabel : '客户经理类型',
									valueField:'key',
								displayField:'value',
								mode : 'local',
								typeAhead: true,
								forceSelection : true,
								triggerAction: 'all',
								emptyText:'请选择',
								selectOnFocus:true,
								width : '100',
								anchor : '90%'
								                 }

							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
								store: levelStore,
								xtype : 'combo',
								name : 'level',
								fieldLabel : '等级档次',
								valueField:'key',
								displayField:'value',
								mode : 'local',
								typeAhead: true,
								forceSelection : true,
								triggerAction: 'all',
								emptyText:'请选择',
								selectOnFocus:true,
								width : '100',
								anchor : '90%'
								                 }

							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
								store: custLevelStore,
								xtype : 'combo',
								name : 'managerLevel',
								fieldLabel : '客户经理等级',
								valueField:'key',
								displayField:'value',
								mode : 'local',
								typeAhead: true,
								forceSelection : true,
								triggerAction: 'all',
								emptyText:'请选择',
								selectOnFocus:true,
								width : '100',
								anchor : '90%'
								 }

							    ]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{	
                             fieldLabel: 'CRM系统短信对象职位',
                             name: 'CRMMailPosition',
                             //forceSelection : true,
                             forceSelection : true,
                             xtype:'combo',
                             triggerAction:'all',
                             mode:'local',
                             store:crmPoStore,
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         }]
						},{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
									xtype : 'textfield',
									width : 200,
									fieldLabel : '联系电话',
									allowBlank : false,
									vtype: 'number',
									id : 'telephone',
									maxLength : '11',
									minLength : '2',
									name : 'telephone',
									anchor : '90%'
								}
							    ]
						},
						{
							columnWidth : .25,
							layout : 'form',
							items : [
								{
								store: isHavingCardStore,
								xtype : 'combo',
								name : 'isHavingCard',
								fieldLabel : '是否有信贷证',
								valueField:'key',
								displayField:'value',
								mode : 'local',
								typeAhead: true,
								forceSelection : true,
								triggerAction: 'all',
								emptyText:'请选择',
								selectOnFocus:true,
								width : '100',
								anchor : '90%'
								                 }

							    ]
						}]}],buttons : [

										{

											text : '保  存',
											handler : function() {
												var tel = Ext.getCmp('telephone').getValue();
												if(tel.length<3||tel.length>11){
													Ext.Msg.alert("提示","您输入的联系电话格式不正确！请重新输入！");
												}else{
												Ext.Ajax.request({

													url : basepath + '/CustomerManagerInfoAction.json',
													method : 'POST',
													params:editBasePlanForm.getForm().getFieldValues(), 
													//form : editBasePlanForm.getForm().id,
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													success :checkResult,
													failure :checkResult

//													success : function() {
//														alert("操作成功");
//														store.reload();
//													},
//													failure : function() {
//														alert("操作失败");
//													}
												});
												function checkResult(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
													var resultError = response.responseText;
													debugger;
													if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
														Ext.Msg.alert('提示', '操作成功');
														editBasePlanForm.getForm().reset();
														store.reload({
												params : {
												start : 0,
												limit :bbar.pageSize
												                    }
												                });
													} else {
														Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
														store.reload({
												params : {
												start : 0,
												limit :bbar.pageSize
												                    }
												                });
													}
												};
												addPlanWindow.hide();
											}
										}

										}, {
											text : '取  消',
											handler : function() {
												addPlanWindow.hide();
											}
										} ]

		});
	//****************************************
		
		
		var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			region:'north',
			// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 80,
			items : [{
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '员工ID',
						//id : 'USER_ID',
						name : 'USER_ID',
						anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '员工姓名',
						name : 'USER_NAME',
						anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [{	
		                 fieldLabel: '学历',
		                 name: 'EDUCATIONLEVEL',
//		                 id : 'EDUCATIONLEVEL',
		                 forceSelection : true,
		                 xtype:'combo',
		                 labelStyle: 'text-align:right;',
		                 triggerAction:'all',
		                 mode:'local',
		                 store:xlStore,
		                 valueField:'myId',
		                 displayField:'displayText',
		                 emptyText:'请选择',
		                 anchor : '90%'
			                         } ]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [{	
		                 fieldLabel: '政治面貌',
		                 name: 'POLITICAL',
		                 forceSelection : true,
		                 xtype:'combo',
		                 labelStyle: 'text-align:right;',
		                 triggerAction:'all',
		                 mode:'local',
		                 store:zzmmStore,
		                 valueField:'myId',
		                 displayField:'displayText',
		                 emptyText:'请选择',
		                 anchor : '90%'
			                         } ]
				}]
			}],
		buttons : [{
					text : '查询',
					handler : function() {
						var conditionStr = qForm.getForm().getFieldValues();
						store.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						store.reload({
							  params : {
                                   start : 0,
                                   limit : bbar.pageSize }} );
				
				   }},{
					text : '重置',
						handler : function() {
							qForm.getForm().reset();
						}
					}]
		});

		// 表格实例
		var grid = new Ext.grid.GridPanel({
					height :310,
					width : 200,
					frame : true,
					autoScroll : true,
					region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
					store : store, // 数据存储
					stripeRows : true, // 斑马线
					cm : cm, // 列模型
					sm : sm, // 复选框
					tbar : tbar, // 表格工具栏
					bbar:bbar,
					viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
		
			// 定义展示员工基本信息窗口
			var addCustWindow = new Ext.Window({
				title : '新增客户经理',
				plain : true,
				layout : 'border',
				width : 1000,
				height : 400,
				resizable : true,
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
				items: [
				        qForm,grid
			     ]
			});
			
			
			// 新增窗口展示的from
			var editPlanPanel = new Ext.Panel({
				labelWidth : 80,
				height : 300,
				layout : 'fit',
				autoScroll : true,
				buttonAlign : "center",
				items : [ editBasePlanForm ]
			});
			
			
			// 定义新增窗口
			var addPlanWindow = new Ext.Window({
				title : '客户经理信息新增',
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
				buttonAlign : 'right',
				border : false,
				items : [ editPlanPanel ]
			});
			
			function addInit(){
				addPlanWindow.show();
			};
			
			
			
			*/