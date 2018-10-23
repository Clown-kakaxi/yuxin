/**
 * 灵活查询面板入口
 */    

var MianViewPanel = new Ext.Panel({
    	width : document.body.scrollWidth,
    	height : document.body.scrollHeight-40,
    	layout : 'column',
    	items : [ {
    		columnWidth : .28,
    		layout : 'form',
    		border : false,
    		items : [ grid ]
    	}, {
    		columnWidth : .23,
    		layout : 'form',
    		border : false,
    		items : [ treeOfPoroduct ]
    	}, {
    		columnWidth : .49,
    		layout : 'form',
    		border : false,
    		items : [ right_panel ]
    	} ]	
    });
	/*
	 * 灵活查询弹出窗口
	 */
	var agileQueryWindow = new Ext.Window({
		layout : 'fit',
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		maximized:true,
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [MianViewPanel],
		buttons : [{
			text : '关闭',
			handler : function() {
	    		//移除已选查询条件
	    		simple.removeAllItems();
	    		//移除已选查询列
	    		simple2.removeAllItems();
	    		//移除修改标志
	    		right_panel.currentSolutionsId = false;
	    		
				agileQueryWindow.hide();
			}
		}]
	});
    