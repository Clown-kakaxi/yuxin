Ext.onReady(function() {
	
	var view = new Ext.Viewport({
		layout : 'border',
		items : [ 
			      {
					region : 'center',
					id : 'center-panel',
					layout : 'fit',
					html:'<div id="chartdiv"></div>' 
				  }
				]
	});
});