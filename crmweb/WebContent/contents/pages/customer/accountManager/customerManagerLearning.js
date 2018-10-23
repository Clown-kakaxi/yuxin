Ext.onReady(function() {
	Ext.QuickTips.init();
//	// 渠道类型下拉框的数据查询
//		var channelTypeStore=util.form._store('/channel-type.json','channelTypeId','channelTypeName');	
//		//var testStore=util.form._store('/lookup.json?name=IS_SMALL');
//		channelTypeStore.load();
//		//testStore.load();


//		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户经理学习管理",
			//
			//seBaseForm ：true,
			stUrl : basepath + '/customerManagerLearning!indexPage.json',
			// demoData :
			// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
			//新增URL，如果不定义则不出现新增按钮
			addUrl : basepath + '/customerManagerLearning.json',
			updateUrl : basepath + '/customerManagerLearning.json',
			deUrl : basepath + '/customerManagerLearning!batchDestroy.json',
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
//				//debugger;
			if(b.FR_DATE!=''&&b.FR_DATE!=undefined&&b.FR_DATE!=null)
			Ext.getCmp('from').setValue(new Date(b.FR_DATE.time));
			if(b.TO_DATE!=''&&b.TO_DATE!=undefined&&b.TO_DATE!=null)
		    Ext.getCmp('to').setValue(new Date(b.TO_DATE.time));
			},
			createFun:function(){

				Ext.getCmp('custManagerId').setValue(__userId);
				Ext.getCmp('CUST_MANAGER').setValue(__userName);
			},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :{items:[
			                     
				util.layout._tr([util.form._td({name : 'custManagerId',xtype : 'textfield',fieldLabel : '客户经理编号',columnWidth : .25, anchor : '95%'})],
								[util.form._td({name : 'custManagerName',xtype : 'textfield',fieldLabel : '客户经理姓名',columnWidth : .25, anchor : '95%'})],
								[util.form._td({name : 'FR_DATE',xtype : 'datefield',fieldLabel : '学习时间从',id:'fr_date',columnWidth : .25, anchor : '95%'})],
								[util.form._td({name : 'TO_DATE',xtype : 'datefield',fieldLabel : '学习时间至',id:'to_date',columnWidth : .25, anchor : '95%',
									listeners:{
											'blur':function(){
											if(""==Ext.getCmp('fr_date').getValue()){
												Ext.Msg.alert('提示', '请选择“学习时间从”');
												Ext.getCmp('to_date').setValue("");
											}else if(Ext.getCmp('fr_date').getValue()>Ext.getCmp('to_date').getValue()){
												Ext.Msg.alert('提示', '“学习时间至”应该大于“学时间从”');
												Ext.getCmp('to_date').setValue("");
												Ext.getCmp('fr_date').setValue("");
											}
									}
								}})	]
								)
			]},
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name:'id'},
			    {name : 'custManagerId',header:'客户经理编号'},  
			    {name : 'custManagerName',header : '客户经理名称'},  
				{name : 'learnContent',header : '学习内容'},
				{name : 'FR_DATE',header : '学习时间从',type:'date'},
				{name : 'TO_DATE',header : '学习时间至',type:'date'}

				
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({id:'custManagerId',name : 'custManagerId',xtype : 'textfield',fieldLabel : '客户经理编号',labelStyle: 'text-align:right;',readOnly:true})],
									[util.form._td(new Ext.ux.form.CustMgrField({ 
										fieldLabel : '所属客户经理', 
										id:'CUST_MANAGER',
										labelStyle: 'text-align:right;',
										name : 'custManagerName',
										//store: store, 
										//singleSelected:true,
										allowBlank:false,
										anchor : '90%',
										callback: function() {
										var cust_id = null;
										var cust_name = null;
										cust_name = Ext.getCmp('CUST_MANAGER').getValue();
										if (cust_name != null && cust_name != '') {
											cust_id = Ext.getCmp('CUST_MANAGER').userId.aId[0];
											listPanel.fp.getForm().findField('custManagerId').setValue(cust_id);
										}
									}
										}))]
									),
					util.layout._tr([util.form._td({allowBlank:false,id:'from',name : 'FR_DATE',fieldLabel : '学习时间从',labelStyle: 'text-align:right;',xtype : 'datefield',
						listeners:{
						'blur':function(){
						if(Ext.getCmp('from').getValue()>Ext.getCmp('to').getValue()&&Ext.getCmp('to').getValue()!=''){
							Ext.Msg.alert('提示', '“学习时间至”应该大于“学时间从”');
							Ext.getCmp('from').setValue("");
						}}}})],
							[util.form._td({allowBlank:false,id:'to',name : 'TO_DATE',fieldLabel : '学习时间至',labelStyle: 'text-align:right;',xtype : 'datefield',
								listeners:{
								'blur':function(){
								if(Ext.getCmp('from').getValue()>Ext.getCmp('to').getValue()&&Ext.getCmp('from').getValue()!=''){
									Ext.Msg.alert('提示', '“学习时间至”应该大于“学时间从”');
									Ext.getCmp('to').setValue("");
								}
						}
					}})]
					),
					util.layout._tr([util.form._td({allowBlank:false,name : 'learnContent',fieldLabel : '学习内容',labelStyle: 'text-align:right;',xtype : 'textarea',maxLength : 450,anchor:'95%'})]
					),
					util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})]
					)
					
				
			]})}
		});

		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
		
		
		
	});