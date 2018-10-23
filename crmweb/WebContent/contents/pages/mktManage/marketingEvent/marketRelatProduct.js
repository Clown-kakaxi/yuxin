
		 var myData = [
				['634','订单融资','黄金','1.20%','0.35%'],
				['635','其他','黄金','1.20%','0.35%'],
				['151','定活两便','黄金','1.20%','0.35%'],
				['152','定活两便(93年以前)','白银','1.20%','0.35%'],
				['153','礼仪存单','白银','1.20%','0.35%'],
				['154','整存整取','黄金','1.20%','0.35%'],
				['155','储能保','白银','1.20%','0.35%']];
				var cusStore = new Ext.data.ArrayStore({
		               fields: [
		                  {name: 'a1'},
		                  {name: 'a2'},
		                  {name: 'a3'},
		                  {name: 'a4'},
		                  {name: 'a5'}
		               ]
		           });
		            cusStore.loadData(myData);
		             var productgrid = new Ext.grid.GridPanel({
		             	layout:'fit',
		             	title : '关联产品信息',
		               store: cusStore,
		               viewConfig:{
//						   forceFit:true,
						   autoScroll:true
						},
		               columns: [
						{header:'产品编号',width:120,dataIndex:'a1'},
						{header:'产品名称',width:120,dataIndex:'a2'},
						{header:'产品分类名称',width:120,dataIndex:'a3'},
						{header:'产品利率',width:120,dataIndex:'a4'},
						{header:'产品费率',width:120,dataIndex:'a5'}
		               ],
		               stripeRows: true,
		               width: '150%',
		               height : 450
		           });

	    
     	