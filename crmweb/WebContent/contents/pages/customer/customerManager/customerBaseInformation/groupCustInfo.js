Ext.onReady(function() {
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var asoctDepRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var groupCustRecord = Ext.data.Record.create([
             {name: 'groupCustName', mapping: 'GROUP_CUST_NAME'},
             {name: 'groupCustShortName', mapping: 'GROUP_CUST_SHORT_NAME'},
             {name: 'groupCustMotherName', mapping: 'GROUP_CUST_MOTHER_NAME'} 
             ]);
	
	//集团客户信息的模型
	var groupCm = new Ext.grid.ColumnModel([asoctDepRownum,
	        {
				dataIndex : 'groupCustName',
				header : '集团名称',
				sortable : true,
				width : 200
			}, {
				dataIndex : 'groupCustShortName',
				header : '集团简称',
				width : 200
			}, {
				dataIndex : 'groupCustMotherName',
				header : '集团母公司',
			 	sortable : true,
			 	width : 200
	       }
	]);
	
	var cust_id =oCustInfo.cust_id;
	// 集团客户信息列表的数据查询
	var groupCustStore = new Ext.data.Store( {
		restful : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/asoct_dep.json?custId=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, groupCustRecord)	
	});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","GROUP_CUST_NAME":'文博贸易有限公司',"GROUP_CUST_SHORT_NAME":"文博贸易","GROUP_CUST_MOTHER_NAME":"文博钢铁贸易有限公司"}
			]
		};
	groupCustStore.loadData(memberData);

	//集团客户信息列表信息grid
	var asoctDepGrid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">集团客户信息列表</span>',
		height :160,
		width:document.body.scrollWidth-228,
		frame : true,
		autoScroll : true,
		store : groupCustStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : groupCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//集团客户信息end-------------------------------------------------------------
	//股东信息start-------------------------------------------------------------
	// 定义自动当前页行号
	var Rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var stockRecord = Ext.data.Record.create([
             {name: 'stockName', mapping: 'STOCK_NAME'},
             {name: 'stockScale', mapping: 'STOCK_SCALE'}
             ]);
	
	//股东信息关系列表的模型
	var stockCm = new Ext.grid.ColumnModel([Rownum,
	       {
			 	header : '股东名称',
			 	dataIndex : 'stockName',
			 	sortable : true,
			 	width : 200
	       }, {
				dataIndex : 'stockScale',
				header : '股东控股比例（%）',
				sortable : true,
				align : 'center',
				width : 200
			}
	]);
	// 股东信息关系表的数据查询
	var stockStore = new Ext.data.Store( {
		restful : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/dep_ln_asoct_rltnp.json?custId=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
				totalProperty:'num',// 记录总数
				root:'rows'// Json中的列表数据根节点
			}, stockRecord)
	});
	
	var memberData1= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","STOCK_NAME":'北京文博启胜投资有限公司',"STOCK_SCALE":"0.50"},
			{"rownum":"2","STOCK_NAME":'成都文博贸易有限公司',"STOCK_SCALE":"0.25"},
			{"rownum":"3","STOCK_NAME":'上海文博贸易有限公司',"STOCK_SCALE":"0.25"}
			]
		};
	stockStore.loadData(memberData1);

	//股东信息关系表信息grid
	var depLnAsoctRltnpGrid = new Ext.grid.GridPanel({
		height :160,
		width:document.body.scrollWidth-228,
		frame : true,
		autoScroll : true,
		store : stockStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : stockCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//股东信息关系end-------------------------------------------------------------
	//子公司情况登记表start-------------------------------------------------------------
	var subCompRecord = Ext.data.Record.create([
              {name: 'subCompName', mapping: 'SUB_COMP_NAME'},
              {name: 'subCompScale', mapping: 'SUB_COMP_SCALE'}
              ]);
	//子公司情况列表的模型
	var subCompCm = new Ext.grid.ColumnModel([Rownum,
	       {
			 	header : '子公司名称',
			 	dataIndex : 'subCompName',
			 	sortable : true,
			 	width : 200
	       }, {
			 	header : '对子公司持股比例（%）',
			 	dataIndex : 'subCompScale',
			 	sortable : true,
			 	align : 'center',
			 	width : 200
	       }
	]);
	
	// 子公司情况信息列表的数据查询
	var subCompStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/dep_ln_asoct_rgst.json?custId=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, subCompRecord)
	});
	
	var memberData2= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","SUB_COMP_NAME":'永康市博文贸易有限公司',"SUB_COMP_SCALE":"0.30"},
			{"rownum":"2","SUB_COMP_NAME":' 深圳市利德隆机电设备有限公司',"SUB_COMP_SCALE":"0.50"},
			{"rownum":"3","SUB_COMP_NAME":' 无锡市华堂电子有限公司',"SUB_COMP_SCALE":"0.40"},
			{"rownum":"4","SUB_COMP_NAME":' 深圳市光维科技有限公司',"SUB_COMP_SCALE":"0.30"}
			]
		};
	subCompStore.loadData(memberData2);
	
	//子公司情况列表信息grid
	var depLnAxoctRgstGrid = new Ext.grid.GridPanel({
		height :160,
		width:document.body.scrollWidth-228,
		frame : true,
		autoScroll : true,
		store : subCompStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : subCompCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//子公司情况情况登记表end-------------------------------------------------------------
	
	// 布局模型
	var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		//layout : 'fit',
		autoScroll:true,
		items : [asoctDepGrid,depLnAsoctRltnpGrid,depLnAxoctRgstGrid]
	});

});