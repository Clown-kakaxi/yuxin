/**
 * 财富管理->业务需求->止损设置：JS文件；hujun；2013-10-21
 */
Ext.onReady(function(){
	var custSelectPartAdd = new Com.yucheng.bcrm.common.CustomerQueryField({
		fieldLabel : '客户名称',
		labelWidth : 100,
		name : 'custName',
		id:'custName',
		custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
		custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
		singleSelected : true,// 单选复选标志
		editable : false,
		allowBlank : false,
		blankText : '此项为必填项，请检查！',
		anchor : '95%',
		hiddenName : 'custId'
	});
	var addProd = new Com.yucheng.crm.common.ProductManage({
		xtype : 'productChoose',
		fieldLabel : '产品',
		labelStyle : 'text-align:right;',
		name : 'prodName',
		id:'prodName',
		hiddenName : 'prodId',
		singleSelect : false,
		allowBlank : false,
		blankText : '此项为必填项，请检查！',
		anchor : '95%'
	});
	var addAccount = new Ext.form.TextField({
		 fieldLabel:'帐号',
		 name:'accounts',
		 id:'accounts',
		 anchor:'95%'
	});
	
	//查询模块
 var search =new Ext.form.FormPanel({
	 title:'条件查询',
	 frame:true,
	 labelAlign:'right',
	 buttonAlign:'center',
	 split:true,
	 height:100,
	 region:'north',
	 autoScoll:true,
	 items:[{
		 layout:'column',
		 items:[{
			 layout:'form',
			 columnWidth:.3,
			 items:[{
				 xtype:'textfield',
				 fieldLabel:'规则编号',
				 name:'rule_Id',
				 id:'rule_Id',
				 anchor:'90%'
			 }]
		 },{
			 layout:'form',
			 columnWidth:.3,
			 items:[{
				 xtype:'textfield',
				 fieldLabel:'规则名称',
				 name:'rule_name',
				 id:'rule_name',
				 anchor:'90%'
			 }]
		 },{
			 layout:'form',
			 columnWidth:.3,
			 items:[{
					xtype:'combo',
					id:'balance',
					name:'ruleLevel',
					triggerAction:'all',
					anchor:'90%',
				//	lazyRender:true,
					fieldLabel:'规则类型',
					mode:'local',
					store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: ['value','displayText'],
			        data: [[1, '客户'], [2, '账户'],[3,'产品']]
			               }),
			       valueField:'value',
			       displayField:'displayText'
			 }]
		 }]
	 }],
	 buttons:[{
		 text:'查询',
		 handler:function(){
		 
	 }
	 },{
		 text:'重置',
		 handler:function(){
		 search.getForm().reset();
	 }
	 }]
 });
 debugger;
 var sm = new Ext.grid.CheckboxSelectionModel();
	var Columns = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                new Ext.grid.RowNumberer(),sm,
	                                {header :'规则编号',dataIndex:'ruleId',id:"ruleId",sortable : true,width:60},
	                                {header :'规则名称',dataIndex:'ruleName',id:"ruleName",sortable : true,width:100},
	                                {header :'规则类型',dataIndex:'ruleType',id:"ruleType",sortable : true,width:100},
	                                {header :'规则状态',dataIndex:'ruleState',id:"ruleState",sortable : true,width:100},
	                                {header :'阀值(%)',dataIndex:'ruleLimit',id:"ruleLimit",sortable : true,width:100},
	                                {header :'开始时间',dataIndex:'ruleStar',id:"ruleStar",sortable : true,width:100},
	                                {header :'结束时间',dataIndex:'ruleEnd',id:"ruleEnd",sortable : true,width:100},
	                                {header :'提醒方式',dataIndex:'remindType',id:"remindType",sortable : true,width:120},
	                                {header :'描述',dataIndex:'describe',id:"describe",sortable : true,width:180}
	                                               	]);
	 var data=[
	        	  ['1001','客户理财','客户','启用','67','2013-9-10','2013-9-10','短信','根据数据分析'],
	        	  ['1002','100g黄金','产品','启用','89','2013-9-10','2013-9-10','短信','根据数据分析'],
	        	  ['1003','账户余额','账户','停用','76','2013-9-10','2013-9-10','站内信','根据数据分析'],
	        	  ['1004','客户理财','客户','启用','50','2013-9-10','2013-9-10','短信','根据数据分析'],
	        	  ['1005','客户理财','客户','停用','66','2013-9-10','2013-9-10','短信',''],
	        	  ['1006','客户理财','客户','启用','68','2013-9-10','2013-9-10','短信',''],
	        	  ['1007','客户理财','客户','启用','87','2013-9-10','2013-9-10','短信','']        			     
	        			          ];
	var store=new Ext.data.Store({
		proxy:new Ext.data.MemoryProxy(data),
		reader:new Ext.data.ArrayReader({},[{name:'ruleId'},{name:'ruleName'},
		                                    {name:'ruleType'}, {name:'ruleState'},
		                                    {name:'ruleLimit'}, {name:'ruleStar'},
		                                    {name:'ruleEnd'},{name:'remindType'},
		                                    {name:'describe'}])
				
	});
	store.load();
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
		store.reload({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
	});
	// 分页工具栏
	var sbbar = new Ext.PagingToolbar({
		pageSize : parseInt(spagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	//***********************
	 var addPanel = new Ext.form.FormPanel({
		 labelWidth : 100,
		 //height : 215,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items:[{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'textfield',
					 fieldLabel:'规则名称',
					 name:'ruleName',
					 id:'ruleName',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
						xtype:'combo',
						id:'ruleType',
						name:'ruleType',
						triggerAction:'all',
						anchor:'90%',
					//	lazyRender:true,
						fieldLabel:'规则类型',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 0,
				        fields: ['value','displayText'],
				        data: [[1, '客户'], [2, '账户'],[3,'产品']]
				               }),
				       valueField:'value',
				       displayField:'displayText',				 
				       listeners : {
								'select' : function(combo) {
									distTaskTypeAddChange();
								}
							}
				 }]
			 }]
		 },{
				 layout:'form',
				// columnWidth:.5,
				 items:[custSelectPartAdd,addProd,addAccount]
			 
		 },{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'textfield',
					 fieldLabel:'止损阀值',
					 name:'ruleLimit',
					 id:'ruleLimit',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'datefield',
					 fieldLabel:'开始日期',
					 format:'Y-m-d',
					 name:'ruleStar',
					 id:'ruleStar',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'datefield',
					 fieldLabel:'截止日期',
					 format:'Y-m-d',
					 name:'ruleEnd',
					 id:'ruleEnd',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
						xtype:'combo',
						id:'remindType',
						name:'remindType',
						triggerAction:'all',
						anchor:'90%',
						fieldLabel:'规则类型',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 1,
				        fields: ['value','displayText'],
				        data: [[1, '站内信'], [2, '短信']]
				               }),
				       valueField:'value',
				       displayField:'displayText'
				 
				 }]
			 }]
		 },{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:1,
					 items:[{
						 xtype:'textarea',
						 fieldLabel:'描述',
						 name:'describe',
						 height:'100',
						 anchor:'95%'
					 }]}]	 
		 }]
	 });
	 var addRuleWind=new Ext.Window({
   		title:'新增规则',
   		width:880,
   		height:420,
   		closeAction:'hide',
   		closable:true,
   		maximizable:true,
   		buttonAlign:'center',
   		border:false,
   		layout:'fit',
   		draggable:true,
   		collapsible:true,
   		titleCollapse:true,
   		items:[addPanel],
   		buttons:[{
   			text:'保 存',
   			handler:function(){
   				Ext.Msg.alert("提示","保存成功！");
   				addRuleWind.hide();
   				addPanel.getForm.reset();
   		}
   		}]
   	});
	    Ext.getCmp('custName').setVisible(false);		
		Ext.getCmp('prodName').setVisible(false);		
		Ext.getCmp('accounts').setVisible(false);
	// “规则类型”下拉框选择值时，处理逻辑
	 function distTaskTypeAddChange() {
	 	var Type = addPanel.form.findField('ruleType').getValue();
	 	if (Type == "1") {
	 		// 规则类型为“客户”
	 		Ext.getCmp('custName').setVisible(true);
	 		Ext.getCmp('custName')["allowBlank"] = false;
	 		Ext.getCmp('prodName').setVisible(false);
	 		Ext.getCmp('prodName')["allowBlank"] = true;
	 		Ext.getCmp('accounts').setVisible(false);
	 		Ext.getCmp('accounts')["allowBlank"] = true;
	 	} else if (Type == "2") {
	 		// 规则类型为：账户
	 		Ext.getCmp('custName').setVisible(false);
	 		Ext.getCmp('custName')["allowBlank"] = true;
	 		Ext.getCmp('prodName').setVisible(false);
	 		Ext.getCmp('prodName')["allowBlank"] = true;
	 		Ext.getCmp('accounts').setVisible(true);
	 		Ext.getCmp('accounts')["allowBlank"] = false;
	 	}else if(Type=='3'){
	 		//规则类型为：产品
	 		Ext.getCmp('custName').setVisible(false);
	 		Ext.getCmp('custName')["allowBlank"] = true;
	 		Ext.getCmp('prodName').setVisible(true);
	 		Ext.getCmp('prodName')["allowBlank"] = false;
	 		Ext.getCmp('accounts').setVisible(false);
	 		Ext.getCmp('accounts')["allowBlank"] = true;
	 	}
	 };
	var grid=new Ext.grid.GridPanel({
		title:'规则列表',
		frame:true,
		region:'center',
		id:'productInfoGrid',
		store:store,
		loadMask:true,
		cm :Columns,
		sm:sm,
		tbar:[{
			text:'新增',
			iconCls:'addIconCss',
			handler:function(){
				addRuleWind.show();
			}
		},{
			text:'维护',
			iconCls:'editIconCss',
			handler:function(){
				var record = grid.getSelectionModel().getSelected();
				if (record==null || record=="undefined") {
					Ext.Msg.alert('提示','请选择一条记录!');
				} else {
					editPanel.getForm().loadRecord(record);
					var ruleType = grid.getSelectionModel().getSelections()[0].data.ruleType;
				 	if (ruleType == "客户") {
				 		// 规则类型为“客户”
				 		Ext.getCmp('custName1').setVisible(true);
				 		Ext.getCmp('prodName1').setVisible(false);
				 		Ext.getCmp('accounts1').setVisible(false);
				 	} else if (ruleType == "账户") {
				 		// 规则类型为：账户
				 		Ext.getCmp('custName1').setVisible(false);
				 		Ext.getCmp('prodName1').setVisible(false);
				 		Ext.getCmp('accounts1').setVisible(true);
				 	}else if(ruleType=='产品'){
				 		//规则类型为：产品
				 		Ext.getCmp('custName1').setVisible(false);
				 		Ext.getCmp('prodName1').setVisible(true);
				 		Ext.getCmp('accounts1').setVisible(false);
				 	}
					editRuleWind.show();
				}									
			}
		},{
			text:'删除',
			iconCls:'deleteIconCss',
			handler:function(){
				var record = grid.getSelectionModel().getSelected();
				if (record==null || record=="undefined") {
					Ext.Msg.alert('提示','请选择一条记录!');
				} else {
					Ext.Msg.confirm("提示","你确认要删除此规则？？",function(but){
						if(but=='yes'){
							Ext.Msg.alert("提示","成功操作!");
						}else{
							Ext.Msg.alert("提示","你取消了此操作!");
						}
					});
				}
			}
		},{
			text:'启用/停用',
			iconCls:'detailIconCss',
			handler:function(){
				var record = grid.getSelectionModel().getSelected();
				if (record==null || record=="undefined") {
					Ext.Msg.alert('提示','请选择一条记录!');
				} else {
					var state = grid.getSelectionModel().getSelections()[0].data.ruleState;
					if(state=="启用"){
						Ext.Msg.confirm("提示","你确认要停用此规则？？",function(but){
							if(but=='yes'){
								Ext.Msg.alert("提示","成功操作!");
							}else{
								Ext.Msg.alert("提示","你取消了此操作!");
							}
						});
					}else if(state =='停用'){
						Ext.Msg.confirm("提示","你确认要启用此规则？？",function(but){
							if(but=='yes'){
								Ext.Msg.alert("提示","成功操作!");
							}else{
								Ext.Msg.alert("提示","你取消了此操作!");
							}
						});
					}				
				}				
			}
		}			
		,{text:'详情',
			iconCls:'detailIconCss',
			handler:function(){
				var record = grid.getSelectionModel().getSelected();
				if (record==null || record=="undefined") {
					Ext.Msg.alert('提示','请选择一条记录!');
				} else {
					detailPanel.getForm().loadRecord(record);
					var ruleType = grid.getSelectionModel().getSelections()[0].data.ruleType;
				 	if (ruleType == "客户") {
				 		// 规则类型为“客户”
				 		Ext.getCmp('custName2').setVisible(true);
				 		Ext.getCmp('prodName2').setVisible(false);
				 		Ext.getCmp('accounts2').setVisible(false);
				 	} else if (ruleType == "账户") {
				 		// 规则类型为：账户
				 		Ext.getCmp('custName2').setVisible(false);
				 		Ext.getCmp('prodName2').setVisible(false);
				 		Ext.getCmp('accounts2').setVisible(true);
				 	}else if(ruleType=='产品'){
				 		//规则类型为：产品
				 		Ext.getCmp('custName2').setVisible(false);
				 		Ext.getCmp('prodName2').setVisible(true);
				 		Ext.getCmp('accounts2').setVisible(false);
				 	}
					detailRuleWind.show();
				}									
			}}
		],
		bbar:sbbar,
		loadMask : {
        msg : '正在加载表格数据,请稍等...'
    }

	});
	//布局
	var view = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items:[{
		layout:'border',
		items:[search,grid]
		       }]
	});

});