/**
 * @description 中小企客户营销流程 - Pipeline营销概览
 * @author denghj
 * @since 2015-09-07
 */
imports([ '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',// 导出
          '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		  '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',// 机构放大镜
		  '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js', // 用户放大镜
		  '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldYY.js', // 客户放大镜（企商金营销用）
		  '/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js',
		  '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		  '/contents/pages/common/Com.yucheng.bcrm.common.CallReportQueryFieldSME.js'// CallReport放大镜SME
]);
// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};

// 加载机构树
var treeLoaders = [ {
	key : 'ORGTREELOADER',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
} ];

// 树配置
var treeCfgs = [ {
	key : 'ORGTREE',
	loaderKey : 'ORGTREELOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		id : JsContext._orgId,
		text : JsContext._unitname,
		autoScroll : true,
		children : [],
		UNITID : JsContext._orgId,
		UNITNAME : JsContext._unitname
	}
} ];

var url = basepath + '/pipelineCo.json';

var lookupTypes = [ 'PIPELINE_STEP','IF_FLAG', 'XD000084', 'CP_CURRENCY', 'PRODUCT_SUBJECT',
		'PRODUCT_FORM', 'IF_NEXT', 'CUST_TYPE', 'CUST_PROPERTIES', 'PIPELINE_CURRENCY',
		'PIPELINE_CA_STATE','NEW_HAS_CUST', {
			TYPE : 'AREA',// 区域中心数据字典
			url : '/smeProspectE!searchArea.json',
			key : 'KEY',
			value : 'VALUE',
			root : 'data'
		}, {
			TYPE : 'ADD_STEP_NAME',// 新增案件时阶段字典
			url : '/pipelineCo!addStepName.json',
			key : 'KEY',
			value : 'VALUE',
			root : 'data'
		}, {
			TYPE : 'STEP_NAME',
			url : '/pipelineCo!searchStepName.json',// 查询区域的阶段字典，过滤掉'prospect信息'
			key : 'KEY',
			value : 'VALUE',
			root : 'data'
		} ];

var CUST_ID = '';// 定义全局变量，为CallReport放大镜 传递参数
var ifPipeline = 0;// 此标志用于判断是否需要把信息录入pipeline;值为1则需要
var cust_name;//定义全局客户名称
var if_reload = false;//是否重新加载，当同步成功后，隐藏修改面板时触发重新加载页面数据，如果同步不成功或不做同步操作，则不重新加载
// 页面表格加载字段
var fields = [ 
               {
	name : 'NOW_PROGRESS',
	hidden : true
},
{
	name : 'NOW_PROGRESS_NAME',
	text : '所属阶段',
	hiddenName : 'NOW_PROGRESS',
	translateType : 'STEP_NAME',
	searchField : true,
	resutlWidth : 80
}, {
	name : 'CUST_TYPE',
	hidden : true
}, {
//	name : 'CUST_TYPE_NAME',
	name : 'CUST_TYPE',
	text : '客户细分',
	hiddenName : 'CUST_TYPE',
	translateType : 'CUST_TYPE',
	searchField : true,
	resutlWidth : 50
}, {
	name : 'NEW_HAS_CUST',
	text : '新户/既有增贷',
	translateType : 'NEW_HAS_CUST',
	searchField : true,
	resutlWidth : 80
}, {
	name : 'CUST_NAME',
	text : '客户名称',
	hiddenName : 'CUST_ID',
	searchField : true,
	resutlWidth : 170
}, {
	name : 'IF_POTENTIAL',
	text : '是否潜在客户',
	translateType : 'XD000084',
	resutlWidth : 80
}, {
	name : 'AREA_NAME',
	text : '区域中心',
	translateType : 'AREA',
	resutlWidth : 80,
	searchField : true,
	hiddenName : 'AREA_ID',
	showField : 'text'
}, {
	name : 'DEPT_NAME',
	text : '营业部门',
	hiddenName : 'DEPT_ID'
}, {
	name : 'RM',
	text : 'RM',
	dataType : 'string',
	gridField : true,
	resutlWidth : 50
}, {
	name : 'APPLY_AMT_TORMB',//申请额度(折人民币/元)
	text : '申请额度(折人民币/元)',
	dataType : 'money',
	maxLength : 20,
	resutlWidth : 140
}, {
	name : 'INSURE_AMT_TORMB',//核批额度(折人民币/元)
	text : '核批额度(折人民币/元)',
	dataType : 'money',
	maxLength : 20,
	resutlWidth : 140
}, {
	name : 'FIRST_M_AMT_TORMB',//首次动拨金额(折人民币/元)
	text : '首次动拨金额(折人民币/元)',
	dataType : 'money',
	maxLength : 20,
	resutlWidth : 150
}, {
	name : 'SURPLUS_M_AMT_TORMB',//剩余动拨余额(折人民币/元)
	text : '剩余动拨余额(折人民币/元)',
	dataType : 'money',
	maxLength : 20,
	resutlWidth : 150
}, {
	name : 'APPLY_AMT'
//	,
//	text : '申请额度(元)',
//	dataType : 'money',
//	maxLength : 20,
//	resutlWidth : 110
}, {
	name : 'INSURE_AMT'
//	,
//	text : '核批额度(元)',
//	dataType : 'money',
//	maxLength : 20,
//	resutlWidth : 110
}, {
	name : 'FIRST_M_AMT'
//	,
//	text : '首次动拨金额(元)',
//	dataType : 'money',
//	maxLength : 20,
//	resutlWidth : 110
}, {
	name : 'SURPLUS_M_AMT'
//	,
//	text : '剩余动拨金额(元)',
//	dataType : 'money',
//	maxLength : 20,
//	resutlWidth : 110
}, {
	name : 'FIRST_IN_DATE',
	text : '首次转入本阶段日期',
	resutlWidth : 110
}, {
	name : 'STEP_DAYS',
	text : '本阶段案件处理天数',
	resutlWidth : 100
}, {
	name : 'CASE_DAYS',
	text : '案件总计天数',
	resutlWidth : 100
}, {//add by liuming 20170612
	name : 'BACK_NUMBER',
	text : '退回修改次数',
	resutlWidth : 100
}, {
	name : 'CA_NUMBER'
}, {
	name : 'CA_SP_STATE'
}, {
	name : 'IF_TAIWAN'
}, {
	name : 'CUST_PROPERTIES'
}, {
	name : 'IF_SAME'
}, {
	name : 'IF_MUNI'
}, {
	name : 'PRODUCT_SUBJECT'
}, {
	name : 'PRODUCT_FORM'
}, {
	name : 'BUY_NAME'
}, {
	name : 'APPLY_DATE'
}, {
	name : 'APPLY_CURRENCY'
}, {
	name : 'INSURE_CURRENCY'
}, {
	name : 'INSURE_DATE'
}, {
	name : 'EXPECT_M_AMT'
}, {
	name : 'EXPECT_M_DATE'
}, {
	name : 'FIRST_M_DATE'
}, {
	name : 'FIRST_M_CURRENCY'
}, {
	name : 'INSURE_MONEY'
}, {
	name : 'VISIT_DATE'
}, {
	name : 'CA_VALIDITY'
}, {
	name : 'CA_DUE'
}, {
	name : 'IF_NEXT'
}, {
	name : 'MEMO'
}, {
	name : 'AREA_ID'
}, {
	name : 'DEPT_ID'
}, {
	name : 'RM_ID'
}, {
	name : 'CUST_ID'
}, {
	name : 'PIPELINE_ID'
}, {
	name : 'ID'
}, {
	name : 'CREATER'
}, {
	name : 'CREATE_DATE'
}, {
	name : 'UPDATER'
}, {
	name : 'UPDATE_DATE'
}, {//本次增贷额度
	name : 'CREDIT_GROWTH'
//	,text : '本次增贷额度(元)',
//	resutlWidth : 100
}, {//本次增贷额度(折人民币)
	name : 'CREDIT_GROWTH_TORMB'
//	,text : '本次增贷额度(折人民币/元)',
//	dataType : 'money',
//	resutlWidth : 150
}];

var tbar = [ {
	text : '删除',
	hidden:JsContext.checkGrant('PipelineDelete'),
	handler : function() {
		var records = getAllSelects();
		if (records == null || records.length < 1) {
			Ext.Msg.alert('提示', '请选择一条数据');
			return false;
		}
		// 取得当前操作人id
		var user_id = JsContext._userId;
		for ( var i = 0; i < records.length; i++) {
			// 取得选中数据的RM信息
			if(records[i].data.RM_ID != user_id){
				Ext.Msg.alert('提示', '无权限删除其他RM的案件！');
				return false;
			}
		}
		var ids = "";
		for ( var i = 0; i < records.length; i++) {
			if (records[i].data.NOW_PROGRESS != '2') {
				Ext.Msg.alert('提示', '只能删除合作意向阶段数据，请检查后再操作！');
				return false;
			} else {
				ids += records[i].data.ID + ",";
			}
		}
		Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {
				return false;
			}
			Ext.Ajax.request({
				url : basepath + '/pipelineCo!batchDel.json',
				method : 'POST',
				params : {
					idStr : ids
				},
				success : function() {
					Ext.Msg.alert('提示', '删除成功');
					reloadCurrentData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '删除失败');
					reloadCurrentData();
				}
			});
		});
	}
}, new Com.yucheng.crm.common.NewExpButton({// 导出按钮
	hidden : JsContext.checkGrant('pipelineAllExport'),
	formPanel : 'searchCondition',
	url : basepath + '/pipelineCo.json'
}) ];

/**
 * 创建侧滑界面，添加流程按钮
 */
var edgeVies = {
	left : {
		width : 150,
		layout : 'fit',
		title : 'Pipeline流程',
		collapsed : true,
		// hidden : true,
		items : [ {
			width : '100%',
			height : '100%',
			layout : {
				type : 'vbox',
				padding : '8',
				align : 'stretch'
			},
			items : [
					{
						xtype : 'button',
						text : '合作意向',
						hidden : JsContext.checkGrant('pipelineButton1'),
						flex : 1,
						handler : function() {
							window._app.searchDomain.searchPanel.getForm()
									.findField("NOW_PROGRESS_NAME").setValue(
											'2');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '文件收集及CA准备',
						hidden : JsContext.checkGrant('pipelineButton2'),
						flex : 1,
						handler : function() {
							window._app.searchDomain.searchPanel.getForm()
									.findField("NOW_PROGRESS_NAME").setValue(
											'3');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '信用审查',
						hidden : JsContext.checkGrant('pipelineButton3'),
						flex : 1,
						handler : function() {
							window._app.searchDomain.searchPanel.getForm()
									.findField("NOW_PROGRESS_NAME").setValue(
											'4');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '核批阶段',
						hidden : JsContext.checkGrant('pipelineButton4'),
						flex : 1,
						handler : function() {
							window._app.searchDomain.searchPanel.getForm()
									.findField("NOW_PROGRESS_NAME").setValue(
											'5');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '已核批动拨',
						hidden : JsContext.checkGrant('pipelineButton5'),
						flex : 1,
						handler : function() {
							window._app.searchDomain.searchPanel.getForm()
									.findField("NOW_PROGRESS_NAME").setValue(
											'6');
							window._app.searchDomain.searchButton.handler();
						}
					} /*
						 * , { xtype : 'button', text : '否决及退案汇总', hidden :
						 * JsContext.checkGrant('pipelineButton6'), flex : 1,
						 * handler : function() {
						 * showCustomerViewByTitle("否决及退案汇总"); } },{ xtype :
						 * 'button', text : '查看历史移转', hidden :
						 * JsContext.checkGrant('pipelineButton7'), flex : 1,
						 * handler : function() {
						 * showCustomerViewByTitle("查看历史移转"); } }
						 */]
		} ]
	}
};
/**
 * 创建store,用于存储查询所得拒绝及退案的信息
 */
var store = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/smePipelineAll!refusedCollect.json'
	}),
	reader : new Ext.data.JsonReader({
		root : 'json.data'
	}, [ {
		name : 'STEP_NAME'
	}, {
		name : 'HIDE_STEP'
	}, {
		name : 'CUST_NAME'
	}, {
		name : 'DEPT_NAME'
	}, {
		name : 'AREA_NAME'
	}, {
		name : 'RM'
	}, {
		name : 'CASE_TYPE'
	}, {
		name : 'CASE_TYPE_NAME'
	}, {
		name : 'STATE'
	}, {
		name : 'UPDATE_DATE'
	}, {
		name : 'APPLY_AMT'
	}, {
		name : 'INSURE_AMT'
	}, {
		name : 'DB_AMTS'
	}, {
		name : 'VISIT_COUNT'
	}, {
		name : 'TREAMENT_DAYS'
	}, {
		name : 'TREAMENT_ALLDAYS'
	}, {
		name : 'CUST_ID'
	}, {
		name : 'AREA_ID'
	}, {
		name : 'DEPT_ID'
	}, {
		name : 'RM_ID'
	}, {
		name : 'PIPELINE_ID'
	} ])
});
store.load();
/**
 * 创建grid,用于将store信息显示在customerView
 */
