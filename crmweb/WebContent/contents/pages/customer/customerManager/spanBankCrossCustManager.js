Ext.onReady(function() {
	Ext.QuickTips.init();   

	//跨分行交叉客户查询条件
	var qForm = new Ext.form.FormPanel({
		title : "跨分行交叉客户查询",
		labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 100,
		width : document.body.scrollWidth,
		layout : 'column',
		items : [ {
			columnWidth : .25,
			layout : 'form',
			items : [ new Com.yucheng.bcrm.common.OrgField({
				searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				fieldLabel : '所属机构',
				labelStyle : 'text-align:right;',
				id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
				name : 'CUST_ORG', 
				hiddenName: 'instncode',   //后台获取的参数名称
				anchor : '90%',
				checkBox:true //复选标志
			}) ]
		},{
				columnWidth : .25,
				layout : 'form',
				items : [new Ext.yucheng.form.MonthField( {
					id:'dataDate',
					fieldLabel : '数据日期',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					format : 'Y-m',
					name : 'DATA_DATE',
					value:new Date(),
					anchor : '90%'
				}) ]
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
				Ext.getCmp("jigouhao").setValue('');
			}
		} ]
	});
	
	//跨支行交叉客户查询条件
	var qForm1 = new Ext.form.FormPanel({
		title : "跨支行交叉客户查询",
		labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 100,
		width :1500,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ new Com.yucheng.bcrm.common.OrgField({
					searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					labelStyle : 'text-align:right;',
					id : 'jigouhao2', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'instncode',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				}) ]
			},{
					columnWidth : .25,
					layout : 'form',
					items : [ new Ext.yucheng.form.MonthField( {
						id:'dataDate1',
						fieldLabel : '数据日期',
						labelStyle: 'text-align:right;',
						xtype : 'datefield',
						format : 'Y-m',
						name : 'DATA_DATE1',
						value:new Date(),
						anchor : '80%'
					}) ]
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = qForm1.getForm().getValues(false);
				store1.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				store1.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo1.getValue())
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				qForm1.getForm().reset();
				Ext.getCmp("jigouhao2").setValue('');
			}
		} ]
	});
	// 定义展示窗口的tabPanel
	var listPanel = new Ext.TabPanel({
		id : 'listPanel',
		//loyout : 'fit',
		autoScroll :true,
		region : 'center',
		activeTab : 0,
		tabPosition : 'top',
		items : [ {
					title : '总行跨分行开户客户明细',
					layout : 'border',
					items : [  qForm,grid ]
		          },{
					title : '分行跨支行开户客户明细',
					layout : 'border',
				    items : [  qForm1,grid1]}]
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [ {
			layout : 'border',
			items : [ listPanel ]
		} ]
	});

});