/**
 * @description 价格计算器
 * @author Denghj
 * @since 2015-07-13
 */

Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews = false; // 自定义面板是否浮动

var fields = [{
			name : 'TEST',
			text : '此文件fields必须要有一个无用字段',
			resutlWidth : 80
		}];
/**
 * 本地数据字典定义
 */
var localLookup = {
	// 抵押物类型组
	'LOAN_TYPE' : [{key : '1',value : '出让土地住房（双证)'},
				   {key : '2',value : '商铺（双证）'},
				   {key : '3',value : '工厂（双证）'}, 
				   {key : '4',value : '写字楼（双证）'}, 
				   {key : '5',value : '纯土地'},
				   {key : '6',value : '停车位'},
				   {key : '7',value : '轿车'}, 
				   {key : '8',value : '轻型商务车'},
				   {key : '9',value : '轻型货车'},
				   {key : '10',value : '机器设备X类'}, 
				   {key : '11',value : '机器设备Y类'},
				   {key : '12',value : '机器设备Z类'}],
	// 价格对应评价组
	'PRICE_GRADE' : [{key : '1',value : 'A'}, 
					 {key : '2',value : 'B'},
					 {key : '3',value : 'C'}],
	// 还款方式组
	'REPAY_TYPE' : [{key : '1',value : '等额本息'}, 
					{key : '2',value : '每月付息,到期还本'}]
	
}

/**
 * 工具说明form
 */
var notesForm = new Ext.Panel({
			title : '工具说明',
			// height:160,
			collapsed : false,
			collapsible : true,
			autoHeight : true,
			padding : '10px 20px 0px',
			html : '带<font color=red>*</font>项为用户输入项<br>其余为输出项'
		});

/**
 * 计算器格局布置
 */
