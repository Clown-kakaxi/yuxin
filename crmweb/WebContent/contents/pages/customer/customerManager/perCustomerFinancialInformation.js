Ext.onReady(function() {
	var custId =oCustInfo.cust_id;
	var custName =oCustInfo.cust_name;
	var xlStore = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data : [['1','一月'], ['2','二月' ], ['3','三月' ],[ '4','四月'],['5','五月'],['6','六月'],['7','七月' ],['8','八月' ],[ '9','九月'],['10','十月'],['11','十一月'],['12','十二月' ]]
	});
	
	var listPanel2 = new Mis.Ext.CrudPanel({
		id : "listPanel2",
	    title : "客户资债信息",
		stUrl : basepath + '/perCustomerBalanceSheet-info!indexPage.json?cust_id='+custId,
		addUrl : basepath + '/perCustomerBalanceSheet-info.json',
		updateUrl : basepath + '/perCustomerBalanceSheet-info.json',
		deUrl : basepath + '/perCustomerBalanceSheet-info!batchDestroy.json',
		primary : "id",
	    checkbox : true,
//		height:document.body.clientHeight,
//		width : document.body.scrollWidth-230,
//		gridHeight : document.body.clientHeight-100,
		frame : true,
	    winHeight : 350,
		winWidth : 800,
//		afterSeOneFun : function(b) {
//			//debugger;
//			Ext.getCmp('month').setValue(new Date(b.month.time));
//	    	Ext.getCmp('year').setValue(new Date(b.year.time));
//		},
		gclms : [ 
		         {name : 'id'},
				 {name : 'period'},  
				 {name:'custId'},
				 {name:'custName'},
				 {name:'year',header:'年份',width:200},
				 {name:'month',header:'月份',width:200},
				 {name : 'target1',header : '资产合计',renderer:money('0,000.00'),align : 'right',width:200},  
			     {name : 'target24'},
				 {name : 'target13',header : '负债合计',renderer:money('0,000.00'),align : 'right',width:200}, 
				 {name:'remark'},
				 {name:'target10'},
				 {name:'target11'},
				 {name:'target12'},
				 {name:'target14'},
				 {name:'target15'},
				 {name:'target16'},
				 {name:'target17'},
				 {name:'target18'},
				 {name:'target19'},
				 {name:'target2'},
				 {name:'target20'},
				 {name:'target21'},
				 {name:'target22'},
				 {name:'target23'},
				 {name:'target25'},
				 {name:'target26'},
				 {name:'target27'},
				 {name:'target28'},
				 {name:'target29'},
				 {name:'target3'},
				 {name:'target4'},
				 {name:'target5'},
				 {name:'target6'},
				 {name:'target7'},
				 {name:'target8'},
				 {name:'target9'},
				 {name:'tmTyp'}
				], 	
		pagesize : 20,
		//from的字段
		
	formColums :function(){
		return new Ext.form.FieldSet({items:[
			util.layout._tr([util.form._td({name : 'custId',value:custId,readOnly:true,xtype : 'textfield',fieldLabel : '客户码'})],
							[util.form._td({name : 'custName',value:custName,readOnly:true,xtype : 'textfield',fieldLabel : '客户名称'})]
							),
			util.layout._tr([util.form._td({name : 'year',fieldLabel : '年份',xtype : 'datefield',format : 'Y'})],
					        [util.form._td({name : 'period',fieldLabel : '时期',xtype : 'textfield'})]
							),
			util.layout._tr([util.form._td({forceSelection : true,
				fieldLabel : '月份',
                xtype:'combo',
                triggerAction:'all',
                mode:'local',
                store:xlStore,
                valueField:'myId',
                displayField:'displayText',
                emptyText:'请选择'})],
					        [util.form._td({name : 'target10',fieldLabel : '公积金账户余额',xtype : 'numberfield'})]
			),
			util.layout._tr([util.form._td({name : 'target11',fieldLabel : '银行股票（股金）价值',xtype : 'numberfield'})],
					[util.form._td({name : 'target14',fieldLabel : '其中：我行长期贷款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target2',fieldLabel : '我行存款',xtype : 'numberfield'})], 
					[util.form._td({name : 'target15',fieldLabel : '其中：我行短期贷款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target8',fieldLabel : '应收款和应收票据',xtype : 'numberfield'})],
					[util.form._td({name : 'target16',fieldLabel : '应付票据',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target25',fieldLabel : '现金',xtype : 'numberfield'})],
					[util.form._td({name : 'target18',fieldLabel : '房地产负债',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target5',fieldLabel : '汽车',xtype : 'textfield'})],
					[util.form._td({name : 'target19',fieldLabel : '汽车贷款负债',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target12',fieldLabel : '其他资产',	xtype : 'numberfield'})],
					[util.form._td({name : 'target21',fieldLabel : '应付税款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target4',fieldLabel : '房产类资产',xtype : 'numberfield'})],
					[util.form._td({name : 'target23',fieldLabel : '其他负债',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target24',fieldLabel : '对外担保',xtype : 'numberfield'})],
					[util.form._td({name : 'id',xtype : 'hidden'})]
					),
					util.layout._tr([util.form._td({name : 'target27',fieldLabel : '存货',xtype : 'numberfield'})],
					[util.form._td({name : 'target26',fieldLabel : '预付款项',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target28',fieldLabel : '预收款项',xtype : 'numberfield'})],
					[util.form._td({name : 'target29',fieldLabel : '待摊租金',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target3',fieldLabel : '他行存款',xtype : 'numberfield'})],
					[util.form._td({name : 'target22',fieldLabel : '个人借款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target6',fieldLabel : '其他固定资产',xtype : 'numberfield'})],
					[util.form._td({name : 'target20',fieldLabel : '其他贷款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target7',fieldLabel : '上市交易证券价值',xtype : 'numberfield'})],
					[util.form._td({name : 'target17',fieldLabel : '应付账款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target9',fieldLabel : '保险的净现金和退保金',xtype : 'numberfield'})],
					[util.form._td({name : 'tmTyp',fieldLabel : '时间类型',xtype : 'textfield'})]
					),
					util.layout._tr([util.form._td({name : 'target1',fieldLabel : '资产合计',xtype : 'numberfield'})],
					[util.form._td({name : 'target13',fieldLabel : '负债合计',xtype : 'numberfield'})]

					),
					util.layout._tr([util.form._td({name : 'remark',fieldLabel : '备注',xtype : 'textarea'})]
					)
			]});}
});			
	
	//最终展现的panel
	var listPanel3 = new Mis.Ext.CrudPanel({
		id : "listPanel3",
	    title : "客户家庭收支信息",
		stUrl : basepath + '/perCustomerHomeInandex-info!indexPage.json?cust_id='+custId,
		addUrl : basepath + '/perCustomerHomeInandex-info.json',
		updateUrl : basepath + '/perCustomerHomeInandex-info.json',
		deUrl : basepath + '/perCustomerHomeInandex-info!batchDestroy.json',
		primary : "id",
		checkbox : true,
//		height:document.body.clientHeight,
//		width : document.body.scrollWidth-230,
//		gridHeight : document.body.clientHeight-100,
		frame : true,
		winHeight : 350,
		winWidth : 800,
		gclms : [ 
                    {name : 'id'},
				    {name : 'period'},  
				    {name :'custId'},
				    {name :'custName'},
				    
				    {name :'year',header:'年份',width:200},
				    {name :'month',header:'月份',width:200},
				    {name : 'target1',header : '上年总收入',renderer:money('0,000.00'),align : 'right',width:200},  
				    {name : 'target15',header : '上年总支出',renderer:money('0,000.00'),align : 'right',width:200}, 
					{name : 'target2',header : '合计',renderer:money('0,000.00'),align : 'right',width:200},
					{name :'target10'},
					{name :'target11'},
					{name :'target12'},
					{name :'target13'},
					{name :'target14'},
					{name :'target16'},
					{name :'target17'},
					{name :'target18'},
					{name :'target19'},
					{name :'target20'},
					{name :'target21'},
					{name :'target22'},
					{name :'target23'},
					{name :'target24'},
					{name :'target25'},
					{name :'target26'},
					{name :'target27'},
					{name :'target28'},
					{name :'target29'},
					{name :'target3'},
					{name :'target30'},
					{name :'target31'},
					{name :'target32'},
					{name :'target33'},
					{name :'target34'},
					{name :'target35'},
					{name :'target36'},
					{name :'target37'},
					{name :'target38'},
					{name :'target39'},
					{name :'target40'},
					{name :'target41'},
					{name :'target42'},
					{name :'target43'},
					{name :'target44'},
					{name :'target45'},
					{name :'target5'},
					{name :'target6'},
					{name :'target7'},
					{name :'target8'},
					{name :'target9'},
					{name :'tmTyp'}
				], 	
		pagesize : 10,
		
		
		//from的字段
		
		formColums :function(){
		return new Ext.form.FieldSet({items:[
			util.layout._tr([util.form._td({name : 'custId',value:custId,readOnly:true,xtype : 'textfield',fieldLabel : '客户码'})],
							[util.form._td({name : 'custName',value:custName,readOnly:true,xtype : 'textfield',fieldLabel : '客户名称'})]
							),
			util.layout._tr([util.form._td({name : 'year',fieldLabel : '年份',xtype : 'datefield',format : 'Y'})],
					        [util.form._td({name : 'period',fieldLabel : '时期',xtype : 'textfield'})]
							),
			util.layout._tr([util.form._td({forceSelection : true,
				fieldLabel : '月份',
                xtype:'combo',
                triggerAction:'all',
                mode:'local',
                store:xlStore,
                valueField:'myId',
                displayField:'displayText',
                emptyText:'请选择'})] ,
					       [util.form._td({name : 'tmTyp',fieldLabel : '时间类型',xtype : 'textfield'})]
			),
			util.layout._tr([util.form._td({name : 'target11',fieldLabel : '合伙收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target19',fieldLabel : '住房贷款分期偿还',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target13',fieldLabel : '信托收入',	xtype : 'numberfield'})],
					[util.form._td({name : 'target31',fieldLabel : '通讯费',xtype : 'numberfield'})]
					),
					util.layout._tr( [util.form._td({name : 'target10',fieldLabel : '股息收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target16',fieldLabel : '合计1',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target28',fieldLabel : '工资',xtype : 'numberfield'})],
					[util.form._td({name : 'target18',fieldLabel : '租金支出和物业费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target12',fieldLabel : '其他投资收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target2',fieldLabel : '合计',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target6',fieldLabel : '薪金收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target21',fieldLabel : '保险费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target40',fieldLabel : '非农收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target23',fieldLabel : '学费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target5',fieldLabel : '其中：经营收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target25',fieldLabel : '其他生活费用',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target7',fieldLabel : '经利和佣金',xtype : 'numberfield'})],
					[util.form._td({name : 'target27',fieldLabel : '毛利润',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target9',fieldLabel : '利息收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target29',fieldLabel : '租金',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target3',fieldLabel : '涉农收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target30',fieldLabel : '水电费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target14',fieldLabel : '其他收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target32',fieldLabel : '交通费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target33',fieldLabel : '材料损失',xtype : 'numberfield'})],
					[util.form._td({name : 'target34',fieldLabel : '广告及维护费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target36',fieldLabel : '其他税收',xtype : 'numberfield'})],
					[util.form._td({name : 'target35',fieldLabel : '招待费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target42',fieldLabel : '月可支配资金',xtype : 'numberfield'})],
					[util.form._td({name : 'target38',fieldLabel : '分期还款',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target39',fieldLabel : '税前利润',xtype : 'numberfield'})],
					[util.form._td({name : 'target22',fieldLabel : '赡养费和子女抚养费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target41',fieldLabel : '净利润',xtype : 'numberfield'})],
					[util.form._td({name : 'target37',fieldLabel : '其他费用（餐费）',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target43',fieldLabel : '其他影响现金流的因素',xtype : 'numberfield'})],
					[util.form._td({name : 'target44',fieldLabel : '前12个月的营业额',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target45',fieldLabel : '其他可变成本',xtype : 'numberfield'})],
					[util.form._td({name : 'target24',fieldLabel : '医疗费',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target20',fieldLabel : '其他贷款利息和本金偿还',xtype : 'numberfield'})],
					[util.form._td({name : 'target26',fieldLabel : '其他费用',xtype : 'numberfield'})]
					),
					util.layout._tr([util.form._td({name : 'target8',fieldLabel : '租金收入',xtype : 'numberfield'})],
					[util.form._td({name : 'target17',fieldLabel : '所得税和其他税负',xtype : 'numberfield'})]
					),
            util.layout._tr([util.form._td({name : 'target1',fieldLabel : '上年总收入',xtype : 'numberfield'})],
            		[util.form._td({name : 'target15',fieldLabel : '上年总支出',xtype : 'numberfield'})]
            ),
            util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})]
            )
			]});}
});	
	var listPanel = new Ext.TabPanel({
		renderTo:'viewport_center',
		id : 'listPanel',
		activeTab : 0,//鍒ゆ柇鏄惁鏄粠棣栭〉閾炬帴杩囨潵
		tabPosition : 'top',
		height : document.body.clientHeight - 30,
		items : [{
			title : '客户资债信息',
			forceFit : false,
			autoScroll : true,
			layout : 'fit',
			items : [listPanel2]
		}, {
			title : '客户家庭收支信息',
			forceFit : false,
			autoScroll : true,
			layout : 'fit',
			items : [listPanel3]
		}]
	});
	
//	var listPanel = new Ext.TabPanel({
//		id : 'listPanel',
//    	activeTab : 0,
//		tabPosition : 'top',
//		height:document.body.clientHeight,
//		primary : "id",
//		items : [ {
//			title : '客户资债信息',
//			items : [ listPanel2 ]
//		}, {
//			title : '客户家庭收支信息',
//			items : [ listPanel3 ]
//		}]
//	});
//			
//	var editPlanPanel = new Ext.Panel({
//		renderTo:'viewport_center',
//		labelWidth : 250,
//		layout : 'fit',
//		primary : "id",
//		buttonAlign : "center",
//		items : [ listPanel ]
//	});
});
