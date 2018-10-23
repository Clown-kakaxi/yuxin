Ext.onReady(function() {
	Ext.QuickTips.init();
	// 客户编号
		var cust_id = oCustInfo.cust_id;
		// 客户储蓄卡积分panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath
					+ '/acrmFCiCustIntegral-info!indexPage.json?custId='
					+ cust_id+'&integralType=1',
			// 定义查询条件Form的高度
			seFormHeight : 0,
			// 定义增删详情页面弹出窗口高度
			winHeight : 450,
			// 定义增删详情页面弹出窗口宽度
			winWidth : 900,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			// 定义高度
//			height : document.body.clientHeight,
			// 定义宽度
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 80,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {
				name : 'id'
			}, {
				name : 'custId'
			}, {
				name : 'custId',
				header : '客户编号',
				width : 100
			}, {
				name : 'custName',
				header : '客户名称',
				width : 100
			}, {
				name : 'custType',
				header : '客户类别',
				width : 100
			}, {
				name : 'countNum',
				header : '客户累计积分',
				type : 'float',
				width : 100
			}, {
				name : 'custCumCount',
				header : '客户当月积分',
				type : 'float',
				width : 100
			}, {
				name : 'custCumCost',
				header : '客户当月消费积分',
				type : 'float',
				width : 100
			}, {
				name : 'custCostSum',
				header : '客户累计消费积分',
				type : 'float',
				width : 100
			}, {
				name : 'custSpareCount',
				header : '客户可用积分',
				type : 'float',
				width : 100
			}, {
				name : 'etlDate',
				header : '平台日期',
				type : 'date',
				width : 100
			} ]

		});
			
		// 信用卡积分panel
		var listPanel2 = new Mis.Ext.CrudPanel( {
			id : "listPanel2",
			title : "",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath
					+ '/acrmFCiCustIntegral-info!indexPage.json?custId='
					+ cust_id+'&integralType=2',
			// 定义查询条件Form的高度
			seFormHeight : 0,
			// 定义增删详情页面弹出窗口高度
			winHeight : 450,
			// 定义增删详情页面弹出窗口宽度
			winWidth : 900,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			// 定义高度
//			height : document.body.clientHeight,
			// 定义宽度
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 80,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {
				name : 'id'
			}, {
				name : 'custId'
			}, {
				name : 'custId',
				header : '客户编号',
				width : 100
			}, {
				name : 'custName',
				header : '客户名称',
				width : 100
			}, {
				name : 'custType',
				header : '客户类别',
				width : 100
			}, {
				name : 'countNum',
				header : '客户累计积分',
				type : 'float',
				width : 100
			}, {
				name : 'custCumCount',
				header : '客户当月积分',
				type : 'float',
				width : 100
			}, {
				name : 'custCumCost',
				header : '客户当月消费积分',
				type : 'float',
				width : 100
			}, {
				name : 'custCostSum',
				header : '客户累计消费积分',
				type : 'float',
				width : 100
			}, {
				name : 'custSpareCount',
				header : '客户可用积分',
				type : 'float',
				width : 100
			}, {
				name : 'etlDate',
				header : '平台日期',
				type : 'date',
				width : 100
			} ]

		});
			
		var listPanel = new Ext.TabPanel({
			renderTo:'viewport_center',
			id : 'listPanel',
			activeTab : 0,
			tabPosition : 'top',
			height : document.body.clientHeight - 30,
			items : [{
				title : '客户储蓄卡积分',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel]
			}, {
				title : '客户信用卡积分',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel2]
			}]
		});
		

	});