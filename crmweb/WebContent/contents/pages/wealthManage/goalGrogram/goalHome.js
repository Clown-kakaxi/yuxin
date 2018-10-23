/**
 * 财务健康诊断
 */
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	
	//查询panel
	var productSearchPanel = new Ext.form.FormPanel({
		title:'财务健康诊断',
		region:'north',
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
							fieldLabel:'客户名称',
							anchor:'100%'
						}
						]
					 },{
						 columnWidth:.25,
						 layout:'form',
						 items:[{
								xtype:'textfield',
								name:'PROD_NAME',
								fieldLabel:'客户号',
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
							fieldLabel:'证件类型'
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
							xtype:'textfield',
							editable : false,
							format:'Y-m-d',				
							fieldLabel:'证件号码'
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
		{header :'客户号',dataIndex:'productId',id:"productId",sortable : true},
		{header:'客户名称',dataIndex:'productName',id:'productName',sortable : true},
		{header:'报告状态',dataIndex:'catlName',id:'catlName',sortable : true},	
		{header:'创建日期',dataIndex:'productStartDate',id:'productStartDate',sortable : true},
		{header:'创建人',dataIndex:'productEndDate',id:'productEndDate',sortable : true},
		{header:'创建机构',dataIndex:'rate',id:'rate',renderer: ratePercent(true), hidden:false,align : 'right',sortable : true},
		{header:'最近维护日期',dataIndex:'cost_rate',renderer: ratePercent(true),align : 'right', hidden:false,id:'cost_rate',sortable : true}
	]);
	
	var productInfoRecord = new Ext.data.Record.create([	
		{name:'productId',mapping:'PRODUCT_ID'},
		{name:'productName',mapping:'PROD_NAME'},
		{name:'catlCode',mapping:'CATL_CODE'},
		{name:'catlName',mapping:'CATL_NAME'},
		{name:'productState',mapping:'PROD_STATE'},
		{name:'productCreator',mapping:'PROD_CREATOR'},
		{name:'productStartDate',mapping:'PROD_START_DATE'}	
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
		region:'center',
		loadMask:true,
		cm :productInfoColumns,
    	bbar:sbbar,
    	tbar:[{
    		text:'新增',
    		iconCls:'addIconCss'
    	},{
    		text:'修改',
    		iconCls:'editIconCss'	
    	},{
    		text:'删除',
    		iconCls:'deleteIconCss'	
    	},{
    		text:'查看报告',
    		iconCls:'taskDistrIconCss',
    		handler:function() {
    			infoWin.show();
    		}	
    	}],
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
	});
	
	var teamcm = new Ext.grid.ColumnModel([//gridtable中的列定义
   		new Ext.grid.RowNumberer(),   		
   		{header:'资产',dataIndex:'productName',id:'productName',sortable : true},
   		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
   	]);
	
	var teamcm1 = new Ext.grid.ColumnModel([//gridtable中的列定义
  		new Ext.grid.RowNumberer(),   		
  		{header:'资产',dataIndex:'productName',id:'productName',sortable : true},
  		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
  	]);
	
	var teamcm2 = new Ext.grid.ColumnModel([//gridtable中的列定义
		new Ext.grid.RowNumberer(),   		
		{header:'资产',dataIndex:'productName',id:'productName',sortable : true},
		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
	]);
	
	var teamcm3 = new Ext.grid.ColumnModel([//gridtable中的列定义
  		new Ext.grid.RowNumberer(),   		
  		{header:'资产',dataIndex:'productName',id:'productName',sortable : true},
  		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
  	]);
	
	var teamcm4 = new Ext.grid.ColumnModel([//gridtable中的列定义
  		new Ext.grid.RowNumberer(),   		
  		{header:'资产',dataIndex:'productName',id:'productName',sortable : true},
  		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
  	]);

	var teamcm5 = new Ext.grid.ColumnModel([//gridtable中的列定义
  		new Ext.grid.RowNumberer(),   		
  		{header:'资产',dataIndex:'productName',id:'productName',sortable : true},
  		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
  	]);
	
	var teamcm6 = new Ext.grid.ColumnModel([//gridtable中的列定义
  		new Ext.grid.RowNumberer(),   		
  		{header:'负债',dataIndex:'productName',id:'productName',sortable : true},
  		{header :'金额(元)',dataIndex:'productId',id:"productId",sortable : true,editor:new Ext.form.TextField()} //一般的编辑框
  	]);

	var teamgrid = new Ext.grid.EditorGridPanel( {
		title : '现金及现金等价物',
		height : 150,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid',
		store : productInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : teamcm, // 列模型
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var teamgrid1 = new Ext.grid.EditorGridPanel( {
		title : '定期存款',
		height : 150,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid1',
		store : productInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : teamcm1, // 列模型
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var teamgrid2 = new Ext.grid.EditorGridPanel( {
		title : '投资资产',
		height : 150,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid2',
		store : productInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : teamcm2, // 列模型
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var teamgrid3 = new Ext.grid.EditorGridPanel( {
		title : '家居资产',
		height : 150,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid3',
		store : productInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : teamcm3, // 列模型
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var teamgrid4 = new Ext.grid.EditorGridPanel( {
		title : '限定性资产',
		height : 150,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid4',
		store : productInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : teamcm4, // 列模型
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var teamgrid5 = new Ext.grid.EditorGridPanel( {
		title : '保险',
		height : 150,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid5',
		store : productInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : teamcm5, // 列模型
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var teamgrid6 = new Ext.grid.EditorGridPanel( {
		title : '贷款',
		height : 200,
		frame : true,
		overflow : 'auto',
		autoScroll : true,
		id:'teamgrid6',
		store : productInfoStore, // 数据存储		
		stripeRows : true, // 斑马线
		cm : teamcm6, // 列模型 
		//sm : teamsm,
		//bbar : bbar,
		clicksToEdit:1,  //单击可编辑  默认是2 为双击
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	//暂时没用的代码
	var panel3 = new Ext.Panel({
        layout:'table',
        defaults: {bodyStyle:'padding:0px'},
        layoutConfig: {columns: 4 },
        items: [{
            width:165,
            layout: 'form',
            labelWidth:100,
            labelAlign:'right',
            items: [{xtype:'label',fieldLabel: '资产',name: 'termId',id:'termId',anchor:'95%'}]
        },{
      	  	width:165,
      	  	layout: 'form',
      	  	labelWidth:100,
      	  	labelAlign:'right',
      	  	items: [{xtype:'label',fieldLabel: '金额',name: 'timeLapse',id:'timeLapse',anchor:'95%'}]
        },{
            width:165,
            layout: 'form',
            labelWidth:100,
            labelAlign:'right',
            items: [{xtype:'label',fieldLabel: '负债', name: 'consumerId',anchor:'95%',id: 'consId'}]       
       },{
      	 	 width:165,
	    	 layout: 'form',
	    	 labelWidth:100,
	    	 labelAlign:'right',
	    	 items: [{xtype:'label',fieldLabel: '金额',name: 'consumerArea',id:'areaId',anchor:'95%'}]
	     },{
        	 width:165,
        	 layout: 'form',
        	 labelWidth:165,
        	 labelAlign:'right',
        	 items: [{xtype:'label',fieldLabel: '现金及现金等价物',name: 'termStatus',id:'termStatus',anchor:'95%'}]
         },{
         },{
        	 width:165,
             layout: 'form',
             labelWidth:165,
             labelAlign:'right',
             items: [{xtype:'label',fieldLabel: '贷款',name: 'timeLapse2',id:'timeLapse2',anchor:'95%'}]
         },{
         }]
	});
	
	
	var parnelSun  = new Ext.Panel({
        layout:'table',
        defaults: {bodyStyle:'padding:0px'},
        layoutConfig: {columns: 3 },
        items: [{
            //width:165,
            layout: 'form',
            labelWidth:250,
            labelAlign:'right',
            items: [{xtype:'label',fieldLabel: '总资产',name: 'termId',id:'termId',anchor:'95%'}]
        },{
      	  	//width:165,
      	  	layout: 'form',
      	  	labelWidth:250,
      	  	labelAlign:'right',
      	  	items: [{xtype:'label',fieldLabel: '总负债',name: 'timeLapse',id:'timeLapse',anchor:'95%'}]
        },{
            //width:165,
            layout: 'form',
            labelWidth:250,
            labelAlign:'right',
            items: [{xtype:'label',fieldLabel: '净资产', name: 'consumerId',anchor:'95%',id: 'consId'}]       
       }]
	});

	//资产图表
	var char_set_1 = new Ext.form.FieldSet( {
		height : 200,
		title : '客户资产信息',
		items : {
			region : 'center',
			id : 'center-panel',
			layout : 'fit',
			html : '<div id="chartdiv1"></div>'
		}
	});
	
	//负债图表
	var char_set_2 = new Ext.form.FieldSet( {
		height : 200,
		title : '客户负债信息',
		items : {
			region : 'center',
			id : 'center-panel',
			layout : 'fit',
			html : '<div id="chartdiv1"></div>'
		}
	});
	
	
	//客户资产信息
	var tab_1 = new Ext.Panel( {
		layout : 'form',
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .33,
				items : [ teamgrid ]
			}, {
				columnWidth : .33,
				items : [ teamgrid1 ]
			} ,{
				columnWidth : .34,
				items : [ teamgrid2 ]
			}]
		},{
			layout:'column',
			items : [{
					columnWidth : .33,
					items : [ teamgrid3 ]
				}, {
					columnWidth : .33,
					items : [ teamgrid4 ]
				} ,{
					columnWidth : .34,
					items : [ teamgrid5 ]
			}]
		},{
			layout:'column',
			items:[{
				columnWidth : 0.33,
				items : [ teamgrid6 ]
			},{
				columnWidth : .33,
				items : [ char_set_1 ]
			},{
				columnWidth : .34,
				items : [ char_set_2 ]
			}]
		},{
			layout:'column',
			items:[{ 
				columnWidth : 1,
				items:[parnelSun]
			}]
		}]
	});
	
	
	var tabs = new Ext.TabPanel( {
		defaults : {
			overflow : 'auto',
			autoScroll : true
		},
		activeTab : 0,
		items : [ {
			title : '资产负债信息',
			items : [ tab_1 ]

		}, {
			title : '收支信息'
			//items : [ tab_4 ]
		}, {
			title : '财务现状'
			//items : [ tab_3 ]
		}, {
			title : '财务指标分析'
			//items : [ tab_5 ]
		} ]
	
	});
	
	
	var infoWin = new Ext.Window( {
		resizable : false,
		collapsible : false,
		draggable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		animCollapse : false,
		border : false,
		loadMask : true,
		closable : true,
		constrain : true,
		layout : 'fit',
		width : 1000,
		height : 460,
		buttonAlign : "center",
		title : '财务健康诊断',
		items : [ tabs ]
	});
		
	
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		layout:'border',
		items : [
				productSearchPanel,
				productInfoGrid
		]
	});	
	

});