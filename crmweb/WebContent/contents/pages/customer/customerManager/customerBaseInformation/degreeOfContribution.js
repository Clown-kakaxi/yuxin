Ext.onReady(function() {
	
	
	var qForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 70,
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
											fieldLabel : '统计起始日期',
											name : 'StatStart',
											format:'Y-m-d', //日期格式化
											labelStyle: 'text-align:right;',
											xtype : 'datefield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '统计截止日期', // 标签
											 labelStyle: 'text-align:right;',
											 format:'Y-m-d', //日期格式化
											name : 'StatEnd', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											xtype : 'datefield',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}
							/*,
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '日期纬度', // 标签
											name : 'c', // name:后台根据此name属性取值
											 labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '指标分类', // 标签
											name : 'csdf', // name:后台根据此name属性取值
											 labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}
							*/
							]
				}],
		buttons : [{
					text : '查询',
						handler : function() {
							store.load();
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
	    {header : 'id',dataIndex : 'ID',sortable : true,hidden :true}, 
		{header : '客户id',dataIndex : 'CUST_ID',sortable : true,hidden :true}, 
        {header : '统计日期',dataIndex : 'CRM_DT',sortable : true,width : 150}, 
        {header : '存款考核利润',dataIndex : 'CRCV',sortable : true,width : 150},
        {header : '存款贡献度',dataIndex : 'CCON',sortable : true,width : 150 }, 
        {header : '贷款考核利润',dataIndex : 'DRCV',sortable : true,width : 150 }, 
        {header : '贷款贡献度',dataIndex : 'DCON',sortable : true,width : 150 },
        {header : '中间业务贡献度',dataIndex : 'MCON',sortable : true,width : 150 },
		{header : '综合贡献度（模拟利润）',dataIndex : 'MNCON',sortable : true,width : 150 },
		{header : '综合贡献度（经济资本调整后模拟利润）',dataIndex : 'TZCON',sortable : true,width : 150 },
		{header : '综合贡献度（模拟净利润）',dataIndex : 'MNJCON',sortable : true,width : 150 },
		{header : '经济资本占用',dataIndex : 'JJZB',sortable : true,width : 150 },
		{header : '资本费用率',dataIndex : 'ZBFL',sortable : true,width : 150 },
		{header : '客户关系费用',dataIndex : 'GXFY',sortable : true,width : 150 },
		{header : '产品相关间接费用',dataIndex : 'XGJJFY',sortable : true,width : 150 },
		{header : '风险损失拨备',dataIndex : 'CREDITLOSE',sortable : true,width : 150 }
	]);

	//数据源
  var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/querydegreeofcontribution.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			        root:'json.data'
			        }, [
						{name: 'CUST_ID'},
						{name: 'CRM_DT'},
						{name: 'CRCV'},
						{name: 'CCON'},
						{name: 'DRCV'},
						{name: 'DCON'},
						{name: 'MCON'},
						{name: 'MNCON'},
						{name: 'TZCON'},
						{name: 'MNJCON'},
						{name: 'JJZB'},
						{name: 'ZBFL'},
						{name: 'XGJJFY'},
						{name: 'CREDITLOSE'}
					])
				});
  store.on('beforeload', function() {
  	var conditionStr =  qForm.getForm().getValues(false);
      this.baseParams = {
              "condition":Ext.encode(conditionStr)
      };
});
  var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['value', 'text'],
					data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [100, '100条/页']]
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
				bbar.pageSize = parseInt(pagesize_combo.getValue());
				store.reload({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
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
				items : ['-', pagesize_combo]
			});
var panel2 = new Ext.FormPanel({ 
    frame:true,
    id:'panel2',
    bodyStyle:'padding:5px 5px 0',
    title : '<span style="font-weight:normal">成本费用设置</span>',
    width: '100%',
    height:100,
    reader: new Ext.data.JsonReader({
     root:'json.data'
     }, ['GXFY','XGJJFY','CREDITLOSE']),
    items: [{
        autoHeight:true,
        items :[{ layout:'column',
            buttonAlign : 'center',
                 items:[{
                     columnWidth:.33,
                     labelWidth : 80, // 标签宽度
                     layout: 'form',
                     items: [{
                         name : 'GXFY',
                         xtype: 'numberfield',
                         anchor : '90%',
                         //allowBlank : false,
  	                   fieldLabel: '客户关系费用',
  	                   labelStyle: 'text-align:right;'
                         }]
                 },{
                     columnWidth:.33,
                     labelWidth : 80, // 标签宽度
                     layout: 'form',
                     items: [{
                       name : 'XGJJFY',
                       xtype: 'numberfield',
                       anchor : '90%',
	                   fieldLabel: '产品相关间接费用',
	                   labelStyle: 'text-align:right;'
                       }]
                 },{
                     columnWidth:.33,
                     labelWidth : 80, // 标签宽度
                     layout: 'form',
                     items: [{
                           name : 'CREDITLOSE',
                           xtype: 'numberfield',
                          // maxValue:100,
                           anchor : '90%',
		                   fieldLabel: '风险损失拨备',
		                   labelStyle: 'text-align:right;'
                           }]
                 }
            ]} 
            ]}]
    });
