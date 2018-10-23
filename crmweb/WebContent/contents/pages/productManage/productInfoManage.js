/*
 * 产品信息维护
 * */
var pProductId = '';
var productName = '';
Ext.onReady(function(){
    Ext.QuickTips.init(); 
    var typeStore = new Ext.data.Store({
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=FEATURE_DISC'
        }),
        reader : new Ext.data.JsonReader({
            root : 'JSON'
        }, [ 'key', 'value' ])
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
    var productEditPanel = new Ext.FormPanel({ //产品信息formpanel
        frame:true,
        region:'north',
        bodyStyle:'padding:5px 5px 0',
        width: '100%',
        height:380,
        autoScroll : true,
        split:true,
        labelWidth:120,
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
                                    fieldLabel: '产品类别',
                                    name: 'catlName',
                                    anchor:'100%'
                                },
                                 {
                                     xtype:'textfield',
                                     labelStyle: 'text-align:right;',
                                     fieldLabel: '产品名称',
                                     name: 'productName',
                                     allowBlank : false,
                                     anchor:'100%'
                                 },
                                 {
                                     xtype:'datefield',
                                     fieldLabel: '产品发布日期',
                                     labelStyle: 'text-align:right;',
                                     name: 'productStartDate',
                                     id: 'productStartDate1',
                                     format:'Y-m-d',
                                     anchor:'100%'
                                 },{
                                     xtype:'datefield',
                                     fieldLabel: '产品有效截止日期',
                                     labelStyle: 'text-align:right;',
                                     name: 'productEndDate',
                                     id: 'productEndDate1',
                                     format:'Y-m-d',
                                     anchor:'100%'
                                 },{
                                     xtype:'textarea',
                                     labelStyle: 'text-align:right;',
                                  fieldLabel: '产品描述',
                                  name: 'productDescription',
                                  maxLength : 150,
                                  anchor:'100%'
                                 },{
                                     xtype:'textarea',
                                     labelStyle: 'text-align:right;',
                                     maxLength : 150,
                                      fieldLabel: '产品特点',
                                      name: 'prod_charact',
                                      anchor:'100%'
                                 },{ 
                                     xtype:'textarea',
                                     fieldLabel: '营销渠道描述',
                                     maxLength : 150,
                                      labelStyle: 'text-align:right;',
                                     name: 'channel_disc',
                                     anchor:'100%'
                                   }             
                                 ]
                             },{
                                 columnWidth:.5,
                                 layout: 'form',
                                 items: [
                                    {
                                        name:'productState',
                                        hiddenName:'productState',
                                        xtype:'combo',
                                        anchor:'100%',
                                        fieldLabel:'产品状态',
                                        labelStyle: 'text-align:right;',
                                        triggerAction:'all',
                                        mode:'local',
                                        store: prodStatStore,
                                        resizable:true,
                                        forceSelection : true,
                                        valueField:'key',
                                        displayField:'value'
                                    }             ,
                                 {
                                     xtype:'textfield',
                                     fieldLabel: '费率(%)',
                                     //readOnly:true,
                                     labelStyle: 'text-align:right;',
                                     name: 'cost_rate',
                                     anchor:'100%'
                                 }     ,{
                                     xtype:'textfield',
                                     labelStyle: 'text-align:right;',
                                     fieldLabel: '期限',
                                     //readOnly:true,
                                     name: 'limit_time',
                                     anchor:'100%'
                                 },{
                                     xtype:'textfield',
                                     fieldLabel: '利率(%)',
                                     //readOnly:true,
                                     labelStyle: 'text-align:right;',
                                     name: 'rate',
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
                                      labelStyle: 'text-align:right;',
                                     name: 'obj_cust_disc',
                                     anchor:'100%'
                                 },{
                                     xtype:'textarea',
                                     labelStyle: 'text-align:right;',
                                     maxLength : 150,
                                  fieldLabel: '担保要求描述',
                                  name: 'assure_disc',
                                  anchor:'100%'
                                 },{
                                     xtype:'textarea',
                                     labelStyle: 'text-align:right;',
                                     maxLength : 150,
                                      fieldLabel: '风险提示描述',
                                      name: 'danger_disc',
                                      anchor:'100%'
                                 }                          
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
                                             name: 'productCreator',
                                             anchor:'100%'
                                         }                                   
                                         ]
                                        }
                            ]}
                            ]
                            }         
                    ]
    }); 
        
    var prodTabs = new Ext.TabPanel({
        width:'100%',
        heignt:'100%',
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        resizeTabs:true, // turn on tab resizing
        preferredTabWidth:150,	        
        items:[
        	{ 
				title: '<span style=\'text-align:center;\'>产品信息</span>',
				items:[productEditPanel]
			},
            { 
				title: '<span style=\'text-align:center;\' >产品介绍</span>',
				html:'',
		          listeners : {
					'activate' : function(o) {
						var nodeId = Ext.getCmp('productIdId').getValue();
						
						
	        	  		o.el.dom.innerHTML = '<iframe id="content3" name="content3" style="width:100%;height:'
			  	  			+ 450
			  				+ 'px;" frameborder="no"" src=\"'
			  				+ basepath
			  				+ '/contents/pages/demo/docs/doc1.jsp?nodeId='+nodeId
			  				+ '\" "/> scrolling="auto"> ';
					}
				  }
			}
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
                                 	prodTabs//productEditPanel
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
                            }                           
                        }
                        ]
                            
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
    {header :'产品编号',dataIndex:'productId',id:"productId",width:80,sortable : true},
    {header:'产品名称',dataIndex:'productName',id:'productName',width:200,sortable : true},
    {header:'产品分类名称',dataIndex:'catlName',id:'catlName',sortable : true},   
    {header:'产品发布日期',dataIndex:'productStartDate',id:'productStartDate',sortable : true},
    {header:'产品截止日期',dataIndex:'productEndDate',id:'productEndDate',sortable : true},
    {header:'利率',dataIndex:'rate',id:'rate',renderer: ratePercent(true), hidden:false,align : 'right',sortable : true},
    {header:'费率',dataIndex:'cost_rate',renderer: ratePercent(true),align : 'right', hidden:false,id:'cost_rate',sortable : true},
    {header:'期限',dataIndex:'limit_time',id:'limit_time',align : 'right', hidden:false,sortable : true},
    {header:'产品特征',dataIndex:'prod_charact',id:'prod_charact', hidden:false,sortable : true},
    {header:'是否已设定目标客户',dataIndex:'targetCustNumOra',width:120,sortable : true},
    {header:'目标客户描述',dataIndex:'obj_cust_disc',id:'obj_cust_disc', hidden:false,sortable : true},
    {header:'风险提示描述',dataIndex:'danger_disc',id:'danger_disc', hidden:false,sortable : true},
    {header:'营销渠道描述',dataIndex:'channel_disc',id:'channel_disc', hidden:false,sortable : true},
    {header:'担保要求描述 ',dataIndex:'assure_disc',id:'assure_disc', hidden:false,sortable : true},
    {header:'产品状态',dataIndex:'productState',id:'productState',hidden:false,sortable : true},    
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
    {name:'assure_disc',mapping:'ASSURE_DISC'},
    {name:'targetCustNum',mapping:'TARGET_CUST_NUM'},
	{name:'targetCustNumOra',mapping:'TARGET_CUST_NUM_ORA'}
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
        id:'产品列表',
        frame:true,
        id:'productInfoGrid',
        store:productInfoStore,
        loadMask:true,
        tbar:[ 
        {
            text:'修改',
            iconCls:'editIconCss',
            handler:function()
            {               
                var record = productInfoGrid.getSelectionModel().getSelected();
                if(record==null || record=="undefined"){
                    Ext.MessageBox.alert('提示','请选择一条记录');
                    return;
                }           
                productEditWind.setTitle('产品修改');               
                productEditWind.show();
                prodTabs.activate(0);
                productEditPanel.getForm().loadRecord(record);
            }
        
        },
        {
            text:'产品特征项',
            iconCls:'completeIconCss',
            handler: function(){
        		var rrecord = productInfoGrid.getSelectionModel().getSelected();
        		if (!rrecord) {
        			Ext.MessageBox.alert('系统提示信息', '请选择要操作的记录！');
        			return false;
        		}
        		productId =rrecord.data.productId;

        		producPropertyStore.baseParams={                                
        				'productId':productId                       
        		};
        		producPropertyStore.reload();
                productProWind.show();
        	}
        },
        {
        	text:'产品对照关系',
        	iconCls:'detailIconCss',
        	handler:function(){
        		var _record = productInfoGrid.getSelectionModel().getSelected();
        		if (!_record) {
        			Ext.MessageBox.alert('系统提示信息', '请选择要操作的记录！');
        			return false;
        		}
        		pid =_record.data.productId;
        		productContrastStore.load({
        			params : {
        				'productId':pid
        			}
        		});
        		productContrastWindow.show();
        	}
        },
        {
            text:'目标客户设定',
            iconCls:'maintainIconCss',
            handler: function(){
        	var _record = productInfoGrid.getSelectionModel().getSelected();
    		if (!_record) {
    			Ext.MessageBox.alert('系统提示信息', '请选择一条数据！');
    			return ;
    		}
    		 pProductId=_record.data.productId;
    		 productName = _record.data.productName;
     /*       addRoleWindow.show();
	        setTimeout(function(){
	            Ext.ScriptLoader.loadScript({      
	                scripts: [basepath+'/contents/pages/productManage/productCusAgileQuery.js'],        
	                finalCallback: function() {
	            	  pProductId=_record.data.productId;

	          		fnUpdateQuery();
	            }
	            });
	        },800);
	        */
    		   if(Com.yucheng.crm.query){
				   agileQueryWindow.show();
				   right_panel.currentSolutionsId=pProductId;
				   selectItems(0);
				   return;
			   }
			   Ext.ScriptLoader.loadScript({        
				   scripts: [
//				             basepath+'/contents/pages/customer/customerManager/agileQuerySolutions.js',
				             basepath+'/contents/pages/productManage/productAgileQueryDatasets.js',
				             basepath+'/contents/pages/productManage/productAgileQueryItems.js',
//				             basepath+'/contents/pages/customer/customerManager/agileQueryResults.js',
//				             basepath+'/contents/pages/customer/customerManager/agileQueryGrouping.js',
				             basepath+'/contents/pages/productManage/productAgileQuery.js'
				             ],        
				    finalCallback: function() {
				   		agileQueryWindow.show();
				   	}
				   });
            }
        },
        {
            text:'查找目标客户',
            iconCls:'searchIconCss',
            handler: function(){
        	var _record = productInfoGrid.getSelectionModel().getSelected();
    		if (!_record) {
    			Ext.MessageBox.alert('系统提示信息', '请选择一条数据！');
    			return ;
    		}
    				Ext.Ajax.request({
						url:basepath+'/querytatgetcusquery!queryAgileCondition.json?SS_ID='+_record.data.productId,
                        method: 'GET',
						success : function(response) {
							var conditionData = Ext.util.JSON.decode(response.responseText);
							 conditionArray=conditionData.JSON.data;
							 if(conditionArray.length>0){
									var conditions = new Array();
									Ext.each(conditionArray,function(con){
										var conAtt = {};
										conAtt.ss_col_item = con.SS_COL_ITEM;
										conAtt.ss_col_op = con.SS_COL_OP;
										conAtt.ss_col_value = con.SS_COL_VALUE;
										conditions.push(conAtt);
									});
				                	targetCustStore.on('beforeload', function() {
				         		       this.baseParams = {
				         		    		   conditionAttrs : Ext.encode(conditions),
				         		    		   radio: conditionArray[0].SS_COL_JOIN,
				         		    		   productId:_record.data.productId
				         		      };
				         		  	});
				                	targetCustStore.load({ 
				                			params : {
				                    start : 0,
				                    limit : 20
					                        }
				                   });
			                targetCustDetail.show(); 
    				}else {
    					Ext.Msg.alert('提示','请先设定目标客户!');
    				}	 
						},
						failure : function(response) {
							  var resultArray = Ext.util.JSON.decode(response.status);
							   if(resultArray == 403) {
							      Ext.Msg.alert('提示','查询失败!');
							   } else {
								  Ext.Msg.alert('提示','操作失败!');
							   }
						}});
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
        }
        ],
        cm :productInfoColumns,
        bbar:sbbar,
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }

    });
