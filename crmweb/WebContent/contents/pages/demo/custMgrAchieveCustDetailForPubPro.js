//合并单元格
var continentGroupRow21 = [ {
	header : '',
	colspan : 5,
	align : 'center'
	}, {
		header : '存款业务',
		colspan : 4,
		align : 'center'
	}, {
		header : '贷款业务',
		colspan : 3,
		align : 'center'
	}, {
		header : '票据贴现业务',
		colspan : 3,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '银行保证业务',
		colspan : 3,
		align : 'center'
	}, {
		header : '信贷证明业务',
		colspan : 3,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '国际结算业务',
		colspan : 3,
		align : 'center'
	}, {
		header : '国际融资业务',
		colspan : 3,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '集算快线',
		colspan : 3,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '国内支付结算业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '国际支付结算业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '代理业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '担保及承诺业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '交易类业务手续费收入',
		colspan : 2,
		align : 'center'
	}, {
		header : '托管业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '咨询顾问业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '电子银行业务手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '其他手续费收入',
		colspan : 3,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	}, {
		header : '预付类',
		colspan : 3,
		align : 'center'
	}, {
		header : '存货类',
		colspan : 3,
		align : 'center'
	}, {
		header : '应收类',
		colspan : 3,
		align : 'center'
	}, {
		header : '',
		colspan : 1,
		align : 'center'
	} ];

var group1 = new Ext.ux.grid.ColumnHeaderGroup( {
	rows : [ continentGroupRow21 ]
// continentGroupRow,
		});

var record1 = Ext.data.Record.create( [ {
			name : 'id',
			mapping : 'ID'
		}, {
			name : 'custName',
			mapping : 'CUST_NAME'
		}, {
			name : 'custMgrName',
			mapping : 'CUST_MGR_NAME'
		}, {
			name : 'xcNo',
			mapping : 'XC_NO'
		}, {
			name : 'atOrgName',
			mapping : 'AT_ORG_NAME'
		}, {
			name : 'UnitHuoSave',
			mapping : 'UNIT_HUO_SAVE'
		}, {
			name : 'UnitDingSave',
			mapping : 'UNIT_DING_SAVE'
		}, {
			name : 'AskedSave',
			mapping : 'ASKED_SAVE'
		}, {
			name : 'subTotal1',
			mapping : 'SUB_TOTAL1'
		}, {
			name : 'flowFinanLoan',
			mapping : 'FLOW_FINAN_LOAN'
		}, {
			name : 'baseCreaLoan',
			mapping : 'BASE_CREA_LOAN'
		}, {
			name : 'subTotal2',
			mapping : 'SUB_TOTAL2'
		}, {
			name : 'subTotal3',
			mapping : 'SUB_TOTAL3'
		}, {
			name : 'subTotal4',
			mapping : 'SUB_TOTAL4'
		}, {
			name : 'subTotal5',
			mapping : 'SUB_TOTAL5'
		}, {
			name : 'subTotal6',
			mapping : 'SUB_TOTAL6'
		}, {
			name : 'subTotal7',
			mapping : 'SUB_TOTAL7'
		}, {
			name : 'subTotal8',
			mapping : 'SUB_TOTAL8'
		}, {
			name : 'subTotal9',
			mapping : 'SUB_TOTAL9'
		}, {
			name : 'creditLoanFeeIncome',
			mapping : 'CREDIT_LOAN_FEE_INCOME'
		} ]);
