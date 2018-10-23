/**
 * 产品对照关系
 * @author ZSXIN
 * @since 2012-11-20
 */    

	var pid='';//定义productId传递参数
    
    var relTypeStore = new Ext.data.Store({//对照类型的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=CON_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});   

    var productContrastRecord = Ext.data.Record.create(
    		[
    		 {name:'id',mapping:'ID'},
    		 {name:'productId',mapping:'PRODUCT_ID'},
    		 {name:'relType',mapping:'REL_TYPE'},
    		 {name:'relTypeOra',mapping:'REL_TYPE_ORA'},
    		 {name:'relDesc',mapping:'REL_DESC'},
    		 {name:'key',mapping:'KEY'}
    		 ]
    );
    var productContrastReader = new Ext.data.JsonReader(//读取jsonReader
    		{
    			successProperty : 'success',
    			idProperty : 'ID',
    			totalProperty : 'json.count',
    			root:'json.data'
    		},productContrastRecord
	);
	var productContrastStore = new Ext.data.Store({//产品对照关系store
	        restful : true, 
	        proxy : new Ext.data.HttpProxy({ 
	        	url:basepath+'/productContrastRelationInfo.json',
	        	method:'get'
	        }),
			reader:productContrastReader
			
	});
	
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
	productContrastStore.on('beforeload', function() {
		this.baseParams = {
				productId:pid
		};
	});
	productContrastStore.reload({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		productContrastStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});

	var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
			pageSize : parseInt(pagesize_combo.getValue()),
			store : productContrastStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});


	 var productContrastColumns = new Ext.grid.ColumnModel(
				{
					columns:[
					{ header:'ID',dataIndex:'id',id:'id',sortable:true,hidden:true},
					{ header:'产品ID',dataIndex:'productId',id:'productId',sortable:true,hidden:true},
					{ header:'对照类型',dataIndex:'relTypeOra',id:'relType',sortable:true,width:150},
					{ header:'对照关键字',dataIndex:'key',sortable:true,width:160},
					{ header:'描述',dataIndex:'relDesc',id:'relDesc',width:160,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/
	 var sm = new Ext.grid.CheckboxSelectionModel();
	 var productContrastGrid = new Ext.grid.EditorGridPanel({
			
			store:productContrastStore, 
			frame:true,
			cm:productContrastColumns,
			region:'center',
			sm:sm,
			tbar:[
			      { text:'新增',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	addProductContrastWind.show();
			    	  	addProductContrastWind.setTitle('产品对照关系新增');
			    	  	productContrastForm.getForm().getEl().dom.reset();
			    	  	Ext.getCmp('productId').setValue(pid);
			    	  	productContrastStore.reload();
			      }
			      }, '-' ,{
			    	text:'修改',
			    	iconCls:'editIconCss',
			    	handler:function(){
			        	 update();
			        	 productContrastStore.reload();
			      	}
			      }, '-' ,{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
			    	  var _record = productContrastGrid.getSelectionModel().getSelected();
							if (!_record) {
								Ext.MessageBox.alert('系统提示信息', '请选择要删除的的信息！');
								return false;
							} else {
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
										return;
									} 
									Ext.Ajax.request({
										url:basepath+'/productContrastRelationInfo!destroy.json',
										method: 'POST',
										params : {
											'idStr' : productContrastGrid.getSelectionModel().getSelected().data.id
										},
										success : function(response) {
											productContrastStore.reload();
											Ext.Msg.alert('提示','信息删除成功' /*response.responseText*/);
										},
										failure : function(response) {
											Ext.Msg.alert('提示','操作失败' /*response.responseText*/);
										}
									});

								});
							}
			      }
			      }],
			      bbar:bbar,
			      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	 			  },
	 			  loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			  }
	 });
	 
	 /****************************************产品对照关系信息*************************************************/
	 var productContrastForm = new Ext.form.FormPanel({
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
	    			 name : 'id',
	    			 xtype : 'textfield',
	    			 fieldLabel : 'id',
	    			 hidden : true
	    		 },{
	    			 id : 'productId',
	    			 name : 'productId',
	    			 xtype : 'textfield',
	    			 fieldLabel : '产品id',
	    			 hidden : true
	    		 },{
	    			 name : 'relType',
	    			 id:'relType2',
	    			 xtype : 'combo',
	    			 fieldLabel : '<font color=red>*</font>对照类型',
	    			 triggerAction : 'all',
	    			 mode : 'local',
	    			 store : relTypeStore,
	    			 editable : false,
	    			 valueField : 'key',
	    			 displayField : 'value',
	    			 emptyText : '请选择',
	    			 allowBlank : false,
	    			 anchor : '90%'
	    		 }]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [{
	    			 name:'key',
	    			 xtype:'textfield',
	    			 fieldLabel : '<font color=red>*</font>对照关键字',
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
				 fieldLabel : '描述',
				 anchor : '95%'
			 }]
			}]
	 });
	var addProductContrastWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[productContrastForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 if (!productContrastForm.getForm().isValid()) {
		        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		        		 return false;
		        	 }
		        	 Ext.Ajax.request({
		        		 url : basepath + '/productContrastRelationInfo.json',
		        		 method : 'POST',
		        		 params : productContrastForm.getForm().getFieldValues(),
		        		 waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		        		 success : function() {
		        		 Ext.Msg.alert('提示', '操作成功');
		        		 productContrastStore.reload();
		        	 },
		        	 failure : function(response) {
		        		 var resultArray = Ext.util.JSON.decode(response.status);
		        		 if (resultArray == 403) {
		        			 Ext.Msg.alert('提示',response.responseText);
		        		 } else {
		        			 Ext.Msg.alert('提示','操作失败,失败原因:'+ response.responseText);
		        		 }
		        	 }
		        	 });
		        	 addProductContrastWind.hide();
		         }
		         },
		         {
		        	 text:'重置',
		        	 handler:function(){
		        	 	productContrastForm.getForm().reset();
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
	    			 name : 'id',
	    			 id:'id1',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 fieldLabel : 'id',
	    			 hidden : true
	    		 },{
	    			 id : 'productId1',
	    			 name : 'productId',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 fieldLabel : '产品id',
	    			 hidden : true
	    		 },{
	    			 name : 'relType',
	    			 id:'relType1',
	    			 xtype : 'combo',
	    			 fieldLabel : '<font color=red>*</font>对照类型',
	    			 triggerAction : 'all',
	    			 mode : 'local',
	    			 store : relTypeStore,
	    			 readOnly:true,
	    			 valueField : 'key',
	    			 displayField : 'value',
	    			 emptyText : '请选择',
	    			 allowBlank : false,
	    			 anchor : '90%'
	    		 }]
	    	 }, {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [{
	    			 name:'key',
	    			 xtype:'textfield',
	    			 readOnly:true,
	    			 fieldLabel : '<font color=red>*</font>对照关键字',
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
				 fieldLabel : '描述',
				 anchor : '95%'
			 }]
			}]
	 });
	var detailProductContrastWind = new Ext.Window({//展示详情的window
		title:'产品对照关系详情',
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
		var record = productContrastGrid.getSelectionModel().getSelected();
		if(!record){
			Ext.MessageBox.alert('提示', '请选择要修改的一列！');
		}
		else{
			addProductContrastWind.show();
			addProductContrastWind.setTitle('产品对照关系修改');
			productContrastForm.getForm().loadRecord(record);
			var selectedRow1 = productContrastGrid.selModel.getSelections();
			productId = selectedRow1[0].data.productId;
			productContrastStore.load({
				params : {
					'productId':productId
				}
			});
		}
	};
	
	//展示详细信息窗口
	function showInit() {
		// 得到选中记录
		var selectRe = productContrastGrid.getSelectionModel().getSelections()[0];
		var selectLength = productContrastGrid.getSelectionModel().getSelections().length;
		if (selectLength != 1) {
			Ext.Msg.alert("提示", "请选择一条记录!");
		} else {
			detailproductContrastForm.getForm().loadRecord(selectRe);
			detailProductContrastWind.show();
		}
	}
	 productContrastGrid.on('rowdblclick', function(grid, rowIndex, event) {
		 showInit();
	});
	
	
	/********************************产品对照关系**************************************/
	var productContrastWindow = new Ext.Window({
		title:'产品对照关系',
		closeAction:'hide',
		height:350,
		width:500,
		buttonAlign:'center',
		maximizable : true,
		layout:'fit',
		modal : true,//遮罩
		buttons:[{
		        	 text:'关闭',
		        	 handler:function()
		        	 {
						productContrastWindow.hide();
		        	 }
		         }],
		        items:[productContrastGrid]
	});






