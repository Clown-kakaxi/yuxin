//	Ext.QuickTips.init();	
//
//var record = Ext.data.Record.create([
//         {name: 'id', mapping: 'ID'},
//         {name: 'custZzdm', mapping: 'CUST_ZZDM'},   
//         {name: 'groupRootAdress', mapping: 'ZC_ADDR'},  
//         {name: 'groupHostOrgNo', mapping: 'GROUP_HOST_ORG_NO'},
//         {name : 'groupRootCustName1', mapping :'CUST_ZH_NAME'},
//         {name : 'groupRootCustId', mapping :'CUST_ID'}
//	     ]);
//	
//	 //集团状态
//	var JTKHZTStore = new Ext.data.Store({  
//		restful:true,   
//		autoLoad :true,
//		proxy : new Ext.data.HttpProxy({
//				url :basepath+'/lookup.json?name=GROUP_STS'
//			}),
//			reader : new Ext.data.JsonReader({
//				root : 'JSON'
//			}, [ 'key', 'value' ])
//		});
//	
//	 //集团类型
//	var JTKHLXStore = new Ext.data.Store({  
//		restful:true,   
//		autoLoad :true,
//		proxy : new Ext.data.HttpProxy({
//				url :basepath+'/lookup.json?name=GROUP_TYP'
//			}),
//			reader : new Ext.data.JsonReader({
//				root : 'JSON'
//			}, [ 'key', 'value' ])
//		});
//	
//	
//	 var Sstore = new Ext.data.Store({
//			restful:true,	
//	        proxy : new Ext.data.HttpProxy({url:basepath+'/groupmaininfo.json'
//	        }),
//	       reader: new Ext.data.JsonReader({
//	       totalProperty : 'json.count',
//	        root:'json.data'
//	        }, record)
//		});
//
//	 //***************************************
//	
//	 // 复选框
//	var sm = new Ext.grid.CheckboxSelectionModel();
//
//	// 定义自动当前页行号
//	var rownum = new Ext.grid.RowNumberer({
//				header : 'No.',
//				width : 28
//			});
//		// 定义列模型
//		var cm = new Ext.grid.ColumnModel([rownum,
//		        {header : '集团母公司编号',dataIndex : 'custId',width : 250,hidden :true},
//		        {header : '集团母公司名称',dataIndex : 'groupRootCustName1',width : 200},
//		        {header : '组织机构代码',dataIndex : 'custZzdm',width : 200},
//		        {header : '集团母公司注册地址',dataIndex : 'groupRootAdress',width : 200},
//		        {header : 'id', dataIndex : 'id',sortable : true,width : 150,hidden :true}
//				]);
//		
//		
//	     var pagesize_combo = new Ext.form.ComboBox({
//	         name : 'pagesize',
//	         triggerAction : 'all',
//	         mode : 'local',
//	         store : new Ext.data.ArrayStore({
//	             fields : ['value', 'text'],
//	             data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
//	         }),
//	         valueField : 'value',
//	         displayField : 'text',
//	         value : '100',
//	         forceSelection : true,
//	         width : 85
//	     });
//	    var number = parseInt(pagesize_combo.getValue());
//	    pagesize_combo.on("select", function(comboBox) {
//	    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
//			Sstore.load({
//						params : {
//							start : 0,
//							limit : parseInt(pagesize_combo.getValue())
//						}
//					});
//		});
//		var bbar = new Ext.PagingToolbar({
//	        pageSize : number,
//	        store : Sstore,
//	        displayInfo : true,
//	        displayMsg : '显示{0}条到{1}条,共{2}条',
//	        // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
//	        emptyMsg : "没有符合条件的记录",
//	        items : ['-', '&nbsp;&nbsp;', pagesize_combo
//	                 ]
//	    });
//		// 表格工具栏****************************************************************************************
//		
//		var tbar = new Ext.Toolbar({
//					items : [
//					{
//						text : '设置为集团客户母公司',
//						handler : function() {
//							var selectLength = grid.getSelectionModel()
//							.getSelections().length;
//							
//					if (selectLength < 1) {
//						alert('请选择需要加入名单的客户');
//					} else 
//					 {
//
//						var selectLength = grid
//								.getSelectionModel()
//								.getSelections().length;
//
//						var selectRe = grid
//								.getSelectionModel()
//								.getSelections()[0];
//
//						if (selectLength != 1) {
//							Ext.Msg.alert("系统提醒","请选择一条记录！");
//						} else {
//							
//							//获得集团母客户名，并将该名字存入集团客户名中
//							
//							var ss = grid.getSelectionModel().selections.items[0].data.groupRootCustName1;
//								selectRe.set('groupName',ss);
//								
////								debugger;
//								editBasePlanForm.getForm().loadRecord(selectRe);
//								//editBasePlanForm.getForm().
//								document.getElementById('idStr').value = selectRe.data.id;
//								addGroupCustInfoWindow.hide();
//								addInit();
//							
//						}
//					}
//						
//				}
//					}			
//					
//					]
//				});
//
//
//	 //***************************************
//		// 新增基本信息展示的form
//		var editBasePlanForm = new Ext.form.FormPanel({
//			labelWidth : 80,
//			height : 300,
//			frame : true,
//			region : 'center',
//			autoScroll : true,
//			buttonAlign : "center",
//			items : [{ 
//				layout : 'column',
//				items : [
//							{
//								columnWidth : .33,
//								layout : 'form',
//								items : [
//										{
//											columnWidth:.25,
//											xtype:'hidden',
//											editable:true,
//											name : 'groupRootCustId',
//											triggerAction:'all',
//											anchor:'90%',
//											fieldLabel : '隐藏ID'
//										},{
//											columnWidth:.25,
//											xtype:'textfield',
//											editable:true,
//											name : 'groupRootCustName1',
//											triggerAction:'all',
//											readOnly : true,
//											anchor:'90%',
//											fieldLabel : '集团母公司名称'
//										},
//										{
//											columnWidth:.25,
//											xtype:'textfield',
//											editable:true,
//											name:'groupName',
//											triggerAction:'all',
//											allowBlank : false,
//											anchor:'90%',
//											fieldLabel:'<span style="color:red">*</span>集团客户名称'
//										}										
//										]
//							}, 
//							{
//								columnWidth : .33,
//								layout : 'form',
//								items : [
//									   {
//											xtype : 'textfield',
//											fieldLabel : '集团母公司注册地址',
//											name : 'groupRootAdress',
//											readOnly : true,
//											anchor : '90%'
//										},{
//											xtype : 'textfield',
//											fieldLabel : '授信主办行',
//											hidden:true,
//											name : 'groupHostOrgNo',
//											readOnly : true,
//											anchor : '90%'
//										}
//										]
//							},
//							{
//								columnWidth : .34,
//								layout : 'form',
//								items : [
//										{
//											columnWidth : .25,
//											layout : 'form',
//											items : [
//												{
//												store: JTKHLXStore,
//												xtype : 'combo',
//												name : 'groupType',
//												fieldLabel : '<span style="color:red">*</span>集团类型',
//												valueField:'key',
//												displayField:'value',
//												mode : 'local',
//												typeAhead: true,
//												allowBlank : false,
//												forceSelection : true,
//												resizable:true,
//												triggerAction: 'all',
//												emptyText:'请选择',
//												selectOnFocus:true,
//												width : '100',
//												anchor : '90%'
//												                 }
//					
//											    ]
//										},
//										{
//											columnWidth : .25,
//											layout : 'form',
//											items : [
//												{
//												store: JTKHZTStore,
//												xtype : 'combo',
//												name : 'groupStatus',
//												fieldLabel : '<span style="color:red">*</span>集团状态',
//												valueField:'key',
//												value:'0',
//												allowBlank : false,
//												displayField:'value',
//												mode : 'local',
//												typeAhead: true,
//												forceSelection : true,
//												resizable:true,
//												triggerAction: 'all',
//												emptyText:'请选择',
//												selectOnFocus:true,
//												width : '100',
//												anchor : '90%'
//												                 }
//
//														    ]
//													}	
//										]
//							},{
//								columnWidth : .99,
//								layout : 'form',
//								items : [
//										{
//											xtype : 'textarea',
//											fieldLabel : '公司简介',
//											//width : 200,
//											name : 'groupMemo',
//											anchor : '90%'
//										}										
//										]
//							}
//							]
//			}],buttons : [
//
//							{
//
//								text : '保  存',
//								handler : function() {
////									if(){
////										
////									}
//									debugger;
//									
//									if(!editBasePlanForm.getForm().isValid()){
//										Ext.Msg.alert("提醒","请填写必填项");
//										return false;
//									}
//									
//									Ext.Ajax.request({
//
//										url : basepath + '/GroupInfoAction.json',
//										method : 'POST',
//										params:editBasePlanForm.getForm().getFieldValues(), 
//										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//										success :checkResult,
//										failure :checkResult
//
//									});
//									function checkResult(response) {
//										var resultArray = Ext.util.JSON.decode(response.status);
//										var resultError = response.responseText;
//										debugger;
//										if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
//											Ext.Msg.alert('提示', '操作成功');
//											editBasePlanForm.getForm().reset();
//											window.location.href = 'groupClientMaintenance1.jsp';
////											alert(Ext.getCmp("listPanel1"));
////											Ext.getCmp("listPanel1").load();
////											//Ext.getCmp("groupStore").reload();//*******************************************************************
////											store.reload({
////									params : {
////									start : 0,
////									limit :bbar.pageSize
////									                    }
////									                });
//										} else {
//											Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
//											Ext.getCmp("groupStore").reload();
////											store.reload({
////											params : {
////											start : 0,
////											limit :bbar.pageSize
////											                    }
////											                });
//										}
//									};
//									addPlanWindow.hide();
//							}
//
//							}, {
//								text : '取  消',
//								handler : function() {
//									editBasePlanForm.getForm().reset();
//									addPlanWindow.hide();
//								}
//							} ]
//		});
//		
//	//****************************************
//		
//		
//		var qForm = new Ext.form.FormPanel({
//			labelWidth : 100, // 标签宽度
//			frame : true, // 是否渲染表单面板背景色
//			labelAlign : 'middle', // 标签对齐方式
//			region:'north',
//			// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
//			buttonAlign : 'center',
//			height : 80,
//			items : [{
//				layout : 'column',
//				border : false,
//				items : [{
//					columnWidth : .5,
//					layout : 'form',
//					items : [ {
//						xtype : 'textfield',
//						fieldLabel : '集团母公司名称',
//						name : 'CUST_ZH_NAME',
//						anchor : '90%'
//					} ]
//				},{
//					columnWidth : .5,
//					layout : 'form',
//					items : [ {
//						xtype : 'textfield',
//						fieldLabel : '组织机构代码',
//						id : 'CUST_ZZDM',
//						name : 'CUST_ZZDM',
//						anchor : '90%'
//					} ]
//				}]
//			}],
//		buttons : [{
//					text : '查询',
//					handler : function() {
//						var conditionStr = qForm.getForm().getFieldValues();
//						Sstore.baseParams = {
//								"condition" : Ext.encode(conditionStr)
//							};
//						Sstore.reload({
//							  params : {
//                                   start : 0,
//                                   limit : bbar.pageSize }} );
//				
//				   }},{
//					text : '重置',
//						handler : function() {
//							qForm.getForm().reset();
//						}
//					}]
//		});
//
//		// 表格实例
//		var grid = new Ext.grid.GridPanel({
//					height :310,
//					width : 200,
//					frame : true,
//					autoScroll : true,
//					region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
//					store : Sstore, // 数据存储
//					stripeRows : true, // 斑马线
//					cm : cm, // 列模型
//					sm : sm, // 复选框
//					tbar : tbar, // 表格工具栏
//					bbar:bbar,
//					viewConfig:{
//						   forceFit:false,
//						   autoScroll:true
//						},
//					loadMask : {
//						msg : '正在加载表格数据,请稍等...'
//					}
//				});
//		
//			// 定义展示员工基本信息窗口
//			var addGroupCustInfoWindow = new Ext.Window({
//				title : '集团客户创建',
//				plain : true,
//				layout : 'border',
//				width : 800,
//				height : 400,
//				resizable : true,
//				draggable : true,
//				closable : true,
//				closeAction : 'hide',
//				modal : true, // 模态窗口
//				loadMask : true,
//				maximizable : true,
//				collapsible : true,
//				titleCollapse : true,
//				buttonAlign : 'right',
//				border : false,
//				items: [qForm,grid]
//			});
//			
//			
//			// 新增窗口展示的from
//			var editPlanPanel = new Ext.Panel({
//				labelWidth : 80,
//				height : 300,
//				layout : 'fit',
//				autoScroll : true,
//				buttonAlign : "center",
//				items : [ editBasePlanForm ]
//			});
//			
//			
//			// 定义新增窗口
//			var addPlanWindow = new Ext.Window({
//				title : '集团客户新增',
//				plain : true,
//				layout : 'fit',
//				width : 880,
//				height : 300,
//				resizable : true,
//				draggable : true,
//				closable : true,
//				closeAction : 'hide',
//				modal : true, // 模态窗口
//				loadMask : true,
//				maximizable : true,
//				collapsible : true,
//				titleCollapse : true,
//				buttonAlign : 'right',
//				border : false,
//				items : [ editPlanPanel ]
//			});
//			
//			function addInit(){
//				addPlanWindow.show();
//			};
//			
//			
//			
//			