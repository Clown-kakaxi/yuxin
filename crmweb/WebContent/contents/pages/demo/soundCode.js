Ext.onReady(function(){
	var codeStore = new Ext.data.ArrayStore({
        fields : ['type', 'fileName'],
        data : [['JS','theBaseDemo.js']]
    });
	
	var rownum = new Ext.grid.RowNumberer({
        header : 'No.',
        width : 28
    });
	
	var cm = new Ext.grid.ColumnModel([rownum, 
        {
            header : '类型',
	        dataIndex : 'type',
	        sortable : true,
	        width : 120
	    },{
	    	header : '文件名',
	    	dataIndex : 'fileName',
	    	sortable : true,
	    	width : 220,
	    	renderer:function(value, p, record){
                return "<a href='"+basepath+"/contents/pages/demo/"+value+"'>"+value+"</a>";
	        }
	    }]);
	
	 var grid = new Ext.grid.GridPanel({
	        title : '源码信息',
	        frame : true,
	        store : codeStore, 
	        region : 'center',
	        stripeRows : true, 
	        cm : cm,
	        viewConfig : {
	        },
	        loadMask : {
	            msg : '正在加载表格数据,请稍等...'
	        }
	    });
	
	new Ext.Viewport({
		layout:'border',
        items:[
               grid
        ]
	});
	
});