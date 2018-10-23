
Ext.onReady(function() {
	var oldAssistInput=null;
	/**防止内存控制机制误删Ext.MessageBox内部对象**/
	Ext.Msg.alert('ANTIDEBUG','ANTIDEBUG');
	Ext.Msg.hide();
	//客户类型
	var boxstore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//证件类型数据集
	var certstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//客户级别数据集
	var boxstore8 = new Ext.data.Store({  
		sortInfo: {
	    	field: 'key',
	    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=P_CUST_GRADE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});



	

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
   
	


   
	

	
	/*
	 * 特殊客户屏蔽
	 */
	var custScreenStore = new Ext.data.Store({
	    restful: true,
	    autoLoad: true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/custScreenQuery.json'
	    }),
	    reader: new Ext.data.JsonReader({
	        root: 'json.data'
	    },
	    [{
	        name: 'CUST_ID',
	        mapping: 'CUST_ID'
	    },{
	        name: 'CORE_ID',
	        mapping: 'CORE_ID'
	    },{
	        name: 'CUST_ZH_NAME',
	        mapping: 'CUST_ZH_NAME'
	    },
	    {
	        name: 'SCREEN_DATE',
	        mapping: 'CREATE_DATE'
	    },
	    {
	        name: 'SCREEN_STUTS',
	        mapping: 'SCREEN_STUTS'
	    },
	    {
	        name: 'SCREEN_ID',
	        mapping: 'ID'
	    },{
            name: 'CREAT_USER_NAME',
            mapping: 'CREAT_USER_NAME'
        }
	    
	    
	    ])
	});

	var custScreenrownum = new Ext.grid.RowNumberer({
	    header: 'No.',
	    width: 28
	});

	var custScreensm = new Ext.grid.CheckboxSelectionModel();

	var custScreencm = new Ext.grid.ColumnModel([custScreenrownum, custScreensm,{
        header: '客户编号',
        dataIndex: 'CUST_ID',
        sortable: true,
        width: 120
    }, {
	    header: '客户名称',
	    dataIndex: 'CUST_ZH_NAME',
	    sortable: true,
	    width: 200
	},
	{
	    header: '屏蔽日期',
	    dataIndex: 'SCREEN_DATE',
	    sortable: true,
	    width: 200
	},
	{
	    header: '屏蔽状态',
	    dataIndex: 'SCREEN_STUTS',
	    sortable: true,
	    width: 200,
	    renderer:function(v){
			if(v=="1")
				return "<span style='color:red'>已屏蔽</span>";
			else 
				return "<span style='color:green'>未屏蔽</span>";
		}
	},
	{
	    dataIndex: 'CREAT_USER_NAME',
	    header: '创建人',
	    allowBlank: false,
	    sortable: true,
	    //defaultValue : '2',
	    width: 100
	},
	{
	    dataIndex: 'SCREEN_ID',
	    header: 'ID',
	    hidden:true,
	    allowBlank: false,
	    sortable: true,
	    hidden:true,
	    //defaultValue : '2',
	    width: 100
	}]);
	
	var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({
	    fieldLabel: '客户姓名',
	    labelStyle: 'text-align:right;',
	    labelWidth: 100,
	    name: 'custName',
	    id: 'rel_cust_name',
	    singleSelected: false,
	    //单选复选标志
	    editable: false,
	    allowBlank: false,
	    //不允许为空
	    blankText: "不能为空，请填写",
	    anchor: '85%',
	    hiddenName:'abcd',
	    callback: function(a,b) {
			var records = Ext.getCmp('rel_cust_name').oCustomerQueryGrid.getSelectionModel().selections.items;
			custScreenStore.add(records);
	        
	    }
	});
	
	var custScreenGrid = new Ext.grid.GridPanel({
	    layout:'fit',
        region : 'center',
	    id: 'custScreenGrid',
	    tbar : new Ext.Toolbar({
			items : [
			         "添加客户：",search_cust,'-',
			         "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;筛选条件：",'-',"客户号",
			         {
						fieldLabel : '客户号',
						name : 'CUST_ID',
						id:"custIdScreen",
						xtype : 'textfield',
						labelStyle: 'text-align:right;',
						anchor : '85%'
					},'-','客户名称',
					{
						fieldLabel : '客户名称',
						name : 'CUST_ZH_NAME',
						id:"custZhNameScreen",
						xtype : 'textfield',
						labelStyle: 'text-align:right;',
						anchor : '85%'
					},'-',
					{
						text : '筛选',
						id : 'descBtn',
						xtype : 'button',
						handler : function() {
							var CUST_ID = Ext.getCmp("custIdScreen").getValue();
							var CUST_ZH_NAME = Ext.getCmp("custZhNameScreen").getValue();
							custScreenStore.baseParams = {
								'condition':"{\"CUST_ID\":\""+CUST_ID+"\",\"CUST_ZH_NAME\":\""+CUST_ZH_NAME+"\"}"
							};
							custScreenStore.load();   		
						}
					},'-',{
						text : '重置',
						id : 'descResetBtn',
						xtype : 'button',
						handler : function() {
							Ext.getCmp("custIdScreen").setValue("");
							Ext.getCmp("custZhNameScreen").setValue("");
						}
					},'-'
				]
			}),
	    store: custScreenStore,
	    cm: custScreencm,
	    sm: custScreensm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    },
	    buttons : [{
	                     text : '确认屏蔽',
	                     handler : function() {
	        if (!custScreenGrid.selModel.hasSelection()) {
                Ext.Msg.alert("操作提示","请选择确认屏蔽的客户");
            }else{
            	debugger;
                 var records = custScreenGrid.getSelectionModel().selections.items;
                 var custIds = "";
                 for ( var i = 0; i < records.length; i++) {
                     if(!records[i].data.SCREEN_STUTS)//选择未屏蔽的客户来屏蔽
                     {
                         custIds += records[i].data.CUST_ID+",";
                     }
                 }
                 if (custIds==''){
                     Ext.Msg.alert("操作提示","请选择未屏蔽的客户进行屏蔽！");
                     return false;
                 }
                 Ext.Ajax.request({
                     url : basepath + '/custScreenOper!create.json',
                     method : 'GET',
                      params:{
                         'condition':custIds
                     },
                     waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                     success : function(response){
                         custScreenStore.load();
                         Ext.Msg.alert("操作提示","客户屏蔽成功");
                     },
                     failure : function(form, action) {
                         custScreenStore.load();
                         Ext.Msg.alert("操作提示","客户屏蔽失败");
                     }
                 }); 
             }}
         },
         {
             text : '取消屏蔽',
             handler : function() {
                 if (!custScreenGrid.selModel.hasSelection()) {
                     Ext.Msg.alert("操作提示","请选择取消屏蔽的客户");
                 }
                 else
                 {
                     var records = custScreenGrid.getSelectionModel().selections.items;
                     var screenIds = "";
                     for ( var i = 0; i < records.length; i++) {
                         if(records[i].data.SCREEN_STUTS)//选择未屏蔽的客户来屏蔽
                         {
                             screenIds += records[i].data.SCREEN_ID+",";
                         }
                     }
                     Ext.Ajax.request({
                         url : basepath + '/custScreenOper!remove.json',
                         method : 'GET',
                          params:{
                             'condition':screenIds
                         },
                         waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                         success : function(response){
                             custScreenStore.load();
                             Ext.Msg.alert("操作提示","客户取消屏蔽成功");
                         },
                         failure : function(form, action) {
                             custScreenStore.load();
                             Ext.Msg.alert("操作提示","客户取消屏蔽失败");
                         }
                     }); 
                 }
             }
         }/*,
         {
             text : '关闭',
             handler : function() {
                 custScreenWindow.hide();
                 store.load({      
                     params : {
                        start : 0,
                        limit : bbar.pageSize,
                        userId:Ext.encode(userId.aId)
                     }
                 });
             }
         }*/]
	});
	
//布局模型

var viewport = new Ext.Viewport({
    layout:'fit',
    items:[
            custScreenGrid]
});
});