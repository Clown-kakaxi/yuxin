/*
 * 客户等级信息
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var custLevelStore=util.form._store('/lookup.json?name=P_CUST_ZC_GRADE');	
	custLevelStore.load();
	var cust_id =oCustInfo.cust_id;
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户分级信息",
		primary : "id",
		stUrl : basepath + '/acrmFCiCustGrade-info!indexPage.json?cust_id='+cust_id,
		seFormHeight : 50,
		dbclick : false,
		winHeight : 450,
		winWidth : 900,
		pagesize : 20,
		gclms : [ 
		    {name : 'custId'}, 
		    {name : 'custName',header : '客户名称',width : 100},
		    {name : 'custTyp',header : '客户类别',width : 100},
		    {name : 'targetCount',header : '评级指标得分',width : 100,type:'float'},
		    {name : 'custLevel',header : '客户资产等级',width : 100,type :'mapping',store : custLevelStore,valueField : 'key',displayField : 'value'},
		    {name : 'brandLevel',header : '客户等级',width : 100},
		    {name : 'etlDt',header : '平台日期',width : 100,type:'date'}
	  ]
	});
	debugger;
		var viewport = new Ext.Panel( {
			 renderTo:'viewport_center',
			 height:document.body.scrollHeight-30,
			layout : 'fit',
			autoScroll:true,
			items : [ listPanel ]
		});

});