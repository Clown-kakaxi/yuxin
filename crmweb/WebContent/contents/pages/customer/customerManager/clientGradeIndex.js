Ext.onReady(function() {
	function setValue(a) {
		Ext.getCmp('wancheng_1').setValue(a), listPanel.save();
	}
	Ext.QuickTips.init();
	var finishStateStore = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=FINISH_STATE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	/*********客户类型定义********/
	var  customer_Type = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户管理->客户分级管理->客户分级等级定义",
		stUrl : basepath + '/ocrmFCiGradeDefine-info!indexPage.json',
		deUrl : basepath + '/ocrmFCiGradeDefine-info!batchDestroy.json',
		addUrl : basepath + '/ocrmFCiGradeDefine-info.json',
		updateUrl : basepath + '/ocrmFCiGradeDefine-info.json',
		pagesize : 20,
		buts : [],
		primary : "gradeId",
		checkbox : true,
		seFormHeight : 0,
		winHeight : 220,
		winWidth : 550,
		gclms : [ {
			width : 150,
			name : 'gradeId',
			header : '客户等级编号'
		}, {
			width : 150,
			name : 'custType',
			header : '客户类型',
			type : 'mapping',
			store : customer_Type,
			mappingkey : 'key',
			mappingvalue : 'value'
		}, {
			width : 150,
			name : 'gradeName',
			header : '客户等级名称'
		}, {
			width : 150,
			name : 'countLower',
			header : '指标总分数下线(包含)'
		}, {
			width : 150,
			name : 'countUpper',
			header : '指标总分数上线(不包含)'
		}],
		fclms : [ {
			layout : 'form',
			labelAlign:'right',
			items : [ {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					store : customer_Type,
					xtype : 'combo',
					editable:false,
					resizable : true,
					fieldLabel : '客户类型',
					name : 'custType',
					hiddenName : 'custType',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					width : '100',
					anchor : '95%'
				} ]
			}, {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					name : 'gradeName',
					fieldLabel : '客户等级名称',
					xtype : 'textfield',
					width : 100,
					allowBlank : false,
					maxLength : 200,
					anchor : '95%'
				} ]
			}, {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					name : 'countLower',
					fieldLabel : '指标总分数下线(包含)',
					xtype : 'numberfield',
					decimalPrecision:10,
					width : 100,
					allowBlank : false,
					maxLength : 200,
					anchor : '95%'
				} ]
			}, {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					name : 'countUpper',
					fieldLabel : '指标总分数上线(不包含)',
					xtype : 'numberfield',
					decimalPrecision:10,
					width : 100,
					allowBlank : false,
					maxLength : 200,
					anchor : '95%'
				} ]
			} ]
		}, {
			layout : 'column',
			items : [ {// 特别注意：须放置隐藏域的主键
				name : 'gradeId',
				xtype : 'textfield',
				header : 'gradeId',
				hidden : true
			} ]
		} ]
	});
	var viewport = new Ext.Viewport( {
		items : [ listPanel ]
	});
});