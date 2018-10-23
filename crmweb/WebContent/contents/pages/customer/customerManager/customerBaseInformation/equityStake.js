Ext.onReady(function() {
    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
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
    
    var dkkztStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=LNCRD_STS'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

    /***************************************************************************/

	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">对外股权投资情况</span>',
		width: '100%',
	    height:600,
		items: [{
	          xtype:'textfield',
	          name:'MXTID',
	          id:'id',
	          hidden:true
	        },{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*投向企业名称',
							 maxLength:200,
                             allowBlank:false,
							  labelStyle: 'text-align:right;',
							 name: 'COMPANY',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '投向企业法人代表名称',
							 name: 'FR_NAME',
                             maxLength:40,
							  labelStyle: 'text-align:right;',
							 value :'总经理',
							 anchor:'90%'
						 },{
						     xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
                             allowBlank:false,
							fieldLabel:'*实际投资金额（元）',
                            maxLength:18,							 labelStyle: 'text-align:right;',
							name:'INV_AMT',
							anchor:'90%'
						 
						 },{
                             fieldLabel : '是否第一大股东',
                             name: 'BIG_FLG' ,
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
							 xtype:'textfield',
							  fieldLabel: '投资方式',
							   labelStyle: 'text-align:right;',
							 name: 'INV_TYP',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '投向企业贷款卡编号',
							 name: 'LNCRD_NO',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
						     xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
							 fieldLabel: '投资占比（%）',
							 maxValue:100,
							 name: 'INV_PCT',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
                             fieldLabel : '是否合并报表',
                             name: 'HBRPT_FLG' ,
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
						 items: [
						 {
                             store: bzStore,
                             xtype : 'combo',
                             name : 'CUR_COD',
                             hiddenName : 'CUR_COD',
                             fieldLabel : '出资币种',
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
                         },{
							 fieldLabel: '投向企业贷款卡状态',
							 name: 'LNCRD_STS',
							 store: dkkztStore,
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
						 },{
							 xtype:'datefield',
							 fieldLabel: '投资日期',
							 editable:false,
							 format:'Y-m-d', //日期格式化
							 name: 'INV_DT',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
							 xtype:'textarea',
							 fieldLabel: '备注',
							 name: 'RMAK',
                             maxLength:2000,
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '投向企业组织机构代码',
                             maxLength:40,
							  labelStyle: 'text-align:right;',
							 name: 'INV_ZZDM',
							 anchor:'90%'
						 },{
						     xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
							 fieldLabel: '应出资金额（元）',
                             maxLength:18,
							 name: 'PAY_AMT',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
						     xtype:'numberfield',
                             minValue:0,allowNegative :false,
                             allowDecimals:false,
							 fieldLabel: '第一年投资收益（元）',
                             maxLength:18,
							 name: 'INV_RCV',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
                             name:'CUST_ID',
                             id:'custid',xtype:'textfield',
                             hidden:true
                         }]
					 }
				]/*,
				buttons : [{
					text : '保存',
					handler : function(){
					    Ext.Ajax.request({
                            url: basepath+'/customerinvestment.json',
                            method: 'POST',
                            form:panel2.getForm().id
                        });
                        addRoleWindow.hide();
					}
				}]*/}
				]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : document.body.scrollHeight-100,
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
		buttons:[{ // 窗口底部按钮配置
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
                            url: basepath+'/customerinvestment.json',
                            method: 'POST',
                            params:panel2.getForm().getFieldValues(),        
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
                        resetForm(panel2);
                    }
                },
                { // 窗口底部按钮配置
                    text : '关闭', // 按钮文本
                    handler : function() { // 按钮响应函数
                        addRoleWindow.hide();
                    }
                }]
	});
	var qForm = new Ext.form.FormPanel({
		renderTo:'viewport_center',
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
											fieldLabel : '投向企业名称',
											name : 'COMPANY',
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
											fieldLabel : '投资方式', // 标签
//											id : 'INV_TYP',
											 labelStyle: 'text-align:right;',
											name : 'INV_TYP', // name:后台根据此name属性取值
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
											fieldLabel : '投向企业组织机构代码', // 标签
											name : 'INV_ZZDM', // name:后台根据此name属性取值
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
								    store: bzStore,
		                             xtype : 'combo',
		                             name : 'CUR_COD',
		                             hiddenName : 'CUR_COD',
		                             fieldLabel : '出资币种',
		                             labelStyle: 'text-align:right;',
		                             valueField:'key',
		                             displayField:'value',
		                             mode : 'local',
		                             typeAhead: true,
		                             forceSelection: true,
		                             triggerAction: 'all',
		                             emptyText:'请选择',
		                             selectOnFocus:true,
		                             width : '100',
		                             anchor : '80%'
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
	           {
				header : '投向企业名称', // 列标题
				dataIndex : 'COMPANY', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '投资方式',
				dataIndex : 'INV_TYP',
				sortable : true,
				width : 150
			}, {
				header : '出资币种',
				dataIndex : 'CUR_COD_GP'
			}, {
				header : '投向企业组织机构代码',
				dataIndex : 'INV_ZZDM',
				width : 150
			}, {
				header : '投向企业法人代表名称',
				dataIndex : 'FR_NAME',
				width : 150
			}, {
				header : '投向企业贷款卡编号',
				dataIndex : 'LNCRD_NO',
				width : 150
			}, {
				header : '投向企业贷款卡状态',
				dataIndex : 'LNCRD_STS_ORA',
				width : 150
			}, {
				header : '应出资金额(元)',
				dataIndex : 'PAY_AMT',
                align : 'right',
                width:150,
                renderer: money('0,000.00' )
			}, {
				header : '实际投资金额(元)',
				dataIndex : 'INV_AMT',
                align : 'right',
                width:150,
                renderer: money('0,000.00' )
			}, {
				header : '投资占比(%)',
				dataIndex : 'INV_PCT',
                align : 'right',
                renderer: percent(false)
			}, {
				header : '投资日期',
				dataIndex : 'INV_DT'
			}, {
				header : '第一年投资收益（元）',
				dataIndex : 'INV_RCV',
                align : 'right',
                width:150,
                renderer: money('0,000.00' )
			}, {
				header : '是否第一大股东',
				dataIndex : 'BIG_FLG'
			}, {
				header : '是否合并报表',
				dataIndex : 'HBRPT_FLG'
			}, {
				header : '备注',
				dataIndex : 'RMAK'
			}
			]);

	/**
	 * 数据存储
	 */
	  var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/investment.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [
			            {name: 'MXTID'},
						{name: 'ID'},
						{name: 'BIG_FLG'},
						{name: 'COMPANY'},
						{name: 'CUR_COD'},
						{name: 'CUR_COD_GP'},
						{name: 'CUST_ID'},
						{name: 'FR_NAME'},
						{name: 'HBRPT_FLG'},
						{name: 'INV_AMT'},
						{name: 'INV_DT'},
						{name: 'INV_PCT'},
						{name: 'INV_RCV'},
						{name: 'INV_TYP'},
						{name: 'INV_ZZDM'},
                        {name: 'LNCRD_NO'},
                        {name: 'LNCRD_STS'},
                        {name: 'LNCRD_STS_ORA'},
                        {name: 'PAY_AMT'},
						{name: 'RMAK'}
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
                    }}]
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
		renderTo:'viewport_center',
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

//	// 布局模型
//	var viewport = new Ext.Viewport({
//	    layout:'fit',
//        items:{
//
//				layout : 'border',
//				items: [{   
//					region: 'north',
////				    id: 'north-panel',
//				    title: "客户统一视图->对外股权投资情况", 
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
            panel2.getForm().loadRecord(infoRecord);
            addRoleWindow.show();
        }}
    }
    function addInit(){
        editFlag = 0;
        resetForm(panel2);
        Ext.getCmp('id').setValue('');
        Ext.getCmp('custid').setValue(parent.location.href.split("customerId=")[1]);

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
                url : basepath+'/customerinvestment/'
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
        debugger;
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
    function resetForm(form){debugger;
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
            debugger;
            f.setValue('');
           // f.originalValue = '';
        });
    }
    /**********************************************************/
}); 