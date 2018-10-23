/**
 * 理财产品搜索器
 * @author zkl
 * 
 */
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	var  openType=  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '是' ],[ 2, '否' ]
		]
	});
	
	var  prodType=  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '理财产品' ],[ 2, '储蓄类产品' ],[3,'保险产品'],[4,'基金产品']
		]
	});
	
	var  ccyType=  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '人民币' ],[ 2, '美元' ],[3,'台币'],[4,'澳元']
		]
	});
	
	//查询panel
	var finaSearchPanel = new Ext.form.FormPanel({
		title:'理财产品搜索器',
		region:'north',
		height:160,
		labelWidth:100,//label的宽度
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
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'发行银行',anchor:'100%'}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'投资币种',anchor:'100%',store : ccyType,valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'预期收益率',anchor:'100%'}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'实际收益率',anchor:'100%'}]
			}]
		},{
			layout:'column',
			items:[{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'产品名称',anchor:'100%'}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'挂钩标的',anchor:'100%',store : openType,valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'产品类型',anchor:'100%',store : prodType,valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'textfield',name:'PRODUCT_ID',fieldLabel:'投资期限(月)',anchor:'100%'}]
			}]
		},{
			layout:'column',
			items:[{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'datefield',name:'PRODUCT_ID',fieldLabel:'募集起始日',anchor:'100%'}]
			},{
				columnWidth: .25,
				layout:'form',
				items:[{xtype:'datefield',name:'PRODUCT_ID',fieldLabel:'募集终止日',anchor:'100%'}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{text:'查询'},{text:'重置',
			handler:function(){productSearchPanel.getForm().reset();}
		}]
	});
	
	
	
	//增长率规划 数据源 DEMO
	var eduFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}, {name : 'a3'}, {name : 'a4'},
	                  {name : 'a5'},{name : 'a6'},{name : 'a7'},{name : 'a8'},{name : 'a9'}];
	var eduData = [
	            ['中国银行','理财产品A','理财产品','人民币','6','是','8','82','2013-6-8','2013-8-9'],
	            ['中国农业银行','理财产品B','理财产品','人民币','6','是','8','82','2013-6-8','2013-8-9'],
	            ['交通银行','理财产品C','理财产品','人民币','6','是','7.2','82','2013-6-8','2013-8-9'],
	            ['民生银行','理财产品D','理财产品','人民币','6','是','8','82','2013-6-8','2013-8-9'],
	            ['中国建设银行','理财产品E','理财产品','人民币','6','是','6.5','82','2013-6-8','2013-8-9']
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
   		{header:'发行银行',dataIndex:'a0',id:"productId",sortable : true},
   		{header:'产品名称',dataIndex:'a1',id:'productName',sortable : true},
   		{header:'产品类型',dataIndex:'a2',id:'catlName',sortable : true},	
   		{header:'投资币种',dataIndex:'a3',id:'productStartDate',sortable : true},
   		{header:'投资期限(月)',dataIndex:'a4',id:'productEndDate',sortable : true},
   		{header:'挂钩标的',dataIndex:'a5',id:'rate',sortable : true},
   		{header:'预期收益上限/年化',dataIndex:'a6',renderer: ratePercent(true),align : 'right', hidden:false,id:'cost_rate',sortable : true},
   		{header:'实际收益率/年化',dataIndex:'a7',renderer: ratePercent(true),align : 'right', hidden:false,id:'cost_rate',sortable : true},
   		{header:'募集起始日',dataIndex:'a8', hidden:false,id:'cost_rate',sortable : true},
   		{header:'募集终止日',dataIndex:'a9', hidden:false,id:'cost_rate',sortable : true}
   	]);
	
	
	
	var finaInfoStore = new Ext.data.ArrayStore({
		fields : eduFields,
		data : eduData
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
		store : finaInfoStore,
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
		store:finaInfoStore,
		region:'center',
		loadMask:true,
		cm :finaInfoColumns,
    	bbar:sbbar, //分页工具栏
    	viewConfig:{
	       forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
		},
    	tbar:[{
    		text:'查看',
    		iconCls:'taskDistrIconCss',
    		handler:function() {
				var onGrid = Ext.getCmp("finaInfoGrid").getSelectionModel();
				var selectRe = finaInfoGrid.getSelectionModel().getSelections()[0];
				if (onGrid.getSelections()) {
					var records = onGrid.getSelections();// 选择行的个数 
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						viewFinanPanel.getForm().loadRecord(selectRe);
						viewFinanWin.show();
					}
				}
    		}	
    	}],
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
	});
	
	
	//查询面板
	var  viewFinanPanel = new Ext.form.FormPanel( {
		region:'north',
		height:100,
		labelWidth:100,//label的宽度
		labelAlign:'right',
		frame:true,
		autoScroll : true,
		region:'north',
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a0',fieldLabel:'产品名称',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a1',fieldLabel:'发行银行',anchor:'90%'}]
				
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a2',fieldLabel:'产品类型',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a3',fieldLabel:'投资币种',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a4',fieldLabel:'投资期限(月)',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a5',fieldLabel:'挂钩标的',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a6',fieldLabel:'预期收益率',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a7',fieldLabel:'实际收益率',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a8',fieldLabel:'募集起始日',anchor:'90%'}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'textfield',name:'a9',fieldLabel:'募集终止日',anchor:'90%'}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{text:'关闭',
			handler:function(){viewFinanWin.hide();}
		}]
	});
	
	
	//查看页面
	var viewFinanWin = new Ext.Window( {
		resizable : false,
		collapsible : false,
		draggable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		animCollapse : false,
		border : false,
		loadMask : true,
		closable : true,
		constrain : true,
		layout : 'fit',
		width : 500,
		height : 220,
		buttonAlign : "center",
		title : '查看产品详情',
		items : [ viewFinanPanel ]
	});
	
	
	
	//页面布局
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		layout:'border',
		items : [finaSearchPanel,finaInfoGrid]
	});
	
});