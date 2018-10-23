Ext.onReady(function() {
	Ext.QuickTips.init();
	var sum_flag = false;
	// 渠道类型下拉框的数据查询
	// var
	// channelTypeStore=util.form._store('/channel-type.json','channelTypeId','channelTypeName');
	// var testStore=util.form._store('/lookup.json?name=IS_SMALL');
	// channelTypeStore.load();
	// testStore.load();
	var riskTypeStore = new Ext.data.SimpleStore({
				fields : ['key', 'value'],
				data : [['1', '保守型'], ['2', '轻度保守型'], ['3', '均衡型'],['4', '轻度进取型'],['5', '进取型']]
			});

	var title_record = Ext.data.Record.create([{
				name : 'titleId',
				mapping : 'TITLE_ID'
			}, {
				name : 'titleName',
				mapping : 'TITLE_NAME'
			}, {
				name : 'titleRemark',
				mapping : 'TITLE_REMARK'
			}, {
				name : 'titleIdL',
				mapping : 'titleId'
			}, {
				name : 'qaId',
				mapping : 'QA_ID'
			}]);

	var title_qa_rs = Ext.data.Record.create([{
				name : 'custqtId',
				mapping : 'CUST_Q_T_ID'
			}, {
				name : 'custqId',
				mapping : 'CUST_Q_ID'
			}, {
				name : 'custSelectContent',
				mapping : 'CUST_SELECT_CONTENT'
			}, {
				name : 'qaTitle',
				mapping : 'QA_TITLE'
			}, {
				name : 'scoring',
				mapping : 'SCORING'
			},{
				name : 'titleRemark',
				mapping : 'TITLE_REMARK'
			}]);
	var title_store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/TitleQuery!loadTitleRs.json'
						// success:function(response){
						// Ext.Msg.alert("数据",response.responseText);
						// }
					}),
				reader : new Ext.data.JsonReader({
							successProperty : 'success',
							// idProperty : 'TITLE_ID',
							messageProperty : 'message',
							root : 'data',
							totalProperty : 'count'
						}, title_record)
			});
	title_store.load();
	
		var title_qa_rs = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/RiskEvaluation!loadCustRiskQa.json'
					}),
				reader : new Ext.data.JsonReader({
							successProperty : 'success',
							messageProperty : 'message',
							root : 'data',
							totalProperty : 'count'
						}, title_qa_rs)
			});
			
		var search_cust = new Ext.ux.form.CustomerQueryField({ 
				fieldLabel : '客户名称', 
				labelStyle: 'text-align:right;',
				name : 'custName',
				id:'custName',
				 editable : false,
				 allowBlank:false,//不允许为空
                 blankText:"不能为空，请填写",
				singleSelected:true,
				width : 150,
 				anchor : '100%',
 				callback :function(){
 						var cust_id = null;
								var cust_name = null;
								cust_name = Ext.getCmp('custName').getValue();
								if (cust_name != null && cust_name != '') {
									cust_id = Ext.getCmp('custName').customerId.aId[0];
									cust_risk_form.getForm().findField('custNo').setValue(cust_id);
								}
 				}
			});
			
	var cust_risk_form = new Ext.form.FormPanel({
				id : 'cust_risk_form',
				height : 80,
				labelAlign : 'right',
				frame : true,
				items : [{
							layout : 'column',
							labelWidth : 100,
							items : [{
										layout : 'form',
										columnWidth : .25,

										items : [search_cust]
									}, {
										layout : 'form',
										columnWidth : .25,
										items : [{
													name : 'adjustmentValue',
													id : 'adjustmentValue',
													xtype : 'numberfield',
													fieldLabel : '调整值',
//													regex:/^[-]?\d+\.?\d*$/,
//													regexText:'输入格式不正确',
													maxLength : 10,
													maxLengthText : '数字长度为10',
													value :0,
													allowBlank:false,//不允许为空
													blankText:"不能为空，请填写",
													width : '100',
													anchor : '100%'
												}]
									}, {
										layout : 'form',
										columnWidth : .25,

										items : [{
													name : 'indageteQaScoring',
													id : 'indageteQaScoring',
													xtype : 'textfield',
													fieldLabel : '调查问卷得分',
													maxLength : 10,
													maxLengthText : '最大长度为10',
													blankText : '该项为必填项',
													readOnly  : true,
													width : '100',
													anchor : '100%'
												}]
									}, {
										layout : 'form',
										columnWidth : .25,
										items : [{
													
													id : 'riskCharactType',
													xtype : 'textfield',
													name : 'riskCharactType',
													fieldLabel : '风险特性分类',
													readOnly  : true,
													width : '100',
													anchor : '100%'
												}]
									}]
						}, {
							layout : 'column',
							labelWidth : 100,
							items : [{
										layout : 'form',
										columnWidth : .25,
										items : [{
													name : 'custOtherInfo',
													xtype : 'textfield',
													fieldLabel : '客户其他信息',
													width : '100',
													anchor : '100%'
												}]
									}, {
										layout : 'form',
										columnWidth : .25,
										items : [{
													name : 'evaluateRelatTelephone',
													xtype : 'textfield',
													fieldLabel : '评估人联系电话',
													maxLength : 20,
													maxLengthText : '最大长度为20',
													allowBlank:false,//不允许为空
													blankText : '该项为必填项',
													width : '100',
													anchor : '100%'
												}]
									}]
						},{
							xtype : 'hidden',
							name : 'custNo'
						},{
							xtype : 'hidden',
							name : 'evaluateName'
						}, {
							xtype : 'hidden',
							name : 'evaluateInst'
						}, 
							{
							xtype : 'datefield',
							id :'evaluateDate',
							hidden :true,
							name : 'evaluateDate'
						}, 
							{
							xtype : 'hidden',
							name : 'custRiskCharact'
						}, {
							xtype : 'hidden',
							name : 'custqId'
						}]
			});
			

	var rd_set = new Ext.form.FieldSet({
				xtype : 'fieldset',
				title : '问卷调查',
				labelWidth : 200,
				labelAlign : 'right',
				collapsible : true,
				items : []
			});

	var opForm = new Ext.Panel({
		id : 'opForm',
		layout : 'form',
		autoScroll : true,
		labelAlign : 'right',
		frame : true,
		buttonAlign : "center",
		items : [cust_risk_form, rd_set],
		listeners : {
			beforerender : function() {
				var title_count = null;
				var title = null;
				var title_rs = null;
				var rs = null;
				title_count = title_store.getCount();
				for (var i = 0; i < title_count; i++) {
					title = title_store.getAt(i);
					title_rs = new Array();
					for (var b = 0; b < title.json.titleIdL.length; b++) {
						rs = title.json.titleIdL[b];
						title_rs.push(new Ext.form.Radio({
									boxLabel : rs.result,
									name : 'result' + i,
									inputValue : rs.resultId
								}));
					}
					new Ext.form.RadioGroup({
								id : 'rg' + i,
								fieldLabel : title.json.titleName,
								items : [title_rs]
							});
					rd_set.add(Ext.getCmp('rg' + i));
					rd_set.doLayout();
				}
				

			}

		},
		buttons : [{
			text : '统计得分',
			id : 'score_count',
			handler : function() {
				sum_flag = false;
				var rdName = null;
				var title = null;
				var rs = null;
				var adjustNumber = 0;
				var title_result =null;
				var rsCount = 0;
				title_count = title_store.getCount();
				for (var j = 0; j < title_count; j++) {
					title = title_store.getAt(j);
					if (Ext.getCmp('rg' + j).getValue() != null) {
						rdName = Ext.getCmp('rg' + j).getValue().inputValue;
						for (var k = 0; k < title.json.titleIdL.length; k++) {
							rs = title.json.titleIdL[k];
							if (rs.resultId == rdName) {
								rsCount = parseFloat(rsCount)
										+ parseFloat(rs.resultScoring);
							}
						}
					}
				}
				for (var i = 0; i < title_count; i++) {
					if (Ext.getCmp('rg' + i).getValue() != null) {
						rsId = Ext.getCmp('rg' + i).getValue().inputValue;
						if (rsId != '') {
							title_result += rsId;
							if (i != title_count - 1) {
								title_result += ',';
							}
						}
					} else {
						Ext.Msg.alert("提示", "您还有:"+ Ext.getCmp('rg' + i).fieldLabel+ " 问题没回答！");
						return;
					}
				}
				adjustNumber = Ext.getCmp('adjustmentValue').getValue();
				if (adjustNumber == null) {
					Ext.Msg.alert("提示", "调整值不能为空！");
					return;
				} else {
					sum_flag = true;
					Ext.getCmp('indageteQaScoring').setValue(rsCount);
					if (parseFloat(rsCount) + parseFloat(adjustNumber) <= 10) {
						Ext.getCmp('riskCharactType').setValue('保守型');
					} else if (parseFloat(rsCount) + parseFloat(adjustNumber) <= 15) {
						Ext.getCmp('riskCharactType').setValue('轻度保守型');
					} else if (parseFloat(rsCount) + parseFloat(adjustNumber) <= 22) {
						Ext.getCmp('riskCharactType').setValue('均衡型');
					} else if (parseFloat(rsCount) + parseFloat(adjustNumber) <= 30) {
						Ext.getCmp('riskCharactType').setValue('轻度进取型');
					} else {
						Ext.getCmp('riskCharactType').setValue('进取型');
					} 
					Ext.Msg.alert("提示", "得分已统计！");
				}
			}
		}, {
			text : '保存',
			id : 'save_info',
			handler : function() {
				var title_result = '';
				if (sum_flag == false) {
					Ext.Msg.alert("提示", "请先统计得分！");
					return ;
				}
				if (!cust_risk_form.form.isValid()) {
					Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
					sum_flag = false;
					return;
				}
					title_count = title_store.getCount();
					for (var i = 0; i < title_count; i++) {
					if (Ext.getCmp('rg' + i).getValue() != null) {
						rsId = Ext.getCmp('rg' + i).getValue().inputValue;
						if (rsId != '') {
							title_result += rsId;
							if (i != title_count - 1) {
								title_result += ',';
							}
						}
					}}
					var typeValue = Ext.getCmp('riskCharactType').getValue();
					if(typeValue=='保守型'){
							Ext.getCmp('riskCharactType').setValue('1');
					}else if(typeValue=='轻度保守型'){
							Ext.getCmp('riskCharactType').setValue('2');
					}else if(typeValue=='均衡型'){
							Ext.getCmp('riskCharactType').setValue('3');
					}else if(typeValue=='轻度进取型'){
							Ext.getCmp('riskCharactType').setValue('4');
					}else if(typeValue=='进取型'){
							Ext.getCmp('riskCharactType').setValue('5');
					}
				Ext.Ajax.request({
							url : basepath+ '/RiskEvaluation!addCustRiskEvaluation.json',
							form : cust_risk_form.form.id,
							method : 'POST',
							params : {
								title_result : title_result
							},
							failure : function(form, action) {
								Ext.MessageBox.alert('新增操作', '新增失败！');
								sum_flag = false;
							},
							success : function(response) {
								Ext.MessageBox.alert('新增操作', '新增成功！');
								listPanel.grid.getStore().reload();
								cust_risk_form.form.reset();
								title_count = title_store.getCount();
								for (var j = 0; j < title_count; j++) {
									Ext.getCmp('rg' + j).reset();
								}
								sum_flag = false;
								opWin.hide();
							}
						});

			}
		}, {
			text : '修改',
			id : 'update_info',
			handler : function() {
				var title_count = null;
			if (sum_flag == false) {
					Ext.Msg.alert("提示", "请先统计得分！");
					return ;
				}else{
							search_cust.setDisabled(false);
					var typeValue = Ext.getCmp('riskCharactType').getValue();
					if(typeValue=='保守型'){
							Ext.getCmp('riskCharactType').setValue('1');
					}else if(typeValue=='轻度保守型'){
							Ext.getCmp('riskCharactType').setValue('2');
					}else if(typeValue=='均衡型'){
							Ext.getCmp('riskCharactType').setValue('3');
					}else if(typeValue=='轻度进取型'){
							Ext.getCmp('riskCharactType').setValue('4');
					}else if(typeValue=='进取型'){
							Ext.getCmp('riskCharactType').setValue('5');
					}
							Ext.Ajax.request({
								url : basepath+ '/RiskEvaluation!updateCustRiskEvaluation.json',
								form : cust_risk_form.form.id,
								method : 'POST',
								failure : function(form, action) {
									Ext.MessageBox.alert('修改操作', '修改失败！');
									sum_flag =false;
								},
								success : function(response) {
									Ext.MessageBox.alert('修改操作', '修改成功！');
									listPanel.grid.getStore().reload();
									cust_risk_form.form.reset();
									title_count = title_store.getCount();
									for (var j = 0; j < title_count; j++) {
										Ext.getCmp('rg' + j).reset();
									}
									sum_flag == false;
									opWin.hide();
								}
							});
						
				}

			}

		}, {
			text : '重置',
			id : 'reset_info',
			handler : function() {
				sum_flag = false;
				cust_risk_form.form.reset();
				title_count = title_store.getCount();
				for (var j = 0; j < title_count; j++) {
					Ext.getCmp('rg' + j).reset();
				}

			}
		}, {
			text : '取消',
			handler : function() {
				sum_flag = false;
				cust_risk_form.form.reset();
				title_count = title_store.getCount();
				for (var j = 0; j < title_count; j++) {
					Ext.getCmp('rg' + j).reset();
				}
				opWin.hide();
			}
		}]

	});

	var opWin = new Ext.Window({
				plain : true,
				layout : 'fit',
				resizable : true,
				draggable : true,
				closable : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				shadow : true,
				loadMask : true,
				maximizable : true,
				collapsible : true,
				titleCollapse : true,
				border : false,
				width : 900,
				height : 450,
				title : '信息维护',
				items : [opForm]
			});
			
			opWin.on('hide',function(){
								sum_flag = false;
								text_arr = cust_risk_form.findByType('textfield');
									
									for(var ta=0;ta<text_arr.length;ta++){
											text_arr[ta].setReadOnly(false);
									}
									Ext.getCmp('indageteQaScoring').setReadOnly(true);
									Ext.getCmp('riskCharactType').setReadOnly(true) ;
									cust_risk_form.form.reset();
									cust_risk_form.form.render();
									title_count = title_store.getCount();
											for (var j = 0; j < title_count; j++) {
												Ext.getCmp('rg' + j).setReadOnly(false);
												Ext.getCmp('rg' + j).reset();
											}
			});

	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
				id : "listPanel",
				title : "风险评测",
				//
				// seBaseForm : true,
				stUrl : basepath + '/RiskEvaluation!indexPage.json',
				// demoData :
				// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
				// 新增URL，如果不定义则不出现新增按钮
				// addUrl : basepath + '/channel-info.json',
				// updateUrl : basepath + '/channel-info.json',
				// deUrl : basepath + '/channel-info!batchDestroy.json',
				primary : "id",
				checkbox : true,
				// 定义查询条件Form的高度
				seFormHeight : 100,
				// 定义增删详情页面弹出窗口高度
				winHeight : 450,
				// 宽度
				winWidth : 800,
				// 设置分页每页显示条数，若不设置则不出现分页栏
				pagesize : 20,
				// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
				// afterSeOneFun : function(b) {
				// //debugger;
				// Ext.getCmp('createDate').setValue(new
				// Date(b.createDate.time));
				// Ext.getCmp('updateDate').setValue(new
				// Date(b.updateDate.time));
				// },
				// 查询字段定义，若不定义则不出现查询条件From
				selectItems : {
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								border : false,
								items : [{
											name : 'custName',
											xtype : 'textfield',
											fieldLabel : '客户名称',
											width : '100',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100,
								defaultType : 'textfield',
								border : false,
								items : [{
											store : riskTypeStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '客户风险特性',
											name : 'riskCharactType',
											hiddenName : 'riskCharactType',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											width : '100',
											anchor : '90%'
										}]
							}]
				},

				// 查询列表字段定义，有header属性则在页面显示
				// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
				gclms : [{
							name : 'custqId'
						}, {
							name : 'custName',
							header : '客户名称'
						}, {
							name : 'custNo',
							header : '客户编号'
						},
						{
							name : 'riskCharactType',
							header : '客户风险特性',
							renderer:function(v){
								if(v==1){
									return v = '保守型';
								}else if(v==2){
									return v = '轻度保守型';
								}else if(v==3){
									return v = '均衡型';
								}else if(v==4){
									return v = '轻度进取型';
								}else if(v==5){
									return v = '进取型';
								}
							}
						}, {
							name : 'evaluateName',
							header : '评估人'
						}, {
							name : 'evaluateDate',
							header : '评估时间',
							type : 'date'
						}, {
							name : 'adjustmentValue'
						}, {
							name : 'custOtherInfo'
						}, {
							name : 'evaluateInst'
						}, {
							name : 'evaluateRelatTelephone'
						}, {
							name : 'indageteQaScoring'
						}, {
							name : 'custRiskCharact'
						}],
				buts : [{
							text : '新增',
							handler : function() {
								opWin.show();
								Ext.getCmp('update_info').hide();
								Ext.getCmp('save_info').show();
								Ext.getCmp('score_count').show();
								Ext.getCmp('reset_info').show();
								search_cust.setDisabled(false);

							}
						}, {
							text : '修改',
							handler : function() {
									var records = listPanel.grid.getSelectionModel().getSelections();
									var recordsLen = records.length;
									if (recordsLen !=1) {
										Ext.Msg.alert("系统提示信息","请选择其中一条记录进行修改！");
										return ;
									} else {
												opWin.show();
												text_arr = cust_risk_form.findByType('textfield');
												for(var ta=0;ta<text_arr.length;ta++){
													text_arr[ta].setReadOnly(true);
												}
												search_cust.setDisabled(true);
												Ext.getCmp('adjustmentValue').setReadOnly(false);
										cust_risk_form.form.loadRecord(records[0]);
										
										var typeValue = Ext.getCmp('riskCharactType').getValue();
										if(typeValue=='1'){
												Ext.getCmp('riskCharactType').setValue('保守型');
										}else if(typeValue=='2'){
												Ext.getCmp('riskCharactType').setValue('轻度保守型');
										}else if(typeValue=='3'){
												Ext.getCmp('riskCharactType').setValue('均衡型');
										}else if(typeValue=='4'){
												Ext.getCmp('riskCharactType').setValue('轻度进取型');
										}else if(typeValue=='5'){
												Ext.getCmp('riskCharactType').setValue('进取型');
										}
					
										title_qa_rs.load({
											params :{
											custqId : records[0].data.custqId
											},
											callback :function(r){
												for (var i = 0; i < r.length; i++) {
													title_count = title_store.getCount();
													for (var j = 0; j < title_count; j++) {
														Ext.getCmp('rg' + j).setReadOnly(true);
														arr = Ext.getCmp('rg' + j).items.items;
														for(var n=0;n<arr.length;n++){
															if(r[i].json.custSelectContent== arr[n].boxLabel){
															    arr[n].setValue(true);
															}
														
														}
													}
												}
												cust_risk_form.doLayout();
											}
										});
												
												Ext.getCmp('update_info').show();
												Ext.getCmp('save_info').hide();
												Ext.getCmp('score_count').show();
												Ext.getCmp('reset_info').hide();
									}
								
							}
						}]
			});
			listPanel.grid.purgeListeners();

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [listPanel]
			});

});