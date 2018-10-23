/**
 * 理财产品搜索器
 * @author zkl
 * 
 */
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	//查询panel
	var finaSearchPanel = new Ext.form.FormPanel({
		title:'单目标规划  > 购车规划',
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
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'客户姓名',anchor:'100%'}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'报告名称',anchor:'100%'}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'创建日期',anchor:'100%'}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{text:'查询'},{text:'重置',
			handler:function(){productSearchPanel.getForm().reset();}
		}]
	});
	
	
	//DEMO 数据
	var mesgFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}, {name : 'a3'}, {name : 'a4'}];
	var mesgData = [
	            ['张三购车规划','张三','2013-08-26','2013-08-26','张三购车规划'],
	            ['李四购车规划','李四','2013-07-24','2013-08-26','李四购车规划'],
	            ['五XX购车规划','五XX','2013-08-26','2013-08-26','五XX购车规划'],
	            ['张XX购车规划','张XX','2013-07-26','2013-08-26','张XX购车规划'],
	            ['软三购车规划','软三','2013-11-25','2013-08-26','软三购车规划']
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
	var finaInfoColumns = new Ext.grid.ColumnModel([num,sm,
   		//new Ext.grid.RowNumberer(),
   		{header:'报告名称',dataIndex:'a0',id:"productId",sortable : true},
   		{header:'客户姓名',dataIndex:'a1',id:'productName',sortable : true},
   		{header:'<center>创建日期</center>',dataIndex:'a2',id:'catlName',sortable : true,align:'right'},	
   		{header:'<center>规划开始日期</center>',dataIndex:'a3',id:'productStartDate',sortable : true,align:'right'},
   		{header:'备注',dataIndex:'a4',id:'productEndDate',sortable : true}   		
   	]);
	
	var productInfoStore = new Ext.data.ArrayStore({
		fields : mesgFields,
		data : mesgData
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
		store : productInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	//***********************
	
	//理财产品查询结果grid
	var finaInfoGrid =  new Ext.grid.GridPanel({
		id:'产品列表',
		frame:true,
		id:'finaInfoGrid',
		store:productInfoStore,
		region:'center',
		loadMask:true,
		cm :finaInfoColumns,
		sm : sm,
    	bbar:sbbar, //分页工具栏
    	viewConfig:{
   	       forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
   		},
    	tbar:[{
    		text:'新增',
    		iconCls:'addIconCss',
    		handler:function() {
    			carWin1.show();
    		}	
    	},'-',{
    		text:'编辑',
    		iconCls:'editIconCss',
    		handler:function() {
				var onGrid = Ext.getCmp("finaInfoGrid").getSelectionModel();
				if (onGrid.getSelections()) {
					var records = onGrid.getSelections();// 选择行的个数 
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						carWin1.show();
					}
				}
			}
    	},'-',{
    		text:'删除',
    		iconCls:'deleteIconCss',
    		handler:function() {
				var onGrid = Ext.getCmp("finaInfoGrid").getSelectionModel();
				if (onGrid.getSelections()) {
					var records = onGrid.getSelections();// 选择行的个数 
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						Ext.Msg.alert("系统提示信息", "删除成功！");
					}
				}
			}
    	},'-',{
    		text:'下载报告',
    		iconCls:'detailIconCss',
    		handler:function() {
				var onGrid = Ext.getCmp("finaInfoGrid").getSelectionModel();
				if (onGrid.getSelections()) {
					var records = onGrid.getSelections();// 选择行的个数 
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'+ ' scrollbars=no, resizable=no,location=no, status=no';
    					var fileName = 'readme.txt';// 电子杂志文件名称
    					var uploadUrl = basepath + '/AnnexeDownload?filename='+ fileName + '&annexeName=' + fileName;
    					window.open(uploadUrl, '', winPara);
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
		items : [finaSearchPanel,finaInfoGrid]
	});
	
});