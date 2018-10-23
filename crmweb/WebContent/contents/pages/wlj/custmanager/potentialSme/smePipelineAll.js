/**
 * @description 中小企客户营销流程 - Pipeline营销概览
 * @author denghj
 * @since 2015-09-07
 */
imports([ '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
		, '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js' // 用户放大镜
		,
		'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js' // 客户放大镜（企商金营销用）
		, '/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js',
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

var localLookup = {
	'CURRENCY' : [ {
		key : '10',
		value : 'RMB'
	}, {
		key : '13',
		value : 'USD'
	}, {
		key : '5',
		value : 'EUR'
	}, {
		key : '8',
		value : 'JPY'
	}, {
		key : '7',
		value : 'HKD'
	}, {
		key : '6',
		value : 'GBP'
	}, {
		key : '1',
		value : 'AUD'
	}, {
		key : '2',
		value : 'CAD'
	}, {
		key : '3',
		value : 'CHF'
	}, {
		key : '9',
		value : 'NZD'
	}, {
		key : '11',
		value : 'SGD'
	}, {
		key : '12',
		value : 'TWD'
	} ],
	'SUC_PROBABILITY' : [ {
		key : '1',
		value : '无'
	}, {
		key : '2',
		value : '10'
	}, {
		key : '3',
		value : '20'
	}, {
		key : '4',
		value : '30'
	}, {
		key : '5',
		value : '40'
	}, {
		key : '6',
		value : '50'
	}, {
		key : '7',
		value : '60'
	}, {
		key : '8',
		value : '70'
	}, {
		key : '9',
		value : '80'
	}, {
		key : '10',
		value : '90'
	}, {
		key : '11',
		value : '100'
	} ]
};

var url = basepath + '/smePipelineAll.json';

var lookupTypes = [ 'CASE_TYPE_SME', 'PIPELINE_STEP', 'CUST_SOURCE_SME',
		'IF_FLAG', 'IF_PIPELINE', 'IF_FLAG_HZ', 'CUST_TYPE_SME',
		'HARD_INFO_INTENT_SME', {
			type : 'AREA',// 区域中心数据字典
			url : '/smeProspectE!searchArea.json',
			key : 'KEY',
			value : 'VALUE',
			root : 'data'
		} ];

var CUST_ID = '';// 定义全局变量，为CallReport放大镜 传递参数
var ifPipeline = 0;// 此标志用于判断是否需要把信息录入pipeline;值为1则需要
var fields = [ {
	name : 'STEP',
	text : '所属阶段',
	hiddenName : 'HIDE_STEP',
	translateType : 'PIPELINE_STEP',
	searchField : true
//	comFn : function(data) {
//		return "STEP";
//	}
}, {
	name : 'HIDE_STEP',
	searchField : true,
	hidden : true
}, {
	name : 'CUST_NAME',
	text : '客户名称',
	hiddenName : 'CUST_ID',
	searchField : true,
	resutlWidth : 150
}, {
	name : 'DEPT_NAME',
	text : '营业部门',
	hiddenName : 'DEPT_ID',
	searchField : false
}, {
	name : 'AREA_NAME',
	text : '区域中心',
	translateType : 'AREA',
	resutlWidth : 80,
	searchField : true,
	showField : 'text',
	valueField : 'value',
	listeners : {
		select : function(a, b) {
			a.setValue(b.data.value);
		}
	}
}, {
	name : 'RM',
	text : 'RM',
	dataType : 'string',
	gridField : true
}, {
	name : 'CASE_TYPE',
	text : '案件类型',
	translateType : 'CASE_TYPE_SME',
	searchField : true,
	editable : true,
	resizable : true
}, {
	name : 'STATE',
	text : '案件状态',
	searchField : true
}, {
	name : 'APPLY_AMT',
	text : '申请额度(折人民币/千元)',
	dataType : 'money',
	maxLength : 20
}, {
	name : 'INSURE_AMT',
	text : '核准金额(折人民币/千元)',
	dataType : 'money',
	maxLength : 24
}, {
	name : 'DB_AMTS',
	text : '已动拨金额(折人民币/千元)',
	dataType : 'money',
	maxLength : 24
}, {
	name : 'VISIT_COUNT',
	text : '拜访次数',
	searchField : false
}, {
	name : 'TREAMENT_DAYS',
	text : '本阶段案件处理天数',
	searchField : false
}, {
	name : 'TREAMENT_ALLDAYS',
	text : '案件总计天数',
	searchField : false
}, {
	name : 'AREA_ID'
}, {
	name : 'DEPT_ID'
}, {
	name : 'RM_ID'
}, {
	name : 'CUST_ID'
}, {
	name : 'ID'
}, {
	name : 'PIPELINE_ID'
} ];

var tbar = [ new Com.yucheng.crm.common.NewExpButton({// 导出按钮
	formPanel : 'searchCondition',
	url : basepath + '/smePipelineAll.json'
}), {
	text : '进入相应阶段',
	hidden : JsContext.checkGrant('smePipelineAllEnter'),//
	handler : function() {
		if (getSelectedData() == false) {
			Ext.Msg.alert("提示", "请选择一条数据");
			return false;
		}
		selectParam(getSelectedData().data.HIDE_STEP,getSelectedData().data.PIPELINE_ID); // 页面跳转
	}
} ];

/**
 * 创建侧滑界面，添加流程按钮
 */
var edgeVies = {
	left : {
		width : 150,
		layout : 'fit',
		title : 'Pipeline流程',
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
						text : 'prospect信息',
						hidden : JsContext.checkGrant('smePipelineButton1'),
						flex : 1,
						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('1');
							window._app.searchDomain.searchPanel.getForm()
									.findField("STEP").setValue('1');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '合作意向',
						hidden : JsContext.checkGrant('smePipelineButton2'),
						flex : 1,
						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('2');
							window._app.searchDomain.searchPanel.getForm()
									.findField("STEP").setValue('2');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '文件收集及CA准备',
						hidden : JsContext.checkGrant('smePipelineButton3'),
						flex : 1,
						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('3');
							window._app.searchDomain.searchPanel.getForm()
									.findField("STEP").setValue('3');
							window._app.searchDomain.searchButton.handler();
						}
					},
//					{
//						xtype : 'button',
//						text : '文件及CA准备',
//						 hidden : JsContext.checkGrant('smePipelineButton4'),
//						flex : 1,
//						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('4');
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("STEP").setValue('3');
//							window._app.searchDomain.searchButton.handler();
//						}
//					},
					{
						xtype : 'button',
						text : '信用审查',
						hidden : JsContext.checkGrant('smePipelineButton4'),
						flex : 1,
						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('5');
							window._app.searchDomain.searchPanel.getForm()
									.findField("STEP").setValue('4');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '核批阶段',
						hidden : JsContext.checkGrant('smePipelineButton5'),
						flex : 1,
						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('6');
							window._app.searchDomain.searchPanel.getForm()
									.findField("STEP").setValue('5');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '已核批动拨',
						hidden : JsContext.checkGrant('smePipelineButton6'),
						flex : 1,
						handler : function() {
//							window._app.searchDomain.searchPanel.getForm()
//									.findField("HIDE_STEP").setValue('7');
							window._app.searchDomain.searchPanel.getForm()
									.findField("STEP").setValue('6');
							window._app.searchDomain.searchButton.handler();
						}
					},
					{
						xtype : 'button',
						text : '否决及退案汇总',
						hidden : JsContext.checkGrant('smePipelineButton7'),
						flex : 1,
						handler : function() {
							showCustomerViewByTitle("否决及退案汇总");
						}
					},{
						xtype : 'button',
						text : '查看历史移转',
//						hidden : JsContext.checkGrant('smePipelineButton8'),
						flex : 1,
						handler : function() {
							showCustomerViewByTitle("查看历史移转");
						}
					}]
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
	},{
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
		name : 'ID'
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
		header : '申请额度(折人民币/千元)'
	}, {
		name : 'INSURE_AMT',
		header : '核准金额(折人民币/千元)'
	}, {
		name : 'DB_AMTS',
		header : '已动拨金额(折人民币/千元)'
	}, {
		name : 'VISIT_COUNT',
		header : '拜访次数'
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
	},{
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
		renderer:function(value){
			var val = translateLookupByKey("PIPELINE_STEP",value);
			return val?val:"";
		}
	}, {
		name : 'BEFORE_STATE',
		header : '移转前状态'
	}, {
		name : 'AFTER_STEP',
		header : '移转后所属阶段',
		renderer:function(value){
			var val = translateLookupByKey("PIPELINE_STEP",value);
			return val?val:"";
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
			title : '新增prospect',
			hideTitle : JsContext.checkGrant('smePipelineAllAddProspect'),
			type : 'form',
			groups : [
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [
								{
									name : 'CUST_NAME',
									text : '客户名称',
									xtype : 'customerqueryqz',
									hiddenName : 'CUST_ID',
									allowBlank : false,
									custType : '1',
									singleSelected : true,
									newCust : true,
									callback : function(a) {
										CUST_ID = a.customerId;
										var CUST_NAME = getCurrentView().contentPanel.form
												.findField("CUST_NAME")
												.getValue();
										var VISIT_DATE = getCurrentView().contentPanel.form
												.findField("VISIT_DATE")
												.getValue();
										var CUST_SOURCE = getCurrentView().contentPanel.form
												.findField("CUST_SOURCE")
												.getValue();
										if (CUST_NAME != ''
												&& (VISIT_DATE != '' || CUST_SOURCE != '')) {
											getCurrentView().contentPanel.form
													.findField("VISIT_DATE")
													.setValue('');
											getCurrentView().contentPanel.form
													.findField("CUST_SOURCE")
													.setValue('');
										}
									}
								},
								{
									name : 'AREA_NAME',
									text : '区域中心',
									resutlWidth : 80,
									searchField : true,
									showField : 'text',
									allowBlank : false,
									valueField : 'value',
									listeners : {
										select : function(a, b) {
											a.setValue(b.data.value);
										}
									}
								},
								{
									name : 'DEPT_NAME',
									text : '营业部门',
									// xtype : 'orgchoose',
									hiddenName : 'DEPT_ID',
									allowBlank : false
								},
								{
									name : 'RM',
									text : 'RM',
									// xtype : 'userchoose',
									hiddenName : 'RM_ID',
									resutlWidth : 150,
									allowBlank : false
								},
								{
									name : 'AREA_ID'
								},
								{
									name : 'RM_ID'
								},
								{
									name : 'CUST_ID'
								},
								{
									name : 'DEPT_ID'
								},
								{
									name : 'CUST_SOURCE',
									text : '客户来源',
									translateType : 'CUST_SOURCE_SME',
									allowBlank : false,
									searchField : true,
									editable : true
								},
								{
									name : 'CUST_SOURCE_DATE',
									text : '客户来源日期',
									dataType : 'date',
									allowBlank : false
								},
								{
									name : 'VISIT_DATE',
									text : '拜访日期',
									xtype : 'callreportquerysme',
									hiddenName : 'CUST_ID',
									allowBlank : false,
									singleSelected : true,
									listeners : {
										focus : function() {
											if (CUST_ID == '') {
												Ext.Msg.alert("提示", "请先选择客户！");
												return false;
											}
										}
									},
									callback : function(a) {
										getCurrentView().contentPanel.form
												.findField("CUST_SOURCE")
												.setValue(a.resCustSource);
									}
								},
								{
									name : 'IF_FILES',
									text : '是否能取得财务资料',
									dataType : 'string',
									translateType : 'IF_FLAG',
									allowBlank : false,
									gridField : true,
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("IF_FILES").value;
											if (flag == '0') {
												getCurrentView().contentPanel.form
														.findField(
																"IF_TRANS_CUST")
														.show();
												getCurrentView().contentPanel.form
														.findField("IF_TRANS_CUST").allowBlank = false;
											} else {
												getCurrentView().contentPanel.form
														.findField(
																"IF_TRANS_CUST")
														.hide();
												getCurrentView().contentPanel.form
														.findField("IF_TRANS_CUST").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"IF_TRANS_CUST")
														.setValue('0');
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE")
														.hide();
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE")
														.setValue('');
											}
										}
									}
								},
								{
									name : 'IF_TRANS_CUST',
									text : '若无法取得财务资料,能否转为我行存款户',
									translateType : 'IF_FLAG',
									dataType : 'string',
									gridField : true,
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("IF_TRANS_CUST").value;
											if (flag == '1') {
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE")
														.show();
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE").allowBlank = false;
											} else {
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE")
														.hide();
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE")
														.setValue('');
											}
										}
									}
								},
								{
									name : 'TRANS_DATE',
									text : '转为存款户日期',
									dataType : 'date',
									allowBlank : false,
									gridField : true
								},
								{
									name : 'IF_PIPELINE',
									text : '是否转入PIPELINE',
									translateType : 'IF_PIPELINE',
									allowBlank : false,
									dataType : 'string',
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("IF_PIPELINE").value;
											if (flag == '0') {
												getCurrentView().contentPanel.form
														.findField("CUST_SOURCE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("CUST_SOURCE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("VISIT_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("IF_TRANS_CUST").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE").allowBlank = true;

												getCurrentView().contentPanel.form
														.findField(
																"PIPELINE_DATE")
														.hide();
												getCurrentView().contentPanel.form
														.findField("PIPELINE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"PIPELINE_DATE")
														.setValue('');

												getCurrentView().contentPanel.form
														.findField(
																"RUFUSE_REASON")
														.show();
												getCurrentView().contentPanel.form
														.findField("RUFUSE_REASON").allowBlank = false;
												if (getCurrentView().contentPanel.form
														.findField("RUFUSE_REASON").getValue == '3') {
													getCurrentView().contentPanel.form
															.findField(
																	"REASON_REMARK")
															.show();
													getCurrentView().contentPanel.form
															.findField("REASON_REMARK").allowBlank = false;
												} else {
													getCurrentView().contentPanel.form
															.findField(
																	"REASON_REMARK")
															.hide();
													getCurrentView().contentPanel.form
															.findField("REASON_REMARK").allowBlank = true;
													getCurrentView().contentPanel.form
															.findField(
																	"REASON_REMARK")
															.setValue('');
												}
											} else if (flag == '1') {
												getCurrentView().contentPanel.form
														.findField("CUST_SOURCE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CUST_SOURCE_DATE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("VISIT_DATE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField(
																"PIPELINE_DATE")
														.show();
												getCurrentView().contentPanel.form
														.findField("PIPELINE_DATE").allowBlank = false;

												getCurrentView().contentPanel.form
														.findField(
																"RUFUSE_REASON")
														.hide();
												getCurrentView().contentPanel.form
														.findField("RUFUSE_REASON").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"RUFUSE_REASON")
														.setValue('');

												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.hide();
												getCurrentView().contentPanel.form
														.findField("REASON_REMARK").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.setValue('');
											} else {
												getCurrentView().contentPanel.form
														.findField("CUST_SOURCE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("CUST_SOURCE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("VISIT_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("IF_TRANS_CUST").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("TRANS_DATE").allowBlank = true;

												getCurrentView().contentPanel.form
														.findField(
																"PIPELINE_DATE")
														.hide();
												getCurrentView().contentPanel.form
														.findField("PIPELINE_DATE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"PIPELINE_DATE")
														.setValue('');

												getCurrentView().contentPanel.form
														.findField(
																"RUFUSE_REASON")
														.hide();
												getCurrentView().contentPanel.form
														.findField("RUFUSE_REASON").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"RUFUSE_REASON")
														.setValue('');

												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.hide();
												getCurrentView().contentPanel.form
														.findField("REASON_REMARK").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.setValue('');
											}
										}
									}
								},
								{
									name : 'PIPELINE_DATE',
									text : '转为PIPELINE日期',
									dataType : 'date',
									allowBlank : false,
									gridField : true
								},
								{
									name : 'RUFUSE_REASON',
									text : '拒绝理由',
									xtype : 'string',
									translateType : 'RUFUSE_REASON_PROSPECT_SME',
									allowBlank : false,
									gridField : true,
									editable : true,
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("RUFUSE_REASON").value;
											if (flag == '3') {
												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.show();
												getCurrentView().contentPanel.form
														.findField("REASON_REMARK").allowBlank = false;
											} else {
												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.hide();
												getCurrentView().contentPanel.form
														.findField("REASON_REMARK").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"REASON_REMARK")
														.setValue('');
											}
										}
									}
								}, {
									name : 'ID'
								}

						],
						fn : function(CUST_NAME, AREA_NAME, DEPT_NAME, RM,
								AREA_ID, RM_ID, CUST_ID, DEPT_ID, CUST_SOURCE,
								CUST_SOURCE_DATE, VISIT_DATE, IF_FILES,
								IF_TRANS_CUST, TRANS_DATE, IF_PIPELINE,
								PIPELINE_DATE, RUFUSE_REASON, ID) {

							AREA_NAME.readOnly = true;
							AREA_NAME.cls = 'x-readOnly';
							DEPT_NAME.readOnly = true;
							DEPT_NAME.cls = 'x-readOnly';
							RM.readOnly = true;
							RM.cls = 'x-readOnly';
							CUST_ID.hidden = true;
							AREA_ID.hidden = true;
							DEPT_ID.hidden = true;
							RM_ID.hidden = true;
							ID.hidden = true;
							RUFUSE_REASON.hidden = true;

							return [ CUST_NAME, AREA_NAME, DEPT_NAME, RM,
									AREA_ID, RM_ID, CUST_ID, DEPT_ID,
									CUST_SOURCE, CUST_SOURCE_DATE, VISIT_DATE,
									IF_FILES, IF_TRANS_CUST, TRANS_DATE,
									IF_PIPELINE, PIPELINE_DATE, RUFUSE_REASON,
									ID ];
						}
					},
					{
						columnCount : 1,
						fields : [ {
							name : 'CUST_SOURCE_INFO',
							text : '客户来源说明',
							xtype : 'textarea',
							maxLength : 200
						}, {
							name : 'PRODUCT_NEED',
							text : '客户产品需求',
							xtype : 'textarea',
							maxLength : 200
						}, {
							name : 'REASON_REMARK',
							text : '<font color="red">*</font>拒绝原因说明',
							xtype : 'textarea',
							maxLength : 500
						} ],
						fn : function(CUST_SOURCE_INFO, PRODUCT_NEED,
								REASON_REMARK) {
							REASON_REMARK.hidden = true;
							return [ CUST_SOURCE_INFO, PRODUCT_NEED,
									REASON_REMARK ];
						}
					} ],
			formButtons : [ {
				text : '提交',
				fn : function(formPanel, basicForm) {
					if (!formPanel.getForm().isValid()) {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
					;
					var value = formPanel.form.findField("IF_PIPELINE")
							.getValue();
					if (value == '0') {
						if (formPanel.form.findField("RUFUSE_REASON")
								.getValue() == '') {
							Ext.MessageBox.alert('提示', '请填写[拒绝理由]');
							return false;
						}
					}
					if (value == '1') {
						if (formPanel.form.findField("IF_FILES").getValue() == '0') {
							Ext.MessageBox.alert('提示', '未取得财务资料，不能进入下一阶段');
							return false;
						}
					}
					var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data, 1);
					ifPipeline = commintData.ifPipeline;
					Ext.Msg.wait('正在处理，请稍后......', '系统提示');
					Ext.Ajax.request({
						url : basepath + '/smeProspectE!save.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							if (ifPipeline == '1') {
								var ret = Ext.decode(response.responseText);
								Ext.Ajax.request({
									url : basepath + '/smeIntentE!save.json',// 如果要转入pipeline阶段，把数据保存进合作意向表内
									method : 'GET',
									params : ret,
									success : function(response) {
										// var ret =
										// Ext.decode(response.responseText);//取得数据
										Ext.MessageBox.alert('提示',
												'保存数据成功,请在合作意向阶段查看数据!');
										hideCurrentView();
										reloadCurrentData();
									}
								});
							} else {
								Ext.MessageBox.alert('提示', '保存数据成功!');
								hideCurrentView();
								reloadCurrentData();
							}
						}
					});
				}
			} ]
		},
		{
			title : '新增合作意向',
			hideTitle : JsContext.checkGrant('smePipelineAllAddIntent'),
			type : 'form',
			groups : [
					{
						labelWidth : 150,
						columnCount : 2,
						fields : [
								{
									name : 'CUST_NAME',
									text : '客户名称',
									xtype : 'customerqueryqz',
									allowBlank : false,
									hiddenName : 'CUST_ID',
									resutlWidth : 150,
									singleSelect : false,
									custType : '1',
									searchField : true,
									isNew : true
								},
								{
									name : 'AREA_NAME',
									text : '区域中心',
									resutlWidth : 80,
									showField : 'text',
									allowBlank : false,
									valueField : "value",
									listeners : {
										select : function(a, b) {
											a.setValue(b.data.value);
										}
									}
								},
								{
									name : 'DEPT_NAME',
									text : '营业部门',
									// xtype : 'orgchoose',
									hiddenName : 'DEPT_ID',
									allowBlank : false

								},
								{
									name : 'RM',
									text : 'RM',
									// xtype : 'userchoose',
									hiddenName : 'RM_ID',
									resutlWidth : 150,
									allowBlank : false
								},
								{
									name : 'FOREIGN_MONEY',
									text : '申请额度(原币金额/千元)',
									gridField : true,
									dataType : 'decimal',
									viewFn : money('0,000.00'),
									minValue : 0,
									maxLength : 24,
									allowBlank : false
								},
								{
									name : 'CASE_TYPE',
									text : '案件类型',
									translateType : 'CASE_TYPE_SME',
									searchField : true,
									editable : true,
									resizable : true,
									allowBlank : false
								},
								{
									name : 'CURRENCY',
									text : '申请额度币别',
									gridField : true,
									translateType : 'CURRENCY',
									allowBlank : false,
									listeners : {
										select : function() {
											var flag = this.value;
											var FOREIGN_MONEY = getCurrentView().contentPanel.form
													.findField("FOREIGN_MONEY")
													.getValue();
											if (flag == '13') {
												getCurrentView().contentPanel.form
														.findField("APPLY_AMT")
														.setValue(
																FOREIGN_MONEY * 6);
											} else if (flag == '10') {
												getCurrentView().contentPanel.form
														.findField("APPLY_AMT")
														.setValue(FOREIGN_MONEY);
											} else {
												getCurrentView().contentPanel.form
														.findField("APPLY_AMT")
														.setValue('');
											}
										}
									}
								},
								{
									name : 'CUST_TYPE',
									text : '客户类型',
									translateType : 'CUST_TYPE_SME',
									searchField : true,
									editable : true,
									resizable : true,
									allowBlank : false,
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("CUST_TYPE").value;
											if (flag == '2') {
												getCurrentView().contentPanel.form
														.findField("IF_ADD")
														.show();
											} else {
												getCurrentView().contentPanel.form
														.findField("IF_ADD")
														.hide();
												getCurrentView().contentPanel.form
														.findField("IF_ADD").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("IF_ADD")
														.setValue('');

												getCurrentView().contentPanel.form
														.findField("ADD_AMT")
														.hide();
												getCurrentView().contentPanel.form
														.findField("ADD_AMT").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("ADD_AMT")
														.setValue('');
											}
										}
									}
								},
								{
									name : 'APPLY_AMT',
									text : '申请额度(折人民币/千元)',
									dataType : 'money',
									maxLength : 20,
									allowBlank : false
								},
								{
									name : 'IF_ADD',
									text : '若为旧案是否为增贷',
									gridField : true,
									translateType : 'IF_FLAG',
									allowBlank : false,
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("IF_ADD").value;
											if (flag == '1') {
												getCurrentView().contentPanel.form
														.findField("ADD_AMT")
														.show();
											} else {
												getCurrentView().contentPanel.form
														.findField("ADD_AMT")
														.hide();
												getCurrentView().contentPanel.form
														.findField("ADD_AMT").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("ADD_AMT")
														.setValue('');
											}
										}
									}
								},
								{
									name : 'SUC_PROBABILITY',
									text : '预计可送审概率(%)',
									translateType : 'SUC_PROBABILITY',
									allowBlank : false,
									searchField : true,
									editable : true
								},
								{
									name : 'ADD_AMT',
									text : '增贷金额(折人民币/千元)',
									gridField : true,
									dataType : 'money',
									allowBlank : false,
									maxLength : 24,
									minValue : 0
								},
								{
									name : 'IF_SECOND_STEP',
									text : '是否进入第二阶段',
									translateType : 'IF_FLAG_HZ',
									allowBlank : false,
									searchField : true,
									listeners : {
										select : function() {
											var flag = getCurrentView().contentPanel.form
													.findField("IF_SECOND_STEP").value;
											if (flag == '0') {// 否
												getCurrentView().contentPanel.form
														.findField(
																"CP_HARD_INFO")
														.show();
												getCurrentView().contentPanel.form
														.findField("CP_HARD_INFO").allowBlank = false;

												getCurrentView().contentPanel.form
														.findField("FOREIGN_MONEY").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("CURRENCY").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("APPLY_AMT").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("CUST_TYPE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("CASE_TYPE").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("SUC_PROBABILITY").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("IF_ADD").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField("ADD_AMT").allowBlank = true;
											} else if (flag == '1') {// 是
												getCurrentView().contentPanel.form
														.findField(
																"CP_HARD_INFO")
														.hide();
												getCurrentView().contentPanel.form
														.findField("CP_HARD_INFO").allowBlank = true;
												getCurrentView().contentPanel.form
														.findField(
																"CP_HARD_INFO")
														.setValue('');

												getCurrentView().contentPanel.form
														.findField("FOREIGN_MONEY").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CURRENCY").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("APPLY_AMT").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CUST_TYPE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CASE_TYPE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("SUC_PROBABILITY").allowBlank = false;

												if (getCurrentView().contentPanel.form
														.findField("CUST_TYPE").value == '2') {
													getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.show();
													getCurrentView().contentPanel.form
															.findField("IF_ADD").allowBlank = false;
													if (getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.getValue() == '1') {
														getCurrentView().contentPanel.form
																.findField(
																		"ADD_AMT")
																.show();
														getCurrentView().contentPanel.form
																.findField("ADD_AMT").allowBlank = false;
													} else {
														getCurrentView().contentPanel.form
																.findField(
																		"ADD_AMT")
																.hide();
														getCurrentView().contentPanel.form
																.findField("ADD_AMT").allowBlank = true;
														getCurrentView().contentPanel.form
																.findField(
																		"ADD_AMT")
																.setValue('');
													}
												} else {
													getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.hide();
													getCurrentView().contentPanel.form
															.findField("IF_ADD").allowBlank = true;
													getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.setValue('');

													getCurrentView().contentPanel.form
															.findField(
																	"ADD_AMT")
															.hide();
													getCurrentView().contentPanel.form
															.findField("ADD_AMT").allowBlank = true;
													getCurrentView().contentPanel.form
															.findField(
																	"ADD_AMT")
															.setValue('');
												}

											} else {// 暂时维持本阶段
												getCurrentView().contentPanel.form
														.findField(
																"CP_HARD_INFO")
														.show();
												getCurrentView().contentPanel.form
														.findField("CP_HARD_INFO").allowBlank = false;

												getCurrentView().contentPanel.form
														.findField("FOREIGN_MONEY").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CURRENCY").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("APPLY_AMT").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CUST_TYPE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("CASE_TYPE").allowBlank = false;
												getCurrentView().contentPanel.form
														.findField("SUC_PROBABILITY").allowBlank = false;

												if (getCurrentView().contentPanel.form
														.findField("CUST_TYPE").value == '2') {
													getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.show();
													getCurrentView().contentPanel.form
															.findField("IF_ADD").allowBlank = false;
													if (getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.getValue() == '1') {
														getCurrentView().contentPanel.form
																.findField(
																		"ADD_AMT")
																.show();
														getCurrentView().contentPanel.form
																.findField("ADD_AMT").allowBlank = false;
													} else {
														getCurrentView().contentPanel.form
																.findField(
																		"ADD_AMT")
																.hide();
														getCurrentView().contentPanel.form
																.findField("ADD_AMT").allowBlank = true;
														getCurrentView().contentPanel.form
																.findField(
																		"ADD_AMT")
																.setValue('');
													}
												} else {
													getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.hide();
													getCurrentView().contentPanel.form
															.findField("IF_ADD").allowBlank = true;
													getCurrentView().contentPanel.form
															.findField("IF_ADD")
															.setValue('');

													getCurrentView().contentPanel.form
															.findField(
																	"ADD_AMT")
															.hide();
													getCurrentView().contentPanel.form
															.findField("ADD_AMT").allowBlank = true;
													getCurrentView().contentPanel.form
															.findField(
																	"ADD_AMT")
															.setValue('');
												}
											}
										}
									}
								}, {
									name : 'CP_HARD_INFO',
									text : '营销难点',
									translateType : 'HARD_INFO_INTENT_SME',
									allowBlank : false,
									searchField : true,
									editable : true
								}, {
									name : 'CUST_ID',
									text : '客户编号',
									gridField : false
								}, {
									name : 'RM_ID',
									text : 'RM编号',
									gridField : false
								}, {
									name : 'AREA_ID',
									text : '区域编号',
									gridField : false
								}, {
									name : 'DEPT_ID',
									text : 'DEPT_ID',
									gridField : false
								}, {
									name : 'ID',
									text : 'ID',
									gridField : false
								}, {
									name : 'PROSPECT_ID',
									text : 'PROSPECT_ID',
									gridField : false
								}, {
									name : 'PIPELINE_ID',
									text : 'PIPELINE_ID',
									gridField : false
								}, {
									name : 'RECORD_DATE',
									text : 'RECORD_DATE',
									gridField : false
								} ],
						fn : function(CUST_NAME, AREA_NAME, DEPT_NAME, RM,
								FOREIGN_MONEY, CASE_TYPE, CURRENCY, CUST_TYPE,
								APPLY_AMT, IF_ADD, SUC_PROBABILITY, ADD_AMT,
								IF_SECOND_STEP, CP_HARD_INFO, CUST_ID, RM_ID,
								AREA_ID, DEPT_ID, ID, PROSPECT_ID, PIPELINE_ID,
								RECORD_DATE) {
							AREA_NAME.readOnly = true;
							AREA_NAME.cls = 'x-readOnly';
							DEPT_NAME.readOnly = true;
							DEPT_NAME.cls = 'x-readOnly';
							RM.readOnly = true;
							RM.cls = 'x-readOnly';

							IF_ADD.hidden = true;
							ADD_AMT.hidden = true;
							// CP_HARD_INFO.hidden = true;

							CUST_ID.hidden = true;
							RM_ID.hidden = true;
							AREA_ID.hidden = true;
							DEPT_ID.hidden = true;
							ID.hidden = true;
							PROSPECT_ID.hidden = true;
							PIPELINE_ID.hidden = true;
							RECORD_DATE.hidden = true;

							return [ CUST_NAME, AREA_NAME, DEPT_NAME, RM,
									FOREIGN_MONEY, CASE_TYPE, CURRENCY,
									CUST_TYPE, APPLY_AMT, IF_ADD,
									SUC_PROBABILITY, ADD_AMT, IF_SECOND_STEP,
									CP_HARD_INFO, CUST_ID, RM_ID, AREA_ID,
									DEPT_ID, ID, PROSPECT_ID, PIPELINE_ID,
									RECORD_DATE ];
						}
					}, {
						columnCount : 1,
						fields : [ {
							name : 'REMARK',
							text : '备注',
							xtype : 'textarea',
							maxLength : 400
						} ],
						fn : function(REMARK) {
							REMARK.hidden = false;
							return [ REMARK ];
						}
					} ],
			formButtons : [ {
				text : '提交',
				fn : function(formPanel, basicForm) {
					if (!formPanel.getForm().isValid()) {
						Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
						return false;
					}
					;
					var data = formPanel.getForm().getFieldValues();
					if (data.IF_SECOND_STEP == '0') {
						if (formPanel.form.findField("CP_HARD_INFO").getValue() == '') {
							Ext.MessageBox.alert('提示', '请填写[营销难点]');
							return false;
						}
					}

					if (formPanel.getForm().findField("IF_ADD").getValue() == '1') {
						if (formPanel.getForm().findField("ADD_AMT").getValue() == '') {
							Ext.MessageBox.alert('提示', '请填写增贷金额');
							return false;
						}
					}

					// 申请额度（折人民币/千元）’与‘申请额度（原币金额/千元）’如果‘申请额度币别’是RMB那么两个额度应该相同
					var CURRENCY = formPanel.form.findField("CURRENCY")
							.getValue();
					var APPLY_AMT = formPanel.form.findField("APPLY_AMT")
							.getValue();
					var FOREIGN_MONEY = formPanel.form.findField(
							"FOREIGN_MONEY").getValue();
					if (CURRENCY == 'RMB') {
						if (APPLY_AMT != FOREIGN_MONEY) {
							Ext.MessageBox
									.alert('提示',
											'申请额度币别为RMB，申请额度（折人民币/千元）应与申请额度（原币金额/千元）相等');
							return false;
						}
					}
					if (((data.APPLY_AMT == 0 || data.APPLY_AMT == '')
							&& (data.FOREIGN_MONEY != 0 || data.FOREIGN_MONEY != '') && data.IF_SECOND_STEP == '1')
							|| ((data.APPLY_AMT != 0 || data.APPLY_AMT != '')
									&& (data.FOREIGN_MONEY == 0 || data.FOREIGN_MONEY == '') && data.IF_SECOND_STEP == '1')) {
						Ext.MessageBox.alert('提示',
								'申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
						return false;
					}
					var commintData = translateDataKey(data, 1);
					// var gradePersect=commintData.gradePersect;
					Ext.Msg.wait('正在处理，请稍后......', '系统提示');
					Ext.Ajax.request({
						url : basepath + '/smeIntentE!save.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							if (data.IF_SECOND_STEP == '1') {// 需要在下一阶段进行数据存储
								var ret = Ext.decode(response.responseText);
								Ext.Ajax.request({
									url : basepath + '/smeCaE!save.json',// 把数据转入文件收集阶段，把数据保存进合作意向表内
									method : 'GET',
									params : ret,
									success : function(response) {
										Ext.MessageBox.alert('提示',
												'保存数据成功,请在文件收集阶段查看数据!');
									}
								});
							} else {
								Ext.MessageBox.alert('提示', '保存数据成功!');
							}
							hideCurrentView();
							reloadCurrentData();
						}
					});

				}
			} ]
		}, {
			title : '否决及退案汇总',
			hideTitle : JsContext.checkGrant('smePipelineAllRefused'),
			items : [ grid ]

		},{
			title : '查看历史移转',
//			hideTitle : JsContext.checkGrant('smePipelineAllHis'),
			items : [ gridHis ]
		} ];

