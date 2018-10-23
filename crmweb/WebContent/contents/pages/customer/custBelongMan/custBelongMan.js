Ext.onReady(function() {

	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '直接分配原则' ], [ 2, '最早开户原则' ],
				[ 3, '存款规模最大原则' ],[4,'贷款优先原则'],[5,'综合贡献度最大原则'],[6,'基本户开户原则'] ]
	});
	var ifStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '是' ], [ 2, '否' ] ]
	});
	var orderStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '1' ], [ 2, '2' ],
				[ 3, '3' ],[4,'4'],[5,'5'],[6,'6']  ]
	});
	var keyStore1 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '按占比最大优先' ], [ 2, '按分配时间优先' ] ]
	});

	var keyStore2 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '按年日均' ], [ 2, '按季日均' ],[3,'按月日均'],[4,'按余额'] ]
	});

	var keyStore3 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '按最早办理贷款优先' ], [ 2, '按贷款规模最大优先' ] ]
	});

	var keyStore4 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '按综合贡献度最大优先' ], [ 2, '按存款贡献度最大优先' ], [ 3, '按贷款贡献度最大优先' ] ]
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
			items : [ new Ext.form.ComboBox({
    			hiddenName : 'PROGRESS_STAGE0',
    			fieldLabel : '方案名称',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : typeStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		})]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [
			         new Ext.form.ComboBox({
			    			hiddenName : 'PROGRESS_STAGE',
			    			fieldLabel : '是否启用',
			    			labelStyle: 'text-align:right;',
			    			triggerAction : 'all',
			    			store : ifStore,
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
			items : [new Ext.form.ComboBox({
    			hiddenName : 'PROGRESS_STAGE1',
    			fieldLabel : '优先级',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : orderStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
    		})]
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
	
	var fields = [{
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}, {
		name : 'a4'
	}];

	//定义自动当前页行号
	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});


	var columns = new Ext.grid.ColumnModel([num,  {
		dataIndex : 'a1',
		header : '方案名称',
		sortable : true,
		width : 120
	}, {
		dataIndex : 'a2',
		header : '是否启用',
		sortable : true,
		width : 100
	}, {
		dataIndex : 'a3',
		header : '优先级',
		sortable : true,
		
		width : 100
	}, {
		dataIndex : 'a4',
		header : '方案描述',
		sortable : true,
		width : 400
	}]);
	var data = [
	['直接分配原则', '是', '1', '在源业务系统中已经有分配关系的，则直接进行分配至客户经理'],
	['最早开户原则', '是', '2', '客户在哪家分支机构的开户时间最早，则分配给哪个机构'],
	['存款规模最大原则', '是', '3', '客户居哪家分支机构的存款规模最大，则分配给哪家机构。存款规模系数可以是日均或余额'],
	['贷款优先原则', '否', '4', '客户在哪家经营机构贷款，则分配给哪家经营机构'],
	['综合贡献度最大原则', '是', '5', '客户在哪家经营机构的综合贡献度最大，则分配给哪家机构'],
	['基本户开户原则', '否', '6', '客户的基本户开在哪家机构，则分配给哪家机构']];


	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});
	
	var editForm = new Ext.form.FormPanel( {
		frame : true,
		autoScroll : true,
		buttonAlign :'center',
		items : [{
			xtype : 'fieldset',
			title : '基本信息 ',
			collapsible : true,
			autoScroll : true,
			labelWidth : 80, // 标签宽度
			items : [ {
				layout : 'column',
				items : [ {
					columnWidth : .4,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						fieldLabel : '方案名称',
						name : 'a1',
						readOnly:true,
						anchor : '90%'
					}]}
				,{
						columnWidth : .3,
						layout : 'form',
						items : [new Ext.form.ComboBox({
			    			hiddenName : 'a3',
			    			fieldLabel : '优先级',
			    			labelStyle: 'text-align:right;',
			    			triggerAction : 'all',
			    			store : orderStore,
			    			displayField : 'value',
			    			valueField : 'key',
			    			mode : 'local',
			    			emptyText:'请选择 ',
			    			resizable : true,
			    			anchor : '90%'
			    		})]
				},{
					columnWidth : .3,
					layout : 'form',
					items : [new Ext.form.ComboBox({
		    			hiddenName : 'a2',
		    			fieldLabel : '是否启用',
		    			labelStyle: 'text-align:right;',
		    			triggerAction : 'all',
		    			store : ifStore,
		    			displayField : 'value',
		    			valueField : 'key',
		    			mode : 'local',
		    			emptyText:'请选择 ',
		    			resizable : true,
		    			anchor : '90%'
		    		})]},{
		    			columnWidth : .99,
		    			layout : 'form',
		    			items : [{
		    				xtype : 'textfield',
		    				fieldLabel : '方案描述',
		    				name : 'a4',
		    				anchor : '97%'
		    			}]
		    		}]  }] },{
					xtype : 'fieldset',
					id:'fpyz',
					title : '主办客户经理分配原则 ',
					collapsible : true,
					autoScroll : true,
					labelWidth : 80, // 标签宽度
					items : [ {
						layout : 'column',
						items : [ {
							columnWidth : .4,
							layout : 'form',
							items : [new Ext.form.ComboBox({
				    			hiddenName : 'a51',
				    			fieldLabel : '分配原则要素',
				    			labelStyle: 'text-align:right;',
				    			triggerAction : 'all',
				    			store : keyStore1,
				    			displayField : 'value',
				    			valueField : 'key',
				    			mode : 'local',
				    			emptyText:'请选择 ',
				    			hidden:true,
				    			resizable : true,
				    			anchor : '90%',
				    			listeners:{
				       			 select :function(){
				       				 if(editForm.form.findField('a51').getValue()=='1'){
				       					editForm.form.findField('a7').setValue('按客户所有账户中以开户时间最早的账户计算，以客户经理的账户分配比例占比最大者为主办');
					    			 }
					    			 if(editForm.form.findField('a51').getValue()=='2'){
					    				 editForm.form.findField('a7').setValue('按客户所有账号中，最先分配账户的客户经理为主办');
				       			 }
				       			 }
				    			}
				    		}),new Ext.form.ComboBox({
				    			hiddenName : 'a52',
				    			fieldLabel : '分配原则要素',
				    			labelStyle: 'text-align:right;',
				    			triggerAction : 'all',
				    			store : keyStore2,
				    			displayField : 'value',
				    			valueField : 'key',
				    			mode : 'local',
				    			hidden:true,
				    			emptyText:'请选择 ',
				    			resizable : true,
				    			anchor : '90%'
				    		}),new Ext.form.ComboBox({
				    			hiddenName : 'a53',
				    			fieldLabel : '分配原则要素',
				    			labelStyle: 'text-align:right;',
				    			triggerAction : 'all',
				    			store : keyStore3,
				    			displayField : 'value',
				    			valueField : 'key',
				    			mode : 'local',
				    			emptyText:'请选择 ',
				    			hidden:true,
				    			resizable : true,
				    			anchor : '90%'
				    		}),new Ext.form.ComboBox({
				    			hiddenName : 'a54',
				    			fieldLabel : '分配原则要素',
				    			labelStyle: 'text-align:right;',
				    			triggerAction : 'all',
				    			store : keyStore4,
				    			displayField : 'value',
				    			valueField : 'key',
				    			mode : 'local',
				    			hidden:true,
				    			emptyText:'请选择 ',
				    			resizable : true,
				    			anchor : '90%'
				    		})]},{ 
								columnWidth : 1,
								layout : 'form',
								items : [{
				    				xtype : 'textarea',
				    				fieldLabel : '分配原则提示',
				    				name : 'a6',
				    				hidden:true,
				    				readOnly:true,
				    				anchor : '97%'
				    			},{
				    				xtype : 'textarea',
				    				fieldLabel : '分配原则说明',
				    				name : 'a7',
				    				hidden:true,
				    				readOnly:true,
				    				anchor : '97%'
				    			}]}]
					
							}]
		    		}],
		    		buttonAlign : 'center',
		    		buttons : [ {
		    			text : '确定',
		    			handler : function() {
		    				editActivityWindow.hide();
		    				Ext.Msg.alert("提示", "保存成功!");
		    			}
		    		},{
		    			text : '关闭',
		    			handler : function() {
		    				editActivityWindow.hide();
		    			}
		    		}]
		         
	});
	var editActivityWindow = new Ext.Window({
		title : '分配原则修改',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 350,
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
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'center',
						layout : 'fit',
						items : [ editForm ]
					}
			]
		} ]
	});
	function editInit() {
		editActivityWindow.show();
	}

	var listPanel = new Ext.grid.GridPanel({
		title : '分配原则信息',
		region : 'center',
		frame : true,
		layout:'fit',
		store : store,
		stripeRows : true,
		cm : columns,
		tbar :  [{
					text : '修改',
					iconCls : 'editIconCss',
					handler : function() {
						var selectLength = listPanel
						.getSelectionModel()
						.getSelections().length;

						var selectRe = listPanel
								.getSelectionModel()
								.getSelections()[0];

						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
						}else{
							editForm.form.reset();
							editForm.getForm().loadRecord(selectRe);
							if(selectRe.data.a1 == '直接分配原则'){
								editForm.form.findField('a51').setVisible(true);
								editForm.form.findField('a52').setVisible(false);
								editForm.form.findField('a53').setVisible(false);
								editForm.form.findField('a54').setVisible(false);
								editForm.form.findField('a6').setVisible(false);
								editForm.form.findField('a7').setVisible(true);
								editForm.form.findField('a51').setValue('1');
								editForm.form.findField('a7').setValue('按客户所有账户中以开户时间最早的账户计算，以客户经理的账户分配比例占比最大者为主办');
								Ext.getCmp('fpyz').show();
							}
							if(selectRe.data.a1 == '最早开户原则'){
								editForm.form.findField('a51').setVisible(true);
								editForm.form.findField('a52').setVisible(false);
								editForm.form.findField('a53').setVisible(false);
								editForm.form.findField('a54').setVisible(false);
								editForm.form.findField('a6').setVisible(false);
								editForm.form.findField('a7').setVisible(false);
								editForm.form.findField('a51').setValue('1');
								Ext.getCmp('fpyz').show();
							}
							if(selectRe.data.a1 == '存款规模最大原则'){
								editForm.form.findField('a51').setVisible(false);
								editForm.form.findField('a52').setVisible(true);
								editForm.form.findField('a53').setVisible(false);
								editForm.form.findField('a54').setVisible(false);
								editForm.form.findField('a6').setVisible(true);
								editForm.form.findField('a52').setValue('1');
								editForm.form.findField('a6').setValue('按客户在辖内机构汇总数据统计');
								editForm.form.findField('a7').setVisible(false);
								Ext.getCmp('fpyz').show();
							}
							if(selectRe.data.a1 == '贷款优先原则'){
								editForm.form.findField('a51').setVisible(false);
								editForm.form.findField('a52').setVisible(false);
								editForm.form.findField('a53').setVisible(true);
								editForm.form.findField('a54').setVisible(false);
								editForm.form.findField('a6').setVisible(true);
								editForm.form.findField('a53').setValue('1');
								editForm.form.findField('a6').setValue('按客户在辖内机构汇总数据统计');
								editForm.form.findField('a7').setVisible(false);
								Ext.getCmp('fpyz').show();
							}
							if(selectRe.data.a1 == '综合贡献度最大原则'){
								editForm.form.findField('a51').setVisible(false);
								editForm.form.findField('a52').setVisible(false);
								editForm.form.findField('a53').setVisible(false);
								editForm.form.findField('a54').setVisible(true);
								editForm.form.findField('a54').setValue('1');
								editForm.form.findField('a6').setVisible(true);
								editForm.form.findField('a6').setValue('按客户在辖内机构汇总数据统计');
								editForm.form.findField('a7').setVisible(false);
								Ext.getCmp('fpyz').show();
							}
							if(selectRe.data.a1 == '基本户开户原则'){
								editForm.form.findField('a51').setVisible(false);
								editForm.form.findField('a52').setVisible(false);
								editForm.form.findField('a53').setVisible(false);
								editForm.form.findField('a54').setVisible(false);
								editForm.form.findField('a6').setVisible(false);
								editForm.form.findField('a7').setVisible(false);
								Ext.getCmp('fpyz').hide();
							}
								editInit();
							}
							
						}
							
				}]
	});


	
	
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'north',
						id : 'north-panel',
						title : "客户归属方案查询",
						height : 100,
						layout : 'fit',
						items : [ searchPanel ]
					},{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [ listPanel ]
					}
					]
				} ]
			});
		});