var grid = new Ext.grid.GridPanel({
	store : store,
	stripeRows : true,
	stateful : true,
	stateId : 'grid',
	columns : [ {
		name : 'STEP_NAME',
		header : '所属阶段'
	}, {
		name : 'HIDE_STEP',
		hidden : true,
		comFn : function(data) {
			return "HIDE_STEP";
		}
	}, {
		name : 'CUST_NAME',
		header : '客户名称'
	}, {
		name : 'DEPT_NAME',
		header : '营业部门'
	}, {
		name : 'AREA_NAME',
		header : '区域中心'
	}, {
		name : 'RM',
		header : 'RM'
	}, {
		name : 'CASE_TYPE',
		hidden : true
	}, {
		name : 'CASE_TYPE_NAME',
		header : '案件类型'
	}, {
		name : 'STATE',
		header : '案件状态'
	}, {
		name : 'UPDATE_DATE',
		header : '否决或退案时间'
	}, {
		name : 'APPLY_AMT',
		header : '申请额度(折人民币/元)'
	}, {
		name : 'INSURE_AMT',
		header : '核准金额(折人民币/元)'
	}, {
		name : 'DB_AMTS',
		header : '已动拨金额(折人民币/元)'
	}, {
		name : 'TREAMENT_DAYS',
		header : '本阶段案件处理天数'
	}, {
		name : 'TREAMENT_ALLDAYS',
		header : '案件总计天数'
	}, {
		name : 'CUST_ID',
		hidden : true
	}, {
		name : 'AREA_ID',
		hidden : true
	}, {
		name : 'DEPT_ID',
		hidden : true
	}, {
		name : 'RM_ID',
		hidden : true
	}, {
		name : 'ID',
		hidden : true
	}, {
		name : 'PIPELINE_ID',
		hidden : true
	} ]
});
/**
 * 创建store,用于存储查询所得历史转移的信息
 */
var storeHis = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFCiTransBusiness!queryHistory.json'
	}),
	reader : new Ext.data.JsonReader({
		root : 'json.data'
	}, [ {
		name : 'ID'
	}, {
		name : 'CUST_ID'
	}, {
		name : 'CUST_NAME'
	}, {
		name : 'PIPELINE_ID'
	}, {
		name : 'BEFORE_MGR_ID'
	}, {
		name : 'BEFORE_MGR_NAME'
	}, {
		name : 'AFTER_MGR_ID'
	}, {
		name : 'AFTER_MGR_NAME'
	}, {
		name : 'BEFORE_STEP'
	}, {
		name : 'BEFORE_STATE'
	}, {
		name : 'AFTER_STEP'
	}, {
		name : 'AFTER_STATE'
	}, {
		name : 'EFFECT_DATE'
	}, {
		name : 'CREATE_DATE'
	} ])
});
storeHis.load();
/**
 * 创建gridHis,用于将store信息显示在customerView
 */
