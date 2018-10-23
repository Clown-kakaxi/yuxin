Ext.onReady(function() {
	
			/**防止内存控制机制误删Ext.MessageBox内部对象**/
			Ext.Msg.alert('ANTIDEBUG','ANTIDEBUG');
			Ext.Msg.hide();
			var startData = new Date();
//			var __appButton = true;
//			if(__mktAppType=='01'){
//				__appButton = false;
//			}
			
			//客户群查询panel
			var searchPanel = new Ext.form.FormPanel({
				labelWidth : 100,
				labelAlign : 'right',
				height : 130,
				frame : true,
				region : 'north',
				autoScroll : true,
					layout : 'column',
					items : [
						{
						columnWidth : .25,
						layout : 'form',
						items : [{
							store : customerSourceTypeStore,
							xtype : 'combo', 
							resizable : true,
							fieldLabel : '客户来源',
							name : 'CUST_FROM',
							hiddenName : 'CUST_FROM',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable :false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
	                        selectOnFocus : true,
							anchor : '90%'
							}]
					 },{
						columnWidth : .25,
						layout : 'form',
						items : [{
							store : groupMemeberTypeStore,
							xtype : 'combo', 
							resizable : true,
							fieldLabel : '群成员类型',
							name : 'GROUP_MEMBER_TYPE',
							hiddenName : 'GROUP_MEMBER_TYPE',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable :false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
	                        selectOnFocus : true,
							anchor : '90%'
							}]
						},{
						columnWidth : .25,
						layout : 'form',
						items : [{
							store : shareFlagStore,
							xtype : 'combo', 
							resizable : true,
							fieldLabel : '共享范围',
							name : 'SHARE_FLAG',
							hiddenName : 'SHARE_FLAG',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable :false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
	                        selectOnFocus : true,
							anchor : '90%'
							}]
						},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							name : 'CUST_ID',
							fieldLabel : '客户号',
							anchor : '90%'
							}]
					    },{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							name : 'CUST_NAME',
							fieldLabel : '客户名称',
							anchor : '90%'
							}]
						},{
						columnWidth : .25,
						layout : 'form',
						items : [{
							store : certTypeStore,
							xtype : 'combo', 
							resizable : true,
							fieldLabel : '客户证件类型',
							name : 'CERT_TYPE',
							hiddenName : 'CERT_TYPE',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable :false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
	                        selectOnFocus : true,
							anchor : '90%'
							}]
						},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							name : 'CERT_NUM',
							fieldLabel : '客户证件号码',
							anchor : '90%'
							}]
						},{
					columnWidth : .25,
					layout : 'form',
					items : [{
						fieldLabel : '创建日期从',
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						name : 'CUST_BASE_CREATE_DATE_S',
						anchor : '90%'
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [{
						fieldLabel : '创建日期到',
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						name : 'CUST_BASE_CREATE_DATE_E',
						anchor : '90%'
					}]
				}],
				buttonAlign : 'center',
				buttons : [{
					text : '查询',
					handler : function() {
						var conditionStr = searchPanel.getForm().getValues(false);
						store.on('beforeLoad', function() {
							this.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						});
						store.load({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
						}
						},{
					text : '重置',
					handler : function() {
						searchPanel.getForm().reset();
						Ext.getCmp('BELONG_ORG').setValue('');
						Ext.getCmp('BELONG_CUSTMANAGER').setValue('');
						}}
				]
			});
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			var sm = new Ext.grid.CheckboxSelectionModel();
			
			var columns = new Ext.grid.ColumnModel([rownum,sm,	// 定义列模型
	             {header : '客户群ID', dataIndex : 'id',sortable : true,width : 120,hidden:true },                        
                 {header : '客户群编号', dataIndex : 'custBaseNumber',sortable : true,width : 120 }, 
                 {header : '客户群名称',dataIndex : 'custBaseName',sortable : true,width : 120}, 
                 {header : '客户群分类',dataIndex : 'GROUP_TYPE_ORA',sortable : true,width : 120}, 
                 {header : '客户来源',dataIndex : 'CUST_FROM_ORA',sortable : true,width : 120},
                 {header : '群成员类型',dataIndex : 'GROUP_MEMBER_TYPE_ORA',sortable : true,width : 120},
                 {header : '共享范围',dataIndex : 'SHARE_FLAG_ORA',sortable : true,width : 135},
                 {header : '成员数',dataIndex : 'customerBaseMemberNum',
                	 renderer: function(v,p,record){
	                        var nl = record.data.custFrom;
	                        if(nl == '2'){
	                            return '动态';
	                        }else if(v==''){
	                        	return "0"+"人";
	                        	}else{
	                        		return v+"人";
	                        		}
	                    },
                	 sortable : true,
                	 align:'right',
                	 width : 120 
                 },
//                 {header : '主办机构',dataIndex : 'mainOrgName',sortable : true,width: 120},
//                 {header : '主办客户经理',dataIndex : 'mainUserName',sortable : true,width: 120},
                 {header : '创建日期',dataIndex : 'custBaseCreateDate',sortable : true,width : 120},
                 {header : '创建人',dataIndex : 'createName',sortable : true,width: 120},
                 {header : '创建机构',dataIndex : 'custBaseCreateOrgName',sortable : true,width : 120},
                 {header : '最近修改日期',dataIndex : 'recentUpdateDate',sortable : true,width : 120},
                 {header : '最近修改人',dataIndex : 'recentUpdateUser',sortable : true,width: 120},
                 {header : '最近修改机构',dataIndex : 'recentUpdateOrg',sortable : true,width : 120},
                 
                 {header : '客户群分类ID',dataIndex : 'groupType',sortable : true,width : 120,hidden : true,hideable:false}, 
                 {header : '客户群共享范围ID',dataIndex : 'shareFlag',sortable : true,width : 135,hidden : true,hideable:false},
                 {header : '客户来源ID',dataIndex : 'custFrom',sortable : true,width : 120,hidden : true,hideable:false},
                 {header : '群成员类型ID',dataIndex : 'groupMemberType',sortable : true,width : 120,hidden : true,hideable:false},
                 {header : 'id', dataIndex : 'id',sortable : true,width : 120,hidden : true,hideable:false},
                 {header : '客户群成员来源标识',dataIndex : 'customerFrom',sortable : true,width : 120,hidden : true},
                 {header : '客户群共享范围ID',dataIndex : 'shareFlag',sortable : true,width : 135,hidden : true,hideable:false},
                 {header : '创建人ID',dataIndex : 'cust_base_create_name',sortable : true,width: 120,hidden:true,hideable:false}, 
                 {header : '创建机构ID',dataIndex : 'custBaseCreateOrg',sortable : true,width : 120,hidden : true,hideable:false}, 
                 {header : '客户群描述',dataIndex : 'custBaseDesc',sortable : true,width : 120,hidden : true,hideable:false}
                 ]);

		var store = new Ext.data.Store({//数据存储
		  	restful:true,
		  	proxy : new Ext.data.HttpProxy({url:basepath+'/customergroupinfo.json'
		  	}),
		  	  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'id', mapping: 'ID'}
		      ,{name: 'custBaseNumber', mapping: 'CUST_BASE_NUMBER'}
		      ,{name: 'custBaseName', mapping: 'CUST_BASE_NAME'}
		      ,{name: 'createName',mapping:'CREATENAME'}
		      ,{name: 'custBaseCreateDate', mapping: 'CUST_BASE_CREATE_DATE'}
		      ,{name: 'custBaseCreateName', mapping: 'CUST_BASE_CREATE_NAME'} 	
		      ,{name: 'customerBaseMemberNum', mapping: 'MEMBERSNUM'}
		      ,{name: 'custFrom', mapping: 'CUST_FROM'}
		      ,{name: 'custFromName', mapping: 'CUST_FROM_NAME'}
		      ,{name: 'custBaseDesc', mapping: 'CUST_BASE_DESC'}
		      ,{name: 'shareFlag',mapping :'SHARE_FLAG'}
		      ,{name: 'custBaseCreateOrg',mapping : 'CUST_BASE_CREATE_ORG'}
		      ,{name: 'custBaseCreateOrgName',mapping : 'CUST_BASE_CREATE_ORG_NAME'}
		      ,{name: 'mainUserName',mapping : 'MAIN_USER_NAME'}
		      ,{name: 'mainOrgName',mapping : 'MAIN_ORG_NAME'}
		      ,{name: 'recentUpdateUser',mapping : 'RECENT_UPDATE_USER'}
		      ,{name: 'recentUpdateOrg',mapping : 'RECENT_UPDATE_ORG'}
		      ,{name: 'recentUpdateDate',mapping : 'RECENT_UPDATE_DATE'}
		      ,{name: 'groupType',mapping : 'GROUP_TYPE'}
		      ,{name: 'groupMemberType',mapping : 'GROUP_MEMBER_TYPE'}
		      ,{name: 'GROUP_TYPE_ORA'}
		      ,{name: 'SHARE_FLAG_ORA'}
		      ,{name: 'CUST_FROM_ORA'}
		      ,{name: 'GROUP_MEMBER_TYPE_ORA'}
		      ])
	 		  });
			
			//分页栏********************************************************
			// 每页显示条数下拉选择框 
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				resizable : true,
				width : 85
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			//END********************************************************
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
			
			// 展示新增的窗口
			function addActivityInit() {
				ifGroup = true;	
				ifProd = false;
				_buttonVisible = true;
				_sheetVisible = false;
				editBasePlanForm.form.reset();
				
				editBasePlanForm.form.findField('mktActiName').setDisabled(false);
				editBasePlanForm.form.findField('mktActiCost').setDisabled(false);
				editBasePlanForm.form.findField('mktActiType').setDisabled(false);
				editBasePlanForm.form.findField('pstartDate').setDisabled(false);
				editBasePlanForm.form.findField('mktActiMode').setDisabled(false);
				editBasePlanForm.form.findField('pendDate').setDisabled(false);
				editBasePlanForm.form.findField('mktActiStat').setDisabled(false);
				editBasePlanForm.form.findField('mktActiAddr').setDisabled(false);
				editBasePlanForm.form.findField('mktActiCont').setDisabled(false);
				editBasePlanForm.form.findField('actiCustDesc').setDisabled(false);
				editBasePlanForm.form.findField('actiOperDesc').setDisabled(false);
				editBasePlanForm.form.findField('actiProdDesc').setDisabled(false);
				editBasePlanForm.form.findField('mktActiAim').setDisabled(false);
				editBasePlanForm.form.findField('actiRemark').setDisabled(false);
				
				editPlanWindow.setTitle('营销活动新增');
				editBasePlanForm.form.findField('createUser').setValue(__userId);
				editBasePlanForm.form.findField('createDate').setValue(startData);
				editBasePlanForm.form.findField('updateUser').setValue(__userId);
				editBasePlanForm.form.findField('updateDate').setValue(startData);
				editBasePlanForm.form.findField('mktActiStat').setValue('1');
				editBasePlanForm.form.findField('createOrg').setValue(__units);
				
				Ext.getCmp('jbxx').show();
				Ext.getCmp('glcpxx').hide();
				Ext.getCmp('glkkxx').hide();
				Ext.getCmp('glqdxx').hide();
				Ext.getCmp('fjxx').hide();
				Ext.getCmp('spxx').hide();
				editPlanWindow.show();
			};
			
			
			
			//客户群展示列表
			var listPanel = new Ext.grid.GridPanel(
					{
						store : store,
						frame : true,
						cm : columns,
						sm : sm,
						stripeRows : true,
						tbar : [{
								  text:'群成员视图',
//								  hidden:JsContext.checkGrant('khqgl_custGroupView'),
								  iconCls:'custGroupMemIconCss',
								  handler : function() {
								 var checkedNodes = listPanel.getSelectionModel().selections.items;
								 if(checkedNodes.length==0)
								  {
									  Ext.Msg.alert('提示', '未选择任何客户群');
									  return ;
								  }
								  else if(checkedNodes.length>1)
								  {
									  Ext.Msg.alert('提示', '您只能选中一个客户群进行查看');
									  return ;
								  }
								  var viewTypeStr = '3';
								  if('1'==checkedNodes[0].data.groupType){
								  	 viewTypeStr = '3';
								  }else if('2'==checkedNodes[0].data.groupType){
								   viewTypeStr = '4';
								  }
							     var viewWindow = new Com.yucheng.crm.custGroup.ViewWindow({
											id:'groupViewWindow',
											groupId:checkedNodes[0].data.id,
											groupName:checkedNodes[0].data.custBaseName,
											viewType:viewTypeStr
										}); 	
								  
								oCustInfo.groupId = checkedNodes[0].data.id;//客户群ID
        						oCustInfo.groupName = checkedNodes[0].data.custBaseName;//客户群名称
        						oCustInfo.groupType = checkedNodes[0].data.groupType;//客户群类型
        						oCustInfo.groupMemberType = checkedNodes[0].data.groupMemberType;//客户群成员类型
        						oCustInfo.custFrom = checkedNodes[0].data.custFrom;//客户来源
        						oCustInfo.view_source = 'group_viewport_center';//客户群视图标示
        						
        						var userId = checkedNodes[0].data.custBaseCreateName;
       						 if(userId!=__userId){//非创建人
	        						 __hiddeAble=true;
	        						 __modelSign1=0.00;
	        					     __modelSign2=1;
       						 }else{
       							 __hiddeAble=false;
        						 __modelSign1=.45;
        					     __modelSign2=.55;
       						 }
       						 
								  viewWindow.show();
							  }
							  },'-',{
									text : '创建客户群',
//									hidden:JsContext.checkGrant('khqgl_add'),
									iconCls:'addIconCss',
									handler : function() {
									GroupBaseInfoInit();
									groupLeaguerStore.load({
							  		  params : {
									  start : 0,
									  limit : parseInt(groupLeaguerpagesize_combo.getValue())
									  }
									  });
									}
								},
								'-',
								{
									text : '删除客户群',
//									hidden:JsContext.checkGrant('khqgl_delete'),
									iconCls:'deleteIconCss',
									handler : function() 
									{
										 var selectLength = listPanel.getSelectionModel().getSelections().length;
										 var json={'id':[]};
										 var selectRe;
									if(selectLength < 1){
										Ext.Msg.alert('提示','请选择需要删除的记录!');
								   }else {
										for(var i = 0; i<selectLength;i++)
										{
											selectRe = listPanel.getSelectionModel().getSelections()[i];
											 json.id.push(selectRe.data.id);
										}
									Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
										if(buttonId.toLowerCase() == "no"){
										return false;
										} 
									Ext.Ajax.request({
										url : basepath+ '/customergroupinfo.json',
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										params : {
										cbid:Ext.encode(json),
										'operate':'delete'
										},
										success : function() {
											Ext.Msg.alert('提示', '操作成功');
											store.reload();
										},
										failure : function(response) {
											var resultArray = Ext.util.JSON.decode(response.status);
											if(resultArray == 403) {
										           Ext.Msg.alert('提示', response.responseText);
										}else {
											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
											store.reload();
										}}
										});
										});
										}}
								}
								,'-',{
									text : '批量创建商机',
									iconCls:'addIconCss',
									handler : function() {
										 var checkedNodes = listPanel.getSelectionModel().selections.items;
										 if(checkedNodes.length==0)
										  {
											  Ext.Msg.alert('提示', '未选择任何客户群');
											  return ;
										  }
										  else if(checkedNodes.length>1)
										  {
											  Ext.Msg.alert('提示', '您只能选中一个客户群');
											  return ;
										  }
										 var data = checkedNodes[0].data;
											if (data.customerBaseMemberNum == 0&&data.custFrom!='2' ) {
											Ext.Msg.alert('提示', '该群没有客户！');
											return;
											}
									 Ext.ScriptLoader.loadScript({        
										   scripts: [
										             basepath+'/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',
										             basepath+'/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktTaskTargetCommonQuery.js',
										             basepath+'/contents/pages/mktManage/mktBusiOppor/mktBusiOpporQueryCondition.js',
										             basepath+'/contents/pages/mktManage/mktBusiOppor/mktBusiOpporQueryResult.js',
										             basepath+'/contents/pages/mktManage/mktBusiOppor/mktBusiOpporAddForm.js'
										             ],        
										    finalCallback: function() {  
										    	addBusiOpporWindow.show();
										    	//将客户部分隐藏  将在程序中处理
										    	addBusiOpporForm.form.findField("custType").setValue("1");
										    	addBusiOpporForm.form.findField("custCategory").setValue("1");
										    	addBusiOpporForm.form.findField("custName").setValue("1");
										    	addBusiOpporForm.form.findField("mainCustManager").setValue("1");
										    	addBusiOpporForm.form.findField("mainCustOrgname").setValue("1");
										    	addBusiOpporForm.form.findField("custContactName").setValue("1");
										    	
										    	addBusiOpporForm.form.findField("custType").hide();
										    	addBusiOpporForm.form.findField("custCategory").hide();
										    	addBusiOpporForm.form.findField("custName").hide();
										    	addBusiOpporForm.form.findField("mainCustManager").hide();
										    	addBusiOpporForm.form.findField("mainCustOrgname").hide();
										    	addBusiOpporForm.form.findField("custContactName").hide();
										    	addBusiOpporForm.buttons[1].hide();
										    	
										    	ifGroup = true;	
												ifProd = false;
												groupId =checkedNodes[0].data.id ;
										    	
										   	}
										   });
									}
								},
								'-',
								{
									text : '创建营销活动',
									iconCls:'addIconCss',
									handler : function() 
									{
										 var checkedNodes = listPanel.getSelectionModel().selections.items;
										 if(checkedNodes.length==0)
										  {
											  Ext.Msg.alert('提示', '未选择任何客户群');
											  return ;
										  }
										  else if(checkedNodes.length>1)
										  {
											  Ext.Msg.alert('提示', '您只能选中一个客户群');
											  return ;
										  }
										 var data = checkedNodes[0].data;
											if (data.customerBaseMemberNum == 0&&data.custFrom!='2' ) {
											Ext.Msg.alert('提示', '该群没有客户！');
											return;
											}
									 Ext.ScriptLoader.loadScript({        
										   scripts: [
										             basepath+'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js',
										             basepath+'/contents/pages/mktManage/mktActivityManage/mktActivityProdEditInfo.js',
										             basepath+'/contents/pages/mktManage/mktActivityManage/mktActivityCustEditInfo.js',
										             basepath+'/contents/pages/mktManage/mktActivityManage/mktActivityChanelEditInfo.js',
										             basepath+'/contents/pages/mktManage/mktActivityManage/mktActivityShenPiInfo.js',
										             basepath+'/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',
										             basepath+'/contents/pages/mktManage/mktActivityManage/mktActivityList1.js'
										             ],        
										    finalCallback: function() { 
										    	groupId =checkedNodes[0].data.id ;
										    	addActivityInit();
										   	}
										   });
									
									}
								}
								],
						region : 'center',
						frame : true,
						bbar : bbar
					});
			
			// 展示新增的窗口
			function GroupBaseInfoInit() {
				editGroupBaseInfoForm.form.findField('groupMemberType').setReadOnly(false);
    			editGroupBaseInfoForm.form.findField('custFrom').setReadOnly(false);
				editGroupBaseInfoForm.getForm().reset();
				editGroupBaseInfoWindow.setTitle('客户群新增-->第1步，共2步');
				editGroupBaseInfoWindow.show();
				editGroupBaseInfoPanel.buttons[0].setDisabled(true);
				editGroupBaseInfoPanel.buttons[1].setDisabled(false);
				editGroupBaseInfoPanel.layout.setActiveItem('info1'); 
				editGroupBaseInfoForm.form.findField("groupType").setValue("1");
			};
			
			// 展示修改窗口
			function editInit() {
				editGroupBaseInfoWindow.setTitle('客户群修改');
				editGroupBaseInfoWindow.show();
			}

			editGroupBaseInfoWindow.addListener('hide',function(){
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			
			var view = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'border',
					items : [{
						region : 'center',
						id : 'center-panel',
						title : "客户群列表",
						layout : 'fit',
						items : [ listPanel ]
					},{
						xtype : 'fieldset',
			            collapsed:false,
						collapsible : true,
						region : 'north',
						id : 'north-panel',
						title : "客户群查询",
						height : 138,
						layout : 'fit',
						items : [ searchPanel ]
					}]
				}]
			});
		});