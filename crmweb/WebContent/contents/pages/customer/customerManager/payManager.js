Ext.onReady(function() {
	Ext.QuickTips.init();
	var cust_id = oCustInfo.cust_id;
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户支付管理信息",
			//seBaseForm ：true,
			stUrl : basepath + '/AcrmFCiPayManagerment!indexPage.json?cust_id='+cust_id,
			detailUrl: basepath + '/AcrmFCiPayManagerment!indexPage.json',
			primary : "contNo",
			//定义查询条件Form的高度
			seFormHeight : 30,
			//定义增删详情页面弹出窗口高度
			winHeight : 210,
			//宽度
			winWidth : 800,
			//设置分页每页显示条数，若不设置则不出现分页栏
			checkbox : true,
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
//				Ext.getCmp('payManager_payDt').setValue(new Date(b.payDt.time));
			},
			gclms : [ 
			         {name : 'custId',header : '统计日期',
							renderer:function(){return '2012-06-29';}},
			    {name : 'contNo',header :'合同号'},  
				{name : 'custName'},
			    {name : 'counterPartyAccNo',header : '交易对手帐号'}, 
				{name : 'counterPartyName',header : '交易对手名称'},
				{name : 'payType',header :'支付方式'},
			    {name : 'amt',header : '金额'},  
				{name : 'useFor',header :'实际用途'},
				{name : 'payDt',header : '支付时间',type : 'date'}
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'contNo',xtype : 'textfield',fieldLabel : '合同号'})],
									[util.form._td({name : 'payType',xtype : 'textfield',fieldLabel : '支付方式'})]
									),
					util.layout._tr([util.form._td({name : 'custId',xtype : 'textfield',fieldLabel : '客户编号'})],
									[util.form._td({name : 'custName',xtype : 'textfield',fieldLabel : '客户名称'})]
									),
					util.layout._tr([util.form._td({name : 'counterPartyAccNo',xtype : 'textfield',fieldLabel : '交易对手帐号'})],
									[util.form._td({name : 'counterPartyName',xtype : 'textfield',fieldLabel : '交易对手名称'})]
									),
					util.layout._tr([util.form._td({name : 'amt',xtype : 'textfield',fieldLabel : '金额'})],
									[util.form._td({name : 'useFor',xtype : 'textfield',fieldLabel : '实际用途'})]
									),
					util.layout._tr([util.form._td({/*id:'payManager_payDt',*/name : 'payDt',xtype : 'hidden'/*,fieldLabel : '支付时间'*/})],
									[util.form._td({name : 'hidden',xtype : 'hidden'})]
									)
			]})}

		});

		// 布局模型
		var viewport = new Ext.Panel( {
			renderTo:'viewport_center',
			height:document.body.scrollHeight-30,
			layout : 'fit',
			autoScroll:true,
			items : [ listPanel ]
		});
		
		
		
	});