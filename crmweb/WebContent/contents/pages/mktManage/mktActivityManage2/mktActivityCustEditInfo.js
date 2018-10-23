  	
				var addRoleWindow = new Ext.Window(
					{
						layout : 'fit',
						width : 1000,
						height : 400,
						//resizable : false,//是否允许缩放
						draggable : true,//是否可以拖动
						closable : true,// 是否可关闭
						modal : true,
						closeAction : 'hide',
						
						// iconCls : 'page_addIcon',
						//maximizable: true,
						maximized:true,
						//collapsible : true,// 是否可收缩
						titleCollapse : true,
						buttonAlign : 'center',
						border : false,
						animCollapse : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [{html:' <div style="width:1000px;height:400px;"><div style="position:absolute; left:0px; top:0px; " id=\'view\'></div></div>'
					}],
						buttons : [
								/*{
									text : '查询',
									handler : function() {
										var conditionStr1 =  simple.getForm().getValues(false);
										store.baseParams={
																			
												'condition':Ext.encode(conditionStr1)								
								};
										store.reload({
											  params : {
				                                   start : 0,
				                                   limit : bbar.pageSize
											  
											  }}); 
										addRoleWindow.hide();
										 Ext.getCmp('exportbatten').formPanel=simple;
										//Ext.MessageBox.alert('提示', "保存成功!");
									}
								}, {
									text : '重置',
									handler : function() {
										simple.getForm().reset();
									}
								},*/ {
									text : '关闭',
									handler : function() {
										addRoleWindow.hide();
										//document.getElementById('view').innerHTML = "";
									}
								} ]
					});
				addRoleWindow.on('hide', function() {
					document.getElementById('view').innerHTML = "";
					addSolutionWindow.destroy();
				});
				var pid='';//定义custId传递参数
	var test = null; 
  	var search_cust_edit = new Com.yucheng.bcrm.common.CustomerQueryField({ 
		fieldLabel : '目标客户', 
		labelStyle: 'text-align:right;',
		name : 'custNameStr',
		id:'groupRootCustName1',
		custtype :'2',//客户类型：  1：对私, 2:对公,  不设默认全部
	    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
	    singleSelected:false,//单选复选标志
		editable : false,
//		allowBlank:false,//不允许为空
		blankText:"请填写",
		anchor : '90%',
		hiddenName:'abcd',
		callback :function(){
		var cust_name = null;
		var linkNum = '';
	}
	});
  	
	
	
	
  	 var myData2 = [
['李四','南京市白下区支行','未开始','秦青','2013-2-28'],
['赵武','南京市白下区支行','未开始','秦青','2013-2-27']
];

	var custContrastStore1 =  new Ext.data.ArrayStore({
        fields: [
	                  {name: 'custName'},
	                  {name: 'institutionName'},
	                  {name: 'productName'},
	                  {name: 'createUserName'},
	                  {name: 'createDate'}
	               ]
	           });
	custContrastStore1.loadData(myData2);
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
			         [ 50, '50条/页' ],[ 100, '100条/页' ]  ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
	});
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		custContrastStore1.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});

	var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
			pageSize : parseInt(pagesize_combo.getValue()),
			store : custContrastStore1,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});


	 /*************************************列模型***********************************************/
	 var custContrastGrid1 = new Ext.grid.GridPanel({			
			store:custContrastStore1, 
			frame:true,
			height : 200,
			columns:[	
						{ header:'客户名称',dataIndex:'custName',width:150},
						{ header:'主办机构',dataIndex:'institutionName',width:150},
						{ header:'进阶阶段',dataIndex:'productName',width:150},
						{ header:'创建人',dataIndex:'createUserName',width:160},
						{ header:'创建日期',dataIndex:'createDate',width:160}
						],
			region:'center',
			tbar:[
			      { text:'新增',
			    	iconCls:'addIconCss',
			    	hidden:test,
			       handler:function(){
//			    	  	addcustContrastWind.show();
//			    	  	addcustContrastWind.setTitle('关联客户新增');
					   addRoleWindow.show();
						setTimeout(function(){
							Ext.ScriptLoader.loadScript({        
								scripts: [basepath+'/contents/pages/customer/customerManager/agileQuery.js'],        
								callback: function() {  
							}
							});
						},800);
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = custContrastGrid1.getSelectionModel().getSelections().length;
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return;
									} 
								});
						}}
			      }],
			      bbar:bbar
	 });
	 var custContrastGrid1 = new Ext.grid.GridPanel({			
			store:custContrastStore1, 
			frame:true,
			height : 200,
			columns:[	
						{ header:'客户名称',dataIndex:'custName',width:150},
						{ header:'主办机构',dataIndex:'institutionName',width:150},
						{ header:'进阶阶段',dataIndex:'productName',width:150},
						{ header:'创建人',dataIndex:'createUserName',width:160},
						{ header:'创建日期',dataIndex:'createDate',width:160}
						],
			region:'center',
			      bbar:bbar
	 });
	 var custContrastGrid2 = new Ext.grid.GridPanel({			
			store:custContrastStore1, 
			frame:true,
			height : 200,
			columns:[	
						{ header:'客户名称',dataIndex:'custName',width:150},
						{ header:'主办机构',dataIndex:'institutionName',width:150},
						{ header:'进阶阶段',dataIndex:'productName',width:150},
						{ header:'创建人',dataIndex:'createUserName',width:160},
						{ header:'创建日期',dataIndex:'createDate',width:160}
						],
			region:'center',
			tbar:[
			      { text:'新增',
			    	iconCls:'addIconCss',
			    	hidden:test,
			       handler:function()
			       {
//			    	  	addcustContrastWind.show();
//			    	  	addcustContrastWind.setTitle('关联客户新增');
					   addRoleWindow.show();
						setTimeout(function(){
							Ext.ScriptLoader.loadScript({        
								scripts: [basepath+'/contents/pages/customer/customerManager/agileQuery.js'],        
								callback: function() {  
							}
							});
						},800);
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = custContrastGrid2.getSelectionModel().getSelections().length;
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return;
									}   else{
										Ext.Msg.alert('提示','删除成功!');
									}
								});
						}}
			      }],
			      bbar:bbar
	 });
	 /****************************************产品对照关系信息*************************************************/
	 var custContrastForm = new Ext.form.FormPanel({
		 labelWidth : 80,
		 height : 200,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items : [ {
	    	 layout : 'column',
	    	 items : [ {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [search_cust_edit]
	    	 }]
		 }]
	 });
	var addcustContrastWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[custContrastForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 addcustContrastWind.hide();
		         }
		         },
		         {
		        	 text:'重置',
		        	 handler:function(){
		        	 	custContrastForm.getForm().reset();
		         	}
		         }
		        ]
	});
	/******************************展示详情*************************************/
	 var detailcustContrastForm = new Ext.form.FormPanel({
		 labelWidth : 80,
		 height : 200,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items : [ {
	    	 layout : 'column',
	    	 items : [ {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [  {
	    			 name : 'custId',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 value:'CN201300001',
	    			 fieldLabel : '客户编号'
	    		 },{
	    			 name : 'mgrName',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 value:'秦青',
	    			 fieldLabel : '主办客户经理',
	    			 anchor : '90%'
	    		 }]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [{
	    			 name:'custName',
	    			 xtype:'textfield',
	    			 readOnly:true,
	    			 value:'李四',
	    			 fieldLabel : '客户名称',
	    			 allowBlank : false,
	    			 anchor : '90%'
	    		 },{
	    			 name:'institutionName',
	    			 xtype:'textfield',
	    			 value:'南京新街口支行',
	    			 fieldLabel : '主办机构',
	    			 anchor : '90%'
	    		 }]
	    	 }
	    	 ]
		 }]
	 });
	var detailcustContrastWind = new Ext.Window({//展示详情的window
		title:'产品对照关系详情',
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[detailcustContrastForm],
		buttons:[{
					text:'返回',
					handler:function(){
						detailcustContrastWind.hide();
					}
		}]
	});
	
	 /****************************修改方法*************************************/

	var update = function() {
		debugger;
		var record = custContrastGrid1.getSelectionModel().getSelected();
		if(!record){
			Ext.MessageBox.alert('提示', '请选择要修改的一列！');
		}
		else{
			addcustContrastWind.show();
			addcustContrastWind.setTitle('产品对照关系修改');
			custContrastForm.getForm().loadRecord(record);
			var selectedRow1 = custContrastGrid1.selModel.getSelections();
			custId = selectedRow1[0].data.custId;
			custContrastStore1.load({
				params : {
					'productId':custId,
					'querysign':'customer'
				}
			});
		}
	};
	
	//展示详细信息窗口
	function showInit2() {
		// 得到选中记录
			detailcustContrastWind.show();
	}
	 custContrastGrid1.on('rowdblclick', function(grid, rowIndex, event) {
		 showInit2();
	});
	 function showInit21() {
			// 得到选中记录
				detailcustContrastWind.show();
		}
		 custContrastGrid1.on('rowdblclick', function(grid, rowIndex, event) {
			 showInit21();
		});
