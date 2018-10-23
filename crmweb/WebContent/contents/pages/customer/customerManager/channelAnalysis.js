Ext.onReady(function() {
	Ext.QuickTips.init();
	var cust_id = oCustInfo.cust_id;
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			title : "客户业务渠道分析",
			//seBaseForm ：true,
			stUrl : basepath + '/AcrmFCiChannelAnalysi!indexPage.json?cust_id='+cust_id,
			detailUrl : basepath + '/AcrmFCiChannelAnalysi!indexPage.json',
			//新增URL，如果不定义则不出现新增按钮
			primary : "id",
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 30,
			//定义增删详情页面弹出窗口高度
			winHeight : 250,
			//宽度
			winWidth : 800,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
				//debugger;
				Ext.getCmp('channelAnalysis_startDt').setValue(new Date(b.startDt.time));
		    	Ext.getCmp('channelAnalysis_endDtt').setValue(new Date(b.endDt.time));
			},
			gclms : [ 
			    {name : 'id'}, 
			    {name : 'custId',header : '客户号'},  
				{name : 'custName',header:'客户名称'},
			    {name : 'channel',header : '渠道'},  
				{name : 'startDt',header : '统计开始日期',type : 'date'},
				{name : 'endDt',header : '统计截止日期',type : 'date'},
				{name : 'saveAmt',header : '存款金额',align:'right',renderer: money('0,000.00')},
				{name : 'saveCount',header : '存款笔数'},
				{name : 'drawAmt',header : '取款金额',align:'right',renderer: money('0,000.00')},
				{name : 'drawCount',header : '取款笔数'}
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'custId',xtype : 'textfield',fieldLabel : '客户编号'})],
									[util.form._td({name : 'custName',xtype : 'textfield',fieldLabel : '客户名称'})]
									),
					util.layout._tr([util.form._td({name : 'channel',fieldLabel : '渠道',xtype : 'textfield'})],
									[util.form._td({name : 'id',xtype : 'hidden'})]
									),
					util.layout._tr([util.form._td({id:'channelAnalysis_startDt',name : 'startDt',fieldLabel : '统计开始日期',xtype : 'datefield'})],
									[util.form._td({id:'channelAnalysis_endDtt',name : 'endDt',fieldLabel : '统计截止日期',xtype : 'datefield'})]
					),
					util.layout._tr([util.form._td({name : 'saveAmt',fieldLabel : '存款金额',xtype : 'textfield'})],
									[util.form._td({name : 'saveCount',fieldLabel : '存款笔数',xtype : 'textfield'})]
					),
					util.layout._tr([util.form._td({name : 'drawAmt',fieldLabel : '取款金额',xtype : 'textfield'})],
									[util.form._td({name : 'drawCount',fieldLabel : '取款笔数',xtype : 'textfield'})]
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