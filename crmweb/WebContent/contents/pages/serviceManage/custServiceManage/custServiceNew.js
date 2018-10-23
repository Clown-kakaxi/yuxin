/**
 * 服务管理NEW
 * 
 * @author hujun 2014-06-05
 */

imports([ '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
		'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
		'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js' ]);

var mktId = null;
var createView = true;// 是否启用新建
var editView = true;// 是否启用修改面板
var detailView = true;// 是否启用详情面板
// WLJUTIL.alwaysLockCurrentView=true;
var lookupTypes = [ // 数据字典项
'SERVICE_STAT', 'SERVICE_KIND', 'SERVICE_CHANNEL' ];
var localLookup = {
	'SERVICE_NODE' : [ {
		key : '01',
		value : '首次联系(电访)'
	}, {
		key : '02',
		value : '合作意向(约访)'
	}, {
		key : '03',
		value : '产品建议书准备'
	}, {
		key : '04',
		value : '修正产品建议书准备'
	}, {
		key : '05',
		value : '结案'
	} ]
};
var url = basepath + '/custServiceManage.json';
var comitUrl = basepath + '/custServiceManage.json';
var fields = [ {
	name : 'SERVICE_ID',
	text : '服务ID',
	hidden : true
}, {
	name : 'SERVICE_STAT',
	text : '服务状态',
	searchField : true,
	resutlFloat : 'right',
	translateType : 'SERVICE_STAT'
}, {
	name : 'CUST_ID',
	text : '客户编号',
	resutlFloat : 'right'
}, {
	name : 'CUST_NAME',
	text : '客户名称',
	xtype : 'customerquery',
	hiddenName : 'CUST_ID1',
	searchField : true,
	callback : function(a) {
		// getCreateView().getFieldsByName('CUST_ID').setValue(a.custStr);
		getCreateView().setValues({
			CUST_ID : a.customerId
		});
	}
}, {
	name : 'SERVICE_KIND',
	text : '服务类别',
	searchField : true,
	resutlFloat : 'right',
	translateType : 'SERVICE_KIND'
}, {
	name : 'PSTART_DATE',
	text : '服务开始时间',
	format : 'Y-m-d',
	xtype : 'datefield'
}, {
	name : 'PEND_DATE',
	text : '服务结束日期',
	format : 'Y-m-d',
	xtype : 'datefield'
}, {
	name : 'SERVICE_NODE',
	text : '服务节点',
	translateType : 'SERVICE_NODE'
}, {
	name : 'EXECUTOR',
	text : '执行人',
	hidden : true
}, {
	name : 'EXECUTOR_NAME',
	text : '执行人'
}, {
	name : 'SERVICE_CONT',
	text : '服务内容',
	gridField : false,
	resutlWidth : 350,
	xtype : 'textarea'
}, {
	name : 'NEED_RESOURCE',
	text : '所需资源',
	gridField : false,
	resutlWidth : 350,
	xtype : 'textarea'
}, {
	name : 'CREATE_USER',
	text : '',
	hidden : true
}, {
	name : 'USER_NAME',
	text : '创建人',
	hidden : true
}, {
	name : 'CREATE_DATE',
	text : '创建日期',
	hidden : true
}, {
	name : 'UPDATE_USER',
	text : '',
	hidden : true
}, {
	name : 'UPDATEUSER_NAME',
	text : '最近更新人',
	hidden : true
}, {
	name : 'UPDATE_DATE',
	text : '最近更新日期',
	hidden : true
}, {
	name : 'CREATE_ORG',
	text : '创建机构',
	hidden : true
}, {
	name : 'P_OR_C',
	text : '对公对私标识',
	hidden : true
} ];
/** ******************新增面板的编辑****************************** */
var createFormViewer = [
		{
			columnCount : 2,
			fields : [ 'CUST_ID', 'CUST_NAME', 'SERVICE_STAT', 'SERVICE_KIND',
					'PSTART_DATE', 'PEND_DATE', 'SERVICE_NODE' ],
			fn : function(CUST_ID, CUST_NAME, SERVICE_STAT, SERVICE_KIND,
					PSTART_DATE, PEND_DATE, SERVICE_NODE) {
				SERVICE_STAT.value = '01';
				CUST_ID.hidden = true;
				SERVICE_STAT.hidden = true;
				return [ CUST_ID, CUST_NAME, SERVICE_STAT, SERVICE_KIND,
						PSTART_DATE, PEND_DATE, SERVICE_NODE ];
			}
		}, {
			columnCount : 0.95,
			fields : [ 'SERVICE_CONT' ],
			fn : function(SERVICE_CONT) {
				return [ SERVICE_CONT ];
			}
		}, {
			columnCount : 0.95,
			fields : [ 'NEED_RESOURCE' ],
			fn : function(NEED_RESOURCE) {
				return [ NEED_RESOURCE ];
			}
		} ];
