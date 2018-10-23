	

	var planStore = new Ext.data.JsonStore({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/market-plan.json'
           
		}),
		fields : [ {name:'planId',mapping:'planId'}, {name:'planName',mapping:'planName'} ],
		reader : new Ext.data.JsonReader({
			root:'list'
		}, [ {
			name : 'planId',
			mapping : 'planId'
		}, {
			name : 'planName',
			mapping : 'planName'
		} ])
	});
	

	var addActivityForm = null;
	
	// 新增活动展示的from
	var addActivityForm = new Ext.form.FormPanel({
		labelWidth : 150,
		height : 400,
		frame : true,
		id:'allForms',
		labelAlign : 'right',
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'marketActivityName',
					xtype : 'textfield',
					fieldLabel : '*营销活动名称',
					allowBlank : false,
					blankText : '此项不能为空',
					maxLength:100,
					width : '100',
					anchor : '90%'
				}, new Ext.ux.form.CustomerQueryField({ 
					fieldLabel : '客户名称', 
					labelStyle: 'text-align:right;',
					name : 'activityCustomerName',
					id:'activity_Customer_Name',
					 editable : false,
					 allowBlank:false,//不允许为空
	                 blankText:"不能为空，请填写",
					singleSelected:true,
					width : 100,
	 				anchor : '90%',
	 				callback :function(){
	 						var cust_id = null;
									var cust_name = null;
									cust_name = Ext.getCmp('activity_Customer_Name').getValue();
									if (cust_name != null && cust_name != '') {
										cust_id = Ext.getCmp('activity_Customer_Name').customerId.aId[0];
										addActivityForm.getForm().findField('activityCustomerId').setValue(cust_id);
									}
	 				}
				}),
//					{
//					name : 'activityCustomerName',
//					//readOnly : true,
//					xtype : 'textfield',
//					fieldLabel : '客户名称',
//					editable :false,
//					allowBlank : false,
//					blankText : '客户名称不能为空',
//					maxLength:100,
//					//editable: false,
//					width : '100',
//					anchor : '90%',
//					listeners:{
//						'focus':function(){
//							custSearchWindow.show();
//						}
//					}
//				},
				{
					name : 'activityStartDate',
					id :'activity_Start_Date',
					xtype : 'datefield',
					format : 'Y-m-d',
//					allowBlank : false,
//					blankText : '开始日期不能为空',
					editable : false,
					allowBlank : false,
					blankText : '此项不能为空',
					fieldLabel : '开始日期',
					width : 100,
					anchor : '90%'

				} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					store : planStore,
					xtype : 'combo', resizable : true,
					fieldLabel : '营销计划',
					name : 'planId',
					hiddenName : 'planId',
					valueField : 'planId',
					displayField : 'planName',
					mode : 'local',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,
					blankText : '此项不能为空',
					selectOnFocus : true,
					width : '100',
					anchor : '90%'
				}, new Ext.ux.form.ExeTeamField({
					fieldLabel : '执行团队',
					editable :false,
					allowBlank : false,
					name : 'activityOperaterName',
					width : '100',
					anchor : '90%'
				}),
					{
					xtype : 'datefield',
					format : 'Y-m-d',
					editable : false,
					width : '100',
					fieldLabel : '结束日期',
					allowBlank : false,
					blankText : '结束日期不能为空',
					name : 'activityEndDate',
					id :'activity_End_Date',
					anchor : '90%'			
				} ]
			}

			]
		}, {
			layout : 'form',
			buttonAlign : 'center',
			items : [ {
				xtype : 'textarea',
				width : 200,
				fieldLabel : '营销活动地点',
				maxLength:400,
				name : 'marketActivityAddress',
				anchor : '90%'
			}, {
				xtype : 'textarea',
				width : 200,
				maxLength:400,
				fieldLabel : '营销活动目的',
				allowBlank : false,
				blankText : '此项不能为空',
				name : 'activityAim',
				anchor : '90%'
			}, {
				xtype : 'textarea',
				width : 200,
				maxLength:400,
				fieldLabel : '活动完成情况',
				name : 'activityComplementCircumstance',
				anchor : '90%'
			}, {
				xtype : 'hidden',
				fieldLabel : '客户ID',
				name : 'activityCustomerId',
				anchor : '90%'
			}, {
				xtype : 'hidden',
				fieldLabel : '执行团队ID',
				name : 'activityOperaterId',
				anchor : '90%'
			}, {
				xtype : 'hidden',
				fieldLabel : '创建人',
				name : 'createUser',
				anchor : '90%'
			}, {
				xtype : 'hidden',
				fieldLabel : '创建人id',
				name : 'createUserid',
				anchor : '90%'
			}, {
				xtype : 'hidden',
				fieldLabel : '创建日期',
				name : 'createDate',
				anchor : '90%'
			}
			],

			buttons : [

			{

				text : '保存',
				handler : function() {
            		if(!addActivityForm.getForm().isValid())
					{ 
            			Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return false;
					}
									var start = Ext.getCmp('activity_Start_Date').getValue();
									var end = Ext.getCmp('activity_End_Date').getValue();
									if (start == ''&&end!='') {
										Ext.Msg.alert('消息框','请先选择开始时间！');
										Ext.getCmp('activity_End_Date').reset();
										return false;
									} else if (end!=''&&start > end) {
										Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
										Ext.getCmp('activity_End_Date').reset();
										return false;
									}
					Ext.Ajax.request({
						url : basepath + '/market-activity.json?a=1',
						method : 'POST',
						form : addActivityForm.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							store.reload();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{

							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
							store.reload();
						}
					});
					addActivityWindow.hide();
					addActivityForm.getForm().reset();
				}

			}, {
				text : '取  消',
				handler : function() {
					addActivityWindow.hide();
				}
			} ]
		}

		]

	});


var addActivityWindow = new Ext.Window({
		title : '营销活动新增',
		plain : true,
		layout : 'fit',
		width : 880,
		height : 450,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		constrain : true,
		items : [ addActivityForm ]
	});
	

