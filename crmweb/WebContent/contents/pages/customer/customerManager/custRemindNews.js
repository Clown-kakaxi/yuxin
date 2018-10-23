Ext.onReady(function() {
	 Ext.QuickTips.init();
	 // 客户编号
	 var cust_id = oCustInfo.cust_id;
	/****************************************************************/
   
	//提醒类型
	var remindTypeStore;
	if (__roleType == 2) {
		remindTypeStore = util.form._store('/lookup.json?name=REMIND_COM_TYPE');
	} else {
		remindTypeStore = util.form._store('/lookup.json?name=REMIND_TYPE');
	}
	remindTypeStore.load();
	
	var readStore = new Ext.data.ArrayStore({
            fields:['myId','displayText'],
            data:[['1','已阅读'],['0','未阅读']]
        });
	
	
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
					id : "listPanel",
					title : "客户提醒信息",
					stUrl : basepath+'/queryremindlist.json?custId='+cust_id,
					primary : "ID",
					checkbox : true,
					// 定义查询条件Form的高度
					//seFormHeight : 80,
					// 定义增删详情页面弹出窗口高度
					winHeight : 300,
					// 宽度
					//winWidth : 400,
					gridHeight :document.body.clientHeight-120,
					pagesize : 20,
					afterSeOneFun : function(b) {
						//Ext.getCmp('createDt').setValue(new Date(b.createDt.time));
				    	//Ext.getCmp('valDt').setValue(new Date(b.valDt.time));
					},
					selectItems : {
						layout : 'column',
						items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									id : 'msg_type',
									xtype : 'combo',
									name : 'MSG_TYP',
									hiddenName : 'MSG_TYP',
									fieldLabel : '提醒类型',
									labelStyle : 'text-align:right;',
									anchor : '100%',
									mode : 'local',
									triggerAction : 'all',
									resizable : true,
									store : remindTypeStore,
									valueField : 'key',
									displayField : 'value'
								}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											xtype : 'numberfield',
											fieldLabel : '剩余天数',
											labelStyle : 'text-align:right;',
											name : 'MSG_LAST',
											allowDecimal : false,
											anchor : '100%'
										}, {
											hidden : true,
											name : 'MSG_LAST_DATE',
											id : 'msgLastDate'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '信息状态',
											hiddenName : 'MSG_STS',
											resizable : true,
											forceSelection : true,
											xtype : 'combo',
											labelStyle : 'text-align:right;',
											triggerAction : 'all',
											mode : 'local',
											store : new Ext.data.ArrayStore({
														fields : ['myId', 'displayText'],
														data : [['1', '已阅读'], ['0', '未阅读']]
													}),
											valueField : 'myId',
											displayField : 'displayText',
											emptyText : '请选择',
											anchor : '100%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											xtype : 'datefield',
											fieldLabel : '提醒到期日',
											labelStyle : 'text-align:right;',
											name : 'MSG_END_DATE',
											format : 'Y-m-d',
											allowDecimal : false,
											anchor : '100%',
											editable : false
			
										}]
						}]
					},
					// 查询列表字段定义，有header属性则在页面显示
					// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
					gclms : [ {name : 'ID'}, 
					          {name : 'MSG_TYP',header : '提醒类型',store : remindTypeStore,type : 'mapping',mappingkey:'key',mappingvalue:'value'}, 
					          {name : 'CUST_NAME',header : '客户名称'},
					          {name : 'MSG_STS',header : '信息状态',store : readStore,type : 'mapping',mappingkey:'myId',mappingvalue:'displayText'}, 
					          {name : 'MSG_LAST',header : '提醒剩余天数'}, 
					          {name : 'MSG_END_DATE',header : '提醒到期日'}, 
					          {name : 'MSG_REMARK',header : '提醒备注'},
		                        {name: 'MSG_TYP_ORA'},
		                        {name: 'CUST_ID'},
		                        {name: 'CUST_ZZDM'},
		                        {name: 'ACCT_NO'},
		                        {name: 'ACCT_NAME'},
		                        {name: 'ACCT_ORGNO'},
		                        {name: 'ACCT_BAL'},
		                        {name: 'ACCT_AMT'},
		                        {name: 'MANAGER_NAME'},
		                        {name: 'MANAGER_PHONE'},
		                        {name: 'EVENT_NAME'},
		                        {name: 'MSG_END_DATE'},
		                        {name: 'MSG_LAST'},
		                        {name: 'MSG_REMARK'},
		                        {name: 'USER_NO'},
		                        {name: 'MSG_CRT_DATE'},
		                        {name: 'READ_DATE'},
		                        {name: 'USER_UNITID'},
		                        {name: 'KH_DATE'},
		                        {name: 'DQ_DATE'},
		                        {name: 'ADDR'},
		                        {name: 'ACCT_ORGNO'},
		                        {name: 'KH_ORG'},
		                        {name: 'JK_AMT'},
		                        {name: 'FF_DATE'},
		                        {name: 'YJLX'},
		                        {name: 'WHQS'},
		                        {name: 'QX_AMT'},
		                        {name: 'JY_DATE'},
		                        {name: 'JY_TYP'},
		                        {name: 'BIRTHDAY1'},
		                        {name: 'BIRTHDAY2'},
		                        {name: 'CREDIT_TYP'},
		                        {name: 'CREDIT_NO'},
		                        {name: 'ZJQF_DATE'},
		                        {name: 'ZJDQ_DATE'},
		                        {name: 'QFGJ'},
		                        {name: 'QF_ORG'},
		                        {name: 'NJBZ'},
		                        {name: 'CARD_NO'},
		                        {name: 'TZ_AMT'},
		                        {name: 'LAST_HK_DATE'}
					        ],
					// 新增、修改、详情的form的字段
					buts : [{
							text : '查看详细信息',
							iconCls : 'editIconCss',
							handler : viewInit
						}, '-', {
							text : '设为已读',
							iconCls : 'ReadIconCss',
							handler : read
					}]
				});
		
	/*	var view = new Ext.Viewport({
					layout : 'fit',
					items : [listPanel]
		});*/
		
		var editPlanPanel = new Ext.Panel({
				renderTo : 'viewport_center',
				width:document.body.scrollWidth-228,
				height : document.body.clientHeight,//400,
				layout : 'fit',
				items : [listPanel]
			});
		
		//-----------
	 
		//查看详细
		function viewInit(){
			if (!listPanel.grid.selModel.hasSelection()) {
				Ext.Msg.alert("提示", "请选择其中一条记录!");
				return ;
			}
			var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
			var recordsLen = records.length;// 得到行数组的长度
			if (recordsLen > 1) {
				Ext.Msg.alert("系统提示信息", "只能操作一条记录！");
				return ;
			} 
			
			var anchor = '98%';
			var MSG_TYP = new Ext.form.ComboBox({fieldLabel : '提醒类型',name : 'MSG_TYP',hiddenName:'MSG_TYP',store : remindTypeStore,mode:'local',valueField : 'key',
									displayField : 'value',editable:false,readOnly : true,anchor:anchor});
			var MSG_STS = new Ext.form.TextField({fieldLabel : '信息状态',name : 'MSG_STS',readOnly : true,anchor:anchor});
			var CUST_ID = new Ext.form.TextField({fieldLabel : '客户编号',name : 'CUST_ID',readOnly : true,anchor:anchor})
			var CUST_NAME = new Ext.form.TextField({fieldLabel : '客户名称',name : 'CUST_NAME',readOnly : true,anchor:anchor});
			var CUST_ZZDM = new Ext.form.TextField({fieldLabel : '证件号码',name : 'CUST_ZZDM',readOnly : true,anchor:anchor});
			var ACCT_NO = new Ext.form.TextField({fieldLabel : '账号',name : 'ACCT_NO',readOnly : true,anchor:anchor});
			var ACCT_NAME = new Ext.form.TextField({fieldLabel : '账户名',name : 'ACCT_NAME',readOnly : true,anchor:anchor});
			var ACCT_ORGNO = new Ext.form.TextField({fieldLabel : '账户机构号',name : 'ACCT_ORGNO',readOnly : true,anchor:anchor});
			var ACCT_BAL = new Ext.form.TextField({fieldLabel : '账户余额',name : 'ACCT_BAL',readOnly : true,anchor:anchor});
			var ACCT_AMT = new Ext.form.TextField({fieldLabel : '变动金额',name : 'ACCT_AMT',readOnly : true,anchor:anchor});
			var MANAGER_NAME = new Ext.form.TextField({fieldLabel : '高管姓名',name : 'MANAGER_NAME',readOnly : true,anchor:anchor});
			var MANAGER_PHONE = new Ext.form.TextField({fieldLabel : '联系电话',name : 'MANAGER_PHONE',readOnly : true,anchor:anchor});
			var EVENT_NAME = new Ext.form.TextField({fieldLabel : '事件名称',name : 'EVENT_NAME',readOnly : true,anchor:anchor});
			var MSG_CRT_DATE = new Ext.form.TextField({fieldLabel : '信息创建日期',name : 'MSG_CRT_DATE',readOnly : true,anchor:anchor});
			var MSG_END_DATE = new Ext.form.TextField({fieldLabel : '信息截止日期',name : 'MSG_END_DATE',readOnly : true,anchor:anchor});
			var MSG_LAST = new Ext.form.TextField({fieldLabel : '剩余提醒天数',name : 'MSG_LAST',readOnly : true,anchor:anchor});
			var READ_DATE = new Ext.form.TextField({fieldLabel : '阅读日期',name : 'READ_DATE',readOnly : true,anchor:anchor});
			var USER_NO = new Ext.form.TextField({fieldLabel : '用户编号',name : 'USER_NO',readOnly : true,anchor:anchor});
			var USER_UNITID = new Ext.form.TextField({fieldLabel : '用户机构号',name : 'USER_UNITID',readOnly : true,anchor:'100'});
			var MSG_REMARK = new Ext.form.TextArea({fieldLabel : '信息备注',name : 'MSG_REMARK',readOnly : true,anchor:anchor});
			var CCY = new Ext.form.TextField({fieldLabel : '币种',name : 'CCY',readOnly : true,anchor:anchor});
			var BAL_DIR = new Ext.form.TextField({fieldLabel : '变动方向',name : 'BAL_DIR',readOnly : true,anchor:'100'});
			var EVENT_TYP = new Ext.form.TextField({fieldLabel : '事件类型',name : 'EVENT_TYP',readOnly : true,anchor:anchor});
			var CREDIT_TYP = new Ext.form.TextField({fieldLabel : '证件类型',name : 'CREDIT_TYP',readOnly : true,anchor:anchor});
			var KH_DATE = new Ext.form.TextField({fieldLabel : '开户日',name : 'KH_DATE',readOnly : true,anchor:anchor});
			var DQ_DATE = new Ext.form.TextField({fieldLabel : '到期日',name : 'DQ_DATE',readOnly : true,anchor:anchor});
			var JK_AMT = new Ext.form.TextField({fieldLabel : '借款金额',name : 'JK_AMT',readOnly : true,anchor:anchor});
			var FF_DATE = new Ext.form.TextField({fieldLabel : '发放日',name : 'FF_DATE',readOnly : true,anchor:anchor});
			var YJLX = new Ext.form.TextField({fieldLabel : '应缴利息',name : 'YJLX',readOnly : true,anchor:anchor});
			var WHQS = new Ext.form.TextField({fieldLabel : '未还期数',name : 'WHQS',readOnly : true,anchor:anchor});
			var QX_AMT = new Ext.form.TextField({fieldLabel : '欠息金额',name : 'QX_AMT',readOnly : true,anchor:anchor});
			var JY_DATE = new Ext.form.TextField({fieldLabel : '交易日期',name : 'JY_DATE',readOnly : true,anchor:anchor});
			var JY_TYP = new Ext.form.TextField({fieldLabel : '交易类型',name : 'JY_TYP',readOnly : true,anchor:anchor});
			var BIRTHDAY1 = new Ext.form.TextField({fieldLabel : '阴历生日日期',name : 'BIRTHDAY1',readOnly : true,anchor:anchor});
			var BIRTHDAY2 = new Ext.form.TextField({fieldLabel : '阳历生日日期',name : 'BIRTHDAY2',readOnly : true,anchor:anchor});
			var ZJQF_DATE = new Ext.form.TextField({fieldLabel : '证件签发日期',name : 'ZJQF_DATE',readOnly : true,anchor:anchor});
			var ZJDQ_DATE = new Ext.form.TextField({fieldLabel : '证件到期日期',name : 'ZJDQ_DATE',readOnly : true,anchor:anchor});
			var QFGJ = new Ext.form.TextField({fieldLabel : '签发国家',name : 'QFGJ',readOnly : true,anchor:anchor});
			var QF_ORG = new Ext.form.TextField({fieldLabel : '签发机构',name : 'QF_ORG',readOnly : true,anchor:anchor});
			var NJBZ = new Ext.form.TextField({fieldLabel : '年检标识',name : 'NJBZ',readOnly : true,anchor:anchor});
			var CARD_NO = new Ext.form.TextField({fieldLabel : '卡号',name : 'CARD_NO',readOnly : true,anchor:anchor});
			var TZ_AMT = new Ext.form.TextField({fieldLabel : '透支金额',name : 'TZ_AMT',readOnly : true,anchor:anchor});
			var LAST_HK_DATE = new Ext.form.TextField({fieldLabel : '最后还款日',name : 'LAST_HK_DATE',readOnly : true,anchor:anchor});
			var ADDR = new Ext.form.TextField({fieldLabel : '联系地址',name : 'ADDR',readOnly : true,anchor:anchor});
			/**
			 *  //零售
			 *  1000000001	客户生日提醒
				1000000002	客户升降级提醒
				1000000003	理财产品到期提醒
				1000000004	定期存款到期提醒
				1000000005	贷款产品到期提醒
				1000000006	客户账户大额变动提醒
				1000000007	贷款逾期提醒
				1000000008	信用卡逾期提醒
				1000000009	睡眠客户提醒
				//公司
				2000000001	理财产品到期提醒
				2000000002	信托产品到期提醒
				2000000003	本外币定期存款到期提醒
				2000000004	贷款产品到期提醒
				2000000005	客户账户大额变动提醒
				2000000006	贷款逾期提醒
				2000000007	客户需求提醒
				2000000008	远期信用证、远期托收项下承兑到期日提醒
			 * 
			 */
			
			var itemArrayL = [];
			var itemArrayM = [];
			var itemArrayR = [];
			//三个必须要的
			itemArrayL.push(MSG_TYP);
			itemArrayM.push(MSG_LAST);
			itemArrayR.push(MSG_END_DATE);
			//----
			var record = listPanel.grid.getSelectionModel().getSelected();
			var msgTypValue = record.data.MSG_TYP;
			if(msgTypValue == '1000000001'){ //客户生日提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(MANAGER_PHONE);
				itemArrayR.push(ACCT_ORGNO);
				itemArrayL.push(BIRTHDAY1);
				itemArrayM.push(BIRTHDAY2);
			}else if(msgTypValue == '1000000002'){//客户升降级提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_AMT);
				itemArrayL.push(ACCT_BAL);
				itemArrayM.push(KH_DATE);
			}else if(msgTypValue == '1000000003'){//理财产品到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '1000000004'){//定期存款到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(KH_DATE);
				itemArrayR.push(DQ_DATE);
				itemArrayL.push(ACCT_BAL);
				itemArrayM.push(ADDR);
				itemArrayR.push(MANAGER_PHONE);
				itemArrayL.push(ACCT_ORGNO);
			}else if(msgTypValue == '1000000005'){//贷款产品到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(MANAGER_PHONE);
				itemArrayR.push(ACCT_NO);
				itemArrayL.push(JK_AMT);
				itemArrayM.push(FF_DATE);
				itemArrayR.push(DQ_DATE);
				itemArrayL.push(ACCT_BAL);
				itemArrayM.push(YJLX);
				itemArrayR.push(ACCT_ORGNO);
			}else if(msgTypValue == '1000000006'){//客户账户大额变动提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ADDR);
				itemArrayL.push(MANAGER_PHONE);
				itemArrayM.push(ACCT_ORGNO);
				itemArrayR.push(JY_TYP);
				itemArrayL.push(JY_DATE);
				itemArrayM.push(ACCT_BAL);
				itemArrayR.push(ACCT_AMT);
			}else if(msgTypValue == '1000000007'){//贷款逾期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(MANAGER_PHONE);
				itemArrayR.push(ACCT_ORGNO);
				itemArrayL.push(ADDR);
				itemArrayM.push(CARD_NO);
				itemArrayR.push(TZ_AMT);
				itemArrayL.push(LAST_HK_DATE);
			}else if(msgTypValue == '1000000008'){//信用卡逾期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(MANAGER_PHONE);
				itemArrayR.push(ACCT_ORGNO);
				itemArrayL.push(ADDR);
				itemArrayM.push(CARD_NO);
				itemArrayR.push(TZ_AMT);
				itemArrayL.push(LAST_HK_DATE);
			}else if(msgTypValue == '1000000009'){//睡眠客户提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(JY_DATE);
				itemArrayR.push(ACCT_BAL);
				
				itemArrayL.push(ACCT_NO);
				itemArrayM.push(ACCT_ORGNO);
				
			}else if(msgTypValue == '2000000001'){//理财产品到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '2000000002'){//信托产品到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '2000000003'){//本外币定期存款到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '2000000004'){//贷款产品到期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '2000000005'){//客户账户大额变动提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ADDR);
				itemArrayL.push(MANAGER_PHONE);
				itemArrayM.push(ACCT_ORGNO);
				itemArrayR.push(JY_TYP);
				itemArrayL.push(JY_DATE);
				itemArrayM.push(ACCT_BAL);
				itemArrayR.push(ACCT_AMT);
			}else if(msgTypValue == '2000000006'){//贷款逾期提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '2000000007'){//客户需求提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}else if(msgTypValue == '2000000008'){//远期信用证、远期托收项下承兑到期日提醒
				itemArrayL.push(CUST_NAME);
				itemArrayM.push(ACCT_NO);
				itemArrayR.push(ACCT_NAME);
			}
			
			
			var dataFormPanel = new Ext.FormPanel({
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				width : '100%',
				height : '100%',
				items : [{
							layout : 'column',
							items : [{
								layout : 'form',
								columnWidth : .33,
								labelWidth : 100,
								items : itemArrayL
							},{
								layout : 'form',
								columnWidth : .33,
								labelWidth : 100,
								items : itemArrayM
							},{
								layout : 'form',
								columnWidth : .33,
								labelWidth : 100,
								items : itemArrayR
							}]
						},{ layout : 'column',
							items : [{
								layout : 'form',
								columnWidth : .7,
								labelWidth : 100,
								items : MSG_REMARK
							}]
					}]
			});
			dataFormPanel.getForm().loadRecord(record);
			var dataInfoWindow = new Ext.Window({
				title : '<span style="font-weight:normal">信息提醒</span>',
				height : 300,
				width : 850,
				buttonAlign : 'center',
				draggable : true,// 是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				autoScroll : true,
				//closeAction : 'hide',
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				border : false,
				animCollapse : true,
				pageY : 20,
				items : [dataFormPanel],
				buttons : [{
							text : '关    闭',
							handler : function() {
								dataInfoWindow.close();
							}
						}]
			});
			dataInfoWindow.show();
		}
		// 批量设为已读
		function read() {
				var selectLength = listPanel.grid.getSelectionModel().getSelections().length;
				if (selectLength < 1) {
					Ext.Msg.alert('提示', '请选择需要设为已读的记录!');
				} else {
					Ext.MessageBox.confirm('提示', '确定设为已读吗?', function(buttonId) {
						if (buttonId.toLowerCase() == "no") {
							return;
						}
						var selectRe;
						var tempId;
						var idStr = '';
						for (var i = 0; i < selectLength; i++) {
							selectRe = listPanel.grid.getSelectionModel().getSelections()[i];
							tempId = selectRe.data.ID;
							idStr += tempId;
							if (i != selectLength - 1)
								idStr += ',';
						}
						Ext.Ajax.request({
									url : basepath
											+ '/workplatremindlist!read.json?idStr='+ idStr,
									method : 'POST',
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function(){
										 Ext.Msg.alert('提示', '操作成功');
										 listPanel.loadCurrData();
									},
									failure : function(){
										 Ext.Msg.alert('提示', '操作失败');
									}
								});

					});
		}
	}

});