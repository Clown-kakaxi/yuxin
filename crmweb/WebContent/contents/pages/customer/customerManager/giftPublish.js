Ext.onReady(function() {
	var tmepMethod = 'add';
	var sOrgIdJson={'orgid':[]};
	/**
	 * 数据存储
	 */
	var unitStore =  new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','件'],['2','元'],['3','箱'],['4','只'],['5','个'],['6','套'],['7','公斤'],['8','米']]
	});
	var giftTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','家用电器'],['2','手机数码'],['3','图书期刊'],['4','音像制品'],['5','汽车用品'],['6','运动健康'],['7','医疗保健'],['8','箱包服饰'],['9','食品饮料'],['10','消费卡类']]
	});
	     
	var custTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','公司客户'],['2','零售客户'],['3','全部']]
	});

	var custGroupStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','王一平相关客户群'],['2','本地房地产企业群'],['3','上市企业群'],['4','大连群'],['5','华北区大客户群']]
	});
 
	var operateTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','发布'],['2','调整'],['3','下架']]
	});

	 var addPotentialCustomerPanel = new Ext.FormPanel({
		 id:'add',
		  frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        items: [{
	           autoHeight:true,
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '礼品名称',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             id: 'giftName',
	                             name: 'giftName',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	 							name : 'giftType',
	 							id:'giftType',
								fieldLabel : '礼品种类',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : giftTypeStore,
								displayField : 'value',
								allowBlank : false,
								valueField : 'key',
								mode : 'local',
								readOnly : true,
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),{
	                             xtype:'textfield',
	                             fieldLabel: '礼品数量',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             hidden : true,
	                             id: 'giftSum',
	                             name: 'giftSum',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
		 							name : 'giftUnit',
		 							id:'giftUnit',
									fieldLabel : '礼品单位',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : unitStore,
									displayField : 'value',
									allowBlank : false,
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
		 							name : 'operateType',
		 							id:'operateType',
									fieldLabel : '操作类型',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : operateTypeStore,
									displayField : 'value',
							//		allowBlank : false,
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
		                             xtype:'textfield',
		                             fieldLabel: '发布状态',
		                             maxLength:50,
		                             allowBlank : false,
		                             labelStyle: 'text-align:right;',
		                             id: 'publishStatus',
		                             name: 'publishStatus',
		                             anchor:'95%'
		                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '兑换积分',
	                             maxLength:50,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             id: 'exchangePoints',
	                             name: 'exchangePoints',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: 'VIP兑换积分',
	                             maxLength:50,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             id: 'VIPexchangePoints',
	                             name: 'VIPexchangePoints',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
		 							name : 'adaptCustType',
		 							id:'adaptCustType',
									fieldLabel : '适应客户类型',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : custTypeStore,
									displayField : 'value',
							//		allowBlank : false,
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
	                             xtype:'textfield',
	                             fieldLabel: '发布人',
	                             maxLength:50,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             id: 'publisher',
	                             name: 'publisher',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                            	xtype:'datefield',
	                 				format:'Y-m-d',
	                 				fieldLabel : '有效期',
	                 				labelStyle: 'text-align:right;',
	                 				name : 'validity',
	                 				anchor : '95%'
	                         },new Com.yucheng.bcrm.common.OrgField({
									searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
									fieldLabel : '适应机构',
									labelStyle : 'text-align:right;',
									id : 'org', //放大镜组件ID，用于在重置清空时获取句柄
									name : 'CUST_ORG', 
									hiddenName: 'adaptinstitutions',   //后台获取的参数名称
									anchor : '95%',
									checkBox:false //复选标志
								}),new Ext.form.ComboBox({
		 							name : 'adaptCustGroup',
		 							id:'adaptCustGroup',
									fieldLabel : '适应特定客户群',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : custGroupStore,
									displayField : 'value',
							//		allowBlank : false,
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
	                            	xtype:'datefield',
	                 				format:'Y-m-d',
	                 				fieldLabel : '发布日期',
	                 				labelStyle: 'text-align:right;',
	                 				name : 'publishDate',
	                 				anchor : '95%'
	                         }]
	                     }
	            ]}
	            ]}]
	    });
	   
	 var addPotentialCustomerWindow = new Ext.Window(
				{
					layout : 'fit',
					width : 700,
					height : 280,
					draggable : true,//是否可以拖动
					closable : true,// 是否可关闭
					modal : true,
					closeAction : 'hide',
					titleCollapse : true,
					buttonAlign : 'center',
					border : false,
					animCollapse : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [addPotentialCustomerPanel],
					buttons : [
							{
								text : '保存',
								handler : function(){
									if(!addPotentialCustomerPanel.getForm().isValid()){
										Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
									return false;
									}
									Ext.Msg.alert('提示', '已保存为草稿');
									Ext.getCmp('add').getForm().getEl().dom.reset();
									addPotentialCustomerWindow.hide();
								}
							}, {
								text : '提交',
								handler : function() {
								if(!addPotentialCustomerPanel.getForm().isValid()){
									Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
								return false;
								}
								Ext.Msg.alert('提示', '已提交');
								Ext.getCmp('add').getForm().getEl().dom.reset();
								addPotentialCustomerWindow.hide();
							
								
								}
							}, {
								text : '重置',
								id : 'btnReset',
								handler : function() {
							//	Ext.getCmp('add').getForm().getEl().dom.reset();
								addPotentialCustomerPanel.getForm().reset();   
									//clearForm(addRoleFormPanel.getForm());
								}
							}, {
								text : '关闭',
								handler : function() {
								
								Ext.getCmp('add').getForm().getEl().dom.reset();
								addPotentialCustomerWindow.hide();
								}
							} ]
				});
	    

