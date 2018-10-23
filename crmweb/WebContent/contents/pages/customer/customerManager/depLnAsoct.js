Ext.onReady(function() {
	Ext.QuickTips.init();
	var cust_id =oCustInfo.cust_id;
	//var cust_id = '100';
	// 关联存款积数表的数据查询
	var asoctDepStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/asoct_dep.json?custId=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name:'CTR_OU_ID'},{name:'CST_NO'},{name: 'PRIM_CST_ID'},{name: 'CST_NM'},
		   {name : 'DEP_LN_ASOCT_ACML_AMT'},{name : 'EFF_DT',type : 'date',mapping : 'EFF_DT.time',dateFormat : 'time'}])
	});
	asoctDepStore.load();
	// 定义自动当前页行号
	var asoctDepRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//关联存款积数列表的模型
	var asoctDepCm = new Ext.grid.ColumnModel([asoctDepRownum,
	        {
				dataIndex : 'CST_NM',
				header : '统计日期',
				sortable : true,
				width : 100,
				renderer:function(){return '2012-06-29';}
			}, {
				dataIndex : 'DEP_LN_ASOCT_ACML_AMT',
				header : '存贷挂钩总积数',
				width : 200
			}, {
			 	header : '存贷挂钩建立日期',
			 	dataIndex : 'EFF_DT',
			 	sortable : true,
			 	width : 200
	       }
	]);
	//关联存款积数表信息grid
	var asoctDepGrid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">关联存款积数表</span>',
		height :100,
		width :1000,
		frame : true,
		autoScroll : true,
		store : asoctDepStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : asoctDepCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//关联存款积数end-------------------------------------------------------------
	//存贷积数关系start-------------------------------------------------------------
	// 存贷积数关系表的数据查询
	var depLnAsoctRltnpStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/dep_ln_asoct_rltnp.json?custId=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name:'CTR_OU_ID'},{name:'CST_NO'},{name: 'PRIM_CST_ID'},{name: 'CST_NM'},
		   {name : 'DEP_AR_ID'},{name : 'RLTNP_WH_BRW'},{name : 'MANAGER_ID'},
		   {name : 'ACTV_F'},{name : 'DEP_LN_ASOCT_ACML_AMT'},
		   {name : 'EFF_DT',type : 'date',mapping : 'EFF_DT.time',dateFormat : 'time'},
		   {name : 'END_DT',type : 'date',mapping : 'END_DT.time',dateFormat : 'time'}])
	});
	depLnAsoctRltnpStore.load();
	// 定义自动当前页行号
	var depLnAsoctRltnpRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//存贷积数关系列表的模型
	var depLnAsoctRltnpCm = new Ext.grid.ColumnModel([depLnAsoctRltnpRownum,
	       {
				dataIndex : 'CST_NM',
				header : '统计日期',
				sortable : true,
				width : 90,
				renderer:function(){return '2012-06-29';}
			}, {
			 	header : '存款账号',
			 	dataIndex : 'DEP_AR_ID',
			 	sortable : true,
			 	width : 90
	       }, {
				dataIndex : 'RLTNP_WH_BRW',
				header : '与借款人关',
				sortable : true,
				width : 90
			}, {
			 	header : '管理客户经理',
			 	dataIndex : 'MANAGER_ID',
			 	sortable : true,
			 	width : 90
	       }, {
				dataIndex : 'ACTV_F',
				header : '状态',
				sortable : true,
				width : 90
			}, {
				dataIndex : 'DEP_LN_ASOCT_ACML_AMT',
				header : '存贷挂钩总积数',
				width : 90
			}, {
			 	header : '生效日期',
			 	dataIndex : 'EFF_DT',
			 	sortable : true,
			 	width : 90
	       }, {
			 	header : '停用日期',
			 	dataIndex : 'END_DT',
			 	sortable : true,
			 	width : 90
	       }
	]);
	//存贷积数关系表信息grid
	var depLnAsoctRltnpGrid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">存贷积数关系表</span>',
		height :230,
		width :1000,
		frame : true,
		autoScroll : true,
		store : depLnAsoctRltnpStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : depLnAsoctRltnpCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//存贷积数关系end-------------------------------------------------------------
	//存贷积数使用情况登记表start-------------------------------------------------------------
	// 关联存款积数表的数据查询
	var depLnAxoctRgstStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/dep_ln_asoct_rgst.json?custId=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name:'LN_AR_ID'},{name:'LN_CTR_OU_ID'},{name: 'PRIM_CST_ID'},{name: 'ORIG_RTO_DEP_TO_LN'},
		   {name : 'RTO_DEP_TO_LN'},{name : 'PREF_INT_AMT'},{name : 'ACT_PREF_INT_AMT'},
		   {name : 'EFF_DT',type : 'date',mapping : 'EFF_DT.time',dateFormat : 'time'},
		   {name : 'END_DT',type : 'date',mapping : 'END_DT.time',dateFormat : 'time'}])
	});
	depLnAxoctRgstStore.load();
	// 定义自动当前页行号
	var depLnAxoctRgstRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//关联存款积数列表的模型
	var depLnAxoctRgstCm = new Ext.grid.ColumnModel([depLnAxoctRgstRownum,
	       {
			 	header : '统计日期',
			 	dataIndex : 'PRIM_CST_ID',
			 	sortable : true,
			 	width : 80,
				renderer:function(){return '2012-06-29';}
	       }, {
			 	header : '贷款账号',
			 	dataIndex : 'LN_AR_ID',
			 	sortable : true,
			 	width : 80
	       }, {
			 	header : '贷款机构号',
			 	dataIndex : 'LN_CTR_OU_ID',
			 	sortable : true,
			 	width : 80
	       }, {
				dataIndex : 'ORIG_RTO_DEP_TO_LN',
				header : '存贷积数比例',
				sortable : true,
				width : 80
			}, {
				dataIndex : 'RTO_DEP_TO_LN',
				header : '使用存贷积数比例',
				width : 80
			}, {
			 	header : '开始日期',
			 	dataIndex : 'EFF_DT',
			 	sortable : true,
			 	width : 80
	       }, {
			 	header : '结束日期',
			 	dataIndex : 'END_DT',
			 	sortable : true,
			 	width : 80
	       }, {
			 	header : '应优惠利息',
			 	dataIndex : 'PREF_INT_AMT',
			 	sortable : true,
			 	width : 80
	       }, {
			 	header : '实际优惠利息',
			 	dataIndex : 'ACT_PREF_INT_AMT',
			 	sortable : true,
			 	width : 80
	       }
	]);
	//关联存款积数表信息grid
	var depLnAxoctRgstGrid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">存贷积数使用情况登记表</span>',
		height :230,
		width :1000,
		frame : true,
		autoScroll : true,
		store : depLnAxoctRgstStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : depLnAxoctRgstCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//存贷积数使用情况登记表end-------------------------------------------------------------
	
	// 布局模型
	var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		//layout : 'fit',
		autoScroll:true,
		items : [asoctDepGrid,depLnAsoctRltnpGrid,depLnAxoctRgstGrid]
	});

});