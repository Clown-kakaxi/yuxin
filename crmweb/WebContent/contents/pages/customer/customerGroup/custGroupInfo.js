/**静态客户群JS*************************************/ 
Ext.onReady(function(){
	
	var custGroupTypeStore = new Ext.data.ArrayStore({
		fields : ['typeId', 'typeText'],
		data : [[1, '静态群'], [2, '动态群']]
	});
	//客户群查询条件
	 var simple = new Ext.FormPanel({
	        frame:true,
	        id:'queryGroup',
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        labelAlign:'center',
	        items: [{
	            items :[{ 
	            		layout:'column',
	                     items:[
	                     {
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户群编号',
	                             labelStyle: 'text-align:right;',
	                             name:'CUSTGROUP_ID',
	                             anchor:'90%'
	                         }]
	                     },	                     
	                     {
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户群名称',
	                             labelStyle: 'text-align:right;',
	                             name: 'CUSTGROUP_NAME',
	                             anchor:'90%'
	                         }]
	                     },	                     
	                     {
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                        	store : custGroupTypeStore,
	     						xtype : 'combo',
	     						resizable : true,
	     						fieldLabel : '客户群类型',
	     						name : 'CUSTGROUP_TYPE',
	     						hiddenName : 'CUSTGROUP_TYPE',
	     						valueField : 'typeId',
	     						displayField : 'typeText',
	     						mode : 'local',
	     						typeAhead : true,
	     						forceSelection : true,
	     						triggerAction : 'all',
	     						emptyText : '请选择',
	     						selectOnFocus : true,
	     						width : '100',
	     						anchor : '90%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'datefield',
	                             fieldLabel: '客户群创建日期',
	                             labelStyle: 'text-align:right;',
	                             format:'Y-m-d', //日期格式化
	                             name: 'CREATE_DATE',
	                             anchor:'90%'
	                         }]
	                     }
	            ]}
	            ]}],
			buttonAlign:'center',
	        buttons: [{
	            text: '查询',
	        	handler : function() {
	        		store.load(
	        				{ params : {
                                   start : 0,
                                   limit : bbar.pageSize }} );
               
			}
	        },{
	            text: '重置',
	        	handler : function() {
	        		simple.getForm().reset();   
			}
	            
	           
	        }]
	    });

	    
	    //新增客户群成员
		var addCustomer=new Ext.FormPanel({
		//layout:'fit',
		title:'客户查询',
		frame:true,
		border:false,
		labelAlign:'right',
		items : [{
					layout:'column',
					items : [
							{
								columnWidth : .33,
								labelWidth : 80, // 标签宽度
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户名称',
									//Width:'100',
									name : 'CUST_NAME',
									anchor : '95%'
								}]
							}, 
							{
								columnWidth : .33,
								labelWidth : 80, // 标签宽度
								layout : 'form',
								items : [{
									xtype : 'textfield',
									fieldLabel : '组织机构',
									//Width:'100',
									name : 'ORG_ID',
									anchor : '95%'
								}]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									hidden :true,
//									disabled:true,
									fieldLabel : '客户群号',
									labelStyle:{
										width:'120px'
									},	
									Width:'100',
									id : 'cbid',
									name : 'id',
									anchor : '90%'
								}]
							}]
				}],
				buttonAlign:'center',
				buttons:[{
					text:'查询',
					handler:function()
					{
						cusstore.reload(
	        				{ params : {
	                        start : 0,
	                        /*limit : cusGridDetail.pageSize*/
	                        limit : parseInt(20)}} );
			   
					},
					width:80
				},{
					text:'重置',
						handler : function() {
							addCustomer.getForm().reset();
						}
						
				}]
	});
	//新增客户群成员的表格面板 
	
	var custbar = new Ext.Toolbar({
		items:[
		{
			text:'归入客户群',
			handler:function()
			{
				batchAdd();
			
				
			}
		}
		]
	});
	var cussm = new Ext.grid.CheckboxSelectionModel();
	var cusrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cuscm = new Ext.grid.ColumnModel([cusrownum,cussm,
		   {header : 'cust_id', dataIndex : 'CUST_ID',sortable : true,width : 150,hidden:true}, 
           {header : '组织机构代码',dataIndex : 'ORG_ID',sortable : true,width : 150}, 
           {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 150 }, 
           {header : '客户类型',dataIndex : 'CUST_TYP',sortable : true,width : 150,hidden:true},
		   {header : '客户级别',dataIndex : 'CUST_LEV',sortable : true,width : 150,hidden:true}
			]);

	/**
	 * 数据存储
	 */
	var cusstore = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustomerDescGroup.json'}),
			        reader: new Ext.data.JsonReader({
                    totalProperty : 'json.count',
			        root:'json.data'
			        }, [ 'CUST_ID','CUST_NAME','ORG_ID','CUST_STS','CUST_GRADE'])
				});
	 
	  cusstore.on('beforeload', function() {
	   var conditionStr =  addCustomer.getForm().getValues(false);
       this.baseParams = {
              "condition":Ext.encode(conditionStr)
      };});
	
	// 每页显示条数下拉选择框
	var cuspagesize_combo = new Ext.form.ComboBox({
					name : 'pagesize',
					triggerAction : 'all',
					mode : 'local',
					store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
							}),
					valueField : 'value',
					displayField : 'text',
					value : '100',
					editable : false,
					width : 85
				});
	var cusnumber = parseInt(cuspagesize_combo.getValue());
			// 改变每页显示条数reload数据
			cuspagesize_combo.on("select", function(comboBox) {
						cusbbar.pageSize = parseInt(cuspagesize_combo.getValue());
						//number = parseInt(comboBox.getValue());
						cusstore.reload({
									params : {
										start : 0,
										limit : parseInt(cuspagesize_combo.getValue())
									}
								});
					});
	// 分页工具栏
	var cusbbar = new Ext.PagingToolbar({
						pageSize : cusnumber,
						store : cusstore,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', cuspagesize_combo]
					});

	var cusGrid = new Ext.grid.GridPanel({
		height: document.body.scrollHeight-155,
		frame : true,
		autoScroll : true,
		store : cusstore, // 数据存储
		stripeRows : true, // 斑马线
		cm : cuscm, // 列模型
		sm : cussm, // 复选框
		bbar : cusbbar,
        tbar:custbar,
		viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	    
	    //新增客户群
	  var addCustomerGroup=new Ext.FormPanel({
		//layout:'fit',
	  	//title : '新增客户群',
		  name:'addCustomerGroup',
	    id:'addCustomerGroup',
	  	labelAlign:'right',
		frame:true,
		border:false,
		style:'padding:10 10 10 10',
		items : [
				{
					layout:'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '*客户群名称',
									allowBlank : false,
									maxLength :20,
							/*		labelStyle:{
										width:'120px'
									},	*/
									labelStyle: 'text-align:right;',
									Width:'100',
									name : 'custgroupName',
									anchor : '90%'
								}]
							},{
		                         columnWidth:.33,
		                         layout: 'form',
		                         items: [{
		                        	id: 'cusgrpType',
		                        	store : custGroupTypeStore,
		     						xtype : 'combo',
		     						resizable : true,
		     						fieldLabel : '客户群类型',
		     						allowBlank : false,
		     						name : 'custgroupType',
		     						hiddenName : 'custgroupType',
		     						valueField : 'typeId',
		     						displayField : 'typeText',
		     						mode : 'local',
		     						typeAhead : true,
		     						forceSelection : true,
		     						triggerAction : 'all',
		     						emptyText : '请选择',
		     						selectOnFocus : true,
		     						width : '100',
		     						anchor : '90%'
		                         }]
		                     }, {
								columnWidth : .33,
								layout : 'form',
								items : [{ 
									id:'basegrpid',
									xtype : 'textfield',
									hidden:true,
									//disabled:true,
									fieldLabel : '客户群编号-测试1',
									labelStyle: 'text-align:right;',
									Width:'100',
									name : 'custgroupId',
									anchor : '90%'
								}]
							}, 
							{
								layout : 'form',
								items : [{ 
									hidden :true,
									xtype : 'textfield',
									fieldLabel : 'id',
									labelStyle:{
										width:'120px'
									},	
									labelStyle: 'text-align:right;',
									Width:'100',
									name : 'id',
									anchor : '90%'
								}]
							}]
				}
				,
				{
					layout:'form',
					items:{
						name : 'describe',
						anchor:'80%',
						xtype:'textarea',
						maxLength :360,
						fieldLabel : '客户群描述'
					}
				}
				]
	});
	
	 //修改客户群
	  var updateCustomerGroup=new Ext.FormPanel({
		//layout:'fit',
	  	//title : '新增客户群',
	  	labelAlign:'right',
		frame:true,
		border:false,
		style:'padding:10 10 10 10',
		items : [
				{
					layout:'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '*客户群名称',
									allowBlank : false,
									maxLength:20,
									labelStyle: 'text-align:right;',
									Width:'100',
									name : 'custgroupName',
									anchor : '90%'
								}]
							}, 
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
							     	hidden:true,
									fieldLabel : '客户群编号',
									labelStyle: 'text-align:right;',
									Width:'100',
									name : 'custgroupId',
									anchor : '90%'
								}]
							},
							{
								layout : 'form',
								items : [{ 
									hidden :true,
									xtype : 'textfield',
									fieldLabel : 'id',
									labelStyle:{
										width:'120px'
									},	
									Width:'100',
									name : 'id',
									anchor : '90%'
								}]
							}]
				}
				,
				{
					layout:'form',
					items:{
						name : 'describe',
						anchor:'80%',
						maxLength :360,
						xtype:'textarea',
						fieldLabel : '客户群描述'
					}
				}
				]
	});

		var updatewin=new Ext.Window({
			title:"修改客户群信息",
	        layout : 'fit',
			width : 750,
			height :350,
			closable : true,
			resizable : false,
			collapsible : false,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口 
			//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
			animCollapse : false,
			maximizable : true,
			border : false,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [updateCustomerGroup],
			buttonAlign:'center',
			buttons:[{
				text:'保存',
				handler:function()
				{if(!updateCustomerGroup.getForm().isValid())
				{ 
					Ext.Msg.alert('提示','输入有误!');
					return false;
				}
					Ext.Ajax.request({
					    url:basepath+'/customer-group.json',
					    method: 'POST',
					    form:updateCustomerGroup.getForm().id,
					    success : function(response) {
					    	var resultArray  =response.status;
					    	if(response.status==201||response.status==200){
								Ext.Msg.alert('提示', '修改成功');
								store.load(	{ params : {
	                                   start : 0,
	                                   limit : bbar.pageSize }} );
								}
					    	else{Ext.Msg.alert('提示', '修改失败');}
							},
							params : {
								'operate':'update'
							}
					});
					updatewin.hide();
				}
			},{
		  			text: '取消',
		  			handler:function(){
		  			updatewin.hide();
				}
 				}]	
	});
		var cussmGroupMemberCheck = new Ext.grid.CheckboxSelectionModel();
		
		var cusGrouprownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});			
				
		var groupMemberCol = new Ext.grid.ColumnModel(
				[
			  cusGrouprownum,cussmGroupMemberCheck,
			  {header : 'id', dataIndex : 'GROUP_MEM_ID',sortable : true,width : 150,hidden:true}, 
              {header : '组织机构代码',dataIndex : 'ORG_ID',sortable : true,width : 100}, 
              {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 100 }, 
              {header:'加入群组日期',dataIndex:'IN_DATE',sortable : true}
				]);	
		var custGroupStore = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustomerGroup2.json'}
	        ),
	        reader: new Ext.data.JsonReader({
	        root:'json.data',
            totalProperty : 'json.count'
	        }, [ 'GROUP_MEM_ID','CUST_NAME','ORG_ID','IN_DATE'])
		});
		custGroupStore.on('beforeload', function() {
		       this.baseParams = {
		    		   cbid: Ext.getCmp('cbid').getValue()
		      };});
		var cusGroupcombo = new Ext.form.ComboBox({
	        name : 'pagesize',
	        triggerAction : 'all',
	        mode : 'local',
	        store : new Ext.data.ArrayStore({
	            fields : ['value', 'text'],
	            data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
	        }),
	        valueField : 'value',
	        displayField : 'text',
	        value : '100',
	        editable : false,
	        width : 85
	    });
	    var cusGroupnumber = parseInt(cusGroupcombo.getValue());
	    // 改变每页显示条数reload数据
	    cusGroupcombo.on("select", function(comboBox) {
	    	cusGroupBbar.pageSize = parseInt(cusGroupcombo.getValue()),
	    	custGroupStore.reload({
	            params : {
	                start : 0,
	                limit : parseInt(cusGroupcombo.getValue())
	            }
	        });
	    });
		var cusGroupBbar = new Ext.PagingToolbar({
	       pageSize : cusGroupnumber,
	       store : custGroupStore,
	       displayInfo : true,
	       displayMsg : '显示{0}条到{1}条,共{2}条',
	       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	       emptyMsg : "没有符合条件的记录",
	       items : ['-', '&nbsp;&nbsp;', cusGroupcombo
	                ]
	   });
						
		
			var cusGroupMemeberGrid = new Ext.grid.GridPanel({
				title:'客户群成员列表',
				frame : true,
				height: document.body.scrollHeight-58,
				autoScroll : true,
				store: custGroupStore,
				stripeRows : true, // 斑马线
				cm : groupMemberCol,
				sm:cussmGroupMemberCheck,
				tbar:[{'text':'移除客户群',handler:function(){
				     batchDelete();
				}}],
				bbar : cusGroupBbar,
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	    
		var newmember=new Ext.Window(
				{
					   layout : 'fit',
						width:1000,
						height :420,
						closable : true,
						resizable : false,
						collapsible : false,
						maximizable: true,
						maximized:true,
						draggable : true,
						closeAction : 'hide',
						title : '客户群成员维护',
						buttonAlign:'center',
						modal : true, // 模态窗口 
						//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
						animCollapse : false,
						border : false,
						closable : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [
					         {
							layout : 'column',
							border : false,
							items : [
							        {
								columnWidth : .53,
								layout : 'form',
								border : false,
								items : [addCustomer,cusGrid]}, {
									columnWidth : .47,
									layout : 'form',
									border : false,
									items : [cusGroupMemeberGrid]
								}
								]
						}
						],
						
						buttonAlign:'center',
						
						buttons:[{
  				  			text: '关闭',
   				  			handler:function(){
   				  			newmember.hide();
    						}
			 				}]	
		});
		var custbarDetail = new Ext.Toolbar({
			items:[
			{
				text:'查看详细',
				handler:function()
				{custview();	
				}
			}
			]
		});
		var cusrownumDetail = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});
		var cusrownumDetailAct = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cuscmDetail = new Ext.grid.ColumnModel([
   			  cusrownumDetail,
   			  {header : 'GROUP_MEM_ID', dataIndex : 'GROUP_MEM_ID',sortable : true,width : 150,hidden:true}, 
   			  {header : 'CUST_ID', dataIndex : 'CUST_ID',sortable : true,width : 150,hidden:true},
   			  {header : '组织机构代码',dataIndex : 'ORG_ID',sortable : true,width : 100}, 
              {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 100 }, 
              {header : '客户性别', dataIndex : 'SEX',sortable : true,width : 100 },
              {header : '客户级别', dataIndex : 'CUST_GRADE',sortable : true,width : 100 },
              {header : '客户经理名', dataIndex : 'STAFF_NAME',sortable : true,width : 100 },
              {header : '加入群组日期',dataIndex:'IN_DATE',sortable : true}
   				]);	
		
