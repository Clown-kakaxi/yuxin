/**
 * 
 * @update 20141013
 */
Ext.onReady(function() {
    var tokenDelimiter = ':';
    var tp = new Ext.TabPanel({
        id: 'main-tabs',
        minTabWidth:20,
        region:'center',
        enableTabScroll : true,
        activeTab: 0,
        items: [{
            title: '待办任务',
            id: 'subtab1',
            html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"callCenterWorkTodoList.jsp\" "/> scrolling="auto"> </iframe>'
        },{
            title: '办结任务',
            id: 'subtab3',
            html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"callCenterWorkDoneList.jsp\" "/> scrolling="auto"> </iframe>'
        }] ,
		listeners: {
	        'tabchange': function(tabPanel, tab){
	            Ext.History.add(tabPanel.id + tokenDelimiter + tab.id);
	        }
	    }
	});
    
    Ext.History.on('change', function(token){
        if(token){
            var parts = token.split(tokenDelimiter);
            var tabPanel = Ext.getCmp(parts[0]);
            var tabId = parts[1];
            tabPanel.show();
            tabPanel.setActiveTab(tabId);
        }else{
            tp.setActiveTab(0);
            tp.getItem(0).setActiveTab(0);
        }
    });

	var view = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [{
			layout : 'border',
			items : [{
				region : 'center',
				id : 'center-panel',
				layout : 'fit',
				items : [ tp ]
			}]
		}]
	});	  
}); 