// 定义列模型
var cm1 = new Ext.grid.ColumnModel( [ // rownum,
		{
			header : 'NO.',
			width : 50,
			align : 'center',
			dataIndex : 'id'
		},// ,hidden:true
				{
					header : '客户名称',
					width : 150,
					align : 'center',
					dataIndex : 'custName',
					sortable : true
				}, {
					header : '客户经理姓名',
					width : 150,
					align : 'center',
					dataIndex : 'custMgrName',
					sortable : true
				}, {
					header : '吸存代码',
					width : 150,
					align : 'center',
					dataIndex : 'xcNo',
					sortable : true
				}, {
					header : '所属机构',
					width : 150,
					align : 'center',
					dataIndex : 'atOrgName',
					sortable : true
				}, {
					header : '单位活期存款',
					width : 150,
					align : 'center',
					dataIndex : 'UnitHuoSave',
					sortable : true
				}, {
					header : '单位定期存款',
					width : 150,
					align : 'center',
					dataIndex : 'UnitDingSave',
					sortable : true
				}, {
					header : '通知存款',
					width : 150,
					align : 'center',
					dataIndex : 'AskedSave',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal1',
					sortable : true
				}, {
					header : '流动资金贷款',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '基本建设贷款',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '银行承兑汇票贷款',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '商业承兑汇票贴现（直贴）',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal3',
					sortable : true
				}, {
					header : '银行承兑汇票',
					width : 150,
					align : 'center',
					dataIndex : 'UnitHuoSave',
					sortable : true
				}, {
					header : '履约保函',
					width : 150,
					align : 'center',
					dataIndex : 'UnitDingSave',
					sortable : true
				}, {
					header : '投标保函',
					width : 150,
					align : 'center',
					dataIndex : 'AskedSave',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal1',
					sortable : true
				}, {
					header : '不载明前提条件的贷款承诺函',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '不载明附加条款的招投标银行信贷证明',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal4',
					sortable : true
				}, {
					header : '进口开证',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '进口代付',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '进口押汇',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '出口押汇',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal5',
					sortable : true
				}, {
					header : '企业网银',
					width : 150,
					align : 'center',
					dataIndex : 'UnitHuoSave',
					sortable : true
				}, {
					header : '集团公司',
					width : 150,
					align : 'center',
					dataIndex : 'UnitDingSave',
					sortable : true
				}, {
					header : '签约公司',
					width : 150,
					align : 'center',
					dataIndex : 'AskedSave',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal1',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal6',
					sortable : true
				}, {
					header : '单位结算手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '工本费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '进口信用证手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '出口信用证手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '单位委托贷款业务手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '单位理财业务手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '单位人民币保函手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '外汇非融资类保函手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '信贷业务交易手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'creditLoanFeeIncome',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal7',
					sortable : true
				}, {
					header : '托管手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '贷款委托管理手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '单位存款证明手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '咨询顾问手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : 'B2B网上支付业务手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '网上银行结算手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '邮寄发票业务手续费收入',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '自助渠道广告费收入',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal8',
					sortable : true
				}, {
					header : '未来提货权融资保兑仓模式',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '未来提货权仓储监管模式',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '核定货值货物质押融资',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '非核定货值货物质押融资',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '有追索权国内保理',
					width : 150,
					align : 'center',
					dataIndex : 'flowFinanLoan',
					sortable : true
				}, {
					header : '无追索权国内保理',
					width : 150,
					align : 'center',
					dataIndex : 'baseCreaLoan',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal2',
					sortable : true
				}, {
					header : '小计',
					width : 150,
					align : 'center',
					dataIndex : 'subTotal9',
					sortable : true
				} ]);

/**
 * 数据存储
 */
var store1 = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/mktModelManage.json'// custMgrGroupCount
		}),
	reader : new Ext.data.JsonReader( {
		// successProperty : 'success',
		// idProperty : 'ID',
		// messageProperty : 'message',
		// root : 'json.data',
		// totalProperty : 'json.count'
		totalProperty : 'num',// 记录总数
		root : 'rows'// Json中的列表数据根节点
	}, record1)
});

