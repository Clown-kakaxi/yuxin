var name = '';
Ext.onReady(function() {
	
	
	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '汽车金融供应链' ],
				[ 2, '日化用品金融供应链' ],[ 3, '家用电器金融供应链' ] , [ 4, '其他' ]]
	});
	
	var typeStore1 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '厂商' ],
				[ 2, '采购商' ],[ 3, '供应商' ] , [ 4, '经销商' ]]
	});
	 var custSourceStore = new Ext.data.ArrayStore( {
         fields : [ 'myId', 'displayText' ],
         data : [ [ '1', '自定义筛选' ], [ '2', '客户群导入' ]]
     });
	var  searchPanel = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .25,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '供应链名称',
				name : 'cust_NAME',
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [
			         new Ext.form.ComboBox({
			    			hiddenName : 'PROGRESS_STAGE',
			    			fieldLabel : '供应链类型',
			    			labelStyle: 'text-align:right;',
			    			triggerAction : 'all',
			    			store : typeStore,
			    			displayField : 'value',
			    			valueField : 'key',
			    			mode : 'local',
			    			emptyText:'请选择 ',
			    			resizable : true,
			    			anchor : '90%'
			    		})
				]
		}
		,{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '创建时间 从',
				format : 'Y-m-d',
				xtype : 'datefield',
				editable:false,
				name : 'CREATE_DATE',
				id : 'CREATE_DATE',
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '到',
				format : 'Y-m-d',
				xtype : 'datefield',
				editable:false,
				name : 'CREATE_DATE1',
				id : 'CREATE_DATE1',
				anchor : '90%'
			}]
		}],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {

			}
		}, {
			text : '重置',
			handler : function() {
				searchPanel.form.reset();
			}
		}]
		
	});
	


	// 展示新增的窗口
	function addActivityInit() {
		_buttonVisible = true;
		editBasePlanForm.form.reset();
		editBasePlanForm.form.findField('mktActiName').setDisabled(false);
		editBasePlanForm.form.findField('mktActiCost').setDisabled(false);
		editBasePlanForm.form.findField('mktActiType').setDisabled(false);
		editBasePlanForm.form.findField('pstartDate').setDisabled(false);
		editBasePlanForm.form.findField('mktActiMode').setDisabled(false);
		editBasePlanForm.form.findField('pendDate').setDisabled(false);
		editBasePlanForm.form.findField('mktActiStat').setDisabled(false);
		editBasePlanForm.form.findField('mktActiAddr').setDisabled(false);
		editBasePlanForm.form.findField('mktActiCont').setDisabled(false);
		editBasePlanForm.form.findField('actiCustDesc').setDisabled(false);
		editBasePlanForm.form.findField('actiOperDesc').setDisabled(false);
		editBasePlanForm.form.findField('actiProdDesc').setDisabled(false);
		editBasePlanForm.form.findField('mktActiAim').setDisabled(false);
		editBasePlanForm.form.findField('actiRemark').setDisabled(false);
		
		editPlanWindow.setTitle('营销活动新增');
		editBasePlanForm.form.findField('createUser').setValue(__userId);
		editBasePlanForm.form.findField('createDate').setValue(new Date());
		editBasePlanForm.form.findField('updateUser').setValue(__userId);
		editBasePlanForm.form.findField('updateDate').setValue(new Date());
		editBasePlanForm.form.findField('mktActiStat').setValue('1');
		editBasePlanForm.form.findField('createOrg').setValue(__units);
		
		Ext.getCmp('jbxx').show();
		Ext.getCmp('glcpxx').hide();
		Ext.getCmp('glkkxx').hide();
		Ext.getCmp('glqdxx').hide();
		Ext.getCmp('fjxx').hide();
		Ext.getCmp('spxx').hide();
		editPlanWindow.show();
	};
	
	var shareFlagStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=SHARE_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	 var infoPanel = new Ext.form.FormPanel({
         frame : true,
         buttonAlign : "center",
         autoScroll : true,
         labelWidth : 100,
         items : [ {
             layout : 'column',
             items : [ {
                 columnWidth : .33,
                 layout : 'form',
                 items : [ {
                     xtype : 'textfield',
                     labelStyle : 'text-align:right;',
                     width:150,
                     fieldLabel : '<span style="color:red">*</span>供应链名称',
                     allowBlank : false,
                     name : 'custBaseName',
                     value:'乐扣环保产品供应链',
                     anchor : '99%'
                 }]
             },{ columnWidth : .33,
                 layout : 'form',
            	 items:[{
						store : shareFlagStore,
						xtype : 'combo', 
						resizable : true,
						width:150,
						fieldLabel : '<span style="color:red">*</span>共享范围',
						hiddenName : 'shareFlag',
						name : 'shareFlag',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						allowBlank : false,
						mode : 'local',
						editable :false,
						value:'1',
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					}]
             },{
                 columnWidth : .33,
                 layout : 'form',
                 items : [{
						store : typeStore,
						xtype : 'combo', 
						resizable : true,
						width:150,
						fieldLabel : '<span style="color:red">*</span>供应链分类',
						hiddenName : 'groupType',
						name : 'groupType',
						valueField : 'key',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						editable :false,
						value:'2',
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					}]
             },{
                 columnWidth : 1,
                 layout : 'form',
                 items : [ {
                     xtype : 'textarea',
                     labelStyle : 'text-align:right;',
                     fieldLabel : '供应链描述',
                     name : 'custBaseDesc',
                     value:'乐扣是一个产品生产供销为一体大型环保类日用品供应链',
                     anchor : '95%'
                 } ]
             } ]
         }]
     });

		var fields1 = [ {
			name : 'b1'
		},{
			name : 'b2'
		}, {
			name : 'b3'
		}];

		//定义自动当前页行号
		var num1 = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

		var sm1 = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true
		});

		var columns1 = new Ext.grid.ColumnModel([num1, sm1, {
			dataIndex : 'b1',
			header : '客户编号',
			sortable : true,
			width : 140
		}, {
			dataIndex : 'b2',
			header : '客户名称',
			sortable : true,
			width : 140
		} ,{
			dataIndex : 'b3',
			header : '成员类型',
			sortable : true,
			width : 140
		}]);
		
		var data1 = [
['CNJ2013021800090','河北金星日化用品厂','厂商' ],
['CNJ2013021800091','河北金星日化采购中心','采购商' ],
['CNJ2013021800092','河北金星日化产品原料厂','供应商' ],
['CNJ2013021800093','河北金星日化分销公司','经销商' ],
['CNJ2013021800094','河北金星日化零售公司','经销商' ]
		];

		//每页显示条数下拉选择框
		var combo1 = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'],
								[50, '50条/页'], [100, '100条/页'],
								[250, '250条/页'], [500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});

		var store1 = new Ext.data.ArrayStore({
			fields : fields1,
			data : data1
		});

		var number1 = parseInt(combo1.getValue());

		//分页工具栏
		var bbar1 = new Ext.PagingToolbar({
			pageSize : number1,
			store : store1,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', combo1]
		});
		
  	var search_cust_group = new Com.yucheng.bcrm.common.CustomerQueryField({ 
		fieldLabel : '目标客户', 
		labelStyle: 'text-align:right;',
		name : 'custNameStr',
		custtype :'2',//客户类型：  1：对私, 2:对公,  不设默认全部
	    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
	    singleSelected:true,//单选复选标志
		editable : false,
		blankText:"请填写",
		anchor : '90%',
		hiddenName:'abcd',
		callback :function(){
	}
	});
		
  	var showCust = new Ext.form.FormPanel({
		 labelWidth : 80,
		 height : 200,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items : [{
			 layout : 'column',
	    	 items : [{
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [search_cust_group]
	    	 },{
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [{
						store : typeStore1,
						xtype : 'combo', 
						resizable : true,
						width:150,
						fieldLabel : '成员类型',
						hiddenName : 'groupType',
						name : 'groupType',
						valueField : 'key',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					}]
	    	 }]
		 }]
	 });
	
		
		var addcustWin = new Ext.Window({
			title : '添加成员',
			height:'200',
			width:'500',
			modal : true,//遮罩
			buttonAlign:'center',
			layout:'fit',
			items:[showCust],
			buttons:[
			         {
			        	 text:'添加',
			        	 handler: function(){
			        		 if(showCust.form.findField('custNameStr').getValue()==null||showCust.form.findField('custNameStr').getValue()==''){
				        		 Ext.MessageBox.alert('系统提示信息', '未选择客户！');
				        		 return false;
				        	 }
			    					Ext.MessageBox.alert('系统提示信息', '操作成功');
			    					addcustWin.hide();
						 }
						
			         }, {
			        	 text:'关闭',
			        	 handler:function(){
			        		 addcustWin.hide();
			         	}
			         }]
		});

		
	 var custList = new Ext.grid.GridPanel({
			region : 'center',
			frame : true,
			layout:'fit',
			store : store1,
			stripeRows : true,
			sm : sm1,
			cm : columns1,
			buttonAlign : "center",
			bbar : bbar1,
			tbar :  [{
						text : '新增',
						iconCls : 'editIconCss',
						handler : function() {
							addcustWin.show();
						}
					},{
						text : '删除',
						iconCls : 'deleteIconCss',
						handler : function() {
							var selectLength = custList
							.getSelectionModel()
							.getSelections().length;

							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
							}else{
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return false;
									}
									Ext.Msg.alert("提示", "操作成功!");
								});
							}	
						}
					}]
		});
	 
	 
	 
	var addWin = new Ext.Window({
		title : '供应链新增',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 550,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [{
			layout : 'border',
			items : [{
				region : 'center',
				id : 'center-panel',
				title : "成员列表",
				layout : 'fit',
				items : [ custList ]
			},{
				region : 'north',
				id : 'north-panel',
				title : "基本信息",
				height : 160,
				layout : 'fit',
				items : [ infoPanel ]
			}]
		}],
		buttonAlign : "center",
		buttons:[{
			text : '保存',
			handler : function() {
				var selectLength = custList.getSelectionModel().getSelections().length;
				if (selectLength != 1) {
					Ext.Msg.alert("提示", "请选择一个成员作为核心企业!");
				}else{
					if (!infoPanel.getForm().isValid()) {
	                    Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	                    return false;
	                }else{
	                	Ext.Msg.alert("提示", "操作成功!");
	                	addWin.hide();
	                }
				} 
			}}, '-', {
			text : '关闭',
			handler : function() {
				addWin.hide();
			}
		}
		]
	});
	
	
	var fields = [ {
		name : 'a'
	},{
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}, {
		name : 'a4'
	}, {
		name : 'a5'
	}];

	//定义自动当前页行号
	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	var columns = new Ext.grid.ColumnModel([num, sm, {
		dataIndex : 'a',
		header : '供应链编号',
		sortable : true,
		width : 120
	}, {
		dataIndex : 'a0',
		header : '供应链名称',
		sortable : true,
		width : 180
	}, {
		dataIndex : 'a1',
		header : '供应链类型',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '创建时间',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a3',
		header : '成员数',
		align:'right;',
		sortable : true,
		
		width : 140
	}, {
		dataIndex : 'a4',
		header : '供应链级别',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a5',
		header : '主管客户经理',
		sortable : true,
		width : 140
	}]);
	var data = [
	            ['gyl001','河北红星小家电供应链','家用电器金融供应链','2012-09-18','5','分行','张强'],
	            ['gyl002','河北石家庄金星日化供应链','日化用品金融供应链','2012-09-18','8','总行','苏芮'],
	            ['gyl003','河北一汽集团供应链','汽车金融供应链','2012-09-18','6','分行','张强'],
	            ['gyl004','河北邯郸市纸业集团供应链','日化用品金融供应链','2012-09-18','4','总行','苏芮'],
	            ['gyl005','河北东风汽车供应链','汽车金融供应链','2012-09-18','5','分行','张强']
	];

	//每页显示条数下拉选择框
	var combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['value', 'text'],
					data : [[10, '10条/页'], [20, '20条/页'],
							[50, '50条/页'], [100, '100条/页'],
							[250, '250条/页'], [500, '500条/页']]
				}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});

	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});

	var number = parseInt(combo.getValue());

	//分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', combo]
	});
	
		var listPanel = new Ext.grid.GridPanel({
			region : 'center',
			frame : true,
			layout:'fit',
			store : store,
			stripeRows : true,
			sm : sm,
			cm : columns,
			buttonAlign : "center",
			bbar : bbar,
			tbar :  [{
						text : '供应链视图',
						iconCls : 'custGroupMemIconCss',
						handler : function() {
							var selectLength = listPanel
							.getSelectionModel()
							.getSelections().length;
							 var checkedNodes = listPanel.getSelectionModel().selections.items;

							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
							}else{
								 var viewWindow = new Ext.Window(
										  {
											  layout : 'fit',
											  id:'viewWindow',
											  draggable : true,//是否可以拖动
											  closable : true,// 是否可关闭
											  modal : true,
											  closeAction : 'close',
											  maximized:true,
											  titleCollapse : true,
											  buttonAlign : 'center',
											  border : false,
											  animCollapse : true,
											  animateTarget : Ext.getBody(),
											  constrain : true,
											  items : [{ 
												  html:' <div style="width:100%;height:100%;"><div style="position:absolute; left:0px; top:0px; width:180px" id=\'sena_tree\'></div><div style="position:absolute; left:180px; top:0px;height:100%; " id=\'viewport_center\'></div></div>'}  
											  ]});
								  name=checkedNodes[0].data.a0;
								  viewWindow.title='您所浏览的供应链为：'+checkedNodes[0].data.a0;
								  oBaseInfo.base_id=checkedNodes[0].data.a;
								  oCustInfo.view_source='viewport_center';
								  oCustInfo.groupId = checkedNodes[0].data.a.substring(3,checkedNodes[0].data.a.length); 
								  ScriptMgr = new ScriptLoaderMgr();
								  ScriptMgr.load({        
									  scripts: ['menuOfOfferLinkView.js'],        
									  callback: function(){  
								  }
								  }); 
								  viewWindow.show();
							}	
							}
					},{
						text : '新增供应链',
						iconCls : 'editIconCss',
						handler : function() {
							addWin.show();
						}
					},{
						text : '删除供应链',
						iconCls : 'deleteIconCss',
						handler : function() {
							var selectLength = listPanel
							.getSelectionModel()
							.getSelections().length;

							if (selectLength != 1) {
								Ext.Msg.alert("提示", "请选择一条记录!");
							}else{
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return false;
									}
									Ext.Msg.alert("提示", "操作成功!");
								});
							}	
						}
					},{
						text : '创建营销活动',
						iconCls : 'editIconCss',
						handler : function() {
							addActivityInit();
						}
					}]
		});
		
	
var view = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'border',
					items : [{
						region : 'center',
						id : 'center-panel',
						title : "供应链列表",
						layout : 'fit',
						items : [ listPanel ]
					},{
						xtype : 'fieldset',
			            collapsed:false,
						collapsible : true,
						region : 'north',
						id : 'north-panel',
						title : "供应链查询",
						height : 100,
						layout : 'fit',
						items : [ searchPanel ]
					}]
				}]
			});
		});