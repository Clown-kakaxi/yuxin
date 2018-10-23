/**
 * 客户信息管理页面
 * 包含：新增、修改、删除、转信贷临时户、转信贷正式户
 * @author sunjing5 2017-05-01
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'//bcrm树控件
	,'/contents/resource/ext3/ux/Ext.ux.Notification.js'//右下角 提示窗口控件
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	// 用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.BusinessType.js'		// 行业树放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.Address.js'  // 地址放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.EconomicType.js'//经济类型放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.Group.js'// 集团客户
	,'/contents/pages/wlj/customerManager/queryAllCustomer/newAllLookup.js' // 所有数据字典定义
	,'/contents/pages/wlj/customerManager/queryAllCustomer/qzComInfoThree.js'
	,'/contents/pages/wlj/customerManager/queryAllCustomer/qzComInfoFive.js'
	,'/contents/pages/wlj/customerManager/queryAllCustomer/lnBlankOrRead.js'// 修改时必输与只读控制
	,'/contents/pages/wlj/customerManager/queryAllCustomer/saveHandler.js'// 各个保存的处理
	,'/contents/pages/wlj/customerManager/queryAllCustomer/updateHisCust.js'
//	,'/contents/pages/wlj/customerManager/queryAllCustomer/qzComUpdateHis.js'//修改历史
	,'/contents/pages/common/Com.yucheng.crm.common.EchainNextNodeUserNew.js'
]);
// 初始化标签中的Ext:Qtip属性。
Ext.QuickTips.init();
// 自定义全局变量：
var custId = '';
var custName = '';
var tempValue = '';
var ifhidden = false;// 是否保存后要收起面板
var codeflag = false;// 是否有重复中征码
// 查询面板
var needCondition = true;
var needGrid = true;
// 查询结果列表
WLJUTIL.suspendViews = true;
/**
 * 启用1-5屏的保存按钮
 */
var enableSaveBtn = function() {
	Ext.getCmp('first_save').enable();
	Ext.getCmp('second_save').enable();
	Ext.getCmp('third_save').enable();
	Ext.getCmp('forth_save').enable();
	Ext.getCmp('fifth_save').enable();
};

// /////////////////////////////////下拉树/////////////////////////////////////////////////////
// 机构树加载条件
var condition = {
	searchType	: 'SUBTREE' // 查询子机构
};

// 加载机构树
var treeLoaders = [{
	key	         : 'ORGTREELOADER',
	url	         : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr	 : 'SUPERUNITID',
	locateAttr	 : 'UNITID',
	jsonRoot	 : 'json.data',
	rootValue	 : JsContext._orgId,
	textField	 : 'UNITNAME',
	idProperties	: 'UNITID'
}, {
	key	         : 'BLINETREELOADER',
	url	         : basepath + '/businessLineTree.json',
	parentAttr	 : 'PARENT_ID',
	locateAttr	 : 'BL_ID',
	jsonRoot	 : 'json.data',
	rootValue	 : '0',
	textField	 : 'BL_NAME',
	idProperties	: 'BL_ID'
}, {
	key	         : 'BTYPETREELOADER',
	url	         : basepath + '/btypeTree.json',
	parentAttr	 : 'PARENT_CODE',
	locateAttr	 : 'F_CODE',
	jsonRoot	 : 'json.data',
	rootValue	 : '-1',
	textField	 : 'F_VALUE',
	idProperties	: 'F_CODE'
}];

// 树配置
var treeCfgs = [{
	key	       : 'ORGTREE',
	loaderKey	: 'ORGTREELOADER',
	autoScroll	: true,
	rootCfg	   : {
		expanded	: true,
		id		   : JsContext._orgId,
		text		: JsContext._unitname,
		autoScroll	: true,
		children	: [],
		UNITID		: JsContext._orgId,
		UNITNAME	: JsContext._unitname
	}
}, {
	key	       : 'BLTREE',
	loaderKey	: 'BLINETREELOADER',
	autoScroll	: true,
	rootCfg	   : {
		expanded	: true,
		id		   : '0',
		text		: '归属业务条线',
		autoScroll	: true,
		children	: []
	}
}, {
	key	       : 'BTTREE',
	loaderKey	: 'BTYPETREELOADER',
	autoScroll	: true,
	rootCfg	   : {
		expanded	: true,
		id		   : '-1',
		text		: '所有行业',
		autoScroll	: true,
		children	: []
	}
}];

// ///////////////////////////////////////////////////////////初始化查询页面//////////////////////////////////////////////
var afterinit = function() {
};
var url = basepath + '/customerManagerNew.json';

