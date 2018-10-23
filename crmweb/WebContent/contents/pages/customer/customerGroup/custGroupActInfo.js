  /**动态客户群JS*************************************/
        var eventDate = new Date();		
        var cusrownumDetailAct = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		
		var cuscmActDetail = new Ext.grid.ColumnModel([
 			  cusrownumDetailAct,
 			  //{header : 'GROUP_MEM_ID', dataIndex : 'GROUP_MEM_ID',sortable : true,width : 150,hidden:true}, 
 			  {header : 'CUST_ID', dataIndex : 'CUST_ID',sortable : true,width : 150,hidden:true},
 			  {header : '组织机构代码',dataIndex : 'ORG_ID',sortable : true,width : 100}, 
              {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 100 }, 
              {header : '客户性别', dataIndex : 'SEX',sortable : true,width : 100 },
              {header : '客户级别', dataIndex : 'CUST_GRADE',sortable : true,width : 100 },
              {header : '客户经理名', dataIndex : 'STAFF_NAME',sortable : true,width : 100 }
            //{header : '加入群组日期',dataIndex:'IN_DATE',sortable : true}
 				]);	

		/**动态客户群成员---查询Store设置************************************************************************/
		var custListDetail = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustActListGroup.json'}
	        ),
	        reader: new Ext.data.JsonReader({
	        totalProperty : 'json.count',
	        root:'json.data'
	        //totalProperty : 'list'
	        }, [ 'GROUP_MEM_ID','CUST_ID','CUST_NAME','ORG_ID','SEX','CUST_GRADE','STAFF_NAME','IN_DATE'])
		});
		
		custListDetail.on('beforeload', function() {
//			   var conditionStr =  memberSearch.getForm().getValues(false);
//			   var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			   //alert(Ext.getCmp('cbid').getValue());
		       this.baseParams = {
		    		  start : 0,
		              limit : parseInt(cuspagesize_combo2Detail.getValue()),
//		              "condition":Ext.encode(conditionStr),
		              //cbid:checkedNodes[0].data.id
		              cbid: Ext.getCmp('cbid').getValue()
		      };});
		// 每页显示条数下拉选择框
		var cuspagesize_combo2Detail = new Ext.form.ComboBox({
							name : 'pagesize',
							triggerAction : 'all',
							mode : 'local',
							store : new Ext.data.ArrayStore({
										fields : ['value', 'text'],
										data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
									}),
							valueField : 'value',
							displayField : 'text',
							value : '100',
							editable : false,
							width : 85
						});
		/**************************************************************************************/
		
		/**动态客户群设置---查询Store设置************************************************************************/
		var cusstoreActDetail = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustomerActListGroup.json'}
	        ),
	        method : 'POST',
	        reader: new Ext.data.JsonReader({
	        totalProperty : 'json.count',
	        root:'json.data'
	        //totalProperty : 'list'
	        }, [ 'CUST_ID','ORG_ID','CUST_NAME','SEX','CUST_GRADE','STAFF_NAME'])
		});

		
		cusstoreActDetail.on('beforeload', function() {
			   var conditionStr =  memberSearchAct.getForm().getValues(false);
			   //var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			   //alert(Ext.getCmp('cbid').getValue());
		       this.baseParams = {
		    		  start : 0,
		              limit : parseInt(cuspagesize_comboDetailAct.getValue()),
		              "condition":Ext.encode(conditionStr),
		              //cbid:checkedNodes[0].data.id
		              cbid: Ext.getCmp('cbid').getValue()
		      };});
		
		
		// 每页显示条数下拉选择框
		var cuspagesize_comboDetailAct = new Ext.form.ComboBox({
							name : 'pagesize',
							triggerAction : 'all',
							mode : 'local',
							store : new Ext.data.ArrayStore({
										fields : ['value', 'text'],
										data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
									}),
							valueField : 'value',
							displayField : 'text',
							value : '100',
							editable : false,
							width : 85
						});
		 var cusrownumDetailAct = parseInt(cuspagesize_comboDetailAct.getValue());
		    // 改变每页显示条数reload数据
		 cuspagesize_comboDetailAct.on("select", function(comboBox) {
			 cusbbarDetailAct.pageSize = parseInt(cuspagesize_comboDetailAct.getValue()),
			 cusstoreActDetail.reload({
		            params : {
		                start : 0,
		                limit : parseInt(cuspagesize_comboDetailAct.getValue())
		            }
		        });
		    });
		// 分页工具栏
		var cusbbarDetailAct = new Ext.PagingToolbar({
							pageSize : cusrownumDetailAct,
							store : cusstoreActDetail,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
							emptyMsg : "没有符合条件的记录",
							items : ['-', '&nbsp;&nbsp;', cuspagesize_comboDetailAct]
						});
		/**************************************************************************************/

		debugger;
		/**动态客户群成员设置--上部查询**********************************************************/
		var memberSearchAct=new Ext.FormPanel({
			//layout:'fit',
			title:'动态群成员信息',
			frame:true,
			border:false,
			buttonAlign:'center',
			labelAlign:'right',
			items : [{
						layout:'column',
						items : [
								{
									columnWidth : .33,
									layout : 'form',
									items : [{
										xtype : 'textfield',
										id : 'cst_name',
										fieldLabel : '客户名称',
										name:'CUST_NAME',
										labelStyle:{
											width:'120px'
										},	
										anchor : '90%'
									}
									,{ 
										xtype : 'textfield',
										hidden :true,
										fieldLabel : 'id',
										Width:'100',
										id : 'cbid2',
										name : 'id',
										anchor : '90%'
									}
									]
								}, 
								{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										id : 'inst_id',
										fieldLabel : '组织机构代码',
										name:'ORG_ID',
										labelStyle:{
											width:'120px'
										},	
									
										anchor : '90%'
									}]
								}, 
								{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										id : 'groupid',
										hidden:true,
										fieldLabel : '客户群编号-测试2',
										name:'custgroupId',
										labelStyle:{
											width:'120px'
										},	
									
										anchor : '90%'
									}]
								}/*, 
								{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										id : 'sql',
										//fieldLabel : 'SQL',
										name:'sql',
										value:'sql',
										labelStyle:{
											width:'120px'
										},	
									
										anchor : '90%'
									}]
								}*//*,{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'datefield',
										id : 'in_date',
										name:'IN_DATE',
										fieldLabel : '加入群日期',
										 format:'Y-m-d', 
										labelStyle:{
											width:'120px'
										},	
										
										anchor : '90%'
									}]
								}*/]
					}],
					//buttonAlign:'center',
					buttons:[{
						text:'查询',
						handler:function()
						{
						   cusstoreActDetail.load(
									/*		{ params : {
				                                   start : 0,
				                                   limit : cusGroupbbar.pageSize }} */
						   );
//							Ext.Ajax.request({
//								url:basepath+'/QueryCustomerActListGroup.json',
//		                        method: 'POST',
//								success : function(response) {
//									Ext.Msg.alert('提示', '动态客户群成员设置--上部查询');
//								},
//								failure : function(response) {
//									Ext.Msg.alert('提示','操作失败' );
//								},
//								params : {
//							 		cst_id: Ext.getCmp('cst_id').getValue(),
//							 		inst_id:Ext.getCmp('inst_id').getValue()
//							 		//in_date:Ext.getCmp('in_date').getValue()
//								}});
						}
					},{
						text:'重置',
						handler : function() {
							memberSearch.getForm().reset();
						}
					}]
		});

		/**************************************************************************************/
		
		/**动态客户群成员设置--下部Grade********************************************************/
	    var cusGridDetailAct = new Ext.grid.GridPanel({
//	    	tbar:custbarDetail,
	        store: cusstoreActDetail,
			cm : cuscmActDetail,
			height :250,
//			sm:cussmDetail,
			bbar : cusbbarDetailAct,
			selModel:new Ext.grid.RowSelectionModel({
					singleSelect:true
					}),
	        stripeRows: true,
	       listeners:{
	       	rowdblclick:function()
	       	{
	       		//window.location="../customerManager/customerViewIndex.html";
	       	}
	       },
	        width: '100%'
	    });
	    /*********************************************************************************/
	    
	    /**动态客户群成员查看**************************************************************/
	    var cusrownumMemActDetail = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
	   // 定义列模型
		var cuscmMemActDetail = new Ext.grid.ColumnModel([
   			  cusrownumMemActDetail,
   			  {header : 'GROUP_MEM_ID', dataIndex : 'GROUP_MEM_ID',sortable : true,width : 150,hidden:true}, 
   			  {header : 'CUST_ID', dataIndex : 'CUST_ID',sortable : true,width : 150,hidden:true},
   			  {header : '组织机构代码',dataIndex : 'ORG_ID',sortable : true,width : 100}, 
              {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 100 }, 
              {header : '客户性别', dataIndex : 'SEX',sortable : true,width : 100 },
              {header : '客户级别', dataIndex : 'CUST_GRADE',sortable : true,width : 100 },
              {header : '客户经理名', dataIndex : 'STAFF_NAME',sortable : true,width : 100 },
              {header : '加入群组日期',dataIndex:'IN_DATE',value:eventDate.getDate(),sortable : true}
   				]);	
	    
		// 每页显示条数下拉选择框
		var cuspagesize_comboDetail = new Ext.form.ComboBox({
							name : 'pagesize',
							triggerAction : 'all',
							mode : 'local',
							store : new Ext.data.ArrayStore({
										fields : ['value', 'text'],
										data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
									}),
							valueField : 'value',
							displayField : 'text',
							value : '100',
							editable : false,
							width : 85
						});
		
		var cusrownumMemActDetail = parseInt(cuspagesize_comboDetail.getValue());
	    // 改变每页显示条数reload数据
	    cuspagesize_comboDetail.on("select", function(comboBox) {
	    	cusbbarMemActDetail.pageSize = parseInt(cuspagesize_comboDetail.getValue()),
	    	cusstoreDetail.reload({
	            params : {
	                start : 0,
	                limit : parseInt(cuspagesize_comboDetail.getValue())
	            }
	        });
	    });
	    // 分页工具栏
	    var cusbbarMemActDetail = new Ext.PagingToolbar({
						pageSize : cusrownumMemActDetail,
						store : custListDetail,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', cuspagesize_comboDetail]
					});
		
	    var cusMemGridDetail = new Ext.grid.GridPanel({
//	    	tbar:custbarDetail,
	        store: custListDetail,
			cm : cuscmMemActDetail,
			height :355,
//			sm:cussmDetail,
			bbar : cusbbarMemActDetail,
			selModel:new Ext.grid.RowSelectionModel({
					singleSelect:true
					}),
	        stripeRows: true,
	       listeners:{
	       	rowdblclick:function()
	       	{
	       		//window.location="../customerManager/customerViewIndex.html";
	       	}
	       },
	        width: '100%'
	    });
	    
	    
	    var winList=new Ext.Window({
			//layout : 'fit',
					width : 800,
					height :420,
					closable : true,
					resizable : false,
					collapsible : false,
					draggable : true,
					closeAction : 'hide',
					title : '动态客户群成员查看',
					//titleCollapse : false,
					modal : true, // 模态窗口 
					//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
					animCollapse : false,
					//maximizable : true,
					//maximized : true,
					border : false,
					closable : true,
					//animateTarget : Ext.getBody(),
					constrain : true,
					//items : [memberSearch,cusGridDetail],
					items : [cusMemGridDetail],
					buttonAlign:'center',
					buttons:[{
				  			text: '关闭',
				  			handler:function(){
						    winList.hide();
						}
		 				}]	
			});
	    /*****************************************************************************************/

		/**动态客户群成员设置**********************************************************************/
		var winMemberActList=new Ext.Window({
			//layout : 'fit',
					width : 800,
					height :420,
					closable : true,
					resizable : false,
					collapsible : false,
					draggable : true,
					closeAction : 'hide',
					//title : '成员企业信息',
					//titleCollapse : false,
					modal : true, // 模态窗口 
					//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
					animCollapse : false,
					//maximizable : true,
					//maximized : true,
					border : false,
					closable : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [memberSearchAct,cusGridDetailAct],
					buttonAlign:'center',
					buttons:[{
				  			text: '保存',
//				  			handler:function(){
//			 		        winMemberList.hide();
//						}
				  			handler:function()
							{   /*if(!addCustomerGroup.getForm().isValid())
							{ 
								Ext.Msg.alert('提示','输入信息有误!');
								return false;
							}*/
								/*Ext.Ajax.request({
								    url:basepath+'/cust-set.json',
								    method: 'POST',
								    form:memberSearchAct.getForm().id,
									success : function(response) {
		    							Ext.Msg.alert('提示', '成功');
		    							store.load(
		    									{ params : {
		    						                   start : 0,
		    						                   limit : parseInt(20) }} );
		    						},
		    						failure : function(response) {
		    							Ext.Msg.alert('提示', response.responseText);
		    						},
										params : {
											//'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
											'operate':'add'
										}
								});*/
								//win.hide();
									//alert(Ext.getCmp('cst_id').getValue());
								     //var cbid= Ext.getCmp('cbid').getValue();
										/*Ext.Ajax.request({
											url:basepath+'/cust-set.json',
					                        method: 'POST',
											success : function(response) {
												Ext.Msg.alert('提示', '保存成功');
											},
											failure : function(response) {
												Ext.Msg.alert('提示','保存失败' );
											},
											params : {
										 		cst_id: Ext.getCmp('cst_id').getValue(),
										 		inst_id:Ext.getCmp('inst_id').getValue(),
										 		groupid:Ext.getCmp('groupid').getValue()
										 		//in_date:Ext.getCmp('in_date').getValue()
											}});*/
											Ext.Ajax.request({
											    url:basepath+'/cust-set.json',
											    method: 'POST',
											    form:memberSearchAct.getForm().id,
											    success : function(response) {
											    	var resultArray  =response.status;
											    	if(response.status==201||response.status==200){
														Ext.Msg.alert('提示', '修改成功');
														/*store.load(	{ params : {
							                                   start : 0,
							                                   limit : bbar.pageSize }} );*/
														}
											    	else{Ext.Msg.alert('提示', '修改失败');}
													},
													params : {
														'operate':'update',
														cst_name: Ext.getCmp('cst_name').getValue(),
												 		inst_id:Ext.getCmp('inst_id').getValue(),
												 		groupid:Ext.getCmp('groupid').getValue()
													}
											});
											winMemberActList.hide();
							}
		 				},{
				  			text: '取消',
				  			handler:function(){
		 					winMemberActList.hide();
						}
		 				}]	
			});
	/*********************************************************************************/