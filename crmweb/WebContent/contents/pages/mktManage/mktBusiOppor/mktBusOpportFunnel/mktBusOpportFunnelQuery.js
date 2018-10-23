/**
 * 营销管理->商机管理->销售漏斗 查询条件Form对象定义 wzy，2013-02-20
 */

/** ***************************定义查询条件中的下拉框对象***********开始**************** */
// "商机类型"下拉框定义
var combo_oppor_type = new Ext.data.Store({
			autoLoad : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=OPPOR_TYPE',
						method : 'GET'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value']),
			fields : ['key', 'value']
		});
// "商机状态"下拉框定义
var combo_busi_chance_status = new Ext.data.Store({
			autoLoad : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=BUSI_CHANCE_STATUS',
						method : 'GET'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value']),
			fields : ['key', 'value']
		});
// "商机阶段"下拉框定义
var combo_busi_chance_stage = new Ext.data.Store({
			autoLoad : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=BUSI_CHANCE_STAGE',
						method : 'GET'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value']),
			fields : ['key', 'value']
		});
// "商机来源"下拉框定义
var combo_busi_chance_source = new Ext.data.Store({
			autoLoad : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=BUSI_CHANCE_SOURCE',
						method : 'GET'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value']),
			fields : ['key', 'value']
		});
// "达成概率"下拉框定义
var combo_reach_prob = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=BUSI_CHANCE_PROB'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});
/** ***************************定义查询条件中的下拉框对象***********结束**************** */

/** ***************************定义查询条件Form表单中的按钮*********开始**************** */
var queryButton = [{
			text : '查询',
			handler : function() {
				var conditionStr = qForm.getForm().getFieldValues();
				var paraJson = Ext.encode(conditionStr);
				// 查询漏斗图形
				displayFunnel(paraJson);
				// 查询列表
				store.load({
							params : {
								'condition' : paraJson
							}
						});
			}
		}, {
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
			}
		}];
/** ***************************定义查询条件Form表单中的按钮*********结束**************** */

// 查询漏斗图形
function displayFunnel(paraJson) {
	var swfUrl = basepath + "/FusionCharts/Funnel.swf";
	var chart = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0", "0");
	var queryUrl = basepath + '/mktBusiOpporFunnelQueryAction!'
			+ 'getQueryResultJsonData.json';
	Ext.Ajax.request({
				url : queryUrl,
				method : 'POST',
				waitMsg : '正在查询数据,请等待...',
				params : {
					'condition' : paraJson
				},
				success : function(response) {
					var rs = response.responseText;
					if (rs) {
						chart.setJSONData(praseData(rs));
						chart.render("chartdiv");
					}
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询出错！');
				}
			});
}

// 数据解析转换
function praseData(rs) {
	var arr = rs.split(",");
	var caption = "";
	if (arr[1] == 0 && arr[3] == 0 && arr[5] == 0 && arr[7] == 0 && arr[9] == 0) {
		caption = "各阶段商机数量全为0，漏斗不能展示。";
	}
	var jsonData = {
		"chart" : {
			"manageresize" : "1",
			"caption" : caption,
			"subcaption" : "",
			"showpercentvalues" : "0",
			"decimals" : "2",
			"basefontsize" : "12",
			"issliced" : "1",
			"connectNullData" : "1"
		},
		"data" : [{
					"label" : arr[0],
					"value" : arr[1] == 0 ? "0.000001" : arr[1]
				}, {
					"label" : arr[2],
					"value" : arr[3] == 0 ? "0.000001" : arr[3]
				}, {
					"label" : arr[4],
					"value" : arr[5] == 0 ? "0.000001" : arr[5]
				}, {
					"label" : arr[6],
					"value" : arr[7] == 0 ? "0.000001" : arr[7]
				}, {
					"label" : arr[8],
					"value" : arr[9] == 0 ? "0.000001" : arr[9]
				}],
		"styles" : {
			"definition" : [{
						"type" : "font",
						"name" : "captionFont",
						"size" : "15"
					}],
			"application" : [{
						"toobject" : "CAPTION",
						"styles" : "captionFont"
					}]
		}
	};
	return jsonData;
}

/** ***************************查询条件Form表单定义*****************开始**************** */
var qForm = new Ext.form.FormPanel({
	id : 'qform',
	title : '销售漏斗查询',
	border : true,
	region : 'north',
	autoScroll : true,
	frame : true, // 是否渲染表单面板背景色
	labelAlign : 'right', // 标签对齐方式
	// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
	buttonAlign : 'center',
	height : 130,
	// width:document.body.scrollWidth-10,
	items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										xtype : 'datefield',
										fieldLabel : '完成日期',
										labelStyle : 'text-align:right;',
										name : 'OPPOR_END_DATE',
										format : 'Y-m-d',
										allowDecimal : false,
										anchor : '95%',
										editable : false
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										xtype : 'datefield',
										fieldLabel : '开始日期',
										labelStyle : 'text-align:right;',
										name : 'OPPOR_START_DATE',
										format : 'Y-m-d',
										allowDecimal : false,
										anchor : '95%',
										editable : false
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										xtype : 'datefield',
										fieldLabel : '创建日期',
										labelStyle : 'text-align:right;',
										name : 'CREATE_DATE_TIME',
										format : 'Y-m-d',
										allowDecimal : false,
										anchor : '95%',
										editable : false
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										xtype : 'datefield',
										fieldLabel : '到期日期',
										labelStyle : 'text-align:right;',
										name : 'OPPOR_DUE_DATE',
										format : 'Y-m-d',
										allowDecimal : false,
										anchor : '95%',
										editable : false
									}]
						}]
			}, {
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Com.yucheng.bcrm.common.OrgField({
								searchType : 'SUBTREE',// 指定查询机构范围属性,SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH（所有父、祖机构）ALLORG（所有机构）
								fieldLabel : '执行机构',
								labelStyle : 'text-align:right;',
								name : 'EXEC_ORG_NAME',
								hiddenName : 'EXEC_ORG_ID', // 后台获取的参数名称
								anchor : '95%',
								checkBox : true
									// 复选标志
								})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Com.yucheng.crm.common.OrgUserManage({
										xtype : 'userchoose',
										fieldLabel : '执行人',
										allowBlank : true,
										labelStyle : 'text-align:right;',
										name : 'EXEC_USER_NAME',
										hiddenName : 'EXEC_USER_ID',
										searchType : 'SUBORGS',// *指定查询机构范围属性,SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH（所有父、祖机构）ALLORG（所有机构）
										singleSelect : false,// 控制是否只能单选
										anchor : '95%'
									})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Com.yucheng.bcrm.common.CustomerQueryField(
									{
										fieldLabel : '客户名称',
										name : 'CUST_NAME',
										// custtype :'1',//客户类型：1：对私,2:对公,不设默认全部
										// custStat:'1',//客户状态:1:正式 2：潜在,不设默认全部
										singleSelected : false,// 单选复选标志
										editable : false,
										anchor : '95%',
										hiddenName : 'CUST_ID',
										callback : function() {
										}
									})]
						}]
			}],
	buttons : queryButton
});
/** ***************************查询条件Form表单定义*****************结束**************** */