/** **************修改面板配置*********************** */
var editFormViewer = [
		{
			columnCount : 2,
			fields : [ 'SERVICE_ID', 'CUST_ID', 'CUST_NAME', 'SERVICE_STAT',
					'SERVICE_KIND', 'PSTART_DATE', 'PEND_DATE', 'SERVICE_NODE',
					'EXECUTOR', 'EXECUTOR_NAME' ],
			fn : function(SERVICE_ID, CUST_ID, CUST_NAME, SERVICE_STAT,
					SERVICE_KIND, PSTART_DATE, PEND_DATE, SERVICE_NODE,
					EXECUTOR, EXECUTOR_NAME) {
				EXECUTOR.hidden = true;
				CUST_ID.disabled = true;
				CUST_NAME.disabled = true;
				SERVICE_STAT.disabled = true;
				EXECUTOR_NAME.disabled = true;
				EXECUTOR.disabled = true;
				CUST_ID.cls = 'x-readOnly';
				CUST_NAME.cls = 'x-readOnly';
				SERVICE_STAT.cls = 'x-readOnly';
				EXECUTOR_NAME.cls = 'x-readOnly';
				EXECUTOR.cls = 'x-readOnly';
				return [ SERVICE_ID, CUST_ID, CUST_NAME, SERVICE_STAT,
						SERVICE_KIND, PSTART_DATE, PEND_DATE, SERVICE_NODE,
						EXECUTOR, EXECUTOR_NAME ];
			}
		}, {
			columnCount : 0.95,
			fields : [ 'SERVICE_CONT' ],
			fn : function(SERVICE_CONT) {
				return [ SERVICE_CONT ];
			}
		}, {
			columnCount : 0.95,
			fields : [ 'NEED_RESOURCE' ],
			fn : function(NEED_RESOURCE) {
				return [ NEED_RESOURCE ];
			}
		} ];
