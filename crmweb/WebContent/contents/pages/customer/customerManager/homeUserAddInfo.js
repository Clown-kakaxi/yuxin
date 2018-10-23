Ext.onReady(function() {
	//var custid =oCustInfo.cust_id;
	var custid ='';

	 var boxstore2 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['未婚', '01'], ['已婚', '02'],['离异','03'],['其他','04']]
				});

	 var boxstore6 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['好', '01'], ['一般', '02'],['差','03']]
				});

	 var boxstore4 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['工人', '01'], ['农民', '02'],['下岗','03'],['现役军人','04'],['退役军人','05'],['医生','06'],['教师','07'],['公务员','08'],['事业单位','09'],['金融机构','10'],['学生','11'],['外企','12'],['国企','13'],['退离休人员','14'],['个体户','15'],['无业人员','16'],['私营企业主','17'],['其他','18']]
				});

	 var boxstore1 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['博士及以上', '01'], ['研究生', '02'],['本科生','03'],['专科生','04'],['大专','05'],['中专','06'],['技术学校','07'],['高中','08'],['初中','09'],['小学','10'],['文盲或半文盲','11'],['未知','12']]
				});
	 
	
	
	var type_Store = new Ext.data.Store({  
	restful:true,   
	sortInfo:{
			field:'key',
			direction:'ASC'
		},
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100047'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	type_Store.load();
	
	
	var rsRecord = Ext.data.Record.create( [ {
		name : 'houseCode',
		mapping:'HOUSE_CODE'
	}, {
		name : 'houseMaster',
			mapping:'HOUSE_MASTER'
	}, {
		name : 'familyNum',
			mapping:'FAMILY_NUM'
	}, {
		name : 'workNum',
			mapping:'WORK_NUM'
	}, {
		name : 'liveAddr',
			mapping:'LIVE_ADDR'
	}, {
		name : 'manageScal',
			mapping:'MANAGE_SCAL'
	}, {
		name : 'phoneNo',
			mapping:'PHONE_NO'
	}, {
		name : 'custId',
			mapping:'CUST_ID'
	}

	, {
		name : 'famiStat',
			mapping:'FAMI_STAT'
	}, {
		name : 'nebRelt',
			mapping:'NEB_RELT'
	}, {
		name : 'houseStat',
			mapping:'HOUSE_STAT'
	}, {
		name : 'creditStat',
		mapping:'CREDIT_STAT'
	}, {
		name : 'debtStat',
			mapping:'DEBT_STAT'
	}, {
		name : 'familyRelt',
			mapping:'FAMILY_RELT'
	}, {
		name : 'workStat',
			mapping:'WORK_STAT'
	}, {
		name : 'badnessRecord',
			mapping:'BADNESS_RECORD'
	}, {
		name : 'carVer',
			mapping:'CAR_VER'
	}, {
		name : 'awardYn',
		mapping:'AWARD_YN'
	}, {
		name : 'comHouse',
			mapping:'COM_HOUSE'
	}, {
		name : 'villaComent',
			mapping:'VILLA_COMENT'
	}, {
		name : 'famiCon',
			mapping:'FAMI_CON'
	}, {
		name : 'awarYn',
			mapping:'AWARD_YN'
	}, {
		name : 'awardYnLine',
			mapping:'AWARD_YN_LINE'
	}, {
		name : 'dutyOfficer',
			mapping:'DUTY_OFFICER'
	}, {
		name : 'familyFinal',
			mapping:'FAMILY_FINAL'
	}, {
		name : 'familyActual',
			mapping:'FAMILY_ACTUAL'
	}
	]);

	var rsreader = new Ext.data.JsonReader( {
		successProperty : 'success',
		idProperty : 'CUST_ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, rsRecord);

		var simple = new Ext.FormPanel( {
			reader : rsreader,
			id:'simple',
			frame : true,
			height : 475,
			autoScroll : true,
			buttonAlign :'center',
			items : [ {
				xtype : 'fieldset',
				title : '客户家庭基本信息',
				titleCollapse : true,
				//collapsible : true,
				autoHeight : true,
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '户主姓名',
							name : 'houseMaster',
							allowBlank:false,
							labelStyle : 'text-align:right;',
							minLengthText :'内容过长！',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '户主代码',
							name : 'houseCode',
							allowBlank:false,
							labelStyle : 'text-align:right;',
							maxLength :20,
							minLengthText :'内容过长！',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '固定电话',
							name : 'phoneNo',
							maxLength :20,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'numberfield',
							fieldLabel : '家庭人数',
							name : 'familyNum',
							allowBlank:false,
							labelStyle : 'text-align:right;',
							
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '其中：劳动力人数',
							name : 'workNum',
							allowBlank:false,
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '家庭地址',
							name : 'liveAddr',
							maxLength :128,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '经营项目及规模',
							name : 'manageScal',
							maxLength :60,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					} ]
				} ]
			},
			/**户主信息START****************************************************/
			{
				xtype : 'fieldset',
				title : '户主信息',
//				collapsed : true,
//				collapsible : true,
				titleCollapse : true,
				// hideCollapseTool:true,
				autoHeight : true,
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'name',
							// style:'text-align:right;',
//							maxLength :1,
//							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '与户主关系',
							name : 'masterRelt',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							value:'01',
							readOnly:'true',
//							maxLength :1,
//							minLengthText :'内容过长！',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '身份证号码',
							name : 'identiCardNo',
							// style:'text-align:right;',
							maxLength :20,
							allowBlank:false,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
//							xtype : 'textfield',
//							fieldLabel : '文化程度',
//							name : 'eduLevel',
//							// style:'text-align:right;',
//							labelStyle : 'text-align:right;',
////							maxLength :1,
////							minLengthText :'内容过长！',
//							anchor : '95%'
							fieldLabel : '文化程度',
							hiddenName  : 'eduLevel',
							xtype : 'combo',
							store : boxstore1,
							mode : 'local',
							valueField : 'code',
							displayField : 'name',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							editable:false,		
							labelStyle : 'text-align:right;',
							anchor : '95%'	
						}, {
							xtype : 'textfield',
							fieldLabel : '村编号',
							name : 'villaNo',
							maxLength :20,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						} ]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
//							xtype : 'textfield',
//							fieldLabel : '婚姻状况',
//							name : 'marrgStatus',
//							// style:'text-align:right;',
//							labelStyle : 'text-align:right;',
////							maxLength :1,
////							minLengthText :'内容过长！',
//							anchor : '95%'
							fieldLabel : '婚姻状况',
							hiddenName  : 'marrgStatus',
							xtype : 'combo',
							store : boxstore2,
							mode : 'local',
							valueField : 'code',
							displayField : 'name',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							editable:false,		
							labelStyle : 'text-align:right;',
							anchor : '95%'	
						}, {
//							xtype : 'textfield',
//							fieldLabel : '健康状况',
//							name : 'healthStatus',
//							// style:'text-align:right;',
//							labelStyle : 'text-align:right;',
////							maxLength :1,
////							minLengthText :'内容过长！',
//							anchor : '95%'
							fieldLabel : '健康状况',
							hiddenName  : 'healthStatus',
							xtype : 'combo',
							store : boxstore6,
							mode : 'local',
							valueField : 'code',
							displayField : 'name',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							editable:false,		
							labelStyle : 'text-align:right;',
							anchor : '95%'	
						}, {
//							xtype : 'textfield',
//							fieldLabel : '职业大类',
//							name : 'profeKind',
////							maxLength :40,
////							minLengthText :'内容过长！',
//							labelStyle : 'text-align:right;',
//							anchor : '95%'
							fieldLabel : '职业大类',
							hiddenName  : 'profeKind',
							xtype : 'combo',
							store : boxstore4,
							mode : 'local',
							valueField : 'code',
							displayField : 'name',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							editable:false,		
							labelStyle : 'text-align:right;',
							anchor : '95%'	
						}, {
							xtype : 'textfield',
							fieldLabel : '职业（经营项目）',
							name : 'manageItem',		
//							maxLength :17,
//							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '村姓名',
							name : 'villaName',
							maxLength :20,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							xtype : 'numberfield',
							fieldLabel : '年收入',
							name : 'yeIncome',		
							allowBlank:false,
//							maxLength :17,
//							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '手机号码',
							name : 'mobile',		
							maxLength :11,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textarea',
							fieldLabel : '备注',
							name : 'remark',
							// style:'text-align:right;',
							maxLength :200,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}]
				}
				]
			
			}, 
			/**户主信息END****************************************************/
			{
				xtype : 'fieldset',
				title : '其他信息',
//				collapsed : true,
//				collapsible : true,
				titleCollapse : true,
				// hideCollapseTool:true,
				autoHeight : true,
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '家庭参保情况',
							name : 'famiStat',
							// style:'text-align:right;',
							maxLength :1,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '不良记录',
							name : 'badnessRecord',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							maxLength :1,
							minLengthText :'内容过长！',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '汽车',
							name : 'carVer',
							// style:'text-align:right;',
							maxLength :50,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '是否授信',
							name : 'awardYn',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							maxLength :1,
							minLengthText :'内容过长！',
							anchor : '95%'
						} ]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							id :'type_id',
							fieldLabel : '邻里关系',
							hiddenName  : 'nebRelt',
							xtype : 'combo',
							store : type_Store,
							mode : 'local',
							valueField : 'key',
							displayField : 'value',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							editable:false,		
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							id :'type_id2',
							fieldLabel : '负债情况',
							hiddenName  : 'debtStat',
							xtype : 'combo',
							store : type_Store,
							mode : 'local',
							valueField : 'key',
							displayField : 'value',
							editable:false,	
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '商品房',
							name : 'comHouse',
							maxLength :40,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '授信金额',
							name : 'awardYnLine',		
							allowBlank:false,