var fields=[    
	{name:'QUERY_AUTH',id:'QUERY_AUTH',text:'查询权限',resutlWidth:80,translateType:'QUERY_AUTH',searchField:true,gridField:false},
    {name:'CUST_TYPE',text:'客户类型',gridField:true,searchField:true,resutlWidth:50,translateType:'XD000080',readOnly : true,cls :'x-readOnly',value : 1,hidden:true},
    {name:'CUST_STAT',text:'客户状态',resutlWidth:50,translateType:'XD000081',searchField:true},
    {name:'POTENTIAL_FLAG',text:'是否潜在客户',resutlWidth:75,gridField:true,searchField:true,translateType:'XD000084'},
    {name:'CUST_ID',text:'客户编号',gridField:true,searchField:true,resutlWidth:90},
    {name:'CORE_NO',text:'核心客户号',gridField:true,searchField:true,resutlWidth:90},
    {name:'LOAN_CUST_ID',text:'信贷客户号',gridField:true,searchField:true,resutlWidth:100},
    {name:'LOAN_CUST_STAT',text:'信贷客户状态',gridField:true,searchField:true,translateType:'XD000075',resutlWidth:80},
    {name:'CUST_NAME',text:'客户名称',gridField:true,searchField:true},
    {name:'IDENT_TYPE',text:'证件类型',gridField:true,searchField:true,translateType:'XD000040',resutlWidth:110},
    {name:'IDENT_NO',text:'证件号码',gridField:true,searchField:true,resutlWidth:110},
    {name:'ORG_NAME',text:'归属机构',xtype:'wcombotree',innerTree:'ORGTREE',resutlWidth:110,searchField: true, showField:'text',hideField:'UNITID',editable:false,allowBlank:false},
    {name:'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:80,singleSelect: false,searchField: true},
    {name:'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:85,singleSelect: false,searchField: true},
    {name:'FXQ_RISK_LEVEL',text:'反洗钱风险等级',resutlWidth:80,translateType:'FXQ_RISK_LEVEL',gridField:true,searchField: true}
];

/**
 * 保存时，有些值必须置空
 */
var setnullValue=function(){
	var nullflag = false; // 提示清空标志
	var marflag = Ext.getCmp('p2listedId').getValue(); // 上市公司标志
	var stock = qzComLists.form.findField('STOCK_CODE').getValue();//股票代码
	var market = qzComLists.form.findField('MARKET_PLACE').getValue();//上市地
	var speflag = Ext.getCmp('p2spId').getValue(); // 特种经营标识
	var comorg = qzComLists.form.findField('COM_SP_ORG').getValue();//特种经营颁发机关
	var comdate = qzComLists.form.findField('COM_SP_REG_DATE').getValue();//特种经营登记日期
	var comsitu = qzComLists.form.findField('COM_SP_SITU').getValue();
	var comcode = qzComLists.form.findField('COM_SP_CODE').getValue();
	var comend = qzComLists.form.findField('COM_SP_END_DATE').getValue();
	var arflag = Ext.getCmp('p2arflagId').getValue(); // AR客户类型与AR客户标志
	var artype = qzComLists.form.findField('AR_CUST_TYPE').getValue();
	var scirange = Ext.getCmp('p2sciencerange').getValue();// 科技型企业范围
	// 上市公司标志
	if(marflag == '1') {
		qzComLists.form.findField('STOCK_CODE').setVisible(true);
		qzComLists.form.findField('STOCK_CODE').allowBlank = false;
		qzComLists.form.findField('STOCK_CODE').fieldLabel = '<font color="red">*</font>股票代码:';
		qzComLists.form.findField('MARKET_PLACE').setVisible(true);
		qzComLists.form.findField('MARKET_PLACE').allowBlank = false;
		qzComLists.form.findField('MARKET_PLACE').fieldLabel = '<font color="red">*</font>上市地:';

	} else {
		if(stock != '' || market != '') {
			nullflag = true;
		}
		qzComLists.form.findField('STOCK_CODE').setValue('');
		qzComLists.form.findField('STOCK_CODE').setVisible(false);
		qzComLists.form.findField('STOCK_CODE').allowBlank = true;
		qzComLists.form.findField('STOCK_CODE').fieldLabel = '股票代码:';
		qzComLists.form.findField('MARKET_PLACE').setValue('');
		qzComLists.form.findField('MARKET_PLACE').setVisible(false);
		qzComLists.form.findField('MARKET_PLACE').allowBlank = true;
		qzComLists.form.findField('MARKET_PLACE').fieldLabel = '上市地:';

	};
	// 特种经营标识
	if(speflag == '1') {
		qzComLists.form.findField('COM_SP_ORG').setVisible(true);
		qzComLists.form.findField('COM_SP_REG_DATE').setVisible(true);
		qzComLists.form.findField('COM_SP_SITU').setVisible(true);
		qzComLists.form.findField('COM_SP_CODE').setVisible(true);
		qzComLists.form.findField('COM_SP_END_DATE').setVisible(true);
		qzComLists.form.findField('COM_SP_CODE').allowBlank = false;
		qzComLists.form.findField('COM_SP_CODE').fieldLabel = '<font color="red">*</font>特种经营许可证编号';
		qzComLists.form.findField('COM_SP_END_DATE').allowBlank = false;
		qzComLists.form.findField('COM_SP_END_DATE').fieldLabel = '<font color="red">*</font>特种经营到期日期';
	} else {
		if(comorg != '' || comdate != '' || comsitu != '' || comcode != '' || comend != '') {
			nullflag = true;
		}
		qzComLists.form.findField('COM_SP_ORG').setValue('');
		qzComLists.form.findField('COM_SP_REG_DATE').setValue('');
		qzComLists.form.findField('COM_SP_SITU').setValue('');
		qzComLists.form.findField('COM_SP_CODE').setValue('');
		qzComLists.form.findField('COM_SP_END_DATE').setValue('');
		qzComLists.form.findField('COM_SP_ORG').setVisible(false);
		qzComLists.form.findField('COM_SP_REG_DATE').setVisible(false);
		qzComLists.form.findField('COM_SP_SITU').setVisible(false);
		qzComLists.form.findField('COM_SP_CODE').setVisible(false);
		qzComLists.form.findField('COM_SP_END_DATE').setVisible(false);
		qzComLists.form.findField('COM_SP_CODE').allowBlank = true;
		qzComLists.form.findField('COM_SP_CODE').fieldLabel = '特种经营许可证编号';
		qzComLists.form.findField('COM_SP_END_DATE').allowBlank = true;
		qzComLists.form.findField('COM_SP_END_DATE').fieldLabel = '特种经营到期日期';
	};
	// AR客户类型与AR客户标志
	if(arflag == '1') {
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
		qzComLists.form.findField('AR_CUST_TYPE').allowBlank = false;
		qzComLists.form.findField('AR_CUST_TYPE').fieldLabel = '<font color="red">*</font>AR客户类型(CSPS)';
	} else {
		if(artype != '') {
			nullflag = true;
		}
		qzComLists.form.findField('AR_CUST_TYPE').setValue('');
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(false);
		qzComLists.form.findField('AR_CUST_TYPE').allowBlank = true;
		qzComLists.form.findField('AR_CUST_TYPE').fieldLabel = 'AR客户类型';
	};
	return nullflag;
};
/**
 * 自定义工具条上按钮 新增跳出中间窗口
 */
var tbar = [{
	id	    : 'add',
	text	: '新增',
	hidden	: JsContext.checkGrant('latent_add'),
	handler	: function() {
		qzComStore.removeAll();
		var _store = findLookupByType('OPENIDENT');
		_store.load();
		qzCombaseInfo.form.findField('IDENT_TYPE').bindStore(_store);// 开户证件类型
		var _store4 = findLookupByType('REGISTERCUR');
		_store4.load();
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store4);// 注册资金币种
		qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store4);// 年销售币别
		var _store5 = findLookupByType('COMTYPE');
		_store5.load();
		qzCombaseInfo.form.findField('ORG_CUST_TYPE').bindStore(_store5);// 客户类型
		// 第三页放开按钮新增和移除按钮
		setDetail(false);
		// 去除保存按钮的屏蔽
		setDetailSave(false);
		// 只读项全部清除
		// setNotDisabledFun(qzCombaseInfo);
		// setNotDisabledFun(qzComLists);
		// setNotDisabledFun(qzComOther1Info);
		/**
		 * 等待客户操作类型和客户类型两个下拉框数据集加载数据完成 完成后再进行默认值的设置
		 */
		function check() {
			if(custSignStore.getCount() == 0 || custTypeStore.getCount() == 0) {
				setTimeout(check, 100);
			} else {
				Ext.Msg.hide();
				// 新增时屏蔽修改历史
				Ext.getCmp("his_1").setVisible(false);
				Ext.getCmp("his_2").setVisible(false);
				Ext.getCmp("his_3").setVisible(false);
				Ext.getCmp("his_4").setVisible(false);
				Ext.getCmp("sign").setValue('1');// 客户操作类型为潜在客户
				Ext.getCmp("type").setValue('1');// 客户类型为企业
				qzCombaseInfo.form.findField('CUST_ID').setVisible(false);
				qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
				// qzComLists.form.findField('STOCK_CODE').setVisible(false);
				// qzComLists.form.findField('MARKET_PLACE').setVisible(false);
				// qzComLists.form.findField('COM_SP_ORG').setVisible(false);
				// qzComLists.form.findField('COM_SP_REG_DATE').setVisible(false);
				// qzComLists.form.findField('COM_SP_SITU').setVisible(false);
				// qzComLists.form.findField('COM_SP_CODE').setVisible(false);
				// qzComLists.form.findField('COM_SP_END_DATE').setVisible(false);
				// qzComLists.form.findField('SCIENTIFIC_TYPE').setVisible(false);//4.企业类型(大中小微)
				// qzComLists.form.findField('SCIENTIFIC_TERM').setVisible(false);
				// 刷新页面
				// 控制第二页的码值
				if(Ext.getCmp("type").getValue() == '1') {
					var _store3 = findLookupByType('CUSTTYPEALL');
					_store3.load();
					qzComLists.form.findField('ORG_SUB_TYPE').bindStore(_store3);
				}
				window.resetjs('');// 清空所有页数据
				// modified by liuyx 新增的时候只需清空数据即可，无需重新加载空数据
				// window.loadAllData();// 刷新数据
				AddWindow.show();
			}
		}
		Ext.Msg.wait("处理");
		setTimeout(check, 100);
	}
},{
	id	    : 'update',
	text	: '修改',
	hidden	: JsContext.checkGrant('latent_update'),
	handler	: function() {
		// setNotDisabledFun(qzCombaseInfo);
		// setNotDisabledFun(qzComLists);
		// setNotDisabledFun(qzComOther1Info);
		// 刷新页面
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
		if(getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请选择一条数据进行操作！');
			return false;
		} else {
			// 只读项全部清除
			// setNotDisabledFun(qzCombaseInfo);
			// setNotDisabledFun(qzComLists);
			// setNotDisabledFun(qzComOther1Info);
			var records = getAllSelects(); // 获取选择记录
			var custids = records[0].data.CUST_ID;// 获取id
			custId = custids;// add by liuming 20170825
			custName = records[0].data.CUST_NAME;// add by liuming 20170825;
			var potentialFlag = records[0].data.POTENTIAL_FLAG;// 获取潜在客户标志1:潜在客户
			var loanCustStat = records[0].data.LOAN_CUST_STAT;// 获取信贷客户状态
			var custType = records[0].data.CUST_TYPE;// 获取客户类型：1，企业，2：个人
			var belongRm = records[0].json.MGR_ID;// 归属客户经理
			if(belongRm != JsContext._userId) {
				Ext.Msg.alert('提示信息', '请选择归属于本人的客户！');
				return false;
			} else {
				window.readyCount = 0;// 初始化异步加载完成计数器
				// 第三页放开按钮新增和移除按钮
				setXiugai(potentialFlag);
				// 去除保存按钮的屏蔽
				setDetailSave(false);
				var _store4 = findLookupByType('REGISTERCUR');
				_store4.load();
				qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store4);// 注册资金币种
				qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store4);// 年销售币别

				var _store5 = findLookupByType('COMTYPE');
				_store5.load();
				qzCombaseInfo.form.findField('ORG_CUST_TYPE').bindStore(_store5);// 客户类型
				// 控制第二页的码值
				if(custType == '1') {
					var _store3 = findLookupByType('CUSTTYPEALL');
					_store3.load();
					qzComLists.form.findField('ORG_SUB_TYPE').bindStore(_store3);
				}
				// 清空数据
				window.resetjs(potentialFlag);
				qzCombaseInfo.form.findField('CUST_ID').setVisible(true);
				qzCombaseInfo.form.findField('CUST_ID').setReadOnly(true);
				qzCombaseInfo.form.findField('CUST_ID').addClass('x-readOnly');
				// 刷新页面记忆加载页面
				searchFirstComFn(custids);// 第一页
				SearchqzComListsFn(custids);// 第二页
				window.queryTempData(custids);// 第二页发生日期表格、第三页表格数据加载
				SearchqzComOther1Fn(custids);// 第四页
				SearchqzComOther2Fn(custids);// 第五页
				function check() {
					if(window.readyCount == 9) {
						Ext.MessageBox.hide();
					} else {
						setTimeout(check, 200);
					}
				}
				Ext.Msg.wait("处理");
				setTimeout(check, 200);
				/**
				 * 有些隐藏项需要展示： 1、上市地和股票代码需显示 2、特种经营表示 3、AR客户类型与AR客户标志 4、科技型企业
				 */
				// qzComLists.form.findField('STOCK_CODE').setVisible(true);
				// qzComLists.form.findField('MARKET_PLACE').setVisible(true);
				// qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
				// qzComLists.form.findField('COM_SP_ORG').setVisible(true);
				// qzComLists.form.findField('COM_SP_REG_DATE').setVisible(true);
				// qzComLists.form.findField('COM_SP_SITU').setVisible(true);
				// qzComLists.form.findField('COM_SP_CODE').setVisible(true);
				// qzComLists.form.findField('COM_SP_END_DATE').setVisible(true);
				qzComLists.form.findField('SCIENTIFIC_TYPE').setVisible(true);// 4.企业类型(大中小微)
				qzComLists.form.findField('SCIENTIFIC_TERM').setVisible(true);

				if(custids != '' && potentialFlag == '0') { // 既有客户
					opNoRead();
					opRead(); // 既有客户原必输项，修改时只读
					qzBlank();
					Ext.getCmp("his_1").setVisible(true);
					Ext.getCmp("his_2").setVisible(true);
					Ext.getCmp("his_3").setVisible(true);
					Ext.getCmp("his_4").setVisible(true);
					if(loanCustStat == '01') {// 临时户
						lnLsBlank(); // 临时户必输项控制
						AddorUpdate('修改既有客户-信贷临时户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改既有客户-信贷临时户';
					} else if(loanCustStat == '21') {// 准正式客户
						lnZzsBlank(); // 准正式客户必输项控制
						// add by liuming 20170821
						gyzzsReadAndNoRead();
						AddorUpdate('修改既有客户-信贷准正式户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改既有客户-信贷准正式户';
					} else if(loanCustStat == '20') {// 正式客户
						lnZshBlank(); // 正式客户必输项控制
						AddorUpdate('修改既有客户-信贷正式户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改既有客户-信贷正式户';
					} else {
						AddorUpdate('修改既有客户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改既有客户';
					}
					opBlank();
				}
				if(custids != '' && potentialFlag == '1') { // 潜在客户
					opNoRead(); // 潜在客户非只读项控制
					qzBlank();
					Ext.getCmp("his_1").setVisible(false);
					Ext.getCmp("his_2").setVisible(false);
					Ext.getCmp("his_3").setVisible(false);
					Ext.getCmp("his_4").setVisible(false);
					if(loanCustStat == '01') {// 临时户
						lnLsBlank(); // 临时户必输项
						qzlsReadAndNoRead();// 临时户只读项控制
						AddorUpdate('修改潜在客户-信贷临时户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改潜在客户-信贷临时户';
					} else if(loanCustStat == '21') {// 准正式客户
						lnZzsBlank(); // 准正式户必输项
						qzzzsReadAndNoRead();// 准正式户只读项控制
						AddorUpdate('修改潜在客户-信贷准正式户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改潜在客户-信贷准正式户';
					} else {
						AddorUpdate('修改潜在客户', false, custType);
						document.getElementById('myview').firstChild.innerHTML = '修改潜在客户';
					}
				}
			}
		}
	}	

}, {
	id	    : 'changeToLs',
	text	: '转信贷临时户',
	hidden	: JsContext.checkGrant('latent_ls'),
	handler	: function() {
		Ext.getCmp("his_1").setVisible(false);
		Ext.getCmp("his_2").setVisible(false);
		Ext.getCmp("his_3").setVisible(false);
		Ext.getCmp("his_4").setVisible(false);
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
		if(getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请选择一条数据进行操作！');
			return false;
		} else {
			// 去除保存按钮的屏蔽
			setDetailSave(false);
			// 只读项全部清除
			// setNotDisabledFun(qzCombaseInfo);
			// setNotDisabledFun(qzComLists);
			// setNotDisabledFun(qzComOther1Info);
			var _store4 = findLookupByType('REGISTERCUR');
			_store4.load();
			qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store4);// 注册资金币种
			qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store4);// 年销售币别

			var _store5 = findLookupByType('COMTYPE');
			_store5.load();
			qzCombaseInfo.form.findField('ORG_CUST_TYPE').bindStore(_store5);// 客户类型

			var records = getAllSelects(); // 获取选择记录
			var custids = records[0].data.CUST_ID;// 获取id
			custName = records[0].data.CUST_NAME;// 获取name
			var identno = records[0].data.IDENT_NO;// 获取证件编号
			var identtype = records[0].data.IDENT_TYPE;// 获取证件类型
			var potentialFlag = records[0].data.POTENTIAL_FLAG;// 获取潜在客户标志
			var loanCustStat = records[0].data.LOAN_CUST_STAT;// 获取信贷客户状态
			var custType = records[0].data.CUST_TYPE;// 获取客户类型：1，企业，2：个人
			var belongRm = records[0].json.MGR_ID;// 归属客户经理
			if(belongRm != JsContext._userId) {
				Ext.Msg.alert('提示信息', '请选择归属于本人的客户！');
				return false;
			}
			if(custids != '' && loanCustStat == '01') {// 临时户
				Ext.Msg.alert('提示信息', '该客户已是临时户，不得再转为临时户！');
				return false;
			}
			if(custids != '' && loanCustStat == '21') {// 准正式户
				Ext.Msg.alert('提示信息', '该客户已是准正式户，不得再转为临时户！');
				return false;
			}
			if(custids != '' && loanCustStat == '20') {// 正式户
				Ext.Msg.alert('提示信息', '该客户已是正式户，不得再转为临时户！');
				return false;
			} else {
				window.readyCount = 0;
				//根据客户号加载各个页的数据
				searchFirstComFn(custids);
				SearchqzComListsFn(custids);
				window.queryTempData(custids);
				SearchqzComOther1Fn(custids);
				SearchqzComOther2Fn(custids);
				// 校验临时户必输项
				lnLsBlank();// 临时户必输项
				opNoRead();
				// 第三页放开按钮新增和移除按钮
				setXiugai(potentialFlag);
				qzComInfo.setActiveTab(0);
				Ext.getCmp('myview').title = '既有客户转临时户';
				var viewtitle = Ext.getCmp('myview').title;
				function check() {
					if(window.readyCount == 9) {
						Ext.MessageBox.hide();
						// 校验必输项
						if(!(qzCombaseInfo.getForm().isValid() && qzComLists.getForm().isValid())) {
							Ext.MessageBox.confirm('提示信息', '尚未完成临时户必输项，是否补录信息？', function(btn) {
								if(btn == 'yes') {
									lnLsBlank();
									if(custids != '' && potentialFlag == '0') {// 既有客户
										opBlank();
										opRead();
										qzCombaseInfo.form.findField('LOAN_CUST_STAT').setValue('01');// 临时户
										AddorUpdate("既有客户转临时户", false);
										document.getElementById('myview').firstChild.innerHTML = '既有客户转临时户';
									} else {
										opNoRead();
										qzCombaseInfo.form.findField('LOAN_CUST_STAT').setValue('01');// 临时户
										AddorUpdate("潜在客户转临时户", false);
										document.getElementById('myview').firstChild.innerHTML = '潜在客户转临时户';
									}
								}
							}, this);
						} else {
							Ext.Msg.alert('提示信息', '该客户满足临时户要求！');
							qzCombaseInfo.form.findField('LOAN_CUST_STAT').setValue('01');// 临时户
							if(potentialFlag == '0') {// 既有客户需走流程审批
								Ext.getCmp('myview').title = '既有客户转临时户';
								secondAddSave(custids, custName, identno, identtype, '既有客户转临时户');
							} else {// 潜在客户无需审批
								Ext.getCmp('myview').title = '潜在客户转临时户';
								secondAddSave(custids, custName, identno, identtype, '潜在客户转临时户');
							}
							// return false;
						}
						// 如果已输完
						// 调信贷接口，更改状态，保存信贷客户号
					} else {
						setTimeout(check, 500);
					}
				}
				Ext.Msg.wait("处理");
				setTimeout(check, 500);
				// setTimeout(function () {},1500);
			}
		}
	}
}, {
	id	    : 'changeToZs',
	text	: '转信贷正式户',
	hidden	: JsContext.checkGrant('latent_zs'),
	handler	: function() {
		Ext.getCmp("his_1").setVisible(false);
		Ext.getCmp("his_2").setVisible(false);
		Ext.getCmp("his_3").setVisible(false);
		Ext.getCmp("his_4").setVisible(false);
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
		if(getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请选择一条数据进行操作！');
			return false;
		} else {
			window.readyCount = 0;
			// 去除保存按钮的屏蔽
			setDetailSave(false);
			// 只读项全部清除
			// setNotDisabledFun(qzCombaseInfo);
			// setNotDisabledFun(qzComLists);
			// setNotDisabledFun(qzComOther1Info);
			var _store4 = findLookupByType('REGISTERCUR');
			_store4.load();
			qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store4);// 注册资金币种
			qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store4);// 年销售币别

			var _store5 = findLookupByType('COMTYPE');
			_store5.load();
			qzCombaseInfo.form.findField('ORG_CUST_TYPE').bindStore(_store5);// 客户类型

			var records = getAllSelects(); // 获取选择记录
			var custids = records[0].data.CUST_ID;// 获取id
			custName = records[0].data.CUST_NAME;// 获取name
			var identno = records[0].data.IDENT_NO;// 获取证件编号
			var identtype = records[0].data.IDENT_TYPE;// 获取证件类型
			var potentialFlag = records[0].data.POTENTIAL_FLAG;// 获取潜在客户标志
			var loanCustStat = records[0].data.LOAN_CUST_STAT;// 获取信贷客户状态
			var custType = records[0].data.CUST_TYPE;// 获取客户类型：1，企业，2：个人
			var loanCustId = records[0].data.LOAN_CUST_ID;// 获取信贷客户号
			var coreNo = records[0].data.CORE_NO;// 核心客户号
			var belongRm = records[0].json.MGR_ID;// 归属客户经理
			if(belongRm != JsContext._userId) {
				Ext.Msg.alert('提示信息', '请选择归属于本人的客户！');
				return false;
			}
			// if(custids!=''&&loanCustId==''&&loanCustStat!='21'){//非信贷客户
			// Ext.Msg.alert('提示信息', '该客户不是准正式客户，不得转为正式户！');
			// return false;
			// }
			if(custids != '' && loanCustStat != '21') {// 暂时用这个
				Ext.Msg.alert('提示信息', '该客户不是准正式客户，不得转为正式户！');
				return false;
			}
			if(custids != '' && loanCustStat == '21' && coreNo == '') {// 非既有客户
				Ext.Msg.alert('提示信息', '该客户为非既有客户，不得转为正式户！');
				return false;
			}
			if(custids != '' && loanCustStat == '21' && coreNo != '') {// 既有客户
				// 刷新页面记忆加载页面,根据客户号加载所有页数据
				searchFirstComFn(custids);
				SearchqzComListsFn(custids);
				window.queryTempData(custids);
				SearchqzComOther1Fn(custids);
				SearchqzComOther2Fn(custids);
				lnZshBlank();// 正式户必输项
				// 第三页放开按钮新增和移除按钮
				setXiugai(potentialFlag);
				opNoRead();
				qzComInfo.setActiveTab(0);
				Ext.getCmp('myview').title = '既有客户-信贷准正式户转正式户';
				var viewtitle = Ext.getCmp('myview').title;
				// 校验正式户必输项
				// 检查是否所有页的数据已经加载完成
				function check() {
					if(window.readyCount == 9) {
						if(!(qzCombaseInfo.getForm().isValid() && qzComLists.getForm().isValid())) {
							Ext.MessageBox.confirm('提示信息', '尚未完成正式户必输项，是否补录信息？', function(btn) {
								if(btn == 'yes') {
									opRead();// 既有客户只读项
									qzCombaseInfo.form.findField('POTENTIAL_FLAG').setValue('0');// 既有客户
									qzCombaseInfo.form.findField('LOAN_CUST_STAT').setValue('20');// 正式户
									AddorUpdate("既有客户-信贷准正式户转正式户", false);
									document.getElementById('myview').firstChild.innerHTML = '既有客户-信贷准正式户转正式户';
								}
							}, this);
						} else {
							Ext.Msg.alert('提示信息', '该客户满足正式户要求！');
							qzCombaseInfo.form.findField('LOAN_CUST_STAT').setValue('20');// 正式户
							if(potentialFlag == '0') {// 既有客户需走流程审批
								Ext.getCmp('myview').title = '既有客户-信贷准正式户转正式户';
								secondAddSave(custids, custName, identno, identtype, '既有客户-信贷准正式户转正式户');
							}
						}
					} else {
						setTimeout(check, 500);
					}
				}
				Ext.MessageBox.show({
					msg			: '正在处理，请稍后......',
					title		: '系统消息',
					width		: 300,
					wait		: true,
					progress	: true,
					closable	: true,
					waitConfig	: {
						interval	: 200
					},
					icon		: Ext.Msg.INFO
				});
				setTimeout(check, 500);
				// 如果已输完
				// 调信贷接口，更改状态
			}
		}
	}
}, {// 按钮：删除
	id	    : 'delete',
	text	: '删除',
	hidden	: JsContext.checkGrant('latent_delete'),
	handler	: function() {
		// 刷新页面
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
		if(getSelectedData() == false || getAllSelects().length < 1) {
			Ext.Msg.alert('提示信息', '请至少选择一条数据进行操作！');
			return false;
		} else {
			var records = getAllSelects(); // 获取选择记录
			var selectLength = getAllSelects().length; // 获取选择记录数
			var selectRe = '';// 选中记录
			var selectedCustId = '';// 获取客户编号
			var potentialFlag = '';// 获取潜在客户标志1:潜在客户
			var idsStr = '';// 将编号拼接成字符串
			var loanCustStat = '';
			// var custids = records[0].data.CUST_ID;// 获取id
			// var potentialFlag = records[0].data.POTENTIAL_FLAG;// 获取潜在客户标志1:潜在客户
			// var loanCustStat=records[0].data.LOAN_CUST_STAT;// 获取信贷客户状态
			// var custType=records[0].data.CUST_TYPE;// 获取客户类型：1，企业，2：个人
			for(var i = 0;i < selectLength;i++) {
				selectRe = records[i];
				selectedCustId = selectRe.data.CUST_ID;// 获取CUST_ID
				idsStr += selectedCustId;
				if(i != selectLength - 1)
					idsStr += ',';
				potentialFlag = selectRe.data.POTENTIAL_FLAG;// 获取潜在客户标志
				loanCustStat = selectRe.data.LOAN_CUST_STAT;// 获取信贷客户状态
				if(potentialFlag != '1') {
					Ext.Msg.alert('提示信息', '只能删除潜在客户！');
					return false;
				}
				if(loanCustStat !== null && loanCustStat !== '') {
					Ext.Msg.alert('提示信息', '不能删除信贷客户！');
					return false;
				}
			};
			if(selectedCustId != '') {
				Ext.Ajax.request({
					url		: basepath + '/dealWithCom!dodeleteCheck.json',
					method	: 'GET',
					async	: false,
					params	: {
						idsStr	: idsStr
					},
					success	: function(response) {
						var ret = Ext.decode(response.responseText);
						var deletecust = ret.custId;
						var type = ret.type;
						var deletename = ret.custName;
						if(type == '1') {// 可以删除
							Ext.MessageBox.confirm('系统提示信息', '确认进行删除吗？', function(btn) {
								if(btn == 'yes') {// 确认进行删除时
									Ext.Ajax.request({
										url		: basepath + '/dealWithCom!batchDestroy.json',
										params	: {
											idsStr	: idsStr
										},
										waitMsg	: '正在删除数据,请等待...',
										method	: 'POST', // method : 'GET'
										scope	: this,
										success	: function(response) {
											Ext.Msg.alert('提示信息', '删除成功！');
											reloadCurrentData();
										}
									});
								}
							}, this);
						} else if(type == '2') {
							Ext.MessageBox.alert('提示', '客户【' + deletename + '】未完成移转，不得删除！');
							return false;
						} else if(type == '3') {
							Ext.MessageBox.alert('提示', '客户【' + deletename + '】未完成callreport，不得删除！');
							return false;
						} else {

						}
					}
				});
			}
		}
	}
}, {
	id	    : 'detail',
	text	: '详情',
	hidden	: JsContext.checkGrant('latent_detail'),
	handler	: function() {
		// 刷新页面
		qzComLists.form.findField('AR_CUST_TYPE').setVisible(true);
		if(getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请选择一条数据进行操作！');
			return false;
		} else {
			var records = getAllSelects(); // 获取选择记录
			var custids = records[0].data.CUST_ID;// 获取id
			custId = custids;// add by liuming 20170825
			custName = records[0].data.CUST_NAME;//add by liuming 20170825
			var potentialFlag = records[0].data.POTENTIAL_FLAG;// 获取潜在客户标志1:潜在客户
			var loanCustStat = records[0].data.LOAN_CUST_STAT;// 获取信贷客户状态
			var custType = records[0].data.CUST_TYPE;// 获取客户类型：1，企业，2：个人
			var belongRm = records[0].json.MGR_ID;// 归属客户经理
			//modify by liuming 20170830 查询处理的信息都应可以查看详情
//			if(belongRm!=JsContext._userId){
//				Ext.Msg.alert('提示信息', '请选择归属于本人的客户！');
//				return false;
//			}else{
				// 详情只读(第一页\第二页\第四页)
			setDisabledFun(qzCombaseInfo);
			setDisabledFun(qzComLists);
			setDisabledFun(qzComOther1Info);
			// 第三页去除按钮新增和移除按钮
			setDetail(true);
			qzBlank();
			opNoRead();
			// 保存按钮的屏蔽
			setDetailSave(true);
			var _store4 = findLookupByType('REGISTERCUR');
			_store4.load();
			qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store4);// 注册资金币种
			qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store4);// 年销售币别

			var _store5 = findLookupByType('COMTYPE');
			_store5.load();
			qzCombaseInfo.form.findField('ORG_CUST_TYPE').bindStore(_store5);// 客户类型
			// 控制第二页的码值
			if(custType == '1') {
				var _store3 = findLookupByType('CUSTTYPEALL');
				_store3.load();
				qzComLists.form.findField('ORG_SUB_TYPE').bindStore(_store3);
			}
			window.resetjs(potentialFlag);// 清空所有页数据
			qzCombaseInfo.form.findField('CUST_ID').setVisible(true);
			qzCombaseInfo.form.findField('CUST_ID').setReadOnly(true);
			qzCombaseInfo.form.findField('CUST_ID').addClass('x-readOnly');
			// 刷新页面记忆加载页面
			searchFirstComFn(custids);// 第一页数据
			SearchqzComListsFn(custids);// 第二页数据
			window.queryTempData(custids);
			SearchqzComOther1Fn(custids);// 第四页数据
			SearchqzComOther2Fn(custids);// 第五页数据

			/**
			 * 有些隐藏项需要展示： 1、上市地和股票代码需显示 2、特种经营表示 3、AR客户类型与AR客户标志 4、科技型企业
			 */

			qzComLists.form.findField('SCIENTIFIC_TYPE').setVisible(true);// 4.企业类型(大中小微)
			qzComLists.form.findField('SCIENTIFIC_TERM').setVisible(true);

			if(custids != '' && potentialFlag == '0') { // 既有客户
				Ext.getCmp("his_1").setVisible(true);
				Ext.getCmp("his_2").setVisible(true);
				Ext.getCmp("his_3").setVisible(true);
				Ext.getCmp("his_4").setVisible(true);

				if(loanCustStat == '01') {// 临时户
					AddorUpdate('查看既有客户-信贷临时户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看既有客户-信贷临时户';
				} else if(loanCustStat == '21') {// 准正式客户
					AddorUpdate('查看既有客户-信贷准正式户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看既有客户-信贷准正式户';
				} else if(loanCustStat == '20') {// 正式客户
					AddorUpdate('查看既有客户-信贷正式户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看既有客户-信贷正式户';
				} else {
					AddorUpdate('查看既有客户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看既有客户';
				}
			}
			if(custids != '' && potentialFlag == '1') { // 潜在客户
				Ext.getCmp("his_1").setVisible(false);
				Ext.getCmp("his_2").setVisible(false);
				Ext.getCmp("his_3").setVisible(false);
				Ext.getCmp("his_4").setVisible(false);
				if(loanCustStat == '01') {// 临时户
					AddorUpdate('查看潜在客户-信贷临时户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看潜在客户-信贷临时户';
				} else if(loanCustStat == '21') {// 准正式客户
					AddorUpdate('查看潜在客户-信贷准正式户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看潜在客户-信贷准正式户';
				} else {
					AddorUpdate('查看潜在客户', false, custType);
					document.getElementById('myview').firstChild.innerHTML = '查看潜在客户';
				}
			}
// }
		}
	}	
}];

// 新增时选择客户类型的from
var addActivityForm = new Ext.form.FormPanel({
	labelWidth	: 140,
	width	    : 550,
	height	    : 160,
	frame	    : true,// 加滚动条
	id	        : 'allForms',
	labelAlign	: 'center',
	region	    : 'center',
	// autoScroll : true,
	buttonAlign	: "center",
	items	    : [{
		layout		: 'form',
		buttonAlign	: 'center',
		style		: 'margin-top:10px',
		items		: [{
			xtype			: 'combo',
			id			  : 'sign',
			name			: 'custSign',
			width			: 200,
			fieldLabel		: '客户操作类型',
			maxLength		: 300,
			store			: custSignStore,
			displayField	: 'value',
			valueField		: 'key',
			triggerAction	: 'all',
			emptyText		: '请选择...',
			allowBlank		: false,
			blankText		: '请选择客户操作类型',
			editable		: false,
			mode			: 'local',
			anchor			: '80%',
			listeners		: {
				'focus'	: {
					fn		: function(e) {
						e.expand();
						this.doQuery(this.allQuery, true);
					},
					buffer	: 200
				}
			}
		}, {
			xtype			: 'combo',
			id			  : 'type',
			width			: 200,
			name			: 'custType',
			fieldLabel		: '客户类型',
			maxLength		: 300,
			store			: custTypeStore,
			displayField	: 'value',
			valueField		: 'key',
			triggerAction	: 'all',
			emptyText		: '请选择...',
			allowBlank		: false,
			blankText		: '请选择客户标识',
			editable		: false,
			mode			: 'local',
			anchor			: '80%',
			readOnly		: true,
			cls			  : 'x-readOnly',
			listeners		: {
				'focus'	: {
					fn		: function(e) {
						e.expand();
						this.doQuery(this.allQuery, true);
					},
					buffer	: 200
				}
			}
		}]
	}]
});
/**
 * 新增按钮点击时弹出的窗口
 */
var AddWindow = new Ext.Window({
	plain	      : true,
	// layout : 'fit',
	layout	      : {
		align	: 'middle',
		pack	: 'center',
		type	: 'hbox'
	},
	constrain	  : true, // 限制窗口不超出浏览器边界
	resizable	  : true,
	draggable	  : true,
	closable	  : true,
	autoScroll	  : true,
	closeAction	  : 'hide',
	modal	      : true, // 模态窗口
	shadow	      : true,
	loadMask	  : true,
	maximizable	  : false,
	collapsible	  : false,
	titleCollapse	: true,
	border	      : false,
	width	      : 600,
	height	      : 300,
	buttonAlign	  : "center",
	title	      : '潜在客户新建向导页面',
	items	      : [addActivityForm],
	buttons	      : [{
		text	: '确定',
		handler	: function(btn) {
			AddWindow.hide();
			var myType = Ext.getCmp('type').getValue();// 1:企业;2:个人
			var mySign = Ext.getCmp('sign').getValue();// 1:潜在客户;2:信贷临时户

			qzCombaseInfo.form.findField('POTENTIAL_FLAG').setValue('1');
			opNoRead(); // 潜在客户非只读项控制
			if(myType == '1' && mySign == '1') { // 新增潜在客户
				qzBlank();// 潜在客户非必输项控制
				opNoRead();// 潜在客户只读项控制
				AddorUpdate("新增潜在客户", true, myType, false);// 新增潜在客户
				document.getElementById('myview').firstChild.innerHTML = '新增潜在客户';
			} else if(myType == '1' && mySign == '2') {// 新增临时户，控制必输项
				lnLsBlank();// 临时户必输项
				qzlsReadAndNoReadForAdd();// 临时户只读项控制
				AddorUpdate("新增信贷临时户", true, myType, false);// 新增信贷临时户
				document.getElementById('myview').firstChild.innerHTML = '新增信贷临时户';
				qzCombaseInfo.form.findField('LOAN_CUST_STAT').setValue('01');
			}
		}
	}, {
		text	: '取  消',
		handler	: function() {
			AddWindow.hide();
		}
	}]
});
/**
 * @see 页面的刷新:
 * @see 1.重置第一页、第二页、第四页、第五页所有输入框的值
 * @see 2.清空第二页、第三页所有表格里的数据
 * @param {Boolean} potentialFlag 是否潜在客户
 */
window.resetjs = function(potentialFlag) {
	// 重置第一页
	if(qzCombaseInfo.getForm().getEl()) {
		// qzCombaseInfo.getForm().getEl().dom.reset();
		qzCombaseInfo.getForm().reset();
	} else {
		qzCombaseInfo.getForm().reset();
	}
	// 重置第二页
	if(qzComLists.getForm().getEl()) {
		// qzComLists.getForm().getEl().dom.reset();
		qzComLists.getForm().reset();
	} else {
		qzComLists.getForm().reset();
	}
	// 清空相关表格数据：发生日期(第2页)、证件信息(第3页)、地址信息(第3页)、联系人信息(第3页)、联系信息(第3页)
	addHappenStore.removeAll();
	identInfoStore.removeAll();
	addrCustInfoStore.removeAll();
	comContactPersonStore.removeAll();
	comContactInfoStore.removeAll();
	// window.queryTempData();//modified by liuyx 这个清空不需要重新加载

	// modify by liuming 20170820
	// setXiugai(potentialFlag);
	// 重置第四页
	if(qzComOther1Info.getForm().getEl()) {
		// qzComOther1Info.getForm().getEl().dom.reset();
		qzComOther1Info.getForm().reset();
	} else {
		qzComOther1Info.getForm().reset();
	}
	// 重置第五页
	if(qzComOther2Info.getForm().getEl()) {
		// qzComOther2Info.getForm().getEl().dom.reset();
		qzComOther2Info.getForm().reset();
	} else {
		qzComOther2Info.getForm().reset();
	}
};
/**
 * 根据客户号，查询所有页数据并填充数据到相应的地方
 * @param {String} custId 客户号
 */
window.loadAllData = function(custId) {
	// 第一页
	qzComStore.load({
		params		: {
			custId	: custId
		},
		callback	: function() {
			// if(!(custId==null||custId=='')){
			if(qzComStore.getCount() != 0) {
				qzCombaseInfo.getForm().loadRecord(qzComStore.getAt(0));
			}
			// }
		}
	})
	// 第二页
	qzComListsStore.load({
		params		: {
			custId	: custId
		},
		callback	: function() {
			if(qzComListsStore.getCount() != 0) {
				if(!(custId == null || custId == '')) {
					qzComLists.getForm().loadRecord(qzComListsStore.getAt(0));
				}
			}
		}
	});

	// 第三页
	window.queryTempData(custId);
	// 第四页
	qzComOther1InfoStore.load({
		params		: {
			custId	: custId
		},
		callback	: function() {
			if(qzComOther1InfoStore.getCount() != 0) {
				if(!(custId == null || custId == '')) {
					qzComOther1Info.getForm().loadRecord(qzComOther1InfoStore.getAt(0));
				}
			}
		}
	});
	// 第五页
	qzComOther2InfoStore.load({
		params		: {
			custId	: custId
		},
		callback	: function() {
			if(qzComOther2InfoStore.getCount() != 0) {
				if(!(custId == null || custId == '')) {
					qzComOther2Info.getForm().loadRecord(qzComOther2InfoStore.getAt(0));
				}
			}
		}
	});
};
/**
 * 查询客户信息第一页基本信息
 */
var qzComStore = new Ext.data.Store({
	restful	: true,
	proxy	: new Ext.data.HttpProxy({
		url		: basepath + '/dealWithCom!queryComfsx.json',
		method	: 'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, ['CUST_ID', 'CORE_NO', 'LOAN_CUST_ID', 'CUST_TYPE', 'SHORT_NAME', 'CUST_NAME', 'IDENT_TYPE', 'RISK_NATION_CODE', 
	    'STAFFIN', 'CREATE_DATE', 'EN_NAME','IDENT_ID', 'IDENT_CUST_ID','IDENT_NO','IDENT_END_DATE', 'CREATE_BRANCH_NO', 'LOAN_CUST_STAT', 'SWIFT', 'LOAN_CUST_RANK','POTENTIAL_FLAG',
	    'OBU_ID','OBU_CUST_ID','IDENT_NO2','OPEN_ID','REGIS_ID','MGR_CUST_ID',
	    'ORG1_CUST_ID', 'ORG_CUST_TYPE', 'FLAG_CAP_DTL', 'LOAN_ORG_TYPE', 'NATION_CODE', 'IN_CLL_TYPE', 'IN_CLL_TYPE_ID','EMPLOYEE_SCALE',
	    'INVEST_TYPE', 'ORG_TYPE', 'ENT_SCALE', 'MAIN_BUSINESS', 'CREDIT_CODE', 'HQ_NATION_CODE', 'ENT_PROPERTY',
	    'COM_HOLD_TYPE', 'BUILD_DATE', 'ENT_SCALE_CK', 'MINOR_BUSINESS', 'TOTAL_ASSETS', 
	     'ANNUAL_INCOME', 'LOAN_CARD_NO','AREA_CODE','BUSI_LIC_NO','REMARK',
	    'ORG_CUST_ID', 'LEGAL_LINKMAN_ID', 'LEGAL_REPR_IDENT_NO', 'LEGAL_REPR_IDENT_TYPE', 'LEGAL_IDENT_EXPIRED_DATE',
	    'LEGAL_REPR_NAME', 'LEGAL_LINKMAN_TYPE', 'LEGAL_LAST_UPDATE_SYS', 'LEGAL_LAST_UPDATE_TM', 'LEGAL_LAST_UPDATE_USER',
	    'BUSIINFO_CUST_ID' ,'SALE_CCY' ,'SALE_AMT' ,
	    'REGISTER_CUST_ID', 'REGISTER_DATE', 'REGISTER_AREA', 'REGISTER_CAPITAL_CURR', 'END_DATE', 'REGISTER_NO', 'REGISTER_ADDR',
	    'REG_CODE_TYPE', 'REGISTER_TYPE', 'REGISTER_CAPITAL',
	    'ADDRESS_CUST_ID0','ADDR_ID0','ADDR0','REGISTER_ADDR','ADDR_TYPE0','ADDRESS_LAST_UPDATE_SYS0',
	    'ADDRESS_LAST_UPDATE_USER0', 'ADDRESS_LAST_UPDATE_TM0',
	    'ADDRESS_CUST_ID1','ADDR_ID1','ADDR1','ADDR_TYPE1','ADDRESS_LAST_UPDATE_SYS1',
	    'ADDRESS_LAST_UPDATE_USER1','ADDRESS_LAST_UPDATE_TM1',
	    'SW_REGIS_CODE','ACC_OPEN_LICENSE','MEMBER_ID','GROUP_NO','BELONG_GROUP',
	    'NATION_REG_ID','NATION_REG_CODE','AREA_REG_ID','AREA_REG_CODE',
	    'MGR_KEY_ID','MGR_ID','BELONG_RM','UNITID','BELONG_ORG','BELONG_BUSI_LINE',
	    'CREATE_TIME_LN','LAST_UPDATE_USER','LAST_UPDATE_TM','LAST_UPDATE_SYS','FIRST_LOAN_DATE'
//	    , 'BRANCH_ID'//归属机构id
	  ])
});
/**
 * @see 确定新增
 * @see 查询客户信息第一页基本信息并设置到相应的地方
 */
var searchFirstComFn = function(custId){
	if(qzCombaseInfo.getForm().getEl()) {
		qzCombaseInfo.getForm().getEl().dom.reset();
		// qzCombaseInfo.getForm().reset();
	} else {
		qzCombaseInfo.getForm().reset();
	}
	//查询客户信息第一页基本信息
	qzComStore.load({
		params		: {
			custId	: custId
		},
		callback:function(){
			window.readyCount++;
			// add by liuming 20170816
			var _store = findLookupByType('OPENIDENTALL');// 显示全部证件类型码值
			_store.load();
			qzCombaseInfo.form.findField('IDENT_TYPE').bindStore(_store);
			var _store1 = findLookupByType('REGISTERCURALL');// 显示所有币种
			_store1.load();
			qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store1);
			qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store1);
			
			if(qzComStore.getCount() != 0) {
        	   qzCombaseInfo.getForm().loadRecord(qzComStore.getAt(0));
				var title = getCustomerViewByIndex(0).title;
				var isLn = false;// 是否信贷客户
				if(title.indexOf('临时户') > -1 || title.indexOf('正式户') > -1) {
					isLn = true;
				}
				// add by liuming 20170817
				if(qzCombaseInfo.getForm().findField('IDENT_TYPE').getValue() == '2X') {
					setMust(qzCombaseInfo, 'EN_NAME', '英文名称', true);// 英文名称必输
				} else {
					setMust(qzCombaseInfo, 'EN_NAME', '英文名称', false);// 英文名称非必输
				}
				// add by liuming 20170817
				if(!(qzCombaseInfo.getForm().findField('IDENT_TYPE').getValue() == '2X') && isLn) {
					setMust(qzCombaseInfo, 'LOAN_CARD_NO', '中征码', true);// 中征码必输
				} else {
					setMust(qzCombaseInfo, 'LOAN_CARD_NO', '中征码', false);// 中征码非必输
				}
				// setReadOrNoRead(qzCombaseInfo,'CREDIT_CODE',qzCombaseInfo.getForm().findField('CREDIT_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'SW_REGIS_CODE',qzCombaseInfo.getForm().findField('SW_REGIS_CODE').getValue());
				// add by liuming 20170824
				// setReadOrNoRead(qzCombaseInfo,'AREA_REG_CODE',qzCombaseInfo.getForm().findField('AREA_REG_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'NATION_REG_CODE',qzCombaseInfo.getForm().findField('NATION_REG_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'IDENT_END_DATE',qzCombaseInfo.getForm().findField('IDENT_END_DATE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'NATION_CODE',qzCombaseInfo.getForm().findField('NATION_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'IN_CLL_TYPE',qzCombaseInfo.getForm().findField('IN_CLL_TYPE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'RISK_NATION_CODE',qzCombaseInfo.getForm().findField('RISK_NATION_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'AREA_CODE',qzCombaseInfo.getForm().findField('AREA_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'STAFFIN',qzCombaseInfo.getForm().findField('STAFFIN').getValue());
				// setReadOrNoRead(qzCombaseInfo,'HQ_NATION_CODE',qzCombaseInfo.getForm().findField('HQ_NATION_CODE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'BUILD_DATE',qzCombaseInfo.getForm().findField('BUILD_DATE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'LEGAL_REPR_NAME',qzCombaseInfo.getForm().findField('LEGAL_REPR_NAME').getValue());
				// setReadOrNoRead(qzCombaseInfo,'LEGAL_REPR_IDENT_NO',qzCombaseInfo.getForm().findField('LEGAL_REPR_IDENT_NO').getValue());
				// setReadOrNoRead(qzCombaseInfo,'LEGAL_REPR_IDENT_TYPE',qzCombaseInfo.getForm().findField('LEGAL_REPR_IDENT_TYPE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'LEGAL_IDENT_EXPIRED_DATE',qzCombaseInfo.getForm().findField('LEGAL_IDENT_EXPIRED_DATE').getValue());
				// setReadOrNoRead(qzCombaseInfo,'REGISTER_CAPITAL_CURR',qzCombaseInfo.getForm().findField('REGISTER_CAPITAL_CURR').getValue());
				// setReadOrNoRead(qzCombaseInfo,'REGISTER_CAPITAL',qzCombaseInfo.getForm().findField('REGISTER_CAPITAL').getValue());
				// setReadOrNoRead(qzCombaseInfo,'SALE_AMT',qzCombaseInfo.getForm().findField('SALE_AMT').getValue());
				// setReadOrNoRead(qzCombaseInfo,'SALE_CCY',qzCombaseInfo.getForm().findField('SALE_CCY').getValue());
				// setReadOrNoRead(qzCombaseInfo,'EN_NAME',qzCombaseInfo.getForm().findField('EN_NAME').getValue());
				setReadOrNoRead(qzCombaseInfo, 'REGISTER_ADDR', qzCombaseInfo.getForm().findField('REGISTER_ADDR').getValue());
				// setReadOrNoRead(qzCombaseInfo,'ADDR0',qzCombaseInfo.getForm().findField('ADDR0').getValue());
			}
		}
	})
};
// 基本信息部分 第一页（客户基础信息）
var qzCombaseInfo = new Ext.form.FormPanel({
	frame	    : true,
	autoScroll	: true,
	title	    : '第一页（客户基础信息）',
	labelWidth	: 140,
	buttonAlign	: "center",
	items	    : [{
		xtype		  : 'fieldset',
		title		  : '基本信息',
		titleCollapse	: true,
		collapsible		: true,
		autoHeight		: true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[// 隐藏字段
					         {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'客户编号',name:'CUST_ID',hidden:true},
					       	 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'核心客户号',name:'CORE_NO',hidden:true},
					       	 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'信贷客户号',name:'LOAN_CUST_ID',hidden:true},
					       	 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'信贷客户状态',name:'LOAN_CUST_STAT',hidden:true},
					       	 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'潜在客户标识',name:'POTENTIAL_FLAG',hidden:true},
					    	 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'客户类型1',name:'CUST_TYPE',hidden:true},
					       // 显示字段
					       	 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:80,fieldLabel:'客户简称',name:'SHORT_NAME'},		                                           
				    		 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:50,fieldLabel:'<font color=red>\*</font>客户名称',name:'CUST_NAME',allowBlank:false,
				       			 emptyText:'请按照机构登记注册证上或批文上的名称填写',msgTarget:"side", blankText: '请按照机构登记注册证上或批文上的名称填写'
				       			 ,listeners:{
				       				 blur:function(){
				       					 // 修改英文类型的括号为中文的括号
//				       					 var myname=qzCombaseInfo.form.findField('CUST_NAME').getValue();
//				       					if(myname.indexOf(' ')>-1){
//				       						 for(var i=0;i>-1;){
//				       							 var newname1=myname.replace(' ','');			  		
//				       					  		 qzCombaseInfo.form.findField('CUST_NAME').setValue(newname1);
//				       					  		 myname=newname1;
//				       					  		 i=myname.indexOf(' ');
//				       						 }		  			
//				       				  	}
				       					 
				       					var myname=qzCombaseInfo.form.findField('CUST_NAME').getValue();
				       					qzCombaseInfo.form.findField('CUST_NAME').setValue(myname.trim());
				       					 
				       				  	 var myname1=qzCombaseInfo.form.findField('CUST_NAME').getValue();
				       				  	 if(myname1.indexOf('(')>-1){
				       						 for(var i=0;i>-1;){
				       							 var newname1=myname1.replace('(','（');			  		
				       					  		 qzCombaseInfo.form.findField('CUST_NAME').setValue(newname1);
				       					  		myname1=newname1;
				       					  		i=myname1.indexOf('(');
				       						 }		  			
				       				  		};
				       				  	if(myname1.indexOf(')')>-1){
				       						 for(var i=0;i>-1;){					
				       					  		 var newname2=myname1.replace(')','）');
				       					  		 qzCombaseInfo.form.findField('CUST_NAME').setValue(newname2);
				       					  		myname1=newname2;
				       					  		i=myname1.indexOf(')');
				       						 }		  					  		
				       				  	};
				       				  
				       				 }
				       			 }},		                                           
				    		 {xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'<font color=red>\*</font>证件类型',name:'IDENT_TYPE',store:identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',allowBlank:false,listeners:{
										select:function(combo,record){
											 var ownerForm=this;
						        			 while(ownerForm && !ownerForm.form){
						        				 ownerForm=ownerForm.ownerCt;
						        			 }
						         			var value = combo.value;
						         			var title = getCustomerViewByIndex(0).title;
						         			var isLn = false;//是否信贷客户
						         			if(title.indexOf('临时户') > -1 || title.indexOf('正式户') > -1){
						         				isLn = true;
						         			}
						         			if(value=='20'){// 境内组织机构代码
						         				// 境内境外标志
						         				ownerForm.form.findField('NATION_CODE').setValue('CHN');// 企业所在国别：中国
						         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内境外标志：境内
						         				setMust(qzCombaseInfo,'EN_NAME','英文名称',false)//英文名称非必输
						         				if(isLn){
						         					setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',true)//中征码必输
						         				}else{
						         					setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//中征码非必输
						         				}
						         			}
						         			else if (value=='2X'){// 境外组织机构代码（赋码）
						         				qzComLists.form.findField('INOUT_FLAG').setValue('F');// 境内境外标志：境外
						         				setMust(qzCombaseInfo,'EN_NAME','英文名称',true)//境外组织机构代码（赋码）或注册号类型为99其它时，英文名必输
						         				setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//境外组织机构代码（赋码）或注册号类型为99其它时，中征码非必输
						         			}
						         			else if (value==''){//add by liuming 20170816
						         				ownerForm.form.findField('NATION_CODE').setValue(null);// 企业所在国别置为空
						         				qzComLists.form.findField('INOUT_FLAG').setValue(null);// 境内境外标志置为空
						         				setMust(qzCombaseInfo,'EN_NAME','英文名称',false)//英文名称非必输
						         				setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//中征码非必输
						         			}
						         			else{
//						         				qzComLists.form.findField('INOUT_FLAG').setValue(99);
						         				//modify by liuming20170816
						         				ownerForm.form.findField('NATION_CODE').setValue('CHN');// 企业所在国别：中国
						         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内境外标志：境内
						         				setMust(qzCombaseInfo,'EN_NAME','英文名称',false)//英文名称非必输
						         				setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//中征码非必输
						         			}
						         			
						         			//add by liuming 20170829
						         			var regCodeType = qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
						         			if(value == '20'){//境内组织机构代码
						         				if(regCodeType !='' && regCodeType == '99'){
						         					Ext.Msg.alert('提示','第一页基本信息校验失败，当“证件类型”为“境内组织机构代码”时，登记注册号类型不能为“其他”!');
						         					return;
						         				}
						         			}
						         			if (value == '2X'){//境外登记证件代码(赋码)
						         				if(regCodeType !='' && regCodeType != '99'){
						         					Ext.Msg.alert('提示','第一页基本信息校验失败，当“证件类型”为“境外登记证件代码(赋码)”时，登记注册号类型必须为“其他”!');
						         					return;
						         				}
						         			}
						         			
						         		},'focus': {  
						    			        fn: function(e) {  
						    			        	//add by liuming 20170816
						    			       	    var _store = findLookupByType('OPENIDENT');//新增时的码值
						    						_store.load();
						    						qzCombaseInfo.form.findField('IDENT_TYPE').bindStore(_store);
						    						//add end
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 },blur:function(combo,record){
						    				 	var identType=qzCombaseInfo.form.findField('IDENT_TYPE').getValue();// 获取证件类型
						    					var identNo=qzCombaseInfo.form.findField('IDENT_NO').getValue();// 获取证件号码的值
						    					var ownerForm=this;
							        			while(ownerForm && !ownerForm.form){
							        				 ownerForm=ownerForm.ownerCt;
							        			}
							         			var value = combo.value;
							         			var title = getCustomerViewByIndex(0).title;
							         			var isLn = false;//是否信贷客户
							         			if(title.indexOf('临时户') > -1 || title.indexOf('正式户') > -1){
							         				isLn = true;
							         			}
						    					if(value=='20'){// 境内组织机构代码
							         				// 境内境外标志
							         				ownerForm.form.findField('NATION_CODE').setValue('CHN');// 企业所在国别：中国
							         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内境外标志：境内
							         				setMust(qzCombaseInfo,'EN_NAME','英文名称',false)//英文名称非必输
							         				if(isLn){
							         					setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',true)//中征码必输
							         				}else{
							         					setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//中征码非必输
							         				}
							         			}
							         			else if (value=='2X'){// 境外组织机构代码（赋码）
							         				qzComLists.form.findField('INOUT_FLAG').setValue('F');// 境内境外标志：境外
							         				qzCombaseInfo.form.findField('IDENT_NO2').setValue(identNo);// Obu// Code
							         				//add by liuming 20170816
							         				setMust(qzCombaseInfo,'EN_NAME','英文名称',true)//境外组织机构代码（赋码）或注册号类型为99其它时，英文名必输
							         				setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//境外组织机构代码（赋码）或注册号类型为99其它时，中征码非必输
							         			}
							         			else if (value==''){//add by liuming 20170816
							         				ownerForm.form.findField('NATION_CODE').setValue(null);// 企业所在国别置为空
							         				qzComLists.form.findField('INOUT_FLAG').setValue(null);// 境内境外标志置为空
							         				setMust(qzCombaseInfo,'EN_NAME','英文名称',false)//英文名称非必输
							         				setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//中征码非必输
							         			}
							         			else{
//							         				qzComLists.form.findField('INOUT_FLAG').setValue('99');
							         				//modify by liuming 20170816
							         				ownerForm.form.findField('NATION_CODE').setValue('CHN');// 企业所在国别：中国
							         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内境外标志：境内
							         				qzCombaseInfo.form.findField('IDENT_NO2').setValue('');// Obu Code
							         				setMust(qzCombaseInfo,'EN_NAME','英文名称',false)//英文名称非必输
							         				setMust(qzCombaseInfo,'LOAN_CARD_NO','中征码',false)//中征码非必输
							         			}
						    			 }		
									} 
				       			 },	
							 {xtype : 'textfield',fieldLabel : 'Obu Code',name : 'IDENT_NO2',anchor : '90%',maxLength:50,hidden:true},
							 {xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'客户类型',name:'ORG_CUST_TYPE',store:orgCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode			: 'local',
                					forceSelection	: true,
                					maxLength		: 30,
                					triggerAction	: 'all',
                					listeners		: {
                						'focus'	: {
                							fn		: function(e) {
                								e.expand();
                								this.doQuery(this.allQuery, true);
                							},
                							buffer	: 200
                						}
                					}
                			 },
				    		 {xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'组织机构类别',name:'LOAN_ORG_TYPE',store:zzOrgStore,resizable : true,valueField : 'key',displayField : 'value',
    								mode			: 'local',
                					forceSelection	: true,
                					maxLength		: 30,
                					triggerAction	: 'all',
                					listeners		: {
                						'focus'	: {
                							fn		: function(e) {
                								e.expand();
                								this.doQuery(this.allQuery, true);
                							},
                							buffer	: 200
                						}
                					}
                			 },
							 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:18,fieldLabel:'统一社会信用代码',name:'BUSI_LIC_NO'
