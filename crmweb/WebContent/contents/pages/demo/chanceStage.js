Ext.onReady(function() {
	var h= document.body.clientHeight;
	 /*******************************************************************/
	var fields = [],
	    columns = [],
	    data = [];
	
	    
	    
	      fields =
	      [
	          {name:'a1'},
	{name:'a2'}
	      ];
	      
	      columns =
	      [
{dataIndex:'a1',header:'阶段',sortable:true},
{dataIndex:'a2',header:'数量',sortable:true}
	      ];
	      data = [
	              	['收集资料阶段',21],
	              	['进职调查阶段',33],
	              	['撰写报告阶段',53],
	              	['支行审批阶段',31],
	              	['分行审批阶段',21],
	              	['总行审批阶段',11],
	              	['合同签定',4]
	              ];
	     
	    var grid = new Ext.grid.GridPanel({
	        height: 300,
	        store: new Ext.data.ArrayStore({
	            fields: fields,
	            data: data
	        }),
	        stripeRows:true,
	        columns: columns//,
//	        viewConfig: {
//	            forceFit: true
//	        }
	    });
	 /*******************************************************************/
	// 布局模型
	var viewport = new Ext.Viewport({
			layout:'fit',
			frame:true,
			items:[{
				layout : 'border',
				items: [{   
			    	region:'center',
//			    	autoScroll:true,
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 
			}]

	});
}); 