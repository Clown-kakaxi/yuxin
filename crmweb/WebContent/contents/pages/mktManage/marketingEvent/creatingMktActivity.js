
    
/*******************************************生成商机**************************************************/ 
	var activityStore = new Ext.data.JsonStore({  
    	restful:true,   
    	autoLoad :true,
        proxy : new Ext.data.HttpProxy({url:basepath+'/market-activity.json'}), 
        fields: ['marketActivityId','marketActivityName'],
        reader: new Ext.data.JsonReader({
            totalProperty : 'list'
        },[{name:'marketActivityId',mapping:'marketActivityId'},
           {name:'marketActivityName',mapping:'marketActivityName'}])});
	
	
	var chanceTypeStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=OPPOR_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
//	-------------------------------------------------------------------------------------------		
		var search_cust_add = new Ext.ux.form.CustomerQueryField({ 
				fieldLabel : '客户名称', 
				labelStyle: 'text-align:right;',
				labelWidth : 100,
				name : 'aimCustomerName',
				id:'add_aimCustomerName',
				 editable : false,
				 allowBlank:false,//不允许为空
                 blankText:"不能为空，请填写",
				singleSelected:true,
 				anchor : '90%',
 				callback :function(){
 						var cust_id = null;
								var cust_name = null;
								cust_name = Ext.getCmp('add_aimCustomerName').getValue();
								if (cust_name != null && cust_name != '') {
									cust_id = Ext.getCmp('add_aimCustomerName').customerId.aId[0];
									addChanceForm.getForm().findField('aimCustomerId').setValue(cust_id);
								}
 				}
			});
	// 新增窗口展示的from
	var addChanceForm = new Ext.FormPanel({
//		formId:'addform',
				labelWidth : 100,
				height : 250,
				frame : true,
				autoScroll : true,
				labelAlign : 'right',
//				  labelStyle: 'text-align:right;',
				buttonAlign : "center",
				items : [{
					layout : 'column',
					items : [{
								columnWidth : .5,
								layout : 'form',
								items : [
									{
										store: activityStore,
					                	xtype:'combo',
					                	resizable : true,
					                	fieldLabel:'营销活动',
					                	name : 'marketActivityId',
					                	hiddenName:'marketActivityId',
					                	valueField:'marketActivityId',
					                    displayField:'marketActivityName',
					                    mode : 'local',
					                    typeAhead: true,
					                    forceSelection: true,
					                    triggerAction: 'all',
					                    emptyText:'请选择',
					                    selectOnFocus:true,
					                    anchor : '90%'
									}, {
										store : chanceTypeStore,
										xtype : 'combo', resizable : true,
										resizable : true,
										name : 'marketOpportunityType',
										hiddenName : 'marketOpportunityType',
										fieldLabel : '商机类型',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									},
									new Ext.form.DateField({
									name : 'opportunityStartDate',
									id : 'opportunityStartDate',
									format : 'Y-m-d',
									value:'',
									editable : false,
									fieldLabel : '开始日期',
									anchor : '90%'
									}),new Ext.form.DateField({
									fieldLabel : '预计结束日期',
									value:'',
									format : 'Y-m-d',
									editable : false,
									name : 'opportunityPlanEndDate',
									id : 'opportunityPlanEndDate',
									anchor : '90%'
								})
								    ]
							},  {
								columnWidth : .5,
								layout : 'form',
								items : [									
								{
									xtype : 'textfield',
									fieldLabel : '商机名称',
									allowBlank :false,
									blankText  :'此项为必填项，请检查！',
									name : 'marketOpportunityName',
									anchor : '90%'
								},  {
									xtype : 'textfield',
									fieldLabel : '执行人',
									name : 'operUserId',
									anchor : '90%'
								},  
								search_cust_add,{
										xtype : 'hidden',
										name : 'aimCustomerId'
									}]
				}]}, 
				{
					layout : 'form',
					buttonAlign : 'center',
					items : [ {
								xtype : 'textarea',
								fieldLabel : '商机内容',
								name : 'opportunityContent',
								anchor : '95%'
							},{
								xtype : 'textarea',
								fieldLabel : '商机分析说明',
								name : 'opportunityAnalysis',
								anchor : '95%'
							},{
								xtype : 'hidden',
								fieldLabel : '商机状态',
								value : '1',
								id : 'marketOpportunityStatementNew',
								name : 'marketOpportunityStatement',
								anchor : '95%'
							}],
					buttons : [

					{

								text : '保  存',
								handler : function() {
									if (!addChanceForm.form.isValid()) {
										Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
										return;
									}
									var start = Ext.getCmp('opportunityStartDate').getValue();
														var end = Ext.getCmp('opportunityPlanEndDate').getValue();
														if(start=='' && end != ''){
															Ext.Msg.alert('消息框','请先选择开始时间！');
															Ext.getCmp('opportunityPlanEndDate').reset();
															return ;
														}else if(end != '' && start>end){
															Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
															Ext.getCmp('opportunityPlanEndDate').reset();
															return;
														}
									Ext.Ajax.request({
										url : basepath+'/market-opportunity.json',
										method : 'POST',
										form : addChanceForm.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											Ext.Msg.alert('提示', '操作成功');
											store.reload();
										},
										failure : function(response) {
											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
											store.reload();
										}
									});
									addChanceWindow.hide();
								}

							},{

								text : '提  交',
								handler : function() {
									if (!addChanceForm.form.isValid()) {
										Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
										return;
									}
									document.getElementById('marketOpportunityStatementNew').value = '2';
									Ext.Ajax.request({
										url : basepath+'/market-opportunity.json',
										method : 'POST',
										form : addChanceForm.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											Ext.Msg.alert('提示', '操作成功');
											store.reload();
										},
										failure : function(response) {
											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
											store.reload();
										}
									});
									addChanceWindow.hide();
								}

							},{
								text : '取  消',
								handler : function() {
									addChanceWindow.hide();
								}
							}]
				}]

			});
	
	
	
	

	

	// 定义新增窗口
	var addChanceWindow = new Ext.Window({
		title : '商机新增',
		plain : true,
		layout : 'fit',
		width : 800,
		height : 350,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		buttonAlign : 'right',
		border : false,
		constrain : true,
		items : [addChanceForm]
		});

	

