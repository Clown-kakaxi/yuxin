Ext.onReady(function() {
	var custid =oCustInfo.cust_id;

	
	
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
						columnWidth : .24,
						labelWidth : 105,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '户主姓名',
							name : 'houseMaster',
							labelStyle : 'text-align:right;',
							minLengthText :'内容过长！',
							readOnly : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '户主代码',
							name : 'houseCode',
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
						columnWidth : .24,
						labelWidth : 105,
						items : [ {
							xtype : 'numberfield',
							fieldLabel : '家庭人数',
							name : 'familyNum',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '其中：劳动力人数',
							name : 'workNum',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .48,
						labelWidth : 105,
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
			}, {
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
						columnWidth : .24,
						labelWidth : 105,
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
						columnWidth : .24,
						labelWidth : 105,
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
							maxLength :17,
							minLengthText :'内容过长！',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						id :'type_id3',
						layout : 'form',
						columnWidth : .24,
						labelWidth : 105,
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
						columnWidth : .24,
						labelWidth : 105,
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
						labelWidth : 105,
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
						labelWidth : 105,
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
					text :'确认',
					handler:function(){
						if (!simple.form.isValid()) {
							Ext.Msg.alert('提示', '输入不合法，请重新输入');
							return false;
						}
						
						
						Ext.Ajax.request({
							url : basepath + '/perCustFamily.json',
							method : 'POST',
							form : simple.form.id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								initForm();
							},
							failure : function() {
								Ext.Msg.alert('提示', '操作失败');
							}
						});
					
					}
			}]
		});
		initForm();
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
		var viewport_center = new Ext.Panel({
			 renderTo:'viewport_center',
//			 id:'viewport_center',
				frame:true,
				height:document.body.scrollHeight-30,
				layout:'fit',
				autoScroll:true,
			
					items: [simple] 

				});
		/*
		 * function editInit(){ window.location.href =
		 * 'familyMemberInformation.html' ; };
		 */
	});