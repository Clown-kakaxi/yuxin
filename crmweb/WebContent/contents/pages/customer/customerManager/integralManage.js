Ext.onReady(function(){
	Ext.QuickTips.init(); 

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
      

   var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			region: 'north',
		    title: "服务管理->积分管理->积分查询", 
		    height: 120,
			items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [
				         {
							fieldLabel : '客户号',
							name : 'CUST_ID',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
						},new Ext.form.ComboBox({
							hiddenName : 'CUST_TYP',
							fieldLabel : '客户类型',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
						})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth: 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '客户名称',
								id:'CUST_ZH_NAME',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},new Com.yucheng.crm.common.OrgUserManage({ 
								xtype:'userchoose',
								fieldLabel : '所属客户经理', 
								id:'CUST_MANAGER',
								labelStyle: 'text-align:right;',
								name : 'CUST_MANAGER',
								hiddenName:'custMgrId',
								searchRoleType:('127,47'),  //指定查询角色属性 ,默认全部角色
								searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								singleSelect:false,
								anchor : '90%'
								})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '证件号码',
								name : 'CERT_NUM',
								id:'CERT_NUM',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},new Ext.form.ComboBox({
								hiddenName : 'CUST_LEV',
								fieldLabel : '客户级别',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore8,
								displayField : 'value',
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '90%'
							})
							]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Com.yucheng.bcrm.common.OrgField({
								searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								fieldLabel : '所属机构',
								labelStyle : 'text-align:right;',
								id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
								name : 'CUST_ORG', 
								hiddenName: 'instncode',   //后台获取的参数名称
								anchor : '90%',
								checkBox:true //复选标志
							})]
						}]
			}],
		buttons : [{
					text : '查询',
					handler : function() {
				        store.on('beforeload', function() {
				        	var conditionStr =  qForm.getForm().getValues(false);
				        	
				        	
				            this.baseParams = {
				                    "condition":Ext.encode(conditionStr)
				                    
				            };
					});
				        
						store.load({      
							  params : {
                                   start : 0,
                                   limit : bbar.pageSize/*,
                                   userId:Ext.encode(userId.aId)*/}});     
				
				   }},{
					text : '重置',
					     handler : function() {
					    	 qForm.getForm().reset();
					    	 Ext.getCmp('jigouhao').setValue('');
					    	 Ext.getCmp('CUST_MANAGER').setValue('');
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

	    
        {header : 'ID',dataIndex : 'id',sortable : true,width : 120,hidden:true},
        {header : '客户编号',dataIndex : 'custId',sortable : true,width : 150},
        {header : '客户名称',dataIndex : 'custName',sortable : true,width : 150},
        {header : '客户累计积分',dataIndex : 'countNum',sortable : true,width : 150},
        {header : '客户当月积分',dataIndex : 'custCumCount',sortable : true,width : 150},
        {header : '客户当月消费积分',dataIndex : 'custCumCost',sortable : true,width : 150},
        {header : '客户累计消费积分',dataIndex : 'custCostSum',sortable : true,width : 150},
        {header : '客户可用积分',dataIndex : 'custSpareCount',sortable : true,width : 150},
        {header : '平台日期',dataIndex : 'etlDate',type:'date',sortable : true,renderer:function(a,b,c,d){return new Date(a.time);},width : 150}




	    
		]);

/**
 * 数据存储
 */
 var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/acrmFCiCustIntegral-info!indexPage.json'/*,
		        	success:function(response){
		        	alert(response.responseText);
		        }*/
		     /* ,
		        	success : function(response) {
						Ext.Msg.alert('提示', response.responseText);
					}*/
		        }),
		       reader: new Ext.data.JsonReader({
		       totalProperty : 'json.count',
		        root:'json.data'
		        }, [
					{name: 'id'},
					{name: 'countNum'},
					{name: 'custCostSum'},				
					{name: 'custCumCount'},	
					{name: 'custId'},				
					{name:'custName'},
					{name:'custSpareCount'},
					{name:'custType'},
					{name:'etlDate'},
					{name: 'custCumCost'}			

			
				])
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
var number = parseInt(pagesize_combo.getValue());
pagesize_combo.on("select", function(comboBox) {
	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
	store.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});
});
var bbar = new Ext.PagingToolbar({
    pageSize : number,
    store : store,
    displayInfo : true,
    displayMsg : '显示{0}条到{1}条,共{2}条',
    emptyMsg : "没有符合条件的记录",
    items : ['-', '&nbsp;&nbsp;', pagesize_combo
             ]
});
var grid = new Ext.grid.GridPanel({
	frame : true,
	autoScroll : true,
	region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
	store : store, // 数据存储
	stripeRows : true, // 斑马线
	cm : cm, // 列模型
	sm : sm, // 复选框
	bbar:bbar,
	viewConfig:{
		   forceFit:false,
		   autoScroll:true
		},
		tbar:[ 
		        {
					text:'积分兑换',
					iconCls:'detailIconCss',
					handler:function()
					{addProForm.getForm().findField('orderStatus').setValue('草稿');

		        	/*window.location.href = '../customerManager/integralManage.jsp';*/
		        	addProWindow.show();
		        	
					}}],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});


var viewport = new Ext.Viewport({
	layout : 'fit',
	items:[{
		layout:'border',
	items: [qForm,grid] 
	}]
});



   }); 