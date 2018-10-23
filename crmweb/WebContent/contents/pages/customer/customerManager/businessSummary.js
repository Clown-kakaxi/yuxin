	
Ext.onReady(function(){
	Ext.QuickTips.init(); 


	
	
    // create the data store
    var record = Ext.data.Record.create([
   		{name: 'product'},
     	{name: 'price', type: 'float'},
     	{name: 'sum', type: 'float'},
     	{name: '_id', type: 'int'},
     	{name: '_parent',type: 'int'},
     	{name: '_level', type: 'int'},
     	{name: '_lft', type: 'int'},
     	{name: '_rgt', type: 'int'},
     	{name: '_is_leaf', type: 'bool'}
   	]);
    var store = new Ext.ux.maximgb.tg.NestedSetStore({
    	autoLoad : true,
			reader: new Ext.data.JsonReader({id: '_id'}, record),
			proxy: new Ext.data.MemoryProxy(data)
    });
    var data = [
{"product":"存款业务","price":64.72,"sum":64.72,"_id":1,"_parent":null,"_level":1,"_lft":1,"_rgt":2,"_is_leaf":false},

{"product":"融资业务","price":150.86,"sum":34.14,"_id":2,"_parent":null,"_level":1,"_lft":3,"_rgt":8,"_is_leaf":false},
{"product":"货款业务","price":75.43,"sum":75.43,"_id":3,"_parent":2,"_level":2,"_lft":4,"_rgt":5,"_is_leaf":true},
{"product":"票据贴现业务","price":75.43,"sum":75.43,"_id":4,"_parent":2,"_level":2,"_lft":6,"_rgt":7,"_is_leaf":true},
{"product":"担保承诺业务","price":150.86,"sum":75.43,"_id":5,"_parent":null,"_level":1,"_lft":9,"_rgt":15,"_is_leaf":false},
{"product":"银行保证业务","price":75.43,"sum":75.43,"_id":6,"_parent":5,"_level":2,"_lft":10,"_rgt":11,"_is_leaf":true},
{"product":"信贷证明业务","price":75.43,"sum":75.43,"_id":7,"_parent":5,"_level":2,"_lft":13,"_rgt":14,"_is_leaf":true},
{"product":"国际业务","price":150.86,"sum":75.43,"_id":8,"_parent":null,"_level":1,"_lft":16,"_rgt":21,"_is_leaf":false},
{"product":"国际结算业务","price":75.43,"sum":75.43,"_id":9,"_parent":8,"_level":2,"_lft":17,"_rgt":18,"_is_leaf":true},
{"product":"信贷证明业务","price":75.43,"sum":75.43,"_id":10,"_parent":8,"_level":2,"_lft":19,"_rgt":20,"_is_leaf":true},
{"product":"电子银行业务","price":75.43,"sum":75.43,"_id":11,"_parent":null,"_level":1,"_lft":22,"_rgt":23,"_is_leaf":true},
{"product":"中间业务收入","price":678.87,"sum":75.43,"_id":12,"_parent":null,"_level":1,"_lft":24,"_rgt":43,"_is_leaf":false},
{"product":"国内支付结算业务手续费收入","price":75.43,"sum":75.43,"_id":13,"_parent":12,"_level":2,"_lft":25,"_rgt":26,"_is_leaf":true},
{"product":"国际支付结算业务手续费收入","price":75.43,"sum":75.43,"_id":14,"_parent":12,"_level":2,"_lft":27,"_rgt":28,"_is_leaf":true},
{"product":"代理业务手续费收入","price":75.43,"sum":75.43,"_id":15,"_parent":12,"_level":2,"_lft":29,"_rgt":30,"_is_leaf":true},
{"product":"担保及承诺业务手续费收入","price":75.43,"sum":75.43,"_id":16,"_parent":12,"_level":2,"_lft":31,"_rgt":32,"_is_leaf":true},
{"product":"交易类业务手续费收入","price":75.43,"sum":75.43,"_id":17,"_parent":12,"_level":2,"_lft":33,"_rgt":34,"_is_leaf":true},
{"product":"托管业务手续费收入","price":75.43,"sum":75.43,"_id":18,"_parent":12,"_level":2,"_lft":35,"_rgt":36,"_is_leaf":true},
{"product":"咨询顾问业务手续费收入","price":75.43,"sum":75.43,"_id":19,"_parent":12,"_level":2,"_lft":37,"_rgt":38,"_is_leaf":true},
{"product":"电子银行业务手续费收入","price":75.43,"sum":75.43,"_id":20,"_parent":12,"_level":2,"_lft":39,"_rgt":40,"_is_leaf":true},
{"product":"其他手续费收入","price":75.43,"sum":75.43,"_id":21,"_parent":12,"_level":2,"_lft":41,"_rgt":42,"_is_leaf":true},
{"product":"供应链金融业务","price":226.29,"sum":34.14,"_id":22,"_parent":null,"_level":1,"_lft":44,"_rgt":54,"_is_leaf":false},
{"product":"预付类","price":75.43,"sum":75.43,"_id":23,"_parent":22,"_level":2,"_lft":45,"_rgt":46,"_is_leaf":true},
{"product":"存货类","price":75.43,"sum":75.43,"_id":24,"_parent":22,"_level":2,"_lft":47,"_rgt":48,"_is_leaf":true},
{"product":"应收类","price":75.43,"sum":75.43,"_id":25,"_parent":22,"_level":2,"_lft":49,"_rgt":53,"_is_leaf":false},
{"product":"单位活动存款","price":64.72,"sum":64.72,"_id":26,"_parent":25,"_level":3,"_lft":50,"_rgt":52,"_is_leaf":true}	
    
    		];
    store.loadData(data);
    store.expandAll();


    // create the Grid
    var grid = new Ext.ux.maximgb.tg.GridPanel({
      store: store,
      master_column_id : 'product',
      columns: [
		{id:'product',header: "产品", width: 100, sortable: true, dataIndex: 'product'},
        {header: "金额", width: 100, sortable: true, renderer: money('0,000.00'), dataIndex: 'price',align:'right'},
        {header:"sum", width:'100',sortable:true,dataIndex:'sum',renderer: 'money',hidden : true}

      ],
      stripeRows: true,
      autoExpandColumn: 'product',
      title: '业统汇总计',
      viewConfig : {
      	enableRowBody : true
      }
      
    });
	var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
    	layout : 'fit',
		height:document.body.scrollHeight-30,

		autoScroll:true,

    	items : [grid]
    });
});