/*****************************特征项新增*********************************/
	
	var sm2 = new Ext.grid.CheckboxSelectionModel();
	var rownum2 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var fields2 = [{
		name : 'c1'
	}, {
		name : 'c2'
	}];
	
	var Columns2 = new Ext.grid.ColumnModel([//gridtable中的列定义
                                             sm2,
                                            {header :'类别编号',dataIndex:'c1',width:150,sortable : true},
                                            {header:'客户群分类',dataIndex:'c2',width:150,sortable : true}
                                            ]);
	
	var data2= [['1','普通客户群'],['2','商圈客户群'],['3','链式客户群'],['4','族群'],['5','集团']];
			

			 var store2 = new Ext.data.ArrayStore({
				   fields : fields2,
					data : data2
				});
    var grid = new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		layout:'fit',
		store : store2,
		stripeRows : true,
		cm : Columns2,
		sm:sm2,
		buttonAlign : "center",
		buttons:[{
			text : '确定',
			handler : function() {
				var selectLength = grid.getSelectionModel().getSelections().length;
				if (selectLength < 1) {
					Ext.Msg.alert("提示", "请选择至少一条记录!");
				}else{
					groupTypewin.hide();
					var typestr = '';
					for(var i = 0; i<selectLength;i++)
					{
						selectRe = grid.getSelectionModel().getSelections()[i];
						typestr 	+= ','+ selectRe.data.c2;
					};
					addProForm.form.findField('productPropertyDesc').setValue(typestr.substring(1, typestr.length));
					}
				}
			}
		]
	});

    
    var groupTypewin = new Ext.Window({
    	title : '客户群类型',
		plain : true,
		layout : 'fit',
		width : 400,
		height : 300,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'center',
						layout : 'fit',
						items : [ grid ]
					}
			]
		} ]
    	
    });
    // 新增窗口展示的from
    var addProForm = new Ext.form.FormPanel({
        id : "add",
        labelWidth : 100,
        height : 450,
        frame : true,
        labelAlign : 'right',
        region : 'center',
        autoScroll : true,
        buttonAlign : "center",
        items : [ {
            layout : 'column',
            items : [ {
                columnWidth : .5,
                layout : 'form',
                items : [  {
                    name : 'id',
                    xtype : 'textfield',
                    fieldLabel : 'id',
                    hidden : true
                },{
                    name : 'productId',
                    xtype : 'textfield',
                    fieldLabel : '产品id',
                    hidden : true
                },{
                    name : 'productPropertyName',
                    xtype : 'textfield',
                    fieldLabel : '特征项名称',
                    width : '100',
                    anchor : '90%'
                }]
            }, {
                columnWidth : .5,
                layout : 'form',
                items : [{
                    store : typeStore,
                    xtype : 'combo', 
                    resizable : true,
                    name : 'PRODUCT_PROPERTY_TYPE_ORA',
                    hiddenName : 'productPropertyType',
                    fieldLabel : '特征项类别',
                    valueField : 'key',
                    displayField : 'value',
                    mode : 'local',
                    editable : false,
                    typeAhead : true,
                    forceSelection : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    width : '100',
                    anchor : '90%',
                    listeners:{
           			 select :function(){
           				 if(addProForm.form.findField('productPropertyType').getValue()=='4'){
    	    				groupTypewin.show();
    	    			 }
           			 }}
                }]
            }
            ]
        },{
        layout : 'form',
        buttonAlign : 'center',
        items : [ {
            xtype : 'textarea',
            labelStyle : {
            width : '120px'
            },
            width : 260,
            fieldLabel : '特征项描述',
            name : 'productPropertyDesc',
            anchor : '90%'
        }]
    }]

    });
    
    var modProForm = new Ext.form.FormPanel({
        id : "mod",
        labelWidth : 100,
        height : 450,
        frame : true,
        labelAlign : 'right',
        region : 'center',
        autoScroll : true,
        buttonAlign : "center",
        items : [ {
            layout : 'column',
            items : [ {
                columnWidth : .5,
                layout : 'form',
                items : [  {
                    name : 'id',
                    xtype : 'textfield',
                    fieldLabel : 'id',
                    hidden : true
                },{
                    name : 'productId',
                    xtype : 'textfield',
                    fieldLabel : '产品id',
                    hidden : true
                },{
                    name : 'productPropertyName',
                    xtype : 'textfield',
                    fieldLabel : '特征项名称',
                    width : '100',
                    anchor : '90%'
                }]
            }, {
                columnWidth : .5,
                layout : 'form',
                items : [{
                    store : typeStore,
                    xtype : 'combo', 
                    resizable : true,
                    name : 'PRODUCT_PROPERTY_TYPE_ORA',
                    hiddenName : 'productPropertyType',
                    fieldLabel : '特征项类别',
                    valueField : 'key',
                    displayField : 'value',
                    mode : 'local',
                    editable : false,
                    typeAhead : true,
                    forceSelection : true,
                    triggerAction : 'all',
                    emptyText : '请选择',
                    selectOnFocus : true,
                    width : '100',
                    anchor : '90%'
                }]
            }
            ]
        },{
        layout : 'form',
        buttonAlign : 'center',
        items : [ {
            xtype : 'textarea',
            labelStyle : {
            width : '120px'
            },
            //width : 200,
            fieldLabel : '特征项描述',
            name : 'productPropertyDesc',
            anchor : '90%'
        }]
    }]

    });
    
    
    var addProWindow = new Ext.Window({
        title : '特征项新增',
        plain : true,
        layout : 'fit',
        width : 450,
        height : 200,
        resizable : true,
        draggable : true,
        closable : true,
        closeAction : 'hide',
        modal : true, // 模态窗口
        loadMask : true,
        maximizable : true,
        collapsible : true,
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        items : [ addProForm ],
        buttons : [

                    {

                        text : '保  存',
                        handler : function() {
                            /*if(!addProForm.getForm().isValid()) { 
                                alert('请输入营销任务名称');
                                return false;
                            }
                            */
                        
                                addProForm.getForm().findField('productId').setValue(productId);
                                Ext.Ajax.request({
                                url : basepath + '/ProdProperty.json',
                                method : 'POST',
                                params:addProForm.getForm().getFieldValues(),
        //                      form : addProForm.getForm().id,
                                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                success : function() {
                                    Ext.Msg.alert('提示', '操作成功');
                                    producPropertyStore.reload();
                                },
                                failure : function(response) {
                                    var resultArray = Ext.util.JSON.decode(response.status);
                                     if(resultArray == 403) {
                                           Ext.Msg.alert('提示', response.responseText);
                                     }else{
                                    Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                                }
                                }
                            });
                            addProWindow.hide();
                            addProForm.getForm().reset();
                        }

                    }, {
                        text : '取  消',
                        handler : function() {
                            addProWindow.hide();
                        }
                    } ]
    });
    var modPropertyWin = new Ext.Window({
        title : '特征项修改',
        plain : true,
        layout : 'fit',
        width : 450,
        height : 200,
        resizable : true,
        draggable : true,
        closable : true,
        closeAction : 'hide',
        modal : true, // 模态窗口
        loadMask : true,
        maximizable : true,
        collapsible : true,
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        items : [ modProForm ],
        buttons : [

                    {

                        text : '保  存',
                        handler : function() {
                                Ext.Ajax.request({
                                    url : basepath + '/ProdProperty.json',
                                    method : 'POST',
                                    params:modProForm.getForm().getFieldValues(),
//                                  form : addProForm.getForm().id,
                                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                    success : function() {
                                        Ext.Msg.alert('提示', '操作成功');
                                        producPropertyStore.reload();
                                    },
                                    failure : function(response) {
                                        var resultArray = Ext.util.JSON.decode(response.status);
                                         if(resultArray == 403) {
                                               Ext.Msg.alert('提示', response.responseText);
                                         }else{
                                        Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                                    }
                                    }
                                });
                            modPropertyWin.hide();
                            modProForm.getForm().reset();
                        }

                    }, {
                        text : '取  消',
                        handler : function() {
                            modPropertyWin.hide();
                        }
                    } ]
    }); 
