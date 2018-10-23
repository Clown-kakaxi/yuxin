Ext.onReady(function() {
	Ext.QuickTips.init();
		var afterFlag = '0';
		var data=[];
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
		
	
	
		var mergeAppPanel = new Ext.FormPanel( {
			frame : true,
			autoScroll : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {id : 'createDate',name : 'createDate',allowBlank:false,xtype : 'datefield',format:'Y-m-d',value:new Date(),fieldLabel : '申请日期',labelStyle : 'text-align:right;',anchor : '95%'}]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {id : 'applyReason',name : 'applyReason',allowBlank:false,xtype : 'textarea',fieldLabel : '合并理由',labelStyle : 'text-align:right;',anchor : '95%'}]
				}]
			}]
		});
		//行选择
		var sm1 = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true
//			hidden : true
		});
		var rownum = new Ext.grid.RowNumberer({ header : 'No.',width : 28 });  	//行号
		//已选择 合并的客户信息展现,待合并列模型
		var mergeCm = new Ext.grid.ColumnModel([sm1,rownum,
		       {
				 	header : '客户编号',
				 	dataIndex : 'custId',
				 	sortable : true,
				 	width : 200
		       },{
				 	header : '客户名称',
				 	dataIndex : 'custZhName',
				 	width : 200
		       },{
				 	header : '客户所属机构',
				 	dataIndex : 'orgId',
				 	sortable : true,
				 	width : 200,
				 	hidden:true
		       },{
				 	header : '主协办类型',
				 	dataIndex : 'mainType',
				 	width : 200,
				 	hidden:true
		       },{
		    	    header : '是否主账户',
		    	    dataIndex : 'ifMain',
		    	    hidden:true,
		    		width : 200
//		    	    renderer:function(value,record,e){
//		    	   debugger;
//			    		var checkBox = '<div style="text-align:center;"><input id='+e.id+'_check type="checkbox" "/><div>';
//			    		return  checkBox;
//			    	}
		       }
		]);
		//记录
		var mergeRecord = Ext.data.Record.create([
	        {name: 'custId'},
	        {name: 'custZhName'}
	    ]);
	     var mergeStore = new Ext.data.Store({
	    	 reader: new Ext.data.JsonReader({
	        	 root : 'mergeData'
	         },mergeRecord )
	     });
		
		//已选择合并的客户,待合并客户信息展现
		var mergeGrid = new Ext.grid.EditorGridPanel({
			id : 'mergeGrid',
			title : '<span style="font-weight:normal">待合并的客户列表<b>(请选择一个客户作为主客户)</b></span>',
			height : 250,
			autoScroll : true,
			store : mergeStore, 			// 数据存储
			stripeRows : true, 				// 斑马线
			cm : mergeCm, 				    // 列模型
			sm : sm1,						//行选择
			loadMask : { msg : '正在加载表格数据,请稍等...' },
	    	listeners:{
	    		'click' : function(){
//					Ext.each(mergeStore.data.getRange(),function(e){
//						if(e.id != mergeGrid.getSelectionModel().getSelected().id+'_check'){
//							document.getElementById(e.id+'_check').checked = false;
//						}
//					});
//					document.getElementById(mergeGrid.getSelectionModel().getSelected().id+'_check').checked = true;
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
			closeAction : 'close',
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
			title : '合并客户申请',
			items : [mergeAppPanel,mergeGrid],
			buttons : [{
		    	text:'合并申请',
		    	
		    	handler:function(){
				debugger;
				//合并字段嵌入form
					if(Ext.getCmp('createDate').value <new Date().format('Y-m-d')){
						Ext.Msg.alert('提示', '操作申请日期不能小于当前日期！');	
						return false;
					}
					var record =Ext.getCmp('mergeGrid').getSelectionModel().getSelections();
					var grid = Ext.getCmp('mergeGrid').getSelectionModel().getSelected();;
					if (record.length==0){
						Ext.Msg.alert('提示', '请选择一个客户为目标合并客户!');
						return false;
					}
					if (!mergeAppPanel.form.isValid()){
						return false;
					}
					//目标合并客户信息
					var targetCustInfo = '';
					targetCustInfo += grid.data["custId"] + ',';
					targetCustInfo += grid.data["custZhName"] + ',';
					//加入申请相关信息
					var createDate = Ext.getCmp('createDate').value;//申请日期
					var applyReason = Ext.getCmp('applyReason').getValue();//申请理由
					targetCustInfo += createDate + ',';//加入申请日期
					targetCustInfo += applyReason;//加入申请理由
					
					//记录申请合并的所有客户信息
					var hbSelectRecords = '';
					var hbSet = mergeGrid.store.data.items;
					for (var i=0; i<hbSet.length; i++){
						var record = hbSet[i].data;
						hbSelectRecords += record["custId"] + ',';//合并客户编号
						hbSelectRecords += record["custZhName"]+ ',';//合并客户中文名
						hbSelectRecords += record["hbOrgId"] + ',';//合并客户所属机构
						hbSelectRecords += record["hbMainType"];//合并客户主协办类型
						if (i != hbSet.length - 1) {
							hbSelectRecords += "&";
						}
					}
					
					
					Ext.Ajax.request({
						url : basepath + '/custDescHhbInfo-info!hbCustInfoApply.json',
						params : {
							data : hbSelectRecords,
							selectRecord : targetCustInfo
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						method : 'POST',
						scope : custMergeWin,
						success : function(a,b) {
							Ext.Msg.alert('提示', '操作成功');							
							hhbListPanel.loadCurrData();
						},
						failure : function(a,b) {
						 	Ext.Msg.alert('提示', '操作失败');							
							hhbListPanel.loadCurrData();
						}
					});
					custMergeWin.hide();
					
				}
			},{
		    	text:'取消',handler:function(){custMergeWin.hide();}
		    }]
		    
		});
		
		// 合并客户信息的展现panel
		var hhbListPanel = new Mis.Ext.CrudPanel( {
			id : "hhbListPanel",
			title : "客户合并->待合并客户查询",
			closable:false,	//在选项卡上，不显示关闭按钮
			stUrl : basepath + '/custDescHhbInfo-info.json',
			//applyUrl : basepath + '/lat_apply_info!apply.json',
			primary : "custId",
			dbclick : false,	
			checkbox : true,
			// 定义查询条件Form的高度
//			seFormHeight : 100,
			// 定义增删详情页面弹出窗口高度
			winHeight : 250,
			//宽度
			winWidth : 600,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			
			buts : [
			{
				id : 'mergeInfo',
				xtype : 'button',
				tooltip : '合并申请',
				text : '合并申请',
				iconCls:'completeIconCss',
				listeners : {
					click : function(n) {
								if (hhbListPanel.grid.selModel.hasSelection()) {
									
								
									var record = hhbListPanel.grid.getSelectionModel().getSelections();
									debugger;
									custMergeWin.show();
									debugger;
									mergeStore.removeAll();
									for (var i = 0; i < record.length; i++) {
										var addRecord = new Ext.data.Record({
											custId:record[i].json.CUST_ID,
											custZhName:record[i].json.CUST_ZH_NAME,
											hbOrgId:record[i].json.ORG_ID,
											hbMainType:record[i].json.MAIN_TYPE
										});
										mergeStore.add(addRecord);
									}
									debugger;
									custMergeWin.doLayout();
									
								} else {
									Ext.Msg.alert("提示", "请先选择要申请合并的客户!");
								}
					}
				}
			}],
		
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :{items:[
				util.layout._tr([util.form._td({name : 'custZhName',columnWidth: .25,xtype : 'textfield',fieldLabel : '客户名称'})],
								[util.form._td({name : 'certType',columnWidth: .25,xtype : 'combo',fieldLabel : '证件类型',store : certTypStore,valueField : 'key',displayField : 'value'})],
								[util.form._td({name : 'certNum',columnWidth: .25,xtype : 'textfield',fieldLabel : '证件号码'})]
								
								)
			]},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'custId',header : '客户编号',mapping:'CUST_ID'},
			          {name : 'custStat',header : '客户状态',type : 'mapping',store : custStatStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CUST_STAT_ORA'}, 
			          {name : 'custZhName',header : '客户名称',mapping:'CUST_ZH_NAME'}, 
			          {name : 'certType',header : '证件类型',type : 'mapping',store : certTypStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CERT_TYPE_ORA'}, 
			          {name : 'certNum',sortable : true,header : '证件号码',mapping:'CERT_NUM'},
			          {name : 'orgId',sortable : true,mapping:'CUST_ID'}, 
			          {name : 'mainType',sortable : true,mapping:'MAIN_TYPE'},
			          {name : 'custTyp',header : '客户类型',type : 'mapping',store : custTypStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CUST_TYP_ORA'},
			          {name : 'custLev',sortable : true,header : '客户级别',type : 'mapping',store : custLevStore,mappingkey : 'key',mappingvalue : 'value',mapping:'CUST_LEV_ORA'}
			        ]
	
		});
		//已选择合并的客户,待合并客户信息展现
		var dpsm = new Ext.grid.CheckboxSelectionModel({
//		singleSelect : true,
//		hidden : true
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
				 	header : '是否主客户',
				 	dataIndex : 'ifMain',
				 	width : 120
		       }
		]);
		//记录
		var dpmergeRecord = Ext.data.Record.create([
	        {name: 'custId',mapping : 'HB_CUST_ID'},
	        {name: 'custName',mapping : 'HB_CUST_NAME'},
	        {name: 'ifMain'}
	    ]);
	     var dpmergeStore = new Ext.data.Store({
	    	 reader: new Ext.data.JsonReader({
	        	 root : 'mergeData'
	         },dpmergeRecord )
	     });
		
		
	     var dpmergeGrid = new Ext.grid.EditorGridPanel({
			id : 'dpmergeGrid',
			region : 'center',
			height : 80,
			autoScroll : true,
			store : dpmergeStore, 			// 数据存储
			stripeRows : true, 				// 斑马线
//			sm : dpsm,		
			cm : dpmergeCm, 				    // 列模型
			loadMask : { msg : '正在加载表格数据,请稍等...' }
		});
		//合并审批panel
		var hhbAppListPanel = new Mis.Ext.CrudPanel( {
			id : "hhbAppListPanel",
//			disabled:JsContext.checkGrant('approve'),
			title : "客户合并->合并审批",
			closable:false,	//在选项卡上，不显示关闭按钮
			stUrl : basepath + '/ocrmFCiHhbApplyInfo-info.json',
			detailUrl : basepath + '/ocrmFCiHhbApplyInfo-info!indexPage.json',
			approvelURl : basepath + '/ocrmFCiHhbApplyInfo-info!approvelBack.json',
			primary : "id",
			// 采用单选框
			singleSelect : true,
			// 定义查询条件Form的高度
			seFormHeight : 100,
			// 定义增删详情页面弹出窗口高度
			winHeight : 450,
			//宽度
			winWidth : 600,
			spIdStr : '',
			hbCustId : '',
			//自定义按钮
			buts : [
			{
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
								var record = hhbAppListPanel.grid.getSelectionModel()
										.getSelected();
								var id = record.get(hhbAppListPanel.primary);
								hhbAppListPanel.opUrl = hhbAppListPanel.approvelURl;
								
								hhbAppListPanel.spIdStr = records[0].get(hhbAppListPanel.primary);
								hhbAppListPanel.hbCustId = records[0].get('hbCustId');
								hhbAppListPanel.tarCustId = records[0].get('tarCustId');
								var approvelStatus = records[0].get('approvelStatus');
								
//								hhbAppListPanel.hbOrgId = records[0].get('hbOrgId');
//								hhbAppListPanel.hbMainType = records[0].get('hbMainType');
//								hhbAppListPanel.tarOrgId = records[0].get('tarOrgId');
//								hhbAppListPanel.tarMainType = records[0].get('tarMainType');
								
								
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
						                url: basepath + '/CustHBDetailQueryAction.json',
						                method: 'GET',
						                params: {
	                                        'idStr': records[0].get('tarCustId')
	                                    },
						                success: function(response) {
	                                    	debugger;
							    	var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
						    		var json={'mergeData':[]};
						    		dpmergeGrid.store.removeAll();
						    		data = [];
						    		for (var i = 0; i < nodeArra.length; i++) {
										 var firstArray = {};
										json.mergeData.push(nodeArra[i]);
										firstArray.idStr=nodeArra[i].ID;
										firstArray.hbCustId=nodeArra[i].HB_CUST_ID;
										firstArray.tarCustId=nodeArra[i].TAR_CUST_ID;
										firstArray.hbOrgId=nodeArra[i].HB_ORG_ID;
										firstArray.hbMainType=nodeArra[i].HB_MAIN_TYPE;
										firstArray.tarOrgId=nodeArra[i].TAR_ORG_ID;
										firstArray.tarMainType=nodeArra[i].TAR_MAIN_TYPE;
										if(firstArray.hbCustId == firstArray.tarCustId){
											tmp = '是';
											firstArray.ifMain='1';
										}else{
											tmp = '否';
											firstArray.ifMain='0';
										}
										var addRecord = new Ext.data.Record({
											custId:nodeArra[i].HB_CUST_ID,
											custName:nodeArra[i].HB_CUST_NAME,
											ifMain:tmp
										});
										debugger;
										data.push(firstArray);
										dpmergeGrid.store.add(addRecord);
									}
						    		hhbAppListPanel.data=data;
							     },
						                failure: function(a, b, c) {}
						            });
							debugger;
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
					url : basepath + '/ocrmFCiHhbApplyInfo-info!approvel.json',
					params : {
					  'data': Ext.encode(data)
						
					},
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					method : 'POST',
					scope : hhbAppListPanel,
					success : function() {
						Ext.Msg.alert('提示', '操作成功');
						hhbAppListPanel.loadCurrData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						hhbAppListPanel.loadCurrData();
					}
				});
				hhbAppListPanel.closeWin();
			},
				
			//审批不通过
			approvelBack : function() {
					Ext.Ajax.request({
						url : hhbAppListPanel.approvelURl,
						params : {
							  'data': Ext.encode(data)
							},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						method : 'POST',
						scope : hhbAppListPanel,
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							hhbAppListPanel.loadCurrData();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							hhbAppListPanel.loadCurrData();
						}
					});
					hhbAppListPanel.closeWin();
			},	
		
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
				debugger;

//				 Ext.Ajax.request({
//		                url: basepath + '/CustHBDetailQueryAction.json',
//		                method: 'GET',
//		                params: {
//                     'idStr': b.TAR_CUST_ID
//                 },
//		                success: function(response) {
//                 	debugger;
//			    	var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
//		    		var json={'mergeData':[]};
//		    		dpmergeGrid.store.removeAll();
//		    		data = [];
//		    		for (var i = 0; i < nodeArra.length; i++) {
//						 var firstArray = {};
//						json.mergeData.push(nodeArra[i]);
//						firstArray.idStr=nodeArra[i].ID;
//						firstArray.hbCustId=nodeArra[i].HB_CUST_ID;
//						firstArray.tarCustId=nodeArra[i].TAR_CUST_ID;
//						firstArray.hbOrgId=nodeArra[i].HB_ORG_ID;
//						firstArray.hbMainType=nodeArra[i].HB_MAIN_TYPE;
//						firstArray.tarOrgId=nodeArra[i].TAR_ORG_ID;
//						firstArray.tarMainType=nodeArra[i].TAR_MAIN_TYPE;
//						if(firstArray.hbCustId == firstArray.tarCustId){
//							tmp = '是';
//						}else{
//							tmp = '否';
//						}
//						var addRecord = new Ext.data.Record({
//							custId:nodeArra[i].HB_CUST_ID,
//							custName:nodeArra[i].HB_CUST_NAME,
//							ifMain:tmp
//						});
//						dpmergeGrid.store.add(addRecord);
//						data.push(firstArray);
//					}
//		    		afterFlag = '1';
//					
//			     },
//		                failure: function(a, b, c) {}
//		            });
			
				
			},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :{items:[
				util.layout._tr([util.form._td({name : 'APPROVEL_STATUS',columnWidth: .25,xtype : 'combo',fieldLabel : '审批状态',store : appStatusStore,valueField : 'key',displayField : 'value'})],
								[util.form._td({name : 'TAR_CUST_ID',columnWidth: .25,xtype : 'textfield',fieldLabel : '合并后客户号'})],
								[util.form._td({name : 'HB_CUST_ID',columnWidth: .25,xtype : 'textfield',fieldLabel : '原客户号'})]
								)
			]},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'id',mapping:'ID'},
			          {name : 'approvelStatus',header : '审批状态',type : 'mapping',store : appStatusStore,mappingkey : 'key',mappingvalue : 'value',mapping:'APPROVEL_STATUS'}, 
			          {name : 'sourceCustId',mapping:'HB_CUST_ID'}, 
			          {name : 'sourceCustName',mapping:'HB_CUST_NAME'}, 
			          {name : 'tarCustId',header : '合并后客户号',mapping:'TAR_CUST_ID'}, 
			          {name : 'tarCustName',header : '合并后客户名称',mapping:'TAR_CUST_NAME'}, 
			          {name : 'hbCustId',mapping:'HB_CUST_ID'}, 
			          {name : 'hbCustName',mapping:'HB_CUST_NAME'}, 
			          {name : 'applyUser',header : '申请人',mapping:'APPLY_USER'}, 
			          {name : 'applyInit',header : '申请人机构',mapping:'APPLY_INIT'}, 
			          {name : 'createDate',header : '申请日期',mapping:'CREATE_DATE'}, 
			          {name : 'applyReason',header : '合并理由',mapping:'APPLY_REASON'}
			        ],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'approvelStatus',xtype : 'combo',fieldLabel : '审批状态',disabled : true,store : appStatusStore,valueField : 'key',displayField : 'value'})],
									[util.form._td({id : 'sub_createDate',name : 'createDate',value:new Date(),fieldLabel : '申请日期',disabled : true,xtype : 'datefield',readOnly : true})]
									),
					util.layout._tr([util.form._td({name : 'tarCustId',fieldLabel : '合并后客户编号',disabled : true,xtype : 'textfield'})],
							[util.form._td({name : 'tarCustName',fieldLabel : '合并后客户名称',disabled : true,xtype : 'textfield'})]
							),
					util.layout._tr([util.form._td({
						xtype : 'fieldset',
						title : '原客户列表',
						items : [dpmergeGrid]
					})]),
					util.layout._tr([util.form._td({name : 'applyUser',fieldLabel : '申请人',disabled : true,xtype : 'textfield'})],
									[util.form._td({name : 'applyInit',fieldLabel : '申请人机构',disabled : true,xtype : 'textfield'})]
					),
					util.layout._tr([util.form._td({name : 'applyReason',fieldLabel : '合并理由',disabled : true,xtype : 'textarea',maxLength : 600})]
					),