//								 ,emptyText:'只能录入18位字符，且不可存有空格',msgTarget:"side"
//					    					 ,regex:/^[A-Za-z0-9]{18}$/,regexText:'只能录入18位字符，且不可存有空格，例：91430111MA4L16JQ9B'	
//					    						 ,validator:function(){
//					    						   	  var ownerForm=this;
//									            	  while(ownerForm && !ownerForm.form){
//									            			ownerForm=ownerForm.ownerCt;
//									            	  }
//									            	  setReadOrNoRead(this,this.name,this.value);
//									            	  if(!ownerForm.form.findField(this.name).allowBlank){
//									            		  return true; 
//									            	  }
//					    						 }
					    			 },
							 {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'税务登记证编号',name:'SW_REGIS_CODE' 
					    	 },
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'AREA_REG_CODE',fieldLabel:'地税税务登记代码'}
				    		]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'所属集团',name:'BELONG_GROUP',hiddenName:'GROUP_NO',xtype:'group',resutlWidth:80,singleSelect: false,searchField: true},		 		
					       {xtype:'textfield',id:'enNameId',anchor:'90%',maxLength:100,readOnly:false,fieldLabel:'英文名称',name:'EN_NAME',emptyText:'如果没有英文名称，此项填写外文名称。',msgTarget:"side", blankText: '不可与存量正式客户之英文名称重名；当登记注册号类型为99（即境外机构）者，为必填项', allowBlank: true},				 
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'<font color=red>\*</font>证件号码',name:'IDENT_NO',allowBlank:false ,listeners:{
			    			   blur:function(){
			    				 	var identType=qzCombaseInfo.form.findField('IDENT_TYPE').getValue();// 获取证件类型
			    					var identNo=qzCombaseInfo.form.findField('IDENT_NO').getValue();// 获取证件号码的值
			    					
			    					if(identType=='20'){// 境内组织机构代码
				         				// 境内境外标志
			    						qzCombaseInfo.form.findField('NATION_CODE').setValue('CHN');// 企业所在国别：中国
				         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内境外标志：境内
				         			}
				         			else if (identType=='2X'){// 境外组织机构代码（赋码）
				         				qzComLists.form.findField('INOUT_FLAG').setValue('F');// 境内境外标志：境外
				         				qzCombaseInfo.form.findField('IDENT_NO2').setValue(identNo);// Obu Code
				         			}
				         			else if (value==''){//add by liuming 20170816
				         				ownerForm.form.findField('NATION_CODE').setValue(null);// 企业所在国别置为空
				         				qzComLists.form.findField('INOUT_FLAG').setValue(null);// 境内境外标志置为空
				         			}
				         			else{
//				         				qzComLists.form.findField('INOUT_FLAG').setValue('99');
				         				//modify by liuming 20170816
				         				qzCombaseInfo.form.findField('NATION_CODE').setValue('CHN');// 企业所在国别：中国
				         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内境外标志：境内
				         				qzCombaseInfo.form.findField('IDENT_NO2').setValue('');// Obu Code
				         			}
			    			 }
			    		   }},	
			    		   {xtype:'datefield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'证件到期日',name:'IDENT_END_DATE',format:'Y-m-d',beforeBlur:function(){ return false;},
						   listeners:{
			    			 'blur': function(datefield,record){
			    				 if(!this.validate()){
			    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
			    				 }else{
			    					 if(!CheckDate('证件到期日',this.getRawValue())){
			    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
			    					 }
			    				  }
			         		    }
			    		     }
			    		   }, 
			    		   {xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'组织机构类别细分',name:'FLAG_CAP_DTL',store:zzOrgDetailStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',
								listeners:{
									'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 },select:function(combo,record){
								 var ownerForm=this;
			        			 while(ownerForm && !ownerForm.form){
			        				 ownerForm=ownerForm.ownerCt;
			        			 }
			         			var value = combo.value;// 组织机构细分
			         			var zztype=ownerForm.form.findField('LOAN_ORG_TYPE').getValue();// 组织机构类别；
			         			if(zztype==''){
			         				 showMsgNotification('提示：‘组织机构类别’未有值，请先选择‘组织机构类别’!');
			         			}
			         		}}},		       			 	
//                         {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'组织机构细分',name:'ORG_TYPE_DETAIL',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'机构信用代码',name:'CREDIT_CODE' ,
			    			   emptyText:'只能录入18位字符，且不可存有空格',msgTarget:"side"
			    			   ,regex:/^[A-Za-z0-9]{18}$/,regexText:'只能录入18位字符，且不可存有空格，例：91430111MA4L16JQ9B'	
//                              ,listeners:{
//                                 blur:function(){
//                                 //小写转为大写
//                                 var mycode=qzCombaseInfo.form.findField('CREDIT_CODE').getValue();
//                                 var newcode=Ext.util.Format.uppercase(mycode);
//                                 qzCombaseInfo.form.findField('CREDIT_CODE').setValue(newcode);
//                                 }
//                                }
			    		   },  
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'开户许可证核准号',name:'ACC_OPEN_LICENSE',readOnly:false
//                          ,regex:/^[J,L,Z,j,l,z]{1}[0-9]{13}$/,regexText:'格式有误,例：J4910123456712'
//                          //限制开户许可证号的空格
//                          ,listeners:{
//                            blur:function(){
//                            var mycode=qzCombaseInfo.form.findField('ACC_OPEN_LICENSE').getValue();
//                            var newcode=Ext.util.Format.uppercase(mycode);
//                            qzCombaseInfo.form.findField('ACC_OPEN_LICENSE').setValue(newcode);
//                          }
//                        }
			    			 ,regex:/^[0-9A-Za-z]{14}$/,regexText:'只能录入14位字符，且不可存有空格',
			    			  emptyText:'只能录入14位字符，且不可存有空格',msgTarget:"side"
						   },
						   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'NATION_REG_CODE',fieldLabel:'国税税务登记代码'}
					   ]
				},{
					columnWidth:.5,  
					layout:'form',
					items:[		 	
					       // 主键
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'ORG1_CUST_ID',fieldLabel:'机构id',hidden:true},	
//                         {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'LEGAL_ORG_CUST_ID',fieldLabel:'干系人id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'LEGAL_LINKMAN_ID',fieldLabel:'法人id',hidden:true},		       			 	
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'BUSIINFO_CUST_ID',fieldLabel:'经营id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'REGISTER_CUST_ID',fieldLabel:'注册id',hidden:true},
//                         {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'ADDRESS_CUST_ID',fieldLabel:'地址客户id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'ADDR_ID0',fieldLabel:'地址0id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'ADDR_ID1',fieldLabel:'地址1id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'IDENT_ID',fieldLabel:'证件id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'IDENT_CUST_ID',fieldLabel:'证件cust_id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'MEMBER_ID',fieldLabel:'集团id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'OBU_ID',fieldLabel:'OBU证件id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'OBU_CUST_ID',fieldLabel:'OBU证件custid',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'OPEN_ID',fieldLabel:'OPEN证件id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'REGIS_ID',fieldLabel:'REGIS证件id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'NATION_REG_ID',fieldLabel:'国税证件id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'AREA_REG_ID',fieldLabel:'地税证件id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'MGR_KEY_ID',fieldLabel:'归属客户id',hidden:true}	,
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'BRANCH_ID',fieldLabel:'归属机构id',hidden:true}
			    	]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '分类信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
				       		{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'企业所在国别',name:'NATION_CODE', id:'NATION_CODE',msgTarget:"side", 
				       			store:comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',emptyText:'采用GB/T 2659－2000 世界各国和地区名称代码'
									,listeners:{
									   'select':function(combo,record){
											 var ownerForm=this;
						        			 while(ownerForm && !ownerForm.form){
						        				 ownerForm=ownerForm.ownerCt;
						        			 }
						         			var value = combo.value;
						         			if(value=='CHN'){// 中国
						         				qzComLists.form.findField('INOUT_FLAG').setValue('D');// 境内
						         			}
						         			else{
						         				qzComLists.form.findField('INOUT_FLAG').setValue('F');// 境外
						         			}
						         		}
						         		,'focus': {  
					    			        fn: function(e) {  
					    			            e.expand();  
					    			            this.doQuery(this.allQuery, true);  
					    			        },  
					    			        buffer:200  
					    			    }
									}
					       	 },
							{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'总部所在国别',name:'HQ_NATION_CODE',store:comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
									listeners:{	
                                         'focus': {  
					    			        fn: function(e) {  
					    			            e.expand();  
					    			            this.doQuery(this.allQuery, true);  
					    			        },  
					    			        buffer:200  
					    			 }}
					         },				
//                          {anchor:'90%',readOnly:false,fieldLabel:'行业类别',name:'IN_CLL_TYPE',
//                           xtype : 'wcombotree',innerTree:'BTTREE',resutlWidth:80,searchField: false,
//                           showField:'text',hideField:'F_CODE',editable:false,allowBlank:false},
							{anchor:'90%',readOnly:false,fieldLabel:'行业类别',name:'IN_CLL_TYPE',hiddenName:'IN_CLL_TYPE_ID',maxLength:100,
					        	xtype : 'businessType',searchField: false, editable:false
					         },
							{anchor:'90%',name:'IN_CLL_TYPE_ID',hidden:true,fieldLabel:'行业类型id'},
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:6,fieldLabel:'从业人数',name:'EMPLOYEE_SCALE',regex:/^[0-9]*$/,regexText:'格式有误,只能填数字，不允许有小数点',
								listeners : {
								       change : function(field,newValue,oldValue){
								    	   if(newValue <= 0){
								    		   field.setValue(oldValue);
								    		   Ext.Msg.alert('提示','请输入大于0的数值!');
					         				   return;
								    	   }
								       }
								}},	
							{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'国别风险国别代码',name:'RISK_NATION_CODE',store:comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }}
						},								
							{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'投资主体',name:'INVEST_TYPE',store:investTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',
									listeners:{
										'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }}}							
						   ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       	{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,name:'AREA_CODE',fieldLabel:'地区代码',store:dqStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			  }
						       	}
						     },											
					       	{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'关联人类型',name:'STAFFIN',store:staffInStore,resizable:true,valueField:'key',displayField:'value',
								mode:'local',forceSelection:true,triggerAction:'all',
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }}
								},
							{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'企业性质',name:'ENT_PROPERTY',store:entPropertyStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
									listeners:{
										'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }}},
							{anchor:'90%',readOnly:false,fieldLabel:'经济类型',name:'REGISTER_TYPE',hiddenName:'REGISTER_TYPE_ID',maxLength:100,
								xtype : 'economicType',searchField: false, editable:false
//								,
////								msgTarget:"side", 
//								validator:function(){
//					               	  var ownerForm=this;
//					            	  while(ownerForm && !ownerForm.form){
//					            			ownerForm=ownerForm.ownerCt;
//					            	  }
//					            	  setReadOrNoRead(this,this.name,this.value);
//					            	  if(!ownerForm.form.findField(this.name).allowBlank){
//					            		  return true; 
//					            	  }
//					              }
							},									
							{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'控股类型',name:'COM_HOLD_TYPE',store:comHoldTypeStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
							}},	
							{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'企业规模(银监)',name:'ENT_SCALE',store:entScaleStore,resizable:true,valueField:'key',displayField:'value',
									mode:'local',forceSelection:true,triggerAction:'all',
									listeners:{	
										'focus': {  
					    			        fn: function(e) {  
					    			            e.expand();  
					    			            this.doQuery(this.allQuery, true);  
					    			        },  
					    			        buffer:200  
					    			 }
							       }
//							      ,validator:function(){
//					    			   	  var ownerForm=this;
//						            	  while(ownerForm && !ownerForm.form){
//						            			ownerForm=ownerForm.ownerCt;
//						            	  }
//						            	  setReadOrNoRead(this,this.name,this.value);
//						            	  if(!ownerForm.form.findField(this.name).allowBlank){
//						            		  return true; 
//						            }
//					    		}
					    	},
									
							{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'公司规模',name:'ENT_SCALE_CK',store:orgCustTypeStore,resizable:true,valueField:'key',displayField:'value',
								mode:'local',forceSelection:true,triggerAction:'all',hidden:true,
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
								}}														
										
							]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '注册信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					        {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'法定代表人姓名',name:'LEGAL_REPR_NAME',readOnly:false
						      },		
					        {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:40,fieldLabel:'法定代表人证件号码',name:'LEGAL_REPR_IDENT_NO',readOnly:false
						      },							
					        {xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'登记注册号类型',name:'REG_CODE_TYPE',store:regCodeStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
								,listeners:{
									select:function(combo,record){
					         			var value = combo.value;
					         			//add by liuming 20170828
					         			var identType = qzCombaseInfo.form.findField('IDENT_TYPE').getValue();
					         			if(value == '99'){
					         				if(identType != '2X'){
					         					Ext.Msg.alert('提示','第一页基本信息校验失败，当“登记注册号类型”为“其他”时，证件类型必须为“境外登记证件代码(赋码)”!');
					         					return;
					         				}
					         			}
					         			if (value != '' && value != '99'){
					         				if(identType != '20'){
					         					Ext.Msg.alert('提示','第一页基本信息校验失败，当“登记注册号类型”不为“其他”时，证件类型必须为“境内组织机构代码”!');
					         					return;
					         				}
					         			}
					         			
					         			if(value=='99'){// 其他
					         				// 英文名称必输
					         			    qzCombaseInfo.form.findField('EN_NAME').label.dom.innerHTML='<font color=red>\*</font>英文名称:';
					         				qzCombaseInfo.form.findField('EN_NAME').allowBlank=false;
					         				// 机构信用代码免输
					         				qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=true;
					         				qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='机构信用代码:';
//					         				//不为统一社会信用代码，四个值清空
					         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
					         				}
//					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue('');//税务登记证号码
//					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue('');//地税登记证号码
//					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue('');//国税税务登记号码
					         			}
					         			else if(value=='07'){// 统一社会信用代码
					         				var busiLicNo=qzCombaseInfo.form.findField('REGISTER_NO').getValue();//登记注册号码
					         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue(busiLicNo);//统一社会信用代码
					         					qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue(busiLicNo);//税务登记证号码
					         				}else{
					         				   //add by liuming 20170825
					         					validateBusiLicNoIsBlank();
					         				}
					         				//地税与国税取统一社会信用代码9-17位
//					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue(busiLicNo.substr(8,9));//税务登记证号码
//					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(busiLicNo.substr(8,9));//地税登记证号码
//					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(busiLicNo.substr(8,9));//国税税务登记号码
					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(busiLicNo);//地税登记证号码
					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(busiLicNo);//国税税务登记号码
					         				
					         				// 机构信用代码必输
//					         				qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='<font color=red>\*</font>机构信用代码:';
//					         				qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=false;
					         			}
					         			else if(value=='01'){// 工商注册号
					    	            	  var registerNo=qzCombaseInfo.form.findField('REGISTER_NO').getValue();// 登记注册号码
					    	            	  if(registerNo.length!=15){
					    	            		  showMsgNotification('提示：当“登记注册号类型选择“工商注册号”时，登记注册号码长度必须为15!');
					    	            	  }
					    	            	  //不为统一社会信用代码，四个值清空
					    	            	  if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
						         				  qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
						         			  }
//						         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue('');//税务登记证号码
//						         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue('');//地税登记证号码
//						         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue('');//国税税务登记号码
							    	             
					         			}
					         			else{// 英文名称不必输
					         			    qzCombaseInfo.form.findField('EN_NAME').label.dom.innerHTML='英文名称:';
					         				qzCombaseInfo.form.findField('EN_NAME').allowBlank=true;
					         				
					         				//不为统一社会信用代码，四个值清空
					         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
					         				}
//					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue('');//税务登记证号码
//					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue('');//地税登记证号码
//					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue('');//国税税务登记号码
//					         				
					         		    }
					         		},blur:function(combo,record){
					         			var value = combo.value;
					         			if(value=='99'){// 其他
					         				// 英文名称必输
					         			    qzCombaseInfo.form.findField('EN_NAME').label.dom.innerHTML='<font color=red>\*</font>英文名称:';
					         				qzCombaseInfo.form.findField('EN_NAME').allowBlank=false;
					         				// 机构信用代码免输
					         				qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=true;
					         				qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='机构信用代码:';
					         				//不为统一社会信用代码，四个值清空
					         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
					         				}
//					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue('');//税务登记证号码
//					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue('');//地税登记证号码
//					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue('');//国税税务登记号码
					         			}
					         			else if(value=='07'){// 统一社会信用代码
					         				var busiLicNo=qzCombaseInfo.form.findField('REGISTER_NO').getValue();//登记注册号码
					         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue(busiLicNo);//统一社会信用代码
					         					qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue(busiLicNo);//税务登记证号码
					         				}else{
					         				    //add by liuming 20170825
					         					validateBusiLicNoIsBlank();
						         			}
					         				//地税与国税取统一社会信用代码9-17位
//					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue(busiLicNo.substr(8,9));//税务登记证号码
//					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(busiLicNo.substr(8,9));//地税登记证号码
//					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(busiLicNo.substr(8,9));//国税税务登记号码
					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(busiLicNo);//地税登记证号码
					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(busiLicNo);//国税税务登记号码
					         				// 机构信用代码必输
//					         				qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='<font color=red>\*</font>机构信用代码:';
//					         				qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=false;
					         			}
					         			else if(value=='01'){// 工商注册号
					    	            	  var registerNo=qzCombaseInfo.form.findField('REGISTER_NO').getValue();// 登记注册号码
					    	            	  if(registerNo.length!=15){
					    	            		  showMsgNotification('提示：当“登记注册号类型选择“工商注册号”时，登记注册号码长度必须为15!');
					    	            	  }
					    	            	//不为统一社会信用代码，四个值清空
					    	            		if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
						         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
						         				}
//						         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue('');//税务登记证号码
//						         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue('');//地税登记证号码
//						         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue('');//国税税务登记号码   
					         			}
					         			else{// 英文名称不必输
					         			    qzCombaseInfo.form.findField('EN_NAME').label.dom.innerHTML='英文名称:';
					         				qzCombaseInfo.form.findField('EN_NAME').allowBlank=true;
					         				//不为统一社会信用代码，四个值清空
					         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
					         				}
//					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue('');//税务登记证号码
//					         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue('');//地税登记证号码
//					         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue('');//国税税务登记号码
					         				// showMsgNotification('提示：“登记注册号类型”不为“其他”时，机构信用代码必输!');
					         			
					         				// 机构信用代码必输
					         				// qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=false;
					         				// qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='<font
					         				// color=red>\*</font>机构信用代码:';
					         				
					         		    }
					         		},'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
								}	
			    		},					    					    		
				    		{xtype:'datefield',anchor:'90%',readOnly:false,maxLength:40,fieldLabel:'注册登记日期',name:'REGISTER_DATE',format:'Y-m-d',beforeBlur:function(){ return false;},
							 listeners:{
					    			 'blur': function(datefield,record){
					    				 if(!this.validate()){
					    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    				 }else{
					    					 if(!CheckDate('注册登记日期',this.getRawValue())){
					    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    					 }
					    				  }
					         		    }
					    		     }
				    		},						
				    		{xtype:'datefield',anchor:'90%',readOnly:false,maxLength:40,fieldLabel:'注册登记证到期日',name:'END_DATE',format:'Y-m-d',blankText:'机构登记证书上记载的有效期最终到期日',beforeBlur:function(){ return false;},
							 listeners:{
					    			 'blur': function(datefield,record){
					    				 if(!this.validate()){
					    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    				 }else{
					    					 if(!CheckDate('注册登记证有效期',this.getRawValue())){
					    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    					 }
					    				  }
					         		    }
					    		 }
				    		},
				    		{xtype:'address',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'行政区划名称',name:'REGISTER_AREA',hiddenName:'REGISTER_AREA_ID',maxLength:100,
				    			 emptyText:'请参照GB/T 2260-2007 中华人民共和国行政区划代码'
//				    				 ,msgTarget:"side"
				    				, blankText: '请参照GB/T 2260-2007 中华人民共和国行政区划代码'
				    			// 将值赋给地址
// ,
// callback:function(b){
// qzCombaseInfo.form.findField('REGISTER_ADDR').setValue(b);
// qzCombaseInfo.form.findField('ADDR').setValue(b);
// }
			    	        },	
				    		{xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'注册资金币种',name:'REGISTER_CAPITAL_CURR',	store:saleCcyStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			        	//add by liuming 20170816
				    			       	    var _store = findLookupByType('REGISTERCUR');//新增时的码值
				    						_store.load();
				    						qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').bindStore(_store);
				    						//add end
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }}
				    	      	},
							{xtype:'textarea',anchor:'90%',readOnly:false,maxLength:200,fieldLabel:'主营业务',name:'MAIN_BUSINESS'},
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'SwiftAddress',name:'SWIFT',hidden:true}	    
					]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					        {xtype:'combo',anchor:'90%',readOnly:false,maxLength:40,fieldLabel:'法定代表人证件类型',name:'LEGAL_REPR_IDENT_TYPE',
					        	store:identTypeStore,mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',resizable : true,valueField : 'key',displayField : 'value',
								listeners:{
									'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}	
					        },			
							{xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'法定代表人证件失效日期',name:'LEGAL_IDENT_EXPIRED_DATE',format:'Y-m-d',readOnly:false,beforeBlur:function(){ return false;},
							 listeners:{
					    			 'blur': function(datefield,record){
					    				 if(!this.validate()){
					    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    				 }else{
					    					 if(!CheckDate('法定代表人证件失效日期',this.getRawValue())){
					    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    					 }
					    				  }
					         		    }
					    		 }
					        },												
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'登记注册号码',name:'REGISTER_NO',listeners:{
								blur:function(combo,record){
				         			var value = combo.value;
				         			var type= qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
				         			if(type=='99'){// 其他
				         				// 英文名称必输
				         			    qzCombaseInfo.form.findField('EN_NAME').label.dom.innerHTML='<font color=red>\*</font>英文名称:';
				         				qzCombaseInfo.form.findField('EN_NAME').allowBlank=false;
				         				// 机构信用代码免输
				         				qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=true;
				         				qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='机构信用代码:';
				         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
				         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
				         				}
				         			}
				         			else if(type=='07'){// 统一社会信用代码
				         				var busiLicNo=qzCombaseInfo.form.findField('REGISTER_NO').getValue();//登记注册号码
				         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
				         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue(busiLicNo);//统一社会信用代码
					         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue(busiLicNo);//税务登记证号码
				         				}else{
				         				    //add by liuming 20170825
				         					validateBusiLicNoIsBlank();
					         			}
				         				//地税与国税取统一社会信用代码9-17位
//				         				qzCombaseInfo.form.findField('SW_REGIS_CODE').setValue(busiLicNo.substr(8,9));//税务登记证号码
//				         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(busiLicNo.substr(8,9));//地税登记证号码
//				         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(busiLicNo.substr(8,9));//国税税务登记号码
				         				qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(busiLicNo);//地税登记证号码
				         				qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(busiLicNo);//国税税务登记号码
				         				
				         				// 机构信用代码必输
//				         				qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='<font color=red>\*</font>机构信用代码:';
//				         				qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=false;
				         			}
				         			else if(type=='01'){// 工商注册号
				    	            	  var registerNo=qzCombaseInfo.form.findField('REGISTER_NO').getValue();// 登记注册号码
				    	            	  if(registerNo.length!=15){
				    	            		  showMsgNotification('提示：当“登记注册号类型选择“工商注册号”时，登记注册号码长度必须为15!');
				    	            	  }
				    	            	  if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
					         				  qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
					         			  }
				         			}
				         			else{// 英文名称不必输
				         			    qzCombaseInfo.form.findField('EN_NAME').label.dom.innerHTML='英文名称:';
				         				qzCombaseInfo.form.findField('EN_NAME').allowBlank=true;
				         				if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
				         					qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
				         				}
				         		    }
				         		}
							}},		
							{xtype:'datefield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'成立日期',name:'BUILD_DATE',format:'Y-m-d',beforeBlur:function(){ return false;},
							 listeners:{
					    			 'blur': function(datefield,record){
					    				 if(!this.validate()){
					    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    				 }else{
					    					 if(!CheckDate('成立日期',this.getRawValue())){
					    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
					    					 }
					    				  }
					         		    }
					    		 }
				    	 },	
// {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'注册登记有效期',name:'END_DATE'
// ,regex:/^[0-9]*$/},
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:200,fieldLabel:'注册（登记）地址',name:'REGISTER_ADDR'  }	,			
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:200,fieldLabel:'实际经营地址',name:'ADDR0'},														
							{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'注册资金(万元)',name:'REGISTER_CAPITAL',maxLength:15,
								regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
								regexText:'格式有误,例：999999.9，-9999999.9'
							},
							{xtype:'textarea',anchor:'90%',readOnly:false,maxLength:200,fieldLabel:'兼营业务',name:'MINOR_BUSINESS'}
							]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '经营信息',
			titleCollapse : false,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:15,fieldLabel:'预计营业收入',name:'ANNUAL_INCOME',
					    	   	regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
								regexText:'格式有误,例：999999.9，-9999999.9'},	
					       {xtype:'combo',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'年销售额币别',name:'SALE_CCY',store:saleCcyStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
								listeners:{	
									'focus': {  
				    			        fn: function(e) {  
				    			        	//add by liuming 20170816
				    			       	    var _store = findLookupByType('REGISTERCUR');//新增时的码值
				    						_store.load();
				    						qzCombaseInfo.form.findField('SALE_CCY').bindStore(_store);
				    						//add end
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
						       } 
				    		 }
						]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					    {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:15,fieldLabel:'预计资产总额(万元)',name:'TOTAL_ASSETS',
								regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
								regexText:'格式有误,例：999999.9，-9999999.9'},	
						{xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'年销售额(万元)',name:'SALE_AMT',maxLength:15
								,regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
								regexText:'格式有误,例：999999.9，-9999999.9'
					}]
			}]		
		  }]	
			
		},{
			xtype : 'fieldset',
			title : '与我行往来信息',
			titleCollapse : false,
			collapsible : true,
			collapsed:true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'客户资料开立日',name:'CREATE_DATE',format:'Y-m-d',readOnly:true,cls:'x-readOnly'},
					       {xtype:'textfield',anchor:'90%',disabled:true,maxLength:30,fieldLabel:'客户归属业务条线',name:'BELONG_BUSI_LINE'},
					       {xtype:'textfield',name:'MGR_ID',hidden:true},
					       {xtype:'userchoose',resutlWidth:110,hiddenName:'MGR_ID',anchor:'90%',maxLength:30,fieldLabel:'归属客户经理',name:'BELONG_RM',disabled:true,cls:'x-readOnly'},
					       {xtype:'textfield',anchor:'90%',maxLength:30,fieldLabel:'最后更新系统',name:'LAST_UPDATE_SYS',readOnly:true,cls:'x-readOnly'},
					       {xtype:'userchoose',resutlWidth:110,hiddenName:'MGR_ID1',anchor:'90%',maxLength:30,fieldLabel:'最后更新人',name:'LAST_UPDATE_USER',disabled:true,cls:'x-readOnly'}]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'textfield',anchor:'90%',maxLength:30,fieldLabel:'开户行',name:'CREATE_BRANCH_NO',readOnly:true,cls:'x-readOnly'},
					       {xtype:'orgchoose',resutlWidth:110,hiddenName:'UNITID',anchor:'90%',maxLength:30,fieldLabel:'归属机构',name:'BELONG_ORG',disabled:true,cls:'x-readOnly'},
