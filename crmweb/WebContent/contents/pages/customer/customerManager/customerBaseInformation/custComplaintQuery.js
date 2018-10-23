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
		title : '<span style="font-weight:normal">客户投诉信息</span>',
		width: '100%',
	    height:250,
		items: [{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '*投诉标题',
							 maxLength:100,
//                             allowBlank:false,
							  labelStyle: 'text-align:right;',
							 name: 'TITLE',
							 anchor:'95%'
						 },
						 {
							 xtype:'textfield',
							 fieldLabel: '投诉内容',
							 maxLength:100,
//                             allowBlank:false,
							  labelStyle: 'text-align:right;',
							 name: 'CONTENT',
							 anchor:'95%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '被投诉人',
							 maxLength:100,
//                             allowBlank:false,
							  labelStyle: 'text-align:right;',
							 name: 'COMPLAINTED_PERSON',
							 anchor:'95%'
						 },
			                {
			                    fieldLabel : '被投诉机构',
			                    name:'COMPLAINTED_ORG',
			                    
	                             xtype : 'combo',
	                             //editable:false,
//	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             valueField:'key',
	                             displayField:'value',
	                             mode : 'local',
	                             typeAhead: true,
	                             forceSelection: true,
	                             triggerAction: 'all',
	                             emptyText:'请选择',
	                             selectOnFocus:true,
//	                             store:new Ext.data.ArrayStore({
//	                                 fields:['myId','displayText'],
//	                                 data:[['客户经理','客户经理'],
//	                                       ['柜面','柜面'],['CC','CC'],['网上银行','网上银行'],
//	                                       ['手机银行','手机银行'],['其他','其他']]
//	                             }),
	                             valueField:'myId',
	                             displayField:'displayText',
	                             width : '100',
	                             anchor : '95%'
			                },
			                {
							     fieldLabel: '处理机构',
							     name: 'HANDLE_ORG',
							     editable:false,
	                             forceSelection : true,
	                             xtype:'combo',
	                             labelStyle: 'text-align:right;',
	                             triggerAction:'all',
	                             mode:'local',
	                             store:new Ext.data.ArrayStore({
	                                 fields:['myId','displayText'],
	                                 data:[]
	                             }),
	                             valueField:'myId',
	                             displayField:'displayText',
	                             emptyText:'请选择',
	                             anchor : '95%'
	                         },{
								 xtype:'textfield',
								 fieldLabel: '处理人',
								 maxLength:100,
//	                             allowBlank:false,
								  labelStyle: 'text-align:right;',
								 name: 'HANDLE_PERSON',
								 anchor:'95%'
							 },{
								 xtype:'datefield',
								 editable:false,
								 fieldLabel: '分配日期',
								 format:'Y-m-d', //日期格式化
								 labelStyle: 'text-align:right;',
								 name: 'DISTRIBUTE_DATE',
								 anchor:'95%'
							 }
		                
						 ]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
			                    fieldLabel : '投诉类型',
			                    name:'COMPLAIN_TYPE',
//			                    store: sjlxStore,
	                             xtype : 'combo',
	                             //editable:false,
