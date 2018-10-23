Ext.onReady(function() {
    Ext.QuickTips.init(); 
    var stsStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=GROUP_STS'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    var stypStore = new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=MARKET_EVENT_TYP'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
	var qForm = new Ext.form.FormPanel({
	    id:'qform',
	    region:'north',
		//title : '<span style="font-weight:normal">查询条件<span>',
		border : true,
		labelWidth : 90, // 标签宽度
//        autoScroll : true,
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 80,
		width:document.body.scrollWidth-20,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 110, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [/*{
								    fieldLabel:'*机构号',
								    name:'INSTN_ID',
		                            labelStyle: 'text-align:right;',
                                    allowBlank:false,
				                    anchor : '100%'
								},*/
						         {
					                    xtype:'textfield',
					                    name:'checkedNodes',
					                    hidden:true 
				                 }]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
								    xtype:'datefield',
								    //editable:false,
		                            format:'Y-m-d', 
								    fieldLabel:'*生效起始日期',
                                    name:'START_DT',
                                    id:'startdt',
                                    labelStyle: 'text-align:right;',
                                    allowBlank:false,
                                    anchor : '100%'
								}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    xtype:'datefield',
                                    //editable:false,
                                    format:'Y-m-d', 
                                    fieldLabel:'*生效截止日期',
                                    name:'END_DT',
                                    labelStyle: 'text-align:right;',
                                    allowBlank:false,
                                    anchor : '100%'
                                }]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : []
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
					    var startData = qForm.getForm().findField('START_DT').getValue();
		                var endData = qForm.getForm().findField('END_DT').getValue();
		                
		                if(startData!=undefined&&startData!=""&&endData!=undefined&&endData!=""){
		                    if(startData>endData){
		                        Ext.MessageBox.alert('条件异常', '开始时间应该小于等于结束时间！');
		                        return false;
		                    }
		                }
					    
					    if(!qForm.getForm().isValid())
					    { 
					        alert('输入有误');
					        return false;
					    }
					    var conditionStr =  qForm.getForm().getFieldValues();
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
    /*****************************************************************************************/
	var panel2 = new Ext.FormPanel({ 
        frame:true,
        formId:'panel2',
//        bodyStyle:'padding:5px 5px 0',
        title : '<span style="font-weight:normal">修改事件式营销规则</span>',
        width: '100%',
        height:330,
        items: [{
            autoHeight:true,
            items :[{ layout:'column',
                buttonAlign : 'center',
                     items:[{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             fieldLabel: '规则类型',
//                             disabled:true,
                             readOnly:true,
                             hiddenName:'a1',
                             store: stypStore,
                             xtype : 'combo',
                             ////editable:false,
                             allowBlank : false,
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             resizable:true,
                             forceSelection: true,
                             triggerAction: 'all',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '85%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             fieldLabel : '状态',
                             hiddenName:'a2',
                             store: stsStore,
                             xtype : 'combo',
                             ////editable:false,
                             allowBlank : false,
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             resizable:true,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '85%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             name:'RULE_ID',
                             xtype:'textfield',
                             hidden:true
                         },{
                             xtype:'numberfield',
                             fieldLabel: '提醒提前天数',
                             maxValue:90,
                             minValue:15,
                             allowDecimal:false,
                             allowBlank:false,
                              labelStyle: 'text-align:right;',
                             name: 'a3',
                             anchor:'85%'
                         }]
                     },{
                         columnWidth:.25,
                         labelWidth:110,
                         layout: 'form',
                         items: [{
                             xtype:'numberfield',
                             fieldLabel: '阀值',
                             minValue:1,
                             maxValue:10000000,
                             allowDecimal:false,
                             allowBlank:false,
                             labelStyle: 'text-align:right;',
                             name: 'a4',
                             anchor:'85%'
                         }]
                     }
                ]}
                ]}]
        });
    var addRoleWindow = new Ext.Window(
    {
//        layout : 'fit',
        height : 400,
        width:1000,
        buttonAlign : 'center',
        draggable : true,//是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        autoScroll:true,
        closeAction : 'hide',
        // iconCls : 'page_addIcon',
        //maximizable: true,
        //maximized:true,
        collapsible : true,// 是否可收缩
        titleCollapse : true,
        border : false,
        animCollapse : true,
//        pageY : 20,
        //pageX : document.body.clientWidth / 2 - 420 / 2,
        animateTarget : Ext.getBody(),
        constrain : true,
        items : [panel2],
        buttons : [
                    {
                        text : '预    览',
                        handler : function() {
                    	Ext.Msg.alert('计算结果','');
                        }
                    },{
                        text : '保    存',
                        handler : function() {
//                            insert();
                    	alert('保存成功');
                    	 addRoleWindow.hide();
                        }
                    },
                    {
                        text : '关    闭',
                        handler : function() {
                            addRoleWindow.hide();
                        }
                    } ]
    });
    /*****************************************************************************************/
	var panel1 = new Ext.FormPanel({ 
        frame:true,
        formId:'panel1',
//        bodyStyle:'padding:5px 5px 0',
        title : '<span style="font-weight:normal">新增事件式营销规则</span>',
        width: '100%',
        height:330,
        items: [{
            autoHeight:true,
            items :[{ layout:'column',
                buttonAlign : 'center',
                     items:[{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             fieldLabel: '规则类型',
                             hiddenName:'a1',
                             store: stypStore,
                             xtype : 'combo',
                             ////editable:false,
                             allowBlank : false,
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             resizable:true,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '85%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             fieldLabel : '状态',
                             hiddenName:'a2',
                             store: stsStore,
                             xtype : 'combo',
                             ////editable:false,
                             allowBlank : false,
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             resizable:true,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '85%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             name:'RULE_ID',
                             xtype:'textfield',
                             hidden:true
                         },{
                             xtype:'numberfield',
                             fieldLabel: '提醒提前天数',
                             maxValue:90,
                             minValue:15,
                             allowDecimal:false,
                             allowBlank:false,
                              labelStyle: 'text-align:right;',
                             name: 'a3',
                             anchor:'85%'
                         }]
                     },{
                         columnWidth:.25,
                         labelWidth:110,
                         layout: 'form',
                         items: [{
                             xtype:'numberfield',
                             fieldLabel: '阀值',
                             minValue:1,
                             maxValue:10000000,
                             allowDecimal:false,
                             allowBlank:false,
                              labelStyle: 'text-align:right;',
                             name: 'a4',
                             anchor:'85%'
                         }]
                     }
                ]}
                ]}]
        });
    var addRoleWindow1 = new Ext.Window(
    	    {
//    	        layout : 'fit',
    	        height : 400,
    	        width:1000,
    	        buttonAlign : 'center',
    	        draggable : true,//是否可以拖动
    	        closable : true,// 是否可关闭
    	        modal : true,
    	        autoScroll:true,
    	        closeAction : 'hide',
    	        // iconCls : 'page_addIcon',
    	        //maximizable: true,
    	        //maximized:true,
    	        collapsible : true,// 是否可收缩
    	        titleCollapse : true,
    	        border : false,
    	        animCollapse : true,
//    	        pageY : 20,
    	        //pageX : document.body.clientWidth / 2 - 420 / 2,
    	        animateTarget : Ext.getBody(),
    	        constrain : true,
    	        items : [panel1],
    	        buttons : [
    	                    {
    	                        text : '保    存',
    	                        handler : function() {
//    	                            insert();
    	                    	alert('保存成功');
    	                    	addRoleWindow1.hide();
    	                        }
    	                    },
    	                    {
    	                        text : '关    闭',
    	                        handler : function() {
    	                            addRoleWindow1.hide();
    	                        }
    	                    } ]
    	    });
    /*****************************************************************************************/
    /*****************************************************************************************/
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, sm,
	                                   {
                header : '规则类型',
                dataIndex : 'a1',
                sortable : true,
                width : 200,
                align:'centre'
            }, {
                header : '状态', // 列标题
                dataIndex : 'a2', // 数据索引:和Store模型对应
                width : 200,
                align:'centre'
             
            }, {
                header : '提醒提前天数',
                dataIndex : 'a3',
                sortable : true,
                width : 200,
                align:'right'
            }, {
                header : '变动金额1/账户余额大于等于(万元)',
                sortable : true,
                dataIndex : 'a4',
                width : 200,
                align:'right',
                renderer: money('0,000.00' )
            }
			]);

	/**
	 * 数据存储
	 */
	
	var myData = [
	              ['客户账户异动','启用', '120','50']
	               ,['客户高管生日','启用', '32','4']
	               ,['客户流失','启用', '5','45']
	               ,['客户级别变动','启用', '12','1200']
	               ,['客户定期产品到期','启用', '4','123']
	               ,['客户理财产品异动','启用', '23','22']
	               ];
					var store = new Ext.data.ArrayStore({
			               fields: [
			                  {name: 'a1'},
			                  {name: 'a2'},
			                  {name: 'a3'},
			                  {name: 'a4'}
			               ]
			           });
					store.loadData(myData);
	
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
				    text:'新增规则',
				    iconCls:'addIconCss',
				    handler:function(){
				        addInit();
				    }
				},'-',{
				    text:'修改规则',
				    iconCls:'deleteIconCss',
				    handler:function(){
						addInit();
				    }
				},'-',{
				    text:'删除规则',
				    iconCls:'detailIconCss',
				    handler:function(){
					var infoRecord = grid.getSelectionModel().getSelected();
			        if(infoRecord ==null||infoRecord==''){
			            Ext.Msg.alert('提示','请选择一行数据');
			            return false;
			        }
					Ext.MessageBox.confirm('提示','确定要删除吗？',function(buttonId){
						if(buttonId.toLowerCase() == "no"){
								return;
							}else{
								Ext.Msg.alert('系统提示','删除成功!');
							}
				    })}
				}]
			});
	// 每页显示条数下拉选择框
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
        //editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