//					       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'潜在客户开户时间',name:'CREATE_TIME_LN',format:'Y-m-d',readOnly:true,cls:'x-readOnly'},
					       //modify by liuming 20170824 创建时间加十分秒
					       {xtype:'textfield',anchor:'90%',maxLength:30,fieldLabel:'潜在客户开户时间',name:'CREATE_TIME_LN',readOnly:true,cls:'x-readOnly'},
					       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'信贷最早开户日期',name:'FIRST_LOAN_DATE',format:'Y-m-d',readOnly:true,cls:'x-readOnly'},
//					       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'最后更新时间',name:'LAST_UPDATE_TM',format:'Y-m-d',readOnly:true,cls:'x-readOnly'}
					       //modify by liuming 20170824 最后更新时间加十分秒
					       {xtype:'textfield',anchor:'90%',maxLength:30,fieldLabel:'最后更新时间',name:'LAST_UPDATE_TM',readOnly:true,cls:'x-readOnly'}
					       ]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '中征码信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[ {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,fieldLabel:'中征码',name:'LOAN_CARD_NO',
						 emptyText:'只能录入16位字符，且不可存有空格',msgTarget:"side",
						 regex:/^[A-Za-z0-9]{16}$/,regexText:'只能录入16位字符，且不可存有空格' }]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '备注信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:1,  
					layout:'form',
					items:[ {xtype:'textarea',anchor:'90%',readOnly:false,maxLength:200,fieldLabel:'备注',name:'REMARK'}]
					}]
			}]		
		}],
	buttonAlign : "center",
	buttons:[{
		text:'修改历史',
		id:'his_1',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'下一页',
		 handler:function(btn){
            // SearchqzComListsFn();
			qzComInfo.setActiveTab(1);
			
		 }
	},{// 第一页的保存
		text:'保存',
		id:'first_save',
		handler:function(){
			Ext.getCmp('first_save').disable();
			// 获取自定义视图里的第一个，并获取标题
			var viewtitle = getCustomerViewByIndex(0).title;
			var nullflag = setnullValue();
			if(nullflag) {
				showMsgNotification('提示：‘上市公司标志’或‘特种经营标志’或‘AR客户标志’选择‘否’时，其余相关信息为空!');
			}
			// 第一页（客户基础信息）校验
			if(!qzCombaseInfo.getForm().isValid()) {
				Ext.MessageBox.alert('提示', '校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
				enableSaveBtn();
				return false;
			}
			//第二页（客户识别）校验
			if(!qzComLists.getForm().isValid()) {
				Ext.MessageBox.alert('提示', '校验失败，客户识别信息第二页输入有误或存在漏输入项,请检查输入项');
				enableSaveBtn();
				return false;
			}
			//登记注册号类型
			var regCodeType = qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
			//英文名称
			var enName = Ext.util.Format.trim(qzCombaseInfo.form.findField('EN_NAME').getValue());
			if(regCodeType == '99' && (enName == '' || enName.length == 0)) {
				Ext.MessageBox.alert('提示信息！', '第一页基本信息中的【英文名称】必输');// 弹出提示框
				enableSaveBtn();
				return false;
			}
			// 第一页校验
			if(!validqzComFirstFn(viewtitle)) {
				enableSaveBtn();
				return false;
			}
			// 第二页校验
			if(!validqzComSecondFn()) {
				enableSaveBtn();
				return false;
			}
			// 第四页校验
			if(!validqzComForthFn()) {
				enableSaveBtn();
				return false;
			}
			// 成立日期:给是否2年内新设立企业赋值
			var val = qzCombaseInfo.form.findField('BUILD_DATE').value;
			var buildDate = Date.parse(val);
			var buildDate = new Date(val);// 成立日期
			var nowDate = new Date();
			var tempday = parseInt(Math.abs(nowDate - buildDate) / 1000 / 60 / 60 / 24) + 1;
			if(tempday > 730) {// 获取日期
				qzComLists.form.findField('IS_NEW_CORP').setValue('2');
			} else if(tempday <= 730) {
				qzComLists.form.findField('IS_NEW_CORP').setValue('1');
			} else if(tempday == '' || tempday == null) {
				qzComLists.form.findField('IS_NEW_CORP').setValue('');
			}
			// 第一页：必需要有客户名称、证件类型、证件号码
			var qzCombaseInfodata = qzCombaseInfo.form.getFieldValues(false);
			var firstjson = [];
			custId = qzCombaseInfodata.CUST_ID;
			// var commitFirstData =
			// translateDataKey(qzCombaseInfodata,_app.VIEWCOMMITTRANS);

			custName = qzCombaseInfodata.CUST_NAME;
			var identNo = qzCombaseInfodata.IDENT_NO;
			var identType = qzCombaseInfodata.IDENT_TYPE;

			// Ext.Msg.wait('正在处理，请稍后......', '系统提示');
			// if(viewtitle='新增潜在客户'||viewtitle=='新增信贷临时户'){
			// firstAddSave(custName,identNo,identType);
			// }
			// else{
			// secondAddSave(custId,custName,identNo,identType,viewtitle);
			// }
			var qzComSecondInfodata = qzComLists.form.getFieldValues(false);
			var secondjson = [];
			if(viewtitle != '') {
				firstjson.push(qzCombaseInfodata);
				secondjson.push(qzComSecondInfodata);
				secondAddSave(custId, custName, identNo, identType, viewtitle, firstjson, secondjson);
			}
			// 是否保存后要收起面板
			if(ifhidden == true) {
				hideCurrentView();
				reloadCurrentData();
			}			
		}
	},{
		text:'关闭',
		handler:function(){
            //第一页关闭按钮
		 	enableSaveBtn();
			hideCurrentView();
			reloadCurrentData();
			custId='';
			custName='';
			//window.loadAllData(custId);// 刷新数据
            //window.resetjs(viewtitle); //刷新页面
		}
	}]
});
///////////////////////////////////////////////////////////////发生处罚日期//////////////////////////////
//临时存储发生日期
var tempHappenDateStore=new Ext.data.Store();
var addHappenRecord = new Ext.data.Record.create([
    {name:'HAPPEN_ID'},
    {name:'HAPPEN_CUST_ID'},
    {name:'PENALIZE_TYPE'},
    {name:'PENALIZE_TYPE_ORA'},
    {name:'HAPPEN_DATE'},
    {name:'IS_ADD_FLAG'}
]);

var addHappenStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithComThree!queryHappenDate.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },addHappenRecord)
    
});
var addHappenRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
var addHappenCm = new Ext.grid.ColumnModel([
                                               addrCustInfoRowNumber,
                                               {dataIndex:'PENALIZE_TYPE_ORA',header:'是否发生处罚',width : 120,sortable : true},
                                               {dataIndex:'HAPPEN_DATE',header:'发生日期',width : 120,sortable : true}
                                           ]);
/**
 * 点击新增OR修改时弹出FORM
 */
var mainHappenForm = new Ext.FormPanel({
	frame	   : true,
	autoScroll : true,
	split	   : true,
	items	   : [
        {xtype:'textfield',name:'HAPPEN_ID',hidden:true},
        {xtype:'textfield',name:'HAPPEN_CUST_ID',hidden:true},
        {xtype : 'combo',name : 'PENALIZE_TYPE',id:'penalize_type',hiddenName : 'PENALIZE_TYPE',fieldLabel : '<font color="red">*</font>是否发生处罚',store : comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
			mode		   : 'local',
    		forceSelection	: true,
    		triggerAction	: 'all',
    		anchor		   : '90%',
    		allowBlank		: false,
    		listeners		: {
    			'focus'	: {
    				fn		: function(e) {
    					e.expand();
    					this.doQuery(this.allQuery, true);
    				},
    				buffer	: 200
    			}
    		},
    		readOnly		: true,
    		cls		       : 'x-readOnly'
    	},
    	{xtype:'datefield',name:'HAPPEN_DATE',fieldLabel:'<font color="red">*</font>发生日期',format:'Y-m-d',maxLength:40,anchor : '90%',allowBlank:false}	
    ]
});
/**
 * 点击新增OR修改时弹出WINDOW
 */
var mainHappenWindow = new Ext.Window({
	title	    : '新增OR修改',
	width	    : 500,
	height	    : 320,
	layout	    : 'fit',
	closable	: true,
	closeAction	: 'hide',
	modal	    : true,
	buttonAlign	: 'center',
	items	    : [mainHappenForm],
    buttons	    : [{
		text	: '暂存',
		handler	: function() {
			if(!mainHappenForm.getForm().isValid()) {
				Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
				Ext.getCmp('first_save').disabled = false;
				Ext.getCmp('second_save').disabled = false;
				Ext.getCmp('third_save').disabled = false;
				Ext.getCmp('forth_save').disabled = false;
				Ext.getCmp('fifth_save').disabled = false;
				return false;
			}
			var tempJson = mainHappenForm.getForm().getValues(false);
			var validFlag = false;// 判断类型是否已存在,由于不允许修改类型,故不作与原值比较

            if(mainHappenWindow.title =='修改'){
            	var tempRowData = happenDateGrid.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	addHappenStore.removeAt(addHappenStore.findExact('HAPPEN_ID', tempRowData.HAPPEN_ID));
            	tempRowData.PENALIZE_TYPE = tempJson.PENALIZE_TYPE;
            	tempRowData.PENALIZE_TYPE_ORA = mainHappenForm.getForm().findField('PENALIZE_TYPE').getRawValue();
            	tempRowData.HAPPEN_DATE= tempJson.HAPPEN_DATE;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.ADDR_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	addHappenStore.addSorted(tempRecord);

            }else{
	            var tempRowData = {
	            	HAPPEN_ID : -(new Date().getTime()),
	            	HAPPEN_CUST_ID : custId,
	            	PENALIZE_TYPE : '1',
	            	PENALIZE_TYPE_ORA : mainHappenForm.getForm().findField('PENALIZE_TYPE').getRawValue(),
	            	HAPPEN_DATE:tempJson.HAPPEN_DATE,
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            addHappenStore.addSorted(tempRecord);
            }
            addHappenStore.sort('PENALIZE_TYPE','ASC');
            mainHappenWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            mainHappenWindow.hide();
        }
    }]
});

/**
 * grid按钮
 */
var addHappenTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_2-dt',
        handler: function(){
            if(mainHappenForm.getForm().getEl()){
                mainHappenForm.getForm().getEl().dom.reset();
            }
            Ext.getCmp('penalize_type').setValue('1');
            mainHappenWindow.setTitle('新增');
            mainHappenWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_2-dt',
        handler: function(){
        	var viewtitle=getCustomerViewByIndex(0).title;
            var selectLength = happenDateGrid.getSelectionModel().getSelections().length;
            var selectRecord = happenDateGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
         
            mainHappenWindow.setTitle('修改');
            mainHappenForm.getForm().loadRecord(selectRecord);
            mainHappenWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_2-dt',
        handler: function(){
            var selectLength = happenDateGrid.getSelectionModel().getSelections().length;
            var selectRecord = happenDateGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            addHappenStore.remove(selectRecord);
        }
    }]
});
/**
 *发生处罚日期信息
 */
var happenDateGrid = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: addHappenStore,
    cm : addHappenCm,
    tbar: addHappenTbar,
    viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据是否修改状态修改背景颜色  
			if(record.data.IS_ADD_FLAG=='0'){//修改过
			  	return 'my_row_set_blue';
		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
		  		return 'my_row_set_red';
		  	}
		}
	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
addrPartnerStore.on('beforeload',function(){
	addrPartnerStore.baseParams = {
		custId : custId
	};
});
/**
 * 查询客户信息第二页基本信息
 */
var qzComListsStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithCom!queryComsecond.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, ['AR_CUST_FLAG','AR_CUST_TYPE','INOUT_FLAG',
	    'LNCUSTP','IF_ORG_SUB_TYPE','ORG_SUB_TYPE','ORG_TYPE','COM_SP_BUSINESS',
	    'KEY_CUST_ID','IS_LISTED_CORP','SCIENTIFIC_ENT','IS_NOT_LOCAL_ENT','IS_STEEL_ENT','IS_FAX_TRANS_CUST',
	    'IS_MATERIAL_RISK','IS_RURAL','IS_SCIENCE_TECH','ENERGY_SAVING','IS_TAIWAN_CORP','IS_NEW_CORP',
	    'SHIPPING_IND','ENVIRO_PENALTIES','IS_HIGH_POLLUTE',
	    'ISSUE_STOCK_ID','STOCK_CUST_ID','STOCK_CODE','MARKET_PLACE',
	    'COM_IDENT_TYPE','COM_SP_ORG','COM_SP_REG_DATE','COM_SP_CODE',
	    'COM_SP_SITU','COM_SP_END_DATE',  
	    'SCIENCE_CUST_ID','IF_SCIENCE','SCIENTIFIC_TERM','SCIENTIFIC_TYPE','SCIENTIFIC_RANGE','SCIENTIFIC_RATE'
])
});

/**
 * 查询对公的第二页信息
 */
var SearchqzComListsFn = function(custId){
	if(qzComLists.getForm().getEl()){
		qzComLists.getForm().getEl().dom.reset();
     // qzComLists.getForm().reset();
	}else{
		qzComLists.getForm().reset();
	}
	qzComListsStore.load({//加载数据集获取客户信息第二页基本信息
		params : {
			custId : custId
		},
		callback:function(){
			window.readyCount++;
			if(qzComListsStore.getCount()!=0){
				qzComLists.getForm().loadRecord(qzComListsStore.getAt(0));
				//add by liuming 20170817
//				setReadOrNoRead(qzComLists,'IS_FAX_TRANS_CUST',qzComLists.getForm().findField('IS_FAX_TRANS_CUST').getValue());
//				setReadOrNoRead(qzComLists,'IS_TAIWAN_CORP',qzComLists.getForm().findField('IS_TAIWAN_CORP').getValue());
//				setReadOrNoRead(qzComLists,'LNCUSTP',qzComLists.getForm().findField('LNCUSTP').getValue());
//				setReadOrNoRead(qzComLists,'ORG_SUB_TYPE',qzComLists.getForm().findField('ORG_SUB_TYPE').getValue());
//				setReadOrNoRead(qzComLists,'IF_ORG_SUB_TYPE',qzComLists.getForm().findField('IF_ORG_SUB_TYPE').getValue());
//				setReadOrNoRead(qzComLists,'ORG_TYPE',qzComLists.getForm().findField('ORG_TYPE').getValue());
			}
		}
	});
};
/**
 * 第二页（客户识别）
 */
var qzComLists = new Ext.form.FormPanel({
	title : '第二页（客户识别）',
	frame : true,
	autoScroll : true,
	buttonAlign : "center",
	 labelWidth : 140,
	items : [{
	    items:[{
			xtype : 'fieldset',
			title : '上市信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[{xtype:'combo',id:'p2listedId',anchor:'90%',readOnly:false,fieldLabel:'上市公司标志',name:'IS_LISTED_CORP',store:isSteelEntStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',
//							emptyText : '未知',
							listeners:{
								select:function(combo,record){
									 var ownerForm=this;
				        			 while(ownerForm && !ownerForm.form){
				        				 ownerForm=ownerForm.ownerCt;
				        			 }
				         			var value = combo.value;
				         			if(value=='1'){// 选“是”时，股票代码和上市地显示
				         				ownerForm.form.findField('STOCK_CODE').setVisible(true);
				         				ownerForm.form.findField('STOCK_CODE').allowBlank=false;
				         				if(ownerForm.form.findField('STOCK_CODE').label!=undefined){
					         				ownerForm.form.findField('STOCK_CODE').label.dom.innerHTML='<font color="red">*</font>股票代码:';		
				         				}else{
					         				ownerForm.form.findField('STOCK_CODE').fieldLabel='<font color="red">*</font>股票代码';
				         				}
				         				ownerForm.form.findField('MARKET_PLACE').setVisible(true);
				         				ownerForm.form.findField('MARKET_PLACE').allowBlank=false;
				         				if(ownerForm.form.findField('MARKET_PLACE').label!=undefined){
					         				ownerForm.form.findField('MARKET_PLACE').label.dom.innerHTML='<font color="red">*</font>上市地:';		
				         				}else{
					         				ownerForm.form.findField('MARKET_PLACE').fieldLabel='<font color="red">*</font>上市地';
				         				}
				         			}
				         			else{
				         				ownerForm.form.findField('STOCK_CODE').setValue('');
				         				ownerForm.form.findField('STOCK_CODE').setVisible(false);
				         				ownerForm.form.findField('STOCK_CODE').allowBlank=true;
				         				if(ownerForm.form.findField('STOCK_CODE').label!=undefined){
					         				ownerForm.form.findField('STOCK_CODE').label.dom.innerHTML='股票代码:';		
				         				}else{
					         				ownerForm.form.findField('STOCK_CODE').fieldLabel='股票代码';
				         				}
				         				ownerForm.form.findField('MARKET_PLACE').setValue('');
				         				ownerForm.form.findField('MARKET_PLACE').setVisible(false);
				         				ownerForm.form.findField('MARKET_PLACE').allowBlank=true;
				         				if(ownerForm.form.findField('MARKET_PLACE').label!=undefined){
					         				ownerForm.form.findField('MARKET_PLACE').label.dom.innerHTML='上市地:';		
				         				}else{
					         				ownerForm.form.findField('MARKET_PLACE').fieldLabel='上市地';
				         				}
				         			}
				         		},'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }
							}
					,validator:function(){
								var ownerForm=this;
			        			 while(ownerForm && !ownerForm.form){
			        				 ownerForm=ownerForm.ownerCt;
			        			 }
			         			var value = this.value;
								if(value == '是' || value == '1'){
									ownerForm.form.findField('STOCK_CODE').setVisible(true);
			         				ownerForm.form.findField('STOCK_CODE').allowBlank=false;
			         				if(ownerForm.form.findField('STOCK_CODE').label!=undefined){
				         				ownerForm.form.findField('STOCK_CODE').label.dom.innerHTML='<font color="red">*</font>股票代码:';		
			         				}else{
				         				ownerForm.form.findField('STOCK_CODE').fieldLabel='<font color="red">*</font>股票代码';
			         				}
			         				ownerForm.form.findField('MARKET_PLACE').setVisible(true);
			         				ownerForm.form.findField('MARKET_PLACE').allowBlank=false;
			         				if(ownerForm.form.findField('MARKET_PLACE').label!=undefined){
				         				ownerForm.form.findField('MARKET_PLACE').label.dom.innerHTML='<font color="red">*</font>上市地:';		
			         				}else{
				         				ownerForm.form.findField('MARKET_PLACE').fieldLabel='<font color="red">*</font>上市地';
			         				}
								}else{
									ownerForm.form.findField('STOCK_CODE').setValue('');
			         				ownerForm.form.findField('STOCK_CODE').setVisible(false);
			         				ownerForm.form.findField('STOCK_CODE').allowBlank=true;
			         				if(ownerForm.form.findField('STOCK_CODE').label!=undefined){
				         				ownerForm.form.findField('STOCK_CODE').label.dom.innerHTML='股票代码:';		
			         				}else{
				         				ownerForm.form.findField('STOCK_CODE').fieldLabel='股票代码';
			         				}
			         				ownerForm.form.findField('MARKET_PLACE').setValue('');
			         				ownerForm.form.findField('MARKET_PLACE').setVisible(false);
			         				ownerForm.form.findField('MARKET_PLACE').allowBlank=true;
			         				if(ownerForm.form.findField('MARKET_PLACE').label!=undefined){
				         				ownerForm.form.findField('MARKET_PLACE').label.dom.innerHTML='上市地:';		
			         				}else{
				         				ownerForm.form.findField('MARKET_PLACE').fieldLabel='上市地';
			         				}
								}
								return true;
							}
					},
						{xtype:'textfield',id:'p2code',anchor:'90%',readOnly:false,fieldLabel:'股票代码',name:'STOCK_CODE',hidden:true}]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[ {xtype:'combo',id:'p2corpadd',anchor:'90%',readOnly:false,fieldLabel:'上市地',name:'MARKET_PLACE',store:marketplaceStore,resizable : true,valueField : 'key',displayField : 'value',
				    	   mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',hidden:true,listeners:{
				    		   'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }
				    	   }}]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '企业类型信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'combo',id:'p2lncusp',anchor:'90%',readOnly:false,fieldLabel:'企业类型',name:'LNCUSTP',store:comStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',
//						emptyText : '未知',
						listeners:{
							select:function(combo,record){
								 var ownerForm=this;
			        			 while(ownerForm && !ownerForm.form){
			        				 ownerForm=ownerForm.ownerCt;
			        			 }
			         			var value = combo.value;
			         			var ifzm=ownerForm.form.findField('IF_ORG_SUB_TYPE').getValue();//是否自贸区
			         			if(value=='035' ||value=='054'){// 选“保税区内”
		    			        	if(ifzm=='1'){//是
		    			        		var _store1 = findLookupByType('CUSTTYPEZM1');
				         				_store1.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store1);
		    			        	}else if(ifzm=='2'){//否
		    			        		var _store1 = findLookupByType('CUSTTYPENZM1');
				         				_store1.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store1);
		    			        	}else{
		    			        		var _store = findLookupByType('CUSTTYPE');
				         				_store.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store);
		    			        	}
		    			        	if(value=='035'){//三资企业
		    			        		ownerForm.form.findField('ORG_TYPE').allowBlank=false;
		    			        		if(ownerForm.form.findField('ORG_TYPE').label!=undefined){
					         				ownerForm.form.findField('ORG_TYPE').label.dom.innerHTML='<font color="red">*</font>合资类型:';		
				         				}else{
					         				ownerForm.form.findField('ORG_TYPE').fieldLabel='<font color="red">*</font>合资类型';
				         				}
		    			        	}  
		    			        	else{//中资企业
		    			        		ownerForm.form.findField('ORG_TYPE').allowBlank=true;
		    			        		if(ownerForm.form.findField('ORG_TYPE').label!=undefined){
					         				ownerForm.form.findField('ORG_TYPE').label.dom.innerHTML='合资类型:';		
				         				}else{
					         				ownerForm.form.findField('ORG_TYPE').fieldLabel='合资类型';
				         				}
		    			        	}
		    			        	ownerForm.form.findField('ORG_SUB_TYPE').setValue('');
		    			        	ownerForm.form.findField('ORG_SUB_TYPE').allowBlank=false;
		    			        	if(ownerForm.form.findField('ORG_SUB_TYPE').label!=undefined){
				         				ownerForm.form.findField('ORG_SUB_TYPE').label.dom.innerHTML='<font color="red">*</font>特殊监管区:';		
			         				}else{
				         				ownerForm.form.findField('ORG_SUB_TYPE').fieldLabel='<font color="red">*</font>特殊监管区';
			         				}
			         			}
			         			else if(value=='031' ||value=='032'){// 031:境内中资保税区外
			         				if(ifzm=='1'){//是
		    			        		var _store1 = findLookupByType('CUSTTYPEZM2');
				         				_store1.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store1);
		    			        	}else if(ifzm=='2'){//否
		    			        		var _store1 = findLookupByType('CUSTTYPENZM2');
				         				_store1.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store1);
		    			        	}else{
		    			        		var _store = findLookupByType('CUSTTYPE2');
				         				_store.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store);
		    			        	}
		    			        	if(value=='032'){// 境内三资（外）
		    			        		ownerForm.form.findField('ORG_TYPE').allowBlank=false;
		    			        		if(ownerForm.form.findField('ORG_TYPE').label!=undefined){
					         				ownerForm.form.findField('ORG_TYPE').label.dom.innerHTML='<font color="red">*</font>合资类型:';		
				         				}else{
					         				ownerForm.form.findField('ORG_TYPE').fieldLabel='<font color="red">*</font>合资类型';
				         				}
		    			        	}
		    			        	else{
		    			        		ownerForm.form.findField('ORG_TYPE').allowBlank=true;
		    			        		if(ownerForm.form.findField('ORG_TYPE').label!=undefined){
					         				ownerForm.form.findField('ORG_TYPE').label.dom.innerHTML='合资类型:';		
				         				}else{
					         				ownerForm.form.findField('ORG_TYPE').fieldLabel='合资类型';
				         				}
		    			        	}
		    			        	ownerForm.form.findField('ORG_SUB_TYPE').setValue('');
		    			        	ownerForm.form.findField('ORG_SUB_TYPE').allowBlank=false;
		    			        	if(ownerForm.form.findField('ORG_SUB_TYPE').label!=undefined){
				         				ownerForm.form.findField('ORG_SUB_TYPE').label.dom.innerHTML='<font color="red">*</font>特殊监管区:';		
			         				}else{
				         				ownerForm.form.findField('ORG_SUB_TYPE').fieldLabel='<font color="red">*</font>特殊监管区';
			         				}
			         			}
			         			else{
			         				var _store3 = findLookupByType('CUSTTYPEALL');
			         				_store3.load();
			         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store3);
		    			        	ownerForm.form.findField('ORG_SUB_TYPE').setValue('');
		    			        	ownerForm.form.findField('ORG_SUB_TYPE').allowBlank=true;
		    			        	if(ownerForm.form.findField('ORG_SUB_TYPE').label!=undefined){
				         				ownerForm.form.findField('ORG_SUB_TYPE').label.dom.innerHTML='特殊监管区:';		
			         				}else{
				         				ownerForm.form.findField('ORG_SUB_TYPE').fieldLabel='特殊监管区';
			         				}
		    			        	
			         		      	ownerForm.form.findField('ORG_TYPE').allowBlank=true;
		    			        	if(ownerForm.form.findField('ORG_TYPE').label!=undefined){
				         				ownerForm.form.findField('ORG_TYPE').label.dom.innerHTML='合资类型:';		
			         				}else{
				         				ownerForm.form.findField('ORG_TYPE').fieldLabel='合资类型';
			         				}
			         			}
			         		},'focus': {  
		    			        fn: function(e) {  
		    			            e.expand();  
		    			            this.doQuery(this.allQuery, true);  
		    			        },  
		    			        buffer:200  
		    			 }
						}
					  	},
						{xtype:'combo',id:'p2subId',anchor:'90%',readOnly:false,fieldLabel:'是否自贸区',name:'IF_ORG_SUB_TYPE',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',
//						emptyText:'未知',
						listeners:{
							select:function(combo,record){
								 var ownerForm=this;
			        			 while(ownerForm && !ownerForm.form){
			        				 ownerForm=ownerForm.ownerCt;
			        			 }
			         			var value = combo.value;
			         			var lncustp=ownerForm.form.findField('LNCUSTP').getValue();//企业类型
			         			if(value=='1'){//是
			         				//1、企业类型为“保税区内”
			         				if(lncustp=='035' ||lncustp=='054'){
			         					var _store4 = findLookupByType('CUSTTYPEZM1');
				         				_store4.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store4);
			         				}
			         				//2、企业类型为“保税区外”
			         				else if(lncustp=='031' ||lncustp=='032'){
			         					var _store5 = findLookupByType('CUSTTYPEZM2');
				         				_store5.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store5);
				         			//3、企业类型其他值
			         				}else{
			         					var _store6 = findLookupByType('CUSTTYPEALL');
				         				_store6.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store6);
			         				}
			         			}
			         			else if(value=='2'){//否
			         				//1、企业类型为“保税区内”
			         				if(lncustp=='035' ||lncustp=='054'){
			         					var _store4 = findLookupByType('CUSTTYPENZM1');
				         				_store4.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store4);
			         				}
			         				//2、企业类型为“保税区外”
			         				else if(lncustp=='031' ||lncustp=='032'){
			         					var _store5 = findLookupByType('CUSTTYPENZM2');
				         				_store5.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store5);
				         			//3、企业类型其他值
			         				}else{
			         					var _store6 = findLookupByType('CUSTTYPEALL');
				         				_store6.load();
				         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store6);
			         				}
			         			}else{
			         				var _store3 = findLookupByType('CUSTTYPEALL');
			         				_store3.load();
			         				ownerForm.form.findField('ORG_SUB_TYPE').bindStore(_store3);
			         			}
			         			ownerForm.form.findField('ORG_SUB_TYPE').setValue('');
	    			        	ownerForm.form.findField('ORG_SUB_TYPE').allowBlank=true;
	    			        	if(ownerForm.form.findField('ORG_SUB_TYPE').label!=undefined){
			         				ownerForm.form.findField('ORG_SUB_TYPE').label.dom.innerHTML='特殊监管区:';		
		         				}else{
			         				ownerForm.form.findField('ORG_SUB_TYPE').fieldLabel='特殊监管区';
		         				}
			         		},'focus': {  
		    			        fn: function(e) {  
		    			            e.expand();  
		    			            this.doQuery(this.allQuery, true);  
		    			        },  
		    			        buffer:200  
		    			 }
						}
						}]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'combo',id:'p2orgsubId',anchor:'90%',readOnly:false,fieldLabel:'特殊监管区',name:'ORG_SUB_TYPE',store:orgSubTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   mode : 'local',forceSelection : true,triggerAction : 'all',
