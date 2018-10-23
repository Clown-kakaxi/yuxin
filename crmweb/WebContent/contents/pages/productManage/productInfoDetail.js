//Ext.onReady(function(){
/*产品详细信息
 * 
 * 姚亮
 * 2011-06-09
 * */
 var cuspanel = new Ext.FormPanel({ //产品信息formpanel
	        frame:true,
	        region:'north',
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	      	 height:250,
			split:true,
	        items: [
	        	{
//	           	autoHeight:true,
	            items :[
	            	{  
	            		layout:'column',
	                     items:[{
	                         columnWidth:.33,
                         layout: 'form',
                         items: [
                         	{
	                             xtype:'textfield',
	                             fieldLabel: '产品名称',
	                             value :'单位普通活期存款',
	                             name: 'productName',
	                             anchor:'70%'
	                         }, {
	                             xtype:'textfield',
	                             fieldLabel: '产品ID',
	                             name: 'productId',
	                             value :'10201055',
	                             anchor:'70%'
	                         },{
	                         	xtype:'textfield',
	                         	fieldLabel:'产品类别',
	                         	value:'单位客户活期存款',
	                         	name:'productCategory',
	                         	anchor:'70%'
	                         
	                         },
							{
	                             xtype:'textfield',
	                             fieldLabel: '产品录入人',
	                             name: 'productCreatePerson',
	                             value :'总行产品经理',
	                             anchor:'70%'
	                         }	 	                         
							
	                         ]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [
								{
	                             xtype:'textfield',
	                             fieldLabel: '利率',
	                             name: 'isSalable',
	                             value :'5%',
	                             anchor:'70%'
	                         },                         
								{
	                             xtype:'textfield',
	                             fieldLabel: '费率',
	                             name: 'productStatus',
	                             value :'5%',
	                             anchor:'70%'
	                         } ,	                         
	                         	{
	                             xtype:'textfield',
	                             fieldLabel: '产品生效日期',
	                             name: 'productStartDate',
	                             value :'2011-03-03',
	                             anchor:'70%'
	                         },	                         	
	                         {
	                             xtype:'textfield',
	                              fieldLabel: '产品有效截止日期',
	                             name: 'productEndDate',
	                             value :'2013-03-03',
	                             anchor:'70%'
	                         }
 
							]
	                     },
	                     {
	                         columnWidth:.34,
	                         layout: 'form',
	                         items: [
								{
	                             xtype:'textfield',
	                             fieldLabel: '期限',
	                             name: 'productStatus',
	                             value :'六个月',
	                             anchor:'70%'
	                         } ,	 
								{
	                             xtype:'textfield',
	                             fieldLabel: '额度',
	                             name: 'productStatus',
	                             style:'text-align:right',
	                             value :'500,000,000',
	                             anchor:'70%'
	                         } ,	                         
 							{
	                             xtype:'textfield',
	                             fieldLabel: '产品创建时间',
	                             name: 'productCreateDate',
	                             value :'2011-03-02',
	                             anchor:'70%'
	                         },

							 {
	                             xtype:'textfield',
	                             fieldLabel: '产品提供者',
	                             name: 'productProvider',
	                             value :'总行业务主管02',
	                             anchor:'70%'
	                         }	                         
	                         ]
	                     }
	            ]}
	            ]
	            
	            },
	            {

	                  xtype:'textarea',
	                  fieldLabel: '产品描述',
	                  name: 'productMemo',
	                  value :'产品描述',
	                  anchor:'70%'
        
	            },	            
	            {
	                  
	                  xtype:'textarea',
	                  fieldLabel: '客户准入规则',
	                  name: 'productCustomRule',
	                  value :'',
	                  anchor:'70%'
	                             
	            }	            
	            ]
	    });
 
	    
var sm = new Ext.grid.CheckboxSelectionModel();//列表前面的复选框

var producPropertyColumns = new Ext.grid.ColumnModel(//产品特征项table的列数
		{
			columns:[
//			sm,
			{ header:'产品名称',dataIndex:'productName',id:'productName',sortable:true,width:250},
			{ header:'特征项类别',dataIndex:'productPropertyType',sortable:true},
			{ header:'特征项名称',dataIndex:'productPropertyName',sortable:true,width:200},
			{ header:'特征值',dataIndex:'productPropertyValue',sortable:true,width:200
				,editor:new Ext.form.Field(),align:'right'
			}
			]
		}
	);
	
	var producPropertyRecord= Ext.data.Record.create(//产品table的 record
		[
		{name:'productName'},
		{name:'productPropertyType'},
		{name:'productPropertyName'},
		{name:'productPropertyValue'}
		]
	);
	var producPropertyData = {//特性项列表数据
		num:6,
		rows:[
		{"productName":"单位普通活期存款","productPropertyType":"费率","productPropertyName":"利率","productPropertyValue":"5%"},
		{"productName":"单位普通活期存款","productPropertyType":"费率","productPropertyName":"费率","productPropertyValue":"5%"},
		{"productName":"单位普通活期存款","productPropertyType":"期限","productPropertyName":"活期","productPropertyValue":"六个月"},
		{"productName":"单位普通活期存款","productPropertyType":"金额","productPropertyName":"发生额","productPropertyValue":"500,000,000"}

		]
		
	}; 
	
	var producPropertyReader = new Ext.data.JsonReader(//读取特性项列表数据
		{
			totalProperty:'num',
			root:'rows'
		},producPropertyRecord
	);
	
	var producPropertyStore = new Ext.data.Store({//产品特性项列表panel中的store
		reader:producPropertyReader
	});
	
	producPropertyStore.loadData(producPropertyData);
	
	
	var producPropertyGrid = new Ext.grid.EditorGridPanel({//产品特性项列表panel
		
		title:'产品特征项列表',
		store:producPropertyStore, 
		frame:true,
		clicksToEdit :1,
		height:200,
		cm:producPropertyColumns,
		region:'center',
		sm:sm,
      	stripeRows: true,
//      	tbar:[ 
//      	{
//      		text:'新增',
//      		iconCls:'page_addIcon',
//      		handler:function()
//      		{
//      			productBusinessAddWindow.show();
//      		}
//      	},'-',
//      	{
//      		text:'修改',
//      		iconCls:'page_edit_1Icon',
//      		handler:function()
//      		{
//
//      		}
//      	
//      	},'-',
//       	{
//      		text:'删除',
//      		iconCls:'page_edit_1Icon',
//      		handler:function()
//      		{
//
//      		}
//      	
//      	}
//      	],
       		autoExpandColumn:'productName',
      		bbar:
			{	
				xtype:'paging',
				pageSize : 10,
				store : producPropertyStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', {xtype:'textfield',value:'10'} ]
			}	
	});
		
	
	var productDetailWind = new Ext.Window(//产品详细信息的弹出window
				{
						layout : 'border',
						closeAction:'hide',
						maximized:true,
						closable:true,
						title:'产品信息',
						constrain:true,
						modal:true,
						maximizable:true,
						height:500,
						width:800,
						items : [
								  	cuspanel,
								  	producPropertyGrid
								]
							
					});
					
//  var detailView = new Ext.Viewport({
//  	layout:'border',
//  	items:
//  	[
//  	cuspanel,
//  	producPropertyGrid
//  	]
//  });                                            	   
//});