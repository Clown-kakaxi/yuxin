Ext.onReady(function() {
		 //复选框
	var xsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true}); 

	// 定义自动当前页行号
	var xrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

		// 定义列模型
	var xcm = new Ext.grid.ColumnModel([xrownum,xsm, 
	         {header : '目标客户ID', dataIndex : 'DEST_CUST_ID',sortable : true,width : 150,hidden:true},
	         {header : '目标客户名字', dataIndex : 'DEST_CUST_NAME',sortable : true,width : 150},
		     {header : '目标客户组织机构代码',dataIndex : 'DEST_CUST_ZZDM',sortable : true,width : 150}, 
		     {header : '关联客户ID',dataIndex : 'RELA_CUST_ID',sortable : true,width : 150,hidden:true},
	         {header : '关联客户名称',dataIndex : 'RELA_CUST_NAME'}, 
		     {header : '关联客户组织机构代码',dataIndex : 'RELA_CUST_ZZDM'},
			 {header : '关系名称',dataIndex : 'RELA_NAME'},
			 {header : '关系描述',dataIndex : 'RELA_DESC'},
			 {header : '持股比例 ',dataIndex : 'SH_PCT'},
			 {header : '创建人ID',dataIndex : 'CREATOR'},
			 {header : '创建人姓名',dataIndex : 'CREATOR_NAME'},
			 {header : '创建时间',dataIndex : 'CREAT_DATE'}
			]);

	/**
	 * 数据存储
	 */
		 var xstore = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/queryviewandgrid2.json?customerId='+parent.location.href.split("customerId=")[1]
			      ,
			        	success : function(response) {
							Ext.Msg.alert('提示', response.responseText);
						}
				  }
				  ),
			       reader: new Ext.data.JsonReader({
			       totalProperty : 'json.count',
			        root:'json.data'
			        }, [
						{name: 'DEST_CUST_ID'},
						{name: 'DEST_CUST_NAME'},
						{name: 'DEST_CUST_ZZDM'},
						{name: 'RELA_CUST_ID'},
						{name: 'RELA_CUST_NAME'},
						{name: 'RELA_CUST_ZZDM'},
						{name: 'RELA_NAME'},
						{name: 'RELA_DESC'},
						{name: 'SH_PCT'},
						{name: 'CREATOR'},
						{name: 'CREATOR_NAME'},
						{name: 'CREAT_DATE'}
					])
				});

// 表格实例
	var xgrid = new Ext.grid.GridPanel({
		        //collapsible : true,// 是否可收缩
				height : 200,
				frame : true,
				autoScroll : true,
				store : xstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : xcm, // 列模型
				sm:xsm,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:{
			layout : 'border',
			items: [{   
			    	region:'center',
				    id: 'center-panel',
				    height: 500,
				    margins: '0 0 0 0',
				    items : [{
						html:'<iframe id="contentFrame" name="content" height="500" frameborder="no" width="100%" src=\"../../groupClientManager/organizationChart/chartDemo.jsp\" "/> scrolling="auto"> </iframe>'         
					}]
			    },{   region: 'south',
					id: 'south-panel',
					collapsed : true,
					collapsible: true,
					split: true,
					height:190,
					minSize: 80,
					maxSize: 200,
					collapsible: true,
					hidden:false,
					margins: '0 0 0 0',
					layout: 'fit',
					items:[xgrid]
     }] }

	});
	/*grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	function editInit(){
		addRoleWindow.show();
		root.expand(true);
	};
*/
	//xstore.load();
}); 