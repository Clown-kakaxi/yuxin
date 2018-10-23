Ext.onReady(function() {
	Ext.QuickTips.init();
	// 客户编号
		var cust_id = oCustInfo.cust_id;
		var cust_name = oCustInfo.cust_name;

		var tbar = new Ext.Toolbar( {
			items : [{
				text : '积分扣减记录',
				handler : function() {
					showWin.show();
				}
			} ]
		});
		// 客户活动积分panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户活动积分信息",
			tbar : tbar,

			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath
					+ '/acrmFCiCustIntegral-info!indexPage.json?custId='
					+ cust_id+'&integralType=3',
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
//			gridHeight : document.body.clientHeight - 100,
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
			
		//活动积分扣减面板
		var listPanel3 = new Mis.Ext.CrudPanel( {
			id : "listPanel3",
			title : "客户活动积分扣减记录",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			checkbox : true,
			// 查询路径设置
			stUrl : basepath + '/acrmFCiCustIntegralChange!indexPage.json?custId=' + cust_id,
			addUrl : basepath + '/acrmFCiCustIntegralChange.json',
			updateUrl : basepath + '/acrmFCiCustIntegralChange.json',
			deUrl : basepath + '/acrmFCiCustIntegralChange!batchDestroy.json',
			// 定义查询条件Form的高度
			seFormHeight : 0,
			// 定义增删详情页面弹出窗口高度
			winHeight : 300,
			// 定义增删详情页面弹出窗口宽度
			winWidth : 450,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			// 定义高度
//			height : document.body.clientHeight,
			// 定义宽度
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
			gridHeight : document.body.clientHeight - 140,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'id'}, {name : 'custId',header : '客户编号',width : 100}, 
				{name : 'custName',header : '客户名称',width : 100}, 
				{name : 'activityName',header : '参加活动名称',width : 100}, 
				{name : 'activityTime',	header : '参加活动时间',	type : 'date',width : 100}, 
				{name : 'deductionIntegral',header : '扣减积分',	type : 'float',	width : 100	}, 
				{name : 'handler',header : '扣分人',width : 100}, 
				{name : 'handleTime',header : '扣分时间',type : 'date',width : 100
			} ],
			// 新增、修改、详情的form的字段
			fclms : [ {
						layout : 'form',
						items : [{
							name : 'activityName',
							fieldLabel : '参加活动名称',
							width : 100,
							xtype : 'textfield',
							anchor : '100%'
						},{
							id : 'activityTime',
							name : 'activityTime',
							fieldLabel : '参加活动时间',
							width : 100,
							xtype : 'datefield',
							format : 'Y-m-d',
							anchor : '100%'
						},{
							name : 'deductionIntegral',
							fieldLabel : '扣减积分',
							width : 100,
							xtype : 'textfield',
							anchor : '100%'
						}, {
							id : 'custId',
							name : 'custId',
							xtype : 'hidden'
						}, {
							id : 'custName',
							name : 'custName',
							xtype : 'hidden'
						}, {
							id : 'handler',
							name : 'handler',
							xtype : 'hidden'
						}, {
							id : 'handleTime',
							name : 'handleTime',
							xtype : 'hidden'
						}, {
							// 特别注意：
							// 必须放置隐藏域的主键
							name : 'id',
							xtype : 'hidden'
						}]
					}],
			createFun : function(){
				Ext.getCmp('custId').setValue(cust_id);
				Ext.getCmp('custName').setValue(cust_name);
			},
			afterSeOneFun : function(b){
				Ext.getCmp('activityTime').setValue(new Date(b.activityTime.time));
				if(!Ext.isEmpty(b.handleTime)){
					Ext.getCmp('handleTime').setValue(new Date(b.handleTime.time).format('Y-m-d'));
				}
			}

		});
		
		var showWin = new Ext.Window( {
			title : '',
			plain : true,
			closeAction : 'hide',
			frame : true,
			resizable : true,
			draggable : true,
			closable : true,
			width : 800,
			height : 400,
			layout : 'fit',
			items : [ listPanel3]
		});
		
		var editPlanPanel = new Ext.Panel({
			renderTo:'viewport_center',
			labelWidth : 250,
			height : document.body.clientHeight - 30,
			layout : 'fit',
			primary : "id",
			buttonAlign : "center",
			items : [ listPanel ]
		});

	});