//					    	   emptyText:'未知',
					    	   listeners:{
									select:function(combo,record){
										 var ownerForm=this;
					        			 while(ownerForm && !ownerForm.form){
					        				 ownerForm=ownerForm.ownerCt;
					        			 }
					         			var value = combo.value;
					         			var lncustp=ownerForm.form.findField('LNCUSTP').getValue();
					         			if(value>='1'&&value<='D'||value=='L'){
					         				Ext.getCmp('p2subId').setValue(2);// 是
					         			}
					         			else if(value>='E'&&value<='I'||value=='M'){// 否
					         				Ext.getCmp('p2subId').setValue(1);
					         			}
					         			else{
					         				Ext.getCmp('p2subId').setValue(9);
					         			}
					         		},'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
							} 	
					       } ,
							{xtype:'combo',anchor:'90%',id:'p2orgtype',readOnly:false,maxLength:30,fieldLabel:'合资类型',name:'ORG_TYPE',store:orgTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							    	   mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',
//							    	   emptyText : '未知',
							    	   listeners:{
							    		   'focus': {  
					    			        fn: function(e) {  
					    			            e.expand();  
					    			            this.doQuery(this.allQuery, true);  
					    			        },  
					    			        buffer:200  
					    			 }} 	
					  }]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '特种经营信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{ 
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'combo',id:'p2spId',anchor:'90%',readOnly:false,fieldLabel:'特种经营标示',name:'COM_SP_BUSINESS',store:isSteelEntStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   mode : 'local',forceSelection : true,triggerAction : 'all',
//					    	   emptyText:'未知',
					    	   listeners:{
									select:function(combo,record){
										 var ownerForm=this;
					        			 while(ownerForm && !ownerForm.form){
					        				 ownerForm=ownerForm.ownerCt;
					        			 }
					         			var value = combo.value;
					         	
					         			if(value=='1'){// 是
					         				ownerForm.form.findField('COM_SP_ORG').setVisible(true);
					         				ownerForm.form.findField('COM_SP_REG_DATE').setVisible(true);
					         				ownerForm.form.findField('COM_SP_SITU').setVisible(true);
					         				ownerForm.form.findField('COM_SP_CODE').setVisible(true);
					         				ownerForm.form.findField('COM_SP_END_DATE').setVisible(true);
					         				
					         				ownerForm.form.findField('COM_SP_CODE').allowBlank=false;
				    			        	if(ownerForm.form.findField('COM_SP_CODE').label!=undefined){
						         				ownerForm.form.findField('COM_SP_CODE').label.dom.innerHTML='<font color="red">*</font>特种经营许可证编号:';		
					         				}else{
						         				ownerForm.form.findField('COM_SP_CODE').fieldLabel='<font color="red">*</font>特种经营许可证编号';
					         				}
				    			        	ownerForm.form.findField('COM_SP_END_DATE').allowBlank=false;
				    			        	if(ownerForm.form.findField('COM_SP_END_DATE').label!=undefined){
						         				ownerForm.form.findField('COM_SP_END_DATE').label.dom.innerHTML='<font color="red">*</font>特种经营到期日期:';		
					         				}else{
						         				ownerForm.form.findField('COM_SP_END_DATE').fieldLabel='<font color="red">*</font>特种经营到期日期';
					         				}
					         				
					         			}
					         			else {// 否
					         				ownerForm.form.findField('COM_SP_ORG').setValue('');
					         				ownerForm.form.findField('COM_SP_REG_DATE').setValue('');
					         				ownerForm.form.findField('COM_SP_SITU').setValue('');
					         				ownerForm.form.findField('COM_SP_CODE').setValue('');
					         				ownerForm.form.findField('COM_SP_END_DATE').setValue('');
					         				ownerForm.form.findField('COM_SP_ORG').setVisible(false);
					         				ownerForm.form.findField('COM_SP_REG_DATE').setVisible(false);
					         				ownerForm.form.findField('COM_SP_SITU').setVisible(false);
					         				ownerForm.form.findField('COM_SP_CODE').setVisible(false);
					         				ownerForm.form.findField('COM_SP_END_DATE').setVisible(false);
					         				
					         				ownerForm.form.findField('COM_SP_CODE').allowBlank=true;
				    			        	ownerForm.form.findField('COM_SP_CODE').label.dom.innerHTML='特种经营许可证编号:';
				    			        	if(ownerForm.form.findField('COM_SP_CODE').label!=undefined){
						         				ownerForm.form.findField('COM_SP_CODE').label.dom.innerHTML='特种经营许可证编号:';		
					         				}else{
						         				ownerForm.form.findField('COM_SP_CODE').fieldLabel='特种经营许可证编号';
					         				}
				    			        	ownerForm.form.findField('COM_SP_END_DATE').allowBlank=true;
				    			        	if(ownerForm.form.findField('COM_SP_END_DATE').label!=undefined){
						         				ownerForm.form.findField('COM_SP_END_DATE').label.dom.innerHTML='特种经营到期日期:';		
					         				}else{
						         				ownerForm.form.findField('COM_SP_END_DATE').fieldLabel='特种经营到期日期';
					         				}
					         			}
					         		},'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
							}
					       ,validator:function(){
								var ownerForm=this;
			        			 while(ownerForm && !ownerForm.form){
			        				 ownerForm=ownerForm.ownerCt;
			        			 }
			         			var value = this.value;
								if(value == '是' || value == '1'){
									ownerForm.form.findField('COM_SP_ORG').setVisible(true);
			         				ownerForm.form.findField('COM_SP_REG_DATE').setVisible(true);
			         				ownerForm.form.findField('COM_SP_SITU').setVisible(true);
			         				ownerForm.form.findField('COM_SP_CODE').setVisible(true);
			         				ownerForm.form.findField('COM_SP_END_DATE').setVisible(true);
			         				
			         				ownerForm.form.findField('COM_SP_CODE').allowBlank=false;
		    			        	if(ownerForm.form.findField('COM_SP_CODE').label!=undefined){
				         				ownerForm.form.findField('COM_SP_CODE').label.dom.innerHTML='<font color="red">*</font>特种经营许可证编号:';		
			         				}else{
				         				ownerForm.form.findField('COM_SP_CODE').fieldLabel='<font color="red">*</font>特种经营许可证编号';
			         				}
		    			        	ownerForm.form.findField('COM_SP_END_DATE').allowBlank=false;
		    			         	if(ownerForm.form.findField('COM_SP_END_DATE').label!=undefined){
				         				ownerForm.form.findField('COM_SP_END_DATE').label.dom.innerHTML='<font color="red">*</font>特种经营到期日期:';		
			         				}else{
				         				ownerForm.form.findField('COM_SP_END_DATE').fieldLabel='<font color="red">*</font>特种经营到期日期';
			         				}
								}else{
									ownerForm.form.findField('COM_SP_ORG').setValue('');
			         				ownerForm.form.findField('COM_SP_REG_DATE').setValue('');
			         				ownerForm.form.findField('COM_SP_SITU').setValue('');
			         				ownerForm.form.findField('COM_SP_CODE').setValue('');
			         				ownerForm.form.findField('COM_SP_END_DATE').setValue('');
			         				ownerForm.form.findField('COM_SP_ORG').setVisible(false);
			         				ownerForm.form.findField('COM_SP_REG_DATE').setVisible(false);
			         				ownerForm.form.findField('COM_SP_SITU').setVisible(false);
			         				ownerForm.form.findField('COM_SP_CODE').setVisible(false);
			         				ownerForm.form.findField('COM_SP_END_DATE').setVisible(false);
			         				
			         				ownerForm.form.findField('COM_SP_CODE').allowBlank=true;
		    			        	if(ownerForm.form.findField('COM_SP_CODE').label!=undefined){
				         				ownerForm.form.findField('COM_SP_CODE').label.dom.innerHTML='特种经营许可证编号:';		
			         				}else{
				         				ownerForm.form.findField('COM_SP_CODE').fieldLabel='特种经营许可证编号';
			         				}
		    			        	ownerForm.form.findField('COM_SP_END_DATE').allowBlank=true;
		    			        	if(ownerForm.form.findField('COM_SP_END_DATE').label!=undefined){
				         				ownerForm.form.findField('COM_SP_END_DATE').label.dom.innerHTML='特种经营到期日期:';		
			         				}else{
				         				ownerForm.form.findField('COM_SP_END_DATE').fieldLabel='特种经营到期日期';
			         				}
								}
							}
					       } ,
					       {xtype:'textfield',id:'p2sporg',anchor:'90%',readOnly:false,fieldLabel:'特种经营颁发机关',name:'COM_SP_ORG',hidden:true},
					       {xtype:'datefield',id:'p2spregdate',anchor:'90%',readOnly:false,fieldLabel:'特种经营登记日期',name:'COM_SP_REG_DATE',format:'Y-m-d',hidden:true}
					      
					]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[ 
					       {xtype:'textfield',id:'p2spcode',anchor:'90%',readOnly:false,fieldLabel:'特种经营许可证编号',name:'COM_SP_CODE',hidden:true},
					       {xtype:'textfield',id:'p2spsitu',anchor:'90%',readOnly:false,fieldLabel:'特种经营情况',name:'COM_SP_SITU',hidden:true},
					       {xtype:'datefield',id:'p2spend',anchor:'90%',readOnly:false,fieldLabel:'特种经营到期日期',name:'COM_SP_END_DATE',format:'Y-m-d',hidden:true}
					       ]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : 'AR客户信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[{xtype:'combo',id:'p2arflagId',anchor:'90%',readOnly:false,fieldLabel:'AR客户标志(CSPS)',name:'AR_CUST_FLAG',store:arCustFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',
//						emptyText:'未知',
						listeners:{
							select:function(combo,record){
								 var ownerForm=this;
			        			 while(ownerForm && !ownerForm.form){
			        				 ownerForm=ownerForm.ownerCt;
			        			 }
			         			var value = combo.value;
			         	
			         			if(value=='1'){// 是
			         				ownerForm.form.findField('AR_CUST_TYPE').setVisible(true);
			         				ownerForm.form.findField('AR_CUST_TYPE').allowBlank=false;
		    			        	if(ownerForm.form.findField('AR_CUST_TYPE').label!=undefined){
				         				ownerForm.form.findField('AR_CUST_TYPE').label.dom.innerHTML='<font color="red">*</font>AR客户类型(CSPS):';		
			         				}else{
				         				ownerForm.form.findField('AR_CUST_TYPE').fieldLabel='<font color="red">*</font>AR客户类型(CSPS)';
			         				}
			         			}
			         			else {// 否
			         				Ext.getCmp('p2artypeId').setValue('');
			         				ownerForm.form.findField('AR_CUST_TYPE').setVisible(false);
			         				ownerForm.form.findField('AR_CUST_TYPE').allowBlank=true;
		    			        	if(ownerForm.form.findField('AR_CUST_TYPE').label!=undefined){
				         				ownerForm.form.findField('AR_CUST_TYPE').label.dom.innerHTML='AR客户类型(CSPS):';		
			         				}else{
				         				ownerForm.form.findField('AR_CUST_TYPE').fieldLabel='AR客户类型(CSPS)';
			         				}
			         			}
			         		},'focus': {  
		    			        fn: function(e) {  
		    			            e.expand();  
		    			            this.doQuery(this.allQuery, true);  
		    			        },  
		    			        buffer:200  
		    			 }
					}
					,validator:function(){
						var ownerForm=this;
	        			 while(ownerForm && !ownerForm.form){
	        				 ownerForm=ownerForm.ownerCt;
	        			 }
	         			var value = this.value;
						if(value == '是' || value == '1'){
							ownerForm.form.findField('AR_CUST_TYPE').setVisible(true);
	         				ownerForm.form.findField('AR_CUST_TYPE').allowBlank=false;
    			         	if(ownerForm.form.findField('AR_CUST_TYPE').label!=undefined){
		         				ownerForm.form.findField('AR_CUST_TYPE').label.dom.innerHTML='<font color="red">*</font>AR客户类型(CSPS):';		
	         				}else{
		         				ownerForm.form.findField('AR_CUST_TYPE').fieldLabel='<font color="red">*</font>AR客户类型(CSPS)';
	         				}
						}else{
							Ext.getCmp('p2artypeId').setValue('');
	         				ownerForm.form.findField('AR_CUST_TYPE').setVisible(false);
	         				ownerForm.form.findField('AR_CUST_TYPE').allowBlank=true;
    			         	if(ownerForm.form.findField('AR_CUST_TYPE').label!=undefined){
		         				ownerForm.form.findField('AR_CUST_TYPE').label.dom.innerHTML='AR客户类型(CSPS):';		
	         				}else{
		         				ownerForm.form.findField('AR_CUST_TYPE').fieldLabel='AR客户类型(CSPS)';
	         				}
						}
						return true;
					  }
					} ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[{xtype:'combo',id:'p2artypeId',anchor:'90%',readOnly:false,fieldLabel:'AR客户类型(CSPS)',name:'AR_CUST_TYPE',hiddenName:'AR_CUST_TYPE',store:arCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',
//						emptyText : '未知',
						listeners:{
							'focus': {  
		    			        fn: function(e) {  
		    			            e.expand();  
		    			            this.doQuery(this.allQuery, true); 
		    			        },  
		    			        buffer:200  
		    			 },'select':function(combo,record){
		    				 var ownerForm=this;
		        			 while(ownerForm && !ownerForm.form){
		        				 ownerForm=ownerForm.ownerCt;
		        			 }
		         			var valueFlag =ownerForm.form.findField('AR_CUST_FLAG').getValue();
		         			var valueType = combo.value;
		         			if(valueFlag=='' || valueFlag=='9'){// AR客户标志(CSPS)为空或‘未知’
		         				  showMsgNotification('提示：‘AR客户标志(CSPS)’为空或者未知时，需先选择‘AR客户标志(CSPS)’!');
		         			}		    				 
		    			 }
						}}]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '科技型企业信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			labelWidth : 140,
			items:[{
				layout:'column',
				items:[{
					columnWidth:0.5,  
					layout:'form',
					items:[
					       {xtype:'combo',id:'p2sciencerange',anchor:'90%',readOnly:false,fieldLabel:'产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围',name:'SCIENTIFIC_RANGE',store:scienceRangeStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   mode : 'local',forceSelection : true,triggerAction : 'all',hidden:false,
					    	   listeners:{
					    		   select:function(){
					    			   var v = this.getValue();// 产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围
					    			   var ownerForm=this;
					    			   while(ownerForm && !ownerForm.form){
					    				   ownerForm=ownerForm.ownerCt;
					    			   }
					    			   if(ownerForm && ownerForm.form){	
					    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
					    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）
					    				   var type=ownerForm.form.findField('SCIENTIFIC_TYPE').getValue();// 4.企业类型(大中小微)
					    				   if(v=='99'){// 非属相关企业
					    				   /**
											 * 如内容选择为"非属相关企业"时，则后续问题不需回答，客户不属于科技型企业
											 */  
					    					   ownerForm.form.findField('SCIENTIFIC_TYPE').setValue('');// 4.企业类型(大中小微)
					    					   ownerForm.form.findField('SCIENTIFIC_TERM').setValue('');// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
					    					   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
					    					   
					    					   ownerForm.form.findField('SCIENTIFIC_TYPE').setVisible(false);// 4.企业类型(大中小微)
					    					   ownerForm.form.findField('SCIENTIFIC_TERM').setVisible(false);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
					    					   
					    					   ownerForm.form.findField('SCIENTIFIC_TYPE').allowBlank=true;// 4.企业类型(大中小微)
					    					   ownerForm.form.findField('SCIENTIFIC_TERM').allowBlank=true;// 5.
					    					   
					    						if(ownerForm.form.findField('SCIENTIFIC_TYPE').label!=undefined){
							         				ownerForm.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='企业类型(大中小微):';		
						         				}else{
							         				ownerForm.form.findField('SCIENTIFIC_TYPE').fieldLabel='企业类型(大中小微)';
						         				}
					    					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
							         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
						         				}else{
							         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
						         				}
					    				   }
					    				   else{
					    					   if(rate>=3 &&ifnew=='1'){// 满足1.2.3
					    						   ownerForm.form.findField('SCIENTIFIC_TYPE').setVisible(true);// 4.企业类型(大中小微)
						    					   ownerForm.form.findField('SCIENTIFIC_TYPE').allowBlank=false;// 4.企业类型(大中小微)
						    					   if(ownerForm.form.findField('SCIENTIFIC_TYPE').label!=undefined){
								         				ownerForm.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='<font color="red">*</font>企业类型(大中小微):';		
							         				}else{
								         				ownerForm.form.findField('SCIENTIFIC_TYPE').fieldLabel='<font color="red">*</font>企业类型(大中小微)';
							         				}
						    					 if(type!='01'&&type!=''){// 当企业类型不是“大”时，5.才是必输
						    						  ownerForm.form.findField('SCIENTIFIC_TERM').allowBlank=false;// 5.
							    					   ownerForm.form.findField('SCIENTIFIC_TERM').setVisible(true);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
							    					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
									         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
								         				}else{
									         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
								         				}
						    					 }
					    					   }  						    				  
					    				   }  
					    			   }						
					    		   },
					    		   blur:function(){					    			   
					    			   var v = this.getValue();// 产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围
					    			   var ownerForm=this;
					    			   while(ownerForm && !ownerForm.form){
					    				   ownerForm=ownerForm.ownerCt;
					    			   }
					    			   if(ownerForm && ownerForm.form){
                                            // ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');//科技类型
					    				   /**
											 * 一、[科技型企业]：
											 * 同时满足以下条件（1.2.3）的企业，（4.5）为判断科技型企业类型的依据
											 * 1.产品（或服务）属于《国家重点支持的高新技术领域》的范围；
											 * 2.企业当年研究开发经费（技术开发费）占企业总收入的3%以上；
											 * 3.企业有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，有专门从事研发的部门或机构。
											 * 4.企业类型(大中小微)选择大，即为大型；选择中小微，即为中小 ；
											 * 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件，在中小微的前提下，若不选择“未符合任何条件”，则为科创企业
											 */
					    				   var range=v;
					    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
					    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）
					    				   var type=ownerForm.form.findField('SCIENTIFIC_TYPE').getValue();// 4.大型
					    				   var term=ownerForm.form.findField('SCIENTIFIC_TERM').getValue();// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
					    				   if(range!='99'&&rate>='3' &&ifnew=='1' ){// 满足1.2.3.
					    					   if(type=='01'){// 大型
					    						   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('01');// 1.科技型类型大型
					    					   }else if(type!='01'&&type!=''){// 中小型
					    						   if(term!='04'&&term!==''){
					    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('03');// 2.科创型
					    						   }
					    						   else{
					    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('02');// 3.科技型类型中小型
					    						   }
					    					   }   
					    				   }
					    				   
					    			   }						
					    		   },
					    		   'focus': {  
					    			   fn: function(e) {  
					    				   e.expand();  
					    				   this.doQuery(this.allQuery, true);  
					    			   },  
					    			   buffer:200  
					    	}}},
							 {xtype:'combo',id:'p2scienceorg',anchor:'90%',readOnly:false,fieldLabel:'企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）',name:'IF_SCIENCE',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   	mode : 'local',forceSelection : true,triggerAction : 'all',hidden:false,
					    	   	listeners:{
					    	   	 select:function(){
					    			   var v = this.getValue();// 产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围
					    			   var ownerForm=this;
					    			   while(ownerForm && !ownerForm.form){
					    				   ownerForm=ownerForm.ownerCt;
					    			   }
					    			   if(ownerForm && ownerForm.form){	
					    				   var range=ownerForm.form.findField('SCIENTIFIC_RANGE').getValue();// 1.范围
					    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
					    				   var type=ownerForm.form.findField('SCIENTIFIC_TYPE').getValue();// 4.企业类型(大中小微)
					    				   if(v!='1'){// 否
					    				   /**
											 * 如内容选择为"非属相关企业"时，则后续问题不需回答，客户不属于科技型企业
											 */  
					    					   ownerForm.form.findField('SCIENTIFIC_TYPE').setValue('');// 4.企业类型(大中小微)
					    					   ownerForm.form.findField('SCIENTIFIC_TERM').setValue('');// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
					    					   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
					    					   
					    					   ownerForm.form.findField('SCIENTIFIC_TYPE').setVisible(false);// 4.企业类型(大中小微)
					    					   ownerForm.form.findField('SCIENTIFIC_TERM').setVisible(false);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
					    					   
					    					   ownerForm.form.findField('SCIENTIFIC_TYPE').allowBlank=true;// 4.企业类型(大中小微)
					    					   ownerForm.form.findField('SCIENTIFIC_TERM').allowBlank=true;// 5.
					    					   
					    					   if(ownerForm.form.findField('SCIENTIFIC_TYPE').label!=undefined){
							         				ownerForm.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='企业类型(大中小微):';		
						         				}else{
							         				ownerForm.form.findField('SCIENTIFIC_TYPE').fieldLabel='企业类型(大中小微)';
						         				}
					     					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
							         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
						         				}else{
							         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
						         				}
					    				   }
					    				   else{// 是
					    					   if((range!='99'||range !='')&&rate>=3){// 满足1.2.3
					    						   ownerForm.form.findField('SCIENTIFIC_TYPE').setVisible(true);// 4.企业类型(大中小微)
						    					   ownerForm.form.findField('SCIENTIFIC_TYPE').allowBlank=false;// 4.企业类型(大中小微)
						    					   if(ownerForm.form.findField('SCIENTIFIC_TYPE').label!=undefined){
								         				ownerForm.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='<font color="red">*</font>企业类型(大中小微):';		
							         				}else{
								         				ownerForm.form.findField('SCIENTIFIC_TYPE').fieldLabel='<font color="red">*</font>企业类型(大中小微)';
							         				}
						    					 if(type!='01'&&type!=''){// 当企业类型不是“大”时，5.才是必输
						    						  ownerForm.form.findField('SCIENTIFIC_TERM').allowBlank=false;// 5.
							    					   ownerForm.form.findField('SCIENTIFIC_TERM').setVisible(true);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
							    					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
									         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
								         				}else{
									         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
								         				}
						    					 }
					    					   }  						    				  
					    				   }  
					    			   }						
					    		   },
					    		   blur:function(){					    			   
					    			   var v = this.getValue();// 产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围
					    			   var ownerForm=this;
					    			   while(ownerForm && !ownerForm.form){
					    				   ownerForm=ownerForm.ownerCt;
					    			   }
					    			   if(ownerForm && ownerForm.form){
					    				   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
					    				   var range=v;
					    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
					    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）
					    				   var type=ownerForm.form.findField('SCIENTIFIC_TYPE').getValue();// 4.大型
					    				   var term=ownerForm.form.findField('SCIENTIFIC_TERM').getValue();// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
					    				   if(range!='99'&&rate>='3' &&ifnew=='1' ){// 满足1.2.3.
					    					   if(type=='01'){// 大型
					    						   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('01');// 1.科技型类型大型
					    					   }else if(type!='01'&&type!=''){// 中小型
					    						   if(term!='04'&&term!==''){
					    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('03');// 2.科创型
					    						   }
					    						   else{
					    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('02');// 3.科技型类型中小型
					    						   }
					    					   }   
					    				   }
					    				   
					    			   }						
					    		   },
					    	   		'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}},
			    			 {xtype:'combo',id:'p2sciencetype',anchor:'90%',readOnly:false,fieldLabel:'企业类型(大中小微)',name:'SCIENTIFIC_TYPE',store:scienceTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						    	    mode : 'local',forceSelection : true,triggerAction : 'all',hidden:true,
						    	    listeners:{
						    	    	 select:function(){
							    			   var v = this.getValue();// 4.企业类型
							    			   var ownerForm=this;
							    			   while(ownerForm && !ownerForm.form){
							    				   ownerForm=ownerForm.ownerCt;
							    			   }
							    			   if(ownerForm && ownerForm.form){	
							    				   var range=ownerForm.form.findField('SCIENTIFIC_RANGE').getValue();// 1.范围
							    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
							    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新
							    				   if(v=='01'){// 为“大”
							    				   /**
													 * 则后续问题5不需回答
													 */ 
							    					   ownerForm.form.findField('SCIENTIFIC_TERM').setValue('');// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
							    					   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
							    					 
							    					   ownerForm.form.findField('SCIENTIFIC_TERM').setVisible(false);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
							    					   
							    					   ownerForm.form.findField('SCIENTIFIC_TERM').allowBlank=true;// 5.
							    					  
							    					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
									         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
								         				}else{
									         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
								         				}
							    				   }
							    				   else if (v!=''){// 为“中。小。微”
							    					   if((range!='99'||range !='')&&rate>=3&& ifnew=='1'){// 满足1.2.3
								    					   ownerForm.form.findField('SCIENTIFIC_TERM').allowBlank=false;// 5.
								    					   ownerForm.form.findField('SCIENTIFIC_TERM').setVisible(true);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
								    					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
										         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
									         				}else{
										         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
									         			  }
							    					   }  						    				  
							    				   }  
							    			   }						
							    		   },
							    		   blur:function(){					    			   
							    			   var v = this.getValue();// 4.企业类型
							    			   var ownerForm=this;
							    			   while(ownerForm && !ownerForm.form){
							    				   ownerForm=ownerForm.ownerCt;
							    			   }
							    			   if(ownerForm && ownerForm.form){
							    				   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
							    				   var range=ownerForm.form.findField('SCIENTIFIC_RANGE').getValue();// 1.
							    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
							    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）
							    				   var type=v;// 4.大型
							    				   var term=ownerForm.form.findField('SCIENTIFIC_TERM').getValue();// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
							    				   if(range!='99'&&rate>='3' &&ifnew=='1' ){// 满足1.2.3.
							    					   if(type=='01'){// 大型
							    						   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('01');// 1.科技型类型大型
							    					   }else if(type!='01'&&type!=''){// 中小型
							    						   if(term!='04'&&term!==''){
							    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('03');// 2.科创型
							    						   }
							    						   else{
							    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('02');// 3.科技型类型中小型
							    						   }
							    					   }   
							    				   }
							    				   
							    			   }						
							    		   },
						    	    	'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    		 }}}
						   ]
					},{
					columnWidth:0.5,  
					layout:'form',
					items:[
					       {xtype:'textfield',id:'p2sciencerate',anchor:'90%',readOnly:false,fieldLabel:'企业年度研究开发费用占销售收入总额的比例(%)',name:'SCIENTIFIC_RATE',hidden:false,maxLength:15,
					    		regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
								regexText:'格式有误,例：999999.9，-9999999.9',
					      	 listeners:{
				    		   blur:function(){	
				    			   var v = this.getValue();// 2.比例
				    			   
				    			   var range=qzComLists.form.findField('SCIENTIFIC_RANGE').getValue();// 1.
			    				   var rate =v;// 2.企业年度研究开发费用占销售收入总额的比例
			    				   var ifnew =qzComLists.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）
			    				   var type=qzComLists.form.findField('SCIENTIFIC_TYPE').getValue();// 4.大型
			    				   var term=qzComLists.form.findField('SCIENTIFIC_TERM').getValue();// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
				    			 			    				  
			    				   if(v<'3'||v==''){// 小于3
			    				   /**
									 * 则后续问题不需回答，客户不属于科技型企业
									 */  
			    					   qzComLists.form.findField('SCIENTIFIC_TYPE').setValue('');// 4.企业类型(大中小微)
			    					   qzComLists.form.findField('SCIENTIFIC_TERM').setValue('');// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
			    					   qzComLists.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
			    					   
			    					   qzComLists.form.findField('SCIENTIFIC_TYPE').setVisible(false);// 4.企业类型(大中小微)
			    					   qzComLists.form.findField('SCIENTIFIC_TERM').setVisible(false);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
			    					   
			    					   qzComLists.form.findField('SCIENTIFIC_TYPE').allowBlank=true;// 4.企业类型(大中小微)
			    					   qzComLists.form.findField('SCIENTIFIC_TERM').allowBlank=true;// 5.
			    					   
			    					   qzComLists.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='企业类型(大中小微):';
			    					   if(ownerForm.form.findField('SCIENTIFIC_TYPE').label!=undefined){
					         				ownerForm.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='企业类型(大中小微):';		
				         				}else{
					         				ownerForm.form.findField('SCIENTIFIC_TYPE').fieldLabel='企业类型(大中小微)';
				         				}
			    					   if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
					         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
				         				}else{
					         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
				         				}
			    				   }
			    				   else{// 大于等于3
			    					   if((range!='99'||range !='')&&ifnew=='1'){// 满足1.2.3
			    						   qzComLists.form.findField('SCIENTIFIC_TYPE').setVisible(true);// 4.企业类型(大中小微)
			    						   qzComLists.form.findField('SCIENTIFIC_TYPE').allowBlank=false;// 4.企业类型(大中小微)
			    						   if(ownerForm.form.findField('SCIENTIFIC_TYPE').label!=undefined){
						         				ownerForm.form.findField('SCIENTIFIC_TYPE').label.dom.innerHTML='<font color="red">*</font>企业类型(大中小微):';		
					         				}else{
						         				ownerForm.form.findField('SCIENTIFIC_TYPE').fieldLabel='<font color="red">*</font>企业类型(大中小微)';
					         				}
				    					 if(type!='01'&&type!=''){// 当企业类型不是“大”时，5.才是必输
				    						 qzComLists.form.findField('SCIENTIFIC_TERM').allowBlank=false;// 5.
				    						 qzComLists.form.findField('SCIENTIFIC_TERM').setVisible(true);// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
				    						  if(ownerForm.form.findField('SCIENTIFIC_TERM').label!=undefined){
							         				ownerForm.form.findField('SCIENTIFIC_TERM').label.dom.innerHTML='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件:';		
						         				}else{
							         				ownerForm.form.findField('SCIENTIFIC_TERM').fieldLabel='<font color="red">*</font>是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件';
						         				}
				    					 }
			    					   }  						    				  
			    				   }   
			    				   if(range!='99'&&rate>='3' &&ifnew=='1' ){// 满足1.2.3.
			    					   if(type=='01'){// 大型
			    						   qzComLists.form.findField('SCIENTIFIC_ENT').setValue('01');// 1.科技型类型大型
			    					   }else if(type!='01'&&type!=''){// 中小型
			    						   if(term!='04'&&term!==''){
			    							   qzComLists.form.findField('SCIENTIFIC_ENT').setValue('03');// 2.科创型
			    						   }
			    						   else{
			    							   qzComLists.form.findField('SCIENTIFIC_ENT').setValue('02');// 3.科技型类型中小型
			    						   }
			    					   }   
			    				   }					
				    		   }
					    	 }},					      
					       {xtype:'combo',id:'p2sciencehigh',anchor:'90%',readOnly:false,fieldLabel:'是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件',name:'SCIENTIFIC_TERM',store:scienceHighStore,resizable : true,valueField : 'key',displayField : 'value',
						    	 mode : 'local',forceSelection : true,triggerAction : 'all',hidden:true,
						    	  listeners:{
						    	    	 select:function(){
							    			   var v = this.getValue();// 5.是否具备高新企业认定
							    			   var ownerForm=this;
							    			   while(ownerForm && !ownerForm.form){
							    				   ownerForm=ownerForm.ownerCt;
							    			   }
							    			   if(ownerForm && ownerForm.form){	
							    				   var range=ownerForm.form.findField('SCIENTIFIC_RANGE').getValue();// 1.范围
							    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
							    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新
							    				   var type=ownerForm.form.findField('IF_SCIENCE').getValue();// 4.企业类型
							    				   if(v=='04'){// 为“未符合任何条件”
							    				   /**
													 * 无后续问题
													 */ 
							    					   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
							    				   }
							    				   else if(v!=''){// 科创性的判断逻辑
							    					   if((range!='99'||range !='')&&rate>=3&& ifnew=='1'){// 满足1.2.3
							    						   if(type=='01'){// 大型
								    						   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('01');// 1.科技型类型大型
								    						}
							    						   else if(type!=''){
							    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('03');// 科创型
							    						   }
							    					   }  						    				  
							    				   }  
							    			   }						
							    		   },
							    		   blur:function(){					    			   
							    			   var v = this.getValue();// 5.是否具备高新企业认定
							    			   var ownerForm=this;
							    			   while(ownerForm && !ownerForm.form){
							    				   ownerForm=ownerForm.ownerCt;
							    			   }
							    			   if(ownerForm && ownerForm.form){
							    				   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('');// 科技类型
							    				   var range=ownerForm.form.findField('SCIENTIFIC_RANGE').getValue();// 1.
							    				   var rate =ownerForm.form.findField('SCIENTIFIC_RATE').getValue();// 2.企业年度研究开发费用占销售收入总额的比例
							    				   var ifnew =ownerForm.form.findField('IF_SCIENCE').getValue();// 3.企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）
							    				   var type=ownerForm.form.findField('SCIENTIFIC_TYPE').getValue();// 4.大型
							    				   var term=v;// 5.是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
							    				   if(range!='99'&&rate>='3' &&ifnew=='1' ){// 满足1.2.3.
							    					   if(type=='01'){// 大型
							    						   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('01');// 1.科技型类型大型
							    					   }else if(type!='01'&&type!=''){// 中小型
							    						   if(term!='04'&&term!==''){
							    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('03');// 2.科创型
							    						   }
							    						   else{
							    							   ownerForm.form.findField('SCIENTIFIC_ENT').setValue('02');// 3.科技型类型中小型
							    						   }
							    					   }   
							    				   }  
							    			   }						
							    		   },
						    	    	'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    		 }}},
						    {xtype:'combo',id:'p2science',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'科技型企业类型',name:'SCIENTIFIC_ENT',store:scienceComStore,resizable : true,valueField : 'key',displayField : 'value',
							    	   mode : 'local',forceSelection : true,triggerAction : 'all',listeners:{
										'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }
							}}  
					    ]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '环保处罚事件信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			labelWidth : 140,
			items:[{
				layout:'column',
				items:[{
					columnWidth:0.5,  
					layout:'form',
					items:[
					    {xtype:'combo',id:'p2innoId',anchor:'90%',readOnly:false,fieldLabel:'是否发生过环保处罚事件',name:'ENVIRO_PENALTIES',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',
//						emptyText:'未知',
							listeners:{
								'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
								},select:function(){
				    			   var v = this.getValue();// 5.是否具备高新企业认定
				    			   var ownerForm=this;
				    			   while(ownerForm && !ownerForm.form){
				    				   ownerForm=ownerForm.ownerCt;
				    			   }
				    			   if(ownerForm && ownerForm.form){	
				    				   if(v=='1'){
				    					   Ext.getCmp('happen_grid').expand();//展开
				    				   }else{
				    					   Ext.getCmp('happen_grid').collapse();//收起
				    					   addHappenStore.removeAll();//清空缓存数据
				    				   }
				    			   }
								}
							}},
                        {
							xtype : 'fieldset',
							id:'happen_grid',
							title : '发生日期',
							titleCollapse : true,
							collapsible : true,
							collapsed:true,//收起
							autoHeight : true,
							items : [happenDateGrid]
						}
					]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '其他信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					        {xtype:'combo',id:'p2localId',anchor:'90%',readOnly:false,fieldLabel:'是否异地客户',name:'IS_NOT_LOCAL_ENT',store:isNotLocalEntStore,resizabe:true,valueField:'key',displayField:'value',
					        	mode:'local',forceSelection:true,triggerAction:'all',
//					        	emptyText:'未知',
					        	listeners:{
					        		'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}},				        				       										        			
			        		{xtype:'combo',id:'p2steelId',readOnly:false,fieldLabel:'是否钢贸行业',name:'IS_STEEL_ENT',store:isSteelEntStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
//								emptyText:'未知',
								listeners:{
									'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}},							        					
			        											        			
					        {xtype:'combo',id:'p2faxlId',anchor:'90%',readOnly:false,fieldLabel:'是否传真交易指示标志',name:'IS_FAX_TRANS_CUST',store:ifFaxTransStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}  
//			    			  ,validator:function(){
//							   	  var ownerForm=this;
//				            	  while(ownerForm && !ownerForm.form){
//				            			ownerForm=ownerForm.ownerCt;
//				            	  }
//				            	  setReadOrNoRead(this,this.name,this.value);
//				            	  if(!ownerForm.form.findField(this.name).allowBlank){
//				            		  return true; 
//				            	  }
//							 }
			    			},								        	
					        {xtype:'combo',id:'p2greenId',anchor:'90%',readOnly:false,fieldLabel:'节能环保项目及服务贷款',name:'ENERGY_SAVING',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }}},
							{xtype:'combo',id:'p2materialId',anchor:'90%',readOnly:false,fieldLabel:'是否环境、安全等重大风险企业',name:'IS_MATERIAL_RISK',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',
//									emptyText:'未知',
									listeners:{
										'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }}},
					        {xtype:'combo',id:'p2agribusilId',anchor:'90%',readOnly:false,fieldLabel:'是否为涉农贷款',name:'IS_RURAL',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }}}
					       ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
				            {xtype:'combo',id:'p2taiwanId',anchor:'90%',readOnly:false,fieldLabel:'是否台资企业',name:'IS_TAIWAN_CORP',store:ifTaiWanStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }} 
//				                ,validator:function(){
//										   	  var ownerForm=this;
//							            	  while(ownerForm && !ownerForm.form){
//							            			ownerForm=ownerForm.ownerCt;
//							            	  }
//							            	  setReadOrNoRead(this,this.name,this.value);
//							            	  if(!ownerForm.form.findField(this.name).allowBlank){
//							            		  return true; 
//							            	  }
//										 }
				                },
							{xtype:'combo',id:'p2newId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否2年内新设立企业',name:'IS_NEW_CORP',store:isSteelEntStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}},				
							{xtype:'combo',id:'p2inoutId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'境内境外标志',name:'INOUT_FLAG',store:inOutFlagStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }}},							
							{xtype:'combo',id:'p2shipId',anchor:'90%',readOnly:false,fieldLabel:'是否为航运行业（银监统计）',name:'SHIPPING_IND',store:isSteelEntStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			 }}},					
							
							{xtype:'combo',id:'p2highId',anchor:'90%',readOnly:false,fieldLabel:'是否两高一剩',name:'IS_HIGH_POLLUTE',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',
//								emptyText:'未知',
								listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
								}},
							{xtype:'combo',id:'p2scienceId',anchor:'90%',readOnly:false,fieldLabel:'是否科技金融行业',name:'IS_SCIENCE_TECH',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',
									//emptyText:'未知',
									listeners:{
										'focus': {  
						    			        fn: function(e) {  
						    			            e.expand();  
						    			            this.doQuery(this.allQuery, true);  
						    			        },  
						    			        buffer:200  
						    			 }
						    		}
					    	}]
				}]
			}]		
		},{
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[// 主键
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'KEY_CUST_ID',fieldLabel:'重要标识id',hidden:true},		       			 	
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'ISSUE_STOCK_ID',fieldLabel:'股票id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'SCIENCE_CUST_ID',fieldLabel:'科技型id',hidden:true},
			               // cust_id:
                           //{xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'COM_CUST_ID',fieldLabel:'证件客户id',hidden:true},
			    		   {xtype:'textfield',anchor:'90%',readOnly:false,maxLength:30,name:'STOCK_CUST_ID',fieldLabel:'股票客户id',hidden:true}
			    		   
			    		   ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[]
				}]
			}]		
		}]
	}],
	buttonAlign : "center",
	buttons: [{
		text:'修改历史',
		id:'his_2',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'上一页',
		 handler:function(){
			qzComInfo.setActiveTab(0);
		 }
	},{
		 text:'下一页',
		 handler:function(){
            //window.queryTempData();
            //addrCustInfoStore.load();
			qzComInfo.setActiveTab(2);
		 }
	},{// 第二页保存
		text:'保存',
		id:'second_save',
		handler:function(){
			Ext.getCmp('second_save').disabled=true;
			var viewtitle=getCustomerViewByIndex(0).title;
			var nullflag=setnullValue();
			if(nullflag){
				showMsgNotification('提示：‘上市公司标志’或‘特种经营标志’或‘AR客户标志’选择‘否’时，其余相关信息为空!');
			}
			// 校验必输项
			if (!qzCombaseInfo.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
				enableSaveBtn();
				return false;
			}
			if (!qzComLists.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，客户识别信息第二页输入有误或存在漏输入项,请检查输入项');
				enableSaveBtn();
				return false;
			}
			var regCodeType=qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
			var enName= Ext.util.Format.trim(qzCombaseInfo.form.findField('EN_NAME').getValue());
			if(regCodeType=='99'&& (enName==''||enName.length==0)){
				 Ext.MessageBox.alert('提示信息！','第一页基本信息中的【英文名称】必输');// 弹出提示框
				 enableSaveBtn();
				 return false;
			}
			// 第一页校验
			if(!validqzComFirstFn(viewtitle)){
				 enableSaveBtn();
				 return false;
			}
			// 第二页校验
			if(!validqzComSecondFn()){
				 enableSaveBtn();
				 return false;
			}
			//第四页校验
			if(!validqzComForthFn()){
				 enableSaveBtn();
				 return false;
			}
			//成立日期:给是否2年内新设立企业赋值
			var val=qzCombaseInfo.form.findField('BUILD_DATE').value;
			var buildDate = Date.parse(val);
            var buildDate = new Date(val);//成立日期
			var nowDate=new Date();
			var tempday=parseInt(Math.abs(nowDate-buildDate)/1000/60/60/24)+1;
			if(tempday>730){//获取日期
				qzComLists.form.findField('IS_NEW_CORP').setValue('2');
			}else if (tempday<=730){
				qzComLists.form.findField('IS_NEW_CORP').setValue('1');
			}else if(tempday==''||tempday==null){
				qzComLists.form.findField('IS_NEW_CORP').setValue('');
			}
			
			// 第一页：必需要有客户名称、证件类型、证件号码
			var qzCombaseInfodata=qzCombaseInfo.form.getFieldValues(false);	
			var firstjson=[];
			custId=qzCombaseInfodata.CUST_ID;
			var custName=qzCombaseInfodata.CUST_NAME;
			var identNo=qzCombaseInfodata.IDENT_NO;
			var identType=qzCombaseInfodata.IDENT_TYPE;
			
			var qzComSecondInfodata=qzComLists.form.getFieldValues(false);	
			var secondjson=[];
			if(viewtitle!=''){
				firstjson.push(qzCombaseInfodata);
				secondjson.push(qzComSecondInfodata);
				secondAddSave(custId,custName,identNo,identType,viewtitle,firstjson,secondjson);
			}
			
			if(ifhidden==true){
				hideCurrentView();
				reloadCurrentData();
			}	
		}
	},{
		text:'关闭',
		handler:function(){
			//第二页关闭按钮
		 	enableSaveBtn();
			hideCurrentView();
			reloadCurrentData();
			custId='';
			custName='';
			//window.loadAllData(custId);// 刷新数据
            //window.resetjs(viewtitle); //刷新页面
		}
	}]
});