/******************************查询面板*******************************************/
		var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			region: 'north',
		    title: "服务管理->积分管理->礼品管理->礼品发布", 
		    height: 120,
		    buttonAlign : 'center',
				layout : 'column',
				items : [{
							columnWidth : .25,
							layout : 'form',
							defaultType : 'textfield',
							border : false,
							items : [
				         {
							fieldLabel : '礼品名称',
							name : 'CUST_ID',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
						}]
						}, {
							columnWidth : .25,
							layout : 'form',
							defaultType : 'textfield',
							border : false,
							items : [
							         new Ext.form.ComboBox({
											hiddenName : 'CUST_TYP',
											fieldLabel : '礼品种类',
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : giftTypeStore,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											typeAhead : true,
											emptyText:'请选择',
											resizable : true,
											anchor : '90%'
										})]
						}, {
							columnWidth : .25,
							layout : 'form',
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '适应机构',
								name : 'CERT_NUM',
								//id:'CERT_NUM',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}
							]
						}, {
							columnWidth : .25,
							layout : 'form',
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '适应客户类型',
								name : 'CERT_NUM',
							//	id:'CERT_NUM',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
						}]
			}],
		buttons : [{
					text : '查询',
					handler : function() {
			store.loadData(storeData);
		}},{
					text : '重置',
					     handler : function() {
					    	 qForm.getForm().reset();
					    	
						}
					}]
		});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	        {header : '礼品名称',dataIndex : 'giftName',sortable : true,width : 150},
		    {header : '礼品种类',dataIndex : 'giftType',width : 200,sortable : true},
		    {header : '礼品数量',dataIndex : 'giftSum',width : 150,sortable : true},
		    {header : '礼品单位',dataIndex : 'giftUnit',width : 150,sortable : true},
		    {header : '兑换积分',dataIndex : 'exchangePoints',width : 150,sortable : true},
		    {header : 'VIP兑换积分',dataIndex : 'VIPexchangePoints',width : 200,sortable : true},
		    {header : '有效期',dataIndex : 'validity',width : 200,sortable : true},
		    {header : '适应机构',dataIndex : 'adaptInstitutions',width : 200,sortable : true},
		    {header : '适应客户类型',dataIndex : 'adaptCustType',sortable : true},
		    {header : '适应特定客户群',dataIndex : 'adaptCustGroup',width : 150,sortable : true},
		    {header : '发布人',dataIndex : 'publisher',width : 150,sortable : true},
		    {header : '发布日期',dataIndex : 'publishDate',width : 150,sortable : true},
		    {header : '发布状态',dataIndex : 'publishStatus',width : 150,sortable : true}
		    
			]);

	var storeData = {//特性项数据
			num:6,
			rows:[
			{"giftName":"洗衣机","giftType":"家用电器","giftSum":"123","giftUnit":"个","exchangePoints":"170000","VIPexchangePoints":"100000","validity":"2012-08-16","adaptInstitutions":"世纪支行","adaptCustType":"零售客户","adaptCustGroup":"王一平相关客户群","publisher":"李毅","publishDate":"2012-08-16","publishStatus":"已提交"},
			{"giftName":"手机","giftType":"手机数码","giftSum":"23","giftUnit":"个","exchangePoints":"110000","VIPexchangePoints":"80000","validity":"2012-08-16","adaptInstitutions":"世纪支行","adaptCustType":"公司客户","adaptCustGroup":"上市企业群","publisher":"李毅","publishDate":"2012-08-16","publishStatus":"已发布"},
			{"giftName":"电冰箱","giftType":"家用电器","giftSum":"34","giftUnit":"个","exchangePoints":"102000","VIPexchangePoints":"82000","validity":"2012-08-16","adaptInstitutions":"北三环支行","adaptCustType":"全部","adaptCustGroup":"本地房地产企业群","publisher":"李毅","publishDate":"2012-08-16","publishStatus":"已发布"},
			{"giftName":"衣物","giftType":"箱包服饰","giftSum":"57","giftUnit":"件","exchangePoints":"12000","VIPexchangePoints":"10000","validity":"2012-08-16","adaptInstitutions":"北三环支行","adaptCustType":"全部","adaptCustGroup":"华北大客户群","publisher":"李毅","publishDate":"2012-08-16","publishStatus":"已提交"}
			]
			
		}; 
	var storeRecord = Ext.data.Record.create(//特性项记录（record）
			[
				{name:'giftName'},
				{name:'giftType'},
				{name:'giftSum'},
				{name:'giftUnit'},
				{name:'exchangePoints'},
				{name:'VIPexchangePoints'},
				{name:'validity'},
				{name:'adaptInstitutions'},
				{name:'adaptCustType'},
				{name:'adaptCustGroup'},
				{name:'publisher'},
				{name:'publishDate'},
				{name:'publishStatus'}
				]
			);
	var storerReader = new Ext.data.JsonReader(//读取特性项数据的jsonReader
			{
				totalProperty:'num',
				root:'rows'
			},storeRecord
		);
	var store = new Ext.data.Store({//特性项数据的store
		reader:storerReader
	});
	
     var pagesize_combo = new Ext.form.ComboBox({
         name : 'pagesize',
         triggerAction : 'all',
         mode : 'local',
         store : new Ext.data.ArrayStore({
             fields : ['value', 'text'],
             data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ],
					[ 500, '500条/页' ] ]
         }),
         valueField : 'value',
         displayField : 'text',
         value : '20',
         editable : false,
         width : 85
     });
    var number = parseInt(pagesize_combo.getValue());
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load(storeData);
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	var checkedNodessd = '';
	// 表格工具栏

	var tbar = new Ext.Toolbar({

				items : [{
					text : '礼品发布',
					iconCls:'addIconCss',
					id:'addPer',
					handler : function() {
					Ext.getCmp('add').getForm().getEl().dom.reset();
					addPotentialCustomerWindow.show();
					}
				},'-',{
					text : '礼品调整',
					iconCls:'editIconCss',
					id:'del',
					handler : function() {
					
					var record = grid.getSelectionModel().getSelected();
	      			if(record==null || record=="undefined"){
	      				Ext.MessageBox.alert('提示','请选择一条记录');
	      				return;
	      			}      		
	      			addPotentialCustomerPanel.getForm().reset();
	      			addPotentialCustomerWindow.setTitle('产品修改');     			
	      			addPotentialCustomerPanel.getForm().loadRecord(record);
	      			addPotentialCustomerWindow.show();
	      			

					
					}
				}]
			});


/******************************表格显示******************************************/
	var grid = new Ext.grid.GridPanel({
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : [tbar,{
					text : '审批',
					iconCls:'shenpiIconCss',
					handler : function() {

					var columns = grid.getSelectionModel().getSelections();
					if(columns.length == 1){
						Ext.Msg.alert('提示', '操作成功');
					}else{
						Ext.Msg.alert('提示','请选择一条记录进行修改');
						return ;
					}
				
					Ext.Msg.alert('提示', '已发布');
					
				}
				}], // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


   
	// 布局模型
	    
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
		layout : 'border',
		items: [qForm,grid] 
		}]
});

}); 