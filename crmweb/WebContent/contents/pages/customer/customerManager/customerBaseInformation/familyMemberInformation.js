Ext.onReady(function() {
    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
    /***********************************数据字典store*****************************/
    var zjlxStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=CMRZJLX'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });

    var jtgxStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=FAMILY_RELA'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    /***************************************************************************/
    var panel2 = new Ext.FormPanel({ 
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        title : '<span style="font-weight:normal">高管家庭成员详细信息</span>',
        width: '100%',
        height:600,
        items: [{
            autoHeight:true,
            items :[{ layout:'column',
                buttonAlign : 'center',
                     items:[{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             xtype:'textfield',
                             fieldLabel: '*家庭成员名称',
                             maxLength:40,
                             allowBlank : false,
                              labelStyle: 'text-align:right;',
                             name: 'MEMBERNAME',
                             anchor:'90%'
                         }, 
                         {
                             fieldLabel : '*家庭关系',
                             name:'FAMILYRELA',
                             store: jtgxStore,
                             xtype : 'combo',
                             allowBlank : false,
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
                             xtype:'datefield',
                             format:'Y-m-d', //日期格式化
                             fieldLabel: '生日',
                             editable:false,
                             name: 'BIRTHDAY',
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [
                         {
                             fieldLabel : '*成员证件类型',
                             id:'crettype',
                             name : 'MEMBERCRET_TYP',
                             store: zjlxStore,
                             xtype : 'combo',
                             allowBlank : false,
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
                             xtype:'textfield',
                             id:'cretno',
                             vtype:'alphanum',
                             maxLength:100,
                             fieldLabel: '*成员证件号码',
                             name: 'MEMBERCRET_NO',
                             allowBlank : false,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             xtype:'numberfield',
                             vtype:'telephone',
                             fieldLabel: '固定电话',
                              labelStyle: 'text-align:right;',
                             name: 'TEL',
                             anchor:'90%'
                         },{
                             xtype:'numberfield',
                             vtype:'mobile',
                             fieldLabel: '手机号码',
                             name: 'MOBILE',
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         }]
                     },{
                         columnWidth:.25,
                         layout: 'form',
                         items: [{
                             vtype:'email',
                             maxLength:40,
                             xtype:'textfield',
                             fieldLabel: '邮箱',
                              labelStyle: 'text-align:right;',
                             name: 'EMAIL',
                             anchor:'90%'
                         },{
                             xtype:'textfield',
                             fieldLabel: '家族成员所在企业名称',
                             name: 'COMPANY',
                             maxLength:50,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         },{
                             xtype:'textarea',
                             fieldLabel: '备注',
                             name: 'RMAK',
                             maxLength:2000,
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         },{
                             xtype:'textfield',
                             id : 'MANAGER_ID',
                             fieldLabel: '高管编号',
                             hidden:true,
                             name: 'MANAGER_ID',
                              labelStyle: 'text-align:right;',
                             anchor:'90%'
                         },{
                             name:'MXTID',
                             id:'id',
                             xtype:'textfield',
                             hidden:true
                         },{
                             name:'CUST_ID',
                             id:'custid',xtype:'textfield',
                             hidden:true
                         },{
                             name:'MEMBER_ID',
                             id:'MEMBER_ID',xtype:'textfield',
                             hidden:true
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
                    url: basepath+'/customerfamilymember.json',
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
            text : '关    闭', // 按钮文本
            handler : function() { // 按钮响应函数
                addRoleWindow.hide();
            }
        }]
    });
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
											fieldLabel : '家庭成员名称',
											name : 'MEMBERNAME',
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
								    fieldLabel : '家庭关系',
								    name:'FAMILYRELA',
								    store: jtgxStore,
		                             xtype : 'combo',
		                             allowBlank : false,
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
                        store.load({
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
			   {header : 'id', dataIndex : 'MXTID',sortable : true,width : 150,hidden :true},
               {header : '家庭成员名称', dataIndex : 'MEMBERNAME',sortable : true,width : 150 },
               {header : '成员证件类型', dataIndex : 'MEMBERCRET_TYP_GP',sortable : true,width : 150 },
		       {header : '成员证件号码', dataIndex : 'MEMBERCRET_NO',sortable : true,width : 150 },
				{header : '家庭关系', dataIndex : 'FAMILYRELA_ORA',sortable : true,width : 150 },
				{header : '固定电话', dataIndex : 'TEL',sortable : true,width : 150 },
				{header : '手机号码', dataIndex : 'MOBILE',sortable : true,width : 150 },
				{header : 'e-mail', dataIndex : 'EMAIL',sortable : true,width : 150 },
				{header : '生日', dataIndex : 'BIRTHDAY',sortable : true,width : 150 },
				{header : '家族成员所在企业名称', dataIndex : 'COMPANY',sortable : true,width : 150 },
				{header : '备注', dataIndex : 'RMAK',sortable : true,width : 150 }
				
			]);

		/**
		 * 数据存储
		 */
           var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/queryfamilymemberinformation.json?customerId='+parent.location.href.split("customerId=")[1]+'&managerId='+window.location.href.split("managerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			            root:'json.data',
			            totalProperty: 'json.count'
			        }, [
						{name: 'CUST_ID'},
						{name: 'MEMBER_ID'},
						{name: 'MANAGER_ID'},
						{name: 'MXTID'},
						{name: 'MEMBERCRET_NO'},
						{name: 'MEMBERNAME'},
						{name: 'FAMILYRELA'},
						{name: 'FAMILYRELA_ORA'},
						{name: 'MEMBERCRET_TYP'},
                        {name: 'MEMBERCRET_TYP_GP'},
						{name: 'TEL'},
						{name: 'MOBILE'},
						{name: 'EMAIL'},
						{name: 'BIRTHDAY'},
                        {name: 'COMPANY'},
                        {name: 'RMAK'},
						{name: 'CRM_DT'}
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
                    }},'-',
                    {
                        text : '返回高管信息',
                        handler : function() {
                            backleader();
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
				    title: "客户统一视图->高管家庭成员信息", 
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
        editFlag = 1;
		editInit();
	});
//	function editInit(){
//		addRoleWindow.show();
//		root.expand(true);
//	};
	
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
        Ext.getCmp('custid').setValue(parent.location.href.split("customerId=")[1]);
        Ext.getCmp('MANAGER_ID').setValue(window.location.href.split("managerId=")[1]);
        Ext.getCmp('MEMBER_ID').setValue(window.location.href.split("managerId=")[1]);
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
                url : basepath+'/customerfamilymember/'
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
    
    function backleader(){
        window.location.href = 'leaderInformation.jsp' ;
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
    
    var cb = Ext.getCmp('crettype');
    var cb1 = Ext.getCmp('cretno');
    cb.addListener('select',function(){
        if(cb.getValue()=='CRM_CMRZJLX_001'){
            cb1.vtype='IDCard';
        }else{
            cb1.vtype='alphanum';
        }
    });
    /**********************************************************/
}); 