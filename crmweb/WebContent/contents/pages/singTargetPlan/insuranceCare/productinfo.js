	
	//产品库选择
//	var win = new Ext.Window({
//		title : '添加产品',
//		height:'200',
//		width:'500',
//		modal : true,//遮罩
//		buttonAlign:'center',
//		layout:'fit',
//		items:[{	
//			xtype : 'panel',
//			layout : 'column',
//			frame : true,
//				items : [{
//					columnWidth : .5,
//					layout : 'form',
//					items : [ new Com.yucheng.crm.common.ProductManage({
//						xtype : 'productChoose',
//						fieldLabel : '产品选择',
//						labelStyle : 'text-align:right;',
//						name : 'prodName',
//						hiddenName : 'prodId',
//						singleSelect : false,
//						allowBlank : false,
//						blankText : '此项为必填项，请检查！',
//						anchor : '90%'
//					})]
//				}
//				]}],
//		buttons:[
//		         {
//		        	 text:'添加',
//		        	 handler: function(){
//		    					Ext.MessageBox.alert('系统提示信息', '操作成功');
//		    					win.hide();
//					 }
//					
//		         }, {
//		        	 text:'关闭',
//		        	 handler:function(){
//		        		 win.hide();
//		         	}
//		         }]
//	});
	
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
						fieldLabel : '产品编号',
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
							fieldLabel : '产品名称',
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
		width : 100
	}, {
		dataIndex : 'a1',
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '起购金额',
		sortable : true,
		align : 'right',
		width : 100
	}, {
		dataIndex : 'a3',
		header : '推荐原因',
		sortable : true,
		width : 140
	}]);



	var data2 = [
	            ['101','得利宝天添利Ａ款','1，000，000','收益可观'],
	            ['102','得利宝天添利B款','1，000，000','收益可观'],
	            ['103','得利宝天添利C款','100，000','收益可观'],
	            ['104','天添利A款至尊版','2，000，000','收益可观'],
	            ['105','天添利D款至尊版','100，000','收益可观']
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
		    	 var records = ruleGrid1.selModel.getSelections();// 得到被选择的行的数组
         	var selectLength = records.length;
            if (selectLength <= 0 ) {
            	alert("请选择产品");
                return false;
            }
            var tempName = '';
            for(var i = 0; i<selectLength;i++)
				{
					selectRe = ruleGrid1.getSelectionModel().getSelections()[i];
					tempName = selectRe.data.a1;
					 var u = new store1.recordType({
					    	"a0" :tempName,             
							"a1" :"",
							"a2" :""
					    });
					 productGrid.stopEditing();
					 store1.insert(0, u);
					 productGrid.startEditing(0, 0);
				}
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
	    width : 600,
	    height : 400,
	    loadMask : true,
	    border : false,
	    items : [ {
	        buttonAlign : "center",
	        layout : 'fit',
	        items : [ruleSetPanel1]
	    }]
	});
	//产品介绍
	//产品介绍
	var dateails = new Ext.form.FormPanel({
	    frame : true,
	    buttonAlign : "center",
	    region : 'center',
	    autoScroll : true,
	    labelWidth : 100,
	    items:[{
	    	layout : 'column',
	        items:[
	                {columnWidth : .5,
		            layout : 'form',
		            items :[{
		            	xtype : 'textfield',
						fieldLabel : '产品编号',
						name : 'scoreName',
						value:'CP001',
						disabled:true,
						labelStyle: 'text-align:right;',
						allowBlank:false,
						anchor : '90%'
		            },{
		            	xtype : 'textfield',
						fieldLabel : '风险等级',
						name : 'scoreName',
						value:'中',
						disabled:true,
						labelStyle: 'text-align:right;',
						allowBlank:false,
						anchor : '90%'
		            }]
	        	
	                },
	                {columnWidth : .5,
	    	            layout : 'form',
	    	            items :[{
			            	xtype : 'textfield',
							fieldLabel : '产品名称',
							name : 'scoreName',
							disabled:true,
							value:'得利宝天天利A款',
							labelStyle: 'text-align:right;',
							allowBlank:false,
							anchor : '90%'
			            },{
			            	xtype : 'textfield',
							fieldLabel : '逾期收益',
							name : 'scoreName',
							disabled:true,
							value:'4%',
							labelStyle: 'text-align:right;',
							allowBlank:false,
							anchor : '90%'
			            }]
	            	
	                    },
	                    {columnWidth : 1,
		    	            layout : 'form',
		    	            items :[{
				            	xtype : 'textarea',
								fieldLabel : '产品介绍',
								name : 'scoreName',
								disabled:true,
								value:'得利宝天天利A......',
								labelStyle: 'text-align:right;',
								allowBlank:false,
								anchor : '95%'
				            }]
		            	
		                    }
	                ]
	    }]
	});
	var dateail = new Ext.Panel({
	    layout : 'border',
	    autoScroll : true,
	    buttonAlign : "center",
	    items : [ dateails],
	    buttons : [ {
				text : '关闭',
				handler  : function() {
					win2.hide();
				}
			} ]
	});
	var win2 = new Ext.Window({
		title:'产品介绍',
		layout : 'fit',
	    autoScroll : true,
	    draggable : true,
	    closable : true,
	    closeAction : 'hide',
	    modal : true,
	    width : 600,
	    height : 240,
	    loadMask : true,
	    border : false,
	    items : [ {
	        buttonAlign : "center",
	        layout : 'fit',
	        items : [dateail]
	    }]
	});
	//产品推荐
	var fields1 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];


	var sm = new Ext.grid.CheckboxSelectionModel();
	var columns1 = new Ext.grid.ColumnModel([sm,{
		dataIndex : 'a0',
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '购买金额',
		align : 'right',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '推荐理由',
		sortable : true,
		width : 300,
		editor : new Ext.form.Field()
	}]);

	var data1 = [
	            ['得利宝天添利Ａ款','100,000','投资小，收效快'],
	            ['得利宝天添利B款','100,000','效益高']
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
		            	productManageInfoStore.load({
		        			params:{
		        				start:0,
		        				limit: 100
		        			}
		        		});

		            	productManageWindow.show();
		        }},{
		            text : '从精选库选择',
		            iconCls:'addIconCss',
		            handler:function() {
		            	win1.show();
		        }},{
		            text : '产品介绍',
		            iconCls:'detailIconCss',
		            handler:function() {
		            	var records = productGrid.selModel.getSelections();// 得到被选择的行的数组
		            	var selectLength = records.length;
		                 if (selectLength != 1) {
		                 	alert("请选择一条记录");
		                     return false;
		                 }
		            	win2.show();
		        }},{
		            text : '删除',
		            iconCls:'deleteIconCss',
		            handler:function() {
		            	var records = productGrid.selModel.getSelections();// 得到被选择的行的数组
		            	var selectLength = records.length;
		            	
		                 if (selectLength < 1) {
		                 	alert("请选择记录");
		                     return false;
		                 }
		                 var selectRe ;
		                 for(var i = selectLength-1; i>=0;i--)
							{
								selectRe = productGrid.getSelectionModel().getSelections()[i];
								store1.remove(selectRe);
							}
		                 alert("删除成功");
		        }}]
			});
	
	
	var productGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:300,
		frame : true,
		store : store1,
		stripeRows : true,
		tbar:tbar,
		cm : columns1,
		sm:sm
});
