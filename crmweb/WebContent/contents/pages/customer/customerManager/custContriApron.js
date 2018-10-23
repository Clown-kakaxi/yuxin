Ext.onReady(function() {
	Ext.QuickTips.init();
	// 渠道类型下拉框的数据查询
		var type_Store=util.form._store('/lookup.json?name=ACC_TYPE');
		var limit_Store=util.form._store('/lookup.json?name=ACC_LIMIT');
		type_Store.load();
		limit_Store.load();
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户贡献度参数维护",
			//seBaseForm ：true,
			stUrl : basepath + '/AcrmFCiCustContriApron!indexPage.json',
			addUrl : basepath + '/AcrmFCiCustContriApron.json',
			updateUrl : basepath + '/AcrmFCiCustContriApron.json',
			deUrl : basepath + '/AcrmFCiCustContriApron!batchDestroy.json',
			primary : "id",
			checkbox : true,
			seFormHeight : 100,
			winHeight : 300,
			winWidth : 450,
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
//			afterSeOneFun : function(b) {
//				//debugger;
//				Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
//		    	Ext.getCmp('updateDate').setValue(new Date(b.updateDate.time));
//			},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'accTyp',xtype : 'combo',fieldLabel : '账户种类',store : type_Store,valueField : 'key',displayField : 'value'})],
								[util.form._td({name : 'timeLimit',xtype : 'combo',fieldLabel : '账户期限',store : limit_Store,valueField : 'key',displayField : 'value'})]
								)
			]}),
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			    {name : 'id'	},  
			    {name : 'accTyp',header : '账户类型',type :'mapping',store : type_Store, mappingkey : 'key',mappingvalue : 'value'}, 
			    {name : 'timeLimit',header : '账户期限',type :'mapping',store : limit_Store, mappingkey : 'key',mappingvalue : 'value'}, 
				{name : 'innerPrice',header : '内转价格'},
				{name : 'runCost',header : '账户经营成本'}
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'accTyp',xtype : 'combo',fieldLabel : '账户种类',store : type_Store,valueField : 'key',displayField : 'value'})]
									),
					util.layout._tr([util.form._td({name : 'timeLimit',xtype : 'combo',fieldLabel : '账户期限',store : limit_Store,valueField : 'key',displayField : 'value'})]
									),
					util.layout._tr([util.form._td({
															name : 'innerPrice',
															fieldLabel : '内转价格',
															xtype : 'textfield',
															regex:/^[0]\.\d{1,6}$/,
															regexText:'例：0.999999',
															allowBlank : false,//不允许为空
															blankText : "不能为空，请填写"})]
					),
					util.layout._tr([util.form._td({
															name : 'runCost',
															fieldLabel : '账户经营成本',
															xtype : 'textfield',
															regex : /^\d+\.?\d{0,2}$/,
															regexText : '例99...9999.99',
															allowBlank : false,//不允许为空
															blankText : "不能为空，请填写"})]
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