Ext.onReady(function() {
    Ext.QuickTips.init(); 
	
    /***********************************数据字典store*****************************/
    var bzStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=CCY'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    
    var zqlxStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=BOND_TYP'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

    /**********************************判断是否为编辑状态的flag*****************************************/
	var editFlag = 0;
	/**********************************************************************************************/
	
	/*************************新增债券发行信息*****************************/
	var bondIssueForm = new Ext.form.FormPanel({
	    frame:true,
//        formId:'bondIssueForm',
        bodyStyle:'padding:5px 5px 0',
        title : '<span style="font-weight:normal">债券信息</span>',
        width: '100%',
        
        height:document.body.clientHeight-320,
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
                             id:'id02',
                             hidden:true
                           },
                               {
                                   fieldLabel :'*债券名称',
                                   xtype:'textfield',
                                   maxLength:80,
                                   allowBlank:false,
                                   labelStyle: 'text-align:right;',
                                   name:'ISSUE_NAME',
                                   anchor : '90%'
                               },
                               {
                                   fieldLabel :'*债券代码',
                                   vtype:'alphanum',
                                   xtype:'textfield',
                                   maxLength:30,
                                   allowBlank:false,
                                   labelStyle: 'text-align:right;',
                                   name:'ISSUE_COD',
                                   anchor : '90%'
                               },
                               {
                                   fieldLabel :'*债券类型',
                                   name:'BND_TYP',
//                                   allowBlank:false,
                                   store: zqlxStore,
                                   xtype : 'combo',
                                   labelStyle: 'text-align:right;',
                                   valueField:'key',
                                   displayField:'value',
                                   mode : 'local',
                                   typeAhead: true,
                                   //editable:false,
                                   forceSelection: true,
                                   triggerAction: 'all',
                                   emptyText:'请选择',
                                   selectOnFocus:true,
                                   width : '100',
                                   anchor : '90%'
                               }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             store: bzStore,
                             xtype : 'combo',
                             name : 'CUR_COD',
                             hiddenName : 'CUR_COD',
                             fieldLabel : '币种',
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             //editable:false,
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '90%'
                         },
                         {
                             fieldLabel:'发行金额(元)',
                             labelStyle: 'text-align:right;',
                             xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             maxLength:18,
                             name:'ISSUE_AMT',
                             anchor:'90%'
                         },
                         {
                             fieldLabel :'利率规定',
                             xtype:'textfield',
                             maxLength:250,
                             labelStyle: 'text-align:right;',
                             name:'INTR',
                             anchor : '90%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             fieldLabel:'债券期限(年)',
                             labelStyle: 'text-align:right;',
                             xtype:'numberfield',
                             maxLength:6,
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
                             name:'BND_YEAR',
                             anchor:'90%'
                         },
                         {
                             fieldLabel :'是否上市',
                             name: 'SH_FLG', 
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
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             fieldLabel :'备注',
                             labelStyle: 'text-align:right;',
                             xtype:'textarea',
                             name:'RMAK',
                             maxLength:2000,
                             anchor : '100%'
                         },{
                             name:'CUST_ID',
                             id:'custid02',xtype:'textfield',
                             hidden:true
                         }]
                     }
                ]}
                ]}]
       
       
        });
	
	var bondIssueWindow = new Ext.Window(
	        {
	            //layout : 'fit',
	            height : document.body.clientHeight-240,
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
	            items : [bondIssueForm],
	            buttons : [{ // 窗口底部按钮配置
	                text : '提    交', // 按钮文本
	                handler : function() { // 按钮响应函数
	                    if(!bondIssueForm.getForm().isValid())
	                    { 
	                        alert('请填写正确信息');
	                        return false;
	                    }
	                    if(editFlag == 1){
	                        var infoRecord = grid.getSelectionModel().getSelected();
	                        id=infoRecord.data.MXTID;
	                        custid=oCustInfo.cust_id;
	                        Ext.getCmp('id02').setValue(id);
	                        Ext.getCmp('custid02').setValue(custid);
	                    }else{
	                    	custid=oCustInfo.cust_id;
	                        Ext.getCmp('custid02').setValue(custid);
	                    }
	                    Ext.Ajax.request({
	                        url: basepath+'/customerbondissue.json',
	                        method: 'POST',
	                        params:bondIssueForm.getForm().getFieldValues(),
	                        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                        success : checkResult,
	                        failure : checkResult
	                    });
	                    bondIssueWindow.hide();
	                    
	                }
	            },
	            { // 窗口底部按钮配置
	                text : '重    置', // 按钮文本
	                handler : function() { // 按钮响应函数
	                    resetForm(bondIssueForm);
	                }
	            },
	            { // 窗口底部按钮配置
	                text : '关    闭', // 按钮文本
	                handler : function() { // 按钮响应函数
	                    bondIssueWindow.hide();
	                }
	            }]
	        });
	/******************************************************************/
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
											fieldLabel : '债券类型',
											name : 'BND_TYP',
											store: zqlxStore,
				                             xtype : 'combo',
				                             labelStyle: 'text-align:right;',
				                             valueField:'key',
				                             displayField:'value',
				                             mode : 'local',
				                             typeAhead: true,
				                             //editable:false,
				                             forceSelection: true,
				                             triggerAction: 'all',
				                             emptyText:'请选择',
				                             selectOnFocus:true,
				                             width : '100',
				                             anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '债券代码', // 标签