//	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             valueField:'key',
	                             displayField:'value',
	                             mode : 'local',
	                             typeAhead: true,
	                             store:new Ext.data.ArrayStore({
	                                 fields:['myId','displayText'],
	                                 data:[['服务态度','服务态度'],['服务时效','服务时效'],
	                                       ['系统问题','系统问题'],['账务差错','账务差错'],
	                                       ['贵宾业务','贵宾业务'],['收费问题','收费问题']]
	                             }),
	                             valueField:'myId',
	                             displayField:'displayText',
	                         
	                             forceSelection: true,
	                             triggerAction: 'all',
	                             emptyText:'请选择',
	                             selectOnFocus:true,
	                             width : '100',
	                             anchor : '95%'
			                },{
							 xtype:'datefield',
							 fieldLabel: '投诉日期',
//							 disabled:true,
							 readOnly:true,
							 name: 'WHDT',
							 format:'Y-m-d', //日期格式化
							  labelStyle: 'text-align:right;',
							 anchor:'95%'
						 },
						 {
			                    fieldLabel : '投诉来源渠道',
			                    name:'CHANNEL',
			                    
	                             xtype : 'combo',
	                             //editable:false,
//	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             valueField:'key',
	                             displayField:'value',
	                             mode : 'local',
	                             typeAhead: true,
	                             forceSelection: true,
	                             triggerAction: 'all',
	                             emptyText:'请选择',
	                             selectOnFocus:true,
	                             store:new Ext.data.ArrayStore({
	                                 fields:['myId','displayText'],
	                                 data:[['客户经理','客户经理'],
	                                       ['柜面','柜面'],['CC','CC'],['网上银行','网上银行'],
	                                       ['手机银行','手机银行'],['其他','其他']]
	                             }),
	                             valueField:'myId',
	                             displayField:'displayText',
	                             width : '100',
	                             anchor : '95%'
			                }]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
						     fieldLabel: '投诉状态',
						     name: 'COMPLAINT_STATE',
						     editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['未处理','未处理'],['已分配','已分配'],['已处理','已处理']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '100%'
                         },{
						     fieldLabel: '被投诉业务',
						     name: 'COMPLAINT_BUSINESS',
						     editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['存款业务','存款业务'],['贷款业务','贷款业务'],
                                       ['理财业务','理财业务'],['基金业务','基金业务'],
                                       ['网银业务','网银业务'],['呼叫中心业务','呼叫中心业务'],
                                       ['手机银行','手机银行'],['其他中间业务','其他中间业务']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '95%'
                         },{

						 xtype:'textfield',
						 fieldLabel: '处理方案',
						 maxLength:100,
//                         allowBlank:false,
						 labelStyle: 'text-align:right;',
						 name: 'HANDLE_FUNCTION',
						 anchor:'95%'
                         },
                         {
							 xtype:'datefield',
							 editable:false,
							 fieldLabel: '处理日期',
							 format:'Y-m-d', //日期格式化
							 labelStyle: 'text-align:right;',
							 name: 'HANDLE_DATE',
							 anchor:'95%'
						 }]
					 }
				]}
				]}]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height : document.body.scrollHeight-140,
        width:document.body.scrollWidth-600,
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
	var qForm = new Ext.form.FormPanel({//查询panel
		
		title:'投诉信息查询',
		height:130,
//		renderTo:'viewport_center',
//		buttonAlign:'center',
		labelWidth:100,//label的宽度
		labelAlign:'right',
		frame:true,
		autoScroll : true,
//		region:'north',
		split:true,
		items:[
				{
					layout:'column',
					items:[
					{
					 columnWidth:.22,
					 layout:'form',
					 items:[
					 	{
							xtype:'textfield',
							name:'TITLE',
							fieldLabel:'投诉标题',
							anchor:'95%'
						},{
		                    fieldLabel : '投诉类型',
		                    name:'COMLAINT_TYPE',
		                    store: sjlxStore,
                             xtype : 'combo',
                             //editable:false,
//                             allowBlank : false,
                             labelStyle: 'text-align:right;',
                             valueField:'key',
                             displayField:'value',
                             mode : 'local',
                             typeAhead: true,
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['服务态度','服务态度'],['服务时效','服务时效'],
                                       ['系统问题','系统问题'],['账务差错','账务差错'],
                                       ['贵宾业务','贵宾业务'],['收费问题','收费问题']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                         
                             forceSelection: true,
                             triggerAction: 'all',
                             emptyText:'请选择',
                             selectOnFocus:true,
                             width : '100',
                             anchor : '95%'
		                }
						]
					 },{
						 columnWidth:.22,
						 layout:'form',
						 items:[ {
						     fieldLabel: '被投诉业务',
						     name: 'COMPLAINTED_BUSINESS',
						     editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['存款业务','存款业务'],['贷款业务','贷款业务'],
                                       ['理财业务','理财业务'],['基金业务','基金业务'],
                                       ['网银业务','网银业务'],['呼叫中心业务','呼叫中心业务'],
                                       ['手机银行','手机银行'],['其他中间业务','其他中间业务']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '95%'
                         },{
			                    fieldLabel : '投诉来源渠道',
			                    name:'CHANNEL',
			                    
	                             xtype : 'combo',
	                             //editable:false,
//	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             valueField:'key',
	                             displayField:'value',
	                             mode : 'local',
	                             typeAhead: true,
	                             forceSelection: true,
	                             triggerAction: 'all',
	                             emptyText:'请选择',
	                             selectOnFocus:true,
	                             store:new Ext.data.ArrayStore({
	                                 fields:['myId','displayText'],
	                                 data:[['客户经理','客户经理'],
	                                       ['柜面','柜面'],['CC','CC'],['网上银行','网上银行'],
	                                       ['手机银行','手机银行'],['其他','其他']]
	                             }),
	                             valueField:'myId',
	                             displayField:'displayText',
	                             width : '100',
	                             anchor : '95%'
			                }
							]
						 },
					 {
					 	columnWidth:.22,
					 	layout:'form',
					 	items:[
						{
						name:'PROD_START_DATE_FROM',
						id:'PROD_START_DATE_FROM',
						anchor:'100%',
						xtype:'datefield',
						editable : false,
						format:'Y-m-d',
						fieldLabel:'起始日期'
						},
						{
						     fieldLabel: '投诉状态',
						     name: 'COMPAINT_STATE',
						     editable:false,
                             forceSelection : true,
                             xtype:'combo',
                             labelStyle: 'text-align:right;',
                             triggerAction:'all',
                             mode:'local',
                             store:new Ext.data.ArrayStore({
                                 fields:['myId','displayText'],
                                 data:[['未处理','未处理'],['已分配','已分配'],['已处理','已处理']]
                             }),
                             valueField:'myId',
                             displayField:'displayText',
                             emptyText:'请选择',
                             anchor : '100%'

                        }
					 	]
					 },
					 {
					 	columnWidth:.22,
					 	layout:'form',
					 	items:[			 	
						{
						name:'PROD_START_DATE_TO',
						id :'PROD_START_DATE_TO',
						anchor:'100%',
						xtype:'datefield',
						editable : false,
						format:'Y-m-d',				
						fieldLabel:'截止日期'
						}
						]			
					 }


					 
					]
				}
				],
		buttonAlign:'center',
		buttons : [{
			text : '查询',
				handler : function() {
				    var conditionStr =  qForm.getForm().getFieldValues();
                    store.on('beforeload', function() {
                        this.baseParams = {
                                "condition":Ext.encode(conditionStr)
                        };
                        });
//                    store.reload({
//                        params : {
//                            start : 0,
//                            limit : parseInt(pagesize_combo.getValue())
//                        }
//                    });
                    store.loadData(tb_memberData);
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
	    {header : 'INFO_ID',dataIndex : 'EVENT_ID',sortable : true,hidden :true}, 
        {header : '投诉标题',dataIndex : 'AA',sortable : true,width : 150}, 
        {header : '投诉内容',dataIndex : 'BB',sortable : true,width : 150},
        {header : '投诉类型',dataIndex : 'CC',sortable : true,width : 150},
        {header : '投诉来源渠道',dataIndex : 'DD',width : 150}, 
        {header : '被投诉业务',dataIndex : 'EE',width : 150}, 
        {header : '投诉状态',dataIndex : 'FF',width : 150}, 
        {header : '投诉日期',dataIndex : 'GG',width : 150},
        {header : '被投诉人',dataIndex : 'HH',width : 150},
        {header : '被投诉机构',dataIndex : 'II',width : 150}, 
        {header : '处理方案',dataIndex : 'JJ',width : 150}, 
        {header : '处理人',dataIndex : 'KK',width : 150}, 
        {header : '处理机构',dataIndex : 'LL',width : 150},
        {header : '分配日期',dataIndex : 'MM',width : 150},
        {header : '处理日期',dataIndex : 'NN',width : 150}

	]);

	var tempUserId = oCustInfo.cust_id;
	//数据源
   var store = new Ext.data.Store({
					//restful:true,
//			        proxy : new Ext.data.HttpProxy({url:basepath+'/eventinformation.json?customerId='+tempUserId}),
			        reader: new Ext.data.JsonReader({
		                //data:tb_memberData,

			           // successProperty: 'success',
			            root:'rows',
			            totalProperty: 'num'
			        }, [{name:'INFO_ID'},
			            {name: 'AA'},
						{name: 'BB'},
						{name: 'CC'},
                        {name: 'DD'},
                        {name: 'EE'},
						{name: 'FF'},
						{name: 'GG'},
						{name: 'HH'},
						{name: 'II'},
						{name: 'JJ'},
						{name: 'KK'},
						{name: 'LL'},
						{name: 'MM'},
						{name: 'NN'},
						{name: 'JJ'}
					])
				});
	var tb_memberData= {
			num:1,
			rows:[
			{"rownum":"1","AA":"投诉标题1","BB":"投诉内容1","CC":"服务时效","DD":"客户经理","EE":"网银业务",
				"FF":"处理中","GG":"2012.12.12","HH":"张三","II":"支行","JJ":"处理方案","KK":"李四",
				"LL":"总行","MM":"2012.12.13","NN":"2012.12.14"},
				{"rownum":"1","AA":"投诉标题2","BB":"投诉内容2","CC":"收费问题","DD":"柜面","EE":"黄金业务",
					"FF":"处理中","GG":"2012.12.12","HH":"张四","II":"支行","JJ":"处理方案","KK":"李四",
					"LL":"总行","MM":"2012.12.13","NN":"2012.12.14"},
					{"rownum":"1","AA":"投诉标题3","BB":"投诉内容3","CC":"服务时效","DD":"CC","EE":"基金业务",
						"FF":"处理中","GG":"2012.12.12","HH":"李四","II":"分行","JJ":"处理方案","KK":"李四",
						"LL":"总行","MM":"2012.12.13","NN":"2012.12.14"},
						{"rownum":"1","AA":"投诉标题4","BB":"投诉内容4","CC":"收费问题","DD":"网上银行","EE":"其他中间业务",
							"FF":"处理中","GG":"2012.12.12","HH":"王三","II":"分行","JJ":"处理方案","KK":"李四",
							"LL":"总行","MM":"2012.12.13","NN":"2012.12.14"},
							{"rownum":"1","AA":"投诉标题5","BB":"投诉内容5","CC":"服务时效","DD":"手机银行","EE":"理财业务",
								"FF":"处理中","GG":"2012.12.12","HH":"赵二","II":"支行","JJ":"处理方案","KK":"李四",
								"LL":"总行","MM":"2012.12.13","NN":"2012.12.14"}

			]
		};
	store.loadData(tb_memberData);
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
//        store.reload({
//            params : {
//                start : 0,
//                limit : parseInt(pagesize_combo.getValue())
//            }
//        });
        store.loadData(tb_memberData);
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
        height : document.body.clientHeight-160,
        width : document.body.clientWidth-225,
		frame : true,
		autoScroll : true,
//		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store:store,
		valueField:'myId',
		displayField:'displayText',
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,
		viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			forceFit : false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
/*	//拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        grid.setHeight(document.body.scrollHeight-130);
        grid.setWidth(document.body.scrollWidth);
        grid.getView().refresh();
    });*/

	// 布局模型
    var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
		height : document.body.clientHeight-30,
	    layout:'fit',
        items:{
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    height: 130,
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
		Ext.Ajax.request({
            url: basepath+'/customer-event.json',
            method: 'POST',
//            form:'panel2',
            params:panel2.getForm().getFieldValues(),
            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
            success : checkResult,
            failure : checkResult
        });
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
        Ext.getCmp('INFO_ID').setValue('');
//        Ext.getCmp('').setValue();

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
            Ext.Ajax.request({
                url : basepath+'/customer-event/'
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
//        debugger;
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
//            store.reload({
//                        params : {
//                            start : 0,
//                            limit : bbar.pageSize
//                        }
//                    });
            store.loadData(tb_memberData);
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
//            store.reload({
//                        params : {
//                            start : 0,
//                            limit : bbar.pageSize
//                        }
//                    });
            store.loadData(tb_memberData);
        }
    }
    
/*    store.load({
        params : {
            start : 0,
            limit : parseInt(pagesize_combo.getValue())
        }
    });*/

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