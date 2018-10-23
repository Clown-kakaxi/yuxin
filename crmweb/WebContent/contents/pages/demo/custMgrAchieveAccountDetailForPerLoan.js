	var record4 = Ext.data.Record.create([ {
		name : 'id',
		mapping : 'ID'
	}, {
		name : 'accountNo',
		mapping : 'ACCOUNT_NO'
	}, {
		name : 'accountName',
		mapping : 'ACCOUNT_NAME'
	}, {
		name : 'openOrgName',
		mapping : 'OPEN_ORG_NAME'
	}, {
		name : 'accountXcName',
		mapping : 'ACCOUNT_XC_NAME'
	}, {
		name : 'xcNo',
		mapping : 'XC_NO'
	}, {
		name : 'atSubOrgName',
		mapping : 'AT_SUB_ORG_NAME'
	}, {
		name : 'atOrgName',
		mapping : 'AT_ORG_NAME'
	}, {
		name : 'productClass',
		mapping : 'PRODUCT_CLASS'
	}, {
		name : 'distributeDate',
		mapping : 'DISTRIBUTE_DATE'
	}, {
		name : 'endDate',
		mapping : 'END_DATE'
	}, {
		name : 'loanDateDate',
		mapping : 'LOAN_DATE_DATE'
	}, {
		name : 'profitScale',
		mapping : 'PROFIT_SCALE'
	}, {
		name : 'flowerRate',
		mapping : 'FLOWER_RATE'
	}, {
		name : 'localMonthDayAvg',
		mapping : 'LOCAL_MONTH_DAY_AVG'
	}, {
		name : 'localseasonDayAvg',
		mapping : 'LOCAL_SEASON_DAY_AVG'
	}, {
		name : 'localYearDayAvg',
		mapping : 'LOCAL_YEAR_DAY_AVG'
	}, {
		name : 'localDayAmount',
		mapping : 'LOCAL_DAY_AMOUNT'
	}, {
		name : 'loanAllScale',
		mapping : 'LOAN_ALL_SCALE'
	}, {
		name : 'profitIncome',
		mapping : 'PROFIT_INCOME'
	}, {
		name : 'qianAmount',
		mapping : 'QIAN_AMOUNT'
	}, {
		name : 'tuoAmount',
		mapping : 'TUO_AMOUNT'
	}, {
		name : 'fiveClassStatus',
		mapping : 'FIVE_CLASS_STATUS'
	}]);
	// 定义列模型
	var cm4 = new Ext.grid.ColumnModel([ rownum,
                {header : 'ID',width : 50,align : 'center',dataIndex : 'id',hidden:true},//
           	    {header : '账号',width : 150,align : 'center',dataIndex : 'accountNo',sortable : true}, 
         	    {header : '账户名称',width : 150,align : 'center',dataIndex : 'accountName',sortable : true}, 
         	    {header : '开立网点',width : 150,align : 'center',dataIndex : 'openOrgName',sortable : true},// 
         	    {header : '吸存人姓名',width : 150,align : 'center',dataIndex : 'accountXcName',sortable : true},
         	    {header : '吸存号',width : 150,align : 'center',dataIndex : 'xcNo',sortable : true}, 
         	    {header : '所属分行',width : 150,align : 'center',dataIndex : 'atSubOrgName',sortable : true}, 
         	    {header : '所属机构',width : 150,align : 'center',dataIndex : 'atOrgName',sortable : true}, 
         	    {header : '产品种类',width : 150,align : 'center',dataIndex : 'productClass',sortable : true},
         	    {header : '发放日期',width : 150,align : 'center',dataIndex : 'distributeDate',sortable : true}, 
         	    {header : '到期日期',width : 150,align : 'center',dataIndex : 'endDate',sortable : true}, 
         	    {header : '贷款期限',width : 150,align : 'center',dataIndex : 'loanDateDate',sortable : true}, 
         	    {header : '利率%',width : 150,align : 'center',dataIndex : 'profitScale',sortable : true},
         	    {header : '浮动幅度',width : 150,align : 'center',dataIndex : 'flowerRate',sortable : true}, 
         	    {header : '本月日均',width : 150,align : 'right',dataIndex : 'localMonthDayAvg',sortable : true}, 
         	    {header : '本季日均',width : 150,align : 'right',dataIndex : 'localseasonDayAvg',sortable : true}, 
         	    {header : '本年日均',width : 150,align : 'right',dataIndex : 'localYearDayAvg',sortable : true}, 
         	    {header : '本日余额',width : 150,align : 'right',dataIndex : 'localDayAmount',sortable : true}, 
         	    {header : '在该笔贷款账户日均中占比',width : 150,align : 'center',dataIndex : 'loanAllScale',sortable : true}, 
         	    {header : '利息收入',width : 150,align : 'right',dataIndex : 'profitIncome',sortable : true},
         	    {header : '欠息余额',width : 150,align : 'right',dataIndex : 'qianAmount',sortable : true},
         	    {header : '拖欠金额',width : 150,align : 'right',dataIndex : 'tuoAmount',sortable : true},
         	    {header : '五级分类状态',width : 150,align : 'center',dataIndex : 'fiveClassStatus',sortable : true}
	]);
	
	/**
	 * 数据存储
	 */
	var store4 = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/mktModelManage.json'//custMgrGroupCount
		}),
		reader : new Ext.data.JsonReader({
//			successProperty : 'success',
//			idProperty : 'ID',
//			messageProperty : 'message',
//			root : 'json.data',
//			totalProperty : 'json.count'
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, record4)
	});
	
	var memberData4= {
			TOTALCOUNT:3,
			rows : [ {
				"rownum" : "1",
				"ACCOUNT_NO" : '00822202',
				"ACCOUNT_NAME" : "周中伟",
				"OPEN_ORG_NAME" : "华夏广州分行",
				"ACCOUNT_XC_NAME" : "周梦琪",
				"XC_NO" : "016901",
				"AT_SUB_ORG_NAME" : "华夏广州支行",
				"AT_ORG_NAME" : "华夏广州分行",
				"PRODUCT_CLASS" : "贷款产品",
				"DISTRIBUTE_DATE" : "2011-04-02",
				"END_DATE" : "2013-04-01",
				"LOAN_DATE_DATE" : "2年",
				"PROFIT_SCALE" : "0.12",
				"FLOWER_RATE" : "0.05",
				"LOCAL_MONTH_DAY_AVG" : "3",
				"LOCAL_SEASON_DAY_AVG" : "4",
				"LOCAL_YEAR_DAY_AVG" : "2",
				"LOCAL_DAY_AMOUNT" : "50",
				"LOAN_ALL_SCALE" : "0.5",
				"PROFIT_INCOME" : "1.2",
				"QIAN_AMOUNT" : "0.12",
				"TUO_AMOUNT" : "40",
				"FIVE_CLASS_STATUS" : "可疑"
			},{
				"rownum" : "2",
				"ACCOUNT_NO" : '00822230',
				"ACCOUNT_NAME" : "杨洪利",
				"OPEN_ORG_NAME" : "华夏北京银行",
				"ACCOUNT_XC_NAME" : "周梦琪",
				"XC_NO" : "016901",
				"AT_SUB_ORG_NAME" : "华夏北京支行",
				"AT_ORG_NAME" : "华夏北京分行",
				"PRODUCT_CLASS" : "贷款产品",
				"DISTRIBUTE_DATE" : "2010-04-02",
				"END_DATE" : "2013-04-01",
				"LOAN_DATE_DATE" : "3年",
				"PROFIT_SCALE" : "0.15",
				"FLOWER_RATE" : "0.08",
				"LOCAL_MONTH_DAY_AVG" : "3.0",
				"LOCAL_SEASON_DAY_AVG" : "4.0",
				"LOCAL_YEAR_DAY_AVG" : "2.0",
				"LOCAL_DAY_AMOUNT" : "30",
				"LOAN_ALL_SCALE" : "0.3",
				"PROFIT_INCOME" : "1.35",
				"QIAN_AMOUNT" : "0.12",
				"TUO_AMOUNT" : "5",
				"FIVE_CLASS_STATUS" : "正常"
			},{
				"rownum" : "3",
				"ACCOUNT_NO" : '00822233',
				"ACCOUNT_NAME" : "张宪",
				"OPEN_ORG_NAME" : "华夏北京银行",
				"ACCOUNT_XC_NAME" : "梁瀚宇",
				"XC_NO" : "016907",
				"AT_SUB_ORG_NAME" : "华夏北京支行",
				"AT_ORG_NAME" : "华夏北京分行",
				"PRODUCT_CLASS" : "国债类产品",
				"DISTRIBUTE_DATE" : "2008-04-02",
				"END_DATE" : "2013-04-01",
				"LOAN_DATE_DATE" : "5年",
				"PROFIT_SCALE" : "0.2",
				"FLOWER_RATE" : "0.05",
				"LOCAL_MONTH_DAY_AVG" : "5.0",
				"LOCAL_SEASON_DAY_AVG" : "4.0",
				"LOCAL_YEAR_DAY_AVG" : "4.5",
				"LOCAL_DAY_AMOUNT" : "80",
				"LOAN_ALL_SCALE" : "0.8",
				"PROFIT_INCOME" : "9",
				"QIAN_AMOUNT" : "1",
				"TUO_AMOUNT" : "30",
				"FIVE_CLASS_STATUS" : "关注"
			},{
				"rownum" : "4",
				"ACCOUNT_NO" : '00822249',
				"ACCOUNT_NAME" : "佟彤彤",
				"OPEN_ORG_NAME" : "华夏北京银行",
				"ACCOUNT_XC_NAME" : "郝红婵",
				"XC_NO" : "016912",
				"AT_SUB_ORG_NAME" : "华夏北京支行",
				"AT_ORG_NAME" : "华夏北京分行",
				"PRODUCT_CLASS" : "国债类产品",
				"DISTRIBUTE_DATE" : "2010-04-02",
				"END_DATE" : "2013-04-01",
				"LOAN_DATE_DATE" : "3年",
				"PROFIT_SCALE" : "0.15",
				"FLOWER_RATE" : "0.05",
				"LOCAL_MONTH_DAY_AVG" : "5",
				"LOCAL_SEASON_DAY_AVG" : "4",
				"LOCAL_YEAR_DAY_AVG" : "4.5",
				"LOCAL_DAY_AMOUNT" : "50",
				"LOAN_ALL_SCALE" : "0.4",
				"PROFIT_INCOME" : "2.25",
				"QIAN_AMOUNT" : "1.5",
				"TUO_AMOUNT" : "20",
				"FIVE_CLASS_STATUS" : "次级"
			},{
				"rownum" : "5",
				"ACCOUNT_NO" : '00822322',
				"ACCOUNT_NAME" : "何玉冰",
				"OPEN_ORG_NAME" : "华夏北京银行",
				"ACCOUNT_XC_NAME" : "韩冰",
				"XC_NO" : "016912",
				"AT_SUB_ORG_NAME" : "华夏北京支行",
				"AT_ORG_NAME" : "华夏北京分行",
				"PRODUCT_CLASS" : "国债类产品",
				"DISTRIBUTE_DATE" : "2010-04-02",
				"END_DATE" : "2013-04-01",
				"LOAN_DATE_DATE" : "3年",
				"PROFIT_SCALE" : "0.8",
				"FLOWER_RATE" : "0.05",
				"LOCAL_MONTH_DAY_AVG" : "1",
				"LOCAL_SEASON_DAY_AVG" : "1.5",
				"LOCAL_YEAR_DAY_AVG" : "1.2",
				"LOCAL_DAY_AMOUNT" : "40",
				"LOAN_ALL_SCALE" : "0.8",
				"PROFIT_INCOME" : "0.96",
				"QIAN_AMOUNT" : "0.5",
				"TUO_AMOUNT" : "35",
				"FIVE_CLASS_STATUS" : "损失"
			}]
	};
	store4.loadData(memberData4);

	// 每页显示条数下拉选择框
	var pagesize_combo4 = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	store4.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo4.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo4.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo4.getValue()), store4
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo4.getValue())
					}
				});
	});
	// 分页工具栏
	var bbar4 = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo4.getValue()),
		store : store4,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo4 ]
	});

	// 表格实例
	var grid4 = new Ext.grid.GridPanel({
		title : '个人贷款账户明细列表',
		//loyout:'fit',
		width : document.body.scrollWidth,
		height: 360,
		frame : true,
		autoScroll : true,
		store : store4,
		stripeRows : true, // 斑马线
		cm : cm4, // 列模型
		tbar :  new Ext.Toolbar({
	        items  : ['数据日期：　　　　年　　月　　日','　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　币种：',
	                  '->','单位：万元']
	    }), // 表格工具栏
		bbar : bbar4,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});