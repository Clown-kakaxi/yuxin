/**
 * 客户模拟利润查询
 * @author weijl
 * @since 2012-09-21
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	/*******************参数级别数据源******************/
	var paramLevelStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '总行参数'], ['2', '分行参数']]
			});

	/*******************币种数据源******************/
	var currencyStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '本币'], ['2', '外币折本币'], ['3', '本外币合计']]
			});

	/*******************明细查询频度数据源******************/
	var MXfrequencyStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '本季'], ['2', '半年'], ['3', '本年累计']]
			});
	
	/*******************账户查询频度数据源******************/
	var ZHfrequencyStore = new Ext.data.ArrayStore({
		fields : ['key', 'value'],
		data : [['1', '本季'], ['2', '半年'], ['3', '本年累计']]
	});
	
	/*******************汇总查询CrudPanel******************/
	var hzPanel = new Mis.Ext.CrudPanel({
		id : "hzPanel",
		title : "汇总查询",
		// 在选项卡上，不显示关闭按钮
		closable : false, 
		//查询Url
		stUrl : basepath + '/ContributionInfoQuery.json',
		//主键
		primary : "custId",
		//关闭双击事件触发
		dbclick : false,
		//默认不自动加载
		defaultLoad : false,
		// 定义查询条件Form的高度
		seFormHeight : 100,
		// 定义增删详情页面弹出窗口高度
		winHeight : 250,
		// 宽度
		winWidth : 600,
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//查询条件表单
		selectItems : {
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'paramLevel',
					hiddenName : 'paramLevel',
					xtype : 'combo',
					fieldLabel : '参数级别',
					store : paramLevelStore,
					selectOnFocus : true,
					mode : 'local',
					editable:false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'custName',
					xtype : 'textfield',
					fieldLabel : '客户名称',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				border : false,
				items : [{
					name : 'custMgrName',
					xtype : 'textfield',
					fieldLabel : '客户维护人',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'sumDate',
					xtype : 'datefield',
					fieldLabel : '汇总日期',
					format : 'Y-m-d',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'currency',
					hiddenName : 'currency',
					xtype : 'combo',
					fieldLabel : '币种',
					store : currencyStore,
					selectOnFocus : true,
					mode : 'local',
					editable:false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [new Com.yucheng.bcrm.common.OrgField({
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					labelStyle : 'text-align:right;',
					id : 'CUST_ORG1', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'checkedNodes',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			}]
			},
			//结果展示列，可通过设置mapping属性匹配后台参数名
			gclms : [{
				name : 'custId',
				mapping : 'CUST_ID',
				header : '客户ID'
			}, {
				name : 'custName',
				mapping : 'CUST_NAME',
				header : '客户名称'
			}, {
				name : 'custManagerName',
				mapping :'CUST_MANAGER_NAME',
				header : '客户维护人'
			}, {
				name : 'bank',
				mapping : 'BANK',
				header : '所在分行'
			}, {
				name : 'unitName',
				mapping : 'UNITNAME',
				header : '所属机构'
			}, {
				name : 'quarter1Profit',
				mapping : 'QUARTER1_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '一季度模拟利润'
			}, {
				name : 'quarter2Profit',
				mapping : 'QUARTER2_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '二季度模拟利润'
			}, {
				name : 'quarter3Profit',
				mapping : 'QUARTER3_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '三季度模拟利润'
			}, {
				name : 'quarter4Profit',
				mapping : 'QUARTER4_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '四季度模拟利润'
			}, {
				name : 'totalProfit',
				mapping : 'TOTAL_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '累计模拟利润'
			}, {
				name : 'etlDate',
				mapping : 'ETL_DATE',
				header : '汇总日期',
				width : 100
			}],
			//加载数据，如存在demo数据且可用则加载demo数据
			loadCurrData : function(flag) {
				if (this.stUrl) {
					if (flag != 0 && this.selectForm) {
						var conditionStr = this.selectForm.getForm().getValues(false);
						this.store.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
					};
					this.store.load({
						params : {
							start : 0,
							limit : parseInt(this.pagesize)
						}
					});
				} else if (this.demoData)
					this.store.loadData(this.demoData);
			}

		});
	
	/*******************明细查询CrudPanel******************/
	var mxPanel = new Mis.Ext.CrudPanel({
		id : "mxPanel",
		title : "明细查询",
		//默认不自动加载
		defaultLoad : false,
		// 在选项卡上，不显示关闭按钮
		closable : false, 
		//查询Url
		stUrl : basepath + '/ContributionProfitDetailQuery.json',
		//主键
		primary : "custId",
		//关闭双击事件触发
		dbclick : false,
		// 定义查询条件Form的高度
		seFormHeight : 100,
		//设置结果展示panel的布局与滚动属性
		viewConfig:{
			   forceFit:false,
			   autoScroll:true
			},
		// 宽度
		winWidth : 600,
		//关闭多选框
		checkbox : false,
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//查询条件表单
		selectItems : {
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'paramLeveld',
					hiddenName : 'paramLeveld',
					xtype : 'combo',
					fieldLabel : '参数级别',
					store : paramLevelStore,
					selectOnFocus : true,
					editable:false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					mode : 'local',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'custNamed',
					xtype : 'textfield',
					fieldLabel : '客户名称',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				border : false,
				items : [{
					name : 'marketStuffd',
					xtype : 'textfield',
					fieldLabel : '客户维护人',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'sumDated',
					xtype : 'datefield',
					fieldLabel : '汇总日期',
					format : 'Y-m-d',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'currencyd',
					hiddenName : 'currencyd',
					xtype : 'combo',
					fieldLabel : '币种',
					store : currencyStore,
					selectOnFocus : true,
					editable:false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					mode : 'local',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'frequencyd',
					hiddenName : 'frequencyd',
					xtype : 'combo',
					fieldLabel : '频度',
					store : MXfrequencyStore,
					selectOnFocus : true,
					mode : 'local',
					editable:false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100,
				defaultType : 'textfield',
				border : false,
				items : [new Com.yucheng.bcrm.common.OrgField({
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					labelStyle : 'text-align:right;',
					id : 'CUST_ORG2', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'unitsd',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			}]
			},
			//结果展示列，可设置mapping属性匹配后台参数名
			gclms : [{
				name : 'custId',
				mapping : 'CUST_ID',
				header : '客户ID',
				width : 100
			}, {
				name : 'custName',
				mapping : 'CUST_NAME',
				header : '客户名称',
				width : 100
			}, {
				name : 'custManagerName',
				mapping : 'CUST_MANAGER_NAME',
				header : '客户维护人',
				width : 100
			}, {
				name : 'bank',
				mapping : 'BANK',
				header : '所在分行',
				width : 100
			}, {
				name : 'unitName',
				mapping : 'UNITNAME',
				header : '所属机构',
				width : 100
			}, {
				name : 'nbailDeAvg',
				mapping : 'NBAIL_DE_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '非保证金存款日均',
				width : 100
			}, {
				name : 'nbailDeProfit',
				mapping : 'NBAIL_DE_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '非保证金存款利润',
				width : 100
			}, {
				name : 'bailDeAvg',
				mapping : 'BAIL_DE_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '保证金存款日均',
				width : 100
			}, {
				name : 'bailDeProfit',
				mapping : 'BAIL_DE_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '保证金存款利润',
				width : 100
			}, {
				name : 'interbankDeAvg',
				mapping : 'INTERBANK_DE_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '同业存款日均',
				width : 100
			}, {
				name : 'interbankDeProfit',
				mapping : 'INTERBANK_DE_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '同业存款利润',
				width : 100
			}, {
				name : 'comLoanAvg',
				mapping : 'COM_LOAN_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '对公纯贷款日均',
				width : 100
			}, {
				name : 'comLoanProfit',
				mapping : 'COM_LOAN_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '对公纯贷款利润',
				width : 100
			}, {
				name : 'discountAvg',
				mapping : 'DISCOUNT_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '贴现日均',
				width : 100
			}, {
				name : 'discountProfit',
				mapping : 'DISCOUNT_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '贴现利润',
				width : 100
			}, {
				name : 'midbuTotalIncome',
				mapping : 'MIDBU_TOTAL_INCOME',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '中间业务累计收入',
				width : 100
			}, {
				name : 'midbuProfit',
				mapping : 'MIDBU_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '中间业务利润',
				width : 100
			}, {
				name : 'totalProfit',
				mapping : 'TOTAL_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '本期模拟利润合计',
				width : 100
			}, {
				name : 'etlDate',
				mapping : 'ETL_DATE',
				header : '汇总日期',
				width : 100
			}],
			//加载数据，如存在demo数据且可用则加载demo数据
			loadCurrData : function(flag) {
				if (this.stUrl) {
					if (flag != 0 && this.selectForm) {
						var conditionStr = this.selectForm.getForm().getValues(false);
						this.store.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
					};
					this.store.load({
						params : {
							start : 0,
							limit : parseInt(this.pagesize)
						}
					});
				} else if (this.demoData)
					this.store.loadData(this.demoData);
			}
		});

	var flag = 0;
	
	/*******************账户查询CrudPanel******************/
	var zhPanel = new Mis.Ext.CrudPanel({
		id : "zhPanel",
		title : "账户查询",
		// 在选项卡上，不显示关闭按钮
		closable : false, 
		//查询Url
		stUrl : basepath + '/ContributionAccProfitDetailQuery.json',
		//主键
		primary : "custId",
		//关闭双击事件触发
		dbclick : false,
		//默认不自动加载
		defaultLoad : false,
		//关闭多选框
		checkbox : false,
		// 定义查询条件Form的高度
		seFormHeight : 100,
		// 定义增删详情页面弹出窗口高度
		winHeight : 300,
		// 宽度
		winWidth : 600,
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			Ext.getCmp('hhbDt').setValue(new Date(b.hhbDt.time));
		},
		//查询条件表单
		selectItems : {
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'paramLevela',
					hiddenName : 'paramLevela',
					xtype : 'combo',
					fieldLabel : '参数级别',
					store : paramLevelStore,
					selectOnFocus : true,
					editable:false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					mode : 'local',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'custNamea',
					xtype : 'textfield',
					fieldLabel : '客户名称',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				border : false,
				items : [{
					name : 'marketStuffa',
					xtype : 'textfield',
					fieldLabel : '客户维护人',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'sumDatea',
					xtype : 'datefield',
					fieldLabel : '汇总日期',
					format : 'Y-m-d',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'currencya',
					hiddenName : 'currencya',
					xtype : 'combo',
					fieldLabel : '币种',
					store : currencyStore,
					selectOnFocus : true,
					mode : 'local',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [{
					name : 'frequencya',
					hiddenName : 'frequencya',
					xtype : 'combo',
					fieldLabel : '频度',
					store : ZHfrequencyStore,
					selectOnFocus : true,
					mode : 'local',
					emptyText : '请选择',
					valueField : 'key',
					displayField : 'value',
					width : '100',
					anchor : '90%'
				}]
			}, {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 100,
				defaultType : 'textfield',
				border : false,
				items : [new Com.yucheng.bcrm.common.OrgField({
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					labelStyle : 'text-align:right;',
					id : 'CUST_ORG3', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'unitsa',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			}]
			},
			//查询结果展示列，可设置mapping属性匹配后台参数名
			gclms : [{
				name : 'custId',
				mapping : 'CUST_ID',
				header : '客户ID',
				width : 100
			}, {
				name : 'custName',
				mapping : 'CUST_NAME',
				header : '客户名称',
				width : 100
			}, {
				name : 'accountNo',
				mapping : 'ACCOUNT_NO',
				header : '账号',
				width : 100
			}, {
				name : 'outlets',
				mapping : 'OUTLETS',
				header : '开户网点',
				width : 100
			}, {
				name : 'custManagerName',
				mapping : 'CUST_MANAGER_NAME',
				header : '客户维护人',
				width : 100
			}, {
				name : 'disBalance',
				mapping : 'DIS_BALANCE',
				header : '分成比例',
				width : 100
			}, {
				name : 'bank',
				mapping : 'BANK',
				header : '所在分行',
				width : 100
			}, {
				name : 'unitName',
				mapping : 'UNITNAME',
				header : '所属机构',
				width : 100
			}, {
				name : 'nbailDeAvg',
				mapping : 'NBAIL_DE_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '非保证金存款日均',
				width : 100
			}, {
				name : 'nbailDeProfit',
				mapping : 'NBAIL_DE_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '非保证金存款利润',
				width : 100
			}, {
				name : 'bailDeAvg',
				mapping : 'BAIL_DE_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '保证金存款日均',
				width : 100
			}, {
				name : 'bailDeProfit',
				mapping : 'BAIL_DE_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '保证金存款利润',
				width : 100
			}, {
				name : 'interbankDeAvg',
				mapping : 'INTERBANK_DE_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '同业存款日均',
				width : 100
			}, {
				name : 'interbankDeProfit',
				mapping : 'INTERBANK_DE_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '同业存款利润',
				width : 100
			}, {
				name : 'comLoanAvg',
				mapping : 'COM_LOAN_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '对公纯贷款日均',
				width : 100
			}, {
				name : 'comLoanProfit',
				mapping : 'COM_LOAN_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '对公纯贷款利润',
				width : 100
			}, {
				name : 'discountAvg',
				mapping : 'DISCOUNT_AVG',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '贴现日均',
				width : 100
			}, {
				name : 'discountProfit',
				mapping : 'DISCOUNT_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '贴现利润',
				width : 100
			}, {
				name : 'midbuTotalIncome',
				mapping : 'MIDBU_TOTAL_INCOME',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '中间业务累计收入',
				width : 100
			}, {
				name : 'midbuProfit',
				mapping : 'MIDBU_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '中间业务利润',
				width : 100
			}, {
				name : 'totalProfit',
				mapping : 'TOTAL_PROFIT',
				align : 'right',
				renderer: money('0,000.00' ),
				header : '本期模拟利润合计',
				width : 100
			}, {
				name : 'etlDate',
				mapping : 'ETL_DATE',
				header : '汇总日期',
				width : 100
			}],
			//加载数据，如存在demo数据且可用则加载demo数据
			loadCurrData : function(flag) {
				if (this.stUrl) {
					if (flag != 0 && this.selectForm) {
						var conditionStr = this.selectForm.getForm().getValues(false);
						this.store.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
					};
					this.store.load({
						params : {
							start : 0,
							limit : parseInt(this.pagesize)
						}
					});
				} else if (this.demoData)
					this.store.loadData(this.demoData);
			}
		});

	/*******************整体布局******************/
	var tabs = new Ext.TabPanel({
		xtype : "tabpanel",
		region : "center",
		activeTab : 0,
		items : [hzPanel, mxPanel, zhPanel]
	});
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [tabs]
	});

});