//		var cuscmActDetail = new Ext.grid.ColumnModel([
// 			  cusrownumDetailAct,
// 			  {header : 'GROUP_MEM_ID', dataIndex : 'GROUP_MEM_ID',sortable : true,width : 150,hidden:true}, 
// 			  {header : 'CUST_ID', dataIndex : 'CUST_ID',sortable : true,width : 150,hidden:true},
// 			  {header : '组织机构代码',dataIndex : 'ORG_ID',sortable : true,width : 100}, 
//              {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 100 }, 
//              {header : '客户性别', dataIndex : 'SEX',sortable : true,width : 100 },
//              {header : '客户级别', dataIndex : 'CUST_GRADE',sortable : true,width : 100 },
//              {header : '客户经理名', dataIndex : 'CUST_MANAGER_NAME',sortable : true,width : 100 }
//            //{header : '加入群组日期',dataIndex:'IN_DATE',sortable : true}
// 				]);	

			var cusstoreDetail = new Ext.data.Store({
				restful:true,
		        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustomerListGroup.json'}
		        ),
		        reader: new Ext.data.JsonReader({
		        totalProperty : 'json.count',
		        root:'json.data'
		        //totalProperty : 'list'
		        }, [ 'GROUP_MEM_ID','CUST_ID','CUST_NAME','ORG_ID','SEX','CUST_GRADE','STAFF_NAME','IN_DATE'])
			});
			
			
			
			cusstoreDetail.on('beforeload', function() {
//				   var conditionStr =  memberSearch.getForm().getValues(false);
//				   alert(conditionStr);
//				   var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
				   //alert(Ext.getCmp('cbid').getValue());
			       this.baseParams = {
			    		  start : 0,
			              limit : parseInt(cuspagesize_comboDetail.getValue()),
//			              "condition":Ext.encode(conditionStr),
			              //cbid:checkedNodes[0].data.id
			              cbid: Ext.getCmp('cbid').getValue()
			      };});	
			
		
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
		 var cusrownumDetail = parseInt(cuspagesize_comboDetail.getValue());
		    // 改变每页显示条数reload数据
		 cuspagesize_comboDetail.on("select", function(comboBox) {
		    	cusbbarDetail.pageSize = parseInt(cuspagesize_comboDetail.getValue()),
		    	cusstoreDetail.reload({
		            params : {
		                start : 0,
		                limit : parseInt(cuspagesize_comboDetail.getValue())
		            }
		        });
		    });
		// 分页工具栏
		var cusbbarDetail = new Ext.PagingToolbar({
							pageSize : cusrownumDetail,
							store : cusstoreDetail,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
							emptyMsg : "没有符合条件的记录",
							items : ['-', '&nbsp;&nbsp;', cuspagesize_comboDetail]
						});
	    // create the Grid
						
	    var cusGridDetail = new Ext.grid.GridPanel({
//	    	tbar:custbarDetail,
	        store: cusstoreDetail,
			cm : cuscmDetail,
			height :355,
//			sm:cussmDetail,
			bbar : cusbbarDetail,
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
	    
		/**动态客户群设置************************************************************************/
/*		var cusstoreActDetail = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustomerActListGroup.json'}
	        ),
	        reader: new Ext.data.JsonReader({
	        	successProperty : 'success',
	        totalProperty : 'json.count',
	        messageProperty : 'message',
	        root:'json.data'
	        //totalProperty : 'list'
	        }, [ 'CUST_ID','CUST_NAME','ORG_ID','SEX','CUST_GRADE','CUST_MANAGER_NAME'])
		});

		
//		cusstoreActDetail.on('beforeload', function() {
//			   var conditionStr =  memberSearchAct.getForm().getValues(false);
//			   var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
//			   //alert(Ext.getCmp('cbid').getValue());
//		       this.baseParams = {
//		    		  start : 0,
//		              limit : parseInt(cuspagesize_comboDetail.getValue()),
//		              "condition":Ext.encode(conditionStr),
//		              //cbid:checkedNodes[0].data.id
//		              cbid: Ext.getCmp('cbid').getValue()
//		      };});
		
		
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
						});*/
		/**************************************************************************************/
	    
	    /**动态客户群成员设置--下部Grade********************************************************/
/*	    var cusGridDetailAct = new Ext.grid.GridPanel({
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
	    });*/
	    /*********************************************************************************/

		var memberSearch=new Ext.FormPanel({
			//layout:'fit',
			title:'群成员信息',
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
										fieldLabel : '客户名称',
										name:'CUST_NAME',
										labelStyle:{
											width:'120px'
										},	
										anchor : '90%'
									}
									/*,{ 
										xtype : 'textfield',
										hidden :true,
										fieldLabel : 'id',
										Width:'100',
										id : 'cbid2',
										name : 'id',
										anchor : '90%'
									}*/
									]
								}, 
								{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										fieldLabel : '组织机构代码',
										name:'ORG_ID',
										labelStyle:{
											width:'120px'
										},	
									
										anchor : '90%'
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'datefield',
										name:'IN_DATE',
										fieldLabel : '加入群日期',
										 format:'Y-m-d', 
										labelStyle:{
											width:'120px'
										},	
										
										anchor : '90%'
									}]
								}]
					}],
					//buttonAlign:'center',
					buttons:[{
						text:'查询',
						handler:function()
						{ cusstoreDetail.load(
						/*		{ params : {
	                                   start : 0,
	                                   limit : cusGroupbbar.pageSize }} */
								
						
						);}
					},{
						text:'重置',
						handler : function() {
							memberSearch.getForm().reset();
						}
					}]
		});

		/**动态客户群成员设置--上部查询**********************************************************/
