  	var pid='';//定义custId传递参数
    
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

    var xlStore = new Ext.data.ArrayStore( {
        fields : [ 'myId', 'displayText' ],
        data : [ [ 'ATM', 'ATM' ], [ '大堂', '大堂' ],
                [ '呼叫中心', '呼叫中心' ], [ '柜台', '柜台' ], [ '内部推荐', '内部推荐' ]]
    });
    
	 var myData3 = [
['123','渠道(大堂)','秦青','2013-2-28'],
['456','渠道(手机银行)','秦青','2013-2-28']
];

		
	var chanelContrastStore1 =  new Ext.data.ArrayStore({
        fields: [
	                  {name: 'productId'},
	                  {name: 'productName'},
	                  {name: 'createUser'},
	                  {name: 'createDate'}
	               ]
	           });
	chanelContrastStore1.loadData(myData3);
	
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
			store : chanelContrastStore1,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});


	 /*************************************列模型***********************************************/
	 var chanelContrastGrid1 = new Ext.grid.GridPanel({			
			store:chanelContrastStore1, 
			frame:true,
			height : 200,
			columns:[	
					{ header:'渠道编号',dataIndex:'productId',width:150},
					{ header:'渠道名称',dataIndex:'productName',width:160},
					{ header:'创建人',dataIndex:'createUser',width:160},
					{ header:'创建时间',dataIndex:'createDate',width:160}
						],
			region:'center',
			tbar:[
			      { text:'新增',
			    	iconCls:'addIconCss',
			       handler:function(){
			    	  	addchanelContrastWind1.show();
			    	  	addchanelContrastWind1.setTitle('渠道新增');
			      }
			      },{
			    	text:'删除',
			    	iconCls:'deleteIconCss',
			    	handler:function(){
						 var selectLength = chanelContrastGrid2.getSelectionModel().getSelections().length;
						if(selectLength < 1){
							Ext.Msg.alert('提示','请选择需要删除的记录!');
						} else {
								Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
									if(buttonId.toLowerCase() == "no"){
										return;
										}   else{
											Ext.Msg.alert('提示','删除成功!');
										}
								});
						}}
			      }],
			      bbar:bbar
	 });
	 var chanelContrastGrid1 = new Ext.grid.GridPanel({			
			store:chanelContrastStore1, 
			frame:true,
			height : 200,
			columns:[	
					{ header:'渠道编号',dataIndex:'productId',width:150},
					{ header:'渠道名称',dataIndex:'productName',width:160},
					{ header:'创建人',dataIndex:'createUser',width:160},
					{ header:'创建时间',dataIndex:'createDate',width:160}
						],
			region:'center',
			      bbar:bbar
	 });
	 var chanelContrastGrid2 = new Ext.grid.GridPanel({			
			store:chanelContrastStore1, 
			frame:true,
			height : 200,
			columns:[	
					{ header:'渠道编号',dataIndex:'productId',width:150},
					{ header:'渠道名称',dataIndex:'productName',width:160},
					{ header:'创建人',dataIndex:'createUser',width:160},
					{ header:'创建时间',dataIndex:'createDate',width:160}
						],
						tbar:[
						      { text:'新增',
						    	iconCls:'addIconCss',
						       handler:function(){
						    	  	addchanelContrastWind1.show();
						    	  	addchanelContrastWind1.setTitle('渠道新增');
						      }
						      },{
						    	text:'删除',
						    	iconCls:'deleteIconCss',
						    	handler:function(){
									 var selectLength = chanelContrastGrid2.getSelectionModel().getSelections().length;
									if(selectLength < 1){
										Ext.Msg.alert('提示','请选择需要删除的记录!');
									} else {
											Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
											});
									}}
						      }],			
			region:'center',
			      bbar:bbar
	 });
	 var chanelContrastForm1 = new Ext.form.FormPanel({
		 labelWidth : 80,
		 height : 200,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items : [ {
	    	 layout : 'column',
	    	 items : [/* {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [  {
	    			 name : 'productId',
	    			 xtype : 'textfield',
	    			 fieldLabel : '渠道编号',
	    			 value:'123'
	    			 
	    		 }]
	    	 },*/ {
	    		 columnWidth : .5,
	    		 layout : 'form',
	    		 items : [ {
	                    fieldLabel : '渠道名称',
	                    name : 'EDUCATIONLEVEL',
	                    forceSelection : true,
	                    resizable : true,
	                    xtype : 'combo',
	                    labelStyle : 'text-align:right;',
	                    triggerAction : 'all',
	                    mode : 'local',
	                    store : xlStore,
	                    valueField : 'myId',
	                    displayField : 'displayText',
	                    emptyText : '请选择',
	                    anchor : '95%'
	                }]
	    	 }
	    	 ]
		 }]
	 });
	var addchanelContrastWind1 = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[chanelContrastForm1],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler: function(){
		        	 addchanelContrastWind1.hide();
		         }
		         },
		         {
		        	 text:'重置',
		        	 handler:function(){
		        	 	chanelContrastForm1.getForm().reset();
		         	}
		         }
		        ]
	});
	/******************************展示详情*************************************/
	 var detailchanelContrastForm = new Ext.form.FormPanel({
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
	    			 readOnly:true,
	    			 fieldLabel : 'id',
	    			 hidden : true
	    		 },{
	    			 name : 'productId',
	    			 xtype : 'textfield',
	    			 readOnly:true,
	    			 fieldLabel : '产品id',
	    			 hidden : true
	    		 },{
	    			 name : 'relType',
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
	var detailchanelContrastWind = new Ext.Window({//展示详情的window
		title:'产品对照关系详情',
		closeAction:'hide',
		height:'200',
		width:'500',
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[detailchanelContrastForm],
		buttons:[{
					text:'返回',
					handler:function(){
						detailchanelContrastWind.hide();
					}
		}]
	});
	
	 /****************************修改方法*************************************/

	var update = function() {
		debugger;
		var record = chanelContrastGrid1.getSelectionModel().getSelected();
		if(!record){
			Ext.MessageBox.alert('提示', '请选择要修改的一列！');
		}
		else{
			addchanelContrastWind1.show();
			addchanelContrastWind1.setTitle('产品对照关系修改');
			chanelContrastForm1.getForm().loadRecord(record);
			var selectedRow1 = chanelContrastGrid1.selModel.getSelections();
			chanelId = selectedRow1[0].data.chanelId;
			chanelContrastStore1.load({
				params : {
					'productId':chanelId,
					'querysign':'chanelomer'
				}
			});
		}
	};
	
	//展示详细信息窗口
	function showInit3() {
		// 得到选中记录
			detailchanelContrastWind.show();
	}
//	 chanelContrastGrid1.on('rowdblclick', function(grid, rowIndex, event) {
//		 showInit3();
//	});