/** **************详情面板的配置*********************** */
var detailFormViewer = [
		{
			columnCount : 2,
			fields : [ 'CUST_ID', 'CUST_NAME', 'SERVICE_STAT', 'SERVICE_KIND',
					'PSTART_DATE', 'PEND_DATE', 'SERVICE_NODE', 'EXECUTOR',
					'EXECUTOR_NAME', 'CREATE_USER', 'CREATE_DATE',
					'UPDATE_USER', 'UPDATE_DATE' ],
			fn : function(CUST_ID, CUST_NAME, SERVICE_STAT, SERVICE_KIND,
					PSTART_DATE, PEND_DATE, SERVICE_NODE, EXECUTOR_NAME,
					CREATE_USER, CREATE_DATE, UPDATE_USER, UPDATE_DATE) {
				CUST_ID.disabled = true;
				CUST_NAME.disabled = true;
				SERVICE_STAT.disabled = true;
				SERVICE_KIND.disabled = true;
				PSTART_DATE.disabled = true;
				PEND_DATE.disabled = true;
				SERVICE_NODE.disabled = true;
				EXECUTOR_NAME.disabled = true;
				CREATE_USER.disabled = true;
				CREATE_DATE.disabled = true;
				UPDATE_USER.disabled = true;
				UPDATE_DATE.disabled = true;
				CUST_ID.cls = 'x-readOnly';
				CUST_NAME.cls = 'x-readOnly';
				SERVICE_STAT.cls = 'x-readOnly';
				SERVICE_KIND.cls = 'x-readOnly';
				PSTART_DATE.cls = 'x-readOnly';
				PEND_DATE.cls = 'x-readOnly';
				SERVICE_NODE.cls = 'x-readOnly';
				EXECUTOR_NAME.cls = 'x-readOnly';
				CREATE_USER.cls = 'x-readOnly';
				CREATE_DATE.cls = 'x-readOnly';
				UPDATE_USER.cls = 'x-readOnly';
				UPDATE_DATE.cls = 'x-readOnly';
				return [ CUST_ID, CUST_NAME, SERVICE_STAT, SERVICE_KIND,
						PSTART_DATE, PEND_DATE, SERVICE_NODE, EXECUTOR_NAME,
						CREATE_USER, CREATE_DATE, UPDATE_USER, UPDATE_DATE ];
			}
		}, {
			columnCount : 0.95,
			fields : [ 'SERVICE_CONT' ],
			fn : function(SERVICE_CONT) {
				SERVICE_CONT.disabled = true;
				SERVICE_CONT.cls = 'x-readOnly';
				return [ SERVICE_CONT ];
			}
		}, {
			columnCount : 0.95,
			fields : [ 'NEED_RESOURCE' ],
			fn : function(NEED_RESOURCE) {
				NEED_RESOURCE.disabled = true;
				NEED_RESOURCE.cls = 'x-readOnly';
				return [ NEED_RESOURCE ];
			}
		} ];
