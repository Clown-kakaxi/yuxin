Ext.onReady(function() {
    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
    /************************************************************************/

    var sjlxStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=EVENT_TYP'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    /************************************************************************/
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">客户事件信息</span>',
		width: '100%',
	    height:'100%',
		items: [{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*事件名称',
							 maxLength:100,
                             allowBlank:false,
							  labelStyle: 'text-align:right;',
							 name: 'EVENT_NAME',
							 anchor:'95%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '维护人员',
							 name: 'WHRY',
							 disabled:true,
							 readOnly:true,
							 labelStyle: 'text-align:right;',
							 anchor:'95%'
						 }]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
			                    fieldLabel : '事件类型',
			                    name:'EVENT_TYP',
			                    store: sjlxStore,
	                             xtype : 'combo',
	                             //editable:false,
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
	                             anchor : '95%'
			                },{
							 xtype:'datefield',
							 fieldLabel: '维护日期',
							 disabled:true,
							 readOnly:true,
							 name: 'WHDT',
							 format:'Y-m-d', //日期格式化
							  labelStyle: 'text-align:right;',
							 anchor:'95%'
						 }]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
						     fieldLabel: '是否提醒',
						     name: 'WARN_FLG',
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
                             anchor : '95%'
                         }]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
							 xtype:'datefield',
							 editable:false,
							 fieldLabel: '发生日期',
							 format:'Y-m-d', //日期格式化
							 labelStyle: 'text-align:right;',
							 name: 'FS_DT',
							 anchor:'95%'
						 },{
						     name:'EVENT_ID',
						     xtype:'textfield',
						     id:'EVENT_ID',
						     hidden:true
						 },{
                             name:'CUST_ID',
                             id:'custid',xtype:'textfield',
                             hidden:true
                         }]
					 },{
						 columnWidth:.99,
						 layout: 'form',
						 items: [{
							 xtype:'textarea',
							 fieldLabel: '*事件内容',
                             maxLength:200,
                             allowBlank:false,
							 labelStyle: 'text-align:right;',
							 name: 'EVENT_DESC',
							 anchor:'99%'
						 }]
					 }
				]}
				]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : document.body.scrollHeight-200,
        width:document.body.scrollWidth-400,
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
		pageY : 20,
		//pageX : document.body.clientWidth / 2 - 420 / 2,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [panel2],
	    buttons : [
					{
						text : '保存',
						handler : function() {
							insert();
						}
					}, {
						text : '重置',
						handler : function() {
							resetForm(panel2);
						}
					}, {
						text : '关闭',
						handler : function() {
							addRoleWindow.hide();
						}
					} ]
	});
	var qForm = new Ext.form.FormPanel({
//		renderTo:'viewport_center',
		title:'交叉营销',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		height:80,
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .3,
				layout : 'form',
				labelWidth : 80, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '数据周期从',
					name : 'EVENT_NAME',
                     maxLength:100,
					 labelStyle: 'text-align:right;',
					xtype : 'datefield', // 设置为数字输入框类型
					anchor : '90%'
				},new Com.yucheng.bcrm.common.OrgField({
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '机构',
					labelStyle : 'text-align:right;',
					id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'instncode',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			}, {
				columnWidth : .3,
				layout : 'form',
				labelWidth : 80, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '数据周期到',
					name : 'EVENT_NAME',
                     maxLength:100,
					 labelStyle: 'text-align:right;',
					xtype : 'datefield', // 设置为数字输入框类型
					anchor : '90%'
				},{
					fieldLabel : '数据挖掘模型',
					name : 'EVENT_NAME',
                     maxLength:100,
					 labelStyle: 'text-align:right;',
					xtype : 'textfield', // 设置为数字输入框类型
					anchor : '90%'
				}]
			}]
				}],
		buttons : [{
					text : '确定',
						handler : function() {}
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
	    {header : 'EVENT_ID',dataIndex : 'EVENT_ID',sortable : true,hidden :true}, 
        {header : '事件名称',dataIndex : 'EVENT_NAME',sortable : true,width : 150}, 
        {header : '事件类型',dataIndex : 'EVENT_TYP_ORA',sortable : true,width : 150},
        {header : '事件内容',dataIndex : 'EVENT_DESC',width : 200}, 
        {header : '是否提醒',dataIndex : 'WARN_FLG',width : 100}, 
        {header : '发生日期',dataIndex : 'FS_DT',sortable : true,width : 150}, 
        {header : '维护人员',dataIndex : 'WHRY',width : 150},
        {header : '维护日期',dataIndex : 'WHDT',sortable : true,width : 150}
	]);

	var tempUserId = 100;
	//数据源
   var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/eventinformation.json?customerId='+tempUserId}),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			            root:'json.data',
			            totalProperty: 'json.count'
			        }, [{name: 'EVENT_ID'},
						{name: 'CUST_ID'},
						{name: 'EVENT_NAME'},
                        {name: 'EVENT_TYP'},
                        {name: 'EVENT_TYP_ORA'},
						{name: 'EVENT_DESC'},
						{name: 'FS_DT'},
						{name: 'WHRY'},
						{name: 'WARN_FLG'},
						{name: 'WHDT'}
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
                    iconCls : 'deleteIconCss',
                    handler : function() {
                        editFlag = 1;
                        editInit();
                    }},'-',
                    {
                    text : '删除',
                    iconCls:'editIconCss',
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
                limit : parseInt(pagesize_combo.getValue())
            }
        });
    });
    
