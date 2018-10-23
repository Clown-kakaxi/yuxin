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
	
    var typeStore = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data:[['1','家用电器'],['2','手机数码'],
              ['3','图书期刊'],['4','音像制品'],
              ['5','汽车用品'],['6','运动健康'],
              ['7','医疗保健'],['8','箱包服装'],
              ['9','食品饮料'],['10','优惠礼券']]});
    var statusStore = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data:[['1','草稿'],['2','已提交'],
              ['3','已确认'],['4','已审批'],
              ['5','已兑换']]});
      

   var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			region: 'north',
		    title: "服务管理->积分管理->积分兑换", 
		    height: 120,
				layout : 'column',
				items : [{
							columnWidth : .25,
							layout : 'form',
							border : false,
							items : [
				         {
							fieldLabel : '订单编号',
							name : 'ID',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
						},{
							
							fieldLabel : '订单状态',
							store : statusStore,
							xtype : 'combo', 
							resizable : true,
							name : 'ORDER_STATUS',
							hiddenName : 'ORDER_STATUS',
							valueField : 'myId',
							displayField : 'displayText',
							labelStyle: 'text-align:right;',
							mode : 'local',
							editable : false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							anchor : '90%'
						}]
						}, {
							columnWidth : .25,
							layout : 'form',
							border : false,
							items : [ {
								fieldLabel : '礼品名称',
								name : 'GIFT_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},{
								fieldLabel : '订单生成日期从',
								id : 'orderDateStart',
								name : 'orderDateStart',
								xtype : 'datefield',
								format : 'Y-m-d',
								editable : false,
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						}, {
							columnWidth : .25,
							layout : 'form',
							border : false,
							items : [{
								fieldLabel : '礼品种类',
								store : typeStore,
								xtype : 'combo', 
								resizable : true,
								name : 'GIFT_TYPE',
								hiddenName : 'GIFT_TYPE',
								valueField : 'myId',
								displayField : 'displayText',
								labelStyle: 'text-align:right;',
								mode : 'local',
								editable : false,
								typeAhead : true,
								forceSelection : true,
								triggerAction : 'all',
								emptyText : '请选择',
								selectOnFocus : true,
								//width : '100',
								anchor : '90%'
							},{
								fieldLabel : '订单生成日期到',
								id : 'orderDateEnd',
								name : 'orderDateEnd',
								format : 'Y-m-d',
								editable : false,
								xtype : 'datefield',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}
							]
						}, {
							columnWidth : .25,
							layout : 'form',
							border : false,
							items : [{
								fieldLabel : '客户名称',
								//id:'CUST_ZH_NAME',
								name : 'CUST_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
						}]
			}],
		buttons : [{
					text : '查询',
					handler : function() {
				        store.on('beforeload', function() {
				        	var conditionStr =  qForm.getForm().getValues(false);
				        	
				        	debugger;
				        	
				           /* this.baseParams = {
				                    "condition":Ext.encode(conditionStr)
				                    
				            };*/
				        	store.baseParams = {
									"condition" : Ext.encode(conditionStr)
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


	    
        {header : '订单编号',dataIndex : 'id',sortable : true,width : 120},
        {header : '礼品名称',dataIndex : 'giftName',sortable : true,width : 120},
        {header : '礼品种类',dataIndex : 'giftType',sortable : true,width : 120,renderer:function(value){
        	if('1'==value) return '家用电器';
        	if('2'==value) return '手机数码';
        	if('3'==value) return '图书期刊';
        	if('4'==value) return '音像制品';
        	if('5'==value) return '汽车用品';
        	if('6'==value) return '运动健康';
        	if('7'==value) return '医疗保健';
        	if('8'==value) return '箱包服装';
        	if('9'==value) return '食品饮料';
        	if('10'==value) return '优惠礼券';
        }},
        {header : '单位礼品积分',dataIndex : 'giftPoint',sortable : true,width : 120},
        {header : '兑换数量',dataIndex : 'exchangeCount',sortable : true,width : 120},
        {header : '消费积分总额',dataIndex : 'exchangeTotle',sortable : true,width : 120},
        {header : '客户号',dataIndex : 'custId',sortable : true,width : 120},
        {header : '客户名称',dataIndex : 'custName',sortable : true,width : 120},
        {header : '客户经理',dataIndex : 'custMgr',sortable : true,width : 120},
        {header : '订单状态',dataIndex : 'orderStatus',sortable : true,width : 120},

        //{header : '订单状态',dataIndex : 'orderStatus',type:'date',sortable : true,renderer:function(a,b,c,d){return new Date(a.time);},width : 150}

        {header : '订单生成日期',dataIndex : 'orderDate',sortable : true,width : 120}



	    
		]);

/**
 * 数据存储
 */
 var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({
		        	url:basepath+'/integral-Action.json'/*,
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
					{name: 'id',mapping : 'ID'},
					{name: 'giftName',mapping : 'GIFT_NAME'},
					{name: 'custId',mapping:'CUST_ID'},				
					{name: 'custMgr',mapping:'CUST_MGR'},	
					{name: 'custName',mapping:'CUST_NAME'},
					{name: 'exchangeCount',mapping:'EXCHANGE_COUNT'},
					{name: 'exchangeTotle',mapping:'EXCHANGE_TOTLE'},
					{name: 'giftPoint',mapping:'GIFT_POINT'},
					{name: 'giftType',mapping:'GIFT_TYPE'},
					{name: 'orderDate',mapping:'ORDER_DATE'},
					{name: 'orderStatus',mapping:'ORDER_STATUS'}

		

			
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



// 新增窗口展示的from
var addProForm = new Ext.form.FormPanel({
	labelWidth : 100,
	width:800,
	height : 300,
	frame : true,
	labelAlign : 'right',
	region : 'center',
	autoScroll : true,
	buttonAlign : "center",
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .3,
			layout : 'form',
			items : [{
				name : 'giftName',
				xtype : 'textfield',
				fieldLabel : '礼品名称',
				anchor : '90%'

				//hidden : true
			},{
				name : 'giftPoint',
				xtype : 'numberfield',
				fieldLabel : '单位礼品积分',
				anchor : '90%'


			},{
				name : 'orderStatus',
				id : 'orderStatus',
				xtype : 'textfield',
				fieldLabel : '订单状态',
				hidden:true,
				anchor : '90%'

				//hidden : true
			},	   {
				name : 'custId',
				xtype : 'textfield',
				fieldLabel : '客户号',
				anchor : '90%'}]
		}, {
			columnWidth : .3,
			layout : 'form',
			items : [{
				store : typeStore,
				xtype : 'combo', 
				resizable : true,
				name : 'giftType',
				hiddenName : 'giftType',
				fieldLabel : '礼品种类',
				valueField : 'myId',
				displayField : 'displayText',
				mode : 'local',
				editable : false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				width : '100',
				anchor : '90%'
			},{
				name : 'exchangeCount',
				xtype : 'numberfield',
				fieldLabel : '兑换数量',
				anchor : '90%'
			},{
				name : 'custName',
				xtype : 'textfield',
				fieldLabel : '客户名称',
				anchor : '90%'
			}]
		},{
			layout : 'form',
			columnWidth : .3,
			items : [ {
				name : 'orderDate',
				xtype : 'textfield',
				fieldLabel : '订单生成日期',
				value : DateFor(new Date()),
				anchor : '90%'
			},
			          
		
				{
					name : 'exchangeTotle',
					xtype : 'numberfield',
					fieldLabel : '消费积分总额',
					anchor : '90%'
				},{
					name : 'custMgr',
					xtype : 'textfield',
					fieldLabel : '客户经理',
					anchor : '90%'
						
				  }]
		     }
		]
	}]

});


//修改窗口展示的from
var editProForm = new Ext.form.FormPanel({
	labelWidth : 100,
	width:800,
	height : 300,
	frame : true,
	labelAlign : 'right',
	region : 'center',
	autoScroll : true,
	buttonAlign : "center",
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .3,
			layout : 'form',
			items : [  {
				name : 'id',
				xtype : 'textfield',
				fieldLabel : '订单编号',
				hidden : true
			},{
				name : 'giftName',
				xtype : 'textfield',
				fieldLabel : '礼品名称',
				anchor : '90%'

				//hidden : true
			},{
				name : 'giftPoint',
				xtype : 'numberfield',
				fieldLabel : '单位礼品积分',
				anchor : '90%'


			},{
				name : 'orderStatus',
				id : 'orderStatus1',
				xtype : 'textfield',
				fieldLabel : '订单状态',
				readOnly : true,
				hidden:true,
				anchor : '90%'

				//hidden : true
			},	   {
				name : 'custId',
				xtype : 'textfield',
				fieldLabel : '客户号',
				anchor : '90%'}]
		}, {
			columnWidth : .3,
			layout : 'form',
			items : [{
				store : typeStore,
				xtype : 'combo', 
				resizable : true,
				readOnly : true,
				name : 'giftType',
				hiddenName : 'giftType',
				fieldLabel : '礼品种类',
				valueField : 'myId',
				displayField : 'displayText',
				mode : 'local',
				editable : false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				width : '100',
				anchor : '90%'
			},{
				name : 'exchangeCount',
				xtype : 'numberfield',
				fieldLabel : '兑换数量',
				anchor : '90%'
			},{
				name : 'custName',
				xtype : 'textfield',
				fieldLabel : '客户名称',
				anchor : '90%'
			}]
		},{
			layout : 'form',
			columnWidth : .3,
			items : [ {
				name : 'orderDate',
				xtype : 'textfield',
				fieldLabel : '订单生成日期',
				//value : new Date(),
				readOnly:true,
				anchor : '90%'
			},{
					name : 'exchangeTotle',
					xtype : 'numberfield',
					fieldLabel : '消费积分总额',
					anchor : '90%'
				},{
					name : 'custMgr',
					xtype : 'textfield',
					fieldLabel : '客户经理',
					anchor : '90%'
						
				  }]
		     }
		]
	}]

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
					text:'新增',
					iconCls:'addIconCss',
					handler:function()
					{							
		        	addProForm.getForm().findField('orderStatus').setValue('草稿');

		        	addProWindow.show();
		        	
					}},'-',{
						text:'修改',
						iconCls:'editIconCss',
						handler : function() {

							var selectLength = grid
									.getSelectionModel()
									.getSelections().length;

							var selectRe = grid
									.getSelectionModel()
									.getSelections()[0];
              debugger;
							if (selectLength != 1) {
								Ext.Msg.alert('提示','请选择一条记录!');
							} 
						
							else {
								editProForm.getForm().loadRecord(selectRe);
								//debugger;
							//	editInit();
								editProWindow.show();
							}
						
						}},'-',{ // id : 'deleteProduct',
							text : '删除',
							iconCls : 'deleteIconCss',
							handler : function() {
								var record = grid
										.getSelectionModel().getSelected();// 选择
																			// 多行记录
								var selectLength = grid
										.getSelectionModel()
										.getSelections().length;
						
								if (selectLength < 1) {
									Ext.Msg.alert('提示', '请选择需要删除的记录!');
								} else {
									Ext.MessageBox
											.confirm(
													'提示',
													'确定删除吗?',
													function(buttonId) {
														if (buttonId
																.toLowerCase() == "no") {
															return;
														}
														var selectRe;
														var tempId;
														var idStr = '';
														for ( var i = 0; i < selectLength; i++) {
															selectRe = grid
																	.getSelectionModel()
																	.getSelections()[i];
															tempId = selectRe.data.id;
															//spid = selectRe.data.prodBusId;
															idStr += tempId;
															
															if (i != selectLength - 1)
																idStr += ',';
														}
														  
													debugger;
														Ext.Ajax.request( {
															url : basepath
															+ '/ChangeAction/'
															+ tempId
															+ '.json?idStr='
															+ idStr,	
																	method : 'DELETE',
																	success : checkResult,
																	failure : checkResult

																});
														function checkResult(response) {
															var resultArray = Ext.util.JSON.decode(response.status);
															var resultError = response.responseText;
															  
															if ((resultArray == 200 || resultArray == 201)
																	&& resultError == '') {

																Ext.Msg.alert('提示','删除成功');
                                                                 store.reload();}
													         else {Ext.Msg.alert('提示', response.responseText);

																	
																}
															
														}
													}
								);
								}
							}}
//						,'-',{
//								text:'提交',
//								iconCls:'completeIconCss',
//								handler:function()
//								{
//					        		
//					        	
//								}},'-',{
//										text:'确认',
//										iconCls:'maintainIconCss',
//										handler:function()
//										{
//							        		
//							        	
//										}},'-',{
//											text:'审批',
//											iconCls:'shenpiIconCss',
//											handler:function()
//											{
//								        		
//								        	
//											}},'-',{
//												text:'兑换',
//												iconCls:'detailIconCss',
//												handler:function()
//												{
//									        		
//									        	
//												}}
											],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var addProWindow = new Ext.Window({
	title : '新增积分兑换',
	plain : true,
	layout : 'fit',
	width : 900,
	height : 220,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	items : [ addProForm ],
	buttons : [

				{

					text : '保  存',
					handler : function() {
						if(!addProForm.getForm().isValid()) { 
							Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请检查!");
							return false;
						}
						sel = addProForm.getForm();
						debugger;
					
						Ext.Ajax.request({
							url : basepath + '/ChangeAction.json',
							method : 'POST',
							form : addProForm.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								addProWindow.hide();
								store.removeAll();
						
								store.reload();
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.status);
								 if(resultArray == 403) {
							           Ext.Msg.alert('提示', response.responseText);
								 }else{
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							}
							}
						});
						addProForm.getForm().reset();
					}

				}, {
					text : '取  消',
					handler : function() {
						addProWindow.hide();
					}
				} ]
});
var editProWindow = new Ext.Window({
	title : '修改积分兑换订单信息',
	plain : true,
	layout : 'fit',
	width : 900,
	height : 220,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	items : [ editProForm ],
	buttons : [

				{

					text : '保  存',
					handler : function() {
					
				
						
						
				if (!editProForm.getForm().isValid()) {
					Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
					return false;
				}
		
					Ext.Ajax.request( {
						url : basepath + '/ChangeAction.json',
						method : 'POST',
						form : editProForm.getForm().id,
						success : checkResult,
						failure : checkResult
					});
					  
					function checkResult(response) {
						var resultArray = Ext.util.JSON
								.decode(response.status);
						var resultError = response.responseText;
						if ((resultArray == 200 || resultArray == 201)
								&& resultError == '') {
							Ext.Msg.alert('系统提示信息', '操作成功');
							editProWindow.hide();
							store.removeAll();
							store.load();
						} else {
							if (resultArray == 403) {
								Ext.Msg.alert('系统提示信息',
										response.responseText);
							} else {
								Ext.Msg.alert('系统提示信息',
										'操作失败,失败原因:' + resultError);
							}
						}
					}
					
						
				
					//editProWindow.hide();
					}

				}, {
					text : '取  消',
					handler : function() {
					editProWindow.hide();
					}
				} ]
});

var viewport = new Ext.Viewport({
	layout:'fit',
	items:[{
	layout : 'border',
	items: [qForm,grid] 
	}]
});



   }); 