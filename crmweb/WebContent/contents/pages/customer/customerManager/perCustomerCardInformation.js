Ext.onReady(function() {
	Ext.QuickTips.init();
	var cust_id = oCustInfo.cust_id;
	
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "贷记卡信息",
		//客户编号
		primary : "custId",
		//单选框
		singleSelect : true,
		//查询路径设置
		stUrl : basepath + '/acrmCreditCardInfo-info!indexPage.json?cust_id='+cust_id,
		//详情的url
		detailUrl : basepath + '/acrmCreditCardInfo-info!indexPage.json',
		//定义查询条件Form的高度
		seFormHeight : 0,
		//定义增删详情页面弹出窗口高度
		winHeight : 235,
		//定义增删详情页面弹出窗口宽度
		winWidth : 900,
		//定义宽度
//		width : document.body.scrollWidth-230,
		//定义显示列表高度
//		gridHeight : document.body.clientHeight-70,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			//debugger;
			Ext.getCmp('pubDate1').setValue(new Date(b.pubDate.time));
	    	Ext.getCmp('expDate1').setValue(new Date(b.expDate.time));
		},
		//查询列表字段定义，有header属性则在页面显示 
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ 
		    {name : 'custId'}, 
		    {name:'cardNo1',header:'统计日期',
				renderer:function(){return '2012-06-29';}},
		    {name :'cardNo',header : '卡号',width : 100},
		    {name : 'ifFirstUse',header : '信用卡是否首刷',width : 100},
		    {name : 'ifOvd',header : '信用卡是否逾期',width : 100},
		    {name : 'creditLine',header : '信用额度',type:'float',width : 100},
		    {name : 'odLine',header : '透支额度',type:'float',width : 100},
		    {name : 'pubDate',header : '信用卡核发日期',type : 'date',width : 100},
		    {name : 'expDate',header : '信用卡失效日期',type : 'date',width : 100},
		    {name : 'customId'},
		    {name : 'ifByStage'},
		    {name : 'custMeritLevel'},
		    {name : 'repayAcctNo'},
		    {name : 'customName'},
		    {name : 'custRiskLevel'},
		    {name : 'cardTyp'},
		    {name : 'ifImpCard'},
		    {name : 'transTyp'},
		    {name : 'ifAutoRepay'}
		],
//		buts:[{
//			text : '导出',
//			handler : function() {
//			 		new  Ext.ux.Grid2Excel.Save2Excel(listPanel.grid);
//			}
//		}],
		
		// 新增、修改、详情的form的字段
		formColums :function(){
			return new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'customId',xtype : 'textfield',fieldLabel : '客户号',readOnly :true})],
								[util.form._td({name : 'ifByStage',xtype : 'textfield',fieldLabel : '有无信用卡分期',readOnly:true})],
								[util.form._td({name : 'custMeritLevel',xtype : 'textfield',fieldLabel : '客户价值级别',readOnly:true})],
								[util.form._td({name : 'repayAcctNo',xtype : 'textfield',fieldLabel : '自动还款帐号',readOnly:true})]
				),
				util.layout._tr([util.form._td({name : 'customName',xtype : 'textfield',fieldLabel : '客户名称',readOnly:true})],
								[util.form._td({name : 'ifOvd',xtype : 'textfield',fieldLabel : '信用卡是否逾期',readOnly:true})],
								[util.form._td({name : 'custRiskLevel',xtype : 'textfield',fieldLabel : '客户风险级别',readOnly:true})],
								[util.form._td({name : 'cardTyp',xtype : 'textfield',fieldLabel : '信用卡类别',readOnly:true})]
				),
				util.layout._tr([util.form._td({name : 'ifImpCard',xtype : 'textfield',fieldLabel : '是否质押办卡',readOnly:true})],
								[util.form._td({name : 'creditLine',xtype : 'textfield',fieldLabel : '信用额度',readOnly:true})],
								[util.form._td({name : 'transTyp',xtype : 'textfield',fieldLabel : '交易类型',readOnly:true})],
								[util.form._td({id : 'pubDate1',name : 'pubDate',fieldLabel : '信用卡核发日期',xtype : 'datefield',readOnly:true})]
				),
				util.layout._tr([util.form._td({name : 'ifFirstUse',xtype : 'textfield',fieldLabel : '信用卡是否首刷',readOnly:true})],
								[util.form._td({name : 'odLine',xtype : 'textfield',fieldLabel : '透支额度',readOnly:true})],
								[util.form._td({name : 'ifAutoRepay',xtype : 'textfield',fieldLabel : '是否办理自动还款',readOnly:true})],
								[util.form._td({id : 'expDate1',name : 'expDate',fieldLabel : '信用卡失效日期',xtype : 'datefield',readOnly:true})]
				),
				util.layout._tr([util.form._td({name : 'custId',xtype : 'hidden'})]
				)
		]});}

	});
		


		// 布局模型
		var viewport = new Ext.Panel( {
			renderTo:'viewport_center',
			height:document.body.scrollHeight-35,
			layout : 'fit',
			autoScroll:true,
			items : [ listPanel ]
		});

});