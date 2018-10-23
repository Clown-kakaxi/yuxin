Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'qtip';
	var rsRecord = Ext.data.Record.create( [ 
		{name : 'id'},
		{name : 'agencyBran'},
		{name : 'amoInt'},
		{name : 'amoPrinc'},
		{name : 'appExeDt'},
		{name : 'appExeObj'},
		{name : 'balAmt'},
		{name : 'borrName'},
		{name : 'brief'},
		{name : 'cashRecAmt'},
		{name : 'couIdenInt'},
		{name : 'couIdenPr'},
		{name : 'custName'},
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
		{name : 'litiSta'},
		{name : 'maCaseNo'},
		{name : 'manageBran'},
		{name : 'month'},
		{name : 'odsStDate'},
		{name : 'oriBorrAmt'},
		{name : 'othCots'},
		{name : 'othProsec'},
		{name : 'parAgiaExeFil'},
		{name : 'pprdLose'},
		{name : 'presCots'},
		{name : 'proByRecAmt'},
		{name : 'recordDate'},
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
	]);

	var rsreader = new Ext.data.JsonReader( {
		successProperty : 'success',
//		idProperty : 'CUST_ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, rsRecord);
alert(1);
		var simple = new Ext.FormPanel( {
			reader : rsreader,
			frame : true,
//			height : 475,
			autoScroll : true,
		region:'center',
		buttonAlign:'center',
			tbar:[{text:'编辑',
				handler:function(){
				Ext.getCmp('id').enable();
				Ext.getCmp('agencyBran').enable();
				Ext.getCmp('amoInt').enable();
				Ext.getCmp('amoPrinc').enable();
				Ext.getCmp('appExeDt').enable();
				Ext.getCmp('appExeObj').enable();
				Ext.getCmp('balAmt').enable();
				Ext.getCmp('borrName').enable();
				Ext.getCmp('brief').enable();
				Ext.getCmp('cashRecAmt').enable();
				Ext.getCmp('couIdenInt').enable();
				Ext.getCmp('couIdenPr').enable();
				Ext.getCmp('custName').enable();
				Ext.getCmp('estimCots').enable();
				Ext.getCmp('exeAccCou').enable();
				Ext.getCmp('exeAccDt').enable();
				Ext.getCmp('exeCots').enable();
				Ext.getCmp('exeLegInstNo').enable();
				Ext.getCmp('exeLegInstTyp').enable();
				Ext.getCmp('exePubCots').enable();
				Ext.getCmp('exeYnAcc').enable();
				Ext.getCmp('finalUpdateDate').enable();
				Ext.getCmp('firstDutyOfficer').enable();
				Ext.getCmp('firstToAngent').enable();
				Ext.getCmp('fisAccCou').enable();
				Ext.getCmp('fisInstr').enable();
				Ext.getCmp('fisJudDt').enable();
				Ext.getCmp('fisJudEffDt').enable();
				Ext.getCmp('fisLegInstNo').enable();
				Ext.getCmp('fisLegInstTyp').enable();
				Ext.getCmp('fisNolPro').enable();
				Ext.getCmp('fisPresDt').enable();
				Ext.getCmp('fisRecoDt').enable();
				Ext.getCmp('fisYnAcc').enable();
				Ext.getCmp('fisYnPres').enable();
				Ext.getCmp('lawyCots').enable();
				Ext.getCmp('litiCots').enable();
				Ext.getCmp('litiInt').enable();
				Ext.getCmp('litiObj').enable();
				Ext.getCmp('litiPr').enable();
				Ext.getCmp('litiPubCharg').enable();
				Ext.getCmp('litiSta').enable();
				Ext.getCmp('maCaseNo').enable();
				Ext.getCmp('manageBran').enable();
				Ext.getCmp('month').enable();
				Ext.getCmp('odsStDate').enable();
				Ext.getCmp('oriBorrAmt').enable();
				Ext.getCmp('othCots').enable();
				Ext.getCmp('othProsec').enable();
				Ext.getCmp('parAgiaExeFil').enable();
				Ext.getCmp('pprdLose').enable();
				Ext.getCmp('presCots').enable();
				Ext.getCmp('proByRecAmt').enable();
				Ext.getCmp('recordDate').enable();
				Ext.getCmp('registrant').enable();
				Ext.getCmp('remarks').enable();
				Ext.getCmp('saleCots').enable();
				Ext.getCmp('secAccCou').enable();
				Ext.getCmp('secDutyOfficer').enable();
				Ext.getCmp('secInstr').enable();
				Ext.getCmp('secJudDt').enable();
				Ext.getCmp('secJudEffDt').enable();
				Ext.getCmp('secLegInstNo').enable();
				Ext.getCmp('secLegInstTyp').enable();
				Ext.getCmp('secNolPro').enable();
				Ext.getCmp('secPresDt').enable();
				Ext.getCmp('secRecoDt').enable();
				Ext.getCmp('secResul').enable();
				Ext.getCmp('secYnAcc').enable();
				Ext.getCmp('secYnPres').enable();
				Ext.getCmp('secondAgent').enable();
				Ext.getCmp('secondToAngent').enable();
				Ext.getCmp('theAgent').enable();
				Ext.getCmp('triResul').enable();
				Ext.getCmp('year').enable();
				Ext.getCmp('ynFisPubAnn').enable();
				Ext.getCmp('ynSecPubAnn').enable();

			}
		},{text:'保存',
			handler:function(){
			save();
			setDisable();
			}}],
			items : [ {
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
							id:'id',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '年份',
							name : 'year',
							id : 'year',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '月份',
							name : 'month',
							id : 'month',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'maCaseNo',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '诉讼阶段',
							name : 'litiSta',
							id : 'litiSta',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id:'borrName',
							//value:affiCustManager,
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'custName',
							id : 'custName',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '其他被起诉人',
							name : 'othProsec',
							id: 'othProsec',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'oriBorrAmt',
							//value:affiCustManager,
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '结欠金额（元）',
							name : 'balAmt',
							id:'balAmt',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '其中结欠本金（元）',
							name : 'amoPrinc',
							id:'amoPrinc',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '其中结欠利息（元）',
							name : 'amoInt',
							id:'amoInt',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'litiObj',
							//value:affiCustManager,
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '诉讼本金（元）',
							name : 'litiPr',
							id : 'litiPr',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '诉讼利息（元）',
							name : 'litiInt',
							id : 'litiInt',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '案由',
							name : 'brief',
							id:'brief',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'theAgent',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '一审移送代理人日期',
							name : 'firstToAngent',
							id : 'firstToAngent',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审是否受理',
							name : 'fisYnAcc',
							id : 'fisYnAcc',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审受理法院',
							name : 'fisAccCou',
							id : 'fisAccCou',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'fisRecoDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审是否保全',
							name : 'fisYnPres',
							id : 'fisYnPres',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '一审保全时间',
							disabled : true,
							name : 'fisPresDt',
							id : 'fisPresDt',
							format:'Y-m-d',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审法律文书类型',
							disabled : true,
							name : 'fisLegInstTyp',
							id : 'fisLegInstTyp',
							labelStyle : 'text-align:right;',
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
							id : 'fisLegInstNo',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '审理结果',
							name : 'triResul',
							id : 'triResul',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审撤诉原因',
							name : 'fisNolPro',
							id : 'fisNolPro',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '法院认定本金',
							disabled : true,
							name : 'couIdenPr',
							id : 'couIdenPr',
							labelStyle : 'text-align:right;',
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
							id : 'couIdenInt',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审是否公告送达',
							name : 'ynFisPubAnn',
							id : 'ynFisPubAnn',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '一审判决日期',
							name : 'fisJudDt',
							id : 'fisJudDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '一审判决生效日期',
							name : 'fisJudEffDt',
							id : 'fisJudEffDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '一审其他需要说明情况',
							name : 'fisInstr',
							id : 'fisInstr',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'secondAgent',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '二审移送代理人日期',
							name : 'secondToAngent',
							id : 'secondToAngent',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审是否受理',
							name : 'secYnAcc',
							id : 'secYnAcc',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审受理法院',
							name : 'secAccCou',
							id : 'secAccCou',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'secRecoDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审是否保全',
							name : 'secYnPres',
							id : 'secYnPres',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '二审保全时间',
							disabled : true,
							name : 'secPresDt',
							id : 'secPresDt',
							format:'Y-m-d',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审是否公告送达',
							disabled : true,
							name : 'ynSecPubAnn',
							id : 'ynSecPubAnn',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '二审法律文书类型',
							disabled : true,
							name : 'secLegInstTyp',
							id : 'secLegInstTyp',
							labelStyle : 'text-align:right;',
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
							id : 'secLegInstNo',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审审理结果',
							name : 'secResul',
							id : 'secResul',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审撤诉原因',
							name : 'secNolPro',
							id : 'secNolPro',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '二审判决日期',
							disabled : true,
							name : 'secJudDt',
							id : 'secJudDt',
							format:'Y-m-d',
							labelStyle : 'text-align:right;',
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
							id : 'secJudEffDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '二审其他需要说明情况',
							name : 'secInstr',
							id : 'secInstr',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id:'appExeDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '申请执行标的',
							name : 'appExeObj',
							id : 'appExeObj',
							maxLength:2,
							maxLengthText: '长度过长',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '被申请执行人',
							name : 'parAgiaExeFil',
							id: 'parAgiaExeFil',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '执行受理法院',
							name : 'exeAccCou',
							id : 'exeAccCou',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
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
							id : 'exeAccDt',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '执行法律文书类型',
							name : 'exeLegInstTyp',
							id:'exeLegInstTyp',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '执行法律文书案号',
							disabled : true,
							name : 'exeLegInstNo',
							id : 'exeLegInstNo',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '现金回收金额',
							disabled : true,
							name : 'cashRecAmt',
							id : 'cashRecAmt',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						},{
							xtype : 'textfield',
							fieldLabel : '以资抵债收回金额',
							disabled : true,
							name : 'proByRecAmt',
							id : 'proByRecAmt',
							labelStyle : 'text-align:right;',
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
							id : 'pprdLose',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remarks',
							id : 'remarks',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '管理机构',
							name : 'manageBran',
							id : 'manageBran',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '经办机构',
							disabled : true,
							name : 'agencyBran',
							id:'agencyBran',
							labelStyle : 'text-align:right;',
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
							id : 'firstDutyOfficer',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '第二责任人',
							name : 'secDutyOfficer',
							id : 'secDutyOfficer',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '登记人',
							name : 'registrant',
							id : 'registrant',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '最后修改日期',
							name : 'finalUpdateDate',
							id : 'finalUpdateDate',
							format:'Y-m-d',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '登记日期',
							name : 'recordDate',
							id : 'recordDate',
							labelStyle : 'text-align:left;',
							format:"Y-m-d",
							disabled : true,
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
							id: 'exeYnAcc',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '诉讼费用',
							name : 'litiCots',
							id : 'litiCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '诉讼公告费用',
							name : 'litiPubCharg',
							id : 'litiPubCharg',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '执行费用',
							name : 'exeCots',
							id : 'exeCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '执行公告费用',
							name : 'exePubCots',
							id : 'exePubCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '评估费用',
							name : 'estimCots',
							id : 'estimCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拍卖费用',
							name : 'saleCots',
							id : 'saleCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '律师费用',
							name : 'lawyCots',
							id : 'lawyCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '保全费用',
							name : 'presCots',
							id : 'presCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '其他费用',
							name : 'othCots',
							id:'othCots',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '平台数据日期',
							name : 'odsStDate',
							id : 'odsStDate',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}]
					} ]
				}
				]
			}


			]
		});
		alert(2);
			simple.getForm().load({
			
			restful : true,
			url : basepath + '/AcrmFCiCustLawInfoAction-info!query.json',
			params : {
					'condition' : oCustInfo.cust_id
			},
			method : 'GET'
		});
			simple.on('afterlayout',function(){
				Ext.getCmp('borrName').setValue(oCustInfo.cust_id);
				Ext.getCmp('custName').setValue(oCustInfo.cust_name);
			});
			function save(){
				Ext.Ajax.request({
					url:basepath + '/AcrmFCiCustLawInfoAction-info!update.json',
					form:simple.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功');
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
					}
				});
			}
		var viewport_center = new Ext.Panel({
			 renderTo:'viewport_center',
//			 id:'viewport_center',
				height:document.body.scrollHeight-30,
				width : document.body.clientWidth-223,
				layout:'fit',
				autoScroll:true,
			
					items: [simple] 

				});
		
		function setDisable(){
			Ext.getCmp('id').disable();
			Ext.getCmp('agencyBran').disable();
			Ext.getCmp('amoInt').disable();
			Ext.getCmp('amoPrinc').disable();
			Ext.getCmp('appExeDt').disable();
			Ext.getCmp('appExeObj').disable();
			Ext.getCmp('balAmt').disable();
			Ext.getCmp('borrName').disable();
			Ext.getCmp('brief').disable();
			Ext.getCmp('cashRecAmt').disable();
			Ext.getCmp('couIdenInt').disable();
			Ext.getCmp('couIdenPr').disable();
			Ext.getCmp('custName').disable();
			Ext.getCmp('estimCots').disable();
			Ext.getCmp('exeAccCou').disable();
			Ext.getCmp('exeAccDt').disable();
			Ext.getCmp('exeCots').disable();
			Ext.getCmp('exeLegInstNo').disable();
			Ext.getCmp('exeLegInstTyp').disable();
			Ext.getCmp('exePubCots').disable();
			Ext.getCmp('exeYnAcc').disable();
			Ext.getCmp('finalUpdateDate').disable();
			Ext.getCmp('firstDutyOfficer').disable();
			Ext.getCmp('firstToAngent').disable();
			Ext.getCmp('fisAccCou').disable();
			Ext.getCmp('fisInstr').disable();
			Ext.getCmp('fisJudDt').disable();
			Ext.getCmp('fisJudEffDt').disable();
			Ext.getCmp('fisLegInstNo').disable();
			Ext.getCmp('fisLegInstTyp').disable();
			Ext.getCmp('fisNolPro').disable();
			Ext.getCmp('fisPresDt').disable();
			Ext.getCmp('fisRecoDt').disable();
			Ext.getCmp('fisYnAcc').disable();
			Ext.getCmp('fisYnPres').disable();
			Ext.getCmp('lawyCots').disable();
			Ext.getCmp('litiCots').disable();
			Ext.getCmp('litiInt').disable();
			Ext.getCmp('litiObj').disable();
			Ext.getCmp('litiPr').disable();
			Ext.getCmp('litiPubCharg').disable();
			Ext.getCmp('litiSta').disable();
			Ext.getCmp('maCaseNo').disable();
			Ext.getCmp('manageBran').disable();
			Ext.getCmp('month').disable();
			Ext.getCmp('odsStDate').disable();
			Ext.getCmp('oriBorrAmt').disable();
			Ext.getCmp('othCots').disable();
			Ext.getCmp('othProsec').disable();
			Ext.getCmp('parAgiaExeFil').disable();
			Ext.getCmp('pprdLose').disable();
			Ext.getCmp('presCots').disable();
			Ext.getCmp('proByRecAmt').disable();
			Ext.getCmp('recordDate').disable();
			Ext.getCmp('registrant').disable();
			Ext.getCmp('remarks').disable();
			Ext.getCmp('saleCots').disable();
			Ext.getCmp('secAccCou').disable();
			Ext.getCmp('secDutyOfficer').disable();
			Ext.getCmp('secInstr').disable();
			Ext.getCmp('secJudDt').disable();
			Ext.getCmp('secJudEffDt').disable();
			Ext.getCmp('secLegInstNo').disable();
			Ext.getCmp('secLegInstTyp').disable();
			Ext.getCmp('secNolPro').disable();
			Ext.getCmp('secPresDt').disable();
			Ext.getCmp('secRecoDt').disable();
			Ext.getCmp('secResul').disable();
			Ext.getCmp('secYnAcc').disable();
			Ext.getCmp('secYnPres').disable();
			Ext.getCmp('secondAgent').disable();
			Ext.getCmp('secondToAngent').disable();
			Ext.getCmp('theAgent').disable();
			Ext.getCmp('triResul').disable();
			Ext.getCmp('year').disable();
			Ext.getCmp('ynFisPubAnn').disable();
			Ext.getCmp('ynSecPubAnn').disable();
		}
		/*
		 * function editInit(){ window.location.href =
		 * 'familyMemberInformation.html' ; };
		 */
	});