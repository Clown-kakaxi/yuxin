/**
 * 
 * 产品参数维护功能
 * 
 */

Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	//产品放大镜
	var prodEditCode = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '产品选择',
		name : 'a1',
		labelStyle: 'text-align:right;',
		hiddenName : 'aimProd',
		allowBlank : false,
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		}
	}); 
	
	
	//查询panel
	var prodParaSearchPanel = new Ext.form.FormPanel({
		title:'产品管理  > 产品参数维护',
		region:'north',
		height:100,
		labelWidth:150,//label的宽度
		labelAlign:'right',
		frame:true,
		autoScroll : true,
		region:'north',
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth: .25,
				layout:'form',
				items:[prodEditCode]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'风险级别',anchor:'100%',store : a2,displayField : 'value',valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'市场方向',anchor:'100%',store : a5,displayField : 'value',valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'是否新品',anchor:'100%',store : a6,displayField : 'value',valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{text:'查询'},{text:'重置',
			handler:function(){prodParaSearchPanel.getForm().reset();}
		}]
	});
	
	
	//数据源 DEMO
	var prodParaFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}, {name : 'a3'}, {name : 'a4'},
	                  {name : 'a5'}, {name : 'a6'}, {name : 'a7'}, {name : 'a8'}, {name : 'a9'},
	                  {name : 'a10'}, {name : 'a11'}, {name : 'a12'}, {name : 'a13'}, {name : 'a14'},
	                  {name : 'a15'}, {name : 'a16'}, {name : 'a17'}, {name : 'a18'},{name:'a19'}];
	var prodParaData = [
	            ['PD001','产品001','2级','中','0.8','货币市场方向','是','2013-5-6','推荐理由信息内容','是','2013-8-7','2013-8-7','2014-8-7','','全行','是','是','优惠信息介绍','2013-9-12','001'],
	            ['PD002','产品002','3级','中','0.7','其他市场方向','是','2013-5-6','推荐理由信息内容','是','2013-8-7','2013-8-7','2014-8-7','','全行','是','是','优惠信息介绍','2013-9-12','002'],
	            ['PD003','产品003','4级','中','0.7','固定收益市场方向','是','2013-5-6','推荐理由信息内容','是','2013-8-7','2013-8-7','2014-8-7','','全行','是','是','优惠信息介绍','2013-9-12','003'],
	            ['PD004','产品004','5级','高','0.7','股票市场方向','是','2013-5-6','推荐理由信息内容','是','2013-8-7','2013-8-7','2014-8-7','','全行','是','是','优惠信息介绍','2013-9-12','004'],
	            ['PD005','产品005','1级','低','0.7','货币市场方向','是','2013-5-6','推荐理由信息内容','是','2013-8-7','2013-8-7','2014-8-7','','全行','是','是','优惠信息介绍','2013-9-12','005']
	];
	
	//定义自动当前页行号
	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	
	//gridtable中的列定义
	var prodParaColumns = new Ext.grid.ColumnModel([num,sm,
   		{header:'产品代码',dataIndex:'a0',id:"productId",sortable : true},
   		{header:'产品名称',dataIndex:'a1',id:'productName',sortable : true},
   		{header:'产品树代码',dataIndex:'a19',id:'productName',sortable : true},
   		{header:'风险级别',dataIndex:'a2',id:'catlName',sortable : true},	
   		{header:'流动性',dataIndex:'a3',id:'productStartDate',sortable : true},
   		{header:'收益率',dataIndex:'a4',id:'productEndDate',sortable : true},   	
   		{header:'市场方向',dataIndex:'a5',id:"productId",sortable : true},
   		{header:'是否推荐',dataIndex:'a6',id:'productName',sortable : true},
   		{header:'推荐结束日期',dataIndex:'a7',id:'catlName',sortable : true},	
   		{header:'推荐理由',dataIndex:'a8',id:'productStartDate',sortable : true},
   		{header:'是否新品',dataIndex:'a9',id:'productEndDate',sortable : true}, 
   		
   		{header:'新品结束日期',dataIndex:'a10',id:"productId",sortable : true},
   		{header:'上架日期',dataIndex:'a11',id:'productName',sortable : true},
   		{header:'下架日期',dataIndex:'a12',id:'catlName',sortable : true},	
   		{header:'上级分类',dataIndex:'a13',id:'productStartDate',sortable : true},
   		{header:'销售范围',dataIndex:'a14',id:'productEndDate',sortable : true}, 
   		{header:'是否行内优惠',dataIndex:'a15',id:"productId",sortable : true},
   		{header:'是否优惠',dataIndex:'a16',id:'productName',sortable : true},
   		{header:'优惠信息',dataIndex:'a17',id:'catlName',sortable : true},	
   		{header:'优惠结束日期',dataIndex:'a18',id:'productStartDate',sortable : true}
   	]);
	
	var prodParaStore = new Ext.data.ArrayStore({
		fields : prodParaFields,
		data : prodParaData
	});
	
	//***********************
	// 每页显示条数下拉选择框
	var spagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],[ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});

	// 分页工具栏
	var sbbar = new Ext.PagingToolbar({
		pageSize : parseInt(spagesize_combo.getValue()),
		store : prodParaStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	//***********************
	
	//理财产品查询结果grid
	var prodParaGrid =  new Ext.grid.GridPanel({
		frame:true,
		id:'prodParaGrid',
		store:prodParaStore,
		region:'center',
		loadMask:true,
		cm :prodParaColumns,
    	bbar:sbbar, //分页工具栏
    	viewConfig:{
   	       //forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
    		autoScroll : true,
    		scrollOffset:2 // Grid按钮将不会显示上下滚动条
   		},
    	tbar:[{
    		text:'新增',
    		iconCls:'addIconCss',
    		handler:function() {
    			addProdParaPanel.getForm().reset();
    			addProdParaWin.show();
    			addProdParaPanel.getForm().reset();
    			Ext.getCmp("saveID").setVisible(true);
    		}	
    	},'-',{
	    	text:'查看',
			iconCls:'detailIconCss',
			handler : function() {
				var selectLength = prodParaGrid.getSelectionModel().getSelections().length;
				var selectRe = prodParaGrid.getSelectionModel().getSelections()[0];
				if (selectLength != 1) {
					Ext.Msg.alert("提示", "请选择一条记录!");
				}else {
					addProdParaPanel.getForm().loadRecord(selectRe);
					addProdParaWin.show();
					Ext.getCmp("saveID").setVisible(false);
				}
			}
    	},'-',{
    		text:'编辑',
    		iconCls:'editIconCss',
    		handler : function() {
				var selectLength = prodParaGrid.getSelectionModel().getSelections().length;
				var selectRe = prodParaGrid.getSelectionModel().getSelections()[0];
				if (selectLength != 1) {
					Ext.Msg.alert("提示", "请选择一条记录!");
				}else {
					addProdParaPanel.getForm().loadRecord(selectRe);
					Ext.getCmp("saveID").setVisible(true);
					addProdParaWin.show();
				}
			}
    	},'-',{
    		text:'删除',
    		iconCls:'deleteIconCss',
    		handler:function() {
				var onGrid = Ext.getCmp("prodParaGrid").getSelectionModel();
				if (onGrid.getSelections()) {
					var records = onGrid.getSelections();// 选择行的个数 
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						Ext.Msg.alert('提示', '删除成功');
					}
				}
			}
    	},'-',{
    		 text:'附件信息',
             iconCls:'youJiIconCss',
             handler:function() {
 				var onGrid = Ext.getCmp("prodParaGrid").getSelectionModel();
 				if (onGrid.getSelections()) {
 					var records = onGrid.getSelections();// 选择行的个数 
 					var recordsLen = records.length;// 得到行数组的长度
 					if (recordsLen < 1) {
 						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
 					} else {
 						 appendixWindow.show();
 					}
 				}
 			}
    	}],
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
	});
	
	//页面布局
	new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		layout:'border',
		items : [prodParaSearchPanel,prodParaGrid]
	});
	
});