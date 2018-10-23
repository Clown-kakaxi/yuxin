/**
 * 客户群查询panel
 */
 //证件类型
	 var certTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
 //客户群分类
	 var customergroupTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=CUSTOMER_GROUP_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
     //客户来源
     var customerSourceTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=CUSTOMER_SOURCE_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
     //群成员类型
     var groupMemeberTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=GROUP_MEMEBER_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
    //客户群共享范围
	var shareFlagStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=SHARE_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
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
						groupstore.on('beforeLoad', function() {
							this.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						});
						groupstore.load({
							params : {
								start : 0,
								limit : groupbbar.pageSize
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
			var grouprownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
			var groupsm = new Ext.grid.CheckboxSelectionModel();
			
			var groupcolumns = new Ext.grid.ColumnModel([grouprownum,groupsm,	// 定义列模型
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

		var groupstore = new Ext.data.Store({//数据存储
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
			var grouppagesize_combo = new Ext.form.ComboBox({
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
			grouppagesize_combo.on("select", function(comboBox) {
				groupbbar.pageSize = parseInt(grouppagesize_combo.getValue()),
				groupstore.reload({
					params : {
						start : 0,
						limit : parseInt(grouppagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var groupbbar = new Ext.PagingToolbar({
				pageSize : parseInt(grouppagesize_combo.getValue()),
				store : groupstore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', grouppagesize_combo ]
			});
			//END********************************************************
			groupstore.load({
				params : {
					start : 0,
					limit : groupbbar.pageSize
				}
			});
			var grouppanel = new Ext.form.FormPanel({
				frame:true,
				region:'center',
				labelWidth:80,
				items:[new Com.yucheng.crm.common.ProductManage({
						xtype : 'productChoose',
						fieldLabel : '产品名称',
						labelStyle : 'text-align:right;',
						name : 'prodName',
						hiddenName : 'prodId',
						singleSelect : true,
						allowBlank : false,
						blankText : '此项为必填项，请检查！',
						anchor : '100%',
						//选择产品之后查询该产品类别是否有模板   有择查询模板供选择，否则不显示模板选择
							callback:function(checkedNodes){
								modelstore.load({
									params : {
				 						'code':checkedNodes[0].data.productId
				 					},
									callback:function(){
										if(modelstore.getCount()!=0){
											grouppanel.form.findField('modelId').setVisible(true);
							        	}else{
							        		Ext.Msg.alert("提示", "本产品没有相关的短信模板!");
							        		grouppanel.form.findField('modelId').setVisible(false);
							        	}
										
									}
								});
							}
					}),
					{fieldLabel : '短信模板',hiddenName : 'modelId',name:'modelId',xtype : 'combo',labelStyle: 'text-align:right;',triggerAction : 'all',
			        	 store : modelstore,displayField : 'value',valueField : 'key',mode : 'local',forceSelection : true,typeAhead : true,emptyText:'请选择',
			        	 resizable : true,anchor : '100%',hidden:true},{xtype:'textfield',name:'groupId',hidden:true}
				]
			});
			
			var groupWin = new Ext.Window({
				title:'短信营销',
				height:150,
				width:450,
				buttonAlign : 'center',
				draggable : true,// 是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				autoScroll : true,
				closeAction : 'hide',
				border : false,
				items : [ grouppanel ],
				buttons : [ {text:'确定',
					handler:function(){
						if(!grouppanel.getForm().isValid()){
							 Ext.MessageBox.alert('提示','输入有误,请检查输入项');
					         return false;
						}else{
							 Ext.Msg.wait('正在保存，请稍后......','系统提示');
								Ext.Ajax.request({
						             url : basepath + '/ocrmFMmSysMsg!saveData2.json',
						             method : 'POST',
						             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						             form : grouppanel.getForm().id,
						             params:{
						            	 'groupId':grouppanel.form.findField('groupId').getValue(),
						            	 'modelId':grouppanel.form.findField('modelId').getValue(),
						            	 'prodId':grouppanel.form.findField('prodId').getValue(),
						            	 'prodName':grouppanel.form.findField('prodName').getValue()
						             },
						             success : function(response) {
						            	 debugger;
						            	 var  num = response.responseText;
						                 Ext.Msg.alert('提示', '成功生成短信.');
						                 groupWin.hide();
						             },
						             failure : function(response) {
						                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						             }
						         });
						}
					}},{
					text : '关 闭',
					handler : function() {
						groupWin.hide();
					}
				} ]
			});
			//客户群展示列表
			var listPanel = new Ext.grid.GridPanel(
					{
						store : groupstore,
						frame : true,
						cm : groupcolumns,
						sm : groupsm,
						stripeRows : true,
						tbar : [{text:'短信营销',
						     iconCls:'detailIconCss',
						     handler:function(){
						    	 var checkedNodes = listPanel.getSelectionModel().selections.items;
									if (checkedNodes.length == 0) {
										Ext.Msg.alert('提示', '未选择任何客户群');
										return;
									} else if (checkedNodes.length > 1) {
										Ext.Msg.alert('提示', '您只能选择一个客户群');
										return;
									}
									var data = checkedNodes[0].data;
									if (data.customerBaseMemberNum == 0&&data.custFrom!='2'  ) {
									Ext.Msg.alert('提示', '该群没有客户！');
									return;
								} else {
									groupWin.show();
									grouppanel.getForm().reset();
									grouppanel.getForm().findField('groupId').setValue(data.id);
									
								}
						 }}
//								,'-',{
//									text : '批量创建商机',
//									iconCls:'addIconCss',
//									handler : function() {
//									resetAddForm();
//									addMyBusOpportInit01(listPanel);	
//									}
//								},
//								'-',
//								{
//									text : '创建营销活动',
//									iconCls:'addIconCss',
//									handler : function() 
//									{
//									addActivityInit();
//									}
//								}
								],
						region : 'center',
						frame : true,
						bbar : groupbbar
					});
			

var groupPanel = new Ext.Panel({
	title:'客户群查询',
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'border',
					items : [{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [ listPanel ]
					},{
						region : 'north',
						id : 'north-panel',
						height : 120,
						layout : 'fit',
						items : [ searchPanel ]
					}]
				}]
			});