var viewshow = function(view) {
	if (view._defaultTitle == '新增prospect') {
		// 进行返现RM，营业单位
		view.contentPanel.form.findField("RM_ID").setValue(__userId);
		view.contentPanel.form.findField("RM").setValue(__userName);
		view.contentPanel.form.findField("DEPT_NAME").setValue(__unitname);
		view.contentPanel.form.findField("DEPT_ID").setValue(__units);

		// 通过营业单位查询出区域中心并返现
		Ext.Ajax.request({
			url : basepath + '/smeProspectE!searchAreaBack.json',
			method : 'GET',
			params : {
				deptId : __units
			},
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				view.contentPanel.form.findField("AREA_ID").setValue(
						ret.data[0].AREA_ID);
				view.contentPanel.form.findField("AREA_NAME").setValue(
						ret.data[0].AREA_NAME);
			}
		});

		view.contentPanel.form.findField("IF_PIPELINE").setValue('2');// 是否转入PEPILINE默认为暂时维持本阶段
		view.contentPanel.form.findField("IF_FILES").setValue('1'); // 是否能取得财务资料默认为是
		view.contentPanel.form.findField("PIPELINE_DATE").hide(); // 隐藏转为pipeline日期
		view.contentPanel.form.findField("TRANS_DATE").hide();
		view.contentPanel.form.findField("IF_TRANS_CUST").hide();
	}

	if (view._defaultTitle == '新增合作意向') {
		// 进行返现RM，营业单位
		view.contentPanel.form.findField("RM_ID").setValue(__userId);
		view.contentPanel.form.findField("RM").setValue(__userName);
		view.contentPanel.form.findField("DEPT_NAME").setValue(__unitname);
		view.contentPanel.form.findField("DEPT_ID").setValue(__units);

		// 通过营业单位查询出区域中心并返现
		Ext.Ajax.request({
			url : basepath + '/smeProspectE!searchAreaBack.json',
			method : 'GET',
			params : {
				deptId : __units
			},
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				view.contentPanel.form.findField("AREA_ID").setValue(
						ret.data[0].AREA_ID);
				view.contentPanel.form.findField("AREA_NAME").setValue(
						ret.data[0].AREA_NAME);
			}
		});

		view.contentPanel.form.findField("IF_SECOND_STEP").setValue('2');
		view.contentPanel.form.findField("CASE_TYPE").setValue('16');
		view.contentPanel.form.findField("CUST_TYPE").setValue('0');

		if (view.contentPanel.form.findField("IF_SECOND_STEP").value == '0') {// 否
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;

			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = true;
			view.contentPanel.form.findField("CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = true;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = true;
			view.contentPanel.form.findField("IF_ADD").allowBlank = true;
			view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = true;
		} else if (view.contentPanel.form.findField("IF_SECOND_STEP").value == '1') {// 是
			view.contentPanel.form.findField("CP_HARD_INFO").hide();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("CP_HARD_INFO").setValue('');

			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
			view.contentPanel.form.findField("CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = false;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = false;
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;

			if (view.contentPanel.form.findField("CUST_TYPE").getValue() == '2') {// 客户类型是否为
				// 旧户增贷
				view.contentPanel.form.findField("IF_ADD").show();
				view.contentPanel.form.findField("IF_ADD").allowBlank = false;

				if (view.contentPanel.form.findField("IF_ADD").getValue() == '1') {
					view.contentPanel.form.findField("ADD_AMT").show();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = false;
				} else {
					view.contentPanel.form.findField("ADD_AMT").hide();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
					view.contentPanel.form.findField("ADD_AMT").setValue('');
				}
			} else {
				view.contentPanel.form.findField("IF_ADD").hide();
				view.contentPanel.form.findField("IF_ADD").allowBlank = true;
				view.contentPanel.form.findField("IF_ADD").setValue('');

				view.contentPanel.form.findField("ADD_AMT").hide();
				view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
				view.contentPanel.form.findField("ADD_AMT").setValue('');
			}
		} else {// 暂时维持本阶段
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;

			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
			view.contentPanel.form.findField("CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = false;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = false;
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;

			if (view.contentPanel.form.findField("CUST_TYPE").getValue() == '2') {// 客户类型是否为
				// 旧户增贷
				view.contentPanel.form.findField("IF_ADD").show();
				view.contentPanel.form.findField("IF_ADD").allowBlank = false;

				if (view.contentPanel.form.findField("IF_ADD").getValue() == '1') {
					view.contentPanel.form.findField("ADD_AMT").show();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = false;
				} else {
					view.contentPanel.form.findField("ADD_AMT").hide();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
					view.contentPanel.form.findField("ADD_AMT").setValue('');
				}
			} else {
				view.contentPanel.form.findField("IF_ADD").hide();
				view.contentPanel.form.findField("IF_ADD").allowBlank = true;
				view.contentPanel.form.findField("IF_ADD").setValue('');

				view.contentPanel.form.findField("ADD_AMT").hide();
				view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
				view.contentPanel.form.findField("ADD_AMT").setValue('');
			}

		}

	}
};

/**
 * 根据step 判断跳转时所需参数
 */
var selectParam = function(step,pipelineId) {
	// smeProspectE prospect信息SME 1006082
	// smeIntentE 合作意向信息SME 1006083
	// smeCaE 文件收集阶段SME 1006084
	// smeCaCo 文件及CA准备阶段SME 1006108
	// smeCheckE 信用审查SME 1006085
	// smeApprovlE 核批阶段SME 1006086
	// smeApprovedE 已核批动拨SME 1006087
	if (step == '1') {
		smeForward('1006082', 'prospect信息SME', 'smeProspectE',pipelineId);
	} else if (step == '2') {
		smeForward('1006083', '合作意向信息SME', 'smeIntentE',pipelineId);
	} else if (step == '3') {
		smeForward('1006084', '文件收集阶段SME', 'smeCaE',pipelineId);
	} else if (step == '4') {
		smeForward('1006108', '文件及CA准备阶段SME', 'smeCaCo',pipelineId);
	} else if (step == '5') {
		smeForward('1006085', '信用审查SME', 'smeCheckE',pipelineId);
	} else if (step == '6') {
		smeForward('1006086', '核批阶段SME', 'smeApprovlE',pipelineId);
	} else if (step == '7') {
		smeForward('1006087', '已核批动拨SME', 'smeApprovedE',pipelineId);
	}
};

/**
 * 根据传递参数 跳转至相应界面
 */
var smeForward = function(resId, name, path,pipelineId) {
	// var resId = resId; //页面功能资源号，CNT_MENU,ID可通过页面右键菜单获取
	var taskMgr = Wlj ? Wlj.TaskMgr : undefined;
	var p = parent;
	for ( var i = 0; i < 10 && !taskMgr; i++) {
		p = p.parent;
		taskMgr = p.Wlj ? p.Wlj.TaskMgr : undefined;
	}
	if (taskMgr.getTask('task_' + resId)) {
		taskMgr.getTask('task_' + resId).close();
	}
	parent._APP.taskBar.openWindow({
		name : name,
		action : basepath + '/contents/pages/wlj/customerManager/potentialSme/'
				+ path + '.js?pipeline_id=' + pipelineId,
		resId : resId,
		id : 'task_' + resId
	});
};

/**
 * 双击数据，跳转至相应界面
 */
var rowdblclick = function(tile, record) {
	selectParam(getSelectedData().data.HIDE_STEP,getSelectedData().data.PIPELINE_ID);
};
// 查询结果每页默认显示20条
var beforeconditioninit = function(panel, app) {
	app.pageSize = 20;
};