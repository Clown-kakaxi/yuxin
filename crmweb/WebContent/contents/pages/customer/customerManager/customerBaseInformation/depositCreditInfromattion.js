/*
 * 存贷比信息
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var custid =oCustInfo.cust_id;
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		dbclick : false,
		title : "存贷比信息",
		//客户编号
		primary : "id",
		//单选框
		//singleSelect : true,
		//查询路径设置
		stUrl : basepath + '/acrmFCiDepandloan-info!indexPage.json?custid='+custid,
		//详情的url
		//detailUrl :
		//定义查询条件Form的高度
		seFormHeight : 50,
		//定义增删详情页面弹出窗口高度
		winHeight : 450,
		//宽度
		winWidth : 900,
		//设置分页每页显示条数，若不设置则不出现分页栏
		 pagesize : 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
//		afterSeOneFun : function(b) {
//			//debugger;
//			Ext.getCmp('odsStDate').setValue(new Date(b.odsStDate.time));
//		},
		//查询列表字段定义，有header属性则在页面显示 
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ 
		    {name : 'custId'}, 
		    {name:'custId1',header:'统计日期',
				renderer:function(){return '2012-06-29';}},
		    {name : 'custName'},
		    {name : 'savingAveYear',header : '存款年积数',width : 100},
		    {name : 'loanAveYear',header : '贷款年积数',width : 100},
		    {name : 'depoLoanLast',header : '上年末存贷比',width : 100},
		    {name : 'depoLoan',header : '存贷比',width : 100},
		    {name : 'odsStDate',header : '数据日期'},
		    {name : 'lastSavingAveYear'},
		    {name : 'lastLoanAveYear'}
	  ]

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