/**
 * 查询第三页数据
 */

/**
 * @see 给暂存数据赋值
 * @see 根据客户号查询相关表格数据：发生日期(第2页)、证件信息(第3页)、地址信息(第3页)、联系人信息(第3页)、联系信息(第3页)
 * @param {String} custId 客户号
 */
window.queryTempData=function(custId){
	//加载第二页发生日期
	addHappenStore.load({
		params : {
			custId : custId
		},
		callback: function(){
			window.readyCount++;
			tempHappenDateStore.removeAll();
			addHappenStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempHappenDateStore.addSorted(tempRecord);
			});
		}
	});
    // 加载第三页数据
	//潜在客户地址信息
	addrCustInfoStore.load({
		params : {
			custId : custId
		},
		callback: function(){
			window.readyCount++;
			tempComAddrStore.removeAll();
			addrCustInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempComAddrStore.addSorted(tempRecord);
			});
		}
	});
	//客户联系人信息
	comContactPersonStore.load({
		params : {
			custId : custId
		},
		callback: function(){
			window.readyCount++;
			tempComContactPersonStore.removeAll();
			comContactPersonStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempComContactPersonStore.addSorted(tempRecord);
			});
		}
	});
	//股东信息
	comHolderInfoStore.load({
		params : {
			custId : custId
		},
		callback: function(){/*
			window.readyCount++;
			tempComHolderInfoStore.removeAll();
			comHolderInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempComHolderInfoStore.addSorted(tempRecord);
			});
		*/}
	});
	//客户联系信息
	comContactInfoStore.load({
		params : {
			custId : custId
		},
		callback: function(){
			window.readyCount++;
			tempComContactInfoStore.removeAll();
			comContactInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempComContactInfoStore.addSorted(tempRecord);
			});
		}
	});
	//客户证件信息
	identInfoStore.load({
		params : {
			custId : custId
		},
		callback: function(){
			window.readyCount++;
			tempComIdentStore.removeAll();
			identInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempComIdentStore.addSorted(tempRecord);
			});
		}
	});	
}
window.queryComThreeFn = function(custId){
    // 加载第三页数据
	addrCustInfoStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});
	comContactPersonStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});
	comContactInfoStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});
	identInfoStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});	
};
// 基本信息部分 第三页（地址、联系人、证件信息）
var qzComThreePanel = new Ext.form.FormPanel({
	frame : true,
	title : '第三页（证件、地址、联系人、股东信息）',
	autoScroll : true,
	buttonAlign : "center",
	items : [{
		xtype : 'fieldset',
		title : '证件信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [identGridPanel]
	},
	{
		xtype : 'fieldset',
		title : '地址信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [addrGridPanel]
	},{
		xtype : 'fieldset',
		title : '联系人信息'	,
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [comContactPersonGrid]
	},{
		xtype : 'fieldset',
		title : '联系信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [comContactGridPanel]
	},{
        xtype : 'fieldset',
        title : '股东信息',
        titleCollapse : true,
        collapsible : true,
        autoHeight : true,
        items : [comHolderGridPanel]
    }],
	buttonAlign : "center",
	buttons:[{
		text:'修改历史',
		id:'his_3',
		handler:function(){
			updateHisWin.show();
		}
	},{
		text:'上一页',
		handler:function(){
			qzComInfo.setActiveTab(1);
		}
	},{
		text:'下一页',
		handler:function(){
			qzComInfo.setActiveTab(3);
		}
	},{// 第三页保存
		text:'保存',
		id:'third_save',
		handler:function(){
			Ext.getCmp('third_save').disabled=true;
			var viewtitle=getCustomerViewByIndex(0).title;
			var nullflag=setnullValue();
			if(nullflag){
				showMsgNotification('提示：‘上市公司标志’或‘特种经营标志’或‘AR客户标志’选择‘否’时，其余相关信息为空!');
			}
			// 校验必输项
			if (!qzCombaseInfo.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
				 enableSaveBtn();
				 return false;
			}
			if (!qzComLists.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，客户识别信息第二页输入有误或存在漏输入项,请检查输入项');
				 enableSaveBtn();
				 return false;
			}
			var regCodeType=qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
			var enName= Ext.util.Format.trim(qzCombaseInfo.form.findField('EN_NAME').getValue());
			if(regCodeType=='99'&& (enName==''||enName.length==0)){
				 Ext.MessageBox.alert('提示信息！','第一页基本信息中的【英文名称】必输');// 弹出提示框
				 enableSaveBtn();
				 return false;
			}
			// 第一页校验
			if(!validqzComFirstFn(viewtitle)){
				 enableSaveBtn();
				 return false;
			}
			// 第二页校验
			if(!validqzComSecondFn()){
				 enableSaveBtn();
				 return false;
			}
			//第四页校验
			if(!validqzComForthFn()){
				 enableSaveBtn();
				 return false;
			}
			//成立日期:给是否2年内新设立企业赋值
			var val=qzCombaseInfo.form.findField('BUILD_DATE').value;
			var buildDate = Date.parse(val);
            var buildDate = new Date(val);//成立日期
			var nowDate=new Date();
			var tempday=parseInt(Math.abs(nowDate-buildDate)/1000/60/60/24)+1;
			if(tempday>730){//获取日期
				qzComLists.form.findField('IS_NEW_CORP').setValue('2');
			}else if (tempday<=730){
				qzComLists.form.findField('IS_NEW_CORP').setValue('1');
			}else if(tempday==''||tempday==null){
				qzComLists.form.findField('IS_NEW_CORP').setValue('');
			}
			
			// 第一页：必需要有客户名称、证件类型、证件号码
			
			var qzCombaseInfodata=qzCombaseInfo.form.getFieldValues(false);	
			var firstjson=[];
			custId=qzCombaseInfodata.CUST_ID;
			var custName=qzCombaseInfodata.CUST_NAME;
			var identNo=qzCombaseInfodata.IDENT_NO;
			var identType=qzCombaseInfodata.IDENT_TYPE;
			
			var qzComSecondInfodata=qzComLists.form.getFieldValues(false);	
			var secondjson=[];
			if(viewtitle!=''){
				firstjson.push(qzCombaseInfodata);
				secondjson.push(qzComSecondInfodata);
				secondAddSave(custId,custName,identNo,identType,viewtitle,firstjson,secondjson);
			}
			if(ifhidden==true){
				hideCurrentView();
				reloadCurrentData();
			}				
		}
	},{
		text:'关闭',
		handler:function(){
			//第三页关闭按钮
			enableSaveBtn();
			hideCurrentView();
			reloadCurrentData();
			custId='';
			custName='';
			//window.loadAllData(custId);
			//Ext.getCmp('third_save').disabled=false;
            //window.resetjs(viewtitle); //刷新页面
		}
	}]
});
/**
 * 查询客户信息第四页基本信息
 */
var qzComOther1InfoStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithCom!queryComfourth.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, ['GRADE_CUST_ID','AGENT_NATION_CODE','AGENT_ID','GRADE_IDENT_TYPE','TEL','AGENT_NAME',
	    'GRADE_IDENT_NO','ORG1_CUST_ID','SUPER_DEPT','ORG_STATE','YEAR_RATE','ENT_BELONG','BAS_CUS_STATE'])
});

/**
 * 查询对公的第四页信息
 */
var SearchqzComOther1Fn = function(custId){
	if(qzComOther1Info.getForm().getEl()){
        qzComOther1Info.getForm().getEl().dom.reset();
//		qzComOther1Info.getForm().reset();
	}else{
		qzComOther1Info.getForm().reset();
	}
	qzComOther1InfoStore.load({//加载数据集获取客户信息第四页基本信息
		params : {
			custId : custId
		},
		callback:function(){
			window.readyCount++;
			if(qzComOther1InfoStore.getCount()!=0){
				qzComOther1Info.getForm().loadRecord(qzComOther1InfoStore.getAt(0));
				//add by liuming 20170817
//				setReadOrNoRead(qzComOther1Info,'AGENT_NATION_CODE',qzComOther1Info.getForm().findField('AGENT_NATION_CODE').getValue());
//				setReadOrNoRead(qzComOther1Info,'GRADE_IDENT_TYPE',qzComOther1Info.getForm().findField('GRADE_IDENT_TYPE').getValue());
//				setReadOrNoRead(qzComOther1Info,'AGENT_NAME',qzComOther1Info.getForm().findField('AGENT_NAME').getValue());
//				setReadOrNoRead(qzComOther1Info,'TEL',qzComOther1Info.getForm().findField('TEL').getValue());
//				setReadOrNoRead(qzComOther1Info,'GRADE_IDENT_NO',qzComOther1Info.getForm().findField('GRADE_IDENT_NO').getValue());
			}
		}
	});
};
// 其他信息部分 第四页（其他信息1）
var qzComOther1Info = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 title : '第四页（其他信息1）',
	 labelWidth : 140,
	 buttonAlign : "center",
	 items:[{
			xtype : 'fieldset',
			title : '代理人信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'combo',anchor:'90%',readOnly:false,fieldLabel:'代理人国籍',name:'AGENT_NATION_CODE',store:countryStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   mode : 'local',forceSelection : true,triggerAction : 'all',maxLength:30,listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
								} 
//					            ,validator:function(){
//								   	  var ownerForm=this;
//					            	  while(ownerForm && !ownerForm.form){
//					            			ownerForm=ownerForm.ownerCt;
//					            	  }
//					            	  setReadOrNoRead(this,this.name,this.value);
//					            	  if(!ownerForm.form.findField(this.name).allowBlank){
//					            		  return true; 
//					            	  }
//								 }
					          },
					       {xtype:'combo',anchor:'90%',readOnly:false,fieldLabel:'代理人证件类型',name:'GRADE_IDENT_TYPE',store:identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   mode : 'local',forceSelection : true,triggerAction : 'all',listeners:{
									'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
								}  
//								 ,validator:function(){
//								   	  var ownerForm=this;
//					            	  while(ownerForm && !ownerForm.form){
//					            			ownerForm=ownerForm.ownerCt;
//					            	  }
//					            	  setReadOrNoRead(this,this.name,this.value);
//					            	  if(!ownerForm.form.findField(this.name).allowBlank){
//					            		  return true; 
//					            	  }
//								 }
							},
					       {xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'代理人联系电话',name:'TEL',maxLength:15
//									 ,validator:function(){
//									   	  var ownerForm=this;
//						            	  while(ownerForm && !ownerForm.form){
//						            			ownerForm=ownerForm.ownerCt;
//						            	  }
//						            	  setReadOrNoRead(this,this.name,this.value);
//						            	  if(!ownerForm.form.findField(this.name).allowBlank){
//						            		  return true; 
//						            	  }
//									 }
								 }]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[ {xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'代理人户名',name:'AGENT_NAME',maxLength:50
//						,validator:function(){
//						   	  var ownerForm=this;
//			            	  while(ownerForm && !ownerForm.form){
//			            			ownerForm=ownerForm.ownerCt;
//			            	  }
//			            	  setReadOrNoRead(this,this.name,this.value);
//			            	  if(!ownerForm.form.findField(this.name).allowBlank){
//			            		  return true; 
//			            	  }
//						 }
					},
				    	    {xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'代理人证件号码',name:'GRADE_IDENT_NO',maxLength:26
//							 ,validator:function(){
//							   	  var ownerForm=this;
//				            	  while(ownerForm && !ownerForm.form){
//				            			ownerForm=ownerForm.ownerCt;
//				            	  }
//				            	  setReadOrNoRead(this,this.name,this.value);
//				            	  if(!ownerForm.form.findField(this.name).allowBlank){
//				            		  return true; 
//				            	  }
//							 }
						 }]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '其他信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[	//
					       	{xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'主管单位名称',name:'SUPER_DEPT',maxLength:40},
					       	{xtype:'combo',anchor:'90%',readOnly:false,fieldLabel:'机构状态(警示RM)',name:'ORG_STATE',readOnly:false,store:orgstateStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all',
						    	   listeners:{
										'focus': {  
					    			        fn: function(e) {  
					    			            e.expand();  
					    			            this.doQuery(this.allQuery, true);  
					    			        },  
					    			        buffer:200  
					    		    }
							}},
					       	{xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'年化入账比例',name:'YEAR_RATE',hidden:true,maxLength:15,
								regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
								regexText:'格式有误,例：999999.9，-9999999.9'}
				    		 
				    		]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
				       {xtype:'combo',anchor:'90%',readOnly:false,fieldLabel:'隶属类型',name:'ENT_BELONG',store:subTypeStore,resizable : true,valueField : 'key',displayField : 'value',
                            mode : 'local',forceSelection : true,triggerAction : 'all',
                            listeners:{
                                'focus': {  
			    			        fn: function(e) {  
			    			            e.expand();  
			    			            this.doQuery(this.allQuery, true);  
			    			        },  
			    			        buffer:200  
			    			     }
                            }},
				       {xtype:'combo',anchor:'90%',readOnly:false,fieldLabel:'基本户状态',name:'BAS_CUS_STATE',readOnly:false,store:orgstateStore,resizable : true,valueField : 'key',displayField : 'value',
                            mode : 'local',forceSelection : true,triggerAction : 'all',listeners:{
								'focus': {  
				    			        fn: function(e) {  
				    			            e.expand();  
				    			            this.doQuery(this.allQuery, true);  
				    			        },  
				    			        buffer:200  
				    			 }
                            }}
		    		  ]
				}]
			},{
				columnWidth:.5,  
				layout:'form',
				items:[{xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'代理人客户id',name:'GRADE_CUST_ID',hidden:true},
						{xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'主管单位id',name:'ORG1_CUST_ID',hidden:true},
						{xtype:'textfield',anchor:'90%',readOnly:false,fieldLabel:'代理人id',name:'AGENT_ID',hidden:true}]
			}]		
		}],
	buttonAlign : "center",
	buttons:[{
		text:'修改历史',
		id:'his_4',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'上一页',
		 handler:function(btn){
			qzComInfo.setActiveTab(2);
		 }
	},{
		 text:'下一页',
		 handler:function(btn){
			qzComInfo.setActiveTab(4);
		 }
	},{// 第四页保存
		text:'保存',
		id:'forth_save',
		handler:function(){
			Ext.getCmp('forth_save').disabled=true;
			var viewtitle=getCustomerViewByIndex(0).title;
			var nullflag=setnullValue();
			if(nullflag){
				showMsgNotification('提示：‘上市公司标志’或‘特种经营标志’或‘AR客户标志’选择‘否’时，其余相关信息为空!');
			}
			// 校验必输项
			if (!qzCombaseInfo.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
				 enableSaveBtn();
				 return false;
			}
			if (!qzComLists.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，客户识别信息第二页输入有误或存在漏输入项,请检查输入项');
				 enableSaveBtn();
				 return false;
			}
			var regCodeType=qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
			var enName= Ext.util.Format.trim(qzCombaseInfo.form.findField('EN_NAME').getValue());
			if(regCodeType=='99'&& (enName==''||enName.length==0)){
				 Ext.MessageBox.alert('提示信息！','第一页基本信息中的【英文名称】必输');// 弹出提示框
				 enableSaveBtn();
				 return false;
			}
			// 第一页校验
			if(!validqzComFirstFn(viewtitle)){
				 enableSaveBtn();
				 return false;
			}
			// 第二页校验
			if(!validqzComSecondFn()){
				 enableSaveBtn();
				 return false;
			}
			//第四页校验
			if(!validqzComForthFn()){
				 enableSaveBtn();
				 return false;
			}
			//成立日期:给是否2年内新设立企业赋值
			var val=qzCombaseInfo.form.findField('BUILD_DATE').value;
			var buildDate = Date.parse(val);
            var buildDate = new Date(val);//成立日期
			var nowDate=new Date();
			var tempday=parseInt(Math.abs(nowDate-buildDate)/1000/60/60/24)+1;
			if(tempday>730){//获取日期
				qzComLists.form.findField('IS_NEW_CORP').setValue('2');
			}else if (tempday<=730){
				qzComLists.form.findField('IS_NEW_CORP').setValue('1');
			}else if(tempday==''||tempday==null){
				qzComLists.form.findField('IS_NEW_CORP').setValue('');
			}
			
			// 第一页：必需要有客户名称、证件类型、证件号码
			var qzCombaseInfodata=qzCombaseInfo.form.getFieldValues(false);	
			var firstjson=[];
			custId=qzCombaseInfodata.CUST_ID;
			var custName=qzCombaseInfodata.CUST_NAME;
			var identNo=qzCombaseInfodata.IDENT_NO;
			var identType=qzCombaseInfodata.IDENT_TYPE;
			
			var qzComSecondInfodata=qzComLists.form.getFieldValues(false);	
			var secondjson=[];
			if(viewtitle!=''){
				firstjson.push(qzCombaseInfodata);
				secondjson.push(qzComSecondInfodata);
				secondAddSave(custId,custName,identNo,identType,viewtitle,firstjson,secondjson);
			}
			if(ifhidden==true){
				hideCurrentView();
				reloadCurrentData();
			}	
		}
	},{
		text:'关闭',
		handler:function(){
			//第四页关闭按钮
		 	enableSaveBtn();
			hideCurrentView();
			reloadCurrentData();
			custId='';
			custName='';
			//window.loadAllData(custId);
            //window.resetjs(viewtitle); //刷新页面
		}
	}]
});
/**
 * 查询客户信息第五页基本信息
 */
var qzComOther2InfoStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithCom!queryComfifth.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, ['TASK_NUMBER','INTERVIEWEE_NAME','RES_CUSTSOURCE','CUS_BUSISTATUS','CUS_OPERATEPTEL',
	    'CUS_MAJORPRODUCT','ACT_CTL_PHONE','ACT_CTL_WIFE','ACT_CTL_NAME'])
});

/**
 * 查询对公的第五页信息
 */
