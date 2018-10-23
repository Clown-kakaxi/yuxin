Ext.onReady(function() {
	Ext.QuickTips.init();   

	//跨行存款业务汇总查询条件
	var qForm = new Ext.form.FormPanel({
		title : "跨行存款业务汇总查询",
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
				items : [ new Ext.yucheng.form.MonthField( {
					id:'dataDate',
					fieldLabel : '数据日期',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					format : 'Y-m',
					name : 'DATA_DATE',
					value:new Date(),
					anchor : '90%'
				})]
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
			}
		} ]
	});
	
	//跨行存款业务明细查询条件
	var qForm1 = new Ext.form.FormPanel({
		title : "跨行存款业务明细查询",
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
				items : [ new Ext.yucheng.form.MonthField( {
					id:'dataDate1',
					fieldLabel : '数据日期',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					format : 'Y-m',
					name : 'DATA_DATE1',
					value:new Date(),
					anchor : '90%'
				})]
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
			}
		} ]
	});
	// 定义展示窗口的tabPanel
	var listPanel = new Ext.TabPanel({
		id : 'listPanel',
		loyout : 'fit',
		autoScroll :true,
		region : 'center',
		activeTab : 0,
		tabPosition : 'top',
		items : [ {title : '跨行存款代理业务汇总',layout : 'border',items : [ qForm,grid ]},
		          {title : '跨行存款代理业务明细',layout : 'border',items : [ qForm1,grid1]}]
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [ {
			layout : 'border',
			items : [  listPanel ]
		} ]
	});

});