// 客户服务执行时修改服务状态
var serviceExecute = function(selectRe, infoStatus) {
	var serviceId = selectRe.data.SERVICE_ID;
	Ext.Ajax.request({
		url : basepath + '/custServiceManage!updateStat.json',
		params : {
			stat : infoStatus ? infoStatus : '02',
			idStr : serviceId
		},
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		method : 'GET',
		scope : this,
		success : function() {
			// reloadCurrentData(); //此时加载会关闭执行页面，所以不加载数据，改在viewhide时调用
		}
	});
};
var customerView = [
		{
			title : '客户服务执行',
			type : 'grid',
			url : basepath + '/MarketTractManage.json',
			pageable : true,
			fields : {
				fields : [ {
					name : 'MKT_ID',
					hidden : true
				}, {
					name : 'RECORD_ID',
					hidden : true
				}, {
					name : 'CUST_ID',
					text : '客户编号',
					hidden : true
				}, {
					name : 'CUST_NAME',
					text : '客户名称'
				}, {
					name : 'CANTACT_DATE',
					text : '服务时间',
					xtype : 'datefield',
					format : 'Y-m-d'
				}, {
					name : 'CANTACT_CHANNEL',
					text : '接触渠道',
					translateType : 'SERVICE_CHANNEL',
					renderer : function(value) {
						return translateLookupByKey("SERVICE_CHANNEL", value);
					}
				}, {
					name : 'SERVICE_KIND',
					text : '服务类型',
					translateType : 'SERVICE_KIND',
					renderer : function(value) {
						return translateLookupByKey("SERVICE_KIND", value);
					}
				}, {
					name : 'MARKET_RESULT',
					text : '服务结果'
				}, {
					name : 'NEED_EVENT',
					text : '待跟进事项'
				}, {
					name : 'CREATE_USER',
					hidden : true
				}, {
					name : 'EXECUTOR_NAME',
					text : '执行人'
				}, {
					name : 'EXECUTOR',
					text : '执行人',
					hidden : true
				}, {
					name : 'CREATE_DATE',
					text : '创建日期'
				}, {
					name : 'UPDATE_USER',
					hidden : true
				}, {
					name : 'UPDATEUSER_NAME',
					text : '最近更新人'
				}, {
					name : 'UPDATE_DATE',
					text : '最近更新日期'
				} ]
			},
			gridButtons : [
					{
						text : '新增',
						fn : function(grid) {
							showCustomerViewByTitle('新增执行记录');
						}
					},
					{
						text : '修改',
						fn : function(grid) {
							var selectLength = grid.getSelectionModel()
									.getSelections().length;
							var selectRecord = grid.getSelectionModel()
									.getSelections()[0];
							if (selectLength != 1) {
								Ext.Msg.alert('提示', '请选择一条数据进行操作!');
								return false;
							}
							showCustomerViewByTitle('修改执行记录');

						}
					},
					{
						text : '删除',
						fn : function(grid) {

							var selectLength = grid.getSelectionModel()
									.getSelections().length;
							var selectRecords = grid.getSelectionModel()
									.getSelections();
							if (selectLength < 1) {
								Ext.Msg.alert('提示', '请选择一条数据进行操作!');
								return false;
							}
							var idStr = '';
							for ( var i = 0; i < selectLength; i++) {
								var selectRecord = selectRecords[i];
								idStr += selectRecord.data.RECORD_ID;
								if (i != selectLength - 1) {
									idStr += ',';
								}
							}
							Ext.MessageBox
									.confirm(
											'提示',
											'你确定删除吗!',
											function(buttonId) {
												if (buttonId.toLowerCase() == 'no') {
													return false;
												}
												// var tempId = 123;
												Ext.Ajax
														.request({
															url : basepath
																	+ '/MarketTractManage!batchDestroy.json',
															method : 'POST',
															waitMsg : '正在删除，请稍等...',
															params : {
																idStr : idStr
															},
															success : function() {
																Ext.Msg
																		.alert(
																				'提示',
																				'删除操作成功!');
																grid.store
																		.reload();
															},
															failure : function() {
																Ext.Msg
																		.alert(
																				'提示',
																				'删除操作失败!');
															}
														});
											});

						}
					} ]
		},
		{
			title : '新增执行记录',
			type : 'form',
			hideTitle : true,
			groups : [
					{
						columnCount : 2,
						fields : [ {
							name : 'MKT_ID',
							hidden : true
						}, {
							name : 'RECORD_ID',
							hidden : true
						}, {
							name : 'CUST_ID',
							text : '客户编号',
							allowBlank : false,
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CUST_NAME',
							text : '客户名称',
							allowBlank : false,
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CANTACT_DATE',
							text : '服务时间',
							allowBlank : false,
							xtype : 'datefield',
							format : 'Y-m-d'
						}, {
							name : 'CANTACT_CHANNEL',
							text : '接触渠道',
							translateType : 'SERVICE_CHANNEL'
						}, {
							name : 'SERVICE_KIND',
							text : '服务类型',
							translateType : 'SERVICE_KIND',
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CREATE_USER'
						}, {
							name : 'EXECUTOR',
							readOnly : true
						},// 执行人
						{
							name : 'EXECUTOR_NAME',
							text : '执行人',
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CREATE_DATE',
							xtype : 'datefield',
							format : 'Y-m-d'
						},// 创建日期
						{
							name : 'UPDATE_USER'
						},
						// {name:'UPDATEUSER_NAME',text:'最近更新人'},
						{
							name : 'UPDATE_DATE'
						} // 最近更新日期
						],
						fn : function(MKT_ID, CUST_ID, CUST_NAME,
								CANTACT_CHANNEL, SERVICE_KIND, CANTACT_DATE,
								CREATE_USER, CREATE_DATE, UPDATE_USER,
								UPDATE_DATE) {
							MKT_ID.value = '';

							return [ MKT_ID, CUST_ID, CUST_NAME,
									CANTACT_CHANNEL, SERVICE_KIND,
									CANTACT_DATE, CREATE_USER, CREATE_DATE,
									UPDATE_USER, UPDATE_DATE ];
						}
					}, {
						columnCount : 0.95,
						fields : [ {
							name : 'MARKET_RESULT',
							text : '服务结果',
							xtype : 'textarea'
						} ],
						fn : function(MARKET_RESULT) {
							return [ MARKET_RESULT ];
						}
					}, {
						columnCount : 0.95,
						fields : [ {
							name : 'NEED_EVENT',
							text : '待跟进事项',
							xtype : 'textarea'
						} ],
						fn : function(NEED_EVENT) {
							return [ NEED_EVENT ];
						}
					} ],
			formButtons : [
					{
						text : '保存',
						fn : function(contentPanel, baseform) {

							if (!baseform.isValid()) {
								Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
								return false;
							}
							var commintData = translateDataKey(baseform
									.getFieldValues(), _app.VIEWCOMMITTRANS);
							Ext.Ajax.request({
								url : basepath + '/MarketTractManage.json',
								method : 'POST',
								params : commintData,
								success : function(response) {
									Ext.Msg.alert('提示', '保存成功！');
									showCustomerViewByTitle('客户服务执行');
								},
								failure : function() {
									Ext.Msg.alert('提示', '保存失败！');
								}
							});

						}
					}, {
						text : '返回',
						fn : function(contentPanel, baseform) {
							showCustomerViewByTitle('客户服务执行');
						}
					} ]
		},
		{
			title : '修改执行记录',
			type : 'form',
			hideTitle : true,
			groups : [
					{
						columnCount : 2,
						fields : [ {
							name : 'MKT_ID',
							hidden : true
						}, {
							name : 'RECORD_ID',
							hidden : true
						}, {
							name : 'CUST_ID',
							text : '客户编号',
							allowBlank : false,
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CUST_NAME',
							text : '客户名称',
							allowBlank : false,
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CANTACT_DATE',
							text : '服务时间',
							allowBlank : false,
							xtype : 'datefield',
							format : 'Y-m-d'
						}, {
							name : 'CANTACT_CHANNEL',
							text : '接触渠道',
							translateType : 'SERVICE_CHANNEL'
						}, {
							name : 'SERVICE_KIND',
							text : '服务类型',
							translateType : 'SERVICE_KIND',
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CREATE_USER'
						}, {
							name : 'EXECUTOR'
						},// 执行人
						{
							name : 'EXECUTOR_NAME',
							text : '执行人',
							readOnly : true,
							cls : 'x-readOnly'
						}, {
							name : 'CREATE_DATE',
							xtype : 'datefield',
							format : 'Y-m-d'
						},// 创建日期
						{
							name : 'UPDATE_USER'
						}, {
							name : 'UPDATEUSER_NAME'
						},// 最近更新人
						{
							name : 'UPDATE_DATE'
						} // 最近更新日期
						],
						fn : function(MKT_ID, RECORD_ID, CUST_ID, CUST_NAME,
								CANTACT_CHANNEL, SERVICE_KIND, CANTACT_DATE,
								CREATE_USER, CREATE_DATE, UPDATE_USER,
								UPDATE_DATE) {
							// MKT_ID.value='';
							return [ MKT_ID, RECORD_ID, CUST_ID, CUST_NAME,
									CANTACT_CHANNEL, SERVICE_KIND,
									CANTACT_DATE, CREATE_USER, CREATE_DATE,
									UPDATE_USER, UPDATE_DATE ];
						}
					}, {
						columnCount : 0.95,
						fields : [ {
							name : 'MARKET_RESULT',
							text : '服务结果',
							xtype : 'textarea'
						} ],
						fn : function(MARKET_RESULT) {
							return [ MARKET_RESULT ];
						}
					}, {
						columnCount : 0.95,
						fields : [ {
							name : 'NEED_EVENT',
							text : '待跟进事项',
							xtype : 'textarea'
						} ],
						fn : function(NEED_EVENT) {
							return [ NEED_EVENT ];
						}
					} ],
			formButtons : [
					{
						text : '保存',
						fn : function(contentPanel, baseform) {

							if (!baseform.isValid()) {
								Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
								return false;
							}
							var commintData = translateDataKey(baseform
									.getFieldValues(), _app.VIEWCOMMITTRANS);
							Ext.Ajax.request({
								url : basepath + '/MarketTractManage.json',
								method : 'POST',
								params : commintData,
								success : function(response) {
									Ext.Msg.alert('提示', '保存成功！');
									showCustomerViewByTitle('客户服务执行');
								},
								failure : function() {
									Ext.Msg.alert('提示', '保存失败！');
								}
							});

						}
					}, {
						text : '返回',
						fn : function(contentPanel, baseform) {
							hideCurrentView();
						}
					} ]
		},
		{
			title : '服务补录窗口',
			type : 'form',
			hideTitle : true,
			groups : [
					{
						columnCount : 2,
						fields : [ {
							name : 'SERVICE_ID',
							text : '服务ID',
							hidden : true
						}, {
							name : 'CUST_ID',
							text : '客户编号',
							resutlFloat : 'right'
						}, {
							name : 'CUST_NAME',
							text : '客户名称',
							xtype : 'customerquery',
							hiddenName : 'CUST_ID1',
							callback : function(a) {
								// getCreateView().getFieldsByName('CUST_ID').setValue(a.custStr);
								getCreateView().setValues({
									CUST_ID : a.customerId
								});
							}
						}, {
							name : 'SERVICE_KIND',
							text : '服务类别',
							resutlFloat : 'right',
							translateType : 'SERVICE_KIND'
						}, {
							name : 'SERVICE_STAT',
							text : '服务状态',
							resutlFloat : 'right',
							translateType : 'SERVICE_STAT'
						}, {
							name : 'PSTART_DATE',
							text : '服务开始时间',
							format : 'Y-m-d',
							xtype : 'datefield'
						}, {
							name : 'PEND_DATE',
							text : '服务结束日期',
							format : 'Y-m-d',
							xtype : 'datefield'
						}, {
							name : 'SERVICE_NODE',
							text : '服务节点',
							translateType : 'SERVICE_NODE'
						} ],
						fn : function(SERVICE_ID, CUST_ID, CUST_NAME,
								SERVICE_KIND, SERVICE_STAT, PSTART_DATE,
								PEND_DATE, SERVICE_NODE) {
							CUST_ID.hidden = true;
							SERVICE_ID.hidden = true;
							return [ SERVICE_ID, CUST_ID, CUST_NAME,
									SERVICE_KIND, SERVICE_STAT, PSTART_DATE,
									PEND_DATE, SERVICE_NODE ];
						}
					}, {
						columnCount : 0.95,
						fields : [ {
							name : 'SERVICE_CONT',
							text : '服务内容',
							resutlWidth : 350,
							xtype : 'textarea'
						} ],
						fn : function(SERVICE_CONT) {
							return [ SERVICE_CONT ];
						}
					}, {
						columnCount : 0.95,
						fields : [ {
							name : 'NEED_RESOURCE',
							text : '所需资源',
							resutlWidth : 350,
							xtype : 'textarea'
						} ],
						fn : function(NEED_RESOURCE) {
							return [ NEED_RESOURCE ];
						}
					} ],
			formButtons : [
					{
						text : '保存',
						fn : function(contentPanel, baseform) {

							if (!baseform.isValid()) {
								Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
								return false;
							}
							var commintData = translateDataKey(baseform
									.getFieldValues(), _app.VIEWCOMMITTRANS);
							Ext.Ajax.request({
								url : basepath + '/custServiceManage.json',
								method : 'POST',
								params : commintData,
								success : function(response) {
									Ext.Msg.alert('提示', '保存成功！');
									hideCurrentView();
									reloadCurrentData();

								},
								failure : function() {
									Ext.Msg.alert('提示', '保存失败！');
								}
							});

						}
					}, {
						text : '返回',
						fn : function(contentPanel, baseform) {
							hideCurrentView();
						}
					} ]
		} ];