var calcForm = new Ext.FormPanel({
	title : '价格计算器',
	height : 480,
	collapsible : true,
	padding : '10px 0px 0px',
	labelWidth : 120,
	layout : 'form', // 整个大的表单是form布局,从上往下
	items : [{
		layout : 'column', // 第一行 每行是column布局,从左往右
		columnWidth : 1,
		items : [{
					layout : 'form', // coulumn不能显示fieldLable,加层layout:form
					columnWidth : .25,
					items : [{
								xtype : 'numberfield',								
								name : 'LOAN_PRICE',
								fieldLabel : '抵押物价值(万元)<font color=red>*</font>',
								width : 180,
								minValue : 0,
								editable : false,
								allowBlank : false
							}]
				}, {
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'numberfield',
								name : 'PLAN_APPLY',
								fieldLabel : '拟申请金额(万元)<font color=red>*</font>',
								width : 180,
								minValue : 0,
								editable : false,
								allowBlank : false,
								listeners : {
									blur : function() {
										loanPercentFun();// 监听抵押物价值与拟申请金额,计算出抵押成数
										loanLimit2Fun(); // 监听拟申请金额与额度1金额,计算出额度2金额
									}
								}
							}]
				}]
	}, {
		layout : 'column', // 第二行
		columnWidth : 1,
		items : [{
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'combo',
								name : 'LOAN',
								fieldLabel : '抵押物类型<font color=red>*</font>',
								width : 180,
								mode : 'local',
								store : new Ext.data.Store(),
								triggerAction : 'all',
								displayField : 'value',
								valueField : 'key',
								editable : false,
								allowBlank : false,
								listeners : {// 监听抵押物类型,价格对应评级及还款方式匹配出可抵押成数
									blur : function() {
										loanablePercentFun();
									}
								}
							}]
				}, {
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'textfield',
								name : 'LOAN_PERCENT',
								fieldLabel : '抵押成数(%)',
								width : 180,
								disabled : true
							}]
				}]
	}, {
		layout : 'column', // 第三行
		items : [{
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'combo',
								name : 'PRICE',
								fieldLabel : '价格对应评级<font color=red>*</font>',
								width : 180,
								mode : 'local',
								store : new Ext.data.Store(),
								triggerAction : 'all',
								displayField : 'value',
								valueField : 'key',
								editable : false,
								allowBlank : false,
								listeners : {// 监听抵押物类型,价格对应评级及还款方式匹配出可抵押成数
									blur : function() {
										loanablePercentFun();
									}
								}
							}]
				}, {
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'textfield',
								name : 'LOANABLE_PERCENT',
								fieldLabel : '可抵押成数(%)',
								width : 180,
								disabled : true
							}]
				}]
	}, {
		layout : 'column', // 第四行
		items : [{
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'numberfield',
								name : 'LIMIT1',
								fieldLabel : '额度1金额(万元)<font color=red>*</font>',
								width : 180,
								minValue : 0,
								editable : false,
								allowBlank : false,
								listeners : { // 监听拟申请金额和额度1金额,得出额度2金额
									blur : function() {
										loanLimit2Fun();
									}
								}
							}]
				}, {
					layout : 'form',
					columnWidth : .25,
					items : [{
								layout : 'form',
								columnWidth : .25,
								items : [{
											xtype : 'textfield',
											name : 'LIMIT2',
											fieldLabel : '额度2金额(万元)',
											width : 180,
											disabled : true
										}]
							}]
				}]
	}, {
		layout : 'column', // 第五行
		items : [{
			layout : 'form',
			columnWidth : .25,
			items : [{
				xtype : 'combo',
				name : 'REPAY1',
				fieldLabel : '额度1还款方式<font color=red>*</font>',
				width : 180,
				mode : 'local',
				store : new Ext.data.Store(),
				triggerAction : 'all',
				displayField : 'value',
				valueField : 'key',
				editable : false,
				allowBlank : false,
				listeners : {
					blur : function() {
						loanablePercentFun(); // 监听抵押物类型,价格对应评级及还款方式匹配出可抵押成数
						repayToTimeFun(calcForm.getForm().findField('REPAY1').getValue(),
						calcForm.getForm().findField('LIMIT1_TIME').getValue()); // 监听还款方式限制还款期限
					}
				}
			}]
		}, {
			layout : 'form',
			columnWidth : .25,
			items : [{
				xtype : 'combo',
				name : 'REPAY2',
				fieldLabel : '额度2还款方式',
				width : 180,
				mode : 'local',
				store : new Ext.data.Store(),
				triggerAction : 'all',
				displayField : 'value',
				valueField : 'key',
				editable : false,
				allowBlank : false,
				listeners : {
					blur : function() {
						repayToTimeFun(calcForm.getForm().findField('REPAY2').getValue(), 
						calcForm.getForm().findField('LIMIT2_TIME').getValue()); // 监听还款方式限制还款期限
					}
				}
			}]
		}]
	}, {
		layout : 'column', // 第六行
		items : [{
			layout : 'form',
			columnWidth : .25,
			items : [{
				xtype : 'numberfield',
				name : 'LIMIT1_TIME',
				fieldLabel : '额度1期限(月)<font color=red>*</font>',
				width : 180,
				minValue : 0,
				maxValue : 60,
				editable : false,
				allowBlank : false,
				listeners : {
					blur : function() {
						repayToTimeFun(calcForm.getForm().findField('REPAY1').getValue(),
						calcForm.getForm().findField('LIMIT1_TIME').getValue()); // 监听还款方式限制还款期限
					}
				}
			}]
		}, {
			layout : 'form',
			columnWidth : .25,
			items : [{
				xtype : 'numberfield',
				name : 'LIMIT2_TIME',
				fieldLabel : '额度2期限(月)',
				width : 180,
				minValue : 0,
				maxValue : 60,
				editable : false,
				allowBlank : false,
				listeners : { // 监听还款方式限制还款期限
					blur : function() {
						repayToTimeFun(calcForm.getForm().findField('REPAY2').getValue(), 
						calcForm.getForm().findField('LIMIT2_TIME').getValue());
					}
				}
			}]
		}]
	}, {
		layout : 'column', // 第七行
		items : [{
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'numberfield',
								name : 'LIMIT1_RATE',
								decimalPrecision: 4,
								fieldLabel : '额度1年利率(%)<font color=red>*</font>',
								width : 180,
								minValue : 0,
								editable : false,
								allowBlank : false
							}]
	}, {
		layout : 'form',
		columnWidth : .25,
		items : [{
			xtype : 'numberfield',
			name : 'LIMIT2_RATE',
			decimalPrecision: 4,
			fieldLabel : '额度2年利率(%)',
			width : 180,
			minValue : 0,
			editable : false,
			allowBlank : false
				}]
		}]
	}, {
		layout : 'column', // 第七行
		items : [{
		layout : 'form',
			columnWidth : .25,
			items : [{
						xtype : 'textfield',
						name : 'LIMIT1_REPAY',
						fieldLabel : '额度1每月还款额(元)',
						width : 180,
						disabled : true
			}]
		}, {
			layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'textfield',
								name : 'LIMIT2_REPAY',
								fieldLabel : '额度2每月还款额(元)',
								width : 180,
								disabled : true
					}]
			}]
	},{
		layout : 'column', // 第八行
		items : [{
		layout : 'form',
			columnWidth : .25,
			items : [{												
						xtype : 'textfield',
						name : 'LIMIT1_LEVEL',
						fieldLabel : '额度1需签核层级',
						width : 180,
						disabled : true	
			}]
		}, {
			layout : 'form',
					columnWidth : .25,
					items : [{								
								xtype : 'textfield',
								name : 'LIMIT2_LEVEL',
								fieldLabel : '额度2需签核层级',																															
								width : 180,
								disabled : true
					}]
			}]
	},{
		layout : 'column', // 第十行
		items : [{
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'textfield',
								name : 'AMOUNT_REPAY',
								fieldLabel : '总计每月还款额(元)',
								width : 180,
								disabled : true
							}]
				}, {
					layout : 'form',
					columnWidth : .25,
					items : [{
								xtype : 'textfield',
								name : 'AMOUNT_RATE',
								fieldLabel : '综合利率(%)',
								width : 180,
								disabled : true
							}]
				}]
	}, {
		layout : 'column', // 第十一行
		items : [{
					layout : 'form',
					columnWidth : .25,
					items : [{								
								xtype : 'textfield',
								name : 'FINAL_LEVEL',
								fieldLabel : '最终需签核层级',																
								width : 180,
								disabled : true
							}]
				}]
	}, {
		columnWidth : 1,
		layout : 'column', // 第十二行
		padding : '5px 260px 0px',
		items : [{
					xtype : 'button',
					text : '计算',
					width : 60,
					style : {
						marginRight : '10px'
					},
					handler : function() {						
						calc();											
					}
				}, {
					xtype : 'button',
					text : '重置',
					width : 60,
					style : {
						marginRight : '10px'
					},
					handler : function() {
						calcForm.getForm().reset();
					}
				}, {
					xtype : 'button',
					text : '打印预览',
					width : 60,
					handler : function() {
						printPreview();
					}
				}]
	}]
});

