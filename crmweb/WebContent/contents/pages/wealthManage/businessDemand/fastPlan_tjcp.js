	
   var editForm =new Ext.form.FormPanel({				
				labelWidth:90,
				id:'sreach',
				frame:true,
				layout:'fit',
				labelAlign:'right',
				buttonAlign:'center',
				split:true,
				height:80,
				items:[{
					layout:'column',
					border:false,
					items:[{
							columnWidth:.5,
							layout:'form',
							items:[{							
								xtype:'textfield',
								fieldLabel:'产品名称',
								disabled:true,
								name:'a0',
								anchor:'90%'
							
						}]
					},{
							columnWidth:.5,
							layout:'form',
							items:[{
								xtype:'textfield',
								fieldLabel:'购买金额',
								disabled:true,
								name:'a1',
								anchor:'90%'
					   }]
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'风险类别',
								disabled:true,
								name:'a2',
								anchor:'90%'
						   }]
						
					},{


						   columnWidth:1,
						   layout:'form',
						   items:[{
							   	xtype:'textarea',
							   	disabled:true,
								fieldLabel:'推荐理由',								
								name:'a3',
								anchor:'95%'
						   }]
						
					
					
					}]
				}]
			});
	var win = new Ext.Window({
						width:540,
						height:200,
						closeAction:'hide',
						closable:true,
						maximizable:true,
						buttonAlign:'center',
						border:false,
						layout:'fit',
						draggable:true,
						collapsible:true,
						titleCollapse:true,
						items:[editForm],
						buttons:[{
							text:'关闭',
							handler:function(){
								win.hide();
							}
						}]
					}
					);
	//精品选择
	var ruleSetForm1 = new Ext.form.FormPanel({
	    frame : true,
	    buttonAlign : "center",
	    region : 'north',
	    autoScroll : true,
	    height:100,
	    labelWidth : 100,
	    items:[{
	    	layout : 'column',
	        items:[
	                {columnWidth : .5,
		            layout : 'form',
		            items :[{
		            	xtype : 'textfield',
						fieldLabel : '<span style="color:red">*</span>产品编号',
						name : 'scoreName',
						labelStyle: 'text-align:right;',
						allowBlank:false,
						anchor : '100%'
		            }]
	        	
	                },
	                {columnWidth : .5,
	    	            layout : 'form',
	    	            items :[{
			            	xtype : 'textfield',
							fieldLabel : '<span style="color:red">*</span>产品名称',
							name : 'scoreName',
							labelStyle: 'text-align:right;',
							allowBlank:false,
							anchor : '100%'
			            }]
	            	
	                    }
	                ]
	    }],
		buttonAlign : 'center',
			buttons : [ {
				text : '查询',
				handler : function() {

				}
			}, {
				text : '重置',
				handler : function() {
					ruleSetForm1.form.reset();
				}
			}]
	});

	var fields2 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}];

	//定义自动当前页行号
	var num2 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm2 = new Ext.grid.CheckboxSelectionModel();

	var columns2 = new Ext.grid.ColumnModel([num2, sm2,{
		dataIndex : 'a0',
		header : '产品编号',
		sortable : true,
		width : 180
	}, {
		dataIndex : 'a1',
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '起购金额',
		renderer: money('0,000.00'),
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a3',
		header : '推荐原因',
		sortable : true,
		width : 140
	}]);



	var data2 = [
	            ['101','得利宝天添利Ａ款','1000000','收益可观'],
	            ['102','得利宝天添利B款','1000000','****'],
	            ['103','得利宝天添利C款','100000','****'],
	            ['104','天添利A款至尊版','2000000','****'],
	            ['105','天添利D款至尊版','100000','****']
	];

	//每页显示条数下拉选择框
	var combo2 = new Ext.form.ComboBox({
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

	var store2 = new Ext.data.ArrayStore({
		fields : fields2,
		data : data2
	});

	var number2 = parseInt(combo2.getValue());

	//分页工具栏
	var bbar2 = new Ext.PagingToolbar({
		pageSize : number2,
		store : store2,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', combo2]
	});


	var ruleGrid1 = new Ext.grid.GridPanel({
			region : 'center',
			frame : true,
			store : store2,
			stripeRows : true,
			cm : columns2,
			sm : sm2,
			buttonAlign : "center",
			bbar : bbar2
	});

	var ruleSetPanel1 = new Ext.Panel({
	    layout : 'border',
	    autoScroll : true,
	    buttonAlign : "center",
	    items : [ ruleSetForm1,ruleGrid1],
	    buttons : [{ 
		     text : '添加', 
		     handler  : function() {
		    	 var index =  ruleGrid1.getSelectionModel().getSelections().length;
                 if (index<1) {
                 	Ext.Msg.alert('提示',"请选择一条记录");
                     return false;
                 }
                for(var i=0;i<index;i++){
                	var racod=ruleGrid1.getSelectionModel().getSelections()[i];
                 var name=racod.data.a1;	                
                	 var p=new Ext.data.Record({
							a0:name,
			                 a1:'',
			                 a2:'B类',
			                 a3:''
						});
                	 productGrid.stopEditing();
					 store1.insert(0,p);
					 productGrid.startEditing(0,0);
                };
            
		    	 Ext.MessageBox.alert('系统提示信息', '操作成功');
		    	 win1.hide();
				}
		    }, {
				text : '关闭',
				handler  : function() {
					win1.hide();
				}
			} ]
	});
	
	var win1 = new Ext.Window({
		title:'精选产品',
		layout : 'fit',
	    autoScroll : true,
	    draggable : true,
	    closable : true,
	    closeAction : 'hide',
	    modal : true,
	    width : 700,
	    height : 400,
	    loadMask : true,
	    border : false,
	    items : [ {
	        buttonAlign : "center",
	        layout : 'fit',
	        items : [ruleSetPanel1]
	    }]
	});
	// 列表中的复选框
	var prosm = new Ext.grid.CheckboxSelectionModel();
	// 列表记录序号
	var prorownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	//产品推荐
	var fields1 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	},{
		name : 'a3'
	}];



	var columns1 = new Ext.grid.ColumnModel([prorownum,prosm,{
		dataIndex : 'a0',
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '购买金额',
		sortable : true,
		renderer: money('0,000.00'),
		align:'right',
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '风险类别',
		sortable : true,
		width : 140
		//editor : new Ext.form.Field()
	}, {
		dataIndex : 'a3',
		header : '推荐理由',
		sortable : true,
		width : 250,
		editor : new Ext.form.Field()
	}]);

	var data1 = [
	            ['得利宝天添利Ａ款','100000','A类','投资小，收效快'],
	            ['得利宝天添利B款','100000','A类','效益高']
	];

	var store1 = new Ext.data.ArrayStore({
		fields : fields1,
		data : data1
	});

	var tbar = new  Ext.Toolbar(
			{
				id:'tbar',
				items : [{
		            text : '从产品库选择',
		            iconCls:'addIconCss',
		            handler:function() {
		        		show();
		        }},'-',{
		            text : '从精选库选择',
		            iconCls:'addIconCss',
		            handler:function() {
		            	win1.show();
		        }},'-',{
		        	text:'详情',
		        	iconCls : 'detailIconCss',
		        	handler:function(){
		        		 var index =  productGrid.getSelectionModel().getSelected();;
		                 if (index==null || index=="undefined") {
		                 	Ext.Msg.alert('提示',"请选择一条记录");
		                     return false;
		                 }else{
		                	 editForm.getForm().loadRecord(index);
		                	 win.setTitle('产品详情');
							 win.show();
		                 }
		        	}
		        },'-',{
		            text : '删除',
		            iconCls:'deleteIconCss',
		            handler:function() {
		            	 var index =  productGrid.getSelectionModel().getSelected();;
		                 if (index==null || index=="undefined") {
		                 	Ext.Msg.alert('提示',"请选择记录");
		                     return false;
		                 }
							var Nodes = productGrid.getSelectionModel().getSelections().length;
							for(var i=Nodes-1;i>=0;i--){
//								var sm=productGrid.getSelectionModel();
//								var record=store1.getAt(i);
								var record=productGrid.getSelectionModel().getSelections()[i];
								store1.remove(record);
							};
		                 Ext.Msg.alert('提示',"删除成功");
		        }}]
			});
	
	
	var productGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:300,
		frame : true,
		store : store1,
		stripeRows : true,
		tbar:tbar,
		sm:prosm,
		cm : columns1
});
	var win_tjcp=new Ext.Window({
		title:'推荐产品',
		layout : 'fit',
	    autoScroll : true,
	    draggable : true,
	    closable : true,
	    closeAction : 'hide',
	    modal : true,
	    width : 750,
	    height : 350,
	    loadMask : true,
	    border : false,
	    buttonAlign:'center',
	    items:[productGrid],
	    buttons:[{
	    	text:'关闭 ',
	    	handler:function(){
	    		win_tjcp.hide();
	    	}
	    }]
	
	});
