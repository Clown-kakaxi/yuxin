  	var pid='';//定义custId传递参数
	var _buttonVisible = false; 
	var _sheetVisible =false;//页签展示项是否可见
	var _layOutSet = true; 
	
	 var custSourceStore = new Ext.data.ArrayStore( {
         fields : [ 'myId', 'displayText' ],
         data : [ //[ '1', '自定义筛选' ],
                  [ '2', '客户群导入' ]]
     });
     
	 //根据目标客户来源不同，下拉框显示不同的客户来源
	 if(_SOURCE_CUST){
		 custSourceStore.data.removeAt(0);
	 }if(_SOURCE_GROUP){
		 custSourceStore.data.removeAt(1);
	 }
//  	var search_cust_edit = new Com.yucheng.bcrm.common.CustomerQueryField({ 
//		fieldLabel : '目标客户', 
//		labelStyle: 'text-align:right;',
//		name : 'custNameStr',
////		id:'groupRootCustName1',
//		custtype :'2',//客户类型：  1：对私, 2:对公,  不设默认全部
//	    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
//	    singleSelected:false,//单选复选标志
//		editable : false,
//		hidden:_SOURCE_CUST,
//		blankText:"请填写",
//		anchor : '90%',
//		hiddenName:'abcd',
//		callback :function(){
//	}
//	});
  	
  	/*
	 * 灵活查询弹出窗口
	 */
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
				items : [{html:' <div style="width:'+document.clientWidth+'px;height:'+document.clientHeight+'px;"><div style="position:absolute; left:0px; top:0px; " id=\'view\'></div></div>'
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
  	
  	
	var search_cust_group = new Com.yucheng.bcrm.common.CustGroup({ 
		fieldLabel : '关联客户群', 
		labelStyle: 'text-align:right;',
		name : 'custGroup',
	    singleSelected:true,//单选复选标志
		editable : false,
		blankText:"请填写",
		hidden:_SOURCE_GROUP,
		anchor : '90%',
		hiddenName:'groupStr'
	});

    var custContrastRecord = Ext.data.Record.create(
    		[
    		 {name:'aimCustId',mapping:'AIM_CUST_ID'},
    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'},
    		 {name:'custId',mapping:'CUST_ID'},
    		 {name:'createDate',mapping:'CREATE_DATE'},
    		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
    		 {name:'custName',mapping:'CUST_NAME'},
    		 {name:'aimCustSource',mapping:'AIM_CUST_SOURCE'},
    		 {name:'AIM_CUST_SOURCE_ORA'},
    		 {name:'progressStep',mapping:'PROGRESS_STEP'},
    		 {name:'PROGRESS_STEP_ORA'},
    		 {name:'mktActiStat',mapping:'MKT_ACTI_STAT'},
    		 {name:'mgrName',mapping:'MGR_NAME'},
    		 {name:'institutionName',mapping:'INSTITUTION_NAME'}
    		 ]
    );
    var custContrastReader = new Ext.data.JsonReader(//读取jsonReader
    		{
    			successProperty : 'success',
    			idProperty : 'ID',
    			totalProperty : 'json.count',
    			root:'json.data'
    		},custContrastRecord
	);
	var custContrastStore = new Ext.data.Store({//产品对照关系store
	        restful : true, 
	        proxy : new Ext.data.HttpProxy({ 
	        	url:basepath+'/mktactivityrelateinfoaction.json',
	        	method:'get'
	        }),
			reader:custContrastReader
			
	});
	
//	// 每页显示条数下拉选择框
//	var pagesize_combo = new Ext.form.ComboBox({
//		name : 'pagesize',
//		triggerAction : 'all',
//		mode : 'local',
//		store : new Ext.data.ArrayStore({
//			fields : [ 'value', 'text' ],
//			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
//			         [ 50, '50条/页' ],[ 100, '100条/页' ]  ]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		value : '100',
//		resizable : true,
//		width : 85
//	});
////	custContrastStore.on('beforeload', function() {
////		this.baseParams = {
////				custId:pid,
////				querysign:'customer'
////		};
////	});
//	custContrastStore.reload({
//		params : {
//			start : 0,
//			limit : parseInt(pagesize_combo.getValue())
//		}
//	});
//	// 改变每页显示条数reload数据
//	pagesize_combo.on("select", function(comboBox) {
//		bbar.pageSize = parseInt(pagesize_combo.getValue()),
//		custContrastStore.reload({
//			params : {
//				start : 0,
//				limit : parseInt(pagesize_combo.getValue())
//			}
//		});
//	});
//
//	var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
//			pageSize : parseInt(pagesize_combo.getValue()),
//			store : custContrastStore,
//			displayInfo : true,
//			displayMsg : '显示{0}条到{1}条,共{2}条',
//			emptyMsg : "没有符合条件的记录",
//			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
//	});

	 //*********************************
	// 每页显示条数下拉选择框
	var cust_pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});
	var cust_bbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(cust_pagesize_combo.getValue()),
		store : custContrastStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', cust_pagesize_combo]
	});
	
	cust_pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  cust_bbar.pageSize = parseInt(cust_pagesize_combo.getValue()),
		  custContrastStore.reload({
			  params : {
			  start : 0,
			  limit : parseInt(cust_pagesize_combo.getValue())
		  }
		  });
	  });
 //********************************
	
 	var cust_sm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	var cust_rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	    });
	 var custContrastColumns = new Ext.grid.ColumnModel(
				{
					columns:[ cust_rownum,cust_sm,
					{ header:'aimProdId',dataIndex:'aimProdId',sortable:true,hidden:true},
					{ header:'客户编号',dataIndex:'custId',sortable:true,hidden:true},
					{ header:'客户名称',dataIndex:'custName',sortable:true,width:150},
					{ header:'主办客户经理',dataIndex:'mgrName',sortable:true},
					{ header:'主办机构',dataIndex:'institutionName',sortable:true,width:150},
					{ header:'目标客户来源',dataIndex:'AIM_CUST_SOURCE_ORA',sortable:true,width:150},
					{ header:'进展阶段',dataIndex:'PROGRESS_STEP_ORA',sortable:true,width:150},
					{ header:'创建人',dataIndex:'createUserName',sortable:true,width:160},
					{ header:'创建日期',dataIndex:'createDate',width:160,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/
	 var custContrastGrid = new Ext.grid.EditorGridPanel({			
			store:custContrastStore, 
			frame:true,
			height : 200,
//			width : 200,
			cm:custContrastColumns,
			region:'center',
			sm:cust_sm,
			tbar:[
			      { text:'新增',
			    	  id:'__aimCustAdd',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	addcustContrastWind.show();
			    	  	addcustContrastWind.setTitle('关联客户维护');
			    	  	custContrastForm.getForm().getEl().dom.reset();
			    	  	custContrastStore.reload();
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = custContrastGrid.getSelectionModel().getSelections().length;
						 var selectRe;
						 var tempId;
						 var idStr = '';
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
							for(var i = 0; i<selectLength;i++)
							{
								selectRe = custContrastGrid.getSelectionModel().getSelections()[i];
								tempId = selectRe.data.aimCustId;
								idStr += tempId;
								if( i != selectLength-1)
									idStr += ',';
							}
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return;
									} 
									Ext.Ajax.request({
												url : basepath
												+ '/addmarketprodaction!batchDestroy.json',
												params : {
													'idStr' : idStr,
													'mktActiId' : editBasePlanForm.form.findField('mktActiId').getValue(),
													'delSgin':'cust'
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													custContrastStore.reload();
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
													if(resultArray == 403) {
												           Ext.Msg.alert('提示', response.responseText);
												  } else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
													custContrastStore.reload();
												  }}
											});
								});
						}}
			      }],
			      bbar:cust_bbar,
			      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	 			  },
	 			  loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			  }
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
	    		 items : [ {
	                    fieldLabel : '客户来源',
	                    name : 'custSource',
	                    forceSelection : true,
	                    resizable : true,
	                    xtype : 'combo',
	                    labelStyle : 'text-align:right;',
	                    triggerAction : 'all',
	                    mode : 'local',
	                    store : custSourceStore,
	                    valueField : 'myId',
	                    displayField : 'displayText',
	                    emptyText : '请选择',
	                    anchor : '95%',
	                    listeners:{
	    			 select :function(){
	    			 if(custContrastForm.form.findField('custSource').getValue()=='1'){
	    				 
	  					   addRoleWindow.show();
	  							setTimeout(function(){
	  								Ext.ScriptLoader.loadScript({        
	  									scripts: [basepath+'/contents/pages/mktManage/mktActivityManage/agileQuery.js'],        
	  									callback: function() {  
	  								}
	  								});
	  							},800);
	  					 custContrastForm.form.findField('custNameStr').setVisible(true);	
		    			 custContrastForm.form.findField('custGroup').setVisible(false);
		    			 custContrastForm.form.findField('custGroup').reset();
		    			 search_cust_group.custStr='';
		    			 search_cust_group.custStr1='';
	    			 }if(custContrastForm.form.findField('custSource').getValue()=='2'){
	    				 custContrastForm.form.findField('custNameStr').setVisible(false);
		    			 custContrastForm.form.findField('custGroup').setVisible(true);
		    			 custContrastForm.form.findField('custNameStr').reset();
		    			 custContrastForm.form.findField('abcd').reset();
	    			 }
	    			
	    		 }
	    		 }
	                } ]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
