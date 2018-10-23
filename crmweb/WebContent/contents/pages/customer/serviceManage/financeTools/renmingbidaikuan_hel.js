
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	//定义列模型
	var cm = new Ext.grid.ColumnModel([
     	{header : '项目',dataIndex : 'item',width : 300,sortable : true},
     	{header : '年利率(%)',dataIndex : 'rate',width : 100,sortable : true}
     ]);
    var record = new Ext.data.Record.create([
		{name : 'item'},
		{name : 'rate'}
		]);
	var store = new Ext.data.Store({
		restful:true,
//		proxy: new Ext.data.HttpProxy({
//			url: basepath + '/giftManage.json'
//		}),
		reader: new Ext.data.JsonReader({
			root:'rows',
			totalProperty:'total'
		},record)
	});
	
	var tempData= {
		total:12,
		rows:[
			{"item":"商业贷款（六个月）","rate":"5.60"},
			{"item":"商业贷款（六个月至一年）","rate":"6.00"},
			{"item":"商业贷款（一至三年）","rate":"6.15"},
			{"item":"商业贷款（三至五年）","rate":"6.40"},
			{"item":"商业贷款（五年以上）","rate":"6.55"},
			{"item":"个人住房公积金贷款（五年以下）","rate":"4.00"},
			{"item":"个人住房公积金贷款（五年以上）","rate":"4.50"}
		]
	};
	store.loadData(tempData);
	var gridPanel = new Ext.grid.GridPanel({
		title: '人民币贷款利率表',
		frame: true,
		height:360,
		autoScroll: true,
		region: 'center',
		stripeRows: true,
		store: store,
		cm : cm,	//列模型
		viewConfig:{},
		loadMask: {
			msg: '正在加载表格数据,请稍等...'
		}
	});
	
	var p = new Ext.Panel({
	    renderTo:'viewDiv',
	    layout:'column',
	    width:840,
	    items: [{
			columnWidth:1,
			layout:'form',
			items:[gridPanel]
		}]
	});



});