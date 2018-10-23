/*
 * 客户存贷款明细查询
 * 
 * 姚亮
 * */

Ext.onReady(function(){

	var depAndLonDetailSearchPanel = new Ext.form.FormPanel({//查询panel
		height:130,
		labelWidth:120,//label的宽度
		labelAlign:'right',
		frame:true,
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
					columnWidth:.25,
					xtype:'combo',
					name:'instn_no',
					hiddenName:'instn_no',
					fieldLabel:'机构',
					anchor:'90%'
				},
			 	{
					columnWidth:.25,
					xtype:'combo',
					name:'instn_level',
					hiddenName:'instn_level',
					fieldLabel:'机构层次',
					anchor:'90%'
				},				
				{
					columnWidth:.25,
					anchor:'90%',
					name:'cur_type',
					hiddenName:'cur_type',
					xtype:'combo',
					fieldLabel:'币种',
					triggerAction:'all',
					anchor:'90%',
					mode:'local',
					store: new Ext.data.Store({			        
			        autoLoad:true,
			        proxy:new Ext.data.HttpProxy({
			        	url:basepath+'/lookup.json?name=CUR_TYPE',
			        	method:'GET'
			        }),
			        reader:new Ext.data.JsonReader({
			        	root:'JSON'
			        },['key','value']),
			        fields: [
			            'key',
			            'value'
			        ]			        
			 	   }),
			       valueField:'key',
			       displayField:'value'					
			    }				
				]
			 },			 
			 {			columnWidth:.5,
						layout:'column',
						items:[
						
							{
								layout:'form',
								labelWidth:120,
								columnWidth:.5,
								items:[
								{
									name:'crm_dt',
									
									anchor:'90%',
									xtype:'datefield',
									format:'Y-m-d',
									fieldLabel:'统计日期'
								},
								{
									name:'tjkj_type',
									name:'tjkj_type',
									anchor:'90%',
									xtype:'combo',
									format:'Y-m-d',
									mode:'local',
									store: new Ext.data.Store({			        
							        autoLoad:true,
							        proxy:new Ext.data.HttpProxy({
							        	url:basepath+'/lookup.json?name=TJKJ_TYPE',
							        	method:'GET'
							        }),
							        reader:new Ext.data.JsonReader({
							        	root:'JSON'
							        },['key','value']),
							        fields: [
							            'key',
							            'value'
							        ]			        
							 	   }),									
								   fieldLabel:'统计口径'
								},
								{
									name:'cust_name',								
									anchor:'90%',
									xtype:'textfield',
									format:'Y-m-d',
									fieldLabel:'客户名称'
								}								
								]
							},
							{
								layout:'form',
								labelWidth:120,
								columnWidth:.5,
								items:[
								{
								name:'sts',
								hiddenName:'sts',
								anchor:'90%',
								xtype:'combo',
								fieldLabel:'客户状态'
								},
								{
								name:'cust_zzdm',
								anchor:'90%',
								xtype:'textfield',
								fieldLabel:'组织机构代码'
								}								
								]							
							}
						]
			 },			 
			 {
			 	columnWidth:.25,
			 	layout:'form',
			 	labelWidth:80,
				items:[	
				{
							name:'scope',
							hiddenName:'scope',
							xtype:'combo',
							anchor:'90%',
							fieldLabel:'客户规模'
			 	},
				{
							name:'crm_scope',
							hiddenName:'crm_scope',
							xtype:'combo',
							anchor:'90%',
							fieldLabel:'考核口径客户规模'
			 	}			 	
				]
			}
		],
		
			buttonAlign:'center',
			buttons:[
			{
				text:'查询',
				handler:function(){
					
					var paramsObj = depAndLonDetailSearchPanel.getForm().getValues(false);
					depAndLonDetailStore.removeAll();
					depAndLonDetailStore.load({
						params:{
					
						start:0,
						limit:10,
						'condition':Ext.util.JSON.encode(paramsObj)
											
						}
					});
				}
			}
			]
		}
		
	 ]
	}
	);
	
	var depAndLonDetailWindow = new Ext.Window(//新增一个特征项 弹出window
	{		
		width:600,
		height:300,
		closeAction:'hide',
		closable:true,
		maximizable:true,
		buttonAlign:'right',
		border:false,
		layout:'fit',
		draggable:true,
		collapsible:true,
		titleCollapse:true,
		items:
		new Ext.form.FormPanel(
				{//查询panel
				height:130,
				id:'depAndLonDetailAdvanceSearch',
				labelWidth:120,//label的宽度
				labelAlign:'right',
				frame:true,
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
							columnWidth:.25,
							xtype:'combo',
							name:'instn_no',
							fieldLabel:'机构',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'instn_level',
							hiddenName:'instn_level',
							fieldLabel:'机构层次',
							anchor:'90%',
							mode:'local',
							store: new Ext.data.Store({			        
					        autoLoad:true,
					        proxy:new Ext.data.HttpProxy({
					        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
					        	method:'GET'
					        }),
					        reader:new Ext.data.JsonReader({
					        	root:'JSON'
					        },['key','value']),
					        fields: [
					            'key',
					            'value'
					        ]			        
					 	   }),
					 	   valueField:'key',
					 	   displayField:'value'
						},				
						{
							columnWidth:.25,
							anchor:'90%',
							name:'cur_type',
							hiddenName:'cur_type',
							xtype:'combo',
							fieldLabel:'币种',
							triggerAction:'all',
							anchor:'90%',
							mode:'local',
							store: new Ext.data.Store({			        
					        autoLoad:true,
					        proxy:new Ext.data.HttpProxy({
					        	url:basepath+'/lookup.json?name=CUR_TYPE',
					        	method:'GET'
					        }),
					        reader:new Ext.data.JsonReader({
					        	root:'JSON'
					        },['key','value']),
					        fields: [
					            'key',
					            'value'
					        ]			        
					 	   }),
					       valueField:'key',
					       displayField:'value'					
					    },
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'cust_typ',
							hiddenName:'cust_typ',
							fieldLabel:'行业大类',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'cust_typ_desc',
							hiddenName:'cust_typ_desc',
							fieldLabel:'行业小类',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_bal_from',							
							fieldLabel:'存款时点余额起始',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_bal_to',							
							fieldLabel:'存款时点余额截止',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_year_from',							
							fieldLabel:'存款日均余额起始',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_year_to',							
							fieldLabel:'存款日均余额截止',
							anchor:'90%'
						}						
						]
					 },			 
					 {			columnWidth:.5,
								layout:'column',
								items:[
								
									{
										layout:'form',
										labelWidth:120,
										columnWidth:.5,
										items:[
										{
										name:'crm_dt',
										anchor:'90%',
										xtype:'datefield',
										format:'Y-m-d',
										fieldLabel:'统计日期'
										},
										{
										name:'tjkj_type',
										hiddeName:'tjkj_type',
										anchor:'90%',
										xtype:'combo',
										format:'Y-m-d',
										fieldLabel:'统计口径',
										store: new Ext.data.Store({			        
								        autoLoad:true,
								        proxy:new Ext.data.HttpProxy({
								        	url:basepath+'/lookup.json?name=TJKJ_TYPE',
								        	method:'GET'
								        }),
								        reader:new Ext.data.JsonReader({
								        	root:'JSON'
								        },['key','value']),
								        fields: [
								            'key',
								            'value'
								        ]			        
								 	   }),
								 	   valueField:'key',
								 	   displayField:'value'
										},
										{
										name:'cust_name',
										anchor:'90%',
										xtype:'textfield',
										format:'Y-m-d',
										fieldLabel:'客户名称'
										},
										{
										name:'cust_big_lev',
										hiddeName:'cust_big_lev',
										anchor:'90%',
										xtype:'combo',
										format:'Y-m-d',
										fieldLabel:'大客户级别'
										},
										{
										name:'cust_small_lev',
										hiddeName:'cust_small_lev',
										anchor:'90%',
										xtype:'combo',
										format:'Y-m-d',
										fieldLabel:'中小客户级别'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_bal_from',							
											fieldLabel:'贷款时点余额起始',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_bal_to',							
											fieldLabel:'贷款时点余额截止',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_year_from',							
											fieldLabel:'贷款日均余额起始',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_year_to',							
											fieldLabel:'贷款日均余额截止',
											anchor:'90%'
										}										
										]
									},
									{
										layout:'form',
										labelWidth:120,
										columnWidth:.5,
										items:[
										{
										name:'sts',
										hiddenName:'sts',
										anchor:'90%',
										xtype:'combo',
										fieldLabel:'客户状态'
										},
										{
										name:'cust_zzdm',
										anchor:'90%',
										xtype:'textfield',
										fieldLabel:'组织机构代码'
										},
										{
										name:'cust_lev',
										anchor:'90%',
										xtype:'textfield',
										fieldLabel:'客户评级'
										}									
										]							
									}
								]
					 },			 
					 {
					 	columnWidth:.25,
					 	layout:'form',
					 	labelWidth:80,
						items:[	
						{
									name:'scope',
									hiddenName:'scope',
									xtype:'combo',
									anchor:'90%',
									fieldLabel:'客户规模'
					 	},
						{
									name:'crm_scope',
									hiddenName:'crm_scope',
									xtype:'combo',
									anchor:'90%',
									fieldLabel:'考核口径客户规模'
					 	}			 	
					  ]
					}
				],
					buttonAlign:'center',
					buttons:[
						{
							text:'查询',
							handler:function(){
								
								var paramsObj = Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().getValues(false);
								depAndLonIncreStore.removeAll();
								depAndLonIncreStore.load(
								{
									params:{
											
												start:0,
												limit:10,
												'condition':Ext.util.JSON.encode(paramsObj)
																	
									}
								}
								);
							}
						}
					]
				}
				
			 ]
			}
			)		,
		buttonAlign:'center',
		buttons:[
		{ 
			text:'保  存',
			handler:function()
			{
				var productId = featureAddPanel.getForm().findField("productId").value;
				Ext.Ajax.request({
					url:basepath+'/product-feature-value.json',
					form:featureAddPanel.getForm().id,
					method:'POST',
					success:function(){
						Ext.MessageBox.alert('提示','操作成功!');
						featureAddWindow.hide();
						featurePropertyStore.removeAll();
						featurePropertyStore.reload({
							params:{
								"condition":'{ "PRODUCT_ID" : "'+productId+'" }'
							}
						});
					}									
				});
			}
		},
		{
			text:'取  消',
			handler:function()
			{
				featureAddWindow.hide();
			}
		}
		]	
	}
	);

	
	var depAndLonDetailColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	 new Ext.grid.RowNumberer(),
	{header:'统计日期',dataIndex:'crm_dt',id:'crm_dt'},
	{header:'客户名称',dataIndex:'cust_name',id:'cust_name',width:150},
	{header:'组织机构代码',dataIndex:'cust_zzdm',id:'cust_zzdm'},
	{header:'客户状态',dataIndex:'sts',id:'sts',width:150},
	{header:'客户规模',dataIndex:'scope',id:'scope'},
	{header:'考核口径客户规模',dataIndex:'crm_scope',id:'crm_scope'},
	{header:'行业大类',dataIndex:'cust_typ',id:'cust_typ'},
	{header:'行业小类',dataIndex:'cust_typ_desc',id:'cust_typ_desc'},
	{header:'客户类型',dataIndex:'cust_crm_typ',id:'cust_crm_typ'},
	{header:'大客户级别',	  dataIndex:'cust_big_lev',id:'cust_big_lev'},
	{header:'中小客户级别',dataIndex:'cust_small_lev',id:'cust_small_lev',align:'right'},
	{header:'统计口径',dataIndex:'caculate_type',width:100,align:'right'},

	{header:'存款:活期存款',dataIndex:'dep_bal1',width:100,align:'right'},
	{header:'存款:定期存款',dataIndex:'dep_bal2',width:100,align:'right'},
	{header:'存款:协议存款',dataIndex:'dep_bal3',width:100,align:'right'},
	{header:'存款:通知存款',dataIndex:'dep_bal4',width:100,align:'right'},
	{header:'存款:保证金存款',dataIndex:'dep_bal5',width:100,align:'right'},
	{header:'存款:保本机构理财',dataIndex:'dep_bal6',width:100,align:'right'},
	{header:'存款:其他存款',dataIndex:'dep_bal7',width:100,align:'right'},
	{header:'存款余额合计',dataIndex:'dep_bal',width:100,align:'right'},

	{header:'贷款:流动资金贷款时点余额',dataIndex:'lon_bal1',width:100,align:'right'},
	{header:'贷款:固定资产贷款时点余额',dataIndex:'lon_bal2',width:100,align:'right'},
	{header:'贷款:贸易融资贷款时点余额',dataIndex:'lon_bal3',width:100,align:'right'},
	{header:'贷款:贴现时点余额',dataIndex:'lon_bal4',width:100,align:'right'},
	{header:'贷款:其他贷款时点余额',dataIndex:'lon_bal5',width:100,align:'right'},
	{header:'贷款:不良贷款时点余额',dataIndex:'lon_bal6',width:100,align:'right'},
	{header:'贷款时点余额合计',dataIndex:'lon_bal',width:100,align:'right'}

	]);
	
	var depAndLonDetailRecord = new Ext.data.Record.create([
	{name:'crm_dt'},
	{name:'cust_name'},
	{name:'cust_zzdm'},
	{name:'sts'},
	{name:'scope'},
	{name:'crm_scope'},
	{name:'cust_typ'},
	{name:'cust_typ_desc'},	
	{name:'cust_crm_typ'},
	{name:'cust_big_lev'},
	{name:'cust_small_lev'},
	{name:'caculate_type'},
	
	{name:'dep_bal1'},
	{name:'dep_bal2'},
	{name:'dep_bal3'},
	{name:'dep_bal4'},
	{name:'dep_bal5'},
	{name:'dep_bal6'},
	{name:'dep_bal7'},
	
	{name:'lon_bal1'},
	{name:'lon_bal2'},
	{name:'lon_bal3'},
	{name:'lon_bal4'},
	{name:'lon_bal5'},
	{name:'lon_bal6'},
	{name:'lon_bal'}
	
	]);
	