/*****************************特征项**********************************/
    var producPropertyRecord= Ext.data.Record.create(//特性项记录（record）
            [
             {name:'id',mapping:'ID'},
            {name:'productId',mapping:'PRODUCT_ID'},
//          {name:'productName',mapping:'PRODUCT_NAME'},
            {name:'productPropertyName',mapping:'PRODUCT_PROPERTY_NAME'},
            {name:'productPropertyType',mapping:'PRODUCT_PROPERTY_TYPE'},
            {name:'PRODUCT_PROPERTY_TYPE_ORA'},
            {name:'productPropertyDesc',mapping:'PRODUCT_PROPERTY_DESC'}
            ]
        );
     var producPropertyReader = new Ext.data.JsonReader(//读取特性项数据的jsonReader
                {
//                  totalProperty:'num',
//                  root:'rows'
                    totalProperty : 'json.count',
                    root:'json.data'
                },producPropertyRecord
            );
     
/*   var proxyIndex = new Ext.data.HttpProxy({
         
         url:basepath+'/product-property-list.json?productId='+productId+'',
         method:'GET'
        });
     */
    var producPropertyStore = new Ext.data.Store({//特性维护store

        
        
        
        
        //  id: 'notice',
            restful : true, 
            proxy : new Ext.data.HttpProxy({ url:basepath+'/product-property-list.json'}),
        
     //       reader : reader,
      //      writer : writer,
            recordType:producPropertyRecord,
            reader:producPropertyReader
        });

    
        
         
         var producPropertyColumns = new Ext.grid.ColumnModel(//产品特性项列数
                    {
                        columns:[
                    //  sm,
                        { header:'ID',dataIndex:'id',id:'id',sortable:true,hidden:true},
                        { header:'产品ID',dataIndex:'productId',id:'productId',sortable:true,hidden:true},
                        { header:'特征项名称',dataIndex:'productPropertyName',sortable:true,width:150
                        //  ,editor:new Ext.form.TextField()
                        },
                        { header:'特征项类别',dataIndex:'PRODUCT_PROPERTY_TYPE_ORA',sortable:true,width:150
//                          ,editor:new Ext.form.ComboBox({
//                          typeAhead: true,
//                          triggerAction: 'all',
//                          lazyRender:true,
//                          mode: 'local',
//                          store: new Ext.data.ArrayStore({
//                              id: 0,
//                              fields: [
//                                  'myId',
//                                  'displayText'
//                              ],
//                              data: [['1', '一级类'], ['2', '二级类'], ['3', '三级类']]
//                          }),
//                          valueField: 'displayText',
//                          displayField: 'displayText'
//                          })
                        },
                        { header:'特征项描述',dataIndex:'productPropertyDesc',id:'productProperty',width:150,sortable:true
        //              ,editor:new Ext.form.TextField()
                        }
                        ]
                    }
                );
         var sm = new Ext.grid.CheckboxSelectionModel();//特性项前面的复选框
         var producPropertyGrid = new Ext.grid.EditorGridPanel({//可修改的gridTable
                
                title:'特征项维护',
                store:producPropertyStore, 
                frame:true,
                cm:producPropertyColumns,
                region:'center',
                sm:sm,
                stripeRows: true,
                tbar:[ 
                {
                    text:'新增',
                    iconCls:'addIconCss ',
                    handler:function()
                    {
//                      Ext.getCmp('add').getForm().getEl().dom.reset();
                        addProForm.getForm().reset();
                        addProWindow.show();
                    
                    }
                
                },'-',
                {
                    text:'修改',
                    iconCls:'resetIconCss',
                    handler:function()
                    {
                        var record = producPropertyGrid.getSelectionModel().getSelected();
                        if(record==null || record=="undefined"){
                            Ext.MessageBox.alert('提示','请选择一条记录');
                            return;
                        }   
                        modProForm.getForm().loadRecord(record);
                        modPropertyWin.show();

                    }
                },
                {
                    text:'删除',
                   iconCls:'deleteIconCss',
                    handler:function()
                    {
                        Ext.Msg.confirm('信息', '确定要删除', function(btn) {  
                              if(btn == 'yes') {  
                               /* var sm = producPropertyGrid.getSelectionModel(); //得到表格的选择模型  
                                var cell = sm.getSelected(); //通过选择模型得到选择的单元格    
//                              var record = producPropertyStore.getAt(cell);  //得到store对应的Record  
                                    producPropertyStore.remove(cell);    
                                  debugger;*/
                                  deleteInit();
                                  function deleteInit(){
                                    
                                    /****************************************************************************************/
                                    var selectLength = producPropertyGrid.getSelectionModel()
                                    .getSelections().length;
                                    
                                    if(selectLength < 1){
                                        alert('请选择需要删除的记录!');
                                    } else {
                                        
                                        var selectRe;
                                        var tempId;
                                        var idStr = '';
                                        for(var i = 0; i<selectLength;i++)
                                        {
                                            selectRe = producPropertyGrid.getSelectionModel()
                                            .getSelections()[i];
                                            tempId = selectRe.data.id;
                                            idStr += tempId;
                                            if( i != selectLength-1)
                                                idStr += ',';
                                        }
                                        Ext.Ajax.request({
                                            url : basepath+'/ProdProperty/'+tempId+'.json?idStr='+idStr,
                                            method : 'DELETE',        
                                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                                            success : checkResult,
                                            failure : checkResult
                                        });
                                        
                                      function checkResult(response) {
                                        var resultArray = Ext.util.JSON.decode(response.status);
                                        var resultError = response.responseText;
                                        
                                        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
                                            Ext.Msg.alert('提示', '操作成功');
                                          producPropertyStore.reload();
                                        } else {
                                            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
                                          producPropertyStore.reload();
                                        }
                                    }
                                        
//                                    };

                                }
                                  

                                
                                  };
                              }  
                        });
                        
                    }
                
                }
                ],
         //      autoExpandColumn:'productName',
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
    
    
    //特征项维护
    var productProWind = new Ext.Window({
        closeAction:'hide',
        height:400,
        width:500,
        buttonAlign:'center',
        layout:'fit',
        buttons:[
        {
            text:'关闭',
            handler:function()
            {
                productProWind.hide();
            }
        }
        ],
        items:producPropertyGrid
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