//					util.layout._tr([util.form._td({name : '',fieldLabel : '审批意见',xtype : 'textarea',maxLength : 600})]
//					),
					util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})]
					)
			]});}
		});
		/***********************************/
		var menberRecord = new Ext.data.Record.create([{
			name : 'custId',
			mapping : 'CUST_ID'
		},{
			name : 'tarCust',
			mapping : 'TAR_CUST'
		},{
			name : 'name',
			mapping : 'CUST_NAME'
		},{
			name : 'relation',
			mapping : 'RELATION'
		},{
			name : 'custNum',
			mapping : 'CUST_NUM'
		},{
			name : 'telephone',
			mapping : 'TELEPHONE'
		},{
			name : 'custType',
			mapping : 'CUST_TYPE'
		},{
			name : 'custGrade',
			mapping : 'CUST_GRADE'
		}]);
		
		
		
		var menberSm = new Ext.grid.CheckboxSelectionModel({
			hidden : true
		});

		var mRowNumb = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		
	

		
//		var hisDetailPanel = new Ext.grid.GridPanel({
//			id : 'hisDetailPanel_id',
//			height : 200,
//			viewConfig : {
//				autoScroll : true,
//				forceFit : false
//			},
//			store : menberStore,
//			stripeRows : true,
//			loadMask : '请稍后...',
//			cm : menberCm,
//			sm : menberSm
//		});
//		var hisDetailWin = new Ext.Window({
//			title : '客户合并历史详情',
//			modal : true,
//			draggable : true,
//			layout : 'fit',
//			width : 600,
//			height : 280,
//			closable : true,// 是否可关闭
//			modal : true,
//			closeAction : 'hide',
//			titleCollapse : true,
//			buttonAlign : 'center',
//			border : false,
//			constrain : true,
//			items : [hisDetailPanel]
//		});
		/***********************************/

		
		//合并历史记录panel
		var mergeHisPanel = new Mis.Ext.CrudPanel( {
			id : "mergeHisPanel",
			title : "客户合并历史信息",
			closable:false,	//在选项卡上，不显示关闭按钮
			stUrl : basepath + '/ocrmFCiHhbMapping-info.json',
			detailUrl : basepath + '/ocrmFCiHhbMapping-info.json',//
			primary : "id",
			dbclick : false,
			checkbox : false,
			// 定义查询条件Form的高度
			seFormHeight : 100,
			// 定义增删详情页面弹出窗口高度
			winHeight : 300,
			//宽度
			winWidth : 600,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b,a,c) {
				debugger;
				 Ext.Ajax.request({
		                url: basepath + '/CustHBDetailQueryAction.json',
		                method: 'GET',
		                params: {
                      'idStr': b.TARGET_CUST_ID
                  },
		                success: function(response) {
                  	debugger;
			    	var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
		    		var json={'mergeData':[]};
		    		dpmergeGrid.store.removeAll();
		    		data = [];
		    		for (var i = 0; i < nodeArra.length; i++) {
						 var firstArray = {};
						 var tmp='';
						json.mergeData.push(nodeArra[i]);
						firstArray.idStr=nodeArra[i].ID;
						firstArray.hbCustId=nodeArra[i].HB_CUST_ID;
						firstArray.tarCustId=nodeArra[i].TAR_CUST_ID;
						firstArray.hbOrgId=nodeArra[i].HB_ORG_ID;
						firstArray.hbMainType=nodeArra[i].HB_MAIN_TYPE;
						firstArray.tarOrgId=nodeArra[i].TAR_ORG_ID;
						firstArray.tarMainType=nodeArra[i].TAR_MAIN_TYPE;
						if(firstArray.hbCustId == firstArray.tarCustId){
							tmp = '是';
						}else{
							tmp = '否';
						}
						var addRecord = new Ext.data.Record({
							custId:nodeArra[i].HB_CUST_ID,
							custName:nodeArra[i].HB_CUST_NAME,
							ifMain:tmp
						});
						dpmergeGrid.store.add(addRecord);
						data.push(firstArray);
					}
		    		
					
			     },
		                failure: function(a, b, c) {}
		            });
			},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :{items:[
				util.layout._tr([util.form._td({name : 'TARGET_CUST_ID',xtype : 'textfield',fieldLabel : '合并后客户号',columnWidth: .25})],
							[util.form._td({name : 'HHB_DT',xtype : 'datefield',fieldLabel : '合并日期',columnWidth: .25})],
							[util.form._td({name : 'SOURCE_CUST_ID',xtype : 'textfield',fieldLabel : '原客户号',columnWidth: .25,hidden:true})]
								)
			]},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [{name : 'id',mapping:'ID'},
			         {name : 'sourceCustId',mapping:'SOURCE_CUST_ID'},
			         {name : 'sourceCustId',header : '合并前客户名称',mapping:'SOURCE_ZH_NAME'},
			         {name : 'targetCustId',mapping:'TARGET_CUST_ID'},
			         {name : 'custZhName',header : '合并后客户名称',mapping:'CUST_ZH_NAME'},
			         {name : 'custState',header : '客户状态',mapping:'CUST_STAT_ORA'},
			         {name : 'certType',header : '证件类型',mapping:'CERT_TYPE_ORA'},
			         {name : 'certNum',header : '证件号码',mapping:'CERT_NUM'},
			         {name : 'custType',header : '客户类型',mapping:'CUST_TYP_ORA'},
			         {name : 'custLevel',header : '客户级别',mapping:'CUST_LEV_ORA'},
			         {name : 'hhbDt',header : '合并日期',mapping:'HHB_DT'}
			],
			// 新增、修改、详情的form的字段
			
			
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'targetCustId',mapping:'TARGET_CUST_ID',xtype : 'textfield',fieldLabel : '合并后客户号',disabled : true,valueField : 'key',displayField : 'value'})],
									[util.form._td({id : 'ifMain',name : 'ifMain',fieldLabel : '是否主客户',disabled : true,xtype : 'textfield',hidden:true,readOnly : true})]
									),
					util.layout._tr([util.form._td({
						xtype : 'fieldset',
						title : '原客户列表',
						items : [dpmergeGrid]
					})]),
					util.layout._tr([util.form._td({name : 'custZhName',mapping:'CUST_ZH_NAME',fieldLabel : '合并后客户名称',disabled : true,xtype : 'textfield'})],
									[util.form._td({name : 'custState',mapping:'CUST_STAT_ORA',fieldLabel : '客户状态',disabled : true,xtype : 'textfield'})]
									),
					util.layout._tr([util.form._td({name : 'certType',mapping:'CERT_TYPE_ORA',fieldLabel : '证件类型',disabled : true,xtype : 'textfield'})],
									[util.form._td({name : 'certNum',mapping:'CERT_NUM',fieldLabel : '证件号码',disabled : true,xtype : 'textfield'})]
					),
					util.layout._tr([util.form._td({name : 'custType',mapping:'CUST_TYP_ORA',fieldLabel : '客户类型',disabled : true,xtype : 'textfield'})]
					),
					util.layout._tr([util.form._td({name : 'custLevel',mapping:'CUST_LEV_ORA',fieldLabel : '客户级别',xtype : 'textfield',disabled : true})]
					)
			]});}
			
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
		
	
});			