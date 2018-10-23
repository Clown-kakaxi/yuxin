Ext.onReady(function() {
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">客户签约信息</span>',
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
							 fieldLabel: '*签约名称',
							 labelStyle: 'text-align:right;',
							 allowBlank : false,
							 maxLength:99,
							 name: 'SIGN_NAME',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '*签约机构',
							 name: 'SIGN_ORG',
							 allowBlank : false,
							 maxLength:99,
							 labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'datefield',
							  fieldLabel: '*签约日期',
							  format:'Y-m-d', //日期格式化
							  allowBlank : false,
							   labelStyle: 'text-align:right;',
							 name: 'SIGN_DATE',
							 anchor:'90%'
						 },{
							 xtype:'datefield',
							 fieldLabel: '*签约有效期',
							 name: 'SIGN_END_DATE',
							 allowBlank : false,
							 format:'Y-m-d', //日期格式化
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*主办机构名称',
							 allowBlank : false,
								maxLength:99,
							  labelStyle: 'text-align:right;',
							 name: 'OGR_NAME',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '*经办人',
							 allowBlank : false,
								maxLength:99,
							 name: 'ATTN',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*签约状态',
							 allowBlank : false,
							maxLength:99,
							 labelStyle: 'text-align:right;',
							 name: 'SIGN_STS',
							 anchor:'90%'
						 }/*,{
                             name: 'ID',
                             xtype:'textfield',
                             id:'id',
                             hidden:true
                         },{
                             name:'CUST_ID',
                             id:'custid',
                             xtype:'textfield',
                             hidden:true
                         }*/]
					 }
				]}
				]}]
		});
	var panel3 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">客户签约信息</span>',
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
							 fieldLabel: '*签约名称',
							  labelStyle: 'text-align:right;',
							  allowBlank : false,
								maxLength:20,
							 name: 'SIGN_NAME',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '*签约机构',
							 allowBlank : false,
								maxLength:20,
							 name: 'SIGN_ORG',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'datefield',
							  fieldLabel: '*签约日期',
							  allowBlank : false,
							  format:'Y-m-d', //日期格式化
							   labelStyle: 'text-align:right;',
							 name: 'SIGN_DATE',
							 anchor:'90%'
						 },{
							 xtype:'datefield',
							 fieldLabel: '*签约有效期',
							 name: 'SIGN_END_DATE',
							 allowBlank : false,
							 format:'Y-m-d', //日期格式化
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*主办机构名称',
							 allowBlank : false,
								maxLength:20,
							  labelStyle: 'text-align:right;',
							 name: 'OGR_NAME',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '*经办人',
							 allowBlank : false,
								maxLength:20,
							 name: 'ATTN',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 allowBlank : false,
								maxLength:20,
							 fieldLabel: '*签约状态',
							  labelStyle: 'text-align:right;',
							 name: 'SIGN_STS',
							 anchor:'90%'
						 }/*,{
                             name: 'ID',
                             xtype:'textfield',
                             id:'id',
                             hidden:true
                         },{
                             name:'CUST_ID',
                             id:'custid',
                             xtype:'textfield',
                             hidden:true
                         }*/]
					 }
				]}
				]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		layout : 'fit',
        height : document.body.scrollHeight-200,
        width:document.body.scrollWidth-100,
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
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		pageY : 20,
		//pageX : document.body.clientWidth / 2 - 420 / 2,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [panel2],
        buttons : [{ // 窗口底部按钮配置
            text : '提    交', // 按钮文本
            handler : function() { // 按钮响应函数
            	if(!panel2.getForm().isValid())
				{ 
					Ext.Msg.alert('提示','输入有误!');
					return false;
				}
            	var checkedNodes = grid.getSelectionModel().selections.items;
                Ext.Ajax.request({
                    url: basepath+'/customersigninfo.json',
                    method: 'POST',
                    params : {
        				'did':checkedNodes[0].data.ID,
        				'cid': checkedNodes[0].data.CUST_ID,
        				'operate':'update'
        			},
        			 form:panel2.getForm().id,
                    //params:params:panel2.getForm().getFieldValues(),        
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
                addRoleWindow.hide();
            }
        },
        { // 窗口底部按钮配置
            text : '重    置', // 按钮文本
            handler : function() { // 按钮响应函数
                panel2.getForm().reset();
            }
        },
        { // 窗口底部按钮配置
            text : '关    闭', // 按钮文本
            handler : function() { // 按钮响应函数
                addRoleWindow.hide();
            }
        }]
	});
	var addRoleWindow2 = new Ext.Window(
			{
				layout : 'fit',
		        height : document.body.scrollHeight-200,
		        width:document.body.scrollWidth-100,
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
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				pageY : 20,
				//pageX : document.body.clientWidth / 2 - 420 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [panel3],
		        buttons : [{ // 窗口底部按钮配置
		            text : '提    交', // 按钮文本
		            handler : function() { // 按钮响应函数
		            	if(!panel3.getForm().isValid())
						{ 
							Ext.Msg.alert('提示','输入有误!');
							return false;
						}
		            	var checkedNodes = grid.getSelectionModel().selections.items;
		                Ext.Ajax.request({
		                    url: basepath+'/customersigninfo.json',
		                    method: 'POST',
		                    form:panel3.getForm().id,
		                    params : {
		        				'cid': parent.location.href.split("customerId=")[1],
		                        'operate':'add'
		        			},
		        			
		                    //params:panel3.getForm().getFieldValues(),        
		                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		                    success : checkResult,
		                    failure : checkResult
		                });
		                addRoleWindow.hide();
		            }
		        },
		        { // 窗口底部按钮配置
		            text : '重    置', // 按钮文本
		            handler : function() { // 按钮响应函数
		                panel3.getForm().reset();
		            }
		        },
		        { // 窗口底部按钮配置
		            text : '关    闭', // 按钮文本
		            handler : function() { // 按钮响应函数
		                addRoleWindow2.hide();
		                panel3.getForm().reset();
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
											fieldLabel : '签约名称',
											name : 'SIGN_NAME',
											 labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							},{
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 80, // 标签宽度
                                defaultType : 'textfield',
                                border : false,
                                items : [{
                                            fieldLabel : '签约机构',
                                            name : 'SIGN_ORG',
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
				{header : '签约名称', dataIndex : 'SIGN_NAME',sortable : true,width : 150 }, 
				{header : '签约日期', dataIndex : 'SIGN_DATE',sortable : true,width : 150 },
			    {header : '签约机构', dataIndex : 'SIGN_ORG',sortable : true,width : 150 }, 
				{header : '签约有效期', dataIndex : 'SIGN_END_DATE',sortable : true,width : 150 }, 
				{header : '主办机构名称', dataIndex : 'OGR_NAME',sortable : true,width : 150 }, 
				{header : '经办人', dataIndex : 'ATTN',sortable : true,width : 150 }, 
				{header : '签约状态', dataIndex : 'SIGN_STS',sortable : true,width : 150 } 
			]);

	/**
	 * 数据存储
	 */
		var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/querysigninformation.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [{name: 'ID'},
			            {name: 'CUST_ID'},
						{name: 'SIGN_STS'},
						{name: 'ATTN'},
						{name: 'OGR_NAME'},
						{name: 'SIGN_END_DATE'},
						{name: 'SIGN_ORG'},
						{name: 'SIGN_DATE'},
						{name: 'SIGN_NAME'}
					])
				});
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
                    text : '新增',
                    handler : function() {
                        addInit();
                    }},'-',
                    {
                    text : '修改',
                    handler : function() {
                        editInit();
                    }},'-',
                    {
                    text : '删除',
                    handler : function() {
                        deleteInit();
                    }},'-',{
                        text:'附件信息',
                        handler:function()
                        {
                            var record = grid.getSelectionModel().getSelected(); 
                            if (!record) {
                                Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
                                return false;
                            }
                            var checkedNodes = grid.getSelectionModel().selections.items;
                            if(checkedNodes.length>1){
                                Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
                                return false;
                            }
                            var noticeIdStr = record.get('ID');
                            
                            uploadForm.relaId = noticeIdStr;
                            uploadForm.modinfo = 'customer';
                            var condi = {};
                            condi['relationInfo'] = noticeIdStr;
                            condi['relationMod'] = 'customer';
                            Ext.Ajax.request({
                                url:basepath+'/queryanna.json',
                                method : 'GET',
                                params : {
                                    "condition":Ext.encode(condi)
                                },
                                failure : function(a,b,c){
                                    Ext.MessageBox.alert('查询异常', '查询失败！');
                                },
                                success : function(response){
                                    var anaExeArray = Ext.util.JSON.decode(response.responseText);
                                    appendixStore.loadData(anaExeArray.json.data);
                                    appendixGridPanel.getView().refresh();
                                }
                            });
                            appendixWindow.show();
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
				    title: "客户统一视图->客户签约信息", 
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
	
	function addInit(){
        panel3.getForm().reset();
        //Ext.getCmp('id').setValue('');
        //Ext.getCmp('custid').setValue(parent.location.href.split("customerId=")[1]);
        addRoleWindow2.show();  
    }
    function deleteInit(response){
        
        /****************************************************************************************/
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } 
        
        else {
            if(confirm("确定删除吗?"))
            {
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = grid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/customersigninfo/'
                        +tempId+'.json?idStr='+idStr,
                method : 'DELETE',        
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : checkResult,
                failure : checkResult
            });
            
            };

    }
        /****************************************************************************************/
    }
	
	store.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });

	function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            addRoleWindow.hide();
            addRoleWindow2.hide();
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
    }
	
}); 