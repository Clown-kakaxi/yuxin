Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var mgr_namess = '';
	var cusId = '';
	var cusName = '';
	var activeIndex = false;
	var accounts = '';
	//吸存人列表
	var isAssignStore = new Ext.data.ArrayStore({
	    fields:['myId','displayText'],
	    data:[['未分配','未分配'],['已分配','已分配']]
	});

	var searchCustmgr = new Ext.ux.form.CustMgrField({ 
		fieldLabel : '吸存人', 
		labelStyle: 'text-align:right;',
		labelWidth : 100,
		name : 'MGR_NAME',
		id:'add_aimMgrName',
		editable : true,
		singleSelected:true,
		anchor : '90%'
	});
	//吸存人列表
	var accountAssignSearchPanel = new Ext.form.FormPanel( {// 查询panel
		title : '客户帐号分配查询',
		height : 120,
		width:600,				
		labelWidth : 80,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		layout : 'column',
			items : [ {
				columnWidth : .50,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					name : 'ACCT_NAME',
					fieldLabel : '客户名称',
					anchor : '100%'
				} ]
			}, {
				columnWidth : .50,
				layout : 'form',
				items : [ 
					
						{
							xtype : 'combo',
							width : 200,
							fieldLabel : '分配状态',
							name : 'IS_ASSIGN',
							anchor : '90%',
							editable : false,
							//emptyText:'请选择',
							mode : 'local',
							triggerAction:'all',
							store:isAssignStore,
							valueField:'myId',
							displayField:'displayText',
							anchor:'95%'
						}
				]
		} ],
		buttonAlign : 'center',
		buttons : [
				{
					text : '查询',
					handler : function() {
			
						var parameters = accountAssignSearchPanel
								.getForm().getValues(false);

						accountAssignStore.removeAll();
						accountAssignStore.baseParams = {
							'condition' : Ext.util.JSON
									.encode(parameters)
						};
						accountAssignStore.load( {
							params : {
								start : 0,
								limit : parseInt(spagesize_combo
										.getValue())
							}
						});
					}
				},
				{
					text : '重置',
					handler : function() {
					accountAssignSearchPanel.getForm().reset();
					}
				} ]
	});
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var grid = new Ext.grid.ColumnModel([//gridtable中的列定义
		                                   rownum,
		                                               	{header :'id',dataIndex:'id',id:"id",width:100,sortable : true,align:'left',hidden:true},
		                                               	{header :'客户编号',dataIndex:'custId',width:125,sortable : true,align:'left',hidden:true},
		                                               	{header : '客户名称',dataIndex:'custName',width:220,align:'left',sortable : true},
		                                                {header :'账户',dataIndex:'account',width:220,align:'right',sortable : true},
		                                            	{header : '分配状态',dataIndex:'isAssign',width:218,sortable:true}
/*		                                               	{header :'吸存号',dataIndex:'mgrCode',width:105,sortable : true,hidden : true},
		                                             /* {header :'吸存人编号 ',dataIndex:'mgrId',width:120,sortable : true,hidden:true},
		                                              	{header :'吸存人',dataIndex:'mgrName',width:110,sortable:true},
		                                               	{header :'吸存人机构',dataIndex:'orgCode',width:110,sortable:true},
		                                               	{header :'分润比例(%)',dataIndex:'contributeRate',width:110,sortable:true},
		                                            	{header :'分配人 ',dataIndex:'assignUser',width:120,sortable : true,hidden:true},
		                                               	{header :'分配日期 ',dataIndex:'assignDate',width:120,sortable : true,hidden:true}*/
		                                               	]);
	var accountAssignRecord = new Ext.data.Record.create([	
	  	                                               	{name:'id',mapping:'ID'},
	  	                                               	{name:'account',mapping:'ACCOUNT'},
	  	                                               	{name:'custName',mapping:'ACCT_NAME'},
	  	                                               	{name:'custId',mapping:'CUST_ID'},
	  	                                               	{name:'isAssign',mapping:'IS_ASSIGN'}

	  	                                            /*   	{name:'custId',mapping:'CUST_ID'},
	  	                                               	{name:'custName',mapping:'CUST_NAME'},
	  	                                               	{name:'mgrId',mapping:'MGR_ID'},
	  	                                               	{name:'mgrCode',mapping:'MGR_CODE'},
	  	                                            	{name:'mgrName',mapping:'MGR_NAME'},
	  	                                            	{name:'orgCode',mapping:'ORG_CODE'},
	  	                                            	{name:'contributeRate',mapping:'CONTRIBUTE_RATE'},
	  	                                            	{name:'assignUser',mapping:'ASSIGN_USER'},
	  	                                            	{name:'assignDate',mapping:'ASSIGN_DATE'}*/
	  	                                               	]);
	
 	var accountAssignReader = new Ext.data.JsonReader({//读取json数据的panel
       	totalProperty:'json.count',
       	root:'json.data'
       	},accountAssignRecord);
 	var accountAssignStore = new Ext.data.Store(
           	{
           		proxy:new Ext.data.HttpProxy({
           		url:basepath+'/assignInfo-Action.json',
//           		},
           		failure : function(response){
           			var resultArray = Ext.util.JSON.decode(response.status);
           			if(resultArray == 403) {
           				Ext.Msg.alert('提示', response.responseText);
           			}
           		},
           		method:'GET'
           		}),
           		reader:accountAssignReader
           	}
           	);
       	
		// 每页显示条数下拉选择框
	var spagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});

	// 改变每页显示条数reload数据
	spagesize_combo.on("select", function(comboBox) {
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		accountAssignStore.reload({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
	});
	// 分页工具栏
	var sbbar = new Ext.PagingToolbar({
		pageSize : parseInt(spagesize_combo.getValue()),
		store : accountAssignStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	accountAssignStore.load({params:{		
		start:0,
		limit: parseInt(spagesize_combo.getValue())
	}});
    var numberField = new Ext.form.NumberField({allowBlank : false,minValue:0.01,maxValue:100.00});  
    var textField = new Ext.form.TextField({allowBlank : false,minValue:0}); 
  var record = assignInfoStore;
  debugger;
  var search =  new Ext.ux.form.CustMgrField({ 
		fieldLabel : '吸存人', 
		labelStyle: 'text-align:right;',
		labelWidth : 100,
		//name : 'MGR_NAME',
		id:'add_aimMgrNames',
		editable : false,
		singleSelected:true,
		anchor : '90%',
		callback :function(a,b,c,d){
		var mgr_namess = null;
		//var mgr_id = null;
		records1 = assignInfoGrid.getSelectionModel().selection;
				var mgrIds1 = '';
				activeIndex;
				mgr_namess = Ext.getCmp('add_aimMgrNames').getValue();
				if (mgr_namess != null && mgr_namess != '') {
					mgrIds1 = Ext.getCmp('add_aimMgrNames').userId.aId[0];
					mgrCodes = Ext.getCmp('add_aimMgrNames').userCode.userCode[0];
					var t = assignInfoStore.data.length;
					if(t!=1){
					for(i=0;i<t-1;i++){
						if(assignInfoStore.getAt(i).data.id != assignInfoStore.getAt(activeIndex).data.id ){
				         if(mgr_namess == assignInfoStore.getAt(i).data.mgrName ){
		           				Ext.Msg.alert('提示', '吸存人选择重复，请重新选择！');
                                return;
				         }}
						}
					}
					
					            assignInfoStore.getAt(activeIndex).data.mgrName = mgr_namess;
					            assignInfoStore.getAt(activeIndex).data.mgrId = mgrIds1;
					            assignInfoStore.getAt(activeIndex).data.mgrCode = mgrCodes;
					            assignInfoGrid.getView().refresh(false);
				}
	}

	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	 var rownum1 = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
			});                           		
     var grids = new Ext.grid.ColumnModel([//gridtable中的列定义
                                          sm, rownum1,
                                       	{header :'id',dataIndex:'id',id:"ids",width:100,sortable : true,hidden:true},
                                       	{header :'客户编号',dataIndex:'custId',width:125,sortable : true,align:'left',hidden:true},
                                       	{header : '客户名',dataIndex:'custName',width:125,sortable : true,hidden:true,editor:textField},
                                       	{header :'账户',dataIndex:'account',width:120,sortable : true,align:'left',hidden:true},
                                       	{header :'吸存号',dataIndex:'mgrCode',width:80,align:'left',sortable : true},
                                       	{header :'吸存人编号 ',dataIndex:'mgrId',width:80,sortable : true,align:'left', hidden:true},
                                      	{header :'吸存人',id:'mgrNamess',dataIndex:'mgrName',width:100,align:'left',sortable:true,editor:search},
                                       	{header :'吸存人机构',dataIndex:'orgCode',width:130,align:'left',sortable:true,hidden:true},
                                       	{header :'分润比例(%)',dataIndex:'contributeRate',width:80,sortable:true,align:'left',editor:numberField},
                                       	{header :'分配人 ',dataIndex:'userName',width:100,align:'left',sortable : true},
                                       	{header :'分配日期 ',dataIndex:'assignDate',width:100,align:'left',sortable : true}
                                       	]);
 	var assignInfoRecord = new Ext.data.Record.create([	
 		  	                                               	{name:'id',mapping:'ID'},
 		  	                                               	{name:'custId',mapping:'CUS_ID'},
 		  	                                               	{name:'custName',mapping:'CUST_NAME'},
 		  	                                               	{name:'account',mapping:'ACCOUNT'},
 		  	                                               	{name:'mgrId',mapping:'MGR_ID'},
 		  	                                               	{name:'mgrCode',mapping:'MGR_CODE'},
 		  	                                            	{name:'mgrName',mapping:'MGR_NAME'},
 		  	                                            	{name:'orgCode',mapping:'ORG_CODE'},
 		  	                                            	{name:'contributeRate',mapping:'CONTRIBUTE_RATE'},
 		  	                                            	{name:'assignUser',mapping:'ASSIGN_USER'},
 		  	                                            	{name:'assignDate',mapping:'ASSIGN_DATE'},
 		  	                                            	{name:'userName',mapping:'USER_NAMES'}

 		  	                                               	]);
   	
 	var assignInfoReader = new Ext.data.JsonReader({//读取json数据的panel
       	totalProperty:'json.count',
       	root:'json.data'
       	},assignInfoRecord);
 	var assignInfoStore = new Ext.data.Store(
           	{
           		proxy:new Ext.data.HttpProxy({
           		url:basepath+'/assignQuery-Action.json',
//           		},
           		failure : function(response){
           			var resultArray = Ext.util.JSON.decode(response.status);
           			if(resultArray == 403) {
           				Ext.Msg.alert('提示', response.responseText);
           			}
           		},
           		method:'GET'
           		}),
           		reader:assignInfoReader
           	}
           	);
 
 	    
		var assignInfoGrid =  new Ext.grid.EditorGridPanel({//产品列表数据grid
			frame:true,
			region:"center",
			id:'assignInfoGrid',
			height : 300,
//			width : document.body.clientWidth-602,
			store:assignInfoStore,
			loadMask:true,
			cm :grids,
			sm:sm,
	      	tbar:[ 
	            	{
	            		text:'添加',
	            		iconCls : 'addIconCss',
	            		disabled:true,
	        			id :'Add',
	            		handler:function()
	            		{      			
	            		assignInfoGrid.stopEditing(); 
	            		var data={json: { data : [{account:'',mgrId:'',mgrCode:'',contributeRate:'',ASSIGN_USER:__userName,ASSIGN_DATE:new Date()}]}};
	            		assignInfoStore.loadData(data,true);
	            		assignInfoGrid.startEditing(0, 0);  //激活该行的编辑状态  
	            		}
	            	},'-',{
	            		text:'删除',
	            		iconCls : 'deleteIconCss',
	            		id : 'delete',
	            		disabled:true,
	            		handler:function()
	            	{      			
	        			var checkedNode = assignInfoGrid.getSelectionModel().selections.items;

	                           
	                   			var checkedNode = assignInfoGrid.getSelectionModel().selections.items;
	                   			if (checkedNode.length == 0) {
	                				Ext.Msg.alert('提示', '请选择要删除的记录！');
	                				return;
	                			}else{
	                				  Ext.Msg.confirm('信息', '确定要删除', function(btn) {  
	                					  if(btn == 'yes') { 
	                							var selectRe;
												var tempId;
												var idStr = '';
												for ( var i = 0; i < checkedNode.length; i++) {
													tempId = checkedNode[i].data.id;
													if(tempId == ""){
														assignInfoStore.remove(checkedNode);
														Ext.Msg.alert('系统提示信息', '操作成功');
                                                       return;
													}else{
													idStr += tempId;
													
													if (i != checkedNode.length - 1)
														idStr += ',';
												}}
												Ext.Ajax
												.request( {
													url : basepath
															+ '/assignAccount/'
															+ tempId
															+ '.json?idStr='
															+ idStr,
													method : 'DELETE',
													success : checkResult,
													failure : checkResult

												});
												function checkResult(response) {
													var resultArray = Ext.util.JSON
															.decode(response.status);
													var resultError = response.responseText;
													  
													if ((resultArray == 200 || resultArray == 201)
															&& resultError == '') {
														Ext.Msg.alert('系统提示信息', '操作成功');
														
														assignInfoStore.reload();
														accountAssignStore
																.load( {
																	params : {
																		start : 0,
																		limit : parseInt(spagesize_combo
																				.getValue())
																	}
																});
													} else {
														if (resultArray == 403) {
															Ext.Msg.alert('系统提示信息',
																	response.responseText);
														} else {
															Ext.Msg
																	.alert(
																			'系统提示信息',
																			'操作失败,失败原因:' + resultError);
														}}}
                                                   }
	                     });
	                   }}}
	            	],
			buttonAlign:'center',
    		buttons:[{text: '保存',
    			    id : 'save',
    			    disabled:true,
    			handler: function(){
    			
    			 var t1=0;
    			for(var i=0;i<assignInfoStore.getCount();i++){
					var t =assignInfoStore.getAt(i);
					t1 = t1+parseInt(t.data.contributeRate);
				}
    			if(t1!=100){
					Ext.Msg.alert('提示','分配比例总和不为 1，请检查！');
					return ;
				}
    			var checkedNodes = assignInfoStore.data.items;
    			
		var xModel = [];
		debugger;
		
		
			for ( var i = 0; i < assignInfoStore.data.length; i++) {

				var oneModel = {};
				oneModel.id =checkedNodes[i].data.id ? checkedNodes[i].data.id : '';
				oneModel.mgrId = checkedNodes[i].data.mgrId;
				oneModel.mgrCode = checkedNodes[i].data.mgrCode;
				oneModel.custId = cusId;
				oneModel.custName = cusName;
				oneModel.account = accounts;
				oneModel.assignUser = __userId;
				oneModel.assignDate = new Date();
				oneModel.contributeRate = checkedNodes[i].data.contributeRate;
                oneModel.orgCode = JsContext._orgId;

				if(""==checkedNodes[i].data.mgrName){
					Ext.Msg.alert("系统提示","吸存人不能为空!");
					return false;
				};
				oneModel.mgrName = checkedNodes[i].data.mgrName;
				oneModel.contributeRate = checkedNodes[i].data.contributeRate;
				xModel.push(oneModel);
		}
			
		Ext.Ajax.request( {
			url : basepath + '/assignAccount.json',
			method : 'POST',
			params : {
				'models' : Ext.encode(xModel)
			},
			success : checkResult,
			failure : checkResult
		}); 
		function checkResult(response) {
			var resultArray = Ext.util.JSON
					.decode(response.status);
			var resultError = response.responseText;
			  
			if ((resultArray == 200 || resultArray == 201)
					&& resultError == '') {
				Ext.Msg.alert('系统提示信息', '操作成功');
				
				assignInfoStore.removeAll();
				accountAssignStore
						.load( {
							params : {
								start : 0,
								limit : parseInt(spagesize_combo
										.getValue())
							}
						});
			} else {
				if (resultArray == 403) {
					Ext.Msg.alert('系统提示信息',
							response.responseText);
				} else {
					Ext.Msg
							.alert(
									'系统提示信息',
									'操作失败,失败原因:' + resultError);
				}
			}
		}
    		}
    	
    			},'-',{text: '取消',
    				id:'cancel',
    				disabled:true,
        			handler: function(){
    		        Ext.getCmp('Add').setDisabled(true);
    	            Ext.getCmp('delete').setDisabled(true);
    	            Ext.getCmp('save').setDisabled(true);
    	            Ext.getCmp('cancel').setDisabled(true);
    	            assignInfoStore.reload();
    			}
        			}],
		
	        loadMask : {
	            msg : '正在加载表格数据,请稍等...'
	        }
		});	

		assignInfoGrid.on('celldblclick',function(a,b,c,d){
			activeIndex = b;
		});
 	
	var accountAssignGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		frame:true,
		region:"center",
		id:'accountAssign',
		width:600,
		height : 300,

		store:accountAssignStore,
		loadMask:true,
      	tbar:[ 
      	{
      		text:'调整',
      		iconCls : 'editIconCss',
      		handler:function()
      		{      			
    		var selectLength = accountAssignGrid.getSelectionModel().getSelections().length; 
    		if(selectLength < 1){
  				Ext.MessageBox.alert('提示','请选择要修改的记录');
  				return;
  			}else
  			{
  				records = accountAssignGrid.getSelectionModel().getSelected();
  	      		cusId = records.data.custId;
  	      	    cusName = records.data.custName;
  	      	    accounts = records.data.account;
	            Ext.getCmp('Add').setDisabled(false);
	            Ext.getCmp('delete').setDisabled(false);
	            Ext.getCmp('save').setDisabled(false);
	            Ext.getCmp('cancel').setDisabled(false);
  			}
      		
      		}
      	},'-',	new Ext.Button({
			text:'批量导入',
			iconCls:'importIconCss',
			handler :function(){
		importForm.tradecode='importantCustSelected';
			importWindow.show();
			}
		}
		)
      	],
		cm :grid,
    	bbar:sbbar,
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }, 
    	listeners:{
			'rowclick':function(a,b,c){	
			record = accountAssignGrid.store.getAt(b);
			debugger;
			ma = record.data.account;
			assignInfoStore.load( {
             	params : {
						'account' : ma
					}
				});
	        Ext.getCmp('Add').setDisabled(true);
            Ext.getCmp('delete').setDisabled(true);
            Ext.getCmp('save').setDisabled(true);
            Ext.getCmp('cancel').setDisabled(true);
			

			}
		}

	});	
	
	var addAccountPanel = new Ext.form.FormPanel( {// 查询panel

		title : '调整吸存信息',
		height : 80,
	    width : document.body.clientWidth-602,				
		labelWidth : 80,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		layout : 'column',
		items : [ {
			columnWidth : .50,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				name : 'mrgNames',
				id : 'mrgNamess',
				fieldLabel : '当前分配人',
				editable : false,
				value :__userName,
				anchor : '90%'
			}]
		},{
			columnWidth : .50,
			layout : 'form',
			items : [ {
				name : 'date',
				id : 'date',
				anchor : '100%',
				xtype : 'datefield',
				editable : false,
				format : 'Y-m-d',
				value : new Date(),
				readOnly:true,
				fieldLabel : '当前日期',
				anchor : '90%'
			}]
		}]
	});

	
	var view = new Ext.Viewport({//页面展示
		layout:'fit',
		frame : true,
		items:[{
			layout:'border',
			frame:true,
			items:[
			       {
			    	   region : 'west',
			    	   layout : 'border',
			    	   width :'600',
			    	   items : [accountAssignSearchPanel, accountAssignGrid ]
			    	            
			       },{
			    	   region : 'center',
			    	   layout : 'border',
			    	   items : [addAccountPanel, assignInfoGrid ]
			       }
			       ]
		}]
			}
	    );
});