var SearchqzComOther2Fn = function(custId){
	if(qzComOther2Info.getForm().getEl()){
        qzComOther2Info.getForm().getEl().dom.reset();
//		qzComOther2Info.getForm().reset();
	}else{
		qzComOther2Info.getForm().reset();
	}
	qzComOther2InfoStore.load({//加载数据集获取客户信息第五页基本信息
		params : {
			custId : custId
		},
		callback:function(){
			window.readyCount++;
			if(qzComOther2InfoStore.getCount()!=0){
				qzComOther2Info.getForm().loadRecord(qzComOther2InfoStore.getAt(0));
			}
		}
	});
};
// 其他信息部分2 第五页（其他信息2）
var qzComOther2Info = new Ext.form.FormPanel({
	frame : true,
	title : '第五页（其他信息2）',
	autoScroll : true,
	labelWidth : 140,
	buttonAlign : "center",
	items : [{
		xtype : 'fieldset',
		title : '基础信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[	
			       		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'客户来源 ',name:'CUS_RESOURCE'},		                                           
			    		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'实际控制人姓名',id:'myname',name:'ACT_CTL_NAME'},		                                           
			    		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'实际控制人电话',name:'ACT_CTL_PHONE'},	
						 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'主要竞争对手',name:'CUS_MAJORRIVAL'}]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[
				       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'行业地位',name:'CUS_BUSISTATUS'},		 		
				       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'实际控制人配偶姓名',name:'ACT_CTL_WIFE'},				 
		    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'主要产品',name:'CUS_MAJORPRODUCT'}]
			}]
		}]		
	},{
		xtype : 'fieldset',
		title : '持股人',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [partnerGroupGrid]
	},{
		xtype : 'fieldset',
		title : '供货商及买售商',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [infComGrid]
	},{
		xtype : 'fieldset',
		title : '抵押',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [mortgageGridPanel]
	},{
		xtype : 'fieldset',
		title : '关联企业',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [relationGridPanel]	
	},{
		xtype : 'fieldset',
		title : '主要固定资产',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [fixedGridPanel]	
	},{
		xtype : 'fieldset',
		title : '盈利获利情况',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [profitGridPanel]	
	},{
		xtype : 'fieldset',
		title : '往来银行表',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [commiGridPanel]	
	}],
	buttonAlign : "center",
	buttons:[{
		text:'上一页',
		handler:function(){
			qzComInfo.setActiveTab(3);
		}
	},{// 第五页的保存
		text:'保存',
		id:'fifth_save',
		handler:function(){
			Ext.getCmp('fifth_save').disabled=true;
			var viewtitle=getCustomerViewByIndex(0).title;
			var nullflag=setnullValue();
			if(nullflag){
				showMsgNotification('提示：‘上市公司标志’或‘特种经营标志’或‘AR客户标志’选择‘否’时，其余相关信息为空!');
			}
			// 校验必输项
			if (!qzCombaseInfo.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
				 enableSaveBtn();
				 return false;
			}
			if (!qzComLists.getForm().isValid()) {
                Ext.MessageBox.alert('提示','校验失败，客户识别信息第二页输入有误或存在漏输入项,请检查输入项');
				enableSaveBtn();
				return false;
			}
			var regCodeType=qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();
			var enName= Ext.util.Format.trim(qzCombaseInfo.form.findField('EN_NAME').getValue());
			if(regCodeType=='99'&& (enName==''||enName.length==0)){
				 Ext.MessageBox.alert('提示信息！','第一页基本信息中的【英文名称】必输');// 弹出提示框
				 enableSaveBtn();
				 return false;
			}
			// 第一页校验
			if(!validqzComFirstFn(viewtitle)){
				 enableSaveBtn();
				 return false;
			}
			// 第二页校验
			if(!validqzComSecondFn()){
				 enableSaveBtn();
				 return false;
			}
			//第四页校验
			if(!validqzComForthFn()){
				 enableSaveBtn();
				 return false;
			}
			//成立日期:给是否2年内新设立企业赋值
			var val=qzCombaseInfo.form.findField('BUILD_DATE').value;
			var buildDate = Date.parse(val);
            var buildDate = new Date(val);//成立日期
			var nowDate=new Date();
			var tempday=parseInt(Math.abs(nowDate-buildDate)/1000/60/60/24)+1;
			if(tempday>730){//获取日期
				qzComLists.form.findField('IS_NEW_CORP').setValue('2');
			}else if (tempday<=730){
				qzComLists.form.findField('IS_NEW_CORP').setValue('1');
			}else if(tempday==''||tempday==null){
				qzComLists.form.findField('IS_NEW_CORP').setValue('');
			}

			// 第一页：必需要有客户名称、证件类型、证件号码
			var qzCombaseInfodata=qzCombaseInfo.form.getFieldValues(false);	
			var firstjson=[];
			custId=qzCombaseInfodata.CUST_ID;
            //var commitFirstData =
            //translateDataKey(qzCombaseInfodata,_app.VIEWCOMMITTRANS);
			
			custName=qzCombaseInfodata.CUST_NAME;
			var identNo=qzCombaseInfodata.IDENT_NO;
			var identType=qzCombaseInfodata.IDENT_TYPE;

            // Ext.Msg.wait('正在处理，请稍后......', '系统提示');
            			
            // if(viewtitle='新增潜在客户'||viewtitle=='新增信贷临时户'){
            // firstAddSave(custName,identNo,identType);
            // }
            // else{
            // secondAddSave(custId,custName,identNo,identType,viewtitle);
            // }
			var qzComSecondInfodata=qzComLists.form.getFieldValues(false);	
			var secondjson=[];
			if(viewtitle!=''){
				firstjson.push(qzCombaseInfodata);
				secondjson.push(qzComSecondInfodata);
				secondAddSave(custId,custName,identNo,identType,viewtitle,firstjson,secondjson);
			}
			
			if(ifhidden==true){
				hideCurrentView();
				reloadCurrentData();
			}			
		}
	},{
		text:'关闭',
		handler:function(){
			//第五页关闭按钮
		 	enableSaveBtn();
			hideCurrentView();
			reloadCurrentData();
			custId='';
			custName='';
			//window.loadAllData(custId);
            //window.resetjs(viewtitle); //刷新页面
		}
	}]
});
/**
 * 潜在客户信息面板
 */
var qzComInfo = new Ext.TabPanel({
	activeItem : 0,
	deferredRender:false,// 自动渲染
	items:[qzCombaseInfo,qzComLists,qzComThreePanel,qzComOther1Info,qzComOther2Info],
	listeners:{
        tabchange:function(t,p){
			var viewtitle=getCustomerViewByIndex(0).title;
			if(viewtitle.indexOf('新增')>-1){// 代表是新增
				 custId=qzCombaseInfo.form.findField('CUST_ID').getValue();
			}else {
				 var records =getAllSelects(); // 获取选择记录
				 custId =  records[0].data.CUST_ID;// 获取id
			}
            /*if(p==qzCombaseInfo){
                qzComStore.load({
                    params : {
                       custId : custId
                    },
                    callback:function(){
                        qzCombaseInfo.getForm().loadRecord(qzComStore.getAt(0));
                    }
                });
            				
             }
             if(p==qzComLists){
                qzComListsStore.load({
                    params : {
                        custId : custId
                    },
                    callback:function(){
                        qzComLists.getForm().loadRecord(qzComListsStore.getAt(0));
                    }
                });//加载第二页信息
             }
             if(p==qzComThreePanel){
                window.queryComThreeFn(custId);
             }
             if(p==qzComOther1Info){
                 qzComOther1InfoStore.load({
                     params : {
                        custId : custId
                     },
                     callback:function(){
                         if(qzComOther1InfoStore.getCount()!=0){
                            qzComOther1Info.getForm().loadRecord(qzComOther1InfoStore.getAt(0));
                         }
                     }
                 });
             }
             if(p==qzComOther2Info){
                 qzComOther2InfoStore.load({
                     params : {
                        custId : custId
                     },
                     callback:function(){
                         if(qzComOther2InfoStore.getCount()!=0){
                            qzComOther2Info.getForm().loadRecord(qzComOther2InfoStore.getAt(0));
                         }
                     }
                 });
             }*/
        }
	}
});
/**
 * 潜在客户信息容器面板
 */
var infoPanel = new Ext.Panel({
	layout : 'card',
	activeItem : 0,
	items:[qzComInfo]
});
/**
 * 自定义视图
 */
var customerView = [{
	id:'myview',
	title:'新增潜在客户',
	suspendWidth:1,//视图宽度
	hideTitle:true,
	xtype:'panel',
	suspended : false,//点击标题是否收缩当前视图
	items:[infoPanel]
}];

/**
 * 新增或修改
 * @param {String} title 标题
 * @param {Boolean} isadd 是否新增。true:新增;false:修改
 * @param {int} myType 客户类型：1代表企业，2代表个人
 */
var AddorUpdate=function(title,isadd,myType){
	Ext.getCmp('myview').title=title;
	qzComInfo.setActiveTab(0);
	showCustomerViewByIndex(0);
	Ext.getCmp('add').setVisible(false);// 新增
	Ext.getCmp('delete').setVisible(false);// 删除
	Ext.getCmp('update').setVisible(false);// 修改
	Ext.getCmp('detail').setVisible(false);// 详情
	Ext.getCmp('changeToLs').setVisible(false);// 转临时户
	Ext.getCmp('changeToZs').setVisible(false);// 转正式户
	if(document.getElementsByClassName('xtb-sep')&&document.getElementsByClassName('xtb-sep')[0] != null){// xtb-sep;隐藏工具栏
		tempValue = document.getElementsByClassName('xtb-sep')[0].id;
	}
	if(tempValue != null && tempValue != ''){
		document.getElementById(tempValue).className=''
	}
	collapseSearchPanel();// 收起查询面板
	if(isadd){
		qzCombaseInfo.form.findField('CUST_TYPE').setValue(myType);
	}
 	setTimeout(function () {  
       //add by liuming 20170825
	   validateBusiLicNo();
	},4000); 
};

/**
 * 视图隐藏之前触发
 * @param {} view 当前视图
 * @return {Boolean}
 */
var beforeviewhide =  function(view) {
	opNoRead();
    Ext.getCmp('add').setVisible(!JsContext.checkGrant('latent_add'));// 新增
	if(tempValue != null && tempValue != ''){
		document.getElementById(tempValue).className='xtb-sep'
	}// xtb-sep;隐藏工具栏
	Ext.getCmp('detail').setVisible(!JsContext.checkGrant('latent_detail'));// 详情
	Ext.getCmp('update').setVisible(!JsContext.checkGrant('latent_update'));// 修改
	Ext.getCmp('delete').setVisible(!JsContext.checkGrant('latent_delete'));// 删除
	Ext.getCmp('changeToLs').setVisible(!JsContext.checkGrant('latent_ls'));// 转临时户
	Ext.getCmp('changeToZs').setVisible(!JsContext.checkGrant('latent_zs'));// 转正式户
	expandSearchPanel();
//  window.location.reload();
//  Ext.getCmp('myview').load();
	// 第一页既有客户是否只读参数(下拉框)
	qzCombaseInfo.form.findField('NATION_CODE').isRead = undefined;
	qzCombaseInfo.form.findField('AREA_CODE').isRead = undefined;
	qzCombaseInfo.form.findField('HQ_NATION_CODE').isRead = undefined;
	qzCombaseInfo.form.findField('STAFFIN').isRead = undefined;
	qzCombaseInfo.form.findField('RISK_NATION_CODE').isRead = undefined;
	qzCombaseInfo.form.findField('ENT_SCALE').isRead = undefined;
	qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').isRead = undefined;
	qzCombaseInfo.form.findField('SALE_CCY').isRead= undefined;
	qzCombaseInfo.form.findField('IN_CLL_TYPE').isRead= undefined;
	qzCombaseInfo.form.findField('IDENT_END_DATE').isRead= undefined;
	qzCombaseInfo.form.findField('BUILD_DATE').isRead= undefined;
	qzCombaseInfo.form.findField('LEGAL_IDENT_EXPIRED_DATE').isRead= undefined;
	// 第二页既有客户是否只读参数(下拉框)
	qzComLists.form.findField('LNCUSTP').isRead= undefined;
	qzComLists.form.findField('ORG_SUB_TYPE').isRead= undefined;
	qzComLists.form.findField('IF_ORG_SUB_TYPE').isRead= undefined;
	qzComLists.form.findField('ORG_TYPE').isRead= undefined;
	qzComLists.form.findField('IS_FAX_TRANS_CUST').isRead= undefined;
	qzComLists.form.findField('IS_TAIWAN_CORP').isRead= undefined;
	// 第一页既有客户是否只读参数(文本框)
	qzCombaseInfo.form.findField('BUSI_LIC_NO').isRead= undefined;
	qzCombaseInfo.form.findField('SW_REGIS_CODE').isRead= undefined;
	qzCombaseInfo.form.findField('CREDIT_CODE').isRead= undefined;
	qzCombaseInfo.form.findField('LEGAL_REPR_NAME').isRead= undefined;
	qzCombaseInfo.form.findField('REGISTER_CAPITAL').isRead= undefined;
	qzCombaseInfo.form.findField('SALE_AMT').isRead= undefined;
	qzCombaseInfo.form.findField('LEGAL_REPR_IDENT_NO').isRead= undefined;
	qzCombaseInfo.form.findField('LEGAL_REPR_IDENT_TYPE').isRead= undefined;
	// 第三页既有客户是否只读参数(下拉框)
	qzComOther1Info.form.findField('AGENT_NATION_CODE').isRead= undefined;
	qzComOther1Info.form.findField('GRADE_IDENT_TYPE').isRead= undefined;
	// 第三页既有客户是否只读参数(文本框)
	qzComOther1Info.form.findField('AGENT_NAME').isRead= undefined;
	qzComOther1Info.form.findField('TEL').isRead= undefined;
	qzComOther1Info.form.findField('GRADE_IDENT_NO').isRead= undefined;
};


/**
 * helin
 * @param store 要遍历的store
 * @param keyField 要比较的字段
 * @param keyValue 要比较的字段的值
 * @param valueField 要获取值的字段
 */
var getStoreFieldValue = function(store,keyField,keyValue,valueField){
	for(var i=0;i<store.getCount();i++){
		if(store.getAt(i).data[keyField] == keyValue){
			return store.getAt(i).data[valueField];
		}
	}
	return keyValue;
};


/**
 * 第一页基础信息的校验
 * @param {} viewtitle
 * @return {Boolean}
 */
