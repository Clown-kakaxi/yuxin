/**
 * 信用卡客户行为分析demo
 * (东亚POC用)
 */
Ext.onReady(function() {
	/*******************获取当前登录用户ID，机构ID，机构级别******************/
	var userId = __userId;
	var orgId = __units;
	var unitlevel = __unitlevel;

	var classStore = new Ext.data.ArrayStore({
		fields:['key','value'],
		data : [['1','普卡'],['2','金卡'],['2','白金卡']]
	});
	/*******************参数设置级别数据源******************/
	var parmLevelStore = new Ext.data.ArrayStore({
		fields:['key','value'],
		data : [['1','总行'],['2','分行']]
	});
	var serviceStatStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SERVICE_STAT'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	serviceStatStore.load();
	var serviceKindStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SERVICE_KIND'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	serviceKindStore.load();
	var planChannelStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SERVICE_CHANNEL'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	planChannelStore.load();
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">主动服务新增</span>',
		width: '100%',
	    height:'100%',
		items: [
				{
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : serviceStatStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '服务状态',
							name : 'serviceStat',
							editable : false,
							hiddenName : 'serviceStat',
							value : '01',
							readOnly : true,
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						}, {
							name : 'serviceId',
							xtype : 'hidden'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : serviceKindStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '服务类别',
							editable : false,
							name : 'serviceKind',
							hiddenName : 'serviceKind',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							value : '01',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					} ]
				},
				{
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									id : 'custId',
									name : 'custId',
									fieldLabel : '客户编号',
									readOnly : true,
									width : 100,
									xtype : 'textfield',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .5,
								layout : 'form',
								items : [ new Com.yucheng.bcrm.common.CustomerQueryField(
										{
											fieldLabel : '*客户名称',
											labelWidth : 100,
											allowBlank : false,
											blankText : '此项不能为空',
											id : 'CUST_NAMEIDFS',
											name : 'custName',
											custtype : '1',// 客户类型：
															// 1：对私,
															// 2:对公,
															// 不设默认全部
											singleSelected : true,// 单选复选标志
											editable : false,
											anchor : '95%',
											hiddenName : 'CUST_ID',
											callback : function() {
												var cust_name = null;
												cust_name = Ext.getCmp('CUST_NAMEIDFS').getValue();
												if (cust_name != null
														&& cust_name != '') {
													var cust_id = Ext.getCmp('CUST_NAMEIDFS').customerId;
													this.ownerCt.ownerCt.ownerCt.getForm().findField('custId').setValue(cust_id);
												}
											}
										}) ]
							} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ new Com.yucheng.crm.common.ProductManage({ 
							 xtype:'productChoose',
								fieldLabel : '目标营销产品',
						 name : 'productName',
						  hiddenName:'aimProd',
						        singleSelect:false,
						        anchor : '90%'
						       })]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : planChannelStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '服务预计渠道',
							editable : false,
							name : 'planChannel',
							hiddenName : 'planChannel',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ new Ext.form.DateField( {
							name : 'pStartDate',
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '*计划开始日期',
							allowBlank : false,
							blankText : '此项不能为空',
							anchor : '90%'
						}) ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							columnWidth : .5,
							layout : 'form',
							items : [ new Ext.form.DateField( {
								name : 'pEndDate',
								format : 'Y-m-d',
								editable : false,
								fieldLabel : '*预计结束日期',
								allowBlank : false,
								blankText : '此项不能为空',
								anchor : '90%'
							}) ]
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							columnWidth : .5,
							layout : 'form',
							items : [ new Ext.form.DateField( {
								name : 'actualDate',
								disabled : true,
								format : 'Y-m-d',
								fieldLabel : '实际服务日期',
								anchor : '90%'
							}) ]
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : planChannelStore,
							disabled : true,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '实际接触渠道',
							name : 'cantactChannel',
							hiddenName : 'cantactChannel',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'serviceCont',
						xtype : 'textarea',
						fieldLabel : '*服务内容',
						allowBlank : false,
						blankText : '此项不能为空',
						maxLength : 1000,
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'needResource',
						xtype : 'textarea',
						fieldLabel : '所需资源',
						maxLength : 1000,
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'serviceResult',
						xtype : 'textarea',
						fieldLabel : '服务结果',
						maxLength : 1000,
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'needEvent',
						xtype : 'textarea',
						fieldLabel : '待跟进事项',
						maxLength : 2000,
						anchor : '90%'
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'hidden',
							fieldLabel : '创建人ID',
							name : 'createUser',
							anchor : '90%'
						},{
							xtype : 'hidden',
							name : 'createOrg',
							anchor : '90%'
						},{
							xtype : 'hidden',
							name : 'pOrC',
							anchor : '90%'
						},  {
							id : 'userName',
							xtype : 'textfield',
							fieldLabel : '创建人',
							readOnly : true,
							name : 'USER_NAME',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'createDate',
							xtype : 'textfield',
							fieldLabel : '创建日期',
							readOnly : true,
							name : 'createDate',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'hidden',
							fieldLabel : '最近更新人ID',
							name : 'updateUser',
							anchor : '90%'
						}, {
							id : 'updateUserName',
							xtype : 'textfield',
							fieldLabel : '最近更新人',
							name : 'UPDATEUSER_NAME',
							readOnly : true,
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'updateDate',
							xtype : 'textfield',
							fieldLabel : '最近更新日期',
							readOnly : true,
							name : 'updateDate',
							anchor : '90%'
						} ]
					} ]
				} ]
		});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
        height :500,
        width:800,
		buttonAlign : 'center',
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
        autoScroll:true,
		closeAction : 'hide',
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
//							insert();
						}
					}, {
						text : '重置',
						handler : function() {
//							resetForm(panel2);
						}
					}, {
						text : '关闭',
						handler : function() {
							addRoleWindow.hide();
						}
					} ]
	});
	/*******************获取已有参数名数据源，用于防止设置重名参数******************/
	var checkrecord = Ext.data.Record.create([ 
   	    {name: 'parmName', mapping: 'PARM_NAME'},
 	    {name: 'parmNum', mapping: 'PARM_NUM'}
   		]);
  	                             	
   	var checkStore = new Ext.data.Store({
   		restful : true,
   		proxy : new Ext.data.HttpProxy({
   			url : basepath + '/ContributionParam.json',
   			method:'GET'
   		}),
   		autoLoad : true,
   		reader : new Ext.data.JsonReader({
   			successProperty: 'success',
   	        idProperty: 'parmName',
   	        messageProperty: 'message',
   			root : 'json.data',
   			totalProperty : 'json.count'
   		}, checkrecord)
   	});
	
   	/*******************查询表单******************/
	var qform = new Ext.form.FormPanel({
		labelWidth:90,
		height:120,
		frame:true,
		labelAlign:'middle',
		buttonAlign:'center',
		items:[{
			layout:'column',
			border:false,
			items:[{
				columnWidth:.33,
				border:true,
				layout:'form',
				labelWidth:80,
				items:[new Ext.form.ComboBox({
                    xtype:'combo',
                    fieldLabel : '卡类型',
                    name: 'parmType' ,
                    hiddenName : 'parmType' ,
                    id:'parmTypeSearch',
                    editable : false,
                    resizable:true,
                    forceSelection : true,
                    labelStyle: 'text-align:right;',
                    triggerAction:'all',
                    mode:'local',
                    store:classStore,
                    valueField:'key',
                    displayField:'value',
                    emptyText:'请选择',
                    anchor : '90%'
               })]
			},{
				columnWidth:.33,
				border:true,
				layout:'form',
				labelWidth:80,
				items:[{
					 xtype:'datefield',
					 fieldLabel: '统计起始日期',
					 name: 'WHDT',
					 format:'Y-m-d', //日期格式化
					  labelStyle: 'text-align:right;',
					 anchor:'90%'
				 }]
			},{
				columnWidth:.33,
				border:true,
				layout:'form',
				labelWidth:80,
				items:[{
					 xtype:'datefield',
					 fieldLabel: '统计截止日期',
					 name: 'WHDT',
					 format:'Y-m-d', //日期格式化
					  labelStyle: 'text-align:right;',
					 anchor:'90%'
				 }]
			}]
		}],
		buttons:[{
			text : '查询',
			handler : function() {
//				var conditionStr = qform.getForm().getFieldValues();
//				store.baseParams = {
//						"condition" : Ext.encode(conditionStr)
//					};
//				store.load({
//					params : {
//						start : 0,
//						limit : parseInt(pagesize_combo.getValue())
//					}
//				});
//				store.reload();
			}
		},{
			text:'重置',
			handler:function(){
//			qform.getForm().reset();
//			parmTree.root.getUI().toggleCheck(false);
		}
			
		}]
	});
	
	/*******************每页显示条数下拉选择框******************/
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});
	
	/*******************改变每页显示条数reload数据******************/
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue());
		store.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	/*******************定义自动当前页行号******************/
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	/*******************复选框******************/