//    Ext.getCmp('EVENT_TYP1').on("select",function(comboBox){
//       alert(parseInt(Ext.getCmp('EVENT_TYP1').getValue())); 
//       Ext.getCmp('EVENT_TYP1').setValue(parseInt(Ext.getCmp('EVENT_TYP1').getValue())); 
//    });
//    
//    Ext.getCmp('EVENT_TYP2').on("select",function(comboBox){
//        Ext.getCmp('EVENT_TYP2').setValue(parseInt(Ext.getCmp('EVENT_TYP2').getValue())); 
//     });
    
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
//		renderTo:'viewport_center',
        height : document.body.clientHeight-130,
        width : document.body.clientWidth-225,
		frame : true,
		autoScroll : true,
//		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,
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

	var viewport = new Ext.Viewport({
	    layout:'fit',
	    title: "营销管理->交叉营销分析",
	    items:[grid]
	});
	// 布局模型
	function insert(){
	    if(!panel2.getForm().isValid())
	    { 
	        alert('请填写正确信息');
	        return false;
	    }
        if(editFlag == 1){
            var infoRecord = grid.getSelectionModel().getSelected();
            id=infoRecord.data.EVENT_ID;
            custid=oCustInfo.cust_id;
            Ext.getCmp('EVENT_ID').setValue(id);
            Ext.getCmp('custid').setValue(custid);
        }
//		Ext.Ajax.request({
//            url: basepath+'/customer-event.json',
//            method: 'POST',
////            form:'panel2',
//            params:panel2.getForm().getFieldValues(),
//            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//            success : checkResult,
//            failure : checkResult
//        });
        Ext.Msg.alert('提示','保存成功');
        addRoleWindow.hide();

	};
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
        panel2.getForm().reset();
        Ext.getCmp('EVENT_ID').setValue('');
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
                tempId = selectRe.data.EVENT_ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
//            Ext.Ajax.request({
//                url : basepath+'/customer-event/'
//                        +tempId+'.json?idStr='+idStr,
//                method : 'DELETE',        
//                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//                success : checkResult,
//                failure : checkResult
//            });
            Ext.Msg.alert('提示','删除成功');
            };

    }
        /****************************************************************************************/

    }
    
    function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        debugger;
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
            limit : parseInt(pagesize_combo.getValue())
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
        
        debugger;
        
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