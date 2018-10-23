Ext.onReady(function() {

    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
    var currDate = new Date();
	var panel2 = new Ext.FormPanel({ 
		formId:'panel2012',
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">客户他行信息</span>',
		width: '100%',
	    height:300,
		items: [{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.5,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '序号',
							  labelStyle: 'text-align:right;',
							 name: 'ID',
							 hidden:true,
							 anchor:'90%'
						 }, {
                             xtype:'textfield',
                             fieldLabel: '*银行名称',
                             name: 'INSTN_NAME',
                             allowBlank:false,
                             maxLength:100,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         }, {
                             xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
                             fieldLabel: '合作年限',
                             name: 'HZYEARS',
                             maxLength:6,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         }, {
                             fieldLabel: '基本户开户行',
                             name: 'IS_BASIC_BANK',
                             //editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['是','是'],['否','否']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '90%'
                         },{
                             xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
                             maxLength:6,
                          fieldLabel: '授信期限',
                          name: 'CRED_LIMIT',
                           labelStyle: 'text-align:right;',
                          anchor:'90%'
                      }, {
                          xtype:'textfield',
                          fieldLabel: '产品使用情况',
                          maxLength:100,
                          name: 'PRD_USE',
                           labelStyle: 'text-align:right;',
                          anchor:'90%'
                      },{
                          xtype:'textfield',
                          fieldLabel: '维护人员',
                          disabled:true,
                          readOnly:true,
                          name: 'USERID',
                           labelStyle: 'text-align:right;',
                          anchor:'90%'
                      }]
					 },{
						 columnWidth:.5,
						 layout: 'form',
						 labelWidth:150,
						 items: [{
			                    fieldLabel:'活期存款时点余额(万元)',
                                labelStyle: 'text-align:right;',
			                    name:'CURRENT_VAL' ,
			                        xtype: 'numberfield',
	                                maxLength:18,
	                                minValue:0,allowNegative :false,
	                                anchor:'100%'
			                },{
                                fieldLabel:'定期存款时点余额(万元)',
                                labelStyle: 'text-align:right;',
                                name:'PERIODCIAL_VAL', 
                                    xtype: 'numberfield',
                                    maxLength:18,
                                    minValue:0,allowNegative :false,
                                    anchor:'100%'
                            },{
                                fieldLabel:'保证金存款时点余额(万元)',
                                labelStyle: 'text-align:right;',
                                name:'BAIL_VAL',
                                    xtype: 'numberfield',
                                    maxLength:18,
                                    minValue:0,allowNegative :false,
                                    anchor:'100%'
                            },{
                                fieldLabel:'贷款时点余额(万元)',
                                labelStyle: 'text-align:right;',
                                name:'LON_VAL',
                                    xtype: 'numberfield',
                                    maxLength:18,
                                    minValue:0,allowNegative :false,
                                    anchor:'100%'
                            },{
                                fieldLabel:'授信总额(万元)',
                                name:'CRED_AMT',
                                labelStyle: 'text-align:right;',
                                xtype: 'numberfield',
                                maxLength:18,
                                minValue:0,allowNegative :false,
                                anchor:'100%'
                            },{
	                             xtype:'datefield',
	                             fieldLabel: '维护日期',
	                             readOnly:true,
	                             disabled:true,
	                             format:'Y-m-d', //日期格式化
	                             name: 'UPDT_DT',
	                             value:currDate,
//	                             readOnly:true,
	                              labelStyle: 'text-align:right;',
	                             anchor:'100%'
	                         }]
					 }/*{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
						     xtype:'numberfield',
					            minValue:0,allowNegative :false,
					            allowDecimals:false,
                             fieldLabel: '授信期限',
                             name: 'CRED_LIMIT',
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         }, {
                             xtype:'textfield',
                             fieldLabel: '产品使用情况',
                             name: 'PRD_USE',
                              labelStyle: 'text-align:right;',
                             value :'总经理',
                             anchor:'90%'
                         }]
					 },*/
//					 {
//						 columnWidth:.3,
//						 layout: 'form',
//						 items: [{
//                             xtype:'textfield',
//                             fieldLabel: '维护人员',
//                             disabled:true,
//                             readOnly:true,
//                             name: 'USERID',
//                              labelStyle: 'text-align:right;',
//                             anchor:'90%'
//                         }, {
//                             xtype:'datefield',
//                             fieldLabel: '维护日期',
//                             readOnly:true,
//                             disabled:true,
//                             format:'Y-m-d', //日期格式化
//                             name: 'UPDT_DT',
//                             value:currDate,
////                             readOnly:true,
//                              labelStyle: 'text-align:right;',
//                             anchor:'90%'
//                         },{
//                             xtype:'textarea',
//                             maxLength:2000,
//                             fieldLabel: '备注',
//                              labelStyle: 'text-align:right;',
//                             name: 'RMAK',
//                             anchor:'90%'
//                         }, {
//                             name: 'MXTID',
//                             xtype:'textfield',
//                             id:'id',
//                             hidden:true
//                         },{
//                             name:'CUST_ID',
//                             id:'custid',
//                             xtype:'textfield',
//                             hidden:true
//                         }]
//					 }
				]
				}
				]},
				{
				    autoHeight:true,
					items :[{ layout:'column',
						buttonAlign : 'center',
							 items:[
							 {
								 columnWidth:1,
								 layout: 'form',
								 items: [{
		                             xtype:'textarea',
		                             maxLength:2000,
		                             fieldLabel: '备注',
		                              labelStyle: 'text-align:right;',
		                             name: 'RMAK',
		                             anchor:'90%'
		                         }, {
		                             name: 'MXTID',
		                             xtype:'textfield',
		                             id:'id',
		                             hidden:true
		                         },{
		                             name:'CUST_ID',
		                             id:'custid',
		                             xtype:'textfield',
		                             hidden:true
		                         }]
							 }
						]
						}
						]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : 370,
        width:800,
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
                    alert('请填写正确信息');
                    return false;
                }
                if(editFlag == 1){
                    var infoRecord = grid.getSelectionModel().getSelected();
                    id=infoRecord.data.MXTID;
                    custid=infoRecord.data.CUST_ID;
                    Ext.getCmp('id').setValue(id);
                    Ext.getCmp('custid').setValue(custid);
                }
                Ext.Ajax.request({
                    url: basepath+'/customerotherbank.json',
                    method: 'POST',
//                    params:panel2.getForm().getFieldValues(),        
                    form:'panel2012',
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
                addRoleWindow.hide();
                store.reload();
            }
        },'-',
        { // 窗口底部按钮配置
            text : '重    置', // 按钮文本
            handler : function() { // 按钮响应函数
                resetForm(panel2);
            }
        },'-',
        { // 窗口底部按钮配置
            text : '关    闭', // 按钮文本
            handler : function() { // 按钮响应函数
                addRoleWindow.hide();
            }
        }]
	});
	var qForm = new Ext.form.FormPanel({
		renderTo:'viewport_center',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 100,
		width:document.body.clientWidth-210,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .30,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '他行名称',
											name : 'INSTN_NAME',
											maxLength:100,
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}]
				}],
		buttons : [{
					text : '查询',
                    handler : function() {
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
			   {header : '客户编号', dataIndex : 'CUST_ID',sortable : true,width : 150,hidden :true }, 
	           {header : '银行名称',dataIndex : 'INSTN_NAME',sortable : true,width : 150},
               {header : '合作年限',dataIndex : 'HZYEARS',sortable : true,width : 150},
               {header : '基本开户行',dataIndex : 'IS_BASIC_BANK',sortable : true,width : 150},
		       {header : '活期存款时点余额',dataIndex : 'CURRENT_VAL',
                   align : 'right',
                   renderer: money('0,000.00' ),width : 150},
		       {header : '定期存款时点余额',dataIndex : 'PERIODCIAL_VAL',
                   align : 'right',
                   renderer: money('0,000.00' ),width : 150},
		       {header : '保证金存款时点余额(万元)',dataIndex : 'BAIL_VAL',
                   align : 'right',
                   renderer: money('0,000.00' ),width : 150},
		       {header : '贷款时点余额(万元)',dataIndex : 'LON_VAL',
                   align : 'right',
                   renderer: money('0,000.00' ),width : 150},
	           {header : '授信总额',dataIndex : 'CRED_AMT',
                   align : 'right',
                   renderer: money('0,000.00' ),width : 150}, 
		       {header : '授信期限',dataIndex : 'CRED_LIMIT',width : 150},
		       {header : '产品使用情况',dataIndex : 'PRD_USE',width : 150},
			   {header : '备注',dataIndex : 'RMAK',width : 150},
			   {header : '维护人员',dataIndex : 'USERNAME',width : 150},
			   {header : '维护日期',dataIndex : 'UPDT_DT',width : 150}
			]);

	/**
	 * 数据存储
	 */
    var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/otherbank.json?customerId='+oCustInfo.cust_id}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [{name: 'MXTID'},
			            {name: 'ID'},
						{name: 'CUST_ID'},
						{name: 'INSTN_NAME'},
						{name: 'HZYEARS'},
						{name: 'CURRENT_VAL'},
						{name: 'PERIODCIAL_VAL'},
						{name: 'BAIL_VAL'},
						{name: 'LON_VAL'},
						{name: 'CRED_AMT'},
						{name: 'CRED_LIMIT'},
                        {name: 'PRD_USE'},
						{name: 'RMAK'},
						{name: 'UPDT_DT'},
                        {name: 'USERID'},
                        {name: 'IS_BASIC_BANK'},
						{name: 'USERNAME'}
					])
				});
	
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
                    text : '新增',
                    iconCls :'addIconCss',
                    handler : function() {
                        editFlag = 0;
                        addInit();
                    }},'-',
                    {
                    text : '修改',
                    iconCls:'editIconCss',
                    handler : function() {
                        editFlag = 1;
                        editInit();
                    }},'-',
                    {
                    text : '删除',
                    iconCls : 'deleteIconCss',
                    handler : function() {
                        deleteInit();
                    }}]
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
		renderTo:'viewport_center',
        height : document.body.scrollHeight-130,
        width:document.body.clientWidth-220,
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
//	// 布局模型
//	var viewport = new Ext.Viewport({
//	    layout:'fit',
//        items:{
//
//				layout : 'border',
//				items: [{   
//					region: 'north',
//				    id: 'north-panel',
//				    title: "客户统一视图->客户他行信息", 
//				    height: 128,
//				    hidden:false,
//				    margins: '0 0 0 0',
//					items:[qForm]
//			     },{   
//			    	region:'center',
//				    id: 'center-panel',
//				    margins: '0 0 0 0',
//				    items : [grid]
//			    }] 
//        }
//			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
        editFlag = 1;
		editInit();
	});
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
    function addInit(){
        resetForm(panel2);
        Ext.getCmp('id').setValue('');
        Ext.getCmp('custid').setValue(oCustInfo.cust_id);
        addRoleWindow.show();  
    }
    function deleteInit(){
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
                tempId = selectRe.data.MXTID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/customerotherbank/'
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
    
    function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
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
    
    store.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });

    /**********************************************************/
    function resetForm(form){
        var resetObj;
        if(typeof form == 'string'){
            resetObj = Ext.getCmp(form);
        }else resetObj = form;
        
        if(resetObj == undefined){
            alert('debug:the formPanel has not been defined!');
            return false;
        }
        
        
        
        if(resetObj.getXType() != 'form'){
            alert('debug:the Obj is not a FormPanel!');
            return false;
        }
        
        Ext.each(resetObj.getForm().items.items,function(f){
            f.setValue('');
           // f.originalValue = '';
        });
        
        
    }
    /**********************************************************/
}); 