//		var memberSearchAct=new Ext.FormPanel({
//			//layout:'fit',
//			title:'动态群成员信息',
//			frame:true,
//			border:false,
//			buttonAlign:'center',
//			labelAlign:'right',
//			items : [{
//						layout:'column',
//						items : [
//								{
//									columnWidth : .33,
//									layout : 'form',
//									items : [{
//										xtype : 'textfield',
//										id : 'cst_id',
//										fieldLabel : '客户名称',
//										name:'CUST_NAME',
//										labelStyle:{
//											width:'120px'
//										},	
//										anchor : '90%'
//									}
//									,{ 
//										xtype : 'textfield',
//										hidden :true,
//										fieldLabel : 'id',
//										Width:'100',
//										id : 'cbid2',
//										name : 'id',
//										anchor : '90%'
//									}
//									]
//								}, 
//								{
//									columnWidth : .33,
//									layout : 'form',
//									items : [{ 
//										xtype : 'textfield',
//										id : 'inst_id',
//										fieldLabel : '组织机构代码',
//										name:'ORG_ID',
//										labelStyle:{
//											width:'120px'
//										},	
//									
//										anchor : '90%'
//									}]
//								}, 
//								{
//									columnWidth : .33,
//									layout : 'form',
//									items : [{ 
//										xtype : 'textfield',
//										id : 'groupid',
//										fieldLabel : '客户群ID',
//										name:'CUSTGROUP_ID',
//										labelStyle:{
//											width:'120px'
//										},	
//									
//										anchor : '90%'
//									}]
//								}, 
//								{
//									columnWidth : .33,
//									layout : 'form',
//									items : [{ 
//										xtype : 'textfield',
//										id : 'sql',
//										//fieldLabel : 'SQL',
//										name:'sql',
//										value:'sql',
//										labelStyle:{
//											width:'120px'
//										},	
//									
//										anchor : '90%'
//									}]
//								},{
//									columnWidth : .33,
//									layout : 'form',
//									items : [{ 
//										xtype : 'datefield',
//										id : 'in_date',
//										name:'IN_DATE',
//										fieldLabel : '加入群日期',
//										 format:'Y-m-d', 
//										labelStyle:{
//											width:'120px'
//										},	
//										
//										anchor : '90%'
//									}]
//								}]
//					}],
//					//buttonAlign:'center',
//					buttons:[{
//						text:'查询123',
//						handler:function()
//						{
//						alert();
////						   cusstoreActDetail.load(
////									/*		{ params : {
////				                                   start : 0,
////				                                   limit : cusGroupbbar.pageSize }} */
////						   );
////							Ext.Ajax.request({
////								url:basepath+'/QueryCustomerActListGroup.json',
////		                        method: 'POST',
////								success : function(response) {
////									Ext.Msg.alert('提示', '动态客户群成员设置--上部查询');
////								},
////								failure : function(response) {
////									Ext.Msg.alert('提示','操作失败' );
////								},
////								params : {
////							 		cst_id: Ext.getCmp('cst_id').getValue(),
////							 		inst_id:Ext.getCmp('inst_id').getValue()
////							 		//in_date:Ext.getCmp('in_date').getValue()
////								}});
//						}
//					},{
//						text:'重置',
//						handler : function() {
//							memberSearch.getForm().reset();
//						}
//					}]
//		});
//		

		
		
		/*********************************************************************************/
		debugger;
		var winMemberList=new Ext.Window({
			//layout : 'fit',
					width : 800,
					height :420,
					closable : true,
					resizable : false,
					collapsible : false,
					draggable : true,
					closeAction : 'hide',
					title : '静态客户群成员查看',
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
					items : [cusGridDetail],
					buttonAlign:'center',
					buttons:[{
				  			text: '关闭',
				  			handler:function(){
			 		        winMemberList.hide();
						}
		 				}]	
			});
		
		/**动态客户群成员设置**********************************************************************/
