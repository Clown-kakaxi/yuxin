Ext.onReady(function(){
	
	var tPanel =new  Ext.Panel({
		title:'tPanel'
	});
//	// 布局模型
//	var viewport = new Ext.Viewport( {
//		layout : 'fit',
//		items : [ tPanel ]
//	});
	
	var window = new Ext.Window({
		width:500,
		height: 500,
		maximizable:true,
		layout:'fit',
		items:[tPanel]
	});
	window.show();
	
//	var cont = tPanel.body.appendChild(Ext.getBody().createChild({
//		tag:'div',
//		id: 'inlineContainer'
//	}));
	orgChartsMain(tPanel);
});
