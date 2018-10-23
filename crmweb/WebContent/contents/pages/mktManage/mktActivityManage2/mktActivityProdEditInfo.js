  	var pid='';//定义productId传递参数
	 var myData1 = [
['123','龙凤呈祥20G','秦青','2013-2-28'],
['456','龙凤呈祥50G','秦青','2013-2-27']
];
	 
	//产品放大镜
	var prodEditCode1 = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '目标营销产品',
		name : 'productName',
		hiddenName : 'aimProd',
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		var prodCode = productContrastForm1.form.findField('aimProd').getValue();
		productContrastForm1.form.findField('prodCode').setValue(prodCode);
	}
		});  

    var productContrastRecord1 = Ext.data.Record.create(
    		[
    		 {name:'aimProdId',mapping:'AIM_PROD_ID'},
    		 {name:'createUser',mapping:'CREATE_USER'},
    		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
    		 {name:'createDate',mapping:'CREATE_DATE'},
    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'},
    		 {name:'productId',mapping:'PRODUCT_ID'},
    		 {name:'productName',mapping:'PRODUCT_NAME'}
    		 ]
    );
	
	var productContrastStore1 =  new Ext.data.ArrayStore({
        fields: [
	                  {name: 'productId'},
	                  {name: 'productName'},
	                  {name: 'createUserName'},
	                  {name: 'createDate'}
	               ]
	           });
	productContrastStore1.loadData(myData1);
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
			         [ 50, '50条/页' ],[ 100, '100条/页' ]  ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
	});

	var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
			pageSize : parseInt(pagesize_combo.getValue()),
			store : productContrastStore1,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});


	 var productContrastGrid1 = new Ext.grid.GridPanel({			
			store:productContrastStore1, 
			frame:true,
			height : 200,
			columns:[	{ header:'产品编号',dataIndex:'productId',width:150},
						{ header:'产品名称',dataIndex:'productName',width:150},
						{ header:'创建人',dataIndex:'createUserName',width:160},
						{ header:'创建日期',dataIndex:'createDate',width:160}
						],
			stripeRows : true,
			tbar:[
			      { text:'新增',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	addProductContrastWind1.show();
			    	  	addProductContrastWind1.setTitle('产品对照关系新增');
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = productContrastGrid1.getSelectionModel().getSelections().length;
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
									return;
									} 
								});
						}}
			      }],
			      width: '200',
				bbar : bbar
				});
	 var productContrastGrid1 = new Ext.grid.GridPanel({			
			store:productContrastStore1, 
			frame:true,
			height : 200,
			columns:[	{ header:'产品编号',dataIndex:'productId',width:150},
						{ header:'产品名称',dataIndex:'productName',width:150},
						{ header:'创建人',dataIndex:'createUserName',width:160},
						{ header:'创建日期',dataIndex:'createDate',width:160}
						],
			stripeRows : true,
			      width: '200',
				bbar : bbar
				});
	 var productContrastGrid2 = new Ext.grid.GridPanel({			
			store:productContrastStore1, 
			frame:true,
			height : 200,
			columns:[	{ header:'产品编号',dataIndex:'productId',width:150},
						{ header:'产品名称',dataIndex:'productName',width:150},
						{ header:'创建人',dataIndex:'createUserName',width:160},
						{ header:'创建日期',dataIndex:'createDate',width:160}
						],
			stripeRows : true,
			      width: '200',
			      tbar:[
					      { text:'新增',
					    	iconCls:'addIconCss',
					       handler:function(){
					    	  	addProductContrastWind1.show();
					    	  	addProductContrastWind1.setTitle('产品对照关系新增');
					      }
					      },{
					    	text:'删除',
					    	iconCls:'deleteIconCss',
					    	handler:function(){
								 var selectLength = productContrastGrid2.getSelectionModel().getSelections().length;
								if(selectLength < 1){
									Ext.Msg.alert('提示','请选择需要删除的记录!');
								} else {
										Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
											if(buttonId.toLowerCase() == "no"){
											return;
											}  else{
												Ext.Msg.alert('提示','删除成功!');
											}
										});
								}}
					      }],
				bbar : bbar
				});
	 
	 /****************************************产品对照关系信息*************************************************/
	 var productContrastForm1 = new Ext.form.FormPanel({
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
	    		 items : [{
	    			 name : 'prodCode',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 fieldLabel : '产品编号'
	    		 },prodEditCode1]
	    	 }]
		 }]
	 });
	 

	 
	 
	var addProductContrastWind1 = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[productContrastForm1],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 addProductContrastWind1.hide();
		         }
		         },
		         {
		        	 text:'重置',
		        	 handler:function(){
		        	 	productContrastForm1.getForm().reset();
		         	}
		         }
		        ]
	});
	/******************************展示详情*************************************/
	 var detailproductContrastForm = new Ext.form.FormPanel({
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
	    		 items : [  {
	    			 name : 'productId',
	    			 id:'id1',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 fieldLabel : 'id',
	    			 hidden : true
	    		 },{
	    			 id : 'productId',
	    			 name : 'productId',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 fieldLabel : '产品编号'
	    		 }]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [{
	    			 name:'productName',
	    			 xtype:'textfield',
	    			 readOnly:true,
	    			 fieldLabel : '产品名称',
	    			 allowBlank : false,
	    			 anchor : '90%'
	    		 }]
	    	 }
	    	 ]
		 },{
			 layout : 'form',
			 buttonAlign : 'center',
			 items : [ {
				 name : 'relDesc',
				 xtype : 'textarea',
				 readOnly:true,
				 value:'该产品为最新推出......',
				 fieldLabel : '描述',
				 anchor : '95%'
			 }]
			}]
	 });
	var detailProductContrastWind = new Ext.Window({//展示详情的window
		title:'产品详情',
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[detailproductContrastForm],
		buttons:[{
					text:'返回',
					handler:function(){
						detailProductContrastWind.hide();
					}
		}]
	});
	
	 /****************************修改方法*************************************/

	var update = function() {
		var record = productContrastGrid1.getSelectionModel().getSelected();
		if(!record){
			Ext.MessageBox.alert('提示', '请选择要修改的一列！');
		}
		else{
			addProductContrastWind1.show();
			addProductContrastWind1.setTitle('产品对照关系修改');
			productContrastForm1.getForm().loadRecord(record);
			var selectedRow1 = productContrastGrid1.selModel.getSelections();
			productId = selectedRow1[0].data.productId;
			productContrastStore1.load({
				params : {
					'productId':productId
				}
			});
		}
	};
	
	//展示详细信息窗口
	function showInit1() {
		// 得到选中记录
		var selectRe = productContrastGrid1.getSelectionModel().getSelections()[0];
			detailproductContrastForm.getForm().loadRecord(selectRe);
			detailProductContrastWind.show();
			
	}
	 productContrastGrid1.on('rowdblclick', function(grid, rowIndex, event) {
		 showInit1();
	});
	 
	//展示详细信息窗口
		function showInit11() {
			// 得到选中记录
			var selectRe = productContrastGrid1.getSelectionModel().getSelections()[0];
				detailproductContrastForm.getForm().loadRecord(selectRe);
				detailProductContrastWind.show();
				
		}
		 productContrastGrid1.on('rowdblclick', function(grid, rowIndex, event) {
			 showInit11();
		});