//		var winMemberActList=new Ext.Window({
//			//layout : 'fit',
//					width : 800,
//					height :420,
//					closable : true,
//					resizable : false,
//					collapsible : false,
//					draggable : true,
//					closeAction : 'hide',
//					//title : '成员企业信息',
//					//titleCollapse : false,
//					modal : true, // 模态窗口 
//					//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
//					animCollapse : false,
//					//maximizable : true,
//					//maximized : true,
//					border : false,
//					closable : true,
//					animateTarget : Ext.getBody(),
//					constrain : true,
//					items : [memberSearchAct,cusGridDetailAct],
//					buttonAlign:'center',
//					buttons:[{
//				  			text: '保存',
////				  			handler:function(){
////			 		        winMemberList.hide();
////						}
//				  			handler:function()
//							{   /*if(!addCustomerGroup.getForm().isValid())
//							{ 
//								Ext.Msg.alert('提示','输入信息有误!');
//								return false;
//							}*/
//								/*Ext.Ajax.request({
//								    url:basepath+'/cust-set.json',
//								    method: 'POST',
//								    form:memberSearchAct.getForm().id,
//									success : function(response) {
//		    							Ext.Msg.alert('提示', '成功');
//		    							store.load(
//		    									{ params : {
//		    						                   start : 0,
//		    						                   limit : parseInt(20) }} );
//		    						},
//		    						failure : function(response) {
//		    							Ext.Msg.alert('提示', response.responseText);
//		    						},
//										params : {
//											//'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
//											'operate':'add'
//										}
//								});*/
//								//win.hide();
//									//alert(Ext.getCmp('cst_id').getValue());
//								     //var cbid= Ext.getCmp('cbid').getValue();
//										Ext.Ajax.request({
//											url:basepath+'/cust-set.json',
//					                        method: 'POST',
//											success : function(response) {
//												Ext.Msg.alert('提示', '保存成功');
//											},
//											failure : function(response) {
//												Ext.Msg.alert('提示','保存失败' );
//											},
//											params : {
//										 		cst_id: Ext.getCmp('cst_id').getValue(),
//										 		inst_id:Ext.getCmp('inst_id').getValue()
//										 		//in_date:Ext.getCmp('in_date').getValue()
//											}});
//							}
//		 				}]	
//			});
		/*********************************************************************************/
		
