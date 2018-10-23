Ext.onReady(function() {
	Ext.QuickTips.init();
	//禁用标志，数据字典
	var forbidFlagStore = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=FORBID_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});

	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "积分管理设置",
		//客户编号
		primary : "id",
		//单选框
		singleSelect : true,
		//查询路径设置
		stUrl : basepath + '/ocrmFCiIntegralSetting-info!indexPage.json',
		//设置禁用
		updateUrl : basepath + '/ocrmFCiIntegralSetting-info.json',
		//定义查询条件Form的高度
		seFormHeight : 0,
		//定义增删详情页面弹出窗口高度
		winHeight : 230,
		//宽度
		winWidth : 800,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		
		//查询列表字段定义，有header属性则在页面显示 
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ 
		    {name : 'id'}, 
		    {name : 'targetName',header : '指标名称',width : 150},
		    {name : 'custType',header : '客户类型',width : 150},
		    {name : 'targetBal',header : '指标金额',type:'float',width : 150},
		    {name : 'targetMark',header : '指标分值（兑换比）',width : 150},
		    {name : 'convertRate',header : '折算率',hidden:true,width : 140,renderer:money('0,000.00%')},
		    {name : 'isDisabled',header : '是否禁用',type :'mapping',store : forbidFlagStore, mappingkey : 'key',mappingvalue : 'value'}, 
		    {name : 'targetId'}
		],
		    
		    
		// 新增、修改、详情的form的字段
		formColums :function(){
			return new Ext.form.FieldSet({items:[
				util.layout._tr(
						[util.form._td({name : 'targetId',xtype : 'textfield',fieldLabel : '指标号',readOnly :true,width : '100',anchor : '90%'})],
						[util.form._td({name : 'targetName',xtype : 'textfield',fieldLabel : '指标名称',readOnly :true,width : '100',anchor : '90%'})]
				),
				util.layout._tr(
						[util.form._td({name : 'targetBal',xtype : 'numberfield',fieldLabel : '指标金额',width : '100',anchor : '90%'})],
						[util.form._td({name : 'targetMark',xtype : 'textfield',fieldLabel : '*指标分值（兑换比）'})]
				),
				util.layout._tr(
						[util.form._td({name : 'custType',xtype : 'textfield',fieldLabel : '客户类型',readOnly :true,width : '100',anchor : '90%'})],
						[util.form._td({name : 'isDisabled',xtype : 'combo',fieldLabel : '*是否禁用',store : forbidFlagStore,valueField : 'key',displayField : 'value'})]
				),
				util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})],
						[util.form._td({name : 'convertRate',xtype : 'hidden'})]
				)
		]});}
	
	});
	
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
	
});	    
		    