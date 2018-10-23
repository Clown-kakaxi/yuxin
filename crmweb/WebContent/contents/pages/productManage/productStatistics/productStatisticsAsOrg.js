/*
 * 产品统计
 * 2011-06-09
 * 姚亮
 * */

Ext.onReady(function(){

	var productSearchPanel = new Ext.form.FormPanel({//查询panel
		title:'产品统计',
		height:120,
		labelWidth:120,//label的宽度
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
					xtype:'textfield',
					name:'ORG_CODE',
					fieldLabel:'机构号',
					anchor:'90%'
				},
				{
					columnWidth:.25,
					anchor:'90%',
					name:'ORG_LEVEL',
					xtype:'combo',
					fieldLabel:'机构层次',
					triggerAction:'all',
					anchor:'90%',
					mode:'local',
					store: new Ext.data.SimpleStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '市场产品部'],[2,'中小企业事业部'],[3,'国际业务部'],[4,'公司业务部'],[5, '投资银行部']]
			 	   }),
			       valueField:'myId',
			       displayField:'displayText'					
			    }				
				]
			 },			 
			 {			columnWidth:.5,
						layout:'column',
						items:[
						
							{
								layout:'form',
								labelWidth:120,
								columnWidth:.55,
								items:[
								{
								name:'CACULATE_DATE_FROM',
								anchor:'97%',
								xtype:'datefield',
								format:'Y-m-d',
								fieldLabel:'统计日期'
								}]
							},
							{
								layout:'form',
								labelWidth:20,
								columnWidth:.45,
								items:[
								{
								name:'CACULATE_DATE_TO',
								anchor:'78%',
								xtype:'datefield',
								format:'Y-m-d',
								labelSeparator:'',
								fieldLabel:'至'
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
							name:'PROD_NAME',
							xtype:'textfield',
							anchor:'90%',
							fieldLabel:'产品名称'
			 	}
				]
			}
		],
		
			buttonAlign:'center',
			buttons:[
			{
				text:'查询',
				handler:function(){
					
					var paramsObj = productSearchPanel.getForm().getValues(false);
//					var params = Ext.encode(paramsObj);	
					productInfoStore.removeAll();
					productInfoStore.load({
						start:0,
						limt:10,
						'condition':Ext.util.JSON.encode(paramsObj)
					});
				}
			}
			]
		}
		
	 ]
	}
	);
	
   var productInfoMidDepData ={//gridPanel中的数据加载
		num:6,
		rows:[
		{
		sellOrg:'华安支行',sellAmount:'200,000,000.00',productCode:'010103',productStatus:'已发布',productName:'影视制做',productCategoryOne:'资产业务',productCategoryTwo:'文化创意产业贷款',productCategoryThree:'',productProvider:'产品经理01',
		 productStartDate:'2011-02-03',productEndDate:'2012-02-03',salable:'是',productCategoryTwo:'文化创意产业贷款',saleMethod:'直销',belongOrg:'中小企业事业部'
		},
		{sellOrg:'华安支行',sellAmount:'400,000,000.00',productCode:'0101014',productStatus:'已发布',productName:'设计创意',productCategoryOne:'资产业务',productCategoryTwo:'文化创意产业贷款',productCategoryThree:'',productProvider:'产品经理01',
		 productStartDate:'2011-02-03',productEndDate:'2012-02-03',salable:'是',saleMethod:'直销',belongOrg:'中小企业事业部'
		},
		{sellOrg:'华安支行',sellAmount:'350,000,000.00',productCode:'0101015',productStatus:'已发布',productName:'出版发行 ',productCategoryOne:'资产业务',productCategoryTwo:'文化创意产业贷款',productCategoryThree:'',productProvider:'产品经理01',
		 productStartDate:'2011-02-03',productEndDate:'2012-02-03',salable:'是',saleMethod:'直销',belongOrg:'中小企业事业部'
		}	
		]
	};
	var productInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	 new Ext.grid.RowNumberer(),
	{header:'统计日期',dataIndex:'CACULATE_DATE',id:'CACULATE_DATE'},
	{header:'机构号',dataIndex:'ORG_CODE',id:'ORG_CODE',width:150},
	{header:'机构名称',dataIndex:'ORG_NAME',id:'ORG_NAME'},
	{header:'产品编号',dataIndex:'PRODUCT_ID',id:'PRODUCT_ID',width:150},
	{header:'产品名称',dataIndex:'PROD_NAME',id:'PROD_NAME'},
	{header:'一级分类',dataIndex:'PRO_CAT_ONE_NAME',id:'PRO_CAT_ONE_NAME'},
	{header:'二级分类',dataIndex:'PRO_CAT_TWO_NAME',id:'PRO_CAT_TWO_NAME'},
	{header:'三级分类',dataIndex:'PRO_CAT_THREE_NAME',id:'PRO_CAT_THREE_NAME'},
	{header:'四级分类',dataIndex:'PRO_CAT_FOUR_NAME',id:'PRO_CAT_FOUR_NAME'},
	{header:'状态',	  dataIndex:'PROD_STATE',id:'PROD_STATE'},
	{header:'时点余额',dataIndex:'TIME_VALUE',id:'TIME_VALUE',align:'right'},
	{header:'年均余额',dataIndex:'YEAR_AVG_VALUE',width:100,align:'right'},
	{header:'平均利率',dataIndex:'AVG_INTEREST',width:100,align:'right'},
	{header:'销售金额',dataIndex:'TRADE_VALUE',width:100,align:'right'},
	{header:'销售金额',dataIndex:'PROD_INCOME',width:100,align:'right'}
	]);
	
	var productInfoRecord = new Ext.data.Record.create([
	{name:'CACULATE_DATE'},
	{name:'ORG_CODE'},
	{name:'ORG_NAME'},
	{name:'PRODUCT_ID'},
	{name:'PROD_NAME'},
	{name:'PRO_CAT_ONE'},
	{name:'PRO_CAT_TWO'},
	{name:'PRO_CAT_THREE'},	
	{name:'PRO_CAT_FOUR'},
	{name:'PROD_STATE'},
	{name:'TIME_VALUE'},
	{name:'YEAR_AVG_VALUE'},
	{name:'AVG_INTEREST'},
	{name:'TRADE_VALUE'},
	{name:'PROD_INCOME'}
	]);
	var productInfoReader = new Ext.data.JsonReader({//读取json数据的panel
	root:'tradeTotalMap.data'
	},productInfoRecord);
	
	var productInfoStore = new Ext.data.Store(
	{
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/product-trade-total',
			method:'GET'
		}),
		reader:productInfoReader
	}
	);
	productInfoStore.load({
		params:{
			start:0,
			limit:10			
		}
	});
	
	var productInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		id:'产品列表',
		frame:true,
		id:'productInfoListGrid',
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true
		}),
		store:productInfoStore,
      	tbar:[ 
       	{
      		text:'查看交易明细',
      		iconCls:'page_edit_1Icon',
      		handler:function()
      		{      			
      			var record = productInfoGrid.getSelectionModel().getSelected();
      			if(record == null || record == "" || record=="undefined"){
      				Ext.MessageBox.alert('提示','请选择一条记录!');
      				return;
      			}      			
      			var productId = record.get("PRODUCT_ID");
      			var orgCode = record.get("ORG_CODE");
      			var caculate_date = record.get("CACULATE_DATE");
      			tradeDetailStore.load({
      				start:0,
      				limit:10,
      				'condition':'{"PRODUC_ID":"'+productId+'","ORG_CODE":"'+orgCode+'","CACULATE_DATE":"'+caculate_date+'"}'
      			});
      			tradeListWind.show();
      		}
      	
      	}
    	
      	],
		cm :productInfoColumns,
		bbar:
			{	
				xtype:'paging',
				pageSize : 10,
				store : productInfoStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', {xtype:'textfield',value:'10'} ]
			}	
	});
	
	
	
	var view = new Ext.Viewport({//页面展示
		layout:'fit',
		items:[
			{
					layout:'border',
					items:
					[productSearchPanel,
					{
						region:'center',
						layout:'fit',
						items:[productInfoGrid]
					}
					]				
			}
		
		]

	});	

});