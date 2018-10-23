Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'qtip';
	var listPanel = new Mis.Ext.CrudPanel({
		id : "listPanel",
	    title : "客户诉讼信息",
		stUrl : basepath + '/AcrmFCiCustLawInfoAction-info!query.json?condition='+oCustInfo.cust_id,
		detailUrl : basepath + '/AcrmFCiCustLawInfoAction-info!query.json?condition='+oCustInfo.cust_id,
		primary : "id",
	    checkbox : true,
	    //定义高度
		height:document.body.clientHeight,
		//定义宽度
		width : document.body.clientWidth-240,
		//定义显示结果列表高度
		gridHeight : document.body.clientHeight-80,
		frame : true,
	    winHeight : 400,
		winWidth : 900,
		gclms : [ 
				{name : 'id',header:'ID'},
				{name : 'agencyBran'},
				{name : 'amoInt'},
				{name : 'amoPrinc'},
				{name : 'appExeDt'},
				{name : 'appExeObj'},
				{name : 'balAmt',header:'结欠金额（元）'},
				{name : 'borrName',header:'客户编号'},
				{name : 'brief'},
				{name : 'cashRecAmt'},
				{name : 'couIdenInt'},
				{name : 'couIdenPr'},
				{name : 'custName',header:'客户名称'},
				{name : 'estimCots'},
				{name : 'exeAccCou'},
				{name : 'exeAccDt'},
				{name : 'exeCots'},
				{name : 'exeLegInstNo'},
				{name : 'exeLegInstTyp'},
				{name : 'exePubCots'},
				{name : 'exeYnAcc'},
				{name : 'finalUpdateDate'},
				{name : 'firstDutyOfficer'},
				{name : 'firstToAngent'},
				{name : 'fisAccCou'},
				{name : 'fisInstr'},
				{name : 'fisJudDt'},
				{name : 'fisJudEffDt'},
				{name : 'fisLegInstNo'},
				{name : 'fisLegInstTyp'},
				{name : 'fisNolPro'},
				{name : 'fisPresDt'},
				{name : 'fisRecoDt'},
				{name : 'fisYnAcc'},
				{name : 'fisYnPres'},
				{name : 'lawyCots'},
				{name : 'litiCots'},
				{name : 'litiInt'},
				{name : 'litiObj'},
				{name : 'litiPr'},
				{name : 'litiPubCharg'},
				{name : 'litiSta',header:'诉讼阶段'},
				{name : 'maCaseNo'},
				{name : 'manageBran'},
				{name : 'month'},
				{name : 'odsStDate'},
				{name : 'oriBorrAmt',header:'原借款金额'},
				{name : 'othCots'},
				{name : 'othProsec',header:'其他被起诉人'},
				{name : 'parAgiaExeFil'},
				{name : 'pprdLose'},
				{name : 'presCots'},
				{name : 'proByRecAmt'},
				{name : 'recordDate',header:'登记日期'},
				{name : 'registrant'},
				{name : 'remarks'},
				{name : 'saleCots'},
				{name : 'secAccCou'},
				{name : 'secDutyOfficer'},
				{name : 'secInstr'},
				{name : 'secJudDt'},
				{name : 'secJudEffDt'},
				{name : 'secLegInstNo'},
				{name : 'secLegInstTyp'},
				{name : 'secNolPro'},
				{name : 'secPresDt'},
				{name : 'secRecoDt'},
				{name : 'secResul'},
				{name : 'secYnAcc'},
				{name : 'secYnPres'},
				{name : 'secondAgent'},
				{name : 'secondToAngent'},
				{name : 'theAgent'},
				{name : 'triResul'},
				{name : 'year'},
				{name : 'ynFisPubAnn'},
				{name : 'ynSecPubAnn'}
				], 	
		pagesize : 10,
		fclms:[ {
			xtype : 'fieldset',
			title : '客户诉讼信息',
			titleCollapse : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : 'ID',
						name : 'id',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '年份',
						name : 'year',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '月份',
						name : 'month',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '主案件编号',
						name : 'maCaseNo',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '诉讼阶段',
						name : 'litiSta',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '客户编号',
						name : 'borrName',
						//value:affiCustManager,
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '客户名称',
						name : 'custName',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '其他被起诉人',
						name : 'othProsec',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '原借款金额（元）',
						name : 'oriBorrAmt',
						//value:affiCustManager,
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '结欠金额（元）',
						name : 'balAmt',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '其中结欠本金（元）',
						name : 'amoPrinc',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '其中结欠利息（元）',
						name : 'amoInt',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				},{
					layout : 'form',
					columnWidth : .5,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '诉讼标的（元）',
						name : 'litiObj',
						//value:affiCustManager,
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '诉讼本金（元）',
						name : 'litiPr',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '诉讼利息（元）',
						name : 'litiInt',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '案由',
						name : 'brief',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				} ]
			} ]
		}, {
			xtype : 'fieldset',
			title : '一审信息',
			titleCollapse : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '一审代理人',
						name : 'theAgent',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '一审移送代理人日期',
						name : 'firstToAngent',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审是否受理',
						name : 'fisYnAcc',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审受理法院',
						name : 'fisAccCou',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'datefield',
						fieldLabel : '一审立案日期',
						name : 'fisRecoDt',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审是否保全',
						name : 'fisYnPres',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '一审保全时间',
						name : 'fisPresDt',
						format:'Y-m-d',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审法律文书类型',
						name : 'fisLegInstTyp',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '一审法律文书案号',
						name : 'fisLegInstNo',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '审理结果',
						name : 'triResul',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审撤诉原因',
						name : 'fisNolPro',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '法院认定本金',
						name : 'couIdenPr',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '法院认定利息',
						name : 'couIdenInt',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审是否公告送达',
						name : 'ynFisPubAnn',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '一审判决日期',
						name : 'fisJudDt',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '一审判决生效日期',
						name : 'fisJudEffDt',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '一审其他需要说明情况',
						name : 'fisInstr',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				} ]
			}
			]
		}, {
			xtype : 'fieldset',
			title : '二审信息',
			titleCollapse : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '二审代理人',
						name : 'secondAgent',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '二审移送代理人日期',
						name : 'secondToAngent',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审是否受理',
						name : 'secYnAcc',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审受理法院',
						name : 'secAccCou',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'datefield',
						fieldLabel : '二审立案日期',
						name : 'secRecoDt',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审是否保全',
						name : 'secYnPres',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '二审保全时间',
						name : 'secPresDt',
						format:'Y-m-d',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审是否公告送达',
						name : 'ynSecPubAnn',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '二审法律文书类型',
						name : 'secLegInstTyp',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '二审法律文书案号',
						name : 'secLegInstNo',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审审理结果',
						name : 'secResul',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审撤诉原因',
						name : 'secNolPro',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '二审判决日期',
						name : 'secJudDt',
						format:'Y-m-d',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'datefield',
						fieldLabel : '二审判决生效日期',
						name : 'secJudEffDt',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '二审其他需要说明情况',
						name : 'secInstr',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				} ]
			}
			]
		}, {
			xtype : 'fieldset',
			title : '其他信息',
			titleCollapse : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'datefield',
						fieldLabel : '申请执行日期',
						name : 'appExeDt',
						format:'Y-m-d',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '申请执行标的',
						name : 'appExeObj',
						maxLength:2,
						maxLengthText: '长度过长',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '被申请执行人',
						name : 'parAgiaExeFil',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '执行受理法院',
						name : 'exeAccCou',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'datefield',
						fieldLabel : '执行受理时间',
						name : 'exeAccDt',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '执行法律文书类型',
						name : 'exeLegInstTyp',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '执行法律文书案号',
						name : 'exeLegInstNo',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '现金回收金额',
						name : 'cashRecAmt',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						fieldLabel : '以资抵债收回金额',
						name : 'proByRecAmt',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '预计损失',
						name : 'pprdLose',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '备注',
						name : 'remarks',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '管理机构',
						name : 'manageBran',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '经办机构',
						name : 'agencyBran',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '第一责任人',
						name : 'firstDutyOfficer',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '第二责任人',
						name : 'secDutyOfficer',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '登记人',
						name : 'registrant',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '最后修改日期',
						name : 'finalUpdateDate',
						format:'Y-m-d',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel : '登记日期',
						name : 'recordDate',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '执行是否受理',
						name : 'exeYnAcc',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '诉讼费用',
						name : 'litiCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '诉讼公告费用',
						name : 'litiPubCharg',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}
				
				, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '执行公告费用',
						name : 'exePubCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '评估费用',
						name : 'estimCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '拍卖费用',
						name : 'saleCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [{
						xtype : 'textfield',
						fieldLabel : '保全费用',
						name : 'presCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '其他费用',
						name : 'othCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '平台数据日期',
						name : 'odsStDate',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .25,
					labelWidth : 120,
					items : [{
						xtype : 'textfield',
						fieldLabel : '执行费用',
						name : 'exeCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '律师费用',
						name : 'lawyCots',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',readOnly:true,
						anchor : '95%'
					}]
				}
				]
			}
			]
		}


		]

});
	var viewport_center = new Ext.Panel({
		 renderTo:'viewport_center',
//		 id:'viewport_center',
			frame:true,
			height:document.body.scrollHeight-30,
			layout:'fit',
			autoScroll:true,
		
				items: [listPanel] 

			});
	});