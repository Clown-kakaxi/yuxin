Ext.onReady(function() {
	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '厂商' ],
				[ 2, '采购商' ],[ 3, '供应商' ] , [ 4, '经销商' ]]
	});
	var fields1 = [ {
		name : 'b1'
	},{
		name : 'b2'
	}, {
		name : 'b3'
	}, {
		name : 'b5'
	}, {
		name : 'b4'
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
	}, {
		dataIndex : 'b3',
		header : '成员类型',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b5',
		header : '是否核心企业',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'b4',
		header : '加入时间',
		sortable : true,
		width : 140
	}]);
	
	var data1 = [
	            ['CNJ2013021800090','河北金星日化用品厂','厂商','是' ,'2012-09-26'],
	            ['CNJ2013021800091','河北金星日化采购中心','采购商','否' ,'2012-09-26'],
	            ['CNJ2013021800092','河北金星日化产品原料厂','供应商','否' ,'2012-09-26'],
	            ['CNJ2013021800093','河北金星日化分销公司','经销商','否','2012-09-26' ],
	            ['CNJ2013021800094','河北金星日化零售公司','经销商','否' ,'2012-09-26']
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
							store : typeStore,
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
	 var memberList = new Ext.grid.GridPanel({
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
				text : '设为核心企业',
				iconCls : 'editIconCss',
				handler : function() {
					var selectLength = memberList
					.getSelectionModel()
					.getSelections().length;

					if (selectLength != 1) {
						Ext.Msg.alert("提示", "请选择一条记录!");
					}else{
						Ext.MessageBox.confirm('提示','此操作将设定所选企业为核心企业，是否确定执行?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							return false;
							}
							Ext.Msg.alert("提示", "操作成功!");
						});
					}	
				}
				},{
						text : '新增',
						iconCls : 'editIconCss',
						handler : function() {
							addcustWin.show();
						}
					},{
						text : '删除',
						iconCls : 'deleteIconCss',
						handler : function() {
							var selectLength = memberList
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
	
	
	
	var view=new Ext.Panel({
		 renderTo:oCustInfo.view_source,		 
		 height:document.body.scrollHeight-30,
		 width:document.body.scrollWidth-200,
		 layout : 'fit',
			frame : true,
			items : [{
				layout : 'border',
				items : [{
					region : 'center',
					id : 'center-panel',
					title : "成员列表",
					layout : 'fit',
					items : [ memberList ]
				}]
			}]
	});
	
});