var tbar = [
		{
			text : '服务补录',
			id : 'additionalBut',
			handler : function() {
				if( getSelectedData() == false || getAllSelects().length > 1){
					Ext.Msg.alert('提示','请选择一条数据！');
					return false;
				}
				showCustomerViewByTitle('服务补录窗口');
			}
		},
		{
			text : '服务结束',
			id : 'serviceEndBut',
			handler : function() {
				if (getSelectedData()) {// 判断是否选择记录
					var records = getAllSelects();// 得到被选择的行的数组
					var selectLength = getAllSelects().length;// 得到行数组的长度
					if (selectLength < 1) {
						Ext.Msg.alert('提示信息', '请至少选择一条记录！');
					} else {
						var serviceId, infoStatus, createUser;
						var idStr = '';
						for ( var i = 0; i < selectLength; i++) {
							selectRe = records[i];
							serviceId = selectRe.data.SERVICE_ID;// 获得选中记录的id
							infoStatus = selectRe.data.SERVICE_STAT;
							createUser = selectRe.data.CREATE_USER;
							if (infoStatus != '02') {
								Ext.Msg.alert('提示', '只能结束状态为执行的服务信息!');
								return false;
							}
							;
							if (createUser != __userId) {
								Ext.Msg.alert('提示', '只能结束自己创建的服务信息!');
								return false;
							}
							;
							idStr += serviceId;
							if (i != selectLength - 1)
								idStr += ',';
						}
						;
						if (idStr != '') {
							Ext.MessageBox
									.confirm(
											'系统提示信息',
											'确认结束该条服务吗？',
											function(buttonobj) {
												if (buttonobj == 'yes') {
													Ext.Ajax
															.request({
																url : basepath
																		+ '/custServiceManage!updateStat.json',
																params : {
																	stat : '03',
																	idStr : idStr
																},
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																method : 'GET',
																scope : this,
																success : function() {
																	Ext.Msg
																			.alert(
																					'提示信息',
																					'操作成功！');
																	reloadCurrentData();
																}
															});
												}
											}, this);
						}
					}
				} else {
					Ext.Msg.alert('提示信息', '请选择一条记录！');
				}
			}
		},
		{
			text : '删除',
			handler : function() {

				var records = getAllSelects();// 得到被选择的行的数组
				var selectLength = getAllSelects().length;// 得到行数组的长度
				if (selectLength < 1) {
					Ext.Msg.alert('提示信息', '请至少选择一条记录！');
				} else {
					var serviceId, infoStatus, createUser;
					var idStr = '';
					for ( var i = 0; i < selectLength; i++) {
						selectRe = records[i];
						serviceId = selectRe.data.SERVICE_ID;// 获得选中记录的id
						infoStatus = selectRe.data.SERVICE_STAT;
						createUser = selectRe.data.CREATE_USER;
						if (infoStatus != '02') {
							Ext.Msg.alert('提示', '只能删除状态为执行的服务信息!');
							return false;
						}
						;
						if (createUser != __userId) {
							Ext.Msg.alert('提示', '只能删除自己创建的服务信息!');
							return false;
						}
						;
						idStr += serviceId;
						if (i != selectLength - 1)
							idStr += ',';
					}
					;
					if (idStr != '') {
						Ext.MessageBox
								.confirm(
										'系统提示信息',
										'确认进行结束操作吗？',
										function(buttonobj) {
											if (buttonobj == 'yes') {
												Ext.Ajax
														.request({
															url : basepath
																	+ '/custServiceManage!batchDestroy.json',
															params : {
																// stat : '03',
																idStr : idStr
															},
															waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
															method : 'GET',
															scope : this,
															success : function() {
																Ext.Msg
																		.alert(
																				'提示信息',
																				'操作成功！');
																reloadCurrentData();
															}
														});
											}
										}, this);
					}
				}
			}
		} ];
