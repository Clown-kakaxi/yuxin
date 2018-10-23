	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		//var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
//		var jsonListRecord = Ext.data.Record.create([  
//		                                             {name:'CUST_MANAGER_ID'},
//		                                             {name:'CUST_MANAGER_NAME'},
//		                                             {name:'CERTIFICATE'}
//		                                             ]);  
//		var store = new Ext.data.Store({
//			restful:true,	
//	        proxy : new Ext.data.HttpProxy(
//	        		{
//	        			url:basepath+'/CustomerManagerInfoAction1.json'
//	        		}),
//	        reader: new Ext.data.JsonReader({
//	        	root : 'json.data'
//	        }, jsonListRecord
//		)});
		
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({
	        	url:basepath+'/ocrmFMmSysMsg.json',
	        	method:'GET'
	        }),
	        reader: new Ext.data.JsonReader({
	        	totalProperty : 'json.count',
	        	root:'json.data'
	        }, [
				{name: 'id',mapping :'ID'},
				{name: 'custId',mapping :'CUST_ID'},
				{name: 'custName',mapping :'CUST_NAME'},
				{name: 'messageRemark',mapping :'MESSAGE_REMARK'}
			])
		});
		
		
		 //复选框
		var sm = new Ext.grid.CheckboxSelectionModel();

		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		
		// 定义列模型
		var cm = new Ext.grid.ColumnModel([rownum,sm, 
	        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
		    {header : '客户名称',dataIndex : 'custName',width : 200,sortable : true},
		    {header : '内容',dataIndex : 'messageRemark',width : 600,sortable : true}
		  
		]);
		
		// 表格实例
		var grid = new Ext.grid.GridPanel({
					id:'viewgrid',
					frame : true,
					height:100,
					autoScroll : true,
					region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
					tbar : [ {
						text : '详情',
						iconCls : 'detailIconCss',
						handler : function() {
							viewWindowInit()
						}
					}],
					store : store, // 数据存储
					stripeRows : true, // 斑马线
					cm : cm, // 列模型
					sm : sm, // 复选框
//					tbar : [tbar], // 表格工具栏
//					bbar:bbar,
					viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
		var viewForm = new Ext.FormPanel({
				labelWidth : 100,
				height : 380,
				frame : true,
				autoScroll : true,
				labelAlign : 'right',
				buttonAlign : "center",
				items : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '客户号',
													hidden :false,
													name : 'custId',
													anchor : '90%'
												}]
									},{
										columnWidth : .5,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : '客户名称',
													hidden :false,
													name : 'custName',
													anchor : '90%'
												}]
									},{
										columnWidth : 1.05,
										layout : 'form',
										items : [{
													xtype : 'textarea',
													fieldLabel : '商机内容',
													hidden :false,
													name : 'messageRemark',
													anchor : '90%'
												}]
									}]
						}]
			});		
		var viewWindow = new Ext.Window({
				title : '详情',
				plain : true,
				layout : 'fit',
				width : 800,
				height : 200,
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
				items : [viewForm],
				listeners : {
//					'beforeshow' : function() {
//						detailViewTap.setActiveTab(0);// 显示第一个tab页签内容
//					}
				}
			});		
		function viewWindowInit() {
			var record = grid.getSelectionModel().getSelected();
				if (record == null) {
					Ext.Msg.alert('提示', '请先选择要查看信息！');
						return;
				}
				viewForm.getForm().reset();
				viewForm.getForm().loadRecord(record);
				viewWindow.show();
		}		

//	    var infoForm = new Ext.FormPanel( {
//			frame : true,
//			items : [ {
//				layout : 'column',
//				items : [{
//					layout : 'form',columnWidth : .5,labelWidth:100,
//					items : [ {name : 'CUST_MANAGER_ID',xtype : 'textfield',fieldLabel : '客户经理编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					          {name : 'CUST_MANAGER_NAME',xtype : 'textfield',fieldLabel : '客户经理名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					          {name : 'CERTIFICATE',xtype : 'textfield',fieldLabel : '资格证书',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
//                         	]
//				}]
//			}]
//		});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[grid]
	   }); 
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			  frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
//		function loadFormData(){
//    		infoForm.getForm().loadRecord(store.getAt(0));
//		}
		store.load({params:{'instanceId':instanceid,'operate':'review'}});
				
	});
