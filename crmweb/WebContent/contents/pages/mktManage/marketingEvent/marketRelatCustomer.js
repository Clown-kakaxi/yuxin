
		 var myData = [
		               ['CNJ2013021800024','圆胜电子科技(昆山)有限公司','对公','客户经理A','南京银行光华支行'] 
		                ,['CNJ2013021800025','苏州宇鑫电子有限公司南京办事','对公','客户经理A','南京银行光华支行']
		                ,['CNJ2013021800026','南京新月桑拿泳池设备有限公司','对公','客户经理A','南京银行光华支行']
		                ,['CNJ2013021800027','南京策杰贸易有限公司', '对公','客户经理A','南京银行光华支行']
		                ,['CNJ2013021800028','南京百世照明电器有限公司','对公','客户经理A','南京银行光华支行' ]
		                ,['CNJ2013021800029','南京多美达广告展览有限责任公司','对公','客户经理A','南京银行光华支行']
		                ,['CNJ2013021800030','南京望凯电光源有限公司', '对公','客户经理A','南京银行光华支行']
		                ];
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
		             var customergrid = new Ext.grid.GridPanel({
		             	layout:'fit',
		               store: cusStore,
		               title : '关联客户信息',
		               viewConfig:{
//						   forceFit:true,
						   autoScroll:true
						},
		               columns: [
						{header:'客户号',width:120,dataIndex:'a1'},
						{header:'客户名称',width:120,dataIndex:'a2'},
						{header:'客户类型',width:120,dataIndex:'a3'},
						{header:'主办客户经理',width:120,dataIndex:'a4'},
						{header:'客户所属机构',width:120,dataIndex:'a5'}
		               ],
		               stripeRows: true,
		               width: '150%',
		               height : 450
		           });

	    
     	