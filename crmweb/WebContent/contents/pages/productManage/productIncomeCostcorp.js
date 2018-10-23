Ext.onReady(function(){	 
	
	 var search =new Ext.form.FormPanel({
		 title:'条件查询',
		 frame:true,
		 labelAlign:'right',
		 buttonAlign:'center',
		 split:true,
		 height:100,
		 region:'north',
		 autoScoll:true,
		 items:[{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.3,
				 items:[{
					 xtype:'numberfield',
					 fieldLabel:'产品期数从',
					 name:'prodcut_start',
					 id:'prodcut_start',
					 allowDecimals : 0,
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.3,
				 items:[{
					 xtype:'numberfield',
					 fieldLabel:'--到',
					 name:'prodcut_end',
					 allowDecimals : 0,
					 id:'prodcut_end',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.3,
				 items:[{
					 
						xtype:'textfield',
						fieldLabel:'产品名',
						id:'productName1',
						name:'productName1',
						anchor:'90%'
				 }]
			 }]
		 }],
		 buttons:[{
			 text:'查询',
			 handler:function(node){
			 var parameters = search.getForm().getValues(false);
			 	storecorp.removeAll();
			 	storecorp.baseParams = {
	             'condition':Ext.util.JSON.encode(parameters)
	            };
	           storecorp.reload({
	             params:{
	                 start:0,
	                 limit: parseInt(spagesize_combo.getValue())
	             }
	           });
	         
		 }
		 },{
			 text:'重置',
			 handler:function(){
			 search.getForm().reset();
		 }
		 }]
	 });
	
	//********************机构列表
	var GroupRow = [
	                  	{header: '', colspan: 8, align: 'center'},
	                  	{header: '兑付收益', colspan: 2, align: 'center'},
	                  	{header: '', colspan: 1, align: 'center'},
	                  	{header: '银行手续费收入', colspan: 2, align: 'center'},
	                  	{header: '主办行费用', colspan: 2, align: 'center'},
	                  	{header: '分支行销售手续费收入', colspan: 2, align: 'center'},
	                  	{header: '', colspan: 1, align: 'center'}
	                  	];
	 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [GroupRow]
	    });
		var productLimitColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
		                                new Ext.grid.RowNumberer(),
		                                {header :'产品编号',dataIndex:'productId',id:"productId",sortable : true,width:100},
		                                {header :'产品类型 ',dataIndex:'productType',id:"productType",sortable : true,width:100},
		                                {header :'产品名',dataIndex:'productName',id:"productName",sortable : true,width:100},
		                                {header :'产品发布时间',dataIndex:'productStarttime',id:"productStarttime",sortable : true,width:100},
		                                {header :'产品截止时间',dataIndex:'productEndtime',id:"productEndtime",sortable : true,width:100},
		                                {header :'产品期数',dataIndex:'prodPeriod',id:"prodPeriod",sortable : true,width:100},
		                                {header :'投资收益',dataIndex:'investIncome',id:"investIncome",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'个人',dataIndex:'personalCashin',id:"personalCashin",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'机构',dataIndex:'organCashin',id:"organCashin",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'托管费用',dataIndex:'custodyFee',id:"custodyFee",align:'right',renderer: money('0,000.00'),sortable : true,width:100}, 
		                                {header :'个人',dataIndex:'personalIncome',id:"personalIncome",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'机构',dataIndex:'organIncome',id:"organIncome",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'个人',dataIndex:'personalhost',id:"personalhost",align:'right',align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'机构',dataIndex:'organhost',id:"organhost",align:'right',renderer: money('0,000.00'),sortable : true,width:100},
		                                {header :'个人',dataIndex:'personalBranch',id:"personalBranch",align:'right',renderer: money('0,000.00'),sortable : true,width:130},
		                                {header :'机构',dataIndex:'organBranch',id:"organBranch",align:'right',renderer: money('0,000.00'),sortable : true,width:130},
		                                {header :'总行手续费收入',dataIndex:'parentBank',id:"parentBank",align:'right',renderer: money('0,000.00'),sortable : true,width:130}
		                                ]);
		  var productInfoRecord = new Ext.data.Record.create([    
		                                   {name:'productId',mapping:'PRODUCT_ID'},
		                                   {name:'productType',mapping:'PROD_TYPE'},
		                                   {name:'productName',mapping:'PROD_NAME'},
		                                   {name:'productStarttime',mapping:'PROD_START_TIME'},
		                                   {name:'productEndtime',mapping:'PROD_END_TIME'},
		                                   {name:'prodPeriod',mapping:'PROD_PERIOD'},	
		                                   {name:'investIncome',mapping:'INVEST_INCOME'},
		                                   {name:'personalCashin',mapping:'PERS_CASHIN'},
		                                   {name:'organCashin',mapping:'ORGAN_CSHIN'},
		                                   {name:'custodyFee',mapping:'CUSTODY_FEE'},
		                                   {name:'personalIncome',mapping:'PERS_INCOME'},
		                                   {name:'organIncome',mapping:'ORGAN_INCOME'},
		                                   {name:'personalhost',mapping:'PERS_HOST'},
		                                   {name:'organhost',mapping:'ORGAN_HOST'},
		                                   {name:'personalBranch',mapping:'PERS_BRANCH'},
		                                   {name:'organBranch',mapping:'ORGAN_BRANCH'},
		                                   {name:'parentBank',mapping:'PARENT_BANK'}
		                                      
		                                                      ]);

		var Reader = new Ext.data.JsonReader({//读取json数据的panel
		    totalProperty:'json.count',
		    root:'json.data'
		    },productInfoRecord);
		
		var storecorp=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({
		        url:basepath+'/product-IncomeCost.json',
		        method:'GET'
		        }),
			reader:Reader
		});
		//*********************
		
		 var spagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								[ 100, '100条/页' ], [ 250, '250条/页' ],
								[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				forceSelection : true,
				width : 85
			});
	
		// 机构列表的改变每页显示条数reload数据
		spagesize_combo.on("select", function(comboBox) {
			sbbarcorp.pageSize = parseInt(spagesize_combo.getValue()),
			storecorp.reload({
				params : {
					start : 0,
					limit : parseInt(spagesize_combo.getValue())
				}
			});
		});
		// 机构列表分页工具栏
		var sbbarcorp = new Ext.PagingToolbar({
			pageSize : parseInt(spagesize_combo.getValue()),
			store : storecorp,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
		});
		var gridcorp=new Ext.grid.GridPanel({
			title:'收益-费用展示列表',
			frame:true,
			region:'center',
			id:'productInfoGridcorp',
			store:storecorp,
			stripeRows:true,
			plugins: group,
			loadMask:true,
			cm :productLimitColumns,
			tbar:[ 
			       new Com.yucheng.bob.ExpButton({
				  formPanel : gridcorp,
				  iconCls:'exportIconCss',
				  id:'exportbtcorp',
				  url : basepath+'/product-IncomeCost.json'
			  	})	
			       ],
	    	bbar:sbbarcorp,
			loadMask : {
	        msg : '正在加载表格数据,请稍等...'
	    }

		});
		
		storecorp.load({
			params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		}
		});
		

	//布局
	 var view = new Ext.Viewport({//页面展示
			layout : 'fit',
			frame : true,
			items : [{
			layout:'border',
			items:[
                      search,
						{
							region:'center',
							layout:'fit',
							items:[gridcorp]
						}
						]				
			}]
		});	
	 

		
});