var gridHis = new Ext.grid.GridPanel({
	store : storeHis,
	stripeRows : true,
	stateful : true,
	stateId : 'gridHis',
	columns : [ {
		name : 'ID',
		hidden : true
	}, {
		name : 'CUST_ID',
		hidden : true
	}, {
		name : 'CUST_NAME',
		width : 160,
		header : '客户名称'
	}, {
		name : 'PIPELINE_ID',
		hidden : true
	}, {
		name : 'BEFORE_MGR_ID',
		hidden : true
	}, {
		name : 'BEFORE_MGR_NAME',
		header : '移转前客户经理'
	}, {
		name : 'AFTER_MGR_ID',
		hidden : true
	}, {
		name : 'AFTER_MGR_NAME',
		header : '移转后客户经理'
	}, {
		name : 'BEFORE_STEP',
		header : '移转前所属阶段',
		renderer : function(value) {
			var val = translateLookupByKey("PIPELINE_STEP", value);
			return val ? val : "";
		}
	}, {
		name : 'BEFORE_STATE',
		header : '移转前状态'
	}, {
		name : 'AFTER_STEP',
		header : '移转后所属阶段',
		renderer : function(value) {
			var val = translateLookupByKey("PIPELINE_STEP", value);
			return val ? val : "";
		}
	}, {
		name : 'AFTER_STATE',
		header : '移转后状态'
	}, {
		name : 'EFFECT_DATE',
		header : '移转生效时间'
	}, {
		name : 'CREATE_DATE',
		header : '移转时间'
	} ]
});
// 创建新增客户功能
var customerView = [
		{
			title : '新增合作意向',
			hideTitle : JsContext.checkGrant('pipelineAllAdd'),
			type : 'form',
			groups : [
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [
								{
									name : 'CUST_TYPE',
									text : '客户细分',
									translateType : 'CUST_TYPE',
									allowBlank : false,
									listeners : {
										select : function() {
											var CUST_TYPE = getCurrentView().contentPanel.form.findField("CUST_TYPE").getValue();
											if (CUST_TYPE == '1') { // TT
												getCurrentView().contentPanel.form.findField("IF_SAME").show();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
												getCurrentView().contentPanel.form.findField("IF_MUNI").show();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").hide();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").setValue('');
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
												getCurrentView().contentPanel.form.findField("BUY_NAME").hide();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setValue('');
												getCurrentView().contentPanel.form.findField("BUY_NAME").allowBlank = true;
											} else { // CB
												getCurrentView().contentPanel.form.findField("IF_SAME").hide();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_SAME").setValue('');
												getCurrentView().contentPanel.form.findField("IF_MUNI").hide();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_MUNI").setValue('');
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").show();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").allowBlank = false;
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").label.dom.innerHTML = '<font color=red>*</font>客户属性:';
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = false;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").label.dom.innerHTML = '<font color=red>*</font>产品科目:';
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").label.dom.innerHTML = '<font color=red></font>产品形态:';
												getCurrentView().contentPanel.form.findField("BUY_NAME").show();
											}
										},
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'NOW_PROGRESS',
									text : '所属阶段',
									translateType : 'ADD_STEP_NAME',
									searchField : true,
									allowBlank : false,// 是否允许为空
									listeners : {
										select : function() {
											var STEP_NAME = getCurrentView().contentPanel.form.findField("NOW_PROGRESS").getValue();
											if (STEP_NAME == '' || STEP_NAME == '2') { // 合作意向
												getCurrentView().contentPanel.form.findField("CA_NUMBER").hide();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue('');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").hide();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue('');
											} else if (STEP_NAME == '3') { // CA准备
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
											}
										},
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CUST_NAME',
									text : '客户名称',
									xtype : 'customerqueryyy',
									hiddenName : 'CUST_ID',
									allowBlank : false,
									searchField : true,
									singleSelected : true,
									newCust : true,
									callback : function(a, b) {
										//CUST_ID = a.customerId;
										//获得客户名称的值
										var new_cust = getCurrentView().contentPanel.form.findField("CUST_NAME").getValue();
										if(new_cust == null || new_cust == ''){
											CUST_ID = '';
										}else{
											CUST_ID = a.customerId;
										}
										//如果客户名称和全局不等，则清空拜访记录
										if(new_cust != cust_name){
											getCurrentView().contentPanel.form.findField("VISIT_DATE").setValue('');
											cust_name = new_cust;
										}
										getCurrentView().contentPanel.form.findField("AREA_ID").setValue(a.region);// 区域ID
										var val = translateLookupByKey("AREA",a.region);
										getCurrentView().contentPanel.form.findField("AREA_NAME").setValue(val ? val : a.region);// 区域
										getCurrentView().contentPanel.form.findField("DEPT_ID").setValue(a.orgId);// 分支行ID
										getCurrentView().contentPanel.form.findField("DEPT_NAME").setValue(a.orgName);// 分支行
										getCurrentView().contentPanel.form.findField("RM_ID").setValue(a.mgrId);// 客户经理ID
										getCurrentView().contentPanel.form.findField("RM").setValue(a.mgrName);// 客户经理
										if ('Y' == a.isTaiwanCorp) {
											getCurrentView().contentPanel.form.findField("IF_TAIWAN").setValue('1');// 是否台商
										} else {
											getCurrentView().contentPanel.form.findField("IF_TAIWAN").setValue('0');// 是否台商
										}

									}
								},
								{
									name : 'AREA_NAME',
									text : '区域中心',
									resutlAREAWidth : 80,
									showField : 'text',
									hiddenName : 'AREA_ID',
									searchField : true,
									translateType : 'AREA',
									listeners : {
										select : function(a, b) {
											a.setValue(b.data.value);
										}
									}
								},
								{
									name : 'DEPT_NAME',
									text : '营业部门',
									hiddenName : 'DEPT_ID'
								},
								{
									name : 'RM',
									text : 'RM',
									resutlWidth : 150
								},
								{
									name : 'IF_TAIWAN',
									text : '是否台商',
									translateType : 'IF_FLAG'
								},
								{
									name : 'NEW_HAS_CUST',
									text : '新户/既有增贷',
									translateType : 'NEW_HAS_CUST',
									allowBlank : false,
									listeners : {
										select : function() {
											var NEW_HAS_CUST = getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").getValue();
											if (NEW_HAS_CUST == '2') {
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(false);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").removeClass('x-readOnly');
											} else {
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
											}
										},
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CUST_PROPERTIES',
									text : '客户属性',
									translateType : 'CUST_PROPERTIES',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								}, {
									name : 'BUY_NAME',
									text : '买方名称',
									allowBlank : true,
									maxLength : 30
								} ],
						fn : function(CUST_TYPE, NOW_PROGRESS, CUST_NAME,
								AREA_NAME, DEPT_NAME, RM, IF_TAIWAN,
								NEW_HAS_CUST, CUST_PROPERTIES, BUY_NAME) {
							AREA_NAME.readOnly = true;
							AREA_NAME.cls = 'x-readOnly';
							DEPT_NAME.readOnly = true;
							DEPT_NAME.cls = 'x-readOnly';
							RM.readOnly = true;
							RM.cls = 'x-readOnly';
							IF_TAIWAN.readOnly = true;// 设为只读
							IF_TAIWAN.cls = 'x-readOnly';// 设为灰色

							return [ CUST_TYPE, NOW_PROGRESS, CUST_NAME,
									AREA_NAME, DEPT_NAME, RM, IF_TAIWAN,
									NEW_HAS_CUST, CUST_PROPERTIES, BUY_NAME ];
						}
					},
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [ {
							name : 'CA_NUMBER',
							text : 'CA业务流水号',
							maxLength : 20
						} ],
						fn : function(CA_NUMBER) {
							CA_NUMBER.hidden = true;

							return [ CA_NUMBER ];
						}
					},
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [ {
							name : 'IF_SAME',
							text : '是否同行',
							translateType : 'IF_FLAG',
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'IF_MUNI',
							text : '是否市政',
							translateType : 'IF_FLAG',
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'PRODUCT_SUBJECT',
							text : '产品科目',
							translateType : 'PRODUCT_SUBJECT',
							allowBlank : false,
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'PRODUCT_FORM',
							text : '产品形态',
							translateType : 'PRODUCT_FORM',
							allowBlank : true,
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'CA_SP_STATE',
							text : 'CA流程状态',
							translateType : 'PIPELINE_CA_STATE'
						}, {
							name : 'APPLY_AMT',
							text : '申请金额(元)',
							regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
							regexText : '格式有误,例：999999.9，-9999999.9',
							maxLength : 20,
							allowBlank : false
						}, {
							name : 'APPLY_DATE',
							text : '申请日期',
							dataType : 'date',
							allowBlank : false,
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'VISIT_DATE',
							text : '首次拜访日期',
							xtype : 'callreportquerysme',
							hiddenName : 'CUST_ID',
							allowBlank : true,
							singleSelected : true,
							listeners : {
								focus : function() {
									if (CUST_ID == '') {
										Ext.Msg.alert("提示", "请先选择客户！");
										return false;
									}
								}
							}
						}, {
							name : 'APPLY_CURRENCY',
							text : '申请币别',
							translateType : 'PIPELINE_CURRENCY',
							allowBlank : false,
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'INSURE_MONEY',
							text : '原核批额度（元）',
							regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
							regexText : '格式有误,例：999999.9，-9999999.9',
							maxLength : 20
						}, {
							name : 'IF_NEXT',
							text : '是否进入下一阶段',
							translateType : 'IF_NEXT',
							allowBlank : true,
							listeners : {
								'focus' : {
									fn : function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer : 200
								}
							}
						}, {
							name : 'AREA_ID'
						}, {
							name : 'RM_ID'
						}, {
							name : 'CUST_ID'
						}, {
							name : 'PIPELINE_ID'
						}, {
							name : 'DEPT_ID'
						} ],
						fn : function(IF_SAME, IF_MUNI, PRODUCT_SUBJECT,
								PRODUCT_FORM, CA_SP_STATE, APPLY_AMT,
								APPLY_DATE, VISIT_DATE, APPLY_CURRENCY,
								INSURE_MONEY, IF_NEXT, AREA_ID, RM_ID, CUST_ID,
								PIPELINE_ID, DEPT_ID) {

							CA_SP_STATE.hidden = true;
							IF_SAME.hidden = true;
							IF_MUNI.hidden = true;
							CUST_ID.hidden = true;
							PIPELINE_ID.hidden = true;
							AREA_ID.hidden = true;
							DEPT_ID.hidden = true;
							RM_ID.hidden = true;
							INSURE_MONEY.hidden = true;
							IF_NEXT.hidden = true;

							return [ IF_SAME, IF_MUNI, PRODUCT_SUBJECT,
									PRODUCT_FORM, CA_SP_STATE, APPLY_AMT,
									APPLY_DATE, VISIT_DATE, APPLY_CURRENCY,
									INSURE_MONEY, IF_NEXT, AREA_ID, RM_ID,
									CUST_ID, PIPELINE_ID, DEPT_ID ];
						}
					}, {
						columnCount : 1,
						fields : [ {
							name : 'MEMO',
							text : '备注',
							xtype : 'textarea',
							maxLength : 200
						} ],
						fn : function(MEMO) {

							return [ MEMO ];
						}
					} ],
			formButtons : [ {
				text : '提交',
				fn : function(formPanel, basicForm) {
					if (!formPanel.getForm().isValid()) {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
					if(getCurrentView().contentPanel.form.findField('VISIT_DATE').getValue() == null 
							|| getCurrentView().contentPanel.form.findField('VISIT_DATE').getValue() == ''){
						Ext.MessageBox.alert('提示', '首次拜访日期不可为空！');
						return false;
					}
					var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data, 1);
					ifPipeline = commintData.ifPipeline;
					Ext.Msg.wait('正在处理，请稍后......', '系统提示');
					Ext.Ajax.request({
						url : basepath + '/pipelineCo!saveOrUpdate.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							Ext.MessageBox.alert('提示', '保存数据成功!');
							hideCurrentView();
							reloadCurrentData();
						}
					});
				}
			} ]
		},
		{
			title : '修改',
			hideTitle : JsContext.checkGrant('pipelineAllEdit'),
			type : 'form',
			autoLoadSeleted : true,// 自动加载
			groups : [
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [
								{
									name : 'CUST_TYPE',
									text : '客户细分',
									translateType : 'CUST_TYPE',
									allowBlank : false,
									anchor : '90%'
								},
								{
									name : 'NOW_PROGRESS',
									text : '所属阶段',
									translateType : 'STEP_NAME',
									allowBlank : false,// 是否允许为空
									searchField : true,
									anchor : '90%'
								},
								{
									name : 'CUST_NAME',
									text : '客户名称',
									hiddenName : 'CUST_ID',
									searchField : true,
									anchor : '90%'
								},
								{
									name : 'AREA_NAME',
									text : '区域中心',
									resutlWidth : 80,
									showField : 'text',
									translateType : 'AREA',
									valueField : 'value',
									anchor : '90%',
									listeners : {
										select : function(a, b) {
											a.setValue(b.data.value);
										}
									}
								},
								{
									name : 'DEPT_NAME',
									text : '营业部门',
									hiddenName : 'DEPT_ID',
									anchor : '90%'
								},
								{
									name : 'RM',
									text : 'RM',
									hiddenName : 'RM_ID',
									anchor : '90%'
								},
								{
									name : 'IF_TAIWAN',
									text : '是否台商',
									translateType : 'IF_FLAG',
									anchor : '90%'
								},
								{
									name : 'NEW_HAS_CUST',
									text : '新户/既有增贷',
									translateType : 'NEW_HAS_CUST',
									anchor : '90%',
									allowBlank : false,
									listeners : {
										select : function() {
											var NEW_HAS_CUST = getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").getValue();
											if (NEW_HAS_CUST == '2') {
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(false);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
											} else {
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
											}
										},
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CUST_PROPERTIES',
									text : '客户属性',
									translateType : 'CUST_PROPERTIES',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CA_NUMBER',
									text : 'CA业务流水号',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'IF_SAME',
									text : '是否同行',
									translateType : 'IF_FLAG',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'IF_MUNI',
									text : '是否市政',
									translateType : 'IF_FLAG',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'PRODUCT_SUBJECT',
									text : '产品科目',
									translateType : 'PRODUCT_SUBJECT',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'PRODUCT_FORM',
									text : '产品形态',
									translateType : 'PRODUCT_FORM',
									anchor : '90%',
									allowBlank : true,
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CA_SP_STATE',
									text : 'CA流程状态',
									translateType : 'PIPELINE_CA_STATE',
									anchor : '90%'
								},

								{
									name : 'BUY_NAME',
									text : '买方名称',
									maxLength : 30,
									allowBlank : true,
									anchor : '90%'
								},
								{
									name : 'APPLY_DATE',
									text : '申请日期',
									dataType : 'date',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'APPLY_AMT',
									text : '申请金额(元)',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'APPLY_AMT_TORMB',
									text : '申请金额(折人民币/元)',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'APPLY_CURRENCY',
									text : '申请币别',
									translateType : 'PIPELINE_CURRENCY',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'INSURE_AMT',
									text : '核批金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'INSURE_AMT_TORMB',
									text : '核批金额(折人民币/元)',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'INSURE_CURRENCY',
									text : '核批币别',
									translateType : 'PIPELINE_CURRENCY',
									anchor : '90%'
								},
								{
									name : 'INSURE_DATE',
									text : '核批日期',
									dataType : 'date',
									// width : 160,
									anchor : '90%'
								},
								{
									name : 'EXPECT_M_AMT',
									text : '预计动拨金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'EXPECT_M_DATE',
									text : '预计动拨日期',
									dataType : 'date',
									anchor : '90%'
								},
								{
									name : 'FIRST_M_AMT',
									text : '首次动拨金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'FIRST_M_AMT_TORMB',
									text : '首次动拨金额(折人民币/元)',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'FIRST_M_DATE',
									text : '首次动拨日期',
									dataType : 'date',
									// width : 160,
									anchor : '90%'
								},
								{
									name : 'FIRST_M_CURRENCY',
									text : '首次动拨币别',
									translateType : 'PIPELINE_CURRENCY',
									anchor : '90%'
								},
								{
									name : 'SURPLUS_M_AMT',
									text : '剩余动拨金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'SURPLUS_M_AMT_TORMB',
									text : '剩余动拨金额(折人民币/元)',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'INSURE_MONEY',
									text : '原核批额度（元）',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},

								{
									name : 'STEP_DAYS',
									text : '本阶段案件处理天数',
									allowBlank : true,
									anchor : '90%'
								},
								{
									name : 'VISIT_DATE',
									text : '首次拜访日期',
									allowBlank : false,
									anchor : '90%'
								},
								{
									name : 'IF_NEXT',
									text : '是否进入下一阶段',
									translateType : 'IF_NEXT',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery, true);
											},
											buffer : 200
										}
									}
								}, {
									name : 'CASE_DAYS',
									text : '案件总计天数',
									anchor : '90%'
								}, {
									name : 'AREA_ID'
								}, {
									name : 'RM_ID'
								}, {
									name : 'CUST_ID'
								}, {
									name : 'PIPELINE_ID'
								}, {
									name : 'DEPT_ID'
								}, {
									name : 'ID'
								}, {
									name : 'CREATER'
								}, {
									name : 'CREATE_DATE'
								}, {
									name : 'UPDATER'
								}, {
									name : 'UPDATE_DATE'
								}, {
									name : 'FIRST_IN_DATE'
								}, {
									name : 'CA_VALIDITY'
								}, {
									name : 'CA_DUE'
								} ],
						fn : function(CUST_TYPE, NOW_PROGRESS, CUST_NAME,
								AREA_NAME, DEPT_NAME, RM, IF_TAIWAN,
								NEW_HAS_CUST, CUST_PROPERTIES, CA_NUMBER,
								IF_SAME, IF_MUNI, PRODUCT_SUBJECT,
								PRODUCT_FORM, CA_SP_STATE, BUY_NAME,
								APPLY_DATE, APPLY_AMT, APPLY_AMT_TORMB, APPLY_CURRENCY,
								INSURE_AMT, INSURE_AMT_TORMB, INSURE_CURRENCY, INSURE_DATE,
								EXPECT_M_AMT, EXPECT_M_DATE, FIRST_M_AMT, FIRST_M_AMT_TORMB,
								FIRST_M_DATE, FIRST_M_CURRENCY, SURPLUS_M_AMT, SURPLUS_M_AMT_TORMB,
								INSURE_MONEY, STEP_DAYS, VISIT_DATE, IF_NEXT,
								CASE_DAYS, AREA_ID, RM_ID, CUST_ID,
								PIPELINE_ID, DEPT_ID, ID, CREATER, CREATE_DATE,
								UPDATER, UPDATE_DATE, FIRST_IN_DATE,
								CA_VALIDITY, CA_DUE) {

							CUST_TYPE.readOnly = true;
							CUST_TYPE.cls = 'x-readOnly';
							NOW_PROGRESS.readOnly = true;
							NOW_PROGRESS.cls = 'x-readOnly';
							APPLY_AMT_TORMB.readOnly = true;
							APPLY_AMT_TORMB.cls = 'x-readOnly';
							CUST_NAME.readOnly = true;
							CUST_NAME.cls = 'x-readOnly';
							AREA_NAME.readOnly = true;
							AREA_NAME.cls = 'x-readOnly';
							DEPT_NAME.readOnly = true;
							DEPT_NAME.cls = 'x-readOnly';
							CA_SP_STATE.readOnly = true;
							CA_SP_STATE.cls = 'x-readOnly';
							RM.readOnly = true;
							RM.cls = 'x-readOnly';
							IF_TAIWAN.readOnly = true;// 设为只读
							IF_TAIWAN.cls = 'x-readOnly';// 设为灰色
							VISIT_DATE.readOnly = true;
							VISIT_DATE.cls = 'x-readOnly';
							INSURE_AMT_TORMB.readOnly = true;
							INSURE_AMT_TORMB.cls = 'x-readOnly';
							FIRST_M_AMT_TORMB.readOnly = true;
							FIRST_M_AMT_TORMB.cls = 'x-readOnly';
							SURPLUS_M_AMT_TORMB.readOnly = true;
							SURPLUS_M_AMT_TORMB.cls = 'x-readOnly';
							CA_NUMBER.hidden = true;
							CA_SP_STATE.hidden = true;
							IF_SAME.hidden = true;
							IF_MUNI.hidden = true;
							CUST_ID.hidden = true;
							AREA_ID.hidden = true;
							DEPT_ID.hidden = true;
							RM_ID.hidden = true;
							INSURE_AMT.hidden = true;
							INSURE_CURRENCY.hidden = true;
							// INSURE_DATE.hidden = true;
							INSURE_DATE.setVisible(false);
							EXPECT_M_AMT.hidden = true;
							EXPECT_M_DATE.hidden = true;
							FIRST_M_AMT.hidden = true;
							FIRST_M_DATE.hidden = true;
							FIRST_M_CURRENCY.hidden = true;
							SURPLUS_M_AMT.hidden = true;
							INSURE_MONEY.hidden = true;
							STEP_DAYS.hidden = true;
							CASE_DAYS.hidden = true;
							IF_NEXT.hidden = true;
							PIPELINE_ID.hidden = true;
							ID.hidden = true;
							CREATER.hidden = true;
							CREATE_DATE.hidden = true;
							UPDATER.hidden = true;
							UPDATE_DATE.hidden = true;
							FIRST_IN_DATE.hidden = true;
							CA_VALIDITY.hidden = true;
							CA_DUE.hidden = true;
							APPLY_AMT_TORMB.hidden = true;
							INSURE_AMT_TORMB.hidden = true;
							FIRST_M_AMT_TORMB.hidden = true;
							SURPLUS_M_AMT_TORMB.hidden = true;

							return [ CUST_TYPE, NOW_PROGRESS, CUST_NAME,
									AREA_NAME, DEPT_NAME, RM, IF_TAIWAN,
									NEW_HAS_CUST, CUST_PROPERTIES, CA_NUMBER,
									IF_SAME, IF_MUNI, PRODUCT_SUBJECT,
									PRODUCT_FORM, CA_SP_STATE, BUY_NAME,
									APPLY_DATE, APPLY_AMT, APPLY_AMT_TORMB, APPLY_CURRENCY,
									INSURE_AMT, INSURE_AMT_TORMB, INSURE_CURRENCY, INSURE_DATE,
									EXPECT_M_AMT, EXPECT_M_DATE, FIRST_M_AMT, FIRST_M_AMT_TORMB,
									FIRST_M_DATE, FIRST_M_CURRENCY,
									SURPLUS_M_AMT, SURPLUS_M_AMT_TORMB, INSURE_MONEY, STEP_DAYS,
									VISIT_DATE, IF_NEXT, CASE_DAYS, AREA_ID,
									RM_ID, CUST_ID, PIPELINE_ID, DEPT_ID, ID,
									CREATER, CREATE_DATE, UPDATER, UPDATE_DATE,
									FIRST_IN_DATE, CA_VALIDITY, CA_DUE ];
						}
					}, {
						columnCount : 1,
						fields : [ {
							name : 'MEMO',
							text : '备注',
							xtype : 'textarea',
							maxLength : 200
						} ],
						fn : function(MEMO) {
							return [ MEMO ];
						}
					} ],
			formButtons : [
					{   id:"bc",
						text : '保存',
						fn : function(contentPanel, baseform) {

							showCustomerViewByTitle('修改');
							Ext.getCmp('bc').setDisabled(true);
							if (!baseform.isValid()) {
								Ext.Msg.alert('提醒', '字段检验失败，请检查输入项');
								return false;
							}
							var commintData = translateDataKey(baseform.getFieldValues(), 1);// _app.VIEWCOMMITTRANS
							Ext.Ajax.request({
								url : basepath + '/pipelineCo!saveOrUpdate.json',
								method : 'POST',
								params : commintData,
								success : function(response) {
									Ext.Msg.alert('提示', '保存成功！');
									Ext.getCmp('bc').setDisabled(false);
									reloadCurrentData();
								},
								failure : function() {
									Ext.Msg.alert('提示', '保存失败！');
									Ext.getCmp('bc').setDisabled(false);
								}
							});
						}
					},
					{
						text : '同步',
						id : 'synchnize',
						hidden : true,
						fn : function(contentPanel, baseform) {
							Ext.getCmp('synchnize').setDisabled(true);
							var id = getAllSelects()[0].data.ID;
							var ca_number = getCurrentView().contentPanel.form.findField("CA_NUMBER").getValue();
							var cust_id = getCurrentView().contentPanel.form.findField("CUST_ID").getValue();
							if (ca_number == '') {
								Ext.Msg.alert('提示', 'CA业务流水号不可为空！');
								Ext.getCmp('synchnize').setDisabled(false);
								return false;
							}
							var commintData = translateDataKey(baseform.getFieldValues(), 1);
							Ext.Ajax.request({
										url : basepath + '/pipelineCo!syncLN2.json',
										method : 'GET',
										params : commintData,
										success : function(response) {
											var res = Ext.decode(response.responseText);
											var errMsg = res.json.data[0].errMsg;
											if (errMsg != null) {
												Ext.Msg.alert("提示",res.json.data[0].errMsg);
												Ext.getCmp('synchnize').setDisabled(false);
												return false;
											}
											var CUST_TYPE = getCurrentView().contentPanel.form.findField("CUST_TYPE").getValue();
											var STEP_NAME = res.json.data[0].stepName;
											var new_has_cust = res.json.data[0].newHasCust;
											if (CUST_TYPE == '1' && STEP_NAME == '3') { // TT+CA准备阶段
												getCurrentView().contentPanel.form.findField("IF_SAME").show();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
												getCurrentView().contentPanel.form.findField("IF_MUNI").show();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(true);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").addClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '申请金额(元)';
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '申请币别';
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").show();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '申请日期';
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").show();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").hide();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").setValue('');
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
												getCurrentView().contentPanel.form.findField("BUY_NAME").hide();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setValue('');
												getCurrentView().contentPanel.form.findField("BUY_NAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_DATE").hide();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("CASE_DAYS").hide();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("CASE_DAYS").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("3");
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue(res.json.data[0].APPLY_AMT_TORMB);
											} else if (CUST_TYPE == '1' && STEP_NAME == '4') { // TT+信用审查阶段
												getCurrentView().contentPanel.form.findField("IF_SAME").show();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
												getCurrentView().contentPanel.form.findField("IF_MUNI").show();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(true);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").addClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").show();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").show();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").hide();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").setValue('');
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
												getCurrentView().contentPanel.form.findField("BUY_NAME").hide();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setValue('');
												getCurrentView().contentPanel.form.findField("BUY_NAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_DATE").hide();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("CASE_DAYS").hide();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("CASE_DAYS").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("4");
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue(res.json.data[0].APPLY_AMT_TORMB);
											} else if (CUST_TYPE == '1' && STEP_NAME == '5') { // TT+核批阶段
												getCurrentView().contentPanel.form.findField("IF_SAME").show();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
												getCurrentView().contentPanel.form.findField("IF_MUNI").show();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(true);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").addClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").show();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").show();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").show();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").show();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = false;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setReadOnly(false);
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").label.dom.innerHTML = '<font color=red>*</font>预计动拨金额:';
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").show();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = false;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setReadOnly(false);
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").label.dom.innerHTML = '<font color=red>*</font>预计动拨日期:';

												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").hide();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").setValue('');
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
												getCurrentView().contentPanel.form.findField("BUY_NAME").hide();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setValue('');
												getCurrentView().contentPanel.form.findField("BUY_NAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("CASE_DAYS").hide();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("CASE_DAYS").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("5");
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(res.json.data[0].insureAmt);
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue(res.json.data[0].insureCurrency);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue(res.json.data[0].insureDate != ''?new Date(res.json.data[0].insureDate) : null);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue(res.json.data[0].APPLY_AMT_TORMB);
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue(res.json.data[0].INSURE_AMT_TORMB);
											} else if (CUST_TYPE == '1' && STEP_NAME == '6') { // TT+已核批动拨
												getCurrentView().contentPanel.form.findField("IF_SAME").show();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_SAME").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
												getCurrentView().contentPanel.form.findField("IF_MUNI").show();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").readOnly = false;
												getCurrentView().contentPanel.form.findField("IF_MUNI").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(false);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").removeClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").show();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").show();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").show();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").show();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CASE_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").hide();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").setValue('');
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").hide();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").setValue('');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
												getCurrentView().contentPanel.form.findField("BUY_NAME").hide();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setValue('');
												getCurrentView().contentPanel.form.findField("BUY_NAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("STEP_DAYS").hide();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("6");
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(res.json.data[0].insureAmt);
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue(res.json.data[0].insureCurrency);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue(res.json.data[0].insureDate != ''?new Date(res.json.data[0].insureDate) : null);
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue(res.json.data[0].firstMAmt);
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue(res.json.data[0].firstMDate != ''?new Date(res.json.data[0].firstMDate) : null);
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue(res.json.data[0].firstMCurrency);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue(res.json.data[0].surplusMAmt);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue(res.json.data[0].APPLY_AMT_TORMB);
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue(res.json.data[0].INSURE_AMT_TORMB);
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue(res.json.data[0].FIRST_M_AMT_TORMB);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue(res.json.data[0].SURPLUS_M_AMT_TORMB);
												
											} else if (CUST_TYPE == '2' && STEP_NAME == '3') { // CB+CA准备阶段
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").show();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(true);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").addClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("BUY_NAME").show();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("BUY_NAME").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("VISIT_DATE").show();
												getCurrentView().contentPanel.form.findField("VISIT_DATE").readOnly = true;
												getCurrentView().contentPanel.form.findField("VISIT_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").show();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '申请日期';
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '申请金额(元)';
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '申请币别';
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").show();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("IF_SAME").hide();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_SAME").setValue('');
												getCurrentView().contentPanel.form.findField("IF_MUNI").hide();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_MUNI").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").hide();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("CASE_DAYS").hide();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").allowBlank = true;
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("3");
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue(res.json.data[0].APPLY_AMT_TORMB);
											} else if (CUST_TYPE == '2' && STEP_NAME == '4') { // CB+信用审查阶段
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").show();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(false);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").removeClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("BUY_NAME").show();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("BUY_NAME").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("VISIT_DATE").show();
												getCurrentView().contentPanel.form.findField("VISIT_DATE").readOnly = true;
												getCurrentView().contentPanel.form.findField("VISIT_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").show();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '申请日期';
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '申请金额(元)';
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '申请币别';
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").show();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("IF_SAME").hide();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_SAME").setValue('');
												getCurrentView().contentPanel.form.findField("IF_MUNI").hide();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_MUNI").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").hide();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("CASE_DAYS").hide();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").allowBlank = true;
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("4");
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue(res.json.data[0].APPLY_AMT_TORMB);
											} else if (CUST_TYPE == '2' && STEP_NAME == '5') { // CB+核批阶段
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").show();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("BUY_NAME").show();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("BUY_NAME").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(true);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").addClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("VISIT_DATE").show();
												getCurrentView().contentPanel.form.findField("VISIT_DATE").readOnly = true;
												getCurrentView().contentPanel.form.findField("VISIT_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").show();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").show();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = false;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").label.dom.innerHTML = '<font color=red>*</font>预计动拨金额:';
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setReadOnly(false);
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").show();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = false;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").label.dom.innerHTML = '<font color=red>*</font>预计动拨日期:';
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setReadOnly(false);
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").removeClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").show();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("IF_SAME").hide();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_SAME").setValue('');
												getCurrentView().contentPanel.form.findField("IF_MUNI").hide();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_MUNI").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").hide();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").hide();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("CASE_DAYS").hide();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").allowBlank = true;
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("5");
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(res.json.data[0].insureAmt);
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue(res.json.data[0].insureCurrency);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue(res.json.data[0].insureDate != ''?new Date(res.json.data[0].insureDate) : null);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue(res.json.data[0].surplusMAmt);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue(res.json.data[0].INSURE_AMT_TORMB);
											} else if (CUST_TYPE == '2' && STEP_NAME == '6') { // CB+已核批动拨
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").show();
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
												getCurrentView().contentPanel.form.findField("CUST_PROPERTIES").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_NUMBER").show();
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").show();
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_SUBJECT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").show();
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
												getCurrentView().contentPanel.form.findField("PRODUCT_FORM").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("BUY_NAME").show();
												getCurrentView().contentPanel.form.findField("BUY_NAME").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("BUY_NAME").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").show();
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
												if (new_has_cust == '2') {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setReadOnly(false);
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").removeClass('x-readOnly');
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
												} else {
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
													getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
												}
												getCurrentView().contentPanel.form.findField("VISIT_DATE").show();
												getCurrentView().contentPanel.form.findField("VISIT_DATE").readOnly = true;
												getCurrentView().contentPanel.form.findField("VISIT_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("INSURE_DATE").show();
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").show();
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").readOnly = true;
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").show();
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").addClass('x-readOnly');
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").show();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").show();
												getCurrentView().contentPanel.form.findField("CASE_DAYS").setReadOnly(true);
												getCurrentView().contentPanel.form.findField("CASE_DAYS").addClass('x-readOnly');

												getCurrentView().contentPanel.form.findField("IF_SAME").hide();
												getCurrentView().contentPanel.form.findField("IF_SAME").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_SAME").setValue('');
												getCurrentView().contentPanel.form.findField("IF_MUNI").hide();
												getCurrentView().contentPanel.form.findField("IF_MUNI").allowBlank = true;
												getCurrentView().contentPanel.form.findField("IF_MUNI").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").hide();
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("EXPECT_M_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_DATE").hide();
												getCurrentView().contentPanel.form.findField("APPLY_DATE").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_AMT").hide();
												getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").hide();
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue('');
												getCurrentView().contentPanel.form.findField("STEP_DAYS").hide();
												getCurrentView().contentPanel.form.findField("STEP_DAYS").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").hide();
												getCurrentView().contentPanel.form.findField("IF_NEXT").setValue('');
												getCurrentView().contentPanel.form.findField("IF_NEXT").allowBlank = true;
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").hide();
												getCurrentView().contentPanel.form.findField("APPLY_AMT_TORMB").setValue('');
												Ext.getCmp('synchnize').setVisible(true);

												getCurrentView().contentPanel.form.findField("ID").setValue(res.json.data[0].id);
												getCurrentView().contentPanel.form.findField("NOW_PROGRESS").setValue("6");
												getCurrentView().contentPanel.form.findField("NEW_HAS_CUST").setValue(res.json.data[0].newHasCust);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(res.json.data[0].applyAmt);
												getCurrentView().contentPanel.form.findField("APPLY_CURRENCY").setValue(res.json.data[0].applyCurrency);
												getCurrentView().contentPanel.form.findField("APPLY_DATE").setValue(res.json.data[0].applyDate != ''?new Date(res.json.data[0].applyDate) : null);
												getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue(res.json.data[0].insureMoney);
												getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(res.json.data[0].insureAmt);
												getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue(res.json.data[0].insureCurrency);
												getCurrentView().contentPanel.form.findField("INSURE_DATE").setValue(res.json.data[0].insureDate != ''?new Date(res.json.data[0].insureDate) : null);
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT").setValue(res.json.data[0].firstMAmt);
												getCurrentView().contentPanel.form.findField("FIRST_M_DATE").setValue(res.json.data[0].firstMDate != ''?new Date(res.json.data[0].firstMDate) : null);
												getCurrentView().contentPanel.form.findField("FIRST_M_CURRENCY").setValue(res.json.data[0].firstMCurrency);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT").setValue(res.json.data[0].surplusMAmt);
												getCurrentView().contentPanel.form.findField("CA_NUMBER").setValue(res.json.data[0].caNumber);
												getCurrentView().contentPanel.form.findField("CA_SP_STATE").setValue(res.json.data[0].caSPState);
												getCurrentView().contentPanel.form.findField("INSURE_AMT_TORMB").setValue(res.json.data[0].INSURE_AMT_TORMB);
												getCurrentView().contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue(res.json.data[0].FIRST_M_AMT_TORMB);
												getCurrentView().contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue(res.json.data[0].SURPLUS_M_AMT_TORMB);
											}
											Ext.Msg.alert('提示', '同步成功！');
											Ext.getCmp('synchnize').setDisabled(false);
											if_reload = true;
										},
										failure : function() {
											Ext.Msg.alert('提示', '请求同步失败！');
											Ext.getCmp('synchnize').setDisabled(false);
										}
									});
						}
					} ]
		},
		{

			title : '详情',
			hideTitle : JsContext.checkGrant('pipelineAllEdit'),
			type : 'form',
			autoLoadSeleted : true,// 自动加载
			groups : [
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [
								{
									name : 'CUST_TYPE',
									text : '客户细分',
									translateType : 'CUST_TYPE',
									allowBlank : false,
									anchor : '90%'
								},
								{
									name : 'NOW_PROGRESS',
									text : '所属阶段',
									translateType : 'STEP_NAME',
									allowBlank : false,// 是否允许为空
									searchField : true,
									anchor : '90%'
								},
								{
									name : 'CUST_NAME',
									text : '客户名称',
									hiddenName : 'CUST_ID',
									searchField : true,
									anchor : '90%'
								},
								{
									name : 'AREA_NAME',
									text : '区域中心',
									resutlWidth : 80,
									showField : 'text',
									translateType : 'AREA',
									valueField : 'value',
									anchor : '90%',
									listeners : {
										select : function(a, b) {
											a.setValue(b.data.value);
										}
									}
								},
								{
									name : 'DEPT_NAME',
									text : '营业部门',
									hiddenName : 'DEPT_ID',
									anchor : '90%'
								},
								{
									name : 'RM',
									text : 'RM',
									hiddenName : 'RM_ID',
									anchor : '90%'
								},
								{
									name : 'IF_TAIWAN',
									text : '是否台商',
									translateType : 'IF_FLAG',
									anchor : '90%'
								},
								{
									name : 'NEW_HAS_CUST',
									text : '新户/既有增贷',
									translateType : 'NEW_HAS_CUST',
									anchor : '90%',
									allowBlank : false,
									listeners : {
										select : function() {
											var NEW_HAS_CUST = getCurrentView().contentPanel.form
													.findField("NEW_HAS_CUST")
													.getValue();
											if (NEW_HAS_CUST == '2') {
												getCurrentView().contentPanel.form
														.findField(
																"INSURE_MONEY")
														.show();
												getCurrentView().contentPanel.form
														.findField("INSURE_MONEY").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField(
																"INSURE_MONEY")
														.setReadOnly(false);
												getCurrentView().contentPanel.form
														.findField(
																"INSURE_MONEY")
														.removeClass(
																'x-readOnly');
												getCurrentView().contentPanel.form
														.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
											} else {
												getCurrentView().contentPanel.form
														.findField(
																"INSURE_MONEY")
														.hide();
												getCurrentView().contentPanel.form
														.findField(
																"INSURE_MONEY")
														.setValue('');
												getCurrentView().contentPanel.form
														.findField("INSURE_MONEY").allowBlank = true;
											}
										},
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CUST_PROPERTIES',
									text : '客户属性',
									translateType : 'CUST_PROPERTIES',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CA_NUMBER',
									text : 'CA业务流水号',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'IF_SAME',
									text : '是否同行',
									translateType : 'IF_FLAG',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'IF_MUNI',
									text : '是否市政',
									translateType : 'IF_FLAG',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'PRODUCT_SUBJECT',
									text : '产品科目',
									translateType : 'PRODUCT_SUBJECT',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'PRODUCT_FORM',
									text : '产品形态',
									translateType : 'PRODUCT_FORM',
									anchor : '90%',
									allowBlank : true,
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'CA_SP_STATE',
									text : 'CA流程状态',
									translateType : 'PIPELINE_CA_STATE',
									anchor : '90%'
								},

								{
									name : 'BUY_NAME',
									text : '买方名称',
									maxLength : 30,
									allowBlank : true,
									anchor : '90%'
								},
								{
									name : 'APPLY_DATE',
									text : '申请日期',
									dataType : 'date',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'APPLY_AMT',
									text : '申请金额(元)',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'APPLY_CURRENCY',
									text : '申请币别',
									translateType : 'PIPELINE_CURRENCY',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								},
								{
									name : 'INSURE_AMT',
									text : '核批金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'INSURE_CURRENCY',
									text : '核批币别',
									translateType : 'PIPELINE_CURRENCY',
									anchor : '90%'
								},
								{
									name : 'INSURE_DATE',
									text : '核批日期',
									dataType : 'date',
									// width : 160,
									anchor : '90%'
								},
								{
									name : 'EXPECT_M_AMT',
									text : '预计动拨金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'EXPECT_M_DATE',
									text : '预计动拨日期',
									dataType : 'date',
									anchor : '90%'
								},
								{
									name : 'FIRST_M_AMT',
									text : '首次动拨金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'FIRST_M_DATE',
									text : '首次动拨日期',
									dataType : 'date',
									// width : 160,
									anchor : '90%'
								},
								{
									name : 'FIRST_M_CURRENCY',
									text : '首次动拨币别',
									translateType : 'PIPELINE_CURRENCY',
									anchor : '90%'
								},
								{
									name : 'SURPLUS_M_AMT',
									text : '剩余动拨金额',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},
								{
									name : 'INSURE_MONEY',
									text : '原核批额度（元）',
									regex : /^[-]?[0-9]+([.][0-9]+){0,1}$/,
									regexText : '格式有误,例：999999.9，-9999999.9',
									maxLength : 20,
									anchor : '90%'
								},

								{
									name : 'STEP_DAYS',
									text : '本阶段案件处理天数',
									allowBlank : true,
									anchor : '90%'
								},
								{
									name : 'VISIT_DATE',
									text : '首次拜访日期',
									allowBlank : false,
									anchor : '90%'
								},
								{
									name : 'IF_NEXT',
									text : '是否进入下一阶段',
									translateType : 'IF_NEXT',
									anchor : '90%',
									listeners : {
										'focus' : {
											fn : function(e) {
												e.expand();
												this.doQuery(this.allQuery,
														true);
											},
											buffer : 200
										}
									}
								}, {
									name : 'CASE_DAYS',
									text : '案件总计天数',
									anchor : '90%'
								}, {
									name : 'AREA_ID'
								}, {
									name : 'RM_ID'
								}, {
									name : 'CUST_ID'
								}, {
									name : 'PIPELINE_ID'
								}, {
									name : 'DEPT_ID'
								}, {
									name : 'ID'
								}, {
									name : 'CREATER'
								}, {
									name : 'CREATE_DATE'
								}, {
									name : 'UPDATER'
								}, {
									name : 'UPDATE_DATE'
								}, {
									name : 'FIRST_IN_DATE'
								}, {
									name : 'CA_VALIDITY'
								}, {
									name : 'CA_DUE'
								} ],
						fn : function(CUST_TYPE, NOW_PROGRESS, CUST_NAME,
								AREA_NAME, DEPT_NAME, RM, IF_TAIWAN,
								NEW_HAS_CUST, CUST_PROPERTIES, CA_NUMBER,
								IF_SAME, IF_MUNI, PRODUCT_SUBJECT,
								PRODUCT_FORM, CA_SP_STATE, BUY_NAME,
								APPLY_DATE, APPLY_AMT, APPLY_CURRENCY,
								INSURE_AMT, INSURE_CURRENCY, INSURE_DATE,
								EXPECT_M_AMT, EXPECT_M_DATE, FIRST_M_AMT,
								FIRST_M_DATE, FIRST_M_CURRENCY, SURPLUS_M_AMT,
								INSURE_MONEY, STEP_DAYS, VISIT_DATE, IF_NEXT,
								CASE_DAYS, AREA_ID, RM_ID, CUST_ID,
								PIPELINE_ID, DEPT_ID, ID, CREATER, CREATE_DATE,
								UPDATER, UPDATE_DATE, FIRST_IN_DATE,
								CA_VALIDITY, CA_DUE) {

							CUST_TYPE.readOnly = true;
							CUST_TYPE.cls = 'x-readOnly';
							NOW_PROGRESS.readOnly = true;
							NOW_PROGRESS.cls = 'x-readOnly';
							CUST_NAME.readOnly = true;
							CUST_NAME.cls = 'x-readOnly';
							AREA_NAME.readOnly = true;
							AREA_NAME.cls = 'x-readOnly';
							DEPT_NAME.readOnly = true;
							DEPT_NAME.cls = 'x-readOnly';
							CA_SP_STATE.readOnly = true;
							CA_SP_STATE.cls = 'x-readOnly';
							RM.readOnly = true;
							RM.cls = 'x-readOnly';
							IF_TAIWAN.readOnly = true;// 设为只读
							IF_TAIWAN.cls = 'x-readOnly';// 设为灰色
							VISIT_DATE.readOnly = true;
							VISIT_DATE.cls = 'x-readOnly';
							CA_NUMBER.hidden = true;
							CA_SP_STATE.hidden = true;
							IF_SAME.hidden = true;
							IF_MUNI.hidden = true;
							CUST_ID.hidden = true;
							AREA_ID.hidden = true;
							DEPT_ID.hidden = true;
							RM_ID.hidden = true;
							INSURE_AMT.hidden = true;
							INSURE_CURRENCY.hidden = true;
							// INSURE_DATE.hidden = true;
							INSURE_DATE.setVisible(false);
							EXPECT_M_AMT.hidden = true;
							EXPECT_M_DATE.hidden = true;
							FIRST_M_AMT.hidden = true;
							FIRST_M_DATE.hidden = true;
							FIRST_M_CURRENCY.hidden = true;
							SURPLUS_M_AMT.hidden = true;
							INSURE_MONEY.hidden = true;
							STEP_DAYS.hidden = true;
							CASE_DAYS.hidden = true;
							IF_NEXT.hidden = true;
							PIPELINE_ID.hidden = true;
							ID.hidden = true;
							CREATER.hidden = true;
							CREATE_DATE.hidden = true;
							UPDATER.hidden = true;
							UPDATE_DATE.hidden = true;
							FIRST_IN_DATE.hidden = true;
							CA_VALIDITY.hidden = true;
							CA_DUE.hidden = true;

							return [ CUST_TYPE, NOW_PROGRESS, CUST_NAME,
									AREA_NAME, DEPT_NAME, RM, IF_TAIWAN,
									NEW_HAS_CUST, CUST_PROPERTIES, CA_NUMBER,
									IF_SAME, IF_MUNI, PRODUCT_SUBJECT,
									PRODUCT_FORM, CA_SP_STATE, BUY_NAME,
									APPLY_DATE, APPLY_AMT, APPLY_CURRENCY,
									INSURE_AMT, INSURE_CURRENCY, INSURE_DATE,
									EXPECT_M_AMT, EXPECT_M_DATE, FIRST_M_AMT,
									FIRST_M_DATE, FIRST_M_CURRENCY,
									SURPLUS_M_AMT, INSURE_MONEY, STEP_DAYS,
									VISIT_DATE, IF_NEXT, CASE_DAYS, AREA_ID,
									RM_ID, CUST_ID, PIPELINE_ID, DEPT_ID, ID,
									CREATER, CREATE_DATE, UPDATER, UPDATE_DATE,
									FIRST_IN_DATE, CA_VALIDITY, CA_DUE ];
						}
					}, {
						columnCount : 1,
						fields : [ {
							name : 'MEMO',
							text : '备注',
							xtype : 'textarea',
							maxLength : 200
						} ],
						fn : function(MEMO) {
							return [ MEMO ];
						}
					} ]
		}];

