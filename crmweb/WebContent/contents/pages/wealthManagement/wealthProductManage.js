Ext.onReady(function(){
	Ext.QuickTips.init();
    /**
     *产品树数据加载 
     */         

	var productTreeLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'CATL_PARENT',//指向父节点的属性列
		locateAttr : 'CATL_CODE',//节点定位属性列，也是父属性所指向的列
		rootValue :'0',//虚拟根节点id 若果select的值为null则为根节点
		textField : 'CATL_NAME',//用于展示节点名称的属性列
		idProperties : 'CATL_CODE'//,//指定节点ID的属性列
	});
	
	/**
	 * 产品树数据请求
	 */
	Ext.Ajax.request({//请求产品树数据
		url : basepath + '/productCatlTreeAction.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			productTreeLoader.nodeArray = nodeArra;
			var children = productTreeLoader.loadAll();
			Ext.getCmp('productLeftTreePanel12').appendChild(children);
		}
	});

	/**
	 * 产品树面板，在模块左侧显示
	 */
	 var prodCatlSign = '';
	var productLeftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		id:'productLeftTreePanel12',
		height : document.body.clientHeight,
		width : 200,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
		enableDrag:true,
		resloader:productTreeLoader,//加载产品树
		region:'west',//布局位置设置
		split:true,
		root: new Ext.tree.AsyncTreeNode({//设置根节点
			id:'root',
			expanded:true,
			text:'银行产品树',
			autoScroll:true,
			children:[]
		}),
		  clickFn:function(node){//单击事件，当单击树节点时触发并且获得这个节点的CATL_CODE
	            if(node.attributes.CATL_CODE == undefined){
	                Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
	                return;
	            }
	            if(node.attributes.id!=''){
	            	prodCatlSign = node.attributes.CATL_CODE;
	            	wind.show();
	            }
	        }
	 }); 

    var addpanel =new Ext.form.FormPanel({
        buttonAlign:'center',
        labelWidth:100,//label的宽度
        labelAlign:'right',
        frame:true,
        autoScroll : true,
        region:'center',
        split:true,
        items:[{
       	 layout:'column',
       	 items:[{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 id:'upperLevelName1',
       			 value:'理财产品',
       			 readOnly:true,
       			 name:'upperLevelName',
       			 fieldLabel:'上级分类名称',
       			 anchor:'99%'
       		 }]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 id:'LevelId1',
       			 value:'1101',
       			 name:'LevelId',
       			 fieldLabel:'分类编码',
       			 anchor:'99%'
       		 }]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 id:'productId',
       			 name:'productId',
       			 fieldLabel:'产品代码',
       			 anchor:'99%'
       		 }]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 id:'LevelName1',
       			 name:'LevelName',
       			 fieldLabel:'分类名称',
       			 anchor:'99%'
       		 }]
       	 }]
        }],
        buttons:[{
       	 text:'保存',
       	 handler:function(){
       		 
       	 }
        }]
   });
    var panel =new Ext.form.FormPanel({
         buttonAlign:'center',
         labelWidth:100,//label的宽度
         labelAlign:'right',
         frame:true,
         autoScroll : true,
         region:'center',
         split:true,
         items:[{
        	 layout:'column',
        	 items:[{
        		 layout:'form',
        		 columnWidth:.5,
        		 items:[{
        			 xtype:'textfield',
        			 id:'upperLevelName',
        			 value:'理财产品',
        			 readOnly:true,
        			 name:'upperLevelName',
        			 fieldLabel:'上级分类名称',
        			 anchor:'99%'
        		 }]
        	 },{
        		 layout:'form',
        		 columnWidth:.5,
        		 items:[{
        			 xtype:'textfield',
        			 id:'LevelId',
        			 value:'1101',
        			 name:'LevelId',
        			 fieldLabel:'分类编码',
        			 anchor:'99%'
        		 }]
        	 },{
        		 layout:'form',
        		 columnWidth:.5,
        		 items:[{
        			 xtype:'textfield',
        			 id:'LevelName',
        			 value:'开放式',
        			 name:'LevelName',
        			 fieldLabel:'分类名称',
        			 anchor:'99%'
        		 }]
        	 },{
        		 layout:'form',
        		 columnWidth:.5,
        		 items:[{
        			 xtype:'textfield',
        			 id:'presentTier',
        			 readOnly:true,
        			 value:'3',
        			 name:'presentTier',
        			 fieldLabel:'当前层级',
        			 anchor:'99%'
        		 }]
        	 }]
         }],
         buttons:[{
        	 text:'保存修改',
        	 handler:function(){
        		 
        	 }
         },{
        	 text:'创建新分类',
        	 handler:function(){
        		 wind.hide();
        		 addwind.show();
        		 
        	 }
         }]
    });
    var addwind = new Ext.Window( {
        layout : 'fit',
        width : 600,
        title:'新增产品类别',
        height : 170,
        resizable : false,//是否允许缩放
        draggable : true,// 是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        closeAction : 'hide',
        collapsible : true,// 是否可收缩
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        items:[addpanel]
    });
    var wind = new Ext.Window( {
        layout : 'fit',
        width : 600,
        title:'产品类别修改',
        height : 170,
        resizable : false,//是否允许缩放
        draggable : true,// 是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        closeAction : 'hide',
        collapsible : true,// 是否可收缩
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        items:[panel]
    });
    var panel113 =new Ext.form.FormPanel({
        buttonAlign:'center',
        labelWidth:100,//label的宽度
        labelAlign:'right',
        frame:true,
        autoScroll : true,
        region:'center',
        split:true,
        items:[{
       	 layout:'column',
       	 items:[{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 name:'[roductId',
       			 fieldLabel:'产品编号',
       			 anchor:'99%'
       		 }]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 name:'productName',
       			 fieldLabel:'产品名称',
       			 anchor:'99%'
       		 }]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 name:'proudctLevelId',
       			 fieldLabel:'产品类别编码',
       			 anchor:'99%'
       		 }]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{ xtype:'combo',
				 name:'riskGrade',
					triggerAction:'all',
					anchor:'100%',
					emptyText:'请选择',
					fieldLabel:'风险等级',
					mode:'local',
					store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: ['value','displayText'],
			        data: [[1, 'A级'], [2, 'B级'],['3','C级'],['4','D级'],['5','E级']]
			               }),
			       valueField:'value',
			       displayField:'displayText'}]
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 name:'proudctLevelName',
       			 fieldLabel:'五级分类名称',
       			 anchor:'99%'
       		 }]       	 
       	 },{

       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
					xtype:'combo',
					name:'ifRecommend',
					triggerAction:'all',
					anchor:'100%',
					fieldLabel:'是否推荐',
					emptyText:'请选择',
					mode:'local',
					store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: ['value','displayText'],
			        data: [[1, '是'], [2, '否']]
			               }),
			       valueField:'value',
			       displayField:'displayText'
			 }]
       	 
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 name:'sellState',
       			 fieldLabel:'营销状态',
       			 value:'热销',
       			 readOnly:true,
       			 anchor:'99%'
       		 }]       	 
       	 },{
       		 layout:'form',
       		 columnWidth:.5,
       		 items:[{
       			 xtype:'textfield',
       			 name:'expectIncome',
       			 fieldLabel:'预期收益',
       			 anchor:'99%'
       		 }]       	 
       	 }]
        }],
        buttons:[{
       	 text:'保存',
       	 handler:function(){
       		 
       	 }
        }]
   });
    var addWindow = new Ext.Window( {
        layout : 'fit',
        width : 600,
        title:'新产品设定',
        height : 200,
        resizable : false,//是否允许缩放
        draggable : true,// 是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        closeAction : 'hide',
        collapsible : true,// 是否可收缩
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        items:[panel113]
    });
    var sm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var record= Ext.data.Record.create([
	                 {name:'productId'},
	                 {name:'prodName'},
	                 {name:'riskGrade'},
	                 {name:'ifRecommend'},{name:'sellState'}
	                                    ]);
	debugger;
	var cm = new Ext.grid.ColumnModel([
	                  rownum,sm,
	                  {header :'产品编号',dataIndex:'productId',id:"productId1",width:100,sortable : true},
	                  {header:'产品名称',dataIndex:'prodName',id:'prodName1',align:'right',sortable : true,width:200},    
	                  {header:'风险等级',dataIndex:'riskGrade',sortable : true,width:100},
	                  {header:'是否推荐',dataIndex:'ifRecommend',sortable : true},
	                  {header:'销售状态',dataIndex:'sellState',sortable : true}

	                               ]);
	/**
	 * 数据存储
	 */
	var data=[
	          ['200110','黄金100g','A','是','已上架'],
	          ['201005','黄金100g','B','是','已上架'],
	          ['201009','黄金100g','C','是','未上架'],
	          ['201102','黄金100g','D','是','已上架'],
	          ['201107','黄金100g','E','否','已下架'],
	          ['201111','黄金100g','A','否','未上架']
	          ];
	var store = new Ext.data.Store({
		proxy : new Ext.data.MemoryProxy(data),
		reader : new Ext.data.ArrayReader({}, record)
	});

	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
	});

	// 默认加载数据
	store.load(data);

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	var grid = new Ext.grid.GridPanel({
		frame:true,
		autoScroll:true,
		region:'center',
		store:store,
		stripeRows:true,
		cm:cm,
		sm:sm,
		bbar:bbar,
		tbar:[{
			text:'上架产品',
			iconCls:'addIconCss',
			handler:function(){
				 var selectRe = grid.getSelectionModel().getSelected();//选择一条数据
					if (selectRe == null|| selectRe == "undefined") {
						Ext.Msg.alert('提示','请选择一条记录!');
						return;
					}else{
						var state =grid.getSelectionModel().getSelections()[0].data.sellState;						
						if(state=="未上架"||state=="已下架"){
							var name =grid.getSelectionModel().getSelections()[0].data.prodName;
							Ext.Msg.confirm("信息提示","是否上架:"+name,function(but){
								if(but=='yes'){
								Ext.Msg.alert("提示","产品上架成功!");	
								}
							});
						}else{
							Ext.Msg.alert("提示","只能选择没上架的产品");
							
						}
					} 
			}
		},{
			text:'下架产品',
			iconCls:'deleteIconCss',
			handler:function(){
				var selectRe = grid.getSelectionModel().getSelected();//选择一条数据
				if (selectRe == null|| selectRe == "undefined") {
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
				}else{
				var state =grid.getSelectionModel().getSelections()[0].data.sellState;
				Ext.Msg.alert("提示","产品状态"+state);
				if(state=="已上架"){
					var name =grid.getSelectionModel().getSelections()[0].data.prodName;
					Ext.Msg.confirm("信息提示","是否下架:"+name,function(but){
						if(but=='yes'){
						Ext.Msg.alert("提示","产品下架成功!");	
						}
					});
				}else{
					Ext.Msg.alert("提示","只能选择已上架产品");
				}
			}
				}
		}],
		loadMask:{
			msg:'正在加载表格数据，请等候。。。。'
		}
	});
	var upAndDownWin =new Ext.Window( {
        layout : 'fit',
        width : 700,
        title:'产品上下架管理',
        height : 400,
        resizable : false,//是否允许缩放
        draggable : true,// 是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        closeAction : 'hide',
        collapsible : true,// 是否可收缩
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        items:[grid]
    });
    /*********产品精选库**************/
	 var sm1 = new Ext.grid.CheckboxSelectionModel();
		// 定义自动当前页行号
		var rownum1= new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

		var record1= Ext.data.Record.create([
		                 {name:'productId'},
		                 {name:'prodName'},
		                 {name:'catlCode'},
		                 {name:'riskGrade'},
		                 {name:'levelName'},
		                 {name:'ifRecommend'},
		                 {name:'sellState'},
		                 {name:'expectIncome'}
		                                    ]);
		debugger;
		var cm1 = new Ext.grid.ColumnModel([
		                  rownum1,sm1,
		                  {header :'产品编号',dataIndex:'productId',id:"productId",width:150,sortable : true},
		                  {header:'产品名称',dataIndex:'prodName',id:'prodName',sortable : true,width:200},
		                  {header:'产品类别编码',dataIndex:'catlCode',sortable : true,width:150},    
		                  {header:'风险等级',dataIndex:'riskGrade',sortable : true,width:150},
		                  {header:'五级分类名称',dataIndex:'levelName',sortable : true,width:150},
		                  {header:'是否推荐',dataIndex:'ifRecommend',sortable : true},
		                  {header:'销售状态',dataIndex:'sellState',sortable : true},
		                  {header:'预期收益',dataIndex:'expectIncome',renderer: money('0,000.00'),sortable : true}

		                               ]);
		/**
		 * 数据存储
		 */
		var data1=[
		          ['200110','黄金100g','1323','A','4','是','已上架','300000'],
		          ['201005','黄金100g','1321','B','4','是','已上架','32123'],
		          ['201009','黄金100g','1325','B','4','是','已上架','432323'],
		          ['201102','黄金100g','1314','A','3','是','已上架','342342'],
		          ['201107','黄金100g','1322','A','3','否','已上架','334233'],
		          ['201111','黄金100g','1326','A','4','否','已上架','453343']
		          ];
		var store1 = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(data1),
			reader : new Ext.data.ArrayReader({}, record1)
		});

		// 每页显示条数下拉选择框
		var pagesize_combo1 = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
						[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '100',
			resizable : true,
			width : 85
		});

		// 默认加载数据
		store1.load(data);

		// 改变每页显示条数reload数据
		pagesize_combo1.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo1.getValue()),
			store1.reload({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo1.getValue())
				}
			});
		});
		// 分页工具栏
		var bbar1 = new Ext.PagingToolbar({
			pageSize : parseInt(pagesize_combo1.getValue()),
			store : store1,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo1 ]
		});
		
		var grid1 = new Ext.grid.GridPanel({
			frame:true,
			autoScroll:true,
			region:'center',
			store:store1,
			stripeRows:true,
			cm:cm1,
			sm:sm1,
			bbar:bbar1,
			loadMask:{
				msg:'正在加载表格数据，请等候。。。。'
			}
		});
		var goodProdWin =new Ext.Window( {
	        layout : 'fit',
	        width : 800,
	        title:'产品精选库',
	        height : 300,
	        resizable : false,//是否允许缩放
	        draggable : true,// 是否可以拖动
	        closable : true,// 是否可关闭
	        modal : true,
	        closeAction : 'hide',
	        collapsible : true,// 是否可收缩
	        titleCollapse : true,
	        buttonAlign : 'center',
	        border : false,
	        items:[grid1]
	    });
	/**********************************************/
    var productSearchPanel = new Ext.form.FormPanel({//查询panel
    
        title:'产品查询',
        height:120,
//      buttonAlign:'center',
        labelWidth:100,//label的宽度
        labelAlign:'right',
        frame:true,
        autoScroll : true,
        region:'north',
        split:true,
        items:[
                {
                    layout:'column',
                    items:[
                    {
                     columnWidth:.25,
                     layout:'form',
                     items:[
                        {
                            xtype:'textfield',
                            name:'PRODUCT_ID',
                            fieldLabel:'产品编号',
                            anchor:'100%'
                        }
                        ]
                     },{
                         columnWidth:.25,
                         layout:'form',
                         items:[{
                                xtype:'textfield',
                                name:'PROD_NAME',
                                fieldLabel:'产品名称',
                                anchor:'100%'
                            }
                            ]
                         },
                     {
                        columnWidth:.25,
                        layout:'form',
                        items:[
                        {    xtype:'combo',
    						 name:'riskGrade',
    						triggerAction:'all',
    						anchor:'100%',
    						fieldLabel:'风险等级',
    						mode:'local',
    						store: new Ext.data.ArrayStore({
    				        id: 0,
    				        fields: ['value','displayText'],
    				        data: [[1, 'A级'], [2, 'B级'],['3','C级'],['4','D级'],['5','E级']]
    				               }),
    				       valueField:'value',
    				       displayField:'displayText'
                        }
                        ]
                     },
                     {
                        columnWidth:.25,
                        layout:'form',
                        items:[             
                        {
                            name:'expectIncome',
                            anchor:'100%',
                            xtype:'textfield',          
                            fieldLabel:'预期收益'
                        }
                        ]           
                     },{
                         columnWidth:.25,
                         layout:'form',
                         items:[             
                         {
     						xtype:'combo',
    						id:'customerState1',
    						name:'ifRecommend',
    						triggerAction:'all',
    						anchor:'100%',
    						fieldLabel:'是否推荐',
    						mode:'local',
    						store: new Ext.data.ArrayStore({
    				        id: 0,
    				        fields: ['value','displayText'],
    				        data: [[1, '是'], [2, '否']]
    				               }),
    				       valueField:'value',
    				       displayField:'displayText'
    				 }
                         ]           
                      
                     }     
                    ]
                }
                ],
        buttonAlign:'center',
        buttons:[
        {
            text:'查询',
            handler:function(){             
                
                var parameters = productSearchPanel.getForm().getValues(false);
                
                productInfoStore.removeAll();
                productInfoStore.baseParams = {
                    'condition':Ext.util.JSON.encode(parameters)
                };
                productInfoStore.load({
                    params:{
                        start:0,
                        limit: parseInt(spagesize_combo.getValue())
                    }
                });
            }
        },{
            text:'重置',
            handler:function(){
                productSearchPanel.getForm().reset();
            }
        }
        ]

    });
    
    var productInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
    new Ext.grid.RowNumberer(),
    {header :'产品编号',dataIndex:'productId',id:"productId",width:150,sortable : true},
    {header:'产品名称',dataIndex:'prodName',id:'prodName',sortable : true,width:200},
    {header:'产品类别编码',dataIndex:'catlCode',sortable : true,width:150},    
    {header:'风险等级',dataIndex:'riskGrade',sortable : true,width:150},
    {header:'五级分类名称',dataIndex:'levelName',sortable : true,width:150},
    {header:'是否推荐',dataIndex:'ifRecommend',sortable : true},
    {header:'销售状态',dataIndex:'sellState',sortable : true},
    {header:'预期收益',dataIndex:'expectIncome',sortable : true}
    ]);
    
    var productInfoRecord = new Ext.data.Record.create([	
            	{name:'productId',mapping:'PRODUCT_ID'},
            	{name:'prodName',mapping:'PROD_NAME'},
            	{name:'catlCode',mapping:'CATL_CODE'},
            	{name:'prodTypeId',mapping:'PROD_TYPE_ID'},
            	{name:'catlName',mapping:'CATL_NAME'},
            	{name:'prodState',mapping:'PROD_STATE'},
            	{name:'PROD_STATE_ORA'},
            	{name:'prodCreator',mapping:'PROD_CREATOR'},
            	{name:'prodStartDate',mapping:'PROD_START_DATE'}
            	]);
    
    var productInfoReader = new Ext.data.JsonReader({//读取json数据的panel
    totalProperty:'json.count',
    root:'json.data'
    },productInfoRecord);
    
    var productInfoStore = new Ext.data.Store(
    {
        proxy:new Ext.data.HttpProxy({
        url:basepath+'/product-list.json?isHotSale='+'true',
        method:'GET'
        }),
        reader:productInfoReader
    }
    );
    
        //***********************

        // 每页显示条数下拉选择框
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

        // 改变每页显示条数reload数据
        spagesize_combo.on("select", function(comboBox) {
            sbbar.pageSize = parseInt(spagesize_combo.getValue()),
            productInfoStore.reload({
                params : {
                    start : 0,
                    limit : parseInt(spagesize_combo.getValue())
                }
            });
        });
        // 分页工具栏
        var sbbar = new Ext.PagingToolbar({
            pageSize : parseInt(spagesize_combo.getValue()),
            store : productInfoStore,
            displayInfo : true,
            displayMsg : '显示{0}条到{1}条,共{2}条',
            emptyMsg : "没有符合条件的记录",
            items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
        });

        //***********************

        
        
        productInfoStore.load({params:{     
            start:0,
            limit: parseInt(spagesize_combo.getValue())
//          'condition':'{"CATL_CODE":"A1"}'
        }});
    	var addRoleWindow = new Ext.Window({
    		layout : 'fit',
    		width : 1000,
    		height : 400,
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
    		items : [{html:' <div style="width:'+document.body.clientWidth+'px;height:'+document.body.clientHeight+'px;"><div style="position:absolute; left:0px; top:0px; " id=\'view\'></div></div>'}],
    		buttons : [{
    			text : '关闭',
    			handler : function() {
    				addRoleWindow.hide();
    				//document.getElementById('view').innerHTML = "";
    			}
    		} ]
    	});
    	
    
    	
    var productId = null;
    var productInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
        frame:true,
        id:'productInfoGrid',
        store:productInfoStore,
        loadMask:true,
        cm :productInfoColumns,
        bbar:sbbar,
        tbar:[{
      		text:'产品上下架管理',
      		iconCls:'editIconCss',
      		handler:function(){
      			upAndDownWin.show();
      		}
      	},{
      		text:'产品精选库',
      		iconCls:'resetIconCss ',
      		handler:function(){
      			goodProdWin.show();
      		}
      	},{
      		text:'新产品设定',
      		iconCls:'resetIconCss ',
      		handler:function(){
      			addWindow.show();
      			
      		}
      	},{
      		text:'产品推荐',
      		iconCls:'resetIconCss ',
      		handler:function(){
      			 var selectRe = productInfoGrid.getSelectionModel().getSelected();//选择一条数据
					if (selectRe == null
							|| selectRe == "undefined") {
						Ext.Msg.alert('提示','请选择一条记录!');
						return;
					} 
					Ext.Msg.confirm('信息提示',"是否推荐:"+selectRe.data.prodName,function(but){
						if(but=='yes'){
							Ext.Msg.alert("信息提示","产品推荐成功!");
						}else{
							Ext.Msg.alert('信息提示',"你取消了产品推荐!");
						}
						
					});
      		}
      	}],
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }

    });
    
    var view = new Ext.Viewport({//页面展示
        layout : 'fit',
        frame : true,
        items : [{
        layout:'border',
        items:[
            productLeftTreeForShow,
            {
                    region:'center',
                    layout:'border',
                    items:[
                    productSearchPanel,
                    {
                        region:'center',
                        layout:'fit',
                        
                        items:[productInfoGrid]
                    }
                    ]               
            }
        
        ]
        }]
    }); 
    

});