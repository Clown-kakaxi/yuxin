Ext.onReady(function() {

	Ext.setGridColumnMenuDisable(true);
    var blocViewRecord = new Ext.data.Record.create(
    [
    	{
    		name:'viewItem'
   		}
    ]
    );
    var blocViewReader = new Ext.data.JsonReader({
    	root:'rows'
    },blocViewRecord);
    
    var blocViewStore = new Ext.data.Store(
    {
    	reader:blocViewReader
    }
    );
    var blocViewData ={
     rows:[
     {viewItem:'集团基本信息'},
     {viewItem:'集团组织架构图'},
     {viewItem:'集团成员列表'},
     {viewItem:'存贷款信息'},
     {viewItem:'产品信息'},
     {viewItem:'授信信息'}
     ]
    };
    
 // 表格工具栏
    var tbar = new Ext.Toolbar({
                items : [{
                    text:'返回',
                    handler:function(){
                        window.location.href ='./groupClientMaintenance1.jsp';
                    }
                }]
            });

    
    blocViewStore.loadData(blocViewData);
     
    var leftPanel = new Ext.grid.GridPanel({

    	columns:[
    	{
    		header:'集团客户视图',
    		id:'viewItem',
    		align:'center',
    		renderer:function(value)
    		{
    			return "<a href='#' onClick='gotoBlocView( "+"\""+value+"\""+" )'>"+value+"</a>";	
    		}
    	}
    	],
//    	tbar : tbar,
    	autoExpandColumn:'viewItem',
    	store:blocViewStore
    });
    
    var groupWin = new Ext.Window(
				{
					layout : 'fit',
					draggable : true,// 是否可以拖动
					closable : true,// 是否可关闭
					modal : true,
					closeAction : 'close',
					maximized : true,// 默认最大化
					titleCollapse : true,
					buttonAlign : 'center',
					border : false,
					animCollapse : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [ {
						layout : 'border',
						items : [
								{
									region : 'west',
									id : 'west-panel',
									split : true,// 是否可拖动
									width : 200,
									minSize : 175,
									maxSize : 400,
									collapsible : true,// 是否有伸缩按钮
									margins : '0 0 0 5',
									layout : 'fit',
									items : [ leftPanel ]
								},
								{
									region : 'center',
									id : 'center-panel',
									title : "  ",
									html : '<iframe src="customerOrgChart/groupCustomerChart.jsp" frameborder="0" scrolling="yes" id="blocViewFrame" name="blocViewFrame" width="100%" height="100%"></iframe>'
								} ]
					} ]
				});
		groupWin.show();
		groupWin.on('hide', function() {
			window.location.href = 'groupClientMaintenance1.jsp';
		});
    

//var view = new Ext.Viewport({
//	layout:'fit',
//	frame : true,
//	items : [{
//layout: 'border',
//items: [
//	 {   
//		region: 'west',
//	    id: 'west-panel',
////		collapsible: true, 
//		split: true,//是否可拖动
//		width: 200,
//		minSize: 175,
//		maxSize: 400,
//		collapsible: true,//是否有伸缩按钮
//		margins: '0 0 0 5',
//		layout: 'fit',
//		items:[leftPanel]
//	 },
//	 {
//	 	region:'center',
//	    id: 'center-panel',
//	    title: "  ",
//	    html:'<iframe src="customerOrgChart/groupCustomerChart.jsp" frameborder="0" scrolling="yes" id="blocViewFrame" name="blocViewFrame" width="100%" height="100%"></iframe>' 
//	 }
//	]
//	}]
//    } );
	   
}); 