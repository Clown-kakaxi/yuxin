Ext.onReady(function() {
	var custid = '';
			//会话页面调用时使用
			var parms = '';
			if (window.location.search) {
				parms = Ext.urlDecode(window.location.search);
			}
			custid = parms['?custid'];
		
	
	
	 var typeStore = new Ext.data.Store({
	        restful:true,   
	        autoLoad :true,
	        sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	        proxy : new Ext.data.HttpProxy({
	                url :basepath+'/lookup.json?name=FEATURE_DISC'
	        }),
	        reader : new Ext.data.JsonReader({
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    }); 
	    //产品状态
	    var prodStatStore = new Ext.data.Store({
	        restful:true,   
	        autoLoad :true,
	        sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	        proxy : new Ext.data.HttpProxy({
	                url :basepath+'/lookup.json?name=PROD_STATE'
	        }),
	        reader : new Ext.data.JsonReader({
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    //是否标志
	    var IFStore = new Ext.data.Store({
	        restful : true,
	        autoLoad : true,
	        sortInfo : {
	            field:'key',
	            direction:'DESC'
	        },
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=IF_FLAG'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    //风险级别
	    var riskTypeStore = new Ext.data.Store({
	        restful : true,
	        autoLoad : true,
	        sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=PROD_RISK_LEVEL'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    //流动性
	    var flowAbilityStore = new Ext.data.Store({
	        restful : true,
	        autoLoad : true,
	        sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=FLOW_ABILITY'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    //市场方向
	    var marketDirectionStore = new Ext.data.Store({
	        restful : true,
	        autoLoad : true,
	        sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=MARKET_DIRECTION'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    //5级分类
	    var fiveClassifyStore = new Ext.data.Store({
	        restful : true,
	        autoLoad : true,
	        sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=PRODUCT_TYPE'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
    var productEditPanel = new Ext.FormPanel({ //产品信息formpanel
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        width: '99%',
        height:355,
        autoScroll : true,
        split:true,
        labelWidth:120,
        items: [{
        	items :[{  
            	layout:'column',
             	items:[{
                	columnWidth:.33,
                	layout: 'form',
                	items: [
                	 {xtype:'textfield',fieldLabel: '产品编码',labelStyle: 'text-align:right;',disabled:true,name: 'productId',id:'productIdId',allowBlank : false,anchor:'100%'},
                     {xtype:'textfield',fieldLabel: '产品类别编码',labelStyle: 'text-align:right;',disabled:true,name: 'catlCode',readOnly:true,anchor:'100%'},
                     {name:'productState',hiddenName:'productState',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'产品状态',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: prodStatStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value',value:'1'},
                     {xtype:'datefield',fieldLabel: '产品发布日期',disabled:true,labelStyle: 'text-align:right;',name: 'productStartDate',id: 'productStartDate1',format:'Y-m-d',anchor:'100%'},
	                 {xtype:'datefield',fieldLabel: '产品有效截止日期',disabled:true,labelStyle: 'text-align:right;',name: 'productEndDate',id: 'productEndDate1',format:'Y-m-d',anchor:'100%'},
	                 {xtype:'datefield',name: 'addedDate',disabled:true,fieldLabel: '上架日期',labelStyle: 'text-align:right;',format:'Y-m-d',anchor:'100%'},
	                 {xtype:'datefield',name: 'soldoutDate',disabled:true,fieldLabel: '下架日期',labelStyle: 'text-align:right;',format:'Y-m-d',anchor:'100%'},
	                 {name:'fxjb',hiddenName:'parentClassify',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'上级分类',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: fiveClassifyStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	 {name:'ifInternalSale',hiddenName:'ifInternalSale',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'是否行内销售',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: IFStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	 {xtype:'textfield',labelStyle: 'text-align:right;',disabled:true,fieldLabel: '销售范围',name: 'saleOrg',anchor:'100%'},
                 	 {xtype:'numberfield',labelStyle: 'text-align:right;',disabled:true,fieldLabel: '收益率(%)',name: 'incomeProfit',anchor:'100%'},
                 	 {xtype:'textarea',fieldLabel: '产品描述',disabled:true,name: 'productDescription',labelStyle: 'text-align:right;',height:74, maxLength : 150,anchor:'100%'},
	                 {xtype:'textarea',fieldLabel: '产品特点',disabled:true,name: 'prod_charact',labelStyle: 'text-align:right;',height:74,maxLength : 150,anchor:'100%'}            
	                ]
	             },{
                 columnWidth:.33,
                 layout: 'form',
                 items: [
	                 {xtype:'textfield',labelStyle: 'text-align:right;',fieldLabel: '产品名称',disabled:true,name: 'productName',allowBlank : false,anchor:'100%'},
	                 {xtype:'textfield',labelStyle: 'text-align:right;',fieldLabel: '产品类别',disabled:true,name: 'catlName',readOnly: true,anchor:'100%'},
	                 {xtype:'textfield',fieldLabel: '费率(%)',disabled:true,labelStyle: 'text-align:right;',name: 'cost_rate',anchor:'100%'},
	                 {xtype:'textfield',labelStyle: 'text-align:right;',fieldLabel: '期限',disabled:true,name: 'limit_time',anchor:'100%'},
	                 {xtype:'textfield',fieldLabel: '利率(%)',disabled:true,labelStyle: 'text-align:right;',name: 'rate',anchor:'100%'},
	                 {xtype:'textarea',fieldLabel: '客户准入规则',disabled:true,labelStyle: 'text-align:right;',hidden:true,name: 'custTarRule',anchor:'100%'},
		             {name:'ifRecommend',hiddenName:'ifRecommend',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'是否推荐',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: IFStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	 {xtype:'datefield',name: 'recommendEndDate',disabled:true,fieldLabel: '推荐结束日期',labelStyle: 'text-align:right;',format:'Y-m-d',anchor:'100%'},
                 	 {xtype:'textarea',name: 'recommentReason',disabled:true,fieldLabel: '推荐理由',labelStyle: 'text-align:right;',height:74,maxLength : 150,anchor:'100%'},
                 	 {xtype:'textarea',fieldLabel: '目标客户描述',disabled:true,name: 'obj_cust_disc',height:74,maxLength : 150,labelStyle: 'text-align:right;',anchor:'100%'},
		             {xtype:'textarea', fieldLabel: '担保要求描述',disabled:true,name: 'assure_disc',labelStyle: 'text-align:right;',height:74, maxLength : 150,anchor:'100%'}                         
	            ]
	         },{
                 columnWidth:.33,
                 layout: 'form',
                 items: [
                 	{name:'riskLevel',hiddenName:'riskLevel',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'风险级别',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: riskTypeStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	{name:'flowAbility',hiddenName:'flowAbility',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'流动性',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: flowAbilityStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	{name:'marketDirection',disabled:true,hiddenName:'marketDirection',xtype:'combo',anchor:'100%',fieldLabel:'市场方向',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: marketDirectionStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	{name:'ifNew',hiddenName:'ifNew',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'是否新品',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: IFStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	{xtype:'datefield',name: 'newEndDate',disabled:true,fieldLabel: '新品结束日期',labelStyle: 'text-align:right;',format:'Y-m-d',anchor:'100%'},
                 	{name:'ifDiscount',hiddenName:'ifDiscount',disabled:true,xtype:'combo',anchor:'100%',fieldLabel:'是否优惠',labelStyle: 'text-align:right;',triggerAction:'all',
                        mode:'local',store: IFStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
                 	{xtype:'datefield',name: 'discountEndDate',disabled:true,fieldLabel: '优惠结束日期',labelStyle: 'text-align:right;',format:'Y-m-d',anchor:'100%'},
                 	{xtype:'textarea',name: 'discountInfo',disabled:true,fieldLabel: '优惠信息',labelStyle: 'text-align:right;',height:74,maxLength : 150,anchor:'100%'},
                 	{xtype:'textarea',fieldLabel: '风险提示描述',disabled:true,name: 'danger_disc',labelStyle: 'text-align:right;',height:74,maxLength : 150,anchor:'100%'},
                 	{xtype:'textarea',fieldLabel: '营销渠道描述',disabled:true,name: 'channel_disc',labelStyle: 'text-align:right;',height:74,maxLength : 150,anchor:'100%'} 
                 ]
	         }
	    ]}
        ]
        },{
        items :[{  
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [
                	{xtype:'textfield',fieldLabel: '所属法人号',labelStyle: 'text-align:right;',hidden:true,name: 'frId',anchor:'100%'},
                 	{xtype:'textfield',fieldLabel: '创建人',labelStyle: 'text-align:right;',hidden:true,name: 'productCreator', anchor:'100%'}
                ]
             }]
          }]
       }]
    }); 
    var productInfoRecord = new Ext.data.Record.create([    
                                                        {name:'productId',mapping:'PRODUCT_ID'},
                                                        {name:'productName',mapping:'PROD_NAME'},
                                                        {name:'catlCode',mapping:'CATL_CODE'},
                                                        {name:'catlName',mapping:'CATL_NAME'},
                                                        {name:'productState',mapping:'PROD_STATE'},
                                                        {name:'prodState',mapping:'PROD_STATE'},
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
                                                    	
                                                    	{name:'incomeProfit',mapping:'INCOME_PROFIT'},
                                                    	{name:'flowAbility',mapping:'FLOW_ABILITY'},
                                                    	{name:'flowAbility',mapping:'FLOW_ABILITY'},
                                                    	{name:'marketDirection',mapping:'MARKET_DIRECTION'},
                                                    	{name:'ifNew',mapping:'IF_NEW'},
                                                    	{name:'newEndDate',mapping:'NEW_END_DATE'},
                                                    	{name:'ifDiscount',mapping:'IF_DISCOUNT'},
                                                    	{name:'discountEndDate',mapping:'DISCOUNT_END_DATE'},
                                                    	{name:'discountInfo',mapping:'DISCOUNT_INFO'},
                                                    	{name:'ifRecommend',mapping:'IF_RECOMMEND'},
                                                    	{name:'recommendEndDate',mapping:'RECOMMEND_END_DATE'},
                                                    	{name:'recommentReason',mapping:'RECOMMENT_REASON'},
                                                    	{name:'addedDate',mapping:'ADDED_DATE'},
                                                    	{name:'soldoutDate',mapping:'SOLDOUT_DATE'},
                                                    	{name:'parentClassify',mapping:'PARENT_CLASSIFY'},
                                                    	{name:'PARENT_CLASSIFY_ORA'},
                                                    	{name:'ifInternalSale',mapping:'IF_INTERNAL_SALE'},
                                                    	{name:'IF_INTERNAL_SALE_ORA'},
                                                    	{name:'saleOrg',mapping:'SALE_ORG'},
                                                    	{name:'riskLevel',mapping:'RISK_LEVEL'},
                                                    	{name:'RISK_LEVEL_ORA'},
                                                    	{name:'frId',mapping:'FR_ID'}
                                                        ]);
                                                        
     var productInfoReader = new Ext.data.JsonReader({//读取json数据的panel
                              totalProperty:'json.count',
                              root:'json.data'
                             },productInfoRecord
               );
      var productInfoStore = new Ext.data.Store({
                              proxy:new Ext.data.HttpProxy({
                                   url:basepath+'/custFitProdDetailsQueryAction.json',
                                    method:'GET'
                              }),
                 reader:productInfoReader
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
		        	  		o.el.dom.innerHTML = '<iframe id="content3" name="content3" style="width:100%;height:450px;" frameborder="no"" src=\"'
				  				+ basepath + '/contents/pages/demo/docs/doc1.jsp?nodeId='+nodeId + '\" "/> scrolling="auto"> ';
						}
					  }
			}
        ]
    });	

	var productInfo = new Ext.Window({//产品推荐window

		title : '产品详情',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height:420,
		width:1100,
		draggable : false,
		closeAction : 'hide',
		modal : true, // 模态窗口 
		border : false,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items:[prodTabs]
	});
//	// 列选择模型
	var sm = new Ext.grid.CheckboxSelectionModel();
//	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var productInfoColumns2 = new Ext.grid.ColumnModel([//gridtable中的列定义
//		                                                sm,
		                                               rownum,
		                                               {header :'ID',dataIndex:'id2',id:"id2",width:150,sortable : true,hidden:true},
		                                               {header :'产品名称',dataIndex:'productName',id:"productId2",width:150,sortable : true}
		                                               ]);

	   var store2 = new Ext.data.Store({
			//restful:true,
//	        proxy : new Ext.data.HttpProxy({url:basepath+'/eventinformation.json?customerId='+tempUserId}),
	        reader: new Ext.data.JsonReader({
             //data:tb_memberData,

	           // successProperty: 'success',
	            root:'rows',
	            totalProperty: 'num'
	        }, [ {name:'id'},
             {name:'productId2'},
             {name:'productName2'},
             {name:'productType2'},
             {name:'order2'},
             {name:'fitRate2'}
			])
		});

	 var advisePanel = new Ext.form.FormPanel({
			height:230,
			width:400,
			buttonAlign:'center',
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
					 columnWidth:.8,
					 layout:'form',
					 items:[{
				        		xtype:'textfield',
								name:'id',
								fieldLabel:'ID',
								anchor:'100%',
								hidden:true
							},
					        {
					        	xtype:'textfield',
									name:'accountName',
									fieldLabel:'产品名称',
									anchor:'100%'
								},
								 {
								     fieldLabel: '推荐方式',
								     name: 'COMPLAINTED_BUSINESS',
								     editable:false,
		                             forceSelection : true,
		                             xtype:'combo',
		                             labelStyle: 'text-align:right;',
		                             triggerAction:'all',
		                             mode:'local',
		                             store:new Ext.data.ArrayStore({
		                                 fields:['myId','displayText'],
		                                 data:[['电话','电话'],['网点','网点'],
		                                       ['邮件','邮件'],['短信','短信']]
		                             }),
		                             valueField:'myId',
		                             displayField:'displayText',
		                             emptyText:'请选择',
		                             anchor : '100%'
		                         },
									{
								     fieldLabel: '购买意愿',
								     name: 'COMPLAINTED_BUSINESS',
								     editable:false,
		                             forceSelection : true,
		                             xtype:'combo',
		                             labelStyle: 'text-align:right;',
		                             triggerAction:'all',
		                             mode:'local',
		                             store:new Ext.data.ArrayStore({
		                                 fields:['myId','displayText'],
		                                 data:[['十分强烈','十分强烈'],
		                                       ['有兴趣','有兴趣'],['无兴趣','无兴趣']]
		                             }),
		                             valueField:'myId',
		                             displayField:'displayText',
		                             emptyText:'请选择',
		                             anchor : '100%'
		                         },{
								     fieldLabel: '是否成功',
								     name: 'COMPLAINTED_BUSINESS',
								     editable:false,
		                             forceSelection : true,
		                             xtype:'combo',
		                             labelStyle: 'text-align:right;',
		                             triggerAction:'all',
		                             mode:'local',
		                             store:new Ext.data.ArrayStore({
		                                 fields:['myId','displayText'],
		                                 data:[['是','是'],
		                                       ['否','否']]
		                             }),
		                             valueField:'myId',
		                             displayField:'displayText',
		                             emptyText:'请选择',
		                             anchor : '100%'
		                         },{ 
                                  xtype:'textarea',
                                  fieldLabel: '后续计划',
                                  maxLength : 150,
                                   labelStyle: 'text-align:right;',
                                  name: 'obj_cust_disc',
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
			                	text:'推荐',
			                	handler:function(){
			                	productAdvise2.hide();
			                }
			                }
			                ]
		});
	var productAdvise2 = new Ext.Window({//产品推荐window

		title : '产品推荐',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height:250,
		width:400,
		draggable : false,
		closeAction : 'hide',
		modal : true, // 模态窗口 
		border : false,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
//		tbar:tbar3,
		items:[advisePanel]
	});
	var tbar3 = new Ext.Toolbar({
	items : [
	         {
			text : '推荐',
			handler : function() {

	        	 productAdvise2.show();
				
	         }
	         }
	         ]
});
	var productGrid2 =  new Ext.grid.GridPanel({//用户列表数据grid
	frame:true,
	width:'100%',
	height:600,
	id:'productGrid2',
	autoScroll : true,
	tbar:tbar3,
//	bbar:bbar,
	stripeRows : true, // 斑马线
	store:store2,
	loadMask:true,
	cm :productInfoColumns2,
	sm :sm,
	viewConfig:{
		forceFit:false,
		autoScroll:true
	},
        loadMask : {
	msg : '正在加载表格数据,请稍等...'
}
});

	var productAdvise = new Ext.Window({//产品推荐window

		title : '产品推荐',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height:300,
		width:700,
		draggable : false,
		closeAction : 'hide',
		modal : true, // 模态窗口 
		border : false,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
//		tbar:tbar3,
		items:[productGrid2]
	});
	var productDetail = new Ext.Window({//产品饼图展示window

		title : '客户分布展示图',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height:430,
		width:1050,
		draggable : false,
		 autoScroll:true,
		closeAction : 'hide',
		modal : true, // 模态窗口 
		border : false,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
        layout:'border',
	       // border:false,
	        items:[{
	            xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[
	            	{
	                columnWidth:.33333,
	                border:false,
	                autoHeight:true,
	                id:'cus',
	                //layout:'fit',
	                items:[{
	                    title: '客户分布统计图-年收入',
	                    collapsible:true,
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    height:200,
	                    html:'<iframe id="contentFrame" name="content" height="200" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/contribute/aa.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户分布统计图-年龄段',
	                    height:200,
            		    html:'<iframe id="contentFrame4" name="content4" height="200" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/contribute/bb.html\" "/> scrolling="no"> </iframe>'
	                }]
	            },{
	                columnWidth:.33333,
	                autoHeight:true,
	                //layout:'fit',
	                border:false,
	                items:[{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户分布统计图-职位',
	                    height:200,
            		    html:'<iframe id="contentFrame1" name="content1" height="200" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/contribute/dd.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户分布统计图-性别',
	                    height:200,
            		    html:'<iframe id="contentFrame3" name="content3" height="200" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/contribute/ee.html\" "/> scrolling="no"> </iframe>'
	                }]
	                
	            },{
	                columnWidth:.33333,
	                autoHeight:true,
	                //layout:'fit',
	                border:false,
	                items:[{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户分布统计图-总资产',
	                    height:200,
            		    html:'<iframe id="contentFrame2" name="content2" height="200" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/contribute/cc.html\" "/> scrolling="no"> </iframe>'
	                }]
	                
	            },{
	                columnWidth:.33333,
	                autoHeight:true,
	                //layout:'fit',
	                border:false,
	                items:[{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户分布统计图-等级',
	                    height:200,
            		    html:'<iframe id="contentFrame2" name="content2" height="200" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/contribute/khfb.html\" "/> scrolling="no"> </iframe>'
	                }]
	                
	            }] 
	        }]
	});	


	var tbar = new Ext.Toolbar({
		items : [
		         {
				text : '产品详情',
				iconCls : 'detailIconCss',
				handler : function() {

					 var selectLength = productGrid.getSelectionModel()
				        .getSelections().length;
				        
				        if(selectLength > 1){
				            alert('请选择一条记录!');
				        } else{
					    var infoRecord = productGrid.getSelectionModel().getSelected();
				        if(infoRecord == null||infoRecord == ''){
				            Ext.Msg.alert('提示','请选择一行数据');
				        }else{
				        	
				        	productInfoStore.load({
				        		params:{
				        			'prodId':infoRecord.data.prodId
				        		},
				        		callback:function(){
						        	productEditPanel.getForm().loadRecord(productInfoStore.getAt(0));
				        		}
				        	});
				        	productInfo.show();
				        }}					
		         }
		         }
		        /* ,'-',{
		        	 text : '客户分布统计',
		        	 iconCls : 'custGroupMemIconCss',
		        	 handler : function() {
		        	 productDetail.show();
		         }
		         }
		         ,'-',{
		        	 text : '产品推荐',
		        	 iconCls : 'resetIconCss',
		        	 handler : function() {
		        	 productAdvise.show();
		         }
		         }*/
		         ]
	});
//	// 列选择模型
	var sm = new Ext.grid.CheckboxSelectionModel();
//	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	   var store = new Ext.data.Store({
			restful:true,
	        proxy : new Ext.data.HttpProxy({url:basepath+'/custFitProdQueryAction.json?custId='+custid}),
	        reader: new Ext.data.JsonReader({
	        	totalProperty : 'json.count',
				  root:'json.data'
	        }, [ {name:'id',mapping:'ID'},
             {name:'prodId',mapping:'PROD_ID'},
             {name:'prodName',mapping:'PROD_NAME'},
             {name:'FR_ID_ORA'},
             {name:'PROD_FR_ID_ORA'}
			])
		});
	var productInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
//	                                                sm,
	                                               rownum,
	                                               {header :'ID',dataIndex:'id',id:"id",width:150,sortable : true,hidden:true},
	                                               {header :'产品编号',dataIndex:'prodId',id:"prodId",width:150,sortable : true},
	                                               {header:'产品名称',dataIndex:'prodName',id:'prodName',width:200,sortable : true}
	                                               ]);


	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
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

	var number = parseInt(pagesize_combo.getValue());
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
			params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
		});
	});
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});
	var productGrid =  new Ext.grid.GridPanel({//用户列表数据grid
		frame:true,
		width:'100%',
		//width : document.body.clientWidth-240,
		autoScroll : true,
//		tbar:tbar,
		bbar:bbar,
		stripeRows : true, // 斑马线
		store:store,
		loadMask:true,
		cm :productInfoColumns,
//		sm :sm,
		viewConfig:{},
	        loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
	});
	store.load();
	
	
	var view=new Ext.Viewport({
		layout:'fit',
		autoScroll : true,
		height:document.body.clientHeight-30,
		items:[productGrid]

	});

}) ;