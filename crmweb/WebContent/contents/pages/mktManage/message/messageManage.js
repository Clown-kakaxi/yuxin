/**
 * 短信营销管理页面
 */

Ext.onReady(function() {
	var dxForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		layout : 'column',
		border : false,
		items : [{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
				{fieldLabel : '客户号',name : 'CUST_ID',xtype : 'textfield',labelStyle: 'text-align:right;',anchor : '90%'}]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth: 90, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
				{fieldLabel : '客户名称',name : 'CUST_NAME',xtype : 'textfield',labelStyle: 'text-align:right;',anchor : '90%'}
				]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
			         new Com.yucheng.crm.common.ProductManage({
										xtype : 'productChoose',
										fieldLabel : '产品名称',
										labelStyle : 'text-align:right;',
										name : 'PROD_NAME',
										hiddenName : 'PROD_ID',
										singleSelect : true,
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										anchor : '90%'
									})
			]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
			         {fieldLabel : '审批状态',hidden:true,hiddenName : 'APPROVE_STATE',name:'APPROVE_STATE',xtype : 'combo',labelStyle: 'text-align:right;',triggerAction : 'all',
			        	 store : approvestore,displayField : 'value',valueField : 'key',mode : 'local',forceSelection : true,typeAhead : true,emptyText:'请选择',resizable : true,anchor : '90%'}
			]
		}],
		buttons : [{
			text : '查询',
			handler : function() {
				var parameters = dxForm.getForm().getValues(false);
				dxStore.baseParams = {
					'condition':Ext.util.JSON.encode(parameters)
				};
				dxStore.load({      
					params : {
                       start : 0,
                       limit : bbar.pageSize
                    }
				});     
		
		   }
		},{
			text : '重置',
		     handler : function() {
		    	 dxForm.getForm().reset();
			}
		}]
	});
	
	var dxStore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFMmSysMsg.json'}),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
			{name: 'custId',mapping :'CUST_ID'},
			{name: 'custName',mapping :'CUST_NAME'},
			{name:'id',mapping:'ID'},
			{name:'cellNumber',mapping:'CELL_NUMBER'},
			{name:'prodId',mapping:'PROD_ID'},
			{name:'prodName',mapping:'PROD_NAME'},
			{name:'modelId',mapping:'MODEL_ID'},
			{name:'messageRemark',mapping:'MESSAGE_REMARK'},
			{name:'ifApprove',mapping:'IF_APPROVE'},
			{name:'IF_APPROVE_ORA'},
			{name:'approveState',mapping:'APPROVE_STATE'},
			{name:'APPROVE_STATE_ORA'},
			{name:'userId',mapping:'USER_ID'},
			{name:'crtDate',mapping:'CRT_DATE'}
		])
	});
	
	 //复选框
	var dxsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var dxrownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	// 定义列模型
	var dxcm = new Ext.grid.ColumnModel([dxrownum,dxsm, 
	    {header : '编号',dataIndex : 'id',sortable : true,width : 150,hidden:true},
        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
	    {header : '客户名称',dataIndex : 'custName',width : 240,sortable : true},
	    {header : '产品名称',dataIndex : 'prodName',width : 240,sortable : true},
	    {header : '是否需要审批',dataIndex : 'IF_APPROVE_ORA',width : 100,sortable : true,hidden:true},
	    {header : '审批状态',dataIndex : 'APPROVE_STATE_ORA',width : 150,sortable : true,hidden:true},
	    {header : '建立日期',dataIndex : 'crtDate',width :150,sortable : true},
	    {header : '短信内容',dataIndex : 'messageRemark',width :400,sortable : true}
	    
	]);

     var dxpagesize_combo = new Ext.form.ComboBox({
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
    var dxnumber = parseInt(dxpagesize_combo.getValue());
    dxpagesize_combo.on("select", function(comboBox) {
    	dxbbar.pageSize = parseInt(dxpagesize_combo.getValue()),
    	dxStore.load({
			params : {
				start : 0,
				limit : parseInt(dxpagesize_combo.getValue())
			}
		});
	});
	var dxbbar = new Ext.PagingToolbar({
        pageSize : number,
        store : dxStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', dxpagesize_combo]
    });
	
	var panelInfo =  new Ext.FormPanel({
		frame : true,
		height :'100%',
		items : [{
			xtype:'textarea',
			fieldLabel : '短信内容',
			name : 'messageRemark',
			labelStyle : 'text-align:right;',
			readOnly : true,
			anchor : '100%'
		}]
	});
	
	var winInfo =  new Ext.Window({
		title:'短信内容',
		height : 150,
		width : 400,
		buttonAlign : 'center',
		draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll : true,
		closeAction : 'hide',
		border : false,
		items : [ panelInfo ],
		buttons : [ {
			text : '关 闭',
			handler : function() {
				winInfo.hide();
			}
		} ]
	});
	var dxtbar = new Ext.Toolbar({
		items : [{text:'查看内容',
		     iconCls:'detailIconCss',
		     handler:function(){
		    	 var checkedNodes = dxGrid.getSelectionModel().selections.items;
					if (checkedNodes.length == 0) {
						Ext.Msg.alert('提示', '请选择记录');
						return;
					} else if (checkedNodes.length > 1) {
						Ext.Msg.alert('提示', '请选择一条记录');
						return;
					}
					panelInfo.getForm().loadRecord(checkedNodes[0]);
					winInfo.show();
				}
		 }]
	});
	
	// 表格实例
	var dxGrid = new Ext.grid.GridPanel({
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : dxStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : dxcm, // 列模型
				sm : dxsm, // 复选框
				tbar : [dxtbar], // 表格工具栏
				bbar:dxbbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


	var messagePanel  = new Ext.Panel({
		title: "短信查询", 
	    layout:'fit',
	    items:[{
				layout : 'border',
				items: [{   
					region: 'north',
				    height: 80,
				    hidden:false,
				    margins: '0 0 0 0',
					items:[dxForm]
			     },{   
			    	region:'center',
				    layout:'fit',
				    margins: '0 0 0 0',
				    items : [dxGrid]
			    }] 
	    }]
	});
// 布局模型
		var tabs = new Ext.TabPanel({
			xtype:"tabpanel",			   			   
			region:"center",
			activeTab: 0,
		    items: [custPanel,groupPanel,messagePanel],
		    listeners:{
		    	'tabchange':function(){
		    		if (tabs.getActiveTab().title == '短信查询') {
		    			dxStore.reload();
		    		}
		    	}
		    }
		    });
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ tabs ]
		});
		
});