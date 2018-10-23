/*
 
 * */
var show=function(){
var productTreeLoader1 = new Com.yucheng.bcrm.ArrayTreeLoader({
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
			productTreeLoader1.nodeArray = nodeArra;
			var children = productTreeLoader1.loadAll();
			Ext.getCmp('productLeftTreePanel23').appendChild(children);
		}
	});

	/**
	 * 产品树面板，在模块左侧显示
	 */
	var productLeftTreeForShow1 = new Com.yucheng.bcrm.TreePanel({
		id:'productLeftTreePanel23',
		height : document.body.clientHeight,
		width : 200,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
		resloader:productTreeLoader1,//加载产品树
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
                     columnWidth:.5,
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
                         columnWidth:.5,
                         layout:'form',
                         items:[{
                                xtype:'textfield',
                                name:'PROD_NAME',
                                fieldLabel:'产品名称',
                                anchor:'100%'
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
    var prosm = new Ext.grid.CheckboxSelectionModel();
    var productInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
    new Ext.grid.RowNumberer(),prosm,
    {header :'产品编号',dataIndex:'productId',id:"productId",width:150,sortable : true},
    {header:'产品名称',dataIndex:'prodName',id:'prodName',width:160,sortable : true},
    {header:'风险类别',dataIndex:'prodName1',id:'prodName1',width:150,sortable : true},
    {header:'流动性',dataIndex:'prodName2',id:'prodName2',width:150,sortable : true}
    ]);
    
    var productInfoRecord = new Ext.data.Record.create([	
            	{name:'productId',mapping:'PRODUCT_ID'},
            	{name:'prodName',mapping:'PROD_NAME'}
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
 
    	var tbar = new Ext.Toolbar({				
			items : [{ 
				text : '选定',
				handler : function() {
					var index =  productInfoGrid.getSelectionModel().getSelections().length;
	                 if (index<1) {
	                 	Ext.Msg.alert('提示',"请选择一条记录");
	                     return false;
	                 }
	                for(var i=0;i<index;i++){
	                	var racod=productInfoGrid.getSelectionModel().getSelections()[i];
	                 var name=racod.data.prodName;	                
	                	 var p=new Ext.data.Record({
								a0:name,
				                 a1:'',
				                 a2:'B类',
				                 a3:''
							});
	                	 productGrid.stopEditing();
						 store1.insert(0,p);
						 productGrid.startEditing(0,0);
	                };
					productManageWindow.hide();
				}
			},'-',{
				text : '取消',
				handler : function() {
					
					productManageWindow.hide();
				}
			}]
		});
    var productId = null;
    var productInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
        id:'产品列表',
        frame:true,
        id:'productInfoGrid',
        store:productInfoStore,
        loadMask:true,
        cm :productInfoColumns,
        sm:prosm,
        bbar:sbbar,
        tbar:tbar,
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }

    }); 
    var productManageWindow = new Ext.Window({//页面展示
    	title : '添加产品',
		height:400,
		width:860,
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
        items : [{
        layout:'border',
        items:[
           productLeftTreeForShow1,
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
    productManageWindow.show();
};