/**
 * 结果域扩展功能面板
 */
var customerView = [{
	/**
	 * 自定义计算器面板
	 */
	title : '计算器面板',
	hideTitle : true,
	layout : 'form',
	items : [calcForm, notesForm]
		// 新增改动
	}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法 注意：各组写在一个beforeviewshow下面
 * 
 * @param {}
 *          view
 * @return {Boolean}
 */
var beforeviewshow = function(view) {
	if (view._defaultTitle == '计算器面板') {
		calcForm.getForm().findField('LOAN').bindStore(findLookupByType('LOAN_TYPE'));
		calcForm.getForm().findField('PRICE').bindStore(findLookupByType('PRICE_GRADE'));
		calcForm.getForm().findField('REPAY1').bindStore(findLookupByType('REPAY_TYPE'));
		calcForm.getForm().findField('REPAY2').bindStore(findLookupByType('REPAY_TYPE'));
	} 
};

//定义全局变量,获取值后传递至打印预览页面
var FINAL_LEVEL;//最终需签核层级
var EMPOWER1_RATE;//额度1授权利率
var EMPOWER2_RATE;//额度2授权利率
var EMPOWER1_FTPRATE;//额度1FTP利率
var EMPOWER2_FTPRATE;//额度2FTP利率
var EMPOWER1_RMBRATE;//额度1RMB利率
var EMPOWER2_RMBRATE;//额度2RMB利率

/**
 * 实现打印预览功能
 */
var printPreview = function() {

	var cond = calcForm.getForm().getValues();
	var LOAN_PERCENT = calcForm.getForm().findField('LOAN_PERCENT').getValue();
	var LOANABLE_PERCENT = calcForm.getForm().findField('LOANABLE_PERCENT').getValue();
	var LIMIT1_REPAY = calcForm.getForm().findField('LIMIT1_REPAY').getValue();
	var LIMIT2 = strCut(calcForm.getForm().findField('LIMIT2').getValue());
	var LIMIT2_REPAY = calcForm.getForm().findField('LIMIT2_REPAY').getValue();
	var LIMIT1_LEVEL = calcForm.getForm().findField('LIMIT1_LEVEL').getValue();
	var LIMIT2_LEVEL = calcForm.getForm().findField('LIMIT2_LEVEL').getValue();
	var AMOUNT_RATE = calcForm.getForm().findField('AMOUNT_RATE').getValue();	

	var turl = '?LOAN_PRICE=' + cond.LOAN_PRICE + '&PLAN_APPLY='
			+ cond.PLAN_APPLY + '&LOAN=' + cond.LOAN + '&LOAN_PERCENT='
			+ LOAN_PERCENT + '&PRICE=' + cond.PRICE + '&LOANABLE_PERCENT='
			+ LOANABLE_PERCENT + '&LIMIT1=' + cond.LIMIT1 + '&REPAY1='
			+ cond.REPAY1 + '&LIMIT1_TIME=' + cond.LIMIT1_TIME
			+ '&LIMIT1_RATE=' + cond.LIMIT1_RATE + '&LIMIT1_REPAY='
			+ LIMIT1_REPAY + '&LIMIT2=' + LIMIT2 + '&REPAY2=' + cond.REPAY2
			+ '&LIMIT2_TIME=' + cond.LIMIT2_TIME + '&REPAY2=' + cond.REPAY2
			+ '&LIMIT2_RATE=' + cond.LIMIT2_RATE + '&LIMIT2_REPAY='
			+ LIMIT2_REPAY +'&LIMIT1_LEVEL='+LIMIT1_LEVEL+'&LIMIT2_LEVEL='
			+LIMIT2_LEVEL+'&EMPOWER1_RATE='+EMPOWER1_RATE+'&EMPOWER2_RATE='
			+EMPOWER2_RATE+'&EMPOWER1_FTPRATE='+EMPOWER1_FTPRATE+'&EMPOWER2_FTPRATE='
			+EMPOWER2_FTPRATE+'&EMPOWER1_RMBRATE='+EMPOWER1_RMBRATE+'&EMPOWER2_RMBRATE='
			+EMPOWER2_RMBRATE+'&AMOUNT_RATE=' + AMOUNT_RATE + '&FINAL_LEVEL=' + FINAL_LEVEL;
	var taskMgr = parent.Wlj?parent.Wlj.TaskMgr:undefined;
	var p = parent;
	for(var i=0;i<10 && !taskMgr;i++){
		p = p.parent;
		taskMgr = p.Wlj?p.Wlj.TaskMgr:undefined;
	}
	if(taskMgr.getTask('task_print_1')){
		taskMgr.getTask('task_print_1').close();
	}
	var tempApp = parent._APP ? parent._APP : parent.parent._APP;
	tempApp.openWindow({
				name : '打印预览',
				action : basepath
						+ '/contents/pages/wlj/printManager/printPreviewCalcPrice.jsp'+turl,
				resId : 'task_print_1',
				id : 'task_print_1',
				serviceObject : false
			});
}

/**
 * 添加计算函数
 */
var calc = function(){//额度每月还款额,额度需签核层级,总计每月还款额,综合利率,额度需签核层级
	 limitRepayFun(calcForm.getForm().findField('REPAY1').getValue(),calcForm.getForm().findField('LIMIT1').getValue(),calcForm.getForm().findField('LIMIT1_TIME').getValue(),calcForm.getForm().findField('LIMIT1_RATE').getValue(),1);//额度1每月还款
	 limitRepayFun(calcForm.getForm().findField('REPAY2').getValue(),calcForm.getForm().findField('LIMIT2').getValue(),calcForm.getForm().findField('LIMIT2_TIME').getValue(),calcForm.getForm().findField('LIMIT2_RATE').getValue(),2);//额度2每月还款
	 amountRepayFun();//总计每月还款额
	 amountRateFun(0);//综合利率	
	 powerLevelFun(calcForm.getForm().findField('LIMIT1_TIME').getValue(),calcForm.getForm().findField('LIMIT1_RATE').getValue(),1,calcForm.getForm().findField('LIMIT2_TIME').getValue(),calcForm.getForm().findField('LIMIT2_RATE').getValue(),2);//需签核层级
}


// 计算抵押成数
var loanPercentFun = function() {
	var loanPrice = calcForm.getForm().findField('LOAN_PRICE').getValue();// 抵押物价值(万元)(由用户输入)
	var planApply = calcForm.getForm().findField('PLAN_APPLY').getValue();// 拟申请金额(万元)(由用户输入)
	/*
	 * 计算抵押成数 公式:抵押成数 = 拟申请金额/抵押物价值
	 */
	if ((loanPrice != 0) && (planApply != 0)) {
		var loanPercent = planApply / loanPrice; // 计算抵押成数
		calcForm.getForm().findField('LOAN_PERCENT').setValue((loanPercent * 100).toFixed(2));// 反馈抵押成数
	}
}

// 计算额度2
var loanLimit2Fun = function() {
	var planApply = calcForm.getForm().findField('PLAN_APPLY').getValue();
	var loanLimit1 = calcForm.getForm().findField('LIMIT1').getValue();
	if (planApply != 0 && loanLimit1 != 0) {
		if (planApply >= loanLimit1) {
			var loanLIMIT2 = planApply - loanLimit1;
			calcForm.getForm().findField('LIMIT2').setValue(numFormat(loanLIMIT2,2));
		} else if (planApply < loanLimit1) {
			Ext.Msg.alert("提示", "拟申请额度应大于或等于额度1,请核查后重新输入");
			calcForm.getForm().findField('LIMIT1').setValue();
		}
	}
}

// 计算可抵押成数
var loanablePercentFun = function() {

	// 获得抵押物类型key值
	var loanKey = calcForm.getForm().findField('LOAN').getValue();
	// 获得价格对应评级key值
	var priceKey = calcForm.getForm().findField('PRICE').getValue();
	// 还款方式类型key值
	var repayKey1 = calcForm.getForm().findField('REPAY1').getValue();
	// 定义flag标记是否可匹配出可抵押成数
	var flag = false;
	// 定义可抵押成数
	var loanablePercent = 0.0;
	if (repayKey1 == 1) {
		if (priceKey == 1) {
			switch (loanKey) {
				case '1' :
					loanablePercent = 1.5;
					break;
				case '2' :
					loanablePercent = 1.0;
					break;
				case '3' :
				case '4' :
					loanablePercent = 0.8;
					break;
				case '5' :
				case '6' :
					loanablePercent = 0.6;
					break;
				case '7' :
				case '10' :
					loanablePercent = 0.5;
					break;
				case '8' :
				case '11' :
					loanablePercent = 0.4;
					break;
				case '9' :
				case '12' :
					loanablePercent = 0.3;
					break;
			}
		} else if (priceKey == 2) {
			switch (loanKey) {
				case '1' :
					loanablePercent = 1.2;
					break;
				case '2' :
					loanablePercent = 0.9;
					break;
				case '3' :
				case '4' :
					loanablePercent = 0.6;
					break;
				case '5' :
				case '6' :
				case '7' :
				case '10' :
					loanablePercent = 0.4;
					break;
				case '8' :
				case '11' :
					loanablePercent = 0.3;
					break;
				case '9' :
				case '12' :
					loanablePercent = 0.2;
					break;
			}
		} else {
			switch (loanKey) {
				case '1' :
					loanablePercent = 1.0;
					break;
				case '2' :
					loanablePercent = 0.8;
					break;
				case '3' :
				case '4' :
					loanablePercent = 0.4;
					break;
				case '5' :
				case '6' :
					loanablePercent = 0.3;
					break;
				case '7' :
				case '8' :
				case '9' :
				case '10' :
				case '11' :
				case '12' :
					flag = true;
					break;
			}
		}
	} else {
		if (priceKey == 1) {
			switch (loanKey) {
				case '1' :
					loanablePercent = 0.9;
					break;
				case '2' :
					loanablePercent = 0.8;
					break;
				case '3' :
					loanablePercent = 0.6;
					break;
				case '4' :
				case '6' :
					loanablePercent = 0.7;
					break;
				case '5' :
				case '7' :
				case '8' :
				case '9' :
				case '10' :
				case '11' :
				case '12' :
					flag = true;
					break;
			}
		} else if (priceKey == 2) {
			switch (loanKey) {
				case '1' :
					loanablePercent = 0.8;
					break;
				case '2' :
					loanablePercent = 0.7;
					break;
				case '3' :
					loanablePercent = 0.5;
					break;
				case '4' :
				case '6' :
					loanablePercent = 0.6;
					break;
				case '5' :
				case '7' :
				case '8' :
				case '9' :
				case '10' :
				case '11' :
				case '12' :
					flag = true;
					break;
			}
		} else {
			switch (loanKey) {
				case '1' :
					loanablePercent = 0.7;
					break;
				case '2' :
					loanablePercent = 0.6;
					break;
				case '3' :
					loanablePercent = 0.4;
					break;
				case '4' :
				case '6' :
					loanablePercent = 0.5;
					break;
				case '5' :
				case '7' :
				case '8' :
				case '9' :
				case '10' :
				case '11' :
				case '12' :
					flag = true;
					break;
			}
		}
	}
	// 反馈可抵押成数
	if ((repayKey1 != 0) && (priceKey != 0) && (loanKey != 0)) {
		if (flag) {
			Ext.Msg.alert('提示', '不接受此类抵押及其还款方式');
			calcForm.getForm().findField('LOAN').setValue();
			calcForm.getForm().findField('PRICE').setValue();
			calcForm.getForm().findField('REPAY1').setValue();
			calcForm.getForm().findField('LOANABLE_PERCENT').setValue();
		} else {
			var loanPercent = calcForm.getForm().findField('PLAN_APPLY').getValue()/ calcForm.getForm().findField('LOAN_PRICE').getValue();// 抵押成数
			calcForm.getForm().findField('LOANABLE_PERCENT').setValue((loanablePercent * 100).toFixed(2));//可抵押成数			
		}
	}
}

// 根据还款方式,限制还款期限(还款方式,还款期限)
var repayToTimeFun = function(repayType, limitTime) {
	if (repayType == 1) {
		if (limitTime > 60) {
			Ext.Msg.alert("提示", "等额本息期限应小于或等于60个月,请重新输入");
			// calcForm.getForm().findField('LIMIT2_TIME').setValue();
		}
	} else if (repayType == 2) {
		if (limitTime > 12) {
			Ext.Msg.alert("提示", "到期还本期限应小于或等于12个月,请重新输入");
			// calcForm.getForm().findField('LIMIT2_TIME').setValue();
		}
	}
}

/*计算每月还款金额(还款方式,额度,期限,利率(年),额度类型) 
 * 还款方式1.等额本息a*[i*(1+i)^n]/[(1+i)^n-1],i为月利率
 * 2.按月付息,到期还本 a*i/365*30,i为年利率
 */
var limitRepayFun = function(repayType,limitN,limitTime,limitRateB,limitType){
	var limitRepay;
	if(limitType == 2){
		limitN = Number(strCut(limitN));
	}
	if((repayType!=0)&&(limitN!=0)&&(limitRateB!=0)&&(limitTime!=0)){
		var limit = limitN * 10000;//金额由万转换元
		var limitRate = limitRateB * 0.01;//利率转换为小数
		if(repayType == 1){//等额本息
			var rate = limitRate/12;//年利率转换月利率
			limitRepay = limit * (rate * Math.pow((1 + rate),limitTime))/(Math.pow((1 + rate),limitTime) - 1);      
		} else if(repayType == 2){//每月付息,到期还本
			if(limitTime == 1){
				limitRepay = (limit * limitRate / 365 * 30) + limit;
			} else{
				limitRepay = limit * limitRate / 365 * 30;
			}
		}
		var limitRepayF = numFormat(limitRepay,2);
		if(limitType == 1){//反馈额度1每月还款额
			calcForm.getForm().findField('LIMIT1_REPAY').setValue(limitRepayF);
		} else if(limitType == 2){//反馈额度2每月还款额
			calcForm.getForm().findField('LIMIT2_REPAY').setValue(limitRepayF);
		}
	}				
}

//计算总计每月还款额
var amountRepayFun = function(){

	var limit1RepayF = calcForm.getForm().findField('LIMIT1_REPAY').getValue();
	var limit2RepayF = calcForm.getForm().findField('LIMIT2_REPAY').getValue();
	var limit1Repay = Number(strCut(limit1RepayF));
	var limit2Repay = Number(strCut(limit2RepayF));
	if((limit1Repay != 0)||(limit2Repay != 0)){
		var amountRepay = limit1Repay + limit2Repay;
		var amountRepayF = numFormat(amountRepay,2);
		calcForm.getForm().findField('AMOUNT_REPAY').setValue(amountRepayF);// 反馈总计每月还款额
	}
}

//计算综合年利率
var amountRateFun = function(flag){
	
	var planApply = calcForm.getForm().findField('PLAN_APPLY').getValue();// 拟申请金额(万元)(由用户输入)	
	var limit1 = calcForm.getForm().findField('LIMIT1').getValue();// 额度1金额(万元)(由用户输入)
	var limit2F = calcForm.getForm().findField('LIMIT2').getValue();// 额度2金额(万元)(计算得出,千位符)
	var limit2 = Number(strCut(limit2F));//转为数字
	var limit1Rate = calcForm.getForm().findField('LIMIT1_RATE').getValue();// 额度1利率(由用户输入)	
	var limit2Rate = calcForm.getForm().findField('LIMIT2_RATE').getValue();// 额度2利率(由用户输入)
	var amountRate;
	if(flag == 0){
		if(limit2 == 0 || limit2Rate == 0){
			amountRate = limit1Rate;
		}else{
			amountRate = (limit1 / planApply * limit1Rate) + (limit2 / planApply * limit2Rate);	
		}		
	} else if(flag == 1){//额度1利率过低无授权等级,重算综合利率
		amountRate = limit2Rate;
	} else if(flag == 2){//额度2利率过低无授权等级,重算综合利率
		amountRate = limit1Rate;
	}
	calcForm.getForm().findField('AMOUNT_RATE').setValue(amountRate.toFixed(4));	// 反馈综合利率
}

//计算额度需签核层级(价格对应评级,利率(年),额度类型)--最终得出签核层级
var powerLevelFun = function(limit1Time,limit1RateN,limit1Type,limit2Time,limit2RateN,limit2Type){
	
	var priceLevel = calcForm.getForm().findField('PRICE').getValue();
	var powerLevel1;
	var empowerRate1;
	var powerLevel2;
	var empowerRate2;
	var limit1Rate = Number(limit1RateN);
	var limit2Rate = Number(limit2RateN);
	
	if((limit1Time != 0)&&(limit1Rate != 0)&&(limit1Type != 0)&&(priceLevel != null)){//额度1
		
		var limitTY = limit1Time/12;     //期限由月转年			
			Ext.Ajax.request({   //获取rate
				url : basepath + '/calcPriceQueryFtpRate!getFtpRate.json',
				method : 'GET',
				dataType : 'json',
				params : {limitTY : limitTY},
				success : function(response){
					var resultInfo = Ext.util.JSON.decode(response.responseText);
			 		var ftpRate1 = Number(resultInfo.json.ftpRate);
				
					if(priceLevel == '1'){//价格评级A
						if((limit1Rate > (ftpRate1 * 10000 * 1.3 /10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.3 /10000))){	//权限等级1
							powerLevel1 = 1;
							empowerRate1 = ftpRate1 * 1.3;
						} else if ((limit1Rate > (ftpRate1 * 10000 * 1.25 /10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.25 /10000))){//权限等级2
							powerLevel1 = 2;
							empowerRate1 = ftpRate1 * 1.25;
						}else if ((limit1Rate > (ftpRate1 * 10000 * 1.2 /10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.2 /10000))){//权限等级3
							powerLevel1 = 3;
							empowerRate1 = ftpRate1 * 1.2;
						} else {
							powerLevel1 = 0;
							empowerRate1 = 0;
						}
					} else if(priceLevel == '2'){//价格评级B
						if((limit1Rate > (ftpRate1 * 10000 * 1.35 /10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.35 /10000))){	//权限等级1
							powerLevel1 = 1;
							empowerRate1 = ftpRate1 * 1.35;
						} else if ((limit1Rate > (ftpRate1 * 10000 * 1.3 /10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.3 /10000))){//权限等级2
							powerLevel1 = 2;
							empowerRate1 = ftpRate1 * 1.3;
						}else if ((limit1Rate > (ftpRate1 * 10000 * 1.25 /10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.25 /10000))){//权限等级3
							powerLevel1 = 3;
							empowerRate1 = ftpRate1 * 1.25;
						} else {
							powerLevel1 = 0;
							empowerRate1 = 0;
						}
					}else if(priceLevel == '3'){//价格评级C
						if((limit1Rate > (ftpRate1 * 10000 * 1.4/10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.4/10000))){	//权限等级1
							powerLevel1 = 1;
							empowerRate1 = ftpRate1 * 1.4;
						} else if ((limit1Rate > (ftpRate1 * 10000 * 1.35/10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.35/10000))){//权限等级2
							powerLevel1 = 2;
							empowerRate1 = ftpRate1 * 1.35;
						}else if ((limit1Rate > (ftpRate1 * 10000 * 1.3/10000)) || (limit1Rate == (ftpRate1 * 10000 * 1.3/10000))){//权限等级3
							powerLevel1 = 3;
							empowerRate1 = ftpRate1 * 1.3;
						} else {
							powerLevel1 = 0;
							empowerRate1 = 0;
						}
					}
					
					EMPOWER1_FTPRATE = ftpRate1.toFixed(4);//额度1授ftpRate
					EMPOWER1_RATE = empowerRate1.toFixed(4);//额度1授权利率标准
					switch(powerLevel1){//额度1权限层级
						case 3 :
							calcForm.getForm().findField('LIMIT1_LEVEL').setValue('权限等级Ⅲ');
							break;
						case 2 :
							calcForm.getForm().findField('LIMIT1_LEVEL').setValue('权限等级Ⅱ');
							break;
						case 1 :
							calcForm.getForm().findField('LIMIT1_LEVEL').setValue('权限等级I');
							break;
						case 0 :
							Ext.Msg.alert("提示","额度1利率过低无授权等级"); //超出权限范围
							calcForm.getForm().findField('LIMIT1_LEVEL').setValue();
							calcForm.getForm().findField('LIMIT1_REPAY').setValue();
							amountRepayFun();//重新计算每月总还款额
							amountRateFun(1);//重新计算综合利率
							break;
					}
					
					finalPowerLevelFun();//计算最终续签核层级
					
					Ext.Ajax.request({
						url : basepath + '/calcPriceQueryRmbRate!getRmbRate.json',
						method : 'GET',
						dataType : 'json',
						params : {limitTY : limitTY},
						success : function(response){
							var resultInfo = Ext.util.JSON.decode(response.responseText);
							var rmbRate1 = resultInfo.json.rmbRate;//额度1RmbRate
							EMPOWER1_RMBRATE = rmbRate1.toFixed(4);
						},failure : function(response){
							Ext.Msg.alert('提示','操作失败!');
						}
					});
					
				},failure : function(response) {
			   		Ext.Msg.alert('提示', '操作失败!');
				}
			});
																
	}
	
	if((limit2Time!=0)&&(limit2Rate!=0)&&(limit2Type!=0)&&(priceLevel != null)){//额度2
		
		var limitTY = limit2Time/12;     //期限由月转年			
		Ext.Ajax.request({   //获取rate
			url : basepath + '/calcPriceQueryFtpRate!getFtpRate.json',
			method : 'GET',
			dataType : 'json',
			params : {limitTY : limitTY},
			success : function(response){
				var resultInfo = Ext.util.JSON.decode(response.responseText);
		 		var ftpRate2 = Number(resultInfo.json.ftpRate);
				
				if(priceLevel == '1'){   //价格评级A
					if((limit2Rate > (ftpRate2 * 10000 * 3.1 /10000)) || (limit2Rate == (ftpRate2 * 10000 * 3.1 /10000))){	//权限等级1
						powerLevel2 = 1;
						empowerRate2 = ftpRate2 * 3.1;
					} else if ((limit2Rate > (ftpRate2 * 10000 * 2.9/10000)) || (limit2Rate == (ftpRate2 * 10000 * 2.9/10000))){//权限等级2
						powerLevel2 = 2;
						empowerRate2 = ftpRate2 * 2.9;
					}else if ((limit2Rate > (ftpRate2 * 10000 * 2.7/10000)) || (limit2Rate == (ftpRate2 * 10000 * 2.7/10000))){//权限等级3
						powerLevel2 = 3;
						empowerRate2 = ftpRate2 * 2.7;
					} else {
						powerLevel2 = 0;
						empowerRate2 = 0;
					}
				}else if(priceLevel == '2'){   //价格评级B
					if((limit2Rate > (ftpRate2 * 10000 * 3.3 /10000)) || (limit2Rate == (ftpRate2 * 10000 * 3.3 /10000))){	//权限等级1
						powerLevel2 = 1;
						empowerRate2 = ftpRate2 * 3.3;
					} else if ((limit2Rate > (ftpRate2 * 10000 * 3.1/10000)) || (limit2Rate == (ftpRate2 * 10000 * 3.1/10000))){//权限等级2
						powerLevel2 = 2;
						empowerRate2 = ftpRate2 * 3.1;
					}else if ((limit2Rate > (ftpRate2 * 10000 * 2.9/10000)) || (limit2Rate == (ftpRate2 * 10000 * 2.9/10000))){//权限等级3
						powerLevel2 = 3;
						empowerRate2 = ftpRate2 * 2.9;
					} else {
						powerLevel2 = 0;
						empowerRate2 = 0;
					}
				}else if(priceLevel == '3'){   //价格评级C
					if((limit2Rate > (ftpRate2 * 10000 * 3.5/10000)) || (limit2Rate == (ftpRate2 * 10000 * 3.5/10000))){	//权限等级1
						powerLevel2 = 1;
						empowerRate2 = ftpRate2 * 3.5;
					} else if ((limit2Rate > (ftpRate2  * 10000 * 3.3/10000)) || (limit2Rate == (ftpRate2  * 10000 * 3.3/10000))){//权限等级2
						powerLevel2 = 2;
						empowerRate2 = ftpRate2 * 3.3;
					}else if ((limit2Rate > (ftpRate2 * 10000 * 3.1/10000)) || (limit2Rate == (ftpRate2 * 10000 * 3.1/10000))){//权限等级3
						powerLevel2 = 3;
						empowerRate2 = ftpRate2 * 3.1;
					} else {
						powerLevel2 = 0;
						empowerRate2 = 0;
					}
				}
				
				EMPOWER2_FTPRATE = ftpRate2.toFixed(4);//额度2ftpRate
				EMPOWER2_RATE = empowerRate2.toFixed(4);//额度2授权利率标准
				switch(powerLevel2){//额度2权限层级
					case 3 :
						calcForm.getForm().findField('LIMIT2_LEVEL').setValue('权限等级Ⅲ');
						break;
					case 2 :
						calcForm.getForm().findField('LIMIT2_LEVEL').setValue('权限等级Ⅱ');
						break;
					case 1 :
						calcForm.getForm().findField('LIMIT2_LEVEL').setValue('权限等级I');
						break;
					case 0 :
						Ext.Msg.alert("提示","额度2利率过低无授权等级"); //超出权限范围
						calcForm.getForm().findField('LIMIT2_LEVEL').setValue();
						calcForm.getForm().findField('LIMIT2_REPAY').setValue();
						amountRepayFun();//重新计算每月总还款额
						amountRateFun(2);//重新计算综合利率
						break;
				}
				
				finalPowerLevelFun();
				
				Ext.Ajax.request({
					url : basepath + '/calcPriceQueryRmbRate!getRmbRate.json',
					method : 'GET',
					dataType : 'json',
					params : {limitTY : limitTY},
					success : function(response){
						var resultInfo = Ext.util.JSON.decode(response.responseText);
						var rmbRate2 = resultInfo.json.rmbRate;//额度2RmbRate
						EMPOWER2_RMBRATE = rmbRate2.toFixed(4);
					},failure : function(response){
						Ext.Msg.alert('提示','操作失败!');
					}
				});
				
			},failure : function(response) {
		   		Ext.Msg.alert('提示', '操作失败!');
			}
		});		
	}
}

//计算最终需签核层级
var finalPowerLevelFun = function(){
	var level1 = calcForm.getForm().findField('LIMIT1_LEVEL').getValue();
	var level2 = calcForm.getForm().findField('LIMIT2_LEVEL').getValue();
	if((level1 != null) || (level2 != null)){
		if((level1 == '权限等级Ⅲ') || (level2 =='权限等级Ⅲ')){
			calcForm.getForm().findField('FINAL_LEVEL').setValue('权限等级Ⅲ');
			FINAL_LEVEL = '权限等级Ⅲ-业务条线主管';	
		} else if((level1 == '权限等级Ⅱ') || (level2 == '权限等级Ⅱ')){
			calcForm.getForm().findField('FINAL_LEVEL').setValue('权限等级Ⅱ');
			FINAL_LEVEL = '权限等级Ⅱ-营业单位行长';	
		} else if((level1 == '权限等级I') || (level2 == '权限等级I')){
			calcForm.getForm().findField('FINAL_LEVEL').setValue('权限等级I');
			FINAL_LEVEL = '权限等级I-团队主管';	
		}
	}
}

 //数据格式化
var numFormat = function(s,n){ 
	n = (n > 0 && n < 20 ? n : 2); 
	s = parseFloat((s + '').replace('/[^\d\.-]/g', '')).toFixed(n) + ''; 
	var l = s.split('.')[0].split('').reverse(), r = s.split('.')[1]; 
	t = ''; 
	for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : ''); 
	} 
	return t.split('').reverse().join('') + '.' + r; 
} 

//数据字符串截取
var strCut = function(s){

	var str = [];
	str = s.split(',');
	var t = '';
	var i;
	for(i = 0;i<str.length;i++){
		t += str[i];
	}
	return t;
}

