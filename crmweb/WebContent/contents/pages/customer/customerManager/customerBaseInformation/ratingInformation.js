Ext.onReady(function() {
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">客户评级信息</span>',
		width: '100%',
	    height:300,
		items: [{
		    autoHeight:true,
		    readOnly:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '客户名称',
							  labelStyle: 'text-align:right;',
							 name: 'CUST_NAME',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '客户级别',
							 name: 'CUST_LEV',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							  fieldLabel: '评级系统',
							   labelStyle: 'text-align:right;',
							 name: 'PJ_SYS',
							 anchor:'90%'
						 },{
							 xtype:'datefield',
							 fieldLabel: '评级日期',
							 name: 'PJ_DT',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '有效期',
							  labelStyle: 'text-align:right;',
							 name: 'VAL_DT',
							 anchor:'90%'
						 },{
							 xtype:'textarea',
							 fieldLabel: '级别描述',
							 name: 'CUST_LEV_DESC',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textarea',
							 fieldLabel: '评级理由',
							  labelStyle: 'text-align:right;',
							 name: 'PJ_CAUSE',
							 anchor:'90%'
						 }]
					 }
				]}
				]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : document.body.scrollHeight-200,
        width:document.body.scrollWidth-100,
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		// iconCls : 'page_addIcon',
		//maximizable: true,
		//maximized:true,
		collapsible : true,// 是否可收缩
		titleCollapse : true,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		pageY : 20,
		//pageX : document.body.clientWidth / 2 - 420 / 2,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [panel2],
        buttons : [{
            text : '关闭',
            handler:function(){
                addRoleWindow.hide();
            }
        }]
	});
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 100,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户级别',
											name : 'CUST_LEV',
											 labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}]
				}],
		buttons : [{
		    text : '查询',
            handler : function() {
                var conditionStr =  qForm.getForm().getValues(false);
                store.on('beforeload', function() {
                    this.baseParams = {
                            "condition":Ext.encode(conditionStr)
                    };
                    });
                store.reload({
                    params : {
                        start : 0,
                        limit : bbar.pageSize
                    }
                });
					}
				}, {
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
				{header : 'id', dataIndex : 'ID',sortable : true,width : 150,hidden :true},
				{header : '客户编号', dataIndex : 'CUST_ID',sortable : true,width : 150 }, 
				{header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 150 },
			    {header : '评级日期', dataIndex : 'PJ_DT',sortable : true,width : 150 }, 
				{header : '评级系统', dataIndex : 'PJ_SYS',sortable : true,width : 150 }, 
				{header : '有效期', dataIndex : 'VAL_DT',sortable : true,width : 150 }, 
				{header : '评级理由', dataIndex : 'PJ_CAUSE',sortable : true,width : 150 }, 
				{header : '客户级别', dataIndex : 'CUST_LEV',sortable : true,width : 150 }, 
				{header : '级别描述', dataIndex : 'CUST_LEV_DESC',sortable : true,width : 150 }, 
				{header : '平台日期', dataIndex : 'CRM_DT',sortable : true,width : 150 }
			]);

	/**
	 * 数据存储
	 */
		var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/queryratinginformation.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [{name: 'ID'},
						{name: 'CUST_ID'},
						{name: 'PJ_DT'},
						{name: 'PJ_SYS'},
						{name: 'VAL_DT'},
						{name: 'CUST_NAME'},
						{name: 'PJ_CAUSE'},
						{name: 'CUST_LEV'},
						{name: 'CUST_LEV_DESC'},
						{name: 'CRM_DT'}
					])
				});
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '查看',
					
					handler : function() {
						editInit();
			
					}
				}]
			});

    // 每页显示条数下拉选择框
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '100',
        editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
    pagesize_combo.on("select", function(comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
        store.reload({
            params : {
                start : 0,
                limit : bbar.pageSize
            }
        });
    });
    // 分页工具栏
    var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });

	// 表格实例
	var grid = new Ext.grid.GridPanel({
        height : document.body.scrollHeight-130,
        width:document.body.scrollWidth,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
    //拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        grid.setHeight(document.body.scrollHeight-130);
        grid.setWidth(document.body.scrollWidth);
        grid.getView().refresh();
    });


	// 布局模型
	var viewport = new Ext.Viewport({
	    layout:'fit',
        items:{
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户统一视图->客户评价信息", 
				    height: 128,
				    hidden:false,
				    margins: '0 0 0 0',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 
        }
			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	function editInit(){
	    var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            panel2.getForm().loadRecord(infoRecord);
            addRoleWindow.show();
        }
	}
	
	store.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });

}); 