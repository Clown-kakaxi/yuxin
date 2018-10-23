/**
*@description 360客户视图对私 投资信息 -- 客户财务分析信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
	'/contents/pages/fusionChart/tool/FusionCharts.js'
]);
var i = 1;
var url =  basepath + '/FinancialAnalysis!findFinIndex.json';
var fields = [{name:'CODE',text:'',hidden:true}];

var char_set_1 = new Ext.form.FieldSet( {
	height : 350,
	title : '客户本行资产负债信息',
	items : {
		region : 'center',
		id : 'center-panel',
		layout : 'fit',
		html : '<div id="chartdiv1"></div>'

	}
});

//引入自定义函数
Ext.ns('Mis.Ext');
Mis.Ext.FormatCnMoney = function(v) {
	return '￥'+Ext.util.Format.number(v,'0,000.00');
};

var char_set_1 = new Ext.form.FieldSet( {
	height : 350,
	title : '客户本行资产负债信息',
	items : {
		region : 'center',
		id : 'center-panel',
		layout : 'fit',
		html : '<div id="chartdiv1"></div>'

	}
});

var char_set_2 = new Ext.form.FieldSet( {
	height : 350,
	title : '客户他行资产负债信息',
	items : {
		region : 'center',
		id : 'center-panel2',
		layout : 'fit',
		html : '<div id="chartdiv2"></div>'
	}
});

var char_set_3 = new Ext.form.FieldSet( {
	title : '客户其他资产负债信息',
	height : 350,
	items : {
		region : 'center',
		id : 'center-panel3',
		layout : 'fit',
		html : '<div id="chartdiv3"></div>'
	}
});

// 折线
var char_set_4 = new Ext.form.FieldSet( {
	title : '客户家庭月度收支信息',

	height : 350,
	items : {
		region : 'center',
		id : 'center-panel4',
		layout : 'fit',
		html : '<div id="chartdiv4"></div>'
	}
});

var char_set_5 = new Ext.form.FieldSet( {
	title : '客户本行资产负债（元）',
	labelWidth : 80,
	anchor : '90%',
	items : [ {
		width : 100,
		fieldLabel : '本行资产合计',
		name : 'bankAssetSum',
		id : 'bankAssetSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '95%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '本行负债合计',
		name : 'bankDebtSum',
		id : 'bankDebtSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '95%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '本行净资产',
		name : 'bankNetAsset',
		id : 'bankNetAsset1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '95%',
		viewFn: money('0,000.00'),
		disabled : true
	} ]
});

var char_set_6 = new Ext.form.FieldSet( {
	title : '客户他行资产负债（元）',
	labelWidth : 80,
	anchor : '90%',
	items : [ {
		width : 100,
		fieldLabel : '他行资产合计',
		name : 'otherAssetSum',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		id : 'otherAssetSum1',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '他行负债合计',
		name : 'otherDebtSum',
		id : 'otherDebtSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '他行净资产',
		name : 'otherNetAsset',
		id : 'otherNetAsset1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	} ]
});

var char_set_7 = new Ext.form.FieldSet( {
	title : '客户其他资产负债（元）',
	labelWidth : 80,
	anchor : '90%',
	items : [ {
		width : 100,
		fieldLabel : '其他资产合计',
		name : 'anotherAssetSum',
		id : 'anotherAssetSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '其他负债合计',
		name : 'anotherDebtSum',
		id : 'anotherDebtSum1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '其他净资产',
		name : 'anotherNetAsset',
		id : 'anotherNetAsset1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	} ]
});

var char_set_8 = new Ext.form.FieldSet( {
	title : '月度收支信息（元）',
	labelWidth : 80,
	items : [ {
		width : 100,
		fieldLabel : '月度总收入',
		name : 'monthIn',
		id : 'monthIn1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '月度总支出',
		name : 'monthOut',
		id : 'monthOut1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	}, {
		width : 100,
		fieldLabel : '盈余',
		name : 'monthNet',
		id : 'monthNet1',
		xtype : 'textfield', // 设置为数字输入框类型
		labelStyle : 'text-align:right;',
		anchor : '90%',
		viewFn: money('0,000.00'),
		disabled : true
	} ]
});

var char_set_9 = new Ext.form.FieldSet( {
	title : '客户资产汇总（元）',
	layout : 'column',
	anchor : '99%',
	items : [ {
		columnWidth : 1 / 3,
		layout : 'form',
		items : [ {
			fieldLabel : '客户总资产',
			name : 'assetSum',
			id : 'assetSum1',
			xtype : 'textfield', // 设置为数字输入框类型
			labelStyle : 'text-align:right;',
			anchor : '90%',
			viewFn: money('0,000.00'),
			disabled : true
		} ]
	}, {
		columnWidth : 1 / 3,
		layout : 'form',
		items : [ {
			fieldLabel : '客户总负债资产',
			name : 'debtSum',
			id : 'debtSum1',
			xtype : 'textfield', // 设置为数字输入框类型
			labelStyle : 'text-align:right;',
			anchor : '90%',
			viewFn: money('0,000.00'),
			disabled : true
		} ]
	}, {
		columnWidth : 1 / 3,
		layout : 'form',
		items : [ {
			fieldLabel : '净资产',
			name : 'newAsset',
			id : 'newAsset1',
			xtype : 'textfield', // 设置为数字输入框类型
			labelStyle : 'text-align:right;',
			anchor : '90%',
			viewFn: money('0,000.00'),
			disabled : true
		} ]
	} ]
});

var load = function(custId) {//load方法
	var chart1 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart2 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart3 = new FusionCharts(basepath
			+ "/FusionCharts/Column3D.swf", "chartId" + i, "90%",
			"320", "0", "0");
	i += 1;
	var chart4 = new FusionCharts(basepath
			+ "/FusionCharts/Column3D.swf", "chartId" + i, "90%",
			"320", "0", "0");
	i += 1;
	Ext.Ajax
			.request( {
				url : basepath
						+ '/FinancialAnalysis!assetXml.json?customerId='
						+ custId,
				method : 'GET',
				success : function(response) {
					var dataXml1 = Ext.util.JSON
							.decode(response.responseText).dataXml1;//data小毛驴是从这里进来的
					var dataXml2 = Ext.util.JSON
							.decode(response.responseText).dataXml2;
					var dataXml3 = Ext.util.JSON
							.decode(response.responseText).dataXml3;
					var dataXml4 = Ext.util.JSON
							.decode(response.responseText).dataXml4;
					var r = Ext.util.JSON.decode(response.responseText).valueMap;//？可能是json..可以被record接收
					var r2 = new Ext.data.Record(r);
					tab_3.getForm().loadRecord(r2);//r2前往tab_3 视图
					chart1.setDataXML(dataXml1);//设值  设置数据
					chart2.setDataXML(dataXml2);
					chart3.setDataXML(dataXml3);
					chart4.setDataXML(dataXml4);
					chart1.render("chartdiv1");//能图显  在相应位置
					chart2.render("chartdiv2");
					chart3.render("chartdiv3");
					chart4.render("chartdiv4");
					// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位
					var newAsset11 = Ext.getCmp('newAsset1').getValue();
					if (newAsset11 == '') {
						Ext.getCmp('newAsset1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(newAsset11);
						Ext.getCmp('newAsset1').setValue(money);
					}
					var bankAssetSum11 = Ext.getCmp('bankAssetSum1')
							.getValue();
					if (bankAssetSum11 == '') {
						Ext.getCmp('bankAssetSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(bankAssetSum11);
						Ext.getCmp('bankAssetSum1').setValue(money);
					}
					var bankDebtSum11 = Ext.getCmp('bankDebtSum1')
							.getValue();
					if (bankDebtSum11 == '') {
						Ext.getCmp('bankDebtSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(bankDebtSum11);
						Ext.getCmp('bankDebtSum1').setValue(money);
					}
					var bankNetAsset11 = Ext.getCmp('bankNetAsset1')
							.getValue();
					if (bankNetAsset11 == '') {
						Ext.getCmp('bankNetAsset1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(bankNetAsset11);
						Ext.getCmp('bankNetAsset1').setValue(money);
					}
					var otherAssetSum11 = Ext.getCmp('otherAssetSum1')
							.getValue();
					if (otherAssetSum11 == '') {
						Ext.getCmp('otherAssetSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(otherAssetSum11);
						Ext.getCmp('otherAssetSum1').setValue(money);
					}
					var otherDebtSum11 = Ext.getCmp('otherDebtSum1')
							.getValue();
					if (otherAssetSum11 == '') {
						Ext.getCmp('otherDebtSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(otherAssetSum11);
						Ext.getCmp('otherDebtSum1').setValue(money);
					}
					var otherNetAsset11 = Ext.getCmp('otherNetAsset1')
							.getValue();
					if (otherNetAsset11 == '') {
						Ext.getCmp('otherNetAsset1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(otherNetAsset11);
						Ext.getCmp('otherNetAsset1').setValue(money);
					}
					var anotherAssetSum11 = Ext.getCmp(
							'anotherAssetSum1').getValue();
					if (anotherAssetSum11 == '') {
						Ext.getCmp('anotherAssetSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(anotherAssetSum11);
						Ext.getCmp('anotherAssetSum1').setValue(money);
					}
					var anotherDebtSum11 = Ext
							.getCmp('anotherDebtSum1').getValue();
					if (anotherDebtSum11 == '') {
						Ext.getCmp('anotherDebtSum1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(anotherDebtSum11);
						Ext.getCmp('anotherDebtSum1').setValue(money);
					}
					var anotherNetAsset11 = Ext.getCmp(
							'anotherNetAsset1').getValue();
					if (anotherNetAsset11 == '') {
						Ext.getCmp('anotherNetAsset1').setValue('');
					} else {
						var money = Mis.Ext
								.FormatCnMoney(anotherNetAsset11);
						Ext.getCmp('anotherNetAsset1').setValue(money);
					}
					var monthIn11 = Ext.getCmp('monthIn1').getValue();
					if (monthIn11 == '') {
						Ext.getCmp('monthIn1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthIn11);
						Ext.getCmp('monthIn1').setValue(money);
					}
					var monthOut11 = Ext.getCmp('monthOut1').getValue();
					if (monthOut11 == '') {
						Ext.getCmp('monthOut1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthOut11);
						Ext.getCmp('monthOut1').setValue(money);
					}
					var monthNet11 = Ext.getCmp('monthNet1').getValue();
					if (monthNet11 == '') {
						Ext.getCmp('monthNet1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthNet11);
						Ext.getCmp('monthNet1').setValue(money);
					}
					var debtSum11 = Ext.getCmp('debtSum1').getValue();
					if (debtSum11 == '') {
						Ext.getCmp('debtSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(debtSum11);
						Ext.getCmp('debtSum1').setValue(money);
					}
					var assetSum11 = Ext.getCmp('assetSum1').getValue();
					if (assetSum11 == '') {
						Ext.getCmp('assetSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(assetSum11);
						Ext.getCmp('assetSum1').setValue(money);
					}
					// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位
				},
				failure : function(response) {
				}
			});
};

var tab_3 = new Ext.form.FormPanel( {
//	title : '客户资产负债信息（单位：元）',
	overflow : 'auto',
	items : [ {
		layout : 'column',
		anchor : '99%',
		items : [ {
			layout : 'form',
			columnWidth : 0.5,
			items : [ char_set_1, char_set_3, {
				layout : 'column',
				items : [ {
					columnWidth : 0.5,
					items : [ char_set_5 ]
				}, {
					columnWidth : 0.5,
					items : [ char_set_6 ]
				} ]

			} ]
		}, {
			layout : 'form',
			columnWidth : 0.5,
			items : [ char_set_2, char_set_4, {
				layout : 'column',
				items : [ {
					columnWidth : 0.5,
					items : [ char_set_7 ]
				}, {
					columnWidth : 0.5,
					items : [ char_set_8 ]
				} ]

			} ]
		} ]
	}, char_set_9 ]
});
var tabs = new Ext.TabPanel( {
	activeTab : 0,
	items : [ {
		title : '客户资产负债信息（单位：元）',
		tabWidth:'100%',
		items : [ tab_3 ]
	}],
	listeners : {
		'tabchange' : function() {
			load(_custId);
		}
	}
});
// 页面视图
var viewport = new Ext.Viewport({
	layout : 'fit',
	defaults : {
		overflow : 'auto',
		autoScroll : true
	},
	items : [{
		items:[tabs]
	} ]
});
