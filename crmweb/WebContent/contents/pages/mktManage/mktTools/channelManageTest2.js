Ext.onReady(function() {
	Ext.QuickTips.init();
	// 渠道类型下拉框的数据查询
		var channelTypeStore=util.form._store('/channel-type!indexAll.json','channelTypeId','channelTypeName');	
		//var testStore=util.form._store('/lookup.json?name=IS_SMALL');
		channelTypeStore.load();
		//testStore.load();
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "渠道管理s",
			//
			//seBaseForm ：true,
			stUrl : basepath + '/channel-info!indexPage.json',
			// demoData :
			// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
			//新增URL，如果不定义则不出现新增按钮
			addUrl : basepath + '/channel-info.json',
			addGrant : 'ADD',
			updateGrant : 'MODIFY',
			delGrant : 'DELETE',
			updateUrl : basepath + '/channel-info.json',
			deUrl : basepath + '/channel-info!batchDestroy.json',
			primary : "channelId",
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 100,
			//定义增删详情页面弹出窗口高度
			winHeight : 450,
			//宽度
			winWidth : 800,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
				//debugger;
				Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
		    	Ext.getCmp('updateDate').setValue(new Date(b.updateDate.time));
			},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'channelName',xtype : 'textfield',fieldLabel : '渠道名称1'})],
								[util.form._td({name : 'channelTypeId',xtype : 'combo',fieldLabel : '渠道类型',store : channelTypeStore,valueField : 'channelTypeId',displayField : 'channelTypeName'})],
								[util.form._td({name : 'createDateS',xtype : 'datefield',fieldLabel : '建立日期'})],
								[util.form._td({name : 'createDateE',xtype : 'datefield',fieldLabel : '至'})	]
								)
			]}),
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			    {name : 'channelId'	},  
			    {name : 'channelName',header : '渠道名称'},  
			    {name : 'channelTypeId',header : '渠道类型',type :'mapping',store : channelTypeStore, mappingkey : 'channelTypeId',mappingvalue : 'channelTypeName'}, 
				{name : 'createUser',header : '渠道建立人'},
				{name : 'createOrganization',header : '渠道建立机构'},
				{name : 'createDate',header : '渠道建立日期',type : 'date'},
				{name : 'accessCondition'},
				{name : 'channelFeature'},
				{name : 'channelPolicy'},
				{name : 'guarantee'},
				{name : 'remark'},
				{name : 'updateDate'}
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'channelName',xtype : 'textfield',fieldLabel : '渠道名称'})],
									[util.form._td({name : 'channelTypeId',xtype : 'combo',fieldLabel : '渠道类型',store : channelTypeStore,valueField : 'channelTypeId',displayField : 'channelTypeName'})]
									),
					util.layout._tr([util.form._td({name : 'accessCondition',fieldLabel : '准入条件',xtype : 'textarea',maxLength : 400})]
									),
					util.layout._tr([util.form._td({name : 'channelFeature',fieldLabel : '渠道特点',xtype : 'textarea',maxLength : 400})]
					),
					util.layout._tr([util.form._td({name : 'channelPolicy',fieldLabel : '渠道政策',xtype : 'textarea',maxLength : 400})]
					),
					util.layout._tr([util.form._td({name : 'guarantee',fieldLabel : '担保',xtype : 'textarea',maxLength : 400})]
					),
					util.layout._tr([util.form._td({name : 'remark',fieldLabel : '备注',xtype : 'textarea',maxLength : 400})]
					),
					util.layout._tr([util.form._td({name : 'createUser',fieldLabel : '渠道建立人',xtype : 'textfield',readOnly : true})],
									[util.form._td({name : 'createOrganization',fieldLabel : '渠道建立机构',	xtype : 'textfield',readOnly : true})]
					),
					util.layout._tr([util.form._td({id : 'createDate',name : 'createDate',fieldLabel : '渠道建立日期',xtype : 'datefield',readOnly : true})],
									[util.form._td({id : 'updateDate',name : 'updateDate',fieldLabel : '最近更新日期',xtype : 'datefield',readOnly : true})]
					),
					util.layout._tr([util.form._td({name : 'channelId',xtype : 'hidden'})]
					)
			]})}

		});

		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
		
		
		
	});