//    pagesize_combo.on("select", function(comboBox) {
//        bbar.pageSize = parseInt(comboBox.getValue());
//        number = parseInt(comboBox.getValue());
//        store.reload({
//            params : {
//                start : 0,
//                limit : bbar.pageSize
//            }
//        });
//    });
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
//        height : document.body.scrollHeight-160,
//        width:document.body.scrollWidth,
		title: '事件式营销规则设置',
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
        grid.setHeight(document.body.scrollHeight-160);
        grid.setWidth(document.body.scrollWidth);
        grid.getView().refresh();
    });
    var mainPanel = new Ext.Viewport({
    	layout:'fit',
    	frame:true,
    	items:[{ 
    		layout:'border',
            items:[grid]
        }]
    });
    /**********************************************************/
    grid.on('rowdblclick', function(grid, rowIndex, event) {
        editInit();
    });
    
    function addInit(){  
//            panel1.getForm().reset();
//            addRoleWindow1.show();
    	editDictWindow.show();
       
    }
    
    function editInit(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
        var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            panel2.getForm().loadRecord(infoRecord);
            addRoleWindow.show();
        }}
    }
   
    function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            if(resultArray == 403) {
                Ext.Msg.alert('提示', response.responseText);
        }else{
            
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
        }
    }
    function checkResult1(response,b) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
        debugger;
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '数据初始化成功');
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            if(resultArray == 403) {
                Ext.Msg.alert('提示', response.responseText);
        }else{
            
            Ext.Msg.alert('提示', '数据初始化失败,失败原因:' + resultError);
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
        }
    }
}); 