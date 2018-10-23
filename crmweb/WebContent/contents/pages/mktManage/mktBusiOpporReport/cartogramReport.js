/*
 * 商机管理-商机达成分析-统计图表JS，wzy，2013-04-02
 */
Ext.onReady(function() {
	// 定义图片容器宽度和高度
	var height = document.documentElement.clientHeight - 10;
	var width = document.body.clientWidth;

	// 显示图形
	displayCartogram("");

	// 查询图形
	function displayCartogram(paraJson) {
		var swfUrl = basepath + "/FusionCharts/Pie3D.swf";
		var swfUrl_3 = basepath + "/FusionCharts/MSLine.swf";
		var chart_1 = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0",
				"0");
		var chart_2 = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0",
				"0");
		var chart_3 = new FusionCharts(swfUrl_3, "ChartId", "100%", "100%",
				"0", "0");
		var queryUrl = basepath + '/mktBusiOpporCartogramQueryAction!'
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
							// 状态占比图
							chart_1.setJSONData(praseDataZTZB(rs));
							chart_1.render("div_1");
							// 来源占比图
							chart_2.setJSONData(praseDataLYZB(rs));
							chart_2.render("div_2");
							// 趋势图
							chart_3.setJSONData(praseDataLYQS(rs));
							chart_3.render("div_3");
						}
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '查询出错！');
					}
				});
	}

	// 状态占比图数据解析转换
	function praseDataZTZB(rs) {
		var arr = rs.split(";");
		arr = arr[0].split(",");
		var jsonData = {
			"chart" : {
				"caption" : "商机状态占比图",
				"basefontsize" : "12",
				"pieslicedepth" : "30",
				"startingangle" : "125"
			},
			"data" : [{// 暂存
				"label" : arr[0],
				"value" : arr[1],
				"issliced" : "1"
			}, {	// 待分配
						"label" : arr[2],
						"value" : arr[3],
						"issliced" : "1"
					}, {// 认领
						"label" : arr[4],
						"value" : arr[5],
						"issliced" : "1"
					}, {// 待审批
						"label" : arr[6],
						"value" : arr[7],
						"issliced" : "1"
					}, {// 执行中
						"label" : arr[8],
						"value" : arr[9],
						"issliced" : "1"
					}, {// 退回
						"label" : arr[10],
						"value" : arr[11],
						"issliced" : "1"
					}, {// 到期退回
						"label" : arr[12],
						"value" : arr[13],
						"issliced" : "1"
					}, {// 失败关闭
						"label" : arr[14],
						"value" : arr[15],
						"issliced" : "1"
					}, {// 成功关闭
						"label" : arr[16],
						"value" : arr[17],
						"issliced" : "1"
					}],
			"styles" : {
				"definition" : [{
							"type" : "font",
							"name" : "captionFont",
							"size" : "15",
							"color" : "666666"
						}, {
							"type" : "font",
							"name" : "SubCaptionFont",
							"bold" : "0"
						}],
				"application" : [{
							"toobject" : "caption",
							"styles" : "captionFont"
						}, {
							"toobject" : "SubCaption",
							"styles" : "SubCaptionFont"
						}]
			}
		};
		return jsonData;
	}

	// 来源占比图数据解析转换
	function praseDataLYZB(rs) {
		var arr = rs.split(";");
		arr = arr[1].split(",");
		var jsonData = {
			"chart" : {
				"caption" : "商机来源占比图",
				"basefontsize" : "12",
				"pieslicedepth" : "30",
				"startingangle" : "125"
			},
			"data" : [{// 手动创建
				"label" : arr[0],
				"value" : arr[1],
				"issliced" : "1"
			}, {	// 提醒创建
						"label" : arr[2],
						"value" : arr[3],
						"issliced" : "1"
					}, {// 渠道(新媒体)
						"label" : arr[4],
						"value" : arr[5],
						"issliced" : "1"
					}, {// 渠道(CRM自动)
						"label" : arr[6],
						"value" : arr[7],
						"issliced" : "1"
					}, {// 渠道(其他)
						"label" : arr[8],
						"value" : arr[9],
						"issliced" : "1"
					}, {// 外部名单批量创建
						"label" : arr[10],
						"value" : arr[11],
						"issliced" : "1"
					}, {// 内部名单批量创建
						"label" : arr[12],
						"value" : arr[13],
						"issliced" : "1"
					}, {// 渠道(大堂)
						"label" : arr[14],
						"value" : arr[15],
						"issliced" : "1"
					}, {// 渠道(柜员)
						"label" : arr[16],
						"value" : arr[17],
						"issliced" : "1"
					}, {// 渠道(网银)
						"label" : arr[18],
						"value" : arr[19],
						"issliced" : "1"
					}, {// 渠道(手机银行)
						"label" : arr[20],
						"value" : arr[21],
						"issliced" : "1"
					}, {// 渠道(门户)
						"label" : arr[22],
						"value" : arr[23],
						"issliced" : "1"
					}, {// 渠道(外呼)
						"label" : arr[24],
						"value" : arr[25],
						"issliced" : "1"
					}],
			"styles" : {
				"definition" : [{
							"type" : "font",
							"name" : "captionFont",
							"size" : "15",
							"color" : "666666"
						}, {
							"type" : "font",
							"name" : "SubCaptionFont",
							"bold" : "0"
						}],
				"application" : [{
							"toobject" : "caption",
							"styles" : "captionFont"
						}, {
							"toobject" : "SubCaption",
							"styles" : "SubCaptionFont"
						}]
			}
		};
		return jsonData;
	}

	// 商机趋势图数据解析转换
	function praseDataLYQS(rs) {
		var arr = rs.split(";");
		arr = arr[2].split(",");
		var jsonData = {
			"chart" : {
				"caption" : "商机来源占比图",
				"linethickness" : "1",
				"showvalues" : "0",
				"formatnumberscale" : "0",
				"anchorradius" : "2",
				"divlinealpha" : "20",
				"divlinecolor" : "CC3300",
				"divlineisdashed" : "1",
				"showalternatehgridcolor" : "1",
				"alternatehgridcolor" : "CC3300",
				"shadowalpha" : "40",
				"labelstep" : "2",
				"numvdivlines" : "5",
				"chartrightmargin" : "35",
				"bgcolor" : "FFFFFF,CC3300",
				"bgangle" : "270",
				"bgalpha" : "10,10",
				"alternatehgridalpha" : "5",
				"legendposition" : "RIGHT "
			},
			"categories" : [{
						"category" : [{
									"label" : "1月"
								}, {
									"label" : "2月"
								}, {
									"label" : "3月"
								}, {
									"label" : "4月"
								}, {
									"label" : "5月"
								}, {
									"label" : "6月"
								}, {
									"label" : "7月"
								}, {
									"label" : "8月"
								}, {
									"label" : "9月"
								}, {
									"label" : "10月"
								}, {
									"label" : "11月"
								}, {
									"label" : "12月"
								}]
					}],
			"dataset" : [{
						"seriesname" : "新增商机",
						"color" : "1D8BD1",
						"anchorbordercolor" : "1D8BD1",
						"anchorbgcolor" : "1D8BD1",
						"data" : [{
									"value" : arr[0]
								}, {
									"value" : arr[3]
								}, {
									"value" : arr[6]
								}, {
									"value" : arr[9]
								}, {
									"value" : arr[12]
								}, {
									"value" : arr[15]
								}, {
									"value" : arr[18]
								}, {
									"value" : arr[21]
								}, {
									"value" : arr[24]
								}, {
									"value" : arr[27]
								}, {
									"value" : arr[30]
								}, {
									"value" : arr[33]
								}]
					}, {
						"seriesname" : "新增达成",
						"color" : "F1683C",
						"anchorbordercolor" : "F1683C",
						"anchorbgcolor" : "F1683C",
						"data" : [{
									"value" : arr[1]
								}, {
									"value" : arr[4]
								}, {
									"value" : arr[7]
								}, {
									"value" : arr[10]
								}, {
									"value" : arr[13]
								}, {
									"value" : arr[16]
								}, {
									"value" : arr[19]
								}, {
									"value" : arr[22]
								}, {
									"value" : arr[25]
								}, {
									"value" : arr[28]
								}, {
									"value" : arr[31]
								}, {
									"value" : arr[34]
								}]
					}, {
						"seriesname" : "新增失败",
						"color" : "2AD62A",
						"anchorbordercolor" : "2AD62A",
						"anchorbgcolor" : "2AD62A",
						"data" : [{
									"value" : arr[2]
								}, {
									"value" : arr[5]
								}, {
									"value" : arr[8]
								}, {
									"value" : arr[11]
								}, {
									"value" : arr[14]
								}, {
									"value" : arr[17]
								}, {
									"value" : arr[20]
								}, {
									"value" : arr[23]
								}, {
									"value" : arr[26]
								}, {
									"value" : arr[29]
								}, {
									"value" : arr[32]
								}, {
									"value" : arr[35]
								}]
					}],
			"styles" : {
				"definition" : [{
							"name" : "CaptionFont",
							"type" : "font",
							"size" : "12"
						}],
				"application" : [{
							"toobject" : "CAPTION",
							"styles" : "CaptionFont"
						}, {
							"toobject" : "SUBCAPTION",
							"styles" : "CaptionFont"
						}]
			}
		}
		return jsonData;
	}

	// 页面布局
	var viewport = new Ext.Viewport({
		layout : 'fit',
		autoScroll : true,
		items : [{
			xtype : 'portal',
			id : 'center',
			region : 'center',
			items : [{
				columnWidth : .3,
				collapsible : true,
				items : [{
					title : '商机状态占比图',
					collapsible : true,
					style : 'padding:0px 0px 0px 0px',
					height : height,
					width : width,
					html : '<div id="div_1" style="width:100%; height:100%"></div>'
				}]
			}, {
				columnWidth : .4,
				collapsible : true,
				items : [{
					title : '商机来源占比图',
					collapsible : true,
					style : 'padding:0px 0px 0px 0px',
					height : height,
					width : width,
					html : '<div id="div_2" style="width:100%; height:100%"></div>'
				}]
			}, {
				columnWidth : .3,
				collapsible : true,
				items : [{
					title : '商机趋势图',
					collapsible : true,
					style : 'padding:0px 0px 0px 0px',
					height : height,
					width : width,
					html : '<div id="div_3" style="width:100%; height:100%"></div>'
				}]
			}]
		}]
	});

});