// 面板滑出前校验
var beforeviewshow = function(view) {
	if (view._defaultTitle == '新增合作意向') {
		//申请币别，默认人民币
		view.contentPanel.form.findField("APPLY_CURRENCY").setValue("CNY");
		view.contentPanel.form.findField("NOW_PROGRESS").setValue("2");
		view.contentPanel.form.findField("NOW_PROGRESS").readOnly = true;
		view.contentPanel.form.findField("NOW_PROGRESS").addClass("x-readOnly");
		view.contentPanel.form.findField("CA_NUMBER").hide();
		view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
		view.contentPanel.form.findField("CA_NUMBER").setValue('');
		view.contentPanel.form.findField("CA_SP_STATE").hide();
		view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
		view.contentPanel.form.findField("CA_SP_STATE").setValue('');
		view.contentPanel.form.findField("INSURE_MONEY").hide();
		view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
		view.contentPanel.form.findField("INSURE_MONEY").setValue('');
		view.contentPanel.form.findField("IF_SAME").hide();
		view.contentPanel.form.findField("IF_SAME").allowBlank = true;
		view.contentPanel.form.findField("IF_SAME").setValue('');
		view.contentPanel.form.findField("IF_MUNI").hide();
		view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
		view.contentPanel.form.findField("IF_MUNI").setValue('');
		view.contentPanel.form.findField("IF_NEXT").hide();
		view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
		view.contentPanel.form.findField("IF_NEXT").setValue('');
		view.contentPanel.form.findField("VISIT_DATE").label.dom.innerHTML = '<font color=red>*</font>首次拜访日期:';

		view.contentPanel.form.findField("CUST_PROPERTIES").show();
		view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = false;
		view.contentPanel.form.findField("CUST_PROPERTIES").label.dom.innerHTML = '<font color=red>*</font>客户属性:';
		view.contentPanel.form.findField("BUY_NAME").show();
		view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
		view.contentPanel.form.findField("BUY_NAME").label.dom.innerHTML = '<font color=red></font>买方名称:';
		view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
		view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = false;
		view.contentPanel.form.findField("PRODUCT_SUBJECT").label.dom.innerHTML = '<font color=red>*</font>产品科目:';
		view.contentPanel.form.findField("PRODUCT_FORM").show();
		view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
		view.contentPanel.form.findField("PRODUCT_FORM").label.dom.innerHTML = '<font color=red></font>产品形态:';
	}
	if (view._defaultTitle == '修改') {
		if_reload = false;
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请选择一条数据！');
			return false;
		}
		// 取得当前操作人id
		var user_id = JsContext._userId;
		// 取得选中数据的RM信息
		var rm_id = getSelectedData().data.RM_ID;
		if (user_id != rm_id) {
			Ext.Msg.alert('提示信息', '无权限修改其他RM的案件！');
			return false;
		}
		var records = getAllSelects();
		var tempRecord = records[0];
		var cust_type = records[0].data.CUST_TYPE;// 获取客户细分字段值
		var step_name = records[0].data.NOW_PROGRESS;// 获取所处阶段字段值
		var new_has_cust = records[0].data.NEW_HAS_CUST;
		if (cust_type == '1' && step_name == '2') { // TT+合作意向阶段
			view.contentPanel.form.findField("IF_SAME").show();
			view.contentPanel.form.findField("IF_SAME").allowBlank = false;
			view.contentPanel.form.findField("IF_SAME").readOnly = false;
			view.contentPanel.form.findField("IF_SAME").removeClass('x-readOnly');
			view.contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
			view.contentPanel.form.findField("IF_MUNI").show();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
			view.contentPanel.form.findField("IF_MUNI").readOnly = false;
			view.contentPanel.form.findField("IF_MUNI").removeClass('x-readOnly');
			view.contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").removeClass('x-readOnly');
			view.contentPanel.form.findField("NEW_HAS_CUST").label.dom.innerHTML = '<font color=red>*</font>新户/既有增贷:';
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(false);
				view.contentPanel.form.findField("INSURE_MONEY").removeClass('x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_DATE").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '<font color=red>*</font>申请日期:';
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_AMT").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '<font color=red>*</font>申请金额(元):';
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '<font color=red>*</font>申请币别:';
			view.contentPanel.form.findField("IF_NEXT").show();
			view.contentPanel.form.findField("IF_NEXT").allowBlank = false;
			view.contentPanel.form.findField("IF_NEXT").readOnly = false;
			view.contentPanel.form.findField("IF_NEXT").removeClass('x-readOnly');
			view.contentPanel.form.findField("IF_NEXT").label.dom.innerHTML = '<font color=red>*</font>是否进入下一阶段:';
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();

			view.contentPanel.form.findField("CUST_PROPERTIES").hide();
			view.contentPanel.form.findField("CUST_PROPERTIES").setValue('');
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
			view.contentPanel.form.findField("CA_NUMBER").hide();
			view.contentPanel.form.findField("CA_NUMBER").setValue('');
			view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
			view.contentPanel.form.findField("CA_SP_STATE").hide();
			view.contentPanel.form.findField("CA_SP_STATE").setValue('');
			view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").hide();
			view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").hide();
			view.contentPanel.form.findField("BUY_NAME").setValue('');
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_AMT").setValue('');
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_DATE").hide();
			view.contentPanel.form.findField("INSURE_DATE").setValue('');
			view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT_TORMB").hide();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(false);
		} else if (cust_type == '1' && step_name == '3') { // TT+CA准备阶段
			view.contentPanel.form.findField("IF_SAME").show();
			view.contentPanel.form.findField("IF_SAME").allowBlank = false;
			view.contentPanel.form.findField("IF_SAME").readOnly = false;
			view.contentPanel.form.findField("IF_SAME").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
			view.contentPanel.form.findField("IF_MUNI").show();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
			view.contentPanel.form.findField("IF_MUNI").readOnly = false;
			view.contentPanel.form.findField("IF_MUNI").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("NEW_HAS_CUST").label.dom.innerHTML = '<font color=red>*</font>新户/既有增贷:';
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						false);
				view.contentPanel.form.findField("INSURE_MONEY").removeClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(false);
			view.contentPanel.form.findField("CA_NUMBER").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_DATE").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '<font color=red>*</font>申请日期:';
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_AMT").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '<font color=red>*</font>申请金额(元):';
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '<font color=red>*</font>申请币别:';
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").readOnly = true;
			view.contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();

			view.contentPanel.form.findField("CUST_PROPERTIES").hide();
			view.contentPanel.form.findField("CUST_PROPERTIES").setValue('');
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").hide();
			view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").hide();
			view.contentPanel.form.findField("BUY_NAME").setValue('');
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_AMT").setValue('');
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_DATE").hide();
			view.contentPanel.form.findField("INSURE_DATE").setValue('');
			view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT_TORMB").hide();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '1' && step_name == '4') { // TT+信用审查阶段
			view.contentPanel.form.findField("IF_SAME").show();
			view.contentPanel.form.findField("IF_SAME").allowBlank = false;
			view.contentPanel.form.findField("IF_SAME").readOnly = false;
			view.contentPanel.form.findField("IF_SAME").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
			view.contentPanel.form.findField("IF_MUNI").show();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
			view.contentPanel.form.findField("IF_MUNI").readOnly = false;
			view.contentPanel.form.findField("IF_MUNI").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
			view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
					'x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_MONEY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
			view.contentPanel.form.findField("CA_NUMBER")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_AMT")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();

			view.contentPanel.form.findField("CUST_PROPERTIES").hide();
			view.contentPanel.form.findField("CUST_PROPERTIES").setValue('');
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").hide();
			view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").hide();
			view.contentPanel.form.findField("BUY_NAME").setValue('');
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_AMT").setValue('');
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_DATE").hide();
			view.contentPanel.form.findField("INSURE_DATE").setValue('');
			view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT_TORMB").hide();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '1' && step_name == '5') { // TT+核批阶段
			view.contentPanel.form.findField("IF_SAME").show();
			view.contentPanel.form.findField("IF_SAME").allowBlank = false;
			view.contentPanel.form.findField("IF_SAME").readOnly = false;
			view.contentPanel.form.findField("IF_SAME").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
			view.contentPanel.form.findField("IF_MUNI").show();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
			view.contentPanel.form.findField("IF_MUNI").readOnly = false;
			view.contentPanel.form.findField("IF_MUNI").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
			view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
					'x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_MONEY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
			view.contentPanel.form.findField("CA_NUMBER")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_AMT")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_AMT").show();
			view.contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_AMT").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_CURRENCY").show();
			view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_DATE").show();
			view.contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("EXPECT_M_AMT").show();
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = false;
			view.contentPanel.form.findField("EXPECT_M_AMT").setReadOnly(false);
			view.contentPanel.form.findField("EXPECT_M_AMT").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("EXPECT_M_DATE").show();
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = false;
			view.contentPanel.form.findField("EXPECT_M_DATE")
					.setReadOnly(false);
			view.contentPanel.form.findField("EXPECT_M_DATE").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").show();

			view.contentPanel.form.findField("CUST_PROPERTIES").hide();
			view.contentPanel.form.findField("CUST_PROPERTIES").setValue('');
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").hide();
			view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").hide();
			view.contentPanel.form.findField("BUY_NAME").setValue('');
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '1' && step_name == '6') { // TT+已核批动拨
			view.contentPanel.form.findField("IF_SAME").show();
			view.contentPanel.form.findField("IF_SAME").allowBlank = false;
			view.contentPanel.form.findField("IF_SAME").readOnly = false;
			view.contentPanel.form.findField("IF_SAME").removeClass('x-readOnly');
			view.contentPanel.form.findField("IF_SAME").label.dom.innerHTML = '<font color=red>*</font>是否同行:';
			view.contentPanel.form.findField("IF_MUNI").show();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
			view.contentPanel.form.findField("IF_MUNI").readOnly = false;
			view.contentPanel.form.findField("IF_MUNI").removeClass('x-readOnly');
			view.contentPanel.form.findField("IF_MUNI").label.dom.innerHTML = '<font color=red>*</font>是否市政:';
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
			view.contentPanel.form.findField("NEW_HAS_CUST").addClass('x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(true);
				view.contentPanel.form.findField("INSURE_MONEY").addClass('x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
			view.contentPanel.form.findField("CA_NUMBER").addClass('x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_AMT").addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_DATE").addClass('x-readOnly');
			view.contentPanel.form.findField("INSURE_AMT").show();
			view.contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_AMT").addClass('x-readOnly');
			view.contentPanel.form.findField("INSURE_CURRENCY").show();
			view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").addClass('x-readOnly');
			view.contentPanel.form.findField("INSURE_DATE").show();
			view.contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_DATE").addClass('x-readOnly');
			view.contentPanel.form.findField("FIRST_M_DATE").show();
			view.contentPanel.form.findField("FIRST_M_DATE").setReadOnly(true);
			view.contentPanel.form.findField("FIRST_M_DATE").addClass('x-readOnly');
			view.contentPanel.form.findField("FIRST_M_AMT").show();
			view.contentPanel.form.findField("FIRST_M_AMT").setReadOnly(true);
			view.contentPanel.form.findField("FIRST_M_AMT").addClass('x-readOnly');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").show();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").addClass('x-readOnly');
			view.contentPanel.form.findField("SURPLUS_M_AMT").show();
			view.contentPanel.form.findField("SURPLUS_M_AMT").setReadOnly(true);
			view.contentPanel.form.findField("SURPLUS_M_AMT").addClass('x-readOnly');
			view.contentPanel.form.findField("CASE_DAYS").show();
			view.contentPanel.form.findField("CASE_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("CASE_DAYS").addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").show();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").show();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").show();

			view.contentPanel.form.findField("CUST_PROPERTIES").hide();
			view.contentPanel.form.findField("CUST_PROPERTIES").setValue('');
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").setValue('');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").hide();
			view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").hide();
			view.contentPanel.form.findField("BUY_NAME").setValue('');
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("STEP_DAYS").hide();
			view.contentPanel.form.findField("STEP_DAYS").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '2' && step_name == '2') { // CB+合作意向阶段
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").label.dom.innerHTML = '<font color=red>*</font>新户/既有增贷:';
			view.contentPanel.form.findField("NEW_HAS_CUST").removeClass(
					'x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						false);
				view.contentPanel.form.findField("INSURE_MONEY").removeClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CUST_PROPERTIES").show();
			view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = false;
			view.contentPanel.form.findField("CUST_PROPERTIES").label.dom.innerHTML = '<font color=red>*</font>客户属性:';
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = false;
			view.contentPanel.form.findField("CUST_PROPERTIES").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = false;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").label.dom.innerHTML = '<font color=red>*</font>产品科目:';
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = false;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_FORM").show();
			view.contentPanel.form.findField("PRODUCT_FORM").readOnly = false;
			view.contentPanel.form.findField("PRODUCT_FORM").label.dom.innerHTML = '<font color=red></font>产品形态:';
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("BUY_NAME").show();
			view.contentPanel.form.findField("BUY_NAME").setReadOnly(false);
			view.contentPanel.form.findField("BUY_NAME").label.dom.innerHTML = '<font color=red></font>买方名称:';
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("VISIT_DATE").show();
			view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
			view.contentPanel.form.findField("VISIT_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '<font color=red>*</font>申请日期:';
			view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
			view.contentPanel.form.findField("APPLY_DATE").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '<font color=red>*</font>申请金额(元):';
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").removeClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '<font color=red>*</font>申请币别:';
			view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").removeClass('x-readOnly');
			view.contentPanel.form.findField("IF_NEXT").show();
			view.contentPanel.form.findField("IF_NEXT").label.dom.innerHTML = '<font color=red>*</font>是否进入下一阶段:';
			view.contentPanel.form.findField("IF_NEXT").allowBlank = false;
			view.contentPanel.form.findField("IF_NEXT").removeClass('x-readOnly');
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS").addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();

			view.contentPanel.form.findField("IF_SAME").hide();
			view.contentPanel.form.findField("IF_SAME").allowBlank = true;
			view.contentPanel.form.findField("IF_SAME").setValue('');
			view.contentPanel.form.findField("IF_MUNI").hide();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
			view.contentPanel.form.findField("IF_MUNI").setValue('');
			view.contentPanel.form.findField("CA_NUMBER").hide();
			view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
			view.contentPanel.form.findField("CA_NUMBER").setValue('');
			view.contentPanel.form.findField("CA_SP_STATE").hide();
			view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
			view.contentPanel.form.findField("CA_SP_STATE").setValue('');
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").setValue('');
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
			view.contentPanel.form.findField("INSURE_DATE").hide();
			view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
			view.contentPanel.form.findField("INSURE_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("INSURE_AMT_TORMB").hide();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(false);
		} else if (cust_type == '2' && step_name == '3') { // CB+CA准备阶段
			view.contentPanel.form.findField("CUST_PROPERTIES").show();
			view.contentPanel.form.findField("CUST_PROPERTIES").label.dom.innerHTML = '<font color=red>*</font>客户属性:';
			view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = false;
			view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = false;
			view.contentPanel.form.findField("CUST_PROPERTIES").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").label.dom.innerHTML = '<font color=red>*</font>新户/既有增贷:';
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").removeClass(
					'x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						false);
				view.contentPanel.form.findField("INSURE_MONEY").removeClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(false);
			view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
			view.contentPanel.form.findField("CA_NUMBER").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").label.dom.innerHTML = '<font color=red>*</font>产品科目:';
			view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = false;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_FORM").show();
			view.contentPanel.form.findField("PRODUCT_FORM").label.dom.innerHTML = '<font color=red></font>产品形态:';
			view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
			view.contentPanel.form.findField("PRODUCT_FORM").readOnly = false;
			view.contentPanel.form.findField("PRODUCT_FORM").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("BUY_NAME").show();
			view.contentPanel.form.findField("BUY_NAME").label.dom.innerHTML = '<font color=red></font>买方名称:';
			view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
			view.contentPanel.form.findField("BUY_NAME").setReadOnly(false);
			view.contentPanel.form.findField("BUY_NAME").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("VISIT_DATE").show();
			view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
			view.contentPanel.form.findField("VISIT_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '<font color=red>*</font>申请日期:';
			view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
			view.contentPanel.form.findField("APPLY_DATE").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(false);
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '<font color=red>*</font>申请金额(元):';
			view.contentPanel.form.findField("APPLY_AMT").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '<font color=red>*</font>申请币别:';
			view.contentPanel.form.findField("APPLY_CURRENCY").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();

			view.contentPanel.form.findField("IF_SAME").hide();
			view.contentPanel.form.findField("IF_SAME").allowBlank = true;
			view.contentPanel.form.findField("IF_SAME").setValue('');
			view.contentPanel.form.findField("IF_MUNI").hide();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
			view.contentPanel.form.findField("IF_MUNI").setValue('');
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").setValue('');
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
			view.contentPanel.form.findField("INSURE_DATE").hide();
			view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
			view.contentPanel.form.findField("INSURE_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("INSURE_AMT_TORMB").hide();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '2' && step_name == '4') { // CB+信用审查阶段
			view.contentPanel.form.findField("CUST_PROPERTIES").show();
			view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = false;
			view.contentPanel.form.findField("CUST_PROPERTIES").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = false;
			view.contentPanel.form.findField("NEW_HAS_CUST").removeClass(
					'x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						false);
				view.contentPanel.form.findField("INSURE_MONEY").removeClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
			view.contentPanel.form.findField("CA_NUMBER")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = false;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_FORM").show();
			view.contentPanel.form.findField("PRODUCT_FORM").readOnly = false;
			view.contentPanel.form.findField("PRODUCT_FORM").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("BUY_NAME").show();
			view.contentPanel.form.findField("BUY_NAME").setReadOnly(false);
			view.contentPanel.form.findField("BUY_NAME").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("VISIT_DATE").show();
			view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
			view.contentPanel.form.findField("VISIT_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_DATE").show();
			view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '申请日期';
			view.contentPanel.form.findField("APPLY_DATE").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '申请金额(元)';
			view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
			view.contentPanel.form.findField("APPLY_AMT")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_CURRENCY").show();
			view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '申请币别';
			view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").show();

			view.contentPanel.form.findField("IF_SAME").hide();
			view.contentPanel.form.findField("IF_SAME").allowBlank = true;
			view.contentPanel.form.findField("IF_SAME").setValue('');
			view.contentPanel.form.findField("IF_MUNI").hide();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
			view.contentPanel.form.findField("IF_MUNI").setValue('');
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").setValue('');
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
			view.contentPanel.form.findField("INSURE_DATE").hide();
			view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
			view.contentPanel.form.findField("INSURE_DATE").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("INSURE_AMT_TORMB").hide();
			view.contentPanel.form.findField("INSURE_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '2' && step_name == '5') { // CB+核批阶段
			view.contentPanel.form.findField("CUST_PROPERTIES").show();
			view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
			view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
			view.contentPanel.form.findField("CA_NUMBER")
					.addClass('x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_MONEY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_FORM").show();
			view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
			view.contentPanel.form.findField("PRODUCT_FORM").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("BUY_NAME").show();
			view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
			view.contentPanel.form.findField("BUY_NAME").addClass('x-readOnly');
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
			view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("VISIT_DATE").show();
			view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
			view.contentPanel.form.findField("VISIT_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_AMT").show();
			view.contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_AMT").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_CURRENCY").show();
			view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_DATE").show();
			view.contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("EXPECT_M_AMT").show();
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = false;
			view.contentPanel.form.findField("EXPECT_M_AMT").label.dom.innerHTML = '<font color=red>*</font>预计动拨金额:';
			view.contentPanel.form.findField("EXPECT_M_AMT").setReadOnly(false);
			view.contentPanel.form.findField("EXPECT_M_AMT").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("EXPECT_M_DATE").show();
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = false;
			view.contentPanel.form.findField("EXPECT_M_DATE").label.dom.innerHTML = '<font color=red>*</font>预计动拨日期:';
			view.contentPanel.form.findField("EXPECT_M_DATE")
					.setReadOnly(false);
			view.contentPanel.form.findField("EXPECT_M_DATE").removeClass(
					'x-readOnly');
			view.contentPanel.form.findField("STEP_DAYS").show();
			view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("STEP_DAYS")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("INSURE_AMT_TORMB").show();

			view.contentPanel.form.findField("IF_SAME").hide();
			view.contentPanel.form.findField("IF_SAME").allowBlank = true;
			view.contentPanel.form.findField("IF_SAME").setValue('');
			view.contentPanel.form.findField("IF_MUNI").hide();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
			view.contentPanel.form.findField("IF_MUNI").setValue('');
			view.contentPanel.form.findField("APPLY_DATE").hide();
			view.contentPanel.form.findField("APPLY_DATE").allowBlank = true;
			view.contentPanel.form.findField("APPLY_DATE").setValue('');
			view.contentPanel.form.findField("APPLY_AMT").hide();
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
			view.contentPanel.form.findField("APPLY_AMT").setValue('');
			view.contentPanel.form.findField("APPLY_CURRENCY").hide();
			view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("APPLY_CURRENCY").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT").hide();
			view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
			view.contentPanel.form.findField("FIRST_M_DATE").hide();
			view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
			view.contentPanel.form.findField("CASE_DAYS").hide();
			view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			view.contentPanel.form.findField("CASE_DAYS").setValue('');
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("APPLY_AMT_TORMB").hide();
			view.contentPanel.form.findField("APPLY_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").setValue('');
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").hide();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		} else if (cust_type == '2' && step_name == '6') { // CB+已核批动拨
			view.contentPanel.form.findField("CUST_PROPERTIES").show();
			view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
			view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("CA_NUMBER").show();
			view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
			view.contentPanel.form.findField("CA_NUMBER")
					.addClass('x-readOnly');
			view.contentPanel.form.findField("CA_SP_STATE").show();
			view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(true);
			view.contentPanel.form.findField("CA_SP_STATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
			view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
			view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("PRODUCT_FORM").show();
			view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
			view.contentPanel.form.findField("PRODUCT_FORM").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("BUY_NAME").show();
			view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
			view.contentPanel.form.findField("BUY_NAME").addClass('x-readOnly');
			view.contentPanel.form.findField("NEW_HAS_CUST").show();
			view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
			view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
					'x-readOnly');
			if (new_has_cust == '2') {
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_MONEY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_MONEY").label.dom.innerHTML = '<font color=red>*</font>原核批额度（元）:';
			} else {
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
			}
			view.contentPanel.form.findField("VISIT_DATE").show();
			view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
			view.contentPanel.form.findField("VISIT_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_AMT").show();
			view.contentPanel.form.findField("INSURE_AMT").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_AMT").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_CURRENCY").show();
			view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("INSURE_DATE").show();
			view.contentPanel.form.findField("INSURE_DATE").setReadOnly(true);
			view.contentPanel.form.findField("INSURE_DATE").addClass(
					'x-readOnly');
			view.contentPanel.form.findField("FIRST_M_AMT").show();
			view.contentPanel.form.findField("FIRST_M_AMT").setReadOnly(true);
			view.contentPanel.form.findField("FIRST_M_AMT").addClass('x-readOnly');
			view.contentPanel.form.findField("FIRST_M_DATE").show();
			view.contentPanel.form.findField("FIRST_M_DATE").setReadOnly(true);
			view.contentPanel.form.findField("FIRST_M_DATE").addClass('x-readOnly');
			view.contentPanel.form.findField("FIRST_M_CURRENCY").show();
			view.contentPanel.form.findField("FIRST_M_CURRENCY").readOnly = true;
			view.contentPanel.form.findField("FIRST_M_CURRENCY").addClass('x-readOnly');
			view.contentPanel.form.findField("SURPLUS_M_AMT").show();
			view.contentPanel.form.findField("SURPLUS_M_AMT").setReadOnly(true);
			view.contentPanel.form.findField("SURPLUS_M_AMT").addClass('x-readOnly');
			view.contentPanel.form.findField("CASE_DAYS").show();
			view.contentPanel.form.findField("CASE_DAYS").setReadOnly(true);
			view.contentPanel.form.findField("CASE_DAYS").addClass('x-readOnly');
			view.contentPanel.form.findField("INSURE_AMT_TORMB").show();
			view.contentPanel.form.findField("FIRST_M_AMT_TORMB").show();
			view.contentPanel.form.findField("SURPLUS_M_AMT_TORMB").show();

			view.contentPanel.form.findField("IF_SAME").hide();
			view.contentPanel.form.findField("IF_SAME").allowBlank = true;
			view.contentPanel.form.findField("IF_SAME").setValue('');
			view.contentPanel.form.findField("IF_MUNI").hide();
			view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
			view.contentPanel.form.findField("IF_MUNI").setValue('');
			view.contentPanel.form.findField("EXPECT_M_AMT").hide();
			view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
			view.contentPanel.form.findField("EXPECT_M_DATE").hide();
			view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
			view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
			view.contentPanel.form.findField("APPLY_DATE").hide();
			view.contentPanel.form.findField("APPLY_DATE").allowBlank = true;
			view.contentPanel.form.findField("APPLY_DATE").setValue('');
			view.contentPanel.form.findField("APPLY_AMT").hide();
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
			view.contentPanel.form.findField("APPLY_AMT").setValue('');
			view.contentPanel.form.findField("APPLY_CURRENCY").hide();
			view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("APPLY_CURRENCY").setValue('');
			view.contentPanel.form.findField("STEP_DAYS").hide();
			view.contentPanel.form.findField("STEP_DAYS").setValue('');
			view.contentPanel.form.findField("IF_NEXT").hide();
			view.contentPanel.form.findField("IF_NEXT").setValue('');
			view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			view.contentPanel.form.findField("APPLY_AMT_TORMB").hide();
			view.contentPanel.form.findField("APPLY_AMT_TORMB").setValue('');
			Ext.getCmp('synchnize').setVisible(true);
		}
		view.contentPanel.getForm().loadRecord(tempRecord);
	}
	if (view._defaultTitle == '详情') {
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请选择一条数据！');
			return false;
		} else {
			var records = getAllSelects();
			var tempRecord = records[0];
			var cust_type = records[0].data.CUST_TYPE;// 获取客户细分字段值
			var step_name = records[0].data.NOW_PROGRESS;// 获取所处阶段字段值
			var new_has_cust = records[0].data.NEW_HAS_CUST;
			if (cust_type == '1' && step_name == '2') { // TT+合作意向阶段
				view.contentPanel.form.findField("IF_SAME").show();
				view.contentPanel.form.findField("IF_SAME").allowBlank = false;
				view.contentPanel.form.findField("IF_SAME").readOnly = true;
				view.contentPanel.form.findField("IF_SAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_MUNI").show();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
				view.contentPanel.form.findField("IF_MUNI").readOnly = true;
				view.contentPanel.form.findField("IF_MUNI").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_NEXT").show();
				view.contentPanel.form.findField("IF_NEXT").allowBlank = false;
				view.contentPanel.form.findField("IF_NEXT").readOnly = true;
				view.contentPanel.form.findField("IF_NEXT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("CUST_PROPERTIES").hide();
				view.contentPanel.form.findField("CUST_PROPERTIES")
						.setValue('');
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
				view.contentPanel.form.findField("CA_NUMBER").hide();
				view.contentPanel.form.findField("CA_NUMBER").setValue('');
				view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
				view.contentPanel.form.findField("CA_SP_STATE").hide();
				view.contentPanel.form.findField("CA_SP_STATE").setValue('');
				view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
				view.contentPanel.form.findField("PRODUCT_SUBJECT")
						.setValue('');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").hide();
				view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").hide();
				view.contentPanel.form.findField("BUY_NAME").setValue('');
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY")
						.setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_DATE").hide();
				view.contentPanel.form.findField("INSURE_DATE").setValue('');
				view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
			} else if (cust_type == '1' && step_name == '3') { // TT+CA准备阶段
				view.contentPanel.form.findField("IF_SAME").show();
				view.contentPanel.form.findField("IF_SAME").allowBlank = false;
				view.contentPanel.form.findField("IF_SAME").readOnly = true;
				view.contentPanel.form.findField("IF_SAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_MUNI").show();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
				view.contentPanel.form.findField("IF_MUNI").readOnly = true;
				view.contentPanel.form.findField("IF_MUNI").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").readOnly = true;
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("CUST_PROPERTIES").hide();
				view.contentPanel.form.findField("CUST_PROPERTIES")
						.setValue('');
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
				view.contentPanel.form.findField("PRODUCT_SUBJECT")
						.setValue('');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").hide();
				view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").hide();
				view.contentPanel.form.findField("BUY_NAME").setValue('');
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY")
						.setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_DATE").hide();
				view.contentPanel.form.findField("INSURE_DATE").setValue('');
				view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").setValue('');
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			} else if (cust_type == '1' && step_name == '4') { // TT+信用审查阶段
				view.contentPanel.form.findField("IF_SAME").show();
				view.contentPanel.form.findField("IF_SAME").allowBlank = false;
				view.contentPanel.form.findField("IF_SAME").readOnly = true;
				view.contentPanel.form.findField("IF_SAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_MUNI").show();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
				view.contentPanel.form.findField("IF_MUNI").readOnly = true;
				view.contentPanel.form.findField("IF_MUNI").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("CUST_PROPERTIES").hide();
				view.contentPanel.form.findField("CUST_PROPERTIES")
						.setValue('');
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
				view.contentPanel.form.findField("PRODUCT_SUBJECT")
						.setValue('');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").hide();
				view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").hide();
				view.contentPanel.form.findField("BUY_NAME").setValue('');
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY")
						.setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_DATE").hide();
				view.contentPanel.form.findField("INSURE_DATE").setValue('');
				view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").setValue('');
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			} else if (cust_type == '1' && step_name == '5') { // TT+核批阶段
				view.contentPanel.form.findField("IF_SAME").show();
				view.contentPanel.form.findField("IF_SAME").allowBlank = false;
				view.contentPanel.form.findField("IF_SAME").readOnly = true;
				view.contentPanel.form.findField("IF_SAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_MUNI").show();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
				view.contentPanel.form.findField("IF_MUNI").readOnly = true;
				view.contentPanel.form.findField("IF_MUNI").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_AMT")
						.setReadOnly(true);
				view.contentPanel.form.findField("INSURE_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_DATE").show();
				view.contentPanel.form.findField("INSURE_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("EXPECT_M_AMT").show();
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = false;
				view.contentPanel.form.findField("EXPECT_M_AMT").setReadOnly(
						true);
				view.contentPanel.form.findField("EXPECT_M_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("EXPECT_M_DATE").show();
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = false;
				view.contentPanel.form.findField("EXPECT_M_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("EXPECT_M_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("CUST_PROPERTIES").hide();
				view.contentPanel.form.findField("CUST_PROPERTIES")
						.setValue('');
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
				view.contentPanel.form.findField("PRODUCT_SUBJECT")
						.setValue('');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").hide();
				view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").hide();
				view.contentPanel.form.findField("BUY_NAME").setValue('');
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").setValue('');
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
			} else if (cust_type == '1' && step_name == '6') { // TT+已核批动拨
				view.contentPanel.form.findField("IF_SAME").show();
				view.contentPanel.form.findField("IF_SAME").allowBlank = false;
				view.contentPanel.form.findField("IF_SAME").readOnly = true;
				view.contentPanel.form.findField("IF_SAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_MUNI").show();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = false;
				view.contentPanel.form.findField("IF_MUNI").readOnly = true;
				view.contentPanel.form.findField("IF_MUNI").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '送案金额';
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '送案币别';
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '送案日期';
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_AMT")
						.setReadOnly(true);
				view.contentPanel.form.findField("INSURE_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_DATE").show();
				view.contentPanel.form.findField("INSURE_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("FIRST_M_DATE").show();
				view.contentPanel.form.findField("FIRST_M_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("FIRST_M_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("FIRST_M_AMT").show();
				view.contentPanel.form.findField("FIRST_M_AMT").setReadOnly(
						true);
				view.contentPanel.form.findField("FIRST_M_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").show();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("SURPLUS_M_AMT").show();
				view.contentPanel.form.findField("SURPLUS_M_AMT").setReadOnly(
						true);
				view.contentPanel.form.findField("SURPLUS_M_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CASE_DAYS").show();
				view.contentPanel.form.findField("CASE_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("CASE_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("CUST_PROPERTIES").hide();
				view.contentPanel.form.findField("CUST_PROPERTIES")
						.setValue('');
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").hide();
				view.contentPanel.form.findField("PRODUCT_SUBJECT")
						.setValue('');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").hide();
				view.contentPanel.form.findField("PRODUCT_FORM").setValue('');
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").hide();
				view.contentPanel.form.findField("BUY_NAME").setValue('');
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("STEP_DAYS").hide();
				view.contentPanel.form.findField("STEP_DAYS").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").setValue('');
			} else if (cust_type == '2' && step_name == '2') { // CB+合作意向阶段
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CUST_PROPERTIES").show();
				view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = false;
				view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
				view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = false;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_FORM").show();
				view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("BUY_NAME").show();
				view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("VISIT_DATE").show();
				view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
				view.contentPanel.form.findField("VISIT_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '<font color=red>*</font>申请日期:';
				view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '<font color=red>*</font>申请金额(元):';
				view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '<font color=red>*</font>申请币别:';
				view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("IF_NEXT").show();
				view.contentPanel.form.findField("IF_NEXT").allowBlank = false;
				view.contentPanel.form.findField("IF_NEXT").readOnly = true;
				view.contentPanel.form.findField("IF_NEXT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("IF_SAME").hide();
				view.contentPanel.form.findField("IF_SAME").allowBlank = true;
				view.contentPanel.form.findField("IF_SAME").setValue('');
				view.contentPanel.form.findField("IF_MUNI").hide();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
				view.contentPanel.form.findField("IF_MUNI").setValue('');
				view.contentPanel.form.findField("CA_NUMBER").hide();
				view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
				view.contentPanel.form.findField("CA_NUMBER").setValue('');
				view.contentPanel.form.findField("CA_SP_STATE").hide();
				view.contentPanel.form.findField("CA_SP_STATE").allowBlank = true;
				view.contentPanel.form.findField("CA_SP_STATE").setValue('');
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY")
						.setValue('');
				view.contentPanel.form.findField("INSURE_DATE").hide();
				view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
				view.contentPanel.form.findField("INSURE_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
			} else if (cust_type == '2' && step_name == '3') { // CB+CA准备阶段
				view.contentPanel.form.findField("CUST_PROPERTIES").show();
				view.contentPanel.form.findField("CUST_PROPERTIES").allowBlank = false;
				view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
				view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").allowBlank = false;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").allowBlank = true;
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
				view.contentPanel.form.findField("PRODUCT_SUBJECT").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_FORM").show();
				view.contentPanel.form.findField("PRODUCT_FORM").allowBlank = true;
				view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_FORM").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("BUY_NAME").show();
				view.contentPanel.form.findField("BUY_NAME").allowBlank = true;
				view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
				view.contentPanel.form.findField("BUY_NAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("VISIT_DATE").show();
				view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
				view.contentPanel.form.findField("VISIT_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '<font color=red>*</font>申请日期:';
				view.contentPanel.form.findField("APPLY_DATE").allowBlank = false;
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
				view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '<font color=red>*</font>申请金额(元):';
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '<font color=red>*</font>申请币别:';
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("IF_SAME").hide();
				view.contentPanel.form.findField("IF_SAME").allowBlank = true;
				view.contentPanel.form.findField("IF_SAME").setValue('');
				view.contentPanel.form.findField("IF_MUNI").hide();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
				view.contentPanel.form.findField("IF_MUNI").setValue('');
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY")
						.setValue('');
				view.contentPanel.form.findField("INSURE_DATE").hide();
				view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
				view.contentPanel.form.findField("INSURE_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").setValue('');
			} else if (cust_type == '2' && step_name == '4') { // CB+信用审查阶段
				view.contentPanel.form.findField("CUST_PROPERTIES").show();
				view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
				view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
				view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_FORM").show();
				view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_FORM").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("BUY_NAME").show();
				view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
				view.contentPanel.form.findField("BUY_NAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("VISIT_DATE").show();
				view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
				view.contentPanel.form.findField("VISIT_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_DATE").show();
				view.contentPanel.form.findField("APPLY_DATE").label.dom.innerHTML = '申请日期';
				view.contentPanel.form.findField("APPLY_DATE")
						.setReadOnly(true);
				view.contentPanel.form.findField("APPLY_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_AMT").show();
				view.contentPanel.form.findField("APPLY_AMT").label.dom.innerHTML = '申请金额(元)';
				view.contentPanel.form.findField("APPLY_AMT").setReadOnly(true);
				view.contentPanel.form.findField("APPLY_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("APPLY_CURRENCY").show();
				view.contentPanel.form.findField("APPLY_CURRENCY").label.dom.innerHTML = '申请币别';
				view.contentPanel.form.findField("APPLY_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("IF_SAME").hide();
				view.contentPanel.form.findField("IF_SAME").allowBlank = true;
				view.contentPanel.form.findField("IF_SAME").setValue('');
				view.contentPanel.form.findField("IF_MUNI").hide();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
				view.contentPanel.form.findField("IF_MUNI").setValue('');
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY")
						.setValue('');
				view.contentPanel.form.findField("INSURE_DATE").hide();
				view.contentPanel.form.findField("INSURE_DATE").allowBlank = true;
				view.contentPanel.form.findField("INSURE_DATE").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").setValue('');
			} else if (cust_type == '2' && step_name == '5') { // CB+核批阶段
				view.contentPanel.form.findField("CUST_PROPERTIES").show();
				view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
				view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
				view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_FORM").show();
				view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_FORM").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("BUY_NAME").show();
				view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
				view.contentPanel.form.findField("BUY_NAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("VISIT_DATE").show();
				view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
				view.contentPanel.form.findField("VISIT_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_AMT")
						.setReadOnly(true);
				view.contentPanel.form.findField("INSURE_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_DATE").show();
				view.contentPanel.form.findField("INSURE_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("EXPECT_M_AMT").show();
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = false;
				view.contentPanel.form.findField("EXPECT_M_AMT").setReadOnly(
						true);
				view.contentPanel.form.findField("EXPECT_M_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("EXPECT_M_DATE").show();
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = false;
				view.contentPanel.form.findField("EXPECT_M_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("EXPECT_M_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("STEP_DAYS").show();
				view.contentPanel.form.findField("STEP_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("STEP_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("IF_SAME").hide();
				view.contentPanel.form.findField("IF_SAME").allowBlank = true;
				view.contentPanel.form.findField("IF_SAME").setValue('');
				view.contentPanel.form.findField("IF_MUNI").hide();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
				view.contentPanel.form.findField("IF_MUNI").setValue('');
				view.contentPanel.form.findField("APPLY_DATE").hide();
				view.contentPanel.form.findField("APPLY_DATE").allowBlank = true;
				view.contentPanel.form.findField("APPLY_DATE").setValue('');
				view.contentPanel.form.findField("APPLY_AMT").hide();
				view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
				view.contentPanel.form.findField("APPLY_AMT").setValue('');
				view.contentPanel.form.findField("APPLY_CURRENCY").hide();
				view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").setValue('');
				view.contentPanel.form.findField("FIRST_M_AMT").hide();
				view.contentPanel.form.findField("FIRST_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_AMT").setValue('');
				view.contentPanel.form.findField("FIRST_M_DATE").hide();
				view.contentPanel.form.findField("FIRST_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_DATE").setValue('');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").hide();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").setValue(
						'');
				view.contentPanel.form.findField("SURPLUS_M_AMT").hide();
				view.contentPanel.form.findField("SURPLUS_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("SURPLUS_M_AMT").setValue('');
				view.contentPanel.form.findField("CASE_DAYS").hide();
				view.contentPanel.form.findField("CASE_DAYS").allowBlank = true;
				view.contentPanel.form.findField("CASE_DAYS").setValue('');
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
				view.contentPanel.form.findField("IF_NEXT").setValue('');
			} else if (cust_type == '2' && step_name == '6') { // CB+已核批动拨
				view.contentPanel.form.findField("CUST_PROPERTIES").show();
				view.contentPanel.form.findField("CUST_PROPERTIES").readOnly = true;
				view.contentPanel.form.findField("CUST_PROPERTIES").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_NUMBER").show();
				view.contentPanel.form.findField("CA_NUMBER").setReadOnly(true);
				view.contentPanel.form.findField("CA_NUMBER").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CA_SP_STATE").show();
				view.contentPanel.form.findField("CA_SP_STATE").setReadOnly(
						true);
				view.contentPanel.form.findField("CA_SP_STATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_SUBJECT").show();
				view.contentPanel.form.findField("PRODUCT_SUBJECT").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_SUBJECT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("PRODUCT_FORM").show();
				view.contentPanel.form.findField("PRODUCT_FORM").readOnly = true;
				view.contentPanel.form.findField("PRODUCT_FORM").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("BUY_NAME").show();
				view.contentPanel.form.findField("BUY_NAME").setReadOnly(true);
				view.contentPanel.form.findField("BUY_NAME").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("NEW_HAS_CUST").show();
				view.contentPanel.form.findField("NEW_HAS_CUST").readOnly = true;
				view.contentPanel.form.findField("NEW_HAS_CUST").addClass(
						'x-readOnly');
				if (new_has_cust == '2') {
					view.contentPanel.form.findField("INSURE_MONEY").show();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
					view.contentPanel.form.findField("INSURE_MONEY")
							.setReadOnly(true);
					view.contentPanel.form.findField("INSURE_MONEY").addClass(
							'x-readOnly');
				} else {
					view.contentPanel.form.findField("INSURE_MONEY").hide();
					view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
					view.contentPanel.form.findField("INSURE_MONEY").setValue(
							'');
				}
				view.contentPanel.form.findField("VISIT_DATE").show();
				view.contentPanel.form.findField("VISIT_DATE").readOnly = true;
				view.contentPanel.form.findField("VISIT_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_AMT")
						.setReadOnly(true);
				view.contentPanel.form.findField("INSURE_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("INSURE_DATE").show();
				view.contentPanel.form.findField("INSURE_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("INSURE_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("FIRST_M_AMT").show();
				view.contentPanel.form.findField("FIRST_M_AMT").setReadOnly(
						true);
				view.contentPanel.form.findField("FIRST_M_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("FIRST_M_DATE").show();
				view.contentPanel.form.findField("FIRST_M_DATE").setReadOnly(
						true);
				view.contentPanel.form.findField("FIRST_M_DATE").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("FIRST_M_CURRENCY").show();
				view.contentPanel.form.findField("FIRST_M_CURRENCY").readOnly = true;
				view.contentPanel.form.findField("FIRST_M_CURRENCY").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("SURPLUS_M_AMT").show();
				view.contentPanel.form.findField("SURPLUS_M_AMT").setReadOnly(
						true);
				view.contentPanel.form.findField("SURPLUS_M_AMT").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("CASE_DAYS").show();
				view.contentPanel.form.findField("CASE_DAYS").setReadOnly(true);
				view.contentPanel.form.findField("CASE_DAYS").addClass(
						'x-readOnly');
				view.contentPanel.form.findField("MEMO").show();
				view.contentPanel.form.findField("MEMO").setReadOnly(true);
				view.contentPanel.form.findField("MEMO").addClass('x-readOnly');

				view.contentPanel.form.findField("IF_SAME").hide();
				view.contentPanel.form.findField("IF_SAME").allowBlank = true;
				view.contentPanel.form.findField("IF_SAME").setValue('');
				view.contentPanel.form.findField("IF_MUNI").hide();
				view.contentPanel.form.findField("IF_MUNI").allowBlank = true;
				view.contentPanel.form.findField("IF_MUNI").setValue('');
				view.contentPanel.form.findField("EXPECT_M_AMT").hide();
				view.contentPanel.form.findField("EXPECT_M_AMT").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_AMT").setValue('');
				view.contentPanel.form.findField("EXPECT_M_DATE").hide();
				view.contentPanel.form.findField("EXPECT_M_DATE").allowBlank = true;
				view.contentPanel.form.findField("EXPECT_M_DATE").setValue('');
				view.contentPanel.form.findField("APPLY_DATE").hide();
				view.contentPanel.form.findField("APPLY_DATE").allowBlank = true;
				view.contentPanel.form.findField("APPLY_DATE").setValue('');
				view.contentPanel.form.findField("APPLY_AMT").hide();
				view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
				view.contentPanel.form.findField("APPLY_AMT").setValue('');
				view.contentPanel.form.findField("APPLY_CURRENCY").hide();
				view.contentPanel.form.findField("APPLY_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("APPLY_CURRENCY").setValue('');
				view.contentPanel.form.findField("STEP_DAYS").hide();
				view.contentPanel.form.findField("STEP_DAYS").setValue('');
				view.contentPanel.form.findField("IF_NEXT").hide();
				view.contentPanel.form.findField("IF_NEXT").setValue('');
				view.contentPanel.form.findField("IF_NEXT").allowBlank = true;
				Ext.getCmp('synchnize').setVisible(true);
			}
			view.contentPanel.getForm().loadRecord(tempRecord);
		}

	}
}

//修改面板隐藏后触发
var viewhide = function(){
	if(if_reload){
		reloadCurrentData();
	}else{
		//do nothing
	}
}

// 查询结果每页默认显示20条
var beforeconditioninit = function(panel, app) {
	app.pageSize = 20;
};