//	    		 hidden:_SOURCE_GROUP,
	    		 items : [/*search_cust_edit*/{
						xtype : 'textarea',
						name : 'custNameStr',
						fieldLabel : '客户名称',
						hidden:true,
						anchor : '90%'
					},{
						xtype : 'textfield',
						name : 'abcd',
						hidden:true,
						fieldLabel : '客户编号1',
						anchor : '90%'
					},search_cust_group]
	    	 }]
		 }]
	 });
	var addcustContrastWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'600',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[custContrastForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 if (!custContrastForm.getForm().isValid()) {
		        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		        		 return false;
		        	 }
		        	 var countNum = 0;
		        	 var countName = '';
		        	 var selectStr = custContrastStore.data.items;//当前关联客户数据
		        	 var selectCustId = '';
		        	 var selectCustName = '';
		        	 if(custContrastForm.form.findField('abcd').getValue().length>0){
		        	 selectCustId = custContrastForm.form.findField('abcd').getValue().split(',');//所筛选的关联产品ID 
	        		 selectCustName = custContrastForm.form.findField('custNameStr').getValue().split(',');//所筛选的关联产品名称
		        	 }
		        	 var selectCustId1 = '';
		        	 var selectCustName1 = '';
		        	 if(!_SOURCE_GROUP){
		        		 if(search_cust_group.custStr!=undefined)
		        		 if(search_cust_group.custStr.length>0){
			        		  selectCustId1 = search_cust_group.custStr.split(','); 
				        	  selectCustName1 = search_cust_group.custStr1.split(',');
		        		 }
		        	 }
		        	 for(var i = 0;i<custContrastStore.data.length;i++)
		        	 {
		        		 //判断客户放大镜
		        		 for(var j = 0;j<selectCustId.length;j++){
		        			 if(selectCustId[j]==selectStr[i].data.custId){
		        				 countNum++;
		        				 countName=countName+',<br>'+selectCustId[j]+':'+selectCustName[j];
		        			 }
		        		 }
		        		 //判断客户群
		        		 for(var j = 0;j<selectCustId1.length;j++){
		        			 if(selectCustId1[j]==selectStr[i].data.custId){
		        				 countNum++;
		        				 countName=countName+',<br>'+selectCustId[j]+':'+selectCustName[j];
		        			 }
		        		 }
		        	 }
		        	 if(countNum>0){
		        		 Ext.MessageBox.confirm('提示','您所选客户中有'+countNum+'位已经建立关联关系，分别为：'+countName+'。 <br>保存结果将不包含该部分客户，<br>确定执行此操作吗?',
		        				 function(buttonId){
		     				if(buttonId.toLowerCase() == "no"){
		     					 return false;
		     					}
		     			Ext.Msg.wait('正在保存，请稍后......','系统提示');
		        	 Ext.Ajax.request({
							url : basepath + '/addmarketprodaction!saveData.json?sign=customer',
							params : {
							'custIdStr':custContrastForm.form.findField('abcd').getValue(),
							'custNameStr':custContrastForm.form.findField('custNameStr').getValue(),
							'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
							'custStr':search_cust_group.custStr,
							'custStr1':search_cust_group.custStr1
							},
							method : 'POST',
							form : custContrastForm.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功!');
								custContrastForm.form.reset();
								custContrastStore.reload( {
                                     params : {
                                         start : 0,
                                         limit : cust_bbar.pageSize
                                     }
                                 });
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.status);
							       if(resultArray == 403) {
							           Ext.Msg.alert('提示', response.responseText);
							  } else{

								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							}
//								store.reload();
							}
						});
		        		 });}else if(countNum==0){
		        		 	Ext.Msg.wait('正在保存，请稍后......','系统提示');
		        			 Ext.Ajax.request({
									url : basepath + '/addmarketprodaction!saveData.json?sign=customer',
									params : {
									'custIdStr':custContrastForm.form.findField('abcd').getValue(),
									'custNameStr':custContrastForm.form.findField('custNameStr').getValue(),
									'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue(),
									'custStr':search_cust_group.custStr,
									'custStr1':search_cust_group.custStr1
									},
									method : 'POST',
									form : custContrastForm.getForm().id,
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function() {
										Ext.Msg.alert('提示', '操作成功!');
										custContrastStore.reload( {
		                                     params : {
		                                         start : 0,
		                                         limit : cust_bbar.pageSize
		                                     }
		                                 });
									},
									failure : function(response) {
										var resultArray = Ext.util.JSON.decode(response.status);
									       if(resultArray == 403) {
									           Ext.Msg.alert('提示', response.responseText);
									  } else{

										Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
//										store.reload();
									}
								});
		        		 }
		        	 addcustContrastWind.hide();
		         }
		         }, {
		        	 text:'重置',
		        	 handler:function(){
		        	 	custContrastForm.getForm().reset();
		        	 	search_cust_group.custStr='';
		         	}
		         }]
	});