//		var pars = addCustomerGroup.getForm().getFieldValues();
 // 表格工具栏
	var tbar = new Ext.Toolbar({
		items : [ {
					text : '新增客户群',
					handler : function() {
						var win=new Ext.Window({
				        	title:"新增客户群",
					        layout : 'fit',
							width : 750,
							height :300,
							closable : true,
							resizable : false,
							collapsible : false,
							draggable : true,
							closeAction : 'hide',
							//titleCollapse : false,
							modal : true, // 模态窗口 
							//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
							animCollapse : false,
							maximizable : true,
							border : false,
							closable : true,
							animateTarget : Ext.getBody(),
							constrain : true,
							items : [addCustomerGroup],
							buttonAlign:'center',
							listeners:{
								"hide":function(){
									addCustomerGroup.getForm().reset();
								}
								}, 
							buttons:[{
								text:'下一步',
								handler:function()
								{   if(!addCustomerGroup.getForm().isValid())
									{ 
										Ext.Msg.alert('提示','输入信息有误!');
										return false;
									}
								  
									Ext.Ajax.request({
									    url:basepath+'/customer-group.json',
									    method: 'GET',
//									    params : pars,
									    form:addCustomerGroup.getForm().id,
										success : function(a,b,c) {
			    							//Ext.Msg.alert('提示', '成功');
											Ext.Ajax.request({
												url : basepath+'/session-info!getPid.json',
												method : 'GET',
												success : function(a,b,v) {
												    debugger;
													win.hide();
//												    alert(Ext.decode(a.responseText).pid);
												    Ext.getCmp('basegrpid').setValue(Ext.decode(a.responseText).pid);
												    Ext.getCmp('groupid').setValue(Ext.decode(a.responseText).pid);
												    Ext.getCmp('cbid').setValue(Ext.decode(a.responseText).pid);
												    store.load({ params : {
					    						                   start : 0,
					    						                   limit : parseInt(20) }} );
												},
												failure : function(a,b,c) {
													Ext.Msg.alert('提示','获取群序列号错误!');
												}
											});
			    						},
			    						failure : function(response) {
			    							Ext.Msg.alert('提示', response.responseText);
			    						},
											params : {
												//'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
												'operate':'add'
											}
									});
									
									if("1" == Ext.getCmp("cusgrpType").value){
										newmember.show();
									}else if("2" == Ext.getCmp("cusgrpType").value){
										winMemberActList.show();
									}
								}
							},{
						  			text: '取消',
						  			handler:function(){
					 		        win.hide();
								}
				 				}]	
					});
			/*		Ext.Ajax.request({
							url:basepath+'/querycustomerbasenumber.json',
						    method: 'GET',
						    success:function(response){
						    	var json=Ext.util.JSON.decode(response.responseText);
						    	 Ext.getCmp('customerBaseNumber').setValue(json.json.data);
					    }
						});*/
						
						
					/*	addCustomerGroup.getForm().load({
					          //waitMsg: '正在加载数据',
					  	        //waitTitle: '提示',
						restful:true,	
					    url:basepath+'/querycustomerbasenumber.json',
					    method: 'GET',
					   callback : function(form,action) {
						   debugger;
					    	Ext.Msg.alert('提示',action);
				        	 //debugger;
							//var resultArray = Ext.util.JSON.decode(response.responseText);
								//Ext.Msg.alert('提示',response.responseText);
							}
					     }
						);*/
						win.show();
						
					}
				},'-',{
					text:'客户群成员维护',
					handler : function() {
						  var _record = cusGroupGrid.getSelectionModel().getSelected();
						  var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
					        if (!_record||checkedNodes.length>1) {
					        	Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
					        } else {
					        	if("1" == _record.data.custgroupType){
					        		addCustomer.getForm().loadRecord(_record);
							          Ext.getCmp('cbid').setValue(_record.data.custgroupId);
							          newmember.show();
							          custGroupStore.load(
							        		  {
													params : {
														cbid: Ext.getCmp('cbid').getValue()
													}
							        		  }
							          );
							          cusstore.load();
					        	}else if("2" == _record.data.custgroupType){
					        		Ext.MessageBox.alert('消息', '请选择静态客户群！');
					        	}
					        }
					}
				}/*,'-',{
					text:'动态客户群成员设置',
					handler:function() {
						 var _record = cusGroupGrid.getSelectionModel().getSelected();
						 var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
					     if (!_record||checkedNodes.length>1) {
					        Ext.MessageBox.alert('查询操作', '请选择要操作的一列！');
					        }
					     else {
					    	 winMemberActList.show();
							 var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
							 Ext.getCmp('groupid').setValue(_record.data.custgroupId);
					         
							//memberSearch.getForm().loadRecord(_record);
							cusstoreDetail.load(
				        		  {
										params : {
											cbid: checkedNodes[0].data.id
										}
				        		  }
				          );
					    }
					}
				}*/,'-',{
					text:'查看群成员',
					handler:function() {
						 var _record = cusGroupGrid.getSelectionModel().getSelected();
						 var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
					     if (!_record||checkedNodes.length>1) {
					        Ext.MessageBox.alert('查询操作', '请选择要操作的一列！');
					        }
					     else {
							
							 var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
							 Ext.getCmp('cbid').setValue(_record.data.custgroupId);
							 
							//memberSearch.getForm().loadRecord(_record);
							if(_record.data.custgroupType == '1'){
								winMemberList.show();
								cusstoreDetail.load(
						        		  {
												params : 
												{
						        			        start : 0,
						        			        limit : 20,
													cbid: checkedNodes[0].data.custgroupId
												}
						        		  }
						          );
							}else if(_record.data.custgroupType == '2'){
								winList.show();
								custListDetail.load(
						        		  {
												params : {
						        			        start : 0,
					        			            limit : 20,
													cbid: checkedNodes[0].data.custgroupId
												}
						        		  }
						          );
							}else{
								Ext.MessageBox.alert('查询操作', '无客户类型！');
							}
							}
					}
				},'-',{
					text:'修改客户群',
					handler : function() {
					
						updateCustomerBase(cusGroupGrid);
						
					}
				},'-',{
					text:'删除客户群',
					handler:function()
					{
						deleteCustomerBase(cusGroupGrid);
						store.load(
		        				{ params : {
	                                   start : 0,
	                                   limit : bbar.pageSize }} );
	               
				
				
					}
				}]
	});
	
	
	//客户群表格面板
	
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cusGroupsm = new Ext.grid.CheckboxSelectionModel();
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm,
	       //{header : 'id', dataIndex : 'id',sortable : true,width : 150,hidden :true}, 
	       {header : '客户群编号', dataIndex : 'custgroupId',sortable : true,width : 150 }, 
           {header : '客户群名称',dataIndex : 'custgroupName',sortable : true,width : 150}, 
           {header : '客户群创建日期',dataIndex : 'createDate',sortable : true,width : 150},
           /*{header : '客户群成员数', dataIndex : 'customerBaseMemberNum',renderer:function(value){
               if(value==''){
                   return "0";
               }else{
                   return value;
               }
           },sortable : true,width : 150 }, */
           {header : '客户群描述',dataIndex : 'describe',sortable : true,width : 150},
           {header : '客户群类型',dataIndex : 'custgroupType',sortable : true,width : 150}
			]);
	/**
	 * 数据存储
	 */

	var store = new Ext.data.Store({
					restful:true,
					proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCustomerGroup.json'
			    /*   	success : function(response) {
							var resultArray = Ext.util.JSON.decode(response.responseText);
							Ext.Msg.alert('提示', response.responseText);
						}*/
			        }),
			        reader: new Ext.data.JsonReader({
			        totalProperty : 'json.count',
			        root:'json.data'
			        }, [{name: 'custgroupId', mapping: 'CUSTGROUP_ID'},{name: 'custgroupName', mapping: 'CUSTGROUP_NAME'},{name: 'createDate', mapping: 'CREATE_DATE'},{name: 'describe', mapping: 'DESCRIBE'},{name: 'custgroupType', mapping: 'CUSTGROUP_TYPE'}/*,{name: 'customerBaseMemberNum', mapping: 'MEMBERSNUM'},{name: 'customerBaseDescribe', mapping: 'CUST_BASE_DESC'}*/])
				});
	
	
	/***********************************************/
	store.load(
			{ params : {
                   start : 0,
                   limit : parseInt(20) }} );
	/***********************************************/
	
     store.on('beforeload', function() {
    	var conditionStr =  simple.getForm().getValues(false);
        this.baseParams = {
                "condition":Ext.encode(conditionStr)
        };
     });
	
	var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [[100, '100条/页'], [200, '200条/页'],[500, '500条/页'], [1000, '1000条/页']]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '100',
        editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
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
	var bbar = new Ext.PagingToolbar({
       pageSize : number,
       store : store,
       displayInfo : true,
       displayMsg : '显示{0}条到{1}条,共{2}条',
       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
       emptyMsg : "没有符合条件的记录",
       items : ['-', '&nbsp;&nbsp;', pagesize_combo
                ]
   });
	

  

    // create the Grid
	var cusGroupGrid = new Ext.grid.GridPanel({
		frame : true,
		 height: document.body.scrollHeight-100,
		autoScroll : true,
		region : 'center', // 返回给页面的div
		store: store,
		stripeRows : true, // 斑马线
		cm : cm,
		sm:sm,
		tbar:tbar,
		bbar : bbar,
		viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户管理->客户群管理", 
				    height: 100,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[simple]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    height: document.body.scrollHeight-100,
				    margins: '0 0 0 0',
				    items : [cusGroupGrid]
			    }] 

			});
	var updateCustomerBase = function(cusGroupGrid) {
        var _record = cusGroupGrid.getSelectionModel().getSelected();
	 	var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
        if (!_record||checkedNodes.length>1) {
        	Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
        } else {
          var record = cusGroupGrid.getSelectionModel().getSelected();
          updateCustomerGroup.getForm().loadRecord(record);
          updatewin.show();
        }
    };
    var deleteCustomerBase = function(cusGroupGrid) {
        var _record = cusGroupGrid.getSelectionModel().getSelected();
        if (!_record) {
        	Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
        } else {
    	 	var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
    		var json={'id':[]};
    		for(var i=0;i<checkedNodes.length;i++)
    			{
    				json.id.push(checkedNodes[i].data.custgroupId);
    			}
    			Ext.Ajax.request({
    						url:basepath+'/customer-group.json',
                            method: 'POST',
    						success : function(response) {
    							Ext.Msg.alert('提示', '成功');
    							store.load();
    						},
    						failure : function(response) {
    							Ext.Msg.alert('提示', '失败');
    						},
    						params : {
    							cbid:Ext.encode(json),
    							'operate':'delete'
    						}});
        	
        	
        }
    };
	var batchAdd= function(){
		var cbid= Ext.getCmp('cbid').getValue();
	 	var checkedNodes = cusGrid.getSelectionModel().selections.items;
		var json={'id':[]};
		var json2={'custName':[]};
	 	var json3={'orgId':[]};
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.id.push(checkedNodes[i].data.CUST_ID);
				json2.custName.push(checkedNodes[i].data.CUST_NAME);
				json3.orgId.push(checkedNodes[i].data.ORG_ID);
			}
			Ext.Ajax.request({
						url:basepath+'/customer-relate-customer-group.json',
                        method: 'POST',
						success : function(response) {
							Ext.Msg.alert('提示', '加入成功');
							custGroupStore.load(
									 {
											params : {
												cbid: Ext.getCmp('cbid').getValue()
											}
					        		  }
									);
						},
						failure : function(response) {
							Ext.Msg.alert('提示','加入失败' );
						},
						params : {
							'cid':Ext.encode(json),
							'custName': Ext.encode(json2),
							'orgId': Ext.encode(json3),
							'cbid': cbid,
							'operate': 'add'
							
						}});
	
	};
	var batchDelete=function(){
		   var checkedNodes = cusGroupMemeberGrid.getSelectionModel().selections.items;
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			
		    var json={'id':[]};
				for(var i=0;i<checkedNodes.length;i++)
			{	json.id.push(checkedNodes[i].data.GROUP_MEM_ID);
			
			}
			var id =checkedNodes[0].data.GROUP_MEM_ID;
		    Ext.Ajax.request({url: basepath+'/customer-relate-customer-group.json',
			method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '删除成功');
				custGroupStore.load(
						 {
								params : {
									cbid: Ext.getCmp('cbid').getValue()
								}
		        		  }
				);
			},
			failure : function(response) {
				Ext.Msg.alert('提示','删除失败' );
			},
			params : {
				'cbid':Ext.encode(json),
				'operate': 'delete'
			}
		    });
		    
				    
			

	};
	var custview=function(){
	var checkedNodes = cusGridDetail.getSelectionModel().selections.items;
	if(checkedNodes.length==0)
	{
		Ext.Msg.alert('提示', '未选择任何客户');
		return ;
	}
  var custId = cusGridDetail.getSelectionModel().selections.items[0].data.CUST_ID;
  window.location.href = '../customerBaseInformation.jsp?customerId='+custId;
	};
});