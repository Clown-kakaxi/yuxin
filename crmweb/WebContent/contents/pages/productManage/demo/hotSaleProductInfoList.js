/*
 * 热销产品列表
 * */
var _prodId='';//产品ID
Ext.onReady(function(){
    Ext.QuickTips.init(); 
    var typeStore = new Ext.data.ArrayStore({
        fields:['key','value'],
        data:[['1','费率/利率类'],['2','期限类'],['3','金额类'],['4','其他']]
    });
    var prodStatStore = new Ext.data.Store({
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=PROD_STATE'
        }),
        reader : new Ext.data.JsonReader({
            root : 'JSON'
        }, [ 'key', 'value' ])
    }); 
    //按应用领域分类
    var prodAppTypeStore = new Ext.data.Store({
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=PROD_APP_TYPE'
        }),
        reader : new Ext.data.JsonReader({
            root : 'JSON'
        }, [ 'key', 'value' ])
    }); 
    //按条线维度分类
    var prodTxTypeStore = new Ext.data.Store({
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=PROD_TX_TYPE'
        }),
        reader : new Ext.data.JsonReader({
            root : 'JSON'
        }, [ 'key', 'value' ])
    }); 
    
    //是否标识
    var isFlagStore = new Ext.data.Store({
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=IF_FLAG'
        }),
        reader : new Ext.data.JsonReader({
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
    
	//产品放大镜
	var prodEditCode = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '<span style="color:red">*</span>产品选择',
		name : 'productName',
		labelStyle: 'text-align:right;',
		hiddenName : 'aimProd',
		allowBlank : false,
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		
	}
		});  
    
    var productEditPanel = new Ext.FormPanel({ //产品信息formpanel
        frame:true,
        region:'north',
        bodyStyle:'padding:5px 5px 0',
        width: '100%',
        labelWidth:120,
      	height:380,
      	autoScroll : true,
    	split:true,
        items: [
				{
				items :[
				  {  
				      layout:'column',
				       items:[
				          {
				          columnWidth:.5,
				          layout: 'form',
				          items: [
				           {
				               xtype:'textfield',
				               labelStyle: 'text-align:right;',
				               fieldLabel: '产品名称',
				               name: 'prodName',
				               disabled:true,
				               allowBlank : false,
				               anchor:'100%'
				           },
				           {
				               xtype:'datefield',
				               fieldLabel: '产品发布日期',
				               labelStyle: 'text-align:right;',
				               name: 'prodStartDate',
				               editable:false,
				               disabled:true,
				               id: 'productStartDate1',
				               format:'Y-m-d',
				               anchor:'100%'
				           },{
				               xtype:'datefield',
				               fieldLabel: '产品有效截止日期',
				               labelStyle: 'text-align:right;',
				               name: 'prodEndDate',
				               editable:false,
				               disabled:true,
				               id: 'productEndDate1',
				               format:'Y-m-d',
				               anchor:'100%'
				           },{
				                  name:'prodAppType',
				                  hiddenName:'prodAppType',
				                  xtype:'combo',
				                  anchor:'100%',
				                  disabled:true,
				                  fieldLabel:'按应用领域分类',
				                  labelStyle: 'text-align:right;',
				                  triggerAction:'all',
				                  mode:'local',
				                  editable:false,
				                  store: prodAppTypeStore,
				                  resizable:true,
				                  forceSelection : true,
				                  valueField:'key',
				                  displayField:'value'
				              },{
					               xtype:'textfield',
					               fieldLabel: '利率(%)',
					               //readOnly:true,
					               disabled:true,
					               labelStyle: 'text-align:right;',
					               name: 'rate',
					               anchor:'100%'
					           },{
				               xtype:'textarea',
				               labelStyle: 'text-align:right;',
				            fieldLabel: '产品描述',
				            name: 'prodDesc',
				            maxLength : 150,
				            disabled:true,
				            anchor:'100%'
				           },{
				               xtype:'textarea',
				               labelStyle: 'text-align:right;',
				               maxLength : 150,
				                fieldLabel: '产品特点',
				                name: 'prodCharact',
				                disabled:true,
				                anchor:'100%'
				           },{ 
				               xtype:'textarea',
				               fieldLabel: '营销渠道描述',
				               maxLength : 150,
				                disabled:true,
				                labelStyle: 'text-align:right;',
				               name: 'channelDisc',
				               anchor:'100%'
				             },
				            /* { 
					               xtype:'textarea',
					               fieldLabel: '按条线维度分类',
					               maxLength : 150,
					                labelStyle: 'text-align:right;',
					               name: 'prodTxType',
					               anchor:'100%'
					             }*/{ 
						               xtype:'textarea',
						               fieldLabel: '按产品经理分类',
						               maxLength : 150,
						                disabled:true,
						                labelStyle: 'text-align:right;',
						               name: 'prodManagerType',
						               anchor:'100%'
						             }             
				           ]
				       },{
				           columnWidth:.5,
				           layout: 'form',
				           items: [
				              {
				                  name:'prodState',
				                  hiddenName:'prodState',
				                  xtype:'combo',
				                  anchor:'100%',
				                  fieldLabel:'产品状态',
					                disabled:true,
				                  labelStyle: 'text-align:right;',
				                  triggerAction:'all',
				                  mode:'local',
				                  editable:false,
				                  store: prodStatStore,
				                  resizable:true,
				                  forceSelection : true,
				                  valueField:'key',
				                  displayField:'value'
				              }, {
				                  name:'isHotSale',
				                  hiddenName:'isHotSale',
				                  xtype:'combo',
					                disabled:true,
				                  anchor:'100%',
				                  fieldLabel:'是否热销',
				                  labelStyle: 'text-align:right;',
				                  triggerAction:'all',
				                  mode:'local',
				                  editable:false,
				                  store: isFlagStore,
				                  resizable:true,
				                  forceSelection : true,
				                  valueField:'key',
				                  displayField:'value'
				              }
				              ,{
					               xtype:'textfield',
					               labelStyle: 'text-align:right;',
					               fieldLabel: '期限',
					               //readOnly:true,
					                disabled:true,
					               name: 'limitTime',
					               anchor:'100%'
					           },{
					                  name:'prodTxType',
					                  hiddenName:'prodTxType',
					                  xtype:'combo',
					                  anchor:'100%',
					                  fieldLabel:'按条线维度分类',
					                  labelStyle: 'text-align:right;',
					                  triggerAction:'all',
					                  mode:'local',
					                  editable:false,
					                  store: prodTxTypeStore,
					                  resizable:true,
						                disabled:true,
					                  forceSelection : true,
					                  valueField:'key',
					                  displayField:'value'
					              },
				           {
				               xtype:'textfield',
				               fieldLabel: '费率(%)',
				               //readOnly:true,
				                disabled:true,
				               labelStyle: 'text-align:right;',
				               name: 'costRate',
				               anchor:'100%'
				           },{ 
				               xtype:'textarea',
				               fieldLabel: '客户准入规则',
				                labelStyle: 'text-align:right;',
				               hidden:true,
				               name: 'custTarRule',
				               anchor:'100%'
				           },{ 
				               xtype:'textarea',
				               fieldLabel: '目标客户描述',
				               maxLength : 150,
				                disabled:true,
				                labelStyle: 'text-align:right;',
				               name: 'objCustDisc',
				               anchor:'100%'
				           },{
				               xtype:'textarea',
				               labelStyle: 'text-align:right;',
				               maxLength : 150,
				                disabled:true,
				            fieldLabel: '担保要求描述',
				            name: 'assureDisc',
				            anchor:'100%'
				           },{
				               xtype:'textarea',
				               labelStyle: 'text-align:right;',
				               maxLength : 150,
				                disabled:true,
				                fieldLabel: '风险提示描述',
				                name: 'dangerDisc',
				                anchor:'100%'
				           }/*{
				               xtype:'textarea',
				               labelStyle: 'text-align:right;',
				               maxLength : 150,
				                fieldLabel: '按应用领域分类',
				                name: 'prodAppType',
				                anchor:'100%'
				           }  */                         
				          ]
				       }
				      ]}
				      ]
				      },{
				  items :[
				          {  
				              layout:'column',
				               items:[
				                  {
				                  columnWidth:.5,
				                  layout: 'form',
				                  items: [
				                   {
				                       xtype:'textfield',
				                       fieldLabel: '隐藏Id',
				                       labelStyle: 'text-align:right;',
				                       hidden:true,
				                       name: 'productId',
				                       id:'productIdId',
				                       anchor:'100%'
				                   },{
				                       xtype:'textfield',
				                       fieldLabel: '隐藏catlCode',
				                       labelStyle: 'text-align:right;',
				                       hidden:true,
				                       name: 'catlCode',
				                       anchor:'100%'
				                   },{
				                       xtype:'textfield',
				                       fieldLabel: '创建人',
				                       labelStyle: 'text-align:right;',
				                       hidden:true,
						                disabled:true,
				                       name: 'prodCreator',
				                       anchor:'100%'
				                   },{
				                       xtype:'textfield',
				                       fieldLabel: '创建人',
				                       labelStyle: 'text-align:right;',
				                       hidden:true,
				                       name: 'prodCode',
				                       anchor:'100%'
				                   }                                   
				                   ]
				                  }
				      ]}
				      ]
				      }         
				]
    });	
    var productEditPanel1 = new Ext.FormPanel({ //产品信息formpanel
        frame:true,
        region:'north',
        bodyStyle:'padding:5px 5px 0',
        width: '100%',
        labelWidth:120,
      	height:380,
      	autoScroll : true,
    	split:true,
        items: [
				{
				items :[
				  {  
				      layout:'column',
				       items:[
				          {
				          columnWidth:.5,
				          layout: 'form',
				          items: [prodEditCode,
				           {
				               xtype:'datefield',
				               fieldLabel: '<span style="color:red">*</span>产品发布日期从',
				               labelStyle: 'text-align:right;',
				               name: 'prodStartDate',
				               editable:false,
				               allowBlank : false,
				               format:'Y-m-d',
				               anchor:'90%'
				           }           
				           ]
				       },{
				           columnWidth:.5,
				           layout: 'form',
				           items: [
				             {
					               xtype:'textfield',
					               labelStyle: 'text-align:right;',
					               fieldLabel: '<span style="color:red">*</span>产品热销排名',
					               allowBlank : false,
					               name: 'limitTime',
					               anchor:'90%'
					           } ,
					           {
					               xtype:'datefield',
					               fieldLabel: '<span style="color:red">*</span>产品发布日期到',
					               labelStyle: 'text-align:right;',
					               name: 'prodEndDate',
					               editable:false,
					               allowBlank : false,
					               format:'Y-m-d',
					               anchor:'90%'
					           }                        
				          ]},{
					          columnWidth:1,
					          layout: 'form',
					          items: [{ 
					               xtype:'textarea',
					               fieldLabel: '营销渠道描述',
					               maxLength : 150,
					                labelStyle: 'text-align:right;',
					               name: 'channelDisc',
					               anchor:'90%'
					             }            
					           ]
					       }
				      ]}
				      ]}       
				]
    });	

    
  //产品信息修改的弹出window				
	var productEditWind = new Ext.Window(
				{
						closeAction:'hide',
						maximized:true,
						closable:true,
						constrain:true,
						modal:true,
						maximizable:true,
						height:500,
						width:800,
						items : [
								  	productEditPanel
								],
						buttonAlign:'center',
						buttons:[
								{
								text:'保存',
								handler:function(){
								  var test1 = Ext.getCmp("productStartDate1").getValue();
								  var test2 = Ext.getCmp("productEndDate1").getValue();
								  if(!productEditPanel.getForm().isValid()){
								      Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
								  return false;
								  }
								  if(((test2-test1)/1000/60/60/24)<1){
								      Ext.Msg.alert("系统提示","请填写正确日期！");
								      return false;
								  }
								  if(!productEditPanel.getForm().isValid()){
								      Ext.Msg.alert("提醒","输入的信息过长！");
								      return false;
								  }
								  Ext.Ajax.request({
								      url:basepath+'/product.json?a=2',
								      method:'POST',
								      form:productEditPanel.getForm().id,
								      success :checkResult
								  });     
								  function checkResult(response) {
								      Ext.Msg.alert('提示', '操作成功');
								      productEditWind.hide();     
								      productInfoStore.removeAll();
								      productInfoStore.load({
								          params:{
								              start:0,
								              limit:parseInt(spagesize_combo.getValue())
								          }
								      });
								  }
								}
								},
								{
								text:'返回',
								handler:function()
								{
								  productEditWind.hide();
								}}]
								});	
					
	var productEditWind1 = new Ext.Window(
				{
						closeAction:'hide',
						maximized:false,
						closable:true,
						constrain:true,
						modal:true,
						maximizable:true,
						height:300,
						width:800,
						items : [
								  	productEditPanel1
								],
						buttonAlign:'center',
						buttons:[
								{
								text:'保存',
								handler:function(){
								if (!productEditPanel1.getForm().isValid()) {
				                    Ext.MessageBox.alert('提示','输入有误,请检查输入项');
				                    return false;
				                };
								Ext.Msg.alert('系统提示','保存成功');	
								 productEditWind1.hide();
								}
								},
								{
								text:'返回',
								handler:function()
								{
								  productEditWind1.hide();
								}}]
								});	
    

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
            Ext.getCmp('productLeftTreePanel').appendChild(children);
        }
    });

    /**
     * 产品树面板，在模块左侧显示
     */
    var prodCatlSign = '';
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
            if(node.attributes.id!=''){
            	prodCatlSign = node.attributes.CATL_CODE;
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
    
        title:'热销产品查询',
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
                        {
                            name:'PROD_START_DATE_FROM',
                            anchor:'100%',
                            id :'PROD_START_DATE_FROM',
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
    {header :'产品编号',dataIndex:'productId',id:"productId",width:150,sortable : true},
    {header:'产品名称',dataIndex:'prodName',id:'prodName',sortable : true},
    {header:'产品类别编码',dataIndex:'catlCode',sortable : true},   
//    {header:'产品分类名称',dataIndex:'catlName',id:'catlName',sortable : true},   
    {header:'产品发布日期',dataIndex:'prodStartDate',id:'prodStartDate',sortable : true},
    {header:'产品截止日期',dataIndex:'prodEndDate',id:'prodEndDate',sortable : true},
    {header:'产品所属部门',dataIndex:'prodDept',hidden:true,hideable:false,sortable : true},
    {header:'利率',dataIndex:'rate',id:'rate',renderer: ratePercent(true), hidden:false,align : 'right',sortable : true},
    {header:'费率',dataIndex:'costRate',renderer: ratePercent(true),align : 'right', hidden:false,id:'costRate',sortable : true},
    {header:'期限',dataIndex:'limitTime',id:'limitTime',align : 'right', hidden:false,sortable : true},
    {header:'产品特征',dataIndex:'prodCharact',sortable : true},
    {header:'目标客户描述',dataIndex:'objCustDisc',sortable : true},
    {header:'产品状态',dataIndex:'PROD_STATE_ORA',hidden:true,hideable:false,sortable : true},
    {header:'产品状态',dataIndex:'prodState',hidden:true,hideable:false,sortable : true},
    {header:'是否热销 ',dataIndex:'isHotSale', hidden:true,hideable:false,sortable : true},
    {header:'是否热销 ',dataIndex:'IS_HOT_SALE_ORA',hidden:true,hideable:false,sortable : true},
    {header:'按条线维度分类 ',dataIndex:'prodTxType',id:'assureDisc',hidden:true,hideable:false,sortable : true},  
    {header:'按产品经理分类 ',dataIndex:'prodManagerType',hidden:true,hideable:false,sortable : true},  
    {header:'按应用领域分类 ',dataIndex:'prodAppType',hidden:true,hideable:false,sortable : true},
    {header:'创建人 ',dataIndex:'prodCreator',id:'prodCreator',sortable : true}
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
            	{name:'prodStartDate',mapping:'PROD_START_DATE'},
            	{name:'prodEndDate',mapping:'PROD_END_DATE'},
            	{name:'prodDept',mapping:'PROD_DEPT'},
            	{name:'prodDesc',mapping:'PROD_DESC'},
            	{name:'rate',mapping:'RATE',type:'float'},
            	{name:'costRate',mapping:'COST_RATE',type:'float'},
            	{name:'limitTime',mapping:'LIMIT_TIME'},
            	{name:'prodCharact',mapping:'PROD_CHARACT'},
            	{name:'objCustDisc',mapping:'OBJ_CUST_DISC'},
            	{name:'prodCode',mapping:'PROD_CODE'},
            	{name:'dangerDisc',mapping:'DANGER_DISC'},
            	{name:'channelDisc',mapping:'CHANNEL_DISC'},
            	{name:'assureDisc',mapping:'ASSURE_DISC'},
                {name:'prodAppType',mapping:'PROD_APP_TYPE'},//按应用领域分类
                {name:'isHotSale',mapping:'IS_HOT_SALE'},//是否热销
                {name:'IS_HOT_SALE_ORA'},//是否热销
                {name:'prodTxType',mapping:'PROD_TX_TYPE'},//按条线维度分类
                {name:'prodManagerType',mapping:'PROD_MANAGER_TYPE'}//按产品经理分类
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
    	addRoleWindow.on('hide', function() {
    		document.getElementById('view').innerHTML = "";
    	});
    var productId = null;
    var productInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
        id:'热销产品列表',
        frame:true,
        id:'productInfoGrid',
        store:productInfoStore,
        loadMask:true,
        cm :productInfoColumns,
        bbar:sbbar,
        tbar:[{
      		text:'详情',
      		iconCls:'editIconCss',
      		handler:function()
      		{      			
      			var record = productInfoGrid.getSelectionModel().getSelected();
      			if(record==null || record=="undefined"){
      				Ext.MessageBox.alert('提示','请选择一条记录');
      				return;
      			}      		
      			productEditWind.setTitle('产品详细信息');
      			productEditWind.buttons[0].setVisible(false);
      			productEditWind.show();
     			productEditPanel.getForm().loadRecord(record);
      		}
      	
      	},'-',{
      		text:'手动设置热销产品',
      		iconCls:'addIconCss',
      		handler:function()
      		{      			
      			var record = productInfoGrid.getSelectionModel().getSelected();
      			if(record==null || record=="undefined"){
      				Ext.MessageBox.alert('提示','请选择一条记录');
      				return;
      			}      		
      			productEditWind1.setTitle('热销产品设置');
      			productEditWind1.show();
     			productEditPanel1.getForm().loadRecord(record);
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