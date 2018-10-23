/*
 * 客户产品查询
 * 
 * 姚亮
 * */
Ext.onReady(function(){

	var cutomerProductSearchPanel = new Ext.form.FormPanel({//查询panel
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
					fieldLabel:'机构号',
					anchor:'90%'
				},
			 	{
					columnWidth:.25,
					xtype:'combo',
					name:'instn_level',
					hiddenName:'instn_level',
					fieldLabel:'机构层次',
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
								name:'stat_dt',								
								anchor:'90%',
								xtype:'datefield',
								format:'Y-m-d',
								fieldLabel:'统计日期'
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
								hiddenName:'cust_sts',
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
							fieldLabel:'大客户级别'
			 	},
				{
							name:'crm_scope',
							hiddenName:'crm_scope',
							xtype:'combo',
							anchor:'90%',
							fieldLabel:'中小客户级别'
			 	}			 	
				]
			}
		],
		
			buttonAlign:'center',
			buttons:[
			{
				text:'查询',
				handler:function(){
					
					var paramsObj = cutomerProductSearchPanel.getForm().getValues(false);
					cutomerProductStore.removeAll();
					cutomerProductStore.load({
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
	var cutomerProductColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	new Ext.grid.RowNumberer(),
	{header:'统计日期',dataIndex:'stat_dt',id:'stat_dt'},
	{header:'机构号',dataIndex:'instn_no',id:'instn_no',width:150},
	{header:'机构名称',dataIndex:'instn_name',id:'instn_name'},
	{header:'客户名称',dataIndex:'cust_name',id:'cust_name',width:150},
	{header:'组织机构代码',dataIndex:'cust_zzdm',id:'cust_zzdm'},
	{header:'产品编号',dataIndex:'prd_id',id:'prd_id'},
	{header:'产品名称',dataIndex:'prd_name',id:'prd_name'},
	{header:'产品一级分类',dataIndex:'cust_typ_desc',id:'cust_typ_desc'},
	{header:'产品二级分类',dataIndex:'cust_crm_typ',id:'cust_crm_typ'},
	{header:'产品三级分类',	  dataIndex:'cust_big_lev',id:'cust_big_lev'},
	{header:'产品四级分类',dataIndex:'cust_small_lev',id:'cust_small_lev',align:'right'},
	{header:'时点余额',dataIndex:'caculate_type',width:100,align:'right'},
	{header:'年均余额',dataIndex:'dep_bal1',width:100,align:'right'},
	{header:'销售金额',dataIndex:'dep_bal2',width:100,align:'right'},
	{header:'利息收入',dataIndex:'dep_bal3',width:100,align:'right'},
	{header:'利息支出',dataIndex:'dep_bal4',width:100,align:'right'},
	{header:'费用收入',dataIndex:'dep_bal5',width:100,align:'right'}

	]);
	
	var cutomerProductRecord = new Ext.data.Record.create([
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
	
	 var pageCombo = new Ext.form.ComboBox({
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
         listeners:{
         'select':function(combo){         	
         	pageBar.pageSize = combo.getValue();
         	cutomerProductStore.removeAll();
         	cutomerProductStore.load({
         		params:{
         			start:0,
         			limit:combo.getValue()
         		}
         	});
         }
         },
         width : 85
     });
     
	var cutomerProductReader = new Ext.data.JsonReader({//读取json数据的panel
	root:'cutomerProductMap.data'
	},cutomerProductRecord);
	
	var cutomerProductStore = new Ext.data.Store(
	{
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/dep-and-lon-detail',
			method:'GET'			
		}),
		reader:cutomerProductReader
	}
	);
	cutomerProductStore.load({
		params:{
			start:0,
			limit:pageCombo.getValue()			
		}
	});
	
	var cutomerProductGrid =  new Ext.grid.GridPanel({//产品列表数据grid 
		frame:true,
		id:'depAndLonDetailListGrid',
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true
		}),
		store:cutomerProductStore,

		cm :cutomerProductColumns,
		bbar:
			{	
				xtype:'paging',
				pageSize : pageCombo.getValue(),
				store : cutomerProductStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pageCombo ]
			}	
	});
	
	var view = new Ext.Viewport({//页面展示
		layout:'fit',
		items:[
			{
					layout:'border',
					items:
					[cutomerProductSearchPanel,
					{
						region:'center',
						layout:'fit',
						title:'客户存贷款明细查询列表',
						items:[cutomerProductGrid]
					}
					]				
			}
		
		]

	});	

});