//	 var pageCombo = new Ext.form.ComboBox({
//         name : 'pagesize',
//         triggerAction : 'all',
//         mode : 'local',
//         store : new Ext.data.ArrayStore({
//             fields : ['value', 'text'],
//             data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
//         }),
//         valueField : 'value',
//         displayField : 'text',
//         value : '10',
//         editable : false,
//         listeners:{
//         'select':function(combo){         	
//         	pageBar.pageSize = combo.getValue();
//         	depAndLonIncreStore.removeAll();
//         	depAndLonIncreStore.load({
//         		params:{
//         			start:0,
//         			limit:combo.getValue()
//         		}
//         	});
//         }
//         },
//         width : 85
//     });

    var pagesize_combo = new Ext.form.ComboBox({
         name : 'pagesize',
         triggerAction : 'all',
         mode : 'local',
         store : new Ext.data.ArrayStore({
             fields : ['value', 'text'],
             data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
         }),
         valueField : 'value',
         displayField : 'text',
         value : '10',
         editable : false,
         width : 85
     });
    var number = parseInt(pagesize_combo.getValue());
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		depAndLonDetailStore.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : depAndLonDetailStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });	   
     
	var depAndLonDetailReader = new Ext.data.JsonReader({//读取json数据的panel
	root:'depAndLonDetailMap.data'
	},depAndLonDetailRecord);
	
	var depAndLonDetailStore = new Ext.data.Store(
	{
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/dep-and-lon-detail',
			method:'GET'			
		}),
		reader:depAndLonDetailReader
	}
	);
	depAndLonDetailStore.load({
		params:{
			start:0,
			limit:pagesize_combo.getValue()			
		}
	});
	
	var depAndLonDetailGrid =  new Ext.grid.GridPanel({//产品列表数据grid 
		frame:true,
		id:'depAndLonDetailListGrid',
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true
		}),
		store:depAndLonDetailStore,

		cm :depAndLonDetailColumns,
		bbar:bbar
				
	});
	
	var view = new Ext.Viewport({//页面展示
		layout:'fit',
		items:[
			{
					layout:'border',
					items:
					[depAndLonDetailSearchPanel,
					{
						region:'center',
						layout:'fit',
						title:'客户存贷款明细查询列表',
						items:[depAndLonDetailGrid]
					}
					]				
			}
		
		]

	});	

});