//客户号、客户名称、消费总金额、消费次数、最高消费金额、平均消费金额、消费商户类-餐饮、消费商户类-商场、消费商户类-网上支付、消费商户类-其他
	/*******************列模型******************/
	var columns = new Ext.grid.ColumnModel([rownum,sm,{
		header : 'id',
		dataIndex : 'id', 
		sortable : true,
		hidden : true,
		width : 120
	},{
        header : '客户号',
        dataIndex : 'custId', 
        sortable : true,
        width : 120
    },{
        header : '客户名称',
        dataIndex : 'custName', 
        sortable : true,
        width : 200
    },{
        header : '消费总金额',
        dataIndex : 't1',
        align : 'right',
       renderer: money('0,000.00'),
        sortable : true,
        width : 120
    },{
		header : '消费次数', 
		dataIndex : 't2', 
		sortable : true,
		width : 120
	},{
		header : '最高消费金额', 
		dataIndex : 't3', 
	    align : 'right',
	    renderer: money('0,000.00'),
		sortable : true,
		width : 150
	},{
		header : '平均消费金额', 
		dataIndex : 't4', 
	    align : 'right',
	    renderer: money('0,000.00'),
		sortable : true,
		width : 120
	},{
		header : '消费商户类-餐饮', 
		dataIndex : 't5', 
	    align : 'right',
	    renderer: money('0,000.00'),
		sortable : true,
		width : 120
	},{
		header : '消费商户类-商场', 
		dataIndex : 't6', 
	    align : 'right',
		sortable : true,
		 renderer: money('0,000.00'),
		width : 120
	},{
		header : '消费商户类-网上支付', 
		dataIndex : 't7', 
		sortable : true,
	    align : 'right',
	    renderer: money('0,000.00'),
		width : 120
	},{
		header : '消费商户类-其他', 
		dataIndex : 't8', 
		sortable : true,
	    align : 'right',
	    renderer: money('0,000.00'),
		width : 120
	}]);

   var store = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
            root:'rows',
            totalProperty: 'num'
        }, [ {name:'id'},
        {name:'custId'},
        {name:'custName'},
        {name:'t1'},
        {name:'t2'},
        {name:'t3'},
        {name:'t4'},
        {name:'t5'},
        {name:'t6'},
        {name:'t7'},
        {name:'t8'}
		])
	});
	var tempData= {
			num:1,
			rows:[
			{"id":"1","custId":"101","custName":"张帆","t1":"6000","t2":"1","t3":"2000000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"2","custId":"102","custName":"谢娜","t1":"7000","t2":"2","t3":"2000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"3","custId":"103","custName":"吕胜","t1":"6000","t2":"2","t3":"2000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"4","custId":"104","custName":"王文艺","t1":"7000","t2":"3","t3":"4000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"5","custId":"105","custName":"赵强","t1":"6000","t2":"1","t3":"2000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"6","custId":"106","custName":"李丽","t1":"6000","t2":"5","t3":"4000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"7","custId":"107","custName":"张华","t1":"7000","t2":"1","t3":"2000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"8","custId":"108","custName":"王一平","t1":"7000","t2":"1","t3":"2000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"9","custId":"109","custName":"齐岳","t1":"6000","t2":"1","t3":"1000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"10","custId":"110","custName":"杨帆","t1":"7000","t2":"1","t3":"1000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"11","custId":"111","custName":"于敏","t1":"7000","t2":"1","t3":"1000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"12","custId":"112","custName":"刘洋","t1":"7000","t2":"1","t3":"1000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"},
			{"id":"13","custId":"113","custName":"张志强","t1":"7000","t2":"1","t3":"1000","t4":"2000","t5":"1000","t6":"2000","t7":"2000","t8":"2000"}
			]
		};
		store.loadData(tempData);

	/*******************分页工具栏******************/
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	// 新增商机
	function addInit() {
		busiOpportAddWindowInit();
	}
	var grid = new Ext.grid.GridPanel({
		title : "信用卡客户行为分析查询",
		store : store,
		frame : true,
		sm : sm,
		cm : columns,
		stripeRows : true,
		tbar : [{
			text : '产品推荐',
			iconCls : 'addIconCss',
//			hidden:true,
			handler : function() {
			addInit();
		}
		},'-',{
			text : '制定服务计划',
//			hidden:true,
			iconCls : 'editIconCss',
			handler : function() {
			addRoleWindow.show();
		}							
		},'-',new Com.yucheng.bob.ExpButton({ //导出按钮
            formPanel : qform,
            iconCls:'exportIconCss',
            url : basepath + '/AdminLogQuery.json'
		})],
		region : 'center',
		frame : true,
		bbar : bbar,// 分页工具栏
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	/*******************整体布局******************/
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items:[{
		layout:'border',
		items:[{
			region:'north',
			id:'north-panel',
			title:"信用卡客户行为分析",
			height:'118',
			margins:'0 0 0 0',
			items:[qform]
		},{
			region:'center',
			id:'center-panel',
			layout:'fit',
			autoScroll:true,
			margins:'0 0 0 0',
			items:[grid]
		}]
		}]
	});
});