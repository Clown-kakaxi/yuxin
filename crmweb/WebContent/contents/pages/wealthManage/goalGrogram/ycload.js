var custId;
var i = 1;
function load(custId) {
	var chart1 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart2 = new FusionCharts(basepath + "/FusionCharts/Pie3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart3 = new FusionCharts(basepath + "/FusionCharts/Column3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	var chart4 = new FusionCharts(basepath + "/FusionCharts/Column3D.swf",
			"chartId" + i, "90%", "320", "0", "0");
	i += 1;
	Ext.Ajax
			.request({
				url : basepath + '/FinancialAnalysis!assetXml.json?customerId='
						+ custId,
				method : 'GET',
				success : function(response) {
					var dataXml1 = Ext.util.JSON.decode(response.responseText).dataXml1;
					var dataXml2 = Ext.util.JSON.decode(response.responseText).dataXml2;
					var dataXml3 = Ext.util.JSON.decode(response.responseText).dataXml3;
					var dataXml4 = Ext.util.JSON.decode(response.responseText).dataXml4;
					var r = Ext.util.JSON.decode(response.responseText).valueMap;
					var r2 = new Ext.data.Record(r);
					tab_3.getForm().loadRecord(r2);
					chart1.setDataXML(dataXml1);
					chart2.setDataXML(dataXml2);
					chart3.setDataXML(dataXml3);
					chart4.setDataXML(dataXml4);
					chart1.render("chartdiv1");
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
					;
					var bankAssetSum11 = Ext.getCmp('bankAssetSum1').getValue();
					if (bankAssetSum11 == '') {
						Ext.getCmp('bankAssetSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(bankAssetSum11);
						Ext.getCmp('bankAssetSum1').setValue(money);
					}
					;
					var bankDebtSum11 = Ext.getCmp('bankDebtSum1').getValue();
					if (bankDebtSum11 == '') {
						Ext.getCmp('bankDebtSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(bankDebtSum11);
						Ext.getCmp('bankDebtSum1').setValue(money);
					}
					;
					var bankNetAsset11 = Ext.getCmp('bankNetAsset1').getValue();
					if (bankNetAsset11 == '') {
						Ext.getCmp('bankNetAsset1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(bankNetAsset11);
						Ext.getCmp('bankNetAsset1').setValue(money);
					}
					;
					var otherAssetSum11 = Ext.getCmp('otherAssetSum1')
							.getValue();
					if (otherAssetSum11 == '') {
						Ext.getCmp('otherAssetSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(otherAssetSum11);
						Ext.getCmp('otherAssetSum1').setValue(money);
					}
					;
					var otherDebtSum11 = Ext.getCmp('otherDebtSum1').getValue();
					if (otherAssetSum11 == '') {
						Ext.getCmp('otherDebtSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(otherAssetSum11);
						Ext.getCmp('otherDebtSum1').setValue(money);
					}
					;
					var otherNetAsset11 = Ext.getCmp('otherNetAsset1')
							.getValue();
					if (otherNetAsset11 == '') {
						Ext.getCmp('otherNetAsset1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(otherNetAsset11);
						Ext.getCmp('otherNetAsset1').setValue(money);
					}
					;
					var anotherAssetSum11 = Ext.getCmp('anotherAssetSum1')
							.getValue();
					if (anotherAssetSum11 == '') {
						Ext.getCmp('anotherAssetSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(anotherAssetSum11);
						Ext.getCmp('anotherAssetSum1').setValue(money);
					}
					;
					var anotherDebtSum11 = Ext.getCmp('anotherDebtSum1')
							.getValue();
					if (anotherDebtSum11 == '') {
						Ext.getCmp('anotherDebtSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(anotherDebtSum11);
						Ext.getCmp('anotherDebtSum1').setValue(money);
					}
					;
					var anotherNetAsset11 = Ext.getCmp('anotherNetAsset1')
							.getValue();
					if (anotherNetAsset11 == '') {
						Ext.getCmp('anotherNetAsset1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(anotherNetAsset11);
						Ext.getCmp('anotherNetAsset1').setValue(money);
					}
					;
					var monthIn11 = Ext.getCmp('monthIn1').getValue();
					if (monthIn11 == '') {
						Ext.getCmp('monthIn1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthIn11);
						Ext.getCmp('monthIn1').setValue(money);
					}
					;
					var monthOut11 = Ext.getCmp('monthOut1').getValue();
					if (monthOut11 == '') {
						Ext.getCmp('monthOut1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthOut11);
						Ext.getCmp('monthOut1').setValue(money);
					}
					;
					var monthNet11 = Ext.getCmp('monthNet1').getValue();
					if (monthNet11 == '') {
						Ext.getCmp('monthNet1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(monthNet11);
						Ext.getCmp('monthNet1').setValue(money);
					}
					;
					var debtSum11 = Ext.getCmp('debtSum1').getValue();
					if (debtSum11 == '') {
						Ext.getCmp('debtSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(debtSum11);
						Ext.getCmp('debtSum1').setValue(money);
					}
					;
					var assetSum11 = Ext.getCmp('assetSum1').getValue();
					if (assetSum11 == '') {
						Ext.getCmp('assetSum1').setValue('');
					} else {
						var money = Mis.Ext.FormatCnMoney(assetSum11);
						Ext.getCmp('assetSum1').setValue(money);
					}
					;
					// 添加财务分析界面客户本行资产负债金额格式修改为小数点后2位

				},
				failure : function(response) {
				}
			});

}