var validqzComFirstFn = function(viewtitle){
//  var tempJson2 = qzCombaseInfo.getForm().getValues(false);
	var tempJson2=qzCombaseInfo.form.getFieldValues(false);
	
	var identType=qzCombaseInfo.form.findField('IDENT_TYPE').getValue();// 证件类型
	var regCodeType=qzCombaseInfo.form.findField('REG_CODE_TYPE').getValue();// 登记注册号类型
	var creditCode=qzCombaseInfo.form.findField('CREDIT_CODE').getValue();// 机构信用代码
	var identType=qzCombaseInfo.form.findField('IDENT_TYPE').getValue();// 证件类型
	var identNo=qzCombaseInfo.form.findField('IDENT_NO').getValue();// 证件号码
	var inoutFlag=qzComLists.form.findField('INOUT_FLAG').getValue();// 境内/外标志
	var hqNationCode=qzCombaseInfo.form.findField('HQ_NATION_CODE').getValue();// 总部所在国别
	var nationCode=qzCombaseInfo.form.findField('NATION_CODE').getValue();// 企业所在国别
	var areaCode=qzCombaseInfo.form.findField('AREA_CODE').getValue();// 地区代码
	var registerCapitalCurr=qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').getValue();// 注册资本币种
	var registerCapital= Ext.util.Format.trim(qzCombaseInfo.form.findField('REGISTER_CAPITAL').getValue());// 注册资本(万元)
	var saleCcy=qzCombaseInfo.form.findField('SALE_CCY').getValue();// 年销售额币别
	var saleAmt= Ext.util.Format.trim(qzCombaseInfo.form.findField('SALE_AMT').getValue());// 年销售额(万元)
	var regiserNo= Ext.util.Format.trim(qzCombaseInfo.form.findField('REGISTER_NO').getValue());// 登记注册号号码
	
	var accOpenLicense= Ext.util.Format.trim(qzCombaseInfo.form.findField('ACC_OPEN_LICENSE').getValue());// 开户许可证核批号
	var swRegisCode= Ext.util.Format.trim(qzCombaseInfo.form.findField('SW_REGIS_CODE').getValue());// 税务登记证
	var areaRegCode= Ext.util.Format.trim(qzCombaseInfo.form.findField('AREA_REG_CODE').getValue());//地税
	var nationRegCode= Ext.util.Format.trim(qzCombaseInfo.form.findField('NATION_REG_CODE').getValue());//国税
	//国税
	
	// 1、证件类型：【登记注册号类型】为“99-其他”时，【证件类型必选组织机构代码（附码）】
	if(regCodeType == '99' && identType != '2X'){
		Ext.Msg.alert('提示','第一页基本信息校验失败，当“登记注册类型”为“其他”时，证件类型必须为“境外登记证件代码(赋码)”！');
		enableSaveBtn();
		return false;
	}
	// 2、证件类型：【登记注册号类型】不为“99-其他”时，【证件类型必选组织机构代码】
	if(regCodeType != '99' &&regCodeType!='' && identType != '20'){
		Ext.Msg.alert('提示','第一页基本信息校验失败，当“登记注册类型”不为“其他”时，证件类型必须为“境内组织机构代码”！');
		enableSaveBtn();
		return false;
	}
	// 3.1证件号码：【证件类型】选组织机构代码时，证件号码不能有空格
	if(identNo.indexOf(' ')>-1){
		Ext.Msg.alert('提示','第一页基本信息校验失败，证件号码不能有空格”！');
		enableSaveBtn();
		return false;
	}
	
	// 3.2证件号码：【证件类型】选组织机构代码时，证件号码必为10位，且满足xxxxxxxx-x形式
	if(identType == '20'&& identNo.length !=10){
		qzCombaseInfo.getForm().findField('NATION_CODE').setValue('CHN');//中国
		qzComLists.getForm().findField('INOUT_FLAG').setValue('D');//境内
		Ext.Msg.alert('提示','第一页基本信息校验失败，当证件类型为“境内组织机构代码时，证件号码必须为10 位”！');
		enableSaveBtn();
		return false;
	}
	// 3.3证件号码：【证件类型】选组织机构代码时，证件号码必为10位，且满足xxxxxxxx-x形式
	if(identType == '20'&& identNo.indexOf('-')!=8){
		Ext.Msg.alert('提示','第一页基本信息校验失败，当证件类型为“境内组织机构代码时，证件号码必须满足xxxxxxxx-x形式”！');
		enableSaveBtn();
		return false;
	}

	// 新增潜在客户，修改既有客户时，只要校验op字段
	if(viewtitle=='新增潜在客户'||viewtitle=='修改既有客户'){
	}else{
		// 其他情况还需校验信贷的字段
		// 9.3登记注册号类型:当“登记注册号类型”选择“其他”时，机构信用代码必填
        // if(regCodeType != '' && regCodeType == '99'&&creditCode==''){
        //    Ext.Msg.alert('提示','第一页注册信息校验失败，当“登记注册号类型”选择“其他”时，"机构信用代码"必填！');
        //    return false;
        //  }
		// 校验中征码
		if(tempJson2.LOAN_CARD_NO!=null && tempJson2.LOAN_CARD_NO!=''){	
			
			var CheckDKK=function (financecodeStr){
		      /*
			   * 检查贷款卡号函数      * 返回值为true是贷款卡号符合规则 为false贷款卡号不符合规则      
			   */
				if(financecodeStr.length!=16) {
					enableSaveBtn();
					return false;
				}
				var financecode=new Array();
				financecode[0]=financecodeStr.charCodeAt(0);
				financecode[1]=financecodeStr.charCodeAt(1);
				financecode[2]=financecodeStr.charCodeAt(2);
				financecode[3]=financecodeStr.charCodeAt(3);
				financecode[4]=financecodeStr.charCodeAt(4);
				financecode[5]=financecodeStr.charCodeAt(5);
				financecode[6]=financecodeStr.charCodeAt(6);
				financecode[7]=financecodeStr.charCodeAt(7);
				financecode[8]=financecodeStr.charCodeAt(8);
				financecode[9]=financecodeStr.charCodeAt(9);
				financecode[10]=financecodeStr.charCodeAt(10);
				financecode[11]=financecodeStr.charCodeAt(11);
				financecode[12]=financecodeStr.charCodeAt(12);
				financecode[13]=financecodeStr.charCodeAt(13);
				financecode[14]=financecodeStr.charCodeAt(14);
				financecode[15]=financecodeStr.charCodeAt(15);
				var weightValue = new Array();
				var checkValue = new Array();
				var totalValue = 0;
				var c = 0;
				weightValue[0] = 1;
				weightValue[1] = 3;
				weightValue[2] = 5;
				weightValue[3] = 7;
				weightValue[4] = 11;
				weightValue[5] = 2;
				weightValue[6] = 13;
				weightValue[7] = 1;
				weightValue[8] = 1;
				weightValue[9] = 17;
				weightValue[10] = 19;
				weightValue[11] = 97;
				weightValue[12] = 23;
				weightValue[13] = 29;
				for (var j = 0; j < 14; j++)
				{
					if (financecode[j] >= 65 && financecode[j] <= 90){// 大写字母A-Z
						checkValue[j] = (financecode[j] - 65) + 10;
					}
					else if (financecode[j] >= 48 && financecode[j] <= 57)// 数字0-9
					{
						checkValue[j] = financecode[j] - 48;
					}
					else{
						enableSaveBtn();
						return false;
					}
					totalValue += weightValue[j] * checkValue[j];
				}
				c = 1 + totalValue % 97;
				var val = (financecode[14] - 48) * 10 + (financecode[15] - 48);
				
				if(val==c){
					return true;
				}
			}
			if(tempJson2.LOAN_CARD_NO.length!=16){
				   Ext.Msg.alert('提示','第一页中征码信息校验失败，中征码必须为16位字符');
				   enableSaveBtn();
				   return false;
			}else if(! CheckDKK(tempJson2.LOAN_CARD_NO)){
				   Ext.Msg.alert('提示','第一页中征码信息校验失败，中征码不符合校验规则');
				   enableSaveBtn();
				   return false;
			}
		}		 	
	}
	
	// 1.统一社会信用代码的校验
//	if(tempJson2.BUSI_LIC_NO!=null && tempJson2.BUSI_LIC_NO!=''){
//		var endCode='';
//		var validateUnicode=function(unicode){
//			var wi=[1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28];
//			var ci=[{key:0,value:0},{key:1,value:1},{key:2,value:2},{key:3,value:3},{key:4,value:4},{key:5,value:5},{key:6,value:6},{key:7,value:7},{key:8,value:8},{key:9,value:9},{key:'A',value:10},{key:'B',value:11},{key:'C',value:12},{key:'D',value:13},{key:'E',value:14},{key:'F',value:15},{key:'G',value:16},{key:'H',value:17},{key:'J',value:18},{key:'K',value:19},{key:'L',value:20},{key:'M',value:21},{key:'N',value:22},{key:'P',value:23},{key:'Q',value:24},{key:'R',value:25},{key:'T',value:26},{key:'U',value:27},{key:'W',value:28},{key:'X',value:29},{key:'Y',value:30}];
//			var ciE=[{key:0,value:0},{key:1,value:1},{key:2,value:2},{key:3,value:3},{key:4,value:4},{key:5,value:5},{key:6,value:6},{key:7,value:7},{key:8,value:8},{key:9,value:9},{key:10,value:'A'},{key:11,value:'B'},{key:12,value:'C'},{key:13,value:'D'},{key:14,value:'E'},{key:15,value:'F'},{key:16,value:'G'},{key:17,value:'H'},{key:18,value:'J'},{key:19,value:'K'},{key:20,value:'L'},{key:21,value:'M'},{key:22,value:'N'},{key:23,value:'P'},{key:24,value:'Q'},{key:25,value:'R'},{key:26,value:'T'},{key:27,value:'U'},{key:28,value:'W'},{key:29,value:'X'},{key:30,value:'Y'}];
//			var sum=0;
//			var unicodes=unicode.split("");
//			var civ=0;
//			for(var i=0;i<17;i++){
//				for(var j=0;j<31;j++){
//					if(ci[j].key==unicodes[i]){
//						civ=ci[j].value;
//					}
//				}
//				sum+=wi[i]*civ;
//			}
//			var valCodePosition =31-(sum%31);
//			if(valCodePosition==31){
//				valCodePosition=0;
//			}else{
//				valCodePosition =31-(sum%31)
//			}
//// var endCode='';
//			for(var k=0;k<31;k++){
//				if(ciE[k].key==valCodePosition){
//					endCode=ciE[k].value;
//				}
//			}
//			if(unicodes[17]==endCode){
//				return true;
//			}else{
//				return false;
//			}
//		}
//			   if(tempJson2.BUSI_LIC_NO.length!=18 &&tempJson2.BUSI_LIC_NO!=''){
//				   Ext.Msg.alert('提示','第一页基本信息校验失败，统一社会信用码必须为18位字符');
//				   return false;
//			   }else if(!validateUnicode(tempJson2.BUSI_LIC_NO)){
//				   Ext.Msg.alert('提示','第一页基本信息校验失败，统一社会信用码不符合校验规则,请修改最后一位为：'+endCode);
//				   return false;
//			   }else if(tempJson2.BUSI_LIC_NO.length==18){
//				   if(qzCombaseInfo.form.findField('IDENT_TYPE').getValue()=='20'){// 境内组织机构代码
//					   var IDENT_NO=identNo.replace(/-/g,'');
//					   	if(IDENT_NO!=tempJson2.BUSI_LIC_NO.substr(8,9)){
//					   		Ext.MessageBox.confirm(   
//					                "提示"  
//					               ,"境内组织机构代码与社会统一信用代码9-17位不匹配，是否自动更改境内组织机构代码？"  
//					               ,function( button ){  
//					                   if( button == 'yes'){
//					                	   var IDENT_NO_temp=tempJson2.BUSI_LIC_NO.substr(8,8)+'-'+tempJson2.BUSI_LIC_NO.substr(16,1);
//					                	   	qzCombaseInfo.getForm().findField('IDENT_NO').setValue(IDENT_NO_temp);
//					                   } else if(button == 'no'){
//					                	   return false;
//					                   } else{
//					                	   return false;
//					                   }
//					               }   
//					           );
//					   		return false;
//					   	}
//					}
//					if((qzCombaseInfo.form.findField('IDENT_TYPE').getValue()!='20')){
//                    fsxCombaseInfo.getForm().findField('IDENT_TYPE').setValue('20');
//                    var IDENT_NO_temp=tempJson2.BUSI_LIC_NO.substr(8,8)+'-'+tempJson2.BUSI_LIC_NO.substr(16,1);
//                    fsxCombaseInfo.getForm().findField('IDENT_NO').setValue(IDENT_NO_temp);
//					}
//			   }
//		}
	// 2.证件类型只能是境内组织机构代码或者境外登记证件代码(赋码)
	if(viewtitle.indexOf("既有客户")>-1){
		//既有客户时不校验
	}else{
		if(identType != '' && identType != '20' && identType != '2X'){
			Ext.Msg.alert('提示','第一页基本信息校验失败，证件类型必须为“境内组织机构代码”或“境外登记证件代码(赋码)”！');
			enableSaveBtn();
			return false;
		}
	}
	
	// 3.1总部所在国别：境内/外标志为“境外”时，总部所在国别必须输入
	if(inoutFlag == 'F' && hqNationCode == ''){
		Ext.Msg.alert('提示','第一页分类信息校验失败，境内/外标志为“境外”时，总部所在国别必须输入！');
		enableSaveBtn();
		return false;
	}
	// 3.2总部所在国别，警告不影响程序向下执行：境内/外标志为“境内”时，总部所在国别不允许输入，已设置为空
	if(inoutFlag == 'D' &&　hqNationCode != ''){
		qzCombaseInfo.getForm().findField('HQ_NATION_CODE').setValue('');
		showMsgNotification('注意：第一页分类信息校验失败，境内/外标志为“境内”时，总部所在国别不允许输入，已设置为空！');
	}
	// 4.1企业所在国别：境内/外标志为“境内”时，所在国别只能选择“中国”且地区代码不能选择“台湾”、“香港”、“澳门”
	if(inoutFlag == 'D' && (nationCode != 'CHN' 
		|| areaCode == '710000'
		|| areaCode == '810000'
		|| areaCode == '820000')){
//      qzCombaseInfo.getForm().findField('NATION_CODE').setValue('CHN');
		Ext.Msg.alert('提示','第一页分类信息校验失败，境内/外标志为“境内”时，企业所在国别只能选择“中国”且地区代码不能选择“台湾”、“香港”、“澳门”！');
		enableSaveBtn();
		return false;
	}
	// 4.2企业所在国别：境内/外标志为“境外”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”
	if(inoutFlag == 'F' && nationCode == 'CHN'
		&& (areaCode != '710000'
		&& areaCode != '810000'
		&& areaCode != '820000')){
		Ext.Msg.alert('提示','第一页分类信息校验失败，境内/外标志为“境外”且企业所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”！');
		enableSaveBtn();
		return false;
	}
	// 4.3企业所在国别：证件类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，所在国别必须为“中国”且地区代码不能选择“台湾”、“香港”、“澳门”
	if((identType == '20' || identType == '0' || identType == 'X4')
		&& (nationCode != 'CHN'
		 || (nationCode == 'CHN' && (areaCode == '710000'
			|| areaCode == '810000'
			|| areaCode == '820000')))){
		Ext.Msg.alert('提示','第一页分类信息校验失败，证件类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，企业所在国别必须为“中国”且地区代码不能选择“台湾”、“香港”、“澳门”！');
		enableSaveBtn();
		return false;
	}
	// 5.1地区代码:
	// 证件类型为“境外登记证件代码(赋码)”、“境外居民护照”、“旅行证件”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”
	if((identType == '2X' || identType == '2' || identType == 'X3')
		&& nationCode == 'CHN' 
		&& (areaCode != '710000' && areaCode != '810000' && areaCode != '820000')){
		Ext.Msg.alert('提示','第一页分类信息校验失败，证件类型为“境外登记证件代码(赋码)”、“境外居民护照”、“旅行证件”且企业所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”！');
		enableSaveBtn();
		return false;
	}
	// 5.2地区代码: 证件类型为“台湾居民身份证”且所在国别为“中国”时，地区代码只能选择“台湾”
	if((identType == 'X2' || identType == '2') && nationCode == 'CHN' && areaCode != '710000'){
		Ext.Msg.alert('提示','第一页分类信息校验失败，证件类型为“台湾居民身份证”或“境外居民护照”，且企业所在国别为“中国”时，地区代码只能选择“台湾”！');
		enableSaveBtn();
		return false;
	}
	// 5.3地区代码: 证件类型为“港澳居民身份证”且所在国别为“中国”时，地区代码只能选择“香港”、“澳门”
	if(identType == 'X1' && nationCode == 'CHN'
		&& (areaCode != '810000' && areaCode != '820000')){
		Ext.Msg.alert('提示','第一页分类信息校验失败，证件类型为“港澳居民身份证”且企业所在国别为“中国”时，地区代码只能选择“香港”、“澳门”！');
		enableSaveBtn();
		return false;
	}
	// 6.针对OP使用的全行客户查询中， 证件类型为‘境外登记证件代码’，且境内外标志位为‘境外’的企业客户，
	if(identType == '2X' && inoutFlag == 'F' && identNo.length != 9){
		Ext.Msg.alert('提示','第一页基本信息校验失败，证件类型为“境外登记证件代码”境内外标志位“境外”时，对应证件号码必须输为9位字符');
		enableSaveBtn();
		return false;
	}
	// 7.必须保证注册资本币种、注册资本(万元)同时有值
	if((registerCapitalCurr == '' || registerCapital == '')
		&& (registerCapitalCurr != '' || registerCapital != '')){
		Ext.Msg.alert('提示','第一页注册信息校验失败，必须保证注册资金币种、注册资金(万元)同时有值！');
		enableSaveBtn();
		return false;
	}
	// 8.必须保证年销售额币别、年销售额(万元)同时有值
	// modify by liuming 20170824,既有客户时不校验年销售额币别、年销售额(万元)
	if(!isGy()){
		if((saleCcy == '' || saleAmt == '')
				&& (saleCcy != '' || saleAmt != '')){
				Ext.Msg.alert('提示','第一页经营信息校验失败，必须保证年销售额币别、年销售额(万元)同时有值！');
				enableSaveBtn();
				return false;
		}
	}
	//8.1年销售额必须是金额类型
	var reg=/^[-]?[0-9]+([.][0-9]+){0,1}$/;
	if (!reg.test(saleAmt)&&saleAmt.length!=0){
		Ext.Msg.alert('提示','第一页经营信息校验失败，必须保证年销售额（万元）输入如下格式：999999.9，-9999999.9！');
		enableSaveBtn();
		return false;
	}
	//8.2
	if(!reg.test(registerCapital)&&registerCapital.length!=0){
		Ext.Msg.alert('提示','第一页经营信息校验失败，必须保证注册资金（万元）输入如下格式：999999.9，-9999999.9！');
		enableSaveBtn();
		return false;
	}

	// 9.1登记注册号类型：当“登记注册号类型选择“工商注册号”时，长度必须为15、18或20,且不能有空格
	if(regiserNo.indexOf(' ')>-1){
		Ext.Msg.alert('提示','第一页注册信息校验失败，当“登记注册号类型”选择“工商注册号”时，不能有空格！');
		enableSaveBtn();
		return false;
	}
	if(regCodeType != '' && regCodeType == '01'){
		qzCombaseInfo.form.findField('REGISTER_NO').setValue(regiserNo);
//      qzCombaseInfo.form.findField('AREA_REG_CODE').setValue(regiserNo);
//      qzCombaseInfo.form.findField('NATION_REG_CODE').setValue(regiserNo);
		if(regiserNo.length!=15){			
			Ext.Msg.alert('提示','第一页注册信息校验失败，当“登记注册号类型”选择“工商注册号”时，登记注册号码长度必须为15！');
			enableSaveBtn();
			return false;
		}
		// 地税税务登记代码：NATION_REG_CODE
		if(areaRegCode.length!=15 && areaRegCode.length!=18 &&areaRegCode.length!=20){					
			Ext.Msg.alert('提示','第一页基本信息校验失败，“地税税务登记代码”的位数应为15位、18位或20位！');
			enableSaveBtn();
			return false;
		}
		// 国税税务登记证代码：NATION_REG_CODE
		if(nationRegCode.length!=15 && nationRegCode.length!=18 &&nationRegCode.length!=20){					
			Ext.Msg.alert('提示','第一页基本信息校验失败，“国税税务登记代码”的位数应为15位、18位或20位！');
			enableSaveBtn();
			return false;
		}
	}
	// 9.2登记注册号类型:当“登记注册号类型”选择“其他”时，长度必须为18,且不能有空格
//	if(regCodeType != '' && regCodeType == '99'){		
//		if(regiserNo.length!=18){			
//			Ext.Msg.alert('提示','第一页注册信息校验失败，当“登记注册号类型”选择“其他”时，"登记注册号码"长度必须为18！');
//			Ext.getCmp('first_save').disabled=false;
//			Ext.getCmp('second_save').disabled=false;
//			Ext.getCmp('third_save').disabled=false;
//			Ext.getCmp('forth_save').disabled=false;
//			Ext.getCmp('fifth_save').disabled=false;
//			return false;
//		}
//	}
	// 9.3登记注册号类型:当“登记注册号类型”选择“统一社会信用代码”时
	if(regCodeType == '07' ){
		var endCode='';
		var validateUnicode=function(unicode){
			var wi=[1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28];
			var ci=[{key:0,value:0},{key:1,value:1},{key:2,value:2},{key:3,value:3},{key:4,value:4},{key:5,value:5},{key:6,value:6},{key:7,value:7},{key:8,value:8},{key:9,value:9},{key:'A',value:10},{key:'B',value:11},{key:'C',value:12},{key:'D',value:13},{key:'E',value:14},{key:'F',value:15},{key:'G',value:16},{key:'H',value:17},{key:'J',value:18},{key:'K',value:19},{key:'L',value:20},{key:'M',value:21},{key:'N',value:22},{key:'P',value:23},{key:'Q',value:24},{key:'R',value:25},{key:'T',value:26},{key:'U',value:27},{key:'W',value:28},{key:'X',value:29},{key:'Y',value:30}];
			var ciE=[{key:0,value:0},{key:1,value:1},{key:2,value:2},{key:3,value:3},{key:4,value:4},{key:5,value:5},{key:6,value:6},{key:7,value:7},{key:8,value:8},{key:9,value:9},{key:10,value:'A'},{key:11,value:'B'},{key:12,value:'C'},{key:13,value:'D'},{key:14,value:'E'},{key:15,value:'F'},{key:16,value:'G'},{key:17,value:'H'},{key:18,value:'J'},{key:19,value:'K'},{key:20,value:'L'},{key:21,value:'M'},{key:22,value:'N'},{key:23,value:'P'},{key:24,value:'Q'},{key:25,value:'R'},{key:26,value:'T'},{key:27,value:'U'},{key:28,value:'W'},{key:29,value:'X'},{key:30,value:'Y'}];
			var sum=0;
			var unicodes=unicode.split("");
			var civ=0;
			for(var i=0;i<17;i++){
				for(var j=0;j<31;j++){
					if(ci[j].key==unicodes[i]){
						civ=ci[j].value;
					}
				}
				sum+=wi[i]*civ;
			}
			var valCodePosition =31-(sum%31);
			if(valCodePosition==31){
				valCodePosition=0;
			}else{
				valCodePosition =31-(sum%31)
			}
//          var endCode='';
			for(var k=0;k<31;k++){
				if(ciE[k].key==valCodePosition){
					endCode=ciE[k].value;
				}
			}
			if(unicodes[17]==endCode){
				return true;
			}else{
				return false;
			}
        }
        if(regiserNo.length!=18 &&regiserNo!=''){
            Ext.Msg.alert('提示','第一页基本信息校验失败，当“登记注册号类型”选择“统一社会信用代码”时，“登记注册号码”必须为18位字符');
            enableSaveBtn();
            return false;
        }
	   if(!validateUnicode(regiserNo)){
            Ext.Msg.alert('提示','第一页基本信息校验失败，当“登记注册号类型”选择“统一社会信用代码”时，“登记注册号码”不符合校验规则,请修改最后一位为：'+endCode);
            enableSaveBtn();
            return false;
	   }
        if(isGy()){//不能修改既有客户的统一社会信用代码 20170822
            if(regiserNo != qzCombaseInfo.form.findField('BUSI_LIC_NO').getValue()){
                Ext.Msg.alert('提示','第一页基本信息校验失败,当“登记注册号类型”选择“统一社会信用代码”时,“登记注册号码”与开户统一社会信用代码不一致,请核对或联系开户同事修改!');
                enableSaveBtn();
                return false;
            }
        }
       if(regiserNo.length==18){
		   if(qzCombaseInfo.form.findField('IDENT_TYPE').getValue()=='20'){// 境内组织机构代码
			   var IDENT_NO=identNo.replace(/-/g,'');
			   	if(IDENT_NO!=tempJson2.BUSI_LIC_NO.substr(8,9)){
			   		if(isGy()){//不能修改既有客户的组织机构代码
			    		Ext.Msg.alert('提示','第一页基本信息校验失败,当“登记注册号类型”选择“统一社会信用代码”时,“登记注册号码”的9-17位与境内组织机构代码不一致,请核对或联系开户同事修改!');
			    		enableSaveBtn();
						return false;
			   		}else{
				   		Ext.MessageBox.confirm(   
				                "提示"  
				               ,"境内组织机构代码与社会统一信用代码9-17位不匹配，是否自动更改境内组织机构代码？"  
				               ,function( button ){  
				                   if( button == 'yes'){
				                	   var IDENT_NO_temp=tempJson2.BUSI_LIC_NO.substr(8,8)+'-'+tempJson2.BUSI_LIC_NO.substr(16,1);
				                	   	qzCombaseInfo.getForm().findField('IDENT_NO').setValue(IDENT_NO_temp);
				                   } else if(button == 'no'){
				                	   enableSaveBtn();
				                	   return false;
				                   } else{
				                	   enableSaveBtn();
				                	   return false;
				                   }
				               }   
				           );
				   		enableSaveBtn();
				   		return false;
			   		}
			   	}
			}
		  //税务登记证代码
		   var swRegisCode1=swRegisCode.replace(/-/g,'');
//		   if(swRegisCode1!=tempJson2.BUSI_LIC_NO.substr(8,9)){
//		   		Ext.MessageBox.confirm(   
//		                "提示"  
//		               ,"税务登记证编号与社会统一信用代码9-17位不匹配，是否自动更改税务登记证编号？"  
//		               ,function( button ){  
//		                   if( button == 'yes'){
//		                	   var swRegisCodetemp=tempJson2.BUSI_LIC_NO.substr(8,9);
//		                	   	qzCombaseInfo.getForm().findField('SW_REGIS_CODE').setValue(swRegisCodetemp);
//		                   } else if(button == 'no'){
//		                	   enableSaveBtn();
//		                	   return false;
//		                   } else{
//		                	   enableSaveBtn();
//		                	   return false;
//		                   }
//		               }   
//		           );
//		   		enableSaveBtn();
//		   		return false;
//		   	}
		   if(swRegisCode1!=tempJson2.BUSI_LIC_NO){
			   if(isGy()){//不能修改既有客户的税务登记证编号
					Ext.Msg.alert('提示','第一页基本信息校验失败,当“登记注册号类型”选择“统一社会信用代码”时,“登记注册号码”与税务登记证编号不一致,请核对或联系开户同事修改!');
		    		enableSaveBtn();
					return false;
			   }else{
			   		Ext.MessageBox.confirm(   
			                "提示"  
			               ,"税务登记证编号与社会统一信用代码不匹配，是否自动更改税务登记证编号？"  
			               ,function( button ){  
			                   if( button == 'yes'){
			                	   var swRegisCodetemp=tempJson2.BUSI_LIC_NO;
			                	   qzCombaseInfo.getForm().findField('SW_REGIS_CODE').setValue(swRegisCodetemp);
			                   } else if(button == 'no'){
			                	   enableSaveBtn();
			                	   return false;
			                   } else{
			                	   enableSaveBtn();
			                	   return false;
			                   }
			               }   
                    );
			   		enableSaveBtn();
			   		return false;
			   }
		   	}
		  // 地税
		   var areaRegCode1=areaRegCode.replace(/-/g,'');
		   if(areaRegCode1!=tempJson2.BUSI_LIC_NO){
		   		Ext.MessageBox.confirm(   
		                "提示"  
		               ,"地税税务登记证代码与社会统一信用代码不匹配，是否自动更改地税税务登记证代码？"  
		               ,function( button ){  
		                   if( button == 'yes'){
		                	   var areaRegCodetemp=tempJson2.BUSI_LIC_NO;
		                	   	qzCombaseInfo.getForm().findField('AREA_REG_CODE').setValue(areaRegCodetemp);
		                   } else if(button == 'no'){
		                	   enableSaveBtn();
		                	   return false;
		                   } else{
		                	   enableSaveBtn();
		                	   return false;
		                   }
		               }   
                );
		   		enableSaveBtn();
		   		return false;
			}
		   // 国税
		   var nationRegCode1=nationRegCode.replace(/-/g,'');
	   	   if(nationRegCode1!=tempJson2.BUSI_LIC_NO){
		   		Ext.MessageBox.confirm(   
		                "提示"  
		               ,"国税税务登记证代码与社会统一信用代码不匹配，是否自动更改国税税务登记证代码？"  
		               ,function( button ){  
		                   if( button == 'yes'){
		                	   var nationRegCodetemp=tempJson2.BUSI_LIC_NO;
		                	   	qzCombaseInfo.getForm().findField('NATION_REG_CODE').setValue(nationRegCodetemp);
		                   } else if(button == 'no'){
		                	   enableSaveBtn();
		                	   return false;
		                   } else{
		                	   enableSaveBtn();
		                	   return false;
		                   }
		               }   
                );
		   		enableSaveBtn();
		   		return false;
		   	}
			if((qzCombaseInfo.form.findField('IDENT_TYPE').getValue()!='20')){
//                  fsxCombaseInfo.getForm().findField('IDENT_TYPE').setValue('20');
//                  var IDENT_NO_temp=tempJson2.BUSI_LIC_NO.substr(8,8)+'-'+tempJson2.BUSI_LIC_NO.substr(16,1);
//                  fsxCombaseInfo.getForm().findField('IDENT_NO').setValue(IDENT_NO_temp);
			}
	   }
	}else{
		//不为统一社会信用代码，统一社会信用代码清空
		if(!isGy()){//不能修改既有客户的统一社会信用代码 20170822
			qzCombaseInfo.form.findField('BUSI_LIC_NO').setValue('');//统一社会信用代码
		}
		//9.4当登记注册类型不选择“统一社会信用代码”时，校验税务登记证代码的位数
		// 税务登记证编号：SW_REGIS_CODE
		if(swRegisCode!='' &&swRegisCode.length!=15 && swRegisCode.length!=18 &&swRegisCode.length!=20){					
            Ext.Msg.alert('提示','第一页基本信息校验失败，“税务登记证编号”的位数应为15位、18位或20位！');
			enableSaveBtn();
			return false;
		}
		// 地税税务登记证代码：AREA_REG_CODE
        if(areaRegCode!='' &&areaRegCode.length!=15 && areaRegCode.length!=18 &&areaRegCode.length!=20){					
            Ext.Msg.alert('提示','第一页基本信息校验失败，“地税税务登记代码”的位数应为15位、18位或20位！');
            enableSaveBtn();
            return false;
        }
        // 国税税务登记证代码：NATION_REG_CODE
        if(nationRegCode!='' &&nationRegCode.length!=15 && nationRegCode.length!=18 &&nationRegCode.length!=20){					
            Ext.Msg.alert('提示','第一页基本信息校验失败，“国税税务登记代码”的位数应为15位、18位或20位！');
            enableSaveBtn();
            return false;
        }
	}
	// 10.1开户许可证核批号:当于我行开户时,为必填项?校验位数14位,且不可存有空格:ACC_OPEN_LICENSE
    if(accOpenLicense.indexOf(' ')>-1){					
	   Ext.Msg.alert('提示','第一页基本信息校验失败，“开户许可证核批号”不能存有空格！');
	   enableSaveBtn();
	   return false;
	}
	if(accOpenLicense!=''&&accOpenLicense.length!=14){					
	   Ext.Msg.alert('提示','第一页基本信息校验失败，“开户许可证核批号”必须为14位！');
	   enableSaveBtn();
	   return false;
	}
    //add by liuming 20170831单独增加日期控件的校验
    var ident_end_date = qzCombaseInfo.form.findField('IDENT_END_DATE').getRawValue();
    if(!CheckDate('证件到期日',ident_end_date)){
        qzCombaseInfo.form.findField('IDENT_END_DATE').markInvalid(ident_end_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
        Ext.Msg.alert('提示信息', "第一屏校验失败：证件到期日【"+ident_end_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
       enableSaveBtn();
       return false;
    }
    var register_date = qzCombaseInfo.form.findField('REGISTER_DATE').getRawValue();
    if(!CheckDate('注册登记日期',register_date)){
        qzCombaseInfo.form.findField('REGISTER_DATE').markInvalid(register_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
        Ext.Msg.alert('提示信息', "第一屏校验失败：注册登记日期【"+register_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
        enableSaveBtn();
        return false;
    }
    var legal_ident_expired_date = qzCombaseInfo.form.findField('LEGAL_IDENT_EXPIRED_DATE').getRawValue();
    if(!CheckDate('法定代表人证件失效日期',legal_ident_expired_date)){
        qzCombaseInfo.form.findField('LEGAL_IDENT_EXPIRED_DATE').markInvalid(legal_ident_expired_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
        Ext.Msg.alert('提示信息', "第一屏校验失败：法定代表人证件失效日期【"+legal_ident_expired_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
        enableSaveBtn();
    	return false;
    }
    var build_date1 = qzCombaseInfo.form.findField('BUILD_DATE').getRawValue();
    if (!CheckDate('成立日期', build_date1)) {
    qzCombaseInfo.form.findField('BUILD_DATE').markInvalid(build_date1+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    Ext.Msg.alert('提示信息', "第一屏校验失败：成立日期【"+build_date1+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
    	enableSaveBtn();
    	return false;
    }
    var end_date1 = qzCombaseInfo.form.findField('END_DATE').getRawValue();
    if(!CheckDate('注册登记证有效期',end_date1)){
        qzCombaseInfo.form.findField('END_DATE').markInvalid(end_date1+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
        Ext.Msg.alert('提示信息', "第一屏校验失败：注册登记证有效期【"+end_date1+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
    	enableSaveBtn();
    	return false;
    }
	return true;
};

// 第二页校验
var validqzComSecondFn=function(){
	var tempJson2 = qzComLists.getForm().getValues(false);
	// 1.1特殊监管区:若是自贸区客户，请在特殊监管区中选择自贸区
	var ifOrgSubType=qzComLists.form.findField('IF_ORG_SUB_TYPE').getValue();// 是否自贸区
	var orgSubType=qzComLists.form.findField('ORG_SUB_TYPE').getValue();// 特殊监管区
	var lncustp=qzComLists.form.findField('LNCUSTP').getValue();// 企业类型
	var orgType=qzComLists.form.findField('ORG_TYPE').getValue();// 合资类型
	var identType=qzCombaseInfo.form.findField('IDENT_TYPE').getValue();// 证件类型
	var inoutFlag=qzComLists.form.findField('INOUT_FLAG').getValue();// 境内/外标志
	var penality=qzComLists.form.findField('ENVIRO_PENALTIES').getValue();//是否发生过环保处罚事件
	var selectLength =addHappenStore.data.length;//发生日期记录条数
	
	if(tempJson2.IF_ORG_SUB_TYPE == '1' && (tempJson2.ORG_SUB_TYPE=='' || (tempJson2.ORG_SUB_TYPE!='E' && tempJson2.ORG_SUB_TYPE!='F' && tempJson2.ORG_SUB_TYPE!='G' && tempJson2.ORG_SUB_TYPE!='H' && tempJson2.ORG_SUB_TYPE!='I' && tempJson2.ORG_SUB_TYPE!='M')  )){
		Ext.Msg.alert('提示','第二页企业类型信息校验失败，若是自贸区客户，请在特殊监管区中选择自贸区！');
		enableSaveBtn();
		return false;
	}
	// 1.2特殊监管区：企业类型为“境内三资企业（保税区内）” 、“境内中资企业（保税区内） ”时，特殊监管区必须输入
	if(orgSubType == '' && (lncustp == '035' || lncustp == '054')){
		Ext.Msg.alert('提示','第二页企业类型信息校验失败，企业类型为“境内三资企业（保税区内）” 、“境内中资企业（保税区内） ”时，特殊监管区必须输入！');
		enableSaveBtn();
		return false;
	}
	// 2.合资类型:企业类型为“境内三资企业（保税区外） ” 、“境内三资企业（保税区内） ”时，合资类型必须输入
	if(orgType == '' && (lncustp == '032' || lncustp == '035')){
		Ext.Msg.alert('提示','第二页其他信息校验失败，企业类型为“境内三资企业（保税区外） ” 、“境内三资企业（保税区内）  ”时，合资类型必须输入！');
		enableSaveBtn();
		return false;
	}
	// 3.1境内/外标志--：证件类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内
	if((identType == '20' || identType == '0' || identType == 'X4') && inoutFlag != 'D'){
		Ext.Msg.alert('提示','第二页其他信息校验失败，证件类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内！');
		enableSaveBtn();
		return false;
	}
	// 3.2境内/外标志--:证件类型为“境外登记证件代码(赋码)”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外
	if((identType== '2X' || identType == 'X2' || identType == 'X1'|| identType == '2' || identType == 'X3') && inoutFlag != 'F'){
		Ext.Msg.alert('提示','第二页其他信息校验失败，证件类型为“境外登记证件代码(赋码)”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外！');
		enableSaveBtn();
		return false;
	}
	// 4.科技型企业：
	// 5.是否发生过环保处罚事件，选“是”时，发生日期必须有值；选“否”时，数据必须清空；
	if(penality=='1'&&selectLength=='0'){//选“是”并且发生日期为空时，提示
		Ext.Msg.alert('提示','第二页其他信息校验失败，是否发生过环保处罚事件为“是”、“发生日期”数据不能为空！');
		enableSaveBtn();
		return false;
	}
	if(penality=='2'&&selectLength!='0'){//选“否”并且发生日期不为空时，提示
		addHappenStore.removeAll();
		showMsgNotification('提示：‘第二页其他信息校验失败，是否发生过环保处罚事件为“否”时、“发生日期”数据清空！’!');
		enableSaveBtn();
		return false;
	}
	return true;
};

//第四页校验
var validqzComForthFn=function(){
	var tempJson2 = qzComOther1Info.getForm().getValues(false);
	// 代理人联系电话\代理人户名\代理人证件号码
	var tel=qzComOther1Info.form.findField('TEL').getValue();// 代理人联系电话
	var agentName=qzComOther1Info.form.findField('AGENT_NAME').getValue();// 代理人户名
	var gradeIdentNo=qzComOther1Info.form.findField('GRADE_IDENT_NO').getValue();// 代理人证件号码
	if(tel.length>15){
		Ext.Msg.alert('提示:‘第四页代理人信息校验失败，“代理人联系电话”长度不得超过15位’!');
		enableSaveBtn();
		return false;
	}
	if(agentName.length>35){
		Ext.Msg.alert('提示：‘第四页代理人信息校验失败，“代理人户名”长度不得超过35个汉字’!');
		enableSaveBtn();
		return false;
	}
	if(gradeIdentNo.length>26){
		Ext.Msg.alert('提示：‘第四页代理人信息校验失败，“代理人证件号码”长度不得超过26位’!');
		enableSaveBtn();
		return false;
	}
	return true;
}
/**
 * 右下角显示提示信息
 * 
 * @param {} msg 提示信息
 * @param {} error  是否错信息
 * @param {} hideDelay 延时因此时间，单位毫秒
 */
var showMsgNotification = function(msg, hideDelay, error) {
	new Ext.ux.Notification( {
		iconCls : 'ico-g-51',
		title : error ? '<font color=red>错误</font>' : '<font color=red>提示</font>',
		html : "<br><font style='font-weight:bold;' color=red text-align=left>" + msg
				+ '</font><br><br>',
		autoDestroy : true,
		plain : false,
		shadow : false,
		draggable : false,
		hideDelay : hideDelay || (error ? 30000 : 30000),
		width : 400
	}).show(document);
};

//add by liuming 设置栏位为只读项或非只读项
//ownerForm 面板
//fieldName 栏位英文名
//value 栏位值
var setReadOrNoRead  = function(ownerForm,fieldName,value){
	while(ownerForm && !ownerForm.form){
		 ownerForm=ownerForm.ownerCt;
	 }
	var title = getCustomerViewByIndex(0).title;
	var name = fieldName;
	var isRead = ownerForm.form.findField(name).isRead;
//	if(fieldName == 'AGENT_NATION_CODE' || fieldName == 'GRADE_IDENT_TYPE' 
//		|| fieldName == 'AGENT_NAME' || fieldName == 'TEL' || fieldName == 'GRADE_IDENT_NO'){
//		isRead = qzCombaseInfo.form.findField('NATION_CODE').isRead;
//	}
	if(value != ''){
		if(title.indexOf('既有客户') > -1){
    		if(fieldName == 'REGISTER_CAPITAL_CURR'){
    			if(!ownerForm.form.findField(fieldName).readOnly){
    				ownerForm.form.findField(fieldName).readOnly=true;
    				ownerForm.form.findField(fieldName).cls='x-readOnly';
    				ownerForm.form.findField(fieldName).setReadOnly(true);
    				ownerForm.form.findField(fieldName).addClass('x-readOnly');
    				ownerForm.form.findField(name).isRead = true;
    				return true;
    			}
    		 }
    		if((ownerForm.form.findField(fieldName).getXType() == 'combo' )
    			|| fieldName == 'IN_CLL_TYPE' || fieldName == 'IDENT_END_DATE' || fieldName == 'BUILD_DATE') {
    			if(!ownerForm.form.findField(fieldName).disabled){
    			    ownerForm.form.findField(fieldName).setDisabled(true);
    			    ownerForm.form.findField(fieldName).addClass('x-readOnly');
    			    ownerForm.form.findField(name).isRead = true;
    			}
    		}else{
    			if(!ownerForm.form.findField(fieldName).readOnly){
    				ownerForm.form.findField(fieldName).readOnly=true;
    				ownerForm.form.findField(fieldName).cls='x-readOnly';
    				ownerForm.form.findField(fieldName).setReadOnly(true);
    				ownerForm.form.findField(fieldName).addClass('x-readOnly');
    				ownerForm.form.findField(name).isRead = true;
    			}
    		}
		}else{ 
			isQz(ownerForm,fieldName);
		}
	}else{
		    isQz(ownerForm,fieldName);
	}
};
/**
 * 
 * @param {Boolean} ifDetail 是否点击的详情按钮状态，即只显示，不能修改，true:按钮全部隐藏;false:按钮全部显示
 */
var setDetailSave=function(ifDetail){
	Ext.getCmp('first_save').setVisible(!ifDetail);
    Ext.getCmp('second_save').setVisible(!ifDetail);
    Ext.getCmp('third_save').setVisible(!ifDetail);
    Ext.getCmp('forth_save').setVisible(!ifDetail);
    Ext.getCmp('fifth_save').setVisible(!ifDetail);
    Ext.getCmp('com_xz_2-dt').setVisible(!ifDetail);//第二页处罚发生日期新增
    Ext.getCmp('com_xg_2-dt').setVisible(!ifDetail);//第二页处罚发生日期修改
    Ext.getCmp('com_yc_2-dt').setVisible(!ifDetail);//第二页处罚发生日期移除
};

//add by liuming 20170818 设置栏位为必输项或非必输项
//formValue 面板
//name 栏位英文名
//name1 栏位中文名
//boolean true或false
var setMust = function(formValue,name,name1,boolean){
    if(boolean){
    	if(formValue.form.findField(name).label!=undefined){
			formValue.form.findField(name).label.dom.innerHTML='<font color="red">*</font>'+name1+':';
		}else{
			formValue.form.findField(name).fieldLabel='<font color="red">*</font>'+name1;
		}
		formValue.form.findField(name).allowBlank=false;
    }else{
     	if(formValue.form.findField(name).label!=undefined){
			formValue.form.findField(name).label.dom.innerHTML = name1+':';
		}else{
			formValue.form.findField(name).fieldLabel = name1;
		}
		formValue.form.findField(name).allowBlank=true;
    }
};

//是否既有客户
var isGy = function(){
	var title = getCustomerViewByIndex(0).title;
	if(title.indexOf('既有客户') > -1){
		return true;
	}else{
		return false;
	}
}

var isQz = function(ownerForm,fieldName){
	 if(fieldName == 'REGISTER_CAPITAL_CURR'){
         if(ownerForm.form.findField(fieldName).readOnly){
    	     ownerForm.form.findField(fieldName).setReadOnly(false);
    		 ownerForm.form.findField(fieldName).removeClass('x-readOnly');
    		 ownerForm.form.findField(fieldName).isRead = false; 
    		 return true;
         }
	 }
	 if((ownerForm.form.findField(fieldName).getXType() == 'combo')
				|| fieldName == 'IN_CLL_TYPE' || fieldName == 'IDENT_END_DATE' || fieldName == 'BUILD_DATE') {
		 if(ownerForm.form.findField(fieldName).disabled){
			 ownerForm.form.findField(fieldName).setDisabled(false);
			 ownerForm.form.findField(fieldName).removeClass('x-readOnly');
			 ownerForm.form.findField(fieldName).isRead = false;  
		 }
	 }else{
		 if(ownerForm.form.findField(fieldName).readOnly){
			 ownerForm.form.findField(fieldName).setReadOnly(false);
			 ownerForm.form.findField(fieldName).removeClass('x-readOnly');
			 ownerForm.form.findField(fieldName).isRead = false; 
		 }
	}
}

//既有客户加载页面时校验统一社会信用代码与税务登记证编号是否满足校验
//add by liuming 201708245
var validateBusiLicNo = function() {
	if(isGy()){
		var busi_lic_no = qzCombaseInfo.getForm().findField('BUSI_LIC_NO').getValue();//统一社会信用代码
	    var sw_regis_code = qzCombaseInfo.getForm().findField('SW_REGIS_CODE').getValue();//税务登记证
	    if((busi_lic_no.trim() != '') && (sw_regis_code.trim() != '') && (busi_lic_no != sw_regis_code)) {
			Ext.MessageBox.alert('提示','第一屏校验失败，统一社会信用代码与税务登记证编号不一致,请先联系开户同事修改!');
	    }
	}
}

//注册登记号类型为统一社会信用代码时，统一社会信用代码和税务登记证编号不能为空
//add by liuming 201708245
var validateBusiLicNoIsBlank = function() {
	if(isGy()){
		var busi_lic_no = qzCombaseInfo.getForm().findField('BUSI_LIC_NO').getValue();//统一社会信用代码
	    var sw_regis_code = qzCombaseInfo.getForm().findField('SW_REGIS_CODE').getValue();//税务登记证
	    if(busi_lic_no.trim() == '' && sw_regis_code.trim() == '') {
			Ext.MessageBox.alert('提示','第一屏校验失败，当登记注册号类型选“统一社会信用代码”时,统一社会信用代码与税务登记证编号不能为空,请先联系开户同事修改!');
			return;
	    }
	    if(busi_lic_no.trim() == '') {
			Ext.MessageBox.alert('提示','第一屏校验失败，当登记注册号类型选“统一社会信用代码”时,统一社会信用代码不能为空并且与税务登记证编号不一致,请先联系开户同事修改!');
			return;
	    }
	    if(sw_regis_code.trim() == '') {
			Ext.MessageBox.alert('提示','第一屏校验失败，当登记注册号类型选“统一社会信用代码”时,税务登记证编号不能为空并且与统一社会信用代码不一致,请先联系开户同事修改!');
			return;
	    }
	    validateBusiLicNo();
	}
}

//判断输入框中输入的日期格式为yyyy-mm-dd和正确的日期 
var IsDate = function (sm,mystring) { 
	var reg = /^(\d{4})-(\d{2})-(\d{2})$/; 
	var str = mystring; 
	var arr = reg.exec(str); 
	if (str=="") 
		return true; 
	if (!(reg.test(str) && parseInt(RegExp.$2)<=12 && parseInt(RegExp.$3)<=31)){ 
//		Ext.Msg.alert('提示信息', "请保证【"+sm+"】中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return false; 
	} 
	return true; 
} 
/**
 * @deprecated 该函数写法存在问题，废弃
 * @param {} sm
 * @param {} mystring
 * @return {Boolean}
 */
var CheckDate = function (sm,mystring) { 
    var date = mystring;
    var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (result == null){
//		Ext.Msg.alert('提示信息', "请保证【"+sm+"】中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return true; 
    }else{
    	return (new Date(date).getDate()==date.substring(date.length-2));
    }
}
CheckDate = IsDate;