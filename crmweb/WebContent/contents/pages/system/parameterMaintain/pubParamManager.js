Ext.onReady(function() {
	Ext.QuickTips.init();
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "公共参数查询",
			//
			//seBaseForm ：true,
			stUrl : basepath + '/pubParamManagerAction.json',
			//新增URL，如果不定义则不出现新增按钮
			addUrl : basepath + '/pubParamManagerAction!saveData.json',
			updateUrl : basepath + '/pubParamManagerAction!saveData.json',
			deUrl : basepath + '/pubParamManagerAction!batchDestroy.json',
			primary : "id",
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 100,
			//定义增删详情页面弹出窗口高度
			winHeight : 250,
			//宽度
			winWidth : 800,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
				//debugger;
			 
			},
			createFun:function(){

				Ext.getCmp('appId').setValue("62");
				Ext.getCmp('version').setValue("4");
			},
			// 查询字段定义，若不定义则不出现查询条件Form
			selectItems : {
				layout : 'column',
				items : [{
							columnWidth : .5,
							layout : 'form',
							items : [{
										name : 'propName',
										xtype : 'textfield',
										fieldLabel : '参数名称',
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 80,
							items : [{
										name : 'propDesc',
										xtype : 'textfield',
										fieldLabel : '参数说明',
										anchor : '90%'
									}]
						}]
			},
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name:'id',mapping:'ID'},{name:'appId',mapping:'APP_ID'},
			    {name : 'propName',mapping : 'PROP_NAME',header : '参数名称',width : 150},  
			    {name : 'propDesc',mapping : 'PROP_DESC',header : '参数说明',width : 150},  
			    {name : 'propValue',mapping : 'PROP_VALUE',header : '参数值',width : 250},  
				{name : 'remark',mapping : 'REMARK',header : '参数备注',width : 400}
				
			],
			
			// 新增、修改、详情的form的字段
			fclms : [{
						layout : 'column',
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [{
												name : 'propName',
												fieldLabel : '参数名称',
												xtype : 'textfield',
												width : 100,
												allowBlank : false,
												maxLength : 100,
												anchor : '90%'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
										name : 'propDesc',
										fieldLabel : '参数说明',
										xtype : 'textfield',
										width : 100,
										allowBlank : false,
										maxLength : 150,
										anchor : '90%'
									}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .95,
									layout : 'form',
									items : [{
										name : 'propValue',
										fieldLabel : '参数值',
										xtype : 'textfield',
										width : 100,
										allowBlank : false,
										maxLength : 254,
										anchor : '100%'
									}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .95,
									layout : 'form',
									items : [{
										name : 'remark',
										fieldLabel : '参数备注',
										xtype : 'textarea',
										width : 100,
										allowBlank : false,
										maxLength : 255,
										anchor : '100%'
									}]
								}, {
									// 特别注意：
									// 必须放置隐藏域的主键
									name : 'id',
									xtype : 'hidden'
								}, {
									id : 'version',
									xtype : 'hidden'
								}, {
									id : 'appId',
									xtype : 'hidden'
								}]
					}]
			
			
		});
		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
				
	});