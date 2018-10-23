
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
			{"item":"活期","rate":"0.35"},
			{"item":"整存整取（三个月）","rate":"2.60"},
			{"item":"整存整取（半年）","rate":"2.80"},
			{"item":"整存整取（一年）","rate":"3.00"},
			{"item":"整存整取（二年）","rate":"3.75"},
			{"item":"整存整取（三年）","rate":"4.25"},
			{"item":"整存整取（五年）","rate":"4.75"},
			{"item":"零存整取、整存零取、存本取息（一年）","rate":"2.85"},
			{"item":"零存整取、整存零取、存本取息（二年）","rate":"2.90"},
			{"item":"零存整取、整存零取、存本取息（二年）","rate":"3.00"},
			{"item":"通知存款（一天）","rate":"0.80"},
			{"item":"通知存款（七天）","rate":"1.35"}
		]
	};
	store.loadData(tempData);
	var gridPanel = new Ext.grid.GridPanel({
		title: '人民币存款利率表',
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