var addRoleWindow = new Ext.Window(
	    {
	        //layout : 'fit',
	        width : 850,
	        height :150,
	        buttonAlign : 'center',
	        draggable : true,//是否可以拖动
	        closable : true,// 是否可关闭
	        modal : true,
	        closeAction : 'hide',
	        // iconCls : 'page_addIcon',
	        //maximizable: true,
	        //maximized:true,
	        collapsible : true,// 是否可收缩
	        titleCollapse : true,
	        border : false,
	        animCollapse : true,
	        pageY : 20,
	        //pageX : document.body.clientWidth / 2 - 420 / 2,
	        animateTarget : Ext.getBody(),
	        constrain : true,
	        items : [panel2
	                 ],
	        buttons : [
	                    {
	                        text : '保存',
	                        handler : function() {
	                            insert();
	                        }
	                    }, {
	                        text : '重置',
	                        handler : function() {
	                        	panel2.getForm().reset();
//	                        	.getEl().dom
	                        	
	                        }
	                    }, {
	                        text : '关闭',
	                        handler : function() {
	                            addRoleWindow.hide();
	                        }
	                    } ]
	    });
	
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '成本费用设置',
					handler : function() {
						addRoleWindow.show();
					
							panel2.getForm().load({
						         //waitMsg: '正在加载数据',
						 	        //waitTitle: '提示',
							 restful:true,	
						     url:basepath+'/querycostexpenseinstall.json?customerId='+parent.location.href.split("customerId=")[1],
						     method: 'GET'/*,
						     success : function() {
						    	 Ext.Msg.alert('提示',Ext.getCmp('CPTL_AMT').getValue);
						    	 
						    	 Ext.getCmp('CPTL_AMT').setValue(Ext.util.Format.number(Ext.getCmp('CPTL_AMT').getValue, "0,000.00"));
						    	 debugger;*/
								//var resultArray = Ext.util.JSON.decode(response.responseText);
									//Ext.Msg.alert('提示',response.responseText);
					    });
					
			
					}
				}/*,'-',{
					text : '修改',
					handler : function() {
						addRoleWindow.show();
			
					}
				},'-',{
					text : '删除',
					handler : function() {
						editInit();
			
					}
				}*/]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
		        height :document.body.scrollHeight-95,
		        width : document.body.scrollWidth-6,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


	// 布局模型
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:{
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户统一视图->客户贡献度", 
				    height: 90,
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
/*	function insert(){
		panel2.getForm().submit({												
			url: 'http://localhost:8080/bob_all/customer-event.json',
			method: 'POST'
		});

	};*/
    function insert(){
       /*if(Ext.getCmp('business_coeff').value==""&&Ext.getCmp('grade_coeff').value==""
    			){
    		Ext.Msg.alert('提示','请选则一个行业系数或评级系数!');
			return false;
    		
    	}*/
    	if(!panel2.getForm().isValid())
		{ 
			Ext.Msg.alert('提示','输入信息有误!');
			return false;
		}
    	//var checkedNodes = loanGrid.getSelectionModel().selections.items;
  
        Ext.Ajax.request({
            url: basepath+'/costexpenseinstall.json',
            method: 'POST',
            form:panel2.getForm().id,
    		success : function(response) {
				Ext.Msg.alert('提示', '操作成功');
			},
			failure : function(response) {
				Ext.Msg.alert('提示','操作失败' );
			},
          params : {
				'customerId': parent.location.href.split("customerId=")[1]
				//parent.location.href.split("customerId=")[1]
			}
      /*      waitMsg : '正在保存数据,请等待...' */// 显示读盘的动画效果，执行完成后效果消失
           // success : checkResult,
           // failure : checkResult
        });
        addRoleWindow.hide();
           
    };
/*	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});*/
	function editInit(){
		addRoleWindow.show();
	};
	store.load();
}); 