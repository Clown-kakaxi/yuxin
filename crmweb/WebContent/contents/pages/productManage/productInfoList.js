/**
 * 产品信息维护
 */
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
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
			Ext.getCmp('productLeftTreePanel').appendChild(children);
		}
	});

	/**
	 * 产品树面板，在模块左侧显示
	 */
	var productLeftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		id:'productLeftTreePanel',
		height : document.body.clientHeight,
		width : 200,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
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
			if(node.attributes.id!= ''){
				productInfoStore.baseParams = {
						'condition':'{"CATL_CODE":"'+node.attributes.CATL_CODE+'"}'
				};
				productInfoStore.load({//重新加载产品列表
					params:{
						limit:parseInt(spagesize_combo.getValue()),
						start:0
					}
				});		
			}
	 	}
	 }); 
	
	var productSearchPanel = new Ext.form.FormPanel({//查询panel
	
		title:'产品查询',
		height:120,
//		buttonAlign:'center',
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
						{
							name:'PROD_START_DATE_FROM',
							id:'PROD_START_DATE_FROM',
							anchor:'100%',
							xtype:'datefield',
							editable : false,
							format:'Y-m-d',
							fieldLabel:'发布日期从'
						}
					 	]
					 },
					 {
					 	columnWidth:.25,
					 	layout:'form',
					 	items:[			 	
						{
							name:'PROD_START_DATE_TO',
							id :'PROD_START_DATE_TO',
							anchor:'100%',
							xtype:'datefield',
							editable : false,
							format:'Y-m-d',				
							fieldLabel:'发布日期到'
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
				var start = Ext.getCmp('PROD_START_DATE_FROM').getValue();
										var end = Ext.getCmp('PROD_START_DATE_TO').getValue();
										if(start==''&&end !=''){
											Ext.Msg.alert('消息框','请先选择开始时间！');
											Ext.getCmp('PROD_START_DATE_TO').reset();
											return;
										}else if(end !=''&&start>end){
											Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
											Ext.getCmp('PROD_START_DATE_TO').reset();
											return;
										}
				
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
	{header :'产品编号',dataIndex:'productId',id:"productId",sortable : true},
	{header:'产品名称',dataIndex:'productName',id:'productName',sortable : true},
	{header:'产品分类名称',dataIndex:'catlName',id:'catlName',sortable : true},	
	{header:'产品发布日期',dataIndex:'productStartDate',id:'productStartDate',sortable : true},
	{header:'产品截止日期',dataIndex:'productEndDate',id:'productEndDate',sortable : true},
	{header:'利率',dataIndex:'rate',id:'rate',renderer: ratePercent(true), hidden:false,align : 'right',sortable : true},
	{header:'费率',dataIndex:'cost_rate',renderer: ratePercent(true),align : 'right', hidden:false,id:'cost_rate',sortable : true},
	{header:'期限',dataIndex:'limit_time',id:'limit_time',align : 'right', hidden:false,sortable : true},
	{header:'产品特征',dataIndex:'prod_charact',id:'prod_charact', hidden:false,sortable : true},
	{header:'目标客户描述',dataIndex:'obj_cust_disc',id:'obj_cust_disc', hidden:false,sortable : true},
	{header:'风险提示描述',dataIndex:'danger_disc',id:'danger_disc', hidden:true,sortable : true},
	{header:'营销渠道描述',dataIndex:'channel_disc',id:'channel_disc', hidden:true,sortable : true},
	{header:'担保要求描述 ',dataIndex:'assure_disc',id:'assure_disc', hidden:true,sortable : true},
	{header:'创建人 ',dataIndex:'productCreator',id:'productCreator',sortable : true}
	]);
	
	var productInfoRecord = new Ext.data.Record.create([	
	{name:'productId',mapping:'PRODUCT_ID'},
	{name:'productName',mapping:'PROD_NAME'},
	{name:'catlCode',mapping:'CATL_CODE'},
	{name:'catlName',mapping:'CATL_NAME'},
	{name:'productState',mapping:'PROD_STATE'},
	{name:'productCreator',mapping:'PROD_CREATOR'},
	{name:'productStartDate',mapping:'PROD_START_DATE'},
	{name:'productEndDate',mapping:'PROD_END_DATE'},
	{name:'productDepartment',mapping:'PROD_DEPT'},
	{name:'productDescription',mapping:'PROD_DESC'},
	{name:'rate',mapping:'RATE',type:'float'},
	{name:'cost_rate',mapping:'COST_RATE',type:'float'},
	{name:'limit_time',mapping:'LIMIT_TIME'},
	{name:'prod_charact',mapping:'PROD_CHARACT'},
	{name:'obj_cust_disc',mapping:'OBJ_CUST_DISC'},
	{name:'danger_disc',mapping:'DANGER_DISC'},
	{name:'channel_disc',mapping:'CHANNEL_DISC'},
	{name:'assure_disc',mapping:'ASSURE_DISC'}
	]);
	
	var productInfoReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},productInfoRecord);
	
	var productInfoStore = new Ext.data.Store(
	{
		proxy:new Ext.data.HttpProxy({
		url:basepath+'/product-list.json',
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
//			'condition':'{"CATL_CODE":"A1"}'
	}});
	
	var productInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		id:'产品列表',
		frame:true,
		id:'productInfoGrid',
		store:productInfoStore,
		loadMask:true,
		cm :productInfoColumns,
    	bbar:sbbar,
    	tbar:[{
    		text:'产品信息反馈',
    		iconCls:'resetIconCss',
    		handler:function(){
    		var record = productInfoGrid.getSelectionModel().getSelected().data.productId;
    		if(record==null || record=="undefined"){
  				Ext.MessageBox.alert('提示','请选择一条记录');
  				return;
  			} 
    		Ext.getCmp("productId3").setValue(record);//把产品ID付给productId3
    		feedbackwind.show();
    		//加载数据
    		   feedbackStore.baseParams = {
    			               productId : record
    		   };
    		   
    			feedbackStore.load({
    				params:{	
    					start:0,
    					limit: parseInt(pagesize_combo.getValue())
    				}
    			});
    	}
    	},
        {
            text:'产品介绍',
            iconCls:'detailIconCss',
            handler:function()
            {  
	        	var record = productInfoGrid.getSelectionModel().getSelected();
	            if(record==null || record=="undefined"){
	                Ext.MessageBox.alert('提示','请选择一条记录');
	                return;
	            }      
	            productJSWind.setTitle('产品介绍');               
	            productJSWind.show();
	            
	            Ext.Ajax.request({
					url:basepath+'/ocrmSysRicheditInfo!indexPage.json',
					method:'GET',
					params:{
						relId:record.data.productId
					},
					success:function(r){
						if(Ext.decode(r.responseText).json.data.length>0){
							docid = Ext.decode(r.responseText).json.data[0].id;
							var context = Ext.decode(r.responseText).json.data[0].content;
							Ext.getCmp('prodJSPanel').body.update(context);
						}
					},failure:function(){
					}
				});
	            
//	            prodJSTabs.activate(0);
//	            prodJSTabs.activate(1);
//	            productJSPanel.getForm().loadRecord(record);
	            
            }
        }],
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }

	});
	//产品介绍window               
    var productJSWind = new Ext.Window(
    {
        closeAction:'hide',
        maximized:true,
        closable:true,
        constrain:true,
        modal:true,
        maximizable:true,
        autoScroll : true,
        height:500,
        width:800,
        items : [
            new Ext.FormPanel({ //产品信息formpanel
		        frame:false,
		        id:'prodJSPanel',
		        region:'north',
		        bodyStyle:'padding:5px 5px 0',
		        width: '100%',
		        height:500,
		        defaults:{autoHeight: true},
		        autoScroll : true,
		        split:true
		    })
        ],
        buttonAlign:'center',
        buttons:[
            {
                text:'返回',
                handler:function()
                {
            		productJSWind.hide();
                }                           
            }
        ]
            
    });  
	
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		items : [{
		layout:'border',
		items:[
			productLeftTreeForShow,
			//orgTreeForShow,
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