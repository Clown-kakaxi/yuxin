Ext.onReady(function() {

    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
    var stockAmt = new Ext.form.NumberField({
//      xtype:'numberfield',
      minValue:0,allowNegative :false,
      allowDecimals:false,
//      id:'stockAmt01',
      fieldLabel: '当前股本总量',
      name: 'STOCK_AMT',
      maxLength:18,
       labelStyle: 'text-align:right;',
      anchor:'90%'
  });
    var ltStock = new Ext.form.NumberField({
//        xtype:'numberfield',
//        id:'ltStock01',
        minValue:0,allowNegative :false,
        allowDecimals:false,
        fieldLabel: '流通股',
        name: 'LT_STOCK',
        maxLength:18,
         labelStyle: 'text-align:right;',
        anchor:'90%'
    });
	var issueInfoForm = new Ext.form.FormPanel({
	    frame:true,
//        formId:'issueInfoForm',
        bodyStyle:'padding:5px 5px 0',
        title : '<span style="font-weight:normal">股票发行信息</span>',
        width: document.body.clientWidht,
        height:document.body.clientHeight-310,
        items: [{
            autoHeight:true,
            items :[{ layout:'column',
                buttonAlign : 'center',
                     items:[{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             xtype:'textfield',
                             name:'MXTID',
                             id:'id01',
                             hidden:true
                           },{
                             xtype:'textfield',
                             fieldLabel: '*股票类型',
                             maxLength:20,
                             allowBlank:false,
                              labelStyle: 'text-align:right;',
                             name: 'STOCK_TYP',
                             anchor:'90%'
                         }, {
                             xtype:'textfield',
                             fieldLabel: '*股票名称',
                             name: 'STOCK_NAME',
                             allowBlank:false,
                             maxLength:100,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         },{
                             xtype:'numberfield',
                             minValue:0,allowNegative :false,
                            fieldLabel:'每股净现金流量',
                            maxLength:18,
                             labelStyle: 'text-align:right;',
                            name:'STOCKFLOW',
                            anchor:'90%'
                         
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             xtype:'textfield',
                             vtype:'alphanum',
                             allowBlank:false,
                             fieldLabel: '*股票代码',
                             maxLength:20,
                              labelStyle: 'text-align:right;',
                            name: 'STOCK_COD',
                            anchor:'90%'
                        },{
                            xtype:'numberfield',
                            minValue:0,allowNegative :false,
                            allowDecimals:false,
                            fieldLabel: '当年增发配股额',
                            name: 'STOCK_ADD',
                            maxLength:18,
                             labelStyle: 'text-align:right;',
                            anchor:'90%'
                        },{
                            xtype:'numberfield',
                            minValue:0,allowNegative :false,
                            fieldLabel: '每股净资产',
                            maxLength:18,
                            name: 'STOCKASSETS',
                             labelStyle: 'text-align:right;',
                            anchor:'90%'
                        }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             xtype:'datefield',
                             fieldLabel: '上市日期',
                             format:'Y-m-d', //日期格式化
                              labelStyle: 'text-align:right;',
                             name: 'IPO_DT',
                             editable:false,
                             anchor:'90%'
                         },stockAmt ,{
                             xtype:'textfield',
                             fieldLabel: '上市地',
                             maxLength:100,
                              labelStyle: 'text-align:right;',
                             name: 'IPO_INSTN',
                             anchor:'90%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [ltStock ,{
                             xtype:'textarea',
                             fieldLabel: '备注',
                             name: 'RMAK',
                             maxLength:2000,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         },{
                             name:'CUST_ID',
                             id:'custid01',xtype:'textfield',
                             hidden:true
                         }]
                     }
                ]}
                ]}]
        
	});
	var issueInfoWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : document.body.clientHeight-240,
        width:document.body.clientWidth-100,
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
		items : [issueInfoForm],
		buttons :[{ // 窗口底部按钮配置
                    text : '提    交', // 按钮文本
                    handler : function() { // 按钮响应函数
                        if(!issueInfoForm.getForm().isValid())
                        { 
                            alert('请填写正确信息');
                            return false;
                        }
                        if(editFlag == 1){
                            var infoRecord = grid.getSelectionModel().getSelected();
                            id=infoRecord.data.MXTID;
                            custid=infoRecord.data.CUST_ID;
                            Ext.getCmp('id01').setValue(id);
                            Ext.getCmp('custid01').setValue(custid);
                        }else{
                        	custid=oCustInfo.cust_id;
                            Ext.getCmp('custid01').setValue(custid);
                        }
                        Ext.Ajax.request({
                            url: basepath+'/customeripoinfo.json',
                            method: 'POST',
                            params:issueInfoForm.getForm().getFieldValues(),
                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                            success : checkResult,
                            failure : checkResult
                        });
                        issueInfoWindow.hide();
                    }
                },
                { // 窗口底部按钮配置
                    text : '重    置', // 按钮文本
                    handler : function() { // 按钮响应函数
                        resetForm(issueInfoForm);
                    }
                },
                { // 窗口底部按钮配置
                    text : '关闭', // 按钮文本
                    handler : function() { // 按钮响应函数
                        issueInfoWindow.hide();
                    }
                }]
	});
	var qForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		renderTo:'viewport_center',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		width:document.body.scrollWidth-228,
		height : 90,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 75, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '股票类型',
											name : 'STOCK_TYP',
				                             maxLength:20,
											 labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 75, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '股票代码', // 标签
											 labelStyle: 'text-align:right;',
											vtype:'alphanum',
											 name : 'STOCK_COD', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
											anchor : '90%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 75, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '股票名称', // 标签
											name : 'STOCK_NAME', // name:后台根据此name属性取值
											 labelStyle: 'text-align:right;',
											allowBlank : true,
				                             maxLength:100,
											anchor : '90%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 75, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '上市地',
									name : 'IPO_INSTN',
		                             maxLength:100,
									xtype : 'textfield', // 设置为数字输入框类型
								    labelStyle: 'text-align:right;',
									anchor : '90%'
								}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
					    var conditionStr =  qForm.getForm().getFieldValues();
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
	           {header : '股票类型',dataIndex : 'STOCK_TYP',sortable : true,width : 150 }, 
		       {header : '股票代码',dataIndex : 'STOCK_COD',sortable : true,width : 150}, 
		       {header : '上市日期',dataIndex : 'IPO_DT',sortable : true}, 
		       {header : '上市地',dataIndex : 'IPO_INSTN',sortable : true}, 
		       {header : '股票名称',dataIndex : 'STOCK_NAME',sortable : true},
		       {header : '当年增发配股额',dataIndex : 'STOCK_ADD', align : 'right',sortable : true, renderer: money('0,000.00' )}, 
		       {header : '当前股本总量',dataIndex : 'STOCK_AMT', align : 'right',sortable : true ,renderer: money('000' )}, 
		       {header : '流通股',dataIndex : 'LT_STOCK',sortable : true,  align : 'right',renderer: money('0,000' )},
		       {header : '每股净现金流量',dataIndex : 'STOCKFLOW',sortable : true,
	                align : 'right',
	                renderer: money('0,000.00' )}, 
		       {header : '每股净资产',dataIndex : 'STOCKASSETS',sortable : true,
	                    align : 'right',
	                    renderer: money('0,000' )}, 
		       {header : '备注',dataIndex : 'RMAK',sortable : true}
			]);

	/**
	 * 数据存储
	 */
	
	var tempCustId1 = oCustInfo.cust_id;
	   var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/issueinformation.json?customerId='+tempCustId1}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [
						{name: 'ID'},
						{name: 'CUST_ID'},
						{name: 'STOCK_TYP'},
						{name: 'STOCK_COD'},
						{name: 'IPO_DT'},
						{name: 'IPO_INSTN'},
						{name: 'STOCK_NAME'},
						{name: 'STOCK_ADD'},
						{name: 'STOCK_AMT'},
						{name: 'LT_STOCK'},
						{name: 'STOCKFLOW'},
						{name: 'STOCKASSETS'},
						{name: 'RMAK'},
                        {name: 'MXTID'}
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
                    }}
                ]
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
        width:document.body.scrollWidth-228,
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
//	// 布局模型
//	var viewport = new Ext.Viewport({
//	    layout:'fit',
//        items:{
//
//				layout : 'border',
//				items: [{   
//					region: 'north',
////				    id: 'north-panel',
//				    title: "客户统一视图->股票发行信息", 
//				    height: 128,
//				    hidden:false,
//				    margins: '0 0 0 0',
//					items:[qForm]
//			     },{   
//			    	region:'center',
////				    id: 'center-panel',
//				    margins: '0 0 0 0',
//				    items : [grid]
//			    }] 
//        }
//			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	function editInit(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
        editFlag = 1;
        var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            issueInfoForm.getForm().loadRecord(infoRecord);
            issueInfoWindow.show();
        }}
    }
    function addInit(){
        editFlag = 0;
        resetForm(issueInfoForm);
        Ext.getCmp('id01').setValue('');
        Ext.getCmp('custid01').setValue(parent.location.href.split("customerId=")[1]);
      issueInfoWindow.show();  
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
                url : basepath+'/customeripoinfo/'
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
    
//    var cb3 = Ext.getCmp('stockAmt01');
    stockAmt.addListener("change",function(){
          var  maxValue=stockAmt.getValue();
          ltStock.setMaxValue(maxValue);
//          
    });

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