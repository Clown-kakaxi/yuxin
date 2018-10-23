Ext.onReady(function() {
	Ext.QuickTips.init();   

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'NO',
		width : 28
	});
	
	//活动响应
	var qForm = new Ext.form.FormPanel({
		labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 130,
//		width :1500,
		items : [ {
			layout : 'column',
			items : [{
				columnWidth : .3,
				layout : 'form',
				items : [ {
					fieldLabel : '活动计划名称',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'MKT_ACTI_NAME',
					anchor : '90%'
				} ]
			}, {
				columnWidth : .3,
				layout : 'form',
				border : false,
				items : [
				    new Com.yucheng.bcrm.common.OrgField({
					searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					roleType:__roleType,
					labelStyle : 'text-align:right;',
					id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'ORG_NAME', 
					hiddenName: 'ORG_ID',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			},{
				columnWidth : .3,
				layout : 'form',
				border : false,
				items : [
					new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '所属客户经理', 
						id:'CUST_MANAGER',
						labelStyle: 'text-align:right;',
						name : 'CUST_MANAGER',
						hiddenName:'CUST_MANAGER_ID',
						searchRoleType:(__roleType=='1'?'1025,1027':'1014'),  //指定查询角色属性
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:false,
						anchor : '90%'
					})
				]
			}, {
				columnWidth : .3,
				layout : 'form',
				items : [{
					fieldLabel : '统计日期',
					xtype : 'datefield',
					id : 'createDateS',
					format : 'Y-m-d',
					editable : false,
					name : 'createDateS',
					anchor : '90%'
				}]
			},{
			    columnWidth : .3,
                layout : 'form',
//                labelWidth: 20,
				items : [{
					xtype : 'datefield',
					format : 'Y-m-d',
					editable : false,
					fieldLabel : '至',
					name : 'createDateE',
					id : 'createDateE',
					anchor : '90%'
				}]
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = qForm.getForm().getValues(false);
				store.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
//    		    Ext.getCmp('jigouhao').setValue('');
//    		    Ext.getCmp('CUST_MANAGER').setValue('');
			}
		} ]
	});
	
	//活动响应record
	var record = Ext.data.Record.create([{
		name : 'countId',
		mapping : 'COUNT_ID'
	},{
		name : 'planId',
		mapping : 'PLAN_ID'
	},{
		name : 'planName',
		mapping : 'MKT_ACTI_NAME'
	},{
		name : 'targetCustNum',
		mapping : 'TARGET_CUST_NUM'
	},{
		name : 'addChanceNum',
		mapping : 'ADD_CHANCE_NUM'
	},{
		name : 'custResScale',
		mapping : 'CUST_RES_SCALE'
	},{
		name : 'compleChanceNum',
		mapping : 'COMPLE_CHANCE_NUM'
	},{
		name : 'compleChanceScale',
		mapping : 'COMPLE_CHANCE_SCALE'
	},{
		name : 'mktSuccessNum',
		mapping : 'MKT_SUCCESS_NUM'
	},{
		name : 'mktSuccessScale',
		mapping : 'MKT_SUCCESS_SCALE'
	},{
		name : 'countDate',
		mapping : 'ETL_DATE'
	},{
		name : 'orgId',
		mapping : 'ORG_ID'
	},{
		name : 'orgName',
		mapping : 'ORG_NAME'
	},{
		name : 'custManagerId',
		mapping : 'CUST_MANAGER_ID'
	},{
		name : 'custManagerName',
		mapping : 'CUST_MANAGER_NAME'
	},{
		name : 'targetProductId',
		mapping : 'TARGET_PRODUCT_ID'
	},{
		name : 'targetProductName',
		mapping : 'TARGET_PRODUCT_NAME'
	}]);

	
	// 活动响应定义列模型
	var cm = new Ext.grid.ColumnModel([ rownum,{
		header : 'ID',
		width : 190,
		align : 'center',
		hidden:true,
		dataIndex : 'countId',
		sortable : true
	},{
		header : '活动计划ID',
		width : 190,
		align : 'center',
		dataIndex : 'planId',
		sortable : true,
		hidden : true
	}, {
		header : '活动计划名称',
		width : 190,
		align : 'center',
		dataIndex : 'planName',
		sortable : true
	},{
		header : '统计日期',
		width : 190,
		align : 'center',
		dataIndex : 'countDate',
		sortable : true
	},{
		header : '机构名称',
		width : 190,
		align : 'center',
		dataIndex : 'orgName',
		sortable : true
	},{
		header : '客户经理',
		width : 190,
		align : 'center',
		dataIndex : 'custManagerName',
		sortable : true
	}, {
		header : '目标产品',
		width : 190,
		align : 'center',
		dataIndex : 'targetProductName',
		sortable : true,
		hidden : true
	}, {
		header : '目标客户数',
		width : 180,
		align : 'center',
		dataIndex : 'targetCustNum',
		sortable : true
	},{
		header : '增加商机数',
		width : 180,
		align : 'center',
		dataIndex : 'addChanceNum',
		sortable : true,
		hidden : true
	}, {
		header : '客户响应比例',
		width : 180,
		align : 'center',
		dataIndex : 'custResScale',
		sortable : true,
		hidden : true
	}, {
		header : '成功商机数',
		width : 180,
		align : 'center',
		dataIndex : 'compleChanceNum',
		sortable : true,
		hidden : true
	}, {
		header : '商机成功率',
		width : 180,
		align : 'center',
		dataIndex : 'compleChanceScale',
		sortable : true,
		hidden : true
	}, {
		header : '营销成功数',
		width : 180,
		align : 'center',
		dataIndex : 'mktSuccessNum',
		sortable : true
	}, {
		header : '营销成功率',
		width : 180,
		align : 'center',
		dataIndex : 'mktSuccessScale',
		sortable : true
	}
]);
	
	/**
	 * 数据存储，营销响应
	 */
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelCust.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'COUNT_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record)
	});
			
	
	//活动响应			
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	store.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), store
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
			
	// 表格实例
	var grid = new Ext.grid.GridPanel({
		frame : true,
		autoScroll : true,
//		height : 500,
		height: document.body.scrollHeight-140,
//		width :1500,
		region : 'center',
		store : store,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		//tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [{
						region : 'center',
						id : 'center-panel',
						title : "活动响应情况统计列表",
						layout : 'fit',
						items : [ grid ]
					},

					{
						region : 'north',
						id : 'north-panel',
						title : "活动响应情况查询",
						height : 130,
						layout : 'fit',
						items : [ qForm ]
					}

			]
		} ]
	});
	
	

});