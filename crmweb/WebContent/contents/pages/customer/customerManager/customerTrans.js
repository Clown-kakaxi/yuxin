Ext.onReady(function() {
	Ext.QuickTips.init();
	var mainTypeStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=MAINTAIN_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	mainTypeStore.load();
	
	var xlStore1 = new Ext.data.ArrayStore({
		restful : true,
		autoLoad : true,
		data : [['岗位变动','岗位变动'], ['离职','离职' ],['其他','其他' ]],
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ]),
        fields:['myId','displayText']
        
	});
	//form
	var transedFPanel = new Ext.FormPanel( {
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',
				columnWidth : .5,
				labelWidth:100,
				items : [ new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '新客户经理名称', 
					id:'CUST_MANAGER',
					labelStyle: 'text-align:right;',
					name : 'CUST_MANAGER',
					hiddenName:'afterMgrName',
					searchRoleType:('47'),  //指定查询角色属性 ,默认全部角色
					searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:true,
					allowBlank:false,
					anchor : '95%'
					}) ]
			},{
				columnWidth : .5,
				layout : 'form',
				labelWidth:100,
				items : [
					{	
                 fieldLabel: '工作移交类别',
                 name : 'workTranLevel',
                 id:'workTranLevel',
                 forceSelection : true,
                 xtype:'combo',
                 labelStyle: 'text-align:right;',
                 triggerAction:'all',
                 mode:'local',
                 store:xlStore1,
                 valueField:'myId',
                 displayField:'displayText',
                 allowBlank:false,
                 emptyText:'请选择',
                 anchor : '95%'
             }
                      ]
			},{
				layout : 'form',
				columnWidth : .5,
				labelWidth:100,
				items : [ {
				    id : 'workTranDate',
				    name : 'workTranDate',
				    xtype : 'datefield',
				    format : 'Y-m-d',
				    fieldLabel : '工作交接日期',
				    labelStyle : 'text-align:right;',
				    allowBlank:false,
				    anchor : '95%'
				}]
			},{
				layout : 'form',
				columnWidth : 1,
				labelWidth:100,
				items : [ {
				    id : 'workTranReason',
				    name : 'workTranReason',
				    xtype : 'textarea',
				    fieldLabel : '工作移交原因',
				    labelStyle : 'text-align:right;',
				    allowBlank:false,
				    anchor : '97.5%'
				}]
			}]
		}]
	});
	var combo = new Ext.form.ComboBox({
	    typeAhead: true,
	    triggerAction: 'all',
	    forceSelection : true,
	    mode: 'local',
	    store: new Ext.data.ArrayStore({
	        id: 0,
	        fields: [
	            'beforeMainType',
	            'displayText'
	        ],
	        data: [[1, '主办'], [2, '协办']]
	    }),
	    valueField: 'beforeMainType',
	    displayField: 'displayText',
	    hiddenName : 'beforeMainType',
	    allowBlank : false
   });
	
   Ext.util.Format.comboRenderer = function(combo){
	    return function(value){
	        var record = combo.findRecord(combo.valueField, value);
	        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
	    };
	};
	var sm = new Ext.grid.RowSelectionModel();								//选中行
	//var sm = new Ext.grid.CheckboxSelectionModel();						//选中行
	var rownum = new Ext.grid.RowNumberer({ header : 'No.',width : 28 });  	//行号
	//已选择移交的客户,待移交列模型
	var transedCm = new Ext.grid.ColumnModel([rownum,
	       {
			 	dataIndex : 'belMgrId',
			 	hidden : true
		   },
	       {
			 	header : '客户编号',
			 	dataIndex : 'custId',
			 	sortable : true,
			 	width : 105
	       },{
			 	header : '客户经理编号',
			 	dataIndex : 'beforeMgrId',
			 	width : 120
	       },{
			 	header : '客户经理名称',
			 	dataIndex : 'beforeMgrName',
			 	width : 200
	       },{
			 	header : '主协办类型',
			 	dataIndex : 'beforeMainType',
			 	width : 130,
			 	editor: combo,
				renderer: Ext.util.Format.comboRenderer(combo)
	       }
	]);
	//已选择移交的客户,待移交Record
	var transedRecord = Ext.data.Record.create([
        {name: 'belMgrId',mapping : 'ID'},
        {name: 'custId',mapping : 'CUST_ID'},
        {name: 'beforeMgrId',mapping : 'MGR_ID'},
        {name: 'beforeMgrName',mapping : 'MGR_NAME'},
        {name: 'beforeMainType',mapping : 'MAIN_TYPE'}
    ]);
     var transedStore = new Ext.data.Store({
    	 reader: new Ext.data.JsonReader({
        	 root : 'transedData'
         },transedRecord )
     });
     var transedTbar = new Ext.Toolbar({
 	    items: [{
 	            text: '删除选中行',
 	            handler : function(){
 	    			var grid = Ext.getCmp('transedGrid');
		 	    	var selectedRows = grid.getSelectionModel().getSelections(); 
		 	    	var ds = grid.getStore();
		 	    	//删除选中多行
		 	    	for (var i = 0; i < selectedRows.length; i++) {
		 	    		ds.remove(selectedRows[i]); 
		 	    	}
 				}
 	    }]
 	});
	//已选择移交的客户,待移交grid
	var transedGrid = new Ext.grid.EditorGridPanel({
		id : 'transedGrid',
		title : '<span style="font-weight:normal">待移交的客户列表</span>',
		//autoHeight : true,
		height : 230,
		//frame : true,
		autoScroll : true,
		store : transedStore, 			// 数据存储
		stripeRows : true, 				// 斑马线
		cm : transedCm, 				// 列模型
		sm : sm,						//行选择
		tbar : transedTbar,				// 工具栏
		loadMask : { msg : '正在加载表格数据,请稍等...' }
	});
	//机构分配模态窗口
	var batchTransWin = new Ext.Window({
		plain : true,
		layout : 'anchor',
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
		height : 430,
		buttonAlign : "center",
		title : '主管批量移交客户',
		items : [transedFPanel,transedGrid],
		buttons : [{
	    	text:'保存',
	    	handler:function(){
				if(!transedFPanel.form.isValid()){
					return false;
				}
				var result = '';
//				var mgrId = afterMgrName.userId.aId[0];							//调整后客户经理编号
				var mgrId = Ext.getCmp('CUST_MANAGER').hiddenField.value;
				var mgrName = Ext.getCmp('CUST_MANAGER').getValue();
				//调整后客户经理名称
				var workTranLevel = Ext.getCmp('workTranLevel').getValue();		//工作移交级别
				var workTranDate = Ext.getCmp('workTranDate').value;		//工作交接日期
				var workTranReason = Ext.getCmp('workTranReason').getValue();	//工作移交理由
				result += mgrId + ',';
				result += mgrName + ',';
				result += workTranLevel + ',';
				result += workTranDate + ',';
				result += workTranReason + '&';
				//取出待移交的所有客户
				var transedCusts = transedGrid.store.data.items;
				for (var i = 0; i < transedCusts.length; i++) {
					var record = transedCusts[i].data;
					result += record["belMgrId"] + ',';			//归属客户经理信息表ID
					//result += record["custId"] + ',';			//客户编号
					//result += record["beforeMgrId"] + ',';		//客户经理编号
					//result += record["beforeMgrName"] + ',';	//客户经理名称
					result += record["beforeMainType"];			//主协办类型
					if (i != transedCusts.length - 1) {
						result += "&";
					}
				}
				Ext.Ajax
				.request({
					url : basepath + '/customer_trans!batchTrans.json',
					params : {
						data : result
					},
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					method : 'POST',
					scope : batchTransWin,
					success : function(a,b) {
						Ext.Msg.alert('提示', '操作成功');
						//通过以下代码，可以实现操作成功后加载操作后的数据
						batchTransPanel.selectForm.getForm().setValues([
						   {id : 'MGR_NAME',value : Ext.getCmp('MGR_NAME').getValue()}
						]);
						batchTransPanel.loadCurrData();
					},
					failure : function(a,b) {
						Ext.Msg.alert('提示', '操作失败');
						batchTransPanel.selectForm.getForm().setValues([
						   {id : 'MGR_NAME',value : Ext.getCmp('MGR_NAME').getValue()}
						]);
						batchTransPanel.loadCurrData();
					}
				});
				batchTransWin.hide();
				//debugger;
	    	}
	    },{
	    	text:'关闭',handler:function(){batchTransWin.hide();}
	    }]
	});
	
	// 主管批量移交客户panel
	var batchTransPanel = new Mis.Ext.CrudPanel( {
		id : "batchTransPanel",
		title : "主管批量移交客户",
		defaultLoad : false,
		dbclick : false,
		closable:false,	//在选项卡上，不显示关闭按钮
		stUrl : basepath + '/customer_trans.json',
		primary : "CUST_ID",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 80,
		// 定义增删详情页面弹出窗口高度
		winHeight : 250,
		//宽度
		winWidth : 600,
		buts : [{
			id : 'trans',
			xtype : 'button',
			tooltip : '客户批量移交',
			text : '移交至...',
			iconCls:'publishIconCss',
			listeners : {
				click : function(n) {
					transedFPanel.getForm().reset();
					if (batchTransPanel.grid.selModel.hasSelection()) {
						var record = batchTransPanel.grid.getSelectionModel().getSelections();
						//var records = [];
						var json={'transedData':[]};
						for (var i = 0; i < record.length; i++) {
							//records[i] = record[i].json;
							json.transedData.push(record[i].json);
						}
						transedStore.loadData(json);
						batchTransWin.show();
					} else {
						Ext.Msg.alert("提示", "请先选择要移交的客户!");
					}
				}
			}
		}],
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems : {
			layout : 'column',
			items : [ {
				columnWidth : .3,
				layout : 'form',
				labelWidth : 150,
				allowBlank:false,
				anchor : '95%',
				labelStyle : 'text-align:right',
				items : [ 
				    new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '请选择客户经理名称(*)', 
					id:'MGR_NAME',
					labelStyle: 'text-align:right;',
					name : 'MGR_NAME',
					hiddenName:'MGR_ID',
					searchRoleType:('47'),  //指定查询角色属性 ,默认全部角色
					searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:true,
					anchor : '95%'
					}) ]
			}]
		},
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ {
			name : 'CUST_ID',
			header : '客户编号'
		},{
			name : 'CUST_ZH_NAME',
			header : '客户中文名称'
		},{
			name : 'MGR_ID',
			header : '客户经理编号'
		},{
			name : 'MGR_NAME',
			header : '客户经理名称'
		},{
			name : 'MAIN_TYPE',
			header : '主协办类型',
			type : 'mapping',
			store : mainTypeStore,
		 	mappingkey : 'key',
		 	mappingvalue : 'value'
		},{
			name : 'ASSIGN_USERNAME',
			header : '分配人'
		},{
			name : 'ASSIGN_DATE',
			header : '分配日期'
		}],
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20
	});
	
	// 移交历史panel
	var transHisPanel = new Mis.Ext.CrudPanel( {
		id : "transHisPanel",
		title : "客户移交历史",
		closable:false,	//在选项卡上，不显示关闭按钮
		stUrl : basepath + '/customer_trans_hist!indexPage.json',
//		detailUrl : basepath + '/customer_trans_hist!indexPage.json',
		primary : "id",
		checkbox : true,
		// 定义查询条件Form的高度
//		seFormHeight : 80,
		// 定义增删详情页面弹出窗口高度
		winHeight : 430,
		//宽度
		winWidth : 800,
	// 查询字段定义，若不定义则不出现查询条件From
		selectItems : {
			layout : 'column',
			items : [  {
				columnWidth : .3,
				layout : 'form',
				labelWidth : 90,
				defaultType : 'textfield',
				border : false,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',width : '100',anchor : '95%'
				} ]
			} ]
		},
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [{name : 'id'},
		         {name : 'custId',header : '客户编号'},
		         {name : 'beforeInstCode'},
		         {name : 'afterInstCode'},
		         {name : 'beforeInstName'},
		         {name : 'afterInstName'},
		         {name : 'afterMgrId'}, 
		         {name : 'beforeMgrId'}, 
		         {name : 'afterMgrId'}, 
		         {name : 'beforeMgrName',header : '调整前归属客户经理名称'}, 
		         {name : 'afterMgrName',header : '调整后归属客户经理名称'}, 
		         {name : 'beforeMainType'},
		         {name : 'afterMainType'},
		         {name : 'workTranReason',header : '工作移交原因'}, 
		         {name : 'workTranLevel',header : '工作移交类别',type : 'mapping',store : xlStore1,mappingkey : 'key',mappingvalue : 'value'}, 
		         {name : 'workTranDate',header : '工作交接日期',type : 'date'}, 
		         {name : 'assignUser'},
		         {name : 'assignUsername',header : '分配人名称'}, 
		         {name : 'assignDate',header : '分配日期',type : 'date'
		}],
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			//debugger;
			Ext.getCmp('assignDate').setValue(new Date(b.assignDate.time));
			Ext.getCmp('workTranDate').setValue(new Date(b.workTranDate.time));
		},
		// 新增、修改、详情的form的字段
		fclms : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'id',
						xtype : 'textfield',
						fieldLabel : '编号',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'custId',
						xtype : 'textfield',
						fieldLabel : '客户编号',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'beforeInstCode',
						xtype : 'textfield',
						fieldLabel : '调整前归属机构代码',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'afterInstCode',
						xtype : 'textfield',
						fieldLabel : '调整后归属机构代码',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'beforeInstName',
						xtype : 'textfield',
						fieldLabel : '调整前归属机构名称',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'afterInstName',
						xtype : 'textfield',
						fieldLabel : '调整后归属机构名称',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'beforeMgrId',
						xtype : 'textfield',
						fieldLabel : '调整前归属客户经理编号',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'afterMgrId',
						xtype : 'textfield',
						fieldLabel : '调整后归属客户经理编号',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'beforeMgrName',
						xtype : 'textfield',
						fieldLabel : '调整前归属客户经理名称',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'afterMgrName',
						xtype : 'textfield',
						fieldLabel : '调整后归属客户经理名称',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						store : mainTypeStore,
						xtype : 'combo',
						name : 'beforeMainType',
						hiddenName : 'beforeMainType',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						disabled : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						fieldLabel : '调整前主协办类型',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						store : mainTypeStore,
						xtype : 'combo',
						name : 'afterMainType',
						hiddenName : 'afterMainType',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						disabled : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						fieldLabel : '调整后主协办类型',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'assignUser',
						xtype : 'textfield',
						fieldLabel : '分配人',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'assignUsername',
						xtype : 'textfield',
						fieldLabel : '分配人名称',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						id : 'assignDate',
						name : 'assignDate',
						xtype : 'datefield',
						format : 'Y-m-d',
						fieldLabel : '分配日期',
						labelStyle : 'text-align:right;',
						disabled : true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						name : 'workTranLevel',
						xtype : 'textfield',
						fieldLabel : '工作移交类别',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : .5,
					labelWidth : 180,
					items : [ {
						id : 'workTranDate1',
						name : 'workTranDate',
						xtype : 'datefield',
						format : 'Y-m-d',
						fieldLabel : '工作移交日期',
						labelStyle : 'text-align:right;',
						disabled : true,
						anchor : '95%'
					} ]
				}, {
					layout : 'form',
					columnWidth : 1,
					labelWidth : 180,
					items : [ {
						name : 'workTranReason',
						xtype : 'textarea',
						fieldLabel : '工作移交原因',
						disabled : true,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					} ]
				} ]
			}]
	});
	var tabs = new Ext.TabPanel({
		xtype:"tabpanel",			   			   
		region:"center",
		activeTab: 0,
	    items: [batchTransPanel,transHisPanel]
	});
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ tabs ]
	});
});