/** ****************提交前数据校验********************* */
var validates = [ {
	desc : '服务开始日期不得大于结束日期',
	fn : function(PSTART_DATE, PEND_DATE) {
		if (PSTART_DATE.format('Y-m-d') > PEND_DATE.format('Y-m-d')) {
			return false;
		}
	},
	dataFields : [ 'PSTART_DATE', 'PEND_DATE' ]
} ];
/** *********view 滑入前控制*********** */

var beforeviewshow = function(view) {
	/* 修改查询面板滑入时，做相应判断 */
	if (view.baseType == 'editView' || view.baseType == 'detailView') {
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示', '请只选择一条数据');
			return false;
		}
	} else if (view.baseType != 'createView') {
		if (view._defaultTitle == '客户服务执行') {
			if (getSelectedData()) {
				var serviceId = getSelectedData().data.SERVICE_ID;// 获得选中记录的id
				var infoStatus = getSelectedData().data.SERVICE_STAT;
				var createUser = getSelectedData().data.CREATE_USER;
				if (createUser != __userId) {
					Ext.Msg.alert('提示', '只能对自己创建的服务信息进行此项操作!');
					return false;
				}
				if (infoStatus == '03') {
					Ext.Msg.alert('提示', '该服务计划已结束!');
					return false;
				}

			} else {
				Ext.Msg.alert('提示', '请选择一条数据进行操作！');
				return false;
			}
		} else if (view._defaultTitle == '新增执行记录') {
			var tempData = getSelectedData().data;
			view.contentPanel.getForm().reset();
			view.contentPanel.getForm().findField('MKT_ID').setValue(
					tempData.SERVICE_ID);
			view.contentPanel.getForm().findField('CUST_ID').setValue(
					tempData.CUST_ID);
			view.contentPanel.getForm().findField('CUST_NAME').setValue(
					tempData.CUST_NAME);
			view.contentPanel.getForm().findField('EXECUTOR').setValue(
					tempData.EXECUTOR);
			view.contentPanel.getForm().findField('EXECUTOR_NAME').setValue(
					tempData.EXECUTOR_NAME);
			view.contentPanel.getForm().findField('SERVICE_KIND').setValue(
					tempData.SERVICE_KIND);
		} else if (view._defaultTitle == '修改执行记录') {
			var tempGridView = getCustomerViewByTitle('客户服务执行');
			var tempRecord = tempGridView.grid.getSelectionModel()
					.getSelections()[0];
			view.contentPanel.getForm().loadRecord(tempRecord);
		}
	}

};
var viewshow = function(view) {
	if (view._defaultTitle == '客户服务执行') {
		if (getSelectedData()) {
			view.setParameters({
				mkt_Id : getSelectedData().data.SERVICE_ID
			});
			var serviceId = getSelectedData().data.SERVICE_ID;// 获得选中记录的id
			var infoStatus = getSelectedData().data.SERVICE_STAT;
			var createUser = getSelectedData().data.CREATE_USER;
			if (serviceId != '' && infoStatus == '01') {
				serviceExecute(getSelectedData());
			} else if (serviceId != '') {
				serviceExecute(getSelectedData(), infoStatus);
			}

			return true;
		}
	}
};
var beforecommit = function() {
	lockGrid();// 锁定结果列表
};
beforesetsearchparams = function(data) {
	for ( var key in data) {
		if (data[key] instanceof Date) {
			data[key] = data[key].format('Y-m-d');
		}
	}
};

var viewhide = function(view) {
	if (view._defaultTitle == '客户服务执行') {
		// reloadCurrentData();
	}
};