var memberData1 = {
	TOTALCOUNT : 3,
	rows : [ {
		"rownum" : "1",
		"ID" : "1",
		"CUST_NAME" : '赵兰',
		"CUST_MGR_NAME" : "周梦琪",
		"XC_NO" : "10005",
		"AT_ORG_NAME" : "公司业务部",
		"UNIT_HUO_SAVE" : "300",
		"UNIT_DING_SAVE" : "30",
		"ASKED_SAVE" : "5",
		"SUB_TOTAL1" : "335",
		"FLOW_FINAN_LOAN" : "500",
		"BASE_CREA_LOAN" : "300",
		"SUB_TOTAL2" : "800",
		"SUB_TOTAL3" : "1,935",
		"SUB_TOTAL4" : "1,135",
		"SUB_TOTAL5" : "1,600",
		"SUB_TOTAL6" : "335",
		"SUB_TOTAL7" : "5",
		"SUB_TOTAL8" : "1,650",
		"SUB_TOTAL9" : "240",
		"CREDIT_LOAN_FEE_INCOME":"5"
	}, {
		"rownum" : "2",
		"ID" : "2",
		"CUST_NAME" : '王金瓯',
		"CUST_MGR_NAME" : "梁瀚宇",
		"XC_NO" : "016907",
		"AT_ORG_NAME" : "华夏北京支行",
		"UNIT_HUO_SAVE" : "3,000",
		"UNIT_DING_SAVE" : "300",
		"ASKED_SAVE" : "50",
		"SUB_TOTAL1" : "3,350",
		"FLOW_FINAN_LOAN" : "800",
		"BASE_CREA_LOAN" : "500",
		"SUB_TOTAL2" : "1,300",
		"SUB_TOTAL3" : "2,935",
		"SUB_TOTAL4" : "1,635",
		"SUB_TOTAL5" : "2,600",
		"SUB_TOTAL6" : "3,350",
		"SUB_TOTAL7" : "30",
		"SUB_TOTAL8" : "10,430",
		"SUB_TOTAL9" : "3,900",
		"CREDIT_LOAN_FEE_INCOME":"30"
	}, {
		"rownum" : "3",
		"ID" : "3",
		"CUST_NAME" : '张善俊',
		"CUST_MGR_NAME" : "郝红婵",
		"XC_NO" : "016912",
		"AT_ORG_NAME" : "华夏上地支行",
		"UNIT_HUO_SAVE" : "300",
		"UNIT_DING_SAVE" : "30",
		"ASKED_SAVE" : "5",
		"SUB_TOTAL1" : "335",
		"FLOW_FINAN_LOAN" : "500",
		"BASE_CREA_LOAN" : "300",
		"SUB_TOTAL2" : "800",
		"SUB_TOTAL3" : "1,935",
		"SUB_TOTAL4" : "1,135",
		"SUB_TOTAL5" : "1,600",
		"SUB_TOTAL6" : "335",
		"SUB_TOTAL7" : "5",
		"SUB_TOTAL8" : "1,650",
		"SUB_TOTAL9" : "240",
		"CREDIT_LOAN_FEE_INCOME":"5"
	}, {
		"rownum" : "4",
		"ID" : "4",
		"CUST_NAME" : '李显鸥',
		"CUST_MGR_NAME" : "韩冰",
		"XC_NO" : "016913",
		"AT_ORG_NAME" : "华夏天津分行",
		"UNIT_HUO_SAVE" : "3,000",
		"UNIT_DING_SAVE" : "300",
		"ASKED_SAVE" : "50",
		"SUB_TOTAL1" : "3,350",
		"FLOW_FINAN_LOAN" : "800",
		"BASE_CREA_LOAN" : "500",
		"SUB_TOTAL2" : "1,300",
		"SUB_TOTAL3" : "2,935",
		"SUB_TOTAL4" : "1,635",
		"SUB_TOTAL5" : "2,600",
		"SUB_TOTAL6" : "3,350",
		"SUB_TOTAL7" : "30",
		"SUB_TOTAL8" : "10,430",
		"SUB_TOTAL9" : "3,900",
		"CREDIT_LOAN_FEE_INCOME":"30"
	}, {
		"rownum" : "5",
		"ID" : "5",
		"CUST_NAME" : '萧红',
		"CUST_MGR_NAME" : "曹乐",
		"XC_NO" : "016908",
		"AT_ORG_NAME" : "华夏北京分行",
		"UNIT_HUO_SAVE" : "30",
		"UNIT_DING_SAVE" : "30",
		"ASKED_SAVE" : "5",
		"SUB_TOTAL1" : "65",
		"FLOW_FINAN_LOAN" : "100",
		"BASE_CREA_LOAN" : "100",
		"SUB_TOTAL2" : "200",
		"SUB_TOTAL3" : "465",
		"SUB_TOTAL4" : "265",
		"SUB_TOTAL5" : "400",
		"SUB_TOTAL6" : "65",
		"SUB_TOTAL7" : "10",
		"SUB_TOTAL8" : "1,700",
		"SUB_TOTAL9" : "600",
		"CREDIT_LOAN_FEE_INCOME":"10"
	} ]
};
store1.loadData(memberData1);

// 每页显示条数下拉选择框
var pagesize_combo1 = new Ext.form.ComboBox( {
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore( {
		fields : [ 'value', 'text' ],
		data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
				[ 1000, '1000条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	editable : false,
	width : 85
});

// 默认加载数据
store1.load( {
	params : {
		start : 0,
		limit : parseInt(pagesize_combo1.getValue())
	}
});

// 改变每页显示条数reload数据
pagesize_combo1.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo1.getValue()), store1.reload( {
		params : {
			start : 0,
			limit : parseInt(pagesize_combo1.getValue())
		}
	});
});
// 分页工具栏
var bbar1 = new Ext.PagingToolbar( {
	pageSize : parseInt(pagesize_combo1.getValue()),
	store : store1,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', pagesize_combo1 ]
});

// 表格实例
var grid1 = new Ext.grid.GridPanel( {
	title : '对公客户产品统计明细列表',
	// loyout:'fit',
	width : document.body.scrollWidth,
	height : 360,
	frame : true,
	autoScroll : true,
	store : store1,
	stripeRows : true, // 斑马线
	cm : cm1, // 列模型
	tbar :  new Ext.Toolbar({
        items  : ['数据日期：　　　　年　　月　　日','　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　币种：',
                  '->','单位：万元']
    }), // 表格工具栏
	bbar : bbar1,// 分页工具栏
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	},
	plugins : group1
});