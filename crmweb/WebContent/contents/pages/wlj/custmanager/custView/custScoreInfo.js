/**
 * 客户视图-客户积分信息
 */
Ext.QuickTips.init();    

var _custId;
//    var url = basepath+'/custScoreInfoQuery.json?custId='+_custId;
//    var needCondition = false;
    var needGrid = false;
    WLJUTIL.suspendViews=false;  //自定义面板是否浮动
    
    var lookupTypes=[
                     'REMIND_TYPE',
                     'ADD_STATE',
                     'GOODS_STATE'
    ];
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'SCORE_TOTAL',text:'可用积分',searchField: true},
				  {name:'SCORE_USED',text:'已用积分',searchField: true},
				  {name:'SCORE_TODEL',text:'待扣积分',searchField: true},
				  {name:'SCORE_ADD',text:'拟加积分',searchField: false},
				  {name:'ADD_STATE',text:'拟加积分状态',searchField: false},
				  {name:'ADD_REASON',text:'最近一次积分原因',searchField:false},
				  {name:'ADD_DATE',text:'最近一次本次加分日期',searchField:false,dataType:'date'},
				  {name:'COUNT_NUM',text:'客户累计积分',searchField: false},
				  {name:'CUST_CUM_COUNT',text:'客户当月积分',searchField: false},
				  {name:'CUST_CUM_COST',text:'客户当月消费积分',searchField: false},
				  {name:'CUST_COST_SUM',text:'客户累计消费积分',searchField: false}
	              ];
	
	var custScoreInfoPanel= new Ext.FormPanel({
		title:'积分信息',
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		items:[{
			autoHeight : true,
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
					  {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'可用积分',name:'SCORE_TOTAL'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'待扣积分',name:'SCORE_TODEL'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'拟加积分状态',name:'ADD_STATE'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'最近一次本次加分日期',name:'ADD_DATE'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'客户当月积分',name:'CUST_CUM_COUNT'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'客户累计消费积分',name:'CUST_COST_SUM'}
		              ]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[
				      {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'已用积分',name:'SCORE_USED'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'拟加积分',name:'SCORE_ADD'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'最近一次积分原因',name:'ADD_REASON'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'客户累计积分',name:'COUNT_NUM'},
		              {xtype:'textfield',readOnly:true,cls: 'x-readOnly',anchor : '95%',fieldLabel:'客户当月消费积分',name:'CUST_CUM_COST'}
			              ]
			}]
		}]
	});
	
	
	var record=Ext.data.Record.create( [
	   {name: 'SCORE_TOTAL'}
	  ,{name: 'SCORE_TODEL'}
	  ,{name: 'ADD_STATE'}
	  ,{name: 'ADD_DATE'}
	  ,{name: 'CUST_CUM_COUNT'}
	  ,{name: 'CUST_COST_SUM'} 	
	  ,{name: 'SCORE_USED'}
	  ,{name: 'SCORE_ADD'}
	  ,{name: 'ADD_REASON'}
	  ,{name: 'COUNT_NUM'}
	  ,{name: 'CUST_CUM_COST'}
	]);
	
	
	
	custScoreInfoPanel.getForm().reader=new Ext.data.JsonReader({
        root:'data'
    },record);
	   
	   //customerBaseInfoPanel加载数据
	custScoreInfoPanel.getForm().load({
	   	url: basepath+'/custScoreInfoQuery!getScoreInfo.json',
	   	method:'get',
	   	params : {
	   		'custId':_custId
	       }
	});
	
	/**************************************************************/
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 38
	});
	
	var scoreAddStore=new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url :basepath + '/custScoreAddQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root : 'json.data'
		},[ {
			name : 'SCORE_ADD',
			mapping : 'SCORE_ADD'
		}, {
			name : 'ADD_RESON',
			mapping : 'ADD_RESON'
		}, {
			name : 'ADD_DATE',
			mapping : 'ADD_DATE'
		}, {
			name : 'STATE',
			mapping : 'STATE'
		}, {
			name : 'ADD_FILE',
			mapping : 'ADD_FILE'
		}])
	});
	scoreAddStore.baseParams={
				custId :_custId
			};
	scoreAddStore.load({
		params:{custId:_custId}
	});
	
	var scoreAdd_cm = new Ext.grid.ColumnModel([//gridtable中的列定义
	 rownum,
	// {header : 'ID',dataIndex : 'id',width : 100,sortable : true,hidden : true},
	 {header : '拟加积分',dataIndex : 'SCORE_ADD',width : 120,sortable : true},
	 {header : '积分原因',dataIndex : 'ADD_RESON',width : 120,sortable : true},
	 {header : '加分日期',dataIndex : 'ADD_DATE',width : 150,sortable : true},
	 {header : '数据状态',dataIndex : 'STATE',width : 150,renderer:function(value){
			var val = translateLookupByKey("ADD_STATE",value);
			return val?val:"";
			},sortable : true},
	 {header : '佐证资料',dataIndex : 'ADD_FILE',width : 150,sortable : true}
	 ]);
	
		// 每页显示条数下拉选择框
	var scoreAddPagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ]]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	
	scoreAddPagesize_combo.on("select", function(comboBox) { // 改变每页显示条数reload数据
		scoreAddBbar.pageSize = parseInt(scoreAddPagesize_combo.getValue());
		scoreAddStore.reload({
			params : {
				start : 0,
				limit : parseInt(scoreAddPagesize_combo.getValue())
			}
		});
	});
	
		// 分页工具栏
	var scoreAddBbar = new Ext.PagingToolbar({
		pageSize : 20,
		store : scoreAddStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', scoreAddPagesize_combo ]
	});
	
	var scoreAddGrid =  new Ext.grid.GridPanel({
		title : '积分增加记录',
		height: 350,
		frame : true,
		store : scoreAddStore,
		stripeRows : true, // 斑马线
		loadMask : true,
		cm : scoreAdd_cm,
		bbar:scoreAddBbar,
		viewConfig : {},
	    loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
		
	});
	
	/****************************************************************************/
	
	var getGiftStore=new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath +'/custGoodsGetInfoQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root : 'json.data'
		},[ {
			name : 'COMP_ACTI',
			mapping : 'COMP_ACTI'
		}, {
			name : 'GOODS_NAME',
			mapping : 'GOODS_NAME'
		}, {
			name : 'CREATE_DATE',
			mapping : 'CREATE_DATE'
		}, {
			name : 'GIVE_NUMBER',
			mapping : 'GIVE_NUMBER'
		}, {
			name : 'GIVE_DATE',
			mapping : 'GIVE_DATE'
		}, {
			name : 'GIVE_REASON',
			mapping : 'GIVE_REASON'
		}, {
			name : 'NEED_SCORE',
			mapping : 'NEED_SCORE'
		}, {
			name : 'GIVE_STATE',
			mapping : 'GIVE_STATE'
		},{
			name : 'REMARK',
			mapping : 'REMARK'
		}])
	});
	getGiftStore.baseParams={
				custId :_custId
			};
	getGiftStore.load({
		params:{custId:_custId}
	});
	
	var getGift_cm = new Ext.grid.ColumnModel([//gridtable中的列定义
	 rownum,
	// {header : 'ID',dataIndex : 'id',width : 100,sortable : true,hidden : true},
	 {header : '配合活动',dataIndex : 'COMP_ACTI',width : 120,sortable : true},
	 {header : '礼品名称',dataIndex : 'GOODS_NAME',width : 120,sortable : true},
	 {header : '创建日期',dataIndex : 'CREATE_DATE',width : 150,sortable : true},
	 {header : '赠送数量',dataIndex : 'GIVE_NUMBER',width : 150,sortable : true},
	 {header : '赠送日期',dataIndex : 'GIVE_DATE',width : 150,sortable : true},
	 {header : '赠送原因',dataIndex : 'GIVE_REASON',width : 150,sortable : true},
	 {header : '需要积分',dataIndex : 'NEED_SCORE',width : 150,sortable : true},
	 {header : '复核状态',dataIndex : 'GIVE_STATE',width : 150,renderer:function(value){
			var val = translateLookupByKey("GOODS_STATE",value);
			return val?val:"";
			},sortable : true},
	 {header : '备注',dataIndex : 'REMARK',width : 150,sortable : true}
	 ]);
		// 分页工具栏
		// 每页显示条数下拉选择框
	var getGiftPagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	getGiftPagesize_combo.on("select", function(comboBox) { // 改变每页显示条数reload数据
		getGiftBbar.pageSize = parseInt(getGiftPagesize_combo.getValue()), getGiftStore.reload({
			params : {
				start : 0,
				limit : parseInt(getGiftPagesize_combo.getValue())
			}
		});
	});
	var getGiftBbar = new Ext.PagingToolbar({
		pageSize : 20,
		store : getGiftStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', getGiftPagesize_combo ]
	});
	
	var getGiftGrid =  new Ext.grid.GridPanel({
		title : '礼品领取记录',
		height: 350,
		frame : true,
		store : getGiftStore,
		loadMask : true,
		cm : getGift_cm,
		bbar:getGiftBbar,
	    loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
		
	});
	
	var customerView=[{
		title:'积分信息',
		layout: 'form',
		items: [custScoreInfoPanel,scoreAddGrid,getGiftGrid]
	}];
    
	
	
	
	/*********************/
	
	
	
	
