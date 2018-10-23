/**
 * 理财产品比较器
 * @author zkl
 * 
 */
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	
	var  ccyType=  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '人民币理财产品' ],[ 2, '美元理财产品' ],[3,'台币理财产品'],[4,'澳元理财产品']
		]
	});
	
	var  bankType=  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '中国银行' ],[ 2, '交通银行' ],[3,'工商银行'],[4,'建设银行']
		]
	});

	//查询panel
	var finaComparePanel = new Ext.form.FormPanel({
		title:'理财产品搜索器',
		region:'north',
		height:140,
		labelWidth:150,//label的宽度
		labelAlign:'right',
		frame:true,
		autoScroll : true,
		region:'north',
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'发行银行',anchor:'50%',
					store : bankType,displayField : 'value',valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: .5,
				layout:'form',
				items:[{xtype:'combo',name:'PRODUCT_ID',fieldLabel:'理财产品',anchor:'50%',
					store : ccyType,displayField : 'value',valueField : 'key',mode : 'local',emptyText:'请选择 '}]
			},{
				columnWidth: 1,
				layout:'form',
				items:[{
                    xtype: "checkboxgroup",
                    fieldLabel: "比较的理财产品",
                    columns: 5,
                    anchor:'98%',
                    items: [
                        { boxLabel: "理财产品A", name: "swim" },
                        { boxLabel: "理财产品B", name: "walk" },
                        { boxLabel: "理财产品C", name: "read" },
                        { boxLabel: "理财产品D", name: "game" },
                        { boxLabel: "理财产品E", name: "game" }                        
                    ]
				}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{text:'开始比较'},{text:'从比较器中删除',
			handler:function(){finaComparePanel.getForm().reset();}
		}]
	});
	
	
	
	//增长率规划 数据源 DEMO
	var eduFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}, {name : 'a3'}];
	var eduData = [
	            ['发行银行','中国银行','中国农业银行','中国工商银行','光大银行'],
	            ['产品类型','理财','基金','保险','储蓄'],
	            ['投资币种','人民币','人民币','人民币','人民币'],
	            ['投资期限(月)','6','6','6','6'],
	            ['本金保障程度(%)','80%','79%','85%','60%'],
	            ['预期收益上限/年化','8','7','7','8'],
	            ['风险收益类型','保本型','保本型','保本型','保本型'],
	            ['收益支付类型','','','',''],
	            ['挂钩标的','是','否','是','是'],
	            ['募集起止时间','2013-7-8','2013-7-8','2013-7-8','2013-7-8'],
	            ['投资起止时间','2013-7-8','2013-7-8','2013-7-8','2013-7-8'],
	            ['产品起购金额','50000','50000','50000','50000'],
	            ['产品等级','A级','A级','B级','C级'],
	            ['发行地区','全国','北京','上海','四川'],
	            ['是否可质押','是','是','是','否']
	            
	];
	//定义自动当前页行号
	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	//gridtable中的列定义
	var finaInfoColumns = new Ext.grid.ColumnModel([num,
   		{header:'产品属性',dataIndex:'a0',id:"productId",sortable : true},
   		{header:'理财产品A',dataIndex:'a1',id:'productName',sortable : true},
   		{header:'理财产品B',dataIndex:'a2',id:'catlName',sortable : true},	
   		{header:'理财产品C',dataIndex:'a3',id:'productStartDate',sortable : true}   		
   	]);
	
	
	
	var finaInfoStore = new Ext.data.ArrayStore({
		fields : eduFields,
		data : eduData
	});
	
	
	//理财产品查询结果grid
	var finaCompareGrid =  new Ext.grid.GridPanel({
		id:'产品列表',
		frame:true,
		id:'finaInfoGrid',
		store:finaInfoStore,
		region:'center',
		loadMask:true,
		cm :finaInfoColumns,
    	viewConfig:{
	       forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
		},
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
	});
	
	
	//页面布局
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		layout:'border',
		items : [finaComparePanel,finaCompareGrid]
	});
});