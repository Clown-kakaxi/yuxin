Ext.onReady(function(){
	
	var tPanel =new  Ext.Panel({
		height:100,
		title:'tPanel'
	});
	
	var tempPanel =new  Ext.Panel({
//		height:100,
		title:'tempPanel'
	});
//	// 布局模型
//	var viewport = new Ext.Viewport( {
//		layout : 'fit',
//		items : [ tPanel ]
//	});
	
	var window = new Ext.Window({
		width:1000,
		height: 500,
		maximizable:true,
		layout:'fit',
		items:[tPanel,tempPanel]
	});
	window.show();
	
//	var cont = tPanel.body.appendChild(Ext.getBody().createChild({
//		tag:'div',
//		id: 'inlineContainer'
//	}));
	toolbarMain(tempPanel,tPanel);
});