//							maxLength :17,
//							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							fieldLabel : '住宅情况',
							hiddenName  : 'houseStat',
							xtype : 'combo',
							store : type_Store,
							mode : 'local',
							valueField : 'key',
							displayField : 'value',
							editable:false,	
							typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							id :'type_id4',
							fieldLabel : '家庭和睦',
							hiddenName  : 'familyRelt',
							xtype : 'combo',
							store : type_Store,
							mode : 'local',
							valueField : 'key',
							displayField : 'value',
							editable:false,	
							typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '家庭经济实力',
							name : 'familyFinal',
							// style:'text-align:right;',
							maxLength :5,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '责任人',
							name : 'dutyOfficer',
							maxLength :10,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						} ]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							id :'type_id5',
							fieldLabel : '信用情况',
							hiddenName  : 'creditStat',
							xtype : 'combo',
							store : type_Store,
							mode : 'local',
							valueField : 'key',
							displayField : 'value',
							editable:false,	
							typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							id :'type_id6',
							fieldLabel : '工作稳定性',
							hiddenName  : 'workStat',
							xtype : 'combo',
							store : type_Store,
							mode : 'local',
							valueField : 'key',
							displayField : 'value',
							editable:false,	
							typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '家庭综合实力',
							name : 'familyActual',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					} ]
				},{
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .99,
						labelWidth : 120,
						items : [ {
							xtype : 'textarea',
							fieldLabel : '村组评价',
							name : 'villaComent',
							// style:'text-align:right;',
							maxLength :400,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						} ]
					} ]
				},{
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .99,
						labelWidth : 120,
						items : [ {
							xtype : 'textarea',
							fieldLabel : '客户大事件',
							name : 'famiCon',
							// style:'text-align:right;',
							maxLength :60,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						} ]
					} ]
				},{
					xtype:'hidden',
					name : 'awarYn'
				},{
					xtype:'hidden',
					name :'custId'
				}
				]
			}
			],
			buttons:[{
					text :'保存',
					handler:function(){
						if (!simple.form.isValid()) {
							Ext.Msg.alert('提示', '输入不合法，请重新输入');
							return false;
						}
						
						
						Ext.Ajax.request({
							url : basepath + '/homeUserAdd.json',
							method : 'POST',
							form : simple.form.id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								initForm();
							},
							failure:function(a,b){
								var t = Ext.decode(a.responseText);
								Ext.Msg.alert('系统提示',t.message);
							}
						});
					
					}
			},{
				text:'返回',
				handler:function(){
					javascript :history.back(-1);
				}
			}]
		});
		//initForm();
		function initForm(){
			simple.getForm().load( {
					restful : true,
					url : basepath + '/perCustFamilyQuery.json',
					params : {
						'condition' : Ext.encode( {
							custId : custid
						})
		
					},
					method : 'GET'
		});
		}
//		var viewport_center = new Ext.Panel({
//			 renderTo:'viewport_center',
////			 id:'viewport_center',
//				frame:true,
//				height:document.body.scrollHeight-30,
//				layout:'fit',
//				autoScroll:true,
//			
//					items: [simple] 
//
//				});
		var viewport = new Ext.Viewport( {
			height:document.body.scrollHeight-30,
			layout:'fit',
			autoScroll:true,
			items : [ simple ]
		});
		/*
		 * function editInit(){ window.location.href =
		 * 'familyMemberInformation.html' ; };
		 */
	});