//											id : 'ISSUE_COD',
			                                   maxLength:30,
			                                   vtype:'alphanum',
											 labelStyle: 'text-align:right;',
											name : 'ISSUE_COD', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '债券名称', // 标签
											name : 'ISSUE_NAME', // name:后台根据此name属性取值
											 labelStyle: 'text-align:right;',
											allowBlank : true,
			                                   maxLength:80,
											anchor : '80%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '是否上市',
									name : 'SH_FLG',
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
		                             anchor : '80%'
								}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
					    var conditionStr =  qForm.getForm().getFieldValues();
                        //var conditionTstr = Ext.encode(conditionStr);
//					    alert(Ext.encode(conditionStr));
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
				header : 'NO',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	           {
				header : '债券名称', // 列标题
				dataIndex : 'ISSUE_NAME', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '债券代码',
				dataIndex : 'ISSUE_COD',
				sortable : true,
				width : 150
			}, {
				header : '债券类型',
			    sortable : true,
				dataIndex : 'BND_TYP_ORA'
			}, {
				header : '币种',
				sortable : true,
				dataIndex : 'CUR_COD_GP'
			}, {
				header : '发行金额（元）',
				sortable : true,
				dataIndex : 'ISSUE_AMT',
				align : 'right',
                renderer: money('0,000' )

			}, {
				header : '利率规定',
				sortable : true,
				dataIndex : 'INTR'
			}, {
				header : '债券期限（年）',
				sortable : true,
				dataIndex : 'BND_YEAR'
			}, {
				header : '是否上市',
				sortable : true,
				dataIndex : 'SH_FLG'
			}, {
				header : '备注',
				sortable : true,
				dataIndex : 'RMAK'
			}
			]);

	/**
	 * 数据存储
	 */
	var tempCustId = oCustInfo.cust_id;
 var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/bondinformation.json?customerId='+tempCustId
//			            success : function(response) {
//                            var resultArray = Ext.util.JSON.decode(response.responseText);
//                            Ext.Msg.alert('提示', response.responseText);
//                        }
			            
			        }),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [
						{name: 'CUST_ID'},
						{name: 'ISSUE_NAME'},
						{name: 'ISSUE_COD'},
						{name: 'BND_TYP'},
                        {name: 'BND_TYP_ORA'},
						{name: 'CUR_COD'},
                        {name: 'CUR_COD_GP'},
						{name: 'ISSUE_AMT'},
						{name: 'INTR'},
						{name: 'SH_FLG'},
						{name: 'RMAK'},
						{name: 'ID'},
                        {name: 'BND_YEAR'},
						{name: 'MXTID'}
					])
				});
	
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
                    text : '新增',
                    handler : function() {
                        editFlag = 0;
                        addInit();
                    }},'-',
                    {
                    text : '修改',
                    handler : function() {
                        editFlag = 1;
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
//				    title: "客户统一视图->债券发行信息", 
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
            bondIssueForm.getForm().loadRecord(infoRecord);
            bondIssueWindow.show();
        }}
	}
	function addInit(){
	   // resetForm(bondIssueForm);
        Ext.getCmp('id02').setValue('');
        Ext.getCmp('custid02').setValue(parent.location.href.split("customerId=")[1]);

	  bondIssueWindow.show();  
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
                url : basepath+'/customerbondissue/'
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