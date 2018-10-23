Ext.onReady(function() {
	
    var qForm = new Ext.form.FormPanel({
        //title : '<span style="font-weight:normal">查询条件<span>',
        //border : true,
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
                                            fieldLabel : '营销计划名称',
                                            name : 'PLAN_NAME',
                                             labelStyle: 'text-align:right;',
                                            xtype : 'textfield', // 设置为数字输入框类型
                                            anchor : '80%'
                                        }]
                            }, {
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 80, // 标签宽度
                                defaultType : 'textfield',
                                border : false,
                                items : [{
                                            fieldLabel : '营销计划状态', // 标签
                                             labelStyle: 'text-align:right;',
                                            name : 'MKT_PLAN_STAT', // name:后台根据此name属性取值
                                            allowBlank : true, // 是否允许为空
                                            //maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                                            anchor : '80%' // 宽度百分比
                                        }]
                            }, {
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 80, // 标签宽度
                                defaultType : 'datefield',
                                border : false,
                                items : [{
                                            fieldLabel : '计划开始时间', // 标签
                                            name : 'PLAN_START_DATE', // name:后台根据此name属性取值
                                             labelStyle: 'text-align:right;',
                                            allowBlank : true,
                                            anchor : '80%'// 宽度百分比
                                        }]
                            }, {
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 80, // 标签宽度
                                defaultType : 'datefield',
                                border : false,
                                items : [{
                                    fieldLabel : '计划结束时间',
                                    name : 'PLAN_END_DATE',
                                    labelStyle: 'text-align:right;',
                                    anchor : '80%'
                                }]
                            }]
                }],
        buttons : [{
                    text : '查询',
                    handler : function() {
                        var conditionStr =  qForm.getForm().getValues(false);
                        //var conditionTstr = Ext.encode(conditionStr);
//                      alert(conditionStr);
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
                        //queryBalanceInfo(qForm.getForm());
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
				{header : 'id', dataIndex : 'planId',sortable : true,width : 150,hidden :true},
				{header : '营销计划名称', dataIndex : 'planName',sortable : true,width : 150 }, 
				{header : '营销计划状态', dataIndex : 'marketPlanStatement',sortable : true,width : 150 },
			    {header : '计划制定机构号', dataIndex : 'customerId',sortable : true,width : 150 }, 
				{header : '计划制定机构名称', dataIndex : 'customerName',sortable : true,width : 150 }, 
				{header : '计划开始时间', dataIndex : 'planStartDate',sortable : true,width : 150 }, 
				{header : '计划结束时间', dataIndex : 'planEndDate',sortable : true,width : 150 }
			]);

	/**
	 * 数据存储
	 */
		var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/querymarketplan.json?customerId='+parent.location.href.split("customerId=")[1]
//			        ,
//			            success : function(response) {
//                          var resultArray = Ext.util.JSON.decode(response.responseText);
//                          Ext.Msg.alert('提示', response.responseText);
//                      }
			        }),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [{name: 'planId',mapping:'PLAN_ID'},
						{name: 'createDate',mapping:'CREATE_DATE'},
						{name: 'createUser',mapping:'CREATE_USER'},
						{name: 'marketPlanAim',mapping:'MKT_PLAN_AIM'},
						{name: 'marketPlanCharge',mapping:'MKT_PLAN_CHARGE'},
						{name: 'marketPlanContent',mapping:'MKT_PLAN_CONT'},
						{name: 'marketPlanStatement',mapping:'MKT_PLAN_STAT'},
						{name: 'planCustomerDescribe',mapping:'PLAN_CUST_DESC'},
						{name: 'planEndDate',mapping:'PLAN_END_DATE'},
						{name: 'actualEndDate',mapping:'ACTUAL_END_DATE'},
						{name: 'planName',mapping:'PLAN_NAME'},
                        {name: 'planProductDescribe',mapping:'PLAN_PROD_DESC'},
                        {name: 'planStartDate',mapping:'PLAN_START_DATE'},
                        {name: 'updateUser',mapping:'UPDATE_USER'},
                        {name: 'updateDate',mapping:'UPDATE_DATE'},
                        {name: 'customerId',mapping:'CUST_ID'},
                        {name: 'customerName',mapping:'CUST_NAME'}
					])
				});
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '营销计划',
					
					handler : function() {
						window.location.href='marketInformation.jsp';
			
					}
				},'-',{
                    text : '营销活动',
                    
                    handler : function() {
                        window.location.href='marketActivity.jsp';
            
                    }
                },'-',{
                    text : '商    机',
                    
                    handler : function() {
                        window.location.href='marketOpportunity.jsp';
            
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
				    title: "客户统一视图->营销信息->营销计划", 
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
//		editInit();
	});
	
	
	store.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });

}); 