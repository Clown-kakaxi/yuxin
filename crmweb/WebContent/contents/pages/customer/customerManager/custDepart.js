Ext.onReady(function() {
	Ext.QuickTips.init();
	
		// 审批状态下拉框的数据查询
	var appStatusStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=APPROVEL_STATUS'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	appStatusStore.load();
	//证件类型
	var certTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	certTypStore.load();
	//客户类型
	var custTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custTypStore.load();
	//客户状态
	var custStatStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=ABC0100020'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custStatStore.load();
	//客户级别
	var custLevStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CDE0100016'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custLevStore.load();
	
	//合并字段嵌入form
	var mergeAppPanel = new Ext.FormPanel( {
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth:100,
				items : [ {id : 'createDate',name : 'createDate',allowBlank:false,xtype : 'datefield',format:'Y-m-d',value:new Date(),editable:false,fieldLabel : '申请日期',labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:100,
				items : [ {id : 'applyReason',name : 'applyReason',allowBlank:false,xtype : 'textarea',fieldLabel : '拆分原因',labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	//行选择
	var sm = new Ext.grid.CheckboxSelectionModel({
//		singleSelect : true
	});
	var rownum = new Ext.grid.RowNumberer({ header : 'No.',width : 28 });  	//行号
	//已选择 合并的客户信息展现,待合并列模型
	var mergeCm = new Ext.grid.ColumnModel([rownum,
	       {
			 	header : '客户编号',
			 	dataIndex : 'source_custId',
			 	sortable : true,
			 	width : 100
	       },{
			 	header : '客户名称',
			 	dataIndex : 'source_custZhName',
			 	width : 120
	       },{
			 	header : '所属机构',
			 	dataIndex : 'source_orgId',
			 	width : 120,
			 	hidden:true
	       },{
			 	header : '主协办类型',
			 	dataIndex : 'source_mainType',
			 	width : 120,
			 	hidden:true
	       },{
	    	    header : '是否主账户',
	    	    dataIndex : 'ifMain',
	    	    width : 120,
	    	    renderer:function(a,b,c){
	    	   if(c.json.TARGET_CUST_ID == c.json.SOURCE_CUST_ID){
	    		   return '是';
	    	   }else{
	    		   return '否';
	    	   }
	       }
	       }
	]);
	//记录
	var mergeRecord = Ext.data.Record.create([
        {name: 'source_custId',mapping:'SOURCE_CUST_ID'},
        {name: 'source_custZhName',mapping:'SOURCE_CUST_ZH_NAME'},
        {name: 'target_custId',mapping:'TARGET_CUST_ID'},
        {name: 'target_custZhName',mapping:'TARGET_CUST_ZH_NAME'},
        {name: 'target_orgId',mapping:'TAR_ORG_ID'},
        {name: 'target_mainType',mapping:'TAR_MAIN_TYPE'},
        {name: 'source_orgId',mapping:'SOURCE_ORG_ID'},
        {name: 'source_mainType',mapping:'SOURCE_MAIN_TYPE'},
        {name: 'ifMain',mapping:'IF_MAIN'}
    ]);
     var mergeStore = new Ext.data.Store({
    	 reader: new Ext.data.JsonReader({
        	 root : 'mergeData'
         },mergeRecord )
     });
	
	//已选择合并的客户,待合并客户信息展现
	var mergeGrid = new Ext.grid.EditorGridPanel({
		id : 'mergeGrid',
		title : '可拆分的客户列表',
		height : 250,
		autoScroll : true,
		store : mergeStore, 			// 数据存储
		stripeRows : true, 				// 斑马线
		cm : mergeCm, 				    // 列模型
//		sm : sm,						//行选择
		loadMask : { msg : '正在加载表格数据,请稍等...' },
    	listeners:{
    		'click' : function(){
//				Ext.each(mergeStore.data.getRange(),function(e){
//					if(e.id != mergeGrid.getSelectionModel().getSelected().id+'_check'){
//						document.getElementById(e.id+'_check').checked = false;
//					}
//				});
//				document.getElementById(mergeGrid.getSelectionModel().getSelected().id+'_check').checked = true;
    		}
    	}
	});
	
	//客户合并 模态窗口
	var custMergeWin = new Ext.Window({
		plain : true,
		//layout : 'fit',
		resizable : true,
		draggable : true,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 600,
		height : 400,
		buttonAlign : "center",
		title : '拆分客户申请',
		items : [mergeAppPanel,mergeGrid],
		buttons : [{
	    	text:'拆分申请',
	    	handler:function(){

			debugger;
			var flag=0;
//			var selection = mergeGrid.selModel.getSelections();
			var gridData2 = hhbListPanel.grid.selModel.getSelected().data;
			var data=[];
			var tempR= mergeGrid.store.data.items;
			for(var i = 0 ;i<tempR.length;i++){
				var temp={};
				temp.targetCustId=tempR[i].data.target_custId;
				temp.sourceCustId=tempR[i].data.source_custId;
				temp.targetCustName=tempR[i].data.target_custZhName;
				temp.sourceCustName=tempR[i].data.source_custZhName;
				temp.targetOrgId=tempR[i].data.target_orgId;
				temp.sourceOrgId=tempR[i].data.source_orgId;
				temp.targetMainType=tempR[i].data.target_mainType;
				temp.sourceMainType=tempR[i].data.source_mainType;
				data.push(temp);
			}
			dpt(data,'all');
//			if(flag==0){
//				Ext.Msg.confirm('提示：','确认将选中客户拆分吗？',function(btn){
//					if(btn == 'yes')
//					{
//						dpt(data,'single');
//						}
//					});
//				}
//			else{
//				Ext.Msg.confirm('提示：','确认将选中客户拆分吗？',function(btn){
//					if(btn == 'yes')
//					{
//						dpt(data,'all');
//						}
//					});		
//					}
		
				
			
			}
			}
		,{
	    	text:'取消',handler:function(){custMergeWin.hide();}
	    }]
	    
	});
	
	// 合并客户信息的展现panel
	var hhbListPanel = new Mis.Ext.CrudPanel( {
		id : "hhbListPanel",
		title : "客户拆分->待拆分客户查询",
		closable:false,	//在选项卡上，不显示关闭按钮
		stUrl : basepath + '/CustomerCfQueryAction.json',
		//applyUrl : basepath + '/lat_apply_info!apply.json',
		primary : "custId",
		dbclick : false,	
		checkbox : true,
//		height : document.body.clientHeight-30,
		// 定义查询条件Form的高度
//			seFormHeight : 100,
		// 定义增删详情页面弹出窗口高度
		winHeight : 250,
		//宽度
		winWidth : 600,
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		buts : [{
			id : 'mergeInfo',
			xtype : 'button',
			tooltip : '拆分申请',
			text : '拆分申请',
			iconCls:'completeIconCss',
			listeners : {
				click : function(n) {
							if (hhbListPanel.grid.selModel.hasSelection() && hhbListPanel.grid.selModel.getSelections().length==1) {
								
								debugger;
								var record = hhbListPanel.grid.getSelectionModel().getSelected();
								mergeGrid.store.removeAll();
							     Ext.Ajax.request({
						                url: basepath + '/CustCFDetailQueryAction.json',
						                method: 'GET',
						                params: {
	                                        'targetCustId': record.get('targetCustId')
	                                    },
						                success: function(response) {
	                                    	debugger;
	                                    	var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
						    	
							    	var json={'mergeData':[]};
									for (var i = 0; i < nodeArra.length; i++) {
										debugger;
										json.mergeData.push(nodeArra[i]);
									}
									mergeGrid.store.removeAll();
									mergeGrid.store.loadData(json);
							    	 debugger;
							     },
						                failure: function(a, b, c) {}
						            });
//								var json={'mergeData':[{"CUST_ID":data.TARGET_CUST_ID,"CUST_NAME":data.CUST_ZH_NAME,"IF_MAIN":"是"},
//								                       {"CUST_ID":data.SOURCE_CUST_ID,"CUST_NAME":data.CUST_ZH_NAME1,"IF_MAIN":"否"}]};
//								mergeStore.loadData(json);
								custMergeWin.show();
							} else if (hhbListPanel.grid.selModel.getSelections().length>1){
								Ext.Msg.alert("提示", "请选择一条申请拆分的客户!");
							}
				}
			}
		}],
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems :{items:[
			util.layout._tr([util.form._td({name : 'CUST_ZH_NAME',columnWidth: .25,xtype : 'textfield',fieldLabel : '客户名称'})],
							[util.form._td({name : 'CERT_TYPE',columnWidth: .25,xtype : 'combo',fieldLabel : '证件类型',store : certTypStore,valueField : 'key',displayField : 'value'})],
							[util.form._td({name : 'CERT_NUM',columnWidth: .25,xtype : 'textfield',fieldLabel : '证件号码',maskRe:''})]
							
							)
		]},
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ {name : 'targetCustId',header : '目标客户编号',mapping:'TARGET_CUST_ID'},
		          {name : 'custStat',header : '客户状态',type : 'mapping',store : custStatStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CUST_STAT_ORA'}, 
		          {name : 'custZhName',header : '客户中文名称',mapping:'CUST_ZH_NAME'}, 
		          {name : 'certType',header : '证件类型',type : 'mapping',store : certTypStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CERT_TYPE_ORA'}, 
		          {name : 'certNum',sortable : true,header : '证件号码',mapping:'CERT_NUM'},
		          {name : 'custTyp',header : '客户类型',type : 'mapping',store : custTypStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CUST_TYP_ORA'},
		          {name : 'custLev',sortable : true,header : '客户级别',type : 'mapping',store : custLevStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CUST_LEV_ORA'} ,
		          {name : 'hhbDt',header : '合并日期',mapping:'HHB_DT'}
		          ]
	});
	
	var dprownum = new Ext.grid.RowNumberer({ header : 'No.',width : 28 });  	//行号
	//已选择 合并的客户信息展现,待合并列模型
	var dpmergeCm = new Ext.grid.ColumnModel([dprownum,
	       {
			 	header : '客户编号',
			 	dataIndex : 'custId',
			 	sortable : true,
			 	width : 100
	       },{
			 	header : '客户名称',
			 	dataIndex : 'custName',
			 	width : 120
	       },{
			 	header : '所属机构',
			 	dataIndex : 'orgId',
			 	sortable : true,
			 	width : 100,
			 	hidden:true
	       },{
			 	header : '主协办类型',
			 	dataIndex : 'mainType',
			 	width : 120,
			 	hidden:true
	       }
	]);
	//记录
	var dpmergeRecord = Ext.data.Record.create([
        {name: 'custId',mapping : 'HB_CUST_ID'},
        {name: 'custName',mapping : 'HB_CUST_NAME'},
        {name: 'orgId',mapping : 'HB_CUST_ID'},
        {name: 'mainType',mapping : 'HB_CUST_NAME'}
    ]);
     var dpmergeStore = new Ext.data.Store({
    	 reader: new Ext.data.JsonReader({
        	 root : 'mergeData'
         },dpmergeRecord )
     });
	
	//已选择合并的客户,待合并客户信息展现
	var dpmergeGrid = new Ext.grid.EditorGridPanel({
		id : 'mergeGrid',
		region : 'center',
		height : 80,
		autoScroll : true,
		store : dpmergeStore, 			// 数据存储
		stripeRows : true, 				// 斑马线
		cm : dpmergeCm, 				    // 列模型
		loadMask : { msg : '正在加载表格数据,请稍等...' }
	});
	
	//拆分审批panel
	var hhbAppListPanel = new Mis.Ext.CrudPanel( {
		id : "hhbAppListPanel",
//		disabled:JsContext.checkGrant('approve'),
		title : "客户拆分->拆分审批",
		closable:false,	//在选项卡上，不显示关闭按钮
		stUrl : basepath + '/CustomerCFApplyQueryAction.json',//
		detailUrl : basepath + '/CustomerCFApplyQueryAction.json',//
		primary : "id",
		// 采用单选框
		singleSelect : true,
//		height : document.body.clientHeight-30,
		// 定义查询条件Form的高度
		seFormHeight : 100,
		// 定义增删详情页面弹出窗口高度
		winHeight : 450,
		//宽度
		winWidth : 600,
		spIdStr : '',
		hbCustId : '',
		//自定义按钮
		buts : [{
			id : 'shenpi',
			xtype : 'button',
			tooltip : '审批',
			iconCls:'shenpiIconCss',
			text : '审批',
			listeners : {
				click : function(n) {
					if (hhbAppListPanel.grid.selModel.hasSelection()) {
						var records = hhbAppListPanel.grid.selModel.getSelections();// 得到被选择的行的数组
						var recordsLen = records.length;// 得到行数组的长度
						if (recordsLen > 1) {
							Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行审批！");
						} else {
							debugger;
							var record = hhbAppListPanel.grid.getSelectionModel().getSelections();
							var record2 = hhbAppListPanel.grid.getSelectionModel().getSelected();
							var id = record2.get(hhbAppListPanel.primary);
							hhbAppListPanel.spIdStr=id;
							hhbAppListPanel.opUrl = hhbAppListPanel.approvelURl;
							
							var approvelStatus = records[0].get('approvelStatus');
							var winButsArray = [];
							//审批状态中2为已审批
							if (approvelStatus == '1') {
								winButsArray.push({text : "通过",handler : hhbAppListPanel.approvel , scope : hhbAppListPanel});
								winButsArray.push({text : "不通过",handler : hhbAppListPanel.approvelBack, scope : hhbAppListPanel});
							}
							winButsArray.push({text : "关闭",handler : hhbAppListPanel.closeWin,scope : hhbAppListPanel});
							hhbAppListPanel.winButs = winButsArray;
							hhbAppListPanel.showWin();
							
							dpmergeGrid.store.removeAll();
							debugger;
						     Ext.Ajax.request({
					                url: basepath + '/CustCFApplyDetailQueryAction.json',
					                method: 'GET',
					                params: {
                                       'targetCustId': records[0].get('hbCustId')
                                   },
					                success: function(response) {
                                	   debugger;
                                   	var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
                                   	var json={'mergeData':[]};
                                   	for (var i = 0; i < nodeArra.length; i++) {
                                   		json.mergeData.push(nodeArra[i]);
                                   	}
                                   	dpmergeGrid.store.removeAll();
                                   	dpmergeGrid.store.loadData(json);
                                   	var tempArray=[];
        							for(var i=0 ;i<dpmergeGrid.store.data.items.length;i++){
        								var oppData={};
        								var temp =dpmergeGrid.store.data.items[i];
        								oppData.spIdStr = temp.get(hhbAppListPanel.primary);
        								oppData.hbCustId = records[0].get('hbCustId');
        								oppData.hbCustName = records[0].get('hbCustId');
        								oppData.tarCustId = temp.get('custId');
        								oppData.tarCustName = temp.get('custName');
//        								oppData.approvelStatus = temp.get('approvelStatus');
//        								oppData.hbOrgId = temp.get('hbOrgId');
//        								oppData.hbMainType = temp.get('hbMainType');
//        								oppData.tarOrgId = temp.get('tarOrgId');
//        								oppData.tarMainType = temp.get('tarMainType');
        								tempArray.push(oppData);
        							}
        							
        							hhbAppListPanel.tempArray=tempArray;
                                   	
                                   },
					                failure: function(a, b, c) {}
					            });
							
						    		if(hhbAppListPanel.editFun)
						    			hhbAppListPanel.editFun();
						    		if(hhbAppListPanel.stUrl)
						    			hhbAppListPanel.seOneRecord(id);
						    		else if(hhbAppListPanel.demoData)
						    			hhbAppListPanel.fp.getForm().loadRecord(record);
				    		
						}
					} else {
						Ext.Msg.alert("提示", "请先选择要审批的记录!");
					}
				}
		}
		}],
		
		//审批通过
		approvel : function() {
		debugger;
		Ext.Ajax.request({
			url : basepath + '/CustCFApplyAction!approvel.json',
			params : {
				idStr : hhbAppListPanel.spIdStr,	
				tempArray:Ext.encode(hhbAppListPanel.tempArray)
			},
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			method : 'POST',
			scope : hhbAppListPanel,
			success : function() {
				Ext.Msg.alert('提示', '审批通过');
				hhbAppListPanel.loadCurrData();
			},
			failure : function() {
				Ext.Msg.alert('提示', '审批不通过');
				hhbAppListPanel.loadCurrData();
			}
		});
		hhbAppListPanel.closeWin();
		},
		//审批不通过
		approvelBack : function() {
			Ext.Ajax.request({
				url : basepath + '/CustCFApplyAction!approvelBack.json',
				params : {
					idStr : hhbAppListPanel.spIdStr
				},
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				method : 'POST',
				scope : hhbAppListPanel,
				success : function() {
					Ext.Msg.alert('提示', '操作成功');
					hhbAppListPanel.loadCurrData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作不成功');
					hhbAppListPanel.loadCurrData();
				}
			});
			hhbAppListPanel.closeWin();
		},	
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			//debugger;
//			Ext.getCmp('sub_createDate').setValue(new Date(b.createDate.time));
		},
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems :{items:[
			util.layout._tr([util.form._td({name : 'approvelStatus',columnWidth: .25,xtype : 'combo',fieldLabel : '审批状态',store : appStatusStore,valueField : 'key',displayField : 'value'})],
							[util.form._td({name : 'tarCustId',columnWidth: .25,xtype : 'textfield',fieldLabel : '原客户编号'})],
							[util.form._td({name : 'hbCustId',columnWidth: .25,xtype : 'textfield',fieldLabel : '原客户名称'})]
							)
		]},
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ {name : 'id',mapping:'ID'},
		          {name : 'approvelStatus',header : '审批状态',type : 'mapping',store : appStatusStore,mappingkey : 'key',mappingvalue : 'value',mapping:'APPROVEL_STATUS'}, 
		          {name : 'hbCustId',header : '拆分前客户编号',mapping:'TAR_CUST_ID'}, 
		          {name : 'hbCustName',header : '拆分前客户名称',mapping:'TAR_CUST_NAME'}, 
		          {name : 'hbOrgId',mapping:'HB_ORG_ID'}, 
		          {name : 'hbMainType',mapping:'HB_MAIN_TYPE'}, 
		          {name : 'tarOrgId',hidden : true,mapping:'TAR_ORG_ID'}, 
		          {name : 'tarMainType',hidden : true,mapping:'TAR_MAIN_TYPE'}, 
		          {name : 'tarCustId',mapping:'HB_CUST_ID'}, //拆分后客户编号
		          {name : 'tarCustName',mapping:'HB_CUST_NAME'}, //拆分后客户名称
		          {name : 'applyUser',header : '申请人',mapping:'USER_NAME'}, 
		          {name : 'applyInit',header : '申请人机构',mapping:'APPLY_INIT'}, 
		          {name : 'createDate',header : '申请日期',mapping:'CREATE_DATE'}, 
		          {name : 'applyReason',header : '拆分原因',mapping:'APPLY_REASON'}
		        ],
		
		// 新增、修改、详情的form的字段
		formColums :function(){
			return new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'approvelStatus',xtype : 'combo',fieldLabel : '审批状态',disabled : true,store : appStatusStore,valueField : 'key',displayField : 'value'})],
								[util.form._td({id : 'sub_createDate',name : 'createDate',fieldLabel : '申请日期',disabled : true,xtype : 'datefield',readOnly : true})]
								),
				util.layout._tr([util.form._td({name : 'hbCustId',fieldLabel : '原客户编号',disabled : true,xtype : 'textfield'})],
								[util.form._td({name : 'hbCustName',fieldLabel : '原客户名称',disabled : true,xtype : 'textfield'})]
								),
				util.layout._tr([util.form._td({
					xtype : 'fieldset',
					title : '拆分形成客户列表',
					items : [dpmergeGrid]
				})]),
				util.layout._tr([util.form._td({name : 'applyUser',fieldLabel : '申请人',disabled : true,xtype : 'textfield'})],
								[util.form._td({name : 'applyInit',fieldLabel : '申请人机构',disabled : true,xtype : 'textfield'})]
				),
				util.layout._tr([util.form._td({name : 'applyReason',fieldLabel : '拆分原因',disabled : true,xtype : 'textarea',maxLength : 600})]
				),
				util.layout._tr([util.form._td({name : '',fieldLabel : '审批意见',xtype : 'textarea',maxLength : 600,hidden:true})]
				),
				util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})]
				)
		]});}
	});
	/***********************************/
	var menberRecord = new Ext.data.Record.create([{
		name : 'custId',
		mapping : 'HB_CUST_ID'
	},{
		name : 'tarCust',
		mapping : 'TAR_CUST_ID'
	},{
		name : 'name',
		mapping : 'HB_CUST_NAME'
	},{
		name : 'relation',
		mapping : 'CUST_STAT_ORA'
	},{
		name : 'custNum',
		mapping : 'CERT_TYPE_ORA'
	},{
		name : 'telephone',
		mapping : 'CERT_NUM'
	},{
		name : 'custType',
		mapping : 'CUST_TYP_ORA'
	},{
		name : 'custGrade',
		mapping : 'CUST_LEV_ORA'
	}]);
	var menberReader = new Ext.data.JsonReader({// 读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	}, menberRecord);
	var menberStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/CustomerCFDetailQueryAction.json',
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if (resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			},
			method : 'GET'
		}),
		reader : menberReader
	});
	
	var menberSm = new Ext.grid.CheckboxSelectionModel({
		hidden : true
	});

	var mRowNumb = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var menberCm = new Ext.grid.ColumnModel([mRowNumb,menberSm,{
		header : '客户编号',
		dataIndex : 'custId',
		sortable : true
	},{
		header : '是否目标客户',
		dataIndex : 'tarCust',
		sortable : true,
		hidden : true,
		width : 100
	},{
		header : '客户名称',
		dataIndex : 'name',
		sortable : true,
		width : 100
	},{
		header : '客户状态',
		dataIndex : 'relation',
		sortable : true,
		width : 100
	},{
		header : '证件类型',
		dataIndex : 'custNum',
		sortable : false,
		width : 100
	},{
		header : '证件号码',
		dataIndex : 'telephone',
		sortable : false,
		width : 100
	},{
		header : '客户类型',
		dataIndex : 'custType',
		sortable : false,
		width : 100
	},{
		header : '客户级别',
		dataIndex : 'custGrade',
		sortable : false,
		width : 100
	}]);

	
	var hisDetailPanel = new Ext.grid.GridPanel({
		id : 'hisDetailPanel_id',
		height : 200,
		viewConfig : {
			autoScroll : true,
			forceFit : false
		},
		store : menberStore,
		stripeRows : true,
		loadMask : '请稍后...',
		cm : menberCm,
		sm : menberSm
	});
	var hisDetailWin = new Ext.Window({
		title : '客户拆分历史详情',
		modal : true,
		draggable : true,
		layout : 'fit',
		width : 600,
		height : 280,
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		constrain : true,
		items : [hisDetailPanel]
	});
	/***********************************/

	
	//合并历史记录panel
	var mergeHisPanel = new Mis.Ext.CrudPanel( {
		id : "mergeHisPanel",
		title : "客户拆分历史信息",
		closable:false,	//在选项卡上，不显示关闭按钮
		stUrl : basepath + '/CustomerCFHisQueryAction.json',
		primary : "id",
		dbclick : false,
		checkbox : false,
//		height : document.body.clientHeight-30,
		// 定义查询条件Form的高度
		seFormHeight : 100,
		// 定义增删详情页面弹出窗口高度
		winHeight : 300,
		//宽度
		winWidth : 600,
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			//debugger;
			Ext.getCmp('hhbDt').setValue(new Date(b.hhbDt.time));
		},
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems :{items:[
			util.layout._tr([util.form._td({name : 'targetCustId',xtype : 'textfield',fieldLabel : '拆分后客户编号',columnWidth: .25})],
							[util.form._td({name : 'targetCustName',xtype : 'textfield',fieldLabel : '拆分后客户名称',columnWidth: .25})],
							[util.form._td({name : 'hhbDt',xtype : 'datefield',fieldLabel : '拆分日期',columnWidth: .25})]
							)
		]},
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [{name : 'id'},
		         {name : 'sourceCustId',header : '原客户号',hidden : true},
		         {name : 'targetCustId',header : '拆分前客户名称',mapping:'HB_CUST_NAME'},
		         {name : 'sourceCustName',header : '拆分后客户名称',mapping:'TAR_CUST_NAME'},
		         {name : 'custState',header : '客户状态',mapping:'CUST_STAT_ORA'},
		         {name : 'certTyp',header : '证件类型',mapping:'CERT_TYPE_ORA'},
		         {name : 'certNum',header : '证件号码',mapping:'CERT_NUM'},
		         {name : 'custType',header : '客户类型',mapping:'CUST_TYP_ORA'},
		         {name : 'custLev',header : '客户级别',mapping:'CUST_LEV_ORA'},
		         {name : 'cfDt',header : '拆分日期',mapping:'HHB_DT'}
		],
		buts : [{
			text : '详情',
			hidden:true,
			handler : function(){
              if (mergeHisPanel.grid.selModel.getSelections().length == 1) {
            	  var _record = mergeHisPanel.grid.selModel.getSelections();
                  menberStore.load({
      				params : {
      				'cfId' : _record[0].json.TAR_CUST_ID
      			}
      			});
      				hisDetailWin.show();
              } else {
            	  Ext.MessageBox.alert('提示', '请选择要查看详情的一条数据！');
              };
			}
		}]
	});
	
	// 布局模型
	var tabs = new Ext.TabPanel({
		xtype:"tabpanel",			   			   
		region:"center",
		activeTab: 0,
	    items: [hhbListPanel,hhbAppListPanel,mergeHisPanel]
	});
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ tabs ]
	});
	
	//拆分方法，分单独拆分与整体拆分

	function dpt(data,flag){
		debugger;
		var tmp = {};
		tmp.createDate = Ext.getCmp('createDate').getValue();
		tmp.applyReason = Ext.getCmp('applyReason').getValue();
		 Ext.Ajax.request({
             url: basepath + '/CustCFApplyAction!batchCF.json',
             method: 'GET',
             params : {
			 'data': Ext.encode(data),
			 'opTag':flag,
			 'createDate':tmp.createDate,
			 'applyReason': tmp.applyReason
		 },
             success: function(response) {
			 Ext.Msg.alert("提示", "操作成功!");
//                 var pId = Ext.util.JSON.decode(response.responseText).pid;
//                 Ext.getCmp('userId').setValue(pId);
             },
             failure: function(response) {
                 Ext.Msg.alert('提示', '保存出错');
             }
         });
	};
});			